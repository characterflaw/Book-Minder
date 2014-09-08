package com.fultonroad.bookminder.book;

import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.db.book.DaoBooks;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BooksAdapter extends CursorAdapter {
	 
	private Context mContext = null;
	private LayoutInflater mInflater;
	
	public BooksAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	
		if(cursor.getPosition()%2==1) {
			view.setBackgroundColor(context.getResources().getColor(R.color.greenbar));
		} else {
			view.setBackgroundColor(context.getResources().getColor(R.color.azure));
		}
	
//		int id = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_ID));
		String title = cursor.getString(cursor.getColumnIndex(DaoBooks.kCOLUMN_TITLE)); 
//		int year = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_YEAR));
//		int authorID = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_AUTHOR_ID));
//		int subAuthorID = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_SUB_AUTHOR_ID));
		int ownit = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_OWN_IT));
		int readit = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_READ_IT));

		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvTitle.setText(title);

		TextView tvRead = (TextView) view.findViewById(R.id.tvBooksRead);
		if (readit > 0) {
			tvRead.setBackgroundColor(mContext.getResources().getColor(R.color.green));
			tvRead.setTextColor(mContext.getResources().getColor(R.color.black));
		} else {
			tvRead.setBackgroundColor(mContext.getResources().getColor(R.color.red));
			tvRead.setTextColor(mContext.getResources().getColor(R.color.white));
		}
		
		TextView tvOwn = (TextView) view.findViewById(R.id.tvBooksOwn);
		if (ownit > 0) {
			tvOwn.setBackgroundColor(mContext.getResources().getColor(R.color.green));
			tvOwn.setTextColor(mContext.getResources().getColor(R.color.black));
		} else {
			tvOwn.setBackgroundColor(mContext.getResources().getColor(R.color.red));
			tvOwn.setTextColor(mContext.getResources().getColor(R.color.white));
		}
		
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.row_book, parent, false);
	}

}