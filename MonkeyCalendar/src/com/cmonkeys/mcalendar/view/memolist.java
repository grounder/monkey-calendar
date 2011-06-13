package com.cmonkeys.mcalendar.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmonkeys.db.Article;
import com.cmonkeys.db.MemoDBHelper;
import com.cmonkeys.mcalendar.R;

public class memolist extends Activity {
	private int noMemo;
	ArrayList<Article> m_arrayOfMemos;
	MemoDBHelper m_memoHelper;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memolist);

        m_memoHelper = new MemoDBHelper(this);
        
        loadMemoList();
        updateMemoList();
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonExitMemoList:
    		finish();
    		break;
    	case R.id.buttonNewMemo:
    		showNewMemoEditor();
    		break;
    	}
    }
    
    private int getNoMemos()
    {
    	return noMemo;
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
    	noMemo = m_arrayOfMemos.size();
    }
    
    private final void removeMemo(int index)
    {
    	m_arrayOfMemos.remove(index);
    }
    
    private void clearList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutMemoList);
    	layout.removeAllViewsInLayout();
    }
    
    private final void updateMemoList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutMemoList);
    	
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
    }
    
    private void showSelectedMemo(int index)
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(memolist.this);
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
    	AlertDialog.Builder bld = new AlertDialog.Builder(memolist.this);
    	final LinearLayout editLayout = (LinearLayout)View.inflate(memolist.this, R.layout.memoedit, null);
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
				// TODO Remove this memo
    			m_memoHelper.deleteMemo(currentArticle.getIndex());
    			updateMemoList();
			}});
    	bld.setNegativeButton("Close", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Save this memo to DB
    			int indexToUpdate = currentArticle.getIndex();
    			String oldTitle = currentArticle.getTitle();
    			String oldDescription = currentArticle.getDescription();
    			String newTitle = editTextTitle.getText().toString();
    			String newDescription = editTextDescription.getText().toString();    			
    			
    			if( oldTitle.compareTo(newTitle) == 0 || oldDescription.compareTo(newDescription) == 0 )
    				m_memoHelper.updateMemo(indexToUpdate, newTitle, newDescription);
				updateMemoList();
			}});
    	bld.show();
    }
    
    private void showNewMemoEditor()
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(memolist.this);
    	final LinearLayout editLayout = (LinearLayout)View.inflate(memolist.this, R.layout.memoedit, null);
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
