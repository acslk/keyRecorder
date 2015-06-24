package testing;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RecordMain extends JFrame {

	private static final long serialVersionUID = -1986232613855164253L;
	private RecordArea a;
	private static ImageIcon inactive = new ImageIcon ("image/grey.png");
	private static ImageIcon active = new ImageIcon ("image/green.png");
	private static ImageIcon wait = new ImageIcon ("image/yellow.png");
	
	public RecordMain () {
		
		try {
			a = new RecordArea(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLocation(500, 500);	
		this.setResizable(false);
		this.setIconImage(inactive.getImage());
	}
	
	public void setIcon (int state) {
		if (state == 1)
			this.setIconImage(inactive.getImage());
		else if (state == 2)
			this.setIconImage(active.getImage());
		else if (state == 3)
			this.setIconImage(wait.getImage());
	}
	

	public static void main(String[] args) {
		
		final RecordMain frame = new RecordMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setState(Frame.ICONIFIED);
		frame.pack();
		frame.setVisible(true);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        frame.a.update();
		    }
		});
		
	}

}
