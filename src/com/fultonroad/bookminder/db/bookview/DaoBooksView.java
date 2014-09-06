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
	protected DBHelper mDBHelper;
	
	private static final String kVIEW_NAME = "viewBook"; 	
	private static final String kCOLUMN_ID = "id"; 	
	private static final String kCOLUMN_TITLE = "title"; 	
	private static final String kCOLUMN_AUTHOR_FIRST = "authorFirst";
	private static final String kCOLUMN_AUTHOR_LAST = "authorLast";
	private static final String kCOLUMN_SUB_AUTHOR_FIRST = "subAuthorFirst";
	private static final String kCOLUMN_SUB_AUTHOR_LAST = "subAuthorLast";

	public DaoBooksView(DBHelper hlpr) {
		super();
		mDBHelper = hlpr;
	}
	
	public static String createViewDdl() {
	
		return "CREATE VIEW " + kVIEW_NAME + " AS " + selectAllQuery(); 
			
	}

	public static String selectAllQuery() {
		
		return " SELECT " + DaoBooks.kQUALIFIED_ID + " AS " + kCOLUMN_ID + ", " + DaoBooks.kQUALIFIED_TITLE + " AS " + kCOLUMN_TITLE + ", author." + DaoAuthors.kCOLUMN_FIRST_NAME + " AS " + kCOLUMN_AUTHOR_FIRST + ","
			+ " author." + DaoAuthors.kCOLUMN_LAST_NAME + " AS " + kCOLUMN_AUTHOR_LAST + ", subAuthor." + DaoAuthors.kCOLUMN_FIRST_NAME + " AS " + kCOLUMN_SUB_AUTHOR_FIRST + ","
			+ " subAuthor." + DaoAuthors.kCOLUMN_LAST_NAME + " AS " + kCOLUMN_SUB_AUTHOR_LAST + " FROM " + DaoBooks.kTABLE_NAME 
			+ " INNER JOIN " + DaoAuthors.kTABLE_NAME + " AS author ON " + DaoBooks.kQUALIFIED_AUTHOR_MAJOR + "=author." + DaoAuthors.kCOLUMN_ID 
			+ " INNER JOIN " + DaoAuthors.kTABLE_NAME + " AS subAuthor ON " + DaoBooks.kQUALIFIED_AUTHOR_MINOR + "=subAuthor." + DaoAuthors.kCOLUMN_ID;  

	}

	
	public List<ModelBookView> read() {
		
		SQLiteDatabase database = mDBHelper.getReadableDatabase();
		List<ModelBookView> bookviews = new ArrayList<ModelBookView>();  		
		
		String orderby = kCOLUMN_TITLE + " ASC";
		
		Cursor cursor = database.query(kVIEW_NAME, null, null, null, null, null, orderby);
		
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				bookviews.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		try {
			database.close();
		} catch (CursorIndexOutOfBoundsException ex1) {
			Log.v("DB1", ex1.toString());
		} catch (Exception ex2) {
			Log.v("DB2", ex2.toString());
		}
		
		return bookviews;
	}
	
	
	
	
	public List<ModelBookView> read(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

		SQLiteDatabase database = mDBHelper.getReadableDatabase();
		
		List<ModelBookView> bookviews = new ArrayList<ModelBookView>();  		
	
		Cursor cursor = database.query(kVIEW_NAME, getAllColumns(), selection, selectionArgs, null, null, null);
		
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				bookviews.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		try {
			database.close();
		} catch (CursorIndexOutOfBoundsException ex1) {
			System.out.println("PAUSE1");
			Log.v("DB1", ex1.toString());
		} catch (Exception ex2) {
			Log.v("DB2", ex2.toString());
		}
		
		return bookviews;
	
	}
	
	public String[] getAllColumns() {
		return new String[] { kCOLUMN_ID, kCOLUMN_TITLE, kCOLUMN_AUTHOR_FIRST, kCOLUMN_AUTHOR_LAST, kCOLUMN_SUB_AUTHOR_FIRST, kCOLUMN_SUB_AUTHOR_LAST };
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
		return bv;
	}
}
