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

import java.util.List;
import java.util.Vector;

public class GlobalKeyListener {
	protected PoolHook hook;
	public GlobalKeyListener() { (hook=new PoolHook(this)).start();	}
	protected List<KeyListener> listeners = new Vector<KeyListener>();

	public void addKeyListener(KeyListener listener) { listeners.add(listener); }
	public void removeKeyListener(KeyListener listener) { listeners.remove(listener); }

	void keyPressed(KeyEvent event) {
		try {
			for(KeyListener listener:listeners)
				listener.keyPressed(event);
		} catch(Exception e) { e.printStackTrace(); }
	}
	void keyReleased(KeyEvent event) {
		try {
			for(KeyListener listener:listeners)
				listener.keyReleased(event);
		} catch(Exception e) { e.printStackTrace(); }
	}
}
