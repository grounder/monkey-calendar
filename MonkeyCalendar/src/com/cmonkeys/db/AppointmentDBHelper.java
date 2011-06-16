package com.cmonkeys.db;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentDBHelper extends SQLiteOpenHelper {

	static final String m_nameOfTable = "appointment";
	static final String m_nameOfIndex = "_id";
	static final String m_nameOfTitle = "title";
	static final String m_nameOfDescription = "description";
	static final String m_nameOfLastUpdate = "lastUpdate";
	static final String m_nameOfParticipant = "participant";
	static final String m_nameOfLocation = "location";
	static final String m_nameOfStarttime = "starttime";
	static final String m_nameOfFinishtime = "finishtime";
	static final String m_nameOfRepeatWeekly = "repeatweekly";
	static final String m_nameOfRepeatMonthly = "repeatmonthly";
	static final String m_nameOfRepeatYearly = "repeatyearly";
		
	public AppointmentDBHelper(Context context) 
	{
		super(context,"MKCal.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + m_nameOfTable + "(" + m_nameOfIndex + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ m_nameOfTitle + " TEXT, " + m_nameOfDescription+ " TEXT, " + m_nameOfLastUpdate + " TEXT, " 
				+ m_nameOfParticipant + " TEXT, " + m_nameOfLocation + " TEXT, " 
				+ m_nameOfStarttime + " TEXT, " + m_nameOfFinishtime + " TEXT,"
				+ m_nameOfRepeatYearly + " INTEGER, " + m_nameOfRepeatMonthly + "INTEGER,"
				+ m_nameOfRepeatWeekly + " INTEGER );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + m_nameOfTable);
		onCreate(db);
	}
	
	public void insertAppointment(Appointment app)
	{
		insertAppointment(app.getTitle(), app.getDescription(), app.getM_parts(), app.getM_location(), 
				app.getM_start(), app.getM_end());
	}

	public void insertAppointment(String title, String description, String participant, String location,
			Date starttime, Date finishtime)
    {
    	// set the format to sql date time 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(m_nameOfTitle, title);
    	row.put(m_nameOfDescription, description);
    	row.put(m_nameOfLastUpdate, dateFormat.format(now));
    	row.put(m_nameOfParticipant, participant);
    	row.put(m_nameOfLocation, location);
    	row.put(m_nameOfStarttime, dateFormat.format(starttime));
    	row.put(m_nameOfFinishtime, dateFormat.format(finishtime));
    		     
    	db.insert(m_nameOfTable, null, row);
    	
    	close();
    }	
	
	public void deleteAppointment(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(m_nameOfTable, m_nameOfIndex + "=" + index, null);
		close();
	}	

	public void updateAppointment(int index, String title, String description, String participant, String location,
			SimpleDateFormat starttime, SimpleDateFormat finishtime)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
				
		args.put(m_nameOfTitle, title);
		args.put(m_nameOfDescription, description);
		args.put(m_nameOfParticipant, participant);
		args.put(m_nameOfLocation, location);
		args.put(m_nameOfStarttime, dateFormat.format(starttime));
		args.put(m_nameOfFinishtime, dateFormat.format(finishtime));
		
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
    			new String[] {m_nameOfIndex, m_nameOfTitle, m_nameOfDescription, m_nameOfLastUpdate,
    				m_nameOfParticipant, m_nameOfLocation, m_nameOfStarttime, m_nameOfFinishtime}, 
    				null, null, null, null, null);
    	    	
    	return cursor;    	
	}

	public ArrayList<Appointment> getAppointment(int year, int month , int day)
	{
		ArrayList<Appointment> arrayOfAppointment = new ArrayList<Appointment>();
		
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
    		cursor = db.query(m_nameOfTable, 
    			new String[] {m_nameOfIndex, m_nameOfTitle, m_nameOfDescription, m_nameOfLastUpdate,
    				m_nameOfParticipant, m_nameOfLocation, m_nameOfStarttime, m_nameOfFinishtime}, 
    				null, null, null, null, null);
				
		return arrayOfAppointment;
	}
}
