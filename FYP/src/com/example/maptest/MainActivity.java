package com.example.maptest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost tabHost;
	private android.support.v4.app.FragmentManager fragment = getSupportFragmentManager();
	// private TextView topBarText;
	private LayoutInflater layoutInflater;

	private Class fragmentArray[] = { PhotographicGuideFragment.class,
			SearchEngineFragment.class, emailFragment.class, mapFragment.class };

	private int mImageViewArray[] = { R.drawable.tab1, R.drawable.tab2,
			R.drawable.tab3, R.drawable.tab4 };

	private String mTextviewArray[] = { "圖鑑", "搜尋", "報告", "熱點" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// android.support.v4.app.FragmentTransaction fragmentTransaction =
		// fragment
		// .beginTransaction();
		// Fragment backgroundFragment = new BackgroundFragment();
		//
		// fragmentTransaction.add(R.id.realtabcontent, backgroundFragment,
		// "backgroundFragment");
		// fragmentTransaction.commit();

		// topBarText = (TextView) findViewById(R.id.bartext);

		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = tabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			tabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}

		tabHost.getTabWidget()
				.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		tabHost.getTabWidget().setDividerDrawable(R.drawable.tarbarline);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabID) {
				delGuide2Fragment();
				delGuide3Fragment();
				delHotPointFragment();
				if (!tabID.equals("Google Map"))
					delGoogleMapFragment();
				Button topBarBtn = (Button) findViewById(R.id.backbarbtn);
				topBarBtn.setVisibility(View.GONE);
			}
			// if (!tabID.equals("Google Map"))
			// delGoogleMapFragment();

		});
	}

	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		return view;
	}

	public void changeFragment(mapFragment mapFragment, int pageNum) {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment fragmenta = new aFragment();
		fragmentTransaction.detach(mapFragment);
		fragmentTransaction.add(R.id.realtabcontent, fragmenta,
				"HotPointFragmentTag");
		fragmentTransaction.commit();

	}

	protected void delHotPointFragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment
				.findFragmentByTag("HotPointFragmentTag");
		if (previousInstance != null)
			fragmentTransaction.remove(previousInstance);
		fragmentTransaction.commit();
	}

	protected void addGoogleMapFragment() {
		Log.i("addGoogleMapFragment()", "addGoogleMapFragment");
		com.example.maptest.mapFragment mapFragment2 = new mapFragment();
		FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fm
				.beginTransaction();
		if (mapFragment2 != null)
			fragmentTransaction.replace(R.id.realtabcontent, mapFragment2,
					"MapFragmentTag");
		fragmentTransaction.commit();
	}

	protected void delGoogleMapFragment() {
		Log.i("delGoogleMapFragment()", "delGoogleMapFragment");
		FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fm
				.beginTransaction();
		Fragment previousInstance = fm.findFragmentByTag("MapFragmentTag");
		if (previousInstance != null)
			fragmentTransaction.remove(previousInstance);
		fragmentTransaction.commit();
	}

	// protected void addGuide1Fragment(Bundle butterflyData) {
	// Log.e("MainActivity","addGuide2");
	// // Bundle bundle=new Bundle();
	// // bundle.putString("edttext", "From Activity");
	//
	//
	//
	// android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
	// .beginTransaction();
	// PhotographicGuideFragment photographicGuideFragment = new
	// PhotographicGuideFragment();
	// //fragmentTransaction.detach(mapFragment);
	// photographicGuideFragment.setArguments(butterflyData);
	// fragmentTransaction.add(R.id.realtabcontent, photographicGuideFragment,
	// "PhotographicGuideFragment");
	// fragmentTransaction.commit();
	//
	// }
	//
	// protected void delGuide1Fragment() {
	// android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
	// .beginTransaction();
	// Fragment previousInstance = fragment
	// .findFragmentByTag("PhotographicGuideFragment");
	// if (previousInstance != null)
	// fragmentTransaction.remove(previousInstance);
	// fragmentTransaction.commit();
	// }

	protected void detachGuide1Fragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment.findFragmentByTag("圖鑑");

		if (previousInstance != null) {
			Log.e("detachGuide1Fragment()", "previousInstance != null");
			fragmentTransaction.detach(previousInstance);
		} else {
			Log.e("detachGuide1Fragment()", "previousInstance == null");
		}
		fragmentTransaction.commit();
	}

	protected void attachGuide1Fragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment.findFragmentByTag("圖鑑");
		if (previousInstance != null)
			fragmentTransaction.attach(previousInstance);
		fragmentTransaction.commit();
	}

	protected void addGuide2Fragment(Bundle butterflyData) {
		Log.e("MainActivity", "addGuide2");
		// Bundle bundle=new Bundle();
		// bundle.putString("edttext", "From Activity");

		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		CustomListViewAndroid customListViewAndroid = new CustomListViewAndroid();
		// fragmentTransaction.detach(mapFragment);
		customListViewAndroid.setArguments(butterflyData);
		fragmentTransaction.add(R.id.realtabcontent, customListViewAndroid,
				"CustomListViewAndroid");
		fragmentTransaction.commit();

	}

	protected void delGuide2Fragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment
				.findFragmentByTag("CustomListViewAndroid");
		if (previousInstance != null)
			fragmentTransaction.remove(previousInstance);
		fragmentTransaction.commit();
	}

	protected void detachGuide2Fragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment
				.findFragmentByTag("CustomListViewAndroid");
		if (previousInstance != null)
			fragmentTransaction.detach(previousInstance);
		fragmentTransaction.commit();
	}

	protected void addGuide3Fragment(Bundle butterflyData) {
		Log.e("MainActivity", "addGuide3");
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		BtfDetailsFragment btfDetailsFragment = new BtfDetailsFragment();
		// fragmentTransaction.detach(mapFragment);
		btfDetailsFragment.setArguments(butterflyData);
		fragmentTransaction.add(R.id.realtabcontent, btfDetailsFragment,
				"BtfDetailsFragment");
		fragmentTransaction.commit();

	}

	protected void delGuide3Fragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment
				.findFragmentByTag("BtfDetailsFragment");
		Fragment previousInstance2 = fragment
				.findFragmentByTag("CustomListViewAndroid");
		if (previousInstance != null)
			fragmentTransaction.remove(previousInstance);
		if (previousInstance2 != null)
			fragmentTransaction.attach(previousInstance2);
		fragmentTransaction.commit();

	}

	protected void setTopBarButtonOnResume(String pageName) {
		Button topBarBtn = (Button) findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.VISIBLE);
		topBarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delGuide2Fragment();

			}

		});
	}

	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setTitle("你確定要離開?").setCancelable(false)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MainActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});// .setIcon(R.drawable.icon_sosad);
		AlertDialog alert = builder.create();
		alert.show();
	}

}
