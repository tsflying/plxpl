package cn.plxpl.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.plxpl.wx.entity.LuckyVoucher;
import cn.plxpl.wx.entity.LuckyVoucherParam;
import cn.plxpl.wx.entity.LuckyVoucherType;
import cn.plxpl.wx.entity.bo.LvAndLvtBo;

public interface ILuckyDao {

	List<LuckyVoucherType> queryLuckyVouchers();

	List<LuckyVoucherType> queryLuckyVoucherTypes();

	LuckyVoucherType queryLuckyVoucherById(@Param("id") Long id);

	List<LuckyVoucherParam> queryLuckyVoucherParams();

	int queryLuckyDrawTimesTodayByOpenId(@Param("openId") String openId,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	int insertLuckyVoucher(LuckyVoucher lvt);

	/**
	 * 查询该用户最近一段时间中奖总数
	 * 
	 * @param openId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int queryLuckyVoucherCnt(@Param("openId") String openId,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 查询有效期内的中奖记录
	 * 
	 * @param openId
	 * @return
	 */
	List<LvAndLvtBo> queryLuckyVouchersByOpenId(@Param("openId") String openId,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 更新兑奖券状态
	 * 
	 * @param id
	 * @return
	 */
	int updateLuckyVoucherState(@Param("id") int id);
}
