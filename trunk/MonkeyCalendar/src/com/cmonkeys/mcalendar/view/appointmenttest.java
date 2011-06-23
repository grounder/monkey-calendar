package com.cmonkeys.mcalendar.view;

import java.util.ArrayList;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.Article;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class appointmenttest extends Activity {
	ArrayList<Appointment> m_arrayOfAppointment;  
	AppointmentDBHelper m_AppointmentHelper;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmenttest);      
        
        loadAppointmentList();
        updateAppointmentList();
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonAddAppointment:
    		showNewAppointmentEditor();
    		break;
    	case R.id.buttonExitAppointment:  
    		finish();
    		break;
    	}
    }    

    //delete appointment 
    private final void removeAppointment(int index)
    {
    	m_arrayOfAppointment.remove(index);
    }    
    
    private void clearList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutAppointmentList);
    	layout.removeAllViewsInLayout();
    	m_arrayOfAppointment.clear();
    }         
    
    private void showNewAppointmentEditor()
    {    	
    	AlertDialog.Builder bld = new AlertDialog.Builder(appointmenttest.this);
    	
    	final LinearLayout editLayout = (LinearLayout)View.inflate(appointmenttest.this, R.layout.appointmentedit, null);
    	final EditText editTextTitle = (EditText)editLayout.findViewById(R.id.editTextAppointmentEditTitle);
    	final EditText editTextDescription = (EditText)editLayout.findViewById(R.id.editTextAppointmentEditDescription);
    	final EditText editTextParticipant = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditParticipant));
    	final EditText editTextLocation = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditLocation));
    	
    	//Input date, String type, (yyyy-mm-dd-hh-mm)
    	final EditText editTextStartYear = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditStartYear));
    	final EditText editTextFinishYear = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditFinishYear));
       	final EditText editTextStartMonth = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditStartMonth));
    	final EditText editTextFinishMonth = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditFinishMonth));
       	final EditText editTextStartDate = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditStartDay));
    	final EditText editTextFinishDate = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditFinishDay));
       	final EditText editTextStartHour = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditStartHour));
    	final EditText editTextFinishHour = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditFinishHour));
       	final EditText editTextStartMinute = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditStartMinute));
    	final EditText editTextFinishMinute = ((EditText)editLayout.findViewById(R.id.editTextAppointmentEditFinishMinute));
 	
    	bld.setTitle("New");
    	editTextTitle.setText("");
    	editTextDescription.setText("");
    	editTextParticipant.setText("");
    	editTextLocation.setText("");
    	editTextStartYear.setText("");
    	editTextFinishYear.setText("");
    	editTextStartMonth.setText("");
    	editTextFinishMonth.setText("");
    	editTextStartDate.setText("");
    	editTextFinishDate.setText("");
    	editTextStartHour.setText("");
    	editTextFinishHour.setText("");
    	editTextStartMinute.setText("");
    	editTextFinishMinute.setText("");
    	bld.setView(editLayout);  
    	   	  	    	    	    	
    	bld.setPositiveButton("Save", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			
    			Date StartTime = new Date();
    			Date EndTime = new Date();
    	    	
    			//String type input date convert to Date type 
    	    	StartTime.setYear(Integer.parseInt(editTextStartYear.getText().toString()) - 1900);
    	    	StartTime.setMonth(Integer.parseInt(editTextStartMonth.getText().toString()));
    	    	StartTime.setDate(Integer.parseInt(editTextStartDate.getText().toString()));
    	    	StartTime.setHours(Integer.parseInt(editTextStartHour.getText().toString()));
    	    	StartTime.setMinutes(Integer.parseInt(editTextStartMinute.getText().toString()));
    	    	StartTime.setSeconds(00);
    	    	
    	    	EndTime.setYear(Integer.parseInt(editTextFinishYear.getText().toString()) - 1900);
    	    	EndTime.setMonth(Integer.parseInt(editTextFinishMonth.getText().toString()));
    	    	EndTime.setDate(Integer.parseInt(editTextFinishDate.getText().toString()));
    	    	EndTime.setHours(Integer.parseInt(editTextFinishHour.getText().toString()));
    	    	EndTime.setMinutes(Integer.parseInt(editTextFinishMinute.getText().toString()));
    	    	EndTime.setSeconds(00);
    			
    			m_AppointmentHelper.insertAppointment(editTextTitle.getText().toString(), 
    					editTextDescription.getText().toString(),
    					editTextParticipant.getText().toString(),
    					editTextLocation.getText().toString(),
    					StartTime, EndTime, 0);
    			
    			updateAppointmentList();
			}});
    	bld.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			// do noting
			}});
    	bld.show();
    }
    
    private final void updateAppointmentList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutAppointmentList);
    	
    	clearList();
    	loadAppointmentList();    	

    	for(final Article art : m_arrayOfAppointment){
    		TextView tvMemoTitle = new TextView(this);
    		tvMemoTitle.setPadding(10, 10, 10, 10);
    		tvMemoTitle.setText(art.getTitle());
    		tvMemoTitle.setClickable(true);
    		tvMemoTitle.setId(art.getIndex());
    		tvMemoTitle.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//showSelectedMemo(art.getIndexAtCurrent());
				}
    		});
    		tvMemoTitle.setTextSize(20);
    		layout.addView(tvMemoTitle);
    	}
    }
    
    private void loadAppointmentList()
    {
    	m_AppointmentHelper = new AppointmentDBHelper(this);
    	m_arrayOfAppointment = m_AppointmentHelper.getAllAppointments();
    	m_AppointmentHelper.close();
    }
}
