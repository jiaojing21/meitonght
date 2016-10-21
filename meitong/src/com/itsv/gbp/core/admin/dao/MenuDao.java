package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.util.TreeCodeTool;

/**
 * �˵���������ݷ����ࡣ<br>
 * 
 * ��ʾ��ɾ���˵�ʱͨ��Hsql����Ƿ����н�ɫʹ�á�<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:25:04
 * @version 1.0
 */
public class MenuDao extends HibernatePagedDao<Menu> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuDao.class);

	//�˵������ı����ʽ
	private String codePattern = "3-3-3-3";

	@Override
	public void save(Object o) throws OrmException {
		Menu menu = (Menu) o;
		//�жϲ�����ʽ�Ƿ���ȷ
		if (!TreeCodeTool.isValidate(menu.getCode(), codePattern)) {
			logger.info("�ò˵�����[" + menu.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
			throw new OrmException("�ò˵�����[" + menu.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
		}

		//�ж��Ƿ�����ͬ����Ĳ˵���
		if (isNotUnique(menu, "code")) {
			logger.info("�ò˵�����[" + menu.getCode() + "]�Ѿ�����");
			throw new OrmException("�ò˵�����[" + menu.getCode() + "]�Ѿ�����");
		}

		//�жϸ������Ƿ����
		String fatherCode = TreeCodeTool.getFatherCode(menu.getCode(), codePattern);
		if (null != fatherCode && fatherCode.length() > 0) {
			Menu father = findUniqueBy("code", fatherCode);
			if (null == father) {
				logger.info("�ò˵����ϼ��˵�[" + fatherCode + "]������");
				throw new OrmException("�ò˵����ϼ��˵�[" + fatherCode + "]������");

			}
		}

		//���ñ��뼶�μ��Ƿ�׼�
		menu.setIdClass(TreeCodeTool.getLevel(menu.getCode(), codePattern));
		menu.setLeaf(true);

		// �����¼
		super.save(o);

		//��������ļ���
		confirmLeaf(fatherCode);
	}

	@Override
	public void update(Object o) throws OrmException {
		Menu menu = (Menu) o;

		//ȡ�������ľɱ���
		String oldCode = menu.getOthers().getStrParam1();
		//����޸��˲���룬����Ҫ������У��
		if (!menu.getCode().equals(oldCode)) {

			//1 �жϲ�����ʽ�Ƿ���ȷ
			if (!TreeCodeTool.isValidate(menu.getCode(), codePattern)) {
				logger.info("�ò˵�����[" + menu.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
				throw new OrmException("�ò˵�����[" + menu.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
			}

			//2 �ж�ԭ�˵������Ƿ����Ӳ˵�
			String hsql = "select count(*) from Menu where code!='" + oldCode + "' and code like '" + oldCode
					+ "%'";
			long count = (Long) getFirst(find(hsql));
			if (count > 0) {
				logger.info("�˵�[" + oldCode + "]�����Ӳ˵���������������");
				throw new OrmException("�˵�[" + oldCode + "]�����Ӳ˵���������������");
			}

			//3 �ж��Ƿ������±�����ͬ�Ĳ˵���
			if (isNotUnique(menu, "code")) {
				logger.info("�ò˵�����[" + menu.getCode() + "]�Ѿ�����");
				throw new OrmException("�ò˵�����[" + menu.getCode() + "]�Ѿ�����");
			}

			//4 �ж��±���ĸ������Ƿ����
			String fatherCode = TreeCodeTool.getFatherCode(menu.getCode(), codePattern);
			if (null != fatherCode && fatherCode.length() > 0) {
				Menu father = findUniqueBy("code", fatherCode);
				if (null == father) {
					logger.info("�ò˵����ϼ��˵�[" + fatherCode + "]������");
					throw new OrmException("�ò˵����ϼ��˵�[" + fatherCode + "]������");
				}
			}

			//����޸��˱��룬��һ��Ϊ�׼�
			menu.setLeaf(true);
		}

		//���ñ��뼶��
		menu.setIdClass(TreeCodeTool.getLevel(menu.getCode(), codePattern));

		super.update(o);

		if (!menu.getCode().equals(oldCode)) {
			//����ɵĸ��˵�
			confirmLeaf(TreeCodeTool.getFatherCode(oldCode, codePattern));

			//�����µĸ��˵�
			confirmLeaf(TreeCodeTool.getFatherCode(menu.getCode(), codePattern));
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		// �жϸò˵��Ƿ��ѷ������ɫ
		String hsql = "select count(*) from RoleMenu where menuId = ?";
		long count = (Long) getFirst(find(hsql, id));
		if (count > 0) {
			logger.info("ɾ���˵�" + id + "ʧ�ܣ��ò˵��ѷ���� " + count + " ����ɫʹ��");
			throw new OrmException("�ò˵��ѷ���� " + count + " ����ɫʹ��");
		}

		//���Ȼ�øò˵�����Ϣ
		Menu menu = get(id);
		if (null == menu) {
			logger.info("�˵�[" + id + "]�����ڻ��Ѿ���ɾ����");
			throw new OrmException("�˵�[" + id + "]�����ڻ��Ѿ���ɾ����");
		}

		//����˵����Ӳ˵���������ɾ��
		hsql = "select count(*) from Menu where code!='" + menu.getCode() + "' and code like '"
				+ menu.getCode() + "%'";
		count = (Long) getFirst(find(hsql));
		if (count > 0) {
			logger.info("�˵�[" + menu.getCode() + "]���Ӳ˵������ɾ���Ӳ˵���");
			throw new OrmException("�˵�[" + menu.getCode() + "]���Ӳ˵������ɾ���Ӳ˵���");
		}

		super.removeById(id);

		//�����˵����Ƿ�׼�
		confirmLeaf(TreeCodeTool.getFatherCode(menu.getCode(), codePattern));
	}

	/**
	 * ���ݸ����Ĳ���룬ȷ���ò�����Ƿ�Ϊ��׼�
	 * 
	 * @param treeCode
	 */
	private void confirmLeaf(String treeCode) {
		if (treeCode == null || treeCode.length() == 0) {
			return;
		}

		Menu menu = findUniqueBy("code", treeCode);
		String hsql = "select count(*) from Menu where code <> '" + menu.getCode() + "' and code like '"
				+ menu.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));

		if (count == 0 && menu.getLeaf() == Boolean.FALSE) {
			menu.setLeaf(true);
		}

		if (count > 0 && menu.getLeaf() == Boolean.TRUE) {
			menu.setLeaf(false);
		}
	}
}
