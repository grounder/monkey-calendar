package com.cmonkeys.mcalendar.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.cmonkeys.db.Article;
import com.cmonkeys.db.DaysDBHelper;
import com.cmonkeys.db.DaysImportDBHelper;
import com.cmonkeys.mcalendar.R;
import com.cmonkeys.mcalendar.view.mkCalendarType1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class main extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClick(View v) {
    	Intent intent;
    	switch (v.getId()) {
    	case R.id.buttonMonth:
    		intent = new Intent(main.this, mkCalendarType1.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonWeek:
    		intent = new Intent(main.this, week.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonAppointment:
    		intent = new Intent(main.this, appointmenttest.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonDay:
    		intent = new Intent(main.this, daystest.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonMemo:
    		intent = new Intent(main.this, memolist.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonFun:
    		intent = new Intent(main.this, fun.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonImportDB:
    		//DaysDBHelper db = new DaysDBHelper(this);
    		//db.importEmbededData(this);
    		DaysImportDBHelper db = new DaysImportDBHelper(this);
    		Cursor cursor = db.getCursor();
    		EditText ed = (EditText)findViewById(R.id.editTextMainLog);
    		
    		while(cursor.moveToNext())
        	{
        		int index = cursor.getInt(0);
        		int year = cursor.getInt(1);
        		
        		String line = "" + index + " : " + year;
        		
        		ed.setText(ed.getText().toString() + line + "\n");
        	}
    		
    		db.close();
    		break;
    	case R.id.buttonExit:
    		finish();
    		System.exit(0);
    		break;
    	}
    }
}
