import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import Util.ImageMatConvert;

public class GaussianFilterTest implements Runnable {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new GaussianFilterTest());
	}

	public void run() {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			// no .gifs
			BufferedImage source = ImageIO.read(getClass().getResource(
					"/miniImage.png"));

			System.out.println(source);

			// Mat sourceMat = ImageToMat.readImage(("/grayscale.jpg"));

			Mat sourceMat = ImageMatConvert.BufferedImageToMatPixels(source);
			
//			***************************************************
			sourceMat = ImageMatConvert.removeAlpha(sourceMat, 1);
//			***************************************************			

			// System.out.println(sourceMat.dump());

			Mat destination = new Mat(sourceMat.rows(), sourceMat.cols(),
					sourceMat.type(), new Scalar(255, 255, 255));
			
			System.out.println();

			Imgproc.GaussianBlur(sourceMat, destination, new Size(5, 5), 0);

			// 255 = Completely Opaque

			Imgcodecs.imwrite("GaussianResult.jpg", destination);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println("Done");
	}
}