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
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import android.util.Log;


public class Stroke5Keyboard extends Keyboard {

	public Stroke5Keyboard(Context context, int xmlLayoutResId) {
		super(context, xmlLayoutResId);
		Log.d("Stroke5IME", "Stroke5Keyboard->init");
	} 

	public Stroke5Keyboard(Context context, int layoutTemplateResId,
		CharSequence characters, int columns, int horizontalPadding) {
		super(context, layoutTemplateResId, characters, columns, horizontalPadding);
		Log.d("Stroke5IME", "Stroke5Keyboard->init - Context");
	}


	static class Stroke5Key extends Keyboard.Key {
		public Stroke5Key(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
			super(res, parent, x, y, parser);
			Log.d("Stroke5IME", "Stroke5Key->init - Resource");
		}
		/**
		 * Overriding this method so that we can reduce the target area for the key that
		 * closes the keyboard. 
		 **/
		@Override public boolean isInside(int x, int y) {
			Log.d("Stroke5IME", "Stroke5Key->isInside");
			return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
		}
	}
}

