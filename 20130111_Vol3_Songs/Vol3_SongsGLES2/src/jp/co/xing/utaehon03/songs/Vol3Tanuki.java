/* Vol3Tanuki.java
 * Create on Feb 27, 2012
 */
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TanukiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.os.Handler;
import android.util.Log;

public class Vol3Tanuki extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private TexturePack tanukiPack1, tanukiPack2;
	private TexturePackTextureRegionLibrary libTanukiPack1, libTanukiPack2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private ITextureRegion ttrBackground, ttrYama, ttrJimen, ttrPlate, ttrDai1,
			ttrDai2, ttrDai3, ttrCloudLeft, ttrCloudRight;
	private ITextureRegion ttrArrDango[] = new ITextureRegion[10];
	private Sprite sYama, sJimen, sPlate, sDai1, sDai2, sDai3, sCloudLeft,
			sCloudRight;
	private Sprite sArrDango[] = new Sprite[10];
	private TiledTextureRegion tttrSusuki, tttrRabit, tttrMama, tttrPapa,
			tttrAni, tttrOtouto;
	private AnimatedSprite aniSusuki, aniRabit, aniMama, aniPapa, aniAni,
			aniOtouto;

	private int currentDai = 0;
	private int currentDango = 0;
	private float arrPointDango[][] = new float[][] {
			{ 67, 88, 109, 131, 78, 99, 120, 88, 109, 99 },
			{ 518, 518, 518, 518, 499, 499, 499, 481, 481, 462 } };
	private boolean istouchMama = true;
	private boolean istouchOtouto = true;
	private boolean istouchAni = true;
	private boolean istouchPapa1 = true;
	private boolean istouchPapa2 = true;
	private boolean istouchRabit = true;
	private boolean istouchTmpPapa = true;
	private Handler handlerAni, handlerPapa1, handlerAni1;
	private Runnable callAni, callBackPapa1, callAni1;
	/**
	 * Sounds
	 */
	private Sound E00053_TANUKI_PYON, E00080_TANUKI_BOMI, E00145_BO2,
			E00146_CON2, E00147_BOWAN2, E00148_CYUCYU, E0056_POWA;

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, ttrBackground, this
						.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.sYama = new Sprite(0, 0, ttrYama,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sYama);
		this.aniSusuki = new AnimatedSprite((float) 22.817, (float) 360.332,
				tttrSusuki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniSusuki);
		aniSusuki.animate(700);
		this.sJimen = new Sprite(0, (float) 450.534, ttrJimen,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sJimen);
		this.sDai1 = new Sprite((float) 68.633, (float) 145.334, ttrDai1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sDai1);
		sDai1.setVisible(false);
		this.sDai2 = new Sprite((float) 68.633, (float) 145.334, ttrDai2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sDai2);
		sDai2.setVisible(false);
		this.sDai3 = new Sprite((float) 68.633, (float) 145.334, ttrDai3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sDai3);
		sDai3.setVisible(false);
		this.aniRabit = new AnimatedSprite((float) 289.466, 0, tttrRabit,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniRabit);
		this.sCloudLeft = new Sprite((float) 115.300, (float) 29.501,
				ttrCloudLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCloudLeft);
		this.sCloudRight = new Sprite((float) 666.966, (float) 92.001,
				ttrCloudRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCloudRight);
		this.aniMama = new AnimatedSprite((float) 68.633, (float) 240.334,
				tttrMama, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniMama);
		this.aniPapa = new AnimatedSprite(608, 157, tttrPapa,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPapa);
		this.aniAni = new AnimatedSprite((float) 455.3, (float) 356.168,
				tttrAni, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniAni);
		this.aniOtouto = new AnimatedSprite((float) 325.3, (float) 342.834,
				tttrOtouto, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOtouto);
		for (int k = 0; k < 10; k++) {
			this.sArrDango[k] = new Sprite(arrPointDango[0][k],
					arrPointDango[1][k], this.ttrArrDango[k],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(sArrDango[k]);
			this.sArrDango[k].setVisible(false);
		}
		this.sPlate = new Sprite((float) 59.466, 537, this.ttrPlate,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sPlate);
		addGimmicsButton(mScene, Vol3TanukiResource.SOUND_GIMMIC,
				Vol3TanukiResource.IMAGE_GIMMIC, libTanukiPack1);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("tanuki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tanuki/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "tanuki/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		tanukiPack1 = mTexturePackLoaderFromSource.load("tanuki1.xml");
		tanukiPack1.loadTexture();
		tanukiPack2 = mTexturePackLoaderFromSource.load("tanuki2.xml");
		tanukiPack2.loadTexture();
		libTanukiPack1 = tanukiPack1.getTexturePackTextureRegionLibrary();
		libTanukiPack2 = tanukiPack2.getTexturePackTextureRegionLibrary();

		this.ttrBackground = libTanukiPack2
				.get(Vol3TanukiResource.A5_13_1_IPHONE4_HAIKEI_ID);
		this.ttrYama = libTanukiPack2
				.get(Vol3TanukiResource.A5_13_2_IPHONE4_HAIKEI_YAMA_ID);
		this.ttrJimen = libTanukiPack2
				.get(Vol3TanukiResource.A5_13_3_IPHONE4_HAIKEI_JIMEN_ID);
		this.ttrCloudLeft = libTanukiPack1
				.get(Vol3TanukiResource.A5_12_1_IPHONE4_CLOUD_LEFT_ID);
		this.ttrCloudRight = libTanukiPack1
				.get(Vol3TanukiResource.A5_12_2_IPHONE4_CLOUD_RIGHT_ID);
		this.tttrSusuki = getTiledTextureFromPacker(tanukiPack2,
				Vol3TanukiResource.A5_11_1_IPHONE4_SUSUKI_ID,
				Vol3TanukiResource.A5_11_2_IPHONE4_SUSUKI_ID);
		this.tttrRabit = getTiledTextureFromPacker(tanukiPack2,
				Vol3TanukiResource.PACK_RABBIT);
		this.tttrMama = getTiledTextureFromPacker(tanukiPack1,
				Vol3TanukiResource.A5_05_1_IPHONE4_MAMA_ID,
				Vol3TanukiResource.A5_05_2_IPHONE4_MAMA_ACTION_ID);
		this.tttrPapa = getTiledTextureFromPacker(tanukiPack1,
				Vol3TanukiResource.PACK_PAPA);
		this.tttrAni = getTiledTextureFromPacker(tanukiPack1,
				Vol3TanukiResource.PACK_ANI);
		this.tttrOtouto = getTiledTextureFromPacker(tanukiPack1,
				Vol3TanukiResource.PACK_OTOUTO);
		this.ttrPlate = libTanukiPack1
				.get(Vol3TanukiResource.A5_10_1_IPHONE4_PLATE_ID);
		this.ttrDai1 = libTanukiPack1
				.get(Vol3TanukiResource.A5_03_1_IPHONE4_DAI_ID);
		this.ttrDai2 = libTanukiPack1
				.get(Vol3TanukiResource.A5_03_2_IPHONE4_DAI_ID);
		this.ttrDai3 = libTanukiPack1
				.get(Vol3TanukiResource.A5_03_3_IPHONE4_DAI_ID);
		for (int k = 0; k < 10; k++) {
			this.ttrArrDango[k] = libTanukiPack1
					.get(Vol3TanukiResource.A5_10_2_IPHONE4_DANGO_ID);
		}
	}

	@Override
	protected void loadKaraokeSound() {
		E00053_TANUKI_PYON = loadSoundResourceFromSD(Vol3TanukiResource.E00053_TANUKI_PYON);
		E00080_TANUKI_BOMI = loadSoundResourceFromSD(Vol3TanukiResource.E00080_TANUKI_BOMI);
		E00145_BO2 = loadSoundResourceFromSD(Vol3TanukiResource.E00145_BO2);
		E00146_CON2 = loadSoundResourceFromSD(Vol3TanukiResource.E00146_CON2);
		E00147_BOWAN2 = loadSoundResourceFromSD(Vol3TanukiResource.E00147_BOWAN2);
		E00148_CYUCYU = loadSoundResourceFromSD(Vol3TanukiResource.E00148_CYUCYU);
		E0056_POWA = loadSoundResourceFromSD(Vol3TanukiResource.E0056_POWA);
	}

	@Override
	public synchronized void onResumeGame() {
		loadDefault();
		clearData();
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.d("Vol3Tanuki: ", "loadKaraokeComplete ");
		super.loadKaraokeComplete();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		if (callAni != null) {
			handlerAni.removeCallbacks(callAni);
		}
		if (callBackPapa1 != null) {
			handlerPapa1.removeCallbacks(callBackPapa1);
		}
		if (callAni1 != null) {
			handlerAni1.removeCallbacks(callAni1);
		}
		if (aniPapa != null) {
			aniPapa.stopAnimation();
			aniPapa.setCurrentTileIndex(0);
		}
		if (aniAni != null) {
			aniAni.stopAnimation();
			aniAni.setVisible(true);
			aniAni.setCurrentTileIndex(0);
		}
		if (aniOtouto != null) {
			aniOtouto.stopAnimation();
			aniOtouto.setVisible(true);
			aniOtouto.setCurrentTileIndex(0);
		}
		if (aniMama != null) {
			aniMama.stopAnimation();
			aniMama.setCurrentTileIndex(0);
		}
	}

	private void clearData() {
		// remove dango
		for (int k = 0; k < 10; k++) {
			if (sArrDango[k] != null) {
				sArrDango[k].setVisible(false);
			}
		}
		currentDango = 0;
		// remove dai
		sDai1.setVisible(false);
		sDai2.setVisible(false);
		sDai3.setVisible(false);
		currentDai = 0;
		istouchMama = true;
		istouchOtouto = true;
		istouchAni = true;
		istouchPapa1 = true;
		istouchPapa2 = true;
		istouchRabit = true;
		istouchTmpPapa = true;
	}

	public void loadDefault() {
		try {
			handlerAni = new Handler();
			handlerPapa1 = new Handler();
			handlerAni1 = new Handler();
			moveCloud();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(aniRabit, 20, 1, 366, 262, pX, pY)) {
				if (istouchRabit) {
					istouchRabit = false;
					touchAnimate(1);
				}
			} else if (checkContains(sPlate, 3, 3, 106, 80, pX, pY)) {
				currentDango++;
				displayDango(currentDango);
				if (currentDango == 10) {
					currentDango = -1;
				}
			} else if (checkContains(aniOtouto, 6, 20, 116, 190, pX, pY)) {
				if (istouchOtouto) {
					istouchOtouto = false;
					istouchMama = false;
					touchAnimate(2);
				}
			} else if (checkContains(aniMama, 40, 42, 218, 320, pX, pY)) {
				if (istouchMama) {
					istouchMama = false;
					istouchOtouto = false;
					touchAnimate(3);
					mEngine.registerUpdateHandler(new TimerHandler(2.2f,
							new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler arg0) {
									istouchMama = true;
									istouchOtouto = true;

								}
							}));
				}
			} else if (checkContains(aniPapa, 30, 65, 184, 183, pX, pY)) {
				if (istouchPapa1) {
					handlerAni.removeCallbacks(callAni);
					handlerPapa1.removeCallbacks(callBackPapa1);
					handlerAni1.removeCallbacks(callAni1);
					Log.i("vol3Tanuki", "tap vao dau bo:");
					istouchPapa1 = false;
					istouchPapa2 = false;
					istouchTmpPapa = false;
					istouchAni = true;
					touchAnimate(4);
				}
			} else if (checkContains(aniPapa, 78, 214, 198, 440, pX, pY)) {
				if (istouchPapa2 == true && istouchTmpPapa == true) {
					handlerAni1.removeCallbacks(callAni1);
					handlerAni.removeCallbacks(callAni);
					Log.i("vol3Tanuki", "tap vao nguoi bo:");
					istouchPapa2 = false;
					istouchAni = false;
					istouchTmpPapa = false;
					aniPapa.setCurrentTileIndex(4);
					touchAnimate(5);
					handlerPapa1.removeCallbacks(callBackPapa1);
					handlerPapa1.postDelayed(callBackPapa1 = new Runnable() {
						@Override
						public void run() {
							istouchPapa2 = true;
							istouchAni = true;
							istouchTmpPapa = true;
						}
					}, 2200);
				}
			} else if (checkContains(aniAni, 42, 42, 160, 235, pX, pY)) {
				if (istouchAni) {
					istouchAni = false;
					istouchPapa2 = false;
					touchAnimate(6);
				}
			} else if (checkContains(sYama, 54, 182, 222, 268, pX, pY)) {
				displayDai();
			}
		}
		return false;
	}

	private void touchAnimate(int id) {
		switch (id) {
		case 1:
			long pDurationRabit[] = new long[] { 100, 100 };
			int pFrameRabit[] = new int[] { 1, 2 };
			E00146_CON2.play();
			aniRabit.animate(pDurationRabit, pFrameRabit, 0, this);
			break;
		case 2:
			long pDuration[] = new long[] { 200, 200 };
			int pFrame[] = new int[] { 1, 2 };
			E00148_CYUCYU.play();
			aniOtouto.animate(pDuration, pFrame, 1, this);
			break;
		case 3:
			E00053_TANUKI_PYON.play();
			aniOtouto.setVisible(false);
			aniMama.setCurrentTileIndex(1);
			mEngine.registerUpdateHandler(new TimerHandler(2.0f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							aniMama.setCurrentTileIndex(0);
							aniOtouto.setVisible(true);
						}
					}));
			break;
		case 4:
			// tap vao dau papa
			long pDurationPapa1[] = new long[] { 1000, 1000, 1000 };
			int pFramePapa1[] = new int[] { 1, 2, 3 };
			E00147_BOWAN2.play();
			mEngine.registerUpdateHandler(new TimerHandler(2.0f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							E00147_BOWAN2.play();
						}
					}));
			aniAni.setVisible(true);
			aniPapa.animate(pDurationPapa1, pFramePapa1, 0, this);
			break;
		case 5:
			// tap vao nguoi papa
			E00053_TANUKI_PYON.play();
			aniAni.setVisible(false);
			handlerAni.removeCallbacks(callAni);
			handlerAni.postDelayed(callAni = new Runnable() {
				@Override
				public void run() {
					aniPapa.setCurrentTileIndex(0);
					aniAni.setVisible(true);
				}
			}, 2000);
			break;
		case 6:
			// tap ani
			long pDurationAni[] = new long[] { 600, 600 };
			int pFrameAni[] = new int[] { 1, 2 };
			E00080_TANUKI_BOMI.play();
			handlerAni1.removeCallbacks(callAni1);
			handlerAni1.postDelayed(callAni1 = new Runnable() {
				@Override
				public void run() {
					istouchPapa2 = true;
				}
			}, 1200);
			aniAni.animate(pDurationAni, pFrameAni, 0, this);
			break;
		default:
			break;
		}
	}

	private void displayDango(int index) {
		E0056_POWA.play();
		if (index == 0) {
			for (int k = 0; k < 10; k++) {
				sArrDango[k].setVisible(false);
			}
		} else {
			sArrDango[index - 1].setVisible(true);
		}
	}

	private void displayDai() {
		E00145_BO2.play();
		if (currentDai == 0) {
			sDai1.setVisible(true);
			currentDai = 1;
		} else if (currentDai == 1) {
			sDai2.setVisible(true);
			currentDai = 2;
		} else if (currentDai == 2) {
			sDai3.setVisible(true);
			currentDai = 3;
		} else if (currentDai == 3) {
			sDai1.setVisible(false);
			sDai2.setVisible(false);
			sDai3.setVisible(false);
			currentDai = 0;
		}
	}

	public void moveCloud() {
		sCloudLeft.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(1.2f, sCloudLeft
						.getX(), sCloudLeft.getX() + 80), new MoveXModifier(
						1.2f, sCloudLeft.getX() + 80, sCloudLeft.getX()),
						new MoveXModifier(0.8f, sCloudLeft.getX(), sCloudLeft
								.getX() - 40), new MoveXModifier(0.8f,
								sCloudLeft.getX() - 40, sCloudLeft.getX()))));
		sCloudRight.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(1.2f, sCloudRight
						.getX(), sCloudRight.getX() + 80), new MoveXModifier(
						1.2f, sCloudRight.getX() + 80, sCloudRight.getX()),
						new MoveXModifier(0.8f, sCloudRight.getX(), sCloudRight
								.getX() - 40), new MoveXModifier(0.8f,
								sCloudRight.getX() - 40, sCloudRight.getX()))));
	}

	@Override
	public void combineGimmic3WithAction() {
		displayDai();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(aniOtouto)) {
			aniOtouto.setCurrentTileIndex(0);
			istouchMama = true;
			istouchOtouto = true;
		}
		if (pAnimatedSprite.equals(aniAni)) {
			aniAni.setCurrentTileIndex(0);
			istouchAni = true;
		}
		if (pAnimatedSprite.equals(aniRabit)) {
			aniRabit.setCurrentTileIndex(0);
			istouchRabit = true;
		}
		if (pAnimatedSprite.equals(aniPapa)) {
			aniPapa.setCurrentTileIndex(0);
			istouchPapa1 = true;
			istouchPapa2 = true;
			istouchTmpPapa = true;
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
