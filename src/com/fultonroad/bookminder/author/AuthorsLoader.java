package com.fultonroad.bookminder.author;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;


public class AuthorsLoader extends AsyncTaskLoader<List<ModelAuthor>> {

	Context mContext = null;
//	private List<ModelBookView> mListBooks = null; 
//	Locale mLocale = Locale.getDefault();
	
	public static final String kFileLoader_RELOAD ="LocalFileLoader.RELOAD";
	List<ModelAuthor> mOldData = null;

	
	
	
	
	public AuthorsLoader(Context context) {
		super(context);

		mContext = context; 
		
	}

	@Override
	protected void onStartLoading() {
		if (mOldData == null) {
			mOldData = new ArrayList<ModelAuthor>();
		} else {
			if(!mOldData.isEmpty())
				super.deliverResult(mOldData);
		}
	}

	@Override
	public List<ModelAuthor> loadInBackground() {

		DBHelper dbHelper = DBHelper.getInstance(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		DaoAuthors dao = new DaoAuthors(db);
		
		String orderBy = DaoAuthors.kCOLUMN_LAST_NAME + ", " + DaoAuthors.kCOLUMN_FIRST_NAME +" ASC";
		List<ModelAuthor> list = null;
		list = dao.read(null, null, null, null, orderBy);
		
		if (db != null)
			db.close();
		
		return list;
		
	}
	
	@Override
	public void deliverResult(List<ModelAuthor> data) {
		
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
