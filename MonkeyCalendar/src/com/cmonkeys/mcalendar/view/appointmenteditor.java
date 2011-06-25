package com.cmonkeys.mcalendar.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.RadioGroup;
import android.widget.Toast;


import android.app.DatePickerDialog;


public class appointmenteditor extends Activity implements RadioGroup.OnCheckedChangeListener{
	private Context m_context;
	private EditText m_editTextTitle;
	private EditText m_editTextDescription;
	private EditText m_editTextParticipant;
	private EditText m_editTextLocation;
	private Button m_buttonStartDate;
	private Button m_buttonStartTime;
	private Button m_buttonEndDate;
	private Button m_buttonEndTime;
	
	private Date m_startDate;
	private Date m_endDate;
	private boolean m_isNewAppointement;
	
	private int m_indexOfCurrentAppointment;
	
	SimpleDateFormat m_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat m_timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmenteditor);
        m_context = this;

        m_editTextTitle = (EditText)findViewById(R.id.editTextAppointmentEditTitle);
        m_editTextDescription = (EditText)findViewById(R.id.editTextAppointmentEditDescription);
        m_editTextParticipant = (EditText)findViewById(R.id.editTextAppointmentEditParticipant);
        m_editTextLocation = (EditText)findViewById(R.id.editTextAppointmentEditLocation);
        
        m_buttonStartDate = (Button)findViewById(R.id.buttonStartDate);
        m_buttonStartTime = (Button)findViewById(R.id.buttonStartTime);
        m_buttonEndDate = (Button)findViewById(R.id.buttonEndDate);
        m_buttonEndTime = (Button)findViewById(R.id.buttonEndTime);
        
        m_editTextTitle.requestFocus();
        
        m_startDate = new Date();
        m_endDate = new Date();
                      
        Intent intent = getIntent();
        m_isNewAppointement = intent.getBooleanExtra("isNew", true);
        
        ((RadioButton)findViewById(R.id.radioDaily)).setVisibility(RadioButton.INVISIBLE);

    	try {
    		if(m_isNewAppointement)
    		{
    			Date now = new Date();
    			
	    		m_startDate = m_dateFormat.parse(intent.getStringExtra("SelectedDay"));
	    		m_buttonStartDate.setText(m_dateFormat.format(m_startDate));
	    		m_buttonStartTime.setText(m_timeFormat.format(now));
	    		
	    		m_buttonEndDate.setText(m_dateFormat.format(m_endDate));
	    		m_buttonEndTime.setText(m_timeFormat.format(now));
	    		m_indexOfCurrentAppointment = -1;
	    		
    		}
    		else
    		{
    			AppointmentDBHelper helper = new AppointmentDBHelper(this);
    			m_indexOfCurrentAppointment = intent.getIntExtra("indexOfCurrent", 1);
    			Appointment currentAppointment = helper.getAppointment(m_indexOfCurrentAppointment); 
    			helper.close();
    			
    			if (currentAppointment != null)
    			{
	    			m_editTextTitle.setText(currentAppointment.getTitle());
	    			m_editTextDescription.setText(currentAppointment.getDescription());
	    			m_editTextParticipant.setText(currentAppointment.getParticipants());
	    			m_editTextLocation.setText(currentAppointment.getLocation());
	    			m_startDate = currentAppointment.getStart();
	    			m_endDate = currentAppointment.getEnd();
	    			
	    			switch(currentAppointment.getRepeat())
	    			{
	    			case 0:
	    				((RadioButton)findViewById(R.id.radioNoRepeat)).setChecked(true);
	    				break;
	    			case 1:
	    				((RadioButton)findViewById(R.id.radioYearly)).setChecked(true);
	    				break;
	    			case 2:
	    				((RadioButton)findViewById(R.id.radioMonthly)).setChecked(true);
	    				break;
	    			case 3:
	    				((RadioButton)findViewById(R.id.radioDaily)).setChecked(true);
	    				break;
	    			}
	    			
		    		m_buttonStartDate.setText(m_dateFormat.format(m_startDate));
		    		m_buttonStartTime.setText(m_timeFormat.format(m_startDate));
		    		
		    		m_buttonEndDate.setText(m_dateFormat.format(m_endDate));
		    		m_buttonEndTime.setText(m_timeFormat.format(m_endDate));
    			}
    		}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}        
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.radioNoRepeat:
    		//m_buttonEndDate.setVisibility(Button.VISIBLE);
    		break;
    	case R.id.radioYearly:
    		//m_buttonEndDate.setVisibility(Button.INVISIBLE);
    		break;
    	case R.id.radioMonthly:
    		//m_buttonEndDate.setVisibility(Button.INVISIBLE);
    		break;
    	case R.id.radioDaily:
    		//m_buttonEndDate.setVisibility(Button.INVISIBLE);
    		break;
    	case R.id.buttonSaveAppointment:
    		String title = m_editTextTitle.getText().toString(); 
    		if(title.length() > 2)
    		{
	    		saveAppointment();
	    		finish();
    		}
    		else
    			Toast.makeText(m_context, "Please input title", Toast.LENGTH_SHORT).show();
    		break;
    	case R.id.buttonCancelAndCloseAppointment:  
    		finish();
    		break;
    	}
    }
    
    public void onStartDateClick(View v)
    {
    	DatePickerDialog dateDlg = new DatePickerDialog(this, m_startDateSetListener, m_startDate.getYear() + 1900, m_startDate.getMonth(), m_startDate.getDate() );
    	dateDlg.show();
    }
    
    public void onStartTimeClick(View v) throws ParseException
    {
    	Date time = m_timeFormat.parse(m_buttonStartTime.getText().toString());
    	TimePickerDialog timeDlg = new TimePickerDialog(this, m_startTimeSetListener, time.getHours(), time.getMinutes(), true);
    	timeDlg.show();
    }

    public void onEndDateClick(View v)
    {
    	DatePickerDialog dateDlg = new DatePickerDialog(this, m_endDateSetListener, m_endDate.getYear() + 1900, m_endDate.getMonth(), m_endDate.getDate() );
    	dateDlg.show();
    }
    
    public void onEndTimeClick(View v) throws ParseException
    {
    	Date time = m_timeFormat.parse(m_buttonEndTime.getText().toString());
    	TimePickerDialog timeDlg = new TimePickerDialog(this, m_endTimeSetListener, time.getHours(), time.getMinutes(), true);
    	timeDlg.show();
    }
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener m_startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                	
                    m_startDate.setYear(year - 1900);
                    m_startDate.setMonth(monthOfYear);
                    m_startDate.setDate(dayOfMonth);
                    
                    if(m_startDate.getTime() > m_endDate.getTime())
                    {
                    	m_endDate = (Date)m_startDate.clone();
                    	m_buttonEndDate.setText(m_dateFormat.format(m_endDate));
                    	m_buttonEndTime.setText(m_timeFormat.format(m_endDate));
                    }
                    
                    m_buttonStartDate.setText(m_dateFormat.format(m_startDate));
                }
            };
            
    private TimePickerDialog.OnTimeSetListener m_startTimeSetListener = 
    	new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				m_startDate.setHours(hourOfDay);
				m_startDate.setMinutes(minute);
				
				if(m_startDate.getTime() > m_endDate.getTime())
                {
                	m_endDate = (Date)m_startDate.clone();
                	m_buttonEndDate.setText(m_dateFormat.format(m_endDate));
                	m_buttonEndTime.setText(m_timeFormat.format(m_endDate));
                }
				
				m_buttonStartTime.setText(m_timeFormat.format(m_startDate));
			}
		};
            
    private DatePickerDialog.OnDateSetListener m_endDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
            	Date tempDate = (Date)m_endDate.clone();
            	
            	tempDate.setYear(year - 1900);
            	tempDate.setMonth(monthOfYear);
            	tempDate.setDate(dayOfMonth);
            	
            	if(tempDate.getTime() < m_startDate.getTime())
            	{
            		Toast.makeText(m_context, "The date is not valid", Toast.LENGTH_SHORT).show();
            	}
            	else
            	{
            		m_endDate.setYear(year - 1900);
                	m_endDate.setMonth(monthOfYear);
                	m_endDate.setDate(dayOfMonth);
            	}
            	
            	m_buttonEndDate.setText(m_dateFormat.format(m_endDate));
            }
        };
    
    private TimePickerDialog.OnTimeSetListener m_endTimeSetListener = 
    	new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if( ((RadioButton)findViewById(R.id.radioNoRepeat)).isChecked())
				{
					Date tempDate = (Date)m_endDate.clone();
					
					tempDate.setHours(hourOfDay);
					tempDate.setMinutes(minute);
					
					if(tempDate.getTime() < m_startDate.getTime())
	            	{
	            		Toast.makeText(m_context, "The time is not valid", Toast.LENGTH_SHORT).show();
	            	}
	            	else
	            	{
	            		m_endDate.setHours(hourOfDay);
	    				m_endDate.setMinutes(minute);
	            	}
					
					m_buttonEndTime.setText(m_timeFormat.format(m_endDate));
				}				
				else
				{
					Date tempDate = (Date)m_startDate.clone();
					
					tempDate.setHours(hourOfDay);
					tempDate.setMinutes(minute);
					
					if(tempDate.getTime() < m_startDate.getTime())
	            	{
	            		Toast.makeText(m_context, "The time is not valid", Toast.LENGTH_SHORT).show();
	            	}
	            	else
	            	{
	            		m_endDate.setHours(hourOfDay);
	    				m_endDate.setMinutes(minute);
	            	}
					
					m_buttonEndTime.setText(m_timeFormat.format(m_endDate));
				}
			}
		};
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}
	
    private Appointment getAppointment()
    {
    	int index = m_indexOfCurrentAppointment;
    	String title = m_editTextTitle.getText().toString();
		String description = m_editTextDescription.getText().toString();
		String participant = m_editTextParticipant.getText().toString();
		String location = m_editTextLocation.getText().toString();
		
		int repeat = 0;
		
		if( ((RadioButton)findViewById(R.id.radioYearly)).isChecked() )
			repeat = 1;
		else if( ((RadioButton)findViewById(R.id.radioMonthly)).isChecked() )
			repeat = 2;
		else if( ((RadioButton)findViewById(R.id.radioDaily)).isChecked() )
			repeat = 3;
		
    	return new Appointment(index, title, description, new Date(), 
				participant, location,m_startDate, m_endDate,
				repeat, 0);
    }
    
    private void saveAppointment()
    {
    	AppointmentDBHelper dbHelper = new AppointmentDBHelper(this);
    	Appointment newApp = getAppointment();
    	
    	if(dbHelper.isOverlappedAppointment(newApp))
    		Toast.makeText(m_context, "Overlapped appointment found", Toast.LENGTH_SHORT).show();
    	
		if(m_isNewAppointement)
			dbHelper.insertAppointment(newApp);
		else if(m_indexOfCurrentAppointment >= 0)
			dbHelper.updateAppointment(newApp);

		dbHelper.close();
    }

	
}
