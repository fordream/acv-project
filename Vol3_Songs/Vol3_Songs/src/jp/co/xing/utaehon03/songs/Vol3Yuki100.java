package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3Yuki100Resource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Yuki100 extends BaseGameActivity implements IEntityModifierListener, IOnSceneTouchListener{
	ArrayList<Integer> listAC, listBF , listDG;
	private static final String TAG = "LOG_YUKI100";
	
	TimerHandler rtime1 , rtime2,rtime3,rtime4,rtime5,rtime6,rtimeSky;
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TexturePack ttpYuki1,ttpYuki2,ttpYuki3,ttpYuki4,ttpYuki5,ttpYuki6,ttpYuki7
	,ttpYuki8,ttpYuki9,ttpYuki10,ttpYuki11;
	
	private TexturePackTextureRegionLibrary ttpLibYuki1,ttpLibYuki2,ttpLibYuki3,ttpLibYuki4,
	ttpLibYuki5,ttpLibYuki6,ttpLibYuki7,ttpLibYuki8,ttpLibYuki9,ttpLibYuki10,ttpLibYuki11;
	
	private TextureRegion ttrCastle1, ttrCastle2, ttrCastle3, ttrCastle4, ttrCastle5, ttrBackSky, ttrFontBack,
	ttrFusmaLeft, ttrFusmaRight, ttrRoom, ttrRoomRight,ttrRoomLeft, ttrTenjoanalLeft,ttrTenjoanalRight, ttrHusumar, 
	ttrHusumal, ttrZabuttonLeft1,ttrZabuttonLeft2, ttrZabuttonRight1, ttrZabuttonRight2,ttrTable2
	,ttrMouseLeft, ttrMouseRight, ttrTable ,ttrBandRight, ttrMono258, ttrMono257, ttrMono255, 
	
	ttrNinjaGray,ttrFNinjaYeloow,ttrFNinjaPink,ttrFNinjaLime, ttrFNinjaOrange,ttrFNinjaGreen,ttrFNinjaLightBlue, 
	ttrFNinjaDarkBlue, ttrFNinjaGray,ttrFNinjaViolet,ttrFNinjaBlack,ttrTeng181, ttrTeng182, ttrTeng183,
	
	ttrHand251, ttrHand252,ttrHand253,ttrHand254,ttrHand255,ttrHand256,ttrHand257,ttrHand258,ttrHand259
	,ttrHand2511,ttrHand2512,ttrHand2515,ttrHand2516,ttrKing, ttrPrincess,ttrTatamiR , ttrTatamiShaR,
	ttrTatamiL , ttrTatamiShaL,ttrSmoke1,ttrSmoke2,ttrSmoke3,ttrSmoke4, 
	
	ttrEyeNinja, ttrFaceYellow, ttrFacePink, ttrFaceLime, ttrFaceOrange, ttrFaceGreen, ttrFaceLightBlue,
	ttrFaceDarkBlue, ttrFaceGray, ttrFaceViolet, ttrFaceBlack, ttrGimmic;
	
	private Sprite sCastle1, sCastle2, sCastle3, sCastle4, sCastle5, sBackSky, sFontBack, sFusmaLeft, sFusmaRight,
	
	sRoom, sRoomRight, sRoomLeft, sTenjoanalLeft, sTenjoanalRight, sHusumar, sHusumal, sTable2,
	sZabuttonLeft1, sZabuttonLeft2, sZabuttonRight1, sZabuttonRight2, sMouseLeft,sMouseRight, sTable, sBandRight
	, sMono258, sMono257, sMono255,sTatamiR, sTatamiShaR, sTatamiL, sTatamiShaL, sSmoke1,sSmoke2,sSmoke3,sSmoke4,
	
	sFNinjaYeloow,sFNinjaPink,sFNinjaLime,sFNinjaOrange,sFNinjaGreen,sFNinjaLightBlue,
	sFNinjaDarkBlue,sFNinjaGray,sFNinjaViolet,sFNinjaBlack, sTeng181,sTeng182,sTeng183,sKing, sPrincess,
	
	sHand251,sHand252,sHand253,sHand254,sHand255,sHand256,sHand257,sHand258,sHand259,sHand2511,sHand2512,sHand2515,
	sHand2516,sGimmic,
	
	sYellow,sPink,sLime,sOrange,sGreen,sLightBlue,sDarkBlue,sGray,sViolet,sBlack;
	
	private TiledTextureRegion 
		tttrNinjaA[] = new TiledTextureRegion[4],
		tttrNinjaB[] = new TiledTextureRegion[4],
		tttrNinjaC[] = new TiledTextureRegion[4],
		tttrNinjaD[] = new TiledTextureRegion[2],
		tttrNinjaF[] = new TiledTextureRegion[4],
		tttrNinjaG[] = new TiledTextureRegion[2];
	
	private DelayModifier deNinjaA, deNinjaC, deTeng183,deTeng181,deTeng182,
	deNinjaB, deNinjaF, deKing,
	deNinjaG, deNinjaD, dePrincess,
	deSmoke1,deSmoke2,deSmoke3,deSmoke4;
	
	private float pointNinja[][] = {
			{10,113,10,113,10,113,10,113,10,113},
			{90,90,190,190,290,290,390,390,490,490}};
	
	private Sprite sNinjaGray[] = new Sprite[10];
	
	private Sprite 
		sNinjaA[] = new Sprite[4],
		sNinjaC[] = new Sprite[4],
		sNinjaB[] = new Sprite[4],
		sNinjaF[] = new Sprite[4],
		sNinjaD[] = new Sprite[2],
		sNinjaG[] = new Sprite[2],
		sEye[] = new Sprite[10];
	
	private boolean  isTouchMouseLeft, isTouchMouseRight, isTouchFace, isTouchNinja;
	
	private RectangularShape shape , shapeBackGround;
	
	private boolean  isCatchNinja ,isTouchHand251, isTouchHand254, isTouchHand255,isTouchHand257,isTouchHand258,
	isTouchHand256,isTouchHand259,isTouchHand2511,isTouchHand2515,isTouchHand2516, isTouchHand252, isTouchHand253,
	isTouchHand2512, isTouchTable;
	
	Random rRandomCase, rNinjaAC, rNinjaBF, rNinjaDG, ranTeng, ranKing , ranPrincess;
	
	private int rCaseAC, rCaseBF, rCaseDG, caseNinjaAC, caseNinjaBF,caseNinjaDG, currentPosition, rTeng,rKing,rPrincess;
	
	private Sound OGG_A6_1_10SAGASE,OGG_A6_7_10,OGG_A6_7_11,OGG_A6_7_1_1,OGG_A6_7_1_2,OGG_A6_7_1_3,OGG_A6_7_1_4,
	OGG_A6_7_1_5,OGG_A6_7_2,OGG_A6_7_3,OGG_A6_7_4,OGG_A6_7_5,OGG_A6_7_6,OGG_A6_7_7,OGG_A6_7_8,OGG_A6_7_9,
	OGG_A6_FUSUMA,OGG_A6_HOHOHO,OGG_A6_ICON_KAITEN,OGG_A6_KING_OHOHO,OGG_A6_KING_TONODEOJARU,OGG_A6_NINJA_MITSUKATA,
	OGG_A6_NINJA_MITSUKATA2,OGG_A6_NINJA_MITSUKATA3,OGG_A6_NINJA_MITSUKATA4,OGG_A6_NINJA_MITSUKATEMOTA,
	OGG_A6_NINJA_MITSUKATEMOTA2,OGG_A6_NINJA_MITSUKATEMOTA3,OGG_A6_NINJA_MITSUKATEMOTA4,OGG_A6_NINJA_MITSUKATEMOTA5,
	OGG_A6_NINJA_MUMUNEN,OGG_A6_NINJA_MUNEN,OGG_A6_NINJA_MUNENJA1,OGG_A6_NINJA_MUNENJA2,OGG_A6_NINJA_MUNENJA3,
	OGG_A6_NINJA_MUNENJA4,OGG_A6_NINJA_MUNENJA5,OGG_A6_NINJA_SHIMATA,OGG_A6_NINJA_SHIMATA2,OGG_A6_NINJA_SHIMATA3,
	OGG_A6_NINJA_SHIMATA4,OGG_A6_NINJA_SHIMATA5,OGG_A6_NINJA_SHIMATA6,OGG_A6_NINJA_SHIMATA7,OGG_A6_NINJA_SMOKE,
	OGG_A6_NINJA_TAUCH,OGG_A6_QUEEN_HIMEJA,OGG_A6_QUEEN_TIGAU,OGG_A6_ZABUTON;
	
	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("yuki100/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("yuki100/gfx/");
		
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "yuki100/gfx/");

		Log.d(TAG, "onCreateResources : end");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources: ");
		//Lib
		ttpYuki1 = mTexturePackLoaderFromSource.load("yuki1001.xml");
		ttpYuki1.loadTexture();
		ttpLibYuki1 = ttpYuki1.getTexturePackTextureRegionLibrary();
	
		ttpYuki2 = mTexturePackLoaderFromSource.load("yuki1002.xml");
		ttpYuki2.loadTexture();
		ttpLibYuki2 = ttpYuki2.getTexturePackTextureRegionLibrary();
	
		ttpYuki3 = mTexturePackLoaderFromSource.load("yuki1003.xml");
		ttpYuki3.loadTexture();
		ttpLibYuki3 = ttpYuki3.getTexturePackTextureRegionLibrary();
		
		ttpYuki4 = mTexturePackLoaderFromSource.load("yuki1004.xml");
		ttpYuki4.loadTexture();
		ttpLibYuki4 = ttpYuki4.getTexturePackTextureRegionLibrary();
		
		ttpYuki5 = mTexturePackLoaderFromSource.load("yuki1005.xml");
		ttpYuki5.loadTexture();
		ttpLibYuki5 = ttpYuki5.getTexturePackTextureRegionLibrary();
		
		ttpYuki6 = mTexturePackLoaderFromSource.load("yuki1006.xml");
		ttpYuki6.loadTexture();
		ttpLibYuki6 = ttpYuki6.getTexturePackTextureRegionLibrary();
		
		ttpYuki7 = mTexturePackLoaderFromSource.load("yuki1007.xml");
		ttpYuki7.loadTexture();
		ttpLibYuki7 = ttpYuki7.getTexturePackTextureRegionLibrary();
		
		ttpYuki8 = mTexturePackLoaderFromSource.load("yuki1008.xml");
		ttpYuki8.loadTexture();
		ttpLibYuki8 = ttpYuki8.getTexturePackTextureRegionLibrary();
		
		ttpYuki9 = mTexturePackLoaderFromSource.load("yuki1009.xml");
		ttpYuki9.loadTexture();
		ttpLibYuki9 = ttpYuki9.getTexturePackTextureRegionLibrary();

		ttpYuki10 = mTexturePackLoaderFromSource.load("yuki10010.xml");
		ttpYuki10.loadTexture();
		ttpLibYuki10 = ttpYuki10.getTexturePackTextureRegionLibrary();

		ttpYuki11 = mTexturePackLoaderFromSource.load("yuki10011.xml");
		ttpYuki11.loadTexture();
		ttpLibYuki11 = ttpYuki11.getTexturePackTextureRegionLibrary();
		
		//Caste
		ttrCastle1 = ttpLibYuki1.get(Vol3Yuki100Resource.A6_1_1_IPHONE_CASTLE_ID);
		ttrCastle2 = ttpLibYuki1.get(Vol3Yuki100Resource.A6_1_2_IPHONE_CASTLE_ID);
		ttrCastle3 = ttpLibYuki1.get(Vol3Yuki100Resource.A6_1_3_IPHONE_CASTLE_ID);
		ttrCastle4 = ttpLibYuki2.get(Vol3Yuki100Resource.A6_1_4_IPHONE_CASTLE_ID);
		ttrCastle5 = ttpLibYuki2.get(Vol3Yuki100Resource.A6_1_5_IPHONE_CASTLE_ID);
		
		//Back ground fly
		ttrBackSky = ttpLibYuki3.get(Vol3Yuki100Resource.A6_2_1_IPHONE_SKY_ID);
		
		//Font Back
		ttrFontBack = ttpLibYuki8.get(Vol3Yuki100Resource.A6_6_1_IPHONE_BACK_ID);
		
		//Room
		ttrRoom = ttpLibYuki11.get(Vol3Yuki100Resource.A6_30_1_IPHONE_ROOM_ID);
		ttrRoomRight = ttpLibYuki10.get(Vol3Yuki100Resource.A6_30_2_IPHONE_ROOM_ID);
		ttrRoomLeft = ttpLibYuki11.get(Vol3Yuki100Resource.A6_30_3_IPHONE_ROOM_ID);
		ttrTenjoanalLeft = ttpLibYuki10.get(Vol3Yuki100Resource.A6_28_1_IPHONE_TENJOANAR_ID);
		ttrTenjoanalRight = ttpLibYuki10.get(Vol3Yuki100Resource.A6_29_1_IPHONE_TENJOANAL_ID);
		ttrHusumar = ttpLibYuki10.get(Vol3Yuki100Resource.A6_26_1_IPHONE_HUSUMAR_ID);
		ttrHusumal = ttpLibYuki10.get(Vol3Yuki100Resource.A6_27_1_IPHONE_HUSUMAL_ID);
		
		//Fushima Door
		ttrFusmaLeft = ttpLibYuki4.get(Vol3Yuki100Resource.A6_3_1_IPHONE_FUSMA_ID);
		ttrFusmaRight = ttpLibYuki4.get(Vol3Yuki100Resource.A6_3_2_IPHONE_FUSMA_ID);
		
		// Table
		ttrTable = ttpLibYuki10.get(Vol3Yuki100Resource.A6_24_1_IPHONE_TABLE2_ID);
		
		//Zabutton Left
		ttrZabuttonLeft1 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_24_2_IPHONE_ZABUTON2_ID);
		ttrZabuttonLeft2 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_22_1_IPHONE_ZABUTON_ID);
		
		//ZaButton Right
		ttrZabuttonRight1 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_24_3_IPHONE_ZABUTON2_ID);
		ttrZabuttonRight2 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_22_2_IPHONE_ZABUTON_ID);
		
		//Mouse
		ttrMouseLeft = ttpLibYuki10.get(Vol3Yuki100Resource.A6_23_2_IPHONE_MOUSE_ID);
		ttrMouseRight = ttpLibYuki10.get(Vol3Yuki100Resource.A6_23_1_IPHONE_MOUSE_ID);
		
		//Band
		ttrBandRight = ttpLibYuki9.get(Vol3Yuki100Resource.A6_8_1_IPHONE_BAND_ID);
		
		//Mono 25-8 , 25-7 , 25-5
		ttrMono255 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_17_1_IPHONE_MONO_ID); 
		ttrMono257 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_17_2_IPHONE_MONO_ID); 
		ttrMono258 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_17_3_IPHONE_MONO_ID); 
		
		//NinjaGray
		ttrNinjaGray = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_1_IPHONE_NINJAGRAY_ID);

		// Face Ninja
		ttrFNinjaYeloow = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_2_IPHONE_NINJACOLOR_ID);
		ttrFNinjaPink = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_3_IPHONE_NINJACOLOR_ID);
		ttrFNinjaLime = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_4_IPHONE_NINJACOLOR_ID);
		ttrFNinjaOrange = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_5_IPHONE_NINJACOLOR_ID);
		ttrFNinjaGreen = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_6_IPHONE_NINJACOLOR_ID);
		ttrFNinjaLightBlue = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_7_IPHONE_NINJACOLOR_ID);
		ttrFNinjaDarkBlue= ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_8_IPHONE_NINJACOLOR_ID);
		ttrFNinjaGray = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_9_IPHONE_NINJACOLOR_ID);
		ttrFNinjaViolet = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_10_IPHONE_NINJACOLOR_ID);
		ttrFNinjaBlack = ttpLibYuki8.get(Vol3Yuki100Resource.A6_7_11_IPHONE_NINJACOLOR_ID);
		
		//Hand 25_1 -> 25_16
		ttrHand251 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_1_IPHONE_HAND_ID);
		ttrHand252 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_2_IPHONE_HAND_ID);
		ttrHand253 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_3_IPHONE_HAND_ID);
		ttrHand254 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_4_IPHONE_HAND_ID);
		ttrHand255 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_5_IPHONE_HAND_ID);
		ttrHand256 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_6_IPHONE_HAND_ID);
		ttrHand257 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_7_IPHONE_HAND_ID);
		ttrHand258 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_8_IPHONE_HAND_ID);
		ttrHand259 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_9_IPHONE_HAND_ID);
		ttrHand2511 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_11_IPHONE_HAND_ID);
		ttrHand2512 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_12_IPHONE_HAND_ID);
		ttrHand2515 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_15_IPHONE_HAND_ID);
		ttrHand2516 = ttpLibYuki10.get(Vol3Yuki100Resource.A6_25_16_IPHONE_HAND_ID);
		
		//NinjaA
		for (int i = 0; i < tttrNinjaA.length; i++) {
			tttrNinjaA[i] = getTiledTextureFromPacker(ttpYuki10, Vol3Yuki100Resource.NinjaA[i]);
		}
	
		//NinjaB
		for (int i = 0; i < tttrNinjaB.length; i++) {
			tttrNinjaB[i] = getTiledTextureFromPacker(ttpYuki9, Vol3Yuki100Resource.NinjaB[i]);
		}
		//NinjaC
		for (int i = 0; i < tttrNinjaC.length; i++) {
			tttrNinjaC[i] = getTiledTextureFromPacker(ttpYuki10, Vol3Yuki100Resource.NinjaC[i]);
		}
		
		//NinjaD
		for (int i = 0; i < tttrNinjaD.length; i++) {
			tttrNinjaD[i] = getTiledTextureFromPacker(ttpYuki9, Vol3Yuki100Resource.NinjaD[i]);
		}	
		
		//NinjaF
		for (int i = 0; i < tttrNinjaF.length; i++) {
			tttrNinjaF[i] = getTiledTextureFromPacker(ttpYuki9, Vol3Yuki100Resource.NinjaF[i]);
		}	
		
		//Ninja G
		for (int i = 0; i < tttrNinjaG.length; i++) {
			tttrNinjaG[i] = getTiledTextureFromPacker(ttpYuki9, Vol3Yuki100Resource.NinjaG[i]);
		}
		
		//Princess
		ttrPrincess = ttpLibYuki9.get(Vol3Yuki100Resource.A6_13_1_IPHONE_PRINCESS_ID);
		
		//King
		ttrKing = ttpLibYuki9.get(Vol3Yuki100Resource.A6_9_1_IPHONE_KING_ID);
		
		//Teng
		ttrTeng181 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_18_1_IPHONE_TENG_ID);
		ttrTeng182 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_18_2_IPHONE_TENG_ID);
		ttrTeng183 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_18_3_IPHONE_TENG_ID);
		
		//Tatami
		ttrTatamiL = ttpLibYuki10.get(Vol3Yuki100Resource.A6_21_2_IPHONE_TATAMISHADOW_ID);
		ttrTatamiR = ttpLibYuki10.get(Vol3Yuki100Resource.A6_21_1_IPHONE_TATAMISHADOW_ID);
		ttrTatamiShaL = ttpLibYuki9.get(Vol3Yuki100Resource.A6_16_1_IPHONE_TATAMI_ID);
		ttrTatamiShaR = ttpLibYuki9.get(Vol3Yuki100Resource.A6_16_2_IPHONE_TATAMI_ID);
		
		//Table 2
		ttrTable2 = ttpLibYuki9.get(Vol3Yuki100Resource.A6_12_1_IPHONE_TABLE_ID);
		
		//Eye Ninja
		ttrEyeNinja = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_1_1_IPHONE_NINJAEYE_ID);
		
		//Face Finish
		ttrFaceYellow = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_4_IPHONE_NINJAFACE_ID);
		ttrFacePink = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_10_IPHONE_NINJAFACE_ID);
		ttrFaceLime = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_2_IPHONE_NINJAFACE_ID);
		ttrFaceOrange = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_1_2_IPHONE_NINJAFACE_ID);
		ttrFaceGreen = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_7_IPHONE_NINJAFACE_ID);
		ttrFaceLightBlue = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_3_IPHONE_NINJAFACE_ID);
		ttrFaceDarkBlue = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_8_IPHONE_NINJAFACE_ID);
		ttrFaceGray = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_5_IPHONE_NINJAFACE_ID);
		ttrFaceViolet = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_9_IPHONE_NINJAFACE_ID);
		ttrFaceBlack = ttpLibYuki8.get(Vol3Yuki100Resource.A6_5_6_IPHONE_NINJAFACE_ID);
		
		//Smoke
		ttrSmoke1 = ttpLibYuki5.get(Vol3Yuki100Resource.A6_4_1_IPHONE_SMOKE_ID);
		ttrSmoke2 = ttpLibYuki5.get(Vol3Yuki100Resource.A6_4_2_IPHONE_SMOKE_ID);
		ttrSmoke3 = ttpLibYuki6.get(Vol3Yuki100Resource.A6_4_3_IPHONE_SMOKE_ID);
		ttrSmoke4 = ttpLibYuki7.get(Vol3Yuki100Resource.A6_4_4_IPHONE_SMOKE_ID);
		
		//Gimmic
		ttrGimmic = ttpLibYuki10.get(Vol3Yuki100Resource.A6_YUKI100_3_IPHONE_ID);
		
		Log.d(TAG, "loadKaraokeResources: end");
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		OGG_A6_1_10SAGASE = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_1_10SAGASE);
		OGG_A6_7_10 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_10);
		OGG_A6_7_11 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_11);
		OGG_A6_7_1_1 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_1_1);
		OGG_A6_7_1_2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_1_2);
		OGG_A6_7_1_3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_1_3);
		OGG_A6_7_1_4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_1_4);
		OGG_A6_7_1_5 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_1_5);
		OGG_A6_7_2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_2);
		OGG_A6_7_3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_3);
		OGG_A6_7_4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_4);
		OGG_A6_7_5 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_5);
		OGG_A6_7_6 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_6);
		OGG_A6_7_7 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_7);
		OGG_A6_7_8 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_8);
		OGG_A6_7_9 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_7_9);
		OGG_A6_FUSUMA = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_FUSUMA);
		OGG_A6_HOHOHO = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_HOHOHO);
		OGG_A6_ICON_KAITEN = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_ICON_KAITEN);
		OGG_A6_KING_OHOHO = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_KING_OHOHO);
		OGG_A6_KING_TONODEOJARU = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_KING_TONODEOJARU);
		OGG_A6_NINJA_MITSUKATA = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATA);
		OGG_A6_NINJA_MITSUKATA2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATA2);
		OGG_A6_NINJA_MITSUKATA3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATA3);
		OGG_A6_NINJA_MITSUKATA4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATA4);
		OGG_A6_NINJA_MITSUKATEMOTA = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATEMOTA);
		OGG_A6_NINJA_MITSUKATEMOTA2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATEMOTA2);
		OGG_A6_NINJA_MITSUKATEMOTA3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATEMOTA3);
		OGG_A6_NINJA_MITSUKATEMOTA4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATEMOTA4);
		OGG_A6_NINJA_MITSUKATEMOTA5 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MITSUKATEMOTA5);
		OGG_A6_NINJA_MUMUNEN = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUMUNEN);
		OGG_A6_NINJA_MUNEN = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNEN);
		OGG_A6_NINJA_MUNENJA1 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNENJA1);
		OGG_A6_NINJA_MUNENJA2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNENJA2);
		OGG_A6_NINJA_MUNENJA3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNENJA3);
		OGG_A6_NINJA_MUNENJA4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNENJA4);
		OGG_A6_NINJA_MUNENJA5 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_MUNENJA5);
		OGG_A6_NINJA_SHIMATA = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA);
		OGG_A6_NINJA_SHIMATA2 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA2);
		OGG_A6_NINJA_SHIMATA3 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA3);
		OGG_A6_NINJA_SHIMATA4 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA4);
		OGG_A6_NINJA_SHIMATA5 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA5);
		OGG_A6_NINJA_SHIMATA6 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA6);
		OGG_A6_NINJA_SHIMATA7 = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SHIMATA7);
		OGG_A6_NINJA_SMOKE = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_SMOKE);
		OGG_A6_NINJA_TAUCH = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_NINJA_TAUCH);
		OGG_A6_QUEEN_HIMEJA = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_QUEEN_HIMEJA);
		OGG_A6_QUEEN_TIGAU = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_QUEEN_TIGAU);
		OGG_A6_ZABUTON = loadSoundResourceFromSD(Vol3Yuki100Resource.A6_ZABUTON);
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();
		//Font
		shape = new Rectangle(0, 0, 960, 640, getVertexBufferObjectManager());
		shape.setAlpha(1.0f);
		mScene.attachChild(shape);
		
		//Room
		sRoom = new Sprite(-21, -69, ttrRoom, this.getVertexBufferObjectManager());
		mScene.attachChild(sRoom);
		sRoom.setVisible(false);
		
		sTenjoanalLeft = new Sprite(227, -69, ttrTenjoanalLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sTenjoanalLeft);
		sTenjoanalLeft.setVisible(false);
		
		sTenjoanalRight = new Sprite(227, -69, ttrTenjoanalRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sTenjoanalRight);
		sTenjoanalRight.setVisible(false);
		
		sTatamiR = new Sprite(378, 437, ttrTatamiR, this.getVertexBufferObjectManager());
		mScene.attachChild(sTatamiR);
		sTatamiR.setVisible(false);
		
		sMouseLeft = new Sprite(445, 500, ttrMouseLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sMouseLeft);
		sMouseLeft.setVisible(false);
		
		sHusumar = new Sprite(688, 179, ttrHusumar, this.getVertexBufferObjectManager());
		mScene.attachChild(sHusumar);
		sHusumar.setVisible(false);
		
		sHusumal = new Sprite(555, 181, ttrHusumal, this.getVertexBufferObjectManager());
		mScene.attachChild(sHusumal);
		sHusumal.setVisible(false);
		
		//NinjaA
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i] = new Sprite(-999,-999, tttrNinjaA[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaA[i]);
			sNinjaA[i].setVisible(false);
		}
		
		//Ninja C
		
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i] = new Sprite(-999, -999, tttrNinjaC[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaC[i]);
			sNinjaC[i].setVisible(false);
		}
		
		//Zabutton
		sZabuttonLeft1 = new Sprite(-999, -999, ttrZabuttonLeft1, this.getVertexBufferObjectManager());
		mScene.attachChild(sZabuttonLeft1);
		
		sZabuttonLeft2 = new Sprite(498, 483, ttrZabuttonLeft2, this.getVertexBufferObjectManager());
		mScene.attachChild(sZabuttonLeft2);
		sZabuttonLeft2.setVisible(false);
		
		sHand256 = new Sprite(-999, -999, ttrHand256, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand256);
		
		//Teng
		sTeng181 = new Sprite(571, 24, ttrTeng181, this.getVertexBufferObjectManager());
		mScene.attachChild(sTeng181);
		sTeng181.setVisible(false);

		sRoomLeft = new Sprite(-21, -69, ttrRoomLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sRoomLeft);
		sRoomLeft.setVisible(false);
		
		//Ninja Gray
		for (int i=0; i < sNinjaGray.length; i++){
			sNinjaGray[i] = new Sprite(-999, -999, ttrNinjaGray, 
					this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaGray[i]);
			sNinjaGray[i].setVisible(false);
		}
		
		//Ninja Face
		sFNinjaYeloow = new Sprite(-999, -999, ttrFNinjaYeloow, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaYeloow);
		
		sFNinjaPink = new Sprite(-999, -999, ttrFNinjaPink, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaPink);
		
		sFNinjaLime = new Sprite(-999, -999, ttrFNinjaLime, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaLime);
		
		sFNinjaOrange = new Sprite(-999, -999, ttrFNinjaOrange, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaOrange);
		
		sFNinjaGreen = new Sprite(-999, -999, ttrFNinjaGreen, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaGreen);
		
		sFNinjaLightBlue = new Sprite(-999, -999, ttrFNinjaLightBlue, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaLightBlue);
		
		sFNinjaDarkBlue = new Sprite(-999, -999, ttrFNinjaDarkBlue, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaDarkBlue);
		
		sFNinjaGray = new Sprite(-999, -999, ttrFNinjaGray, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaGray);
		
		sFNinjaViolet= new Sprite(-999, -999, ttrFNinjaViolet, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaViolet);
		
		sFNinjaBlack = new Sprite(-999, -999, ttrFNinjaBlack, this.getVertexBufferObjectManager());
		mScene.attachChild(sFNinjaBlack);

		//Hand 25_1 -> 25_16
		sRoomRight = new Sprite(823, 102, ttrRoomRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sRoomRight);
		sRoomRight.setVisible(false);
		
		sHand251 = new Sprite(-999, -999, ttrHand251, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand251);
		
		sHand252 = new Sprite(-999, -999, ttrHand252, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand252);
		
		sHand253 = new Sprite(-999, -999, ttrHand253, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand253);
		
		sHand254 = new Sprite(-999, -999, ttrHand254, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand254);
		
		sHand255 = new Sprite(-999, -999, ttrHand255, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand255);
		
		sHand257 = new Sprite(-999, -999, ttrHand257, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand257);
		
		sHand258 = new Sprite(-999, -999, ttrHand258, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand258);
		
		sHand259 = new Sprite(-999, -999, ttrHand259, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand259);
		
		sHand2511 = new Sprite(-999, -999, ttrHand2511, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand2511);
		
		sHand2512 = new Sprite(-999, -999, ttrHand2512, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand2512);
		
		sHand2515 = new Sprite(-999,-999, ttrHand2515, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand2515);
		
		sHand2516 = new Sprite(-999,-999, ttrHand2516, this.getVertexBufferObjectManager());
		mScene.attachChild(sHand2516);
		
		//Mouse
		sMouseRight = new Sprite(762, 500, ttrMouseRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sMouseRight);
		sMouseRight.setVisible(false);

		sZabuttonRight1 = new Sprite(-999, -999, ttrZabuttonRight1, this.getVertexBufferObjectManager());
		mScene.attachChild(sZabuttonRight1);
		
		sZabuttonRight2 = new Sprite(765, 484, ttrZabuttonRight2, this.getVertexBufferObjectManager());
		mScene.attachChild(sZabuttonRight2);
		sZabuttonRight2.setVisible(false);
		
		//Table
		sTable = new Sprite(582, 430, ttrTable, this.getVertexBufferObjectManager());
		mScene.attachChild(sTable);
		sTable.setVisible(false);
		
		//Band
		sBandRight = new Sprite(219, -61, ttrBandRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sBandRight);
		sBandRight.setVisible(false);
		
		//Mono
		sMono255 = new Sprite(713, 359, ttrMono255, this.getVertexBufferObjectManager());
		mScene.attachChild(sMono255);
		sMono255.setVisible(false);
		
		sMono257 = new Sprite(570, 278, ttrMono257, this.getVertexBufferObjectManager());
		mScene.attachChild(sMono257);
		sMono257.setVisible(false);
		
		sTeng182 = new Sprite(571, 24, ttrTeng182, this.getVertexBufferObjectManager());
		mScene.attachChild(sTeng182);
		sTeng182.setVisible(false);
		
		sMono258 = new Sprite(335, 144, ttrMono258, this.getVertexBufferObjectManager());
		mScene.attachChild(sMono258);
		sMono258.setVisible(false);
		
		sTeng183 = new Sprite(571, 24, ttrTeng183, this.getVertexBufferObjectManager());
		mScene.attachChild(sTeng183);
		sTeng183.setVisible(false);
		
		sTatamiShaR = new Sprite(357, 395, ttrTatamiShaR, this.getVertexBufferObjectManager());
		mScene.attachChild(sTatamiShaR);
		sTatamiShaR.setVisible(false);
		
		//Ninja B
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i] = new Sprite(-999, -999, tttrNinjaB[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaB[i]);
			sNinjaB[i].setVisible(false);
		}
		
		//Ninja F
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i] = new Sprite(-999, -999, tttrNinjaF[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaF[i]);
			sNinjaF[i].setVisible(false);
		}
		
		//King
		sKing = new Sprite(444, 96, ttrKing, this.getVertexBufferObjectManager());
		mScene.attachChild(sKing);
		sKing.setVisible(false);
		
		sTatamiL = new Sprite(232, 435, ttrTatamiL, this.getVertexBufferObjectManager());
		mScene.attachChild(sTatamiL);
		sTatamiL.setVisible(false);
		
		sTatamiShaL = new Sprite(234, 283, ttrTatamiShaL, this.getVertexBufferObjectManager());
		mScene.attachChild(sTatamiShaL);
		sTatamiShaL.setVisible(false);

		//Ninja D
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i] = new Sprite(-999, -999, tttrNinjaD[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaD[i]);
			sNinjaD[i].setVisible(false);
		}
		
		//Ninja G
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i] = new Sprite(-999, -999, tttrNinjaG[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sNinjaG[i]);
			sNinjaG[i].setVisible(false);
		}
		
		//Princess
		sPrincess = new Sprite(539, 233, ttrPrincess, this.getVertexBufferObjectManager());
		mScene.attachChild(sPrincess);
		sPrincess.setVisible(false);
		
		sTable2 = new Sprite(573, 375, ttrTable2, this.getVertexBufferObjectManager());
		mScene.attachChild(sTable2);
		sTable2.setVisible(false);
		
		//Eye
		for (int i = 0; i < sEye.length; i++) {
			sEye[i] = new Sprite(0, 0, ttrEyeNinja, this.getVertexBufferObjectManager());
		}
		
		//Gimmic
		sGimmic = new Sprite(612, 496, ttrGimmic, this.getVertexBufferObjectManager());
		mScene.attachChild(sGimmic);
		sGimmic.setVisible(false);
		
		//Fusma Door
		sFusmaLeft = new Sprite(-999, -999, ttrFusmaLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sFusmaLeft);
		
		sFusmaRight = new Sprite(-999, -999, ttrFusmaRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sFusmaRight);
		
		sFontBack = new Sprite(-7, -64, ttrFontBack, this.getVertexBufferObjectManager());
		mScene.attachChild(sFontBack);
		sFontBack.setVisible(false);
		
		//shapeBackGround
		shapeBackGround = new Rectangle(0, 0, 960, 640, this.getVertexBufferObjectManager());
		mScene.attachChild(shapeBackGround);
		shapeBackGround.setAlpha(0.0f);
		shapeBackGround.setVisible(false);
		
		//Face Finish
		sYellow = new Sprite(106, 256, ttrFaceYellow, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sYellow);
		sYellow.attachChild(sEye[0]);
		
		sPink = new Sprite(400, -23, ttrFacePink, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sPink);
		sPink.attachChild(sEye[1]);
		
		sLime = new Sprite(226, 439, ttrFaceLime, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sLime);
		sLime.attachChild(sEye[2]);
		
		sOrange = new Sprite(195, 54, ttrFaceOrange, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sOrange);
		sOrange.attachChild(sEye[3]);
		
		sGreen = new Sprite(470, 454, ttrFaceGreen, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sGreen);
		sGreen.attachChild(sEye[4]);
		
		sLightBlue = new Sprite(389, 136, ttrFaceLightBlue, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sLightBlue);
		sLightBlue.attachChild(sEye[5]);
		
		sDarkBlue = new Sprite(642, 306, ttrFaceDarkBlue, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sDarkBlue);
		sDarkBlue.attachChild(sEye[6]);
		
		sGray = new Sprite(300, 280, ttrFaceGray, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sGray);
		sGray.attachChild(sEye[7]);
		
		sViolet = new Sprite(618, 82, ttrFaceViolet, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sViolet);
		sViolet.attachChild(sEye[8]);
		
		sBlack = new Sprite(478, 278, ttrFaceBlack, this.getVertexBufferObjectManager());
		shapeBackGround.attachChild(sBlack);
		sBlack.attachChild(sEye[9]);
		
		//Smoke
		sSmoke1 = new Sprite(-22, -116, ttrSmoke1, this.getVertexBufferObjectManager());
		mScene.attachChild(sSmoke1);
		sSmoke1.setVisible(false);
		
		sSmoke2 = new Sprite(-22, -116, ttrSmoke2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSmoke2);
		sSmoke2.setVisible(false);
		
		sSmoke3 = new Sprite(-43, -116, ttrSmoke3, this.getVertexBufferObjectManager());
		mScene.attachChild(sSmoke3);
		sSmoke3.setVisible(false);
		
		sSmoke4 = new Sprite(-16, -131, ttrSmoke4, this.getVertexBufferObjectManager());
		mScene.attachChild(sSmoke4);
		sSmoke4.setVisible(false);
		
		//Sky
		sBackSky = new Sprite(-221, -105, ttrBackSky, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackSky);
		
		//Castle
		sCastle1 = new Sprite(0, 0, ttrCastle1, this.getVertexBufferObjectManager());
		mScene.attachChild(sCastle1);
		sCastle1.setVisible(false);
		
		sCastle2 = new Sprite(0, 0, ttrCastle2, this.getVertexBufferObjectManager());
		mScene.attachChild(sCastle2);
		sCastle2.setVisible(false);
		
		sCastle3 = new Sprite(0, 0, ttrCastle3, this.getVertexBufferObjectManager());
		mScene.attachChild(sCastle3);
		sCastle3.setVisible(false);
		
		sCastle4 = new Sprite(0, 0, ttrCastle4, this.getVertexBufferObjectManager());
		mScene.attachChild(sCastle4);
		sCastle4.setVisible(false);
		
		sCastle5 = new Sprite(0, 0, ttrCastle5, this.getVertexBufferObjectManager());
		mScene.attachChild(sCastle5);
		sCastle5.setVisible(false);
		
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene: end");
	}
	
	@Override
	public void combineGimmic3WithAction() {
		sGimmic.registerEntityModifier(new SequenceEntityModifier(
			new ScaleModifier(0.35f, 1, 1.3f),
			new ScaleModifier(0.35f, 1.3f, 1f)));
		isTouchMouseLeft = false;
		isTouchMouseRight = false;
		touchMouseLeft();
		touchMouseRight();
		}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		sFontBack.setVisible(false);
		isCatchNinja = false;
		Log.d(TAG, "1");
		backGroundSky();
		//castleZoom1();
		Log.d(TAG, "8");
		rTeng = 0;
		rKing = 0;
		rPrincess =0;
		super.onResumeGame();
		
	}
	
	public void initialRoom(){
		initial();
		listNinja();
		setFalseFaceNinja(false, sFNinjaYeloow,sFNinjaPink,sFNinjaLime,sFNinjaOrange,sFNinjaGreen,
				sFNinjaLightBlue,sFNinjaDarkBlue,sFNinjaGray,sFNinjaViolet,sFNinjaBlack);
		ranTeng = new Random();
		rTeng = ranTeng.nextInt(4);
		
		ranKing = new Random();
		rKing = ranKing.nextInt(5);
		
		ranPrincess = new Random();
		rPrincess = ranPrincess.nextInt(3);
		
		rCaseAC = 0;
		rCaseBF = 0;
		rCaseDG = 0;
		currentPosition = 0;
		sTable.setVisible(true);
		sBandRight.setVisible(true);
		sZabuttonLeft1.setPosition(498, 483);
		sZabuttonRight1.setPosition(765, 484);
		sMono255.setVisible(true);
		sMono257.setVisible(true);
		sMono258.setVisible(true);
		
		for(int i=0; i < sNinjaGray.length; i++){
			sNinjaGray[i].setVisible(true);
			sNinjaGray[i].setPosition(pointNinja[0][i], pointNinja[1][i]);
		}
		
		sRoomRight.setVisible(true);
		sRoomLeft.setVisible(true);
		sTenjoanalLeft.setVisible(false);
		sTenjoanalRight.setVisible(false);
		
		sHusumal.setVisible(false);
		sHusumar.setVisible(false);
		
		sHand251.setPosition(817, 201);
		sHand252.setPosition(686, 505);
		sHand253.setPosition(225, 401);
		sHand254.setPosition(433, 382);
		sHand255.setPosition(716, 321);
		sHand256.setPosition(484, 210);
		sHand257.setPosition(554, 221);
		sHand258.setPosition(390, 197);
		sHand259.setPosition(659, 184);
		sHand2511.setPosition(536, 161);
		sHand2512.setPosition(434, 115);
		sHand2515.setPosition(648, 24);
		sHand2516.setPosition(296, 23);
		
		sGimmic.setVisible(true);
	}	
	public void initial(){
		isTouchMouseLeft = true;
		isTouchMouseRight = true;
		isTouchFace = true;
		isTouchNinja = false;
		isTouchHand251 = true;
		isTouchHand254 = true;
		isTouchHand255 = true;
		isTouchHand257 = true;
		isTouchHand258 = true;
		isTouchHand256 = true;
		isTouchHand259 = true;
		isTouchHand2511 = true;
		isTouchHand2515 = true;
		isTouchHand2516 = true;
		isTouchTable = true;
		isTouchHand252 = true;
		isTouchHand253 = true;
		isTouchHand2512 = true;
	}

	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		Log.d("++++++++++++++++++++++++++++++++++", "Dung hay sai");
		mp3Stop();
		reset();
		super.onPauseGame();
	}
	
	private void backGroundSky(){
		Log.d(TAG, "2");
		sBackSky.setVisible(true);
		sBackSky.setPosition(-221, -105);
		sBackSky.registerEntityModifier(new MoveXModifier(7.5f, -221, -500));
		mScene.unregisterUpdateHandler(rtimeSky);
		rtimeSky = new TimerHandler(2.4f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
	
				castleZoom1();
			}
		});
		mScene.registerUpdateHandler(rtimeSky);
	}
	
	public void castleZoom1(){
		sCastle1.setVisible(true);
		mScene.unregisterUpdateHandler(rtime1);
		//1.1
		rtime1 = new TimerHandler(0.8f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sCastle1.setVisible(false);
				castleZoom2();
			}
		});
		mScene.registerUpdateHandler(rtime1);
	}
	//
	public void castleZoom2(){
		sCastle2.setVisible(true);
		mScene.unregisterUpdateHandler(rtime2);
		//1.1
		rtime2 = new TimerHandler(0.8f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sCastle2.setVisible(false);
				castleZoom3();
			}
		});
		mScene.registerUpdateHandler(rtime2);
	}
	
	public void castleZoom3(){
		sCastle3.setVisible(true);
		mScene.unregisterUpdateHandler(rtime3);
		rtime3 = new TimerHandler(0.7f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sCastle3.setVisible(false);
				castleZoom4();
			}
		});
		mScene.registerUpdateHandler(rtime3);
	}
	
	public void castleZoom4(){
		sCastle4.setVisible(true);
		mScene.unregisterUpdateHandler(rtime4);
		rtime4 = new TimerHandler(1.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sCastle4.setVisible(false);
				OGG_A6_1_10SAGASE.play();
				castleZoom5();
			}
		});
		mScene.registerUpdateHandler(rtime4);
	}
	
	public void castleZoom5(){
		sCastle5.setVisible(true);
		mScene.unregisterUpdateHandler(rtime5);
		rtime5 = new TimerHandler(2.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sCastle5.setVisible(false);
				sBackSky.setVisible(false);
				sFusmaLeft.setPosition(-121, -66);
				sFusmaRight.setPosition(470, -63);
				
				sRoom.setVisible(true);
				displayDoorLeft(sFusmaLeft);
				displayDoorRight(sFusmaRight);
			}
		});
		mScene.registerUpdateHandler(rtime5);
	}
	
	public void displayDoorLeft(Sprite dDoorL){
		dDoorL.registerEntityModifier(new SequenceEntityModifier(
			new DelayModifier(1.0f),
			new MoveXModifier(0.8f, -121, -687 , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					OGG_A6_FUSUMA.play();
					initialRoom();
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					isCatchNinja = true;
				}
			})));
	}
	
	public void displayDoorRight(Sprite dDoorR){
		dDoorR.registerEntityModifier(new SequenceEntityModifier(
			new DelayModifier(1.0f),
			new MoveXModifier(0.8f, 471, 960 )));		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		//Action Down
		if(pSceneTouchEvent.isActionDown()){
			
			//Mouse
			if(checkContains(sZabuttonLeft1, 27, 27, 118, 69, pX, pY) && isTouchMouseLeft){
				isTouchMouseLeft = false;
				touchMouseLeft();
			}
			
			if(checkContains(sZabuttonRight1, 27, 27, 118, 69, pX, pY) && isTouchMouseRight){
				isTouchMouseRight = false;
				touchMouseRight();
			}
			
			if(checkContains(sHand251, 15, 20, 60, 70, pX, pY) && isTouchHand251 && isCatchNinja){
				isTouchHand251 = false;
				isCatchNinja = false;
				sHand251.setPosition(-999, -999);
				hand251();
				Log.d(TAG, "Hand 25_1");
				case251();
			}
			
			if(sHand254.contains(pX, pY) && isTouchHand254 && isCatchNinja){
				sTatamiR.setVisible(true);
				sTatamiShaR.setVisible(true);
				isCatchNinja = false;
				sHand254.setVisible(false);
				if(rTeng == 0){
					OGG_A6_HOHOHO.play();
					isTouchHand254 = false;
					sTeng183.setVisible(true);
					sTeng183.setPosition(370, 335);
					sTeng183.registerEntityModifier(deTeng183 = new DelayModifier(1.5f, this));
					deTeng183.addModifierListener(this);
				} else {
					hand254();
					Log.d(TAG, "Hand 25_4");
					case254();
				}
			}
		
			if(sHand255.contains(pX, pY) && isTouchHand255 && isCatchNinja){
				isTouchHand255 = false;
				isCatchNinja = false;
				if(rTeng == 1){
					OGG_A6_HOHOHO.play();
					sHand255.setVisible(false);
					sHand259.setVisible(false);
					sRoomRight.setVisible(false);
					sTeng181.setVisible(true);
					sTeng181.setPosition(570, 20);
					sTeng181.registerEntityModifier(deTeng181 = new DelayModifier(1.5f, this));
					deTeng181.addModifierListener(this);
				}else {
					hand255();
					Log.d(TAG, "Hand 25_5");
					case255();
				}
				
			}
			
			if(checkContains(sHand257, 19, 27, 90, 90, pX, pY) && isTouchHand257 && isCatchNinja){
				isTouchHand257 = false;
				isCatchNinja = false;
				if(rTeng == 2){
					OGG_A6_HOHOHO.play();
					sHand257.setVisible(false);
					sHand259.setVisible(false);
					sHand2511.setVisible(false);
					sTeng181.setVisible(true);
					sTeng181.setPosition(480, 85);
					sTeng181.registerEntityModifier(deTeng181 = new DelayModifier(1.5f, this));
					deTeng181.addModifierListener(this);
				}else {
					hand257();
					Log.d(TAG, "Hand 25_7");
					case257();
				}
				
			}
			
			if(checkContains(sHand258, 17, 17, 66, 85, pX, pY) && isTouchHand258 && isCatchNinja){
				isTouchHand258 = false;
				isCatchNinja = false;
				if(rTeng == 3){
					OGG_A6_HOHOHO.play();
					sHand258.setVisible(false);
					sTeng182.setVisible(true);
					sTeng182.setPosition(370, 60);
					sTeng182.registerEntityModifier(deTeng182 = new DelayModifier(1.5f, this));
					deTeng182.addModifierListener(this);
				}else {
					hand258();
					Log.d(TAG, "Hand 25_8");
					case258();
				}
			}
			
			if(checkContains(sHand256, 18, 16, 69, 96, pX, pY) && isCatchNinja && isTouchHand256){
				isTouchHand256 = false;
				isCatchNinja = false;
				if(rKing == 0){
					rSeKing();
					sHand256.setVisible(false);
					sKing.setVisible(true);
					sKing.setPosition(348, 145);
					sKing.registerEntityModifier(deKing = new DelayModifier(1.5f ,this));
					deKing.addModifierListener(this);
				}else {
					hand256();
					Log.d(TAG, "Hand 25_6");
					case256();
				}
			}
			
			if(checkContains(sHand259, 21, 4, 84, 86, pX, pY) && isCatchNinja && isTouchHand259){
				isTouchHand259 = false;
				isCatchNinja = false;
				if(rKing == 1){
					rSeKing();
					sHand259.setVisible(false);
					sKing.setVisible(true);
					sKing.setPosition(585, 90);
					sKing.registerEntityModifier(deKing = new DelayModifier(1.5f ,this));
					deKing.addModifierListener(this);
				}else {
					sHusumar.setVisible(true);
					hand259();
					Log.d(TAG, "Hand 25_9");
					case259();
				}
			}
			
			if(checkContains(sHand2511, 16, 0, 77, 62, pX, pY) && isCatchNinja && isTouchHand2511){
				isTouchHand2511 = false;
				isCatchNinja = false;
				if(rKing == 2){
					rSeKing();
					sHand2511.setVisible(false);
					sKing.setVisible(true);
					sKing.setPosition(445, 90);
					sKing.registerEntityModifier(deKing = new DelayModifier(1.5f ,this));
					deKing.addModifierListener(this);
				}else {
					sHusumal.setVisible(true);
					hand2511();
					Log.d(TAG, "Hand 25_11");
					case2511();
				}
			}
			
			if(sHand2515.contains(pX, pY) && isCatchNinja && isTouchHand2515){
				isTouchHand2515 = false;
				isCatchNinja = false;
				if(rKing == 3){
					rSeKing();
					sHand2515.setVisible(false);
					sKing.setVisible(true);
					sKing.setPosition(522, -50);
					sKing.registerEntityModifier(deKing = new DelayModifier(1.5f ,this));
					deKing.addModifierListener(this);
				}else {
					sTenjoanalLeft.setVisible(true);
					hand2515();
					Log.d(TAG, "Hand 25_15");
					case2515();	
				}
			}
			
			if(sHand2516.contains(pX, pY) && isCatchNinja && isTouchHand2516){
				isTouchHand2516 = false;
				isCatchNinja = false;
				if(rKing == 4){
					rSeKing();
					sHand2516.setVisible(false);
					sKing.setVisible(true);
					sKing.setPosition(232, -50);
					sKing.registerEntityModifier(deKing = new DelayModifier(1.5f ,this));
					deKing.addModifierListener(this);
				}else {
					sTenjoanalRight.setVisible(true);
					hand2516();
					Log.d(TAG, "Hand 25_16");
					case2516();
				}
			}
			
			if(sHand252.contains(pX, pY) && isCatchNinja && isTouchHand252){
				isTouchHand252 = false;
				isCatchNinja = false;
				if(rPrincess == 0){
					rSePrincess();
					sHand252.setVisible(false);
					sPrincess.setVisible(true);
					
					if(isTouchTable){
						touchTable();
					}else Log.d(TAG, "Nothing");
					
					sPrincess.setPosition(545, 285);
					sPrincess.registerEntityModifier(dePrincess = new DelayModifier(1.5f, this));
					dePrincess.addModifierListener(this);
				}else {
					hand252();
					Log.d(TAG, "Hand 25_2");
					case252();
					if(isTouchTable){
						touchTable();
					}else Log.d(TAG, "Nothing");
				}
			}
			
			if(sHand253.contains(pX, pY) && isCatchNinja && isTouchHand253){
				isTouchHand253 = false;
				isCatchNinja = false;
				sTatamiL.setVisible(true);
				sTatamiShaL.setVisible(true);
				if(rPrincess == 1){
					rSePrincess();
					sHand253.setVisible(false);
					sPrincess.setVisible(true);
					sPrincess.setPosition(215, 295);
					sPrincess.registerEntityModifier(dePrincess = new DelayModifier(1.5f, this));
					dePrincess.addModifierListener(this);
				}else {
					hand253();
					Log.d(TAG, "Hand 25_3");
					case253();
				}
			}
			
			if(sHand2512.contains(pX, pY) && isCatchNinja && isTouchHand2512){
				isTouchHand2512 = false;
				isCatchNinja = false;
				if(rPrincess == 2){
					rSePrincess();
					sHand2512.setVisible(false);
					sPrincess.setVisible(true);
					sPrincess.setPosition(310, 105);
					sPrincess.registerEntityModifier(dePrincess = new DelayModifier(1.5f, this));
					dePrincess.addModifierListener(this);
				}else{
					hand2512();
					Log.d(TAG, "Hand 25_12");
					case2512();
				}
			}
			
			if(sFNinjaYeloow.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_2.play();
				sFNinjaYeloow.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaPink.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_3.play();
				sFNinjaPink.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaLime.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_4.play();
				sFNinjaLime.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaOrange.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_5.play();
				sFNinjaOrange.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaGreen.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_6.play();
				sFNinjaGreen.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaLightBlue.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_7.play();
				sFNinjaLightBlue.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaDarkBlue.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_8.play();
				sFNinjaDarkBlue.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaGray.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_9.play();
				sFNinjaGray.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaViolet.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_10.play();
				sFNinjaViolet.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			if(sFNinjaBlack.contains(pX, pY) && isTouchFace){
				isTouchFace = false;
				OGG_A6_7_11.play();
				sFNinjaBlack.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchFace = true;
					}
				}));
			}
			
			for (int i = 0; i < sNinjaGray.length; i++) {
				if(sNinjaGray[i].contains(pX, pY) && isTouchFace){
					isTouchFace = false;
					rSeGrayOut();
					sNinjaGray[i].registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchFace = true;
						}
					}));
				}
			}

			for (int i = 0; i < sNinjaA.length; i++) {
				if(sNinjaA[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			for (int i = 0; i < sNinjaC.length; i++) {
				if(sNinjaC[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			for (int i = 0; i < sNinjaB.length; i++) {
				if(sNinjaB[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			for (int i = 0; i < sNinjaF.length; i++) {
				if(sNinjaF[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			for (int i = 0; i < sNinjaD.length; i++) {
				if(sNinjaD[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			for (int i = 0; i < sNinjaG.length; i++) {
				if(sNinjaG[i].contains(pX, pY) && isTouchNinja) {
					isTouchNinja = false;
					OGG_A6_NINJA_TAUCH.play();
				}
			}
			
			if(sGimmic.contains(pX, pY) && isTouchMouseLeft && isTouchMouseRight){
				combineGimmic3WithAction();
			}
			
		}
		return false;
	}
	public void setHorizontalACtrue(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setFlippedHorizontal(true);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setFlippedHorizontal(true);
		}
	}
	
	public void setHorizontalACfalse(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setFlippedHorizontal(false);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setFlippedHorizontal(false);
		}
	}
	
	public void hand251(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(650, 130);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(689, 130);
		}
		setHorizontalACtrue();
	}
	
	public void hand254(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(370, 420);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(360, 420);
		}
		setHorizontalACfalse();
	}
	
	public void hand255(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(695, 205);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(695, 160);
		}
		setHorizontalACfalse();
	}
	
	public void hand257(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(575, 130);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(555, 125);
		}
		setHorizontalACfalse();
	}
	
	public void hand258(){
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(365, 105);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(335, 105);
		}
		setHorizontalACfalse();
	}
	
	public void hand256(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(345,165);
			sNinjaB[i].setRotation(90);
			sNinjaB[i].setFlippedHorizontal(true);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(305, 165);
			sNinjaF[i].setRotation(90);
		}
	}
	
	public void hand259(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(610,158);
			sNinjaB[i].setRotation(0);
			sNinjaB[i].setFlippedHorizontal(false);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(575, 158);
			sNinjaF[i].setRotation(0);
		}
	}
	
	public void hand2511(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(480,158);
			sNinjaB[i].setRotation(0);
			sNinjaB[i].setFlippedHorizontal(false);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(445, 158);
			sNinjaF[i].setRotation(0);
		}
	}
	
	public void hand2515(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(540,20);
			sNinjaB[i].setRotation(0);
			sNinjaB[i].setFlippedHorizontal(false);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(510, 20);
			sNinjaF[i].setRotation(0);
		}
	}
	
	public void hand2516(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(242,20);
			sNinjaB[i].setRotation(0);
			sNinjaB[i].setFlippedHorizontal(false);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(215, 20);
			sNinjaF[i].setRotation(0);
		}
	}
	
	public void hand252(){
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i].setPosition(564, 315);
		}
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i].setPosition(568, 315);
		}
	}
	
	public void hand253(){
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i].setPosition(240, 350);
		}
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i].setPosition(240, 350);
		}
	}
	
	public void hand2512(){
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i].setPosition(360, -17);
		}
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i].setPosition(364, -17);
		}
	}
	
	public void case251(){
		rRandomCase = new Random();
		rCaseAC = rRandomCase.nextInt(2);
		checkDisplayAC();
		switch (rCaseAC) {
		case 0: 
			randomNinjaA();
			break;
		case 1: 
			randomNinjaC();
			break;
		default:
			break;
		}
	}
	
	public void case254(){
		rRandomCase = new Random();
		rCaseAC = rRandomCase.nextInt(2);
		checkDisplayAC();
		switch (rCaseAC) {
		case 0: 
			randomNinjaA();
			sHand254.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaC();
			sHand254.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case255(){
		rRandomCase = new Random();
		rCaseAC = rRandomCase.nextInt(2);
		checkDisplayAC();
		switch (rCaseAC) {
		case 0: 
			sRoomRight.setVisible(false);
			randomNinjaA();
			sHand255.setPosition(-999, -999);
			break;
			
		case 1: 
			sRoomRight.setVisible(false);
			sHand251.setVisible(false);
			randomNinjaC();
			sHand255.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case257(){
		rRandomCase = new Random();
		rCaseAC = rRandomCase.nextInt(2);
		checkDisplayAC();
		switch (rCaseAC) {
		case 0: 
			sHand259.setVisible(false);
			randomNinjaA();
			sHand257.setPosition(-999, -999);
			break;
			
		case 1: 
			sHand259.setVisible(false);
			randomNinjaC();
			sHand257.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case258(){
		rRandomCase = new Random();
		rCaseAC = rRandomCase.nextInt(2);
		checkDisplayAC();
		switch (rCaseAC) {
		case 0: 
			sHand2512.setVisible(false);
			sHand256.setVisible(false);
			randomNinjaA();
			sHand258.setPosition(-999, -999);
			break;
			
		case 1: 
			sHand2512.setVisible(false);
			randomNinjaC();
			sHand258.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case256(){
		rRandomCase = new Random();
		rCaseBF = rRandomCase.nextInt(2);
		checkDisplayBF();
		switch (rCaseBF) {
		case 0: 
			randomNinjaB();
			sHand256.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaF();
			sHand256.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case259(){
		rRandomCase = new Random();
		rCaseBF = rRandomCase.nextInt(2);
		checkDisplayBF();
		switch (rCaseBF) {
		case 0: 
			randomNinjaB();
			sHand259.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaF();
			sHand259.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case2511(){
		rRandomCase = new Random();
		rCaseBF = rRandomCase.nextInt(2);
		checkDisplayBF();
		switch (rCaseBF) {
		case 0: 
			randomNinjaB();
			sHand2511.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaF();
			sHand2511.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case2515(){
		rRandomCase = new Random();
		rCaseBF = rRandomCase.nextInt(2);
		checkDisplayBF();
		switch (rCaseBF) {
		case 0: 
			randomNinjaB();
			sHand2515.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaF();
			sHand2515.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case2516(){
		rRandomCase = new Random();
		rCaseBF = rRandomCase.nextInt(2);
		checkDisplayBF();
		switch (rCaseBF) {
		case 0: 
			randomNinjaB();
			sHand2516.setPosition(-999, -999);
			break;
			
		case 1: 
			randomNinjaF();
			sHand2516.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case252(){
		rRandomCase = new Random();
		rCaseDG = rRandomCase.nextInt(1);
		checkDisplayDG();
		switch (rCaseDG) {
		case 0: 
			randomNinjaG();
			sHand252.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case253(){
		rRandomCase = new Random();
		rCaseDG = rRandomCase.nextInt(2);
		checkDisplayDG();
		switch (rCaseDG) {
		case 0: 
			randomNinjaG();
			sHand253.setPosition(-999, -999);
			break;
		case 1:
			randomNinjaD();
			sHand253.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}
	
	public void case2512(){
		rRandomCase = new Random();
		rCaseDG = rRandomCase.nextInt(2);
		checkDisplayDG();
		switch (rCaseDG) {
		case 0: 
			randomNinjaG();
			sHand2512.setPosition(-999, -999);
			break;
		case 1:
			randomNinjaD();
			sHand2512.setPosition(-999, -999);
			break;
		default:
			break;
		}
	}

	public void touchMouseLeft(){
		OGG_A6_ZABUTON.play();
		sZabuttonLeft1.setPosition(-999, -999);
		sZabuttonLeft2.setVisible(true);
		sMouseLeft.setVisible(true);
		sMouseLeft.registerEntityModifier(new MoveXModifier(1.0f, 445, -160, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sZabuttonLeft1.setPosition(498, 483);
				sZabuttonLeft2.setVisible(false);
				sMouseLeft.setPosition(sMouseLeft.getmXFirst(), sMouseLeft.getmYFirst());
				sMouseLeft.setVisible(false);
				isTouchMouseLeft = true;
			}
		}));
	}
	
	public void touchMouseRight(){
		OGG_A6_ZABUTON.play();
		sZabuttonRight1.setPosition(-999, -999);
		sZabuttonRight2.setVisible(true);
		sMouseRight.setVisible(true);
		sMouseRight.registerEntityModifier(new MoveXModifier(1.0f, 759, 980, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sZabuttonRight1.setPosition(765, 484);
				sZabuttonRight2.setVisible(false);
				sMouseRight.setPosition(sMouseLeft.getmXFirst(), sMouseLeft.getmYFirst());
				sMouseRight.setVisible(false);
				isTouchMouseRight = true;
			}
		}));
	}
	
	public void touchTable(){
		isTouchTable = false;
		sTable.setVisible(false);
		sTable2.setVisible(true);
		sTable2.registerEntityModifier(new ParallelEntityModifier(
			new SequenceEntityModifier(
				new DelayModifier(1.3f , new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sTable2.setVisible(false);
					}
				}),
				new DelayModifier(0.2f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sTable.setVisible(true);
						isTouchTable = true;
					}
				})),
			new ScaleModifier(1.0f, 1.0f, 1.7f)
			));
	}
	
	
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		// TODO Auto-generated method stub	
		if(pModifier == deNinjaA){
			delayNinjaAC();
		}
		if(pModifier == deNinjaC){
			delayNinjaAC();
		}
		
		if(pModifier == deTeng183){
			sTatamiR.setVisible(false);
			sTatamiShaR.setVisible(false);
			sTeng183.setVisible(false);
			
			isCatchNinja = true;
			
			isTouchHand254 = true;
			sHand254.setVisible(true);
			
		}
		
		if(pModifier == deTeng181){
			isCatchNinja = true;
			sTeng181.setVisible(false);
			isTouchHand255 = true;
			sHand255.setVisible(true);
			sHand251.setVisible(true);
			sHand259.setVisible(true);
			sHand2511.setVisible(true);
			sRoomRight.setVisible(true);
		
			isTouchHand257 = true;
			sHand257.setVisible(true);				
		}
		
		if(pModifier == deTeng182){	
			isCatchNinja = true;
			sTeng182.setVisible(false);
			isTouchHand258 = true;
			sHand258.setVisible(true);			
		}
		
		if(pModifier == deNinjaB){
			delayNinjaBF();
		}
		
		if(pModifier == deNinjaF){
			delayNinjaBF();
		}
		
		if(pModifier == deKing){
			isCatchNinja = true;
			sKing.setVisible(false);
			
			isTouchHand256 = true;
			sHand256.setVisible(true);	
			
			isTouchHand259 = true;
			sHand259.setVisible(true);
			
			isTouchHand2511 = true;
			sHand2511.setVisible(true);
			
			isTouchHand2515 = true;
			sHand2515.setVisible(true);
			
			isTouchHand2516 = true;
			sHand2516.setVisible(true);
		}
		
		if(pModifier == deNinjaG){
			delayNinjaDG();
		}
		
		if(pModifier == deNinjaD){
			delayNinjaDG();
		}
		
		if(pModifier == dePrincess){
			isCatchNinja = true;
			sPrincess.setVisible(false);
			
			isTouchHand252 = true;
			sHand252.setVisible(true);
			
			isTouchHand253 =true;
			sHand253.setVisible(true);
			
			sTatamiL.setVisible(false);
			sTatamiShaL.setVisible(false);
			
			isTouchHand2512 = true;
			sHand2512.setVisible(true);
		}
		
		if(pModifier == deSmoke1){
			sSmoke1.setVisible(false);
			sSmoke2.setVisible(true);
			visibleSmoke2();
		}
		
		if(pModifier == deSmoke2){
			
			sSmoke2.setVisible(false);
			sSmoke3.setVisible(true);
			visibleSmoke3();
		}
		
		if(pModifier == deSmoke3){
			sSmoke3.setVisible(false);
			sSmoke4.setVisible(true);
			visibleSmoke4();
		}
		
		if(pModifier == deSmoke4){
			sSmoke4.setVisible(false);
			visibleSmokeFinish();
		}
}

	@Override
	public void onModifierStarted(IModifier<IEntity> pModifier, IEntity arg1) {
		// TODO Auto-generated method stub
	}
	
	public void setPosition(int position, Sprite display){
		if(position == 1) display.setPosition(10, 90);
		if(position == 2) display.setPosition(113,90);
		if(position == 3) display.setPosition(10, 190);
		if(position == 4) display.setPosition(113,190);
		if(position == 5) display.setPosition(10, 290);
		if(position == 6) display.setPosition(113,290);
		if(position == 7) display.setPosition(10, 390);
		if(position == 8) display.setPosition(113,390);
		if(position == 9) display.setPosition(10, 490);
		if(position == 10) display.setPosition(113,490);
	}
	
	public void delayNinjaAC(){
		sTatamiR.setVisible(false);
		sTatamiShaR.setVisible(false);
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setVisible(false);
			sNinjaA[i].setPosition(-999, -999);
		}
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setVisible(false);
			sNinjaC[i].setPosition(-999, -999);
		}
		
		isCatchNinja = true;
		
		isTouchHand251 = true;
		sHand251.setVisible(true);
		
		isTouchHand254 = true;
		sHand254.setVisible(true);
		
		isTouchHand255 = true;
		sHand251.setVisible(true);
		
		isTouchHand257 = true;
		sHand257.setVisible(true);
		
		isTouchHand258 = true;
		sHand258.setVisible(true);
		
		sHand259.setVisible(true);
		sHand2511.setVisible(true);
		sHand256.setVisible(true);
		sHand2512.setVisible(true);
		
		sRoomRight.setVisible(true);
		Log.d(TAG, "AAAAAAAAAAAAAAACCCCCCCCCCCCCCCCCCCCCC");
		checkFinish();
	}
	
	public void delayNinjaBF(){
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setVisible(false);
			sNinjaB[i].setPosition(-999, -999);
		}
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setVisible(false);
			sNinjaF[i].setPosition(-999, -999);
		}
		isCatchNinja = true;
		
		isTouchHand256 = true;
		sHand256.setVisible(true);
		
		isTouchHand259 = true;
		sHand259.setVisible(true);
		
		isTouchHand2511 = true;
		sHand259.setVisible(true);
		
		isTouchHand2515 = true;
		sHand2515.setVisible(true);
		
		isTouchHand2516 = true;
		sHand2516.setVisible(true);
		Log.d(TAG, "bbbbbbbbbbbbbbbbffffffffffffffffffffffffff");
		checkFinish();
	}
	
	public void delayNinjaDG(){
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i].setVisible(false);
			sNinjaD[i].setPosition(-999, -999);
		}
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i].setVisible(false);
			sNinjaG[i].setPosition(-999, -999);
		}
		isCatchNinja = true;
		
		isTouchHand252 = true;
		sHand252.setVisible(true);
		
		isTouchHand253 = true;
		sHand253.setVisible(true);
		
		sTatamiL.setVisible(false);
		sTatamiShaL.setVisible(false);
		
		isTouchHand2512 = true;
		sHand2512.setVisible(true);
		
		Log.d(TAG, "DDDDDDDDDDDDDDDDGGGGGGGGGGGGGGGGGGGGGGGG");
		checkFinish();
	}
	
	public void checkFinish(){
		if(currentPosition == 10){
			finishCatsh();
		}
	}
	public void finishCatsh(){
		OGG_A6_ICON_KAITEN.play();
		Log.d("Hoan Thanh Bat Ninja", "---------------------------------------------");
		isCatchNinja = false;
		isTouchMouseLeft = false;
		isTouchMouseRight = false;
		sFontBack.setVisible(true);
		setPositionFaceNinja(-999,-999,sFNinjaYeloow,sFNinjaPink,sFNinjaLime,sFNinjaOrange,sFNinjaGreen,
				sFNinjaLightBlue,sFNinjaDarkBlue,sFNinjaGray,sFNinjaViolet,sFNinjaBlack);
		shapeBackGround.setVisible(true);
		shapeBackGround.registerEntityModifier(new ParallelEntityModifier(
			new LoopEntityModifier(
					new RotationAtModifier(0.7f, 0, 360, 480, 320),-1),
			new ScaleAtModifier(2.1f, 1.0f, 0.0f, 480, 320, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					visibleSmoke1();
				}
			})));
	}
	
	public void visibleSmoke1(){
		OGG_A6_NINJA_SMOKE.play();
		sSmoke1.setVisible(true);
		sSmoke1.registerEntityModifier(deSmoke1 = new DelayModifier(0.6f , this));
		deSmoke1.addModifierListener(this);
	}
	
	public void visibleSmoke2(){
		sSmoke2.registerEntityModifier(deSmoke2 = new DelayModifier(0.5f , this));
		deSmoke2.addModifierListener(this);
	}
	
	public void visibleSmoke3(){
		sSmoke3.registerEntityModifier(deSmoke3 = new DelayModifier(0.5f , this));
		deSmoke3.addModifierListener(this);
	}
	
	public void visibleSmoke4(){
		sSmoke4.registerEntityModifier(deSmoke4 = new DelayModifier(0.4f , this));
		deSmoke4.addModifierListener(this);
		sFontBack.setVisible(false);
	}

	public void visibleSmokeFinish(){
		sFusmaLeft.setPosition(-121,-66);
		sFusmaRight.setPosition(470, -63);
		
		sTenjoanalLeft.setVisible(false);
		sTenjoanalRight.setVisible(false);
		
		displayDoorLeft(sFusmaLeft);
		displayDoorRight(sFusmaRight);
	}
	
	public void reset(){
		sBackSky.setVisible(true);
		sCastle1.setVisible(false);
		sCastle2.setVisible(false);
		sCastle3.setVisible(false);
		sCastle4.setVisible(false);
		sCastle5.setVisible(false);
		sGimmic.setVisible(false);
		mScene.unregisterUpdateHandler(rtimeSky);
		mScene.unregisterUpdateHandler(rtime1);
		mScene.unregisterUpdateHandler(rtime2);
		mScene.unregisterUpdateHandler(rtime3);
		mScene.unregisterUpdateHandler(rtime4);
		mScene.unregisterUpdateHandler(rtime5);
		mScene.unregisterUpdateHandler(rtime6);
		
		shape.setVisible(true);
		setFalseFaceNinja(false, sFNinjaYeloow,sFNinjaPink,sFNinjaLime,sFNinjaOrange,sFNinjaGreen,
		sFNinjaLightBlue,sFNinjaDarkBlue,sFNinjaGray,sFNinjaViolet,sFNinjaBlack);
		
		setPositionFaceNinja(-999,-999,sFNinjaYeloow,sFNinjaPink,sFNinjaLime,sFNinjaOrange,sFNinjaGreen,
				sFNinjaLightBlue,sFNinjaDarkBlue,sFNinjaGray,sFNinjaViolet,sFNinjaBlack);
		
		setTrueHand(true,sHand251,sHand252,sHand253,sHand254,sHand255,sHand256,sHand257,sHand258
				,sHand259,sHand2511,sHand2512,sHand2515,sHand2516);
		
		setPositionFaceNinja(-999,-999,sHand251,sHand252,sHand253,sHand254,sHand255,sHand256,sHand257,sHand258
				,sHand259,sHand2511,sHand2512,sHand2515,sHand2516);
		
		for (int i = 0; i < sNinjaA.length; i++) {
			sNinjaA[i].setPosition(-999, -999);
			sNinjaA[i].clearEntityModifiers();
			sNinjaA[i].setVisible(false);
		}
		
		for (int i = 0; i < sNinjaC.length; i++) {
			sNinjaC[i].setPosition(-999, -999);
			sNinjaC[i].clearEntityModifiers();
			sNinjaC[i].setVisible(false);
		}
		
		for (int i = 0; i < sNinjaB.length; i++) {
			sNinjaB[i].setPosition(-999, -999);
			sNinjaB[i].clearEntityModifiers();
			sNinjaB[i].setVisible(false);
		}
		
		for (int i = 0; i < sNinjaF.length; i++) {
			sNinjaF[i].setPosition(-999, -999);
			sNinjaF[i].clearEntityModifiers();
			sNinjaF[i].setVisible(false);
		}
		
		for (int i = 0; i < sNinjaD.length; i++) {
			sNinjaD[i].setPosition(-999, -999);
			sNinjaD[i].clearEntityModifiers();
			sNinjaD[i].setVisible(false);
		}
		
		for (int i = 0; i < sNinjaG.length; i++) {
			sNinjaG[i].setPosition(-999, -999);
			sNinjaG[i].clearEntityModifiers();
			sNinjaG[i].setVisible(false);
		}
		
		sFNinjaYeloow.clearEntityModifiers();
		sFNinjaPink.clearEntityModifiers();
		sFNinjaLime.clearEntityModifiers();
		sFNinjaOrange.clearEntityModifiers();
		sFNinjaGreen.clearEntityModifiers();
		sFNinjaLightBlue.clearEntityModifiers();
		sFNinjaDarkBlue.clearEntityModifiers();
		sFNinjaGray.clearEntityModifiers();
		sFNinjaViolet.clearEntityModifiers();
		sFNinjaBlack.clearEntityModifiers();

		for (int i = 0; i < sNinjaGray.length; i++) {
			sNinjaGray[i].clearEntityModifiers();
			sNinjaGray[i].setVisible(false);
		}
		
		if(sBackSky!= null){
			sBackSky.clearEntityModifiers();
			sBackSky.setVisible(true);
			sBackSky.setPosition(-221, -105);
			
		}
			
		if(sSmoke1 !=null){
			sSmoke1.clearEntityModifiers();
			sSmoke1.setVisible(false);
		}
			
		if(sSmoke2 !=null){
			sSmoke2.clearEntityModifiers();
			sSmoke2.setVisible(false);
		}
		
		if(sSmoke3 !=null){
			sSmoke3.clearEntityModifiers();
			sSmoke3.setVisible(false);
			}
			
		if(sSmoke4 !=null){
			sSmoke4.clearEntityModifiers();
			sSmoke4.setVisible(false);
		}
		
		if(sFusmaLeft !=null){
			sFusmaLeft.clearEntityModifiers();
			sFusmaLeft.setPosition(-999, -999);
		}
		
		if(sFusmaRight !=null){
			sFusmaRight.clearEntityModifiers();
			sFusmaRight.setPosition(-999, -999);
		}
		
		if(shapeBackGround !=null){
			shapeBackGround.clearEntityModifiers();
			shapeBackGround.setVisible(false);
		}
		
		if(sTeng181 !=null){
			sTeng181.clearEntityModifiers();
			sTeng181.setVisible(false);
		}
		
		if(sTeng182 !=null){
			sTeng182.clearEntityModifiers();
			sTeng182.setVisible(false);
		}
		
		if(sTeng183 !=null){
			sTeng183.clearEntityModifiers();
			sTeng183.setVisible(false);
		}
		
		if(sPrincess !=null){
			sPrincess.clearEntityModifiers();
			sPrincess.setVisible(false);
		}
		
		if(sKing !=null){
			sKing.clearEntityModifiers();
			sKing.setVisible(false);
		}
		
		sTable.setVisible(false);
		sBandRight.setVisible(false);
		sZabuttonLeft1.setPosition(-999, -999);
		sZabuttonRight1.setPosition(-999, -999);
		sMono255.setVisible(false);
		sMono257.setVisible(false);
		sMono258.setVisible(false);
		sRoom.setVisible(false);
		sRoomRight.setVisible(false);
		sRoomLeft.setVisible(false);
		sTenjoanalLeft.setVisible(false);
		sTenjoanalRight.setVisible(false);
		sHusumal.setVisible(false);
		sHusumar.setVisible(false);
		
	}
	
	public void checkDisplayAC (){
		rNinjaAC = new Random();
		int indexAC = rNinjaAC.nextInt(listAC.size());
		Log.d(TAG, "index " + indexAC);
		caseNinjaAC = listAC.get(indexAC);
		Log.d(TAG, "thu tu " + caseNinjaAC);
		listAC.remove(indexAC);
	}
	
	public void checkDisplayBF (){
		rNinjaBF = new Random();
		int indexBF = rNinjaBF.nextInt(listBF.size());
		Log.d(TAG, "index " + indexBF);
		caseNinjaBF = listBF.get(indexBF);
		Log.d(TAG, "thu tu " + caseNinjaBF);
		listBF.remove(indexBF);
	}
	
	public void checkDisplayDG (){
		rNinjaDG = new Random();
		int indexDG = rNinjaDG.nextInt(listDG.size());
		Log.d(TAG, "index " + indexDG);
		caseNinjaDG = listDG.get(indexDG);
		Log.d(TAG, "thu tu " + caseNinjaDG);
		listDG.remove(indexDG);
	}
	
	public void randomNinjaA(){
		Log.d(TAG, "Ninja A");
		rSeNinja();
		switch (caseNinjaAC) {
		case 0:
			//Gray 6
			Log.d(TAG, "Blueeeeeeeee AAAAAAAAAAAAAAAAAAAAAA");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaDarkBlue);
			sNinjaA[0].setVisible(true);
			isTouchNinja = true;
			sNinjaA[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaDarkBlue.setVisible(true);
				}
			}));
			sFNinjaDarkBlue.registerEntityModifier(deNinjaA = new DelayModifier(1.3f, this));
			deNinjaA.addModifierListener(this);
			break;
			
		case 1:
			//Gray 7
			Log.d(TAG, "Grayyyyyyyyyyyyyyyy AAAAAAAAAAAAAAAAAAAAAA");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaGray);
			sNinjaA[1].setVisible(true);
			isTouchNinja = true;
			sNinjaA[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaGray.setVisible(true);				
				}
			}));
			sFNinjaGray.registerEntityModifier(deNinjaA = new DelayModifier(1.3f, this));
			deNinjaA.addModifierListener(this);
			break;
			
		case 2:
			//Gray 2
			Log.d(TAG, "Limeeeeeeeeeeeeeeeeeeeeeeee AAAAAAAAAAAAAAAAAAAAAA");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaLime);
			sNinjaA[2].setVisible(true);
			isTouchNinja = true;
			sNinjaA[2].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaLime.setVisible(true);			
				}
			}));
			sFNinjaLime.registerEntityModifier(deNinjaA = new DelayModifier(1.3f, this));
			deNinjaA.addModifierListener(this);
			break;
			
		case 3:
			//Gray 3
			Log.d(TAG, "Orangeeeeeeeeeeeeee AAAAAAAAAAAAAAAAAAAAAA");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaOrange);
			sNinjaA[3].setVisible(true);
			isTouchNinja = true;
			sNinjaA[3].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaOrange.setVisible(true);		
				}
			}));
			sFNinjaOrange.registerEntityModifier(deNinjaA = new DelayModifier(1.3f, this));
			deNinjaA.addModifierListener(this);
			break;
		default:
			break;
		}
	}
	
	public void randomNinjaC(){
		Log.d(TAG, "Ninja C");
		rSeNinja();
		switch (caseNinjaAC) {
		case 0:
			//Gray 6
			Log.d(TAG, "Blueeeeee CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaDarkBlue);
			sNinjaC[0].setVisible(true);
			isTouchNinja = true;
			sNinjaC[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaDarkBlue.setVisible(true);
				}
			}));
			sFNinjaDarkBlue.registerEntityModifier( deNinjaC = new DelayModifier(1.3f, this));
			deNinjaC.addModifierListener(this);
			break;
			
		case 1:
			//Gray 7
			Log.d(TAG, "Grayyyyyyyyyyyyyyyy CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaGray);
			sNinjaC[1].setVisible(true);
			isTouchNinja = true;
			sNinjaC[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaGray.setVisible(true);
				}
			}));
			sFNinjaGray.registerEntityModifier(deNinjaC = new DelayModifier(1.3f, this));
			deNinjaC.addModifierListener(this);
			break;
			
		case 2:
			//Gray 2
			Log.d(TAG, "Limeeeeeeeeeeeeeeeeeeeeeeee CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaLime);
			sNinjaC[2].setVisible(true);
			isTouchNinja = true;
			sNinjaC[2].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaLime.setVisible(true);	
				}
			}));
			sFNinjaLime.registerEntityModifier(deNinjaC = new DelayModifier(1.3f, this));
			deNinjaC.addModifierListener(this);
			break;
			
		case 3:
			//Gray 3
			Log.d(TAG, "Orangeeeeeeeeeeeeee CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaOrange);
			sNinjaC[3].setVisible(true);
			isTouchNinja = true;
			sNinjaC[3].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaOrange.setVisible(true);		
				}
			}));
			sFNinjaOrange.registerEntityModifier(deNinjaC = new DelayModifier(1.3f, this));
			deNinjaC.addModifierListener(this);
			break;
		default:
			break;
		}
	}
	
	public void randomNinjaB(){
		Log.d(TAG, "Ninja B");
		rSeNinja();
		switch (caseNinjaBF) {
		case 0:
			//Gray 5
			Log.d(TAG, "LightttttttttBlueeeeee BBBBBBBBBBBBBBBBBBBBBBB");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaLightBlue);
			sNinjaB[0].setVisible(true);
			isTouchNinja = true;
			sNinjaB[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaLightBlue.setVisible(true);
				}
			}));
			sFNinjaLightBlue.registerEntityModifier( deNinjaB = new DelayModifier(1.3f, this));
			deNinjaB.addModifierListener(this);
			break;
			
		case 1:
			//Gray 4
			Log.d(TAG, "Greennnnnnnnnnnnnn BBBBBBBBBBBBBBBBBBBBBBB");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaGreen);
			sNinjaB[1].setVisible(true);
			isTouchNinja = true;
			sNinjaB[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaGreen.setVisible(true);	
				}
			}));
			sFNinjaGreen.registerEntityModifier( deNinjaB = new DelayModifier(1.3f, this));
			deNinjaB.addModifierListener(this);
			break;
			
		case 2:
			//Gray 8
			Log.d(TAG, "Violettttttttttttttttttttt BBBBBBBBBBBBBBBBBBBBBBB");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaViolet);
			sNinjaB[2].setVisible(true);
			isTouchNinja = true;
			sNinjaB[2].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaViolet.setVisible(true);	
				}
			}));
			sFNinjaViolet.registerEntityModifier( deNinjaB = new DelayModifier(1.3f, this));
			deNinjaB.addModifierListener(this);
			break;
			
		case 3:
			//Gray 0
			Log.d(TAG, "Yellowwwwwwwwwwwwwwwwwwwwwwwww BBBBBBBBBBBBBBBBBBBBBBB");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaYeloow);
			sNinjaB[3].setVisible(true);
			isTouchNinja = true;
			sNinjaB[3].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaYeloow.setVisible(true);	
				}
			}));
			sFNinjaYeloow.registerEntityModifier( deNinjaB = new DelayModifier(1.3f, this));
			deNinjaB.addModifierListener(this);
			break;
		default:
			break;
		}
	}
	
	public void randomNinjaF(){
		Log.d(TAG, "Ninja F");
		rSeNinja();
		switch (caseNinjaBF) {
		case 0:
			//Gray 5
			Log.d(TAG, "LightttttttttBlueeeeee FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaLightBlue);
			sNinjaF[0].setVisible(true);
			isTouchNinja = true;
			sNinjaF[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaLightBlue.setVisible(true);
				}
			}));
			sFNinjaLightBlue.registerEntityModifier( deNinjaF = new DelayModifier(1.3f, this));
			deNinjaF.addModifierListener(this);
			break;
			
		case 1:
			//Gray 4
			Log.d(TAG, "Greennnnnnnnnnnnnn FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaGreen);
			sNinjaF[1].setVisible(true);
			isTouchNinja = true;
			sNinjaF[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaGreen.setVisible(true);
				}
			}));
			sFNinjaGreen.registerEntityModifier( deNinjaF = new DelayModifier(1.3f, this));
			deNinjaF.addModifierListener(this);
			break;
			
		case 2:
			//Gray 8
			Log.d(TAG, "Violettttttttttttttttttttt FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaViolet);
			sNinjaF[2].setVisible(true);
			isTouchNinja = true;
			sNinjaF[2].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaViolet.setVisible(true);
				}
			}));
			sFNinjaViolet.registerEntityModifier( deNinjaF = new DelayModifier(1.3f, this));
			deNinjaF.addModifierListener(this);
			break;
			
		case 3:
			//Gray 0
			Log.d(TAG, "Yellowwwwwwwwwwwwwwwwwwwwwwwww FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaYeloow);
			sNinjaF[3].setVisible(true);
			isTouchNinja = true;
			sNinjaF[3].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaYeloow.setVisible(true);	
				}
			}));
			sFNinjaYeloow.registerEntityModifier( deNinjaF = new DelayModifier(1.3f, this));
			deNinjaF.addModifierListener(this);
			break;
		default:
			break;
		}
	}
	
	public void randomNinjaG(){
		Log.d(TAG, "Ninja G");
		rSeNinja();
		switch (caseNinjaDG) {
		case 0:
			//Gray 9
			Log.d(TAG, "Black       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaBlack);
			sNinjaG[0].setVisible(true);
			isTouchNinja = true;
			sNinjaG[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaBlack.setVisible(true);
				}
			}));
			sFNinjaBlack.registerEntityModifier( deNinjaG = new DelayModifier(1.3f, this));
			deNinjaG.addModifierListener(this);
			break;
			
		case 1:
			//Gray 1
			Log.d(TAG, "Pink     GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaPink);
			sNinjaG[1].setVisible(true);
			isTouchNinja = true;
			sNinjaG[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaPink.setVisible(true);
				}
			}));
			sFNinjaPink.registerEntityModifier( deNinjaG = new DelayModifier(1.3f, this));
			deNinjaG.addModifierListener(this);
			break;
		}
	}
	
	public void randomNinjaD(){
		Log.d(TAG, "Ninja D");
		rSeNinja();
		switch (caseNinjaDG) {
		case 0:
			//Gray 9
			Log.d(TAG, "Black       DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaBlack);
			sNinjaD[0].setVisible(true);
			isTouchNinja = true;
			sNinjaD[0].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaBlack.setVisible(true);
				}
			}));
			sFNinjaBlack.registerEntityModifier( deNinjaD = new DelayModifier(1.3f, this));
			deNinjaD.addModifierListener(this);
			break;
			
		case 1:
			//Gray 1
			Log.d(TAG, "Pink     DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
			currentPosition++;
			Log.d(TAG, "cai gi " + currentPosition);
			setPosition(currentPosition, sFNinjaPink);
			sNinjaD[1].setVisible(true);
			isTouchNinja = true;
			sNinjaD[1].registerEntityModifier(new DelayModifier(0.5f , new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					clearGray(currentPosition);
					sFNinjaPink.setVisible(true);
				}
			}));
			sFNinjaPink.registerEntityModifier( deNinjaD = new DelayModifier(1.3f, this));
			deNinjaD.addModifierListener(this);
			break;
		}
	}
	
	public void setPositionFaceNinja(int x, int y , Sprite ...sFaceNinja){
		for (int i = 0; i < sFaceNinja.length; i++) {
			sFaceNinja[i].setPosition(x, y);
		}
	}
	
	public void setFalseFaceNinja(boolean value, Sprite ...sFace){
		for (int i = 0; i < sFace.length; i++) {
			sFace[i].setVisible(value);
		}
	}
	
	public void setTrueHand(boolean value , Sprite ...sHand){
		for (int i = 0; i < sHand.length; i++) {
			sHand[i].setVisible(value);
		}
	}
	
	public void listNinja(){
		listAC = new ArrayList<Integer>(4);
		for(int i = 0; i < 4; i++) {
			listAC.add(i);
        }
		
		listBF = new ArrayList<Integer>(4);
		for(int i = 0; i < 4; i++) {
			listBF.add(i);
        }
		
		listDG = new ArrayList<Integer>(2);
		for(int i = 0; i < 2; i++) {
			listDG.add(i);
        }
	}
	
	public void rSeNinja(){
		Random rseNinja = new Random();
		int seNinja = rseNinja.nextInt(23);
		if(seNinja == 0) OGG_A6_NINJA_MITSUKATA.play();
		if(seNinja == 5) OGG_A6_NINJA_MITSUKATA2.play();
		if(seNinja == 10) OGG_A6_NINJA_MITSUKATA3.play();
		if(seNinja == 14) OGG_A6_NINJA_MITSUKATA4.play();
		
		if(seNinja == 1) OGG_A6_NINJA_MITSUKATEMOTA.play();
		if(seNinja == 6) OGG_A6_NINJA_MITSUKATEMOTA2.play();
		if(seNinja == 11) OGG_A6_NINJA_MITSUKATEMOTA3.play();
		if(seNinja == 15) OGG_A6_NINJA_MITSUKATEMOTA4.play();
		if(seNinja == 18) OGG_A6_NINJA_MITSUKATEMOTA5.play();
		
		if(seNinja == 2) OGG_A6_NINJA_MUMUNEN.play();
		if(seNinja == 7) OGG_A6_NINJA_MUNEN.play();
		
		if(seNinja == 3) OGG_A6_NINJA_MUNENJA1.play();
		if(seNinja == 8) OGG_A6_NINJA_MUNENJA2.play();
		if(seNinja == 12) OGG_A6_NINJA_MUNENJA3.play();
		if(seNinja == 16) OGG_A6_NINJA_MUNENJA4.play();
		if(seNinja == 19) OGG_A6_NINJA_MUNENJA5.play();
		
		if(seNinja == 4) OGG_A6_NINJA_SHIMATA.play();
		if(seNinja == 9) OGG_A6_NINJA_SHIMATA2.play();
		if(seNinja == 13) OGG_A6_NINJA_SHIMATA3.play();
		if(seNinja == 17) OGG_A6_NINJA_SHIMATA4.play();
		if(seNinja == 20) OGG_A6_NINJA_SHIMATA5.play();
		if(seNinja == 21) OGG_A6_NINJA_SHIMATA6.play();
		if(seNinja == 22) OGG_A6_NINJA_SHIMATA7.play();
	}
	
	public void rSeKing(){
		Random rseKing = new Random();
		int seKing = rseKing.nextInt(2);
		if(seKing == 0) OGG_A6_KING_OHOHO.play();
		if(seKing == 1) OGG_A6_KING_TONODEOJARU.play();
	}
	
	public void rSePrincess(){
		Random rsePrincess = new Random();
		int sePrincess = rsePrincess.nextInt(2);
		if(sePrincess == 0) OGG_A6_QUEEN_HIMEJA.play();
		if(sePrincess == 1) OGG_A6_QUEEN_TIGAU.play();
	}
	
	public void rSeGrayOut(){
		Random rSeGray = new Random();
		int seGray = rSeGray.nextInt(5);
		if(seGray == 0) OGG_A6_7_1_1.play();
		if(seGray == 1) OGG_A6_7_1_2.play();
		if(seGray == 2) OGG_A6_7_1_3.play();
		if(seGray == 3) OGG_A6_7_1_4.play();
		if(seGray == 4) OGG_A6_7_1_5.play();
	}
	
	public void clearGray(int x){
		if(x == 1) sNinjaGray[0].setPosition(-999, -999);
		if(x == 2) sNinjaGray[1].setPosition(-999, -999);
		if(x == 3) sNinjaGray[2].setPosition(-999, -999);
		if(x == 4) sNinjaGray[3].setPosition(-999, -999);
		if(x == 5) sNinjaGray[4].setPosition(-999, -999);
		if(x == 6) sNinjaGray[5].setPosition(-999, -999);
		if(x == 7) sNinjaGray[6].setPosition(-999, -999);
		if(x == 8) sNinjaGray[7].setPosition(-999, -999);
		if(x == 9) sNinjaGray[8].setPosition(-999, -999);
		if(x == 10) sNinjaGray[9].setPosition(-999, -999);
	}
	
	public void mp3Stop(){
		Log.d(TAG, "MP3 STOP");
		OGG_A6_1_10SAGASE.stop();
		OGG_A6_HOHOHO.stop();
		OGG_A6_NINJA_SMOKE.stop();
		OGG_A6_ICON_KAITEN.stop();
	}
}