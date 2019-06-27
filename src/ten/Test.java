package ten;
/*
 * ��������
 */
import javax.swing.plaf.synth.SynthSeparatorUI;

public class Test {
	public static void main(String[] args) {
		Person p1 = new Person("p1");
		Person p2 = new Person("p2");
		p1.friend = p2;
		p2.friend = p1;
		p1.bow();
		new Thread(p1).start();
		new Thread(p2).start();
	}
}
class Person implements Runnable{
	Person friend;
	String name;
	public Person(String name) {
		this.name = name;
	}
	//�Ϲ�
	boolean bow = false;
	public void bow() {
		System.out.println(name+"bow");
		bow = true;
	}
	//�Ϲ�����
	public void up() {
		System.out.println(name+"up");
		bow = false;
	}
	public void run() {
		while(friend.bow) {
			//�����ѾϹ�ʱ�Լ�ҲҪ�Ϲ�
			this.bow();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//1s��Ϲ�����
			this.up();
		}
	}
}