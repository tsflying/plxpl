package cn.plxpl.wx.util;

public class Constants {

	// ���ں�ԭʼID
	public static String originalId = "gh_7c6a4eaa152a";
	// *************************************���Ի���*************************************
	// �������û�Ψһƾ֤
	// public static String appId = "wx2ed20c1fa7ad5f75";
	// �������û�Ψһƾ֤��Կ
	// public static String appSecret = "fa579e6c065064a4aba679a63a4cb3de";
	// *************************************���Ի���*************************************

	// //
	// *************************************���ϻ���*************************************
	// // �������û�Ψһƾ֤
	public static String appId = "wx95040da38fc72d8e";
	// // �������û�Ψһƾ֤��Կ
	public static String appSecret = "6293a125f190dafdd19fc42230f89396";
	// //
	// *************************************���ϻ���*************************************

	// ΢��֧���̺�
	public static String mch_id = "1259469201";

	// ΢��api��Կ
	public static String api_secret_key = "7ondrp6xukl5uh6k1x61k7bce7hhxty6";

	// ΢��֧��ͳһ�µ��ӿ�
	public static String UnifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// �豸��,�ն��豸��(�ŵ�Ż������豸ID)��ע�⣺PC��ҳ���ں���֧���봫"WEB"
	public static String device_info = "WEB";

	// ����΢��֧���첽֪ͨ�ص���ַ
	// public static String notify_url = "http://www.plxpl.cn/wxPayNotify.do";
	// ����΢��֧���첽֪ͨ�ص���ַ(����)
	public static String notify_url = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPayNotify.do";

	public static String JSAPI = "JSAPI";

	// ��ȡjs�ӿ���ʱƱ��url
	public static String JsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	public static String DateFormate = "yyyy-MM-dd HH:mm:ss";

	// public static String WebUrl = "http://www.plxpl.cn/WX/index.html";

	public static String WebUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxIndex.do";
	// public static String WebUrl = "http://www.plxpl.cn/plxpl/wxIndex.do";

	public static String SceneryUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxScenery.do";
	// public static String SceneryUrl =
	// "http://www.plxpl.cn/plxpl/wxScenery.do";

	public static String TripMap = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxNavigation.do";
	// public static String TripMap =
	// "http://www.plxpl.cn/plxpl/wxNavigation.do";

	public static String VideoUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxVideo.do";
	// public static String VideoUrl = "http://www.plxpl.cn/plxpl/wxVideo.do";

	public static String success = "SUCCESS";

	public static String fail = "FAIL";

	// ///////////// OAuth2.0 �û���Ȩ��֤//////////////////////
	// ��ȡ�û���֤ǰ��code
	public static String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	//
	public static String getOpenidAndAccessCode = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// ��Ȩ���ض���Ļص����ӵ�ַ,ʹ��urlencode�����ӽ��д���
	// ����
	// public static String OauRedirectUrl =
	// "http://www.plxpl.cn/wxPrePay.do";

	// ���ز��Ի���
	public static String OauRedirectUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPrePay.do";

	public static String OauRedirectUrlGetCode = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPrePayGetCode.do";

	public static String OauRedirectUrlLuckyDraw = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxLucky.do";

	// ���ϻ���
	// public static String OauRedirectUrl =
	// "http://www.plxpl.cn/plxpl/wxPrePay.do";
	//
	// public static String OauRedirectUrlGetCode =
	// "http://www.plxpl.cn/plxpl/wxPrePayGetCode.do";

	// ��������Ȩҳ�棬ֱ����ת��ֻ�ܻ�ȡ�û�openid
	public static String OAuthScopeBasic = "snsapi_base";

	// ������Ȩҳ�棬��ͨ��openid�õ��ǳơ��Ա����ڵء����ң���ʹ��δ��ע������£�ֻҪ�û���Ȩ��Ҳ�ܻ�ȡ����Ϣ
	public static String OAuthScopeBasec = "snsapi_userinfo";

	// ��ȡcode�������������ӻ�ȡaccess_token(�����֧���е�access_token��ͬ)
	public static String getOAuthAccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// ///////////// OAuth2.0 �û���Ȩ��֤////////////////////

	// /////////////////////////////////////////////
	public static String itrPicUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/images/wap/index/wxtextimg.jpg";
	// public static String itrPicUrl =
	// "http://www.plxpl.cn/plxpl/images/wap/index/wxtextimg.jpg";

	public static String trafficMapUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/images/trafficMap.jpg";
	// public static String trafficMapUrl =
	// "http://www.plxpl.cn/plxpl/images/trafficMap.jpg";

	public static String customerServiceUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/coreServlet.do";
	// public static String customerServiceUrl =
	// "http://www.plxpl.cn/plxpl/wxCustomerService.do";

	// ���ز��Ի���,��ȡ��ҳ��Ȩ֮ǰ��ȡcode��΢�ŷ������ص�
	public static String customerServcieReturnUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxCustomerService.do";

	// //////////////////////WI-FI///////////////////////////
	public static String wifiSSID = "plxpl";
	public static String wifiShopID = "2472161";
	public static String wifiAppId = "wx95040da38fc72d8e";
	public static String wifiSecretKey = "90c81c94328104c612636a2b12204b7e";
}
