
package jp.co.xing.utaehon03.songs;



import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YukainamakibaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Yukainamakiba extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private static final String TAG = "Log Yukainamakiba";
		
	private TexturePack ttpYukai1, ttpYukai2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary ttrYukaibaLibrary1, ttrYukaibaLibrary2;
	private ITextureRegion ttrBackground, ttrFlower, ttrFront, ttrBack, ttrWara,
			ttrLake1, ttrUmakoy, ttrPinWheel, ttrHouse, ttrUma1, ttrUma2,
			ttrUma3, ttrPig1, ttrPig2, ttrPig3, ttrCow1, ttrCow2, ttrCow3;
	private ITextureRegion[] ttrArrDoremiSheep = new ITextureRegion[8];
	private ITextureRegion[] tttrArrChitken = new ITextureRegion[5];
	private Sprite sFlower, sFront, sBack, sWara, sLake1, sUmakoy, sPinWheel,
			sHouse, sUma1, sUma2, sUma3, sPig1, sPig2, sPig3, sCow1, sCow2,
			sCow3;
	private Sprite[] sArrDoremiSheep = new Sprite[8];
	private Sprite[] sArrChitken = new Sprite[5];
	private TiledTextureRegion tttrLake2, tttrLake3, tttrBoy, tttrWindown,
			tttrDogLeft, tttrDogRight;
	private TiledTextureRegion[] tttrArrSheep = new TiledTextureRegion[10];
	private AnimatedSprite aniDuck1, aniDuck2, aniBoy, aniWindown, aniDogLeft,
			aniDogRight,aniSheepOne,aniSheepTwo;
	boolean istouchBoy, istouchDuck, istouchPin, istouchWindown, istouchUma,
			istouchPig, istouchCow, istouchDogLeft, istouchDogRight;
	private boolean isStop=false;
	private boolean isClickSheep;
	private boolean istouchSheep=true;
	private boolean istouchWara= true;
	private SequenceEntityModifier duck1Modifier, duck2Modifier, dogModifier,
			pinModifier, umaModifier, pigModifier, cowModifier, chikenModifier,
			sheepOneModifier, sheepTwoModifier, sheepThreeModifier;
	private int countSheep=0;
	private int tmpSheep=1;
	private int zIndex;
	private float arrpointDrmSheep[][] = new float[][] {
			{ 628, 714, 665, 723, 778, 811, 766, 730 },
			{ 317, 271, 399, 355, 297, 360, 412, 468 } };
	private float arrPointChicken[][] = new float[][] {
			{ 62, 94, 116, 150, 194 }, { 534, 530, 516, 514, 516 } };
	private static final int Xlist[] = new int[] { 50, 40, -50, 50, -50, 40,
			-60};
	long pDuration[] = new long[] { 400, 400 };
	int pFrame[] = new int[] { 1, 2 };
	private RanDomNoRepeat sChitkenRandom;
	private RanDomNoRepeat pChitkenRandom;
	/**
	 * Sounds
	*/
	private Sound A12_04_INUC, A12_05_EIGHT, A12_05_FIVE, A12_05_FOUR,
			A12_05_NINE, A12_05_ONE, A12_05_PAKA, A12_05_SEVEN, A12_05_SIX,
			A12_05_TEN, A12_05_THREE, A12_05_TWO, A12_06_INUF, A12_07_PYON2,
			A12_08_CONBYE, A12_09_ROBA, A12_10_BUTA, A12_11_USHI, A12_12_1_DO2,
			A12_12_2_RE, A12_12_3_MI, A12_12_4_FA, A12_12_5_SO, A12_12_6_RA,
			A12_12_7_SHI, A12_12_8_DO, A12_13_GIGI, A12_14_PIYO,
			A12_15_CYAPACHORO;
	private Sound[] soundCountSheep;

	@Override
	protected void loadKaraokeScene() {
		
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, ttrBackground, this.getVertexBufferObjectManager())));
//		this.mScene.setOnAreaTouchTraversalFrontToBack();
		zIndex = mScene.getZIndex();
		// display lake
		sLake1 = new Sprite(-148, 198, ttrLake1,this.getVertexBufferObjectManager());
		mScene.attachChild(sLake1);
		// display duck 2
		aniDuck2 = new AnimatedSprite(314, 198, tttrLake3,this.getVertexBufferObjectManager());
		mScene.attachChild(aniDuck2);
		// display duck 1
		aniDuck1 = new AnimatedSprite(192, 210, tttrLake2,this.getVertexBufferObjectManager());
		mScene.attachChild(aniDuck1);
		// display flower
		sFlower = new Sprite(1, 283, ttrFlower,this.getVertexBufferObjectManager());
		mScene.attachChild(sFlower);
		// display boy
		aniBoy = new AnimatedSprite(53, 75, tttrBoy,this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoy);
		// display house
		sHouse = new Sprite(479, 30, ttrHouse,this.getVertexBufferObjectManager());
		mScene.attachChild(sHouse);
		// display windown
		aniWindown = new AnimatedSprite(478, 190, tttrWindown,this.getVertexBufferObjectManager());
		mScene.attachChild(aniWindown);
		// display umakoya
		sUmakoy = new Sprite(615, 119, ttrUmakoy,this.getVertexBufferObjectManager());
		mScene.attachChild(sUmakoy);
		// display uma
		sUma1 = new Sprite(618, 139, ttrUma1,this.getVertexBufferObjectManager());
		mScene.attachChild(sUma1);
		sUma2 = new Sprite(618, 139, ttrUma2,this.getVertexBufferObjectManager());
		sUma2.setVisible(false);
		mScene.attachChild(sUma2);
		sUma3 = new Sprite(618, 139, ttrUma3,this.getVertexBufferObjectManager());
		sUma3.setVisible(false);
		mScene.attachChild(sUma3);
		// display pig
		sPig1 = new Sprite(686, 145, ttrPig1,this.getVertexBufferObjectManager());
		mScene.attachChild(sPig1);
		sPig2 = new Sprite(686, 145, ttrPig2,this.getVertexBufferObjectManager());
		sPig2.setVisible(false);
		mScene.attachChild(sPig2);
		sPig3 = new Sprite(686, 145, ttrPig3,this.getVertexBufferObjectManager());
		sPig3.setVisible(false);
		mScene.attachChild(sPig3);
		// display cow
		sCow1 = new Sprite(784, 173, ttrCow1,this.getVertexBufferObjectManager());
		mScene.attachChild(sCow1);
		sCow2 = new Sprite(784, 173, ttrCow2,this.getVertexBufferObjectManager());
		sCow2.setVisible(false);
		mScene.attachChild(sCow2);
		sCow3 = new Sprite(784, 173, ttrCow3,this.getVertexBufferObjectManager());
		sCow3.setVisible(false);
		mScene.attachChild(sCow3);
		// display PinWheel
		sPinWheel = new Sprite(434, -54, ttrPinWheel,this.getVertexBufferObjectManager());
		mScene.attachChild(sPinWheel);

		// display back
		sBack = new Sprite(318, 289, ttrBack,this.getVertexBufferObjectManager());
		mScene.attachChild(sBack);
		
		// display doremi sheep
		for (int k = 0; k < 8; k++) {
			sArrDoremiSheep[k] = new Sprite(arrpointDrmSheep[0][k],
					arrpointDrmSheep[1][k], ttrArrDoremiSheep[k],this.getVertexBufferObjectManager());
			sArrDoremiSheep[k].setZIndex(zIndex+1);
			mScene.attachChild(sArrDoremiSheep[k]);
		}
		// display front
		sFront = new Sprite(316, 384, ttrFront,this.getVertexBufferObjectManager());
		mScene.attachChild(sFront);
		// display chicken
		aniDogRight = new AnimatedSprite(737, 539, tttrDogRight,this.getVertexBufferObjectManager());
		aniDogRight.setZIndex(zIndex+1);
		mScene.attachChild(aniDogRight);
		for (int k = 0; k < 5; k++) {
			sArrChitken[k] = new Sprite(arrPointChicken[0][k],
					arrPointChicken[1][k], tttrArrChitken[k],this.getVertexBufferObjectManager());
			sArrChitken[k].setZIndex(zIndex+1);
			mScene.attachChild(sArrChitken[k]);
		}
		// display wara
		sWara = new Sprite(-39, 376, ttrWara,this.getVertexBufferObjectManager());
		sWara.setZIndex(zIndex+1);
		mScene.attachChild(sWara);

		aniDogLeft = new AnimatedSprite(102, 539, tttrDogLeft,this.getVertexBufferObjectManager());
		aniDogLeft.setZIndex(zIndex+1);
		mScene.attachChild(aniDogLeft);
		addGimmicsButton(mScene, Vol3YukainamakibaResource.SOUND_GIMMIC,
				Vol3YukainamakibaResource.IMAGE_GIMMIC, ttrYukaibaLibrary1);
		mScene.setOnSceneTouchListener(this);
	}
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("yukainamakiba/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("yukainamakiba/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager,"yukainamakiba/gfx/");
		super.onCreateResources();

	}
	@Override
	protected void loadKaraokeResources() {
		
		ttpYukai1 = mTexturePackLoaderFromSource.load("yukainamakiba1.xml");
		ttpYukai2 = mTexturePackLoaderFromSource.load("yukainamakiba2.xml");
		ttpYukai1.loadTexture();
		ttpYukai2.loadTexture();
			
		ttrYukaibaLibrary1 = ttpYukai1.getTexturePackTextureRegionLibrary();
		ttrYukaibaLibrary2 = ttpYukai2.getTexturePackTextureRegionLibrary();
		
		ttrBackground = ttrYukaibaLibrary2.get(Vol3YukainamakibaResource.A12_18_1_IPHONE_HAIKEI_ID);
		
		ttrFlower = ttrYukaibaLibrary2.get(Vol3YukainamakibaResource.A12_18_2_IPHONE_HAIKEI_ID);
		ttrFront = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_17_1_IPHONE_FRONT_ID);
		
		ttrBack = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_17_2_IPHONE_BACK_ID);
		
		ttrWara = ttrYukaibaLibrary2.get(Vol3YukainamakibaResource.A12_14_1_IPHONE_WARA_ID);
		
		ttrLake1 = ttrYukaibaLibrary2.get(Vol3YukainamakibaResource.A12_15_1_IPHONE_LAKE_ID);
		
		tttrLake2 = getTiledTextureFromPacker(ttpYukai1,
				Vol3YukainamakibaResource.A12_15_2_IPHONE_LAKE_ID,
				Vol3YukainamakibaResource.A12_15_3_IPHONE_LAKE_ID);
		
		tttrLake3 = getTiledTextureFromPacker(ttpYukai1,
				Vol3YukainamakibaResource.A12_15_3_IPHONE_LAKE_ID,
				Vol3YukainamakibaResource.A12_15_2_IPHONE_LAKE_ID
				);
		
		ttrUmakoy = ttrYukaibaLibrary2.get(Vol3YukainamakibaResource.A12_16_IPHONE_UMAKOYA_ID);
		ttrHouse = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_08_4_IPHONE_HOUSE_ID);
		ttrPinWheel = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_13_IPHONE_PINWHEEL_ID);
		// doremisheep
		for (int k = 0; k < 8; k++) {
			ttrArrDoremiSheep[k]= ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_12_IPHONE_DO[k]);
		}
		// boy
		tttrBoy = getTiledTextureFromPacker(ttpYukai1,
				Vol3YukainamakibaResource.A12_07_1_IPHONE_BOY_ID,
				Vol3YukainamakibaResource.A12_07_2_IPHONE_BOY_ID);
		// windown
		tttrWindown = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_HOUSE);
		
		// uma
		ttrUma1 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_09_1_IPHONE_UMA_ID);
		ttrUma2 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_09_2_IPHONE_UMA_ID);
		ttrUma3 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_09_3_IPHONE_UMA_ID);
		// pig
		ttrPig1 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_10_1_IPHONE_PIG_ID);
		ttrPig2 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_10_2_IPHONE_PIG_ID);
		ttrPig3 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_10_3_IPHONE_PIG_ID);
		// cow
		ttrCow1 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_11_1_IPHONE_COW_ID);
		ttrCow2 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_11_2_IPHONE_COW_ID);
		ttrCow3 = ttrYukaibaLibrary1.get(Vol3YukainamakibaResource.A12_11_3_IPHONE_COW_ID);
		// dog left
		tttrDogLeft = getTiledTextureFromPacker(ttpYukai1,
				Vol3YukainamakibaResource.A12_04_1_IPHONE_LEFTINU_ID,
				Vol3YukainamakibaResource.A12_04_2_IPHONE_LEFTINU_ID);
		// dog right
		tttrDogRight = getTiledTextureFromPacker(ttpYukai1,
				Vol3YukainamakibaResource.A12_06_1_IPHONE_RIGHTINU_ID,
				Vol3YukainamakibaResource.A12_06_2_IPHONE_RIGHTINU_ID);
		// chicken
		for (int k = 0; k < 5; k++) {
			tttrArrChitken[k] = getTiledTextureFromPacker(ttpYukai1,
					Vol3YukainamakibaResource.A12_14_IPHONE_CHICKEN[k]);
		}
		// sheep run
		tttrArrSheep[0] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_1 );
		tttrArrSheep[1] = getTiledTextureFromPacker(ttpYukai1,Vol3YukainamakibaResource.PACK_SHEEP_2 );
		tttrArrSheep[2] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_3 );
		tttrArrSheep[3] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_4 );
		tttrArrSheep[4] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_5 );
		tttrArrSheep[5] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_6 );
		tttrArrSheep[6] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_7 );
		tttrArrSheep[7] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_8 );
		tttrArrSheep[8] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_9 );
		tttrArrSheep[9] = getTiledTextureFromPacker(ttpYukai1, Vol3YukainamakibaResource.PACK_SHEEP_10 );
				
	}

	@Override
	protected void loadKaraokeSound() {
		A12_04_INUC = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_04_INUC);
		A12_05_EIGHT = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_EIGHT);
		A12_05_FIVE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_FIVE);
		A12_05_FOUR = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_FOUR);
		A12_05_NINE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_NINE);
		A12_05_ONE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_ONE);
		A12_05_PAKA = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_PAKA);
		A12_05_SEVEN = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_SEVEN);
		A12_05_SIX = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_SIX);
		A12_05_TEN = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_TEN);
		A12_05_THREE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_THREE);
		A12_05_TWO = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_05_TWO);
		A12_06_INUF = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_06_INUF);
		A12_07_PYON2 = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_07_PYON2);
		A12_08_CONBYE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_08_CONBYE);
		A12_09_ROBA = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_09_ROBA);
		A12_10_BUTA = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_10_BUTA);
		A12_11_USHI = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_11_USHI);
		A12_12_1_DO2 = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_1_DO2);
		A12_12_2_RE = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_2_RE);
		A12_12_3_MI = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_3_MI);
		A12_12_4_FA = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_4_FA);
		A12_12_5_SO = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_5_SO);
		A12_12_6_RA = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_6_RA);
		A12_12_7_SHI = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_7_SHI);
		A12_12_8_DO = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_12_8_DO);
		A12_13_GIGI = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_13_GIGI);
		A12_14_PIYO = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_14_PIYO);
		A12_15_CYAPACHORO = loadSoundResourceFromSD(Vol3YukainamakibaResource.A12_15_CYAPACHORO);
		soundCountSheep = new Sound[] { A12_05_ONE, A12_05_TWO, A12_05_THREE,
				A12_05_FOUR, A12_05_FIVE, A12_05_SIX, A12_05_SEVEN,
				A12_05_EIGHT, A12_05_NINE, A12_05_TEN };
	}
	@Override
	public synchronized void onResumeGame() {
		Log.e("Vol3Yunamakiba: ", "onResumeGame:" );
		loadDefault();
		runSheepNext();
		super.onResumeGame();
	}
	@Override
	public void onPauseGame() {
		Log.e(TAG, "onPauseGame");
		clearData();
		super.onPauseGame();		
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.e("Vol3Yunamakiba: ", "loadKaraokeComplete:" );
		super.loadKaraokeComplete();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(aniBoy, 12, 38, 170, 246, pX, pY)) {
				if (istouchBoy) {
					istouchBoy = false;
					touchAnimate(1);
				}
			} else if (checkContains(sLake1, 150, 0, 620, 80, pX, pY)) {
				// touch lake
				touchModifier(1);
			} else if (checkContains(sPinWheel, 5, 0, 226, 226, pX, pY)) {
				// touch pin wheel
				touchModifier(2);

			} else if (checkContains(aniWindown, 35, 8, 116, 76, pX, pY)) {
				if (istouchWindown) {
					istouchWindown = false;
					isTouchGimmic[0]=false;
					touchAnimate(2);
				}
			} else if (checkContains(sUma1, 10, 62, 58, 130, pX, pY)) {
				// touch uma
				if (istouchUma) {
					istouchUma = false;
					touchModifier(3);
				}
			} else if (checkContains(sPig1, 44, 85, 104, 120, pX, pY)) {
				// touch pig
				if (istouchPig) {
					istouchPig = false;
					touchModifier(4);
				}
			} else if (checkContains(sCow1, 25, 57, 87, 90, pX, pY)) {
				if (istouchCow) {
					istouchCow = false;
					touchModifier(5);
				}
			} else if (checkContains(aniDogLeft, 15, 0, 115, 126, pX, pY)) {
				// touch dog left
				if (istouchDogLeft) {
					istouchDogLeft = false;
					touchModifier(6);
				}
			} else if (checkContains(aniDogRight, 10, 0, 120, 126, pX, pY)) {
				if (istouchDogRight) {
					istouchDogRight = false;
					touchModifier(7);
				}
			} else if (checkContains(sWara, 0, 136, 307, 344, pX, pY)) {
				// touch wara
				if (istouchWara) {
					istouchWara = false;
					touchWara();
				}
			} else if (checkContains(sArrDoremiSheep[0], 0, 0, 80, 70, pX, pY)) {
				A12_12_8_DO.play();

			} else if (checkContains(sArrDoremiSheep[1], 0, 0, 58, 86, pX, pY)) {
				A12_12_2_RE.play();
			} else if (checkContains(sArrDoremiSheep[2], 1, 5, 84, 72, pX, pY)) {
				A12_12_3_MI.play();
			} else if (checkContains(sArrDoremiSheep[3], 12, 15, 70, 70, pX, pY)) {
				A12_12_4_FA.play();
			} else if (checkContains(sArrDoremiSheep[4], 12, 12, 104, 86, pX,pY)) {
				A12_12_5_SO.play();
			} else if (checkContains(sArrDoremiSheep[5], 25, 15, 125, 70, pX, pY)) {
				A12_12_1_DO2.play();
			} else if (checkContains(sArrDoremiSheep[6], 28, 5, 125, 70, pX, pY)) {
				A12_12_7_SHI.play();
			} else if (checkContains(sArrDoremiSheep[7], 16, 12, 128, 52, pX, pY)) {
				A12_12_6_RA.play();

			} else if (checkContains(aniSheepOne, 15, 30, 152, 169, pX, pY)) {
				Log.d(TAG, "touch sheep");
				touchModifier(8);
			} else if (checkContains(aniSheepTwo, 15, 30, 152, 169, pX, pY)) {
					Log.d(TAG, "touch sheep");
					touchModifier(8);
				}
			
		}
		return false;
	}

	private void runSheepNext() {
		Log.d(TAG, "tao new sheep de tap : ");
		if (tmpSheep == 1) {
			aniSheepOne = new AnimatedSprite(-156, 335,
					tttrArrSheep[countSheep], this.getVertexBufferObjectManager());
			
			aniSheepOne.setZIndex(zIndex);
			this.mScene.attachChild(aniSheepOne);
			setGimmicBringToFront();
			
			
			aniSheepOne.animate(pDuration, pFrame, -1);
			aniSheepOne
					.registerEntityModifier(sheepOneModifier = new SequenceEntityModifier(
							 new MoveXModifier(2.0f,
									-156, 120)));
		} else if (tmpSheep == 2) {
			aniSheepTwo = new AnimatedSprite(-156, 335,
					tttrArrSheep[countSheep],this.getVertexBufferObjectManager());
			aniSheepTwo.setZIndex(zIndex);
			this.mScene.attachChild(aniSheepTwo);
			setGimmicBringToFront();
			
			aniSheepTwo.animate(pDuration, pFrame, -1);
			aniSheepTwo
					.registerEntityModifier(sheepOneModifier = new SequenceEntityModifier(
							 new MoveXModifier(2.0f,
									-156, 120)));
		}
		isStop = false;
		sheepOneModifier.addModifierListener(pModifierSheep);
	}

	private void touchModifier(int id) {
		switch (id) {
		case 1:
			duckModifier();
			break;
		case 2:
			pinWheelModifier();
			break;
		case 3:
			umaModifier();
			break;
		case 4:
			pigModifier();
			break;
		case 5:
			cowModifier();
			break;
		case 6:
			A12_04_INUC.play();
			dogModifier(id, aniDogLeft);
			break;
		case 7:
			A12_06_INUF.play();
			dogModifier(id, aniDogRight);
			break;
		case 8:
			if (isStop) {
				touchSheep();
			} else {
				isClickSheep = true;
			}
			break;
		}
	}

	private void touchSheep() {
		if (istouchSheep) {
			istouchSheep = false;
			if (tmpSheep == 1) {
				tmpSheep = 2;
				aniSheepOne.stopAnimation();
				aniSheepOne.setCurrentTileIndex(3);
				aniSheepOne
						.registerEntityModifier(sheepTwoModifier = new SequenceEntityModifier(
								new MoveModifier(0.3f, 120, 190, 335, 240),
								new MoveXModifier(0.15f, 190, 305),
								new MoveModifier(0.3f, 305, 450, 240, 335)));
				soundCountSheep[countSheep].play();
			} else {
				tmpSheep = 1;
				aniSheepTwo.stopAnimation();
				aniSheepTwo.setCurrentTileIndex(3);
				aniSheepTwo
						.registerEntityModifier(sheepTwoModifier = new SequenceEntityModifier(
								new MoveModifier(0.3f, 120, 190, 335, 240),
								new MoveXModifier(0.15f, 190, 305),
								new MoveModifier(0.3f, 305, 450, 240, 335)));
				soundCountSheep[countSheep].play();
			}
			sheepTwoModifier.addModifierListener(pModifierSheep);
			countSheep++;
			
			if (countSheep == 10) {
				countSheep = 0;
			}
			Log.d(TAG, "gia tri cua tmpSheep: " + tmpSheep);
			runSheepNext();
		}
	}

	IModifierListener<IEntity> pModifierSheep = new IModifierListener<IEntity>() {
		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
			if (pModifier.equals(sheepOneModifier)) {
				if (tmpSheep == 1) {
					aniSheepOne.stopAnimation();
					aniSheepOne.setCurrentTileIndex(0);
				} else if (tmpSheep==2)  {
					aniSheepTwo.stopAnimation();
					aniSheepTwo.setCurrentTileIndex(0);
				}
				isStop = true;
			} else if (pModifier.equals(sheepTwoModifier)) {
				if (tmpSheep == 2) {
					aniSheepOne.animate(pDuration, pFrame, -1);
					A12_05_PAKA.play();
					aniSheepOne
							.registerEntityModifier(sheepThreeModifier = new SequenceEntityModifier(
									new MoveModifier(1.5f, 450, 960, 335, 220),
									new DelayModifier(0.4f)
									));
				} else {
					aniSheepTwo.animate(pDuration, pFrame, -1);
					A12_05_PAKA.play();
					aniSheepTwo
							.registerEntityModifier(sheepThreeModifier = new SequenceEntityModifier(
									new MoveModifier(1.5f, 450, 960, 335, 220),
									new DelayModifier(0.4f)
									));
				}
				// call back touch
				sheepThreeModifier.addModifierListener(pModifierSheep);
				Log.d(TAG, "gia tri cua isClickSheep: "+ isClickSheep);
				touchBackSheep();
			} else if (pModifier.equals(sheepThreeModifier)) {
				if (tmpSheep == 2) {
					if (aniSheepOne != null) {
						aniSheepOne.stopAnimation();
						aniSheepOne.setVisible(false);
					}
					
				} else {
					if (aniSheepTwo!=null) {
					aniSheepTwo.stopAnimation();
					aniSheepTwo.setVisible(false);
					}
				}
				// call back touch
				istouchSheep = true;
				Log.d(TAG, "gia tri cua isClickSheep: "+ isClickSheep);
				touchBackSheep();
			}
		}

		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			
		}
	};

	private void touchBackSheep() {
		if (isClickSheep) {
			touchSheep();
			isClickSheep = false;
		}
	}

	private void touchAnimate(int id) {
		if (id == 1) {
			A12_07_PYON2.play();
			long pDuration[] = new long[] { 400, 200 };
			int pFrame[] = new int[] { 1, 0 };
			aniBoy.animate(pDuration, pFrame, 0, this);
		}
		if (id == 2) {
			this.A12_08_CONBYE.play();
			long pDuration[] = new long[] { 300, 300 };
			int pFrame[] = new int[] { 1, 2 };
			this.aniWindown.animate(pDuration, pFrame, 3, this);
		}
	}

	private void dogModifier(final int id, final AnimatedSprite aniDog) {
		aniDog.registerEntityModifier(dogModifier = new SequenceEntityModifier(
				new MoveYModifier(0.6f, aniDog.getY(), aniDog.getY() - 20, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						long pDuration[] = new long[] { 400, 100 };
						int pFrame[] = new int[] { 1, 0 };
						aniDog.animate(pDuration, pFrame, 0);
					}
				}),
				new MoveYModifier(0.8f, aniDog.getY() - 20, aniDog.getY() + 10),
				new MoveYModifier(0.2f, aniDog.getY() + 10, aniDog.getY())));
		dogModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				if (id == 6) {
					istouchDogLeft = true;
				}
				if (id == 7) {
					istouchDogRight = true;
				}
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void touchWara() {
		sChitkenRandom.setLenghNoRepeat(sArrChitken.length);
		pChitkenRandom.setLenghNoRepeat(Xlist.length);
		final int i=sChitkenRandom.getNextIntNoRepeat(true);
		final int x=pChitkenRandom.getNextIntNoRepeat(true);
		A12_14_PIYO.play();
		Log.d(TAG, "gia tri cua i " + i);
		Log.d(TAG, "gia tri cua x " + x);
		mEngine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				istouchWara = true;
			}
		}));
		sArrChitken[i]
				.registerEntityModifier(chikenModifier = new SequenceEntityModifier(
						new MoveModifier(0.4f, sArrChitken[i].getX(),
								sArrChitken[i].getX() - Xlist[x],
								sArrChitken[i].getY(),
								sArrChitken[i].getY() - 80)));
		chikenModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				sArrChitken[i].setPosition(arrPointChicken[0][i],
						arrPointChicken[1][i]);
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void cowModifier() {
		A12_11_USHI.play();
		sCow1.setVisible(false);
		sCow2.setVisible(true);
		sCow3.setVisible(true);
		sCow2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sCow2.registerEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f), new AlphaModifier(0.1f,
						1.0f, 0.0f), new AlphaModifier(0.1f, 0.0f, 1.0f),
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f)));

		sCow3.setRotationCenter(56, 67);
		sCow3.registerEntityModifier(cowModifier = new SequenceEntityModifier(
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f),
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f)));
		cowModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				sCow1.setVisible(true);
				sCow3.setVisible(false);
				sCow2.setVisible(false);
				istouchCow = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		});
	}

	private void pigModifier() {
		A12_10_BUTA.play();
		sPig1.setVisible(false);
		sPig2.setVisible(true);
		sPig3.setVisible(true);
		sPig2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sPig2.registerEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f), new AlphaModifier(0.1f,
						1.0f, 0.0f), new AlphaModifier(0.1f, 0.0f, 1.0f),
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f)));

		sPig3.setRotationCenter(72, 99);
		sPig3.registerEntityModifier(pigModifier = new SequenceEntityModifier(
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f),
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f)));
		pigModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				sPig1.setVisible(true);
				sPig3.setVisible(false);
				sPig2.setVisible(false);
				istouchPig = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
		});
	}

	private void umaModifier() {
		A12_09_ROBA.play();
		sUma1.setVisible(false);
		sUma2.setVisible(true);
		sUma3.setVisible(true);
		sUma2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sUma2.registerEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f), new AlphaModifier(0.2f,
						1.0f, 0.0f), new AlphaModifier(0.1f, 0.0f, 1.0f),
				new AlphaModifier(0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
						0.0f, 1.0f), new AlphaModifier(0.1f, 1.0f, 0.0f),
				new AlphaModifier(0.1f, 0.0f, 1.0f)));
		sUma3.setRotationCenter(33, 60);
		sUma3.registerEntityModifier(umaModifier = new SequenceEntityModifier(
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f),
				new RotationModifier(0.3f, -10.0f, 10.0f),
				new RotationModifier(0.3f, 10.0f, -10.0f)));
		umaModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				sUma1.setVisible(true);
				sUma3.setVisible(false);
				sUma2.setVisible(false);
				istouchUma = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
		});
	}

	private void duckModifier() {
		if (istouchDuck) {
			A12_15_CYAPACHORO.play();
			istouchDuck = false;
			aniDuck1.registerEntityModifier(duck1Modifier = new SequenceEntityModifier(
					new MoveXModifier(4.0f, 192, 392, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							aniDuck1.setCurrentTileIndex(1);
						}
					}), new MoveXModifier(4.0f,
							392, 192)));
			aniDuck2.registerEntityModifier(duck2Modifier = new SequenceEntityModifier(
					new MoveXModifier(4.0f, 314, 114, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							aniDuck2.setCurrentTileIndex(1);
						}
					}), new MoveXModifier(4.0f,
							114, 314)));
			duck1Modifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					aniDuck1.setCurrentTileIndex(0);
					istouchDuck = true;
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
			duck2Modifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					aniDuck2.setCurrentTileIndex(0);
					istouchDuck = true;
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		}
	}

	private void pinWheelModifier() {
		if (istouchPin) {
			istouchPin = false;
			A12_13_GIGI.play();
			sPinWheel
					.registerEntityModifier(pinModifier = new SequenceEntityModifier(
							new RotationModifier(3.0f, 0.0f, 360.0f)));
			pinModifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					istouchPin = true;
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		}
	}
	
	public void loadDefault() {
		istouchBoy = true;
		istouchDuck = true;
		istouchPin = true;
		istouchWindown = true;
		istouchUma = true;
		istouchPig = true;
		istouchCow = true;
		istouchWara = true;
		istouchDogLeft = true;
		istouchDogRight = true;
		isStop = false;
		isClickSheep = false;
		istouchSheep = true;
		sChitkenRandom = new RanDomNoRepeat();
		pChitkenRandom = new RanDomNoRepeat();
	}

	private void clearData() {
		if (aniSheepOne != null) {
			aniSheepOne.stopAnimation();
			aniSheepOne.setVisible(false);
		}
		if (aniSheepTwo != null) {
			aniSheepTwo.stopAnimation();
			aniSheepTwo.setVisible(false);
		}
		countSheep = 0;
		tmpSheep = 1;
		if (aniWindown != null) {
			aniWindown.clearEntityModifiers();
			aniWindown.stopAnimation();
			aniWindown.setCurrentTileIndex(0);

		}
		if (aniDuck1 != null && aniDuck2 != null) {
			aniDuck1.clearEntityModifiers();
			aniDuck1.setPosition(192, 210);
			aniDuck1.setCurrentTileIndex(0);
			aniDuck2.clearEntityModifiers();
			aniDuck2.setPosition(314, 198);
			aniDuck2.setCurrentTileIndex(0);
			aniDuck1.setRotation(0);
			aniDuck2.setRotation(0);
		}
		if (aniDogLeft != null) {
			aniDogLeft.clearEntityModifiers();
			aniDogLeft.setPosition(102, 539);
			aniDogLeft.setCurrentTileIndex(0);
		}
		if (aniDogRight != null) {
			aniDogRight.clearEntityModifiers();
			aniDogRight.setPosition(737, 539);
			aniDogRight.setCurrentTileIndex(0);
		}
		if (umaModifier != null) {
			sUma2.clearEntityModifiers();
			sUma3.clearEntityModifiers();
			sUma1.setVisible(true);
			sUma3.setVisible(false);
			sUma2.setVisible(false);
		}
		if (pigModifier != null) {
			sPig2.clearEntityModifiers();
			sPig3.clearEntityModifiers();
			sPig1.setVisible(true);
			sPig3.setVisible(false);
			sPig2.setVisible(false);
		}
		if (cowModifier != null) {
			sCow2.clearEntityModifiers();
			sCow3.clearEntityModifiers();
			sCow1.setVisible(true);
			sCow3.setVisible(false);
			sCow2.setVisible(false);
		}
		if (pinModifier != null) {
			sPinWheel.clearEntityModifiers();
			sPinWheel.setRotation(0);
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		pinWheelModifier();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(aniBoy)) {
			istouchBoy = true;
		}
		if (pAnimatedSprite.equals(aniWindown)) {
			aniWindown.setCurrentTileIndex(0);
			istouchWindown = true;
			isTouchGimmic[0]=true;
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
