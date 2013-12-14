package com.example.maptest;

import static com.example.maptest.DBButterfly.BTF_ADULTHABIT;
import static com.example.maptest.DBButterfly.BTF_APPEARTIME;
import static com.example.maptest.DBButterfly.BTF_BABYHABIT;
import static com.example.maptest.DBButterfly.BTF_BACKCOLOR;
import static com.example.maptest.DBButterfly.BTF_BODYRANGE;
import static com.example.maptest.DBButterfly.BTF_CHINESENAME;
import static com.example.maptest.DBButterfly.BTF_DETAIL;
import static com.example.maptest.DBButterfly.BTF_DISTRIBUTIONS;
import static com.example.maptest.DBButterfly.BTF_ENGLISHNAME;
import static com.example.maptest.DBButterfly.BTF_FONTCOLOR;
import static com.example.maptest.DBButterfly.BTF_HAVEWINGTAIL;
import static com.example.maptest.DBButterfly.BTF_RANGETYPE;
import static com.example.maptest.DBButterfly.BTF_SEX;
import static com.example.maptest.DBButterfly.BTF_SPECIES;
import static com.example.maptest.DBButterfly.BTF_SUBJECT;

import java.util.HashMap;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;
import edu.mit.mobile.android.imagecache.ImageCache;
import edu.mit.mobile.android.imagecache.ImageLoaderAdapter;

public class BtfDetailsFragment extends Fragment {

	private ImageCache mCache;

	private final TestData mTestData = new TestData();

	private SQLiteDatabase db;
	
	final int[][] tvSET = { { 0, R.id.spec },
			{ 1, R.id.engName }, { 2, R.id.bodyRange }, { 3, R.id.rangeType },
			{ 4, R.id.fotColor }, { 5, R.id.backColor },
			{ 6, R.id.haveWingTail }, { 7, R.id.adultHabit },
			{ 8, R.id.badyHabit }, { 9, R.id.idenDetail },
			{ 10, R.id.Distributions }, { 11, R.id.appearTime },
			{ 12, R.id.chiName }, { 13, R.id.sciName } };

	final int tvSPEC = 0, tvENGNAME = 1, tvBODYRANGE = 2,
			tvRangeType = 3, tvFONTCOLOR = 4, tvBACKCOLOR = 5,
			tvHAVEWINGTAIL = 6, tvADULTHABIT = 7, tvBABYHABIT = 8,
			tvIDENDETAIL = 9, tvDISTRIBUTIONS = 10, tvAPPEARTIME = 11,
			tvCHINAME = 12, tvSCINAME = 13;

	final int INDEX = 0, RID = 1;
	TextView[] tv = new TextView[14];

	private String strButterflyName;
	private DBButterfly helper;
	private Button topBarBtn;
	private Bundle tempBundle;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		helper = new DBButterfly(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		db.close();
		helper.close();
	}

	private void initData() {
		mTestData.addItem("Bond Image",
				"http://fyp.tswsw.com/www/btfImages/0_1.jpg");
		mTestData.addItem("green home",
				"http://fyp.tswsw.com/www/btfImages/0_2.jpg");
		mTestData.addItem("green home 2",
				"http://fyp.tswsw.com/www/btfImages/0_3.jpg");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarBtn = (Button) ((MainActivity) ((MainActivity) getActivity()))
				.findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tempBundle = getArguments();
				if (tempBundle.getInt("fromPage") == 2) {
					((ControlSearchFragment) getParentFragment()).popBack();
				} else {
					((ControlGuideFramgent) getParentFragment()).popBack();
				}
			}

		});

		View view = inflater.inflate(R.layout.activity_btf_details, container,
				false);

		final Gallery gallery = (Gallery) view.findViewById(R.id.gallery);

		mCache = ImageCache.getInstance(((MainActivity) getActivity()));
		mCache.setCacheMaxSize(1 * 1024 /* mega */* 1024 /* kilo */);

		initData();

		Log.e("((MainActivity) getActivity())",
				((MainActivity) getActivity()).toString());
		if (true) {
			ImageLoaderAdapter adapter = TestData.generateAdapter(
					(MainActivity) getActivity(), mTestData,
					R.layout.small_thumbnail_item, mCache, 160, 100);
			Log.e("Count", adapter.getCount() + "");
			gallery.setAdapter(adapter);
		}

		for (int i = 0; i < tv.length; i++) {
			tv[i] = (TextView) view.findViewById(tvSET[i][RID]);
		}

		db = helper.getReadableDatabase();
		// Get Bundle Data
		strButterflyName = getArguments().getString("butterfly");

		HashMap<String, String> btfData = new HashMap<String, String>();
		btfData = helper.getButterflyDetails(db, strButterflyName);
		db.close();
		if (!btfData.isEmpty()) {
			tv[tvSET[tvENGNAME][INDEX]].setText(btfData.get(BTF_ENGLISHNAME));
			tv[tvSET[tvSPEC][INDEX]].setText(btfData.get(BTF_SPECIES));
			tv[tvSET[tvBODYRANGE][INDEX]].setText(btfData.get(BTF_BODYRANGE));
			tv[tvSET[tvRangeType][INDEX]].setText(btfData.get(BTF_RANGETYPE));
			tv[tvSET[tvFONTCOLOR][INDEX]].setText(btfData.get(BTF_FONTCOLOR));
			tv[tvSET[tvBACKCOLOR][INDEX]].setText(btfData.get(BTF_BACKCOLOR));
			tv[tvSET[tvHAVEWINGTAIL][INDEX]].setText(btfData
					.get(BTF_HAVEWINGTAIL));
			tv[tvSET[tvADULTHABIT][INDEX]].setText(btfData.get(BTF_ADULTHABIT));
			tv[tvSET[tvBABYHABIT][INDEX]].setText(btfData.get(BTF_BABYHABIT));
			tv[tvSET[tvIDENDETAIL][INDEX]].setText(btfData.get(BTF_DETAIL));
			tv[tvSET[tvAPPEARTIME][INDEX]].setText(btfData.get(BTF_APPEARTIME));
			tv[tvSET[tvDISTRIBUTIONS][INDEX]].setText(btfData
					.get(BTF_DISTRIBUTIONS));
			tv[tvSET[tvCHINAME][INDEX]].setText(btfData.get(BTF_CHINESENAME));
			tv[tvSET[tvSCINAME][INDEX]].setText(btfData.get(BTF_SUBJECT));
		}
		return view;
	}
}
