package cn.plxpl.wx.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.plxpl.wx.entity.AccessToken;
import cn.plxpl.wx.entity.JsApiTicket;
import cn.plxpl.wx.util.WeixinUtil;

public class TokenThread implements Runnable {

	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = "wx2ed20c1fa7ad5f75";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "fa579e6c065064a4aba679a63a4cb3de";
	public static AccessToken accessToken = null;
	// 公众号用于调用微信JS接口的临时票据
	public static JsApiTicket jsApiTicket = null;

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static JsApiTicket getJsApiTicket() {
		return jsApiTicket;
	}

	public void run() {
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if (null != accessToken) {
					jsApiTicket = WeixinUtil.getJsApiTicket(accessToken);
					log.info("获取access_token成功，有效时长{}秒 token:{}",
							accessToken.getExpiresIn(), accessToken.getToken());
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}
