package cn.plxpl.wx.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.plxpl.wx.condition.TradeInfoCondition;
import cn.plxpl.wx.entity.Product;
import cn.plxpl.wx.entity.TradeInfo;
import cn.plxpl.wx.pay.resp.PrePayResp;

public interface IPayService {
	public PrePayResp prePay(Product product, Integer count,
			String spbillCreateIp, String openid, String name, String phone)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, UnsupportedEncodingException;

	public String getPayCodeUrl(String returnUrl);

	public String getOpenid(String code);

	public String getSign(Object prePayReq) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException;

	public TradeInfo getTradeInfoById(Integer id);

	public TradeInfo getTradeInfoByOutTradeNo(String outTradeNo);

	public void wxPayNotify(HttpServletRequest request,
			HttpServletResponse response);

	boolean updatePayState(TradeInfo tradeInfo);

	List<TradeInfo> getTradeInfoByOpenid(String openid);

	List<TradeInfo> getTradeInfoByCondition(TradeInfoCondition conditon);

	public Integer getTradeInfosTotalByCondition(TradeInfoCondition condition);

	public void wxCustomerService(HttpServletRequest request,
			HttpServletResponse response, String openid);

}
