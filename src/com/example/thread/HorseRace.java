package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HorseRace {
	static final int FINISH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();

	private ExecutorService executor = Executors.newCachedThreadPool();

	private CyclicBarrier barrier;

	public HorseRace(int nHorses, final int pause) {
		// 它将在给定数量的参与者（线程）处于等待状态时启动
		barrier = new CyclicBarrier(nHorses, new Runnable() {// Runnable是一个"栅栏动作"，当计数值到达0时自动执行
			@Override
			public void run() {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++) {
					sb.append("=");
				}
				System.out.println(sb);
				for (Horse horse : horses) {
					System.out.println(horse.tracks());
				}
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						System.out.println(horse + " Won!");
						executor.shutdownNow();
						return;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					System.out.println("barrier-action sleep interrupted");
				}
			}
		});

		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			executor.execute(horse);
		}
	}

	public static void main(String[] args) {
		new HorseRace(7, 200);
	}
}
