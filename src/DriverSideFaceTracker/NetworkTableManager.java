package DriverSideFaceTracker;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NetworkTableManager implements ITableListener {

	private Mat latestImage;

	private boolean isNew;

	private Size imageSize;

	private NetworkTable netTable;

	public NetworkTableManager() {
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("00.000.0"); // TODO: Set IP
		netTable = NetworkTable.getTable("_-xXx$#@TW0L361F0URQU17@#$xXx-_");
		// TODO: set a real name

		netTable.addTableListener(this);
	}

	public void valueChanged(ITable table, String string, Object recieved,
			boolean hasChanged) {

		if (hasChanged && string.equals("camera-feed")) {

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

		netTable.putValue("error-values", new double[] { forwardError,
				rightError, clockwiseError });

	}
}
