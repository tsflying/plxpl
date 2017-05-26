package cn.plxpl.wx.service;

import java.util.Map;

public interface IBaseDataService {

	public Map<String, Float> queryLuckyVoucherParams();

	/**
	 * 获取用户认证前的code请求URL
	 * 
	 * @param returnUrl
	 *            微信服务器回调地址，返回code值
	 * @return
	 */
	public String getWxCodeUrl(String returnUrl);

	/**
	 * 获取用户Openid
	 * 
	 * @param code
	 * @return
	 */
	public String getWxOpenId(String code);
}
