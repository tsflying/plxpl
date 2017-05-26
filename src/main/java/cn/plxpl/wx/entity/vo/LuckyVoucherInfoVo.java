package cn.plxpl.wx.entity.vo;

import java.util.Date;

public class LuckyVoucherInfoVo {

	private int luckVoucherId;
	private Long luckVoucherTypeId;
	private int tradeinfoId;
	// 中奖时间
	private Date createTime;
	private String luckyTime;
	// 中奖券注解
	private String comment;

	// 中奖券最后有效期
	private int deadTime;

	// 兑奖码
	private String luckyCode;

	public int getLuckVoucherId() {
		return luckVoucherId;
	}

	public void setLuckVoucherId(int luckVoucherId) {
		this.luckVoucherId = luckVoucherId;
	}

	public Long getLuckVoucherTypeId() {
		return luckVoucherTypeId;
	}

	public void setLuckVoucherTypeId(Long luckVoucherTypeId) {
		this.luckVoucherTypeId = luckVoucherTypeId;
	}

	public int getTradeinfoId() {
		return tradeinfoId;
	}

	public void setTradeinfoId(int tradeinfoId) {
		this.tradeinfoId = tradeinfoId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLuckyTime() {
		return luckyTime;
	}

	public void setLuckyTime(String luckyTime) {
		this.luckyTime = luckyTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(int deadTime) {
		this.deadTime = deadTime;
	}

	public String getLuckyCode() {
		return luckyCode;
	}

	public void setLuckyCode(String luckyCode) {
		this.luckyCode = luckyCode;
	}

}
