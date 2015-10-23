package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.SpritePool;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MusundeResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Musunde extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private static final String TAG = "LOG_MUSUNDE";

	private TexturePack musundePack1, musundePack2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary libMusundePack1;
	private TextureRegion ttrRWorm, ttrRBeetle, ttrRAngel;

	private TiledTextureRegion tlTtrRBackground, tlTtrRFlower01,
			tlTtrRFlower02, tlTtrRFlower03, tlTtrRFlower04, tlTtrRFlower05,
			tlTtrRFlower06, tlTtrRTulip01, tlTtrRTulip02, tlTtrRTulip03,
			tlTtrRTulip04, tlTtrRTulip05, tlTtrRTulip06, tlTtrRPerson01,
			tlTtrRPerson02, tlTtrRPerson03, tlTtrRPerson04;

	private AnimatedSprite anmSprBackground, anmSprFlower01, anmSprFlower02,
			anmSprFlower03, anmSprFlower04, anmSprFlower05, anmSprFlower06,
			anmSprTulip01, anmSprTulip02, anmSprTulip03, anmSprTulip04,
			anmSprTulip05, anmSprTulip06, anmSprPerson01, anmSprPerson02,
			anmSprPerson03, anmSprPerson04;

	private SpritePool wormPool, beetlePool, angelPool;

	private boolean isClappedOnce = false, resetIUpdateHandler = false,
			isModifiedObject = false, isTulip01Blossom = false,
			isTulip02Blossom = false, isTulip03Blossom = false,
			isTulip04Blossom = false, isTulip05Blossom = false,
			isTulip06Blossom = false;

	private float spcFlower = 95f, spcTulip = 0.5f;

	private final float speed = 1f;

	private int tmpTulip = 1;

	private IUpdateHandler autoTranslate;

	private float posFlowers[][] = new float[][] { { 900, 10 }, { 1090, 10 },
			{ 1280, 10 }, { 1470, 10 }, { 1660, 10 }, { 1850, 10 } };

	private float posTulips[][] = new float[][] { { 30, 220 }, { 200, 220 },
			{ 370, 220 }, { 540, 220 }, { 710, 220 }, { 880, 220 } };

	/**
	 * All Sound
	 */
	private Sound oggBgr, oggPerson, oggTulip;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("musunde/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("musunde/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "musunde/gfx/");
		super.onCreateResources();
		Log.i(TAG, "onLoadResources");
	}

	@Override
	protected void loadKaraokeResources() {
		Log.i(TAG, "loadKaraokeResources");
		musundePack1 = mTexturePackLoaderFromSource.load("musunde1.xml");
		musundePack1.loadTexture();
		musundePack2 = mTexturePackLoaderFromSource.load("musunde2.xml");
		musundePack2.loadTexture();
		libMusundePack1 = musundePack1.getTexturePackTextureRegionLibrary();
		/** Load Background */
		this.tlTtrRBackground = getTiledTextureFromPacker(musundePack2,
				Vol3MusundeResource.PACK_HAIKEI);
		/** Load Flowers */
		this.tlTtrRFlower01 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);
		this.tlTtrRFlower02 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);

		this.tlTtrRFlower03 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);
		this.tlTtrRFlower04 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);
		this.tlTtrRFlower05 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);
		this.tlTtrRFlower06 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_FLOWER);
		/** Load Tulips */
		this.tlTtrRTulip01 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		this.tlTtrRTulip02 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		this.tlTtrRTulip03 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		this.tlTtrRTulip04 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		this.tlTtrRTulip05 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		this.tlTtrRTulip06 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_BUD);
		/** Load People */
		this.tlTtrRPerson01 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_MUSUBU);
		this.tlTtrRPerson02 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_MUSUBU);
		this.tlTtrRPerson03 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_MUSUBU);
		this.tlTtrRPerson04 = getTiledTextureFromPacker(musundePack1,
				Vol3MusundeResource.PACK_TULIP_MUSUBU);
		/** Load Worm */
		this.ttrRWorm = libMusundePack1
				.get(Vol3MusundeResource.A3_12_IPHONE4_IMOMUSHI_ID);
		/** Load Beetle */
		this.ttrRBeetle = libMusundePack1
				.get(Vol3MusundeResource.A3_13_IPHONE4_TENTOUMUSI_ID);
		/** Load Angel */
		this.ttrRAngel = libMusundePack1
				.get(Vol3MusundeResource.A3_11_IPHONE4_FAIRY_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		Log.i(TAG, "loadKaraokeSound");
		/** Load Sounds */
		oggBgr = loadSoundResourceFromSD(Vol3MusundeResource.E10_0064_HAIKEI);
		oggPerson = loadSoundResourceFromSD(Vol3MusundeResource.E10_0063_MOVE);
		oggTulip = loadSoundResourceFromSD(Vol3MusundeResource.E10_0062_KIRAKIRA);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.i(TAG, "loadKaraokeScene");
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();

		/** Add Background */
		this.anmSprBackground = new AnimatedSprite(0, 0, this.tlTtrRBackground,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprBackground);

		/** Add Flowers */
		this.anmSprFlower01 = new AnimatedSprite(posFlowers[0][0],
				posFlowers[0][1], this.tlTtrRFlower01,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower01);
		this.anmSprFlower02 = new AnimatedSprite(posFlowers[1][0],
				posFlowers[1][1], this.tlTtrRFlower02,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower02);
		this.anmSprFlower03 = new AnimatedSprite(posFlowers[2][0],
				posFlowers[2][1], this.tlTtrRFlower03,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower03);
		this.anmSprFlower04 = new AnimatedSprite(posFlowers[3][0],
				posFlowers[3][1], this.tlTtrRFlower04,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower04);
		this.anmSprFlower05 = new AnimatedSprite(posFlowers[4][0],
				posFlowers[4][1], this.tlTtrRFlower05,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower05);
		this.anmSprFlower06 = new AnimatedSprite(posFlowers[5][0],
				posFlowers[5][1], this.tlTtrRFlower06,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower06);

		/** Add Tulips */
		this.anmSprTulip01 = new AnimatedSprite(posTulips[0][0],
				posTulips[0][1], this.tlTtrRTulip01,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip01);
		this.anmSprTulip02 = new AnimatedSprite(posTulips[1][0],
				posTulips[1][1], this.tlTtrRTulip02,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip02);
		this.anmSprTulip03 = new AnimatedSprite(posTulips[2][0],
				posTulips[2][1], this.tlTtrRTulip03,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip03);
		this.anmSprTulip04 = new AnimatedSprite(posTulips[3][0],
				posTulips[3][1], this.tlTtrRTulip04,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip04);
		this.anmSprTulip05 = new AnimatedSprite(posTulips[4][0],
				posTulips[4][1], this.tlTtrRTulip05,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip05);
		this.anmSprTulip06 = new AnimatedSprite(posTulips[5][0],
				posTulips[5][1], this.tlTtrRTulip06,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTulip06);

		/** Add People */
		this.anmSprPerson01 = new AnimatedSprite(20, 310, this.tlTtrRPerson01,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprPerson01);
		this.anmSprPerson02 = new AnimatedSprite(250, 310, this.tlTtrRPerson02,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprPerson02);
		this.anmSprPerson03 = new AnimatedSprite(480, 310, this.tlTtrRPerson03,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprPerson03);
		this.anmSprPerson04 = new AnimatedSprite(710, 310, this.tlTtrRPerson04,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprPerson04);

		/** Add Three Gimmic Buttons */
		addGimmicsButton(mScene, Vol3MusundeResource.SOUND_GIMMIC,
				Vol3MusundeResource.IMAGE_GIMMIC, libMusundePack1);
//		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
//		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public synchronized void onResumeGame() {
		loadDefault();
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.i(TAG, "loadKaraokeComplete");
		super.loadKaraokeComplete();
	}

	public void loadDefault() {
		anmSprPerson02.setCurrentTileIndex(1);
		anmSprPerson03.setCurrentTileIndex(2);
		anmSprPerson04.setCurrentTileIndex(3);
		anmSprPerson01.setZIndex(3);
		anmSprPerson02.setZIndex(3);
		anmSprPerson03.setZIndex(3);
		anmSprPerson04.setZIndex(3);

		anmSprFlower02.setCurrentTileIndex(2);
		anmSprFlower03.setCurrentTileIndex(4);
		anmSprFlower04.setCurrentTileIndex(6);
		anmSprFlower05.setCurrentTileIndex(8);
		anmSprFlower06.setCurrentTileIndex(10);

		anmSprTulip02.setCurrentTileIndex(1);
		anmSprTulip03.setCurrentTileIndex(2);
		anmSprTulip04.setCurrentTileIndex(3);
		anmSprTulip05.setCurrentTileIndex(4);
		anmSprTulip06.setCurrentTileIndex(5);
		anmSprTulip01.setZIndex(2);
		anmSprTulip02.setZIndex(2);
		anmSprTulip03.setZIndex(2);
		anmSprTulip04.setZIndex(2);
		anmSprTulip05.setZIndex(2);
		anmSprTulip06.setZIndex(2);

		sprGimmic[0].setZIndex(5);
		sprGimmic[1].setZIndex(5);
		sprGimmic[2].setZIndex(5);

		if (!isModifiedObject) {
			modifyObjects();
		}

		wormPool = new SpritePool(ttrRWorm, this.getVertexBufferObjectManager());
		beetlePool = new SpritePool(ttrRBeetle,
				this.getVertexBufferObjectManager());
		angelPool = new SpritePool(ttrRAngel,
				this.getVertexBufferObjectManager());
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (anmSprPerson01.contains(pX - 50, pY)
					|| anmSprPerson02.contains(pX, pY)
					|| anmSprPerson03.contains(pX, pY)
					|| anmSprPerson04.contains(pX + 50, pY)) {
				if ((pX > 201 && pX < 261) || (pX > 412 && pX < 492)
						|| (pX > 653 && pX < 793)) {
					changeBackground();
				} else {
					peopleClap();
				}
			} else if (anmSprTulip01.contains(pX, pY)) {
				onTouchTulip(0);
				isTulip01Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip01Blossom = false;
							}
						}));

			} else if (anmSprTulip02.contains(pX, pY)) {
				onTouchTulip(1);
				isTulip02Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip02Blossom = false;
							}
						}));

			} else if (anmSprTulip03.contains(pX, pY)) {
				onTouchTulip(2);
				isTulip03Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip03Blossom = false;
							}
						}));

			} else if (anmSprTulip04.contains(pX, pY)) {
				onTouchTulip(3);
				isTulip04Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip04Blossom = false;
							}
						}));
			} else if (anmSprTulip05.contains(pX, pY)) {
				onTouchTulip(4);
				isTulip05Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip05Blossom = false;
							}
						}));
			} else if (anmSprTulip06.contains(pX, pY)) {
				onTouchTulip(5);
				isTulip06Blossom = true;
				mEngine.registerUpdateHandler(new TimerHandler(2.1f,
						new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								isTulip06Blossom = false;
							}
						}));
			} else if (anmSprBackground.contains(pX, pY)) {
				changeBackground();
			}
		}
		return false;
	}

	@Override
	public void onPauseGame() {
		resetGameStatus();
		super.onPauseGame();
	}

	private void resetGameStatus() {
		// if (!getActivity().isFinishing()) {
		try {
			anmSprBackground.setCurrentTileIndex(0);

			anmSprPerson01.setCurrentTileIndex(0);
			anmSprPerson02.setCurrentTileIndex(1);
			anmSprPerson03.setCurrentTileIndex(2);
			anmSprPerson04.setCurrentTileIndex(3);

			anmSprFlower01.setCurrentTileIndex(0);
			anmSprFlower02.setCurrentTileIndex(2);
			anmSprFlower03.setCurrentTileIndex(4);
			anmSprFlower04.setCurrentTileIndex(6);
			anmSprFlower05.setCurrentTileIndex(8);
			anmSprFlower06.setCurrentTileIndex(10);
			anmSprFlower01.setPosition(posFlowers[0][0], posFlowers[0][1]);
			anmSprFlower02.setPosition(posFlowers[1][0], posFlowers[1][1]);
			anmSprFlower03.setPosition(posFlowers[2][0], posFlowers[2][1]);
			anmSprFlower04.setPosition(posFlowers[3][0], posFlowers[3][1]);
			anmSprFlower05.setPosition(posFlowers[4][0], posFlowers[4][1]);
			anmSprFlower06.setPosition(posFlowers[5][0], posFlowers[5][1]);

			anmSprTulip01.setCurrentTileIndex(0);
			anmSprTulip02.setCurrentTileIndex(1);
			anmSprTulip03.setCurrentTileIndex(2);
			anmSprTulip04.setCurrentTileIndex(3);
			anmSprTulip05.setCurrentTileIndex(4);
			anmSprTulip06.setCurrentTileIndex(5);
			anmSprTulip01.setPosition(posTulips[0][0], posTulips[0][1]);
			anmSprTulip02.setPosition(posTulips[1][0], posTulips[1][1]);
			anmSprTulip03.setPosition(posTulips[2][0], posTulips[2][1]);
			anmSprTulip04.setPosition(posTulips[3][0], posTulips[3][1]);
			anmSprTulip05.setPosition(posTulips[4][0], posTulips[4][1]);
			anmSprTulip06.setPosition(posTulips[5][0], posTulips[5][1]);

			wormPool = new SpritePool(ttrRWorm,
					this.getVertexBufferObjectManager());
			beetlePool = new SpritePool(ttrRBeetle,
					this.getVertexBufferObjectManager());
			angelPool = new SpritePool(ttrRAngel,
					this.getVertexBufferObjectManager());

			tmpTulip = 1;
			resetIUpdateHandler = true;

			Log.i(TAG, "Initialize game status");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		return;
	}

	@Override
	public void combineGimmic3WithAction() {
		if (tmpTulip > 5) {
			tmpTulip = 0;
		}
		switch (tmpTulip) {
		case 0:
			if (isTulip01Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		case 1:
			if (isTulip02Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		case 2:
			if (isTulip03Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		case 3:
			if (isTulip04Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		case 4:
			if (isTulip05Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		case 5:
			if (isTulip06Blossom) {
				tmpTulip = getLastAvaiTulip(tmpTulip);
			}
			break;
		}
		onForceTouchTulip(tmpTulip);
		tmpTulip++;
	}

	private int getLastAvaiTulip(int id) {
		if (id == 5) {
			id = 0;
		} else {
			id = id + 1;
		}
		switch (id) {
		case 0:
			if (isTulip01Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		case 1:
			if (isTulip02Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		case 2:
			if (isTulip03Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		case 3:
			if (isTulip04Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		case 4:
			if (isTulip05Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		case 5:
			if (isTulip06Blossom) {
				id = getLastAvaiTulip(id);
			}
			break;
		default:
			id = id + 1;
			break;
		}
		return id;
	}

	private void onTouchTulip(final int id) {
		AnimatedSprite sp = null;
		switch (id) {
		case 0:
			sp = anmSprTulip01;
			break;
		case 1:
			sp = anmSprTulip02;
			break;
		case 2:
			sp = anmSprTulip03;
			break;
		case 3:
			sp = anmSprTulip04;
			break;
		case 4:
			sp = anmSprTulip05;
			break;
		case 5:
			sp = anmSprTulip06;
			break;
		}
		final int curTile = sp.getCurrentTileIndex();
		final AnimatedSprite spr = sp;
		if (curTile < 6) {
			oggTulip.play();
			tulipChangeS(spr, curTile + 6, curTile);
			showAnimal(spr.getX());
		}
		return;
	}

	private void onForceTouchTulip(final int id) {
		AnimatedSprite sp = null;
		switch (id) {
		case 0:
			sp = anmSprTulip01;
			break;
		case 1:
			sp = anmSprTulip02;
			break;
		case 2:
			sp = anmSprTulip03;
			break;
		case 3:
			sp = anmSprTulip04;
			break;
		case 4:
			sp = anmSprTulip05;
			break;
		case 5:
			sp = anmSprTulip06;
			break;
		}
		final int curTile = sp.getCurrentTileIndex();
		oggTulip.play();
		if (curTile < 6) {
			tulipChange(sp, curTile + 6, curTile);
		} else {
			tulipChange(sp, curTile, curTile - 6);
		}
		showAnimal(sp.getX());
		return;
	}

	private void tulipChange(AnimatedSprite sp, int curTile, int nextTile) {
		long pDuration[] = new long[] { 2200, 100 };
		int pFrams[] = new int[] { curTile, nextTile };
		sp.animate(pDuration, pFrams, 0, this);
		return;
	}

	private void tulipChangeS(AnimatedSprite sp, int curTile, int nextTile) {
		long pDuration[] = new long[] { 2200, 100 };
		int pFrams[] = new int[] { curTile, nextTile };
		sp.animate(pDuration, pFrams, 0, this);
		return;
	}

	private void showAnimal(final float pos) {
		int rd = new Random().nextInt(3);
		switch (rd) {
		case 0:
			showWorm(pos);
			break;
		case 1:
			showBeetle(pos);
			break;
		case 2:
			showAngel(pos);
			break;
		}
		return;
	}

	private void showWorm(final float p) {
		final Sprite newWorm = wormPool.obtainPoolItem();
		this.mScene.attachChild(newWorm);
		newWorm.setPosition(p, posTulips[5][1] - 80);
		newWorm.setZIndex(1);
		this.mScene.sortChildren();
		ParallelEntityModifier wormModifier;
		newWorm.registerEntityModifier(wormModifier = new ParallelEntityModifier(
				new SequenceEntityModifier(new MoveYModifier(0.45f,
						posTulips[5][1] - 80, 0), new MoveYModifier(0.45f, 0,
						posTulips[5][1] - 100)), new MoveXModifier(0.9f, p,
						p - 30)));
		wormModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				runOnUpdateThread(new Runnable() {
					public void run() {
						wormPool.onHandleRecycleItem(newWorm);
					}
				});
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
		return;
	}

	private void showBeetle(final float p) {
		final Sprite newBeetle = beetlePool.obtainPoolItem();
		this.mScene.attachChild(newBeetle);
		newBeetle.setPosition(p, posTulips[5][1] - 170);
		newBeetle.setZIndex(1);
		this.mScene.sortChildren();
		ParallelEntityModifier beetleModifier = null;
		if (p > 600) {
			newBeetle
					.registerEntityModifier(beetleModifier = new ParallelEntityModifier(
							new MoveYModifier(2f, posTulips[5][1] - 170, 0),
							new MoveXModifier(2f, p, p + 1000)));
		} else {
			newBeetle
					.registerEntityModifier(beetleModifier = new ParallelEntityModifier(
							new MoveYModifier(1.2f, posTulips[5][1] - 170, 0),
							new MoveXModifier(1.2f, p, p + 1000)));
		}
		beetleModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				runOnUpdateThread(new Runnable() {
					public void run() {
						beetlePool.onHandleRecycleItem(newBeetle);
					}
				});
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
		return;
	}

	private void showAngel(final float p) {
		final Sprite newAngel = angelPool.obtainPoolItem();
		this.mScene.attachChild(newAngel);
		newAngel.setPosition(p, posTulips[5][1] - 80);
		newAngel.setZIndex(1);
		this.mScene.sortChildren();
		SequenceEntityModifier angelModifier;
		newAngel.registerEntityModifier(angelModifier = new SequenceEntityModifier(
				new MoveYModifier(1, posTulips[5][1] - 80, -200)));
		angelModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				runOnUpdateThread(new Runnable() {
					public void run() {
						angelPool.onHandleRecycleItem(newAngel);
					}
				});
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
		return;
	}

	private void changeBackground() {
		oggBgr.play();
		int currentTileBgr = anmSprBackground.getCurrentTileIndex();
		if (currentTileBgr < 3) {
			anmSprBackground.setCurrentTileIndex(currentTileBgr + 1);
		} else {
			anmSprBackground.setCurrentTileIndex(0);
		}
		return;

	}

	private void peopleClap() {
		oggPerson.play();
		int currentTilePeople = anmSprPerson01.getCurrentTileIndex();
		if (currentTilePeople < 12) {
			anmSprPerson01.setCurrentTileIndex(currentTilePeople + 4);
			anmSprPerson02.setCurrentTileIndex(currentTilePeople + 5);
			anmSprPerson03.setCurrentTileIndex(currentTilePeople + 6);
			anmSprPerson04.setCurrentTileIndex(currentTilePeople + 7);
		} else {
			if (!isClappedOnce) {
				isClappedOnce = true;
				anmSprPerson01.setCurrentTileIndex(4);
				anmSprPerson02.setCurrentTileIndex(5);
				anmSprPerson03.setCurrentTileIndex(6);
				anmSprPerson04.setCurrentTileIndex(7);
			} else {
				isClappedOnce = false;
				anmSprPerson01.setCurrentTileIndex(0);
				anmSprPerson02.setCurrentTileIndex(1);
				anmSprPerson03.setCurrentTileIndex(2);
				anmSprPerson04.setCurrentTileIndex(3);
			}
		}
		return;
	}

	private void modifyObjects() {
		Log.i(TAG, "modifyObjects");
		modifyFlowersAndTulips(anmSprFlower01, spcFlower);
		modifyFlowersAndTulips(anmSprFlower02, spcFlower);
		modifyFlowersAndTulips(anmSprFlower03, spcFlower);
		modifyFlowersAndTulips(anmSprFlower04, spcFlower);
		modifyFlowersAndTulips(anmSprFlower05, spcFlower);
		modifyFlowersAndTulips(anmSprFlower06, spcFlower);
		modifyFlowersAndTulips(anmSprTulip01, spcTulip);
		modifyFlowersAndTulips(anmSprTulip02, spcTulip);
		modifyFlowersAndTulips(anmSprTulip03, spcTulip);
		modifyFlowersAndTulips(anmSprTulip04, spcTulip);
		modifyFlowersAndTulips(anmSprTulip05, spcTulip);
		modifyFlowersAndTulips(anmSprTulip06, spcTulip);
		isModifiedObject = true;
	}

	private void modifyFlowersAndTulips(final AnimatedSprite spr,
			final float spc) {

		autoTranslate = new IUpdateHandler() {
			float width = spr.getWidth();
			boolean isFlowerBlossom = false;
			float tmpSec = 0f;

			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (resetIUpdateHandler == true) {
					tmpSec = 0f;
					isFlowerBlossom = false;
					resetIUpdateHandler = false;
				}
				float pX = spr.getX();
				float pY = spr.getY();
				spr.setPosition(pX -= speed, pY);
				if (spr.getX() == (-1) * width) {
					spr.setPosition(890 + spc, pY);
				}
				tmpSec += pSecondsElapsed;
				if (tmpSec >= 2.5f) {
					anmSprFlower01.setCurrentTileIndex(1);
					anmSprFlower02.setCurrentTileIndex(3);
					anmSprFlower03.setCurrentTileIndex(5);
					anmSprFlower04.setCurrentTileIndex(7);
					anmSprFlower05.setCurrentTileIndex(9);
					anmSprFlower06.setCurrentTileIndex(11);
					isFlowerBlossom = true;
					tmpSec = 0;
				}
				if (tmpSec >= 0.5f && isFlowerBlossom) {
					anmSprFlower01.setCurrentTileIndex(0);
					anmSprFlower02.setCurrentTileIndex(2);
					anmSprFlower03.setCurrentTileIndex(4);
					anmSprFlower04.setCurrentTileIndex(6);
					anmSprFlower05.setCurrentTileIndex(8);
					anmSprFlower06.setCurrentTileIndex(10);
					isFlowerBlossom = false;
					tmpSec = 0;
				}
			}

			@Override
			public void reset() {
				Log.i(TAG, "reset IUpdateHandler");
			}
		};
		this.mScene.registerUpdateHandler(autoTranslate);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		isModifiedObject = false;
		super.onDestroy();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite == anmSprTulip01) {
			isTulip01Blossom = false;
		} else if (pAnimatedSprite == anmSprTulip02) {
			isTulip02Blossom = false;
		} else if (pAnimatedSprite == anmSprTulip03) {
			isTulip03Blossom = false;
		} else if (pAnimatedSprite == anmSprTulip04) {
			isTulip04Blossom = false;
		} else if (pAnimatedSprite == anmSprTulip05) {
			isTulip05Blossom = false;
		} else if (pAnimatedSprite == anmSprTulip06) {
			isTulip06Blossom = false;
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
