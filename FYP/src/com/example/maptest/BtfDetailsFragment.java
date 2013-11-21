package com.example.maptest;

import static com.example.maptest.DBButterfly.DATABASE_NAME;
import static com.example.maptest.DBButterfly.BTF_SPECIES;
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
import static com.example.maptest.DBButterfly.BTF_SUBJECT;

import java.util.ArrayList;
import java.util.HashMap;

import edu.mit.mobile.android.imagecache.ImageCache;
import edu.mit.mobile.android.imagecache.ImageLoaderAdapter;
import edu.mit.mobile.android.imagecache.SimpleThumbnailAdapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

public class BtfDetailsFragment extends Fragment {

	private ImageCache mCache;

	private final TestData mTestData = new TestData();

	final int[][] tvSET = { { 0, R.id.sex }, { 1, R.id.spec },
			{ 2, R.id.engName }, { 3, R.id.bodyRange }, { 4, R.id.rangeType },
			{ 5, R.id.fotColor }, { 6, R.id.backColor },
			{ 7, R.id.haveWingTail }, { 8, R.id.adultHabit },
			{ 9, R.id.badyHabit }, { 10, R.id.idenDetail },
			{ 11, R.id.Distributions }, { 12, R.id.appearTime },
			{ 13, R.id.chiName }, { 14, R.id.sciName } };

	final int tvSEX = 0, tvSPEC = 1, tvENGNAME = 2, tvBODYRANGE = 3,
			tvRangeType = 4, tvFONTCOLOR = 5, tvBACKCOLOR = 6,
			tvHAVEWINGTAIL = 7, tvADULTHABIT = 8, tvBABYHABIT = 9,
			tvIDENDETAIL = 10, tvDISTRIBUTIONS = 11, tvAPPEARTIME = 12,
			tvCHINAME = 13, tvSCINAME = 14;

	final int INDEX = 0, RID = 1;
	TextView[] tv = new TextView[15];

	private String strButterflyName;
	private DBButterfly helper;
	private Button topBarBtn;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		helper = new DBButterfly(activity);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		((MainActivity) ((MainActivity) getActivity()))
				.setTopBarButtonOnResume("Destory_BtfDetailsFragment");
		// ((MainActivity) ((MainActivity) getActivity())).addGoogleMapFragment();

	}
	
	
	private void initData() {
        mTestData
                .addItem(
                        "Bond Image",
                        "http://fyp.tswsw.com/www/btfImages/0_1.jpg");
        mTestData
                .addItem(
                        "green home",
                        "http://fyp.tswsw.com/www/btfImages/0_2.jpg");
        mTestData
                .addItem(
                        "green home 2",
                        "http://fyp.tswsw.com/www/btfImages/0_3.jpg");
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarBtn = (Button) ((MainActivity) ((MainActivity) getActivity())).findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) ((MainActivity) getActivity())).delGuide3Fragment();

				// ((MainActivity) ((MainActivity) getActivity())).addGoogleMapFragment();
			}

		});

		View view = inflater.inflate(R.layout.activity_btf_details, container,
				false);
		
		final Gallery	gallery = (Gallery) view.findViewById(R.id.gallery);
		
		mCache = ImageCache.getInstance(((MainActivity) getActivity()));
		mCache.setCacheMaxSize(1 * 1024 /* mega */* 1024 /* kilo */);
		
		initData();
		
		Log.e("((MainActivity) getActivity())", ((MainActivity) getActivity()).toString());
		if (true)
		{
			ImageLoaderAdapter adapter = TestData.generateAdapter((MainActivity) getActivity(), mTestData, R.layout.small_thumbnail_item, mCache, 160, 100);
			Log.e("Count", adapter.getCount() + "");
			gallery.setAdapter(adapter);
		}
		
		
		
		for (int i = 0; i < tv.length; i++) {
			tv[i] = (TextView) view.findViewById(tvSET[i][RID]);
		}

		SQLiteDatabase db = helper.getReadableDatabase();

		// Get Bundle Object
		// Bundle bundleButterflyData = this.getArguments().getExtras();

		// Get Bundle Data
		strButterflyName = getArguments().getString("butterfly");

		HashMap<String, String> btfData = new HashMap<String, String>();
		btfData = helper.getButterflyDetails(db, strButterflyName);

		if (!btfData.isEmpty()) {
			tv[tvSET[tvENGNAME][INDEX]].setText(btfData.get(BTF_ENGLISHNAME));
			tv[tvSET[tvSEX][INDEX]].setText(btfData.get(BTF_SEX));
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

		db.close();

		return view;
	}

	private Gallery findViewById(int gallery2) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.btf_details, menu);
	// return true;
	// }

}
