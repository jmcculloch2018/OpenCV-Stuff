package RobotSideFaceTracker;

import org.usfirst.frc.team2485.auto.SequencedItem;
import org.usfirst.frc.team2485.robot.Robot;

import Util.Webcam;

public class FaceTrack implements SequencedItem {

	private final double duration;
	private final NetworkTableManager networkTableManager;
	private final Webcam camera;

	public FaceTrack(double duration) {
		this.duration = duration;
		networkTableManager = new NetworkTableManager();
		camera = new Webcam();
	}

	@Override
	public void run() {

		if (networkTableManager.establishedConnection()) {

			networkTableManager.write(camera.getMat());

			double rightError = 0, forwardError = 0, clockwiseError = 0;

			double[] latestErrors = networkTableManager.getLatestErrors();

			rightError = latestErrors[0];
			forwardError = latestErrors[1];
			clockwiseError = latestErrors[2];

			Robot.drive.warlordDrive(rightError, forwardError, clockwiseError);

		} else {

			// TODO: indicate waiting status, if you want...
		}

	}

	@Override
	public double duration() {
		return duration;
	}

	@Override
	public void finish() {
		Robot.drive.warlordDrive(0, 0, 0);
	}

}