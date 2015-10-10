package OCR;

public class WebcamFeedThread extends Thread {
	
	private OCRScannerDemo ocrScanner;
	
	public WebcamFeedThread(OCRScannerDemo demo) {
		ocrScanner = demo;
	}
	
	public void run() {
		while (true) {
			
			ocrScanner.update();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
