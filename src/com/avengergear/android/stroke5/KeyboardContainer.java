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

import android.util.AttributeSet;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.util.Log;



public class KeyboardContainer extends LinearLayout {

	public KeyboardContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("Stroke5IME", "KeyboardContainer->init");
		Log.d("Stroke5IME", "KeyboardContainer->parent - width:"+this.getWidth());
		Log.d("Stroke5IME", "KeyboardContainer->parent - height:"+this.getHeight());
		Log.d("Stroke5IME", "KeyboardContainer->parent - width:"+this.getMeasuredWidth());
		Log.d("Stroke5IME", "KeyboardContainer->parent - height:"+this.getMeasuredHeight());
	} 

}

