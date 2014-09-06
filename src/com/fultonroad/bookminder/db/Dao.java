package com.fultonroad.bookminder.db;

import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author jdavey
 *
 * @param <T>
 * 
 * code lifted from here:  http://www.phloxblog.in/android-custom-loader-load-data-sqlite-database-android-version-1-6/#.U--d_lbxPZA
 */
public abstract class Dao<T> {
	protected SQLiteDatabase mDatabase;
	
	public Dao() { }
	
	public Dao(SQLiteDatabase database) {
		mDatabase = database;
	}
	
	public abstract boolean insert(T entity);
	public abstract boolean delete(T entity);
	public abstract boolean update(T entity);

	public abstract List read();
	public abstract List read(String selection, String[] selectionArgs, String groupBy, String having, String orderBy);

}