package testing;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;

public class ExPanel extends JPanel {

	private static final long serialVersionUID = 2712103067217369106L;
	private ExMain parentFrame;
	private Robot b;
	private Scanner sc;
	private ArrayList<String> files;
	private ArrayList<Integer> key;
	private boolean running;
	private boolean listen;
	private boolean interupt;
	private String name;
	
	public ExPanel (ExMain parentFrame) {
		
		this.parentFrame = parentFrame;
		running = false;
		listen = true;
		interupt = false;
		
		files = new ArrayList <String> ();
		key = new ArrayList <Integer> ();
		
		try {
			b = new Robot();
			b.setAutoDelay(1);
			initiate();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		new GlobalKeyListener().addKeyListener(new KeyEx());
		Execute ex = new Execute();
		ex.start();

	}
	
	private void initiate() {
		
		key = new ArrayList<Integer>();
		
		try {
			sc = new Scanner (new FileReader ("path.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (sc.hasNext()) {
			files.add(sc.next());
			if (sc.hasNextInt())
				key.add(sc.nextInt());
			else
				key.add(-1);
		}
	}
	
	public void type (int keyCode) {
		b.keyPress(keyCode);
		b.keyRelease(keyCode);
	}
	
	
	public void typeW (String word) {
		b.setAutoDelay(10);
		for (int i = 0; i < word.length(); i++) {
			type(word.charAt(i));
		}
		b.setAutoDelay(100);
	}
	
	public void mp (int x, int y) {
		b.mouseMove(x, y);
		b.mousePress(InputEvent.BUTTON1_MASK);
		b.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	private class KeyEx extends KeyAdapter {
		public void keyPressed(KeyEvent event) {
			
			if (event.getVirtualKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0);
			else if (event.getVirtualKeyCode() == KeyEvent.VK_NUMPAD0)
				listen = !listen;
			else if (event.getVirtualKeyCode() == KeyEvent.VK_NUMPAD1)
				interupt = true;
			else if (!listen || running)
				return;
			else {
				int t = key.indexOf(event.getVirtualKeyCode());
				if (t != -1) {
					running = true;
					name = files.get(t);
				}
			}
			
			parentFrame.setIcon(listen, running);
			
		}
	}
	
	private class Execute extends Thread {
		
		public Execute() {
			
		}

		@Override
		public void run() {
			
			while (true) {
				
				if (running) {
					interupt = false;
					
					try {
						sc = new Scanner (new FileReader (name));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					while (sc.hasNext() && !interupt) {
						
						String s = sc.next();
						
						if (s.equals("M"))
							mp (sc.nextInt(), sc.nextInt());
						else if (s.equals("K"))
							type (sc.nextInt());
						else if (s.equals("PK"))
							b.keyPress(sc.nextInt());
						else if (s.equals("RK"))
							b.keyRelease(sc.nextInt());
						else if (s.equals("D"))
							b.delay(sc.nextInt());
						else {
							System.out.println("invalid command");
							break;
						}
					}
					sc.close();
					running = false;
					parentFrame.setIcon(listen, running);
				}
				
				try { Thread.sleep(10); }
				catch(InterruptedException e) { e.printStackTrace(); }
			}

		}

	}

}
