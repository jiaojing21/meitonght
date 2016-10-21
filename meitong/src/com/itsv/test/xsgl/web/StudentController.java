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
 * ˵�������ӣ��޸ģ�ɾ��student��ǰ�˴�����
 * 
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class StudentController extends BaseCURDController<Student> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(StudentController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.student";

	private StudentService studentService; //�߼������

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Student student = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			student = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", student);
		} else {
			student = new Student();
		}

		this.studentService.queryByVO(records, student);
	}

	//��ʾ����studentҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�studentҳ��ǰ��׼������
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
			showMessage(request, "δ�ҵ���Ӧ��student��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, student);
		}
	}

	/**
	 * ��������student
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Student student = null;
		try {
			student = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//Ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, student)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, student);
			//}

			this.studentService.add(student);

			showMessage(request, "����student�ɹ�");
		} catch (AppException e) {
			logger.error("����student[" + student + "]ʧ��", e);
			showMessage(request, "����studentʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��student
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, student);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�student
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Student student = null;
		try {
			student = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//Ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, student)) {
			//	return edit(request, response);
			//}

			this.studentService.update(student);
			showMessage(request, "�޸�student�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�student[" + student + "]ʧ��", e);
			showMessage(request, "�޸�studentʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�student
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] students = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : students) {
				this.studentService.delete(id);
			}
			showMessage(request, "ɾ��student�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��studentʱʧ��", e);
			showMessage(request, "ɾ��studentʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}