package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KamomeResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.os.Handler;
import android.util.Log;

public class Vol3Kamome extends BaseGameFragment implements
		IOnSceneTouchListener {

	private static final String TAG = "LOG_KAMOME";
	
	private TexturePack ttpKamome1, ttpKamome2;
	private TexturePackTextureRegionLibrary ttpLibKamome1, ttpLibKamome2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion ttrBackground, ttrKamomeSwim, ttrKamomeRight,
			ttrKamomeOpen, ttrKamomeCenter, ttrKamomeAction, ttrCamomeLeft,
			ttrKamomeJump, ttrKamomeWhaleBefore, ttrKamome2Right,
			ttrKamomeLeft, ttrShip, ttrShipSmoke, ttrSun1, ttrSun2,
			ttrKaniRight, ttrKaniLeft, ttrWhaleTide, ttrHaikeBeforeKamome;

	
	private Sprite sKamomeSwim, sKamomeRight, sKamomeOpen, sKamomeCenter,
			sKamomeAction, sCamomeLeft, sKamomeJump, sKamomeWhaleBefore,
			sKamome2Right, sKamomeLeft, sShip, sShipSmoke, sSun1, sSun2,
			sKaniRight, sKaniLeft, sWhaleTide, sHaikeBeforeKamome;

	@SuppressWarnings("unused")
	private SequenceEntityModifier kaniLeftModifier, kaniRightModifier,
			kamomeCenterModifier, sunModifier, kamomeRightModifier,
			kamomeWhaleModifier, kamomeWhaleBeforeModifier;
	@SuppressWarnings({ "unused", "rawtypes" })
	private LoopModifier kamomeShipModifier, kamomeBirdLeftModifier,
			kamomeBirdRightModifier;

	private Handler camomeLeftHandler, kamomeRightHandler, kamomeSwimHandler,
			kamomeWhaleHandlers, kamomeMoveHandler, kamomeShipSmokeHandler,
			kamomeCenterHandler;

	private Runnable camomeLeftCallback, kamomeRightCallback,
			kamomeSwimCallback, kamomeWhaleCallback, kamomeMoveCallback,
			kamomeShipSmokeCallback, kamomeCenterCallback;

	private boolean isTouchCamomeLeft, isTouchKamomeCenter, isTouchKamomeRight,
			isWhaleTouch, isTouchSun;

	private float fromXWhale, fromXWhaleTide = 1344f;

	private Sound mp3Trumpetshort, mp3KaniWalk, mp3Sun, mp3KamomeCenter,
			mp3KamomeLeft, mp3KamomeSwim, mp3BigTide;
	
	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();

		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrBackground, this.getVertexBufferObjectManager())));

//		this.mScene.setOnAreaTouchTraversalFrontToBack();

		sShip = new Sprite(320, 105, ttrShip, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sShip);

		sShipSmoke = new Sprite(410, 40, ttrShipSmoke, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sShipSmoke);
		sShipSmoke.setVisible(false);
		

		sSun2 = new Sprite(47, 8, ttrSun2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSun2);
		
		sSun1 = new Sprite(50, 8, ttrSun1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSun1);


		sHaikeBeforeKamome = new Sprite(0, 247, ttrHaikeBeforeKamome, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHaikeBeforeKamome);

		sWhaleTide = new Sprite(1344, 120, ttrWhaleTide, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sWhaleTide);
		sWhaleTide.setVisible(false);

		sKamomeWhaleBefore = new Sprite(1344, 220, ttrKamomeWhaleBefore, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeWhaleBefore);

		sKamomeSwim = new Sprite(800, 420, ttrKamomeSwim, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeSwim);
		sKamomeSwim.setVisible(false);

		sKamomeRight = new Sprite(615, 380, ttrKamomeRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeRight);

		sKamomeOpen = new Sprite(350, 260, ttrKamomeOpen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeOpen);
		sKamomeOpen.setVisible(false);

		sKamomeCenter = new Sprite(350, 260, ttrKamomeCenter, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeCenter);

		sKamomeAction = new Sprite(85, 210, ttrKamomeAction, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeAction);
		sKamomeAction.setVisible(false);

		sCamomeLeft = new Sprite(85, 210, ttrCamomeLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCamomeLeft);

		sKamomeJump = new Sprite(800, 310, ttrKamomeJump, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeJump);
		sKamomeJump.setVisible(false);

		sKamome2Right = new Sprite(900, 50, ttrKamome2Right, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamome2Right);

		sKamomeLeft = new Sprite(600, 90, ttrKamomeLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamomeLeft);

		sKaniRight = new Sprite(900, 585, ttrKaniRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKaniRight);

		sKaniLeft = new Sprite(3, 570, ttrKaniLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKaniLeft);

		addGimmicsButton(mScene, Vol3KamomeResource.SOUND_GIMMIC,
				Vol3KamomeResource.IMAGE_GIMMIC, ttpLibKamome1);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene: end ");
		
	}
	
	

	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources: ");
		ttpKamome1 = mTexturePackLoaderFromSource.load("kamome1.xml");
		ttpKamome1.loadTexture();
		ttpLibKamome1 = ttpKamome1.getTexturePackTextureRegionLibrary();
		
		ttpKamome2 = mTexturePackLoaderFromSource.load("kamome2.xml");
		ttpKamome2.loadTexture();
		ttpLibKamome2 = ttpKamome2.getTexturePackTextureRegionLibrary();
		
		this.ttrBackground = ttpLibKamome2.get(Vol3KamomeResource.A4_00_ADR_HAIKEI_KAMOME_ID);
		// KamomeSwim
		this.ttrKamomeSwim = ttpLibKamome1.get(Vol3KamomeResource.A4_01_ADR_KAMOME_SWIM_ID);
		// KamomeRight
		this.ttrKamomeRight = ttpLibKamome1.get(Vol3KamomeResource.A4_02_ADR_KAMOME_RIGHT_ID);
		// KamomeOpen
		this.ttrKamomeOpen = ttpLibKamome1.get(Vol3KamomeResource.A4_03_ADR_KAMOME_OPEN_ID);
		// KamomeCenter
		this.ttrKamomeCenter = ttpLibKamome1.get(Vol3KamomeResource.A4_04_ADR_KAMOME_CENTER_ID);
		// KamomeAction
		this.ttrKamomeAction = ttpLibKamome1.get(Vol3KamomeResource.A4_05_ADR_E0005_KAMOME_ACTION_ID);
		// KamomeLeft
		this.ttrCamomeLeft = ttpLibKamome1.get(Vol3KamomeResource.A4_06_ADR_E0005_CAMOME_LEFT_ID);
		// KamomeJump
		this.ttrKamomeJump = ttpLibKamome1.get(Vol3KamomeResource.A4_07_ADR_FISH_JUMP_ID);
		// KamomeWhaleBefore
		this.ttrKamomeWhaleBefore = ttpLibKamome1.get(Vol3KamomeResource.A4_08_ADR_WHALE_BEFORE_ID);
		// KamomeRight 2
		this.ttrKamome2Right = ttpLibKamome1.get(Vol3KamomeResource.A4_11_ADR_KAMOME_RIGHT_ID);
		// KamomeLeft
		this.ttrKamomeLeft = ttpLibKamome1.get(Vol3KamomeResource.A4_12_ADR_KAMOME_LEFT_ID);
		// Ship
		this.ttrShip = ttpLibKamome1.get(Vol3KamomeResource.A4_13_ADR_SHIP_ID);
		// Ship Smoke
		this.ttrShipSmoke = ttpLibKamome1.get(Vol3KamomeResource.A4_14_ADR_SHIP_SMOKE_ID);
		// Sun 1
		this.ttrSun1 = ttpLibKamome1.get(Vol3KamomeResource.A4_15_ADR_SUN1_ID);
		// Sun 2
		this.ttrSun2 = ttpLibKamome1.get(Vol3KamomeResource.A4_16_ADR_SUN2_ID);
		// Kani Right
		this.ttrKaniRight = ttpLibKamome1.get(Vol3KamomeResource.A4_17_ADR_KANI_RIGHT_ID);
		// Kani Left
		this.ttrKaniLeft = ttpLibKamome1.get(Vol3KamomeResource.A4_18_ADR_KANI_LEFT_ID);
		// Whale Tide
		this.ttrWhaleTide = ttpLibKamome1.get(Vol3KamomeResource.A4_19_ADR_WHALE_TIDE_ID);
		// Haike Before Kamome
		this.ttrHaikeBeforeKamome = ttpLibKamome2.get(Vol3KamomeResource.A4_23_ADR_HAIKEI_BEFORE_KAMOME_ID);
		Log.d(TAG, "loadKaraokeResources: end");
		
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeSound: ");
		mp3Trumpetshort = loadSoundResourceFromSD(Vol3KamomeResource.E0004_TRUMPETDOUBLE_SHORT);
		mp3KaniWalk = loadSoundResourceFromSD(Vol3KamomeResource.E00035_KANI_WALK);
		mp3Sun = loadSoundResourceFromSD(Vol3KamomeResource.E00032_SUN);
		mp3KamomeCenter = loadSoundResourceFromSD(Vol3KamomeResource.E00033_KAMOME_CENTER);
		mp3KamomeLeft = loadSoundResourceFromSD(Vol3KamomeResource.E0005_KAMOME);
		mp3KamomeSwim = loadSoundResourceFromSD(Vol3KamomeResource.E00034_KAMOME_SWIM);
		mp3BigTide = loadSoundResourceFromSD(Vol3KamomeResource.E00026_BIG_TIDE);
		Log.d(TAG, "loadKaraokeSound: end ");
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "loadKaraokeSound: ");
		SoundFactory.setAssetBasePath("kamome/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kamome/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "kamome/gfx/");
		super.onCreateResources();

	}
	
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onResumeGame: ");
		super.onResumeGame();
		initial();
		kamomeShipModifier();
		kamomeWhaleModifier();
		kamomeBirdModifier();
	}
	
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		resetData();
		super.onPauseGame();
	}
	
	protected void loadKaraokeComplete(){
		Log.d(TAG, "loadKaraokeComplete: ");
		super.loadKaraokeComplete();
	}
	private void resetData() {
		isTouchCamomeLeft = true;
		isTouchKamomeCenter = true;
		isTouchKamomeRight = true;
		isWhaleTouch = true;
		isTouchSun = true;
		if (sSun2!=null) {
			sSun2.clearEntityModifiers();
			sSun2.setRotation(0.0f);
			
		}
		
		if (sKamomeWhaleBefore!=null) {
			sWhaleTide.clearEntityModifiers();
			sWhaleTide.setPosition(1344, 120);
			sKamomeWhaleBefore.clearEntityModifiers();
			sKamomeWhaleBefore.setPosition(1344, 220);
		}
		if (sKaniRight!=null) {
			sKaniRight.clearEntityModifiers();
			sKaniRight.setPosition(900, 585);
		}
		if (sKaniLeft!=null) {
			sKaniLeft.clearEntityModifiers();
			sKaniLeft.setPosition(3, 570);
		}
		return;
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		Log.d(TAG, "ACTION_DOWN");
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			if (sKaniLeft.contains(pSceneTouchEvent.getX() - 10,
					pSceneTouchEvent.getY())) {
				Log.d(TAG, " 1: ");
				kaniLeftMove();
			} else if (sKaniRight.contains(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY())) {
				Log.d(TAG, " 2: ");
				kaniRightMove();
			} else if (checkContains(sCamomeLeft, 87, 30, 166, 274, pX, pY)) {
				Log.d(TAG, " 3: ");
				kamomeLeftAction();
			} else if (checkContains(sKamomeCenter,73,29,134,244,pX,pY)){
				Log.d(TAG, " 4: ");
				kamomeCenterJump();
			} else if (sSun2.contains(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY())) {
				Log.d(TAG, " 5: ");
				sunRotate();
			} else if (sKamomeRight.contains(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY())) {
				Log.d(TAG, " 6: ");
				kamomeRightModifier();
			} else if (checkContains(sKamomeWhaleBefore, 55, 74, 344, 119, pX, pY)){
				kamomeTouchAction();
//			else if (sKamomeWhaleBefore.contains(pSceneTouchEvent.getX(),
//					pSceneTouchEvent.getY())) {
//				Log.d(TAG, " 7: ");
//				kamomeTouchAction();
			} else if (sShip.contains(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY())) {
				if (!sShipSmoke.isVisible()) {
					shipSmoke();
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Initial All Variable
	 */
	private void initial() {
		camomeLeftHandler = new Handler();
		kamomeRightHandler = new Handler();
		kamomeSwimHandler = new Handler();
		kamomeWhaleHandlers = new Handler();
		kamomeMoveHandler = new Handler();
		kamomeShipSmokeHandler = new Handler();
		kamomeCenterHandler = new Handler();
		isTouchCamomeLeft = true;
		isTouchKamomeCenter = true;
		isTouchKamomeRight = true;
		isWhaleTouch = true;
		isTouchSun = true;

		return;
	}

	// /////////////Kani Left Move//////////////
	private void kaniLeftMove() {

		if (sKaniLeft.getX() == 100.0f) {
			mp3KaniWalk.play();

			sKaniLeft
					.registerEntityModifier(kaniLeftModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new MoveXModifier(1,
									100.0f, 3.0f, EaseQuadOut.getInstance()))));
		} else if (sKaniLeft.getX() == 3.0f) {
			mp3KaniWalk.play();

			sKaniLeft
					.registerEntityModifier(kaniLeftModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new MoveXModifier(1,
									3.0f, 100.0f, EaseQuadOut.getInstance()))));
		}
		return;
	}

	// /////////////Kani Right Move//////////////
	private void kaniRightMove() {

		if (sKaniRight.getX() == 900.0f) {
			mp3KaniWalk.play();
			// Set Modifier For Kani
			sKaniRight
					.registerEntityModifier(kaniRightModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new MoveXModifier(1,
									900.0f, 800.0f, EaseQuadOut.getInstance()))));
		} else if (sKaniRight.getX() == 800.0f) {
			mp3KaniWalk.play();
			sKaniRight
					.registerEntityModifier(kaniRightModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new MoveXModifier(1,
									800.0f, 900.0f, EaseQuadOut.getInstance()))));
		}
		return;
	}

	// /////////////Kamome Left Action//////////
	private void kamomeLeftAction() {
		if (isTouchCamomeLeft) {
			mp3KamomeLeft.play();
			isTouchCamomeLeft = false;
			sKamomeAction.setVisible(true);
			sCamomeLeft.setVisible(false);
			camomeLeftHandler.removeCallbacks(camomeLeftCallback);
			camomeLeftHandler.postDelayed(camomeLeftCallback = new Runnable() {

				@Override
				public void run() {
					sKamomeAction.setVisible(false);
					sCamomeLeft.setVisible(true);
					isTouchCamomeLeft = true;
				}
			}, 400);
		}
		return;
	}

	private void kamomeCenterJump() {
		if (isTouchKamomeCenter) {
			mp3KamomeCenter.play();
			sKamomeOpen.setVisible(true);
			sKamomeCenter.setVisible(false);
			isTouchKamomeCenter = false;
			sKamomeOpen
					.registerEntityModifier(kamomeCenterModifier = new SequenceEntityModifier(
							new ParallelEntityModifier(new MoveYModifier(0.4f,
									260.0f, 210.0f, EaseQuadOut.getInstance())),
							new ParallelEntityModifier(new MoveYModifier(0.4f,
									210.0f, 260.0f, EaseQuadOut.getInstance()))));
			kamomeCenterHandler.removeCallbacks(kamomeCenterCallback);
			kamomeCenterHandler.postDelayed(
					kamomeCenterCallback = new Runnable() {

						@Override
						public void run() {
							sKamomeOpen.setVisible(false);
							sKamomeCenter.setVisible(true);
							isTouchKamomeCenter = true;
						}
					}, 800);
		}
		return;
	}

	private void sunRotate() {

		if (isTouchSun) {
			isTouchSun=false;
			mp3Sun.play();
			sSun2.registerEntityModifier(sunModifier = new SequenceEntityModifier(
					new RotationModifier(1.3f, 0.0f, 359.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchSun = true;
						}
					}, EaseLinear.getInstance())));
		}
		return;
	}

	private void kamomeRightModifier() {

		if (isTouchKamomeRight) {
			isTouchKamomeRight = false;
			sKamomeRight
					.registerEntityModifier(kamomeRightModifier = new SequenceEntityModifier(
							new RotationModifier(0.3f, 0.0f, -8.0f, EaseQuadOut
									.getInstance()), 
									new RotationModifier(0.3f,
									-8.0f, 8.0f, EaseQuadOut.getInstance()),
							new RotationModifier(0.3f, 8.0f, 0.0f, EaseQuadOut
									.getInstance())));

			kamomeRightHandler.removeCallbacks(kamomeRightCallback);
			kamomeRightHandler.postDelayed(
					kamomeRightCallback = new Runnable() {

						@Override
						public void run() {
							mp3KamomeSwim.play();

							sKamomeRight.setVisible(false);
							sKamomeJump.setVisible(true);
							sKamomeSwim.setVisible(true);
						}
					}, 1100);

			kamomeSwimHandler.removeCallbacks(kamomeSwimCallback);
			kamomeSwimHandler.postDelayed(kamomeSwimCallback = new Runnable() {

				@Override
				public void run() {
					isTouchKamomeRight = true;
					sKamomeRight.setVisible(true);
					sKamomeJump.setVisible(false);
					sKamomeSwim.setVisible(false);
				}
			}, 2000);
		}
		return;
	}

	private void kamomeWhaleModifier() {

		kamomeWhaleHandlers.removeCallbacks(kamomeWhaleCallback);
		if (sKamomeWhaleBefore.getX() <= -sKamomeWhaleBefore.getWidth() + 10) {
			fromXWhale = 1344;
		} else {
			fromXWhale = sKamomeWhaleBefore.getX();
		}

		if (sWhaleTide.getX() <= -sKamomeWhaleBefore.getWidth() + 10) {
			fromXWhaleTide = 1344;
		} else {
			fromXWhaleTide = sWhaleTide.getX();
		}
		if (fromXWhale <= -sKamomeWhaleBefore.getWidth() + 10) {
			kamomeWhaleModifier();
		}

		sKamomeWhaleBefore
				.registerEntityModifier(kamomeWhaleBeforeModifier = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveXModifier((Math
								.abs(fromXWhale) + sKamomeWhaleBefore
								.getWidth()) * 14 / 1344, fromXWhale,
								-sKamomeWhaleBefore.getWidth()))));

		sWhaleTide
				.registerEntityModifier(kamomeWhaleModifier = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveXModifier(
								(Math.abs(fromXWhaleTide) + sWhaleTide
										.getWidth()) * 14 / 1344,
								fromXWhaleTide, -sWhaleTide.getWidth()))));

		kamomeWhaleHandlers.postDelayed(kamomeWhaleCallback = new Runnable() {

			@Override
			public void run() {
				kamomeWhaleModifier();
			}
		}, (long) (kamomeWhaleBeforeModifier.getDuration() * 1000));

	}

	private void kamomeTouchAction() {
		if (isWhaleTouch) {
			mp3BigTide.play();
			isWhaleTouch = false;
			sKamomeWhaleBefore.setVisible(false);
			sWhaleTide.setVisible(true);
			sKamomeWhaleBefore.clearEntityModifiers();
			sWhaleTide.clearEntityModifiers();
			kamomeMoveHandler.removeCallbacks(kamomeMoveCallback);
			kamomeMoveHandler.postDelayed(kamomeMoveCallback = new Runnable() {

				@Override
				public void run() {
					isWhaleTouch = true;
					sKamomeWhaleBefore.setVisible(true);
					sWhaleTide.setVisible(false);
					kamomeWhaleModifier();
				}
			}, 1500);
		}
	}

	private void kamomeShipModifier() {
		sShip.registerEntityModifier((IEntityModifier) (kamomeShipModifier = new LoopEntityModifier(
				new SequenceEntityModifier(new ParallelEntityModifier(
						new MoveYModifier(2.5f, 105.0f, 100.0f, EaseQuadOut
								.getInstance())), new ParallelEntityModifier(
						new MoveYModifier(2.5f, 100.0f, 105.0f, EaseQuadOut
								.getInstance()))))));
	}

	private void shipSmoke() {

		mp3Trumpetshort.play();

		sShipSmoke.setVisible(true);
		kamomeShipSmokeHandler.removeCallbacks(kamomeShipSmokeCallback);
		kamomeShipSmokeHandler.postDelayed(
				kamomeShipSmokeCallback = new Runnable() {

					@Override
					public void run() {
						sShipSmoke.setVisible(false);
					}
				}, 1000);
		return;
	}

	private void kamomeBirdModifier() {
		sKamomeLeft
				.registerEntityModifier((IEntityModifier) (kamomeBirdLeftModifier = new LoopEntityModifier(
						new SequenceEntityModifier(new ParallelEntityModifier(
								new MoveXModifier(2.5f, sKamomeLeft.getX(),
										sKamomeLeft.getX() + 100, EaseQuadOut
												.getInstance())),
								new ParallelEntityModifier(new MoveXModifier(
										2.5f, sKamomeLeft.getX() + 100,
										sKamomeLeft.getX(), EaseQuadOut
												.getInstance()))))));

		sKamome2Right
				.registerEntityModifier((IEntityModifier) (kamomeBirdRightModifier = new LoopEntityModifier(
						new SequenceEntityModifier(new ParallelEntityModifier(
								new MoveXModifier(2.2f, sKamome2Right.getX(),
										sKamome2Right.getX() - 100, EaseQuadOut
												.getInstance())),
								new ParallelEntityModifier(new MoveXModifier(
										2.2f, sKamome2Right.getX() - 100,
										sKamome2Right.getX(), EaseQuadOut
												.getInstance()))))));
		return;
	}

	@Override
	public void combineGimmic3WithAction() {
		kamomeLeftAction();
	}

	

	
}