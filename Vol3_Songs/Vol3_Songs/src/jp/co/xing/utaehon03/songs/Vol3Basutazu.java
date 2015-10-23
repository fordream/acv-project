/* Vol3Basutazu.java
* Create on Jul 11, 2012
*/
package jp.co.xing.utaehon03.songs;


import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;


import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3BasutazuResource;

public class Vol3Basutazu extends BaseGameActivity implements 
				IOnSceneTouchListener, IEntityModifierListener, IAnimationListener {
	
	public final static String TAG = " Vol3Basutazu "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpBasutazu1, ttpBasutazu2, ttpBasutazu3, ttpBasutazu4;
	private TexturePackTextureRegionLibrary ttpLibBasutazu1, ttpLibBasutazu2, ttpLibBasutazu3, ttpLibBasutazu4;
	
	private ITextureRegion trBackground1, trBackground2, trKumo1, trKumo2, trTmpBgd, trMeterBody1,
			trMeterBody2, trMeterStar, trWin1, trWin2, trSun1, trSun2, trSolarnel1, trSolarnel2, trSolarnel3,
			trWindow, trBikeLop, trBike, trBikeStand, trSenpuki2, trSenpuki3, trFarther, trWashmachine3, trWashmachine4,
			trWashmachine5, trGrandma, trMama, trSuccessTmp, trSenpuki, trWashmachine, trRefrigerator, trRenji;
	
	
	private ITextureRegion arrTrOmedeto[] = new ITextureRegion[14];
	private ITextureRegion arrTrKaze[] = new ITextureRegion[4];
	private ITextureRegion arrMeterMemory[] = new ITextureRegion[10];
	private Sprite sBackground1, sBackground2, sKumo1, sKumo2, sTmpBgd, sMeterBody1, sMeterBody2, sMeterStar, 
			sWin1, sWin2, sSun1, sSun2, sSolarnel1, sSolarnel2, sSolarnel3, sWindow, sBikeLop, sBike, sBikeStand,
			sSenpuki2, sSenpuki3, sFarther, sWashmachine3, sWashmachine4,sWashmachine5, sGrandma, sMama, sSuccessTmp1,
			sSuccessTmp2, sSenpuki, sWashmachine, sRefrigerator, sRenji;
	private Sprite arrSKaze[] = new Sprite[4];
	private Sprite arrsMeterMemory[] = new Sprite[10];
	private Sprite arrsOmedeto[] = new Sprite[14];
	private TiledTextureRegion ttrMeterFace, ttrTshirt, ttrTree, ttrSunFace, ttrBoy, ttrBoyLeg, ttrDenki, ttrSenpuki1, 
			ttrWashmachine1,ttrWashmachine2, ttrRefrigerator1, ttrRefrigerator2, ttrTv1, ttrTv2, ttrRenji1,
			ttrRenji2, ttrLight1,ttrLight2;
	private AnimatedSprite aniMeterFace, aniTshirt, aniTree, aniSunFace, aniBoy, aniBoyLeg, aniDenki1,
			aniDenki2, aniDenki3, aniDenki4, aniSenpuki1, aniWashmachine1, aniWashmachine2, aniRefrigerator1,
			aniRefrigerator2, aniTv1, aniTv2, aniRenji1, aniRenji2, aniLight1,aniLight2;
	
	private float arrPointMemory[][] = {
			{460, 460, 460, 460, 460, 460, 460, 460, 460, 460},
			{567, 525, 479, 435, 389, 344, 299, 255, 209, 166}
	};


	private float arrPointKaze[][] = {
			{490, 539, 819, 923},{67, 13, 80, 23}
	};
	private Sound A5_10_ATSUIWA,A5_10_HAI3,A5_10_HAI4, A5_10_MITANA, A5_10_OTOSEI, A5_10_OYOBIDESUKA,
		A5_10_REIZOUKO, A5_12_MADO,A5_12_SORA,A5_13_TV,A5_15_FUSYA,A5_15_SUN,A5_15_TREE,A5_15_TS,
		A5_21_AHAHA,A5_21_TSUITAWA, A5_21_UFUFU, A5_2_RENJI, A5_3_DENKIKITA, A5_3_YA, A5_4_SENPUKI, A5_5_META1,
		A5_5_META10, A5_5_META2,A5_5_META3, A5_5_META4, A5_5_META5, A5_5_META6, A5_5_META7, A5_5_META8 ,A5_5_META9,
		A5_5_META_MACH, A5_5_TUDEN_TAN, A5_6_JITENSYA, A5_6_JITENSYA2, A5_6_JITENSYA_SOKU,
		A5_8_SENTAKUKI, A5_9_ARA, A5_9_OW, A5_9_UGOITANE, A5_KACHI, A5_KADEN_TUDEN, A5_TUDEN_ALL;
	private int currentBike, currentSun, checkSoundMama, checkSoundGrandma;
	private boolean touchTshirt, touchTree, touchWindow, touchMeterFace, checkSenpuki, touchSenpukiOff,checkRefrigerator, 
			touchSenpukiOn, checkWaschmachine, touchWaschmachineOff, touchWaschmachineOn, touchRegtorOff, checkTv, 
			checkRenji, checkLight, touchLightOff, touchRenjiOff, touchTvOff, touchWind, istouchBike, checkMeterFull,
			istouchSun, touchRefrigeratorOn, touchTvOn, touchRenjiOn, touchFather, touchMama, touchGrandma;
	private void initial () {
		touchTshirt = true;
		touchTree = true;
		touchWindow = true;
		touchMeterFace = true;
		checkSenpuki = true;
		touchSenpukiOff = true;
		touchSenpukiOn =true;
		checkWaschmachine = true;
		touchWaschmachineOff = true;
		touchWaschmachineOn = true;
		checkRefrigerator = true;
		touchRegtorOff=true;
		checkTv = true; 
		checkRenji = true;
		checkLight = true;
		touchLightOff = true;
		touchRenjiOff = true;
		touchTvOff = true;
		touchWind = true;
		currentBike = 0;
		currentSun = 0;
		istouchBike = true;
		checkMeterFull = true;
		istouchSun = true;
		touchRefrigeratorOn = true;
		touchTvOn = true;
		touchRenjiOn = true;
		touchFather = true;
		touchGrandma = true;
		touchMama = true;
		checkSoundMama = 0;
		checkSoundGrandma = 0;
	}
	TimerHandler timerWindow;
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("basutazu/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("basutazu/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "basutazu/gfx/");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		ttpBasutazu1 = mTexturePackLoaderFromSource.load("basutazu1.xml");
		ttpBasutazu1.loadTexture();
		ttpLibBasutazu1=ttpBasutazu1.getTexturePackTextureRegionLibrary();
		ttpBasutazu2 = mTexturePackLoaderFromSource.load("basutazu2.xml");
		ttpBasutazu2.loadTexture();
		ttpLibBasutazu2=ttpBasutazu2.getTexturePackTextureRegionLibrary();
		ttpBasutazu3 = mTexturePackLoaderFromSource.load("basutazu3.xml");
		ttpBasutazu3.loadTexture();
		ttpLibBasutazu3=ttpBasutazu3.getTexturePackTextureRegionLibrary();
		ttpBasutazu4 = mTexturePackLoaderFromSource.load("basutazu4.xml");
		ttpBasutazu4.loadTexture();
		ttpLibBasutazu4=ttpBasutazu4.getTexturePackTextureRegionLibrary();
		
		trBackground1=ttpLibBasutazu4.get(Vol3BasutazuResource.A5_23_1_IPHONE_HAIKEI_ID);
		trBackground2=ttpLibBasutazu4.get(Vol3BasutazuResource.A5_19_4_IPHONE_DENKI_ID);
		
		trKumo1 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_16_1_IPHONE_KUMO_ID);
		trKumo2 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_16_2_IPHONE_KUMO_ID);
		trTmpBgd = ttpLibBasutazu2.get(Vol3BasutazuResource.TEMP_BACKGROUND_ID);
		
		for (int i = 0; i < arrTrKaze.length; i++) {
			arrTrKaze[i] = ttpLibBasutazu2.get(Vol3BasutazuResource.PACK_KAZE[i]);
		}
		
		trMeterBody2 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_5_6_IPHONE_METER_ID);
		trMeterBody1 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_5_17_IPHONE_METER_ID);
		
		ttrMeterFace = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_5_1_IPHONE_METER_ID,
				Vol3BasutazuResource.A5_5_2_IPHONE_METER_ID,
				Vol3BasutazuResource.A5_5_3_IPHONE_METER_ID,
				Vol3BasutazuResource.A5_5_5_IPHONE_METER_ID);
		ttrTshirt = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_18_1_IPHONE_TSHIRT_ID,
				Vol3BasutazuResource.A5_18_2_IPHONE_TSHIRT_ID);
		ttrTree = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_20_1_IPHONE_TREE_ID,
				Vol3BasutazuResource.A5_20_2_IPHONE_TREE_ID);
		
		ttrSunFace = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_17_1_IPHONE_SUN_ID,
				Vol3BasutazuResource.A5_17_2_IPHONE_SUN_ID,
				Vol3BasutazuResource.A5_17_3_IPHONE_SUN_ID);
		
		ttrBoy = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_6_1_IPHONE_BOY_ID,
				Vol3BasutazuResource.A5_6_2_IPHONE_BOY_ID,
				Vol3BasutazuResource.A5_6_3_IPHONE_BOY_ID);
		
		ttrBoyLeg = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_6_4_IPHONE_BOY_ID,
				Vol3BasutazuResource.A5_6_5_IPHONE_BOY_ID,
				Vol3BasutazuResource.A5_6_6_IPHONE_BOY_ID);
		ttrDenki = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_19_1_IPHONE_DENKI_ID,
				Vol3BasutazuResource.A5_19_2_IPHONE_DENKI_ID,
				Vol3BasutazuResource.A5_19_3_IPHONE_DENKI_ID);
		ttrSenpuki1 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_4_1_IPHONE_SENPUKI_ID,
				Vol3BasutazuResource.A5_4_2_IPHONE_SENPUKI_ID,
				Vol3BasutazuResource.A5_4_5_IPHONE_SENPUKI_ID);
		trSenpuki = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_4_5_IPHONE_SENPUKI_ID);
		trSenpuki2 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_4_4_IPHONE_SENPUKI_ID);
			
		trSenpuki3 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_4_3_IPHONE_SENPUKI_ID);
			
		ttrWashmachine1 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_8_1_IPHONE_WASHMACHINE_ID,
				Vol3BasutazuResource.A5_8_2_IPHONE_WASHMACHINE_ID,
				Vol3BasutazuResource.A5_8_5_IPHONE_WASHMACHINE_ID);
		
		ttrWashmachine2 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_8_6_IPHONE_WASHMACHINE_ID,
				Vol3BasutazuResource.A5_8_7_IPHONE_WASHMACHINE_ID);
		trWashmachine = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_8_5_IPHONE_WASHMACHINE_ID);
		trWashmachine3 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_8_3_IPHONE_WASHMACHINE_ID);
		trWashmachine4 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_8_4_IPHONE_WASHMACHINE_ID);
		trWashmachine5 = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_8_8_IPHONE_WASHMACHINE_ID);
		trGrandma = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_9_1_IPHONE_GRANDMA_PA_ID);
		trMama = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_21_1_IPHONE_MAMA_ID);
		
		ttrRefrigerator1 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_10_1_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_2_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_7_IPHONE_REFRIGERATOR_ID);
		
		ttrRefrigerator2 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_10_3_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_4_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_5_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_6_IPHONE_REFRIGERATOR_ID,
				Vol3BasutazuResource.A5_10_7_IPHONE_REFRIGERATOR_ID);
		trRefrigerator = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_10_7_IPHONE_REFRIGERATOR_ID);
		ttrTv1 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_13_1_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_2_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_3_IPHONE_TV_ID);
		
		ttrTv2 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_13_7_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_8_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_4_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_5_IPHONE_TV_ID,
				Vol3BasutazuResource.A5_13_6_IPHONE_TV_ID);
		
		
		ttrRenji1 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_2_1_IPHONE_RENJI_ID,
				Vol3BasutazuResource.A5_2_2_IPHONE_RENJI_ID,
				Vol3BasutazuResource.A5_2_7_IPHONE_RENJI_ID);
		trRenji = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_2_7_IPHONE_RENJI_ID);
		ttrRenji2 = getTiledTextureFromPacker(ttpBasutazu1,
				Vol3BasutazuResource.A5_2_6_IPHONE_RENJI_ID,
				Vol3BasutazuResource.A5_2_3_IPHONE_RENJI_ID,
				Vol3BasutazuResource.A5_2_4_IPHONE_RENJI_ID,
				Vol3BasutazuResource.A5_2_5_IPHONE_RENJI_ID);
		
		ttrLight1 = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_14_1_IPHONE_LIGHT_ID,
				Vol3BasutazuResource.A5_14_2_IPHONE_LIGHT_ID,
				Vol3BasutazuResource.A5_14_5_IPHONE_LIGHT_ID);
		
		ttrLight2 = getTiledTextureFromPacker(ttpBasutazu2,
				Vol3BasutazuResource.A5_14_3_IPHONE_LIGHT_ID,
				Vol3BasutazuResource.A5_14_4_IPHONE_LIGHT_ID);
		
		for (int i = 0; i < arrMeterMemory.length; i++) {
			arrMeterMemory[i] = ttpLibBasutazu1.get(Vol3BasutazuResource.PACK_MEMORY[i]);
			
		}
		trMeterStar = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_5_4_IPHONE_METER_ID);
		trWin1 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_15_1_IPHONE_WIND_ID); 
		trWin2 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_15_2_IPHONE_WIND_ID);
		trSun1 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_17_5_IPHONE_SUN_ID);
		trSun2 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_17_4_IPHONE_SUN_ID);
		trSolarnel1 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_12_1_IPHONE_SOLARPANEL_ID);
		trSolarnel2 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_12_2_IPHONE_SOLARPANEL_ID);
		trSolarnel3 = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_12_3_IPHONE_SOLARPANEL_ID);
		trWindow = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_11_1_IPHONE_WINDOW_ID);
		
		trBikeLop = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_7_3_IPHONE_BIKE_ID);
		
		trBike = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_7_1_IPHONE_BIKE_ID);
		trBikeStand = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_7_2_IPHONE_BIKE_ID);
		trFarther = ttpLibBasutazu1.get(Vol3BasutazuResource.A5_3_1_IPHONE_FATHER_ID);
		arrTrOmedeto[0] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_1_IPHONE_OMEDETO_ID);
		arrTrOmedeto[1] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_2_IPHONE_OMEDETO_ID);
		arrTrOmedeto[2] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_3_IPHONE_OMEDETO_ID);
		arrTrOmedeto[3] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_4_IPHONE_OMEDETO_ID);
		arrTrOmedeto[4] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_5_IPHONE_OMEDETO_ID);
		arrTrOmedeto[5] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_6_IPHONE_OMEDETO_ID);
		arrTrOmedeto[6] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_7_1_IPHONE_OMEDETO_ID);
		arrTrOmedeto[7] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_7_2_IPHONE_OMEDETO_ID);
		arrTrOmedeto[8] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_8_IPHONE_OMEDETO_ID);
		arrTrOmedeto[9] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_9_IPHONE_OMEDETO_ID);
		arrTrOmedeto[10] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_10_IPHONE_OMEDETO_ID);
		arrTrOmedeto[11] = ttpLibBasutazu2.get(Vol3BasutazuResource.A5_1_11_IPHONE_OMEDETO_ID);
		arrTrOmedeto[12] = ttpLibBasutazu3.get(Vol3BasutazuResource.A5_1_12_IPHONE_OMEDETO_ID);
		arrTrOmedeto[13] = ttpLibBasutazu3.get(Vol3BasutazuResource.A5_1_13_IPHONE_OMEDETO_ID);
		trSuccessTmp = ttpLibBasutazu3.get(Vol3BasutazuResource.BACKGROUND_SUCCESS_ID);
		
		
		
	}
	

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		sBackground1 = new Sprite(0, 0, trBackground1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground1);
		 
		for (int i = 0; i < arrSKaze.length; i++) {
			arrSKaze[i] = new Sprite(arrPointKaze[0][i], arrPointKaze[1][i],
						arrTrKaze[i], this.getVertexBufferObjectManager());
			mScene.attachChild(arrSKaze[i]);
		}
		sTmpBgd = new Sprite(0, 0, trTmpBgd,  this.getVertexBufferObjectManager());
		mScene.attachChild(sTmpBgd);
		sBackground2 = new Sprite(0, 0, trBackground2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);
		aniDenki4 = new AnimatedSprite(396, 560, ttrDenki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniDenki4);
		setvisiableAniSprite(false, aniDenki4);
		
		//----------------------------------------------
		sSun1 = new Sprite(570, -36, trSun1, this.getVertexBufferObjectManager());
		mScene.attachChild(sSun1);
		
		sSun2 = new Sprite(570, -36, trSun2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSun2);
		sSun2.setVisible(false);
		aniSunFace = new AnimatedSprite(570, -36, ttrSunFace, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSunFace);
		
		sKumo1 = new Sprite(580, 19, trKumo1, this.getVertexBufferObjectManager());
		mScene.attachChild(sKumo1);
		sKumo2 = new Sprite(696, 72, trKumo2, this.getVertexBufferObjectManager());
		mScene.attachChild(sKumo2);
		//-----------------------------------
		sWin2 = new Sprite(535, 142, trWin2, this.getVertexBufferObjectManager());
		mScene.attachChild(sWin2);
		sWin1 = new Sprite(539, 56, trWin1, this.getVertexBufferObjectManager());
		mScene.attachChild(sWin1);
		
		aniTree = new AnimatedSprite(632, 214, ttrTree, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTree);
		
		sSolarnel3 = new Sprite(682, 121, trSolarnel3, this.getVertexBufferObjectManager());
		mScene.attachChild(sSolarnel3);
		
		sSolarnel2 = new Sprite(720, 132, trSolarnel2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSolarnel2);
		sSolarnel2.setVisible(false);
		sSolarnel1 = new Sprite(726, 105, trSolarnel1, this.getVertexBufferObjectManager());
		mScene.attachChild(sSolarnel1);
		sSolarnel1.setVisible(false);
		sWindow = new Sprite(716, 201, trWindow, this.getVertexBufferObjectManager());
		mScene.attachChild(sWindow);
//		sWindow.setVisible(false);
		setvisiableSprite(false, sWindow);
		aniDenki1 = new AnimatedSprite(554, 220, ttrDenki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniDenki1);
		setvisiableAniSprite(false, aniDenki1);
//		aniDenki1.setVisible(false);
		aniTshirt = new AnimatedSprite(526, 194, ttrTshirt, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTshirt);
		//---------------------------------------------
		aniDenki2 = new AnimatedSprite(614, 250, ttrDenki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniDenki2);
		setvisiableAniSprite(false, aniDenki2);
		aniDenki3 = new AnimatedSprite(560, 454, ttrDenki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniDenki3);
		sMeterBody1 = new Sprite(447, 89, trMeterBody1, this.getVertexBufferObjectManager());
		mScene.attachChild(sMeterBody1);
		for (int i = 0; i < arrsMeterMemory.length; i++) {
			arrsMeterMemory[i]= new Sprite(arrPointMemory[0][i], arrPointMemory[1][i], 
					arrMeterMemory[i], this.getVertexBufferObjectManager());
			mScene.attachChild(arrsMeterMemory[i]);
			arrsMeterMemory[i].setVisible(false);
		}
		sMeterBody2 = new Sprite(442, 84, trMeterBody2, this.getVertexBufferObjectManager());
		mScene.attachChild(sMeterBody2);
		aniMeterFace = new AnimatedSprite(449, 24, ttrMeterFace, this.getVertexBufferObjectManager());
		mScene.attachChild(aniMeterFace);
		
		sMeterStar = new Sprite(449, 24, trMeterStar, this.getVertexBufferObjectManager());
		mScene.attachChild(sMeterStar);
		sMeterStar.setVisible(false);
		
		//----------------------------------------
		sBikeLop = new Sprite(700, 514, trBikeLop, this.getVertexBufferObjectManager());
		mScene.attachChild(sBikeLop);
		sBike = new Sprite(534, 451, trBike, this.getVertexBufferObjectManager());
		mScene.attachChild(sBike);
		sBikeStand = new Sprite(534, 451, trBikeStand, this.getVertexBufferObjectManager());
		mScene.attachChild(sBikeStand);
		aniBoy = new AnimatedSprite(584, 271, ttrBoy, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoy);
		aniBoyLeg = new AnimatedSprite(634, 460, ttrBoyLeg, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoyLeg);
		setvisiableAniSprite(false, aniDenki3);
		//-------------------------------------
		
		sSenpuki = new Sprite(262, 394, trSenpuki, this.getVertexBufferObjectManager());
		mScene.attachChild(sSenpuki);
		aniSenpuki1 = new AnimatedSprite(262, 394, ttrSenpuki1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSenpuki1);
		
		sSenpuki2 = new Sprite(271, 397, trSenpuki2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSenpuki2);
		sSenpuki2.setVisible(false);
		sSenpuki3 = new Sprite(269, 397, trSenpuki3, this.getVertexBufferObjectManager());
		mScene.attachChild(sSenpuki3);
		sSenpuki3.setVisible(false);
		//--------------------------------------
		sMama = new Sprite(16, 82, trMama, this.getVertexBufferObjectManager());
		mScene.attachChild(sMama);
		setvisiableSprite(false, sMama);
		aniTv2 = new AnimatedSprite(45, 189, ttrTv2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTv2);
		aniTv2.setVisible(false);
		aniTv1 = new AnimatedSprite(45, 189, ttrTv1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTv1);
		//---------------------------
		sGrandma = new Sprite(13, 335, trGrandma, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrandma);
		setvisiableSprite(false, sGrandma);
//		sWashmachine4 = new Sprite(40, 436, trWashmachine4, this.getVertexBufferObjectManager());
//		mScene.attachChild(sWashmachine4);
//		sWashmachine4.setVisible(false);
		sWashmachine5 = new Sprite(65, 480, trWashmachine5, this.getVertexBufferObjectManager());
		mScene.attachChild(sWashmachine5);
		sWashmachine5.setVisible(false);
		aniWashmachine2 = new AnimatedSprite(40, 436, ttrWashmachine2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniWashmachine2);
		aniWashmachine2.setVisible(false);
		sWashmachine =  new Sprite(40, 436, trWashmachine, this.getVertexBufferObjectManager());
		mScene.attachChild(sWashmachine);
		aniWashmachine1 = new AnimatedSprite(40, 436, ttrWashmachine1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniWashmachine1);
		sWashmachine4 = new Sprite(40, 436, trWashmachine4, this.getVertexBufferObjectManager());
		mScene.attachChild(sWashmachine4);
		sWashmachine4.setVisible(false);
		sWashmachine3 = new Sprite(40, 436, trWashmachine3, this.getVertexBufferObjectManager());
		mScene.attachChild(sWashmachine3);
		sWashmachine3.setVisible(false);
		sRefrigerator = new Sprite(260, 134, trRefrigerator, this.getVertexBufferObjectManager());
		mScene.attachChild(sRefrigerator);
		
		aniRefrigerator1 = new AnimatedSprite(260, 134, ttrRefrigerator1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRefrigerator1);
		aniRefrigerator2 = new AnimatedSprite(260, 134, ttrRefrigerator2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRefrigerator2);
		aniRefrigerator2.setVisible(false);
		//-------------------------------------
		sRenji = new Sprite(283, 40, trRenji, this.getVertexBufferObjectManager());
		mScene.attachChild(sRenji);
		aniRenji1 = new AnimatedSprite(283, 40, ttrRenji1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRenji1);
		
		aniRenji2 = new AnimatedSprite(283, 40, ttrRenji2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRenji2);
		aniRenji2.setVisible(false);
		
		//-------------------------------------
		aniLight1 = new AnimatedSprite(91, -2, ttrLight1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniLight1);
		
		aniLight2 = new AnimatedSprite(91, -2, ttrLight2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniLight2);
		aniLight2.setVisible(false);
		//-------------------------------------------
		sFarther = new Sprite(180, 490, trFarther, this.getVertexBufferObjectManager());
		mScene.attachChild(sFarther);
		sFarther.setVisible(false);
		
		arrsOmedeto[13] = new Sprite(0, 0, arrTrOmedeto[13], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[13]);
		
		arrsOmedeto[12] = new Sprite(-21, 410, arrTrOmedeto[12], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[12]);
		
		arrsOmedeto[0] = new Sprite(75, 4, arrTrOmedeto[0], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[0]);
		
		sSuccessTmp2 =  new Sprite(0, 0, trSuccessTmp, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuccessTmp2);
		sSuccessTmp1 =  new Sprite(0, 0, trSuccessTmp, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuccessTmp1);
		
		
		arrsOmedeto[10] = new Sprite(69, 75, arrTrOmedeto[10], this.getVertexBufferObjectManager());
		sSuccessTmp1.attachChild(arrsOmedeto[10]);
		arrsOmedeto[11] = new Sprite(218, 38, arrTrOmedeto[11], this.getVertexBufferObjectManager());
		sSuccessTmp2.attachChild(arrsOmedeto[11]);
		arrsOmedeto[9] = new Sprite(150, 146, arrTrOmedeto[9], this.getVertexBufferObjectManager());
		sSuccessTmp1.attachChild(arrsOmedeto[9]);
		arrsOmedeto[8] = new Sprite(259, 123, arrTrOmedeto[8], this.getVertexBufferObjectManager());
		sSuccessTmp2.attachChild(arrsOmedeto[8]);
		
		arrsOmedeto[6] = new Sprite(367, 7, arrTrOmedeto[6], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[6]);
		arrsOmedeto[7] = new Sprite(492, 42, arrTrOmedeto[7], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[7]);
		arrsOmedeto[5] = new Sprite(676, 97, arrTrOmedeto[5], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[5]);
		arrsOmedeto[2] = new Sprite(124, 38, arrTrOmedeto[2], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[2]);
		arrsOmedeto[1] = new Sprite(701, 160, arrTrOmedeto[1], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[1]);
		
		arrsOmedeto[3] = new Sprite(550, 475, arrTrOmedeto[3], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[3]);
		arrsOmedeto[4] = new Sprite(78, 473, arrTrOmedeto[4], this.getVertexBufferObjectManager());
		mScene.attachChild(arrsOmedeto[4]);
		for (int i = 0; i < arrsOmedeto.length; i++) {
			arrsOmedeto[i].setVisible(false);
			
		}
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pTouchEvent.getX();
		int pY = (int) pTouchEvent.getY();
		if (pTouchEvent.isActionDown()) {
			if (checkContains(aniTshirt, 4, 10, 62, 55, pX, pY)){
				touchTshirt();
			} else if (checkContains(aniTree, 6, 4, 64, 45, pX, pY)) {
				touchTree();
			} else if (checkContains(sSun1, 77, 73, 134, 144, pX, pY) || 
					checkContains(sSolarnel3, 50,3, 247, 78, pX, pY)){
				touchSun(currentSun);
			} 
			else if (checkContains(sWin1, 10, 9, 138,127, pX, pY)) {
				touchWind();
			}
			if (checkContains(aniBoy, 79, 23, 172, 208, pX, pY) || 
					checkContains(sBike, 21, 47, 259, 160, pX, pY)) {
				touchBike(currentBike);
			} else if (checkContains(sSolarnel3, 56, 94, 121, 147, pX, pY)) {
				touchSolarnel3();
			}
			if (checkContains(sMeterBody2, 8, 9, 90, 88, pX, pY)) {
				touchMeter();
			}
			if (checkContains(sFarther, 8, 8, 130, 209, pX, pY) && sFarther.isVisible()) {
				touchFather();
			}
			
			if (checkContains(sMama, 9, 9, 116, 136, pX, pY)&& sMama.isVisible()) {
				touchMama();
			}

			if (checkContains(aniSenpuki1, 9, 9, 108, 210, pX, pY)&&aniSenpuki1.isVisible()) {
				if (checkSenpuki) {
					//khi quat chua co dien
					senpukiOff();
				}else {
					//khi co dien
					senpukiOn();
				}
			}
			if (checkContains(aniWashmachine1, 14, 4, 150, 168, pX, pY) && aniWashmachine1.isVisible()) {
				if (checkWaschmachine) {
					//khi may giat chua co dien
					WaschmachineOff();
				}else {
					//khi co dien
					WaschmachineOn();
				}
			}
			if (checkContains(aniRefrigerator1, 9, 7, 146, 245, pX, pY) && aniRefrigerator1.isVisible()) {
				if (checkRefrigerator) {
					//khi tu lanh chua co dien
					refrigeratorOff();
				}else {
					//khi co dien
					refrigeratorOn();
				}
			}
			if (checkContains(sGrandma, 21, 15, 195, 98, pX, pY) && sGrandma.isVisible()) {
				touchGrandma();
			}else
			if (checkContains(aniTv1, 3, 5, 181, 184, pX, pY) && aniTv1.isVisible()) {
				if (checkTv) {
				//khi tv chua co dien
					tvOff();
				}else {
					//khi co dien
					tvOn();
				}
			}
			if (checkContains(aniRenji1, 9, 6, 122, 95, pX, pY) && aniRenji1.isVisible()) {
				if (checkRenji) {
				//khi lo vi song chua co dien
					renjiOff();
				}else {
					//khi co dien
					renjiOn();
				}
			} 
			if (checkContains(aniLight1, 16, 23, 164, 70, pX, pY) && aniLight1.isVisible()) {
				if (checkLight) {
				//khi den chua co dien
					lightOff();
				}
			}
			if (arrsMeterMemory[0].contains(pX, pY) && arrsMeterMemory[0].isVisible()) {
				A5_5_META1.play();
			}else if (arrsMeterMemory[1].contains(pX, pY)&&arrsMeterMemory[1].isVisible()) {
				A5_5_META2.play();
			}else if (arrsMeterMemory[2].contains(pX, pY)&&arrsMeterMemory[2].isVisible()) {
				A5_5_META3.play();
			}else if (arrsMeterMemory[3].contains(pX, pY)&&arrsMeterMemory[3].isVisible()) {
				A5_5_META4.play();
			}else if (arrsMeterMemory[4].contains(pX, pY)&&arrsMeterMemory[4].isVisible()) {
				A5_5_META5.play();
			}else if (arrsMeterMemory[5].contains(pX, pY)&&arrsMeterMemory[5].isVisible()) {
				A5_5_META6.play();
			}else if (arrsMeterMemory[6].contains(pX, pY)&&arrsMeterMemory[6].isVisible()) {
				A5_5_META7.play();
			}else if (arrsMeterMemory[7].contains(pX, pY)&&arrsMeterMemory[7].isVisible()) {
				A5_5_META8.play();
			}else if (arrsMeterMemory[8].contains(pX, pY)&&arrsMeterMemory[8].isVisible()) {
				A5_5_META9.play();
			}else if (arrsMeterMemory[9].contains(pX, pY)&&arrsMeterMemory[9].isVisible()) {
				A5_5_META10.play();
			}
			
		}
		return false;
	}
	
	private void touchSun(int crtTouch) {
		if (istouchSun&&checkMeterFull){
			istouchSun= false;
			touchWind = false;
			istouchBike=false;
			A5_15_SUN.play();
			A5_12_SORA.play();
			setvisiableAniSprite(true, aniDenki2);
			aniDenki2.animate(100, -1);
			aniDenki2.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.4f, aniDenki2.getX(), aniDenki2.getX()-85),
					new MoveYModifier(0.8f, aniDenki2.getY(), aniDenki2.getY()+155),
					new MoveModifier(0.2f, aniDenki2.getX()-85, aniDenki2.getX()-128, 
							aniDenki2.getY()+155, aniDenki2.getY()+168, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							setvisiableAniSprite(false, aniDenki2);
							aniDenki2.stopAnimation();
							istouchSun= true;
							touchWind = true;
							istouchBike=true;
							appeaMeter();
						}
					})));
			sSolarnel1.setVisible(true);
			sSolarnel1.setAlpha(0);
			sSolarnel1.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.2f),
					new AlphaModifier(0.5f, 0, 1),new DelayModifier(0.2f),
					new AlphaModifier(0.5f, 1, 0, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sSolarnel1.setVisible(false);
						}
					})
					));
			sSolarnel2.setVisible(true);
			sSolarnel2.setAlpha(0);
			sSolarnel2.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.2f),
					new AlphaModifier(0.5f, 0, 1),new DelayModifier(0.2f),
					new AlphaModifier(0.5f, 1, 1, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sSolarnel2.setVisible(false);
						}
					})
					));
			if (crtTouch==0) {
				//tap lan 1
				sKumo1.setVisible(false);
				sKumo2.setVisible(false);
				aniSunFace.setCurrentTileIndex(1);
				
				currentSun++;
			} else if (crtTouch==1) {
				aniSunFace.setCurrentTileIndex(2);
				currentSun++;
			} else if (crtTouch==2) {
				sSun2.setVisible(true);
				currentSun++;
			} else if (crtTouch >= 3) {
				sSun2.registerEntityModifier(new RotationAtModifier(1.8f, 0, -360, 
						sSun2.getWidth()/2, sSun2.getHeight()/2));
				currentSun++;
			}
			
		}
	}
	private void touchBike(int crtTouch) {
		if (istouchBike&&checkMeterFull) {
			istouchBike=false;
			istouchSun= false;
			touchWind = false;
			setvisiableAniSprite(true, aniDenki3);
			aniDenki3.animate(100, -1);
			
			aniDenki3.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.4f, aniDenki3.getX(), aniDenki3.getX()-60),
					new MoveYModifier(0.2f, aniDenki3.getY(), aniDenki3.getY()-25, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							setvisiableAniSprite(false, aniDenki3);
							aniDenki3.stopAnimation();
							aniDenki3.setCurrentTileIndex(0);
							appeaMeter();
						}
					})));
			sBikeLop.registerEntityModifier(new RotationAtModifier(2.5f, 0,-720, 
					sBikeLop.getWidth()/2, sBikeLop.getHeight()/2, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							istouchBike=true;
							istouchSun= true;
							touchWind = true;
						}
					}));
			long pDuration[]={200,200};
			int pFrame[]={1,0};
			if (crtTouch==0) {
				A5_6_JITENSYA.play();
				aniBoyLeg.animate(pDuration, pFrame, 4);
				currentBike++;
				
			} else if (crtTouch==1) {
				A5_6_JITENSYA2.play();
				aniBoy.setCurrentTileIndex(1);
				aniBoyLeg.animate(pDuration, pFrame, 4);
				currentBike++;
			} else if (crtTouch >=2) {
				A5_6_JITENSYA_SOKU.play();
				aniBoyLeg.setCurrentTileIndex(2);
				aniBoyLeg.registerEntityModifier(new RotationModifier(2.0f, 0, -1440));
				currentBike++;
			}
		}
		
		return;
	}
	private void touchWind(){
		if (touchWind&&checkMeterFull) {
			touchWind = false;
			touchTshirt=false;
			touchTree = false;
			istouchSun= false;
			istouchBike=false;
			A5_15_FUSYA.play();
			A5_15_TS.play();
			A5_15_TREE.play();
			
//			aniTshirt.setCurrentTileIndex(1);
			aniTshirt.animate(new long[]{200,200,200,200,200,200,200,200},new int[]{1,0,1,0,1,0,1,0}, 0);
			aniTree.setCurrentTileIndex(1);
			setvisiableAniSprite(true, aniDenki1);
			aniDenki1.animate(100, -1);
			aniDenki1.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.3f, aniDenki1.getX(), aniDenki1.getX()-50),
					new MoveYModifier(0.9f, aniDenki1.getY(), aniDenki1.getY()+200, 
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							setvisiableAniSprite(false, aniDenki1);
							aniDenki1.stopAnimation();
							aniDenki1.setCurrentTileIndex(0);
							appeaMeter();
							
						}
					})));
			sWin1.registerEntityModifier(new RotationAtModifier(1.8f, 0,-720,74,86, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					touchWind = true;
					istouchSun = true;
					istouchBike = true;
					aniTshirt.setCurrentTileIndex(0);
					aniTree.setCurrentTileIndex(0);
					touchTshirt=true;
					touchTree = true;
				}
			}));
		}
	}
	private void appeaMeter(){
		Log.d(TAG, " value isvisible: "+ arrsMeterMemory[0].isVisible());
		A5_5_TUDEN_TAN.play();
		if (!arrsMeterMemory[0].isVisible()) {
			
			arrsMeterMemory[0].setVisible(true);
			mEngine.clearUpdateHandlers();
			mEngine.unregisterUpdateHandler(timerWindow);
			aniMeterFace.setCurrentTileIndex(2);
			senpukiMeter();
			touchMeterFace = true;
			
		}else if (!arrsMeterMemory[1].isVisible()) {
			
			arrsMeterMemory[1].setVisible(true);
		}else if (!arrsMeterMemory[2].isVisible()){
			
			arrsMeterMemory[2].setVisible(true);
			washmachineMeter();
		}else if (!arrsMeterMemory[3].isVisible()){
			
			arrsMeterMemory[3].setVisible(true);
		}else if (!arrsMeterMemory[4].isVisible()){
			
			arrsMeterMemory[4].setVisible(true);
			refrigeratorMeter();
		}else if (!arrsMeterMemory[5].isVisible()){
			
			arrsMeterMemory[5].setVisible(true);
		}else if (!arrsMeterMemory[6].isVisible()){
			
			arrsMeterMemory[6].setVisible(true);
			tvMeter();
		}else if (!arrsMeterMemory[7].isVisible()){
			mEngine.clearUpdateHandlers();
			mEngine.unregisterUpdateHandler(timerWindow);
			aniMeterFace.setCurrentTileIndex(3);
			arrsMeterMemory[7].setVisible(true);
			touchMeterFace = true;
		}else if (!arrsMeterMemory[8].isVisible()){
			
			arrsMeterMemory[8].setVisible(true);
			renjiMeter();
		}else if (!arrsMeterMemory[9].isVisible()){
			
			sMeterStar.setVisible(true);
			arrsMeterMemory[9].setVisible(true);
			lightMeter();
		}
		
	}
	private void senpukiMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		
		aniDenki4.registerEntityModifier(new MoveXModifier(0.6f, aniDenki4.getX(),
				aniDenki4.getX()-90, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				A5_KADEN_TUDEN.play();
				setvisiableAniSprite(false, aniDenki4);
				aniDenki4.stopAnimation();
				aniSenpuki1.stopAnimation();
				aniSenpuki1.setCurrentTileIndex(2);
				sSenpuki2.setVisible(true);
				sSenpuki3.setVisible(true);
				checkSenpuki=false;
				checkMeterFull=true;
				touchFather = false;
				sFarther.setPosition(sFarther.getmXFirst(), sFarther.getmYFirst()+100);
				sFarther.setVisible(true);
				sFarther.registerEntityModifier(new MoveYModifier(1.5f, 
						sFarther.getmYFirst()+100, sFarther.getmYFirst(),new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						touchFather = true;
					}
				}));
				A5_3_DENKIKITA.play();
			}
		}));
		
	}
	private void washmachineMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		
		aniDenki4.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.2f, aniDenki4.getX(),aniDenki4.getX()-36),
				new MoveYModifier(0.15f, aniDenki4.getY(), aniDenki4.getY()+20),
				new MoveXModifier(0.75f, aniDenki4.getX()-40, aniDenki4.getX()-245, 
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableAniSprite(false, aniDenki4);
						aniDenki4.stopAnimation();
						A5_KADEN_TUDEN.play();
						aniWashmachine1.stopAnimation();
						aniWashmachine1.setCurrentTileIndex(2);
						checkMeterFull = true;
						checkWaschmachine = false;
						sWashmachine3.setVisible(true);
						sWashmachine4.setVisible(true);
						A5_9_UGOITANE.play();
						touchGrandma = false;
						sGrandma.setPosition(sGrandma.getmXFirst(), sGrandma.getmYFirst()+100);
						sGrandma.setVisible(true);
						sGrandma.registerEntityModifier(
								new MoveYModifier(1.5f, sGrandma.getmYFirst()+100, 
										sGrandma.getmYFirst(), new IEntityModifierListener() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										// TODO Auto-generated method stub
										touchGrandma = true;
									}
								}));
					}
				})));
	}
	private void refrigeratorMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		aniDenki4.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.2f, aniDenki4.getX(),aniDenki4.getX()-36),
				new MoveYModifier(1.0f, aniDenki4.getY(), aniDenki4.getY()-210 ,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableAniSprite(false, aniDenki4);
						aniDenki4.stopAnimation();
						A5_KADEN_TUDEN.play();
						aniRefrigerator1.stopAnimation();
						aniRefrigerator1.setCurrentTileIndex(2);
						checkMeterFull=true;
						checkRefrigerator = false;
					}
				})));
	
	}
	private void tvMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		aniDenki4.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.2f, aniDenki4.getX(),aniDenki4.getX()-36),
				new MoveYModifier(0.9f, aniDenki4.getY(), aniDenki4.getY()-196),
				new MoveXModifier(0.6f, aniDenki4.getX()-36, aniDenki4.getX()-196,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkMeterFull=true;
						checkTv=false;
						A5_KADEN_TUDEN.play();
						setvisiableAniSprite(false, aniDenki4);
						aniDenki4.stopAnimation();
						aniTv1.stopAnimation();
						aniTv1.setCurrentTileIndex(2);
						aniTv2.setVisible(true);
						A5_21_TSUITAWA.play();
						sMama.setPosition(sMama.getmXFirst(), sMama.getmYFirst()+100);
						touchMama = false;
						sMama.setVisible(true);
						sMama.registerEntityModifier(new MoveYModifier(1.5f, 
								sMama.getmYFirst()+100, sMama.getmYFirst(), new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								touchMama = true;
							}
						}));
					}
				}))); 
	}
	private void renjiMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		aniDenki4.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1.3f, aniDenki4.getY(), aniDenki4.getY()-475),
				new MoveXModifier(0.1f, aniDenki4.getX(), aniDenki4.getX()-8, 
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						setvisiableAniSprite(false, aniDenki4);
						aniDenki4.stopAnimation();
						aniDenki4.setCurrentTileIndex(0);
						checkMeterFull=true;
						checkRenji=false;
						A5_KADEN_TUDEN.play();
						aniRenji1.stopAnimation();
						aniRenji1.setCurrentTileIndex(2);
						aniRenji2.setVisible(true);
						
						
					}
				})));
	}
	private void lightMeter(){
		setvisiableAniSprite(true, aniDenki4);
		aniDenki4.animate(100, -1);
		checkMeterFull=false;
		aniDenki4.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1.5f, aniDenki4.getY(), aniDenki4.getY()-576), 
				new MoveXModifier(0.7f, aniDenki4.getX(), aniDenki4.getX()-240,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						setvisiableAniSprite(false, aniDenki4);
						aniDenki4.stopAnimation();
						aniDenki4.setCurrentTileIndex(0);
						checkLight = false;
						A5_15_SUN.play();
						aniLight1.stopAnimation();
						aniLight1.setCurrentTileIndex(2);
						aniLight2.setVisible(true);
						aniLight2.animate(new long[]{200,200,200,200,200,200,200}, 
								new int[]{0,1,0,1,0,1,0}, 0 , new IAnimationListener() {
							
							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
							
							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
							
							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
								if (arg1 == 0) {
									Log.d(TAG, "you dang o frame: " + arg1);
									aniBoy.setCurrentTileIndex(2);
									setvisiableAniSprite(false, aniBoyLeg);
								}
								
							}
							
							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								Log.d(TAG, "you da ket thuc tro choi" );
								successGame();
								A5_TUDEN_ALL.play();
								
								
							}
						});
						
					}
				})));
	}
	private void successGame(){
		// an cac du lieu
		setvisiableSprite(false, sFarther, sMama, sGrandma, sSenpuki2, sSenpuki3, sWashmachine3, sWashmachine4,
				sWashmachine5, sSun2, sSolarnel2, sMeterStar);
		setvisiableAniSprite(false, aniSenpuki1,aniWashmachine1, aniWashmachine2,aniRefrigerator1,aniRefrigerator2,
				aniTv1, aniTv2,aniRenji1, aniRenji2, aniLight1, aniLight2, aniMeterFace,aniSunFace, aniBoy);
		touchTshirt = touchTree = touchWindow = touchMeterFace= false;
		for (int i = 0; i < arrsMeterMemory.length; i++) {
			arrsMeterMemory[i].setVisible(false);
		}
		//hien thi man hinh chien thang
		for (int i = 0; i < arrsOmedeto.length; i++) {
			arrsOmedeto[i].setVisible(true);
		}
		arrsOmedeto[1].registerEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(0.3f, 1, 0), new DelayModifier(0.5f),
				new AlphaModifier(0.3f, 0, 1), new DelayModifier(0.5f),
				new AlphaModifier(0.3f, 1, 0),new DelayModifier(0.5f),
				new AlphaModifier(0.3f, 0, 1), new DelayModifier(0.5f),
				new AlphaModifier(0.3f, 1, 0),new DelayModifier(0.5f),
				new AlphaModifier(0.3f, 0, 1)));
		
		arrsOmedeto[5].registerEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.7f, 0, 20, 54, 172),
				new RotationAtModifier(1.4f, 20, -20, 54, 172),
				new RotationAtModifier(0.7f, -20, 0, 54, 172)));
		
		arrsOmedeto[3].registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(2.5f, arrsOmedeto[3].getX(), arrsOmedeto[3].getX()+150), 
				new MoveXModifier(2.5f, arrsOmedeto[3].getX()+150, arrsOmedeto[3].getX())) );
		arrsOmedeto[6].registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.8f, arrsOmedeto[6].getY(), arrsOmedeto[6].getY()-10),
				new MoveYModifier(0.8f, arrsOmedeto[6].getY()-10, arrsOmedeto[6].getY()),
				new MoveYModifier(0.8f, arrsOmedeto[6].getY(), arrsOmedeto[6].getY()-10),
				new MoveYModifier(0.8f, arrsOmedeto[6].getY()-10, arrsOmedeto[6].getY())));
		
		
		arrsOmedeto[7].registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(1.0f, arrsOmedeto[7].getX(), arrsOmedeto[7].getX()+35, 
						arrsOmedeto[7].getY(), arrsOmedeto[7].getY()-135, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								setvisiableSprite(true, arrsOmedeto[7]);
							}
						}),
				new MoveModifier(1.0f, arrsOmedeto[7].getX(), arrsOmedeto[7].getX()+35, 
										arrsOmedeto[7].getY(), arrsOmedeto[7].getY()-135, new IEntityModifierListener() {
											
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												
											}
											
											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												// TODO Auto-generated method stub
												setvisiableSprite(false, arrsOmedeto[7]);
											}
										})));
		arrsOmedeto[4].registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(2.5f, arrsOmedeto[4].getX(), arrsOmedeto[4].getX()-150), 
				new MoveXModifier(2.5f, arrsOmedeto[4].getX()-150, arrsOmedeto[4].getX(), new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						resetGame();
						//TODO
					}
				})));
		sSuccessTmp1.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.8f, sSuccessTmp1.getY(), sSuccessTmp1.getY()-10),
				new MoveYModifier(0.8f, sSuccessTmp1.getY()-10, sSuccessTmp1.getY()),
				new MoveYModifier(0.8f, sSuccessTmp1.getY(), sSuccessTmp1.getY()-10),
				new MoveYModifier(0.8f, sSuccessTmp1.getY()-10, sSuccessTmp1.getY())));
		sSuccessTmp2.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.3f),
				new MoveYModifier(0.8f, sSuccessTmp2.getY(), sSuccessTmp2.getY()-10),
				new MoveYModifier(0.8f, sSuccessTmp2.getY()-10, sSuccessTmp2.getY()),
				new MoveYModifier(0.8f, sSuccessTmp2.getY(), sSuccessTmp2.getY()-10),
				new MoveYModifier(0.8f, sSuccessTmp2.getY()-10, sSuccessTmp2.getY())));
		
	}
	
	private void resetGame() {
		// an man hinh chien thang
		Log.d(TAG, "gia tri check meterfull: "+ checkMeterFull);
		for (int i = 0; i < arrsOmedeto.length; i++) {
			arrsOmedeto[i].setVisible(false);
		}
		// hien thi man hinh
		setvisiableSprite(true, sKumo1, sKumo2);
		setvisiableAniSprite(true, aniSenpuki1,aniWashmachine1,aniRefrigerator1, aniTv1, aniRenji1,
				aniLight1, aniMeterFace,aniSunFace, aniBoy, aniBoyLeg);
		checkMeterFull= checkSenpuki= checkWaschmachine= checkRefrigerator= checkTv= checkRenji=
			checkLight= touchTshirt= touchTree= touchWindow= touchMeterFace= istouchSun = touchWind= istouchBike = true;
		touchLightOff=touchSenpukiOff=touchWaschmachineOff=touchRegtorOff=touchTvOff=touchRenjiOff=true;
		currentBike = 0;
		currentSun = 0;
		checkSoundMama = 0;
		checkSoundGrandma = 0;
		
	}
	
	private void lightOff() {
		if (touchLightOff) {
			touchLightOff=false;
			A5_KACHI.play();
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			aniLight1.animate(pDuration, pFrame, 0, this);
		}
	}
	private void renjiOff() {
		if (touchRenjiOff) {
			touchRenjiOff = false;
			A5_KACHI.play();
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			aniRenji1.animate(pDuration, pFrame, 0, this);
		}
	}
	private void renjiOn() {
		if (touchRenjiOn) {
			
			touchRenjiOn = false;
			int id_image = new Random().nextInt(3);
			long pDuration[]= {900,400};
			A5_2_RENJI.play();
			switch (id_image) {
			case 0:
				int pFrame[]={1,0};
				aniRenji2.animate(pDuration, pFrame, 0, this);
				break;
			case 1:
				int pFrame1[]={2,0};
				aniRenji2.animate(pDuration, pFrame1, 0, this);
				break;
			case 2:
				int pFrame2[]={3,0};
				aniRenji2.animate(pDuration, pFrame2, 0, this);
				break;
			default:
				break;
			}
			
		}
	}
	private void tvOff() {
		if (touchTvOff) {
			touchTvOff=false;
			A5_KACHI.play();
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			aniTv1.animate(pDuration, pFrame, 0, this);
		}
	}
	private void tvOn(){
		if (touchTvOn){
			touchTvOn= false;
			long pDuration[]= {400,400, 400, 400, 400, 400, 400};
			int pFrame[]={1,2,3,1,4,1,0};
			A5_13_TV.play();
			aniTv2.animate(pDuration, pFrame, 0, this);
			
		}
	}
	private void refrigeratorOff() {
		if (touchRegtorOff) {
			touchRegtorOff=false;
			A5_KACHI.play();
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			aniRefrigerator1.animate(pDuration, pFrame, 0, this);
		}
	}
	private void refrigeratorOn() {
		if (touchRefrigeratorOn) {
			touchRefrigeratorOn=false;
			A5_10_REIZOUKO.play();
			int id_image =  new Random().nextInt(4);
			int id_sound =  new Random().nextInt(3);
			aniRefrigerator2.setVisible(true);
			long pDuration[] = {1200, 400};
			
			switch (id_image) {
			case 0:
				int pFrame [] = {0, 4};
				A5_10_HAI4.play();
				aniRefrigerator2.animate(pDuration, pFrame, 0, this);
				break;
			case 1:
				int pFrame1 [] = {1, 4};
				A5_10_HAI3.play();
				aniRefrigerator2.animate(pDuration, pFrame1, 0, this);
				break;
			case 2:
				int pFrame2 [] = {2, 4};
				A5_10_OTOSEI.play();
				aniRefrigerator2.animate(pDuration, pFrame2, 0, this);
				break;
				
			case 3:
				int pFrame3 [] = {3, 4};
				if (id_sound==0) {
					A5_10_ATSUIWA.play();
				}else if (id_sound==1){
					A5_10_OYOBIDESUKA.play();
				}else if (id_sound==2) {
					A5_10_MITANA.play();
				}
				aniRefrigerator2.animate(pDuration, pFrame3, 0, this);
				break;

			default:
				break;
			}
			
		}
	}
	private void WaschmachineOff() {
		if (touchWaschmachineOff) {
			touchWaschmachineOff = false;
			A5_KACHI.play();
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			aniWashmachine1.animate(pDuration, pFrame, 0, this);
		}
	}
	private void WaschmachineOn() {
		if (touchWaschmachineOn) {
			A5_8_SENTAKUKI.play();
			long pDuration[]= {200,200};
			int pFrame[]={1,0};
			touchWaschmachineOn = false;
			sWashmachine3.setVisible(false);
			sWashmachine4.setVisible(false);
			aniWashmachine2.setVisible(true);
			aniWashmachine2.animate(pDuration, pFrame, 2);
			sWashmachine5.setVisible(true);
			sWashmachine5.registerEntityModifier(
					new RotationAtModifier(1.5f, 0, 720, 
						sWashmachine5.getWidth()/2, sWashmachine5.getHeight()/2, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sWashmachine3.setVisible(true);
							aniWashmachine2.setVisible(false);
							aniWashmachine2.stopAnimation();
							aniWashmachine2.setCurrentTileIndex(0);
							touchWaschmachineOn = true;
							sWashmachine5.setVisible(false);
							sWashmachine4.setVisible(true);
						}
					}));
			
		}
	}
	private void senpukiOff() {
		if (touchSenpukiOff){
			touchSenpukiOff = false;
			long pDuration[]= {600,400};
			int pFrame[]={1,0};
			A5_KACHI.play();
			aniSenpuki1.animate(pDuration,pFrame,0,this);
		}
	}
	private void senpukiOn() {
		if (touchSenpukiOn) {
			touchSenpukiOn = false;
			A5_4_SENPUKI.play();
			sSenpuki2.registerEntityModifier(new RotationModifier(0.8f, 0, 720, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					touchSenpukiOn = true;
				}
			}));
		}
	}
	private void touchGrandma() {
		if (touchGrandma) {
			touchGrandma = false;
			if (checkSoundGrandma==0) {
				checkSoundGrandma = 1;
				A5_9_ARA.play();
			}else if (checkSoundGrandma==1) {
				A5_9_OW.play();
				checkSoundGrandma = 0;
			}
			sGrandma.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(0.5f, sGrandma.getY(), sGrandma.getY()-20), 
					new MoveYModifier(0.5f, sGrandma.getY()-20, sGrandma.getY(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchGrandma = true;
						}
					})));
			
			
		}
		
	}
	private void touchMama(){
		if (touchMama) {
			touchMama = false;
			if (checkSoundMama==0) {
				checkSoundMama = 1;
				A5_21_AHAHA.play();
			}else if (checkSoundMama==1) {
				A5_21_UFUFU.play();
				checkSoundMama = 0;
			}
			sMama.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMama.getY(), sMama.getY()-20),
					new MoveYModifier(0.5f, sMama.getY()-20, sMama.getY(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchMama = true;
						}
					})));
		}
	}
	private void touchFather() {
		if (touchFather) {
			touchFather = false;
			A5_3_YA.play();
			sFarther.registerEntityModifier(new SequenceEntityModifier( 
					new MoveYModifier(0.5f, sFarther.getY(), sFarther.getY()-20), 
					new MoveYModifier(0.5f, sFarther.getY()-20, sFarther.getY(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchFather = true;
						}
					})));
		}
	}
	private void touchMeter(){
		if (touchMeterFace) {
			touchMeterFace = false;
			final int index = aniMeterFace.getCurrentTileIndex();
			aniMeterFace.setCurrentTileIndex(1);
			A5_5_META_MACH.play();
			mEngine.unregisterUpdateHandler(timerWindow);
			timerWindow = new TimerHandler(1.0f,false, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					// TODO Auto-generated method stub
//					mEngine.unregisterUpdateHandler(arg0);
					aniMeterFace.setCurrentTileIndex(index);
					touchMeterFace = true;
				}
			});
			mEngine.registerUpdateHandler(timerWindow);
		}
		
	}
	private void touchSolarnel3(){
		if (touchWindow){
			touchWindow = false;	
			setvisiableSprite(true, sWindow);
			A5_12_MADO.play();
			TimerHandler timerWindow = new TimerHandler(1.0f,false, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					setvisiableSprite(false, sWindow);
					touchWindow = true;
				}
			});
			mEngine.registerUpdateHandler(timerWindow);
		}
	}
	private void touchTree(){
		if (touchTree) {
			touchTree = false;
			long pDuration[]= {1000,400};
			int pFrame[]={1,0};
			A5_15_TREE.play();
			aniTree.animate(pDuration, pFrame, 0, this);
		}
	}
	private void touchTshirt() {
		if (touchTshirt) {
			touchTshirt = false;
			long pDuration[]= {200,200};
			int pFrame[]={1,0};
			A5_15_TS.play();
			aniTshirt.animate(pDuration, pFrame, 2, this);
		}
		
	}
	private void moveKaze(){
		arrSKaze[0].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(1.7f,arrSKaze[0].getX(), 483-arrSKaze[0].getWidth()),
				new MoveXModifier(5.5f, 980, arrSKaze[0].getX()))));
		arrSKaze[1].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(2.6f,arrSKaze[1].getX(), 483-arrSKaze[1].getWidth()),
				new MoveXModifier(5.7f, 980, arrSKaze[1].getX()))));
		arrSKaze[2].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(5.1f,arrSKaze[2].getX(), 483-arrSKaze[2].getWidth()),
				new MoveXModifier(2.1f, 980, arrSKaze[2].getX()))));
		arrSKaze[3].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(7.0f,arrSKaze[3].getX(), 483-arrSKaze[3].getWidth()),
				new MoveXModifier(1.2f, 980, arrSKaze[3].getX()))));
		
	}

	private void setvisiableSprite(boolean value, Sprite ...sprites){
		if (sprites != null) {
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].setPosition(sprites[i].getmXFirst(), sprites[i].getmYFirst());
				sprites[i].setVisible(value);
				
			}
		}
	}
	private void setvisiableAniSprite(boolean value, AnimatedSprite ...aniSprites){
		if (aniSprites != null) {
			for (int i = 0; i < aniSprites.length; i++) {
				aniSprites[i].setVisible(value);
				aniSprites[i].setPosition(aniSprites[i].getmXFirst(), aniSprites[i].getmYFirst());
				aniSprites[i].setCurrentTileIndex(0);
				
			}
		}
	}
	@Override
	public void combineGimmic3WithAction() {
	}
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		initial();
		moveKaze();
	}
	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		setvisiableSprite(false, sFarther,sGrandma,sMama, sSenpuki2, sSenpuki3, sWashmachine3, sWashmachine4,
				sWashmachine5, sSun2, sSolarnel2, sMeterStar, sSolarnel1);
		setvisiableAniSprite(false, aniSenpuki1,aniWashmachine1, aniWashmachine2,aniRefrigerator1,aniRefrigerator2,
				aniTv1, aniTv2, aniRenji1, aniRenji2, aniLight1, aniLight2, aniMeterFace, aniSunFace, aniBoy);
		for (int i = 0; i < arrsMeterMemory.length; i++) {
			arrsMeterMemory[i].setVisible(false);
		}
		if (aniDenki4!=null){
			aniDenki4.clearEntityModifiers();
			setvisiableAniSprite(false, aniDenki4);
			aniDenki4.stopAnimation();
			aniDenki4.setCurrentTileIndex(0);
			aniLight2.stopAnimation();
			aniLight2.setVisible(false);
			A5_TUDEN_ALL.stop();
		}
		resetGame();
		super.onPauseGame();
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		// TODO Auto-generated method stub
		if (arg0.equals(aniTshirt)){
			touchTshirt=true;
		}
		if (arg0.equals(aniTree)){
			touchTree=true;
		}
		if (arg0.equals(aniRefrigerator1)) {
			touchRegtorOff=true;
		}
		if (arg0.equals(aniRefrigerator2)) {
			touchRefrigeratorOn= true;
		}
		if (arg0.equals(aniTv1)){
			touchTvOff=true;
		}
		if (arg0.equals(aniTv2)) {
			touchTvOn = true;
		}
		if (arg0.equals(aniRenji1)) {
			touchRenjiOff=true;
		}
		if (arg0.equals(aniLight1)) {
			touchLightOff=true;
		}
		if (arg0.equals(aniSenpuki1)) {
			touchSenpukiOff = true;
		}
		if (arg0.equals(aniWashmachine1)){
			touchWaschmachineOff = true;
		}
		if (arg0.equals(aniRenji2)) {
			touchRenjiOn = true;
			
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
	
	@Override
	protected void loadKaraokeSound() {
		A5_10_ATSUIWA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_ATSUIWA);
		A5_10_HAI3 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_HAI3);
		A5_10_HAI4 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_HAI4);
		A5_10_MITANA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_MITANA);
		A5_10_OTOSEI = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_OTOSEI);
		A5_10_OYOBIDESUKA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_OYOBIDESUKA);
		A5_10_REIZOUKO = loadSoundResourceFromSD(Vol3BasutazuResource.A5_10_REIZOUKO);
		A5_12_MADO = loadSoundResourceFromSD(Vol3BasutazuResource.A5_12_MADO);
		A5_12_SORA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_12_SORA);
		A5_13_TV = loadSoundResourceFromSD(Vol3BasutazuResource.A5_13_TV);
		A5_15_FUSYA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_15_FUSYA);
		A5_15_SUN = loadSoundResourceFromSD(Vol3BasutazuResource.A5_15_SUN);
		A5_15_TREE = loadSoundResourceFromSD(Vol3BasutazuResource.A5_15_TREE);
		A5_15_TS = loadSoundResourceFromSD(Vol3BasutazuResource.A5_15_TS);
		A5_21_AHAHA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_21_AHAHA);
		A5_21_TSUITAWA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_21_TSUITAWA);
		A5_21_UFUFU = loadSoundResourceFromSD(Vol3BasutazuResource.A5_21_UFUFU);
		A5_2_RENJI = loadSoundResourceFromSD(Vol3BasutazuResource.A5_2_RENJI);
		A5_3_DENKIKITA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_3_DENKIKITA);
		A5_3_YA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_3_YA);
		A5_4_SENPUKI = loadSoundResourceFromSD(Vol3BasutazuResource.A5_4_SENPUKI);
		A5_5_META1 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META1);
		A5_5_META10 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META10);
		A5_5_META2 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META2);
		A5_5_META3 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META3);
		A5_5_META4 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META4);
		A5_5_META5 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META5);
		A5_5_META6 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META6);
		A5_5_META7 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META7);
		A5_5_META8 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META8);
		A5_5_META9 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META9);
		A5_5_META_MACH = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_META_MACH);
		A5_5_TUDEN_TAN = loadSoundResourceFromSD(Vol3BasutazuResource.A5_5_TUDEN_TAN);
		A5_6_JITENSYA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_6_JITENSYA);
		A5_6_JITENSYA2 = loadSoundResourceFromSD(Vol3BasutazuResource.A5_6_JITENSYA2);
		A5_6_JITENSYA_SOKU = loadSoundResourceFromSD(Vol3BasutazuResource.A5_6_JITENSYA_SOKU);
		A5_8_SENTAKUKI = loadSoundResourceFromSD(Vol3BasutazuResource.A5_8_SENTAKUKI);
		A5_9_ARA = loadSoundResourceFromSD(Vol3BasutazuResource.A5_9_ARA);
		A5_9_OW = loadSoundResourceFromSD(Vol3BasutazuResource.A5_9_OW);
		A5_9_UGOITANE = loadSoundResourceFromSD(Vol3BasutazuResource.A5_9_UGOITANE);
		A5_KACHI = loadSoundResourceFromSD(Vol3BasutazuResource.A5_KACHI);
		A5_KADEN_TUDEN = loadSoundResourceFromSD(Vol3BasutazuResource.A5_KADEN_TUDEN);
		A5_TUDEN_ALL = loadSoundResourceFromSD(Vol3BasutazuResource.A5_TUDEN_ALL);
		
		
	}

}
