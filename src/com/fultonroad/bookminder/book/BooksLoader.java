package com.fultonroad.bookminder.book;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;
import com.fultonroad.bookminder.db.bookview.ModelBookView;

public class BooksLoader extends AsyncTaskLoader<List<ModelBook>> {

	public static final String kBOOK_LOADER_RELOAD = "BookLoader.RELOAD";
	private Context mContext = null;
	private int mAuthorID = 0;
	List<ModelBook> mOldData = null;

	public BooksLoader(Context context, int key) {
		super(context);
		mContext = context; 
		mAuthorID = key;
	}

	@Override
	public List<ModelBook> loadInBackground() {

		DBHelper dbHelper = DBHelper.getInstance(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		DaoBooks dao = new DaoBooks(db);
		
		List<ModelBook> list = null;
		if (mAuthorID == 0) {
			list = dao.read();
		} else {
			String sel = DaoBooks.kCOLUMN_AUTHOR + "=? OR " + DaoBooks.kCOLUMN_SUB_AUTHOR  + "=?";
			String orderby = DaoBooks.kCOLUMN_YEAR + ", " + DaoBooks.kCOLUMN_TITLE + " ASC";
			String[] args = new String[] { Integer.toString(mAuthorID), Integer.toString(mAuthorID) };
			list = dao.read(sel, args, null, null, orderby); 
		}
		
		if (db != null)
			db.close();
		
		return list;
	}

	@Override
	public void deliverResult(List<ModelBook> data) {
		
		mOldData = data;
		
		if(isStarted()){
			super.deliverResult(data);
		}
	}
	
	@Override
	protected void onReset() {
		this.stopLoading();
		super.onReset();
	}
		

}
