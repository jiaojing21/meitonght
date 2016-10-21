package com.itsv.platform.common.messageDetail.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.itsv.platform.common.fileMgr.UploadFileService;
import com.itsv.platform.common.messageDetail.bo.Itsv_message_receiveService;
import com.itsv.platform.common.messageDetail.vo.Itsv_message_receive;
import com.itsv.platform.common.messageMgr.bo.Itsv_messageService;
import com.itsv.platform.common.messageMgr.vo.Itsv_message;

/**
 * 说明：增加，修改，删除接收消息的前端处理类
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receiveController extends
		BaseCURDController<Itsv_message_receive> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(Itsv_message_receiveController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.itsv_message_receive";

	private Itsv_message_receiveService itsv_message_receiveService; // 逻辑层对象
	private Itsv_messageService itsv_messageService; // 逻辑层对象

	// 附件组件
	private UploadFileService fileService;

	private String hintView; // 详细信息显示页面

	private String detailView; // 接收信息显示页面

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
		Itsv_message_receive itsv_message_receive = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			itsv_message_receive = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", itsv_message_receive);
		} else {
			itsv_message_receive = new Itsv_message_receive();
		}
		if (itsv_message_receive == null) {
			itsv_message_receive = new Itsv_message_receive();
		}
		Object status = request.getAttribute("p_Itsv_message_receive_receive_status");
		if(status != null && status != ""){
			itsv_message_receive.setReceive_status(Long.valueOf(String.valueOf(status)));
		}
		itsv_message_receive.setReceiver_id(this.itsv_message_receiveService
				.getCurUserID());
		this.itsv_message_receiveService.queryByVO(records,
				itsv_message_receive);
		mnv.addObject("condition", itsv_message_receive);
		for(int i = 0; i < records.getList().size(); i++){
			Itsv_message_receive msg = (Itsv_message_receive)records.getList().get(i);
			msg.setMsg_title(this.itsv_messageService.queryById(msg.getMsg_id()).getMsg_title());
		}
	}

	// 显示修改接收消息页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		Itsv_message_receive qryitsv_message_receive_obj = this
				.param2Object(request);
		String msgid = "";
		String detailid ="";
		
		try {
			msgid = ServletRequestUtils
					.getStringParameter(request, "msg_id");
			detailid =ServletRequestUtils.getStringParameter(request,
			"detail_id");			
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		Itsv_message itsv_message_obj = new Itsv_message();
		itsv_message_obj.setId(msgid);
		Itsv_message itsv_message = this.itsv_message_receiveService
				.queryMsg(itsv_message_obj);
		if (null == itsv_message) {
			showMessage(request, "未找到对应的接收消息记录。请重试");
			mnv = query(request, response);
		} else {
			Itsv_message_receive itsv_message_receive = this.itsv_message_receiveService
					.queryById(detailid);
			itsv_message_receive.setView_times(itsv_message_receive
					.getView_times() + 1);
			itsv_message_receive.setLast_view_time((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			itsv_message_receive.setReceive_status(Long.valueOf("1"));
			this.itsv_message_receiveService.update(itsv_message_receive);
			mnv.addObject("receive_status", qryitsv_message_receive_obj
					.getReceive_status());
			mnv.addObject(WebConfig.DATA_NAME, itsv_message);
		}
	}

	public ModelAndView showHint(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getEditView());
		Itsv_message_receive itsv_message_receive = new Itsv_message_receive();
		itsv_message_receive.setReceive_status(Long.valueOf("0"));
		itsv_message_receive.setReceiver_id(this.itsv_message_receiveService
				.getCurUserID());
		List listObj = this.itsv_message_receiveService
				.queryByVO(itsv_message_receive);
		mnv.addObject(WebConfig.DATA_NAME, listObj);
		mnv.setViewName(this.getHintView());
		return mnv;
	}

	public ModelAndView showDetail(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getDetailView());
		Itsv_message_receive itsv_message_receive = (Itsv_message_receive) getDataObj(
				request, response, Itsv_message_receive.class);
		List listObj = this.itsv_message_receiveService
				.queryByVO(itsv_message_receive);
		mnv.addObject(WebConfig.DATA_NAME, listObj);
		return mnv;
	}

	/**
	 * 下载附件
	 */
	public ModelAndView downFile(HttpServletRequest request,
			HttpServletResponse response) {
		// 读取附件编号
		String fileid = request.getParameter("fileid");
		com.itsv.platform.common.fileMgr.vo.ZjWjys wjys;
		try {
			// 查询附件信息
			wjys = this.fileService.downFile(fileid);
			if (wjys == null) {
				throw new Exception("查询附件信息失败！");
			}
			this.fileService.doFileData(request, response, wjys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除选中的接收消息
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_message_receive qryitsv_message_receive_obj = this
		.param2Object(request);
		String[] itsv_message_receives = ServletRequestUtils.getStringParameters(
				request, "p_id");
		// 允许部分删除成功
		try {
			for (String id : itsv_message_receives) {
				Itsv_message_receive obj = this.itsv_message_receiveService.queryById(id);
				obj.setReceive_status(Long.valueOf("2")); //表示删除
				//this.itsv_message_receiveService.delete(id);
				this.itsv_message_receiveService.update(obj);
			}
			showMessage(request, "删除接收消息成功");
		} catch (AppException e) {
			logger.error("批量删除接收消息时失败", e);
			showMessage(request, "删除接收消息失败：" + e.getMessage(), e);
		}
		request.setAttribute("p_Itsv_message_receive_receive_status", qryitsv_message_receive_obj.getReceive_status());
		return query(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setItsv_message_receiveService(
			Itsv_message_receiveService itsv_message_receiveService) {
		this.itsv_message_receiveService = itsv_message_receiveService;
	}

	public UploadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UploadFileService fileService) {
		this.fileService = fileService;
	}

	public String getHintView() {
		return hintView;
	}

	public void setHintView(String hintView) {
		this.hintView = hintView;
	}

	public String getDetailView() {
		return detailView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public Itsv_messageService getItsv_messageService() {
		return itsv_messageService;
	}

	public void setItsv_messageService(Itsv_messageService itsv_messageService) {
		this.itsv_messageService = itsv_messageService;
	}

}