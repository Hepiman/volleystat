package si.krkavolley.volleystat;
import si.krkavolley.volleystat.Entity.*;
import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// DB version
	private static final int DB_VERSION = 1;

	// DB name
	private static final String DB_NAME = "volleystat_db";
	
	// Table names
	private static final String TABLE_PLAYER = "player";
	private static final String TABLE_GAME = "game";
	private static final String TABLE_STATS = "stats";	
	
	// Common column names
	private static final String KEY_ID = "id";
	
	// PLAYER table column names
	private static final String KEY_PLAYER_NAME = "player_name";
	private static final String KEY_PLAYER_DESC = "player_description";
	
	// GAME table column names
	private static final String KEY_GAME_DESC = "game_desc";
	private static final String KEY_GAME_HOMETEAM = "game_home_team";
	private static final String KEY_GAME_AWAYTEAM = "game_away_team";
	
	// STATS table column names
	private static final String KEY_PLAYER_ID = "player_id";
	private static final String KEY_GAME_ID = "game_id";
	private static final String KEY_SET = "game_set";
	// reception
	private static final String KEY_RECEPTION_3 = "reception_3";
	private static final String KEY_RECEPTION_2 = "reception_2";
	private static final String KEY_RECEPTION_1 = "reception_1";
	private static final String KEY_RECEPTION_0 = "reception_0";
	private static final String KEY_RECEPTION_wa = "reception_wa";
	private static final String KEY_RECEPTION_over = "reception_over";
	
	// attack
	private static final String KEY_ATTACK_3 = "attack_3";
	private static final String KEY_ATTACK_2 = "attack_2";
	private static final String KEY_ATTACK_1 = "attack_1";
	private static final String KEY_ATTACK_0 = "attack_0";
	private static final String KEY_ATTACK_e = "attack_e";
	private static final String KEY_ATTACK_ee = "attack_ee";
	private static final String KEY_ATTACK_b = "attack_b";
	private static final String KEY_ATTACK_bb = "attack_bb";
	
	// serve
	private static final String KEY_SERVE_3 = "serve_3";
	private static final String KEY_SERVE_2 = "serve_2";
	private static final String KEY_SERVE_1 = "serve_1";
	private static final String KEY_SERVE_0 = "serve_0";
	private static final String KEY_SERVE_wa = "serve_wa";
	private static final String KEY_SERVE_over = "serve_over";
	private static final String KEY_SERVE_e = "serve_e";
	
	// opponent errors
	private static final String KEY_OPP_ERR = "opp_err";
	
	
	// Table create statements
	private static final String CREATE_TABLE_PLAYER = "CREATE TABLE "
			+ TABLE_PLAYER + " (" 
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PLAYER_NAME + " TEXT, " 
			+ KEY_PLAYER_DESC + " TEXT" 
			+ ");";
	
	private static final String CREATE_TABLE_GAME = "CREATE TABLE "
			+ TABLE_GAME + " (" 
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PLAYER_NAME + " TEXT, " 
			+ KEY_PLAYER_DESC + " TEXT" 
			+ ");";
	
	private static final String CREATE_TABLE_STATS = "CREATE TABLE "
			+ TABLE_STATS + " (" 
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_GAME_ID + " INTEGER, "
			+ KEY_PLAYER_ID + " INTEGER,"
			+ KEY_SET + " INTEGER, "
			+ KEY_RECEPTION_3 + " INTEGER, "
			+ KEY_RECEPTION_2 + " INTEGER, "
			+ KEY_RECEPTION_1 + " INTEGER, "
			+ KEY_RECEPTION_0 + " INTEGER, "
			+ KEY_RECEPTION_wa + " INTEGER, "
			+ KEY_RECEPTION_over + " INTEGER, "
			+ KEY_ATTACK_3 + " INTEGER, "
			+ KEY_ATTACK_2 + " INTEGER, "
			+ KEY_ATTACK_1 + " INTEGER, "
			+ KEY_ATTACK_0 + " INTEGER, "
			+ KEY_ATTACK_e + " INTEGER, "
			+ KEY_ATTACK_ee + " INTEGER, "
			+ KEY_ATTACK_b + " INTEGER, "
			+ KEY_ATTACK_bb + " INTEGER, "
			+ KEY_SERVE_3 + " INTEGER, "
			+ KEY_SERVE_2 + " INTEGER, "
			+ KEY_SERVE_1 + " INTEGER, "
			+ KEY_SERVE_0 + " INTEGER, "
			+ KEY_SERVE_wa + " INTEGER, "
			+ KEY_SERVE_over + " INTEGER"
			+ ");";
	
	
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_PLAYER);
		db.execSQL(CREATE_TABLE_GAME);
		db.execSQL(CREATE_TABLE_STATS);
		Log.d(LOG, "Creating database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

		Log.d(LOG, "Database upgrade from version " + oldVersion + " to "
				+ newVersion);

		onCreate(db);
	}
	
	// Create an exercise
	public long createPlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PLAYER_NAME, player.getName());
		values.put(KEY_PLAYER_DESC, player.getDescription());
		
		long playerId = db.insert(TABLE_PLAYER, null, values);
		Log.d(LOG, "nov igralec: "+player.getName());
		return playerId;
	}
	
	// Retrieve a player
	public Player getPlayer(long playerId) {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM " + TABLE_PLAYER + " WHERE " + KEY_ID
				+ " = " + playerId;
		
		Log.e(LOG, query);

		Cursor c = db.rawQuery(query, null);

		if (c != null)
			c.moveToFirst();

		Player e = new Player();
		e.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		e.setName(c.getString(c.getColumnIndex(KEY_PLAYER_NAME)));
		e.setDescription(c.getString(c.getColumnIndex(KEY_PLAYER_DESC)));

		return e;
	}
	
	// Retrieve all players
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		String query = "SELECT * FROM " + TABLE_PLAYER;
		//Log.e(LOG, query);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// loop through and add to list
		if (c.moveToFirst()) {
			do {
				Player e = new Player();
				e.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				e.setName(c.getString(c.getColumnIndex(KEY_PLAYER_NAME)));
				e.setDescription(c.getString(c.getColumnIndex(KEY_PLAYER_DESC)));

				// add to list
				players.add(e);
			} while (c.moveToNext());
		}
		return players;
	}
	
	// Close database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}
