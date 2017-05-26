package cn.plxpl.wx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.plxpl.wx.message.resp.Article;
import cn.plxpl.wx.message.resp.NewsMessage;
import cn.plxpl.wx.message.resp.TransCustomerMessage;
import cn.plxpl.wx.message.resp.TransInfo;
import cn.plxpl.wx.util.Constants;
import cn.plxpl.wx.util.MessageUtil;

public class TextMsgService {

	public String SwitchCustomerService(String fromUserName, String toUserName) {
		TransCustomerMessage transCustMessage = new TransCustomerMessage();
		transCustMessage.setToUserName(fromUserName);
		transCustMessage.setFromUserName(toUserName);
		transCustMessage.setCreateTime(new Date().getTime());
		transCustMessage.setMsgType(MessageUtil.RESP_TRANSFER_CUSTOMER_SERVICE); // ת���˹��ͷ�ģʽ
		TransInfo transInfo = new TransInfo();
		// transInfo.setKfAccount("mengfanfei@tspanlongxi");
		transCustMessage.setTransInfo(transInfo);
		String respMessage = MessageUtil
				.transCustomerMessageToXml(transCustMessage);
		return respMessage;
	}

	public String ReturnDefaultString(String fromUserName, String toUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();

		Article article = new Article();
		article.setTitle("盘龙溪漂流\n安全刺激，全程无桨无晒！");
		article.setDescription("通山盘龙溪漂流安全、惊险刺激！");
		article.setPicUrl(Constants.itrPicUrl);
		article.setUrl(Constants.WebUrl);

		Article article1 = new Article();
		article1.setTitle("景区风景");
		article1.setDescription("");
		article1.setUrl(Constants.SceneryUrl);

		Article article2 = new Article();
		article2.setTitle("自驾游线路");
		article2.setDescription("");
		article2.setUrl(Constants.trafficMapUrl);

		Article article3 = new Article();
		article3.setTitle("回复'zxkf'转人工客服");
		article3.setDescription("");
		article3.setUrl("");

		articleList.add(article);
		articleList.add(article1);
		articleList.add(article2);
		articleList.add(article3);
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}
}
