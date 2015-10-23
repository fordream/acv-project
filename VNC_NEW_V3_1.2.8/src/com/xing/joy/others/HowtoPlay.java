package com.xing.joy.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.CommonUtils;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.joy.common.CoreActivity;

public class HowtoPlay extends CoreActivity implements OnTouchListener {

	/** Image back, background, help, left, right button. */
	private ImageView background, help_image, btLeft, btRight;

	/** Media player. */
	private MediaPlayer mp;
	private TextView page;

	/** Index of image. */
	private int count = 1;

	private ArrayList<String> strImgHelp = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.howto_play);
		createHemImage(R.id.howto_play_display);
		addBackButton(R.id.howto_play_display);
		background = (ImageView) findViewById(R.id.help_background);
		resizeView(background, 0, 0, 0, 0);

		help_image = (ImageView) findViewById(R.id.help_image);
		resizeView(help_image, 0, 0, 0, 0);

		btLeft = (ImageView) findViewById(R.id.help_left);
		resizeView(btLeft, 10, 555, 0, 0);

		btRight = (ImageView) findViewById(R.id.help_right);
		resizeView(btRight, 872, 555, 0, 0);

		page = (TextView) findViewById(R.id.help_text);

		resizeView(page, 450, 565, 0, 0);

		try {
			Typeface font = Typeface.createFromFile(this.getFilesDir().toString() + "/a_otf_jun501pro_bold.otf");
			page.setTypeface(font);
		} catch (Exception exception) {
		}
		parseHelpFile(new File(memory.getPathFileInternalMemory() + "img_helper/", "help.txt"));
	}

	public void parseHelpFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while (!(line = br.readLine()).equalsIgnoreCase("")) {
				String[] tmp = line.split("#");
				strImgHelp.add(tmp[1]);
				tmp = null;
			}
			br.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mp == null) {
			mp = MediaPlayer.create(this, R.raw.opening);
			mp.setVolume(volume, volume);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setLooping(true);
		}
		mp.start();
	}

	@Override
	public void onWindowFocusChanged(boolean pHasWindowFocus) {
		super.onWindowFocusChanged(pHasWindowFocus);
		if (pHasWindowFocus) {
			setBackground();
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (view.getId()) {
			case R.id.help_left:
				if (count > 1) {
					count--;
					setBackground();
				}
				break;
			case R.id.help_right:
				if (count < strImgHelp.size()) {
					count++;
					setBackground();
				}
				break;
			}
		}
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}

	public void setBackground() {

		if (btLeft == null || btRight == null || help_image == null) {
			return;
		}
		btLeft.setOnTouchListener(this);
		btRight.setOnTouchListener(this);
		btLeft.setVisibility(View.VISIBLE);
		btRight.setVisibility(View.VISIBLE);
		if (count == 1) {
			btLeft.setVisibility(View.INVISIBLE);
		}

		if (count == strImgHelp.size()) {
			btRight.setVisibility(View.INVISIBLE);
		}
		int sizePage = page.getHeight() / 2;
		if (sizePage < 25) {
			sizePage = 25;
		}
		if (sizePage > 40) {
			sizePage = 40;
		}
		// page.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePage);
		// page.setText(count + " / " + strImgHelp.size());
		try {
			String path = memory.getPathFileInternalMemory() + "img_helper/" + strImgHelp.get(count - 1);

			// Bitmap bmImg = BitmapFactory.decodeFile(path);
			//
			// Drawable d = new BitmapDrawable(bmImg);
			// help_image.setBackgroundDrawable(d);
			imageClientLoader.getBitmap(path, help_image);
			// help_image.setImageBitmap(bmImg);
		} catch (Exception exception) {

		}
		return;
	}

	@Override
	public void releaseMemory() {

		if (strImgHelp != null) {
			strImgHelp.clear();
			strImgHelp = null;
		}
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		if (page != null) {
			page.setText("");
		}
		setNullViewDrawable(help_image);
		setNullViewDrawable(btLeft);
		setNullViewDrawable(btRight);
		setNullViewDrawable(background);
		page = null;
		help_image = null;
		btLeft = null;
		btRight = null;
		background = null;
		System.gc();
		super.releaseMemory();
	}

	@Override
	protected void onDestroy() {

		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		setNullViewDrawable(help_image);
		setNullViewDrawable(btLeft);
		setNullViewDrawable(btRight);

		help_image = null;
		btLeft = null;
		btRight = null;
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		releaseMemory();
		CommonUtils.startNewActivity(this, Top.class, "");
		super.onBackPressed();
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_VOLUME_UP:
	// mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	// AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	// float streamVolumeCurrent = mgr
	// .getStreamVolume(AudioManager.STREAM_MUSIC);
	// float streamVolumeMax = mgr
	// .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	// volume = streamVolumeCurrent / streamVolumeMax;
	// mp.setVolume(volume, volume);
	// return true;
	// case KeyEvent.KEYCODE_VOLUME_DOWN:
	// mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	// AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	// streamVolumeCurrent = mgr
	// .getStreamVolume(AudioManager.STREAM_MUSIC);
	// streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	// volume = streamVolumeCurrent / streamVolumeMax;
	// mp.setVolume(volume, volume);
	// return true;
	// default:
	// return super.onKeyDown(keyCode, event);
	// }
	// }

	@Override
	public String getNameCount() {
		// return Logtime.PAGE_HELP;
		return getResources().getString(R.string.namescreen_help);
	}
}
