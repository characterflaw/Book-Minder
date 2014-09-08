package com.fultonroad.bookminder.bookview;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;
import com.fultonroad.bookminder.db.bookview.ModelBookView;


public class BooksViewLoader extends AsyncTaskLoader<List<ModelBookView>> {

	Context mContext = null;
	
	public static final String kBOOK_VIEW_LOADER_RELOAD = "BookViewLoader.RELOAD";
	List<ModelBookView> mOldData = null;

	public BooksViewLoader(Context context) {
		super(context);
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
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		DaoBooksView dao = new DaoBooksView(db);
		
		String orderBy = DaoBooksView.kCOLUMN_TITLE + " ASC";
		List<ModelBookView> list = null;
//		list = dao.read(null, null, null, null, orderBy);
		list = dao.read();
		
		if (db != null)
			db.close();
		
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
