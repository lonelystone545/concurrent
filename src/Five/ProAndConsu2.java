package Five;

/*
 * 生产者消费者代码优化
 */
public class ProAndConsu2 {
	
	public static void main(String[] args) {
		Student s = new Student();
		ProductThread pt = new ProductThread(s);
		ConsumeThread ct = new ConsumeThread(s);
		
		Thread t1 = new Thread(pt);
		Thread t2 = new Thread(ct);
		
		t1.start();
		t2.start();
	}
	
	static class ProductThread implements Runnable {
		private Student s ;
		int x = 0;
		public ProductThread(Student s) {
			this.s = s;
		}
		public void run() {
			while(true) {
				if(x%2==0) {
					s.set("wy", 18);
				} else {
					s.set("王元", 24);
				}
				x++;
			}
		}
	}
	static class ConsumeThread implements Runnable {
		private Student s;
		public ConsumeThread(Student s) {
			this.s = s;
		}
		public void run() {
				while(true) {
					s.get();
			}
		}
	}

	static class Student {
		private String name;
		private int age;
		boolean flag = false; //表示没有数据
		
		public synchronized void set(String name,int age) {
			if(this.flag) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.name = name;
			this.age = age;
			this.flag = true;
			this.notify();
		}
		public synchronized void get() {
			if(!this.flag) {
				try {
					this.wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(this.name+"---"+this.age);
			this.flag = false;
			this.notify();
		}
	}
}


