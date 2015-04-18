package com.example.javaio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class SerializableTest {
	@Test
	public void testSerialSimpleBean() {
		try {
			SerialSimpleBean simpleBean = new SerialSimpleBean(1, "序列化学习");
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("D:\\test\\SerialSimpleBean.out"));
			oos.writeObject(simpleBean);
			oos.flush();
			oos.close();

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\test\\SerialSimpleBean.out"));
			SerialSimpleBean reSerialSimpleBean = (SerialSimpleBean) ois.readObject();
			ois.close();
			// 可以从输出看出，反序列化并没有调用构造器来实例化SerialSimpleBean对象
			System.out.println(reSerialSimpleBean);
			System.out.println(simpleBean == reSerialSimpleBean);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
