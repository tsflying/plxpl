package cn.plxpl.wx.entity.dto;

public class TotalFeeAndLuckyVoucher {

	// 优惠前金额
	private float totalFee;
	// 优惠后金额
	private float factFee;
	// 兑奖券ID
	private int luckyVoucherId;
	// 是否有兑奖券
	private boolean useLuckyVoucher;

	// 优惠券类型,1：打折券 2：现金券 3：几免几优惠券
	private long luckyVoucherTypeId;

	public float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}

	public float getFactFee() {
		return factFee;
	}

	public void setFactFee(float factFee) {
		this.factFee = factFee;
	}

	public int getLuckyVoucherId() {
		return luckyVoucherId;
	}

	public void setLuckyVoucherId(int luckyVoucherId) {
		this.luckyVoucherId = luckyVoucherId;
	}

	public boolean isUseLuckyVoucher() {
		return useLuckyVoucher;
	}

	public void setUseLuckyVoucher(boolean useLuckyVoucher) {
		this.useLuckyVoucher = useLuckyVoucher;
	}

	public long getLuckyVoucherTypeId() {
		return luckyVoucherTypeId;
	}

	public void setLuckyVoucherTypeId(long luckyVoucherTypeId) {
		this.luckyVoucherTypeId = luckyVoucherTypeId;
	}

}
