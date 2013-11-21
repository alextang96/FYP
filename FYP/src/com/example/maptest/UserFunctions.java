package com.example.maptest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserFunctions {

	private JSONParser jsonParser;

	private static String DBURL = "http://fyp.tswsw.com";

	private static String version_tag = "checkVersion";

	private Context context;

	// constructor
	public UserFunctions(Context context) {
		jsonParser = new JSONParser();
		this.context = context;
	}

	public Context getActivity() {
		return context;
	}

	public JSONObject checkVersion(int currentVersion) {
		// Building Parameters
		final String passVersion = currentVersion + "";
		JSONObject json;

		DbNetworkConnect networkConnect = new DbNetworkConnect(getActivity(), currentVersion);
		networkConnect.start();

		try {
			networkConnect.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// return json
		json = networkConnect.getResult();
		return json;
	}

	public class DbNetworkConnect extends Thread {
		public DbNetworkConnect(Context context, int passVersion) {
			super();
			this.passVersion = passVersion + "";
		}
		JSONObject json = null;
		String passVersion;
		
		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("tag", version_tag));
			params.add(new BasicNameValuePair("currentVersion", passVersion));
			json = jsonParser.getJSONFromUrl(DBURL, params);
		}

		public JSONObject getResult() {
			// progressDialog.dismiss();
			return json;
		}
	}

}
