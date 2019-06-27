package Five;

public interface ThreadPool<Job extends Runnable> {
	//执行的任务 必须实现Runnbale接口
	void execute(Job job);
	//关闭线程池
	void shutdown();
	//增加工作者线程
	void addWorker(int num);
	void removeWorker(int num);
	//获取正在等待执行的任务数量
	int getJobSize();
}
