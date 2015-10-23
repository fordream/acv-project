package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KizunaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Kizuna extends BaseGameActivity implements IOnSceneTouchListener{
	
	private static final String TAG = "LOG_VOL3KIZUNA";
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TexturePack mCleanWindowTp;
	private TexturePack mCleanWindowIITp;
	private TexturePack mGomiraTp;
	private TexturePack mGrWaInGoBoxTp;
	private TexturePack mHaGaKaOtTp;
	private TexturePack mMiniItemTp;
	private TexturePack mMiniItemIITp;
	private TexturePack mMushibaikinTp;
	private TexturePack mNodobaikinTp;
	private TexturePack mOfukuTp;
	private TexturePack mOjiroTp;
	private TexturePack mOshiriTp;
	private TexturePack mOtaroTp;
	private TexturePack mOtemajinTp;
	private TexturePack mSupermanTp;
	
	private ITextureRegion mBackgroundTR;
	private ITextureRegion mTowerTR[] = new ITextureRegion[4] ;
	private ITextureRegion mSuperManFalseTR;
	private ITextureRegion mGrugruTR;
	private ITextureRegion mShitTR;
	private ITextureRegion mORTR;
	private ITextureRegion mHeroWinTR;
	private ITextureRegion mNodoTR[] = new ITextureRegion[5] ;
	
	private ITiledTextureRegion mBoyTiledTR;
	private ITiledTextureRegion mSuperManTiledTR[] = new ITiledTextureRegion[8];
	private ITiledTextureRegion mMonsterTiledTR[] = new ITiledTextureRegion[8];
	private ITiledTextureRegion mMonsterNameTiledTR ;
	private ITiledTextureRegion mMonsterTowerTiledTR[] =  new ITiledTextureRegion[8];
	private ITiledTextureRegion mButtonAttachTiledTR ;
	private ITiledTextureRegion mSuperManNameTiledTR ;
	private ITiledTextureRegion mWashTR;
	private ITiledTextureRegion mFinishClearTR;
	
	private Sprite mBackgroundSprite;
	private Sprite mTowerSprite[] = new Sprite[4];
	private Sprite mBlankSprite;
	private Sprite mORSprite;
	private Sprite mSuperManClear;
	private Sprite mTextClear;
	private Sprite mBgClear;
	
	private SuperManSprite mSuperManLayer;
	private MonsterLayer mMonsterLayer;
	private AnimatedSprite mMonsterTowerAniSprite[] = new AnimatedSprite[8];
	private ButtonChoose mButtonLeftAniSprite;
	private ButtonChoose mButtonRightAniSprite;
	private AnimatedSprite mFinishClearAniSprite;
	
	private TimerHandler mTimerButton;
	private TimerHandler mNextHandler;
	private TimerHandler mShowButtonHandler;
	
	/* Game Mode */
	private static final int TYPE_GAME_MOD_A = 0;
	private static final int TYPE_GAME_MOD_B = 1;
	private static int TYPE_GAME = -1;
	private int MONSTER_COUNT = 0;
	
	private int MONSTER = -1;
	private int BUTTON_CHOOSE_ID = -1;
	
	
	/* Animation Action */
	// SuperMan
	private static final int HABURASHI = 4;
	private static final int GARAGARA = 7;
	private static final int KARADA = 5;
	private static final int OTETE = 2;
	private static final int GURUGURU = 0;
	private static final int WASH = 3;
	private static final int INUKIREI = 1;
	private static final int GOMINET = 6;
	// Monster
	private static final int OSHIRI = 0;
	private static final int OJIRO = 1;
	private static final int OTEMAJIN = 2;
	private static final int OFUKU = 3;
	private static final int MUSHIBAIKIN = 4;
	private static final int OTARO = 5;
	private static final int GOMIRA = 6;
	private static final int NODOBAIKIN = 7;
	
	private static final int POINTER_TOUCH_EVIL_TOWER[][] = new int[][]{
			{62,18,154,105},
			{52,64,128,177},
			{0,24,125,147},
			{36,38,181,177},
			{78,15,194,139},
			{24,65,143,177},
			{0,45,105,142},
			{0,66,132,190}
	};
	
	private static final int POINTER_EVIL[][] = new int[][] {
			{405,	147},
			{435,	149},
			{411,	48},
			{372,	122},
			{396,	25},
			{413,	124},
			{410,	47},
			{372,	85}
	};
	
	private static final int POINTER_EVIL_TOWER[][] = new int[][] {
			{151,	13},
			{313,	64},
			{478,	1},
			{701,	-10},
			{142,	12},
			{328,	43},
			{478,	1},
			{694,	9}
	};
	
	private static final int POINTER_BUTTON[][] = new int[][] {
		{39,212, 288,212}, // Up 100%
		{-20,450, 142,450}, // Down 50%
	};
	
	private static final int POINTER_BUTTON_TRUE[] = new int[] { 165,290};
	
	private static final int ID_BUTTON_ATTACH[][] = new int[][]{
			{OSHIRI,MUSHIBAIKIN},
			{OJIRO,GOMIRA},
			{NODOBAIKIN,OTEMAJIN},
			{OFUKU,OTARO},
			{OSHIRI,MUSHIBAIKIN},
			{OFUKU,OTARO},
			{OJIRO,GOMIRA},
			{NODOBAIKIN,OTEMAJIN}
	};
	
	private boolean isChoiseButton = false;
	private int ChoiseButton_ID = -1; //0 : left 1: right
	private int isCountButtonShow = 0;

	// Sound
	private Sound OGG_A4_1_HENSHIN,OGG_A4_1_HENSHIN_VO,OGG_A4_1_SYUTA,OGG_A4_2_1_OSHIRIDA,OGG_A4_2_2_OJIRO,
	OGG_A4_3_1_1,OGG_A4_3_2_1,OGG_A4_3_3_1,OGG_A4_3_4_1,OGG_A4_3_5_1,OGG_A4_3_6_1,OGG_A4_3_7_1,OGG_A4_3_8_1,
	OGG_A4_3_KOUGEKI_BT,OGG_A4_3_TATAKAU,OGG_A4_4_TOU,OGG_A4_5_1_1,OGG_A4_5_1_2_1,OGG_A4_5_1_2_2,OGG_A4_5_1_4,
	OGG_A4_5_1_5,OGG_A4_5_2_1,OGG_A4_5_2_2_3,OGG_A4_5_2_4,OGG_A4_5_2_5,OGG_A4_5_3_1,OGG_A4_5_3_2_OTEJIMAN,
	OGG_A4_5_3_2_SORE,OGG_A4_5_3_4,OGG_A4_5_3_5,OGG_A4_5_4_1,OGG_A4_5_4_1_3,OGG_A4_5_4_4,OGG_A4_5_4_5,
	OGG_A4_5_5_1,OGG_A4_5_5_2_3,OGG_A4_5_5_4,OGG_A4_5_5_5,OGG_A4_5_6_1,OGG_A4_5_6_2_OTARO,OGG_A4_5_6_2_YOGOSU,
	OGG_A4_5_6_4,OGG_A4_5_6_5,OGG_A4_5_7_1,OGG_A4_5_7_2_GOMIRA,OGG_A4_5_7_2_UO,OGG_A4_5_7_4,OGG_A4_5_7_5,
	OGG_A4_5_8_1,OGG_A4_5_8_2_IGA,OGG_A4_5_8_2_NODOBAIKIN,OGG_A4_5_8_4,OGG_A4_5_8_5,OGG_A4_5_TEKIKOUSEI,
	OGG_A4_5_TEKITOJO,OGG_A4_7YATANE,OGG_A4_7YATANE_ALL,OGG_A4_9_TUGIHAKOTI,OGG_A4_9_YARARETA,OGG_A4_ENAGY,
	OGG_A4_GARA,OGG_A4_GARAGARAPE,OGG_A4_GARAPE,OGG_A4_GOMIBAKO_KAMAE,
	OGG_A4_GOMIBAKO_NET,OGG_A4_GOMIBAKO_VO,OGG_A4_GURUMAKI,OGG_A4_GURUMAKI_VO,
	OGG_A4_HABURASHIATAC,OGG_A4_HABURASHI_VO,OGG_A4_INUKIREI,OGG_A4_ITEM_KAKAGE,OGG_A4_JUMP,
	OGG_A4_KARADAKIREI,OGG_A4_KIREI,OGG_A4_OTETEKIREI,OGG_A4_SENTAKUGURU,OGG_A4_SENTAKU_MATAGARI,OGG_A4_WASH;

	private Sound OGG_MONSTER_SCENE[];
	private Sound OGG_MONSTER_TRUE[];
	private Sound OGG_MONSTER_FALSE[];
	
	private Sound OGG_HERONAME[];
	private Sound OGG_HEROACTION[][];
	private Sound OGG_BUTTONACTION[];
	
	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kizuna/gfx/");
		SoundFactory.setAssetBasePath("kizuna/mfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets());
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		
		mCleanWindowTp = mTexturePackLoaderFromSource.load("cleanWindow.xml");
		mCleanWindowIITp = mTexturePackLoaderFromSource.load("cleanWindowII.xml");
		mGomiraTp = mTexturePackLoaderFromSource.load("gomira.xml");
		mGrWaInGoBoxTp = mTexturePackLoaderFromSource.load("GrWaInGoBox.xml");
		mHaGaKaOtTp = mTexturePackLoaderFromSource.load("HaGaKaOt.xml");
		mMiniItemTp = mTexturePackLoaderFromSource.load("miniItem.xml");
		mMushibaikinTp = mTexturePackLoaderFromSource.load("mushibaikin.xml");
		mNodobaikinTp = mTexturePackLoaderFromSource.load("nodobaikin.xml");
		mOfukuTp = mTexturePackLoaderFromSource.load("ofuku.xml");
		mOjiroTp = mTexturePackLoaderFromSource.load("ojiro.xml");
		mOshiriTp = mTexturePackLoaderFromSource.load("oshiri.xml");
		mOtaroTp = mTexturePackLoaderFromSource.load("otaro.xml");
		mOtemajinTp = mTexturePackLoaderFromSource.load("otemajin.xml");
		mSupermanTp = mTexturePackLoaderFromSource.load("superman.xml");
		mMiniItemIITp = mTexturePackLoaderFromSource.load("miniItemII.xml");
		
		
		mCleanWindowTp.loadTexture();
		mCleanWindowIITp.loadTexture();
		mGomiraTp.loadTexture();
		mGrWaInGoBoxTp.loadTexture();
		mHaGaKaOtTp.loadTexture();
		mMiniItemTp.loadTexture();
		mMushibaikinTp.loadTexture();
		mNodobaikinTp.loadTexture();
		mOfukuTp.loadTexture();
		mOjiroTp.loadTexture();
		mOshiriTp.loadTexture();
		mOtaroTp.loadTexture();
		mOtemajinTp.loadTexture();
		mSupermanTp.loadTexture();
		mMiniItemIITp.loadTexture();
		
		System.gc();
		
		final TexturePackTextureRegionLibrary mCleanWindowTpLibs = mCleanWindowTp.getTexturePackTextureRegionLibrary();
		mBackgroundTR = mCleanWindowTpLibs.get(Vol3KizunaResource.cleanWindow.A4_1_1_IPHONE_HAIKEI_ID);
		mTowerTR[0] = mCleanWindowTpLibs.get(Vol3KizunaResource.cleanWindow.A4_1_2_IPHONE_BLDG_ID);
		mTowerTR[1] = mCleanWindowTpLibs.get(Vol3KizunaResource.cleanWindow.A4_1_3_IPHONE_BLDG_ID);
		mTowerTR[2] = mCleanWindowTpLibs.get(Vol3KizunaResource.cleanWindow.A4_1_4_IPHONE_BLDG_ID);
		mTowerTR[3] = mCleanWindowTpLibs.get(Vol3KizunaResource.cleanWindow.A4_1_5_IPHONE_BLDG_ID);
		
		//final TexturePackTextureRegionLibrary mSupermanTpLibs = mSupermanTp.getTexturePackTextureRegionLibrary();
		mBoyTiledTR = getTiledTextureFromPacker(mSupermanTp,
				Vol3KizunaResource.superman.A4_4_1_1_IPHONE_HENSHIN_ID,
				Vol3KizunaResource.superman.A4_4_1_2_IPHONE_HENSHIN_ID,
				Vol3KizunaResource.superman.A4_4_1_3_IPHONE_HENSHIN_ID,
				Vol3KizunaResource.superman.A4_4_1_4_IPHONE_HENSHIN_ID
				);
		
		mSuperManTiledTR[GURUGURU] = getTiledTextureFromPacker(mGrWaInGoBoxTp,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_8_1_IPHONE_GURUGURU_ID, // GURUGURU
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_8_2_IPHONE_GURUGURU_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_8_3_IPHONE_GURUGURU_ID
				);
		
		mSuperManTiledTR[HABURASHI] = getTiledTextureFromPacker(mHaGaKaOtTp,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_4_1_IPHONE_HABURASHI_ID, // HABURASHI
				Vol3KizunaResource.HaGaKaOt.A4_4_2_4_2_IPHONE_HABURASHI_ID,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_4_3_IPHONE_HABURASHI_ID
				);
		mSuperManTiledTR[GARAGARA] = getTiledTextureFromPacker(mHaGaKaOtTp,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_5_1_IPHONE_GARAGARA_ID, // GARAGARA
				Vol3KizunaResource.HaGaKaOt.A4_4_2_5_2_IPHONE_GARAGARA_ID,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_5_3_IPHONE_GARAGARA_ID
				);
		mSuperManTiledTR[KARADA] = getTiledTextureFromPacker(mHaGaKaOtTp,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_6_1_IPHONE_KARADA_ID, // KARADA
				Vol3KizunaResource.HaGaKaOt.A4_4_2_6_2_IPHONE_KARADA_ID, 
				Vol3KizunaResource.HaGaKaOt.A4_4_2_6_3_IPHONE_KARADA_ID
				);
		mSuperManTiledTR[OTETE] = getTiledTextureFromPacker(mHaGaKaOtTp,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_7_1_IPHONE_OTETE_ID, // OTETE
				Vol3KizunaResource.HaGaKaOt.A4_4_2_7_2_IPHONE_OTETE_ID,
				Vol3KizunaResource.HaGaKaOt.A4_4_2_7_3_IPHONE_OTETE_ID
				);
		mSuperManTiledTR[WASH] = getTiledTextureFromPacker(mGrWaInGoBoxTp,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_9_1_IPHONE_WASH_ID, // WASH
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_9_2_IPHONE_WASH_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_9_3_IPHONE_WASH_ID
				);
		mSuperManTiledTR[INUKIREI] = getTiledTextureFromPacker(mGrWaInGoBoxTp,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_10_1_IPHONE_INUKIREI_ID, // INUKIREI
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_10_2_IPHONE_INUKIREI_ID, 
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_10_3_IPHONE_INUKIREI_ID
				);
		mSuperManTiledTR[GOMINET] = getTiledTextureFromPacker(mGrWaInGoBoxTp,
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_11_1_IPHONE_GOMINET_ID, // GOMINET
				Vol3KizunaResource.GrWaInGoBox.A4_4_2_11_2_IPHONE_GOMINET_ID
				);
		
		mMonsterTiledTR[OSHIRI] = getTiledTextureFromPacker(mOshiriTp,
				Vol3KizunaResource.oshiri.A4_5_1_1_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.oshiri.A4_5_1_2_1_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.oshiri.A4_5_1_3_1_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.oshiri.A4_5_1_4_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.oshiri.A4_5_1_5_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.oshiri.A4_5_1_6_IPHONE_OSHIRI_ID
				);
		mMonsterTiledTR[OJIRO] = getTiledTextureFromPacker(mOjiroTp, 
				Vol3KizunaResource.ojiro.A4_5_2_1_IPHONE_OJIRO_ID,
				Vol3KizunaResource.ojiro.A4_5_2_2_IPHONE_OJIRO_ID,
				Vol3KizunaResource.ojiro.A4_5_2_3_IPHONE_OJIRO_ID,
				Vol3KizunaResource.ojiro.A4_5_2_4_IPHONE_OJIRO_ID,
				Vol3KizunaResource.ojiro.A4_5_2_5_IPHONE_OJIRO_ID,
				Vol3KizunaResource.ojiro.A4_5_2_6_IPHONE_OJIRO_ID
				);
		mMonsterTiledTR[OTEMAJIN] = getTiledTextureFromPacker(mOtemajinTp,
				Vol3KizunaResource.otemajin.A4_5_3_1_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.otemajin.A4_5_3_2_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.otemajin.A4_5_3_3_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.otemajin.A4_5_3_4_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.otemajin.A4_5_3_5_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.otemajin.A4_5_3_6_IPHONE_OTEMAJIN_ID
				);
		mMonsterTiledTR[OFUKU] = getTiledTextureFromPacker(mOfukuTp, 
				Vol3KizunaResource.ofuku.A4_5_4_1_IPHONE_OFUKU_ID,
				Vol3KizunaResource.ofuku.A4_5_4_2_1_IPHONE_OFUKU_ID,
				Vol3KizunaResource.ofuku.A4_5_4_3_1_IPHONE_OFUKU_ID,
				Vol3KizunaResource.ofuku.A4_5_4_4_IPHONE_OFUKU_ID,
				Vol3KizunaResource.ofuku.A4_5_4_5_IPHONE_OFUKU_ID,
				Vol3KizunaResource.ofuku.A4_5_4_6_IPHONE_OFUKU_ID
				);
		
		mMonsterTiledTR[MUSHIBAIKIN] = getTiledTextureFromPacker(mMushibaikinTp, 
				Vol3KizunaResource.mushibaikin.A4_5_5_1_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.mushibaikin.A4_5_5_2_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.mushibaikin.A4_5_5_3_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.mushibaikin.A4_5_5_4_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.mushibaikin.A4_5_5_5_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.mushibaikin.A4_5_5_6_IPHONE_MUSHIBAIKIN_ID
				);
		mMonsterTiledTR[OTARO] = getTiledTextureFromPacker(mOtaroTp, 
				Vol3KizunaResource.otaro.A4_5_6_1_IPHONE_OTARO_ID,
				Vol3KizunaResource.otaro.A4_5_6_2_IPHONE_OTARO_ID,
				Vol3KizunaResource.otaro.A4_5_6_3_IPHONE_OTARO_ID,
				Vol3KizunaResource.otaro.A4_5_6_4_IPHONE_OTARO_ID,
				Vol3KizunaResource.otaro.A4_5_6_5_IPHONE_OTARO_ID,
				Vol3KizunaResource.otaro.A4_5_6_6_IPHONE_OTARO_ID
				); 
		mMonsterTiledTR[GOMIRA] = getTiledTextureFromPacker(mGomiraTp, 
				Vol3KizunaResource.gomira.A4_5_7_1_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.gomira.A4_5_7_2_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.gomira.A4_5_7_3_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.gomira.A4_5_7_4_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.gomira.A4_5_7_5_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.gomira.A4_5_7_6_IPHONE_GOMIRA_ID
				);
		mMonsterTiledTR[NODOBAIKIN] = getTiledTextureFromPacker(mNodobaikinTp, 
				Vol3KizunaResource.nodobaikin.A4_5_8_1_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.nodobaikin.A4_5_8_2_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.nodobaikin.A4_5_8_3_1_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.nodobaikin.A4_5_8_4_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.nodobaikin.A4_5_8_5_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.nodobaikin.A4_5_8_6_IPHONE_NODOBAIKIN_ID
				);
		
		mMonsterTowerTiledTR[OSHIRI] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_1_1_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.miniItem.A4_2_1_2_IPHONE_OSHIRI_ID
				);
		mMonsterTowerTiledTR[OJIRO] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_2_1_IPHONE_OJIRO_ID,
				Vol3KizunaResource.miniItem.A4_2_2_2_IPHONE_OJIRO_ID
				);
		mMonsterTowerTiledTR[OTEMAJIN] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_3_1_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.miniItem.A4_2_3_2_IPHONE_OTEMAJIN_ID
				);
		mMonsterTowerTiledTR[OFUKU] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_4_1_IPHONE_OFUKU_ID,
				Vol3KizunaResource.miniItem.A4_2_4_2_IPHONE_OFUKU_ID
				);
		mMonsterTowerTiledTR[MUSHIBAIKIN] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_5_1_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.miniItem.A4_2_5_2_IPHONE_MUSHIBAIKIN_ID
				);
		mMonsterTowerTiledTR[OTARO] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_6_1_IPHONE_OTARO_ID,
				Vol3KizunaResource.miniItem.A4_2_6_2_IPHONE_OTARO_ID
				);
		mMonsterTowerTiledTR[GOMIRA] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_7_1_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.miniItem.A4_2_7_2_IPHONE_GOMIRA_ID
				);
		mMonsterTowerTiledTR[NODOBAIKIN] = getTiledTextureFromPacker(mMiniItemTp, 
				Vol3KizunaResource.miniItem.A4_2_8_1_IPHONE_NODOBAIKIN_ID,
				Vol3KizunaResource.miniItem.A4_2_8_2_IPHONE_NODOBAIKIN_ID
				);
		
		mMonsterNameTiledTR = getTiledTextureFromPacker(mGrWaInGoBoxTp, 
				Vol3KizunaResource.GrWaInGoBox.A4_5_1_7_IPHONE_OSHIRI_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_2_7_IPHONE_OJIRO_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_3_7_IPHONE_OTEMAJIN_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_4_7_IPHONE_OFUKU_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_5_7_IPHONE_MUSHIBAIKIN_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_6_7_IPHONE_OTARO_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_7_7_IPHONE_GOMIRA_ID,
				Vol3KizunaResource.GrWaInGoBox.A4_5_8_7_IPHONE_NODOBAIKIN_ID
				);
		
		mButtonAttachTiledTR = getTiledTextureFromPacker(mMiniItemTp,
				Vol3KizunaResource.miniItem.A4_3_5_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_5_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_7_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_7_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_4_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_4_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_6_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_6_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_1_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_1_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_3_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_3_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_8_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_8_2_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_2_1_IPHONE_BUTTON_ID,
				Vol3KizunaResource.miniItem.A4_3_2_2_IPHONE_BUTTON_ID
				);
		mSuperManFalseTR = mSupermanTp.getTexturePackTextureRegionLibrary().get(
				Vol3KizunaResource.superman.A4_4_2_2_IPHONE_HEROLOSE_ID);
		
		
		mSuperManNameTiledTR = getTiledTextureFromPacker(mSupermanTp, 
				Vol3KizunaResource.superman.A4_4_2_8_5_IPHONE_GURUGURU_ID,
				Vol3KizunaResource.superman.A4_4_2_10_5_IPHONE_INUKIREI_ID,
				Vol3KizunaResource.superman.A4_4_2_7_6_IPHONE_OTETE_ID,
				Vol3KizunaResource.superman.A4_4_2_9_6_IPHONE_WASH_ID,
				Vol3KizunaResource.superman.A4_4_2_4_5_IPHONE_HABURASHI_ID,
				Vol3KizunaResource.superman.A4_4_2_6_6_IPHONE_KARADA_ID,
				Vol3KizunaResource.superman.A4_4_2_11_4_IPHONE_GOMINET_ID,
				Vol3KizunaResource.superman.A4_4_2_5_6_IPHONE_GARAGARA_ID
				);
		
		mGrugruTR = getTiledTextureFromPacker(mMiniItemTp, Vol3KizunaResource.miniItem.A4_4_2_8_4_IPHONE_GURUGURU_ID);
		mShitTR = getTiledTextureFromPacker(mCleanWindowTp, Vol3KizunaResource.cleanWindow.A4_5_1_2_2_IPHONE_OSHIRI_ID);
		mORTR = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_3_IPHONE_OR_ID);
		mHeroWinTR = mSupermanTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.superman.A4_4_2_3_IPHONE_HEROWIN_ID);
		mNodoTR[0] = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_5_8_3_2_IPHONE_NODOBAIKIN_ID);
		mNodoTR[1] = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_5_8_3_3_IPHONE_NODOBAIKIN_ID);
		mNodoTR[2] = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_5_8_3_4_IPHONE_NODOBAIKIN_ID);
		mNodoTR[3] = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_5_8_3_5_IPHONE_NODOBAIKIN_ID);
		mNodoTR[4] = mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_5_8_3_6_IPHONE_NODOBAIKIN_ID);
		
		mWashTR = getTiledTextureFromPacker(mMiniItemIITp,  Vol3KizunaResource.miniItemII.A4_4_2_9_4_IPHONE_WASH_ID,Vol3KizunaResource.miniItemII.A4_4_2_9_5_IPHONE_WASH_ID);
		mFinishClearTR = getTiledTextureFromPacker(mCleanWindowIITp, Vol3KizunaResource.cleanWindowII.A4_1_7_IPHONE_CLEARWINDOW_ID, Vol3KizunaResource.cleanWindowII.A4_1_8_IPHONE_CLEARWINDOW_ID);
		
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A4_1_HENSHIN = loadSoundResourceFromSD(Vol3KizunaResource.A4_1_HENSHIN);
		OGG_A4_1_HENSHIN_VO = loadSoundResourceFromSD(Vol3KizunaResource.A4_1_HENSHIN_VO);
		OGG_A4_1_SYUTA = loadSoundResourceFromSD(Vol3KizunaResource.A4_1_SYUTA);
		OGG_A4_2_1_OSHIRIDA = loadSoundResourceFromSD(Vol3KizunaResource.A4_2_1_OSHIRIDA);
		OGG_A4_2_2_OJIRO = loadSoundResourceFromSD(Vol3KizunaResource.A4_2_2_OJIRO);
		OGG_A4_3_1_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_1_1);
		OGG_A4_3_2_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_2_1);
		OGG_A4_3_3_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_3_1);
		OGG_A4_3_4_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_4_1);
		OGG_A4_3_5_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_5_1);
		OGG_A4_3_6_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_6_1);
		OGG_A4_3_7_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_7_1);
		OGG_A4_3_8_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_8_1);
		OGG_A4_3_KOUGEKI_BT = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_KOUGEKI_BT);
		OGG_A4_3_TATAKAU = loadSoundResourceFromSD(Vol3KizunaResource.A4_3_TATAKAU);
		OGG_A4_4_TOU = loadSoundResourceFromSD(Vol3KizunaResource.A4_4_TOU);
		OGG_A4_5_1_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_1_1);
		OGG_A4_5_1_2_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_1_2_1);
		OGG_A4_5_1_2_2 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_1_2_2);
		OGG_A4_5_1_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_1_4);
		OGG_A4_5_1_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_1_5);
		OGG_A4_5_2_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_2_1);
		OGG_A4_5_2_2_3 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_2_2_3);
		OGG_A4_5_2_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_2_4);
		OGG_A4_5_2_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_2_5);
		OGG_A4_5_3_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_3_1);
		OGG_A4_5_3_2_OTEJIMAN = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_3_2_OTEJIMAN);
		OGG_A4_5_3_2_SORE = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_3_2_SORE);
		OGG_A4_5_3_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_3_4);
		OGG_A4_5_3_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_3_5);
		OGG_A4_5_4_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_4_1);
		OGG_A4_5_4_1_3 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_4_1_3);
		OGG_A4_5_4_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_4_4);
		OGG_A4_5_4_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_4_5);
		OGG_A4_5_5_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_5_1);
		OGG_A4_5_5_2_3 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_5_2_3);
		OGG_A4_5_5_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_5_4);
		OGG_A4_5_5_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_5_5);
		OGG_A4_5_6_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_6_1);
		OGG_A4_5_6_2_OTARO = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_6_2_OTARO);
		OGG_A4_5_6_2_YOGOSU = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_6_2_YOGOSU);
		OGG_A4_5_6_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_6_4);
		OGG_A4_5_6_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_6_5);
		OGG_A4_5_7_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_7_1);
		OGG_A4_5_7_2_GOMIRA = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_7_2_GOMIRA);
		OGG_A4_5_7_2_UO = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_7_2_UO);
		OGG_A4_5_7_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_7_4);
		OGG_A4_5_7_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_7_5);
		OGG_A4_5_8_1 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_8_1);
		OGG_A4_5_8_2_IGA = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_8_2_IGA);
		OGG_A4_5_8_2_NODOBAIKIN = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_8_2_NODOBAIKIN);
		OGG_A4_5_8_4 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_8_4);
		OGG_A4_5_8_5 = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_8_5);
		OGG_A4_5_TEKIKOUSEI = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_TEKIKOUSEI);
		OGG_A4_5_TEKITOJO = loadSoundResourceFromSD(Vol3KizunaResource.A4_5_TEKITOJO);
		OGG_A4_7YATANE = loadSoundResourceFromSD(Vol3KizunaResource.A4_7YATANE);
		OGG_A4_7YATANE_ALL = loadSoundResourceFromSD(Vol3KizunaResource.A4_7YATANE_ALL);
		OGG_A4_9_TUGIHAKOTI = loadSoundResourceFromSD(Vol3KizunaResource.A4_9_TUGIHAKOTI);
		OGG_A4_9_YARARETA = loadSoundResourceFromSD(Vol3KizunaResource.A4_9_YARARETA);
		OGG_A4_ENAGY = loadSoundResourceFromSD(Vol3KizunaResource.A4_ENAGY);
		OGG_A4_GARA = loadSoundResourceFromSD(Vol3KizunaResource.A4_GARA);
		OGG_A4_GARAGARAPE = loadSoundResourceFromSD(Vol3KizunaResource.A4_GARAGARAPE);
		OGG_A4_GARAPE = loadSoundResourceFromSD(Vol3KizunaResource.A4_GARAPE);
		OGG_A4_GOMIBAKO_KAMAE = loadSoundResourceFromSD(Vol3KizunaResource.A4_GOMIBAKO_KAMAE);
		OGG_A4_GOMIBAKO_NET = loadSoundResourceFromSD(Vol3KizunaResource.A4_GOMIBAKO_NET);
		OGG_A4_GOMIBAKO_VO = loadSoundResourceFromSD(Vol3KizunaResource.A4_GOMIBAKO_VO);
		OGG_A4_GURUMAKI = loadSoundResourceFromSD(Vol3KizunaResource.A4_GURUMAKI);
		OGG_A4_GURUMAKI_VO = loadSoundResourceFromSD(Vol3KizunaResource.A4_GURUMAKI_VO);
		OGG_A4_HABURASHIATAC = loadSoundResourceFromSD(Vol3KizunaResource.A4_HABURASHIATAC);
		OGG_A4_HABURASHI_VO = loadSoundResourceFromSD(Vol3KizunaResource.A4_HABURASHI_VO);
		OGG_A4_INUKIREI = loadSoundResourceFromSD(Vol3KizunaResource.A4_INUKIREI);
		OGG_A4_ITEM_KAKAGE = loadSoundResourceFromSD(Vol3KizunaResource.A4_ITEM_KAKAGE);
		OGG_A4_JUMP = loadSoundResourceFromSD(Vol3KizunaResource.A4_JUMP);
		OGG_A4_KARADAKIREI = loadSoundResourceFromSD(Vol3KizunaResource.A4_KARADAKIREI);
		OGG_A4_KIREI = loadSoundResourceFromSD(Vol3KizunaResource.A4_KIREI);
		OGG_A4_OTETEKIREI = loadSoundResourceFromSD(Vol3KizunaResource.A4_OTETEKIREI);
		OGG_A4_SENTAKUGURU = loadSoundResourceFromSD(Vol3KizunaResource.A4_SENTAKUGURU);
		OGG_A4_SENTAKU_MATAGARI = loadSoundResourceFromSD(Vol3KizunaResource.A4_SENTAKU_MATAGARI);
		OGG_A4_WASH = loadSoundResourceFromSD(Vol3KizunaResource.A4_WASH);
		
		OGG_MONSTER_SCENE= new Sound[] {
				OGG_A4_5_1_1,
				OGG_A4_5_2_1,
				OGG_A4_5_3_1,
				OGG_A4_5_4_1,
				OGG_A4_5_5_1,
				OGG_A4_5_6_1,
				OGG_A4_5_7_1,
				OGG_A4_5_8_1
		};
		
		OGG_MONSTER_FALSE= new Sound[] {
				OGG_A4_5_1_4,
				OGG_A4_5_2_4,
				OGG_A4_5_3_4,
				OGG_A4_5_4_4,
				OGG_A4_5_5_4,
				OGG_A4_5_6_4,
				OGG_A4_5_7_4,
				OGG_A4_5_8_4
		};
		
		OGG_MONSTER_TRUE= new Sound[] {
				OGG_A4_5_1_5,
				OGG_A4_5_2_5,
				OGG_A4_5_3_5,
				OGG_A4_5_4_5,
				OGG_A4_5_5_5,
				OGG_A4_5_6_5,
				OGG_A4_5_7_5,
				OGG_A4_5_8_5
		};
		
		OGG_HERONAME = new Sound[]{
				OGG_A4_GURUMAKI_VO,
				OGG_A4_INUKIREI,
				OGG_A4_OTETEKIREI,
				OGG_A4_SENTAKUGURU,
				OGG_A4_HABURASHI_VO,
				OGG_A4_KARADAKIREI,
				OGG_A4_GOMIBAKO_VO,
				OGG_A4_GARAPE
		};
		
		
		OGG_HEROACTION = new Sound[][] {
				{OGG_A4_ITEM_KAKAGE, OGG_A4_JUMP, OGG_A4_GURUMAKI}, // GURUGURU 
				{OGG_A4_JUMP, OGG_A4_ENAGY, OGG_A4_KIREI}, // INUKIREI
				{OGG_A4_JUMP, OGG_A4_ENAGY, OGG_A4_KIREI}, // OTETE
				{OGG_A4_SENTAKU_MATAGARI, OGG_A4_ENAGY, OGG_A4_WASH}, //WASH
				{OGG_A4_ITEM_KAKAGE, OGG_A4_JUMP, OGG_A4_HABURASHIATAC}, // HABURASHI
				{OGG_A4_JUMP, OGG_A4_ENAGY, OGG_A4_KIREI}, // KARADA
				{OGG_A4_ITEM_KAKAGE, OGG_A4_GOMIBAKO_KAMAE, OGG_A4_GOMIBAKO_NET}, //GOMINET
				{OGG_A4_ITEM_KAKAGE, OGG_A4_GARA, OGG_A4_GARAGARAPE} //GARAGARA
		};
		
	/*	private static final int OSHIRI = 0;
		private static final int OJIRO = 1;
		private static final int OTEMAJIN = 2;
		private static final int OFUKU = 3;
		private static final int MUSHIBAIKIN = 4;
		private static final int OTARO = 5;
		private static final int GOMIRA = 6;
		private static final int NODOBAIKIN = 7;*/
		
		OGG_BUTTONACTION = new Sound[]{
				OGG_A4_3_5_1,
				OGG_A4_3_7_1,
				OGG_A4_3_4_1,
				OGG_A4_3_6_1,
				OGG_A4_3_1_1,
				OGG_A4_3_3_1,
				OGG_A4_3_8_1,
				OGG_A4_3_2_1
		};

	}

	@Override
	protected void loadKaraokeScene() {
			Log.d(TAG, "loadKaraokeScene -----------------");
			
			mScene = new Scene();
			mScene.setOnSceneTouchListener(this);
			mScene.setTouchAreaBindingOnActionDownEnabled(true);
			mScene.setTouchAreaBindingOnActionMoveEnabled(true);
			
			final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
			mBackgroundSprite = new Sprite(0, 0, mBackgroundTR, vertexBufferObjectManager);
			mScene.attachChild(mBackgroundSprite);
			
			// Tower
			mTowerSprite[3] = new Sprite(701,-80, mTowerTR[0], vertexBufferObjectManager);
			mScene.attachChild(mTowerSprite[3] );
			mTowerSprite[2] = new Sprite(522,0, mTowerTR[1], vertexBufferObjectManager);
			mScene.attachChild(mTowerSprite[2] );
			mTowerSprite[1] = new Sprite(242,-354, mTowerTR[2], vertexBufferObjectManager);
			mScene.attachChild(mTowerSprite[1] );
			mTowerSprite[0] = new Sprite(-21,-21, mTowerTR[3], vertexBufferObjectManager);
			mScene.attachChild(mTowerSprite[0] );
			
			// Action Share
			for (int i = 0; i < mMonsterTowerAniSprite.length; i++) {
				mMonsterTowerAniSprite[i] = new AnimatedSprite(POINTER_EVIL_TOWER[i][0], POINTER_EVIL_TOWER[i][1], mMonsterTowerTiledTR[i], vertexBufferObjectManager);
				mScene.attachChild(mMonsterTowerAniSprite[i] );
				mMonsterTowerAniSprite[i].setVisible(false);
			}
			
			mMonsterLayer = new MonsterLayer(vertexBufferObjectManager);
			mScene.attachChild(mMonsterLayer);
			
			mSuperManLayer = new SuperManSprite(vertexBufferObjectManager);
			mScene.attachChild(mSuperManLayer);
			
			
			final BitmapTextureAtlas pTexture = new BitmapTextureAtlas(getTextureManager(), 2,2, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			final ITextureRegion pTextureRegion = new TextureRegion(pTexture, 2,2,960,640);
			mBlankSprite = new Sprite(0, 0, pTextureRegion, vertexBufferObjectManager);
			mScene.attachChild(mBlankSprite);
			mBlankSprite.setColor(0, 0, 0);
			mBlankSprite.setAlpha(0.7f);
			mBlankSprite.setVisible(false);
			Log.d(TAG, "loadKaraokeScene --------xxxxx");
			mButtonLeftAniSprite = new ButtonChoose(POINTER_BUTTON[0][0], POINTER_BUTTON[0][1], mButtonAttachTiledTR, vertexBufferObjectManager);
			mScene.attachChild(mButtonLeftAniSprite);
			mButtonRightAniSprite = new ButtonChoose(POINTER_BUTTON[0][2], POINTER_BUTTON[0][3], mButtonAttachTiledTR.deepCopy(), vertexBufferObjectManager);
			mScene.attachChild(mButtonRightAniSprite);
			mButtonLeftAniSprite.setVisible(false);
			mButtonRightAniSprite.setVisible(false);
			
			mORSprite = new Sprite(247, 296, mORTR, vertexBufferObjectManager);
			mORSprite.setVisible(false);
			mScene.attachChild(mORSprite);
			
			mBgClear = new Sprite(0, 0, mMiniItemIITp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItemII.A4_1_6_1_IPHONE_CLEARWINDOW_ID), vertexBufferObjectManager);
			mScene.attachChild(mBgClear);
			mBgClear.setVisible(false);
			mBgClear.setY(CAMERA_HEIGHT/2 -  mBgClear.getHeight()/2);
			mBgClear.setRotationCenter(mBgClear.getWidth()/2, mBgClear.getHeight()/2);
			
			mSuperManClear =  new Sprite(355, 100, mCleanWindowTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.cleanWindow.A4_1_6_3_IPHONE_CLEARWINDOW_ID), vertexBufferObjectManager);
			mScene.attachChild(mSuperManClear);
			mSuperManClear.setVisible(false);
			mSuperManClear.setScaleCenter(mSuperManClear.getWidth()/2, mSuperManClear.getHeight()/2);
			
			mTextClear =   new Sprite(183, 386, mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_1_6_2_IPHONE_CLEARWINDOW_ID), vertexBufferObjectManager);
			mScene.attachChild(mTextClear);
			mTextClear.setVisible(false);
			mTextClear.setScaleCenter(mTextClear.getWidth()/2, mTextClear.getHeight()/2);
			
			mFinishClearAniSprite = new AnimatedSprite(0, 0, mFinishClearTR, vertexBufferObjectManager);
			mScene.attachChild(mFinishClearAniSprite);
			mFinishClearAniSprite.setVisible(false);
			
			Log.d(TAG, "loadKaraokeScene ----------------- Complated");
	}

	@Override
	public void combineGimmic3WithAction() {
		
	}
	
	@Override
	public synchronized void onPauseGame() {
		// TODO Pause agme
		Log.d(TAG, "---onPauseGame---");
		// Monster Clear
		mMonsterLayer.cleanMonster();
		mMonsterLayer.clearUpdateHandlers();
		// Hero Clear
		mSuperManLayer.clearAll();
		// Base Clear
		for (int i = 0; i < mTowerSprite.length; i++) {
			mTowerSprite[i].setVisible(true);
		}
		mORSprite.setVisible(false);
		clearUpdate(mSuperManClear);
		mSuperManClear.setVisible(false);
		clearUpdate(mTextClear);
		mTextClear.setVisible(false);
		clearUpdate(mBgClear);
		mBgClear.setVisible(false);
		for (int i = 0; i < mMonsterTowerAniSprite.length; i++) {
			mMonsterTowerAniSprite[i].setVisible(false);
		}
		mButtonLeftAniSprite.setVisible(false);
		mButtonRightAniSprite.setVisible(false);
		mFinishClearAniSprite.setVisible(false);
		clearUpdate(mFinishClearAniSprite);
		mBlankSprite.setVisible(false);
		clearUpdate(mBlankSprite);
		mORSprite.setVisible(false);
		mScene.clearUpdateHandlers();
		MONSTER_COUNT = 0;
		MONSTER = -1;
		BUTTON_CHOOSE_ID = -1;
		isChoiseButton = false;
		ChoiseButton_ID = -1; //0 : left 1: right
		isCountButtonShow = 0;
		
		super.onPauseGame();
	}
	
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "---onResumeGame---");
		StartGame();
		super.onResumeGame();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		mSuperManLayer.onAreaTouch(pTouchEvent);
		int pX = (int) pTouchEvent.getX();
		int pY = (int) pTouchEvent.getY();
		boolean result = false;
			if(pTouchEvent.isActionDown()){
					for (int i = 0; i < mMonsterTowerAniSprite.length; i++) {
						if(checkContains(mMonsterTowerAniSprite[i],
								POINTER_TOUCH_EVIL_TOWER[i][0], POINTER_TOUCH_EVIL_TOWER[i][1], 
								POINTER_TOUCH_EVIL_TOWER[i][2], POINTER_TOUCH_EVIL_TOWER[i][3], pX, pY) 
								&& mMonsterTowerAniSprite[i].isVisible() && !mMonsterTowerAniSprite[i].isAnimationRunning()){
							OGG_MONSTER_SCENE[i].play();
							mMonsterTowerAniSprite[i].animate(new long[]{250,1200,500}, new int[]{0,1,0},0);	
							result = true;
							break;
						}
					}
					
					// Choice button
					if(checkContains(mButtonLeftAniSprite, 0, 0, 
							(int)mButtonLeftAniSprite.getWidth(), (int)mButtonLeftAniSprite.getHeight(), pX, pY)
							&& isChoiseButton
							&&  !mButtonLeftAniSprite.isScaled()
							){
						choiceButton(mButtonLeftAniSprite.getCurrentTileIndex(),0);
						Log.d(TAG, "A");
					}
					if(checkContains(mButtonRightAniSprite, 0, 0, 
							(int)mButtonRightAniSprite.getWidth(), (int)mButtonRightAniSprite.getHeight(), pX, pY) && isChoiseButton
							&&  !mButtonRightAniSprite.isScaled()){
						choiceButton(mButtonRightAniSprite.getCurrentTileIndex(),1);
						Log.d(TAG, "B");
					}
					
			}
		return result;
	}
	
	private void StartGame(){
		// Radom Mod
		MONSTER_COUNT = 0;
		mSuperManLayer.swichBoyToSuperMan();
		int ran = new Random().nextInt(2);
		TYPE_GAME = ran;
		MONSTER = 4 * ran;
		ActionModGame();
	}
	
	
	private void ActionModGame(){
		for (int i = 0; i < mMonsterTowerAniSprite.length; i++) {
					if(TYPE_GAME == TYPE_GAME_MOD_A){
							if(i < 4 ) {
								mMonsterTowerAniSprite[i].setVisible(true);
							}else{
								mMonsterTowerAniSprite[i].setVisible(false);
							}
					}else{
							if(i < 4 ) {
								mMonsterTowerAniSprite[i].setVisible(false);
							}else{
								mMonsterTowerAniSprite[i].setVisible(true);
							}
					}
		}
	}
	
	private void showTwoButtonAttach(int idAttach){
		OGG_A4_3_TATAKAU.play();
		mBlankSprite.setVisible(true);
		final int typeLeft = ID_BUTTON_ATTACH[idAttach][0];
		final int typeRight = ID_BUTTON_ATTACH[idAttach][1];
		final int ran = new Random().nextInt(2);
		if(ran == 0){
			viewButtonDefauft(typeLeft,typeRight);
		}else{
			viewButtonDefauft(typeRight,typeLeft);
		}
		
		mORSprite.setVisible(true);
		isChoiseButton = true;
		isCountButtonShow = 0;
	}
	
	private void viewButtonDefauft(int typeLeft, int typeRight){
		int btLeft = typeLeft * 2;
		int btRight = typeRight * 2;
		mButtonLeftAniSprite.setIndex(typeLeft);
		mButtonRightAniSprite.setIndex(typeRight);
		mButtonLeftAniSprite.setCurrentTileIndex(btLeft);
		mButtonRightAniSprite.setCurrentTileIndex(btRight);
		mButtonLeftAniSprite.resetLocalToFirst();
		mButtonRightAniSprite.resetLocalToFirst();
		mButtonLeftAniSprite.setScale(1.0f);
		mButtonRightAniSprite.setScale(1.0f);
		mButtonLeftAniSprite.setVisible(true);
		mButtonRightAniSprite.setVisible(true);
	}
	
	private void choiceButton(int mFrame,int mValue){
		OGG_A4_3_KOUGEKI_BT.play();
		mORSprite.setVisible(false);
		isChoiseButton = false;
		int id = mFrame/2;
		BUTTON_CHOOSE_ID = id;
		OGG_BUTTONACTION[id].play();
		ChoiseButton_ID = mValue;
		if(id == MONSTER){
			 Log.d(TAG, "Choice Correct" + id);
			 moveButtonToBottum(true);
		}else{
			Log.d(TAG, "Choice Not Correct" + id);
			 moveButtonToBottum(false);
		}
		
	}
	
	private void moveButtonToBottum(final boolean isTrue){
		if(ChoiseButton_ID == 0){
			mButtonRightAniSprite.setCurrentTileIndex(mButtonRightAniSprite.getCurrentTileIndex() + 1);
			mButtonRightAniSprite.setScale(0.8f);
			Log.d(TAG,"Button Left choose ");
		}else{
			mButtonLeftAniSprite.setCurrentTileIndex(mButtonLeftAniSprite.getCurrentTileIndex() + 1);
			mButtonLeftAniSprite.setScale(0.8f);
			Log.d(TAG,"Button Right choose ");
		}
		mScene.unregisterUpdateHandler(mTimerButton);
		mScene.registerUpdateHandler(mTimerButton = new TimerHandler(0.5f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				mBlankSprite.setVisible(false);
				mButtonLeftAniSprite.setScale(0.65f);
				mButtonRightAniSprite.setScale(0.65f);
				if(isCountButtonShow==0){
				mButtonLeftAniSprite.setVisible(true);
				mButtonRightAniSprite.setVisible(true);
				}else{
					mButtonLeftAniSprite.setVisible(false);
					mButtonRightAniSprite.setVisible(false);
				}
				mButtonLeftAniSprite.setPosition(POINTER_BUTTON[1][0], POINTER_BUTTON[1][1]);
				mButtonRightAniSprite.setPosition(POINTER_BUTTON[1][2], POINTER_BUTTON[1][3]);
				mSuperManLayer.attachMonster(BUTTON_CHOOSE_ID, isTrue);
			}
		}));
		
	}
	
	private void viewButtonTrue(){
			mButtonLeftAniSprite.setVisible(false);
			mButtonRightAniSprite.setVisible(false);
			OGG_A4_9_TUGIHAKOTI.play();
			if(mButtonLeftAniSprite.getIndex() == MONSTER){
				mButtonLeftAniSprite.setScale(1.0f);
				mButtonLeftAniSprite.setPosition(POINTER_BUTTON_TRUE[0], POINTER_BUTTON_TRUE[1]);
				mButtonLeftAniSprite.setCurrentTileIndex(mButtonLeftAniSprite.getCurrentTileIndex()- 1);
				mButtonLeftAniSprite.setVisible(true);
			}else{
				mButtonRightAniSprite.setScale(1.0f);
				mButtonRightAniSprite.setCurrentTileIndex(mButtonRightAniSprite.getCurrentTileIndex()- 1);
				mButtonRightAniSprite.setPosition(POINTER_BUTTON_TRUE[0], POINTER_BUTTON_TRUE[1]);
				mButtonRightAniSprite.setVisible(true);
			}
			isCountButtonShow ++;
			isChoiseButton = true;
	}
	
	
	
	// Class Superman
	class SuperManSprite extends Entity{
		// TODO Super Man Layer
		private VertexBufferObjectManager pBufferObjectManager;
		private AnimatedSprite mBoyAniSprite;
		private AnimatedSprite mSuperManAniSprite[] = new AnimatedSprite[8];
		private Sprite mSuperManSpriteFalse;
		private Sprite mSuperManWin;
		private AnimatedSprite mSuperManNameAniSprite;
		
		// Item
		private Sprite mGrugruSprite;
		private Sprite mHikariSprite;
		private Sprite mXiSprite;
		private Sprite mSmokeSprite;
		private Sprite mLuoiSprite;
		private Sprite mXiXanhSprite;
		private Sprite mSmokeXanhSprite;
		private AnimatedSprite mWashAniSprite;
		
		private TimerHandler mBoySwichTimerHandler;
		private boolean isSuperMan = false;
		
		
		
		public SuperManSprite(final VertexBufferObjectManager vbo){
			this.pBufferObjectManager = vbo;
			// Create Boy - > Superman
			mBoyAniSprite =new AnimatedSprite(215, 183, mBoyTiledTR, pBufferObjectManager);
			this.attachChild(mBoyAniSprite);
			// Create Superman
			for (int i = 0; i < mSuperManAniSprite.length; i++) {
				// 114 + 28 = 142
				mSuperManAniSprite[i] = new AnimatedSprite(142, 49, mSuperManTiledTR[i], pBufferObjectManager);
				this.attachChild(mSuperManAniSprite[i]);
				mSuperManAniSprite[i].setVisible(false);
			}
			mSuperManSpriteFalse = new Sprite(178, 316, mSuperManFalseTR, pBufferObjectManager);
			this.attachChild(mSuperManSpriteFalse);
			mSuperManSpriteFalse.setVisible(false);
			
			mSuperManNameAniSprite = new AnimatedSprite((142 + mSuperManAniSprite[0].getWidth()/2) -mSuperManNameTiledTR.getWidth()/2 , -20, mSuperManNameTiledTR, pBufferObjectManager);
			mSuperManNameAniSprite.setVisible(false);
			
			mGrugruSprite = new Sprite(119, 393, mGrugruTR, pBufferObjectManager);
			this.attachChild(mGrugruSprite);
			mGrugruSprite.setVisible(false);
			this.attachChild(mSuperManNameAniSprite);
			
			mHikariSprite = new Sprite(207, 331, mMiniItemIITp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItemII.A4_4_2_4_4_IPHONE_HABURASHI_ID), pBufferObjectManager);
			this.attachChild(mHikariSprite);
			mHikariSprite.setVisible(false);
			
			mSuperManWin = new Sprite(178, 89, mHeroWinTR, pBufferObjectManager);
			this.attachChild(mSuperManWin);
			mSuperManWin.setVisible(false);
			
			mSmokeSprite = new Sprite(182, 241, mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_4_2_10_4_IPHONE_INUKIREI_ID), pBufferObjectManager);
			this.attachChild(mSmokeSprite);
			mSmokeSprite.setVisible(false);
			
			mLuoiSprite = new Sprite(114, 123, mMiniItemTp.getTexturePackTextureRegionLibrary().get( Vol3KizunaResource.miniItem.A4_4_2_11_3_IPHONE_GOMINET_ID), pBufferObjectManager);
			this.attachChild(mLuoiSprite);
			mLuoiSprite.setVisible(false);
			
			mXiSprite = new Sprite(202, 380, mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_4_2_7_4_IPHONE_OTETE_ID), pBufferObjectManager);
			this.attachChild(mXiSprite);
			mXiSprite.setVisible(false);
			
			mXiXanhSprite = new Sprite(202, 314, mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_4_2_5_4_IPHONE_GARAGARA_ID), pBufferObjectManager); 
			this.attachChild(mXiXanhSprite);
			mXiXanhSprite.setVisible(false);
			
			mSmokeXanhSprite = new Sprite(202, 314, mMiniItemTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItem.A4_4_2_5_5_IPHONE_GARAGARA_ID), pBufferObjectManager); 
			this.attachChild(mSmokeXanhSprite);
			mSmokeXanhSprite.setVisible(false);
			
			mWashAniSprite = new AnimatedSprite(168, 208, mWashTR, pBufferObjectManager);
			this.attachChild(mWashAniSprite);
			mWashAniSprite.setVisible(false);
		}
		
		private void hidenAllIteam(){
			mGrugruSprite.setVisible(false);
			mHikariSprite.setVisible(false);
			mSmokeSprite.setVisible(false);
			mLuoiSprite.setVisible(false);
			mXiSprite.setVisible(false);
			mXiXanhSprite.setVisible(false);
			mSmokeXanhSprite.setVisible(false);
			mWashAniSprite.stopAnimation();
			mWashAniSprite.setVisible(false);
			
			clearUpdate(mGrugruSprite);
			clearUpdate(mHikariSprite);
			clearUpdate(mSmokeSprite);
			clearUpdate(mLuoiSprite);
			clearUpdate(mXiSprite);
			clearUpdate(mXiXanhSprite);
			clearUpdate(mSmokeXanhSprite);
			clearUpdate(mWashAniSprite);
		}
		
		
		public void clearAll(){
			hidenAllIteam();
			this.clearUpdateHandlers();
			clearUpdate(mBoyAniSprite);
			mBoyAniSprite.setVisible(false);
			mSuperManSpriteFalse.setVisible(false);
			clearUpdate(mSuperManSpriteFalse);
			mSuperManWin.setVisible(false);
			clearUpdate(mSuperManWin);
			mSuperManNameAniSprite.setVisible(false);
			clearUpdate(mSuperManNameAniSprite);
			for (int i = 0; i < mSuperManAniSprite.length; i++) {
				mSuperManAniSprite[i].setVisible(false);
				clearUpdate(mSuperManAniSprite[i]);
			}
			isSuperMan = false;
		}
		
		public boolean onAreaTouch(TouchEvent pTouchEvent){
			 int pX = (int) pTouchEvent.getX();
			 int pY = (int) pTouchEvent.getX();
			if(checkContains(mBoyAniSprite, 0, 70, 190, 295, pX, pY) && pTouchEvent.isActionDown()){
				Log.i(TAG, "Touch To Boy");
				changeToSuperMan();
			}
			return false;
		}
		
		// Sau 5 giay bien thanh sieu nhan
		private void swichBoyToSuperMan(){
				this.isSuperMan = false;
				mBoyAniSprite.setCurrentTileIndex(0);
				mBoyAniSprite.setVisible(true);
				this.unregisterUpdateHandler(mBoySwichTimerHandler);
				this.registerUpdateHandler(mBoySwichTimerHandler = new TimerHandler(5f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
							if(!isSuperMan){
								changeToSuperMan();
							}
					}
				}));
		}
		
		private void changeToSuperMan(){
			if(!isSuperMan){
				isSuperMan = true;
				mBoyAniSprite.animate(new long[] {250,250}, new int[] {1,2}, 2, new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								OGG_A4_1_HENSHIN.play();
								OGG_A4_1_HENSHIN_VO .play();
								OGG_A4_1_SYUTA.play();
							}
						});

					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
					}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					}
					
					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						mBoyAniSprite.setCurrentTileIndex(3);
						monsterToScene(MONSTER);
					}
				});
			}
		}
		
		private void invisibleSuperMan(){
			for (int i = 0; i < mSuperManAniSprite.length; i++) {
				mSuperManAniSprite[i].setVisible(false);
				mSuperManAniSprite[i].clearEntityModifiers();
				mSuperManAniSprite[i].clearUpdateHandlers();
			}
		}
		
		//OSHIRI = 0; OJIRO = 1;OTEMAJIN = 2; OFUKU = 3; MUSHIBAIKIN = 4; OTARO = 5;GOMIRA = 6; NODOBAIKIN = 7;
		
		private void attachMonster(final int idMonster, final boolean isAction){
			Log.d(TAG,"Attach monster: " + BUTTON_CHOOSE_ID);
			mBoyAniSprite.setVisible(false);
			hidenAllIteam();
			invisibleSuperMan();
			mSuperManSpriteFalse.setVisible(false);
			mSuperManNameAniSprite.setCurrentTileIndex(idMonster);
				switch (idMonster) {
				case OSHIRI:
					mSuperManAniSprite[OSHIRI].animate(new long[]{500,500,100}, new int[]{0,1,2},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							mBoyAniSprite.setVisible(false);
							mSuperManAniSprite[OSHIRI].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							if(paramInt1 == 0){
								OGG_HEROACTION[idMonster][0].play();
							}
							if(paramInt1 == 1){
								OGG_HEROACTION[idMonster][1].play();
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							OGG_HEROACTION[idMonster][2].play();
							mGrugruSprite.registerEntityModifier(new ScaleModifier(1.2f, 1.0f, 1.4f, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									mGrugruSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}));
						}
					});
					break;
				case OJIRO:
					mSuperManAniSprite[OJIRO].animate(new long[]{500,500,100}, new int[]{0,1,2},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							mSuperManAniSprite[OJIRO].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							if(paramInt1>=0){
								Log.d(TAG,"Sound Play ......................");
								OGG_HEROACTION[OJIRO][paramInt1].play();
							}
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							mSmokeSprite.setY(mSmokeSprite.getmYFirst());
							mSmokeSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> paramIModifier,
										IEntity paramT) {
									mSmokeSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> paramIModifier,
										IEntity paramT) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							},
							new ScaleModifier(0.8f, 1.0f, 1.5f),
							new ScaleModifier(0.8f, 1.5f, 1.0f)
									));
						}
					});
					break;
				case OTEMAJIN:
					mSuperManAniSprite[OTEMAJIN].animate(new long[]{500,500}, new int[]{0,1},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mSuperManAniSprite[OTEMAJIN].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
								if(arg1 == 0){
									OGG_HEROACTION[OTEMAJIN][arg1].play();
								}else if(arg1 == 1){
									OGG_HEROACTION[OTEMAJIN][arg1].play();
								}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mSuperManAniSprite[OTEMAJIN].setCurrentTileIndex(2);
							OGG_HEROACTION[OTEMAJIN][2].play();
							mSmokeSprite.setY(380);
							mSmokeSprite.setScale(mSmokeSprite.getWidth()/2, mSmokeSprite.getHeight()/2);
							mSmokeSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									mXiSprite.setVisible(true);
									mSmokeSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}, new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f),
								new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f)
							));
						}
					});
					break;
				case OFUKU:
					mSuperManAniSprite[OFUKU].setCurrentTileIndex(0);
					mSuperManAniSprite[OFUKU].setVisible(true);
					OGG_HEROACTION[OFUKU][0].play();
					SuperManSprite.this.registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							mWashAniSprite.animate(new long[]{600,600}, new int[]{0,1},0);
							mSuperManAniSprite[OFUKU].animate(new long[]{600,600}, new int[]{1,2}, 0,new IAnimationListener() {
								
								@Override
								public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
									mWashAniSprite.setVisible(true);
								}
								
								@Override
								public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
									
								}
								
								@Override
								public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
										if(arg1 == 1){
											OGG_HEROACTION[OFUKU][1].play();
										}
										else if(arg1 == 2){
											OGG_HEROACTION[OFUKU][2].play();
										}
								}
								
								@Override
								public void onAnimationFinished(AnimatedSprite arg0) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							});
						}
					}));
					break;
					
				case MUSHIBAIKIN:  //HABURASHI
					mSuperManAniSprite[MUSHIBAIKIN].animate(new long[]{500,500}, new int[]{0,1},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mBoyAniSprite.setVisible(false);
							invisibleSuperMan();
							mSuperManAniSprite[MUSHIBAIKIN].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							if(arg1>=0){
								OGG_HEROACTION[MUSHIBAIKIN][arg1].play();
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							OGG_HEROACTION[MUSHIBAIKIN][2].play();
							mSuperManAniSprite[MUSHIBAIKIN].setCurrentTileIndex(2);
							mHikariSprite.setRotationCenter(76, 30);
							mHikariSprite.registerEntityModifier(new RotationModifier(1.5f, -180, 0, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									mHikariSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}));
						}
					});
					break;
					
				case OTARO:
					mSuperManAniSprite[OTARO].animate(new long[]{500,500}, new int[]{0,1},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mSuperManAniSprite[OTARO].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
								if(arg1 == 0){
									OGG_HEROACTION[OTARO][arg1].play();
								}else if(arg1 == 1){
									OGG_HEROACTION[OTARO][arg1].play();
								}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mSuperManAniSprite[OTARO].setCurrentTileIndex(2);
							OGG_HEROACTION[OTARO][2].play();
							mSmokeSprite.setY(380);
							mSmokeSprite.setScale(mSmokeSprite.getWidth()/2, mSmokeSprite.getHeight()/2);
							mSmokeSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									mXiSprite.setVisible(true);
									mSmokeSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}, new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f),
								new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f)
							));
						}
					});
					break;
				case GOMIRA:
					mSuperManAniSprite[GOMIRA].animate(new long[]{500,500}, new int[]{0,1},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mSuperManAniSprite[GOMIRA].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							if(arg1 == 0){
								OGG_HEROACTION[idMonster][0].play();
							}
							if(arg1 == 1){
								OGG_HEROACTION[idMonster][1].play();
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mLuoiSprite.registerEntityModifier(new ScaleModifier(1.0f, 0.8f, 1.2f, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									OGG_HEROACTION[idMonster][2].play();
									mLuoiSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[idMonster].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}));
						}
					});
					break;
				case NODOBAIKIN:
					mSuperManAniSprite[NODOBAIKIN].animate(new long[]{500,500},new int[]{0,1},0, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mSuperManAniSprite[NODOBAIKIN].setVisible(true);
						}
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							if(arg1 == 0){
								OGG_HEROACTION[NODOBAIKIN][arg1].play();
							}else if(arg1 == 1){
								OGG_HEROACTION[NODOBAIKIN][arg1].play();
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							mSuperManAniSprite[NODOBAIKIN].setCurrentTileIndex(2);
							OGG_HEROACTION[NODOBAIKIN][2].play();
							mSmokeXanhSprite.setScale(mSmokeXanhSprite.getWidth()/2, mSmokeXanhSprite.getHeight()/2);
							mSmokeXanhSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									mXiXanhSprite.setVisible(true);
									mSmokeXanhSprite.setVisible(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									if(isAction){
										mSuperManNameAniSprite.setVisible(true);
										OGG_HERONAME[NODOBAIKIN].play();
										SuperManSprite.this.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												mMonsterLayer.actionFall(idMonster);
											}
										}));
										
									}else{
										mMonsterLayer.actionAnger(idMonster);
									}
								}
							}, new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f),
								new ScaleModifier(0.4f, 1.0f, 0.5f),
								new ScaleModifier(0.4f, 0.5f, 1.0f)
							));
						}
					});
					break;
					
				}
		}
		
		private void showWiner(){
			hidenAllIteam();
			mSuperManAniSprite[MONSTER].setVisible(false);
			mBoyAniSprite.setCurrentTileIndex(3);
			mBoyAniSprite.setVisible(true);
		}
		
		private void showActionTrue(){
			hidenAllIteam();
			int idTrue = MONSTER%4;
			mTowerSprite[idTrue].setVisible(false);
			mButtonLeftAniSprite.setVisible(false);
			mButtonRightAniSprite.setVisible(false);
			mScene.unregisterUpdateHandler(mNextHandler);
			mScene.registerUpdateHandler(mNextHandler = new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mMonsterLayer.cleanAll(true);
					mSuperManNameAniSprite.setVisible(false);
					mMonsterLayer.clearUpdateHandlers();
					if(MONSTER_COUNT == 4){
						mSuperManClear.registerEntityModifier(new ScaleModifier(1.0f, 1.0f, 1.3f));
						mTextClear.registerEntityModifier(new ScaleModifier(1.3f, 1.0f, 1.3f));
						mBgClear.registerEntityModifier(new RotationModifier(3.0f, 0, 360, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> paramIModifier,
									IEntity paramT) {
								mBoyAniSprite.setVisible(false);
								mBgClear.setVisible(true);
								mSuperManClear.setVisible(true);
								mTextClear.setVisible(true);
								OGG_A4_7YATANE.play();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> paramIModifier,
									IEntity paramT) {
								mBgClear.setVisible(false);
								mSuperManClear.setVisible(false);
								mTextClear.setVisible(false);
								mBoyAniSprite.setCurrentTileIndex(3);
								mBoyAniSprite.setVisible(true);
								mBoyAniSprite.registerEntityModifier(new MoveModifier(2.5f, 
										mBoyAniSprite.getX(), CAMERA_WIDTH+ mBoyAniSprite.getWidth(), 
										mBoyAniSprite.getY(), 0 - mBoyAniSprite.getHeight(), new IEntityModifierListener() {
											
											@Override
											public void onModifierStarted(IModifier<IEntity> paramIModifier,
													IEntity paramT) {
												mBoyAniSprite.setVisible(true);
												OGG_A4_4_TOU.play();
											}
											
											@Override
											public void onModifierFinished(IModifier<IEntity> paramIModifier,
													IEntity paramT) {
													mBoyAniSprite.setCurrentTileIndex(0);
													mBoyAniSprite.resetLocalToFirst();
													changeMode();
											}
										}));
							}
						}));
						
						
					}else if(MONSTER_COUNT == 8){
						MONSTER_COUNT = 0;
						OGG_A4_7YATANE_ALL.play();
						mFinishClearAniSprite.animate(500,3, new IAnimationListener() {
							
							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mBoyAniSprite.setVisible(false);
								mFinishClearAniSprite.setVisible(true);
							}
							
							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								
							}
							
							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
								
							}
							
							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								mFinishClearAniSprite.setVisible(false);
								changeMode();
							}
						});
					}else{
						mBoyAniSprite.setCurrentTileIndex(3);
						mBoyAniSprite.setVisible(true);
						MONSTER++;
						monsterToScene(MONSTER);
					}
				}
			}));
		}
		
		private void changeMode(){
			for (int i = 0; i < mTowerSprite.length; i++) {
				mTowerSprite[i].setVisible(true);
			}
			if(TYPE_GAME == TYPE_GAME_MOD_A){
				TYPE_GAME = TYPE_GAME_MOD_B;
				MONSTER = 4;
			}else{
				TYPE_GAME = TYPE_GAME_MOD_A;
				MONSTER = 0;
			}
			mBoyAniSprite.setCurrentTileIndex(0);
			mBoyAniSprite.setVisible(true);
			mSuperManLayer.swichBoyToSuperMan();
			ActionModGame();
		}
		
		private void showActionFalse(int pAction){
			mSuperManAniSprite[pAction].setVisible(false);
			hidenAllIteam();
			mSuperManSpriteFalse.setVisible(true);
			OGG_A4_9_YARARETA.play();
			mScene.unregisterUpdateHandler(mShowButtonHandler);
			mScene.registerUpdateHandler(mShowButtonHandler = new TimerHandler(1.5f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					viewButtonTrue();
				}
			}));
		}
		
		
	}
	
	private void monsterToScene(int pID){
		MONSTER_COUNT ++;
		mMonsterTowerAniSprite[pID].setVisible(false);
		mMonsterLayer.showInScene(pID);	
	}
	
	// Class Monster
	class MonsterLayer extends Entity{
		// TODO Monster Layer
		private VertexBufferObjectManager pBufferObjectManager;
		private AnimatedSprite mMonsterAniSprite[] = new AnimatedSprite[8];
		private AnimatedSprite mMonsterNameAniSprite;
		private TimerHandler mTimerHandler;
		private TimerHandler mItemAHandler;
	
		private int isSoundPlayAttach = 0;
		
		private Sprite  mShitLeftSprite[] = new Sprite[4];
		private Sprite  mShitRightSprite[] = new Sprite[4];
		private Sprite  mOfukuLeftSprite[] = new Sprite[4];
		private Sprite  mOfukuRightSprite[] = new Sprite[4];
		private Sprite  mNodoSprite[] = new Sprite[5];
		private Sprite  mFlashSprite;
		
		private  int POINT_NODO[][] = new int[][]{
			{372,334},
			{423,186},
			{771,122},
			{812,353},
			{707,243}
		};
		
		
		public MonsterLayer(final VertexBufferObjectManager vbo){
			this.pBufferObjectManager = vbo;
			for (int i = 0; i < mMonsterAniSprite.length; i++) {
				mMonsterAniSprite[i] = new AnimatedSprite(POINTER_EVIL[i][0], POINTER_EVIL[i][1], mMonsterTiledTR[i], pBufferObjectManager);
				this.attachChild(mMonsterAniSprite[i]);
				mMonsterAniSprite[i].setVisible(false);
			}
			
			float scale = 0.8f;
			float pY = 395;
			float pLeftX = 525;
			float pRightX = 685;
			for (int i = 0; i < mShitLeftSprite.length; i++) {
				mShitLeftSprite[i] = new Sprite(pLeftX, pY, mShitTR, pBufferObjectManager);
				mShitRightSprite[i] = new Sprite(pRightX, pY, mShitTR, pBufferObjectManager);
				mShitLeftSprite[i].setScale(scale);
				mShitLeftSprite[i].setScaleCenter(mShitTR.getWidth()/2, mShitTR.getHeight()/2);
				mShitRightSprite[i].setScale(scale);
				mShitRightSprite[i].setScaleCenter(mShitTR.getWidth()/2, mShitTR.getHeight()/2);
				this.attachChild(mShitLeftSprite[i]);
				this.attachChild(mShitRightSprite[i]);
				scale = scale + 0.11f;
				pY = pY + 20;
				pLeftX  = pLeftX - 20;
				pRightX = pRightX +20;
				mShitRightSprite[i].setVisible(false);
				mShitLeftSprite[i].setVisible(false);
			}
			
			scale = 0.8f;
			pLeftX = 380;
			pRightX = 755;
			float  pLeftY = 320;
			float pRightY = 320;
			final ITextureRegion iLeftTR = mCleanWindowTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.cleanWindow.A4_5_4_2_2_IPHONE_OFUKU_ID);
			final ITextureRegion iRightTR = mCleanWindowTp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.cleanWindow.A4_5_4_3_2_IPHONE_OFUKU_ID);
			
			for (int i = 0; i < mOfukuLeftSprite.length; i++) {
				mOfukuLeftSprite[i] = new Sprite(pLeftX, pLeftY, iLeftTR, pBufferObjectManager);
				mOfukuRightSprite[i] = new Sprite(pRightX, pRightY, iRightTR, pBufferObjectManager);
				mOfukuLeftSprite[i].setScale(scale);
				mOfukuLeftSprite[i].setScaleCenter(iLeftTR.getWidth()/2, iLeftTR.getHeight()/2);
				mOfukuRightSprite[i].setScale(scale);
				mOfukuRightSprite[i].setScaleCenter(iRightTR.getWidth()/2, iRightTR.getHeight()/2);
				this.attachChild(mOfukuLeftSprite[i]);
				this.attachChild(mOfukuRightSprite[i]);
				mOfukuLeftSprite[i].setVisible(false);
				mOfukuRightSprite[i].setVisible(false);
				scale = scale + 0.15f;
				pLeftX = pLeftX + 15;
				pLeftY = pLeftY + 75;
				pRightX = pRightX  - 15;
				pRightY = pRightY + 75;
			}
			
			for (int i = 0; i < mNodoSprite.length; i++) {
				mNodoSprite[i] = new Sprite(POINT_NODO[i][0], POINT_NODO[i][1], mNodoTR[i], pBufferObjectManager);
				this.attachChild(mNodoSprite[i]);
				mNodoSprite[i].setVisible(false);
			}

			mMonsterNameAniSprite = new AnimatedSprite(695, 434, mMonsterNameTiledTR, pBufferObjectManager);
			mMonsterNameAniSprite.setVisible(false);
			this.attachChild(mMonsterNameAniSprite);
			
			mFlashSprite = new Sprite(394, 129, mMiniItemIITp.getTexturePackTextureRegionLibrary().get(Vol3KizunaResource.miniItemII.A4_5_IPHONE_HIKARI_ID), pBufferObjectManager);
			this.attachChild(mFlashSprite);
			mFlashSprite.setVisible(false);
			
		}
		
		private void cleanAll(boolean pName){
			hidenAllItem();
			if(pName){
				mMonsterNameAniSprite.setVisible(false);
				clearUpdate(mMonsterNameAniSprite);
			}
			for (int i = 0; i < mMonsterAniSprite.length; i++) {
				mMonsterAniSprite[i].setVisible(false);
				mMonsterAniSprite[i].setScale(1.0f);
				clearUpdate(mMonsterAniSprite[i]);
			}
			
		}
		
		private void cleanMonster(){
				cleanAll(true);
		}
		
		private final  float TIME_DELAY_SHOW_BUTTON[] = new float[]{
				2.0f,1.5f,2.0f,3.5f,3.5f,2.0f,2.5f,3.0f
		};
		
		private final float POINT_MONSTER_TOWER[][] = new float[][]{
				{50,-70},
				{220,0},
				{320,-90},
				{600,-90},
				
				{65,-120},//
				{220,0},
				{320,-90},
				{570,-90},
				
		};
		
		public void showInScene(final int pIdMonster){
			mMonsterAniSprite[pIdMonster].registerEntityModifier(new ParallelEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					mMonsterAniSprite[pIdMonster].setCurrentTileIndex(0);
					mMonsterAniSprite[pIdMonster].setVisible(true);
					mMonsterAniSprite[pIdMonster].setScale(0.4f);
					mMonsterAniSprite[pIdMonster].setScaleCenter(mMonsterAniSprite[pIdMonster].getWidth()/2, mMonsterAniSprite[pIdMonster].getHeight()/2);
					if(pIdMonster == OSHIRI){
						OGG_A4_2_1_OSHIRIDA.play();
					}else if(pIdMonster == OJIRO){
						OGG_A4_2_2_OJIRO.play();
					}else{
						OGG_A4_5_TEKITOJO.play();
					}
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mMonsterNameAniSprite.setCurrentTileIndex(pIdMonster);
					mMonsterNameAniSprite.setVisible(true);
					OGG_MONSTER_SCENE[pIdMonster].play();
					MonsterLayer.this.unregisterUpdateHandler(mTimerHandler);
					MonsterLayer.this.registerUpdateHandler(mTimerHandler = new TimerHandler(TIME_DELAY_SHOW_BUTTON[pIdMonster], new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							actionAttach(pIdMonster, true);
							MonsterLayer.this.unregisterUpdateHandler(mTimerHandler);
							MonsterLayer.this.registerUpdateHandler(mTimerHandler = new TimerHandler(1.5f, new ITimerCallback() {
								
								@Override
								public void onTimePassed(TimerHandler arg0) {
									showTwoButtonAttach(pIdMonster);
								}
							}));
						}
					}));
				}
			}, 
			new ScaleModifier(1.1f, 0.4f, 1.0f),
			new MoveModifier(1.1f, 
					POINT_MONSTER_TOWER[pIdMonster][0],mMonsterAniSprite[pIdMonster].getX() , 
					POINT_MONSTER_TOWER[pIdMonster][1], mMonsterAniSprite[pIdMonster].getY())
			));
		}
		
		private void hidenAllItem(){
			MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
			for (int i = 0; i < mShitLeftSprite.length; i++) {
				clearUpdate(mShitLeftSprite[i]);
				clearUpdate(mShitRightSprite[i]);
				mShitLeftSprite[i].setVisible(false);
				mShitRightSprite[i].setVisible(false);
				
				clearUpdate(mOfukuLeftSprite[i]);
				clearUpdate(mOfukuRightSprite[i]);
				mOfukuLeftSprite[i].setVisible(false);
				mOfukuRightSprite[i].setVisible(false);
			}
			for (int i = 0; i < mNodoSprite.length; i++) {
				clearUpdate(mNodoSprite[i]);
				mNodoSprite[i].setVisible(false);
			}
			clearUpdate(mFlashSprite);
			mFlashSprite.setVisible(false);
		}
		
		private void actionAnger(final int idMonsterFace){
			hidenAllItem();
			OGG_MONSTER_TRUE[MONSTER].play();
			mMonsterAniSprite[MONSTER].setVisible(true);
			mMonsterAniSprite[MONSTER].stopAnimation(4);
			mMonsterAniSprite[MONSTER].registerEntityModifier(new ScaleModifier(1.2f, 1.2f, 1.2f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mMonsterAniSprite[MONSTER].setScale(1.0f);
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							actionAttach(MONSTER,false);
						}
					});
					//actionAttach(MONSTER,false);
					mSuperManLayer.showActionFalse(idMonsterFace);
				}
			}));
		}
		
		private SequenceEntityModifier getSequenceModifier(final AnimatedSprite pAnimatedSprite, final IEntityModifierListener pIEntityModifierListener){
			final SequenceEntityModifier pSequenceEntityModifier = new SequenceEntityModifier(pIEntityModifierListener , 
					new MoveXModifier(0.4f, pAnimatedSprite.getX() + 15, pAnimatedSprite.getX() - 15),
					new MoveXModifier(0.4f, pAnimatedSprite.getX() - 15, pAnimatedSprite.getX() +15),
					new MoveXModifier(0.3f, pAnimatedSprite.getX() + 15, pAnimatedSprite.getX() -10 ),
					new MoveXModifier(0.3f, pAnimatedSprite.getX() - 10 , pAnimatedSprite.getX() + 10 ),
					new MoveXModifier(0.2f, pAnimatedSprite.getX() + 10, pAnimatedSprite.getX() -5 ),
					new MoveXModifier(0.2f, pAnimatedSprite.getX() - 5 , pAnimatedSprite.getX() + 5 )
					);
			return pSequenceEntityModifier;
		}
		
		
		private void actionFall(final int pIdMonster){
			OGG_MONSTER_FALSE[MONSTER].play();
			hidenAllItem();
			mMonsterAniSprite[MONSTER].stopAnimation(3);
			mMonsterAniSprite[MONSTER].registerEntityModifier(getSequenceModifier(mMonsterAniSprite[MONSTER], new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mFlashSprite.registerEntityModifier(new LoopEntityModifier(new AlphaModifier(0.2f, 0.1f, 1.0f), 3, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							OGG_A4_5_TEKIKOUSEI.play();
							mMonsterAniSprite[MONSTER].setCurrentTileIndex(5);
							mFlashSprite.setVisible(true);
							mSuperManLayer.showWiner();
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mSuperManLayer.showActionTrue();
						}
					}));

				}
			}));
		}
		
		private void actionAttach(final int pIdMonster, final boolean pSound){
				cleanAll(false);
				isSoundPlayAttach = 0;
				switch (pIdMonster) {
				case OSHIRI:
					
					mMonsterAniSprite[OSHIRI].animate(new long[]{500,500}, new int[]{1,2},-1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mMonsterAniSprite[OSHIRI].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite aniSprite, int arg1, int arg2) {
							Log.d(TAG, "ID CHANGE: " + arg1);
							if(isSoundPlayAttach <= 1 && pSound){
								if(arg1 == 1 || arg1 == -1){
									OGG_A4_5_1_2_1.play(); 
								}else if(arg1 == 0){
									OGG_A4_5_1_2_2.play();
								}
								isSoundPlayAttach ++;
							}
							if(arg1 == 1  || arg1 == -1){
								mShitLeftSprite[0].setVisible(false);
								mShitLeftSprite[1].setVisible(false);
								mShitLeftSprite[2].setVisible(false);
								mShitLeftSprite[3].setVisible(false);
								MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
								MonsterLayer.this.registerUpdateHandler(mItemAHandler = new TimerHandler(0.12f, true, new ITimerCallback() {
									int count = 0;
									@Override
									public void onTimePassed(TimerHandler arg0) {
										if(count <= 3){
											final int idCount = count;
											mShitRightSprite[idCount].setVisible(true);
										}
										count ++;
									}
								}));
							}else if(arg1 == 0){
								mShitRightSprite[0].setVisible(false);
								mShitRightSprite[1].setVisible(false);
								mShitRightSprite[2].setVisible(false);
								mShitRightSprite[3].setVisible(false);
								MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
								MonsterLayer.this.registerUpdateHandler(mItemAHandler = new TimerHandler(0.12f, true, new ITimerCallback() {
									int count = 0;
									@Override
									public void onTimePassed(TimerHandler arg0) {
										if(count <= 3){
											final int idCount = count;
											mShitLeftSprite[idCount].setVisible(true);
										}
										count ++;
									}
								}));
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
						}
					});
					break;

				case OJIRO:
					mMonsterAniSprite[OJIRO].animate(new long[]{500,600}, new int[]{1,2},-1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mMonsterAniSprite[OJIRO].setVisible(true);
							if(arg1 == 1 && pSound ){
								OGG_A4_5_2_2_3.play();
							}
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
								if(arg1 == 1){
									mMonsterAniSprite[OJIRO].setScale(1.0f);
									mMonsterAniSprite[OJIRO].clearEntityModifiers();
								}else if(arg1 == 0){
									mMonsterAniSprite[OJIRO].registerEntityModifier(new ScaleModifier(0.5f, 1.0f, 1.2f));
								}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							
						}
					});
					break;
				case OTEMAJIN:
					mMonsterAniSprite[OTEMAJIN].animate(new long[]{500,500}, new int[]{1,2},-1, new IAnimationListener() {
						int count = 0;
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mMonsterAniSprite[OTEMAJIN].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							if(arg1 == 2 && pSound && count ==0){
								OGG_A4_5_3_2_SORE.play();
								OGG_A4_5_3_2_OTEJIMAN.play();
								count ++;
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							
						}
					});
					break;
				case OFUKU:
					isSoundPlayAttach = 0;
					mMonsterAniSprite[OFUKU].animate(new long[]{600,600}, new int[]{1,2},-1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
							mMonsterAniSprite[OFUKU].setVisible(true);
							
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							if(isSoundPlayAttach <= 1 && pSound){
								if(arg1 == 1 || arg1 == -1){
									OGG_A4_5_4_1_3.play(); 
								}else if(arg1 == 0){
									OGG_A4_5_1_2_2.play();
								}
								isSoundPlayAttach ++;
							}
							
							if(arg1 == 1 || arg1 == -1){
								mOfukuRightSprite[0].setVisible(false);
								mOfukuRightSprite[1].setVisible(false);
								mOfukuRightSprite[2].setVisible(false);
								mOfukuRightSprite[3].setVisible(false);
								MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
								MonsterLayer.this.registerUpdateHandler(mItemAHandler = new TimerHandler(0.12f, true, new ITimerCallback() {
									int count = 0;
									@Override
									public void onTimePassed(TimerHandler arg0) {
										if(count <= 3){
											final int idCount = count;
											mOfukuLeftSprite[idCount].setVisible(true);
										}
										count ++;
									}
								}));
							}else{
								mOfukuLeftSprite[0].setVisible(false);
								mOfukuLeftSprite[1].setVisible(false);
								mOfukuLeftSprite[2].setVisible(false);
								mOfukuLeftSprite[3].setVisible(false);
								MonsterLayer.this.unregisterUpdateHandler(mItemAHandler);
								MonsterLayer.this.registerUpdateHandler(mItemAHandler = new TimerHandler(0.12f, true, new ITimerCallback() {
									int count = 0;
									@Override
									public void onTimePassed(TimerHandler arg0) {
										if(count <= 3){
											final int idCount = count;
											mOfukuRightSprite[idCount].setVisible(true);
										}
										count ++;
									}
								}));
							}
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							
						}
					});
					break;
				case MUSHIBAIKIN:
					mMonsterAniSprite[MUSHIBAIKIN].animate(new long[]{500,500}, new int[]{1,2}, -1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							if(pSound){
								OGG_A4_5_5_2_3.play();
							}
							mMonsterAniSprite[MUSHIBAIKIN].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							
						}
					});
					break;
				case OTARO:
					mMonsterAniSprite[OTARO].animate(new long[]{500,500}, new int[]{1,2}, -1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							if(pSound){
								OGG_A4_5_6_2_YOGOSU.play();
								OGG_A4_5_6_2_OTARO.play();
							}
							mMonsterAniSprite[OTARO].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							
						}
					});
					break;
				case GOMIRA:
					mMonsterAniSprite[GOMIRA].animate(new long[]{500,500}, new int[]{1,2}, -1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							if(pSound){
								OGG_A4_5_6_2_YOGOSU.play();
								OGG_A4_5_6_2_OTARO.play();
							}
							mMonsterAniSprite[GOMIRA].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							if(isSoundPlayAttach <= 1 && pSound){
								if(paramInt1 == 1 || paramInt1 == -1){
									OGG_A4_5_7_2_UO.play(); 
								}else{
									OGG_A4_5_7_2_GOMIRA.play();
								}
								isSoundPlayAttach ++;
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							
						}
					});
					break;
				case NODOBAIKIN:
					mMonsterAniSprite[NODOBAIKIN].animate(new long[]{500,600},new int[]{1,2},-1, new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
								int paramInt) {
							mMonsterAniSprite[NODOBAIKIN].setVisible(true);
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) { 
							if(isSoundPlayAttach <= 1 && pSound){
								if(paramInt1 == 1 || paramInt1 == -1){
									OGG_A4_5_8_2_IGA.play(); 
								}else{
									OGG_A4_5_8_2_NODOBAIKIN.play();
								}
								isSoundPlayAttach ++;
							}
							if(paramInt1 == 1 || paramInt1 == -1){
									
							}else{
											for (int i = 0; i < mNodoSprite.length; i++) {
												final int idIndex = i;
												mNodoSprite[i].registerEntityModifier(new ScaleModifier(0.5f, 0.9f, 1.1f, new IEntityModifierListener() {
													
													@Override
													public void onModifierStarted(IModifier<IEntity> paramIModifier,
															IEntity paramT) {
														mNodoSprite[idIndex].setVisible(true);
													}
													
													@Override
													public void onModifierFinished(IModifier<IEntity> paramIModifier,
															IEntity paramT) {
														mNodoSprite[idIndex].setVisible(false);
													}
												}));
											}
							}
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
							
						}
					});
					break;
				}
		}
		
	}
	
	private void clearUpdate(IEntity pEntity){
		pEntity.clearEntityModifiers();
		pEntity.clearUpdateHandlers();
		if(pEntity instanceof AnimatedSprite){
			((AnimatedSprite) pEntity).stopAnimation();
		}
	}

	// Class Button
	 class ButtonChoose extends AnimatedSprite{
		 
		private int idIndex = - 1;
		 
		public ButtonChoose(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		}
		
		private int getIndex(){
			return this.idIndex;
		}
		
		private void setIndex(int pIndex){
			this.idIndex = pIndex;
		}
		 
	 }
	

}
