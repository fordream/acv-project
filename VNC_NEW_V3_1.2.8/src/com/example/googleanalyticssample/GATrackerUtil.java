package com.example.googleanalyticssample;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.Tracker;

public class GATrackerUtil {
	static private Activity currentActivity;
	static private Tracker tracker;

	/**
	 * ï¿½Aï¿½Nï¿½eï¿½Bï¿½rï¿½eï¿½Bï¿½ï¿½ onCreate ï¿½ÅŒÄ‚Ñ�oï¿½ï¿½
	 * ï¿½Ä‚Ñ�oï¿½ï¿
	 * ½ï¿½Ì‚Íƒï¿½ï¿½Cï¿½ï¿½ï¿½ÌƒAï¿½Nï¿½eï¿½Bï¿½rï¿½eï¿½Bï¿½Ì‚Ý‚Å‚ï
	 * ¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	 * 
	 * @param c
	 */
	static public void initContext(Context c) {
		EasyTracker.getInstance().setContext(c.getApplicationContext());
		tracker = EasyTracker.getTracker();
	}

	/**
	 * Activity#onStart ï¿½ÅŒÄ‚Ñ�oï¿½ï¿½
	 * 
	 * @param activity
	 */
	static public void startActivity(Activity activity) {
		if (activity == null) {
			throw new java.lang.IllegalArgumentException("argument must not null");
		}

		if (currentActivity != activity) {
			stopActivity();
		}
		currentActivity = activity;

		EasyTracker.getInstance().activityStart(activity);
	}

	/**
	 * Activity#onStop ï¿½ÅŒÄ‚Ñ�oï¿½ï¿½
	 */
	static public void stopActivity() {
		if (currentActivity != null) {
			EasyTracker.getInstance().activityStart(currentActivity);
			currentActivity = null;
		}
	}

	/**
	 * ï¿½yï¿½[ï¿½Wï¿½rï¿½ï¿½ï¿½[ï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½O
	 * 
	 * @param viewName
	 */
	static public void sendView(String viewName) {
		tracker.sendView(viewName);
	}

	/**
	 * ï¿½Cï¿½xï¿½ï¿½ï¿½gï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½O
	 * 
	 * @param category
	 * @param action
	 * @param label
	 * @param optionValue
	 */
	static public void sendEvent(String category, String action, String label, Long optionValue) {
		tracker.sendEvent(category, action, label, optionValue);
	}

	/**
	 * ï¿½Cï¿½xï¿½ï¿½ï¿½gï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½O
	 * 
	 * @param category
	 * @param action
	 * @param label
	 */
	static public void sendEvent(String category, String action, String label) {
		tracker.sendEvent(category, action, label, 0L);
	}

	/**
	 * ï¿½ï¿½ï¿½Ôƒgï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½O
	 * 
	 * @param loadTime
	 *            ï¿½ï¿½ï¿½ï¿½ï¿½É—vï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	 * @param category
	 * @param name
	 * @param label
	 */
	static public void sendTiming(long loadTime, String category, String name, String label) {
		tracker.sendTiming(category, loadTime, name, label);
	}

	/**
	 * ï¿½ï¿½Oï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½O
	 * 
	 * @param ex
	 * @param fatal
	 */
	static public void sendException(Exception ex, boolean fatal) {
		tracker.sendException(ex.getMessage(), fatal);

	}

	/**
	 * ï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½Oï¿½fï¿½[ï¿½^ï¿½ï¿½ï¿½M
	 */
	static public void dispatch() {
		GAServiceManager sm = GAServiceManager.getInstance();
		if (sm != null) {
			sm.dispatch();
		}
	}

	/**
	 * ï¿½gï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½Oï¿½fï¿½[ï¿½^ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Mï¿½ÔŠuï¿½Ý’
	 * ï¿½
	 * 
	 * @param sec
	 *            ï¿½ï¿½ï¿½Iï¿½Éƒgï¿½ï¿½ï¿½bï¿½Lï¿½ï¿½ï¿½Oï¿½fï¿½[ï¿½^ï¿½ð‘—�Mï¿
	 *            ½ï¿½ï¿½ï¿½ÔŠu[sec]ï¿½ï¿½Ý’è‚·ï¿½ï¿½ analytics.xml
	 *            ï¿½Å‚ï¿½ï¿½wï¿½ï¿½Â”\
	 *            ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½wï¿½è‚µï¿½ï¿½ï¿½ê�‡ï
	 *            ¿½ÍŽï¿½ï¿½ï¿½ï¿½Iï¿½É‘ï¿½ï¿½Mï¿½ï¿½ï¿½ï¿½È‚ï¿½ï¿½Bdispatch()
	 *            ï¿
	 *            ½ï¿½ï¿½Ä‚Ñ�oï¿½ï¿½ï¿½ÄŽè“®ï¿½ï¿½ï¿½Mï¿½ï¿½ï¿½ï¿½(ï¿½Nï¿½ï¿½ï¿
	 *            ½ï¿½ï¿½É‚Í•Kï¿½ï¿½ï¿½ï¿½ï¿½Mï¿½ï¿½ï¿½ï¿½ï¿½H) 0
	 *            ï¿½ï¿½ï¿½wï¿½è‚·ï¿½ï¿½Æ‘ï¿½ï¿½ï¿½ï¿½Mï¿½É‚È‚ï¿½Ý‚ï¿½ï¿½ï¿½
	 */
	static public void setDispatchPeriod(int sec) {
		GAServiceManager sm = GAServiceManager.getInstance();
		if (sm != null) {
			sm.setDispatchPeriod(sec);
		}
	}
}
