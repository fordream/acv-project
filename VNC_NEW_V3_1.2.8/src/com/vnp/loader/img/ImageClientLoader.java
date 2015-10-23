/**
 * 
 */
package com.vnp.loader.img;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 */
public class ImageClientLoader {
	private static final float SCALE = 0.7f;
	private static ImageClientLoader instance;
	private Context context;
	private ImageLoader imageLoader;

	public static ImageClientLoader getInstance() {
		if (instance == null) {
			instance = new ImageClientLoader(null);
		}

		return instance;
	}

	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
			imageLoader = new ImageLoader(context);
		}
	}

	private ImageClientLoader(Context context) {
		this.context = context;
	}

	public void getBitmap(final String path, final ImageView imageView) {
		try {
			imageView.setImageBitmap(decodePath(path));
		} catch (Exception ex) {

		}// imageLoader.DisplayImage(path, imageView);
	}

	private Bitmap getBitmap(int res) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(context.getResources(), res, o);
			final int REQUIRED_SIZE = 480;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			return BitmapFactory.decodeResource(context.getResources(), res, o2);

		} catch (OutOfMemoryError exception) {
			release();
		}

		return null;
	}

	public void release() {
		imageLoader.clearCache();
	}

	public void createDrawable(final int res, final View view) {
		try {
			if (view instanceof ImageView) {
				imageLoader.DisplayImage(res + "", (ImageView) view);
			} else {
				Bitmap bitmap = getBitmap(res);
				view.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}
		} catch (Exception exception) {
		}
	}

	public void getBitmapStreamNomedia(final String path, final ImageView imageView) {
		try {
			imageView.setImageBitmap(decodePath(path));
		} catch (Exception ex) {

		}
		// imageLoader.DisplayImage(path, imageView);
	}

	public void loadSynTask(ImageView imageView, final String path, final ILoadResult loadResult) {
		try {
			Bitmap bitmap = hashMap.get(path);
			if (bitmap != null) {
				if (bitmap.isRecycled()) {
					hashMap.remove(path);
					bitmap = null;
				}
			}

			if (bitmap == null) {
				bitmap = decodePath(path);

				if (bitmap != null) {
					Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * SCALE), (int) (bitmap.getHeight() * SCALE), true);
					bitmap.recycle();
					bitmap = bitmap2;

				}
				hashMap.put(path, bitmap);
			}

			imageView.setImageBitmap(bitmap);
		} catch (Exception exception) {
		}
	}

	public interface ILoadResult {
		public void loadResult(Bitmap bitmap);
	}

	public Bitmap getBitmapImageLoader(String path) {
		return decodePath(path);
	}

	public Bitmap getBitmapImageLoader1(String path) {
		return imageLoader.getBitmap(path);
	}

	private Bitmap decodePath(String url) {
		InputStream in1 = null;
		try {
			File tmp = new File(url);
			in1 = new FileInputStream(tmp);
			if (url.endsWith(".nomedia")) {
				in1.read(tmp.getName().replace(".nomedia", "").getBytes());
			}
		} catch (Exception e) {
		} catch (OutOfMemoryError error) {
		}
		return BitmapFactory.decodeStream(in1);
	}

	HashMap<String, Bitmap> hashMap = new HashMap<String, Bitmap>();

	public void clear() {
		hashMap.clear();
		imageLoader.clearCache();
	}

	public void newpath(ImageView imageView, String path) {
		try {
			imageLoader.DisplayImage(path + "", imageView);
		} catch (Exception exx) {
		}
	}

}