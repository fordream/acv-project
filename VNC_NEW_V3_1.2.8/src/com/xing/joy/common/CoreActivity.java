package com.xing.joy.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.LogUtils;
import jp.co.xing.utaehon03.util.MemoryUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.R.bool;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.gcm.CommonUtilities;
import com.example.googleanalyticssample.GAUTils;
import com.ict.library.util.XMLfunctions;
import com.vnp.loader.img.ImageClientLoader;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.processdata.ApplicationData;
import com.xing.joy.service.Logtime;

/**
 * This class contain common functions.
 * */
public abstract class CoreActivity extends FragmentActivity {
	protected GAUTils gauTils = new GAUTils();
	protected ImageClientLoader imageClientLoader;
	protected Handler handler = new Handler();
	protected CalculateResize calResize;
	protected Logtime logtime;
	/** Volume of system. */
	protected float volume;

	/** Image left and right hem. */
	protected ImageView bgRight, bgLeft, imgBack;

	protected ApplicationData appData;

	/** Flag store status of mode. */
	protected boolean isBackPressed = false, isPlayRecordingMode = false;
	protected boolean isTouch = true;

	/** Manager of audio. */
	protected AudioManager mgr;

	public MemoryUtils memory;

	/** Share Preference for purchase_db. */
	protected SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		imageClientLoader = ImageClientLoader.getInstance();
		imageClientLoader.init(this);
		// imageClientLoader.release();

		calResize = new CalculateResize(this);
		appData = new ApplicationData(this);
		memory = new MemoryUtils(this);

		// Initial shared preferences.
		prefs = getApplication().getSharedPreferences(
				IDataActions.PURCHASED_DB, MODE_PRIVATE);

		// set info
		setBaseApplicationData();

		// Load AudioManager
		mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;

		// Set volumn media stream for application
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Kepp screen on
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		registerReceiver(broadcastReceiver, new IntentFilter(
				"com.xing.joy.common.activity.displayevent"));

		Logtime.deviceId = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		Logtime.urlLogPassword = getString(R.string.url_log_password);
		Logtime.urlLogDevice = getString(R.string.url_log_device);

		logtime = new Logtime(this);
		logtime.Logstart(getNameCount());
		logtime.LogPageStart = getNameCount();

		gauTils.onCreate(this);
		if (!"".equals(getNameCount()) && getNameCount() != null)
			gauTils.sendView(getNameCount());

		CommonUtilities.ACTIVITY_TOP = this;
	}

	private long startTime = 0l;

	@Override
	protected void onStart() {
		super.onStart();
		gauTils.onStart();
	}

	@Override
	protected synchronized void onResume() {
		super.onResume();
		logtime.onResume();
		startTime = System.currentTimeMillis();
	}

	/**
	 * Update infomation's application when pause.
	 * */
	@Override
	protected void onPause() {

		long time = System.currentTimeMillis() - startTime;
		// CommonLog.e("time" + time, Math.round(((double) time / (double)
		// 1000))
		// + "");
		logtime.onPause();
		System.gc();
		super.onPause();
	}

	@Override
	protected void onStop() {
		LogUtils.eCommon("onStop : " + getClass().getName());
		super.onStop();
	}

	public void logEnd() {
		logtime.Logend(getNameCount());
		if (!"".equals(getNameCount()) && getNameCount() != null) {
			String category = getResources().getString(
					R.string.namescreen_Access_Count);
			String name = getResources().getString(
					R.string.namescreen_Play_time);

			gauTils.sendTiming(logtime.getTimeTotal(), category, name,
					getNameCount());
		}
		gauTils.onStop();
	}

	public abstract String getNameCount();

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			showPushNotification();
		}
	}

	/**
	 * BroadCastReceiver for receive intent. When receive Intent, it update UI.
	 * */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("message");
			int type = intent.getIntExtra("type", 0);
			CommonUtils.alertNotificationPush(CoreActivity.this, message, type,
					false);
		}
	};

	public void showPushNotification() {
		if (!appData.getStringData("message").equalsIgnoreCase("")) {
			CommonUtils.alertNotificationPush(CoreActivity.this,
					appData.getStringData("message"),
					appData.getIntData("type"), false);
			appData.setStringData("message", "");
		}
	}

	public void addBackButton(int rlSelect) {

		RelativeLayout layout = (RelativeLayout) findViewById(rlSelect);
		// init object
		imgBack = new ImageView(this);
		// imgBack.setImageResource(R.drawable.cb_06_adr_back);

		setBacground3Resource(imgBack, R.drawable.cb_06_adr_back);

		// Adjust position
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				Math.round(97 * calResize.getRatioResizeWidth()),
				Math.round(97 * calResize.getRatioResizeHeight()));

		lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, -1);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);

		lp.leftMargin = Math.round(20 * calResize.getRatioResizeWidth())
				+ calResize.getHemBlackWidth();
		lp.topMargin = Math.round(20 * calResize.getRatioResizeHeight())
				+ calResize.getHemBlackHeight();
		imgBack.setLayoutParams(lp);

		// add into view
		layout.addView(imgBack);

		imgBack.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN && isTouch) {
					onBackPressed();
				}
				return true;
			}
		});
	}

	/**
	 * This function to create black hem for screen.
	 * 
	 * @param rlSelect
	 *            Layout to add black hem.
	 * */
	public void createHemImage(int rlSelect) {
		if (calResize.getStatusHem() == 0) {
			return;
		}
		RelativeLayout layout = (RelativeLayout) findViewById(rlSelect);
		// set image1
		bgLeft = new ImageView(this);
		bgLeft.setBackgroundColor(Color.BLACK);

		// set image2
		bgRight = new ImageView(this);
		bgRight.setBackgroundColor(Color.BLACK);

		// calculate pixels appear black hem
		if (calResize.getStatusHem() == 1) {

			// create image 1
			RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
					calResize.getHemBlackWidth(),
					RelativeLayout.LayoutParams.FILL_PARENT);
			param1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			bgLeft.setLayoutParams(param1);
			layout.addView(bgLeft, param1);

			// create image 2
			RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
					calResize.getHemBlackWidth(),
					RelativeLayout.LayoutParams.FILL_PARENT);
			param2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			bgRight.setLayoutParams(param2);
			layout.addView(bgRight, param2);
		}
		if (calResize.getStatusHem() == 2) {

			// create image 1
			RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT,
					calResize.getHemBlackHeight());
			param1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			bgLeft.setLayoutParams(param1);
			layout.addView(bgLeft, param1);

			// create image 2
			RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT,
					calResize.getHemBlackHeight());
			param2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			bgRight.setLayoutParams(param2);
			layout.addView(bgRight, param2);
		}
	}

	/**
	 * This function to resize image in screen with image in mode (960x640).
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	protected void resizeView(View view, int left, int top, int right,
			int bottom) {
		// function to set layout and margin for a view
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(view.getLayoutParams().width
						* calResize.getRatioResizeWidth()), Math.round(view
						.getLayoutParams().height
						* calResize.getRatioResizeHeight()));
		prView.setMargins(
				Math.round(left * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(top * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), right, bottom);
		view.setLayoutParams(prView);

	}

	protected void resizeView(View view, int width, int height, int left,
			int top, int right, int bottom) {
		// function to set layout and margin for a view
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(width * calResize.getRatioResizeWidth()),
				Math.round(height * calResize.getRatioResizeHeight()));
		prView.setMargins(
				Math.round(left * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(top * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), right, bottom);
		view.setLayoutParams(prView);

	}

	protected void resizeViewRatio(View view, int left, int top, int right,
			int bottom, float ratio) {
		// function to set layout and margin for a view
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(view.getLayoutParams().width
						* calResize.getRatioResizeWidth() * ratio),
				Math.round(view.getLayoutParams().height
						* calResize.getRatioResizeHeight() * ratio));
		prView.setMargins(
				Math.round(left * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(top * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), right, bottom);
		view.setLayoutParams(prView);

	}

	/**
	 * This function to resize image in screen. Difference with resizeView, this
	 * function not add black hem again.
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	protected void resizeViewInView(View view, int left, int top, int right,
			int bottom) {

		// function to set layout and margin for a view
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(view.getLayoutParams().width
						* calResize.getRatioResizeWidth()), Math.round(view
						.getLayoutParams().height
						* calResize.getRatioResizeHeight()));
		prView.setMargins(Math.round(left * calResize.getRatioResizeWidth()),
				Math.round(top * calResize.getRatioResizeHeight()),
				Math.round(right * calResize.getRatioResizeWidth()),
				Math.round(bottom * calResize.getRatioResizeHeight()));
		view.setLayoutParams(prView);

	}

	protected void resizeViewInView(View view, int width, int height, int left,
			int top, int right, int bottom) {
		if (view != null) {
			// function to set layout and margin for a view
			RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
					Math.round(width * calResize.getRatioResizeWidth()),
					Math.round(height * calResize.getRatioResizeHeight()));
			prView.setMargins(
					Math.round(left * calResize.getRatioResizeWidth()),
					Math.round(top * calResize.getRatioResizeHeight()),
					Math.round(right * calResize.getRatioResizeWidth()),
					Math.round(bottom * calResize.getRatioResizeHeight()));
			view.setLayoutParams(prView);
		}

	}

	/**
	 * This function to set new layout for a view.
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	protected void setNewLayout(View view, int left, int top, int right,
			int bottom) {

		// function to set layout and margin for a view
		if (view != null) {
			RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
					Math.round(view.getLayoutParams().width), Math.round(view
							.getLayoutParams().height));
			prView.setMargins(
					Math.round(left * calResize.getRatioResizeWidth()),
					Math.round(top * calResize.getRatioResizeHeight()), right,
					bottom);
			view.setLayoutParams(prView);
		}
	}

	/**
	 * This function to resize web view.
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	protected void resizeWebView(WebView view, int left, int top, int right,
			int bottom) {

		// function to set layout and margin for a view
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(view.getLayoutParams().width
						* calResize.getRatioResizeWidth()), Math.round(view
						.getLayoutParams().height
						* calResize.getRatioResizeHeight()));

		prView.setMargins(
				Math.round(left * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(top * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), right, bottom);
		view.setLayoutParams(prView);
	}

	/**
	 * This function to set new layout for a view.
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	protected void setNewLayoutUntil(View view, int left, int top, int right,
			int bottom) {
		if (view != null) {

			// function to set layout and margin for a view
			RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
					Math.round(view.getLayoutParams().width), Math.round(view
							.getLayoutParams().height));
			prView.setMargins(
					Math.round(left * calResize.getRatioResizeWidth())
							+ calResize.getHemBlackWidth(),
					Math.round(top * calResize.getRatioResizeHeight())
							+ calResize.getHemBlackHeight(), right, bottom);
			view.setLayoutParams(prView);
		}
	}

	/**
	 * set null object view.
	 * 
	 * @param view
	 */
	protected void setNullViewDrawable(ImageView view) {
		try {
			if (view != null) {
				if (view.getDrawable() instanceof BitmapDrawable
						&& view.getDrawable() != null) {
					Bitmap bitmap = ((BitmapDrawable) view.getDrawable())
							.getBitmap();
					view.setBackgroundResource(0);

					// bitmap.recycle();
				}

				if (view.getDrawable() != null) {
					view.getDrawable().setCallback(null);
				}
				if (view.getBackground() != null) {
					view.getBackground().setCallback(null);
				}

				view.setImageDrawable(null);
				view.getResources().flushLayoutCache();
				view.destroyDrawingCache();
				view.setBackgroundDrawable(null);
				view.setBackgroundResource(0);
				// add
				view = null;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Close application when HOME pressed
	 */
	@Override
	protected void onUserLeaveHint() {
		// Log.d("TEST", "onUserLeaveHint");
		releaseMemory();
		finish();
		super.onUserLeaveHint();
	}

	/**
	 * This funciton used for release memory.
	 */
	public void releaseMemory() {
		if (bgLeft != null) {
			bgLeft = null;
		}
		if (bgRight != null) {
			bgRight = null;
		}
		if (imgBack != null) {
			imgBack.setBackgroundResource(0);
			imgBack = null;
		}

		appData.setStringData(IDataActions.CLASS_STATUS, "END");
		System.gc();
	}

	@Override
	protected void onDestroy() {
		LogUtils.eCommon("onDestroy : " + getClass().getName());
		unregisterReceiver(broadcastReceiver);
		releaseMemory();

		super.onDestroy();
		unbindDrawables(findViewById(R.id.buy_display));
		unbindDrawables(findViewById(R.id.credit_display));
		unbindDrawables(findViewById(R.id.layout_root));
		unbindDrawables(findViewById(R.id.main_layout));
		unbindDrawables(findViewById(R.id.howto_play_display));
		unbindDrawables(findViewById(R.id.imageRotate));
		unbindDrawables(findViewById(R.id.select_display));
		unbindDrawables(findViewById(R.id.startup_display));
		unbindDrawables(findViewById(R.id.top_display));
		// System.gc();
		// System.runFinalization();
		// System.gc();

	}

	private void unbindDrawables(View view) {
		if (view != null && view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view != null && view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}

	@Override
	public void onBackPressed() {
		// Log.d("TEST", "onBackPressed");
		// Delete all information of song when back press.
		isBackPressed = true;
		super.onBackPressed();
	}

	public void setBaseApplicationData() {
		if (!this.getClass().getName()
				.equalsIgnoreCase(getPackageName() + ".Startup")
				&& !this.getClass()
						.getName()
						.equalsIgnoreCase(
								getPackageName() + ".VNCStartUpActivity")) {
			appData.setStringData(IDataActions.CLASS_NAME, this.getClass()
					.getName());
			appData.setStringData(IDataActions.CLASS_STATUS, "START");
			PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				appData.setStringData(IDataActions.VERSION, pInfo.versionName);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Check Device is Tablet or not, return true if your device is Tablet,
	 * return false if Phone.
	 * 
	 * @return boolean
	 * */
	protected boolean isTablet() {
		try {
			DisplayMetrics dm = getResources().getDisplayMetrics();
			float screenWidth = (dm.widthPixels / dm.xdpi);
			float screenHeight = (dm.heightPixels / dm.ydpi);
			float tmp;
			if (dm.widthPixels > dm.heightPixels) {
				tmp = dm.heightPixels * dm.density;
			} else {
				tmp = dm.widthPixels * dm.density;
			}
			double size = Math.sqrt(Math.pow(screenWidth, 2)
					+ Math.pow(screenHeight, 2));
			return (size >= 6) && (tmp >= 400);
		} catch (Throwable t) {
			return false;
		}
	}

	// Check Internet Connection
	public boolean isOnline() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conMgr.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method used for check package installed or not.
	 * 
	 * @param uri
	 *            Uri of package to check.
	 * @return {@link bool} true if package is installed and false if package
	 *         not installed.
	 * */
	protected boolean isAppInstalled(String uri, String namePackage) {
		return (new File(uri + ".ini")).exists()
				&& Boolean.parseBoolean(prefs.getString(namePackage,
						isDebug() + ";0").split(";")[0]);
	}

	protected boolean isPurchasedButNotDownloaded(String uri, String namePackage) {
		if (Boolean.parseBoolean(prefs.getString(namePackage, isDebug() + ";0")
				.split(";")[0]) && !new File(uri + ".ini").exists()) {
			return true;
		}
		return false;

	}

	protected boolean isAppRelationInstalled(String namePackage,
			String namePackageRelation) {
		if (isDebug()) {
			return false;
		}
		boolean isPackRelationPurchased = false;

		// check package relation
		if (!(namePackage.equalsIgnoreCase("") || namePackageRelation
				.equalsIgnoreCase(namePackage))) {
			if (namePackageRelation.contains("#")) {
				String[] parseInfo = namePackageRelation.trim().split("#");
				for (int i = 0; i < parseInfo.length; i++) {
					if (Boolean.parseBoolean(prefs.getString(parseInfo[i],
							isDebug() + ";0").split(";")[0])) {
						isPackRelationPurchased = true;
						break;
					}
				}
			} else {
				isPackRelationPurchased = Boolean.parseBoolean(prefs.getString(
						namePackageRelation, isDebug() + ";0").split(";")[0]);
			}
		}
		if (Boolean.parseBoolean(prefs.getString(namePackage, isDebug() + ";0")
				.split(";")[0])
				&& isPackRelationPurchased
				&& namePackageRelation.contains("#")) {
			isPackRelationPurchased = false;
		}

		return isPackRelationPurchased;
	}

	/*
	 * Allow change -/+ volume
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			float streamVolumeCurrent = mgr
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax = mgr
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = streamVolumeCurrent / streamVolumeMax;
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			streamVolumeCurrent = mgr
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = streamVolumeCurrent / streamVolumeMax;
			return true;
		default:
			return super.onKeyDown(keyCode, event);
		}
	}

	public boolean isDebug() {
		return getResources().getBoolean(R.bool.debug_version);
	}

	//
	// public boolean isTest() {
	// return getResources().getBoolean(R.bool.test);
	// }

	/**
	 * This method used for create dialog by purpose.
	 * 
	 * @param titleId
	 *            ID of Title resource string.
	 * @param messageId
	 *            ID of message resource string.
	 * @return {@link Dialog} Dialog to show.
	 * */
	protected Dialog createDialog(int titleId, int messageId) {
		String helpUrl = replaceLanguageAndRegion(getString(R.string.help_url));
		final Uri helpUri = Uri.parse(helpUrl);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titleId)
				.setIcon(android.R.drawable.stat_sys_warning)
				.setMessage(messageId)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(R.string.learn_more,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Intent.ACTION_VIEW,
										helpUri);
								startActivity(intent);
							}
						});
		return builder.create();
	}

	/**
	 * Replaces the language and/or country of the device into the given string.
	 * The pattern "%lang%" will be replaced by the device's language code and
	 * the pattern "%region%" will be replaced with the device's country code.
	 * 
	 * @param str
	 *            the string to replace the language/country within
	 * @return a string containing the local language and region codes
	 */
	protected String replaceLanguageAndRegion(String str) {

		// Substitute language and or region if present in string
		if (str.contains("%lang%") || str.contains("%region%")) {
			Locale locale = Locale.getDefault();
			str = str.replace("%lang%", locale.getLanguage().toLowerCase());
			str = str.replace("%region%", locale.getCountry().toLowerCase());
		}
		return str;
	}

	protected void setBacground3Resource(View view, int res) {
		imageClientLoader.createDrawable(res, view);
	}

	protected void setEnableAndAlPha(boolean isEnable, int alpha, ImageView view) {
		view.setEnabled(isEnable);
		view.setAlpha(alpha);
	}

	protected void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	protected boolean isSHow(View view) {
		if (view != null) {
			if (view.getVisibility() == View.VISIBLE) {
				return true;
			}
		}
		return false;
	}

	protected void showView(View view, boolean ishow) {
		if (view != null) {
			view.setVisibility(ishow ? View.VISIBLE : View.GONE);
		}
	}

	protected boolean isPurcharseOld(String pack) {
		return Boolean.parseBoolean(prefs.getString(pack,
				getResources().getBoolean(R.bool.debug_version) + ";0").split(
				";")[0]);

	}

	// public void startNewActivity(Class<?> cls, String backLink) {
	// Intent intent = new Intent(this, cls);
	//
	// if (!backLink.equalsIgnoreCase("")) {
	// intent.putExtra("Back", backLink);
	// }
	//
	// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
	// | Intent.FLAG_ACTIVITY_NEW_TASK);
	//
	// finish();
	// startActivity(intent);
	// if (Build.MODEL.equalsIgnoreCase("IS03")
	// || Build.MODEL.equalsIgnoreCase("IS06")) {
	// finish();
	// startActivity(intent);
	// } else {
	// startActivity(intent);
	// finish();
	// }
	//
	// logEnd();
	// System.exit(0);
	// }

	protected List<PackageSongs> getLPackageSongs() {
		List<PackageSongs> list = new ArrayList<PackageSongs>();
		String path = memory.getPathFileInternalMemory() + "img_buy/"
				+ "package_info.xml";
		if (new File(path).exists()) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(path));
				doc.getDocumentElement().normalize();
				NodeList nodes = doc.getElementsByTagName("package");

				for (int i = 0; i < nodes.getLength(); i++) {
					Element e = (Element) nodes.item(i);
					int packageID = Integer.parseInt(XMLfunctions.getValue(e,
							"id"));
					int packageNumberSongs = Integer.parseInt(XMLfunctions
							.getValue(e, "song_number"));
					String packageName = XMLfunctions.getValue(e, "name");
					String packageChecksum = XMLfunctions.getValue(e,
							"checksum_data");
					String iconImage = XMLfunctions.getValue(e, "icon");
					String iconInfo = XMLfunctions.getValue(e, "image_intro");
					int packageVol = Integer.parseInt(XMLfunctions.getValue(e,
							"vol"));
					String packagesRelation = XMLfunctions.getValue(e,
							"package_relation");
					PackageSongs packageSongs = new PackageSongs(packageID,
							packageNumberSongs, packageName, packageChecksum,
							iconImage, iconInfo, packageVol, packagesRelation);
					list.add(packageSongs);
				}

			} catch (Exception exception) {
			}
		}

		return list;
	}

	// 20130814
	// DIALOG
//	@Override
//	protected Dialog onCreateDialog(final int id) {
//
//		ZDialogUtils dialogUtils = new ZDialogUtils(this);
//		dialogUtils
//				.setCallBack(new android.content.DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						callBackDialog(id);
//					}
//				});
//		return dialogUtils.onCreateDialog(id);
//	}
//	protected void callBackDialog(int id) {
//		
//	}
	// 20130814 end
}