package jp.co.xing.utaehon03.songs;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3PrikyuaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
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
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Prikyua extends BaseGameFragment implements IOnSceneTouchListener{
	//Define tag log for this class
    private String TAG = Vol3Prikyua.this.getClass().toString();
    //Texture Packer
    private TexturePackLoaderFromSource mTexturePackLoaderFromSourcePrikyua;
    private TexturePack Vol3PrikyuaPacker_1, Vol3PrikyuaPacker_2, Vol3PrikyuaPacker_3, Vol3PrikyuaPacker_4;
    private TexturePackTextureRegionLibrary Vol3PrikyuaLibrary1, Vol3PrikyuaLibrary2, Vol3PrikyuaLibrary3, Vol3PrikyuaLibrary4;
    //Background and Start
    private ITextureRegion iTextBackground, iTextBase, iTextLine, iTextStart, iTextStart_Text;
    private Sprite pSpriteBackground, pSpriteBase, pSpriteLine, pSpriteStart, pSpriteStart_Text;
    //Actor
    private ITiledTextureRegion iTiledLeftAll, iTiledRighAll;
    private AnimatedSprite pAnimatedLeftAll, pAnimatedRightAll;
    //Setsumon
    private ITextureRegion iTextSetsumonBg;
    private Sprite pSpriteSetsumonBg;
    private ITiledTextureRegion iTiledSetsumonAll;
    private AnimatedSprite pAnimatedSetsumonAll;
    //Beauty
    private ITextureRegion iTextBeautyTentoumusi, iTextBeautyKirakira, iTextBeautyKaban, iTextBeautyYubiwa;
    private Sprite pSpriteBeauty[] = new Sprite[4];
    private ITiledTextureRegion iTiledBeautyHidariashi,iTiledBeautyMigiashi,iTiledBeautyKami,iTiledBeautyKuchi;
    private AnimatedSprite pAnimatedBeauty[] = new AnimatedSprite[4];
    private boolean booleanBeauty[] = {false,false,false,false,false,false,false,false};
    //Happy
    private ITextureRegion iTextHappyHana, iTextHappyTama, iTextHappyHoshi, iTextHappyHaert, iTextHappyAtamahane;
    private Sprite pSpriteHappy[] = new Sprite[5];
    private ITiledTextureRegion iTiledHappyHidariashi, iTiledHappyMigiashi, iTiledHappyCandy;
    private AnimatedSprite pAnimatedHappy[] = new AnimatedSprite[3];
    private boolean booleanHappy[] = {false,false,false,false,false,false,false,false};
    //March
    private ITiledTextureRegion iTiledMarchKami,iTiledMarchRibon,iTiledMarchMigiashi,iTiledMarchHidariashi,iTiledMarchPosyetto,iTiledMarchHidarite,iTiledMarchMigite,iTiledMarchKuchi;
    private AnimatedSprite pAnimatedMarch[] = new AnimatedSprite[8];
    private boolean booleanMarch[] = {false,false,false,false,false,false,false,false};
    //Piece
    private ITextureRegion iTextPieceBansoukou, iTextPieceYubiwa, iTextPieceHart;
    private Sprite pSpritePiece[] = new Sprite[3];
    private ITiledTextureRegion iTiledPieceSukato,iTiledPiecePosyetto,iTiledPieceTe,iTiledPieceKuchi,iTiledPieceCandy;
    private AnimatedSprite pAnimatedPiece[] = new AnimatedSprite[5];
    private boolean booleanPiece[] = {false,false,false,false,false,false,false,false};
    //Sunny
    private ITextureRegion iTextSunnyFukunohana, iTextSunnyTenoohana, iTextSunnyAme;
    private Sprite pSpriteSunny[] = new Sprite[3];
    private ITiledTextureRegion iTiledSunnyAshiribon,iTiledSunnyFukunoribon,iTiledSunnyKata,iTiledSunnyKuchi,iTiledSunnyMigite,iTiledSunnyKami;
    private AnimatedSprite pAnimatedSunny[] = new AnimatedSprite[6];
    private boolean booleanSunny[] = {false,false,false,false,false,false,false,false,false};
    //Atari
    private ITextureRegion iTextAtari;
    private AtariSprite pSpriteAtari;
	private LinkedList<Sprite> listpSpriteAtari = new LinkedList<Sprite>();
	//Tyuukan
	private ITextureRegion iTextTyuukan;
    private Sprite pSpriteTyuukan;
    //Find Sprite Prikyua
    private AnimatedSpritePrikyua findAnimatedSprite[] = new AnimatedSpritePrikyua[5];
    private ITiledTextureRegion iTiledFindAnimatedSprite[] = new ITiledTextureRegion[5];

    private boolean findAnimatedPrikyua[] = {false,false,false,false,false};
    //////
    //Hikari
    private ITextureRegion iTextHikari;
    private AtariSprite pSpriteHikari;
	private LinkedList<Sprite> listpSpriteHikari = new LinkedList<Sprite>();
	//Complete
	private ITextureRegion iTextComplete;
	private Sprite pSpriteComplete;
    //Hikari last
	private TextureRegion iTextHikariLast[] = new TextureRegion[5];
	private AtariSprite pSpriteHikariLastPool[] = new AtariSprite[5];
	private LinkedList<Sprite>listpSpriteHikariLast[] = new LinkedList[5];
	 //Start
    private int  pLastX = 0;
	private int  pLastY = 0;
	private float arrPointStar[][]= {
			{0, -60, -60, -60, 0, 60, 60, 60},
			{-60, -60, 0, 60, 60, 60, 0, -60}
	};
    private RanDomNoRepeat randNoRepeat, randBeauty, randHappy, randMarch, randPiece, randSunny, gimicFind;
    private int randomPrikyua;
    private int countPrikyua = 5, countChange = 0, countFindSprite = 5, coutTouchFind = 0, soundMod = 0;
    private int showError,showFind;
    private boolean booleanPrikyua = false, booleanIsTouchGimic = false, booleanIsTouchFind = false, booleanSumon = false;
    //Sound
    private Sound A1_KONNICHIWA,A1_KAGEKIERU2,A1_DOKOGATIGAU,A1_ONAJIKATATI,A1_HINTOGADERU,
	A1_YOKUMITENE,A1_D_PACHI_YUBI,A1_ZANNEN,A1_3SYARA,A1_TENKAN,A1_TUGIHASIRUETO,
	A1_TIGAUTOKORO,A1_SUTEKI,A1_HITOTSUKIERU,A1_SAISYOHA,A1_YATANE_OMEDETO,
	A1_START,A1_ATOSUKOSHI,A1_SEIKAI,A1_KIRAN;
    private TimerHandler timer4s, timerDifference6s, timerFinded6s,timerDifference, timerFinded;
    private boolean isDifference6s = true, isFinded6s = true;
    private boolean beginGame;
    //TODO Create Resource
    @Override
    public void onCreateResources() {
        Log.d(TAG,"onCreateRerource");
        //Create Sound Factory
        SoundFactory.setAssetBasePath("prikyua/mfx/");
        //Create Bitmap Factory
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("prikyua/gfx/");
        //Create Texture Packer
        mTexturePackLoaderFromSourcePrikyua = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "prikyua/gfx/");
        super.onCreateResources();
    }
	@Override
	protected void loadKaraokeResources() {
		 Log.d(TAG,"loadKaraokeResources");
		// TODO Auto-generated method stub
		//Load Texture Packer
		Vol3PrikyuaPacker_1 = mTexturePackLoaderFromSourcePrikyua.load("Vol3PrikyuaPacker1.xml");
		Vol3PrikyuaPacker_1.loadTexture();
		Vol3PrikyuaLibrary1 = Vol3PrikyuaPacker_1.getTexturePackTextureRegionLibrary();
		
		Vol3PrikyuaPacker_2 = mTexturePackLoaderFromSourcePrikyua.load("Vol3PrikyuaPacker2.xml");
		Vol3PrikyuaPacker_2.loadTexture();
		Vol3PrikyuaLibrary2 = Vol3PrikyuaPacker_2.getTexturePackTextureRegionLibrary();
		
		Vol3PrikyuaPacker_3 = mTexturePackLoaderFromSourcePrikyua.load("Vol3PrikyuaPacker3.xml");
		Vol3PrikyuaPacker_3.loadTexture();
		Vol3PrikyuaLibrary3 = Vol3PrikyuaPacker_3.getTexturePackTextureRegionLibrary();
		
		Vol3PrikyuaPacker_4 = mTexturePackLoaderFromSourcePrikyua.load("Vol3PrikyuaPacker4.xml");
		Vol3PrikyuaPacker_4.loadTexture();
		Vol3PrikyuaLibrary4 = Vol3PrikyuaPacker_4.getTexturePackTextureRegionLibrary();
		
		iTextBackground = Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.Vol3PrikyuaPacker1.BACKGROUND_IPHONE_ID);
		
		iTextBase = Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.Vol3PrikyuaPacker1.BASE_IPHONE_ID);
		
		iTextLine = Vol3PrikyuaLibrary2.get(Vol3PrikyuaResource.Vol3PrikyuaPacker2.LINE_IPHONE_ID);
		
		iTiledLeftAll = getTiledTextureFromPacker(Vol3PrikyuaPacker_2, 
				Vol3PrikyuaResource.Vol3PrikyuaPacker2.LEFT_BEAUTY_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker2.LEFT_HAPPY_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker2.LEFT_MARCH_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker2.LEFT_PIECE_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker2.LEFT_SUNNY_IPHONE_ID);
		
		iTiledRighAll = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_MAIN_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_MAIN_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_MAIN_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_MAIN_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_MAIN_IPHONE_ID
				);
		iTextSetsumonBg = Vol3PrikyuaLibrary4.get(Vol3PrikyuaResource.Vol3PrikyuaPacker4.SETSUMON_IPHONE_ID);
		
		iTiledSetsumonAll = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SETSUMON_HINTO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SETSUMON_KIERU_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SETSUMON_ONAJI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SETSUMON_TIGU_IPHONE_ID
				);
		
		iTextStart = Vol3PrikyuaLibrary4.get(Vol3PrikyuaResource.Vol3PrikyuaPacker4.START_IPHONE_ID);
		
		iTextStart_Text = Vol3PrikyuaLibrary4.get(Vol3PrikyuaResource.Vol3PrikyuaPacker4.START_TEXT_IPHONE_ID);
		//Beauty
		iTextBeautyTentoumusi = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_01TENTOUMUSI_IPHONE_ID);
		iTextBeautyKirakira = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_06KIRAKIRA_IPHONE_ID);
		iTextBeautyKaban = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_07KABAN_IPHONE_ID);
		iTextBeautyYubiwa = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_08YUBIWA_IPHONE_ID);
		
		iTiledBeautyHidariashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_02HIDARIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_02HIDARIASHI_YES_IPHONE_ID
				);
		iTiledBeautyMigiashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_03MIGIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_03MIGIASHI_YES_IPHONE_ID
				);
		iTiledBeautyKami = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_04KAMI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_04KAMI_YES_IPHONE_ID
				);
		iTiledBeautyKuchi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_05KUCHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_BEAUTY_05KUCHI_YES_IPHONE_ID
				);
		//Happy
		iTextHappyHana = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_03HANA_IPHONE_ID);
		iTextHappyTama = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_04TAMA_IPHONE_ID);
		iTextHappyHoshi = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_05HOSHI_IPHONE_ID);
		iTextHappyHaert = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_07HAERT_IPHONE_ID);
		iTextHappyAtamahane = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_08ATAMAHANE_IPHONE_ID);
	    
		iTiledHappyMigiashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_01MIGIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_01MIGIASHI_YES_IPHONE_ID
				);
		iTiledHappyHidariashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_02HIDARIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_02HIDARIASHI_YES_IPHONE_ID
				);
		iTiledHappyCandy = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_06CANDY_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_HAPPY_06CANDY_YES_IPHONE_ID
				);
		//March
		iTiledMarchKami = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_01KAMI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_01KAMI_YES_IPHONE_ID
				);
		iTiledMarchRibon = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_02RIBON_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_02RIBON_YES_IPHONE_ID
				);
		iTiledMarchMigiashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_03MIGIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_03MIGIASHI_YES_IPHONE_ID
				);
		iTiledMarchHidariashi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_04HIDARIASHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_04HIDARIASHI_YES_IPHONE_ID
				);
		iTiledMarchPosyetto = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_05POSYETTO_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_05POSYETTO_YES_IPHONE_ID
				);
		iTiledMarchHidarite = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_06HIDARITE_NO1_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_06HIDARITE_NO2_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_06HIDARITE_YES_IPHONE_ID
				);
		iTiledMarchMigite = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_07MIGITE_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_07MIGITE_YES_IPHONE_ID
				);
		iTiledMarchKuchi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_08KUCHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_MARCH_08KUCHI_YES_IPHONE_ID
				);
		//Piece
		iTextPieceBansoukou = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_04BANSOUKOU_IPHONE_ID);
		iTextPieceYubiwa = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_05YUBIWA_IPHONE_ID);
		iTextPieceHart = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_08HART_IPHONE_ID);
	    
		iTiledPieceSukato = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_01SUKATO_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_01SUKATO_YES_IPHONE_ID
				);
		iTiledPiecePosyetto = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_02POSYETTO_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_02POSYETTO_YES_IPHONE_ID
				);
		iTiledPieceTe = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_03TE_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_03TE_YES_IPHONE_ID
				);
		iTiledPieceKuchi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_06KUCHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_06KUCHI_YES_IPHONE_ID
				);
		iTiledPieceCandy = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_07CANDY_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_PIECE_07CANDY_YES_IPHONE_ID
				);
	    
		//Sunny
		iTextSunnyFukunohana = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_02FUKUNOHANA_IPHONE_ID);
		iTextSunnyTenoohana = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_04TENOOHANA_IPHONE_ID);
		iTextSunnyAme = Vol3PrikyuaLibrary3.get(Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_08AME_IPHONE_ID);
	    
	    iTiledSunnyAshiribon = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_01ASHIRIBON_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_01ASHIRIBON_YES_IPHONE_ID
				);
	    iTiledSunnyFukunoribon = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_03FUKUNORIBON_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_03FUKUNORIBON_YES_IPHONE_ID
				);
	    iTiledSunnyKata = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_05KATA_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_05KATA_YES_IPHONE_ID
				);
	    iTiledSunnyKuchi = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_06KUCHI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_06KUCHI_YES_IPHONE_ID
				);
	    iTiledSunnyMigite = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_07MIGITE_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_07MIGITE_YES_IPHONE_ID
				);
	    iTiledSunnyKami = getTiledTextureFromPacker(Vol3PrikyuaPacker_3,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_09KAMI_NO_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker3.MATIGAI_SUNNY_09KAMI_YES_IPHONE_ID
				);
	    //Atari
	    iTextAtari = Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.Vol3PrikyuaPacker1.ATARI_IPHONE_ID);
	    iTextTyuukan = Vol3PrikyuaLibrary4.get(Vol3PrikyuaResource.Vol3PrikyuaPacker4.TYUUKAN_IPHONE_ID);
	    
	    //Find AnimatedSprite 
	    iTiledFindAnimatedSprite[0] = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_BEAUTY_ATARI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_BEAUTY_KAGE_IPHONE_ID
				);
	    iTiledFindAnimatedSprite[1] = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_HAPPY_ATARI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_HAPPY_KAGE_IPHONE_ID
				);
	    iTiledFindAnimatedSprite[2] = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_MARCH_ATARI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_MARCH_KAGE_IPHONE_ID
				);
	    iTiledFindAnimatedSprite[3] = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_PIECE_ATARI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_PIECE_KAGE_IPHONE_ID
				);
	    iTiledFindAnimatedSprite[4] = getTiledTextureFromPacker(Vol3PrikyuaPacker_4,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_SUNNY_ATARI_IPHONE_ID,
				Vol3PrikyuaResource.Vol3PrikyuaPacker4.SIRUETTO_SUNNY_KAGE_IPHONE_ID
				);
	    iTextHikari = Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.Vol3PrikyuaPacker1.HIKARI_IPHONE_ID);
	    //Complete
	    iTextComplete = Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.Vol3PrikyuaPacker1.COMPLETE_IPHONE_ID);
	    //Hitari Star
	    final int length1 = iTextHikariLast.length;
	    for (int i = 0; i < length1; i++) {
			iTextHikariLast[i]= Vol3PrikyuaLibrary1.get(Vol3PrikyuaResource.STAR_COMPLETE[i]);
		}
	}
	@Override
	protected void loadKaraokeSound() {
		Log.d(TAG,"loadKaraokeSound");
		A1_KONNICHIWA = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_KONNICHIWA);
		A1_KAGEKIERU2 = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_KAGEKIERU2);
		A1_DOKOGATIGAU = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_DOKOGATIGAU);
		A1_ONAJIKATATI = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_ONAJIKATATI);
		A1_HINTOGADERU = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_HINTOGADERU);
		A1_YOKUMITENE = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_YOKUMITENE);
		A1_D_PACHI_YUBI = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_D_PACHI_YUBI);
		A1_ZANNEN = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_ZANNEN);
		A1_3SYARA = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_3SYARA);
		A1_TENKAN = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_TENKAN);
		A1_TUGIHASIRUETO = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_TUGIHASIRUETO);
		A1_TIGAUTOKORO = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_TIGAUTOKORO);
		A1_SUTEKI = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_SUTEKI);
		A1_HITOTSUKIERU = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_HITOTSUKIERU);
		A1_SAISYOHA = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_SAISYOHA);
		A1_YATANE_OMEDETO = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_YATANE_OMEDETO);
		A1_START = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_START);
		A1_ATOSUKOSHI = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_ATOSUKOSHI);
		A1_SEIKAI = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_SEIKAI);
		A1_KIRAN = loadSoundResourceFromSD(Vol3PrikyuaResource.A1_KIRAN);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG,"loadKaraokeScene");
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		//Create Random
		randNoRepeat = new RanDomNoRepeat();
		randNoRepeat.setLenghNoRepeat(5);
		gimicFind = new RanDomNoRepeat();
		gimicFind.setLenghNoRepeat(5);
		randBeauty = new RanDomNoRepeat();
		randBeauty.setLenghNoRepeat(6);
		randHappy = new RanDomNoRepeat();
		randHappy.setLenghNoRepeat(6);
		randMarch = new RanDomNoRepeat();
		randMarch.setLenghNoRepeat(6);
		randPiece = new RanDomNoRepeat();
		randPiece.setLenghNoRepeat(6);
		randSunny = new RanDomNoRepeat();
		randSunny.setLenghNoRepeat(6);
		
		pSpriteBackground = new Sprite(0, 0, iTextBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteBackground);
		
		pSpriteBase = new Sprite(503, 15, iTextBase, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteBase);
		pSpriteBase.setVisible(false);
		pSpriteLine = new Sprite(461, 0, iTextLine, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteLine);
		pSpriteLine.setVisible(false);
		pAnimatedLeftAll = new AnimatedSprite(0, 0, iTiledLeftAll, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedLeftAll);
		pAnimatedLeftAll.setVisible(false);
		pAnimatedRightAll = new AnimatedSprite(503, 15, iTiledRighAll, this.getVertexBufferObjectManager());
		//Beauty
		//Sprite
		pSpriteBeauty[0] = new Sprite(281, 523, iTextBeautyTentoumusi, this.getVertexBufferObjectManager());
		pSpriteBeauty[1] = new Sprite(130, 139, iTextBeautyKirakira, this.getVertexBufferObjectManager());
		pSpriteBeauty[2] = new Sprite(242, 75, iTextBeautyKaban, this.getVertexBufferObjectManager());
		pSpriteBeauty[3] = new Sprite(247, 19, iTextBeautyYubiwa, this.getVertexBufferObjectManager());
		//AnimatedSprite
	    pAnimatedBeauty[0] = new AnimatedSprite(99.5f, 340, iTiledBeautyHidariashi, this.getVertexBufferObjectManager());//quan trai
	    pAnimatedBeauty[0].setSize(pAnimatedBeauty[0].getWidth()+1,  pAnimatedBeauty[0].getHeight()+1);
	    
		pAnimatedBeauty[1] = new AnimatedSprite(194.5f, 400, iTiledBeautyMigiashi, this.getVertexBufferObjectManager());//quan phai
		pAnimatedBeauty[1].setSize(pAnimatedBeauty[1].getWidth(),  pAnimatedBeauty[1].getHeight()+1.5f);
		
		pAnimatedBeauty[2] = new AnimatedSprite(281.5f, 202.5f, iTiledBeautyKami, this.getVertexBufferObjectManager());//canh ao
		pAnimatedBeauty[2].setSize(pAnimatedBeauty[2].getWidth()+1,  pAnimatedBeauty[2].getHeight()+1);
		
		pAnimatedBeauty[3] = new AnimatedSprite(183.5f, 186.9f, iTiledBeautyKuchi, this.getVertexBufferObjectManager());//mieng
		pAnimatedBeauty[3].setSize(pAnimatedBeauty[3].getWidth()+1,  pAnimatedBeauty[3].getHeight()+2.5f);
		for(int i = 0; i < 4; i++){
			pAnimatedRightAll.attachChild(pSpriteBeauty[i]);
			pAnimatedRightAll.attachChild(pAnimatedBeauty[i]);
		}
	    /////////
		//Happy
		//Sprite
		pSpriteHappy[0] = new Sprite(184, 259, iTextHappyHana, this.getVertexBufferObjectManager());
		pSpriteHappy[1] = new Sprite(80, 236, iTextHappyTama, this.getVertexBufferObjectManager());
		pSpriteHappy[2] = new Sprite(264, 224, iTextHappyHoshi, this.getVertexBufferObjectManager());
		pSpriteHappy[3] = new Sprite(170, 63, iTextHappyHaert, this.getVertexBufferObjectManager());
		pSpriteHappy[4] = new Sprite(126, 100, iTextHappyAtamahane, this.getVertexBufferObjectManager());
	    //AnimatedSprite
		pAnimatedHappy[0] = new AnimatedSprite(162, 285, iTiledHappyMigiashi, this.getVertexBufferObjectManager());
		pAnimatedHappy[0].setSize(pAnimatedHappy[0].getWidth(),  pAnimatedHappy[0].getHeight()+2);
		
		pAnimatedHappy[1] = new AnimatedSprite(185, 285, iTiledHappyHidariashi, this.getVertexBufferObjectManager());
		pAnimatedHappy[1].setSize(pAnimatedHappy[1].getWidth()+0.5f,  pAnimatedHappy[1].getHeight()+2);
		
		pAnimatedHappy[2] = new AnimatedSprite(2, 163, iTiledHappyCandy, this.getVertexBufferObjectManager());
		pAnimatedHappy[2].setSize(pAnimatedHappy[2].getWidth(),  pAnimatedHappy[2].getHeight()+1);
		
		for(int i = 0; i < 5; i++){
			pAnimatedRightAll.attachChild(pSpriteHappy[i]);
			if(i<=2){
				pAnimatedRightAll.attachChild(pAnimatedHappy[i]);
			}
		}
		///////////////
		//March
		//AnimatedSprite
		//chan
	    pAnimatedMarch[0] = new AnimatedSprite(211.5f, 433, iTiledMarchKami, this.getVertexBufferObjectManager());
	    pAnimatedMarch[0].setSize(pAnimatedMarch[0].getWidth()+1,  pAnimatedMarch[0].getHeight()+1);
	    
		pAnimatedMarch[1] = new AnimatedSprite(80, 497, iTiledMarchRibon, this.getVertexBufferObjectManager());
		//dui
		pAnimatedMarch[2] = new AnimatedSprite(161, 360, iTiledMarchMigiashi, this.getVertexBufferObjectManager());
		pAnimatedMarch[2].setSize(pAnimatedMarch[2].getWidth()+2,  pAnimatedMarch[2].getHeight()+1.25f);
		
		pAnimatedMarch[3] = new AnimatedSprite(281, 323, iTiledMarchHidariashi, this.getVertexBufferObjectManager());
		pAnimatedMarch[3].setSize(pAnimatedMarch[3].getWidth()+2,  pAnimatedMarch[3].getHeight()+0.5f);
		//vay
		pAnimatedMarch[4] = new AnimatedSprite(177.5f, 304.5f, iTiledMarchPosyetto, this.getVertexBufferObjectManager());
		pAnimatedMarch[4].setSize(pAnimatedMarch[4].getWidth()+1.0f,  pAnimatedMarch[4].getHeight()+1.0f);
		//tay
		pAnimatedMarch[5] = new AnimatedSprite(286, 258, iTiledMarchHidarite, this.getVertexBufferObjectManager());
		
		pAnimatedMarch[6] = new AnimatedSprite(181, 211.5f, iTiledMarchMigite, this.getVertexBufferObjectManager());
		pAnimatedMarch[6].setSize(pAnimatedMarch[6].getWidth()+2,  pAnimatedMarch[6].getHeight()+1.5f);
		//mieng
		pAnimatedMarch[7] = new AnimatedSprite(198, 173, iTiledMarchKuchi, this.getVertexBufferObjectManager());
		pAnimatedMarch[7].setSize(pAnimatedMarch[7].getWidth()+2,  pAnimatedMarch[7].getHeight()+1);
		
		for(int i = 0; i < 8; i++){
			pAnimatedRightAll.attachChild(pAnimatedMarch[i]);
		}
		
		///////////////
		//Piece
	    pSpritePiece[0] = new Sprite(277, 464, iTextPieceBansoukou, this.getVertexBufferObjectManager());
		pSpritePiece[1] = new Sprite(132, 221, iTextPieceYubiwa, this.getVertexBufferObjectManager());
		pSpritePiece[2] = new Sprite(311, 139, iTextPieceHart, this.getVertexBufferObjectManager());
	    //AnimatedSprite
	    pAnimatedPiece[0] = new AnimatedSprite(88, 357, iTiledPieceSukato, this.getVertexBufferObjectManager());
	    pAnimatedPiece[0].setSize(pAnimatedPiece[0].getWidth()+1,  pAnimatedPiece[0].getHeight()+2);
	    
		pAnimatedPiece[1] = new AnimatedSprite(147, 356.5f, iTiledPiecePosyetto, this.getVertexBufferObjectManager());
		pAnimatedPiece[1].setSize(pAnimatedPiece[1].getWidth()+1,  pAnimatedPiece[1].getHeight()+1);
		
		pAnimatedPiece[2] = new AnimatedSprite(260, 291, iTiledPieceTe, this.getVertexBufferObjectManager());
		pAnimatedPiece[2].setSize(pAnimatedPiece[2].getWidth()+2,  pAnimatedPiece[2].getHeight()+2);
		
		pAnimatedPiece[3] = new AnimatedSprite(211, 214, iTiledPieceKuchi, this.getVertexBufferObjectManager());
		pAnimatedPiece[3].setSize(pAnimatedPiece[3].getWidth()+2,  pAnimatedPiece[3].getHeight()+2);
		
		pAnimatedPiece[4] = new AnimatedSprite(70, 54, iTiledPieceCandy, this.getVertexBufferObjectManager());
		pAnimatedPiece[4].setSize(pAnimatedPiece[4].getWidth()+2,  pAnimatedPiece[4].getHeight()+1.5f);
		
		for(int i = 0; i < 5; i++){
			pAnimatedRightAll.attachChild(pAnimatedPiece[i]);
			if(i<=2){
				pAnimatedRightAll.attachChild(pSpritePiece[i]);
			}
		}
		///////////////
		//Sunny
		pSpriteSunny[0] = new Sprite(232, 334, iTextSunnyFukunohana, this.getVertexBufferObjectManager());
		pSpriteSunny[1] = new Sprite(130, 228, iTextSunnyTenoohana, this.getVertexBufferObjectManager());
		pSpriteSunny[2] = new Sprite(271, 72, iTextSunnyAme, this.getVertexBufferObjectManager());
		//AnimatedSprite
		//Chan
	    pAnimatedSunny[0] = new AnimatedSprite(146, 434, iTiledSunnyAshiribon, this.getVertexBufferObjectManager());
	    //Gui tay
		pAnimatedSunny[1] = new AnimatedSprite(312, 263, iTiledSunnyFukunoribon, this.getVertexBufferObjectManager());
		//ba vai
		pAnimatedSunny[2] = new AnimatedSprite(242, 214, iTiledSunnyKata, this.getVertexBufferObjectManager());
		pAnimatedSunny[2].setSize(pAnimatedSunny[2].getWidth()+1,  pAnimatedSunny[2].getHeight()+2);
		//Mieng
		pAnimatedSunny[3] = new AnimatedSprite(215, 199, iTiledSunnyKuchi, this.getVertexBufferObjectManager());
		pAnimatedSunny[3].setSize(pAnimatedSunny[3].getWidth()+2,  pAnimatedSunny[3].getHeight()+1);
		//Tay phai
		pAnimatedSunny[4] = new AnimatedSprite(202, 49, iTiledSunnyMigite, this.getVertexBufferObjectManager());
		pAnimatedSunny[4].setSize(pAnimatedSunny[4].getWidth(),  pAnimatedSunny[4].getHeight()+1);
		//Toc
		pAnimatedSunny[5] = new AnimatedSprite(293, 125, iTiledSunnyKami, this.getVertexBufferObjectManager());
		pAnimatedSunny[5].setSize(pAnimatedSunny[5].getWidth(),  pAnimatedSunny[5].getHeight()+1);
		for(int i = 0; i < 6; i++){
			pAnimatedRightAll.attachChild(pAnimatedSunny[i]);
			if(i<=2){
				pAnimatedRightAll.attachChild(pSpriteSunny[i]);
			}
		}
		this.mScene.attachChild(pAnimatedRightAll);
		pAnimatedRightAll.setVisible(false);
		///////////////
		pSpriteSetsumonBg = new Sprite(52, 529, iTextSetsumonBg, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteSetsumonBg);
		pSpriteSetsumonBg.setVisible(false);
		
		pAnimatedSetsumonAll =  new AnimatedSprite(164, 552, iTiledSetsumonAll, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedSetsumonAll);
		pAnimatedSetsumonAll.setCurrentTileIndex(3);
		pAnimatedSetsumonAll.setVisible(false);
		
		pSpriteStart = new Sprite(0, 0, iTextStart, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteStart);
		
		pSpriteStart_Text = new Sprite(256, 255, iTextStart_Text, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteStart_Text);
		pSpriteStart_Text.setVisible(false);
		//Sprite Tyuukan
		pSpriteTyuukan = new Sprite(270, 109, iTextTyuukan, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteTyuukan);
		pSpriteTyuukan.setVisible(false);
		//Find Animated sprite
		final int length2 = findAnimatedSprite.length;
		for(int i = 0; i< length2;i++){
			findAnimatedSprite[i] = new AnimatedSpritePrikyua(i, Vol3PrikyuaResource.findPositionAnimatedSprite[i][0], Vol3PrikyuaResource.findPositionAnimatedSprite[i][1], iTiledFindAnimatedSprite[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(findAnimatedSprite[i]);
			this.mScene.registerTouchArea(findAnimatedSprite[i]);
			findAnimatedSprite[i].setVisible(false);
		}
		
		//Sprite Complete
		pSpriteComplete = new Sprite(0, 0, iTextComplete, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pSpriteComplete);
		pSpriteComplete.setVisible(false);
		final int length3 = listpSpriteHikariLast.length;
		for (int i = 0; i < length3; i++) {
			listpSpriteHikariLast[i] = new LinkedList<Sprite>();
		}
		//Gimic Action
		addGimmicsButton(this.mScene, Vol3PrikyuaResource.SOUND_GIMMIC, Vol3PrikyuaResource.IMAGE_GIMMIC, Vol3PrikyuaLibrary1);
		for(int i =0; i<2;i++){
			this.sprGimmic[i].setVisible(false);
			setTouchGimmic(i, false);
		}
		this.sprGimmic[2].setPosition(sprGimmic[2].getmXFirst()+30, sprGimmic[2].getmYFirst()+25);
		this.sprGimmic[2].setVisible(false);
		//Set Touch Listener
		this.mScene.setOnSceneTouchListener(this);
	}
	//TODO load Karaoke Complete
    @Override
    protected void loadKaraokeComplete() {
    	Log.d(TAG,"loadKaraokeComplete");
        super.loadKaraokeComplete();
    }
    //TODO GIMIC ACTION tim diem sai va hien thi goi y cac diem sai
    private void gimicActionSprite(final Sprite sprite, final boolean booleanSprite, final float [][] arrays){
    	if(sprite.isVisible() && !booleanSprite && !booleanIsTouchGimic && !sprite.equals(pSpriteHappy[4])){
    		booleanIsTouchGimic = true;
    		setTouchGimmic3(false);
			clearObjectHikari();
			final int length4 = arrays.length;
			for(int i = 0; i< length4;i++){
				Sprite sp = pSpriteHikari.obtainPoolItem();
				Vol3Prikyua.this.mScene.attachChild(sp);
				sp.setPosition(arrays[i][0]+30, arrays[i][1]);
				listpSpriteHikari.add(sp);
			}
			sprite.clearEntityModifiers();
			sprite.registerEntityModifier(new DelayModifier(1.2f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							clearObjectHikari();
							booleanIsTouchGimic = false;
							setTouchGimmic3(true);
						}
					});
				}
			}));
		}
    	if(!sprite.isVisible() && sprite.equals(pSpriteHappy[4]) && !booleanSprite && !booleanIsTouchGimic){
    		booleanIsTouchGimic = true;
    		setTouchGimmic3(false);
			clearObjectHikari();
			final int length5 = arrays.length;
			for(int i = 0; i< length5;i++){
				Sprite sp = pSpriteHikari.obtainPoolItem();
				Vol3Prikyua.this.mScene.attachChild(sp);
				sp.setPosition(arrays[i][0]+30, arrays[i][1]);
				listpSpriteHikari.add(sp);
			}
			sprite.clearEntityModifiers();
			sprite.registerEntityModifier(new DelayModifier(1.2f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							clearObjectHikari();
							booleanIsTouchGimic = false;
							setTouchGimmic3(true);
						}
					});
				}
			}));
    	}
    }
    private void gimicActionAnimatedSprite(final AnimatedSprite anis, final boolean booleanSprite, final float [][] arrays){
    	if(anis.isVisible() && (anis.getCurrentTileIndex()==0 || (anis.equals(pAnimatedMarch[5]) && anis.getCurrentTileIndex()==1)) && !booleanSprite && !booleanIsTouchGimic){
    		booleanIsTouchGimic = true;
    		setTouchGimmic3(false);
			clearObjectHikari();
			final int length6 = arrays.length;
			for(int i = 0; i< length6;i++){
				Sprite sp = pSpriteHikari.obtainPoolItem();
				Vol3Prikyua.this.mScene.attachChild(sp);
				sp.setPosition(arrays[i][0]+30, arrays[i][1]);
				listpSpriteHikari.add(sp);
			}
			anis.clearEntityModifiers();
			anis.registerEntityModifier(new DelayModifier(1.2f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							clearObjectHikari();
							booleanIsTouchGimic = false;
							setTouchGimmic3(true);
						}
					});
				}
			}));
		}
    }

	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		//Process gimic action tim diem sai
		if(pAnimatedRightAll.isVisible() && !booleanPrikyua && !booleanIsTouchGimic && countChange!=3){
			A1_KIRAN.play();
			switch(randomPrikyua){
			case 0:
				//Sprite
				gimicActionSprite(pSpriteBeauty[0],booleanBeauty[0],Vol3PrikyuaResource.positionsBeaty0);
				gimicActionSprite(pSpriteBeauty[1],booleanBeauty[1],Vol3PrikyuaResource.positionsBeaty1);
				gimicActionSprite(pSpriteBeauty[2],booleanBeauty[2],Vol3PrikyuaResource.positionsBeaty2);
				gimicActionSprite(pSpriteBeauty[3],booleanBeauty[3],Vol3PrikyuaResource.positionsBeaty3);
				//Animated Sprite
				gimicActionAnimatedSprite(pAnimatedBeauty[0],booleanBeauty[4],Vol3PrikyuaResource.positionsBeaty4);
				gimicActionAnimatedSprite(pAnimatedBeauty[1],booleanBeauty[5],Vol3PrikyuaResource.positionsBeaty5);
				gimicActionAnimatedSprite(pAnimatedBeauty[2],booleanBeauty[6],Vol3PrikyuaResource.positionsBeaty6);
				gimicActionAnimatedSprite(pAnimatedBeauty[3],booleanBeauty[7],Vol3PrikyuaResource.positionsBeaty7);
				break;
			case 1:
				//Sprite
				gimicActionSprite(pSpriteHappy[0],booleanHappy[0],Vol3PrikyuaResource.positionsHappy0);
				gimicActionSprite(pSpriteHappy[1],booleanHappy[1],Vol3PrikyuaResource.positionsHappy1);
				gimicActionSprite(pSpriteHappy[2],booleanHappy[2],Vol3PrikyuaResource.positionsHappy2);
				gimicActionSprite(pSpriteHappy[3],booleanHappy[3],Vol3PrikyuaResource.positionsHappy3);
				gimicActionSprite(pSpriteHappy[4],booleanHappy[4],Vol3PrikyuaResource.positionsHappy4);
				//Animated Sprite
				gimicActionAnimatedSprite(pAnimatedHappy[0],booleanHappy[5],Vol3PrikyuaResource.positionsHappy5);
				gimicActionAnimatedSprite(pAnimatedHappy[1],booleanHappy[6],Vol3PrikyuaResource.positionsHappy6);
				gimicActionAnimatedSprite(pAnimatedHappy[2],booleanHappy[7],Vol3PrikyuaResource.positionsHappy7);
				break;
			case 2:
				//Animated Sprite
				gimicActionAnimatedSprite(pAnimatedMarch[0],booleanMarch[0],Vol3PrikyuaResource.positionsMarch0);
				gimicActionAnimatedSprite(pAnimatedMarch[1],booleanMarch[1],Vol3PrikyuaResource.positionsMarch1);
				gimicActionAnimatedSprite(pAnimatedMarch[2],booleanMarch[2],Vol3PrikyuaResource.positionsMarch2);
				gimicActionAnimatedSprite(pAnimatedMarch[3],booleanMarch[3],Vol3PrikyuaResource.positionsMarch3);
				gimicActionAnimatedSprite(pAnimatedMarch[4],booleanMarch[4],Vol3PrikyuaResource.positionsMarch4);
				gimicActionAnimatedSprite(pAnimatedMarch[5],booleanMarch[5],Vol3PrikyuaResource.positionsMarch5);
				gimicActionAnimatedSprite(pAnimatedMarch[6],booleanMarch[6],Vol3PrikyuaResource.positionsMarch6);
				gimicActionAnimatedSprite(pAnimatedMarch[7],booleanMarch[7],Vol3PrikyuaResource.positionsMarch7);
				break;
			case 3:
				//Sprite
				gimicActionSprite(pSpritePiece[0],booleanPiece[0],Vol3PrikyuaResource.positionsPiece0);
				gimicActionSprite(pSpritePiece[1],booleanPiece[1],Vol3PrikyuaResource.positionsPiece1);
				gimicActionSprite(pSpritePiece[2],booleanPiece[2],Vol3PrikyuaResource.positionsPiece2);
				//Animated Sprite
				gimicActionAnimatedSprite(pAnimatedPiece[0],booleanPiece[3],Vol3PrikyuaResource.positionsPiece3);
				gimicActionAnimatedSprite(pAnimatedPiece[1],booleanPiece[4],Vol3PrikyuaResource.positionsPiece4);
				gimicActionAnimatedSprite(pAnimatedPiece[2],booleanPiece[5],Vol3PrikyuaResource.positionsPiece5);
				gimicActionAnimatedSprite(pAnimatedPiece[3],booleanPiece[6],Vol3PrikyuaResource.positionsPiece6);
				gimicActionAnimatedSprite(pAnimatedPiece[4],booleanPiece[7],Vol3PrikyuaResource.positionsPiece7);
				break;
			case 4:
				//Sprite
				gimicActionSprite(pSpriteSunny[0],booleanSunny[0],Vol3PrikyuaResource.positionsSunny0);
				gimicActionSprite(pSpriteSunny[1],booleanSunny[1],Vol3PrikyuaResource.positionsSunny1);
				gimicActionSprite(pSpriteSunny[2],booleanSunny[2],Vol3PrikyuaResource.positionsSunny2);
				//Animated Sprite
				gimicActionAnimatedSprite(pAnimatedSunny[0],booleanSunny[3],Vol3PrikyuaResource.positionsSunny3);
				gimicActionAnimatedSprite(pAnimatedSunny[1],booleanSunny[4],Vol3PrikyuaResource.positionsSunny4);
				gimicActionAnimatedSprite(pAnimatedSunny[2],booleanSunny[5],Vol3PrikyuaResource.positionsSunny5);
				gimicActionAnimatedSprite(pAnimatedSunny[3],booleanSunny[6],Vol3PrikyuaResource.positionsSunny6);
				gimicActionAnimatedSprite(pAnimatedSunny[4],booleanSunny[7],Vol3PrikyuaResource.positionsSunny7);
				gimicActionAnimatedSprite(pAnimatedSunny[5],booleanSunny[8],Vol3PrikyuaResource.positionsSunny8);
				break;
			}
		}
		//Process gimic action tim bong dung
		if(booleanIsTouchFind){
			//Da co 4 bong duoc touch thi khong cho touch nua
			if(coutTouchFind==4){
				setTouchGimmic3(false);
				return;
			}
			showFind = gimicFind.getNextIntNoRepeat(true);
			//khi random bang voi main va random cua doi tuong la true thi tiep tuc random
			while(showFind==randomPrikyua){
				// || findAnimatedPrikyua[showFind] bo dieu kien nay
				showFind = gimicFind.getNextIntNoRepeat(true);
			}
			switch(showFind){
			case 0:
				A1_KAGEKIERU2.play();
				setVisibleSpriteArray(false,findAnimatedSprite[0]);
				findAnimatedPrikyua[0] = true;
				coutTouchFind++;
				break;
			case 1:
				A1_KAGEKIERU2.play();
				setVisibleSpriteArray(false,findAnimatedSprite[1]);
				findAnimatedPrikyua[1] = true;
				coutTouchFind++;
				break;
			case 2:
				A1_KAGEKIERU2.play();
				setVisibleSpriteArray(false,findAnimatedSprite[2]);
				findAnimatedPrikyua[2] = true;
				coutTouchFind++;
				break;
			case 3:
				A1_KAGEKIERU2.play();
				setVisibleSpriteArray(false,findAnimatedSprite[3]);
				findAnimatedPrikyua[3] = true;
				coutTouchFind++;
				break;
			case 4:
				A1_KAGEKIERU2.play();
				setVisibleSpriteArray(false,findAnimatedSprite[4]);
				findAnimatedPrikyua[4] = true;
				coutTouchFind++;
				break;
			}
		}
	}
	//Class process when find sprite
	class AnimatedSpritePrikyua extends AnimatedSprite{
		private int index;
		public AnimatedSpritePrikyua(int indexs, float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion,
					vertexBufferObjectManager);
			this.index = indexs;
		}
		//Ham dung de random tiep hoac la chien thang
		private void delayAndChangeOrWin(final AnimatedSprite anis){
			//Khong cho touch vao gimic va ca Animated Sprite
			setTouchGimmic3(false);
			final int length7 = findAnimatedPrikyua.length;
			for(int i = 0;i< length7;i++){
    			findAnimatedPrikyua[i] = true;
	    	}
			//Set anh dung ve 0
			anis.setCurrentTileIndex(0);
			//Doan trung thi se tru gia tri find
			countFindSprite--;
			if(countFindSprite==0){
				//Chien thang va bat dau choi lai tu dau man hinh tim diem sai khac
				A1_HITOTSUKIERU.stop();
				A1_HINTOGADERU.stop();
				A1_YOKUMITENE.stop();
				A1_DOKOGATIGAU.stop();
				setVisibleSpriteArray(false,Vol3Prikyua.this.sprGimmic[2]);
				anis.clearEntityModifiers();
				anis.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						//Reset toan bo du lieu
						setVisibleAnimatedSpriteArray(false,0,pAnimatedLeftAll,pAnimatedSetsumonAll);
						setVisibleSpriteArray(false,pSpriteBase,pSpriteLine,pSpriteSetsumonBg,anis);
						resetValue();
						setVisibleAll();
						//Hien hinh complete
						A1_YATANE_OMEDETO.play();
						A1_D_PACHI_YUBI.play();
						setVisibleSpriteArray(true,pSpriteComplete);
						pSpriteComplete.registerEntityModifier(new DelayModifier(6.0f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								randomPrikyua();
							}
						}));
					}
				}));
			}else{
				//Chua thoa man dk chien thang thi chay tiep random tim bong dung
				anis.clearEntityModifiers();
				anis.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						coutTouchFind = 0;
						final int length8 = findAnimatedPrikyua.length;
						for(int i = 0;i< length8;i++){
			    			findAnimatedPrikyua[i] = false;
				    	}
						setTouchGimmic3(true);
						gimicFind.clearTmp();
						gimicFind.setLenghNoRepeat(5);
						randomFindSpritePrikyua();
					}
				}));
			}
		}
		private void stopSoundPrikyua(){
			isFinded6s = true;
			A1_YOKUMITENE.stop();
			A1_DOKOGATIGAU.stop();
			Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerFinded);
			if(countFindSprite!=0){
				if(timerFinded!=null){
					timerFinded = null;
				}
				if(timerFinded==null){
			        timerFinded = new TimerHandler(6.0f, false, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							isFinded6s = false;
						}
					});
		        }
				Vol3Prikyua.this.mScene.registerUpdateHandler(timerFinded);
			}
		}
		//process set Alpha = 50% of main Animated Sprite
		private void alPhaPrikyua(final AnimatedSprite anis){
			anis.clearEntityModifiers();
			anis.registerEntityModifier(new AlphaModifier(1.0f, 1.0f, 0.5f));
		}
		//Touch on Animated Sprite
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(pSceneTouchEvent.isActionDown()){
				if(this.isVisible() && booleanIsTouchFind){
					switch (this.index) {
						case 0:
							if(!findAnimatedPrikyua[0]){
								findAnimatedPrikyua[0] = true;
								if(this.index==randomPrikyua){
									A1_SEIKAI.play();
									delayAndChangeOrWin(this);
									stopSoundPrikyua();
								}else{
									A1_ZANNEN.play();
									alPhaPrikyua(this);
								}
							}
							break;
						case 1:
							if(!findAnimatedPrikyua[1]){
								findAnimatedPrikyua[1] = true;
								if(this.index==randomPrikyua){
									A1_SEIKAI.play();
									delayAndChangeOrWin(this);
									stopSoundPrikyua();
								}else{
									A1_ZANNEN.play();
									alPhaPrikyua(this);
								}
							}
							break;
						case 2:
							if(!findAnimatedPrikyua[2]){
								findAnimatedPrikyua[2] = true;
								if(this.index==randomPrikyua){
									A1_SEIKAI.play();
									delayAndChangeOrWin(this);
									stopSoundPrikyua();
								}else{
									A1_ZANNEN.play();
									alPhaPrikyua(this);
								}
							}
							break;
						case 3:
							if(!findAnimatedPrikyua[3]){
								findAnimatedPrikyua[3] = true;
								if(this.index==randomPrikyua){
									A1_SEIKAI.play();
									delayAndChangeOrWin(this);
									stopSoundPrikyua();
								}else{
									A1_ZANNEN.play();
									alPhaPrikyua(this);
								}
							}
							break;
						case 4:
							if(!findAnimatedPrikyua[4]){
								findAnimatedPrikyua[4] = true;
								if(this.index==randomPrikyua){
									A1_SEIKAI.play();
									delayAndChangeOrWin(this);
									stopSoundPrikyua();
								}else{
									A1_ZANNEN.play();
									alPhaPrikyua(this);
								}
							}
							break;
					}
					return true;
				}
			}
			return false;
		}
		
	}
	//Xoa toan bo doi tuong pool Atari
	private void clearObjectAtari(){
        if(listpSpriteAtari.size()>0){
	        for(int i = 0;i< listpSpriteAtari.size();i++){
	        	listpSpriteAtari.get(i).clearEntityModifiers();
	        	listpSpriteAtari.get(i).clearUpdateHandlers();
	        	pSpriteAtari.onHandleRecycleItem(listpSpriteAtari.get(i));
	        }
        }
	}
	//Xoa toan bo doi tuong pool Hikari
	private void clearObjectHikari(){
        if(listpSpriteHikari.size()>0){
	        for(int i = 0;i< listpSpriteHikari.size();i++){
	        	listpSpriteHikari.get(i).clearEntityModifiers();
	        	listpSpriteHikari.get(i).clearUpdateHandlers();
	        	pSpriteHikari.onHandleRecycleItem(listpSpriteHikari.get(i));
	        }
        }
	}
	//Xoa toan bo doi tuong pool HikariLast
	private void clearObjectHikariLast(){
		final int length9 = pSpriteHikariLastPool.length;
		
		for (int i = 0; i < length9; i++) {
			 if(listpSpriteHikariLast[i].size()>0){
			        for(int j = 0;j< listpSpriteHikariLast[i].size();j++){
			        	if(listpSpriteHikariLast[i].get(j)!=null){
				        	listpSpriteHikariLast[i].get(j).clearEntityModifiers();
				        	listpSpriteHikariLast[i].get(j).clearUpdateHandlers();
				        	pSpriteHikariLastPool[i].onHandleRecycleItem(listpSpriteHikariLast[i].get(j));
			        	}
			        }
			 }
		}
       
	}
	
	//Create object GenericPool
	class AtariSprite extends GenericPool<Sprite> {
		private ITextureRegion mTextureRegion;
		//Constructor
		public AtariSprite(ITextureRegion pITextureRegion) {
			if (pITextureRegion == null) {
		           // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
		           throw new IllegalArgumentException("The texture region must not be NULL");
		          }
			this.mTextureRegion = pITextureRegion;
		}
		@Override
		protected Sprite onAllocatePoolItem() {
			final Sprite mSprites = new Sprite(-68, -67, this.mTextureRegion, Vol3Prikyua.this.getVertexBufferObjectManager());
			return mSprites;
		}
		@Override
		protected void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.setIgnoreUpdate(true);
		}
		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
		    pItem.reset();
		}
	}
	//Di chuyen Tyuukan khi doan trung het man hinh tim diem sai khac
	private void moveTyuukan(){
		//Bat dau chuan bi qua man hinh tim bong
		A1_TENKAN.play();
		pSpriteBackground.clearEntityModifiers();
		pSpriteBackground.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				A1_TUGIHASIRUETO.play();
			}
		}));
		pSpriteTyuukan.setVisible(true);
		pSpriteTyuukan.clearEntityModifiers();
		pSpriteTyuukan.setAlpha(1.0f);
		pSpriteTyuukan.registerEntityModifier(new MoveModifier(3.0f, pSpriteTyuukan.getmXFirst(), pSpriteTyuukan.getmXFirst(), pSpriteTyuukan.getmYFirst()+pSpriteTyuukan.getHeight(), 80, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
				pSpriteTyuukan.registerEntityModifier(new AlphaModifier(2.0f, 1.0f, 0, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
						setVisibleSpriteArray(false,pSpriteTyuukan);
						//Bat dau choi voi man hinh tim bong
						randomFindSpritePrikyua();
						setVisibleSpriteArray(true,Vol3Prikyua.this.sprGimmic[2]);
					}
				}));
			}
		}));
	}
	//Xu ly chuyen qua man hinh tim bong hay la tiep tuc man hinh tim diem sai khac
	private void changeAndWin(){
		countChange++;
		if(countChange==2){
			Vol3Prikyua.this.mScene.clearEntityModifiers();
			Vol3Prikyua.this.mScene.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					A1_ATOSUKOSHI.play();
				}
			}));
		}
		if(countChange==3){
			countPrikyua--;
			if(countPrikyua==0){
				Vol3Prikyua.this.mScene.clearEntityModifiers();
				Vol3Prikyua.this.mScene.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						clearObjectAtari();
						setVisibleAll();
						setVisibleSpriteArray(false,pSpriteBase,pSpriteLine,pSpriteSetsumonBg);
						setVisibleAnimatedSpriteArray(false,0,pAnimatedSetsumonAll);
						setVisibleSpriteArray(false,Vol3Prikyua.this.sprGimmic[2]);
						A1_HITOTSUKIERU.stop();
						A1_HINTOGADERU.stop();
						moveTyuukan();
					}
				}));
			}else{
				Vol3Prikyua.this.mScene.clearEntityModifiers();
				Vol3Prikyua.this.mScene.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						countChange = 0;
						//Chay voi man hinh tim diem khac nhau
						randomPrikyua();
					}
				}));
			}
		}
	}
	//Xu ly an tat ca cac sprite va animated sprite voi bai hat Vol3Prkyua
	private void setVisibleAll(){
		//Set unvisible all objects
		setVisibleSpriteArray(false,pSpriteComplete,pSpriteSetsumonBg,pSpriteBeauty[0],pSpriteBeauty[1],pSpriteBeauty[2],pSpriteBeauty[3],
				pSpriteHappy[0],pSpriteHappy[1],pSpriteHappy[2],pSpriteHappy[3],pSpriteHappy[4],
				pSpritePiece[0],pSpritePiece[1],pSpritePiece[2],pSpriteSunny[0],pSpriteSunny[1],pSpriteSunny[2],pSpriteTyuukan,sprGimmic[2],
				pSpriteStart,pSpriteStart_Text
				);
		setVisibleAnimatedSpriteArray(false,0,pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3],
				pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2],pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],
				pAnimatedMarch[3],pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7],
				pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],pAnimatedPiece[3],pAnimatedPiece[4],
				pAnimatedSunny[0], pAnimatedSunny[1], pAnimatedSunny[2], pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5],
				pAnimatedLeftAll,pAnimatedRightAll,findAnimatedSprite[0],findAnimatedSprite[1],findAnimatedSprite[2],findAnimatedSprite[3],
				findAnimatedSprite[4],pAnimatedSetsumonAll
		);
	}
	//Xu ly clear het cac modifier, update handler
	private void clearAll(){
		//Set unvisible all objects
		clearAllSpriteArray(pSpriteComplete,pSpriteSetsumonBg,pSpriteBeauty[0],pSpriteBeauty[1],pSpriteBeauty[2],pSpriteBeauty[3],
				pSpriteHappy[0],pSpriteHappy[1],pSpriteHappy[2],pSpriteHappy[3],pSpriteHappy[4],
				pSpritePiece[0],pSpritePiece[1],pSpritePiece[2],pSpriteSunny[0],pSpriteSunny[1],pSpriteSunny[2],pSpriteTyuukan,sprGimmic[2],pSpriteStart,pSpriteStart_Text
				);
		clearAllAnimatedSpriteArray(pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3],
				pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2],pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],
				pAnimatedMarch[3],pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7],
				pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],pAnimatedPiece[3],pAnimatedPiece[4],
				pAnimatedSunny[0], pAnimatedSunny[1], pAnimatedSunny[2], pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5],
				pAnimatedLeftAll,pAnimatedRightAll,findAnimatedSprite[0],findAnimatedSprite[1],findAnimatedSprite[2],findAnimatedSprite[3],
				findAnimatedSprite[4],pAnimatedSetsumonAll
		);
		if(pSpriteBackground!=null){
			pSpriteBackground.clearEntityModifiers();
			pSpriteBackground.clearUpdateHandlers();
		}
	}
	//Ham set Visible(true,false) cho nhieu doi tuong Sprite
	private void setVisibleSpriteArray(final boolean visibles, Sprite ...sprites){
		final int length10 = sprites.length;
		for(int i=0;i< length10;i++){
			if(sprites[i]!= null){
				sprites[i].setVisible(visibles);
			}
		}
	}
	//Ham set Visible(true,false) cho nhieu doi tuong Animated Sprite
	private void setVisibleAnimatedSpriteArray(final boolean visibles, final int currentTileds,AnimatedSprite ...animatedSprites){
		final int length11 = animatedSprites.length;
		for(int i=0;i< length11;i++){
			if(animatedSprites[i]!= null){
				animatedSprites[i].setCurrentTileIndex(currentTileds);
				animatedSprites[i].setVisible(visibles);
			}
		}
	}	
	//Ham xu ly clear Modifyer, update hander
	private void clearAllSpriteArray(Sprite ...sprites){
		final int length12 = sprites.length;
		for(int i=0;i< length12;i++){
			if(sprites[i]!=null){
				sprites[i].clearEntityModifiers();
				sprites[i].clearUpdateHandlers();
				sprites[i].setPosition(sprites[i].getmXFirst(), sprites[i].getmYFirst());
				sprites[i].setVisible(false);
			}
		}
	}
	private void clearAllAnimatedSpriteArray(AnimatedSprite ...animatedSprites){
		final int length13 = animatedSprites.length;
		for(int i=0;i< length13;i++){
			if(animatedSprites[i]!=null){
				animatedSprites[i].clearEntityModifiers();
				animatedSprites[i].clearUpdateHandlers();
				animatedSprites[i].setCurrentTileIndex(0);
				animatedSprites[i].setPosition(animatedSprites[i].getmXFirst(), animatedSprites[i].getmYFirst());
				animatedSprites[i].setAlpha(1.0f);
				animatedSprites[i].setVisible(false);
			}
		}
	}
	//Xu ly random tim kiem bong dung cac Animated Sprite
	private void randomFindSpritePrikyua(){
		A1_ONAJIKATATI.play();
		A1_HITOTSUKIERU.stop();
		A1_HINTOGADERU.stop();
		booleanIsTouchFind = false;
		isFinded6s = true;
		booleanSumon = false;
		randomPrikyua = randNoRepeat.getNextIntNoRepeat(true);
		setVisibleAnimatedSpriteArray(true,randomPrikyua,pAnimatedLeftAll);
		setVisibleSpriteArray(true,pSpriteBase,pSpriteLine,pSpriteSetsumonBg);
		setVisibleAnimatedSpriteArray(true,2,pAnimatedSetsumonAll);
		pAnimatedLeftAll.setPosition(-(pAnimatedLeftAll.getWidth()+pAnimatedLeftAll.getmXFirst()), pAnimatedLeftAll.getmYFirst());
		//Clear pAnimatedSetsumonAll
		Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
		Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerFinded);
		pAnimatedSetsumonAll.clearEntityModifiers();
		pAnimatedLeftAll.clearEntityModifiers();
		//Het am thanh bat dau choi
		pAnimatedLeftAll.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
				pAnimatedLeftAll.setPosition(0, 0);
				//Xuat hien em be
				A1_3SYARA.play();
				pAnimatedLeftAll.registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						//Bat dau cho touch vao nut gimic va cac doi tuong bong
						booleanIsTouchFind = true;
						 //Xu ly sau 4s tuc den giay thu 7 moi duoc 
						Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
						Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerFinded);
						if(timer4s!=null){
							timer4s.reset();
							timer4s = null;
						}
				        if(timer4s==null){
					        timer4s = new TimerHandler(4.0f, false, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									//Tim diem sai khac
									if(pAnimatedRightAll.isVisible() && !booleanSumon){
										booleanSumon = true;
										A1_HINTOGADERU.play();
										pAnimatedSetsumonAll.setCurrentTileIndex(0);
										setBooleanSumon();
									}
									//Tim bong
									if(findAnimatedSprite[0].isVisible() || findAnimatedSprite[1].isVisible() || findAnimatedSprite[2].isVisible() 
											|| findAnimatedSprite[3].isVisible() || findAnimatedSprite[4].isVisible()){
										if(!booleanSumon){
											A1_HITOTSUKIERU.play();
											pAnimatedSetsumonAll.setCurrentTileIndex(1);
											booleanSumon = true;
											setBooleanSumon();
										}
									}
								}
							});
				        }
				        //Tim bong
				        if(timerFinded!=null){
				        	timerFinded.reset();
				        	timerFinded = null;
				        }
				        if(timerFinded==null){
					        timerFinded = new TimerHandler(6.0f, false, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									isFinded6s = false;
								}
							});
				        }
				        if(timerFinded6s==null){
					        timerFinded6s = new TimerHandler(6.0f, true, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									//Tim bong
									if(!isFinded6s){
										if(findAnimatedSprite[0].isVisible() || findAnimatedSprite[1].isVisible() || findAnimatedSprite[2].isVisible() 
												|| findAnimatedSprite[3].isVisible() || findAnimatedSprite[4].isVisible()){
											soundMod ++;
											if(soundMod%2==0){
												A1_YOKUMITENE.play();
											}else{
												A1_DOKOGATIGAU.play();
											}
											if(soundMod>=1000){
												soundMod = 0;
											}
										}
									}
								}
							});
					        Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerFinded6s);
					        Vol3Prikyua.this.mScene.registerUpdateHandler(timerFinded6s);
				        }
						Vol3Prikyua.this.mScene.registerUpdateHandler(timer4s);
						Vol3Prikyua.this.mScene.registerUpdateHandler(timerFinded);
					}
				}));
			}
		}));
		//Xet hien toan bo cac bong
		setVisibleAnimatedSpriteArray(true,1,findAnimatedSprite[0],findAnimatedSprite[1],findAnimatedSprite[2],findAnimatedSprite[3],
				findAnimatedSprite[4]);
		//Xet lai alpha cho cac bong
		final int length14 = findAnimatedSprite.length;
		for(int i = 0;i< length14;i++){
			findAnimatedSprite[i].setBlendFunction(GL10.GL_SRC_ALPHA,
					GL10.GL_ONE_MINUS_SRC_ALPHA);
			findAnimatedSprite[i].setAlpha(1.0f);
		}
		//Xet cac vi tri khac nhau cho tung Animated Sprite
		switch (randomPrikyua) {
			case 0:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSprite[i][0], Vol3PrikyuaResource.findPositionAnimatedSprite[i][1]);
				}
				Log.d("TRuong hop la:","findPositionAnimatedSprite");
				break;
			case 1:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSpriteA[i][0], Vol3PrikyuaResource.findPositionAnimatedSpriteA[i][1]);
				}	
				Log.d("TRuong hop la:","findPositionAnimatedSpriteA");
				break;
			case 2:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSpriteB[i][0], Vol3PrikyuaResource.findPositionAnimatedSpriteB[i][1]);
				}
				Log.d("TRuong hop la:","findPositionAnimatedSpriteB");
				break;
			case 3:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSpriteC[i][0], Vol3PrikyuaResource.findPositionAnimatedSpriteC[i][1]);
				}
				Log.d("TRuong hop la:","findPositionAnimatedSpriteC");
				break;
			case 4:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSpriteD[i][0], Vol3PrikyuaResource.findPositionAnimatedSpriteD[i][1]);
				}
				Log.d("TRuong hop la:","findPositionAnimatedSpriteD");
				break;
			default:
				for(int i = 0;i < length14;i++){
					findAnimatedSprite[i].setPosition(Vol3PrikyuaResource.findPositionAnimatedSpriteE[i][0], Vol3PrikyuaResource.findPositionAnimatedSpriteE[i][1]);
				}
				Log.d("TRuong hop la:","findPositionAnimatedSpriteE");
				break;
		}
		//Xet lai gia tri boolean cho cac bong
		final int length15 = findAnimatedPrikyua.length;
		for(int i = 0;i < length15;i++){
			findAnimatedPrikyua[i] = false;
		}
	}
	//Xu ly tim cac diem dung dua vao Main Prikyua
	private void randomPrikyua(){
		//Bat dau tim kiem diem sai khac
		//Khi xuat hien Sumon
		A1_TIGAUTOKORO.play();
		A1_HITOTSUKIERU.stop();
		A1_HINTOGADERU.stop();
		booleanPrikyua = true;
		isDifference6s = true;
		booleanSumon = false;
		//Bat dau dang ky timer
		//Xoa toan bo object pool
		clearObjectAtari();
		clearObjectHikariLast();
		//An toan bo cac doi tuong Sprite or Animated Sprite
		setVisibleAll();
		//Bat dau chay random voi so index tao ra khong lap lai
		randomPrikyua = randNoRepeat.getNextIntNoRepeat(true);
		//Hien thi khung base, line,sumon, gimic 2
		setVisibleSpriteArray(true,pSpriteBase,pSpriteLine,pSpriteSetsumonBg);
		setVisibleAnimatedSpriteArray(true,3,pAnimatedSetsumonAll);
		//Hien nut gimic dau ?
		setVisibleSpriteArray(true,Vol3Prikyua.this.sprGimmic[2]);
		//Xet cho Main Right va Left Prikyua la hien
		pAnimatedLeftAll.setPosition(0, 0);
		setVisibleAnimatedSpriteArray(true,randomPrikyua,pAnimatedLeftAll);
		pAnimatedLeftAll.clearEntityModifiers();
		//Clear pAnimatedSetsumonAll
		Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
		Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerDifference);
		pAnimatedSetsumonAll.clearEntityModifiers();
		//Dang ky delay sau 3.5s moi bat dau cho touch vao cac doi tuong dau hoi va doi tuong tim kiem
		pAnimatedLeftAll.registerEntityModifier(new DelayModifier(3.5f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> paramIModifier,
					IEntity paramT) {
				//Khi xuat hien em be
				setVisibleAnimatedSpriteArray(true,randomPrikyua,pAnimatedRightAll);
				A1_3SYARA.play();
				pAnimatedRightAll.registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						//Bat dau cho Touch vao cac doi tuong
						booleanPrikyua = false;
						//Bat dau cho Touch vao cac doi tuong
						//Bat dau dang ky 4s
						Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
						Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerDifference);
						if(timer4s!=null){
							timer4s.reset();
							timer4s = null;
						}
				        if(timer4s==null){
					        timer4s = new TimerHandler(4.0f, false, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									//Tim diem sai khac
									if(pAnimatedRightAll.isVisible() && !booleanSumon){
										A1_HINTOGADERU.play();
										pAnimatedSetsumonAll.setCurrentTileIndex(0);
										booleanSumon = true;
										setBooleanSumon();
									}
									//Tim bong
									if(findAnimatedSprite[0].isVisible() || findAnimatedSprite[1].isVisible() || findAnimatedSprite[2].isVisible() 
											|| findAnimatedSprite[3].isVisible() || findAnimatedSprite[4].isVisible()){
										if(!booleanSumon){
											A1_HITOTSUKIERU.play();
											pAnimatedSetsumonAll.setCurrentTileIndex(1);
											booleanSumon = true;
											setBooleanSumon();
										}
									}
								}
							});
				        }
				        //Xu ly sau 6s
				        //Tim diem sai khac khi isDifference6s = false thi se phat lan luot 2 am thanh A1_YOKUMITENE va A1_DOKOGATIGAU
				        if(timerDifference!=null){
				        	timerDifference.reset();
				        	timerDifference = null;
				        }
				        if(timerDifference==null){
					        timerDifference = new TimerHandler(6.0f, false, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									//Tim diem sai khac
									isDifference6s = false;
								}
							});
				        }
				        if(timerDifference6s==null){
					        timerDifference6s = new TimerHandler(6.0f, true, new ITimerCallback() {
								@Override
								public void onTimePassed(TimerHandler pTimerHandler) {
									if(!isDifference6s){
										//Tim diem sai khac
										if(pAnimatedRightAll.isVisible()){
											soundMod ++;
											if(soundMod%2==0){
												A1_YOKUMITENE.play();
											}else{
												A1_DOKOGATIGAU.play();
											}
											if(soundMod>=1000){
												soundMod = 0;
											}
										}
									}
								}
							});
					        Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerDifference6s);
					        Vol3Prikyua.this.mScene.registerUpdateHandler(timerDifference6s);
				        }
						Vol3Prikyua.this.mScene.registerUpdateHandler(timer4s);
						Vol3Prikyua.this.mScene.registerUpdateHandler(timerDifference);
					}
				}));

			}
		}));
		//Xu ly random hien thi tung truong hop cua tung Prikyua(chi hien thi 3 diem khac nhau)
        switch(randomPrikyua){
        //Beauty
        	case 0:
        		//pAnimatedBeauty[0],pAnimatedBeauty[1] khong cung xuat hien
        		showError = randBeauty.getNextIntNoRepeat(true);
        		switch (showError) {
				case 0:
					setVisibleSpriteArray(true,pSpriteBeauty[0],pSpriteBeauty[1]);
					setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
					setVisibleAnimatedSpriteArray(true,0, pAnimatedBeauty[0]);
					break;
				case 1:
					setVisibleSpriteArray(true,pSpriteBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedBeauty[1],pAnimatedBeauty[2]);
					break;
				case 2:
					setVisibleSpriteArray(true,pSpriteBeauty[2],pSpriteBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedBeauty[1]);
					break;
				case 3:
					setVisibleSpriteArray(true,pSpriteBeauty[2]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedBeauty[3],pAnimatedBeauty[2]);
					break;
				case 4:
					setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedBeauty[0],pAnimatedBeauty[2],pAnimatedBeauty[3]);
					break;
				case 5:
					setVisibleSpriteArray(true,pSpriteBeauty[1],pSpriteBeauty[2],pSpriteBeauty[3]);
					setVisibleAnimatedSpriteArray(true,1, pAnimatedBeauty[0],pAnimatedBeauty[1],pAnimatedBeauty[2],pAnimatedBeauty[3]);
					break;
				}
        		break;
		//Happy
        	case 1:
        		//pAnimatedHappy[0],pAnimatedHappy[1] khong cung xuat hien
        		showError = randHappy.getNextIntNoRepeat(true);
        		switch (showError) {
				case 0:
					setVisibleSpriteArray(true,pSpriteHappy[0],pSpriteHappy[4]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedHappy[1],pAnimatedHappy[2]);
					break;
				case 1:
					setVisibleSpriteArray(true,pSpriteHappy[1],pSpriteHappy[2],pSpriteHappy[4]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedHappy[0]);
					break;
				case 2:
					setVisibleSpriteArray(true,pSpriteHappy[3],pSpriteHappy[4]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedHappy[2],pAnimatedHappy[1]);
					break;
				case 3:
					setVisibleSpriteArray(true,pSpriteHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedHappy[2]);
					break;
				case 4:
					setVisibleSpriteArray(true,pSpriteHappy[2],pSpriteHappy[3],pSpriteHappy[4]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedHappy[1]);
					break;
				case 5:
					setVisibleSpriteArray(true,pSpriteHappy[1],pSpriteHappy[3],pSpriteHappy[4]);
	        		setVisibleAnimatedSpriteArray(true,1, pAnimatedHappy[0],pAnimatedHappy[1],pAnimatedHappy[2]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedHappy[1]);
					break;
				}
        		break;
		//March
        	case 2:
        		//2,3 pAnimatedMarch[2],pAnimatedMarch[3] khong cung xuat hien
        		showError = randMarch.getNextIntNoRepeat(true);
        		switch (showError) {
				case 0:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,2,pAnimatedMarch[5]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2]);
					break;
				case 1:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,2,pAnimatedMarch[5]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[3]);
					break;
				case 2:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[2],pAnimatedMarch[4],pAnimatedMarch[5]);
					break;
				case 3:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[6],pAnimatedMarch[7]);
					break;
				case 4:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,2,pAnimatedMarch[5]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[2],pAnimatedMarch[4],pAnimatedMarch[6]);
					break;
				case 5:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedMarch[0],pAnimatedMarch[1],pAnimatedMarch[2],pAnimatedMarch[3],
	        				pAnimatedMarch[4],pAnimatedMarch[5],pAnimatedMarch[6], pAnimatedMarch[7]);
					setVisibleAnimatedSpriteArray(true,0,pAnimatedMarch[3],pAnimatedMarch[5],pAnimatedMarch[7]);
					break;
				}
        		break;
		//Piece
        	case 3:
        		showError = randPiece.getNextIntNoRepeat(true);
        		switch (showError) {
				case 0:
					setVisibleSpriteArray(true,pSpritePiece[0]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedPiece[0],pAnimatedPiece[1]);
					break;
				case 1:
					setVisibleSpriteArray(true,pSpritePiece[1]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedPiece[2],pAnimatedPiece[3]);
					break;
				case 2:
					setVisibleSpriteArray(true,pSpritePiece[2]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedPiece[3],pAnimatedPiece[4]);
					break;
				case 3:
					setVisibleSpriteArray(true,pSpritePiece[0],pSpritePiece[1],pSpritePiece[2]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
					break;
				case 4:
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedPiece[0],pAnimatedPiece[2],pAnimatedPiece[4]);
					break;
				case 5:
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedPiece[0],pAnimatedPiece[1],pAnimatedPiece[2],
	        				pAnimatedPiece[3],pAnimatedPiece[4]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedPiece[0],pAnimatedPiece[1],
	        				pAnimatedPiece[3]);
					break;
				}
        		break;
		//Sunny
        	case 4:
        		showError = randSunny.getNextIntNoRepeat(true);
        		switch (showError) {
				case 0:
					setVisibleSpriteArray(true,pSpriteSunny[0],pSpriteSunny[1],pSpriteSunny[2]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);
					break;
				case 1:
					setVisibleSpriteArray(true,pSpriteSunny[0]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);	
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedSunny[0],pAnimatedSunny[2]);
					break;
				case 2:
					setVisibleSpriteArray(true,pSpriteSunny[1]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedSunny[2], pAnimatedSunny[3]);
					break;
				case 3:
					setVisibleSpriteArray(true,pSpriteSunny[2]);
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);
	        		setVisibleAnimatedSpriteArray(true,0, pAnimatedSunny[4],pAnimatedSunny[5]);
					break;
				case 4:
					setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedSunny[0], pAnimatedSunny[2], pAnimatedSunny[4]);
					break;
				case 5:
	        		setVisibleAnimatedSpriteArray(true,1,pAnimatedSunny[0], pAnimatedSunny[1],pAnimatedSunny[2], 
	        				pAnimatedSunny[3], pAnimatedSunny[4],pAnimatedSunny[5]);
	        		setVisibleAnimatedSpriteArray(true,0,pAnimatedSunny[1], pAnimatedSunny[3], pAnimatedSunny[5]);
					break;
				}
        		break;
        }
	}
	//Ngung am thanh dong thoi dang ky lai timer de phat am thanh
	private void stopSoundTrue(){
		isDifference6s = true;
		A1_YOKUMITENE.stop();
		A1_DOKOGATIGAU.stop();
		Vol3Prikyua.this.mScene.unregisterUpdateHandler(timerDifference);
		if(countPrikyua!=0){
			if(timerDifference!=null){
				timerDifference = null;
			}
			if(timerDifference==null){
		        timerDifference = new TimerHandler(6.0f, false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						isDifference6s = false;
					}
				});
	        }
			Vol3Prikyua.this.mScene.registerUpdateHandler(timerDifference);
		}
	}
	//Xu ly khi touch vao cac diem Sprite khac voi anh goc
	private void touchSpritePrikyua(final Sprite prikyuaSprite, final float x, final float y){
		if(prikyuaSprite.isVisible() && !prikyuaSprite.equals(pSpriteHappy[4])){
			A1_SEIKAI.play();
			Sprite sprites = null;
			sprites = pSpriteAtari.obtainPoolItem();
			Vol3Prikyua.this.mScene.attachChild(sprites);
			sprites.setPosition(prikyuaSprite.getmXFirst()+x, prikyuaSprite.getmYFirst()+y);
			listpSpriteAtari.add(sprites);
			changeAndWin();
			stopSoundTrue();
			
		}
		if(!prikyuaSprite.isVisible() && prikyuaSprite.equals(pSpriteHappy[4])){
			A1_SEIKAI.play();
			Sprite sprite = null;
			sprite = pSpriteAtari.obtainPoolItem();
			Vol3Prikyua.this.mScene.attachChild(sprite);
			sprite.setPosition(prikyuaSprite.getmXFirst()+x, prikyuaSprite.getmYFirst()+y);
			listpSpriteAtari.add(sprite);
			changeAndWin();
			stopSoundTrue();
		}
	}
	//Xu ly khi touch vao Animated Sprite khi khac voi anh goc
	private void touchAnimatedSpritePrikyua(final AnimatedSprite prikyuaAnimatedSprites,final float x, final float y){
		if(prikyuaAnimatedSprites.isVisible() && (prikyuaAnimatedSprites.getCurrentTileIndex()==0 || (prikyuaAnimatedSprites.equals(pAnimatedMarch[5]) && prikyuaAnimatedSprites.getCurrentTileIndex()==1))){
			A1_SEIKAI.play();
			Sprite sp = null;
			sp = pSpriteAtari.obtainPoolItem();
			Vol3Prikyua.this.mScene.attachChild(sp);
			sp.setPosition(prikyuaAnimatedSprites.getmXFirst()+x, prikyuaAnimatedSprites.getmYFirst()+y);
			listpSpriteAtari.add(sp);
			changeAndWin();
			stopSoundTrue();
				
		}
	}
	//Xu ly touch vao man hinh chien thang va co cac ngoi sao
	private void touchComplete(int pX,int pY) {
		if(Math.abs((pX + pY) - (pLastX+pLastY)) >= 100){
			A1_SUTEKI.play();
			Sprite sStar[] = new Sprite[5];
			final int length16 = sStar.length;
			for (int i = 0; i < length16; i++) {
				 sStar[i]= pSpriteHikariLastPool[i].obtainPoolItem();
				 sStar[i].setPosition(pX, pY);
				 sStar[i].setAlpha(0.7f);
				 this.mScene.attachChild(sStar[i]);
				 listpSpriteHikariLast[i].add(sStar[i]);
				 starMoveComplete(sStar[i], pX, (int)arrPointStar[0][i], pY, (int)arrPointStar[1][i]);
			}
			pLastX = pX;
			pLastY = pY;
		}
	}
	private void starMoveComplete(final Sprite starMove, int fromX, int toX,  int fromY, int toY) {
		starMove.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(5.0f, 0.7f, 0.0f),
				new SequenceEntityModifier(
				new MoveModifier(2.5f, fromX, fromX+toX, fromY, fromY+toY),
				new MoveModifier(2.5f, fromX+toX, fromX, fromY+toY, fromY, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						runOnUpdateThread(new  Runnable() {
							public void run() {
								final int length17 = pSpriteHikariLastPool.length;
								for (int i = 0; i < length17; i++) {
									pSpriteHikariLastPool[i].onHandleRecycleItem(starMove);
								}
							}
						}); 
						
					}
				}))));
	}
	private void setBooleanSumon(){
		pAnimatedSetsumonAll.clearEntityModifiers();
		pAnimatedSetsumonAll.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				booleanSumon = false;
			}
		}));
	}
	//TODO Touch
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		float pX = arg1.getX();
		float pY = arg1.getY();
		if(arg1.isActionMove()){
			if(pSpriteComplete.contains(pX, pY)){
				if(pSpriteComplete.isVisible()){
					touchComplete((int)pX, (int)pY);
				}
			}
		}
		if(arg1.isActionDown()){
			if(pSpriteSetsumonBg.contains(pX, pY)){
				if(pAnimatedSetsumonAll.isVisible() && pSpriteSetsumonBg.isVisible()){
					if(pAnimatedRightAll.isVisible() && pAnimatedSetsumonAll.getCurrentTileIndex()==3 && !booleanPrikyua){
						if(!booleanSumon){
							Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
							booleanSumon = true;
							A1_HINTOGADERU.stop();
							A1_HINTOGADERU.play();
							pAnimatedSetsumonAll.setCurrentTileIndex(0);
							setBooleanSumon();
						}
					}
					if(pAnimatedRightAll.isVisible() && pAnimatedSetsumonAll.getCurrentTileIndex()==0 && !booleanPrikyua){
						if(!booleanSumon){
							booleanSumon = true;
							A1_HINTOGADERU.play();
							setBooleanSumon();
						}
					}
					if(findAnimatedSprite[0].isVisible() || findAnimatedSprite[1].isVisible() || findAnimatedSprite[2].isVisible() 
							|| findAnimatedSprite[3].isVisible() || findAnimatedSprite[4].isVisible()){
						if(pAnimatedSetsumonAll.getCurrentTileIndex()==2 && booleanIsTouchFind){
							if(!booleanSumon){
								Vol3Prikyua.this.mScene.unregisterUpdateHandler(timer4s);
								booleanSumon = true;
								A1_HITOTSUKIERU.stop();
								A1_HITOTSUKIERU.play();
								pAnimatedSetsumonAll.setCurrentTileIndex(1);
								setBooleanSumon();
							}
						}
						if(pAnimatedSetsumonAll.getCurrentTileIndex()==1 && booleanIsTouchFind){
							if(!booleanSumon){
								booleanSumon = true;
								A1_HITOTSUKIERU.play();
								setBooleanSumon();
							}
						}
					}
				}
			}
			if(!booleanPrikyua && !booleanIsTouchGimic && pAnimatedRightAll.isVisible()){
				if(randomPrikyua==0){
					///////////////////---Beauty---///////////////////////////
					if(checkContains(pAnimatedRightAll, 265,526,315,574, (int)pX, (int)pY)){
						if(!booleanBeauty[0]){
							booleanBeauty[0] = true;
							touchSpritePrikyua(pSpriteBeauty[0],481,-5);
						}
					}
					if(pSpriteBeauty[1].contains(pX, pY)){
						if(!booleanBeauty[1]){
							booleanBeauty[1] = true;
							touchSpritePrikyua(pSpriteBeauty[1],481,10);
						}
					}
					if(pSpriteBeauty[2].contains(pX, pY)){
						if(!booleanBeauty[2]){
							booleanBeauty[2] = true;
							touchSpritePrikyua(pSpriteBeauty[2],491,20);
						}
					}
					if(checkContains(pAnimatedRightAll, 223,10,260,50, (int)pX, (int)pY)){
						if(!booleanBeauty[3]){
							booleanBeauty[3] = true;
							touchSpritePrikyua(pSpriteBeauty[3],471,-15);
						}
					}
				    if(pAnimatedBeauty[0].contains(pX, pY)){
				    	if(!booleanBeauty[4]){
							booleanBeauty[4] = true;
							touchAnimatedSpritePrikyua(pAnimatedBeauty[0],491,0);
				    	}
				    }
					if(pAnimatedBeauty[1].contains(pX, pY)){
						if(!booleanBeauty[5]){
							booleanBeauty[5] = true;
							touchAnimatedSpritePrikyua(pAnimatedBeauty[1],501,5);
						}
					}
					if(pAnimatedBeauty[2].contains(pX, pY)){
						if(!booleanBeauty[6]){
							booleanBeauty[6] = true;
							touchAnimatedSpritePrikyua(pAnimatedBeauty[2],511,0);
						}
					}
					if(pAnimatedBeauty[3].contains(pX, pY)){
						if(!booleanBeauty[7]){
							booleanBeauty[7] = true;
							touchAnimatedSpritePrikyua(pAnimatedBeauty[3],475,-20);
						}
					}
				}
				if(randomPrikyua==1){
					///////////////////---Happy---////////////////////////////
					if(pSpriteHappy[0].contains(pX, pY)){
						if(!booleanHappy[0]){
							booleanHappy[0] = true;
							touchSpritePrikyua(pSpriteHappy[0],481,-20);
						}
				    }
					if(pSpriteHappy[1].contains(pX, pY)){
						if(!booleanHappy[1]){
							booleanHappy[1] = true;
							touchSpritePrikyua(pSpriteHappy[1],481,-10);
						}
				    }
					if(pSpriteHappy[2].contains(pX, pY)){
						if(!booleanHappy[2]){
							booleanHappy[2] = true;
							touchSpritePrikyua(pSpriteHappy[2],481,0);
						}
				    }
					if(pSpriteHappy[3].contains(pX, pY)){
						if(!booleanHappy[3]){
							booleanHappy[3] = true;
							touchSpritePrikyua(pSpriteHappy[3],486,-10);
						}
				    }
					if(checkContains(pAnimatedRightAll, 124,100,240,142, (int)pX, (int)pY)){
						if(!booleanHappy[4]){
							booleanHappy[4] = true;
							touchSpritePrikyua(pSpriteHappy[4],530,0);
						}
					}
				    //AnimatedSprite
					if(pAnimatedHappy[0].contains(pX, pY)){
						if(!booleanHappy[5]){
							booleanHappy[5] = true;
							touchAnimatedSpritePrikyua(pAnimatedHappy[0],481,0);
						}
				    }
					if(pAnimatedHappy[1].contains(pX, pY)){
						if(!booleanHappy[6]){
							booleanHappy[6] = true;
							touchAnimatedSpritePrikyua(pAnimatedHappy[1],481,0);
						}
				    }
					if(pAnimatedHappy[2].contains(pX, pY)){
						if(!booleanHappy[7]){
							booleanHappy[7] = true;
							touchAnimatedSpritePrikyua(pAnimatedHappy[2],481,0);
						}
				    }
				}
				if(randomPrikyua==2){
					///////////////////---March---////////////////////////////
					if(pAnimatedMarch[0].contains(pX, pY)){
						if(!booleanMarch[0]){
							booleanMarch[0] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[0],581,0);
						}
				    }
					if(pAnimatedMarch[1].contains(pX, pY)){
						if(!booleanMarch[1]){
							booleanMarch[1] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[1],481,0);
						}
				    }
					if(pAnimatedMarch[2].contains(pX, pY)){
						if(!booleanMarch[2]){
							booleanMarch[2] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[2],501,10);
						}
				    }
					if(pAnimatedMarch[3].contains(pX, pY)){
						if(!booleanMarch[3]){
							booleanMarch[3] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[3],501,0);
						}
				    }
					if(pAnimatedMarch[4].contains(pX, pY)){
						if(!booleanMarch[4]){
							booleanMarch[4] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[4],491,10);
						}
				    }
					if(pAnimatedMarch[5].contains(pX, pY)){
						if(!booleanMarch[5]){
							booleanMarch[5] = true;
							if(pAnimatedMarch[5].getCurrentTileIndex()==0){
								touchAnimatedSpritePrikyua(pAnimatedMarch[5],481,10);
							}
							if(pAnimatedMarch[5].getCurrentTileIndex()==1){
								touchAnimatedSpritePrikyua(pAnimatedMarch[5],501,-10);
							}
						}
				    }
					if(pAnimatedMarch[6].contains(pX, pY)){
						if(!booleanMarch[6]){
							booleanMarch[6] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[6],488,10);
						}
				    }
					if(pAnimatedMarch[7].contains(pX, pY)){
						if(!booleanMarch[7]){
							booleanMarch[7] = true;
							touchAnimatedSpritePrikyua(pAnimatedMarch[7],481,-15);
						}
				    }
				}
				if(randomPrikyua==3){
					///////////////////---Piece---////////////////////////////
					if(checkContains(pAnimatedRightAll, 273,464,309,494, (int)pX, (int)pY)){
						if(!booleanPiece[0]){
							booleanPiece[0] = true;
							touchSpritePrikyua(pSpritePiece[0],481,-5);
						}
				    }
					if(checkContains(pAnimatedRightAll, 120,200,161,252, (int)pX, (int)pY)){
						if(!booleanPiece[1]){
							booleanPiece[1] = true;
							touchSpritePrikyua(pSpritePiece[1],476,-10);
						}
				    }
					if(pSpritePiece[2].contains(pX, pY)){
						if(!booleanPiece[2]){
							booleanPiece[2] = true;
							touchSpritePrikyua(pSpritePiece[2],491,-10);
						}
				    }
				    //AnimatedSprite
					if(pAnimatedPiece[0].contains(pX, pY)){
						if(!booleanPiece[3]){
							booleanPiece[3] = true;
							touchAnimatedSpritePrikyua(pAnimatedPiece[0],491,-5);
						}
				    }
					if(pAnimatedPiece[1].contains(pX, pY)){
						if(!booleanPiece[4]){
							booleanPiece[4] = true;
							touchAnimatedSpritePrikyua(pAnimatedPiece[1],491,10);
						}
				    }
					if(pAnimatedPiece[2].contains(pX, pY)){
						if(!booleanPiece[5]){
							booleanPiece[5] = true;
							touchAnimatedSpritePrikyua(pAnimatedPiece[2],501,50);
						}
				    }
					if(pAnimatedPiece[3].contains(pX, pY)){
						if(!booleanPiece[6]){
							booleanPiece[6] = true;
							touchAnimatedSpritePrikyua(pAnimatedPiece[3],481,-10);
						}
				    }
					if(pAnimatedPiece[4].contains(pX, pY)){
						if(!booleanPiece[7]){
							booleanPiece[7] = true;
							touchAnimatedSpritePrikyua(pAnimatedPiece[4],521,20);
						}
				    }
				}
				if(randomPrikyua==4){
					///////////////////---Sunny---////////////////////////////
					if(pSpriteSunny[0].contains(pX, pY)){
						if(!booleanSunny[0]){
							booleanSunny[0] = true;
							touchSpritePrikyua(pSpriteSunny[0],481,-5);
						}
				    }
					if(pSpriteSunny[1].contains(pX, pY)){
						if(!booleanSunny[1]){
							booleanSunny[1] = true;
							touchSpritePrikyua(pSpriteSunny[1],491,0);
						}
				    }
					if(pSpriteSunny[2].contains(pX, pY)){
						if(!booleanSunny[2]){
							booleanSunny[2] = true;
							touchSpritePrikyua(pSpriteSunny[2],488,-10);
						}
				    }
					//AnimatedSprite
					if(pAnimatedSunny[0].contains(pX, pY)){
						if(!booleanSunny[3]){
							booleanSunny[3] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[0],481,0);
						}
				    }
					if(pAnimatedSunny[1].contains(pX, pY)){
						if(!booleanSunny[4]){
							booleanSunny[4] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[1],491,0);
						}
				    }
					if(pAnimatedSunny[2].contains(pX, pY)){
						if(!booleanSunny[5]){
							booleanSunny[5] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[2],501,0);
						}
				    }
					if(pAnimatedSunny[3].contains(pX, pY)){
						if(!booleanSunny[6]){
							booleanSunny[6] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[3],481,-10);
						}
				    }
					if(pAnimatedSunny[4].contains(pX, pY)){
						if(!booleanSunny[7]){
							booleanSunny[7] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[4],481,0);
						}
				    }
					if(pAnimatedSunny[5].contains(pX, pY)){
						if(!booleanSunny[8]){
							booleanSunny[8] = true;
							touchAnimatedSpritePrikyua(pAnimatedSunny[5],501,0);
						}
				    }
				}
			return true;
			}
		}
		return false;
	}
	private void resetValue(){
		clearObjectHikari();
    	clearObjectAtari();
    	clearObjectHikariLast();
    	countPrikyua = 5;
    	countChange = 0;
    	countFindSprite = 5;
    	coutTouchFind = 0;
        booleanPrikyua = false;
        booleanIsTouchGimic = false;
        booleanIsTouchFind = false;
        isDifference6s = true;
        isFinded6s = true;
        booleanSumon = false;
        pLastX = 0;
        pLastY = 0;
        soundMod = 0;
    	setTouchGimmic3(true);
    	final int length18 = booleanSunny.length;
    	for(int i = 0;i< length18;i++){
    		if(i < (length18-1)){
    			booleanBeauty[i] = false;
    			booleanHappy[i] = false;
    			booleanMarch[i] = false;
    			booleanPiece[i] = false;
    		}
    		if(i < 5){
    			findAnimatedPrikyua[i] = false;
    		}
    		booleanSunny[i] = false;
    	}
    	if(listpSpriteAtari!=null){
			listpSpriteAtari.clear();
		}
		if(listpSpriteHikari!=null){
			listpSpriteHikari.clear();
		}
		final int length19 = listpSpriteHikariLast.length;
		for(int i = 0;i< length19;i++){
			if(listpSpriteHikariLast[i]!=null){
				listpSpriteHikariLast[i].clear();
			}
		}
	}
	//TODO onPauseGame
    @Override
    public void onPauseGame() {
    	Log.d(TAG,"onPauseGame");
    	super.onPauseGame();
    	stopSounds(A1_KONNICHIWA,
    	    	A1_KAGEKIERU2,A1_DOKOGATIGAU,A1_ONAJIKATATI,A1_HINTOGADERU,
    	    	A1_YOKUMITENE,A1_D_PACHI_YUBI,A1_ZANNEN,A1_3SYARA,A1_TENKAN,A1_TUGIHASIRUETO,
    	    	A1_TIGAUTOKORO,A1_SUTEKI,A1_HITOTSUKIERU,A1_SAISYOHA,A1_YATANE_OMEDETO,
    	    	A1_START,A1_ATOSUKOSHI,A1_SEIKAI,A1_KIRAN);
    	clearAll();
    	randNoRepeat.clearTmp();
    	randNoRepeat.setLenghNoRepeat(5);
    	gimicFind.clearTmp();
    	gimicFind.setLenghNoRepeat(5);
    	randBeauty.clearTmp();
    	randBeauty.setLenghNoRepeat(6);
    	randHappy.clearTmp();
    	randHappy.setLenghNoRepeat(6);
    	randMarch.clearTmp();
    	randMarch.setLenghNoRepeat(6);
    	randPiece.clearTmp();
    	randPiece.setLenghNoRepeat(6);
    	randSunny.clearTmp();
    	randSunny.setLenghNoRepeat(6);
    	resetValue();
		if(listpSpriteAtari!=null){
			listpSpriteAtari.clear();
		}
		if(listpSpriteHikari!=null){
			listpSpriteHikari.clear();
		}
		final int length20 = listpSpriteHikariLast.length;
		for(int i = 0;i< length20;i++){
			if(listpSpriteHikariLast[i]!=null){
				listpSpriteHikariLast[i].clear();
			}
		}
    	if(timer4s!=null){
    		this.mScene.unregisterUpdateHandler(timer4s);
    		timer4s = null;
    	}
    	if(timerDifference6s!=null){
    		this.mScene.unregisterUpdateHandler(timerDifference6s);
    		timerDifference6s = null;
    	}
    	if(timerFinded6s!=null){
    		this.mScene.unregisterUpdateHandler(timerFinded6s);
    		timerFinded6s = null;
    	}
    	if(timerDifference!=null){
    		this.mScene.unregisterUpdateHandler(timerDifference);
    		timerDifference = null;
    	}
    	if(timerFinded!=null){
    		this.mScene.unregisterUpdateHandler(timerFinded);
    		timerFinded = null;
    	}
    	if(this.mScene!=null){
    		this.mScene.clearEntityModifiers();
    		this.mScene.clearUpdateHandlers();
    	}
    	if(pSpriteAtari!=null){
    		pSpriteAtari = null;
    	}
    	if(pSpriteHikari!=null){
        	pSpriteHikari = null;
    	}
    	final int length21 = pSpriteHikariLastPool.length;
    	for(int i = 0;i< length21;i++){
    		if(pSpriteHikariLastPool[i]!=null){
    			pSpriteHikariLastPool[i] = null;
    		}
    	}
    	
    }
    private void stopSounds(Sound ...sounds){
    	final int snd = sounds.length;
    	for(int i = 0; i< snd; i++){
    		if(sounds[i]!=null){
    			sounds[i].stop();
    		}
    	}
    }
    private void beginGame(){
        //Sprite Pool Atari
		pSpriteAtari = new AtariSprite(iTextAtari);
		//Sprite Pool Hikari
		pSpriteHikari = new AtariSprite(iTextHikari);
		//Sprite Pool HikariLastPool
		final int length22 = pSpriteHikariLastPool.length;
		for (int i = 0; i < length22; i++) {
			pSpriteHikariLastPool[i] = new AtariSprite(iTextHikariLast[i]);
		}
        setVisibleAll();
        A1_KONNICHIWA.play();
        //Hien thi back ground text
        //Doi sau 5s phat am thanh tiep theo
        setVisibleSpriteArray(true, pSpriteStart);
        pSpriteStart.clearEntityModifiers();
        pSpriteStart.registerEntityModifier(new DelayModifier(5.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				//Sau 5s se phat am thanh nay
				A1_SAISYOHA.play();
				//Sau 1s khi A1_SAISYOHA ket thuc
				pSpriteStart.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						pSpriteStart_Text.setScale(1.0f);
						setVisibleSpriteArray(true, pSpriteStart_Text);
						A1_START.play();
						pSpriteStart_Text.clearEntityModifiers();
						pSpriteStart_Text.registerEntityModifier( new ParallelEntityModifier(
								new IEntityModifierListener() {
									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier, IEntity pItem) {
										pSpriteStart_Text.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												// TODO Auto-generated method stub
											}
											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												//an start
												setVisibleSpriteArray(false,pSpriteStart_Text,pSpriteStart);
												setVisibleSpriteArray(true,Vol3Prikyua.this.sprGimmic[2]);
												//Bat dau man hinh game tim diem khac
												randomPrikyua();
											}
										}));
									}
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0,
											IEntity arg1) {
									}
								},
								new SequenceEntityModifier(
									new ScaleModifier(1.0f,
											1f, 2.0f)
								)
							)
						);						
					}
				}));
			}
		}));
    }
    //TODO onResumeGame
	@Override
    public synchronized void onResumeGame() {
		Log.d(TAG,"onResumeGame");
        super.onResumeGame();
        this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}
			@Override
			public void onUpdate(float arg0) {
				try{
		        	beginGame = WINDOW_FOCUS_CHANGE;
		        }catch (Exception e) {
		        	beginGame = true;
				}
				if(beginGame){
					Vol3Prikyua.this.mScene.clearUpdateHandlers();
		        	beginGame();
		        }
			}
		});
	}
}
