package com.example.javanet.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSender {
	public static void main(String[] args) {
		InetAddress ia = null;
		try {
			ia = InetAddress.getByName("239.255.255.250");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		byte[] data = "Here's some multicast data\r\n".getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ia, 1224);
		try (MulticastSocket ms = new MulticastSocket()) {
			ms.setTimeToLive(10);
			ms.joinGroup(ia);
			for (int i = 1; i < 10; i++) {
				ms.send(dp);
			}
			ms.leaveGroup(ia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
