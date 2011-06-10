package com.cmonkeys.mcalendar.view;

import com.cmonkeys.mcalendar.R;
import com.cmonkeys.mcalendar.view.mkCalendar;
import com.cmonkeys.mcalendar.view.mkCalendar.mkCalendarColorParam;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mkCalendarType1 extends Activity {
	TextView tvs[] ;
	Button btns[] ;
    

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mkcalendartype1);
        
        /// 달력을 띄울 대상 레이아웃
        LinearLayout lv = (LinearLayout)findViewById( R.id.calendar_lLayout ) ;
        
        /// 년 월 일 표시할 텍스트뷰
        tvs = new TextView[3] ;
        tvs[0] = (TextView)findViewById( R.id.tv1 ) ;
        tvs[1] = (TextView)findViewById( R.id.tv2 ) ;
        tvs[2] = null ; /// 일은 표시하지 않음
        
        /// 누르면 년 월 일 조절할 버튼
        btns = new Button[4] ;
        btns[0] = null ; // 년도는 조절하지 않음
        btns[1] = null ; // 위와 동일
        btns[2] = (Button)findViewById( R.id.Button03 ) ;
        btns[3] = (Button)findViewById( R.id.Button04 ) ;
        
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
        // Drawable img = getResources( ).getDrawable( R.drawable.bg ) ;
        // 배경 이미지 셋~
        //cal.setBackground( img ) ;
        
        /// 달력을 띄울 크기 지정
        cal.setCalendarSize( 478, 500 ) ;
        
        /// 최상단은 높이를 35로 준다(전체높이중 한 셀의 높이 600/7한 값에서 35로 변경되니 달력의 총 높이가 줄어든다.)
        cal.setTopCellSize( 35 ) ;
        
        /// 누르면 반응할 버튼들 셋팅
        cal.setControl( btns ) ;
        
        /// 년 월 일을 띄울 텍스트뷰 셋팅
        cal.setViewTarget( tvs ) ;
        
        cal.initCalendar( ) ;
    }
}
