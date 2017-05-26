package cn.plxpl.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.plxpl.wx.condition.BasicCondition;
import cn.plxpl.wx.dao.IProductDao;
import cn.plxpl.wx.entity.Product;
import cn.plxpl.wx.service.IProductService;

@SuppressWarnings("restriction")
@Service("productService")
public class ProductService implements IProductService {

	@Resource
	IProductDao productDao;

	@Override
	public List<Product> getAllProducts() {
		return productDao.findAll();
	}

	@Override
	public Product getProductById(Integer id) {
		Product product = productDao.findById(id);
		return product;
	}

	@Override
	public List<Product> getProductsByCondition(BasicCondition condition) {
		int page = condition.getPage();
		int rows = condition.getRows();
		page = page < 1 ? 1 : page;
		rows = rows < 1 ? cn.plxpl.constant.Constants.pageSize : rows;
		int startRow = (page - 1) * rows;
		condition.setStartRow(startRow);
		condition.setRows(rows);
		return productDao.queryProductsByBasicCondition(condition);
	}

	@Override
	public int countAllProducts() {
		return productDao.countAll();
	}

	@Override
	public boolean updateProduct(Product product) {
		return productDao.update(product);
	}

	@Override
	public boolean save(Product product) {
		int re = productDao.save(product);
		if (re > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(Integer productId) {
		int re = productDao.delete(productId);
		if (re > 0) {
			return true;
		} else {
			return false;
		}
	}
}
