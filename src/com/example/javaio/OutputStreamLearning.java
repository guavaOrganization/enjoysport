package com.example.javaio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class OutputStreamLearning {
	@Test
	public void test_byteArrayOutputStream() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(45);
		baos.write(46);
		baos.close(); // 即使关闭了输出流也可以操作
		baos.write(57);
		baos.write("陈俊".getBytes());// UTF-8下是3个字节存储一个汉字
		byte[] array = baos.toByteArray();
		for (byte b : array) {
			System.out.println(b);
		}
		System.out.println(baos.toString());
	}

//	@Test
	public void test_PipedStream() {
		try {
			PipedOutputStream pos = new PipedOutputStream();
			PipedInputStream pis = new PipedInputStream();
			pos.connect(pis);
			new Thread(new PipedOutputStreamRunnable(pos)).start();
			new Thread(new PipedInputStreamRunnable(pis)).start();
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class PipedInputStreamRunnable implements Runnable {
		private PipedInputStream pis;

		public PipedInputStreamRunnable(PipedInputStream pis) {
			this.pis = pis;
		}

		@Override
		public void run() {
			byte[] buf = new byte[1024];
			int len;
			try {
				System.out.println("我开始从管道中读取数据....");
				long currentTime = System.currentTimeMillis();
				len = pis.read(buf);
				System.out.println("我从管道中读取数据完毕,耗时：" + (System.currentTimeMillis() - currentTime) + "ms");
				String s = new String(buf, 0, len);
				System.out.println("我从管道中获得数据是：" + s);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (pis != null) {
					try {
						pis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	class PipedOutputStreamRunnable implements Runnable {
		private PipedOutputStream pos;

		public PipedOutputStreamRunnable(PipedOutputStream pos) {
			this.pos = pos;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(4);
				pos.write("我是管道输出流".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (null != pos) {
					try {
						pos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
