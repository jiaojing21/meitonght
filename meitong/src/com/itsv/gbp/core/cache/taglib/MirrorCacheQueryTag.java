package com.itsv.gbp.core.cache.taglib;

import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;

/**
 * 根据标签的属性设置，查询缓存数据，并将结果放入指定变量。<br>
 * 标签示例如下：
 * <pre>
 * 	&lt; cache:query var="userList" region="user" where="status=1" orderby="userid asc" /&gt;
 * </pre>
 * 参数说明如下：<br>
 * <ul>
 * 	<li>var	- 结果会设置在该属性指定的变量名称中。必需项</li>
 *  <li>region	- 要检索的缓存区域名称。必需项。</li>
 *  <li>where	- 检索条件。可选项，若为空表示检索所有缓存对象</li>
 *  <li>orderby	- 排序条件。可选项。</li>
 * </ul>
 * 
 * 得到结果列表后，可使用<c:forEach>进行输出。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-15 下午05:35:48
 * @version 1.0
 */
public class MirrorCacheQueryTag extends TagSupport {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MirrorCacheQueryTag.class);

	private static final long serialVersionUID = 1824606600648497170L;

	private String region; //缓存区域名

	private String var; //结果存放的变量名

	private String where; // 过滤条件

	private String orderby;// 排序方式

	public int doStartTag() throws JspException {
		List result = null;
		try {
			result = MirrorCacheTool.simplyQuery(getRegion(), this.where, this.orderby);
		} catch (Exception e) {
			//标签出错时，不打断执行，仅输出错误消息
			logger.error("执行缓存查询标签时出错.region=" + getRegion() + ",where=" + this.where + ",orderby="
					+ this.orderby, e);
		}
		if (result == null) {
			result = Collections.EMPTY_LIST;
		}
		this.pageContext.setAttribute(getVar(), result);

		return EVAL_PAGE;
	}

	/** 以下为存取方法 */

	public Object getOrderby() {
		return orderby;
	}

	//	orderby条件支持表达式
	public void setOrderby(Object orderby) throws JspException {
		this.orderby = (String) ExpressionEvaluatorManager.evaluate("orderby", orderby.toString(),
				Object.class, this, pageContext);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Object getWhere() {
		return where;
	}

	//where条件支持表达式
	public void setWhere(Object where) throws JspException {
		this.where = (String) ExpressionEvaluatorManager.evaluate("where", where.toString(), Object.class,
				this, pageContext);
	}
}
