package jp.co.xing.utaehon03.songs;

import java.util.HashMap;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KishapoppoResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Kishapoppo extends BaseGameActivity implements
		IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_VOL3KISHAPOPPO";

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mAllItemTexturePack;
	private TexturePack mBackgroundTexturePack;
	private TexturePackTextureRegionLibrary mAllItemTexturePackLib;

	private static boolean isBigballoon = true;
	private static boolean isTree = true;
	private static boolean isGirl = true;
	private static boolean isGirraf = true;
	private static boolean isElephant = true;
	private static boolean isGirlFront = true;
	private static boolean isCat = true;
	private static boolean isBoyJump = true;
	private static boolean isApple = true;
	private static boolean isFace = true;
	private static boolean isBoyTrain = true;

	private int arrPoiterXTire[][] = new int[][] { { 173, 527 }, { 311, 527 },
			{ 472, 528 }, { 563, 527 }, { 687, 527 }, { 776, 527 } };

	private int arrBall[][] = new int[][] { { 143, 134 }, { 143, 134 },
			{ 143, 134 }, { 143, 134 } };

	/** TextureRegion **/
	private ITextureRegion mParallaxLayoutTextureRegion;
	private ITextureRegion mHouseTextureRegion;
	private ITextureRegion mTreeTextureRegion;
	private ITextureRegion mTree2TextureRegion;
	private ITextureRegion mAppleTreeTextureRegion;
	private ITextureRegion mBridgeTextureRegion;
	private ITextureRegion mCentertrainTextureRegion;
	private ITextureRegion mSmokeATextureRegion;
	private ITextureRegion mSmokeBTextureRegion;

	private ITextureRegion mAppleTextureRegion[] = new ITextureRegion[3];
	private ITextureRegion mTireTextureRegion[] = new ITextureRegion[6];

	/** TiledTextureRegion **/
	private TiledTextureRegion mGirlTiledTextureRegion;
	private TiledTextureRegion mBoyJumpTiledTextureRegion;
	private TiledTextureRegion mBigballoonTiledTextureRegion;
	private TiledTextureRegion mButterflyTiledTextureRegion;
	private TiledTextureRegion mHouse2TiledTextureRegion;
	private TiledTextureRegion mFlowerTiledTextureRegion;
	private TiledTextureRegion mLionTiledTextureRegion;
	private TiledTextureRegion mTrainTiledTextureRegion;
	private TiledTextureRegion mGirrafTiledTextureRegion;
	private TiledTextureRegion mElephantTiledTextureRegion;
	private TiledTextureRegion mGirlFrontTiledTextureRegion;
	private TiledTextureRegion mCatTiledTextureRegion;
	private TiledTextureRegion mFaceTiledTextureRegion;
	private TiledTextureRegion mBoyTrainTiledTextureRegion;

	private TiledTextureRegion mBallTiledTextureRegion[] = new TiledTextureRegion[4];

	/** Sprite **/
	private Sprite mParallaxFirst;
	private Sprite mParallaxSeconds;
	private Sprite mHouseSprite, mHouseSecondSprite;
	private Sprite mTreeSprite, mTreeSecondSprite;
	private Sprite mTree2Sprite, mTree2SecondSprite;
	private Sprite mAppleTreeSprite, mAppleTreeSecondSprite;
	private Sprite mBridgeSprite, mBridgeSecondSprite;
	private Sprite mCentertrainSprite;
	private Sprite mSmokeASprite;
	private Sprite mSmokeBSprite;

	private Sprite mAppleSprite[] = new Sprite[3];
	private Sprite mAppleSecondSprite[] = new Sprite[3];
	private Sprite mTireSprite[] = new Sprite[6];

	private AnimatedSprite mGirlAnimatedSprite, mGirlAnimatedSecondSprite;
	private AnimatedSprite mBoyJumpAniSprite, mBoyJumpSecondAniSprite;
	private AnimatedSprite mBigballoonAnimatedSprite,
			mBigballoonAnimatedSecondSprite;
	private AnimatedSprite mButterflyAnimatedSprite,
			mButterflyAnimatedSecondSprite;
	private AnimatedSprite mHouse2AnimatedSprite, mHouse2AnimatedSecondSprite;
	private AnimatedSprite mFlowerAniSprite, mFlowerAniSecondSprite;
	private AnimatedSprite mLionAniSprite, mLionAniSecondSprite;
	private AnimatedSprite mTrainAnimatedSprite, mTrainAnimatedSecondSprite;
	private AnimatedSprite mGirrafAnimatedSprite;
	private AnimatedSprite mElephantAnimatedSprite;
	private AnimatedSprite mGirlFrontAnimatedSprite;
	private AnimatedSprite mCatAnimatedSprite;
	private AnimatedSprite mFaceAnimatedSprite;
	private AnimatedSprite mBoyTrainAniSprite;

	private IUpdateHandler autoParallax;

	private HashMap<Integer, ballAnimatedSprite> hashBall = new HashMap<Integer, Vol3Kishapoppo.ballAnimatedSprite>();

	private Sound oggPowa2, oggHusenpan3, oggNew, oggPaon, oggWai, oggNeko,
			oggKonkon, oggBiyon, oggPyu2, oggPaka, oggPyu5, oggGao, oggPoto,
			oggGatanpopo, oggPopopuwa;

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene...........1............");
		mParallaxFirst = new Sprite(CAMERA_WIDTH
				- mParallaxLayoutTextureRegion.getWidth(), 0,
				this.mParallaxLayoutTextureRegion,
				this.getVertexBufferObjectManager());

		mParallaxSeconds = new Sprite(CAMERA_WIDTH
				- mParallaxLayoutTextureRegion.getWidth()
				- mParallaxLayoutTextureRegion.getWidth(), 0,
				this.mParallaxLayoutTextureRegion,
				this.getVertexBufferObjectManager());

		this.mScene.attachChild(mParallaxFirst);
		this.mScene.attachChild(mParallaxSeconds);
		Log.d(TAG, "loadKaraokeScene...........2............");

		mBoyJumpAniSprite = new AnimatedSprite(62, 60,
				mBoyJumpTiledTextureRegion, this.getVertexBufferObjectManager());
		mBoyJumpAniSprite.setVisible(false);
		mBoyJumpSecondAniSprite = new AnimatedSprite(62, 60,
				mBoyJumpTiledTextureRegion, this.getVertexBufferObjectManager());
		mBoyJumpSecondAniSprite.setVisible(false);

		mParallaxFirst.attachChild(mBoyJumpAniSprite);
		mParallaxSeconds.attachChild(mBoyJumpSecondAniSprite);

		Log.d(TAG, "loadKaraokeScene...........3............");
		mHouseSprite = new Sprite(62, 60, mHouseTextureRegion,
				this.getVertexBufferObjectManager());
		mHouseSecondSprite = new Sprite(62, 60, mHouseTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mHouseSprite);
		mParallaxSeconds.attachChild(mHouseSecondSprite);

		mBigballoonAnimatedSprite = new AnimatedSprite(326, 19,
				mBigballoonTiledTextureRegion,
				this.getVertexBufferObjectManager());
		mBigballoonAnimatedSecondSprite = new AnimatedSprite(326, 19,
				mBigballoonTiledTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mBigballoonAnimatedSprite);
		mParallaxSeconds.attachChild(mBigballoonAnimatedSecondSprite);

		mButterflyAnimatedSprite = new AnimatedSprite(554, 193,
				mButterflyTiledTextureRegion,
				this.getVertexBufferObjectManager());
		mButterflyAnimatedSecondSprite = new AnimatedSprite(554, 193,
				mButterflyTiledTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mButterflyAnimatedSprite);
		mParallaxSeconds.attachChild(mButterflyAnimatedSecondSprite);
		mButterflyAnimatedSecondSprite.animate(new long[] { 500, 500 },
				new int[] { 0, 1 }, -1);
		mButterflyAnimatedSprite.animate(new long[] { 500, 500 }, new int[] {
				0, 1 }, -1);

		Log.d(TAG, "loadKaraokeScene.........A..............");
		mHouse2AnimatedSprite = new AnimatedSprite(658, 78,
				mHouse2TiledTextureRegion, this.getVertexBufferObjectManager());
		mHouse2AnimatedSecondSprite = new AnimatedSprite(658, 78,
				mHouse2TiledTextureRegion, this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mHouse2AnimatedSprite);
		mParallaxSeconds.attachChild(mHouse2AnimatedSecondSprite);

		mTree2Sprite = new Sprite(843, 164, mTree2TextureRegion,
				this.getVertexBufferObjectManager());
		mTree2SecondSprite = new Sprite(843, 164, mTree2TextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mTree2Sprite);
		mParallaxSeconds.attachChild(mTree2SecondSprite);

		mTreeSprite = new Sprite(843, 164, mTreeTextureRegion,
				this.getVertexBufferObjectManager());
		mTreeSecondSprite = new Sprite(843, 164, mTreeTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mTreeSprite);
		mParallaxSeconds.attachChild(mTreeSecondSprite);

		mGirlAnimatedSprite = new AnimatedSprite(1068, 174,
				mGirlTiledTextureRegion, this.getVertexBufferObjectManager());
		mGirlAnimatedSprite.animate(new long[] { 500, 500 },
				new int[] { 0, 1 }, -1);
		mGirlAnimatedSecondSprite = new AnimatedSprite(1068, 174,
				mGirlTiledTextureRegion, this.getVertexBufferObjectManager());
		mGirlAnimatedSecondSprite.animate(new long[] { 500, 500 }, new int[] {
				0, 1 }, -1);

		mParallaxFirst.attachChild(mGirlAnimatedSprite);
		mParallaxSeconds.attachChild(mGirlAnimatedSecondSprite);

		mFlowerAniSprite = new AnimatedSprite(1200, 260,
				mFlowerTiledTextureRegion, this.getVertexBufferObjectManager());
		mFlowerAniSecondSprite = new AnimatedSprite(1200, 260,
				mFlowerTiledTextureRegion, this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mFlowerAniSprite);
		mParallaxSeconds.attachChild(mFlowerAniSecondSprite);
		mFlowerAniSprite.animate(new long[] { 500, 500 }, new int[] { 0, 1 },
				-1);
		mFlowerAniSecondSprite.animate(new long[] { 500, 500 }, new int[] { 0,
				1 }, -1);

		Log.d(TAG, "loadKaraokeScene.........B..............");
		mLionAniSprite = new AnimatedSprite(1293, 143, mLionTiledTextureRegion,
				this.getVertexBufferObjectManager());
		mLionAniSecondSprite = new AnimatedSprite(1293, 143,
				mLionTiledTextureRegion, this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mLionAniSprite);
		mParallaxSeconds.attachChild(mLionAniSecondSprite);

		mAppleTreeSprite = new Sprite(1488, 165, mAppleTreeTextureRegion,
				this.getVertexBufferObjectManager());
		mAppleTreeSecondSprite = new Sprite(1488, 165, mAppleTreeTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mAppleTreeSprite);
		mParallaxSeconds.attachChild(mAppleTreeSecondSprite);

		mAppleSprite[0] = new Sprite(1536, 215, mAppleTextureRegion[0],
				this.getVertexBufferObjectManager());
		mAppleSecondSprite[0] = new Sprite(1536, 215, mAppleTextureRegion[0],
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mAppleSprite[0]);
		mParallaxSeconds.attachChild(mAppleSecondSprite[0]);

		mAppleSprite[1] = new Sprite(1572, 205, mAppleTextureRegion[1],
				this.getVertexBufferObjectManager());
		mAppleSecondSprite[1] = new Sprite(1572, 205, mAppleTextureRegion[1],
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mAppleSprite[1]);
		mParallaxSeconds.attachChild(mAppleSecondSprite[1]);

		mAppleSprite[2] = new Sprite(1592, 240, mAppleTextureRegion[2],
				this.getVertexBufferObjectManager());
		mAppleSecondSprite[2] = new Sprite(1592, 240, mAppleTextureRegion[2],
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mAppleSprite[2]);
		mParallaxSeconds.attachChild(mAppleSecondSprite[2]);

		mTrainAnimatedSprite = new AnimatedSprite(1282, 45,
				mTrainTiledTextureRegion, this.getVertexBufferObjectManager());
		mTrainAnimatedSecondSprite = new AnimatedSprite(1282, 45,
				mTrainTiledTextureRegion, this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mTrainAnimatedSprite);
		mParallaxSeconds.attachChild(mTrainAnimatedSecondSprite);

		mBridgeSprite = new Sprite(1284, 50, mBridgeTextureRegion,
				this.getVertexBufferObjectManager());
		mBridgeSecondSprite = new Sprite(1284, 50, mBridgeTextureRegion,
				this.getVertexBufferObjectManager());
		mParallaxFirst.attachChild(mBridgeSprite);
		mParallaxSeconds.attachChild(mBridgeSecondSprite);

		mCentertrainSprite = new Sprite(53, 246, mCentertrainTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCentertrainSprite);

		for (int i = 0; i < arrPoiterXTire.length; i++) {

			mTireSprite[i] = new Sprite(arrPoiterXTire[i][0],
					arrPoiterXTire[i][1], mTireTextureRegion[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mTireSprite[i]);
			mTireSprite[i].registerEntityModifier(new LoopEntityModifier(
					new RotationModifier(2.0f, 360, 0)));

		}

		mElephantAnimatedSprite = new AnimatedSprite(499, 273,
				mElephantTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mElephantAnimatedSprite);
		runAni(mElephantAnimatedSprite);

		mGirrafAnimatedSprite = new AnimatedSprite(433, 190,
				mGirrafTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mGirrafAnimatedSprite);
		runAni(mGirrafAnimatedSprite);

		mCatAnimatedSprite = new AnimatedSprite(752, 389,
				mCatTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCatAnimatedSprite);
		runAni(mCatAnimatedSprite);

		mGirlFrontAnimatedSprite = new AnimatedSprite(661, 312,
				mGirlFrontTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mGirlFrontAnimatedSprite);
		runAni(mGirlFrontAnimatedSprite);

		mFaceAnimatedSprite = new AnimatedSprite(82, 407,
				mFaceTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mFaceAnimatedSprite);
		runAni(mFaceAnimatedSprite);

		mBoyTrainAniSprite = new AnimatedSprite(251, 329,
				mBoyTrainTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mBoyTrainAniSprite);
		runAni(mBoyTrainAniSprite);

		this.mSmokeASprite = new Sprite(56, 332, mSmokeATextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSmokeASprite);
		this.mSmokeASprite.setVisible(false);

		this.mSmokeBSprite = new Sprite(136, -70, mSmokeBTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSmokeBSprite);
		this.mSmokeBSprite.setVisible(false);

		addGimmicsButton(mScene, Vol3KishapoppoResouce.SOUND_GIMMIC, new int[] {
				Vol3KishapoppoResouce.AllItem.A5_KISYAPOPO_1_IPHONE_ID,
				Vol3KishapoppoResouce.AllItem.A5_KISYAPOPO_2_IPHONE_ID,
				Vol3KishapoppoResouce.AllItem.A5_KISYAPOPO_3_IPHONE_ID },
				mAllItemTexturePackLib);

		Log.d(TAG, "loadKaraokeScene.......................");
	}

	protected void loadComplete() {
		isBigballoon = true;
		isTree = true;
		isGirl = true;
		isGirraf = true;
		isElephant = true;
		isGirlFront = true;
		isCat = true;
		isBoyJump = true;
		isApple = true;
		isFace = true;
		isBoyTrain = true;

		mParallaxFirst.setPosition(
				CAMERA_WIDTH - mParallaxLayoutTextureRegion.getWidth(), 0);
		mParallaxSeconds.setPosition(CAMERA_WIDTH
				- mParallaxLayoutTextureRegion.getWidth()
				- mParallaxLayoutTextureRegion.getWidth(), 0);

		autoParallax = new IUpdateHandler() {
			final float time = 2;

			@Override
			public void reset() {

			}

			@Override
			public void onUpdate(float pSecondsElapsed) {

				float pFirst = mParallaxFirst.getX();
				mParallaxFirst.setPosition(pFirst += time, 0);

				float pSeconds = mParallaxSeconds.getX();
				mParallaxSeconds.setPosition(pSeconds += time, 0);

				if (mParallaxFirst.getX() == 0) {
					mParallaxSeconds.setPosition(-1904, 0);
					Log.d(TAG, "mParallaxSeconds set 1906");
				} else if (mParallaxSeconds.getX() == 0) {
					mParallaxFirst.setPosition(-1904, 0);
					Log.d(TAG, "mParallaxFirst set 1906");
				}
			}
		};
		this.mScene.registerUpdateHandler(autoParallax);
	};

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("kishapoppo/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("kishapoppo/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		mAllItemTexturePack = mTexturePackLoaderFromSource.load("AllItem.xml");
		mBackgroundTexturePack = mTexturePackLoaderFromSource
				.load("BackGround.xml");

		mAllItemTexturePack.loadTexture();
		mBackgroundTexturePack.loadTexture();

		mAllItemTexturePackLib = mAllItemTexturePack
				.getTexturePackTextureRegionLibrary();

		this.mParallaxLayoutTextureRegion = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary()
				.get(Vol3KishapoppoResouce.BackGround.A5_21_IPHONE_HAIKEI_ID);

		this.mGirlTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_15_1_IPHONE_GIRL2_ID,
				Vol3KishapoppoResouce.AllItem.A5_15_2_IPHONE_GIRL2_ID,
				Vol3KishapoppoResouce.AllItem.A5_15_3_IPHONE_GIRL2_ID,
				Vol3KishapoppoResouce.AllItem.A5_15_4_IPHONE_GIRL2_ID);
		this.mHouseTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_10_1_IPHONE_JUMP_ID);
		this.mBoyJumpTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_10_2_IPHONE_JUMP_ID,
				Vol3KishapoppoResouce.AllItem.A5_10_3_IPHONE_JUMP_ID,
				Vol3KishapoppoResouce.AllItem.A5_10_4_IPHONE_JUMP_ID);
		this.mBigballoonTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_11_1_IPHONE_BIGBALLOON_ID,
				Vol3KishapoppoResouce.AllItem.A5_11_2_IPHONE_BIGBALLOON_ID);
		this.mButterflyTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_12_1_IPHONE_BUTTERFLY_ID,
				Vol3KishapoppoResouce.AllItem.A5_12_2_IPHONE_BUTTERFLY_ID);
		this.mHouse2TiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_13_1_IPHONE_HOUSE_ID,
				Vol3KishapoppoResouce.AllItem.A5_13_2_IPHONE_HOUSE_ID);
		this.mTreeTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_14_1_IPHONE_TREE_ID);
		this.mTree2TextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_14_2_IPHONE_TREE_ID);
		this.mFlowerTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_19_1_IPHONE_FLOWER_ID,
				Vol3KishapoppoResouce.AllItem.A5_19_2_IPHONE_FLOWER_ID);
		this.mLionTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_16_1_IPHONE_LION_ID,
				Vol3KishapoppoResouce.AllItem.A5_16_2_IPHONE_LION_ID,
				Vol3KishapoppoResouce.AllItem.A5_16_3_IPHONE_LION_ID);
		this.mAppleTreeTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_17_1_IPHONE_APPLE_ID);

		int Apple[] = new int[] {
				Vol3KishapoppoResouce.AllItem.A5_17_2_IPHONE_APPLE_ID,
				Vol3KishapoppoResouce.AllItem.A5_17_3_IPHONE_APPLE_ID,
				Vol3KishapoppoResouce.AllItem.A5_17_4_IPHONE_APPLE_ID };

		for (int i = 0; i < 3; i++) {
			this.mAppleTextureRegion[i] = mAllItemTexturePackLib.get(Apple[i]);
		}
		this.mBridgeTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_18_1_IPHONE_TRAIN_ID);
		this.mTrainTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack, Vol3KishapoppoResouce.IArrTrain);
		this.mCentertrainTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_22_IPHONE_CENTERTRAIN_ID);

		for (int i = 0; i < Vol3KishapoppoResouce.IArrTire.length; i++) {
			this.mTireTextureRegion[i] = mAllItemTexturePackLib
					.get(Vol3KishapoppoResouce.IArrTire[i]);
		}

		this.mGirrafTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_06_1_IPHONE_GIRRAF_ID,
				Vol3KishapoppoResouce.AllItem.A5_06_2_IPHONE_GIRRAF_ID,
				Vol3KishapoppoResouce.AllItem.A5_06_3_IPHONE_GIRRAF_ID,
				Vol3KishapoppoResouce.AllItem.A5_06_4_IPHONE_GIRRAF_ID);
		this.mElephantTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_07_1_IPHONE_ELEPHANT_ID,
				Vol3KishapoppoResouce.AllItem.A5_07_2_IPHONE_ELEPHANT_ID,
				Vol3KishapoppoResouce.AllItem.A5_07_3_IPHONE_ELEPHANT_ID,
				Vol3KishapoppoResouce.AllItem.A5_07_4_IPHONE_ELEPHANT_ID);
		this.mGirlFrontTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_08_1_IPHONE_GIRL_ID,
				Vol3KishapoppoResouce.AllItem.A5_08_2_IPHONE_GIRL_ID,
				Vol3KishapoppoResouce.AllItem.A5_08_3_IPHONE_GIRL_ID,
				Vol3KishapoppoResouce.AllItem.A5_08_4_IPHONE_GIRL_ID);
		this.mCatTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_09_1_IPHONE_CAT_ID,
				Vol3KishapoppoResouce.AllItem.A5_09_2_IPHONE_CAT_ID,
				Vol3KishapoppoResouce.AllItem.A5_09_3_IPHONE_CAT_ID,
				Vol3KishapoppoResouce.AllItem.A5_09_4_IPHONE_CAT_ID);
		this.mFaceTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_04_1_IPHONE_FACE_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_2_IPHONE_FACE_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_3_IPHONE_FACE_ID,
				Vol3KishapoppoResouce.AllItem.A5_05_5_IPHONE_FACE_ID);
		this.mBallTiledTextureRegion[0] = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_04_4_1_IPHONE_BALLOON_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_4_2_IPHONE_BALLOON_ID);
		this.mBallTiledTextureRegion[1] = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_04_5_1_IPHONE_BALLOON_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_5_2_IPHONE_BALLOON_ID);
		this.mBallTiledTextureRegion[2] = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_04_6_1_IPHONE_BALLOON_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_6_2_IPHONE_BALLOON_ID);
		this.mBallTiledTextureRegion[3] = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_04_7_1_IPHONE_BALLOON_ID,
				Vol3KishapoppoResouce.AllItem.A5_04_7_2_IPHONE_BALLOON_ID);
		this.mSmokeATextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_05_6_1_IPHONE_SMOKE_ID);
		this.mSmokeBTextureRegion = mAllItemTexturePackLib
				.get(Vol3KishapoppoResouce.AllItem.A5_05_6_2_IPHONE_SMOKE_ID);

		this.mBoyTrainTiledTextureRegion = getTiledTextureFromPacker(
				mAllItemTexturePack,
				Vol3KishapoppoResouce.AllItem.A5_05_1_IPHONE_BOY_ID,
				Vol3KishapoppoResouce.AllItem.A5_05_2_IPHONE_BOY_ID,
				Vol3KishapoppoResouce.AllItem.A5_05_3_IPHONE_BOY_ID,
				Vol3KishapoppoResouce.AllItem.A5_05_4_IPHONE_BOY_ID);
		Log.d(TAG, "loadKaraokeResources.......................");

	}

	@Override
	protected void loadKaraokeSound() {
		oggPowa2 = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_04_POWA2);
		oggHusenpan3 = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_04_HUSENPAN3);
		oggNew = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_06_NEW);
		oggPaon = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_07_PAON);
		oggWai = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_08_WAI);
		oggNeko = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_09_NEKO5);
		oggKonkon = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_10_KONKON);
		oggBiyon = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_10_BIYON);
		oggPyu2 = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_11_PYU2);
		oggPaka = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_13_PAKA);
		oggPyu5 = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_15_PYU5);
		oggGao = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_16_GAO);
		oggPoto = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_17_POTO);
		oggGatanpopo = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_18_GATANPOPO);
		oggPopopuwa = loadSoundResourceFromSD(Vol3KishapoppoResouce.A5_05_POPOPUWA);
	}

	private void runAni(AnimatedSprite aniSprite) {
		aniSprite.animate(new long[] { 300, 300 }, new int[] { 0, 1 }, -1);
	}

	@Override
	public void combineGimmic3WithAction() {

		faceTouch();

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() != TouchEvent.ACTION_DOWN) {
			return true;
		}

		float pX = pSceneTouchEvent.getX();
		float pY = pSceneTouchEvent.getY();

		if (checkContains(mBoyTrainAniSprite, 20, 0, 160, 130, (int) pX,
				(int) pY)) {
			boyTrainAction();
		} else if (mFaceAnimatedSprite.contains(pX, pY)) {
			faceTouch();
		} else if (checkContains(mGirrafAnimatedSprite, 55, 165, 115, 285,
				(int) pX, (int) pY)) {
			if (isGirraf) {
				doubleAction(mGirrafAnimatedSprite);
			}
		} else if (checkContains(mElephantAnimatedSprite, 75, 145, 145, 205,
				(int) pX, (int) pY)) {
			if (isElephant) {
				doubleAction(mElephantAnimatedSprite);
			}
		} else if (checkContains(mGirlFrontAnimatedSprite, 45, 111, 105, 165,
				(int) pX, (int) pY)) {
			if (isGirlFront) {
				doubleAction(mGirlFrontAnimatedSprite);
			}
		} else if (checkContains(mCatAnimatedSprite, 36, 8, 86, 88, (int) pX,
				(int) pY)) {
			if (isCat) {
				doubleAction(mCatAnimatedSprite);
			}
		}
		if (checkContains(
				mParallaxSeconds,
				(int) mGirlAnimatedSecondSprite.getX(),
				(int) mGirlAnimatedSecondSprite.getY(),
				(int) (mGirlAnimatedSecondSprite.getX() + mGirlAnimatedSecondSprite
						.getWidth()), (int) ((int) mGirlAnimatedSecondSprite
						.getY() + mGirlAnimatedSecondSprite.getHeight()),
				(int) pX, (int) pY)) {

			if (isGirl) {
				doubleAction(mGirlAnimatedSecondSprite);
			}

		} else if (checkContains(mParallaxFirst,
				(int) mGirlAnimatedSprite.getX(),
				(int) mGirlAnimatedSprite.getY(),
				(int) (mGirlAnimatedSprite.getX() + mGirlAnimatedSprite
						.getWidth()),
				(int) ((int) mGirlAnimatedSprite.getY() + mGirlAnimatedSprite
						.getHeight()), (int) pX, (int) pY)) {

			if (isGirl) {
				doubleAction(mGirlAnimatedSprite);
			}

		} else if (checkContains(mParallaxFirst, (int) mHouseSprite.getX(),
				(int) mHouseSprite.getY(), (int) (mHouseSprite.getX() + 250),
				(int) ((int) mHouseSprite.getY() + mHouseSprite.getHeight()),
				(int) pX, (int) pY)) {
			boyJump(mBoyJumpAniSprite);
		} else if (checkContains(mParallaxSeconds,
				(int) mHouseSecondSprite.getX(),
				(int) mHouseSecondSprite.getY(),
				(int) (mHouseSecondSprite.getX() + 250),
				(int) ((int) mHouseSecondSprite.getY() + mHouseSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			boyJump(mBoyJumpSecondAniSprite);
		} else if (checkContains(
				mParallaxFirst,
				(int) mBigballoonAnimatedSprite.getX(),
				(int) mBigballoonAnimatedSprite.getY(),
				(int) (mBigballoonAnimatedSprite.getX() + 175),
				(int) ((int) mBigballoonAnimatedSprite.getY() + mBigballoonAnimatedSprite
						.getHeight()), (int) pX, (int) pY)) {
			bigBalloonAction(mBigballoonAnimatedSprite);
		} else if (checkContains(
				mParallaxSeconds,
				(int) mBigballoonAnimatedSecondSprite.getX(),
				(int) mBigballoonAnimatedSecondSprite.getY(),
				(int) (mBigballoonAnimatedSecondSprite.getX() + 175),
				(int) ((int) mBigballoonAnimatedSecondSprite.getY() + mBigballoonAnimatedSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			bigBalloonAction(mBigballoonAnimatedSecondSprite);
		} else if (checkContains(
				mParallaxFirst,
				(int) mHouse2AnimatedSprite.getX(),
				(int) mHouse2AnimatedSprite.getY(),
				(int) (mHouse2AnimatedSprite.getX() + 130),
				(int) ((int) mHouse2AnimatedSprite.getY() + mHouse2AnimatedSprite
						.getHeight()), (int) pX, (int) pY)) {
			house2Action(mHouse2AnimatedSprite);
		} else if (checkContains(
				mParallaxSeconds,
				(int) mHouse2AnimatedSecondSprite.getX(),
				(int) mHouse2AnimatedSecondSprite.getY(),
				(int) (mHouse2AnimatedSprite.getX() + 130),
				(int) ((int) mHouse2AnimatedSecondSprite.getY() + mHouse2AnimatedSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			house2Action(mHouse2AnimatedSecondSprite);
		} else if (checkContains(mParallaxFirst, (int) mTreeSprite.getX(),
				(int) mTreeSprite.getY(),
				(int) (mTreeSprite.getX() + mTreeSprite.getWidth()),
				(int) ((int) mTreeSprite.getY() + mTreeSprite.getHeight()),
				(int) pX, (int) pY)) {
			treeAction(mTree2Sprite);
		} else if (checkContains(
				mParallaxSeconds,
				(int) mTreeSecondSprite.getX(),
				(int) mTreeSecondSprite.getY(),
				(int) (mTreeSecondSprite.getX() + mTreeSecondSprite.getWidth()),
				(int) ((int) mTreeSecondSprite.getY() + mTreeSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			treeAction(mTree2SecondSprite);
		} else if (checkContains(
				mParallaxFirst,
				(int) mLionAniSprite.getX(),
				(int) mLionAniSprite.getY(),
				(int) (mLionAniSprite.getX() + mLionAniSprite.getWidth()),
				(int) ((int) mLionAniSprite.getY() + mLionAniSprite.getHeight()),
				(int) pX, (int) pY)) {
			lionAction(mLionAniSprite);
		} else if (checkContains(mParallaxSeconds,
				(int) mLionAniSecondSprite.getX(),
				(int) mLionAniSecondSprite.getY(),
				(int) (mLionAniSecondSprite.getX() + mLionAniSecondSprite
						.getWidth()),
				(int) ((int) mLionAniSecondSprite.getY() + mLionAniSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			lionAction(mLionAniSecondSprite);
		} else if (checkContains(mParallaxFirst, (int) mBridgeSprite.getX(),
				(int) mBridgeSprite.getY(),
				(int) (mBridgeSprite.getX() + mBridgeSprite.getWidth()),
				(int) ((int) mBridgeSprite.getY() + mBridgeSprite.getHeight()),
				(int) pX, (int) pY)) {
			trainAction(mTrainAnimatedSprite);
		} else if (checkContains(mParallaxSeconds,
				(int) mBridgeSecondSprite.getX(),
				(int) mBridgeSecondSprite.getY(),
				(int) (mBridgeSecondSprite.getX() + mBridgeSecondSprite
						.getWidth()),
				(int) ((int) mBridgeSecondSprite.getY() + mBridgeSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			trainAction(mTrainAnimatedSecondSprite);
		} else if (checkContains(mParallaxFirst, (int) mAppleTreeSprite.getX(),
				(int) mAppleTreeSprite.getY(),
				(int) (mAppleTreeSprite.getX() + mAppleTreeSprite.getWidth()),
				(int) ((int) mAppleTreeSprite.getY() + mAppleTreeSprite
						.getHeight()), (int) pX, (int) pY)) {
			appleAction(mAppleSprite);
		} else if (checkContains(
				mParallaxSeconds,
				(int) mAppleTreeSecondSprite.getX(),
				(int) mAppleTreeSecondSprite.getY(),
				(int) (mAppleTreeSecondSprite.getX() + mAppleTreeSecondSprite
						.getWidth()),
				(int) ((int) mAppleTreeSecondSprite.getY() + mAppleTreeSecondSprite
						.getHeight()), (int) pX, (int) pY)) {
			appleAction(mAppleSecondSprite);
		}
		return true;
	}

	@Override
	public void onPauseGame() {

		if (mScene != null) {
			mScene.unregisterUpdateHandler(autoParallax);
		}
		for (int i = 0; i < hashBall.size(); i++) {
			if (hashBall.get(i) != null) {
				if (hashBall.get(i).isVisible()) {
					hashBall.get(i).die();
				}
			}
		}
		hashBall.clear();
		mScene.clearUpdateHandlers();
		super.onPauseGame();
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		loadComplete();
	}

	// ===========================================================
	// Function and Class Action
	// ===========================================================

	private void boyTrainAction() {
		if (isBoyTrain) {
			isBoyTrain = false;
			oggPopopuwa.play();
			mBoyTrainAniSprite.animate(new long[] { 300, 300 }, new int[] { 2,
					3 }, 0, new IAnimationListener() {

				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					Log.d(TAG, "mBoyTrainAniSprite xxxxxx");
					runAni(mBoyTrainAniSprite);
					mFaceAnimatedSprite.animate(new long[] { 1000 },
							new int[] { 3 }, 0, new IAnimationListener() {

								@Override
								public void onAnimationFinished(
										AnimatedSprite arg0) {
									Log.d(TAG, "mFaceAnimatedSprite xxxxxx");
									runAni(mFaceAnimatedSprite);

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
					Log.d(TAG, "mSmokeBSprite xxxxxx");
					mSmokeBSprite.setVisible(true);
					mSmokeBSprite.resetLocalToFirst();
					mSmokeBSprite.reset();
					mSmokeBSprite.setBlendFunction(GL10.GL_SRC_ALPHA,
							GL10.GL_ONE_MINUS_SRC_ALPHA);
					mSmokeBSprite
							.registerEntityModifier(new ParallelEntityModifier(
									new MoveModifier(2.5f,
											mSmokeBSprite.getX(), mSmokeBSprite
													.getX() + 300,
											mSmokeBSprite.getY(),
											0 - mSmokeBSprite.getHeight()),
									new AlphaModifier(2.5f, 1.0f, 0.1f)));

					mSmokeASprite.setVisible(true);
					mSmokeASprite.resetLocalToFirst();
					mSmokeASprite.reset();
					// mSmokeASprite.setBlendFunction(GL10.GL_SRC_ALPHA,
					// GL10.GL_ONE_MINUS_SRC_ALPHA);
					mSmokeASprite.registerEntityModifier(new AlphaModifier(
							2.5f, 1.0f, 0.5f, new IEntityModifierListener() {

								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									runOnUpdateThread(new Runnable() {

										@Override
										public void run() {

											mSmokeBSprite
													.clearEntityModifiers();
											mSmokeBSprite.setVisible(false);

											mSmokeASprite
													.clearEntityModifiers();
											mSmokeASprite.setVisible(false);
											mSmokeASprite.setAlpha(1.0f);
											isBoyTrain = true;
										}
									});
								}

								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
								}
							}));

				}

				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				}

			});
		}
	}

	private void faceTouch() {
		if (isFace) {
			oggPowa2.play();

			int ran = new Random().nextInt(arrBall.length);
			int time = new Random().nextInt(10);
			if (time == 0) {
				time = 1;
			}
			final ballAnimatedSprite newBall = new ballAnimatedSprite(
					arrBall[ran][0], arrBall[ran][1],
					mBallTiledTextureRegion[ran].deepCopy(),
					this.getVertexBufferObjectManager());
			Vol3Kishapoppo.this.mScene.attachChild(newBall);
			Vol3Kishapoppo.this.mScene.registerTouchArea(newBall);
			hashBall.put(hashBall.size(), newBall);
			Log.d(TAG, "hashBall " + hashBall.size());
			newBall.registerEntityModifier(new MoveModifier(1.0f * time,
					newBall.getX(), newBall.getX() + (150 * time), newBall
							.getY(), 0 - newBall.getHeight(),
					new IEntityModifierListener() {

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							newBall.die();
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					}));

			mFaceAnimatedSprite.animate(new long[] { 500 }, new int[] { 2 }, 0,
					new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									isFace = true;
									runAni(mFaceAnimatedSprite);
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

	private class ballAnimatedSprite extends AnimatedSprite {

		private boolean isEnable = true;

		public ballAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertex) {
			super(pX, pY, pTiledTextureRegion, pVertex);

		}

		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);
		}

		public void die() {
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					ballAnimatedSprite.this.isEnable = false;
					ballAnimatedSprite.this.clearEntityModifiers();
					ballAnimatedSprite.this.setVisible(false);
					ballAnimatedSprite.this.setPosition(-99999, -9999);

					ballAnimatedSprite.this.detachSelf();
				}
			});

		}

		public void touch() {
			oggHusenpan3.play();
			this.animate(new long[] { 500 }, new int[] { 1 }, 0,
					new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							ballAnimatedSprite.this.die();
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

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {

			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN
					&& isEnable) {
				if (isEnable) {
					isEnable = false;
					touch();
				}
			}

			return true;
		}

	}

	private void appleAction(final Sprite pSprite[]) {
		if (isApple) {
			isApple = false;
			oggPoto.play();
			mScene.registerUpdateHandler(new TimerHandler(1.5f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							pSprite[0].clearEntityModifiers();
							pSprite[1].clearEntityModifiers();
							pSprite[2].clearEntityModifiers();
							pSprite[0].setVisible(false);
							pSprite[1].setVisible(false);
							pSprite[2].setVisible(false);

							mScene.registerUpdateHandler(new TimerHandler(0.5f,
									new ITimerCallback() {

										@Override
										public void onTimePassed(
												TimerHandler paramTimerHandler) {
											pSprite[0].resetLocalToFirst();
											pSprite[1].resetLocalToFirst();
											pSprite[2].resetLocalToFirst();
											pSprite[0].reset();
											pSprite[1].reset();
											pSprite[2].reset();
											isApple = true;
										}
									}));
						}
					}));

			pSprite[0].registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(1.0f, pSprite[0].getX(),
							pSprite[0].getX() - 25, pSprite[0].getY(),
							pSprite[0].getY() + 100)));
			pSprite[1].registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(1.0f, pSprite[1].getX(),
							pSprite[1].getX() - 0, pSprite[1].getY(),
							pSprite[1].getY() + 100)));
			pSprite[2].registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(1.0f, pSprite[2].getX(),
							pSprite[2].getX() + 15, pSprite[2].getY(),
							pSprite[2].getY() + 100)));
		}
	}

	private void trainAction(final AnimatedSprite pAni) {
		if (!pAni.isAnimationRunning()) {

			oggGatanpopo.play();

			pAni.animate(new long[] { 120, 120, 120, 120, 120, 120, 120, 120,
					120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120,
					120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120,
					120 }, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
					13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
					28, 29, 30, 31, 32, 33 }, 0, new IAnimationListener() {

				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					pAni.setCurrentTileIndex(0);
				}

				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				}

			});
		}
	}

	private void lionAction(final AnimatedSprite pAni) {
		if (!pAni.isAnimationRunning()) {
			oggGao.play();
			pAni.animate(new long[] { 600, 600 }, new int[] { 1, 2 }, 1,
					new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							pAni.setCurrentTileIndex(0);
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

	private void treeAction(final Sprite pAni) {
		if (isTree) {
			isTree = false;
			oggPyu2.play();

			pAni.registerEntityModifier(new MoveModifier(2.5f, pAni.getX(),
					pAni.getX() + 250, pAni.getY(), 0 - pAni.getY() * 2,
					new IEntityModifierListener() {

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									pAni.clearEntityModifiers();
									pAni.resetLocalToFirst();
									pAni.reset();
									isTree = true;
								}
							});
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					}));
		}
	}

	private void house2Action(final AnimatedSprite pAni) {
		if (!pAni.isAnimationRunning()) {

			oggPaka.play();
			pAni.animate(new long[] { 1500 }, new int[] { 1 }, 0,
					new IAnimationListener() {

						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							pAni.setCurrentTileIndex(0);
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

	private void bigBalloonAction(final AnimatedSprite pAnimatedSprite) {
		if (isBigballoon) {
			isBigballoon = false;
			pAnimatedSprite.setCurrentTileIndex(1);
			oggPyu2.play();
			pAnimatedSprite.registerEntityModifier(new MoveYModifier(2.5f,
					pAnimatedSprite.getY(), 0 - pAnimatedSprite.getHeight(),
					new IEntityModifierListener() {

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									pAnimatedSprite.clearEntityModifiers();
									isBigballoon = true;
									pAnimatedSprite.resetLocalToFirst();
									pAnimatedSprite.reset();
									pAnimatedSprite.setCurrentTileIndex(0);
								}
							});
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					}

			));
		}
	}

	private void boyJump(final AnimatedSprite pAnimatedSprite) {
		if (isBoyJump) {
			isBoyJump = false;
			oggKonkon.play();
			pAnimatedSprite.setVisible(true);
			pAnimatedSprite.resetLocalToFirst();
			pAnimatedSprite.reset();
			pAnimatedSprite.setCurrentTileIndex(0);
			Log.d(TAG, "boyJump");
			mScene.registerUpdateHandler(new TimerHandler(1.0f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							oggBiyon.play();
							pAnimatedSprite.animate(new long[] { 500, 500 },
									new int[] { 1, 2 }, 0,
									new IAnimationListener() {

										@Override
										public void onAnimationFinished(
												AnimatedSprite arg0) {
											pAnimatedSprite
													.registerEntityModifier(new MoveYModifier(
															2.0f,
															pAnimatedSprite
																	.getY(),
															0 - pAnimatedSprite
																	.getHeight(),
															new IEntityModifierListener() {

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> pModifier,
																		IEntity pItem) {
																	runOnUpdateThread(new Runnable() {

																		@Override
																		public void run() {
																			pAnimatedSprite
																					.setVisible(false);
																			pAnimatedSprite
																					.clearEntityModifiers();
																			isBoyJump = true;
																		}
																	});

																}

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																}
															}));

										}

										@Override
										public void onAnimationFrameChanged(
												AnimatedSprite arg0, int arg1,
												int arg2) {
										}

										@Override
										public void onAnimationLoopFinished(
												AnimatedSprite arg0, int arg1,
												int arg2) {
										}

										@Override
										public void onAnimationStarted(
												AnimatedSprite arg0, int arg1) {
										}

									});
						}
					}));

		}
	}

	private void doubleAction(final AnimatedSprite mAniSprite) {

		long duration[] = new long[] { 300, 300 };
		int frame[] = new int[] { 2, 3 };

		if (mAniSprite.equals(mGirlAnimatedSprite)
				|| mAniSprite.equals(mGirlAnimatedSecondSprite)) {
			isGirl = false;
			oggPyu5.play();
			duration = new long[] { 600, 600 };
		} else if (mAniSprite.equals(mGirrafAnimatedSprite)) {
			isGirraf = false;
			oggNew.play();
		} else if (mAniSprite.equals(mElephantAnimatedSprite)) {
			isElephant = false;
			oggPaon.play();
		} else if (mAniSprite.equals(mGirlFrontAnimatedSprite)) {
			isGirlFront = false;
			oggWai.play();
		} else if (mAniSprite.equals(mCatAnimatedSprite)) {
			isCat = false;
			oggNeko.play();
		}

		mAniSprite.animate(duration, frame, 1, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				mAniSprite.animate(new long[] { 500, 500 }, new int[] { 0, 1 },
						-1);

				if (mAniSprite.equals(mGirlAnimatedSprite)
						|| mAniSprite.equals(mGirlAnimatedSecondSprite)) {
					isGirl = true;
				} else if (mAniSprite.equals(mGirrafAnimatedSprite)) {
					isGirraf = true;
				} else if (mAniSprite.equals(mElephantAnimatedSprite)) {
					isElephant = true;
				} else if (mAniSprite.equals(mGirlFrontAnimatedSprite)) {
					isGirlFront = true;
				} else if (mAniSprite.equals(mCatAnimatedSprite)) {
					isCat = true;
				}
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
}
