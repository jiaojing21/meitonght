package com.itsv.platform.common.messageMgr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.platform.KeyGenerator;
import com.itsv.platform.UuidGenerator;
import com.itsv.platform.common.fileMgr.UploadFileObj;
import com.itsv.platform.common.fileMgr.UploadFileService;
import com.itsv.platform.common.fileMgr.util.SmartRequest;
import com.itsv.platform.common.fileMgr.util.SmartUpload;
import com.itsv.platform.common.messageDetail.vo.Itsv_message_receive;
import com.itsv.platform.common.messageMgr.bo.Itsv_messageService;
import com.itsv.platform.common.messageMgr.vo.Itsv_message;

/**
 * ˵�������ӣ��޸ģ�ɾ����Ϣ��ǰ�˴�����
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_messageController extends BaseCURDController<Itsv_message> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(Itsv_messageController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.itsv_message";

	// �߼������
	private Itsv_messageService itsv_messageService;

	// �������
	private UploadFileService fileService;

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
	
		Itsv_message itsv_message = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			itsv_message = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", itsv_message);
		} else {
			itsv_message = new Itsv_message();
		}
		itsv_message.setUserid(this.itsv_messageService.getMsgSenderId());
		this.itsv_messageService.queryByVO(records, itsv_message);
	}

	// ��ʾ������Ϣҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	/**
	 * ����������Ϣ
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_message itsv_message = null;
		try {
			SmartUpload smartUpload = new SmartUpload();
			try{
				smartUpload.initialize(this.getServletContext(), request, response);
				smartUpload.upload();
			}catch(ServletException e){}catch(Exception e){}

			SmartRequest smartRequest = smartUpload.getRequest();

			itsv_message = (Itsv_message)this.getDataObjWithFile(smartRequest, response, Itsv_message.class);
			
//			itsv_message = new Itsv_message();
//			itsv_message.setMsg_type(Long.valueOf(smartRequest.getParameter("p_Itsv_message_msg_type")));
//			itsv_message.setMsg_title(smartRequest.getParameter("p_Itsv_message_msg_title"));
//			itsv_message.setMsg_content(smartRequest.getParameter("p_Itsv_message_msg_content"));
//			itsv_message.setuserids(smartRequest.getParameter("p_Itsv_message_userids"));
//			itsv_message.setusernames(smartRequest.getParameter("p_Itsv_message_usernames"));
			
			////-----------------------------
			//// updated by ace8
			////String msgid = 	(long) KeyGenerator.getInstance().getNextKey(itsv_message.getClass())+"";
			////-----------------------------
			String msgid =
				UuidGenerator.getUUID();
			itsv_message.setId(msgid);
			itsv_message.setCreate_time((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			itsv_message.setSent_time((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			itsv_message.setMsg_status(Long.valueOf("0"));
			try {
				// ���ô洢���
				UploadFileObj fileObj = this.fileService
						.getUploadFileObj("�������");
				// ���渽��
				List list = this.fileService.saveFile(smartUpload, "p_Itsv_message_file_names", fileObj);
				String fileids = "";
				String filenames = "";
				for (int i = 0; i < list.size(); i++) {
					com.itsv.platform.common.fileMgr.vo.ZjWjys wjysobj = (com.itsv.platform.common.fileMgr.vo.ZjWjys) list
							.get(i);
					fileids += String.valueOf(wjysobj.getId()) + ",";
					filenames += String.valueOf(wjysobj.getWjmc()) + ",";
				}
				if(!"".equalsIgnoreCase(fileids))
					fileids = fileids.substring(0, fileids.length() - 1);
				if(!"".equalsIgnoreCase(filenames))
					filenames = filenames.substring(0, filenames.length() - 1);
				itsv_message.setFile_ids(fileids);
				itsv_message.setFile_names(filenames);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] userids = itsv_message.getuserids().split(",");
			String[] usernames = itsv_message.getusernames().split(",");
			for (int i = 0; i < userids.length; i++) {
				String msgreceiverid = (long) KeyGenerator.getInstance()
						.getNextKey(Itsv_message_receive.class)+"";
				Itsv_message_receive itsv_message_receive = new Itsv_message_receive();
				itsv_message_receive.setId(msgreceiverid);
				itsv_message_receive.setMsg_id(msgid);
				itsv_message_receive.setReceiver_id((userids[i]));
				itsv_message_receive.setReceiver_name(usernames[i]);
				itsv_message_receive.setReceive_status(Long.valueOf("0"));
				itsv_message_receive.setReceive_time((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				itsv_message_receive.setView_times(Long.valueOf("0"));
				this.itsv_messageService.addDetail(itsv_message_receive);
			}

			this.itsv_messageService.add(itsv_message);

			showMessage(request, "������Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("������Ϣ[" + itsv_message + "]ʧ��", e);
			showMessage(request, "������Ϣʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ����Ϣ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					itsv_message);
		}

		return query(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setItsv_messageService(Itsv_messageService itsv_messageService) {
		this.itsv_messageService = itsv_messageService;
	}

	public UploadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UploadFileService fileService) {
		this.fileService = fileService;
	}

}