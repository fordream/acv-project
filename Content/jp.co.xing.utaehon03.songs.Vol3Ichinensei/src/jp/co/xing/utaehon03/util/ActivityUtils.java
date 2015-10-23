package jp.co.xing.utaehon03.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseActivity.CancelledException;
import org.andengine.util.call.Callback;
import org.andengine.util.debug.Debug;
import org.andengine.util.progress.IProgressListener;
import org.andengine.util.progress.ProgressCallable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

public class ActivityUtils extends org.andengine.util.ActivityUtils {

	private static final int FIRST_LOAD = 0;
	private static final int FINISH_LOAD = 1;
	public static int timeScreenLoading = 1000;

	public static <T> void doLoadingAsync(final Context pContext,
			final CharSequence pTitle, final ImageView mImageViewLoading,
			final int ptype, final ProgressCallable<T> pCallable,
			final Callback<T> pCallback,
			final Callback<Exception> pExceptionCallback) {
		new AsyncTask<Void, Integer, T>() {
			private ImageView mTextview = mImageViewLoading;
			private Exception mException = null;
			private int count = 0;
			private int type = ptype;

			@Override
			public void onPreExecute() {
				if (mImageViewLoading != null && type == FIRST_LOAD) {
					this.mTextview.setVisibility(View.VISIBLE);
					mTextview
							.setBackgroundDrawable(setBackgroundKaraoke("background_karaoke.png"));
				}
				super.onPreExecute();
			}

			public Drawable setBackgroundKaraoke(String name) {
				InputStream is = null;
				Bitmap image = null;
				if (BaseGameFragment.BuildConfig.TEST_VERSION) {
					try {
						is = pContext
								.getResources()
								.getAssets()
								.open(BitmapTextureAtlasTextureRegionFactory
										.getAssetBasePath() + name);
					} catch (IOException e) {
						return null;
					}
				} else {
					String path = Environment.getExternalStorageDirectory()
							+ "/Android/data/" + pContext.getPackageName()
							+ "/files/" + "vol3ichinensei" + "/gfx/" + name
							+ ".nomedia";
					try {
						is = new FileInputStream(new File(path));
						byte[] salt = name.getBytes();
						is.read(salt);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				image = BitmapFactory.decodeStream(is);
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
							count++;
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
						this.mTextview.postDelayed(new Runnable() {

							@Override
							public void run() {
								mTextview.setVisibility(View.GONE);
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
			final ImageView mImageViewLoading,
			ProgressCallable<Void> progressCallable, Callback<Void> callback) {
		doLoadingAsync(pContext, pTitle, mImageViewLoading, FIRST_LOAD,
				progressCallable, callback, null);
	}

	public static void doLoadingFinishAsync(final Context pContext,
			String pTitle, final ImageView mImageViewLoading,
			ProgressCallable<Void> progressCallable, Callback<Void> callback) {
		doLoadingAsync(pContext, pTitle, mImageViewLoading, FINISH_LOAD,
				progressCallable, callback, null);
	}

}
