package ten;

public class PhilosopherTest {
	
	public static void main(String[] args) {
		Fork fork = new Fork();
		for(int i=0;i<5;i++) {
			new Philosopher(i,fork).start();
		}
	}
	
	static class Philosopher extends Thread {
		private Fork fork;
		private int i;
		public Philosopher(int i,Fork fork) {
			this.i = i;
			this.fork = fork;
		}
		
		public void run() {
			while(true) {
				thinking();
				fork.takeFork(i);
				eating();
				fork.putFork(i);
			}
		}
		
		//�����ж�λ��ѧ����ͬʱ�Է���˼��������Ҫ��ͬ��
		public void eating() {
			System.out.println("philosopher "+i+" is eating");
			try {
				Thread.sleep(1000);//ģ��Է���ʱ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void thinking() {
			System.out.println("philosopher "+i+" is thinking");
			try {
				Thread.sleep(1000);//ģ��˼����ʱ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//�Ͳ���
	static class Fork {
		private boolean[] used = {false,false,false,false,false};
		
		public synchronized void takeFork(int i) {
			//����������һ����ʹ��ʱ����ȴ�
			while(used[i] || used[(i+1)%5]) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			used[i] = true;
			used[(i+1)%5] = true;
		}
		
		//������Բ���ͬ�� ������Ҫ��volatile���α���������Ϊ��ʹ��notifyAll�������ͬ��
		public synchronized void putFork(int i) {
			used[i] = false;
			used[(i+1)%5] = false;
			notifyAll(); //�����������ڵȴ����߳�
		}
	}
}

