package com.example.maptest;

import com.example.maptest.Database.DBButterfly;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchEngineFragment extends Fragment {
	private Button topBarBtn;
	private TextView topBarText;
	private int onCreateVieToken = 0;

	// -------
	DBButterfly DBButterfly;
	// Helper Class Name
	DBButterfly helper;
	Activity activity;

	ImageButton[] imageButtons = new ImageButton[11];

	private Button search;
	private Spinner rangeSpinner, specSpinner, haveWingTailSpinner;
	private CheckBox fot_brown, fot_yellow, fot_white, fot_black, fot_orange,
			fot_blue, bak_brown, bak_yellow, bak_white, bak_black, bak_orange,
			bak_blue, bak_red;
	int type = 0;

	String rangeSelection = ""; // type1
	private String returnRangeSelection = "";

	String specSelection = ""; // type2
	private String returnSpecSelection = "";

	String haveWingTailSelection = ""; // type3
	private String returnHaveWingTailSelection = "";

	String[] returnString;

	private int[] finalSelectionNo = { 0 };
	private String finalSelection = "";

	private int[] fotSelectionNo = { 0, 0, 0, 0, 0, 0, 0 };
	private String fot_a = "褐色";
	private String fot_b = "黃色";
	private String fot_c = "白色";
	private String fot_d = "黑色";
	private String fot_e = "橙色";
	private String fot_f = "藍色";
	private String fotSelection = "";

	private int[] bakSelectionNo = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private String bak_a = "褐色";
	private String bak_b = "黃色";
	private String bak_c = "白色";
	private String bak_d = "黑色";
	private String bak_e = "橙色";
	private String bak_f = "藍色";
	private String bak_g = "紅色";
	private String bakSelection = "";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		helper = new DBButterfly(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.searching, container, false);

		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);

		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("搜尋");

		int intNoOfRecord = Integer.valueOf(helper.getNoOfData());
		String[] strSciName = helper.getAllSciName();
		Log.e("NoOfRecord", intNoOfRecord + "");

		// Spinner
		rangeSpinner = (Spinner) view.findViewById(R.id.sRange);
		specSpinner = (Spinner) view.findViewById(R.id.sSpec);
		haveWingTailSpinner = (Spinner) view.findViewById(R.id.sHaveWingTail);

		// Button
		search = (Button) view.findViewById(R.id.search);

		// check box
		fot_brown = (CheckBox) view.findViewById(R.id.sfot_brown);
		fot_yellow = (CheckBox) view.findViewById(R.id.sfot_yellow);
		fot_white = (CheckBox) view.findViewById(R.id.sfot_white);
		fot_black = (CheckBox) view.findViewById(R.id.sfot_black);
		fot_orange = (CheckBox) view.findViewById(R.id.sfot_orange);
		fot_blue = (CheckBox) view.findViewById(R.id.sfot_blue);
		bak_brown = (CheckBox) view.findViewById(R.id.sbak_brown);
		bak_yellow = (CheckBox) view.findViewById(R.id.sbak_yellow);
		bak_white = (CheckBox) view.findViewById(R.id.sbak_white);
		bak_black = (CheckBox) view.findViewById(R.id.sbak_black);
		bak_orange = (CheckBox) view.findViewById(R.id.sbak_orange);
		bak_blue = (CheckBox) view.findViewById(R.id.sbak_blue);
		bak_red = (CheckBox) view.findViewById(R.id.sbak_red);

		// From strings.xml
		String[] rangeItem = getResources().getStringArray(R.array.rangeString);
		String[] specItem = getResources().getStringArray(R.array.specString);
		String[] haveWingTailItem = getResources().getStringArray(
				R.array.haveWingTailString);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, rangeItem);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rangeSpinner.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, specItem);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		specSpinner.setAdapter(adapter2);

		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, haveWingTailItem);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		haveWingTailSpinner.setAdapter(adapter3);

		fot_brown.setOnCheckedChangeListener(listener);
		fot_yellow.setOnCheckedChangeListener(listener);
		fot_white.setOnCheckedChangeListener(listener);
		fot_black.setOnCheckedChangeListener(listener);
		fot_orange.setOnCheckedChangeListener(listener);
		fot_blue.setOnCheckedChangeListener(listener);
		bak_brown.setOnCheckedChangeListener(listener);
		bak_yellow.setOnCheckedChangeListener(listener);
		bak_white.setOnCheckedChangeListener(listener);
		bak_black.setOnCheckedChangeListener(listener);
		bak_orange.setOnCheckedChangeListener(listener);
		bak_blue.setOnCheckedChangeListener(listener);
		bak_red.setOnCheckedChangeListener(listener);

		rangeSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						String SpinnerChoice = rangeSpinner
								.getItemAtPosition(i).toString();
						// Toast.makeText(getApplication(),
						// SpinnerChoice.toString(), Toast.LENGTH_SHORT).show();
						rangeSelection = SpinnerChoice;
						type = 1;
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		specSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						String SpinnerChoice = specSpinner.getItemAtPosition(i)
								.toString();
						if (SpinnerChoice.equals("弄蝶科")) {
							specSelection = "弄蝶科";
						}
						if (SpinnerChoice.equals("鳳蝶科")) {
							specSelection = "鳳蝶科";
						}
						if (SpinnerChoice.equals("粉蝶科")) {
							specSelection = "粉蝶科";
						}
						if (SpinnerChoice.equals("灰蝶科")) {
							specSelection = "灰蝶科";
						}
						if (SpinnerChoice.equals("蜆蝶科")) {
							specSelection = "蜆蝶科";
						}
						if (SpinnerChoice.equals("眼蝶科")) {
							specSelection = "眼蝶科";
						}
						if (SpinnerChoice.equals("環蝶科")) {
							specSelection = "環蝶科";
						}
						if (SpinnerChoice.equals("蛺蝶科")) {
							specSelection = "蛺蝶科";
						}
						if (SpinnerChoice.equals("斑蝶科")) {
							specSelection = "斑蝶科";
						}
						if (SpinnerChoice.equals("珍蝶科")) {
							specSelection = "珍蝶科";
						}
						if (SpinnerChoice.equals("喙蝶科")) {
							specSelection = "喙蝶科";
						}
						// type=2;
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		haveWingTailSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						String SpinnerChoice = haveWingTailSpinner
								.getItemAtPosition(i).toString();
						haveWingTailSelection = SpinnerChoice;
						type = 3;
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("OnClickListener", "OnClickListener");

				Bundle butterflyData = new Bundle();

				if (finalSelectionNo[0] == 0) {
					if (!rangeSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnRangeSelection = "rangeType LIKE '%"
								+ rangeSelection + "%'";
					} else if (!specSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnSpecSelection = "spec LIKE '%" + specSelection
								+ "%'";
					} else if (!haveWingTailSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnHaveWingTailSelection = "haveWingTail LIKE '%"
								+ haveWingTailSelection + "%'";
					}

				} else {
					if (!rangeSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnRangeSelection = "AND rangeType LIKE '%"
								+ rangeSelection + "%'";
					} else if (!specSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnSpecSelection = "AND spec LIKE '%"
								+ specSelection + "%'";
					} else if (!haveWingTailSelection.equals("/")) {
						finalSelectionNo[0] = 1;
						returnHaveWingTailSelection = "AND haveWingTail LIKE '%"
								+ haveWingTailSelection + "%'";
					}
				}

				// returnString =
				// helper.getChineseNameByRangeType(db,rangeSelection + "");
				//
				// returnString = helper.getChineseNameBySex(db,sexSelection +
				// "");
				//
				// returnString =
				// helper.getChineseNameByhaveWingTail(db,haveWingTailSelection
				// + "");

				// if(type==4){
				//
				// }if(type==5){
				//
				// }

				// last write ga
				// Bundle butterflyData = new Bundle();
				//
				// Log.e(fotSelection[0],"123");
				//
				//
				//
				//
				// // for(int x =0;fotSelection.length < x; x++){
				// returnString = helper
				// .getChineseNameByFotColor(db,fotSelection[0]);
				// // }
				//
				// Log.e(returnString[0],"223");
				//
				// for (int i = 0; i < returnString.length; i++) {
				// //
				// butterflyData.putStringArray("butterfly", returnString);
				//
				// }

				// CHECHBOX1
				// if (finalSelectionNo[0] == 0) {
				// finalSelectionNo[0] = 1;

				if (fotSelectionNo[0] == 1) {

					Log.e("12321321", "12321321312");
					if (finalSelectionNo[0] == 0) {
						// Log.e("12321321", "12321321312");

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;
							fotSelection = "fotColor LIKE '%" + fot_a + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_a + "%'";
						Log.e("AVADVAD", "ASFSAFASFAS");
					}
				}
				if (fotSelectionNo[1] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;

							fotSelection = "fotColor LIKE '%" + fot_b + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_b + "%'";
					}
				}
				if (fotSelectionNo[2] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;

							fotSelection = "fotColor LIKE '%" + fot_c + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_c + "%'";
					}
				}
				if (fotSelectionNo[3] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;

							fotSelection = "fotColor LIKE '%" + fot_d + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_d + "%'";
					}
				}
				if (fotSelectionNo[4] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;

							fotSelection = "fotColor LIKE '%" + fot_e + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_e + "%'";
					}
				}
				if (fotSelectionNo[5] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (fotSelection.equals("")) {
							finalSelectionNo[0] = 1;

							fotSelection = "fotColor LIKE '%" + fot_f + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						fotSelection = fotSelection + " AND fotColor LIKE '%"
								+ fot_f + "%'";
					}
				}

				if (bakSelectionNo[0] == 1) {

					if (finalSelectionNo[0] == 0) {
						// Log.e("12321321", "12321321312");

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;
							bakSelection = "bakColor LIKE '%" + bak_a + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_a + "%'";
					}
				}
				if (bakSelectionNo[1] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_b + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_b + "%'";
					}
				}
				if (bakSelectionNo[2] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_c + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_c + "%'";
					}
				}
				if (bakSelectionNo[3] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_d + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_d + "%'";
					}
				}
				if (bakSelectionNo[4] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_e + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_e + "%'";
					}
				}
				if (bakSelectionNo[5] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_f + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_f + "%'";
					}
				}
				if (bakSelectionNo[6] == 1) {
					if (finalSelectionNo[0] == 0) {

						if (bakSelection.equals("")) {
							finalSelectionNo[0] = 1;

							bakSelection = "bakColor LIKE '%" + bak_g + "%'";
						}
					} else if (finalSelectionNo[0] == 1) {
						bakSelection = bakSelection + " AND bakColor LIKE '%"
								+ bak_g + "%'";
					}
				}

				// returnString =
				// helper.getChineseNameByFotColor(db,fotSelection);

				finalSelection = returnRangeSelection + returnSpecSelection
						+ returnHaveWingTailSelection + fotSelection
						+ bakSelection;

				returnString = helper.getChineseNameByRangeType(finalSelection);
				butterflyData.putStringArray("butterfly", returnString);
				helper.close();
				
				((ControlSearchFragment) getParentFragment())
						.replaceGuide1Fragment(butterflyData);

			}

		});

		if (onCreateVieToken == 0) {
			onCreateVieToken++;
			return view;

		} else
			return view;
	}

	private CheckBox.OnCheckedChangeListener listener = new CheckBox.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (fot_brown.isChecked() == true) {
				fotSelectionNo[0] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_brown.isChecked() == false) {
				fotSelectionNo[0] = 0;
			}
			if (fot_yellow.isChecked() == true) {
				fotSelectionNo[1] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_yellow.isChecked() == false) {
				fotSelectionNo[1] = 0;
			}
			if (fot_white.isChecked() == true) {
				fotSelectionNo[2] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_white.isChecked() == false) {
				fotSelectionNo[2] = 0;
			}
			if (fot_black.isChecked() == true) {
				fotSelectionNo[3] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_black.isChecked() == false) {
				fotSelectionNo[3] = 0;
			}
			if (fot_orange.isChecked() == true) {
				fotSelectionNo[4] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_orange.isChecked() == false) {
				fotSelectionNo[4] = 0;
			}
			if (fot_blue.isChecked() == true) {
				fotSelectionNo[5] = 1;
				// Log.e(fotSelectionNo[0], "12312312");
			}
			if (fot_blue.isChecked() == false) {
				fotSelectionNo[5] = 0;
			}

			if (bak_brown.isChecked() == true) {
				bakSelectionNo[0] = 1;
			}
			if (bak_brown.isChecked() == false) {
				bakSelectionNo[0] = 0;
			}
			if (bak_yellow.isChecked() == true) {
				bakSelectionNo[1] = 1;
			}
			if (bak_yellow.isChecked() == false) {
				bakSelectionNo[1] = 0;
			}
			if (bak_white.isChecked() == true) {
				bakSelectionNo[2] = 1;
			}
			if (bak_white.isChecked() == false) {
				bakSelectionNo[2] = 0;
			}
			if (bak_black.isChecked() == true) {
				bakSelectionNo[3] = 1;
			}
			if (bak_black.isChecked() == false) {
				bakSelectionNo[3] = 0;
			}
			if (bak_orange.isChecked() == true) {
				bakSelectionNo[4] = 1;
			}
			if (bak_orange.isChecked() == false) {
				bakSelectionNo[4] = 0;
			}
			if (bak_blue.isChecked() == true) {
				bakSelectionNo[5] = 1;
			}
			if (bak_blue.isChecked() == false) {
				bakSelectionNo[5] = 0;
			}
			if (bak_red.isChecked() == true) {
				bakSelectionNo[6] = 1;
			}
			if (bak_red.isChecked() == false) {
				bakSelectionNo[6] = 0;
			}
		}
	};

}
