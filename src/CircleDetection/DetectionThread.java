package CircleDetection;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;

public class DetectionThread extends Thread {

	private CameraFeedThread feedThread;

	private Mat circles;

	private Circle bestCircle;

	private Circle[] circlesFound;

	final private double MAX_SCORE = .4;

	public DetectionThread(CameraFeedThread feedThread) {
		this.feedThread = feedThread;
	}

	public void run() {

		circles = new Mat();

		while (true) {

			Mat source = feedThread.getLatestFeed();

			if (source != null) {

				Mat grayscale = new Mat(source.rows(), source.cols(),
						CvType.CV_8UC1);

				Imgproc.cvtColor(source, grayscale, Imgproc.COLOR_RGB2GRAY);

				Imgproc.HoughCircles(grayscale, circles,
						Imgproc.CV_HOUGH_GRADIENT, 25, 1, 300, 2, 5, 250);

				Circle.updateFeed(source);

				circlesFound = new Circle[circles.cols()];

				for (int i = 0; i < circles.cols(); i++) {

					double[] curCircle = circles.get(0, i);

					circlesFound[i] = new Circle((int) curCircle[0],
							(int) curCircle[1], (int) curCircle[2]);

				}

				if (circlesFound.length != 0) {
					bestCircle = findBestMatch(circlesFound, Color.YELLOW, 500);
				} else {
					bestCircle = null;
				}

			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public Circle getBestCircle() {
		return bestCircle;
	}

	private Circle findBestMatch(Circle[] circles, Color c, int cycles) {

		float[] HSBValues = new float[3];

		Color.RGBtoHSB((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue(),
				HSBValues);

		System.out.println("Goal: " + HSBValues[0] + ", " + HSBValues[1] + ", "
				+ HSBValues[2]);

		double targetHue = HSBValues[0];

		double bestScore = colorDifference(circles[0].getAvgColor(cycles),
				targetHue);
		Circle bestCircle = circles[0];

		for (int i = 1; i < circles.length; i++) {
			double curScore = colorDifference(circles[i].getAvgColor(cycles),
					targetHue);

			if (curScore < bestScore) {
				bestScore = curScore;
				bestCircle = circles[i];

			}
		}

		System.out.println("Best score: " + bestScore);

		if (bestScore < MAX_SCORE) {
			return bestCircle;
		} else {
			return null;
		}
	}

	private double colorDifference(double hue, double targetHue) {

		System.out.println("Hue: " + hue);

		if (hue < 0) {
			return Integer.MAX_VALUE;
		}

		System.out.println("Targ Hue: " + targetHue);

		return Math.abs(hue - targetHue);
	}

	public Circle[] getLatestCircles() {
		return circlesFound;
	}
}
