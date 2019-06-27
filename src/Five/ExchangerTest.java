package Five;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
	
	//ʹ��volatile���εı�����Ϊ�����Ľ���
	private static volatile boolean flag = false;
	
	public static void main(String[] args) {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<Integer> ex = new Exchanger<Integer>();
		ExchangerPro ep = new ExchangerPro(ex);
		ExchangerCon ec = new ExchangerCon(ex);
		
		exec.execute(ec);
		exec.execute(ep);
		
		exec.shutdown();
	}
	
	static class ExchangerPro implements Runnable {
		
		private Exchanger<Integer> ex;
		private static int data;
		public ExchangerPro(Exchanger<Integer> ex) {
			this.ex = ex;
		}
		
		public void run() {
			while(!flag) {
				for(int i=1;i<3;i++) {
					try {
						data = i;
						System.out.println("ExchangerPro before data: "+data);
						//�ȵ��Ļ�����
						data = ex.exchange(data);
						System.out.println("ExchangerPro after data: "+data); //����ᷢ���̰߳�ȫ����
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
				flag = true;
			}
		}
	}
	static class ExchangerCon implements Runnable {
		private Exchanger<Integer> ex;
		private static int data;
		public ExchangerCon(Exchanger<Integer> ex) {
			this.ex = ex;
		}
		public void run() {
			while(!flag) {
				try {
					data = 0;
					System.out.println("ExchangerCon before data: "+data);
					//�ȵ��Ļ�����
					data = ex.exchange(data);
					System.out.println("ExchangerCon after data: "+data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
	}
}

