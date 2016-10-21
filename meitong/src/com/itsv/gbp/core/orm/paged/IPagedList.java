package com.itsv.gbp.core.orm.paged;

import java.util.List;

/**
 * 说明：配合OR mapper对象的数据分页辅助接口。<br>
 * 
 * 执行时，先给该接口实现类设定条件值，要获得的起始和截至记录号，然后将该对象传入持久化对象<br>
 * 执行结束后，通过getSource()获得查询结果列表，通过getTotalNum()获得查询到的记录数<br>
 * 
 * @author admin 2004-9-24
 */
public interface IPagedList {

	// 向数据库做查询时的查询条件
	public abstract void setParam(Object param);

	public abstract Object getParam();

	// 向数据库做查询请求时，起始的记录号。包含该条记录
	public abstract int getQueryStartNum();

	// 向数据库做查询请求时，截至的记录号。包含该条记录
	public abstract int getQueryEndNum();

	// 设置查询到的记录
	public abstract void setSource(List source);

	// 获得查询到的记录
	public abstract List getSource();

	// 操作总的记录数
	public abstract void setTotalNum(int total);

	public abstract int getTotalNum();

	// 本次查询的起始记录号
	public void setStart(int start);

	public int getStart();
}