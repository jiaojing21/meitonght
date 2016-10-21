package com.itsv.test.xsgl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.test.xsgl.bo.StudentService;
import com.itsv.test.xsgl.vo.Student;

/**
 * 说明：增加，修改，删除student的前端处理类
 * 
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class StudentController extends BaseCURDController<Student> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(StudentController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.student";

	private StudentService studentService; //逻辑层对象

	//覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Student student = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			student = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", student);
		} else {
			student = new Student();
		}

		this.studentService.queryByVO(records, student);
	}

	//显示增加student页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改student页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id="";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		Student student = this.studentService.queryById(id);
		if (null == student) {
			showMessage(request, "未找到对应的student记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, student);
		}
	}

	/**
	 * 保存新增student
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Student student = null;
		try {
			student = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//Ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, student)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, student);
			//}

			this.studentService.add(student);

			showMessage(request, "新增student成功");
		} catch (AppException e) {
			logger.error("新增student[" + student + "]失败", e);
			showMessage(request, "新增student失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给student
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, student);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的student
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Student student = null;
		try {
			student = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//Ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, student)) {
			//	return edit(request, response);
			//}

			this.studentService.update(student);
			showMessage(request, "修改student成功");
		} catch (AppException e) {
			logger.error("修改student[" + student + "]失败", e);
			showMessage(request, "修改student失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的student
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] students = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : students) {
				this.studentService.delete(id);
			}
			showMessage(request, "删除student成功");
		} catch (AppException e) {
			logger.error("批量删除student时失败", e);
			showMessage(request, "删除student失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}