package com.fultonroad.bookminder.author;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fultonroad.bookminder.Communicator;
import com.fultonroad.bookminder.Constants;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.book.BookFragment;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;

public class AuthorFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener, OnClickListener, Communicator, LoaderManager.LoaderCallbacks<List<ModelAuthor>> {

    private ListView mListView;
	private AuthorAdapter mAdapter = null; 

	public AuthorFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_author, container, false);

		view.findViewById(R.id.btn_add).setOnClickListener(this);
		view.findViewById(R.id.btn_books).setOnClickListener(this);
		
		
		
		loadAuthors();
		
		List<ModelAuthor> list = new ArrayList<ModelAuthor>();
		
		mAdapter = new AuthorAdapter(getActivity(), R.layout.row_author, list);
        mListView = (ListView) view.findViewById(R.id.listview_authors);

        View header = (View)inflater.inflate(R.layout.list_header, null);
        
        TextView tvHeader = (TextView)header.findViewById(R.id.tvListHeader);
        tvHeader.setText(R.string.header_title_authors);
        mListView.addHeaderView(header);
        
        mListView.setAdapter(mAdapter);
        
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
		
		return view;
	}

	
	public void loadAuthors() {
		LoaderManager lm = getLoaderManager();
		lm.initLoader(2, null, this).forceLoad();
	}


	
	@Override
	public Loader<List<ModelAuthor>> onCreateLoader(int arg0, Bundle args) {
		AuthorLoader loader = new AuthorLoader(getActivity());
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ModelAuthor>> arg0, List<ModelAuthor> authors) {
		
		mAdapter.clear();

		//	Remove the Author with the Last Name of "None"
		for (ModelAuthor author : authors) {
			if (author.getLastName().equals("None")) {
				authors.remove(author);
				break;
			}
		}

		mAdapter.addAll(authors);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<ModelAuthor>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class AuthorAdapter extends ArrayAdapter<ModelAuthor> {
		
		Context mContext; 
		 
		public AuthorAdapter(Context context, int resource, List<ModelAuthor> items) {
			super(context, resource, items);
	        mContext = context;
		}
		
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		AuthorHolder holder = null;
		
		if (view == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			view = inflater.inflate(R.layout.row_author, null);
			
			holder = new AuthorHolder();
			holder.textViewName = (TextView)view.findViewById(R.id.tvAuthorName);
			
			view.setTag(holder);
		} else {
			holder = (AuthorHolder)view.getTag();
		}
		
		ModelAuthor item = getItem(position);
		holder.textViewName.setText(item.getFirstName() + " " + item.getLastName());
//		holder.imageViewIcon.setImageResource(weather.icon);
	    
		return view;
}


	}
	
	static class AuthorHolder {
		TextView textViewName;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		String msg = "";
				
		if (position == 0)
			msg = "Header clicked...";
		else {
			ModelAuthor rcd = mAdapter.getItem(position-1);
			msg = "Show books for " + rcd.getFirstName() + " " + rcd.getLastName();
			
			/*
			 * 	Show Book records NOT BookView records
			 * 
			 * 
			 */
		}
		
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		System.out.println("PAUSE");
		
	}

	int mCurSel = 0;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		mCurSel = position;
		
		if (position > 0) {
			PopupMenu popup = new PopupMenu(getActivity(), view);
			popup.getMenuInflater().inflate(R.menu.edit, popup.getMenu());
			
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					
					switch(item.getItemId()) {
					case R.id.edit_author_modify:
						Toast.makeText(getActivity(), "Modify this author...", Toast.LENGTH_SHORT).show();
						break;
					case R.id.edit_author_delete:
						Toast.makeText(getActivity(), "Delete this author...", Toast.LENGTH_SHORT).show();
						
						ModelAuthor auth = mAdapter.getItem(mCurSel-1);
						
						
						deleteAuthor(auth.getID());
						break;
					}
					
					return true;
				}
			});
			
			popup.show();
		}
		
		return true;
	}

	
	@Override
	public void onClick(View v) {
		
		int dex = 0;
		
		switch (v.getId()) {
		case R.id.btn_add:
			Toast.makeText(getActivity(), "ADD a new author...", Toast.LENGTH_SHORT).show();
			launchAddAuthorDialog();
			break;
			
		case R.id.btn_books:
			
			BookFragment frag = new BookFragment(0);
			FragmentManager mgr = getActivity().getSupportFragmentManager();
			FragmentTransaction trans = mgr.beginTransaction();
			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK);
			trans.commit();		
			
			break;
			
		}		
	}
	
	
	private void launchAddAuthorDialog() {
		
		FragmentManager fm = null;
		AddAuthorFragment dlg = null;
	
	    fm = getActivity().getSupportFragmentManager();
	    
	    dlg = (AddAuthorFragment) AddAuthorFragment.instantiate(getActivity(), AddAuthorFragment.class.getName());
	    dlg.show(fm, "fragment_add_author");
		
	}

	@Override
	public void sendMessage(int which, int action) {

		System.out.println("sendMessage");
		loadAuthors();
		
	}
	

	private void deleteAuthor(int authID) {
		
		
		/*
		 * 	see if any books exist for this author
		 *
		 *	if so, pop alert warning that all books will be deleted as well
		 *	if NOT, delete the author
		 */
		
		
		//	delete the author
//		DBHelper helper = DBHelper.getInstance(getActivity());
//		DaoAuthors table = new DaoAuthors(helper);
		DaoAuthors table = new DaoAuthors(DBHelper.getInstance(getActivity()));
		if (table.delete(new ModelAuthor(authID))) {
			mAdapter.notifyDataSetChanged();
		}
		
		
		
	}
	
	
	
	
}
