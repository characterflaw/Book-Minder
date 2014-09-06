package com.fultonroad.bookminder.author;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;


public class AuthorLoader extends AsyncTaskLoader<List<ModelAuthor>> {

	Context mContext = null;
//	private List<ModelBookView> mListBooks = null; 
//	Locale mLocale = Locale.getDefault();
	
	public static final String kFileLoader_RELOAD ="LocalFileLoader.RELOAD";
	List<ModelAuthor> mOldData = null;

	
	
	
	
	public AuthorLoader(Context context) {
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

		List<ModelAuthor> list = null;
		
		DBHelper dbHelper = DBHelper.getInstance(mContext);
//		dbHelper.getReadableDatabase();
		DaoAuthors dao = new DaoAuthors(dbHelper.getReadableDatabase());
		
		String orderBy = DaoAuthors.kCOLUMN_LAST_NAME + ", " + DaoAuthors.kCOLUMN_FIRST_NAME +" ASC";

//		dao.read();
		list = dao.read(null, null, null, null, orderBy);
		
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
