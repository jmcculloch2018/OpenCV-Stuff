package OCR;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;
import net.sourceforge.javaocr.scanner.PixelImage;

import org.opencv.core.Mat;

import Util.ImageMatConvert;
import Util.Webcam;

public class OCRScannerDemo {

	private OCRScanner scanner;
	Webcam camera;
	WebcamFeedThread webcamFeedThread;
	OCRPanel panel;

	public OCRScannerDemo() {
		scanner = new OCRScanner();

		HashMap<Character, ArrayList<TrainingImage>> dest = new HashMap<Character, ArrayList<TrainingImage>>();

		TrainingImageLoader trainerLoader = new TrainingImageLoader();

		//CharacterRange charRange = new CharacterRange(33, 126);
		
		CharacterRange charRange = new CharacterRange(65, 90);

		try {
			trainerLoader.load("C:/Users/Anyone/Desktop/imageAZ.jpg",
					charRange, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(dest.size());

		scanner.addTrainingImages(dest);

		camera = new Webcam();
		panel = new OCRPanel(this);
		webcamFeedThread = new WebcamFeedThread(this);
		webcamFeedThread.start();
	}

	public void update() {
//		Mat cameraFeed = camera.getMat();
//
//		Image image = ImageMatConvert.matToImage(cameraFeed);
		
		Image image = null;
		try {
			image = ImageIO.read(new File("C:/Users/Anyone/Desktop/testDoc4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		process(image);
	}

	public void process(Image image) {

		PixelImage pixelImage = new PixelImage(image);

		pixelImage.toGrayScale(true);
		pixelImage.filter();

		String text = scanner.scan(image, 0, 0, 0, 0, null);
		
		System.out.println("******************");
		
		System.out.println(text);

		Graphics g = panel.getGraphics();

		g.drawImage(image, 0, 0, null);
	}

	public static void main(String[] args) {
		new OCRScannerDemo();
	}
}