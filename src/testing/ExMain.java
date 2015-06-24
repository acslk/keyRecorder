package testing;

import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ExMain extends JFrame{
	
	private static final long serialVersionUID = 5819445677407684470L;
	private static ImageIcon on = new ImageIcon ("image/green.png");
	private static ImageIcon off = new ImageIcon ("image/red.png");
	private static ImageIcon running = new ImageIcon ("image/purple.png");
	
	public ExMain () {

		super("Ex");
		
		this.add(new ExPanel(this));
		this.setLocation(500, 500);	
		this.setResizable(false);
		this.setIconImage(on.getImage());
		
	}
	
	public void setIcon (boolean listen, boolean run) {
		if (listen) {
			if (run)
				this.setIconImage(running.getImage());
			else
				this.setIconImage(on.getImage());
		}
		else
			this.setIconImage(off.getImage());
	}
	
	public static void main (String[] args) {
		
		ExMain frame = new ExMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setState(Frame.ICONIFIED);
		frame.pack();
		frame.setVisible(true);
		
	}

}
