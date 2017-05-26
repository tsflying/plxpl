package cn.plxpl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.plxpl.entity.DataGrid;
import cn.plxpl.entity.ReturnResult;
import cn.plxpl.utils.CommonUtils;
import cn.plxpl.utils.DateUtils;
import cn.plxpl.wx.condition.TradeInfoCondition;
import cn.plxpl.wx.entity.Product;
import cn.plxpl.wx.entity.TradeInfo;
import cn.plxpl.wx.entity.vo.LuckyVoucherInfoVo;
import cn.plxpl.wx.enums.LuckyVoucherParamEnum;
import cn.plxpl.wx.pay.req.WxJsPayRequest;
import cn.plxpl.wx.pay.resp.PrePayResp;
import cn.plxpl.wx.service.IBaseDataService;
import cn.plxpl.wx.service.ICoreService;
import cn.plxpl.wx.service.ILuckyService;
import cn.plxpl.wx.service.IPayService;
import cn.plxpl.wx.service.IProductService;
import cn.plxpl.wx.thread.TokenThread;
import cn.plxpl.wx.util.CommonUtil;
import cn.plxpl.wx.util.Constants;
import cn.plxpl.wx.util.MD5Util;
import cn.plxpl.wx.util.SignUtil;
import cn.plxpl.wx.util.WeixinUtil;

@SuppressWarnings("restriction")
@Controller
public class WxAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6490403831070754931L;
	private static Logger log = LoggerFactory.getLogger(WxAction.class);

	@Resource
	private ICoreService coreService;

	@Resource
	private IPayService payService;

	@Resource
	IProductService productService;

	@Resource
	IBaseDataService baseDataService;

	@Resource
	ILuckyService luckyService;

	@RequestMapping(value = "/coreServlet", method = RequestMethod.POST)
	public void coreServletPost(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		// 调用核心业务类接收消息、处理消息
		String respMessage = coreService.processRequest(request, session);

		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	// 开发者提交服务器的URL和token信息后，微信服务器将发送GET请求过来,
	// 需要验证消息的确来自微信服务器，然后接入微信服务器
	@RequestMapping(value = "/coreServlet", method = RequestMethod.GET)
	public void coreServletGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	// 服务启动时候执行，每隔一段时间获取accessToken(accessToken有效期是2个小时)
	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() {
		// 获取web.xml中配置的参数
		// TokenThread.appid = getInitParameter("appid");
		// TokenThread.appsecret = getInitParameter("appsecret");

		log.info("weixin api appid:{}", TokenThread.appid);
		log.info("weixin api appsecret:{}", TokenThread.appsecret);

		// 未配置appid、appsecret时给出提示
		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// 启动定时获取access_token的线程
			new Thread(new TokenThread()).start();
		}
	}

	@RequestMapping(value = "/wxIndex")
	public ModelAndView wxIndex() {
		ModelAndView modelView = new ModelAndView("wap/index");
		return modelView;
	}

	@RequestMapping(value = "/wxIntrodection")
	public ModelAndView wxIntrodection() {
		ModelAndView modelView = new ModelAndView("wap/introduction");
		return modelView;
	}

	@RequestMapping(value = "/wxNotice")
	public ModelAndView wxNotice() {
		ModelAndView modelView = new ModelAndView("wap/notice");
		return modelView;
	}

	@RequestMapping(value = "/wxScenery")
	public ModelAndView wxScenery() {
		ModelAndView modelView = new ModelAndView("wap/scenery");
		return modelView;
	}

	@RequestMapping(value = "/wxVideo")
	public ModelAndView wxVideo() {
		ModelAndView modelView = new ModelAndView("wap/video");
		return modelView;
	}

	@RequestMapping(value = "/wxTrafficMap")
	public ModelAndView wxTrafficMap() {
		ModelAndView modelView = new ModelAndView("wap/trafficMap");
		return modelView;
	}

	@RequestMapping(value = "/wxNavigation")
	public ModelAndView wxNavigation() {
		ModelAndView modelView = new ModelAndView("wap/navigation");
		return modelView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/wxLucky")
	public ModelAndView wxLucky(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView modelView = null;
		Map map = new HashMap();
		String openid = (String) session.getAttribute("openid");
		String code = request.getParameter("code");
		Map<String, Float> params = baseDataService.queryLuckyVoucherParams();
		map.put(LuckyVoucherParamEnum.FREQUENCY_PER_DAY.getContent(), params
				.get(LuckyVoucherParamEnum.FREQUENCY_PER_DAY.getContent())
				.intValue());
		int termOfValid = params.get(
				LuckyVoucherParamEnum.TERM_OF_VALID.getContent()).intValue();
		map.put(LuckyVoucherParamEnum.TERM_OF_VALID.getContent(), termOfValid);
		int luckyInTime = params.get(
				LuckyVoucherParamEnum.LUCKY_IN_TIME.getContent()).intValue();
		map.put(LuckyVoucherParamEnum.LUCKY_IN_TIME.getContent(), luckyInTime);
		map.put(LuckyVoucherParamEnum.LUCKY_RATE.getContent(),
				(int) (params.get(LuckyVoucherParamEnum.LUCKY_RATE.getContent()) * 100)
						+ "%");
		if (StringUtils.isEmpty(openid)) {
			if (StringUtils.isNotBlank(code)) {
				openid = baseDataService.getWxOpenId(code);
				session.setAttribute("openid", openid);
				map.put("openid", openid);
				if (StringUtils.isNotBlank(openid)) {
					LuckyVoucherInfoVo lvt = luckyService
							.queryLuckyVoucherByOpenId(openid, termOfValid,
									termOfValid);
					if (lvt != null) {
						map.put("haveLuckyVoucher", true);
						map.put("luckyVoucher", lvt);
					} else {
						map.put("haveLuckyVoucher", false);
					}
				}
				modelView = new ModelAndView("wap/lucky", map);
			} else {// 先获取code，再获取openId
				String getWxCodeUrl = baseDataService
						.getWxCodeUrl(Constants.OauRedirectUrlLuckyDraw);
				map.put("redirectUrl", getWxCodeUrl);
				modelView = new ModelAndView("wap/prePayGetCode", map);
			}
			return modelView;
		}
		if (StringUtils.isNotBlank(openid)) {
			LuckyVoucherInfoVo lvt = luckyService.queryLuckyVoucherByOpenId(
					openid, termOfValid, termOfValid);
			if (lvt != null) {
				map.put("haveLuckyVoucher", true);
				map.put("luckyVoucher", lvt);
			} else {
				map.put("haveLuckyVoucher", false);
			}
		}
		modelView = new ModelAndView("wap/lucky", map);
		modelView = new ModelAndView("wap/lucky", map);
		return modelView;
	}

	@RequestMapping(value = "/wxTicketGroup")
	public ModelAndView wxTicketGroup() {
		List<Product> produsts = productService.getAllProducts();
		Map map = new HashMap();
		map.put("products", produsts);
		ModelAndView modelView = new ModelAndView("wap/ticketGroup", map);
		return modelView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/wxGetPrePayCode")
	public ModelAndView wxGetPrePayCode(HttpServletRequest request,
			HttpSession session) {

		Map map = new HashMap();
		ModelAndView modelView = new ModelAndView("wap/prePay", map);
		return modelView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/wxPrePay")
	public ModelAndView wxPrePay(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Map map = new HashMap();
		String productIdStr = request.getParameter("type");
		if (StringUtils.isEmpty(productIdStr))
			productIdStr = request.getParameter("state").toString();
		if (StringUtils.isEmpty(productIdStr)) {
			map.put("error", "未传递productId!");
			return new ModelAndView("wap/error", map);
		}

		String payCodeUrl = payService.getPayCodeUrl(Constants.OauRedirectUrl);
		payCodeUrl = payCodeUrl.replace("STATE", productIdStr);
		map.put("redirectUrl", payCodeUrl);
		String openid = (String) session.getAttribute("openid");
		if (StringUtils.isNotEmpty(openid)) {
			map.put("openid", openid);
		}

		Product product = productService.getProductById(Integer
				.valueOf(productIdStr));
		map.put("product", product);
		String code = request.getParameter("code");
		if (StringUtils.isNotEmpty(code)) {
			map.put("code", code);
		} else {
			ModelAndView modelView = new ModelAndView("wap/prePayGetCode", map);
			return modelView;
		}

		if (StringUtils.isNotEmpty(code)) {
			openid = payService.getOpenid(code);
			session.setAttribute("openid", openid);
		}
		LuckyVoucherInfoVo luckyVoucherInfoVo = luckyService
				.queryLuckyVoucherByOpenId(openid);
		if (luckyVoucherInfoVo != null) {
			map.put("hasLuckyVoucher", true);
		}
		map.put("luckyVoucherInfoVo", luckyVoucherInfoVo);
		ModelAndView modelView = new ModelAndView("wap/prePay", map);
		return modelView;
	}

	@RequestMapping(value = "/pay/prePay")
	@ResponseBody
	public ReturnResult prePay(HttpServletRequest request, HttpSession session)
			throws UnsupportedEncodingException {
		ReturnResult returnResult = new ReturnResult();
		returnResult.setIsSuccess(false);

		String openid = (String) session.getAttribute("openid");
		if (StringUtils.isEmpty(openid)) {
			String code = request.getParameter("code");
			if (StringUtils.isNotEmpty(code)) {
				openid = payService.getOpenid(code);
				session.setAttribute("openid", openid);
			} else {
				returnResult.setError("未获取code!");
				log.info("prePay 未获取code!");
				return returnResult;
			}
		}
		String name = (String) request.getParameter("name");
		String phone = (String) request.getParameter("phone");
		Integer productId = Integer.valueOf(request.getParameter("productId")
				.toString());
		Integer cnt = Integer.valueOf(request.getParameter("count"));
		Product product = productService.getProductById(productId);
		String spbillCreateIp = CommonUtils.getIpAddr(request);
		try {
			PrePayResp prePayResp = payService.prePay(product, cnt,
					spbillCreateIp, openid, name, phone);
			WxJsPayRequest wxJsPayRequest = new WxJsPayRequest();
			wxJsPayRequest.setAppId(prePayResp.getAppid());
			wxJsPayRequest.setTimeStamp(String.valueOf(System
					.currentTimeMillis() / 1000));
			wxJsPayRequest.setNonceStr(WeixinUtil.getRandomString(32));
			wxJsPayRequest
					.setWxPackage("prepay_id=" + prePayResp.getPrepayId());
			wxJsPayRequest.setSignType("MD5");
			String paySign = payService.getSign(wxJsPayRequest);
			wxJsPayRequest.setPaySign(paySign);

			returnResult.setIsSuccess(true);
			returnResult.setObj(wxJsPayRequest);
			returnResult.setObj1(prePayResp.getTradeId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			returnResult.setIsSuccess(false);
			returnResult.setError(e.getMessage());
		}
		return returnResult;
	}

	/**
	 * 接收微信支付异步通知回调地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wxPayNotify")
	public void wxPayNotify(HttpServletRequest request,
			HttpServletResponse response) {
		payService.wxPayNotify(request, response);
	}

	/**
	 * 查询订单，并且回写相关状态
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/pay/queryTrade")
	public ModelAndView queryTrade(HttpServletRequest request,
			HttpSession session) {
		Integer tradeId = Integer.valueOf(request.getParameter("tradeId"));
		String payed = request.getParameter("payed");
		if ("true".equals(payed)) {
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setState(1);
			tradeInfo.setId(tradeId);
			tradeInfo.setUpdateTime(new Date());
			payService.updatePayState(tradeInfo);
			String openid = (String) session.getAttribute("openid");
			if (StringUtils.isNotBlank(openid)) {
				TradeInfo ti = payService.getTradeInfoById(tradeId);
				if (ti != null && ti.getLuckyVoucherId() != null) {
					int luckyVoucherId = ti.getLuckyVoucherId();
					if (luckyVoucherId > 0) {
						boolean isUsed = luckyService
								.setLuckyVoucherUsed(luckyVoucherId);
						log.info("设置兑奖券ID为：" + luckyVoucherId + "状态为已使用成功--",
								isUsed);
					}
				}
			}
		}
		TradeInfo tradeInfo = payService.getTradeInfoById(tradeId);
		Map map = new HashMap();
		map.put("tradeInfo", tradeInfo);
		ModelAndView mv = new ModelAndView("wap/showTrade", map);
		return mv;
	}

	/**
	 * 查询订单
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/wxQueryAllTrades")
	public ModelAndView wxQueryAllTrades(HttpServletRequest request,
			HttpSession session) {
		String openid = (String) session.getAttribute("openid");
		Map map = new HashMap();
		if (StringUtils.isEmpty(openid)) {
			String payCodeUrl = payService
					.getPayCodeUrl(Constants.OauRedirectUrlGetCode);
			map.put("redirectUrl", payCodeUrl);
			ModelAndView modelView = new ModelAndView("wap/prePayGetCode", map);
			return modelView;
		} else {// 查询该opendId下的交易信息
			List<TradeInfo> tradeInfos = payService
					.getTradeInfoByOpenid(openid);
			map.put("tradeInfos", tradeInfos);
			if (tradeInfos.size() == 0) {
				map.put("noTickets", "yes");
			}
		}
		ModelAndView mv = new ModelAndView("wap/showAllTrades", map);
		return mv;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/wxPrePayGetCode")
	public ModelAndView wxPrePayGetCode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		Map map = new HashMap();
		String openid;
		if (StringUtils.isEmpty(code)) {
			map.put("error", "未传递productId!");
			return new ModelAndView("wap/error", map);
		} else {
			openid = payService.getOpenid(code);
			session.setAttribute("openid", openid);
			List<TradeInfo> tradeInfos = payService
					.getTradeInfoByOpenid(openid);
			map.put("tradeInfos", tradeInfos);
			if (tradeInfos.size() == 0) {
				map.put("noTickets", "yes");
			}
		}
		ModelAndView mv = new ModelAndView("wap/showAllTrades", map);
		return mv;
	}

	/**
	 * 后台查询页面
	 */
	@RequestMapping(value = "/wxQueryOrders")
	public ModelAndView wxQueryOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("wap/wxQueryOrders");
		return mv;
	}

	@RequestMapping(value = "/queryTickes")
	@ResponseBody
	public String queryTickes(HttpServletRequest request,
			HttpServletResponse response) {
		// Map<String, Object> result = new HashMap<String, Object>(2);
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		DataGrid dataGrid = new DataGrid();
		JSONArray rows1 = new JSONArray();
		TradeInfoCondition condition = new TradeInfoCondition();
		condition.setName(name);
		condition.setPhone(phone);
		condition.setRows(Integer.valueOf(rows));
		condition.setPage(Integer.valueOf(page));
		if (StringUtils.isNotEmpty(startTime))
			condition.setStartTime(DateUtils.strToDate(startTime));
		if (StringUtils.isNotEmpty(endTime))
			condition.setEndTime(DateUtils.strToDate(endTime));
		// condition.setStartTime(startTime);
		// condition.setEndTime(endTime);
		List<TradeInfo> tradeInfos = payService
				.getTradeInfoByCondition(condition);
		rows1 = JSONArray.fromObject(tradeInfos);
		int totalSize = payService.getTradeInfosTotalByCondition(condition);
		dataGrid.setRows(rows1);
		dataGrid.setTotal(totalSize);
		JSONObject jsonObject = JSONObject.fromObject(dataGrid);
		// result.put("total", totalSize);
		// result.put("rows", rows1);
		return jsonObject.toString();
		// return result;
	}

	@RequestMapping(value = "/pay/getTicket")
	@ResponseBody
	public ReturnResult getTicket(HttpServletRequest request,
			HttpSession session) throws UnsupportedEncodingException {
		ReturnResult returnResult = new ReturnResult();
		returnResult.setIsSuccess(false);

		String tradeInfoId = request.getParameter("tradeInfoId");

		TradeInfo tradeInfo = new TradeInfo();
		tradeInfo.setId(Integer.valueOf(tradeInfoId));
		tradeInfo.setState(3);
		Boolean re = payService.updatePayState(tradeInfo);
		if (re) {
			returnResult.setIsSuccess(true);
		}

		return returnResult;
	}

	/**
	 * wifiPortal
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/wifiPortal")
	public ModelAndView wifiPortal(HttpServletRequest request)
			throws UnsupportedEncodingException {
		Map map = new HashMap();
		String appId = Constants.wifiAppId;
		map.put("appId", appId);
		String extend = "";
		map.put("extend", extend);
		String timestamp = String.valueOf(System.currentTimeMillis());
		map.put("timestamp", timestamp);
		String shopId = Constants.wifiShopID;
		map.put("shop_id", shopId);
		String authUrl = "";
		map.put("authUrl", authUrl);
		String ip = CommonUtil.getIpAddr(request);
		String mac = CommonUtil.getMACAddress(ip);
		map.put("mac", mac);
		String ssid = Constants.wifiSSID;
		map.put("ssid", Constants.wifiSSID);
		String secretkey = Constants.wifiSecretKey;
		String sign = MD5Util.MD5(appId + extend + timestamp + shopId + authUrl
				+ mac + ssid + secretkey);
		map.put("sign", sign);
		ModelAndView mv = new ModelAndView("wap/wifiPortal", map);
		return mv;
	}

	/**
	 * test
	 */
	@RequestMapping(value = "/wxtest")
	public ModelAndView wxtest(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("wap/wxtest");
		String id = request.getParameter("id");
		boolean isUsed = luckyService.setLuckyVoucherUsed(67);
		System.out.println(isUsed);
		return mv;
	}
}
