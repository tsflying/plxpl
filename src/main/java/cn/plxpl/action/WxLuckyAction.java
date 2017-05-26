package cn.plxpl.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.plxpl.wx.entity.LuckyVoucherType;
import cn.plxpl.wx.service.IBaseDataService;
import cn.plxpl.wx.service.ILuckyService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/wxLucky")
public class WxLuckyAction {

	@Resource
	ILuckyService luckyService;

	@Resource
	IBaseDataService baseDataService;

	@RequestMapping(value = "/queryPrizeTypes")
	@ResponseBody
	public List<LuckyVoucherType> queryPrizeTypes() {
		List<LuckyVoucherType> list = luckyService.queryLuckyVouchers();
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/luckDraw")
	@ResponseBody
	public Map<String, String> luckDraw(HttpServletRequest request,
			HttpSession session) {
		String openid = (String) session.getAttribute("openid");
		Map map = new HashMap();
		if (StringUtils.isBlank(openid)) {
			openid = request.getParameter("openid");
			if (StringUtils.isBlank(openid)) {
				map.put("isSuccess", false);
				map.put("value", "请刷新页面!");
				return map;
			}
		}
		String value = luckyService.luckyDraw(openid);
		if (value.equals("-1")) {
			map.put("isSuccess", false);
			map.put("value", "您已经使用完今天的抽奖机会，请明天再来！");
			return map;
		} else if (value.equals("-2")) {
			map.put("isSuccess", false);
			map.put("value", "此次抽奖失败请再抽一次！");
			return map;
		} else if (value.equals("-3")) {
			map.put("isSuccess", false);
			map.put("value", "最近有中奖记录，请过段时间再来！");
			return map;
		}
		map.put("isSuccess", true);
		map.put("value", value);
		return map;
	}
}
