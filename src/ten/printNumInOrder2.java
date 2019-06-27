package ten;

import java.util.concurrent.Semaphore;

public class printNumInOrder2 {
	
	//使用信号量实现
	private static Semaphore s1 = new Semaphore(1);
	private static Semaphore s2 = new Semaphore(1);
	private static Semaphore s3 = new Semaphore(1);
	private static Semaphore s4 = new Semaphore(1);
	
	public static void main(String[] args) {
		try {
			s2.acquire();
			s3.acquire();
			s4.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new FirstThread().start();
		new SecondThread().start();
		new ThirdThread().start();
		new ForthThread().start();
	}
	
	static class FirstThread extends Thread {
		public void run() {
			try {
				while(true) {
					s1.acquire();
					System.out.println(1);
					s2.release();
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	static class SecondThread extends Thread {
		public void run() {
			while(true) {
				try {
					while(true) {
						s2.acquire();
						System.out.println(2);
						s3.release();
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	static class ThirdThread extends Thread {
		public void run() {
			while(true) {
				try {
					while(true) {
						s3.acquire();
						System.out.println(3);
						s4.release();
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	static class ForthThread extends Thread {
		public void run() {
			while(true) {
				try {
					while(true) {
						s4.acquire();
						System.out.println(4);
						s1.release();
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
