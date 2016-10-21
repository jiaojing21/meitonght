package com.itsv.gbp.core.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * �������������ѹ��response�����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-15
 */
public class GZIPResponseStream extends ServletOutputStream {

	//Ϊѹ�������������
	protected OutputStream bufferedOutput = null;

	//��ʾclose�����Ƿ����
	protected boolean closed = false;

	// û��ѹ����response
	protected HttpServletResponse response = null;

	//servlet �������
	protected ServletOutputStream output = null;

	// ȱʡ���ڴ滺���С
	private int bufferSize = 50000;

	public GZIPResponseStream(HttpServletResponse response) throws IOException {
		super();

		this.closed = false;
		this.response = response;
		//ʵ����servlet�����������
		this.output = response.getOutputStream();
		//ʵ�����������
		bufferedOutput = new ByteArrayOutputStream();
	}

	/*
	 * �ڸ÷�����ʵ���������ѹ������Ϊ�����������һ��������ڴ��л������ѹ�����ݣ���ѹ����
	 * �ڶ��������ڴ滺����servletѹ�������������ѹ������д��servlet����� ������java.io.OutputStream#close()����
	 * 
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException {

		// ȷ��the output stream�Ƿ�ر�
		if (closed) {
			throw new IOException("This output stream has already been closed");
		}

		// ����������л������ѹ�����ݣ���ѹ��
		if (bufferedOutput instanceof ByteArrayOutputStream) {
			// ��ñ��������
			ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;
			// ׼��һ��ѹ����
			ByteArrayOutputStream compressedContent = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(compressedContent);
			byte[] bytes = baos.toByteArray();
			//д���ݵ�ѹ�������������ѹ��
			gzipstream.write(bytes);
			gzipstream.finish();
			// ���ѹ��������
			byte[] compressedBytes = compressedContent.toByteArray();
			// ���ö�Ӧ HTTP headers
			response.setContentLength(compressedBytes.length);
			response.addHeader("Content-Encoding", "gzip");
			// ��ѹ����д��servlet�������
			output.write(compressedBytes);
			output.flush();
			output.close();
			closed = true;

		}
		// �����ڴ滺����û����ѹ�����ݣ����дѹ�����ݵ����������Ӧ response
		else if (bufferedOutput instanceof GZIPOutputStream) {

			GZIPOutputStream gzipstream = (GZIPOutputStream) bufferedOutput;
			//����ѹ����д��servlet����� ����ΪcheckBufferSize�д���ѹ�����������servlet�������
			gzipstream.finish();
			// �����Ӧresponse
			output.flush();
			output.close();
			closed = true;

		}
	}

	/*
	 * ������java.io.OutputStream#flush()�ķ���
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException {
		if (closed) {
			throw new IOException("Cannot flush a closed output stream");
		}
		bufferedOutput.flush();
	}

	/*
	 * 
	 * �÷�����Ҫ����ѹ������������д������ ������java.io.OutputStream#write(int)�ķ���
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		//ȷ�����������������
		checkBufferSize(1);
		// д�ֽڵ�the temporary output
		bufferedOutput.write((byte) b);
	}

	/**
	 * ����:����ļ��Ƿ񳬹���������� ����:
	 * 
	 * @param length ����:
	 * @throws IOException ����ֵ��void ����:2004-11-17
	 */
	public void checkBufferSize(int length) throws IOException {
		// ����Ƿ񻺴�̫����ļ�
		if (bufferedOutput instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;
			if (baos.size() + length > bufferSize) {
				// ̫���޷������ļ�û�о��峤�ȱ��͵��ͻ���
				response.addHeader("Content-Encoding", "gzip");
				// ����ִ���ֽ�
				byte[] bytes = baos.toByteArray();
				// ��response����� ���� new gzip stream
				GZIPOutputStream gzipstream = new GZIPOutputStream(output);
				//д��ѹ�������
				gzipstream.write(bytes);
				//����ѹ������������������
				bufferedOutput = gzipstream;
			}
		}
	}

	/*
	 * �Ѿ�����ֽ���������д������� ������java.io.OutputStream#write(byte[])�ķ���
	 * 
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	/*
	 * (non-Javadoc) ��ָ�����ֽ���������д������� ��GZIPResponseWrapper��finishResponse()�����е���
	 * ������java.io.OutputStream#write(byte[], int, int)�ķ���
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}

		checkBufferSize(len);
		// дָ�����ȵ��ֽ����鵽���� buffer
		bufferedOutput.write(b, off, len);

	}

	public boolean closed() {
		return (this.closed);
	}

	public void reset() {
		//noop
	}
}