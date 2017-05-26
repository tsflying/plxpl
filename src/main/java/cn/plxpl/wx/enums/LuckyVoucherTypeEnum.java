package cn.plxpl.wx.enums;

public enum LuckyVoucherTypeEnum {

	DISCOUNT(1, "打{0}折"), CASHVOUCHER(2, "{0}元现金抵用券"), FREERATE(3, "{0}免{1}");

	private int type;
	private String content;

	LuckyVoucherTypeEnum(int type, String content) {
		this.type = type;
		this.content = content;
	}

	public int getType() {
		return this.type;
	}

	public String getContent() {
		return this.content;
	}
}
