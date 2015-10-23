package com.xing.joy.common;

import java.text.DecimalFormat;

import jp.co.xing.utaehon.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DownloadProgessBar extends View {
	private static final int REZISE_SCALE_BASE = 4;
	public static long MAX = 0;
	public static int typeDataDownload = 0; // 0: free download, 1: Paid
											// download
	public static String STATUS_DOWNLOAD = "";
	public static String PERCENT_DOWNLOAD = "0 %";
	private static String MB_DOWNLOAD = "0";
	private static String MB_DOWNLOADING = "0";

	private int[] deer_id = { R.drawable.dl_deer1, R.drawable.dl_deer2 };
	private int deer_current = 0;
	private Paint paint;
	private Context context;
	private CalculateResize resize;
	private float resizeImage;
	private float resizeDistance;

	private float deerLeft = 0f;
	private int deerDistanceGo = 0;
	private float kageLeft = 0f;
	private int kageDecrease = 0;

	public long sumDataDownload = 0;

	public DownloadProgessBar(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public DownloadProgessBar(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		STATUS_DOWNLOAD = "";
		MB_DOWNLOAD = "0";
		MB_DOWNLOADING = "0";
		PERCENT_DOWNLOAD = "0 %";
		MAX = 0;
		resize = new CalculateResize(context);
		resizeImage = resize.getRatioResizeWidth() / resize.getDensity();
		resizeDistance = resize.getRatioResizeWidth();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setFakeBoldText(true);
		paint.setTextSize(25 * resizeImage);
		deerLeft = 30 * resizeDistance; // standard 65
		kageLeft = 71 * resizeDistance;
	}

	public void reset() {
		STATUS_DOWNLOAD = "";
		MB_DOWNLOAD = "0";
		MB_DOWNLOADING = "0";
		PERCENT_DOWNLOAD = "0 %";
		MAX = 0;
		deer_current = 0;
		deerLeft = 0f;
		deerDistanceGo = 0;
		kageLeft = 0f;
		kageDecrease = 0;
		sumDataDownload = 0;
		deerLeft = 30 * resizeDistance; // standard 65
		kageLeft = 71 * resizeDistance;
		this.invalidate();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// background
		Bitmap background = null;
		// if (typeDataDownload == 0) {
		// background = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.haikei_iphone_free);
		// } else {
		// background = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.haikei_iphone);
		// }

		if (typeDataDownload == 0) {
			background = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.haikei_iphone_free);

			canvas.drawBitmap(Bitmap.createScaledBitmap(background,
					(int) (background.getWidth() * resizeImage),
					(int) (background.getHeight() * resizeImage), true), 0, 0,
					paint);
		} else {
			background = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.haikei_iphone_fixoutofmemory);

			canvas.drawBitmap(
					Bitmap.createScaledBitmap(
							background,
							(int) (background.getWidth() * resizeImage * REZISE_SCALE_BASE),
							(int) (background.getHeight() * resizeImage * REZISE_SCALE_BASE),
							true), 0, 0, paint);
		}

		// canvas.drawBitmap(Bitmap.createScaledBitmap(background,
		// (int) (background.getWidth() * resizeImage),
		// (int) (background.getHeight() * resizeImage), true), 0, 0,
		// paint);

		// deer*
		Bitmap deer = BitmapFactory.decodeResource(context.getResources(),
				deer_id[deer_current]);
		canvas.drawBitmap(Bitmap.createScaledBitmap(deer,
				(int) (deer.getWidth() * resizeImage),
				(int) (deer.getHeight() * resizeImage), true), deerLeft,
				10 * resizeDistance, paint);

		// dl_bar_whole
		Bitmap barWhole = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.dl_bar_whole);
		canvas.drawBitmap(Bitmap.createScaledBitmap(barWhole,
				(int) (barWhole.getWidth() * resizeImage),
				(int) (barWhole.getHeight() * resizeImage), true),
				65 * resizeDistance, 100 * resizeDistance, paint);

		// dl_bar
		Bitmap bar = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.dl_bar);
		canvas.drawBitmap(Bitmap.createScaledBitmap(bar,
				(int) (bar.getWidth() * resizeImage),
				(int) (bar.getHeight() * resizeImage), true),
				71 * resizeDistance, 105 * resizeDistance, paint);

		// dl_kage*
		Bitmap kage = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.dl_kage);
		if (kage.getWidth() * resizeImage - kageDecrease > 1) {
			canvas.drawBitmap(
					Bitmap.createScaledBitmap(kage, (int) (kage.getWidth()
							* resizeImage - kageDecrease),
							(int) (kage.getHeight() * resizeImage), true),
					kageLeft, 105 * resizeDistance, paint);
		}

		// dl_text
		Bitmap text = null;
		if (typeDataDownload == 0) {
			text = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.dl_text_iphone_free);
		} else {
			text = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.dl_text);
		}

		canvas.drawBitmap(Bitmap.createScaledBitmap(text,
				(int) (text.getWidth() * resizeImage),
				(int) (text.getHeight() * resizeImage), true),
				71 * resizeDistance, 105 * resizeDistance, paint);

		if (deerDistanceGo == 0) {
			deerDistanceGo = (int) (text.getWidth() * resizeImage);
		}

		if (!MB_DOWNLOAD.equalsIgnoreCase("")) {
			canvas.drawText(MB_DOWNLOADING + "/" + MB_DOWNLOAD + " MB",
					65 * resizeDistance, 155 * resizeDistance, paint);
		}

		if (!PERCENT_DOWNLOAD.equalsIgnoreCase("")) {
			canvas.drawText(PERCENT_DOWNLOAD, (585 * resizeDistance)
					- (25 * PERCENT_DOWNLOAD.length() * resizeImage),
					155 * resizeDistance, paint);
		}

		if (!STATUS_DOWNLOAD.equalsIgnoreCase("")) {
			canvas.drawText(STATUS_DOWNLOAD, (500 * resizeDistance),
					155 * resizeDistance, paint);
		}

	}

	public static void calculateMaxDownload() {
		MB_DOWNLOAD = new DecimalFormat("#,##0.##").format(MAX
				/ (double) (1024 * 1024));
	}

	public void updateProgessBar(Integer args) {
		sumDataDownload += args;
		if (MAX != 0) {
			PERCENT_DOWNLOAD = sumDataDownload * 100 / MAX + " %";
			if (sumDataDownload * 100 / MAX >= 100) {
				PERCENT_DOWNLOAD = "";
			}
		}
		MB_DOWNLOADING = new DecimalFormat("#,##0.##").format(sumDataDownload
				/ (double) (1024 * 1024));

		if (deerLeft < deerDistanceGo + 30 * resizeDistance) {
			deerLeft += args * 500 * resizeDistance / MAX;
			kageLeft += args * 500 * resizeDistance / MAX;
			kageDecrease = (int) (kageLeft - 71 * resizeDistance);
			switch (deer_current) {
			case 0:
				deer_current = 1;
				break;
			case 1:
				deer_current = 0;
				break;
			}
		}
		this.invalidate();
	}

}
