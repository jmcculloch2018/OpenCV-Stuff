package CircleDetection;

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

public class CircleDetectionTest extends Thread {

	Webcam camera;

	CircleDetectionPanel panel;

	public static void main(String[] args) {
		new CircleDetectionTest();
	}

	public CircleDetectionTest() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new CircleDetectionPanel((int) cameraSize.width,
				(int) cameraSize.height, "Circle Detector");

		start();
	}

	public void run() {

		while (true) {

			Mat source = camera.getMat();

			BufferedImage sourceImg = ImageMatConvert.matToImage(source);

			Mat grayscale = new Mat(source.rows(), source.cols(),
					CvType.CV_8UC1);

			Imgproc.cvtColor(source, grayscale, Imgproc.COLOR_RGB2GRAY);

			Mat circles = new Mat();

			Imgproc.HoughCircles(grayscale, circles,
					Imgproc.CV_HOUGH_GRADIENT, 1, 25, 200, 100, 0, 0);

			Graphics g = panel.getGraphics();

			g.drawImage(sourceImg, 0, 0, null);

			g.setColor(Color.GREEN);

			for (int i = 0; i < circles.cols(); i++) {
				double[] circle = circles.get(0, i);

				g.drawOval((int) circle[0] - (int) circle[2], (int) circle[1]
						- (int) circle[2], (int) circle[2] * 2,
						(int) circle[2] * 2);
			}
		}
	}
}
