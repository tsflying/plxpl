package cn.plxpl.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ICoreService {

	public String processRequest(HttpServletRequest request, HttpSession session);

	public String getOpenId(String code);
}
