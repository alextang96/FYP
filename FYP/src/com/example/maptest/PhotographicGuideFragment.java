package com.example.maptest;

import com.example.maptest.Database.DBButterfly;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PhotographicGuideFragment extends Fragment {

	// Helper Class Name
	DBButterfly helper;

	// Activity
	Activity activity;

	ImageButton[] imageButtons = new ImageButton[11];
	private Button topBarBtn;
	private TextView topBarText;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("圖鑑");
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
		View view = inflater.inflate(R.layout.guide1, container, false);
		helper = new DBButterfly(activity);
		
		// helper.getHotPointDetails("butterfly1");
		helper.checkForUpdate();

		View.OnClickListener ListenerA = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// avoid old database data reuse, thus, need to create again
				helper.closeDB();
				SQLiteDatabase.releaseMemory();
				helper = new DBButterfly(activity);
				Log.i("PhotographicGuideFragment", "onClick()");
				String[] returnString = helper
						.getSpecificChineseNameBySpecies(v.getTag()
								+ "");
				for (int i = 0; i < returnString.length; i++) {
					Log.i("getSpecific1ChineseNameBySpecies", returnString[i]);
				}

				Bundle butterflyData = new Bundle();
				for (int i = 0; i < returnString.length; i++) {
					butterflyData.putStringArray("butterfly", returnString);
				}
				
//				((MainActivity) getActivity()).addGuide2Fragment(butterflyData);
//				((MainActivity) getActivity()).detachGuide1Fragment();
				((ControlGuideFramgent) getParentFragment()).replaceGuide1Fragment(butterflyData);

			}

		};

		// initial the imageButtons
		imageButtons[0] = (ImageButton) view.findViewById(R.id.imageButton1);
		imageButtons[1] = (ImageButton) view.findViewById(R.id.imageButton2);
		imageButtons[2] = (ImageButton) view.findViewById(R.id.imageButton3);
		imageButtons[3] = (ImageButton) view.findViewById(R.id.imageButton4);
		imageButtons[4] = (ImageButton) view.findViewById(R.id.imageButton5);
		imageButtons[5] = (ImageButton) view.findViewById(R.id.imageButton6);
		imageButtons[6] = (ImageButton) view.findViewById(R.id.imageButton7);
		imageButtons[7] = (ImageButton) view.findViewById(R.id.imageButton8);
		imageButtons[8] = (ImageButton) view.findViewById(R.id.imageButton9);
		imageButtons[9] = (ImageButton) view.findViewById(R.id.imageButton10);
		imageButtons[10] = (ImageButton) view.findViewById(R.id.imageButton11);

		// set to the listenerA
		for (int i = 0; i < imageButtons.length; i++) {
			imageButtons[i].setOnClickListener(ListenerA);
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		int intNoOfRecord = Integer.valueOf(helper.getNoOfData());
		Log.i("NoOfRecord", intNoOfRecord + "");
		helper.close();
	}

}