package cn.plxpl.wx.service;

import java.util.List;

import cn.plxpl.wx.condition.BasicCondition;
import cn.plxpl.wx.entity.Product;

public interface IProductService {
	public List<Product> getAllProducts();

	public Product getProductById(Integer id);

	public List<Product> getProductsByCondition(BasicCondition condition);

	public int countAllProducts();

	public boolean updateProduct(Product product);

	public boolean save(Product product);

	public boolean delete(Integer productId);
}
