package com.cmonkeys.db;

import java.util.Date;

public class Appointment extends Article {
	private String m_location;
	private String m_parts;
	private Date m_start;
	private Date m_end;
	
	public Appointment()
	{
		
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
