package com.cmonkeys.mcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class main extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void mOnClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonMemo:
    		startActivity(new Intent(main.this, Memo.class));
    		break;
    	case R.id.buttonExit:
    		finish();
    		System.exit(0);
    		break;
    	}
    }
}
