package com.example.oblig2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	public static int REQUEST_TAKE_PHOTO = 1;

	private String mCurrentPhotoPath = null;

	Button startActivity, addFragment, addAnotherFragment, notificationToast,
			cameraBtn, vibrate;
	ImageView iView;
	Bitmap imageBitmap = null;
	public final static int mId = 587984548;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}

			MainFragment mainFragment = new MainFragment();

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, mainFragment).commit();
		}

		final Intent intent = new Intent(this, NewActivity.class);
		final Context c = this;
		iView = (ImageView) findViewById(R.id.ivPicture);
		startActivity = (Button) findViewById(R.id.btnActivity);
		addFragment = (Button) findViewById(R.id.btnFragment);
		addAnotherFragment = (Button) findViewById(R.id.btnAnotherFragment);
		notificationToast = (Button) findViewById(R.id.btnNotificationToast);
		cameraBtn = (Button) findViewById(R.id.btnCamera);
		vibrate = (Button) findViewById(R.id.btnVibrate);

		startActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}

		});

		addFragment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PictureFragment pFragment = new PictureFragment();
				FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.fragment_container, pFragment);
				transaction.commit();
				
				if(iView == null){
					Log.d("hei","hei30294");
				}else{
					Log.d("bæ","bæ");
					iView.setImageDrawable(null);
					iView.setImageBitmap(imageBitmap);
				}
			}

		});

		addAnotherFragment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextFragment tFragment = new TextFragment();
				FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.fragment_container, tFragment);
				transaction.addToBackStack(null);
				transaction.commit();

			}

		});

		notificationToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(c, "You've got a notification to",
						Toast.LENGTH_LONG);
				t.show();

				// Notification stuff
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						c).setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle("Wow... Watch Out!!")
						.setContentText("Oh.. Wait, it was nothing... Sorry!");
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(mId, mBuilder.build());

			}

		});

		cameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (takePicture.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(takePicture, REQUEST_TAKE_PHOTO);
				}

			}

		});

		vibrate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(1000);
			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
			Bundle extrasBundle = data.getExtras();
			imageBitmap = (Bitmap) extrasBundle.get("data");
			// bruker ikke bildet enda da
		}
	}


	

}
