package CollageMaker;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class CollageCoordinator {

	BufferedImage[] images;
	BufferedImage template;

	public CollageCoordinator(BufferedImage[] images, BufferedImage template) {
		this.images = images;
		this.template = template;

		resizeImages();
	}

	public void resizeImages() {
		for (int i = 0; i < images.length; i++) {
			Image temp = images[i].getScaledInstance(100, 100,
					BufferedImage.SCALE_DEFAULT);
			
			BufferedImage load = new BufferedImage(100, 100, images[i].getType());
			
			Graphics2D bGr = load.createGraphics();
			bGr.drawImage(temp, 0, 0, null);
			bGr.dispose();
			
			images[i] = load;

			System.out.println(images[i].getWidth() + " "
					+ images[i].getHeight());
		}
	}
}
