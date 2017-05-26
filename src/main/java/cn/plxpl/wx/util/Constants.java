package cn.plxpl.wx.util;

public class Constants {

	// 公众号原始ID
	public static String originalId = "gh_7c6a4eaa152a";
	// *************************************测试环境*************************************
	// 第三方用户唯一凭证
	// public static String appId = "wx2ed20c1fa7ad5f75";
	// 第三方用户唯一凭证密钥
	// public static String appSecret = "fa579e6c065064a4aba679a63a4cb3de";
	// *************************************测试环境*************************************

	// //
	// *************************************线上环境*************************************
	// // 第三方用户唯一凭证
	public static String appId = "wx95040da38fc72d8e";
	// // 第三方用户唯一凭证密钥
	public static String appSecret = "6293a125f190dafdd19fc42230f89396";
	// //
	// *************************************线上环境*************************************

	// 微信支付商号
	public static String mch_id = "1259469201";

	// 微信api秘钥
	public static String api_secret_key = "7ondrp6xukl5uh6k1x61k7bce7hhxty6";

	// 微信支付统一下单接口
	public static String UnifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 设备号,终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	public static String device_info = "WEB";

	// 接收微信支付异步通知回调地址
	// public static String notify_url = "http://www.plxpl.cn/wxPayNotify.do";
	// 接收微信支付异步通知回调地址(测试)
	public static String notify_url = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPayNotify.do";

	public static String JSAPI = "JSAPI";

	// 获取js接口临时票据url
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

	// ///////////// OAuth2.0 用户授权认证//////////////////////
	// 获取用户认证前的code
	public static String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	//
	public static String getOpenidAndAccessCode = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 授权后重定向的回调链接地址,使用urlencode对链接进行处理
	// 线上
	// public static String OauRedirectUrl =
	// "http://www.plxpl.cn/wxPrePay.do";

	// 本地测试环境
	public static String OauRedirectUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPrePay.do";

	public static String OauRedirectUrlGetCode = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxPrePayGetCode.do";

	public static String OauRedirectUrlLuckyDraw = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxLucky.do";

	// 线上环境
	// public static String OauRedirectUrl =
	// "http://www.plxpl.cn/plxpl/wxPrePay.do";
	//
	// public static String OauRedirectUrlGetCode =
	// "http://www.plxpl.cn/plxpl/wxPrePayGetCode.do";

	// 不弹出授权页面，直接跳转，只能获取用户openid
	public static String OAuthScopeBasic = "snsapi_base";

	// 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
	public static String OAuthScopeBasec = "snsapi_userinfo";

	// 获取code后，请求以下链接获取access_token(与基础支持中的access_token不同)
	public static String getOAuthAccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// ///////////// OAuth2.0 用户授权认证////////////////////

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

	// 本地测试环境,获取网页授权之前获取code，微信服务器回调
	public static String customerServcieReturnUrl = "http://v3xlra79dd.proxy.qqbrowser.cc/plxpl/wxCustomerService.do";

	// //////////////////////WI-FI///////////////////////////
	public static String wifiSSID = "plxpl";
	public static String wifiShopID = "2472161";
	public static String wifiAppId = "wx95040da38fc72d8e";
	public static String wifiSecretKey = "90c81c94328104c612636a2b12204b7e";
}
