package com.xing.joy.common;

import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class LoadingProgressBar extends View implements Runnable {

	Context context;
	private CalculateResize resize;
	private float resizeImage;
	private float resizeDistance;
	private Paint paint;
	private int rateRotate = 0;
	public static boolean isRunning = true;
	private Handler handler;

	public LoadingProgressBar(Context context) {
		super(context);
		this.context = context;
		init();

	}

	public LoadingProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		resize = new CalculateResize((Activity) context);
		resizeImage = resize.getRatioResizeWidth() / resize.getDensity();
		resizeDistance = resize.getRatioResizeWidth();
		paint = new Paint();
		paint.setAntiAlias(true);
		handler = new Handler() {
			@Override
			public void handleMessage(final Message msgs) {
				LoadingProgressBar.this.invalidate();
			}
		};
		new Thread(this).start();
	}

	public void stop() {
		isRunning = false;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isRunning) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			// background
			Bitmap background = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.loading_1, options);

			canvas.rotate(0);
			canvas.drawBitmap(Bitmap.createScaledBitmap(background,
					(int) (background.getWidth() * 1.5 * resizeImage),
					(int) (background.getHeight() * 1.5 * resizeImage), true),
					0, 0, paint);

			Bitmap image = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.loading_2, options);

			Bitmap imageScale = Bitmap.createScaledBitmap(image,
					(int) (image.getWidth() * resizeImage),
					(int) (image.getHeight() * resizeImage), true);

			canvas.rotate(rateRotate, imageScale.getWidth() / 2 + 40
					* resizeDistance, imageScale.getHeight() / 2 + 40
					* resizeDistance);
			canvas.drawBitmap(imageScale, 40 * resizeDistance,
					40 * resizeDistance, paint);
		}
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(100);
				rateRotate += 5;
				if (rateRotate == 360) {
					rateRotate = 0;
				}
				handler.sendEmptyMessage(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
