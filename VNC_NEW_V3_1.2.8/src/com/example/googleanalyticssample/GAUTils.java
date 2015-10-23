package com.example.googleanalyticssample;

import jp.co.xing.utaehon.VNCStartUpActivity;
import android.app.Activity;

import com.xing.joy.others.Credit;
import com.xing.joy.others.HowtoPlay;

public class GAUTils {
	private static final boolean ISENABLE_ANALYTICS = true;
	private Activity context;

	public GAUTils() {
	}

	public void onStart() {
		if (ISENABLE_ANALYTICS && context != null) {
			// GATrackerUtil.startActivity((Activity) context);
		}
	}

	public void onStop() {
		if (ISENABLE_ANALYTICS && context != null) {
			// GATrackerUtil.stopActivity();
		}
	}

	private boolean canSend() {
		if (ISENABLE_ANALYTICS && context != null) {
			if (context instanceof Credit || context instanceof HowtoPlay || context instanceof VNCStartUpActivity) {
				return false;
			} else {
				return true;
			}
		}

		return false;
	}

	public void onCreate(Activity context) {
		this.context = context;
		if (ISENABLE_ANALYTICS) {
			GATrackerUtil.initContext(context);
			GATrackerUtil.setDispatchPeriod(10);
		}
	}

	public void sendView(String view) {
		if (!"".equals(view) && ISENABLE_ANALYTICS)
			GATrackerUtil.sendView(view);
	}

	public void sendException(Exception ex, boolean fatal) {
		if (ISENABLE_ANALYTICS)
			GATrackerUtil.sendException(ex, fatal);
	}

	public void sendEvent(String category, String action, String label, Long optionValue) {
		if (ISENABLE_ANALYTICS)
			GATrackerUtil.sendEvent(category, action, label, optionValue);
	}

	public void sendEvent(String category, String action, String label) {

		if (ISENABLE_ANALYTICS)
			GATrackerUtil.sendEvent(category, action, label);
	}

	public void sendTiming(long loadTime, String category, String name, String label) {
		if (ISENABLE_ANALYTICS)
			GATrackerUtil.sendTiming(loadTime, category, name, label);
	}
}