package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.vo.Unit;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.util.TreeCodeTool;

/**
 * ��λ��������ݷ�����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:26:06
 * @version 1.0
 */
public class UnitDao extends HibernatePagedDao<Unit> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UnitDao.class);

	//��λ�����ı����ʽ
	private String codePattern = "3-3-3-3-3-3-3-3";

	@Override
	public void save(Object o) throws OrmException {
		Unit unit = (Unit) o;
		//�жϲ�����ʽ�Ƿ���ȷ
		if (!TreeCodeTool.isValidate(unit.getCode(), codePattern)) {
			logger.info("�õ�λ����[" + unit.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
			throw new OrmException("�õ�λ����[" + unit.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
		}

		//�ж��Ƿ�����ͬ����ĵ�λ��
		if (isNotUnique(unit, "code")) {
			logger.info("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
			throw new OrmException("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
		}

		//�ж��Ƿ�����ͬ����ĵ�λ��
		if (isNotUnique(unit, "departno")) {
			logger.info("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
			throw new OrmException("�õ�λ����[" + unit.getDepartno() + "]�Ѿ�����");
		}

		//�ж��Ƿ�����ͬ���Ƶĵ�λ��
		String sql;
		if(unit.getParentid() != null && !unit.getParentid().equals("")){
			sql = "from Unit where parentid = '" + String.valueOf(unit.getParentid()) + "' and name = '" + unit.getName() + "'";
		}else{
			sql = "from Unit where parentid = null and name = '" + unit.getName() + "'";
		}
		List unitlist = find(sql);
		if (unitlist.size() > 0) {
			logger.info("�õ�λ����[" + unit.getName() + "]�Ѿ�����");
			throw new OrmException("�õ�λ����[" + unit.getName() + "]�Ѿ�����");
		}

		//�жϸ������Ƿ����
		String fatherCode = TreeCodeTool.getFatherCode(unit.getCode(), codePattern);
		if (null != fatherCode && fatherCode.length() > 0) {
			Unit father = findUniqueBy("code", fatherCode);
			if (null == father) {
				logger.info("�õ�λ���ϼ���λ[" + fatherCode + "]������");
				throw new OrmException("�õ�λ���ϼ���λ[" + fatherCode + "]������");
			}
		}

		//���ñ��뼶�μ��Ƿ�׼�
		unit.setIdClass(TreeCodeTool.getLevel(unit.getCode(), codePattern));
		unit.setLeaf(true);

		// �����¼
		super.save(o);

		//��������ļ���
		confirmLeaf(fatherCode);
	}

	public List select(String sql) {
		return getHibernateTemplate().find(sql);
	}
	
	@Override
	public void update(Object o) throws OrmException {
		Unit unit = (Unit) o;

		//ȡ�������ľɱ���
		String oldCode = unit.getOthers().getStrParam1();
		//����޸��˲���룬����Ҫ������У��
		if (!unit.getCode().equals(oldCode)) {

			//1 �жϲ�����ʽ�Ƿ���ȷ
			if (!TreeCodeTool.isValidate(unit.getCode(), codePattern)) {
				logger.info("�õ�λ����[" + unit.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
				throw new OrmException("�õ�λ����[" + unit.getCode() + "]�����ϱ����ʽ[" + codePattern + "]");
			}

			//2 �ж�ԭ��λ�����Ƿ����ӵ�λ
			String hsql = "select count(*) from Unit where code!='" + oldCode + "' and code like '" + oldCode
					+ "%'";
			long count = (Long) getFirst(find(hsql));
			if (count > 0) {
				logger.info("��λ[" + oldCode + "]�����ӵ�λ��������������");
				throw new OrmException("��λ[" + oldCode + "]�����ӵ�λ��������������");
			}

			//3 �ж��Ƿ������±�����ͬ�ĵ�λ��
			if (isNotUnique(unit, "code")) {
				logger.info("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
				throw new OrmException("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
			}

			//�ж��Ƿ�����ͬ����ĵ�λ��
			if (isNotUnique(unit, "departno")) {
				logger.info("�õ�λ����[" + unit.getCode() + "]�Ѿ�����");
				throw new OrmException("�õ�λ����[" + unit.getDepartno() + "]�Ѿ�����");
			}

			//�ж��Ƿ�����ͬ���Ƶĵ�λ��
			if (isNotUnique(unit, "name")) {
				logger.info("�õ�λ����[" + unit.getName() + "]�Ѿ�����");
				throw new OrmException("�õ�λ����[" + unit.getName() + "]�Ѿ�����");
			}
			
			//4 �ж��±���ĸ������Ƿ����
			String fatherCode = TreeCodeTool.getFatherCode(unit.getCode(), codePattern);
			if (null != fatherCode && fatherCode.length() > 0) {
				Unit father = findUniqueBy("code", fatherCode);
				if (null == father) {
					logger.info("�õ�λ���ϼ���λ[" + fatherCode + "]������");
					throw new OrmException("�õ�λ���ϼ���λ[" + fatherCode + "]������");
				}
			}

			//����޸��˱��룬��һ��Ϊ�׼�
			unit.setLeaf(true);
		}

		//���ñ��뼶��
		unit.setIdClass(TreeCodeTool.getLevel(unit.getCode(), codePattern));

		super.update(o);

		if (!unit.getCode().equals(oldCode)) {
			//����ɵĸ���λ
			confirmLeaf(TreeCodeTool.getFatherCode(oldCode, codePattern));

			//�����µĸ���λ
			confirmLeaf(TreeCodeTool.getFatherCode(unit.getCode(), codePattern));
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		//���Ȼ�øõ�λ����Ϣ
		Unit unit = get(id);
		if (null == unit) {
			logger.info("��λ[" + unit.getName() + "]�����ڻ��Ѿ���ɾ����");
			throw new OrmException("��λ[" + unit.getName() + "]�����ڻ��Ѿ���ɾ����");
		}

		//�����λ���ӵ�λ��������ɾ��
		String hsql = "select count(*) from Unit where code!='" + unit.getCode() + "' and code like '"
				+ unit.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));
		if (count > 0) {
			logger.info("��λ[" + unit.getName() + "]���ӵ�λ�����ɾ���ӵ�λ��");
			throw new OrmException("��λ[" + unit.getName() + "]���ӵ�λ�����ɾ���ӵ�λ��");
		}

		super.removeById(id);

		//������λ���Ƿ�׼�
		confirmLeaf(TreeCodeTool.getFatherCode(unit.getCode(), codePattern));
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

		Unit unit = findUniqueBy("code", treeCode);
		String hsql = "select count(*) from Unit where code <> '" + unit.getCode() + "' and code like '"
				+ unit.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));

		if (count == 0 && unit.getLeaf() == Boolean.FALSE) {
			unit.setLeaf(true);
		}

		if (count > 0 && unit.getLeaf() == Boolean.TRUE) {
			unit.setLeaf(false);
		}
	}
}
