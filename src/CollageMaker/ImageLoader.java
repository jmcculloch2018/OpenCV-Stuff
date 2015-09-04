package CollageMaker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	File[] listOfFiles;

	BufferedImage[] images;

	BufferedImage template;

	public ImageLoader(File folder) {
		listOfFiles = folder.listFiles();
		images = new BufferedImage[listOfFiles.length-1];

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile()) {
				try {
					images[i] = ImageIO.read(file);
					System.out.println(file.getName() + " loaded");
				} catch (IOException e) {
					System.out
							.println("FAILED TO LOAD FILE: " + file.getName());
					e.printStackTrace();
				}
			} else {
				try {
					template = ImageIO.read(file.listFiles()[0]);
					System.out.println("Template loaded");
				} catch (IOException e) {
					System.out.println("FAILED TO LOAD TEMPLATE");
					e.printStackTrace();
				}
			}
		}
	}

	 public BufferedImage getTemplateImage() {
		 return template;
	 }

	public BufferedImage[] getImages() {
		return images;
	}
}
