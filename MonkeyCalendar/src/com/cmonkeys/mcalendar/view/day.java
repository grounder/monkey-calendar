package com.cmonkeys.mcalendar.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.Days;
import com.cmonkeys.db.DaysDBHelper;
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
	private ArrayList<Appointment> m_arrayOfAppointments;
	private AppointmentDBHelper m_appointmentDBHelper;
	private Date m_selectedDay;
	private TextView m_textViewTitle;
	
	LinearLayout m_listLayout;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        
        m_appointmentDBHelper = new AppointmentDBHelper(this);
        m_arrayOfAppointments = new ArrayList<Appointment>();
        
        m_listLayout = (LinearLayout)findViewById(R.id.linearLayoutDay);
        
        m_textViewTitle = (TextView)findViewById(R.id.textViewSeletedDay);
        
        Intent intent = getIntent();

    	try {
    		m_selectedDay = dateTimeFormat.parse(intent.getStringExtra("SelectedDay"));			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		update();
    }
    
    @Override
    public void onResume()
    {
    	update();
    	super.onResume();
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	/*
    	case R.id.buttonAddAppointment:
    		
    		break;
    	case R.id.buttonExitAppointment:
    		
    		
    		break;*/
    	}
    }
    
    private void clear()
    {
    	m_listLayout.removeAllViews();
    	m_arrayOfAppointments.clear();
    }
    
    private void update()
    {
    	TextView textViewToAdd;
    	Date timeOfSelected = (Date)m_selectedDay.clone();
    	String strDate = "" + (m_selectedDay.getYear() + 1900) + "/" + (m_selectedDay.getMonth() + 1) + "/" + m_selectedDay.getDate() + " (";
    	int currentDateInt = (m_selectedDay.getYear() + 1900) * 10000 + (m_selectedDay.getMonth() + 1) * 100 + m_selectedDay.getDate();
    	DaysDBHelper helper = new DaysDBHelper(this);
    	Days days = helper.getADay(currentDateInt);
    	if(days != null)
    	{
    		strDate += days.getLunarDateAsString() + ") " + days.getTitle();
	    	helper.close();
	    	
	    	m_textViewTitle.setText(strDate);
	    	if(days.getIsHoliday())
	    		m_textViewTitle.setTextColor(0xffff0000);
	    	else
	    		m_textViewTitle.setTextColor(0xffffffff);
    	}
    	clear();
    	addNewAppointment();
    	
    	m_arrayOfAppointments = m_appointmentDBHelper.getAppointments(timeOfSelected, timeOfSelected);
    	
    	for(Appointment app : m_arrayOfAppointments)
    	{
    		Calendar calCurrentAppointment = Calendar.getInstance();
			calCurrentAppointment.set(app.getStart().getYear() + 1900, app.getStart().getMonth()  , app.getStart().getDate());
			
    		// check
    		switch(app.getRepeat())
			{
			case 0: // No repeat
				if(app.getEnd().getDate() != m_selectedDay.getDate())
					continue;
				break;
			case 1: // Yearly
				if( (app.getStart().getMonth() != m_selectedDay.getMonth()) || 
					(app.getStart().getDate() != m_selectedDay.getDate()) )
					continue;
				break;
			case 2: // Monthly
				if(app.getStart().getDate() != m_selectedDay.getDate())
					continue;
				break;
			case 3: // Weekly
				int startDateInt = (app.getStart().getMonth() + 1) * 100 + app.getStart().getDate();
				int endDateInt = (app.getEnd().getMonth() + 1) * 100 + app.getEnd().getDate();
				currentDateInt = (m_selectedDay.getMonth() + 1)* 100 + m_selectedDay.getDate();
				
				if( startDateInt > currentDateInt || 
						currentDateInt > endDateInt)
					continue;
				
				int currentAppointmentDay = calCurrentAppointment.get(Calendar.DAY_OF_WEEK); 
				int currentDay = timeOfSelected.getDay() + 1;
				
				// if the day of week is same than add
				if(currentAppointmentDay != currentDay)
					continue;
				
				break;
			}
    		
    		final int indexOfCurrentAppointment = app.getIndex();
    		textViewToAdd = new TextView(this);
        	textViewToAdd.setPadding(10, 10, 10, 10);
        	textViewToAdd.setText(app.getTitle());
        	
        	textViewToAdd.setClickable(true);
        	textViewToAdd.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				Intent intent = new Intent(day.this, appointmenteditor.class);
					intent.putExtra("isNew", false);
					intent.putExtra("indexOfCurrent", indexOfCurrentAppointment);
					startActivity(intent);
    			}
    		});
        	
        	textViewToAdd.setTextSize(22);
        	textViewToAdd.setTextColor(0xffffffff);
        	
        	
        	
        	m_listLayout.addView(textViewToAdd);
    	}
    }

	private void addNewAppointment() {
		TextView textViewToAdd;
		textViewToAdd = new TextView(this);
    	textViewToAdd.setPadding(10, 10, 10, 10);
    	textViewToAdd.setText(getResources().getString(R.string.Add_appo));
    	
    	textViewToAdd.setClickable(true);
    	textViewToAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Show add appointment activity
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Intent intent = new Intent(day.this, appointmenteditor.class);
				intent.putExtra("isNew", true);
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
