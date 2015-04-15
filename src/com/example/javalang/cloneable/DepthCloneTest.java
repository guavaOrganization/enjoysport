package com.example.javalang.cloneable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	深拷贝：
 * 	通过Java序列化机制实现深拷贝
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class DepthCloneTest {
	public static void main(String[] args) {
		DepthCloneInfo cloneInfo = new DepthCloneInfo("深拷贝", new DepthCloneInfoAttr("不想上班"));
		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream objOut = null;
		
		ByteArrayInputStream byteIn = null;
		ObjectInputStream objIn = null;
		
		try {
			byteOut  = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(cloneInfo);
			
			byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			objIn = new ObjectInputStream(byteIn);
			
			DepthCloneInfo depthCloneInfo = (DepthCloneInfo) objIn.readObject();
			System.out.println(cloneInfo);
			System.out.println(depthCloneInfo);
			System.out.println(depthCloneInfo.getAttr() == cloneInfo.getAttr());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
