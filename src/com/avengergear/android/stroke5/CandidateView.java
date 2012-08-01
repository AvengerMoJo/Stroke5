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

import android.inputmethodservice.InputMethodService;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.LightingColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;

public class CandidateView extends Button { 
//public class CandidateView extends TextView { 

	private String	mButtonText; 
	private Stroke5	mStroke5;

	public CandidateView(Context context, AttributeSet attrs){
		super(context, attrs);
		Log.d("Stroke5IME", "initialize CandidateView"+ this.isShown() );
	}

	public CandidateView(Context context, String button_text, Stroke5 s5){
		super(context);
		mButtonText = button_text;
		mStroke5 = s5; 
		init();
	}

	private void init(){ 
		Log.d("Stroke5IME", "initialize CandidateView "+ mButtonText);
		this.setTextSize(32);
		this.setText(mButtonText);
		this.setBackgroundResource(R.drawable.green_button);
		this.setOnClickListener( new OnClickListener() { 
			@Override
			public void onClick(View v){
				Log.d("Stroke5IME", "onClickListener"+ mButtonText);
				mStroke5.commit(mButtonText); 
			}
		});
	}

	public void lookUpSuggestion(){
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public void setBackgroundDrawable(Drawable d) {
		BackgroundDrawable layer = new BackgroundDrawable(d);
		super.setBackgroundDrawable(layer);
	}
	
	/**
	 * The LayerDrawable used by candidates button
	 * 
	 * reference https://github.com/shiki/android-autobgbutton
	 **/
	protected class BackgroundDrawable extends LayerDrawable {
		// The color filter to apply when the button is pressed
		protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
		// Alpha value when the button is disabled
		//protected int _disabledAlpha = 100;
		public BackgroundDrawable(Drawable d) {
			super(new Drawable[] { d });
		}
		
		@Override
		public boolean isStateful() {
			return true;
		}

		@Override
		protected boolean onStateChange(int[] states) {
			boolean pressed = false;
			
			for (int state : states) {
				if (state == android.R.attr.state_pressed)
					pressed = true;
			}
			mutate();
			if (pressed) {
				setColorFilter(_pressedFilter);
			} else {
				setColorFilter(null);
			}
			invalidateSelf();
			return super.onStateChange(states);
		}
	}
}
