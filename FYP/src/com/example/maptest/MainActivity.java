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
	private Class fragmentArray[] = { ControlGuideFramgent.class,
			ControlSearchFragment.class, emailFragment.class, mapFragment.class };

	private int mImageViewArray[] = { R.drawable.tab1, R.drawable.tab2,
			R.drawable.tab3, R.drawable.tab4 };

	private String mTextviewArray[] = { "圖鑑", "搜尋", "報告", "熱點" };
	
	private Button topBarBtn,topBarBtn2;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setImageGone();

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
				setImageGone();
				Fragment controlHotPointFragment = getSupportFragmentManager()
						.findFragmentByTag("熱點");
				String currentTabTag = tabHost.getCurrentTabTag();
				if (controlHotPointFragment != null
						&& currentTabTag.equals(mTextviewArray[3])) {

					Fragment previousInstance = fragment
							.findFragmentByTag("HotPointListFragmentTag");
					if (previousInstance != null) {

						backHotPointMapFragment();

					}

				}

			}
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


//	protected void delHotPointFragment() {
//		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
//				.beginTransaction();
//		Fragment previousInstance = fragment
//				.findFragmentByTag("HotPointFragmentTag");
//		if (previousInstance != null)
//			fragmentTransaction.remove(previousInstance);
//		fragmentTransaction.commit();
//	}

//	protected void addGoogleMapFragment() {
//		Log.i("addGoogleMapFragment()", "addGoogleMapFragment");
//		com.example.maptest.mapFragment mapFragment2 = new mapFragment();
//		FragmentManager fm = getSupportFragmentManager();
//		android.support.v4.app.FragmentTransaction fragmentTransaction = fm
//				.beginTransaction();
//		if (mapFragment2 != null)
//			fragmentTransaction.replace(R.id.realtabcontent, mapFragment2,
//					"MapFragmentTag");
//		fragmentTransaction.commit();
//	}

//	protected void delGoogleMapFragment() {
//		Log.i("delGoogleMapFragment()", "delGoogleMapFragment");
//		FragmentManager fm = getSupportFragmentManager();
//		android.support.v4.app.FragmentTransaction fragmentTransaction = fm
//				.beginTransaction();
//		Fragment previousInstance = fm.findFragmentByTag("MapFragmentTag");
//		if (previousInstance != null)
//			fragmentTransaction.remove(previousInstance);
//		fragmentTransaction.commit();
//	}


	
	
	//
	protected void replaceHotPointMapFragment(Bundle tempBundle) {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment fragmenta = new aFragment();
		Fragment hotPointMapFragment = getSupportFragmentManager()
				.findFragmentByTag(mTextviewArray[3]);

		fragmentTransaction.detach(hotPointMapFragment);
		fragmenta.setArguments(tempBundle);
		
		fragmentTransaction.add(R.id.realtabcontent, fragmenta,
				"HotPointFragmentTag");
		fragmentTransaction.commit();

	}
	
	
	public void onBackPressed() {

		Fragment guideFragment = getSupportFragmentManager().findFragmentByTag(
				"圖鑑");
		Fragment controlSearchFragment = getSupportFragmentManager()
				.findFragmentByTag("搜尋");
		Fragment controlHotPointFragment = getSupportFragmentManager()
				.findFragmentByTag("熱點");

		String currentTabTag = tabHost.getCurrentTabTag();
		boolean isPopFragment = false;
		// If the fragment exists and has some back-stack entry
		if (guideFragment != null && currentTabTag.equals(mTextviewArray[0])) {

			isPopFragment = ((ControlGuideFramgent) getSupportFragmentManager()
					.findFragmentByTag(mTextviewArray[0])).popBack();
			if (isPopFragment == false) {
				leftAlert();
			}
//		}	
		}else if(controlSearchFragment != null && currentTabTag.equals(mTextviewArray[1])){
			
			isPopFragment = ((ControlSearchFragment) getSupportFragmentManager()
					.findFragmentByTag(mTextviewArray[1])).popBack();
			if (isPopFragment == false) {
				leftAlert();
			}
		}
		else if(controlHotPointFragment != null && currentTabTag.equals(mTextviewArray[3])){
			
			Fragment previousInstance = fragment
					.findFragmentByTag("HotPointFragmentTag");
			if(previousInstance != null){
				popHotPointsFagment();
			}else{
				leftAlert();
			}
			
			
			
			

		}else{
			leftAlert();
		}

	}

	private void leftAlert() {
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
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	protected void popHotPointsFagment(){
		aFragment previousInstance = (aFragment) fragment
				.findFragmentByTag("HotPointFragmentTag");
		int fromPage = previousInstance.getFromPage();
		if(fromPage == 2){
				attachHotPointListFragment();
			fromPage = 0;
			topBarBtn.setVisibility(View.GONE);
			topBarBtn2.setVisibility(View.VISIBLE);
		}else{
			attachHotPointMapFragment();
		}
		
		backHotPointMapFragment();
	  
	}
	protected void setImageGone(){
	    topBarBtn = (Button) findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
	    topBarBtn2 = (Button) findViewById(R.id.barbtn);
		topBarBtn2.setVisibility(View.GONE);
	}
	
//map
	protected void backHotPointMapFragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment previousInstance = fragment
				.findFragmentByTag("HotPointFragmentTag");
		if (previousInstance != null) {
			fragmentTransaction.remove(previousInstance);

		}
		fragmentTransaction.commit();
	}
	
	protected void attachHotPointListFragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();

		Fragment hotPointListFragment = getSupportFragmentManager()
				.findFragmentByTag("HotPointListFragmentTag");

		fragmentTransaction.attach(hotPointListFragment);

		fragmentTransaction.commit();
	}
	
	protected void attachHotPointMapFragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();

		Fragment hotPointMapFragment = getSupportFragmentManager()
				.findFragmentByTag(mTextviewArray[3]);

		fragmentTransaction.attach(hotPointMapFragment);

		fragmentTransaction.commit();
	}
	
	
	
	protected void replaceHotPointListFragment() {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment hotPointListFragment = new HotPointListFragment();
		Fragment hotPointMapFragment = getSupportFragmentManager()
				.findFragmentByTag(mTextviewArray[3]);

		fragmentTransaction.detach(hotPointMapFragment);
		fragmentTransaction.add(R.id.realtabcontent, hotPointListFragment,
				"HotPointListFragmentTag");
		fragmentTransaction.commit();

	}

	
	
	protected void replaceHotPointListFragment2(Bundle tempBundle) {
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragment
				.beginTransaction();
		Fragment fragmenta = new aFragment();
		Fragment hotPointListFragment = getSupportFragmentManager()
				.findFragmentByTag("HotPointListFragmentTag");
		fragmentTransaction.detach(hotPointListFragment);
		
		fragmenta.setArguments(tempBundle);
		fragmentTransaction.add(R.id.realtabcontent, fragmenta,
				"HotPointFragmentTag");
		fragmentTransaction.commit();

	}
}
