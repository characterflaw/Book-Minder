package com.fultonroad.bookminder.book;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.fultonroad.bookminder.Constants;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.author.AuthorsFragment;
import com.fultonroad.bookminder.bookview.BooksViewFragment;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;

public class BooksFragment extends Fragment implements OnClickListener, OnItemClickListener, LoaderManager.LoaderCallbacks<List<ModelBook>> {

	private ListView mListview = null;
	private int mAuthorID = 0; 
	private String mAuthorName = ""; 
	
	private BooksAdapter mAdapter = null; 
	private Cursor mCursor = null;
	private SQLiteDatabase mDatabase = null; 
	private DaoBooks mBooks = null;
	
	
	public BooksFragment(int key) {
		super();
		mAuthorID = key;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book, container, false);
		mListview = (ListView) view.findViewById(R.id.list_view_books);

		mDatabase = DBHelper.getInstance(getActivity()).getWritableDatabase();
		mBooks = new DaoBooks(mDatabase); 
		
		if (getArguments() != null)
			mAuthorName = getArguments().getString("author_name");

		loadBooks();

        /*
         * 	Listener stuff 
         */
        mListview.setOnItemClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_authors).setOnClickListener(this);
        view.findViewById(R.id.btn_books).setOnClickListener(this);

        /*
         * 	Header stuff
         */
        View header = (View)inflater.inflate(R.layout.list_header, container, false);
        TextView tvHeader = (TextView)header.findViewById(R.id.tvListHeader);
        tvHeader.setText(R.string.header_title_books);
        mListview.addHeaderView(header);
        
		return view;
	}

	public void loadBooks() {

		mCursor = mBooks.getBooksCursor(mAuthorID); 
		mAdapter = new BooksAdapter(getActivity(), mCursor, 0);
		mListview.setAdapter(mAdapter);
		
	}

	@Override
	public Loader<List<ModelBook>> onCreateLoader(int arg0, Bundle arg1) {
		BooksLoader loader = new BooksLoader(getActivity(), mAuthorID);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ModelBook>> arg0, List<ModelBook> books) {
		
//		mAdapter.clear();
//		mAdapter.addAll(books);
//		mAdapter.notifyDataSetChanged();

	}

	@Override
	public void onLoaderReset(Loader<List<ModelBook>> arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onClick(View v) {

		FragmentManager mgr = getActivity().getSupportFragmentManager();
		FragmentTransaction trans = null;			
		
		if (v.getId() == R.id.btn_add) {
			
		} else if (v.getId() == R.id.btn_authors) { 
			AuthorsFragment frag = (AuthorsFragment)mgr.findFragmentByTag(Constants.kTAG_FRAGMENT_AUTHOR);

			if (frag == null) {
				frag = new AuthorsFragment();
			}
			trans = mgr.beginTransaction();
			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_AUTHOR);
			trans.addToBackStack(null);
			trans.commit();

		} else if (v.getId() == R.id.btn_books) { 
			BooksViewFragment frag = (BooksViewFragment)mgr.findFragmentByTag(Constants.kTAG_FRAGMENT_BOOK_VIEW);

			if (frag == null) {
				frag = new BooksViewFragment();
			}
			trans = mgr.beginTransaction();
			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK_VIEW);
			trans.addToBackStack(null);
			trans.commit();		
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String msg = "";
		
		if (position == 0)
			msg = "Header clicked...";
		else {
			Cursor curs = (Cursor)mAdapter.getItem(position-1);
//			int key = curs.getInt(curs.getColumnIndex(DaoBooks.kCOLUMN_ID));
			String title = curs.getString(curs.getColumnIndex(DaoBooks.kCOLUMN_TITLE));
//			int author = curs.getInt(curs.getColumnIndex(DaoBooks.kCOLUMN_AUTHOR));
//			int subauthor = curs.getInt(curs.getColumnIndex(DaoBooks.kCOLUMN_SUB_AUTHOR));
			
			msg = title + " TOUCHED";
			/*
			 * 	Show the BOOK details 
			 */
			
			
//			Bundle args = new Bundle();
//			args.putString("author_name", fullname);
//			
//			BooksFragment frag = new BooksFragment(key);
//			frag.setArguments(args);
//			FragmentManager mgr = getActivity().getSupportFragmentManager();
//			FragmentTransaction trans = mgr.beginTransaction();
//			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK);
//			trans.commit();		
			
		}

		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		
	}
	
}
