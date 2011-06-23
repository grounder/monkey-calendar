package com.cmonkeys.mcalendar.view;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
//import android.database.Cursor;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
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
	
	//private Date m_currentDate;
	//private Date m_selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mkcalendartype1);
        
        // Appointment initialize
        m_appointementHelper = new AppointmentDBHelper(this);
        
        loadAppointmentList();
        
        // Memo initialize
        m_memoHelper = new MemoDBHelper(this);
        
        loadMemoList();
        updateMemoList();        
        
        /// 달력을 띄울 대상 레이아웃
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
        
        /// 달력객체 생성
        mkCalendar cal = new mkCalendar( this, lv ) ;
        
        /// 색상 설정할 객체 생성
        mkCalendarColorParam cParam = new mkCalendarColorParam( ) ;
        
        cParam.m_cellColor = 0x00000000 ;
        cParam.m_textColor = 0xffffffff ;
        cParam.m_saturdayTextColor = 0xff33ccff ;
        cParam.m_lineColor = 0x99999999 ;
        cParam.m_topCellColor = 0xff003333 ;
        cParam.m_topTextColor = 0xffffffff ;
        cParam.m_topSundayTextColor = 0xffffffff ;
        cParam.m_topSaturdatTextColor = 0xffffffff ;
        
        /// 셋팅한 값으로 색상값 셋~
        cal.setColorParam( cParam ) ;
        
        /// 배경으로 사용할 이미지 얻기
        //Drawable img = getResources( ).getDrawable( R.drawable.logo ) ;
        // 배경 이미지 셋~
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
        
        cal.initCalendar( ) ;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Appointment functions
    ArrayList<Appointment> m_arrayOfAppointments;
    AppointmentDBHelper m_appointementHelper;
    
    private void loadAppointmentList()
    {
		
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Memo functions
    
    // Memo(=Article) array to store current memos from DB
    // Just for displaying
    ArrayList<Article> m_arrayOfMemos;
    
    // Memo DB helper to handle memo DB
	MemoDBHelper m_memoHelper;
	
	// Load memos from DB, save all latest memos to m_arrayOfMemos
    private void loadMemoList()
    {
    	m_arrayOfMemos = m_memoHelper.getAllMemos();
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
    	
    	addNewMemoButton(layout);
		addWeatherButton(layout);
		addBiorythmButton(layout);
		addFortuneButton(layout);
		
    }

    private void addNewMemoButton(LinearLayout layout) {
		TextView tvNewMemo = new TextView(this);
    	tvNewMemo.setPadding(10, 10, 10, 10);
    	tvNewMemo.setText(getResources().getString(R.string.Add_memo));
    	
    	tvNewMemo.setClickable(true);
    	tvNewMemo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showNewMemoEditor();
			}
		});
    	tvNewMemo.setTextSize(22);
    	tvNewMemo.setTextColor(0xff00ffff);
    	tvNewMemo.setGravity(Gravity.CENTER);
		layout.addView(tvNewMemo);
	}
    
	private void addBiorythmButton(LinearLayout layout) {
		TextView tvNewMemo = new TextView(this);
    	tvNewMemo.setPadding(10, 10, 10, 10);
    	tvNewMemo.setText(getResources().getString(R.string.MsgBio));
    	
    	tvNewMemo.setClickable(true);
    	tvNewMemo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Forward to biorythm web page
				SharedPreferences pref = getSharedPreferences("fun",0);
                
	            int Year = pref.getInt("Year",1983);
	            int Month = pref.getInt("Month",4);
	            int Day = pref.getInt("Day",19);

				Uri uri = Uri.parse(getResources().getString(R.string.UriBio1) + Year +
									getResources().getString(R.string.UriBio2) + Month + 
									getResources().getString(R.string.UriBio3) + Day);
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
			}
		});
    	tvNewMemo.setTextSize(22);
    	tvNewMemo.setTextColor(0xffffff00);
    	tvNewMemo.setGravity(Gravity.CENTER);
		layout.addView(tvNewMemo);
	}

	private void addWeatherButton(LinearLayout layout) {
		TextView tvNewMemo = new TextView(this);
    	
    	tvNewMemo.setPadding(10, 10, 10, 10);
    	tvNewMemo.setText(getResources().getString(R.string.MsgWeather));
    	
    	tvNewMemo.setClickable(true);
    	tvNewMemo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Forward to weather web page
				Uri uri = Uri.parse(getResources().getString(R.string.UriWeather));
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
			}
		});
    	tvNewMemo.setTextSize(22);
    	tvNewMemo.setTextColor(0xffffff00);
    	tvNewMemo.setGravity(Gravity.CENTER);
		layout.addView(tvNewMemo);
	}
    
	private void addFortuneButton(LinearLayout layout) {
		TextView tvFortuneButton = new TextView(this);
		    	
    	tvFortuneButton.setPadding(10, 10, 10, 10);
    	tvFortuneButton.setText(getResources().getString(R.string.MsgFortune));
    	
    	tvFortuneButton.setClickable(true);
    	tvFortuneButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				SharedPreferences pref = getSharedPreferences("fun",0);
				int iAnimal = pref.getInt("Year",1983)%12;
				
				String sAnimal = getResources().getStringArray(R.array.Ddi)[iAnimal - 1];
				
				// Forward to weather web page
				Uri uri = Uri.parse(getResources().getString(R.string.UriFor) + sAnimal);
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
			}
		});
    	tvFortuneButton.setTextSize(22);
    	tvFortuneButton.setTextColor(0xffffff00);
    	tvFortuneButton.setGravity(Gravity.CENTER);
		layout.addView(tvFortuneButton);
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
				// TODO Show memo editor
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
    	
    	bld.setTitle("Edit");
    	editTextTitle.setText(m_arrayOfMemos.get(index).getTitle());
    	editTextDescription.setText(m_arrayOfMemos.get(index).getDescription());
    	bld.setView(editLayout);    	
    	
    	bld.setPositiveButton("Remove", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// Remove this memo
    			m_memoHelper.deleteMemo(currentArticle.getIndex());
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
    				m_memoHelper.updateMemo(indexToUpdate, newTitle, newDescription);
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
    	
    	bld.setTitle("New");
    	editTextTitle.setText("");
    	editTextDescription.setText("");
    	bld.setView(editLayout);    	
    	
    	bld.setPositiveButton("Save", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
    			m_memoHelper.insertMemo(editTextTitle.getText().toString(), editTextDescription.getText().toString());
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
