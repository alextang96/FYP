package com.example.maptest;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

public class DBButterfly extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	public static int DATABASE_VERSION = 1;

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
	public static final String BTF_SUBJECT = "sciName";
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

	// Application context
	private static Context context;

	static SQLiteDatabase db;

	protected static final String H_ID = "_id";
	protected static final String H_NAME = "name";
	protected static final String H_TRANS = "transportation";
	protected static final String H_BTF = "butterfly";

	protected static final String H_ENVIRO = "environment";

	public DBButterfly(Context context) {
		this(context, loadCurrentVersion(context));
	}

	@Override
	public synchronized void close() {
		super.close();
		Log.i("DB Closed", "true");
	}

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

	private static int loadCurrentVersion(Context context) {
		// Check if the mobile device own the Butterfly Database
		try {
			if (databaseExist(context)) {
				db = SQLiteDatabase.openOrCreateDatabase(
						context.getDatabasePath(DATABASE_NAME), null);
				DATABASE_VERSION = db.getVersion();
			} else {
				// The database is not Exist
				Log.i("Database is not Exitst", "Database is not Exitst");
			}
		} catch (SQLException ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
			Log.i("DBButterfly Load Current Version", ex.getMessage()
					+ " The Database Version is " + db.getVersion());
		}
		return DATABASE_VERSION;
	}

	private DBButterfly(Context context, int version) {
		this(context, DATABASE_NAME, null, loadCurrentVersion(context));
		Log.i("Current Database Version", DATABASE_VERSION + "");
	}

	private DBButterfly(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.setContext(context);
		if (DATABASE_VERSION == 1) {
			insertTableFromAsset();
		}

	}

	public void checkForUpdate() {
		ConnectivityManager conMgr = (ConnectivityManager) getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
			Log.i("current active network type", conMgr.getActiveNetworkInfo()
					.getTypeName());
			UserFunctions uf = new UserFunctions(getContext());
			JSONObject json = uf.getVersion();
			int intRemoteDBVersion;
			try {
				intRemoteDBVersion = Integer.valueOf(json.getString("version"));
				String strShowMessage = (String) getContext().getResources()
						.getText(R.string.dwnCtnContent)
						+ " ( "
						+ " ver. "
						+ DATABASE_VERSION
						+ " -> "
						+ " ver. "
						+ intRemoteDBVersion + " ) ";
				
				this.onUpgrade(db, 2, intRemoteDBVersion);
				addHotSpot();
				if (intRemoteDBVersion > DATABASE_VERSION) {
					new AlertDialog.Builder(context)
							.setTitle(R.string.dwnCtnTitle)
							.setMessage(strShowMessage)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Run onUpgrade
											insertTableFromWeb();
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

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BUTTERFLY_TABLE = "CREATE TABLE " + BUTTERFLY_TABLE_NAME
				+ "(" + BTF_ID + " INTEGER PRIMARY KEY," + BTF_SEX + " TEXT,"
				+ BTF_SPECIES + " TEXT," + BTF_CHINESENAME + " TEXT,"
				+ BTF_ENGLISHNAME + " TEXT," + BTF_SUBJECT + " TEXT,"
				+ BTF_BODYRANGE + " TEXT," + BTF_RANGETYPE + " TEXT,"
				+ BTF_FONTCOLOR + " TEXT," + BTF_BACKCOLOR + " TEXT,"
				+ BTF_HAVEWINGTAIL + " TEXT," + BTF_ADULTHABIT + " TEXT,"
				+ BTF_BABYHABIT + " TEXT," + BTF_DETAIL + " TEXT,"
				+ BTF_APPEARTIME + " TEXT," + BTF_DISTRIBUTIONS + " TEXT,"
				+ BTF_IMAGE1 + " TEXT," + BTF_IMAGE2 + " TEXT," + BTF_IMAGE3
				+ " TEXT" + ")";

		String CREATE_BUTTERFLY_HOTSPOT_TABLE = "CREATE TABLE "
				+ HOTSPOT_TABLE_NAME + "(" + H_ID + " INTEGER PRIMARY KEY,"
				+ H_NAME + " TEXT," + H_TRANS + " TEXT," + H_BTF + " TEXT,"
				+ H_ENVIRO + " TEXT" + ")";

		db.execSQL(CREATE_BUTTERFLY_TABLE);
		db.execSQL(CREATE_BUTTERFLY_HOTSPOT_TABLE);

		// UserFunctions -> Json -> Serverside Database
		Log.i("Database Created", "true");
	}

	// Download JSON from the webserver and insert back to the SQLite
	private void insertTableFromWeb() {
		Log.i("DBBUTERFLY", "insertTableFromWeb");
		db = this.getWritableDatabase();
		db.delete(BUTTERFLY_TABLE_NAME, null, null);
		UserFunctions uf = new UserFunctions(getContext());
		JSONObject json = uf.checkVersion(db.getVersion());
		try {
			if (json != null) {
				for (int i = 0; i < json.getInt("noOfRecord"); i++) {
					addRecord(json.getInt(BTF_ID + i),
							json.getString(BTF_SEX + i),
							json.getString(BTF_SPECIES + i),
							json.getString(BTF_CHINESENAME + i),
							json.getString(BTF_ENGLISHNAME + i),
							json.getString(BTF_SUBJECT + i),
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
					Log.i("BTF_ID", json.getInt(BTF_ID + i) + "");
					Log.i("BTF_CHINESENAME",
							json.getString(BTF_CHINESENAME + i) + "");
					Log.i("BTF_DISTRIBUTIONS",
							json.getString(BTF_DISTRIBUTIONS + i));
				}

				json = uf.getVersion();
				Log.i("json.getString(\"version\")", json.getString("version"));
				db.setVersion(Integer.valueOf(json.getString("version")));
				DATABASE_VERSION = Integer.valueOf(json.getString("version"));
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
	private void insertTableFromAsset() {
		SQLiteDatabase db = this.getWritableDatabase();
		UserFunctions uf = new UserFunctions(getContext());
		JSONObject json = uf.LoadData("butterfly.json", getContext());
		try {
			if (json != null) {
				for (int i = 0; i < json.getInt("noOfRecord"); i++) {
					addRecord(json.getInt(BTF_ID + i),
							json.getString(BTF_SEX + i),
							json.getString(BTF_SPECIES + i),
							json.getString(BTF_CHINESENAME + i),
							json.getString(BTF_ENGLISHNAME + i),
							json.getString(BTF_SUBJECT + i),
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
					Log.i("BTF_ID", json.getInt(BTF_ID + i) + "");
					Log.i("BTF_CHINESENAME",
							json.getString(BTF_CHINESENAME + i) + "");
					Log.i("BTF_DISTRIBUTIONS",
							json.getString(BTF_DISTRIBUTIONS + i));
				}

				addHotSpot();

				db.setVersion(2);
				DATABASE_VERSION = 2;
				// The default version 1 -> 2
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
	
	private void addHotSpot() {
		// TODO dump data
		// Bond, you may use this 4 data to show result, thank you
		this.addHotspotRecord(
				0,
				"地點1",
				"  巴士： 227,244,58K （test1）",
				"Butterfly1",
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test1）");
		this.addHotspotRecord(
				1,
				"地點2",
				"  巴士： 227,244,58K （test2）",
				"Butterfly2",
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test2）");
		this.addHotspotRecord(
				2,
				"地點3",
				"  巴士： 227,244,58K （test3）",
				"Butterfly1",
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test3）");
		this.addHotspotRecord(
				3,
				"地點4",
				"  巴士： 227,244,58K （test4）",
				"Butterfly1",
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  （test4）");
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
		db.close();
		// return user
		return hpData;
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + BUTTERFLY_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + HOTSPOT_TABLE_NAME);
		
		// Create tables again
		onCreate(db);
		
	}

	/**
	 * Storing butterfly details in database
	 * */
	public void addRecord(int _id, String sex, String spec, String chiName,
			String engName, String sciName, String bodyRange, String rangeType,
			String fotColor, String bakColor, String haveWingTail,
			String adultHabit, String babyHabit, String idenDetail,
			String appearTime, String distributions, String image1,
			String image2, String image3) {
		// TODO addRecord
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BTF_ID, _id);
		values.put(BTF_SEX, sex);
		values.put(BTF_SPECIES, spec);
		values.put(BTF_CHINESENAME, chiName);
		values.put(BTF_ENGLISHNAME, engName);
		values.put(BTF_SUBJECT, sciName);
		values.put(BTF_BODYRANGE, bodyRange);
		values.put(BTF_RANGETYPE, rangeType);
		values.put(BTF_FONTCOLOR, fotColor);
		values.put(BTF_BACKCOLOR, bakColor);
		values.put(BTF_HAVEWINGTAIL, haveWingTail);
		values.put(BTF_ADULTHABIT, adultHabit);
		values.put(BTF_BABYHABIT, babyHabit);
		values.put(BTF_DETAIL, idenDetail);
		values.put(BTF_APPEARTIME, appearTime);
		values.put(BTF_DISTRIBUTIONS, distributions);
		values.put(BTF_IMAGE1, image1);
		values.put(BTF_IMAGE2, image2);
		values.put(BTF_IMAGE3, image3);

		// Inserting Row
		db.insert(BUTTERFLY_TABLE_NAME, null, values);
	}

	/**
	 * Storing hotspot details in database
	 * */
	public void addHotspotRecord(int _id, String name, String transportation,
			String butterfly, String environment) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(H_ID, _id);
		values.put(H_NAME, name);
		values.put(H_TRANS, transportation);
		values.put(H_BTF, butterfly);
		values.put(H_ENVIRO, environment);

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
			btfData.put(BTF_SUBJECT, cursor.getString(5));
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
		db.close();
		// return user
		return btfData;
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
		db.close();
		return sNote[0];
	}

	public String[] getAllDistributions() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_DISTRIBUTIONS
				+ " from " + BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getDistributionsByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_DISTRIBUTIONS
				+ " from " + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME
				+ " = '" + chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllAppearTime() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_APPEARTIME + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getAppearTimeByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_APPEARTIME + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllDetail() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_DETAIL + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getDetailByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_DETAIL + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllBabyHabit() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BABYHABIT + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getBabyHabitByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BABYHABIT + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllAdultHabit() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_ADULTHABIT + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getAdultHabitByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_ADULTHABIT + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllHaveWingTail() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_HAVEWINGTAIL
				+ " from " + BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getHaveWingTailByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_HAVEWINGTAIL
				+ " from " + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME
				+ " = '" + chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllBackColor() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BACKCOLOR + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getBackColorByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BACKCOLOR + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllFontColor() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_FONTCOLOR + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getFontColorByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_FONTCOLOR + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllRangeType() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_RANGETYPE + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getRangeTypeByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_RANGETYPE + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllBodyRange() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BODYRANGE + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getBodyRangeByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_BODYRANGE + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllSubject() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SUBJECT + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String[] getSubjectByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SUBJECT + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String[] getAllEnglishName() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_ENGLISHNAME + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getEnglishNameByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_ENGLISHNAME + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
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
		db.close();

		return sNote;
	}

	public String getSpecific1ByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SPECIES + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllChineseName() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String[] getAllSpecies1() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select DISTINCT " + BTF_SPECIES
				+ " from " + BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String[] getAllSex() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SEX + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	public String getSexByChineseName(String chiName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_SEX + " from "
				+ BUTTERFLY_TABLE_NAME + " WHERE " + BTF_CHINESENAME + " = '"
				+ chiName + "'", null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote[0];
	}

	public String[] getAllID() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("" + "select " + BTF_ID + " from "
				+ BUTTERFLY_TABLE_NAME, null);

		// sNote is using for store the retrieve data
		String[] sNote = cursorToArray(cursor);

		cursor.close(); // close the cursor to release resources
		db.close();

		return sNote;
	}

	// public String[] getChineseNameByFotColor(SQLiteDatabase db, String[]
	// string) {
	// Log.i("pppo", "jgkjvkjgvjb");
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME, null);
	//
	//
	//
	//
	// switch (string.length-1){
	//
	// case 0:
	// // Log.i("pppo", "jgkjvkjgvjb");
	// // cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// // " WHERE " + BTF_FONTCOLOR + " LIKE '" + string[0] + "'", null);
	// case 1:
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// " WHERE " + BTF_FONTCOLOR + " LIKE '" + string[0] + "' AND " +
	// BTF_FONTCOLOR + " = '" + string[1] +"'", null);
	// case 2:
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// " WHERE " + BTF_FONTCOLOR + " = '" + string[0] + "' AND " + BTF_FONTCOLOR
	// + " = '" + string[1] + "' AND " + BTF_FONTCOLOR + " = '" + string[2]
	// +"'", null);
	// case 3:
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// " WHERE " + BTF_FONTCOLOR + " = '" + string[0] + "' AND " + BTF_FONTCOLOR
	// + " = '" + string[1]+ "' AND " + BTF_FONTCOLOR + " = '" + string[2]+
	// "' AND " + BTF_FONTCOLOR + " = '" + string[3] +"'", null);
	// case 4:
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// " WHERE " + BTF_FONTCOLOR + " = '" + string[0] + "' AND " + BTF_FONTCOLOR
	// + " = '" + string[1]+ "' AND " + BTF_FONTCOLOR + " = '" + string[2]+
	// "' AND " + BTF_FONTCOLOR + " = '" + string[3]+ "' AND " + BTF_FONTCOLOR +
	// " = '" + string[4] +"'", null);
	// case 5:
	// cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from " +
	// BUTTERFLY_TABLE_NAME +
	// " WHERE " + BTF_FONTCOLOR + " = '" + string[0] + "' AND " + BTF_FONTCOLOR
	// + " = '" + string[1]+ "' AND " + BTF_FONTCOLOR + " = '" + string[2]+
	// "' AND " + BTF_FONTCOLOR + " = '" + string[3]+ "' AND " + BTF_FONTCOLOR +
	// " = '" + string[4]+ "' AND " + BTF_FONTCOLOR + " = '" + string[5]+"'",
	// null);
	//
	// }
	//
	// // Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME +
	// " from "
	// // + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_FONTCOLOR + " = '" + fotcolor
	// // + "'", null);
	//
	// // sNote is using for store the retrieve data
	// String[] sNote = cursorToArray(cursor);
	// Log.i(sNote[0],"323");
	// cursor.close(); // close the cursor to release resources
	//
	// return sNote;
	// }
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
		db.close();

		return sNote;
	}
	// }
	// public String[] getChineseNameBySex(SQLiteDatabase db, String sex) {
	// Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from "
	// + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_SEX + " LIKE '%" + sex + "%'",
	// null);
	//
	// // sNote is using for store the retrieve data
	// String[] sNote = cursorToArray(cursor);
	//
	// cursor.close(); // close the cursor to release resources
	//
	// return sNote;
	// }
	// public String[] getChineseNameByhaveWingTail(SQLiteDatabase db,
	// String haveWingTail) {
	// Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from "
	// + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_HAVEWINGTAIL + " = '"
	// + haveWingTail + "'", null);
	//
	// // sNote is using for store the retrieve data
	// String[] sNote = cursorToArray(cursor);
	//
	// cursor.close(); // close the cursor to release resources
	//
	// return sNote;
	// }
	// public String[] getChineseNameByFotColor(SQLiteDatabase db, String
	// chiName) {
	// Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME + " from "
	// + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_FONTCOLOR + " LIKE '%" + chiName
	// + "%'", null);
	//
	// // Cursor cursor = db.rawQuery("" + "select " + BTF_CHINESENAME +
	// " from "
	// // + BUTTERFLY_TABLE_NAME + " WHERE " + BTF_FONTCOLOR + " LIKE '%" +"褐色"
	// // + "%'" +" AND fotColor LIKE '%"+ "黃色" + "%'", null);
	//
	//
	// // sNote is using for store the retrieve data
	// String[] sNote = cursorToArray(cursor);
	// Log.i(sNote[0],"323");
	// cursor.close(); // close the cursor to release resources
	//
	// return sNote;
	// }
}
