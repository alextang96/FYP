package com.example.maptest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

public class mapFragment extends Fragment implements OnMarkerClickListener {

	private SupportMapFragment fragment;
	private GoogleMap map;
	static final LatLng NKUT = new LatLng(22.393152, 113.989332);
	static final LatLng NKUT2 = new LatLng(22.421978, 113.989334);
	private Button topBarBtn, topBarBtn2;
	private TextView topBarText;

	// DB
	private String[] hotPointsName = { "地點1", "地點2", "地點3", "地點4" };

	// DB

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("熱點");
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);

		topBarBtn2 = (Button) getActivity().findViewById(R.id.barbtn);
		topBarBtn2.setVisibility(View.VISIBLE);
		topBarBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				topBarBtn2.setBackgroundResource(R.drawable.topbartitletextbtn);
				((MainActivity) getActivity()).replaceHotPointListFragment();

			}
		});

		return inflater.inflate(R.layout.activity_main, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("mapFragment()", "onActivityCreated");
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment, "MapFragmentTag")
					.commit();
			Log.e("mapFragment()", "onActivityCreated ,replace Fragment");
		}

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub

		Toast.makeText(getActivity(), "這是" + marker.getTitle(),
				Toast.LENGTH_SHORT).show();

		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("mapFragment()", "onResume");
		if (map == null) {
			map = fragment.getMap();
			Log.e("mapFragment()", "getMap");
			final Marker hp0 = map.addMarker(new MarkerOptions().position(NKUT)
					.title("熱點1").snippet("簡介：xxxxxxxxxxxxxxxxxxxxxxxxxxx"));
			final Marker hp1 = map.addMarker(new MarkerOptions()
					.position(NKUT2).title("熱點2")
					.snippet("簡介：xxxxxxxxxxxxxxxxxxxxxxxxxxx／／／／／"));
			map.setMyLocationEnabled(true);
			// Move the camera instantly to hp with a zoom of 13.
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 13));

			map.setOnMarkerClickListener(this);

			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick(Marker marker) {
					Bundle hotPointData = new Bundle();
					String[] markerId = { hp0.getId(), hp1.getId() };

					for (int x = 0; x < markerId.length; x++) {
						if (marker.getId().equals(markerId[x])) {

							hotPointData.putString("hpName", hotPointsName[x]);
						}
					}

					((MainActivity) getActivity())
							.replaceHotPointMapFragment(hotPointData);

				}
			});

		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
			SupportMapFragment fragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map);
			if (fragment != null)
				getFragmentManager().beginTransaction().remove(fragment)
						.commit();
		} catch (IllegalStateException e) {

		}
	}

}