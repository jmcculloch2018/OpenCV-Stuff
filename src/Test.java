import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.opencv.core.Core;

@SuppressWarnings("serial")
class Test extends JPanel implements Runnable {

	static BufferedImage img;

	static BufferedImage[][] image2D;

	static BufferedImage[][] curImage;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Test());
	}


	public void run() {

		try {

			img = ImageIO.read(new File(
					"C:/Program Files (x86)/eclipse/OpenCV/data/dog.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Stuff");

		frame.add(this);

		frame.setVisible(true);
	}
	
	private void setUpShuffle() {

		image2D = new BufferedImage[20][30];
		curImage = new BufferedImage[20][30];

		for (int row = 0; row < image2D.length; row++) {
			for (int col = 0; col < image2D[row].length; col++) {
				image2D[row][col] = getImagePart(col * 20, row * 20, 20, 20);
			}
		}

		new DrawThingy(this).start();
	}

	private void shuffleImage() {

		for (int count = 0; count < 20 * 30; count++) {

			int takeRow = (int) (Math.random() * 20), takeCol = (int) (Math
					.random() * 30);

			while (image2D[takeRow][takeCol] == null) {
				takeRow = (int) (Math.random() * 20);
				takeCol = (int) (Math.random() * 30);
			}
			int giveRow = (int) (Math.random() * 20), giveCol = (int) (Math
					.random() * 30);

			while (curImage[giveRow][giveCol] != null) {
				giveRow = (int) (Math.random() * 20);
				giveCol = (int) (Math.random() * 30);
			}
			
			curImage[giveRow][giveCol] = image2D[takeRow][takeCol];
			image2D[takeRow][takeCol] = null;
		}

	}
	
	private void renderImagePieces(Graphics g) {
		for (int row = 0; row < curImage.length; row++) {
			for (int col = 0; col < curImage[row].length; col++) {
				g.drawImage(curImage[row][col], col*20, row*20, null);
			}
		}
	}

	private BufferedImage getImagePart(int x, int y, int w, int h) {

		return img.getSubimage(x, y, w, h);
	}

	public void paintComponent(Graphics g) {

		setUpShuffle();
		shuffleImage();
		renderImagePieces(g);

	}
}