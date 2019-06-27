package Five;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/*
 * 利用FutureTask和ExecutorService，可以用多线程的方式提交计算任务，
 * 主线程继续执行其他任务，当主线程需要子线程的计算结果时，在异步获取子线程的执行结果。
 */
public class Futuretask {
	public static void main(String[] args) {
		Futuretask futureTask = new Futuretask();
		//创建任务集合
		List<FutureTask<Integer>> tasks = new ArrayList<>();
		//创建线程池
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for(int i=0;i<10;i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>(new ComputeTask(i, ""+i)); 
			tasks.add(ft);
			//提交给线程池执行任务
			exec.submit(ft);
		}
		System.out.println("所有计算任务提交完毕,主线程继续做自己的事情");
		
		Integer totalRes = 0;
		for(FutureTask<Integer> ft : tasks) {
			try {
				//get方法会阻塞 直至获取结果为止
				totalRes += ft.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		exec.shutdown();
		System.out.println("多任务计算后的总结果为:"+totalRes);
	}
	
	static class ComputeTask implements Callable<Integer> {
		private Integer result = 0;
		private String taskName = "";
		
		public ComputeTask(Integer result,String taskName) {
			this.result = result;
			this.taskName = taskName;
			System.out.println("生成子线程计算任务: "+taskName);
		}
		@Override
		public Integer call() throws Exception {
			for(int i=0;i<100;i++) {
				result += i;
			}
			//睡眠5s 观察主线程行为 主线程会继续执行 到要取得futuretask的结果是等待直至完成
			Thread.sleep(5000);
			System.out.println("子线程计算任务 "+taskName+" 执行完成");
			return result;
		}
	}
}
