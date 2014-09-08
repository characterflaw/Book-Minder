package com.fultonroad.bookminder.db.series;

public class ModelSeries {

	
	private int mID = 0;
	private int mAuthorID = 0;
	private String mName = "";

	
	public ModelSeries() {
		super();
	}
	
	public ModelSeries(int id, int authorID, String name) {
		super();
		mID = id;
		mAuthorID = authorID;
		mName = name;
	}

	
	public int getID() {
		return mID;
	}

	public void setID(int key) {
		this.mID = key;
	}

	public int getAuthorID() {
		return mAuthorID;
	}

	public void setAuthorID(int key) {
		this.mAuthorID = key;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}
	
	
	
}
