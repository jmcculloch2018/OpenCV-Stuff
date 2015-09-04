package CannyEdge;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class ObjectDetectionTest extends Thread {

	double lowThreshold = 75, ratio = 3;

	Webcam camera;

	CannyEdgePanel panel;

	int frameRate = 10;

	public static void main(String[] args) {
		new ObjectDetectionTest();
	}

	public ObjectDetectionTest() {

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new CannyEdgePanel((int) cameraSize.width,
				(int) cameraSize.height, "Canny Edge Detector");
		
		start();
	}

	public void run() {

		while (true) {

			Mat source = camera.getMat();

			Imgproc.GaussianBlur(source, source, new Size(5, 5), 0);

			Mat edges = new Mat(source.rows(), source.cols(), source.type(),
					new Scalar(0));

			Imgproc.Canny(source, edges, lowThreshold, lowThreshold * ratio);

			Graphics g = panel.getGraphics();

			BufferedImage image = ImageMatConvert.matToImage(edges);

			g.drawImage(image, 0, 0, null);

			try {
				Thread.sleep(1000 / frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
