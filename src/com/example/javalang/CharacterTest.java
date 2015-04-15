package com.example.javalang;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class CharacterTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(System.getProperty("file.encoding")); // 文件编码类型
		System.out.println("陈".getBytes().length);// UTF-8 --> 3; getBytes使用了系统属性"file.encoding"指定的编码
		System.out.println("陈".getBytes("Unicode").length);// Unicode --> 4
		// Java 编程语言的本机字符编码方案是 UTF-16。因此 Java 平台的 charset 定义了 16 位 UTF-16 代码单元序列和字节序列之间的映射关系。
		// 编码方案可以从系统属性查看
		System.out.println(System.getProperty("sun.io.unicode.encoding")); //UnicodeLittle
		
		// 不难猜到，UTF-16是完全对应于UCS-2的，即把UCS-2规定的代码点通过Big Endian或Little Endian方式直接保存下来。UTF-16包括三种：UTF-16，UTF-16BE（Big Endian），UTF-16LE（Little Endian）。
		// UTF-16BE和UTF-16LE不难理解，而UTF-16就需要通过在文件开头以名为BOM（Byte Order Mark）的字符来表明文件是Big Endian还是Little Endian。BOM为U+FEFF这个字符。
		// 其实BOM是个小聪明的想法。由于UCS-2没有定义U+FFFE，因此只要出现 FF FE 或者 FE FF 这样的字节序列，就可以认为它是U+FEFF，并且可以判断出是Big Endian还是Little Endian。
		char hanzi = '陈';//
		System.out.println("================================");
		
		// Properties properties = System.getProperties();
		// Set<Object> set = properties.keySet();
		// Iterator<Object> it = set.iterator();
		// while (it.hasNext()) {
		// Object key = it.next();
		// Object value = properties.get(key);
		// System.out.println(key + " : " + value);
		// }
	}
}
