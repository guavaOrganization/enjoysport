package com.example.javanio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel channel;

	public ReadCompletionHandler(AsynchronousSocketChannel channel) {
		if (this.channel == null)
			this.channel = channel;
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String req = new String(body, "UTF-8");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH小时mm分ss秒");
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? sdf.format(new Date(System.currentTimeMillis())) : "BAD ORDER";
			doWrite(currentTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void doWrite(String currentTime) {
		if (currentTime != null && currentTime.trim().length() > 0) {
			byte[] bytes;
			try {
				bytes = currentTime.getBytes("UTF-8");
				ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
				writeBuffer.put(bytes);
				writeBuffer.flip();
				
				channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer buffer) {
						if(buffer.hasRemaining()){
							channel.write(buffer, buffer, this);
						}
					}
					
					@Override
					public void failed(Throwable paramThrowable, ByteBuffer paramA) {
						try {
							channel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
	}

		
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			this.channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
