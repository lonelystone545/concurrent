package Five;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
	public static void main(String[] arsg) {
		//���õȴ�������߳���Ϊ2
		CyclicBarrier cb = new CyclicBarrier(2);
		ExecutorService es = Executors.newCachedThreadPool();
		Runnable runnale = new Runnable() {
			public void run() {
				try {
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("�߳�"+Thread.currentThread().getName()+"�������Ｏ�ϵص�1,��ǰ����"+cb.getNumberWaiting()+"���߳��ڵȴ�");
					cb.await();
					
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("�߳�"+Thread.currentThread().getName()+"�������Ｏ�ϵص�2,��ǰ����"+cb.getNumberWaiting()+"���߳��ڵȴ�");
					cb.await();
					
					Thread.sleep((long) (Math.random()*1000));
					System.out.println("�߳�"+Thread.currentThread().getName()+"�������Ｏ�ϵص�3,��ǰ����"+cb.getNumberWaiting()+"���߳��ڵȴ�");
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
