package com.cmonkeys.db;

import java.util.Date;

public class Days extends Article {
	
	private Date m_solarDate;
	private Date m_lunarDate;
	private String m_nameOfHoliday;
	private boolean m_isHoliday;
	
	//constructor
	public Days()
	{
		
	}
	
	public Days(Date newSolarDate, Date newLunarDate, Date lastUpdate, 
			String newNameOfHoliday, boolean newIsHoliday)
	{
		setM_solarDate(newSolarDate);
		setM_lunarDate(newLunarDate);
		setM_nameOfHoliday(newNameOfHoliday);
		setM_isHoliday(newIsHoliday);
	}
	
	public Date getM_solarDate() {
		return m_solarDate;
	}
	public void setM_solarDate(Date m_solarDate) {
		this.m_solarDate = m_solarDate;
	}
	public Date getM_lunarDate() {
		return m_lunarDate;
	}
	public void setM_lunarDate(Date m_lunarDate) {
		this.m_lunarDate = m_lunarDate;
	}
	public String getM_nameOfHoliday() {
		return m_nameOfHoliday;
	}
	public void setM_nameOfHoliday(String m_nameOfHoliday) {
		this.m_nameOfHoliday = m_nameOfHoliday;
	}
	public boolean isM_isHoliday() {
		return m_isHoliday;
	}
	public void setM_isHoliday(boolean m_isHoliday) {
		this.m_isHoliday = m_isHoliday;
	}
	
	
}
