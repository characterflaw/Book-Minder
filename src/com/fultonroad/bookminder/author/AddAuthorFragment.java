package com.fultonroad.bookminder.author;

import com.fultonroad.bookminder.Communicator;
import com.fultonroad.bookminder.Constants;
import com.fultonroad.bookminder.R;
import com.fultonroad.bookminder.db.DBHelper;
import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;


public class AddAuthorFragment extends DialogFragment implements OnClickListener {

	Communicator mCommunicator = null;
	
	EditText mEtFirstName = null;
	EditText mEtLastName = null;
	
	public AddAuthorFragment() {
		super();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Dialog);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dlg_add_author, container, false);
		setCancelable(false);

        mCommunicator = (Communicator) getFragmentManager().findFragmentByTag(Constants.kTAG_FRAGMENT_AUTHOR);

		getDialog().setTitle("Enter New Author");
		
		
		
		mEtFirstName = (EditText)view.findViewById(R.id.etAuthorFirstName); 
		mEtLastName = (EditText)view.findViewById(R.id.etAuthorLastName); 
		view.findViewById(R.id.btnDialogAdd).setOnClickListener(this);
		view.findViewById(R.id.btnDialogCancel).setOnClickListener(this);
		
		return view;
	}


	@Override
	public void onClick(View v) {
		
		DBHelper mDBHelper = DBHelper.getInstance(getActivity());
		mDBHelper.getWritableDatabase();

		if (v.getId() == R.id.btnDialogAdd) {
			
			
			DaoAuthors dsAuthors = new DaoAuthors(mDBHelper.getWritableDatabase());
			
			String fname = mEtFirstName.getText().toString().trim();
			String lname = mEtLastName.getText().toString().trim();
			Boolean flag = dsAuthors.exists(new ModelAuthor(fname, lname));
			String fullname = fname + " " + lname;  
			
			if (flag) {
				popAlert(fullname, kFAILURE);
				mEtFirstName.setText("");
				mEtLastName.setText("");
			} else {
				
				if ( dsAuthors.insert(new ModelAuthor(fname, lname))) { 
					popAlert(fullname, kSUCCESS);
					
					mCommunicator.sendMessage(Constants.kMSG_AUTHORS, Constants.kMSG_REFRESH);
				}
				this.dismiss();
			}
			
			
			//	check to see if this Author exists
			
			//	if exists
			//		pop an alert
			//	else
			//		add new author
			
		} else if (v.getId() == R.id.btnDialogCancel) {
			this.dismiss();
		}
		
		mDBHelper.close();
	}

	private static final Boolean kFAILURE = false;
	private static final Boolean kSUCCESS = true;
	
	public void popAlert(String author, Boolean status) {
		
		AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
		
		String title = "";
		String msg = author;
		if (status == kSUCCESS) {
			title = "Author Added";
			msg += " was successfuly added to the database";
		} else {
			title = "Author Not Added";
			msg += " already exists in the database.";
		}
		
		mDialogBuilder.setTitle(title);
		mDialogBuilder.setMessage(msg);

		mDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		mDialogBuilder.show();
	}

	
	
}
