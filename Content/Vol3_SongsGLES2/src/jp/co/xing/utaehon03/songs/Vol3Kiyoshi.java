package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KiyoshiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import android.util.Log;

public class Vol3Kiyoshi extends BaseGameActivity implements IOnSceneTouchListener{
	// TAG 
	private String TAG = "VOL3_KIYOSHI";
	
	// Load
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack texpackKiyoshi1,texpackKiyoshi2;
	private TexturePackTextureRegionLibrary texpackLibKiyoshi1,texpackLibKiyoshi2;
	
	// Backgournd
	private Sprite sprBackGround;
	private TextureRegion ttrBackGround;
	
	private Sprite sprBackGroundSnow;
	private TextureRegion ttrBackGroundSnow;
	
	// Tree Pine
	private Sprite sprChangePine;
	private TextureRegion ttrTreePine;
	
	//Star Sky
	private Sprite sprStar[] = new Sprite[23];
	//Star Clause
	private SpritePool sprPoolStar;
	private ArrayList<Sprite> arrayStar = new ArrayList<Sprite>();
	private TextureRegion ttrStar;
	
	// Snow
	private SpritePool sprPoolSnowLarge;
	private SpritePool sprPoolSnowMedium;
	private SpritePool sprPoolSnowSmall;
	
	private ArrayList<Sprite> arraySnowLarge = new ArrayList<Sprite>();
	private ArrayList<Sprite> arraySnowMedium = new ArrayList<Sprite>();
	private ArrayList<Sprite> arraySnowSmall = new ArrayList<Sprite>();
	
	private TextureRegion ttrSnowLarge;
	private TextureRegion ttrSnowMedium;
	private TextureRegion ttrSnowSmall;
	
	// City
	private AnimatedSprite animsprCity;
	private ITiledTextureRegion tttrCity;
	
	// Pine
	private AnimatedSprite animsprPine;
	private ITiledTextureRegion tttrPine;
	
	//Cat
	private AnimatedSprite animsprCatNoel;
	private ITiledTextureRegion tttrCatNoel;
	
	// Snow man
	private AnimatedSprite animsprSnowMan;
	private ITiledTextureRegion tttrSnowMan;
	
	// Star Pine
	private AnimatedSprite animsprStarPine;
	private ITiledTextureRegion tttrStarPine;
	
	//Santa clause
	private AnimatedSprite animsprSantaClaus;
	private ITiledTextureRegion tttrSantaClaus;
	
	// Baby
	private AnimatedSprite animsprBaby;
	private ITiledTextureRegion tttrBaby;
	
	//GIMMIC
	private Sprite sprGimmic;
	private TextureRegion ttrGimmic;
	
	// Boolean
	private boolean isTouchCity;
	private boolean isTouchCat;
	private boolean isTouchChangeTree;
	private boolean isTouchSnowMan;
	private boolean isTouchStarPine;
	
	// Point
	private int pointStar[][] = {{50,140}, {20,25}, {120,65}, {185,15}, {0,235}, {135,190}, {415,25}, {430,160}, {510,105}, {500,25},
								 {485,235},{590,275},{560,350},{610,180},{625,85},{715,5},{870,25},{785,100},{730,170},{900,135},
								 {855,255},{715,255},{770,320}};
	
	private float arrPointCat[][] = {{22, 39, 90, 90 , 0 , 0 , 22}, 
									 {45, 0 , 0 , 90 , 90, 45, 45}};
	
	private float arrSnowMan[][] = {{40, 180, 180, 40 , 40},
									{20, 20 , 105, 105, 20}};
	
	private float arrCity1[][] = {{0  , 80 , 80 , 0   , 0},
								  {440, 440, 600, 600 , 440}};
	
	private float arrCity2[][] = {{360, 450, 450, 480, 480, 360, 360},
								  {540, 540, 480, 480, 600, 600, 540}};
	
	//PLATE
	private Sprite sprPlate[] = new Sprite[5];
	private TextureRegion ttrPlate;
	
	// Box Tree
	private float arrTree[][] = {{255, 290, 290, 510, 365, 365, 210, 210, 35 , 255, 255},
								 {0  , 0  , 25 , 365, 420, 365, 365, 420, 365, 25 , 0 }};
	
	//Item PLATE
	private AnimSprPlate animsprPLATE[] = new AnimSprPlate[30];
	private ITiledTextureRegion tttrItemPlate0[] = new ITiledTextureRegion[6];
	private ITiledTextureRegion tttrItemPlate1[] = new ITiledTextureRegion[5];
	private ITiledTextureRegion tttrItemPlate2[] = new ITiledTextureRegion[6];
	private ITiledTextureRegion tttrItemPlate3[] = new ITiledTextureRegion[6];
	private ITiledTextureRegion tttrItemPlate4[] = new ITiledTextureRegion[7];
	
	//Link List
	LinkedList<AnimNewItemPlate> itemLinkedList = new LinkedList<Vol3Kiyoshi.AnimNewItemPlate>();
	private AnimNewItemPlate animItemPlate;

	private float pointItemPlate[][] = {{40,40},{150,14},{255,20},{55,165},{155,155},{255,155},
										{54,32},{155,39},{241,44},{54,167},{193,166},
										{22,30},{133,28},{220,54},{48,132},{149,148},{255,146},
										{45,38},{124,36},{230,30},{37,152},{124,161},{234,115},
										{13,10},{97,10},{183,10},{268,10},{35,116},{143,105},{268,123}};
										
	private float pointItemScene[][] = {{625,360},{735,334},{840,340},{640,485},{740,475},{840,475},
										{639,352},{740,359},{826,364},{639,487},{778,486},
										{607,350},{718,348},{805,374},{633,452},{734,468},{840,466},
										{630,358},{709,356},{815,350},{622,472},{709,481},{819,435},
										{598,330},{682,330},{768,330},{853,330},{620,436},{728,425},{853,443}};
	
	//COUNT
	private int countItem;
	private boolean movePlate;
	
	private int currentPlate;
	
	//CURRENT PLATE
	private boolean isTouchGimmic;
	
	// TimerHandler
	TimerHandler timerStar;
	TimerHandler timerSnow;
	
	TimerHandler timerStarTree;
	TimerHandler timerChangeTree;
	
	//SOUND
	private Sound OGG_A80_10_MOKKIN;
	private Sound OGG_A80_15_KIRAN;
	private Sound OGG_A80_1_2_MATI;
	private Sound OGG_A80_2_NYA;
	private Sound OGG_A80_5_4_KIRAKIRA;
	private Sound OGG_A80_5_POMI;
	private Sound OGG_A80_7_HAI;
	private Sound OGG_A80_8_BOMIBOMI;
	private Sound OGG_A80_9_HAHAHA;
	private Sound OGG_A80_ITEM_HYUI;

	//Retangle
	private RectangularShape shapeSnow;
	// onCreateResources
	@Override
	public void onCreateResources() {
		Log.d(TAG, "Load --- onCreateResources");
		SoundFactory.setAssetBasePath("kiyoshi/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kiyoshi/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
			getTextureManager(), pAssetManager, "kiyoshi/gfx/");
		super.onCreateResources();
		Log.d(TAG, "Load --- onCreateResources --- End");
	}

	// loadKaraokeResources
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "Load --- loadKaraokeResources");
		//Library
		texpackKiyoshi1 = mTexturePackLoaderFromSource.load("kiyoshi1.xml");
		texpackKiyoshi1.loadTexture();
		texpackLibKiyoshi1 = texpackKiyoshi1.getTexturePackTextureRegionLibrary();
		
		texpackKiyoshi2 = mTexturePackLoaderFromSource.load("kiyoshi2.xml");
		texpackKiyoshi2.loadTexture();
		texpackLibKiyoshi2 = texpackKiyoshi2.getTexturePackTextureRegionLibrary();
		
		//BackGround
		ttrBackGround = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_1_1_IPHONE_ID);
		//Snow
		ttrBackGroundSnow = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_6_1_IPHONE_ID);
		
		//City
		tttrCity = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_1_2_1_IPHONE_ID,
			Vol3KiyoshiResource.A80_1_2_2_IPHONE_ID);
		
		// Pine
		tttrPine = getTiledTextureFromPacker(texpackKiyoshi1, Vol3KiyoshiResource.A80_5_1_IPHONE_ID,
			Vol3KiyoshiResource.A80_5_2_IPHONE_ID,Vol3KiyoshiResource.A80_5_3_IPHONE_ID);
		
		// Cat Noel
		tttrCatNoel = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_2_1_IPHONE_ID,
			Vol3KiyoshiResource.A80_2_2_IPHONE_ID);
		
		//Tree Pine
		ttrTreePine = texpackLibKiyoshi1.get(Vol3KiyoshiResource.A80_16_IPHONE_ID);
		
		//Star Pine
		tttrStarPine = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_5_4_1_IPHONE_ID,
			Vol3KiyoshiResource.A80_5_4_2_IPHONE_ID);
		
		//Snow Man
		tttrSnowMan = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_7_1_IPHONE_ID
			,Vol3KiyoshiResource.A80_7_2_IPHONE_ID);
		
		//Star
		ttrStar = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_3_1_IPHONE_ID);
		
		//Santa Claus
		tttrSantaClaus = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_8_1_IPHONE_ID
			,Vol3KiyoshiResource.A80_8_2_IPHONE_ID);
		
		//Baby
		tttrBaby = getTiledTextureFromPacker(texpackKiyoshi2, Vol3KiyoshiResource.A80_9_1_IPHONE_ID
				,Vol3KiyoshiResource.A80_9_2_IPHONE_ID);
		
		
		//Snow
		ttrSnowLarge = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_4_1_IPHONE_ID);
		ttrSnowMedium = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_4_2_IPHONE_ID);
		ttrSnowSmall = texpackLibKiyoshi2.get(Vol3KiyoshiResource.A80_4_3_IPHONE_ID);
		
		//Plate
		ttrPlate = texpackLibKiyoshi1.get(Vol3KiyoshiResource.A80_10_1_IPHONE_ID);
		
		//Item plate
		// 1
		for (int i = 0; i < tttrItemPlate0.length; i++) {
			tttrItemPlate0[i] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate0[i]);
		}
		
		//2
		for (int i = 0; i < tttrItemPlate1.length; i++) {
			tttrItemPlate1[i] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate1[i]);
		}
		
		//3
		for (int i = 0; i < tttrItemPlate2.length; i++) {
			tttrItemPlate2[i] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate2[i]);
		}
		
		//4
		for (int i = 0; i < tttrItemPlate3.length; i++) {
			tttrItemPlate3[i] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate3[i]);
		}
		
		// 5
		tttrItemPlate4[0] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate4_0);
		tttrItemPlate4[1] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate4_1);
		tttrItemPlate4[2] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate4_2);
		tttrItemPlate4[3] = getTiledTextureFromPacker(texpackKiyoshi1,Vol3KiyoshiResource.KiyoshiPlate4_3);
		tttrItemPlate4[4] = getTiledTextureFromPacker(texpackKiyoshi1, Vol3KiyoshiResource.A80_15_5_IPHONE_ID);
		tttrItemPlate4[5] = getTiledTextureFromPacker(texpackKiyoshi1, Vol3KiyoshiResource.A80_15_6_IPHONE_ID);
		tttrItemPlate4[6] = getTiledTextureFromPacker(texpackKiyoshi1, Vol3KiyoshiResource.A80_15_7_IPHONE_ID);

		//Gimmic
		ttrGimmic = texpackLibKiyoshi1.get(Vol3KiyoshiResource.A2_03_IPHONE_QUESTION_ID);
		
		Log.d(TAG, "Load --- loadKaraokeResources --- End");
	}

	// loadKaraokeSound
	@Override
	protected void loadKaraokeSound() {
		OGG_A80_10_MOKKIN = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_10_MOKKIN);
		OGG_A80_15_KIRAN = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_15_KIRAN);
		OGG_A80_1_2_MATI = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_1_2_MATI);
		OGG_A80_2_NYA = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_2_NYA);
		OGG_A80_5_4_KIRAKIRA = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_5_4_KIRAKIRA);
		OGG_A80_5_POMI = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_5_POMI);
		OGG_A80_7_HAI = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_7_HAI);
		OGG_A80_8_BOMIBOMI = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_8_BOMIBOMI);
		OGG_A80_9_HAHAHA = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_9_HAHAHA);
		OGG_A80_ITEM_HYUI = loadSoundResourceFromSD(Vol3KiyoshiResource.A80_ITEM_HYUI);

	}

	// loadKaraokeScene
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "Load --- loadKaraokeScene");
		mScene = new Scene();
		//BackGround
		sprBackGround = new Sprite(0, 0, ttrBackGround, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackGround);
		
		//City
		animsprCity = new AnimatedSprite(0, 366, tttrCity, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprCity);
		this.mScene.registerTouchArea(animsprCity);
		
		shapeSnow = new Rectangle(0, 0, 960, 680, this.getVertexBufferObjectManager());
		mScene.attachChild(shapeSnow);
		shapeSnow.setAlpha(0.0f);
		
		//Cat Noel
		animsprCatNoel = new AnimatedSprite(315, 55, tttrCatNoel, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprCatNoel);
		
		//Pine Tree
		animsprPine = new AnimatedSprite(22, 75, tttrPine, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprPine);
		
		//Tree Pine (Change)
		sprChangePine = new Sprite(255, 520, ttrTreePine, this.getVertexBufferObjectManager());
		mScene.attachChild(sprChangePine);
		
		//Star Pine
		animsprStarPine = new AnimatedSprite(218, -20, tttrStarPine, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprStarPine);
		
		//Star
		for (int i = 0; i < sprStar.length; i++) {
			sprStar[i] = new Sprite(pointStar[i][0], pointStar[i][1], ttrStar, this.getVertexBufferObjectManager());
			mScene.attachChild(sprStar[i]);
		}
		
		//Background snow
		sprBackGroundSnow = new Sprite(0, 593, ttrBackGroundSnow, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackGroundSnow);
		
		//Snow Man
		animsprSnowMan = new AnimatedSprite(49, 510, tttrSnowMan, this.getVertexBufferObjectManager());
		animsprSnowMan.setZIndex(1);
		mScene.attachChild(animsprSnowMan);
		
		//Baby
		animsprBaby = new AnimatedSprite(0, 458, tttrBaby, this.getVertexBufferObjectManager());
		animsprBaby.setZIndex(2);
		mScene.attachChild(animsprBaby);
		animsprBaby.setVisible(false);
		
		//Plate
		for (int i = 0; i < sprPlate.length; i++) {
			sprPlate[i] = new Sprite(960, 320, ttrPlate, this.getVertexBufferObjectManager());
			mScene.attachChild(sprPlate[i]);
			sprPlate[i].setVisible(false);
		}
		
		//ITEM PLATE
		//1
		for (int i = 0; i < 6; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate0[i], this.getVertexBufferObjectManager()
				, i, 0, 0);
			sprPlate[0].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//2
		for (int i = 6; i < 11; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate1[i-6], this.getVertexBufferObjectManager()
				, i, 1, 0);
			sprPlate[1].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//3
		for (int i = 11; i < 17; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate2[i-11], this.getVertexBufferObjectManager()
				, i, 2, 0);
			sprPlate[2].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//4
		for (int i = 17; i < 23; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate3[i-17], this.getVertexBufferObjectManager()
				, i, 3, 0);
			sprPlate[3].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//5_1
		for (int i = 23; i < 27; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate4[i-23], this.getVertexBufferObjectManager()
				, i, 4, 1);
			sprPlate[4].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//5_2
		for (int i = 27; i < animsprPLATE.length; i++) {
			animsprPLATE[i] = new AnimSprPlate(pointItemPlate[i][0], pointItemPlate[i][1]
				,tttrItemPlate4[i-23], this.getVertexBufferObjectManager()
				, i, 4, 0);
			sprPlate[4].attachChild(animsprPLATE[i]);
			mScene.registerTouchArea(animsprPLATE[i]);
		}
		
		//Santa Claus
		animsprSantaClaus = new AnimatedSprite(960, 160, tttrSantaClaus, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprSantaClaus);
		
		//GIMMIC
		sprGimmic = new Sprite(510, 500, ttrGimmic, this.getVertexBufferObjectManager());
		sprGimmic.setZIndex(3);
		mScene.attachChild(sprGimmic);
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "Load --- loadKaraokeScene --- End");
	}

	//GIMMIC
	@Override
	public void combineGimmic3WithAction() {
	}

	//RESUME GAME
	@Override
	public synchronized void onResumeGame() {
		sprPlate[0].setVisible(true);
		sprPlate[0].setPosition(585, 320);
		
		sprPoolStar = new SpritePool(ttrStar);
		sprPoolSnowLarge = new SpritePool(ttrSnowLarge);
		sprPoolSnowMedium= new SpritePool(ttrSnowMedium);
		sprPoolSnowSmall = new SpritePool(ttrSnowSmall);
		
		visibleSnow();
		
		displayStar();
		
		timerStarTree();
		timerChange();
		
		initializationGame();
		super.onResumeGame();
	}
	
	//PAUSE GAME
	@Override
	public synchronized void onPauseGame() {
		mp3Stop();
		resetALL();
		super.onPauseGame();
	}
	
	// ===========================================================
	// GETTER & SETTER
	// ===========================================================

	// ===========================================================
	// METHODS FOR/ FROM SUPERCLASS / INTERFACES
	// ===========================================================

	// ON TOUCH SCENE
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.isActionDown()){
			//TOUCH GIMMIC
			if(checkContains(sprGimmic, 15, 15, 90, 90, pX, pY) && isTouchGimmic){
				sprGimmic.registerEntityModifier(new SequenceEntityModifier(
					new ScaleModifier(0.35f, 1, 1.3f),
					new ScaleModifier(0.35f, 1.3f, 1f)));
				OGG_A80_10_MOKKIN.play();
				isTouchGimmic = false;
				movePlate = false;
				currentPlate++;
				if(currentPlate > 4) currentPlate = 0;
				changePlate();
				
			}
			
			//TOUCH CAT NOEL
			if(checkContainsPolygon(animsprCatNoel,arrPointCat , 6, pX, pY) && isTouchCat){
				OGG_A80_2_NYA.play();
				isTouchCat = false;
				animatedAnim(animsprCatNoel, 300, 400, 0, 1, 1);
			}
			
			//TOUCH CITY
			if(checkContainsPolygon(sprBackGround, arrCity1, 4, pX, pY) && isTouchCity){
				OGG_A80_1_2_MATI.play();
				isTouchCity = false;
				nextTileAnimatedSprite(animsprCity);
				animsprCity.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchCity = true;
					}
				}));
			}
			
			if(checkContainsPolygon(sprBackGround, arrCity2, 6, pX, pY) && isTouchCity) {
				OGG_A80_1_2_MATI.play();
				isTouchCity = false;
				nextTileAnimatedSprite(animsprCity);
				animsprCity.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchCity = true;
					}
				}));
			}
			//TOUCH CHANGE PINE
			if(sprChangePine.contains(pX, pY) && isTouchChangeTree){
				mScene.unregisterUpdateHandler(timerChangeTree);
				sprChangePine.clearEntityModifiers();
				sprChangePine.setRotation(0.0f);
				OGG_A80_5_POMI.play();
				isTouchChangeTree = false;
				sprChangePine.registerEntityModifier(new SequenceEntityModifier(
					new ScaleAtModifier(0.4f, 1.0f, 1.3f, sprChangePine.getWidth()/2, sprChangePine.getHeight()/2),
					new ScaleAtModifier(0.4f, 1.3f, 1.0f, sprChangePine.getWidth()/2, sprChangePine.getHeight()/2)));
				nextTileAnimatedSprite(animsprPine);
				animsprPine.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						timerChange();
						isTouchChangeTree = true;
					}
				}));
			}
			
			//TOUCH SNOW MAN
			if(checkContainsPolygon(animsprSnowMan, arrSnowMan, 4, pX, pY) && isTouchSnowMan){
				OGG_A80_7_HAI.play();
				isTouchSnowMan = false;
				animatedAnim(animsprSnowMan, 300, 400, 0, 1, 0);
			}
			
			//TOUCH STAR PINE
			if(checkContains(animsprStarPine, 35, 35, 116, 116, pX, pY) && isTouchStarPine){
				mScene.unregisterUpdateHandler(timerStarTree);
				animsprStarPine.clearEntityModifiers();
				animsprStarPine.setRotation(0.0f);
				OGG_A80_5_4_KIRAKIRA.play();
				playSoundDelay(OGG_A80_8_BOMIBOMI, 0.7f);
				isTouchStarPine = false;
				animatedAnim(animsprStarPine, 400, 400, 0, 1, -1);
				animatedAnim(animsprSantaClaus, 400, 400, 0, 1, -1);
				visibleStar();
				animsprSantaClaus.registerEntityModifier(
					new RotateCubicBezierMoveModifier(5.0f, 960, 160, 630, 170, 400, 120, 250, -300,EaseLinear.getInstance()));
				animsprStarPine.registerEntityModifier(new DelayModifier(5.1f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						timerStarTree();
						mScene.unregisterUpdateHandler(timerStar);
						isTouchStarPine = true;
						
						animsprSantaClaus.stopAnimation();
						animsprSantaClaus.setCurrentTileIndex(0);
						animsprSantaClaus.setPosition(animsprSantaClaus.getmXFirst(), animsprSantaClaus.getmYFirst());
						
						animsprStarPine.stopAnimation();
						animsprStarPine.setCurrentTileIndex(0);
					}
				}));
			}
		}
		return false;
	}
	
	// ===========================================================
	// METHODS
	// ===========================================================
	
	//INITTIALIZATION GAME
	public void initializationGame(){
		movePlate = true;
		
		isTouchGimmic = true;
		
		isTouchCity = true;
		isTouchCat = true;
		isTouchChangeTree = true;
		isTouchSnowMan = true;
		isTouchStarPine = true;
		
		countItem = 0;
		currentPlate = 0;
	}
	
	//TIMER STAR TREE
	private void timerStarTree(){
		mScene.unregisterUpdateHandler(timerStarTree);
		timerStarTree = new TimerHandler(4.0f, true ,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				userAtention(animsprStarPine, 75, 116);
			}
		});
		mScene.registerUpdateHandler(timerStarTree);
	}
	
	//TIMER ITEM TREE
	private void timerChange(){
		mScene.unregisterUpdateHandler(timerChangeTree);
		timerChangeTree = new TimerHandler(5.0f,true ,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				userAtention(sprChangePine, sprChangePine.getWidth()/2, 0);
			}
		});
		mScene.registerUpdateHandler(timerChangeTree);
	}
	
	//ATTENTION
	private void userAtention(RectangularShape shape, float x, float y){
		shape.registerEntityModifier(new SequenceEntityModifier(
			new RotationAtModifier(0.1f, 0f, 12f, x, y),
			new RotationAtModifier(0.2f, 12f, -12f, x, y),
			new RotationAtModifier(0.1f, -12f, 0f, x, y)
//			new MoveXModifier(0.2f,shape.getX() , shape.getX() - 14),
//			new MoveXModifier(0.4f,shape.getX() - 14 , shape.getX() + 14),
//			new MoveXModifier(0.1f,shape.getX() + 14 , shape.getX() - 7),
//			new MoveXModifier(0.1f,shape.getX() - 7 , shape.getX() + 7),
//			new MoveXModifier(0.1f,shape.getX() + 7 , shape.getX())
			));
	}
	
	// ANIMATED ANIMATEDSPRITE
	private void animatedAnim(AnimatedSprite animSpr, int a,int b, int x , int y, int loop){
		int pFrames[] = { x, y };
		long pFrameDurations[] = { a, b };
		animSpr.animate(pFrameDurations, pFrames, loop, new IAnimationListener() {
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
				if(arg0 == animsprCity){
					animsprCity.setCurrentTileIndex(0);
					isTouchCity = true;
				}
				
				if(arg0 == animsprCatNoel){
					animsprCatNoel.setCurrentTileIndex(0);
					isTouchCat = true;
				}
				
				if(arg0 == animsprSnowMan){
					animsprSnowMan.setCurrentTileIndex(0);
					isTouchSnowMan = true;
				}
				
				if(arg0 == animsprBaby){
					animsprBaby.setCurrentTileIndex(0);
					animsprBaby.setVisible(false);
				}
			}
		});
	}
	
	//RANDOM SCALE
	private double randomInRange(double min,double max) {
		float randomTrue = (float) ((1-Math.random()) * (max-min) + min);
		Log.e(TAG, "TRUE" + randomTrue);
		float randomFalse = (float) (Math.random() * (max-min) + min);
		Log.e(TAG, "FALSE" + randomFalse);
		return Math.random() < 0.5 ? ((1-Math.random()) * (max-min) + min) : (Math.random() * (max-min) + min);
	}

	//DISPLAY STAR SCALE
	private void displayStar(){
		for (int i = 0; i < sprStar.length; i++) {
			sprStar[i].setScale((float) randomInRange(0.4, 1.0));
			Log.e(TAG, "randomInRange = " + sprStar[i].getScaleX());
			
			if(sprStar[i].getScaleX() > 0.4f && sprStar[i].getScaleX() < 0.7f){
				sprStar[i].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
					new ScaleAtModifier(0.5f, sprStar[i].getScaleX(), sprStar[i].getScaleX() + 0.3f, 
						sprStar[i].getWidthScaled()/2, sprStar[i].getHeightScaled()/2),
					new ScaleAtModifier(0.5f, sprStar[i].getScaleX() + 0.3f, sprStar[i].getScaleX(), 
						sprStar[i].getWidthScaled()/2, sprStar[i].getHeightScaled()/2)),-1));
			}
			
			if(sprStar[i].getScaleX() > 0.6f && sprStar[i].getScaleX() < 1.0f){
				sprStar[i].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
					new ScaleAtModifier(0.5f, sprStar[i].getScaleX(), sprStar[i].getScaleX() - 0.3f, 
						sprStar[i].getWidthScaled()/2, sprStar[i].getHeightScaled()/2),
					new ScaleAtModifier(0.5f, sprStar[i].getScaleX() - 0.3f, sprStar[i].getScaleX(), 
						sprStar[i].getWidthScaled()/2, sprStar[i].getHeightScaled()/2)),-1));
			}
		}
	}
	
	//CHANGE PLATE
	private void changePlate(){
		switch (currentPlate) {
		case 0:
			visiblePlate(sprPlate[4], sprPlate[0]);
			break;
		case 1:
			visiblePlate(sprPlate[0], sprPlate[1]);
			break;
		case 2:
			visiblePlate(sprPlate[1], sprPlate[2]);
			break;
		case 3:
			visiblePlate(sprPlate[2], sprPlate[3]);
			break;
		case 4:
			visiblePlate(sprPlate[3], sprPlate[4]);
			break;
		default:
			break;
		}
	}
	
	//VISIBLE PLATE
	private void visiblePlate(final Sprite current, final Sprite next){
		current.registerEntityModifier(new AlphaModifier(0.75f, 1.0f, 0.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				for (int i = 0; i < animsprPLATE.length; i++) {
					animsprPLATE[i].registerEntityModifier(new AlphaModifier(0.75f, 1.0f, 0.0f));
				}
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				current.setVisible(false);
				current.setPosition(960, 320);
				nextPlate(next);
			}
		}));
	}
	
	//NEXT PLATE
	private void nextPlate(final Sprite next){
		next.setVisible(true);
		next.registerEntityModifier(new ParallelEntityModifier(
			new AlphaModifier(0.75f, 0.0f, 1.0f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					for (int i = 0; i < animsprPLATE.length; i++) {
						animsprPLATE[i].registerEntityModifier(new AlphaModifier(0.75f, 0.0f, 1.0f));
					}
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					movePlate = true;
					isTouchGimmic = true;
				}
			}),
			new MoveXModifier(1.0f, 960f, 585f)));
	}
	
	//VISIBLE STAR SANTA CLAUSE
	private void visibleStar(){
		mScene.unregisterUpdateHandler(timerStar);
		timerStar = new TimerHandler(0.25f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						addStar();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerStar);
	}
		
	//ADD STAR SANTA CLAUSE
	private void addStar(){
		final Sprite _sprPoolStar = sprPoolStar.obtainPoolItem();
		arrayStar.add(_sprPoolStar);
		_sprPoolStar.detachSelf();
		mScene.attachChild(_sprPoolStar);
		
		float point[] = animsprSantaClaus.convertLocalToSceneCoordinates(250,120);
		
		_sprPoolStar.setPosition(point[0], point[1]);
		animationForStar(_sprPoolStar);
	}
	
	//ANIMATION FOR STAR SANTA CLAUS
	private void animationForStar(final Sprite sprStar){
		sprStar.clearEntityModifiers();
		sprStar.registerEntityModifier(new SequenceEntityModifier(
			new DelayModifier(0.5f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sprStar.registerEntityModifier(new AlphaModifier(0.5f, 0.7f, 1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									arrayStar.remove(sprStar);
									sprPoolStar.recyclePoolItem(sprStar);
								}
							});
						}
					}));
				}
			})));
	}
	
	//VISIBLE SNOW SCENE
	private void visibleSnow(){
		mScene.unregisterUpdateHandler(timerSnow);
		timerSnow = new TimerHandler(0.5f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						addSnow();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerSnow);
	}
			
	//ADD SNOW SCENE
	private void addSnow(){
		Random r = new Random();
		int rr = r.nextInt(3);
		switch (rr) {
		case 0:
			final Sprite _sprPoolSnowLarge = sprPoolSnowLarge.obtainPoolItem();
			arraySnowLarge.add(_sprPoolSnowLarge);
			_sprPoolSnowLarge.detachSelf();
			shapeSnow.attachChild(_sprPoolSnowLarge);
			randomPosition(_sprPoolSnowLarge);
			animationForSnow(_sprPoolSnowLarge,arraySnowLarge,sprPoolSnowLarge);
			break;
			
		case 1:
			final Sprite _sprPoolSnowMedium = sprPoolSnowMedium.obtainPoolItem();
			arraySnowMedium.add(_sprPoolSnowMedium);
			_sprPoolSnowMedium.detachSelf();
			shapeSnow.attachChild(_sprPoolSnowMedium);
			randomPosition(_sprPoolSnowMedium);
			animationForSnow(_sprPoolSnowMedium,arraySnowMedium,sprPoolSnowMedium);
			break;
			
		case 2:
			final Sprite _sprPoolSnowSmall = sprPoolSnowSmall.obtainPoolItem();
			arraySnowSmall.add(_sprPoolSnowSmall);
			_sprPoolSnowSmall.detachSelf();
			shapeSnow.attachChild(_sprPoolSnowSmall);
			randomPosition(_sprPoolSnowSmall);
			animationForSnow(_sprPoolSnowSmall,arraySnowSmall,sprPoolSnowSmall);
			break;

		default:
			break;
		}
	}
	
	//ANIMATION FOR SNOW SCENE
	private void animationForSnow(final Sprite sprSnow ,final ArrayList<Sprite> array,final SpritePool spr){
		sprSnow.clearEntityModifiers();
		sprSnow.registerEntityModifier(new ParallelEntityModifier(
			new SequenceEntityModifier(
//				new SkewModifier(0.5f, 0f, 1f, 0f, 1f),
//				new SkewModifier(0.5f, 1f, 0f, 1f, 0f),
//				new SkewModifier(0.5f, 0f, -1f, 0f, -1f),
//				new SkewModifier(0.5f, -1f, 0f, -1f, 0f),
				new LoopEntityModifier(new RotationAtModifier(6.0f, 0f, 360f, sprSnow.getWidth()/2, sprSnow.getHeight()/2), -1)
				),
			
			new MoveYModifier(10.0f, sprSnow.getY(), 700, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							array.remove(sprSnow);
							spr.recyclePoolItem(sprSnow);
						}
					});
				}
			})));
	}
		
	//RANDOM SNOW POSITION
	private void randomPosition(final Sprite snow){
		Random position = new Random();
		int positions = position.nextInt(4);
		
		Random rd = new Random();
		
		switch (positions) {
		case 0:
			int x0 = anyRandomIntRange(rd, 0, 240);
			snow.setPosition(x0, -60);
			break;
		
		case 1:
			int x1 = anyRandomIntRange(rd, 240, 480);
			snow.setPosition(x1, -60);
			break;
		
		case 2:
			int x2 = anyRandomIntRange(rd, 480, 720);
			snow.setPosition(x2, -60);
			break;
			
		case 3:
			int x3 = anyRandomIntRange(rd, 720, 910);
			snow.setPosition(x3, -60);
			break;

		default:
			break;
		}
	}
		
	//RANDOM SNOW VALUE POSITION
	private int anyRandomIntRange(Random random, int low, int high) {
		int randomInt = random.nextInt(high - low) + low;
		return randomInt;
	}
	
	//CHECK ITEM IN TREE
	private void checkItemInTree(){
		if(countItem != 0 && countItem % 10 == 0 && !animsprBaby.isVisible()){
			OGG_A80_9_HAHAHA.play();
			animsprBaby.setVisible(true);
			animatedAnim(animsprBaby, 850, 850, 0, 1, 2);
		}
	}

	// MP3 STOP
	private void mp3Stop(){
		OGG_A80_10_MOKKIN.stop();
		OGG_A80_15_KIRAN.stop();
		OGG_A80_1_2_MATI.stop();
		OGG_A80_2_NYA.stop();
		OGG_A80_5_4_KIRAKIRA.stop();
		OGG_A80_5_POMI.stop();
		OGG_A80_7_HAI.stop();
		OGG_A80_8_BOMIBOMI.stop();
		OGG_A80_9_HAHAHA.stop();
		OGG_A80_ITEM_HYUI.stop();
	}
	
	//RESET ALL
	private void resetALL(){
		mScene.unregisterUpdateHandler(timerStarTree);
		mScene.unregisterUpdateHandler(timerStar);
		mScene.unregisterUpdateHandler(timerSnow);
		mScene.unregisterUpdateHandler(timerChangeTree);
		for (int i = 0; i < sprStar.length; i++) {
			if(sprStar[i] != null ){
				sprStar[i].clearEntityModifiers();
			}
		}
		
		if(sprGimmic != null){
			sprGimmic.clearEntityModifiers();
			sprGimmic.setScale(1.0f);
		}
		
		runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
			
				for (Iterator<Sprite> iterator = arraySnowLarge.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolSnowLarge.onHandleRecycleItem(_iterator);
				}
				arraySnowLarge.clear();
				
				for (Iterator<Sprite> iterator = arraySnowMedium.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolSnowMedium.onHandleRecycleItem(_iterator);
				}
				arraySnowMedium.clear();
				
				for (Iterator<Sprite> iterator = arraySnowSmall.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolSnowSmall.onHandleRecycleItem(_iterator);
				}
				arraySnowSmall.clear();
				
				for (Iterator<Sprite> iterator = arrayStar.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolStar.onHandleRecycleItem(_iterator);
				}
				arrayStar.clear();
				
				Iterator<AnimNewItemPlate> projectiles = itemLinkedList.iterator();
				AnimNewItemPlate _projectile;
				while (projectiles.hasNext()) {
					_projectile = projectiles.next();
					_projectile.die();
				}
				_projectile = null;
				projectiles = null;
				itemLinkedList.clear();
			}
		});
			
		// City
		if(animsprCity != null){
			animsprCity.clearEntityModifiers();
			animsprCity.setCurrentTileIndex(0);
		}
		
		//Tree
		if(sprChangePine != null){
			sprChangePine.clearEntityModifiers();
			sprChangePine.setScale(1.0f);
			sprChangePine.setRotation(0.0f);
		}
		if(animsprPine != null){
			animsprPine.clearEntityModifiers();
			animsprPine.setCurrentTileIndex(0);
		}
		
		//Cat
		if(animsprCatNoel != null){
			animsprCatNoel.clearEntityModifiers();
			animsprCatNoel.stopAnimation();
			animsprCatNoel.setCurrentTileIndex(0);
			animsprCatNoel.setRotation(0.0f);
		}
		
		//Snowman
		if(animsprSnowMan != null){
			animsprSnowMan.clearEntityModifiers();
			animsprSnowMan.stopAnimation();
			animsprSnowMan.setCurrentTileIndex(0);
		}
		
		//Star Tree
		if(animsprStarPine != null){
			animsprStarPine.clearEntityModifiers();
			animsprStarPine.stopAnimation();
			animsprStarPine.setCurrentTileIndex(0);
		}
		
		//Santa
		if(animsprSantaClaus != null){
			animsprSantaClaus.clearEntityModifiers();
			animsprSantaClaus.stopAnimation();
			animsprSantaClaus.setCurrentTileIndex(0);
			animsprSantaClaus.setPosition(animsprSantaClaus.getmXFirst(), animsprSantaClaus.getmYFirst());
		}
		
		//Plate
		for (int i = 0; i < sprPlate.length; i++) {
			if (sprPlate[i] != null) {
				sprPlate[i].clearEntityModifiers();
				sprPlate[i].setVisible(false);
				sprPlate[i].setAlpha(1.0f);
				sprPlate[i].setPosition(sprPlate[i].getmXFirst(), sprPlate[i].getmYFirst());	
			}
		}
		
		//Item
		for (int i = 0; i < animsprPLATE.length; i++) {
			if(animsprPLATE[i] != null){
				animsprPLATE[i].clearEntityModifiers();
				animsprPLATE[i].setVisible(true);
				animsprPLATE[i].setAlpha(1.0f);
				animsprPLATE[i].setPosition(animsprPLATE[i].getmXFirst(),animsprPLATE[i].getmYFirst());
			}
		}
	}
		
	// ===========================================================
	// INNER AND ANONYMOUS CLASSES
	// ===========================================================
	
	//CLASS SPRITE POOL
	class SpritePool extends GenericPool<Sprite> {

		private ITextureRegion mTextureRegion;

		public SpritePool(ITextureRegion mTextureRegion) {
			if (mTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have a TextureRegion
				throw new IllegalArgumentException("The texture region must not be NULL");
			}
			this.mTextureRegion = mTextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			return new Sprite(0, 0, mTextureRegion, Vol3Kiyoshi.this.getVertexBufferObjectManager());
		}

		@Override
		protected void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.detachSelf();	
		}

		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
			pItem.reset();
		}
	}
	
	//CLASS ANIMATEDSPRITE
	class AnimSprPlate extends AnimatedSprite{
		private boolean moveItemPlate = true;
		private boolean isMoved = true;
		
		private int pIndex;
		private int pPlate;
		private int pAnimated;
		
		private int lastX;
		private int lastY;
		
		public AnimSprPlate(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager
				,int fpIndex,int fpPlate,int fpAnimated) {
			super(pX, pY, pTiledTextureRegion,vertexBufferObjectManager);
			
			this.pIndex = fpIndex;
			this.pPlate = fpPlate;
			this.pAnimated = fpAnimated;
		}
		
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float coordinatesX, final float coordinatesY) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if(pSceneTouchEvent.isActionDown()){
				lastX = (int) pSceneTouchEvent.getX();
				lastY = (int) pSceneTouchEvent.getY(); 
				
				if(moveItemPlate && this.isVisible() && Vol3Kiyoshi.this.movePlate){
					moveItemPlate = false;
					Vol3Kiyoshi.this.movePlate = false;
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							AnimSprPlate.this.detachSelf();
							AnimSprPlate.this.setPosition(pointItemScene[pIndex][0], pointItemScene[pIndex][1]);
							mScene.attachChild(AnimSprPlate.this);
						}
					});
				}
			}
			
			if(pSceneTouchEvent.isActionMove() && !moveItemPlate){
				if(Math.abs(((int)pSceneTouchEvent.getX() + (int)pSceneTouchEvent.getY()) - (lastX + lastY)) >= 10){
					isMoved = false;
					AnimSprPlate.this.setPosition(pX - AnimSprPlate.this.getWidth()/2, pY - AnimSprPlate.this.getHeight()/2);
				}
			}
			
			if(pSceneTouchEvent.isActionUp() && !moveItemPlate){
				moveItemPlate = true;
				Vol3Kiyoshi.this.movePlate = true;
				
				if(!isMoved){
					isMoved = true;
					if(checkContainsPolygon(animsprPine, arrTree, 10, pX, pY)){
						
						AnimSprPlate.this.setVisible(false);
						
						countItem++;
						
						ITiledTextureRegion ttrR = AnimSprPlate.this.getTiledTextureRegion();
						AnimNewItemPlate itemPlate = new AnimNewItemPlate(
								pX - AnimSprPlate.this.getWidth()/2, 
								pY - AnimSprPlate.this.getHeight()/2, 
								ttrR, Vol3Kiyoshi.this.getVertexBufferObjectManager(),
								this.pAnimated);
						
						animItemPlate = itemPlate;
						
						itemLinkedList.add(animItemPlate);
						itemPlate.setZIndex(0);
						mScene.attachChild(itemPlate);
						mScene.registerTouchArea(itemPlate);
						mScene.sortChildren();
						
						if(this.pAnimated == 0){
							OGG_A80_ITEM_HYUI.play();
						} else {
							OGG_A80_15_KIRAN.play();
							itemPlate.animate(new long[] { 500, 500 }, 0, 1, true);
						}
						
						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								AnimSprPlate.this.detachSelf();
								AnimSprPlate.this.setPosition(pointItemPlate[pIndex][0], pointItemPlate[pIndex][1]);
								AnimSprPlate.this.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										AnimSprPlate.this.setVisible(true);
									}
								}));
								sprPlate[pPlate].attachChild(AnimSprPlate.this);
							}
						});
						//check item in tree
						checkItemInTree();
					}
					else {
						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								AnimSprPlate.this.detachSelf();
								AnimSprPlate.this.setPosition(pointItemPlate[pIndex][0], pointItemPlate[pIndex][1]);
								sprPlate[pPlate].attachChild(AnimSprPlate.this);
							}
						});
					}
				} else {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							AnimSprPlate.this.detachSelf();
							AnimSprPlate.this.setPosition(pointItemPlate[pIndex][0], pointItemPlate[pIndex][1]);
							sprPlate[pPlate].attachChild(AnimSprPlate.this);
						}
					});
				}
			}
			return true;
		}
		
	}
	
	//CLASS ANIMATEDSPRITE
	class AnimNewItemPlate extends AnimatedSprite{
		private TimerHandler handler;
		private int animated;
		
		private int lastANIPX = 0;
		private int lastANIPY = 0;
		
		private boolean moveItemTree = true;

		private boolean moved = true;
		
		public AnimNewItemPlate(float pX, float pY, 
			ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager vertexBufferObjectManager ,int pAnimated) {
			super(pX, pY, pTiledTextureRegion,vertexBufferObjectManager);
			
			this.animated = pAnimated;
		}
		
		public void drag() {
			
		}

		public void drop() {
			if(animated == 1){
				this.animate(new long[] { 500, 500 }, 0, 1, true);
			}
		}
		
		public void dieDelay(float delayMillis) {
			this.unregisterUpdateHandler(handler);
			this.registerUpdateHandler(handler = new TimerHandler(delayMillis,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							AnimNewItemPlate.this.die();
						}
					}));
		}

		public void die() {
			handler = null;
			this.clearEntityModifiers();
			this.stopAnimation();
			this.clearUpdateHandlers();
			this.setPosition(-999, -999);
			Vol3Kiyoshi.this.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					Vol3Kiyoshi.this.mScene.detachChild(AnimNewItemPlate.this);
				}
			});

		}
		@Override
		public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			float anipX = pTouchEvent.getX();
			float anipY = pTouchEvent.getY();
			
			if (pTouchEvent.getAction() == TouchEvent.ACTION_DOWN && moveItemTree) {
				moveItemTree = false;
				
				lastANIPX = (int)anipX;
				lastANIPY = (int)anipY;
				
				this.setZIndex(4);
				Vol3Kiyoshi.this.mScene.sortChildren();
				
				if(animated == 1){
					this.stopAnimation();
				}
			}
			
			if (pTouchEvent.getAction() == TouchEvent.ACTION_MOVE && !moveItemTree) {
				if(Math.abs(((int)pTouchEvent.getX() + (int)pTouchEvent.getY()) - (lastANIPX + lastANIPY)) >= 10){
					moved = false;
					this.setPosition(anipX - this.getWidth() / 2, anipY - this.getHeight() / 2);
				}
			}
			
			if (pTouchEvent.getAction() == TouchEvent.ACTION_UP && !moveItemTree){
				moveItemTree = true;
				
				this.setZIndex(0);
				Vol3Kiyoshi.this.mScene.sortChildren();
				
				if(!moved){
					moved = true;
					if(checkContainsPolygon(animsprPine, arrTree, 10,anipX, anipY)){
						this.drop();
						this.setPosition(anipX - this.getWidth() / 2, anipY - this.getHeight() / 2);
					} else {
						countItem--;
						this.die();
						itemLinkedList.remove(this);
						//check item in tree
						checkItemInTree();
					}
				} else {
					if(animated == 1){
						this.animate(new long[] { 500, 500 }, 0, 1, true);
					}
				}
			}
			return true;
		}
	}
	
	//CUBIC BEZIER
	class RotateCubicBezierMoveModifier extends CubicBezierCurveMoveModifier {		
	    public RotateCubicBezierMoveModifier(final float pDuration, final float pX1, final float pY1, final float pX2, final float pY2, final float pX3,
	            final float pY3, final float pX4, final float pY4, final IEaseFunction pEaseFunction) {
	        super(pDuration, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, pEaseFunction);
	    }

	    @Override
	    protected void onManagedUpdate(final float pSecondsElapsed, final IEntity pEntity) {
	        final float startX = pEntity.getX();
	        final float startY = pEntity.getY();

	        super.onManagedUpdate(pSecondsElapsed, pEntity);
	       
	        final float deltaX = pEntity.getX() - startX;
	        final float deltaY = pEntity.getY() - startY;
	        
	        final float degree = MathUtils.atan2(deltaY, deltaX);
	        float angle = (float) (degree * 180 / 3.14) + 170;

	        pEntity.setRotation(angle);
	    }
	}	
}
