package com.itsv.gbp.core.orm.paged;

import java.util.List;

/**
 * ˵�������OR mapper��������ݷ�ҳ�����ӿڡ�<br>
 * 
 * ִ��ʱ���ȸ��ýӿ�ʵ�����趨����ֵ��Ҫ��õ���ʼ�ͽ�����¼�ţ�Ȼ�󽫸ö�����־û�����<br>
 * ִ�н�����ͨ��getSource()��ò�ѯ����б�ͨ��getTotalNum()��ò�ѯ���ļ�¼��<br>
 * 
 * @author admin 2004-9-24
 */
public interface IPagedList {

	// �����ݿ�����ѯʱ�Ĳ�ѯ����
	public abstract void setParam(Object param);

	public abstract Object getParam();

	// �����ݿ�����ѯ����ʱ����ʼ�ļ�¼�š�����������¼
	public abstract int getQueryStartNum();

	// �����ݿ�����ѯ����ʱ�������ļ�¼�š�����������¼
	public abstract int getQueryEndNum();

	// ���ò�ѯ���ļ�¼
	public abstract void setSource(List source);

	// ��ò�ѯ���ļ�¼
	public abstract List getSource();

	// �����ܵļ�¼��
	public abstract void setTotalNum(int total);

	public abstract int getTotalNum();

	// ���β�ѯ����ʼ��¼��
	public void setStart(int start);

	public int getStart();
}