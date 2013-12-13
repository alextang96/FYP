package com.example.maptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Splash screen view
		setContentView(R.layout.activity_splash_screen);

		final SplashThread sPlashScreen = new SplashThread(SplashScreen.this);
		sPlashScreen.start();

	}

	public class SplashThread extends Thread {
		public SplashThread(Activity activity) {
			super();
			this.activity = activity;
		}

		Activity activity;

		@Override
		public void run() {
			try {
				synchronized (this) {
					// Wait given period of time or exit on touch
					wait(3000);
					Log.e("hello world", "i am jerry");
				}
			} catch (InterruptedException ex) {
			}

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
//					new AlertDialog.Builder(activity)
//							.setTitle(R.string.dwnCtnTitle)
//							.setMessage(R.string.dwnCtnContent)
//							.setPositiveButton(android.R.string.ok,
//									new DialogInterface.OnClickListener() {
//										public void onClick(
//												DialogInterface dialog,
//												int which) {
//											// Run next activity
//											Intent intent = new Intent(
//													SplashScreen.this,
//													MainActivity.class);
//											startActivity(intent);
//											finish();
//										}
//									})
//							.setNegativeButton(android.R.string.cancel,
//									new DialogInterface.OnClickListener() {
//										public void onClick(
//												DialogInterface dialog,
//												int which) {
//											// do nothing
//											finish();
//										}
//									}).show();
					
					Intent intent = new Intent(
							SplashScreen.this,
							MainActivity.class);
					
//					Intent intent = new Intent(
//							SplashScreen.this,
//							searching.class);
					startActivity(intent);
					finish();

				}
			});

		}
	}
}
