package cn.plxpl.wx.service;

import java.util.List;

import cn.plxpl.wx.entity.LuckyVoucherType;
import cn.plxpl.wx.entity.vo.LuckyVoucherInfoVo;

public interface ILuckyService {

	/**
	 * 获取所有奖券
	 * 
	 * @return
	 */
	public List<LuckyVoucherType> queryLuckyVouchers();

	/**
	 * 抽奖
	 * 
	 * @return
	 */
	public String luckyDraw(String openId);

	/**
	 * 获取该用户有效期内的中奖券
	 * 
	 * @param openId
	 * @param timeInLucky
	 *            一段时间内才有效的兑奖券
	 * @return
	 */
	public LuckyVoucherInfoVo queryLuckyVoucherByOpenId(String openId,
			int luckyInTime, int termOfValid);

	/**
	 * 获取该用户有效期内的中奖券
	 * 
	 * @param openId
	 * @return
	 */
	public LuckyVoucherInfoVo queryLuckyVoucherByOpenId(String openId);

	/**
	 * 设置兑奖券状态为已经使用
	 * 
	 * @param id
	 * @return
	 */
	boolean setLuckyVoucherUsed(int id);

}
