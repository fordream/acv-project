package jp.co.xing.utaehon;

import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.LogUtils;
import jp.co.xing.utaehon03.util.ShortCutUtils;
import jp.co.xing.utaehon.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.ict.library.database.DataStore;
import com.xing.joy.common.CoreActivity;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.others.Buy;
import com.xing.joy.others.Top;

public class VNCStartUpActivity extends CoreActivity {

	/** Image splash screen. */
	private ImageView imgSplashScreen, imgGetActionBar;

	/** Runnable waite . */
	private Runnable runnaSplashScreen;
	private AlertDialog alertDialog;

	/** Tag log. */
	private static final String LOG_STARTUP = "LOG_STARTUP";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Log.d("TEST", "Startup");
		// Set control Media Volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);

		imgSplashScreen = (ImageView) findViewById(R.id.flashscreen);
		resizeView(imgSplashScreen, 0, 0, 0, 0);

		imgGetActionBar = (ImageView) findViewById(R.id.getActionBar);
		resizeView(imgGetActionBar, 0, 0, 0, 0);

		// CommonUtils.autoCreateShortCut(
		// appData.getBoolData(IDataActions.IS_SHORTCUT), this);
		// CommonUtils.autoCreateShortCut(false, this);

		appData.setBoolData(IDataActions.IS_SHORTCUT, true);

		imageClientLoader.clear();
		// imageClientLoader.createDrawable(R.drawable.adr_splash,
		// imgGetActionBar);
		imageClientLoader.createDrawable(R.drawable.adr_splash_fixoutofmemory, imgSplashScreen);

		//ShortCutUtils cutUtils = new ShortCutUtils(this);
		//cutUtils.removeAll(new Class<?>[] { Startup.class, VNCStartUpActivity.class });

		if (getIntent() != null)
			LogUtils.eCommon("VNCStartUpActivity : " + getIntent().toString());
	}

	public void confirmRegisterWithC2DM() {
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(getString(R.string.allow_push));
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setButton2(this.getString(R.string.btn_no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isConfirmGCM(false);
			}
		});
		alertDialog.setButton(this.getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isConfirmGCM(true);
			}
		});
		if (this.hasWindowFocus()) {
			alertDialog.show();
		}
	}

	public void isConfirmGCM(boolean isOK) {
		try {
			if (isOK) {
				appData.setStringData("AllowReceivePush", "yes");
			} else {
				appData.setStringData("AllowReceivePush", "no");
			}
			CommonUtils.registerGCM(isOK, getApplicationContext());
			alertDialog.dismiss();
		} catch (Exception e) {
		}

		startNextActivity();
	}

	@Override
	protected void onResume() {
		if (imgSplashScreen != null) {
			if (appData.getStringData(IDataActions.CLASS_NAME).contains("com.xing.joy.others.Buy") && appData.getIntData(IDataActions.PROCESS_ID) == android.os.Process.myPid()) {
				CommonUtils.startNewActivity(this, Buy.class, "");
			} else {
				imgSplashScreen.postDelayed(runnaSplashScreen = new Runnable() {

					@Override
					public void run() {
						if (appData.getStringData("AllowReceivePush").equalsIgnoreCase("")) {
							confirmRegisterWithC2DM();
						} else {
							if (appData.getStringData("AllowReceivePush").equalsIgnoreCase("yes")) {
								CommonUtils.registerGCM(true, getApplicationContext());
							}
							startNextActivity();
						}
					}
				}, 1500);
			}
		}
		super.onResume();
		countDownload();
	}

	private void countDownload() {
		DataStore.getInstance().init(this);
		String keyName = "_now_version_name";
		int versionCode = DataStore.getInstance().get(keyName, 0);

		int versionCodeOfApp = -1;
		try {
			versionCodeOfApp = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
		}

		if (versionCode != versionCodeOfApp && versionCodeOfApp != -1) {
			// update version
			if (versionCode != 0) {
				// UPDATE
				if (versionCodeOfApp > versionCode) {
					// Log.e(keyName, "UPDATE");
					String category = getResources().getString(R.string.namescreen_count_update);
					gauTils.sendEvent(category, category, category);
					gauTils.sendTiming(1l, category, category, category);

					// create Shortcut
					ShortCutUtils shortCutUtils = new ShortCutUtils(this);
					// shortCutUtils.removeAll(new Class<?>[] { Startup.class,
					// VNCStartUpActivity.class });
					//shortCutUtils.autoCreateShortCut();
				}
			} else {
				// download version
				// NEW DOWNLOAD
				// Log.e(keyName, "NEW DOWNLOAD");
				String category = getResources().getString(R.string.namescreen_count_downnload);
				gauTils.sendEvent(category, category, category);
				gauTils.sendTiming(1l, category, category, category);

				// create Shortcut
				ShortCutUtils shortCutUtils = new ShortCutUtils(this);
				// shortCutUtils.removeAll();
				// shortCutUtils.removeAll(new Class<?>[] { Startup.class,
				// VNCStartUpActivity.class });
				shortCutUtils.autoCreateShortCut();
			}

			DataStore.getInstance().save(keyName, versionCodeOfApp);
		}
	}

	@Override
	public void showPushNotification() {
		// Rewite and Nothing
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (appData.getIntData(IDataActions.HEIGHT_BAR) == 0) {
			appData.setIntData(IDataActions.HEIGHT_BAR, calResize.getHeight() - imgGetActionBar.getHeight() - 2 * calResize.getHemBlackHeight());
		}
	}

	private void startNextActivity() {
		CommonUtils.startNewActivity(this, Top.class, "");
	}

	@Override
	protected void onPause() {
		if (imgSplashScreen != null) {
			imgSplashScreen.removeCallbacks(runnaSplashScreen);
			imgSplashScreen.setBackgroundResource(0);
			imgSplashScreen = null;
		}

		runnaSplashScreen = null;
		Log.d(LOG_STARTUP, "On Pause");
		super.onPause();
	}

	@Override
	public void setBaseApplicationData() {
		return;
	}

	@Override
	public String getNameCount() {
		return "";
	}
}
