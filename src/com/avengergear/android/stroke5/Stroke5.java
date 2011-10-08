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
	private KeyboardContainer       mKeyboardContainer;
	private KeyboardView            mStroke5KeyboardView;
	private Stroke5Keyboard         mStroke5Keyboard;


	/* Override the onCreate, to build the setup */ 
	@Override public void onCreate() {
		super.onCreate();
		Log.d("Stroke5IME", "onCreate");
	}

	/**
	 * InputMethodService Interface Started
	 *
	 * The following is InputMethod Abstract interface for call
	 * back and option for implementation
	 *
	 **/

	/**
	 * Setting up the UI after onCreate and Configuration setup
	 *
	 * google:
	 * for user-interface initialization, in particular to deal 
	 * with configuration changes while the service is running.
	 *
	 **/
	@Override public void onInitializeInterface() {
		super.onInitializeInterface();
		Log.d("Stroke5IME", "onInitializeInterface");
		// create the acutal layout of the Stroke5 keyboard
		mStroke5Keyboard = new Stroke5Keyboard(this, R.xml.stroke5);
	} 

	/**
	 * Handle switching over from one client to another
	 *
	 * google:
	 * to find out about switching to a new client.
	 *
	 **/
	@Override public void onBindInput() {
		super.onBindInput();
		Log.d("Stroke5IME", "onBindInput");
	}

	/**
	 * When client connected to the input method interface
	 * 
	 * google:
	 * to deal with an input session starting with the client.
	 *
	 **/
	@Override public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);
	}

	/**
	 * Create the actual Stroke5 input view for the once the input
	 * method being called or re-configure etc 
	 *
	 **/
	@Override public View onCreateInputView() {
		Log.d("Stroke5IME", "onCreateInputView.");
		mKeyboardContainer = (KeyboardContainer) getLayoutInflater().inflate(R.layout.keyboard_container, null);
		mStroke5KeyboardView = (KeyboardView) mKeyboardContainer.getChildAt(0);
		mStroke5KeyboardView.setOnKeyboardActionListener(this);
		mStroke5KeyboardView.setKeyboard(mStroke5Keyboard);
		return mKeyboardContainer;
	}

	/**
	 * Create the actual candidates containre for user to pick
	 * stroke5 candidates, stroke5 in general has lot of 
	 * candidates for user to choice from, we need bigger service
	 * area for older user to look and pick their choice
	 *
	 **/
	@Override public View onCreateCandidatesView() {
		Log.d("Stroke5IME", "onCreateCandidatesView.");
		return super.onCreateCandidatesView();
	}

	/**
	 * Create UI for extractTextView 
	 *
	 * google:
	 * Called by the framework to create the layout for showing
	 * extacted text. Only called when in fullscreen mode. The 
	 * returned view hierarchy must have an ExtractEditText 
	 * whose ID is inputExtractEditText. 
	 *
	 **/ 
	@Override public View onCreateExtractTextView(){
		Log.d("Stroke5IME", "onCreateExtractTextView.");
		return super.onCreateExtractTextView();
	}


	/**
	 * InputMethodService Interface Ended
	 *
	 **/

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

