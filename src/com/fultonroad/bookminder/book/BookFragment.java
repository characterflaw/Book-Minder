package com.fultonroad.bookminder.book;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.author.AuthorLoader;
import com.fultonroad.bookminder.db.author.ModelAuthor;
import com.fultonroad.bookminder.db.bookview.ModelBookView;

public class BookFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ModelBookView>> {

    private ListView mListView;
	private BookViewAdapter mAdapter = null; 
	private int mAuthorId = 0;

	public BookFragment(int authId) {
		super();
		mAuthorId = authId;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book, container, false);
		
//		try {
//			mAuthorId = getArguments().getInt("author_id",0);
//		} catch(NullPointerException ex) {
//			mAuthorId = 0;
//		}
		
		LoaderManager lm = getLoaderManager();
		lm.initLoader(1, null, this).forceLoad();
		
		List<ModelBookView> list = new ArrayList<ModelBookView>();
		
        mAdapter = new BookViewAdapter(getActivity(), R.layout.row_book, list);
        mListView = (ListView) view.findViewById(R.id.lvBooks);

        View header = (View)inflater.inflate(R.layout.list_header, null);
       
        TextView tvHeader = (TextView)header.findViewById(R.id.tvListHeader);
        tvHeader.setText(R.string.header_title_books);
        mListView.addHeaderView(header);
        
        mListView.setAdapter(mAdapter);
		
		return view;
	}

	
	

	
	@Override
	public Loader<List<ModelBookView>> onCreateLoader(int arg0, Bundle args) {
		BookLoader loader = new BookLoader(getActivity(), mAuthorId);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ModelBookView>> arg0, List<ModelBookView> books) {
		// TODO Auto-generated method stub
		
		mAdapter.clear();
		mAdapter.addAll(books);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<ModelBookView>> arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	public class BookViewAdapter extends ArrayAdapter<ModelBookView> {

		Context mContext = null;
		
		public BookViewAdapter(Context context, int resource, List<ModelBookView> items) {
			super(context, resource, items);
			mContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

		   	View view = convertView;

	    	if (view == null) {
	    		LayoutInflater infltr = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		view = infltr.inflate(R.layout.row_book, null);
	    	}
	    	
	    	ModelBookView item = getItem(position);
	    	
	    	if (item != null) {
	    		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
	    		TextView tvAuthor = (TextView)view.findViewById(R.id.tvAuthor);
			
	    		tvTitle.setText(item.getTitle());
	    		
	    		String str = item.getAuthorFirst() + " " + item.getAuthorLast();
	    				
	    		if (!item.getAuthorSubLast().equals("None")) {
	    			
	    			str += " (with ";
	    			str += item.getAuthorSubFirst();
	    			str += " ";
	    			str += item.getAuthorSubLast();
	    			str += ")";

	    		}
	    		
	    		tvAuthor.setText(str);
	    		
	    	}
			
			return view;
		}
	}

	
}
