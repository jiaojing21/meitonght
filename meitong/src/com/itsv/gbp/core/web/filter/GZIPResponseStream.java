package com.itsv.gbp.core.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：该类进行压缩response输出流
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-15
 */
public class GZIPResponseStream extends ServletOutputStream {

	//为压缩的输出流缓存
	protected OutputStream bufferedOutput = null;

	//标示close（）是否调用
	protected boolean closed = false;

	// 没有压缩的response
	protected HttpServletResponse response = null;

	//servlet 的输出流
	protected ServletOutputStream output = null;

	// 缺省的内存缓存大小
	private int bufferSize = 50000;

	public GZIPResponseStream(HttpServletResponse response) throws IOException {
		super();

		this.closed = false;
		this.response = response;
		//实例化servlet的输出流对象
		this.output = response.getOutputStream();
		//实例化缓存对象
		bufferedOutput = new ByteArrayOutputStream();
	}

	/*
	 * 在该方法中实现输出流的压缩。分为两种情况：第一、如果在内存中缓存的欲压缩内容，就压缩；
	 * 第二、若在内存缓存中servlet压缩输出流，进行压缩数据写到servlet输出流 覆盖了java.io.OutputStream#close()方法
	 * 
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException {

		// 确定the output stream是否关闭
		if (closed) {
			throw new IOException("This output stream has already been closed");
		}

		// 如果在内容中缓存的欲压缩内容，就压缩
		if (bufferedOutput instanceof ByteArrayOutputStream) {
			// 获得保存的内容
			ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;
			// 准备一个压缩流
			ByteArrayOutputStream compressedContent = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(compressedContent);
			byte[] bytes = baos.toByteArray();
			//写数据到压缩输出流，进行压缩
			gzipstream.write(bytes);
			gzipstream.finish();
			// 获得压缩的内容
			byte[] compressedBytes = compressedContent.toByteArray();
			// 设置对应 HTTP headers
			response.setContentLength(compressedBytes.length);
			response.addHeader("Content-Encoding", "gzip");
			// 把压缩流写到servlet的输出流
			output.write(compressedBytes);
			output.flush();
			output.close();
			closed = true;

		}
		// 若在内存缓存中没有欲压缩内容，完成写压缩数据到输出流和响应 response
		else if (bufferedOutput instanceof GZIPOutputStream) {

			GZIPOutputStream gzipstream = (GZIPOutputStream) bufferedOutput;
			//进行压缩并写到servlet输出流 ，因为checkBufferSize中创建压缩输出流是用servlet的输出流
			gzipstream.finish();
			// 完成响应response
			output.flush();
			output.close();
			closed = true;

		}
	}

	/*
	 * 覆盖了java.io.OutputStream#flush()的方法
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
	 * 该方法主要把欲压缩的整型数据写到缓存 覆盖了java.io.OutputStream#write(int)的方法
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		//确保不超过缓存的容量
		checkBufferSize(1);
		// 写字节到the temporary output
		bufferedOutput.write((byte) b);
	}

	/**
	 * 描述:检查文件是否超过缓存的容量 参数:
	 * 
	 * @param length 参数:
	 * @throws IOException 返回值：void 日期:2004-11-17
	 */
	public void checkBufferSize(int length) throws IOException {
		// 检查是否缓存太大的文件
		if (bufferedOutput instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;
			if (baos.size() + length > bufferSize) {
				// 太大无法缓存文件没有具体长度被送到客户端
				response.addHeader("Content-Encoding", "gzip");
				// 获得现存的字节
				byte[] bytes = baos.toByteArray();
				// 用response输出流 创建 new gzip stream
				GZIPOutputStream gzipstream = new GZIPOutputStream(output);
				//写到压缩输出流
				gzipstream.write(bytes);
				//保存压缩输出流到缓存输出流
				bufferedOutput = gzipstream;
			}
		}
	}

	/*
	 * 把具体的字节数组数据写到输出流 覆盖了java.io.OutputStream#write(byte[])的方法
	 * 
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	/*
	 * (non-Javadoc) 把指定长字节数组数据写到输出流 当GZIPResponseWrapper的finishResponse()方法中调用
	 * 覆盖了java.io.OutputStream#write(byte[], int, int)的方法
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}

		checkBufferSize(len);
		// 写指定长度的字节数组到缓存 buffer
		bufferedOutput.write(b, off, len);

	}

	public boolean closed() {
		return (this.closed);
	}

	public void reset() {
		//noop
	}
}