package com.example.maptest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SearchEngineFragment extends Fragment {
	private Button topBarBtn;
	private TextView topBarText;
	private int onCreateVieToken = 0;
	private Activity activity;
	private DBButterfly helper;
	private SQLiteDatabase db;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		helper = new DBButterfly(activity);
		db = helper.getReadableDatabase();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.searching, container, false);

		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);

		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("搜尋");

		if (onCreateVieToken == 0) {
			onCreateVieToken++;
			return view;
			
		} else
			return view;
		
	}

}
