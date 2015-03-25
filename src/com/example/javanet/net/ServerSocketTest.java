package com.example.javanet.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
	public static void main(String[] args) {
		int port = 1223;
		// 关闭ServerSocket会释放本地主机的一个端口，允许另一个服务器绑定到这个端口。它还会中断该ServerSocket已经接受的目前处于打开状态的所有Socket
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				System.out.println("我正在监听【" + port + "】端口");
				Socket socket = server.accept();// accept()调用会阻塞。
				try {
					System.out.println("接受到请求，开始处理请求...");// 已经建立了与客户端socket之间的通道
					new Thread(new SocketHandler(socket)).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
