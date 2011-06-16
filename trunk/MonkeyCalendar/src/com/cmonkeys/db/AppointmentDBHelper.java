package com.cmonkeys.db;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentDBHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "appointment";
	public static final String INDEX = "_id";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String LAST_UPDATE = "lastUpdate";
	public static final String PARTICIPANT = "participant";
	public static final String LOCATION = "location";
	public static final String START_TIME = "starttime";
	public static final String END_TIME = "finishtime";
	public static final String REPEAT = "repeat";
		

	public AppointmentDBHelper(Context context) 
	{
		super(context,"MKCal.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TITLE + " TEXT, " + DESCRIPTION+ " TEXT, " + LAST_UPDATE + " TEXT, " 
				+ PARTICIPANT + " TEXT, " + LOCATION + " TEXT, " 
				+ START_TIME + " TEXT, " + END_TIME + " TEXT,"
				+ REPEAT + " INTEGER );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insertAppointment(Appointment app)
	{
		insertAppointment(app.getTitle(), app.getDescription(), 
				app.getParticipants(), app.getLocation(), 
				app.getStart(), app.getEnd(), app.getRepeat());
	}

	public void insertAppointment(
			String title, String description, String participant, String location,
			Date startTime, Date endTime, int repeat)
    {
    	// set the format to sql date time 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(TITLE, title);
    	row.put(DESCRIPTION, description);
    	row.put(LAST_UPDATE, dateFormat.format(now));
    	row.put(PARTICIPANT, participant);
    	row.put(LOCATION, location);
    	row.put(START_TIME, dateFormat.format(startTime));
    	row.put(END_TIME, dateFormat.format(endTime));
    	row.put(REPEAT, repeat);
    		     
    	db.insert(TABLE_NAME, null, row);
    	
    	close();
    }	
	
	public void deleteAppointment(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(TABLE_NAME, INDEX + "=" + index, null);
		close();
	}	

	public void updateAppointment(int index, String title, String description, String participant, String location,
			Date starttime, Date finishtime, int repeat)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
				
		args.put(TITLE, title);
		args.put(DESCRIPTION, description);
		args.put(PARTICIPANT, participant);
		args.put(LOCATION, location);
		args.put(START_TIME, dateFormat.format(starttime));
		args.put(END_TIME, dateFormat.format(finishtime));
		args.put(REPEAT, repeat);
		
		db = getWritableDatabase();
		db.update(TABLE_NAME, args, INDEX + "=" + index, null);
 
		close();
	}
	
	public Cursor getCursor()
	{
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
		cursor = db.query(TABLE_NAME, 
			new String[] {INDEX, TITLE, DESCRIPTION, LAST_UPDATE,
				PARTICIPANT, LOCATION, START_TIME, END_TIME, REPEAT}, 
				null, null, null, null, null);

    	return cursor;    	
	}

	public ArrayList<Appointment> getAllAppointments()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Appointment> arrayOfAppointment = new ArrayList<Appointment>();
		
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
		cursor = db.query(TABLE_NAME, 
			new String[] {INDEX, TITLE, DESCRIPTION, LAST_UPDATE,
				PARTICIPANT, LOCATION, START_TIME, END_TIME, REPEAT}, 
				null, null, null, null, null);

		while(cursor.moveToNext())
    	{
    		Appointment app = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		String participant = cursor.getString(4);
    		String location = cursor.getString(5);
    		String start = cursor.getString(6);
    		String end = cursor.getString(7);
    		int repeat = cursor.getInt(8);
    		
    		try{   		
    			app = 
    			new Appointment(index, title, description, dateFormat.parse(lastUpdate)
    							, participant, location, dateFormat.parse(start), dateFormat.parse(end)
    							, repeat, arrayOfAppointment.size());
    			
    			arrayOfAppointment.add(app);
    		}
    		catch(Exception ex)
    		{
    			// ignore occurred exception
    			continue;
    		}
    	}
		
		db.close();
				
		return arrayOfAppointment;
	}
	
	public ArrayList<Appointment> getAppointments(Date startDate, Date endDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Appointment> arrayOfAppointment = new ArrayList<Appointment>();
		
		SQLiteDatabase db;
    	Cursor cursor = null;
    	
    	String startString = dateFormat.format(startDate);
    	String endString = dateFormat.format(endDate);

    	db = getReadableDatabase();
		cursor = db.query(TABLE_NAME, 
			new String[] {INDEX, TITLE, DESCRIPTION, LAST_UPDATE,
				PARTICIPANT, LOCATION, START_TIME, END_TIME, REPEAT}, 
				START_TIME + " BETWEEN " + startString + " AND " + endString  + startString, null, null, null, null);

		while(cursor.moveToNext())
    	{
    		Appointment app = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		String participant = cursor.getString(4);
    		String location = cursor.getString(5);
    		String start = cursor.getString(6);
    		String end = cursor.getString(7);
    		int repeat = cursor.getInt(8);
    		
    		try{   		
    			app = 
    			new Appointment(index, title, description, dateFormat.parse(lastUpdate)
    							, participant, location, dateFormat.parse(start), dateFormat.parse(end)
    							, repeat, arrayOfAppointment.size());
    			
    			arrayOfAppointment.add(app);
    		}
    		catch(Exception ex)
    		{
    			// ignore occurred exception
    			continue;
    		}
    	}
		
		db.close();
				
		return arrayOfAppointment;
	}
}
