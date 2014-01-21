package com.example.maptest;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	private static final int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "ButterflyPref";

	// All Shared Preferences Keys
	public static final String IS_CHECKED_UPDATED = "isCheckedUpdated";

	// Storing the current SQLite DBVersion (Can be related to remote database
	// version)
	public static final String DATABASE_VERSION = "DBVersion";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	}

	/**
	 * initial the application
	 * */
	public void initialApplication(boolean isCheckedUpdated, int intDBVersion) {
		editor = pref.edit();

		// Storing CheckedUpdated value as FALSE
		editor.putBoolean(IS_CHECKED_UPDATED, isCheckedUpdated);

		// Storing database version in pref
		editor.putInt(DATABASE_VERSION, intDBVersion);

		// commit changes
		editor.commit();
	}

	/**
	 * Update the Database Version
	 **/
	public void setDBVersion(int intDBVersion) {
		editor = pref.edit();

		// Storing database version in pref
		editor.putInt(DATABASE_VERSION, intDBVersion);

		// commit changes
		editor.commit();
	}

	/**
	 * Get stored session data
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap getUserDetails() {
		HashMap user = new HashMap();
		// Updated Database Checking
		user.put(IS_CHECKED_UPDATED, pref.getBoolean(IS_CHECKED_UPDATED, false));

		// Database Version
		user.put(DATABASE_VERSION, pref.getInt(DATABASE_VERSION, 1));

		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	// public void logoutUser(){
	// // Clearing all data from Shared Preferences
	// editor.clear();
	// editor.commit();
	//
	// // After logout redirect user to Loing Activity
	// Intent i = new Intent(_context, LoginActivity.class);
	// // Closing all the Activities
	// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//
	// // Add new Flag to start new Activity
	// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//
	// // Staring Login Activity
	// _context.startActivity(i);
	// }

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	// public boolean isLoggedIn(){
	// return pref.getBoolean(IS_LOGIN, false);
	// }
}