package com.cmonkeys.mcalendar.view;

import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class appointment extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonAddAppointment:
    		
    		break;
    	case R.id.buttonExitAppointment:  
    		finish();
    		break;
    	}
    }    
}
