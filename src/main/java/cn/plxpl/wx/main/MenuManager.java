package cn.plxpl.wx.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.plxpl.wx.entity.AccessToken;
import cn.plxpl.wx.entity.Button;
import cn.plxpl.wx.entity.CommonButton;
import cn.plxpl.wx.entity.ComplexButton;
import cn.plxpl.wx.entity.Menu;
import cn.plxpl.wx.entity.ViewButton;
import cn.plxpl.wx.util.Constants;
import cn.plxpl.wx.util.WeixinUtil;

/**
 * 菜单管理器类
 * 
 * @author tsflying
 * 
 */
public class MenuManager {

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// *************************************测试环境*************************************
		// 第三方用户唯一凭证֤
		// String appId = "wx2ed20c1fa7ad5f75";
		// 第三方用户唯一凭证密钥
		// String appSecret = "fa579e6c065064a4aba679a63a4cb3de";
		// *************************************测试环境*************************************

		// //
		// *************************************线上环境*************************************
		// // 第三方用户唯一凭证֤
		String appId = "wx95040da38fc72d8e";
		// // 第三方用户唯一凭证密钥
		String appSecret = "6293a125f190dafdd19fc42230f89396";
		// //
		// *************************************线上环境*************************************

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result) {
				log.info("菜单创建成功！");
				System.out.println("菜单创建成功！");
			} else {
				log.info("菜单创建失败，错误码：" + result);
				System.out.println("菜单创建失败，错误码：" + result);
			}
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("使用说明");
		btn11.setType("click");
		btn11.setKey("instruction");

		CommonButton btn21 = new CommonButton();
		btn21.setName("在线客服");
		btn21.setType("click");
		btn21.setKey("zxkf");

		CommonButton btn22 = new CommonButton();
		btn22.setName("景区开放时间");
		btn22.setType("click");
		btn22.setKey("startTime");

		CommonButton btn23 = new CommonButton();
		btn23.setName("联系我们");
		btn23.setType("click");
		btn23.setKey("contact");

		ViewButton btn31 = new ViewButton();
		btn31.setName("网站");
		btn31.setType("view");
		btn31.setUrl(Constants.WebUrl);

		ViewButton btn32 = new ViewButton();
		btn32.setName("导航");
		btn32.setType("view");
		btn32.setUrl(Constants.TripMap);

		ViewButton btn33 = new ViewButton();
		btn33.setName("景区风景");
		btn33.setType("view");
		btn33.setUrl(Constants.SceneryUrl);

		ViewButton btn34 = new ViewButton();
		btn34.setName("精彩视频");
		btn34.setType("view");
		btn34.setUrl(Constants.VideoUrl);

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("盘龙溪");
		mainBtn1.setSub_button(new Button[] { btn11 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("资讯");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多体验");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
		// menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });

		return menu;
	}
}
