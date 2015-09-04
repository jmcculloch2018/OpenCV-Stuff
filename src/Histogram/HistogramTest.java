package Histogram;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;

@SuppressWarnings("serial")
public class HistogramTest extends JPanel {

	myHistogram histogram;

	public static void main(String[] args) {
		new HistogramTest();
	}

	public HistogramTest() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/dog.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame f = new JFrame();

		f.add(this);

		setSize(800, 600);

		f.pack();

		f.setVisible(true);

		histogram = new myHistogram(image, 256);

		histogram.calculateHistogram();

		int[][] hist = histogram.getHistogram();

		Mat imgMat = ImageMatConvert.BufferedImageToMatPixels(image);

		for (int row = 0; row < hist.length; row++) {
			for (int col = 0; col < hist[row].length; col++) {
				System.out.print(hist[row][col] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		Mat desMat = new Mat(imgMat.rows(), imgMat.cols(), imgMat.type());
		
		Imgproc.GaussianBlur(imgMat, desMat, new Size(5, 5), 0);
		
		histogram = new myHistogram(desMat, 256);
		histogram.calculateHistogram();
		
		hist = histogram.getHistogram();
		
		for (int row = 0; row < hist.length; row++) {
			for (int col = 0; col < hist[row].length; col++) {
				System.out.print(hist[row][col] + " ");
			}
			System.out.println();
		}
	}

	public void paintComponent(Graphics g) {
	}
}
