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

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;

import android.util.AttributeSet;
import android.util.Log;

public class Stroke5KeyboardView extends KeyboardView {

	public Stroke5KeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("Stroke5IME", "Stroke5KeyboardView->parent - width:"+this.getWidth());
		Log.d("Stroke5IME", "Stroke5KeyboardView->parent - height:"+this.getHeight());
	}

	public Stroke5KeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
	}
}

