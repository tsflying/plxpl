package cn.plxpl.wx.entity;

import java.util.Date;

public class LuckyVoucherType {

	private Long id;

	// 优惠券类型,1：打折券 2：现金券 3：几免几优惠券
	private int luckyVoucherType;

	// 折率,优惠券类型为1（打折券） 时
	private int discountRate;

	// 现金券面额，优惠券类型为2（现金券） 时
	private int voucherTotal;

	// 优惠券为3(几免几，如5免1)时，其中的5
	private int ticketTotal;

	// 优惠券为3(几免几，如5免1)时，其中的1
	private int freeTicketNum;

	private Date createTime;

	private String content;

	// 0:整除 1:失效
	private int isValid;

	// 剩余数量
	private int residualQuantity;

	// 中奖券注解
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLuckyVoucherType() {
		return luckyVoucherType;
	}

	public void setLuckyVoucherType(int luckyVoucherType) {
		this.luckyVoucherType = luckyVoucherType;
	}

	public int getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public int getVoucherTotal() {
		return voucherTotal;
	}

	public void setVoucherTotal(int voucherTotal) {
		this.voucherTotal = voucherTotal;
	}

	public int getTicketTotal() {
		return ticketTotal;
	}

	public void setTicketTotal(int ticketTotal) {
		this.ticketTotal = ticketTotal;
	}

	public int getFreeTicketNum() {
		return freeTicketNum;
	}

	public void setFreeTicketNum(int freeTicketNum) {
		this.freeTicketNum = freeTicketNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getResidualQuantity() {
		return residualQuantity;
	}

	public void setResidualQuantity(int residualQuantity) {
		this.residualQuantity = residualQuantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
