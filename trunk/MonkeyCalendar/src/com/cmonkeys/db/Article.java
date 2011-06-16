package com.cmonkeys.db;

import java.util.Date;

public class Article {
	// data
	private int index;
	private int m_indexAtCurrent;
	private String title;
	private String description;
	private Date lastUpdate;
	
	// constructor
	public Article()
	{
		setTitle("");
		setDescription("");
		setLastUpdate(new Date());
	}
	
	public Article(int newIndex, String newTitle, String newDescription, Date lastUpdate, int currentIndex)
	{
		setIndex(newIndex);
		setTitle(newTitle);
		setDescription(newDescription);
		setLastUpdate(lastUpdate);
		setIndexAtCurrent(currentIndex);
	}
	
	// method
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndexAtCurrent(int m_indexAtCurrent) {
		this.m_indexAtCurrent = m_indexAtCurrent;
	}

	public int getIndexAtCurrent() {
		return m_indexAtCurrent;
	}
}
