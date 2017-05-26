package cn.plxpl.wx.entity.bo;

import java.util.Date;

public class LvAndLvtBo {

	private int luckVoucherId;
	private Long luckVoucherTypeId;
	private int tradeinfoId;
	// 中奖时间
	private Date createTime;
	// 中奖券注解
	private String comment;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLuckyCode() {
		return luckyCode;
	}

	public void setLuckyCode(String luckyCode) {
		this.luckyCode = luckyCode;
	}

}
