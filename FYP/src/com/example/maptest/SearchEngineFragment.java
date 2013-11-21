package com.example.maptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SearchEngineFragment extends Fragment{
	private Button topBarBtn;
	private TextView topBarText;
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
//		topBarText.setText("Search Engine");
//		Log.e("mapFragment()", "onCreateView");
//		return inflater.inflate(R.layout.activity_main, container, false);
//		
//	}
	private int onCreateVieToken =0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.searching, container, false);
		
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
		
		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("搜尋");
		
		if (onCreateVieToken == 0){
			onCreateVieToken++;
			return view;
		
		}else return view;
	}





}

