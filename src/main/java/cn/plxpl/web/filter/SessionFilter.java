package cn.plxpl.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class SessionFilter implements Filter {

	private static String[] passActions = { "login.do", "loginValid.do",
			"backend/index" };

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	private boolean checkIsPassAction(String path) {
		for (String str : passActions) {
			if (path.indexOf(str) > 0) {
				return true;
			}
		}
		return false;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		String path = req.getRequestURI();
		String userName = (String) session.getAttribute("userName");
		if (checkIsPassAction(path)) {
			chain.doFilter(request, response);
			return;
		}
		if (StringUtils.isEmpty(userName)) {
			res.sendRedirect("login.do");
			return;
		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
