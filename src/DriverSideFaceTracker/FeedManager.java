package DriverSideFaceTracker;

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

	TrackingManager trackingManager;

	long time = 0;

	int saveCount = 0;

	final boolean USE_X_FOR_ROTATE = false;

	Rect targetPoint;

	ArrayList<TrackedFace> trackedFaces;

	public static void main(String[] args) {
		new FeedManager();
	}

	public FeedManager() {

		trackedFaces = new ArrayList<TrackedFace>();

		trackingManager = new TrackingManager(mode, sensitivity,
				trackRate);

		trackingManager.start();

		targetPoint = new Rect((int) (trackingManager.getImageSize().width/2),
				(int) (trackingManager.getImageSize().height/2), 25, 25);

		Mouse m = new Mouse(this);

		start();
	}

	public void run() {

		while (true) {

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
					
					//TODO: draw Rects on image

					if (cur.isSelected()) {
						centerFace(cur);
					}
				}
				
				/*
				 * 
				 * TODO: Draw "Center" point

				Imgproc.line(feed, new Point(targetPoint.x
						- (targetPoint.width / 2), targetPoint.y),
						new Point(targetPoint.x + (targetPoint.width / 2),
								targetPoint.y), new Scalar(255, 0, 0));

				Imgproc.line(feed, new Point(targetPoint.x, targetPoint.y
						- (targetPoint.height / 2)), new Point(targetPoint.x,
						targetPoint.y + (targetPoint.height / 2)), new Scalar(
						255, 0, 0)); 
						
						*/

			} else {
				System.out.println("Waiting For Tracking Thread...");
			}
			
			//TODO: convet Mat to image

			//BufferedImage bufferedImage = ImageMatConvert.matToImage(feed);
			
			//TODO: draw image

//			Graphics g = panel.getGraphics();
//
//			g.drawImage(bufferedImage, 0, 0, null);

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

		Size imageSize = trackingManager.getImageSize();

		Point centerPoint = new Point(targetPoint.x, targetPoint.y);

		double forwardError = 0.0, rightError = 0.0, clockwiseError = 0.0;

		forwardError = (centerY - centerPoint.y) / -(imageSize.height / 2);

		if (Math.abs(forwardError) > 1) {
			forwardError = forwardError / Math.abs(forwardError);
		}

		if (USE_X_FOR_ROTATE) {
			clockwiseError = (centerX - centerPoint.x) / (imageSize.width / 2);

			if (Math.abs(clockwiseError) > 1) {
				clockwiseError = clockwiseError / Math.abs(clockwiseError);
			}
		} else {
			rightError = (centerX - centerPoint.x) / (imageSize.width / 2);

			if (Math.abs(rightError) > 1) {
				rightError = rightError / Math.abs(rightError);
			}
		}
		
		trackingManager.writeToTable(forwardError, rightError, clockwiseError);

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
