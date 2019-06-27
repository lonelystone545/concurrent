package Three;

import java.awt.Event;
import java.util.EventListener;

public class SafeListener {
	private  EventListener listener;
	public SafeListener() {
		System.out.println(11);
		listener = new EventListener() {
			public void onEvent(Event e ) {
				System.out.println(11);
				doSomething(e);
			}
		};
	}
	private void doSomething(Event e) {
	}
	
	public static void main(String[] args) {
		SafeListener s = new SafeListener();
	}
}
