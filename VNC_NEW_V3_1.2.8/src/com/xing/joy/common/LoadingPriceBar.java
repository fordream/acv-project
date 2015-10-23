package com.xing.joy.common;

import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LoadingPriceBar extends View {
	private static final int REZISE_SCALE_BASE = 4;
	Context context;
	private CalculateResize resize;
	private float resizeImage;
	private Paint paint;
	private int price = 0;
	private float resizeDistance;

	private int strNumber[] = { R.drawable.img_0_iphone, R.drawable.img_1_iphone, R.drawable.img_2_iphone, R.drawable.img_3_iphone, R.drawable.img_4_iphone, R.drawable.img_5_iphone, R.drawable.img_6_iphone, R.drawable.img_7_iphone,
			R.drawable.img_8_iphone, R.drawable.img_9_iphone };

	public LoadingPriceBar(Context context) {
		super(context);
		this.context = context;
		init();

	}

	public LoadingPriceBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
		this.invalidate();
	}

	private void init() {
		resize = new CalculateResize((Activity) context);
		resizeImage = resize.getRatioResizeWidth() / resize.getDensity();
		resizeDistance = resize.getRatioResizeWidth();
		paint = new Paint();
		paint.setAntiAlias(true);
		// ----------------------------------------
		// fixbug outofmemory REZISE_SCALE_BASE :
		// use : R.drawable.haikei_iphone_fixoutofmemory and REZISE_SCALE_BASE
		// --------------------------------------
		setBackgroundResource(R.drawable.haikei_iphone_fixoutofmemory);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// ----------------------------------------
		// fixbug outofmemory REZISE_SCALE_BASE :
		// use : R.drawable.haikei_iphone_fixoutofmemory and REZISE_SCALE_BASE
		// --------------------------------------
		// background
		// Bitmap background = BitmapFactory.decodeResource(
		// context.getResources(), R.drawable.haikei_iphone, options);

		// Bitmap background =
		// BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.haikei_iphone_fixoutofmemory, options);
		//
		// canvas.drawBitmap(Bitmap.createScaledBitmap(background, (int)
		// (background.getWidth() * 1.5 * resizeImage * REZISE_SCALE_BASE),
		// (int) (background.getHeight() * 1.5 * resizeImage *
		// REZISE_SCALE_BASE), true), 0, 0, paint);

		if (price == 0) {
			return;
		}

		Bitmap kakaku = BitmapFactory.decodeResource(context.getResources(), R.drawable.kakaku_iphone);
		canvas.drawBitmap(Bitmap.createScaledBitmap(kakaku, (int) (kakaku.getWidth() * resizeImage), (int) (kakaku.getHeight() * resizeImage), true), (0) * resizeDistance, 0 * resizeDistance, paint);

		int[] test = parseNumberToArray("" + price);
		int next = (int) (kakaku.getWidth() * resizeImage);

		for (int i = 0; i < test.length; i++) {
			Bitmap number = BitmapFactory.decodeResource(context.getResources(), strNumber[test[i]]);
			canvas.drawBitmap(Bitmap.createScaledBitmap(number, (int) (number.getWidth() * resizeImage), (int) (number.getHeight() * resizeImage), true), next, 0 * resizeDistance, paint);
			next += (number.getWidth()) * resizeImage;
		}

		Bitmap en = BitmapFactory.decodeResource(context.getResources(), R.drawable.en_iphone);
		canvas.drawBitmap(Bitmap.createScaledBitmap(en, (int) (en.getWidth() * resizeImage), (int) (en.getHeight() * resizeImage), true), next, 0 * resizeDistance, paint);
	}

	protected int[] parseNumberToArray(String price) {
		int[] numbers = new int[price.length()];
		for (int i = 0; i < price.length(); i++) {
			numbers[i] = Integer.parseInt(price.substring(i, i + 1));
		}
		return numbers;
	}

}
