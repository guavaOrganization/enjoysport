package com.guava.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 *
 * @Description Guava工具类
 * @author 八两俊 
 * @date 2014年10月6日 下午9:58:22 
 * 
 *****************************@修改记录***************************
 *      @时间                  @修改人                @修改备注
 *   2014-10-06				        八两俊				新增getWebRoot()方法
 */
public class GuavaUtil {
	
	/**
	 * 
	 * @Description 获取到Web工程的根目录
	 * @return Web工程的根据路绝对路径
	 *
	 *****************************@修改记录***************************
	 *      @时间                  @修改人                @修改备注
	 *   2014-10-06				        八两俊			           获取Web工程的根据路绝对路径
	 *   
	 * @throws
	 */
	public static String getWebRoot() {
		try {
			String path = GuavaUtil.class.getClassLoader().getResource("").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
