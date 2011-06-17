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
    		//DaysImportDBHelper db = new DaysImportDBHelper(this);
    		//db.close();
    		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		intent = new Intent(main.this, day.class);
    		intent.putExtra("SelectedDay", dateTimeFormat.format(new Date()));
    		startActivity(intent);
    	case R.id.buttonExit:
    		finish();
    		System.exit(0);
    		break;
    	}
    }
}
