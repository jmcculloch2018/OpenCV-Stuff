package DriverSideFaceTracker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;

public class FeedManager extends Thread {

	private final double frameRate = 60, trackRate = 30;

	private final double sensitivity = 1.15;

	private final String mode = "haarcascades/haarcascade_frontalface_alt.xml";

	private final TrackingManager trackingManager;

	private final boolean USE_X_FOR_ROTATE = false;

	private Rect targetPoint;

	private JPanel panel;

	private double sleepTime;

	private final ArrayList<TrackedFace> trackedFaces;

	public static void main(String[] args) {
		new FeedManager();
	}

	public FeedManager() {

		trackedFaces = new ArrayList<TrackedFace>();

		trackingManager = new TrackingManager(mode, sensitivity, trackRate);

		trackingManager.start();

		setTargetPoint(new Rect(
				(int) (trackingManager.getImageSize().width / 2),
				(int) (trackingManager.getImageSize().height / 2), 25, 25));

		sleepTime = 1000 / frameRate;

		setUpFrame();

		start();
	}

	private void setUpFrame() {

		JFrame frame = new JFrame();

		panel = new JPanel();

		panel.setSize((int) trackingManager.getImageSize().width,
				(int) trackingManager.getImageSize().height);

		frame.add(panel);

		Mouse m = new Mouse(this);

		panel.addMouseListener(m);
		panel.addMouseMotionListener(m);

		frame.pack();

		frame.setVisible(true);
	}

	public void run() {

		while (true) {

			long cycleTime = System.nanoTime();

			Mat feed = trackingManager.getLatestImage();

			if (trackingManager.hasNewLocations()) {

				matchFaces();

				removeMissingFaces();

				adjustToTarget(feed);

				drawCrosshair(feed);

			}

			writeToPanel(feed);

			cycleTime = System.nanoTime() - cycleTime;

			try {
				Thread.sleep((long) (sleepTime - ((double) cycleTime / 1000000)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void removeMissingFaces() {
		for (int pos = 0; pos < getTrackedFaces().size(); pos++) {
			TrackedFace cur = getTrackedFaces().get(pos);
			if (!cur.hasBeenMatchedThisFrame()) {
				if (cur.disposeYet()) {
					getTrackedFaces().remove(cur);
					pos--;
				} else {
					// cur.attemptToFollow();
				}
			}
		}
	}

	private void matchFaces() {
		for (TrackedFace cur : getTrackedFaces()) {
			cur.prepareForMatching();
		}

		Rect[] locations = trackingManager.getLocations();

		for (Rect cur : locations) {
			TrackedFace match = findMatchingFace(cur);

			if (match == null) {
				getTrackedFaces().add(
						new TrackedFace(cur.x, cur.y, cur.width, cur.height));
			}
		}
	}

	private void adjustToTarget(Mat feed) {
		boolean hasTarget = false;

		for (TrackedFace cur : getTrackedFaces()) {

			cur.display(feed);

			if (cur.isSelected()) {
				hasTarget = true;
				centerFace(cur);
			}
		}

		if (!hasTarget) {
			trackingManager.writeZeros();
		}
	}

	private void writeToPanel(Mat feed) {

		BufferedImage bufferedImage = ImageMatConvert.matToImage(feed);

		Graphics g = panel.getGraphics();

		g.drawImage(bufferedImage, 0, 0, null);

	}

	private void drawCrosshair(Mat drawToThis) {

		Imgproc.line(drawToThis, new Point(getTargetPoint().x
				- (getTargetPoint().width / 2), getTargetPoint().y), new Point(
				getTargetPoint().x + (getTargetPoint().width / 2),
				getTargetPoint().y), new Scalar(255, 0, 0));

		Imgproc.line(drawToThis, new Point(getTargetPoint().x,
				getTargetPoint().y - (getTargetPoint().height / 2)), new Point(
				getTargetPoint().x, getTargetPoint().y
						+ (getTargetPoint().height / 2)), new Scalar(255, 0, 0));
	}

	private void centerFace(TrackedFace target) {
		int centerX = (int) (target.getLocation().x + target.getSize().width / 2);
		int centerY = (int) (target.getLocation().y + target.getSize().height / 2);

		Size imageSize = trackingManager.getImageSize();

		Point centerPoint = new Point(getTargetPoint().x, getTargetPoint().y);

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
		for (TrackedFace cur : getTrackedFaces()) {
			if (cur.isMatch(face)) {
				cur.match(face);
				return cur;
			}
		}
		return null;
	}

	public ArrayList<TrackedFace> getTrackedFaces() {
		return trackedFaces;
	}

	public Rect getTargetPoint() {
		return targetPoint;
	}

	private void setTargetPoint(Rect targetPoint) {
		this.targetPoint = targetPoint;
	}
}
