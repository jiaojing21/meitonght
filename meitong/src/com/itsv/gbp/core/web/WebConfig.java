package com.itsv.gbp.core.web;

import com.itsv.gbp.core.security.util.SecureTool;

/**
 * 说明：前端配置类。集中设置常用变量名。
 * 
 * @author admin 2005-1-12
 */
public class WebConfig {

	/**
	 * WEB层中，传递给前端界面的显示数据在request对象中的名称
	 */
	public static final String DATA_NAME = "data";

	/**
	 * WEB层中，传递给前端界面的元数据在request对象中的名称
	 */
	public static final String META_NAME = "meta";

	/**
	 * WEB层中，传递给前端界面的显示消息在request对象中的名称
	 */
	public static final String MESSAGE_NAME = "message";

	/**
	 * 具体的错误消息相信信息名
	 */
	public static final String MESSAGE_TRACE_NAME = "message_trace";

	/**
	 * session中存放的用户变量名称
	 * @deprecated 改用安全模块提供的用户信息
	 * @see SecureTool#getCurrentUser()
	 */
	public static final String USER_NAME = "session_user";

	/**
	 * 客户端请求传来的令牌参数名
	 */
	public static final String TOKEN_KEY = "token";

	/**
	 * session中存放的token变量的名称
	 */
	public static final String TOKEN_IN_SESSION = "TOKEN_IN_SESSION";
}