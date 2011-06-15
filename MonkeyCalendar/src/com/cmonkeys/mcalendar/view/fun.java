package com.cmonkeys.mcalendar.view;

import com.cmonkeys.mcalendar.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.webkit.WebView;

public class fun extends Activity {
	/** Called when the activity is first created. */
	
    TextView textYear;
    TextView textMonth;
    TextView textDate;
    
    
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fun);


            textYear = (TextView)findViewById(R.id.year);
            textMonth = (TextView)findViewById(R.id.month);
            textDate = (TextView)findViewById(R.id.date);

            SharedPreferences pref = getSharedPreferences("fun",0);

            
            int Year = pref.getInt("Year",2011);
            textYear.setText("" + Year);
            int Month = pref.getInt("Month",06);
            textMonth.setText("" + Month);
            int Date = pref.getInt("Date",15);
            textDate.setText("" + Date);
            
    }

    public void onPause() {
            super.onPause();

            SharedPreferences pref = getSharedPreferences("fun",0);
            SharedPreferences.Editor edit = pref.edit();


            int Year = 0;
            int Month = 0;
            int Date = 0;
            try {
                Year = Integer.parseInt(textYear.getText().toString());
            } catch (Exception e) {}
            try {
                Month = Integer.parseInt(textMonth.getText().toString());
            } catch (Exception e) {}
            try {
                Date = Integer.parseInt(textDate.getText().toString());
            } catch (Exception e) {}


            if(Year>1900)
            	edit.putInt("Year", Year);
            if(Month<=12 && Month>=1)
            	edit.putInt("Month", Month);
            if(Date<=31 && Date>=1)
            	edit.putInt("Date", Date);

            edit.commit();
    } 
    
    public void mOnClick(View v) {
    	switch (v.getId()) {
    	case R.id.Weather:
    		showWeather();
    		break;
    	case R.id.Myfortune:
    		showMyfortune();
    		break;
    	case R.id.Biorhythm:
    		showBiorhythm();
    		break;
    	}
    }
	
	public void showWeather(){
		WebView webView;
		
		webView = (WebView)findViewById(R.id.webView);
		webView.loadUrl("http://www.kma.go.kr/weather/main.jsp");
	}
	public void showMyfortune(){
		WebView webView;
		
		int Year = Integer.parseInt(textYear.getText().toString());
		int iAnimal = Year%12;
		String sAnimal;
		if(iAnimal==9)
			sAnimal = "¹ì";
		else if(iAnimal==10)
			sAnimal = "¸»";
		else if(iAnimal==11)
			sAnimal = "¾ç";
		else if(iAnimal==0)
			sAnimal = "¿ø¼þÀÌ";
		else if(iAnimal==1)
			sAnimal = "´ß";
		else if(iAnimal==2)
			sAnimal = "°³";
		else if(iAnimal==3)
			sAnimal = "µÅÁö";
		else if(iAnimal==4)
			sAnimal = "Áã";
		else if(iAnimal==5)
			sAnimal = "¼Ò";
		else if(iAnimal==6)
			sAnimal = "È£¶ûÀÌ";
		else if(iAnimal==7)
			sAnimal = "Åä³¢";
		else
			sAnimal = "¿ë";
		
		webView = (WebView)findViewById(R.id.webView);
		webView.loadUrl("http://www.unselove.net/saju/unsetoday.php#" + sAnimal);
	}
	public void showBiorhythm(){
		WebView webView;
		
		
		int Year = Integer.parseInt(textYear.getText().toString());
		int Month = Integer.parseInt(textMonth.getText().toString());
		int Date = Integer.parseInt(textDate.getText().toString());
		
		webView = (WebView)findViewById(R.id.webView);
		webView.loadUrl("http://www.60gabja.com/bio/013_modujobio.php3?birth_year=" + Year + "&birth_month=" + Month + "&birth_day=" + Date);
		
	}
}
