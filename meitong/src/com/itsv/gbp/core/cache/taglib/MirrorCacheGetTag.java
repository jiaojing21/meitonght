package com.itsv.gbp.core.cache.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;

/**
 * 缓存读取标签，根据指定的值，查找对应的缓存对象，并将其输出。<br>
 * 标签示例如下：
 * <pre>
 * 	&lt; cache:get var="u" region="user" key="100" targetprop="userName" out="1" /&gt;
 * </pre>
 * 参数说明如下：<br>
 * <ul>
 * 	<li>var	- 	如果设置了值，结果会设置在该属性指定的变量名称中。可选项</li>
 *  <li>region	- 要检索的缓存区域名称。必需项。</li>
 *  <li>key	- 	缓存的key。必需项</li>
 *  <li>targetprop	- 具体指定要检索缓存的哪个属性。可选项，如果忽略表示检索整个对象而不是某个具体属性。</li>
 * 	<li>out		- 是否将获得的缓存内容输出到页面。可选项， <b>默认输出</b></li>
 * </ul>
 * 
 * 得到结果列表后，可使用<c:forEach>进行输出。

 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-15 下午06:04:46
 * @version 1.0
 */
public class MirrorCacheGetTag extends TagSupport {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MirrorCacheGetTag.class);

	private static final long serialVersionUID = -6674275988680577191L;

	private String region; //缓存区域名

	private String var; //结果存放的变量名

	private boolean out = true; //是否直接输出到页面

	private Object key; // 过滤条件

	private String targetprop;// 想要获得的目标属性

	public int doStartTag() throws JspException {
		Object value = null;
		try {
			//如果指定属性名，则取具体属性值，否则取回整个对象
			if (getTargetprop() != null) {
				value = MirrorCacheTool.getProp(getRegion(), getKey().toString(), getTargetprop());
			} else {
				value = MirrorCacheTool.get(getRegion(), getKey().toString());
			}

		} catch (Exception e) {
			//标签出错时，不打断执行，仅输出错误消息
			logger.error("执行缓存读取标签时出错.region=" + getRegion() + ",key=" + getKey() + ",targetProp="
					+ getTargetprop(), e);
		}

		//如果要求输出，则输出内容
		if (value != null && out) {
			try {
				pageContext.getOut().print(value);
			} catch (IOException e) {
				logger.error("输出缓存对象的属性值时出错", e);
			}

		}

		//如果指定了var值，则将结果存入该变量名
		if (value != null && getVar() != null) {
			this.pageContext.setAttribute(getVar(), value);
		}

		return EVAL_PAGE;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) throws JspException {
		this.key = ExpressionEvaluatorManager.evaluate("key", key.toString(), Object.class, this,
				pageContext);
	}

	public String getOut() {
		return String.valueOf(out);
	}

	public void setOut(String out) {
		if ("0".equalsIgnoreCase(out) || "f".equalsIgnoreCase(out) || "false".equalsIgnoreCase(out)) {
			this.out = false;
		}
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTargetprop() {
		return targetprop;
	}

	public void setTargetprop(String targetprop) {
		this.targetprop = targetprop;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	/** 以下为get,set方法	 */

}
