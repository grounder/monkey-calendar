package com.cmonkeys.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "MKCal.db";
	static final String TABLE_NAME = "memo";
	static final String INDEX = "_id";
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LAST_UPDATE = "lastUpdate";
	
	public MemoDBHelper(Context context) {
		super(context,DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TITLE + " TEXT, " + DESCRIPTION +" TEXT, " + LAST_UPDATE + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void insertMemo(Article art)
	{
		insertMemo(art.getTitle(), art.getDescription());
	}
	
	public void insertMemo(String title, String description)
    {
    	// set the format to sql date time 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SQLiteDatabase db;
    	ContentValues row;
    	Date now = new Date();
    	
    	// title is neccasary
    	if(title == "") return;
    	
    	db = getWritableDatabase();
    	
    	// Create memo article
    	row = new ContentValues();
    	row.put(TITLE, title);
    	row.put(DESCRIPTION, description);
    	row.put(LAST_UPDATE, dateFormat.format(now));
    		     
    	db.insert(TABLE_NAME, null, row);
    	
    	db.close();
    }
	
	public void deleteMemo(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(TABLE_NAME, INDEX + "=" + index, null);
		db.close();
	}
	
	public void updateMemo(int index, String title, String description)
	{
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
		
		args.put(TITLE, title);
		args.put(DESCRIPTION, description);
		
		db = getWritableDatabase();
		db.update(TABLE_NAME, args, INDEX + "=" + index, null);
 
		db.close();
	}
	
	public ArrayList<Article> getAllMemos()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Article> arrayOfMemos = new ArrayList<Article>();

		SQLiteDatabase db;
    	Cursor cursor = null;

    	db = getReadableDatabase();
		cursor = db.query(TABLE_NAME, 
			new String[] {INDEX, TITLE, DESCRIPTION, LAST_UPDATE}, 
			null, null, LAST_UPDATE + " DESC", null, null);
		
		while(cursor.moveToNext())
    	{
    		Article art = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		
    		try{
    			art = new Article(index, title, description, dateFormat.parse(lastUpdate), arrayOfMemos.size());
    			arrayOfMemos.add(art);
    		}
    		catch(Exception ex)
    		{
    			// ignore occurred exception
    			continue;
    		}
    	}
		
		cursor.close();
		db.close();
		
		return arrayOfMemos;
	}
}
