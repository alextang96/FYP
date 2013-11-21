package com.example.maptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class aFragment extends Fragment {

	private Button topBarBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragmentb, container, false);
		Log.i("aFragment()", "onCreateView");
		//Toast.makeText(getActivity(), "I am so sad", Toast.LENGTH_SHORT).show();

//		Button button1 = (Button) view.findViewById(R.id.button1);
//		button1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				((MainActivity) getActivity()).delHotPointFragment();
//				((MainActivity) getActivity()).addGoogleMapFragment();
//			}
//
//		});
//		
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);
		
		
		
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).delHotPointFragment();
				((MainActivity) getActivity()).addGoogleMapFragment();
			}

		});
		return view;

	}
}