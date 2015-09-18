package RobotSideFaceTracker;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NetworkTableManager implements ITableListener {
	
	private final NetworkTable netTable;

	private double[] latestErrors;

	public NetworkTableManager() {
		netTable = NetworkTable.getTable("table");
		// TODO: set a real name

		netTable.addTableListener(this);
	}

	public void valueChanged(ITable table, String string, Object recieved,
			boolean hasChanged) {

		if (hasChanged && string.equals("error-values")) {

			latestErrors = (double[]) recieved;
		}
	}

	public void write(Mat image) {
		netTable.putValue("camera-feed", image);
	}

	public double[] getLatestErrors() {
		return latestErrors;
	}

	public boolean establishedConnection() {
		return netTable.getBoolean("driver-ready");
	}
}
