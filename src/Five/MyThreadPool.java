package Five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class MyThreadPool<Job extends Runnable> implements ThreadPool<Job>{
	
	//工作者线程的最大数
	private static final int MAX_WORKER_NUMBERS = 10;
	//工作者线程默认数
	private static final int DEFAULT_WORKER_NUMBERS = 5;
	private static final int MIN_WORKER_NUMBERS = 1;
	//任务列表  工作者线程从任务列表中取出任务执行
	private final LinkedList<Job> jobs = new LinkedList<Job>();
	//工作者线程列表
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
	//工作者线程数量
	private int workerNum;
	
	public MyThreadPool() {
		this.workerNum = DEFAULT_WORKER_NUMBERS;
		initializeWorker(this.workerNum);
	}
	public MyThreadPool(int num) {
		if(num>MAX_WORKER_NUMBERS) {
			this.workerNum = MAX_WORKER_NUMBERS;
		} else {
			this.workerNum = num;
		}
		initializeWorker(this.workerNum);
	}
	
	//初始化工作者县城
	private void initializeWorker(int num) {
		for(int i=0;i<num;i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker);
			thread.start();
		}
	}
	@Override
	public void execute(Job job) {
		if(job!=null) {
			//根据 等待 通知 机制 这里必须加锁
			synchronized (jobs) {
				jobs.add(job);
				jobs.notify();
			}
		}
	}

	//关闭线程池 也就是关闭所有线程
	@Override
	public void shutdown() {
		for(Worker w : workers) {
			w.shutdown();
		}
	}

	@Override
	public void addWorker(int num) {
		//防止该线程还没增加完而下个线程继续增加而导致工作者线程超过最大值
		synchronized (jobs) {
			if(num+this.workerNum>MAX_WORKER_NUMBERS) {
				num = MAX_WORKER_NUMBERS - this.workerNum;
			}
			initializeWorker(num);
			this.workerNum += num;
		}
	}
	
	@Override
	public void removeWorker(int num) {
		synchronized (jobs) {
			if(num>=this.workerNum) {
				throw new IllegalArgumentException("超过已有线程数");
			}
			for(int i=0;i<num;i++) {
				Worker w = workers.get(i);
				if(w!=null) {
					w.shutdown();
					workers.remove(i);
				}
			}
			this.workerNum -= num;
		}
	}
	@Override
	public int getJobSize() {
		return workers.size();
	}
	
	class Worker implements Runnable {
		
		//表示是否运行该worker
		private volatile boolean running = true;
		@Override
		public void run() {
			while(running) {
				Job job = null;
				synchronized (jobs) {
					if(jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					job = jobs.removeFirst();
				}
				if(job!=null) {
					job.run();
				}
			}
		}
		
		public void shutdown() {
			running = false;
		}
	}
}

