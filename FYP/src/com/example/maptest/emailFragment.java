package com.example.maptest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class emailFragment extends Fragment {
	// private static final int RESULT_OK = 0;
	private Button send;
	private EditText subject, emailtext;
	private TextView address;
	private String picturePath;

	private static int RESULT_LOAD_IMAGE = 1;
	private ViewGroup mContainerView;
	private ArrayList<Uri> imagePath = new ArrayList<Uri>();
	private ArrayList<Integer> imageID = new ArrayList<Integer>();
	private int id = 0;
	private Button topBarBtn;
	private TextView topBarText;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		topBarText = (TextView) getActivity().findViewById(R.id.bartext);
		topBarText.setText("報告");
		topBarBtn = (Button) getActivity().findViewById(R.id.backbarbtn);
		topBarBtn.setVisibility(View.GONE);
		
		
		
		View view = inflater.inflate(R.layout.email, container, false);
		mContainerView = (ViewGroup) view.findViewById(R.id.container);
		send = (Button) view.findViewById(R.id.emailsendbutton);


		emailtext = (EditText) view.findViewById(R.id.emailtext);

		Button select = (Button) view.findViewById(R.id.select);
		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);

			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND_MULTIPLE);

				emailIntent.setType("plain/text");

				String[] name = new String[1];
				name[0] = "bond723049@gmail.com";
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, name);

				// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				// "bond723049@gmail.com");
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"蝴蝶報告");
				
//				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//						subject.getText().toString());
				// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// "12233//");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						emailtext.getText());
				emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
						imagePath);
				// Log.d("1", "Size of the ArrayList :: " +imagePath.size());

				// emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(picturePath));
				// emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:///"+
				// picturePath));

				emailIntent.setType("image/*");

				emailIntent.setType("message/rfc882");

				Intent.createChooser(emailIntent, "Choose Email Client");

				emailFragment.this.startActivity(Intent.createChooser(
						emailIntent, "Email"));

			}
		});
		return view;

	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == RESULT_LOAD_IMAGE) {
				Uri selectedImage = data.getData();

				String[] filePathColumn = { MediaColumns.DATA };

				Cursor cursor = getActivity().getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				picturePath = cursor.getString(columnIndex);
				imagePath.add(Uri.parse("file://" + picturePath));
				cursor.close();
				try {
	    		addItem(id);
	    		id++;
	        }
	        catch (Exception ex) {
	            System.out.println("something wrong");
	        }

			}
        }
        catch (Exception ex) {
            System.out.println("something wrong");
        }
		
		
		// if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK &&
		// null != data) {
		

	}

	private void addItem(final int id) {

		imageID.add(id);

		// Instantiate a new "row" view.
		final ViewGroup newView = (ViewGroup) LayoutInflater
				.from(getActivity()).inflate(R.layout.email_pic_list,
						mContainerView, false);

		// Set the text in the new row to a random country.
		TextView test = ((TextView) newView.findViewById(android.R.id.text1));

		//test.setText(imagePath.toString());
		ImageView imageView = (ImageView) newView.findViewById(R.id.image);
		imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		
		// test.setText(picturePath);
		// imageView.setImageBitmap(BitmapFactory.decodeFile("/storage/extSdCard/DCIM/100MSDCF/DSC00600.jpg"));

		newView.findViewById(R.id.delete_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						int temp = imageID.indexOf(id);
						imagePath.remove(temp);
						imageID.remove(temp);
						mContainerView.removeView(newView);

					}
				});

		mContainerView.addView(newView, 0);
	}
	
	
	
	
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onActivityCreated(savedInstanceState);
	  Log.i("onActivityCreated()", "onActivityCreated");
	 }

	 @Override
	 public void onAttach(Activity activity) {
	  // TODO Auto-generated method stub
	  super.onAttach(activity);

	  Log.i("onAttach()", "onAttach");
	 }

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  Log.i("onCreate()", "onCreate");
	 }

	 @Override
	 public void onDestroy() {
	  // TODO Auto-generated method stub
	  super.onDestroy();
	  Log.i("onDestroy()", "onDestroy");
	 }

	 @Override
	 public void onDestroyView() {
	  // TODO Auto-generated method stub
	  super.onDestroyView();
	  Log.i("onDestroyView()", "onDestroyView");
	//if(onCreateToken > 1){
		
		imagePath.clear();
		Log.i("onResume()", "imagePath");
	imageID.clear();
	Log.i("onResume()", "imageID");
	//}
	  
	 }

	 @Override
	 public void onDetach() {
	  // TODO Auto-generated method stub
	  super.onDetach();
	  Log.i("onDetach()", "onDetach");
	 }

	 @Override
	 public void onPause() {
	  // TODO Auto-generated method stub
	  super.onPause();
	  Log.i("onPause()", "onPause");
	 }

	 @Override
	 public void onStart() {
	  // TODO Auto-generated method stub
	  super.onStart();
	  Log.i("onStart()", "onStart");
	 }

	 @Override
	 public void onStop() {
	  // TODO Auto-generated method stub
	  super.onStop();
	  Log.i("onStop()", "onStop");

	 }
	@Override
	public void onResume() {
		super.onResume();
		Log.i("onResume()", "onResume");
	}
	
	

}
