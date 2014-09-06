package com.fultonroad.bookminder.db.author;

public class ModelAuthor {

	private int mID;
	private String mFirstName;
	private String mLastName;

	public ModelAuthor() {
		super();
	}
	
	public ModelAuthor(int key) {
		super();
		this.mID = key;
	}
	
	public ModelAuthor(String fName, String lName) {
		super();
		this.mFirstName = fName;
		this.mLastName = lName;
	}

	public ModelAuthor(int key, String fName, String lName) {
		super();
		this.mID = key;
		this.mFirstName = fName;
		this.mLastName = lName;
	}
	
	public int getID() {
		return mID;
	}
	
	public void setID(int key) {
		this.mID = key;
	}
	
	public String getFirstName() {
		return mFirstName;
	}
	
	public void setFirstName(String fName) {
		this.mFirstName = fName;
	}
	
	public String getLastName() {
		return mLastName;
	}
	
	public void setLastName(String lName) {
		this.mLastName = lName;
	}
	
}
