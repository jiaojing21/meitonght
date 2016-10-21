package com.itsv.platform.common.dictionary.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.annotation.brand.bo.BrandService;
import com.itsv.annotation.brand.vo.Brand;
import com.itsv.annotation.util.ServiceUitl;
import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

/**
 * 说明：增加，修改，删除数据字典的前端处理类
 * 
 * @author milu
 * @since 2007-07-22
 * @version 1.0
 */
public class Itsv_dictionaryController extends
		BaseCURDController<Itsv_dictionary> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(Itsv_dictionaryController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.itsv_dictionary";

	private Itsv_dictionaryService itsv_dictionaryService; // 逻辑层对象
	
	private String treeView; // 树状列表的视图

	private String listView; // 用户列表显示视图

	/**
	 * 在页面左侧显示单位树。与单位管理不同的是，根节点可点击
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());
		mnv.addObject(WebConfig.DATA_NAME, this.itsv_dictionaryService
				.queryByVO(new Itsv_dictionary()));
		mnv.addObject("refreshCode", request.getParameter("refreshCode"));
		return mnv;
	}

	/**
	 * 用户列表显示页面。显示指定单位下的所有用户
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setParentcode(parentcode);
		mnv.addObject(WebConfig.DATA_NAME, this.itsv_dictionaryService
				.queryByVO(itsv_dictionary));
		return mnv;
	}

	// 覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// 实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Itsv_dictionary itsv_dictionary = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			itsv_dictionary = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", itsv_dictionary);
		} else {
			itsv_dictionary = new Itsv_dictionary();
		}

		this.itsv_dictionaryService.queryByVO(records, itsv_dictionary);
	}

	// 显示增加数据字典页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		String code = "";
		if(parentcode.length()>3){
			code = parentcode.substring(0, 3);
		}
		String brandcode = "";
		List<Brand> list = null;
		if(parentcode.length()>6){
			brandcode = parentcode.substring(0, 6);
			list = ServiceUitl.getBrandType(brandcode);
		}
		mnv.addObject("list", list);
		mnv.addObject("code", code);
	}

	// 显示修改数据字典页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		
		String code = "";
		if(parentcode.length()>3){
			code = parentcode.substring(0, 3);
		}
		String brandcode = "";
		List<Brand> list = null;
		if(parentcode.length()>6){
			brandcode = parentcode.substring(0, 6);
			list = ServiceUitl.getBrandType(brandcode);
		}
		mnv.addObject("list", list);
		mnv.addObject("code", code);
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Itsv_dictionary itsv_dictionary = this.itsv_dictionaryService
					.queryById(id);
			if (null == itsv_dictionary) {
				showMessage(request, "未找到对应的数据字典记录。请重试");
				mnv = list(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, itsv_dictionary);
			}

		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 保存新增数据字典
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_dictionary itsv_dictionary = null;
		try {
			itsv_dictionary = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, itsv_dictionary)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// itsv_dictionary);
			// }
			String parentcode = ServletRequestUtils.getStringParameter(request,
					"parentcode", "");
			request.setAttribute("parentcode", parentcode);
			String codeclass = ServletRequestUtils.getStringParameter(request,
					"codeclass", "");
			request.setAttribute("codeclass", codeclass);

			Itsv_dictionary nameItsv_dictionary = new Itsv_dictionary();
			nameItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			nameItsv_dictionary.setDictname(itsv_dictionary.getDictname());
			List namelist = this.itsv_dictionaryService
					.queryByVO(nameItsv_dictionary);
			if (namelist != null && namelist.size() > 0) {
				showMessage(request, "字典名称不能重复！");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						itsv_dictionary);
			}

			Itsv_dictionary codeItsv_dictionary = new Itsv_dictionary();
			codeItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			codeItsv_dictionary.setHardcode(itsv_dictionary.getHardcode());
			List codelist = this.itsv_dictionaryService
					.queryByVO(codeItsv_dictionary);
			if (codelist != null && codelist.size() > 0) {
				showMessage(request, "业务编码不能重复！");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						itsv_dictionary);
			}
			this.itsv_dictionaryService.add(itsv_dictionary);
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "新增数据字典成功");
		} catch (AppException e) {
			logger.error("新增数据字典[" + itsv_dictionary + "]失败", e);
			showMessage(request, "新增数据字典失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给数据字典
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					itsv_dictionary);
		}

		return list(request, response);
	}

	/**
	 * 保存修改的数据字典
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_dictionary itsv_dictionary = null;
		try {
			itsv_dictionary = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, itsv_dictionary)) {
			// return edit(request, response);
			// }

			String parentcode = ServletRequestUtils.getStringParameter(request,
					"parentcode", "");
			request.setAttribute("parentcode", parentcode);
			String codeclass = ServletRequestUtils.getStringParameter(request,
					"codeclass", "");
			request.setAttribute("codeclass", codeclass);

			Itsv_dictionary nameItsv_dictionary = new Itsv_dictionary();
			nameItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			nameItsv_dictionary.setDictname(itsv_dictionary.getDictname());
			List namelist = this.itsv_dictionaryService
					.queryByVO(nameItsv_dictionary);
			if (namelist != null && namelist.size() > 0) {
				if (!itsv_dictionary.getId().equals(
						((Itsv_dictionary) namelist.get(0)).getId())) {
					showMessage(request, "字典名称不能重复！");
					return new ModelAndView(getEditView(), WebConfig.DATA_NAME,
							itsv_dictionary);
				}
			}

			Itsv_dictionary codeItsv_dictionary = new Itsv_dictionary();
			codeItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			codeItsv_dictionary.setHardcode(itsv_dictionary.getHardcode());
			List codelist = this.itsv_dictionaryService
					.queryByVO(codeItsv_dictionary);
			if (codelist != null && codelist.size() > 0) {
				if (!itsv_dictionary.getId().equals(
						((Itsv_dictionary) codelist.get(0)).getId())) {
					showMessage(request, "业务编码不能重复！");
					return new ModelAndView(getEditView(), WebConfig.DATA_NAME,
							itsv_dictionary);
				}
			}

			this.itsv_dictionaryService.update(itsv_dictionary);
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "修改数据字典成功");
		} catch (AppException e) {
			logger.error("修改数据字典[" + itsv_dictionary + "]失败", e);
			showMessage(request, "修改数据字典失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return list(request, response);
	}

	/**
	 * 删除选中的数据字典
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] itsv_dictionarys = ServletRequestUtils.getStringParameters(
				request, "p_id");
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		request.setAttribute("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		request.setAttribute("codeclass", codeclass);
		Itsv_dictionary itsv_dictionary = this.itsv_dictionaryService
				.queryById(itsv_dictionarys[0]);
		// 允许部分删除成功
		try {
			for (String id : itsv_dictionarys) {
				Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
				qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
				List list = this.itsv_dictionaryService
						.queryByVO(qryitsv_dictionary);
				if (list.size() > 0) {
					showMessage(request, "必须先删除数据字典下级后才能删除此项数据字典！");
					return list(request, response);
				} else {
					this.itsv_dictionaryService.delete(id);
				}
			}
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "删除数据字典成功");
		} catch (AppException e) {
			logger.error("批量删除数据字典时失败", e);
			showMessage(request, "删除数据字典失败：" + e.getMessage(), e);
		}
		return list(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setItsv_dictionaryService(
			Itsv_dictionaryService itsv_dictionaryService) {
		this.itsv_dictionaryService = itsv_dictionaryService;
	}
	
	public String getTreeView() {
		return treeView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}
}