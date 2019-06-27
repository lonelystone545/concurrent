package Five;

import java.util.LinkedList;
import java.util.Queue;

public class ProAndConsu3 {
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Producer p = new Producer(queue);
		Consumer c = new Consumer(queue);
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c);
		
		t1.start();
		t2.start();
	}
	
	static class Producer implements Runnable {
		private Queue<Integer> queue;
		public Producer(Queue<Integer> queue) {
			this.queue = queue;
		}
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized (queue) {
					while(!queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("producer:"+i);
					queue.add(i);
					queue.notifyAll();
				}
			}
		}
	}
	static class Consumer implements Runnable {
		private Queue<Integer> queue;
		public Consumer(Queue<Integer> queue) {
			this.queue = queue;
		}
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized (queue) {
					while(queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("customer:"+i);
					queue.remove();
					queue.notifyAll();
				}
			}
		}
	}
}

