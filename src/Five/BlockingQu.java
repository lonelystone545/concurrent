package Five;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQu {
	
	public static void main(String[] args) {
		BlockingQueue bq = new LinkedBlockingQueue<>(10);
		for(int i=0;i<1;i++) {
			new Thread(new Producer(bq)).start();
		}
		for(int i=0;i<4;i++) {
			new Thread(new Consumer(bq)).start();
		}
	}

	
	static class Producer implements Runnable {
		private BlockingQueue bq;
		public Producer(BlockingQueue bq) {
			this.bq = bq;
		}
		public void run() {
			while(true) {
				try {
//					synchronized (bq) {  //��ͬ���Ļ���������
						bq.put(1);
						System.out.println("����һ��Ԫ�أ����г���: "+bq.size());
//					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	static class Consumer implements Runnable {
		private BlockingQueue bq;
		public Consumer(BlockingQueue bq) {
			this.bq = bq;
		}
		public void run() {
			while(true) {
				try {
//					synchronized (bq) {
						bq.take();
						System.out.println("����һ��Ԫ�أ����г���: "+bq.size());
//					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
