package com.itsv.annotation.message.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.message.bo.MessageService;
import com.itsv.annotation.message.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除message的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class MessageController extends BaseAnnotationController<Message> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(MessageController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.message";
	@Autowired
	private MessageService messageService; //逻辑层对象
	
	@Autowired
	private UserService userService;//逻辑层对象

	public MessageController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/message/add");
		super.setIndexView("meitong/message/index");
		super.setEditView("meitong/message/edit");

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
		Message message = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			message = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", message);
		} else {
			message = new Message();
		}

		this.messageService.queryByVO(records, message);
	}

	//显示增加message页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改message页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Message message = this.messageService.queryById(id);
		if (null == message) {
			showMessage(request, "未找到对应的message记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, message);
		}
	}

	// 查询数据准备
	@RequestMapping("/message.query.htm")
	public ModelAndView queryMessage(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		mnv.setViewName(getIndexView());
		return mnv;
	}

	// 查询列表
	@RequestMapping("/message.BeforeData.htm")
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
		Message message = new Message();
		message.setTitle(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"message");
		this.messageService.queryByVO(records, message);
		for(int i = 0;i<records.getSource().size();i++){
			Message m = (Message)records.getSource().get(i);
			m.setSender(this.userService.queryById(m.getSender()).getRealName());
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//添加前数据
	@RequestMapping("/message.beforeadd.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * 保存新增message
	 */
	@RequestMapping("/message.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
  		User user = adapter.getRealUser();
  		ModelAndView mnv = new ModelAndView();
  		mnv.setViewName(getAddView());
		Message message = null;
		try {
			message = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, message)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, message);
			//}
			message.setCreatetime(new Date());
			message.setSender(user.getId());
			this.messageService.add(message);

			showMessage(request, "新增消息成功");
		} catch (AppException e) {
			logger.error("新增消息[" + message + "]失败", e);
			showMessage(request, "新增消息失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给message
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, message);
		}

		return mnv;
	}

	/**
	 * 保存修改的message
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Message message = null;
		try {
			message = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, message)) {
			//	return edit(request, response);
			//}

			this.messageService.update(message);
			showMessage(request, "修改message成功");
		} catch (AppException e) {
			logger.error("修改message[" + message + "]失败", e);
			showMessage(request, "修改message失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的message
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] messages = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : messages) {
				this.messageService.delete(id);
			}
			showMessage(request, "删除message成功");
		} catch (AppException e) {
			logger.error("批量删除message时失败", e);
			showMessage(request, "删除message失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}