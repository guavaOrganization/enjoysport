package com.example.javanet.netty;

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.mysql.jdbc.Buffer;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private int userID;

	public UserInfo buildUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public UserInfo buildUserID(int userID) {
		this.userID = userID;
		return this;
	}

	public final String getUserName() {
		return userName;
	}

	public final void setUserName(String userName) {
		this.userName = userName;
	}

	public final int getUserID() {
		return userID;
	}

	public final void setUserID(int userID) {
		this.userID = userID;
	}

	public byte[] codeC() {
		ByteBuffer bb = ByteBuffer.allocate(1024);
		byte[] value = this.userName.getBytes();
		bb.putInt(value.length);
		bb.put(value);
		bb.putInt(this.userID);
		bb.flip();
		value = null;
		byte[] result = new byte[bb.remaining()];
		bb.get(result);
		return result;
	}
}
