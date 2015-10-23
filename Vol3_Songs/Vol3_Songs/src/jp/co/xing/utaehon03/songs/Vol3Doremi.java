package jp.co.xing.utaehon03.songs;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3DoremiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.util.Log;

@SuppressWarnings("rawtypes")
public class Vol3Doremi extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int CAMERA_WIDTH = 960;
	private static final int CAMERA_HEIGHT = 640;
	private static final String TAG = "LOG_DOREMI";
	private static final int MAJO_RIGHT_TO_LEFT = 0;
	private static final int MAJO_LEFT_TO_RIGHT = 1;
	private static final int TOP_SE = 20;
	private static final int SE_5 = 4;
	private static final int TOP_BLACK_KEY = 412;

	// ===========================================================
	// Fields
	// ===========================================================

	/**
	 * Texture, TextureRegion, TiledTextureRegion, Sprite, AnimatedSprite
	 */
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource; 
	private TexturePack mBackgroundTexturePack;
    private TexturePack mItemTexturePack;
    
    private TexturePackTextureRegionLibrary mBackgroundTexturePackTextureRegionLibrary;
    private TexturePackTextureRegionLibrary mItemexturePackTextureRegionLibrary;
    
	private ITextureRegion ttrRBackground2, ttrRMajoStar, ttrRStar1, ttrRStar2,
			ttrRBoundStar1, ttrRBoundStar2, ttrRMoon,
			arrTtrRCandy[] = new TextureRegion[9],
			arrTtrRMini[] = new TextureRegion[8],
			arrTtrRBig[] = new TextureRegion[8];

	private TiledTextureRegion ttrRBackground, tiledTtrMajoPack,
			arrTiledTtrRSe[] = new TiledTextureRegion[5],
			arrTiledTtrRBlackKey[] = new TiledTextureRegion[6],
			arrTiledTtrRWhiteKey[] = new TiledTextureRegion[10];
	private AnimatedSprite anmSprBackground, anmSprMajoPack;

	private BlackKeyAnimatedSprite anmSprBlackKey[] = new BlackKeyAnimatedSprite[6];
	private WhiteKeyAnimatedSprite anmSprWhiteKey[] = new WhiteKeyAnimatedSprite[10];
	private SeAnimatedSprite anmSprSe[] = new SeAnimatedSprite[5];
	// private SakanaSprite anmSprSakana;

	private Sprite sprBackground2, sprMajoStar, sprStar1, sprStar2,
			sprBoundStar1, sprBoundStar2, sprMoon,
			arrSprCandy[] = new Sprite[9], arrSprMini[][] = new Sprite[8][3],
			arrSprBig[] = new Sprite[8];
	private boolean arrIsFlyMini[] = { false, false, false, false, false,
			false, false, false };
	private boolean arrIsFlyBig[] = { false, false, false, false, false, false,
			false, false };
	private long arrTimeStart[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private float arrPointerXSeoOff[] = { 180, 306, 433, 559, 685 };
	private float arrPointerBig[] = { 88, 178, 206, 360, 464, 540, 668, 774 };
	private float arrPointerXBlackKey[] = { 165, 262, 457, 556, 655, 850 };

	private float arrPointerXWhiteKey[] = { -16, 91, 189, 287, 385, 483, 581,
			679, 777, 875 };
	private int seSelect;

	/**
	 * Handler, Runnable
	 */
	// private Handler ;
	// private Runnable ;

	/**
	 * All status food and give food
	 */

	/**
	 * Modifier
	 */
	@SuppressWarnings("unused")
	private LoopEntityModifier majoLoppModifier, majoStarModifier;
	@SuppressWarnings("unused")
	private SequenceEntityModifier star1RotationModifier,
			star2RotationModifier;
	private SequenceEntityModifier moonModifier,
			arrCandyModifier[] = new SequenceEntityModifier[9];
	private ParallelEntityModifier star1AlphaModifier, star2AlphaModifier;
	private int statusMajo;
	/**
	 * All Sound
	 */
	private Sound mp3Pupupu, mp3Kira, mp3Harp, mp3Pi;

	private HashMap<Integer, Sound[]> arrListMp3Black = new HashMap<Integer, Sound[]>();
	private HashMap<Integer, Sound[]> arrListMp3White = new HashMap<Integer, Sound[]>();

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}
	 @Override
     public void onCreateResources() {
             SoundFactory.setAssetBasePath("doremi/mfx/");
             BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("doremi/gfx/");
             mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets());
             super.onCreateResources();
     }
	
	@Override
	public void loadKaraokeResources() {
			mBackgroundTexturePack = mTexturePackLoaderFromSource.load("doremi_background.xml");
			mBackgroundTexturePack.loadTexture();
			mBackgroundTexturePackTextureRegionLibrary  = mBackgroundTexturePack.getTexturePackTextureRegionLibrary();
			
			mItemTexturePack = mTexturePackLoaderFromSource.load("doremi_item.xml");
			mItemTexturePack.loadTexture();
			mItemexturePackTextureRegionLibrary  = mItemTexturePack.getTexturePackTextureRegionLibrary();

	
		this.ttrRBackground = getTiledTextureFromPacker(mBackgroundTexturePack,
				Vol3DoremiResource.A17_09_1_IPHONE_SORA_ID, Vol3DoremiResource.A17_09_2_IPHONE_SORA_ID);
		this.ttrRBackground2 = mBackgroundTexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_10_IPHONE_HAIKEI_ID);
		this.tiledTtrMajoPack = getTiledTextureFromPacker(mItemTexturePack,
				Vol3DoremiResource.A17_2_1_IPHONE_MAJO_ID,Vol3DoremiResource.A17_2_2_IPHONE_MAJO_ID);
		this.ttrRMajoStar = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_2_3_IPHONE_MAJO_ID);
		this.ttrRStar1 = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_03_1_IPHONE_STAR_ID);
		this.ttrRStar2 = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_04_1_IPHONE_STAR_ID);
		this.ttrRBoundStar1 = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_03_2_IPHONE_STAR_ID);
		this.ttrRBoundStar2 = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_04_2_IPHONE_STAR_ID);
		this.ttrRMoon = mItemexturePackTextureRegionLibrary.get(
				Vol3DoremiResource.A17_05_1_IPHONE_MOON_ID);
		// Load 9 Candy
		for (int i = 0; i < 9; i++) {
			this.arrTtrRCandy[i] = mItemexturePackTextureRegionLibrary.get(
					Vol3DoremiResource.ARR_RESOURCE_CANDY[i]);
		}

		// Load SE
		for (int i = 0; i < 5; i++) {
			this.arrTiledTtrRSe[i] = getTiledTextureFromPacker(mItemTexturePack,
					Vol3DoremiResource.ARR_SE[i * 2], Vol3DoremiResource.ARR_SE[i * 2 + 1]);
		}

		// Load Key
		for (int i = 0; i < 6; i++) {
			this.arrTiledTtrRBlackKey[i] = getTiledTextureFromPacker(mItemTexturePack,
					Vol3DoremiResource.ARR_RESOURCE_BLACK_KEY[i * 2],Vol3DoremiResource.ARR_RESOURCE_BLACK_KEY[i * 2 + 1]);
		}

		for (int i = 0; i < 10; i++) {
			this.arrTiledTtrRWhiteKey[i] = getTiledTextureFromPacker(mItemTexturePack,
					Vol3DoremiResource.ARR_RESOURCE_WHITE_KEY[i * 2],Vol3DoremiResource.ARR_RESOURCE_WHITE_KEY[i * 2 + 1]
					);
			
		}

		// Load Mini
		for (int i = 0; i < 8; i++) {
			this.arrTtrRMini[i] = mItemexturePackTextureRegionLibrary.get(
					Vol3DoremiResource.ARR_RESOURCE_MINI[i]);
		}

		// Load Big
		for (int i = 0; i < 8; i++) {
			this.arrTtrRBig[i] = mItemexturePackTextureRegionLibrary.get(
					Vol3DoremiResource.ARR_RESOURCE_BIG[i]);
		}
	}

	@Override
	protected void loadKaraokeSound() {
		// Load All Sound
		mp3Pupupu = loadSoundResourceFromSD(Vol3DoremiResource.A17_02_PUPUPU4);
		mp3Kira = loadSoundResourceFromSD(Vol3DoremiResource.A17_3_4_KIRA2);
		mp3Harp = loadSoundResourceFromSD(Vol3DoremiResource.A17_05_HARP);
		mp3Pi = loadSoundResourceFromSD(Vol3DoremiResource.A17_08_PI);
		
		loadSound(4,BLACK,Vol3DoremiResource.ARR_SE_BLACK_5);
		loadSound(4,WHITE,Vol3DoremiResource.ARR_SE_WHITE_5);
		
	}
	
	private boolean arrLoad[][] = new boolean[][]{
			{false, false}, {false, false}, {false, false}, {false, false}, {false, false}, 
	};
	private final int WHITE = 0;
	private final int BLACK = 1;
	
	private void loadSound(int index, int type, String... arrStr){
		if(!arrLoad[index][type]){
					final int len = arrStr.length;
					Sound sound[] = new Sound[len];
					for(int i= 0; i < len; i++){
						sound[i] = loadSoundResourceFromSD(arrStr[i]);
					}
					if(type == WHITE){
						arrListMp3White.put(index, sound);
					}else if(type == BLACK){
						arrListMp3Black.put(index, sound);
					}	
					Log.i(TAG,"Load Sound:("+sound.length+") " + sound);
					arrLoad[index][type] = true;
		}
	}

	@Override
	public void loadKaraokeScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// Set Backgroud For Scene

		this.anmSprBackground = new AnimatedSprite(0, 0, this.ttrRBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprBackground);

		this.sprBackground2 = new Sprite(0, 0, this.ttrRBackground2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBackground2);
		
		// Star Center
		this.sprStar1 = new Sprite(376, 114, this.ttrRStar1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprStar1);
		
		// Star Right
		this.sprStar2 = new Sprite(786, 76, this.ttrRStar2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprStar2);
		
		// Bound Star 1
		this.sprBoundStar1 = new Sprite(376, 114, this.ttrRBoundStar1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBoundStar1);
		this.sprBoundStar1.setVisible(false);
		this.sprBoundStar1.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// Bound Star 2
		this.sprBoundStar2 = new Sprite(786, 76, this.ttrRBoundStar2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBoundStar2);
		this.sprBoundStar2.setVisible(false);
		this.sprBoundStar2.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// Moon
		this.sprMoon = new Sprite(54, 129, this.ttrRMoon, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprMoon);

		// MaiJo Pack
		this.anmSprMajoPack = new AnimatedSprite(687, 139,
				this.tiledTtrMajoPack, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprMajoPack);

		// Star Bound Majot
		this.sprMajoStar = new Sprite(717, 120, this.ttrRMajoStar, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprMajoStar);
		this.sprMajoStar.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// Add 9 Candy
		for (int i = 0; i < 9; i++) {
			this.arrSprCandy[i] = new Sprite(50 + 100 * i, -100,
					this.arrTtrRCandy[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSprCandy[i]);
		}

		// Add SE button
		for (int i = 0; i < 5; i++) {
			this.anmSprSe[i] = new SeAnimatedSprite(arrPointerXSeoOff[i],
					TOP_SE, this.arrTiledTtrRSe[i], i, this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.anmSprSe[i]);
			this.mScene.registerTouchArea(this.anmSprSe[i]);
		}
		this.anmSprSe[4].setCurrentTileIndex(1);

		// Add Mini
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				this.arrSprMini[i][j] = new Sprite(
						arrPointerXWhiteKey[i + 1] + 15, 412,
						this.arrTtrRMini[i], this.getVertexBufferObjectManager());
				this.mScene.attachChild(this.arrSprMini[i][j]);
			}

		}

		// Add Big
		for (int i = 0; i < 8; i++) {
			this.arrSprBig[i] = new Sprite(arrPointerBig[i], 420,
					this.arrTtrRBig[i], this.getVertexBufferObjectManager());
			this.arrSprBig[i].setBlendFunction(GL10.GL_SRC_ALPHA,
					GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.mScene.attachChild(this.arrSprBig[i]);
		}

		// Add White Key
		for (int i = 0; i < 10; i++) {
			this.anmSprWhiteKey[i] = new WhiteKeyAnimatedSprite(
					arrPointerXWhiteKey[i], TOP_BLACK_KEY,
					this.arrTiledTtrRWhiteKey[i], i, this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.anmSprWhiteKey[i]);
			this.mScene.registerTouchArea(this.anmSprWhiteKey[i]);
		}

		// Add Black Key
		for (int i = 0; i < 6; i++) {
			this.anmSprBlackKey[i] = new BlackKeyAnimatedSprite(
					arrPointerXBlackKey[i], TOP_BLACK_KEY,
					this.arrTiledTtrRBlackKey[i], i, this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.anmSprBlackKey[i]);
			this.mScene.registerTouchArea(this.anmSprBlackKey[i]);
		}

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	


	@Override
	public void onResumeGame() {
		for (int i = 0; i < 8; i++) {
			arrIsFlyBig[i] = false;
			arrIsFlyMini[i] = false;
			arrTimeStart[i] = 0;
			arrSprBig[i].clearEntityModifiers();
			arrSprBig[i].setAlpha(1.0f);
			arrSprBig[i].setPosition(arrPointerBig[i], 420);
			
			for (int j = 0; j < 3; j++) {
				arrSprMini[i][j].clearEntityModifiers();
				arrSprMini[i][j].setPosition(arrPointerXWhiteKey[i + 1] + 15,
						412);
			}
		}
		for (int i = 0; i < 10; i++) {
			anmSprWhiteKey[i].setCurrentTileIndex(0);
			anmSprWhiteKey[i].pointerId = -1;
		}
		for (int i = 0; i < 6; i++) {
			anmSprBlackKey[i].setCurrentTileIndex(0);
			anmSprBlackKey[i].pointerId = -1;
		}
		for (int i = 0; i < 9; i++) {
			arrSprCandy[i].clearEntityModifiers();
			arrSprCandy[i].setPosition(50 + 100 * i, -100);
		}
		anmSprMajoPack.clearEntityModifiers();
		anmSprMajoPack.setPosition(687, 139);
		anmSprMajoPack.setFlippedHorizontal(false);
		anmSprMajoPack.setCurrentTileIndex(0);

		sprMajoStar.clearEntityModifiers();
		sprMajoStar.setPosition(717, 120);
		sprMajoStar.setAlpha(1.0f);
		majoStarModifier = null;

		sprStar1.clearEntityModifiers();
		sprStar1.setPosition(376, 114);
		sprStar1.setRotation(0.0f);

		sprBoundStar1.clearEntityModifiers();
		sprBoundStar1.setPosition(376, 114);
		sprBoundStar1.setAlpha(0.0f);
		star1AlphaModifier = null;

		sprStar2.clearEntityModifiers();
		sprStar2.setPosition(786, 76);
		sprStar1.setRotation(0.0f);

		sprBoundStar2.clearEntityModifiers();
		sprBoundStar2.setPosition(786, 76);
		sprBoundStar2.setAlpha(0.0f);
		star2AlphaModifier = null;

		sprMoon.clearEntityModifiers();
		sprMoon.setPosition(54, 99);
		sprMoon.setRotation(0.0f);
		moonModifier = null;

		anmSprSe[seSelect].setCurrentTileIndex(0);
		seSelect = SE_5;
		anmSprSe[4].setCurrentTileIndex(1);
		anmSprBackground.setCurrentTileIndex(0);
		statusMajo = MAJO_RIGHT_TO_LEFT;
		seSelect = 4;
		translateMajo();
		super.onResumeGame();

	}

	@Override
	public void onPauseGame() {
		Log.d(TAG, "================= onPauseGame ===============");
		super.onPauseGame();

		return;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(this.anmSprMajoPack,
					(int) this.anmSprMajoPack.getWidth() / 4,
					(int) this.anmSprMajoPack.getHeight() / 6,
					(int) this.anmSprMajoPack.getWidth() * 3 / 4,
					(int) this.anmSprMajoPack.getHeight(), pX, pY)) {
				touchMajo();
			} else if (this.sprStar1.contains(pX, pY)) {
				touchStar1();
			} else if (this.sprStar2.contains(pX, pY)) {
				touchStar2();
			} else if (this.sprMoon.contains(pX, pY)) {
				touchMoon();
			}
		}
		return false;
	}

	@Override
	public void combineGimmic3WithAction() {

	}

	public void onModifierFinished(IModifier pModifier, Object pItem) {
		if (pModifier.equals(majoStarModifier)) {
			nextTileAnimatedSprite(anmSprMajoPack);
		}
	}

	
	/**
	 * MajoPack translate Arrow Scene
	 */
	private void translateMajo() {
		this.anmSprMajoPack
				.registerEntityModifier(majoLoppModifier = new LoopEntityModifier(
						new ParallelEntityModifier(new LoopEntityModifier(
								new SequenceEntityModifier(
										new RotationModifier(1.0f, 0, -5),
										new RotationModifier(1.0f, -5, 10),
										new RotationModifier(1.0f, 10, 0))),
								new LoopEntityModifier(
										new SequenceEntityModifier(
												new MoveXModifier(7.0f,
														this.anmSprMajoPack
																.getX(), 20),
												new MoveXModifier(7.0f, 20,
														this.anmSprMajoPack
																.getX()))))));
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() {

			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (anmSprMajoPack.getX() >= 20 && anmSprMajoPack.getX() <= 22) {
					Log.d(TAG,"_____________COME HERE");
					anmSprMajoPack.setFlippedHorizontal(true);
					statusMajo = MAJO_LEFT_TO_RIGHT;
				}
				if (anmSprMajoPack.getX() <= 687 && anmSprMajoPack.getX() >= 684) {
					anmSprMajoPack.setFlippedHorizontal(false);
					statusMajo = MAJO_RIGHT_TO_LEFT;
				}
				if (statusMajo == MAJO_RIGHT_TO_LEFT) {
					sprMajoStar.setPosition(anmSprMajoPack.getX() + 10, 120);
				} else {
					sprMajoStar.setPosition(anmSprMajoPack.getX() + 70, 120);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void touchMajo() {
		if (majoStarModifier == null || majoStarModifier.isFinished()) {
			mp3Pupupu.play();
			/*if (anmSprMajoPack.getCurrentTileIndex() == anmSprMajoPack.getTileCount() - 1)
			{
				anmSprMajoPack.setCurrentTileIndex(anmSprMajoPack.getCurrentTileIndex() + 1);
			}
			else
			{
				anmSprMajoPack.setCurrentTileIndex(0);
			}*/
			
			nextTileAnimatedSprite(anmSprMajoPack);
			
			sprMajoStar.setAlpha(1.0f);
			sprMajoStar
					.registerEntityModifier(majoStarModifier = new LoopEntityModifier(
							new SequenceEntityModifier(new AlphaModifier(
									0.2f, 1.0f, 0.0f), new AlphaModifier(0.2f,
									0.0f, 1.0f)), 4));
			if (majoStarModifier != null) {
				majoStarModifier.addModifierListener(this);
			}
		}

		return;
	}

	private void touchStar1() {
		if (star1AlphaModifier == null || star1AlphaModifier.isFinished()) {
			mp3Kira.play();
			sprBoundStar1.setVisible(true);
			sprBoundStar1.setAlpha(1.0f);
			sprBoundStar1
					.registerEntityModifier(star1AlphaModifier = new ParallelEntityModifier(
							new SequenceEntityModifier(new MoveXModifier(0.8f,
									this.sprBoundStar1.getX(),
									this.sprBoundStar1.getX() - 40, EaseQuadOut
											.getInstance()), new MoveXModifier(
									0.8f, this.sprBoundStar1.getX() - 40,
									this.sprBoundStar1.getX(), EaseQuadOut
											.getInstance())),
							new LoopEntityModifier(
									new SequenceEntityModifier(

											new AlphaModifier(0.1f, 1.0f, 0.0f),
											new AlphaModifier(0.1f, 0.2f, 1.0f),
											new AlphaModifier(0.1f, 1.0f, 0.0f)), 10)));

			this.sprStar1
					.registerEntityModifier(star1RotationModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new RotationModifier(
									0.8f, 0, -10, EaseQuadOut.getInstance()),
									new MoveXModifier(0.8f, this.sprStar1
											.getX(), this.sprStar1.getX() - 40,
											EaseQuadOut.getInstance())),
							new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveXModifier(0.8f,
													this.sprStar1.getX() - 40,
													this.sprStar1.getX(),
													EaseQuadOut.getInstance()),
											new RotationModifier(0.8f, -10, 10,
													EaseQuadOut.getInstance())),
									new RotationModifier(0.5f, 10, 0,
											EaseQuadOut.getInstance()))));
		}
		return;
	}

	private void touchStar2() {
		if (star2AlphaModifier == null || star2AlphaModifier.isFinished()) {
			mp3Kira.play();
			sprBoundStar2.setVisible(true);
			sprBoundStar2.setAlpha(1.0f);
			sprBoundStar2
					.registerEntityModifier(star2AlphaModifier = new ParallelEntityModifier(
							new SequenceEntityModifier(new MoveXModifier(0.8f,
									this.sprBoundStar2.getX(),
									this.sprBoundStar2.getX() - 40, EaseQuadOut
											.getInstance()), new MoveXModifier(
									0.8f, this.sprBoundStar2.getX() - 40,
									this.sprBoundStar2.getX(), EaseQuadOut
											.getInstance())),
							new LoopEntityModifier(
									
									new SequenceEntityModifier(

											new AlphaModifier(0.1f, 1.0f, 0.0f),
											new AlphaModifier(0.1f, 0.2f, 1.0f),
											new AlphaModifier(0.1f, 1.0f, 0.0f)), 10)));

			this.sprStar2
					.registerEntityModifier(star2RotationModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new RotationModifier(
									0.8f, 0, -10, EaseQuadOut.getInstance()),
									new MoveXModifier(0.8f, this.sprStar2
											.getX(), this.sprStar2.getX() - 40,
											EaseQuadOut.getInstance())),
							new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveXModifier(0.8f,
													this.sprStar2.getX() - 40,
													this.sprStar2.getX(),
													EaseQuadOut.getInstance()),
											new RotationModifier(0.8f, -10, 10,
													EaseQuadOut.getInstance())),
									new RotationModifier(0.5f, 10, 0,
											EaseQuadOut.getInstance()))));
		}
		return;
	}

	private void touchMoon() {
		if (moonModifier == null || moonModifier.isFinished()) {
			mp3Harp.play();
			this.sprMoon
					.registerEntityModifier(moonModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new RotationModifier(
									0.6f, 0, -25, EaseQuadOut.getInstance()),
									new MoveXModifier(0.6f,
											this.sprMoon.getX(), this.sprMoon
													.getX() + 60, EaseQuadOut
													.getInstance())),
							new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveXModifier(0.6f,
													this.sprMoon.getX() + 60,
													this.sprMoon.getX(),
													EaseQuadOut.getInstance()),
											new RotationModifier(0.6f, -25, 25,
													EaseQuadOut.getInstance())),
									new RotationModifier(0.6f, 25, 0,
											EaseQuadOut.getInstance())),
							new DelayModifier(2.0f)));
			for (int i = 0; i < 9; i++) {
				final int index = i;
				TimerHandler timerHandler = new TimerHandler((i + 1) * 0.2f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
								arrSprCandy[index]
										.registerEntityModifier(arrCandyModifier[index] = new SequenceEntityModifier(
												new MoveYModifier(0.8f,
														arrSprCandy[index]
																.getY(), 360,
														EaseBounceOut
																.getInstance()),
												new DelayModifier(0.2f),
												new MoveYModifier(1.0f, 360,
														500, EaseQuadOut
																.getInstance())));
								if (arrCandyModifier[index] != null) {
									arrCandyModifier[index]
											.addModifierListener(new IModifierListener<IEntity>() {

												@Override
												public void onModifierFinished(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													arrSprCandy[index]
															.setPosition(
																	50 + 100 * index,
																	-100);
												}

												@Override
												public void onModifierStarted(
														IModifier<IEntity> arg0,
														IEntity arg1) {
													
												}
											});
								}
							}
						});
				this.mEngine.registerUpdateHandler(timerHandler);
			}
		}

		return;
	}

	class SeAnimatedSprite extends AnimatedSprite {

		private int index;

		public int getIndex() {
			return this.index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public SeAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {

			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
				mp3Pi.play();
				anmSprSe[seSelect].setCurrentTileIndex(0);
				seSelect = this.index;
				loadSound(this.index, BLACK, Vol3DoremiResource.SE_BLACK[this.index]);
				loadSound(this.index, WHITE, Vol3DoremiResource.SE_WHITE[this.index]);
				this.setCurrentTileIndex(1);

			}
			return false;
		}
	}

	class BlackKeyAnimatedSprite extends AnimatedSprite {
		private int index;
		public int pointerId;

		public int getIndex() {
			return this.index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public BlackKeyAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.pointerId = -1;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			int action = pSceneTouchEvent.getAction();
			switch (action) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				arrListMp3Black.get(seSelect)[this.index].play();
				this.pointerId = pSceneTouchEvent.getPointerID();
				break;

			case TouchEvent.ACTION_MOVE:
				if (pTouchAreaLocalX < 0 || pTouchAreaLocalX > this.getWidth())
				{
					for (int i = 0; i < 6; i++) {
						if (anmSprBlackKey[i].getCurrentTileIndex() == 0
								&& anmSprBlackKey[i].contains(pX, pY)
								&& anmSprBlackKey[i].pointerId != pSceneTouchEvent
										.getPointerID()) {
							arrListMp3Black.get(seSelect)[i].play();
							anmSprBlackKey[i].setCurrentTileIndex(1);
							anmSprBlackKey[i].pointerId = pSceneTouchEvent
									.getPointerID();
						} else if (!anmSprBlackKey[i].contains(pX, pY)
								&& anmSprBlackKey[i].getCurrentTileIndex() == 1
								&& anmSprBlackKey[i].pointerId == pSceneTouchEvent
										.getPointerID()) {
							anmSprBlackKey[i].setCurrentTileIndex(0);
							anmSprBlackKey[i].pointerId = -1;
						}
					}
	
					for (int i = 0; i < 10; i++) {
						Log.d(TAG, "pX:" + pX);
						if (anmSprWhiteKey[i].getCurrentTileIndex() == 0
								&& anmSprWhiteKey[i].contains(pX, pY)
								&& anmSprWhiteKey[i].pointerId != pSceneTouchEvent
										.getPointerID()) {
							arrListMp3White.get(seSelect)[i].play();
							anmSprWhiteKey[i].setCurrentTileIndex(1);
							anmSprWhiteKey[i].pointerId = pSceneTouchEvent
									.getPointerID();
	
							if (i >= 1 && i <= 8) {
								final int indexTemp = i;
								arrTimeStart[i - 1] = System.currentTimeMillis();
								if (!arrIsFlyMini[i - 1]) {
									for (int j = 0; j < 3; j++) {
										final int indexTemp2 = j;
	
										arrIsFlyMini[i - 1] = true;
										switch (j) {
										case 0:
											if (arrSprMini[i - 1][2].getY() == 412) {
												arrSprMini[i - 1][j]
														.registerEntityModifier(new SequenceEntityModifier(
	
																new IEntityModifierListener() {
	
																	@Override
																	public void onModifierFinished(
																			IModifier<IEntity> pModifier,
																			IEntity pItem) {
																		arrSprMini[indexTemp - 1][indexTemp2]
																				.setPosition(
																						arrPointerXWhiteKey[indexTemp] + 15,
																						412);
																	}

																	@Override
																	public void onModifierStarted(
																			IModifier<IEntity> arg0,
																			IEntity arg1) {
																		
																		
																	}
																},
																new MoveModifier(
																		0.4f,
																		arrSprMini[indexTemp - 1][j]
																				.getX(),
																		arrSprMini[indexTemp - 1][j]
																				.getX() - 80,
																		arrSprMini[indexTemp - 1][j]
																				.getY(),
																		arrSprMini[indexTemp - 1][j]
																				.getY() - 150),
																				new DelayModifier(0.1f)
																));
											}
											break;
										case 1:
											if (arrSprMini[i - 1][2].getY() == 412) {
												arrSprMini[i - 1][j]
														.registerEntityModifier(new SequenceEntityModifier(
																new IEntityModifierListener() {
	
																	@Override
																	public void onModifierFinished(
																			IModifier<IEntity> pModifier,
																			IEntity pItem) {
																		arrSprMini[indexTemp - 1][indexTemp2]
																				.setPosition(
																						arrPointerXWhiteKey[indexTemp] + 15,
																						412);
																	}

																	@Override
																	public void onModifierStarted(
																			IModifier<IEntity> arg0,
																			IEntity arg1) {
																		
																		
																	}
																},
																new DelayModifier(
																		0.1f),
																new MoveModifier(
																		0.4f,
																		arrSprMini[indexTemp - 1][j]
																				.getX(),
																		arrSprMini[indexTemp - 1][j]
																				.getX() + 80,
																		arrSprMini[indexTemp - 1][j]
																				.getY(),
																		arrSprMini[indexTemp - 1][j]
																				.getY() - 150)));
											}
											break;
										case 2:
											if (arrSprMini[indexTemp - 1][2].getY() == 412) {
												arrSprMini[indexTemp - 1][j]
														.registerEntityModifier(new SequenceEntityModifier(
																new IEntityModifierListener() {
	
																	@Override
																	public void onModifierFinished(
																			IModifier<IEntity> pModifier,
																			IEntity pItem) {
																		arrSprMini[indexTemp - 1][indexTemp2]
																				.setPosition(
																						arrPointerXWhiteKey[indexTemp] + 15,
																						412);
																		arrIsFlyMini[indexTemp - 1] = false;
																	}

																	@Override
																	public void onModifierStarted(
																			IModifier<IEntity> arg0,
																			IEntity arg1) {
																		
																		
																	}
																},
																new DelayModifier(
																		0.2f),
																new MoveModifier(
																		0.4f,
																		arrSprMini[indexTemp - 1][j]
																				.getX(),
																		arrSprMini[indexTemp - 1][j]
																				.getX(),
																		arrSprMini[indexTemp - 1][j]
																				.getY(),
																		arrSprMini[indexTemp - 1][j]
																				.getY() - 150),
																				new DelayModifier(0.1f)
																));
											}
											break;
	
										default:
											break;
										}
									}
								}
							}
						} else if (!anmSprWhiteKey[i].contains(pX, pY)
								&& anmSprWhiteKey[i].getCurrentTileIndex() == 1
								&& anmSprWhiteKey[i].pointerId == pSceneTouchEvent
										.getPointerID()) {
							anmSprWhiteKey[i].setCurrentTileIndex(0);
							anmSprWhiteKey[i].pointerId = -1;
							if (i >= 1 && i < 9) {
								arrIsFlyMini[i - 1] = false;
								arrTimeStart[i - 1] = 0;
							}
						}
					}
				}
				break;

			case TouchEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP:"
						+ pSceneTouchEvent.getMotionEvent().getPointerCount());
				if (pSceneTouchEvent.getMotionEvent().getPointerCount() > 0) {
					for (int i = 0; i < 6; i++) {
						if (anmSprBlackKey[i].pointerId == pSceneTouchEvent
								.getPointerID()) {
							anmSprBlackKey[i].pointerId = -1;
							anmSprBlackKey[i].setCurrentTileIndex(0);
						}
					}
					for (int i = 0; i < 10; i++) {
						if (anmSprWhiteKey[i].pointerId == pSceneTouchEvent
								.getPointerID()) {
							anmSprWhiteKey[i].pointerId = -1;
							anmSprWhiteKey[i].setCurrentTileIndex(0);
							if (i >= 1 && i < 9) {
								if (arrIsFlyBig[i - 1] == false) {
									if (arrTimeStart[i - 1] != 0
											&& System.currentTimeMillis()
													- arrTimeStart[i - 1] >= 1000
											&& anmSprWhiteKey[i]
													.getCurrentTileIndex() == 0) {
										final int indexTemp = i;
										arrIsFlyBig[i - 1] = true;
										if (i == 5) {
											anmSprBackground.setCurrentTileIndex(1);
										}
										arrSprBig[i - 1]
												.registerEntityModifier(new SequenceEntityModifier(
														new IEntityModifierListener() {

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																arrSprBig[indexTemp - 1]
																		.setAlpha(1.0f);
																arrSprBig[indexTemp - 1]
																		.setPosition(
																				arrPointerBig[indexTemp - 1],
																				420);
																arrIsFlyBig[indexTemp - 1] = false;
																if (indexTemp == 5) {
																	anmSprBackground
																			.setCurrentTileIndex(0);
																}
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																
																
															}
														},

														new MoveYModifier(
																1.5f,
																arrSprBig[i - 1]
																		.getY(),
																arrSprBig[i - 1]
																		.getY() - 320),
														new AlphaModifier(1.0f,
																1.0f, 0.0f)

												));
									}
								}
								arrTimeStart[i - 1] = 0;
							}

						}

					}
				} else {
					for (int i = 0; i < 6; i++) {
						anmSprBlackKey[i].pointerId = -1;
						anmSprBlackKey[i].setCurrentTileIndex(0);

					}
					for (int i = 0; i < 10; i++) {

						anmSprWhiteKey[i].pointerId = -1;
						anmSprWhiteKey[i].setCurrentTileIndex(0);
						if (i >= 1 && i < 9) {
							if (arrIsFlyBig[i - 1] == false) {
								if (arrTimeStart[i - 1] != 0
										&& System.currentTimeMillis()
												- arrTimeStart[i - 1] >= 1000
										&& anmSprWhiteKey[i]
												.getCurrentTileIndex() == 0) {
									final int indexTemp = i;
									arrIsFlyBig[i - 1] = true;
									if (indexTemp == 5) {
										anmSprBackground.setCurrentTileIndex(1);
									}
									arrSprBig[i - 1]
											.registerEntityModifier(new SequenceEntityModifier(
													new IEntityModifierListener() {

														@Override
														public void onModifierFinished(
																IModifier<IEntity> pModifier,
																IEntity pItem) {
															arrSprBig[indexTemp - 1]
																	.setAlpha(1.0f);
															arrSprBig[indexTemp - 1]
																	.setPosition(
																			arrPointerBig[indexTemp - 1],
																			420);
															arrIsFlyBig[indexTemp - 1] = false;
															if (indexTemp == 5) {
																anmSprBackground
																		.setCurrentTileIndex(0);
															}
														}

														@Override
														public void onModifierStarted(
																IModifier<IEntity> arg0,
																IEntity arg1) {
															
															
														}
													},

													new MoveYModifier(
															1.5f,
															arrSprBig[i - 1]
																	.getY(),
															arrSprBig[i - 1]
																	.getY() - 320),
													new AlphaModifier(1.0f,
															1.0f, 0.0f)

											));

								}
							}
							arrTimeStart[i - 1] = 0;
						}
					}
				}
				break;

			default:
				break;
			}
			return true;
		}

	}

	class WhiteKeyAnimatedSprite extends AnimatedSprite {
		public int pointerId;
		private int index;

		public int getIndex() {
			return this.index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public WhiteKeyAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.pointerId = -1;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			int action = pSceneTouchEvent.getAction();

			switch (action) {
			case TouchEvent.ACTION_DOWN:
				if (this.getCurrentTileIndex() == 0) {
					this.setCurrentTileIndex(1);
					arrListMp3White.get(seSelect)[this.index].play();
					this.pointerId = pSceneTouchEvent.getPointerID();
					if (this.index >= 1 && this.index <= 8) {
						arrTimeStart[this.index - 1] = System
								.currentTimeMillis();
						final int indexTemp = this.index;
						if (!arrIsFlyMini[this.index - 1]) {
							for (int i = 0; i < 3; i++) {
								final int indexTemp2 = i;

								arrIsFlyMini[this.index - 1] = true;
								switch (i) {
								case 0:
									if (arrSprMini[this.index - 1][2].getY() == 412) {
										arrSprMini[this.index - 1][i]
												.registerEntityModifier(new SequenceEntityModifier(

														new IEntityModifierListener() {

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																arrSprMini[indexTemp - 1][indexTemp2]
																		.setPosition(
																				arrPointerXWhiteKey[indexTemp] + 15,
																				412);
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																
																
															}
														},
														new MoveModifier(
																0.4f,
																arrSprMini[this.index - 1][i]
																		.getX(),
																arrSprMini[this.index - 1][i]
																		.getX() - 80,
																arrSprMini[this.index - 1][i]
																		.getY(),
																arrSprMini[this.index - 1][i]
																		.getY() - 150)));
									}
									break;
								case 1:
									if (arrSprMini[this.index - 1][2].getY() == 412) {
										arrSprMini[this.index - 1][i]
												.registerEntityModifier(new SequenceEntityModifier(
														new IEntityModifierListener() {

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																arrSprMini[indexTemp - 1][indexTemp2]
																		.setPosition(
																				arrPointerXWhiteKey[indexTemp] + 15,
																				412);
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																
																
															}
														},
														new DelayModifier(0.1f),
														new MoveModifier(
																0.4f,
																arrSprMini[this.index - 1][i]
																		.getX(),
																arrSprMini[this.index - 1][i]
																		.getX() + 80,
																arrSprMini[this.index - 1][i]
																		.getY(),
																arrSprMini[this.index - 1][i]
																		.getY() - 150)));
									}
									break;
								case 2:
									if (arrSprMini[this.index - 1][2].getY() == 412) {
										arrSprMini[this.index - 1][i]
												.registerEntityModifier(new SequenceEntityModifier(
														new IEntityModifierListener() {

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																arrSprMini[indexTemp - 1][indexTemp2]
																		.setPosition(
																				arrPointerXWhiteKey[indexTemp] + 15,
																				412);
																arrIsFlyMini[indexTemp - 1] = false;
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																
																
															}
														},
														new DelayModifier(0.2f),
														new MoveModifier(
																0.4f,
																arrSprMini[this.index - 1][i]
																		.getX(),
																arrSprMini[this.index - 1][i]
																		.getX(),
																arrSprMini[this.index - 1][i]
																		.getY(),
																arrSprMini[this.index - 1][i]
																		.getY() - 150),
														new DelayModifier(0.1f)
														));
									}
									break;

								default:
									break;
								}
							}
						}
					}
				}
				break;
			case TouchEvent.ACTION_MOVE:

				for (int i = 0; i < 10; i++) {
					Log.d(TAG, "pX:" + pX);
					if (anmSprWhiteKey[i].getCurrentTileIndex() == 0
							&& anmSprWhiteKey[i].contains(pX, pY)
							&& anmSprWhiteKey[i].pointerId != pSceneTouchEvent
									.getPointerID()) {
						arrListMp3White.get(seSelect)[i].play();
						anmSprWhiteKey[i].setCurrentTileIndex(1);
						anmSprWhiteKey[i].pointerId = pSceneTouchEvent
								.getPointerID();

						if (i >= 1 && i <= 8) {
							final int indexTemp = i;
							arrTimeStart[i - 1] = System.currentTimeMillis();
							if (!arrIsFlyMini[i - 1]) {
								for (int j = 0; j < 3; j++) {
									final int indexTemp2 = j;

									arrIsFlyMini[i - 1] = true;
									switch (j) {
									case 0:
										if (arrSprMini[i - 1][2].getY() == 412) {
											arrSprMini[i - 1][j]
													.registerEntityModifier(new SequenceEntityModifier(

															new IEntityModifierListener() {

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> pModifier,
																		IEntity pItem) {
																	arrSprMini[indexTemp - 1][indexTemp2]
																			.setPosition(
																					arrPointerXWhiteKey[indexTemp] + 15,
																					412);
																}

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	
																	
																}
															},
															new MoveModifier(
																	0.4f,
																	arrSprMini[indexTemp - 1][j]
																			.getX(),
																	arrSprMini[indexTemp - 1][j]
																			.getX() - 80,
																	arrSprMini[indexTemp - 1][j]
																			.getY(),
																	arrSprMini[indexTemp - 1][j]
																			.getY() - 150)));
										}
										break;
									case 1:
										if (arrSprMini[i - 1][2].getY() == 412) {
											arrSprMini[i - 1][j]
													.registerEntityModifier(new SequenceEntityModifier(
															new IEntityModifierListener() {

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> pModifier,
																		IEntity pItem) {
																	arrSprMini[indexTemp - 1][indexTemp2]
																			.setPosition(
																					arrPointerXWhiteKey[indexTemp] + 15,
																					412);
																}

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	
																	
																}
															},
															new DelayModifier(
																	0.1f),
															new MoveModifier(
																	0.4f,
																	arrSprMini[indexTemp - 1][j]
																			.getX(),
																	arrSprMini[indexTemp - 1][j]
																			.getX() + 80,
																	arrSprMini[indexTemp - 1][j]
																			.getY(),
																	arrSprMini[indexTemp - 1][j]
																			.getY() - 150)));
										}
										break;
									case 2:
										if (arrSprMini[indexTemp - 1][2].getY() == 412) {
											arrSprMini[indexTemp - 1][j]
													.registerEntityModifier(new SequenceEntityModifier(
															new IEntityModifierListener() {

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> pModifier,
																		IEntity pItem) {
																	arrSprMini[indexTemp - 1][indexTemp2]
																			.setPosition(
																					arrPointerXWhiteKey[indexTemp] + 15,
																					412);
																	arrIsFlyMini[indexTemp - 1] = false;
																}

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	
																	
																}
															},
															new DelayModifier(
																	0.2f),
															new MoveModifier(
																	0.4f,
																	arrSprMini[indexTemp - 1][j]
																			.getX(),
																	arrSprMini[indexTemp - 1][j]
																			.getX(),
																	arrSprMini[indexTemp - 1][j]
																			.getY(),
																	arrSprMini[indexTemp - 1][j]
																			.getY() - 150),
																			new DelayModifier(0.1f)
															));
										}
										break;

									default:
										break;
									}
								}
							}
						}
					} else if (!anmSprWhiteKey[i].contains(pX, pY)
							&& anmSprWhiteKey[i].getCurrentTileIndex() == 1
							&& anmSprWhiteKey[i].pointerId == pSceneTouchEvent
									.getPointerID()) {
						anmSprWhiteKey[i].setCurrentTileIndex(0);
						anmSprWhiteKey[i].pointerId = -1;
						if (i >= 1 && i < 9) {
							arrIsFlyMini[i - 1] = false;
							arrTimeStart[i - 1] = 0;
						}
					}
				}
				for (int i = 0; i < 6; i++) {
					if (anmSprBlackKey[i].getCurrentTileIndex() == 0
							&& anmSprBlackKey[i].contains(pX, pY)
							&& anmSprBlackKey[i].pointerId != pSceneTouchEvent
									.getPointerID()) {
						arrListMp3Black.get(seSelect)[i].play();
						anmSprBlackKey[i].setCurrentTileIndex(1);
						anmSprBlackKey[i].pointerId = pSceneTouchEvent
								.getPointerID();
					} else if (!anmSprBlackKey[i].contains(pX, pY)
							&& anmSprBlackKey[i].getCurrentTileIndex() == 1
							&& anmSprBlackKey[i].pointerId == pSceneTouchEvent
									.getPointerID()) {
						anmSprBlackKey[i].setCurrentTileIndex(0);
						anmSprBlackKey[i].pointerId = -1;
					}
				}
				break;
			case TouchEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP:"
						+ pSceneTouchEvent.getMotionEvent().getPointerCount());

				if (pSceneTouchEvent.getMotionEvent().getPointerCount() > 0) {
					for (int i = 0; i < 10; i++) {
						if (anmSprWhiteKey[i].pointerId == pSceneTouchEvent
								.getPointerID()) {
							anmSprWhiteKey[i].pointerId = -1;
							anmSprWhiteKey[i].setCurrentTileIndex(0);
							if (i >= 1 && i < 9) {
								if (arrIsFlyBig[i - 1] == false) {
									if (arrTimeStart[i - 1] != 0
											&& System.currentTimeMillis()
													- arrTimeStart[i - 1] >= 1000
											&& anmSprWhiteKey[i]
													.getCurrentTileIndex() == 0) {
										final int indexTemp = i;
										arrIsFlyBig[i - 1] = true;
										if (i == 5) {
											anmSprBackground.setCurrentTileIndex(1);
										}
										arrSprBig[i - 1]
												.registerEntityModifier(new SequenceEntityModifier(
														new IEntityModifierListener() {

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																arrSprBig[indexTemp - 1]
																		.setAlpha(1.0f);
																arrSprBig[indexTemp - 1]
																		.setPosition(
																				arrPointerBig[indexTemp - 1],
																				420);
																arrIsFlyBig[indexTemp - 1] = false;
																if (indexTemp == 5) {
																	anmSprBackground
																			.setCurrentTileIndex(0);
																}
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																
																
															}
														},

														new MoveYModifier(
																1.5f,
																arrSprBig[i - 1]
																		.getY(),
																arrSprBig[i - 1]
																		.getY() - 320),
														new AlphaModifier(1.0f,
																1.0f, 0.0f)

												));
									}
								}
								arrTimeStart[i - 1] = 0;
							}

						}

					}
					for (int i = 0; i < 6; i++) {
						if (anmSprBlackKey[i].pointerId == pSceneTouchEvent
								.getPointerID()) {
							anmSprBlackKey[i].pointerId = -1;
							anmSprBlackKey[i].setCurrentTileIndex(0);
						}
					}
				} else {
					for (int i = 0; i < 10; i++) {

						anmSprWhiteKey[i].pointerId = -1;
						anmSprWhiteKey[i].setCurrentTileIndex(0);
						if (i >= 1 && i < 9) {
							if (arrIsFlyBig[i - 1] == false) {
								if (arrTimeStart[i - 1] != 0
										&& System.currentTimeMillis()
												- arrTimeStart[i - 1] >= 1000
										&& anmSprWhiteKey[i]
												.getCurrentTileIndex() == 0) {
									final int indexTemp = i;
									arrIsFlyBig[i - 1] = true;
									if (i == 5) {
										anmSprBackground.setCurrentTileIndex(1);
									}
									arrSprBig[i - 1]
											.registerEntityModifier(new SequenceEntityModifier(
													new IEntityModifierListener() {

														@Override
														public void onModifierFinished(
																IModifier<IEntity> pModifier,
																IEntity pItem) {
															arrSprBig[indexTemp - 1]
																	.setAlpha(1.0f);
															arrSprBig[indexTemp - 1]
																	.setPosition(
																			arrPointerBig[indexTemp - 1],
																			420);
															arrIsFlyBig[indexTemp - 1] = false;
															if (indexTemp == 5) {
																anmSprBackground
																		.setCurrentTileIndex(0);
															}
														}

														@Override
														public void onModifierStarted(
																IModifier<IEntity> arg0,
																IEntity arg1) {
															
															
														}
													},

													new MoveYModifier(
															1.5f,
															arrSprBig[i - 1]
																	.getY(),
															arrSprBig[i - 1]
																	.getY() - 320),
													new AlphaModifier(1.0f,
															1.0f, 0.0f)

											));

								}
							}
							arrTimeStart[i - 1] = 0;
						}
					}
					for (int i = 0; i < 6; i++) {
						anmSprBlackKey[i].pointerId = -1;
						anmSprBlackKey[i].setCurrentTileIndex(0);

					}
				}

				break;

			default:
				break;
			}
			return true;
		}

	}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		
		
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

	@Override
	public void onModifierStarted(IModifier arg0, Object arg1) {
		
		
	}
}
