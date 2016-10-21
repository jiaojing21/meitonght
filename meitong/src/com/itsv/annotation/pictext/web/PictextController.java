package com.itsv.annotation.pictext.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.pictext.bo.PictextService;
import com.itsv.annotation.pictext.vo.Pictext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除pictext的前端处理类
 * 
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
@Controller
public class PictextController extends BaseAnnotationController<Pictext> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(PictextController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.pictext";
	@Autowired
	private PictextService pictextService; //逻辑层对象
	
	@Autowired
	private FileUploadService fileUploadService; //逻辑层对象

	public PictextController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/pictext/add");
		super.setIndexView("meitong/pictext/index");
		super.setEditView("meitong/pictext/edit");

	}

  /**
   * 注册自定义类型转换类，用来转换日期对象
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
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
		Pictext pictext = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			pictext = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", pictext);
		} else {
			pictext = new Pictext();
		}

		this.pictextService.queryByVO(records, pictext);
	}

	//显示增加pictext页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改pictext页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Pictext pictext = this.pictextService.queryById(id);
		if (null == pictext) {
			showMessage(request, "未找到对应的pictext记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, pictext);
		}
	}
	//跳转新增页面
	@RequestMapping("/pictext.add.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response , ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * 保存新增pictext
	 */
	@RequestMapping("/pictext.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		ModelAndView mnv =new ModelAndView();
		mnv.setViewName(getAddView());
		Pictext pictext = new Pictext();
		try {
			String onlypic = request.getParameter("onlypic");
			String remark = request.getParameter("remark");
			if(null != remark && !"".equals(remark)){
				pictext.setCreatetime(new Date());
				pictext.setUserId(user.getId());
				pictext.setComent(remark);
				this.pictextService.add(pictext);
				if (null != onlypic && !"".equals(onlypic)) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(onlypic);
					fileUpload.setFId(pictext.getId());
					this.fileUploadService.update(fileUpload);
				}
				showMessage(request, "新增图文成功");
			}
		} catch (AppException e) {
			logger.error("新增图文[" + pictext + "]失败", e);
			showMessage(request, "新增图文失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给pictext
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, pictext);
		}

		return mnv;
	}

	/**
	 * 保存修改的pictext
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Pictext pictext = null;
		try {
			pictext = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, pictext)) {
			//	return edit(request, response);
			//}

			this.pictextService.update(pictext);
			showMessage(request, "修改pictext成功");
		} catch (AppException e) {
			logger.error("修改pictext[" + pictext + "]失败", e);
			showMessage(request, "修改pictext失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的pictext
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] pictexts = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : pictexts) {
				this.pictextService.delete(id);
			}
			showMessage(request, "删除pictext成功");
		} catch (AppException e) {
			logger.error("批量删除pictext时失败", e);
			showMessage(request, "删除pictext失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setPictextService(PictextService pictextService) {
		this.pictextService = pictextService;
	}
}