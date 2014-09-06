package com.fultonroad.bookminder.db.book;

public class ModelBook {

	private int mID;
	private String mTitle;
	private int mAuthorID;
	private int mSubAuthorID;

	public ModelBook() {
		super();
	}
	
	public ModelBook(int key, String name, int author, int subAuthor) {
		super();
		this.mID = key;
		this.mTitle = name;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
	}

	public ModelBook(String name, int author, int subAuthor) {
		super();
		this.mID = 0;
		this.mTitle = name;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
	}

	public int getID() {
		return mID;
	}
	
	public void setID(int key) {
		this.mID = key;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}

	public int getAuthorID() {
		return mAuthorID;
	}
	
	public void setAuthorID(int key) {
		this.mAuthorID = key;
	}
	
	public int getSubAuthorID() {
		return mSubAuthorID;
	}
	
	public void setSubAuthorID(int key) {
		this.mSubAuthorID = key;
	}
	
}
