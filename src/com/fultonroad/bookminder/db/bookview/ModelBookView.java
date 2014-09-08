package com.fultonroad.bookminder.db.bookview;

public class ModelBookView {

	private int mID;
	private String mTitle;
	private String mAuthorFirst;
	private String mAuthorLast;
	private String mAuthorSubFirst;
	private String mAuthorSubLast;
	private Boolean mOwnIt;
	private Boolean mReadIt;

	public ModelBookView() {
		super();
	}

	public ModelBookView(int mID, String mTitle, String authorFirst, String authorLast, String authorSubFirst, String authorSubLast) {
		super();
		this.mID = mID;
		this.mTitle = mTitle;
		this.mAuthorFirst = authorFirst;
		this.mAuthorLast = authorLast;
		this.mAuthorSubFirst = authorSubFirst;
		this.mAuthorSubLast = authorSubLast;
	}

	public int getID() {
		return mID;
	}

	public void setID(int mID) {
		this.mID = mID;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getAuthorFirst() {
		return mAuthorFirst;
	}

	public void setAuthorFirst(String authorFirst) {
		this.mAuthorFirst = authorFirst;
	}

	public String getAuthorLast() {
		return mAuthorLast;
	}

	public void setAuthorLast(String authorLast) {
		this.mAuthorLast = authorLast;
	}

	public String getAuthorSubFirst() {
		return mAuthorSubFirst;
	}

	public void setAuthorSubFirst(String authorSubFirst) {
		this.mAuthorSubFirst = authorSubFirst;
	}

	public String getAuthorSubLast() {
		return mAuthorSubLast;
	}

	public void setAuthorSubLast(String authorSubLast) {
		this.mAuthorSubLast = authorSubLast;
	}

	public Boolean getOwnIt() {
		return mOwnIt;
	}

	public void setOwnIt(Boolean ownIt) {
		this.mOwnIt = ownIt;
	}

	public Boolean getReadIt() {
		return mReadIt;
	}

	public void setReadIt(Boolean readIt) {
		this.mReadIt = readIt;
	}
	
}
