package com.example.maptest;

import java.util.ArrayList;

import edu.mit.mobile.android.imagecache.ImageCache;
import edu.mit.mobile.android.imagecache.ImageLoaderAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter implements OnClickListener {	

	/*********** Declare Used Variables *********/
	private Activity activity;
	private CustomListViewAndroid fragment;
	private ArrayList data;
	private static LayoutInflater inflater = null;
	public Resources res;
	ListModel tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	@Override
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView text;
		public TextView textWide;
		public ImageView image;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.tabitem, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.text = (TextView) vi.findViewById(R.id.text);
			holder.image = (ImageView) vi.findViewById(R.id.image);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.text.setText("No Data");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (ListModel) data.get(position);

			/************ Set Model values in Holder elements ***********/

			holder.text.setText(tempValues.getButterflyName());
			/*holder.image.setImageResource(res.getIdentifier(
					"com.androidexample.customlistview:drawable/"
							+ tempValues.getImage(), null, null));*/
			
//file:///android_asset
			holder.image.setTag(R.id.ic__uri, Uri.parse("http://fyp.tswsw.com/www/btfImages/0_" + (position%3+1) +".jpg"));
			
			/******** Set Item Click Listner for LayoutInflater for each row *******/

			//vi.setOnClickListener(new OnItemClickListener(position));
		}
		return vi;
	}

	@Override
	public void onClick(View v) {
		Log.v("CustomAdapter", "=====Row button clicked=====");
	}
	
	

}
