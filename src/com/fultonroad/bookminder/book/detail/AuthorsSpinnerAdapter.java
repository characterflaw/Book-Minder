package com.fultonroad.bookminder.book.detail;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class AuthorsSpinnerAdapter extends CursorAdapter {

	public AuthorsSpinnerAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
//		row_spinner_edit
		return null;
	}

}
