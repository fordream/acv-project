package com.xing.joy.common;

import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.xing.joy.interfaces.IDataActions;

public class CalculateResize {
	private static final String LOG_RESIZEVIEW = "RESIZE";
	private DisplayMetrics metrics;
	private float ratioResizeWidth;
	private float ratioResizeHeight;
	private int hemBlackWidth = 0, hemBlackHeight = 0;
	private int maxWidth = 0, maxHeight = 0;
	private int statusHem = 0;

	public CalculateResize(Context activity) {
		try {
			metrics = new DisplayMetrics();
			((Activity) activity).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);

			// Get with & height from resource
			Resources res = activity.getResources();
			maxWidth = res.getInteger(R.integer.max_width);
			maxHeight = res.getInteger(R.integer.max_height);

			Log.d(LOG_RESIZEVIEW, "CHANGE..." + metrics.toString());
			if (metrics.widthPixels < metrics.heightPixels) {
				int temp = metrics.widthPixels;
				metrics.widthPixels = metrics.heightPixels;
				metrics.heightPixels = temp;
			}

			// Height bar for 3.0
			int heightBar = activity.getSharedPreferences(
					IDataActions.PREFS_INFO_APP, 0).getInt("height_bar", 0);
			metrics.heightPixels = metrics.heightPixels
					- Math.round(heightBar / metrics.density);

			ratioResizeWidth = (float) (metrics.widthPixels) / maxWidth;
			ratioResizeHeight = (float) (metrics.heightPixels) / maxHeight;

			if (ratioResizeHeight < ratioResizeWidth) {
				statusHem = 1;

				hemBlackWidth = Math.round((metrics.widthPixels - maxWidth
						* ratioResizeHeight) / 2);

				ratioResizeWidth = (float) ((metrics.widthPixels) - 2 * hemBlackWidth)
						/ maxWidth;
			} else {
				if (ratioResizeHeight > ratioResizeWidth) {
					statusHem = 2;

					hemBlackHeight = Math
							.round((metrics.heightPixels - maxHeight
									* ratioResizeWidth) / 2);

					ratioResizeHeight = (float) ((metrics.heightPixels) - 2 * hemBlackHeight)
							/ maxHeight;
				}
			}
		} catch (Exception e) {
		}
	}

	public int getStatusHem() {
		return this.statusHem;
	}

	/**
	 * Get rate resize width.
	 * 
	 * @return float
	 * */
	public float getRatioResizeWidth() {
		return this.ratioResizeWidth;
	}

	/**
	 * Get rate resize height.
	 * 
	 * @return float
	 * */
	public float getRatioResizeHeight() {
		return this.ratioResizeHeight;
	}

	/**
	 * Get max width.
	 * 
	 * @return int
	 * */
	public int getMaxWidth() {
		return this.maxWidth;
	}

	public int getWidth() {
		return this.metrics.widthPixels;
	}

	public int getHeight() {
		return this.metrics.heightPixels;
	}

	/**
	 * Get max height.
	 * 
	 * @return int
	 */
	public int getMaxHeight() {
		return this.maxHeight;
	}

	/**
	 * Get black hem width.
	 * 
	 * @return int
	 */
	public int getHemBlackWidth() {
		return this.hemBlackWidth;
	}

	/**
	 * Get black hem width.
	 * 
	 * @return int
	 */
	public int getHemBlackHeight() {
		return this.hemBlackHeight;
	}

	public float getDensity() {
		return this.metrics.density;
	}
}
