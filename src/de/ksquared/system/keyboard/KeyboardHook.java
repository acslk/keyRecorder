/*
 * Copyright 2011 Kristian Kraljic, Johannes Schüth 2008. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY KRISTIAN KRALJIC AND JOHANNES SCHLUETH ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL KRISTIAN KRALJIC AND JOHANNES SCHLUETH OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Kristian Kraljic and Johannes Schüth.
 */

package de.ksquared.system.keyboard;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class PoolHook extends Thread {
	private KeyboardHook hook;
	private GlobalKeyListener listener;

	PoolHook(GlobalKeyListener listener) {
		this.setDaemon(true);
		this.listener = listener;
	}

	public void run() {
		hook = new KeyboardHook();
		hook.registerHook(listener);
	}
}

class EventProcedure extends Thread {
	private KeyboardHook hook;
	
	EventProcedure(KeyboardHook hook) {
		this.setDaemon(true);
		this.hook = hook;
	}
	
	@Override public void run() {
		while(true) {
			if(!hook.buffer.isEmpty()) {
				KeyEvent event = hook.buffer.remove(0);
				GlobalKeyListener listener = event.listener;
				if(event.transitionState)
					   listener.keyPressed(event);
				else listener.keyReleased(event);
			} else try { Thread.sleep(10); }	catch(InterruptedException e) { e.printStackTrace(); }
		}
	}
}

class KeyboardHook {	
	private boolean altPressed,shiftPressed,ctrlPressed,extendedKey;
	
	List<KeyEvent> buffer = Collections.synchronizedList(new LinkedList<KeyEvent>());
	private EventProcedure procedure = new EventProcedure(this);
	
	public KeyboardHook() { if(Native.load()) procedure.start(); }
	void processKey(boolean transitionState,int virtualKeyCode,GlobalKeyListener listener) {
		processControlKeys(transitionState,virtualKeyCode);
		buffer.add(new KeyEvent(this,listener,transitionState,virtualKeyCode,altPressed,shiftPressed,ctrlPressed,extendedKey));
	}
	
	native void registerHook(GlobalKeyListener listener);
	native void unregisterHook();
	
	void processControlKeys(boolean transitionState,int virtualKeyCode) {
		switch(virtualKeyCode) {
		case KeyEvent.VK_RWIN: extendedKey = transitionState; break;
		case KeyEvent.VK_RMENU: extendedKey = transitionState;
		case KeyEvent.VK_MENU: case KeyEvent.VK_LMENU:
			altPressed = transitionState;
			break;			
		case KeyEvent.VK_RSHIFT: extendedKey = transitionState;
		case KeyEvent.VK_SHIFT: case KeyEvent.VK_LSHIFT:
			shiftPressed = transitionState;
			break;
		case KeyEvent.VK_RCONTROL: extendedKey = transitionState;
		case KeyEvent.VK_CONTROL: case KeyEvent.VK_LCONTROL:
			ctrlPressed = transitionState;
			break;
		}
	}
}