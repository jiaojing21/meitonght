package com.itsv.gbp.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：压缩过滤器。对网页内容压缩，然后传输给客户端。可以节省网络传输的数据量，加快用户访问速度。 <br>
 * 详细设计思想是： <br>
 * 第一、在chain.doFilter(req, wrappedResponse)之前没有进行压缩； <br>
 * 第二、在chain.doFilter(req, wrappedResponse)之后才压缩数据，并释放资源
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-22
 */
public class GZIPFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		if (req instanceof HttpServletRequest) {
			//转化为http 的请求和响应
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("Accept-Encoding");
			if (ae != null && ae.toLowerCase().indexOf("gzip") != -1) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);

				chain.doFilter(req, wrappedResponse);

				//真正的压缩数据就在这里进行
				wrappedResponse.finishResponse();

				return;
			}
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}