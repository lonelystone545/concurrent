package Five;

public class ProAndConsu {
	
	public static void main(String[] args) {
		Student s = new Student();
		ProductThread pt = new ProductThread(s);
		ConsumeThread ct = new ConsumeThread(s);
		
		Thread t1 = new Thread(pt);
		Thread t2 = new Thread(ct);
		
		t1.start();
		t2.start();
	}
}

class ProductThread implements Runnable {
	private Student s ;
	int x = 0;
	public ProductThread(Student s) {
		this.s = s;
	}
	public void run() {
		while(true) {
			synchronized (s) {
				//如果有数据
				if(s.flag) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(x%2==0) {
					s.setName("wy");
					s.setAge(18);
				} else {
					s.setName("王元");
					s.setAge(24);
				}
				x++;
				//此时已经有了数据
				s.flag = true;
				s.notify();
			}
		}
	}
}
class ConsumeThread implements Runnable {
	private Student s;
	public ConsumeThread(Student s) {
		this.s = s;
	}
	public void run() {
		synchronized (s) {
			while(true) {
				//如果没有数据
				if(!s.flag) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(s.getName()+"---"+s.getAge());
				s.flag = false;
				s.notify();
			}
		}
	}
}

class Student {
	private String name;
	private int age;
	boolean flag = false; //表示没有数据
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
