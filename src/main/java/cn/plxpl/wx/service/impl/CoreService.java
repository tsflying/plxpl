package cn.plxpl.wx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.plxpl.wx.message.resp.Article;
import cn.plxpl.wx.message.resp.NewsMessage;
import cn.plxpl.wx.message.resp.TextMessage;
import cn.plxpl.wx.service.ICoreService;
import cn.plxpl.wx.util.MessageUtil;

@Service
public class CoreService implements ICoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request, HttpSession session) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			session.setAttribute("openid", fromUserName);
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			TextMsgService textMsgService = new TextMsgService();

			boolean isText = false;
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");
				isText = true;
				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {

				} else if ("2".equals(content)) {

				} else if ("zxkf".equals(content)) {
					respMessage = textMsgService.SwitchCustomerService(
							fromUserName, toUserName);
				} else {
					respMessage = textMsgService.ReturnDefaultString(
							fromUserName, toUserName);
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您关注盘龙溪漂流！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					if ("startTime".equals(eventKey)) {
						respContent = "景区于每年5月19日至10月中旬开业，每天下午2点至2点半开漂！";
					} else if ("contact".equals(eventKey)) {
						respContent = "18627961027,0715-2335998";
					} else if ("zxkf".equals(eventKey)) {//
						respMessage = textMsgService.SwitchCustomerService(
								fromUserName, toUserName);
						return respMessage;
					} else if ("instruction".equals(eventKey)) {
						respMessage = textMsgService.ReturnDefaultString(
								fromUserName, toUserName);
						return respMessage;
					}
				}
			}

			if (!isText) {
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	public String getOpenId(String code) {
		String openId = null;

		return openId;
	}

}
