package com.cmonkeys.mcalendar.view;

import com.cmonkeys.db.DaysDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class daystest extends Activity {
	/** Called when the activity is first created. */
	DaysDBHelper m_DaysHelper;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daystest); 
        
        m_DaysHelper = new DaysDBHelper(this);
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonDaysTestMakeDB:
    		break;
    	case R.id.buttonDaysTestBringHolidays:  
    		break;
    	case R.id.buttonDaysTestStoL: 
    		break;	
    	}
    }
}

