package com.xing.joy.interfaces;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.xing.joy.others.Top;

public class JavascriptInterface {
	Context mContext;

	/** Instantiate the interface and set the context */
	public JavascriptInterface(Context c) {
		mContext = c;
	}

	public int getHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// Log.d("TEST", ""+(int) (Top.webView.getHeight()));
		return (int) (Top.webView.getHeight());
	}
}
