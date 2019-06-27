package Five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class MyThreadPool<Job extends Runnable> implements ThreadPool<Job>{
	
	//�������̵߳������
	private static final int MAX_WORKER_NUMBERS = 10;
	//�������߳�Ĭ����
	private static final int DEFAULT_WORKER_NUMBERS = 5;
	private static final int MIN_WORKER_NUMBERS = 1;
	//�����б�  �������̴߳������б���ȡ������ִ��
	private final LinkedList<Job> jobs = new LinkedList<Job>();
	//�������߳��б�
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
	//�������߳�����
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
	
	//��ʼ���������س�
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
			//���� �ȴ� ֪ͨ ���� ����������
			synchronized (jobs) {
				jobs.add(job);
				jobs.notify();
			}
		}
	}

	//�ر��̳߳� Ҳ���ǹر������߳�
	@Override
	public void shutdown() {
		for(Worker w : workers) {
			w.shutdown();
		}
	}

	@Override
	public void addWorker(int num) {
		//��ֹ���̻߳�û��������¸��̼߳������Ӷ����¹������̳߳������ֵ
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
				throw new IllegalArgumentException("���������߳���");
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
		
		//��ʾ�Ƿ����и�worker
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

