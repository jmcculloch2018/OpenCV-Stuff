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

public class CameraFeedThread extends Thread {

	private Webcam camera;

	private CircleDetectionPanel panel;

	private Mat source;

	private DetectionThread detectionThread;

	public static void main(String[] args) {
		new CameraFeedThread();
	}

	public CameraFeedThread() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new CircleDetectionPanel((int) cameraSize.width,
				(int) cameraSize.height, "Circle Detector");

		detectionThread = new DetectionThread(this);

		detectionThread.start();

		start();
	}

	public void run() {

		while (true) {

			source = camera.getMat();

			BufferedImage sourceImg = ImageMatConvert.matToImage(source);

			Graphics imageG = sourceImg.getGraphics();

			imageG.setColor(Color.GRAY);

			if (detectionThread.getLatestCircles() != null) {

				for (Circle circle : detectionThread.getLatestCircles()) {
					imageG.drawOval(circle.getX() - circle.getRadius(),
							circle.getY() - circle.getRadius(),
							circle.getRadius() * 2, circle.getRadius() * 2);
				}

				imageG.setColor(Color.GREEN);

				Circle circle = detectionThread.getBestCircle();

				if (circle != null) {

					imageG.drawOval(circle.getX() - circle.getRadius(),
							circle.getY() - circle.getRadius(),
							circle.getRadius() * 2, circle.getRadius() * 2);
				}
			}

			Graphics g = panel.getGraphics();

			g.drawImage(sourceImg, 0, 0, null);

			System.out.println("draw");

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Mat getLatestFeed() {
		return source;
	}
}
