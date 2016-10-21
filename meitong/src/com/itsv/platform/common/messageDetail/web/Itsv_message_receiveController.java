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
 * ˵�������ӣ��޸ģ�ɾ��������Ϣ��ǰ�˴�����
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receiveController extends
		BaseCURDController<Itsv_message_receive> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(Itsv_message_receiveController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.itsv_message_receive";

	private Itsv_message_receiveService itsv_message_receiveService; // �߼������
	private Itsv_messageService itsv_messageService; // �߼������

	// �������
	private UploadFileService fileService;

	private String hintView; // ��ϸ��Ϣ��ʾҳ��

	private String detailView; // ������Ϣ��ʾҳ��

	// ���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Itsv_message_receive itsv_message_receive = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			itsv_message_receive = param2Object(request);

			// ����ѯ�������ظ�ҳ��
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

	// ��ʾ�޸Ľ�����Ϣҳ��ǰ��׼������
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
			showMessage(request, "δ�ҵ���Ӧ�Ľ�����Ϣ��¼��������");
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
	 * ���ظ���
	 */
	public ModelAndView downFile(HttpServletRequest request,
			HttpServletResponse response) {
		// ��ȡ�������
		String fileid = request.getParameter("fileid");
		com.itsv.platform.common.fileMgr.vo.ZjWjys wjys;
		try {
			// ��ѯ������Ϣ
			wjys = this.fileService.downFile(fileid);
			if (wjys == null) {
				throw new Exception("��ѯ������Ϣʧ�ܣ�");
			}
			this.fileService.doFileData(request, response, wjys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ɾ��ѡ�еĽ�����Ϣ
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_message_receive qryitsv_message_receive_obj = this
		.param2Object(request);
		String[] itsv_message_receives = ServletRequestUtils.getStringParameters(
				request, "p_id");
		// ������ɾ���ɹ�
		try {
			for (String id : itsv_message_receives) {
				Itsv_message_receive obj = this.itsv_message_receiveService.queryById(id);
				obj.setReceive_status(Long.valueOf("2")); //��ʾɾ��
				//this.itsv_message_receiveService.delete(id);
				this.itsv_message_receiveService.update(obj);
			}
			showMessage(request, "ɾ��������Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��������Ϣʱʧ��", e);
			showMessage(request, "ɾ��������Ϣʧ�ܣ�" + e.getMessage(), e);
		}
		request.setAttribute("p_Itsv_message_receive_receive_status", qryitsv_message_receive_obj.getReceive_status());
		return query(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
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