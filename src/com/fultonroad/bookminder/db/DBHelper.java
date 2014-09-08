package com.fultonroad.bookminder.db;

import com.fultonroad.bookminder.db.author.DaoAuthors;
import com.fultonroad.bookminder.db.author.ModelAuthor;
import com.fultonroad.bookminder.db.book.DaoBooks;
import com.fultonroad.bookminder.db.book.ModelBook;
import com.fultonroad.bookminder.db.bookview.DaoBooksView;
import com.fultonroad.bookminder.db.series.DaoSeries;
import com.fultonroad.bookminder.db.series.ModelSeries;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
	
	private static final String kDATABASE_NAME	= "bookMinder.db";
	private static final int kDATABASE_VERSION = 1;

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
        createSeriesTable(db);
        createBookView(db);
        Log.v("DB", "after creation");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + DaoBooks.kTABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DaoAuthors.kTABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DaoSeries.kTABLE_NAME);
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
		author.insert(new ModelAuthor(20, "Stephanie", "Meyer"));
		author.insert(new ModelAuthor(21, "Brandon", "Sanderson"));
		author.insert(new ModelAuthor(22, "Brad", "Meltzer"));
		author.insert(new ModelAuthor(23, "Robert", "Ludlum"));
		author.insert(new ModelAuthor(24, "Brian", "Herbert"));
		author.insert(new ModelAuthor(25, "Kevin J.", "Anderson"));
		author.insert(new ModelAuthor(26, "Maxine", "Paetro"));
		author.insert(new ModelAuthor(27, "Mark", "Sullivan"));
		author.insert(new ModelAuthor(28, "Ashwin", "Sanghi"));
		author.insert(new ModelAuthor(29, "Marshall", "Karp"));
		author.insert(new ModelAuthor(30, "Stephen R.", "Donaldson"));
		author.insert(new ModelAuthor(31, "William", "Gibson"));

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
		book.insert(new ModelBook("Storm Front", 2000, 2, 1, true, true, 3));
		book.insert(new ModelBook("Full Moon", 2001, 2, 1, true, true, 3));
		book.insert(new ModelBook("Grave Peril", 2001, 2, 1, true, true, 3));
		book.insert(new ModelBook("Summer Knight", 2002, 2, 1, true, true, 3));
		book.insert(new ModelBook("Death Masks", 2003, 2, 1, true, true, 3));
		book.insert(new ModelBook("Blood Rites", 2004, 2, 1, true, true, 3));
		book.insert(new ModelBook("Dead Beat", 2006, 2, 1, true, true, 3));
		book.insert(new ModelBook("Proven Guilty", 2007, 2, 1, true, true, 3));
		book.insert(new ModelBook("White Knight", 2008, 2, 1, true, true, 3));
		book.insert(new ModelBook("Small Favor", 2009, 2, 1, true, true, 3));
		book.insert(new ModelBook("Turn Coat", 2010, 2, 1, true, true, 3));
		book.insert(new ModelBook("Changes", 2011, 2, 1, true, true, 3));
		book.insert(new ModelBook("Ghost Story", 2012, 2, 1, true, true, 3));
		book.insert(new ModelBook("Cold Days", 2013, 2, 1, false, false, 3));
		book.insert(new ModelBook("Skin Game", 2014, 2, 1, false, false, 3));
		book.insert(new ModelBook("Furies of Calderon", 2004, 2, 1, false, true, 4));
		book.insert(new ModelBook("Academ's Fury", 2005, 2, 1, false, true, 4));
		book.insert(new ModelBook("Cursor's Fury", 2006, 2, 1, false, true, 4));
		book.insert(new ModelBook("Captain's Fury", 2007, 2, 1, false, true, 4));
		book.insert(new ModelBook("Princep's Fury", 2008, 2, 1, false, false, 4));
		book.insert(new ModelBook("First Lord's Fury", 2009, 2, 1, false, false, 4));
		book.insert(new ModelBook("Cryptonomicon", 1999, 3, 1, false, true));
		book.insert(new ModelBook("The Hobbit", 1937, 5, 1, false, true));
		book.insert(new ModelBook("The Fellowship of the Ring", 1054, 5, 1, false, true));
		book.insert(new ModelBook("The Return of the King", 1955, 5, 1, false, true));
		book.insert(new ModelBook("The Two Towers", 1954, 5, 1, false, true));
		book.insert(new ModelBook("I, Alex Cross", 2009, 4, 1, false, false));
		book.insert(new ModelBook("Private", 2010, 4, 1, false, true));
		book.insert(new ModelBook("The Camel Club", 2005, 6, 1, false, true));
		book.insert(new ModelBook("King and Maxwell", 2003, 6, 1, false, true));
		book.insert(new ModelBook("Zoo", 2012, 4, 7, false, true));
		book.insert(new ModelBook("The Hit", 2013, 6, 1, false, true));
		book.insert(new ModelBook("Lifeguard", 2005, 4, 8, false, true));
		book.insert(new ModelBook("Private London", 2011, 4, 9, false, true));
		book.insert(new ModelBook("Private Vegas", 2015, 4, 26, false, false));
		book.insert(new ModelBook("Private L.A.", 2015, 4, 27, false, false));
		book.insert(new ModelBook("Private India", 2014, 4, 28, false, false));
		book.insert(new ModelBook("Private Games", 2012, 4, 27, false, false));
		book.insert(new ModelBook("Private Berlin", 2013, 4, 27, false, true));
		book.insert(new ModelBook("Private #1 Suspect", 2012, 4, 26, false, true));
		book.insert(new ModelBook("Killing Floor", 1997, 10, 1, false, false));
		book.insert(new ModelBook("Die Trying", 1998, 10, 1, false, false));
		book.insert(new ModelBook("Tripwire", 1999, 10, 1, false, false));
		book.insert(new ModelBook("Running Blind", 2000, 10, 1, false, false));
		book.insert(new ModelBook("Echo Burning", 2001, 10, 1, false, false));
		book.insert(new ModelBook("Without Fail", 2002, 10, 1, false, false));
		book.insert(new ModelBook("Persuader", 2003, 10, 1, false, false));
		book.insert(new ModelBook("The Enemy", 2004, 10, 1, false, false));
		book.insert(new ModelBook("One Shot", 2005, 10, 1, false, false));
		book.insert(new ModelBook("The Hard Way", 2006, 10, 1, false, false));
		book.insert(new ModelBook("Bad Luck and Trouble", 2007, 10, 1, false, false));
		book.insert(new ModelBook("Nothing To Lose", 2008, 10, 1, false, false));
		book.insert(new ModelBook("Gone Tomorrow", 2009, 10, 1, false, false));
		book.insert(new ModelBook("The Neon Rain", 1987, 13, 1, false, false));
		book.insert(new ModelBook("Heaven's Prisoners", 1988, 13, 1, false, false));
		book.insert(new ModelBook("Black Cherry Blues", 1989, 13, 1, false, false));
		book.insert(new ModelBook("A Morning for Flamingos", 1990, 13, 1, false, false));
		book.insert(new ModelBook("A Stained White Radiance", 1992, 13, 1, false, false));
		book.insert(new ModelBook("In the Electric Mist with Confederate Dead", 1993, 13, 1, false, false));
		book.insert(new ModelBook("Dixie City Jam", 1994, 13, 1, false, false));
		book.insert(new ModelBook("Burning Angel", 1995, 13, 1, false, false));
		book.insert(new ModelBook("Cadillac Jukebox", 1996, 13, 1, false, false));
		book.insert(new ModelBook("Sunset Limited", 1998, 13, 1, false, false));
		book.insert(new ModelBook("Purple Cane Road", 2000, 13, 1, false, false));
		book.insert(new ModelBook("Jolie Blon's Bounce", 2002, 13, 1, false, false));
		book.insert(new ModelBook("Last Car to Elysian Fields", 2003, 13, 1, false, false));
		book.insert(new ModelBook("Crusader's Cross", 2005, 13, 1, false, false));
		book.insert(new ModelBook("Pegasus Descending", 2006, 13, 1, false, false));
		book.insert(new ModelBook("The Tin Roof Blowdown", 2007, 13, 1, false, false));
		book.insert(new ModelBook("Swan Peak", 2008, 13, 1, false, false));
		book.insert(new ModelBook("The Glass Rainbow", 2010, 13, 1, false, false));
		book.insert(new ModelBook("Creole Belle", 2012, 13, 1, false, false));
		book.insert(new ModelBook("Light of the World", 2013, 13, 1, false, false));		
		book.insert(new ModelBook("Death Without Company", 2006,  11, 1, false, true));
		book.insert(new ModelBook("The Cold Dish", 2004, 11, 1, false, true));
		book.insert(new ModelBook("Kindness Goes Unpunished", 2007, 11, 1, false, true));
		book.insert(new ModelBook("A Serpent's Tooth", 2013, 11, 1, false, true));
		book.insert(new ModelBook("Another Man's Moccasins", 2008, 11, 1, false, true));
		book.insert(new ModelBook("Junkyard Dogs", 2010, 11, 1, false, true));
		book.insert(new ModelBook("Hell Is Empty", 11, 2011,  1, false, true));
		book.insert(new ModelBook("The Dark Horse", 2009, 11, 1, false, true));
		book.insert(new ModelBook("As The Crow Flies", 2012, 11, 1, false, true));
		book.insert(new ModelBook("Spirit of Steamboat", 2013, 11, 1, false, false));
		book.insert(new ModelBook("Any Other Name", 2014, 11, 1, false, false));
		book.insert(new ModelBook("Private Down Under", 2013, 4, 12, false, false));
		book.insert(new ModelBook("Cimarron Rose", 1997, 13, 1, false, false));
		book.insert(new ModelBook("In the Moon of Red Ponies", 2004, 13, 1, false, true));
		book.insert(new ModelBook("I, Michael Bennett", 2012, 4, 7, false, true));
		book.insert(new ModelBook("The Eye of the World", 1984, 14, 1, false, true));
		book.insert(new ModelBook("The Great Hunt", 1990, 14, 1, false, true));
		book.insert(new ModelBook("The Dragon Reborn", 1991, 14, 1, false, true));
		book.insert(new ModelBook("The Shadow Rising", 1992, 14, 1, false, true));
		book.insert(new ModelBook("The Fires of Heaven", 1993, 14, 1, false, true));
		book.insert(new ModelBook("Lord of Chaos", 1994, 14, 1, false, true));
		book.insert(new ModelBook("A Crown of Swords", 1996, 14, 1, false, true));
		book.insert(new ModelBook("The Path of Daggers", 1998, 14, 1, false, true));
		book.insert(new ModelBook("Winter's Heart", 14, 2000, 1, false, true));
		book.insert(new ModelBook("Crossroads of Twilight", 2003, 14, 1, false, true));
		book.insert(new ModelBook("Knife of Dreams", 2005, 14, 1, false, true));
		book.insert(new ModelBook("The Gathering Storm", 2009, 14, 21, false, true));
		book.insert(new ModelBook("Towers of Midnight", 2010, 14, 21, false, true));
		book.insert(new ModelBook("A Memory of Light", 2013, 14, 21, false, false));
		book.insert(new ModelBook("New Spring", 2004, 14, 1, false, true));
		book.insert(new ModelBook("Interview with the Vampire", 1976, 15, 1, false, true, 5));
		book.insert(new ModelBook("The Vampire Lestat", 1985, 15, 1, false, true, 5));
		book.insert(new ModelBook("The Queen of the Damned", 1988, 15, 1, false, true, 5));
		book.insert(new ModelBook("The Tale of the Body Thief", 1992, 15, 1, false, true, 5));
		book.insert(new ModelBook("Memnoch the Devil", 1995, 15, 1, false, true, 5));
		book.insert(new ModelBook("The Vampire Armand", 1998, 15, 1, false, true, 5));
		book.insert(new ModelBook("Merrick", 2000, 15, 1, false, true, 5));
		book.insert(new ModelBook("Blood and Gold", 2001, 15, 1, false, true, 5));
		book.insert(new ModelBook("Blackwood Farm", 2002, 15, 1, false, true, 5));
		book.insert(new ModelBook("Blood Canticle", 2003, 15, 1, false, true, 5));
		book.insert(new ModelBook("Prince Lestat", 2014, 15, 1, false, true, 5));
		book.insert(new ModelBook("The Witching Hour", 1990, 15, 1, false, true, 6));
		book.insert(new ModelBook("Lasher", 1993, 15, 1, false, true, 6));
		book.insert(new ModelBook("Taltos", 1994, 15, 1, false, true, 6));
		book.insert(new ModelBook("NYPD Red", 2012, 4, 29, false, true));
		book.insert(new ModelBook("NYPD Red 2", 2014, 4, 29, false, false));
		book.insert(new ModelBook("NYPD Red 3", 2015, 4, 29, false, false));
		book.insert(new ModelBook("Dune", 1966, 16, 1, false, true));
		book.insert(new ModelBook("Children of Dune", 1976, 16, 1, false, true));
		book.insert(new ModelBook("God Emperor of Dune", 1981, 16, 1, false, true));
		book.insert(new ModelBook("Dune Messiah", 1969, 16, 1, false, true));
		book.insert(new ModelBook("Heretics of Dune", 1984, 16, 1, false, true));
		book.insert(new ModelBook("Chapterhouse: Dune", 1985, 16, 1, false, true));
		book.insert(new ModelBook("Hunters of Dune", 2006, 24, 25, false, true));
		book.insert(new ModelBook("Sandworms of Dune", 2007, 24, 25, false, true));
		book.insert(new ModelBook("Dune: House Atreides", 1999, 24, 25, false, true));
		book.insert(new ModelBook("Dune: House Harkonnen", 2000, 24, 25, false, true));
		book.insert(new ModelBook("Dune: House Corrino", 2001, 24, 25, false, true));
		book.insert(new ModelBook("Dune: The Butlerian Jihad", 2002, 24, 25, false, true));
		book.insert(new ModelBook("Dune: The Machine Crusade", 2003, 24, 25, false, true));
		book.insert(new ModelBook("Dune: The Battle of Corrin", 2004, 24, 25, false, true));
		book.insert(new ModelBook("Paul of Dune", 2008, 24, 25, false, true));
		book.insert(new ModelBook("The Winds of Dune", 2009, 24, 25, false, false));
		book.insert(new ModelBook("The Throne of Dune", 2004, 24, 25, false, false));
		book.insert(new ModelBook("Leto of Dune", 2004, 24, 25, false, false));
		book.insert(new ModelBook("The Sisterhood of Dune", 2012, 24, 25, false, false));
		book.insert(new ModelBook("Mentats of Dune", 2014, 24, 25, false, false));
		book.insert(new ModelBook("The Swordmasters of Dune", 2016, 24, 25, false, false));
		book.insert(new ModelBook("Sahara", 1992, 17, 1, false, false));
		book.insert(new ModelBook("The Hunt for Red October", 1984, 18, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Philosopher's Stone", 1997, 19, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Chamber of Secrets", 1998, 19, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Prisoner of Azkaban", 1999, 19, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Goblet of Fire", 19, 2000, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Order of the Phoenix", 2003, 19, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Half-Blood Prince", 2005, 19, 1, false, false));
		book.insert(new ModelBook("Harry Potter and the Deathly Hallows", 2007, 19, 1, false, false));
		book.insert(new ModelBook("Twilight", 2005, 20, 1, false, true));
		book.insert(new ModelBook("New Moon", 2006, 20, 1, false, true));
		book.insert(new ModelBook("Eclipse", 2007, 20, 1, false, true));
		book.insert(new ModelBook("Breaking Dawn", 2008, 20, 1, false, true));
		book.insert(new ModelBook("The Fifth Assassin", 2013, 22, 1, false, true));
		book.insert(new ModelBook("The Book of Lies", 2008, 22, 1, false, true));
		book.insert(new ModelBook("The Book of Fate", 2006, 22, 1, false, false)); 
		book.insert(new ModelBook("The Inner Circle", 2011, 22, 1, false, false)); 
		book.insert(new ModelBook("Identity", 1980, 23, 1, false, false));
		book.insert(new ModelBook("Supremacy", 1986, 23, 1, false, false));
		book.insert(new ModelBook("Ultimatum", 1990, 23, 1, false, false));
		book.insert(new ModelBook("Lord Foul's Bane", 1977, 30, 1, false, true));
		book.insert(new ModelBook("Snow Crash", 1984, 3, 1, true, true));
		book.insert(new ModelBook("The Diamond Age", 1995, 3, 1, true, true));
		book.insert(new ModelBook("The Big U", 1984, 3, 1, false, false));
		book.insert(new ModelBook("Zodiac", 1988, 3, 1, false, false));
		book.insert(new ModelBook("QuickSilver", 2003, 3, 1, true, false));
		book.insert(new ModelBook("Neuromancer", 1984, 31, 1, true, true));
		book.insert(new ModelBook("Count Zero", 1986, 31, 1, false, false));
		book.insert(new ModelBook("Mona Lisa Overdrive", 1988, 31, 1, false, false));
	}

	private Boolean createSeriesTable(SQLiteDatabase db) {
		Boolean retval = true;
		
		String qry = DaoSeries.createTableDdl();

		Log.v("JMD", qry);
		
		try  {
			db.execSQL(qry);
			insertSampleSeries(db);
		} catch(SQLException ex) {
			retval = false;
		}
		
		return retval;
	}
	
	private void insertSampleSeries(SQLiteDatabase db) {

		DaoSeries series = new DaoSeries(db);
		series.insert(new ModelSeries(1, 4, "Lucas Davenport"));
		series.insert(new ModelSeries(2, 13, "Dave Robicheaux"));
		series.insert(new ModelSeries(3, 2, "The Dresden Files"));
		series.insert(new ModelSeries(4, 2, "The Codex Alera"));
		series.insert(new ModelSeries(5, 15, "The Vampire Chronicles"));
		series.insert(new ModelSeries(6, 15, "Lives of the Mayfair Witches"));

		
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
