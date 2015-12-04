package CircleDetection;

import java.awt.Color;

import org.opencv.core.Mat;

public class Circle {

	private int x, y, radius;

	private static Mat latestFeed;
	
	private final double BRIGHTNESS_THRESHOLD = .8, SATURATION_THRESHOLD = .6;

	public static void updateFeed(Mat feed) {
		latestFeed = feed;
	}

	public Circle(int centerX, int centerY, int radius) {
		this.x = centerX;
		this.y = centerY;
		this.radius = radius;
	}

	public double getAvgColor(int cycles) {

		double hueSum = 0;

		int numOfPixels = 0;

		for (int i = 0; i < cycles; i++) {

			int[] ranPoint = getRandomPointInside();
			
			int testX = ranPoint[0];
			int testY = ranPoint[1];

			if (testX < 0) {
				testX = 0;
			}

			if (testY < 0) {
				testY = 0;
			}

			if (testX >= latestFeed.cols()) {
				testX = latestFeed.cols() - 1;
			}

			if (testY >= latestFeed.rows()) {
				testY = latestFeed.rows() - 1;
			}

			double[] rgbAtPoint = latestFeed.get(testY, testX);
			
			float[] hsbAtPoint = new float[3];
			
			Color.RGBtoHSB((int) rgbAtPoint[0], (int) rgbAtPoint[1], (int) rgbAtPoint[2],
					hsbAtPoint);
			
			double hueAtLoc = hsbAtPoint[0];
			
//			System.out.println("HSB: " + hsbAtPoint[0] + ", " + hsbAtPoint[1] + ", " + hsbAtPoint[2]);

			if (!isIndiscriminant(hsbAtPoint)) {
				hueSum += hueAtLoc;
				numOfPixels++;
			}
		}

		if (numOfPixels > cycles / 10) {
			return hueSum / numOfPixels;
		} else {
			return -1;
		}
	}

	private boolean isIndiscriminant(float[] hsbValue) {

		//System.out.println("Brightness: " + brightness);

		if (hsbValue[1] < SATURATION_THRESHOLD || hsbValue[2] < BRIGHTNESS_THRESHOLD) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}
	
	private int[] getRandomPointInside() {
		double ranAngle = Math.toRadians(Math.random() * 360);
		
		double ranRadius = (int) (Math.random() * radius);
		
		int ranX = (int) (Math.cos(ranAngle) * ranRadius) + (x + radius);
		int ranY = (int) (Math.sin(ranAngle) * ranRadius) + (y + radius);
		
		return new int[] {ranX, ranY};
	}
}
