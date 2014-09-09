package com.fultonroad.bookminder.db.series;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fultonroad.bookminder.db.Dao;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;

public class DaoSeries extends Dao<ModelSeries> {

	public static final String kTABLE_NAME = "tblSeries";
	public static final String kCOLUMN_ID = "_id";
	public static final String kCOLUMN_AUTHOR = "author";
	public static final String kCOLUMN_NAME = "name";
	

	public static final String kQUALIFIED_ID = kTABLE_NAME + "." + kCOLUMN_ID;
	public static final String kQUALIFIED_AUTHOR_MAJOR = kTABLE_NAME + "." + kCOLUMN_AUTHOR;
	public static final String kQUALIFIED_NAME = kTABLE_NAME + "." + kCOLUMN_NAME;
	
	public DaoSeries(SQLiteDatabase db) {
		super();
		mDatabase = db;
	}

	public static String createTableDdl() {

		return "CREATE TABLE " + kTABLE_NAME + "("
			+ kCOLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ kCOLUMN_AUTHOR	+ " INTEGER NOT NULL, "	
			+ kCOLUMN_NAME + " TEXT NOT NULL)";

	}

	@Override
	public boolean insert(ModelSeries entity) {
		if (entity == null) {
			return false;
		}

		long result = mDatabase.insert(kTABLE_NAME, null, generateContentValuesFromObject(entity));

		return result != 0;
	}

	@Override
	public boolean delete(ModelSeries entity) {
		if (entity == null) {
			return false;
		}
		
		int result = mDatabase.delete(kTABLE_NAME, kCOLUMN_ID + " = " + entity.getID(), null);
		
		return result != 0;
	}

	@Override
	public boolean update(ModelSeries entity) {
		if (entity == null) {
			return false;
		}
		
		int result = mDatabase.update(kTABLE_NAME, generateContentValuesFromObject(entity), kCOLUMN_ID + " = " + entity.getID(), null);
		
		return result != 0;
	}

	@Override
	public List<ModelSeries> read() {
		Cursor cursor = mDatabase.query(kTABLE_NAME, getAllColumns(), null, null, null, null, null);
		List<ModelSeries> list = new ArrayList<ModelSeries>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
//				list.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return list;
	}

	
	public Cursor getSeriesCursor(int key) {
		
/*
 *		O P T I O N   # 1		
		String selection = "?=?";
		String[] selectionArgs = new String[] { kCOLUMN_AUTHOR, Integer.toString(15) };
		String orderby = "name ASC";
		return mDatabase.query(kTABLE_NAME, getAllColumns(), selection, selectionArgs, null, null, orderby);
 */


/*
 *		O P T I O N   # 2		
 */		
		String selection = "SELECT _id, author, name FROM tblSeries WHERE author=15";
		String[] selectionArgs = new String[] { kCOLUMN_AUTHOR, "15" };
		return mDatabase.rawQuery(selection, null);
		
		
		
/*
 *		O P T I O N   # 3		
		String selection = "SELECT _id, author, name FROM tblSeries WHERE author = 15";
		return mDatabase.rawQuery(selection, null);
 */

				
		
	}
	
	
	@Override
	public List<ModelSeries> read(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String[] getAllColumns() {
		return new String[] { kCOLUMN_ID, kCOLUMN_AUTHOR, kCOLUMN_NAME };
	}
	
	public ModelSeries generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		
		ModelSeries series = new ModelSeries();
		series.setID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_ID)));
		series.setAuthorID(cursor.getInt(cursor.getColumnIndex(kCOLUMN_AUTHOR)));
		series.setName(cursor.getString(cursor.getColumnIndex(kCOLUMN_NAME)));
		
		return series;
	}

	public ContentValues generateContentValuesFromObject(ModelSeries entity) {
		if (entity == null) {
			return null;
		}
		
		ContentValues values = new ContentValues();
		values.put(kCOLUMN_NAME, entity.getName());
		values.put(kCOLUMN_AUTHOR, entity.getAuthorID());

		return values;
	}
	


}
