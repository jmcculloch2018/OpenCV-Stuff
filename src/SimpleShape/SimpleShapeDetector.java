package SimpleShape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;
import Util.Webcam;

public class SimpleShapeDetector extends Thread {

	JFrame frame;
	JPanel panel;
	Webcam camera;

	Size frameSize;

	final int THRESHOLD = 100; // Lower number = detects black/dark objects
								// better
	// Higher number = detects white/lighter objects better

	boolean binaryFeed = false; // False = color image displayed, True = binary
								// image displayed (good adjusting Threshold)

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(new SimpleShapeDetector());
		new SimpleShapeDetector();
	}

	public SimpleShapeDetector() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		frame = new JFrame("Shapes!");

		panel = new JPanel(true);

		frame.add(panel);

		frame.setPreferredSize(new Dimension(850, 650));

		frame.pack();

		panel.setVisible(true);

		frame.setVisible(true);

		camera = new Webcam();

		frameSize = camera.getImageRes();

		start();
	}

	public void run() {

		Graphics panelG = panel.getGraphics();

		while (true) {

			Mat src = camera.getMat();

			Mat grayscale = new Mat();

			Imgproc.cvtColor(src, grayscale, Imgproc.COLOR_BGR2GRAY);

			Imgproc.threshold(grayscale, grayscale, THRESHOLD, 255,
					Imgproc.THRESH_BINARY);

			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

			Imgproc.findContours(grayscale.clone(), contours, new Mat(),
					Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

			MatOfPoint2f approxCurve = new MatOfPoint2f();

			ArrayList<Rect> rects = new ArrayList<Rect>();

			for (int i = 0; i < contours.size(); i++) {

				MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i)
						.toArray());

				double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
				Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance,
						true);

				MatOfPoint points = new MatOfPoint(approxCurve.toArray());

				// if (points.cols() == 4
				// && Math.abs(Imgproc.contourArea(new MatOfPoint(points))) >
				// 100
				// && Imgproc.isContourConvex(new MatOfPoint(points))) {

				Rect rect = Imgproc.boundingRect(points);
				if (rect.height > 50 && rect.width > 50
						&& rect.height < frameSize.height / 2
						&& rect.width < frameSize.width / 2) {

					Rect rec = new Rect(rect.x, rect.y, rect.width, rect.height);

					rects.add(rec);
				}
				// }
			}

			BufferedImage sourceImage;

			if (binaryFeed) {

				sourceImage = ImageMatConvert.matToImage(grayscale);

			} else {
				sourceImage = ImageMatConvert.matToImage(src);
			}
			
			Graphics imgGraphics = sourceImage.getGraphics();

			imgGraphics.setColor(Color.RED);

			for (Rect r : rects) {
				imgGraphics.drawRect(r.x, r.y, r.width, r.height);
			}
			
			panelG.drawImage(sourceImage, 0, 0, null);

//			panel.repaint();
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
