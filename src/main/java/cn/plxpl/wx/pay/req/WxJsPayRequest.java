package cn.plxpl.wx.pay.req;

public class WxJsPayRequest {

	private String appId;
	private String timeStamp;
	private String nonceStr;// 随机字符串
	private String wxPackage;// 订单详情扩展字符串
	private String signType;// 签名方式
	private String paySign;// 签名

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getWxPackage() {
		return wxPackage;
	}

	public void setWxPackage(String wxPackage) {
		this.wxPackage = wxPackage;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

}
