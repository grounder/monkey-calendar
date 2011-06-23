package com.cmonkeys.mcalendar.view;

import java.util.Date;

import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.EditText;

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
}
