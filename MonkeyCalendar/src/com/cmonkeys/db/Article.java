package com.cmonkeys.db;

import java.util.Date;

public class Article {
	// data
	private int index;
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
	
	
}
