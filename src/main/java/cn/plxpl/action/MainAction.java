package cn.plxpl.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.plxpl.entity.User;
import cn.plxpl.service.IUserService;
import cn.plxpl.utils.CommonUtils;

@SuppressWarnings("restriction")
@Controller
public class MainAction {

	private static final long serialVersionUID = -5870626443117195170L;

	@Resource
	private IUserService userService;

	@RequestMapping(value = "/index")
	public ModelAndView Index(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		System.out.println(requestHeader);
		ModelAndView modelView;
		if (CommonUtils.isMobileDevice(requestHeader)) {
			modelView = new ModelAndView("wap/index");
		} else {
			modelView = new ModelAndView("index");
		}
		return modelView;
	}

	@RequestMapping(value = "/introduction")
	public ModelAndView introduction(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		System.out.println(requestHeader);
		ModelAndView modelView = new ModelAndView("introduction");
		return modelView;
	}

	@RequestMapping(value = "/photos")
	public ModelAndView photos() {
		ModelAndView modelView = new ModelAndView("photos");
		return modelView;
	}

	@RequestMapping(value = "/video")
	public ModelAndView video() {
		ModelAndView modelView = new ModelAndView("video");
		return modelView;
	}

	@RequestMapping(value = "/news")
	public ModelAndView news() {
		ModelAndView modelView = new ModelAndView("news");
		return modelView;
	}

	@RequestMapping(value = "/navigation")
	public ModelAndView navigation() {
		ModelAndView modelView = new ModelAndView("navigation");
		return modelView;
	}

	@RequestMapping(value = "/trafficMap")
	public ModelAndView trafficMap() {
		ModelAndView modelView = new ModelAndView("trafficMap");
		return modelView;
	}

	@RequestMapping(value = "/tripTips")
	public ModelAndView tripTips() {
		ModelAndView modelView = new ModelAndView("tripTips");
		return modelView;
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contact() {
		ModelAndView modelView = new ModelAndView("contact");
		return modelView;
	}

	@RequestMapping(value = "/messageBoard")
	public ModelAndView messageBoard() {
		ModelAndView modelView = new ModelAndView("messageBoard");
		return modelView;
	}

	@RequestMapping(value = "/backend/login")
	public ModelAndView login() {
		ModelAndView modelView = new ModelAndView("login");
		return modelView;
	}

	@RequestMapping(value = "/backend/index")
	public ModelAndView index(@ModelAttribute("user") User user,
			HttpSession session) {
		ModelAndView modelView;
		if (userService.checkUser(user)) {
			modelView = new ModelAndView("backend/index");
			session.setAttribute("userName", user.getName());
		} else {
			modelView = new ModelAndView("login");
		}

		return modelView;
	}

	@RequestMapping(value = "/backend/ordersList")
	public ModelAndView ordersList() {
		ModelAndView modelView;
		modelView = new ModelAndView("backend/wxQueryOrders");
		return modelView;
	}

	@RequestMapping(value = "/backend/products")
	public ModelAndView products() {
		ModelAndView modelView;
		modelView = new ModelAndView("backend/products");
		return modelView;
	}
}
