package com.fultonroad.bookminder.book;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;
import com.fultonroad.bookminder.db.bookview.ModelBookView;


public class BookLoader extends AsyncTaskLoader<List<ModelBookView>> {

	Context mContext = null;
//	private List<ModelBookView> mListBooks = null; 
//	Locale mLocale = Locale.getDefault();
	
	int mAuthorId = -1;
	
	
	
	public static final String kBOOK_LOADER_RELOAD = "LocalFileLoader.RELOAD";
	List<ModelBookView> mOldData = null;

	
	
	
	
	public BookLoader(Context context, int authorID) {
		super(context);
		mAuthorId = authorID;
		mContext = context; 
	}

	@Override
	protected void onStartLoading() {
		if (mOldData == null) {
			mOldData = new ArrayList<ModelBookView>();
		} else {
			if(!mOldData.isEmpty())
				super.deliverResult(mOldData);
		}
	}

	@Override
	public List<ModelBookView> loadInBackground() {
		
		DBHelper dbHelper = DBHelper.getInstance(mContext);
		dbHelper.getReadableDatabase();
		DaoBooksView bvds = new DaoBooksView(dbHelper);
		
		List<ModelBookView> list = null;
		
		if (mAuthorId == 0) {
			list = bvds.read();
		} else {
			list = bvds.read(DaoAuthors.kCOLUMN_ID + "=?", new String[] { Integer.toString(mAuthorId) }, null, null, null); 
		}
		
		return list;
	}
	
	@Override
	public void deliverResult(List<ModelBookView> data) {
		
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
