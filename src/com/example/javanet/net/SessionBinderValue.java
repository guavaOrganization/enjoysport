package com.example.javanet.net;

import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;

public class SessionBinderValue implements SSLSessionBindingListener {
	@Override
	public void valueBound(SSLSessionBindingEvent event) {
		System.out.println(event.getName());
	}

	@Override
	public void valueUnbound(SSLSessionBindingEvent event) {
		
	}
}
