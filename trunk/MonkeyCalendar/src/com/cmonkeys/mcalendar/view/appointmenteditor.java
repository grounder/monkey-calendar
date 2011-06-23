package com.cmonkeys.mcalendar.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.app.DatePickerDialog;


public class appointmenteditor extends Activity {
	private EditText m_editTextTitle;
	private EditText m_editTextDescription;
	private EditText m_editTextParticipant;
	private EditText m_editTextLocation;
	private Button m_buttonStartDateTime;
	private Button m_buttonEndDateTime;
	private Date m_startDate;
	private Date m_endDate;
	static final int START_DATE_DIALOG_ID = 0;
	static final int END_DATE_DIALOG_ID = 0;
	
	private int m_indexOfCurrent;
	
	SimpleDateFormat m_dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmenteditor);

        m_editTextTitle = (EditText)findViewById(R.id.editTextAppointmentEditTitle);
        m_editTextDescription = (EditText)findViewById(R.id.editTextAppointmentEditDescription);
        m_editTextParticipant = (EditText)findViewById(R.id.editTextAppointmentEditParticipant);
        m_editTextLocation = (EditText)findViewById(R.id.editTextAppointmentEditLocation);
        
        m_buttonStartDateTime = (Button)findViewById(R.id.buttonStartDateTime_);
        m_buttonEndDateTime = (Button)findViewById(R.id.buttonEndDateTime_);
        
        Date after = new Date(0,0,0,0,30);
        m_startDate = new Date();
        m_endDate = new Date();
        m_endDate.setTime(m_startDate.getTime() + after.getTime());
        
        Intent intent = getIntent();

    	try {
    		if(intent.getBooleanExtra("isNew", true))
    		{
	    		m_startDate = m_dateTimeFormat.parse(intent.getStringExtra("SelectedDay"));
	    		m_buttonStartDateTime.setText(m_dateTimeFormat.format(m_startDate));
	    		m_endDate = m_startDate;
	    		m_buttonEndDateTime.setText(m_dateTimeFormat.format(m_endDate));
	    		m_indexOfCurrent = -1;
    		}
    		else
    		{
    			m_indexOfCurrent = intent.getIntExtra("indexOfCurrent", 1);
    		}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}        
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonSaveAppointment:
    		saveAppointment();
    		finish();
    		break;
    	case R.id.buttonCancelAndCloseAppointment:  
    		finish();
    		break;
    	}
    }
    
    public void onStartClick(View v)
    {
    	DatePickerDialog dateDlg = new DatePickerDialog(this, m_startDateSetListener, m_startDate.getYear() + 1900, m_startDate.getMonth(), m_startDate.getDate() );
    	dateDlg.show();
    }

    public void onEndClick(View v)
    {
    	DatePickerDialog dateDlg = new DatePickerDialog(this, m_endDateSetListener, m_startDate.getYear() + 1900, m_startDate.getMonth(), m_startDate.getDate() );
    	dateDlg.show();
    }
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener m_startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    m_startDate.setYear(year - 1900);
                    m_startDate.setMonth(monthOfYear);
                    m_startDate.setDate(dayOfMonth);
                    m_buttonStartDateTime.setText(m_dateTimeFormat.format(m_startDate));
                }
            };
            
    private DatePickerDialog.OnDateSetListener m_endDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
            	m_endDate.setYear(year - 1900);
            	m_endDate.setMonth(monthOfYear);
            	m_endDate.setDate(dayOfMonth);
            	m_buttonEndDateTime.setText(m_dateTimeFormat.format(m_endDate));
            }
        };
    
    private Appointment getAppointment()
    {
    	String title = m_editTextTitle.getText().toString();
		String description = m_editTextDescription.getText().toString();
		String participant = m_editTextParticipant.getText().toString();
		String location = m_editTextLocation.getText().toString();
		
    	return new Appointment(0, title, description, new Date(), 
				participant, location,m_startDate, m_endDate,
				0, 0);
    }
    
    private void saveAppointment()
    {
		AppointmentDBHelper dbHelper = new AppointmentDBHelper(this);
		dbHelper.insertAppointment(getAppointment());
		dbHelper.close();
    }
}
