package com.example.concurrent;

public class TestObject {
	class Condition {
	}

	public static void main(String[] args) throws InterruptedException {
		TestObject object = new TestObject();
		TestObject.Condition condition = object.new Condition();
		synchronized (condition) {
			// condition.wait();// 当前线程必须拥有此对象监视器。该线程发布对此监视器的所有权并等待，直到其他线程通过调用notify方法或 notifyAll方法通知在此对象的监视器上等待的线程醒来。然后该【线程将等到重新获得对监视器的所有权】后才能继续执行。
			/** main线程的状态将变为WAITING
			"main" prio=6 tid=0x0128e000 nid=0x1230 in Object.wait() [0x016bf000]
   				java.lang.Thread.State: WAITING (on object monitor)
				at java.lang.Object.wait(Native Method)
				- waiting on <0x037853d0> (a com.example.concurrent.TestObject$Condition)
				at java.lang.Object.wait(Object.java:503)
				at com.example.concurrent.TestObject.main(TestObject.java:11)
				- locked <0x037853d0> (a com.example.concurrent.TestObject$Condition)
			
				Locked ownable synchronizers:
				- None
			 */
			/**
			 * 在其他线程调用此对象的notify()方法或notifyAll()方法，或者超过指定的时间量前，导致当前线程等待。
			 * 
			 * 当前线程必须拥有此对象监视器。
			 * 此方法导致当前线程（称之为 T）将其自身放置在对象的等待集中，然后放弃此对象上的所有同步要求。出于线程调度目的，在发生以下四种情况之一前，线程T被禁用，且处于休眠状态：
			 * 1、其他某个线程调用此对象的notify方法，并且线程T碰巧被任选为被唤醒的线程。
			 * 2、其他某个线程调用此对象的notifyAll方法。
			 * 3、其他某个线程中断线程T。
			 * 4、大约已经到达指定的实际时间。但是，如果timeout为零，则不考虑实际时间，在获得通知前该线程将一直等待。
			 * 
			 * 然后，【从对象的等待集中删除线程T】，并重新进行线程调度。然后，该线程以常规方式与其他线程竞争，以获得在该对象上同步的权利；
			 * 一旦获得对该对象的控制权，该对象上的所有其同步声明都将被恢复到以前的状态，这就是调用wait方法时的情况。
			 * 然后，线程T从wait方法的调用中返回。所以，从wait方法返回时，该对象和线程T的同步状态与调用wait方法时的情况完全相同。
			 * 
			 * 在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。虽然这种情况在实践中很少发生，但是应用程序必须通过以下方式防止其发生，
			 * 即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中，如下面的示例：
			 * synchronized (obj) {
			 * 	while (<condition does not hold>)
			 * 		obj.wait(timeout);
			 * 		... // Perform action appropriate to condition
			 * }
			 * 
			 * 注意，【由于wait方法将当前线程放入了对象的等待集中】，所以它只能解除此对象的锁定；可以同步当前线程的任何其他对象在线程等待时仍处于锁定状态。
			 */
			condition.wait(1000 * 1000);
			/**
			 * main线程状态为TIMED_WAITING
				"main" prio=6 tid=0x0016e000 nid=0x1f0c in Object.wait() [0x0174f000]
   				java.lang.Thread.State: TIMED_WAITING (on object monitor)
				at java.lang.Object.wait(Native Method)
				- waiting on <0x038153e0> (a com.example.concurrent.TestObject$Condition)
				at com.example.concurrent.TestObject.main(TestObject.java:48)
				- locked <0x038153e0> (a com.example.concurrent.TestObject$Condition)

   				Locked ownable synchronizers:
				- None
			 */
		}
		
		/**
		 * 唤醒在此对象监视器上等待的单个线程。【如果所有线程都在此对象上等待，则会选择唤醒其中一个线程】。选择是任意性的，并在对实现做出决定时发生。线程通过调用其中一个wait方法，【在对象的监视器上等待】。
		 * 
		 * 直到当前线程放弃此对象上的锁定，才能继续执行被唤醒的线程。被唤醒的线程将以常规方式与在该对象上主动同步的其他所有线程进行竞争；例如，唤醒的线程在作为锁定此对象的下一个线程方面没有可靠的特权或劣势。
		 * 
		 */
		condition.notify();
	}
}
