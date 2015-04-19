package com.example.javanio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	实现单向管道传送的通道对。
 * 	
 * 	【管道由一对通道组成】：一个可写入的 sink通道和一个可读取的source通道。一旦将某些字节写入接收器通道，就可以按照与写入时完全相同的顺序从源通道中读取这些字节。
 * 
 * 	在另一个线程从管道中读取这些字节或先前已写入的字节之前，是否阻塞将该字节写入管道的线程是与系统相关的，因此是未指定的。
 * 	很多管道实现都对接收器和源通道之间一定数量的字节进行缓冲，但是不应假定会进行这种缓冲。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class PipeTest {
	public static void main(String[] args) {
		try {
			Pipe pipe = Pipe.open();
			Pipe.SinkChannel psink = pipe.sink();
			Pipe.SourceChannel psource = pipe.source();
			CountDownLatch cdl = new CountDownLatch(2);
			new Thread(new ReadTask(psource, cdl)).start();
			new Thread(new WriteTask(psink, cdl)).start();
			cdl.await();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static class ReadTask implements Runnable {
		private Pipe.SourceChannel psource;
		private CountDownLatch cdl;

		public ReadTask(Pipe.SourceChannel psource, CountDownLatch cdl) {
			this.psource = psource;
			this.cdl = cdl;
		}
		
		@Override
		public void run() {
			try {
				ByteBuffer bb = ByteBuffer.allocate(1024);
				psource.read(bb); // 会阻塞
				bb.flip();
				byte[] bytes = new byte[bb.remaining()];
				bb.get(bytes);
				System.out.println("我是SourceChannel,我支持的操作集是：[" + (psource.validOps()) + "]SelectionKey.OP_READ");
				System.out.println(new String(bytes));
				cdl.countDown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class WriteTask implements Runnable {
		private Pipe.SinkChannel psink;
		private CountDownLatch cdl;

		public WriteTask(Pipe.SinkChannel psink, CountDownLatch cdl) {
			this.psink = psink;
			this.cdl = cdl;
		}

		@Override
		public void run() {
			try {
				byte[] bytes = "你好，我是八两俊".getBytes();
				ByteBuffer bb = ByteBuffer.allocate(bytes.length);
				bb.put(bytes);
				bb.flip();
				System.out.println("我是SinkChannel,我支持的操作集是：[" + (psink.validOps()) + "]SelectionKey.OP_WRITE");
				while (bb.hasRemaining()) {
					psink.write(bb);
				}
				cdl.countDown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
