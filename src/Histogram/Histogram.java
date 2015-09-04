package Histogram;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

@SuppressWarnings("serial")
public class Histogram extends JPanel implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Histogram());
	}

	private void setUpGUI() {
		JFrame frame = new JFrame("Frame");

		frame.add(this);

		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void generateHistogram() {
		Mat image = Imgcodecs
				.imread("C:/Program Files (x86)/eclipse/OpenCV/data/dog.jpg");
		
		System.out.println(image.get(2, 2));
	}


	public void run() {
		setUpGUI();
		generateHistogram();
	}

}
