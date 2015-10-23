package jp.co.xing.utaehon03.songs;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OhanashiResouce;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
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
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Ohanashi extends BaseGameFragment implements
		IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final String TAG = "LOG_VOL3OHANASHI";
	private boolean isTv = true;
	private boolean isClock = true;
	private boolean isDoor = true;
	private boolean isHand = true;
	private boolean isBabyCry = true;
	private boolean isUongSua = false;

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mBackgroundTexturePack;
	private TexturePack mHandSeeTexturePack;
	private TexturePack mCaiTroGiuaTexturePack;
	private TexturePack mMainTexturePack;
	private TexturePack mNhanUtTexturePack;
	private TexturePackTextureRegionLibrary mBackgroundTexturePackLibs;
	private TexturePackTextureRegionLibrary mMainTexturePackLibs;

	/** TextureRegion **/
	private TextureRegion mHaikeiTextureRegion;
	private TextureRegion mSofaTextureRegion;
	private TextureRegion mClockCandyLeftTextureRegion;
	private TextureRegion mClockCandyRightTextureRegion;
	private TextureRegion mHandDefaultTextureRegion;
	private TextureRegion mMilkTextureRegion;
	private TextureRegion mCryLeftTextureRegion;
	private TextureRegion mCryRightTextureRegion;

	/** TiledTextureRegion **/
	private TiledTextureRegion mKaigaTiledTextureRegion;
	private TiledTextureRegion mClockTiledTextureRegion;
	private TiledTextureRegion mTvTiledTextureRegion;
	private TiledTextureRegion mDoorTiledTextureRegion;
	private TiledTextureRegion mHandActionTiledTextureRegion;
	private TiledTextureRegion mFingerTiledTextureRegion[] = new TiledTextureRegion[5];

	/* Sprite */
	private Sprite mSofaSprite;
	private Sprite mClockCandyLeftSprite;
	private Sprite mClockCandyRightSprite;
	private Sprite mHandDefaultSprite;
	private Sprite mCryLeftSprite;
	private Sprite mCryRightSprite;
	private MilkSprite mMilkSprite;

	/* AnimatedSprite */
	private AnimatedSprite mTvAnimatedSprite;
	private AnimatedSprite mKaigaAnimatedSprite;
	private AnimatedSprite mClockAnimatedSprite;
	private AnimatedSprite mDoorAnimatedSprite;
	private AnimatedSprite mHandActionAniSprite;
	private FringerAnimatedSprite mTempFringerAnimatedSprite;
	private FringerAnimatedSprite mFingerAnimatedSprite[] = new FringerAnimatedSprite[5];

	private static Sound A19_03_08_JANKEN2, A19_04_SAKUTON2, A19_05_DOMO4,
			A19_05_TYAIMASU, A19_05_ZA, A19_05_ZAKU, A19_06_BO_NBOBO_N,
			A19_07_KONKON, A19_10_CHU, A19_10_POMI, A19_9B_5_NAKIGOE1,
			A19_9D_5_WARAIGOE1, A19_07_KEIKOKUON;

	private Sound SFringerFace[] = new Sound[Vol3OhanashiResouce.SFringerFace.length];
	private Sound SFringerBody[] = new Sound[Vol3OhanashiResouce.SFringerFace.length];
	private Sound SFringerFaceMove[] = new Sound[Vol3OhanashiResouce.SFringerFace.length];
	private Sound SFringerBodyMove[] = new Sound[Vol3OhanashiResouce.SFringerFace.length];

	private TimerHandler babyTimerHandler = null;

	private final int DoorFrame[][] = new int[][] { { 0, 2, 3 }, { 0, 4, 5 },
			{ 0, 6, 7, 8, 9, 10, 1 } };

	private final long DoorDuration[][] = new long[][] { { 1000, 300, 300 },
			{ 1000, 300, 300 }, { 1000, 200, 200, 200, 200, 200, 200 } };

	private final int FringerAction[][] = new int[][] { { 0 }, { 1 }, // tab
																		// bung
			{ 2 }, // tab mat
			{ 3 }, // xoa mat
			{ 4, 5 } // xoa bung
	};

	private final int FringerPointer[][] = new int[][] {
			{ 43, 142, 171, 241, 86, 241, 213, 341 },
			{ 71, 0, 185, 142, 86, 142, 199, 256 },
			{ 82, 29, 199, 156, 82, 156, 199, 298 },
			{ 86, 71, 185, 199, 86, 199, 185, 298 },
			{ 57, 43, 185, 171, 57, 171, 185, 241 } };

	final float LpX = 575;
	final float LpY = -100;

	final float RpX = 660;
	final float RpY = -95;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("ohanashi/mfx/"); // Sound Resources
		MusicFactory.setAssetBasePath("ohanashi/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("ohanashi/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "ohanashi/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		mBackgroundTexturePack = mTexturePackLoaderFromSource
				.load("background.xml");
		mHandSeeTexturePack = mTexturePackLoaderFromSource.load("handsee.xml");
		mCaiTroGiuaTexturePack = mTexturePackLoaderFromSource
				.load("caitrogiua.xml");
		mMainTexturePack = mTexturePackLoaderFromSource.load("main.xml");
		mNhanUtTexturePack = mTexturePackLoaderFromSource.load("nhanut.xml");

		mBackgroundTexturePack.loadTexture();
		mHandSeeTexturePack.loadTexture();
		mCaiTroGiuaTexturePack.loadTexture();
		mMainTexturePack.loadTexture();
		mNhanUtTexturePack.loadTexture();

		mBackgroundTexturePackLibs = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary();
		mMainTexturePackLibs = mMainTexturePack
				.getTexturePackTextureRegionLibrary();

		mHaikeiTextureRegion = mBackgroundTexturePackLibs
				.get(Vol3OhanashiResouce.background.A19_12_IPHONE_HAIKEI_ID);
		mKaigaTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack,
				Vol3OhanashiResouce.main.A19_04_1_IPHONE_KAIGA_ID,
				Vol3OhanashiResouce.main.A19_04_2_IPHONE_KAIGA_ID,
				Vol3OhanashiResouce.main.A19_04_3_IPHONE_KAIGA_ID,
				Vol3OhanashiResouce.main.A19_04_4_IPHONE_KAIGA_ID,
				Vol3OhanashiResouce.main.A19_04_5_IPHONE_KAIGA_ID,
				Vol3OhanashiResouce.main.A19_04_6_IPHONE_KAIGA_ID);
		mTvTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack,
				Vol3OhanashiResouce.main.A19_05_1_IPHONE_TV_ID,
				Vol3OhanashiResouce.main.A19_05_2_IPHONE_TV_ID,
				Vol3OhanashiResouce.main.A19_05_3_IPHONE_TV_ID,
				Vol3OhanashiResouce.main.A19_05_4_IPHONE_TV_ID,
				Vol3OhanashiResouce.main.A19_05_5_IPHONE_TV_ID,
				Vol3OhanashiResouce.main.A19_05_6_IPHONE_TV_ID);
		mClockTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack,
				Vol3OhanashiResouce.main.A19_06_1_IPHONE_CLOCK_ID,
				Vol3OhanashiResouce.main.A19_06_2_IPHONE_CLOCK_ID,
				Vol3OhanashiResouce.main.A19_06_3_IPHONE_CLOCK_ID,
				Vol3OhanashiResouce.main.A19_06_6_IPHONE_CLOCK_ID);
		mSofaTextureRegion = mBackgroundTexturePackLibs
				.get(Vol3OhanashiResouce.background.A19_11_IPHONE_SOFA_ID);
		mDoorTiledTextureRegion = getTiledTextureFromPacker(mMainTexturePack,
				Vol3OhanashiResouce.main.A19_07_IPHONE_DOOR_ID,
				Vol3OhanashiResouce.main.A19_07_3_6_IPHONE_OJISAN_ID,
				Vol3OhanashiResouce.main.A19_07_1_1_IPHONE_KUMA_ID,
				Vol3OhanashiResouce.main.A19_07_1_2_IPHONE_KUMA_ID,
				Vol3OhanashiResouce.main.A19_07_2_1_IPHONE_USAGI_ID,
				Vol3OhanashiResouce.main.A19_07_2_2_IPHONE_USAGI_ID,
				Vol3OhanashiResouce.main.A19_07_3_1_IPHONE_OJISAN_ID,
				Vol3OhanashiResouce.main.A19_07_3_2_IPHONE_OJISAN_ID,
				Vol3OhanashiResouce.main.A19_07_3_3_IPHONE_OJISAN_ID,
				Vol3OhanashiResouce.main.A19_07_3_4_IPHONE_OJISAN_ID,
				Vol3OhanashiResouce.main.A19_07_3_5_IPHONE_OJISAN_ID);
		mClockCandyLeftTextureRegion = mMainTexturePackLibs
				.get(Vol3OhanashiResouce.main.A19_06_5_IPHONE_CLOCK_ID);
		mClockCandyRightTextureRegion = mMainTexturePackLibs
				.get(Vol3OhanashiResouce.main.A19_06_4_IPHONE_CLOCK_ID);
		mHandActionTiledTextureRegion = getTiledTextureFromPacker(
				mHandSeeTexturePack,
				Vol3OhanashiResouce.handsee.A19_08_1_1_IPHONE_JANKEN_ID,
				Vol3OhanashiResouce.handsee.A19_08_1_2_IPHONE_JANKEN_ID,
				Vol3OhanashiResouce.handsee.A19_08_1_3_IPHONE_JANKEN_ID,
				Vol3OhanashiResouce.handsee.A19_08_2_1_IPHONE_RENDOU_ID,
				Vol3OhanashiResouce.handsee.A19_08_2_2_IPHONE_RENDOU_ID,
				Vol3OhanashiResouce.handsee.A19_08_2_3_IPHONE_RENDOU_ID);

		mHandDefaultTextureRegion = mNhanUtTexturePack
				.getTexturePackTextureRegionLibrary().get(
						Vol3OhanashiResouce.nhanut.A19_09_IPHONE_TENOHIRA_ID);

		mFingerTiledTextureRegion[0] = getTiledTextureFromPacker(
				mCaiTroGiuaTexturePack,
				Vol3OhanashiResouce.caitrogiua.A19_9A_1_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9B_1_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9C_1_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9D_1_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_1_1_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_1_2_IPHONE_ONAKANADERU_ID);
		mFingerTiledTextureRegion[1] = getTiledTextureFromPacker(
				mCaiTroGiuaTexturePack,
				Vol3OhanashiResouce.caitrogiua.A19_9A_2_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9B_2_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9C_2_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9D_2_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_2_1_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_2_2_IPHONE_ONAKANADERU_ID);
		mFingerTiledTextureRegion[2] = getTiledTextureFromPacker(
				mCaiTroGiuaTexturePack,
				Vol3OhanashiResouce.caitrogiua.A19_9A_3_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9B_3_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9C_3_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9D_3_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_3_1_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.caitrogiua.A19_9E_3_2_IPHONE_ONAKANADERU_ID);
		mFingerTiledTextureRegion[3] = getTiledTextureFromPacker(
				mNhanUtTexturePack,
				Vol3OhanashiResouce.nhanut.A19_9A_4_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9B_4_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9C_4_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9D_4_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9E_4_1_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.nhanut.A19_9E_4_2_IPHONE_ONAKANADERU_ID);
		mFingerTiledTextureRegion[4] = getTiledTextureFromPacker(
				mNhanUtTexturePack,
				Vol3OhanashiResouce.nhanut.A19_9A_5_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9B_5_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9C_5_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9D_5_IPHONE_DEFAULT_ID,
				Vol3OhanashiResouce.nhanut.A19_9E_5_1_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.nhanut.A19_9E_5_2_IPHONE_ONAKANADERU_ID,
				Vol3OhanashiResouce.nhanut.A19_10_1_IPHONE_BABY_ID,
				Vol3OhanashiResouce.nhanut.A19_10_2_1_IPHONE_BABY_ID,
				Vol3OhanashiResouce.nhanut.A19_10_3_IPHONE_BABY_ID,
				Vol3OhanashiResouce.nhanut.A19_10_4_IPHONE_BABY_ID);

		mMilkTextureRegion = mMainTexturePackLibs
				.get(Vol3OhanashiResouce.main.A19_10_2_2_IPHONE_BABY_ID);
		mCryLeftTextureRegion = mMainTexturePackLibs
				.get(Vol3OhanashiResouce.main.A19_10_2_3_IPHONE_BABY_ID);
		mCryRightTextureRegion = mMainTexturePackLibs
				.get(Vol3OhanashiResouce.main.A19_10_2_4_IPHONE_BABY_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		for (int i = 0; i < Vol3OhanashiResouce.SFringerFace.length; i++) {
			SFringerFace[i] = loadSoundResourceFromSD(Vol3OhanashiResouce.SFringerFace[i]);
			SFringerFaceMove[i] = loadSoundResourceFromSD(Vol3OhanashiResouce.SFringerFaceMove[i]);
			SFringerBody[i] = loadSoundResourceFromSD(Vol3OhanashiResouce.SFringerBody[i]);
			SFringerBodyMove[i] = loadSoundResourceFromSD(Vol3OhanashiResouce.SFringerBodyMove[i]);
		}

		A19_03_08_JANKEN2 = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_03_08_JANKEN2);
		A19_04_SAKUTON2 = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_04_SAKUTON2);
		A19_05_DOMO4 = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_05_DOMO4);
		A19_05_TYAIMASU = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_05_TYAIMASU);
		A19_05_ZA = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_05_ZA);
		A19_05_ZAKU = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_05_ZAKU);
		A19_06_BO_NBOBO_N = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_06_BO_NBOBO_N);
		A19_07_KONKON = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_07_KONKON);
		A19_10_CHU = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_10_CHU);
		A19_10_POMI = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_10_POMI);
		A19_9B_5_NAKIGOE1 = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_9B_5_NAKIGOE1);
		A19_9D_5_WARAIGOE1 = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_9D_5_WARAIGOE1);
		A19_07_KEIKOKUON = loadSoundResourceFromSD(Vol3OhanashiResouce.A19_07_KEIKOKUON);

		System.gc();
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				mHaikeiTextureRegion, this.getVertexBufferObjectManager())));

		mKaigaAnimatedSprite = new AnimatedSprite(162, 37,
				mKaigaTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mKaigaAnimatedSprite);

		mTvAnimatedSprite = new AnimatedSprite(56, 226, mTvTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mTvAnimatedSprite);

		mClockAnimatedSprite = new AnimatedSprite(616, 22,
				mClockTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mClockAnimatedSprite);
		mClockAnimatedSprite.animate(new long[] { 500, 500 },
				new int[] { 0, 1 }, -1);

		mClockCandyLeftSprite = new Sprite(575, -100,
				mClockCandyLeftTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mClockCandyLeftSprite);

		mDoorAnimatedSprite = new AnimatedSprite(696, 168,
				mDoorTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mDoorAnimatedSprite);
		mDoorAnimatedSprite.setCurrentTileIndex(0);

		mClockCandyRightSprite = new Sprite(660, -95,
				mClockCandyRightTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mClockCandyRightSprite);
		mClockCandyLeftSprite.setVisible(false);
		mClockCandyRightSprite.setVisible(false);

		mSofaSprite = new Sprite(0, 0, mSofaTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSofaSprite);

		mHandActionAniSprite = new AnimatedSprite(156, 71,
				mHandActionTiledTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHandActionAniSprite);
		mHandActionAniSprite.setVisible(false);

		mHandDefaultSprite = new Sprite(156, 71, mHandDefaultTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHandDefaultSprite);

		final int FingerPoint[][] = new int[][] { { 0, 114 }, { 57, 57 },
				{ 171, 0 }, { 284, 0 }, { 397, 114 } };

		for (int i = 0; i < 5; i++) {
			mFingerAnimatedSprite[i] = new FringerAnimatedSprite(
					156 + FingerPoint[i][0], 71 + FingerPoint[i][1],
					mFingerTiledTextureRegion[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mFingerAnimatedSprite[i]);
			this.mScene.registerTouchArea(mFingerAnimatedSprite[i]);
			mFingerAnimatedSprite[i].setCurrentTileIndex(0);
			mFingerAnimatedSprite[i].idIndex = i;
		}

		mCryLeftSprite = new Sprite(584, 310, mCryLeftTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCryLeftSprite);
		mCryLeftSprite.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		mCryLeftSprite.setVisible(false);

		mCryRightSprite = new Sprite(690, 335, mCryRightTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCryRightSprite);
		// mCryRightSprite.setRotation(-90);
		mCryRightSprite.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		mCryRightSprite.setVisible(false);

		mMilkSprite = new MilkSprite(mMilkTextureRegion,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mMilkSprite);
		this.mScene.registerTouchArea(mMilkSprite);
		mMilkSprite.setVisible(false);

		addGimmicsButton(mScene, Vol3OhanashiResouce.SOUND_GIMMIC, new int[] {
				Vol3OhanashiResouce.main.A19_OHANASHI_1_IPHONE_ID,
				Vol3OhanashiResouce.main.A19_OHANASHI_2_IPHONE_ID,
				Vol3OhanashiResouce.main.A19_OHANASHI_3_IPHONE_ID },
				mMainTexturePackLibs);

	}

	@Override
	public void combineGimmic3WithAction() {
		if (isHand) {
			jankenAction();
		}
	}

	@Override
	public void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();

			if (mKaigaAnimatedSprite.contains(pX, pY)) {
				nextTileAnimatedSprite(mKaigaAnimatedSprite);
				A19_04_SAKUTON2.play();
			} else if (mTvAnimatedSprite.contains(pX, pY) && isTv && isHand) {
				isHand = false;
				tvAction();
				babyDontCry();
				resetBaby();
			} else if (checkContains(mClockAnimatedSprite, 29, 0, 171, 171, pX,
					pY) && isClock) {
				clockAction();
			} else if (checkContains(mDoorAnimatedSprite, 144, 26, 218, 383,
					pX, pY) && isDoor && isHand) {
				isHand = false;
				doorAction();
				babyDontCry();
				resetBaby();
			} else if (checkContains(mHandDefaultSprite, 227, 312, 454, 426,
					pX, pY) && isHand) {
				isHand = false;
				jankenAction();
				resetBaby();
			}
		}
		if (pSceneTouchEvent.isActionUp()) {
			if (mTempFringerAnimatedSprite != null) {
				if (mTempFringerAnimatedSprite.isAnimationRunning()) {
					mTempFringerAnimatedSprite.stopAnimation(0);
					mTempFringerAnimatedSprite = null;
				}
			}
			Log.d(TAG, " upup ,,,,,,,,,,,,,,,,,,,");
		}
		return true;
	}

	@Override
	public void onPauseGame() {
		try {
			babyDontCry();
			this.mEngine.clearUpdateHandlers();
			this.mScene.clearUpdateHandlers();
			handDefault();
			mHandActionAniSprite.clearEntityModifiers();
			mTvAnimatedSprite.setCurrentTileIndex(0);
			mKaigaAnimatedSprite.setCurrentTileIndex(0);
			mDoorAnimatedSprite.setCurrentTileIndex(0);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		isTv = true;
		isClock = true;
		isDoor = true;
		isHand = true;
		isBabyCry = true;
		setTouchGimmic3(true);
		super.onPauseGame();
	}

	@Override
	public void onResumeGame() {
		Log.d(TAG, "onResumeGame");
		babyTimerHandler = new TimerHandler(12.0f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (isBabyCry) {
					babyCry();
				}
			}
		});
		isUongSua = false;
		this.mEngine.registerUpdateHandler(babyTimerHandler);
		super.onResumeGame();
	}

	// Function and Class

	class MilkSprite extends Sprite {

		private float PX = 700;
		private float PY = 379;

		public MilkSprite(TextureRegion pTextureRegion,
				VertexBufferObjectManager vVertex) {
			super(700, 379, pTextureRegion, vVertex);
		}

		private void rotateAction() {
			this.setRotationCenter(this.getWidth() / 2, this.getHeight());
			this.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(new RotationModifier(0.5f, -10,
							10), new RotationModifier(0.5f, 10, -10))));
		}

		public void showAction() {
			this.setVisible(true);
			this.rotateAction();
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (!this.isVisible()) {
				return true;
			}
			if (pSceneTouchEvent.isActionDown()) {
				this.clearEntityModifiers();
				A19_10_POMI.play();
			} else if (pSceneTouchEvent.isActionMove()) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2,
						pSceneTouchEvent.getY() - this.getHeight() / 2);
				if (checkContains(mFingerAnimatedSprite[4], 57, 43, 185, 171,
						(int) pSceneTouchEvent.getX(),
						(int) pSceneTouchEvent.getY())) {

					babyDring();
					this.setPosition(this.PX, this.PY);
					this.setVisible(false);
				}
			} else if (pSceneTouchEvent.isActionUp()) {
				this.setPosition(this.PX, this.PY);
				this.rotateAction();
			}
			return true;
		}

	}

	private void resetBaby() {
		babyTimerHandler.reset();
		isBabyCry = true;

	}

	private void babyCry() {
		isBabyCry = false;
		A19_9B_5_NAKIGOE1.play();
		mFingerAnimatedSprite[4].animate(new long[] { 500, 300 }, new int[] {
				6, 7 }, 0);
		mMilkSprite.showAction();

		this.mScene.registerUpdateHandler(new TimerHandler(0.5f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mCryLeftSprite.setVisible(true);
						mCryRightSprite.setVisible(true);
						mCryLeftSprite.setAlpha(1.0f);
						mCryRightSprite.setAlpha(1.0f);
					}
				}));

		final LoopEntityModifier loop = new LoopEntityModifier(
				new AlphaModifier(1.0f, 1.0f, 0.0f));
		mCryLeftSprite.registerEntityModifier(loop);
		mCryRightSprite.registerEntityModifier(loop.deepCopy());
	}

	private void babyDontCry() {
		mMilkSprite.setVisible(false);
		A19_9B_5_NAKIGOE1.stop();
		mCryLeftSprite.clearEntityModifiers();
		mCryRightSprite.clearEntityModifiers();
		mCryLeftSprite.setVisible(false);
		mCryRightSprite.setVisible(false);
		if (mFingerAnimatedSprite[4].isAnimationRunning()) {
			mFingerAnimatedSprite[4].stopAnimation();
		}
		mFingerAnimatedSprite[4].setCurrentTileIndex(0);
		resetBaby();
	}

	private void babyDring() {
		isHand = false;
		isUongSua = true;
		mMilkSprite.setVisible(false);
		mCryLeftSprite.setVisible(false);
		mCryRightSprite.setVisible(false);
		mCryLeftSprite.clearEntityModifiers();
		mCryRightSprite.clearEntityModifiers();
		A19_9B_5_NAKIGOE1.stop();
		A19_10_CHU.play();
		playSoundDelay(0.75f, A19_9D_5_WARAIGOE1);
		mFingerAnimatedSprite[4].animate(new long[] { 750, 1200 }, new int[] {
				8, 9 }, 0, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				isHand = true;
				isUongSua = false;
				resetBaby();
				mFingerAnimatedSprite[4].setCurrentTileIndex(0);
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void handDefault() {
		setVisibleHandDefault(true);
		mHandActionAniSprite.setVisible(false);
		isHand = true;
		resetBaby();
	}

	private void setVisibleHandDefault(boolean pVisible) {

		System.gc();
		mHandDefaultSprite.setVisible(pVisible);
		int i = 0;
		mFingerAnimatedSprite[i].setVisible(pVisible);
		i++;
		mFingerAnimatedSprite[i].setVisible(pVisible);
		i++;
		mFingerAnimatedSprite[i].setVisible(pVisible);
		i++;
		mFingerAnimatedSprite[i].setVisible(pVisible);
		i++;
		mFingerAnimatedSprite[i].setVisible(pVisible);
		System.gc();
	}

	private void handAction(int pAction) {
		setVisibleHandDefault(false);
		switch (pAction) {
		case 2: // Door Human
			mHandActionAniSprite.setCurrentTileIndex(3);
			mHandActionAniSprite.setVisible(true);
			break;
		case 1: // Door ani
		case 0:
			mHandActionAniSprite.setCurrentTileIndex(4);
			mHandActionAniSprite.setVisible(true);
			break;
		case 3:
			mHandActionAniSprite.setCurrentTileIndex(5);
			mHandActionAniSprite.setVisible(true);
			break;
		}
	}

	private void jankenAction() {
		babyDontCry();
		isHand = false;
		setTouchGimmic3(false);
		A19_03_08_JANKEN2.play();
		final TimerHandler mJankenTimerHandler = new TimerHandler(0.75f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						int rand = new Random().nextInt(3);
						setVisibleHandDefault(false);
						mHandActionAniSprite.setCurrentTileIndex(rand);
						mHandActionAniSprite.setVisible(true);
						mHandActionAniSprite.setRotationCenter(333, 567);
						mHandActionAniSprite
								.registerEntityModifier(new SequenceEntityModifier(
										new IEntityModifierListener() {

											@Override
											public void onModifierFinished(
													IModifier<IEntity> pModifier,
													IEntity pItem) {

												Log.d(TAG, " Ket thuc");
												handDefault();
												setTouchGimmic3(true);
											}

											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												// TODO Auto-generated method
												// stub

											}
										}, new RotationModifier(0.3f, 0, -10),
										new RotationModifier(0.6f, -10, 10),
										new RotationModifier(0.3f, 10, 0)));
					}
				});
		System.gc();
		this.mScene.registerUpdateHandler(mJankenTimerHandler);
	}

	private void doorAction() {
		final int ran = new Random().nextInt(DoorFrame.length);
		isDoor = false;
		A19_07_KONKON.play();
		this.mScene.registerUpdateHandler(new TimerHandler(1.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						handAction(ran);
						if (ran == 2) {
							A19_07_KEIKOKUON.play();
						}
					}
				}));
		mDoorAnimatedSprite.animate(DoorDuration[ran], DoorFrame[ran], 0,
				new IAnimationListener() {

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						mDoorAnimatedSprite.setCurrentTileIndex(0);
						isDoor = true;
						handDefault();
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void tvAction() {
		isTv = false;

		mTvAnimatedSprite.registerUpdateHandler(new TimerHandler(0.5f, true,
				new ITimerCallback() {
					int i = 1;

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if (i < mTvAnimatedSprite.getTileCount()) {
							mTvAnimatedSprite.setCurrentTileIndex(i);
							if (i == 1) {
								A19_05_ZA.play();
								setVisibleHandDefault(false);
								handAction(3);
							} else if (i == 2) {
								A19_05_DOMO4.play();
							} else if (i == 3) {
								A19_05_TYAIMASU.play();
							} else if (i == 4) {
								A19_05_ZAKU.play();
							}
							i++;
						} else {
							mTvAnimatedSprite.setCurrentTileIndex(0);
							mTvAnimatedSprite.clearUpdateHandlers();
							isTv = true;
							handDefault();
						}
					}
				}));
	}

	private void clockAction() {
		isClock = false;
		A19_06_BO_NBOBO_N.play();

		mClockAnimatedSprite.animate(new long[] { 550, 550 },
				new int[] { 2, 3 }, 1, new IAnimationListener() {

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						mClockAnimatedSprite.animate(new long[] { 500, 500 },
								new int[] { 0, 1 }, -1);
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
		mClockCandyLeftSprite.setVisible(true);
		mClockCandyRightSprite.setVisible(true);

		mClockCandyLeftSprite.registerEntityModifier(new MoveModifier(1.2f,
				LpX, LpX - 20, LpY, LpY + 500));

		mClockCandyRightSprite.registerEntityModifier(new MoveModifier(1.2f,
				RpX, RpX + 20, RpY, RpY + 500, new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {

								mClockCandyLeftSprite.clearEntityModifiers();
								mClockCandyLeftSprite.setPosition(LpX, LpY);
								mClockCandyLeftSprite.setVisible(false);

								mClockCandyRightSprite.clearEntityModifiers();
								mClockCandyLeftSprite.setPosition(RpX, RpY);
								mClockCandyRightSprite.reset();
								mClockCandyRightSprite.setVisible(false);
								isClock = true;

								Log.d("xxx", "" + mClockCandyLeftSprite.getY());

							}
						});

					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}
				}));

	}

	class FringerAnimatedSprite extends AnimatedSprite {

		public int idIndex;
		private int type = 0; // 1 : tab bung, 2: tab mat
		private boolean isMove = false;
		private float pXTouch = 0;
		private float pYTouch = 0;
		@SuppressWarnings("unused")
		private boolean isOK = true;

		public FringerAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertex) {
			super(pX, pY, pTiledTextureRegion, pVertex);
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {

			if (isUongSua) {
				return true;
			}

			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				if (this.idIndex == 4 && !isBabyCry) {
					break;
				}
				if (isHand) {
					this.isOK = true;
					this.isMove = false;
					if (this.isAnimationRunning()) {
						break;
					}
					if (checkContains(this, FringerPointer[this.idIndex][0],
							FringerPointer[this.idIndex][1],
							FringerPointer[this.idIndex][2],
							FringerPointer[this.idIndex][3], pX, pY)) {
						Log.d(TAG, "Touch Mat: " + this.idIndex);
						this.type = 2;
						SFringerFace[this.idIndex].play();
					} else if (checkContains(this,
							FringerPointer[this.idIndex][4],
							FringerPointer[this.idIndex][5],
							FringerPointer[this.idIndex][6],
							FringerPointer[this.idIndex][7], pX, pY)) {
						Log.d(TAG, "Touch Bung: " + this.idIndex);
						this.type = 1;
						SFringerBody[this.idIndex].play();
					}
					Log.d(TAG, "idIndex: " + this.idIndex);
					if (this.type > 0) {
						this.pXTouch = pSceneTouchEvent.getX();
						this.pYTouch = pSceneTouchEvent.getY();
						this.setCurrentTileIndex(FringerAction[this.type][0]);
						this.animate(new long[] { 1000, 100 }, new int[] {
								FringerAction[this.type][0], 0 }, 0);
						mTempFringerAnimatedSprite = this;
					}
				}
				break;
			case TouchEvent.ACTION_MOVE:
				if (this.idIndex == 4 && !isBabyCry) {
					break;
				}
				if (!this.isAnimationRunning()
						&& !this.isMove
						&& (pSceneTouchEvent.getX() + pSceneTouchEvent.getY())
								- (this.pXTouch + this.pYTouch) >= 10) {
					this.isMove = true;
					if (this.type == 2) {
						this.type = 3;
						this.setCurrentTileIndex(FringerAction[this.type][0]);
						SFringerFaceMove[this.idIndex].play();
					} else if (this.type == 1) {
						this.type = 4;
						this.animate(new long[] { 300, 300 }, new int[] {
								FringerAction[this.type][0],
								FringerAction[this.type][1] }, -1);
						SFringerBodyMove[this.idIndex].play();
					}
				}

				if (this.isMove) {
					if (this.type == 3) {
						if (!checkContains(this,
								FringerPointer[this.idIndex][0],
								FringerPointer[this.idIndex][1],
								FringerPointer[this.idIndex][2],
								FringerPointer[this.idIndex][3], pX, pY)) {
							this.isOK = false;
							if (this.isAnimationRunning()) {
								this.stopAnimation();
							}
							Log.d(TAG, "Ngoai mat");
							this.setCurrentTileIndex(0);
						}
					} else if (this.type == 4) {
						if (!checkContains(this,
								FringerPointer[this.idIndex][4],
								FringerPointer[this.idIndex][5],
								FringerPointer[this.idIndex][6],
								FringerPointer[this.idIndex][7], pX, pY)) {
							this.isOK = false;
							if (this.isAnimationRunning()) {
								this.stopAnimation();
							}
							Log.d(TAG, "Ngoai bung");
							this.setCurrentTileIndex(0);

						}
					}
				}

				break;
			case TouchEvent.ACTION_UP:
				if (this.idIndex == 4 && !isBabyCry) {
					break;
				}
				this.type = 0;
				Log.d(TAG, "ACTION_UP: " + this.isMove + "/ " + this.idIndex);

				if (this.isMove) {
					this.setCurrentTileIndex(FringerAction[this.type][0]);
				}
				if (this.isAnimationRunning()) {
					this.stopAnimation(0);
				}
				this.isOK = true;
				break;
			}
			return false;
		}

	}

	/**
	 * playSoundDelay
	 * 
	 * @param time
	 *            float
	 * @param pSound
	 *            Sound
	 */
	protected void playSoundDelay(final float time, final Sound pSound) {
		final TimerHandler mTimerHandlerDelay = new TimerHandler(time,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						pSound.play();
					}
				});

		this.mScene.registerUpdateHandler(mTimerHandlerDelay);
	}

}
