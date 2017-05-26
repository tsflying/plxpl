package cn.plxpl.wx.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.plxpl.utils.DateUtils;
import cn.plxpl.wx.condition.TradeInfoCondition;
import cn.plxpl.wx.dao.ILuckyDao;
import cn.plxpl.wx.dao.ITradeDao;
import cn.plxpl.wx.entity.LuckyVoucherType;
import cn.plxpl.wx.entity.Product;
import cn.plxpl.wx.entity.TradeInfo;
import cn.plxpl.wx.entity.bo.LvAndLvtBo;
import cn.plxpl.wx.entity.dto.TotalFeeAndLuckyVoucher;
import cn.plxpl.wx.enums.LuckyVoucherParamEnum;
import cn.plxpl.wx.pay.req.PrePayReq;
import cn.plxpl.wx.pay.resp.PayResp;
import cn.plxpl.wx.pay.resp.PrePayResp;
import cn.plxpl.wx.service.IBaseDataService;
import cn.plxpl.wx.service.IPayService;
import cn.plxpl.wx.util.CommonUtil;
import cn.plxpl.wx.util.Constants;
import cn.plxpl.wx.util.MD5Util;
import cn.plxpl.wx.util.WeixinUtil;

/**
 * 微信支付服务
 * 
 * @author tsflying
 * 
 */
@SuppressWarnings("restriction")
@Service
public class PayService implements IPayService {

	private static Logger log = LoggerFactory.getLogger(PayService.class);

	@Resource
	ITradeDao tradeDao;

	@Resource
	ILuckyDao luckyDao;

	@Resource
	IBaseDataService baseDataService;

	public PrePayResp prePay(Product product, Integer count,
			String spbillCreateIp, String openid, String name, String phone)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, UnsupportedEncodingException {
		PrePayReq prePayReq = new PrePayReq();
		prePayReq.setAppid(Constants.appId);
		prePayReq.setMch_id(Constants.mch_id);
		prePayReq.setDevice_info("WEB");
		prePayReq.setNonce_str(WeixinUtil.getRandomString(32));
		prePayReq.setBody(product.getBody());
		prePayReq.setDetail(product.getDetail());
		prePayReq.setAttach(product.getAttach());
		String outTradeNo = WeixinUtil.getOutTradeNo();
		prePayReq.setOut_trade_no(outTradeNo);
		// Integer totalFee = (int) (product.getPrice() * count * 100);
		TotalFeeAndLuckyVoucher tfAndlv = getFactTotalFee(product.getPrice(),
				count, openid);
		// Integer totalFee = 1 * count;
		// ///////////////////TODO test
		prePayReq.setTotal_fee((int) (tfAndlv.getFactFee() * 100));
		// prePayReq.setSpbill_create_ip(spbillCreateIp);
		prePayReq.setNotify_url(Constants.notify_url);
		prePayReq.setTrade_type(Constants.JSAPI);// JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付

		prePayReq.setOpenid(openid);

		String sign = this.getSign(prePayReq);
		prePayReq.setSign(sign);

		String prePayReqXml = CommonUtil.objToXml(prePayReq);
		// prePayReqXml = new String(prePayReqXml.getBytes(), "ISO8859-1");
		JSONObject response = WeixinUtil.httpRequest(Constants.UnifiedOrderUrl,
				"POST", prePayReqXml);
		PrePayResp prePayResp = new PrePayResp();
		if (response.getString("return_code").equals(Constants.success)) {// 与微信服务器通信成功
			prePayResp.setReturnCode(response.getString("return_code"));
			prePayResp.setAppid(response.getString("appid"));
			prePayResp.setMchId(response.getString("mch_id"));
			prePayResp.setDeviceInfo(response.getString("device_info"));
			prePayResp.setNonceStr(response.getString("nonce_str"));
			prePayResp.setSign(response.getString("sign"));
			String resultCode = response.getString("result_code");
			if (StringUtils.isNotEmpty(resultCode)
					&& resultCode.equals("SUCCESS")) {
				prePayResp.setTradeType(response.getString("trade_type"));
				prePayResp.setPrepayId(response.getString("prepay_id"));
				TradeInfo tradeInfo = new TradeInfo();
				tradeInfo.setName(name);
				tradeInfo.setPhone(phone);
				tradeInfo.setCount(count);
				tradeInfo.setOutTradeNo(prePayReq.getOut_trade_no());
				tradeInfo.setTotalFee(prePayReq.getTotal_fee() / 100f);
				tradeInfo.setCreateTime(new Date());
				tradeInfo.setProductId(product.getId());
				tradeInfo.setOpenId(openid);
				tradeInfo.setPrePayId(response.getString("prepay_id"));
				if (tfAndlv.isUseLuckyVoucher()) {
					tradeInfo.setLuckyVoucherId(tfAndlv.getLuckyVoucherId());
				}
				tradeDao.save(tradeInfo);
				int tradeId = tradeInfo.getId();
				prePayResp.setTradeId(tradeId);
				// prePayResp.setCodeUrl(response.getString("code_url"));//trade_type为NATIVE是有返回
			} else {
				prePayResp.setErrCode(response.getString("err_code"));
				prePayResp.setErrCodeDes(response.getString("err_code_des"));
			}

		} else {// 与微信服务器通信失败
			String returnMsg = response.getString("return_msg");
			prePayResp.setReturnMsg(returnMsg);
			System.out.println("调用统一下单接口通信失败错误原因：" + returnMsg);
			log.info("调用统一下单接口通信失败错误原因：" + returnMsg);
			throw new RuntimeException(returnMsg);
		}

		return prePayResp;
	}

	private TotalFeeAndLuckyVoucher getFactTotalFee(float price, int count,
			String openid) {
		TotalFeeAndLuckyVoucher tfAndlv = new TotalFeeAndLuckyVoucher();
		float totalFee = price * count;
		float factFee = 0f;
		tfAndlv.setTotalFee(totalFee);
		Map<String, Float> params = baseDataService.queryLuckyVoucherParams();
		int luckyInTime = params.get(
				LuckyVoucherParamEnum.LUCKY_IN_TIME.getContent()).intValue();
		String startTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(-luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		String endTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		List<LvAndLvtBo> lst = luckyDao.queryLuckyVouchersByOpenId(openid,
				startTime, endTime);
		if (CollectionUtils.isNotEmpty(lst)) {
			LvAndLvtBo bo = lst.get(0);
			LuckyVoucherType lvt = luckyDao.queryLuckyVoucherById(bo
					.getLuckVoucherTypeId());
			if (lvt.getLuckyVoucherType() == 1) {// 打折券
				float discountRate = lvt.getDiscountRate() / 100f;
				factFee = (price * (count - 1) + price * discountRate);
			} else if (lvt.getLuckyVoucherType() == 2) {// 现金券
				factFee -= lvt.getVoucherTotal();
			} else if (lvt.getLuckyVoucherType() == 3) {// 几免几优惠券
				int sub = count / lvt.getTicketTotal();
				factFee = price * (count - sub);
			}
			tfAndlv.setFactFee(factFee);
			tfAndlv.setLuckyVoucherId(bo.getLuckVoucherId());
			tfAndlv.setLuckyVoucherTypeId(bo.getLuckVoucherTypeId());
		}
		if (tfAndlv.getTotalFee() != tfAndlv.getFactFee()) {
			tfAndlv.setUseLuckyVoucher(true);
		} else {
			tfAndlv.setUseLuckyVoucher(false);
		}
		// 单位是角，乘以100转为元
		return tfAndlv;
	}

	/**
	 * 获取签名参数
	 * 
	 * @param prePayReq
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String getSign(Object obj) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Field[] field = obj.getClass().getDeclaredFields();
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < field.length; i++) {
			String name = field[i].getName();
			String fname = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			fname = "get" + fname;
			String type = field[i].getGenericType().toString();
			String value;
			if (type.equals("class java.lang.Integer")) {
				value = ((Integer) obj.getClass().getMethod(fname).invoke(obj))
						.toString();
			} else if (type.equals("class java.lang.Float")) {
				value = ((Float) obj.getClass().getMethod(fname).invoke(obj))
						.toString();
			} else {
				value = (String) obj.getClass().getMethod(fname).invoke(obj);
			}
			if (StringUtils.isNotEmpty(value)) {
				if (name.equals("wxPackage")) {
					name = "package";
				}
				String str = name + "=" + value;
				lst.add(str);
			}
		}
		Collections.sort(lst);
		StringBuffer sb = new StringBuffer();
		for (String s : lst) {
			sb.append(s);
			sb.append("&");
		}
		// sb.deleteCharAt(sb.length() - 1);
		sb.append("key=" + Constants.api_secret_key);
		String sign = "";
		try {
			sign = MD5Util.MD5(sb.toString()).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(new Date() + ",pay生成sign,MD5过程失败!");
		}
		return sign;
	}

	public String getPayCodeUrl(String returnUrl) {
		String getCodeUrl = Constants.getCodeUrl;
		getCodeUrl = getCodeUrl.replace("APPID",
				CommonUtil.urlEnodeUTF8(Constants.appId));
		getCodeUrl = getCodeUrl.replace("REDIRECT_URI",
				CommonUtil.urlEnodeUTF8(returnUrl));

		// getCodeUrl = getCodeUrl.replace("REDIRECT_URI",
		// Constants.OauRedirectUrl);

		getCodeUrl = getCodeUrl.replace("SCOPE", Constants.OAuthScopeBasic);
		// getCodeUrl = getCodeUrl.replace("STATE", "pay");
		// WeixinUtil.httpRequest(getCodeUrl, "POST", null);
		return getCodeUrl;
	}

	@Override
	public String getOpenid(String code) {
		String url = Constants.getOpenidAndAccessCode;
		url = url.replace("APPID", CommonUtil.urlEnodeUTF8(Constants.appId));
		url = url.replace("SECRET",
				CommonUtil.urlEnodeUTF8(Constants.appSecret));
		url = url.replace("CODE", CommonUtil.urlEnodeUTF8(code));
		JSONObject obj = WeixinUtil.httpRequest(url, "POST", null);
		if (obj == null)
			return null;
		String openid = obj.getString("openid");
		return openid;
	}

	public static void main(String args[]) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			UnsupportedEncodingException {
		PayService service = new PayService();
		PrePayReq prePayReq = new PrePayReq();
		prePayReq.setAppid(Constants.appId);
		prePayReq.setMch_id(Constants.mch_id);
		prePayReq.setDevice_info("WEB");
		prePayReq.setNonce_str(WeixinUtil.getRandomString(32));
		String body = "test";
		prePayReq.setBody(body);

		// String sign = service.getSign(prePayReq);
		// System.out.println(sign);
		// System.out.println(MD5Util.MD5(sign).toUpperCase());

		String prePayReqXml = CommonUtil.objToXml(prePayReq);
		System.out.println(prePayReqXml);
	}

	@Override
	public TradeInfo getTradeInfoById(Integer id) {
		return tradeDao.findById(id);
	}

	@Override
	public TradeInfo getTradeInfoByOutTradeNo(String outTradeNo) {
		return tradeDao.findByOutTradeNo(outTradeNo);
	}

	@Override
	public void wxPayNotify(HttpServletRequest request,
			HttpServletResponse response) {
		BufferedReader br = null;
		try {
			br = request.getReader();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String str, wholeStr = "";
		try {
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> map = CommonUtil.parseXml(wholeStr);
		String returnCode = map.get("return_code");// 此字段是通信标识，非交易标识
		if (Constants.success.equals(returnCode)) {
			String result_code = map.get("result_code");// 业务结果
			String out_trade_no = map.get("out_trade_no");// 商户订单号
			PayResp payResp = new PayResp();
			payResp.setOut_trade_no(out_trade_no);
			if (Constants.success.equals(result_code)) {// 支付成功
				payResp.setState(1);
			} else {// 支付失败
				payResp.setState(2);
				payResp.setErr_code(map.get("err_code"));
				payResp.setErr_code_des(map.get("err_code_des"));
			}
			String timeEnd = map.get("time_end");
			payResp.setDevice_info(map.get("device_info"));
			payResp.setTime_end(timeEnd);
			payResp.setTransaction_id(map.get("transaction_id"));
			tradeDao.updatePayResp(payResp);
			if (payResp.getState() == 1) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("return_code", "SUCCESS");
				map1.put("return_msg", "OK");
				String xml = CommonUtil.mapToXml(map1);
				response.setContentType("text/xml;charset=UTF-8");
				OutputStream out;
				try {
					out = response.getOutputStream();
					out.write(xml.getBytes("UTF-8"));
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {// 返回信息，如非空，为错误原因
			String returnMsg = map.get("return_msg");
			log.info("支付失败，原因：" + returnMsg);
		}
	}

	@Override
	public boolean updatePayState(TradeInfo tradeInfo) {
		return tradeDao.updatePayState(tradeInfo);
	}

	@Override
	public List<TradeInfo> getTradeInfoByOpenid(String openid) {
		return tradeDao.getTradeInfoByOpenid(openid);
	}

	@Override
	public List<TradeInfo> getTradeInfoByCondition(TradeInfoCondition condition) {
		int page = condition.getPage();
		int rows = condition.getRows();
		page = page < 1 ? 1 : page;
		rows = rows < 1 ? cn.plxpl.constant.Constants.pageSize : rows;
		int startRow = (page - 1) * rows;
		condition.setStartRow(startRow);
		condition.setRows(rows);
		return tradeDao.getTradeInfoByCondition(condition);
	}

	@Override
	public Integer getTradeInfosTotalByCondition(TradeInfoCondition condition) {
		return tradeDao.getTradeInfosTotalByCondition(condition);
	}

	/**
	 * 消息转发到微信客服系统
	 */
	@Override
	public void wxCustomerService(HttpServletRequest request,
			HttpServletResponse response, String openid) {
		try {
			String touser = openid;
			String fromuser = Constants.originalId;
			Map<String, String> map = new HashMap<String, String>();
			map.put("ToUserName", touser);
			map.put("FromUserName", fromuser);
			map.put("CreateTime", String.valueOf(System.currentTimeMillis()));
			map.put("MsgType", "transfer_customer_service");
			String xml = CommonUtil.mapToXml(map);
			response.setContentType("text/xml;charset=UTF-8");
			OutputStream out;
			try {
				out = response.getOutputStream();
				out.write(xml.getBytes("UTF-8"));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			log.info("消息转发到微信客服系统失败：" + ex.getMessage());
		}
	}
}
