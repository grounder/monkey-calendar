package com.cmonkeys.mcalendar.view;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmonkeys.db.Article;
import com.cmonkeys.mcalendar.R;

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
    	// TODO Implement memo load
    	// 메모 읽어오기(DB에서)
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
    	art.setIndex(5);
    	art.setTitle("Memo6");
    	art.setDescription("Memo6 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(6);
    	art.setTitle("Memo7");
    	art.setDescription("Memo7 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(7);
    	art.setTitle("Memo8");
    	art.setDescription("Memo8 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(8);
    	art.setTitle("Memo9");
    	art.setDescription("Memo9 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(9);
    	art.setTitle("Memo10");
    	art.setDescription("Memo10 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(10);
    	art.setTitle("Memo11");
    	art.setDescription("Memo11 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(11);
    	art.setTitle("Memo12");
    	art.setDescription("Memo12 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(12);
    	art.setTitle("Memo13");
    	art.setDescription("Memo13 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(13);
    	art.setTitle("Memo14");
    	art.setDescription("Memo14 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(14);
    	art.setTitle("Memo15");
    	art.setDescription("Memo15 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(15);
    	art.setTitle("Memo16");
    	art.setDescription("Memo16 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(16);
    	art.setTitle("Memo17");
    	art.setDescription("Memo17 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(17);
    	art.setTitle("Memo18");
    	art.setDescription("Memo18 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(18);
    	art.setTitle("Memo19");
    	art.setDescription("Memo19 - Details \n test \n test");
    	memos.add(art);art = new Article();
    	art.setIndex(19);
    	art.setTitle("Memo20");
    	art.setDescription("Memo20 - Details \n test \n test");
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
    		tvMemoTitle.setTextSize(20);
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
