package cn.plxpl.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.plxpl.entity.DataGrid;
import cn.plxpl.entity.ReturnResult;
import cn.plxpl.wx.condition.BasicCondition;
import cn.plxpl.wx.entity.Product;
import cn.plxpl.wx.service.IProductService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/backend")
public class BackendAction {

	@Resource
	IProductService productService;

	@RequestMapping(value = "/queryProducts")
	@ResponseBody
	public String queryProducts(@RequestParam String rows,
			@RequestParam String page) {
		DataGrid dataGrid = new DataGrid();
		JSONArray rows1 = new JSONArray();
		BasicCondition condition = new BasicCondition();
		condition.setRows(Integer.valueOf(rows));
		condition.setPage(Integer.valueOf(page));
		List<Product> products = productService
				.getProductsByCondition(condition);
		rows1 = JSONArray.fromObject(products);
		int totalSize = productService.countAllProducts();
		dataGrid.setRows(rows1);
		dataGrid.setTotal(totalSize);
		JSONObject jsonObject = JSONObject.fromObject(dataGrid);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/editeProduct")
	public ModelAndView Index(@RequestParam String productId) {
		Integer prid = Integer.valueOf(productId);
		Product product = productService.getProductById(prid);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("product", product);
		ModelAndView modelView = new ModelAndView("backend/editeProduct", model);
		return modelView;
	}

	@RequestMapping(value = "/toAddProduct")
	public ModelAndView addProduct() {
		ModelAndView modelView = new ModelAndView("backend/addProduct");
		return modelView;
	}

	@RequestMapping(value = "/updateProductInfo")
	@ResponseBody
	public String updateProductInfo(@ModelAttribute Product product) {
		ReturnResult rs = new ReturnResult();
		boolean re = productService.updateProduct(product);
		if (re) {
			rs.setIsSuccess(true);
		} else {
			rs.setIsSuccess(false);
		}
		JSONObject jsonObject = JSONObject.fromObject(rs);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/addProductInfo")
	@ResponseBody
	public String addProductInfo(@ModelAttribute Product product) {
		ReturnResult rs = new ReturnResult();
		boolean re = productService.save(product);
		if (re) {
			rs.setIsSuccess(true);
		} else {
			rs.setIsSuccess(false);
		}
		JSONObject jsonObject = JSONObject.fromObject(rs);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/deleteProduct")
	@ResponseBody
	public String deleteProduct(@RequestParam String productId) {
		ReturnResult rs = new ReturnResult();
		if (StringUtils.isBlank(productId)) {
			rs.setIsSuccess(false);
		} else {
			boolean re = productService.delete(Integer.valueOf(productId));
			if (re) {
				rs.setIsSuccess(true);
			} else {
				rs.setIsSuccess(false);
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(rs);
		return jsonObject.toString();
	}
}
