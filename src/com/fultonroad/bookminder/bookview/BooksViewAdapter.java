package com.fultonroad.bookminder.bookview;

import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BooksViewAdapter extends CursorAdapter {
	 
	private LayoutInflater mInflater;
	
	public BooksViewAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	
		if(cursor.getPosition()%2==1) {
			view.setBackgroundColor(context.getResources().getColor(R.color.greenbar));
		} else {
			view.setBackgroundColor(context.getResources().getColor(R.color.azure));
		}
	
//		int id = cursor.getInt(cursor.getColumnIndex(DaoBooksView.kCOLUMN_ID));
		String title = cursor.getString(cursor.getColumnIndex(DaoBooksView.kCOLUMN_TITLE));
		String fname = cursor.getString(cursor.getColumnIndex(DaoBooksView.kCOLUMN_AUTHOR_FIRST));
		String lname = cursor.getString(cursor.getColumnIndex(DaoBooksView.kCOLUMN_AUTHOR_LAST));
		String subfname = cursor.getString(cursor.getColumnIndex(DaoBooksView.kCOLUMN_SUB_AUTHOR_FIRST));
		String sublname = cursor.getString(cursor.getColumnIndex(DaoBooksView.kCOLUMN_SUB_AUTHOR_LAST));
		int ownit = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_OWN_IT));
		int readit = cursor.getInt(cursor.getColumnIndex(DaoBooks.kCOLUMN_READ_IT));
		String author = fname + " " + lname; 
		
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvTitle.setText(title);
		
		TextView tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
		
		if (sublname.equals("None")) {
			tvAuthor.setText(author);
		} else {
			String subauthor = subfname + " " + sublname; 
			String authorplus = author + " (with " + subauthor + ")";
			tvAuthor.setText(authorplus);
		}
		
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
		return mInflater.inflate(R.layout.row_book_view, parent, false);
	}

}