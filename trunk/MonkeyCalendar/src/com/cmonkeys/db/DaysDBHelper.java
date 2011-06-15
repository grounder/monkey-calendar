package com.cmonkeys.db;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DaysDBHelper extends SQLiteOpenHelper {

	static final String m_nameOfTable = "appointment";
	static final String m_nameOfIndex = "_id";
	static final String m_nameOfSolarDate = "solarDate";
	static final String m_nameOfLunarDate = "lunarDate";
	static final String m_nameOfNameOfHoliday = "nameOfHoliday";
	static final String m_nameOfIsHoliday = "isHoliday";
	static final String m_nameOfLastUpdate = "lastUpdate";
		
	public DaysDBHelper(Context context) 
	{
		super(context,"Days.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + m_nameOfTable + "(" + m_nameOfIndex + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ m_nameOfSolarDate + " TEXT, " + m_nameOfLunarDate+ " TEXT, "  + m_nameOfNameOfHoliday + " TEXT, " 
				+ m_nameOfIsHoliday + " TEXT, " + m_nameOfLastUpdate + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + m_nameOfTable);
		onCreate(db);
	}
	
	public void insertDay(Days app)
	{
		insertDay(app.getM_solarDate(), app.getM_lunarDate(), app.getM_nameOfHoliday(), app.isM_isHoliday());
	}

	public void insertDay(Date solarDate, Date lunarDate, String nameOfHoliday, boolean isHoliday)
    {
    	// set the format to sql date time 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(m_nameOfSolarDate, dateFormat.format(solarDate));
    	row.put(m_nameOfLunarDate, dateFormat.format(lunarDate));
    	row.put(m_nameOfNameOfHoliday, nameOfHoliday);
    	row.put(m_nameOfIsHoliday, isHoliday);
    	row.put(m_nameOfLastUpdate, dateFormat.format(now));
    		     
    	db.insert(m_nameOfTable, null, row);
    	
    	close();
    }	
	
	public void deleteDay(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(m_nameOfTable, m_nameOfIndex + "=" + index, null);
		close();
	}	

	public void updateDay(int index, Date solarDate, Date lunarDate, String nameOfHoliday, boolean isHoliday)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
		Date now = new Date();
				
		args.put(m_nameOfSolarDate, dateFormat.format(solarDate));
		args.put(m_nameOfLunarDate, dateFormat.format(lunarDate));
		args.put(m_nameOfNameOfHoliday, nameOfHoliday);
		args.put(m_nameOfIsHoliday, isHoliday);
		args.put(m_nameOfLastUpdate, dateFormat.format(now));
		
		db = getWritableDatabase();
		db.update(m_nameOfTable, args, m_nameOfIndex + "=" + index, null);
 
		close();
	}
	
	public Cursor getCursor()
	{
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
    		cursor = db.query(m_nameOfTable, 
    			new String[] {m_nameOfIndex, m_nameOfSolarDate, m_nameOfLunarDate, m_nameOfNameOfHoliday,
    				m_nameOfIsHoliday, m_nameOfLastUpdate}, 
    				null, null, null, null, null);
    	    	
    	return cursor;    	
	}
	
	

}
