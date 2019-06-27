package Five;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/*
 * ����FutureTask��ExecutorService�������ö��̵߳ķ�ʽ�ύ��������
 * ���̼߳���ִ���������񣬵����߳���Ҫ���̵߳ļ�����ʱ�����첽��ȡ���̵߳�ִ�н����
 */
public class Futuretask {
	public static void main(String[] args) {
		Futuretask futureTask = new Futuretask();
		//�������񼯺�
		List<FutureTask<Integer>> tasks = new ArrayList<>();
		//�����̳߳�
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for(int i=0;i<10;i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>(new ComputeTask(i, ""+i)); 
			tasks.add(ft);
			//�ύ���̳߳�ִ������
			exec.submit(ft);
		}
		System.out.println("���м��������ύ���,���̼߳������Լ�������");
		
		Integer totalRes = 0;
		for(FutureTask<Integer> ft : tasks) {
			try {
				//get���������� ֱ����ȡ���Ϊֹ
				totalRes += ft.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		exec.shutdown();
		System.out.println("������������ܽ��Ϊ:"+totalRes);
	}
	
	static class ComputeTask implements Callable<Integer> {
		private Integer result = 0;
		private String taskName = "";
		
		public ComputeTask(Integer result,String taskName) {
			this.result = result;
			this.taskName = taskName;
			System.out.println("�������̼߳�������: "+taskName);
		}
		@Override
		public Integer call() throws Exception {
			for(int i=0;i<100;i++) {
				result += i;
			}
			//˯��5s �۲����߳���Ϊ ���̻߳����ִ�� ��Ҫȡ��futuretask�Ľ���ǵȴ�ֱ�����
			Thread.sleep(5000);
			System.out.println("���̼߳������� "+taskName+" ִ�����");
			return result;
		}
	}
}
