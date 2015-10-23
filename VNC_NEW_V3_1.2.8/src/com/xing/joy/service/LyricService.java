package com.xing.joy.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class LyricService extends Service implements Runnable {
	private final IBinder binder = new MyBinder();
	private String randomKey = "";
	private String path = "";
	private String fromPath = "";
	private String action = "";
	public static final String BROADCAST_ACTION_1 = "com.xing.joy.play.dynamicsong.displayevent";
	public static final String BROADCAST_ACTION_2 = "com.xing.joy.play.playmovie.displayevent";
	private final Handler handler = new Handler();
	Intent intent;
	private int countLine = 0;
	ArrayList<Integer> startTime = new ArrayList<Integer>();
	ArrayList<Integer> lastTime = new ArrayList<Integer>();
	ArrayList<String> sentence = new ArrayList<String>();

	@Override
	public void onCreate() {

		// The service is being created
		intent = new Intent();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
		// return the binder(myService reference) when service
		// connected using Service Connection
	}

	// Instance of the Service is now in MyBinder
	public class MyBinder extends Binder {

		public LyricService getService() {

			return LyricService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void startLyric(String action, String randomKey) {
		// I call the Runnable using Handler instance
		this.action = action;
		this.randomKey = randomKey;
		countLine = 0;
		handler.postDelayed(this, startTime.get(0)); // after 1 sec it will call
														// the
		// run() block
	}

	public void stopLyric() {

		handler.removeCallbacks(this); // Make sure we stop handler call backs
										// when stop called
	}

	@Override
	public void run() {
		if (countLine < sentence.size()) {
			Intent intent = new Intent();
			// Bundle the counter value with Intent
			intent.putExtra("lyric", sentence.get(countLine));
			intent.putExtra("songname", this.randomKey);
			intent.setAction(this.action); // Define intent-filter
			sendBroadcast(intent);
			broadCast();
		}

	}

	// Broadcast the counter value so others know
	private void broadCast() {

		countLine++;
		if (countLine >= startTime.size()) {
			return;
		}
		handler.postDelayed(this,
				startTime.get(countLine) - startTime.get(countLine - 1));
	}

	public void setPath(String path, String fromPath) {
		this.path = path;
		this.fromPath = fromPath;
		if (this.fromPath.equalsIgnoreCase("assets")) {
			parseLyricFromAssets();
		} else {
			parseLyricFromSD();
		}
	}

	/**
	 * This function to parse lyric
	 */
	private void parseLyricFromSD() {
		startTime.clear();
		sentence.clear();
		lastTime.clear();
		File txt = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(txt));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace(".", ":");
				String patern = "(\\[\\d{2}:\\d{2}:\\d{2}\\])(.*)(\\<\\d{2}:\\d{2}:\\d{2}\\>)";
				Pattern p = Pattern.compile(patern);
				Matcher m = p.matcher(line);
				if (m.find()) {

					// String root_new = m.group(0);
					String first = m.group(1).replace("[", "").replace("]", "");
					String second = m.group(2);
					String thirst = m.group(3).replace("<", "")
							.replace(">", "");
					startTime.add(calculateTimer(first));
					sentence.add(second);
					lastTime.add(calculateTimer(thirst));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function to parse lyric
	 */
	public void parseLyricFromAssets() {
		startTime.clear();
		sentence.clear();
		lastTime.clear();
		InputStream is;
		byte[] buffer = null;
		try {
			is = getAssets().open(this.path);
			int size = is.available();
			buffer = new byte[size];
			is.read(buffer);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// File
		String text = new String(buffer);
		String[] lines = text.split("\n");
		for (int i = 4; i < lines.length; i++) {
			lines[i] = lines[i].replace(".", ":");
			String patern = "(\\[\\d{2}:\\d{2}:\\d{2}\\])(.*)(\\<\\d{2}:\\d{2}:\\d{2}\\>)";
			Pattern p = Pattern.compile(patern);
			Matcher m = p.matcher(lines[i]);
			if (m.find()) {
				// String rootnew = m.group(0);
				String first = m.group(1).replace("[", "").replace("]", "");
				String second = m.group(2);
				String thirst = m.group(3).replace("<", "").replace(">", "");
				startTime.add(calculateTimer(first));
				sentence.add(second);
				lastTime.add(calculateTimer(thirst));
			}

		}
	}

	/**
	 * This function change format time from format: 00:00.00 to format: 000
	 * 
	 * @return integer
	 * @param startTime
	 * */
	public int calculateTimer(String startTime) {
		String tmp1, tmp2, tmp3;

		// First character on String in first element of array startTime
		tmp1 = startTime.substring(0, 2);
		// Second character on String in first element of array startTime
		tmp2 = startTime.substring(3, 5);
		// Third character on String in first element of array startTime
		tmp3 = startTime.substring(6, 8);

		int time1 = Integer.parseInt(tmp1);
		int time2 = Integer.parseInt(tmp2);
		int time3 = Integer.parseInt(tmp3);

		// format 000 calculated follow formula
		return (time1 * 60000 + time2 * 1000 + time3 * 10);
	}

}