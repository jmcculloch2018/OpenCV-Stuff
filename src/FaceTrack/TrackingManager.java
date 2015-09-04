package FaceTrack;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import Util.Webcam;

public class TrackingManager extends Thread {

	Rect[] locations;

	Webcam camera;

	CascadeClassifier objectDetector;

	double sensitivity;

	int sleepTime;

	boolean newLocations;

	boolean hasBeenConstructed;

	public TrackingManager(String mode, Webcam camera, double sensitivity,
			int trackRate) {

		System.out.println("Setting Up Tracking Manager");

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String path = getClass().getResource("/" + mode).toString().substring(6);
		
		while (path.indexOf("%20") != -1) {
			path = path.substring(0, path.indexOf("%20")) + " " + path.substring(path.indexOf("%20")+3);
		}

		System.out.println(path);

		objectDetector = new CascadeClassifier(path);

		this.sensitivity = sensitivity;

		this.camera = camera;

		newLocations = false;

		sleepTime = 1000 / trackRate;

		hasBeenConstructed = true;

		System.out.println("Tracking Manager Started");
	}

	public void run() {

		while (true) {

			Mat image = camera.getMat();

			MatOfRect faceDetections = new MatOfRect();
			objectDetector.detectMultiScale(image, faceDetections,
					1 * sensitivity, 4, 0, new Size(10, 10), new Size(9999,
							9999));

			locations = faceDetections.toArray();

			newLocations = true;

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasNewLocations() {
		return newLocations;
	}

	public boolean hasBeenConstructed() {
		return hasBeenConstructed;
	}

	public Rect[] getLocations() {
		return locations;
	}
}