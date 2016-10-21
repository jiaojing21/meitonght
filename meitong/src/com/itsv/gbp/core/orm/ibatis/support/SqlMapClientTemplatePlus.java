package com.itsv.gbp.core.orm.ibatis.support;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * 说明：spring提供的SqlMapClientTemplate的增强类<br>
 * 增加了一个query方法，返回经过分页的记录集 <br>
 * 
 * 
 * 为了有效率地分页，查询前首先要用select count(1) from ... 语句计算该查询能返回的记录数 
 * 那么，就需要有两条语句：
 * 一条是真正的查询语句，如: select * from users 
 * 另一条是配套的计算记录数的语句，如: select count(1) from users 
 * 我们在进行分页查询时，需要同时指定这两个查询在sqlmap文件里的名称。
 * 
 * @author admin 2004-9-24
 */
public class SqlMapClientTemplatePlus extends SqlMapClientTemplate {

	// log对象
	private static Log log = LogFactory.getLog(SqlMapClientTemplatePlus.class);

	/**
	 * 查询数据库，返回分页，指定页码的记录集
	 * 
	 * @param queryStatementName 查询statement语句的id
	 * @param countStatementName 与该查询配套的计算总记录数的select语句
	 * @param limitedList LimitedList接口对象
	 */
	public IPagedList query(final String queryStatementName, final String countStatementName,
			final IPagedList limitedList) throws DataAccessException {
		// 如果第一次是查询，先通过countStatementName计算数据集的大小
		if (limitedList.getTotalNum() == -1) {
			if (log.isDebugEnabled()) {
				log.debug("查询记录总数,开始.");
			}

			Integer total = (Integer) queryForObject(countStatementName, limitedList.getParam());

			if (log.isDebugEnabled()) {
				log.debug("查询记录总数,结束.满足查询条件的记录总数为:" + total);
			}

			limitedList.setTotalNum(total.intValue());
		}

		// 查询指定范围的记录
		if (log.isDebugEnabled()) {
			log.debug("分页查询,开始.查询起始记录号:" + limitedList.getQueryStartNum() + ",拟查询至:"
					+ limitedList.getQueryEndNum());
		}

		List records = queryForList(queryStatementName, limitedList.getParam(), limitedList
				.getQueryStartNum() - 1, limitedList.getQueryEndNum());

		if (log.isDebugEnabled()) {
			log.debug("分页查询,结束.此次查询实际得到记录个数为:" + records.size());
		}

		limitedList.setSource(records);
		limitedList.setStart(limitedList.getQueryStartNum());

		return limitedList;
	}

}