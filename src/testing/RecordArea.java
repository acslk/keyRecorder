package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseAdapter;
import de.ksquared.system.mouse.MouseEvent;

public class RecordArea {

	private boolean recording, pause, entering;
	private FileWriter writer, writePath;
	private RecordMain parentFrame;
	private long start;

	public RecordArea(RecordMain parentFrame) throws IOException {
		
		this.parentFrame = parentFrame;
		entering = false;
		recording = false;
		pause = false;
		writePath = new FileWriter(new File("path.txt"),true);
		
		new GlobalMouseListener().addMouseListener(new MouseRecorder());
		new GlobalKeyListener().addKeyListener(new KeyRecorder());
		
	}
	
	public void record (String s) {
		try {
			writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void end () {
		if (recording && !pause) {
			recording = false;
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			parentFrame.setIcon(1);
		}
	}
	
	public void create () {

		if (!recording && !entering) {
			
			entering = true;
			String name =  JOptionPane.showInputDialog(parentFrame ,"Enter in file name:");
			
			if (name == null)
				return;
			
			File file = new File("file/" + name + ".s");
			try {
				writer = new FileWriter(file);
				writePath.write("\n" + "file/" + name + ".s" + "\t" + "\t");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void update() {
		try {
			writePath.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class MouseRecorder extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			if (recording) {
				record("M " + event.toString() + "\n");
			}
		}
	}

	private class KeyRecorder extends KeyAdapter {	
		
		public void keyPressed(KeyEvent event) {
			if (event.getVirtualKeyCode() == KeyEvent.VK_ESCAPE) {
				end();
				update();
				System.exit(0);
			}

			else if (event.getVirtualKeyCode() == KeyEvent.VK_NUMPAD0) {
				end();
			}

			else if (event.getVirtualKeyCode() == KeyEvent.VK_NUMPAD1 || event.getVirtualKeyCode() == KeyEvent.VK_OEM_1) {
				create();
			}
			else if (!recording) {
				return;
			}
			else if (event.getVirtualKeyCode() == KeyEvent.VK_F2) {
				if (!pause) {
					pause = true;
					start = System.currentTimeMillis();
					parentFrame.setIcon(3);
				}
				else {
					pause = false;
					long time = System.currentTimeMillis() - start;
					record("D " + time + "\n");
					parentFrame.setIcon(2);
				}
			}
			
			else if (!pause) {
				record("P"+ event.toString());
			}
		}
		
		public void keyReleased(KeyEvent event) {
			
			if (!recording && entering && event.getVirtualKeyCode() == 10) {
				recording = true;
				parentFrame.setIcon(2);
				entering = false;
			}
			else if (!recording || pause || event.getVirtualKeyCode() == KeyEvent.VK_F2) {
				return;
			}
			else {
				record("R" + event.toString());
			}
		}
	}

}
