package com.itsv.annotation.kefu.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.kefu.bo.KefuService;
import com.itsv.annotation.kefu.vo.Kefu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除kefu的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class KefuController extends BaseAnnotationController<Kefu> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(KefuController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.kefu";
	@Autowired
	private KefuService kefuService; //逻辑层对象

	public KefuController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/kefu/add");
		super.setIndexView("meitong/kefu/index");
		super.setEditView("meitong/kefu/edit");

	}

	//覆盖父类方法，默认执行query()，分页显示数据

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Kefu kefu = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			kefu = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", kefu);
		} else {
			kefu = new Kefu();
		}

		this.kefuService.queryByVO(records, kefu);
	}

	//显示增加kefu页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改kefu页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Kefu kefu = this.kefuService.queryById(id);
		if (null == kefu) {
			showMessage(request, "未找到对应的kefu记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, kefu);
		}
	}
	//添加前数据
	@RequestMapping("/kefu.beforeadd.htm")
	public ModelAndView beforeadd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * 保存新增客服
	 */
	@RequestMapping("/kefu.saveadd.htm")
	public ModelAndView saveAdd1(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView();
		Kefu kefu = null;
		try {
			kefu = param2Object(request);
			this.kefuService.add(kefu);

			showMessage(request, "新增客服成功");
		} catch (AppException e) {
			logger.error("新增客服[" + kefu + "]失败", e);
			showMessage(request, "新增客服失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给kefu
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, kefu);
		}
		mnv.setViewName(getAddView());
		return mnv;
	}
	//查询前数据
		@RequestMapping("/kefu.query.htm")
		public ModelAndView queryKefu(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
			mnv.setViewName(getIndexView());
			return mnv;
		}
		//查询列表
		@RequestMapping("/kefu.BeforeData.htm")
		@ResponseBody
		public Map<String, Object> indexAppControl(HttpServletRequest request,
				HttpServletResponse response) throws AppException {
			Map<String, Object> map = new HashMap<String, Object>();
			String shmc = "";
			try {
				shmc = java.net.URLDecoder
						.decode(ServletRequestUtils.getStringParameter(request,
								"shmc", ""), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Kefu kefu = new Kefu();
			kefu.setName(shmc);
			
			
			CachePagedList records = PagedListTool.getEuiPagedList(request,
					"kefu");
			this.kefuService.queryByVO(records, kefu);
			map.put("total", records.getTotalNum());
			map.put("rows", records.getSource());
			return ResponseUtils.sendMap(map);
		}
	//编辑前数据准备
	@RequestMapping("/kefu.EditData.htm")
	public ModelAndView editData(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName(getEditView());
		String kefuId = "";
		try{
			kefuId = ServletRequestUtils.getStringParameter(request, "kefuid");
			Kefu kefu = this.kefuService.queryById(kefuId);
			mnv.addObject("data", kefu);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mnv;
	}
	/**
	 * 保存修改的kefu
	 */
	@RequestMapping("/kefu.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Kefu kefu = null;
		try {
			kefu = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, kefu)) {
			//	return edit(request, response);
			//}

			this.kefuService.update(kefu);
			showMessage(request, "修改客服成功");
		} catch (AppException e) {
			logger.error("修改客服[" + kefu + "]失败", e);
			showMessage(request, "修改客服失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, kefu);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, kefu);
  	    
	}
	/**
	 * 删除选中的kefu
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] kefus = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : kefus) {
				this.kefuService.delete(id);
			}
			showMessage(request, "删除kefu成功");
		} catch (AppException e) {
			logger.error("批量删除kefu时失败", e);
			showMessage(request, "删除kefu失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setKefuService(KefuService kefuService) {
		this.kefuService = kefuService;
	}
}