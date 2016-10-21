package com.itsv.gbp.core.orm.paged;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * ˵�����ɻ�������ݷ�ҳ���� <br>
 * ����ô���ǵģ�<br>
 * ���ڴ������ݣ�����10000�����϶�����һ��ȫȡ������
 * ͨ����ÿ��ȡһҳ�����ݣ���20������ʵЧ��Ҳ���ߡ�
 * �����ÿ��ȡ����(����)10ҳ�����ݣ�����session���������10ҳ�Ż��һ�����ݿ⡣ 
 * Ĭ�ϵģ� <br>
 * DEFAULT_PAGE_SIZE=20����ÿҳ��ʾ20������
 * DEFAULT_PAGE_NUM=2����ÿ�β�ѯ�����2ҳ�����ݣ���������
 * 
 * @author admin 2004-7-29
 */
public class CachePagedList implements Serializable, IPagedList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5529301152408992565L;

	/**
	 * ��session���/ȡ�б����ʱ���Ƽ�ͳһ���������
	 */
	public static final String NAME = "tmp_data_lists";

	/**
	 * ȱʡ��ҳ���С
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * ȱʡ������ٸ�ҳ��
	 */
	public static final int DEFAULT_PAGE_NUM = 1;

	// ҳ���С �� ÿ��ҳ����ʾ�ļ�¼��
	private int pageSize;

	// �����ҳ�����
	private int pageNum;

	// ʵ�ʵ������б�
	private List source;

	// �ܼ�¼�������Ǳ�������е��ܼ�¼�����������ܵõ���ȫ����¼����
	// -1��ʾ��δ��ѯ�����ݿ�
	private int totalNum = -1;

	// �����������м�¼�ĵ���ʼ�ţ�Ӧ���Ǵ�1��ʼ��
	private int start = 1;

	// ��ǰҳ�����
	private int page = 1;

	// ��Ӧ�Ĳ�ѯ��������
	private Object param;

	// ����ѯ������ơ���Ϊ��������session�Ϊ�˽�������ѯ���𿪣���Ҫ�������
	private String queryName;

	/**
	 * �չ��췽��
	 */
	public CachePagedList() {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUM, "");
	}

	/**
	 * ���췽��
	 * 
	 * @param queryName ��ѯ����
	 */
	public CachePagedList(String queryName) {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUM, queryName);
	}

	/**
	 * ���췽��
	 * 
	 * @param pageSize ҳ���С
	 * @param pageNum ����ҳ�����
	 */
	public CachePagedList(int pageSize, int pageNum) {
		this(pageSize, pageNum, "");
	}

	/**
	 * ���췽��
	 * 
	 * @param pageSize ҳ���С
	 * @param pageNum ����ҳ�����
	 * @param queryName ��ѯ����
	 */
	public CachePagedList(int pageSize, int pageNum, String queryName) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.queryName = queryName;
		this.source = Collections.EMPTY_LIST;
	}

	// �����ҳ��
	public int getTotalPage() {
		if (this.getTotalNum() <= 0)
			return 0;

		float nrOfPages = (float) this.getTotalNum() / this.pageSize;
		return (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
	}

	// ��Ϊ�ڼ���¼�Ƿ�����ڸö���ǰ��
	// �����Ѿ��õ�page����Ϣ����ʱ�ٴ����������
	// �п��ܴ���Ϊ������Χ��ҳ������ɲ���Ҫ�Ĵ���
	// @auther �ܱؿ�2004-11-16�޸�
	// �����ҳ��¼�Ƿ�����ڸö�����
	public boolean exists() {
		return exists(getPage());
	}

	private boolean exists(int page) {
		// ���totalNumΪ-1�����ʾ��û�в�ѯ�����ݿ�
		if (this.getTotalNum() < 0) {
			return false;
		}
		if (page < 1 || page > this.getTotalPage()) {
			return false;
		}

		int end = this.getPageSize() * page;
		if (end > this.getTotalNum()) {
			end = this.getTotalNum();
		}

		if (this.getStart() <= this.getPageSize() * (page - 1) + 1
				&& this.getStart() + this.getSource().size() > end) {
			return true;
		}
		return false;
	}

	// �Ƿ�Ϊ��ҳ
	public boolean isFirstPage() {
		return this.page == 1;
	}

	// �Ƿ�Ϊ��ĩҳ
	public boolean isLastPage() {
		return this.page == this.getTotalPage();
	}

	// ���ص�ǰҳ������ݼ�¼����ָ��ҳ��
	public List getList() {
		if (!this.exists(this.getPage())) {
			return Collections.EMPTY_LIST;
		}
		if (this.getTotalNum() <= 0) {
			return Collections.EMPTY_LIST;
		}

		int start = this.getPageSize() * (this.page - 1) + 1 - this.getStart();
		int end = this.getPageSize() * this.page - this.getStart();
		if (end > this.getTotalNum() - this.getStart()) {
			end = this.getTotalNum() - this.getStart();
		}
		return this.source.subList(start, end + 1);
	}

	// �����������ȡ��¼���������������ǵ�33ҳ�������ǻ�ӵ�31ҳ�ļ�¼��ʼȡ
	// Ace8 changed by 2004.9.3

	// �����ݿ�����ѯ����ʱ����ʼ�ļ�¼�š�����������¼
	public int getQueryStartNum() {
		int remain = (this.getPage() - 1) % this.getPageNum();
		return (this.getPage() - remain - 1) * this.getPageSize() + 1;
	}

	// �����ݿ�����ѯ����ʱ�������ļ�¼�š�����������¼
	public int getQueryEndNum() {
		return this.getQueryStartNum() + this.getPageSize() * this.getPageNum() - 1;
	}

	/** ========== �򵥴�ȡ���� =========== */

	public void setSource(List source) {
		this.source = source;
	}

	public List getSource() {
		return source;
	}

	public void setPageSize(int pageSize) {
		if (pageSize != this.pageSize) {
			this.pageSize = pageSize;
			this.page = 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPage() {
		return this.page;
	}

	// �������ò����ڵ�ҳ��
	// Ace8 changed by 2004.9.3
	public void setPage(int page) {
		if (page == 1) {// ��һҳ�Ƚ����⣬��ʹû�м�¼����Ҳ��������ҳ��Ϊ1
			this.page = page;
		} else if (page > 0 && page <= this.getTotalPage()) { // ��������£�������ҳ��Խ��
			this.page = page;
		}
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
}
