package OCR;

import net.sourceforge.javaocr.scanner.DocumentScanner;
import net.sourceforge.javaocr.scanner.DocumentScannerListenerAdaptor;
import net.sourceforge.javaocr.scanner.PixelImage;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainingImageLoader extends DocumentScannerListenerAdaptor {

	private int charValue = 0;
	private HashMap<Character, ArrayList<TrainingImage>> dest;
	private DocumentScanner documentScanner = new DocumentScanner();

	public DocumentScanner getDocumentScanner() {
		return documentScanner;
	}

	/**
	 * Load an image containing training characters, break it up into
	 * characters, and build a training set. Each entry in the training set (a
	 * <code>Map</code>) has a key which is a <code>Character</code> object
	 * whose value is the character code. Each corresponding value in the
	 * <code>Map</code> is an <code>ArrayList</code> of one or more
	 * <code>TrainingImage</code> objects which contain images of the character
	 * represented in the key.
	 * 
	 * @param imageFilename
	 *            The filename of the image to load.
	 * @param charRange
	 *            A <code>CharacterRange</code> object representing the range of
	 *            characters which is contained in this image.
	 * @param dest
	 *            A <code>Map</code> which gets loaded with the training data.
	 *            Multiple calls to this method may be made with the same
	 *            <code>Map</code> to populate it with the data from several
	 *            training images.
	 * @throws IOException
	 */
	public void load(String imageFilename, CharacterRange charRange,
			HashMap<Character, ArrayList<TrainingImage>> dest)
			throws IOException {

		Image image = ImageIO.read(new File(imageFilename));

		if (image == null) {
			throw new IOException("Cannot find training image file: "
					+ imageFilename);
		}
		load(image, charRange, dest, imageFilename);
	}

	public void load(Image image, CharacterRange charRange,
			HashMap<Character, ArrayList<TrainingImage>> dest,
			String imageFilename) throws IOException {

		PixelImage pixelImage = new PixelImage(image);
		pixelImage.toGrayScale(true);
		pixelImage.filter();
		charValue = charRange.min;
		this.dest = dest;
		documentScanner.scan(pixelImage, this, 0, 0, 0, 0);
		if (charValue != (charRange.max + 1)) {
			throw new IOException("Expected to decode "
					+ ((charRange.max + 1) - charRange.min)
					+ " characters but actually decoded "
					+ (charValue - charRange.min) + " characters in training: "
					+ imageFilename);
		}
	}

	@Override
	public void processChar(PixelImage pixelImage, int x1, int y1, int x2,
			int y2, int rowY1, int rowY2) {
		int w = x2 - x1;
		int h = y2 - y1;
		int[] pixels = new int[w * h];
		for (int y = y1, destY = 0; y < y2; y++, destY++) {
			System.arraycopy(pixelImage.pixels, (y * pixelImage.width) + x1,
					pixels, destY * w, w);
		}
		Character chr = (char) charValue;
		ArrayList<TrainingImage> al = dest.get(chr);
		if (al == null) {
			al = new ArrayList<TrainingImage>();
			dest.put(chr, al);
		}
		al.add(new TrainingImage(pixels, w, h, y1 - rowY1, rowY2 - y2));
		charValue++;
	}
}
