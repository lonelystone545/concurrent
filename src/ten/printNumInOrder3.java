package ten;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class printNumInOrder3 {
	//使用同一个锁
	private static Lock lock = new ReentrantLock();
	private static int state = 0;
	
	public static void main(String[] args) {
		FirstThread t1 = new FirstThread();
		SecondThread t2 = new SecondThread();
		ThirdThread t3 = new ThirdThread();
		ForthThread t4 = new ForthThread();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
	static class FirstThread extends Thread {
		public void run() {
			while(true) {
				lock.lock();
				if(state%4==0) {
					System.out.println(1);
					state++;
				}
				lock.unlock();
			}
		}
	}
	static class SecondThread extends Thread {
		public void run() {
			while(true) {
				lock.lock();
				if(state%4==1) {
					System.out.println(2);
					state++;
				}
				lock.unlock();
			}
		}
	}
	static class ThirdThread extends Thread {
		public void run() {
			while(true) {
				lock.lock();
				if(state%4==2) {
					System.out.println(3);
					state++;
				}
				lock.unlock();
			}
		}
	}
	static class ForthThread extends Thread {
		public void run() {
			while(true) {
				lock.lock();
				if(state%4==3) {
					System.out.println(4);
					state++;
				}
				lock.unlock();
			}
		}
	}
}
