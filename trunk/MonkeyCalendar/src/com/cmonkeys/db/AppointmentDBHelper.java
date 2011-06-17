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
	public static final String START_DATE = "start_date";
	public static final String START_TIME = "start_time";
	public static final String END_DATE = "end_date";
	public static final String END_TIME = "end_time";
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
				+ START_DATE + " INTEGER, " + START_TIME + " INTEGER, " 
				+ END_DATE + " INTERGER," + END_TIME + " INTEGER,"
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
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(TITLE, title);
    	row.put(DESCRIPTION, description);
    	row.put(LAST_UPDATE, dateTimeFormat.format(now));
    	row.put(PARTICIPANT, participant);
    	row.put(LOCATION, location);
    	row.put(START_DATE, Integer.parseInt(dateFormat.format(startTime)));
    	row.put(START_TIME, Integer.parseInt(timeFormat.format(startTime)));
    	row.put(END_DATE, Integer.parseInt(dateFormat.format(endTime)));
    	row.put(END_TIME, Integer.parseInt(timeFormat.format(endTime)));
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
		// set the format to sql date time 
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
    	SQLiteDatabase db;
    	ContentValues row = new ContentValues();
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row.put(TITLE, title);
    	row.put(DESCRIPTION, description);
    	row.put(LAST_UPDATE, dateTimeFormat.format(now));
    	row.put(PARTICIPANT, participant);
    	row.put(LOCATION, location);
    	row.put(START_DATE, Integer.parseInt(dateFormat.format(starttime)));
    	row.put(START_TIME, Integer.parseInt(timeFormat.format(starttime)));
    	row.put(END_DATE, Integer.parseInt(dateFormat.format(finishtime)));
    	row.put(END_TIME, Integer.parseInt(timeFormat.format(finishtime)));
    	row.put(REPEAT, repeat);
				
		db = getWritableDatabase();
		db.update(TABLE_NAME, row, INDEX + "=" + index, null);
 
		db.close();
	}

	public ArrayList<Appointment> getAllAppointments()
	{
		return getAppointments(new Date(1950,1,1,0,0), new Date(2050,12,31,24,0));
	}
	
	private Date getDateFromInteger(int nDate, int nTime)
	{
		int year = nDate / 10000;
		int month = (nDate - (year * 10000)) / 100;
		int day = (nDate - (year * 10000) - (month * 100));
		
		int hour = nTime / 100;
		int min = nTime - (hour * 100);
		
		return new Date(year, month, day, hour, min);
	}
	
	public ArrayList<Appointment> getAppointments(Date selectedStart, Date selectedEnd)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Appointment> arrayOfAppointment = new ArrayList<Appointment>();
		int selectedStartInt = (selectedStart.getYear() * 10000) 
		 					+ (selectedStart.getMonth() * 100)
							+ (selectedStart.getDate());
		int selectedEndInt = (selectedEnd.getYear() * 10000) 
							+ (selectedEnd.getMonth() * 100)
							+ (selectedEnd.getDate());
		
		SQLiteDatabase db = getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, 
    					new String[] {INDEX, TITLE, DESCRIPTION, LAST_UPDATE,
									PARTICIPANT, LOCATION, START_DATE, START_TIME, 
									END_DATE, END_TIME, REPEAT}
									, START_DATE + " BETWEEN " + selectedStartInt + " AND " + selectedEndInt
									, null, null, null, null);

		while(cursor.moveToNext())
    	{
    		Appointment app = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		String participant = cursor.getString(4);
    		String location = cursor.getString(5);
    		Integer startDate = cursor.getInt(6);
    		Integer startTime = cursor.getInt(7);
    		Integer endDate = cursor.getInt(8);
    		Integer endTime = cursor.getInt(9);
    		Integer repeat = cursor.getInt(10);
    		    		   		    		
    		Date start = getDateFromInteger(startDate, startTime);
    		Date end = getDateFromInteger(endDate, endTime);
    		
    		try{   		
    			app = 
    				new Appointment(index, title, description
    							, dateFormat.parse(lastUpdate)
    							, participant, location, start, end
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
