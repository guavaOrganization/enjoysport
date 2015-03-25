package com.example.javaio;

import java.io.Flushable;
import java.io.IOException;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Flushable是可刷新数据的目标地。调用 flush 方法将所有已缓冲输出写入底层流。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MyFlushable implements Flushable {
	@Override
	public void flush() throws IOException {

	}
}
