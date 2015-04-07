package com.example.javanio;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	在新的I/O模型中，不再向输出流写入数据和从输入流读取数据，而是要从缓冲区中读写数据。像在缓冲流中一样，缓冲区可能就是字节数组。不过，原是实现可以将缓冲区直接与硬件或内存连接，
 *  或者使用其他非常高效的实现。
 *  
 *  从编程角度看，流和通道之间的关键区别在于流是基于字节的，而通道是基于块的。流设计为按顺序一个字节接一个字节地传送数据。
 *  处于性能考虑，也可以传送字节数组。不过，基本的概念都是一次传送一个字节的数据。与之不同，通道会传送缓冲区中的数据块。可以读写通道的字节之前，这些字节必须已经存储在缓冲区中，
 *  而且一次会读/写一个缓冲区的数据。
 *  
 *  流和通道/缓冲区之间的第二个关键区别是，通道和缓冲区支持同一对象的读/写。
 *  
 *  【直接与非直接缓冲区】
 *  字节缓冲区要么是直接的，要么是非直接的。
 *  如果为直接字节缓冲区，则Java虚拟机会尽最大努力直接在此缓冲区上执行本机I/O操作。也就是说，在每次调用基础操作系统的一个本机I/O操作之前（或之后），
 *  虚拟机都会尽量避免将缓冲区的内容复制到中间缓冲区中（或从中间缓冲区中复制内容）。
 *  
 *  直接字节缓冲区可以通过调用此类的 allocateDirect工厂方法来创建。此方法返回的缓冲区进行分配和取消分配所需成本通常高于非直接缓冲区。
 *  【直接缓冲区的内容可以驻留在常规的垃圾回收堆之外】，因此，它们对应用程序的内存需求量造成的影响可能并不明显。所以，建议将直接缓冲区主要分配给那些易受基础系统的本机I/O操作影响的大型、持久的缓冲区。
 *  一般情况下，最好仅在直接缓冲区能在程序性能方面带来明显好处时分配它们。
 *  
 *  直接字节缓冲区还可以通过 mapping将文件区域直接映射到内存中来创建。Java平台的实现有助于通过JNI从本机代码创建直接字节缓冲区。如果以上这些缓冲区中的某个缓冲区实例指的是不可访问的内存区域，
 *  则试图访问该区域不会更改该缓冲区的内容，并且将会在访问期间或稍后的某个时间导致抛出不确定的异常。
 *  
 *  字节缓冲区是直接缓冲区还是非直接缓冲区可通过调用其isDirect方法来确定。提供此方法是为了能够在性能关键型代码中执行显式缓冲区管理。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ByteBufferTest {
	public static void main(String[] args) {
		// 新缓冲区的位置将为零，其界限将为其容量，其标记是不确定的。它将具有一个底层实现数组，且其 数组偏移量将为零。
		// allocate()创建的缓冲区是基于Java数组实现，可以通过array()和arrayOffset()方法来访问
		ByteBuffer bb = ByteBuffer.allocate(100);// 创建一个容量为100的非直接缓冲区【空缓冲区】
		
		// 判断是否可通过一个可访问的byte数组实现此缓冲区。
		// 如果此方法返回true，则可以安全地调用array和arrayOffset方法。
		if (bb.hasArray()) {
			// 此缓冲区的内容修改将导致返回的数组内容修改，反之亦然。
			byte[] byteArr = bb.array(); // 调用此方法之前要调用 hasArray 方法，以确保此缓冲区具有可访问的底层实现数组。
			byteArr[2] = 127;
			System.out.println(bb.get(2));// 127
			System.out.println(byteArr.length);// 100
			// for (byte b : byteArr) { // 空缓冲区中的byte值为0
			// System.out.println(b); // 0
			// }
		}
		// 相对 get 方法。读取此缓冲区当前位置的字节，然后该位置递增。
		// 返回：缓冲区当前位置的字节
		System.out.println("bb.get() : " + bb.get());
		System.out.println(bb.position());// 1
		bb.put((byte) 1);
		System.out.println(bb.position());// 2
		// 绝对 get 方法。读取指定索引处的字节。返回：给定索引处的字节
		System.out.println("bb.get(0):" + bb.get(1));

		System.out.println("++++++++++++++++++++++++++++");
		String result = "";
		while (bb.hasRemaining()) {// 告知在当前位置和限制之间是否有元素
			result += bb.get();
		}
		System.out.println("++++++++++++++" + result + "++++++++++++++");
		
		bb.flip();
		result = "";
		while (bb.hasRemaining()) {// 告知在当前位置和限制之间是否有元素
			result += bb.get();
		}
		System.out.println("++++++++++++++" + result + "++++++++++++++");
		// 包装
		// 如果已经有了要输出的数据数组，一般要用缓冲区进行包装，而不是分配一个新缓冲区，然后一次一部分地复制到这个缓冲区。
		try {
			byte[] data = "Some data".getBytes("UTF-8");
			// 将byte数组包装到缓冲区中。
			// 新的缓冲区将由给定的byte数组支持；也就是说，缓冲区修改将导致数组修改，反之亦然。新缓冲区的容量和界限将为 array.length，其位置将为零，其标记是不确定的。
			// 其底层实现数组将为给定数组，并且其数组偏移量将为零。
			ByteBuffer newByteBuffer = ByteBuffer.wrap(data);
			byte[] byteArr = newByteBuffer.array();
			System.out.println(byteArr.length);// 9
			// byteArr[11] = 12; // 抛出java.lang.ArrayIndexOutOfBoundsException
			System.out.println("newByteBuffer.position() : " + newByteBuffer.position());
			System.out.println(newByteBuffer.get());
			System.out.println(newByteBuffer.get());
			System.out.println("newByteBuffer.position() : " + newByteBuffer.position());
			System.out.println("newByteBuffer.capacity() : " + newByteBuffer.capacity());
			
			System.out.println("newByteBuffer.limit() : " + newByteBuffer.limit());
			
			newByteBuffer.mark();
			newByteBuffer.reset();
			// clear()方法将位置设置为0，并将限度设置为容量，从而将缓冲区"清空"。这样一来，就可以完全重新填充缓冲区了。
			// 不过，clear()方法没有删除缓冲区中的老数据。这些数据仍然存在，还可以使用绝对get方法或者再改变限度和位置进行读取。
			newByteBuffer.clear();
			System.out.println(newByteBuffer.get());// 83

			// 将位置设置为0，但不改变限度
			newByteBuffer.rewind();
			System.out.println(newByteBuffer.get());// 83
			
			System.out.println(newByteBuffer.remaining());
			
			// flip()会将限度设置为当前位置，位置设置为0
			newByteBuffer.flip();
			System.out.println(newByteBuffer.get());// 83
			System.out.println(newByteBuffer.get());// Exception in thread "main" java.nio.BufferUnderflowException
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		// 直接分配
		// 不为缓冲区创建后备数组。VM会对以太网卡、核心内存或其他位置上的缓冲区使用直接内存访问，以此实现直接分配的ByteBuffer
		// 在直接缓冲区上调用array()和arrayOffset()或抛出一个UnsupportedOperationException异常。
		// 直接缓冲区在一些虚拟机上会更快，尤其是缓冲区很大时。不过，创建直接缓冲区比间接缓冲区代价更高，所以只能在缓冲区可能只持续较短时间时才分配这种直接缓冲区。
		// 其细节非常依赖于VM。与大多数性能建议一样，除非经过策略后发现性能确实是个问题，否则不应考虑使用直接缓冲区。
		ByteBuffer bufferDirect = ByteBuffer.allocateDirect(10);
	}
}
