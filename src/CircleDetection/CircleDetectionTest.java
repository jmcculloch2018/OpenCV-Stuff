package CircleDetection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class CircleDetectionTest extends Thread {

	public static Webcam camera;

	CircleDetectionPanel panel;

	public static Mat source;

	public static void main(String[] args) {
		new CircleDetectionTest();
	}

	public CircleDetectionTest() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new CircleDetectionPanel((int) cameraSize.width,
				(int) cameraSize.height, "Circle Detector");

		DetectionThread GCTricker = new DetectionThread();

		GCTricker.start();

		start();
	}

	public void run() {

		while (true) {

			source = camera.getMat();

			BufferedImage sourceImg = ImageMatConvert.matToImage(source);

			Graphics imageG = sourceImg.getGraphics();

			Graphics g = panel.getGraphics();

			g.setColor(Color.GREEN);

			double[] circle = DetectionThread.getBestCircle();

			if (circle != null) {

				imageG.drawOval((int) circle[0] - (int) circle[2],
						(int) circle[1] - (int) circle[2], (int) circle[2] * 2,
						(int) circle[2] * 2);
			}

			g.drawImage(sourceImg, 0, 0, null);

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
