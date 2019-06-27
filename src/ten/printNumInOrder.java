package ten;

import java.util.concurrent.atomic.AtomicInteger;

public class printNumInOrder {
	//这里必须使用原子类 保证++操作是原子的
	private static volatile AtomicInteger state = new AtomicInteger(0);
	
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
				if(state.intValue()%4==0) {
					System.out.println(1);
					state.getAndIncrement();//原子操作
				}
			}
		}
	}
	static class SecondThread extends Thread {
		public void run() {
			while(true) {
				if(state.intValue()%4==1) {
					System.out.println(2);
					state.getAndIncrement();
				}
			}
		}
	}
	static class ThirdThread extends Thread {
		public void run() {
			while(true) {
				if(state.intValue()%4==2) {
					System.out.println(3);
					state.getAndIncrement();
				}
			}
		}
	}
	static class ForthThread extends Thread {
		public void run() {
			while(true) {
				if(state.intValue()%4==3) {
					System.out.println(4);
					state.getAndIncrement();
				}
			}
		}
	}
}
