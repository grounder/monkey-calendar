package com.cmonkeys.mcalendar.view;

import java.util.Date;

import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.DaysDBHelper;
import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Preference extends Activity {
	private DatePicker m_datePicker;
	private DatePicker.OnDateChangedListener m_startDateSetListener =
		new DatePicker.OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Set preference of birthday
	        	SharedPreferences pref = getSharedPreferences("fun",0);
	        	SharedPreferences.Editor edit = pref.edit();
	        	
	        	edit.putInt("Year",year - 1900);
	        	edit.putInt("Month",monthOfYear + 1);
	        	edit.putInt("Date",dayOfMonth);
	        	
	        	edit.commit();
			}
		};
	private EditText m_editTextName;
	private TextWatcher m_textChangeWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// 
			SharedPreferences pref = getSharedPreferences("fun",0);
        	SharedPreferences.Editor edit = pref.edit();
        	String name = s.toString();
        	
        	edit.putString("Name",name);
        	
        	edit.commit();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) { }
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        
        m_datePicker = (DatePicker)findViewById(R.id.datePickerUserProfile);
        m_editTextName = (EditText)findViewById(R.id.editTextUserName);
                
        Date now = new Date();
        SharedPreferences pref = getSharedPreferences("fun",0);

        int Year = pref.getInt("Year",now.getYear());
        int Month = pref.getInt("Month",now.getMonth()) - 1;
        int Date = pref.getInt("Date",now.getDate());
        String Name = pref.getString("Name", getResources().getString(R.string.No_name));
        
        if(Year < 1900)
        	Year += 1900;
        
        m_datePicker.init(Year, Month, Date, m_startDateSetListener);
        
        m_editTextName.setText(Name);
        m_editTextName.addTextChangedListener(m_textChangeWatcher);
    }
	
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.buttonImportHolodays:
			Toast.makeText(this, "Wait until done", Toast.LENGTH_LONG).show();
			DaysDBHelper helper = new DaysDBHelper(this);
			helper.importEmbededData(this);
			helper.close();
			Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
			break;
    	case R.id.buttonClearAllAppointmet:
    		final Context context = this;
    		AlertDialog.Builder dlg = new AlertDialog.Builder(this);
			dlg.setTitle(getResources().getString(R.string.MsgClearTitle));
			dlg.setMessage(getResources().getString(R.string.MsgClear));
			dlg.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Clear all appointment
					AppointmentDBHelper helper = new AppointmentDBHelper(context);
		    		helper.clearAllAppointment();
		    		helper.close();					
				}
			});
			dlg.setNegativeButton(getResources().getString(R.string.No), null);
			dlg.show();
    		
    		break;
    	}
	}
}
