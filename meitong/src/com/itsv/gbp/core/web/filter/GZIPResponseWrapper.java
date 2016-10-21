package com.itsv.gbp.core.web.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * ���������๦���Ƕ�GZIPѹ��Response���з�װ
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-15
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {

	//����δѹ������Ӧhttpservletresponse�Ķ���
	protected HttpServletResponse origResponse = null;

	//����ѹ������servlet������Ķ���
	protected ServletOutputStream stream = null;

	//����ͻ���PrintWriter�Ķ���
	protected PrintWriter writer = null;

	/**
	 * ���췽��
	 */
	public GZIPResponseWrapper(HttpServletResponse response) {
		super(response);
		//ʵ����origResponse
		this.origResponse = response;
	}

	/**
	 * ����:����ѹ������� throws IOException ����ֵ��ServletOutputStream ����:2004-11-16
	 */
	public ServletOutputStream createOutputStream() throws IOException {
		//��װδѹ����Ӧ������ѹ����Ӧ��
		return (new GZIPResponseStream(origResponse));
	}

	/**
	 * ����:�����Ӧ�ͷ���Դ,��ִ��writer.close();����ǵ���GZIPResponseStream��write ���� <p/>
	 * ���ŵ���GZIPResponseStream��close�����ڸ÷����н���ѹ�����ݲ��Ұ�ѹ�������д��servlet�����������Ӧ��� ����ֵ��void
	 * ����:2004-11-16
	 */
	public void finishResponse() {
		try {//���ͷ�writer����Դ
			if (writer != null) {
				writer.flush();
				writer.close();

			} else {//�ͷ�writer����Դ֮���ͷ�stream����Դ
				if (stream != null) {
					stream.close();
				}
			}
		} catch (IOException e) {
		}
	}

	//ˢ�»���
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

	//��ÿͻ��˵�Writer��html������
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
		//�����˰�װѹ������Ŀͻ����ı������

		// writer=new PrintWriter(new OutputStreamWriter(stream,"ISO-8859-1"));
		return (writer);
	}

	public void setContentLength(int length) {
	}
}