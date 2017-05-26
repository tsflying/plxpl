package cn.plxpl.wx.enums;

public enum LuckyVoucherParamEnum {

	LUCKY_RATE("luckyRate"), FREQUENCY_PER_DAY("frequencyPerDay"), TERM_OF_VALID(
			"termOfValid"), LUCKY_IN_TIME("luckyInTime");

	private String content;

	LuckyVoucherParamEnum(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
}
