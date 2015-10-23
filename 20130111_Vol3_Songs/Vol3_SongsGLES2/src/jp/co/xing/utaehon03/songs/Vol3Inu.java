package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3InuResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.os.Handler;
import android.util.Log;

public class Vol3Inu extends BaseGameActivity implements IOnSceneTouchListener {
	
	private static final String TAG = "LOG_INU";
	
	private TexturePack ttpInu;
	private TexturePackTextureRegionLibrary ttpLibInu;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion ttrBackground, ttrInuScool, ttrInuClock, ttrInuClockMinute, ttrInuDog,ttrInuTire,
						  ttrInuCarRight, ttrInuCarLeft, ttrInuDogEye, ttrInuOlicestation, ttrInuBallon,
						  ttrInuDogAction, ttrInuFishshop, ttrInuFish, ttrInuFerrisWheel, ttrInuBallonFly,
						  ttrInuPinkCatDrag, ttrInuBlackCatDrag, ttrInuPurpleCatDrag, ttrInuWhiteCatDrag, ttrInuDogGlad;
	
	private Sprite sInuScool, sInuClock, sInuClockMinute, sInuDog, sInuCarRight, sInuCarLeft,
				   sInuTire3, sInuTire4, sInuTire7, sInuTire8, sInuDogEyeLeft, sInuDogEyeRight,
				   sInuOlicestation, sInuBallon, sInuDogAction, sInuFishshop, sInuFish,
				   sInuFerrisWheel, sInuBallonFly, sInuPinkCatDrag, sInuBlackCatDrag, sInuPurpleCatDrag,
				   sInuWhiteCatDrag, sInuDogGlad;
	
	private TiledTextureRegion tttrInuPinkcatParent, tttrInuPinkcatCry, tttrInuBlackCatParent, tttrInuBlackCatCry,
							   tttrInuPurpleCatParent, tttrInuPurpleCatCry, tttrInuWhiteCatParent, tttrInuWhiteCatCry,
							   tttrInuPinkCatSet, tttrInuBlackCatSet, tttrInuPurpleCatSet, tttrInuWhitCatSet, tttrInuGondola;
	
	private AnimatedSprite aniInuPinkcatParent, aniInuPinkcatCry, aniInuBlackCatParent, aniInuBlackCatCry,
						   aniInuPurpleCatParent, aniInuPurpleCatCry, aniInuWhiteCatParent, aniInuWhiteCatCry,
						   aniInuPinkCatSet, aniInuBlackCatSet, aniInuPurpleCatSet, aniInuWhiteCatSet, aniInuGondola;
	//private AnimatedSprite mAnimatedSprite[] = new AnimatedSprite[4];
	
	private Handler inuDogHandler;
	
	private Runnable inuDogCallback;
	
	private boolean isTouchInuDog, isInuDogGlad, isGimmicLeft, isGimicRight, isClock, isTouchInuGodola, isCat,
					isTouchCarRight, isTouchCarLeft, isTouchFish;
	private boolean isBlackCat, isPurpleCat, isPinkCat, isWhiteCat, isBall;
	
	private int iDogGlad = 1, iCountBall = 1;
	
	private Sound mp3InuSailen, mp3InuChime, mp3InuBear, mp3InuPichi, mp3InuFly, mp3InuWan, mp3InuHarb,
				  mp3InuYokatane, mp3InuCatCplus, mp3InuCatA, mp3InuCatB, mp3InuCatC, mp3InuCatD, mp3InuCatE, mp3InuCatF, mp3InuCatG;
	
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "Load resource");
		
		ttpInu = mTexturePackLoaderFromSource.load("inu.xml");
		ttpInu.loadTexture();
		ttpLibInu = ttpInu.getTexturePackTextureRegionLibrary();
		
		this.ttrBackground = ttpLibInu.get(Vol3InuResource.A1_19_IPHONE4_HAIKEI_ID);
		//InuScool
		this.ttrInuScool = ttpLibInu.get(Vol3InuResource.A1_05_1_IPHONE4_SCOOL_ID);
		//InuClock
		this.ttrInuClock = ttpLibInu.get(Vol3InuResource.A1_05_2_IPHONE4_CLOCK_ID);
		this.ttrInuClockMinute = ttpLibInu.get(Vol3InuResource.A1_05_3_IPHONE4_CLOCK_ID);
		//InuDog
		this.ttrInuDog = ttpLibInu.get(Vol3InuResource.A1_04_1_IPHONE4_DOG_ID);	
		//Dog Action
		this.ttrInuDogAction = ttpLibInu.get(Vol3InuResource.A1_9_1_IPHONE4_DOG_ACTION_ID);
		//InuTire
		this.ttrInuTire = ttpLibInu.get(Vol3InuResource.A1_03_2_IPHONE4_TIRE_ID);
		//InuCarRight
		this.ttrInuCarRight = ttpLibInu.get(Vol3InuResource.A1_03_1_IPHONE4_CAR_RIGHT_ID);
		//InuCarLeft
		this.ttrInuCarLeft = ttpLibInu.get(Vol3InuResource.A1_03_3_IPHONE4_CAR_LEFT_ID);
		//InuDogEye
		this.ttrInuDogEye = ttpLibInu.get(Vol3InuResource.A1_04_2_IPHONE4_DOG_EYE_ID);
		//
		this.ttrInuOlicestation = ttpLibInu.get(Vol3InuResource.A1_18_IPHONE4_POLICESTATION_ID);
		//InuBallon
		this.ttrInuBallon = ttpLibInu.get(Vol3InuResource.A1_06_1_IPHONE4_BALLOON_ID);
		this.ttrInuBallonFly = ttpLibInu.get(Vol3InuResource.A1_06_2_IPHONE4_BALLOON_FLY_ID);
		
		this.ttrInuFerrisWheel = ttpLibInu.get(Vol3InuResource.A1_08_1_IPHONE4_FERRISWHEEL_ID);
		this.tttrInuGondola = getTiledTextureFromPacker(ttpInu, 
				Vol3InuResource.A1_08_2_1_IPHONE4_ID,
				Vol3InuResource.A1_08_2_2_IPHONE4_ID,
				Vol3InuResource.A1_08_2_3_IPHONE4_ID,
				Vol3InuResource.A1_08_2_4_IPHONE4_ID,
				Vol3InuResource.A1_08_2_5_IPHONE4_ID,
				Vol3InuResource.A1_08_2_6_IPHONE4_ID);
		
		this.ttrInuFishshop = ttpLibInu.get(Vol3InuResource.A1_07_1_IPHONE4_FISHSHOP_ID);
		
		//cat
		this.tttrInuPinkcatParent = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_10_1_1_IPHONE4_PARENT_PINKCAT_ID,
				Vol3InuResource.A1_10_1_2_IPHONE4_PARENT_PINKCAT_ID,
				Vol3InuResource.A1_10_1_3_IPHONE4_PARENT_PINKCAT_ID);
		this.tttrInuPinkcatCry = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_11_1_IPHONE4_PINKCAT_CRY_ID,
				Vol3InuResource.A1_11_2_IPHONE4_PINKCAT_CRY_ID,
				Vol3InuResource.A1_11_3_IPHONE4_PINKCAT_CRY_ID);
		this.ttrInuPinkCatDrag = ttpLibInu.get(Vol3InuResource.A1_11_4_IPHONE4_PINKCAT_DRAG_ID);
		this.tttrInuPinkCatSet = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_10_2_1_IPHONE4_PINKCAT_SET_ID,
				Vol3InuResource.A1_10_2_2_IPHONE4_PINKCAT_SET_ID);
		
		this.tttrInuBlackCatParent = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_12_1_1_IPHONE4_PARENT_BLACKCAT_ID,
				Vol3InuResource.A1_12_1_2_IPHONE4_PARENT_BLACKCAT_ID,
				Vol3InuResource.A1_12_1_3_IPHONE4_PARENT_BLACKCAT_ID);
		this.tttrInuBlackCatCry = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_13_1_IPHONE4_BLACKCAT_CRY_ID,
				Vol3InuResource.A1_13_2_IPHONE4_BLACKCAT_CRY_ID,
				Vol3InuResource.A1_13_3_IPHONE4_BLACKCAT_CRY_ID);
		this.ttrInuBlackCatDrag = ttpLibInu.get(Vol3InuResource.A1_13_4_IPHONE4_BLACKCAT_DRAG_ID);
		this.tttrInuBlackCatSet = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_12_2_1_IPHONE4_BLACKCAT_SET_ID,
				Vol3InuResource.A1_12_2_2_IPHONE4_BLACKCAT_SET_ID);
		
		this.tttrInuPurpleCatParent = getTiledTextureFromPacker(ttpInu, 
				Vol3InuResource.A1_14_1_1_IPHONE4_PARENT_PURPLECAT_ID,
				Vol3InuResource.A1_14_1_2_IPHONE4_PARENT_PURPLECAT_ID,
				Vol3InuResource.A1_14_1_3_IPHONE4_PARENT_PURPLECAT_ID);
		this.tttrInuPurpleCatCry = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_15_1_IPHONE4_PURPLECAT_CRY_ID,
				Vol3InuResource.A1_15_2_IPHONE4_PURPLECAT_CRY_ID,
				Vol3InuResource.A1_15_3_IPHONE4_PURPLECAT_CRY_ID);
		this.ttrInuPurpleCatDrag = ttpLibInu.get(Vol3InuResource.A1_15_4_IPHONE4_PURPLECAT_DRAG_ID);
		this.tttrInuPurpleCatSet = getTiledTextureFromPacker(ttpInu, 
				Vol3InuResource.A1_14_2_1_IPHONE4_PURPLECAT_SET_ID,
				Vol3InuResource.A1_14_2_2_IPHONE4_PURPLECAT_SET_ID);
		
		this.tttrInuWhiteCatParent = getTiledTextureFromPacker(ttpInu,
				Vol3InuResource.A1_16_1_1_IPHONE4_PARENT_WHITECAT_ID,
				Vol3InuResource.A1_16_1_2_IPHONE4_PARENT_WHITECAT_ID,
				Vol3InuResource.A1_16_1_3_IPHONE4_PARENT_WHITECAT_ID);
		
		this.tttrInuWhiteCatCry = getTiledTextureFromPacker(ttpInu, 
				Vol3InuResource.A1_17_1_IPHONE4_WHITECAT_CRY_ID,
				Vol3InuResource.A1_17_2_IPHONE4_WHITECAT_CRY_ID,
				Vol3InuResource.A1_17_3_IPHONE4_WHITECAT_CRY_ID);
		this.ttrInuWhiteCatDrag = ttpLibInu.get(Vol3InuResource.A1_17_4_IPHONE4_WHITECAT_DRAG_ID);
		this.tttrInuWhitCatSet = getTiledTextureFromPacker(ttpInu, 
				Vol3InuResource.A1_16_2_1_IPHONE4_WHITECAT_SET_ID,
				Vol3InuResource.A1_16_2_2_IPHONE4_WHITECAT_SET_ID);
		
		this.ttrInuFish = ttpLibInu.get(Vol3InuResource.A1_07_2_IPHONE4_FISH_ID);
		
		this.ttrInuDogGlad = ttpLibInu.get(Vol3InuResource.A1_9_2_IPHONE4_DOG_GLAD_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		
		mp3InuSailen = loadSoundResourceFromSD(Vol3InuResource.E00106_SAILEN);
		
		mp3InuChime = loadSoundResourceFromSD(Vol3InuResource.E00107_CHIME);
		mp3InuBear = loadSoundResourceFromSD(Vol3InuResource.E0046_BEAR);
		mp3InuPichi = loadSoundResourceFromSD(Vol3InuResource.E00108_PICHI);
		mp3InuFly = loadSoundResourceFromSD(Vol3InuResource.E0082_FLY);
		mp3InuWan = loadSoundResourceFromSD(Vol3InuResource.E00109_WAN);
		mp3InuCatCplus = loadSoundResourceFromSD(Vol3InuResource.E00110_CATCPLUS);
		
		mp3InuHarb = loadSoundResourceFromSD(Vol3InuResource.E00117_HARB);
		mp3InuYokatane = loadSoundResourceFromSD(Vol3InuResource.E00195_YOKATANE);
		mp3InuCatA = loadSoundResourceFromSD(Vol3InuResource.E00116_CATA);
		mp3InuCatB = loadSoundResourceFromSD(Vol3InuResource.E00114_CATB);
		mp3InuCatC = loadSoundResourceFromSD(Vol3InuResource.E00111_CATC);
		mp3InuCatD = loadSoundResourceFromSD(Vol3InuResource.E00115_CATD);
		mp3InuCatE = loadSoundResourceFromSD(Vol3InuResource.E00113_CATE);
		mp3InuCatF = loadSoundResourceFromSD(Vol3InuResource.E00109_CATF);
		mp3InuCatG = loadSoundResourceFromSD(Vol3InuResource.E00112_CATG);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "Load scene");
		
		mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrBackground, this.getVertexBufferObjectManager())));
		
		sInuScool = new Sprite(334, 393, ttrInuScool, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuScool);
		
		sInuClock = new Sprite(448, 426, ttrInuClock, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuClock);
		
		sInuClockMinute = new Sprite(448, 426, ttrInuClockMinute, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuClockMinute);
		
		aniInuGondola = new AnimatedSprite(376, 0, tttrInuGondola, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuGondola);
		
		sInuFerrisWheel = new Sprite(376, 0, ttrInuFerrisWheel, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuFerrisWheel);
		
		sInuTire3 = new Sprite(11, 66, ttrInuTire, this.getVertexBufferObjectManager());
		sInuTire4 = new Sprite(55, 97, ttrInuTire, this.getVertexBufferObjectManager());
		sInuTire7 = new Sprite(51, 96, ttrInuTire, this.getVertexBufferObjectManager());
		sInuTire8 = new Sprite(96, 64, ttrInuTire, this.getVertexBufferObjectManager());
		
		sInuCarRight = new Sprite(833, 528, ttrInuCarRight, this.getVertexBufferObjectManager());
		sInuCarRight.attachChild(sInuTire3);
		sInuCarRight.attachChild(sInuTire4);
		this.mScene.attachChild(sInuCarRight);
		sInuCarRight.setVisible(true);
		
		sInuBallon = new Sprite(150, 151, ttrInuBallon, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuBallon);
		
		sInuCarLeft = new Sprite(0, 507, ttrInuCarLeft, this.getVertexBufferObjectManager());
		sInuCarLeft.attachChild(sInuTire7);
		sInuCarLeft.attachChild(sInuTire8);
		this.mScene.attachChild(sInuCarLeft);
		sInuCarLeft.setVisible(false);		
		
		sInuOlicestation = new Sprite(387, 223, ttrInuOlicestation, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuOlicestation);
		
		sInuDogGlad = new Sprite(387, 168, ttrInuDogGlad, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuDogGlad);
		sInuDogGlad.setVisible(false);
		
		sInuDog = new Sprite(387, 168, ttrInuDog, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuDog);
		
		sInuDogAction = new Sprite(387, 168, ttrInuDogAction, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuDogAction);
		sInuDogAction.setVisible(false);
		
		sInuDogEyeLeft = new Sprite(443, 238, ttrInuDogEye, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuDogEyeLeft);
		
		sInuDogEyeRight = new Sprite(493, 238, ttrInuDogEye, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuDogEyeRight);
		
		sInuFishshop = new Sprite(616, 223, ttrInuFishshop, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuFishshop);
		
		sInuFish = new Sprite(639, 215, ttrInuFish, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuFish);
		
		/**------------Cat------------------- */		
		aniInuPinkcatParent = new AnimatedSprite(-20, 50, tttrInuPinkcatParent, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPinkcatParent);
		
		sInuPinkCatDrag = new Sprite(762, 300, ttrInuPinkCatDrag, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuPinkCatDrag);
		sInuPinkCatDrag.setVisible(false);
		
		aniInuPinkCatSet = new AnimatedSprite(56, 84, tttrInuPinkCatSet, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPinkCatSet);
		aniInuPinkCatSet.setVisible(false);
		
		aniInuBlackCatParent = new AnimatedSprite(516, -20, tttrInuBlackCatParent, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuBlackCatParent);
		
		sInuBlackCatDrag = new Sprite(79, 322, ttrInuBlackCatDrag, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuBlackCatDrag);
		sInuBlackCatDrag.setVisible(false);
		
		aniInuBlackCatSet = new AnimatedSprite(516, 0, tttrInuBlackCatSet, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuBlackCatSet);
		aniInuBlackCatSet.setVisible(false);
		
		aniInuPurpleCatParent = new AnimatedSprite(140, 376, tttrInuPurpleCatParent, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPurpleCatParent);
		
		sInuPurpleCatDrag = new Sprite(218, 15, ttrInuPurpleCatDrag, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuPurpleCatDrag);
		sInuPurpleCatDrag.setVisible(false);
		
		aniInuPurpleCatSet = new AnimatedSprite(184, 361, tttrInuPurpleCatSet, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPurpleCatSet);
		aniInuPurpleCatSet.setVisible(false);
		
		aniInuWhiteCatParent = new AnimatedSprite(500, 360, tttrInuWhiteCatParent, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuWhiteCatParent);
		
		sInuWhiteCatDrag = new Sprite(773, 138, ttrInuWhiteCatDrag, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sInuWhiteCatDrag);
		sInuWhiteCatDrag.setVisible(false);
		
		aniInuWhiteCatSet = new AnimatedSprite(503, 375, tttrInuWhitCatSet, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuWhiteCatSet);
		aniInuWhiteCatSet.setVisible(false);
		
		aniInuPinkcatCry = new AnimatedSprite(772, 320, tttrInuPinkcatCry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPinkcatCry);
		
		aniInuBlackCatCry = new AnimatedSprite(79, 322, tttrInuBlackCatCry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuBlackCatCry);
		
		aniInuPurpleCatCry = new AnimatedSprite(261, 0, tttrInuPurpleCatCry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuPurpleCatCry);
		
		aniInuWhiteCatCry = new AnimatedSprite(773, 120, tttrInuWhiteCatCry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniInuWhiteCatCry);
		/*----End Cat----*/
		
		addGimmicsButton(mScene, Vol3InuResource.SOUND_GIMMIC, Vol3InuResource.IMAGE_GIMMIC, ttpLibInu);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		
		SoundFactory.setAssetBasePath("inu/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("inu/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "inu/gfx/");
		
		super.onCreateResources();
		
		
	}
	
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "OnResumeGame");
		super.onResumeGame();
		initial();
		//inuGondolaModifier();
		aniCatChildAction(this.aniInuPinkcatCry);
		aniCatChildAction(this.aniInuBlackCatCry);
		aniCatChildAction(this.aniInuPurpleCatCry);
		aniCatChildAction(this.aniInuWhiteCatCry);
	}

	@Override
	public synchronized void onPauseGame() {
		resetData();
		reStartGame();
		initial();
		super.onPauseGame();
	}

	private void reStartGame() {
		mp3InuSailen.stop();
		if(sInuCarLeft != null) {
			sInuCarLeft.clearEntityModifiers();
			sInuCarLeft.setPosition(0, 570);
			sInuCarLeft.setVisible(false);
		}
		
		if(sInuCarRight != null) {
			sInuCarRight.clearEntityModifiers();
			sInuCarRight.setPosition(833, 528);
			sInuCarRight.setVisible(true);
		}
		
		if(sInuClockMinute != null) {
			mp3InuChime.stop();
			//sInuClockMinute.clearUpdateHandlers();
			sInuClockMinute.clearEntityModifiers();
			sInuClockMinute.setRotation(0);
			sInuClockMinute.setPosition(448, 426);
			sInuClockMinute.setVisible(true);
		}
	}
	
	private void resetData() {
		
		iDogGlad = 1;
		mp3InuSailen.stop();
		
		if(isGimmicLeft) {
			isGimmicLeft = false;
		}
		
		if(!isGimicRight) {
			isGimicRight = true;
		}
		
		if(!isClock) {
			isClock = true;
		}
		
		int aRandom[] = new int[4];
		int aRandom1[] = new int[4];
		RanDomNoRepeat random = new RanDomNoRepeat();
		random.setLenghNoRepeat(aRandom1.length);
		
		for (int i = 0; i < aRandom1.length; i++) {
			aRandom1[i] = random.getNextIntNoRepeat(true);
		}
		
		RanDomNoRepeat random1 = new RanDomNoRepeat();
		random1.setLenghNoRepeat(4, 8);
		for (int i = 0; i < aRandom.length; i++) {
			aRandom[i] = random1.getNextIntNoRepeat(true);
		}
		
		if(aniInuBlackCatCry != null) {			
			aniInuBlackCatCry.clearEntityModifiers();
			//mScene.attachChild(aniInuBlackCatCry);
			setPositionCat(aniInuBlackCatCry, aRandom[0]);
			sInuBlackCatDrag.clearEntityModifiers();
			sInuBlackCatDrag.setPosition(aniInuBlackCatCry.getX(), aniInuBlackCatCry.getY());
			sInuBlackCatDrag.setVisible(false);			
		}
		
		if(aniInuPinkcatCry != null) {
			aniInuPinkcatCry.clearEntityModifiers();
			setPositionCat(aniInuPinkcatCry, aRandom[1]);
			sInuPinkCatDrag.clearEntityModifiers();
			sInuPinkCatDrag.setPosition(aniInuPinkcatCry.getX(), aniInuPinkcatCry.getY());
			sInuPinkCatDrag.setVisible(false);
		}
		
		if(aniInuPurpleCatCry != null) {
			aniInuPurpleCatCry.clearEntityModifiers();
			setPositionCat(aniInuPurpleCatCry, aRandom[2]);
			sInuPurpleCatDrag.clearEntityModifiers();
			sInuPurpleCatDrag.setPosition(aniInuPurpleCatCry.getX(), aniInuPurpleCatCry.getY());
			sInuPurpleCatDrag.setVisible(false);
		}
		
		if(aniInuWhiteCatCry != null) {
			aniInuWhiteCatCry.clearEntityModifiers();
			setPositionCat(aniInuWhiteCatCry, aRandom[3]);
			//sInuWhiteCatDrag.setX
			sInuWhiteCatDrag.clearEntityModifiers();
			sInuWhiteCatDrag.setPosition(aniInuWhiteCatCry.getX(), aniInuWhiteCatCry.getY());
			sInuWhiteCatDrag.setVisible(false);
		}
		
		if(aniInuBlackCatParent != null) {
			aniInuBlackCatParent.clearEntityModifiers();
			aniInuBlackCatSet.clearEntityModifiers();
			aniInuBlackCatSet.setVisible(false);
			setPositionCat(aniInuBlackCatParent, aRandom1[0]);
		}
		
		if(aniInuPinkcatParent != null) {
			aniInuPinkCatSet.clearEntityModifiers();
			aniInuPinkCatSet.setVisible(false);
			aniInuPinkcatParent.clearEntityModifiers();
			setPositionCat(aniInuPinkcatParent, aRandom1[1]);
		}
		
		if(aniInuPurpleCatParent != null) {
			aniInuPurpleCatSet.clearEntityModifiers();
			aniInuPurpleCatSet.setVisible(false);
			aniInuPurpleCatParent.clearEntityModifiers();
			setPositionCat(aniInuPurpleCatParent, aRandom1[2]);
		}
		
		if(aniInuWhiteCatParent != null) {
			aniInuWhiteCatSet.clearEntityModifiers();
			aniInuWhiteCatSet.setVisible(false);
			aniInuWhiteCatParent.clearEntityModifiers();
			setPositionCat(aniInuWhiteCatParent, aRandom1[3]);
		}
		
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		Log.d(TAG, "ACTION_DOWN");
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		
		inuTouchAction(pX, pY);
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if(sInuCarRight.contains(pX, pY)) {
				
				Log.d(TAG, "InuCar Action Right");
				inuCarActionRight();
			} else if(sInuCarLeft.contains(pX, pY)) {
				Log.d(TAG, "InuCar Action Left");
				inuCarActionLeft();
			} else if (sInuDog.contains(pX, pY)) {
				Log.d(TAG, "InuDog Action");
				inuDogTouchAction();
			} else if(sInuFish.contains(pX, pY)) {
				inuFishModifier();
			} else if(sInuClockMinute.contains(pX, pY)) {
				Log.d(TAG, "sInuClockMinute Action");
				inuClockModifier();
			} else if(aniInuGondola.contains(pX, pY)) {
				inuGondolaAction();
			} else if(checkContains(sInuBallon, 32, 11, 198, 147, pX, pY)) {
				Log.d(TAG, "Ball touch action");
				inuBallTouchAction();
			} else if(checkContains(aniInuPinkcatParent, 91, 25, 200, 196, pX, pY)) {
				inuTouchCatAction(this.aniInuPinkcatParent);
			} else if(checkContains(aniInuBlackCatParent, 91, 25, 200, 196, pX, pY)) {
				inuTouchCatAction(this.aniInuBlackCatParent);
			} else if(checkContains(aniInuPurpleCatParent, 91, 25, 200, 196, pX, pY)) {
				inuTouchCatAction(this.aniInuPurpleCatParent);
			} else if(checkContains(aniInuWhiteCatParent, 91, 25, 200, 196, pX, pY)) {
				inuTouchCatAction(aniInuWhiteCatParent);
			} else if(sInuBlackCatDrag.contains(pX, pY) && aniInuBlackCatCry.contains(pX, pY)) {
				isBlackCat = true;
				mp3InuCatG.play();
			} else if(sInuPurpleCatDrag.contains(pX, pY) && aniInuPurpleCatCry.contains(pX, pY)) {
				isPurpleCat = true;
				mp3InuCatB.play();
			} else if(sInuPinkCatDrag.contains(pX, pY) && aniInuPinkcatCry.contains(pX, pY)) {
				isPinkCat = true;
				mp3InuCatCplus.play();
			} else if(sInuWhiteCatDrag.contains(pX, pY) && aniInuWhiteCatCry.contains(pX, pY)) {
				isWhiteCat = true;
				mp3InuCatA.play();
			}
			return true;
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if(isBlackCat) {
				moveCatEvent(aniInuBlackCatCry, sInuBlackCatDrag, pX, pY);
			}
			if(isPurpleCat)
				moveCatEvent(aniInuPurpleCatCry, sInuPurpleCatDrag, pX, pY);
			if(isPinkCat)
				moveCatEvent(aniInuPinkcatCry, sInuPinkCatDrag, pX, pY);
			if(isWhiteCat)
				moveCatEvent(aniInuWhiteCatCry, sInuWhiteCatDrag, pX, pY);
			
			
			
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if(isBlackCat) {
				upCatEvent(aniInuBlackCatCry, sInuBlackCatDrag, aniInuBlackCatParent, aniInuBlackCatSet, pX, pY);
				isBlackCat = false;
			}
			
			if(isPurpleCat) {
				upCatEvent(aniInuPurpleCatCry, sInuPurpleCatDrag, aniInuPurpleCatParent, aniInuPurpleCatSet, pX, pY);
				isPurpleCat = false;
			}
			
			if(isPinkCat) {
				upCatEvent(aniInuPinkcatCry, sInuPinkCatDrag, aniInuPinkcatParent, aniInuPinkCatSet, pX, pY);
				isPinkCat = false;
			}
			
			if(isWhiteCat) {
				upCatEvent(aniInuWhiteCatCry, sInuWhiteCatDrag, aniInuWhiteCatParent, aniInuWhiteCatSet, pX, pY);
				isWhiteCat = false;
			}
			
			if(iDogGlad == 5) {
				Log.d(TAG, "............................ successfully: " + Integer.toString(iDogGlad));
				inuDogGladAction();
			}
			
		}
		return false;
	}
	
	/**
	 * Initial all variable
	 */
	private void initial() {
		//iDogGlad = 1;
		inuDogHandler = new Handler();
		isTouchInuDog = true;
		isTouchInuGodola = true;
		isInuDogGlad = true;
		isGimmicLeft = false;
		isGimicRight = true;
		isClock = true;
		isCat = true;
		isTouchCarLeft = false;
		isTouchCarRight = true;
		isTouchFish = true;
		isBlackCat = false;
		isPurpleCat = false;
		isPinkCat = false;
		isWhiteCat = false;
		isBall = true;
	}
	
	/**
	 * InuCarActionRight
	 */
	private void inuCarActionRight() {
		
		if(isTouchCarRight) {
			isTouchCarRight = false;
			mp3InuSailen.play();
			isGimicRight = false;
			sInuCarRight.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(5.0f, 833, -50, 528, -sInuCarRight.getHeight(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							
							/**runOnUpdateThread(new Runnable() {
								
								@Override
								public void run() {
									//mScene.detachChild(sInuCarRight);
								}
							});*/
							
							
							sInuCarRight.setVisible(false);
							sInuCarLeft.setPosition(-sInuCarLeft.getWidth(), 640);
							sInuCarLeft.setVisible(true);
							sInuCarLeft.registerEntityModifier(new SequenceEntityModifier(
									new MoveModifier(2.0f, -sInuCarLeft.getWidth(), 0, 640, 507, new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											isGimmicLeft = true;
											isTouchCarLeft = true;
										}
									})));
						}
					})
					));
		}
		
	}
	
	/**
	 * InuCarActionLeft
	 */
	private void inuCarActionLeft() {
		if (isTouchCarLeft) {
			isTouchCarLeft = false;
			isGimmicLeft = false;
			mp3InuSailen.play();
			sInuCarLeft.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(5.0f, 0 , 863, 507, -sInuCarLeft.getHeight(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sInuCarLeft.setVisible(false);						
							sInuCarRight.registerEntityModifier(new DelayModifier(0.0f, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									
									//sInuCarRight.setPosition(833, 528);
									sInuCarRight.setPosition(CAMERA_WIDTH, CAMERA_HEIGHT);
									sInuCarRight.setVisible(true);			
									sInuCarRight.registerEntityModifier(new SequenceEntityModifier(
											new MoveModifier(2.0f, CAMERA_WIDTH, 833, CAMERA_HEIGHT, 528, new IEntityModifierListener() {
												
												@Override
												public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
													
												}
												
												@Override
												public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
													isGimicRight = true;
													isTouchCarRight = true;
												}
											})));
								}
							}));															
						}
					})
					));		
		}
		
	}
	
	private void inuFishModifier() {
		if(isTouchFish) {
			isTouchFish = false;
			mp3InuPichi.play();
			sInuFish.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					
					isTouchFish = true;
				}
			},
					new RotationModifier(0.1f, 0.0f, -15.0f, EaseQuadOut.getInstance()),
					new RotationModifier(0.1f, -15.0f, 15.0f, EaseQuadOut.getInstance()),
					new RotationModifier(0.1f, 15.0f, 0.0f, EaseQuadOut.getInstance())));
		}
		
	}

	private void inuClockModifier() {
		//sInuClockMinute.registerEntityModifier(new LoopEntityModifier(new RotationModifier(60.0f, 0, 360)));
		if(isClock) {
			isClock = false;
			mp3InuChime.play();
			sInuClockMinute.registerEntityModifier(new SequenceEntityModifier(new RotationModifier(8.0f, 0, 360, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					isClock = true;
				}
			})));			
		}
	}
	
	private void inuGondolaAction() {
		if(isTouchInuGodola) {
			isTouchInuGodola = false;
			mp3InuFly.play();
			long pDurations[] = new long[] {400, 400, 400, 400, 400, 400};
			aniInuGondola.animate(pDurations, false, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					isTouchInuGodola = true;
				}
			});
			
		}
	}
	
	private void inuDogTouchAction() {
		if(isTouchInuDog) {
			sInuDog.setVisible(false);
			sInuDogAction.setVisible(true);
			isTouchInuDog = false;
			
			mp3InuWan.play();
			
			inuDogHandler.removeCallbacks(inuDogCallback);
			inuDogHandler.postDelayed(
					inuDogCallback = new Runnable() {
						
						@Override
						public void run() {
							sInuDog.setVisible(true);
							sInuDogAction.setVisible(false);
							isTouchInuDog = true;
						}
					}, 600);
		}
	}
	
	private void inuDogGladAction() {
		if(isInuDogGlad) {
			sInuDog.setPosition(-999, -999);
			sInuDog.setVisible(false);
			sInuDogAction.setVisible(false);
			sInuDogEyeRight.setVisible(false);
			sInuDogEyeLeft.setVisible(false);
			
			sInuDogGlad.setVisible(true);
			isInuDogGlad = false;
			
			mp3InuYokatane.play();
			
			sInuDogGlad.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					aniInuBlackCatSet.registerEntityModifier(new SequenceEntityModifier(
							new ScaleModifier(1.0f, 1, 1.3f),
							new ScaleModifier(1.0f, 1.3f, 1f)));
					aniInuPinkCatSet.registerEntityModifier(new SequenceEntityModifier(
							new ScaleModifier(1.0f, 1, 1.3f),
							new ScaleModifier(1.0f, 1.3f, 1f)));
					aniInuPurpleCatSet.registerEntityModifier(new SequenceEntityModifier(
							new ScaleModifier(1.0f, 1, 1.3f),
							new ScaleModifier(1.0f, 1.3f, 1f)));
					aniInuWhiteCatSet.registerEntityModifier(new SequenceEntityModifier(
							new ScaleModifier(1.0f, 1, 1.3f),
							new ScaleModifier(1.0f, 1.3f, 1f)));
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sInuDogGlad.setVisible(false);
					sInuDog.setPosition(387, 168);
					sInuDog.setVisible(true);
					sInuDogEyeLeft.setVisible(true);
					sInuDogEyeRight.setVisible(true);
					isInuDogGlad = true;
					resetData();
				}
			}));
			
		}
	}
	
	private void inuBallTouchAction() {
		int pToX = showRandomInteger(480, 960);
		int pToY= -200;
		if(isBall) {
			iCountBall++;
			if(iCountBall == 6) {
				isBall = false;
				sInuBallonFly = new Sprite(112, 151, ttrInuBallonFly, this.getVertexBufferObjectManager());
				this.mScene.attachChild(sInuBallonFly);
				sInuBallonFly.registerEntityModifier(new SequenceEntityModifier(
						new MoveModifier(1.5f, 260, pToX, 198, pToY, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								mp3InuBear.play();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if(iCountBall == 6) {
									isBall = true;
									iCountBall = 1;
								}
							}
						})
					));
			} else {
				sInuBallonFly = new Sprite(112, 151, ttrInuBallonFly, this.getVertexBufferObjectManager());
				this.mScene.attachChild(sInuBallonFly);
				sInuBallonFly.registerEntityModifier(new SequenceEntityModifier(
						new MoveModifier(1.5f, 260, pToX, 198, pToY, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								mp3InuBear.play();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
						})
					));
			}
			
		}
		
	}
	
	private void inuTouchAction(int x, int y) {
		Log.d(TAG, "pX:" + Integer.toString(x) + " | pY:" + Integer.toString(y) );
		
		if(0 <= x && x < CAMERA_WIDTH/2 && 0 <= y && y < CAMERA_HEIGHT/2) {
			Log.d(TAG, "1/4..................1/4");
			sInuDogEyeLeft.setPosition(440, 235);
			sInuDogEyeRight.setPosition(490, 235);
		} else if(0 <= x && x < CAMERA_WIDTH/2 && CAMERA_HEIGHT/2 <= y && y < CAMERA_HEIGHT) {
			Log.d(TAG, "3/4..................3/4");
			sInuDogEyeLeft.setPosition(440, 241);
			sInuDogEyeRight.setPosition(490, 241);
		} else if(CAMERA_WIDTH/2 <= x && x < CAMERA_WIDTH && 0 <= y && y < CAMERA_HEIGHT/2) {
			Log.d(TAG, "2/4..................2/4");
			sInuDogEyeLeft.setPosition(446, 233);
			sInuDogEyeRight.setPosition(496, 233);
		} else if(CAMERA_WIDTH/2 < x && x < CAMERA_WIDTH && CAMERA_HEIGHT/2 < y && y < CAMERA_HEIGHT) {
			Log.d(TAG, "4/4...................4/4");
			sInuDogEyeLeft.setPosition(447, 243);
			sInuDogEyeRight.setPosition(497, 243);
		}
	}
	
	private void inuTouchCatAction(AnimatedSprite aniCat) {
		if(isCat) {
			isCat = false;
			if(aniCat.equals(aniInuPinkcatParent))
				mp3InuCatF.play();
			if(aniCat.equals(aniInuBlackCatParent))
				mp3InuCatC.play();
			if(aniCat.equals(aniInuPurpleCatParent))
				mp3InuCatE.play();
			if(aniCat.equals(aniInuWhiteCatParent))
				mp3InuCatD.play();
			
			long pDurations[] = new long[] {500, 500, 500};
			int pFrame[] = new int[] {2, 1, 0};
			aniCat.animate(pDurations, pFrame, 0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					isCat = true;
				}
			});
		}
	}
	
	private void aniCatChildAction(AnimatedSprite aniChildCat) {
		long pDurations[] = new long[] {600, 600, 600};
		aniChildCat.animate(pDurations);
	}
	
	private void aniCatSetAction(AnimatedSprite aniCat) {
		long pDurations[] = new long[] {400, 400};
		aniCat.animate(pDurations, true);
	}
	
	private void moveCatEvent(AnimatedSprite aniCat, Sprite sCat, int pX, int pY) {		
		aniCat.setVisible(false);
		sCat.setVisible(true);
		
		sCat.setPosition(pX - sCat.getWidth() / 2, pY - sCat.getHeight() / 2);			
	}

	private void upCatEvent(AnimatedSprite aniCatCry, Sprite sCatMove, AnimatedSprite aniCatParent, AnimatedSprite aniCatSet, int pX, int pY) {
		
		//aniCatParent.setWidth(200);
		//aniCatParent.setHeight(200);
		if(sCatMove.collidesWith(aniCatParent) && checkContains(aniCatParent, 91, 25, 200, 196, pX, pY)) {
			   aniCatSetAction(aniCatSet);
			   aniCatSet.setPosition(aniCatParent.getX(), aniCatParent.getY());
			   aniCatSet.setVisible(true);
			   
			   aniCatCry.setPosition(-999, -999);
			   aniCatCry.setVisible(false);
			   sCatMove.setVisible(false);
			   aniCatParent.setPosition(-999, -999);
			   aniCatParent.setVisible(false);
			   //mScene.unregisterTouchArea(pTouchArea);
			   //mScene.detachChild(aniCatCry);
			   //mScene.detachChild(sCatMove);
			   //mScene.detachChild(aniCatParent);
			   mp3InuHarb.play();
			   iDogGlad++;
		   } else {
			   aniCatCry.setVisible(true);
			   sCatMove.setPosition(aniCatCry.getX(), aniCatCry.getY());
			   sCatMove.setVisible(false);
		   }		
	}
		
	private int showRandomInteger(int iStart, int iEnd) {
		int randomNumber = 0;
		Random aRandom = new Random();
		long range = (long)iEnd - (long)iStart + 1;
		long fraction = (long)(range * aRandom.nextDouble());
		randomNumber = (int)(fraction + iStart);
		return randomNumber;
	}
	
	private void setPositionCat(AnimatedSprite myShape,int x) {
		if(x == 0) {
			myShape.setPosition(-20, 50);
			myShape.setVisible(true);
		} else if(x == 1) {
			myShape.setPosition(516, -20);
			myShape.setVisible(true);
		} else if(x == 2) {
			myShape.setPosition(500, 360);
			myShape.setVisible(true);
		} else if(x == 3) {
			myShape.setPosition(140, 376);
			myShape.setVisible(true);
		} else if(x == 4) {
			myShape.setPosition(772, 320);
			myShape.setVisible(true);
		} else if(x == 5) {
			myShape.setPosition(79, 322);
			myShape.setVisible(true);
		} else if(x == 6) {
			myShape.setPosition(773, 120);
			myShape.setVisible(true);
		} else if(x == 7) {
			myShape.setPosition(261, 0);
			myShape.setVisible(true);
		}
	}
	
	@Override
	public void combineGimmic3WithAction() {
		if(isGimmicLeft) {
			//isGimmic = false;
			inuCarActionLeft();
		}
		
		if(isGimicRight) {
			inuCarActionRight();
		}
		
	}
	
	class AniSprite extends AnimatedSprite{
		
		public static final int TYPE_A = 0;
		public static final int TYPE_B = 0;
		public static final int TYPE_C = 0;
		public static final int TYPE_D = 0;
		
		//private boolean isTouch = true;
		
		
		private int TYPE = 0;

		public AniSprite(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager, int pType) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			TYPE = pType;
		}
		
		
		public boolean isCheckType(AniSprite pAniSprite){
			if(this.collidesWith(pAniSprite)){
				if(this.TYPE == pAniSprite.TYPE){
					return true;
				}
			}
			return false;
		}
		
	}

}
