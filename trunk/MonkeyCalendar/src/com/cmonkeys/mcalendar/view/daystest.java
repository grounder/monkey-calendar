package com.cmonkeys.mcalendar.view;

import com.cmonkeys.db.AppointmentDBHelper;
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
    
    public void mOnClick(View v) {
    	switch (v.getId()) {
    	//case R.id.buttonExitMemoList:
    		//break;
    	//case R.id.buttonNewMemo:    		
    		//break;
    	}
    }
}
