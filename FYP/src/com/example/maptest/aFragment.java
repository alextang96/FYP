package com.example.maptest;

import static com.example.maptest.DBButterfly.H_ID;
import static com.example.maptest.DBButterfly.H_NAME;
import static com.example.maptest.DBButterfly.H_TRANS;
import static com.example.maptest.DBButterfly.H_BTF;
import static com.example.maptest.DBButterfly.H_ENVIRO;

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
import android.widget.TextView;
import android.widget.Toast;

public class aFragment extends Fragment {

	private Button topBarBtn, topBarBtn2;
	private int fromPage = 0; // come from testlist or map
	private TextView nameTv;
	private TextView environmentTv;
	private TextView transportation1Tv;
	private TextView transportation2Tv;
	private DBButterfly helper;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		helper = new DBButterfly(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragmentb, container, false);
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);
		topBarBtn2 = (Button) getActivity().findViewById(R.id.barbtn);
		topBarBtn2.setVisibility(View.GONE);
		setFromPage();
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).backHotPointMapFragment();
				if (fromPage == 2) {
					((MainActivity) getActivity()).attachHotPointListFragment();
					fromPage = 0;
					topBarBtn.setVisibility(View.GONE);
					topBarBtn2.setVisibility(View.VISIBLE);
				} else
					((MainActivity) getActivity()).attachHotPointMapFragment();
			}

		});

		environmentTv = (TextView) view.findViewById(R.id.hp_environment);
		transportation1Tv = (TextView) view
				.findViewById(R.id.hp_transportation1);
		transportation2Tv = (TextView) view
				.findViewById(R.id.hp_transportation2);
		nameTv = (TextView) view.findViewById(R.id.hp_Name);
		setFromPage();
		getHotPointDetails();
		return view;

	}

	protected void setFromPage() {
		try {
			fromPage = getArguments().getInt("fromPage");
		} catch (Exception e) {

		}
	}

	protected int getFromPage() {
		return fromPage;
	}

	protected void getHotPointDetails() {
		String hpName = "";
		try {
			hpName = getArguments().getString("hpName");
		} catch (Exception e) {

		}
		SQLiteDatabase db = helper.getReadableDatabase();

		HashMap<String, String> hpData = new HashMap<String, String>();
		hpData = helper.getHotPointDetails(db, hpName);
		db.close();

		if (!hpData.isEmpty()) {
			nameTv.setText(hpName);
			transportation1Tv.setText(hpData.get(H_TRANS));
			environmentTv.setText(hpData.get(H_ENVIRO));

		}
	}
}