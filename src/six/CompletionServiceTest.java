package six;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceTest {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		CompletionService<Integer> com = new ExecutorCompletionService<Integer>(pool);
		
		try {
			for(int i=0;i<10;i++) {
				//提交任务
				com.submit(new CompletionServiceTest.Task(i));
			}
			for(int i=0;i<10;i++) {
				//take方法等待下一个结果并返回future对象
				//poll方法不阻塞，如果没有值，则直接返回null
				System.out.println(com.take().get());
			} 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
	}
	
	static class Task implements Callable<Integer> {
		private int i;
		Task(int i) {
			this.i = i;
		}
		public Integer call() {
			try {
				Thread.sleep((long) (Math.random()*1000));
				System.out.println(Thread.currentThread().getName()+" "+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return i;
		}
	}
}
