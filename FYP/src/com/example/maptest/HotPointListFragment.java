package com.example.maptest;

import java.util.ArrayList;

import edu.mit.mobile.android.imagecache.ImageCache;
import edu.mit.mobile.android.imagecache.ImageLoaderAdapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HotPointListFragment extends Fragment {
	ListView list;
	CustomAdapter adapter;
	public CustomListViewAndroid CustomListView = null;
	public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	private String[] strButterflyData;
	String[] returnString;
	private Button topBarBtn,topBarBtn2;
	private int onCreateViewToken = 0;
	ImageCache cache;
	private String[] hotPointsName= {"地點1","地點2","地點3","地點4"};
	
	
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
		topBarBtn.setVisibility(View.GONE);
		topBarBtn2 = (Button) getActivity().findViewById(R.id.barbtn);
		topBarBtn2.setVisibility(View.VISIBLE);
		
		topBarBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				topBarBtn2.setBackgroundResource(R.drawable.topbartitlemapbtn);
				((MainActivity) getActivity()).backHotPointMapFragment();
				((MainActivity) getActivity()).attachHotPointMapFragment();
			}

		});
		
		
		
		
		
		
		
		cache =  ImageCache.getInstance(this.getActivity());
		
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((ControlGuideFramgent) getParentFragment()).popBack();
				
			}

		});

		View view = inflater.inflate(
				R.layout.hotpoints_list, container,
				false);
		//strButterflyData = getArguments().getStringArray("butterfly");
//		strButterflyData =  {};

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		if (onCreateViewToken == 0) {

			setListData();
			onCreateViewToken++;
		}

		Resources res = getResources();
		list = (ListView) view.findViewById(R.id.hp_list); // List defined in XML (
														// See
		// Below )

		/**************** Create Custom Adapter *********/
		adapter = new CustomAdapter(getActivity(), CustomListViewValuesArr, res);
		list.setAdapter(adapter);
		
		
		//Context context, ListAdapter wrapped, ImageCache cache,
        //int[] imageViewIDs, int defaultWidth, int defaultHeight, int unit
		ImageLoaderAdapter cacheAdapter = 
				new ImageLoaderAdapter(this.getActivity(), 
						adapter, cache, new int[] { R.id.image }, 100,
		                100, ImageLoaderAdapter.UNIT_DIP);
		list.setAdapter(cacheAdapter);
		list.setOnItemClickListener(clickListener);
		
		
		
		
		return view;

	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		for (int i = 0; i < hotPointsName.length; i++) {

			final ListModel sched = new ListModel();

			/******* Firstly take data in model object ******/
			sched.setButterflyName(hotPointsName[i]);
			sched.setImage("type" + (i + 1));

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(sched);
		}

	}

	public OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			// TODO Auto-generated method stub
			ListModel tempValues = CustomListViewValuesArr.get(arg2);
			Bundle tempData = new Bundle();
			tempData.putString("hpName", tempValues.getButterflyName());
			

			tempData.putInt("fromPage", 2);
			((MainActivity) getActivity()).replaceHotPointListFragment2(tempData);
//			((ControlGuideFramgent) getParentFragment()).replaceGuide2Fragment(butterflyData);

		}

	};

}

