package com.xing.joy.play;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.CommonUtils;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xing.joy.common.CoreActivity;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.interfaces.IPlaying;
import com.xing.joy.others.SelectSong;
import com.xing.joy.service.LyricService;

public abstract class PlayMovie extends CoreActivity implements OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback, IPlaying, AnimationListener {
	ImageView imgBackgroundMovie;
	Display currentDisplay;
	Runnable runEraseBackground;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;

	MediaPlayer mediaPlayer;

	/** Intent for start activity. */
	private Intent intent;

	private TextView[] tvLyric = new TextView[9];
	protected RelativeLayout rlMain;

	/** Services play lyric. */
	protected LyricService lyricService;

	private ImageView imgControlLyric;
	protected ImageView[] imgGimmic = new ImageView[3];
	protected String[] strGimmicImage = new String[3];
	protected String[] strGimmicSound = new String[3];

	protected static final int[] MP3_GIMMIC = { 1, 2 };
	protected static final ArrayList<Integer> MP3_GIMMIC_3 = new ArrayList<Integer>();

	protected SoundPool soundPool;
	protected SparseIntArray soundsMap;

	private boolean repeatStatus = false, showLyricStatus = true;
	private Animation aniLyricStart = null, aniLyricShow = null;

	protected String songName, lyricName, typeName, pathMovie, randomKey;

	public final static String LOGTAG = "CUSTOM_VIDEO_PLAYER";

	protected boolean mIsLyricReady = false;
	protected boolean mIsVideoSizeKnown = false;
	protected boolean mIsVideoReadyToBePlayed = false;
	protected boolean mIsGimmicReadyToBePlayed = false;
	protected AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.play_movie);
		super.onCreate(savedInstanceState);

		randomKey = "random_" + new Random().nextLong();
		songName = appData.getStringData(IDataActions.SONG_NAME).toLowerCase();
		typeName = appData.getStringData(IDataActions.TYPE_NAME);

		if (appData.getStringData(IDataActions.PACKAGE_NAME).equalsIgnoreCase("")) {
			pathMovie = memory.getPathFileInternalMemory();
		} else {
			pathMovie = memory.getPathFileExternalMemory();
		}

		if (typeName.equalsIgnoreCase("douga_clicked")) {
			lyricName = songName + "_movie.lrc";
		}

		rlMain = (RelativeLayout) findViewById(R.id.play_movie);

		imgBackgroundMovie = (ImageView) findViewById(R.id.imageGround);
		resizeView(imgBackgroundMovie, 0, 0, 0, 0);

		// Initial sound pool
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		soundsMap = new SparseIntArray();

		surfaceView = (SurfaceView) this.findViewById(R.id.SurfaceView);
		resizeView(surfaceView, 0, 0, 0, 0);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		currentDisplay = getWindowManager().getDefaultDisplay();

		// Set Movie to play
		createLyric();
		setBackgroundMovie();
		try {
			createGimmic();
		} catch (FileNotFoundException e) {
			errorLoadResource();
		} catch (Exception e) {
			e.printStackTrace();

		}
		initializationLyric();
		addLyricControl();
		addBackButton(R.id.play_movie);

	}

	@Override
	public void onResume() {
		super.onResume();
		setBackgroundMovie();
	}

	@Override
	public void onWindowFocusChanged(boolean pHasWindowFocus) {
		if (pHasWindowFocus) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (mediaPlayer == null) {
						initializationMediaPlayer("");
					}
				}
			}, 250);
		}
		super.onWindowFocusChanged(pHasWindowFocus);
	}

	protected void startVideoPlayback() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			return;
		}
		if (lyricService != null)
			lyricService.startLyric(LyricService.BROADCAST_ACTION_2, randomKey);
		if (mediaPlayer != null)
			mediaPlayer.start();

		if (tvLyric[0] != null) {
			int sizeLyric = tvLyric[0].getHeight() / 2;
			if (sizeLyric < 20) {
				sizeLyric = 20;
			}
			if (sizeLyric > 50) {
				sizeLyric = 50;
			}

			for (int i = 0; i < tvLyric.length; i++) {
				if (tvLyric[i] != null) {
					tvLyric[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeLyric);
					tvLyric[i].setVisibility(View.INVISIBLE);
				}
			}
		}
		showLyricStatus = false;
		if (imgBackgroundMovie != null)
			imgBackgroundMovie.postDelayed(runEraseBackground = new Runnable() {
				public void run() {
					if (imgBackgroundMovie != null)
						imgBackgroundMovie.setBackgroundResource(0);
				}
			}, 500);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setPathLyric();
		if (mediaPlayer == null) {
			try {
				initializationMediaPlayer("");
			} catch (Exception e) {
				e.printStackTrace();
				errorLoadResource();
			}
		}

	}

	public abstract void setPathLyric();

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		releaseMemory();
		onBackPressed();
	}

	@Override
	public boolean onError(MediaPlayer mp, int whatError, int extra) {

		if (whatError == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
		} else if (whatError == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
		}

		return false;
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int whatInfo, int extra) {
		if (whatInfo == MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING) {
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_UNKNOWN) {
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
		}
		return false;
	}

	@Override
	abstract public void onPrepared(MediaPlayer mp);

	@Override
	public void onSeekComplete(MediaPlayer mp) {
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if (width == 0 || height == 0) {
			return;
		}
		mIsVideoSizeKnown = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown && mIsLyricReady && mIsGimmicReadyToBePlayed) {
			startVideoPlayback();
		}
	}

	@SuppressWarnings("resource")
	public FileDescriptor getFileDecriptor(Context context, String videoResName) {
		try {
			File tempFile = new File(videoResName);
			FileInputStream fis = new FileInputStream(tempFile);
			return fis.getFD();
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			soundPool.stop(soundsMap.get(1));
			soundPool.stop(soundsMap.get(2));
			soundPool.stop(soundsMap.get(3));
			soundPool.unload(soundsMap.get(3));
			MP3_GIMMIC_3.clear();
			soundPool.release();
		} catch (Exception e) {
		}
		if (lyricService != null) {
			lyricService.stopLyric();
		}
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if (imgBackgroundMovie != null) {
			imgBackgroundMovie.removeCallbacks(runEraseBackground);
		}
		doCleanUp();
		for (int i = 0; i < tvLyric.length; i++) {
			if (tvLyric[i] != null) {
				tvLyric[i].setText("");
			}
		}
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
	}

	private void doCleanUp() {
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMemory();
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if (imgBackgroundMovie != null) {
			imgBackgroundMovie.removeCallbacks(runEraseBackground);
			imgBackgroundMovie = null;
		}
		currentDisplay = null;
		surfaceView = null;
		surfaceHolder = null;
		unregisterReceiver(broadcastReceiver);
		unbindService(serviceConncetion);
		stopService(intent);
	}

	@Override
	public void onBackPressed() {
		releaseMemory();
		super.onBackPressed();
		CommonUtils.startNewActivity(this, SelectSong.class, "");
	}

	@Override
	public void releaseMemory() {
		super.releaseMemory();
		for (int i = 0; i < imgGimmic.length; i++) {
			setNullViewDrawable(imgGimmic[i]);
		}
		for (int i = 0; i < tvLyric.length; i++) {
			if (tvLyric[i] != null) {
				tvLyric[i].setText("");
				tvLyric[i] = null;
			}
		}
		if (imgControlLyric != null) {
			setNullViewDrawable(imgControlLyric);
		}
	}

	// Dieu chinh va set lai volume cho ung dung khi bam nut -/+ volume
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
				float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				volume = streamVolumeCurrent / streamVolumeMax;
				mediaPlayer.setVolume(volume, volume);
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
				streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				volume = streamVolumeCurrent / streamVolumeMax;
				mediaPlayer.setVolume(volume, volume);
				return true;
			default:
				return super.onKeyDown(keyCode, event);
			}
		} catch (Exception exception) {
			return true;
		}
	}

	abstract void setBackgroundMovie();

	@Override
	public void createLyric() {
		// Create new text view for lyric
		Typeface font = null;
		String path = this.getFilesDir().toString() + "/a_otf_jun501pro_bold.otf";
		try {
			if (new File(path).exists()) {
				font = Typeface.createFromFile(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int aSize = 2;
		int bSize = 1;
		int leftText = Math.round(30 * calResize.getRatioResizeWidth()) + calResize.getHemBlackWidth();
		int topText = Math.round(400 * calResize.getRatioResizeHeight()) + calResize.getHemBlackHeight();
		if (isTablet()) {
			topText = Math.round(415 * calResize.getRatioResizeHeight()) + calResize.getHemBlackHeight();
		}
		for (int i = 0; i < tvLyric.length; i++) {
			tvLyric[i] = new TextView(this);
			RelativeLayout.LayoutParams lyricLayout = new RelativeLayout.LayoutParams(Math.round(900 * calResize.getRatioResizeWidth()), Math.round(80 * calResize.getRatioResizeHeight()));
			if (font != null) {
				tvLyric[i].setTypeface(font);
			}
			switch (i) {
			case 0:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText, topText + aSize, 0, 0);
				break;
			case 1:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText, topText - aSize, 0, 0);
				break;
			case 2:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText + aSize, topText, 0, 0);
				break;
			case 3:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText - aSize, topText, 0, 0);
				break;
			case 4:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText + bSize, topText + bSize, 0, 0);
				break;
			case 5:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText - bSize, topText + bSize, 0, 0);
				break;
			case 6:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText - bSize, topText - bSize, 0, 0);
				break;
			case 7:
				tvLyric[i].setTextColor(Color.BLACK);
				lyricLayout.setMargins(leftText + bSize, topText - bSize, 0, 0);
				break;
			case 8:
				tvLyric[i].setTextColor(Color.WHITE);
				lyricLayout.setMargins(leftText, topText, 0, 0);
				break;
			}
			tvLyric[i].setLayoutParams(lyricLayout);
			rlMain.addView(tvLyric[i]);
			if (typeName.equalsIgnoreCase("douga_clicked")) {
				tvLyric[i].setVisibility(View.INVISIBLE);
				showLyricStatus = false;
			}
		}
	}

	abstract public void parseFileMovieInfo();

	abstract public void createGimmic() throws Exception;

	public void playSound(int sound) {
		soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, 1.0f);
	}

	@Override
	public void initializationLyric() {
		intent = new Intent(this, LyricService.class);
		startService(intent);
		Intent bindIntent = new Intent(this, LyricService.class);
		bindService(bindIntent, serviceConncetion, Context.BIND_AUTO_CREATE);
		registerReceiver(broadcastReceiver, new IntentFilter(LyricService.BROADCAST_ACTION_2));
	}

	@Override
	public void addLyricControl() {

		imgControlLyric = (ImageView) findViewById(R.id.control_lyric);
		imgControlLyric.setBackgroundResource(R.drawable.cb_05_adr_list);
		resizeView(imgControlLyric, 850, 20, 0, 0);
		aniLyricStart = AnimationUtils.loadAnimation(this, R.anim.btnlyric_alpha);
		aniLyricStart.setAnimationListener(this);
		aniLyricShow = AnimationUtils.loadAnimation(this, R.anim.lyric_alpha);
		aniLyricShow.setAnimationListener(this);
		imgControlLyric.startAnimation(aniLyricStart);
		imgControlLyric.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (repeatStatus) {
							repeatStatus = false;
							lyricService.startLyric(LyricService.BROADCAST_ACTION_2, randomKey);
							mediaPlayer.start();
							imgControlLyric.setBackgroundResource(R.drawable.cb_05_adr_list);
							imgControlLyric.getBackground().setAlpha(255);
							imgControlLyric.startAnimation(aniLyricStart);
						} else {
							if (showLyricStatus) {
								for (int i = 0; i < tvLyric.length; i++) {
									if (tvLyric[i] != null) {
										tvLyric[i].setVisibility(View.INVISIBLE);
									}
								}
								showLyricStatus = false;
							} else {
								for (int i = 0; i < tvLyric.length; i++) {
									if (tvLyric[i] != null) {
										tvLyric[i].setVisibility(View.VISIBLE);
									}
								}
								showLyricStatus = true;
							}
							imgControlLyric.getBackground().setAlpha(255);
							imgControlLyric.startAnimation(aniLyricShow);
						}
					}
				} catch (Exception exception) {
				}
				return true;
			}
		});
	}

	@Override
	abstract public void initializationMediaPlayer(String pathMusic);

	@Override
	public void lyricPlaying(Intent intent) {
		if (intent.getStringExtra("songname").equalsIgnoreCase(randomKey)) {
			String lyric = intent.getStringExtra("lyric");
			for (int i = 0; i < tvLyric.length; i++) {
				if (tvLyric[i] != null) {
					tvLyric[i].setText(lyric);
				}
			}
		}
	}

	/**
	 * The service connection.
	 * */
	private ServiceConnection serviceConncetion = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			lyricService = ((LyricService.MyBinder) service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			lyricService = null;
		}
	};

	/**
	 * BroadCastReceiver for receive intent. When receive Intent, it update UI.
	 * */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			lyricPlaying(intent);
		}
	};

	@Override
	public void onAnimationEnd(Animation animation) {
		if (imgControlLyric != null && imgControlLyric.getBackground() != null) {
			imgControlLyric.getBackground().setAlpha(85);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	public void errorLoadResource() {
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(this.getString(R.string.error_load_resource_title));
		alertDialog.setMessage(this.getString(R.string.error_load_resource_helper));
		alertDialog.setButton(this.getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PlayMovie.this.getString(R.string.error_load_resource_url_helper)));
				PlayMovie.this.startActivity(browserIntent);
				return;
			}
		});
		alertDialog.setButton2(this.getString(R.string.btn_no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Set intent
				CommonUtils.startNewActivity(PlayMovie.this, SelectSong.class, "");
				alertDialog.dismiss();
				return;
			}
		});
		alertDialog.show();
	}
}