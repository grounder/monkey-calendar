package com.cmonkeys.mcalendar.view;

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
    }
    
    public void mOnClick(View v) {
    	Intent intent;
    	switch (v.getId()) {
    	case R.id.buttonMonth:
    		intent = new Intent(main.this, mkCalendarType1.class);
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
    	case R.id.buttonExit:
    		finish();
    		System.exit(0);
    		break;
    	}
    }
    
    public void loadMemos()
    {
    	
    }
}
