package edu.uic.cs478.Muthiah.Server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Static fields to hold the names of the table, database and columns
	final static String TABLE_NAME = "music_request_track";
	final static String _ID = "_id";
	final static String DATE = "date";
	final static String TIME = "time";
	final static String REQUEST_TYPE = "request_type";
	final static String CLIP_NUM = "clip_num";
	final static String CURRENT_STATE = "current_state";

	// String for creating the table
	final private static String create_cmd = "create table " + TABLE_NAME
			+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ REQUEST_TYPE + " TEXT NOT NULL, " + CLIP_NUM + ", "
			+ CURRENT_STATE + " TEXT NOT NULL, " + DATE + " TEXT NOT NULL, "
			+ TIME + " TEXT NOT NULL)";

	// Constants related to the database creation
	final private static String NAME = "music_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	// Constructor with argument as the constructor
	public DatabaseHelper(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}

	/**
	 * Overriding the SQLite onCreate method
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(create_cmd);

	}

	/**
	 * Overriding the SQLite onUpgrade method
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * Overriding the SQLite deleteDatabase method
	 */
	void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}
}
