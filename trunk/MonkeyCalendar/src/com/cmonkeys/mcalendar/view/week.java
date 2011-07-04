package com.cmonkeys.mcalendar.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.Days;
import com.cmonkeys.db.DaysDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class week extends Activity {
	private Calendar m_calendar;
	private TextView m_textViewDates[];
	private TextView m_textViewDateTitles[];
	private TextView m_textViewAppointments[];
	private TextView m_textViewAppointmentsTitle[];
		
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);
        
        m_textViewDates = new TextView[7];
        m_textViewDateTitles = new TextView[7];
        m_textViewAppointments = new TextView[7];
        m_textViewAppointmentsTitle = new TextView[7];
        
        m_textViewDates[0] = (TextView)findViewById(R.id.textViewWeek1Date);
        m_textViewDateTitles[0] = (TextView)findViewById(R.id.textViewWeek1DateTitle);
        m_textViewAppointments[0] = (TextView)findViewById(R.id.textViewWeek1DateAppointments);
        m_textViewAppointmentsTitle[0] = (TextView)findViewById(R.id.textViewWeek1DateAppointmentTitles);
        
        m_textViewDates[1] = (TextView)findViewById(R.id.textViewWeek2Date);
        m_textViewDateTitles[1] = (TextView)findViewById(R.id.textViewWeek2DateTitle);
        m_textViewAppointments[1] = (TextView)findViewById(R.id.textViewWeek2DateAppointments);
        m_textViewAppointmentsTitle[1] = (TextView)findViewById(R.id.textViewWeek2DateAppointmentTitles);
        
        m_textViewDates[2] = (TextView)findViewById(R.id.textViewWeek3Date);
        m_textViewDateTitles[2] = (TextView)findViewById(R.id.textViewWeek3DateTitle);
        m_textViewAppointments[2] = (TextView)findViewById(R.id.textViewWeek3DateAppointments);
        m_textViewAppointmentsTitle[2] = (TextView)findViewById(R.id.textViewWeek3DateAppointmentTitles);
        
        m_textViewDates[3] = (TextView)findViewById(R.id.textViewWeek4Date);
        m_textViewDateTitles[3] = (TextView)findViewById(R.id.textViewWeek4DateTitle);
        m_textViewAppointments[3] = (TextView)findViewById(R.id.textViewWeek4DateAppointments);
        m_textViewAppointmentsTitle[3] = (TextView)findViewById(R.id.textViewWeek4DateAppointmentTitles);
        
        m_textViewDates[4] = (TextView)findViewById(R.id.textViewWeek5Date);
        m_textViewDateTitles[4] = (TextView)findViewById(R.id.textViewWeek5DateTitle);
        m_textViewAppointments[4] = (TextView)findViewById(R.id.textViewWeek5DateAppointments);
        m_textViewAppointmentsTitle[4] = (TextView)findViewById(R.id.textViewWeek5DateAppointmentTitles);
        
        m_textViewDates[5] = (TextView)findViewById(R.id.textViewWeek6Date);
        m_textViewDateTitles[5] = (TextView)findViewById(R.id.textViewWeek6DateTitle);
        m_textViewAppointments[5] = (TextView)findViewById(R.id.textViewWeek6DateAppointments);
        m_textViewAppointmentsTitle[5] = (TextView)findViewById(R.id.textViewWeek6DateAppointmentTitles);
        
        m_textViewDates[6] = (TextView)findViewById(R.id.textViewWeek7Date);
        m_textViewDateTitles[6] = (TextView)findViewById(R.id.textViewWeek7DateTitle);
        m_textViewAppointments[6] = (TextView)findViewById(R.id.textViewWeek7DateAppointments);
        m_textViewAppointmentsTitle[6] = (TextView)findViewById(R.id.textViewWeek7DateAppointmentTitles);
                
        // Get selected day or set to now
        Date now = new Date();
        Integer nowInt = (now.getYear() + 1900) * 10000 + (now.getMonth() + 1) * 100 + now.getDate();
        Date selectedDate;
        Integer selectedDateInt;
        Intent intent = getIntent();
        
        selectedDateInt = intent.getIntExtra("SelectedDay", nowInt);
        
        if(selectedDateInt == nowInt)
        	selectedDate = now;
        else
        {
        	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd");
        	try{
        		selectedDate = dateTimeFormat.parse(selectedDateInt.toString());
        	}
        	catch(Exception ex)
        	{
        		selectedDate = now;
        	}
        }
        
        m_calendar = Calendar.getInstance();
        m_calendar.set(selectedDate.getYear() + 1900, selectedDate.getMonth(), selectedDate.getDate());
        
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
    	case R.id.textViewWeekPrev:
    		moveToPrevWeek();
    		break;
    	case R.id.textViewWeekNext:  
    		moveToNextWeek();
    		
    		break;
    	}
    }
    
    private void moveToNextWeek()
    {
    	m_calendar.add(Calendar.DATE, 7);
    	update();
    }
    
    private void moveToPrevWeek()
    {
    	m_calendar.add(Calendar.DATE, -7);
    	update();
    }
    
    private void update()
    {
    	switch(m_calendar.get(Calendar.DAY_OF_WEEK))
    	{
    	case Calendar.SUNDAY:
    		break;
    	case Calendar.MONDAY:
    		m_calendar.add(Calendar.DATE, -1);
    		break;
    	case Calendar.TUESDAY:
    		m_calendar.add(Calendar.DATE, -2);
    		break;
    	case Calendar.WEDNESDAY:
    		m_calendar.add(Calendar.DATE, -3);
    		break;
    	case Calendar.THURSDAY:
    		m_calendar.add(Calendar.DATE, -4);
    		break;
    	case Calendar.FRIDAY:
    		m_calendar.add(Calendar.DATE, -5);
    		break;
    	case Calendar.SATURDAY:
    		m_calendar.add(Calendar.DATE, -6);
    		break;
    	}
    	
    	Calendar calForHoliday = (Calendar)m_calendar.clone();
		int currentStartDate = calForHoliday.get( Calendar.YEAR ) * 10000 + (calForHoliday.get( Calendar.MONTH ) + 1)* 100 + calForHoliday.get(Calendar.DAY_OF_MONTH);
		calForHoliday.add(Calendar.DATE, 6);
		int currentEndDate = calForHoliday.get( Calendar.YEAR ) * 10000 + (calForHoliday.get( Calendar.MONTH ) + 1) * 100 + calForHoliday.get(Calendar.DAY_OF_MONTH);
		
		DaysDBHelper helperForDays = new DaysDBHelper(this);
		ArrayList<Days> arrayOfThisMonth = helperForDays.getDays(currentStartDate, currentEndDate);
		helperForDays.close();
		AppointmentDBHelper helper = new AppointmentDBHelper(this);
		Calendar untilDate = (Calendar)m_calendar.clone();
		untilDate.add(Calendar.DATE, 6);
		
		Date firstOfThisWeek = new Date(m_calendar.get(Calendar.YEAR) - 1900 ,m_calendar.get(Calendar.MONTH),m_calendar.get(Calendar.DATE) ,0,0);
		Date endOfThisWeek = new Date(untilDate.get(Calendar.YEAR) - 1900 ,untilDate.get(Calendar.MONTH),untilDate.get(Calendar.DATE) ,0,0);
		ArrayList<Appointment> apps = helper.getAppointments(firstOfThisWeek, endOfThisWeek);
		
    	for(int week = 0; week < 7; week++)
    	{
    		////////////////////////////////////////////////////////////////////////////
    		//  - Common stuffs
    		// Add date string to the item's date label
    		String strDate = "" + getResources().getStringArray(R.array.MonthShort)[m_calendar.get(Calendar.MONTH)] + "." + m_calendar.get(Calendar.DATE);
    		m_textViewDates[week].setText(strDate);
    		
    		if(arrayOfThisMonth.size() == 7)
    		{
	    		if(arrayOfThisMonth.get(week).getIsHoliday())
	    			m_textViewDates[week].setTextColor(0xffff0000);
	    		else 
	    			m_textViewDates[week].setTextColor(0xffffffff);
	    		
	    		m_textViewDateTitles[week].setText(arrayOfThisMonth.get(week).getTitle());
    		}
    		m_textViewAppointments[week].setText("(" + apps.size() + ")");
    		
    		String titlesOfDay = "";
    		// - Until here
    		/////////////////////////////////////////////////////////////////////////////
    		
    		Date today = new Date(m_calendar.get(Calendar.YEAR) - 1900 ,m_calendar.get(Calendar.MONTH),m_calendar.get(Calendar.DATE) ,0,0);
    		int currentApp = 0;
    		
    		for(Appointment app : apps)
    		{
    			Calendar calCurrentAppointment = Calendar.getInstance();
				calCurrentAppointment.set(app.getStart().getYear() + 1900, app.getStart().getMonth()  , app.getStart().getDate());
				
    			// check
        		switch(app.getRepeat())
    			{
    			case 0: // No repeat
    				if(app.getEnd().getDate() == today.getDate())
    				{
    					if(currentApp != 0) titlesOfDay += ", ";
            			titlesOfDay += app.getTitle();
            			currentApp++;
    				}
    				break;
    			case 1: // Yearly
    				if( (app.getStart().getMonth() == today.getMonth()) && 
    					(app.getStart().getDate() == today.getDate()) )
    				{
    					if(currentApp != 0) titlesOfDay += ", ";
            			titlesOfDay += app.getTitle();
            			currentApp++;
    				}
    				break;
    			case 2: // Monthly
    				if(app.getStart().getDate() == today.getDate())
    				{
      					if(currentApp != 0) titlesOfDay += ", ";
                		titlesOfDay += app.getTitle();
                		currentApp++;
    				}
    				break;
    			case 3: // Weekly
    				int currentDateInt = (m_calendar.get(Calendar.MONTH) + 1) * 100 + m_calendar.get(Calendar.DATE);
    				
					int startDateInt = (app.getStart().getMonth() + 1) * 100 + app.getStart().getDate();
					int endDateInt = (app.getEnd().getMonth() + 1) * 100 + app.getEnd().getDate();
					
					if( startDateInt > currentDateInt || 
							currentDateInt > endDateInt)
						continue;
					
					int currentAppointmentDay = calCurrentAppointment.get(Calendar.DAY_OF_WEEK); 
					int currentDay = m_calendar.get(Calendar.DAY_OF_WEEK);
					
					// if the day of week is same than add
					if(currentAppointmentDay == currentDay)
					{
						if(currentApp != 0) titlesOfDay += ", ";
                		titlesOfDay += app.getTitle();
                		currentApp++;
					}					
    				break;
    			}
    		}
    		
    		if(titlesOfDay.length() < 2)
    			titlesOfDay = "No appointment";

    		m_textViewAppointmentsTitle[week].setText(titlesOfDay);
    		m_calendar.add(Calendar.DATE, 1);
    	}
    	
    	helper.close();
    	
    	m_calendar.add(Calendar.DATE, -6);
    }
}
