package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3FushigiResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Fushigi extends BaseGameActivity implements
		IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_VOL3FUSHIGI";

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mAllitemTexturePack;
	private TexturePack mBackgroundTexturePack;

	private static final int COOKIE_X = 392;
	private static final int COOKIE_Y = 435;

	private static int CREAM_ODD_LEFT_PX = 86;
	private static int CREAM_ODD_LEFT_PY = 238;
	private static int CREAM_CHAN_LEFT_PX = 113;
	private static int CREAM_CHAN_LEFT_PY = 221;

	private static int CREAM_ODD_RIGHT_PX = 0;
	private static int CREAM_ODD_RIGHT_PY = 0;
	private static int CREAM_CHAN_RIGHT_PX = 0;
	private static int CREAM_CHAN_RIGHT_PY = 0;

	private static int CREAM_COUNTER = 0;

	private static boolean isDuck = true;
	private static boolean isStrawberry = true;
	private static boolean isBlueCake = true;
	private static boolean isOrangeCake = true;
	private static boolean isPurpleCake = true;
	private static boolean isGreenCake = true;
	private static boolean isDoor = true;
	private static boolean isBoy = true;
	private static boolean isDog = true;
	private static boolean isBoyDog = true;

	/** TextureRegion **/
	private TextureRegion mHaikeiTextureRegion;
	private TextureRegion mDuckTextureRegion;
	private TextureRegion mHouseTextureRegion;
	private TextureRegion mBlueCakeTextureRegion;
	private TextureRegion mOrangeTextureRegion;
	private TextureRegion mPurpleCakeTextureRegion;
	private TextureRegion mGreenCakeTextureRegion;
	private TextureRegion mCookieTextureRegion;
	private TextureRegion mCreamOneTextureRegion;
	private TextureRegion mCreamTwoTextureRegion;

	/** TiledTextureRegion **/
	private TiledTextureRegion mStrawberryTiledTextureRegion;
	private TiledTextureRegion mCandyTiledTextureRegion;
	private TiledTextureRegion mAniTiledTextureRegion;
	private TiledTextureRegion mDoorTiledTextureRegion;
	private TiledTextureRegion mStickTiledTextureRegion;
	private TiledTextureRegion mBoyTiledTextureRegion;
	private TiledTextureRegion mDogTiledTextureRegion;

	/** Sprite **/
	private Sprite mHaikeiSprite;
	private Sprite mDuckSprite;
	private Sprite mHouseSprite;
	private Sprite mBlueCakeSprite;
	private Sprite mOrangeSprite;
	private Sprite mPurpleCakeSprite;
	private Sprite mGreenCakeSprite;
	private Sprite mCookieSprite;

	private CreamSprite mCreamSprite[] = new CreamSprite[27];

	/** AnimatedSprite **/
	private AnimatedSprite mStrawberryAnimatedSprite;
	private AnimatedSprite mCandyAnimatedSprite;
	private AnimatedSprite mStickAnimatedSprite;
	private AnimatedSprite mAniAnimatedSprite;
	private AnimatedSprite mDoorAnimatedSprite;
	private AnimatedSprite mBoyAnimatedSprite;
	private AnimatedSprite mDogAnimatedSprite;

	/** Sound **/
	private Sound oggDoor, oggStrawberry, oggCandy, oggCake, oggBoyFace,
			oggDogTouch, oggBoyShareCake, oggDogEat, oggStick, oggSyu, oggDuck,
			oggTouch;

	@Override
	protected void loadKaraokeScene() {

		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

		mHaikeiSprite = new Sprite(0, 0, mHaikeiTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikeiSprite);

		mDuckSprite = new Sprite(205, 78, mDuckTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mDuckSprite);

		mHouseSprite = new Sprite(52, 0, mHouseTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHouseSprite);

		mStrawberryAnimatedSprite = new AnimatedSprite(87, 426,
				mStrawberryTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mStrawberryAnimatedSprite);

		mCandyAnimatedSprite = new AnimatedSprite(397, 121,
				mCandyTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCandyAnimatedSprite);

		mGreenCakeSprite = new Sprite(761, 362, mGreenCakeTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mGreenCakeSprite);

		mPurpleCakeSprite = new Sprite(705, 347, mPurpleCakeTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mPurpleCakeSprite);

		mOrangeSprite = new Sprite(764, 309, mOrangeTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mOrangeSprite);

		mBlueCakeSprite = new Sprite(712, 263, mBlueCakeTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBlueCakeSprite);

		mStickAnimatedSprite = new AnimatedSprite(588, 277,
				mStickTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mStickAnimatedSprite);

		mAniAnimatedSprite = new AnimatedSprite(417, 303,
				mAniTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAniAnimatedSprite);

		mDoorAnimatedSprite = new AnimatedSprite(414, 301,
				mDoorTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mDoorAnimatedSprite);

		// Cream

		CREAM_ODD_LEFT_PX = 86;
		CREAM_ODD_LEFT_PY = 238;
		CREAM_CHAN_LEFT_PX = 113;
		CREAM_CHAN_LEFT_PY = 221;

		CREAM_ODD_RIGHT_PX = 0;
		CREAM_ODD_RIGHT_PY = 0;
		CREAM_CHAN_RIGHT_PX = 0;
		CREAM_CHAN_RIGHT_PY = 0;

		for (int i = 0; i < 7; i++) {
			// Left odd
			mCreamSprite[i] = new CreamSprite(CREAM_ODD_LEFT_PX,
					CREAM_ODD_LEFT_PY, mCreamOneTextureRegion,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mCreamSprite[i]);
			this.mScene.registerTouchArea(mCreamSprite[i]);
			CREAM_ODD_LEFT_PX += 59;
			CREAM_ODD_LEFT_PY -= 39;
			mCreamSprite[i].setVisible(false);
		}

		for (int i = 7; i < 13; i++) {
			// Left Chan
			mCreamSprite[i] = new CreamSprite(CREAM_CHAN_LEFT_PX,
					CREAM_CHAN_LEFT_PY, mCreamTwoTextureRegion,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mCreamSprite[i]);
			this.mScene.registerTouchArea(mCreamSprite[i]);
			CREAM_CHAN_LEFT_PX += 59;
			CREAM_CHAN_LEFT_PY -= 39;
			mCreamSprite[i].setVisible(false);
		}

		CREAM_ODD_RIGHT_PX = CREAM_ODD_LEFT_PX;
		CREAM_ODD_RIGHT_PY = CREAM_ODD_LEFT_PY + 49;
		CREAM_CHAN_RIGHT_PX = CREAM_CHAN_LEFT_PX;
		CREAM_CHAN_RIGHT_PY = CREAM_CHAN_LEFT_PY;

		for (int i = 20; i < 27; i++) {
			// Right Chan
			mCreamSprite[i] = new CreamSprite(CREAM_CHAN_RIGHT_PX,
					CREAM_CHAN_RIGHT_PY, mCreamTwoTextureRegion,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mCreamSprite[i]);
			this.mScene.registerTouchArea(mCreamSprite[i]);
			CREAM_CHAN_RIGHT_PX += 59;
			CREAM_CHAN_RIGHT_PY += 39;
			mCreamSprite[i].setVisible(false);
		}

		for (int i = 13; i < 20; i++) {
			// Right odd
			mCreamSprite[i] = new CreamSprite(CREAM_ODD_RIGHT_PX,
					CREAM_ODD_RIGHT_PY, mCreamOneTextureRegion,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mCreamSprite[i]);
			this.mScene.registerTouchArea(mCreamSprite[i]);
			CREAM_ODD_RIGHT_PX += 59;
			CREAM_ODD_RIGHT_PY += 39;
			mCreamSprite[i].setVisible(false);
		}
		// End Cream

		mBoyAnimatedSprite = new AnimatedSprite(106, 165,
				mBoyTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBoyAnimatedSprite);

		mDogAnimatedSprite = new AnimatedSprite(625, 376,
				mDogTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mDogAnimatedSprite);

		mCookieSprite = new Sprite(392, 435, mCookieTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCookieSprite);
		mCookieSprite.setVisible(false);

		addGimmicsButton(mScene, Vol3FushigiResouce.SOUND_GIMMIC, new int[] {
				Vol3FushigiResouce.Allitem.A9_01_IPHONE4_E00173_UKURERE_ID,
				Vol3FushigiResouce.Allitem.A9_02_IPHONE4_E00174_STILPAN_ID,
				Vol3FushigiResouce.Allitem.A9_03_IPHONE4_QUESTION_ID },
				mAllitemTexturePack.getTexturePackTextureRegionLibrary());
	}

	@Override
	protected void loadKaraokeResources() {

		mAllitemTexturePack = mTexturePackLoaderFromSource.load("Allitem.xml");
		mBackgroundTexturePack = mTexturePackLoaderFromSource
				.load("background.xml");
		mAllitemTexturePack.loadTexture();
		mBackgroundTexturePack.loadTexture();

		final TexturePackTextureRegionLibrary mAllitemTexturePackLib = mAllitemTexturePack
				.getTexturePackTextureRegionLibrary();

		this.mHaikeiTextureRegion = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary().get(
						Vol3FushigiResouce.background.A9_13_IPHONE4_HAIKEI_ID);
		this.mDuckTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_10_IPHONE4_DUCK_ID);
		this.mHouseTextureRegion = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary().get(
						Vol3FushigiResouce.background.A9_12_IPHONE4_HOUSE_ID);
		this.mStrawberryTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_04_1_IPHONE4_STRAWBERRY_ID,
				Vol3FushigiResouce.Allitem.A9_04_2_IPHONE4_STRAWBERRY_ID,
				Vol3FushigiResouce.Allitem.A9_04_3_IPHONE4_STRAWBERRY_ID);
		this.mCandyTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_05_1_IPHONE4_CANDY_ID,
				Vol3FushigiResouce.Allitem.A9_05_2_IPHONE4_CANDY_ID);
		this.mStickTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_09_1_IPHONE4_STICK_ID,
				Vol3FushigiResouce.Allitem.A9_09_2_IPHONE4_STICK_ID);
		this.mBlueCakeTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_06_1_IPHONE4_BLUE_CAKE_ID);
		this.mOrangeTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_06_2_IPHONE4_ORANGE_CAKE_ID);
		this.mPurpleCakeTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_06_3_IPHONE4_PURPLE_CAKE_ID);
		this.mGreenCakeTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_06_4_IPHONE4_GREEN_CAKE_ID);
		this.mAniTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_03_2_1_IPHONE4_RABBIT_ID,
				Vol3FushigiResouce.Allitem.A9_03_2_2_IPHONE4_BEAR_ID,
				Vol3FushigiResouce.Allitem.A9_03_2_3_IPHONE4_CAT_ID,
				Vol3FushigiResouce.Allitem.A9_03_2_4_IPHONE4_PIG_ID);
		this.mDoorTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_03_1_1_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_2_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_3_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_4_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_5_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_6_IPHONE4_DOOR_ID,
				Vol3FushigiResouce.Allitem.A9_03_1_7_IPHONE4_DOOR_ID);
		this.mBoyTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_07_1_1_IPHONE4_BOY_CLAP_ID,
				Vol3FushigiResouce.Allitem.A9_07_1_2_IPHONE4_BOY_CLAP_ID,
				Vol3FushigiResouce.Allitem.A9_07_2_1_IPHONE4_BOY_EAT_ID,
				Vol3FushigiResouce.Allitem.A9_07_2_2_IPHONE4_BOY_EAT_ID,
				Vol3FushigiResouce.Allitem.A9_07_2_3_IPHONE4_BOY_EAT_ID,
				Vol3FushigiResouce.Allitem.A9_07_2_4_IPHONE4_BOY_EAT_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_1_IPHONE4_BOY_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_2_IPHONE4_BOY_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_3_IPHONE4_BOY_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_4_IPHONE4_BOY_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_5_IPHONE4_BOY_ID,
				Vol3FushigiResouce.Allitem.A9_08_2_6_IPHONE4_BOY_ID);
		this.mDogTiledTextureRegion = getTiledTextureFromPacker(
				mAllitemTexturePack,
				Vol3FushigiResouce.Allitem.A9_08_1_1_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_1_2_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_1_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_2_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_3_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_4_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_5_IPHONE4_DOG_ID,
				Vol3FushigiResouce.Allitem.A9_08_3_6_IPHONE4_DOG_ID);
		this.mCookieTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_08_2_7_IPHONE4_COOKIE_ID);
		this.mCreamOneTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_11_1_IPHONE4_CREAM_03_ID);
		this.mCreamTwoTextureRegion = mAllitemTexturePackLib
				.get(Vol3FushigiResouce.Allitem.A9_11_2_IPHONE4_CREAM_03_ID);

	}

	@Override
	protected void loadKaraokeSound() {

		oggDoor = loadSoundResourceFromSD(Vol3FushigiResouce.E0054_KNOCK);
		oggStrawberry = loadSoundResourceFromSD(Vol3FushigiResouce.E00192_HAI);
		oggCandy = loadSoundResourceFromSD(Vol3FushigiResouce.E00175_OKASHI);
		oggCake = loadSoundResourceFromSD(Vol3FushigiResouce.E00176_POTO2);
		oggBoyFace = loadSoundResourceFromSD(Vol3FushigiResouce.E00177_SAKU);
		oggDogTouch = loadSoundResourceFromSD(Vol3FushigiResouce.E00088_DOG_FUSHIGI);
		oggBoyShareCake = loadSoundResourceFromSD(Vol3FushigiResouce.E00178_POI);
		oggDogEat = loadSoundResourceFromSD(Vol3FushigiResouce.E00179_BARI);
		oggStick = loadSoundResourceFromSD(Vol3FushigiResouce.E00070_POMI);
		oggSyu = loadSoundResourceFromSD(Vol3FushigiResouce.E00180_SYU);
		oggDuck = loadSoundResourceFromSD(Vol3FushigiResouce.E00181_NYUN);
		oggTouch = loadSoundResourceFromSD(Vol3FushigiResouce.E00182_PIYO);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("fushigi/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("fushigi/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	public void combineGimmic3WithAction() {
		doorAction();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		float pX = pSceneTouchEvent.getX();
		float pY = pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			if (mDuckSprite.contains(pX, pY)) {
				duckAction();
			} else if (mStrawberryAnimatedSprite.contains(pX, pY)) {
				strawberryAction();
			} else if (mCandyAnimatedSprite.contains(pX, pY)) {
				changeBackground(mCandyAnimatedSprite);
				oggCandy.play();
			} else if (mBlueCakeSprite.contains(pX, pY)) {
				cakeMoveDown(mBlueCakeSprite, isBlueCake);
			} else if (mOrangeSprite.contains(pX, pY)) {
				cakeMoveDown(mOrangeSprite, isOrangeCake);
			} else if (mPurpleCakeSprite.contains(pX, pY)) {
				cakeMoveDown(mPurpleCakeSprite, isPurpleCake);
			} else if (mGreenCakeSprite.contains(pX, pY)) {
				cakeMoveDown(mGreenCakeSprite, isGreenCake);
			} else if (mStickAnimatedSprite.contains(pX, pY)) {
				oggStick.play();
				changeBackground(mStickAnimatedSprite);
			} else if (checkContains(mDoorAnimatedSprite, 10, 0, 165, 252,
					(int) pX, (int) pY)) {
				doorAction();
			} else if (checkContains(mBoyAnimatedSprite, 95, 50, 275, 240,
					(int) pX, (int) pY)) {
				boyToucFace();
			} else if (checkContains(mDogAnimatedSprite, 50, 65, 230, 240,
					(int) pX, (int) pY)) {
				dogTouch();
			}

		}
		return true;
	}

	@Override
	public void onPauseGame() {
			for (int i = 0; i < mCreamSprite.length; i++) {
				mCreamSprite[i].setVisible(false);
				Log.d(TAG, "ID:" + i + "   mCreamSprite" + mCreamSprite[i]);

			}
			CREAM_COUNTER = 0;

			mDuckSprite.clearEntityModifiers();
			mDuckSprite.resetLocalToFirst();
			mDuckSprite.reset();
			isDuck = true;

			mDoorAnimatedSprite.clearEntityModifiers();
			mDoorAnimatedSprite.setCurrentTileIndex(0);
			isDoor = true;

			mCandyAnimatedSprite.setCurrentTileIndex(0);
			mStickAnimatedSprite.setCurrentTileIndex(0);
			mCookieSprite.setVisible(false);

			mStrawberryAnimatedSprite.clearEntityModifiers();
			mStrawberryAnimatedSprite.resetLocalToFirst();
			mStrawberryAnimatedSprite.reset();
			isStrawberry = true;
			isBoyDog = true;
			mScene.clearUpdateHandlers();
		super.onPauseGame();
	}

	@Override
	public void onResumeGame() {
		boyDefaultAction();
		dogDefaultAction();
		super.onResumeGame();
	}

	// ===========================================================
	// Function and Class Action
	// ===========================================================

	private void checkFullCream() {
		if (CREAM_COUNTER == mCreamSprite.length) {
			Log.d(TAG, "Full Cream");
			mScene.registerUpdateHandler(new TimerHandler(2.0f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							for (int i = 0; i < mCreamSprite.length; i++) {
								mCreamSprite[i].setVisible(false);
								CREAM_COUNTER = 0;
							}
						}
					}));
		}
	}

	private class CreamSprite extends Sprite {
		public CreamSprite(float pX, float pY, TextureRegion pTextureRegion,
				VertexBufferObjectManager pVertex) {
			super(pX, pY, pTextureRegion, pVertex);
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {

			if (!this.isVisible()) {
				oggTouch.play();
				this.setVisible(true);
				CREAM_COUNTER++;
				checkFullCream();
			}

			return false;
		}

	}

	private void cookieFlytoDog() {

		Path path = new Path(5).to(COOKIE_X, COOKIE_Y)
				.to(COOKIE_X + 57, COOKIE_Y - 12)
				.to(COOKIE_X + 115, COOKIE_Y - 25)
				.to(COOKIE_X + 172, COOKIE_Y - 12)
				.to(COOKIE_X + 230, COOKIE_Y + 30);

		mCookieSprite.setVisible(true);
		mCookieSprite.registerEntityModifier(new ParallelEntityModifier(
				new LoopEntityModifier(new RotationModifier(1.0f, 0, 360)),
				new PathModifier(0.5f, path, new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						Log.d(TAG, "END FLY");
						mCookieSprite.setVisible(false);
						isBoyDog = true;
						boyDefaultAction();
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}
				})));

	}

	private void boyDefaultAction() {
		mBoyAnimatedSprite.animate(new long[] { 350, 350 }, new int[] { 0, 1 },
				-1);
		isBoy = true;
	}

	private void boyToucFace() {
		if (isBoy && isBoyDog) {
			isBoy = false;
			oggBoyFace.play();
			mBoyAnimatedSprite.animate(new long[] { 400, 400, 400, 400 },
					new int[] { 2, 3, 4, 5 }, 0, new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							boyDefaultAction();
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
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {

						}

					});
		}
	}

	private void dogDefaultAction() {
		mDogAnimatedSprite.animate(new long[] { 450, 450 }, new int[] { 0, 1 },
				-1);
		isDog = true;
	}

	private void dogTouch() {
		if (isDog && isBoy) {

			isDog = false;
			isBoyDog = false;
			oggDogTouch.play();
			playSoundDelay(oggBoyShareCake, 0.9f);
			playSoundDelay(oggDogEat, 2.59f);
			mBoyAnimatedSprite.animate(new long[] { 300, 300, 300, 300, 300 },
					new int[] { 6, 7, 8, 9, 10 }, 0, new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mBoyAnimatedSprite.setCurrentTileIndex(11);
							cookieFlytoDog();

							mDogAnimatedSprite.animate(new long[] { 166, 166,
									166, 300, 300, 1000 }, new int[] { 2, 3, 4,
									5, 6, 7 }, 0, new IAnimationListener() {

								@Override
								public void onAnimationFinished(
										AnimatedSprite arg0) {
									dogDefaultAction();
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
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {

						}

					});

		}
	}

	private void doorAction() {
		if (isDoor) {
			isDoor = false;

			int ran = new Random().nextInt(4);
			mAniAnimatedSprite.setCurrentTileIndex(ran);
			oggDoor.play();
			mDoorAnimatedSprite.animate(new long[] { 200, 200, 200, 200, 200,
					200, 1000, 200, 200, 200, 200, 200, 200 }, new int[] { 0,
					1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1, 0 }, 0,
					new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							isDoor = true;
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
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {

						}

					});

		}
	}

	private void cakeMoveDown(final Sprite pSprite, boolean isChecked) {
		if (isChecked) {
			if (pSprite.equals(mBlueCakeSprite)) {
				isBlueCake = false;
			} else if (pSprite.equals(mOrangeSprite)) {
				isOrangeCake = false;
			} else if (pSprite.equals(mPurpleCakeSprite)) {
				isPurpleCake = false;
			} else if (pSprite.equals(mGreenCakeSprite)) {
				isGreenCake = false;
			}
			Log.d(TAG, " Blue" + isBlueCake);
			oggCake.play();
			pSprite.registerEntityModifier(new MoveYModifier(1.0f, pSprite
					.getY(), pSprite.getY() + 250,
					new IEntityModifierListener() {

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							pSprite.setPosition(pSprite.getX(),
									pSprite.getY() - 250);
							if (pSprite.equals(mBlueCakeSprite)) {
								isBlueCake = true;
							} else if (pSprite.equals(mOrangeSprite)) {
								isOrangeCake = true;
							} else if (pSprite.equals(mPurpleCakeSprite)) {
								isPurpleCake = true;
							} else if (pSprite.equals(mGreenCakeSprite)) {
								isGreenCake = true;
							}
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {

						}
					}));
		}
	}

	private void changeBackground(AnimatedSprite vAniSprite) {
		if (vAniSprite.getCurrentTileIndex() == 0) {
			vAniSprite.setCurrentTileIndex(1);
		} else {
			vAniSprite.setCurrentTileIndex(0);
		}
	}

	private void strawberryAction() {
		if (isStrawberry) {

			isStrawberry = false;
			oggStrawberry.play();
			mStrawberryAnimatedSprite.animate(new long[] { 400, 400 },
					new int[] { 1, 2 }, 1, new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mStrawberryAnimatedSprite.setCurrentTileIndex(0);
							isStrawberry = true;
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
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {

						}

					});

		}
	}

	private void duckAction() {
		if (isDuck) {
			isDuck = false;

			oggSyu.play();
			playSoundDelay(oggDuck, 0.3f);

			mDuckSprite.registerEntityModifier(new SequenceEntityModifier(

			new MoveYModifier(1.0f, mDuckSprite.getY(),
					mDuckSprite.getY() - 100), new MoveYModifier(0.3f,
					mDuckSprite.getY() - 100, mDuckSprite.getY() - 85),
					new MoveYModifier(0.3f, mDuckSprite.getY() - 85,
							mDuckSprite.getY() - 100), new MoveYModifier(1.0f,
							mDuckSprite.getY() - 100, mDuckSprite.getY())

			));
			mScene.registerUpdateHandler(new TimerHandler(2.7f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							mDuckSprite.resetLocalToFirst();
							mDuckSprite.reset();
							isDuck = true;
						}
					}));
		}
	}

}
