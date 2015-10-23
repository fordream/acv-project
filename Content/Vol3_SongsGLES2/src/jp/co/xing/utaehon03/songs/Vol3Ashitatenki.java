package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AshitatenkiResource;

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
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
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
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import android.util.Log;

public class Vol3Ashitatenki extends BaseGameActivity implements IOnSceneTouchListener{
	String TAG = "ASHITATENKI";
	//Load
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack texpackAshi1,texpackAshi2,texpackAshi3,texpackAshi4,texpackAshi5,texpackAshi6;
	private TexturePackTextureRegionLibrary texpackLibAshi1,texpackLibAshi2,texpackLibAshi3,texpackLibAshi4,texpackLibAshi6;
	
	//back ground
	private Sprite sprBackgournd;
	private Sprite sprMountain;
	private Sprite sprCity;
	private Sprite sprSun;
	
	private TextureRegion ttrBackground;
	private TextureRegion ttrMountain;
	private TextureRegion ttrCity;
	private TextureRegion ttrSun;
	
	// cloudy Mountain
	private Sprite sprCloudyMountain13;
	private Sprite sprNameCloudyMountain13;
	private Sprite sprCloudyMountain14;
	private Sprite sprNameCloudyMountain14;
	private Sprite sprCloudyMountain15;
	private Sprite sprNameCloudyMountain15;	
	
	private TextureRegion ttrCloudyMountain13;
	private TextureRegion ttrNameCloudyMountain13;
	private TextureRegion ttrCloudyMountain14;
	private TextureRegion ttrNameCloudyMountain14;
	private TextureRegion ttrCloudyMountain15;
	private TextureRegion ttrNameCloudyMountain15;
	
	//Gimmic
	private Sprite sprGimmic;
	private TextureRegion ttrGimmic;
	private int gimmic = 0;
	
	private Sprite sprPlane;
	private Sprite sprSmokePlane;
	
	private Sprite sprPlaneHeartL;
	private Sprite sprPlaneHeartR;
	private SpritePool sprPoolSmokeR1;
	private SpritePool sprPoolSmokeR2;
	private SpritePool sprPoolSmokeL1;
	private SpritePool sprPoolSmokeL2;
	
	private ArrayList<Sprite> arraySmokeL1 = new ArrayList<Sprite>();
	private ArrayList<Sprite> arraySmokeL2 = new ArrayList<Sprite>();
	private ArrayList<Sprite> arraySmokeR1 = new ArrayList<Sprite>();
	private ArrayList<Sprite> arraySmokeR2 = new ArrayList<Sprite>();
	
	
	private Sprite sprNamePlane;
	
	private TextureRegion ttrPlane;
	private TextureRegion ttrsmokePlane;
	private TextureRegion ttrsmokePlane5;
	private TextureRegion ttrNamePlane;
	
	
	//interactive
	private Sprite sprBoySurprise;
	private Sprite sprGirlSurprise;
	private Sprite sprThunderleft;
	private Sprite sprThunderright;
	
	private SpritePool sprPoolRain;
	private SpritePool sprPoolThunderRain;
	private SpritePool sprPoolWaterBoy;
	private SpritePool sprPoolWaterGirl;
	private ArrayList<Sprite> arrayRain = new ArrayList<Sprite>();
	private ArrayList<Sprite> arrayThunderRain = new ArrayList<Sprite>();
	private ArrayList<Sprite> arrayWaterBoy = new ArrayList<Sprite>();
	private ArrayList<Sprite> arrayWaterGirl = new ArrayList<Sprite>();
	
	private TextureRegion ttrBoySurprise;
	private TextureRegion ttrGirlSurprise;
	private TextureRegion ttrRainThunder;
	private TextureRegion ttrThunderleft;
	private TextureRegion ttrThunderright;
	private ITextureRegion ttrRain;
	private ITextureRegion ttrWater;
	
	//name Cloudy
	private Sprite sprNameCloudy;
	private Sprite sprNameCloudyEarthquake;
	private Sprite sprNameCloudyWind;
	private Sprite sprNameCloudyWithThunder;
	private Sprite sprNameCloudyWithRain;
	private Sprite sprNameCloudyBlack;
	private Sprite sprNameCloudyShip;
	private Sprite sprNameCloudyDonut;
	private Sprite sprNameCloudyIcecream;
	private Sprite sprNameCloudyRabbit;
	private Sprite sprNameCloudyCar;
	
	private TextureRegion ttrNameCloudy;
	private TextureRegion ttrNameCloudyEarthquake;
	private TextureRegion ttrNameCloudyWind;
	private TextureRegion ttrNameCloudyWithThunder;
	private TextureRegion ttrNameCloudyWithRain;
	private TextureRegion ttrNameCloudyBlack;
	private TextureRegion ttrNameCloudyShip;
	private TextureRegion ttrNameCloudyDonut;
	private TextureRegion ttrNameCloudyIcecream;
	private TextureRegion ttrNameCloudyRabbit;
	private TextureRegion ttrNameCloudyCar;
	
	//back ground cloudy
	private Sprite sprBackgourndThunder;
	private Sprite sprBackgourndRain;
	private Sprite sprBackgourndBlack;
	
	private TextureRegion ttrBackgourndThunder;
	private TextureRegion ttrBackgourndRain;
	private TextureRegion ttrBackgourndBlack;

	//Action Cloudy
	private AnimatedSprite animsprCloudy;
	private AnimatedSprite animsprCloudyEarthquake;
	private AnimatedSprite animsprCloudyWind;
	private AnimatedSprite animsprCloudyWithThunder;
	private AnimatedSprite animsprCloudyWithThunderF;
	private AnimatedSprite animsprCloudyWithRain[] = new AnimatedSprite[5];
	private AnimatedSprite animsprCloudyWithRainF[] = new AnimatedSprite[5];
	private AnimatedSprite animsprCloudyBlack[] = new AnimatedSprite[7];
	private AnimatedSprite animsprCloudyShip[] = new AnimatedSprite[10];
	private AnimatedSprite animsprShip[] = new AnimatedSprite[10];
	private AnimatedSprite animsprCloudyDonut;
	private AnimatedSprite animsprCloudyIcecream;
	private AnimatedSprite animsprCloudyRabbit;
	private AnimatedSprite animsprCloudyCar;
	
	private ITiledTextureRegion tttrCloudy;
	private ITiledTextureRegion tttrCloudyEarthquake;
	private ITiledTextureRegion tttrCloudyWind;
	private ITiledTextureRegion tttrCloudyWithThunder;
	private ITiledTextureRegion tttrCloudyWithThunderF;
	private ITiledTextureRegion tttrCloudyWithRain[] = new ITiledTextureRegion[5];
	private ITiledTextureRegion tttrCloudyWithRainF[] = new ITiledTextureRegion[5];
	private ITiledTextureRegion tttrCloudyBlack[] = new ITiledTextureRegion[7];
	private ITiledTextureRegion tttrCloudyShip[] = new ITiledTextureRegion[10];
	private ITiledTextureRegion tttrShip;
	private ITiledTextureRegion tttrCloudyDonut;
	private ITiledTextureRegion tttrCloudyIcecream;
	private ITiledTextureRegion tttrCloudyRabbit;
	private ITiledTextureRegion tttrCloudyCar;
	
	//Retangle shape
	private RectangularShape shapeCloudy[] = new RectangularShape[11];
	private RectangularShape shapeOne, shapeTwo;
	
	//Other
	private AnimatedSprite animsprBird;
	private AnimatedSprite animsprBoy;
	private AnimatedSprite animsprGirl;
	private AnimatedSprite animsprUmbrella;
	
	private ITiledTextureRegion tttrBird;
	private ITiledTextureRegion tttrBoy;
	private ITiledTextureRegion tttrGirl;
	private ITiledTextureRegion tttrUmbrella;

	private boolean isTouchBird; 
	private boolean isTouchMountain;
	private boolean isTouchCloudyMountain13;
	private boolean isTouchCloudyMountain14;
	private boolean isTouchCloudyMountain15;
	private boolean isTouchBoy;
	private boolean isTouchGirl;
	
	private boolean isTouchCloudy;
	private boolean isTouchCloudyWind;
	private boolean isTouchCloudyCar;
	private boolean isTouchCloudyIce;
	private boolean isTouchCloudyRabbit;
	private boolean isTouchCloudyDonut;
	private boolean isTouchCloudyBlack;
	private boolean isTouchCloudyEarthquake;
	private boolean isTouchCloudyRain;
	private boolean isTouchCloudyThunderRain;
	
	private boolean isTouchCloudyShip[] = new boolean[10];
	private boolean isSpeakShip ;
	
	private boolean isTouchUmbrella;
	
	private boolean isTouchGimmic;
	
	private boolean isTouchShapeOne;
	private boolean isTouchShapeTwo;
	
	private float arrPointCloudy[][] = {{10,490,490,250,10,10} , {0,0,170,230,170,0}};
	private float arrPointCloudyWind[][] = {{0,360,500,480,250,0,0},{0,0,130,300,300,130,0}};
	private float arrPointCloudyCar[][] = {{100,410,410,100,100},{20,20,200,200,20}};
	private float arrPointCloudyIceCream[][] = {{150,350,350,150,150},{0,0,200,200,0}};
	private float arrPointCloudyRabbit[][] = {{140,360,360,140,140},{30,30,200,200,30}};
	private float arrPointCloudyDonut[][] = {{126,375,375,126,126},{0,0,210,210,0}};
	private float arrPointCloudyEarthquake[][] = {{0,280,550,400,0,0},{0,0,350,350,110,0}};
	private float arrPointCloudyThunderRain[][] = {{0,120,360,470,390,40,0},{130,0,0,150,255,255,130}};
	private float arrPointCloudyBlack[][] = {{0,520,520,0,0},{15,15,220,220,15}};
	private float arrPointCloudyRain[][] = {{-40,250,550,550,250,-40,-40},{80,0,80,220,220,165,80}};
	
	private float arrPointMountain[][] = {
			{ 0, 105, 160, 218, 288, 335, 295, 295, 0 , 0},
			{ 273, 170, 80, 80, 170, 210, 275, 342, 342, 273}};
	
	private float pointRain[][] = {{86,0}, {404,130}, {259,42}, {-65,30}, {114,68}};
	private float pointRainF[][] = {{44,-32}, {420,142}, {250,40}, {-124,27}, {78,67}};
	private float pointBlack[][] = {{96,0}, {248,87}, {-22,45}, {178,75}, {99,2}, {375,64}, {300,0}};
	private float pointShip[][] = {{122,4}, {226,4}, {67,57}, {183,69}, {297,76}, {94,126}, {225,140}, {334,147}, {168,196}, {284,203}};
	
	private int repeatCloudy2;
	private int checkTouchUmbrella = 0;
	
	float distanceMove = 0;
	
	private Sound OGG_A76_1_3_GATA,OGG_A76_1_3_POWAN,OGG_A76_2_1_PIYO,OGG_A76_3_1_1,OGG_A76_3_1_2,OGG_A76_3_1_2_EAT,
		OGG_A76_3_4_3_KASA,OGG_A76_3_6_BITYO,OGG_A76_3_6_BITYO2,OGG_A76_3_AREHANANDA,OGG_A76_3_ARENANI,
		OGG_A76_3_KUMONANDA,OGG_A76_3_KUMONANDAG,OGG_A76_3_NANDAARE,OGG_A76_3_NANDAAREG,OGG_A76_4_1,OGG_A76_4_10,
		OGG_A76_4_11,OGG_A76_4_11_USAGI,OGG_A76_4_12,OGG_A76_4_12_KURUMA,OGG_A76_4_13,OGG_A76_4_14,OGG_A76_4_15,
		OGG_A76_4_2,OGG_A76_4_2_2_GATA,OGG_A76_4_3,OGG_A76_4_4,OGG_A76_4_4_2_3_AME,OGG_A76_4_4_2_DON,OGG_A76_4_4_2_GORO,
		OGG_A76_4_5,OGG_A76_4_5_2_6_AME,OGG_A76_4_5_2_GOGO,OGG_A76_4_6,OGG_A76_4_6_2_GOGO,OGG_A76_4_7,OGG_A76_4_7_HITSUJI,
		OGG_A76_4_8,OGG_A76_4_8_2_FLY,OGG_A76_4_8_2_FLY2,OGG_A76_4_9,OGG_A76_4_BOWAN,OGG_A76_ODOROKU1,OGG_A76_ODOROKU2,
		OGG_A76_ODOROKU3;
	
	private Sound OGG_BOY[];
	private Sound OGG_GIRL[];
	private Sound OGG_Earthquake[];
	private Sound OGG_Surprise[];
	private Sound OGG_Umbrella[];
	
	TimerHandler timerRain;
	TimerHandler timerThunder;
	TimerHandler timerWaterBoy;
	TimerHandler timerWaterGirl;
	TimerHandler timerCloudyMountain;
	TimerHandler timerCloudyShip;
	TimerHandler timerSmoke;
	//Creat Resource
	@Override
	public void onCreateResources() {
		Log.d(TAG, "Load --- onCreateResources");
		SoundFactory.setAssetBasePath("ashitatenki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("ashitatenki/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
			getTextureManager(), pAssetManager, "ashitatenki/gfx/");
		super.onCreateResources();
		Log.d(TAG, "Load --- onCreateResources --- End");
	}

	//Load Resource
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "Load --- loadKaraokeResources");
		//Library
		texpackAshi1 = mTexturePackLoaderFromSource.load("ashitatenki1.xml");
		texpackAshi1.loadTexture();
		texpackLibAshi1 = texpackAshi1.getTexturePackTextureRegionLibrary();
		
		texpackAshi2 = mTexturePackLoaderFromSource.load("ashitatenki2.xml");
		texpackAshi2.loadTexture();
		texpackLibAshi2 = texpackAshi2.getTexturePackTextureRegionLibrary();
		
		texpackAshi3 = mTexturePackLoaderFromSource.load("ashitatenki3.xml");
		texpackAshi3.loadTexture();
		texpackLibAshi3 = texpackAshi3.getTexturePackTextureRegionLibrary();
		
		texpackAshi4 = mTexturePackLoaderFromSource.load("ashitatenki4.xml");
		texpackAshi4.loadTexture();
		texpackLibAshi4 = texpackAshi4.getTexturePackTextureRegionLibrary();
		
		texpackAshi5 = mTexturePackLoaderFromSource.load("ashitatenki5.xml");
		texpackAshi5.loadTexture();
		
		texpackAshi6 = mTexturePackLoaderFromSource.load("ashitatenki6.xml");
		texpackAshi6.loadTexture();
		texpackLibAshi6 = texpackAshi6.getTexturePackTextureRegionLibrary();
		
		//Background
		ttrBackground = texpackLibAshi6.get(Vol3AshitatenkiResource.A76_1_1_IPHONE_ID);
		//Mountain
		ttrMountain = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_1_3_IPHONE_ID);
		//City
		ttrCity = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_1_4_IPHONE_ID);
		//Sun
		ttrSun = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_1_2_IPHONE_ID);
		
		//Bird
		tttrBird = getTiledTextureFromPacker(texpackAshi1, Vol3AshitatenkiResource.iAshiBird);
		
		//Cloudy Mountain
		ttrNameCloudyMountain13 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_13_1_IPHONE_ID);
		ttrCloudyMountain13 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_13_2_IPHONE_ID);
		ttrNameCloudyMountain14 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_14_1_IPHONE_ID);
		ttrCloudyMountain14 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_14_2_IPHONE_ID);
		ttrNameCloudyMountain15 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_15_1_IPHONE_ID);
		ttrCloudyMountain15 = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_15_2_IPHONE_ID);
		
		//Boy
		tttrBoy = getTiledTextureFromPacker(texpackAshi5, Vol3AshitatenkiResource.iAshiBoy);
		
		//Girl
		tttrGirl = getTiledTextureFromPacker(texpackAshi5, Vol3AshitatenkiResource.iAshiGirl);
		
		//Umbrella
		tttrUmbrella = getTiledTextureFromPacker(texpackAshi4, Vol3AshitatenkiResource.iAshiUmbrella);
		
		//BoySurprise;
		ttrBoySurprise = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_3_7_4_IPHONE_ID);
		
		//GirlSurprise;
		ttrGirlSurprise = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_3_7_3_IPHONE_ID);
		
		//NameCloudy
		ttrNameCloudy = texpackLibAshi2.get(Vol3AshitatenkiResource.A76_4_1_1_IPHONE_ID);
		
		//NameCloudyEarthquake
		ttrNameCloudyEarthquake = texpackLibAshi2.get(Vol3AshitatenkiResource.A76_4_2_1_IPHONE_ID);
		
		//NameCloudyWind
		ttrNameCloudyWind = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_3_1_IPHONE_ID);
		
		//NameCloudyWithThunder
		ttrNameCloudyWithThunder = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_4_4_1_IPHONE_ID);
		
		//NameCloudyWithRain
		ttrNameCloudyWithRain = texpackLibAshi2.get(Vol3AshitatenkiResource.A76_4_5_1_IPHONE_ID);
		
		//NameCloudyBlack
		ttrNameCloudyBlack = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_6_1_IPHONE_ID);
		
		//NameCloudyShip
		ttrNameCloudyShip = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_7_1_IPHONE_ID);
		
		//NameCloudyDonut
		ttrNameCloudyDonut = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_9_1_IPHONE_ID);
		
		//NameCloudyIcecream
		ttrNameCloudyIcecream = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_10_1_IPHONE_ID);
		
		//NameCloudyRabbit
		ttrNameCloudyRabbit = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_11_1_IPHONE_ID);
		
		//ttrNameCloudyCar
		ttrNameCloudyCar = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_12_1_IPHONE_ID);
		
		//Cloudy
		tttrCloudy = getTiledTextureFromPacker(texpackAshi2,Vol3AshitatenkiResource.A76_4_1_2_IPHONE_ID);
		
		//CloudyEarthquake
		tttrCloudyEarthquake = getTiledTextureFromPacker(texpackAshi2,Vol3AshitatenkiResource.A76_4_2_2_IPHONE_ID);
		
		//CloudyWind
		tttrCloudyWind = getTiledTextureFromPacker(texpackAshi3,Vol3AshitatenkiResource.A76_4_3_2_IPHONE_ID);
		
		//CloudyWithThunder
		tttrCloudyWithThunder = getTiledTextureFromPacker(texpackAshi1, Vol3AshitatenkiResource.A76_4_4_2_2_IPHONE_ID);
		
		//CloudyWithThunderF
		tttrCloudyWithThunderF = getTiledTextureFromPacker(texpackAshi1, Vol3AshitatenkiResource.A76_4_4_2_6_IPHONE_ID);
		
		//CloudyWithRain
		for (int i = 0; i < tttrCloudyWithRain.length; i++) {
			tttrCloudyWithRain[i] = getTiledTextureFromPacker(texpackAshi2, Vol3AshitatenkiResource.iAshiRain[i]);
		}
		
		//CloudyWithRainF
		for (int i = 0; i < tttrCloudyWithRainF.length; i++) {
			tttrCloudyWithRainF[i] = getTiledTextureFromPacker(texpackAshi2, Vol3AshitatenkiResource.iAshiRainF[i]);
		}
		
		//CloudyBlack
		for (int i = 0; i < tttrCloudyBlack.length; i++) {
			tttrCloudyBlack[i] = getTiledTextureFromPacker(texpackAshi3	, Vol3AshitatenkiResource.iAshiBlack[i]);
		}
		
		//CloudyShip
		for (int i = 0; i < tttrCloudyShip.length; i++) {
			tttrCloudyShip[i] = getTiledTextureFromPacker(texpackAshi4, Vol3AshitatenkiResource.iAshiShip[i]);
		}
		
		//Ship
		tttrShip = getTiledTextureFromPacker(texpackAshi4, Vol3AshitatenkiResource.A76_4_7_2_1_IPHONE_ID);
				//texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_7_2_1_IPHONE_ID);
		
		//CloudyDonut
		tttrCloudyDonut = getTiledTextureFromPacker(texpackAshi4, Vol3AshitatenkiResource.iAshiDonut);
		
		//CloudyIcecream
		tttrCloudyIcecream = getTiledTextureFromPacker(texpackAshi4,Vol3AshitatenkiResource.iAshiIcecream);
		
		//CloudyRabbit
		tttrCloudyRabbit = getTiledTextureFromPacker(texpackAshi3, Vol3AshitatenkiResource.iAshiRabit);
		
		//CloudyCar
		tttrCloudyCar = getTiledTextureFromPacker(texpackAshi3, Vol3AshitatenkiResource.iAshiCar);

		//BackgourndThunder
		ttrBackgourndThunder = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_4_4_2_1_IPHONE_ID);
		
		//BackgourndRain
		ttrBackgourndRain = texpackLibAshi2.get(Vol3AshitatenkiResource.A76_4_5_2_7_IPHONE_ID);
		
		//BackgroundBlack
		ttrBackgourndBlack = texpackLibAshi3.get(Vol3AshitatenkiResource.A76_4_6_2_8_IPHONE_ID);
		
		//Rain
		ttrRainThunder = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_4_4_2_3_IPHONE_ID);
		ttrRain = texpackLibAshi2.get(Vol3AshitatenkiResource.A76_4_5_2_6_IPHONE_ID);
		
		//Water
		ttrWater =texpackLibAshi1.get(Vol3AshitatenkiResource.A76_3_6_3_IPHONE_ID);
		
		//Thunder
		ttrThunderleft = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_4_4_2_5_IPHONE_ID);
		ttrThunderright = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_4_4_2_4_IPHONE_ID);
		
		//Gimmic
		ttrGimmic = texpackLibAshi1.get(Vol3AshitatenkiResource.A76_TENKI_SE3_IPHONE_ID);
		ttrPlane = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_8_2_1_IPHONE_ID);
		ttrNamePlane = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_8_1_IPHONE_ID);
		ttrsmokePlane = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_8_2_2_IPHONE_ID);
		ttrsmokePlane5 = texpackLibAshi4.get(Vol3AshitatenkiResource.A76_4_8_2_3_IPHONE_ID);
				
		Log.d(TAG, "Load --- loadKaraokeResources --- End");
	}

	//Load Sound
	@Override
	protected void loadKaraokeSound() {
		OGG_A76_1_3_GATA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_1_3_GATA);
		OGG_A76_1_3_POWAN = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_1_3_POWAN);
		OGG_A76_2_1_PIYO = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_2_1_PIYO);
		OGG_A76_3_1_1 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_1_1);
		OGG_A76_3_1_2 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_1_2);
		OGG_A76_3_1_2_EAT = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_1_2_EAT);
		OGG_A76_3_4_3_KASA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_4_3_KASA);
		OGG_A76_3_6_BITYO = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_6_BITYO);
		OGG_A76_3_6_BITYO2 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_6_BITYO2);
		OGG_A76_3_AREHANANDA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_AREHANANDA);
		OGG_A76_3_ARENANI = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_ARENANI);
		OGG_A76_3_KUMONANDA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_KUMONANDA);
		OGG_A76_3_KUMONANDAG = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_KUMONANDAG);
		OGG_A76_3_NANDAARE = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_NANDAARE);
		OGG_A76_3_NANDAAREG = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_3_NANDAAREG);
		OGG_A76_4_1 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_1);
		OGG_A76_4_10 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_10);
		OGG_A76_4_11 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_11);
		OGG_A76_4_11_USAGI = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_11_USAGI);
		OGG_A76_4_12 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_12);
		OGG_A76_4_12_KURUMA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_12_KURUMA);
		OGG_A76_4_13 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_13);
		OGG_A76_4_14 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_14);
		OGG_A76_4_15 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_15);
		OGG_A76_4_2 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_2);
		OGG_A76_4_2_2_GATA = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_2_2_GATA);
		OGG_A76_4_3 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_3);
		OGG_A76_4_4 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_4);
		OGG_A76_4_4_2_3_AME = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_4_2_3_AME);
		OGG_A76_4_4_2_DON = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_4_2_DON);
		OGG_A76_4_4_2_GORO = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_4_2_GORO);
		OGG_A76_4_5 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_5);
		OGG_A76_4_5_2_6_AME = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_5_2_6_AME);
		OGG_A76_4_5_2_GOGO = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_5_2_GOGO);
		OGG_A76_4_6 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_6);
		OGG_A76_4_6_2_GOGO = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_6_2_GOGO);
		OGG_A76_4_7 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_7);
		OGG_A76_4_7_HITSUJI = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_7_HITSUJI);
		OGG_A76_4_8 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_8);
		OGG_A76_4_8_2_FLY = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_8_2_FLY);
		OGG_A76_4_8_2_FLY2 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_8_2_FLY2);
		OGG_A76_4_9 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_9);
		OGG_A76_4_BOWAN = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_4_BOWAN);
		OGG_A76_ODOROKU1 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_ODOROKU1);
		OGG_A76_ODOROKU2 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_ODOROKU2);
		OGG_A76_ODOROKU3 = loadSoundResourceFromSD(Vol3AshitatenkiResource.A76_ODOROKU3);
		
		OGG_BOY = new Sound[]{OGG_A76_3_AREHANANDA, OGG_A76_3_KUMONANDA, OGG_A76_3_NANDAARE};
		OGG_GIRL = new Sound[]{OGG_A76_3_ARENANI, OGG_A76_3_KUMONANDAG, OGG_A76_3_NANDAAREG};
		OGG_Earthquake = new Sound[]{OGG_A76_ODOROKU1,OGG_A76_ODOROKU2};
		OGG_Surprise = new Sound[]{OGG_A76_ODOROKU1,OGG_A76_ODOROKU2,OGG_A76_ODOROKU3};
		OGG_Umbrella = new Sound[]{OGG_A76_3_6_BITYO,OGG_A76_3_6_BITYO2};
	}

	//Load Scene
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "Load --- loadKaraokeScene");
		mScene = new Scene();
		
		//Background
		sprBackgournd = new Sprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackgournd);
		
		//Sun
		sprSun = new Sprite(152, 79, ttrSun, this.getVertexBufferObjectManager());
		mScene.attachChild(sprSun);
		
		//BackgourndThunder
		sprBackgourndThunder = new Sprite(0, 0, ttrBackgourndThunder, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackgourndThunder);
		
		//BackgourndRain
		sprBackgourndRain = new Sprite(0, 0, ttrBackgourndRain, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackgourndRain);
		
		//BackgourndBlack
		sprBackgourndBlack = new Sprite(0, 0, ttrBackgourndBlack, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackgourndBlack);
		
		//Mountain
		sprMountain = new Sprite(0, 296, ttrMountain, this.getVertexBufferObjectManager());
		mScene.attachChild(sprMountain);
		
		//City
		sprCity = new Sprite(0, 504, ttrCity, this.getVertexBufferObjectManager());
		mScene.attachChild(sprCity);
		
		//Cloudy Sky
		shapeOne = new Rectangle(230, 0, 500, 300, this.getVertexBufferObjectManager());
		mScene.attachChild(shapeOne);
		shapeOne.setVisible(false);
		shapeOne.setAlpha(0.0f);
		
		shapeTwo = new Rectangle(230, 0, 500, 300, this.getVertexBufferObjectManager());
		mScene.attachChild(shapeTwo);
		shapeTwo.setVisible(false);
		shapeTwo.setAlpha(0.0f);
		
		for (int i = 0; i < shapeCloudy.length; i++) {
			shapeCloudy[i] = new Rectangle(0, 0, 500, 300, this.getVertexBufferObjectManager());
			if(i % 2 == 0){
				shapeOne.attachChild(shapeCloudy[i]);
			}
			else {
				shapeTwo.attachChild(shapeCloudy[i]);
			}
			
			shapeCloudy[i].setAlpha(0.0f);
			shapeCloudy[i].setVisible(false);
		}
		
		animsprCloudy = new AnimatedSprite(4, -34, tttrCloudy, this.getVertexBufferObjectManager());
		shapeCloudy[0].attachChild(animsprCloudy);
		
		animsprCloudyEarthquake = new AnimatedSprite(-24, -76, tttrCloudyEarthquake, this.getVertexBufferObjectManager());
		shapeCloudy[1].attachChild(animsprCloudyEarthquake);
		
		animsprCloudyWind = new AnimatedSprite(9, 2, tttrCloudyWind, this.getVertexBufferObjectManager());
		shapeCloudy[2].attachChild(animsprCloudyWind);
		
		animsprCloudyWithThunderF = new AnimatedSprite(-113, -43, tttrCloudyWithThunder, this.getVertexBufferObjectManager());
		shapeCloudy[3].attachChild(animsprCloudyWithThunderF);
		
		animsprCloudyWithThunder = new AnimatedSprite(4, -36, tttrCloudyWithThunderF, this.getVertexBufferObjectManager());
		shapeCloudy[3].attachChild(animsprCloudyWithThunder);
		animsprCloudyWithThunderF.setVisible(false);
		
		for (int i = 0; i < animsprCloudyWithRain.length; i++) {
			animsprCloudyWithRain[i] = new AnimatedSprite(pointRain[i][0], pointRain[i][1], tttrCloudyWithRain[i], this.getVertexBufferObjectManager());
			shapeCloudy[4].attachChild(animsprCloudyWithRain[i]);
		}
		
		for (int i = 0; i < animsprCloudyWithRainF.length; i++) {
			animsprCloudyWithRainF[i] = new AnimatedSprite(pointRainF[i][0], pointRainF[i][1], tttrCloudyWithRainF[i], this.getVertexBufferObjectManager());
			shapeCloudy[4].attachChild(animsprCloudyWithRainF[i]);
			animsprCloudyWithRainF[i].setVisible(false);
		}
		
		for (int i = 0; i < animsprCloudyBlack.length; i++) {
			animsprCloudyBlack[i] = new AnimatedSprite(pointBlack[i][0], pointBlack[i][1], tttrCloudyBlack[i], this.getVertexBufferObjectManager());
			shapeCloudy[5].attachChild(animsprCloudyBlack[i]);
		}
		
		for (int i = 0; i < animsprCloudyShip.length; i++) {
			animsprCloudyShip[i] = new AnimatedSprite(pointShip[i][0], pointShip[i][1], tttrCloudyShip[i], this.getVertexBufferObjectManager());
			shapeCloudy[6].attachChild(animsprCloudyShip[i]);
		}
		
		for (int i = 0; i < animsprShip.length; i++) {
			animsprShip[i] = new AnimatedSprite(0, 0, tttrShip, this.getVertexBufferObjectManager());
			shapeCloudy[6].attachChild(animsprShip[i]);
			animsprShip[i].setVisible(false);
		}
		
		animsprCloudyDonut = new AnimatedSprite(126, 0, tttrCloudyDonut, this.getVertexBufferObjectManager());
		shapeCloudy[7].attachChild(animsprCloudyDonut);
		
		animsprCloudyIcecream = new AnimatedSprite(151, 1, tttrCloudyIcecream, this.getVertexBufferObjectManager());
		shapeCloudy[8].attachChild(animsprCloudyIcecream);
		
		animsprCloudyRabbit = new AnimatedSprite(123, 14, tttrCloudyRabbit, this.getVertexBufferObjectManager());
		shapeCloudy[9].attachChild(animsprCloudyRabbit);
		
		animsprCloudyCar = new AnimatedSprite(96, 0, tttrCloudyCar, this.getVertexBufferObjectManager());
		shapeCloudy[10].attachChild(animsprCloudyCar);
		
		//Cloudy Mountain
		sprCloudyMountain13 = new Sprite(25, 267, ttrCloudyMountain13, this.getVertexBufferObjectManager());
		mScene.attachChild(sprCloudyMountain13);
		
		sprCloudyMountain14 = new Sprite(60, 270, ttrCloudyMountain14, this.getVertexBufferObjectManager());
		mScene.attachChild(sprCloudyMountain14);
		
		sprCloudyMountain15 = new Sprite(60, 270, ttrCloudyMountain15, this.getVertexBufferObjectManager());
		mScene.attachChild(sprCloudyMountain15);
		
		/*
		 * Visible Cloudy Mountain
		 */
		setVisibleSprite(false, sprCloudyMountain13,sprCloudyMountain14,sprCloudyMountain15);
		
		//Thunder
		sprThunderleft = new Sprite(77, 144, ttrThunderleft, this.getVertexBufferObjectManager());
		mScene.attachChild(sprThunderleft);
		
		sprThunderright = new Sprite(612, 164, ttrThunderright, this.getVertexBufferObjectManager());
		mScene.attachChild(sprThunderright);
		
		//Rain
		
		/*
		 * Visible Thunder - Rain
		 */
		setVisibleSprite(false, sprThunderleft, sprThunderright);
		
		//Umbrella
		animsprUmbrella = new AnimatedSprite(267, 237, tttrUmbrella, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprUmbrella);
		animsprUmbrella.setVisible(false);
		
		//Name cloudy Mountain
		sprNameCloudyMountain13 = new Sprite(83, 362, ttrNameCloudyMountain13, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyMountain13);
		
		sprNameCloudyMountain14 = new Sprite(83, 362, ttrNameCloudyMountain14, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyMountain14);
		
		sprNameCloudyMountain15 = new Sprite(83, 362, ttrNameCloudyMountain15, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyMountain15);
		
		//Name cloudy 
		sprNameCloudy = new Sprite(184, 235, ttrNameCloudy, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudy);
		
		sprNameCloudyEarthquake = new Sprite(184, 235, ttrNameCloudyEarthquake, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyEarthquake);
		
		sprNameCloudyWind = new Sprite(184, 235, ttrNameCloudyWind, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyWind);
		
		sprNameCloudyWithThunder = new Sprite(184, 235, ttrNameCloudyWithThunder, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyWithThunder);
		
		sprNameCloudyWithRain = new Sprite(184, 235, ttrNameCloudyWithRain, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyWithRain);
		
		sprNameCloudyBlack = new Sprite(184, 235, ttrNameCloudyBlack, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyBlack);
		
		sprNameCloudyShip = new Sprite(184, 255, ttrNameCloudyShip, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyShip);
		
		sprNameCloudyDonut = new Sprite(184, 235, ttrNameCloudyDonut, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyDonut);
		
		sprNameCloudyIcecream = new Sprite(184, 235, ttrNameCloudyIcecream, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyIcecream);
		
		sprNameCloudyRabbit = new Sprite(184, 235, ttrNameCloudyRabbit, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyRabbit);
		
		sprNameCloudyCar = new Sprite(184, 235, ttrNameCloudyCar, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNameCloudyCar);
		
		/*
		 * Visible Name Cloudy
		 */
		setVisibleSprite(false, sprNameCloudyMountain13,sprNameCloudyMountain14,sprNameCloudyMountain15,sprNameCloudy,
			sprNameCloudyEarthquake,sprNameCloudyWind,sprNameCloudyWithThunder,sprNameCloudyWithRain,sprNameCloudyBlack,
			sprNameCloudyShip,sprNameCloudyDonut,sprNameCloudyIcecream,sprNameCloudyRabbit,sprNameCloudyCar);

		//Bird
		animsprBird = new AnimatedSprite(835, 425, tttrBird, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBird);
		
		//Gimmic plane
		sprPlane = new Sprite(960, 500, ttrPlane, this.getVertexBufferObjectManager());
		mScene.attachChild(sprPlane);
		
		sprSmokePlane = new Sprite(0, -10, ttrsmokePlane, this.getVertexBufferObjectManager());
		sprPlane.attachChild(sprSmokePlane);
		
		sprPlaneHeartR = new Sprite(480, 150, ttrPlane, this.getVertexBufferObjectManager());
		mScene.attachChild(sprPlaneHeartR);
		sprPlaneHeartR.setFlippedVertical(true);
		sprPlaneHeartR.setVisible(false);
		
		sprPlaneHeartL = new Sprite(480, 150, ttrPlane, this.getVertexBufferObjectManager());
		mScene.attachChild(sprPlaneHeartL);
		sprPlaneHeartL.setVisible(false);
		
		sprNamePlane = new Sprite(184, 230, ttrNamePlane, this.getVertexBufferObjectManager());
		mScene.attachChild(sprNamePlane);
		sprNamePlane.setVisible(false);
		
		//Girl
		animsprGirl = new AnimatedSprite(469, 350, tttrGirl, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprGirl);
				
		//Boy
		animsprBoy = new AnimatedSprite(260, 333, tttrBoy, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBoy);
		//GirlSurprise
		sprGirlSurprise = new Sprite(615, 333, ttrGirlSurprise, this.getVertexBufferObjectManager());
		mScene.attachChild(sprGirlSurprise);
		
		//BoySurprise
		sprBoySurprise = new Sprite(263, 333, ttrBoySurprise, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBoySurprise);
		
		/*
		 * Visible ground - surprise
		 */
		setVisibleSprite(false, sprGirlSurprise,sprBoySurprise,sprBackgourndThunder,sprBackgourndRain,sprBackgourndBlack);
		
		//Gimmic
		sprGimmic = new Sprite(650, 496, ttrGimmic, this.getVertexBufferObjectManager());
		mScene.attachChild(sprGimmic);
		
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "Load --- loadKaraokeScene --- End");
	}

	//Gimmic
	@Override
	public void combineGimmic3WithAction() {
		
	}
	
	//Resume game
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, " --- onResumeGame");
		inital();
		
		sprPoolRain = new SpritePool(ttrRain);
		sprPoolThunderRain = new SpritePool(ttrRainThunder);
		sprPoolWaterBoy = new SpritePool(ttrWater);
		sprPoolWaterGirl = new SpritePool(ttrWater);
		
		sprPoolSmokeL1 = new SpritePool(ttrsmokePlane5);
		sprPoolSmokeL2 = new SpritePool(ttrsmokePlane5);
		sprPoolSmokeR1 = new SpritePool(ttrsmokePlane5);
		sprPoolSmokeR2 = new SpritePool(ttrsmokePlane5);
		
		shapeOne.setPosition(1000, shapeOne.getmYFirst());
		shapeTwo.setPosition(1770, shapeTwo.getmYFirst());
		randomVisible1();
		randomVisible2();
		
		moveCloudy1();
		moveCloudy2();
		
		appearCloudyMountain();
		super.onResumeGame();
	}
	
	//Pause game
	@Override
	public synchronized void onPauseGame() {
		mp3Stop();
		reset();
		super.onPauseGame();
	}

	//On Scene Touch Listener
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		//Action Down
		if (pSceneTouchEvent.isActionDown()) {
			//Touch Bird
			if(checkContains(animsprBird, 0, 40, 75, 102, pX, pY) && isTouchBird){
				OGG_A76_2_1_PIYO.play();
				isTouchBird = false;
				animsprBird.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(0.5f, animsprBird.getmYFirst(), animsprBird.getmYFirst() - 25),
					new MoveYModifier(0.5f, animsprBird.getmYFirst() - 25, animsprBird.getmYFirst()),
					new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchBird = true;
						}
					}))
				);
			}
			
			//Touch Mountain
			if(checkContainsPolygon(sprMountain, arrPointMountain, 9, pX, pY) && isTouchMountain){
				OGG_A76_1_3_GATA.play();
				isTouchMountain = false;
				sprMountain.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.2f, sprMountain.getmXFirst(), sprMountain.getmXFirst() + 20),
					new MoveXModifier(0.4f, sprMountain.getmXFirst() + 20, sprMountain.getmXFirst() - 20),
					new MoveXModifier(0.3f, sprMountain.getmXFirst() - 20, sprMountain.getmXFirst() + 10),
					new MoveXModifier(0.2f, sprMountain.getmXFirst() + 10, sprMountain.getmXFirst() - 10),
					new MoveXModifier(0.15f, sprMountain.getmXFirst() - 10, sprMountain.getmXFirst() + 5),
					new MoveXModifier(0.1f, sprMountain.getmXFirst() + 5, sprMountain.getmXFirst() - 5),
					new MoveXModifier(0.05f, sprMountain.getmXFirst() - 5, sprMountain.getmXFirst()),
					new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchMountain = true;
						}
					}
				)));
			}
			
			//Touch Cloudy Mountain 13
			if(checkContains(sprCloudyMountain13, 25, 25, 295, 90, pX, pY) && isTouchCloudyMountain13 && sprCloudyMountain13.isVisible()){
				OGG_A76_4_BOWAN.play();
				playSoundDelay(OGG_A76_4_13, 0.5f);
				touchCloudyMountain(sprCloudyMountain13, sprCloudyMountain14, sprNameCloudyMountain13);
			}
			
			//Touch Cloudy Mountain 14
			if(checkContains(sprCloudyMountain14, 22, 24, 216, 100, pX, pY) && isTouchCloudyMountain14 && sprCloudyMountain14.isVisible()){
				OGG_A76_4_BOWAN.play();
				playSoundDelay(OGG_A76_4_14, 0.5f);
				touchCloudyMountain(sprCloudyMountain14, sprCloudyMountain15, sprNameCloudyMountain14);
			}
			
			//Touch Cloudy Mountain 15
			if(checkContains(sprCloudyMountain15, 12, 17, 245, 90, pX, pY) && isTouchCloudyMountain15 && sprCloudyMountain15.isVisible()){
				OGG_A76_4_BOWAN.play();
				playSoundDelay(OGG_A76_4_15, 0.5f);
				touchCloudyMountain(sprCloudyMountain15, sprCloudyMountain13, sprNameCloudyMountain15);
			}
			
			//Touch Boy
			if(checkContains(animsprBoy, 50, 90, 200, 400, pX, pY) && isTouchBoy){
				Random oggboy = new Random();
				int oggboys = oggboy.nextInt(3);
				OGG_BOY[oggboys].play();
				isTouchBoy = false;
				animsprBoy.setCurrentTileIndex(1);
				animsprBoy.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchBoy = true;
						animsprBoy.setCurrentTileIndex(0);
					}
				}));
			}
			
			//Touch Girl
			if(checkContains(animsprGirl, 60, 70, 190, 350, pX, pY) && isTouchGirl){
				Random ogggirl = new Random();
				int ogggirls = ogggirl.nextInt(3);
				OGG_GIRL[ogggirls].play();
				isTouchGirl = false;
				animsprGirl.setCurrentTileIndex(1);
				animsprGirl.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchGirl = true;
						animsprGirl.setCurrentTileIndex(0);
					}
				}));
			}
			
			//Touch Umbrella
			if(checkContains(animsprUmbrella, 198, 0, 262, 403, pX, pY) && isTouchUmbrella && animsprUmbrella.isVisible()){
				isTouchUmbrella = false;
				OGG_A76_3_4_3_KASA.play();
				
				animsprUmbrella.clearEntityModifiers();
				animsprUmbrella.setRotation(0);
				animsprUmbrella.setCurrentTileIndex(1);
				animsprBoy.setCurrentTileIndex(4);
				animsprGirl.setCurrentTileIndex(4);
			}
			
			// Switch - case Touch Cloudy Sky
			
			//Cloudy - 0
			if(checkContainsPolygon(shapeOne, arrPointCloudy, 5, pX, pY) && isTouchCloudy && isTouchShapeOne && shapeCloudy[0].isVisible()){
				isTouchCloudy = false;
				isTouchGimmic = false;
				isTouchShapeTwo = false;
				isTouchCloudySkyOne(0);
			}
			
			//Cloudy Wind - 2
			if(checkContainsPolygon(shapeOne, arrPointCloudyWind, 6, pX, pY) && isTouchCloudyWind && isTouchShapeOne && shapeCloudy[2].isVisible()){
				isTouchCloudyWind = false;
				isTouchGimmic = false;
				isTouchShapeTwo = false;
				isTouchCloudySkyOne(2);
			}
			
			//Cloudy Rain - 4
			if(checkContainsPolygon(shapeOne, arrPointCloudyRain, 6, pX, pY) && isTouchCloudyRain && isTouchShapeOne && shapeCloudy[4].isVisible()){
				isTouchCloudyRain = false;
				isTouchGimmic = false;
				isTouchShapeTwo = false;
				isTouchCloudySkyOne(4);
			}
			
			//Cloudy Ship - 6
			if(animsprCloudyShip[0].contains(pX, pY) && isTouchCloudyShip[0] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(0);
			}
			
			if(animsprCloudyShip[1].contains(pX, pY) && isTouchCloudyShip[1] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(1);
			}
			
			if(animsprCloudyShip[2].contains(pX, pY) && isTouchCloudyShip[2] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(2);
			}
			
			if(animsprCloudyShip[3].contains(pX, pY) && isTouchCloudyShip[3] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(3);
			}
			
			if(animsprCloudyShip[4].contains(pX, pY) && isTouchCloudyShip[4] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(4);
			}
			
			if(animsprCloudyShip[5].contains(pX, pY) && isTouchCloudyShip[5] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(5);
			}
			
			if(animsprCloudyShip[6].contains(pX, pY) && isTouchCloudyShip[6] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(6);
			}
			
			if(animsprCloudyShip[7].contains(pX, pY) && isTouchCloudyShip[7] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(7);
			}
			
			if(animsprCloudyShip[8].contains(pX, pY) && isTouchCloudyShip[8] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(8);
			}
			
			if(animsprCloudyShip[9].contains(pX, pY) && isTouchCloudyShip[9] && isTouchShapeOne && shapeCloudy[6].isVisible()){
				touchCloudyShip(9);
			}
			
			//Cloudy Ice cream - 8
			if(checkContainsPolygon(shapeOne, arrPointCloudyIceCream, 4, pX, pY) && isTouchCloudyIce && isTouchShapeOne && shapeCloudy[8].isVisible()){
				isTouchCloudyIce = false;
				isTouchGimmic = false;
				isTouchShapeTwo = false;
				isTouchCloudySkyOne(8);
			}
			
			//Cloudy Car - 10
			if(checkContainsPolygon(shapeOne, arrPointCloudyCar, 4, pX, pY) && isTouchCloudyCar && isTouchShapeOne && shapeCloudy[10].isVisible()){
				isTouchCloudyCar = false;
				isTouchGimmic = false;
				isTouchShapeTwo = false;
				isTouchCloudySkyOne(10);
			}
			
			//Cloudy Rabbit - 9
			if(checkContainsPolygon(shapeTwo, arrPointCloudyRabbit, 4, pX, pY) && isTouchCloudyRabbit && isTouchShapeTwo && shapeCloudy[9].isVisible()){
				isTouchCloudyRabbit = false;
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchCloudySkyTwo(9);
			}
			
			//Cloudy Donut - 7
			if(checkContainsPolygon(shapeTwo, arrPointCloudyDonut, 4, pX, pY) && isTouchCloudyDonut && isTouchShapeTwo && shapeCloudy[7].isVisible()){
				isTouchCloudyDonut = false;
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchCloudySkyTwo(7);
			}
			
			//Cloudy Donut - 5
			if(checkContainsPolygon(shapeTwo, arrPointCloudyBlack, 4, pX, pY) && isTouchCloudyBlack && isTouchShapeTwo && shapeCloudy[5].isVisible()){
				isTouchCloudyBlack = false;
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchCloudySkyTwo(5);
			}
			
			//Cloudy Thunder Rain - 3 
			if(checkContainsPolygon(shapeTwo, arrPointCloudyThunderRain, 6, pX, pY) && isTouchCloudyThunderRain && isTouchShapeTwo && shapeCloudy[3].isVisible()){
				isTouchCloudyThunderRain = false;
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchCloudySkyTwo(3);
			}
			
			//Cloudy Earthquake - 1
			if(checkContainsPolygon(shapeTwo, arrPointCloudyEarthquake, 5, pX, pY) && isTouchCloudyEarthquake && isTouchShapeTwo && shapeCloudy[1].isVisible()){
				isTouchCloudyEarthquake = false;
				OGG_A76_2_1_PIYO.stop();
				animsprBird.clearEntityModifiers();
				animsprBird.setPosition(animsprBird.getmXFirst(),animsprBird.getmYFirst());
				isTouchBird = false;
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchCloudySkyTwo(1);
			}
			
			//Gimmic
			if(sprGimmic.contains(pX, pY) && isTouchGimmic){
				isTouchGimmic = false;
				isTouchShapeOne = false;
				isTouchShapeTwo = false;
				OGG_A76_4_8.play();
				sprNamePlane.setVisible(true);
				soundOff(OGG_A76_3_AREHANANDA, OGG_A76_3_KUMONANDA, OGG_A76_3_NANDAARE,OGG_A76_3_ARENANI, OGG_A76_3_KUMONANDAG, OGG_A76_3_NANDAAREG);
				
				isTouchBoy = false;
				isTouchGirl = false;
				
				animsprBoy.clearEntityModifiers();
				animsprGirl.clearEntityModifiers();
				animsprBoy.setCurrentTileIndex(1);
				animsprGirl.setCurrentTileIndex(1);
				
				shapeOne.clearEntityModifiers();
				shapeTwo.clearEntityModifiers();
				
				gimmic++;
				if(gimmic > 5){
					gimmic = 1;
				}
				
				for (int i = 0; i < shapeCloudy.length; i++) {
					shapeCloudy[i].setVisible(false);
				}
				shapeOne.setPosition(1000, shapeOne.getmYFirst());
				shapeTwo.setPosition(1770, shapeTwo.getmYFirst());
				randomVisible1();
				randomVisible2();
				
				actionGimmic();
			}
			
		}
		return false;
	}
	
	//Gimmic
	private void actionGimmic(){
		if(gimmic == 1 || gimmic == 3){
			OGG_A76_4_8_2_FLY.play();
			
			sprPlane.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(2.0f, sprPlane.getX(), -230, sprPlane.getY(), -300, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchGimmic = true;
						isTouchShapeOne = true;
						isTouchShapeTwo = true;
						isTouchBoy = true;
						isTouchGirl = true;
						sprNamePlane.setVisible(false);
						animsprBoy.setCurrentTileIndex(0);
						animsprGirl.setCurrentTileIndex(0);
						moveCloudy1();
						moveCloudy2();
						sprPlane.setPosition(960, 100);
						sprPlane.setRotation(-36);
					}
				})
			));
		}
		else if(gimmic == 2 || gimmic == 4){
			OGG_A76_4_8_2_FLY.play();
			sprPlane.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(2.0f, sprPlane.getX(), -490, sprPlane.getY(), sprPlane.getY(), new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchGimmic = true;
						isTouchShapeOne = true;
						isTouchShapeTwo = true;
						isTouchBoy = true;
						isTouchGirl = true;
						sprNamePlane.setVisible(false);
						animsprBoy.setCurrentTileIndex(0);
						animsprGirl.setCurrentTileIndex(0);
						moveCloudy1();
						moveCloudy2();
						sprPlane.setPosition(960, 500);
						sprPlane.setRotation(0);
					}
				})
			));
		}
		else if(gimmic == 5){
			OGG_A76_4_8_2_FLY2.play();
			sprPlaneHeartR.setVisible(true);
			sprPlaneHeartL.setVisible(true);
			visibleSmoke();
			addSmoke();
			sprPlaneHeartR.registerEntityModifier(
				new RotateCubicBezierMoveModifierR(3.0f, 480, 150, 850, -100, 850, 300, 350, 600,EaseLinear.getInstance()));
			sprPlaneHeartL.registerEntityModifier(
				new RotateCubicBezierMoveModifierL(3.0f, 480, 150, 110, -100, 110, 300, 610, 600,EaseLinear.getInstance()));
			
			sprNamePlane.registerEntityModifier(new DelayModifier(6.5f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sprPlaneHeartR.setPosition(sprPlaneHeartR.getmXFirst(), sprPlaneHeartR.getmYFirst());
					sprPlaneHeartL.setPosition(sprPlaneHeartL.getmXFirst(), sprPlaneHeartL.getmYFirst());
					isTouchGimmic = true;
					isTouchShapeOne = true;
					isTouchShapeTwo = true;
					isTouchBoy = true;
					isTouchGirl = true;
					sprNamePlane.setVisible(false);
					sprPlaneHeartR.setVisible(false);
					sprPlaneHeartL.setVisible(false);
					animsprBoy.setCurrentTileIndex(0);
					animsprGirl.setCurrentTileIndex(0);
					moveCloudy1();
					moveCloudy2();
				}
			}));
			
		}
	}
	
	//Visible Smoke
	private void visibleSmoke(){
			mScene.unregisterUpdateHandler(timerSmoke);
			timerSmoke = new TimerHandler(0.1f, true, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							addSmoke();
						}
					});
				}
			});
			mScene.registerUpdateHandler(timerSmoke);
	}
	
	//ADD SMOKE
	private void addSmoke(){
		final Sprite poolSmokeL1 = sprPoolSmokeL1.obtainPoolItem();
		final Sprite poolSmokeL2 = sprPoolSmokeL2.obtainPoolItem();
		
		final Sprite poolSmokeR1 = sprPoolSmokeR1.obtainPoolItem();
		final Sprite poolSmokeR2 = sprPoolSmokeR2.obtainPoolItem();
		
		arraySmokeL1.add(poolSmokeL1);
		arraySmokeL2.add(poolSmokeL2);
		
		arraySmokeR1.add(poolSmokeR1);
		arraySmokeR2.add(poolSmokeR2);
		
		poolSmokeL1.detachSelf();
		poolSmokeL2.detachSelf();
		poolSmokeR1.detachSelf();
		poolSmokeR2.detachSelf();
		
		poolSmokeL1.setAlpha(0.7f);
		poolSmokeL2.setAlpha(0.7f);
		poolSmokeR1.setAlpha(0.7f);
		poolSmokeR2.setAlpha(0.7f);
		
		mScene.attachChild(poolSmokeL1);
		mScene.attachChild(poolSmokeL2);
		mScene.attachChild(poolSmokeR1);
		mScene.attachChild(poolSmokeR2);
		
		animsprGirl.setZIndex(1);
		animsprBoy.setZIndex(1);
		sprGimmic.setZIndex(1);
		mScene.sortChildren();
		
		poolSmokeL1.setPosition(sprPlaneHeartL.getX() - poolSmokeL1.getWidth()/2, sprPlaneHeartL.getY()- poolSmokeL1.getHeight()/2 - 20);
		poolSmokeL2.setPosition(sprPlaneHeartL.getX() - poolSmokeL1.getWidth()/2, sprPlaneHeartL.getY()- poolSmokeL1.getHeight()/2 + 20);
		
		poolSmokeR1.setPosition(sprPlaneHeartR.getX() - poolSmokeR1.getWidth()/2, sprPlaneHeartR.getY()- poolSmokeR1.getHeight()/2 - 20);
		poolSmokeR2.setPosition(sprPlaneHeartR.getX() - poolSmokeR1.getWidth()/2, sprPlaneHeartR.getY()- poolSmokeR1.getHeight()/2 + 20);
		
		animationForSmoke(poolSmokeL1,arraySmokeL1,sprPoolSmokeL1);
		animationForSmoke(poolSmokeL2,arraySmokeL2,sprPoolSmokeL2);
		animationForSmoke(poolSmokeR1,arraySmokeR1,sprPoolSmokeR1);
		animationForSmoke(poolSmokeR2,arraySmokeR2,sprPoolSmokeR2);
	}
	
	//animationForSmoke
	private void animationForSmoke(final Sprite smoke, final ArrayList<Sprite> array,final SpritePool sprPool){
		smoke.clearEntityModifiers();
		smoke.registerEntityModifier(new SequenceEntityModifier(
			new DelayModifier(2.8f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mScene.unregisterUpdateHandler(timerSmoke);
					smoke.registerEntityModifier(new AlphaModifier(0.5f, 0.7f, 1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									array.remove(smoke);
									sprPool.recyclePoolItem(smoke);
								}
							});
						}
					}));
				}
			})));
			
			
	}
	//============================
	//Method
	//============================
	
	//Creat 
	private void inital() {
		repeatCloudy2 = 0;
		checkTouchUmbrella = 0;
		isTouchBird = true;
		isTouchMountain = true;
		isTouchCloudyMountain13 = true;
		isTouchCloudyMountain14 = true;
		isTouchCloudyMountain15 = true;
		isTouchBoy = true;
		isTouchGirl = true;
		
		isTouchCloudy = true;
		isTouchCloudyWind = true;
		isTouchCloudyCar = true;
		isTouchCloudyIce = true;
		isTouchCloudyRabbit = true;
		isTouchCloudyDonut = true;
		isTouchCloudyBlack = true;
		isTouchCloudyEarthquake = true;
		isTouchCloudyRain = true;
		isTouchCloudyThunderRain = true;
		
		for (int i = 0; i < isTouchCloudyShip.length; i++) {
			isTouchCloudyShip[i] = true;
		}
		isSpeakShip = true;
		
		isTouchUmbrella = true;
		isTouchGimmic = true;
		
		isTouchShapeOne = true;
		isTouchShapeTwo = true;
	}
	int xx = 0;
	
	//Touch Cloudy
	//touch shape one
	private void isTouchCloudySkyOne(final int x){
		distanceMove = shapeOne.getmXFirst() - shapeOne.getX();
		soundOff(OGG_A76_3_AREHANANDA, OGG_A76_3_KUMONANDA, OGG_A76_3_NANDAARE,OGG_A76_3_ARENANI, OGG_A76_3_KUMONANDAG, OGG_A76_3_NANDAAREG);
		OGG_A76_4_BOWAN.play();
		isTouchBoy = false;
		isTouchGirl = false;
		
		animsprBoy.clearEntityModifiers();
		animsprGirl.clearEntityModifiers();
		animsprBoy.setCurrentTileIndex(1);
		animsprGirl.setCurrentTileIndex(1);
		
		shapeOne.clearEntityModifiers();
		shapeTwo.clearEntityModifiers();
		
		shapeOne.registerEntityModifier(new MoveXModifier(0.5f, shapeOne.getX(), shapeOne.getmXFirst(), new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				shapeTwo.setPosition(1000, shapeTwo.getmYFirst());
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				switch (x) {
				case 0:
					OGG_A76_4_1.play();
					sprNameCloudy.setVisible(true);
					sprNameCloudy.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudy.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudy = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
						}
					}));
					break;
					
				case 2:
					OGG_A76_4_3.play();
					sprNameCloudyWind.setVisible(true);
					sprNameCloudyWind.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyWind.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyWind = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
						}
					}));
					break;
					
				case 4:
					for (int i = 0; i < animsprCloudyWithRain.length; i++) {
						animsprCloudyWithRain[i].setVisible(false);
						animsprCloudyWithRainF[i].setVisible(true);
					}
					sprBackgourndRain.setVisible(true);
					sprNameCloudyWithRain.setVisible(true);
					
					OGG_A76_4_5.play();
					OGG_A76_4_5_2_GOGO.play();
					
					cloudyRain();
					break;
					
				case 6:
					if(isSpeakShip){
						sprNameCloudyShip.setVisible(true);
						isSpeakShip = false;
						OGG_A76_4_7.play();
					}
					
					mScene.registerUpdateHandler(timerCloudyShip = new TimerHandler(4.0f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							runOnUpdateThread(new Runnable() {
								@Override public void run() {
									animsprBoy.setCurrentTileIndex(0);
									animsprGirl.setCurrentTileIndex(0);
									isSpeakShip = true;
									isTouchGimmic = true;
									isTouchShapeTwo = true;
									sprNameCloudyShip.setVisible(false);
									isTouchBoy = true;
									isTouchGirl = true;
									moveCloudy1();
									randomVisible2();
									moveCloudy2();
								}
							});
						}
					}));
					break;
					
				case 8:
					OGG_A76_4_10.play();
					playSoundDelay(OGG_A76_3_1_1, 1.5f);
					cloudyChange(animsprCloudyIcecream, 600, 600, 600, 600, 0, 0);
					sprNameCloudyIcecream.setVisible(true);
					sprNameCloudyIcecream.registerEntityModifier(new DelayModifier(2.9f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyIcecream.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyIce = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
						}
					}));
					break;
					
				case 10:
					OGG_A76_4_12.play();
					playSoundDelay(OGG_A76_4_12_KURUMA, 0.7f);
					cloudyChange(animsprCloudyCar, 600, 800, 800, 0, 0, 0);
					sprNameCloudyCar.setVisible(true);
					sprNameCloudyCar.registerEntityModifier(new DelayModifier(2.7f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyCar.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyCar = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
						}
					}));
					break;
				default:
					break;
				}
			}
		}));
	}
	
	//touch shape two
	private void isTouchCloudySkyTwo(final int x){
		distanceMove = shapeTwo.getmXFirst() - shapeTwo.getX();
		soundOff(OGG_A76_3_AREHANANDA, OGG_A76_3_KUMONANDA, OGG_A76_3_NANDAARE,OGG_A76_3_ARENANI, OGG_A76_3_KUMONANDAG, OGG_A76_3_NANDAAREG);
		OGG_A76_4_BOWAN.play();
		isTouchBoy = false;
		isTouchGirl = false;
		
		animsprBoy.clearEntityModifiers();
		animsprGirl.clearEntityModifiers();
		animsprBoy.setCurrentTileIndex(1);
		animsprGirl.setCurrentTileIndex(1);
		
		shapeOne.clearEntityModifiers();
		shapeTwo.clearEntityModifiers();
		animsprCloudyWithThunder.registerEntityModifier(
			new ScaleAtModifier(0.5f, 1f, 1.3f, animsprCloudyWithThunder.getWidth()/2, animsprCloudyWithThunder.getHeight()/2, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					animsprCloudyWithThunder.setScale(1);
				}
			}));
		shapeTwo.registerEntityModifier(new MoveXModifier(0.5f, shapeTwo.getX(), shapeTwo.getmXFirst(), new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				shapeOne.setPosition(1000, shapeOne.getmYFirst());
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				switch (x) {
				//CloudyRabbit
				case 9:
					OGG_A76_4_11.play();
					playSoundDelay(OGG_A76_4_11_USAGI, 1.8f);
					cloudyChange(animsprCloudyRabbit, 600, 600, 500, 500, 0, 0);
					sprNameCloudyRabbit.setVisible(true);
					sprNameCloudyRabbit.registerEntityModifier(new DelayModifier(2.7f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyRabbit.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyRabbit= true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							randomVisible1();
							moveCloudy1();
							moveCloudy2();
						}
					}));
					break;
					
				//CloudyDonut
				case 7:
					OGG_A76_4_9.play();
					playSoundDelay(OGG_A76_3_1_2_EAT, 1.4f);
					playSoundDelay(OGG_A76_3_1_2, 2.2f);
					cloudyChange(animsprCloudyDonut, 600, 600, 500, 300, 800, 0);
					sprNameCloudyDonut.setVisible(true);
					sprNameCloudyDonut.registerEntityModifier(new DelayModifier(3.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyDonut.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyDonut= true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							randomVisible1();
							moveCloudy1();
							moveCloudy2();
						}
					}));
					break;
					
				//CloudyBlack
				case 5:
					sprBackgourndBlack.setVisible(true);
					OGG_A76_4_6.play();
					playSoundDelay(OGG_A76_4_6_2_GOGO, 0.5f);
					sprNameCloudyBlack.setVisible(true);
					sprNameCloudyBlack.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyBlack.setVisible(false);
							sprBackgourndBlack.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyBlack= true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							moveCloudy1();
							randomVisible1();
							moveCloudy2();
						}
					}));
					break;
				
				// Cloudy Thunder Rain
				case 3:
					OGG_A76_4_4.play();
					OGG_A76_4_4_2_GORO.play();
					
					animsprCloudyWithThunder.setVisible(false);
					animsprCloudyWithThunderF.setVisible(true);
					
					sprNameCloudyWithThunder.setVisible(true);
					sprBackgourndThunder.setVisible(true);
					
					animsprCloudyWithThunderF.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							OGG_A76_4_4_2_DON.play();
							
							Random thunder = new Random();
							int thunders = thunder.nextInt(3);
							OGG_Surprise[thunders].play();
							
							setVisibleSprite(true,sprThunderleft, sprThunderright, sprBoySurprise, sprGirlSurprise);
							
							animsprBoy.setCurrentTileIndex(6);
							animsprGirl.setCurrentTileIndex(6);
							
							visibleThunder(sprThunderleft);
							visibleThunder(sprThunderright);
							
							sprBoySurprise.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
									new AlphaModifier(0.35f, 1f, 0f),
									new AlphaModifier(0.35f, 0f, 1f)), 2,new ILoopEntityModifierListener() {
										@Override
										public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
											setVisibleSprite(true, sprBoySurprise);
										}
										
										@Override
										public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
											setVisibleSprite(false, sprBoySurprise);
										}
									}));
							sprGirlSurprise.registerEntityModifier(new SequenceEntityModifier(
									new AlphaModifier(0.35f, 1f, 0f),
									new AlphaModifier(0.35f, 0f, 1f),
									new AlphaModifier(0.35f, 1f, 0f),
									new AlphaModifier(0.35f, 0f, 1f, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											sprGirlSurprise.setVisible(false);
											cloudyRainThunder();
										}
									})));
							
						}
					}));
					break;
					
				// Cloudy Earthquake
				case 1:
					OGG_A76_4_2.play();
					playSoundDelay(OGG_A76_4_2_2_GATA, 1.0f);
					sprNameCloudyEarthquake.setVisible(true);
					sprNameCloudyEarthquake.registerEntityModifier(new DelayModifier(3.6f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							animsprCloudyEarthquake.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									Random Earthquake = new Random();
									int Earthquakes = Earthquake.nextInt(2);
									OGG_Earthquake[Earthquakes].play();
									animsprBoy.setCurrentTileIndex(2);
									animsprGirl.setCurrentTileIndex(2);
									animsprBird.setCurrentTileIndex(1);
									rotateEarthquake(animsprBoy,126,412);
									rotateEarthquake(animsprGirl,126,412);
									rotateEarthquake(animsprBird,40,100);
								}
							}));
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprNameCloudyEarthquake.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchBird = true;
							isTouchCloudyEarthquake= true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							randomVisible1();
							moveCloudy1();
							moveCloudy2();
						}
					}));
					break;
				default:
					break;
				}
			}
		}));
	}
	
	//Cloudy ship 
	private void touchCloudyShip(int shipPosition){
		mScene.unregisterUpdateHandler(timerCloudyShip);
		isTouchCloudyShip[shipPosition] = false;
		isTouchGimmic = false;
		isTouchShapeTwo = false;
		shapeOne.clearEntityModifiers();
		shapeTwo.clearEntityModifiers();
		OGG_A76_4_7_HITSUJI.play();
		isTouchCloudySkyOne(6);
		switch (shipPosition) {
		case 0:
			actionShip(0);
			break;
			
		case 1:
			actionShip(1);
			break;
			
		case 2:
			actionShip(2);
			break;
			
		case 3:
			actionShip(3);
			break;
			
		case 4:
			actionShip(4);
			break;
			
		case 5:
			actionShip(5);
			break;
			
		case 6:
			actionShip(6);
			break;
			
		case 7:
			actionShip(7);
			break;
			
		case 8:
			actionShip(8);
			break;
			
		case 9:
			actionShip(9);
			break;
		default:
			break;
		}
	}
	
	// Action ship
	private void actionShip(final int ship){
		animsprCloudyShip[ship].setVisible(false);
		animsprShip[ship].setVisible(true);
		animsprShip[ship].setPosition(animsprCloudyShip[ship].getX(), animsprCloudyShip[ship].getY());
		animsprShip[ship].setScale(animsprCloudyShip[ship].getWidth()/animsprShip[ship].getWidth());
		animsprShip[ship].registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				animsprShip[ship].setVisible(false);
				animsprCloudyShip[ship].setVisible(true);
				isTouchCloudyShip[ship] = true;
			}
		}));
	}
	
	//Rotate Earthquake 
	private void rotateEarthquake(final AnimatedSprite animatedSprite, int rotateX, int rotateY){
		animatedSprite.registerEntityModifier(new SequenceEntityModifier(
			new RotationAtModifier(0.2f, 0, -5, rotateX, rotateY),
			new RotationAtModifier(0.4f, -5, 5, rotateX, rotateY),
			new RotationAtModifier(0.4f, 5, -5, rotateX, rotateY),
			new RotationAtModifier(0.4f, -5, 5, rotateX, rotateY),
			new RotationAtModifier(0.4f, 5, -5, rotateX, rotateY),
			new RotationAtModifier(0.4f, -5, 5, rotateX, rotateY),
			new RotationAtModifier(0.2f, 5, 0, rotateX, rotateY , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					animatedSprite.setCurrentTileIndex(0);
				}
			})));
	}
	
	//Cloudy Change
	private void cloudyChange(final AnimatedSprite actAnim, int x, int y, int z, int t, int h, int loop){
		final long animDuration[] = new long[] {x,y,z,t,h};
		final int animFrame[] = new int []{0,1,2,3,4};
		actAnim.animate(animDuration, animFrame, loop, new IAnimationListener() {
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
				actAnim.setCurrentTileIndex(0);
				
			}
		});
	}
	
	//Cloudy Rain
	private void cloudyRain(){
		animsprBoy.setCurrentTileIndex(3);
		animsprGirl.setCurrentTileIndex(3);
		
		animsprUmbrella.setVisible(true);
		animsprUmbrella.setCurrentTileIndex(0);
		animsprUmbrella.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
			new RotationAtModifier(0.2f, 0, -2, 230, 403),
			new RotationAtModifier(0.4f, -2, 2, 230, 403),
			new RotationAtModifier(0.2f, 2, 0, 230, 403)),-1));
		
		visibleRain();
	}
	
	//Cloudy Rain Thunder
	private void cloudyRainThunder(){
		animsprBoy.setCurrentTileIndex(3);
		animsprGirl.setCurrentTileIndex(3);
		
		animsprUmbrella.setVisible(true);
		animsprUmbrella.setCurrentTileIndex(0);
		animsprUmbrella.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
			new RotationAtModifier(0.2f, 0, -2, 230, 403),
			new RotationAtModifier(0.4f, -2, 2, 230, 403),
			new RotationAtModifier(0.2f, 2, 0, 230, 403)),-1));
		
		visibleRainThunder();
	}
	
	//Visible rain thunder
	private void visibleRainThunder(){
		OGG_A76_4_4_2_3_AME.play();
		mScene.unregisterUpdateHandler(timerThunder);
		timerThunder = new TimerHandler(0.25f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						checkUmbrella();
						addThunder();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerThunder);
		sprNameCloudyWithThunder.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				mScene.unregisterUpdateHandler(timerThunder);
				if(isTouchUmbrella && checkTouchUmbrella!= 1){
					animsprUmbrella.clearEntityModifiers();
					animsprUmbrella.setVisible(false);
					animsprBoy.setCurrentTileIndex(5);
					animsprGirl.setCurrentTileIndex(5);
					
					Random umbrella = new Random();
					int umbrellas = umbrella.nextInt(2);
					OGG_Umbrella[umbrellas].play();
					
					animsprBoy.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							visibleWaterBoy();
							visibleWaterGirl();
							animsprCloudyWithRainF[0].registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									mScene.unregisterUpdateHandler(timerWaterBoy);
									mScene.unregisterUpdateHandler(timerWaterGirl);
								}
							}));
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							sprBackgourndThunder.setVisible(false);
							animsprCloudyWithThunder.setVisible(true);
							animsprCloudyWithThunderF.setVisible(false);
							sprNameCloudyWithThunder.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyThunderRain = true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							randomVisible1();
							moveCloudy1();
							moveCloudy2();
							checkTouchUmbrella = 0;
						}
					}));
				}else {
					animsprBoy.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							animsprUmbrella.setVisible(false);
							sprBackgourndThunder.setVisible(false);
							sprNameCloudyWithThunder.setVisible(false);
							animsprCloudyWithThunder.setVisible(true);
							animsprCloudyWithThunderF.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyThunderRain = true;
							isTouchGimmic = true;
							isTouchShapeOne = true;
							randomVisible1();
							moveCloudy1();
							moveCloudy2();
							checkTouchUmbrella = 0;
							isTouchUmbrella = true;
						}
					}));
				}
			}
		}));
	}
	
	//Visible rain
	private void visibleRain(){
		OGG_A76_4_5_2_6_AME.play();
		mScene.unregisterUpdateHandler(timerRain);
		timerRain = new TimerHandler(0.25f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						checkUmbrella();
						addRain();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerRain);
		
		sprNameCloudyWithRain.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				mScene.unregisterUpdateHandler(timerRain);
				if(isTouchUmbrella && checkTouchUmbrella!= 1){
					animsprUmbrella.clearEntityModifiers();
					animsprUmbrella.setVisible(false);
					animsprBoy.setCurrentTileIndex(5);
					animsprGirl.setCurrentTileIndex(5);
					Random umbrella = new Random();
					int umbrellas = umbrella.nextInt(2);
					OGG_Umbrella[umbrellas].play();
					
					animsprBoy.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							visibleWaterBoy();
							visibleWaterGirl();
							animsprCloudyWithRainF[0].registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									mScene.unregisterUpdateHandler(timerWaterBoy);
									mScene.unregisterUpdateHandler(timerWaterGirl);
								}
							}));
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							for (int i = 0; i < animsprCloudyWithRain.length; i++) {
								animsprCloudyWithRain[i].setVisible(true);
								animsprCloudyWithRainF[i].setVisible(false);
							}
							sprBackgourndRain.setVisible(false);
							sprNameCloudyWithRain.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyRain = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
							checkTouchUmbrella = 0;
						}
					}));
				} else {
					animsprBoy.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprUmbrella.setVisible(false);
							animsprBoy.setCurrentTileIndex(0);
							animsprGirl.setCurrentTileIndex(0);
							for (int i = 0; i < animsprCloudyWithRain.length; i++) {
								animsprCloudyWithRain[i].setVisible(true);
								animsprCloudyWithRainF[i].setVisible(false);
							}
							sprBackgourndRain.setVisible(false);
							sprNameCloudyWithRain.setVisible(false);
							isTouchBoy = true;
							isTouchGirl = true;
							isTouchCloudyRain = true;
							isTouchGimmic = true;
							isTouchShapeTwo = true;
							moveCloudy1();
							randomVisible2();
							moveCloudy2();
							checkTouchUmbrella = 0;
							isTouchUmbrella = true;
						}
					}));
				}
			}
		}));
	}
	
	//checkUmbrella
	private void checkUmbrella(){
		if(isTouchUmbrella){
			checkTouchUmbrella = 0;
		}else{
			checkTouchUmbrella = 1;
		}
	}
	
	//Visible water boy
	private void visibleWaterBoy(){
		mScene.unregisterUpdateHandler(timerWaterBoy);
		timerWaterBoy = new TimerHandler(0.05f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						addWaterBoy();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerWaterBoy);
	}
	
	//Visible water girl
	private void visibleWaterGirl(){
		mScene.unregisterUpdateHandler(timerWaterGirl);
		timerWaterGirl = new TimerHandler(0.05f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						addWaterGirl();
					}
				});
			}
		});
		mScene.registerUpdateHandler(timerWaterGirl);
	}
	
	//ADD RAIN
	private void addRain(){
		final Sprite poolRain = sprPoolRain.obtainPoolItem();
		arrayRain.add(poolRain);
		poolRain.detachSelf();
		mScene.attachChild(poolRain);
		
		int distance = 450;
		
		poolRain.setPosition(187f, 123f);
		animationForRain(poolRain,distance);
	}
	
	//ADD Rain Thunder
		private void addThunder(){
			final Sprite poolRainThunder = sprPoolThunderRain.obtainPoolItem();
			arrayThunderRain.add(poolRainThunder);
			poolRainThunder.detachSelf();
			mScene.attachChild(poolRainThunder);
			
			int distance = 450;
			
			poolRainThunder.setPosition(126f, 201f);
			animationForRainThunder(poolRainThunder,distance);
		}
	
	//animationForRain
	private void animationForRain(final Sprite rain, final float distance){
		float time = 1.0f;
		rain.clearEntityModifiers();
		rain.registerEntityModifier(new SequenceEntityModifier(
			new MoveYModifier(time, rain.getY(), distance, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							arrayRain.remove(rain);
							sprPoolRain.recyclePoolItem(rain);
						}
					});
				}
			})));
	}
	
	//animationForRainThunder
	private void animationForRainThunder(final Sprite rainthunder, final float distance){
		float time = 1.0f;
		rainthunder.clearEntityModifiers();
		rainthunder.registerEntityModifier(new SequenceEntityModifier(
			new MoveYModifier(time, rainthunder.getY(), distance, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							arrayThunderRain.remove(rainthunder);
							sprPoolThunderRain.recyclePoolItem(rainthunder);
						}
					});
				}
			})));
	}
	
	//ADD WATER BOY
	private void addWaterBoy(){
		Random waterboy = new Random();
		final Sprite poolWaterBoy = sprPoolWaterBoy.obtainPoolItem();
		arrayWaterBoy.add(poolWaterBoy);
		poolWaterBoy.detachSelf();
		animsprBoy.attachChild(poolWaterBoy);
		
		int distance = 200;
		float pX = waterboy.nextInt(100) + 70;
		float pY = waterboy.nextInt(170) + 100;
		poolWaterBoy.setPosition(pX, pY);
		animationForWaterBoy(poolWaterBoy,distance);
	}
	
	//ADD WATER GIRL
		private void addWaterGirl(){
			Random watergirl = new Random();
			final Sprite poolWaterGirl = sprPoolWaterGirl.obtainPoolItem();
			arrayWaterGirl.add(poolWaterGirl);
			poolWaterGirl.detachSelf();
			animsprGirl.attachChild(poolWaterGirl);
			
			int distance = 200;
			float pX = watergirl.nextInt(100) + 70;
			float pY = watergirl.nextInt(170) + 100;
			poolWaterGirl.setPosition(pX, pY);
			animationForWaterGirl(poolWaterGirl,distance);
		}
	
	//animationForWaterBoy
	private void animationForWaterBoy(final Sprite water, final float distance){
		float time = Math.abs(water.getY() + distance) / 600;
		water.clearEntityModifiers();
		water.registerEntityModifier(new SequenceEntityModifier(
			new MoveYModifier(time, water.getY(), water.getY() + distance, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							arrayWaterBoy.remove(water);
							sprPoolWaterBoy.recyclePoolItem(water);
						}
					});
				}
			})));
	}
	
	//animationForWaterGirl
		private void animationForWaterGirl(final Sprite water, final float distance){
			float time = Math.abs(water.getY() + distance) / 600;
			water.clearEntityModifiers();
			water.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(time, water.getY(), water.getY() + distance, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								arrayWaterGirl.remove(water);
								sprPoolWaterGirl.recyclePoolItem(water);
							}
						});
					}
				})));
		}
	
	//Move sky cloudy 1
	private void moveCloudy1(){
		shapeOne.setVisible(true);
		float time = Math.abs(shapeOne.getX() + 540 ) / 308;
		shapeOne.registerEntityModifier(
			new MoveXModifier(time, shapeOne.getX(), -540, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					for (int i = 0; i < shapeCloudy.length; i++) {
						if(i % 2 == 0) shapeCloudy[i].setVisible(false);
					}
					randomVisible1();
					shapeOne.setPosition(1000, shapeOne.getmYFirst());
					moveCloudy1();
				}
			}));
	}

	//Move sky cloudy 2
	private void moveCloudy2(){
		shapeTwo.setVisible(true);
		if(repeatCloudy2 == 0){
			float timeR = Math.abs(shapeTwo.getX() + 540 ) / 308;
			repeatCloudy2 = 1;
			shapeTwo.registerEntityModifier(
					new MoveXModifier(timeR, shapeTwo.getX(), -540, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							for (int i = 0; i < shapeCloudy.length; i++) {
								if(i % 2 != 0) shapeCloudy[i].setVisible(false);
							}
							randomVisible2();
							shapeTwo.setPosition(1000, shapeTwo.getmYFirst());
							moveCloudy2();
						}
					}));
		}
		else {
			float time = Math.abs(shapeTwo.getX() + 540 ) / 308;
			shapeTwo.registerEntityModifier(
				new MoveXModifier(time, shapeTwo.getX(), -540, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						for (int i = 0; i < shapeCloudy.length; i++) {
							if(i % 2 != 0) shapeCloudy[i].setVisible(false);
						}
						randomVisible2();
						shapeTwo.setPosition(1000, shapeTwo.getmYFirst());
						moveCloudy2();
					}
				}));
		}
		
	}
		
	//random shape one
	private void randomVisible1(){
		setVisibleSprite(false, shapeCloudy[0],shapeCloudy[2],shapeCloudy[4],shapeCloudy[6],shapeCloudy[8],shapeCloudy[10]);
		Random shapeone = new Random();
		int shapeones = shapeone.nextInt(6);
		switch (shapeones) {
		case 0:
			shapeCloudy[0].setVisible(true);
			break;
		case 1:
			shapeCloudy[2].setVisible(true);
			break;
		case 2:
			shapeCloudy[4].setVisible(true);
			break;
		case 3:
			shapeCloudy[6].setVisible(true);
			break;
		case 4:
			shapeCloudy[8].setVisible(true);
			break;
		case 5:
			shapeCloudy[10].setVisible(true);
			break;
		default:
			break;
		}
	}
	
	//random shape two
	private void randomVisible2(){
		setVisibleSprite(false, shapeCloudy[1],shapeCloudy[3],shapeCloudy[5],shapeCloudy[7],shapeCloudy[9]);
		Random shapetwo = new Random();
		int shapetwos = shapetwo.nextInt(5);
		switch (shapetwos) {
		case 0:
			shapeCloudy[1].setVisible(true);
			break;
		case 1:
			shapeCloudy[3].setVisible(true);
			break;
		case 2:
			shapeCloudy[5].setVisible(true);
			break;
		case 3:
			shapeCloudy[7].setVisible(true);
			break;
		case 4:
			shapeCloudy[9].setVisible(true);
			break;
		default:
			break;
		}
	}
	
	//Cloudy Mountain Appear
	private void appearCloudyMountain(){
		Random mountain = new Random();
		int mountains = mountain.nextInt(3);
		switch (mountains) {
		case 0:
			mScene.unregisterUpdateHandler(timerCloudyMountain);
			timerCloudyMountain = new TimerHandler(4.0f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					OGG_A76_1_3_POWAN.play();
					setVisibleSprite(true, sprCloudyMountain13);				
				}
			});
			mScene.registerUpdateHandler(timerCloudyMountain);
			break;
		case 1:
			mScene.unregisterUpdateHandler(timerCloudyMountain);
			timerCloudyMountain = new TimerHandler(4.0f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					OGG_A76_1_3_POWAN.play();
					setVisibleSprite(true, sprCloudyMountain14);				
				}
			});
			mScene.registerUpdateHandler(timerCloudyMountain);
			break;
		default:
			mScene.unregisterUpdateHandler(timerCloudyMountain);
			timerCloudyMountain = new TimerHandler(4.0f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					OGG_A76_1_3_POWAN.play();
					setVisibleSprite(true, sprCloudyMountain15);			
				}
			});
			mScene.registerUpdateHandler(timerCloudyMountain);
			break;
		}
	}
	
	//Touch Cloudy Mountain
	private void touchCloudyMountain(final Sprite sCurrent, final Sprite sNext , final Sprite sName){
		isTouchCloudyMountain13 = false;
		isTouchCloudyMountain14 = false;
		isTouchCloudyMountain15 = false;
		sName.setVisible(true);
		sCurrent.registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				isTouchCloudyMountain13 = true;
				isTouchCloudyMountain14 = true;
				isTouchCloudyMountain15 = true;
				sCurrent.setVisible(false);
				sName.setVisible(false);
				sCurrent.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						OGG_A76_1_3_POWAN.play();
						sNext.setVisible(true);
					}
				}));
			}
		}));
	}
	
	//Visible Thunder
	private void visibleThunder(final Sprite sprite){
		sprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(0.3f, 1f, 0f),
				new AlphaModifier(0.3f, 0, 1)), 7,new ILoopEntityModifierListener() {
					@Override
					public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						setVisibleSprite(true, sprite);
					}
					
					@Override
					public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						setVisibleSprite(false, sprite);
					}
				}));
	}
	
	//Reset
	private void reset(){
		mScene.unregisterUpdateHandler(timerRain);
		mScene.unregisterUpdateHandler(timerThunder);
		mScene.unregisterUpdateHandler(timerWaterBoy);
		mScene.unregisterUpdateHandler(timerWaterGirl);
		mScene.unregisterUpdateHandler(timerCloudyMountain);
		mScene.unregisterUpdateHandler(timerCloudyShip);
		mScene.unregisterUpdateHandler(timerSmoke);
		
		runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
			
				for (Iterator<Sprite> iterator = arrayRain.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolRain.onHandleRecycleItem(type);
				}
				arrayRain.clear();
				
				for (Iterator<Sprite> iterator = arrayThunderRain.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolThunderRain.onHandleRecycleItem(type);
				}
				arrayThunderRain.clear();
				
				for (Iterator<Sprite> iterator = arrayWaterBoy.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolWaterBoy.onHandleRecycleItem(type);
				}
				arrayWaterBoy.clear();
				
				for (Iterator<Sprite> iterator = arrayWaterGirl.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolWaterGirl.onHandleRecycleItem(type);
				}
				arrayWaterGirl.clear();
				
				for (Iterator<Sprite> iterator = arraySmokeL1.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolSmokeL1.onHandleRecycleItem(type);
				}
				arraySmokeL1.clear();
				
				for (Iterator<Sprite> iterator = arraySmokeL2.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolSmokeL2.onHandleRecycleItem(type);
				}
				arraySmokeL2.clear();
				
				for (Iterator<Sprite> iterator = arraySmokeR1.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolSmokeR1.onHandleRecycleItem(type);
				}
				arraySmokeR1.clear();
				
				for (Iterator<Sprite> iterator = arraySmokeR2.iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					sprPoolSmokeR2.onHandleRecycleItem(type);
				}
				arraySmokeR2.clear();
			}
		});

		//Clear Cloudy Mountain
		clearEntitySprite(sprCloudyMountain13);
		clearEntitySprite(sprCloudyMountain14);
		clearEntitySprite(sprCloudyMountain15);
		setVisibleSprite(false, sprNameCloudyMountain13,sprNameCloudyMountain14,sprNameCloudyMountain15,
				sprCloudyMountain13,sprCloudyMountain14,sprCloudyMountain15);
		
		//clear Bird
		clearEntitySprite(animsprBird);
		
		//Mountain
		clearEntitySprite(sprMountain);
		
		//Anim boy - girl
		clearEntitySprite(animsprBoy);
		clearEntitySprite(animsprGirl);
		
		//animsprUmbrella
		clearEntitySprite(animsprUmbrella);
		animsprUmbrella.setCurrentTileIndex(0);
		animsprUmbrella.setVisible(false);
		
		//Shape One
		clearEntitySprite(shapeOne);
		//Shape Two
		clearEntitySprite(shapeTwo);
		
		//Shape Cloudy
		for (int i = 0; i < shapeCloudy.length; i++) {
			clearEntitySprite(shapeCloudy[i]);
			shapeCloudy[i].setVisible(false);
		}
		
		clearEntitySprite(sprPlane);
		sprPlane.setRotation(0);
		
		clearEntitySprite(sprPlaneHeartL);
		sprPlaneHeartL.setPosition(sprPlaneHeartL.getmXFirst(), sprPlaneHeartL.getmYFirst());
		
		clearEntitySprite(sprPlaneHeartR);
		sprPlaneHeartR.setPosition(sprPlaneHeartR.getmXFirst(), sprPlaneHeartR.getmYFirst());
		
		clearEntitySprite(sprNamePlane);
		setVisibleSprite(false, sprPlaneHeartL,sprPlaneHeartR,sprNamePlane);
		
		clearEntitySprite(sprBoySurprise);
		clearEntitySprite(sprGirlSurprise);
		clearEntitySprite(sprThunderleft);
		clearEntitySprite(sprThunderright);
		
		setVisibleSprite(false, sprBoySurprise,sprGirlSurprise,sprThunderleft,sprThunderright);
		
		//name Cloudy
		clearEntitySprite(sprNameCloudy);
		clearEntitySprite(sprNameCloudyEarthquake);
		clearEntitySprite(sprNameCloudyWind);
		clearEntitySprite(sprNameCloudyWithThunder);
		clearEntitySprite(sprNameCloudyWithRain);
		clearEntitySprite(sprNameCloudyBlack);
		clearEntitySprite(sprNameCloudyShip);
		clearEntitySprite(sprNameCloudyDonut);
		clearEntitySprite(sprNameCloudyIcecream);
		clearEntitySprite(sprNameCloudyRabbit);
		clearEntitySprite(sprNameCloudyCar);
		
		setVisibleSprite(false, sprNameCloudyMountain13,sprNameCloudyMountain14,sprNameCloudyMountain15,sprNameCloudy,
			sprNameCloudyEarthquake,sprNameCloudyWind,sprNameCloudyWithThunder,sprNameCloudyWithRain,sprNameCloudyBlack,
			sprNameCloudyShip,sprNameCloudyDonut,sprNameCloudyIcecream,sprNameCloudyRabbit,sprNameCloudyCar);
		
		setVisibleSprite(false, sprBackgourndThunder,sprBackgourndRain,sprBackgourndBlack);
		
		//Action Cloudy
		clearEntityAnim(animsprCloudy);
		clearEntityAnim(animsprCloudyEarthquake);
		clearEntityAnim(animsprCloudyWind);
		clearEntityAnim(animsprCloudyWithThunder);
		animsprCloudyWithThunder.setVisible(true);
		clearEntityAnim(animsprCloudyWithThunderF);
		animsprCloudyWithThunderF.setVisible(false);
		for (int i = 0; i < animsprCloudyWithRain.length; i++) {
			clearEntityAnim(animsprCloudyWithRain[i]);
			animsprCloudyWithRain[i].setVisible(true);
			clearEntityAnim(animsprCloudyWithRainF[i]);
			animsprCloudyWithRainF[i].setVisible(false);
		}
		for (int i = 0; i < animsprCloudyBlack.length; i++) {
			clearEntityAnim(animsprCloudyBlack[i]);
		}
		for (int i = 0; i < animsprCloudyShip.length; i++) {
			clearEntityAnim(animsprCloudyShip[i]);
			animsprCloudyShip[i].setVisible(true);
		}
		for (int i = 0; i < animsprShip.length; i++) {
			clearEntityAnim(animsprShip[i]);
			animsprShip[i].setVisible(false);
		}
		clearEntityAnim(animsprCloudyDonut);
		clearEntityAnim(animsprCloudyRabbit);
		clearEntityAnim(animsprCloudyRabbit);
		clearEntityAnim(animsprCloudyCar);
	}
	
	//Clear Entity Sprite
	private void clearEntitySprite(RectangularShape myShape){
		if(myShape != null){
			myShape.clearEntityModifiers();
			myShape.setPosition(myShape.getmXFirst(), myShape.getmYFirst());
		}
	}
	
	//Clear Entity Sprite
	private void clearEntityAnim(AnimatedSprite animShape){
		if(animShape != null){
			animShape.clearEntityModifiers();
			animShape.stopAnimation();
		}
	}
	
	//Sprite Visible
	private void setVisibleSprite(boolean value, RectangularShape ...rectangularShapes){
		for (int i = 0; i < rectangularShapes.length; i++) {
			rectangularShapes[i].setVisible(value);
		}
	}
	
	//Sound off
	private void soundOff(Sound ...sounds){
		for (int i = 0; i < sounds.length; i++) {
			sounds[i].stop();
		}
	}
	
	//MP3 STOP
	private void mp3Stop(){
		OGG_A76_1_3_GATA.stop();
		OGG_A76_1_3_POWAN.stop();
		OGG_A76_2_1_PIYO.stop();
		OGG_A76_3_1_1.stop();
		OGG_A76_3_1_2.stop();
		OGG_A76_3_1_2_EAT.stop();
		OGG_A76_3_4_3_KASA.stop();
		OGG_A76_3_6_BITYO.stop();
		OGG_A76_3_6_BITYO2.stop();
		OGG_A76_3_AREHANANDA.stop();
		OGG_A76_3_ARENANI.stop();
		OGG_A76_3_KUMONANDA.stop();
		OGG_A76_3_KUMONANDAG.stop();
		OGG_A76_3_NANDAARE.stop();
		OGG_A76_3_NANDAAREG.stop();
		OGG_A76_4_1.stop();
		OGG_A76_4_10.stop();
		OGG_A76_4_11.stop();
		OGG_A76_4_11_USAGI.stop();
		OGG_A76_4_12.stop();
		OGG_A76_4_12_KURUMA.stop();
		OGG_A76_4_13.stop();
		OGG_A76_4_14.stop();
		OGG_A76_4_15.stop();
		OGG_A76_4_2.stop();
		OGG_A76_4_2_2_GATA.stop();
		OGG_A76_4_3.stop();
		OGG_A76_4_4.stop();
		OGG_A76_4_4_2_3_AME.stop();
		OGG_A76_4_4_2_DON.stop();
		OGG_A76_4_4_2_GORO.stop();
		OGG_A76_4_5.stop();
		OGG_A76_4_5_2_6_AME.stop();
		OGG_A76_4_5_2_GOGO.stop();
		OGG_A76_4_6.stop();
		OGG_A76_4_6_2_GOGO.stop();
		OGG_A76_4_7.stop();
		OGG_A76_4_7_HITSUJI.stop();
		OGG_A76_4_8.stop();
		OGG_A76_4_8_2_FLY.stop();
		OGG_A76_4_8_2_FLY2.stop();
		OGG_A76_4_9.stop();
		OGG_A76_4_BOWAN.stop();
		OGG_A76_ODOROKU1.stop();
		OGG_A76_ODOROKU2.stop();
		OGG_A76_ODOROKU3.stop();
	}
	
	//Class SpritePool
	class SpritePool extends GenericPool<Sprite> {

		private ITextureRegion mTextureRegion;
		//private VertexBufferObjectManager mVertexBufferObjectManager;

		public SpritePool(ITextureRegion mTextureRegion) {
			if (mTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have
				// a TextureRegion
				throw new IllegalArgumentException(
						"The texture region must not be NULL");
			}
			this.mTextureRegion = mTextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			return new Sprite(0, 0, mTextureRegion, Vol3Ashitatenki.this.getVertexBufferObjectManager());
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
	
	/**
	 * <p>A {@link CubicBezierMoveModifier} that rotates the entity so it faces the direction of travel.</p>
	 *
	 * <p>(c) 2010 Nicolas Gramlich<br>
	 * (c) 2011 Zynga Inc.</p>
	 *
	 * @author Scott Kennedy
	 * @author Pawel Plewa
	 * @author Nicolas Gramlich
	 */
	
	class RotateCubicBezierMoveModifierL extends CubicBezierCurveMoveModifier {		
	    public RotateCubicBezierMoveModifierL(final float pDuration, final float pX1, final float pY1, final float pX2, final float pY2, final float pX3,
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
	        
	        final float degree = ((float)MathUtils.atan2(deltaY, deltaX));
	        float angle = (float) (degree * 180 / 3.14) + 120;

	        pEntity.setRotation(angle);
	    }
	}
	
	class RotateCubicBezierMoveModifierR extends CubicBezierCurveMoveModifier {
	    public RotateCubicBezierMoveModifierR(final float pDuration, final float pX1, final float pY1, final float pX2, final float pY2, final float pX3,
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
	        
	        final float degree = ((float)MathUtils.atan2(deltaY, deltaX));
	        float angle = (float) (degree * 180 / 3.14) + 50;

	        pEntity.setRotation(angle);
	    }
	}

}
