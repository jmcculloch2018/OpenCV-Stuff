package CircleDetectionROI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class CircleDetectionROITest extends Thread {

	Webcam camera;

	CircleDetectionROIPanel panel;

	public static void main(String[] args) {
		new CircleDetectionROITest();
	}

	public CircleDetectionROITest() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new CircleDetectionROIPanel((int) cameraSize.width,
				(int) cameraSize.height, "Circle Detector ROI");

		start();
	}

	Mat lastFrame;

	public void run() {

		lastFrame = camera.getMat();

		while (true) {

			Mat source = camera.getMat();

			BufferedImage sourceImg = ImageMatConvert.matToImage(source);

			Mat grayscale = new Mat(source.rows(), source.cols(),
					CvType.CV_8UC1);

			Imgproc.cvtColor(source, grayscale, Imgproc.COLOR_RGB2GRAY);

			Mat circles = new Mat();

			Imgproc.HoughCircles(grayscale, circles, Imgproc.CV_HOUGH_GRADIENT,
					1, 25, 200, 100, 0, 0);

			Graphics g = panel.getGraphics();

			g.drawImage(sourceImg, 0, 0, null);

			g.setColor(Color.GREEN);

			for (int i = 0; i < circles.cols(); i++) {
				double[] circle = circles.get(0, i);

				g.drawOval((int) circle[0] - (int) circle[2], (int) circle[1]
						- (int) circle[2], (int) circle[2] * 2,
						(int) circle[2] * 2);
			}

			lastFrame = source;

			System.out.println(getPercentChange(source, lastFrame));
		}
	}

	public double getPercentChange(Mat source1, Mat source2) {

		double percent = 0;

		for (int row = 0; row < source1.rows(); row++) {
			for (int col = 0; col < source1.cols(); col++) {
				if (!isSimilar(source1.get(row, col), source2.get(row, col), 5)) {
					System.out.println(row + " " + col);
					percent++;
				}
			}
		}

		return percent / (source1.rows() * source1.cols());
	}

	public boolean isSimilar(double[] pix1, double[] pix2, double error) {
		if (Math.abs(pix1[0] - pix2[0]) > error) {
			System.out.println("a");
			return false;
		}
		if (Math.abs(pix1[1] - pix2[1]) > error) {
			System.out.println("b");
			return false;
			
		}
		if (Math.abs(pix1[2] - pix2[2]) > error) {
			System.out.println("c");
			return false;
		}
		return true;
	}
}
