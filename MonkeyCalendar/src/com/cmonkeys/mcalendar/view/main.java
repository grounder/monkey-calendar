package com.cmonkeys.mcalendar.view;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.cmonkeys.db.DaysImportDBHelper;
import com.cmonkeys.mcalendar.R;
import com.cmonkeys.mcalendar.view.mkCalendarType1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class main extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startActivity(new Intent(this, logo.class));
    }
    
    public void onClick(View v) {
    	Intent intent;
    	switch (v.getId()) {
    	case R.id.buttonMonth_:
    		intent = new Intent(main.this, mkCalendarType1.class);
    		startActivity(intent);
    		break;    		
    	case R.id.buttonDay:
    		intent = new Intent(main.this, daystest.class);
    		startActivity(intent);
    		break;
    	case R.id.buttonNewDay:
    		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		intent = new Intent(main.this, day.class);
    		intent.putExtra("SelectedDay", dateTimeFormat.format(new Date()));
    		startActivity(intent);
    		break;
    	case R.id.buttonExit:
    		finish();
    		System.exit(0);
    		break;
    	}
    }
}
