package cn.plxpl.service;

import cn.plxpl.entity.User;

public interface IUserService {

	boolean checkUser(User user);

	boolean save(User user);
}
