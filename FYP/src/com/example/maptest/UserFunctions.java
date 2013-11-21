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

	private static String checkVersion_tag = "checkVersion";
	private static String getVersion_tag = "getVersion";

	private Context context;

	// constructor
	public UserFunctions(Context context) {
		jsonParser = new JSONParser();
		this.context = context;
	}

	public Context getActivity() {
		return context;
	}

	public JSONObject getVersion() {
		JSONObject json;

		DbNetworkConnect networkConnect = new DbNetworkConnect(getActivity(),
				0, getVersion_tag);
		// 0 is the version notation, but there will not need the current version
		networkConnect.start();

		try {
			networkConnect.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return JSON
		json = networkConnect.getResult();
		return json;
	}

	public JSONObject checkVersion(int currentVersion) {
		final String passVersion = currentVersion + "";
		JSONObject json;

		DbNetworkConnect networkConnect = new DbNetworkConnect(getActivity(),
				currentVersion, checkVersion_tag);
		networkConnect.start();

		try {
			networkConnect.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return JSON
		json = networkConnect.getResult();
		return json;
	}

	public class DbNetworkConnect extends Thread {
		public DbNetworkConnect(Context context, int passVersion, String tag) {
			super();
			this.passVersion = passVersion + "";
			this.tag = tag;
		}

		JSONObject json = null;
		String passVersion;
		String tag;

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (tag.equals(checkVersion_tag)) {
				params.add(new BasicNameValuePair("tag", tag));
				params.add(new BasicNameValuePair("currentVersion", passVersion));
			} else if(tag.equals(getVersion_tag)) {
				params.add(new BasicNameValuePair("tag", tag));
			}
			json = jsonParser.getJSONFromUrl(DBURL, params);
		}

		public JSONObject getResult() {
			// progressDialog.dismiss();
			return json;
		}
	}

}
