package com.celciuslounge.mav;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class TimelineHelper extends SQLiteOpenHelper {
	private static final String TAG = "TimelineHelper";
	private static final String DB_NAME = "celciustimeline.db";
	private static final int DB_VERSION = 1;
	
	public static final String TABLE = "timeline";
	public static final String KEY_ID = BaseColumns._ID;
	public static final String KEY_USER = "user";
	public static final String KEY_MSG = "msg";
	public static final String KEY_CREATED_AT = "createdAt";
	
	private static final String DB_CREATE
		= "create table " + TABLE + " ("
		+ KEY_ID + " int primary key, "
		+ KEY_USER + " text, "
		+ KEY_MSG + " text, "
		+ KEY_CREATED_AT + " int"
		+ ");" ;
	
	public TimelineHelper(Context context) 
			 {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating the timeline database");
		db.execSQL(DB_CREATE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading timeline database from version "+ oldVersion + " to " + newVersion);
		db.execSQL("drop table if exists " + TABLE);
		onCreate(db);
	}

}

