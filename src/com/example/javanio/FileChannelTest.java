package com.example.javanio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	用于读取、写入、映射和操作文件的通道。
 * 	
 * 	文件通道在其文件中有一个当前 position，可对其进行查询和修改。【该文件本身包含一个可读写的长度可变的字节序列】，并且可以查询该文件的当前大小。
 * 	写入的字节超出文件的当前大小时，则增加文件的大小；截取该文件时，则减小文件的大小。文件可能还有某个相关联的元数据，如访问权限、内容类型和最后的修改时间；【此类未定义访问元数据的方法】。
 * 
 * 	除了字节通道中常见的读取、写入和关闭操作外，此类还定义了下列特定于文件的操作：
 * 	a、以不影响通道当前位置的方式，对文件中绝对位置的字节进行读取或写入。
 * 	b、【将文件中的某个区域直接映射到内存中】；对于较大的文件，这通常比调用普通的read或write方法更为高效。
 * 	c、强制对底层存储设备进行文件的更新，确保在系统崩溃时不丢失数据。
 * 	d、以一种可被很多操作系统优化为直接向文件系统缓存发送或从中读取的高速传输方法，将字节从文件传输到某个其他通道中，反之亦然。
 * 	e、可以锁定某个文件区域，以阻止其他程序对其进行访问。
 * 
 *	多个并发线程可安全地使用文件通道。可随时调用关闭方法，正如Channel接口中所指定的。【对于涉及通道位置或者可以更改其文件大小的操作，在任意给定时间只能进行一个这样的操作】；
 *	【如果尝试在第一个操作仍在进行时发起第二个操作，则会导致在第一个操作完成之前阻塞第二个操作】。可以并发处理其他操作，特别是那些采用显式位置的操作；但是否并发处理则取决于基础实现，因此是未指定的。
 *
 *	【确保此类的实例所提供的文件视图与同一程序中其他实例所提供的相同文件视图是一致的】。
 *	但是，【此类的实例所提供的视图不一定与其他并发运行的程序所看到的视图一致，这取决于底层操作系统所执行的缓冲策略和各种网络文件系统协议所引入的延迟】。
 *	不管其他程序是以何种语言编写的，而且也不管是运行在相同机器还是不同机器上都是如此。此种不一致的确切性质取决于系统，因此是未指定的。
 *
 *	此类没有定义打开现有文件或创建新文件的方法，以后的版本中可能添加这些方法。在此版本中，可从现有的 FileInputStream、FileOutputStream 或 RandomAccessFile对象获得文件通道，
 *	方法是调用该对象的 getChannel 方法，这会返回一个连接到相同底层文件的文件通道。
 *
 *	此类在各种情况下指定要求“允许读取操作”、“允许写入操作”或“允许读取和写入操作”的某个实例。
 *	通过 FileInputStream 实例的 getChannel方法所获得的通道将允许进行读取操作。
 *	通过 FileOutputStream 实例的 getChannel 方法所获得的通道将允许进行写入操作。
 *	最后，如果使用模式"r"创建 RandomAccessFile实例，则通过该实例的 getChannel方法所获得的通道将允许进行读取操作，如果使用模式"rw"创建实例，则获得的通道将允许进行读取和写入操作。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class FileChannelTest {
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(new File("E:\\计划.txt"));
			// 可以通过FileInputStream、FileOutputStream、RandomAccessFile获取FileChannel
			FileChannel fc = fis.getChannel();
			System.out.println(fc.size());
			ByteBuffer bb = ByteBuffer.allocate((int) fc.size());
			fc.read(bb);
			System.out.println(new String(bb.array(), "GBK"));
			fis.close();
			
			RandomAccessFile raf = new RandomAccessFile(new File("E:\\计划.txt"), "rw");
			fc = raf.getChannel();
			fc.position(fc.size());
			fc.write(ByteBuffer.wrap("\r\n你好,java nio".getBytes("GBK")));
			raf.close();

			fc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
