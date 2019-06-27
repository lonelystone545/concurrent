package Five;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
	public static void main(String[] arsg) {
		//设置等待到达的线程数为2
		CyclicBarrier cb = new CyclicBarrier(2);
		ExecutorService es = Executors.newCachedThreadPool();
		Runnable runnale = new Runnable() {
			public void run() {
				try {
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点1,当前已有"+cb.getNumberWaiting()+"个线程在等待");
					cb.await();
					
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点2,当前已有"+cb.getNumberWaiting()+"个线程在等待");
					cb.await();
					
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点3,当前已有"+cb.getNumberWaiting()+"个线程在等待");
					cb.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		for(int i=0;i<2;i++) {
			es.execute(runnale);
//			es.submit(runnale);
		}
		es.shutdown();
	}
}
