package com.example.jvm;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	新生代GC(MinorGC)
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MinorGC {
	private static final int _1MB = 1024 * 1024;

	/**
	 * JVM参数：-verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
	 * 一次垃圾收集过程中堆上新生代和老年代的内存变化和使用情况，以及永久代内存使用情况
	[GC[DefNew: 6856K->420K(9216K), 0.0043386 secs] 6856K->6564K(19456K), 0.0043938 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
			Heap
			 def new generation   total 9216K, used 4844K [0x32680000, 0x33080000, 0x33080000)
			  eden space 8192K,  54% used [0x32680000, 0x32ad1fa0, 0x32e80000)
			  from space 1024K,  41% used [0x32f80000, 0x32fe9200, 0x33080000)
			  to   space 1024K,   0% used [0x32e80000, 0x32e80000, 0x32f80000)
			 tenured generation   total 10240K, used 6144K [0x33080000, 0x33a80000, 0x33a80000)
			   the space 10240K,  60% used [0x33080000, 0x33680030, 0x33680200, 0x33a80000)
			 compacting perm gen  total 12288K, used 149K [0x33a80000, 0x34680000, 0x37a80000)
			   the space 12288K,   1% used [0x33a80000, 0x33aa5758, 0x33aa5800, 0x34680000)
			    ro space 10240K,  41% used [0x37a80000, 0x37eb2050, 0x37eb2200, 0x38480000)
			    rw space 12288K,  52% used [0x38480000, 0x38ac6800, 0x38ac6800, 0x39080000)
	 */
	public static void testAllocation() { // 对象优先在Eden分配
		// eden space 为 Eden区域
		// from space 为Survivor区域
		// to   space 为另一个Survivor区域
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		// 出现一次Minor GC。Eden区域已经占用了6MB的空间，此时allocation1、allocation2、allocation3对应的对象无法一致到Survivor区域，只能通过担保机制转移到老年代
		allocation1 = new byte[4 * _1MB];
	}
	
	/**
	 * JVM参数：-verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
	 * JVM参数：-verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
	 */
	public static void testTenuringThreshold() {// 长期存活的对象将进入老年代
		byte[] allocation1, allocation2, allocation3;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB * 4];
		allocation3 = new byte[_1MB * 4]; // 发生一次MinorGC。allocation1被转移到Survivor区域，allocation2被移动到老年代
		allocation3 = null;
		allocation3 = new byte[_1MB * 4];// 发生一次MinorGC
	}
	
	public static void main(String[] args) {
		// testAllocation();
		testTenuringThreshold();
	}
}
