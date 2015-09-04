package FaceDetect;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class FeedManager extends Thread {

	int frameRate = 120, trackRate = 60;

	double sensitivity = 1.2;

	String mode = "haarcascades/haarcascade_frontalcatface_extended.xml";

	Webcam camera;

	FaceDetectPanel panel;

	TrackingManager trackingManager;

	long time = 0;

	int saveCount = 0;

	boolean trackingThreadReady;

	public static void main(String[] args) {
		new FeedManager();
	}

	public FeedManager() {
		time = System.nanoTime();

		camera = new Webcam();

		Size cameraSize = camera.getImageRes();

		panel = new FaceDetectPanel((int) cameraSize.width,
				(int) cameraSize.height, mode);

		trackingManager = new TrackingManager(mode, camera, sensitivity,
				trackRate);

		trackingThreadReady = false;

		trackingManager.start();

		while (!trackingManager.hasBeenConstructed) {
			try {
				System.out
						.println("Waiting for Tracking Manager Finish Construction...");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Got Responce From Tracking Manager");

		System.out.println("Feed Manager Ready");

		start();
	}

	public void run() {

		System.out.println("Starting Feed Manager");

		while (true) {

			Mat feed = camera.getMat();

			if (trackingManager.hasNewLocations()) {

				Rect[] locations = trackingManager.getLocations();

				for (Rect rect : locations) {
					Imgproc.rectangle(
							feed,
							new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(0, 255, 0));
				}

				if (!trackingThreadReady) {
					System.out.println("Got Responce From Tracking Thread");
					time = System.nanoTime() - time;
					System.out.println("\n***Setup Done!*** Setup Time: "
							+ ((double) time / 1000000000) + "sec");
				}

				trackingThreadReady = true;

			} else {
				System.out.println("Waiting For Tracking Thread...");
			}

			BufferedImage bufferedImage = ImageMatConvert.matToImage(feed);

			Graphics g = panel.getGraphics();

			g.drawImage(bufferedImage, 0, 0, null);

			try {
				Thread.sleep(1000 / frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
