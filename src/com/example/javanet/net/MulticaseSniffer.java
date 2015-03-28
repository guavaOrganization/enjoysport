package com.example.javanet.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	组播窃听器
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MulticaseSniffer {
	public static void main(String[] args) {
		InetAddress group = null;
		MulticastSocket ms = null;
		
		try {
			String host = "239.255.255.250";
			group = InetAddress.getByName(host);
			ms = new MulticastSocket(1900);
			ms.joinGroup(group);
			
			byte[] buffer = new byte[8192];
			while (true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				System.out.println(new String(dp.getData(), "UTF-8"));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ms != null) {
				try {
					ms.leaveGroup(group);
					ms.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
