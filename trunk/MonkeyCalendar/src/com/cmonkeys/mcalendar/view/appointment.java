package com.cmonkeys.mcalendar.view;

import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class appointment extends Activity {
	private EditText m_editTextTitle;
	private EditText m_editTextDescription;
	private EditText m_editTextParticipant;
	private EditText m_editTextLocation;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonSaveAppointment:
    		saveAppointment();
    		break;
    	case R.id.buttonCancelAppointment:  
    		finish();
    		break;
    	}
    }

    private Appointment getAppointment()
    {
    	String title = m_editTextTitle.getText().toString();
		String description = m_editTextDescription.getText().toString();
		String participant = m_editTextParticipant.getText().toString();
		String location = m_editTextLocation.getText().toString();
		Date startDate = new Date();
		Date endDate = new Date();
    	

    	return new Appointment(0, title, description, new Date(), 
				participant, location,startDate, endDate,
				0, 0);
    }
    
    private void saveAppointment()
    {
		AppointmentDBHelper dbHelper = new AppointmentDBHelper(this);
		dbHelper.insertAppointment(getAppointment());
		dbHelper.close();
    }
}
