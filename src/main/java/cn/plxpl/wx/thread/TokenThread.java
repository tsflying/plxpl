package cn.plxpl.wx.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.plxpl.wx.entity.AccessToken;
import cn.plxpl.wx.entity.JsApiTicket;
import cn.plxpl.wx.util.WeixinUtil;

public class TokenThread implements Runnable {

	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// �������û�Ψһƾ֤
	public static String appid = "wx2ed20c1fa7ad5f75";
	// �������û�Ψһƾ֤��Կ
	public static String appsecret = "fa579e6c065064a4aba679a63a4cb3de";
	public static AccessToken accessToken = null;
	// ���ں����ڵ���΢��JS�ӿڵ���ʱƱ��
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
					log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}",
							accessToken.getExpiresIn(), accessToken.getToken());
					// ����7000��
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// ���access_tokenΪnull��60����ٻ�ȡ
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
