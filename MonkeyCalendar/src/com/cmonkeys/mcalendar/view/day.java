package com.cmonkeys.mcalendar.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cmonkeys.mcalendar.view.appointmenteditor;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class day extends Activity {

	private Date m_selectedDay;
	
	LinearLayout m_listLayout;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        m_listLayout = (LinearLayout)findViewById(R.id.linearLayoutDay);
        
        Intent intent = getIntent();

    	try {
    		m_selectedDay = dateTimeFormat.parse(intent.getStringExtra("SelectedDay"));			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		update();
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
    
    private void update()
    {
    	TextView textViewToAdd = new TextView(this);
    	
    	textViewToAdd.setPadding(10, 10, 10, 10);
    	textViewToAdd.setText(getResources().getString(R.string.Add_appo));
    	
    	textViewToAdd.setClickable(true);
    	textViewToAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Show add appointment activity
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Intent intent = new Intent(day.this, appointmenteditor.class);
				intent.putExtra("SelectedDay", dateTimeFormat.format(m_selectedDay));
				startActivity(intent);
			}
		});
    	textViewToAdd.setTextSize(22);
    	textViewToAdd.setTextColor(0xffffff00);
    	textViewToAdd.setGravity(Gravity.CENTER);
    	m_listLayout.addView(textViewToAdd);
    	
    	    	
    }
}
