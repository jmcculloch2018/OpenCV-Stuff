package CircleDetection;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.scene.shape.Circle;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;

public class DetectionThread extends Thread {

	public static Mat circles;

	private static double[] bestCircle;

	public void run() {

		circles = new Mat();

		while (true) {

			Mat source = CircleDetectionTest.source;

			if (source != null) {

				Mat grayscale = new Mat(source.rows(), source.cols(),
						CvType.CV_8UC1);

				Imgproc.cvtColor(source, grayscale, Imgproc.COLOR_RGB2GRAY);

				Imgproc.HoughCircles(grayscale, circles,
						Imgproc.CV_HOUGH_GRADIENT, 1, 50, 300, 2, 5, 100);

				double[][] circlesFound = new double[circles.cols()][3];

				for (int i = 0; i < circlesFound.length; i++) {
					circlesFound[i] = circles.get(0, i);
				}

				bestCircle = findBestMatch(source, circlesFound, Color.YELLOW);

			}

			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public static double[] getBestCircle() {
		return bestCircle;
	}

	private double[] findBestMatch(Mat rawFeed, double[][] circles, Color c) {

		int cycle = 0;

		while (circles.length > 1) {
			circles = filterAtLevel(rawFeed, circles, cycle, c);
		}

		if (circles.length > 0) {
			return circles[0];
		} else {
			return null;
		}

	}

	private double[][] filterAtLevel(Mat rawFeed, double[][] circlesFound,
			int cycle, Color c) {

		ArrayList<double[]> passedCircles = new ArrayList<double[]>();

		for (int curCircle = 0; curCircle < circlesFound.length; curCircle++) {

			int centerX = (int) (circlesFound[curCircle][0]);
			int centerY = (int) (circlesFound[curCircle][1]);
			
			System.out.println("Circle " + curCircle + " being test at cycle " + cycle);

			for (int i = 0; i < cycle; i++) {

				double[] color = rawFeed.get(centerX + i, centerY);

				if (isMatch(color, c)) {
					passedCircles.add(circlesFound[curCircle]);
				}

			}
		}

		Object[] toCast = passedCircles.toArray();

		double[][] toReturn = new double[passedCircles.size()][3];

		for (int i = 0; i < toCast.length; i++) {
			toReturn[i] = (double[]) toCast[i];
		}

		return toReturn;
	}

	private boolean isMatch(double[] rgbValue, Color c) {

		int maxDifference = 50;

		double[] goalColor = new double[] { c.getRed(), c.getGreen(),
				c.getBlue() };

		double curDifference = 0;

		for (int i = 0; i < 3; i++) {
			curDifference += Math.abs(rgbValue[i] - goalColor[i]);

			if (curDifference > maxDifference) {
				return false;
			}
		}
		return true;
	}
}
