package com.itsv.gbp.core.web.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 描述：该类功能是对GZIP压缩Response进行封装
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-15
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {

	//定义未压缩的响应httpservletresponse的对象
	protected HttpServletResponse origResponse = null;

	//定义压缩过的servlet输出流的对象
	protected ServletOutputStream stream = null;

	//定义客户端PrintWriter的对象
	protected PrintWriter writer = null;

	/**
	 * 构造方法
	 */
	public GZIPResponseWrapper(HttpServletResponse response) {
		super(response);
		//实例化origResponse
		this.origResponse = response;
	}

	/**
	 * 描述:创建压缩输出流 throws IOException 返回值：ServletOutputStream 日期:2004-11-16
	 */
	public ServletOutputStream createOutputStream() throws IOException {
		//包装未压缩响应而创建压缩响应流
		return (new GZIPResponseStream(origResponse));
	}

	/**
	 * 描述:完成响应释放资源,在执行writer.close();语句是调用GZIPResponseStream的write 方法 <p/>
	 * 接着调用GZIPResponseStream中close（）在该方法中进行压缩数据并且把压缩输出流写到servlet输出流进行响应完成 返回值：void
	 * 日期:2004-11-16
	 */
	public void finishResponse() {
		try {//先释放writer的资源
			if (writer != null) {
				writer.flush();
				writer.close();

			} else {//释放writer的资源之后释放stream的资源
				if (stream != null) {
					stream.close();
				}
			}
		} catch (IOException e) {
		}
	}

	//刷新缓存
	public void flushBuffer() throws IOException {
		stream.flush();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called!");
		}

		if (stream == null) {
			stream = createOutputStream();
		}

		return (stream);
	}

	//获得客户端的Writer的html生成器
	public PrintWriter getWriter() throws IOException {

		if (writer != null) {
			return (writer);
		}

		if (stream != null) {
			throw new IllegalStateException("getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		// BUG FIX 2003-12-01 Reuse content's encoding, don't assume UTF-8
		writer = new PrintWriter(new OutputStreamWriter(stream, origResponse.getCharacterEncoding()));
		//创建了包装压缩输出的客户端文本输出流

		// writer=new PrintWriter(new OutputStreamWriter(stream,"ISO-8859-1"));
		return (writer);
	}

	public void setContentLength(int length) {
	}
}