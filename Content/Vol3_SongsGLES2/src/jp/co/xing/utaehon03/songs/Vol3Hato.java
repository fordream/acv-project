package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3HatoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Hato extends BaseGameFragment implements
		IOnSceneTouchListener, IModifierListener<IEntity> {

	// ===========================================================
	// Constants
	// ===========================================================

	private TexturePack texturePack;
	private TexturePackTextureRegionLibrary mTexturePackTextureRegionLibrary;

	private ITextureRegion txrBackground, txrDoor, txrBoy1, txrBoy2, txrGirl1,
			txrGirl2, txrLampRight1, txrLampRight2, txrLampLeft1, txrLampLeft2,
			txrBirdUp, txrBirdDown, txrBall0, txrBall1, txrBall2;
	private ITextureRegion[] txrWindows = new ITextureRegion[16];
	private TiledTextureRegion ttrBird, ttrBall, ttrLampRight, ttrLampLeft,
			ttrBoy, ttrGirl;
	private Sprite sprDoor, sprBall;
	private MySprite[] sprWindows = new MySprite[16];
	private AnimatedSprite[] ansBird = new AnimatedSprite[5];
	private SequenceEntityModifier[] semBirdFly = new SequenceEntityModifier[5];
	MoveModifier mmfBall;
	private AnimatedSprite ansBall, sprLampRight, sprLampLeft, sprBoy, sprGirl;
	private boolean lampLeftState = false, lampRightState = false;
	private boolean isTouchBoy = true, isTouchGirl = true, isTouchBall = false,
			isCheckWindowBusy = false;
	private boolean[] isTouchWindows = new boolean[16];
	private int countPoorWindowsOpen = 0, currentWindowOpen = -1;

	private int[] rsWindows = { Vol3HatoResource.A7_06_2_A_ADR_HATO_ID,
			Vol3HatoResource.A7_06_3_B_ADR_HATO_ID,
			Vol3HatoResource.A7_06_4_C_ADR_HATO_ID,
			Vol3HatoResource.A7_06_5_D_ADR_HATO_ID,
			Vol3HatoResource.A7_06_6_E_ADR_HATO_ID,
			Vol3HatoResource.A7_06_7_F_ADR_HATO_ID,
			Vol3HatoResource.A7_06_8_G_ADR_HATO_ID,
			Vol3HatoResource.A7_06_9_H_ADR_HATO_ID, };
	private ArrayList<Integer> arlWindows, arlWindowPoors;

	private Sound mp3Boy, mp3Girl, mp3BirdFly, mp3Window, mp3Light, mp3BirdBoy,
			mp3SameBird, mp3Omedeto, mp3Ball;
	TimerHandler pTimerHandlerBird;

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, txrBackground, this
						.getVertexBufferObjectManager())));

		sprDoor = new Sprite(220, 15, txrDoor,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprDoor);
		int[] xWindows = { 219, 353, 485, 617 };
		int[] yWindows = { 17, 131, 258, 378 };
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sprWindows[count] = new MySprite(xWindows[i], yWindows[j],
						txrWindows[count], this.getVertexBufferObjectManager());
				sprWindows[count].setImgRefence(arlWindowPoors.get(count));
				sprWindows[count].setVisible(false);
				mScene.attachChild(sprWindows[count]);
				isTouchWindows[count] = true;
				count++;
			}
		}

		sprLampRight = new AnimatedSprite(740, 68, ttrLampRight,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprLampRight);

		sprLampLeft = new AnimatedSprite(124, 68, ttrLampLeft,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprLampLeft);

		sprBoy = new AnimatedSprite(-36, 276, ttrBoy,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprBoy);

		sprGirl = new AnimatedSprite(654, 258, ttrGirl,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprGirl);

		sprBall = new Sprite(318, -374, txrBall0,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sprBall);
		ansBall = new AnimatedSprite(318, 0, ttrBall,
				this.getVertexBufferObjectManager());
		ansBall.setVisible(false);
		mScene.attachChild(ansBall);

		for (int i = 0; i < ansBird.length; i++) {
			ansBird[i] = new AnimatedSprite(-150, -60, ttrBird,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(ansBird[i]);
		}
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3HatoResource.SOUND_GIMMIC,
				Vol3HatoResource.IMAGE_GIMMIC_ID,
				mTexturePackTextureRegionLibrary);
	}

	@Override
	protected void loadKaraokeResources() {

		texturePack = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "hato/gfx/").load("hato.xml");
		texturePack.loadTexture();
		mTexturePackTextureRegionLibrary = texturePack
				.getTexturePackTextureRegionLibrary();

		txrBackground = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_07_ADR_HAIKEI_ID);
		txrDoor = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_06_1_ADR_DOOR_ID);

		txrBoy1 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_03_1_ADR_BOY_ID);
		txrBoy2 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_03_2_ADR_BOY_ID);
		ttrBoy = new TiledTextureRegion(texturePack.getTexture(), txrBoy1,
				txrBoy2);

		txrGirl1 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_05_1_ADR_GIRL_ID);
		txrGirl2 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_05_2_ADR_BEAN_ID);
		ttrGirl = new TiledTextureRegion(texturePack.getTexture(), txrGirl1,
				txrGirl2);

		txrLampRight1 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_04_1_1_ADR_LAMP_RIGHT_ID);
		txrLampRight2 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_04_1_2_ADR_LAMP_RIGHT_ID);
		ttrLampRight = new TiledTextureRegion(texturePack.getTexture(),
				txrLampRight1, txrLampRight2);

		txrLampLeft1 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_04_2_1_ADR_LAMP_LEFT_ID);
		txrLampLeft2 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_04_2_2_ADR_LAMP_LEFT_ID);
		ttrLampLeft = new TiledTextureRegion(texturePack.getTexture(),
				txrLampLeft1, txrLampLeft2);

		txrBirdUp = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_05_3_ADR_HATO_ID);
		txrBirdDown = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_05_4_ADR_HATO_ID);

		ttrBird = new TiledTextureRegion(texturePack.getTexture(), txrBirdUp,
				txrBirdDown);

		arlWindows = new ArrayList<Integer>();
		arlWindowPoors = new ArrayList<Integer>();
		for (int i = 0; i < rsWindows.length; i++) {
			arlWindowPoors.add(i);
			arlWindowPoors.add(i);
		}
		for (int i = 0; i < 16; i++) {
			arlWindows.add(i);
		}
		int countWindow = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				txrWindows[countWindow] = mTexturePackTextureRegionLibrary
						.get(rsWindows[arlWindowPoors.get(countWindow)]);
				countWindow++;
			}
		}
		txrBall0 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_06_10_ADR_BALL_ID);
		txrBall1 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_06_11_ADR_BALL_1_ID);
		txrBall2 = mTexturePackTextureRegionLibrary
				.get(Vol3HatoResource.A7_06_12_ADR_BALL_2_ID);
		ttrBall = new TiledTextureRegion(texturePack.getTexture(), txrBall1,
				txrBall2);
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3Boy = loadSoundResourceFromSD(Vol3HatoResource.E70161_SYARAN);
		mp3BirdBoy = loadSoundResourceFromSD(Vol3HatoResource.E70183_KURUPO);
		mp3Girl = loadSoundResourceFromSD(Vol3HatoResource.E70163_PARA);
		mp3BirdFly = loadSoundResourceFromSD(Vol3HatoResource.E70164_BASA);
		mp3Light = loadSoundResourceFromSD(Vol3HatoResource.E70162_CATI);
		mp3Window = loadSoundResourceFromSD(Vol3HatoResource.E70165_GATYA);
		mp3SameBird = loadSoundResourceFromSD(Vol3HatoResource.E70191_PINPON2);
		mp3Omedeto = loadSoundResourceFromSD(Vol3HatoResource.E70166_HAKUSYU);
		mp3Ball = loadSoundResourceFromSD(Vol3HatoResource.E70190_PAKA);
	}

	@Override
	protected void loadKaraokeComplete() {
		resetRandomWindows();
		sprLampRight.setCurrentTileIndex(0);
		sprLampLeft.setCurrentTileIndex(0);
		sprBoy.setCurrentTileIndex(0);
		sprGirl.setCurrentTileIndex(0);
		super.loadKaraokeComplete();
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("hato/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("hato/gfx/");
		super.onCreateResources();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		mEngine.unregisterUpdateHandler(pTimerHandlerBird);
		countPoorWindowsOpen = 0;
		currentWindowOpen = -1;
		isTouchBoy = true;
		isTouchGimmic[2] = true;
		isTouchGirl = true;
		isTouchBall = false;
		isCheckWindowBusy = false;
		lampLeftState = false;
		lampRightState = false;
		for (int i = 0; i < sprWindows.length; i++) {
			if (sprWindows[i] != null) {
				sprWindows[i].setVisible(false);
			}
			isTouchWindows[i] = true;
		}
		for (int i = 0; i < ansBird.length; i++) {
			if (ansBird[i] != null) {
				ansBird[i].stopAnimation();
				ansBird[i].clearEntityModifiers();
				ansBird[i].setPosition(-150, -60);
			}
		}
		if (ansBall != null) {
			ansBall.setVisible(false);
			ansBall.setPosition(318, 0);
		}
		if (sprBall != null) {
			sprBall.clearEntityModifiers();
			sprBall.setPosition(318, -374);
		}
		if (sprLampRight != null) {
			sprLampRight.setCurrentTileIndex(0);
		}
		if (sprLampLeft != null) {
			sprLampLeft.setCurrentTileIndex(0);
		}
		if (sprBoy != null) {
			sprBoy.setCurrentTileIndex(0);
		}
		if (sprGirl != null) {
			sprGirl.setCurrentTileIndex(0);
		}

	}

	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		if (isTouchBoy) {
			isTouchBoy = false;
			updateBoyAnimation();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (isTouchBoy
					&& checkContains(sprBoy, 120, 0,
							(int) sprBoy.getWidth() - 50,
							(int) sprBoy.getHeight() - 80, pX, pY)) {
				isTouchBoy = false;
				updateBoyAnimation();
				return true;
			}
			if (isTouchGirl
					&& checkContains(sprGirl, 120, 0,
							(int) sprGirl.getWidth() - 50,
							(int) sprGirl.getHeight() - 80, pX, pY)) {
				isTouchGirl = false;
				updateGirlAnimation();
				return true;
			}
			if (checkContains(sprLampLeft, 0, 0, (int) sprLampLeft.getWidth(),
					(int) sprLampLeft.getHeight() - 298, pX, pY)) {
				updateLampLeftState();
				return true;
			}
			if (checkContains(sprLampRight, 0, 0,
					(int) sprLampRight.getWidth(),
					(int) sprLampRight.getHeight() - 298, pX, pY)) {
				updateLampRightState();
				return true;
			}
			for (int i = 0; i < sprWindows.length; i++) {
				if (isTouchWindows[i]
						&& !isCheckWindowBusy
						&& checkContains(sprWindows[i], 5, 5,
								(int) sprWindows[i].getWidth() - 5,
								(int) sprWindows[i].getHeight() - 5, pX, pY)) {
					Log.d("TEST", i + "/" + currentWindowOpen + "/"
							+ countPoorWindowsOpen);
					isTouchWindows[i] = false;
					mp3Window.play();
					sprWindows[i].setVisible(true);
					if (currentWindowOpen != -1) {
						Log.d("TESTT", sprWindows[i].getImgRefence() + "/"
								+ sprWindows[currentWindowOpen].getImgRefence());
						if (sprWindows[i].getImgRefence() != sprWindows[currentWindowOpen]
								.getImgRefence()) {
							isCheckWindowBusy = true;
							final int tmp = i;
							TimerHandler timerhandler = new TimerHandler(0.2f,
									false, new ITimerCallback() {
										@Override
										public void onTimePassed(
												TimerHandler pTimerHandler) {
											mEngine.unregisterUpdateHandler(pTimerHandler);
											sprWindows[tmp].setVisible(false);
											sprWindows[currentWindowOpen]
													.setVisible(false);
											isTouchWindows[tmp] = true;
											isTouchWindows[currentWindowOpen] = true;
											isCheckWindowBusy = false;
											currentWindowOpen = -1;
										}
									});
							this.mEngine.registerUpdateHandler(timerhandler);

						} else {
							mp3SameBird.play();
							currentWindowOpen = -1;
							countPoorWindowsOpen++;
							// all windows were opened
							if (countPoorWindowsOpen == 8) {
								mp3Omedeto.play();
								countPoorWindowsOpen = 0;
								sprBall.registerEntityModifier(mmfBall = new MoveModifier(
										3, sprBall.getX(), sprBall.getX(),
										sprBall.getY(), 0));
								mmfBall.addModifierListener(this);
							}
						}
					} else {
						currentWindowOpen = i;
					}
					return true;
				}
			}
			if (isTouchBall
					&& checkContains(sprBall, 40, 0,
							(int) sprBall.getWidth() - 40,
							(int) sprBall.getHeight() - 100, pX, pY)) {
				isTouchBall = false;
				mp3Ball.play();
				sprBall.setPosition(318, -374);
				brokenBall();
				return true;
			}
		}
		return true;
	}

	public void brokenBall() {
		ansBall.setVisible(true);
		ansBall.animate(400, 5);

		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mp3Omedeto.play();
					}
				}));

		mEngine.registerUpdateHandler(new TimerHandler(4f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						ansBall.setVisible(false);
						resetRandomWindows();
					}
				}));

	}

	public void updateGirlAnimation() {
		mp3Girl.play();
		sprGirl.setCurrentTileIndex(1);
		for (int i = 0; i < ansBird.length; i++) {
			ansBird[i].animate(300, true);
			semBirdFly[i] = new SequenceEntityModifier(new MoveModifier(2f,
					ansBird[i].getX(), 600, ansBird[i].getY(), 430),
					new MoveModifier(1.1f, 600, 960, 430, 100));
			semBirdFly[i].addModifierListener(this);
		}
		mEngine.registerUpdateHandler(pTimerHandlerBird = new TimerHandler(
				0.3f, true, new ITimerCallback() {
					int iCount = 0;

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if (iCount == ansBird.length - 1) {
							mEngine.unregisterUpdateHandler(pTimerHandlerBird);
						}
						ansBird[iCount]
								.registerEntityModifier(semBirdFly[iCount]);
						iCount++;
						mp3BirdFly.play();
					}
				}));

	}

	public void updateBoyAnimation() {
		// TODO Auto-generated method stub
		isTouchGimmic[2] = false;
		mp3Boy.play();
		sprBoy.setCurrentTileIndex(1);

		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								mp3BirdBoy.play();
							}
						});

					}
				}));

		mEngine.registerUpdateHandler(new TimerHandler(0.4f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								sprBoy.setCurrentTileIndex(0);
								isTouchBoy = true;
								isTouchGimmic[2] = true;
							}
						});
					}
				}));
	}

	public void updateLampLeftState() {
		mp3Light.play();
		this.lampLeftState = !this.lampLeftState;
		if (lampLeftState) {
			sprLampLeft.setCurrentTileIndex(1);
		} else {
			sprLampLeft.setCurrentTileIndex(0);
		}
	}

	public void updateLampRightState() {
		mp3Light.play();
		this.lampRightState = !this.lampRightState;
		if (lampRightState) {
			sprLampRight.setCurrentTileIndex(1);
		} else {
			sprLampRight.setCurrentTileIndex(0);
		}
	}

	public void resetRandomWindows() {
		Collections.shuffle(arlWindows);
		int[] xWindows = { 219, 353, 485, 617 };
		int[] yWindows = { 17, 131, 258, 378 };
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sprWindows[arlWindows.get(count)].setPosition(xWindows[i],
						yWindows[j]);
				sprWindows[arlWindows.get(count)].setVisible(false);
				isTouchWindows[count] = true;
				count++;
			}
		}
	}

	protected class MySprite extends Sprite {

		int imgRefence;

		public int getImgRefence() {
			return imgRefence;
		}

		public void setImgRefence(int imgRefence) {
			this.imgRefence = imgRefence;
		}

		public MySprite(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVer) {
			super(pX, pY, pTextureRegion, pVer);

		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier == semBirdFly[semBirdFly.length - 1]) {
			sprGirl.setCurrentTileIndex(0);
			for (int i = 0; i < ansBird.length; i++) {
				ansBird[i].setPosition(-150, -60);
				ansBird[i].stopAnimation();
			}
			isTouchGirl = true;
			return;
		}
		if (pModifier == mmfBall) {
			isTouchBall = true;
		}

	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

}
