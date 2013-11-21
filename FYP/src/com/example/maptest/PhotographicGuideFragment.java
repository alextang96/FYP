package com.example.maptest;

import static com.example.maptest.DBButterfly.DATABASE_NAME;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PhotographicGuideFragment extends Fragment {

	// SQLiteDatabase Target
	SQLiteDatabase db;

	// Helper Class Name
	DBButterfly helper;

	// User Database Version
	public static int DATABASE_VERSION;

	// User Preference
	private static final String TAG = "SQLITE";
	public static final String PREF = "SQLITE＿PREF";
	public static final String PREF_VERSION = "SQLITE＿Version";

	// Activity
	Activity activity;

	ImageButton[] imageButtons = new ImageButton[11];
	private Button topBarBtn;
	private TextView topBarText;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		restorePrefs();
		helper = new DBButterfly(activity, DATABASE_VERSION);
		Log.e("Current Database Version", DATABASE_VERSION + "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("圖鑑");
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
		View view = inflater.inflate(R.layout.guide1, container, false);

		View.OnClickListener ListenerA = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("PhotographicGuideFragment", "onClick()");
				db = helper.getReadableDatabase();
				String[] returnString = helper
						.getSpecificChineseNameBySpecies(db, v.getTag()
								+ "");
				for (int i = 0; i < returnString.length; i++) {
					Log.e("getSpecific1ChineseNameBySpecies", returnString[i]);
				}

				Bundle butterflyData = new Bundle();
				for (int i = 0; i < returnString.length; i++) {
					butterflyData.putStringArray("butterfly", returnString);
				}

				((MainActivity) getActivity()).addGuide2Fragment(butterflyData);
				((MainActivity) getActivity()).detachGuide1Fragment();
				// Intent intent = new Intent(getActivity(),
				// CustomListViewAndroid.class);
				// Bundle butterflyData = new Bundle();
				// for (int i = 0 ; i < returnString.length ; i++) {
				// butterflyData.putStringArray("butterfly", returnString);
				// }
				//
				// intent.putExtras(butterflyData);
				// startActivity(intent);

				// Toast.makeText(getActivity(), v.getTag() + "",
				// Toast.LENGTH_SHORT).show();
				db.close();
			}

		};

		// initial the imageButtons
		imageButtons[0] = (ImageButton) view.findViewById(R.id.imageButton1);
		imageButtons[1] = (ImageButton) view.findViewById(R.id.imageButton2);
		imageButtons[2] = (ImageButton) view.findViewById(R.id.imageButton3);
		imageButtons[3] = (ImageButton) view.findViewById(R.id.imageButton4);
		imageButtons[4] = (ImageButton) view.findViewById(R.id.imageButton5);
		imageButtons[5] = (ImageButton) view.findViewById(R.id.imageButton6);
		imageButtons[6] = (ImageButton) view.findViewById(R.id.imageButton7);
		imageButtons[7] = (ImageButton) view.findViewById(R.id.imageButton8);
		imageButtons[8] = (ImageButton) view.findViewById(R.id.imageButton9);
		imageButtons[9] = (ImageButton) view.findViewById(R.id.imageButton10);
		imageButtons[10] = (ImageButton) view.findViewById(R.id.imageButton11);

		// set to the listenerA
		for (int i = 0; i < imageButtons.length; i++) {
			imageButtons[i].setOnClickListener(ListenerA);
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		db = helper.getReadableDatabase();
		int intNoOfRecord = Integer.valueOf(helper.getNoOfData(db));
		Log.e("NoOfRecord", intNoOfRecord + "");
		db.close();

	}

	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences settings = activity.getSharedPreferences(PREF, 1);
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt(PREF_VERSION, DATABASE_VERSION);
		PE.commit();
	}

	public int getVersion() {
		return DATABASE_VERSION;
	}

	public void setVersion(int version) {
		this.DATABASE_VERSION = version;
	}

	// Restore preferences
	private void restorePrefs() {
		SharedPreferences settings = activity.getSharedPreferences(PREF, 1);
		int intPrefVersion = settings.getInt(PREF_VERSION, 1);
		DATABASE_VERSION = intPrefVersion;
	}

}