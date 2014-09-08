package com.fultonroad.bookminder.author;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fultonroad.bookminder.Communicator;
import com.fultonroad.bookminder.Constants;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.book.BooksFragment;
import com.fultonroad.bookminder.bookview.BooksViewFragment;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;
import com.fultonroad.bookminder.utility.JustifiedTextView;

public class AuthorsFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener, OnClickListener, Communicator, LoaderManager.LoaderCallbacks<List<ModelAuthor>> {

	private AuthorsAdapter mAdapter = null; 
	private Cursor mCursor = null;
	private SQLiteDatabase mDatabase = null; 
	private DaoAuthors mAuthors = null;
	private ListView mListview = null;

	public AuthorsFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_author, container, false);
		mListview = (ListView)view.findViewById(R.id.list_view_authors); 
		
		mDatabase = DBHelper.getInstance(getActivity()).getWritableDatabase();
		mAuthors = new DaoAuthors(mDatabase); 
		
		loadAuthors();

        /*
         * 	Listener stuff 
         */
		mListview.setOnItemClickListener(this);
        mListview.setOnItemLongClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_books).setOnClickListener(this);

        /*
         * 	Header stuff
         */
        View header = inflater.inflate(R.layout.list_header, container, false);
        TextView tvHeader = (TextView)header.findViewById(R.id.tvListHeader);
        tvHeader.setText("Authors");
        mListview.addHeaderView(header);
        
		
		return view;
	}

	
	public void loadAuthors() {

		mCursor = mAuthors.getAuthorCursor(); 
		mAdapter = new AuthorsAdapter(getActivity(), mCursor, 0);
		mListview.setAdapter(mAdapter);
		
	}


	
	@Override
	public Loader<List<ModelAuthor>> onCreateLoader(int arg0, Bundle args) {
		AuthorsLoader loader = new AuthorsLoader(getActivity());
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ModelAuthor>> arg0, List<ModelAuthor> authors) {
		
//		mAdapter.clear();
//
//		//	Remove the Author with the Last Name of "None"
//		for (ModelAuthor author : authors) {
//			if (author.getLastName().equals("None")) {
//				authors.remove(author);
//				break;
//			}
//		}
//
//		mAdapter.addAll(authors);
//		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<ModelAuthor>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		String msg = "";
		
		if (position == 0)
			msg = "Header clicked...";
		else {
			Cursor curs = (Cursor)mAdapter.getItem(position-1);
			int key = curs.getInt(curs.getColumnIndex(DaoAuthors.kCOLUMN_ID));
			String fname = curs.getString(curs.getColumnIndex(DaoAuthors.kCOLUMN_FIRST_NAME));
			String lname = curs.getString(curs.getColumnIndex(DaoAuthors.kCOLUMN_LAST_NAME));
			
			Bundle args = new Bundle();
			args.putString("author_name", fname + " " + lname);
			
			BooksFragment frag = new BooksFragment(key);
			frag.setArguments(args);
			FragmentManager mgr = getActivity().getSupportFragmentManager();
			FragmentTransaction trans = mgr.beginTransaction();
			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK);
			trans.commit();		
			
		}
		
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	int mCurSel = 0;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
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
						
						Cursor curs = (Cursor)mAdapter.getItem(position-1);
						int key = curs.getInt(curs.getColumnIndex("_id"));
						
						deleteAuthor(key);
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
		
		switch (v.getId()) {
		case R.id.btn_add:
			Toast.makeText(getActivity(), "ADD a new author...", Toast.LENGTH_SHORT).show();
			launchAddAuthorDialog();
			break;
			
		case R.id.btn_books:
			
			BooksViewFragment frag = new BooksViewFragment();
			FragmentManager mgr = getActivity().getSupportFragmentManager();
			FragmentTransaction trans = mgr.beginTransaction();
			trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK_VIEW);
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
		 *
		 *	TODO
		 *
		 *	NOTE:	if you delete the authors and the books, you must also 
		 *			delete the SERIES records for this author AND sub-authors
		 *
		 */
		
		//	check books for this author
		
		DBHelper helper = DBHelper.getInstance(getActivity());
		SQLiteDatabase db = helper.getWritableDatabase(); 
		
		DaoBooks tblBooks = new DaoBooks(db);
		DaoAuthors tblAuthors = new DaoAuthors(db);
		
		String selection = DaoBooks.kQUALIFIED_AUTHOR_MAJOR + "=? OR " + DaoBooks.kQUALIFIED_AUTHOR_MINOR + "=?";  
		String[] selectionArgs = new String[] { Integer.toString(authID), Integer.toString(authID) };
		
		List<ModelBook> list = tblBooks.read(selection, selectionArgs, null, null, null);
		
		System.out.println("PAUSE");
		
		if (list.size() > 0) {
			confirmAndDelete(authID, list.size());
		} else {
			if (tblAuthors.delete(new ModelAuthor(authID))) {
				mAdapter.notifyDataSetChanged();
			}
		}
		
		if (db != null)
			db.close();
	}
	
	public void confirmAndDelete(final int which, int count) {
		
	    final JustifiedTextView tvMessage = new JustifiedTextView(getActivity(), "Hello World!");
	    
	    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    dialog.setTitle(getResources().getString(R.string.delete_author_confirm_title));
	    String msg = getResources().getString(R.string.delete_author_confirm_message);
	    if (count == 1)
	    	msg = msg.replace("~~", "is " + Integer.toString(count) + " book");
	    else 
	    	msg = msg.replace("~~", "are " + Integer.toString(count) + " books");
	    
//	    dialog.setMessage(msg);
	    tvMessage.setText(msg);
	    dialog.setView(tvMessage);
	    dialog.setCancelable(false);
	    
	    dialog.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {

	    	int key = which; 
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				DBHelper helper = DBHelper.getInstance(getActivity());
				SQLiteDatabase db = helper.getWritableDatabase(); 
				DaoBooks tblBooks = new DaoBooks(db);
				DaoAuthors tblAuthors = new DaoAuthors(db);

				if (tblAuthors.delete(new ModelAuthor(key))) {
					FragmentManager mgr = getActivity().getSupportFragmentManager();
					AuthorsFragment	frag = (AuthorsFragment)mgr.findFragmentByTag(Constants.kTAG_FRAGMENT_AUTHOR);
					frag.loadAuthors();
					mAdapter.notifyDataSetChanged();
				}

				tblBooks.deleteForAuthor(key);
				
				if (db != null)
					db.close();

			}
		});
		
	    dialog.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
//				Log.v("JMD", "NEGATIVE");
				
			}
		});
		
	    dialog.show();
	}
	
	
	
	
	
	
	
}
