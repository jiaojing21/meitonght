package com.itsv.gbp.core.orm.paged;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 说明：可缓存的数据分页对象。 <br>
 * 是这么考虑的：<br>
 * 对于大量数据，比如10000条，肯定不能一次全取出来。
 * 通常是每次取一页的数据，如20条，其实效率也不高。
 * 最好是每次取出来(比如)10页的数据，放在session里。这样，翻10页才会查一次数据库。 
 * 默认的： <br>
 * DEFAULT_PAGE_SIZE=20，即每页显示20条数据
 * DEFAULT_PAGE_NUM=2，即每次查询都查出2页的数据，缓存起来
 * 
 * @author admin 2004-7-29
 */
public class CachePagedList implements Serializable, IPagedList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5529301152408992565L;

	/**
	 * 向session里存/取列表对象时，推荐统一用这个名字
	 */
	public static final String NAME = "tmp_data_lists";

	/**
	 * 缺省的页面大小
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 缺省缓存多少个页面
	 */
	public static final int DEFAULT_PAGE_NUM = 1;

	// 页面大小 － 每个页面显示的记录数
	private int pageSize;

	// 缓存的页面个数
	private int pageNum;

	// 实际的数据列表
	private List source;

	// 总记录数（不是本对象持有的总记录数，而是所能得到的全部记录数）
	// -1表示还未查询过数据库
	private int totalNum = -1;

	// 本对象所持有记录的的起始号，应该是从1开始。
	private int start = 1;

	// 当前页面序号
	private int page = 1;

	// 对应的查询参数对象
	private Object param;

	// 给查询起的名称。因为该类会存在session里，为了将各个查询区别开，需要起个名字
	private String queryName;

	/**
	 * 空构造方法
	 */
	public CachePagedList() {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUM, "");
	}

	/**
	 * 构造方法
	 * 
	 * @param queryName 查询名称
	 */
	public CachePagedList(String queryName) {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUM, queryName);
	}

	/**
	 * 构造方法
	 * 
	 * @param pageSize 页面大小
	 * @param pageNum 缓存页面个数
	 */
	public CachePagedList(int pageSize, int pageNum) {
		this(pageSize, pageNum, "");
	}

	/**
	 * 构造方法
	 * 
	 * @param pageSize 页面大小
	 * @param pageNum 缓存页面个数
	 * @param queryName 查询名称
	 */
	public CachePagedList(int pageSize, int pageNum, String queryName) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.queryName = queryName;
		this.source = Collections.EMPTY_LIST;
	}

	// 获得总页数
	public int getTotalPage() {
		if (this.getTotalNum() <= 0)
			return 0;

		float nrOfPages = (float) this.getTotalNum() / this.pageSize;
		return (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
	}

	// 因为在检查记录是否存在于该对象前，
	// 对象已经得到page的信息，这时再传这个参数，
	// 有可能传的为超出范围的页数，造成不必要的错误
	// @auther 周必奎2004-11-16修改
	// 请求的页记录是否存在于该对象内
	public boolean exists() {
		return exists(getPage());
	}

	private boolean exists(int page) {
		// 如果totalNum为-1，则表示还没有查询过数据库
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

	// 是否为首页
	public boolean isFirstPage() {
		return this.page == 1;
	}

	// 是否为最末页
	public boolean isLastPage() {
		return this.page == this.getTotalPage();
	}

	// 返回当前页面的数据记录，不指定页码
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

	// 按照区间段来取记录，比如这次请求的是第33页，但还是会从第31页的记录开始取
	// Ace8 changed by 2004.9.3

	// 向数据库做查询请求时，起始的记录号。包含该条记录
	public int getQueryStartNum() {
		int remain = (this.getPage() - 1) % this.getPageNum();
		return (this.getPage() - remain - 1) * this.getPageSize() + 1;
	}

	// 向数据库做查询请求时，截至的记录号。包含该条记录
	public int getQueryEndNum() {
		return this.getQueryStartNum() + this.getPageSize() * this.getPageNum() - 1;
	}

	/** ========== 简单存取方法 =========== */

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

	// 限制设置不存在的页数
	// Ace8 changed by 2004.9.3
	public void setPage(int page) {
		if (page == 1) {// 第一页比较特殊，即使没有记录数，也允许设置页码为1
			this.page = page;
		} else if (page > 0 && page <= this.getTotalPage()) { // 其他情况下，不允许页码越界
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
