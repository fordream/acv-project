package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MorinokumasanResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Morinokumasan extends BaseGameActivity implements
		IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_VOL3MORINOKUMASAN";

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mAllitemTexturePack;
	private TexturePack mBackGroundTexturePack;

	// BEAR X Y
	private static final int BEAR_POINT_X = 649;
	private static final int BEAR_POINT_Y = 202;

	private static final int BEAR_POINT_AX = 220;
	private static final int BEAR_POINT_AY = 202;

	private static final int BEAR_POINT_BX = 150;
	private static final int BEAR_POINT_BY = 127;

	private static final int BEAR_POINT_CX = -313;
	private static final int BEAR_POINT_CY = 127;

	private static final float TIME_BEAR = 5.0f / 960;

	// Girl X Y
	private static final int GIRL_POINT_X = 700;
	private static final int GIRL_POINT_Y = 265;

	private static final int GIRL_POINT_AX = 220;
	private static final int GIRL_POINT_AY = 265;

	private static final int GIRL_POINT_BX = 150;
	private static final int GIRL_POINT_BY = 190;

	private static final int GIRL_POINT_CX = -315;
	private static final int GIRL_POINT_CY = 190;

	private static final float TIME_GIRL = 20.0f / 960;
	private static final float TIME_GIRL_RUN = 5.0f / 960;

	private static float GIRL_PAUSE_X = 0;
	private static int PICK_FLOWER_ID = 0;

	private static int ORANGE = 0;
	private static int CHERRY = 0;
	private static int GRAPE = 0;
	private static int APPLE = 0;

	private boolean isPick = false;
	private boolean isUpdateGirl = true;
	private boolean isBear = false;
	private boolean isHuman = true;
	private boolean isFox = true;
	private boolean isRabbit = true;
	private boolean isBird = true;
	private boolean isMonkey = true;

	private final static int GIRL_FRAME_FLOWER[] = new int[] { 3, 4, 3, 5, 4,
			5, 3 };

	private static Sound oggTakeFlower, oggBear, oggGirl, oggTouchAni,
			oggHuman, oggOrangeGrape, oggCherryApple, oggDown;

	// ===========================================================
	// Fields
	// ===========================================================

	/** TextureRegion **/
	private ITextureRegion mHaikei15TextureRegion;
	private ITextureRegion mHaikei16TextureRegion;
	private ITextureRegion mHaikei17TextureRegion;
	private ITextureRegion mHaikei18TextureRegion;
	private ITextureRegion mHaikei19TextureRegion;
	private ITextureRegion mHaikei20TextureRegion;
	private ITextureRegion mHaikei21TextureRegion;
	private ITextureRegion mFox1TextureRegion;
	private ITextureRegion mFox2TextureRegion;
	private ITextureRegion mFox3TextureRegion;
	private ITextureRegion mMonkeyTextureRegion;

	private ITextureRegion mFlowerTextureRegion[] = new ITextureRegion[7];
	private ITextureRegion mOrangeTextureRegion[] = new ITextureRegion[Vol3MorinokumasanResouce.IArrOrange.length];
	private ITextureRegion mCherryTextureRegion[] = new ITextureRegion[Vol3MorinokumasanResouce.IArrCherry.length];
	private ITextureRegion mGrapeTextureRegion[] = new ITextureRegion[Vol3MorinokumasanResouce.IArrGrape.length];
	private ITextureRegion mAppleTextureRegion[] = new ITextureRegion[Vol3MorinokumasanResouce.IArrApple.length];

	/** TiledTextureRegion **/
	private TiledTextureRegion mGirlTiledTextureRegion;
	private TiledTextureRegion mBearTiledTextureRegion;
	private TiledTextureRegion mBirdTiledTextureRegion;
	private TiledTextureRegion mRabbitTiledTextureRegion;
	private TiledTextureRegion mMonkeyTiledTextureRegion;
	private TiledTextureRegion mHumanTiledTextureRegion;

	/** Sprite **/
	private Sprite mHaikei15Sprite;
	private Sprite mHaikei16Sprite;
	private Sprite mHaikei17Sprite;
	private Sprite mHaikei18Sprite;
	private Sprite mHaikei19Sprite;
	private Sprite mHaikei20Sprite;
	private Sprite mHaikei21Sprite;
	private Sprite mFox1Sprite;
	private Sprite mFox2Sprite;
	private Sprite mFox3Sprite;
	private Sprite mMonkeySprite;

	private Sprite mFlowerSprite[] = new Sprite[7];
	private Sprite mOrangeSprite[] = new Sprite[Vol3MorinokumasanResouce.IArrOrange.length];
	private Sprite mCherrySprite[] = new Sprite[Vol3MorinokumasanResouce.IArrCherry.length];
	private Sprite mGrapeSprite[] = new Sprite[Vol3MorinokumasanResouce.IArrGrape.length];
	private Sprite mAppleSprite[] = new Sprite[Vol3MorinokumasanResouce.IArrApple.length];

	/** AnimatedSprite **/
	private AnimatedSprite mGirlAniSprite;
	private AnimatedSprite mBearAniSprite;
	private AnimatedSprite mBirdAnimatedSprite;
	private AnimatedSprite mRabbitAnimatedSprite;
	private AnimatedSprite mMonkeyAnimatedSprite;
	private AnimatedSprite mHumanAnimatedSprite;

	/** PathModifier **/
	private PathModifier mGirlPathModifier, mBearPathModifier;

	/** Loop **/
	private LoopEntityModifier mRabbitLoopModifier;
	private LoopEntityModifier mBirdLoopModifier;
	private LoopEntityModifier mMonkeyLoopModifier;
	private LoopEntityModifier mHumanLoopModifier;
	private SequenceEntityModifier mAllSequenceEntityModifier;

	/** IUpdateHandler **/
	private IUpdateHandler updateGirl;

	/** Handler & Runnable **/
	private static TimerHandler bearHandler, foxHandler, birdHandler,
			rabbitHandler, monkeyhandler, humanHandler;

	private static TimerHandler flowerHandler[] = new TimerHandler[7];

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();

		// this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
		// CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRHaikei)));

		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

		mHaikei15Sprite = new Sprite(0, 0, mHaikei15TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei15Sprite);

		mHaikei16Sprite = new Sprite(0, 0, mHaikei16TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei16Sprite);

		mHaikei17Sprite = new Sprite(0, 0, mHaikei17TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei17Sprite);

		mFox1Sprite = new Sprite(452, 203, mFox1TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mFox1Sprite);

		mFox2Sprite = new Sprite(452, 203, mFox2TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mFox2Sprite);

		mFox3Sprite = new Sprite(452, 203, mFox3TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mFox3Sprite);
		mFox3Sprite.setVisible(false);

		mBirdAnimatedSprite = new AnimatedSprite(214, 259,
				mBirdTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBirdAnimatedSprite);

		mRabbitAnimatedSprite = new AnimatedSprite(358, 243,
				mRabbitTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mRabbitAnimatedSprite);

		mMonkeySprite = new Sprite(107, 213, mMonkeyTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mMonkeySprite);

		mMonkeyAnimatedSprite = new AnimatedSprite(107, 213,
				mMonkeyTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mMonkeyAnimatedSprite);

		mHumanAnimatedSprite = new AnimatedSprite(592, 199,
				mHumanTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHumanAnimatedSprite);

		mHaikei18Sprite = new Sprite(0, 0, mHaikei18TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei18Sprite);

		mHaikei19Sprite = new Sprite(0, 0, mHaikei19TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei19Sprite);

		mHaikei20Sprite = new Sprite(0, 0, mHaikei20TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei20Sprite);

		for (int i = 0; i < Vol3MorinokumasanResouce.IArrOrange.length; i++) {

			mOrangeSprite[i] = new Sprite(
					Vol3MorinokumasanResouce.IArrOrange[i][1],
					Vol3MorinokumasanResouce.IArrOrange[i][2],
					mOrangeTextureRegion[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mOrangeSprite[i]);
			mOrangeSprite[i].setVisible(false);

			if (i < Vol3MorinokumasanResouce.IArrCherry.length) {

				mCherrySprite[i] = new Sprite(
						Vol3MorinokumasanResouce.IArrCherry[i][1],
						Vol3MorinokumasanResouce.IArrCherry[i][2],
						mCherryTextureRegion[i],
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mCherrySprite[i]);
				mCherrySprite[i].setVisible(false);

			}
			if (i < Vol3MorinokumasanResouce.IArrGrape.length) {
				mGrapeSprite[i] = new Sprite(
						Vol3MorinokumasanResouce.IArrGrape[i][1],
						Vol3MorinokumasanResouce.IArrGrape[i][2],
						mGrapeTextureRegion[i],
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mGrapeSprite[i]);
				mGrapeSprite[i].setVisible(false);
			}
		}

		mGirlAniSprite = new AnimatedSprite(GIRL_POINT_X, GIRL_POINT_Y,
				mGirlTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mGirlAniSprite);

		mBearAniSprite = new AnimatedSprite(BEAR_POINT_X, BEAR_POINT_Y,
				mBearTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBearAniSprite);
		// End main

		mHaikei21Sprite = new Sprite(0, 0, mHaikei21TextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikei21Sprite);

		for (int i = 0; i < Vol3MorinokumasanResouce.IArrApple.length; i++) {

			mAppleSprite[i] = new Sprite(
					Vol3MorinokumasanResouce.IArrApple[i][1],
					Vol3MorinokumasanResouce.IArrApple[i][2],
					mAppleTextureRegion[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(mAppleSprite[i]);
			mAppleSprite[i].setVisible(false);

		}

		for (int i = 0; i < 7; i++) {
			mFlowerSprite[i] = new Sprite(
					Vol3MorinokumasanResouce.IArrFlower[i][1],
					Vol3MorinokumasanResouce.IArrFlower[i][2],
					this.mFlowerTextureRegion[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mFlowerSprite[i]);

		}
		addGimmicsButton(
				mScene,
				Vol3MorinokumasanResouce.SOUND_GIMMIC,
				new int[] {
						Vol3MorinokumasanResouce.Allitem.A9_MORINOKUMA_1_IPHONE_ID,
						Vol3MorinokumasanResouce.Allitem.A9_MORINOKUMA_2_IPHONE_ID,
						Vol3MorinokumasanResouce.Allitem.A9_MORINOKUMA_3_IPHONE_ID },
				mAllitemTexturePack.getTexturePackTextureRegionLibrary());
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("morinokumasan/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("morinokumasan/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		mAllitemTexturePack = mTexturePackLoaderFromSource.load("Allitem.xml");
		mBackGroundTexturePack = mTexturePackLoaderFromSource
				.load("BackGround.xml");
		mAllitemTexturePack.loadTexture();
		mBackGroundTexturePack.loadTexture();

		final TexturePackTextureRegionLibrary mAllitemTexturePackLib = mAllitemTexturePack
				.getTexturePackTextureRegionLibrary();

		this.mHaikei15TextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_15_IPHONE_HAIKEI_ID);
		this.mHaikei16TextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_16_IPHONE_HAIKEI_ID);
		this.mHaikei17TextureRegion = mBackGroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3MorinokumasanResouce.BackGround.A9_17_IPHONE_HAIKEI_ID);
		this.mHaikei18TextureRegion = mBackGroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3MorinokumasanResouce.BackGround.A9_18_IPHONE_HAIKEI_ID);
		this.mHaikei19TextureRegion = mBackGroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3MorinokumasanResouce.BackGround.A9_19_IPHONE_HAIKEI_ID);
		this.mHaikei20TextureRegion = mBackGroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3MorinokumasanResouce.BackGround.A9_20_IPHONE_HAIKEI_ID);
		this.mHaikei21TextureRegion = mBackGroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3MorinokumasanResouce.BackGround.A9_21_IPHONE_HAIKEI_ID);
		this.mGirlTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_04_1_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_2_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_3_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_4_1_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_4_2_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_4_3_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_5_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_6_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_7_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_8_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_9_IPHONE_GIRL_ID,
				Vol3MorinokumasanResouce.Allitem.A9_04_10_IPHONE_GIRL_ID);
		this.mBearTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_05_1_IPHONE_BEAR_ID,
				Vol3MorinokumasanResouce.Allitem.A9_05_2_IPHONE_BEAR_ID,
				Vol3MorinokumasanResouce.Allitem.A9_05_3_IPHONE_BEAR_ID,
				Vol3MorinokumasanResouce.Allitem.A9_05_4_IPHONE_BEAR_ID,
				Vol3MorinokumasanResouce.Allitem.A9_05_5_IPHONE_BEAR_ID,
				Vol3MorinokumasanResouce.Allitem.A9_05_6_IPHONE_BEAR_ID);
		this.mFox1TextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_10_1_IPHONE_FOX_ID);
		this.mFox2TextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_10_2_IPHONE_FOX_ID);
		this.mFox3TextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_10_3_IPHONE_FOX_ID);

		this.mRabbitTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_11_1_IPHONE_RABBIT_ID,
				Vol3MorinokumasanResouce.Allitem.A9_11_2_IPHONE_RABBIT_ID);
		this.mBirdTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_12_1_IPHONE_BIRD_ID,
				Vol3MorinokumasanResouce.Allitem.A9_12_2_IPHONE_BIRD_ID);
		this.mMonkeyTextureRegion = mAllitemTexturePackLib
				.get(Vol3MorinokumasanResouce.Allitem.A9_13_1_IPHONE_MONKEY_ID);
		this.mMonkeyTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_13_2_IPHONE_MONKEY_ID,
				Vol3MorinokumasanResouce.Allitem.A9_13_3_IPHONE_MONKEY_ID);
		this.mHumanTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3MorinokumasanResouce.Allitem.A9_14_1_IPHONE_MAN_ID,
				Vol3MorinokumasanResouce.Allitem.A9_14_2_IPHONE_MAN_ID);

		// load mFlower
		for (int i = 0; i < 7; i++) {
			this.mFlowerTextureRegion[i] = mAllitemTexturePackLib
					.get(Vol3MorinokumasanResouce.IArrFlower[i][0]);

		}

		for (int i = 0; i < Vol3MorinokumasanResouce.IArrOrange.length; i++) {

			this.mOrangeTextureRegion[i] = mAllitemTexturePackLib
					.get(Vol3MorinokumasanResouce.IArrOrange[i][0]);
			if (i < Vol3MorinokumasanResouce.IArrCherry.length) {
				this.mCherryTextureRegion[i] = mAllitemTexturePackLib
						.get(Vol3MorinokumasanResouce.IArrCherry[i][0]);
			}
			if (i < Vol3MorinokumasanResouce.IArrGrape.length) {
				this.mGrapeTextureRegion[i] = mAllitemTexturePackLib
						.get(Vol3MorinokumasanResouce.IArrGrape[i][0]);
			}
			if (i < Vol3MorinokumasanResouce.IArrApple.length) {
				this.mAppleTextureRegion[i] = mAllitemTexturePackLib
						.get(Vol3MorinokumasanResouce.IArrApple[i][0]);
			}

		}

	}

	@Override
	protected void loadKaraokeSound() {
		oggTakeFlower = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_03_04_SAKU);
		oggBear = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_05_KUMA);
		oggGirl = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_05_GIRL);
		oggTouchAni = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_10_11_12_13);
		oggHuman = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_14_ABUNAI);
		oggOrangeGrape = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_06_08_POMI_TEI);
		oggCherryApple = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_07_09_POMI_KOU);
		oggDown = loadSoundResourceFromSD(Vol3MorinokumasanResouce.A9_06_08_POTO);
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
			resetGame();
		defaultRun();
		updateGirl = new IUpdateHandler() {

			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {

				if (isBear && isUpdateGirl) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {

							isUpdateGirl = false;
							girlRun();

						}
					});

				}

				if (isPick && (mGirlAniSprite.getX() - GIRL_PAUSE_X) <= 5
						&& isUpdateGirl) {

					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							isUpdateGirl = false;
							mGirlAniSprite.clearUpdateHandlers();
							pauseGirl();
							takeFlower(PICK_FLOWER_ID);
						}
					});
					Log.d(TAG, "Da tat");
				}
			}
		};

		GIRL_PAUSE_X = 0;
		PICK_FLOWER_ID = 0;
		ORANGE = 0;
		CHERRY = 0;
		GRAPE = 0;
		APPLE = 0;

		isPick = false;
		isUpdateGirl = true;
		isBear = false;
		isHuman = true;
		isFox = true;
		isRabbit = true;
		isBird = true;
		isMonkey = true;

		mGirlAniSprite.setPosition(GIRL_POINT_X, GIRL_POINT_Y);
		this.mEngine.registerUpdateHandler(new TimerHandler(2.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						resumeGirl();
					}
				}));
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	private void defaultRun() {

		mAllSequenceEntityModifier = new SequenceEntityModifier(
				new RotationModifier(1f, 0, 10),
				new RotationModifier(1f, 10, 0));
		mFox1Sprite.setRotationCenter(71, 85);
		mFox1Sprite.registerEntityModifier(new LoopEntityModifier(
				mAllSequenceEntityModifier));

		mRabbitAnimatedSprite.setRotationCenter(12, 105);
		mRabbitLoopModifier = new LoopEntityModifier(mAllSequenceEntityModifier);
		mRabbitAnimatedSprite.registerEntityModifier(mRabbitLoopModifier);

		mBirdAnimatedSprite.setRotationCenter(23, 73);
		mBirdLoopModifier = new LoopEntityModifier(mAllSequenceEntityModifier);
		mBirdAnimatedSprite.registerEntityModifier(mBirdLoopModifier);

		mMonkeyLoopModifier = new LoopEntityModifier(mAllSequenceEntityModifier);
		mMonkeySprite.registerEntityModifier(mMonkeyLoopModifier);

		mHumanLoopModifier = new LoopEntityModifier(mAllSequenceEntityModifier);
		mHumanAnimatedSprite.registerEntityModifier(mHumanLoopModifier);

	}

	@Override
	public void combineGimmic3WithAction() {
		if (isHuman) {
			humanAction();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			if (checkContains(mHaikei18Sprite, 770, 0, 960, 275, (int) pX,
					(int) pY)) {
				appleTouch();
			}
			// Touch 14 Human
			if (checkContains(mHumanAnimatedSprite, 0, 0, 130, 150, (int) pX,
					(int) pY)) {
				if (isHuman) {
					humanAction();
				}
				// return true;
			}

			if (checkContains(mHaikei18Sprite, 135, 10, 295, 215, (int) pX,
					(int) pY)) {
				orangeTouch();
				// return true;
			}

			if (checkContains(mHaikei18Sprite, 295, 30, 465, 275, (int) pX,
					(int) pY)) {
				cherryTouch();
				// return true;
			}

			if (checkContains(mHaikei18Sprite, 465, 10, 645, 220, (int) pX,
					(int) pY)) {
				grapeTouch();
				// return true;
			}
			// Touch bear
			if (checkContains(mBearAniSprite, 60, 120, 195, 300, (int) pX,
					(int) pY)
					&& !isPick
					&& !isBear
					&& mGirlAniSprite.getX() <= 500) {
				Log.d(TAG, "BEAR touch !");
				bearAction();
			}
			// call pick Flower
			if (getFlower(pX, pY)) {
				// return true;
			}

			// Touch 10 Fox
			if (checkContains(mFox3Sprite, 37, 35, 140, 120, (int) pX, (int) pY)
					&& isFox) {
				isFox = false;
				oggTouchAni.play();
				mFox1Sprite.setVisible(false);
				mFox2Sprite.setVisible(false);
				mFox3Sprite.setVisible(true);
				if (foxHandler != null) {
					this.mScene.unregisterUpdateHandler(foxHandler);
				}
				this.mScene
						.registerUpdateHandler(foxHandler = new TimerHandler(
								0.5f, new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mFox1Sprite.setVisible(true);
										mFox2Sprite.setVisible(true);
										mFox3Sprite.setVisible(false);
										isFox = true;
									}
								}));
			}
			// Touch 11 Rabbit
			if (checkContains(mRabbitAnimatedSprite, 0, 50, 90, 130, (int) pX,
					(int) pY) && isRabbit) {
				isRabbit = false;
				oggTouchAni.play();
				mRabbitAnimatedSprite.clearEntityModifiers();
				mRabbitAnimatedSprite.setCurrentTileIndex(1);
				if (rabbitHandler != null) {
					this.mScene.unregisterUpdateHandler(rabbitHandler);
				}
				this.mScene
						.registerUpdateHandler(rabbitHandler = new TimerHandler(
								0.5f, new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mRabbitAnimatedSprite
												.setCurrentTileIndex(0);
										mRabbitAnimatedSprite
												.registerEntityModifier(mRabbitLoopModifier);
										isRabbit = true;
									}
								}));

				return true;
			}

			// Touch 12 Bird
			if (checkContains(mBirdAnimatedSprite, 0, 15, 115, 105, (int) pX,
					(int) pY) && isBird) {
				isBird = false;
				oggTouchAni.play();
				mBirdAnimatedSprite.clearEntityModifiers();
				mBirdAnimatedSprite.setCurrentTileIndex(1);
				if (birdHandler != null) {
					this.mScene.unregisterUpdateHandler(birdHandler);
				}
				this.mScene
						.registerUpdateHandler(birdHandler = new TimerHandler(
								0.5f, new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mBirdAnimatedSprite
												.setCurrentTileIndex(0);
										mBirdAnimatedSprite
												.registerEntityModifier(mBirdLoopModifier);
										isBird = true;
									}
								}));
				return true;
			}

			// Touch 13 Monkey
			if (checkContains(mMonkeyAnimatedSprite, 0, 18, 135, 110, (int) pX,
					(int) pY) && isMonkey) {
				isMonkey = false;
				oggTouchAni.play();
				mMonkeySprite.setVisible(false);
				mMonkeyAnimatedSprite.setCurrentTileIndex(1);
				if (monkeyhandler != null) {
					this.mScene.unregisterUpdateHandler(monkeyhandler);
				}
				this.mScene
						.registerUpdateHandler(monkeyhandler = new TimerHandler(
								0.5f, new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mMonkeyAnimatedSprite
												.setCurrentTileIndex(0);
										mMonkeySprite.setVisible(true);
										isMonkey = true;
									}
								}));

				return true;
			}

		}
		return true;
	}

	@Override
	public void onPauseGame() {
		resetGame();
		super.onPauseGame();

	}
	
	private void resetGame(){
		this.mScene.clearUpdateHandlers();
		if (mGirlAniSprite != null) {
			mGirlAniSprite.clearEntityModifiers();
			mGirlAniSprite.clearUpdateHandlers();
		}
		mGirlPathModifier = null;
		mGirlAniSprite.resetLocalToFirst();
		mGirlAniSprite.reset();
		if (mBearAniSprite != null) {
			mBearAniSprite.clearEntityModifiers();
			mBearAniSprite.resetLocalToFirst();
			mBearAniSprite.reset();

		}
		if (mBearAniSprite != null) {
			if (mBearAniSprite.isAnimationRunning()) {
				mBearAniSprite.stopAnimation();
				mBearAniSprite.setCurrentTileIndex(0);
			}
		}

		for (int i = 0; i < mFlowerSprite.length; i++) {

			if (i < Vol3MorinokumasanResouce.IArrOrange.length) {
				if (mOrangeSprite[i] != null) {
					mOrangeSprite[i].setVisible(false);
				}
			}

			if (i < Vol3MorinokumasanResouce.IArrCherry.length) {
				if (mCherrySprite[i] != null) {
					mCherrySprite[i].setVisible(false);
				}
			}

			if (i < Vol3MorinokumasanResouce.IArrGrape.length) {
				if (mGrapeSprite[i] != null) {
					mGrapeSprite[i].setVisible(false);
				}
			}

			if (i < Vol3MorinokumasanResouce.IArrApple.length) {
				if (mAppleSprite[i] != null) {
					mAppleSprite[i].setVisible(false);
				}
			}
			
			if(mFlowerSprite[i] != null){
				mFlowerSprite[i].setVisible(true);
			}
		}
	}

	// ===========================================================
	// Function and Class Action
	// ===========================================================

	// Orange
	private void orangeTouch() {
		Log.d(TAG, "orangeTouch");
		final int lenght = Vol3MorinokumasanResouce.IArrOrange.length;
		if (ORANGE < lenght) {
			mOrangeSprite[ORANGE].clearEntityModifiers();
			mOrangeSprite[ORANGE].setAlpha(1.0f);
			mOrangeSprite[ORANGE].resetLocalToFirst();
			mOrangeSprite[ORANGE].reset();
			mOrangeSprite[ORANGE].setVisible(true);
			oggOrangeGrape.play();
			ORANGE++;
			if (ORANGE == lenght) {
				playSoundDelay(oggDown, 1.0f);
				for (int i = 0; i < lenght; i++) {
					final int id = i;
					mOrangeSprite[i]
							.registerEntityModifier(new SequenceEntityModifier(
									new MoveYModifier(1.0f, mOrangeSprite[i]
											.getY(),
											mOrangeSprite[i].getY() + 300),
									new AlphaModifier(0.5f, 1.0f, 0.5f)));
					mScene.registerUpdateHandler(new TimerHandler(1.6f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler paramTimerHandler) {
									mOrangeSprite[id].clearEntityModifiers();
									mOrangeSprite[id].setVisible(false);
									if (id == lenght - 1) {
										ORANGE = 0;
									}
								}
							}));
				}

			}
		}

	}

	private void cherryTouch() {
		final int length = Vol3MorinokumasanResouce.IArrCherry.length;
		if (CHERRY < length) {
			mCherrySprite[CHERRY].clearEntityModifiers();
			mCherrySprite[CHERRY].setAlpha(1.0f);
			mCherrySprite[CHERRY].reset();
			mCherrySprite[CHERRY].resetLocalToFirst();
			mCherrySprite[CHERRY].setVisible(true);
			oggCherryApple.play();
			CHERRY++;

			if (CHERRY == length) {
				playSoundDelay(oggDown, 0.8f);
				for (int i = 0; i < length; i++) {
					final int id = i;
					mCherrySprite[i]
							.registerEntityModifier(new SequenceEntityModifier(
									new MoveYModifier(0.8f, mCherrySprite[i]
											.getY(),
											mCherrySprite[i].getY() + 250),
									new AlphaModifier(0.5f, 1.0f, 0.5f)));
					mScene.registerUpdateHandler(new TimerHandler(1.4f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler paramTimerHandler) {
									mCherrySprite[id].setVisible(false);
									if (id == length - 1) {
										CHERRY = 0;
									}
								}
							}));
				}

			}

		}
	}

	private void grapeTouch() {
		final int length = Vol3MorinokumasanResouce.IArrGrape.length;
		if (GRAPE < length) {
			mGrapeSprite[GRAPE].clearEntityModifiers();
			mGrapeSprite[GRAPE].setAlpha(1.0f);
			mGrapeSprite[GRAPE].reset();
			mGrapeSprite[GRAPE].resetLocalToFirst();
			mGrapeSprite[GRAPE].setVisible(true);
			oggOrangeGrape.play();
			GRAPE++;

			if (GRAPE == length) {
				playSoundDelay(oggDown, 1.0f);
				for (int i = 0; i < length; i++) {
					final int id = i;
					mGrapeSprite[i]
							.registerEntityModifier(new SequenceEntityModifier(
									new MoveYModifier(1.0f, mGrapeSprite[i]
											.getY(),
											mGrapeSprite[i].getY() + 290),
									new AlphaModifier(0.5f, 1.0f, 0.5f)));
					mScene.registerUpdateHandler(new TimerHandler(1.6f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler paramTimerHandler) {
									mGrapeSprite[id].setVisible(false);
									if (id == length - 1) {
										GRAPE = 0;
									}
								}
							}));
				}
			}
		}

	}

	private void appleTouch() {
		final int length = Vol3MorinokumasanResouce.IArrApple.length;
		if (APPLE < length) {
			mAppleSprite[APPLE].clearEntityModifiers();
			mAppleSprite[APPLE].resetLocalToFirst();
			mAppleSprite[APPLE].setAlpha(1.0f);
			mAppleSprite[APPLE].reset();
			mAppleSprite[APPLE].setVisible(true);
			oggCherryApple.play();
			APPLE++;

			if (APPLE == length) {
				playSoundDelay(oggDown, 1.0f);
				for (int i = 0; i < length; i++) {
					final int id = i;
					mAppleSprite[i]
							.registerEntityModifier(new SequenceEntityModifier(
									new MoveYModifier(1.0f, mAppleSprite[i]
											.getY(),
											mAppleSprite[i].getY() + 350),
									new AlphaModifier(0.5f, 1.0f, 0.5f)));
					mScene.registerUpdateHandler(new TimerHandler(1.6f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler paramTimerHandler) {
									mAppleSprite[id].setVisible(false);
									if (id == length - 1) {
										APPLE = 0;
									}
								}
							}));
				}
			}

		}

	}

	//
	private void humanAction() {
		isHuman = false;
		oggHuman.play();
		mHumanAnimatedSprite.setCurrentTileIndex(1);
		this.mScene.unregisterUpdateHandler(humanHandler);
		this.mScene.registerUpdateHandler(humanHandler = new TimerHandler(0.5f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						mHumanAnimatedSprite.setCurrentTileIndex(0);
						isHuman = true;
					}
				}));
	}

	// ========== Bear Action ========================================
	private void bearAction() {

		isBear = true;

		float startX = mBearAniSprite.getX();
		float startY = mBearAniSprite.getY();

		final Path path = new Path(4).to(startX, startY)
				.to(BEAR_POINT_AX, BEAR_POINT_AY)
				.to(BEAR_POINT_BX, BEAR_POINT_BY)
				.to(BEAR_POINT_CX, BEAR_POINT_CY);

		final float duration = TIME_BEAR * (startX + mBearAniSprite.getWidth());
		mBearPathModifier = new PathModifier(duration, path,
				new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						mBearAniSprite.stopAnimation();
						mBearAniSprite.setCurrentTileIndex(0);
						mBearAniSprite.setPosition(BEAR_POINT_X, BEAR_POINT_Y);
						isBear = false;
						isUpdateGirl = true;
						isPick = false;
						mGirlAniSprite.setPosition(GIRL_POINT_X, GIRL_POINT_Y);
						mGirlAniSprite.setVisible(true);
						resumeGirl();
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {

					}
				});

		mBearAniSprite.setCurrentTileIndex(1);
		oggBear.play();
		this.mScene.unregisterUpdateHandler(bearHandler);
		this.mScene.registerUpdateHandler(bearHandler = new TimerHandler(2.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						mBearAniSprite.animate(
								new long[] { 200, 200, 200, 200 }, new int[] {
										2, 3, 4, 5 }, -1);
						mBearAniSprite
								.registerEntityModifier(mBearPathModifier);
					}
				}));

	}

	// ========== Girl Action =========================================
	private void girlRun() {
		mGirlAniSprite.clearUpdateHandlers();
		pauseGirl();

		int nframe[] = { 6, 7 };
		long nduration[] = { 500, 800 };

		playSoundDelay(oggGirl, 0.5f);

		mGirlAniSprite.animate(nduration, nframe, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
					int paramInt) {
			}

			@Override
			public void onAnimationFrameChanged(
					AnimatedSprite paramAnimatedSprite, int paramInt1,
					int paramInt2) {
			}

			@Override
			public void onAnimationLoopFinished(
					AnimatedSprite paramAnimatedSprite, int paramInt1,
					int paramInt2) {
			}

			@Override
			public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
				int nframe[] = { 8, 9, 10, 11 };
				long nduration[] = { 250, 250, 250, 250 };
				mGirlAniSprite.animate(nduration, nframe, -1);

				final float duration = TIME_GIRL_RUN
						* (mGirlAniSprite.getX() + mGirlAniSprite.getWidth());
				mGirlPathModifier = new PathModifier(duration, getPathGirl(),
						new IEntityModifierListener() {

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								mGirlAniSprite.setVisible(false);
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> paramIModifier,
									IEntity paramT) {

							}
						});

				mGirlAniSprite.registerEntityModifier(mGirlPathModifier);
			}
		});

	}

	// Take Flower
	private void takeFlower(final int id) {
		Log.d(TAG, "takeFlower" + id);

		this.mScene.unregisterUpdateHandler(flowerHandler[id]);
		this.mScene.registerUpdateHandler(flowerHandler[id] = new TimerHandler(
				0.75f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						mFlowerSprite[id].setVisible(false);
						mScene.registerUpdateHandler(new TimerHandler(2.0f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										if (mFlowerSprite[id] != null) {
											mFlowerSprite[id].setVisible(true);
										}
									}
								}));
					}
				}));

		// girl change STATE
		final int iframe = GIRL_FRAME_FLOWER[PICK_FLOWER_ID];
		final long takeDuration[] = new long[] { 750, 500 };
		final int takeFrame[] = new int[] { 2, iframe };

		playSoundDelay(oggTakeFlower, 0.5f);
		mGirlAniSprite.animate(takeDuration, takeFrame, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite paramAnimatedSprite, int paramInt) {

					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {

					}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {

					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						resumeGirl();
						isPick = false;
						Log.d(TAG, "takeFlower isPick" + isPick);
					}
				});

	}

	// Check pick Flower
	private boolean getFlower(float pX, float pY) {
		if (isPick || isBear) {
			Log.d(TAG, "getFlower true");
			return false;
		}
		final float GIRL_X_W = mGirlAniSprite.getX()
				+ mGirlAniSprite.getWidth() / 2;

		if (mFlowerSprite[0].contains(pX, pY)
				&& mFlowerSprite[0].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[1].contains(pX, pY)
				&& mFlowerSprite[1].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[2].contains(pX, pY)
				&& mFlowerSprite[2].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[3].contains(pX, pY)
				&& mFlowerSprite[3].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[4].contains(pX, pY)
				&& mFlowerSprite[4].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[5].contains(pX, pY)
				&& mFlowerSprite[5].getX() > GIRL_X_W) {
			return true;
		} else if (mFlowerSprite[6].contains(pX, pY)
				&& mFlowerSprite[6].getX() > GIRL_X_W) {
			return true;
		} else if (mGirlAniSprite.getX() >= 500) {
			return true;
		}

		Log.d(TAG, "getFlower");
		if (checkContains(mGirlAniSprite, 80, 25, 260, 300, (int) pX, (int) pY)
				|| mFlowerSprite[0].contains(pX, pY)
				|| mFlowerSprite[1].contains(pX, pY)
				|| mFlowerSprite[2].contains(pX, pY)
				|| mFlowerSprite[3].contains(pX, pY)
				|| mFlowerSprite[4].contains(pX, pY)
				|| mFlowerSprite[5].contains(pX, pY)
				|| mFlowerSprite[6].contains(pX, pY)) {
			Log.d(TAG, "getFlower -> Checking");

			for (int i = 6; i >= 0; i--) {
				if (GIRL_X_W > (mFlowerSprite[i].getX() + mFlowerSprite[i]
						.getWidth() / 2) && mFlowerSprite[i].isVisible()) {
					PICK_FLOWER_ID = i;
					isPick = true;
					GIRL_PAUSE_X = mFlowerSprite[i].getX()
							+ mFlowerSprite[i].getWidth() / 2
							- mGirlAniSprite.getWidth() / 2;
					Log.d(TAG, "getFlower -> OK");
					return true;
				}
			}
		}
		Log.d(TAG, "getFlower false");
		return false;
	}

	private void pauseGirl() {
		Log.d(TAG, "pauseGirl 0");
		// mGirlAniSprite.clearUpdateHandlers();
		mGirlAniSprite.clearEntityModifiers();
		mGirlPathModifier = null;
	}

	private void resumeGirl() {
		Log.d(TAG, "resumeGirl");
		isUpdateGirl = true;
		float duration = 0;
		int nframe[] = { 0, 1 };
		long nduration[] = { 500, 500 };
		mGirlAniSprite.animate(nduration, nframe, -1);
		duration = TIME_GIRL
				* (mGirlAniSprite.getX() + mGirlAniSprite.getWidth());

		mGirlPathModifier = new PathModifier(duration, getPathGirl(),
				new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						mGirlAniSprite.setPosition(GIRL_POINT_X, GIRL_POINT_Y);
						resumeGirl();
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {

					}
				});
		mGirlAniSprite.registerEntityModifier(mGirlPathModifier);
		mGirlAniSprite.registerEntityModifier(mGirlPathModifier);
		mGirlAniSprite.registerUpdateHandler(updateGirl);

		Log.d(TAG, "duration " + duration + "mGirlAniSprite.getX() "
				+ mGirlAniSprite.getX());

	}

	private Path getPathGirl() {
		Path path = null;
		float startX = mGirlAniSprite.getX();
		float startY = mGirlAniSprite.getY();

		if (startX > GIRL_POINT_AX) {
			path = new Path(4).to(startX, startY)
					.to(GIRL_POINT_AX, GIRL_POINT_AY)
					.to(GIRL_POINT_BX, GIRL_POINT_BY)
					.to(GIRL_POINT_CX, GIRL_POINT_CY);
		} else if (startX > GIRL_POINT_BX) {
			path = new Path(3).to(startX, startY)
					.to(GIRL_POINT_BX, GIRL_POINT_BY)
					.to(GIRL_POINT_CX, GIRL_POINT_CY);
		} else if (startX > GIRL_POINT_CX) {
			path = new Path(2).to(startX, startY).to(GIRL_POINT_CX,
					GIRL_POINT_CY);
		}

		Log.d(TAG, "Path " + path);
		return path;
	}

}
