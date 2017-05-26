package cn.plxpl.wx.dao;

import java.util.List;

import cn.plxpl.wx.condition.BasicCondition;
import cn.plxpl.wx.entity.Product;

public interface IProductDao {
	int save(Product prodect);

	boolean update(Product product);

	int delete(int id);

	Product findById(int id);

	List<Product> findAll();

	List<Product> queryProductsByBasicCondition(BasicCondition condition);

	int countAll();
}
