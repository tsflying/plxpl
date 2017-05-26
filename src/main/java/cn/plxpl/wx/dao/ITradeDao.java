package cn.plxpl.wx.dao;

import java.util.List;

import cn.plxpl.wx.condition.TradeInfoCondition;
import cn.plxpl.wx.entity.TradeInfo;
import cn.plxpl.wx.pay.resp.PayResp;

public interface ITradeDao {

	Integer save(TradeInfo tradeInfo);

	boolean update(TradeInfo tradeInfo);

	boolean updatePayResp(PayResp payResp);

	boolean updatePayState(TradeInfo tradeInfo);

	boolean delete(int id);

	TradeInfo findById(int id);

	TradeInfo findByOutTradeNo(String outTradeNo);

	List<TradeInfo> findAll();

	List<TradeInfo> getTradeInfoByOpenid(String openid);

	List<TradeInfo> getTradeInfoByCondition(TradeInfoCondition conditon);

	public Integer getTradeInfosTotalByCondition(TradeInfoCondition condition);
}
