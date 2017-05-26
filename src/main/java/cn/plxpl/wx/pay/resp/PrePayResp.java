package cn.plxpl.wx.pay.resp;

public class PrePayResp {

	private String returnCode;// 此字段是通信标识，非交易标识
	private String returnMsg;// 返回信息，如非空，为错误原因
	private String appid;// 公众账号ID
	private String mchId;// 商户ID
	private String deviceInfo;// 设备号
	private String nonceStr;// 随机字符串
	private String sign;// 签名
	private String resultCode;// 业务结构,SUCCESS/FAIL
	private String errCode;// 错误代码
	private String errCodeDes;// 错误代码描述

	// 一下代码是result_code为SUCCESS时有返回
	private String tradeType;// 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP
	private String prepayId;// 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	private String codeUrl;// 二维码链接trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付

	private Integer tradeId;// 交易ID

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

}
