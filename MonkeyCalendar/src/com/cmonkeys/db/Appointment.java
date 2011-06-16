package com.cmonkeys.db;

import java.util.Date;

public class Appointment extends Article {
	public static final int NO_REPEAT = 0;
	public static final int REPEAT_YEARLY = 1;
	public static final int REPEAT_MONTHLY = 2;
	public static final int REPEAT_WEEKLY = 3;
	
	private String m_parts;
	private String m_location;
	private Date m_start;
	private Date m_end;
	private int m_repeat;
		
	//constructor
	public Appointment(
			int newIndex, String newTitle, String newDescription, Date lastUpdate, 
			String newParticipant, String newLocation, Date newStartTime, Date newEndTime,
			int repeat, int currentIndex)
	{
		setIndex(newIndex);
		setTitle(newTitle);
		setDescription(newDescription);
		setLastUpdate(lastUpdate);
		
		setParticipants(newParticipant);
		setLocation(newLocation);
		setStart(newStartTime);
		setEnd(newEndTime);
		
		setRepeat(repeat);
		
		setIndexAtCurrent(currentIndex);
	}
	
	private void setLocation(String m_location) {
		this.m_location = m_location;
	}
	
	public String getLocation() {
		return m_location;
	}
	
	private void setParticipants(String m_parts) {
		this.m_parts = m_parts;
	}
	
	public String getParticipants() {
		return m_parts;
	}
	private void setStart(Date m_start) {
		this.m_start = m_start;
	}
	public Date getStart() {
		return m_start;
	}
	private void setEnd(Date m_end) {
		this.m_end = m_end;
	}
	public Date getEnd() {
		return m_end;
	}

	private void setRepeat(int m_repeat) {
		this.m_repeat = m_repeat;
	}

	public int getRepeat() {
		return m_repeat;
	}
}
