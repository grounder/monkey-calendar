package com.cmonkeys.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentDBHelper extends SQLiteOpenHelper {

	static final String m_nameOfTable = "appointment";
	static final String m_nameOfIndex = "_id";
	static final String m_nameOfTitle = "title";
	static final String m_nameOfDescription = "description";
	static final String m_nameOfLastUpdate = "lastUpdate";
	static final String m_nameOfPart = "part";
	static final String m_nameOfLocation = "location";
		
	public AppointmentDBHelper(Context context) 
	{
		super(context,"MKCal.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + m_nameOfTable + "(" + m_nameOfIndex + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ m_nameOfTitle + " TEXT, description TEXT, " + m_nameOfLastUpdate + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
