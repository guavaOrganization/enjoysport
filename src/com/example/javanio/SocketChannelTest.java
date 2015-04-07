package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class SocketChannelTest {
	public static void main(String[] args) {
		try {
			SocketAddress address = new InetSocketAddress("127.0.0.1", 1223);
			// 创建一个新的SocketChannel。并指示要连接的主机和端口
			SocketChannel client = SocketChannel.open(address);// 通道以阻塞模式打开
			// 在真正建立连接之前下面的代码是不会执行的
			client.configureBlocking(false);// 设置通道的模式为非阻塞模式。默认阻塞
			
			ByteBuffer buffer = ByteBuffer.allocate(74);
			// 通道会用从Socket读取的数据填充这个缓冲区。它返回成功读取并存储在缓冲区的字节数
			WritableByteChannel output = Channels.newChannel(System.out);
			
			// 阻塞模式：
			/**
			while(client.read(buffer) != -1){// 在非阻塞模式下，即使没有任何可用的数据，read()也会立即返回
				// int bytesRead = client.read(buffer);// 默认情况下，这回至少读取一个字节，或返回-1指示数据结束

				// 回绕(flip)缓冲区，使得输出通道会从锁读取数据的开头而不是末尾开始写入
				buffer.flip();
				// 你不必告诉输出通道要写入多少字节。缓冲区会记住其中包含多少字节。不过，一般情况下，输出通道不能保证会写入缓冲区中的所有字节。
				output.write(buffer);// 本示例是一个阻塞通道，要么写入全部字节，要么抛出IOException异常。
				// 不要每次读/写都创建一个新的缓冲区。那样做会降低性能。相反，要重用现有的缓冲区。
				// 在再次读取之前要清空缓冲区。
				buffer.clear();
			}
			*/
			// 非阻塞模式
			while (true) {
				int n = client.read(buffer);
				if (n > 0) {
					buffer.flip();
					output.write(buffer);
					buffer.clear();
				} else if (n == -1) {
					break;
				}
			}
			
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
