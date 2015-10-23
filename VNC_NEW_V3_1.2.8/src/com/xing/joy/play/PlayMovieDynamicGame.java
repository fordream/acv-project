package com.xing.joy.play;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon.R;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xing.joy.interfaces.IDataActions;

public class PlayMovieDynamicGame extends PlayMovie {

	@Override
	public void onPrepared(MediaPlayer mp) {

		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown && mIsLyricReady
				&& mIsGimmicReadyToBePlayed) {
			startVideoPlayback();
		}
	}

	@Override
	public void setPathLyric() {
		final Timer tm = new Timer();
		tm.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (lyricService != null) {
					String path = pathMovie + songName;
					if (new File(path + "/" + lyricName).exists()) {
						lyricService.setPath(path + "/" + lyricName, "SD");
						mIsLyricReady = true;
						if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown
								&& mIsLyricReady && mIsGimmicReadyToBePlayed) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									startVideoPlayback();
								}
							});
						}
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								errorLoadResource();
							}
						});
					}
					tm.cancel();
				}
			}
		}, 0, 300);
	}

	@Override
	public void initializationMediaPlayer(String pathMusic) {
		String filePath = pathMovie + songName + "/" + songName
				+ "_movie.mp4.nomedia";
		try {
			File file = new File(filePath);
			byte[] decodeHeader = file.getName().replace(".nomedia", "")
					.getBytes();
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(
					getFileDecriptor(PlayMovieDynamicGame.this, filePath),
					decodeHeader.length, file.length() - decodeHeader.length);
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setOnInfoListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.prepareAsync();
		} catch (FileNotFoundException e) {
			errorLoadResource();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parseFileMovieInfo() {
		try {
			String path = pathMovie + songName + "/movie_info.txt";
			File txt = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(txt));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (!line.equalsIgnoreCase("")) {
					String[] info = line.split("#");
					strGimmicImage[i] = info[0];
					strGimmicSound[i] = info[1];
					i++;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			errorLoadResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createGimmic() {
		parseFileMovieInfo();
		int left[] = {
				(int) (244 * calResize.getRatioResizeWidth() + calResize
						.getHemBlackWidth()),
				(int) (436 * calResize.getRatioResizeWidth() + calResize
						.getHemBlackWidth()),
				(int) (612 * calResize.getRatioResizeWidth() + calResize
						.getHemBlackWidth()), };
		int top = (int) (496 * calResize.getRatioResizeHeight() + calResize
				.getHemBlackHeight());
		for (int i = 0; i < imgGimmic.length; i++) {

			// Load sound gimmic
			if (i <= 1) {
				try {
					String pathSoundSource = pathMovie + songName + "/mfx/"
							+ strGimmicSound[i];
					File file = new File(pathSoundSource);
					if (!file.exists()) {
						throw new FileNotFoundException();
					}
					FileInputStream fis = new FileInputStream(file);
					byte[] decodeHeader = file.getName()
							.replace(".nomedia", "").getBytes();
					soundsMap.put(MP3_GIMMIC[i], soundPool.load(fis.getFD(),
							decodeHeader.length, file.length()
									- decodeHeader.length, 1));
					fis.close();
				} catch (FileNotFoundException e) {
					errorLoadResource();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				String strGimic3[] = strGimmicSound[2].split("@");
				for (int j = 0; j < strGimic3.length; j++) {
					String g3filename = strGimic3[j];
					if (!g3filename.equals("")) {
						try {
							String pathSoundSource = pathMovie + songName
									+ "/mfx/" + strGimic3[j];
							MP3_GIMMIC_3.add(j + 3);
							File file = new File(pathSoundSource);
							if (!file.exists()) {
								throw new FileNotFoundException();
							}
							FileInputStream fis = new FileInputStream(file);
							byte[] decodeHeader = file.getName()
									.replace(".nomedia", "").getBytes();
							soundsMap.put(j + 3, soundPool.load(fis.getFD(),
									decodeHeader.length, file.length()
											- decodeHeader.length, 1));
							fis.close();
						} catch (FileNotFoundException e) {
							errorLoadResource();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			// Load image gimmic
			try {
				String pathImage = pathMovie + songName + "/gfx/"
						+ strGimmicImage[i];
				File tmpImg = new File(pathImage);
				if (!tmpImg.exists()) {
					throw new FileNotFoundException();
				}
				InputStream is = new FileInputStream(tmpImg);
				byte[] saltImage = tmpImg.getName().replace(".nomedia", "")
						.getBytes();
				is.read(saltImage);
				// Decode image size
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPreferredConfig = Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeStream(is, null, option);
				RelativeLayout.LayoutParams gimmicLayout = new RelativeLayout.LayoutParams(
						Math.round(103 * calResize.getRatioResizeWidth()),
						Math.round(103 * calResize.getRatioResizeHeight()));
				gimmicLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP, -1);
				gimmicLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
				gimmicLayout.setMargins(left[i], top, 0, 0);
				imgGimmic[i] = new ImageView(this);
				imgGimmic[i].setBackgroundDrawable(new BitmapDrawable(bitmap));
				// imgGimmic[i].setImageBitmap(bitmap);
				imgGimmic[i].bringToFront();
				rlMain.addView(imgGimmic[i], gimmicLayout);
				mIsGimmicReadyToBePlayed = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				errorLoadResource();
			} catch (IOException e) {
				e.printStackTrace();
			}
			final int tmp = i;
			if (imgGimmic[i] != null) {
				imgGimmic[i].setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							Animation gimmicScale = AnimationUtils
									.loadAnimation(PlayMovieDynamicGame.this,
											R.anim.gimmic_scale);
							if (imgGimmic[tmp] != null) {
								if (tmp == 2) {
									final int id = new Random()
											.nextInt(MP3_GIMMIC_3.size());
									playSound(MP3_GIMMIC_3.get(id));
								} else {
									playSound(MP3_GIMMIC[tmp]);
								}

								imgGimmic[tmp].clearAnimation();
								imgGimmic[tmp].startAnimation(gimmicScale);
							}
						}
						return true;
					}
				});
			}
		}
	}

	@Override
	void setBackgroundMovie() {
		String pathImage = pathMovie + songName
				+ "/background_movie.png.nomedia";
		InputStream in = null;
		try {
			in = new FileInputStream(new File(pathImage));
			in.read("background_movie.png".getBytes());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(in);
		Drawable tmp = new BitmapDrawable(bitmap);
		imgBackgroundMovie.setBackgroundDrawable(tmp);
	}

	@Override
	public String getNameCount() {
		// return appData.getStringData(IDataActions.SONG_NAME) + "_douga";
		String name = appData.getStringData(IDataActions.SONG_NAME_JAPAN)
				+ getResources().getString(R.string._douga);// "_douga";

		name = appData.getStringData(IDataActions.SONG_NAME_JAPAN);
		return name;
	}
}
