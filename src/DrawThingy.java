
public class DrawThingy extends Thread {
	
	Test test;
	
	public DrawThingy(Test t) {
		test = t;
	}
	
	public void run() {
		while (true) {
			test.repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
