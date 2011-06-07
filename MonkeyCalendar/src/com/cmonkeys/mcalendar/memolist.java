package com.cmonkeys.mcalendar;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cmonkeys.db.Article;

public class memolist extends Activity {
	private int noMemo;
	ArrayList<Article> memos;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memolist);

        loadMemoList();
        updateMemoList();
    }
    
    public void mOnClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonExitMemoList:
    		finish();
    		break;
    	}
    }
    
    private void loadMemoList()
    {
    	// TODO Implement memo loading
    	// 메모 읽어오기
    	memos = new ArrayList<Article>();
    	Article art; 
    	    	   
    	art = new Article();
    	art.setIndex(0);
    	art.setTitle("Memo1");
    	art.setDescription("Memo1 - Details \n test \n test");
    	memos.add(art);
    	
    	art = new Article();
    	art.setIndex(1);
    	art.setTitle("Memo2");
    	art.setDescription("Memo2 - Details \n test \n test");
    	memos.add(art);
    	    	
    	art = new Article();
    	art.setIndex(2);
    	art.setTitle("Memo3");
    	art.setDescription("Memo3 - Details \n test \n test");
    	memos.add(art);
    	    	
    	art = new Article();
    	art.setIndex(3);
    	art.setTitle("Memo4");
    	art.setDescription("Memo4 - Details \n test \n test");
    	memos.add(art);
    	    	
    	art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);
    	
    	/**/
    	art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(4);
    	art.setTitle("Memo5");
    	art.setDescription("Memo5 - Details \n test \n test");
    	memos.add(art);
    	
    	noMemo = memos.size();
    }
    
    private void saveMemo(int index, String title, String description)
    {
    	// TODO Save selected memo
    	memos.get(index).setTitle(title);
    	memos.get(index).setDescription(description);
    	memos.get(index).setLastUpdate(new Date());		// Set the last update to now
    }
    
    private void clearList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutMemoList);
    	layout.removeAllViewsInLayout();
    }
    
    private void updateMemoList()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layoutMemoList);
    	
    	clearList();

    	for(final Article art : memos){
    		
    		TextView tvMemoTitle = new TextView(this);
    		tvMemoTitle.setPadding(10, 10, 10, 10);
    		tvMemoTitle.setText(art.getTitle());
    		tvMemoTitle.setClickable(true);
    		tvMemoTitle.setId(art.getIndex());
    		tvMemoTitle.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showSelectedMemo(art.getIndex());
				}
    		});
    		
    		layout.addView(tvMemoTitle);
    	}
    }
    
    private void showSelectedMemo(int index)
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(memolist.this);
    	final int curIndex = index;

    	bld.setTitle(memos.get(index).getTitle());
    	bld.setMessage(memos.get(index).getDescription() + "\n\nLastUpdate :\n" + memos.get(index).getLastUpdate().toString());
    	bld.setPositiveButton("Edit", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Show memo editor
    			showSelectedMemoEditor(curIndex);
			}});
    	bld.setNegativeButton("Close", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// Nothing to do
			}});
    	bld.show();
    }
    
    private void showSelectedMemoEditor(int index)
    {
    	AlertDialog.Builder bld = new AlertDialog.Builder(memolist.this);

    	bld.setTitle(memos.get(index).getTitle());
    	bld.setMessage(memos.get(index).getDescription() + "\n\nLastUpdate :\n" + memos.get(index).getLastUpdate().toString());
    	bld.setPositiveButton("Edit", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Show memo editor
				// Nothing to do
			}});
    	bld.setNegativeButton("Close", new DialogInterface.OnClickListener(){ 
    		@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// Nothing to do
			}});
    	bld.show();
    }
}
