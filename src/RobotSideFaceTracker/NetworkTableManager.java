package RobotSideFaceTracker;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NetworkTableManager implements ITableListener {
	
	private double[] latestErrors;
	
	private NetworkTable netTable;
	
	public NetworkTableManager() {
		netTable = NetworkTable.getTable("_-xXx$#@TW0L361F0URQU17@#$xXx-_"); //TODO: set a real name
		
		netTable.addTableListener(this);
	}
	
	public void valueChanged(ITable table, String string, Object recieved, boolean hasChanged) {
		
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
}

