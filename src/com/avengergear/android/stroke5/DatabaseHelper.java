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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.SQLException; 

import android.content.Context;
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

public class DatabaseHelper extends SQLiteOpenHelper { 

	private static String DataPath = "/data/data/com.avengergear.android.stroke5/databases/";
	private String DataFile = null;
	private static String TableName= "char_table";
	 
	private SQLiteDatabase mCharTable;
	 
	private final Context mContext;

	public DatabaseHelper(Context context, String dbname) {
		super(context, dbname, null, 1);
		this.DataFile = new String(dbname);
		this.mContext = context;
	} 

	public void createDatabase() throws IOException{
		boolean dbExist = checkDatabase();
		if(!dbExist){
			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDatabase(){
		File dbFile = new File(DataPath + DataFile);
		return dbFile.exists();
	} 

	private void copyDatabase() throws IOException{ 
		//Open your local db as the input stream
		InputStream mIS = mContext.getAssets().open(DataFile);
		// Path to the just created empty db
		OutputStream mOS = new FileOutputStream(DataPath+DataFile);
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = mIS.read(buffer))>0){
			mOS.write(buffer, 0, length);
		}
		//Close the streams
		mOS.flush();
		mOS.close();
		mIS.close();
	}

	public void openDatabase() throws SQLException{
		//Open the database
		mCharTable = SQLiteDatabase.openDatabase(DataPath+DataFile, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if(mCharTable != null)
			mCharTable.close();
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public String getCharList(String stroke1, String stroke2, String stroke3) { 
		return this.getCharList(stroke1, stroke2, stroke3, null);
	}
	public String getCharList(String stroke1, String stroke2, String stroke3, String stroke4) { 
		return this.getCharList(stroke1, stroke2, stroke3, stroke4, null);
	}
	public String getCharList(String stroke1, String stroke2, String stroke3, String stroke4, String stroke5) {
		StringBuilder candidates = new StringBuilder();
		String where = null;
		String[] args;
		if( stroke5 != null ) { 
			where = " stroke5 = ? and stroke4 = ? and stroke3 = ? and stroke2 = ? and stroke1 = ? ";
			args = new String[] { stroke5, stroke4, stroke3, stroke2, stroke1 }; 
		} else if( stroke4 != null ) {
			where = " stroke4 = ? and stroke3 = ? and stroke2 = ? and stroke1 = ? ";
			args = new String[] { stroke4, stroke3, stroke2, stroke1 }; 
		} else {
			where = " stroke3 = ? and stroke2 = ? and stroke1 = ? "; 
			args = new String[] { stroke3, stroke2, stroke1 }; 
		}
		Cursor cursor = mCharTable.query( TableName, new String[] {"word"}, where, args, null, null, "freq", "40");
		if(cursor.getCount() == 0)
			return null;
		cursor.moveToLast();
		do { 
			candidates.append( cursor.getString(0));
		}while(cursor.moveToPrevious());
		cursor.close();
		return candidates.toString(); 
	}
}

