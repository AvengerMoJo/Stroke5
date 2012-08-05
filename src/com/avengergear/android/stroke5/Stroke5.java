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

import java.io.IOException;

import android.content.Context;
import android.database.SQLException;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;  
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.View;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.HorizontalScrollView;
import android.widget.FrameLayout;

import android.util.Log;

public class Stroke5 extends InputMethodService implements KeyboardView.OnKeyboardActionListener
{
	private FrameLayout		mFrameLayout;
	//private ScrollView		mScrollView;
	private HorizontalScrollView	mScrollView;
	private CandidateViewContainer  mCandidateViewContainer;
	private CandidateView           mCandidateView;
	private InputConnection		mInputConnection;

	private KeyboardContainer       mKeyboardContainer;
	private KeyboardView            mStroke5KeyboardView;
	private Stroke5Keyboard         mStroke5Keyboard;

	private StringBuilder		mComposing = new StringBuilder();

	private DatabaseHelper		mCommaCharTable;
	private DatabaseHelper		mDotCharTable;
	private DatabaseHelper		mMCharTable;
	private DatabaseHelper		mNCharTable;
	private DatabaseHelper		mSlashCharTable;


	/* Override the onCreate, to build the setup */ 
	@Override public void onCreate() {
		super.onCreate();
		Log.d("Stroke5IME", "Stroke5->onCreate");
		mCommaCharTable = new DatabaseHelper(this, "comma_char_table");
		mDotCharTable = new DatabaseHelper(this, "dot_char_table");
		mMCharTable = new DatabaseHelper(this, "m_char_table");
		mNCharTable = new DatabaseHelper(this, "n_char_table");
		mSlashCharTable = new DatabaseHelper(this, "slash_char_table");
		try {
			mCommaCharTable.createDatabase();
			mDotCharTable.createDatabase();
			mMCharTable.createDatabase();
			mNCharTable.createDatabase();
			mSlashCharTable.createDatabase();
		} catch (IOException e) {
			throw new Error("Unable to create database :" + e);
		}
		 
		try {
			mCommaCharTable.openDatabase();
			mDotCharTable.openDatabase();
			mMCharTable.openDatabase();
			mNCharTable.openDatabase();
			mSlashCharTable.openDatabase();
		}catch(SQLException e){
			throw new Error("Unable to open database :" + e);
		}
	}

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
		Log.d("Stroke5IME", "Stroke5->onInitializeInterface");
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
		Log.d("Stroke5IME", "Stroke5->onBindInput");
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
		Log.d("Stroke5IME", "Stroke5->onStartInput.");
	}
	/**
	 * Create the actual Stroke5 input view for the once the input
	 * method being called or re-configure etc 
	 *
	 **/
	@Override public View onCreateInputView() {
		Log.d("Stroke5IME", "Stroke5->onCreateInputView.");
		mKeyboardContainer = (KeyboardContainer) getLayoutInflater().inflate(R.layout.keyboard_container, null);
		mStroke5KeyboardView = (KeyboardView) mKeyboardContainer.getChildAt(1);
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
		Log.d("Stroke5IME", "Stroke5->onCreateCandidatesView.");
		mFrameLayout= (FrameLayout) mKeyboardContainer.getChildAt(0);
		Log.d("Stroke5IME", "Stroke5->onCreateCandidatesView->getFrameLayout");
		mScrollView = (HorizontalScrollView) mFrameLayout.getChildAt(0); 
		Log.d("Stroke5IME", "Stroke5->onCreateCandidatesView->getScrollView");
		mCandidateViewContainer = (CandidateViewContainer) mScrollView.getChildAt(0);
		Log.d("Stroke5IME", "Stroke5->onCreateCandidatesView->getCandidateViewContainer");
		setCandidatesViewShown(false);
		// we are not using the CandiatesView default Framelayout
		mCandidateViewContainer.setInput(this); 
		return null;
	}
	
	@Override public void onStartInputView (EditorInfo info, boolean restarting) {
		super.onStartInputView(info, restarting);
		Log.d("Stroke5IME", "Stroke5->onStartInputView.");
		// (info.inputType & EditorInfo.TYPE_CLASS_MASK)
		//     TYPE_CLASS_NUMBER
		//     TYPE_CLASS_DATETIME
		//     TYPE_CLASS_PHONE
		//     TYPE_CLASS_TEXT
		//     TYPE_TEXT_VARIATION_PASSWORD or TYPE_TEXT_VARIATION_URI or TYPE_TEXT_FLAG_AUTO_COMPLETE
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
		Log.d("Stroke5IME", "Stroke5->onCreateExtractTextView.");
		return super.onCreateExtractTextView();
	}


	/**
	 * InputMethodService Interface Ended
	 *
	 **/

	public void onText(CharSequence text) {
		Log.d("Stroke5IME", "Stroke5->onText");
	}

	public void commit(String input) { 
		mInputConnection.commitText(input, 1); 
		mComposing.delete(0, mComposing.length());
	}

	/**
	 * Updating the candidate list base on the input from the soft key
	 *
	 * ToDo:
	 * There are only 5 keys max, we can try to make more AI into word
	 * combination in the future 
	 *
	 **/
	private void updateCandidates() {
		DatabaseHelper tempTable = null;
		String candidates = null;
		Log.d("Stroke5IME", "Stroke5->updateCandidates");
		if( mComposing.length() > 0 ){
			switch(mComposing.charAt(0)){
				case ',':
					Log.d("Stroke5IME", "Stroke5->updateCandidates->comma db");
					tempTable = mCommaCharTable;
					break;
				case '.':
					Log.d("Stroke5IME", "Stroke5->updateCandidates->dot db");
					tempTable = mDotCharTable; 
					break;
				case 'm':
					Log.d("Stroke5IME", "Stroke5->updateCandidates->m db");
					tempTable = mMCharTable; 
					break;
				case 'n':
					Log.d("Stroke5IME", "Stroke5->updateCandidates->n db");
					tempTable = mNCharTable; 
					break;
				case '/':
					Log.d("Stroke5IME", "Stroke5->updateCandidates->slash db");
					tempTable = mSlashCharTable; 
					break;
			}
			switch( mComposing.length() ){
				case 1:
					mCandidateViewContainer.setupSingleCharTable(mComposing.toString());
					break;
				case 2:
					mCandidateViewContainer.setupTwoCharTable(mComposing.charAt(0),mComposing.charAt(1));
					break;
				case 3:
					candidates = tempTable.getCharList(
						Character.toString(mComposing.charAt(0)),
						Character.toString(mComposing.charAt(1)),
						Character.toString(mComposing.charAt(2)));
					Log.d("Stroke5IME", "Stroke5->updateCandidates->3" + candidates);
					mCandidateViewContainer.createCandidatesButton(candidates); 
					break;
				case 4:
					candidates = tempTable.getCharList(
						Character.toString(mComposing.charAt(0)),
						Character.toString(mComposing.charAt(1)),
						Character.toString(mComposing.charAt(2)),
						Character.toString(mComposing.charAt(3)));
					Log.d("Stroke5IME", "Stroke5->updateCandidates->4" + candidates);
					mCandidateViewContainer.createCandidatesButton(candidates); 
					break;
				case 5:
					candidates = tempTable.getCharList(
						Character.toString(mComposing.charAt(0)),
						Character.toString(mComposing.charAt(1)),
						Character.toString(mComposing.charAt(2)),
						Character.toString(mComposing.charAt(3)),
						Character.toString(mComposing.charAt(4)));
					Log.d("Stroke5IME", "Stroke5->updateCandidates->5" + candidates);
					mCandidateViewContainer.createCandidatesButton(candidates); 
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Backspace is being handle in both text and composing text
	 **/
	private void backspace() {
		Log.d("Stroke5IME", "Stroke5->backspace");
		if( mComposing.length() > 0 ) { 
			mComposing.deleteCharAt(mComposing.length()-1); 
			mInputConnection.setComposingText(mComposing, mComposing.length());
		} else { 
			mInputConnection.deleteSurroundingText(1,0);
		}
	}

	/* Handle the onKey from the virtual keyboard. */

	@Override public void onKey(int primaryCode, int[] keyCodes) {
		Log.d("Stroke5IME", "onKey - KeyCode ="+primaryCode +" "+ keyCodes[0] );
		mInputConnection = getCurrentInputConnection();
		switch( primaryCode ){
			case KeyEvent.KEYCODE_N:
			case 110:
			case KeyEvent.KEYCODE_M:
			case 109:
			case KeyEvent.KEYCODE_COMMA:
			case 44:
			case KeyEvent.KEYCODE_PERIOD:
			case 46:
			case KeyEvent.KEYCODE_SLASH:
			case 47:
				Log.d("Stroke5IME", "onKey -- "+primaryCode );
				Log.d("Stroke5IME", "onKey key before "+ mInputConnection.getTextBeforeCursor(1,0));
				mComposing.append((char) primaryCode);
				mInputConnection.setComposingText(mComposing, mComposing.length() );
				Log.d("Stroke5IME", "onKey key "+ mComposing +mComposing.length() );
				break;
			case -1:
				backspace(); 
				break;
			default:
				Log.d("Stroke5IME", "onKey -- other key? "+primaryCode );
				mComposing.append((char) primaryCode);
				break;
		}
		updateCandidates();
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

