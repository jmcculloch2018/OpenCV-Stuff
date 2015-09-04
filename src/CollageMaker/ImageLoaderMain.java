package CollageMaker;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ImageLoaderMain {

	public static void main(String[] args) {

		new ImageLoaderMain();

	}
	
	public ImageLoaderMain() {
		
		URL sourceURL = getClass().getResource("/CollageInput");
		System.out.println(sourceURL);
		URI sourceURI = null;
		try {
			sourceURI = sourceURL.toURI();
		} catch (URISyntaxException e) {
			System.out.println("FAILED TO FIND FILE");
			e.printStackTrace();
		}
		
		File sourceFile = new File(sourceURI);
		
		ImageLoader loader = new ImageLoader(sourceFile);
		
		new CollageCoordinator(loader.getImages(), loader.getTemplateImage());
	}
}
