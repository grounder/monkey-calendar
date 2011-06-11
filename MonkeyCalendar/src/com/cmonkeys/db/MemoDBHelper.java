package com.cmonkeys.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cmonkeys.mcalendar.view.memolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {

	static final String m_nameOfTable = "memo";
	static final String m_nameOfIndex = "_id";
	static final String m_nameOfTitle = "title";
	static final String m_nameOfDescription = "description";
	static final String m_nameOfLastUpdate = "lastUpdate";
	
	public MemoDBHelper(Context context) {
		super(context,"MKCal.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + m_nameOfTable + "(" + m_nameOfIndex + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ m_nameOfTitle + " TEXT, description TEXT, " + m_nameOfLastUpdate + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + m_nameOfTable);
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
    	
    	db = getWritableDatabase();
    	
    	row = new ContentValues();
    	row.put(m_nameOfTitle, title);
    	row.put(m_nameOfDescription, description);
    	row.put(m_nameOfLastUpdate, dateFormat.format(now));
    		     
    	db.insert(m_nameOfTable, null, row);
    	
    	close();
    }
	
	public void deleteMemo(int index)
	{
		SQLiteDatabase db;
		db = getWritableDatabase();
		db.delete(m_nameOfTable, m_nameOfIndex + "=" + index, null);
		close();
	}
	
	public void updateMemo(int index, String title, String description)
	{
		SQLiteDatabase db;
		ContentValues args = new ContentValues();
		
		
		args.put(m_nameOfTitle, title);
		args.put(m_nameOfDescription, description);
		
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
    			new String[] {m_nameOfIndex, m_nameOfTitle, m_nameOfDescription, m_nameOfLastUpdate}, 
    			null, null, null, null, null);
    	    	
    	return cursor;
    	
	}
}
