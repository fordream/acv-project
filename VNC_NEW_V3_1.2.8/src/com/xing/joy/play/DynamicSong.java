package com.xing.joy.play;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.ErrorUtils;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xing.joy.common.CoreActivity;
import com.xing.joy.common.MultiDexClassLoader;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.interfaces.IPlaying;
import com.xing.joy.others.SelectSong;
import com.xing.joy.service.LyricService;

public class DynamicSong extends CoreActivity implements AnimationListener, IPlaying {

	/** STATIC */
	public static boolean LOAD_RESOURCE_COMPLETE = false;

	/** Intent for start activity. */
	private Intent intent = null;

	private TextView[] tvLyric = new TextView[9];
	private RelativeLayout rlMain, rlDynamic;

	/** Services play lyric. */
	private LyricService lyricService;

	private MediaPlayer mediaPlayer;

	private ImageView imgControlLyric;

	private boolean repeatStatus = false, showLyricStatus = true, isPlaying = false;
	private Animation aniLyricStart = null, aniLyricShow = null;

	private static String songName, randomKey;

	private String lyricName, typeName, pathSong, pathJarFile, className;

	BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String category = intent.getStringExtra("category");
			if (category != null && !"".equals(category.trim())) {
				gauTils.sendEvent(category, category, category);
				gauTils.sendTiming(1l, category, category, category);
				// gauTils.sendView(category);
				Log.e("LOG SEND GAME END", category);
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		
		this.setContentView(R.layout.dynamic_song);

		LOAD_RESOURCE_COMPLETE = false;
		randomKey = "random_" + new Random().nextLong();
		songName = appData.getStringData(IDataActions.SONG_NAME);
		typeName = appData.getStringData(IDataActions.TYPE_NAME);

		if (appData.getStringData(IDataActions.PACKAGE_NAME).equalsIgnoreCase("")) {
			pathSong = memory.getPathFileInternalMemory() + songName.toLowerCase();
			pathJarFile = memory.getPathFileInternalMemory();
		} else {
			pathSong = memory.getPathFileExternalMemory() + songName.toLowerCase();
			pathJarFile = memory.getPathFileExternalMemory();
		}

		rlMain = (RelativeLayout) findViewById(R.id.main_layout);
		rlDynamic = (RelativeLayout) findViewById(R.id.dynamic_layout);
		resizeView(rlDynamic, 0, 0, 0, 0);

		// Add the APK/JAR/ZIP file's classes to the class loader.
		if (new File(pathJarFile + songName.toLowerCase() + "/" + songName.toLowerCase() + ".jar").exists()) {
			MultiDexClassLoader.getInstance().install(DynamicSong.this, pathJarFile + songName.toLowerCase() + "/" + songName.toLowerCase() + ".jar");
			if (savedInstanceState == null) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

				className = "jp.co.xing.utaehon03.songs." + songName;

				try {
					@SuppressWarnings("unchecked")
					Class<Fragment> fragmentClass = (Class<Fragment>) MultiDexClassLoader.getInstance().loadClass(className);
					Fragment fragment = fragmentClass.newInstance();
					ft = getSupportFragmentManager().beginTransaction();
					ft.add(R.id.dynamic_layout, fragment).commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			createLyric();
		} else {
			ErrorUtils.errorLoadResource(this, songName, appData.getStringData(IDataActions.PACKAGE_NAME));
		}

		songName = songName.toLowerCase();
		if (typeName.equalsIgnoreCase("karaoke_clicked")) {
			lyricName = songName + "_karaoke.lrc";
		}
		if (typeName.equalsIgnoreCase("uta_clicked")) {
			lyricName = songName + "_uta.lrc";
		}
		initializationLyric();
	}

	@Override
	public void onResume() {
		super.onResume();
		initializationMediaPlayer(songName);
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!isPlaying && lyricService != null && mediaPlayer != null && LOAD_RESOURCE_COMPLETE) {
					if (new File(pathSong + "/" + lyricName).exists()) {
						lyricService.setPath(pathSong + "/" + lyricName, "SD");
						lyricService.startLyric(LyricService.BROADCAST_ACTION_1, randomKey);

						mediaPlayer.start();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								int sizeLyric = tvLyric[0].getHeight() / 2;
								if (sizeLyric < 20) {
									sizeLyric = 20;
								}

								if (sizeLyric > 50) {
									sizeLyric = 50;
								}

								for (int i = 0; i < tvLyric.length; i++) {
									tvLyric[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeLyric);
								}

								if (typeName.equalsIgnoreCase("uta_clicked")) {
									showLyricStatus = false;
								} else {
									showLyricStatus = true;
								}

								if (imgControlLyric == null) {
									addLyricControl();
								}
							}
						});
						isPlaying = true;
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ErrorUtils.errorLoadResource(DynamicSong.this, songName, appData.getStringData(IDataActions.PACKAGE_NAME));
							}
						});
					}
					timer.cancel();
				}
			}
		}, 0, 300);

		registerReceiver(broadcastReceiver2, new IntentFilter("jp.co.xing.utaehon03.action"));
	}

	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();

		try {
			unregisterReceiver(broadcastReceiver2);
		} catch (Exception exception) {
		}
	}

	@Override
	public void onWindowFocusChanged(boolean pHasWindowFocus) {
		super.onWindowFocusChanged(pHasWindowFocus);
		BaseGameFragment.WINDOW_FOCUS_CHANGE = pHasWindowFocus;
		if (pHasWindowFocus) {
			isPlaying = false;
		}
		if (imgBack == null) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					addBackButton(R.id.main_layout);
				}
			}, 1000);
		}
	}

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

			default:
				break;
			}
			tvLyric[i].setLayoutParams(lyricLayout);
			rlMain.addView(tvLyric[i]);
		}
	}

	@Override
	public void initializationLyric() {
		intent = new Intent(this, LyricService.class);
		startService(intent);
		Intent bindIntent = new Intent(this, LyricService.class);
		bindService(bindIntent, serviceConncetion, Context.BIND_AUTO_CREATE);
		registerReceiver(broadcastReceiver, new IntentFilter(LyricService.BROADCAST_ACTION_1));
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
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (repeatStatus) {
						repeatStatus = false;
						lyricService.startLyric(LyricService.BROADCAST_ACTION_1, randomKey);
						mediaPlayer.start();
						isPlaying = true;
						imgControlLyric.setBackgroundResource(R.drawable.cb_05_adr_list);
						imgControlLyric.getBackground().setAlpha(255);
						imgControlLyric.startAnimation(aniLyricStart);
					} else {
						if (showLyricStatus) {
							for (int i = 0; i < tvLyric.length; i++) {
								tvLyric[i].setVisibility(View.INVISIBLE);
							}
							showLyricStatus = false;
						} else {
							for (int i = 0; i < tvLyric.length; i++) {
								tvLyric[i].setVisibility(View.VISIBLE);
							}
							showLyricStatus = true;
						}
						imgControlLyric.getBackground().setAlpha(255);
						imgControlLyric.startAnimation(aniLyricShow);
					}
				}
				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		CommonUtils.startNewActivity(this, SelectSong.class, "");
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		for (int i = 0; i < tvLyric.length; i++) {
			if (tvLyric[i] != null) {
				tvLyric[i].setText("   ");
				tvLyric[i].setVisibility(View.VISIBLE);
			}
		}

		if (imgControlLyric != null) {
			imgControlLyric.setBackgroundResource(R.drawable.cb_05_adr_list);
			imgControlLyric.getBackground().setAlpha(85);
		}

		if (lyricService != null) {
			lyricService.stopLyric();
		}

		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}

		// -------------------------------------------------------
		// -------------Date 20130306-----------------------------
		// -------------------------------------------------------
		// fix by: sound is lost when onResume
		isPlaying = false;
		// -------------------------------------------------------
		// -------------Date 20130306 End-----------------------------
		// -------------------------------------------------------
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (lyricService != null) {
			lyricService.stopLyric();
		}
		unregisterReceiver(broadcastReceiver);
		unbindService(serviceConncetion);
		stopService(intent);
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if (imgControlLyric != null) {
			setNullViewDrawable(imgControlLyric);
			imgControlLyric = null;
		}
	}

	@Override
	public void initializationMediaPlayer(String pathMusic) {
		mediaPlayer = new MediaPlayer();
		try {
			String path = pathSong + "/";
			if (typeName.equalsIgnoreCase("karaoke_clicked")) {
				path += songName + "_karaoke.ogg.nomedia";
			}
			if (typeName.equalsIgnoreCase("uta_clicked")) {
				path += songName + "_uta.ogg.nomedia";
			}
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			byte[] decodeHeader = file.getName().replace(".nomedia", "").getBytes();
			mediaPlayer.setDataSource(getFileDecriptor(DynamicSong.this, path), decodeHeader.length, file.length() - decodeHeader.length);
			mediaPlayer.prepare();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			setMediaPlayerListener();
		} catch (FileNotFoundException e) {
			ErrorUtils.errorLoadResource(this, songName, appData.getStringData(IDataActions.PACKAGE_NAME));
		} catch (Exception e) {
			e.printStackTrace();
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

	public void setMediaPlayerListener() {
		if (mediaPlayer != null) {
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					for (int i = 0; i < tvLyric.length; i++) {
						tvLyric[i].setText("");
					}
					lyricService.stopLyric();
					if (imgControlLyric != null) {
						imgControlLyric.setBackgroundResource(R.drawable.adr_repeat_btn);
					}
					isPlaying = false;
					repeatStatus = true;
				}
			});
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
	public void lyricPlaying(Intent intent) {
		if (intent.getStringExtra("songname").equalsIgnoreCase(randomKey)) {
			String lyricUpdate = intent.getStringExtra("lyric");
			for (int i = 0; i < tvLyric.length; i++) {
				tvLyric[i].setText(lyricUpdate);
				if (!showLyricStatus) {
					tvLyric[i].setVisibility(View.INVISIBLE);
				}
			}
		}
	}

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

	@Override
	public String getNameCount() {
		String _end = getResources().getString(R.string._uta);// "_uta";
		if (appData.getStringData(IDataActions.TYPE_NAME).equalsIgnoreCase("karaoke_clicked")) {
			// _end = "_karaoke";
			_end = getResources().getString(R.string._karaokie);

		}
		String name = appData.getStringData(IDataActions.SONG_NAME_JAPAN) + _end;
		name = appData.getStringData(IDataActions.SONG_NAME_JAPAN);
		return name;
		// return appData.getStringData(IDataActions.SONG_NAME) + _end;
	}

	// fix
}
