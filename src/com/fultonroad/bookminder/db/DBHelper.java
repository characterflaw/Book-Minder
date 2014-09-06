package com.fultonroad.bookminder.db;

import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
	
	private static final String kDATABASE_NAME	= "bookMinder.db";
	private static final int kDATABASE_VERSION = 14;

	private static DBHelper sInstance;

	private DBHelper(Context context) {
		super(context, kDATABASE_NAME, null, kDATABASE_VERSION);
		
	}

	public static DBHelper getInstance(Context context) {
	
		// Use the application context, which will ensure that you 
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (sInstance == null) {
			sInstance = new DBHelper(context.getApplicationContext());
		}
		
		return sInstance;
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
        Log.v("DB", "before creation");
        createAuthorsTable(db);
        createBooksTable(db);
        createBookView(db);
        Log.v("DB", "after creation");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + DaoBooks.kTABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DaoAuthors.kTABLE_NAME);
		db.execSQL("DROP VIEW IF EXISTS " + kDATABASE_NAME + ".BookView");
		onCreate(db);

	}
	
	private Boolean createBooksTable(SQLiteDatabase db) {
		Boolean retval = true;
		
		String qry = DaoBooks.createTableDdl();

		Log.v("JMD", qry);
		
		try  {
			db.execSQL(qry);
			insertSampleBooks(db);
		} catch(SQLException ex) {
			retval = false;
		}
		
		return retval;
	}
	
	private void insertSampleAuthors(SQLiteDatabase db) {
		
		DaoAuthors author = new DaoAuthors(db);
		author.insert(new ModelAuthor(1, "", "None"));
		author.insert(new ModelAuthor(2, "Jim", "Butcher"));
		author.insert(new ModelAuthor(3, "Neal", "Stephenson"));
		author.insert(new ModelAuthor(4, "James", "Patterson"));
		author.insert(new ModelAuthor(5, "J.R.R.", "Tolkien"));
		author.insert(new ModelAuthor(6, "David", "Baldacci"));
		author.insert(new ModelAuthor(7, "Michael", "Ledwidge"));
		author.insert(new ModelAuthor(8, "Andrew", "Gross"));
		author.insert(new ModelAuthor(9, "Mark", "Pearson"));
		author.insert(new ModelAuthor(10, "Lee", "Child"));
		author.insert(new ModelAuthor(11, "Craig", "Johnson"));
		author.insert(new ModelAuthor(12, "Michael", "White"));
		author.insert(new ModelAuthor(13, "James Lee", "Burke"));
		author.insert(new ModelAuthor(14, "Robert", "Jordan"));
		author.insert(new ModelAuthor(15, "Anne", "Rice"));
		author.insert(new ModelAuthor(16, "Frank", "Herbert"));
		author.insert(new ModelAuthor(17, "Clive", "Cussler"));
		author.insert(new ModelAuthor(18, "Tom", "Clancy"));
		author.insert(new ModelAuthor(19, "J.K.", "Rowling"));
		author.insert(new ModelAuthor(20, "Stephanie", "Meyers"));
		author.insert(new ModelAuthor(21, "Brandon", "Sanderson"));
		author.insert(new ModelAuthor(22, "Brad", "Meltzer"));
		author.insert(new ModelAuthor(23, "Robert", "Ludlum"));

	}
	
	private Boolean createAuthorsTable(SQLiteDatabase db) {
		Boolean retval = true;

		String qry = DaoAuthors.createTableDdl();

		Log.v("JMD", qry);

		try  {
			db.execSQL(qry);
			insertSampleAuthors(db);
		} catch(SQLException ex) {
			retval = false;
		}
		
		return retval;
	}
	
	
	private void insertSampleBooks(SQLiteDatabase db) {
		
		DaoBooks book = new DaoBooks(db);
		book.insert(new ModelBook("Storm Front", 2, 1));
		book.insert(new ModelBook("Full Moon", 2, 1));
		book.insert(new ModelBook("Grave Peril", 2, 1));
		book.insert(new ModelBook("Summer Knight", 2, 1));
		book.insert(new ModelBook("Death Masks", 2, 1));
		book.insert(new ModelBook("Blood Rites", 2, 1));
		book.insert(new ModelBook("Dead Beat", 2, 1));
		book.insert(new ModelBook("Proven Guilty", 2, 1));
		book.insert(new ModelBook("White Knight", 2, 1));
		book.insert(new ModelBook("Small Favor", 2, 1));
		book.insert(new ModelBook("Turn Coat", 2, 1));
		book.insert(new ModelBook("Changes", 2, 1));
		book.insert(new ModelBook("Ghost Story", 2, 1));
		book.insert(new ModelBook("Cold Days", 2, 1));
		book.insert(new ModelBook("Cold Days", 2, 1));
		book.insert(new ModelBook("Skin Game", 2, 1));
		book.insert(new ModelBook("Furies of Calderon", 2, 1));
		book.insert(new ModelBook("Academ's Fury", 2, 1));
		book.insert(new ModelBook("Cursor's Fury", 2, 1));
		book.insert(new ModelBook("Captain's Fury", 2, 1));
		book.insert(new ModelBook("Princep's Fury", 2, 1));
		book.insert(new ModelBook("First Lord's Fury", 2, 1));
		book.insert(new ModelBook("Cryptonomicon", 3, 1));
		book.insert(new ModelBook("The Hobbit", 5, 1));
		book.insert(new ModelBook("The Fellowship of the Ring", 5, 1));
		book.insert(new ModelBook("The Return of the King", 5, 1));
		book.insert(new ModelBook("The Two Towers", 5, 1));
		book.insert(new ModelBook("Alex Cross", 4, 1));
		book.insert(new ModelBook("Private", 4, 1));
		book.insert(new ModelBook("The Camel Club", 6, 1));
		book.insert(new ModelBook("King and Maxwell", 6, 1));
		book.insert(new ModelBook("Zoo", 4, 7));
		book.insert(new ModelBook("The Hit", 6, 1));
		book.insert(new ModelBook("Lifeguard", 4, 8));
		book.insert(new ModelBook("Private London", 4, 9));
		book.insert(new ModelBook("Killing Floor", 10, 1));
		book.insert(new ModelBook("Die Trying", 10, 1));
		book.insert(new ModelBook("Tripwire", 10, 1));
		book.insert(new ModelBook("Running Blind", 10, 1));
		book.insert(new ModelBook("Echo Burning", 10, 1));
		book.insert(new ModelBook("Without Fail", 10, 1));
		book.insert(new ModelBook("Persuader", 10, 1));
		book.insert(new ModelBook("One Shot", 10, 1));
		book.insert(new ModelBook("Killing Floor", 10, 1));
		book.insert(new ModelBook("The Hard Way", 10, 1));
		book.insert(new ModelBook("Bad Luck and Trouble", 10, 1));
		book.insert(new ModelBook("Nothing To Lose", 10, 1));
		book.insert(new ModelBook("Gone Tomorrow", 10, 1));
		book.insert(new ModelBook("The Cold Dish", 11, 1));
		book.insert(new ModelBook("Death Without Company", 11, 1));
		book.insert(new ModelBook("Kindness Goes Unpunished", 11, 1));
		book.insert(new ModelBook("Another Man's Mocasins", 11, 1));
		book.insert(new ModelBook("The Dark Horse", 11, 1));
		book.insert(new ModelBook("Junkyard Dogs", 11, 1));
		book.insert(new ModelBook("Hell Is Empty", 11, 1));
		book.insert(new ModelBook("As The Crow Flies", 11, 1));
		book.insert(new ModelBook("A Serpant's Tooth", 11, 1));
		book.insert(new ModelBook("Spirit of Steamboat", 11, 1));
		book.insert(new ModelBook("Any Other Name", 11, 1));
		book.insert(new ModelBook("Private Down Under", 4, 12));
		book.insert(new ModelBook("In the Moon of Red Ponies", 13, 1));
		book.insert(new ModelBook("The Eye of the World", 14, 1));
		book.insert(new ModelBook("The Great Hunt", 14, 1));
		book.insert(new ModelBook("The Dragon Reborn", 14, 1));
		book.insert(new ModelBook("The Shadow Rising", 14, 1));
		book.insert(new ModelBook("The Fires of Heaven", 14, 1));
		book.insert(new ModelBook("Lord of Chaos", 14, 1));
		book.insert(new ModelBook("A Crown of Swords", 14, 1));
		book.insert(new ModelBook("The Path of Daggers", 14, 1));
		book.insert(new ModelBook("Winter's Heart", 14, 1));
		book.insert(new ModelBook("Crossroads of Twilight", 14, 1));
		book.insert(new ModelBook("Knife of Dreams", 14, 1));
		book.insert(new ModelBook("The Gathering Storm", 14, 21));
		book.insert(new ModelBook("Towers of Midnight", 14, 21));
		book.insert(new ModelBook("A Memory of Light", 14, 21));
		book.insert(new ModelBook("New Spring", 14, 1));
		book.insert(new ModelBook("Interview with the Vampire", 15, 1));
		book.insert(new ModelBook("The Vampire Lestat", 15, 1));
		book.insert(new ModelBook("The Queen of the Damned", 15, 1));
		book.insert(new ModelBook("The Tale of the Body Thief", 15, 1));
		book.insert(new ModelBook("Memnoch the Devil", 15, 1));
		book.insert(new ModelBook("Dune", 16, 1));
		book.insert(new ModelBook("Children of Dune", 16, 1));
		book.insert(new ModelBook("God Emperor of Dune", 16, 1));
		book.insert(new ModelBook("Dune Messiah", 16, 1));
		book.insert(new ModelBook("Sahara", 17, 1));
		book.insert(new ModelBook("The Hunt for Red October", 18, 1));
		book.insert(new ModelBook("Harry Potter and the Philosopher's Stone", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Chamber of Secrets", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Prisoner of Azkaban", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Goblet of Fire", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Order of the Phoenix ", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Half-Blood Prince", 19, 1));
		book.insert(new ModelBook("Harry Potter and the Deathly Hallows", 19, 1));
		book.insert(new ModelBook("Twilight", 20, 1));
		book.insert(new ModelBook("New Moon", 20, 1));
		book.insert(new ModelBook("Eclipse", 20, 1));
		book.insert(new ModelBook("Breaking Dawn", 20, 1));
		book.insert(new ModelBook("The Fifth Assassin", 22, 1));
		book.insert(new ModelBook("The Book of Lies", 22, 1));
		book.insert(new ModelBook("The Book of Fate", 22, 1));
		book.insert(new ModelBook("The Book of Fate", 22, 1));
		book.insert(new ModelBook("Identity", 23, 1));
		book.insert(new ModelBook("Supremacy", 23, 1));
		book.insert(new ModelBook("Ultimatum", 23, 1));
		
		
		
	}
	
	private void createBookView(SQLiteDatabase db) {
		
		String qry = DaoBooksView.createViewDdl();
		Log.v("DB", qry);

        try {
			db.execSQL(qry);
		} catch (Exception e) {
			Log.e("DB Error", "create  " + e.toString());
			e.printStackTrace();
		}
	}
	
}
