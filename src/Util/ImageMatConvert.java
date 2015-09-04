package Util;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageMatConvert {

	public static Mat convert(BufferedImage img) {
		Mat m = new Mat(img.getHeight(), img.getWidth(), img.getType(),
				new Scalar(150, 150));
		System.out.println(m.channels());

		for (int row = 0; row < m.rows(); row++) {
			for (int col = 0; col < m.cols(); col++) {
				m.put(row, col, img.getRGB(col, row));
			}
		}
		return m;
	}
	
	public static Mat BufferedImageToMatPixels(BufferedImage image) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		Mat m = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC(image.getColorModel().getNumComponents()) );
		m.put(0, 0, pixels);
		return m;
	}
	
	public static Mat readImage(String name) {
        URL url = name.getClass().getResource(name);

        // make sure the file exists
        if (url == null) {
            System.out.println("ResourceNotFound: " + name);
            return new Mat();
        }

        String path = url.getPath();

        // not sure why we (sometimes; while running unpacked from the IDE) end 
        // up with the authority-part of the path (a single slash) as prefix,
        // ...anyways: Highgui.imread can't handle it, so that's why.
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        Mat image = Imgcodecs.imread(path);

        return image;
    }
	
	public static Mat removeAlpha(Mat mat, int backgroundOverrideCutoff) {
		
		Mat m = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC(mat.channels()-1));
		
		for (int row = 0; row < mat.rows(); row++) {
			for (int col = 0; col < mat.cols(); col++) {
				
				double[] original = mat.get(row, col);
				double[] alphaLess = new double[mat.channels()-1];
				
				for (int channel = 1; channel < mat.channels(); channel++) {
					alphaLess[channel-1] = original[channel];
				}
				if (original[0] < backgroundOverrideCutoff) { // check for transparent background
					alphaLess = new double[] {255, 255, 255}; // make backgrounds white
				}
				m.put(row, col, alphaLess);
			}
		}
		return m;
	}
	
	public static BufferedImage matToImage(Mat image) {

		MatOfByte bytemat = new MatOfByte();

		Imgcodecs.imencode(".jpg", image, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
}
