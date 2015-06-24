package com.example.classicalalgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 *     设有n个人围成一圈，从地1个人开始报数，数到第m个人出列,然后从出列的下一个人开始报数，数到第m个人又出列，... ， 如此反复到所有的人都全部出列为止。
 *     设n个人的编码分别为1，2，...，打印出出列的顺序。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ShoutNumberGame {
	public static void main(String[] args) {
		shoutNumberGame(10, 3);
	}
	public static void shoutNumberGame(int total, int step) {
		boolean[] isOuts = new boolean[total]; // 用于标记小伙伴是否出列
		List<Integer> gamerNumberList = new ArrayList<Integer>(10); // 结果列表
		int gameOverCondition = total; // 游戏参与人数
		int count = 0;// 计数器
		while (gameOverCondition > 0) {
			for (int i = 0; i < total; i++) {
				if (gameOverCondition == 1 && !isOuts[i]) { // 最后一个人
					gamerNumberList.add(i + 1);
					gameOverCondition--;
				} else if (!isOuts[i]) { // 我未出列，我报数
					if (++count == step) { // 哎妈呀，报到我了，我滚粗
						isOuts[i] = true; // 打给标记
						gameOverCondition--;// 少了一个小伙伴
						gamerNumberList.add(i + 1);
						count = 0 ;// 重置计数器
					}
				}
			}
		}
		System.out.println("总共有：" + gamerNumberList.size() + "个小伙伴参与游戏");
		for(Integer i : gamerNumberList){
			System.out.println("第 " + i + " 个小伙伴出列");
		}
	}
}
