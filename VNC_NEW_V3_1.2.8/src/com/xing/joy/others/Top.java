package com.xing.joy.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.resouces.TopResource;
import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.MemoryUtils;
import jp.co.xing.utaehon03.util.UrlUtils;
import jp.co.xing.utaehon03.util.ZDialogUtils;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xing.joy.common.CoreGameActivity;
import com.xing.joy.common.DownloadProgessBar;
import com.xing.joy.interfaces.CallbackFromDiaglog;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.processdata.Download;
import com.xing.joy.processdata.DownloadHistory;
import com.xing.joy.processdata.FilesModel;
import com.xing.joy.processdata.Unzip;
import com.xing.joy.service.Logtime;

public class Top extends CoreGameActivity implements IOnSceneTouchListener,
		IModifierListener<IEntity>, IDataActions {
	private static final long DELAY_TIME = 0;
	private static final int CAMERA_WIDTH = 960;
	private static final int CAMERA_HEIGHT = 640;
	private Logtime logtimeForWeb;
	private Camera mCamera;
	private Scene mScene;

	private TexturePack packAnimation, packBackground;
	private TexturePackTextureRegionLibrary packLibAnimation,
			packLibBackground;

	private TiledTextureRegion tttBackground3, tttBear, tttFox, tttDeer,
			tttDuck;
	private ITextureRegion ttrBackground1;
	private ITextureRegion ttrBackground2;
	private ITextureRegion ttrBackground3_1;
	private ITextureRegion ttrBackground3_2;
	private ITextureRegion ttrBackground4;
	private ITextureRegion ttrBackground5;
	private ITextureRegion ttrRabbit;
	private ITextureRegion ttrCloudLeft;
	private ITextureRegion ttrCloudRight;
	private ITextureRegion ttrLogo;
	private ITextureRegion ttrButtonKaraoke;
	private ITextureRegion ttrButtonDouga;
	private ITextureRegion ttrButtonUta;
	private ITextureRegion ttrBuy;
	private ITextureRegion ttrCredit;
	private ITextureRegion ttrHelp;

	private Sprite sBackground1;
	private Sprite sBackground2;
	private Sprite sBackground4;
	private Sprite sBackground5;
	private Sprite sRabbit;
	private Sprite sCloudLeft;
	private Sprite sCloudRight;
	private Sprite sLogo;
	private Sprite sButtonKaraoke;
	private Sprite sButtonDouga;
	private Sprite sButtonUta;
	private Sprite sBuy;
	private Sprite sCredit;
	private Sprite sHelp;
	private AnimatedSprite asBackground3, asBear, asFox, asDeer, asDuck;

	private LoopEntityModifier lemRabbit;
	private LoopEntityModifier lemDeer;
	private LoopEntityModifier lemCloudLeft;
	private LoopEntityModifier lemCloudRight;
	private LoopEntityModifier lemBuy;
	private MoveXModifier mmfDuck1;
	private MoveXModifier mmfDuck2;

	/** Manager of audio. */
	private AudioManager mgr;

	/** Volume of system. */
	private float volume;

	private MediaPlayer mp = null;

	private ImageView imgNote = null;
	private ImageView imgRedownload = null;
	private ImageView close = null;
	private ImageView imgCancel = null;
	private ImageView imgLink = null;
	private ImageView imgLinkButton = null;
	private DownloadProgessBar downloadBar;
	private TopDataDownload download;
	private AlertDialog alertDialog;
	private MemoryUtils memory = null;

	private ArrayList<FilesModel> listDownload = new ArrayList<FilesModel>();

	// variable check exists data
	private boolean isTouch = false;
	private boolean isTouchRedownload = false;
	private long totalDownload = 0l;
	// checkUpdate == 3 -> update web + content
	// checkUpdate == 1 -> content only
	// checkUpdate == 2 -> update web only
	private int checkUpdate = 0;

	// static
	public static WebView webView;
	public static CallbackFromDiaglog callback;

	private DownloadHistory dHistory;
	private GetNewInformation getInfo;

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getRenderOptions().setARGB8888(true);
		engineOptions.getRenderOptions().setDithering(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("top/gfx/");
		try {
			loadKaraokeResources();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected synchronized void onResume() {

		super.onResume();

		if (logtimeForWeb != null) {
			logtimeForWeb.onResume();
		}
		// -------------------------------------------------------
		// -------------Date 20130306-----------------------------
		// -------------------------------------------------------
		// fix by: sound is lost when onResume
		// ZPackageManager zPackageManager = new ZPackageManager(this);
		// zPackageManager.getListIntentFilerAll();

		createSound();
		// -------------------------------------------------------
		// -------------Date 20130306 END-----------------------------
		// -------------------------------------------------------
	}

	@Override
	protected Scene onCreateScene() {
		this.getEngine().registerUpdateHandler(new FPSCounter());
		try {
			loadKaraokeScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.mScene;
	}

	@Override
	protected synchronized void onCreateGame() {
		mEngine.registerUpdateHandler(new TimerHandler(0.05f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						afterOnLoadComplete();
						loadKaraokeComplete();
						// -------------------
						// 20130329
						// fixbug galaxy young have a crash with back press
						// -------------------
						canBack = true;
						// -------------------
						// 20130329 END
						// -------------------
					}
				}));
		super.onCreateGame();
	}

	protected void loadKaraokeResources() throws Exception {

		packBackground = new TexturePackLoader(this.getTextureManager(),
				"top/gfx/").loadFromAsset(getAssets(), "top_background.xml");
		packBackground.loadTexture();

		packLibBackground = packBackground.getTexturePackTextureRegionLibrary();

		packAnimation = new TexturePackLoader(this.getTextureManager(),
				"top/gfx/").loadFromAsset(getAssets(), "top_animation.xml");
		packAnimation.loadTexture();

		packLibAnimation = packAnimation.getTexturePackTextureRegionLibrary();

		this.ttrBackground1 = packLibBackground
				.get(TopResource.top_background.TOP_22_IPHONE_HAIKEI_ID);
		this.ttrBackground2 = packLibBackground
				.get(TopResource.top_background.TOP_21_IPHONE_OKA_ID);
		this.ttrBackground3_1 = packLibBackground
				.get(TopResource.top_background.TOP_20_1_IPHONE_LAKE_ID);
		this.ttrBackground3_2 = packLibBackground
				.get(TopResource.top_background.TOP_20_2_IPHONE_LAKE_ID);
		this.tttBackground3 = new TiledTextureRegion(
				packBackground.getTexture(), ttrBackground3_1, ttrBackground3_2);
		this.ttrBackground4 = packLibBackground
				.get(TopResource.top_background.TOP_18_IPHONE_JIMEN_ID);

		this.ttrBackground5 = packLibBackground
				.get(TopResource.top_background.TOP_00_IPHONE_TOP_BACK_ID);

		this.tttBear = getTiledTextureFromPacker(packAnimation,
				TopResource.top_animation.TOP_06_3_IPHONE4_BEAR_ID,
				TopResource.top_animation.TOP_06_2_IPHONE4_BEAR_ID,
				TopResource.top_animation.TOP_06_1_IPHONE4_BEAR_ID);
		this.tttFox = getTiledTextureFromPacker(packAnimation,
				TopResource.top_animation.TOP_07_1_IPHONE4_FOX_ID,
				TopResource.top_animation.TOP_07_2_IPHONE4_FOX_ID);

		this.ttrRabbit = packLibAnimation
				.get(TopResource.top_animation.TOP_10_IPHONE4_RABBIT_ID);
		this.ttrLogo = packLibAnimation
				.get(TopResource.top_animation.TOP_13_IPHONE_LOGO_ID);

		this.ttrButtonKaraoke = packLibAnimation
				.get(TopResource.top_animation.TOP_02_IPHONE_KARAOKE_BTM_ID);
		this.ttrButtonUta = packLibAnimation
				.get(TopResource.top_animation.TOP_03_IPHONE_UTA_BT_ID);
		this.ttrButtonDouga = packLibAnimation
				.get(TopResource.top_animation.TOP_14_IPHONE_DOUGA_ID);

		// TexturePack packAnimation1 = new
		// TexturePackLoader(this.getTextureManager(),
		// "top/gfx/").loadFromAsset(getAssets(), "button_buy_new.xml");
		// packAnimation1.loadTexture();
		//
		// TexturePackTextureRegionLibrary packLibAnimation1 =
		// packAnimation1.getTexturePackTextureRegionLibrary();
		//
		// this.ttrBuy = packLibAnimation1.get(0);

		this.ttrBuy = packLibAnimation
				.get(TopResource.top_animation.TOP_17_IPHONE_BUY_ID);

		this.ttrCredit = packLibAnimation
				.get(TopResource.top_animation.TOP_04_ADR_CREDIT_BT_ID);
		this.ttrHelp = packLibAnimation
				.get(TopResource.top_animation.TOP_05_ADR_HELP_BT_ID);

	}

	protected void loadKaraokeScene() throws Exception {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(
				sBackground1 = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
						ttrBackground1, this.getVertexBufferObjectManager())));
		sBackground1.setZIndex(1);

		sBackground2 = new Sprite(0, 232, ttrBackground2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);
		sBackground2.setZIndex(4);

		asBackground3 = new AnimatedSprite(0, 322, tttBackground3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asBackground3);
		asBackground3.setZIndex(5);

		sBackground4 = new Sprite(0, 342, ttrBackground4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground4);
		sBackground4.setZIndex(7);

		asFox = new AnimatedSprite(260, 350, tttFox,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asFox);
		asFox.setZIndex(8);

		sRabbit = new Sprite(560, 370, ttrRabbit,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRabbit);
		sRabbit.setZIndex(9);

		asBear = new AnimatedSprite(85, 360, tttBear,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asBear);
		asBear.setZIndex(11);

		sBackground5 = new Sprite(0, 0, ttrBackground5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground5);
		sBackground5.setZIndex(12);

		sLogo = new Sprite(310, 20, ttrLogo,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLogo);
		sLogo.setZIndex(13);

		sButtonKaraoke = new Sprite(45, 200, ttrButtonKaraoke,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sButtonKaraoke);
		sButtonKaraoke.setZIndex(14);

		sButtonDouga = new Sprite(340, 200, ttrButtonDouga,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sButtonDouga);
		sButtonDouga.setZIndex(15);

		sButtonUta = new Sprite(645, 200, ttrButtonUta,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sButtonUta);
		sButtonUta.setZIndex(16);

		sBuy = new Sprite(350, 450, ttrBuy, this.getVertexBufferObjectManager());
		mScene.attachChild(sBuy);
		sBuy.setZIndex(17);

		sCredit = new Sprite(-10, 555, ttrCredit,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sCredit);
		sCredit.setZIndex(18);

		sHelp = new Sprite(812, 555, ttrHelp,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHelp);
		sHelp.setZIndex(19);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	protected void loadKaraokeComplete() {
		if (!new File(memory.getPathFileInternalMemory() + "/"
				+ "a_otf_jun501pro_bold.otf").exists()) {
			setAlphaButton(0.5f);
		} else {
			isTouch = true;
		}
		asBackground3.animate(300);
		asBear.animate(700);
		asFox.animate(600);
		asDeer.animate(350);

		lemRabbit = new LoopEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1, sRabbit.getY(), sRabbit.getY() - 30),
				new MoveYModifier(1, sRabbit.getY() - 30, sRabbit.getY())));
		sRabbit.registerEntityModifier(lemRabbit);

		lemDeer = new LoopEntityModifier(new MoveXModifier(15, asDeer.getX(),
				-asDeer.getWidth()));
		asDeer.registerEntityModifier(lemDeer);

		lemCloudLeft = new LoopEntityModifier(new MoveXModifier(10,
				sCloudLeft.getX(), -sCloudLeft.getWidth()));
		sCloudLeft.registerEntityModifier(lemCloudLeft);

		lemCloudRight = new LoopEntityModifier(new MoveXModifier(10,
				sCloudRight.getX(), -sCloudRight.getWidth()));
		sCloudRight.registerEntityModifier(lemCloudRight);

		mmfDuck1 = new MoveXModifier(10, 630, 300);
		mmfDuck2 = new MoveXModifier(10, 300, 630);
		asDuck.registerEntityModifier(mmfDuck1);
		mmfDuck1.addModifierListener(this);
		mmfDuck2.addModifierListener(this);

		lemBuy = new LoopEntityModifier(new SequenceEntityModifier(
				new ScaleModifier(0.5f, 1f, 1.1f),
				new ScaleModifier(1f, 1f, 1f)));
		sBuy.registerEntityModifier(lemBuy);
	}

	public void setAlphaButton(float alpha) {
		sButtonKaraoke.setAlpha(alpha);
		sButtonUta.setAlpha(alpha);
		sButtonDouga.setAlpha(alpha);
		sCredit.setAlpha(alpha);
		sHelp.setAlpha(alpha);
		sBuy.setAlpha(alpha);
	}

	public void afterOnLoadComplete() {
		this.ttrCloudRight = packLibAnimation
				.get(TopResource.top_animation.TOP_11_IPHONE_RIGHT_CROUD_ID);
		this.ttrCloudLeft = packLibAnimation
				.get(TopResource.top_animation.TOP_12_IPHONE_LEFT_CROUD_ID);
		this.tttDeer = getTiledTextureFromPacker(packAnimation,
				TopResource.top_animation.TOP_09_1_IPHONE4_DEER_ID,
				TopResource.top_animation.TOP_09_2_IPHONE4_DEER_ID);
		this.tttDuck = getTiledTextureFromPacker(packAnimation,
				TopResource.top_animation.TOP_19_IPHONE_SWAN_ID,
				TopResource.top_animation.TOP_19_IPHONE_SWAN_RIGHT_ID);

		sCloudLeft = new Sprite(720, 230, ttrCloudLeft,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sCloudLeft);
		sCloudLeft.setZIndex(2);

		sCloudRight = new Sprite(960, 130, ttrCloudRight,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sCloudRight);
		sCloudRight.setZIndex(3);

		asDuck = new AnimatedSprite(630, 310, tttDuck,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asDuck);
		asDuck.setZIndex(6);

		asDeer = new AnimatedSprite(960, 380, tttDeer,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asDeer);
		asDeer.setZIndex(10);
		this.mScene.sortChildren();
	}

	@SuppressLint("WorldReadableFiles")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new
		// AppRate(this).setShowIfAppHasCrashed(false).setMinDaysUntilPrompt(0).setMinLaunchesUntilPrompt(20).init();
		// Load AudioManager
		mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;

		// Set volumn media stream for application
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		dHistory = new DownloadHistory(this);

		imgNote = (ImageView) findViewById(R.id.top_note);
		resizeView(imgNote, -100, 22, 0, 0);

		imgLink = (ImageView) findViewById(R.id.top_link);
		resizeView(imgLink, 733, -30, -55, 0);

		imgLinkButton = (ImageView) findViewById(R.id.top_link_button);
		resizeView(imgLinkButton, 770, -40, -55, 0);

		imgCancel = (ImageView) findViewById(R.id.canceldownload);
		resizeView(imgCancel, 425, 530, 0, 0);
		imgCancel.setOnTouchListener(cancelDownload);

		imgRedownload = (ImageView) findViewById(R.id.redownload);
		resizeView(imgRedownload, 327, 512, 0, 0);

		imgRedownload.setOnTouchListener(reDownload);

		createHemImage(R.id.top_display);
		memory = new MemoryUtils(this);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);

					// Log.e("VERSION",
					// appData.getBoolData("NotShowPopupNewVersion") + "");

					if (!appData.getBoolData("NotShowPopupNewVersion")) {
						checkDownloadData(checkNewVersionAPK());
					} else {
						checkDownloadData(false);
					}
				} catch (Exception e) {
					checkDownloadData(false);
				}
			}
		}).start();

		imgNote.postDelayed(new Runnable() {

			@Override
			public void run() {
				setImageTopLink();
				try {
					if (checkFileExists(getFilesDir().getPath() + "/"
							+ "top_news_iphone.png")) {
						setImageTopNew(false);
					}
					imgNote.removeCallbacks(this);
				} catch (Exception e) {
				}
			}
		}, 1500);

		// UnZipTestManager manager = new UnZipTestManager() {
		// @Override
		// public void callBack(boolean isUpZipSucess) {
		// if (isUpZipSucess) {
		// CommonLog.e("OK", "OK");
		// }
		// }
		// };
		// manager.execute(this);

	}

	private OnTouchListener cancelDownload = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			try {
				if (imgCancel != null
						&& imgCancel.getVisibility() == View.VISIBLE) {
					if (!new File(memory.getPathFileInternalMemory() + "/"
							+ "a_otf_jun501pro_bold.otf").exists()) {
						for (int i = 0; i < listDownload.size(); i++) {
							dHistory.removeHistory(listDownload.get(i)
									.getFileName() + "isDownloaded");
						}
					}
					if (download != null) {
						download.cancel(true);
						download = null;
					}
					com.xing.joy.processdata.Download.isCancelled = true;
					downloadBar.reset();
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					imgCancel.postDelayed(new Runnable() {

						@Override
						public void run() {
							try {
								if (!new File(memory
										.getPathFileInternalMemory()
										+ "/"
										+ "a_otf_jun501pro_bold.otf").exists()) {
									// imgRedownload
									// .setBackgroundResource(R.drawable.redownload_iphone);

									setBacgroundResource(imgRedownload,
											R.drawable.redownload_iphone);
									isTouchRedownload = true;
								}
							} catch (Exception e) {
							}
						}
					}, 500);
				}
			} catch (Exception e) {
			}
			return true;
		}
	};

	private OnTouchListener reDownload = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			try {
				if (!isTouchRedownload) {
					return false;
				}
				if (imgRedownload != null && isTouchRedownload) {
					if (listDownload.size() <= 0) {
						getInfo = null;
						DownloadProgessBar.MAX = totalDownload;
						DownloadProgessBar.calculateMaxDownload();
						// imgRedownload.setBackgroundResource(0);
						setBacgroundResource(imgRedownload, 0);
						isTouchRedownload = false;
						downloadBar.setVisibility(View.VISIBLE);
						imgRedownload.postDelayed(new Runnable() {

							@Override
							public void run() {
								try {
									if (getInfo == null) {
										getInfo = (GetNewInformation) new GetNewInformation()
												.execute();
									}
								} catch (Exception e) {
								}
							}
						}, 300);
					} else {
						if (download == null) {
							DownloadProgessBar.MAX = totalDownload;
							DownloadProgessBar.calculateMaxDownload();
							// imgRedownload.setBackgroundResource(0);
							setBacgroundResource(imgRedownload, 0);
							isTouchRedownload = false;
							imgRedownload.postDelayed(new Runnable() {

								@Override
								public void run() {
									try {
										download = (TopDataDownload) new TopDataDownload(
												Top.this)
												.execute((FilesModel[]) listDownload
														.toArray(new FilesModel[listDownload
																.size()]));
									} catch (Exception e) {
									}
								}
							}, 300);
						}
					}
				}
			} catch (Exception e) {
			}
			return true;
		}
	};

	private void checkDownloadData(final boolean hasNewVersion) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (!hasNewVersion) {
					if (getInfo == null) {
						getInfo = (GetNewInformation) new GetNewInformation()
								.execute();
					}
				} else {
					try {
						CommonUtils.alertNotificationPush(Top.this,
								getString(R.string.note_update_version), 2,
								true);
						Top.callback = new CallbackFromDiaglog() {

							@Override
							public void callback() {
								if (!isOnline()
										&& !new File(
												memory.getPathFileInternalMemory()
														+ "/"
														+ "a_otf_jun501pro_bold.otf")
												.exists()) {
									// imgRedownload
									// .setBackgroundResource(R.drawable.redownload_iphone);

									setBacgroundResource(imgRedownload,
											R.drawable.redownload_iphone);
									alertInformation(getString(R.string.download_failed));
								} else {
									if (getInfo == null) {
										getInfo = (GetNewInformation) new GetNewInformation()
												.execute();
									}
								}
							}
						};
					} catch (Exception e) {
					}
				}
			}
		});
	}

	private class GetNewInformation extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!new File(memory.getPathFileInternalMemory() + "/"
					+ "a_otf_jun501pro_bold.otf").exists()) {
				if (downloadBar == null) {
					downloadBar = (DownloadProgessBar) findViewById(R.id.download_bar);
					DownloadProgessBar.typeDataDownload = 0;
					resizeView(downloadBar, 163, 396, 0, 0);
				}

				try {
					downloadBar.setVisibility(View.VISIBLE);
				} catch (Exception ex) {
				}
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				getListDownload();
			} catch (Exception e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							if (!new File(memory.getPathFileInternalMemory()
									+ "/" + "a_otf_jun501pro_bold.otf")
									.exists()) {

								// 20130814
								// alertInformation(getString(R.string.download_failed));
								new ZDialogUtils(Top.this) {
									public void onClick(DialogInterface dialog,
											int which) {
										super.onClick(dialog, which);
										try {
											if (downloadBar != null) {
												downloadBar
														.setVisibility(View.INVISIBLE);
												imgCancel
														.setVisibility(View.INVISIBLE);
											}
											isTouchRedownload = true;
										} catch (Exception e) {
										}
									};
								}.onCreateDialog(ZDialogUtils.DIALOG_ERROR_002);

								downloadBar.reset();
								downloadBar.setVisibility(View.GONE);
								imgCancel.setVisibility(View.GONE);
								// imgRedownload
								// .setBackgroundResource(R.drawable.redownload_iphone);

								setBacgroundResource(imgRedownload,
										R.drawable.redownload_iphone);
							}
						} catch (Exception e2) {
						}
					}
				});
				return false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			// Log.d("TEST", "" + result);
			if (result) {
				if (!new File(memory.getPathFileInternalMemory() + "/"
						+ "a_otf_jun501pro_bold.otf").exists()) {
					if (download == null && listDownload != null
							&& listDownload.size() > 0) {
						download = (TopDataDownload) new TopDataDownload(
								Top.this).execute((FilesModel[]) listDownload
								.toArray(new FilesModel[listDownload.size()]));
					}
				} else {
					if (listDownload != null && listDownload.size() > 0) {
						alertDialog = new AlertDialog.Builder(Top.this)
								.create();

						// TODO message update
						if ((checkUpdate == 1 || checkUpdate == 3)) {
							// if ((checkUpdate == 1)) {
							alertDialog.setTitle(Top.this
									.getString(R.string.note_update_title));
							alertDialog.setMessage(Top.this
									.getString(R.string.note_update_content));
						} else {
							alertDialog
									.requestWindowFeature(Window.FEATURE_NO_TITLE);
							alertDialog
									.setMessage(Top.this
											.getString(R.string.note_update_webview_content));
						}

						alertDialog.setCancelable(false);
						alertDialog.setCanceledOnTouchOutside(false);
						alertDialog.setButton(
								Top.this.getString(R.string.btn_yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										alertDialog.dismiss();
										if (download == null) {
											download = (TopDataDownload) new TopDataDownload(
													Top.this)
													.execute((FilesModel[]) listDownload
															.toArray(new FilesModel[listDownload
																	.size()]));
										}
										return;
									}
								});

						try {
							alertDialog.show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private String logWEBD = "WEBLOG";

	@SuppressLint("SetJavaScriptEnabled")
	public void createWeb() {
		logWEBD = getResources().getString(R.string.namescreen_webView);
		logtimeForWeb = new Logtime(this);
		logtimeForWeb.Logstart(logWEBD);
		logtimeForWeb.LogPageStart = logWEBD;

		gauTils.sendView(logWEBD);

		if (webView != null) {
			close.setVisibility(View.VISIBLE);
			close.setEnabled(true);
			webView.setVisibility(View.VISIBLE);
			return;
		}

		RelativeLayout top = (RelativeLayout) findViewById(R.id.top_display);
		webView = new WebView(this);
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
				Math.round(900 * calResize.getRatioResizeWidth()),
				Math.round(600 * calResize.getRatioResizeHeight()));
		prView.setMargins(
				Math.round(30 * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(20 * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), 0, 0);
		webView.setBackgroundColor(0);
		// webView.setBackgroundResource(R.drawable.bg);
		setBacgroundResource(webView, R.drawable.bg);
		webView.setLayoutParams(prView);
		// webView.addJavascriptInterface(new JavascriptInterface(this),
		// "Android");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAppCacheEnabled(false);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(new LinkWebViewClient());
		webView.loadUrl("file://" + memory.getPathFileInternalMemory()
				+ "webview/index.html");
		top.addView(webView);

		close = new ImageView(this);
		RelativeLayout.LayoutParams prClose = new RelativeLayout.LayoutParams(
				Math.round(62 * calResize.getRatioResizeWidth()),
				Math.round(69 * calResize.getRatioResizeHeight()));
		prClose.setMargins(
				Math.round(900 * calResize.getRatioResizeWidth())
						+ calResize.getHemBlackWidth(),
				Math.round(0 * calResize.getRatioResizeHeight())
						+ calResize.getHemBlackHeight(), 0, 0);
		close.setLayoutParams(prClose);
		// close.setBackgroundResource(R.drawable.close_btn_adr4);
		setBacgroundResource(close, R.drawable.close_btn_adr4);
		top.addView(close);
		close.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					closeWeb();
					// if (logtimeForWeb != null) {
					// logtimeForWeb.Logend(logWEBD);
					// long time = logtimeForWeb.getTimeTotal();
					//
					// if (time > 0) {
					// String category = logWEBD;
					// String name = "";
					//
					// category = getResources().getString(
					// R.string.namescreen_Access_Count);
					// name = getResources().getString(
					// R.string.namescreen_Play_time);
					// String label = category;
					// gauTils.sendTiming(time, category, name, label);
					// }
					// }
					//
					// logtimeForWeb = null;
					// gauTils.sendView(getNameCount());
					//
					// close.setVisibility(View.INVISIBLE);
					// webView.setVisibility(View.INVISIBLE);
					// webView.loadUrl("file://"
					// + memory.getPathFileInternalMemory()
					// + "webview/index.html");
				}
				return true;
			}
		});
	}

	private class LinkWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (URLUtil.isValidUrl(url) && url.contains("webview")) {
				view.loadUrl(url);
			} else {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				finish();
			}

			return true;
		}
	}

	public void setImageTopLink() {
		try {
			Bitmap bmImg = BitmapFactory.decodeFile(getFilesDir().getPath()
					+ "/" + "yudou_iphone.png");
			Drawable d = new BitmapDrawable(bmImg);
			imgLink.setBackgroundDrawable(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (imgLink != null && imgLink.getBackground() != null) {
			imgLinkButton.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						try {
							// Get free song
							String path = memory.getPathFileInternalMemory()
									+ "link_free.txt";
							File txt = new File(path);
							BufferedReader br = new BufferedReader(
									new FileReader(txt));
							String line = br.readLine();
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri.parse(line));
							startActivity(intent);
							br.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					return true;
				}
			});
		}
	}

	public void setImageTopNew(boolean isDownloaded) {
		try {
			Bitmap bmImg = BitmapFactory.decodeFile(getFilesDir().getPath()
					+ "/" + "top_news_iphone.png");
			Drawable d = new BitmapDrawable(bmImg);
			imgNote.setBackgroundDrawable(d);
			d = null;
			if (!appData.getBoolData("show_webview")
					&& new File(memory.getPathFileInternalMemory()
							+ "webview/index.html").exists()
					|| checkUpdate == 2 || checkUpdate == 3) {
				createWeb();
				appData.setBoolData("show_webview", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (imgNote != null && imgNote.getBackground() != null) {
			imgNote.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// if (webView == null) {
						createWeb();
						// } else {
						// close.setVisibility(View.VISIBLE);
						// close.setEnabled(true);
						// webView.setVisibility(View.VISIBLE);
						// }
						return true;
					} else {
						return false;
					}
				}
			});
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			// Start Play audio
			if (mp == null) {
				mp = MediaPlayer.create(this, R.raw.opening);
				mp.setVolume(volume, volume);
				mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mp.setLooping(true);
				mp.start();
			}
		}
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onPause() {
		if (logtimeForWeb != null)
			logtimeForWeb.onPause();
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		hiddenVIew(imgNote);
		hiddenVIew(imgLinkButton);

		if (imgNote != null) {
			imgNote.setBackgroundDrawable(null);
			imgNote = null;
		}
		if (download != null) {
			download.cancel(true);
		}
		if (imgRedownload != null) {
			imgRedownload.setBackgroundDrawable(null);
			imgRedownload = null;
		}

		if (imgCancel != null) {
			imgCancel.setBackgroundDrawable(null);
			imgCancel = null;
		}
		webView = null;
		close = null;
		super.onDestroy();
	}

	@Override
	protected int getLayoutID() {
		return R.layout.top;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.xmllayoutexample_rendersurfaceview;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (!isTouch) {
			return false;
		}
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sButtonKaraoke, 0, 0,
					(int) sButtonKaraoke.getWidth(),
					(int) sButtonKaraoke.getHeight(), pX, pY)) {

				appData.setStringData(IDataActions.TYPE_NAME, "karaoke_clicked");
				appData.setIntData(IDataActions.TYPE_ID, 0);
				// CommonUtils.startNewActivity(this, SelectSong.class, "");
				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						// CommonUtils.startNewActivity(Top.this,
						// SelectSong.class, "");

						startActivity(SelectSong.class);
					}
				}, DELAY_TIME);
			}
			if (checkContains(sButtonDouga, 0, 0,
					(int) sButtonDouga.getWidth(),
					(int) sButtonDouga.getHeight(), pX, pY)) {

				// hiddenVIew(imgCancel);
				// hiddenVIew(imgLink);
				// hiddenVIew(imgLinkButton);
				// hiddenVIew(imgNote);
				// hiddenVIew(imgRedownload);

				appData.setStringData(IDataActions.TYPE_NAME, "douga_clicked");
				appData.setIntData(IDataActions.TYPE_ID, 1);
				// CommonUtils.startNewActivity(this, SelectSong.class, "");

				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						// CommonUtils.startNewActivity(Top.this,
						// SelectSong.class, "");
						startActivity(SelectSong.class);
					}
				}, DELAY_TIME);
			}
			if (checkContains(sButtonUta, 0, 0, (int) sButtonUta.getWidth(),
					(int) sButtonUta.getHeight(), pX, pY)) {

				// hiddenVIew(imgCancel);
				// hiddenVIew(imgLink);
				// hiddenVIew(imgLinkButton);
				// hiddenVIew(imgNote);
				// hiddenVIew(imgRedownload);

				appData.setStringData(IDataActions.TYPE_NAME, "uta_clicked");
				appData.setIntData(IDataActions.TYPE_ID, 2);
				// CommonUtils.startNewActivity(this, SelectSong.class, "");
				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						// CommonUtils.startNewActivity(Top.this,
						// SelectSong.class, "");

						startActivity(SelectSong.class);
					}
				}, DELAY_TIME);

			}

			if (checkContains(sBuy, 0, 0, (int) sBuy.getWidth(),
					(int) sBuy.getHeight(), pX, pY)) {
				// hiddenVIew(imgCancel);
				// hiddenVIew(imgLink);
				// hiddenVIew(imgLinkButton);
				// hiddenVIew(imgNote);
				// hiddenVIew(imgRedownload);

				// CommonUtils.startNewActivity(this, Buy.class, "");

				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						startActivity(Buy.class);
					}
				}, DELAY_TIME);
			}
			if (checkContains(sCredit, 0, 0, (int) sCredit.getWidth(),
					(int) sCredit.getHeight(), pX, pY)) {
				// hiddenVIew(imgCancel);
				// hiddenVIew(imgLink);
				// hiddenVIew(imgLinkButton);
				// hiddenVIew(imgNote);
				// hiddenVIew(imgRedownload);

				// CommonUtils.startNewActivity(this, Credit.class, "");

				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						// CommonUtils
						// .startNewActivity(Top.this, Credit.class, "");
						startActivity(Credit.class);
					}
				}, DELAY_TIME);
			}
			if (checkContains(sHelp, 0, 0, (int) sHelp.getWidth(),
					(int) sHelp.getHeight(), pX, pY)) {
				// hiddenVIew(imgCancel);
				// hiddenVIew(imgLink);
				// hiddenVIew(imgLinkButton);
				// hiddenVIew(imgNote);
				// hiddenVIew(imgRedownload);

				handler.postAtTime(new Runnable() {
					@Override
					public void run() {
						hiddenVIew(imgCancel);
						hiddenVIew(imgLink);
						hiddenVIew(imgLinkButton);
						hiddenVIew(imgNote);
						hiddenVIew(imgRedownload);
						// CommonUtils.startNewActivity(Top.this,
						// HowtoPlay.class,
						// "");

						startActivity(HowtoPlay.class);
					}
				}, DELAY_TIME);

			}
		}
		return true;
	}

	private void startActivity(final Class<?> cls) {

		new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				try {
					Thread.sleep(DELAY_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				CommonUtils.startNewActivity(Top.this, cls, "");
			};

		}.execute("");

	}

	private Handler handler = new Handler();

	private void hiddenVIew(final ImageView imgNote2) {
		if (imgNote2 != null) {
			AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			alphaAnimation.setFillAfter(false);
			imgNote2.startAnimation(alphaAnimation);
			// imgNote2.setVisibility(View.GONE);
			imgNote2.post(new Runnable() {
				@Override
				public void run() {
					imgNote2.setVisibility(View.GONE);
				}
			});
		}
	}

	@Override
	public void updateInfosOnPause() {

	}

	@Override
	public void deleteInfosOnBack() {

	}

	@Override
	public void readInfosOnCreate() {

	}

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
			if (mp != null) {
				mp.setVolume(volume, volume);
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			streamVolumeCurrent = mgr
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = streamVolumeCurrent / streamVolumeMax;
			if (mp != null) {
				mp.setVolume(volume, volume);
			}
			return true;
		default:
			return super.onKeyDown(keyCode, event);
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

	protected boolean checkFileExists(String uri) {
		return (new File(uri)).exists();
	}

	public boolean checkNewVersionAPK() throws Exception {
		PackageInfo pInfo = getPackageManager().getPackageInfo(
				getPackageName(), 0);
		if (parserVersionNameToInt(pInfo.versionName) < parserVersionNameToInt(appData
				.getStringData("version_on_market"))) {
			// show popup to note new version on market

			return true;
		} else {
			if (isOnline()) {
				// load current version on server

				// String url = getString(R.string.url_top_free_download)
				// + "versions.ini";
				String url1 = new UrlUtils(Top.this)
						.getUrl(R.string.url_top_free_download)
						+ "versions.ini";

				URL url = new URL(url1);
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(1500);
				conn.setReadTimeout(1000);
				conn.connect();
				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String str = in.readLine();
				appData.setStringData("version_on_market", str);
				in.close();
				if (parserVersionNameToInt(pInfo.versionName) < parserVersionNameToInt(str)) {
					// show popup to note new version on market
					return true;
				}
			}
		}
		return false;
	}

	public int parserVersionNameToInt(String versionName) throws Exception {
		if (versionName.equalsIgnoreCase("")) {
			return 0;
		}
		String[] info = versionName.trim().replace(".", "#").split("#");
		return Integer.parseInt(info[0]) * 100 + Integer.parseInt(info[1]) * 10
				+ Integer.parseInt(info[2]);
	}

	public void readFileInformationDownload(String urlFile, int index)
			throws Exception {
		// Create a URL for the desired page
		URL url = new URL(urlFile);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(45000);
		conn.setReadTimeout(45000);
		conn.connect();
		// Read all the text returned by the server
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String str;
		while ((str = in.readLine()) != null) {
			// str = "vol3_part0_no_free_song.zip#18984960";
			String[] info = str.trim().split("#");
			if (!dHistory.getStringData(info[0]).equalsIgnoreCase("")) {
				dHistory.setStringData(info[0] + "isDownloaded", "OK");

				dHistory.removeHistory(info[0]);
			}

			if (dHistory.getStringData(info[0] + "isDownloaded")
					.equalsIgnoreCase("")) {
				// getString(R.string.url_top_free_download)
				String url1 = new UrlUtils(Top.this)
						.getUrl(R.string.url_top_free_download);
				listDownload.add(new FilesModel(info[0], url1 + info[0], memory
						.getPathCacheExternalMemory(), Long.parseLong(info[1]),
						memory.getPathFileInternalMemory()));
				totalDownload += Long.parseLong(info[1]);
				checkUpdate = index;
			}
			if (index > 1) {
				break;
			}
		}

		if (urlFile.contains("update_free.ini")) {
			// String str1 = "vol3_part0_no_free_song.zip#18984960";
			// str1 = "vol3_part0_free_to_purchase.zip#18984960";
			// String[] info = str1.trim().split("#");
			// String url1 = new UrlUtils(Top.this)
			// .getUrl(R.string.url_top_free_download);
			// listDownload.add(new FilesModel(info[0], url1 + info[0], memory
			// .getPathCacheExternalMemory(), Long.parseLong(info[1]),
			// memory.getPathFileInternalMemory()));
		}
		in.close();
	}

	public void getListDownload() throws Exception {
		String url1 = new UrlUtils(Top.this)
				.getUrl(R.string.url_top_free_download);

		// tString(R.string.url_top_free_download)
		readFileInformationDownload(url1 + "update_free.ini", 1);

		// getString(R.string.url_top_free_download)
		readFileInformationDownload(url1 + "update_freesong.ini", 1);
		if (!new File(memory.getPathFileInternalMemory() + "/"
				+ "a_otf_jun501pro_bold.otf").exists()) {
			for (int i = 0; i < listDownload.size(); i++) {
				dHistory.setStringData(listDownload.get(i).getFileName()
						+ "isDownloaded", "OK");
			}
			listDownload.clear();

			URL tmp = null;
			// getString(R.string.url_top_free_download)
			tmp = new URL(url1 + "vol3_part0.zip");

			HttpURLConnection urlConnection = (HttpURLConnection) tmp
					.openConnection();
			// set up some things on the connection.
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(45000);
			urlConnection.setConnectTimeout(45000);
			// and connect!
			urlConnection.connect();
			Long fileSize = Long.parseLong(urlConnection
					.getHeaderField("content-length"));
			urlConnection.disconnect();
			totalDownload = fileSize;
			checkUpdate = 1;
			// getString(R.string.url_top_free_download)
			listDownload.add(new FilesModel("vol3_part0.zip", url1
					+ "vol3_part0.zip", memory.getPathCacheExternalMemory(),
					fileSize, memory.getPathFileInternalMemory()));

		}
		// getString(R.string.url_top_free_download)
		readFileInformationDownload(url1 + "update_webview.ini",
				checkUpdate + 2);

		// for (int i = 0; i < listDownload.size(); i++) {
		// Log.d("TEST", listDownload.get(i).getFileName());
		// }

		DownloadProgessBar.MAX = totalDownload;
		DownloadProgessBar.calculateMaxDownload();
	}

	private class TopDataDownload extends Download {

		@Override
		protected void onPreExecute() {
			if (downloadBar == null) {
				downloadBar = (DownloadProgessBar) findViewById(R.id.download_bar);
				DownloadProgessBar.typeDataDownload = 0;
				resizeView(downloadBar, 163, 396, 0, 0);
			}
			if (downloadBar != null) {
				downloadBar.setVisibility(View.VISIBLE);
			}
			if (imgCancel != null) {
				imgCancel.setVisibility(View.VISIBLE);
			}
			super.onPreExecute();
		}

		@Override
		public void onProgressUpdate(Integer... args) {
			if (args[0] != null) {
				if (downloadBar != null)
					downloadBar.updateProgessBar(args[0]);
			}
		}

		public TopDataDownload(Context context) {
			super(context, true);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result != null && result) {
				DownloadProgessBar.STATUS_DOWNLOAD = getString(R.string.dlp_unzip);
				DownloadProgessBar.PERCENT_DOWNLOAD = "";
				if (downloadBar != null) {
					downloadBar.invalidate();
				}
				FilesModel[] fmUnzip = new FilesModel[listDownload.size()];
				for (int i = 0; i < fmUnzip.length; i++) {
					fmUnzip[i] = new FilesModel(listDownload.get(i)
							.getFileName(), listDownload.get(i).getPathDir()
							+ listDownload.get(i).getFileName(), listDownload
							.get(i).getPathNewDir());
				}
				new TopDataUnzip(Top.this).execute(fmUnzip);
			} else {
				if (downloadBar != null) {
					downloadBar.setVisibility(View.GONE);
					downloadBar.reset();
					download = null;
				}
				if (imgCancel != null) {
					imgCancel.setVisibility(View.GONE);
				}
				if (!new File(memory.getPathFileInternalMemory() + "/"
						+ "a_otf_jun501pro_bold.otf").exists()) {
					// imgRedownload
					// .setBackgroundResource(R.drawable.redownload_iphone);
					setBacgroundResource(imgRedownload,
							R.drawable.redownload_iphone);
					isTouchRedownload = true;
				}

			}
		}
	}

	public void alertInformation(String message) {
		try {
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage(message);
			alertDialog.setCancelable(false);
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.setButton(getString(R.string.btn_yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								if (downloadBar != null) {
									downloadBar.setVisibility(View.INVISIBLE);
									imgCancel.setVisibility(View.INVISIBLE);
								}
								alertDialog.dismiss();
								isTouchRedownload = true;
							} catch (Exception e) {
							}
							return;
						}
					});
			if (alertDialog != null && !alertDialog.isShowing()) {
				alertDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class TopDataUnzip extends Unzip {

		public TopDataUnzip(Context context) {
			super(context);
		}

		@Override
		protected void onPreExecute() {
			try {
				imgCancel.setVisibility(View.GONE);
			} catch (Exception e) {
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			// -------------------
			// 20130329
			// fixbug home bi crash
			// -------------------
			try {
				if (result != null && result) {
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					setAlphaButton(1f);
					setImageTopNew(true);
					setImageTopLink();
					isTouch = true;
					for (int i = 0; i < listDownload.size(); i++) {
						dHistory.setStringData(listDownload.get(i)
								.getFileName() + "isDownloaded", "OK");
					}
				} else {
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					if (!new File(memory.getPathFileInternalMemory() + "/"
							+ "a_otf_jun501pro_bold.otf").exists()) {
						setBacgroundResource(imgRedownload,
								R.drawable.redownload_iphone);
						isTouchRedownload = true;
					}
					downloadBar.reset();
					download = null;
				}
			} catch (Exception ex) {

			}
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier == mmfDuck1) {
			asDuck.registerEntityModifier(mmfDuck2 = new MoveXModifier(10, 300,
					630));
			mmfDuck2.addModifierListener(this);
			asDuck.stopAnimation(1);
		}
		if (pModifier == mmfDuck2) {
			asDuck.registerEntityModifier(mmfDuck1 = new MoveXModifier(10, 630,
					300));
			mmfDuck1.addModifierListener(this);
			asDuck.stopAnimation(0);
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

	}

	@Override
	public String getNameCount() {
		// return Logtime.PAGE_TOP;
		return getResources().getString(R.string.namescreen_top);
	}

	// -------------------------------------------------------
	// -------------Date 20130306-----------------------------
	// -------------------------------------------------------
	// fix by: sound is lost when onResume

	private void createSound() {
		if (mp == null) {
			mp = MediaPlayer.create(this, R.raw.opening);
			mp.setVolume(volume, volume);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setLooping(true);
			mp.start();
		}
	}

	// -------------------
	// 20130329
	// fixbug galaxy young have a crash with back press
	// -------------------
	private boolean canBack = false;

	@Override
	public void onBackPressed() {
		if (webView != null && webView.getVisibility() == View.VISIBLE) {
			if (close != null) {
				// close.onTouchEvent(MotionEvent.ACTION_DOWN);
				closeWeb();
				return;
			}
		}
		if (canBack) {
			super.onBackPressed();
		}
	}

	private void closeWeb() {
		if (logtimeForWeb != null) {
			logtimeForWeb.Logend(logWEBD);
			long time = logtimeForWeb.getTimeTotal();

			if (time > 0) {
				String category = logWEBD;
				String name = "";

				category = getResources().getString(
						R.string.namescreen_Access_Count);
				name = getResources().getString(R.string.namescreen_Play_time);
				String label = category;
				gauTils.sendTiming(time, category, name, label);
			}
		}

		logtimeForWeb = null;
		gauTils.sendView(getNameCount());

		close.setVisibility(View.INVISIBLE);
		webView.setVisibility(View.INVISIBLE);
		webView.loadUrl("file://" + memory.getPathFileInternalMemory()
				+ "webview/index.html");
	}

	private boolean isDownloaded() {
		return new File(memory.getPathFileInternalMemory() + "/"
				+ "a_otf_jun501pro_bold.otf").exists();
	}
}