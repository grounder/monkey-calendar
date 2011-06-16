package com.cmonkeys.mcalendar.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.Article;
import com.cmonkeys.db.MemoDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class appointmenttest extends Activity {
	
	private int noAppointment;   //index of Appointment
	ArrayList<Appointment> m_arrayOfAppointment;  
	AppointmentDBHelper m_AppointmentHelper;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmenttest);      
        
        m_AppointmentHelper = new AppointmentDBHelper(this);
        
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
    
    private int getNoAppointments()
    {
    	return noAppointment;
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
    			Date FinishTime = new Date();
    	    	
    			//String type input date convert to Date type 
    	    	StartTime.setYear(Integer.parseInt(editTextStartYear.getText().toString()));
    	    	StartTime.setMonth(Integer.parseInt(editTextStartMonth.getText().toString()));
    	    	StartTime.setDate(Integer.parseInt(editTextStartDate.getText().toString()));
    	    	StartTime.setHours(Integer.parseInt(editTextStartHour.getText().toString()));
    	    	StartTime.setMinutes(Integer.parseInt(editTextStartMinute.getText().toString()));
    	    	StartTime.setSeconds(00);
    	    	
    	    	FinishTime.setYear(Integer.parseInt(editTextFinishYear.getText().toString()));
    	    	FinishTime.setMonth(Integer.parseInt(editTextFinishMonth.getText().toString()));
    	    	FinishTime.setDate(Integer.parseInt(editTextFinishDate.getText().toString()));
    	    	FinishTime.setHours(Integer.parseInt(editTextFinishHour.getText().toString()));
    	    	FinishTime.setMinutes(Integer.parseInt(editTextFinishMinute.getText().toString()));
    	    	FinishTime.setSeconds(00);
    			
    			m_AppointmentHelper.insertAppointment(editTextTitle.getText().toString(), 
    					editTextDescription.getText().toString(),
    					editTextParticipant.getText().toString(),
    					editTextLocation.getText().toString(),
    					StartTime, FinishTime, 0);
    			
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
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	Cursor cursor = m_AppointmentHelper.getCursor();
    	
    	m_arrayOfAppointment = new ArrayList<Appointment>();
    	
    	while(cursor.moveToNext())
    	{    		
    		Appointment app = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		String participant = cursor.getString(4);
    		String location = cursor.getString(5);
    		String starttime = cursor.getString(6);
    		String finishtime = cursor.getString(7);
    	    		
    		try{   		
    			app = new Appointment(index, 
    					title, 
    					description, 
    					dateFormat.parse(lastUpdate), 
    					participant,
    					location,
    					dateFormat.parse(starttime),
    					dateFormat.parse(finishtime),
    					0,
    					m_arrayOfAppointment.size());
    		}
    		catch(Exception ex)
    		{
    			continue;
    		}
    		m_arrayOfAppointment.add(app);
    	}

    	m_AppointmentHelper.close();
    	noAppointment = m_arrayOfAppointment.size();
    }
        


	/**
	 * @param noAppointment the noAppointment to set
	 */
	public void setNoAppointment(int noAppointment) {
		this.noAppointment = noAppointment;
	}

	/**
	 * @return the noAppointment
	 */
	public int getNoAppointment() {
		return noAppointment;
	}
}
