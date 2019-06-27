package Five;

public interface ThreadPool<Job extends Runnable> {
	//ִ�е����� ����ʵ��Runnbale�ӿ�
	void execute(Job job);
	//�ر��̳߳�
	void shutdown();
	//���ӹ������߳�
	void addWorker(int num);
	void removeWorker(int num);
	//��ȡ���ڵȴ�ִ�е���������
	int getJobSize();
}
