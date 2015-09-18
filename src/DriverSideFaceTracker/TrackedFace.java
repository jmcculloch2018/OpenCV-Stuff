package DriverSideFaceTracker;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TrackedFace {

	private Point location;
	private Size size;

	private final ArrayList<Double> previousMovements;
	private final ArrayList<Size> previousSizeChanges;	
	private final ArrayList<Point> previousLocations;

	private boolean selected;
	private boolean matchedThisFrame;
	private int framesSinceMatch;
		
	private static final int disposeAfterFrames = 30;
	private static final int BUFFER_SIZE = 3;
	private static final double minMovement = 20;
	private static final Size minSizeChange = new Size(15, 15);
	private static final double TOLERANCE = 2.5;
	
	public TrackedFace(int x, int y, int width, int height) {
		this(new Point(x, y), new Size(width, height));
	}

	public TrackedFace(Point location, Size size) {
		this.location = location;

		this.size = size;

		previousMovements = new ArrayList<Double>(BUFFER_SIZE);
		previousSizeChanges = new ArrayList<Size>(BUFFER_SIZE);
		previousLocations = new ArrayList<Point>(BUFFER_SIZE);

		for (int count = 0; count < BUFFER_SIZE; count++) {
			previousMovements.add(minMovement);
			previousSizeChanges.add(minSizeChange);
			previousLocations.add(location);
		}

		matchedThisFrame = true;
	}

	public boolean isMatch(Rect other) {
		Point otherLocation = new Point(other.x, other.y);
		Size otherSize = new Size(other.width, other.height);

		return checkDistChange(otherLocation) && checkSizeChange(otherSize);
	}

	public void match(Rect other) {
		Point otherLocation = new Point(other.x, other.y);
		Size otherSize = new Size(other.width, other.height);

		double deltaLocation = getDeltaLocation(location, otherLocation);

		deltaLocation = Math.max(deltaLocation, minMovement);

		Size deltaSize = getDeltaSize(size, otherSize);

		deltaSize.width = Math.max(deltaSize.width, minSizeChange.width);
		deltaSize.height = Math.max(deltaSize.height, minSizeChange.height);

		previousMovements.add(deltaLocation);
		previousMovements.remove(0);

		previousSizeChanges.add(deltaSize);
		previousSizeChanges.remove(0);
		
		previousLocations.add(location);
		previousLocations.remove(0);
		
		location = otherLocation;
		size = otherSize;
		
		framesSinceMatch = 0;
		
		matchedThisFrame = true;
	}

	private boolean checkSizeChange(Size otherSize) {
		Size deltaSize = getDeltaSize(this.size, otherSize);

		Size avgChange = new Size(0, 0);

		for (Size cur : previousSizeChanges) {
			avgChange.width += cur.width;
			avgChange.height += cur.height;
		}

		avgChange.width /= previousSizeChanges.size();
		avgChange.height /= previousSizeChanges.size();

		return deltaSize.width <= avgChange.width * TOLERANCE
				&& deltaSize.height <= avgChange.height * TOLERANCE;
	}

	private Size getDeltaSize(Size size1, Size size2) {
		return new Size(Math.abs(size1.width - size2.width),
				Math.abs(size1.height - size2.height));
	}

	private boolean checkDistChange(Point otherLocation) {

		double deltaDist = getDeltaLocation(this.location, otherLocation);

		double avgChange = 0;

		for (double cur : previousMovements) {
			avgChange += cur;
		}

		avgChange /= previousMovements.size();

		return deltaDist <= avgChange * TOLERANCE;
	}

	private double getDeltaLocation(Point location1, Point location2) {
		double deltaX = location1.x - location2.x;
		double deltaY = location1.y - location2.y;

		double deltaDist = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

		return deltaDist;
	}

	public void setSelection(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void display(Mat drawToThis) {
		Scalar color;
		int lineThickness;
		if (selected) {
			color = new Scalar(0, 0, 255);
			lineThickness = 3;
		} else {
			color = new Scalar(0, 255, 0);
			lineThickness = 1;
		}
		
		Imgproc.rectangle(drawToThis, new Point(location.x, location.y),
				new Point(location.x + size.width, location.y + size.height),
				color, lineThickness);
	}

	public void prepareForMatching() {
		framesSinceMatch++;
		matchedThisFrame = false;
	}

	public boolean hasBeenMatchedThisFrame() {
		return matchedThisFrame;
	}

	public Point getLocation() {
		return location;
	}

	public Size getSize() {
		return size;
	}

	public boolean isInside(Point p) {
		if (p.x >= location.x && p.x <= location.x + size.width) {
			if (p.y >= location.y && p.y <= location.y + size.height) {
				return true;
			}
		}
		return false;
	}
	
	public boolean disposeYet() {
		return framesSinceMatch >= disposeAfterFrames;
	}

	public void attemptToFollow() {
		
		double[] changeX = new double[BUFFER_SIZE-1];
		double[] changeY = new double[BUFFER_SIZE-1];
		
		for (int count = 0; count < BUFFER_SIZE-1; count++) {
			changeX[count] = previousLocations.get(count+1).x - previousLocations.get(count).x;
			changeY[count] = previousLocations.get(count+1).y - previousLocations.get(count).y;
		}
		
		double aproxDeltaX = 0;
		double aproxDeltaY = 0;
		
		for (int count = 0; count < BUFFER_SIZE-1; count++) {
			aproxDeltaX += changeX[count];
			aproxDeltaY += changeY[count];
		}
		aproxDeltaX /= (BUFFER_SIZE-1);
		aproxDeltaY /= (BUFFER_SIZE-1);
		
		location.x += aproxDeltaX;
		location.y += aproxDeltaY;
	}
}
