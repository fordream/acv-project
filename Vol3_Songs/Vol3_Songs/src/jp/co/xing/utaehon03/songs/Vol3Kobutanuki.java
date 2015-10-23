package jp.co.xing.utaehon03.songs;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KobutanukiResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Kobutanuki extends BaseGameActivity implements
		IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_VOL3KOBUTANKI";
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack Vol3KobutanukiPacker_1, Vol3KobutanukiPacker_2, Vol3KobutanukiPacker_3, Vol3KobutanukiPacker_4, Vol3KobutanukiPacker_5,
	Vol3KobutanukiPacker_6, Vol3KobutanukiPacker_7, Vol3KobutanukiPacker_8, Vol3KobutanukiPacker_9, Vol3KobutanukiPacker_10;
	private TexturePackTextureRegionLibrary   Vol3KobutanukiLibrary6, Vol3KobutanukiLibrary7;
	
	private static int KOBUTA = 0, TANUKI = 1, KITUNE = 2, NEKO = 3;
	private static int LAST_RANDOM = -1;
	private static int ID4ANI = 0;
	private static int ISU = 0;
	private static boolean isTOUCH = true;

	/** TextureRegion **/
	private ITextureRegion backgroundTextureRegion;
	private ITextureRegion mIsuTextureRegion;

	/** TiledTextureRegion **/
	// private TiledTextureRegion
	private TiledTextureRegion m4BAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion m4EAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion m5AAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion m6AAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion m6BAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion m6CAniTiledTextureRegion[] = new TiledTextureRegion[4];
	private TiledTextureRegion mCurtainTiledTextureRegion;
	private TiledTextureRegion m6DTiledTextureRegion;

	private AnimatedSprite mCurtainAniSprite;
	private Sprite m4ShowSprite;
	private AnimatedSprite mWayAniSprite;
	private AnimatedSprite mRunAniSprite;
	private AnimatedSprite mEndAniSprite;
	private ChangeAnimatedSprite mTempAniSprite;

	private Sprite mIsuSprite[] = new Sprite[10];
	private AnimatedSprite mIsuAniSprite[] = new AnimatedSprite[10];
	private AnimatedSprite m5AAniSprite[] = new AnimatedSprite[4];
	private RanDomNoRepeat randomnorepeat;

	private final int m5APointY[][] = new int[][] { { TANUKI, 112 },
			{ KITUNE, 235 }, { KOBUTA, 360 }, { NEKO, 484 } };

	private static final int B = 1, E = 2;

	private static final int arr4Ani[][] = new int[][] {
			// /{KOBUTA,TANUKI,KITUNE,NEKO}
			{ KOBUTA, B, 0, 0, 1, 35, 173 },
			{ TANUKI, B, 1, 0, 1, 35, 173 },
			{ KITUNE, B, 2, 0, 1, 35, 173 },
			{ NEKO, B, 3, 0, 1, 35, 173 },
			{ KOBUTA, E, 0, 0, 1, 137, 251 }, // E
			{ KOBUTA, E, 0, 2, 3, 146, 251 }, // F
			{ KOBUTA, E, 0, 4, 5, 137, 251 }, // G
			{ TANUKI, E, 1, 0, 1, 137, 251 }, { TANUKI, E, 1, 2, 3, 146, 251 },
			{ TANUKI, E, 1, 4, 5, 137, 251 },
			{ KITUNE, E, 2, 0, 1, 137, 251 },
			{ KITUNE, E, 2, 2, 3, 146, 251 }, // F
			{ KITUNE, E, 2, 4, 5, 137, 251 }, { NEKO, E, 3, 0, 1, 137, 251 },
			{ NEKO, E, 3, 2, 3, 146, 251 }, // F
			{ NEKO, E, 3, 4, 5, 137, 251 },

	};

	private static final int arr4AniWay[][] = new int[][] {
			{ KOBUTA, 0, 6, 7 }, { TANUKI, 1, 6, 7 }, { KITUNE, 2, 6, 7 },
			{ NEKO, 3, 6, 7 } };

	private static Sound oggEANI[] = new Sound[4];
	private static Sound oggAANI[] = new Sound[4];
	private static Sound oggTRUE, oggFALSE, oggUPISU, oggEnd1, oggEnd2;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("kobutanuki/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("kobutanuki/gfx/"); // Image Resources
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		Vol3KobutanukiPacker_1 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker1.xml");
		Vol3KobutanukiPacker_1.loadTexture();
		Vol3KobutanukiPacker_2 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker2.xml");
		Vol3KobutanukiPacker_2.loadTexture();
		
		Vol3KobutanukiPacker_3 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker3.xml");
		Vol3KobutanukiPacker_3.loadTexture();
		
		Vol3KobutanukiPacker_4 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker4.xml");
		Vol3KobutanukiPacker_4.loadTexture();
		
		Vol3KobutanukiPacker_5 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker5.xml");
		Vol3KobutanukiPacker_5.loadTexture();
		
		Vol3KobutanukiPacker_6 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker6.xml");
		Vol3KobutanukiPacker_6.loadTexture();
		Vol3KobutanukiLibrary6 = Vol3KobutanukiPacker_6
		.getTexturePackTextureRegionLibrary();
		
		Vol3KobutanukiPacker_7 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker7.xml");
		Vol3KobutanukiPacker_7.loadTexture();
		Vol3KobutanukiLibrary7 = Vol3KobutanukiPacker_7
		.getTexturePackTextureRegionLibrary();
		
		Vol3KobutanukiPacker_8 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker8.xml");
		Vol3KobutanukiPacker_8.loadTexture();
		
		Vol3KobutanukiPacker_9 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker9.xml");
		Vol3KobutanukiPacker_9.loadTexture();
		
		Vol3KobutanukiPacker_10 = mTexturePackLoaderFromSource
		.load("Vol3KobutanukiPacker10.xml");
		Vol3KobutanukiPacker_10.loadTexture();

		this.backgroundTextureRegion = Vol3KobutanukiLibrary7.get(
						Vol3KobutanukiResouce.Vol3KobutanukiPacker7.A13_7_IPHONE_HAIKEI_ID);

		for (int i = 0; i < 4; i++) {
			final TexturePack tmpTexturePackGroup1;
			final TexturePack tmpTexturePackGroup2;
			switch (i) {
			case 0:
				tmpTexturePackGroup1 = Vol3KobutanukiPacker_2;
				tmpTexturePackGroup2 = Vol3KobutanukiPacker_7;
				break;
			case 1:
				tmpTexturePackGroup1 = Vol3KobutanukiPacker_3;
				tmpTexturePackGroup2 = Vol3KobutanukiPacker_8;
				break;
			case 2:
				tmpTexturePackGroup1 = Vol3KobutanukiPacker_4;
				tmpTexturePackGroup2 = Vol3KobutanukiPacker_9;
				break;
			case 3:
				tmpTexturePackGroup1 = Vol3KobutanukiPacker_5;
				tmpTexturePackGroup2 = Vol3KobutanukiPacker_10;
				break;
			default:
				tmpTexturePackGroup1 = null;
				tmpTexturePackGroup2 = null;
				break;
			}
			this.m4BAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					Vol3KobutanukiPacker_1, Vol3KobutanukiResouce.IArr4BAni[i]);
			this.m4EAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					tmpTexturePackGroup1, Vol3KobutanukiResouce.IArr4EAni[i]);
			this.m5AAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					Vol3KobutanukiPacker_6, Vol3KobutanukiResouce.IArr5AAni[i]);
			this.m6AAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					tmpTexturePackGroup2, Vol3KobutanukiResouce.IArr6AAni[i]);
			this.m6BAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					Vol3KobutanukiPacker_6, Vol3KobutanukiResouce.IArr6BAni[i]);
			this.m6CAniTiledTextureRegion[i] = getTiledTextureFromPacker(
					Vol3KobutanukiPacker_6, Vol3KobutanukiResouce.IArr6CAni[i]);
		}

		this.mCurtainTiledTextureRegion = getTiledTextureFromPacker(
				Vol3KobutanukiPacker_10,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker10.A13_04_1_IPHONE_CURTAIN_ID,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker10.A13_04_2_IPHONE_CURTAIN_ID);

		this.mIsuTextureRegion = Vol3KobutanukiLibrary6.get(
						Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_6_IPHONE_ISU_ID);
		this.m6DTiledTextureRegion = getTiledTextureFromPacker(
				Vol3KobutanukiPacker_6,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_6D_1_IPHONE_OMEDETO_ID,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_6D_2_IPHONE_OMEDETO_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		for (int i = 0; i < oggAANI.length; i++) {
			oggAANI[i] = loadSoundResourceFromSD(Vol3KobutanukiResouce.arrSound6AAni[i]);
			oggEANI[i] = loadSoundResourceFromSD(Vol3KobutanukiResouce.arrSound4EAni[i]);
		}

		oggTRUE = loadSoundResourceFromSD(Vol3KobutanukiResouce.A13_05_PINPON2);
		oggFALSE = loadSoundResourceFromSD(Vol3KobutanukiResouce.A13_05_TIGAUYO);
		oggUPISU = loadSoundResourceFromSD(Vol3KobutanukiResouce.A13_06_TOKOKATAN);
		oggEnd1 = loadSoundResourceFromSD(Vol3KobutanukiResouce.A13_D_OMEDETO2);
		oggEnd2 = loadSoundResourceFromSD(Vol3KobutanukiResouce.A13_D_PATIHUE);
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.backgroundTextureRegion, this
						.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

		int x = 684;
		for (int i = 0; i <= 9; i++) {
			mIsuSprite[i] = new Sprite(x, 32, mIsuTextureRegion,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mIsuSprite[i]);
			x = x - 58;
		}

		m4ShowSprite = new Sprite(0, 0, new TextureRegion(
				new BitmapTextureAtlas(getTextureManager(), 2, 2,
						TextureOptions.BILINEAR_PREMULTIPLYALPHA), 0, 0, 960,
				640), this.getVertexBufferObjectManager());
		this.mScene.attachChild(m4ShowSprite);
		m4ShowSprite.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		m4ShowSprite.setAlpha(0.0f);

		mCurtainAniSprite = new AnimatedSprite(-47, 128,
				mCurtainTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCurtainAniSprite);

		for (int i = 0; i < m5APointY.length; i++) {
			m5AAniSprite[i] = new AnimatedSprite(743, m5APointY[i][1],
					m5AAniTiledTextureRegion[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(m5AAniSprite[i]);
		}

		addGimmicsButton(mScene, Vol3KobutanukiResouce.SOUND_GIMMIC, new int[] {
				Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_KOBUTANUKITUNEKO_1_IPHONE_ID,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_KOBUTANUKITUNEKO_2_IPHONE_ID,
				Vol3KobutanukiResouce.Vol3KobutanukiPacker6.A13_KOBUTANUKITUNEKO_3_IPHONE_ID },
				Vol3KobutanukiLibrary6);
		Log.d(TAG, " onLoadScene ");
	}

	@Override
	public void loadKaraokeComplete() {
		super.loadKaraokeComplete();

		Log.d(TAG, "xxxxxxxxx loadKaraokeComplete xxxxxxxxxxxxxxxx");
	}

	@Override
	public void combineGimmic3WithAction() {
		oggEANI[randomnorepeat.getNextIntNoRepeatLast()].play();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		float pX = pSceneTouchEvent.getX();
		float pY = pSceneTouchEvent.getY();
		if (!isTOUCH) {
			return true;
		}
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			for (int i = 0; i < m5APointY.length; i++) {
				if (m5AAniSprite[i].contains(pX, pY)
						&& !m5AAniSprite[i].isAnimationRunning()) {
					choiseAction(m5APointY[i][0]);
					break;
				}

			}
			if (checkContains(mCurtainAniSprite, 0, 35, 564, 435, (int) pX,
					(int) pY) && mCurtainAniSprite.isVisible()) {
				mTempAniSprite.touchMe();
			}
		}

		return true;
	}

	@Override
	public synchronized void onResumeGame() {
		randomnorepeat = new RanDomNoRepeat();
		randomnorepeat.setLenghNoRepeat(m5APointY.length);

		createShow();
		showHidenchoise(true);
		Log.d(TAG, "onResumeGame");
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		this.mEngine.clearUpdateHandlers();
		LAST_RANDOM = -1;
		ID4ANI = 0;
		ISU = 0;
		isTOUCH = true;
		clearIsu();
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				if (mWayAniSprite != null) {
					mWayAniSprite.detachSelf();
					mWayAniSprite = null;
				}
				if (mRunAniSprite != null) {
					mRunAniSprite.detachSelf();
					mRunAniSprite = null;
				}
				if (mEndAniSprite != null) {
					mEndAniSprite.detachSelf();
					mEndAniSprite = null;
				}
				if (mTempAniSprite != null) {
					mTempAniSprite.clear();
				}

			}
		});
		
		for(int i = 0; i < m5AAniSprite.length; i++){
			m5AAniSprite[i].setCurrentTileIndex(0);
		}

		super.onPauseGame();
	}

	private void setVisibleIsu() {
		for (int i = 0; i < mIsuSprite.length; i++) {
			mIsuSprite[i].setVisible(false);
			mIsuAniSprite[i].setVisible(false);
		}
	}

	// ===========================================================
	// Function and Class Action
	// ===========================================================

	/*
	 * private void bringtoFront(){ //sprGimmic[0]. }
	 */

	private void showHidenchoise(boolean pVisible) {
		for (int i = 0; i < m5AAniSprite.length; i++) {
			m5AAniSprite[i].setVisible(pVisible);
		}
	}

	private void clearIsu() {
		for (int i = 0; i < mIsuAniSprite.length; i++) {
			final int id = i;
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					mIsuSprite[id].setVisible(true);
					if (mIsuAniSprite[id] != null) {
						mIsuAniSprite[id].stopAnimation();
						mIsuAniSprite[id].detachSelf();
						mIsuAniSprite[id].setVisible(false);
					}
				}
			});
			ISU = 0;
		}
	}

	private void isWin() {

		isTOUCH = false;

		// way
		// dis show + temp
		mCurtainAniSprite.setVisible(false);
		mTempAniSprite.clear();
		mTempAniSprite = null;

		int id = 0;
		for (int i = 0; i < arr4AniWay.length; i++) {
			if (arr4AniWay[i][0] == arr4Ani[ID4ANI][0]) {
				id = i;
				break;
			}
		}
		mWayAniSprite = new AnimatedSprite(0, 250,
				m4EAniTiledTextureRegion[arr4AniWay[id][1]],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mWayAniSprite);
		Log.d(TAG, "mWayAniSprite: " + mWayAniSprite.isVisible());
		mWayAniSprite.animate(new long[] { 350, 350 }, new int[] {
				arr4AniWay[id][2], arr4AniWay[id][3] }, -1);
		mWayAniSprite.registerEntityModifier(new MoveXModifier(1.5f,
				mWayAniSprite.getX(), (CAMERA_WIDTH / 2)
						+ mWayAniSprite.getWidth() / 2
						- mWayAniSprite.getWidth(),
				new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						mWayAniSprite.stopAnimation();
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								mWayAniSprite.detachSelf();
								mWayAniSprite.setVisible(false);
								mWayAniSprite = null;
								oggAANI[arr4Ani[ID4ANI][0]].play();
								mWayAniSprite = new AnimatedSprite(
										86,
										24,
										m6AAniTiledTextureRegion[arr4Ani[ID4ANI][0]],
										Vol3Kobutanuki.this
												.getVertexBufferObjectManager());
								Vol3Kobutanuki.this.mScene
										.attachChild(mWayAniSprite);
								setGimmicBringToFront();
								Log.d(TAG,
										"mWayAniSprite: "
												+ mWayAniSprite.isVisible());
								mWayAniSprite.animate(new long[] { 600, 650 },
										new int[] { 0, 1 }, 0,
										new IAnimationListener() {

											@Override
											public void onAnimationStarted(
													AnimatedSprite paramAnimatedSprite,
													int paramInt) {
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
												mWayAniSprite.detachSelf();
												mWayAniSprite.setVisible(false);
												mWayAniSprite = null;
												runToIsu();
											}
										});
							}
						});
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}
				}));

	}

	private void runToIsu() {
		mRunAniSprite = new AnimatedSprite(449, 345,
				m6BAniTiledTextureRegion[arr4Ani[ID4ANI][0]],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mRunAniSprite);
		oggUPISU.play();
		mRunAniSprite.animate(new long[] { 300, 300 }, new int[] { 0, 1 }, -1);
		mRunAniSprite.registerEntityModifier(new MoveModifier(1.5f,
				mRunAniSprite.getX(), mIsuSprite[ISU].getX(), mRunAniSprite
						.getY(), mIsuSprite[ISU].getY(),
				new IEntityModifierListener() {

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								mRunAniSprite.stopAnimation();
								mRunAniSprite.detachSelf();
								mRunAniSprite.setVisible(false);
								mRunAniSprite = null;
							}
						});

						float pX = ISU * 58;
						mIsuAniSprite[ISU] = new AnimatedSprite(684 - (pX), 32,
								m6CAniTiledTextureRegion[arr4Ani[ID4ANI][0]],
								Vol3Kobutanuki.this
										.getVertexBufferObjectManager());
						mIsuAniSprite[ISU].animate(new long[] { 400, 400 },
								new int[] { 0, 1 }, -1);
						Vol3Kobutanuki.this.mScene
								.attachChild(mIsuAniSprite[ISU]);
						getNewAni();
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}
				}));

	}

	private void setChoiseTrue() {

		for (int i = 0; i < m5AAniSprite.length; i++) {
			m5AAniSprite[i].animate(new long[] { 1000, 100 },
					new int[] { 1, 0 }, 0);
		}

	}

	private void choiseAction(int pANI) {
		Log.d(TAG, "Choise: " + pANI + " /Status: " + arr4Ani[ID4ANI][0]);
		if (pANI == arr4Ani[ID4ANI][0]) {
			// OK
			setChoiseTrue();
			oggTRUE.play();
			isWin();
			Log.d(TAG, " Doan dung ! ");
		} else {
			Log.d(TAG, " Doan Sai ");
			oggFALSE.play();
			for (int i = 0; i < m5APointY.length; i++) {
				m5AAniSprite[i].animate(new long[] { 1000, 50 }, new int[] { 2,
						0 }, 0);
			}
		}
	}

	private void getNewAni() {
		ISU++;
		if (ISU == 10) {

			TimerHandler timerhandler = new TimerHandler(0.8f, false,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {

							showHidenchoise(false);
							oggEnd1.play();
							oggEnd2.play();
							setVisibleIsu();
							mEndAniSprite = new AnimatedSprite(-30, -57,
									m6DTiledTextureRegion,
									getVertexBufferObjectManager());
							Vol3Kobutanuki.this.mScene
									.attachChild(mEndAniSprite);
							setGimmicBringToFront();
							mEndAniSprite.animate(new long[] { 500, 500 },
									new int[] { 0, 1 }, 3,
									new IAnimationListener() {

										@Override
										public void onAnimationStarted(
												AnimatedSprite paramAnimatedSprite,
												int paramInt) {
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
											runOnUpdateThread(new Runnable() {

												@Override
												public void run() {
													mEndAniSprite.detachSelf();
													mEndAniSprite
															.setVisible(false);
													mEndAniSprite = null;
												}
											});
											clearIsu();
											isTOUCH = true;
											showHidenchoise(true);
											createShow();
										}
									});
						}
					});

			this.mEngine.registerUpdateHandler(timerhandler);

		} else {
			isTOUCH = true;
			createShow();
		}
	}

	private void createShow() {
		ID4ANI = RandomArray(arr4Ani.length);
		Log.d(TAG, "ID4ANI:" + ID4ANI + " TYPE: " + arr4Ani[ID4ANI][1]);

		if (mTempAniSprite != null) {
			mTempAniSprite.clear();
		}
		if (arr4Ani[ID4ANI][1] == B) {

			mCurtainAniSprite.setCurrentTileIndex(1);
			// {KOBUTA, B, 0, 0, 1, 35, 173},
			mTempAniSprite = new ChangeAnimatedSprite(arr4Ani[ID4ANI][5],
					arr4Ani[ID4ANI][6],
					m4BAniTiledTextureRegion[arr4Ani[ID4ANI][2]],
					this.getVertexBufferObjectManager());
			// mTempAniSprite = new ChangeAnimatedSprite(0, 0,
			// m4BAniTiledTextureRegion[arr4Ani[ID4ANI][2]]);
			mTempAniSprite.mID = ID4ANI;
			mTempAniSprite.show(new int[] { arr4Ani[ID4ANI][3],
					arr4Ani[ID4ANI][4] });

		} else {

			mCurtainAniSprite.setCurrentTileIndex(0);
			mTempAniSprite = new ChangeAnimatedSprite(arr4Ani[ID4ANI][5],
					arr4Ani[ID4ANI][6],
					m4EAniTiledTextureRegion[arr4Ani[ID4ANI][2]],
					this.getVertexBufferObjectManager());
			mTempAniSprite.mID = ID4ANI;
			mTempAniSprite.show(new int[] { arr4Ani[ID4ANI][3],
					arr4Ani[ID4ANI][4] });

		}
		m4ShowSprite.attachChild(mTempAniSprite);
		Log.d(TAG, mTempAniSprite.isVisible() + "/ " + mTempAniSprite);
		mCurtainAniSprite.setVisible(true);

	}

	private int RandomArray(int arrlenght) {
		Log.d(TAG, "LAST_RANDOM: " + LAST_RANDOM);
		int ran = new Random().nextInt(arrlenght);
		if (arr4Ani[ran][0] == LAST_RANDOM) {
			ran = RandomArray(arrlenght);
			// ran = new Random().nextInt(arrlenght);
		}
		LAST_RANDOM = arr4Ani[ran][0];
		return ran;
	}

	private class ChangeAnimatedSprite extends AnimatedSprite {
		public int mID = 0;
		private int frame[] = new int[] {};

		public ChangeAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertex) {
			super(pX, pY, pTiledTextureRegion, pVertex);
			this.setVisible(false);
		}

		public void clear() {
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					ChangeAnimatedSprite.this.clearEntityModifiers();
					ChangeAnimatedSprite.this.setVisible(false);
				}
			});
		}

		public void show(int pFrame[]) {
			this.frame = pFrame;
			this.setCurrentTileIndex(this.frame[0]);
			this.setVisible(true);

		}

		public void touchMe() {
			oggEANI[arr4Ani[this.mID][0]].play();
			if (!this.isAnimationRunning())
				if (this.getCurrentTileIndex() == this.frame[0]) {
					this.setCurrentTileIndex(this.frame[1]);
				} else {
					this.setCurrentTileIndex(this.frame[0]);
				}

		}

	}

}
