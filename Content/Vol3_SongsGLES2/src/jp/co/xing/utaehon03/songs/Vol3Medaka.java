package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MedakaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.annotation.SuppressLint;
import android.opengl.GLES20;
import android.util.Log;

public class Vol3Medaka extends BaseGameActivity implements
		IOnSceneTouchListener {
	private static final String LOG = "LOG_VOL3MEDAKA";
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mTextureAll;
	private TexturePack mTextureWater;
	private TexturePack mTextureHeader;
	private TexturePackTextureRegionLibrary mTextureLibHeader;
	
	private ITextureRegion mWaterTexture;
	private ITextureRegion mHeaderTexture;
	private ITextureRegion mClipBgTexture;
	private ITextureRegion mClipBoxTexture;
	private ITextureRegion mCotTexture;
	
	private ITiledTextureRegion mStridersTiledTextureRegion;
	private ITiledTextureRegion fishTiledTextureRegion;
	private ITiledTextureRegion mBugTiledTextureRegion;
	private ITiledTextureRegion mWaterTiledTextureRegion;
	
	private ClipLayer mClipLayer;
	
	private Sprite sprbgHeaderLeft;
	private Sprite sprbgHeaderRight;
	
	private TextureRegion ttrHeaderLeft;
	private TextureRegion ttrHeaderRight;
	
	private Sprite sprBongFake;
	
	private Sprite waterSprite,clipBg, mBoxClipSprite,headerSprite,mBongBoxClipSprite;
	private Sprite mCotSprite,mBangSprite,mCongTraiSprite,mCongPhaiSprite,mBongSprite;
	private Sprite fish131Sprite,fish132Sprite,fish134Sprite,fish135Sprite,fish136Sprite
	,fish137Sprite,fish139Sprite,fish1310Sprite,fish1311Sprite,fish1313Sprite,fish1314Sprite
	,fish1315Sprite,fish1316Sprite,saudat1318Sprite,saudat1319Sprite,saudat1320Sprite
	,saudat1321Sprite;
	private ITiledTextureRegion giunTiledTextureRegion,cuonchieuTiledTextureRegion
	,echTiledTextureRegion;
	private AnimatedSprite mGiunSprite,mCuonchieuSprite,mEchSprite,mWaterSprite;
	private Sprite mCayHoaSprite,saudat451Sprite,saudat452Sprite, mLa101Sprite
	,mLa102Sprite,mLa103Sprite,mLa1011Sprite,mLa1012Sprite;
	private Sprite mCanhHoaTrenCaySprite[]=new Sprite[7],mCanhHoaDuoiNuocSprite[]=new Sprite[7];
	private int pointCanhHoaTrenCay[][]={{832,759,838,924,937,838,742},{1,96,134,65,14,44,19}}
	,pointCanhHoaDuoiNuoc[][]={{622,850,748,699,710,683,870},{327,215,287,261,307,283,317}};
	private Sprite mCanhHoaSprite[]=new Sprite[7];
	
	private AnimatedSprite mStridersSprite;
	private AnimatedSprite fishSprite;
	private AnimatedSprite mBugSprite;

	Random randSound;
	
	private boolean checkfish131=true,checkfish132=true,checkfish134=true,checkfish135=true
		,checkfish139=true,checkfish1310=true,checkfish1311=true,checkbong=true
		,checksaudat1318=true,checksaudat1319 = true,checksaudat1320 = true,checksaudat1321=true,checksaudat451=true,
		checksaudat452=true,checkgiun=true,checkcuonchieu=true,checkech=true,checkfish=true,checkWater=true
		,checkstrider=true,checkbug=true,checkCayHoa=true,checkfish137=true,checkfish136=true;
	
	private static final int CLIP_WIDTH = 400;
	private static final int CLIP_HEIGHT = 280;
	private int TRUE_WIDTH = 0;
	private int TRUE_HEIGHT = 0;
	private int lastX = 0;
	private int lastY = 0;
	private int countFish = 2;
	private int pointEch;
	private Rectangle rectangle;
	
	private float arrPointBox[][] = {
			{ 30, 530, 530, 555, 555, 0, 0, 30 ,30 },
			{ 0, 0, 175,  220, 370, 370, 220, 175 , 0} };
	
	TimerHandler soundTimer, echTimer;
	
	private Sound OGG_A3_14_KORO,OGG_A4_10_SAKURA,OGG_A4_13_15_16,OGG_A4_13_HAWAI
	,OGG_A4_13_HYOI,OGG_A4_13_ITEKIMASU,OGG_A4_13_ITEKIMASU2,OGG_A4_13_KYU,OGG_A4_13_NU_VO
	,OGG_A4_13_POMI,OGG_A4_13_SEITO1,OGG_A4_13_SEITO2,OGG_A4_13_SEITO3,OGG_A4_13_SEITO4
	,OGG_A4_13_SEITO5,OGG_A4_13_VO1,OGG_A4_13_VO2,OGG_A4_13_VO3,OGG_A4_14_CHAP2
	,OGG_A4_14_CHAPON,OGG_A4_2_KAERU,OGG_A4_3_MIMIZU,OGG_A4_4_DANGOMUSHI,OGG_A4_8_FUNA
	,OGG_A4_9_GENGORO;
	
	// Creat Resource
	@Override
	public void onCreateResources() {
		Log.d(LOG, "+++++++++++ ON CREATE RESOURCE.");
		SoundFactory.setAssetBasePath("medaka/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("medaka/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager()
				, pAssetManager, "medaka/gfx/");
		super.onCreateResources();
	}

	//Load Resource 
	@Override
	protected void loadKaraokeResources() {
		Log.d(LOG, "+++++++++++ LOAD KARAOKE RESOURCE.");
		mTextureAll = mTexturePackLoaderFromSource.load("gfxAll.xml");
		mTextureWater = mTexturePackLoaderFromSource.load("gfxWater.xml");
		mTextureHeader = mTexturePackLoaderFromSource.load("gfxHeader.xml");
		mTextureAll.loadTexture();
		mTextureWater.loadTexture();
		mTextureHeader.loadTexture();
		
		mTextureLibHeader = mTextureHeader.getTexturePackTextureRegionLibrary();
		
		mWaterTexture = mTextureWater.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_11_IPHONE_ID);
		mClipBgTexture = mTextureWater.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_22_IPHONE_ID);
		mHeaderTexture= mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_6_1_IPHONE_ID);
		
		mStridersTiledTextureRegion = getTiledTextureFromPacker(mTextureAll, 
				Vol3MedakaResource.A4_7_1_IPHONE_ID,Vol3MedakaResource.A4_7_2_IPHONE_ID);
		fishTiledTextureRegion = getTiledTextureFromPacker(mTextureAll, 
				Vol3MedakaResource.A4_8_1_IPHONE_ID,Vol3MedakaResource.A4_8_2_IPHONE_ID);
		mBugTiledTextureRegion = getTiledTextureFromPacker(mTextureAll, 
				Vol3MedakaResource.A4_9_1_IPHONE_ID,Vol3MedakaResource.A4_9_2_IPHONE_ID);
		mWaterTiledTextureRegion=getTiledTextureFromPacker(mTextureAll, Vol3MedakaResource.A4_15_1_IPHONE_ID, Vol3MedakaResource.AA4_15_2_IPHONE_ID);
		giunTiledTextureRegion=getTiledTextureFromPacker(mTextureAll, Vol3MedakaResource.A4_3_1_IPHONE_ID, Vol3MedakaResource.A4_3_2_IPHONE_ID, Vol3MedakaResource.A4_3_3_IPHONE_ID);
		cuonchieuTiledTextureRegion=getTiledTextureFromPacker(mTextureAll, Vol3MedakaResource.A4_4_1_IPHONE_ID, Vol3MedakaResource.A4_4_2_IPHONE_ID);
		echTiledTextureRegion=getTiledTextureFromPacker(mTextureAll, Vol3MedakaResource.A4_2_1_IPHONE_ID, Vol3MedakaResource.A4_2_2_IPHONE_ID, Vol3MedakaResource.A4_2_3_IPHONE_ID);
		mClipBoxTexture = mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_14_1_IPHONE_ID);
		mCotTexture = mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_12_IPHONE_ID);
		
		ttrHeaderLeft = mTextureLibHeader.get(Vol3MedakaResource.A4_6_1_IPHONE_1_ID);
		ttrHeaderRight = mTextureLibHeader.get(Vol3MedakaResource.A4_6_1_IPHONE_2_ID);
	}

	//Load Sound
	@Override
	protected void loadKaraokeSound() {
		Log.d(LOG, "+++++++++++ LOAD KARAOKE SOUND.");
		OGG_A3_14_KORO = loadSoundResourceFromSD(Vol3MedakaResource.A3_14_KORO);
		OGG_A4_10_SAKURA = loadSoundResourceFromSD(Vol3MedakaResource.A4_10_SAKURA);
		OGG_A4_13_15_16 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_15_16);
		OGG_A4_13_HAWAI = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_HAWAI);
		OGG_A4_13_HYOI = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_HYOI);
		OGG_A4_13_ITEKIMASU = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_ITEKIMASU);
		OGG_A4_13_ITEKIMASU2 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_ITEKIMASU2);
		OGG_A4_13_KYU = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_KYU);
		OGG_A4_13_NU_VO = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_NU_VO);
		OGG_A4_13_POMI = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_POMI);
		OGG_A4_13_SEITO1 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_SEITO1);
		OGG_A4_13_SEITO2 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_SEITO2);
		OGG_A4_13_SEITO3 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_SEITO3);
		OGG_A4_13_SEITO4 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_SEITO4);
		OGG_A4_13_SEITO5 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_SEITO5);
		OGG_A4_13_VO1 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_VO1);
		OGG_A4_13_VO2 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_VO2);
		OGG_A4_13_VO3 = loadSoundResourceFromSD(Vol3MedakaResource.A4_13_VO3);
		OGG_A4_14_CHAP2 = loadSoundResourceFromSD(Vol3MedakaResource.A4_14_CHAP2);
		OGG_A4_14_CHAPON = loadSoundResourceFromSD(Vol3MedakaResource.A4_14_CHAPON);
		OGG_A4_2_KAERU = loadSoundResourceFromSD(Vol3MedakaResource.A4_2_KAERU);
		OGG_A4_3_MIMIZU = loadSoundResourceFromSD(Vol3MedakaResource.A4_3_MIMIZU);
		OGG_A4_4_DANGOMUSHI = loadSoundResourceFromSD(Vol3MedakaResource.A4_4_DANGOMUSHI);
		OGG_A4_8_FUNA = loadSoundResourceFromSD(Vol3MedakaResource.A4_8_FUNA);
		OGG_A4_9_GENGORO = loadSoundResourceFromSD(Vol3MedakaResource.A4_9_GENGORO);
	}

	//Load Scene
	@Override
	protected void loadKaraokeScene() {
		Log.d(LOG, "+++++++++++ ON LOAD SCENE");
		mScene = new Scene();
		mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		
		headerSprite = new Sprite(-22, -16, mHeaderTexture, getVertexBufferObjectManager());
		mScene.attachChild(headerSprite);
		
		sprbgHeaderRight = new Sprite(465, -16, ttrHeaderRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sprbgHeaderRight);
		sprbgHeaderRight.setVisible(false);
		
		sprBongFake = new Sprite(679, 200, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_17_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(sprBongFake);
		
		waterSprite = new Sprite(0, 173, mWaterTexture, getVertexBufferObjectManager());
		mScene.attachChild(waterSprite);
		
		sprbgHeaderLeft = new Sprite(-22, -16, ttrHeaderLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sprbgHeaderLeft);
		//sprbgHeaderLeft.setVisible(false);
		
		mLa101Sprite = new Sprite(310, 538, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_1_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mLa101Sprite);
		
		mLa102Sprite = new Sprite(60, 255, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_2_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mLa102Sprite);

		mLa103Sprite = new Sprite(879, 346, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_3_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mLa103Sprite);
		
		mLa1011Sprite = new Sprite(71, 618, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_11_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mLa1011Sprite);
		
		mLa1012Sprite = new Sprite(649, 215, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_12_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mLa1012Sprite);

		
		for(int i=0;i<mCanhHoaDuoiNuocSprite.length;i++){
			mCanhHoaDuoiNuocSprite[i]=new Sprite(pointCanhHoaDuoiNuoc[0][i], pointCanhHoaDuoiNuoc[1][i], mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_IPHONE_ID), getVertexBufferObjectManager());
			mScene.attachChild(mCanhHoaDuoiNuocSprite[i]);
			mCanhHoaDuoiNuocSprite[i].setVisible(false);
		}
		
		mStridersSprite = new AnimatedSprite(34, 342, mStridersTiledTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(mStridersSprite);

		fishSprite = new AnimatedSprite(854, 535, fishTiledTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(fishSprite);

		mBugSprite = new AnimatedSprite(435, 405, mBugTiledTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(mBugSprite);
		mBugSprite.setVisible(false);

		mClipLayer = new ClipLayer(0, 0, 0, 0, CLIP_WIDTH, CLIP_HEIGHT);
		mClipLayer.setCenterClip(CLIP_WIDTH/2, CLIP_HEIGHT/2);
		mScene.attachChild(mClipLayer);
		
		clipBg = new Sprite(0, 171, mClipBgTexture, getVertexBufferObjectManager());
		mClipLayer.attachChild(clipBg);
		
		mBongBoxClipSprite=new Sprite(190, 237, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_14_2_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mBongBoxClipSprite);
		
		mWaterSprite = new AnimatedSprite(94,242,mWaterTiledTextureRegion,getVertexBufferObjectManager());
		mScene.attachChild(mWaterSprite);
		mWaterSprite.setVisible(false);
		
		mBoxClipSprite = new Sprite(190, 237, mClipBoxTexture, getVertexBufferObjectManager());
		mScene.attachChild(mBoxClipSprite);
		
		mCotSprite = new Sprite(88, 207, mCotTexture, getVertexBufferObjectManager());
		mClipLayer.attachChild(mCotSprite);
		mScene.registerTouchArea(mCotSprite);
		
		saudat1319Sprite = new Sprite(575, 210, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_19_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(saudat1319Sprite);
		
		saudat1320Sprite = new Sprite(536, 337, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_20_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(saudat1320Sprite);
		
		mBangSprite = new Sprite(352, 299, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_8_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(mBangSprite);

		mBongSprite = new Sprite(679, 200, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_17_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(mBongSprite);
		
		fish134Sprite = new Sprite(651, 438, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_4_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish134Sprite);
		
		mCongTraiSprite = new Sprite(704, 387, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_3_1_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(mCongTraiSprite);
		
		mCongPhaiSprite = new Sprite(804, 387, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_3_2_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(mCongPhaiSprite);
		
		fish131Sprite = new Sprite(327, 362, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_1_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish131Sprite);
		
		fish132Sprite = new Sprite(791, 422, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_2_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish132Sprite);
		
		fish135Sprite = new Sprite(763, 530, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_5_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish135Sprite);

		fish136Sprite = new Sprite(506, 402, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_6_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish136Sprite);

		fish137Sprite = new Sprite(436, 446, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_7_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish137Sprite);
		
		fish139Sprite = new Sprite(226, 420, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_9_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish139Sprite);
		
		fish1310Sprite = new Sprite(123, 418, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_10_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1310Sprite);
		
		fish1311Sprite = new Sprite(15, 429, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_11_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1311Sprite);
		
		fish1313Sprite = new Sprite(181, 250, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_13_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1313Sprite);
		
		fish1314Sprite = new Sprite(86, 225, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_14_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1314Sprite);
		fish1314Sprite.setVisible(false);
		
		fish1315Sprite = new Sprite(585, 255, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_15_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1315Sprite);
		
		fish1316Sprite = new Sprite(720, 251, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_16_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(fish1316Sprite);
		
		saudat1318Sprite = new Sprite(546, 558, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_18_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(saudat1318Sprite);
		
		saudat1321Sprite = new Sprite(593, 546, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_13_21_IPHONE_ID), getVertexBufferObjectManager());
		mClipLayer.attachChild(saudat1321Sprite);
		
		mGiunSprite =new AnimatedSprite(174, 20, giunTiledTextureRegion, getVertexBufferObjectManager());
		sprbgHeaderLeft.attachChild(mGiunSprite);
		
		saudat451Sprite = new Sprite(271, 166, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_5_1_IPHONE_ID), getVertexBufferObjectManager());
		sprbgHeaderLeft.attachChild(saudat451Sprite);
		
		saudat452Sprite = new Sprite(271, 166, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_5_2_IPHONE_ID), getVertexBufferObjectManager());
		sprbgHeaderLeft.attachChild(saudat452Sprite);

		mCuonchieuSprite =new AnimatedSprite(558, 74, cuonchieuTiledTextureRegion, getVertexBufferObjectManager());
		sprbgHeaderLeft.attachChild(mCuonchieuSprite);
		
		mCayHoaSprite = new Sprite(705, 0, mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_1_8_IPHONE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mCayHoaSprite);
		
		for(int i=0;i<mCanhHoaTrenCaySprite.length;i++){
			mCanhHoaTrenCaySprite[i]=new Sprite(pointCanhHoaTrenCay[0][i], pointCanhHoaTrenCay[1][i], mTextureAll.getTexturePackTextureRegionLibrary().get(i+39), getVertexBufferObjectManager());
			mScene.attachChild(mCanhHoaTrenCaySprite[i]);
		}

		for(int i=0;i<mCanhHoaSprite.length;i++){
			mCanhHoaSprite[i]=new Sprite(pointCanhHoaTrenCay[0][i], pointCanhHoaTrenCay[1][i],  mTextureAll.getTexturePackTextureRegionLibrary().get(Vol3MedakaResource.A4_10_IPHONE_ID), getVertexBufferObjectManager());
			mScene.attachChild(mCanhHoaSprite[i]);
			mCanhHoaSprite[i].setVisible(false);
			}
		
		rectangle = new Rectangle(0, 0, CLIP_WIDTH, CLIP_HEIGHT, getVertexBufferObjectManager());
		rectangle.setColor(1.0f, 0.0f, 0.0f);
		rectangle.setAlpha(0.0f);
		mScene.attachChild(rectangle);
		rectangle.setVisible(false);

		mEchSprite =new AnimatedSprite(282, 25, echTiledTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(mEchSprite);
	}

	//Gimmic
	@Override
	public void combineGimmic3WithAction() {
		Log.d(LOG, "+++++++++++ COMBINE GIMMIC C3 WITH ACTION.");
	}

	float dX = 0, dY = 0;
	boolean checkMove = false;
	
	//Scene Touch Event
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		//Log.d(LOG, "+++++++++++ ON SCENE TOUCH EVENT.");
		final int pX = (int)pTouchEvent.getX();
		int pY = (int)pTouchEvent.getY();
		//Move
		if(pTouchEvent.isActionMove()){
			//Move Clip
			if(mClipLayer.isVisible()&& checkContainsPolygon(mBoxClipSprite, arrPointBox, 8, pX, pY) &&!checkWater&& Math.abs(((int)pTouchEvent.getX() + (int)pTouchEvent.getY()) - (lastX + lastY)) >=10)
				{
				if(checkech){
					float x = pX - dX;
					float y = pY - dY;
					if(x < -280 ) x = -280;
					else if (x > 680) x= 680;
					
					if(y < 170) y = 170;
					else if( y > 500) y = 500;
					
					mClipLayer.setClip( x + 280,y + 175);
					mBoxClipSprite.setPosition(x, y);
					
					if(!checkMove){
						checkMove = true;
						OGG_A4_14_CHAP2.play();
						mScene.registerUpdateHandler(soundTimer = new TimerHandler(5f, new ITimerCallback() {
							@Override
							public void onTimePassed(TimerHandler arg0) {
								checkMove = false;
							}
						}));
					}
				}
				}
		}else {
			//Down
			if(pTouchEvent.isActionDown()){
			//Log.d(LOG, "+++++++++++ ON SCENE TOUCH EVENT IS ACTION DOWN.");
			lastX = pX;
			lastY = pY;
			dX = lastX - mBoxClipSprite.getX();
			dY = lastY - mBoxClipSprite.getY();
			
			// Teacher Fish
			if(checking(fish131Sprite, pX, pY)&&fish131Sprite.contains(pX, pY)&&checkfish131&&mClipLayer.isVisible()&&pX<425){
				OGG_A4_13_KYU.play();
				switch (randSound.nextInt(3)) {
				case 0:
					playSoundDelay(OGG_A4_13_VO1, 0.5f);
					break;
				case 1:
					playSoundDelay(OGG_A4_13_VO2, 0.5f);
					break;
				default:
					playSoundDelay(OGG_A4_13_VO3, 0.5f);
					break;
				}
				fish131Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish131=false;
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish131=true;
						}
					},
					new MoveYModifier(1.2f, fish131Sprite.getmYFirst(), fish131Sprite.getmYFirst()-25),
					new MoveYModifier(1.2f, fish131Sprite.getmYFirst()-25, fish131Sprite.getmYFirst())));
			}
			
			//Student bag blue
			if(checking(fish132Sprite, pX, pY)&&fish132Sprite.contains(pX, pY)&&checkfish132&&mClipLayer.isVisible()){
				fish132Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				mCongTraiSprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				
				checkfish132=false;
				
				OGG_A4_13_HYOI.play();
				countFish++;
				if(countFish%2==0){
					playSoundDelay(OGG_A4_13_ITEKIMASU, 0.5f);
				}else{
					playSoundDelay(OGG_A4_13_ITEKIMASU2, 0.5f);
				}
				
				Path path = new Path(4).to(791, 422).to(720, 410).to(492, 332).to(-190, 270);
				fish132Sprite.registerEntityModifier(new PathModifier(8.0f, path, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkfish132=true;
						fish132Sprite.setPosition(791, 422);
					}
				}, EaseLinear.getInstance()));
			}
			
			// Student bag red
			if(checking(fish134Sprite, pX, pY)&&fish134Sprite.contains(pX, pY)&&mClipLayer.isVisible()&&checkfish134&&!fish132Sprite.contains(pX, pY)){
				fish134Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				mCongTraiSprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				
				checkfish134=false;
				
				OGG_A4_13_HYOI.play();
				countFish++;
				if(countFish%2==0){
					playSoundDelay(OGG_A4_13_ITEKIMASU, 0.5f);
				}else{
					playSoundDelay(OGG_A4_13_ITEKIMASU2, 0.5f);
				}
				
				Path path = new Path(3).to(651, 438).to(564, 405).to(-190, 380);
				fish134Sprite.registerEntityModifier(new PathModifier(8.0f, path, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkfish134=true;
						fish134Sprite.setPosition(651, 438);
					}
				}));
			}
			
			// Student bag brown
			if(checking(fish135Sprite, pX, pY)&&fish135Sprite.contains(pX, pY)&&checkfish135&&mClipLayer.isVisible()){
				fish135Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				mCongTraiSprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				
				checkfish135=false;
				
				OGG_A4_13_HYOI.play();
				countFish++;
				if(countFish%2==0){
					playSoundDelay(OGG_A4_13_ITEKIMASU, 0.5f);
				}else{
					playSoundDelay(OGG_A4_13_ITEKIMASU2, 0.5f);
				}
				
				Path path = new Path(3).to(763, 530).to(693, 464).to(-190, 490);
				fish135Sprite.registerEntityModifier(new PathModifier(8.0f, path, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkfish135=true;
						fish135Sprite.setPosition(763, 530);
					}
				}));
			}
			
			//Fish a4_13_6_iphone
			if(checking(fish136Sprite, pX, pY)&&fish136Sprite.contains(pX, pY)&&checkfish136&&!fish137Sprite.contains(pX, pY)&&mClipLayer.isVisible()){
				processA4_13_6_7(fish136Sprite);
			}
			
			//Fish a4_13_7_iphone
			if(checking(fish137Sprite, pX, pY)&&fish137Sprite.contains(pX, pY)&&checkfish137&&mClipLayer.isVisible()){
				processA4_13_6_7(fish137Sprite);
			}
			
			//Fish dance green
			if(checking(fish139Sprite, pX, pY)&&fish139Sprite.contains(pX, pY)&&checkfish139&&mClipLayer.isVisible()){
				fish139Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish139=false;
							OGG_A4_13_HAWAI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							fish139Sprite.registerEntityModifier(new DelayModifier(1.3f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									checkfish139=true;
								}
							}));
							fish139Sprite.setPosition(fish139Sprite.getmXFirst(),fish139Sprite.getmYFirst());
						}
					},
					new MoveXModifier(0.2f, fish139Sprite.getmXFirst(), fish139Sprite.getmXFirst()-20),
					new MoveXModifier(0.4f, fish139Sprite.getmXFirst()-20, fish139Sprite.getmXFirst()+20),
					new MoveXModifier(0.3f, fish139Sprite.getmXFirst()+20, fish139Sprite.getmXFirst()-15),
					new MoveXModifier(0.25f, fish139Sprite.getmXFirst()-15, fish139Sprite.getmXFirst() +15),
					new MoveXModifier(0.2f, fish139Sprite.getmXFirst()+15, fish139Sprite.getmXFirst()-10f),
					new MoveXModifier(0.15f, fish139Sprite.getmXFirst()-10f, fish139Sprite.getmXFirst()+10),
					new MoveXModifier(0.1f, fish139Sprite.getmXFirst()+10, fish139Sprite.getmXFirst()-5),
					new MoveXModifier(0.15f, fish139Sprite.getmXFirst()-5, fish139Sprite.getmXFirst()+5),
					new MoveXModifier(0.05f, fish139Sprite.getmXFirst()+5, fish139Sprite.getmXFirst())));
			}
			
			//Fish dance blue
			if(checking(fish1310Sprite, pX, pY)&&fish1310Sprite.contains(pX, pY)&&checkfish1310&&mClipLayer.isVisible()){
				fish1310Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish1310=false;
							OGG_A4_13_HAWAI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							fish1310Sprite.registerEntityModifier(new DelayModifier(1.3f , new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									checkfish1310=true;
								}
							}));
							fish1310Sprite.setPosition(fish1310Sprite.getmXFirst(),fish1310Sprite.getmYFirst());
						}
					},
					new MoveXModifier(0.2f, fish1310Sprite.getmXFirst(), fish1310Sprite.getmXFirst()-20),
					new MoveXModifier(0.4f, fish1310Sprite.getmXFirst()-20, fish1310Sprite.getmXFirst()+20),
					new MoveXModifier(0.3f, fish1310Sprite.getmXFirst()+20, fish1310Sprite.getmXFirst()-15),
					new MoveXModifier(0.25f, fish1310Sprite.getmXFirst()-15, fish1310Sprite.getmXFirst() +15),
					new MoveXModifier(0.2f, fish1310Sprite.getmXFirst()+15, fish1310Sprite.getmXFirst()-10f),
					new MoveXModifier(0.15f, fish1310Sprite.getmXFirst()-10f, fish1310Sprite.getmXFirst()+10),
					new MoveXModifier(0.1f, fish1310Sprite.getmXFirst()+10, fish1310Sprite.getmXFirst()-5),
					new MoveXModifier(0.15f, fish1310Sprite.getmXFirst()-5, fish1310Sprite.getmXFirst()+5),
					new MoveXModifier(0.05f, fish1310Sprite.getmXFirst()+5, fish1310Sprite.getmXFirst())));
			}
			
			//Fish dance orange
			if(checking(fish1311Sprite, pX, pY)&&fish1311Sprite.contains(pX, pY)&&checkfish1311&&mClipLayer.isVisible()){
				fish1311Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish1311=false;
							OGG_A4_13_HAWAI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							fish1311Sprite.registerEntityModifier(new DelayModifier(1.3f , new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									checkfish1311=true;
								}
							}));
							fish1311Sprite.setPosition(fish1311Sprite.getmXFirst(),fish1311Sprite.getmYFirst());
						}
					},
					new MoveXModifier(0.2f, fish1311Sprite.getmXFirst(), fish1311Sprite.getmXFirst()-20),
					new MoveXModifier(0.4f, fish1311Sprite.getmXFirst()-20, fish1311Sprite.getmXFirst()+20),
					new MoveXModifier(0.3f, fish1311Sprite.getmXFirst()+20, fish1311Sprite.getmXFirst()-15),
					new MoveXModifier(0.25f, fish1311Sprite.getmXFirst()-15, fish1311Sprite.getmXFirst() +15),
					new MoveXModifier(0.2f, fish1311Sprite.getmXFirst()+15, fish1311Sprite.getmXFirst()-10f),
					new MoveXModifier(0.15f, fish1311Sprite.getmXFirst()-10f, fish1311Sprite.getmXFirst()+10),
					new MoveXModifier(0.1f, fish1311Sprite.getmXFirst()+10, fish1311Sprite.getmXFirst()-5),
					new MoveXModifier(0.15f, fish1311Sprite.getmXFirst()-5, fish1311Sprite.getmXFirst()+5),
					new MoveXModifier(0.05f, fish1311Sprite.getmXFirst()+5, fish1311Sprite.getmXFirst())));
			}
			
			//Cot do chieu cao
			if(checking(mCotSprite, pX, pY) && !fish1314Sprite.isVisible()&&mClipLayer.isVisible()
					||checking(fish1313Sprite, pX, pY) && !fish1314Sprite.isVisible()&&mClipLayer.isVisible()){
				fish1314Sprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						fish1314Sprite.setVisible(true);
						OGG_A4_13_NU_VO.play();
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						fish1314Sprite.setVisible(false);
						OGG_A4_13_NU_VO.stop();
					}
				},
				new MoveXModifier(1.0f, 0 - fish1314Sprite.getWidth(), fish1314Sprite.getmXFirst()),
				new DelayModifier(3.0f)));
			}
			
			//Fish head down
			if(checking(fish1315Sprite, pX, pY)&&fish1315Sprite.contains(pX, pY)&&checkbong&&mClipLayer.isVisible()){
				OGG_A4_13_15_16.play();
				fish1315Sprite.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new RotationModifier(0.00001f, 0, -100),
						new MoveModifier(0.3f, fish1315Sprite.getmXFirst(), fish1315Sprite.getmXFirst()+18, fish1315Sprite.getmYFirst(), fish1315Sprite.getmYFirst()-21),
						new MoveModifier(0.3f, fish1315Sprite.getmXFirst()+18, fish1315Sprite.getmXFirst(), fish1315Sprite.getmYFirst()-21, fish1315Sprite.getmYFirst())),2
					));
				mBongSprite.registerEntityModifier(new LoopEntityModifier(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						checkbong=false;								
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						mBongSprite.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								mBongSprite.setPosition(mBongSprite.getmXFirst(),mBongSprite.getmYFirst());
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								fish1315Sprite.reset();
								fish1315Sprite.clearEntityModifiers();
								checkbong=true;
							}
						}));
					}},2,
					new ILoopEntityModifierListener() {
						@Override
						public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
						
						@Override
						public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
					},
					new SequenceEntityModifier(
						new MoveModifier(0.3f, mBongSprite.getmXFirst(), mBongSprite.getmXFirst(), mBongSprite.getmYFirst(), mBongSprite.getmYFirst()),
						new MoveModifier(0.3f, mBongSprite.getmXFirst(), mBongSprite.getmXFirst(), mBongSprite.getmYFirst(), mBongSprite.getmYFirst()-50,new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

							}
					}))));
				
				sprBongFake.registerEntityModifier(new LoopEntityModifier(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sprBongFake.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sprBongFake.setPosition(sprBongFake.getmXFirst(),sprBongFake.getmYFirst());
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
						}));
					}},2,
					new ILoopEntityModifierListener() {
						@Override
						public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
						
						@Override
						public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
					},
					new SequenceEntityModifier(
						new MoveModifier(0.3f, sprBongFake.getmXFirst(), sprBongFake.getmXFirst(), sprBongFake.getmYFirst(), sprBongFake.getmYFirst()),
						new MoveModifier(0.3f, sprBongFake.getmXFirst(), sprBongFake.getmXFirst(), sprBongFake.getmYFirst(), sprBongFake.getmYFirst()-50,new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

							}
					}))));
			}
			
			//Fish head top
			if(checking(fish1316Sprite, pX, pY)&&fish1316Sprite.contains(pX, pY)&&checkbong&&mClipLayer.isVisible()&&!fish134Sprite.contains(pX, pY)){
				OGG_A4_13_15_16.play();
				fish1316Sprite.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new MoveModifier(0.15f, fish1316Sprite.getmXFirst(), fish1316Sprite.getmXFirst()-12, fish1316Sprite.getmYFirst(), fish1316Sprite.getmYFirst()-20),
						new MoveModifier(0.3f, fish1316Sprite.getmXFirst()-12, fish1316Sprite.getmXFirst(), fish1316Sprite.getmYFirst()-20, fish1316Sprite.getmYFirst())),2));
				mBongSprite.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new MoveModifier(0.15f, mBongSprite.getmXFirst(), mBongSprite.getmXFirst(), mBongSprite.getmYFirst(), mBongSprite.getmYFirst()),
						new MoveModifier(0.3f, mBongSprite.getmXFirst(), mBongSprite.getmXFirst(), mBongSprite.getmYFirst(), mBongSprite.getmYFirst()-50,new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							}
						})
				),2,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						checkbong=false;
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						mBongSprite.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								mBongSprite.setPosition(mBongSprite.getmXFirst(),mBongSprite.getmYFirst());
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								checkbong=true;
							}
						}));
					}
				}));
				
				sprBongFake.registerEntityModifier(new LoopEntityModifier(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sprBongFake.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sprBongFake.setPosition(sprBongFake.getmXFirst(),sprBongFake.getmYFirst());
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
						}));
					}},2,
					new ILoopEntityModifierListener() {
						@Override
						public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
						
						@Override
						public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						}
					},
					new SequenceEntityModifier(
						new MoveModifier(0.15f, sprBongFake.getmXFirst(), sprBongFake.getmXFirst(), sprBongFake.getmYFirst(), sprBongFake.getmYFirst()),
						new MoveModifier(0.3f, sprBongFake.getmXFirst(), sprBongFake.getmXFirst(), sprBongFake.getmYFirst(), sprBongFake.getmYFirst()-50,new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

							}
					}))));
			}
			
			// Touch sau dat 1318
			if(checking(saudat1318Sprite, pX, pY)&&saudat1318Sprite.contains(pX, pY)&&checksaudat1318&&mClipLayer.isVisible()){
				saudat1318Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				saudat1318Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1318=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1318=true;
							OGG_A4_13_POMI.stop();
							saudat1318Sprite.setPosition(saudat1318Sprite.getmXFirst(),saudat1318Sprite.getmYFirst());
						}},
					new MoveXModifier(0.25f, saudat1318Sprite.getmXFirst(), saudat1318Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat1318Sprite.getmXFirst()-10, saudat1318Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat1318Sprite.getmXFirst()+8, saudat1318Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat1318Sprite.getmXFirst(), saudat1318Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat1318Sprite.getmXFirst()-7f, saudat1318Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat1318Sprite.getmXFirst()+5f, saudat1318Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat1318Sprite.getmXFirst(), saudat1318Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat1318Sprite.getmXFirst()-4, saudat1318Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat1318Sprite.getmXFirst()+2, saudat1318Sprite.getmXFirst())));
			}
			
			// Touch sau dat 1319
			if(checking(saudat1319Sprite, pX, pY)&&saudat1319Sprite.contains(pX, pY)&&checksaudat1319&&mClipLayer.isVisible()){
				saudat1319Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				saudat1319Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1319=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1319=true;
							OGG_A4_13_POMI.stop();
							saudat1319Sprite.setPosition(saudat1319Sprite.getmXFirst(),saudat1319Sprite.getmYFirst());
						}},
					new MoveXModifier(0.25f, saudat1319Sprite.getmXFirst(), saudat1319Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat1319Sprite.getmXFirst()-10, saudat1319Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat1319Sprite.getmXFirst()+8, saudat1319Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat1319Sprite.getmXFirst(), saudat1319Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat1319Sprite.getmXFirst()-7f, saudat1319Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat1319Sprite.getmXFirst()+5f, saudat1319Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat1319Sprite.getmXFirst(), saudat1319Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat1319Sprite.getmXFirst()-4, saudat1319Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat1319Sprite.getmXFirst()+2, saudat1319Sprite.getmXFirst())));
			}
			
			// Touch sau dat 1320
			if(checking(saudat1320Sprite, pX, pY)&&saudat1320Sprite.contains(pX, pY)&&checksaudat1320&&mClipLayer.isVisible()){
				saudat1320Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				saudat1320Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1320=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1320=true;
							OGG_A4_13_POMI.stop();
							saudat1320Sprite.setPosition(saudat1320Sprite.getmXFirst(),saudat1320Sprite.getmYFirst());
						}
					},
					new MoveXModifier(0.25f, saudat1320Sprite.getmXFirst(), saudat1320Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat1320Sprite.getmXFirst()-10, saudat1320Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat1320Sprite.getmXFirst()+8, saudat1320Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat1320Sprite.getmXFirst(), saudat1320Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat1320Sprite.getmXFirst()-7f, saudat1320Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat1320Sprite.getmXFirst()+5f, saudat1320Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat1320Sprite.getmXFirst(), saudat1320Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat1320Sprite.getmXFirst()-4, saudat1320Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat1320Sprite.getmXFirst()+2, saudat1320Sprite.getmXFirst())));
			}
			
			// Touch sau dat 1321
			if(checking(saudat1321Sprite, pX, pY)&&saudat1321Sprite.contains(pX, pY)&&checksaudat1321&&mClipLayer.isVisible()){
				saudat1321Sprite.setZIndex(mClipLayer.getLastChild().getZIndex()+1);
				mClipLayer.sortChildren();
				saudat1321Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1321=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat1321=true;
							OGG_A4_13_POMI.stop();
							saudat1321Sprite.setPosition(saudat1321Sprite.getmXFirst(),saudat1321Sprite.getmYFirst());
						}
					},
					new MoveXModifier(0.25f, saudat1321Sprite.getmXFirst(), saudat1321Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat1321Sprite.getmXFirst()-10, saudat1321Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat1321Sprite.getmXFirst()+8, saudat1321Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat1321Sprite.getmXFirst(), saudat1321Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat1321Sprite.getmXFirst()-7f, saudat1321Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat1321Sprite.getmXFirst()+5f, saudat1321Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat1321Sprite.getmXFirst(), saudat1321Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat1321Sprite.getmXFirst()-4, saudat1321Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat1321Sprite.getmXFirst()+2, saudat1321Sprite.getmXFirst())));
			}
			
			// Touch sau dat 451
			if(saudat451Sprite.contains(pX, pY)&&checksaudat451&&pX>290){
				Log.d(LOG, "saudat451Sprite x : "+pX);
				saudat451Sprite.setZIndex(sprbgHeaderLeft.getLastChild().getZIndex()+1);
				sprbgHeaderLeft.sortChildren();
				saudat451Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
							
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat451=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							saudat451Sprite.setPosition(saudat451Sprite.getmXFirst(), saudat451Sprite.getmYFirst());
							checksaudat451=true;
						}
					},
					new MoveXModifier(0.25f, saudat451Sprite.getmXFirst(), saudat451Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat451Sprite.getmXFirst()-10, saudat451Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat451Sprite.getmXFirst()+8, saudat451Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat451Sprite.getmXFirst(), saudat451Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat451Sprite.getmXFirst()-7f, saudat451Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat451Sprite.getmXFirst()+5f, saudat451Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat451Sprite.getmXFirst(), saudat451Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat451Sprite.getmXFirst()-4, saudat451Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat451Sprite.getmXFirst()+2, saudat451Sprite.getmXFirst())));
			}
			
			//Touch sau dat 452
			if(saudat452Sprite.contains(pX, pY)&&checksaudat452&&pX<=290){
				Log.d(LOG, "saudat452Sprite x : "+pX);
				saudat452Sprite.setZIndex(sprbgHeaderLeft.getLastChild().getZIndex()+1);
				sprbgHeaderLeft.sortChildren();
				saudat452Sprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checksaudat452=false;
							OGG_A4_13_POMI.play();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							saudat452Sprite.setPosition(saudat452Sprite.getmXFirst(), saudat452Sprite.getmYFirst());
							checksaudat452=true;
						}
					},
					new MoveXModifier(0.25f, saudat452Sprite.getmXFirst(), saudat452Sprite.getmXFirst()-10),
					new MoveXModifier(0.45f, saudat452Sprite.getmXFirst()-10, saudat452Sprite.getmXFirst()+8),
					new MoveXModifier(0.20f, saudat452Sprite.getmXFirst()+8, saudat452Sprite.getmXFirst()),
					new MoveXModifier(0.15f, saudat452Sprite.getmXFirst(), saudat452Sprite.getmXFirst()-7f),
					new MoveXModifier(0.35f, saudat452Sprite.getmXFirst()-7f, saudat452Sprite.getmXFirst()+5f),
					new MoveXModifier(0.10f, saudat452Sprite.getmXFirst()+5f, saudat452Sprite.getmXFirst()),
					new MoveXModifier(0.05f, saudat452Sprite.getmXFirst(), saudat452Sprite.getmXFirst()-4),
					new MoveXModifier(0.1f, saudat452Sprite.getmXFirst()-4, saudat452Sprite.getmXFirst()+2),
					new MoveXModifier(0.05f, saudat452Sprite.getmXFirst()+2, saudat452Sprite.getmXFirst())));
			}
			
			//Touch cuon chieu
			if(mCuonchieuSprite.contains(pX, pY)&&checkcuonchieu){
				int iFrames[] = { 0, 1 };
				long lDurations[] = { 0, 3000 };
				runAnimatedSprite(lDurations, iFrames, mCuonchieuSprite, 0);
			}
			
			//Touch Giun
			if(checkContains(sprbgHeaderLeft, 196, 61, 275, 142, pX, pY) &&checkgiun&&!mCuonchieuSprite.contains(pX, pY)){
				int iFrames[] = { 0, 1, 2 };
				long lDurations[] = { 200, 500, 800 };
				runAnimatedSprite(lDurations, iFrames, mGiunSprite, 0);
			}
			
			//Touch Ech
			if(checkContains(mEchSprite, 107, 0, 218, 137, pX, pY) && checkech && pX<470 && pX>370 && pY<140){
				touchEch();
			}
			
			//Touch mBoxClipSprite
			if(mBoxClipSprite.contains(pX, pY)){
				mBongBoxClipSprite.setVisible(false);
				mBongBoxClipSprite.clearEntityModifiers();
				mBoxClipSprite.clearEntityModifiers();
				mBugSprite.setVisible(true);
				mClipLayer.clearEntityModifiers();
				if(checkWater){
					OGG_A4_14_CHAPON.play();
					int iFrames[] = { 0, 1 };
					long lDurations[] = { 250, 250 };
					runAnimatedSprite(lDurations, iFrames, mWaterSprite, 0);
					mScene.sortChildren();
				}else{
					
				}
			}
			
			//Touch Fish Tea
			if(fishSprite.contains(pX, pY)&&checkfish&&!mBoxClipSprite.contains(pX, pY)){
				fishSprite.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(0.5f, fishSprite.getmXFirst(), 800, fishSprite.getmYFirst(), 500,new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							OGG_A4_8_FUNA.play();
							checkfish=false;
							fishSprite.setCurrentTileIndex(1);
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						}
					}),
					new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checkfish=true;
							OGG_A4_8_FUNA.stop();
							fishSprite.setCurrentTileIndex(0);
							fishSprite.setPosition(fishSprite.getmXFirst(), fishSprite.getmYFirst());							
						}
					})));
			}
			
			//Touch Strider
			if(mStridersSprite.contains(pX, pY)&&checkstrider&&!mBoxClipSprite.contains(pX, pY)&&pX<226&&pY<541){
				Log.d(LOG, "mStridersSprite x tap : "+pX);
				Log.d(LOG, "mStridersSprite y tap : "+pY);
				mStridersSprite.registerEntityModifier(new SequenceEntityModifier(
					new DelayModifier(0.5f,new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkstrider=false;
							OGG_A4_8_FUNA.play();
							mStridersSprite.setCurrentTileIndex(1);
							mStridersSprite.animate(350);
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							OGG_A4_8_FUNA.play();
						}}),
					new DelayModifier(0.5f,new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mStridersSprite.stopAnimation();
							mStridersSprite.setCurrentTileIndex(0);
						}
					}),
					new DelayModifier(1.0f,new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checkstrider=true;	
						}
					})));
			}
			
			//Touch Bug
			if(mBugSprite.contains(pX, pY)&&checkbug&&!mBoxClipSprite.contains(pX, pY)&&pY<555&&pX>500){
				Log.d(LOG, "mBugSprite x tap : "+pX);
				Log.d(LOG, "mBugSprite y tap : "+pY);
				mBugSprite.registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							checkbug=true;
							OGG_A4_9_GENGORO.stop();
						}
					},
					new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							checkbug=false;
							OGG_A4_9_GENGORO.play();
							mBugSprite.setCurrentTileIndex(1);
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mBugSprite.setCurrentTileIndex(0);
						}
					}),
					new DelayModifier(1.0f)));
			}
			
			// Touch Cay hoa
			if(mCayHoaSprite.contains(pX, pY)&&checkCayHoa){
				for(int i=0;i<mCanhHoaSprite.length;i++){
					mCanhHoaSprite[i].setRotation(30*i);
					mCanhHoaDuoiNuocSprite[i].setRotation(30*i);
					mCanhHoaSprite[i].setVisible(true);
					mCanhHoaSprite[i].registerEntityModifier(
						new MoveModifier(2.0f, mCanhHoaSprite[i].getmXFirst(), pointCanhHoaDuoiNuoc[0][i], mCanhHoaSprite[i].getmYFirst(), pointCanhHoaDuoiNuoc[1][i],new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								checkCayHoa=false;
								OGG_A4_10_SAKURA.play();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								for(int j=0;j<mCanhHoaSprite.length;j++){
									mCanhHoaDuoiNuocSprite[j].setVisible(true);
									mCanhHoaSprite[j].setVisible(false);
									mCanhHoaDuoiNuocSprite[j].registerEntityModifier(
										new MoveXModifier(9.0f, mCanhHoaDuoiNuocSprite[j].getmXFirst(), mCanhHoaDuoiNuocSprite[j].getmXFirst()-900,new IEntityModifierListener() {
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												
											}
											
											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												for(int z=0;z<mCanhHoaSprite.length;z++){
													mCanhHoaSprite[z].setPosition(mCanhHoaSprite[z].getmXFirst(), mCanhHoaSprite[z].getmYFirst());
													mCanhHoaDuoiNuocSprite[z].setPosition(mCanhHoaDuoiNuocSprite[z].getmXFirst(), mCanhHoaDuoiNuocSprite[z].getmYFirst());
													mCanhHoaDuoiNuocSprite[z].setVisible(false);
													mCanhHoaDuoiNuocSprite[z].setRotation(0);
													mCanhHoaSprite[z].setRotation(0);
													checkCayHoa=true;
												}
											}
										}));
									}
								}
							}	
						));
					}
				}
			}
		}
		return false;
	}
	
	// method checking rectangle with box
	public boolean checking(RectangularShape myShape, int pX, int pY){
		if(myShape.contains(pX, pY) == true && rectangle.contains(pX, pY) == true){
			return true;
		}
		return false;
	}
	
	@Override
	public synchronized void onSurfaceChanged(GLState pGLState, int pWidth,
			int pHeight) {
		super.onSurfaceChanged(pGLState, pWidth, pHeight);
		Log.d(LOG, "+++++++++++ ON SURFACE CHANGED.");
		Log.i(LOG, "pWidth: " + pWidth + " pHeight: " + pHeight);
		TRUE_WIDTH = pWidth;
		TRUE_HEIGHT = pHeight;
		
//		if(mClipLayer != null){
//			Log.i(LOG, "mClipLayer Not Null !");
//			mScene.registerUpdateHandler(
//					new TimerHandler(0.1f
//					,new ITimerCallback() {
//				
//				@Override
//				public void onTimePassed(TimerHandler arg0) {
//					mClipLayer.setClip(CLIP_WIDTH, CAMERA_HEIGHT);
//					mBoxClipSprite.setPosition(CLIP_WIDTH - mBoxClipSprite.getWidth()/2, CAMERA_HEIGHT - 170f);
//					rectangle.setPosition(CLIP_WIDTH, CAMERA_HEIGHT);
//						mBoxClipSprite.registerEntityModifier(
//								new LoopEntityModifier(
//										new SequenceEntityModifier(
//												new MoveXModifier(0.1f, mBoxClipSprite.getmXFirst(), mBoxClipSprite.getmXFirst()-5)
//												,new MoveXModifier(0.1f, mBoxClipSprite.getmXFirst()-5, mBoxClipSprite.getmXFirst()+5)
//												,new MoveXModifier(0.1f, mBoxClipSprite.getmXFirst()+5, mBoxClipSprite.getmXFirst())
//												),-1
//										)
//								);
//						mBongBoxClipSprite.registerEntityModifier(new LoopEntityModifier(
//								new SequenceEntityModifier(
//										new MoveXModifier(0.1f, mBongBoxClipSprite.getmXFirst(), mBongBoxClipSprite.getmXFirst()-5)
//										,new MoveXModifier(0.1f, mBongBoxClipSprite.getmXFirst()-5, mBongBoxClipSprite.getmXFirst()+5)
//										,new MoveXModifier(0.1f, mBongBoxClipSprite.getmXFirst()+5, mBongBoxClipSprite.getmXFirst())
//										),-1
//								));
//				}
//			}));
//		}
		}
	
	//==========================================================
	// Method
	//==========================================================
	
	public void runningGame(final Sprite mSprite) {
		Log.d(LOG, "+++++++++++ METHOD RUNNING GAME.");
		mClipLayer.registerEntityModifier(
			new LoopEntityModifier(
				new MoveXModifier(2.08f, 0, 0,new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
						if (mSprite.isVisible()) {mSprite.registerEntityModifier(
							new LoopEntityModifier(new SequenceEntityModifier(
								new MoveXModifier(0.1f, mSprite.getmXFirst(), mSprite.getmXFirst()-8),
								new MoveXModifier(0.1f, mSprite.getmXFirst()-8, mSprite.getmXFirst()+8),
								new MoveXModifier(0.1f, mSprite.getmXFirst()+8, mSprite.getmXFirst())),2));
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
						mSprite.setPosition(mSprite.getmXFirst(),mSprite.getmYFirst());}}), -1 ));
	}
	
	private void laMoveX(final Sprite mSprite){
		float time = Math.abs(mSprite.getmXFirst() + mSprite.getWidth() * 2) / 40;
		mSprite.registerEntityModifier(
			new MoveXModifier(time, mSprite.getmXFirst(), -40,new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mSprite.registerEntityModifier(new LoopEntityModifier(
						new MoveXModifier(50.0f, 1000, mSprite.getX()), -1 ));
				}
			}));
	}
	
	private void laMove(final Sprite mSprite){
		Log.d(LOG, "+++++++++++ METHOD LA MOVE.");
		float time = Math.abs(mSprite.getmXFirst() + mSprite.getWidth() * 2) / 100 + 15f;
		mSprite.registerEntityModifier( 
			new SequenceEntityModifier(
				new MoveXModifier(time, mSprite.getmXFirst(), -110f,new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						mSprite.registerEntityModifier(new LoopEntityModifier(
							new MoveXModifier(40.5f, 1070f, mSprite.getX()), -1 ));
					}
				})
			));
	}
	private void touchEch(){
		mEchSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
		mScene.sortChildren();
		
		checkech=false;
		OGG_A4_2_KAERU.play();
		
		mEchSprite.setCurrentTileIndex(1);
		
		pointEch++;
		if(checkWater){
			if(pointEch % 2 ==0){
				mEchSprite.setRotation(-20);
				mEchSprite.registerEntityModifier(
					new MoveModifier(0.5f, mEchSprite.getmXFirst(), 630, mEchSprite.getmYFirst(), 100, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mEchSprite.setRotation(0);
							mEchSprite.setCurrentTileIndex(2);
							mStridersSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							mBugSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							fishSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							mClipLayer.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							mBoxClipSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							mCayHoaSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
							mScene.sortChildren();
							for(int i=0;i<mCanhHoaTrenCaySprite.length;i++){
								mCanhHoaTrenCaySprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mCanhHoaSprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
							}
							
							mScene.unregisterUpdateHandler(echTimer);
							echTimer = new TimerHandler(1.5f, new ITimerCallback() {
								
								@Override
								public void onTimePassed(TimerHandler arg0) {
									mEchSprite.setCurrentTileIndex(0);
									mEchSprite.setPosition(282, 25);
									checkech = true;
								}
							});
							mScene.registerUpdateHandler(echTimer);
						}
					}));
			}else {
				mEchSprite.setRotation(20);
				mEchSprite.registerEntityModifier(
						new MoveModifier(0.5f, mEchSprite.getmXFirst(), -50, mEchSprite.getmYFirst(), 100, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								mEchSprite.setRotation(0);
								mEchSprite.setCurrentTileIndex(2);
								mStridersSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mBugSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								fishSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mClipLayer.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mBoxClipSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mCayHoaSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								for(int i=0;i<mCanhHoaTrenCaySprite.length;i++){
									mCanhHoaTrenCaySprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mCanhHoaSprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
								}
								
								mScene.unregisterUpdateHandler(echTimer);
								echTimer = new TimerHandler(1.5f, new ITimerCallback() {
									
									@Override
									public void onTimePassed(TimerHandler arg0) {
										mEchSprite.setCurrentTileIndex(0);
										mEchSprite.setPosition(282, 25);
										checkech = true;
									}
								});
								mScene.registerUpdateHandler(echTimer);
							}
						}));
				}
			} else {
				if(mBoxClipSprite.getX() <= 190) {
					mEchSprite.setRotation(-20);
					mEchSprite.registerEntityModifier(
						new MoveModifier(0.5f, mEchSprite.getmXFirst(), 630, mEchSprite.getmYFirst(), 100, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								mEchSprite.setRotation(0);
								mEchSprite.setCurrentTileIndex(2);
								mStridersSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mBugSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								fishSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mClipLayer.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mBoxClipSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								mCayHoaSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
								mScene.sortChildren();
								for(int i=0;i<mCanhHoaTrenCaySprite.length;i++){
									mCanhHoaTrenCaySprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mCanhHoaSprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
								}
								
								mScene.unregisterUpdateHandler(echTimer);
								echTimer = new TimerHandler(1.5f, new ITimerCallback() {
									
									@Override
									public void onTimePassed(TimerHandler arg0) {
										mEchSprite.setCurrentTileIndex(0);
										mEchSprite.setPosition(282, 25);
										checkech = true;
									}
								});
								mScene.registerUpdateHandler(echTimer);
							}
						}));
				}
				
				else if(mBoxClipSprite.getX() > 190) {
					mEchSprite.setRotation(20);
					mEchSprite.registerEntityModifier(
							new MoveModifier(0.5f, mEchSprite.getmXFirst(), -50, mEchSprite.getmYFirst(), 100, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									mEchSprite.setRotation(0);
									mEchSprite.setCurrentTileIndex(2);
									mStridersSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mBugSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									fishSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mClipLayer.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mBoxClipSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									mCayHoaSprite.setZIndex(mScene.getLastChild().getZIndex()+1);
									mScene.sortChildren();
									for(int i=0;i<mCanhHoaTrenCaySprite.length;i++){
										mCanhHoaTrenCaySprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
										mScene.sortChildren();
										mCanhHoaSprite[i].setZIndex(mScene.getLastChild().getZIndex()+1);
										mScene.sortChildren();
									}
									
									mScene.unregisterUpdateHandler(echTimer);
									echTimer = new TimerHandler(1.5f, new ITimerCallback() {
										
										@Override
										public void onTimePassed(TimerHandler arg0) {
											mEchSprite.setCurrentTileIndex(0);
											mEchSprite.setPosition(282, 25);
											checkech = true;
										}
									});
									mScene.registerUpdateHandler(echTimer);
								}
							}));
				}
			}
		}
	
	private void runAnimatedSprite(final long lDurations[],
			final int iFrames[], final AnimatedSprite anis, final int iRepeat) {
		Log.d(LOG, "+++++++++++ METHOD RUN ANIMATED SPRITE.");
		anis.animate(lDurations, iFrames, iRepeat,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				if(anis.equals(mGiunSprite)){
					checkgiun=false;
					OGG_A4_3_MIMIZU.play();
				}
				
				if(anis.equals(mWaterSprite)){
					mWaterSprite.setVisible(true);
					if(mWaterSprite.isVisible()){
						mClipLayer.setClip(mBoxClipSprite.getX()+280,mBoxClipSprite.getY()+170f);
						mClipLayer.setVisible(true);
						checkWater=false;
					}
				}
				
				if(anis.equals(mCuonchieuSprite)){
					mCuonchieuSprite.setCurrentTileIndex(1);
					mCuonchieuSprite.setWidth(cuonchieuTiledTextureRegion.getWidth(1));
					mCuonchieuSprite.setRotationCenter(mCuonchieuSprite.getWidth()/2, mCuonchieuSprite.getHeight()/2);
					
					final Path path = new Path(5).to(558, 72).to(465,90).to(306, 72).to(165, 90).to(-95, 72);

					mCuonchieuSprite.registerEntityModifier(
						new SequenceEntityModifier(
							new MoveXModifier(0.5f, mCuonchieuSprite.getmXFirst(), mCuonchieuSprite.getmXFirst(),
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									checkcuonchieu=false;
									OGG_A4_4_DANGOMUSHI.play();
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A4_4_DANGOMUSHI.stop();
								}
							}),
						new ParallelEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A3_14_KORO.play();
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A3_14_KORO.stop();
							
									checkcuonchieu=true;
									mCuonchieuSprite.setCurrentTileIndex(0);
									mCuonchieuSprite.setWidth(cuonchieuTiledTextureRegion.getWidth(0));
									mCuonchieuSprite.setPosition(mCuonchieuSprite.getmXFirst(), mCuonchieuSprite.getmYFirst());
								}
							}
							,new PathModifier(2.5f, path)
							,new RotationModifier(2.5f, 0, -360*4.0f))));
				}
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				if(anis.equals(mGiunSprite)){
					checkgiun=true;
					mGiunSprite.setCurrentTileIndex(0);
					OGG_A4_3_MIMIZU.stop();
				}
				
				if(anis.equals(mWaterSprite)){
					mWaterSprite.setVisible(false);
				}
			}
		});
	}
	
	public void processA4_13_6_7(final Sprite mSprite){
		Log.d(LOG, "+++++++++++ METHOD PROCESS A4_13_6_7.");
		switch (randSound.nextInt(2)) {
		case 0:
			mSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					if(mSprite.equals(fish136Sprite)){
					checkfish136=false;
					}else {
						checkfish137=false;
					}
					switch (randSound.nextInt(5)) {
						case 0:
							OGG_A4_13_SEITO1.play();
							break;
						case 1:
							OGG_A4_13_SEITO2.play();
							break;
						case 2:
							OGG_A4_13_SEITO3.play();
							break;
						case 3:
							OGG_A4_13_SEITO4.play();
							break;
						default:
							OGG_A4_13_SEITO5.play();
							break;
						}
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					if(mSprite.equals(fish136Sprite)){
						checkfish136=true;
					}else{
							checkfish137=true;
					}
				}
				},
				new MoveYModifier(0.5f, mSprite.getmYFirst(), mSprite.getmYFirst()-20),
				new MoveYModifier(0.5f, mSprite.getmYFirst()-20, mSprite.getmYFirst())));
			break;
		default:
			mSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					if(mSprite.equals(fish136Sprite)){
						checkfish136=false;
					}else{
						checkfish137=false;
					}
					switch (randSound.nextInt(5)) {
						case 0:
							OGG_A4_13_SEITO1.play();
							break;
						case 1:
							OGG_A4_13_SEITO2.play();
							break;
						case 2:
							OGG_A4_13_SEITO3.play();
							break;
						case 3:
							OGG_A4_13_SEITO4.play();
							break;
						default:
							OGG_A4_13_SEITO5.play();
							break;
						}
					}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					if(mSprite.equals(fish136Sprite)){
						checkfish136=true;
						}else{
							checkfish137=true;
						}
					}
				},
				new MoveXModifier(0.5f, mSprite.getmXFirst(), mSprite.getmXFirst()-20),
				new MoveXModifier(0.5f, mSprite.getmXFirst()-20, mSprite.getmXFirst()),
				new MoveXModifier(0.5f, mSprite.getmXFirst(), mSprite.getmXFirst()-20),
				new MoveXModifier(0.5f, mSprite.getmXFirst()-20, mSprite.getmXFirst())
			));
			break;
		}
	}
	
	//==========================================================
	// ClipLayer class
	//==========================================================
	
	@SuppressLint("NewApi")
	public class ClipLayer extends Entity {
		private float clipX;
		private float clipY;
		private int clipWidth;
		private int clipHeight;
		
		private int clipXcenter = 0;
		private int clipYcenter = 0;
		
		private float radioWidth = 0;
		private float radioHeight = 0;
		
		public ClipLayer(float x, float y,float pclipX, float pclipY, int pwidth, int pheight) {
			super(x, y);
			this.clipWidth = pwidth;
			this.clipHeight = pheight;
			this.clipX = pclipX;
			this.clipY = pclipY;
		}

		public int getClipWidth() {
			return clipWidth;
		}

		public void setClipWidth(int clipWidth) {
			this.clipWidth = clipWidth;
		}

		public int getClipHeight() {
			return clipHeight;
		}

		public void setClipHeight(int clipHeight) {
			this.clipHeight = clipHeight;
		}
		
		public void setCenterClip(int pX, int pY){
			this.clipXcenter = pX;
			this.clipYcenter = pY;
		}
		
		public void setClip(float pX, float pY){
			this.clipX = pX;
			this.clipY = pY;
			rectangle.setPosition(pX - rectangle.getWidth()/2, pY - rectangle.getHeight()/2);
		}
		
		public Rectangle getBox(){
			return rectangle;
		}
		
		public float getBoxX(){
			return clipX - clipXcenter;
		}
		
		public float getBoxY(){
			return clipY - clipYcenter;
		}

		@Override
		protected void onManagedDraw(GLState pGLState, Camera pCamera) {
			radioWidth  =  (float)TRUE_WIDTH / pCamera.getWidth();
			radioHeight =  (float)TRUE_HEIGHT / pCamera.getHeight();
			pGLState.pushProjectionGLMatrix();
			enableScissorTest();
//			pGLState.enableScissorTest();
			final int y = (int) (pCamera.getSurfaceHeight() - (clipHeight*radioHeight) - clipY*radioHeight);
			final int x = (int)(clipX * radioWidth);
			GLES20.glScissor((int)(x  - clipXcenter * radioWidth),(int)(y + clipYcenter * radioHeight), (int) (getClipWidth() * radioWidth), (int) (getClipHeight() * radioHeight));
			super.onManagedDraw(pGLState, pCamera);
			disableScissorTest();
			//pGLState.disableScissorTest();
			pGLState.popProjectionGLMatrix();
		}
	}
	private boolean mScissorTestEnabled = false;
	
	private boolean enableScissorTest(){
		if(this.mScissorTestEnabled) {
			return true;
			}

		this.mScissorTestEnabled = true;
		GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
		return false;
	}
	
	public boolean disableScissorTest() {
		if(!this.mScissorTestEnabled) {
			return false;
		}
		
		this.mScissorTestEnabled = false;
		GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
		return true;
		}

	@Override
	public synchronized void onResumeGame() {
		Log.d(LOG, "+++++++++++ ON RESUME GAME");
		mBugSprite.setVisible(false);
		pointEch = 0;
		laMoveX(mLa101Sprite);
		laMoveX(mLa102Sprite);
		laMoveX(mLa103Sprite);
		laMove(mLa1011Sprite);
		laMove(mLa1012Sprite);
		runningGame(mBoxClipSprite);
		runningGame(mBongBoxClipSprite);
		randSound=new Random();
		super.onResumeGame();
		checkMove = false;
	}

	@Override
	public synchronized void onPauseGame() {
		Log.d(LOG, "+++++++++++ ON PAUSE GAME");
		resetData();
		stopSound();
		super.onPauseGame();
	}
	public void resetData(){
		Log.d(LOG, "+++++++++++ METHOD RESET DATA.");
		mScene.unregisterUpdateHandler(soundTimer);
		mScene.unregisterUpdateHandler(echTimer);
		pointEch = 0;
		checkfish131=true;
		checkfish132=true;
		checkfish134=true;
		checkfish135=true;
		checkfish139=true;
		checkfish1310=true;
		checkfish1311=true;
		checkbong=true;
		checksaudat1318=true;
		checksaudat1319 = true;
		checksaudat1320 = true;
		checksaudat1321=true;
		checksaudat451=true;
		checksaudat452=true;
		checkgiun=true;
		checkcuonchieu=true;
		checkech=true;
		checkfish=true;
		checkWater=true;
		checkstrider=true;
		checkbug=true;
		checkCayHoa=true;
		
		if(mBoxClipSprite != null){
			mBoxClipSprite.clearEntityModifiers();
			mBoxClipSprite.setPosition(190, 237);
		}
		
		if(mBongBoxClipSprite != null){
			mBongBoxClipSprite.clearEntityModifiers();
			mBongBoxClipSprite.setPosition(190, 237);
			mBongBoxClipSprite.setVisible(true);	
		}
		
		if(mClipLayer != null){
			mClipLayer.clearEntityModifiers();
			mClipLayer.setCenterClip(CLIP_WIDTH/2, CLIP_HEIGHT/2);
			mClipLayer.setVisible(false);	
		}
		
		if(fish131Sprite != null){
			fish131Sprite.clearEntityModifiers();
			fish131Sprite.setPosition(327, 362);
		}
		
		if(fish132Sprite != null){
			fish132Sprite.clearEntityModifiers();
			fish132Sprite.setPosition(791, 422);
		}
		
		if(fish134Sprite != null){
			fish134Sprite.clearEntityModifiers();
			fish134Sprite.setPosition(651, 438);
		}
		
		if(fish135Sprite != null){
			fish135Sprite.clearEntityModifiers();
			fish135Sprite.setPosition(763, 530);	
		}
	
		if(fish136Sprite != null){
			fish136Sprite.clearEntityModifiers();
			fish136Sprite.setPosition(506, 402);	
		}
		
		if(fish137Sprite != null){
			fish137Sprite.clearEntityModifiers();
			fish137Sprite.setPosition(436, 446);	
		}
		
		if(fish139Sprite != null){
			fish139Sprite.clearEntityModifiers();
			fish139Sprite.setPosition(226, 420);	
		}
	
		if(fish1310Sprite != null){
			fish1310Sprite.clearEntityModifiers();
			fish1310Sprite.setPosition(123, 418);	
		}
	
		if(fish1311Sprite != null){
			fish1311Sprite.clearEntityModifiers();
			fish1311Sprite.setPosition(15, 429);	
		}
	
		if(fish1314Sprite != null){
			fish1314Sprite.clearEntityModifiers();
			fish1314Sprite.setPosition(86, 225);
			fish1314Sprite.setVisible(false);	
		}
		
		if(fish1316Sprite != null){
			fish1316Sprite.clearEntityModifiers();
			fish1316Sprite.setPosition(720, 221);	
		}
		
		if(fish1315Sprite != null){
			fish1315Sprite.clearEntityModifiers();
			fish1315Sprite.reset();
			fish1315Sprite.setPosition(585, 255);
		}
	
		if(mBongSprite != null){
			mBongSprite.clearEntityModifiers();
			mBongSprite.setPosition(679, 200);
		}
		
		if(sprBongFake != null){
			sprBongFake.clearEntityModifiers();
			sprBongFake.setPosition(679, 200);
		}
	
		if(mLa101Sprite != null){
			mLa101Sprite.clearEntityModifiers();
			mLa101Sprite.setPosition(310, 538);
		}
		
		if(mLa102Sprite != null){
			mLa102Sprite.clearEntityModifiers();
			mLa102Sprite.setPosition(60, 225);
		}
		
		if(mLa103Sprite != null){
			mLa103Sprite.clearEntityModifiers();
			mLa103Sprite.setPosition(879, 346);	
		}
		
		if(mLa1011Sprite != null){
			mLa1011Sprite.clearEntityModifiers();
			mLa1011Sprite.setPosition(71, 618);		
		}
		
		if(mLa1012Sprite != null){
			mLa1012Sprite.clearEntityModifiers();
			mLa1012Sprite.setPosition(649, 215);
		}
		
		if(saudat1318Sprite != null){
			saudat1318Sprite.clearEntityModifiers();
			saudat1318Sprite.setPosition(546, 558);
		}
		if(saudat1319Sprite != null){
			saudat1319Sprite.clearEntityModifiers();
			saudat1319Sprite.setPosition(575, 210);
		}
		if(saudat1320Sprite != null){
			saudat1320Sprite.clearEntityModifiers();
			saudat1320Sprite.setPosition(536, 337);
		}
	
		if(saudat1321Sprite != null){
			saudat1321Sprite.clearEntityModifiers();
			saudat1321Sprite.setPosition(593, 546);
		}
	
		if(saudat451Sprite != null){
			saudat451Sprite.clearEntityModifiers();
			saudat451Sprite.setPosition(saudat451Sprite);
		}
	
		if(saudat452Sprite != null){
			saudat452Sprite.clearEntityModifiers();
			saudat452Sprite.setPosition(271, 166);
		}
	
		if(fishSprite != null){
			fishSprite.clearEntityModifiers();
			fishSprite.setPosition(854, 535);
			fishSprite.setCurrentTileIndex(0);	
		}
	
		if(mStridersSprite != null){
			mStridersSprite.clearEntityModifiers();
			mStridersSprite.setPosition(34, 342);
			mStridersSprite.stopAnimation();
			mStridersSprite.setCurrentTileIndex(0);	
		}

		if(mBugSprite != null){
			mBugSprite.clearEntityModifiers();
			mBugSprite.setPosition(435, 405);
			mBugSprite.stopAnimation();
			mBugSprite.setCurrentTileIndex(0);	
		}
		
		if(mBangSprite != null){
			mBangSprite.clearEntityModifiers();
			mBangSprite.setPosition(352, 299);	
		}
		
		if(mCuonchieuSprite != null){
			mCuonchieuSprite.stopAnimation();
			mCuonchieuSprite.clearEntityModifiers();
			mCuonchieuSprite.resetRotationCenter();
			mCuonchieuSprite.reset();
			mCuonchieuSprite.setCurrentTileIndex(0);
			mCuonchieuSprite.setWidth(cuonchieuTiledTextureRegion.getWidth(0));
			mCuonchieuSprite.setPosition(558, 74);
		}
		
		if(mEchSprite != null){
			mEchSprite.stopAnimation();
			mEchSprite.clearEntityModifiers();
			mEchSprite.setCurrentTileIndex(0);
			mEchSprite.setPosition(282, 25);
		}
		
		if(mGiunSprite != null){
			mGiunSprite.stopAnimation();
			mGiunSprite.clearEntityModifiers();
			mGiunSprite.setCurrentTileIndex(0);
			mGiunSprite.setPosition(174, 20);	
		}

		countFish = 2;
		
		for(int i=0;i<mCanhHoaSprite.length;i++){
			if(mCanhHoaSprite[i] != null) {
				mCanhHoaSprite[i].clearEntityModifiers();
				mCanhHoaSprite[i].setPosition(pointCanhHoaDuoiNuoc[0][i], pointCanhHoaDuoiNuoc[1][i]);
				mCanhHoaSprite[i].setVisible(false);
			}
		}
		
		for(int i=0;i<mCanhHoaDuoiNuocSprite.length;i++){
			if(mCanhHoaDuoiNuocSprite != null){
				mCanhHoaDuoiNuocSprite[i].clearEntityModifiers();
				mCanhHoaDuoiNuocSprite[i].setPosition(pointCanhHoaDuoiNuoc[0][i], pointCanhHoaDuoiNuoc[1][i]);
				mCanhHoaDuoiNuocSprite[i].setVisible(false);
			}
		}
	}
	
	public void stopSound(){
		Log.d(LOG, "+++++++++++ METHOD STOP SOUND.");
		OGG_A3_14_KORO.stop();
		OGG_A4_10_SAKURA.stop();
		OGG_A4_13_15_16.stop();
		OGG_A4_13_HAWAI.stop();
		OGG_A4_13_HYOI.stop();
		OGG_A4_13_ITEKIMASU.stop();
		OGG_A4_13_ITEKIMASU2.stop();
		OGG_A4_13_KYU.stop();
		OGG_A4_13_NU_VO.stop();
		OGG_A4_13_POMI.stop();
		OGG_A4_13_SEITO1.stop();
		OGG_A4_13_SEITO2.stop();
		OGG_A4_13_SEITO3.stop();
		OGG_A4_13_SEITO4.stop();
		OGG_A4_13_SEITO5.stop();
		OGG_A4_13_VO1.stop();
		OGG_A4_13_VO2.stop();
		OGG_A4_13_VO3.stop();
		OGG_A4_14_CHAP2.stop();
		OGG_A4_14_CHAPON.stop();
		OGG_A4_2_KAERU.stop();
		OGG_A4_3_MIMIZU.stop();
		OGG_A4_4_DANGOMUSHI.stop();
		OGG_A4_8_FUNA.stop();
		OGG_A4_9_GENGORO.stop();
	}
}
