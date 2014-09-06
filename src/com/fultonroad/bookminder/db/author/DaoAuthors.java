package com.fultonroad.bookminder.db.author;

import java.util.ArrayList;
import java.util.List;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.Dao;
import com.fultonroad.bookminder.db.book.DaoBooks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DaoAuthors extends Dao<ModelAuthor> {

	public static final String kTABLE_NAME = "tblAuthors";
	public static final String kCOLUMN_ID = "_id";
	public static final String kCOLUMN_FIRST_NAME = "firstName";
	public static final String kCOLUMN_LAST_NAME = "lastName";

	public static final String kQUALIFIED_ID = kTABLE_NAME + "." + kCOLUMN_ID;
	public static final String kQUALIFIED_FIRST_NAME = kTABLE_NAME + "." + kCOLUMN_FIRST_NAME;
	public static final String kQUALIFIED_LAST_NAME = kTABLE_NAME + "." + kCOLUMN_LAST_NAME;

	
	protected DBHelper mDBHelper;

	public DaoAuthors(DBHelper hlpr) {
		super();
		mDBHelper = hlpr;
	}

	public DaoAuthors(SQLiteDatabase db) {
		super();
		mDatabase = db;
	}

	public static String createTableDdl() {
		
		return "CREATE TABLE " + DaoAuthors.kTABLE_NAME + " (" + DaoAuthors.kCOLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DaoAuthors.kCOLUMN_FIRST_NAME + " TEXT NOT NULL, "
			+ DaoAuthors.kCOLUMN_LAST_NAME + " TEXT NOT NULL)";

	}
	
	
	@Override
	public boolean insert(ModelAuthor entity) {
		if (entity == null) {
			return false;
		}

		long result = mDatabase.insert(kTABLE_NAME, null,
				generateContentValuesFromObject(entity));
	
		return result != -1;
	}

	@Override
	public boolean delete(ModelAuthor entity) {
		Boolean retval = true;
		
		if (entity == null) {
			return false;
		}
		
		if (retval) {
			mDatabase = mDBHelper.getWritableDatabase();
	
			int result = mDatabase.delete(kTABLE_NAME, kCOLUMN_ID + " = " + entity.getID(), null);

			if (result == 0)
				retval = false;
			
			try {
				mDatabase.close();
			} catch (CursorIndexOutOfBoundsException ex1) {
				Log.v("DB1", ex1.toString());
				retval = false;
			} catch (Exception ex2) {
				Log.v("DB2", ex2.toString());
				retval = false;
			}
		} 
		
		return retval;
	}

	@Override
	public boolean update(ModelAuthor entity) {
		if (entity == null) {
			return false;
		}
		
		String whereClause = "WHERE ";
		whereClause += kCOLUMN_ID;
		whereClause += "="; 
		whereClause += entity.getID();
		Log.v("JMD", whereClause);
		
		int result = mDatabase.update(kTABLE_NAME,
				generateContentValuesFromObject(entity), kCOLUMN_ID + "="
						+ entity.getID(), null);
		
		return result != 0;
	}
	
	
	/**
	 * WARNING: Can return an empty List
	 */
	@Override
	public List<ModelAuthor> read() {
		
		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), null, null, null, null, null);
		List<ModelAuthor> lst = new ArrayList<ModelAuthor>();
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
	public List<ModelAuthor> read(String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		
		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List<ModelAuthor> lst = new ArrayList<ModelAuthor>();
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
		return new String[] { kCOLUMN_ID, kCOLUMN_FIRST_NAME, kCOLUMN_LAST_NAME };
	}

	public ModelAuthor generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		
		ModelAuthor author = new ModelAuthor();
		author.setID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_ID)));
		author.setFirstName(cursor.getString(cursor.getColumnIndex(kCOLUMN_FIRST_NAME)));
		author.setLastName(cursor.getString(cursor.getColumnIndex(kCOLUMN_LAST_NAME)));
		
		return author;
	}

	public ContentValues generateContentValuesFromObject(ModelAuthor entity) {
		if (entity == null) {
			return null;
		}
		
		ContentValues values = new ContentValues();
		values.put(kCOLUMN_FIRST_NAME, entity.getFirstName());
		values.put(kCOLUMN_LAST_NAME, entity.getLastName());
		
		return values;
	}

	public Boolean exists(ModelAuthor entity) {

		Boolean retval = false;
		
		String[] args = new String[] { entity.getFirstName(), entity.getLastName() };
		
		
		List<ModelAuthor> list = read(kCOLUMN_FIRST_NAME + "=? AND " + kCOLUMN_LAST_NAME + "=? ", args, null, null, null);
		
		if (list.size() > 0)
			retval = true;
		
		return retval;
	}

}