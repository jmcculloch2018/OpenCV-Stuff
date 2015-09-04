package Histogram;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import Util.ImageMatConvert;

public class myHistogram {

	int numBins;
	int numChannels;

	int[][] histogram;

	Mat imageMat;

	public myHistogram(BufferedImage image, int bins) {
		numBins = bins;
		numChannels = image.getColorModel().getNumComponents();

		histogram = new int[numChannels][numBins];

		imageMat = ImageMatConvert.BufferedImageToMatPixels(image);
	}

	public myHistogram(BufferedImage image, int bins, int alphaCutoff) {
		this(image, bins);

		imageMat = ImageMatConvert.removeAlpha(imageMat, alphaCutoff);
	}
	
	public myHistogram(Mat imageMat, int bins) {
		numBins = bins;
		numChannels = imageMat.get(0, 0).length;
		
		histogram = new int[numChannels][numBins];
		
		this.imageMat = imageMat;
	}

	public void calculateHistogram() {

		for (int row = 0; row < imageMat.rows(); row++) {
			for (int col = 0; col < imageMat.cols(); col++) {
				for (int channel = 0; channel < numChannels; channel++) {
					int value = (int) Math
							.round(imageMat.get(row, col)[channel]);

					histogram[channel][value]++;
				}
			}
		}
	}

	public int[][] getHistogram() {
		return histogram;
	}
}
