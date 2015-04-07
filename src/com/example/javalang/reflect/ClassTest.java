package com.example.javalang.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.junit.Test;


/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * Class 类的实例表示正在运行的 Java 应用程序中的类和接口。
 * 枚举是一种类，注释是一种接口。
 * 每个数组属于被映射为 Class 对象的一个类，所有具有相同元素类型和维数的数组都共享该 Class 对象。
 * 
 * 基本的 Java 类型（boolean、byte、char、short、int、long、float 和 double）和关键字 void 也表示为 Class 对象。
 * 
 * Class 没有公共构造方法。Class 对象是在 【加载类】时由【Java虚拟机】以及【通过调用类加载器中的 defineClass方法自动构造】的。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ClassTest {
	
	public void test_type() {
		// 向Class引用添加泛型语法的原因仅仅是为了提供编译期类型检查，因此如果你操作有误，稍后立即就会发现一点.
		Class<? extends Number> bounded = int.class;
		bounded = Integer.class;
		bounded = Number.class;
		bounded = Double.class;
		
		Class<Integer> inte = int.class;
		// inte = double.class;//编译失败
	}
	
	// @Test
	public void test_toString() {
		System.out.println(ClassInfo.class.toString());// 类
		System.out.println(InterfaceInfo.class.toString());// 接口
		System.out.println(EnumInfo.class.toString());// 枚举
		System.out.println(AnnotationInfo.class.toString());// 注解
		System.out.println(void.class.toString());// void
		System.out.println(int.class.toString());// 基本类型
	}
	
	// @Test
	/**
	 * 使用给定的类加载器，返回与带有给定字符串名的类或接口相关联的 Class 对象。
	 * （以 getName 所返回的格式）给定一个类或接口的完全限定名，此方法会试图【定位、加载和链接(包括验证、准备、解析)】该类或接口。
	 * 指定的类加载器用于加载该类或接口。如果参数loader为 null，则该类通过引导类加载器加载。只有 initialize参数为 true且以前未被初始化时，才初始化该类。
	 * 如果name表示一个基本类型或 void，则会尝试在未命名的包中定位用户定义的名为 name 的类。因此，该方法不能用于获得表示基本类型或 void 的任何 Class 对象。
	 * 如果 name 表示一个数组类，则会加载但不初始化该数组类的组件类型。
	 * @since
	 * @throws
	 */
	public void test_forName() {
		try {
			// 相当于执行了类加载过程中的"加载-->验证-->准备-->解析-->初始化"五步。注意初始化不是实例化。会在JVM堆上生成类对应Class的对象
			// Class cls = Class.forName("com.example.javalang.reflect.ClassInfo");
			
			// 相当于执行了类加载过程的"加载-->验证-->准备-->解析"四步
			// Class cls = ClassInfo.class;
			
			// 由第二个参数控制是否初始化
			Class cls = Class.forName("com.example.javalang.reflect.ClassInfo", true, getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建此 Class 对象所表示的类的一个新实例。如同用一个带有一个【空参数】列表的 new 表达式实例化该类。如果该类尚未初始化，则初始化这个类。
	 * @since
	 * @throws
	 */
	// @Test
	public void test_newInstance() {
		try {
			Class cls = Class.forName("com.example.javalang.reflect.ClassInfo", false, getClass().getClassLoader());
			ClassInfo classInfo =  (ClassInfo) cls.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	// @Test
	public void test_isInstance() {
		try {
			Class cls = Class.forName("com.example.javalang.reflect.ClassInfo", false, getClass().getClassLoader());
			ClassInfo classInfo = (ClassInfo) cls.newInstance();

			ClassLoader loader = new ClassLoader() {
				@Override
				public Class<?> loadClass(String name) throws ClassNotFoundException {
					try {
						String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
						InputStream is = getClass().getResourceAsStream(fileName);
						if (null == is)
							return super.loadClass(name);
						byte[] b = new byte[is.available()];
						is.read(b);
						return defineClass(name, b, 0, b.length);
					} catch (IOException e) {
						throw new ClassNotFoundException(name);
					}
				}
			};
			
			Class otherCls = Class.forName("com.example.javalang.reflect.ClassInfo", false, loader);
			System.out.println(cls.isInstance(otherCls.newInstance())); // 类加载器不一致
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判定此Class对象所表示的类或接口与指定的Class参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回true；否则返回false。
	 * 如果该 Class表示一个基本类型，且指定的Class参数正是该Class对象，则该方法返回true；否则返回 false。
	 * 
	 * @since
	 * @throws
	 */
	// @Test
	public void test_isAssignableFrom() {
		// 调用者和参数一样
		System.out.println(ClassInfo.class.isAssignableFrom(ClassInfo.class));
		System.out.println(ClassInfo.class.isAssignableFrom(InterfaceInfo.class)); // false
		// 调用者是参数的超类或者接口
		System.out.println(InterfaceInfo.class.isAssignableFrom(ClassInfo.class));// true
	}
	
	/**
	 * 以 String 的形式返回此 Class 对象所表示的实体（类、接口、数组类、基本类型或 void）名称。
	 * @since
	 * @throws
	 */
	// @Test
	public void test_getName() {
		System.out.println(ClassInfo.class.getName());
		System.out.println(void.class.getName());
		System.out.println(int.class.getName());
		System.out.println(String.class.getName());
	}
	
	/**
	 * 返回该类的类加载器。有些实现可能使用 null 来表示引导类加载器。如果该类由引导类加载器加载，则此方法在这类实现中将返回 null。
	 * @since
	 * @throws
	 */
	// @Test
	public void test_getClassLoader() {
		// sun.misc.Launcher$AppClassLoader@157c2bd --> 应用类加载器，加载classpath下面的字节码文件
		System.out.println(this.getClass().getClassLoader());
	}
	
	@Test
	public void test_getTypeParameters() {
		try {
			Class classInfo = Class.forName("com.example.javalang.reflect.InterfaceClassInfo");
			System.out.println(classInfo);
			TypeVariable<?>[] variables = classInfo.getTypeParameters();
			for (TypeVariable<?> type : variables) {
				System.out.println(type.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
