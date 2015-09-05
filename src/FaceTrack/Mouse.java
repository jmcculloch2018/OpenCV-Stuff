package FaceTrack;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Mouse extends Thread implements MouseListener, MouseMotionListener {

	FeedManager feedManager;

	Rect targetPoint;

	public Mouse(FeedManager fM) {
		feedManager = fM;
		targetPoint = fM.targetPoint;
	}

	public void mouseDragged(MouseEvent arg0) {

		int mouseX = arg0.getX();
		int mouseY = arg0.getY();

		if (mouseX >= targetPoint.x - (targetPoint.width / 2)
				&& mouseX <= targetPoint.x + targetPoint.width) {
			if (mouseY >= targetPoint.y - (targetPoint.height / 2)
					&& mouseY <= targetPoint.y + targetPoint.height) {
				targetPoint.x = mouseX;
				targetPoint.y = mouseY;
			}
		}

	}

	public void mouseMoved(MouseEvent arg0) {

	}

	public void mouseClicked(MouseEvent e) {

		Point mouseLoc = new Point(e.getX(), e.getY());

		for (TrackedFace cur : feedManager.trackedFaces) {
			cur.setSelection(false);
		}

		for (TrackedFace cur : feedManager.trackedFaces) {

			if (cur.isInside(mouseLoc)) {
				cur.setSelection(true);
				System.out.println("select");
				break;
			}
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
