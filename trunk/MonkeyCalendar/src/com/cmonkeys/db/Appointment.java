package com.cmonkeys.db;

import java.util.Date;

public class Appointment extends Article {
	
	private String m_parts;
	private String m_location;
	private Date m_start;
	private Date m_end;
	private boolean m_repeatWeekly;
	private boolean m_repeatMonthly;
	
	//constructor
	public Appointment(int newIndex, String newTitle, String newDescription, Date lastUpdate, 
			String newParticipant, String newLocation, Date newStarttime, Date newFinishtime, int currentIndex)
	{
		setIndex(newIndex);
		setTitle(newTitle);
		setDescription(newDescription);
		setLastUpdate(lastUpdate);
		
		setM_parts(newParticipant);
		setM_location(newLocation);
		setM_start(newStarttime);
		setM_end(newFinishtime);
		
		setIndexAtCurrent(currentIndex);
	}
	
	public void setM_location(String m_location) {
		this.m_location = m_location;
	}
	public String getM_location() {
		return m_location;
	}
	public void setM_parts(String m_parts) {
		this.m_parts = m_parts;
	}
	public String getM_parts() {
		return m_parts;
	}
	public void setM_start(Date m_start) {
		this.m_start = m_start;
	}
	public Date getM_start() {
		return m_start;
	}
	public void setM_end(Date m_end) {
		this.m_end = m_end;
	}
	public Date getM_end() {
		return m_end;
	}
	
	
}
