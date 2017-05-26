package cn.plxpl.wx.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TradeInfo {

	private Integer id;
	private String name;
	private String phone;
	private String outTradeNo;
	private float totalFee;
	private Date timeStart;
	private Date timeExpire;
	private Integer productId;
	private String openId;
	private Date createTime;
	private String createTimeStr;
	private Date updateTime;
	private String prePayId;
	private Integer count;// 购买票数
	private Integer state;// 0:未支付 1:已支付 2:支付失败 3:交易完成(已经完成兑票)
	private Integer luckyVoucherId;// 兑奖券ID

	public String getCreateTimeStr() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		createTimeStr = f.format(createTime);
		return createTimeStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPrePayId() {
		return prePayId;
	}

	public void setPrePayId(String prePayId) {
		this.prePayId = prePayId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getLuckyVoucherId() {
		return luckyVoucherId;
	}

	public void setLuckyVoucherId(Integer luckyVoucherId) {
		this.luckyVoucherId = luckyVoucherId;
	}

}
