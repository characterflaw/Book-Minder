package com.fultonroad.bookminder.author;

import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.db.author.DaoAuthors;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AuthorsAdapter extends CursorAdapter {
	 
	private LayoutInflater mInflater;
	
	public AuthorsAdapter(Context context, Cursor c, int flags) {
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
	
		String fname = cursor.getString(cursor.getColumnIndex(DaoAuthors.kCOLUMN_FIRST_NAME));
		String lname = cursor.getString(cursor.getColumnIndex(DaoAuthors.kCOLUMN_LAST_NAME));
//		int id = cursor.getInt(cursor.getColumnIndex(DaoAuthors.kCOLUMN_ID));
		String fullname = fname + " " + lname; 
		
		TextView tvName = (TextView) view.findViewById(R.id.tvAuthorName);
		tvName.setText(fullname);
	
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.row_author, parent, false);
	}

}