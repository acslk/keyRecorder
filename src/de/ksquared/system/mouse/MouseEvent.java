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

package de.ksquared.system.mouse;

import java.util.EventObject;

public class MouseEvent extends EventObject {
	private static final long serialVersionUID = -8194688548489965445L;
	
	public static final int TRANSITION_STATE_MOVE = 1,TRANSITION_STATE_DOWN = 2,TRANSITION_STATE_UP = 3;
	public static final int BUTTON_NO = 0x0,BUTTON_LEFT = 1<<1,BUTTON_RIGHT = 1<<2;
	
	protected GlobalMouseListener listener;
	protected int transitionState,button,buttons;
	protected int x,y;

	public MouseEvent(Object source,GlobalMouseListener listener,int transitionState,int button,int buttons,int x,int y) {
		super(source);
		this.listener = listener;
		this.transitionState = transitionState;
		this.button = button;
		this.buttons = buttons;
		this.x = x;
		this.y = y;
	}
	
	public int getTransitionState() { return transitionState; }
	public int getButton() { return button; }
	public int getButtons() { return buttons; }
	public int getX() { return x; }
	public int getY() { return y; }
	
	public boolean equals(MouseEvent event) {
		return event.getButton()==button
		     &&event.getButtons()==buttons
		     &&event.getX()==x
		     &&event.getY()==y;
	}
	
	@Override public String toString() {
		return x + " " + y;
	}
}