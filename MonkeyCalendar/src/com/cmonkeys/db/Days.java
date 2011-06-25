package com.cmonkeys.db;

public class Days extends Article {
	private int m_solarDate;
	private int m_lunarDate;
	private boolean m_isHoliday;
	
	public Days(int newSolarDate, int newLunarDate, 
			String newNameOfHoliday, boolean newIsHoliday)
	{
		setSolarDate(newSolarDate);
		setLunarDate(newLunarDate);
		setTitle(newNameOfHoliday);
		setIsHoliday(newIsHoliday);
	}
	
	public int getSolarDate() 
	{
		return m_solarDate;
	}
	public void setSolarDate(int solarDate) 
	{
		this.m_solarDate = solarDate;
	}
	public int getLunarDate()
	{
		return m_lunarDate;
	}
	public String getLunarDateAsString() 
	{
		int year = m_lunarDate / 10000;
		int month = (m_lunarDate - (year * 10000)) / 100;
		int date = m_lunarDate - (year * 10000) - (month * 100);
		
		return "" + month + "/" + date;
	}
	public void setLunarDate(int lunarDate) 
	{
		this.m_lunarDate = lunarDate;
	}
	public boolean getIsHoliday() 
	{
		return m_isHoliday;
	}
	public void setIsHoliday(boolean isHoliday) 
	{
		this.m_isHoliday = isHoliday;
	}
	
	
}
