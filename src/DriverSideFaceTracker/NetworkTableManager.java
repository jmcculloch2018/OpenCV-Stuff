package DriverSideFaceTracker;

import org.opencv.core.Mat;
import org.opencv.core.Size;

public class NetworkTableManager implements ITableListener {
	
	private Mat latestImage;
	
	private boolean isNew;
	
	private Size imageSize;
	
	public void valueChanged(ITable table, String string, Object recieved, boolean hasChanged) {
		if (hasChanged) {
			latestImage = (Mat) recieved;
			
			if (imageSize == null) {
				imageSize = latestImage.size();
			}
			
			isNew = true;
		}
	}
	
	public boolean hasNewImage() {
		return isNew;
	}
	
	public Mat getLatestImage() {
		isNew = false;
		return latestImage;
	}
	
	public Size getImageSize() {
		return imageSize;
	}

	public void write(double forwardError, double rightError,
			double clockwiseError) {
		//TODO: write to table
		
	}
}
