/*
 *  Copyright (C) 2011 AvengerGear Inc
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.avengergear.android.stroke5;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;  
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.view.KeyEvent;
import android.widget.TextView;

import android.util.Log;

public class Stroke5 extends InputMethodService implements KeyboardView.OnKeyboardActionListener
{

	/* Override the onCreate, to build the setup */ 
	@Override public void onCreate() {
		super.onCreate();
	}

	/**
	 * Setting up the UI after onCreate and Configuration setup
	 **/
	@Override public void onInitializeInterface() {
	}

	/**
	 * Create the actual Stroke5 input view for the once the input
	 * method being called or re-configure etc 
	 *
	 **/
	@Override public View onCreateInputView() {
		Log.d("Stroke5IME", "onCreateInputView.");
		return super.onCreateInputView();
	}

	@Override public View onCreateCandidatesView() {
		Log.d("Stroke5IME", "onCreateCandidatesView.");
		return super.onCreateCandidatesView();
	}

	@Override public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);
	}

	public void onText(CharSequence text) {
	}

	/* Handle the onKey from the virtual keyboard. */

	@Override public void onKey(int primaryCode, int[] keyCodes) {
	}

	/* Override the default hard key press from physical keyboard */

	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}

	public void swipeRight() {
	}

	public void swipeLeft() {} 

	public void swipeDown() {}

	public void swipeUp() {}

	public void onPress(int primaryCode) {}

	public void onRelease(int primaryCode) {}
}

