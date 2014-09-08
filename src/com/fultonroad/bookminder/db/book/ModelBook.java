package com.fultonroad.bookminder.db.book;

public class ModelBook {

	private int mID = 0;
	private String mTitle;
	private int mYear = 0;
	private int mAuthorID;
	private int mSubAuthorID;
	private Boolean mOwnIt = false;
	private Boolean mReadIt = false;
	private int mSeriesID = 0;

	public ModelBook() {
		super();
	}
	
	public ModelBook(int key, String title, int author, int subAuthor) {
		super();
		this.mID = key;
		this.mTitle = title;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
	}

	public ModelBook(String title, int author, int subAuthor) {
		super();
		this.mTitle = title;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
	}

	public ModelBook(String title, int yr, int author, int subAuthor, Boolean own, Boolean read) {
		super();
		this.mTitle = title;
		this.mYear = yr;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
		this.mOwnIt = own;
		this.mReadIt = read;
		this.mSeriesID = 0;
	}

	public ModelBook(String title, int yr, int author, int subAuthor, Boolean own, Boolean read, int series) {
		super();
		this.mTitle = title;
		this.mYear = yr;
		this.mAuthorID = author;
		this.mSubAuthorID = subAuthor;
		this.mOwnIt = own;
		this.mReadIt = read;
		this.mSeriesID = series;
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

	public int getYear() {
		return mYear;
	}
	
	public void setYear(int yr) {
		this.mYear = yr;
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
	
	public Boolean getOwnIt() {
		return mOwnIt;
	}
	
	public void setOwnIt(Boolean flag) {
		this.mOwnIt = flag;
	}
	
	public Boolean getReadIt() {
		return mReadIt;
	}
	
	public void setReadIt(Boolean flag) {
		this.mReadIt = flag;
	}

	public int getSeriesID() {
		return mSeriesID;
	}
	
	public void setSeriesID(int key) {
		this.mSeriesID = key;
	}
	
}
