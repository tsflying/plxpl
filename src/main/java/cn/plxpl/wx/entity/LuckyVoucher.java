package cn.plxpl.wx.entity;

import java.util.Date;

public class LuckyVoucher {

	private int id;
	private Long luckVoucherTypeId;
	private int tradeinfoId;
	private int OriginalTotalPrice;
	private int discountTotalPrice;
	private Date createTime;
	private Date updateTime;
	private int isValid;
	private String openId;
	private int isUsed;
	private String luckyCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getOriginalTotalPrice() {
		return OriginalTotalPrice;
	}

	public void setOriginalTotalPrice(int originalTotalPrice) {
		OriginalTotalPrice = originalTotalPrice;
	}

	public int getDiscountTotalPrice() {
		return discountTotalPrice;
	}

	public void setDiscountTotalPrice(int discountTotalPrice) {
		this.discountTotalPrice = discountTotalPrice;
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

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public String getLuckyCode() {
		return luckyCode;
	}

	public void setLuckyCode(String luckyCode) {
		this.luckyCode = luckyCode;
	}

}
