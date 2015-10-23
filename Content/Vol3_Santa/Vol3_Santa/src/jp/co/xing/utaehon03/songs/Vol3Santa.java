/* Vol3Santa.java
 * Create on Nov 8, 2012
 */
package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

public class Vol3Santa extends BaseGameFragment implements
		IOnSceneTouchListener {

	/**
	 * SATAN
	 */
	private static final int[][] STTRES = new int[][] { Resource.PACK_KAERU,
			Resource.PACK_NEKO, Resource.PACK_DOROBOU, Resource.PACK_TORI };

	private static final int[][] RESKODOMO = new int[][] {
			Resource.PACK_KODOMO1, Resource.PACK_KODOMO2,
			Resource.PACK_KODOMO3, Resource.PACK_KODOMO4,
			Resource.PACK_KODOMO5, Resource.PACK_KODOMO6,
			Resource.PACK_KODOMO7, Resource.PACK_KODOMO8,
			Resource.PACK_KODOMO9, Resource.PACK_KODOMO10 };
	private static final int SIZE_4 = 4;

	private static final int SIZE_22 = 22;

	private static final int SIZE_10 = 10;
	/**
	 * ----------------------------------------
	 */

	private final static String TAG = " Vol3Santa ";

	private TexturePack ttpSanta1, ttpSanta2, ttpSanta3, ttpSanta4, ttpSanta5,
			ttpSanta6, ttpSanta7, ttpSanta8;

	private TexturePackTextureRegionLibrary ttpLibSanta6, ttpLibSanta7,
			ttpLibSanta8;

	private TextureRegion trBackground, trSantaStar, trSantaWater1,
			trSantaWater2, trRecMan;

	private TextureRegion trFukidashi[] = new TextureRegion[SIZE_4];

	private TextureRegion trPresent[] = new TextureRegion[SIZE_22];

	private Sprite sBackground, sSantaStar, sSantaWater1, sSantaWater2,
			recSanta, sprTemp;

	private Sprite sFukidashi[] = new Sprite[SIZE_4];

	private TiledTextureRegion ttrCity, ttrSnow, ttrShika, ttrSantaMan;

	private TiledTextureRegion ttrAnimal[] = new TiledTextureRegion[SIZE_4];

	private TiledTextureRegion ttrKodomo[] = new TiledTextureRegion[SIZE_10];

	private AnimatedSprite aniCity, aniSnow, aniShika, aniSantaMan, aniSnow1;

	private AnimalAniSprite aniAnimal[] = new AnimalAniSprite[SIZE_4];

	/**
	 * House
	 */
	private KodomoAniSprite aniKodomo[] = new KodomoAniSprite[SIZE_10];

	/**
	 * Dream of child
	 */
	private DreamSprite sPresentDream[] = new DreamSprite[SIZE_22];

	private PresentSprite sPresentSanta[] = new PresentSprite[SIZE_22];

	private ArrayList<Integer> alPreGirl;

	private ArrayList<Integer> alPreGirl1;

	private ArrayList<Integer> alPreBoy1;

	private ArrayList<Integer> alPreBoy2;

	private ArrayList<Integer> alPreBoy3;

	private ArrayList<Integer> arlKodomo;

	/**
	 * ---------------------------------------
	 */
	private int countAnimal = 0;

	private int countKodomo = 0;

	private int houseShow = 0;

	private int movePresent = 0;

	private int countBoy = 0;

	private int countGirl = 0;

	float presentFirstX;

	float touchFirstX;

	float touchFirstY;

	float presentFirstY;

	private boolean touchAnimal = false;

	private boolean touchPresent = false;

	private boolean isMovePresent = false;

	private boolean isUpPresent = false;

	private boolean touchSantaMan = true;

	private boolean touchKodomo = true;

	private boolean checkMoveHouse = false;

	private boolean touchShika = true;

	private float blue, green, red;

	/**
	 * ---------------------------------------
	 */
	private final float pointFukidashi[][] = { { 257, 310 }, { 281, 279 },
			{ 272, 94 }, { 313, 250 } };

	private final float arrPointSantaMan[][] = {
			{ 159, 153, 182, 236, 300, 286, 370, 366, 159 },
			{ 311, 196, 142, 113, 182, 232, 242, 333, 333 } };

	/**
	 * Sound for game
	 */
	private Sound A1_8_NEIKI2, A1_1_8_7_TIGAU2, A1_1_8_7_YATA1, A1_1_9_IBIKI,
			A1_2_5_TIGAU1, A1_2_5_YATA4, A1_2_6_MUNYA1, A1_3_10_TIGAU2,
			A1_3_10_UHU, A1_3_MUNYA2, A1_4_MUNYA4, A1_04_SYU2, A1_4_URESHINA,
			A1_5_MUNYA3, A1_6_9_TIGAU1, A1_6_9_URESHI, A1_06_CYU,
			A1_06_IIKODANE, A1_06_PRESENT6, A1_06_SANTADAYO, A1_07_KACHI,
			A1_7_NEIKI1, A1_08_KIRA10, A1_09_MOKIN, A1_09_BOMI2;

	/**
	 * loadKaraokeResources load texture
	 */
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources ");

		/**
		 * create list item
		 */
		alPreGirl = new ArrayList<Integer>();
		alPreGirl1 = new ArrayList<Integer>();
		arlKodomo = new ArrayList<Integer>();
		alPreBoy1 = new ArrayList<Integer>();
		alPreBoy2 = new ArrayList<Integer>();
		alPreBoy3 = new ArrayList<Integer>();

		/**
		 * Load content from resource
		 */
		(ttpSanta1 = loaderFromSource("santa1.xml")).loadTexture();
		(ttpSanta2 = loaderFromSource("santa2.xml")).loadTexture();
		(ttpSanta3 = loaderFromSource("santa3.xml")).loadTexture();
		(ttpSanta4 = loaderFromSource("santa4.xml")).loadTexture();
		(ttpSanta5 = loaderFromSource("santa5.xml")).loadTexture();
		(ttpSanta6 = loaderFromSource("santa6.xml")).loadTexture();
		(ttpSanta7 = loaderFromSource("santa7.xml")).loadTexture();
		(ttpSanta8 = loaderFromSource("santa8.xml")).loadTexture();

		/**
		 * load
		 */
		ttpLibSanta6 = ttpSanta6.getTexturePackTextureRegionLibrary();
		ttpLibSanta7 = ttpSanta7.getTexturePackTextureRegionLibrary();

		ttpLibSanta8 = ttpSanta8.getTexturePackTextureRegionLibrary();
		trBackground = ttpLibSanta8.get(Resource.A1_12_IPHONE_HAIKEI_ID);

		ttrCity = getTiledTextureFromPacker(ttpSanta6,
				Resource.A1_08_1_IPHONE_CITY_ID,
				Resource.A1_08_2_IPHONE_CITY_ID);

		ttrSnow = getTiledTextureFromPacker(ttpSanta8,
				Resource.A1_11_1_IPHONE_SNOW_ID,
				Resource.A1_11_2_IPHONE_SNOW_ID);

		/**
		 * 
		 */
		for (int i = 0; i < SIZE_4; i++) {
			ttrAnimal[i] = getTiledTextureFromPacker(ttpSanta6, STTRES[i]);
		}

		/**
		 * 
		 */
		for (int i = 0; i < trFukidashi.length; i++) {
			trFukidashi[i] = ttpLibSanta6.get(Resource.PACK_FUKIDASHI[i]);
		}

		/**
		 * load santa
		 */
		final TexturePack[] sTexture = new TexturePack[] { ttpSanta1,
				ttpSanta1, ttpSanta2, ttpSanta2, ttpSanta3, ttpSanta3,
				ttpSanta4, ttpSanta4, ttpSanta5, ttpSanta5 };

		for (int i = 0; i < SIZE_10; i++) {
			ttrKodomo[i] = getTiledTextureFromPacker(sTexture[i], RESKODOMO[i]);
		}

		/**
		 * Load santa present
		 */
		for (int i = 0; i < trPresent.length; i++) {
			trPresent[i] = ttpLibSanta6.get(Resource.PACK_PRESENT[i]);
		}

		trSantaStar = ttpLibSanta7.get(Resource.A1_06_2_2_IPHONE_SANTA_ID);
		trSantaWater1 = ttpLibSanta7.get(Resource.A1_06_2_3_IPHONE_SANTA_ID);
		trSantaWater2 = ttpLibSanta7.get(Resource.A1_06_3_2_IPHONE_SANTA_ID);

		ttrShika = getTiledTextureFromPacker(ttpSanta6,
				Resource.A1_07_1_IPHONE_SHIKA_ID,
				Resource.A1_07_2_IPHONE_SHIKA_ID);

		ttrSantaMan = getTiledTextureFromPacker(ttpSanta7,
				Resource.A1_06_1_IPHONE_SANTA_ID,
				Resource.A1_06_2_1_IPHONE_SANTA_ID,
				Resource.A1_06_3_1_IPHONE_SANTA_ID);

		trRecMan = ttpLibSanta6.get(Resource.RECTANGLE_MAN_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene.attachChild((sBackground = createSpire(0, 0, trBackground)));
		mScene.attachChild((aniSnow1 = createAnimatedSprite(-480, 0, ttrSnow)));
		aniSnow1.animate(1000, -1);

		for (int i = 0; i < aniKodomo.length; i++) {
			aniKodomo[i] = new KodomoAniSprite(-18, 48, ttrKodomo[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(aniKodomo[i]);
			setvisiableAniSprite(false, aniKodomo[i]);
			aniKodomo[i].setIdSound(i);
			aniKodomo[i].setImgIndex(acvCheckIn(i, 0, 5, 6, 7, 8) ? 1 : 0);
		}

		/**
		 * get red, blue, green
		 */
		blue = (recSanta = createSpire(427, 170, trRecMan)).getBlue();
		green = recSanta.getGreen();
		red = recSanta.getRed();

		mScene.attachChild(recSanta);

		recSanta.attachChild((sSantaStar = createSpire(0, 0, trSantaStar)));
		recSanta.attachChild((sSantaWater1 = createSpire(40, -10, trSantaWater1)));
		recSanta.attachChild((sSantaWater2 = createSpire(40, -10, trSantaWater2)));

		setvisiableSprite(false, sSantaStar, sSantaWater1, sSantaWater2);

		aniShika = new AnimatedSprite(-20, 136, ttrShika,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown() && aniShika.isVisible()
						&& touchShika) {
					Log.d(TAG, "touch shika!!!!!!!!!!!");
					A1_07_KACHI.play();
					int index = (aniShika.getCurrentTileIndex() == 0) ? 1 : 0;
					aniShika.setCurrentTileIndex(index);
				}
				return false;
			};
		};

		mScene.registerTouchArea(aniShika);
		aniShika.setVisible(false);
		recSanta.attachChild(aniShika);

		aniSantaMan = new AnimatedSprite(34, -10, ttrSantaMan,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()
						&& touchSantaMan
						&& aniSantaMan.isVisible()
						&& checkContainsPolygon(aniSantaMan, arrPointSantaMan,
								9, pTouchAreaLocalX, pTouchAreaLocalY)) {
					Log.d(TAG, "touch santa man: \ntouch santa man:!!!!!!!!!!!");

					A1_06_SANTADAYO.play();

					touchSantaMan = false;

					mScene.registerUpdateHandler(new TimerHandler(2.0f,
							new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler arg0) {
									touchSantaMan = true;
								}
							}));
				}
				return false;
			};
		};

		recSanta.attachChild(aniSantaMan);
		mScene.registerTouchArea(aniSantaMan);
		mScene.registerTouchArea(recSanta);

		aniSantaMan.setVisible(false);

		recSanta.setScale(0.1f);
		recSanta.setVisible(false);

		mScene.attachChild((aniSnow = createAnimatedSprite(0, 0, ttrSnow)));
		aniSnow.animate(1000, -1);

		for (int i = 0; i < sFukidashi.length; i++) {
			sFukidashi[i] = createSpire(pointFukidashi[i][0],
					pointFukidashi[i][1], trFukidashi[i]);

			mScene.attachChild(sFukidashi[i]);

			setvisiableSprite(false, sFukidashi[i]);
		}

		for (int i = 0; i < aniAnimal.length; i++) {
			aniAnimal[i] = new AnimalAniSprite(-186, -22, ttrAnimal[i],
					this.getVertexBufferObjectManager());

			mScene.registerTouchArea(aniAnimal[i]);
			mScene.attachChild(aniAnimal[i]);
		}

		for (int i = 0; i < sPresentDream.length; i++) {
			sPresentDream[i] = new DreamSprite(0, 0, trPresent[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(sPresentDream[i]);

			sPresentDream[i].setImgIndex(i);
			sPresentDream[i].setVisible(false);

			sPresentSanta[i] = new PresentSprite(0, 0, trPresent[i],
					this.getVertexBufferObjectManager(), "sPresentSanta" + i);
			recSanta.attachChild(sPresentSanta[i]);

			sPresentSanta[i].setImgIndex(i);
			sPresentSanta[i].setVisible(false);

			float scale = acvCheckIn(i, 1, 2) ? 0.7f : 0.9f;

			if (acvCheckIn(i, 21, 7, 0, 15, 11, 12, 4, 13, 5, 6)) {
				scale = 0.8f;
			}

			sPresentDream[i].setScale(scale);
			sPresentSanta[i].setScale(scale);

			if (acvCheckIn(i, 4, 11, 12)) {
				alPreBoy1.add(i);
			} else if (acvCheckIn(i, 17, 19, 20, 8)) {
				alPreBoy2.add(i);
			} else if (acvCheckIn(i, 0, 7, 16)) {
				alPreBoy3.add(i);
			} else if (acvCheckIn(i, 1, 2, 14, 21)) {
				alPreGirl1.add(i);
			} else if (acvCheckIn(i, 6, 3, 5, 9, 10, 13, 15, 18)) {
				alPreGirl.add(i);
			}

			mScene.registerTouchArea(sPresentSanta[i]);
		}

		aniCity = new AnimatedSprite(342, 491, ttrCity,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					A1_08_KIRA10.play();
					int index = (aniCity.getCurrentTileIndex() == 0) ? 1 : 0;
					aniCity.setCurrentTileIndex(index);
				}

				return false;
			};
		};

		mScene.registerTouchArea(aniCity);
		mScene.attachChild(aniCity);

		addGimmicsButton(mScene, Resource.SOUND_GIMMIC, Resource.IMAGE_GIMMIC,
				ttpLibSanta6);

		Log.d(TAG, "loadKaraokeScene end ");
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	/**
	 * onPauseGame
	 */
	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		stopSound();
		resetData();
		initial();

		mScene.unregisterUpdateHandler(timerHandlerForGame);
		timerHandlerForGame = null;
	}

	@Override
	public void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		getActivity().registerReceiver(mReceiver, filter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			}

			if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
				openNewgame();
			}
		}
	};

	/*
	 * TruongVV
	 */
	private void openNewgame() {
		// for (PresentSprite presentSprite : sPresentSanta) {
		// if (presentSprite != null) {
		// presentSprite.setVisible(false);
		// }
		// }
		initial();

		arlKodomo = getRandomArray(10, 10);
		if (timerHandlerForGame == null) {
			timerHandlerForGame = new TimerHandler(0.3f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					loadRandomKodomo(countKodomo);
				}
			});
		}

		if (mScene != null)
			mScene.registerUpdateHandler(timerHandlerForGame);
	}

	private TimerHandler timerHandlerForGame;

	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mReceiver);
	}

	private boolean isFrist = true;

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		presentFirstX = 0;
		touchFirstX = 0;
		touchFirstY = 0;
		presentFirstY = 0;
		arlKodomo.clear();

		if (isFrist) {
			isFrist = false;
			openNewgame();
		} else {
			if (!UTils.haveScreenLock(this)) {
				openNewgame();
			} else {
				mScene.unregisterUpdateHandler(timerHandlerForGame);
				timerHandlerForGame = null;
			}
		}
	}

	private void initial() {
		touchAnimal = false;
		touchPresent = false;
		isUpPresent = false;
		isMovePresent = false;
		touchSantaMan = true;
		touchKodomo = true;
		checkMoveHouse = false;
		touchShika = true;
		countAnimal = 0;
		countKodomo = 0;
		houseShow = 0;
		movePresent = 0;
		countBoy = 0;
		countGirl = 0;

		UTils.setVisible(sPresentDream, alPreBoy1);
		UTils.setVisible(sPresentDream, alPreBoy2);
		UTils.setVisible(sPresentDream, alPreBoy3);
		UTils.setVisible(sPresentDream, alPreGirl);
		UTils.setVisible(sPresentDream, alPreGirl1);
	}

	/**
	 * reset all data
	 */
	private void resetData() {

		PointF pointF = new PointF(recSanta.getmXFirst(), recSanta.getmYFirst());

		UTils.clearEntityModifiersVisible(recSanta, pointF);
		UTils.clearEntityModifiersVisible(sprTemp, null);

		setvisiableSprite(false, sFukidashi[0], sFukidashi[1], sFukidashi[2],
				sFukidashi[3]);

		for (int j = 0; j < aniAnimal.length; j++) {
			if (aniAnimal[j] != null) {
				aniAnimal[j].clearEntityModifiers();
				aniAnimal[j].setPosition(aniAnimal[j].getmXFirst(),
						aniAnimal[j].getmYFirst());
				aniAnimal[j].setCurrentTileIndex(0);
			}
		}

		for (int i = 0; i < sPresentSanta.length; i++) {
			if (sPresentSanta[i] != null) {
				sPresentSanta[i].setPosition(0, 0);
				sPresentSanta[i].setVisible(false);
			}
		}

		for (int i = 0; i < sPresentDream.length; i++) {
			sPresentDream[i].setVisible(false);
			sPresentDream[i].setPosition(sPresentDream[i].getmXFirst(),
					sPresentDream[i].getmYFirst());
		}

		UTils.setVisible(sPresentDream, alPreBoy1);
		UTils.setVisible(sPresentDream, alPreBoy2);
		UTils.setVisible(sPresentDream, alPreBoy3);
		UTils.setVisible(sPresentDream, alPreGirl1);

		for (int i = 0; i < aniKodomo.length; i++) {
			aniKodomo[i].clearEntityModifiers();
			aniKodomo[i].setCurrentTileIndex(0);
			aniKodomo[i].setVisible(false);
		}

		UTils.clearEntityModifiersVisible(sSantaStar, null);
		UTils.clearEntityModifiersVisible(sSantaWater1, null);
		UTils.clearEntityModifiersVisible(sSantaWater2, null);

		UTils.setCurrentTileIndex(aniShika, 0);
		UTils.setCurrentTileIndex(aniSantaMan, 0);
		UTils.setCurrentTileIndex(aniCity, 0);

		if (mScene != null) {
			mScene.clearEntityModifiers();
		}
	}

	public void loadRandomKodomo(final int index) {
		houseShow = index;
		Collections.shuffle(alPreBoy1);
		Collections.shuffle(alPreBoy2);
		Collections.shuffle(alPreBoy3);
		Collections.shuffle(alPreGirl);
		Collections.shuffle(alPreGirl1);

		aniKodomo[arlKodomo.get(index)].setVisible(true);

		touchKodomo = true;
		A1_04_SYU2.play();

		if (aniKodomo[arlKodomo.get(index)].getImgIndex() == 1) {
			UTils.setPositionVisible(sPresentSanta[alPreBoy3.get(0)], 380, 50,
					true);
			UTils.setPositionVisible(sPresentSanta[alPreBoy1.get(0)], 310, 20,
					true);
			UTils.setPositionVisible(sPresentSanta[alPreBoy2.get(0)], 370, 115,
					true);
		} else {
			UTils.setPositionVisible(sPresentSanta[alPreGirl1.get(0)], 303, 5,
					true);
			UTils.setPositionVisible(sPresentSanta[alPreGirl.get(0)], 390, 35,
					true);
			UTils.setPositionVisible(sPresentSanta[alPreGirl.get(1)], 370, 116,
					true);
		}

		A1_09_BOMI2.stop();

		moveMan();

		sFukidashi[0].setVisible(true);

		mScene.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sFukidashi[1].setVisible(true);

					}
				}), new DelayModifier(0.2f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sFukidashi[3].setVisible(true);
					}
				}), new DelayModifier(0.6f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sFukidashi[2].setVisible(true);
						loadRandomPresent(index);
						isTouchGimmic[2] = false;
						touchAnimal = false;
						moveAnimal(countAnimal);
					}
				})));
	}

	public void moveMan() {
		Log.e(TAG, "moveMan.........");
		recSanta.setPosition(650, 340);
		aniShika.setVisible(false);
		aniShika.setCurrentTileIndex(0);
		aniSantaMan.setVisible(false);
		recSanta.setVisible(true);
		touchSantaMan = false;
		touchShika = false;

		recSanta.setColor(red, green, blue, 1);
		recSanta.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), new ParallelEntityModifier(
						new ScaleModifier(1.5f, 0.1f, 0.3f), new MoveModifier(
								1.5f, recSanta.getX(), recSanta.getX() - 400,
								recSanta.getY(), recSanta.getY() - 80,
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {
									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										recSanta.setFlippedHorizontal(true);
										if (aniKodomo[arlKodomo.get(houseShow)]
												.getImgIndex() == 1) {
											sPresentSanta[alPreBoy3.get(0)]
													.setPosition(52, 34);
											sPresentSanta[alPreBoy1.get(0)]
													.setPosition(140, 16);
											sPresentSanta[alPreBoy2.get(0)]
													.setPosition(54, 120);
										} else {
											sPresentSanta[alPreGirl.get(0)]
													.setPosition(50, 26);
											sPresentSanta[alPreGirl1.get(0)]
													.setPosition(140, 16);
											sPresentSanta[alPreGirl.get(1)]
													.setPosition(50, 112);
										}
										moveBackMan();
									}
								}))));

	}

	private void moveBackMan() {
		checkMoveHouse = true;
		recSanta.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), new ParallelEntityModifier(
						new ScaleModifier(2.0f, 0.3f, 0.1f), new MoveModifier(
								2.0f, recSanta.getX(), recSanta.getX() + 400,
								recSanta.getY(), recSanta.getY() - 200,
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {
									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										recSanta.setFlippedHorizontal(false);
										if (aniKodomo[arlKodomo.get(houseShow)]
												.getImgIndex() == 1) {
											sPresentSanta[alPreBoy3.get(0)]
													.setPosition(381, 50);
											sPresentSanta[alPreBoy1.get(0)]
													.setPosition(310, 20);
											sPresentSanta[alPreBoy2.get(0)]
													.setPosition(370, 115);
										} else {
											sPresentSanta[alPreGirl.get(0)]
													.setPosition(390, 35);
											sPresentSanta[alPreGirl1.get(0)]
													.setPosition(303, 0);
											sPresentSanta[alPreGirl.get(1)]
													.setPosition(375, 116);
										}
										moveFirstMan();
									}
								}))));
	}

	private void moveFirstMan() {
		aniShika.setVisible(true);
		aniSantaMan.setVisible(true);

		recSanta.setColor(Color.TRANSPARENT);
		recSanta.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), new ParallelEntityModifier(
						new ScaleModifier(1.5f, 0.1f, 1f), new MoveModifier(
								1.5f, recSanta.getX(), recSanta.getmXFirst(),
								recSanta.getY(), recSanta.getmYFirst(),
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {

									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {

										touchPresent = true;
										touchSantaMan = true;
										touchShika = true;
									}
								}))));
	}

	private void loadRandomPresent(int index) {
		if (aniKodomo[arlKodomo.get(houseShow)].getImgIndex() == 1) {
			// hien thi do choi cho em be nam
			int i = (countBoy == 0) ? alPreBoy1.get(0)
					: ((countBoy == 1) ? alPreBoy2.get(0) : alPreBoy3.get(0));
			countBoy = (countBoy == 0) ? 1 : ((countBoy == 1) ? 2 : 0);
			sPresentDream[i].setPosition(356, 155);
			sPresentDream[i].setVisible(true);
		} else {
			// hien thi do choi cho em be nu
			int i = countGirl == 0 ? alPreGirl.get(0) : alPreGirl1.get(0);
			countGirl = (countGirl == 0) ? 1 : 0;

			sPresentDream[i].setPosition(360, 140);
			sPresentDream[i].setVisible(true);
		}
	}

	public void moveAnimal(final int index) {
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				aniAnimal[index].detachSelf();
				aniAnimal[index].setPosition(-150, -40);
				aniKodomo[arlKodomo.get(houseShow)]
						.attachChild(aniAnimal[index]);
			}
		});

		aniAnimal[index].animate(new long[] { 400, 400 }, new int[] { 0, 1 },
				-1);
		aniAnimal[index].clearEntityModifiers();
		aniAnimal[index].registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.2f), new MoveXModifier(1.6f, -150, 180,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								A1_09_BOMI2.play();

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								aniAnimal[index].stopAnimation(2);
								touchAnimal = true;
								isTouchGimmic[2] = true;

							}
						})));

	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			for (int i = 0; i < aniKodomo.length; i++) {

				if (checkContains(aniKodomo[i], 130, 330, 270, 470, pX, pY)
						&& aniKodomo[i].isVisible() && touchKodomo) {
					touchKodomo = false;
					switch (aniKodomo[i].getIdSound()) {
					case 0:
					case 8:
						A1_1_9_IBIKI.play();
						break;
					case 1:
					case 5:
						A1_2_6_MUNYA1.play();
						break;
					case 2:
						A1_3_MUNYA2.play();
						break;
					case 3:
					case 9:
						A1_4_MUNYA4.play();
						break;
					case 4:
						A1_5_MUNYA3.play();
						break;
					case 6:
						A1_7_NEIKI1.play();
						break;
					case 7:
						A1_8_NEIKI2.play();
						break;

					default:
						break;
					}
					mScene.registerUpdateHandler(new TimerHandler(2.5f,
							new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler arg0) {
									touchKodomo = true;
								}
							}));
				}
			}
		}

		return false;
	}

	public static ArrayList<Integer> getRandomArray(int input, int iNumbers) {
		if (input < iNumbers) {
			System.out.println("error: lenght array > iNumbers");
			return null;
		} else {
			ArrayList<Integer> alOuput = new ArrayList<Integer>();
			ArrayList<Integer> alInput = new ArrayList<Integer>();
			Random rd = new Random();

			for (int i = 0; i < input; i++) {
				alInput.add(i);
			}

			for (int i = 0; i < iNumbers; i++) {
				int index = rd.nextInt(alInput.size());
				alOuput.add(alInput.get(index));
				alInput.remove(index);
			}
			return alOuput;
		}
	}

	private void setvisiableSprite(boolean value, Sprite... sprites) {
		if (sprites != null) {
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].setPosition(sprites[i].getmXFirst(),
						sprites[i].getmYFirst());
				sprites[i].setVisible(value);

			}
		}
	}

	private void setvisiableAniSprite(boolean value,
			AnimatedSprite... aniSprites) {
		if (aniSprites != null) {
			for (int i = 0; i < aniSprites.length; i++) {
				aniSprites[i].setVisible(value);
				aniSprites[i].setPosition(aniSprites[i].getmXFirst(),
						aniSprites[i].getmYFirst());
				aniSprites[i].setCurrentTileIndex(0);

			}

		}
	}

	PresentSprite presentSprite;

	// ///////////////////////////////////////////////////
	// Inner Class
	// ///////////////////////////////////////////////////
	public class KodomoAniSprite extends AnimatedSprite {

		int index;
		int idSound;

		public int getIdSound() {
			return idSound;
		}

		public void setIdSound(int idSound) {
			this.idSound = idSound;
		}

		public int getImgIndex() {
			return index;
		}

		public void setImgIndex(int index) {
			this.index = index;
		}

		public KodomoAniSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTiledTextureRegion, verBufObjManager);
		}
	}

	public class PresentSprite extends Sprite {
		String name;
		int index;

		public int getImgIndex() {
			return index;
		}

		public void setImgIndex(int index) {
			this.index = index;
		}

		public PresentSprite(float pX, float pY, TextureRegion pTextureRegion,
				VertexBufferObjectManager verBufObjManager, String name) {
			super(pX, pY, pTextureRegion, verBufObjManager);
			this.name = name;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			presentSprite = this;
			int action = pSceneTouchEvent.getAction();
			final float pX = pSceneTouchEvent.getX();
			final float pY = pSceneTouchEvent.getY();
			switch (action) {
			// TODO
			case TouchEvent.ACTION_CANCEL:
				PresentSprite.this.detachSelf();
				Log.d(TAG, "ACTION_CANCEL uuuuuu2222222: ");
				PresentSprite.this.setPosition(presentFirstX, presentFirstY);
				recSanta.attachChild(PresentSprite.this);
				touchPresent = true;
				break;
			case TouchEvent.ACTION_DOWN:
				Log.d(TAG, "ACTION_DOWN:::::" + name);
				touchFirstX = pX;
				touchFirstY = pY;
				Log.d(TAG, "pX:::: " + pX + "/ pY::::" + pY);
			case TouchEvent.ACTION_MOVE:

				Log.d(TAG, "ACTION_MOVE:::::" + name);

				if ((pX < touchFirstX - 15 || pX > touchFirstX + 15
						|| pY < touchFirstY - 15 || pY > touchFirstY + 15)
						&& touchPresent
						&& this.contains(pX, pY)
						&& this.isVisible()) {

					Log.d(TAG, "pX:::: " + pX + "/ pY::::" + pY);
					Log.d(TAG, "movePresent:::: " + movePresent);
					touchPresent = false;
					presentFirstX = this.getX();
					presentFirstY = this.getY();
					isMovePresent = true;
					isUpPresent = true;
					A1_06_CYU.play();

					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							PresentSprite.this.detachSelf();
							PresentSprite.this.setPosition(pX
									- (PresentSprite.this.getWidth() / 2), pY
									- (PresentSprite.this.getHeight() / 2));

							mScene.attachChild(PresentSprite.this);
						}
					});
				}
				if (isMovePresent) {
					Log.d(TAG, "ACTION_MOVE MMMMMMMMMMMMMM: index " + index);
					Log.d(TAG, "pX:: " + pX + "pY:::" + pY);
					this.setPosition(pX - (this.getWidth() / 2),
							pY - (this.getHeight() / 2));
				}

				break;
			case TouchEvent.ACTION_UP:

				Log.d(TAG, "ACTION_UP ::: ");
				Log.d(TAG, "presentFirstX ::: " + presentFirstX
						+ " presentFirstY:::" + presentFirstY);

				if (isUpPresent) {
					isUpPresent = false;
					isMovePresent = false;

					DreamSprite[] sCheckDreamSprite = new DreamSprite[] {
							sPresentDream[alPreBoy1.get(0)],
							sPresentDream[alPreBoy2.get(0)],
							sPresentDream[alPreBoy3.get(0)],
							sPresentDream[alPreGirl.get(0)],
							sPresentDream[alPreGirl1.get(0)] };
					if (this.collidesWithCollection(sCheckDreamSprite)) {
						touchKodomo = false;

						this.setPosition(pX - (this.getWidth() / 2),
								pY - (this.getHeight() / 2));

						if (this.checkIndexAndVisible(sCheckDreamSprite)) {
							touchSantaMan = false;

							Log.e(TAG, "co vao day ko&&&&&&&&&&&&&&&&&&&");

							int witch = aniKodomo[arlKodomo.get(houseShow)]
									.getIdSound();

							if (acvCheckIn(witch, 0, 6, 7)) {
								A1_1_8_7_YATA1.play();
							} else if (acvCheckIn(witch, 1, 4)) {
								A1_2_5_YATA4.play();
							} else if (acvCheckIn(witch, 2, 9)) {
								A1_3_10_UHU.play();
							} else if (acvCheckIn(witch, 3)) {
								A1_4_URESHINA.play();
							} else if (acvCheckIn(witch, 5, 8)) {
								A1_6_9_URESHI.play();
							}

							aniKodomo[arlKodomo.get(houseShow)]
									.setCurrentTileIndex(1);
							aniSantaMan.setCurrentTileIndex(1);
							sSantaStar.setVisible(true);
							sSantaWater1.setVisible(true);
							sSantaStar
									.registerEntityModifier(new SequenceEntityModifier(
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(0.2f, 0.0f, 1.0f),
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(0.2f, 0.0f, 1.0f)));
							sSantaWater1
									.registerEntityModifier(new SequenceEntityModifier(
											new DelayModifier(0.3f),
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(0.2f, 0.0f, 1.0f),
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(
													0.2f,
													0.0f,
													1.0f,
													new IEntityModifierListener() {
														@Override
														public void onModifierStarted(
																IModifier<IEntity> arg0,
																IEntity arg1) {
														}

														@Override
														public void onModifierFinished(
																IModifier<IEntity> arg0,
																IEntity arg1) {
															aniSantaMan
																	.setCurrentTileIndex(0);
															sSantaStar
																	.setVisible(false);
															sSantaWater1
																	.setVisible(false);

															Log.d(TAG,
																	" touchPresent:::"
																			+ touchPresent
																			+ "/isMovePresent::: "
																			+ isMovePresent);
															runOnUpdateThread(new Runnable() {

																@Override
																public void run() {
																	PresentSprite.this
																			.detachSelf();
																	Log.d(TAG,
																			"ACTION_UP uu222222222222222: ");
																	PresentSprite.this
																			.setPosition(
																					presentFirstX,
																					presentFirstY);
																	recSanta.attachChild(PresentSprite.this);
																	PresentSprite.this
																			.setVisible(false);

																}
															});
															if (sPresentDream[alPreBoy1
																	.get(0)]
																	.isVisible()) {
																moveEndGame(sPresentDream[alPreBoy1
																		.get(0)]);

															} else if (sPresentDream[alPreBoy2
																	.get(0)]
																	.isVisible()) {
																moveEndGame(sPresentDream[alPreBoy2
																		.get(0)]);

															} else if (sPresentDream[alPreBoy3
																	.get(0)]
																	.isVisible()) {
																moveEndGame(sPresentDream[alPreBoy3
																		.get(0)]);
															} else if (sPresentDream[alPreGirl
																	.get(0)]
																	.isVisible()) {
																moveEndGame(sPresentDream[alPreGirl
																		.get(0)]);
															} else if (sPresentDream[alPreGirl1
																	.get(0)]
																	.isVisible()) {
																moveEndGame(sPresentDream[alPreGirl1
																		.get(0)]);
															}
														}
													})));

						} else {

							// neu sai
							int index = aniKodomo[arlKodomo.get(houseShow)]
									.getIdSound();

							if (acvCheckIn(index, 0, 6, 7)) {
								A1_1_8_7_TIGAU2.play();
							} else if (acvCheckIn(index, 1, 3, 4)) {
								A1_2_5_TIGAU1.play();
							} else if (acvCheckIn(index, 2, 9)) {
								A1_3_10_TIGAU2.play();
							} else if (acvCheckIn(index, 5, 8)) {
								A1_6_9_TIGAU1.play();
							}

							aniKodomo[arlKodomo.get(houseShow)]
									.setCurrentTileIndex(2);

							aniSantaMan.setCurrentTileIndex(2);
							sSantaWater2.setVisible(true);

							sSantaWater2
									.registerEntityModifier(new SequenceEntityModifier(
											new DelayModifier(0.3f),
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(0.2f, 0.0f, 1.0f),
											new AlphaModifier(0.2f, 1.0f, 0.0f),
											new AlphaModifier(
													0.2f,
													0.0f,
													1.0f,
													new IEntityModifierListener() {
														@Override
														public void onModifierStarted(
																IModifier<IEntity> arg0,
																IEntity arg1) {
														}

														@Override
														public void onModifierFinished(
																IModifier<IEntity> arg0,
																IEntity arg1) {
															sSantaWater2
																	.setVisible(false);
															aniKodomo[arlKodomo
																	.get(houseShow)]
																	.setCurrentTileIndex(0);
															aniSantaMan
																	.setCurrentTileIndex(0);
															runOnUpdateThread(new Runnable() {

																@Override
																public void run() {
																	PresentSprite.this
																			.detachSelf();
																	Log.d(TAG,
																			"ACTION_UP uu22222 ");
																	PresentSprite.this
																			.setPosition(
																					presentFirstX,
																					presentFirstY);
																	recSanta.attachChild(PresentSprite.this);
																	touchPresent = true;
																	touchKodomo = true;
																}
															});
														}
													})));
						}
					} else {
						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								PresentSprite.this.detachSelf();
								Log.d(TAG, "ACTION_UP uuuuuu2222222: ");
								PresentSprite.this.setPosition(presentFirstX,
										presentFirstY);
								recSanta.attachChild(PresentSprite.this);
								touchPresent = true;
							}
						});
					}
				}
				break;
			default:
				break;
			}
			return true;
		}

		private boolean checkIndexAndVisible(DreamSprite[] sCheckDreamSprite) {
			for (DreamSprite dreamSprite : sCheckDreamSprite) {
				if ((this.getImgIndex() == dreamSprite.getImgIndex() && dreamSprite
						.isVisible())) {
					return true;
				}
			}
			return false;
		}

		private boolean collidesWithCollection(DreamSprite[] sCheckDreamSprite) {
			for (DreamSprite dreamSprite : sCheckDreamSprite) {
				if (this.collidesWith(dreamSprite)) {
					return true;
				}
			}
			return false;
		}
	}

	private void moveEndGame(final Sprite sprPresent) {
		(sprTemp = sprPresent).setVisible(true);

		sprTemp.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.6f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						A1_06_PRESENT6.play();
					}
				}), new MoveModifier(1.5f, sprPresent.getX(),
						sprPresent.getX() - 110, sprPresent.getY(), sprPresent
								.getY() + 250), new DelayModifier(0.3f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkMoveHouse = false;
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								for (int j = 0; j < sFukidashi.length; j++) {
									setvisiableSprite(false, sFukidashi[j],
											sprTemp, sprPresent);
								}

								float timeDelay = 0.5f;
								countKodomo++;
								if (countKodomo == 10) {
									countKodomo = 0;
									A1_06_IIKODANE.play();
									timeDelay = 3.5f;
									setvisiableSprite(false,
											sPresentSanta[alPreBoy1.get(0)],
											sPresentSanta[alPreBoy2.get(0)],
											sPresentSanta[alPreBoy3.get(0)],
											sPresentSanta[alPreGirl.get(0)],
											sPresentSanta[alPreGirl.get(1)],
											sPresentSanta[alPreGirl1.get(0)]);
								}
								moveHouse(timeDelay);
							}
						})));
	}

	private void moveHouse(float timeDelay) {
		touchAnimal = false;
		isTouchGimmic[2] = false;

		A1_09_BOMI2.stop();

		aniKodomo[arlKodomo.get(houseShow)]
				.registerEntityModifier(new SequenceEntityModifier(
						new DelayModifier(timeDelay), new MoveXModifier(1.5f,
								aniKodomo[arlKodomo.get(houseShow)].getX(),
								-430)));

		recSanta.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(timeDelay), new ParallelEntityModifier(
						new ScaleModifier(2.5f, 1f, 0.1f), new MoveXModifier(
								2.5f, recSanta.getX(), -530,
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										touchShika = false;

										A1_09_BOMI2.stop();

									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										setvisiableSprite(
												false,
												sPresentSanta[alPreBoy1.get(0)],
												sPresentSanta[alPreBoy2.get(0)],
												sPresentSanta[alPreBoy3.get(0)],
												sPresentSanta[alPreGirl.get(0)],
												sPresentSanta[alPreGirl.get(1)],
												sPresentSanta[alPreGirl1.get(0)]);

										setvisiableAniSprite(false,
												aniKodomo[arlKodomo
														.get(houseShow)]);

										Log.d(TAG, "countKodomo::::"
												+ countKodomo);

										aniAnimal[countAnimal].setPosition(
												-150, -40);
										aniAnimal[countAnimal]
												.setCurrentTileIndex(0);
										aniAnimal[countAnimal]
												.clearEntityModifiers();
										countAnimal++;

										A1_09_BOMI2.stop();
										countAnimal = (countAnimal == 4) ? 0
												: countAnimal;
										loadRandomKodomo(countKodomo);
									}
								}))));

	}

	public class DreamSprite extends Sprite {
		int index;

		public int getImgIndex() {
			return index;
		}

		public void setImgIndex(int index) {
			this.index = index;
		}

		public DreamSprite(float pX, float pY, TextureRegion pTextureRegion,
				VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTextureRegion, verBufObjManager);
		}
	}

	public class AnimalAniSprite extends AnimatedSprite {
		int index;

		public int getImgIndex() {
			return index;
		}

		public void setImgIndex(int index) {
			this.index = index;
		}

		public AnimalAniSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTiledTextureRegion, verBufObjManager);
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (pSceneTouchEvent.isActionDown() && touchAnimal) {
				touchAnimal = false;
				isTouchGimmic[2] = false;
				touchAnimal();
			}
			return true;
		}
	}

	public void touchAnimal() {
		aniAnimal[countAnimal].setCurrentTileIndex(3);
		A1_09_MOKIN.play();

		aniAnimal[countAnimal]
				.registerEntityModifier(new SequenceEntityModifier(
						new DelayModifier(0.1f), new MoveYModifier(0.1f,
								aniAnimal[countAnimal].getY(),
								aniAnimal[countAnimal].getY() - 50),
						new MoveYModifier(0.6f,
								aniAnimal[countAnimal].getY() - 50,
								aniAnimal[countAnimal].getY() + 500,
								new IEntityModifierListener() {
									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {

									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										aniAnimal[countAnimal].setPosition(
												-150, -40);
										aniAnimal[countAnimal]
												.setCurrentTileIndex(0);
										countAnimal++;
										countAnimal = (countAnimal <= 3) ? countAnimal
												: 0;
										moveAnimal(countAnimal);
									}
								})));
	}

	/**
	 * Load threw button
	 */
	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimmic[2] && checkMoveHouse) {
			isTouchGimmic[2] = false;
			touchAnimal = false;
			touchAnimal();
		}
	}

	/**
	 * Load Kraokie
	 */
	@Override
	protected void loadKaraokeSound() {
		A1_8_NEIKI2 = loadSoundResourceFromSD(Resource.A1_8_NEIKI2);
		A1_1_8_7_TIGAU2 = loadSoundResourceFromSD(Resource.A1_1_8_7_TIGAU2);
		A1_1_8_7_YATA1 = loadSoundResourceFromSD(Resource.A1_1_8_7_YATA1);
		A1_1_9_IBIKI = loadSoundResourceFromSD(Resource.A1_1_9_IBIKI);
		A1_2_5_TIGAU1 = loadSoundResourceFromSD(Resource.A1_2_5_TIGAU1);
		A1_2_5_YATA4 = loadSoundResourceFromSD(Resource.A1_2_5_YATA4);
		A1_2_6_MUNYA1 = loadSoundResourceFromSD(Resource.A1_2_6_MUNYA1);
		A1_3_10_TIGAU2 = loadSoundResourceFromSD(Resource.A1_3_10_TIGAU2);
		A1_3_10_UHU = loadSoundResourceFromSD(Resource.A1_3_10_UHU);
		A1_3_MUNYA2 = loadSoundResourceFromSD(Resource.A1_3_MUNYA2);
		A1_4_MUNYA4 = loadSoundResourceFromSD(Resource.A1_4_MUNYA4);
		A1_04_SYU2 = loadSoundResourceFromSD(Resource.A1_04_SYU2);
		A1_4_URESHINA = loadSoundResourceFromSD(Resource.A1_4_URESHINA);
		A1_5_MUNYA3 = loadSoundResourceFromSD(Resource.A1_5_MUNYA3);
		A1_6_9_TIGAU1 = loadSoundResourceFromSD(Resource.A1_6_9_TIGAU1);
		A1_6_9_URESHI = loadSoundResourceFromSD(Resource.A1_6_9_URESHI);
		A1_06_CYU = loadSoundResourceFromSD(Resource.A1_06_CYU);
		A1_06_IIKODANE = loadSoundResourceFromSD(Resource.A1_06_IIKODANE);
		A1_06_PRESENT6 = loadSoundResourceFromSD(Resource.A1_06_PRESENT6);
		A1_06_SANTADAYO = loadSoundResourceFromSD(Resource.A1_06_SANTADAYO);
		A1_07_KACHI = loadSoundResourceFromSD(Resource.A1_07_KACHI);
		A1_7_NEIKI1 = loadSoundResourceFromSD(Resource.A1_7_NEIKI1);
		A1_08_KIRA10 = loadSoundResourceFromSD(Resource.A1_08_KIRA10);
		A1_09_MOKIN = loadSoundResourceFromSD(Resource.A1_09_MOKIN);
		A1_09_BOMI2 = loadSoundResourceFromSD(Resource.A1_09_BOMI2);
	}

	/**
	 * Stop all sound
	 */
	public void stopSound() {
		Sound[] sounds = new Sound[] { A1_8_NEIKI2, A1_1_8_7_TIGAU2,
				A1_1_8_7_YATA1, A1_1_9_IBIKI, A1_2_5_TIGAU1, A1_2_5_YATA4,
				A1_2_6_MUNYA1, A1_3_10_TIGAU2, A1_3_10_UHU, A1_3_MUNYA2,
				A1_4_MUNYA4, A1_04_SYU2, A1_4_URESHINA, A1_5_MUNYA3,
				A1_6_9_TIGAU1, A1_6_9_URESHI, A1_06_CYU, A1_06_IIKODANE,
				A1_06_PRESENT6, A1_06_SANTADAYO, A1_07_KACHI, A1_7_NEIKI1,
				A1_08_KIRA10, A1_09_MOKIN, A1_09_BOMI2 };

		for (Sound sound : sounds) {
			if (sound != null)
				sound.stop();
		}
	}

	/**
	 * 
	 * @param input
	 * @param conlections
	 * @return true if input in conlections
	 */
	private boolean acvCheckIn(int input, int... conlections) {
		for (int item : conlections) {
			if (input == item) {
				return true;
			}
		}

		return false;
	}

	final static class Resource {

		// 1
		public static final int A1_04_1_1_IPHONE_KODOMO_ID = 0;
		public static final int A1_04_1_2_IPHONE_KODOMO_ID = 1;
		public static final int A1_04_1_3_IPHONE_KODOMO_ID = 2;
		public static final int A1_04_2_1_IPHONE_KODOMO_ID = 3;
		public static final int A1_04_2_2_IPHONE_KODOMO_ID = 4;
		public static final int A1_04_2_3_IPHONE_KODOMO_ID = 5;

		// 2
		public static final int A1_04_3_1_IPHONE_KODOMO_ID = 0;
		public static final int A1_04_3_2_IPHONE_KODOMO_ID = 1;
		public static final int A1_04_3_3_IPHONE_KODOMO_ID = 2;
		public static final int A1_04_4_1_IPHONE_KODOMO_ID = 3;
		public static final int A1_04_4_2_IPHONE_KODOMO_ID = 4;
		public static final int A1_04_4_3_IPHONE_KODOMO_ID = 5;

		// 3
		public static final int A1_04_5_1_IPHONE_KODOMO_ID = 0;
		public static final int A1_04_5_2_IPHONE_KODOMO_ID = 1;
		public static final int A1_04_5_3_IPHONE_KODOMO_ID = 2;
		public static final int A1_04_6_1_IPHONE_KODOMO_ID = 3;
		public static final int A1_04_6_2_IPHONE_KODOMO_ID = 4;
		public static final int A1_04_6_3_IPHONE_KODOMO_ID = 5;

		// 4
		public static final int A1_04_7_1_IPHONE_KODOMO_ID = 0;
		public static final int A1_04_7_2_IPHONE_KODOMO_ID = 1;
		public static final int A1_04_7_3_IPHONE_KODOMO_ID = 2;
		public static final int A1_04_8_1_IPHONE_KODOMO_ID = 3;
		public static final int A1_04_8_2_IPHONE_KODOMO_ID = 4;
		public static final int A1_04_8_3_IPHONE_KODOMO_ID = 5;

		// 5
		public static final int A1_04_10_1_IPHONE_KODOMO_ID = 0;
		public static final int A1_04_10_2_IPHONE_KODOMO_ID = 1;
		public static final int A1_04_10_3_IPHONE_KODOMO_ID = 2;
		public static final int A1_04_9_1_IPHONE_KODOMO_ID = 3;
		public static final int A1_04_9_2_IPHONE_KODOMO_ID = 4;
		public static final int A1_04_9_3_IPHONE_KODOMO_ID = 5;

		// 6
		public static final int A1_01_IPHONE_SUZU_ID = 0;
		public static final int A1_02_IPHONE_GON_ID = 1;
		public static final int A1_03_IPHONE_QUESTION_ID = 2;
		public static final int A1_05_10_IPHONE_PRESENT_ID = 3;
		public static final int A1_05_11_IPHONE_PRESENT_ID = 4;
		public static final int A1_05_12_IPHONE_PRESENT_ID = 5;
		public static final int A1_05_13_IPHONE_PRESENT_ID = 6;
		public static final int A1_05_14_IPHONE_PRESENT_ID = 7;
		public static final int A1_05_15_IPHONE_PRESENT_ID = 8;
		public static final int A1_05_16_IPHONE_PRESENT_ID = 9;
		public static final int A1_05_17_IPHONE_PRESENT_ID = 10;
		public static final int A1_05_18_IPHONE_PRESENT_ID = 11;
		public static final int A1_05_19_IPHONE_PRESENT_ID = 12;
		public static final int A1_05_1_IPHONE_PRESENT_ID = 13;
		public static final int A1_05_20_IPHONE_PRESENT_ID = 14;
		public static final int A1_05_21_IPHONE_PRESENT_ID = 15;
		public static final int A1_05_22_IPHONE_PRESENT_ID = 16;
		public static final int A1_05_2_IPHONE_PRESENT_ID = 17;
		public static final int A1_05_3_IPHONE_PRESENT_ID = 18;
		public static final int A1_05_4_IPHONE_PRESENT_ID = 19;
		public static final int A1_05_5_IPHONE_PRESENT_ID = 20;
		public static final int A1_05_6_IPHONE_PRESENT_ID = 21;
		public static final int A1_05_7_IPHONE_PRESENT_ID = 22;
		public static final int A1_05_8_IPHONE_PRESENT_ID = 23;
		public static final int A1_05_9_IPHONE_PRESENT_ID = 24;
		public static final int A1_07_1_IPHONE_SHIKA_ID = 25;
		public static final int A1_07_2_IPHONE_SHIKA_ID = 26;
		public static final int A1_08_1_IPHONE_CITY_ID = 27;
		public static final int A1_08_2_IPHONE_CITY_ID = 28;
		public static final int A1_09_1_1_IPHONE_KAERU_ID = 29;
		public static final int A1_09_1_2_IPHONE_KAERU_ID = 30;
		public static final int A1_09_1_3_IPHONE_KAERU_ID = 31;
		public static final int A1_09_1_4_IPHONE_KAERU_ID = 32;
		public static final int A1_09_2_1_IPHONE_NEKO_ID = 33;
		public static final int A1_09_2_2_IPHONE_NEKO_ID = 34;
		public static final int A1_09_2_3_IPHONE_NEKO_ID = 35;
		public static final int A1_09_2_4_IPHONE_NEKO_ID = 36;
		public static final int A1_09_3_1_IPHONE_DOROBOU_ID = 37;
		public static final int A1_09_3_2_IPHONE_DOROBOU_ID = 38;
		public static final int A1_09_3_3_IPHONE_DOROBOU_ID = 39;
		public static final int A1_09_3_4_IPHONE_DOROBOU_ID = 40;
		public static final int A1_09_4_1_IPHONE_TORI_ID = 41;
		public static final int A1_09_4_2_IPHONE_TORI_ID = 42;
		public static final int A1_09_4_3_IPHONE_TORI_ID = 43;
		public static final int A1_09_4_4_IPHONE_TORI_ID = 44;
		public static final int A1_10_1_IPHONE_FUKIDASHI_ID = 45;
		public static final int A1_10_2_IPHONE_FUKIDASHI_ID = 46;
		public static final int A1_10_3_IPHONE_FUKIDASHI_ID = 47;
		public static final int A1_10_4_IPHONE_FUKIDASHI_ID = 48;
		public static final int RECTANGLE_MAN_ID = 49;

		// 7
		public static final int A1_06_1_IPHONE_SANTA_ID = 0;
		public static final int A1_06_2_1_IPHONE_SANTA_ID = 1;
		public static final int A1_06_2_2_IPHONE_SANTA_ID = 2;
		public static final int A1_06_2_3_IPHONE_SANTA_ID = 3;
		public static final int A1_06_3_1_IPHONE_SANTA_ID = 4;
		public static final int A1_06_3_2_IPHONE_SANTA_ID = 5;

		// 8
		public static final int A1_11_1_IPHONE_SNOW_ID = 0;
		public static final int A1_11_2_IPHONE_SNOW_ID = 1;
		public static final int A1_12_IPHONE_HAIKEI_ID = 2;

		// sound
		public static final String A1_8_NEIKI2 = "a1_8_neiki2.ogg";
		public static final String A1_1_8_7_TIGAU2 = "a1_1_8_7_tigau2.ogg";
		public static final String A1_1_8_7_YATA1 = "a1_1_8_7_yata1.ogg";
		public static final String A1_1_9_IBIKI = "a1_1_9_ibiki.ogg";
		public static final String A1_01_SUZU = "a1_01_suzu.ogg";
		public static final String A1_2_5_TIGAU1 = "a1_2_5_tigau1.ogg";
		public static final String A1_2_5_YATA4 = "a1_2_5_yata4.ogg";
		public static final String A1_2_6_MUNYA1 = "a1_2_6_munya1.ogg";
		public static final String A1_02_GON = "a1_02_gon.ogg";
		public static final String A1_3_10_TIGAU2 = "a1_3_10_tigau2.ogg";
		public static final String A1_3_10_UHU = "a1_3_10_uhu.ogg";
		public static final String A1_3_MUNYA2 = "a1_3_munya2.ogg";
		public static final String A1_4_MUNYA4 = "a1_4_munya4.ogg";
		public static final String A1_04_SYU2 = "a1_04_syu2.ogg";
		public static final String A1_4_URESHINA = "a1_4_ureshina.ogg";
		public static final String A1_5_MUNYA3 = "a1_5_munya3.ogg";
		public static final String A1_6_9_TIGAU1 = "a1_6_9_tigau1.ogg";
		public static final String A1_6_9_URESHI = "a1_6_9_ureshi.ogg";
		public static final String A1_06_CYU = "a1_06_cyu.ogg";
		public static final String A1_06_IIKODANE = "a1_06_iikodane.ogg";
		public static final String A1_06_PRESENT6 = "a1_06_present6.ogg";
		public static final String A1_06_SANTADAYO = "a1_06_santadayo.ogg";
		public static final String A1_07_KACHI = "a1_07_kachi.ogg";
		public static final String A1_7_NEIKI1 = "a1_7_neiki1.ogg";
		public static final String A1_08_KIRA10 = "a1_08_kira10.ogg";
		public static final String A1_09_MOKIN = "A1_09_mokin.ogg";
		public static final String A1_09_BOMI2 = "A1_09_bomi2.ogg";

		// Sound Gimmic
		public static final String SOUND_GIMMIC[] = { A1_01_SUZU, A1_02_GON,
				A1_09_MOKIN };

		// Image Gimmic
		public static final int IMAGE_GIMMIC[] = { A1_01_IPHONE_SUZU_ID,
				A1_02_IPHONE_GON_ID, A1_03_IPHONE_QUESTION_ID };

		public static final int PACK_KAERU[] = { A1_09_1_1_IPHONE_KAERU_ID,
				A1_09_1_2_IPHONE_KAERU_ID, A1_09_1_3_IPHONE_KAERU_ID,
				A1_09_1_4_IPHONE_KAERU_ID };

		public static final int PACK_NEKO[] = { A1_09_2_1_IPHONE_NEKO_ID,
				A1_09_2_2_IPHONE_NEKO_ID, A1_09_2_3_IPHONE_NEKO_ID,
				A1_09_2_4_IPHONE_NEKO_ID };

		public static final int PACK_DOROBOU[] = { A1_09_3_1_IPHONE_DOROBOU_ID,
				A1_09_3_2_IPHONE_DOROBOU_ID, A1_09_3_3_IPHONE_DOROBOU_ID,
				A1_09_3_4_IPHONE_DOROBOU_ID };

		public static final int PACK_TORI[] = { A1_09_4_1_IPHONE_TORI_ID,
				A1_09_4_2_IPHONE_TORI_ID, A1_09_4_3_IPHONE_TORI_ID,
				A1_09_4_4_IPHONE_TORI_ID };

		public static final int PACK_FUKIDASHI[] = {
				A1_10_1_IPHONE_FUKIDASHI_ID, A1_10_2_IPHONE_FUKIDASHI_ID,
				A1_10_4_IPHONE_FUKIDASHI_ID, A1_10_3_IPHONE_FUKIDASHI_ID };

		public static final int PACK_PRESENT[] = { A1_05_1_IPHONE_PRESENT_ID,
				A1_05_2_IPHONE_PRESENT_ID, A1_05_3_IPHONE_PRESENT_ID,
				A1_05_4_IPHONE_PRESENT_ID, A1_05_5_IPHONE_PRESENT_ID,
				A1_05_6_IPHONE_PRESENT_ID, A1_05_7_IPHONE_PRESENT_ID,
				A1_05_8_IPHONE_PRESENT_ID, A1_05_9_IPHONE_PRESENT_ID,
				A1_05_10_IPHONE_PRESENT_ID, A1_05_11_IPHONE_PRESENT_ID,
				A1_05_12_IPHONE_PRESENT_ID, A1_05_13_IPHONE_PRESENT_ID,
				A1_05_14_IPHONE_PRESENT_ID, A1_05_15_IPHONE_PRESENT_ID,
				A1_05_16_IPHONE_PRESENT_ID, A1_05_17_IPHONE_PRESENT_ID,
				A1_05_18_IPHONE_PRESENT_ID, A1_05_19_IPHONE_PRESENT_ID,
				A1_05_20_IPHONE_PRESENT_ID, A1_05_21_IPHONE_PRESENT_ID,
				A1_05_22_IPHONE_PRESENT_ID

		};

		public static final int PACK_KODOMO1[] = { A1_04_1_1_IPHONE_KODOMO_ID,
				A1_04_1_2_IPHONE_KODOMO_ID, A1_04_1_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO2[] = { A1_04_2_1_IPHONE_KODOMO_ID,
				A1_04_2_2_IPHONE_KODOMO_ID, A1_04_2_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO3[] = { A1_04_3_1_IPHONE_KODOMO_ID,
				A1_04_3_2_IPHONE_KODOMO_ID, A1_04_3_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO4[] = { A1_04_4_1_IPHONE_KODOMO_ID,
				A1_04_4_2_IPHONE_KODOMO_ID, A1_04_4_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO5[] = { A1_04_5_1_IPHONE_KODOMO_ID,
				A1_04_5_2_IPHONE_KODOMO_ID, A1_04_5_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO6[] = { A1_04_6_1_IPHONE_KODOMO_ID,
				A1_04_6_2_IPHONE_KODOMO_ID, A1_04_6_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO7[] = { A1_04_7_1_IPHONE_KODOMO_ID,
				A1_04_7_2_IPHONE_KODOMO_ID, A1_04_7_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO8[] = { A1_04_8_1_IPHONE_KODOMO_ID,
				A1_04_8_2_IPHONE_KODOMO_ID, A1_04_8_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO9[] = { A1_04_9_1_IPHONE_KODOMO_ID,
				A1_04_9_2_IPHONE_KODOMO_ID, A1_04_9_3_IPHONE_KODOMO_ID };
		public static final int PACK_KODOMO10[] = {
				A1_04_10_1_IPHONE_KODOMO_ID, A1_04_10_2_IPHONE_KODOMO_ID,
				A1_04_10_3_IPHONE_KODOMO_ID };
	}

	final static class UTils {
		/**
		 * 
		 * @param recSanta
		 * @param pointF
		 */
		public static void clearEntityModifiersVisible(Sprite recSanta,
				PointF pointF) {
			if (recSanta != null) {
				recSanta.clearEntityModifiers();
				recSanta.setVisible(false);

				if (pointF != null) {
					recSanta.setPosition(pointF.x, pointF.y);
				}
			}
		}

		public static void setPositionVisible(PresentSprite presentSprite,
				float x, float y, boolean b) {
			presentSprite.setPosition(x, y);
			presentSprite.setVisible(b);
		}

		public static boolean haveScreenLock(Vol3Santa vol3Santa) {
			KeyguardManager kgMgr = (KeyguardManager) vol3Santa.getActivity()
					.getSystemService(Context.KEYGUARD_SERVICE);
			boolean haveScreenLock = kgMgr.inKeyguardRestrictedInputMode();
			return haveScreenLock;
		}

		public static void setVisible(DreamSprite[] sPresentDream,
				ArrayList<Integer> arlPresentBoy1) {
			for (int i = 0; i < arlPresentBoy1.size(); i++) {
				sPresentDream[arlPresentBoy1.get(i)].setVisible(false);
			}
		}

		/**
		 * 
		 * @param aniShika
		 * @param index
		 */
		public static void setCurrentTileIndex(AnimatedSprite aniShika,
				int index) {
			if (aniShika != null) {
				aniShika.setCurrentTileIndex(index);
			}
		}
	}

	private AnimatedSprite createAnimatedSprite(float dx, float dy,
			TiledTextureRegion ttrSnow2) {
		return new AnimatedSprite(dx, dy, ttrSnow2,
				this.getVertexBufferObjectManager());
	}

	private Sprite createSpire(float pX, float pY, TextureRegion trBackground2) {
		return new Sprite(pX, pY, trBackground2,
				this.getVertexBufferObjectManager());
	}

	@Override
	protected String getBasePathFolder() {
		return "santa";
	}
}