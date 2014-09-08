package com.fultonroad.bookminder.bookview;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fultonroad.bookminder.Constants;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.author.AuthorsFragment;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;
import com.fultonroad.bookminder.db.bookview.ModelBookView;

public class BooksViewFragment extends Fragment implements OnClickListener, OnItemClickListener, LoaderManager.LoaderCallbacks<List<ModelBookView>> {

	private ListView mListview = null;

	private BooksViewAdapter mAdapter = null; 
	private Cursor mCursor = null;
	private SQLiteDatabase mDatabase = null; 
	private DaoBooksView mBooksView = null;
	
	public BooksViewFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_view, container, false);
		mListview = (ListView) view.findViewById(R.id.list_view_book_view);

		mDatabase = DBHelper.getInstance(getActivity()).getWritableDatabase();
		mBooksView = new DaoBooksView(mDatabase); 

		loadBooksView();
			
        /*
         * 	Listener stuff 
         */
        mListview.setOnItemClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_authors).setOnClickListener(this);

        /*
         * 	Header stuff
         */
        View header = (View)inflater.inflate(R.layout.list_header, container, false);
        TextView tvHeader = (TextView)header.findViewById(R.id.tvListHeader);
        tvHeader.setText(R.string.header_title_books);
        mListview.addHeaderView(header);
        
		return view;
	}

	public void loadBooksView() {

		mCursor = mBooksView.getBooksViewCursor(); 
		mAdapter = new BooksViewAdapter(getActivity(), mCursor, 0);
		mListview.setAdapter(mAdapter);
		
	}
	
	@Override
	public Loader<List<ModelBookView>> onCreateLoader(int arg0, Bundle args) {
		BooksViewLoader loader = new BooksViewLoader(getActivity());
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ModelBookView>> arg0, List<ModelBookView> books) {
		// TODO Auto-generated method stub
		
//		mAdapter.clear();
//		mAdapter.addAll(books);
//		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<ModelBookView>> arg0) {
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
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		String msg = "";
		
		if (position == 0)
			msg = "Header clicked...";
		else {
			Cursor curs = (Cursor)mAdapter.getItem(position-1);
//			int key = curs.getInt(curs.getColumnIndex(DaoBooksView.kCOLUMN_ID));
			String title = curs.getString(curs.getColumnIndex(DaoBooksView.kCOLUMN_TITLE));
			String fname = curs.getString(curs.getColumnIndex(DaoBooksView.kCOLUMN_AUTHOR_FIRST));
			String lname = curs.getString(curs.getColumnIndex(DaoBooksView.kCOLUMN_AUTHOR_LAST));
			String fullname = fname + " " + lname;
			msg = title + " - " + fullname;
			
			
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
