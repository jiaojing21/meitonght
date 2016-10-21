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
 * ˵�������ӣ��޸ģ�ɾ��kefu��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class KefuController extends BaseAnnotationController<Kefu> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(KefuController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.kefu";
	@Autowired
	private KefuService kefuService; //�߼������

	public KefuController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/kefu/add");
		super.setIndexView("meitong/kefu/index");
		super.setEditView("meitong/kefu/edit");

	}

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Kefu kefu = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			kefu = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", kefu);
		} else {
			kefu = new Kefu();
		}

		this.kefuService.queryByVO(records, kefu);
	}

	//��ʾ����kefuҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�kefuҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Kefu kefu = this.kefuService.queryById(id);
		if (null == kefu) {
			showMessage(request, "δ�ҵ���Ӧ��kefu��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, kefu);
		}
	}
	//���ǰ����
	@RequestMapping("/kefu.beforeadd.htm")
	public ModelAndView beforeadd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * ���������ͷ�
	 */
	@RequestMapping("/kefu.saveadd.htm")
	public ModelAndView saveAdd1(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView();
		Kefu kefu = null;
		try {
			kefu = param2Object(request);
			this.kefuService.add(kefu);

			showMessage(request, "�����ͷ��ɹ�");
		} catch (AppException e) {
			logger.error("�����ͷ�[" + kefu + "]ʧ��", e);
			showMessage(request, "�����ͷ�ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��kefu
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, kefu);
		}
		mnv.setViewName(getAddView());
		return mnv;
	}
	//��ѯǰ����
		@RequestMapping("/kefu.query.htm")
		public ModelAndView queryKefu(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
			mnv.setViewName(getIndexView());
			return mnv;
		}
		//��ѯ�б�
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
	//�༭ǰ����׼��
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
	 * �����޸ĵ�kefu
	 */
	@RequestMapping("/kefu.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Kefu kefu = null;
		try {
			kefu = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, kefu)) {
			//	return edit(request, response);
			//}

			this.kefuService.update(kefu);
			showMessage(request, "�޸Ŀͷ��ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ŀͷ�[" + kefu + "]ʧ��", e);
			showMessage(request, "�޸Ŀͷ�ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, kefu);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, kefu);
  	    
	}
	/**
	 * ɾ��ѡ�е�kefu
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] kefus = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : kefus) {
				this.kefuService.delete(id);
			}
			showMessage(request, "ɾ��kefu�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��kefuʱʧ��", e);
			showMessage(request, "ɾ��kefuʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setKefuService(KefuService kefuService) {
		this.kefuService = kefuService;
	}
}