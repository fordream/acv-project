package jp.co.xing.utaehon03.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.andengine.ui.activity.BaseActivity.CancelledException;
import org.andengine.util.call.Callback;
import org.andengine.util.debug.Debug;
import org.andengine.util.progress.IProgressListener;
import org.andengine.util.progress.ProgressCallable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.xing.joy.common.CalculateResize;
import com.xing.joy.common.LoadingProgressBar;
import com.xing.joy.play.DynamicSong;

public class ActivityUtils extends org.andengine.util.ActivityUtils {

	private static final int FIRST_LOAD = 0;
	private static final int FINISH_LOAD = 1;
	public static int timeScreenLoading = 200;// 1300;
	public static String pathSourceSD = "";
	private static ImageView background;
	private static ImageView capture;
	private static LoadingProgressBar lpBar;

	public static <T> void doLoadingAsync(final Context pContext,
			final CharSequence pTitle, final RelativeLayout mImageViewLoading,
			final int ptype, final ProgressCallable<T> pCallable,
			final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		new AsyncTask<Void, Integer, T>() {
			private RelativeLayout mTextview = mImageViewLoading;
			private Exception mException = null;
			private int type = ptype;

			@Override
			public void onPreExecute() {
				if (mImageViewLoading != null && type == FIRST_LOAD) {
					LoadingProgressBar.isRunning = true;
					RelativeLayout.LayoutParams lpCapture = new RelativeLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

					capture = new ImageView(pContext);
					capture.setBackgroundDrawable(setBackgroundKaraoke("background_karaoke.png"));
					mTextview.addView(capture, lpCapture);

					background = new ImageView(pContext);
					background.setBackgroundColor(Color.GRAY);
					background.getBackground().setAlpha(100);
					mTextview.addView(background, lpCapture);

					lpBar = new LoadingProgressBar(pContext);
					CalculateResize cResize = new CalculateResize(
							(Activity) pContext);
					RelativeLayout.LayoutParams lpPopup = new RelativeLayout.LayoutParams(
							(int) (525 * cResize.getRatioResizeWidth()),
							(int) (150 * cResize.getRatioResizeHeight()));
					lpPopup.addRule(RelativeLayout.CENTER_VERTICAL);
					lpPopup.addRule(RelativeLayout.CENTER_HORIZONTAL);
					mTextview.addView(lpBar, lpPopup);
				} else {
					LoadingProgressBar.isRunning = false;
				}
				super.onPreExecute();
			}

			public Drawable setBackgroundKaraoke(String name) {
				InputStream is = null;
				Bitmap image = null;
				try {
					is = new FileInputStream(new File(pathSourceSD + name
							+ ".nomedia"));
					byte[] salt = name.getBytes();
					is.read(salt);
					image = BitmapFactory.decodeStream(is);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.gc();
				return new BitmapDrawable(image);
			}

			@Override
			public T doInBackground(final Void... params) {
				try {
					return pCallable.call(new IProgressListener() {
						@Override
						public void onProgressChanged(final int pProgress) {
							onProgressUpdate(pProgress);
						}
					});
				} catch (final Exception e) {
					this.mException = e;
				}
				return null;
			}

			@Override
			public void onPostExecute(final T result) {
				if (mImageViewLoading == null || type == FIRST_LOAD) {
					return;
				}
				if (mImageViewLoading != null && type == FINISH_LOAD) {

					try {

						capture.postDelayed(new Runnable() {

							@Override
							public void run() {
								background.setBackgroundDrawable(null);
								background.setVisibility(View.INVISIBLE);
								capture.setBackgroundDrawable(null);
								capture.setVisibility(View.INVISIBLE);
								mTextview.removeView(background);
								mTextview.removeView(capture);
								mTextview.removeView(lpBar);
								DynamicSong.LOAD_RESOURCE_COMPLETE = true;
							}
						}, timeScreenLoading);

					} catch (final Exception e) {
						Debug.e("Error", e);
						/* Nothing. */
					}
				}

				if (this.isCancelled()) {
					this.mException = new CancelledException();
				}

				if (this.mException == null) {
					pCallback.onCallback(result);
				} else {
					if (pExceptionCallback == null) {
						Debug.e("Error", this.mException);
					} else {
						pExceptionCallback.onCallback(this.mException);
					}
				}

				super.onPostExecute(result);
			}
		}.execute((Void[]) null);
	}

	public static void doLoadingAsync(final Context pContext, String pTitle,
			final RelativeLayout mImageViewLoading,
			ProgressCallable<Void> progressCallable, Callback<Void> callback) {
		doLoadingAsync(pContext, pTitle, mImageViewLoading, FIRST_LOAD,
				progressCallable, callback, null);
	}

	public static void doLoadingFinishAsync(final Context pContext,
			String pTitle, final RelativeLayout mImageViewLoading,
			ProgressCallable<Void> progressCallable, Callback<Void> callback) {
		doLoadingAsync(pContext, pTitle, mImageViewLoading, FINISH_LOAD,
				progressCallable, callback, null);
	}

}