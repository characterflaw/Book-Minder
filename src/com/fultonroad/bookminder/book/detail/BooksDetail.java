package com.fultonroad.bookminder.book.detail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.AdapterView.OnItemSelectedListener;

import com.fultonroad.bookminder.MainActivity;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.series.DaoSeries;

public class BooksDetail extends Fragment implements OnClickListener, OnItemSelectedListener {

	/*
	 *	C A P T I O N S
	 */
//	private TextView mTvTitle = null;
//	private TextView mTvAuthor = null;
//	private TextView mTvSubAuthor = null;
//	private TextView mTvSeries = null;
//	private TextView mTvYear = null;

	/*
	 *	C O N T R O L S
	 */
	private EditText mEtTitle = null;
	private Spinner mSpinnerAuthor = null;
	private Spinner mSpinnerSubAuthor = null;
	private Spinner mSpinnerSeries = null;
	private EditText mEtYear = null;
	private Switch mSwOwnIt = null;
	private Switch mSwReadIt = null;
	private Button mBtnCancel = null;
	private Button mBtnSave = null;
	
	
	
	
	private int mBookID = 0; 
	private String mAuthorName = ""; 
	
	private SimpleCursorAdapter mAdapterAuthor = null; 
	private SimpleCursorAdapter mAdapterSubAuthor = null; 
	private SimpleCursorAdapter mAdapterSeries = null; 
	private Cursor mCursorAuthor = null;
	private Cursor mCursorSubAuthor = null;
	private Cursor mCursorSeries = null;
	private SQLiteDatabase mDatabase = null; 
	private DaoBooks mBooks = null;
	private DaoAuthors mAuthors = null;
	private DaoSeries mSeries = null;
	

	public BooksDetail(int key) {
		super();
		mBookID = key;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

		/*
		 *	O U T L E T S
		 *
		 *	C A P T I O N S
		 */
//		mTvTitle = (TextView)view.findViewById(R.id.tvBookDetailTitle);
//		mTvAuthor = (TextView)view.findViewById(R.id.tvBookDetailAuthor);
//		mTvSubAuthor = (TextView)view.findViewById(R.id.tvBookDetailSubAuthor);
//		mTvSeries = (TextView)view.findViewById(R.id.tvBookDetailSeries);
//		mTvYear = (TextView)view.findViewById(R.id.tvBookDetailYear);

		/*
		 *	O U T L E T S
		 *
		 *	C O N T R O L S
		 */
		mEtTitle = (EditText)view.findViewById(R.id.etBookDetailTitle);
		mSpinnerAuthor = (Spinner)view.findViewById(R.id.spBookDetailAuthor);
		mSpinnerAuthor.setOnItemSelectedListener(this);
		mSpinnerSubAuthor = (Spinner)view.findViewById(R.id.spBookDetailSubAuthor);
		mSpinnerSubAuthor.setOnItemSelectedListener(this);
		mSpinnerSeries = (Spinner)view.findViewById(R.id.spBookDetailSeries);
		mEtYear = (EditText)view.findViewById(R.id.etBookDetailYear);
		mSwOwnIt = (Switch)view.findViewById(R.id.swBookDetailOwnIt);
		mSwReadIt = (Switch)view.findViewById(R.id.swBookDetailReadIt);
		mBtnCancel = (Button)view.findViewById(R.id.btnDialogCancel);
		mBtnSave = (Button)view.findViewById(R.id.btnDialogSave);
		
		mDatabase = DBHelper.getInstance(getActivity()).getWritableDatabase();
		mBooks = new DaoBooks(mDatabase); 
		mAuthors = new DaoAuthors(mDatabase); 
		mSeries = new DaoSeries(mDatabase); 
		
		loadSeriesForAuthor(15);
		loadAuthors();

        /*
         * 	Listener stuff 
         */
		mBtnCancel.setOnClickListener(this);
		mBtnSave.setOnClickListener(this);

		return view;
	}

	public void loadSeriesForAuthor(int key) {
	
		String[] from = new String[] { DaoSeries.kCOLUMN_NAME };
		int[] to = new int[] { R.id.tvAuthorName };
		
		Context ctext = (Context)getActivity();
		mCursorSeries = mSeries.getSeriesCursor(key); 
		mAdapterSeries = new SimpleCursorAdapter(ctext, R.layout.row_author, mCursorSeries, from, to, 0); 
				
				
		mSpinnerSeries.setAdapter(mAdapterSeries);

	}

	public void loadAuthors() {

		String[] from = new String[] { DaoAuthors.kCOLUMN_FULL_NAME };
		int[] to = new int[] { R.id.tvDetailAuthorName };
		
		Context ctext = (Context)getActivity();
		mCursorAuthor = mAuthors.getAuthorCursorFull(); 
		mCursorSubAuthor = mAuthors.getSubAuthorCursorFull(); 
		mAdapterAuthor = new SimpleCursorAdapter(ctext, R.layout.row_spinner_edit, mCursorAuthor, from, to, 0); 
		mAdapterSubAuthor = new SimpleCursorAdapter(ctext, R.layout.row_spinner_edit, mCursorSubAuthor, from, to, 0); 
				
				
		mSpinnerAuthor.setAdapter(mAdapterAuthor);
		mSpinnerSubAuthor.setAdapter(mAdapterSubAuthor);
		
	}

	@Override
	public void onClick(View v) {
		MainActivity act = (MainActivity)getActivity();
		
		if (v.getId() == R.id.btnDialogSave) {
			
		} else if (v.getId() == R.id.btnDialogSave) {
			act.showBookViewFragment();
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		Cursor curs = (Cursor)mAdapterAuthor.getItem(position);
		int key = curs.getInt(curs.getColumnIndex(DaoAuthors.kCOLUMN_ID));
		
		if (id == R.id.spBookDetailAuthor) {
			loadSeriesForAuthor(key);		
		} else if (id == R.id.spBookDetailSubAuthor) {
			loadSeriesForAuthor(key);		
		}
	}

	
	/*
	 * 	N O T   U S E D
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) { }

	
}
