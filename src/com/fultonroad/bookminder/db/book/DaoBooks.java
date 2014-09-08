package com.fultonroad.bookminder.db.book;

import java.util.ArrayList;
import java.util.List;

import com.fultonroad.bookminder.db.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DaoBooks extends Dao<ModelBook> {

	public static final String kTABLE_NAME = "tblBooks";
	public static final String kCOLUMN_ID = "_id";
	public static final String kCOLUMN_TITLE = "title";
	public static final String kCOLUMN_YEAR = "year";
	public static final String kCOLUMN_AUTHOR = "author_id";
	public static final String kCOLUMN_SUB_AUTHOR = "subAuthor_id";
	public static final String kCOLUMN_OWN_IT = "ownIt";
	public static final String kCOLUMN_READ_IT = "readIt";
	public static final String kCOLUMN_SERIES = "series_id";
	

	public static final String kQUALIFIED_ID = kTABLE_NAME + "." + kCOLUMN_ID;
	public static final String kQUALIFIED_TITLE = kTABLE_NAME + "." + kCOLUMN_TITLE;
	public static final String kQUALIFIED_YEAR = kTABLE_NAME + "." + kCOLUMN_YEAR;
	public static final String kQUALIFIED_AUTHOR_MAJOR = kTABLE_NAME + "." + kCOLUMN_AUTHOR;
	public static final String kQUALIFIED_AUTHOR_MINOR = kTABLE_NAME + "." + kCOLUMN_SUB_AUTHOR;
	public static final String kQUALIFIED_OWN_IT = kTABLE_NAME + "." + kCOLUMN_OWN_IT;
	public static final String kQUALIFIED_READ_IT = kTABLE_NAME + "." + kCOLUMN_READ_IT;
	public static final String kQUALIFIED_SERIES = kTABLE_NAME + "." + kCOLUMN_SERIES;
	
	public DaoBooks(SQLiteDatabase db) {
		super();
		mDatabase = db;
	}

	public static String createTableDdl() {

		return "CREATE TABLE " + DaoBooks.kTABLE_NAME + "("
			+ kCOLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ kCOLUMN_TITLE + " TEXT NOT NULL, " 
			+ kCOLUMN_YEAR + " INTEGER NULL, " 
			+ kCOLUMN_AUTHOR	+ " INTEGER NOT NULL, "	
			+ kCOLUMN_SUB_AUTHOR	+ " INTEGER NOT NULL, "
			+ kCOLUMN_OWN_IT	+ " INTEGER NOT NULL, "
			+ kCOLUMN_READ_IT	+ " INTEGER NOT NULL, "
			+ kCOLUMN_SERIES	+ " INTEGER NOT NULL" + ")";

	}
	
	@Override
	public boolean insert(ModelBook entity) {
		if (entity == null) {
			return false;
		}

		long result = mDatabase.insert(kTABLE_NAME, null, generateContentValuesFromObject(entity));

		return result != 0;
	}

	@Override
	public boolean delete(ModelBook entity) {
		if (entity == null) {
			return false;
		}
		
		int result = mDatabase.delete(kTABLE_NAME, kCOLUMN_ID + " = " + entity.getID(), null);
		
		return result != 0;
	}

	public boolean deleteForAuthor(int authID) {
		
		int result = mDatabase.delete(kTABLE_NAME, kCOLUMN_AUTHOR + "=" + authID + " OR " + kCOLUMN_SUB_AUTHOR + "=" + authID, null);
		
		return result != 0;
	}

	@Override
	public boolean update(ModelBook entity) {
		if (entity == null) {
			return false;
		}
		
		int result = mDatabase.update(kTABLE_NAME,
				generateContentValuesFromObject(entity), kCOLUMN_ID + " = "
						+ entity.getID(), null);
		
		return result != 0;
	}
	
	/**
	 * WARNING: Can return an empty List
	 */
	@Override
	public List<ModelBook> read() {
		
		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), null, null, null, null, null);
		List<ModelBook> lst = new ArrayList<ModelBook>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				lst.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return lst;
	}

	@Override
	public List<ModelBook> read(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), selection, selectionArgs, groupBy, having, orderBy);
		List<ModelBook> lst = new ArrayList<ModelBook>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				lst.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}


		return lst;
	}

	public String[] getAllColumns() {
		return new String[] { kCOLUMN_ID, kCOLUMN_TITLE, kCOLUMN_YEAR, kCOLUMN_AUTHOR, kCOLUMN_SUB_AUTHOR, kCOLUMN_OWN_IT, kCOLUMN_READ_IT, kCOLUMN_SERIES };
	}

	public ModelBook generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		
		ModelBook book = new ModelBook();
		book.setID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_ID)));
		book.setTitle(cursor.getString(cursor.getColumnIndex(kCOLUMN_TITLE)));
		book.setYear(cursor.getInt(cursor.getColumnIndex(kCOLUMN_YEAR)));
		book.setAuthorID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_AUTHOR)));
		book.setSubAuthorID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_SUB_AUTHOR)));
		book.setSeriesID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_SERIES)));
		
		if (cursor.getInt(cursor.getColumnIndex(kCOLUMN_OWN_IT)) > 0)
			book.setOwnIt(true);
		else 
			book.setOwnIt(false);

		if (cursor.getInt(cursor.getColumnIndex(kCOLUMN_READ_IT)) > 0)
			book.setReadIt(true);
		else
			book.setReadIt(true);
		
		return book;
	}

	public ContentValues generateContentValuesFromObject(ModelBook entity) {
		if (entity == null) {
			return null;
		}
		
		ContentValues values = new ContentValues();
		values.put(kCOLUMN_TITLE, entity.getTitle());
		values.put(kCOLUMN_YEAR, entity.getYear());
		values.put(kCOLUMN_AUTHOR, entity.getAuthorID());
		values.put(kCOLUMN_SUB_AUTHOR, entity.getSubAuthorID());
		values.put(kCOLUMN_OWN_IT, entity.getOwnIt());
		values.put(kCOLUMN_READ_IT, entity.getReadIt());
		values.put(kCOLUMN_SERIES, entity.getSeriesID());

		return values;
	}

	
	
	
	
	/*
	 * 
	 * 		C U R S O R S
	 * 
	 */
	public Cursor getBooksCursor(int key) {
		
		String selection = DaoBooks.kCOLUMN_AUTHOR + "=? OR " + DaoBooks.kCOLUMN_SUB_AUTHOR + "=?";
		String[] selectionArgs = new String[] { Integer.toString(key), Integer.toString(key) };  
		
		String orderby = kCOLUMN_YEAR + ", " + kCOLUMN_TITLE + " ASC";
		return mDatabase.query(kTABLE_NAME, getAllColumns(), selection, selectionArgs, null, null, orderby);
		
	}
	
	
}
