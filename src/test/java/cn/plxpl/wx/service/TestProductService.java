package cn.plxpl.wx.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.plxpl.wx.entity.Product;

@SuppressWarnings("restriction")
@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:mybatis-config.xml",
		"classpath:spring-mvc.xml" })
public class TestProductService {
	private static Logger logger = Logger.getLogger(TestProductService.class);
	// private ApplicationContext ac = null;
	@Resource
	IProductService productService;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("spring.xml");
	// userService = (IProductService) ac.getBean("ProductService");
	// }
	@Test
	public void testGetAllProducts() {
		List<Product> products = productService.getAllProducts();
		System.out.println(products.size());
		logger.info(products.size());
	}
}
