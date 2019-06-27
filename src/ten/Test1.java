package ten;

public class Test1 {
	public static void main(String[] args) {
		
		for(int i=0;i<10;i++) {
			new MyThread(i).start();
		}
	}
}
class MyThread extends Thread {
	private static  int[] arr = {1,2};
	private int i;
	public MyThread(int i) {
		this.i = i;
	}
	public void run() {
		synchronized(arr) {
			for(int j=0;j<10;j++) {
				System.out.println(i+":"+j);
			}
		}
	}
}
