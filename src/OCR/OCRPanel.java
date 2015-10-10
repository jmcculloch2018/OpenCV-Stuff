package OCR;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OCRPanel extends JPanel { 
	
	JFrame frame;
	OCRScannerDemo demo;
	
	public OCRPanel(OCRScannerDemo demo) {
		this.demo = demo;
		frame = new JFrame();
		
		frame.add(this);
		
		setPreferredSize(new Dimension((int) demo.camera.getImageSize().width, (int) demo.camera.getImageSize().height));
	
		frame.pack();
		
		frame.setVisible(true);
	}
}
