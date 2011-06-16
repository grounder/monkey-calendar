package com.cmonkeys.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DaysImportDBHelper extends SQLiteOpenHelper {

	public DaysImportDBHelper(Context context) {
		super(context,"MKCalDays.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE days (_id INTEGER PRIMARY KEY AUTOINCREMENT, s_year INTEGER, s_month INTEGER, s_day INTEGER, l_year INTEGER, l_month INTEGER, l_day INTEGER, s_holiday TEXT, l_holiday TEXT, holiday_type INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS days");
		onCreate(db);
	}
	
	public Cursor getCursor()
	{
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
		cursor = db.query("days", 
			new String[] {"_id", "s_year" ,"s_holiday"}, 
				"s_holiday=\'어린이날\'", null, null, null, null);
	
    	return cursor;    	
	}
}
