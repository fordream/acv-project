/* Vol3Indian.java
 * Create on Feb 29, 2012
 */
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3IndianResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Indian extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private static final String TAG = "Log Indian";

	private TexturePack indianPack1, indianPack2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private TexturePackTextureRegionLibrary libIndianPack1, libIndianPack2;

	private ITextureRegion ttrBackground, ttrBackground1, ttrCroudLeft,
			ttrCroudRight, ttrTent, ttrYubiDuck, ttrYubiTent;

	private Sprite sBackground1, sCroudLeft, sCroudRight, sTent, sYubiDuck,
			sYubiTent;
	private TiledTextureRegion tttrCactus, tttrDuck, tttrFire, tttrPall1,
			tttrPall2, tttrPall3, tttrPall4;
	private TiledTextureRegion[] tttrIndian = new TiledTextureRegion[10];
	private AnimatedSprite aniCactus, aniDuck, aniFire, aniPall1, aniPall2,
			aniPall3, aniPall4;

	private PathModifier pCroudModifire;

	private LoopEntityModifier loopTentModifier;
	private ParallelEntityModifier tentModifier1, tentModifier2;
	private SequenceEntityModifier sIndianModifier, sPallUpModifier,
			sPallDownModifier, pDuckModifire;

	private IndianAnimateSprite[] aniIndian = new IndianAnimateSprite[10];
	private int currentCactus = 1;
	private int currentFire = 0;
	private int currentTent = 0;
	private int currentPall1 = 0;
	private int currentPall2 = 0;
	private int currentPall3 = 0;
	private int currentPall4 = 0;
	private int zIndex;
	private boolean istouchDuck = true;
	private boolean istouchTent = false;
	private boolean istouchPall = true;
	private boolean istouchAll = true;
	private boolean[] istouch = new boolean[] { false, false, false, false,
			false, false, false, false, false, false };

	private float arrPointIndianFirst[][] = new float[][] {
			{ 70, 197, 357, 474, 612 }, { 148, 159, 138, 172, 161 } };

	private float arrPointIndianSecond[][] = new float[][] {
			{ 179, 263, 424, 537, 685 }, { 114, 129, 145, 126, 145 },
			{ 59, 175, 293, 441, 583 }, { 310, 340, 342, 326, 342 } };
	private float arrIndianList[][] = new float[][] {
			{ 402, 400, 414, 400, 410, 409, 415, 405, 406, 413 },
			{ 116, 122, 113, 132, 126, 99, 107, 117, 109, 118 } };

	private Sound A8_04_1, A8_05_2, A8_06_3, A8_07_4, A8_08_5, A8_09_6,
			A8_10_7, A8_11_8, A8_12_9, A8_13_10, A8_14_DURAN, A8_14_MOKIN2,
			A8_14_MOKIN2_5, A8_15_POWA2, A8_16_BOWA, A8_17_BOMI2, A8_18_SLIDE;
	private Sound[] soundCountIndian;

	@Override
	protected void loadKaraokeSound() {
		A8_04_1 = loadSoundResourceFromSD(Vol3IndianResource.A8_04_1);
		A8_05_2 = loadSoundResourceFromSD(Vol3IndianResource.A8_05_2);
		A8_06_3 = loadSoundResourceFromSD(Vol3IndianResource.A8_06_3);
		A8_07_4 = loadSoundResourceFromSD(Vol3IndianResource.A8_07_4);
		A8_08_5 = loadSoundResourceFromSD(Vol3IndianResource.A8_08_5);
		A8_09_6 = loadSoundResourceFromSD(Vol3IndianResource.A8_09_6);
		A8_10_7 = loadSoundResourceFromSD(Vol3IndianResource.A8_10_7);
		A8_11_8 = loadSoundResourceFromSD(Vol3IndianResource.A8_11_8);
		A8_12_9 = loadSoundResourceFromSD(Vol3IndianResource.A8_12_9);
		A8_13_10 = loadSoundResourceFromSD(Vol3IndianResource.A8_13_10);
		A8_14_DURAN = loadSoundResourceFromSD(Vol3IndianResource.A8_14_DURAN);
		A8_14_MOKIN2 = loadSoundResourceFromSD(Vol3IndianResource.A8_14_MOKIN2);
		A8_14_MOKIN2_5 = loadSoundResourceFromSD(Vol3IndianResource.A8_14_MOKIN2_5);
		A8_15_POWA2 = loadSoundResourceFromSD(Vol3IndianResource.A8_15_POWA2);
		A8_16_BOWA = loadSoundResourceFromSD(Vol3IndianResource.A8_16_BOWA);
		A8_17_BOMI2 = loadSoundResourceFromSD(Vol3IndianResource.A8_17_BOMI2);
		A8_18_SLIDE = loadSoundResourceFromSD(Vol3IndianResource.A8_18_SLIDE);
		soundCountIndian = new Sound[] { A8_04_1, A8_05_2, A8_06_3, A8_07_4,
				A8_08_5, A8_09_6, A8_10_7, A8_11_8, A8_12_9, A8_13_10 };
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("indian/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("indian/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "indian/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		try {
			indianPack1 = mTexturePackLoaderFromSource.load("indian1.xml");
			indianPack1.loadTexture();
			indianPack2 = mTexturePackLoaderFromSource.load("indian2.xml");
			indianPack2.loadTexture();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// add lib
		libIndianPack1 = indianPack1.getTexturePackTextureRegionLibrary();
		libIndianPack2 = indianPack2.getTexturePackTextureRegionLibrary();
		this.ttrBackground = libIndianPack2
				.get(Vol3IndianResource.A8_20_1_IPHONE_HAIKEI_ID);
		this.ttrBackground1 = libIndianPack2
				.get(Vol3IndianResource.A8_20_2_IPHONE_HAIKEI_ID);
		// croud left
		this.ttrCroudLeft = libIndianPack2
				.get(Vol3IndianResource.A8_19_1_IPHONE_LEFTCROUD_ID);
		// croud right
		this.ttrCroudRight = libIndianPack2
				.get(Vol3IndianResource.A8_19_2_IPHONE_RIGHTCROUD_ID);
		// tent
		this.ttrTent = libIndianPack1
				.get(Vol3IndianResource.A8_17_IPHONE_TENT_ID);
		// cactus
		this.tttrCactus = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_CACTUS);
		// duck
		this.tttrDuck = getTiledTextureFromPacker(indianPack2,
				Vol3IndianResource.PACK_DUCK);
		// Fire
		this.tttrFire = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_FIRE);
		// list indian
		this.tttrIndian[0] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_1);
		this.tttrIndian[1] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_2);
		this.tttrIndian[2] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_3);
		this.tttrIndian[3] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_4);
		this.tttrIndian[4] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_5);
		this.tttrIndian[5] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_6);
		this.tttrIndian[6] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_7);
		this.tttrIndian[7] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_8);
		this.tttrIndian[8] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_9);
		this.tttrIndian[9] = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_INDIAN_10);
		// pall 1
		this.tttrPall1 = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_BALL_1);
		// pall 2
		this.tttrPall2 = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_BALL_2);
		// pall 3
		this.tttrPall3 = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_BALL_3);
		// pall 4
		this.tttrPall4 = getTiledTextureFromPacker(indianPack1,
				Vol3IndianResource.PACK_BALL_4);
		// yubi duck
		this.ttrYubiDuck = libIndianPack2
				.get(Vol3IndianResource.A8_21_1_IPHONE_YUBI_ID);
		this.ttrYubiTent = libIndianPack2
				.get(Vol3IndianResource.A8_21_2_IPHONE_YUBI_ID);

	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, ttrBackground, this
						.getVertexBufferObjectManager())));
		// this.mScene.setOnAreaTouchTraversalFrontToBack();
		zIndex = mScene.getZIndex();
		this.sBackground1 = new Sprite(0, 0, ttrBackground1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground1);
		// display croud left
		this.sCroudLeft = new Sprite(313, 29, ttrCroudLeft,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCroudLeft);
		// display croud right
		this.sCroudRight = new Sprite(591, 26, ttrCroudRight,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCroudRight);
		// display tent
		this.sTent = new Sprite(349, 36, ttrTent,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTent);
		// display cactus
		this.aniCactus = new AnimatedSprite(81, 24, tttrCactus,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniCactus);

		this.aniDuck = new AnimatedSprite(372, 264, tttrDuck,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDuck);
		// display hand
		this.sYubiDuck = new Sprite(270, 224, ttrYubiDuck,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sYubiDuck);
		this.sYubiTent = new Sprite(300, 70, ttrYubiTent,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sYubiTent);
		sYubiTent.setVisible(false);
		// display Fire
		this.aniFire = new AnimatedSprite(694, 239, tttrFire,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniFire);
		// display pall 1
		this.aniPall1 = new AnimatedSprite(769, 550, tttrPall1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPall1);

		// display pall 2
		this.aniPall2 = new AnimatedSprite(769, 647, tttrPall2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPall2);

		// display pall 3
		this.aniPall3 = new AnimatedSprite(769, 745, tttrPall3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPall3);

		// display pall 4
		this.aniPall4 = new AnimatedSprite(769, 849, tttrPall4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPall4);

		addGimmicsButton(mScene, Vol3IndianResource.SOUND_GIMMIC,
				Vol3IndianResource.IMAGE_GIMMIC, libIndianPack2);
		this.mScene.setOnSceneTouchListener(this);

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(aniCactus, 78, 10, 184, 222, pX, pY)) {
				Log.d(TAG, "tap vao xuong rong");
				touchCactus();
			} else if (checkContains(aniFire, 10, 336, 97, 375, pX, pY)) {
				Log.d(TAG, "tap vao dong lua");
				currentFire++;
				Log.d(TAG, "gia tri currentFire: " + currentFire);
				touchAnimate(1, currentFire);
				if (currentFire == 2) {
					currentFire = -1;
				}
			} else if (checkContains(sTent, 40, 30, 225, 165, pX, pY)) {
				Log.d(TAG, "tap vao leu");
				if (istouchTent) {
					if (currentTent < 10) {
						istouch[currentTent] = false;
						sYubiTent.setVisible(false);
						sYubiTent.clearEntityModifiers();
						A8_18_SLIDE.play();
						touchTent(currentTent);
					}
					currentTent++;
					if (currentTent == 11) {
						for (int k = 0; k < 10; k++) {
							aniIndian[k].detachSelfIndian();
						}
						currentTent = 0;
						moveTent();
					}
				}
			} else if (checkContains(aniDuck, 26, 46, 80, 184, pX, pY)) {
				// tap duck
				Log.d(TAG, "tap vao vit");
				if (istouchDuck) {
					istouchDuck = false;
					touchDuck();
				}
			} else if (checkContains(aniPall1, 64, 10, 124, 95, pX, pY)) {
				currentPall1++;
				touchAnimate(2, currentPall1);
				if (currentPall1 == 2) {
					currentPall1 = -1;
				}

			} else if (checkContains(aniPall1, 15, 19, 64, 56, pX, pY)
					|| checkContains(aniPall1, 126, 18, 182, 57, pX, pY)) {
				if (istouchPall == true) {
					movePallUp();
				} else {
					movePallDown();
				}

			} else if (checkContains(aniPall2, 64, 9, 123, 97, pX, pY)) {
				currentPall2++;
				touchAnimate(3, currentPall2);
				if (currentPall2 == 2) {
					currentPall2 = -1;
				}
			} else if (checkContains(aniPall3, 64, 10, 124, 102, pX, pY)) {
				currentPall3++;
				touchAnimate(4, currentPall3);
				if (currentPall3 == 2) {
					currentPall3 = -1;
				}
			} else if (checkContains(aniPall4, 64, 9, 124, 174, pX, pY)) {
				currentPall4++;
				touchAnimate(5, currentPall4);
				if (currentPall4 == 2) {
					currentPall4 = -1;
				}
			}
			for (int k = 0; k < 10; k++) {
				if (aniIndian[k] != null) {
					if (checkContains(aniIndian[k], 20, 20,
							(int) aniIndian[k].getWidth() - 50,
							(int) aniIndian[k].getHeight() - 30, pX, pY)) {
						if (istouch[k]) {
							istouch[k] = false;
							aniIndian[k].touchIndian(k);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	private void touchTent(final int currentTent) {
		Log.d(TAG, "tap vao leu" + currentTent);
		if (loopTentModifier != null) {
			sTent.clearEntityModifiers();
		}
		if (currentTent == 9) {
			istouchTent = false;
			istouchAll = false;
		}
		istouch[currentTent] = false;
		aniIndian[currentTent] = new IndianAnimateSprite(
				arrIndianList[0][currentTent], arrIndianList[1][currentTent],
				tttrIndian[currentTent], this.getVertexBufferObjectManager());
		aniIndian[currentTent].setScale(0.0f);
		if (currentTent < 4) {
			aniIndian[currentTent].setZIndex(zIndex + 2);
		} else if (10 > currentTent && currentTent >= 4) {

			if (currentTent == 9) {
				aniIndian[currentTent].setZIndex(zIndex);
			} else {
				aniIndian[currentTent].setZIndex(zIndex + 1);
			}
		}
		mScene.attachChild(aniIndian[currentTent]);
		this.mScene.sortChildren();
		if (currentTent < 5) {
			aniIndian[currentTent]
					.registerEntityModifier(tentModifier1 = new ParallelEntityModifier(
							new ScaleModifier(1.5f, 0.0f, 1.0f),
							new MoveModifier(1.5f,
									arrIndianList[0][currentTent],
									arrPointIndianFirst[0][currentTent],
									arrIndianList[1][currentTent],
									arrPointIndianFirst[1][currentTent])));
			tentModifier1.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					aniIndian[currentTent].animateIndian(currentTent);
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		} else if (10 > currentTent && currentTent >= 5) {
			aniIndian[currentTent]
					.registerEntityModifier(tentModifier2 = new ParallelEntityModifier(
							new ScaleModifier(1.5f, 0.0f, 1.0f),
							new MoveModifier(1.5f,
									arrIndianList[0][currentTent],
									arrPointIndianSecond[0][currentTent - 5],
									arrIndianList[1][currentTent],
									arrPointIndianSecond[1][currentTent - 5])));
			tentModifier2.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0,
						IEntity arg1) {
					aniIndian[currentTent].setZIndex(zIndex + 3);
					setGimmicBringToFront();
					aniIndian[currentTent]
							.registerEntityModifier(sIndianModifier = new SequenceEntityModifier(
									new MoveModifier(
											1.0f,
											arrPointIndianSecond[0][currentTent - 5],
											arrPointIndianSecond[2][currentTent - 5],
											arrPointIndianSecond[1][currentTent - 5],
											arrPointIndianSecond[3][currentTent - 5])));

					sIndianModifier
							.addModifierListener(new IModifierListener<IEntity>() {

								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									aniIndian[currentTent]
											.animateIndian(currentTent);
									if (currentTent == 9) {
										for (int k = 0; k < 10; k++) {
											aniIndian[k].moveIndian(k);
										}
									}
								}
							});
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {

				}
			});
		}
	}

	private void touchDuck() {
		sYubiDuck.setVisible(false);
		sYubiDuck.clearEntityModifiers();
		aniDuck.clearEntityModifiers();
		aniDuck.stopAnimation();
		long pFrameDurations[] = new long[] { 400, 400, 400, 400, 400 };
		int pFrames[] = new int[] { 2, 3, 4, 5, 6 };
		aniDuck.animate(pFrameDurations, pFrames, 0);
		A8_17_BOMI2.play();
		aniDuck.registerEntityModifier(pDuckModifire = new SequenceEntityModifier(
				new MoveModifier(2.0f, aniDuck.getX(), sTent.getX()
						+ sTent.getWidth() / 2 - aniDuck.getWidth() / 2,
						aniDuck.getY(), sTent.getY() + sTent.getHeight()
								- aniDuck.getHeight())));
		pDuckModifire.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						aniDuck.setVisible(false);
						aniDuck.stopAnimation();
						aniDuck.clearEntityModifiers();
						istouchTent = true;
						istouchDuck = false;
						moveTent();
					}
				});
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void touchCactus() {
		A8_15_POWA2.play();
		if (currentCactus == 1) {
			aniCactus.setCurrentTileIndex(currentCactus);
			currentCactus = 2;
		} else if (currentCactus == 2) {
			aniCactus.setCurrentTileIndex(currentCactus);
			currentCactus = 3;
		} else if (currentCactus == 3) {
			aniCactus.setCurrentTileIndex(currentCactus);
			currentCactus = 0;
		} else if (currentCactus == 0) {
			aniCactus.setCurrentTileIndex(currentCactus);
			currentCactus = 1;
		}
	}

	private void touchAnimate(int id, int index) {
		switch (id) {
		case 1:
			A8_16_BOWA.play();
			if (index == 1) {
				Log.e(TAG, "tap vao lua lan 1: ");
				aniFire.setPosition(700, 239);
				aniFire.setCurrentTileIndex(index);
			} else {
				Log.e(TAG, "tap vao lua lan 2: ");
				aniFire.setPosition(694, 239);
				aniFire.setCurrentTileIndex(index);
			}
			break;
		case 2:
			A8_14_DURAN.play();
			aniPall1.setCurrentTileIndex(index);
			break;
		case 3:
			A8_14_DURAN.play();
			aniPall2.setCurrentTileIndex(index);
			break;
		case 4:
			A8_14_DURAN.play();
			aniPall3.setCurrentTileIndex(index);
			break;
		case 5:
			A8_14_DURAN.play();
			aniPall4.setCurrentTileIndex(index);
			break;
		default:
			break;
		}
	}

	private void movePallUp() {
		Log.e(TAG, "di len tren: ");
		A8_14_MOKIN2.play();
		aniPall1.registerEntityModifier(sPallUpModifier = new SequenceEntityModifier(
				new MoveYModifier(0.3f, 550, 167)));
		aniPall2.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 647, 264)));
		aniPall3.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 745, 362)));
		aniPall4.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 849, 466)));
		sPallUpModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				istouchPall = false;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void movePallDown() {
		Log.e(TAG, "di xuong duoi: ");
		A8_14_MOKIN2_5.play();
		aniPall1.registerEntityModifier(sPallDownModifier = new SequenceEntityModifier(
				new MoveYModifier(0.3f, 167, 550)));
		aniPall2.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 264, 647)));
		aniPall3.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 362, 745)));
		aniPall4.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 466, 849)));
		sPallDownModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				istouchPall = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void moveTent() {
		yubiTent();
		sTent.registerEntityModifier(loopTentModifier = new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(0.06f, sTent
						.getX(), sTent.getX() + 10), new MoveXModifier(0.06f,
						sTent.getX() + 10, sTent.getX()), new MoveXModifier(
						0.06f, sTent.getX(), sTent.getX() - 10),
						new MoveXModifier(0.06f, sTent.getX() - 10, sTent
								.getX()), new DelayModifier(0.8f))));
	}

	private void moveDuck() {
		Log.d(TAG, "trong ham move duck");
		if (loopTentModifier != null) {
			sTent.clearEntityModifiers();
		}
		yubiDuck();
		aniDuck.setVisible(true);
		long pFrameDurations[] = new long[] { 400, 400 };
		int pFrames[] = new int[] { 1, 0 };
		aniDuck.animate(pFrameDurations, pFrames, -1);
		aniDuck.setRotationCenter(50.0f, 188.0f);
		aniDuck.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new RotationModifier(0.8f, 0.0f,
						5.0f), new RotationModifier(0.8f, 5.0f, 0.0f),
						new RotationModifier(0.8f, 0.0f, -5.0f),
						new RotationModifier(0.8f, -5.0f, 0.0f))));
	}

	private void yubiDuck() {
		sYubiDuck.setVisible(true);
		sYubiDuck.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new ScaleModifier(0.1f, 1, 1.2f),
						new ScaleModifier(0.1f, 1.2f, 1f), new DelayModifier(
								1.0f))));
	}

	private void yubiTent() {
		sYubiTent.setVisible(true);
		sYubiTent.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new ScaleModifier(0.1f, 1, 1.2f),
						new ScaleModifier(0.1f, 1.2f, 1f), new DelayModifier(
								1.0f))));
	}

	private void moveCroud() {
		Path path1 = new Path(3).to(sCroudLeft.getX(), sCroudLeft.getY())
				.to(sCroudLeft.getX() + 20, sCroudLeft.getY())
				.to(sCroudLeft.getX(), sCroudLeft.getY());
		pCroudModifire = new PathModifier(1.5f, path1, iCroudListener);
		sCroudLeft
				.registerEntityModifier(new LoopEntityModifier(pCroudModifire));
		Path path2 = new Path(3).to(sCroudRight.getX(), sCroudRight.getY())
				.to(sCroudRight.getX() + 20, sCroudRight.getY())
				.to(sCroudRight.getX(), sCroudRight.getY());
		pCroudModifire = new PathModifier(1.5f, path2, iCroudListener);
		sCroudRight.registerEntityModifier(new LoopEntityModifier(
				pCroudModifire));
	}

	IEntityModifierListener iCroudListener = new IEntityModifierListener() {
		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
		}

		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		}
	};

	@Override
	public void combineGimmic3WithAction() {
		touchCactus();
	}

	private class IndianAnimateSprite extends AnimatedSprite {

		private SequenceEntityModifier seSheepModifier1;
		private SequenceEntityModifier seSheepModifier2;

		public IndianAnimateSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vAnimateSprite) {
			super(pX, pY, pTiledTextureRegion, vAnimateSprite);
		}

		public void animateIndian(int id) {
			long pFrameDuration[] = new long[] { 300, 300 };
			int pFrame[] = new int[] { 1, 0 };
			this.animate(pFrameDuration, pFrame, -1);
			Vol3Indian.this.istouch[id] = true;
		}

		public void detachSelfIndian() {
			runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					IndianAnimateSprite.this.setVisible(false);
					IndianAnimateSprite.this.detachSelf();
					IndianAnimateSprite.this.clearEntityModifiers();
					IndianAnimateSprite.this.stopAnimation();
					for (int k = 0; k < 10; k++) {
						Vol3Indian.this.istouch[k] = false;
						aniIndian[k] = null;
					}
				}
			});
		}

		public void moveIndian(final int index) {
			seSheepModifier1 = new SequenceEntityModifier(
					new MoveYModifier(0.6f, aniIndian[index].getY(),
							aniIndian[index].getY() - 30), new MoveYModifier(
							0.6f, aniIndian[index].getY() - 30,
							aniIndian[index].getY()));
			this.registerEntityModifier(seSheepModifier1);
			seSheepModifier1
					.addModifierListener(new IModifierListener<IEntity>() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							Vol3Indian.this.istouchAll = true;
							Vol3Indian.this.istouchTent = true;
							for (int k = 0; k < 10; k++) {
								Vol3Indian.this.istouch[k] = true;
							}
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					});
		}

		public void touchIndian(final int index) {
			Log.d(TAG, "tap vao indian");
			if (Vol3Indian.this.istouchAll == true) {
				Vol3Indian.this.soundCountIndian[index].play();
				IndianAnimateSprite.this.stopAnimation();
				IndianAnimateSprite.this.setCurrentTileIndex(2);
				seSheepModifier2 = new SequenceEntityModifier(
						new MoveYModifier(0.5f, aniIndian[index].getY(),
								aniIndian[index].getY() - 30),
						new MoveYModifier(0.5f, aniIndian[index].getY() - 30,
								aniIndian[index].getY()));
				IndianAnimateSprite.this
						.registerEntityModifier(seSheepModifier2);
				seSheepModifier2
						.addModifierListener(new IModifierListener<IEntity>() {
							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								animateIndian(index);
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}
						});
			}
		}
	}

	@Override
	public void onPauseGame() {
		Log.e(TAG, "Pause game: ");
		clearData();
		super.onPauseGame();
	}

	@Override
	public synchronized void onResumeGame() {
		moveCroud();
		moveDuck();
		super.onResumeGame();
	}

	private void clearData() {
		Log.e(TAG, "call when Pause game: ");
		for (int k = 0; k < 10; k++) {
			if (aniIndian[k] != null) {
				aniIndian[k].detachSelfIndian();
				aniIndian[k] = null;
			}
		}
		if (aniCactus != null) {
			currentCactus = 1;
			aniCactus.setCurrentTileIndex(0);
		}
		if (aniFire != null) {
			currentFire = 0;
			aniFire.setCurrentTileIndex(0);
		}
		istouchPall = true;
		if (aniPall1 != null) {
			currentPall1 = 0;
			aniPall1.setCurrentTileIndex(0);
			aniPall1.setPosition(769, 550);
		}
		if (aniPall2 != null) {
			currentPall2 = 0;
			aniPall2.setCurrentTileIndex(0);
			aniPall2.setPosition(769, 647);
		}
		if (aniPall3 != null) {
			currentPall3 = 0;
			aniPall3.setCurrentTileIndex(0);
			aniPall3.setPosition(769, 745);
		}
		if (aniPall4 != null) {
			currentPall4 = 0;
			aniPall4.setCurrentTileIndex(0);
			aniPall4.setPosition(769, 849);
		}
		if (loopTentModifier != null) {
			sYubiTent.setVisible(false);
			sYubiTent.clearEntityModifiers();
			sTent.clearEntityModifiers();
			istouchTent = false;
			currentTent = 0;
		}
		if (pDuckModifire != null) {
			// sYubiDuck.setVisible(true);
			aniDuck.clearEntityModifiers();
			aniDuck.stopAnimation();
			aniDuck.setVisible(true);
			aniDuck.setPosition(372, 264);
			aniDuck.setCurrentTileIndex(0);
			istouchDuck = true;
		}
	}

	protected void loadKaraokeComplete() {
		// moveCroud();
		// moveDuck();
		super.loadKaraokeComplete();
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
}
