package jp.co.xing.utaehon03.songs;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3HappybirthdayResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Vol3Happybirthday extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private static final String TAG = "LOG_VOL3HAPPYBIRTHDAY";

	// ===========================================================
	// Fields
	// ===========================================================
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mMainTexturePack;
	private TexturePack mAllitemTexturePack;
	private TexturePack mBoyGirlTexturePack;
	private TexturePack mBackgroundTexturePack;

	private ITextureRegion ttrRTable, ttrRTitle, ttrRHaikei;

	private TiledTextureRegion ballTiledTextureRegion[] = new TiledTextureRegion[Vol3HappybirthdayResouce.listBall.length];
	private TiledTextureRegion ttrRItem[] = new TiledTextureRegion[Vol3HappybirthdayResouce.resourceItem.length];
	private TiledTextureRegion ttrRCake, ttrRBoy, ttrRGirl, ttrRBoyAddons,
			ttrRGirlAddons, leftCrackerTiledTextureRegion,
			rightCrackerTiledTextureRegion;

	private Sprite sTable, sTitle;
	private Entity sBallLayout;

	private AnimatedSprite ballAniSprite[] = new AnimatedSprite[Vol3HappybirthdayResouce.listBall.length];
	private AnimatedSprite anisCake, anisBoy, anisGirl, anisBoyAddons,
			anisGirlAddons, leftCrackerAniSprite, rightCrackerAniSprite;
	private AnimatedSpriteItem anisMove;

	private LinkedList<AnimatedSpriteItem> itemList = new LinkedList<Vol3Happybirthday.AnimatedSpriteItem>();

	private static TimerHandler andHandler, leftBallHandler, rightBallHandler;

	private long tmpTime = 0;
	private int isBallLeft = 0;
	private int isBallRight = 0;
	private int SCROLL = 0;
	private int isCake, isFaceBoy, isFaceGirl;
	private boolean isCANDLE = false;
	private boolean isBLOWCANDLE = false;
	private boolean isBusy = false;
	private boolean isActionBlow = false;
	private boolean isActionCracker = false;
	private boolean isEat = false;

	private int cakePoint[][] = new int[][] { { 10, 435, 55, 375 },
			{ 0, 435, 10, 380 }, { 0, 440, 100, 400 }, { 50, 400, 75, 380 },
			{ 0, 440, 90, 400 }, { 10, 440, 150, 370 } };

	// // Widget Layout
	private HorizontalScrollView hScrollView;
	private LinearLayout linearLayout;
	private RelativeLayout frameItem, rlyRoot;
	private RelativeLayout.LayoutParams adViewLayoutParams;
	private RelativeLayout.LayoutParams frameIteamParams, linearLayoutParams;
	private ImageView imgView[] = new ImageView[Vol3HappybirthdayResouce.resourceItem.length];
	private int idSelect;
	private ResizeView resizeView;

	// private TranslateAnimation transUpDown;

	// Load Sound
	private Sound mp3Boy, mp3Girl, mp3Eat, mp3GirlBoyTouch, mp3BlowCandle,
			mp3Drag, mp3Drop, mp3AfterBlowA, mp3AfterBlowB, mp3Cracker,
			mp3Ball, mp3RootUp, mp3RootDown, mp3Ask;

	private void onCreateViewScroll() {
		Log.d(TAG, "onCreateViewScroll");
		resizeView = new ResizeView();
		// Layout Root
		// adViewLayoutParams = new RelativeLayout.LayoutParams(w, h);
		adViewLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adViewLayoutParams.topMargin = Math.round(-82
				* resizeView.ratioResizeHeight);
		adViewLayoutParams.leftMargin = Math
				.round((float) (157.5 * resizeView.ratioResizeHeight));
		adViewLayoutParams.width = Math
				.round(645 * resizeView.ratioResizeWidth);
		adViewLayoutParams.height = Math
				.round(125 * resizeView.ratioResizeHeight);

		rlyRoot = new RelativeLayout(this);
		rlyRoot.setBackgroundDrawable(getDrawableFromSD(
				Vol3HappybirthdayResouce.A22_09_IPHONE_BAR,
				"happybirthday/gfx/"));

		hScrollView = new HorizontalScrollView(this);
		rlyRoot.addView(hScrollView);
		resizeView.resizeViewFix(hScrollView, 65, 25, 530, 80);
		hScrollView.setHorizontalScrollBarEnabled(false);
		hScrollView.setOnTouchListener(sScrollTouch);

		linearLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				Math.round(80 * resizeView.ratioResizeHeight));
		linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(0);
		hScrollView.addView(linearLayout, linearLayoutParams);

		frameIteamParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		frameItem = new RelativeLayout(this);
		linearLayout.addView(frameItem, frameIteamParams);

		int pX = 0;
		for (int i = 0; i < Vol3HappybirthdayResouce.resourceItem.length; i++) {

			imgView[i] = new ImageView(this);
			imgView[i].setOnTouchListener(viewTouch);
			frameItem.addView(imgView[i]);
			imgView[i].setBackgroundDrawable(getDrawableFromSD(
					Vol3HappybirthdayResouce.resourceItem[i][1],
					"happybirthday/gfx/"));
			resizeView.resizeViewFix(imgView[i], pX, 0, 80, 80);
			pX += 85;
		}

		rlyRoot.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent e) {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {
					scrollTouch(true);
				}
				return true;
			}
		});
		// adViewLayoutParams.width = hScrollView.getWidth();
		relativeLayout.addView(rlyRoot, adViewLayoutParams);
		Log.d(TAG,
				rlyRoot.getWidth() + "rlyRoot xxxxxxxxxxxxxxxx"
						+ rlyRoot.getLeft());
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "load Scense.............................................");
		this.mScene = new Scene();

		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRHaikei, this
						.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

		sTitle = new Sprite(0, 0, ttrRTitle,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTitle);

		// sBallLayout = new Sprite(0, 0, bgTextureRegion,
		// this.getVertexBufferObjectManager());
		sBallLayout = new Entity(0, 0);
		this.mScene.attachChild(sBallLayout);

		// Load boy & girl
		anisBoy = new AnimatedSprite(140, 26, ttrRBoy,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anisBoy);
		anisGirl = new AnimatedSprite(569, 26, ttrRGirl,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anisGirl);

		// Iteam addons
		anisBoyAddons = new AnimatedSprite(140, 26, ttrRBoyAddons,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anisBoyAddons);
		anisGirlAddons = new AnimatedSprite(569, 26, ttrRGirlAddons,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anisGirlAddons);

		sTable = new Sprite(0, 0, ttrRTable,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTable);
		anisCake = new AnimatedSprite(260, 195, ttrRCake,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anisCake);

		leftCrackerAniSprite = new AnimatedSprite(64, 337,
				leftCrackerTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(leftCrackerAniSprite);

		rightCrackerAniSprite = new AnimatedSprite(626, 337,
				rightCrackerTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(rightCrackerAniSprite);

		leftCrackerAniSprite.setVisible(false);
		rightCrackerAniSprite.setVisible(false);

		// ball
		for (int i = 0; i < Vol3HappybirthdayResouce.listBall.length; i++) {

			int pX = Vol3HappybirthdayResouce.listBall[i][0];
			int pY = Vol3HappybirthdayResouce.listBall[i][1];
			ballAniSprite[i] = new AnimatedSprite(pX, pY,
					ballTiledTextureRegion[i],
					this.getVertexBufferObjectManager());
			sBallLayout.attachChild(ballAniSprite[i]);

		}

		addGimmicsButton(
				mScene,
				Vol3HappybirthdayResouce.SOUND_GIMMIC,
				new int[] {
						Vol3HappybirthdayResouce.main.A22_HAPPYBIRTHDAY_1_IPHONE_ID,
						Vol3HappybirthdayResouce.main.A22_HAPPYBIRTHDAY_2_IPHONE_ID,
						Vol3HappybirthdayResouce.main.A22_HAPPYBIRTHDAY_3_IPHONE_ID },
				mMainTexturePack.getTexturePackTextureRegionLibrary());
		Log.d(TAG, "load Scense.............................................");
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				onCreateViewScroll();
			}
		});
		Log.d(TAG, "load Scense.............................................");
	}

	@Override
	protected void loadKaraokeComplete() {

		super.loadKaraokeComplete();

	}

	@Override
	public synchronized void onResumeGame() {
		defaultLoad();
		super.onResumeGame();
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("happybirthday/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("happybirthday/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "happybirthday/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		mMainTexturePack = mTexturePackLoaderFromSource.load("main.xml");
		mAllitemTexturePack = mTexturePackLoaderFromSource.load("allitem.xml");
		mBoyGirlTexturePack = mTexturePackLoaderFromSource.load("boygirl.xml");
		mBackgroundTexturePack = mTexturePackLoaderFromSource
				.load("background.xml");

		mMainTexturePack.loadTexture();
		mAllitemTexturePack.loadTexture();
		mBoyGirlTexturePack.loadTexture();
		mBackgroundTexturePack.loadTexture();

		final TexturePackTextureRegionLibrary mBackgroundTexturePackLib = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary();

		Log.d(TAG, "load ........................Start.....................");

		this.ttrRTable = mBackgroundTexturePackLib
				.get(Vol3HappybirthdayResouce.background.A22_11_IPHONE_TABLE_ID);
		this.ttrRTitle = mBackgroundTexturePackLib
				.get(Vol3HappybirthdayResouce.background.A22_12_IPHONE_TITLE_ID);
		this.ttrRHaikei = mBackgroundTexturePackLib
				.get(Vol3HappybirthdayResouce.background.A22_13_IPHONE_HAIKEI_ID);

		this.ttrRCake = getTiledTextureFromPacker(mMainTexturePack,
				Vol3HappybirthdayResouce.main.A22_03_1_IPHONE_CAKE_ID,
				Vol3HappybirthdayResouce.main.A22_03_2_IPHONE_CAKE_ID,
				Vol3HappybirthdayResouce.main.A22_03_3_IPHONE_CAKE_ID,
				Vol3HappybirthdayResouce.main.A22_03_4_IPHONE_CAKE_ID,
				Vol3HappybirthdayResouce.main.A22_03_5_IPHONE_CAKE_ID,
				Vol3HappybirthdayResouce.main.A22_03_6_IPHONE_CAKE_ID);
		this.ttrRBoy = getTiledTextureFromPacker(mBoyGirlTexturePack,
				Vol3HappybirthdayResouce.boygirl.A22_7_1_IPHONE_BOY_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_2_IPHONE_BOY_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_3_IPHONE_BOY_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_4_IPHONE_BOY_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_5_IPHONE_BOY_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_6_IPHONE_BOY_ID);
		this.ttrRGirl = getTiledTextureFromPacker(mBoyGirlTexturePack,
				Vol3HappybirthdayResouce.boygirl.A22_8_1_IPHONE_GIRL_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_2_IPHONE_GIRL_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_3_IPHONE_GIRL_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_4_IPHONE_GIRL_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_5_IPHONE_GIRL_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_6_IPHONE_GIRL_ID);
		this.ttrRBoyAddons = getTiledTextureFromPacker(mBoyGirlTexturePack,
				Vol3HappybirthdayResouce.boygirl.A22_7_7_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_8_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_9_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_7_10_IPHONE_ITEM_ID);
		this.ttrRGirlAddons = getTiledTextureFromPacker(mBoyGirlTexturePack,
				Vol3HappybirthdayResouce.boygirl.A22_8_7_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_8_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_9_IPHONE_ITEM_ID,
				Vol3HappybirthdayResouce.boygirl.A22_8_10_IPHONE_ITEM_ID);
		this.leftCrackerTiledTextureRegion = getTiledTextureFromPacker(
				mMainTexturePack,
				Vol3HappybirthdayResouce.main.A22_04_1_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_04_2_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_04_3_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_04_4_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_04_5_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_04_6_IPHONE_CRACKER_ID);
		this.rightCrackerTiledTextureRegion = getTiledTextureFromPacker(
				mMainTexturePack,
				Vol3HappybirthdayResouce.main.A22_05_1_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_05_2_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_05_3_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_05_4_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_05_5_IPHONE_CRACKER_ID,
				Vol3HappybirthdayResouce.main.A22_05_6_IPHONE_CRACKER_ID);

		// ===== Load iteam ====
		for (int i = 0; i < Vol3HappybirthdayResouce.resourceItem.length; i++) {
			if (i < Vol3HappybirthdayResouce.IResourceItemFOOD.length) {
				this.ttrRItem[i] = getTiledTextureFromPacker(
						mAllitemTexturePack,
						Vol3HappybirthdayResouce.IResourceItemFOOD[i]);
			} else {
				this.ttrRItem[i] = getTiledTextureFromPacker(
						mAllitemTexturePack,
						Vol3HappybirthdayResouce.IResourceItemCANDLE[i
								- Vol3HappybirthdayResouce.IResourceItemFOOD.length]);
			}

		}
		// load ball
		for (int i = 0; i < Vol3HappybirthdayResouce.IlistBallResource.length; i++) {
			this.ballTiledTextureRegion[i] = getTiledTextureFromPacker(
					mBoyGirlTexturePack,
					Vol3HappybirthdayResouce.IlistBallResource[i]);
		}

	}

	@Override
	protected void loadKaraokeSound() {
		mp3Boy = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_7_3_HA);
		mp3Girl = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_8_3_WA3);
		mp3Eat = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_07_08_4PERON);
		mp3GirlBoyTouch = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_07_08_POWA2);
		mp3BlowCandle = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_07_08_2HU);
		mp3Drag = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_09_PYON);
		mp3Drop = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_09_POMI);
		mp3AfterBlowA = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_07_08_OMEDETO2);
		mp3AfterBlowB = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_PATIHUE);
		mp3Cracker = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_04_05_PAN);
		mp3Ball = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_06_HUSENPAN3);
		mp3RootUp = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_09_MOKIN2_5);
		mp3RootDown = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_09_MOKIN2);
		mp3Ask = loadSoundResourceFromSD(Vol3HappybirthdayResouce.A22_03_SYARAN);
	}

	private void defaultLoad() {
		isCake = 0;
		isFaceBoy = 0;
		isFaceGirl = 0;
		anisBoyAddons.setVisible(false);
		anisGirlAddons.setVisible(false);
		changeCake(false);
		Log.d(TAG, "changeCake: " + isCake);
	}

	private void scrollTouch(boolean psound) {

		// rlyRoot.setEnabled(false);
		int y = 0;
		if (SCROLL == 0) {
			// transUpDown = new TranslateAnimation(0, 0, 0.0f, -0.576f );
			SCROLL = 1;
			y = Math.round(-10 * resizeView.ratioResizeHeight);
			if (psound) {
				mp3RootUp.play();
			}

		} else {
			// transUpDown = new TranslateAnimation(0, 0, 0.0f, 0.576f );
			SCROLL = 0;
			y = Math.round(-82 * resizeView.ratioResizeHeight);
			if (psound) {
				mp3RootDown.play();
			}

		}
		adViewLayoutParams.topMargin = y;
		rlyRoot.setLayoutParams(adViewLayoutParams);

	};

	private void allTouch(TouchEvent pSceneTouchEvent) {
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (checkContains(leftCrackerAniSprite, 0, 175, 100, 290, pX, pY)
					|| checkContains(rightCrackerAniSprite, 165, 175, 270, 285,
							pX, pY)) {
				onCLickCracker();
			}
			break;
		}

	}

	private boolean ballTouch(int pX, int pY) {
		boolean xReturn = false;
		if (!isEat) {

			for (int i = 0; i < Vol3HappybirthdayResouce.listBall.length; i++) {
				// final int value = i;
				int x1 = Vol3HappybirthdayResouce.listBall[i][2];
				int y1 = Vol3HappybirthdayResouce.listBall[i][3];
				int x2 = Vol3HappybirthdayResouce.listBall[i][4];
				int y2 = Vol3HappybirthdayResouce.listBall[i][5];

				if (checkContains(ballAniSprite[i], x1, y1, x2, y2, pX, pY)
						&& ballAniSprite[i].isVisible()
						&& !ballAniSprite[i].isAnimationRunning()) {

					xReturn = true;

					long sDuration[] = { 500 };
					int sFrame[] = { 1 };
					mp3Ball.play();

					ballAniSprite[i].registerEntityModifier(new AlphaModifier(
							0.5f, 1.0f, 0.0f));

					ballAniSprite[i].animate(sDuration, sFrame, 0,
							new IAnimationListener() {

								@Override
								public void onAnimationFinished(
										AnimatedSprite pAnimatedSprite) {
									pAnimatedSprite.setVisible(false);

									if (pAnimatedSprite
											.equals(ballAniSprite[0])
											|| pAnimatedSprite
													.equals(ballAniSprite[1])
											|| pAnimatedSprite
													.equals(ballAniSprite[2])
											|| pAnimatedSprite
													.equals(ballAniSprite[3])
											|| pAnimatedSprite
													.equals(ballAniSprite[4])
											|| pAnimatedSprite
													.equals(ballAniSprite[5])) {
										isBallLeft++;
									} else if (pAnimatedSprite
											.equals(ballAniSprite[6])
											|| pAnimatedSprite
													.equals(ballAniSprite[7])
											|| pAnimatedSprite
													.equals(ballAniSprite[8])
											|| pAnimatedSprite
													.equals(ballAniSprite[9])
											|| pAnimatedSprite
													.equals(ballAniSprite[10])
											|| pAnimatedSprite
													.equals(ballAniSprite[11])) {
										isBallRight++;
									}

									if (isBallLeft >= 6) {
										if (leftBallHandler != null) {
											mScene.unregisterUpdateHandler(leftBallHandler);
										}
										leftBallHandler = new TimerHandler(
												0.4f, new ITimerCallback() {

													@Override
													public void onTimePassed(
															TimerHandler paramTimerHandler) {
														for (int i = 0; i < 6; i++) {
															Log.d(TAG, "Vo day");
															// ballAniSprite[i].registerEntityModifier(null);
															ballAniSprite[i]
																	.setAlpha(1);
															ballAniSprite[i]
																	.setVisible(true);
															ballAniSprite[i]
																	.setCurrentTileIndex(0);

														}
														isBallLeft = 0;
													}
												});
										mScene.registerUpdateHandler(leftBallHandler);
									}

									if (isBallRight >= 6) {
										if (rightBallHandler != null) {
											mScene.unregisterUpdateHandler(rightBallHandler);
										}
										rightBallHandler = new TimerHandler(
												0.4f, new ITimerCallback() {

													@Override
													public void onTimePassed(
															TimerHandler paramTimerHandler) {
														for (int i = 6; i < 12; i++) {

															// ballAniSprite[i].registerEntityModifier(null);
															ballAniSprite[i]
																	.setAlpha(1);
															ballAniSprite[i]
																	.setVisible(true);
															ballAniSprite[i]
																	.setCurrentTileIndex(0);

														}
														isBallRight = 0;
													}
												});
										mScene.registerUpdateHandler(rightBallHandler);
									}

								}

								@Override
								public void onAnimationFrameChanged(
										AnimatedSprite arg0, int arg1, int arg2) {
								}

								@Override
								public void onAnimationLoopFinished(
										AnimatedSprite arg0, int arg1, int arg2) {
								}

								@Override
								public void onAnimationStarted(
										AnimatedSprite arg0, int arg1) {
								}

							});

					ballController(i);

					break;
				}
			}
		}
		return xReturn;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		allTouch(pSceneTouchEvent);
		if (isBusy) {
			return true;
		}
		switch (pSceneTouchEvent.getAction()) {

		case TouchEvent.ACTION_DOWN:
			Log.d(TAG, "isBLOWCANDLE" + isBLOWCANDLE);

			if (ballTouch((int) pSceneTouchEvent.getX(),
					(int) pSceneTouchEvent.getY())) {
				break;
			}

			if (checkContains(anisBoy, 40, 90, 200, 250,
					(int) pSceneTouchEvent.getX(),
					(int) pSceneTouchEvent.getY())) {
				if (isBLOWCANDLE) {
					blowCandle();
				} else {
					mp3GirlBoyTouch.play();
					faceBoy();
				}

			} else if (checkContains(anisGirl, 50, 90, 210, 250,
					(int) pSceneTouchEvent.getX(),
					(int) pSceneTouchEvent.getY())) {
				if (isBLOWCANDLE) {
					blowCandle();
				} else {
					mp3GirlBoyTouch.play();
					faceGirl();
				}

			}
			break;
		case TouchEvent.ACTION_MOVE:
			Log.d(TAG, "pSceneTouchEvent - ACTION_MOVE");
			break;
		}
		return true;
	}

	@Override
	public void combineGimmic3WithAction() {
		changeCake(true);
	}

	/** onPauseGame **/
	@Override
	public void onPauseGame() {

		if (leftCrackerAniSprite != null) {
			leftCrackerAniSprite.setVisible(false);
		}
		if (rightCrackerAniSprite != null) {
			rightCrackerAniSprite.setVisible(false);
		}
		Iterator<AnimatedSpriteItem> projectiles = itemList.iterator();
		AnimatedSpriteItem _projectile;
		while (projectiles.hasNext()) {
			_projectile = projectiles.next();
			if (_projectile.idItem >= 0) {
				_projectile.die();
			}
		}
		_projectile = null;
		projectiles = null;
		itemList.clear();

		if (anisBoyAddons != null) {
			anisBoyAddons.setVisible(false);
		}
		isFaceBoy = 0;
		if (anisGirlAddons != null) {
			anisGirlAddons.setVisible(false);
		}
		isFaceGirl = 0;
		SCROLL = 0;
		isCake = 0;
		isCANDLE = false;
		isBLOWCANDLE = false;
		isBusy = false;
		isActionBlow = false;
		isActionCracker = false;
		isEat = false;

		for (int i = 0; i < 12; i++) {
			if (ballAniSprite[i] != null) {
				ballAniSprite[i].setAlpha(1);
				ballAniSprite[i].setVisible(true);
				ballAniSprite[i].setCurrentTileIndex(0);
			}
			isBallLeft = 0;
			isBallRight = 0;

		}
		if (SCROLL == 0) {
			scrollTouch(false);
		}
		if (anisBoy.isAnimationRunning()) {
			anisBoy.stopAnimation();
		}
		if (anisGirl.isAnimationRunning()) {
			anisGirl.stopAnimation();
		}
		anisBoy.setCurrentTileIndex(0);
		anisGirl.setCurrentTileIndex(0);

		super.onPauseGame();
	}

	// ===========================================================
	// Function and Class Action
	// ===========================================================
	private void ballController(int nID) {
		Calendar now = Calendar.getInstance();
		long newtime = now.getTimeInMillis();
		if ((newtime - tmpTime) <= 500) {
			long duration[] = { 1000, 100 };
			int frame[] = { 2, 0 };
			if (nID <= 5) {
				mp3Boy.play();
				anisBoy.animate(duration, frame, 0);
			} else {
				mp3Girl.play();
				anisGirl.animate(duration, frame, 0);
			}
		} else {
			mp3Boy.play();
			mp3Girl.play();
			long duration[] = { 1000, 100 };
			int frame[] = { 2, 0 };
			anisBoy.animate(duration, frame, 0);
			anisGirl.animate(duration, frame, 0);
		}

		Log.d(TAG, "isBallLeft " + isBallLeft + " isBallRight " + isBallRight);
		tmpTime = newtime;
	}

	private void resetAll() {

		// remove all item
		leftCrackerAniSprite.setVisible(false);
		rightCrackerAniSprite.setVisible(false);

		if (andHandler != null) {
			mScene.unregisterUpdateHandler(andHandler);
		}
		andHandler = new TimerHandler(1.0f, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler paramTimerHandler) {
				Iterator<AnimatedSpriteItem> projectiles = itemList.iterator();
				AnimatedSpriteItem _projectile;
				while (projectiles.hasNext()) {
					_projectile = projectiles.next();
					if (_projectile.idItem >= 0) {
						_projectile.die();
					}
				}
				_projectile = null;
				projectiles = null;
				itemList.clear();

				anisBoyAddons.setVisible(false);
				isFaceBoy = 0;
				anisGirlAddons.setVisible(false);
				isFaceGirl = 0;

				isCANDLE = false;
				isBLOWCANDLE = false;
				isBusy = false;
				isActionBlow = false;
				isActionCracker = false;
			}
		});
		mScene.registerUpdateHandler(andHandler);

	}

	private void onCLickCracker() {
		if (leftCrackerAniSprite.isVisible() && !isEat) {

			isBusy = true;

			if (!isActionCracker) {
				isActionCracker = true;
			}

			if (!leftCrackerAniSprite.isAnimationRunning()) {

				mp3Cracker.play();

				long sDuraion[] = new long[] { 440, 440, 440, 440, 440, 50 };
				int frame[] = new int[] { 1, 2, 3, 4, 5, 0 };

				leftCrackerAniSprite.animate(sDuraion, frame, 0);
				rightCrackerAniSprite.animate(sDuraion, frame, 0);

			}

			if (!anisBoy.isAnimationRunning()) {
				mp3AfterBlowA.play();
				mp3AfterBlowB.play();
				long sDuraionA[] = new long[] { 550, 550, 550, 550, 550, 50 };
				int frameA[] = new int[] { 2, 4, 5, 4, 5, 0 };
				anisBoy.animate(sDuraionA, frameA, 0);
				anisGirl.animate(sDuraionA, frameA, 0, this);
			}
		}
	}

	private void updateIsCracker() {
		Iterator<AnimatedSpriteItem> projectiles = itemList.iterator();
		AnimatedSpriteItem _projectile;
		isCANDLE = false;
		isBLOWCANDLE = false;

		while (projectiles.hasNext()) {
			_projectile = projectiles.next();
			if (_projectile.isType.equals("CANDLE")) {
				isCANDLE = true;
				if (_projectile.isFire) {
					isBLOWCANDLE = true;
					break;
				}

			}
		}

		if (!isCANDLE) {

			isActionCracker = false;
			leftCrackerAniSprite.setVisible(false);
			rightCrackerAniSprite.setVisible(false);

		}
		Log.d(TAG, "isCANDLE " + isCANDLE + " /isBLOWCANDLE " + isBLOWCANDLE);
	}

	private void blowCandle() {

		isBusy = true;
		isActionBlow = true;
		// Boy and Girl Action Blow
		long sDuraion[] = new long[] { 700, 550, 550, 550, 550, 50 };
		int frame[] = new int[] { 1, 4, 5, 4, 5, 0 };
		anisBoy.animate(sDuraion, frame, 0);
		anisGirl.animate(sDuraion, frame, 0, this);
		mp3BlowCandle.play();
		playSoundDelay(mp3AfterBlowA, 0.7f);
		playSoundDelay(mp3AfterBlowB, 0.7f);
		// Check CANDLE
		Iterator<AnimatedSpriteItem> projectiles = itemList.iterator();
		AnimatedSpriteItem _projectile;
		while (projectiles.hasNext()) {
			_projectile = projectiles.next();
			if (_projectile.isFire) {
				_projectile.blowCandle();
			}
		}

	}

	private void changeCake(boolean isPlaySound) {

		if (isCake == ttrRCake.getTileCount()) {
			isCake = 0;
		}
		if (isPlaySound) {
			mp3Ask.play();
		}
		if (anisCake != null) {
			anisCake.setCurrentTileIndex(isCake);
		}
		isCake++;
	}

	// Boy Action
	private void eatBoy() {
		isEat = true;
		long sDuraion[] = new long[] { 500, 500, 50 };
		int frame[] = new int[] { 2, 3, 0 };
		anisBoy.animate(sDuraion, frame, 0, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				isBusy = false;
				isEat = false;
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			}
		});
	}

	private void faceBoy() {
		Log.d(TAG, "faceBoy:" + isFaceBoy);
		if (isFaceBoy == ttrRBoyAddons.getTileCount()) {
			anisBoyAddons.setVisible(false);
			isFaceBoy = 0;
			return;
		} else {
			anisBoyAddons.setCurrentTileIndex(isFaceBoy);
			if (!anisBoyAddons.isVisible()) {
				anisBoyAddons.setVisible(true);
			}
			isFaceBoy++;
			return;
		}
	}

	// Girl action
	private void eatGirl() {
		isEat = true;
		long sDuraion[] = new long[] { 500, 500, 50 };
		int frame[] = new int[] { 2, 3, 0 };
		anisGirl.animate(sDuraion, frame, 0, new IAnimationListener() {
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				isBusy = false;
				isEat = false;
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			}
		});
	}

	private void faceGirl() {
		Log.d(TAG, "faceGirl:" + isFaceGirl);
		if (isFaceGirl == ttrRGirlAddons.getTileCount()) {
			anisGirlAddons.setVisible(false);
			isFaceGirl = 0;
			return;
		} else {
			long sDuraion[] = new long[] { 50 };
			int frame[] = new int[] { isFaceGirl };
			anisGirlAddons.setCurrentTileIndex(isFaceGirl);
			anisGirlAddons.animate(sDuraion, frame, 0);
			if (!anisGirlAddons.isVisible()) {
				anisGirlAddons.setVisible(true);

			}
			isFaceGirl++;

			return;
		}
	}

	private int getIdIteam(View v) { // getIdIteam;
		int id = -1;
		for (int i = 0; i < Vol3HappybirthdayResouce.resourceItem.length; i++) {
			if (imgView[i] == v) {
				id = i;
				break;
			}
		}
		return id;
	}

	View.OnTouchListener sScrollTouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getPointerCount() >= 2 || isBusy) {
				return true;
			}
			boolean xreturn = false;
			if (idSelect < 0 || anisMove == null) {
				return false;
			}
			float x_cord = event.getRawX();
			float y_cord = event.getRawY();

			if (y_cord >= (hScrollView.getTop() + hScrollView.getHeight())) {
				xreturn = true;
			}

			anisMove.moveXY(x_cord / resizeView.ratioResizeWidth
					- resizeView.hemBlackWidth, y_cord
					/ resizeView.ratioResizeHeight - resizeView.hemBlackHeight);

			Log.d(TAG, "Move[" + idSelect + "]" + x_cord);

			if (event.getAction() == MotionEvent.ACTION_UP) {
				actionUp(x_cord / resizeView.ratioResizeWidth
						- resizeView.hemBlackWidth, y_cord
						/ resizeView.ratioResizeHeight
						- resizeView.hemBlackHeight);
			}
			return xreturn;
		}
	};

	View.OnTouchListener viewTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getPointerCount() >= 2 || isBusy) {
				return true;
			}

			float x_cord = event.getRawX();
			float y_cord = event.getRawY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (SCROLL == 0) {
					scrollTouch(true);
					return true;
				}
				Log.d(TAG,
						"W" + anisCake.getWidth() + "H" + anisCake.getHeight());
				idSelect = getIdIteam(v);
				if (idSelect < 0) {
					return false;
				}
				if (anisMove == null) {
					TiledTextureRegion ttrR = ttrRItem[idSelect].deepCopy();
					AnimatedSpriteItem spriteItem = new AnimatedSpriteItem(
							x_cord / resizeView.ratioResizeWidth
									- resizeView.hemBlackWidth, y_cord
									/ resizeView.ratioResizeHeight
									- resizeView.hemBlackHeight, ttrR,
							Vol3Happybirthday.this
									.getVertexBufferObjectManager());
					spriteItem.isType = Vol3HappybirthdayResouce.resourceItem[idSelect][0];
					spriteItem.idItem = idSelect;
					mScene.attachChild(spriteItem);
					spriteItem.drag();
					anisMove = spriteItem;
					mp3Drag.play();
					Log.d(TAG, "Create img " + idSelect + " is "
							+ spriteItem.isType);
				}
				Log.d(TAG, "ACTION_DOWN: " + getIdIteam(v));
				break;
			case MotionEvent.ACTION_MOVE:
				if (idSelect < 0 || anisMove == null) {
					break;
				}

				anisMove.moveXY(x_cord / resizeView.ratioResizeWidth
						- resizeView.hemBlackWidth, y_cord
						/ resizeView.ratioResizeHeight
						- resizeView.hemBlackHeight);
				Log.d(TAG, "ACTION_MOVE: " + idSelect);
				break;
			case MotionEvent.ACTION_UP:
				if (idSelect < 0 || anisMove == null) {
					break;
				}
				actionUp(x_cord / resizeView.ratioResizeWidth
						- resizeView.hemBlackWidth, y_cord
						/ resizeView.ratioResizeHeight
						- resizeView.hemBlackHeight);
				break;

			}
			return true;
		}
	};

	private void actionUp(float nX, float nY) { // type 960 * 640;
		Log.d(TAG, "isCake:" + isCake);
		if (anisMove.isVisible() && anisMove.isType.equals("FOOD")
				&& checkContains(anisBoy, 40, 90, 200, 250, (int) nX, (int) nY)) {
			anisMove.idItem = -1;
			anisMove.dieDelay(1.0f);
			mp3Boy.play();
			playSoundDelay(mp3Eat, 0.5f);
			eatBoy();
			itemList.remove(this);
			mp3Drop.play();
		} else if (anisMove.isVisible()
				&& anisMove.isType.equals("FOOD")
				&& checkContains(anisGirl, 50, 90, 210, 250, (int) nX, (int) nY)) {
			anisMove.idItem = -1;
			anisMove.dieDelay(1.0f);
			mp3Girl.play();
			playSoundDelay(mp3Eat, 0.5f);
			eatGirl();
			itemList.remove(this);
			mp3Drop.play();
		} else if (anisMove.isVisible()
				&& checkContains(anisCake, cakePoint[isCake - 1][0],
						cakePoint[isCake - 1][2], cakePoint[isCake - 1][1],
						cakePoint[isCake - 1][3], Math.round(nX),
						Math.round(nY))) {
			Log.d(TAG, "Ok up 1");

			itemList.add(anisMove);
			mp3Drop.play();
			if (anisMove.isType.equals("CANDLE")) {

				isBLOWCANDLE = true;
				isCANDLE = true;
				isActionBlow = false;
				anisMove.isFire = true;

				if (!leftCrackerAniSprite.isVisible()) {
					leftCrackerAniSprite.setVisible(true);
					rightCrackerAniSprite.setVisible(true);
				}
			}
			anisMove.drop();
			this.mScene.registerTouchArea(anisMove);
			Log.d(TAG, "Ok up");
		} else {
			Log.d(TAG, "Die up");
			anisMove.die();

		}

		idSelect = -1;
		anisMove = null;

		return;
	}

	// =========================
	// AnimatedSprite Class
	// =========================

	class AnimatedSpriteItem extends AnimatedSprite {
		public int idItem = -1;
		public String isType = "";
		public boolean isFire = true;
		private AnimatedSpriteItem myThis;
		private TimerHandler handler;

		public AnimatedSpriteItem(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertext) {
			super(pX, pY, pTiledTextureRegion, pVertext);
			this.myThis = this;
			this.isFire = true;
		}

		public void moveXY(float pX, float pY) {
			this.setPosition(pX - this.getWidth() / 2, pY - this.getHeight()
					/ 2);
		}

		public void blowCandle() {
			if (isType.equals("CANDLE")) {
				long sDuraion[] = new long[] { 300, 300, 300, 300, 50 };
				int frame[] = new int[] { 2, 3, 2, 3, 4 };
				this.animate(sDuraion, frame, 0);
				this.isFire = false;
			}
		}

		public void drag() {

		}

		public void drop() {
			Log.d(TAG, "Add new is " + isType + "isFire + " + isFire);

			if (isType.equals("CANDLE")) {
				if (isFire) {
					this.setCurrentTileIndex(1);
				} else {
					long sDuraion[] = new long[] { 50 };
					int frame[] = new int[] { 4 };
					this.animate(sDuraion, frame, 0);
				}
			}
		}

		public void dieDelay(float delayMillis) {
			this.unregisterUpdateHandler(handler);
			this.registerUpdateHandler(handler = new TimerHandler(delayMillis,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							Log.d(TAG, "die delay");
							myThis.die();
						}
					}));
		}

		public void die() {
			idItem = -1;
			isType = null;
			isFire = true;
			handler = null;
			this.clearEntityModifiers();
			this.clearUpdateHandlers();
			this.setPosition(-999, -999);
			Vol3Happybirthday.this.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					Vol3Happybirthday.this.mScene.detachChild(myThis);
				}
			});

		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int eventaction = pSceneTouchEvent.getAction();
			if (idItem < 0 || isBusy) {
				return true;
			}
			float X = pSceneTouchEvent.getX();
			float Y = pSceneTouchEvent.getY();

			switch (eventaction) {
			case TouchEvent.ACTION_DOWN:
				this.drag();

				break;
			case TouchEvent.ACTION_MOVE: {
				this.setPosition(X - this.getWidth() / 2, Y - this.getHeight()
						/ 2);
				break;
			}
			case TouchEvent.ACTION_UP:
				// An banh
				if (isType.equals("FOOD")
						&& checkContains(anisBoy, 40, 90, 200, 250, (int) X,
								(int) Y)) {
					idItem = -1;
					this.dieDelay(1.0f);
					mp3Boy.play();
					playSoundDelay(mp3Eat, 0.5f);
					eatBoy();
					itemList.remove(this);
					mp3Drop.play();
					break;
				}

				if (isType.equals("FOOD")
						&& checkContains(anisGirl, 50, 90, 210, 250, (int) X,
								(int) Y)) {
					idItem = -1;
					this.dieDelay(1.0f);
					mp3Girl.play();
					playSoundDelay(mp3Eat, 0.5f);
					eatGirl();
					itemList.remove(this);
					mp3Drop.play();
					break;
				}

				if (checkContains(anisCake, cakePoint[isCake - 1][0],
						cakePoint[isCake - 1][2], cakePoint[isCake - 1][1],
						cakePoint[isCake - 1][3], Math.round(X), Math.round(Y))) {
					this.drop();

				} else {
					Log.d(TAG, "itemList" + itemList.size());
					this.die();
					itemList.remove(this);
					updateIsCracker();
					Log.d(TAG, "itemList" + itemList.size());

				}
				break;
			}
			return true;
		}

	}

	class ResizeView {
		private static final String LOG_RESIZEVIEW = "LOG_RESIZEVIEW_HAPPY";
		public DisplayMetrics metrics;
		public float ratioResizeWidth;
		public float ratioResizeHeight;
		public int hemBlackWidth = 0, hemBlackHeight = 0;
		private int maxWidth = 960, maxHeight = 640;
		public int statusHem = 0;

		ResizeView() {
			metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			Log.d(LOG_RESIZEVIEW, "CHANGE..." + metrics.toString());
			if (metrics.widthPixels < metrics.heightPixels) {
				int temp = metrics.widthPixels;
				metrics.widthPixels = metrics.heightPixels;
				metrics.heightPixels = temp;
			}

			// Height bar for 3.0
			// SharedPreferences shareApplicationData =
			// Vol3Happybirthday.this.getSharedPreferences("application_data",
			// 1);
			int heightBar = 0;
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
			Log.d(TAG, "ratioResizeWidth" + ratioResizeWidth
					+ "ratioResizeHeight" + ratioResizeHeight + "hemBlackWidth"
					+ hemBlackWidth + "hemBlackHeight" + hemBlackHeight);
		}

		protected void resizeViewFix(View view, int left, int top, int width,
				int height) {
			RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
					Math.round(width * this.ratioResizeWidth),
					Math.round(height * this.ratioResizeHeight));
			prView.setMargins(Math.round(left * this.ratioResizeWidth),
					Math.round(top * this.ratioResizeHeight), 0, 0);
			view.setLayoutParams(prView);
		}
	}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		if (isActionBlow) {
			isBLOWCANDLE = false;
		}
		if (isActionBlow && isActionCracker) {
			resetAll();
		} else {
			isBusy = false;
		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
	}

}
