package cn.plxpl.wx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import cn.plxpl.utils.DateUtils;
import cn.plxpl.wx.dao.ILuckyDao;
import cn.plxpl.wx.entity.LuckyVoucher;
import cn.plxpl.wx.entity.LuckyVoucherType;
import cn.plxpl.wx.entity.bo.LvAndLvtBo;
import cn.plxpl.wx.entity.vo.LuckyVoucherInfoVo;
import cn.plxpl.wx.enums.LuckyVoucherParamEnum;
import cn.plxpl.wx.service.IBaseDataService;
import cn.plxpl.wx.service.ILuckyService;
import cn.plxpl.wx.util.WeixinUtil;

@SuppressWarnings("restriction")
@Service
public class LuckyService implements ILuckyService {

	@Resource
	ILuckyDao luckyDao;

	@Resource
	IBaseDataService baseDataService;

	@Override
	public List<LuckyVoucherType> queryLuckyVouchers() {
		List<LuckyVoucherType> luckyVoucherTypes = luckyDao
				.queryLuckyVoucherTypes();
		// if (CollectionUtils.isNotEmpty(luckyVoucherTypes)) {
		// for (LuckyVoucherType lv : luckyVoucherTypes) {
		// String content = "";
		// if (lv.getLuckyVoucherType() == LuckyVoucherTypeEnum.DISCOUNT
		// .getType()) {// 打折券
		// content = LuckyVoucherTypeEnum.DISCOUNT.getContent();
		// content = content.replace("{0}",
		// String.valueOf((float) lv.getDiscountRate() / 10));
		// } else if (lv.getLuckyVoucherType() ==
		// LuckyVoucherTypeEnum.CASHVOUCHER
		// .getType()) {// 现金抵用券
		// content = LuckyVoucherTypeEnum.CASHVOUCHER.getContent();
		// content = content.replace("{0}",
		// String.valueOf(lv.getVoucherTotal()));
		// } else if (lv.getLuckyVoucherType() == LuckyVoucherTypeEnum.FREERATE
		// .getType()) {
		// content = LuckyVoucherTypeEnum.FREERATE.getContent();
		// content = content.replace("{0}",
		// String.valueOf(lv.getTicketTotal()));
		// content = content.replace("{1}",
		// String.valueOf(lv.getFreeTicketNum()));
		// }
		// lv.setContent(content);
		// }
		// }
		return luckyVoucherTypes;
	}

	/**
	 * 抽奖
	 */
	@Override
	public String luckyDraw(String openId) {
		Map<String, Float> params = baseDataService.queryLuckyVoucherParams();
		float luckyRate = 0.5f;
		if (MapUtils.isNotEmpty(params)) {
			luckyRate = params.get(LuckyVoucherParamEnum.LUCKY_RATE
					.getContent());
			if (luckyRate == 0f) {
				luckyRate = 0.5f;
			}

			int luckyInTime = params.get(
					LuckyVoucherParamEnum.LUCKY_IN_TIME.getContent())
					.intValue();
			if (isDrawVoucher(openId, luckyInTime)) {
				return "-3";
			}

			// 每天你容许抽奖次数
			int allowDrawVoucherCnt = params.get(
					LuckyVoucherParamEnum.FREQUENCY_PER_DAY.getContent())
					.intValue();
			String startTime = DateUtils.dateToStr(
					DateUtils.getSomeDayToToday(0), "yyyy-MM-dd 00:00:00");
			String endTime = DateUtils.dateToStr(
					DateUtils.getSomeDayToToday(1), "yyyy-MM-dd 00:00:00");
			// 当天已经抽奖次数
			int luckyDrawCnt = luckyDao.queryLuckyDrawTimesTodayByOpenId(
					openId, startTime, endTime);
			if (luckyDrawCnt >= allowDrawVoucherCnt) {// 超过当天容许抽奖次数则放回错误
				return "-1";
			}
		}

		List<LuckyVoucherType> luckyVoucherTypes = luckyDao
				.queryLuckyVoucherTypes();
		if (CollectionUtils.isNotEmpty(luckyVoucherTypes)) {
			BigDecimal bd = new BigDecimal(Math.random());
			float rand = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			int size = luckyVoucherTypes.size();
			int re = (int) (Math.random() * size);
			LuckyVoucher lv = new LuckyVoucher();
			lv.setOpenId(openId);
			if (rand < luckyRate) {// 中奖
				LuckyVoucherType lvt = luckyVoucherTypes.get(re);// 抽中的奖项
				lv.setLuckVoucherTypeId(lvt.getId());
				lv.setLuckyCode(genLuckyCode());
				re = re * 2 + 1;
			} else {// 未中奖
				lv.setLuckVoucherTypeId(-1l);
				re *= 2;
			}
			int cnt = luckyDao.insertLuckyVoucher(lv);// 中奖记录数据库
			if (cnt == 0) {
				return String.valueOf(-2);
			} else {
				return String.valueOf(re);
			}
		} else {// 没有设置奖项
			return String.valueOf(1);
		}

	}

	/**
	 * 生成兑奖码
	 * 
	 * @param openId
	 * @return
	 */
	private String genLuckyCode() {
		String code = null;
		String currentTime = DateUtils.getCurrentTime("yyyyMMddHHmmss");
		String fixed = WeixinUtil.getRandomString(5);
		code = currentTime + fixed;
		return code;
	}

	/**
	 * 判断该用户最近一段时间内是否有中奖记录
	 * 
	 * @param openId
	 * @return
	 */
	private boolean isDrawVoucher(String openId, int luckyInTime) {
		String startTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(-luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		String endTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		int re = luckyDao.queryLuckyVoucherCnt(openId, startTime, endTime);
		if (re > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询该用户中奖信息
	 */
	@Override
	public LuckyVoucherInfoVo queryLuckyVoucherByOpenId(String openId,
			int luckyInTime, int termOfValid) {
		String startTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(-luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		String endTime = DateUtils.dateToStr(
				DateUtils.getSomeDayToToday(luckyInTime / 2),
				"yyyy-MM-dd 00:00:00");
		List<LvAndLvtBo> lst = luckyDao.queryLuckyVouchersByOpenId(openId,
				startTime, endTime);
		if (CollectionUtils.isNotEmpty(lst)) {
			LvAndLvtBo bo = lst.get(0);
			LuckyVoucherInfoVo vo = new LuckyVoucherInfoVo();
			try {
				BeanUtils.copyProperties(vo, bo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			vo.setLuckyTime(DateUtils.dateToStr(vo.getCreateTime()));
			int diff = DateUtils.getDiffDayNum(vo.getCreateTime(), new Date());
			vo.setDeadTime(termOfValid - diff);
			return vo;
		} else {
			return null;
		}
	}

	@Override
	public LuckyVoucherInfoVo queryLuckyVoucherByOpenId(String openId) {
		Map<String, Float> params = baseDataService.queryLuckyVoucherParams();
		int termOfValid = params.get(
				LuckyVoucherParamEnum.TERM_OF_VALID.getContent()).intValue();
		int luckyInTime = params.get(
				LuckyVoucherParamEnum.LUCKY_IN_TIME.getContent()).intValue();
		return queryLuckyVoucherByOpenId(openId, luckyInTime, termOfValid);
	}

	@Override
	public boolean setLuckyVoucherUsed(int id) {
		int optRows = luckyDao.updateLuckyVoucherState(id);
		if (optRows > 0) {
			return true;
		} else {
			return false;
		}
	}
}
