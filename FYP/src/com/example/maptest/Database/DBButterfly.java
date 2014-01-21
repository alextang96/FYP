package com.example.maptest.Database;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.maptest.MainActivity;
import com.example.maptest.R;
import com.example.maptest.SessionManager;
import com.example.maptest.UserFunctions;
import com.example.maptest.R.string;
import com.example.maptest.Database.model.Butterfly;
import com.example.maptest.Database.model.Hotspot;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class DBButterfly extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	public static int DATABASE_VERSION;

	// Database Name
	public static final String DATABASE_NAME = "DBButterfly";

	// Login table name
	public static final String BUTTERFLY_TABLE_NAME = "ButterflyInfo";
	public static final String HOTSPOT_TABLE_NAME = "hospot";

	// Butterfly Table Columns names
	public static final String BTF_ID = "_id";
	public static final String BTF_SEX = "sex";
	public static final String BTF_SPECIES = "spec";
	public static final String BTF_CHINESENAME = "chiName";
	public static final String BTF_ENGLISHNAME = "engName";
	public static final String BTF_SCINAME = "sciName";
	public static final String BTF_BODYRANGE = "bodyRange";
	public static final String BTF_RANGETYPE = "rangeType";
	public static final String BTF_FONTCOLOR = "fotColor";
	public static final String BTF_BACKCOLOR = "bakColor";
	public static final String BTF_HAVEWINGTAIL = "haveWingTail";
	public static final String BTF_ADULTHABIT = "adultHabit";
	public static final String BTF_BABYHABIT = "babyHabit";
	public static final String BTF_DETAIL = "idenDetail";
	public static final String BTF_APPEARTIME = "appearTime";
	public static final String BTF_DISTRIBUTIONS = "distributions";
	public static final String BTF_IMAGE1 = "image1";
	public static final String BTF_IMAGE2 = "image2";
	public static final String BTF_IMAGE3 = "image3";

	// Hotspot Table Columns names
	public static final String H_ID = "_id";
	public static final String H_NAME = "name";
	public static final String H_TRANS = "transportation";
	public static final String H_BTF = "butterfly";
	public static final String H_ENVIRO = "environment";

	// Application context
	private static Context context;

	static SQLiteDatabase db;

	// Create table SQL statement
	private static final String CREATE_BUTTERFLY_TABLE = "CREATE TABLE "
			+ BUTTERFLY_TABLE_NAME + "(" + BTF_ID + " INTEGER PRIMARY KEY,"
			+ BTF_SEX + " TEXT," + BTF_SPECIES + " TEXT," + BTF_CHINESENAME
			+ " TEXT," + BTF_ENGLISHNAME + " TEXT," + BTF_SCINAME + " TEXT,"
			+ BTF_BODYRANGE + " TEXT," + BTF_RANGETYPE + " TEXT,"
			+ BTF_FONTCOLOR + " TEXT," + BTF_BACKCOLOR + " TEXT,"
			+ BTF_HAVEWINGTAIL + " TEXT," + BTF_ADULTHABIT + " TEXT,"
			+ BTF_BABYHABIT + " TEXT," + BTF_DETAIL + " TEXT," + BTF_APPEARTIME
			+ " TEXT," + BTF_DISTRIBUTIONS + " TEXT," + BTF_IMAGE1 + " TEXT,"
			+ BTF_IMAGE2 + " TEXT," + BTF_IMAGE3 + " TEXT" + ")";

	private static final String CREATE_BUTTERFLY_HOTSPOT_TABLE = "CREATE TABLE "
			+ HOTSPOT_TABLE_NAME
			+ "("
			+ H_ID
			+ " INTEGER PRIMARY KEY,"
			+ H_NAME
			+ " TEXT,"
			+ H_TRANS
			+ " TEXT,"
			+ H_BTF
			+ " TEXT,"
			+ H_ENVIRO + " TEXT" + ")";

	private static SessionManager session;

	// closing database
	public void closeDB() {
		// db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/* Inheritance from DatabaseHelper */

	@Override
	public SQLiteDatabase getReadableDatabase() {
		Log.i("Readable Database Got", "true");
		Log.i("mGetReadable Version", DATABASE_VERSION + "");
		return super.getReadableDatabase();
	}

	private static boolean databaseExist(Context context) {
		File dbFile = context.getDatabasePath(DATABASE_NAME);
		Log.i("databaseExist(context)", dbFile.exists() + "");
		return dbFile.exists();
	}

	public DBButterfly(Context context) {
		this(context, loadCurrentVersion(context));
		this.setContext(context);
	}

	private DBButterfly(Context context, int version) {
		this(context, DATABASE_NAME, null, version);
		Log.i("Current Database Version", DATABASE_VERSION + "");
	}

	private DBButterfly(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static int loadCurrentVersion(Context context) {
		// Session Manager
		session = new SessionManager(context);
		boolean isCheckedUpdated = false;
		HashMap UserDetails;
		UserDetails = session.getUserDetails();

		int intDBVersion = (Integer) UserDetails
				.get(SessionManager.DATABASE_VERSION);
		Log.i("DBButterfly", "Got session db version : " + intDBVersion);
		// The first Time, it will assign 1
		// In the future, it will assign the incremented number
		DATABASE_VERSION = intDBVersion;

		// Check if the mobile device own the Butterfly Database
		try {
			if (databaseExist(context)) {
				db = SQLiteDatabase.openOrCreateDatabase(
						context.getDatabasePath(DATABASE_NAME), null);
				Log.i("DBButterfly", "openOrCreateDatabase Run");
				Log.i("DBButterfly",
						"Loaded from session? " + !UserDetails.isEmpty()
								+ " Database Version : " + DATABASE_VERSION);
			} else {
				// The database is not Exist
				// It will run onCreate Method

				// initialize an application default setting
				session.initialApplication(isCheckedUpdated, 2);

			}
		} catch (SQLException ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
			Log.i("DBButterfly Load Current Version", ex.getMessage()
					+ " The Database Version is " + db.getVersion());
		}
		return DATABASE_VERSION;
	}

	public void checkForUpdate() {
		// initial the database again because it is a public method
		SQLiteDatabase db = this.getWritableDatabase();

		UserFunctions uf = new UserFunctions(getContext());
		JSONObject json = uf.getVersion();
		final int intRemoteDBVersion;

		// Session Manager
		session = new SessionManager(context);
		boolean isCheckedUpdated = false;
		int intDBVersion;

		HashMap UserDetails;
		UserDetails = session.getUserDetails();
		intDBVersion = (Integer) UserDetails
				.get(SessionManager.DATABASE_VERSION);
		Log.i("DBButterfly check For Update", " DBVersion from session : " + intDBVersion + "");
		if (db == null) {
			Log.i("DBButterfly", "The database is null");
		}
		Log.i("DBButterfly", "The database is opened?" + db.isOpen());
		ConnectivityManager conMgr = (ConnectivityManager) getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
			Log.i("current active network type", conMgr.getActiveNetworkInfo()
					.getTypeName());

			try {
				intRemoteDBVersion = Integer.valueOf(json.getString("version"));
				String strShowMessage = (String) getContext().getResources()
						.getText(R.string.dwnCtnContent)
						+ " ( "
						+ " ver. "
						+ intDBVersion
						+ " -> "
						+ " ver. "
						+ intRemoteDBVersion
						+ " ) ";
				if (intRemoteDBVersion > intDBVersion) {
					new AlertDialog.Builder(context)
							.setTitle(R.string.dwnCtnTitle)
							.setMessage(strShowMessage)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Run onUpgrade because value changed
											Log.i("DBButterfly",
													"Ready to upgrade Database. Version : "
															+ intRemoteDBVersion);
											session.setDBVersion(intRemoteDBVersion);
											DBButterfly helper = new DBButterfly(getContext());
											SQLiteDatabase db = helper.getReadableDatabase();
											db.close();
											SQLiteDatabase.releaseMemory();

										}
									})
							.setNegativeButton(android.R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// do nothing
											// finish();
										}
									}).show();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// notify user you are online
			Log.i("Network Connection", "Connected");

		} else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			// notify user you are not online
			Log.i("Network Connection", "Disconnected");
		}
	}

	// Bond:
	public HashMap<String, String> getHotPointDetails(String hpName) {
		SQLiteDatabase db = this.getReadableDatabase();
		HashMap<String, String> hpData = new HashMap<String, String>();
		String selectQuery = "select " + "*" + " from " + HOTSPOT_TABLE_NAME
				+ " WHERE " + H_NAME + " = '" + hpName + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			hpData.put(H_TRANS, cursor.getString(0));
			hpData.put(H_BTF, cursor.getString(1));
			hpData.put(H_ENVIRO, cursor.getString(2));
			hpData.put(H_TRANS, cursor.getString(2));
			hpData.put(H_BTF, cursor.getString(3));
			hpData.put(H_ENVIRO, cursor.getString(4));
		} else {
			// No Data
		}
		cursor.close();
		closeDB();
		// return user
		return hpData;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BUTTERFLY_TABLE);
		db.execSQL(CREATE_BUTTERFLY_HOTSPOT_TABLE);

		// UserFunctions -> Json -> Serverside Database
		Log.i("DBButterfly", "Database Created");

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		try {
			if (db == null) {
				Log.i("DBButterfly", "The database is null");
			}
			Log.i("DBButterfly", "onUpgrade Run");
			Log.i("DBButterfly", "The database is opened?" + db.isOpen());
			db.beginTransaction();
			switch (newVersion) {
			case 2:
				Log.i("DBButterfly", "insertTableFromAsset Called");
				insertTableFromAsset(db);
				break;
			default:
				Log.i("DBButterfly", "insertTableFromWeb Called");
				insertTableFromWeb(db);
				break;
			}
		} catch (SQLException e) {
			Log.e("DBButterfly", e.getMessage());
		} finally {
			db.endTransaction();
			// DATABASE_VERSION = newVersion + 1;
			Log.i("DBButterfly", "database finally upgraded to Ver." + newVersion);
		}

	}

	// Download JSON from the webserver and insert back to the SQLite
	private void insertTableFromWeb(SQLiteDatabase db) {
		int intNewVersion;
		Log.i("DBBUTERFLY", "insertTableFromWeb");

		UserFunctions uf = new UserFunctions(getContext());
		JSONObject json = uf.checkVersion(db.getVersion());
		try {
			if (json != null) {
				for (int i = 0; i < json.getInt("noOfRecord"); i++) {
					Butterfly newButterfly = new Butterfly(json.getInt(BTF_ID
							+ i), json.getString(BTF_SEX + i),
							json.getString(BTF_SPECIES + i),
							json.getString(BTF_CHINESENAME + i),
							json.getString(BTF_ENGLISHNAME + i),
							json.getString(BTF_SCINAME + i),
							json.getString(BTF_BODYRANGE + i),
							json.getString(BTF_RANGETYPE + i),
							json.getString(BTF_FONTCOLOR + i),
							json.getString(BTF_BACKCOLOR + i),
							json.getString(BTF_HAVEWINGTAIL + i),
							json.getString(BTF_ADULTHABIT + i),
							json.getString(BTF_BABYHABIT + i),
							json.getString(BTF_DETAIL + i),
							json.getString(BTF_APPEARTIME + i),
							json.getString(BTF_DISTRIBUTIONS + i),
							json.getString(BTF_IMAGE1 + i),
							json.getString(BTF_IMAGE2 + i),
							json.getString(BTF_IMAGE3 + i));

					createButterfly(newButterfly, db);
					Log.i("DBButterfly",
							"Created new record id : "
									+ json.getInt(BTF_ID + i) + "");

				}

				json = uf.getVersion();
				Log.i("json.getString(\"version\")", json.getString("version"));
				intNewVersion = Integer.valueOf(json.getString("version"));
				session.setDBVersion(intNewVersion);
				db.setTransactionSuccessful();
			} else {
				Toast.makeText(
						context,
						"Can not connect to the server, please uninstall the application and try again.",
						Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// insert table in the asset folder
	private void insertTableFromAsset(SQLiteDatabase db) {
		UserFunctions uf = new UserFunctions(getContext());
		JSONObject json = uf.LoadData("butterfly.json", getContext());
		try {
			if (json != null) {
				for (int i = 0; i < json.getInt("noOfRecord"); i++) {
					Butterfly newButterfly = new Butterfly(json.getInt(BTF_ID
							+ i), json.getString(BTF_SEX + i),
							json.getString(BTF_SPECIES + i),
							json.getString(BTF_CHINESENAME + i),
							json.getString(BTF_ENGLISHNAME + i),
							json.getString(BTF_SCINAME + i),
							json.getString(BTF_BODYRANGE + i),
							json.getString(BTF_RANGETYPE + i),
							json.getString(BTF_FONTCOLOR + i),
							json.getString(BTF_BACKCOLOR + i),
							json.getString(BTF_HAVEWINGTAIL + i),
							json.getString(BTF_ADULTHABIT + i),
							json.getString(BTF_BABYHABIT + i),
							json.getString(BTF_DETAIL + i),
							json.getString(BTF_APPEARTIME + i),
							json.getString(BTF_DISTRIBUTIONS + i),
							json.getString(BTF_IMAGE1 + i),
							json.getString(BTF_IMAGE2 + i),
							json.getString(BTF_IMAGE3 + i));

					createButterfly(newButterfly, db);

					Log.i("DBButterfly",
							"Created new record id : "
									+ json.getInt(BTF_ID + i) + "");
				}

				// TODO dummy data
				Hotspot[] newHotspot = new Hotspot[4];
				newHotspot[0] = new Hotspot(
						0,
						"地點1",
						"  巴士： 227,244,58K （test1）",
						"Butterfly1",
						"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test1）");
				newHotspot[1] = new Hotspot(
						1,
						"地點1",
						"  巴士： 227,244,58K （test1）",
						"Butterfly1",
						"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test2）");
				newHotspot[2] = new Hotspot(
						2,
						"地點1",
						"  巴士： 227,244,58K （test1）",
						"Butterfly1",
						"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test3）");
				newHotspot[3] = new Hotspot(
						3,
						"地點1",
						"  巴士： 227,244,58K （test1）",
						"Butterfly1",
						"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test4）");

				for (int i = 0; i < newHotspot.length; i++) {
					this.addHotspotRecord(newHotspot[i], db);
				}
				db.setTransactionSuccessful();
			} else {
				// db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				Toast.makeText(
						context,
						"Can not connect to the server, please uninstall the application and try again.",
						Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Storing butterfly details in database
	 * */
	public void createButterfly(Butterfly butterfly, SQLiteDatabase db) {
		// TODO addRecord
		ContentValues values = new ContentValues();
		values.put(BTF_ID, butterfly.getBTF_ID());
		values.put(BTF_SEX, butterfly.getBTF_SEX());
		values.put(BTF_SPECIES, butterfly.getBTF_SPECIES());
		values.put(BTF_CHINESENAME, butterfly.getBTF_CHINESENAME());
		values.put(BTF_ENGLISHNAME, butterfly.getBTF_ENGLISHNAME());
		values.put(BTF_SCINAME, butterfly.getBTF_SCINAME());
		values.put(BTF_BODYRANGE, butterfly.getBTF_BODYRANGE());
		values.put(BTF_RANGETYPE, butterfly.getBTF_RANGETYPE());
		values.put(BTF_FONTCOLOR, butterfly.getBTF_FONTCOLOR());
		values.put(BTF_BACKCOLOR, butterfly.getBTF_BACKCOLOR());
		values.put(BTF_HAVEWINGTAIL, butterfly.getBTF_HAVEWINGTAIL());
		values.put(BTF_ADULTHABIT, butterfly.getBTF_ADULTHABIT());
		values.put(BTF_BABYHABIT, butterfly.getBTF_BABYHABIT());
		values.put(BTF_DETAIL, butterfly.getBTF_DETAIL());
		values.put(BTF_APPEARTIME, butterfly.getBTF_APPEARTIME());
		values.put(BTF_DISTRIBUTIONS, butterfly.getBTF_DISTRIBUTIONS());
		values.put(BTF_IMAGE1, butterfly.getBTF_IMAGE1());
		values.put(BTF_IMAGE2, butterfly.getBTF_IMAGE2());
		values.put(BTF_IMAGE3, butterfly.getBTF_IMAGE3());

		// Inserting Row
		db.insert(BUTTERFLY_TABLE_NAME, null, values);
	}

	/**
	 * Storing hotspot details in database
	 * */
	public void addHotspotRecord(Hotspot hotspot, SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(H_ID, hotspot.getH_ID());
		values.put(H_NAME, hotspot.getH_NAME());
		values.put(H_TRANS, hotspot.getH_TRANS());
		values.put(H_BTF, hotspot.getH_BTF());
		values.put(H_ENVIRO, hotspot.getH_ENVIRO());

		// Inserting Row
		db.insert(HOTSPOT_TABLE_NAME, null, values);
	}

	/**
	 * Getting butterfly data from database
	 * */
	public HashMap<String, String> getButterflyDetails(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		HashMap<String, String> btfData = new HashMap<String, String>();
		String selectQuery = "select " + "*" + " from " + BUTTERFLY_TABLE_NAME
				+ " WHERE " + BTF_CHINESENAME + " = '" + chiName + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			btfData.put(BTF_ID, cursor.getString(0));
			btfData.put(BTF_SEX, cursor.getString(1));
			btfData.put(BTF_SPECIES, cursor.getString(2));
			btfData.put(BTF_CHINESENAME, cursor.getString(3));
			btfData.put(BTF_ENGLISHNAME, cursor.getString(4));
			btfData.put(BTF_SCINAME, cursor.getString(5));
			btfData.put(BTF_BODYRANGE, cursor.getString(6));
			btfData.put(BTF_RANGETYPE, cursor.getString(7));
			btfData.put(BTF_FONTCOLOR, cursor.getString(8));
			btfData.put(BTF_BACKCOLOR, cursor.getString(9));
			btfData.put(BTF_HAVEWINGTAIL, cursor.getString(10));
			btfData.put(BTF_ADULTHABIT, cursor.getString(11));
			btfData.put(BTF_BABYHABIT, cursor.getString(12));
			btfData.put(BTF_DETAIL, cursor.getString(13));
			btfData.put(BTF_APPEARTIME, cursor.getString(14));
			btfData.put(BTF_DISTRIBUTIONS, cursor.getString(15));
			btfData.put(BTF_IMAGE1, cursor.getString(16));
			btfData.put(BTF_IMAGE2, cursor.getString(17));
			btfData.put(BTF_IMAGE3, cursor.getString(18));
		} else {
			// No Data
		}
		cursor.close();
		closeDB();
		// return user
		return btfData;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(BUTTERFLY_TABLE_NAME, null, null);
		closeDB();
	}

	public static Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		DBButterfly.context = context;
	}

	// Use cursor to translate data to String Array
	public String[] cursorToArray(Cursor cursor) {
		int rows_num = cursor.getCount(); // calculate number of rows

		String[] sNote = new String[rows_num];
		if (rows_num != 0) {
			cursor.moveToFirst(); // pull back to the first row of record
			for (int i = 0; i < rows_num; i++) {
				String strCr = cursor.getString(0);
				sNote[i] = strCr;
				Log.i("result", sNote[i]);

				cursor.moveToNext();// move to next record
			}
		} else {
			sNote = new String[1];
			sNote[0] = "No Record!";
		}

		return sNote;

	}

	public String getNoOfData() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select Count(" + BTF_ID + ") from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		closeDB();
		return sNote[0];
	}

	public String getDetailByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_DETAIL + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		closeDB();

		return sNote[0];
	}

	public String[] getAllSciName() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SCINAME + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		closeDB();

		return sNote;
	}

	public String[] getSpecificChineseNameBySpecies(String species) {
		SQLiteDatabase db = this.getReadableDatabase();
		String statement = "" + "SELECT " + BTF_CHINESENAME + " FROM "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_SPECIES + " LIKE '%"
				+ species + "%'";

		Cursor cursor = db.rawQuery(statement, null);
		Log.i("statement", statement);
		Log.i("cursor", cursor.toString());

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		closeDB();

		return sNote;
	}

	public String[] getChineseNameByRangeType(String rangeType) {
		SQLiteDatabase db = this.getReadableDatabase();
		// backup
		Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + rangeType, null);

		// Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME +
		// " from "
		// + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_RANGETYPE + " = '" +
		// rangeType
		// + "'", null);

		Log.i(rangeType, "hi");
		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		closeDB();

		return sNote;
	}
}
