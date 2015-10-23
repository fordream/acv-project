package jp.co.xing.utaehon03.basegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jp.co.xing.utaehon03.util.ActivityUtils;

import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.input.sensor.acceleration.AccelerationSensorOptions;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.sensor.location.ILocationListener;
import org.andengine.input.sensor.location.LocationSensorOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationSensorOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.shader.ShaderProgramManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.view.IRendererListener;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.IGameInterface;
import org.andengine.util.Constants;
import org.andengine.util.call.Callback;
import org.andengine.util.debug.Debug;
import org.andengine.util.progress.IProgressListener;
import org.andengine.util.progress.ProgressCallable;
import org.andengine.util.system.SystemUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public abstract class BaseGameFragment extends Fragment implements
		IGameInterface, IRendererListener {
	// ===========================================================
	// Class Inner
	// ===========================================================

	public final class BuildConfig {
		public final static boolean DEBUG = true;
		public final static boolean TEST_VERSION = true;
	}
	
	public static boolean WINDOW_FOCUS_CHANGE = true;

	// ===========================================================
	// Fields GENERAL
	// ===========================================================

	protected Engine mEngine;

	private WakeLock mWakeLock;

	protected RenderSurfaceView mRenderSurfaceView;

	private boolean mGamePaused;
	private boolean mGameCreated;
	private boolean mCreateGameCalled;
	private boolean mOnReloadResourcesScheduled;

	// ===========================================================
	// Fields Game for JOYSOUND VOL3
	// ===========================================================

	protected SharedPreferences shareApplicationData;
	protected String songName;

	protected AssetManager pAssetManager;
	protected static final int CAMERA_WIDTH = 960;
	protected static final int CAMERA_HEIGHT = 640;
	protected Scene mScene;
	protected Camera mCamera;
	protected Sprite[] sprGimmic;
	protected boolean[] isTouchGimmic = { true, true, true };
	protected boolean isLoadingComplete = false;
	private ImageView mImageViewLoading;
	protected RelativeLayout relativeLayout;

	// ===========================================================
	// Constructors
	// ===========================================================

	@Override
	public void onCreate(final Bundle pSavedInstanceState) {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onCreate"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		shareApplicationData = getActivity().getSharedPreferences(
				"application_data", 1);
		songName = shareApplicationData.getString("song_name", "")
				.toLowerCase();

		super.onCreate(pSavedInstanceState);

		this.mGamePaused = true;

		this.mEngine = this.onCreateEngine(this.onCreateEngineOptions());

		this.applyEngineOptions();
	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new Engine(pEngineOptions);
	}

	@Override
	public synchronized void onSurfaceCreated(final GLState pGLState) {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onSurfaceCreated"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		if (this.mGameCreated) {
			this.onReloadResources();

			if (this.mGamePaused && this.mGameCreated) {
				this.onResumeGame();
			}
		} else {
			if (this.mCreateGameCalled) {
				this.mOnReloadResourcesScheduled = true;
			} else {
				this.mCreateGameCalled = true;
				this.onCreateGame();
			}
		}
	}

	@Override
	public synchronized void onSurfaceChanged(final GLState pGLState,
			final int pWidth, final int pHeight) {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName()
					+ ".onSurfaceChanged(Width=" + pWidth + ",  Height="
					+ pHeight + ")" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");
		}
	}

	protected synchronized void onCreateGame() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onCreateGame"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		final OnPopulateSceneCallback onPopulateSceneCallback = new OnPopulateSceneCallback() {
			@Override
			public void onPopulateSceneFinished() {
				try {
					if (BuildConfig.DEBUG) {
						Debug.d(BaseGameFragment.this.getClass()
								.getSimpleName()
								+ ".onGameCreated"
								+ " @(Thread: '"
								+ Thread.currentThread().getName() + "')");
					}

					BaseGameFragment.this.onGameCreated();
				} catch (final Throwable pThrowable) {
					Debug.e(BaseGameFragment.this.getClass().getSimpleName()
							+ ".onGameCreated failed." + " @(Thread: '"
							+ Thread.currentThread().getName() + "')",
							pThrowable);
				}

				BaseGameFragment.this.callGameResumedOnUIThread();
			}
		};

		final OnCreateSceneCallback onCreateSceneCallback = new OnCreateSceneCallback() {
			@Override
			public void onCreateSceneFinished(final Scene pScene) {
				BaseGameFragment.this.mEngine.setScene(pScene);

				try {
					if (BuildConfig.DEBUG) {
						Debug.d(BaseGameFragment.this.getClass()
								.getSimpleName()
								+ ".onPopulateScene"
								+ " @(Thread: '"
								+ Thread.currentThread().getName() + "')");
					}

					BaseGameFragment.this.onPopulateScene(pScene,
							onPopulateSceneCallback);
				} catch (final Throwable pThrowable) {
					Debug.e(BaseGameFragment.this.getClass().getSimpleName()
							+ ".onPopulateScene failed." + " @(Thread: '"
							+ Thread.currentThread().getName() + "')",
							pThrowable);
				}
			}
		};

		final OnCreateResourcesCallback onCreateResourcesCallback = new OnCreateResourcesCallback() {
			@Override
			public void onCreateResourcesFinished() {
				try {
					if (BuildConfig.DEBUG) {
						Debug.d(BaseGameFragment.this.getClass()
								.getSimpleName()
								+ ".onCreateScene"
								+ " @(Thread: '"
								+ Thread.currentThread().getName() + "')");
					}

					BaseGameFragment.this.onCreateScene(onCreateSceneCallback);
				} catch (final Throwable pThrowable) {
					Debug.e(BaseGameFragment.this.getClass().getSimpleName()
							+ ".onCreateScene failed." + " @(Thread: '"
							+ Thread.currentThread().getName() + "')",
							pThrowable);
				}
			}
		};

		try {
			if (BuildConfig.DEBUG) {
				Debug.d(this.getClass().getSimpleName() + ".onCreateResources"
						+ " @(Thread: '" + Thread.currentThread().getName()
						+ "')");
			}

			this.onCreateResources(onCreateResourcesCallback);
		} catch (final Throwable pThrowable) {
			Debug.e(this.getClass().getSimpleName() + ".onCreateGame failed."
					+ " @(Thread: '" + Thread.currentThread().getName() + "')",
					pThrowable);
		}
	}

	@Override
	public synchronized void onResume() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onResume"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		super.onResume();

		this.acquireWakeLock();
		this.mRenderSurfaceView.onResume();
		if ((this.mGamePaused) && (this.mGameCreated)) {
			onResumeGame();
		}
	}

	@Override
	public synchronized void onResumeGame() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onResumeGame"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		this.mEngine.start();

		this.mGamePaused = false;
	}

	@Override
	public void onReloadResources() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onReloadResources"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		this.mEngine.onReloadResources();
	}

	@Override
	public void onPause() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onPause"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		super.onPause();

		this.mRenderSurfaceView.onPause();
		this.releaseWakeLock();

		if (!this.mGamePaused) {
			this.onPauseGame();
		}
	}

	@Override
	public synchronized void onPauseGame() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onPauseGame"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		this.mGamePaused = true;

		this.mEngine.stop();
	}

	@Override
	public void onDestroy() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onDestroy"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		super.onDestroy();

		this.mEngine.onDestroy();

		try {
			this.onDestroyResources();
		} catch (final Throwable pThrowable) {
			Debug.e(this.getClass().getSimpleName()
					+ ".onDestroyResources failed." + " @(Thread: '"
					+ Thread.currentThread().getName() + "')", pThrowable);
		}

		this.onGameDestroyed();

		this.mEngine = null;
	}

	@Override
	public void onDestroyResources() throws Exception {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onDestroyResources"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		if (this.mEngine.getEngineOptions().getAudioOptions().needsMusic()) {
			this.getMusicManager().releaseAll();
		}

		if (this.mEngine.getEngineOptions().getAudioOptions().needsSound()) {
			this.getSoundManager().releaseAll();
		}
	}

	@Override
	public synchronized void onGameDestroyed() {
		if (BuildConfig.DEBUG) {
			Debug.d(this.getClass().getSimpleName() + ".onGameDestroyed"
					+ " @(Thread: '" + Thread.currentThread().getName() + "')");
		}

		this.mGameCreated = false;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public Engine getEngine() {
		return this.mEngine;
	}

	public boolean isGamePaused() {
		return this.mGamePaused;
	}

	public boolean isGameRunning() {
		return !this.mGamePaused;
	}

	public boolean isGameLoaded() {
		return this.mGameCreated;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return this.mEngine.getVertexBufferObjectManager();
	}

	public TextureManager getTextureManager() {
		return this.mEngine.getTextureManager();
	}

	public FontManager getFontManager() {
		return this.mEngine.getFontManager();
	}

	public ShaderProgramManager getShaderProgramManager() {
		return this.mEngine.getShaderProgramManager();
	}

	public SoundManager getSoundManager() {
		return this.mEngine.getSoundManager();
	}

	public MusicManager getMusicManager() {
		return this.mEngine.getMusicManager();
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	private void callGameResumedOnUIThread() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				BaseGameFragment.this.onResumeGame();
			}
		});
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		return createView(inflater, container, savedInstanceState);
	}

	protected View createView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		
		relativeLayout = new RelativeLayout(getActivity());
        final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(
        		FrameLayout.LayoutParams.FILL_PARENT,FrameLayout.LayoutParams.FILL_PARENT);
        
        // Create loading
        RelativeLayout mRelativeLayoutLoad = new RelativeLayout(getActivity());

        
        this.mRenderSurfaceView = new RenderSurfaceView(getActivity());
        this.mRenderSurfaceView.setEGLConfigChooser(false);
        this.mRenderSurfaceView.setRenderer(this.mEngine,this);
        
        final RelativeLayout.LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(createSurfaceViewLayoutParams());
        surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
 
        relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
        relativeLayout.addView(mRelativeLayoutLoad, relativeLayoutLayoutParams);
        
        mImageViewLoading = new ImageView(getActivity());
        mRelativeLayoutLoad.addView(mImageViewLoading, surfaceViewLayoutParams);
		
		return relativeLayout;
	}

	public void runOnUpdateThread(final Runnable pRunnable) {
		this.mEngine.runOnUpdateThread(pRunnable);
	}

	public void runOnUpdateThread(final Runnable pRunnable,
			final boolean pOnlyWhenEngineRunning) {
		this.mEngine.runOnUpdateThread(pRunnable, pOnlyWhenEngineRunning);
	}

	private void acquireWakeLock() {
		this.acquireWakeLock(this.mEngine.getEngineOptions()
				.getWakeLockOptions());
	}

	private void acquireWakeLock(final WakeLockOptions pWakeLockOptions) {
		if (pWakeLockOptions == WakeLockOptions.SCREEN_ON) {
			ActivityUtils.keepScreenOn(getActivity());
		} else {
			final PowerManager pm = (PowerManager) getActivity()
					.getSystemService(Context.POWER_SERVICE);
			this.mWakeLock = pm.newWakeLock(pWakeLockOptions.getFlag()
					| PowerManager.ON_AFTER_RELEASE, Constants.DEBUGTAG);
			try {
				this.mWakeLock.acquire();
			} catch (final SecurityException pSecurityException) {
				Debug.e("You have to add\n\t<uses-permission android:name=\"android.permission.WAKE_LOCK\"/>\nto your AndroidManifest.xml !",
						pSecurityException);
			}
		}
	}

	private void releaseWakeLock() {
		if (this.mWakeLock != null && this.mWakeLock.isHeld()) {
			this.mWakeLock.release();
		}
	}

	private void applyEngineOptions() {
		final EngineOptions engineOptions = this.mEngine.getEngineOptions();

		if (engineOptions.isFullscreen()) {
			// ActivityUtils.requestFullscreen(getActivity());
		}

		if (engineOptions.getAudioOptions().needsMusic()
				|| engineOptions.getAudioOptions().needsSound()) {
			getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		}

		switch (engineOptions.getScreenOrientation()) {
		case LANDSCAPE_FIXED:
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case LANDSCAPE_SENSOR:
			if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				Debug.w(ScreenOrientation.class.getSimpleName() + "."
						+ ScreenOrientation.LANDSCAPE_SENSOR
						+ " is not supported on this device. Falling back to "
						+ ScreenOrientation.class.getSimpleName() + "."
						+ ScreenOrientation.LANDSCAPE_FIXED);
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			break;
		case PORTRAIT_FIXED:
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case PORTRAIT_SENSOR:
			if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				Debug.w(ScreenOrientation.class.getSimpleName() + "."
						+ ScreenOrientation.PORTRAIT_SENSOR
						+ " is not supported on this device. Falling back to "
						+ ScreenOrientation.class.getSimpleName() + "."
						+ ScreenOrientation.PORTRAIT_FIXED);
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			break;
		}
	}

	protected static LayoutParams createSurfaceViewLayoutParams() {
		final LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}

	protected void enableVibrator() {
		this.mEngine.enableVibrator(getActivity());
	}

	protected void enableLocationSensor(
			final ILocationListener pLocationListener,
			final LocationSensorOptions pLocationSensorOptions) {
		this.mEngine.enableLocationSensor(getActivity(), pLocationListener,
				pLocationSensorOptions);
	}

	protected void disableLocationSensor() {
		this.mEngine.disableLocationSensor(getActivity());
	}

	protected boolean enableAccelerationSensor(
			final IAccelerationListener pAccelerationListener) {
		return this.mEngine.enableAccelerationSensor(getActivity(),
				pAccelerationListener);
	}

	protected boolean enableAccelerationSensor(
			final IAccelerationListener pAccelerationListener,
			final AccelerationSensorOptions pAccelerationSensorOptions) {
		return this.mEngine.enableAccelerationSensor(getActivity(),
				pAccelerationListener, pAccelerationSensorOptions);
	}

	protected boolean disableAccelerationSensor() {
		return this.mEngine.disableAccelerationSensor(getActivity());
	}

	protected boolean enableOrientationSensor(
			final IOrientationListener pOrientationListener) {
		return this.mEngine.enableOrientationSensor(getActivity(),
				pOrientationListener);
	}

	protected boolean enableOrientationSensor(
			final IOrientationListener pOrientationListener,
			final OrientationSensorOptions pLocationSensorOptions) {
		return this.mEngine.enableOrientationSensor(getActivity(),
				pOrientationListener, pLocationSensorOptions);
	}

	protected boolean disableOrientationSensor() {
		return this.mEngine.disableOrientationSensor(getActivity());
	}

	// ===========================================================
	// Function abstract for JOYSOUND VOL3
	// ===========================================================
	
	public void onCreateResources() {
		// nothing
	};
	
	protected abstract void loadKaraokeResources();

	protected abstract void loadKaraokeSound();

	protected abstract void loadKaraokeScene();

	public abstract void combineGimmic3WithAction();

	protected void loadKaraokeComplete() {
	}

	// ===========================================================
	// Function for JOYSOUND VOL3
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		pAssetManager = getActivity().getAssets();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			final OnCreateResourcesCallback paramOnCreateResourcesCallback)
			throws Exception {
		
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ActivityUtils.doLoadingAsync(getActivity(),
						"Loading Resources...",
						mImageViewLoading,
						new ProgressCallable<Void>() {
							@Override
							public Void call(
									final IProgressListener pProgressListener)
									throws Exception {
								BaseGameFragment.this
										.onCreateResources(pProgressListener);
								paramOnCreateResourcesCallback
										.onCreateResourcesFinished();

								return null;
							}
						}, new Callback<Void>() {
							@Override
							public void onCallback(final Void pCallbackValue) {
								/* Nothing. */
							}
						});
			}
		});

	}

	@Override
	public void onCreateScene(final OnCreateSceneCallback paramOnCreateSceneCallback)
			throws Exception {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ActivityUtils.doLoadingAsync(getActivity(),
						"Loading Scene...", null,
						new ProgressCallable<Void>() {
							@Override
							public Void call(
									final IProgressListener pProgressListener)
									throws Exception {
								final Scene scene = BaseGameFragment.this
										.onCreateScene(pProgressListener);

								pProgressListener.onProgressChanged(100);

								paramOnCreateSceneCallback.onCreateSceneFinished(scene);

								return null;
							}
						}, new Callback<Void>() {
							@Override
							public void onCallback(final Void pCallbackValue) {
								/* Nothing. */
							}
						});
			}
		});
	}

	@Override
	public void onPopulateScene(Scene paramScene,
			final OnPopulateSceneCallback paramOnPopulateSceneCallback)
			throws Exception {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ActivityUtils.doLoadingFinishAsync(getActivity(),
						"Loading Scene...", mImageViewLoading,
						new ProgressCallable<Void>() {
							@Override
							public Void call(
									final IProgressListener pProgressListener)
									throws Exception {
								loadKaraokeSound();
								pProgressListener.onProgressChanged(100);

								paramOnPopulateSceneCallback.onPopulateSceneFinished();

								return null;
							}
						}, new Callback<Void>() {
							@Override
							public void onCallback(final Void pCallbackValue) {
								/* Nothing. */
							}
						});
			}
		});
	}

	public void onCreateResources(final IProgressListener pProgressListener) {
		onCreateResources();
		loadKaraokeResources();
	}

	protected Scene onCreateScene(final IProgressListener pProgressListener) {
		this.getEngine().registerUpdateHandler(new FPSCounter());
		loadKaraokeScene();
		return mScene;
	}

	@Override
	public synchronized void onGameCreated() {
		this.mGameCreated = true;
		if (this.mOnReloadResourcesScheduled) {
			this.mOnReloadResourcesScheduled = false;
			try {
				this.onReloadResources();
			} catch (final Throwable pThrowable) {
				Debug.e(this.getClass().getSimpleName()
						+ ".onReloadResources failed." + " @(Thread: '"
						+ Thread.currentThread().getName() + "')", pThrowable);
			}
		}
		loadKaraokeComplete();
	}

	public void setTouchGimmic3(boolean isTouch) {
		this.isTouchGimmic[2] = isTouch;
	}
	
	public void setTouchGimmic(int index,boolean isTouch){
		this.isTouchGimmic[index] = isTouch;
	}

	public void setGimmicBringToFront() {
		if (this.mScene != null) {

			int zIndex = this.mScene.getChild(mScene.getLastChild().getTag()).getZIndex();
			zIndex++;
			this.sprGimmic[0].setZIndex(zIndex++);
			this.sprGimmic[1].setZIndex(zIndex++);
			this.sprGimmic[2].setZIndex(zIndex++);
			this.mScene.sortChildren();
		}
	}
	
	protected Drawable getDrawableFromSD(String name, String pathDir) {
		Drawable draTmp = null;
		InputStream is = null;
		Bitmap image = null;
		if (BuildConfig.TEST_VERSION) {
			try {
				is = this.getResources().getAssets().open(pathDir + name);
			} catch (IOException e) {
				return null;
			}
		} else {
			String path = Environment.getExternalStorageDirectory()
					+ "/Android/data/" + getActivity().getPackageName()
					+ "/files/" + songName + "/gfx/" + name + ".nomedia";
			try {
				is = new FileInputStream(new File(path));
				byte[] salt = name.getBytes();
				is.read(salt);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		image = BitmapFactory.decodeStream(is);
		draTmp = new BitmapDrawable(image);
		return draTmp;
	}

	public ITextureRegion loadImageResourceFromSD(String nameResource,
			BitmapTextureAtlas pBitmapTextureAtlas, int pTexturePositionX,
			int pTexturePositionY) {

		if (BuildConfig.TEST_VERSION) {
			return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
					pBitmapTextureAtlas, getActivity(), nameResource,
					pTexturePositionX, pTexturePositionY);
		} else {
			String path = Environment.getExternalStorageDirectory()
					+ "/Android/data/" + getActivity().getPackageName()
					+ "/files/" + songName + "/gfx/" + nameResource
					+ ".nomedia";
			File tmp = new File(path);
			if (!tmp.exists()) {
				return null;
			}
			return BitmapTextureAtlasTextureRegionFactory.createFromSource(
					pBitmapTextureAtlas,
					FileBitmapTextureAtlasSource.create(tmp),
					pTexturePositionX, pTexturePositionY);
		}

	}

	public TiledTextureRegion loadImageResourceTiledFromSD(String nameResource,
			BitmapTextureAtlas pBitmapTextureAtlas, int pTexturePositionX,
			int pTexturePositionY, int numColumns, int numRows) {
		if (BuildConfig.TEST_VERSION) {
			return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					pBitmapTextureAtlas, getActivity(), nameResource,
					pTexturePositionX, pTexturePositionY, numColumns, numRows);
		} else {
			String path = Environment.getExternalStorageDirectory()
					+ "/Android/data/" + getActivity().getPackageName()
					+ "/files/" + songName + "/gfx/" + nameResource
					+ ".nomedia";
			File tmp = new File(path);
			if (!tmp.exists()) {
				return null;
			}
			return BitmapTextureAtlasTextureRegionFactory
					.createTiledFromSource(pBitmapTextureAtlas,
							FileBitmapTextureAtlasSource.create(tmp),
							pTexturePositionX, pTexturePositionY, numColumns,
							numRows);
		}

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

	public Sound loadSoundResourceFromSD(String nameResource) {
		if (BuildConfig.TEST_VERSION) {
			try {
				return SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(), getActivity(),
						nameResource);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			String path = Environment.getExternalStorageDirectory()
					+ "/Android/data/" + getActivity().getPackageName()
					+ "/files/" + songName + "/mfx/" + nameResource
					+ ".nomedia";
			try {
				File tmp = new File(path);
				FileInputStream fis = new FileInputStream(tmp);
				byte[] decodeHeader = tmp.getName().replace(".nomedia", "")
						.getBytes();
				System.gc();
				return SoundFactory
						.createSoundFromFileDescriptor(
								this.mEngine.getSoundManager(), fis.getFD(),
								decodeHeader.length, tmp.length()
										- decodeHeader.length);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public void addGimmicsButton(Scene mScene, String[] sound, int[] image,
			TexturePackTextureRegionLibrary pTexturePackTextureRegionLibrary) {

		ITextureRegion[] ttrGimmic = new ITextureRegion[3];
		sprGimmic = new Sprite[3];
		final Sound[] sndGimmic = new Sound[3];
		int[] start = { 244, 436, 612 };

		for (int i = 0; i < sprGimmic.length; i++) {
			// load sound
			sndGimmic[i] = loadSoundResourceFromSD(sound[i]);

			ttrGimmic[i] = pTexturePackTextureRegionLibrary.get(image[i]);
			final int tmp = i;
			sprGimmic[i] = new Sprite(start[i], 496, ttrGimmic[i],
					this.getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						if (isTouchGimmic[tmp]) {
							if (tmp == 2) {
								combineGimmic3WithAction();
							} else {
								sndGimmic[tmp].play();
							}
							sprGimmic[tmp]
									.registerEntityModifier(new SequenceEntityModifier(
											new ScaleModifier(0.35f, 1, 1.3f),
											new ScaleModifier(0.35f, 1.3f, 1f)));
						}
						return true;

					}
					return super.onAreaTouched(pSceneTouchEvent,
							pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			mScene.registerTouchArea(sprGimmic[i]);
			mScene.attachChild(sprGimmic[i]);
		}

		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
	}

	/**
	 * Method check Touch Point Contains A Rectangle
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
		boolean c = false;
		if (myShape != null) {
			int myShapeX = (int) myShape.getX();
			int myShapeY = (int) myShape.getY();

			int polyX[] = new int[] { myShapeX + x1, myShapeX + x1,
					myShapeX + x2, myShapeX + x2 };
			int polyY[] = new int[] { myShapeY + y1, myShapeY + y2,
					myShapeY + y2, myShapeY + y1 };

			int i, j = 0;
			for (i = 0, j = 3; i < 4; j = i++) {
				if (((polyY[i] > pY) != (polyY[j] > pY))
						&& (pX < (polyX[j] - polyX[i]) * (pY - polyY[i])
								/ (polyY[j] - polyY[i]) + polyX[i]))
					c = !c;
			}
		}
		return c;
	}

	public boolean checkContainsPolygon(RectangularShape myShape,
			float[][] arrPointer, int numberPointer, float pX, float pY) {

		float myShapeX = myShape.getX();
		float myShapeY = myShape.getY();
		float arrPoiterTemp[][] = new float[2][numberPointer + 1];

		for (int j = 0; j <= 1; j++) {
			for (int i = 0; i < numberPointer; i++) {
				if (j == 0) {
					arrPoiterTemp[j][i] = arrPointer[j][i] + myShapeX;
				}
				if (j == 1) {
					arrPoiterTemp[j][i] = arrPointer[j][i] + myShapeY;
				}
			}
		}

		boolean c = false;
		int i, j = 0;
		for (i = 0, j = numberPointer - 1; i < numberPointer; j = i++) {
			if (((arrPoiterTemp[1][i] > pY) != (arrPoiterTemp[1][j] > pY))
					&& (pX < (arrPoiterTemp[0][j] - arrPoiterTemp[0][i])
							* (pY - arrPoiterTemp[1][i])
							/ (arrPoiterTemp[1][j] - arrPoiterTemp[1][i])
							+ arrPoiterTemp[0][i]))
				c = !c;
		}

		return c;
	}

	public void playSoundDelay(final Sound pSound, final float timeDelay) {
		this.mEngine.registerUpdateHandler(new TimerHandler(timeDelay,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						pSound.play();
					}
				}));
	}
	
	public void nextTileAnimatedSprite(final AnimatedSprite pAnimatedSprite) {
		int index = pAnimatedSprite.getCurrentTileIndex();
		if (index >= pAnimatedSprite.getTileCount() - 1) {
			index = 0;
		} else {
			index++;
		}
		pAnimatedSprite.setCurrentTileIndex(index);
	}
}