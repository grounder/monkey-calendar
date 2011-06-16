package com.cmonkeys.db;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.res.AssetManager;

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
		super(context,"MKCal.db", null, 1);
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
	
	public void importEmbededData(Context context)
	{
		
        // Load days database
        // Holidays and lunar's dates are included in the database
        try{
        	// DB파일 패키지 설치 폴더에 복사
        	File outfile = new File(
        	  "/data/data/com.cmonkeys.mcalendar/databases/MKCalDays.db");

        	AssetManager assetManager = context.getResources().getAssets();
        	InputStream is = assetManager.open("MKCalDays.db",
        										AssetManager.ACCESS_BUFFER);
        	long filesize = is.available();

        	// 패키지 폴더에 설치된 DB파일이 포함된 DB파일 보다 크기가 작을 경우 DB파일을 덮어 쓴다.
        	if(outfile.length() < filesize){
	        	 byte[] tempdata = new byte[(int) filesize];
	        	 is.read(tempdata);
	        	 is.close();
	        	 outfile.createNewFile();
	        	 FileOutputStream fo = new FileOutputStream(outfile);
	        	 fo.write(tempdata);
	        	 fo.close();
        	}
        	
        	
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
	
	public void showSampleData()
	{
		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
    		cursor = db.query(m_nameOfTable, 
    			new String[] {m_nameOfIndex, m_nameOfSolarDate, 
    				m_nameOfLunarDate, m_nameOfNameOfHoliday,
    				m_nameOfIsHoliday, m_nameOfLastUpdate}, 
    				null, null, null, null, null);
    	
    	close();
	}
}
