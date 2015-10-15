package si.krkavolley.volleystat;

import android.database.DatabaseUtils;
import si.krkavolley.volleystat.Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// DB version
	private static final int DB_VERSION = 4;

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
	private static final String KEY_GAME_NAME = "game_name";
	private static final String KEY_GAME_DATE = "game_date";
	private static final String KEY_GAME_SCORE = "game_score";

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
	private static final String KEY_SERVE_wa = "serve_wa"; // 4
	private static final String KEY_SERVE_over = "serve_over"; // 5
	private static final String KEY_SERVE_e = "serve_e"; // 6

	// opponent errors
	private static final String KEY_OPP_ERR = "opp_err";

	// Table create statements
	private static final String CREATE_TABLE_PLAYER = "CREATE TABLE "
			+ TABLE_PLAYER + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PLAYER_NAME + " TEXT, " + KEY_PLAYER_DESC + " TEXT" + ");";

	private static final String CREATE_TABLE_GAME = "CREATE TABLE "
			+ TABLE_GAME + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_GAME_NAME + " TEXT, " + KEY_GAME_DESC + " TEXT,"
			+ KEY_GAME_DATE + " TEXT, " + KEY_GAME_SCORE + " TEXT" + ");";

	private static final String CREATE_TABLE_STATS = "CREATE TABLE "
			+ TABLE_STATS + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_GAME_ID + " INTEGER, " + KEY_PLAYER_ID + " INTEGER,"
			+ KEY_SET + " INTEGER, " + KEY_RECEPTION_3 + " INTEGER, "
			+ KEY_RECEPTION_2 + " INTEGER, " + KEY_RECEPTION_1 + " INTEGER, "
			+ KEY_RECEPTION_0 + " INTEGER, " + KEY_RECEPTION_wa + " INTEGER, "
			+ KEY_RECEPTION_over + " INTEGER, " + KEY_ATTACK_3 + " INTEGER, "
			+ KEY_ATTACK_2 + " INTEGER, " + KEY_ATTACK_1 + " INTEGER, "
			+ KEY_ATTACK_0 + " INTEGER, " + KEY_ATTACK_e + " INTEGER, "
			+ KEY_ATTACK_ee + " INTEGER, " + KEY_ATTACK_b + " INTEGER, "
			+ KEY_ATTACK_bb + " INTEGER, " + KEY_SERVE_3 + " INTEGER, "
			+ KEY_SERVE_2 + " INTEGER, " + KEY_SERVE_1 + " INTEGER, "
			+ KEY_SERVE_0 + " INTEGER, " + KEY_SERVE_wa + " INTEGER, "
			+ KEY_SERVE_e + " INTEGER, " + KEY_SERVE_over + " INTEGER" + ");";

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

	// Create a player
	public long createPlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PLAYER_NAME, player.getName());
		values.put(KEY_PLAYER_DESC, player.getDescription());

		long playerId = db.insert(TABLE_PLAYER, null, values);
		Log.d(LOG, "nov igralec: " + player.getName());
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

	// Remove a player
	public void removePlayer(long playerId) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.delete(TABLE_PLAYER, KEY_ID + "=?", new String[] { "" + playerId });
		db.close();
		Log.d(LOG, "Deleted player with id: " + playerId);
		return;
	}

	// Retrieve all players
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		String query = "SELECT * FROM " + TABLE_PLAYER + " ORDER BY "
				+ KEY_PLAYER_NAME;
		// Log.e(LOG, query);

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
	public HashMap<Integer, Player> getAllPlayersHashMap() {
		HashMap<Integer, Player> map = new HashMap<Integer, Player>();
		
		String query = "SELECT * FROM " + TABLE_PLAYER + " ORDER BY "
				+ KEY_PLAYER_NAME;
		// Log.e(LOG, query);

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
				map.put(e.getId(), e);
			} while (c.moveToNext());
		}
		return map;
	}
	
	
	// Create a game
	public long createGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_GAME_NAME, game.getName());
		values.put(KEY_GAME_DESC, game.getDescription());
		values.put(KEY_GAME_DATE, game.getDate());
		values.put(KEY_GAME_SCORE, game.getScore());

		long gameId = db.insert(TABLE_GAME, null, values);
		Log.d(LOG, "nova tekma: " + game.getName());
		return gameId;
	}

	// Retrieve a game
	public Game getGame(long gameId) {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM " + TABLE_GAME + " WHERE " + KEY_ID
				+ " = " + gameId;

		Log.e(LOG, query);

		Cursor c = db.rawQuery(query, null);

		if (c != null)
			c.moveToFirst();

		Game g = new Game();
		g.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		g.setName(c.getString(c.getColumnIndex(KEY_GAME_NAME)));
		g.setDescription(c.getString(c.getColumnIndex(KEY_GAME_DESC)));
		g.setDate(c.getString(c.getColumnIndex(KEY_GAME_DATE)));
		g.setScore(c.getString(c.getColumnIndex(KEY_GAME_SCORE)));

		return g;
	}

	// Retrieve all games
	public List<Game> getAllGames() {
		List<Game> games = new ArrayList<Game>();

		String query = "SELECT * FROM " + TABLE_GAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// loop through and add to list
		if (c.moveToFirst()) {
			do {
				Game g = new Game();
				g.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				g.setName(c.getString(c.getColumnIndex(KEY_GAME_NAME)));
				g.setDescription(c.getString(c.getColumnIndex(KEY_GAME_DESC)));
				g.setDate(c.getString(c.getColumnIndex(KEY_GAME_DATE)));
				g.setScore(c.getString(c.getColumnIndex(KEY_GAME_SCORE)));

				// add to list
				games.add(g);
			} while (c.moveToNext());
		}
		return games;
	}

	// Remove a game
	public void removeGame(long gameId) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.delete(TABLE_GAME, KEY_ID + "=?", new String[] { "" + gameId });
		db.close();
		Log.d(LOG, "Deleted game with id: " + gameId);
		return;
	}

	// Create a stat
	public long createNewStat(Stat stat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_GAME_ID, stat.getGame_id());
		values.put(KEY_SET, stat.getGame_set());
		values.put(KEY_PLAYER_ID, stat.getPlayer_id());
		values.put(KEY_SERVE_0, 0);
		values.put(KEY_SERVE_1, 0);
		values.put(KEY_SERVE_2, 0);
		values.put(KEY_SERVE_3, 0);
		values.put(KEY_SERVE_wa, 0);
		values.put(KEY_SERVE_e, 0);
		values.put(KEY_SERVE_over, 0);
		values.put(KEY_RECEPTION_0, 0);
		values.put(KEY_RECEPTION_1, 0);
		values.put(KEY_RECEPTION_2, 0);
		values.put(KEY_RECEPTION_3, 0);
		values.put(KEY_RECEPTION_over, 0);
		values.put(KEY_RECEPTION_wa, 0);
		values.put(KEY_ATTACK_0, 0);
		values.put(KEY_ATTACK_1, 0);
		values.put(KEY_ATTACK_2, 0);
		values.put(KEY_ATTACK_3, 0);
		values.put(KEY_ATTACK_e, 0);
		values.put(KEY_ATTACK_ee, 0);
		values.put(KEY_ATTACK_b, 0);
		values.put(KEY_ATTACK_bb, 0);

		long statId = db.insert(TABLE_STATS, null, values);
		Log.d(LOG, "new stat");
		return statId;
	}

	public void writeServe(int gameId, int playerId, int setNumber,
			int serveType) {
		SQLiteDatabase db = this.getReadableDatabase();
		SQLiteDatabase dbw = this.getWritableDatabase();
		Log.d("hello", "hello");
		Cursor cursor = db.query(TABLE_STATS, new String[] { KEY_ID,
				KEY_PLAYER_ID, KEY_GAME_ID }, KEY_PLAYER_ID + "=? and "
				+ KEY_GAME_ID + "=? and " + KEY_SET + "=?", new String[] {
				"" + playerId, "" + gameId, "" + setNumber }, null, null, null);

		if (cursor.moveToFirst()) {
			Log.d("Query", "seeking stats: found");
			// stat = cursorToStat(cursor);
			// increase appropriate counter
			switch (serveType) {
			case 0:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_0
						+ " = " + KEY_SERVE_0 + " + 1 WHERE " + KEY_GAME_ID
						+ " = ? AND " + KEY_PLAYER_ID + " = ? AND " + KEY_SET
						+ " = ?", new String[] { "" + gameId, "" + playerId,
						"" + setNumber });
				Log.d("Serve", "Counter 0 increased");
				break;
			case 1:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_1
						+ " = " + KEY_SERVE_1 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter 1 increased");
				break;
			case 2:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_2
						+ " = " + KEY_SERVE_2 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter 2 increased");
				break;
			case 3:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_3
						+ " = " + KEY_SERVE_3 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter 3 increased");
				break;
			case 4:

				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_wa
						+ " = " + KEY_SERVE_wa + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter wa increased");
				break;
			case 5:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_over
						+ " = " + KEY_SERVE_over + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter over increased");
				break;
			case 6:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_SERVE_e
						+ " = " + KEY_SERVE_e + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Serve", "Counter error increased");
				break;

			default:
				break;
			}
			db.close();
			dbw.close();
		} else {
			Log.d("Query", "seeking stats: not found");
			Stat stat = new Stat(gameId, playerId, setNumber);
			createNewStat(stat);
			writeServe(gameId, playerId, setNumber, serveType);
		}
	}

	public void writeReception(int gameId, int playerId, int setNumber,
			int receptionType) {
		SQLiteDatabase db = this.getReadableDatabase();
		SQLiteDatabase dbw = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_STATS, new String[] { KEY_ID,
				KEY_PLAYER_ID, KEY_GAME_ID }, KEY_PLAYER_ID + "=? and "
				+ KEY_GAME_ID + "=? and " + KEY_SET + "=?", new String[] {
				"" + playerId, "" + gameId, "" + setNumber }, null, null, null);

		if (cursor.moveToFirst()) {
			Log.d("Query", "seeking stats: found");
			// stat = cursorToStat(cursor);
			// increase appropriate counter
			switch (receptionType) {
			case 0:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_RECEPTION_0
						+ " = " + KEY_RECEPTION_0 + " + 1 WHERE " + KEY_GAME_ID
						+ " = ? AND " + KEY_PLAYER_ID + " = ? AND " + KEY_SET
						+ " = ?", new String[] { "" + gameId, "" + playerId,
						"" + setNumber });
				Log.d("Reception", "Counter 0 increased");
				break;
			case 1:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_RECEPTION_1
						+ " = " + KEY_RECEPTION_1 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Reception", "Counter 1 increased");
				break;
			case 2:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_RECEPTION_2
						+ " = " + KEY_RECEPTION_2 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Reception", "Counter 2 increased");
				break;
			case 3:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_RECEPTION_3
						+ " = " + KEY_RECEPTION_3 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Reception", "Counter 3 increased");
				break;
			case 4:

				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_RECEPTION_wa
						+ " = " + KEY_RECEPTION_wa + " + 1 WHERE "
						+ KEY_GAME_ID + " = " + gameId + " AND "
						+ KEY_PLAYER_ID + " = " + playerId + " AND " + KEY_SET
						+ " = " + setNumber);
				Log.d("Reception", "Counter wa increased");
				break;
			case 5:
				db.execSQL("UPDATE " + TABLE_STATS + " SET "
						+ KEY_RECEPTION_over + " = " + KEY_RECEPTION_over
						+ " + 1 WHERE " + KEY_GAME_ID + " = " + gameId
						+ " AND " + KEY_PLAYER_ID + " = " + playerId + " AND "
						+ KEY_SET + " = " + setNumber);
				Log.d("Reception", "Counter over increased");
				break;
			default:
				break;
			}
			db.close();
			dbw.close();
		} else {
			Log.d("Query", "seeking stats: not found");
			Stat stat = new Stat(gameId, playerId, setNumber);
			createNewStat(stat);
			writeReception(gameId, playerId, setNumber, receptionType);
		}
	}

	public void writeAttack(int gameId, int playerId, int setNumber,
			int attackType) {
		SQLiteDatabase db = this.getReadableDatabase();
		SQLiteDatabase dbw = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_STATS, new String[] { KEY_ID,
				KEY_PLAYER_ID, KEY_GAME_ID }, KEY_PLAYER_ID + "=? and "
				+ KEY_GAME_ID + "=? and " + KEY_SET + "=?", new String[] {
				"" + playerId, "" + gameId, "" + setNumber }, null, null, null);

		if (cursor.moveToFirst()) {
			Log.d("Query", "seeking stats: found");
			// stat = cursorToStat(cursor);
			// increase appropriate counter
			switch (attackType) {
			case 0:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_0
						+ " = " + KEY_ATTACK_0 + " + 1 WHERE " + KEY_GAME_ID
						+ " = ? AND " + KEY_PLAYER_ID + " = ? AND " + KEY_SET
						+ " = ?", new String[] { "" + gameId, "" + playerId,
						"" + setNumber });
				Log.d("Attack", "Counter 0 increased");
				break;
			case 1:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_1
						+ " = " + KEY_ATTACK_1 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("ATTACK", "Counter 1 increased");
				break;
			case 2:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_2
						+ " = " + KEY_ATTACK_2 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Attack", "Counter 2 increased");
				break;
			case 3:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_3
						+ " = " + KEY_ATTACK_3 + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("ATTACK", "Counter 3 increased");
				break;
			case 4:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_e
						+ " = " + KEY_ATTACK_e + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Attack", "Counter e increased");
				break;
			case 5:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_ee
						+ " = " + KEY_ATTACK_ee + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Attack", "Counter ee increased");
				break;
			case 6:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_b
						+ " = " + KEY_ATTACK_b + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Attack", "Counter b increased");
				break;
			case 7:
				db.execSQL("UPDATE " + TABLE_STATS + " SET " + KEY_ATTACK_bb
						+ " = " + KEY_ATTACK_bb + " + 1 WHERE " + KEY_GAME_ID
						+ " = " + gameId + " AND " + KEY_PLAYER_ID + " = "
						+ playerId + " AND " + KEY_SET + " = " + setNumber);
				Log.d("Attack", "Counter bb increased");
				break;

			default:
				break;
			}
			db.close();
			dbw.close();
		} else {
			Log.d("Query", "seeking stats: not found");
			Stat stat = new Stat(gameId, playerId, setNumber);
			createNewStat(stat);
			writeAttack(gameId, playerId, setNumber, attackType);
		}
	}


	public List<Stat> getPlayerStats(int playerId, int gameId) {
		List<Stat> stats = new ArrayList<Stat>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_STATS, new String[] { "*" },
				KEY_PLAYER_ID + "=? and " + KEY_GAME_ID + "=? ORDER BY " + KEY_SET, new String[] {
						"" + playerId, "" + gameId }, null, null, null);
		Log.d("Cursor", "db.query successful");
		
		if (cursor.moveToFirst()) {
			do {
				Stat stat = new Stat();
				stat = statCursorToStat(cursor);
				stats.add(stat);
			} while (cursor.moveToNext());
		}
		return stats;
	}
	
	public List<Stat> getFullGameStats(int gameId){
		List<Stat> statsList = new ArrayList<Stat>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STATS, new String[] { 
				KEY_ID+","
				+KEY_PLAYER_ID+","
				+KEY_GAME_ID+","
				+KEY_SET
				+", sum("+KEY_RECEPTION_3+") as "+KEY_RECEPTION_3
				+", sum("+KEY_RECEPTION_2+") as "+KEY_RECEPTION_2
				+", sum("+KEY_RECEPTION_1+") as "+KEY_RECEPTION_1
				+", sum("+KEY_RECEPTION_0+") as "+KEY_RECEPTION_0
				+", sum("+KEY_RECEPTION_wa+") as "+KEY_RECEPTION_wa
				+", sum("+KEY_RECEPTION_over+") as "+KEY_RECEPTION_over
				+", sum("+KEY_ATTACK_3+") as "+KEY_ATTACK_3
				+", sum("+KEY_ATTACK_2+") as "+KEY_ATTACK_2
				+", sum("+KEY_ATTACK_1+") as "+KEY_ATTACK_1
				+", sum("+KEY_ATTACK_0+") as "+KEY_ATTACK_0
				+", sum("+KEY_ATTACK_e+") as "+KEY_ATTACK_e
				+", sum("+KEY_ATTACK_ee+") as "+KEY_ATTACK_ee
				+", sum("+KEY_ATTACK_b+") as "+KEY_ATTACK_b
				+", sum("+KEY_ATTACK_bb+") as "+KEY_ATTACK_bb
				+", sum("+KEY_SERVE_3+") as "+KEY_SERVE_3
				+", sum("+KEY_SERVE_2+") as "+KEY_SERVE_2
				+", sum("+KEY_SERVE_1+") as "+KEY_SERVE_1
				+", sum("+KEY_SERVE_0+") as "+KEY_SERVE_0
				+", sum("+KEY_SERVE_wa+") as "+KEY_SERVE_wa
				+", sum("+KEY_SERVE_over+") as "+KEY_SERVE_over
				+", sum("+KEY_SERVE_e+") as "+KEY_SERVE_e
				+" "},
				KEY_GAME_ID + "=? " , new String[] {
						"" + gameId},  KEY_PLAYER_ID , null, null);
		Log.d("Cursor", ""+DatabaseUtils.dumpCursorToString(cursor));
		if (cursor.moveToFirst()) {
			do {
				Stat stat = new Stat();
				stat = statCursorToStat(cursor);
				statsList.add(stat);
			} while (cursor.moveToNext());
		}
		//DatabaseUtils.dumpCursorToString(cursor);
		return statsList;
	}
	

	private Stat statCursorToStat(Cursor cursor) {

		Stat stat = new Stat();
		if (cursor != null) {
			stat.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			stat.setGame_id(cursor.getInt(cursor.getColumnIndex(KEY_GAME_ID)));
			stat.setPlayer_id(cursor.getInt(cursor
					.getColumnIndex(KEY_PLAYER_ID)));
			stat.setGame_set(cursor.getInt(cursor.getColumnIndex(KEY_SET)));
			stat.setReception_3(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_3)));
			stat.setReception_2(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_2)));
			stat.setReception_1(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_1)));
			stat.setReception_0(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_0)));
			stat.setReception_wa(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_wa)));
			stat.setReception_over(cursor.getInt(cursor
					.getColumnIndex(KEY_RECEPTION_over)));
			stat.setAttack_3(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_3)));
			stat.setAttack_2(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_2)));
			stat.setAttack_1(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_1)));
			stat.setAttack_0(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_0)));
			stat.setAttack_e(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_e)));
			stat.setAttack_ee(cursor.getInt(cursor
					.getColumnIndex(KEY_ATTACK_ee)));
			stat.setAttack_b(cursor.getInt(cursor.getColumnIndex(KEY_ATTACK_b)));
			stat.setAttack_bb(cursor.getInt(cursor
					.getColumnIndex(KEY_ATTACK_bb)));
			stat.setServe_3(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_3)));
			stat.setServe_2(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_2)));
			stat.setServe_1(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_1)));
			stat.setServe_0(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_0)));
			stat.setServe_wa(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_wa)));
			stat.setServe_e(cursor.getInt(cursor.getColumnIndex(KEY_SERVE_e)));
			stat.setServe_over(cursor.getInt(cursor
					.getColumnIndex(KEY_SERVE_over)));
		}
		return stat;
	}

	// Close database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}
