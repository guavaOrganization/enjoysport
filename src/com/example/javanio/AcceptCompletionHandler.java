package com.example.javanio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>{

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attchment) {
		// 当我们调用AsynchronousServerSocketChannel的accept方法后，如果有新的客户端连接接入，系统将回调我们传入的CompletionHandler
		// 示例的completed方法，表示新的客户端已经接入成功，因为一个AsynchronousServerSocketChannel可以接收成千上万个客户端。
		// 所以我们需要继续调用它的accept方法，接收其他的客户端连接，最终行程一个循环。
		attchment.asynchronousServerSocketChannel.accept(attchment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		// 从通道中读取字节序列到指定的buffer
		// 此方法开启一个异步读取操作并从这个通道异步读取字节序列到指定的buffer。
		// handler参数是一个完成处理器，它在读取操作完成(或失败)时被调用.传递到完成处理器的结果是读取到的字节数或当读取到通道的结束流时的字节数为-1。
		// read参数说明：
		// ByteBuffer : 接受缓冲区，用于从异步Channel中读取数据包
		// A attachment : 异步Channel携带的附件，通过回调的时候作为入参使用
		// CompletionHandler<Integer,? supper A> : 接收通知回调的业务handler
		result.read(buffer, buffer, new ReadCompletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attchment) {
		exc.printStackTrace();
		attchment.latch.countDown();
	}
}
