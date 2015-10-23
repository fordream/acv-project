/* Vol3Anpanmanmachi.java
* Create on Jun 4, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AnpanmanmachiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

public class Vol3Anpanmanmachi extends BaseGameFragment implements IOnSceneTouchListener,
 IAnimationListener, IEntityModifierListener {
	
	public static final String TAG = " Vol3Anpanmanmachi ";
	private TexturePack ttpMachi1, ttpMachi2, ttpMachi3, ttpMachi4;
	private TexturePackTextureRegionLibrary ttpLibMachi1, ttpLibMachi2, ttpLibMachi3, ttpLibMachi4;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion trBackground, trEarth, trShip, trBgrRotat, trSmoke, trCowboy, trHat,
			trMegami1,trMegami2, trSakura1, trSakura2, trTora, trSuperman, trTower, trTsutan1, trTsutan3,
			trTsutan4, trSfinks, trMasai, trFishSmall, trManbou, trFutari2, trFutari3, trChina2,
			trFutari4, trFusya1, trFusya2, trHouse, trSticks, trFireWork1, trFireWork2, trFireWork3;
	private Sprite sBackground, sEarth, sShip, sBgrRotat, sSmoke, sCowboy, sHat, sMegami1, sMegami2,
			sSakura1, sSakura2, sTora, sSuperman, sTower, sTsutan1, sTsutan3, sTsutan4, sSfinks, sMasai,
			sFishSmall,  sManbou, sFutari2, sFutari3, sFutari4, sFusya1, sFusya2, sChina2,
			sHouse, sSticks, sFireWork1, sFireWork2, sFireWork3;
	
	private TextureRegion trarrManbou[] = new TextureRegion[6];
	
	private Sprite sarrMabou[] = new Sprite[6];
	
	private TiledTextureRegion ttrMaiko, ttrSanba, ttrIndian, ttrChina1, ttrRakura, ttrTako, ttrNinja, 
			ttrFuraGirl, ttrSamurai, ttrPanda, ttrShiro, ttrTsutan2, ttrKame, ttrOranda, ttrFutari1,
			ttrDragon, ttrPiramid, ttrTaban, ttrBird, ttrRion, ttrKirin, ttrZou,ttrFishRed, ttrFish;
	private AnimatedSprite aniMaiko, aniSanba, aniIndian, aniChina1, aniRakura, aniTako, aniNinja, aniNinjaSmoke,
			aniFuraGirl, aniSamurai, aniPanda, aniShiro, aniTsutan2, aniKame, aniOranda, aniFutari1,
			aniDragon, aniPiramid, aniTaban, aniBird, aniRion, aniKirin, aniZou, sFishRed, sFish;
	
	
	private Sound A3_10_GUBAY2, A3_10_MEGAMI, A3_11_HAWAI, A3_12_KIRA6, A3_13_TORA, A3_14_NINJA, A3_15_SUPAMAN,
			A3_16_TOWER, A3_17_SAMURAI, A3_18_PANDA3, A3_19_SHIRO, A3_1_MAIKO, A3_1_OIDEYASU2, A3_20_TSUTAN2, 
			A3_20_WA, A3_21_SFINKS2, A3_22_RION, A3_23_AFRIKA2, A3_24_KIRIN, A3_25_ZOU, A3_26_SAKANA, A3_27_MANBOU,
			A3_29_ORANDA, A3_2_SANBA, A3_30_OUJITOHIME, A3_31_DRAGON, A3_32_PIRAMID, A3_33_GIGIGI, A3_34_BASA,
			A3_34_HYUDON, A3_35_ARABU,A3_3_AWA2, A3_4_NIHAO, A3_4_SUPON, A3_5_RAKUDA, A3_6_BO1, A3_9_BOUSHITOBU,
			A3_9_OMYGOD, A3_KAMETAKO, A3_YUBIBUE_3;
	
	private SequenceEntityModifier toraModifire, supermanModifire, sfinksModifire, masaiModifire, 
			 fishSmallModifire;
//	private RotationModifier ;
	private MoveModifier chinaModifire;
	
	Timer timeFire = new Timer();
	Timer timeKirin = new Timer();
	private float arrPointManbou[][] = {
			{922,954,926,949,938,962}, {826,808,804,815,812,820}
	};
	private boolean istouchMako, istouchSanba, istouchIndian, istouchChina, istouchShip, istouchCowboy, 
			istouchMegami, istouchFuraGirl, istouchTora, istouchNinja, istouchTower, istouchShiro, istouchTaban,
			istouchPanda, istouchSamurai, istouchSuperman, istouchTsutan, istouchSfinks, istouchPiramid,
			istouchMasai, istouchZou, istouchRion, istouchFusya, istouchOranda, istouchBoy, istouchDragon,
			istouchHouse, istouchKame, istouchTako, istouchFishRed1, istouchFishSmall, istouchManbou, istouchFish,
			istouchSakura, istouchFishRed2, istouchFishRedFirst, istouchFishRedBack, touchFishSmallFirst,
			touchFishSmallBack,touchFishFirst,touchFishBack, istouchKirin;
	//TODO
	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("anpanmanmachi/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("anpanmanmachi/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "anpanmanmachi/gfx/");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		
		Log.d(TAG, "loadKaraokeResources: ");
		ttpMachi1 = mTexturePackLoaderFromSource.load("anpanmanmachi1.xml");
		ttpMachi1.loadTexture();
		ttpLibMachi1 = ttpMachi1.getTexturePackTextureRegionLibrary();
		
		ttpMachi2 = mTexturePackLoaderFromSource.load("anpanmanmachi2.xml");
		ttpMachi2.loadTexture();
		ttpLibMachi2 = ttpMachi2.getTexturePackTextureRegionLibrary();
		
		ttpMachi3 = mTexturePackLoaderFromSource.load("anpanmanmachi3.xml");
		ttpMachi3.loadTexture();
		ttpLibMachi3 = ttpMachi3.getTexturePackTextureRegionLibrary();
		
		ttpMachi4 = mTexturePackLoaderFromSource.load("anpanmanmachi4.xml");
		ttpMachi4.loadTexture();
		ttpLibMachi4 = ttpMachi4.getTexturePackTextureRegionLibrary();
		
		trBackground = ttpLibMachi3.get(Vol3AnpanmanmachiResource.A3_37_IPHONE_HAIKEI_ID);
		trBgrRotat = ttpLibMachi4.get(Vol3AnpanmanmachiResource.BACKGROUND_ID);
		trEarth = ttpLibMachi3.get(Vol3AnpanmanmachiResource.A3_36_IPHONE_EARTH_ID);
		ttrMaiko = getTiledTextureFromPacker(ttpMachi1,
				Vol3AnpanmanmachiResource.A3_1_1_IPHONE_MAIKO_ID,
				Vol3AnpanmanmachiResource.A3_1_2_IPHONE_MAIKO_ID);
		ttrSanba = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_2_1_IPHONE_SANBA_ID,
				Vol3AnpanmanmachiResource.A3_2_2_IPHONE_SANBA_ID,
				Vol3AnpanmanmachiResource.A3_2_3_IPHONE_SANBA_ID,
				Vol3AnpanmanmachiResource.A3_2_4_IPHONE_SANBA_ID,
				Vol3AnpanmanmachiResource.A3_2_5_IPHONE_SANBA_ID);
		
		ttrIndian = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_3_1_IPHONE_INDIAN_ID,
				Vol3AnpanmanmachiResource.A3_3_2_IPHONE_INDIAN_ID,
				Vol3AnpanmanmachiResource.A3_3_3_IPHONE_INDIAN_ID);
		
		ttrChina1 = getTiledTextureFromPacker(ttpMachi1,
				Vol3AnpanmanmachiResource.A3_4_1_IPHONE_CHINA_ID,
				Vol3AnpanmanmachiResource.A3_4_2_IPHONE_CHINA_ID);
		
		ttrRakura = getTiledTextureFromPacker(ttpMachi1,
				Vol3AnpanmanmachiResource.A3_5_1_IPHONE_RAKUDA_ID,
				Vol3AnpanmanmachiResource.A3_5_2_IPHONE_RAKUDA_ID,
				Vol3AnpanmanmachiResource.A3_5_3_IPHONE_RAKUDA_ID,
				Vol3AnpanmanmachiResource.A3_5_4_IPHONE_RAKUDA_ID);
		
		ttrTako = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_7_1_IPHONE_TAKO_ID,
				Vol3AnpanmanmachiResource.A3_7_2_IPHONE_TAKO_ID);
		
		ttrFuraGirl = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_11_1_IPHONE_FURAGIRL_ID,
				Vol3AnpanmanmachiResource.A3_11_2_IPHONE_FURAGIRL_ID,
				Vol3AnpanmanmachiResource.A3_11_3_IPHONE_FURAGIRL_ID,
				Vol3AnpanmanmachiResource.A3_11_4_IPHONE_FURAGIRL_ID,
				Vol3AnpanmanmachiResource.A3_11_5_IPHONE_FURAGIRL_ID);
		
		ttrSamurai = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_17_1_IPHONE_SAMURAI_ID,
				Vol3AnpanmanmachiResource.A3_17_2_IPHONE_SAMURAI_ID,
				Vol3AnpanmanmachiResource.A3_17_3_IPHONE_SAMURAI_ID,
				Vol3AnpanmanmachiResource.A3_17_4_IPHONE_SAMURAI_ID,
				Vol3AnpanmanmachiResource.A3_17_5_IPHONE_SAMURAI_ID,
				Vol3AnpanmanmachiResource.A3_17_6_IPHONE_SAMURAI_ID);
		
		ttrPanda = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_18_1_IPHONE_PANDA_ID,
				Vol3AnpanmanmachiResource.A3_18_2_IPHONE_PANDA_ID,
				Vol3AnpanmanmachiResource.A3_18_3_IPHONE_PANDA_ID);
		
		ttrShiro = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_19_1_IPHONE_SHIRO_ID,
				Vol3AnpanmanmachiResource.A3_19_2_IPHONE_SHIRO_ID);
		ttrTsutan2 = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_20_2_IPHONE_TSUTAN_ID,
				Vol3AnpanmanmachiResource.A3_20_3_IPHONE_TSUTAN_ID);
		ttrNinja = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_14_1_IPHONE_NINJA_ID,
				Vol3AnpanmanmachiResource.A3_14_2_IPHONE_NINJA_ID,
				Vol3AnpanmanmachiResource.A3_14_3_IPHONE_NINJA_ID,
				Vol3AnpanmanmachiResource.A3_14_4_IPHONE_NINJA_ID,
				Vol3AnpanmanmachiResource.A3_14_5_IPHONE_NINJA_ID);
		
		
		ttrKame = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_28_1_IPHONE_KAME_ID,
				Vol3AnpanmanmachiResource.A3_28_2_IPHONE_KAME_ID);
		ttrOranda = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_29_1_IPHONE_ORANDA_ID,
				Vol3AnpanmanmachiResource.A3_29_2_IPHONE_ORANDA_ID);
		ttrFutari1 = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_30_1_IPHONE_FUTARI_ID,
				Vol3AnpanmanmachiResource.A3_30_2_IPHONE_FUTARI_ID,
				Vol3AnpanmanmachiResource.A3_30_4_IPHONE_FUTARI_ID);
		
		ttrDragon = getTiledTextureFromPacker(ttpMachi2, 
				Vol3AnpanmanmachiResource.A3_31_1_IPHONE_DRAGON_ID,
				Vol3AnpanmanmachiResource.A3_31_2_IPHONE_DRAGON_ID,
				Vol3AnpanmanmachiResource.A3_31_3_IPHONE_DRAGON_ID,
				Vol3AnpanmanmachiResource.A3_31_4_IPHONE_DRAGON_ID,
				Vol3AnpanmanmachiResource.A3_31_5_IPHONE_DRAGON_ID,
				Vol3AnpanmanmachiResource.A3_31_6_IPHONE_DRAGON_ID);
		
		ttrPiramid = getTiledTextureFromPacker(ttpMachi2, 
				Vol3AnpanmanmachiResource.A3_32_1_IPHONE_PIRAMID_ID,
				Vol3AnpanmanmachiResource.A3_32_2_IPHONE_PIRAMID_ID);
			
		ttrTaban = getTiledTextureFromPacker(ttpMachi2, 
				Vol3AnpanmanmachiResource.A3_35_1_IPHONE_TABAN_ID,
				Vol3AnpanmanmachiResource.A3_35_2_IPHONE_TABAN_ID,
				Vol3AnpanmanmachiResource.A3_35_3_IPHONE_TABAN_ID);
		
		ttrBird = getTiledTextureFromPacker(ttpMachi2, 
				Vol3AnpanmanmachiResource.A3_34_2_IPHONE_SHIRO_ID,
				Vol3AnpanmanmachiResource.A3_34_3_IPHONE_SHIRO_ID);
		
		ttrRion  = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_22_1_IPHONE_RION_ID,
				Vol3AnpanmanmachiResource.A3_22_2_IPHONE_RION_ID,
				Vol3AnpanmanmachiResource.A3_22_3_IPHONE_RION_ID,
				Vol3AnpanmanmachiResource.A3_22_4_IPHONE_RION_ID,
				Vol3AnpanmanmachiResource.A3_22_5_IPHONE_RION_ID,
				Vol3AnpanmanmachiResource.A3_22_6_IPHONE_RION_ID);
		ttrKirin = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_24_1_IPHONE_KIRIN_ID,
				Vol3AnpanmanmachiResource.A3_24_2_IPHONE_KIRIN_ID,
				Vol3AnpanmanmachiResource.A3_24_3_IPHONE_KIRIN_ID);
		ttrZou = getTiledTextureFromPacker(ttpMachi1, 
				Vol3AnpanmanmachiResource.A3_25_1_IPHONE_ZOU_ID,
				Vol3AnpanmanmachiResource.A3_25_2_IPHONE_ZOU_ID,
				Vol3AnpanmanmachiResource.A3_25_3_IPHONE_ZOU_ID);
		
		//-----------------------------------------------
		trChina2 = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_4_3_IPHONE_CHINA_ID);
		trShip = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_6_1_IPHONE_SHIP_ID);
		trSmoke = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_6_2_IPHONE_SHIP_ID);
		
		ttrFish = getTiledTextureFromPacker(ttpMachi1,
				Vol3AnpanmanmachiResource.A3_8_IPHONE_FISH_ID,
				Vol3AnpanmanmachiResource.A3_8_IPHONE_FISH_BACK_ID);
		trCowboy =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_9_1_IPHONE_COWBOY_ID);
		trHat =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_9_2_IPHONE_COWBOY_ID);
		trMegami1 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_10_1_IPHONE_MEGAMI_ID);
		trMegami2 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_10_2_IPHONE_MEGAMI_ID);
		
		trSakura1 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_12_1_IPHONE_SAKURA_ID);
		trSakura2 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_12_2_IPHONE_SAKURA_ID);
		trTora =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_13_IPHONE_TORA_ID);
		trSuperman =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_15_IPHONE_SUPERMAN_ID);
		trTower =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_16_IPHONE_TOWER_ID);
		trTsutan1 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_20_1_IPHONE_TSUTAN_ID);
		trTsutan3 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_20_4_IPHONE_TSUTAN_ID);
		trTsutan4 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_20_5_IPHONE_TSUTAN_ID);
		
		trSfinks =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_21_IPHONE_SFINKS_ID);
		trMasai =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_23_IPHONE_MASAI_ID);
		trFishSmall =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_26_1_IPHONE_FISH_ID);
		ttrFishRed =  getTiledTextureFromPacker(ttpMachi1,
				Vol3AnpanmanmachiResource.A3_26_2_IPHONE_FISH_ID);
		trManbou =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_1_IPHONE_MANBOU_ID);
		trarrManbou[0] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_2_IPHONE_MANBOU_ID);
		trarrManbou[1] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_3_IPHONE_MANBOU_ID);
		trarrManbou[2] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_4_IPHONE_MANBOU_ID);
		trarrManbou[3] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_5_IPHONE_MANBOU_ID);
		trarrManbou[4] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_6_IPHONE_MANBOU_ID);
		trarrManbou[5] = ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_27_7_IPHONE_MANBOU_ID);
		
		trFutari2 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_30_3_IPHONE_FUTARI_ID);
		trFutari3 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_30_5_IPHONE_FUTARI_ID);
		trFutari4 =  ttpLibMachi1.get(Vol3AnpanmanmachiResource.A3_30_6_IPHONE_FUTARI_ID);
		
		trFusya1 =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_33_1_IPHONE_FUSYA_ID);
		trFusya2 =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_33_2_IPHONE_FUSYA_ID);
		trHouse =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_34_1_IPHONE_SHIRO_ID);
		trSticks =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_34_4_IPHONE_SHIRO_ID);
		trFireWork1 =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_34_5_IPHONE_SHIRO_ID);
		trFireWork2 =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_34_6_IPHONE_SHIRO_ID);
		trFireWork3 =  ttpLibMachi2.get(Vol3AnpanmanmachiResource.A3_34_7_IPHONE_SHIRO_ID);
		
	}

	@Override
	protected void loadKaraokeSound() {
		
		Log.d(TAG, "loadKaraokeSound: ");
		A3_YUBIBUE_3 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_YUBIBUE_3);
		A3_10_GUBAY2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_10_GUBAY2);
		A3_10_MEGAMI = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_10_MEGAMI);
		A3_11_HAWAI = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_11_HAWAI);
		A3_12_KIRA6 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_12_KIRA6);
		A3_13_TORA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_13_TORA);
		A3_14_NINJA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_14_NINJA);
		A3_15_SUPAMAN = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_15_SUPAMAN);
		A3_16_TOWER = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_16_TOWER);
		A3_17_SAMURAI = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_17_SAMURAI);
		A3_18_PANDA3 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_18_PANDA3);
		A3_19_SHIRO = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_19_SHIRO);
		
		A3_1_MAIKO = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_1_MAIKO);
		A3_1_OIDEYASU2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_1_OIDEYASU2);
		A3_20_TSUTAN2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_20_TSUTAN2);
		A3_20_WA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_20_WA);
		A3_21_SFINKS2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_21_SFINKS2);
		A3_22_RION = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_22_RION);
		A3_23_AFRIKA2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_23_AFRIKA2);
		A3_24_KIRIN = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_24_KIRIN);
		A3_25_ZOU = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_25_ZOU);
		A3_26_SAKANA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_26_SAKANA);
		A3_27_MANBOU = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_27_MANBOU);
		A3_29_ORANDA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_29_ORANDA);
		A3_2_SANBA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_2_SANBA);
		A3_30_OUJITOHIME = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_30_OUJITOHIME);
		A3_31_DRAGON = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_31_DRAGON);
		A3_32_PIRAMID = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_32_PIRAMID);
		A3_33_GIGIGI = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_33_GIGIGI);
		A3_34_BASA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_34_BASA);
		A3_34_HYUDON = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_34_HYUDON);
		A3_35_ARABU = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_35_ARABU);
		A3_3_AWA2 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_3_AWA2);
		A3_4_NIHAO = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_4_NIHAO);
		A3_4_SUPON = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_4_SUPON);
		A3_5_RAKUDA = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_5_RAKUDA);
		A3_6_BO1 = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_6_BO1);
		A3_9_BOUSHITOBU = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_9_BOUSHITOBU);
		A3_9_OMYGOD = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_9_OMYGOD);
		A3_KAMETAKO = loadSoundResourceFromSD(Vol3AnpanmanmachiResource.A3_KAMETAKO);
		
		
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();
		sBackground = new Sprite(0, 0, trBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground);
		sBgrRotat = new Sprite(-300, -152, trBgrRotat, this.getVertexBufferObjectManager());
		mScene.attachChild(sBgrRotat);
		
		sEarth = new Sprite(337, 352, trEarth, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sEarth);
		
		sSuperman = new Sprite(1114, 521, trSuperman, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSuperman);
		
		aniIndian = new AnimatedSprite(1027, 392, ttrIndian, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniIndian);
		
		aniChina1 = new AnimatedSprite(447, 377, ttrChina1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniChina1);
		sChina2 = new Sprite(447, 377, trChina2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sChina2);
		aniTako = new AnimatedSprite(712, 616, ttrTako, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniTako);
		
		sFish = new AnimatedSprite(934, 510, ttrFish, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFish);
		
		sCowboy = new Sprite(1168, 662, trCowboy, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sCowboy);
		
		sHat = new Sprite(1168, 662, trHat, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sHat);
		
		
		sMegami2 = new Sprite(1212, 734, trMegami2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sMegami2);
		
		sMegami1 = new Sprite(1212, 734, trMegami1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sMegami1);
		//------------------------------------
		aniSamurai = new AnimatedSprite(761, 233, ttrSamurai, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniSamurai);
		
		sTower = new Sprite(772, 304, trTower, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sTower);
		
		sSakura1 = new Sprite(788, 458, trSakura1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSakura1);
		
		sSakura2 = new Sprite(788, 458, trSakura2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSakura2);
		sSakura2.setVisible(false);
		aniFuraGirl = new AnimatedSprite(870, 404, ttrFuraGirl, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniFuraGirl);
		
		sShip = new Sprite(754, 511, trShip, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sShip);
		
		sSmoke = new Sprite(754, 511, trSmoke, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSmoke);
		sSmoke.setVisible(false);
		
		aniMaiko = new AnimatedSprite(700, 384, ttrMaiko, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniMaiko);
		
		sTora = new Sprite(366, 471, trTora, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sTora);

		aniNinja = new AnimatedSprite(590, 430, ttrNinja, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniNinja);
		
		aniNinjaSmoke = new AnimatedSprite(590, 430, ttrNinja, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniNinjaSmoke);
		aniNinjaSmoke.setVisible(false);
		
		aniSanba = new AnimatedSprite(1027, 517, ttrSanba, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniSanba);
		
		//-------------------------
		sSticks = new Sprite(1212,1128, trSticks, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSticks);
		sSticks.setVisible(false);
		sFireWork1 = new Sprite(1232,1151, trFireWork1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFireWork1);
		sFireWork1.setVisible(false);
		
		sFireWork2 = new Sprite(1232,1151, trFireWork2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFireWork2);
		sFireWork2.setVisible(false);
		
		sFireWork3 = new Sprite(1232,1151, trFireWork3, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFireWork3);
		sFireWork3.setVisible(false);
		
		aniBird = new AnimatedSprite(1239, 1138, ttrBird, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniBird);
		aniBird.setVisible(false);
		sHouse = new Sprite(1057,1019, trHouse, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sHouse);
		//---------------------------------
		aniPiramid = new AnimatedSprite(370, 1004, ttrPiramid, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniPiramid);
		
		sSfinks = new Sprite(528, 954, trSfinks, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sSfinks);
		
		aniPanda = new AnimatedSprite(529, 327, ttrPanda, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniPanda);
		
		aniShiro = new AnimatedSprite(248, 723, ttrShiro, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniShiro);
		
		aniRakura = new AnimatedSprite(335, 647, ttrRakura, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniRakura);
		
		sTsutan1 = new Sprite(327, 903, trTsutan1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sTsutan1);
		
		aniTsutan2 = new AnimatedSprite(327, 903, ttrTsutan2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniTsutan2);
		
		sTsutan3 = new Sprite(327, 903, trTsutan3, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sTsutan3);
		
		sTsutan4 = new Sprite(327, 903, trTsutan4, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sTsutan4);
		
		sFusya1 = new Sprite(702,1219, trFusya1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFusya1);
		
		sFusya2 = new Sprite(702,1219, trFusya2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFusya2);
		
		//---------------------------------------
		aniKirin = new AnimatedSprite(600, 1090, ttrKirin, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniKirin);
		sMasai = new Sprite(524, 1041, trMasai, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sMasai);
		
		aniRion = new AnimatedSprite(622, 1006, ttrRion, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniRion);
		
		aniZou = new AnimatedSprite(610, 878, ttrZou, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniZou);
		
		sFishSmall = new Sprite(712, 796, trFishSmall, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFishSmall);
		
		sFishRed = new AnimatedSprite(800, 960, ttrFishRed, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFishRed);
		//TODO
		sarrMabou[0] = new Sprite(arrPointManbou[0][0], arrPointManbou[1][0], 
				trarrManbou[0], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[0]);
		sarrMabou[0].setVisible(false);
		
		sarrMabou[1] = new Sprite(arrPointManbou[0][1], arrPointManbou[1][1],
				trarrManbou[1], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[1]);
		sarrMabou[1].setVisible(false);
		sarrMabou[2] = new Sprite(arrPointManbou[0][2], arrPointManbou[1][2], 
				trarrManbou[2], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[2]);
		sarrMabou[2].setVisible(false);
		sarrMabou[3] = new Sprite(arrPointManbou[0][3], arrPointManbou[1][3],
				trarrManbou[3], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[3]);
		sarrMabou[3].setVisible(false);
		sarrMabou[4] = new Sprite(arrPointManbou[0][4], arrPointManbou[1][4],
				trarrManbou[4], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[4]);
		sarrMabou[4].setVisible(false);
		sarrMabou[5] = new Sprite(arrPointManbou[0][5], arrPointManbou[1][5],
				trarrManbou[5], this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sarrMabou[5]);
		sarrMabou[5].setVisible(false);
		sManbou = new Sprite(840, 818, trManbou, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sManbou);
		
		aniKame = new AnimatedSprite(940, 920, ttrKame, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniKame);
		
		aniOranda = new AnimatedSprite(826, 1147, ttrOranda, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniOranda);
		
		aniFutari1 = new AnimatedSprite(1064, 925, ttrFutari1, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniFutari1);
		
		sFutari2 = new Sprite(1064, 925, trFutari2, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFutari2);
		sFutari2.setVisible(false);
		
		//----------------
		aniDragon = new AnimatedSprite(910, 1020, ttrDragon, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniDragon);
		sFutari3 = new Sprite(1092,1077, trFutari3, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFutari3);
		sFutari3.setVisible(false);
		sFutari4 = new Sprite(1253,1002, trFutari4, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(sFutari4);
		sFutari4.setVisible(false);
		
		aniTaban = new AnimatedSprite(349, 775, ttrTaban, this.getVertexBufferObjectManager());
		sBgrRotat.attachChild(aniTaban);
		
		
		addGimmicsButton(mScene, Vol3AnpanmanmachiResource.SOUND_GIMMIC,
				Vol3AnpanmanmachiResource.IMAGE_GIMMIC, ttpLibMachi2);
		mScene.setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		// TODO Auto-generated method stub
		int pX =  (int) pTouchEvent.getX();
		int pY =  (int) pTouchEvent.getY();
		if (pTouchEvent.isActionDown()) {
			if (sTora.contains(pX, pY)) {
				touchTora();
			} else 
			if (aniPanda.contains(pX, pY)) {
				touchPanda();
			} else
			if (aniChina1.contains(pX, pY)) {
				touchChina();
			} else
			if (aniNinja.contains(pX +30, pY+10)) {
				touchNinja();
			} else
			
			if(sSakura1.contains(pX, pY)){
				touchSakura();
			} else if (aniMaiko.contains(pX+30, pY-10)) {
				touchMaiko();
			} else if (sTower.contains(pX, pY)) {
				touchTower();
			} else if (aniSamurai.contains(pX, pY)) {
				touchSamurai();
			} 
			
			//--------------------------------
			if (aniSanba.contains(pX, pY)) {
				touchSanba();
				
			} else if (sSuperman.contains(pX, pY)) {
				touchSuperman();
			}
			if (aniIndian.contains(pX, pY)) {
				touchIndian();
			}
			
			if (aniTaban.contains(pX, pY)) {
				touchTaban();
				
			} else
			if (aniRakura.contains(pX, pY)) {
				A3_5_RAKUDA.play();
				nextTileAnimatedSprite(aniRakura);
			} else if (aniShiro.contains(pX, pY)) {
				touchShiro();
			} else
			if (aniZou.contains(pX, pY)) {
				touchZou();
			} else 
			if (aniRion.contains(pX, pY)) {
				touchRion();
			} else
			if (sMasai.contains(pX, pY)) {
				touchMasai();
			} else
			if (aniKirin.contains(pX, pY)) {
				touchKirin();
			} else if (sTsutan1.contains(pX, pY)||aniTsutan2.contains(pX, pY)) {
				touchTsutan();
			} else 
			if (sSfinks.contains(pX, pY)) {
				touchSfinks();
			} else 
			if (aniPiramid.contains(pX, pY)) {
				touchPiramid();
			}
			if (sShip.contains(pX+30, pY-10)) {
				touchShip();
			}
			if (sCowboy.contains(pX, pY)) {
				touchCowboy();
			}
			if (sMegami2.contains(pX, pY)) {
				touchMegami();
			}
			if (aniFuraGirl.contains(pX, pY)) {
				touchFuraGirl();
			} else 
			if (sFish.contains(pX, pY)) {
				if (touchFishFirst){
					touchFish();
				}else if (touchFishBack) {
					touchFishBack();
				}
			}
			
			if (sFusya1.contains(pX, pY)||sFusya2.contains(pX, pY)){
				touchFusya();
			}
			if (aniOranda.contains(pX, pY)) {
				touchOranda();
			}
			if (aniFutari1.contains(pX-10, pY-10)) {
				boyKissGirl();
			} else
			if (aniDragon.contains(pX, pY)) {
				Log.d(TAG, "gia tri PX: " + pX);
				Log.d(TAG, "gia tri PY: " + pY);
				touchDragon();
			 
			} else if (sHouse.contains(pX, pY)) {
				touchHouse();
			}
			if (aniKame.contains(pX, pY)) {
				touchKame();
			}
			if (aniTako.contains(pX, pY)) {
				touchTako();
			}
			if (sFishRed.contains(pX, pY)) {
				if (istouchFishRedFirst) {
//					istouchFishRed1 = false;
					touchFishRed1();
				}else
				if (istouchFishRedBack) {
//					istouchFishRed2 = false;
					touchFishRed2();
				}
			}
			if (sFishSmall.contains(pX, pY)) {
				if (touchFishSmallFirst) {
					touchFishSmall1();
				}else 
				if (touchFishSmallBack){
					touchFishSmall2();
				}	
			}
			if (sManbou.contains(pX, pY)) {
				touchManbou();
				
			}
			
		}
		//TODO
		return false;
	}
	private void touchFishBack() {
		if (istouchFish) {
			istouchFish = false;
			sFish.clearEntityModifiers();
			A3_26_SAKANA.play();
			
			
			sFish.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(
					new MoveModifier(0.8f, sFish.getX(), sFish.getX()+40, sFish.getY(), sFish.getY()-40), 
					new RotationModifier(0.3f, 0, -30)),
					new ParallelEntityModifier(new RotationModifier(0.3f, -30, 20),
					new MoveModifier(0.8f, sFish.getX()+40, sFish.getX()+10, sFish.getY()-40, 
							sFish.getY()+10, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sFish.setRotation(0);
							istouchFish=true;
							moveFishBack();
						}
					}))));
		}
		
		
	}
	private void touchFish(){
		if (istouchFish) {
			istouchFish = false;
			sFish.clearEntityModifiers();
			A3_26_SAKANA.play();
			
			
			sFish.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(
					new MoveModifier(0.8f, sFish.getX(), sFish.getX()+40, sFish.getY(), sFish.getY()-40), 
					new RotationModifier(0.3f, 0, 30)),
					new ParallelEntityModifier(new RotationModifier(0.3f, 30, -20),
					new MoveModifier(0.8f, sFish.getX()+40, sFish.getX()-10, sFish.getY()-40, 
							sFish.getY()-10, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sFish.setRotation(0);
							istouchFish=true;
							moveFishFirst();
						}
					}))));
		}
	}
	private void moveFishFirst(){
		float duration1 = 6*(sFish.getX()-884)/50;
		Log.d(TAG, "gia tri duration: " + duration1);
		sFish.registerEntityModifier(new MoveModifier(duration1, sFish.getX(), 884, sFish.getY(), 
				390, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				touchFishFirst=true;
				touchFishBack=false;
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				sFish.setCurrentTileIndex(1);
				moveFishBack();
			}
		}));
	}
	private void moveFishBack(){
		float duration2 = 6*(934-sFish.getX())/50;
		Log.d(TAG, "gia tri duration: " + duration2);
		sFish.registerEntityModifier(new MoveModifier(duration2, sFish.getX(), 934, sFish.getY(), 
				510, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				touchFishFirst=false;
				touchFishBack=true;
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				sFish.setCurrentTileIndex(0);
				moveFishFirst();
			}
		}));
	}
	private void touchSakura() {
		if (istouchSakura) {
			istouchSakura = false;
			sSakura2.setVisible(true);
			A3_12_KIRA6.play();
			sSakura2.registerEntityModifier(new MoveXModifier(1.0f, sSakura2.getX(), sSakura2.getX()+40,
					new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSakura = true;
					sSakura2.setVisible(false);
					sSakura2.setPosition(788, 458);
				}
			}));
		}
	}
	private void touchManbou() {
		if (istouchManbou) {
			istouchManbou = false;
			A3_27_MANBOU.play();
			sarrMabou[0].setVisible(true);
			sarrMabou[0].registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(3.0f,sarrMabou[0].getX(),sarrMabou[0].getX()+50, 
							sarrMabou[0].getY(), sarrMabou[0].getY()+ 80, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									sarrMabou[0].setVisible(false);
									sarrMabou[0].setPosition(arrPointManbou[0][0], arrPointManbou[1][0]);
								}
							})));
			sarrMabou[1].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.0f, 
					new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sarrMabou[1].setVisible(true);
				}
			}),new MoveModifier(2.8f,sarrMabou[1].getX(),sarrMabou[1].getX() + 60,
					sarrMabou[1].getY(), sarrMabou[1].getY()+ 70, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							sarrMabou[1].setVisible(false);
							sarrMabou[1].setPosition(arrPointManbou[0][1], arrPointManbou[1][1]);
						}
					})));
			
			sarrMabou[2].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.5f, 
					new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sarrMabou[2].setVisible(true);
				}
			}), new MoveModifier(3.6f,sarrMabou[2].getX(),sarrMabou[2].getX()+50,
					sarrMabou[2].getY(),sarrMabou[2].getY()+ 80, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							sarrMabou[2].setVisible(false);
							sarrMabou[2].setPosition(arrPointManbou[0][2], arrPointManbou[1][2]);
						}
					})));
			sarrMabou[3].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(2.0f, 
					new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sarrMabou[3].setVisible(true);
				}
			}), new MoveModifier(3.2f, sarrMabou[3].getX(),sarrMabou[3].getX()+60,
					sarrMabou[3].getY(), sarrMabou[3].getY()+ 70, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sarrMabou[3].setVisible(false);
							sarrMabou[3].setPosition(arrPointManbou[0][3], arrPointManbou[1][3]);
						}
					})));
			sarrMabou[4].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(2.5f,
					new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sarrMabou[4].setVisible(true);
				}
			}), new MoveModifier(3.0f, sarrMabou[4].getX(),sarrMabou[4].getX()+20,
					sarrMabou[4].getY(), sarrMabou[4].getY()+ 60, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							sarrMabou[4].setVisible(false);
							sarrMabou[4].setPosition(arrPointManbou[0][4], arrPointManbou[1][4]);
						}
					})));
			sarrMabou[5].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(3.0f),
					new MoveModifier(4.1f, sarrMabou[5].getX(),sarrMabou[5].getX()+40,
							sarrMabou[5].getY(), sarrMabou[5].getY()+ 60,
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							sarrMabou[5].setVisible(true);
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							
							sarrMabou[5].setVisible(false);
							sarrMabou[5].setPosition(arrPointManbou[0][5], arrPointManbou[1][5]);
							istouchManbou = true;
						}
					})));
		}
	}
	
	private void touchTako(){
		if (istouchTako) {
			istouchTako = false;
			A3_KAMETAKO.play();
			long pDuration[] = new long[]{ 400, 400};
			int pFrame[] = new int[]{1,0};
			aniTako.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchKame() {
		if (istouchKame) {
			istouchKame = false;
			A3_KAMETAKO.play();
			long pDuration[] = new long[]{ 400, 400};
			int pFrame[] = new int[]{1,0};
			aniKame.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchHouse(){
		if (istouchHouse) {
			istouchHouse = false;
			aniBird.setVisible(true);
			A3_34_BASA.play();// chim bay
			A3_34_HYUDON.play();//phao hoa
			aniBird.animate(300,-1);
			aniBird.registerEntityModifier(new MoveModifier(3.0f, aniBird.getX(), aniBird.getX()- 1000,
					aniBird.getY(), aniBird.getY() + 600, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchHouse = true;
					sFireWork2.setVisible(false);
					sFireWork1.setVisible(false);
					sFireWork3.setVisible(false);
					aniBird.setPosition(1239, 1138);
					aniBird.stopAnimation();
					aniBird.setVisible(false);
				}
			}));
			
			sSticks.setVisible(true);
			sSticks.registerEntityModifier(new MoveModifier(1.3f, sSticks.getX(),sSticks.getX()+20,
					sSticks.getY(), sSticks.getY()+20, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							int random = new Random().nextInt(3);
							sSticks.setVisible(false);
							sSticks.setPosition(1212,1128);
							switch (random) {
							case 0:
								sFireWork1.registerEntityModifier(new ScaleModifier(0.5f, 0.3f, 1.0f));
								sFireWork1.setVisible(true);
								break;
							case 1:
								sFireWork2.registerEntityModifier(new ScaleModifier(0.5f, 0.3f, 1.0f));
								sFireWork2.setVisible(true);
								break;
							case 2:
								sFireWork3.registerEntityModifier(new ScaleModifier(0.5f, 0.3f, 1.0f));
								sFireWork3.setVisible(true);
								break;
							default:
								break;
							}
						}
					}));
			
		}
		
	}
	private void touchDragon() {
		if (istouchDragon) {
			istouchBoy = false;
			istouchDragon = false;
			long pDuration[] = new long[]{ 400, 400,400,300,300};
			int pFrame[] = new int[]{1,2,3,4,5};
			A3_31_DRAGON.play();
			aniDragon.animate(pDuration,pFrame,0,this);
		}
	}
	private void boyKissGirl() {
		if (istouchBoy) {
			istouchBoy = false;
			istouchDragon = false;
			A3_30_OUJITOHIME.play();
			long pDuration[] = new long[]{ 1500, 200};
			int pFrame[] = new int[]{1,0};
			sFutari2.setVisible(true);
			aniFutari1.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchFishRed2() {
		if (istouchFishRed1) {
			istouchFishRed1 = false;
			A3_26_SAKANA.play();
		 
			 sFishRed.registerEntityModifier(new SequenceEntityModifier(
					 new ParallelEntityModifier(new RotationModifier(0.4f,0,-50), 
							 new MoveModifier(0.8f, sFishRed.getX(), sFishRed.getX()+30, 
									 sFishRed.getY(), sFishRed.getY()+40, new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
											sFishRed.setRotation(0);
										}
									})),
					 new ParallelEntityModifier(new RotationModifier(0.3f,0,50),
							 new MoveModifier(0.8f, sFishRed.getX()+30, sFishRed.getX(), 
									 sFishRed.getY()+40, sFishRed.getY(), new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
											sFishRed.setRotation(0);
											istouchFishRed1 = true;
											Log.d(TAG, "istouchFishRed2:" + istouchFishRed2);
										}
									}) )
					 ));
			 
			}
		 
	}
	private void touchFishRed1() {
		if (istouchFishRed1) {
			istouchFishRed1 = false;
		
			A3_26_SAKANA.play();
			 sFishRed.registerEntityModifier(new SequenceEntityModifier(
					 new ParallelEntityModifier(new RotationModifier(0.3f,0,50), 
							 new MoveModifier(0.7f, sFishRed.getX(), sFishRed.getX()+40, 
									 sFishRed.getY(), sFishRed.getY()+50, new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
											sFishRed.setRotation(0);
										}
									})),
					 new ParallelEntityModifier(new RotationModifier(0.3f,0,-50),
							 new MoveModifier(0.7f, sFishRed.getX()+40, sFishRed.getX(), 
									 sFishRed.getY()+50, sFishRed.getY(), new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											sFishRed.setRotation(0);
											istouchFishRed1 = true;
											Log.d(TAG, "istouchFishRed1:" + istouchFishRed1);
										}
									}) )
					 ));
		 
		}
	}
	private void moveFishRed() {
		sFishRed.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(10.0f, sFishRed.getX(), sFishRed.getX()+160, 
						new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						istouchFishRedFirst = true;
						istouchFishRedBack = false;
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sFishRed.setFlippedHorizontal(true);
					}
				}),
				new MoveXModifier(10.0f, sFishRed.getX() + 160, sFishRed.getX(), new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						istouchFishRedFirst=false;
						istouchFishRedBack=true;
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sFishRed.setFlippedHorizontal(false);
					}
				}))));
	}
	private void touchFishSmall1() {
		if (istouchFishSmall) {
			istouchFishSmall = false;
			A3_26_SAKANA.play();
			sFishSmall.registerEntityModifier(fishSmallModifire = new SequenceEntityModifier(
					new ParallelEntityModifier(new RotationModifier(0.4f,0,-20),
					new MoveYModifier(0.8f, sFishSmall.getY(), sFishSmall.getY()+30)),
					new ParallelEntityModifier(new RotationModifier(0.4f,-20,20),
					new MoveYModifier(0.8f, sFishSmall.getY()+30, sFishSmall.getY()))));
			fishSmallModifire.addModifierListener(this);
		}
		return;
	}
	private void touchFishSmall2(){
		if (istouchFishSmall) {
			istouchFishSmall = false;
			A3_26_SAKANA.play();
			sFishSmall.registerEntityModifier(fishSmallModifire = new SequenceEntityModifier(
					new ParallelEntityModifier(new RotationModifier(0.4f,0,20),
					new MoveYModifier(0.6f, sFishSmall.getY(), sFishSmall.getY()+30)),
					new ParallelEntityModifier(new RotationModifier(0.4f,20,-20),
					new MoveYModifier(0.6f, sFishSmall.getY()+30, sFishSmall.getY()))));
			fishSmallModifire.addModifierListener(this);
		}
	}
	private void moveFishSmall() {
		sFishSmall.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(8.0f, sFishSmall.getX(), sFishSmall.getX()-140, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						touchFishSmallFirst=true;
						touchFishSmallBack=false;
						sFishSmall.setFlippedHorizontal(true);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					}
				}),
				new MoveXModifier(8.0f, sFishSmall.getX()-140, sFishSmall.getX(),
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						touchFishSmallFirst=false;
						touchFishSmallBack=true;
						sFishSmall.setFlippedHorizontal(false);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					}
				}))));
	}
	private void moveTako() {
		aniTako.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(16.0f, aniTako.getX(), aniTako.getX()-160, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aniTako.setFlippedHorizontal(true);
					}
				}),
				new MoveXModifier(16.0f, aniTako.getX()-160, aniTako.getX(), new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aniTako.setFlippedHorizontal(false);
					}
				}))));
	}
	private void moveKame() {
		aniKame.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveModifier(10.0f, aniKame.getX(),aniKame.getX()+112,
						aniKame.getY(), aniKame.getY()-166, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								aniKame.setFlippedVertical(true);
								aniKame.registerEntityModifier(new RotationModifier(0.001f,0,60));
							}
						}),
						new MoveModifier(10.0f, aniKame.getX()+112,aniKame.getX(),
								aniKame.getY()-166, aniKame.getY(), new IEntityModifierListener() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										// TODO Auto-generated method stub
										aniKame.setFlippedVertical(false);
										aniKame.setRotation(0);
//										aniKame.setFlippedHorizontal(false);
									}
								}))));
	}
	private void touchOranda(){
		if (istouchOranda) {
			istouchOranda = false;
			A3_29_ORANDA.play();
			aniOranda.setCurrentTileIndex(1);
			aniOranda.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f),
					new MoveYModifier(0.1f,
					 aniOranda.getY(), aniOranda.getY()-70), 
					new DelayModifier(1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							aniOranda.setVisible(false);
						}
					}), new DelayModifier(0.5f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							aniOranda.setPosition(826, 1147);
							aniOranda.setCurrentTileIndex(0);
							aniOranda.setVisible(true);
							istouchOranda = true;
						}
					})));
		}
		return;
	}
	private void moveShip() {
		sShip.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.5f, sShip.getY(), sShip.getY() - 4),
				new MoveYModifier(0.5f, sShip.getY() - 4, sShip.getY()),
				new MoveYModifier(0.5f, sShip.getY(), sShip.getY() + 4),
				new MoveYModifier(0.5f, sShip.getY() + 4, sShip.getY()))));
	}
	private void moveManbou(){
		sManbou.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.5f, 0, -10, 31, 73),
				new RotationAtModifier(0.5f, -10, 0, 31, 73))));
	}
	private void touchFusya() {
		if (istouchFusya) {
			istouchFusya = false;
			A3_33_GIGIGI.play();
			sFusya2.registerEntityModifier(new RotationModifier(2.8f, 0, 360, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchFusya = true;
				}
			}, EaseLinear.getInstance()));
			
		}
		return;
	}
	private void touchRion(){
		if (istouchRion) {
			istouchRion = false;
			A3_22_RION.play();
			aniRion.stopAnimation();
			long pDuration[] = new long[]{300, 300, 300, 500};
			int pFrame[] = new int[]{2,3,4,5};
			aniRion.animate(pDuration,pFrame,0,this);
		}
		return;
	}
	private void touchKirin(){
		if (istouchKirin) {
			istouchKirin=false;
			aniKirin.stopAnimation();
			aniKirin.setCurrentTileIndex(2);
			A3_24_KIRIN.play();
			
			timeKirin.schedule(new TimerTask() {
				
				@Override
				public void run() {
					istouchKirin=true;
					moveKirin();
				}
			}, 1200);
		}
		return;
	}
	private void touchZou() {
		if (istouchZou) {
			istouchZou = false;
			long pDuration[] = new long[]{700, 800, 500};
			int pFrame[] = new int[]{1,2,0};
			A3_25_ZOU.play();
			aniZou.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchMasai() {
		if (istouchMasai) {
			istouchMasai = false;
			A3_23_AFRIKA2.play();
			sMasai.registerEntityModifier(masaiModifire = new SequenceEntityModifier(
					new MoveModifier(0.2f, sMasai.getX(), sMasai.getX()-50, sMasai.getY(), sMasai.getY()+50),
					new MoveModifier(0.2f, sMasai.getX()-50, sMasai.getX(), sMasai.getY()+50, sMasai.getY()),
					new MoveModifier(0.2f, sMasai.getX(), sMasai.getX()-50, sMasai.getY(), sMasai.getY()+50),
					new MoveModifier(0.2f, sMasai.getX()-50, sMasai.getX(), sMasai.getY()+50, sMasai.getY()),
					new MoveModifier(0.2f, sMasai.getX(), sMasai.getX()-50, sMasai.getY(), sMasai.getY()+50),
					new MoveModifier(0.2f, sMasai.getX()-50, sMasai.getX(), sMasai.getY()+50, sMasai.getY()),
					new MoveModifier(0.2f, sMasai.getX(), sMasai.getX()-50, sMasai.getY(), sMasai.getY()+50),
					new MoveModifier(0.2f, sMasai.getX()-50, sMasai.getX(), sMasai.getY()+50, sMasai.getY())));
			masaiModifire.addModifierListener(this);
		}
		return;
	}
	private void moveRion(){
		long pDuration[] = new long[]{500, 500};
		int pFrame[] = new int[]{0,1};
		aniRion.animate(pDuration,pFrame,-1);
		return;
	}
	private void moveKirin() {
		long pDuration[] = new long[]{500, 500};
		int pFrame[] = new int[]{0,1};
		aniKirin.animate(pDuration,pFrame,-1);
		return;
	}
	private void touchPiramid(){
		if (istouchPiramid) {
			istouchPiramid = false;
			A3_32_PIRAMID.play();
			long pDuration[] = new long[]{1000, 500};
			int pFrame[] = new int[]{1,0};
			aniPiramid.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchSfinks(){
		if (istouchSfinks) {
			istouchSfinks = false;
			A3_21_SFINKS2.play();
			sSfinks.setRotationCenter(11, 1);
			sSfinks.registerEntityModifier(sfinksModifire = new SequenceEntityModifier(
					new RotationModifier(1.3f, 0, 50),
					new RotationModifier(1.3f, 50, 0)));
			sfinksModifire.addModifierListener(this);
		}
	}
	private void touchTsutan() {
		if (istouchTsutan) {
			istouchTsutan = false;
			aniTsutan2.setCurrentTileIndex(1);
			A3_20_TSUTAN2.play();
			A3_20_WA.play();
			sTsutan3.registerEntityModifier(new MoveModifier(1.2f, sTsutan3.getX(), 
					sTsutan3.getX()-600, sTsutan3.getY(),sTsutan3.getY()+ 600,
					new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchTsutan = true;
					aniTsutan2.setCurrentTileIndex(0);
					sTsutan3.setPosition(327, 903);
				}
			}));
		}
		return;
	}
	private void touchTaban() {
		if (istouchTaban) {
			istouchTaban = false;
			long pDuration[] = new long[]{500, 500};
			int pFrame[] = new int[]{1,2};
			A3_35_ARABU.play();
			aniTaban.animate(pDuration,pFrame,1,this);
			
			
		}
	}
	private void touchShiro() {
		if (istouchShiro) {
			istouchShiro = false;
			long pDuration[] = new long[]{600, 500};
			int pFrame[] = new int[]{1,0};
			A3_19_SHIRO.play();
			aniShiro.animate(pDuration,pFrame,0, this);
			
		}
	}
	
	
	private void touchSuperman() {
		if (istouchSuperman) {
			istouchSuperman = false;
			A3_15_SUPAMAN.play();
			sSuperman.registerEntityModifier(supermanModifire = new SequenceEntityModifier(
					new ParallelEntityModifier(
					new RotationAtModifier( 0.8f, 0, -60, 97, 65),
					new MoveModifier(1.0f, sSuperman.getX(), sSuperman.getX()-20,
							sSuperman.getY(), sSuperman.getY()-250)),
							new ParallelEntityModifier(new MoveModifier(3.0f, sSuperman.getX()-20, 
									sSuperman.getX()-700, sSuperman.getY()-250, sSuperman.getY()-500),
									new RotationAtModifier(1.0f, -60, -110, 97, 65))));
			supermanModifire.addModifierListener(this);
		}
		return;
	}
	private void touchSamurai() {
		if (istouchSamurai) {
			istouchSamurai = false;
			long pDuration[] = new long[]{100, 100, 100, 100, 100, 300};
			int pFrame[] = new int[]{1,2,3,4,5,0};
			A3_17_SAMURAI.play();
			aniSamurai.animate(pDuration,pFrame,0,this);
		}
	}
	private void touchPanda() {
		if (istouchPanda) {
			istouchPanda = false;
			long pDuration[] = new long[]{300, 300};
			int pFrame[] = new int[]{1,2};
			A3_18_PANDA3.play();
			aniPanda.animate(pDuration,pFrame,1,this);
		}
		return;
	}
	private void touchTower() {
		if (istouchTower) {
			istouchTower = false;
			A3_16_TOWER.play();
			sTower.registerEntityModifier(new SequenceEntityModifier(
			new MoveYModifier(0.8f, sTower.getY(), sTower.getY()-600),
			new DelayModifier(0.4f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					istouchTower = true;
					sTower.setPosition(772, 304);
				}
			})));
			
		}
	}
	private void touchNinja() {
		if (istouchNinja) {
			istouchNinja = false;
			A3_14_NINJA.play();
			long pDuration[] = new long[]{500, 500, 500};
			int pFrame[] = new int[]{2,3,4};
			aniNinja.setCurrentTileIndex(1);
			aniNinjaSmoke.setVisible(true);
			aniNinjaSmoke.animate(pDuration, pFrame, 0, this);
			
		}
		
		return;
	}
	private void touchTora() {
		if (istouchTora) {
			istouchTora = false;
			A3_13_TORA.play();
			sTora.setRotationCenter(162, 19);
			sTora.registerEntityModifier(toraModifire = new SequenceEntityModifier(
					new RotationModifier(1.0f, 0, 30),
					new RotationModifier(1.0f, 30, 0)));
			toraModifire.addModifierListener(this);
		}
		
		return;
	}
	private void touchFuraGirl() {
		if (istouchFuraGirl) {
			istouchFuraGirl = false;
			A3_11_HAWAI.play();
			long pDuration[] = new long[]{700,700,800,500};
			int pFrame[] = new int[]{1,2,3,4};
			aniFuraGirl.animate(pDuration,pFrame,0,this);
			
		}
		return;
	}
	private void touchMegami() {
		if (istouchMegami) {
			istouchMegami = false;
			A3_10_MEGAMI.play();
			A3_10_GUBAY2.play();
			sMegami2.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(1.2f, sMegami2.getX()	, sMegami2.getX() + 700),
					new DelayModifier(0.4f,new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchMegami = true;
					sMegami2.setPosition(1212, 734);
				}
			})));
		}
		return;
	}
	private void touchCowboy() {
		if (istouchCowboy){
			istouchCowboy = false;
			A3_9_BOUSHITOBU.play();
			A3_9_OMYGOD.play();
		sHat.registerEntityModifier(new SequenceEntityModifier(new MoveXModifier(1.2f, sHat.getX(),
				sHat.getX() + 700 ), new DelayModifier(0.4f, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sHat.setPosition(1168, 662);
						istouchCowboy= true;
					}
				})));
		}
		return;
	}
	private void touchShip() {
		if (istouchShip) {
			istouchShip = false;
			sSmoke.setVisible(true);
			A3_6_BO1.play();
			Timer timeShip = new Timer();
			timeShip.schedule(new TimerTask() {
				@Override
				public void run() {
					istouchShip = true;
					sSmoke.setVisible(false);
				}
			}, 1000);
		}
		return;
	}
	
	private void touchChina(){
		if (istouchChina) {
			istouchChina = false;
			aniChina1.setCurrentTileIndex(1);
			A3_4_NIHAO.play();
			A3_4_SUPON.play();
			sChina2.registerEntityModifier(new SequenceEntityModifier( new MoveModifier(0.6f, sChina2.getX(), 
					sChina2.getX() - 300, sChina2.getY(), sChina2.getY() - 300), 
					new DelayModifier(0.4f,new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							istouchChina = true;
							aniChina1.setCurrentTileIndex(0);
							sChina2.setPosition(447, 377);
						}
					})));
			
		}
		return;
	}
	private void touchIndian() {
		if (istouchIndian) {
			istouchIndian = false;
			A3_3_AWA2.play();
			long pDuration[] = new long[]{500,500,400};
			int pFrame[] = new int[]{1,2,0};
			aniIndian.animate(pDuration,pFrame,0,this);
		}
		return;
	}
	private void touchSanba() {
		if (istouchSanba) {
			istouchSanba = false;
			A3_2_SANBA.play();
			long pDuration[] = new long[]{100,100,100,100};
			int pFrame[] = new int[]{1,2,3,4};
			aniSanba.animate(pDuration, pFrame, 3, this);
			
		}
		
		return;
	}
	private void touchMaiko() {
		if (istouchMako) {
			istouchMako = false;
			A3_1_MAIKO.play();
			A3_1_OIDEYASU2.play();
			long pDuration[] = new long[]{800,400};
			int pFrame[] = new int[]{1,0};
			aniMaiko.animate(pDuration, pFrame, 0, this);
		}
		return;
		
	}
	private void resetData() {
		if (sBgrRotat!=null) {
			sBgrRotat.clearEntityModifiers();
			sBgrRotat.setRotation(0.0f);
		}
		initial();
		if (sFish!=null) {
			sFish.clearEntityModifiers();
			sFish.setRotation(0);
			sFish.setCurrentTileIndex(0);
			sFish.setPosition(934, 510);
		}
		if (sFishRed!=null) {
			sFishRed.clearEntityModifiers();
			sFishRed.setRotation(0);
			sFishRed.setPosition(800, 960);
		}
		if (sFishSmall!= null) {
			sFishSmall.clearEntityModifiers();
			sFishSmall.setRotation(0);
			sFishSmall.setPosition(712, 796);
		}
		if (aniTako != null){
			aniTako.clearEntityModifiers();
			aniTako.setPosition(659, 616);
			aniTako.setCurrentTileIndex(0);
		}
		if (aniRakura!=null) {
			aniRakura.setCurrentTileIndex(0);
		}
		if (aniOranda!=null) {
			aniOranda.setPosition(826, 1147);
			aniOranda.setCurrentTileIndex(0);
			aniOranda.setVisible(true);
			aniOranda.setVisible(true);
			
		}
		if (sSticks != null){
			sSticks.clearEntityModifiers();
			sSticks.setVisible(false);
			sSticks.setPosition(1212,1128);
		}
		return;
	}
	private void moveBackground() {
		
		sBgrRotat.setRotationCenter(784, 792);
		sBgrRotat.registerEntityModifier(new LoopEntityModifier(new RotationModifier(110.0f, 0, 360)));
		
		return;
	}
	private void initial() {
		//TODO
		istouchMako = true;
		istouchSanba = true;
		istouchIndian = true;
		istouchChina = true;
		istouchShip = true;
		istouchCowboy = true;
		istouchMegami = true;
		istouchFuraGirl = true;
		istouchTora = true;
		istouchNinja = true;
		istouchTower = true;
		istouchPanda = true;
		istouchSamurai = true;
		istouchSuperman = true;
		istouchShiro = true;
		istouchTaban = true;
		istouchTsutan = true;
		istouchSfinks = true;
		istouchPiramid = true;
		istouchMasai = true;
		istouchZou = true;
		istouchRion = true;
		istouchFusya = true;
		istouchOranda = true;
		istouchBoy = true;
		istouchDragon = true;
		istouchHouse = true;
		istouchKame = true;
		istouchTako = true;
		istouchFishSmall = true;
		istouchManbou = true;
		istouchFish = true;
		istouchSakura = true;
		istouchFishRed1 = true;
		istouchFishRed2 = true;
		istouchFishRedFirst=false;
		istouchFishRedBack=false;
		touchFishSmallFirst = false;
		touchFishSmallBack = false;
		touchFishFirst = false;
		touchFishBack = false;
		istouchKirin = true;
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		if (pModifier.equals(toraModifire)) {
			istouchTora = true;
			sTora.setRotation(0.0f);
		}
		if (pModifier.equals(chinaModifire)) {
			istouchChina = true;
			aniChina1.setCurrentTileIndex(0);
			sChina2.setPosition(447, 377);
		}
		if (pModifier.equals(supermanModifire)) {
			istouchSuperman = true;
			sSuperman.setPosition(1114, 521);
			sSuperman.setRotation(0.0f);
		}
		if (pModifier.equals(sfinksModifire)) {
			istouchSfinks = true;
			sSfinks.setRotation(0.0f);
		}
		if (pModifier.equals(masaiModifire)) {
			istouchMasai = true;
		}
//		if (pModifier.equals(fishRedModifire)){
//			sFishRed.setRotation(0);
//			istouchFishRed1=true;
//		}
		if (pModifier.equals(fishSmallModifire)) {
			sFishSmall.setRotation(0);
			istouchFishSmall = true;
		}
	}
	
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimateSprite) {
		// TODO Auto-generated method stub
		if (pAnimateSprite.equals(aniMaiko)) {
			istouchMako = true;
		}
		if (pAnimateSprite.equals(aniSanba)) {
			istouchSanba = true;
		}
		if (pAnimateSprite.equals(aniIndian)) {
			istouchIndian = true;
		}
		if (pAnimateSprite.equals(aniFuraGirl)) {
			istouchFuraGirl = true;
		}
		if (pAnimateSprite.equals(aniNinjaSmoke)) {
			istouchNinja = true;
			aniNinjaSmoke.setVisible(false);
			aniNinja.setCurrentTileIndex(0);
			aniNinjaSmoke.setCurrentTileIndex(2);
		}
		if (pAnimateSprite.equals(aniPanda)) {
			istouchPanda = true;
			aniPanda.setCurrentTileIndex(0);
		}
		if (pAnimateSprite.equals(aniSamurai)) {
			istouchSamurai = true;
		}
		if (pAnimateSprite.equals(aniShiro)){
			istouchShiro = true;
		} 
		if (pAnimateSprite.equals(aniTaban)) {
			aniTaban.setCurrentTileIndex(0);
			istouchTaban = true;
		}
		if (pAnimateSprite.equals(aniPiramid)) {
			istouchPiramid = true;
		}
		if (pAnimateSprite.equals(aniZou)) {
			istouchZou = true;
		}
		if (pAnimateSprite.equals(aniRion)) {
			istouchRion=true;
			moveRion();
		}
		if (pAnimateSprite.equals(aniFutari1)) {
			istouchBoy = true;
			istouchDragon = true;
			sFutari2.setVisible(false);
		}
		if (pAnimateSprite.equals(aniDragon)) {
			aniFutari1.stopAnimation();
			aniFutari1.setCurrentTileIndex(2);
			sFutari3.setVisible(true);
			sFutari4.setVisible(true);
			Timer timeDragon = new Timer();
			timeDragon.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					aniDragon.setCurrentTileIndex(0);
					
				}
			},300);
			Timer timeBoy = new Timer();
			timeBoy.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					aniFutari1.setCurrentTileIndex(0);
					sFutari3.setVisible(false);
					sFutari4.setVisible(false);
					istouchBoy = true;
					istouchDragon = true;
				}
			},2000);
			
		}
		if (pAnimateSprite.equals(aniKame)){
			istouchKame = true;
		}
		if (pAnimateSprite.equals(aniTako)) {
			istouchTako = true;
		}
		
	}
	@Override
	public void combineGimmic3WithAction() {
		A3_YUBIBUE_3.play();
	}
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		moveBackground();
		//TODO
		initial();
		moveFishFirst();
		moveKirin();
		moveRion();
		moveKame();
		moveFishRed();
		moveTako();
		moveFishSmall();
		moveShip();
		moveManbou();
		super.onResumeGame();
	}
	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onPauseGame: ");
		resetData();
		super.onPauseGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, "loadKaraokeComplete: ");
		super.loadKaraokeComplete();
	}
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
		
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}


}
