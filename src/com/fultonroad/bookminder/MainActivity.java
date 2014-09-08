package com.fultonroad.bookminder;

import com.fultonroad.bookminder.bookview.BooksViewFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showBookViewFragment();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void showBookViewFragment() {
		FragmentManager fm = getSupportFragmentManager();
		BooksViewFragment frag = (BooksViewFragment)fm.findFragmentByTag(Constants.kTAG_FRAGMENT_BOOK_VIEW);
		FragmentTransaction trans = null;			

		if (frag == null) {
			frag = new BooksViewFragment();
		}
		trans = fm.beginTransaction();
		trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_BOOK_VIEW);
		trans.addToBackStack(null);
		trans.commit();
			
	}
	
//	private void showAuthorFragment() {
//		FragmentManager fm = getSupportFragmentManager();
//		AuthorsFragment frag = (AuthorsFragment)fm.findFragmentByTag(Constants.kTAG_FRAGMENT_AUTHOR);
//		FragmentTransaction trans = null;			
//
//		if (frag == null) {
//			frag = new AuthorsFragment();
//		}
//		trans = fm.beginTransaction();
//		trans.replace(R.id.listContainer, frag, Constants.kTAG_FRAGMENT_AUTHOR);
//		trans.addToBackStack(null);
//		trans.commit();
//			
//	}
	
}
