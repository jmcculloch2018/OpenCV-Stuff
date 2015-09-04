package FaceTrack;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class FeedManager extends Thread {

	int frameRate = 60, trackRate = 30;

	double sensitivity = 1.15;

	String mode = "haarcascades/haarcascade_frontalface_alt.xml";

	Webcam camera;

	FaceTrackPanel panel;

	TrackingManager trackingManager;

	long time = 0;

	int saveCount = 0;

	boolean trackingThreadReady;

	final boolean USE_X_FOR_ROTATE = false;

	Rect targetPoint;

	ArrayList<TrackedFace> trackedFaces;

	public static void main(String[] args) {
		new FeedManager();
	}

	public FeedManager() {
		time = System.nanoTime();

		camera = new Webcam();

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		trackedFaces = new ArrayList<TrackedFace>();

		Size cameraSize = camera.getImageSize();

		trackingManager = new TrackingManager(mode, camera, sensitivity,
				trackRate);

		trackingThreadReady = false;

		trackingManager.start();

		camera.changeRes(new Size(camera.getImageRes().width / 1.5, camera
				.getImageRes().height / 1.5));

		System.out.println(camera.getImageRes());

		targetPoint = new Rect((int) cameraSize.width / 2,
				(int) cameraSize.height / 2, 25, 25);

		panel = new FaceTrackPanel((int) (cameraSize.width * 1.2),
				(int) (cameraSize.height * 1.2), mode);

		while (!trackingManager.hasBeenConstructed) {
			try {
				System.out
						.println("Waiting for Tracking Manager Finish Construction...");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Mouse m = new Mouse(this);

		panel.addMouseListener(m);

		panel.addMouseMotionListener(m);

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

				for (TrackedFace cur : trackedFaces) {
					cur.prepareForMatching();
				}

				for (Rect cur : locations) {
					TrackedFace match = findMatchingFace(cur);

					if (match == null) {
						trackedFaces.add(new TrackedFace(cur.x, cur.y,
								cur.width, cur.height));
					}
				}

				for (int pos = 0; pos < trackedFaces.size(); pos++) {
					TrackedFace cur = trackedFaces.get(pos);
					if (!cur.hasBeenMatchedThisFrame()) {
						if (cur.disposeYet()) {
							trackedFaces.remove(cur);
							pos--;
						} else {
							// cur.attemptToFollow();
						}
					}
				}

				for (TrackedFace cur : trackedFaces) {
					cur.display(feed);

					if (cur.isSelected()) {
						centerFace(cur);
					}
				}

				Imgproc.line(feed, new Point(targetPoint.x
						- (targetPoint.width / 2), targetPoint.y),
						new Point(targetPoint.x + (targetPoint.width / 2),
								targetPoint.y), new Scalar(255, 0, 0));

				Imgproc.line(feed, new Point(targetPoint.x, targetPoint.y
						- (targetPoint.height / 2)), new Point(targetPoint.x,
						targetPoint.y + (targetPoint.height / 2)), new Scalar(
						255, 0, 0));

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

	private void centerFace(TrackedFace target) {
		int centerX = (int) (target.getLocation().x + target.getSize().width / 2);
		int centerY = (int) (target.getLocation().y + target.getSize().height / 2);

		Size cameraSize = camera.getImageSize();

		Point centerPoint = new Point(targetPoint.x, targetPoint.y);

		double forwardError = 0.0, rightError = 0.0, clockwiseError = 0.0;

		forwardError = (centerY - centerPoint.y) / -(cameraSize.height / 2);

		if (Math.abs(forwardError) > 1) {
			forwardError = forwardError / Math.abs(forwardError);
		}

		if (USE_X_FOR_ROTATE) {
			clockwiseError = (centerX - centerPoint.x) / (cameraSize.width / 2);

			if (Math.abs(clockwiseError) > 1) {
				clockwiseError = clockwiseError / Math.abs(clockwiseError);
			}
		} else {
			rightError = (centerX - centerPoint.x) / (cameraSize.width / 2);

			if (Math.abs(rightError) > 1) {
				rightError = rightError / Math.abs(rightError);
			}
		}

		System.out.println("Forward: " + forwardError + " Right: " + rightError
				+ " Clockwise: " + clockwiseError);
	}

	private TrackedFace findMatchingFace(Rect face) {
		for (TrackedFace cur : trackedFaces) {
			if (cur.isMatch(face)) {
				cur.match(face);
				return cur;
			}
		}
		return null;
	}
}
