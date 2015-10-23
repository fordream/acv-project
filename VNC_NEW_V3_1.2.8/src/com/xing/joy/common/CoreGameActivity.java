package com.xing.joy.common;

import java.io.IOException;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.CommonUtils;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleLayoutGameActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.gcm.CommonUtilities;
import com.example.googleanalyticssample.GAUTils;
import com.vnp.loader.img.ImageClientLoader;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.processdata.ApplicationData;
import com.xing.joy.service.Logtime;

public abstract class CoreGameActivity extends SimpleLayoutGameActivity {

	protected GAUTils gauTils = new GAUTils();

	protected Logtime logtime;
	protected CalculateResize calResize;
	protected ImageClientLoader imageClientLoader;
	/** Using to get display information. */
	private DisplayMetrics metrics;

	protected ApplicationData appData;

	/** Image left and right hem. */
	protected ImageView bgRight, bgLeft;

	/** Manager of audio. */
	protected AudioManager mgr;

	/** Volume of system. */
	protected float volume;

	protected abstract void onCreateResources();

	protected abstract Scene onCreateScene();

	public ITextureRegion loadImageResourceFromSD(String nameResource,
			BitmapTextureAtlas pBitmapTextureAtlas, int pTexturePositionX,
			int pTexturePositionY) {

		return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				pBitmapTextureAtlas, this.getApplicationContext(),
				nameResource, pTexturePositionX, pTexturePositionY);

	}

	public ITextureRegion loadImageResourceFromAsset(String nameResource,
			BitmapTextureAtlas pBitmapTextureAtlas, int pTexturePositionX,
			int pTexturePositionY) {

		return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				pBitmapTextureAtlas, this.getApplicationContext(),
				nameResource, pTexturePositionX, pTexturePositionY);

	}

	public TiledTextureRegion loadImageResourceTiledFromSD(String nameResource,
			BitmapTextureAtlas pBitmapTextureAtlas, int pTexturePositionX,
			int pTexturePositionY, int numColumns, int numRows) {

		return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				pBitmapTextureAtlas, this.getApplicationContext(),
				nameResource, pTexturePositionX, pTexturePositionY, numColumns,
				numRows);
	}

	public TiledTextureRegion getTiledTextureFromPacker(
			TexturePack pTexturePack, int... pSpriteSheets) {
		final TexturePackTextureRegionLibrary mTexturePackTextureRegionLibrary = pTexturePack
				.getTexturePackTextureRegionLibrary();
		final int sheetcount = pSpriteSheets.length;
		TexturePackerTextureRegion[] objTextureRegion = new TexturePackerTextureRegion[sheetcount];
		for (int i = 0; i < sheetcount; i++) {
			objTextureRegion[i] = mTexturePackTextureRegionLibrary
					.get(pSpriteSheets[i]);
		}
		TiledTextureRegion mTiledTextureRegion = new TiledTextureRegion(
				pTexturePack.getTexture(), objTextureRegion);
		return mTiledTextureRegion;
	}

	/**
	 * Load Sound Resource
	 * 
	 * @param nameResource
	 * @return
	 */
	public Sound loadSoundResourceFromSD(String nameResource) {
		try {
			return SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), this, nameResource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Play action with gimmic 3, please overide action in child class
	 */
	public void combineGimmic3WithAction() {
	}

	/**
	 * Method check Touch Point Contains A Polygon
	 * 
	 * @myShap: A Sprite Or AnimatedSprite on Scene
	 * @x1: Axis coordinates 1 as compared to myShape
	 * @x2: Axis coordinates 2 as compared to myShape
	 * @y1: Axis coordinates 2 as compared to myShape
	 * @y2: Axis coordinates 2 as compared to myShape
	 * @pX, pY: Point Touch
	 */
	public boolean checkContains(RectangularShape myShape, int x1, int y1,
			int x2, int y2, int pX, int pY) {

		int myShapeX = (int) myShape.getX();
		int myShapeY = (int) myShape.getY();

		int polyX[] = new int[] { myShapeX + x1, myShapeX + x1, myShapeX + x2,
				myShapeX + x2 };
		int polyY[] = new int[] { myShapeY + y1, myShapeY + y2, myShapeY + y2,
				myShapeY + y1 };

		boolean c = false;
		int i, j = 0;
		for (i = 0, j = 3; i < 4; j = i++) {
			if (((polyY[i] > pY) != (polyY[j] > pY))
					&& (pX < (polyX[j] - polyX[i]) * (pY - polyY[i])
							/ (polyY[j] - polyY[i]) + polyX[i]))
				c = !c;
		}

		return c;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		appData = new ApplicationData(this);
		super.onCreate(savedInstanceState);
		imageClientLoader = ImageClientLoader.getInstance();
		imageClientLoader.init(this);
		// imageClientLoader.release();
		setBaseApplicationData();
		calResize = new CalculateResize(this);
		mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;

		// Set volumn media stream for application
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
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

	/**
	 * Update infomation's application when pause.
	 * */
	@Override
	protected void onPause() {

		long time = System.currentTimeMillis() - startTime;
		// CommonLog.e("time" + time, Math.round(((double) time / (double)
		// 1000)) + "");
		logtime.onPause();
		System.gc();
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// logtime.Logend(getNameCount());
		// if (!"".equals(getNameCount()) && getNameCount() != null) {
		// String category = getResources().getString(
		// R.string.namescreen_Access_Count);
		// String name = getResources().getString(
		// R.string.namescreen_Play_time);
		// gauTils.sendTiming(logtime.getTimeTotal(), category, name,
		// getNameCount());
		//
		// }
		// gauTils.onStop();

		// logEnd();
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
			CommonUtils.alertNotificationPush(CoreGameActivity.this, message,
					type, false);
		}
	};

	public void showPushNotification() {
		if (!appData.getStringData("message").equalsIgnoreCase("")) {
			CommonUtils.alertNotificationPush(CoreGameActivity.this,
					appData.getStringData("message"),
					appData.getIntData("type"), false);
			appData.setStringData("message", "");
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		appData.setStringData(IDataActions.CLASS_STATUS, "END");
		unbindDrawables(findViewById(R.id.buy_display));
		unbindDrawables(findViewById(R.id.credit_display));
		unbindDrawables(findViewById(R.id.layout_root));
		unbindDrawables(findViewById(R.id.main_layout));
		unbindDrawables(findViewById(R.id.howto_play_display));
		unbindDrawables(findViewById(R.id.imageRotate));
		unbindDrawables(findViewById(R.id.select_display));
		unbindDrawables(findViewById(R.id.startup_display));
		unbindDrawables(findViewById(R.id.top_display));

		super.onDestroy();

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
	 * Close application when HOME pressed
	 */
	@Override
	protected void onUserLeaveHint() {
		finish();
		super.onUserLeaveHint();
	}

	/**
	 * Check Device is Tablet or not, return true if your device is Tablet,
	 * return false if Phone.
	 * 
	 * @return boolean
	 * */
	protected boolean isTablet() {
		try {
			metrics = getResources().getDisplayMetrics();
			float screenWidth = (metrics.widthPixels / metrics.xdpi)
					* metrics.density;
			float screenHeight = (metrics.heightPixels / metrics.ydpi)
					* metrics.density;
			float tmp;
			if (metrics.widthPixels > metrics.heightPixels) {
				tmp = metrics.heightPixels * metrics.density;
			} else {
				tmp = metrics.widthPixels * metrics.density;
			}
			double size = Math.sqrt(Math.pow(screenWidth, 2)
					+ Math.pow(screenHeight, 2));
			return (size >= 6) && (tmp >= 400);
		} catch (Throwable t) {
			return false;
		}
	}

	protected void resizeView(View view, int left, int top, int right,
			int bottom) {

		try {
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
		} catch (Exception exception) {
		}
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

	protected void setBacgroundResource(View view, int res) {
		imageClientLoader.createDrawable(res, view);
	}

	public void startNewActivity(Class<?> cls, String backLink) {
		Intent intent = new Intent(this, cls);

		if (!backLink.equalsIgnoreCase("")) {
			intent.putExtra("Back", backLink);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);

		finish();
		startActivity(intent);
		if (Build.MODEL.equalsIgnoreCase("IS03")
				|| Build.MODEL.equalsIgnoreCase("IS06")) {
			finish();
			startActivity(intent);
		} else {
			startActivity(intent);
			finish();
		}

		logEnd();
		System.exit(0);
	}

	// 20130814
	// DIALOG
//	@Override
//	protected Dialog onCreateDialog(final int id) {
//
//		ZDialogUtils dialogUtils = new ZDialogUtils(this);
//		dialogUtils
//				.setCallBack(new android.content.DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						callBackDialog(id);
//					}
//				});
//		return dialogUtils.onCreateDialog(id);
//	}
//
//	protected void callBackDialog(int id) {
//
//	}

	// 20130814 end

}
