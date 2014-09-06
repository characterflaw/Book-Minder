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
	public static final String kCOLUMN_AUTHOR = "author";
	public static final String kCOLUMN_SUB_AUTHOR = "subAuthor";

	public static final String kQUALIFIED_ID = kTABLE_NAME + "." + kCOLUMN_ID;
	public static final String kQUALIFIED_TITLE = kTABLE_NAME + "." + kCOLUMN_TITLE;
	public static final String kQUALIFIED_AUTHOR_MAJOR = kTABLE_NAME + "." + kCOLUMN_AUTHOR;
	public static final String kQUALIFIED_AUTHOR_MINOR = kTABLE_NAME + "." + kCOLUMN_SUB_AUTHOR;

	
	public DaoBooks(SQLiteDatabase database) {
		super(database);
	}

	public static String createTableDdl() {

		return "CREATE TABLE " + DaoBooks.kTABLE_NAME + "("
			+ DaoBooks.kCOLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DaoBooks.kCOLUMN_TITLE + " TEXT NOT NULL, " 
			+ DaoBooks.kCOLUMN_AUTHOR	+ " INTEGER NOT NULL, "	
			+ DaoBooks.kCOLUMN_SUB_AUTHOR	+ " INTEGER NOT NULL)";

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
		
		int result = mDatabase.delete(kTABLE_NAME,
				kCOLUMN_ID + " = " + entity.getID(), null);
		
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
		
		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), null,
				null, null, null, null);
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
	public List<ModelBook> read(String selection, String[] selectionArgs, String groupBy, 
			String having, String orderBy) {

		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
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
		return new String[] { kCOLUMN_ID, kCOLUMN_TITLE, kCOLUMN_AUTHOR, kCOLUMN_SUB_AUTHOR };
	}

	public ModelBook generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		
		ModelBook book = new ModelBook();
		book.setID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_ID)));
		book.setTitle(cursor.getString(cursor.getColumnIndex(kCOLUMN_TITLE)));
		book.setAuthorID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_AUTHOR)));
		book.setSubAuthorID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_SUB_AUTHOR)));
		
		return book;
	}

	public ContentValues generateContentValuesFromObject(ModelBook entity) {
		if (entity == null) {
			return null;
		}
		
		ContentValues values = new ContentValues();
		values.put(kCOLUMN_TITLE, entity.getTitle());
		values.put(kCOLUMN_AUTHOR, entity.getAuthorID());
		values.put(kCOLUMN_SUB_AUTHOR, entity.getSubAuthorID());

		return values;
	}
	
}
