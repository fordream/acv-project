/* Darumasan.java
 * Create on Feb 6, 2012
 */
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3DarumasanResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;
public class Vol3Darumasan extends BaseGameActivity implements IOnSceneTouchListener {

	private TexturePack ttpDarumasan;
	private TexturePackTextureRegionLibrary ttpDarumasanLibrary;

	private ITextureRegion ttrBackground, ttrSyuriken, ttrHasira, ttrNinja,
			ttrTree90, ttrDarumasanMove;

	private TiledTextureRegion ttrDoorRight, ttrDoorLeft, ttrDarumasanLeft,
			ttrDarumasanCenter, ttrDarumasanRight;

	private Sprite sSyuriken, sHasira, sNinja, sTree90, sBackground;

	private Sprite aniDarumasanMove[] = new Sprite[6];
	private AnimatedSprite aniDoorRight, aniDoorLeft, aniDarumasanLeft,
			aniDarumasanCenter, aniDarumasanRight;

	@SuppressWarnings("unused")
	private SequenceEntityModifier ninjaSequenceModifier, treeSequenceModifier;
	private ParallelEntityModifier[] paDarumasanMove = new ParallelEntityModifier[6];
	private int currentDrms1 = 0, currentDrms2 = 0, currentDrms3 = 0,
			fallCounter = 0;

	boolean leftIsOpen, rightIsOpen, bothIsOpen, istouchCot, istouchDoorleft,
			istouchDoorRight, istouchMove;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	/**
	 * Sounds
	 */
	private Sound mp3Husuma, mp3Ninja, mp3DarumaChange, mp3DarumaDown;

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(
				sBackground = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
						ttrBackground, this.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();

		// display cot
		this.sHasira = new Sprite(441, 0, ttrHasira,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHasira);
		// display syuriken
		this.sSyuriken = new Sprite(470, 160, ttrSyuriken,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSyuriken);
		// display door left
		this.aniDoorLeft = new AnimatedSprite(0, 0, ttrDoorLeft,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDoorLeft);
		// display door right
		this.aniDoorRight = new AnimatedSprite(525, 0, ttrDoorRight,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDoorRight);
		// display ninja
		this.sNinja = new Sprite(426, -220, ttrNinja,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sNinja);
		// display tree 90
		this.sTree90 = new Sprite(440, -20, ttrTree90,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree90);

		// display darumasan left
		this.aniDarumasanLeft = new AnimatedSprite(47, 300, ttrDarumasanLeft,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDarumasanLeft);
		// display darumasan center
		this.aniDarumasanCenter = new AnimatedSprite(332, 300,
				ttrDarumasanCenter, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDarumasanCenter);
		// display darumasan right
		this.aniDarumasanRight = new AnimatedSprite(622, 300,
				ttrDarumasanRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDarumasanRight);

		addGimmicsButton(this.mScene, Vol3DarumasanResource.SOUND_GIMMIC,
				Vol3DarumasanResource.IMAGE_GIMMIC_DARUMASAN,
				ttpDarumasanLibrary);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	protected void loadKaraokeResources() {
		
		ttpDarumasan = mTexturePackLoaderFromSource.load("darumasan.xml");
		ttpDarumasan.loadTexture();
		ttpDarumasanLibrary = ttpDarumasan.getTexturePackTextureRegionLibrary();
		this.ttrBackground = ttpDarumasanLibrary
				.get(Vol3DarumasanResource.A3_00_IPHONE3G_HAIKEI_DARUMASAN_ID);

		this.ttrSyuriken = ttpDarumasanLibrary
				.get(Vol3DarumasanResource.A3_08_IPHONE3G_SYURIKEN_ID);

		// load Hasira
		this.ttrHasira = ttpDarumasanLibrary
				.get(Vol3DarumasanResource.A3_09_IPHONE3G_HASIRA_ID);

		// load door left
		this.ttrDoorLeft = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_01_IPHONE3G_E00015_DOOR_RIGHT_ID,
				Vol3DarumasanResource.A3_05_IPHONE3G_E00015_OPENDOOR_RIGHT_ID);
		// load door right
		this.ttrDoorRight = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_01_IPHONE3G_E00015_DOOR_RIGHT_ID,
				Vol3DarumasanResource.A3_05_IPHONE3G_E00015_OPENDOOR_RIGHT_ID);

		// load ninja
		this.ttrNinja = ttpDarumasanLibrary
				.get(Vol3DarumasanResource.A3_04_IPHONE3G_E00014_NINJA_ID);

		// load tree 90
		this.ttrTree90 = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_13_IPHONE3G_TREE90_ID,
				Vol3DarumasanResource.A3_13_IPHONE3G_TREE90_ID);

		// load darumasan left
		this.ttrDarumasanLeft = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_03_3_IPHONE3G_LEFT_BEFORE_ID,
				Vol3DarumasanResource.A3_07_3_IPHONE3G_LEFT_AFTER_ID,
				Vol3DarumasanResource.A3_07_2_IPHONE3G_CENTER_AFTER_ID,
				Vol3DarumasanResource.A3_07_1_IPHONE3G_RIGHT_AFTER_ID);

		// load darumasan center
		this.ttrDarumasanCenter = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_03_2_IPHONE3G_CENTER_BEFORE_ID,
				Vol3DarumasanResource.A3_07_2_IPHONE3G_CENTER_AFTER_ID,
				Vol3DarumasanResource.A3_07_3_IPHONE3G_LEFT_AFTER_ID,
				Vol3DarumasanResource.A3_07_1_IPHONE3G_RIGHT_AFTER_ID);

		// load darumasan right
		this.ttrDarumasanRight = getTiledTextureFromPacker(ttpDarumasan,
				Vol3DarumasanResource.A3_03_1_IPHONE3G_RIGHT_BEFORE_ID,
				Vol3DarumasanResource.A3_07_1_IPHONE3G_RIGHT_AFTER_ID,
				Vol3DarumasanResource.A3_07_2_IPHONE3G_CENTER_AFTER_ID,
				Vol3DarumasanResource.A3_07_3_IPHONE3G_LEFT_AFTER_ID);

		// load darumasan move
		this.ttrDarumasanMove = ttpDarumasanLibrary
				.get(Vol3DarumasanResource.A3_03_2_IPHONE3G_CENTER_BEFORE_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		Log.e("Vol3Darumasan", "loadKaraokeSound: ");
		mp3Husuma = loadSoundResourceFromSD(Vol3DarumasanResource.E00015_HUSUMA);
		mp3Ninja = loadSoundResourceFromSD(Vol3DarumasanResource.E00014_NINJA);
		mp3DarumaChange = loadSoundResourceFromSD(Vol3DarumasanResource.E00071_DARUMA_CHANGE);
		mp3DarumaDown = loadSoundResourceFromSD(Vol3DarumasanResource.E00036_DARUMA_DOWN);
	}

	@Override
	public synchronized void onResumeGame() {
		loadDefault();
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();

	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("darumasan/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("darumasan/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "darumasan/gfx/");
		super.onCreateResources();
	}

	public void loadDefault() {
		istouchCot = true;
		leftIsOpen = false;
		rightIsOpen = false;
		bothIsOpen = false;
		istouchDoorleft = true;
		istouchDoorRight = true;
		istouchMove = true;
	}

	@Override
	public void onPauseGame() {
		clearData();
	}

	private void clearData() {
		istouchCot = true;
		leftIsOpen = false;
		rightIsOpen = false;
		bothIsOpen = false;
		istouchDoorleft = true;
		istouchDoorRight = true;
		istouchMove = true;
		if (ninjaSequenceModifier != null) {
			sNinja.clearEntityModifiers();
			sNinja.setPosition(426, -220);

		}
		if (sTree90 != null) {
			sTree90.clearEntityModifiers();
			sTree90.reset();

			sTree90.setPosition(440, -20);

		}
		for (int i = 0; i < 6; i++) {
			if (aniDarumasanMove[i] != null) {
				final int k = i;
				runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						aniDarumasanMove[k].clearEntityModifiers();
						aniDarumasanMove[k].setVisible(false);
						aniDarumasanMove[k].detachSelf();
					}
				});
			}
		}
		if (aniDoorRight != null) {
			aniDoorRight.setCurrentTileIndex(0);
		}
		if (aniDoorLeft != null) {
			aniDoorLeft.setCurrentTileIndex(0);
		}
		if (aniDarumasanLeft != null) {
			aniDarumasanLeft.setCurrentTileIndex(0);
		}
		if (aniDarumasanCenter != null) {
			aniDarumasanCenter.setCurrentTileIndex(0);
		}
		if (aniDarumasanRight != null) {
			aniDarumasanRight.setCurrentTileIndex(0);
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			if (checkContains(aniDoorLeft, 0, 0, (int) aniDoorLeft.getWidth(),
					(int) aniDoorLeft.getHeight(), pX, pY)) {
				openDoorLeft();
			} else if (checkContains(aniDoorRight, 0, 0,
					(int) aniDoorRight.getWidth(),
					(int) aniDoorRight.getHeight(), pX, pY)) {
				openDoorRight();
			} else if (checkContains(sHasira, (int) sHasira.getWidth() / 3, 0,
					(int) sHasira.getWidth() * 2 / 3,
					(int) sHasira.getHeight(), pX, pY)) {
				moveNinja();
			}

			else if (checkContains(aniDarumasanLeft, 40, 10,
					(int) aniDarumasanLeft.getWidth() - 50,
					(int) aniDarumasanLeft.getHeight() / 2, pX, pY)) {
				currentDrms1++;
				touchDarumasan(1, currentDrms1);
				if (currentDrms1 >= 3) {
					currentDrms1 = -1;
				}
			} else if (checkContains(aniDarumasanCenter, 40, 10,
					(int) aniDarumasanCenter.getWidth() - 50,
					(int) aniDarumasanCenter.getHeight() / 2, pX, pY)) {
				currentDrms2++;
				touchDarumasan(2, currentDrms2);
				if (currentDrms2 >= 3) {
					currentDrms2 = -1;
				}

			} else if (checkContains(aniDarumasanRight, 40, 10,
					(int) aniDarumasanRight.getWidth() - 50,
					(int) aniDarumasanRight.getHeight() / 2, pX, pY)) {
				currentDrms3++;
				touchDarumasan(3, currentDrms3);
				if (currentDrms3 >= 3) {
					currentDrms3 = -1;
				}

			} else if (checkContains(sBackground, 0, 320, 930, 623, pX, pY)) {
				moveDarumasan();
			}
			return true;
		}
		return false;
	}

	private void moveDarumasan() {
		float rate;
		int tmpleft;
		if (istouchMove) {
			istouchMove = false;
			if (fallCounter == 0) {
				rate = 0.5f;
				tmpleft = 0;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			if (fallCounter == 1) {
				rate = 0.5f;
				tmpleft = 160;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			if (fallCounter == 2) {
				rate = 0.6f;
				tmpleft = 320;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			if (fallCounter == 3) {
				rate = 0.75f;
				tmpleft = 445;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			if (fallCounter == 4) {
				rate = 0.25f;
				tmpleft = 600;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			if (fallCounter == 5) {
				rate = 0.5f;
				tmpleft = 750;
				darumasanModifier(rate, tmpleft, fallCounter);
			}
			fallCounter++;
			if (fallCounter >= 6) {
				fallCounter = 0;
			}
			
			mEngine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					istouchMove = true;
					
				}
			}));

			mEngine.registerUpdateHandler(new TimerHandler(0.2f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							istouchMove = true;

						}
					}));

		}
	}

	private void darumasanModifier(float rate, int left, final int index) {

		aniDarumasanMove[index] = new Sprite(left, -324, this.ttrDarumasanMove,
				this.getVertexBufferObjectManager());
		int indexbg = this.mScene.getZIndex();
		aniDarumasanMove[index].setZIndex(indexbg);
		aniDarumasanCenter.setZIndex(indexbg + 1);
		aniDarumasanLeft.setZIndex(indexbg + 1);
		aniDarumasanRight.setZIndex(indexbg + 1);
		setGimmicBringToFront();
		this.mScene.attachChild(aniDarumasanMove[index]);
		this.mScene.sortChildren();
		mp3DarumaDown.play();
		aniDarumasanMove[index]
				.registerEntityModifier(paDarumasanMove[index] = new ParallelEntityModifier(

				new SequenceEntityModifier(new MoveYModifier(0.3f,
						aniDarumasanMove[index].getY(), aniDarumasanMove[index]
								.getY() + 400), new MoveYModifier(0.1f,
						aniDarumasanMove[index].getY() + 400,
						aniDarumasanMove[index].getY() + 350),
						new MoveYModifier(0.1f,
								aniDarumasanMove[index].getY() + 350,
								aniDarumasanMove[index].getY() + 400),
						new DelayModifier(0.1f)), new ScaleModifier(0.5f, rate,
						rate)));
		paDarumasanMove[index]
				.addModifierListener(new IModifierListener<IEntity>() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {
							public void run() {
								aniDarumasanMove[index].clearEntityModifiers();
								aniDarumasanMove[index].setVisible(false);
								aniDarumasanMove[index].detachSelf();
							}
						});
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {

					}
				});
	}

	/**
	 * tap darumasan
	 */
	private void touchDarumasan(int id, int position) {
		mp3DarumaChange.play();
		if (id == 1) {
			// tap darumasan left
			aniDarumasanLeft.setCurrentTileIndex(position);

		} else if (id == 2) {
			// tap darumasan center
			aniDarumasanCenter.setCurrentTileIndex(position);
		} else if (id == 3) {
			// tap darumasan center
			aniDarumasanRight.setCurrentTileIndex(position);
		}
	}

	/**
	 * move Ninja
	 */
	private void moveNinja() {
		if (istouchCot) {
			istouchCot = false;
			mp3Ninja.play();
			sNinja.registerEntityModifier(ninjaSequenceModifier = new SequenceEntityModifier(
					new DelayModifier(0.2f), new MoveYModifier(0.7f, sNinja
							.getY(), sNinja.getY() + 120), new DelayModifier(
							0.5f), new MoveYModifier(0.7f, sNinja.getY() + 120,
							sNinja.getY())));
			sTree90.setRotationCenter(130, 2);
			sTree90.registerEntityModifier(treeSequenceModifier = new SequenceEntityModifier(
					new RotationModifier(0.7f, 0.0f, -89.0f),
					new DelayModifier(0.7f), new RotationModifier(1.0f, -89.0f,
							0.0f)));

			mEngine.registerUpdateHandler(new TimerHandler(3.0f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							istouchCot = true;

						}
					}));

		}
	}

	/**
	 * open door left
	 */
	private void openDoorLeft() {
		long pDurationDoor[] = new long[] { 800 };
		int[] pFrameDoor = new int[] { 1 };
		int[] pFrameDoor1 = new int[] { 0 };

		if (istouchDoorleft) {
			mp3Husuma.play();
			istouchDoorleft = false;
			if (leftIsOpen) {
				// close door
				aniDoorLeft.animate(pDurationDoor, pFrameDoor1, 0);
				bothIsOpen = false;
			} else {
				// open door
				aniDoorLeft.animate(pDurationDoor, pFrameDoor, 0);
				if (!rightIsOpen) {
					bothIsOpen = false;
				} else {
					bothIsOpen = true;
				}
			}

			
			mEngine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					istouchDoorleft = true;
				}
			}));
			

			mEngine.registerUpdateHandler(new TimerHandler(0.2f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							istouchDoorleft = true;

						}
					}));


			leftIsOpen = !leftIsOpen;
		}
	}

	/**
	 * open door right
	 */
	private void openDoorRight() {
		long pDurationDoor[] = new long[] { 800 };
		int[] pFrameDoor = new int[] { 1 };
		int[] pFrameDoor1 = new int[] { 0 };

		if (istouchDoorRight) {
			mp3Husuma.play();
			istouchDoorRight = false;
			if (rightIsOpen) {
				// close door
				aniDoorRight.animate(pDurationDoor, pFrameDoor1, 0);
				bothIsOpen = false;
			} else {
				// open door
				aniDoorRight.animate(pDurationDoor, pFrameDoor, 0);
				if (!leftIsOpen) {
					bothIsOpen = false;
				} else {
					bothIsOpen = true;
				}
			}

			
			mEngine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					istouchDoorRight = true;
				}
			}));

			mEngine.registerUpdateHandler(new TimerHandler(0.2f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							istouchDoorRight = true;

						}
					}));

			rightIsOpen = !rightIsOpen;
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		checkGimmic3();
	}

	private void checkGimmic3() {

		long pDurationDoor[] = new long[] { 800 };
		int[] pFrameDoor = new int[] { 1 };
		int[] pFrameDoor1 = new int[] { 0 };
		mp3Husuma.play();
		if (bothIsOpen) {
			// close door
			aniDoorLeft.animate(pDurationDoor, pFrameDoor1, 0);
			aniDoorRight.animate(pDurationDoor, pFrameDoor1, 0);
			leftIsOpen = false;
			rightIsOpen = false;
			bothIsOpen = false;
		} else if (!leftIsOpen & !rightIsOpen) {
			// open door
			aniDoorLeft.animate(pDurationDoor, pFrameDoor, 0);
			aniDoorRight.animate(pDurationDoor, pFrameDoor, 0);
			leftIsOpen = true;
			rightIsOpen = true;
			bothIsOpen = true;
		} else {
			if (leftIsOpen) {
				leftIsOpen = !leftIsOpen;
				// close left
				aniDoorLeft.animate(pDurationDoor, pFrameDoor1, 0);
				// open right
				aniDoorRight.animate(pDurationDoor, pFrameDoor, 0);
				rightIsOpen = true;
				bothIsOpen = false;
			} else if (rightIsOpen) {
				rightIsOpen = !rightIsOpen;
				// close right
				aniDoorRight.animate(pDurationDoor, pFrameDoor1, 0);
				// open left
				aniDoorLeft.animate(pDurationDoor, pFrameDoor, 0);
				leftIsOpen = true;
				bothIsOpen = false;
			}
		}
	}
}