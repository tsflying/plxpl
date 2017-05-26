package cn.plxpl.wx.dao;

import java.util.List;

import cn.plxpl.entity.User;

public interface IUserDao {

	User query(User user);

	int save(User user);

	boolean update(User user);

	boolean delete(int id);

	List<User> queryAll();
}
