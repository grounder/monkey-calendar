package com.cmonkeys.mcalendar.view;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;

import com.cmonkeys.db.Appointment;
import com.cmonkeys.db.AppointmentDBHelper;
import com.cmonkeys.db.Article;
import com.cmonkeys.db.MemoDBHelper;
import com.cmonkeys.mcalendar.R;
import com.cmonkeys.mcalendar.view.mkCalendar;
import com.cmonkeys.mcalendar.view.mkCalendar.mkCalendarColorParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mkCalendarType1 extends Activity {
	// Buttons to change the current date
	private TextView m_arrayOfTextViews[];
	private Button m_arrayOfButtons[];
	
	private Date m_currentDate;
	private Date m_selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mkcalendartype1);
        
        // set today
        m_currentDate = new Date();
        
        // Appointment initialize
        loadAppointmentList();
        
        // Memo initialize
        loadMemoList();
        updateMemoList();        
        
        // Target layout to add a calendar
        LinearLayout lv = (LinearLayout)findViewById( R.id.linearLayoutCalendar ) ;
        
        /// 년 월 일 표시할 텍스트뷰
        m_arrayOfTextViews = new TextView[3] ;
        m_arrayOfTextViews[0] = (TextView)findViewById( R.id.textViewYear ) ;
        m_arrayOfTextViews[1] = (TextView)findViewById( R.id.textViewMonth ) ;
        m_arrayOfTextViews[2] = null ; /// 일은 표시하지 않음
        
        /// 누르면 년 월 일 조절할 버튼
        m_arrayOfButtons = new Button[4] ;
        m_arrayOfButtons[0] = null ; // 년도는 조절하지 않음
        m_arrayOfButtons[1] = null ; // 위와 동일
        m_arrayOfButtons[2] = (Button)findViewById( R.id.buttonPrevMonth ) ;
        m_arrayOfButtons[3] = (Button)findViewById( R.id.buttonNextMonth ) ;
        
        // Allocate new calendar object
        mkCalendar cal = new mkCalendar( this, lv ) ;
        
        // Set colors of the calendar
        mkCalendarColorParam cParam = new mkCalendarColorParam( ) ;
        
        cParam.m_cellColor = 0x00000000 ;
        cParam.m_textColor = 0xffffffff ;
        cParam.m_saturdayTextColor = 0xff33ccff ;
        cParam.m_lineColor = 0x99999999 ;
        cParam.m_topCellColor = 0xff003333 ;
        cParam.m_topTextColor = 0xffffffff ;
        cParam.m_topSundayTextColor = 0xffffffff ;
        cParam.m_topSaturdatTextColor = 0xffffffff ;
        cal.setColorParam( cParam ) ;
        
        /// Set a background of the calendar
        //Drawable img = getResources( ).getDrawable( R.drawable.logo ) ;
        //cal.setBackground( img ) ;
                            
        // Set the size of the calendar
        int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320.0f, getResources().getDisplayMetrics());
        int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324.0f, getResources().getDisplayMetrics());
        
        cal.setCalendarSize(width, height) ;
        
        // Set the top cells' height to 35
        cal.setTopCellSize( 35 ) ;
        
        // Set the buttons to change month
        cal.setControl( m_arrayOfButtons ) ;
        
        // set the textView to show year, month, and day
        cal.setViewTarget( m_arrayOfTextViews ) ;
        
        // Init the calendar
        cal.initCalendar( ) ;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	// Options menu configure
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0,1,0,getResources().getString(R.string.Add_memo).toString());
    	menu.add(0,2,0,getResources().getString(R.string.Show_week_view).toString());
    	menu.add(0,3,0,getResources().getString(R.string.Set_preference).toString());
    	menu.add(0,4,0,getResources().getString(R.string.MsgBio).toString());
    	menu.add(0,5,0,getResources().getString(R.string.MsgWeather).toString());
    	menu.add(0,6,0,getResources().getString(R.string.MsgFortune).toString());
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent intent;
    	// Set the options action
    	switch(item.getItemId())
    	{
    	case 1:	// Add memo
    		showNewMemoEditor();
    		return true;
    	case 2:	// Show week view
    		intent = new Intent(this, week.class);
    		startActivity(intent);
    		return true;
    	case 3:	// Set preference
    		intent = new Intent(this, Preference.class);
    		startActivity(intent);
    		return true;
    	case 4: // Bio
    		 showBiorythm();
    		return true;
    	case 5: // Weather
    		showWeather();
    		return true;
    	case 6: // Fortune
    		showFortune();
    		return true;
    	}
    	return false;
    }
    
	private void showBiorythm() {
		// Forward to biorythm web page
		SharedPreferences pref = getSharedPreferences("fun",0);
		Date now = new Date();
        
        int Year = pref.getInt("Year",now.getYear()) + 1900;
        int Month = pref.getInt("Month",now.getMonth());
        int Day = pref.getInt("Date",now.getDate());

		Uri uri = Uri.parse(getResources().getString(R.string.UriBio1) + Year +
							getResources().getString(R.string.UriBio2) + Month + 
							getResources().getString(R.string.UriBio3) + Day);
		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		 startActivity(intent);
	}

	private void showWeather() {
		// Forward to weather web page
		Uri uri = Uri.parse(getResources().getString(R.string.UriWeather));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
    
	private void showFortune() {
		SharedPreferences pref = getSharedPreferences("fun",0);
		Date now = new Date();
		int iAnimal = pref.getInt("Year",now.getYear()) + 1900; 
		iAnimal = iAnimal %12;
		
		String sAnimal = getResources().getStringArray(R.array.Ddi)[iAnimal];

		// Forward to weather web page
		Uri uri = Uri.parse(getResources().getString(R.string.UriFor) + sAnimal);
		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		 startActivity(intent);
	}

    ///////////////////////////////////////////////////////////////////////////
    // Appointment functions
    ArrayList<Appointment> m_arrayOfAppointments;
    
    private void loadAppointmentList()
    {
    	AppointmentDBHelper appointementHelper = new AppointmentDBHelper(this);
    	
    	//m_arrayOfAppointments = appointementHelper.getAppointments(selectedStart, selectedEnd);
    	
    	appointementHelper.close();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Memo functions
    
    // Memo(=Article) array to store current memos from DB
    // Just for displaying
    ArrayList<Article> m_arrayOfMemos;
    
	// Load memos from DB, save all latest memos to m_arrayOfMemos
    private void loadMemoList()
    {
    	// Memo DB helper to handle memo DB
    	MemoDBHelper memoHelper = new MemoDBHelper(this);
    	
    	m_arrayOfMemos = memoHelper.getAllMemos();
    	
    	memoHelper.close();
    }

    // Clear memo list, it doesn't mean clear DB 
    private void clearMemoList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutMemoBottom);
    	layout.removeAllViewsInLayout();
    	m_arrayOfMemos.clear();
    }
    
    // Update memo list from m_arrayOfMemos
    private final void updateMemoList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutMemoBottom);
    	
    	clearMemoList();
    	loadMemoList();
    	
    	for(final Article art : m_arrayOfMemos){
    		TextView tvMemoTitle = new TextView(this);
    		tvMemoTitle.setPadding(10, 10, 10, 10);
    		tvMemoTitle.setText(art.getTitle());
    		tvMemoTitle.setClickable(true);
    		tvMemoTitle.setId(art.getIndex());
    		tvMemoTitle.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					showSelectedMemo(art.getIndexAtCurrent());
				}
    		});
    		tvMemoTitle.setTextSize(20);
    		layout.addView(tvMemoTitle);
    		
    	}
    	
    	if(m_arrayOfMemos.size() == 0)
    	{
    		TextView tvMemoTitle = new TextView(this);
    		tvMemoTitle.setPadding(10, 10, 10, 10);
    		tvMemoTitle.setText(getResources().getString(R.string.No_memo).toString());
    		tvMemoTitle.setTextSize(20);
    		layout.addView(tvMemoTitle);
    	}
    }

    // Show selected memo 
    private void showSelectedMemo(int index)
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(this);
    	final int curIndex = index;
		
    	bld.setTitle(m_arrayOfMemos.get(index).getTitle());
    	bld.setMessage(m_arrayOfMemos.get(index).getDescription() + "\n\nLastUpdate :\n" + m_arrayOfMemos.get(index).getLastUpdate().toString());
    	bld.setPositiveButton("Edit", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// Show memo editor
    			showSelectedMemoEditor(curIndex);
			}});
    	bld.setNegativeButton("Close", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			// Close
			}});
    	bld.show();
    }
    
    private void showSelectedMemoEditor(int index)
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(this);
    	final LinearLayout editLayout = (LinearLayout)View.inflate(this, R.layout.memoedit, null);
    	final EditText editTextTitle = (EditText)editLayout.findViewById(R.id.editTextMemoEditTitle);
    	final EditText editTextDescription = (EditText)editLayout.findViewById(R.id.editTextMemoEditDescription);
    	final int currentIndex = index;
    	final Article currentArticle = m_arrayOfMemos.get(currentIndex);
    	final mkCalendarType1 currentView = this;
    	
    	bld.setTitle("Edit");
    	editTextTitle.setText(m_arrayOfMemos.get(index).getTitle());
    	editTextDescription.setText(m_arrayOfMemos.get(index).getDescription());
    	bld.setView(editLayout);
    	
    	bld.setPositiveButton("Remove", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// Remove this memo
    			MemoDBHelper memoHelper = new MemoDBHelper(currentView); 
    			memoHelper.deleteMemo(currentArticle.getIndex());
    			memoHelper.close();
    			updateMemoList();
			}});
    	bld.setNegativeButton("Close", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// Save this memo to DB
    			int indexToUpdate = currentArticle.getIndex();
    			String oldTitle = currentArticle.getTitle();
    			String oldDescription = currentArticle.getDescription();
    			String newTitle = editTextTitle.getText().toString();
    			String newDescription = editTextDescription.getText().toString();    			
    			    			
    			if( oldTitle != newTitle || oldDescription != newDescription )
    			{
    				MemoDBHelper memoHelper = new MemoDBHelper(currentView); 
    				memoHelper.updateMemo(indexToUpdate, newTitle, newDescription);
        			memoHelper.close();
    			}
				updateMemoList();
			}});
    	bld.show();
    }
    
    private void showNewMemoEditor()
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(this);
    	final LinearLayout editLayout = (LinearLayout)View.inflate(this, R.layout.memoedit, null);
    	final EditText editTextTitle = (EditText)editLayout.findViewById(R.id.editTextMemoEditTitle);
    	final EditText editTextDescription = (EditText)editLayout.findViewById(R.id.editTextMemoEditDescription);
    	final mkCalendarType1 currentView = this;
    	
    	bld.setTitle("New");
    	editTextTitle.setText("");
    	editTextDescription.setText("");
    	bld.setView(editLayout);    	
    	
    	bld.setPositiveButton("Save", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			MemoDBHelper memoHelper = new MemoDBHelper(currentView); 
				memoHelper.insertMemo(editTextTitle.getText().toString(), editTextDescription.getText().toString());
    			memoHelper.close();
    			updateMemoList();
			}});
    	bld.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			// do noting
			}});
    	bld.show();
    }
}
