package com.example.maptest;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import edu.mit.mobile.android.imagecache.ImageCache;
import edu.mit.mobile.android.imagecache.ImageLoaderAdapter;
import edu.mit.mobile.android.imagecache.SimpleThumbnailAdapter;

public class CustomListViewAndroid extends Fragment {
	ListView list;
	CustomAdapter adapter;
	public CustomListViewAndroid CustomListView = null;
	public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	private String[] strButterflyData;
	String[] returnString;
	private Button topBarBtn;
	private int onCreateViewToken = 0;
	ImageCache cache;
	private boolean isNullRecord = false;
	private Bundle tempBundle;

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);

		cache = ImageCache.getInstance(this.getActivity());
		tempBundle = getArguments();
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (tempBundle.getInt("fromPage") == 2) {
					((ControlSearchFragment) getParentFragment()).popBack();
				} else {
					((ControlGuideFramgent) getParentFragment()).popBack();
				}
			}

		});

		View view = inflater.inflate(
				R.layout.activity_custom_list_view_android_example, container,
				false);

		// Get Bundle Object
		// Bundle bundleButterflyData = this.getIntent().getExtras();

		// Get Bundle Data
		strButterflyData = getArguments().getStringArray("butterfly");

		CustomListView = this;

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		if (onCreateViewToken == 0) {

			setListData();
			onCreateViewToken++;
		}

		Resources res = getResources();
		list = (ListView) view.findViewById(R.id.list); // List defined in XML (
														// See
		// Below )

		/**************** Create Custom Adapter *********/
		adapter = new CustomAdapter(getActivity(), CustomListViewValuesArr, res);
		list.setAdapter(adapter);

		// Context context, ListAdapter wrapped, ImageCache cache,
		// int[] imageViewIDs, int defaultWidth, int defaultHeight, int unit
		ImageLoaderAdapter cacheAdapter = new ImageLoaderAdapter(
				this.getActivity(), adapter, cache, new int[] { R.id.image },
				100, 100, ImageLoaderAdapter.UNIT_DIP);
		list.setAdapter(cacheAdapter);
		list.setOnItemClickListener(clickListener);

		return view;

	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		for (int i = 0; i < strButterflyData.length; i++) {

			final ListModel sched = new ListModel();

			/******* Firstly take data in model object ******/
			sched.setButterflyName(strButterflyData[i]);
			sched.setImage("type" + (i + 1));

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(sched);
		}
		Log.e("strButterflyData.length", strButterflyData.length + "");
		isNullRecord = strButterflyData[0].equals("No Record!") ? true : false;

	}

	/***************** This function used by adapter ****************/

	public OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (isNullRecord == false) {
				ListModel tempValues = CustomListViewValuesArr.get(arg2);

				Bundle butterflyData = new Bundle();
				butterflyData.putString("butterfly",
						tempValues.getButterflyName());

				if (tempBundle.getInt("fromPage") == 2) {

					((ControlSearchFragment) getParentFragment())
							.replaceGuide2Fragment(butterflyData);
				} else {

					((ControlGuideFramgent) getParentFragment())
							.replaceGuide2Fragment(butterflyData);
				}

			}

		}

	};

}
