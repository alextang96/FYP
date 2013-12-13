package com.example.maptest;

import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ControlSearchFragment extends Fragment {

	Bundle tempBundle;
	private int onCreateToken = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.guide_nested_fragment, container,
				false);

		android.support.v4.app.FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		Fragment searchEngineFragment = new SearchEngineFragment();
		
		
		if(onCreateToken==0){
		fragmentTransaction.add(R.id.guidecontent, searchEngineFragment,
				"searchEngineFragment");
		onCreateToken++;
		}else{
			fragmentTransaction.replace(R.id.guidecontent, searchEngineFragment,
					"searchEngineFragment");
		}
		fragmentTransaction.commit();
		return view;
	}


	protected void replaceGuide1Fragment(Bundle butterflyData) {
		android.support.v4.app.FragmentTransaction parentFragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		Fragment customListViewAndroid = new CustomListViewAndroid();
		tempBundle =butterflyData;
		tempBundle.putInt("fromPage", 2);
		customListViewAndroid.setArguments(butterflyData);
		parentFragmentTransaction.replace(R.id.guidecontent,
				customListViewAndroid, "CustomListViewAndroid");
		parentFragmentTransaction.commit();
	}

	protected void replaceGuide2Fragment(Bundle butterflyData) {
		android.support.v4.app.FragmentTransaction parentFragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		Fragment btfDetailsFragment = new BtfDetailsFragment();
//		tempBundle =butterflyData;
		butterflyData.putInt("fromPage", 2);
		btfDetailsFragment.setArguments(butterflyData);
		
		parentFragmentTransaction.replace(R.id.guidecontent,
				btfDetailsFragment, "BtfDetailsFragment");
		Log.e("replaceGuide2Fragment:"
				+ getChildFragmentManager().getBackStackEntryCount() + "",
				"bond Test");
		parentFragmentTransaction.commit();
	}
    protected void backGuideFragment() {	
		
		try{
	
			Fragment searchEngineFragment = new SearchEngineFragment();		
			android.support.v4.app.FragmentTransaction parentFragmentTransaction = getChildFragmentManager()
			.beginTransaction();
	parentFragmentTransaction.replace(R.id.guidecontent,
			searchEngineFragment, "searchEngineFragment");
	parentFragmentTransaction.commit();
			
			
		}
		catch(Exception e){
			
		}
		
		
	}
	
	
    
    protected void backGuide3Fragment() {	
		if(tempBundle == null){
			Log.e("tempBundle == null","bond Test");
		}
		try{
		    Fragment customListViewAndroid = new CustomListViewAndroid();
		    customListViewAndroid.setArguments(tempBundle);
			android.support.v4.app.FragmentTransaction parentFragmentTransaction = getChildFragmentManager()
			.beginTransaction();
	parentFragmentTransaction.replace(R.id.guidecontent,
			customListViewAndroid, "CustomListViewAndroid");
	parentFragmentTransaction.commit();
			
			
		}
		catch(Exception e){
			Log.e("backGuide3Fragment error","bond Test");
		}
		
		
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.e("onDestroyView()", "onDestroy");

	}

	protected boolean popFragment() {

		boolean isPop = false;
		if (getChildFragmentManager().getBackStackEntryCount() > 0) {
			isPop = true;

			getChildFragmentManager().popBackStack();
		}
		return isPop;
	}

	protected boolean popBack() {
		boolean isPop = false;
		Fragment searchEngineFragment = getChildFragmentManager()
				.findFragmentByTag("searchEngineFragment");

		Fragment customListViewAndroid = getChildFragmentManager()
				.findFragmentByTag("CustomListViewAndroid");
		Fragment BtfDetailsFragment = getChildFragmentManager()
				.findFragmentByTag("BtfDetailsFragment");

		
		if (BtfDetailsFragment != null) {
			 if (BtfDetailsFragment.isVisible()) {
				 isPop = true;
				 backGuide3Fragment();
				 Log.e("back BtfDetailsFragment","bond Test");
			 }Log.e("back BtfDetailsFragment with no cheaking","bond Test");
		}else
		
		
		if (customListViewAndroid != null) {
			 if (customListViewAndroid.isVisible()) {
				 isPop = true;
				 backGuideFragment();
				 Log.e("back customListViewAndroid","bond Test");
				 
			 }Log.e("back customListViewAndroid with no cheaking","bond Test");
		}
		
		return isPop;
		
		
	}

}
