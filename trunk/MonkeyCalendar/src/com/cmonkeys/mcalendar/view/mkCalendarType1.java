package com.cmonkeys.mcalendar.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.cmonkeys.db.Article;
import com.cmonkeys.db.MemoDBHelper;
import com.cmonkeys.mcalendar.R;
import com.cmonkeys.mcalendar.view.mkCalendar;
import com.cmonkeys.mcalendar.view.mkCalendar.mkCalendarColorParam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mkCalendarType1 extends Activity {
	TextView tvs[] ;
	Button btns[] ;

	ArrayList<Article> m_arrayOfMemos;
	MemoDBHelper m_memoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mkcalendartype1);
        
        // Memo initialize
        m_memoHelper = new MemoDBHelper(this);
        
        loadMemoList();
        updateMemoList();        
        
        /// �޷��� ��� ��� ���̾ƿ�
        LinearLayout lv = (LinearLayout)findViewById( R.id.linearLayoutCalendar ) ;
        
        /// �� �� �� ǥ���� �ؽ�Ʈ��
        tvs = new TextView[3] ;
        tvs[0] = (TextView)findViewById( R.id.textViewYear ) ;
        tvs[1] = (TextView)findViewById( R.id.textViewMonth ) ;
        tvs[2] = null ; /// ���� ǥ������ ����
        
        /// ������ �� �� �� ������ ��ư
        btns = new Button[4] ;
        btns[0] = null ; // �⵵�� �������� ����
        btns[1] = null ; // ���� ����
        btns[2] = (Button)findViewById( R.id.buttonPrevMonth ) ;
        btns[3] = (Button)findViewById( R.id.buttonNextMonth ) ;
        
        /// �޷°�ü ����
        mkCalendar cal = new mkCalendar( this, lv ) ;
        
        /// ���� ������ ��ü ����
        mkCalendarColorParam cParam = new mkCalendarColorParam( ) ;
        
        cParam.m_cellColor = 0x00000000 ;
        cParam.m_textColor = 0xffffffff ;
        cParam.m_saturdayTextColor = 0xff33ccff ;
        cParam.m_lineColor = 0x99999999 ;
        cParam.m_topCellColor = 0xff003333 ;
        cParam.m_topTextColor = 0xffffffff ;
        cParam.m_topSundayTextColor = 0xffffffff ;
        cParam.m_topSaturdatTextColor = 0xffffffff ;
        
        /// ������ ������ ���� ��~
        cal.setColorParam( cParam ) ;
        
        /// ������� ����� �̹��� ���
        // Drawable img = getResources( ).getDrawable( R.drawable.bg ) ;
        // ��� �̹��� ��~
        //cal.setBackground( img ) ;
                               
        /// �޷��� ��� ũ�� ����
        cal.setCalendarSize(480, 500) ;
        
        /// �ֻ���� ���̸� 35�� �ش�(��ü������ �� ���� ���� 600/7�� ������ 35�� ����Ǵ� �޷��� �� ���̰� �پ���.)
        cal.setTopCellSize( 35 ) ;
        
        /// ������ ������ ��ư�� ����
        cal.setControl( btns ) ;
        
        /// �� �� ���� ��� �ؽ�Ʈ�� ����
        cal.setViewTarget( tvs ) ;
        
        cal.initCalendar( ) ;
    }
    
    private void loadMemoList()
    {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Cursor cursor = m_memoHelper.getCursor();
    	
    	m_arrayOfMemos = new ArrayList<Article>();
    	
    	while(cursor.moveToNext())
    	{
    		Article art = null;
    		int index = cursor.getInt(0);
    		String title = cursor.getString(1);
    		String description = cursor.getString(2);
    		String lastUpdate = cursor.getString(3);
    		try{   		
    			art = new Article(index, title, description, dateFormat.parse(lastUpdate), m_arrayOfMemos.size());
    		}
    		catch(Exception ex)
    		{
    			continue;
    		}
    		m_arrayOfMemos.add(art);
    	}

    	m_memoHelper.close();
    }
        
    private void clearList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutMemoBottom);
    	layout.removeAllViewsInLayout();
    }
    
    private final void updateMemoList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutMemoBottom);
    	
    	clearList();
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
    	tvNewMemo.setTextColor(0xff0000ff);
    	tvNewMemo.setGravity(Gravity.CENTER);
		layout.addView(tvNewMemo);
    }
    
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
