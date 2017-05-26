package cn.plxpl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.plxpl.entity.User;
import cn.plxpl.service.IUserService;
import cn.plxpl.wx.dao.IUserDao;

@SuppressWarnings("restriction")
@Service
public class UserService implements IUserService {

	@Resource
	IUserDao IUserDao;

	@Override
	public boolean checkUser(User user) {
		User user1 = IUserDao.query(user);
		if (user1 != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean save(User user) {
		int re = IUserDao.save(user);
		if (re > 0)
			return true;
		return false;
	}

}
