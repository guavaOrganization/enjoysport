package com.example.javaio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SimpleSerial {
	public static void main(String[] args) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\test\\person.out")));
			Person person = Person.getInstance();
			// writeObject 方法负责写入特定类的对象状态，以便相应的 readObject方法可以恢复它。
			// 该方法本身不必与属于对象的超类或子类的状态有关。状态是通过使用 writeObject方法或使用
			// DataOutput支持的用于基本数据类型的方法将各个字段写入ObjectOutputStream来保存的。
			oos.writeObject(person);
			oos.flush();
			oos.close();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\test\\person.out")));
			Person newPerson = (Person)ois.readObject();
			ois.close();
			System.out.println(newPerson);
			System.out.println(newPerson == person);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
