package com.cmonkeys.mcalendar.view;

import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class logo extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
        Handler handler = new Handler () {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 1000);
    }
	
}
