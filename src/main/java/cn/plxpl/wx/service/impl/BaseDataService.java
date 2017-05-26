package cn.plxpl.wx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.plxpl.wx.dao.ILuckyDao;
import cn.plxpl.wx.entity.LuckyVoucherParam;
import cn.plxpl.wx.service.IBaseDataService;
import cn.plxpl.wx.util.CommonUtil;
import cn.plxpl.wx.util.Constants;
import cn.plxpl.wx.util.WeixinUtil;

@SuppressWarnings("restriction")
@Service
public class BaseDataService implements IBaseDataService {

	@Resource
	ILuckyDao luckyDao;

	@Override
	public Map<String, Float> queryLuckyVoucherParams() {
		List<LuckyVoucherParam> luckyVoucherParams = luckyDao
				.queryLuckyVoucherParams();
		Map<String, Float> params = new HashMap<String, Float>();
		if (CollectionUtils.isNotEmpty(luckyVoucherParams)) {
			for (LuckyVoucherParam param : luckyVoucherParams) {
				params.put(param.getParamName(), param.getValue());
			}
		}
		return params;
	}

	@Override
	public String getWxCodeUrl(String returnUrl) {
		String getCodeUrl = Constants.getCodeUrl;
		getCodeUrl = getCodeUrl.replace("APPID",
				CommonUtil.urlEnodeUTF8(Constants.appId));
		getCodeUrl = getCodeUrl.replace("REDIRECT_URI",
				CommonUtil.urlEnodeUTF8(returnUrl));
		getCodeUrl = getCodeUrl.replace("SCOPE", Constants.OAuthScopeBasic);
		return getCodeUrl;
	}

	@Override
	public String getWxOpenId(String code) {
		String url = Constants.getOpenidAndAccessCode;
		url = url.replace("APPID", CommonUtil.urlEnodeUTF8(Constants.appId));
		url = url.replace("SECRET",
				CommonUtil.urlEnodeUTF8(Constants.appSecret));
		url = url.replace("CODE", CommonUtil.urlEnodeUTF8(code));
		JSONObject obj = WeixinUtil.httpRequest(url, "POST", null);
		if (obj == null)
			return null;
		String openid = obj.getString("openid");
		return openid;
	}

}
