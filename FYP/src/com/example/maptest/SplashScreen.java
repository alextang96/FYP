package com.example.maptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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
        public void run(){
            try {
                synchronized(this){
                    // Wait given period of time or exit on touch
                    wait(5000);
                }
            }
            catch(InterruptedException ex){                    
            }
            
            
            activity.runOnUiThread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(activity)
			          .setTitle("Download Content")
			          .setMessage("Will you download the application content?")
			          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			              public void onClick(DialogInterface dialog, int which) { 
			              	// Run next activity
			                  Intent intent = new Intent(SplashScreen.this, MainActivity.class);
			                  startActivity(intent);
			                  finish();
			              }
			           })
			          .setNegativeButton("No", new DialogInterface.OnClickListener() {
			              public void onClick(DialogInterface dialog, int which) { 
			                  // do nothing
			            	  finish();
			              }
			           })
			           .show();
			      
				
				}});
            
            
        }
    }
 }


