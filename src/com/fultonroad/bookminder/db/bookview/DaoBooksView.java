package com.fultonroad.bookminder.db.bookview;

import java.util.ArrayList;
import java.util.List;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.book.DaoBooks;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DaoBooksView {

	SQLiteDatabase mDatabase = null;
	
	public static final String kVIEW_NAME = "viewBook"; 	
	public static final String kCOLUMN_ID = "_id"; 	
	public static final String kCOLUMN_TITLE = "title"; 	
	public static final String kCOLUMN_AUTHOR_FIRST = "authorFirst";
	public static final String kCOLUMN_AUTHOR_LAST = "authorLast";
	public static final String kCOLUMN_SUB_AUTHOR_FIRST = "subAuthorFirst";
	public static final String kCOLUMN_SUB_AUTHOR_LAST = "subAuthorLast";

	public DaoBooksView(SQLiteDatabase db) {
		super();
		mDatabase = db;
	}
	
	public static String createViewDdl() {
	
		return "CREATE VIEW " + kVIEW_NAME + " AS " + selectAllQuery(); 
			
	}

	public static String selectAllQuery() {
		
		return " SELECT " + DaoBooks.kQUALIFIED_ID + " AS " + kCOLUMN_ID + ", " + DaoBooks.kQUALIFIED_TITLE + " AS " + kCOLUMN_TITLE + ", author." + DaoAuthors.kCOLUMN_FIRST_NAME + " AS " + kCOLUMN_AUTHOR_FIRST + ","
			+ " author." + DaoAuthors.kCOLUMN_LAST_NAME + " AS " + kCOLUMN_AUTHOR_LAST + ", subAuthor." + DaoAuthors.kCOLUMN_FIRST_NAME + " AS " + kCOLUMN_SUB_AUTHOR_FIRST + ","
			+ " subAuthor." + DaoAuthors.kCOLUMN_LAST_NAME + " AS " + kCOLUMN_SUB_AUTHOR_LAST + ", " + DaoBooks.kQUALIFIED_OWN_IT + ", " + DaoBooks.kQUALIFIED_READ_IT +  " FROM " + DaoBooks.kTABLE_NAME 
			+ " INNER JOIN " + DaoAuthors.kTABLE_NAME + " AS author ON " + DaoBooks.kQUALIFIED_AUTHOR_MAJOR + "=author." + DaoAuthors.kCOLUMN_ID 
			+ " INNER JOIN " + DaoAuthors.kTABLE_NAME + " AS subAuthor ON " + DaoBooks.kQUALIFIED_AUTHOR_MINOR + "=subAuthor." + DaoAuthors.kCOLUMN_ID;  

	}

	public List<ModelBookView> read() {
		
		List<ModelBookView> bookviews = new ArrayList<ModelBookView>();  		
		
		String orderby = kCOLUMN_TITLE + " ASC";
		
		Cursor cursor = mDatabase.query(kVIEW_NAME, null, null, null, null, null, orderby);
		
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				bookviews.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return bookviews;
	}
	
	public List<ModelBookView> read(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

		List<ModelBookView> bookviews = new ArrayList<ModelBookView>();  		
	
		Cursor cursor = mDatabase.query(kVIEW_NAME, getAllColumns(), selection, selectionArgs, null, null, null);
		
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				bookviews.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return bookviews;
	}
	
	public String[] getAllColumns() {
		return new String[] { kCOLUMN_ID, kCOLUMN_TITLE, kCOLUMN_AUTHOR_FIRST, kCOLUMN_AUTHOR_LAST, kCOLUMN_SUB_AUTHOR_FIRST, kCOLUMN_SUB_AUTHOR_LAST, DaoBooks.kCOLUMN_OWN_IT, DaoBooks.kCOLUMN_READ_IT  };
	}
	
	public List<ModelBookView> getBooksForAuthor(int key) {
		
		String[] args = new String[] { Integer.toString(key) };
		return read(kCOLUMN_ID, args, null, null, null); 
		
	}
	
	public ModelBookView generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		
		ModelBookView bv= new ModelBookView();
		bv.setID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_ID)));
		bv.setTitle(cursor.getString(cursor.getColumnIndex(kCOLUMN_TITLE)));
		bv.setAuthorFirst(cursor.getString(cursor.getColumnIndex(kCOLUMN_AUTHOR_FIRST)));
		bv.setAuthorLast(cursor.getString(cursor.getColumnIndex(kCOLUMN_AUTHOR_LAST)));
		bv.setAuthorSubFirst(cursor.getString(cursor.getColumnIndex(kCOLUMN_SUB_AUTHOR_FIRST)));
		bv.setAuthorSubLast(cursor.getString(cursor.getColumnIndex(kCOLUMN_SUB_AUTHOR_LAST)));
		
		if (cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_OWN_IT)) > 0) {
			bv.setOwnIt(true);
		} else {
			bv.setOwnIt(false);
		}
		
		if (cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_READ_IT)) > 0) {
			bv.setReadIt(true);
		} else {
			bv.setReadIt(false);
		}
		
		return bv;
	}
	
	public void closeDatabase() {
		try {
			mDatabase.close();
		} catch (CursorIndexOutOfBoundsException ex1) {
			Log.v("DB1", ex1.toString());
		} catch (Exception ex2) {
			Log.v("DB2", ex2.toString());
		}
	}
	
	
	
	
	
	
	/*
	 * 
	 * 		C U R S O R S
	 * 
	 */
	public Cursor getBooksViewCursor() {
		
		String orderby = kCOLUMN_TITLE + " ASC";
		return mDatabase.query(kVIEW_NAME, getAllColumns(), null, null, null, null, orderby);
		
	}
}

