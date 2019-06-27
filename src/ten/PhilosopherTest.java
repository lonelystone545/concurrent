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
		
		//可以有多位哲学家在同时吃饭和思考，不需要加同步
		public void eating() {
			System.out.println("philosopher "+i+" is eating");
			try {
				Thread.sleep(1000);//模拟吃饭的时间
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void thinking() {
			System.out.println("philosopher "+i+" is thinking");
			try {
				Thread.sleep(1000);//模拟思考的时间
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//餐叉类
	static class Fork {
		private boolean[] used = {false,false,false,false,false};
		
		public synchronized void takeFork(int i) {
			//当左右中有一个在使用时，则等待
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
		
		//这里可以不加同步 但是需要用volatile修饰变量，但是为了使用notifyAll，必须加同步
		public synchronized void putFork(int i) {
			used[i] = false;
			used[(i+1)%5] = false;
			notifyAll(); //唤醒所有正在等待的线程
		}
	}
}

