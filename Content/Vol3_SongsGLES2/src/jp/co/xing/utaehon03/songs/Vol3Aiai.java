package jp.co.xing.utaehon03.songs;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AiaiResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Vol3Aiai extends BaseGameFragment implements
		IOnSceneTouchListener {

	private static final String TAG = "LOG_VOL3AIAI";
	private static final int A = 1, B = 2, C = 3;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mMainTexturePack;
	private TexturePack mArrMonkeyTexturePack;
	private TexturePack mArrMonkeyScrollTexturePack;
	private TexturePack mArrMonkeyScrollStickTexturePack;
	private TexturePack mArrTreeTexturePack;
	
	private TexturePackTextureRegionLibrary mMainTexturePackLibs;
	// private static
	private static boolean isSquirrel = true;
	private static boolean isDraw = true;
	private static boolean isApplicationBusy = false;


	/** TextureRegion **/
	private TextureRegion mHaikeiTextureRegion;
	private TextureRegion mCloudTextureRegion[] = new TextureRegion[4];
	private TextureRegion mHoleTextureRegion;
	private TextureRegion mMonkeyHandMobileTextureRegion;
	private TextureRegion mMonkeyEyeTextureRegion;
	private TextureRegion mListIteamMoveTextureRegion[] = new TextureRegion[Vol3AiaiResouce.arrListIteamTouch.length];

	/** TiledTextureRegion **/
	private TiledTextureRegion mSunTiledTextureRegion;
	private TiledTextureRegion mTreeTiledTextureRegion;
	private TiledTextureRegion mBagwormTiledTextureRegion;
	private TiledTextureRegion mBirdTiledTextureRegion;
	private TiledTextureRegion mSquirrelTiledTextureRegion;
	private TiledTextureRegion mMonkeyTiledTextureRegion;
	private TiledTextureRegion mMonkeyScrollTiledTextureRegion;
	private TiledTextureRegion mMonkeyScrollPickTiledTextureRegion;

	/** Sprite **/
	private Sprite mCloudLayoutSprite;
	private Sprite mCloudSprite[] = new Sprite[4];
	private Sprite mHoleSprite;
	private MonkeyLayoutSprite mMonkeyLayout;
	private MoveSprite mMoveSprite;

	/** AnimatedSprite **/
	private AnimatedSprite mSunAnimatedSprite;
	private AnimatedSprite mTreeAnimatedSprite;
	private AnimatedSprite mBagwormAnimatedSprite;
	private AnimatedSprite mBirdAnimatedSprite;
	private AnimatedSprite mSquirrelAnimatedSprite;

	private Sound oggMyon, oggPiyo, oggPyokotoko, oggWaraigoe, oggNeiki,
			oggGiro, oggMonkey, oggKira3, oggHowa, oggUn, oggKacha, oggKyu,
			oggTaberu;

	// Anroid widget
	private ScrollView mScrollView;
	private LinearLayout linearLayout;
	private RelativeLayout frameItem;
	private ImageView imgbackground, imgScrollViewHeader;
	private ImageViewIteam imgIteam[] = new ImageViewIteam[Vol3AiaiResouce.arrIteam.length];
	private RelativeLayout.LayoutParams mScrollViewLayoutParams, imgHeaderLayoutParams;
	private RelativeLayout.LayoutParams linearLayoutLayoutParams,
			imgLayoutParams;
	// private RelativeLayout.LayoutParams mScrollViewLayoutParams;
	private ResizeView mResizeView;

	public static final int ListIteam[][] = new int[][] {
			{ A, 0, 0, 1 },
			{ A, 104, 2, 3 },
			{ A, 208, 4, 5 },
			{ A, 305, 6, 7 },
			{ B, 400, 335, 13 }, // Hat
			{ B, 505, 335, 201 }, // Glass
			{ B, 609, 457, 131 }, // RIBBON
			{ B, 705, 357, 55 }, // CROWN
			{ C, 805, 0 }, { B, 910, 375, 262 }, { B, 1005, 267, 13 },
			{ B, 1105, 267, 13 }, { B, 1200, 299, 176 }, { C, 1300, 1 },
			{ C, 1400, 2 } };

	/*******************************
	 * Using for basegame activity *
	 *******************************/
	
	/*private Vol3Aiai getActivity(){
			return Vol3Aiai.this;
	}*/
	
	private void loadAndroidFrame() {
		mResizeView = new ResizeView();
		// Create widget
		imgScrollViewHeader = new ImageView(getActivity());
		imgHeaderLayoutParams = new RelativeLayout.LayoutParams(
				Math.round(109 * mResizeView.ratioResizeWidth),
				Math.round(41 * mResizeView.ratioResizeHeight));
		imgHeaderLayoutParams.leftMargin = Math
				.round(80 * mResizeView.ratioResizeWidth);
		imgHeaderLayoutParams.topMargin = Math
				.round(106 * mResizeView.ratioResizeHeight);
		imgScrollViewHeader.setBackgroundDrawable(getDrawableFromSD(
				Vol3AiaiResouce.SCROLL_HEADER_IPHONE, "aiai/gfx/"));

		mScrollView = new ScrollView(getActivity());
		mScrollView.setVerticalFadingEdgeEnabled(false);
		mScrollView.setHapticFeedbackEnabled(false);
		mScrollView.setFadingEdgeLength(0);
		mScrollViewLayoutParams = new RelativeLayout.LayoutParams(
				Math.round(109 * mResizeView.ratioResizeWidth),
				Math.round(415 * mResizeView.ratioResizeHeight));
		// -405
		mScrollViewLayoutParams.leftMargin = Math
				.round(80 * mResizeView.ratioResizeWidth);
		mScrollViewLayoutParams.topMargin = Math
				.round(146 * mResizeView.ratioResizeHeight);
		Log.d(TAG, "topMargin" + mScrollViewLayoutParams.topMargin);

		linearLayout = new LinearLayout(getActivity());
		linearLayoutLayoutParams = new RelativeLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		mScrollView.addView(linearLayout, linearLayoutLayoutParams);
		mScrollView.setVerticalScrollBarEnabled(false);

		frameItem = new RelativeLayout(getActivity());
		linearLayout.addView(frameItem, linearLayoutLayoutParams);

		imgbackground = new ImageView(getActivity());
		imgLayoutParams = new android.widget.RelativeLayout.LayoutParams(
				Math.round(109 * mResizeView.ratioResizeWidth),
				Math.round(1519 * mResizeView.ratioResizeHeight));

		frameItem.addView(imgbackground, imgLayoutParams);
		imgbackground.setBackgroundDrawable(getDrawableFromSD(
				Vol3AiaiResouce.SCROLL_BACKGROUND_IPHONE, "aiai/gfx/"));

		// add iteam
		for (int i = 0; i < ListIteam.length; i++) {
			imgIteam[i] = new ImageViewIteam(getActivity(), i, ListIteam[i][0]);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					Math.round(91 * mResizeView.ratioResizeWidth),
					Math.round(94 * mResizeView.ratioResizeHeight));
			lp.leftMargin = Math.round(10 * mResizeView.ratioResizeWidth);
			lp.topMargin = Math.round(ListIteam[i][1]
					* mResizeView.ratioResizeHeight);
			imgIteam[i].setBackgroundDrawable(getDrawableFromSD(
					Vol3AiaiResouce.arrListIteam[i], "aiai/gfx/"));
			frameItem.addView(imgIteam[i], lp);
			imgIteam[i].setOnTouchListener(iteamTouchListener);
		}

		mScrollView.setOnTouchListener(scrollTouchListener);

		relativeLayout.addView(imgScrollViewHeader, imgHeaderLayoutParams);
		relativeLayout.addView(mScrollView, mScrollViewLayoutParams);
	}

	@Override
	public void onPauseGame() {
		System.gc();
		if (mMonkeyLayout != null) {
			mMonkeyLayout.resetAll();
		}
		isSquirrel = true;
		isDraw = true;
		isApplicationBusy = false;

		System.gc();
		Log.d(TAG, "onPauseGame");
		super.onPauseGame();
	}

	@Override
	public void onDestroy() {

		Log.d(TAG, "onDestroy");
		for (int i = 0; i < Vol3AiaiResouce.arrIteam.length; i++) {
			setNullImageView(imgIteam[i]);
			imgIteam[i] = null;
		}
		setNullImageView(imgbackground);
		setNullImageView(imgScrollViewHeader);
		setNullImageView(frameItem);
		setNullImageView(linearLayout);
		setNullImageView(mScrollView);
		imgScrollViewHeader = null;
		imgbackground = null;
		System.gc();
		super.onDestroy();
	}

	protected void setNullImageView(View pView) {
		if(pView == null){
			return;
		}
		if (pView.getBackground() != null) {
			pView.getBackground().setCallback(null);
		}
		if (pView instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) pView).getChildCount(); i++) {
				setNullImageView(((ViewGroup) pView).getChildAt(i));
			}
			((ViewGroup) pView).removeAllViews();
		}
	}
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("aiai/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("aiai/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "aiai/gfx/");
		super.onCreateResources();
	}


	@Override
	protected void loadKaraokeResources() {
		
		mMainTexturePack = mTexturePackLoaderFromSource.load("main.xml");
		mArrTreeTexturePack = mTexturePackLoaderFromSource.load("arrTree.xml");
		mArrMonkeyTexturePack = mTexturePackLoaderFromSource.load("arrMonkey.xml");
		mArrMonkeyScrollTexturePack = mTexturePackLoaderFromSource.load("arrMonkeyScroll.xml"); 
		mArrMonkeyScrollStickTexturePack = mTexturePackLoaderFromSource.load("arrMonkeyScrollStick.xml");
		
		mMainTexturePack.loadTexture();
		mArrTreeTexturePack.loadTexture();
		mArrMonkeyTexturePack.loadTexture();
		mArrMonkeyScrollTexturePack.loadTexture();
		mArrMonkeyScrollStickTexturePack.loadTexture();

		final TexturePackTextureRegionLibrary mHaikeiTextureLibs = mArrTreeTexturePack.getTexturePackTextureRegionLibrary();
		mMainTexturePackLibs = mMainTexturePack.getTexturePackTextureRegionLibrary();
		
		this.mHaikeiTextureRegion = mHaikeiTextureLibs.get(Vol3AiaiResouce.arrITree.A1_18_1_IPHONE_HAIKEI_ID);
		
		this.mSunTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack, 
				Vol3AiaiResouce.main.A1_11_1_IPHONE_SUN_ID, Vol3AiaiResouce.main.A1_11_2_IPHONE_SUN_ID);
		
		this.mCloudTextureRegion[0] = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_12_1_IPHONE_CLOUD_ID);
		this.mCloudTextureRegion[1] = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_12_2_IPHONE_CLOUD_ID);
		this.mCloudTextureRegion[2] = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_12_3_IPHONE_CLOUD_ID);
		this.mCloudTextureRegion[3] = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_12_4_IPHONE_CLOUD_ID);
		
		this.mTreeTiledTextureRegion = getTiledTextureFromPacker(mArrTreeTexturePack, Vol3AiaiResouce.arrTree);
		this.mBagwormTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack, 
				Vol3AiaiResouce.main.A1_08_1_IPHONE_BAGWORM_ID, Vol3AiaiResouce.main.A1_08_2_IPHONE_BAGWORM_ID);
		this.mBirdTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack, 
				Vol3AiaiResouce.main.A1_05_1_IPHONE_BIRD_ID, Vol3AiaiResouce.main.A1_05_2_IPHONE_BIRD_ID);
		this.mHoleTextureRegion = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_07_1_IPHONE_SQUIRREL_ID);
		this.mSquirrelTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack,
				Vol3AiaiResouce.main.A1_07_2_IPHONE_SQUIRREL_ID, Vol3AiaiResouce.main.A1_07_3_IPHONE_SQUIRREL_ID, 
				Vol3AiaiResouce.main.A1_07_4_IPHONE_SQUIRREL_ID
				);
		this.mMonkeyTiledTextureRegion = getTiledTextureFromPacker(mArrMonkeyTexturePack, Vol3AiaiResouce.arrMonkey);
		this.mMonkeyScrollTiledTextureRegion = getTiledTextureFromPacker(mArrMonkeyScrollTexturePack, Vol3AiaiResouce.arrMonkeyScroll);
		this.mMonkeyScrollPickTiledTextureRegion = getTiledTextureFromPacker(mArrMonkeyScrollStickTexturePack, Vol3AiaiResouce.arrMonkeyScrollStick);
		
		this.mMonkeyHandMobileTextureRegion = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_04_14_2_IPHONE_MOBILE_ID);
		this.mMonkeyEyeTextureRegion = mMainTexturePackLibs.get(Vol3AiaiResouce.main.A1_10_IPHONE_EYE_ID);

		for (int z = 0; z < Vol3AiaiResouce.arrListIteamTouch.length; z++) {
			mListIteamMoveTextureRegion[z] = mMainTexturePackLibs.get(Vol3AiaiResouce.arrListIteamTouch[z]);
		}

	}

	@Override
	protected void loadKaraokeSound() {

		oggMyon = loadSoundResourceFromSD(Vol3AiaiResouce.A1_08_MYON);
		oggPiyo = loadSoundResourceFromSD(Vol3AiaiResouce.A1_05_PIYO);
		oggPyokotoko = loadSoundResourceFromSD(Vol3AiaiResouce.A1_07_PYOKOTOKO);
		oggWaraigoe = loadSoundResourceFromSD(Vol3AiaiResouce.A1_06_WARAIGOE);
		oggNeiki = loadSoundResourceFromSD(Vol3AiaiResouce.A1_09_NEIKI);
		oggGiro = loadSoundResourceFromSD(Vol3AiaiResouce.A1_13_GIRO);
		oggMonkey = loadSoundResourceFromSD(Vol3AiaiResouce.A1_13_MONKEY);
		oggKira3 = loadSoundResourceFromSD(Vol3AiaiResouce.A1_14_KIRA3);
		oggHowa = loadSoundResourceFromSD(Vol3AiaiResouce.A1_16_HOWA);
		oggUn = loadSoundResourceFromSD(Vol3AiaiResouce.A1_17_UN);
		oggKacha = loadSoundResourceFromSD(Vol3AiaiResouce.A1_04_KACHA);
		oggKyu = loadSoundResourceFromSD(Vol3AiaiResouce.A1_04_KYU);
		oggTaberu = loadSoundResourceFromSD(Vol3AiaiResouce.A1_04_TABERU);

		System.gc();
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.mHaikeiTextureRegion, this.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		// Background

		mSunAnimatedSprite = new AnimatedSprite(659, 0, mSunTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSunAnimatedSprite);
		mSunAnimatedSprite.animate(new long[] { 350, 350 }, new int[] { 0, 1 },
				-1);

		mCloudLayoutSprite = new Sprite(415, 0,
				new TextureRegion(new BitmapTextureAtlas(getTextureManager(),2, 2,
						TextureOptions.BILINEAR_PREMULTIPLYALPHA), 0, 0, 960,
						640), this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCloudLayoutSprite);
		mCloudLayoutSprite.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		mCloudLayoutSprite.setAlpha(0.0f);

		// add cloud
		int i = 0;
		mCloudSprite[i] = new Sprite(545, 0, mCloudTextureRegion[i], this.getVertexBufferObjectManager());
		mCloudLayoutSprite.attachChild(mCloudSprite[i]);
		i++;
		mCloudSprite[i] = new Sprite(740, 62, mCloudTextureRegion[i], this.getVertexBufferObjectManager());
		mCloudLayoutSprite.attachChild(mCloudSprite[i]);
		i++;
		mCloudSprite[i] = new Sprite(795, -30, mCloudTextureRegion[i], this.getVertexBufferObjectManager());
		mCloudLayoutSprite.attachChild(mCloudSprite[i]);
		i++;
		mCloudSprite[i] = new Sprite(847, 103, mCloudTextureRegion[i], this.getVertexBufferObjectManager());
		mCloudLayoutSprite.attachChild(mCloudSprite[i]);
		// end

		mCloudLayoutSprite.registerEntityModifier(new LoopEntityModifier(
				new MoveXModifier(8.0f, mCloudLayoutSprite.getX(),
						0 - mCloudLayoutSprite.getWidth(), EaseLinear
								.getInstance())));
		// tree
		mTreeAnimatedSprite = new AnimatedSprite(0, 0, mTreeTiledTextureRegion, this.getVertexBufferObjectManager());
		mTreeAnimatedSprite.setCurrentTileIndex(0);
		this.mScene.attachChild(mTreeAnimatedSprite);

		//
		mBagwormAnimatedSprite = new AnimatedSprite(314, 24,
				mBagwormTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBagwormAnimatedSprite);

		mBirdAnimatedSprite = new AnimatedSprite(202, 394,
				mBirdTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBirdAnimatedSprite);

		mHoleSprite = new Sprite(722, 318, mHoleTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHoleSprite);

		mSquirrelAnimatedSprite = new AnimatedSprite(722, 318,
				mSquirrelTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSquirrelAnimatedSprite);
		mSquirrelAnimatedSprite.setVisible(false);

		mMonkeyLayout = new MonkeyLayoutSprite(0, 0, new TextureRegion(
				new BitmapTextureAtlas(getTextureManager(),2, 2, TextureOptions.BILINEAR_PREMULTIPLYALPHA), 0,
				0, 960, 640), this.getVertexBufferObjectManager());
		mMonkeyLayout.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		mMonkeyLayout.setAlpha(0.0f);
		this.mScene.attachChild(mMonkeyLayout);
		this.mScene.registerTouchArea(mMonkeyLayout);

		addGimmicsButton(mScene, Vol3AiaiResouce.SOUND_GIMMIC,
				new int[]{
				Vol3AiaiResouce.main.A1_AIAI_1_IPHONE_ID,Vol3AiaiResouce.main.A1_AIAI_2_IPHONE_ID,Vol3AiaiResouce.main.A1_AIAI_3_IPHONE_ID
		}, mMainTexturePackLibs);

		System.gc();
		// loadAndroidFrame() ;
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				loadAndroidFrame() ;
			}
		});
		Log.d(TAG, "loadKaraokeComplete");
	}
	
	@Override
	public synchronized void onResumeGame() {
		bagwormDefault();
		isSquirrel = true;
		super.onResumeGame();
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isSquirrel && !isApplicationBusy) {
			squirrelRun();
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			if (mBagwormAnimatedSprite.contains(pX, pY)
					&& !mBagwormAnimatedSprite.isAnimationRunning()) {
				bagwormTouch();
			} else if (mBirdAnimatedSprite.contains(pX, pY)
					&& !mBirdAnimatedSprite.isAnimationRunning()
					&& !isApplicationBusy) {
				birdTouch();
			} else if (checkContains(mHoleSprite, 20, 81, 135, 143, (int) pX,
					(int) pY) && isSquirrel && !isApplicationBusy) {
				squirrelRun();
			}

		}
		return true;
	}

	// =================================================
	// Function and class
	// =================================================

	private void squirrelRun() {

		isSquirrel = false;
		oggPyokotoko.play();
		setTouchGimmic3(false);
		mSquirrelAnimatedSprite.setVisible(true);
		mSquirrelAnimatedSprite.animate(new long[] { 400 }, new int[] { 0 }, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						mSquirrelAnimatedSprite.animate(
								new long[] { 250, 250 }, new int[] { 1, 2, },
								-1);
						mSquirrelAnimatedSprite
								.registerEntityModifier(new MoveModifier(0.8f,
										mSquirrelAnimatedSprite.getX(),
										CAMERA_WIDTH, mSquirrelAnimatedSprite
												.getY(),
										mSquirrelAnimatedSprite.getY() - 40,
										new IEntityModifierListener() {

											@Override
											public void onModifierFinished(
													IModifier<IEntity> pModifier,
													IEntity pItem) {
												runOnUpdateThread(new Runnable() {

													@Override
													public void run() {
														mSquirrelAnimatedSprite
																.stopAnimation();
														mSquirrelAnimatedSprite
																.clearEntityModifiers();
														mSquirrelAnimatedSprite.setPosition(722, 318);
														mSquirrelAnimatedSprite
																.setVisible(false);
														mSquirrelAnimatedSprite
																.setCurrentTileIndex(0);
														setTouchGimmic3(true);
														isSquirrel = true;
													}
												});
											}

											@Override
											public void onModifierStarted(
													IModifier<IEntity> paramIModifier,
													IEntity paramT) {}
										}));
					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationStarted(
							AnimatedSprite paramAnimatedSprite, int paramInt) {}
				});

	}

	private void birdTouch() {
		oggPiyo.play();
		mBirdAnimatedSprite.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.325f, mBirdAnimatedSprite.getY(),
						mBirdAnimatedSprite.getY() - 30), new MoveYModifier(
						0.325f, mBirdAnimatedSprite.getY() - 30,
						mBirdAnimatedSprite.getY())));
		mBirdAnimatedSprite.animate(new long[] { 750 }, new int[] { 1 }, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite paramAnimatedSprite, int paramInt) {}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						mBirdAnimatedSprite.setCurrentTileIndex(0);
					}
				});
	}

	private void bagwormDefault() {
		mBagwormAnimatedSprite.setRotationCenter(
				mBagwormAnimatedSprite.getWidth() / 2, 0);
		mBagwormAnimatedSprite.setCurrentTileIndex(0);
		mBagwormAnimatedSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new RotationModifier(1.0f, -30, 30),
						new RotationModifier(1.0f, 30, -30))));
	}

	private void bagwormTouch() {
		mBagwormAnimatedSprite.clearEntityModifiers();
		mBagwormAnimatedSprite.reset();
		oggMyon.play();
		mBagwormAnimatedSprite.animate(new long[] { 700 }, new int[] { 1 }, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite paramAnimatedSprite, int paramInt) {
						}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {}

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						bagwormDefault();
					}
				});
	}

	private class MonkeyLayoutSprite extends Sprite {

		private AnimatedSprite mMonkey;
		private AnimatedSprite mMonkeyScroll;
		private AnimatedSprite mMonkeyScrollPick;
		private Sprite mMonkeyHandMobile;
		private Sprite imgMonkeyEyeLeft, imgMonkeyEyeRight;
		private Sprite mGlassSprite;
		private Sprite mHatSprite;
		private Sprite mRibbonSprite;
		private Sprite mBeardSprite;
		private int xMonkeyEyeLeft, yMonkeyEyeLeft, xMonkeyEyeRight,
				yMonkeyEyeRight;
		private boolean isBusy = false;
		private boolean isActionUp = false;
		private boolean isTouch = false;
		private static final int LAUGH = 1, SLEEP = 2, HEART = 3, CRY = 4,
				ANGRY = 5;
		private int STATUS = 0;
		private int mID = 0;
		private int mType = 0;

		private boolean getIsBusy() {
			return this.isBusy;
		}

		private void resetAll() {
			isBusy = false;
			isActionUp = false;
			mMonkeyScroll.setVisible(false);
			mMonkeyScrollPick.setVisible(false);
			mMonkeyHandMobile.setVisible(false);
			cleanIteamFace();
			monkeyDefault();
		}

		private void cleanIteamFace() {
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					if (mGlassSprite != null) {
						mGlassSprite.setVisible(false);
						mGlassSprite.detachSelf();
						mGlassSprite = null;
					}
					if (mHatSprite != null) {
						mHatSprite.setVisible(false);
						mHatSprite.detachSelf();
						mHatSprite = null;
					}
					if (mRibbonSprite.isVisible()) {
						mRibbonSprite.setVisible(false);
					}
					if (mBeardSprite != null) {
						mBeardSprite.setVisible(false);
						mBeardSprite.detachSelf();
						mBeardSprite = null;
					}
				}
			});

		}

		private void monkeyJump() {
			isApplicationBusy = true;
			isBusy = true;
			Log.d(TAG, "monkeyJump");
			cleanIteamFace();
			mMonkeyHandMobile.setVisible(false);
			mMonkeyScrollPick.setVisible(false);
			showEye(false);
			oggGiro.play();
			oggMonkey.play();
			mHoleSprite.setVisible(false);
			if (mMonkey.isAnimationRunning()) {
				mMonkey.stopAnimation();
			}
			mMonkey.setCurrentTileIndex(1);
			mMonkey.setVisible(true);
			mMonkey.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(0.6f, mMonkey.getY(), mMonkey.getY() - 65),
					new MoveYModifier(0.6f, mMonkey.getY() - 65,
							mMonkey.getY() - 0)));
			mTreeAnimatedSprite.animate(new long[] { 200, 200, 200, 200, 200,
					200 }, new int[] { 1, 2, 1, 3, 4, 3 }, 0,
					new IAnimationListener() {


						@Override
						public void onAnimationStarted(
								AnimatedSprite paramAnimatedSprite, int paramInt) {

						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {

						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
	
						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							mHoleSprite.setVisible(true);
							monkeyDefault();
							isBusy = false;
							isApplicationBusy = false;
						}
					});
			// Bird
			mBirdAnimatedSprite
					.registerEntityModifier(new SequenceEntityModifier(
							new MoveYModifier(0.6f, mBirdAnimatedSprite.getY(),
									mBirdAnimatedSprite.getY() - 30),
							new MoveYModifier(0.6f,
									mBirdAnimatedSprite.getY() - 30,
									mBirdAnimatedSprite.getY())));
			mBirdAnimatedSprite.animate(new long[] { 1200 }, new int[] { 1 },
					0, new IAnimationListener() {


						@Override
						public void onAnimationStarted(
								AnimatedSprite paramAnimatedSprite, int paramInt) {
				
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							mBirdAnimatedSprite.setCurrentTileIndex(0);
						}
					});

		}

		public MonkeyLayoutSprite(float pX, float pY,
				TextureRegion pTextureRegion, VertexBufferObjectManager pVertex) {
			super(pX, pY, pTextureRegion, pVertex);
			loadDefault();
		}

		private void loadDefault() {

			mMonkey = new AnimatedSprite(151, 15, mMonkeyTiledTextureRegion, this.getVertexBufferObjectManager());
			mMonkey.setCurrentTileIndex(0);
			this.attachChild(mMonkey);

			mMonkeyScroll = new AnimatedSprite(151, 15,
					mMonkeyScrollTiledTextureRegion,this.getVertexBufferObjectManager());
			this.attachChild(mMonkeyScroll);
			mMonkeyScroll.setVisible(false);

			mMonkeyScrollPick = new AnimatedSprite(151, 15,
					mMonkeyScrollPickTiledTextureRegion,this.getVertexBufferObjectManager());
			this.attachChild(mMonkeyScrollPick);
			mMonkeyScrollPick.setVisible(false);

			mMonkeyHandMobile = new Sprite(151, 15,
					mMonkeyHandMobileTextureRegion,this.getVertexBufferObjectManager());
			this.attachChild(mMonkeyHandMobile);
			mMonkeyHandMobile.setVisible(false);

			imgMonkeyEyeLeft = new Sprite(362, 213, mMonkeyEyeTextureRegion,this.getVertexBufferObjectManager());
			this.attachChild(imgMonkeyEyeLeft);

			imgMonkeyEyeRight = new Sprite(476, 213,
					mMonkeyEyeTextureRegion.deepCopy(),this.getVertexBufferObjectManager());
			this.attachChild(imgMonkeyEyeRight);

			mRibbonSprite = new Sprite(ListIteam[6][2], ListIteam[6][3],
					mListIteamMoveTextureRegion[6],this.getVertexBufferObjectManager());
			this.attachChild(mRibbonSprite);
			mRibbonSprite.setVisible(false);

			Log.d(TAG, "MonkeyLayoutSprite loadDefault");
		}

		private void monkeyDefault() {
			mMonkey.setVisible(true);
			mMonkey.setCurrentTileIndex(0);
			showEye(true);
			isTouch = false;
		}

		private void showEye(boolean pShow) {
			imgMonkeyEyeLeft.setVisible(pShow);
			imgMonkeyEyeRight.setVisible(pShow);
		}

		private void monkeyLaugh() { // Action 6
			showEye(false);
			oggWaraigoe.play();
			STATUS = LAUGH;
			mMonkey.setCurrentTileIndex(2);
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 1500 }, new int[] { 2 }, 0,
					new IAnimationListener() {

						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}

						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							monkeyDefault();
						}
					});
		}

		private void monkeySleep() { // 9
			showEye(false);
			oggNeiki.play();
			STATUS = SLEEP;
			mMonkey.setCurrentTileIndex(3);
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 1500 }, new int[] { 3 }, 0,
					new IAnimationListener() {
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							monkeyDefault();
						}
						
					});

		}

		private void monkeyHeart() { // 14
			showEye(false);
			oggKira3.play();
			STATUS = HEART;
			mMonkey.setCurrentTileIndex(4);
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 1500 }, new int[] { 4 }, 0,
					new IAnimationListener() {
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							monkeyDefault();
						}
		
				});

		}

		private void monkeyCry() { // 16
			showEye(false);
			oggHowa.play();
			STATUS = CRY;
			mMonkey.setCurrentTileIndex(5);
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 2500 }, new int[] { 5 }, 0,
					new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
							@Override
							public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								monkeyDefault();
							}
					});

		}

		private void monkeyAngry() {
			showEye(false);
			oggUn.play();
			STATUS = ANGRY;
			mMonkey.setCurrentTileIndex(6);
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 1500 }, new int[] { 6 }, 0,
					new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
							@Override
							public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								monkeyDefault();
							}

					});

		}

		private boolean check(int pSTATUS) {
			if (pSTATUS == STATUS && mMonkey.isAnimationRunning()) {
				return true;
			}
			return false;
		}

		private void monkeyMumum() {
			Log.d(TAG, "monkeyMumum" + isBusy);
			mMonkey.setCurrentTileIndex(7);
			oggTaberu.play();
			mMonkey.setVisible(true);
			mMonkey.animate(new long[] { 200, 200 }, new int[] { 7, 8 }, 1,
					new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
							@Override
							public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								monkeyDefault();
								isBusy = false;
							}					
					});

		}

		private void ActionUP() {
			Log.d(TAG, "Gate process Action Up" + mMoveSprite.getX());
			isActionUp = false;
			if (checkContains(mMonkeyLayout, 248, 108, 629, 341,
					(int) (mMoveSprite.getX() + mMoveSprite.getWidth() / 2),
					(int) (mMoveSprite.getY() + mMoveSprite.getHeight() / 2))) {
				this.mID = mMoveSprite.mID;
				this.mType = mMoveSprite.mType;
				isActionUp = true;
				Log.d(TAG, "mID: " + mID + " - mType: " + mType);
			}

			if (isActionUp && !isBusy) {
				processAction();
			}

		}

		private boolean checkTouch(int pid, int pType) {
			switch (pType) {
			case B:
				if (pid == 4 || pid == 7 || pid == 10 || pid == 11) { // hat
					if (mHatSprite != null) {
						if (mHatSprite.getTextureRegion().equals(
								mListIteamMoveTextureRegion[pid])
								&& mHatSprite.isVisible()) {
							return false;
						}
					}
				} else if (pid == 5 || pid == 12) {
					if (mGlassSprite != null) {
						if (mGlassSprite.getTextureRegion().equals(
								mListIteamMoveTextureRegion[pid])
								&& mGlassSprite.isVisible()) {
							return false;
						}
					}
				} else if (pid == 9) {
					if (mBeardSprite != null) {
						return false;
					}
				} else if (pid == 6) {
					if (mRibbonSprite.isVisible()) {
						return false;
					}
				}
				break;
			case C:
				if (mMonkeyScrollPick.getCurrentTileIndex() == ListIteam[pid][2]
						&& mMonkeyScrollPick.isVisible()) {
					return false;
				}
				break;
			}
			return true;
		}

		private void processAction() {
			Log.d(TAG, this.mID + " processAction " + this.mType);
			switch (this.mType) {
			case A:
				isBusy = true;
				mMonkey.setVisible(false);
				mMonkeyHandMobile.setVisible(false);
				mMonkeyScrollPick.setVisible(false);
				mMonkeyScroll.setCurrentTileIndex(ListIteam[this.mID][2]);
				showEye(false);
				mMonkeyScroll.animate(new long[] { 300, 300 }, new int[] {
						ListIteam[this.mID][2], ListIteam[this.mID][3] }, 0,
						new IAnimationListener() {
								
								@Override
								public void onAnimationStarted(AnimatedSprite paramAnimatedSprite, int paramInt) {}
								@Override
								public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
								@Override
								public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,int paramInt1, int paramInt2) {}
								@Override
								public void onAnimationFinished(
										AnimatedSprite paramAnimatedSprite) {
									mMonkeyScroll.setVisible(false);
									monkeyMumum();
								}	
						
						});
				mMonkeyScroll.setVisible(true);

				break;
			case B:

				if (this.mID == 4 || this.mID == 7 || this.mID == 10
						|| this.mID == 11) { // hat
					if (mHatSprite != null) {
						mHatSprite.setVisible(false);
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								mHatSprite.detachSelf();
								mHatSprite = null;
								mHatSprite = new Sprite(
										ListIteam[MonkeyLayoutSprite.this.mID][2],
										ListIteam[MonkeyLayoutSprite.this.mID][3],
										mListIteamMoveTextureRegion[MonkeyLayoutSprite.this.mID], Vol3Aiai.this.getVertexBufferObjectManager());
								MonkeyLayoutSprite.this.attachChild(mHatSprite);
								mRibbonSprite
										.setZIndex(mHatSprite.getZIndex() + 2);
								mMonkeyHandMobile.setZIndex(mHatSprite
										.getZIndex() + 3);
								if (mGlassSprite != null) {
									mGlassSprite.setZIndex(mHatSprite
											.getZIndex() + 3);
								}
								MonkeyLayoutSprite.this.sortChildren();
							}
						});
					} else {
						mHatSprite = new Sprite(ListIteam[this.mID][2],
								ListIteam[this.mID][3],
								mListIteamMoveTextureRegion[this.mID], Vol3Aiai.this.getVertexBufferObjectManager());
						this.attachChild(mHatSprite);
						mRibbonSprite.setZIndex(mHatSprite.getZIndex() + 2);
						mMonkeyHandMobile.setZIndex(mHatSprite.getZIndex() + 3);
						if (mGlassSprite != null) {
							mGlassSprite.setZIndex(mHatSprite.getZIndex() + 3);
						}
						this.sortChildren();
					}

					// Vol3Aiai.this.mScene.sortChildren();
				} else if (this.mID == 5 || this.mID == 12) {
					if (mGlassSprite != null) {
						mGlassSprite.setVisible(false);
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								mGlassSprite.detachSelf();
								mGlassSprite = null;
								mGlassSprite = new Sprite(
										ListIteam[MonkeyLayoutSprite.this.mID][2],
										ListIteam[MonkeyLayoutSprite.this.mID][3],
										mListIteamMoveTextureRegion[MonkeyLayoutSprite.this.mID], Vol3Aiai.this.getVertexBufferObjectManager());
								MonkeyLayoutSprite.this
										.attachChild(mGlassSprite);
							}
						});
					} else {
						mGlassSprite = new Sprite(
								ListIteam[MonkeyLayoutSprite.this.mID][2],
								ListIteam[MonkeyLayoutSprite.this.mID][3],
								mListIteamMoveTextureRegion[MonkeyLayoutSprite.this.mID], Vol3Aiai.this.getVertexBufferObjectManager());
						MonkeyLayoutSprite.this.attachChild(mGlassSprite);
						if (mHatSprite != null) {
							mGlassSprite.setZIndex(mHatSprite.getZIndex() + 1);
							this.sortChildren();
						}
					}
				} else if (this.mID == 9) {
					if (mBeardSprite != null) {

					} else {
						mBeardSprite = new Sprite(
								ListIteam[MonkeyLayoutSprite.this.mID][2],
								ListIteam[MonkeyLayoutSprite.this.mID][3],
								mListIteamMoveTextureRegion[MonkeyLayoutSprite.this.mID], Vol3Aiai.this.getVertexBufferObjectManager());
						MonkeyLayoutSprite.this.attachChild(mBeardSprite);
					}
				} else if (this.mID == 6) {
					if (!mRibbonSprite.isVisible()) {
						mRibbonSprite.setVisible(true);
					}
				}
				oggKacha.play();
				break;
			case C:

				if (this.isTouch == true) {
					break;
				}

				showEye(false);
				mMonkey.setVisible(false);
				mMonkeyScroll.setVisible(false);
				if (this.mID == 8) {
					oggKyu.play();
				} else {
					oggKacha.play();
				}
				if (this.mID == 13) {
					mMonkeyHandMobile.setVisible(true);
				} else {
					mMonkeyHandMobile.setVisible(false);
				}
				mMonkeyScrollPick.setCurrentTileIndex(ListIteam[this.mID][2]);
				mMonkeyScrollPick.setVisible(true);

				break;
			}
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();
			Log.d(TAG, "pX: " + pX + "pY:" + pY);
			rotate_monkey_eye((int) pX, (int) pY);
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN
					&& !isBusy && !isApplicationBusy) {
				if (checkContains(mMonkey, 79, 224, 175, 361, (int) pX,
						(int) pY) && !check(LAUGH)) {
					monkeyTouch();
					monkeyLaugh();
				} else if (checkContains(mMonkey, 237, 143, 349, 194, (int) pX,
						(int) pY) && !check(SLEEP)) {
					monkeyTouch();
					monkeySleep();
				} else if (checkContains(mMonkey, 519, 91, 620, 369, (int) pX,
						(int) pY) && !check(HEART)) {
					monkeyTouch();
					monkeyHeart();
				} else if (checkContains(mMonkey, 359, 91, 481, 206, (int) pX,
						(int) pY) && !check(CRY)) {
					monkeyTouch();
					monkeyCry();
				} else if (checkContains(mMonkey, 95, 91, 207, 206, (int) pX,
						(int) pY) && !check(ANGRY)) {
					monkeyTouch();
					monkeyAngry();
				} else if (checkContains(this, 366, 425, 728, 530, (int) pX,
						(int) pY)
						&& isSquirrel
						&& !mBirdAnimatedSprite.isAnimationRunning()) {
					monkeyTouch();
					monkeyJump();
				}
			}

			return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);
		}

		private void monkeyTouch() {
			this.isTouch = true;
			mMonkeyScrollPick.setVisible(false);
			mMonkeyHandMobile.setVisible(false);
		}

		public void rotate_monkey_eye(int rawX, int rawY) {
			// Get coordinate of left eye
			try {
				int temp = 8;

				if (xMonkeyEyeLeft == 0) {
					xMonkeyEyeLeft = (int) imgMonkeyEyeLeft.getX();
					yMonkeyEyeLeft = (int) imgMonkeyEyeLeft.getY();

					xMonkeyEyeRight = (int) imgMonkeyEyeRight.getX();
					yMonkeyEyeRight = (int) imgMonkeyEyeRight.getY();
				}

				int xDisEye = rawX - xMonkeyEyeLeft;
				int yDisEye = rawY - yMonkeyEyeLeft;
				int disEye = (int) Math.sqrt(xDisEye * xDisEye + yDisEye * yDisEye);
				int x = Math.round(temp * xDisEye / disEye);
				int y = Math.round(temp * yDisEye / disEye);
				// set new layout for left eye
				int newXLeft = xMonkeyEyeLeft + x;
				int newYLeft = yMonkeyEyeLeft + y;
				imgMonkeyEyeLeft.setPosition(newXLeft, newYLeft);
				// set new layout for right eye
				int newXRight = xMonkeyEyeRight + x;
				int newYRight = yMonkeyEyeRight + y;
				imgMonkeyEyeRight.setPosition(newXRight, newYRight);
			} catch (Exception e) {
			
			}
		

		}

	}

	class MoveSprite extends Sprite {
		public int mID = 0;
		public int mType = 0;

		public MoveSprite(float pX, float pY, int id, int type, VertexBufferObjectManager pVertex) {
			super(pX, pY, mListIteamMoveTextureRegion[id], pVertex);
			this.mID = id;
			this.mType = type;
			this.setVisible(false);
			this.show(pX, pY);
		}

		public void show(float pX, float pY) {
			final float mX = pX / mResizeView.ratioResizeWidth
					- mResizeView.hemBlackWidth;
			final float mY = pY / mResizeView.ratioResizeHeight
					- mResizeView.hemBlackHeight;

			if (this.mID == mListIteamMoveTextureRegion.length - 1) {
				this.setScale(3.0f);
			}

			this.setPosition(mX - this.getWidth() / 2, mY - this.getWidth() / 2);
			this.setVisible(true);
			Vol3Aiai.this.mScene.attachChild(this);

		}

		public void moveXY(float pX, float pY) {

			final float mX = pX / mResizeView.ratioResizeWidth
					- mResizeView.hemBlackWidth;
			final float mY = pY / mResizeView.ratioResizeHeight
					- mResizeView.hemBlackHeight;
			// Log.d(TAG,"mX:" + mX + " /mY: " +mY);
			this.setPosition(mX - this.getWidth() / 2, mY - this.getWidth() / 2);
		}

		public void die() {
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					MoveSprite.this.setVisible(false);
					MoveSprite.this.detachSelf();
				}
			});

		}

	}

	private void moveActionUP() {

		// Check in Face
		mMonkeyLayout.ActionUP();
		isDraw = false;
		try {

			mMoveSprite.die();
			mMoveSprite = null;
			isDraw = true;
		} catch (Exception e) {
			mMoveSprite = null;
			isDraw = true;
		}

	}

	// =================================================
	// Android widget
	// =================================================

	View.OnTouchListener scrollTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getPointerCount() >= 2 && !isDraw && isApplicationBusy) {
				return true;
			}
			boolean xreturn = false;
			float pX = event.getRawX();
			float pY = event.getRawY();

			if (mMoveSprite != null && isDraw) {
				mMoveSprite.moveXY(pX, pY);
			}
			// Log.d(TAG, "scrollTouchListener:  ACTION_MOVE");

			if (event.getAction() == MotionEvent.ACTION_UP && isDraw
					&& mMoveSprite != null) {
				moveActionUP();
			}
			return xreturn;
		}
	};

	View.OnTouchListener iteamTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getPointerCount() >= 2 && !isDraw && isApplicationBusy) {
				return true;
			}
			float pX = event.getRawX();
			float pY = event.getRawY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "xxxxxxxxxxx2" + mMonkeyLayout.getIsBusy());
				ImageViewIteam imgSelect = (ImageViewIteam) v;
				if (mMonkeyLayout.getIsBusy()) {
					break;
				}
				if (!mMonkeyLayout.checkTouch(imgSelect.mID, imgSelect.mType)) {
					break;
				}
				Log.d(TAG, "imgSelect: " + imgSelect.mID + " /Type:"
						+ imgSelect.mType);
				// ceate move
				mMoveSprite = new MoveSprite(pX, pY, imgSelect.mID,
						imgSelect.mType, getVertexBufferObjectManager());
				break;
			case MotionEvent.ACTION_MOVE:
				if (mMoveSprite != null) {
					mMoveSprite.moveXY(pX, pY);
				}
				// Log.d(TAG, "iteamTouchListener:  ACTION_MOVE");
				break;
			case MotionEvent.ACTION_UP:
				if (isDraw && mMoveSprite != null) {
					moveActionUP();
				}

				break;
			}

			return true;

		}
	};

	class ImageViewIteam extends ImageView {
		public int mID;
		public int mType;

		public ImageViewIteam(Context context, int id, int type) {
			super(context);
			this.mID = id;
			this.mType = type;
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
			getActivity().getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			Log.d(LOG_RESIZEVIEW, "CHANGE..." + metrics.toString());
			if (metrics.widthPixels < metrics.heightPixels) {
				int temp = metrics.widthPixels;
				metrics.widthPixels = metrics.heightPixels;
				metrics.heightPixels = temp;
			}

			// Height bar for 3.0
			// int heightBar = shareApplicationData.getInt("height_bar", 0);
			// metrics.heightPixels = metrics.heightPixels
			// - Math.round(heightBar / metrics.density);

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
}
