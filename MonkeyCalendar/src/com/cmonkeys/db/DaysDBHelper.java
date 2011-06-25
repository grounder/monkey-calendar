package com.cmonkeys.db;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.res.AssetManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaysDBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "MKCalFixed.db";
	public static final String TABLE_NAME = "Days";
	public static final String INDEX = "_id";
	public static final String SOLAR_DATE = "solarDate";
	public static final String LUNAR_DATE = "lunarDate";
	public static final String HOLYDAY_TITLE = "nameOfHoliday";
	public static final String IS_HOLIDAY = "isHoliday";
	public static final String LAST_UPDATE = "lastUpdate";

	public DaysDBHelper(Context context) 
	{
		super(context,DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ SOLAR_DATE + " INTEGER, " + LUNAR_DATE+ " INTEGER, "  + HOLYDAY_TITLE + " TEXT, " 
				+ IS_HOLIDAY + " INTEGER, " + LAST_UPDATE + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insertDay(Days app)
	{
		insertDay(app.getSolarDate(), app.getLunarDate(), app.getTitle(), app.getIsHoliday());
	}

	public void insertDay(int solarDate, int lunarDate, String nameOfHoliday, boolean isHoliday)
    {
    	// set the format to sql date time 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
       	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(SOLAR_DATE, solarDate);
    	row.put(LUNAR_DATE, lunarDate);
    	row.put(HOLYDAY_TITLE, nameOfHoliday);
    	row.put(IS_HOLIDAY, isHoliday);
    	row.put(LAST_UPDATE, dateFormat.format(now));
    		     
    	db.insert(TABLE_NAME, null, row);
    	
    	db.close();
    }	
	
	public void deleteAllDays()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, "", null);
		db.close();
	}
	
	public void deleteDay(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(TABLE_NAME, INDEX + "=" + index, null);
		db.close();
	}

	public void updateDay(int index, Date solarDate, Date lunarDate, String nameOfHoliday, boolean isHoliday)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
		Date now = new Date();
				
		args.put(SOLAR_DATE, dateFormat.format(solarDate));
		args.put(LUNAR_DATE, dateFormat.format(lunarDate));
		args.put(HOLYDAY_TITLE, nameOfHoliday);
		args.put(IS_HOLIDAY, isHoliday);
		args.put(LAST_UPDATE, dateFormat.format(now));
		
		db = getWritableDatabase();
		db.update(TABLE_NAME, args, INDEX + "=" + index, null);
 
		db.close();
	}
	
	public Days getADay(int selectedSolarDate)
	{
		Days days = null;
		
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, 
				new String[]{SOLAR_DATE, LUNAR_DATE, HOLYDAY_TITLE, IS_HOLIDAY}, 
							SOLAR_DATE + " = " + selectedSolarDate, null, null, null, null);
		
		while(cursor.moveToNext())
		{
			int solarDate = cursor.getInt(0);
			int lunarDate = cursor.getInt(1);
			String title = cursor.getString(2);
			int holidayType = cursor.getInt(3);
			boolean isHoliday = false;
			if(holidayType > 0) isHoliday = true;
			days = new Days(solarDate, lunarDate, title, isHoliday);
		}
		
		cursor.close();
		db.close();
		
		return days;
	}
	
	public ArrayList<Days> getDays(int startDate, int endDate)
	{
		ArrayList<Days> arrayOfDays = new ArrayList<Days>();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, 
				new String[]{SOLAR_DATE, LUNAR_DATE, HOLYDAY_TITLE, IS_HOLIDAY}, 
							SOLAR_DATE + " BETWEEN " + startDate + " AND " +endDate, 
							null, null, null, null);
		while(cursor.moveToNext())
		{
			Days days = null;
			int solarDate = cursor.getInt(0);
			int lunarDate = cursor.getInt(1);
			String title = cursor.getString(2);
			int holidayType = cursor.getInt(3);
			boolean isHoliday = false;
			if(holidayType > 0) isHoliday = true;
			days = new Days(solarDate, lunarDate, title, isHoliday);
			arrayOfDays.add(days);
		}
		
		cursor.close();
		db.close();

		return arrayOfDays;
	}
	
	public void importEmbededData(Context context)
	{
		deleteAllDays();
		
		// Load days database
        // Holidays and lunar's dates are included in the database
        try{
        	AssetManager assetManager = context.getResources().getAssets();
        	InputStream is = assetManager.open("lunar.csv",
        										AssetManager.ACCESS_BUFFER);
        	
        	InputStreamReader isr = new InputStreamReader(is,"euc-kr");
        	BufferedReader br = new BufferedReader(isr);
        	String line;
        	
        	while((line = br.readLine()) != null)
        	{
        		String[] cols = line.split(",");
        		String strSolarDate = cols[0];
        		if(cols[1].length() == 1)
        			strSolarDate += "0" + cols[1];
        		else
        			strSolarDate += cols[1];
    			
        		if(cols[2].length() == 1)
        			strSolarDate += "0" + cols[2];
        		else
        			strSolarDate += cols[2];
        		
        		String strLunarDate = cols[3];
        		if(cols[4].length() == 1)
        			strLunarDate += "0" + cols[4];
        		else
        			strLunarDate += cols[4];
    			
        		if(cols[5].length() == 1)
        			strLunarDate += "0" + cols[5];
        		else
        			strLunarDate += cols[5];
        		
    			String strHolidayTitle = cols[6];
    			
    			if(cols[6].length() > 1 && cols[7].length() > 1)
    				strHolidayTitle += "," + cols[7];
    			else
    				strHolidayTitle += cols[7];
    			
    			int holidayType = Integer.parseInt(cols[8]);
    			boolean isHoliday = false;
    			if(holidayType == 1 || holidayType == 2 || holidayType == 3)
    				isHoliday = true;
    			
    			insertDay(Integer.parseInt(strSolarDate), Integer.parseInt(strLunarDate), strHolidayTitle, isHoliday);
        	}
        	
        	br.close();
        	isr.close();
        	is.close();
        }
        catch(java.io.IOException ex)
        {
        	// Show error message
        	AlertDialog.Builder bld = new AlertDialog.Builder(context);
        	bld.setTitle("Warnning");
        	bld.setMessage(ex.getMessage());
        	bld.setPositiveButton("Dismiss", new DialogInterface.OnClickListener(){ 
        		@Override
    			public void onClick(DialogInterface dialog, int which) {
    			}});
        	bld.show();
        }
        catch(Exception ex)
        {
        	// Show error message
        	AlertDialog.Builder bld = new AlertDialog.Builder(context);
        	bld.setTitle("Warnning");
        	bld.setMessage("Loading a fixed database is failed");
        	bld.setPositiveButton("Dismiss", new DialogInterface.OnClickListener(){ 
        		@Override
    			public void onClick(DialogInterface dialog, int which) {
    			}});
        	bld.show();
        }
	}
	
	public void importEmbededData2(Context context)
	{
		// Load days database
        // Holidays and lunar's dates are included in the database
		try{
	       	 // DB파일 패키지 설치 폴더에 복사
	       	File outfile = new File("/data/data/com.cmonkeys.mcalendar/databases/MKCalFixed.db");
			AssetManager assetManager = context.getResources().getAssets();
        	InputStream is = assetManager.open("MKCalFixed.db",
        										AssetManager.ACCESS_BUFFER);
        	long filesize = is.available();

    		// 패키지 폴더에 설치된 DB파일이 포함된 DB파일 보다 크기가 작을 경우 DB파일을 덮어 쓴다.
        	byte[] tempdata = new byte[(int) filesize];
        	is.read(tempdata);
        	is.close();

        	outfile.delete();
        	outfile.createNewFile();
        	FileOutputStream fo = new FileOutputStream(outfile);
        	fo.write(tempdata);
        	fo.close();
    	
        }
        catch(IOException ex)
        {
        	// Show error message
        	AlertDialog.Builder bld = new AlertDialog.Builder(context);
        	bld.setTitle("Warnning");
        	bld.setMessage(ex.getMessage());
        	bld.setPositiveButton("Dismiss", new DialogInterface.OnClickListener(){ 
        		@Override
    			public void onClick(DialogInterface dialog, int which) {
    			}});
        	bld.show();
        }
        catch(Exception ex)
        {
        	// Show error message
        	AlertDialog.Builder bld = new AlertDialog.Builder(context);
        	bld.setTitle("Warnning");
        	bld.setMessage("Loading a fixed database is failed");
        	bld.setPositiveButton("Dismiss", new DialogInterface.OnClickListener(){ 
        		@Override
    			public void onClick(DialogInterface dialog, int which) {
    			}});
        	bld.show();
        }
	}
}
