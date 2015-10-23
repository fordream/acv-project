package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Random;


import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3BokukoshiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseQuartOut;

import android.util.Log;

public class Vol3Bokukoshi extends BaseGameActivity implements IOnSceneTouchListener, IEntityModifierListener {
	private static final String TAG ="BOKUKOSHI";
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TexturePack ttpBokukoshi1, ttpBokukoshi2, ttpBokukoshi3, ttpBokukoshi4;
	
	private TexturePackTextureRegionLibrary tttpLibBokukoshi1, tttpLibBokukoshi2, tttpLibBokukoshi3;
	
	private TextureRegion ttrStage1;
	private TextureRegion ttrTreeStage1;
	private TextureRegion ttrSkyStage2;
	private TextureRegion ttrStage2Bg;
	private TextureRegion ttrStage2TreeBg1;
	private TextureRegion ttrStage2TreeBg2;
	private TextureRegion ttrTree1Stage2;
	private TextureRegion ttrTree2Stage2;
	private TextureRegion ttrSearch;
	private TextureRegion ttrRing;
	private TextureRegion ttrHouse1;
	private TextureRegion ttrWater;
	private TextureRegion ttrHouse2;
	private TextureRegion ttrBall;
	private TextureRegion ttrUfo;
	private TextureRegion ttrFlag;
	private TextureRegion ttrGray;
	
	private Sprite sprStage1; 
	private Sprite sprTreeStage1;
	private Sprite sprSkyStage2;
	private Sprite sprStage2Bg;
	private Sprite sprStage2TreeBg1;
	private Sprite sprStage2TreeBg2;
	private Sprite sprTree1Stage2;
	private Sprite sprTree2Stage2;
	private Sprite sprSearch; 
	private Sprite[] sprRing = new Sprite[5];
	private Sprite sprHouse1;
	private Sprite sprWater;
	private Sprite sprHouse2;
	private Sprite sprBall;
	private Sprite sprUfo;
	private Sprite sprFlag;
	private Sprite sprGray[] = new Sprite[5];
	
	private ITiledTextureRegion tttrDataSearch[] = new TiledTextureRegion[52];
	private ITiledTextureRegion tttrCharacter0;
	private ITiledTextureRegion tttrCharacter1;
	private ITiledTextureRegion tttrCharacter2;
	private ITiledTextureRegion tttrCharacter3;
	private ITiledTextureRegion tttrSceneFinish;
	private ITiledTextureRegion tttrFlower;
	private ITiledTextureRegion tttrBalloon;
	private ITiledTextureRegion tttrBear;
	private ITiledTextureRegion tttrBabyCake;
	private ITiledTextureRegion tttrChicken;
	private ITiledTextureRegion tttrBabyOctopus;
	private ITiledTextureRegion tttrFrog;
	private ITiledTextureRegion tttrBird;
	private ITiledTextureRegion tttrDoor;
	private ITiledTextureRegion tttrBeetle;
	private ITiledTextureRegion tttrFlag;
	private ITiledTextureRegion tttrMonkey;
	private ITiledTextureRegion tttrFootball;
	private ITiledTextureRegion tttrClown;
	private ITiledTextureRegion tttrThrowPaper;
	private ITiledTextureRegion tttrBoysurprise;
	private ITiledTextureRegion tttrBabyHappy;
	private ITiledTextureRegion tttrBabyBalloon;
	private ITiledTextureRegion tttrBabyDrum;
	private ITiledTextureRegion tttrBoyBalloon;
	private ITiledTextureRegion tttrBangBlue;
	private ITiledTextureRegion tttrBangYellow;
	private ITiledTextureRegion tttrBongbay[] = new ITiledTextureRegion[11];
	
	private AnimatedSpriteCharacter animsprCharacter[] = new AnimatedSpriteCharacter[52];
	private AnimatedSprite animsprDataSearch[] = new AnimatedSprite[52];
	private AnimatedSprite animsprSceneFinish ;
	private AnimatedSprite animsprFlower;
	private AnimatedSprite animsprBalloon;
	private AnimatedSprite animsprBear;
	private AnimatedSprite animsprBabyCake;
	private AnimatedSprite animsprChicken;
	private AnimatedSprite animsprBabyOctopus;
	private AnimatedSprite animsprFrog;
	private AnimatedSprite animsprBird;
	private AnimatedSprite animsprDoor;
	private AnimatedSprite animsprBeetle;
	private AnimatedSprite animsprFlag;
	private AnimatedSprite animsprMonkey;
	private AnimatedSprite animsprFootball;
	private AnimatedSprite animsprClown;
	private AnimatedSprite animsprThrowPaper;
	private AnimatedSprite animsprBoysurprise;
	private AnimatedSprite animsprBabyHappy;
	private AnimatedSprite animsprBabyBalloon;
	private AnimatedSprite animsprBabyDrum;
	private AnimatedSprite animsprBoyBalloon;
	private AnimatedSprite animsprBangBlue;
	private AnimatedSprite animsprBangYellow;
	private AnimatedSprite animsprBongbay[] = new AnimatedSprite[11];
	
	private int rdCharacterStage1[] = new int[24];
	private int rdCharacterStage2[] = new int[42];
	private int rdChastage2[] = new int[4];
	private int rdBongbay[] = new int [11];
	private int valueGrayStage1,valueGrayStage2, zIndex = 0;
	private int isStage;
	
	private ArrayList<Integer> arraySearch = new ArrayList<Integer>(5);
	
	private float arrCharacterPointStage1[][] = {{0,90},{815,80},{127,130},{400,130},{680,130},{265,150},{540,150}
		,{0,235},{820,200},{130,290},   {451,391}   ,{715,290},{280,295},{0,345},{575,320},{835,330},{220,385},{130,440},
		{320,445},{595,445},{840,445},{730,445}, {0,450},{445,540}};
	
	private float arrCharacterPointStage2[][] = {
		{495,130},{700,152},{770,160},{542,165},{895,205},{595,210},{826,216},{652,225},{765,229},{555,222},
		{2,245},{64,256},{294,261},{226,264},{896,270},{436,284},{728,287},{662,294},{0,321},
		{255,337},{599,338},{310,357},{64,365},{195,375},{3,397},{129,399},{257,414},{496,434},{63,459},{187,460},
		{1,473},{124,475},{325,510},{64,544},{408,544},{279,545},{1,545},{132,545},{356,545},{614,545},{835,545},
		{685,545}};
	
	private float arrChaStage2 [][] = {{162,273},{829,350},{201,590},{771,590}};
	
	private float arrPointBongbay[][] = {{870,110},{320,140},{77,150},{495,245},{890,375},{435,380},{375,330},{624,415},
		{815,425},{222,530},{915,540}};
	
	private float arrCharacter0stage2[][] = {{817,135}};
	
	private float arrCharacter2stage2[][] = {{555,460}};
	
	private float arrCharacter50stage2[][] = {{425,425}};
	
	private float arrCharacter1stage2[][]= {{165,206}};
	
	private float arrCharacter3stage2[][]= {{230,206}};
	
	private float arrPointGray[][] = {{0,0},{125,0},{250,0},{375,0},{500,0}};
	
	private float pointPolygonBalloon[][]={
		{ 0, 10, 30, 88, 150, 160, 100, 40, 30, 0 },
		{ 160, 100, 39, 0, 4, 40, 30, 100, 165, 160 } };

	private boolean isTouchDataSearch;
	private boolean isTouchsBabyCake;
	private boolean isTouchChicken;
	private boolean isTouchBabytOctopus;
	private boolean isTouchFlower;
	private boolean isTouchBear;
	private boolean isTouchBalloon;
	private boolean isTouchFrog;
	private boolean isTouchBird;
	private boolean isTouchDoor;
	private boolean isTouchBall;
	private boolean isTouchBeetle;
	private boolean isTouchFlag;
	private boolean isTouchMonkey;
	private boolean isTouchFootball;
	private boolean isTouchClown;
	private boolean isTouchThrowPaper;
	private boolean isTouchBoysurprise;
	private boolean isTouchBabyHappy;
	private boolean isTouchBabyBalloon;
	private boolean isTouchBabyDrum;
	private boolean isTouchBoyBalloon;
	private boolean isTouchBangBlue;
	private boolean isTouchBangYellow;
	private boolean isTouchCharacter[] = new boolean[52];
	private boolean isTouchBongbay[] = new boolean[11];
	
	private boolean beginGame;
	
	private DelayModifier[] delayCharacter = new DelayModifier[52];
	private DelayModifier delayBabycake;
	private DelayModifier delayChicken;
	private DelayModifier delayBabytOctopus;
	private DelayModifier delayFlower;
	private DelayModifier delayBear;
	private DelayModifier delayBalloon;
	private DelayModifier delayFrog;
	private DelayModifier delayBird;
	private DelayModifier delayDoor;
	private DelayModifier delayFlag;
	private DelayModifier delayFootball;
	private DelayModifier delayClown;
	private DelayModifier delayThrowPaper;
	private DelayModifier delayBoysurprise;
	private DelayModifier delayBabyHappy;
	private DelayModifier delayBabyBalloon;
	private DelayModifier delayBabyDrum;
	private DelayModifier delayBoyBalloon;
	
	private ParallelEntityModifier parallUfo;
	
	RanDomNoRepeat rdnSearch , rdnCharacter, rdnBongbay, rdnCharacter46;
	
	TimerHandler rtime;
	
	private Sound OGG_A5_10_KIRAN,OGG_A5_14_TENTOMUSHI,OGG_A5_15_KOINOBORI,OGG_A5_21_1_OMEDETO,OGG_A5_21_2_OMEDETO_BIG,
	OGG_A5_22_1_NYUN,OGG_A5_23_KAERU,OGG_A5_24_25_WARERU,OGG_A5_26_HYU,OGG_A5_27_HYOI,OGG_A5_27_MOGU,OGG_A5_28_EREKI,
	OGG_A5_29_BALL,OGG_A5_30_PIERO,OGG_A5_31_BASA,OGG_A5_32_15_UMA,OGG_A5_32_16_ZOU,OGG_A5_33_ZOU,OGG_A5_34_KUMA,
	OGG_A5_35_PIYO,OGG_A5_36_37_SUBERU,OGG_A5_38_AGERU,OGG_A5_39_KODAIKO2,OGG_A5_3_1_UFO,OGG_A5_5_KUMARAKU,
	OGG_A5_6_CHUN,OGG_A5_7_KESYOU,OGG_A5_7_KIRA4,OGG_A5_9_KIRA,OGG_A5_HYOI,OGG_A5_HYU1,OGG_A5_HYU2,OGG_A5_HYU3,
	OGG_A5_JAJAN,OGG_A5_KOREDOKO,OGG_A5_MUZUKASHI,OGG_A5_PINPON,OGG_A5_POMI,OGG_A5_POWAN;
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("bokukoshi/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("bokukoshi/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
			getTextureManager(), pAssetManager, "bokukoshi/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		//Library
		ttpBokukoshi1 = mTexturePackLoaderFromSource.load("bokukoshi.xml");
		ttpBokukoshi1.loadTexture();
		tttpLibBokukoshi1 = ttpBokukoshi1.getTexturePackTextureRegionLibrary();
		
		ttpBokukoshi2 = mTexturePackLoaderFromSource.load("bokukoshi2.xml");
		ttpBokukoshi2.loadTexture();
		tttpLibBokukoshi2 = ttpBokukoshi2.getTexturePackTextureRegionLibrary();
		
		ttpBokukoshi3 = mTexturePackLoaderFromSource.load("bokukoshi3.xml");
		ttpBokukoshi3.loadTexture();
		tttpLibBokukoshi3 = ttpBokukoshi3.getTexturePackTextureRegionLibrary();
		
		ttpBokukoshi4 = mTexturePackLoaderFromSource.load("bokukoshi4.xml");
		ttpBokukoshi4.loadTexture();
		
		//BackGround 1
		ttrStage1 = tttpLibBokukoshi1.get(Vol3BokukoshiResource.A5_20_2_IPHONE_ID);
		
		// Tree Stage 1
		ttrTreeStage1 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_20_3_IPHONE_ID);
		
		//BackGround 2
		ttrSkyStage2 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_21_1_IPHONE_ID);
		ttrStage2Bg = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_20_1_IPHONE_ID);
		ttrStage2TreeBg1 = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_18_1_IPHONE_ID);
		ttrStage2TreeBg2 = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_13_1_IPHONE_ID);
		
		//Tree Stage2
		ttrTree1Stage2 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_17_1_IPHONE_ID);
		ttrTree2Stage2 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_19_1_IPHONE_ID);
		
		//House1
		ttrHouse1 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_16_1_IPHONE_ID);
		
		//Flag
		tttrFlag = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprFlag);
		ttrFlag = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_15_1_IPHONE_ID);
		
		//Beetle
		tttrBeetle = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprBeetle);
		
		//Ball
		ttrBall = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_11_1_IPHONE_ID);
		
		//Flower
		tttrFlower = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprFlower);
		
		//Water
		ttrWater = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_12_4_IPHONE_ID);
		
		//Frog
		tttrFrog = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprFrog);
		
		//Balloon
		tttrBalloon = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprBalloon);
		
		//Football
		tttrFootball = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprFootball);
		
		//ThrowPaper
		tttrThrowPaper = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprBoythrowpaper);
		
		//Clown
		tttrClown = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprClown);
		
		//Boysurprise
		tttrBoysurprise = getTiledTextureFromPacker(ttpBokukoshi4,Vol3BokukoshiResource.A5_32_27_IPHONE_ID);
		
		//BabyHappy
		tttrBabyHappy = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.A5_32_38_IPHONE_ID);
		
		//BabyBalloon
		tttrBabyBalloon = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.A5_32_41_IPHONE_ID);
		
		//House 2
		ttrHouse2 = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_8_1_IPHONE_ID);
		
		//Monkey
		tttrMonkey = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprMonkey);
		
		//Door
		tttrDoor = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprDoor);
		
		//Bird
		tttrBird = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprBird);
		
		//Bear
		tttrBear = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.iAnisprBear);
		
		//Baby Cake
		tttrBabyCake = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.A5_32_29_IPHONE_ID);
		
		//Baby Drum
		tttrBabyDrum = getTiledTextureFromPacker(ttpBokukoshi3, Vol3BokukoshiResource.A5_32_45_IPHONE_ID);
		
		//Boy Balloon
		tttrBoyBalloon = getTiledTextureFromPacker(ttpBokukoshi1, Vol3BokukoshiResource.iAnisprBoyBalloon);
		
		//Chicken
		tttrChicken = getTiledTextureFromPacker(ttpBokukoshi1, Vol3BokukoshiResource.iAnisprChicken);
		
		//BabyOctopus
		tttrBabyOctopus = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprBabyOctopus);
		
		//UFO
		ttrUfo = tttpLibBokukoshi2.get(Vol3BokukoshiResource.A5_3_1_IPHONE_ID);
		
		//Data Search
		for (int i = 0; i < tttrDataSearch.length; i++) {
			tttrDataSearch[i] = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.dataSearch[i]);
		}
		
		//Character
		tttrCharacter0 = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprCharacter0);
		tttrCharacter1 = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprCharacter1);
		tttrCharacter2 = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprCharacter2);
		tttrCharacter3 = getTiledTextureFromPacker(ttpBokukoshi4, Vol3BokukoshiResource.iAnisprCharacter3);

		//Search
		ttrSearch = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_1_1_IPHONE_ID);
		
		// Bong bay
		for (int i = 0; i < tttrBongbay.length; i++) {
			tttrBongbay[i] = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprBongbay[i]);
		}
		
		//Bang Blue - Yellow
		tttrBangBlue = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprBangBlue);
		tttrBangYellow = getTiledTextureFromPacker(ttpBokukoshi2, Vol3BokukoshiResource.iAnisprBangYellow);
		
		//Gray Data Search
		ttrGray = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A5_1_2_IPHONE_ID);
		
		//Ring
		ttrRing = tttpLibBokukoshi3.get(Vol3BokukoshiResource.A2_40_IPHONE_ID);
		
		//Scene Finish
		tttrSceneFinish = getTiledTextureFromPacker(ttpBokukoshi1, Vol3BokukoshiResource.iAnisprFinish);
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A5_10_KIRAN = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_10_KIRAN);
		OGG_A5_14_TENTOMUSHI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_14_TENTOMUSHI);
		OGG_A5_15_KOINOBORI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_15_KOINOBORI);
		OGG_A5_21_1_OMEDETO = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_21_1_OMEDETO);
		OGG_A5_21_2_OMEDETO_BIG = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_21_2_OMEDETO_BIG);
		OGG_A5_22_1_NYUN = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_22_1_NYUN);
		OGG_A5_23_KAERU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_23_KAERU);
		OGG_A5_24_25_WARERU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_24_25_WARERU);
		OGG_A5_26_HYU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_26_HYU);
		OGG_A5_27_HYOI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_27_HYOI);
		OGG_A5_27_MOGU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_27_MOGU);
		OGG_A5_28_EREKI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_28_EREKI);
		OGG_A5_29_BALL = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_29_BALL);
		OGG_A5_30_PIERO = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_30_PIERO);
		OGG_A5_31_BASA = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_31_BASA);
		OGG_A5_32_15_UMA = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_32_15_UMA);
		OGG_A5_32_16_ZOU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_32_16_ZOU);
		OGG_A5_33_ZOU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_33_ZOU);
		OGG_A5_34_KUMA = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_34_KUMA);
		OGG_A5_35_PIYO = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_35_PIYO);
		OGG_A5_36_37_SUBERU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_36_37_SUBERU);
		OGG_A5_38_AGERU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_38_AGERU);
		OGG_A5_39_KODAIKO2 = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_39_KODAIKO2);
		OGG_A5_3_1_UFO = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_3_1_UFO);
		OGG_A5_5_KUMARAKU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_5_KUMARAKU);
		OGG_A5_6_CHUN = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_6_CHUN);
		OGG_A5_7_KESYOU = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_7_KESYOU);
		OGG_A5_7_KIRA4 = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_7_KIRA4);
		OGG_A5_9_KIRA = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_9_KIRA);
		OGG_A5_HYOI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_HYOI);
		OGG_A5_HYU1 = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_HYU1);
		OGG_A5_HYU2 = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_HYU2);
		OGG_A5_HYU3 = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_HYU3);
		OGG_A5_JAJAN = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_JAJAN);
		OGG_A5_KOREDOKO = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_KOREDOKO);
		OGG_A5_MUZUKASHI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_MUZUKASHI);
		OGG_A5_PINPON = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_PINPON);
		OGG_A5_POMI = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_POMI);
		OGG_A5_POWAN = loadSoundResourceFromSD(Vol3BokukoshiResource.A5_POWAN);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		//Back Ground Stage 1
		sprStage1 = new Sprite(0, 0, ttrStage1, this.getVertexBufferObjectManager());
		mScene.attachChild(sprStage1);
		
		//BackGround Stage 2
		sprSkyStage2 = new Sprite(0, 0, ttrSkyStage2, this.getVertexBufferObjectManager());
		mScene.attachChild(sprSkyStage2);
		sprSkyStage2.setVisible(false);
		
		sprStage2Bg = new Sprite(0, 137, ttrStage2Bg, this.getVertexBufferObjectManager());
		mScene.attachChild(sprStage2Bg);
		sprStage2Bg.setVisible(false);
		
		sprStage2TreeBg1 = new Sprite(0, 0, ttrStage2TreeBg1, this.getVertexBufferObjectManager());
		mScene.attachChild(sprStage2TreeBg1);
		sprStage2TreeBg1.setVisible(false);
		
		sprStage2TreeBg2 = new Sprite(0, 0, ttrStage2TreeBg2, this.getVertexBufferObjectManager());
		mScene.attachChild(sprStage2TreeBg2);
		sprStage2TreeBg2.setVisible(false);
		
		//Baby Cake
		animsprBabyCake = new AnimatedSprite(320, 110, tttrBabyCake, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBabyCake);
		animsprBabyCake.setVisible(false);

		//Chicken
		animsprChicken = new AnimatedSprite(342, 209, tttrChicken, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprChicken);
		animsprChicken.setVisible(false);
		
		//Tree
		sprTree1Stage2 = new Sprite(286, 71, ttrTree1Stage2, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTree1Stage2);
		sprTree1Stage2.setVisible(false);
		
		sprTree2Stage2 = new Sprite(732, 39, ttrTree2Stage2, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTree2Stage2);
		sprTree2Stage2.setVisible(false);
		
		//House 1
		sprHouse1 = new Sprite(4, 64, ttrHouse1, this.getVertexBufferObjectManager());
		mScene.attachChild(sprHouse1);
		sprHouse1.setVisible(false);
		
		//BabyDrum
		animsprBabyDrum = new AnimatedSprite(80, 320, tttrBabyDrum, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBabyDrum);
		animsprBabyDrum.setVisible(false);
		
		//Flag
		animsprFlag = new AnimatedSprite(160, 17, tttrFlag, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFlag);
		animsprFlag.setVisible(false);
		
		sprFlag = new Sprite(750, 333, ttrFlag, this.getVertexBufferObjectManager());
		mScene.attachChild(sprFlag);
		sprFlag.setVisible(false);
		
		//Beetle
		animsprBeetle = new AnimatedSprite(190, 130, tttrBeetle, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBeetle);
		animsprBeetle.setVisible(false);
		
		//Football
		animsprFootball = new AnimatedSprite(480, 360, tttrFootball, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFootball);
		animsprFootball.setVisible(false);
		
		//ThrowPaper
		animsprThrowPaper = new AnimatedSprite(797, 374, tttrThrowPaper, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprThrowPaper);
		animsprThrowPaper.setVisible(false);
		
		//BabyBalloon - 1 flame
		animsprBabyBalloon = new AnimatedSprite(777, 245, tttrBabyBalloon, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBabyBalloon);
		animsprBabyBalloon.setVisible(false);
		
		//Clown
		animsprClown = new AnimatedSprite(440, 500, tttrClown, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprClown);
		animsprClown.setVisible(false);
		
		//Boysurprise
		animsprBoysurprise = new AnimatedSprite(560, 100, tttrBoysurprise, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBoysurprise);
		animsprBoysurprise.setVisible(false);
		
		//Boy Balloon
		animsprBoyBalloon = new AnimatedSprite(300, 347, tttrBoyBalloon, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBoyBalloon);
		animsprBoyBalloon.setVisible(false);
		
		//Balloon
		animsprBalloon = new AnimatedSprite(556, 364, tttrBalloon, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBalloon);
		animsprBalloon.setVisible(false);
		
		//House 2
		sprHouse2 = new Sprite(623, 339, ttrHouse2, this.getVertexBufferObjectManager());
		mScene.attachChild(sprHouse2);
		sprHouse2.setVisible(false);
		
		//Monkey
		animsprMonkey = new AnimatedSprite(662, 400, tttrMonkey, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprMonkey);
		animsprMonkey.setVisible(false);
		
		//Door
		animsprDoor = new AnimatedSprite(705, 490, tttrDoor, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprDoor);
		animsprDoor.setVisible(false);
		
		//Bird
		animsprBird = new AnimatedSprite(614, 510, tttrBird, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBird);
		animsprBird.setVisible(false);
		
		// Bear
		animsprBear = new AnimatedSprite(486, 469, tttrBear, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBear);
		animsprBear.setVisible(false);
		
		//BabyOctopus
		animsprBabyOctopus = new AnimatedSprite(620, 140, tttrBabyOctopus, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBabyOctopus);
		animsprBabyOctopus.setVisible(false);
		
		// Anim Character
		animsprCharacter[0] = new AnimatedSpriteCharacter(-999, -999, tttrCharacter0, this.getVertexBufferObjectManager());
		animsprCharacter[0].setIndex(0);
		mScene.attachChild(animsprCharacter[0]);
		this.mScene.registerTouchArea(animsprCharacter[0]);
		
		animsprCharacter[27] = new AnimatedSpriteCharacter(-999, -999, tttrCharacter1, this.getVertexBufferObjectManager());
		animsprCharacter[27].setIndex(27);
		mScene.attachChild(animsprCharacter[27]);
		this.mScene.registerTouchArea(animsprCharacter[27]);
		
		animsprCharacter[2] = new AnimatedSpriteCharacter(-999, -999, tttrCharacter2, this.getVertexBufferObjectManager());
		animsprCharacter[2].setIndex(2);
		mScene.attachChild(animsprCharacter[2]);
		this.mScene.registerTouchArea(animsprCharacter[2]);
		
		animsprCharacter[28] = new AnimatedSpriteCharacter(-999, -999, tttrCharacter3, this.getVertexBufferObjectManager());
		animsprCharacter[28].setIndex(28);
		mScene.attachChild(animsprCharacter[28]);
		this.mScene.registerTouchArea(animsprCharacter[28]);
		
		animsprCharacter[1] = new AnimatedSpriteCharacter(-999, -999, tttrDataSearch[1], this.getVertexBufferObjectManager());
		animsprCharacter[1].setIndex(1);
		mScene.attachChild(animsprCharacter[1]);
		this.mScene.registerTouchArea(animsprCharacter[1]);
		
		animsprCharacter[3] = new AnimatedSpriteCharacter(-999, -999, tttrDataSearch[3], this.getVertexBufferObjectManager());
		animsprCharacter[3].setIndex(3);
		mScene.attachChild(animsprCharacter[3]);
		this.mScene.registerTouchArea(animsprCharacter[3]);
		
		for (int i = 4; i < 27; i++) {
			animsprCharacter[i] = new AnimatedSpriteCharacter(-999, -999, tttrDataSearch[i], this.getVertexBufferObjectManager());
			animsprCharacter[i].setIndex(i);
			mScene.attachChild(animsprCharacter[i]);
			this.mScene.registerTouchArea(animsprCharacter[i]);
		}
		
		for (int i = 29; i < animsprDataSearch.length; i++) {
			animsprCharacter[i] = new AnimatedSpriteCharacter(-999, -999, tttrDataSearch[i], this.getVertexBufferObjectManager());
			animsprCharacter[i].setIndex(i);
			mScene.attachChild(animsprCharacter[i]);
			this.mScene.registerTouchArea(animsprCharacter[i]);
		}
		
		//Ball
		sprBall = new Sprite(128, 262, ttrBall, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBall);
		sprBall.setVisible(false);
		
		//UFO
		sprUfo = new Sprite(15, 125, ttrUfo, this.getVertexBufferObjectManager());
		mScene.attachChild(sprUfo);
		sprUfo.setVisible(false);
		
		//Search
		sprSearch = new Sprite(160, -132, ttrSearch, this.getVertexBufferObjectManager());
		mScene.attachChild(sprSearch);
		
		//Data Search
		for (int i = 0; i < animsprDataSearch.length; i++) {
			animsprDataSearch[i] = new AnimatedSprite(-999, -999, tttrDataSearch[i], this.getVertexBufferObjectManager());
			animsprDataSearch[i].setScaleCenter(animsprDataSearch[i].getWidth()/2, animsprDataSearch[i].getHeight()/2);
			
			if(animsprDataSearch[i].getHeight() > 80 && animsprDataSearch[i].getHeight() < 149 && animsprDataSearch[i].getWidth() < 187  ){
				animsprDataSearch[i].setScale(0.80f);
			}
			else if(animsprDataSearch[i].getHeight() > 80 && animsprDataSearch[i].getHeight() < 149 && animsprDataSearch[i].getWidth() > 187  ){
				animsprDataSearch[i].setScale(0.65f);
			}
			else if(animsprDataSearch[i].getHeight() > 149 && animsprDataSearch[i].getHeight() < 170 ){
				animsprDataSearch[i].setScale(0.70f);
			}
			else if(animsprDataSearch[i].getHeight() > 170 && animsprDataSearch[i].getHeight() < 198 && animsprDataSearch[i].getWidth() < 190 ){
				animsprDataSearch[i].setScale(0.65f);
			}
			else if(animsprDataSearch[i].getHeight() > 170 && animsprDataSearch[i].getHeight() < 198 && animsprDataSearch[i].getWidth() > 190 ){
				animsprDataSearch[i].setScale(0.60f);
			}
			else if(animsprDataSearch[i].getHeight() > 198 && animsprDataSearch[i].getHeight() < 250 ){
				animsprDataSearch[i].setScale(0.55f);
			}
			animsprDataSearch[i].setVisible(false);
			sprSearch.attachChild(animsprDataSearch[i]);
		}
		
		// Gray
		for (int i = 0; i < sprGray.length; i++) {
			sprGray[i] = new Sprite(arrPointGray[i][0], arrPointGray[i][1], ttrGray, this.getVertexBufferObjectManager());
			sprSearch.attachChild(sprGray[i]);
			sprGray[i].setVisible(false);
		}
		
		//Tree Stage1
		sprTreeStage1 = new Sprite(423, 313, ttrTreeStage1, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTreeStage1);
		
		//Flower
		animsprFlower = new AnimatedSprite(430, 239, tttrFlower, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFlower);
		animsprFlower.setVisible(false);
		
		//BabyHappy
		animsprBabyHappy = new AnimatedSprite(288, 217, tttrBabyHappy, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBabyHappy);
		animsprBabyHappy.setVisible(false);
		
		//Water
		sprWater = new Sprite(505, 320, ttrWater, this.getVertexBufferObjectManager());
		mScene.attachChild(sprWater);
		sprWater.setVisible(false);
		
		//Frog
		animsprFrog = new AnimatedSprite(510, 290, tttrFrog	, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFrog);
		animsprFrog.setVisible(false);
		
		//Bong bay
		for (int i = 0; i < animsprBongbay.length; i++) {
			animsprBongbay[i] = new AnimatedSprite(0, 0, tttrBongbay[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprBongbay[i]);
			animsprBongbay[i].setVisible(false);
		}
		
		//Bang Blue - Yellow
		animsprBangBlue = new AnimatedSprite(562, 120, tttrBangBlue, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBangBlue);
		animsprBangBlue.setVisible(false);
		
		animsprBangYellow = new AnimatedSprite(715, 230, tttrBangYellow, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprBangYellow);
		animsprBangYellow.setVisible(false);
		
		//Ring
		for (int i = 0; i < sprRing.length; i++) {
			sprRing[i] = new Sprite(100, 100, ttrRing, this.getVertexBufferObjectManager());
			mScene.attachChild(sprRing[i]);
			sprRing[i].setVisible(false);
		}
		
		// Scene Finish
		animsprSceneFinish = new AnimatedSprite(35, 70, tttrSceneFinish, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprSceneFinish);
		animsprSceneFinish.setVisible(false);
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void combineGimmic3WithAction() {
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean focus){
		super.onWindowFocusChanged(focus);
		beginGame = focus;
		Log.d("111111111111111111111111", "onWindowFocusChanged");
	}
	
	@Override
	public synchronized void onResumeGame() {
		isStage = 1;
		valueGrayStage1 = 0;
		valueGrayStage2 = 0;
        this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}
			@Override
			public void onUpdate(float arg0) {
//				try{
//					beginGame = WINDOW_FOCUS_CHANGE;
//					Log.d("try  11111111111111111", "beginGame = WINDOW_FOCUS_CHANGE = : " + beginGame);
//				}catch (Exception e) {
//					beginGame = true;
//					Log.d("catch  1111111111111111", "beginGame = WINDOW_FOCUS_CHANGE = : " + beginGame);
//				}
				if(beginGame){
					Log.d("222222222222222222222222222222222", "onResumeGame");
					Vol3Bokukoshi.this.mScene.clearUpdateHandlers();
					startStage1();
		        }
			}
		});
		super.onResumeGame();
	}
	
	//Start Stage 1
	public void startStage1(){
		for (int i = 0; i < animsprCharacter.length; i++) {
			animsprCharacter[i].setScaleCenter(0, 0);
			animsprCharacter[i].setScale(0.95f);
			animsprCharacter[i].setPosition(-999, -999);
		}
		for (int j = 0; j < sprRing.length; j++) {
			sprRing[j].setScaleCenter(0, 0);
			sprRing[j].setScale(0.9f);
		}
		randomCharacterStage1();
		sSearchVisible();
		
	}
	
	//Start Stage 2
	public void startStage2(){
		for (int i = 0; i < animsprCharacter.length; i++) {
			animsprCharacter[i].setScaleCenter(0, 0);
			animsprCharacter[i].setScale(0.5f);
		}
		for (int j = 0; j < sprRing.length; j++) {
			sprRing[j].setScaleCenter(0,0);
			sprRing[j].setScale(0.5f);
		}
		initialStage2();
		randomBongbay();
		randomCharacterStage2();
		sSearchVisible();
	}
	int r;
	
	private void initialStage2(){
		for (int i = 0; i < isTouchBongbay.length; i++) {
			isTouchBongbay[i] = true;
		}
		isTouchsBabyCake = true;
		isTouchChicken = true;
		isTouchBabytOctopus = true;
		isTouchFlower = true;
		isTouchBear = true;
		isTouchBalloon = true;
		isTouchFrog = true;
		isTouchBird = true;
		isTouchDoor = true;
		isTouchBall = true;
		isTouchBeetle = true;
		isTouchFlag = true;
		isTouchMonkey = true;
		isTouchFootball = true;
		isTouchClown = true;
		isTouchThrowPaper = true;
		isTouchBoysurprise = true;
		isTouchBabyHappy = true;
		isTouchBabyBalloon = true;
		isTouchBabyDrum = true;
		isTouchBoyBalloon = true;
		isTouchBangBlue = true;
		isTouchBangYellow = true;
	}
	
	//On Pause Game
	@Override
	public synchronized void onPauseGame() {
		mp3stop();
		mScene.unregisterUpdateHandler(rtime);
		isStage = 1;
		for (int i = 0; i < sprRing.length; i++) {
			if(sprRing[i] != null){
				sprRing[i].clearEntityModifiers();
				sprRing[i].setVisible(false);
			}
		}
		if(sprSearch != null){
			sprSearch.clearEntityModifiers();
		}
		arraySearch.clear();
		
		for (int i = 0; i < sprGray.length; i++) {
			if(sprGray[i] != null){
				sprGray[i].setVisible(false);
			}
		}

		for (int i = 0; i < animsprDataSearch.length; i++) {
			if(animsprDataSearch[i] != null){
				animsprDataSearch[i].setPosition(-999, -999);
				animsprDataSearch[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < animsprCharacter.length; i++) {
			if(animsprCharacter[i] != null){
				animsprCharacter[i].clearEntityModifiers();
			}
		}
		
		if(animsprSceneFinish != null){
			animsprSceneFinish.stopAnimation();
			animsprSceneFinish.clearEntityModifiers();
			animsprSceneFinish.setVisible(false);
		}
		
		for (int i = 0; i < animsprBongbay.length; i++) {
			if(animsprBongbay[i] != null){
				animsprBongbay[i].setVisible(false);
				animsprBongbay[i].clearEntityModifiers();
			}
		}	
		if(sprStage1 != null){
			sprStage1.setVisible(true);
			sprTreeStage1.setPosition(423, 313);
			sprTreeStage1.setScale(1.0f);
		}
		
		stopclearAnimation(animsprBongbay);
		stopclearAnimation(animsprBabyCake, animsprChicken, animsprBabyOctopus, animsprFlower, animsprBear,
			animsprBalloon, animsprFrog, animsprBird, animsprDoor, animsprBeetle, animsprFlag,animsprMonkey,
			animsprFootball,animsprClown, animsprThrowPaper, animsprBoysurprise, animsprBabyHappy, animsprBabyBalloon,
			animsprBabyDrum, animsprBoyBalloon, animsprBangBlue, animsprBangYellow);
		
		spriteSetVisible(false, sprStage2Bg, sprSkyStage2, sprStage2TreeBg1, sprStage2TreeBg2,sprTree1Stage2,
			sprTree2Stage2, sprHouse1, sprWater, sprHouse2, sprBall,sprUfo,sprFlag);
			
		animSetVisible (false, animsprFlower, animsprBalloon, animsprBear, animsprBabyCake, animsprChicken,animsprBabyOctopus
			,animsprFrog, animsprBird, animsprDoor, animsprBeetle, animsprFlag, animsprMonkey, animsprFootball, animsprClown, 
			animsprThrowPaper ,animsprBoysurprise, animsprBabyHappy, animsprBabyBalloon, animsprBabyDrum, animsprBoyBalloon
			,animsprBangBlue,animsprBangYellow);
		
		for (int i = 0; i < isTouchBongbay.length; i++) {
			isTouchBongbay[i] = false;
		}
		
		isTouchsBabyCake = false;
		isTouchChicken = false;
		isTouchBabytOctopus = false;
		isTouchFlower = false;
		isTouchBear = false;
		isTouchBalloon = false;
		isTouchFrog = false;
		isTouchBird = false;
		isTouchDoor = false;
		isTouchBall = false;
		isTouchBeetle = false;
		isTouchFlag = false;
		isTouchMonkey = false;
		isTouchFootball = false;
		isTouchClown = false;
		isTouchThrowPaper = false;
		isTouchBoysurprise = false;
		isTouchBabyHappy = false;
		isTouchBabyBalloon = false;
		isTouchBabyDrum = false;
		isTouchBoyBalloon = false;
		isTouchBangBlue = false;
		isTouchBangYellow = false;
		
		super.onPauseGame();
	}
	
	// sSearch visible
	public void sSearchVisible(){
		OGG_A5_JAJAN.play();
		isTouchDataSearch = false;
		for (int i = 0; i < isTouchCharacter.length; i++) {
			isTouchCharacter[i] = false;
		}
		
		if(isStage == 1){
			Log.d("55555555555555555555555555555555555555", "sSearchVisible stage 11111111111111111111111111111111111111111");
			playSoundDelay(OGG_A5_KOREDOKO,0.5f);
			sprSearch.registerEntityModifier(new MoveYModifier(1.5f, -132, -2, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					for (int i = 0; i < isTouchCharacter.length; i++) {
						isTouchCharacter[i] = true;
					}
					isTouchDataSearch= true;
				}
			}));
		} else {
			Log.d("55555555555555555555555555555555555555", "sSearchVisible stage 22222222222222222222222222222222222");
			playSoundDelay(OGG_A5_MUZUKASHI,0.5f);
			sprSearch.registerEntityModifier(new MoveYModifier(1.5f, -132, -2, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mScene.unregisterUpdateHandler(rtime);
					rtime = new TimerHandler(1.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							Log.d(TAG, "ERORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
							for (int i = 0; i < isTouchCharacter.length; i++) {
								isTouchCharacter[i] = true;
							}
							isTouchDataSearch= true;
						}
					});
					mScene.registerUpdateHandler(rtime);
					Log.d("55555555555555555555555555555555555555", "registerUpdateHandler");
				}
			}));
		}
	}
	
	//Random Character 0 - 1 - 2 - 3 - 50
	public void testa(){
		Random r = new Random();
		int rr = r.nextInt(5);
		switch (rr) {
		case 0:
			rdCharacterStage1[0] = 0;
			break;
		case 1:
			rdCharacterStage1[0] = 1;
			break;
		case 2:
			rdCharacterStage1[0] = 2;
			break;
		case 3:
			rdCharacterStage1[0] = 3;
			break;
		case 4:
			rdCharacterStage1[0] = 51;
			break;
		default:
			break;
		}
	}
	
	//Random Character 46 - 47 - 48 - 49 - 50
	public void testb(){
		Random r = new Random();
		int rr = r.nextInt(5);
		switch (rr) {
		case 0:
			rdCharacterStage1[23] = 46;
			break;
		case 1:
			rdCharacterStage1[23] = 47;
			break;
		case 2:
			rdCharacterStage1[23] = 48;
			break;
		case 3:
			rdCharacterStage1[23] = 49;
			break;
		case 4:
			rdCharacterStage1[23] = 50;
			break;
		default:
			break;
		}
	}
	
	// Random Object Stage1
	public void randomCharacterStage1(){
		Log.d("3333333333333333333333333333333333", "randomCharacterStage1");
		zIndex = Vol3Bokukoshi.this.mScene.getChild(mScene.getLastChild().getTag()).getZIndex();
		//random Character
		rdnCharacter = new RanDomNoRepeat();
		rdnCharacter.setLenghNoRepeat(4, 45);
		testa();
		testb();
		for (int i = 1; i < 23; i++) {
			rdCharacterStage1[i] = rdnCharacter.getNextIntNoRepeat(true);
		}

		//Sort Character
		for (int i = 0; i < rdCharacterStage1.length; i++) {
			animsprCharacter[rdCharacterStage1[i]].setPosition(arrCharacterPointStage1[i][0], arrCharacterPointStage1[i][1]);
			animsprCharacter[rdCharacterStage1[i]].setZIndex(zIndex++);
		}

		//sprTreeStage1.setZIndex(zIndex++);
		sprSearch.setZIndex(zIndex++);
		animsprSceneFinish.setZIndex(zIndex++);
		Vol3Bokukoshi.this.mScene.sortChildren();
		
		//random Search Data
		rdnSearch = new RanDomNoRepeat();
		rdnSearch.setLenghNoRepeat(rdCharacterStage1.length);

		for (int i = 0; i < 5; i++) {
			int rPoint = rdnSearch.getNextIntNoRepeat(true);
			arraySearch.add(rdCharacterStage1[rPoint]);
		}
		//Sort Search Data
		for (int i = 0; i < arraySearch.size(); i++) {
			animsprDataSearch[arraySearch.get(0)].setPosition((122/2) + 4 - animsprDataSearch[arraySearch.get(0)].getWidth()/2, 
				+ 122/2 + 4 - animsprDataSearch[arraySearch.get(0)].getHeight()/2);
			
			animsprDataSearch[arraySearch.get(1)].setPosition((122/2) + 122 + 8 - animsprDataSearch[arraySearch.get(1)].getWidth()/2, 
				122/2 + 4 - animsprDataSearch[arraySearch.get(1)].getHeight()/2);
			
			animsprDataSearch[arraySearch.get(2)].setPosition((122/2) + (122 * 2) + 12 - animsprDataSearch[arraySearch.get(2)].getWidth()/2, 
				122/2 + 4 - animsprDataSearch[arraySearch.get(2)].getHeight()/2);
			
			animsprDataSearch[arraySearch.get(3)].setPosition((122/2) + (122 * 3) + 16 - animsprDataSearch[arraySearch.get(3)].getWidth()/2, 
				122/2 + 4 - animsprDataSearch[arraySearch.get(3)].getHeight()/2);
			
			animsprDataSearch[arraySearch.get(4)].setPosition((122/2) + (122 * 4) + 20 - animsprDataSearch[arraySearch.get(4)].getWidth()/2, 
				122/2 + 4 - animsprDataSearch[arraySearch.get(4)].getHeight()/2);
			
			animsprDataSearch[arraySearch.get(i)].setVisible(true);
			Log.d("4444444444444444444444444444444444444444", "Random &&&&&&&&&&&&&&  Sort Search Data");
		}
	}
	
	// Random Object Stage2
		public void randomCharacterStage2(){
			zIndex = Vol3Bokukoshi.this.mScene.getChild(mScene.getLastChild().getTag()).getZIndex();
			//random Character
			rdnCharacter = new RanDomNoRepeat();
			rdnCharacter.setLenghNoRepeat(4,46);
			
			for (int i = 0; i < 42; i++) {
				rdCharacterStage2[i] = rdnCharacter.getNextIntNoRepeat(true);
			}
			
			rdnCharacter46 = new RanDomNoRepeat();
			rdnCharacter46.setLenghNoRepeat(46, 50);

			for (int i = 0; i < 4; i++) {
				rdChastage2[i] = rdnCharacter46.getNextIntNoRepeat(true);
			}
			
			Random rr = new Random();
			r = rr.nextInt(2);
			switch (r) {
			case 0:
				for (int i = 0; i < 4; i++) {
					if(rdChastage2[i] == 47) rdChastage2[i] = 50;
				}
				break;
			default:
				break;
			}

			//Sort Character
			sprBall.setZIndex(zIndex++);
			for (int i = 0; i < 4; i++) {
				animsprCharacter[rdChastage2[i]].setPosition(arrChaStage2[i][0], arrChaStage2[i][1]);
				animsprCharacter[rdChastage2[i]].setZIndex(zIndex++);
			}
			
			for (int i = 0; i < 42; i++) {
				animsprCharacter[rdCharacterStage2[i]].setPosition(arrCharacterPointStage2[i][0], arrCharacterPointStage2[i][1]);
				animsprCharacter[rdCharacterStage2[i]].setZIndex(zIndex++);
			}
			
			animsprCharacter[0].setPosition(arrCharacter0stage2[0][0], arrCharacter0stage2[0][1]);
			animsprCharacter[1].setPosition(arrCharacter1stage2[0][0], arrCharacter1stage2[0][1]);
			animsprCharacter[2].setPosition(arrCharacter2stage2[0][0], arrCharacter2stage2[0][1]);
			animsprCharacter[3].setPosition(arrCharacter3stage2[0][0], arrCharacter3stage2[0][1]);
			animsprCharacter[51].setPosition(arrCharacter50stage2[0][0], arrCharacter50stage2[0][1]);
			
//			animsprCharacter[0].setZIndex(0);
//			animsprCharacter[1].setZIndex(0);
//			animsprCharacter[3].setZIndex(0);
			animsprCharacter[2].setZIndex(zIndex++);
			animsprCharacter[51].setZIndex(zIndex++);
			animsprFrog.setZIndex(zIndex++);
			animsprThrowPaper.setZIndex(zIndex++);
			animsprBabyBalloon.setZIndex(zIndex++);
			sprFlag.setZIndex(zIndex++);
			animsprBalloon.setZIndex(zIndex++);
			animsprBear.setZIndex(zIndex++);
			animsprClown.setZIndex(zIndex++);
			//animsprBabyHappy.setZIndex(zIndex++);
			sprTreeStage1.setZIndex(zIndex++);
			for (int i = 0; i < animsprBongbay.length; i++) {
				animsprBongbay[i].setZIndex(zIndex++);
			}
			sprUfo.setZIndex(zIndex++);
			animsprBangBlue.setZIndex(zIndex++);
			animsprBangYellow.setZIndex(zIndex++);
			sprSearch.setZIndex(zIndex++);
			animsprSceneFinish.setZIndex(zIndex++);
			Vol3Bokukoshi.this.mScene.sortChildren();
			
			//random Search Data
			rdnSearch = new RanDomNoRepeat();
			rdnSearch.setLenghNoRepeat(50);

			for (int i = 0; i < 5; i++) {
				int rPoint = rdnSearch.getNextIntNoRepeat(true);
				if(r == 0 && rPoint == 47) {
					rPoint = 50;
					r = 1;
				}
				arraySearch.add(rPoint);
			}
			
			//Sort Search Data
			for (int i = 0; i < arraySearch.size(); i++) {
				animsprDataSearch[arraySearch.get(0)].setPosition((122/2) + 4 - animsprDataSearch[arraySearch.get(0)].getWidth()/2, 
					+ 122/2 + 4 - animsprDataSearch[arraySearch.get(0)].getHeight()/2);
				
				animsprDataSearch[arraySearch.get(1)].setPosition((122/2) + 122 + 8 - animsprDataSearch[arraySearch.get(1)].getWidth()/2, 
					122/2 + 4 - animsprDataSearch[arraySearch.get(1)].getHeight()/2);
				
				animsprDataSearch[arraySearch.get(2)].setPosition((122/2) + (122 * 2) + 12 - animsprDataSearch[arraySearch.get(2)].getWidth()/2, 
					122/2 + 4 - animsprDataSearch[arraySearch.get(2)].getHeight()/2);
				
				animsprDataSearch[arraySearch.get(3)].setPosition((122/2) + (122 * 3) + 16 - animsprDataSearch[arraySearch.get(3)].getWidth()/2, 
					122/2 + 4 - animsprDataSearch[arraySearch.get(3)].getHeight()/2);
				
				animsprDataSearch[arraySearch.get(4)].setPosition((122/2) + (122 * 4) + 20 - animsprDataSearch[arraySearch.get(4)].getWidth()/2, 
					122/2 + 4 - animsprDataSearch[arraySearch.get(4)].getHeight()/2);
				
				animsprDataSearch[arraySearch.get(i)].setVisible(true);
			}
		}
		
	// onSceneTouch
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		//Action Down
		if(pSceneTouchEvent.isActionDown()){
			//Touch Help
			for (int i = 0; i < arraySearch.size(); i++) {
				if (animsprDataSearch[arraySearch.get(i)].contains(pX, pY) && isTouchDataSearch && !sprGray[i].isVisible()) {
					OGG_A5_POWAN.play();
					isTouchDataSearch = false;
					for (int j = 0; j < isTouchCharacter.length; j++) {
						isTouchCharacter[j] = false;
					}
					if(isStage == 1){
						animsprCharacter[arraySearch.get(i)].registerEntityModifier(
							new SequenceEntityModifier(
							new LoopEntityModifier(
								new SequenceEntityModifier(
									new ScaleModifier(0.4f,animsprCharacter[arraySearch.get(i)].getScaleX(), 1.2f),
									new ScaleModifier(0.1f, 1.2f, animsprCharacter[arraySearch.get(i)].getScaleX())),3),
								
								new DelayModifier(0.3f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isTouchDataSearch = true;
										for (int j = 0; j < isTouchCharacter.length; j++) {
											isTouchCharacter[j] = true;
										}
									}
							})
							));
					} else {
						animsprCharacter[arraySearch.get(i)].registerEntityModifier(
							new SequenceEntityModifier(
								new LoopEntityModifier(
									new SequenceEntityModifier(
										new ScaleModifier(0.4f,animsprCharacter[arraySearch.get(i)].getScaleX(), 0.7f),
										new ScaleModifier(0.2f, 0.7f, animsprCharacter[arraySearch.get(i)].getScaleX())),3),
									
									new DelayModifier(0.3f, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											isTouchDataSearch = true;
											for (int j = 0; j < isTouchCharacter.length; j++) {
												isTouchCharacter[j] = true;
											}
										}
								})
							));
					}
				}
			}
			
			// Touch Baby Cake
			if(checkContains(animsprBabyCake, 55, 36, 166, 100, pX, pY) && isTouchsBabyCake){
				isTouchsBabyCake = false;
				actionJumpOrSway(animsprBabyCake,OGG_A5_POMI,OGG_A5_HYOI);
				animsprBabyCake.registerEntityModifier(delayBabycake = new DelayModifier(0.8f , this)); 
				delayBabycake.addModifierListener(this);
			}
			
			// Touch Chicken
			if(checkContains(animsprChicken, 0, 15, 109, 35, pX, pY) && isTouchChicken){
				isTouchChicken = false;
				OGG_A5_35_PIYO.play();
				actionAnim(animsprChicken, 300, 300, 0, 0);
				animsprChicken.registerEntityModifier(delayChicken = new DelayModifier(1.0f, this));
				delayChicken.addModifierListener(this);
			}
			
			//Touch Baby Octopus
			if (checkContains(animsprBabyOctopus, 31, 36, 69, 109, pX, pY) && isTouchBabytOctopus) {
				isTouchBabytOctopus = false;
				OGG_A5_22_1_NYUN.play();
				actionAnim(animsprBabyOctopus, 300, 300, 300, 0);
				animsprBabyOctopus.registerEntityModifier(delayBabytOctopus = new DelayModifier(1.0f,this));
				delayBabytOctopus.addModifierListener(this);
			}
			
			//Touch Flower
			if(checkContains(animsprFlower, 20, 10, 75, 120, pX, pY) && isTouchFlower){
				isTouchFlower = false;
				OGG_A5_10_KIRAN.play();
				actionAnim(animsprFlower, 500, 500, 500, 0);
				animsprFlower.registerEntityModifier(delayFlower = new DelayModifier(1.7f, this));
				delayFlower.addModifierListener(this);
			}
			
			//Touch Bear
			if(checkContains(animsprBear, 70, 70, 130, 160, pX, pY) && isTouchBear){
				isTouchBear = false;
				OGG_A5_5_KUMARAKU.play();
				actionAnim(animsprBear, 600, 700, 800, 0);
				animsprBear.registerEntityModifier(delayBear = new DelayModifier(2.4f, this));
				delayBear.addModifierListener(this);
			}
			
			// Touch Balloon
			if(checkContainsPolygon(animsprBalloon, pointPolygonBalloon, 9, pX, pY) && isTouchBalloon){
				isTouchBalloon = false;
				OGG_A5_9_KIRA.play();
				actionAnim(animsprBalloon, 400, 400, 0, 1);
				animsprBalloon.registerEntityModifier(delayBalloon = new DelayModifier(2.0f, this));
				delayBalloon.addModifierListener(this);
			}
			
			// Touch Frog
			if(checkContains(animsprFrog, 15, 20, 75, 75, pX, pY) && isTouchFrog){
				isTouchFrog = false;
				OGG_A5_23_KAERU.play();
				actionAnim(animsprFrog, 300, 300, 300, 0);
				animsprFrog.registerEntityModifier(delayFrog = new DelayModifier(1.2f, this));
				delayFrog.addModifierListener(this);
			}
			
			// Touch Bird
			if(checkContains(animsprBird, 240, 0, 280, 90, pX, pY) && isTouchBird){
				isTouchBird = false;
				OGG_A5_6_CHUN.play();
				actionAnim(animsprBird, 700, 700, 700, 0);
				animsprBird.registerEntityModifier(delayBird = new DelayModifier(2.2f, this));
				delayBird.addModifierListener(this);
			}
			
			// Touch Door
			if (animsprDoor.contains(pX, pY) && isTouchDoor) {
				isTouchDoor = false;
				OGG_A5_7_KESYOU.play();
				playSoundDelay(OGG_A5_7_KIRA4, 0.7f);
				actionAnim(animsprDoor, 700, 1500, 0, 0);
				animsprDoor.registerEntityModifier(delayDoor = new DelayModifier(2.4f, this));
				delayDoor.addModifierListener(this);
			}
			
			// Touch Ball
			if(sprBall.contains(pX, pY) && isTouchBall){
				isTouchBall = false;
				sprBall.registerEntityModifier(new ParallelEntityModifier(
					new RotationAtModifier(2.0f, 0, -720, sprBall.getWidth()/2, sprBall.getHeight()/2),
					new MoveXModifier(2.0f, sprBall.getmXFirst(), -128,new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchBall = true;
							sprBall.setPosition(sprBall.getmXFirst(), sprBall.getmYFirst());
						}
					})));
			}
			
			// Touch Beetle
			if(animsprBeetle.contains(pX, pY) && isTouchBeetle){
				isTouchBeetle = false;
				animsprBeetle.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						animsprBeetle.setCurrentTileIndex(1);
						animsprBeetle.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animsprBeetle.setCurrentTileIndex(2);
								animsprBeetle.registerEntityModifier(new SequenceEntityModifier(
									new MoveModifier(1.5f, 190, -50, 130, 20, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											OGG_A5_14_TENTOMUSHI.play();
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											isTouchBeetle = true;
											animsprBeetle.setCurrentTileIndex(0);
											animsprBeetle.setPosition(animsprBeetle.getmXFirst(), animsprBeetle.getmYFirst());
										}
									})));
							}
						}));
					}	
				}));
			}
			
			// Touch Flag
			if(animsprFlag.contains(pX, pY) && isTouchFlag){
				isTouchFlag = false;
				OGG_A5_15_KOINOBORI.play();
				actionAnim(animsprFlag, 500, 500, 500, 0);
				animsprFlag.registerEntityModifier(delayFlag = new DelayModifier(1.7f, this));
				delayFlag.addModifierListener(this);
			}
			
			// Touch Monkey
			if(checkContains(animsprMonkey, 25, 12, 120, 75, pX, pY) && isTouchMonkey){
				isTouchMonkey = false;
				OGG_A5_27_MOGU.play();
				animsprMonkey.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						animsprMonkey.setCurrentTileIndex(1);
						animsprMonkey.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animsprMonkey.setCurrentTileIndex(2);
								animsprMonkey.registerEntityModifier(new DelayModifier(0.8f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										OGG_A5_27_HYOI.play();
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isTouchMonkey = true;
										animsprMonkey.setCurrentTileIndex(0);
									}
								}));
							}
						}));
					}
				}));
			}
			
			// Touch Baby FootBall
			if(checkContains(animsprFootball, 15, 15, 85, 85, pX, pY) && isTouchFootball){
				isTouchFootball = false;
				OGG_A5_29_BALL.play();
				actionAnim(animsprFootball, 400, 400, 0, 0);
				animsprFootball.registerEntityModifier(delayFootball = new DelayModifier(1.0f, this));
				delayFootball.addModifierListener(this);
			}
			
			//Touch Clown
			if(checkContains(animsprClown, 11, 19, 65, 130, pX, pY) && isTouchClown){
				isTouchClown = false;
				OGG_A5_30_PIERO.play();
				actionAnim(animsprClown, 400, 400, 400, 0);
				animsprClown.registerEntityModifier(delayClown = new DelayModifier(1.4f, this));
				delayClown.addModifierListener(this);
			}
			
			// Touch Throw	Paper
			if(checkContains(animsprThrowPaper, 65, 72, 112, 127, pX, pY) && isTouchThrowPaper){
				isTouchThrowPaper = false;
				actionAnim(animsprThrowPaper, 500, 500, 0, 0);
				animsprThrowPaper.registerEntityModifier(delayThrowPaper = new DelayModifier(1.2f, this));
				delayThrowPaper.addModifierListener(this);
			}
			
			//Touch Bousurprise
			if(checkContains(animsprBoysurprise, 32, 64, 80, 140, pX, pY) && isTouchBoysurprise){
				isTouchBoysurprise = false;
				actionJumpOrSway(animsprBoysurprise,OGG_A5_POMI,OGG_A5_HYOI);
				animsprBoysurprise.registerEntityModifier(delayBoysurprise = new DelayModifier(0.8f, this));
				delayBoysurprise.addModifierListener(this);
			}
			
			//Touch Baby Happy
			if(checkContains(animsprBabyHappy, 52, 38, 155, 114, pX, pY) && isTouchBabyHappy){
				isTouchBabyHappy = false;
				actionJumpOrSway(animsprBabyHappy,OGG_A5_POMI,OGG_A5_HYOI);
				animsprBabyHappy.registerEntityModifier(delayBabyHappy = new DelayModifier(0.8f, this));
				delayBabyHappy.addModifierListener(this);
			}
			
			//Touch Baby Ballon - 1 flame
			if(checkContains(animsprBabyBalloon, 25, 105, 120, 180, pX, pY) && isTouchBabyBalloon){
				isTouchBabyBalloon = false;
				actionJumpOrSway(animsprBabyBalloon,OGG_A5_POMI,OGG_A5_HYOI);
				animsprBabyBalloon.registerEntityModifier(delayBabyBalloon = new DelayModifier(1.0f, this));
				delayBabyBalloon.addModifierListener(this);		
			}
			
			//Touch Baby Drum
			if(checkContains(animsprBabyDrum, 45, 15, 114, 90, pX, pY) && isTouchBabyDrum){
				isTouchBabyDrum = false;
				actionJumpOrSway(animsprBabyDrum,OGG_A5_POMI,OGG_A5_HYOI);
				animsprBabyDrum.registerEntityModifier(delayBabyDrum = new DelayModifier(0.8f, this));
				delayBabyDrum.addModifierListener(this);
			}
			
			//Touch Boy Balloon
			if(checkContains(animsprBoyBalloon, 17, 85, 135, 180, pX, pY) && isTouchBoyBalloon){
				isTouchBoyBalloon = false;
				OGG_A5_38_AGERU.play();
				actionAnim(animsprBoyBalloon, 500, 500, 500, 0);
				animsprBoyBalloon.registerEntityModifier(delayBoyBalloon = new DelayModifier(1.7f, this));
				delayBoyBalloon.addModifierListener(this);
			}
			
			// Touch UFO
			if(sprUfo.contains(pX, pY)){
				moveUfo();
			}
			
			//Touch Bong bay
			for (int i = 0; i < animsprBongbay.length; i++) {
				if(checkContains(animsprBongbay[i], 0, 0, 36, 48, pX, pY) && isTouchBongbay[i]){
					isTouchBongbay[i] = false;
					mp3Bongbay();
					touchBongbay(animsprBongbay[i]);
				}
			}
			
			//Bang Blue
			if(checkContains(animsprBangBlue, 6, 4, 50, 50, pX, pY) && isTouchBangBlue){
				isTouchBangBlue = false;
				OGG_A5_24_25_WARERU.play();
				final long Duration[] = new long[] {200,500};
				final int frame[] = new int[] {0,1};
				animsprBangBlue.animate(Duration, frame, 0, new IAnimationListener() {
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
						animsprBangBlue.setVisible(false);
						animsprBangBlue.registerEntityModifier(new DelayModifier(0.9f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animsprBangBlue.setVisible(true);
								animsprBangBlue.setCurrentTileIndex(0);
								isTouchBangBlue = true;
							}
						}));
					}
				});
			}
			
			//Bang Yellow
			if(checkContains(animsprBangYellow, 15, 6, 50, 50, pX, pY) && isTouchBangYellow){
				isTouchBangYellow = false;
				OGG_A5_24_25_WARERU.play();
				final long Duration[] = new long[] {200,500};
				final int frame[] = new int[] {0,1};
				animsprBangYellow.animate(Duration, frame, 0, new IAnimationListener() {
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
						animsprBangYellow.setVisible(false);
						animsprBangYellow.registerEntityModifier(new DelayModifier(0.9f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animsprBangYellow.setVisible(true);
								animsprBangYellow.setCurrentTileIndex(0);
								isTouchBangYellow = true;
							}
						}));
					}
				});
			}
		}
		return false;
	}
	
	public void touchBongbay(final AnimatedSprite animBongbay){
		animBongbay.registerEntityModifier(new MoveYModifier(3.5f, animBongbay.getY(), animBongbay.getY() - 700, 
			new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					for (int i = 0; i < 11; i++) {
						isTouchBongbay[i] = true;
					}
					animBongbay.setPosition(animBongbay.getX(), 
							animBongbay.getY() + 700);
				}
			}));
	}

	//Class Character
	class AnimatedSpriteCharacter extends AnimatedSprite implements IEntityModifierListener {
		int idIndex = -1;

		public AnimatedSpriteCharacter(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		}
		
		public void setIndex(int pIndex){
			this.idIndex = pIndex;
		}
		
		public int getIndex(){
			return this.idIndex;
		}
		
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			boolean check = false;
			if (pSceneTouchEvent.isActionDown()){
				switch (idIndex) {
				case 0:
					if(isTouchCharacter[0]){
						isTouchCharacter[0] = false;
						OGG_A5_26_HYU.play();
						checkDataSearch(animsprCharacter[0],0);
						actionAnim(animsprCharacter[0], 300, 300, 0, 0);
						animsprCharacter[0].registerEntityModifier(delayCharacter[0] = new DelayModifier(1.0f, this));
						delayCharacter[0].addModifierListener(this);
					}
					check = true;
					break;
					
				case 1:
					if(isTouchCharacter[1]){
						isTouchCharacter[1] = false;
						checkDataSearch(animsprCharacter[1],1);
						animhorseRiding(animsprCharacter[1]);
						animsprCharacter[1].registerEntityModifier(delayCharacter[1] = new DelayModifier(5.0f, this));
						delayCharacter[1].addModifierListener(this);
					}
					check = true;
					break;
			
				case 2:
					if(isTouchCharacter[2]){
						isTouchCharacter[2] = false;
						OGG_A5_33_ZOU.play();
						checkDataSearch(animsprCharacter[2],2);
						actionAnim(animsprCharacter[2], 100, 300, 1500, 0);
						animsprCharacter[2].registerEntityModifier(delayCharacter[2] = new DelayModifier(2.0f, this));
						delayCharacter[2].addModifierListener(this);
					}
					check = true;
					break;
					
				case 3:
					if(isTouchCharacter[3]){
						isTouchCharacter[3] = false;
						checkDataSearch(animsprCharacter[3],3);
						animhorseRiding(animsprCharacter[3]);
						animsprCharacter[3].registerEntityModifier(delayCharacter[3] = new DelayModifier(5.0f, this));
						delayCharacter[3].addModifierListener(this);
						}
					check = true;
					break;
					
				case 4:
					caseAreaTouched(4);
					check = true;
					break;
					
				case 5:
					caseAreaTouched(5);
					check = true;
					break;
					
				case 6:
					caseAreaTouched(6);
					check = true;
					break;
					
				case 7:
					caseAreaTouched(7);
					check = true;
					break;
					
				case 8:
					caseAreaTouched(8);
					check = true;
					break;
					
				case 9:
					caseAreaTouched(9);
					check = true;
					break;
					
				case 10:
					caseAreaTouched(10);
					check = true;
					break;
					
				case 11:
					caseAreaTouched(11);
					check = true;
					break;
			
				case 12:
					caseAreaTouched(12);
					check = true;
					break;
					
				case 13:
					caseAreaTouched(13);
					check = true;
					break;
					
				case 14:
					caseAreaTouched(14);
					check = true;
					break;
					
				case 15:
					caseAreaTouched(15);
					check = true;
					break;
					
				case 16:
					caseAreaTouched(16);
					check = true;
					break;
					
				case 17:
					caseAreaTouched(17);
					check = true;
					break;
					
				case 18:
					caseAreaTouched(18);
					check = true;
					break;
					
				case 19:
					caseAreaTouched(19);
					check = true;
					break;
					
				case 20:
					caseAreaTouched(20);
					check = true;
					break;
					
				case 21:
					caseAreaTouched(21);
					check = true;
					break;
			
				case 22:
					caseAreaTouched(22);
					check = true;
					break;
					
				case 23:
					caseAreaTouched(23);
					check = true;
					break;
					
				case 24:
					caseAreaTouched(24);
					check = true;
					break;
					
				case 25:
					caseAreaTouched(25);
					check = true;
					break;
					
				case 26:
					caseAreaTouched(26);
					check = true;
					break;
					
				case 27:
					if(isTouchCharacter[27]){
						isTouchCharacter[27] = false;
						OGG_A5_28_EREKI.play();
						checkDataSearch(animsprCharacter[27],27);
						actionAnim(animsprCharacter[27], 600, 700, 800, 0);
						animsprCharacter[27].registerEntityModifier(delayCharacter[27] = new DelayModifier(2.2f, this));
						delayCharacter[27].addModifierListener(this);
					}
					check = true;
					break;
				case 28:
					if(isTouchCharacter[28]){
						isTouchCharacter[28] = false;
						OGG_A5_39_KODAIKO2.play();
						checkDataSearch(animsprCharacter[28],28);
						actionAnim(animsprCharacter[28], 300, 300, 500, 0);
						animsprCharacter[28].registerEntityModifier(delayCharacter[28] = new DelayModifier(1.2f, this));
						delayCharacter[28].addModifierListener(this);
					}
					check = true;
					break;
					
				case 29:
					caseAreaTouched(29);
					check = true;
					break;
					
				case 30:
					caseAreaTouched(30);
					check = true;
					break;
					
				case 31:
					caseAreaTouched(31);
					check = true;
					break;
			
				case 32:
					caseAreaTouched(32);
					check = true;
					break;
					
				case 33:
					caseAreaTouched(33);
					check = true;
					break;
					
				case 34:
					caseAreaTouched(34);
					check = true;
					break;
					
				case 35:
					caseAreaTouched(35);
					check = true;
					break;
					
				case 36:
					caseAreaTouched(36);
					check = true;
					break;
					
				case 37:
					caseAreaTouched(37);
					check = true;
					break;
					
				case 38:
					if(isTouchCharacter[38]){
						isTouchCharacter[38] = false;
						animSlide(animsprCharacter[38]);
						checkDataSearch(animsprCharacter[38],38);
						animsprCharacter[38].registerEntityModifier(delayCharacter[38] = new DelayModifier(1.5f, this));
						delayCharacter[38].addModifierListener(this);
					}
					check = true;
					break;
					
				case 39:
					if(isTouchCharacter[39]){
						isTouchCharacter[39] = false;
						animSlide(animsprCharacter[39]);
						checkDataSearch(animsprCharacter[39],39);
						animsprCharacter[39].registerEntityModifier(delayCharacter[39] = new DelayModifier(1.5f, this));
						delayCharacter[39].addModifierListener(this);
					}
					check = true;
					break;
					
				case 40:
					caseAreaTouched(40);
					check = true;
					break;
				case 41:
					caseAreaTouched(41);
					check = true;
					break;
			
				case 42:
					caseAreaTouched(42);
					check = true;
					break;
					
				case 43:
					caseAreaTouched(43);
					check = true;
					break;
					
				case 44:
					caseAreaTouched(44);
					check = true;
					break;
					
				case 45:
					if(isTouchCharacter[45]){
						OGG_A5_34_KUMA.play();
						isTouchCharacter[45] = false;
							checkDataSearch(animsprCharacter[45],45);
							animsprCharacter[45].registerEntityModifier(new SequenceEntityModifier(
								new MoveYModifier(0.3f, animsprCharacter[45].getY(), animsprCharacter[45].getY() - 30),
								new MoveYModifier(0.2f, animsprCharacter[45].getY() - 30, animsprCharacter[45].getY())
							));
							animsprCharacter[45].registerEntityModifier(delayCharacter[45] = new DelayModifier(1.0f, this));
							delayCharacter[45].addModifierListener(this);
					}
					check = true;
					break;
					
				case 46:
					caseAreaTouched(46);
					check = true;
					break;
					
				case 47:
					caseAreaTouched(47);
					check = true;
					break;
					
				case 48:
					caseAreaTouched(48);
					check = true;
					break;
					
				case 49:
					caseAreaTouched(49);
					check = true;
					break;
					
				case 50:
					caseAreaTouched(50);
					check = true;
					break;
				case 51:
					caseAreaTouched(51);
					check = true;
					break;

				default:
					break;
				}
			}
			return check;
		}
		
		//Case Area Touched
		public void caseAreaTouched(int x){
			if(isTouchCharacter[x]){
				isTouchCharacter[x] = false;
					actionJumpOrSway(animsprCharacter[x],OGG_A5_POMI,OGG_A5_HYOI);
					checkDataSearch(animsprCharacter[x],x);
					animsprCharacter[x].registerEntityModifier(delayCharacter[x] = new DelayModifier(1.0f, this));
					delayCharacter[x].addModifierListener(this);
			}
		}

		int ck = 0;
		
		//Check Data Search
		public void checkDataSearch(AnimatedSpriteCharacter aniSprite, int x){
			for (int i = 0; i < sprGray.length; i++) {
				if(aniSprite.getIndex() == arraySearch.get(i)){
					if(!sprGray[i].isVisible()){
						isTouchDataSearch = false;
						OGG_A5_PINPON.play();
//						if(x == 2){
//							mp3OnOff(OGG_A5_33_ZOU);
//						} else if (x == 0){
//							mp3OnOff(OGG_A5_26_HYU);
//						} else if (x == 1 || x == 3){
//							mp3OnOff(OGG_A5_32_15_UMA);
//						} else if (x == 27){
//							mp3OnOff(OGG_A5_28_EREKI);
//						} else if (x == 28){
//							mp3OnOff(OGG_A5_39_KODAIKO2);
//						} else if (x == 38 || x == 39){
//							mp3OnOff(OGG_A5_36_37_SUBERU);
//						} else if(x == 45){
//							mp3OnOff(OGG_A5_34_KUMA);
//						}
//						mp3OnOff(OGG_A5_HYOI,OGG_A5_POMI);
						ck = i;
						ringPosition(sprRing[ck], aniSprite);
						sprRing[ck].setVisible(true);
						sprRing[ck].registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sprGray[ck].registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sprGray[ck].setVisible(true);
									}
								}));
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sprRing[ck].setVisible(false);
								valueGrayStage1++;
								valueGrayStage2++;
								isTouchDataSearch = true;
								
								if(isStage == 1){
									isFinishStage1();
								}else {
									isFinishStage2();
								}
							}
						}));
					}
				}
			}
			}

		@Override
		public void onModifierStarted(IModifier<IEntity> pIModifier, IEntity arg1) {
			
		}
		
		@Override
		public void onModifierFinished(IModifier<IEntity> pIModifier, IEntity arg1) {
			for (int i = 0; i < delayCharacter.length; i++) {
				if(pIModifier == delayCharacter[i]) isTouchCharacter[i] = true;
			}
		}
		}
		//------------------------------------End----Class Character----------------------------//
	
		// Check finish stage
		public void isFinishStage1(){
			if(valueGrayStage1 == 5) {
				valueGrayStage1 = 0;
				valueGrayStage2 = 0;
				isStage = 2;
				sprSearch.setPosition(160, -132);
				
				for (int i = 0; i < animsprCharacter.length; i++) {
					isTouchCharacter[i] = false;
					animsprCharacter[i].stopAnimation();
					animsprCharacter[i].clearEntityModifiers();
					animsprCharacter[i].setCurrentTileIndex(0);
				}
				mp3stop();
				
				for (int i = 0; i < animsprDataSearch.length; i++) {
					animsprDataSearch[i].setPosition(-999, -999);
					animsprDataSearch[i].setVisible(false);
				}
				OGG_A5_21_1_OMEDETO.play();
				
				final long fDuration[] = new long[] {650,650};
				int fFrame[] = new int[] {0,1};
				animsprSceneFinish.setVisible(true);
				animsprSceneFinish.animate(fDuration, fFrame, 1, new IAnimationListener() {
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
						animsprSceneFinish.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								for (int i = 0; i < sprGray.length; i++) {
									sprGray[i].setVisible(false);
								}
								
								arraySearch.clear();
								sprStage1.setVisible(false);
								sprTreeStage1.setPosition(195, 440);
								sprTreeStage1.setScale(0.5f);
								animsprSceneFinish.setVisible(false);
								
								startStage2();
					
								setScaleAnim(0.5f, animsprBabyCake,animsprBoysurprise,animsprBabyHappy,animsprBabyOctopus);
								setScaleAnim(0.75f, animsprBabyDrum, animsprThrowPaper, animsprBabyBalloon, animsprClown, animsprBoyBalloon);
								
								spriteSetVisible(true, sprStage2Bg, sprSkyStage2, sprStage2TreeBg1, sprStage2TreeBg2,sprTree1Stage2,
										sprTree2Stage2, sprHouse1, sprWater, sprHouse2, sprBall,sprUfo,sprFlag);
								
								animSetVisible (true, animsprFlower, animsprBalloon, animsprBear, animsprBabyCake, animsprChicken,animsprBabyOctopus
									,animsprFrog, animsprBird, animsprDoor, animsprBeetle, animsprFlag, animsprMonkey, animsprFootball, animsprClown, 
									animsprThrowPaper ,animsprBoysurprise, animsprBabyHappy, animsprBabyBalloon, animsprBabyDrum, animsprBoyBalloon
									,animsprBangBlue,animsprBangYellow);
							}
						}));
					}
				});
			}
		}
		
		// Stage 2 
		public void isFinishStage2(){
			if(valueGrayStage2 == 5) {
				valueGrayStage1 = 0;
				valueGrayStage2 = 0;
				isStage = 1;
				sprSearch.setPosition(160, -132);
				stopclearAnimation(animsprBongbay);
				stopclearAnimation(animsprBabyCake, animsprChicken, animsprBabyOctopus, animsprFlower, animsprBear,
					animsprBalloon, animsprFrog, animsprBird, animsprDoor, animsprBeetle, animsprFlag,animsprMonkey,
					animsprFootball,animsprClown, animsprThrowPaper, animsprBoysurprise, animsprBabyHappy, animsprBabyBalloon,
					animsprBabyDrum, animsprBoyBalloon, animsprBangBlue, animsprBangYellow);
				for (int i = 0; i < isTouchBongbay.length; i++) {
					isTouchBongbay[i] = false;
				}
				isTouchsBabyCake = false;
				isTouchChicken = false;
				isTouchBabytOctopus = false;
				isTouchFlower = false;
				isTouchBear = false;
				isTouchBalloon = false;
				isTouchFrog = false;
				isTouchBird = false;
				isTouchDoor = false;
				isTouchBall = false;
				isTouchBeetle = false;
				isTouchFlag = false;
				isTouchMonkey = false;
				isTouchFootball = false;
				isTouchClown = false;
				isTouchThrowPaper = false;
				isTouchBoysurprise = false;
				isTouchBabyHappy = false;
				isTouchBabyBalloon = false;
				isTouchBabyDrum = false;
				isTouchBoyBalloon = false;
				isTouchBangBlue = false;
				isTouchBangYellow = false;
				
				for (int i = 0; i < animsprDataSearch.length; i++) {
					animsprDataSearch[i].setPosition(-999, -999);
					animsprDataSearch[i].setVisible(false);
				}
				
				for (int i = 0; i < isTouchCharacter.length; i++) {
					isTouchCharacter[i] = false;
				}
				
				for (int i = 0; i < animsprCharacter.length; i++) {
					isTouchCharacter[i] = false;
					animsprCharacter[i].stopAnimation();
					animsprCharacter[i].clearEntityModifiers();
					animsprCharacter[i].setCurrentTileIndex(0);
				}
				mp3stop();
				
				OGG_A5_21_2_OMEDETO_BIG.play();
				
				final long fDuration[] = new long[] {700,700};
				int fFrame[] = new int[] {0,1};
				animsprSceneFinish.setVisible(true);
				animsprSceneFinish.animate(fDuration, fFrame, 2, new IAnimationListener() {
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
						animsprSceneFinish.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								for (int i = 0; i < sprGray.length; i++) {
									sprGray[i].setVisible(false);
								}
								
								arraySearch.clear();
								animsprSceneFinish.setVisible(false);
								for (int i = 0; i < animsprBongbay.length; i++) {
									animsprBongbay[i].setVisible(false);
								}	
								sprStage1.setVisible(true);
								sprTreeStage1.setPosition(423, 313);
								sprTreeStage1.setScale(1.0f);
								
								startStage1();
					
								spriteSetVisible(false, sprStage2Bg, sprSkyStage2, sprStage2TreeBg1, sprStage2TreeBg2,sprTree1Stage2,
									sprTree2Stage2, sprHouse1, sprWater, sprHouse2, sprBall,sprUfo,sprFlag);
								
								animSetVisible (false, animsprFlower, animsprBalloon, animsprBear, animsprBabyCake, animsprChicken,animsprBabyOctopus
									,animsprFrog, animsprBird, animsprDoor, animsprBeetle, animsprFlag, animsprMonkey, animsprFootball, animsprClown, 
									animsprThrowPaper ,animsprBoysurprise, animsprBabyHappy, animsprBabyBalloon, animsprBabyDrum, animsprBoyBalloon
									,animsprBangBlue,animsprBangYellow);
							}
						}));
					}
				});
			}
		}
		
		//Action
		//horseRiding
		public void animhorseRiding(final AnimatedSprite horseRiding){
			OGG_A5_32_15_UMA.play();
			horseRiding.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(4.0f, horseRiding.getX(), horseRiding.getX()- 550),
				new DelayModifier(0.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						horseRiding.setPosition(horseRiding.getX() + 550, horseRiding.getY());
					}
				})
			));
		}
		
		//Slide Grass
		public void animSlide(final AnimatedSprite slideGrass){
			OGG_A5_36_37_SUBERU.play();
			slideGrass.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(0.7f, slideGrass.getY(), slideGrass.getY() + 20),
					new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							slideGrass.setPosition(slideGrass.getX(), slideGrass.getY()-20);
						}
					})
				));
		}
		
		//Jump or Sway 
		public void actionJumpOrSway(AnimatedSprite actJumporSway , Sound mp3Soundcase0, Sound mp3Soundcase1){
			Random r = new Random();
			int rr = r.nextInt(2);
			switch (rr) {
			case 0:
				mp3Soundcase0.play();
				actJumporSway.registerEntityModifier(new SequenceEntityModifier(
						new MoveYModifier(0.3f, actJumporSway.getY(), actJumporSway.getY() - 15),
						new MoveYModifier(0.3f, actJumporSway.getY() - 15, actJumporSway.getY())
						));
				break;
			case 1:
				mp3Soundcase1.play();
				actJumporSway.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.15f, actJumporSway.getX(), actJumporSway.getX() - 10),
						new MoveXModifier(0.3f, actJumporSway.getX() - 10, actJumporSway.getX() + 10),
						new MoveXModifier(0.15f, actJumporSway.getX() + 10, actJumporSway.getX())));
				break;
			default:
				break;
			}
		}
		
		// Animation
		public void actionAnim(final AnimatedSprite actAnim, int x, int y, int z, int loop){
			final long animDuration[] = new long[] {x,y,z};
			final int animFrame[] = new int []{0,1,2};
	
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
	
		//set visible sprite
		public void spriteSetVisible(boolean value, Sprite ...sSprites){
			for (int i = 0; i < sSprites.length; i++) {
				if(sSprites[i] != null){
					sSprites[i].setVisible(value);
				}
			}
		}
		
		//set visible animation
		public void animSetVisible(boolean value, AnimatedSprite ...animatedSprites){
			for (int i = 0; i < animatedSprites.length; i++) {
				if(animatedSprites[i] != null){
					animatedSprites[i].setVisible(value);
				}
			}
		}
		
		@Override
		public void onModifierStarted(IModifier<IEntity> pIModifier, IEntity arg1) {
			
		}
	
		@Override
		public void onModifierFinished(IModifier<IEntity> pIModifier, IEntity arg1) {
			if (pIModifier.equals(parallUfo)) {
				sprUfo.setPosition(15, 125);
				sprUfo.setRotation(0);
			}
			if (pIModifier == delayBabycake) {
				isTouchsBabyCake = true;
			}
			if(pIModifier == delayChicken) {
				isTouchChicken = true;
			}
			if(pIModifier == delayBabytOctopus){
				isTouchBabytOctopus = true;
			}
			if(pIModifier == delayFlower){
				isTouchFlower = true;
			}
			if(pIModifier == delayBear){
				isTouchBear = true;
			}
			if(pIModifier == delayBalloon){
				isTouchBalloon = true;
			}
			if(pIModifier == delayFrog){
				isTouchFrog = true;
			}
			if(pIModifier == delayBird){
				isTouchBird = true;
			}
			if(pIModifier == delayDoor){
				isTouchDoor = true;
			}
			if(pIModifier == delayFlag){
				isTouchFlag = true;
			}
			if(pIModifier == delayFootball){
				isTouchFootball = true;
			}
			if(pIModifier == delayClown){
				isTouchClown = true;
			}
			if(pIModifier == delayThrowPaper){
				isTouchThrowPaper = true;
				OGG_A5_31_BASA.play();
			}
			if(pIModifier == delayBoysurprise){
				isTouchBoysurprise = true;
			}
			if(pIModifier == delayBabyHappy){
				isTouchBabyHappy = true;
			}
			if(pIModifier == delayBabyBalloon){
				isTouchBabyBalloon = true;
			}
			if(pIModifier == delayBabyDrum){
				isTouchBabyDrum = true;
			}
			if(pIModifier == delayBoyBalloon){
				isTouchBoyBalloon = true;
			}
		}
		
		public void setScaleAnim(float x, AnimatedSprite ...scaleAnim){
			for (int i = 0; i < scaleAnim.length; i++) {
				scaleAnim[i].setScaleCenter(scaleAnim[i].getWidth()/2, scaleAnim[i].getHeight()/2);
				scaleAnim[i].setScale(x);
			}
		}
		
		public void moveUfo(){
			if(parallUfo == null){
				OGG_A5_3_1_UFO.play();
				parallUfo = new ParallelEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new RotationModifier(0.5f, 0, 30),
						new RotationModifier(0.5f, 30, 0))),
					new SequenceEntityModifier(
						new MoveModifier(1.5f,sprUfo.getX(),209,sprUfo.getY(),169, EaseQuartOut.getInstance()),
						new MoveModifier(1.0f, 209, 266, 169, 129, EaseQuartOut.getInstance()),
						new MoveModifier(1.0f, 266, 328, 129, 173, EaseQuartOut.getInstance()),
						new MoveModifier(2.0f, 328, 647, 173, -32, EaseQuartOut.getInstance())));
				
				sprUfo.registerEntityModifier(parallUfo);
				sprUfo.registerUpdateHandler(new IUpdateHandler() {
					
					@Override
					public void reset() {
						
					}
					
					@Override
					public void onUpdate(float arg0) {
						if(sprUfo.getX() == 647){
							sprUfo.clearEntityModifiers();
							sprUfo.setPosition(15, 125);
							sprUfo.setRotation(0);
							parallUfo = null;
						}
					}
				});
				if(parallUfo != null){
					parallUfo.addModifierListener(this);
				}
			}
			return;
		}
		
		public void randomBongbay(){
			rdnBongbay = new RanDomNoRepeat();
			rdnBongbay.setLenghNoRepeat(animsprBongbay.length);
			for (int i = 0; i < 11; i++) {
				rdBongbay[i] = rdnBongbay.getNextIntNoRepeat(true);
			}
			// Sort position
			for (int i = 0; i < rdBongbay.length; i++) {
				animsprBongbay[rdBongbay[i]].setPosition(arrPointBongbay[i][0], arrPointBongbay[i][1]);
				animsprBongbay[rdBongbay[i]].setVisible(true);
			}
		}
		
		public void mp3Bongbay(){
			Random rmp3Bongbay = new Random();
			int mp3bongbay = rmp3Bongbay.nextInt(3);
			switch (mp3bongbay) {
			case 0:
				OGG_A5_HYU1.play();
				break;
			case 1:
				OGG_A5_HYU2.play();
				break;
			default:
				OGG_A5_HYU3.play();
				break;
			}
		}
		
		public void bangBalloon(final AnimatedSprite animsprBang){
			OGG_A5_24_25_WARERU.play();
			final long Duration[] = new long[] {200,500,0};
			final int frame[] = new int[] {0,1,2};
			animsprBang.animate(Duration, frame, 0, new IAnimationListener() {
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
					animsprBang.setVisible(false);
					animsprBang.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							animsprBang.setVisible(true);
							animsprBang.setCurrentTileIndex(0);
						}
					}));
				}
			});
		}
		
		public void stopclearAnimation(AnimatedSprite ...animAnimation){
			for (int i = 0; i < animAnimation.length; i++) {
				if(animAnimation[i] != null){
					animAnimation[i].stopAnimation();
					animAnimation[i].clearEntityModifiers();
					animAnimation[i].setCurrentTileIndex(0);
				}
			}
		}
		
		public void mp3OnOff(Sound ...mp3OnOff){
			for (int i = 0; i < mp3OnOff.length; i++) {
				mp3OnOff[i].stop();
			}
		}
		
		public void ringPosition(Sprite sRing, AnimatedSprite anim){
			float pX = anim.getX() + anim.getWidthScaled()/2 - sRing.getWidthScaled()/2;
			float pY = anim.getY() + anim.getHeightScaled()/2 - sRing.getHeightScaled()/2;
			sRing.setPosition(pX, pY);
		}
		
		public void mp3stop(){
			OGG_A5_21_1_OMEDETO.stop();
			OGG_A5_21_2_OMEDETO_BIG.stop();
			OGG_A5_14_TENTOMUSHI.stop();
			OGG_A5_32_16_ZOU.stop();
			OGG_A5_28_EREKI.stop();
			OGG_A5_39_KODAIKO2.stop();
			OGG_A5_26_HYU.stop();
			OGG_A5_10_KIRAN.stop();
			OGG_A5_5_KUMARAKU.stop();
			OGG_A5_7_KIRA4.stop();
			OGG_A5_32_15_UMA.stop();
			OGG_A5_MUZUKASHI.stop();
			OGG_A5_KOREDOKO.stop();
			OGG_A5_HYOI.stop();
			OGG_A5_POMI.stop();
		}
}
