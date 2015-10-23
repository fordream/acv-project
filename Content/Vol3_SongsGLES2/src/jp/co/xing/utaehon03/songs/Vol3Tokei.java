package jp.co.xing.utaehon03.songs;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TokeiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseLinear;

import android.graphics.Typeface;
import android.text.format.Time;
import android.util.FloatMath;
import android.util.Log;

public class Vol3Tokei extends BaseGameActivity implements IOnSceneTouchListener,
IAnimationListener, IEntityModifierListener{
	//Define tag log for this class
    private String TAG = Vol3Tokei.this.getClass().toString();
    //Texture Packer
    private TexturePackLoaderFromSource mTexturePackLoaderFromSourceTokei;
    private TexturePack Vol3TokeiPacker_1, Vol3TokeiPacker_2, Vol3TokeiPacker_3, Vol3TokeiPacker_4, Vol3TokeiPacker_5;
    private TexturePackTextureRegionLibrary Vol3TokeiLibrary1, Vol3TokeiLibrary2, Vol3TokeiLibrary3, Vol3TokeiLibrary4;
    //Clock
    private ITextureRegion iTextureRegionMainClock, iTextureRegionSubClock1, iTextureRegionSubClock2, iTextureRegionSubClock3,iTextureRegionChangeClock1;
    private Sprite mSpriteMainClock, mSpriteSubClock1, mSpriteSubClock2, mSpriteSubClock3, mSpriteChangeClock1;
    //Clock and Background
    private TiledTextureRegion iTiledTextureRegionBackground,  iTextureRegionChangeClock2, iTextureRegionChangeClock3;
    private AnimatedSprite mAnimatedSpriteBackground;
    private AnimatedSprite mAnimatedSpriteChangeClock2,mAnimatedSpriteChangeClock3;
    //Door
    private TiledTextureRegion iTiledTextureRegionLeftDoor, iTiledTextureRegionRightDoor;
    private AnimatedSprite mAnimatedSpriteLeftDoor,mAnimatedSpriteRightDoor;
    private Sprite mSpriteDoor,mSpriteGirlDoor, mSpriteBoyDoor;
    private TiledTextureRegion iTiledTextureRegionRetro;
    private AnimatedSprite mAnimatedSpriteRetro;
    //Text Font
    private BitmapTextureAtlas mFontTexture;
    private Font mFont;
	private Text pTextHour;
	private Text pTextMinutes;
	//Init variable
    private int rOpenLeftDoor = 3, rOpenRightDoor = 3;
    private boolean bOpenLeftDoor = false, bOpenRightDoor = false;
    private float iMinutes, iHour, iTotalMinutes;
    private String sMinutes="", sHour="";
    private boolean isMoveClock = false, isTouchAm = false, isTouchPm = false;
    private Time mCalendar;
    private int hour, minute;
	private final float fMinute_X = 655, fMinute_Y = 275;
	private boolean isTouchAM7 = false, isTouchAM8 = false, isTouchAM9 = false, isTouchAM10 = false,
		isTouchAM12 = false, isTouchPM13 = false, isTouchPM15 = false, isTouchPM16 = false, isTouchPM18 = false, isTouchPM19 = false, isTouchPM20 = false, isTouchPM21 = false;
	private boolean isTouchDreamA = false, isTouchDreamB = false, isTouchDreamC = false;
	private int touchPM16 = 0, touchPM20 = 0;
	//Dream background ABC
	private TiledTextureRegion  iTiledTextureRegionDreamABC;
	private AnimatedSprite mAnimatedSpriteDreamABC;
	//Dream A
	private ITextureRegion ITextureRegionDreamA_A;
	private TiledTextureRegion  iTiledTextureRegionDreamA_B;
	private Sprite mSpriteDreamA_A;
	private AnimatedSprite mAnimatedSpriteDreamA_B;
	//Dream B
	private ITextureRegion ITextureRegionDreamB_A;
	private TiledTextureRegion  iTiledTextureRegionDreamB_B;
	private Sprite mSpriteDreamB_A;
	private AnimatedSprite mAnimatedSpriteDreamB_B;
	//Dream C
	private ITextureRegion ITextureRegionDreamC_A, ITextureRegionDreamC_B, ITextureRegionDreamC_C;
	private TiledTextureRegion  iTiledTextureRegionDreamC_D;
	private Sprite mSpriteDreamC_A, mSpriteDreamC_B, mSpriteDreamC_C;
	private AnimatedSprite mAnimatedSpriteDreamC_D;
	//Booka boy Red
	private TiledTextureRegion  iTiledTextureRegionBookaBoyRed;
	private AnimatedSprite mAnimatedSpriteBookaBoyRed;
	//Booka boy green
	private TiledTextureRegion  iTiledTextureRegionBookaBoyGreen;
	private AnimatedSprite mAnimatedSpriteBookaBoyGreen;
	//Booka girl green
	private TiledTextureRegion  iTiledTextureRegionBookaGirlGreen;
	private AnimatedSprite mAnimatedSpriteBookaGirlGreen;
	//Bookb girl purple
	private TiledTextureRegion  iTiledTextureRegionBookbGirlPurple;
	private AnimatedSprite mAnimatedSpriteBookbGirlPurple;
	//AM7
	private TiledTextureRegion  iTiledTextureRegionAM7;
	private AnimatedSprite mAnimatedSpriteAM7;
	//AM8
	private TiledTextureRegion  iTiledTextureRegionAM8;
	private AnimatedSprite mAnimatedSpriteAM8;
	//AM9
	private TiledTextureRegion  iTiledTextureRegionAM9;
	private AnimatedSprite mAnimatedSpriteAM9;
	//AM10_A
	private ITextureRegion ITextureRegionAM10_A;
	private Sprite mSpriteAM10_A1, mSpriteAM10_A2, mSpriteAM10_A3;
	//AM10_B
	private ITextureRegion ITextureRegionAM10_B;
	private Sprite mSpriteAM10_B1, mSpriteAM10_B2, mSpriteAM10_B3;
	//AM10_C
	private ITextureRegion ITextureRegionAM10_C;
	private Sprite mSpriteAM10_C1, mSpriteAM10_C2, mSpriteAM10_C3;
	//AM10_D
	private TiledTextureRegion  iTiledTextureRegionAM10_D;
	private AnimatedSprite mAnimatedSpriteAM10_D;
	//AM12
	private TiledTextureRegion  iTiledTextureRegionAM12;
	private AnimatedSprite mAnimatedSpriteAM12;
	//PM13
	private TiledTextureRegion  iTiledTextureRegionPM13;
	private AnimatedSprite mAnimatedSpritePM13;
	//PM15
	private ITextureRegion  iTextureRegionPM15;
	private Sprite mSpritePM15;
	//PM16
	private TiledTextureRegion  iTiledTextureRegionPM16;
	private AnimatedSprite mAnimatedSpritePM16;
	//PM18
	private TiledTextureRegion  iTiledTextureRegionPM18;
	private AnimatedSprite mAnimatedSpritePM18;
	//PM19
	private TiledTextureRegion  iTiledTextureRegionPM19;
	private AnimatedSprite mAnimatedSpritePM19;
	//PM19_A
	private ITextureRegion  iTextureRegionPM19_A;
	private Sprite mSpritePM19_A;
	//PM19_B
	private ITextureRegion  iTextureRegionPM19_B;
	private Sprite mSpritePM19_B;
	//PM20
	private ITextureRegion  iTextureRegionPM20;
	private Sprite mSpritePM20;
	//PM21
	private TiledTextureRegion  iTiledTextureRegionPM21;
	private AnimatedSprite mAnimatedSpritePM21;
	//Background AM-PM from 0h to 23h
	private TiledTextureRegion  iTiledTextureRegionBG_AM_PM_0_23;
	private AnimatedSprite mAnimatedSpriteBG_AM_PM_0_23;
	//Itadakimasu
	private ITextureRegion  iTextureRegionItadakimasu;
	private Sprite mSpriteItadakimasu;
	private Random rand;
	private int randDream15PM, randDream21PM, rand18PM, rand19PM = 0,rand19PMA = 0, randBoyGirlDoor, randNote;
	private boolean backwardTime = true;
	private boolean boyDoor = false, girlDoor = false;
	private TimerHandler times;
	private float iTempMinutes;
	//Sound
	private Sound A4_A2_UHU,A4_10_REMIFA,A4_8_MOGU,A4_1_GYAKU_HARI,A4_B2_ARIGATO,A4_5_2_HAIHILU,A4_13_KOROBU
	,A4_1_HARI,A4_15_KIRA10,A4_2_GOGO,A4_21_MUNYA,A4_5_1_KOBITO,A4_16_EHON,A4_19A_IIYUDANE,A4_18B_TABERU2,A4_7_OHAYO
	,A4_19B_ATAMAARAU,A4_3_ASHIATO,A4_3_HATO,A4_8_GOCHISOSAMA,A4_8_ITADAKI1,A4_2_GOZEN,A4_9_ITEKIMASU,A4_B2_KONCHIWA
	,A4_C2_HENSHIN,A4_19A_ATAKAI,A4_7_HAMIGAKI,A4_21_NEIKI_MUNYA,A4_10_DOREMI,A4_13_ITAI,A4_3_TOBIRA;
	private int DRM_RMF =0;
	private int DreamB = 0;
	private int touch_Gimic = 0;
    //TODO Create Resource
    @Override
    public void onCreateResources() {
        Log.d(TAG,"on Create Rerource");
        //Create Sound Factory
        SoundFactory.setAssetBasePath("tokei/mfx/");
        //Create Bitmap Factory
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tokei/gfx/");
        //Create Texture Packer
        mTexturePackLoaderFromSourceTokei = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "tokei/gfx/");
        //Create Font
        mFontTexture = new BitmapTextureAtlas(getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mFont = new Font(this.getFontManager(), mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, true, Color.WHITE);
        this.mEngine.getTextureManager().loadTexture(mFontTexture);
        this.getFontManager().loadFont(mFont);
        //Create Time
        mCalendar = new Time();
        super.onCreateResources();
    }
	//TODO Load Sounds
	@Override
	protected void loadKaraokeSound() {
		A4_A2_UHU = loadSoundResourceFromSD(Vol3TokeiResource.A4_A2_UHU);
		A4_10_REMIFA = loadSoundResourceFromSD(Vol3TokeiResource.A4_10_REMIFA);
		A4_8_MOGU = loadSoundResourceFromSD(Vol3TokeiResource.A4_8_MOGU);
		A4_1_GYAKU_HARI = loadSoundResourceFromSD(Vol3TokeiResource.A4_1_GYAKU_HARI);
		A4_B2_ARIGATO = loadSoundResourceFromSD(Vol3TokeiResource.A4_B2_ARIGATO);
		A4_5_2_HAIHILU = loadSoundResourceFromSD(Vol3TokeiResource.A4_5_2_HAIHILU);
		A4_13_KOROBU = loadSoundResourceFromSD(Vol3TokeiResource.A4_13_KOROBU);
		A4_1_HARI = loadSoundResourceFromSD(Vol3TokeiResource.A4_1_HARI);
		A4_15_KIRA10 = loadSoundResourceFromSD(Vol3TokeiResource.A4_15_KIRA10);
		A4_2_GOGO = loadSoundResourceFromSD(Vol3TokeiResource.A4_2_GOGO);
		A4_21_MUNYA = loadSoundResourceFromSD(Vol3TokeiResource.A4_21_MUNYA);
		A4_5_1_KOBITO = loadSoundResourceFromSD(Vol3TokeiResource.A4_5_1_KOBITO);
		A4_16_EHON = loadSoundResourceFromSD(Vol3TokeiResource.A4_16_EHON);
		A4_19A_IIYUDANE = loadSoundResourceFromSD(Vol3TokeiResource.A4_19A_IIYUDANE);
		A4_18B_TABERU2 = loadSoundResourceFromSD(Vol3TokeiResource.A4_18B_TABERU2);
		A4_7_OHAYO = loadSoundResourceFromSD(Vol3TokeiResource.A4_7_OHAYO);
		A4_19B_ATAMAARAU = loadSoundResourceFromSD(Vol3TokeiResource.A4_19B_ATAMAARAU);
		A4_3_ASHIATO = loadSoundResourceFromSD(Vol3TokeiResource.A4_3_ASHIATO);
		A4_3_HATO = loadSoundResourceFromSD(Vol3TokeiResource.A4_3_HATO);
		A4_8_GOCHISOSAMA = loadSoundResourceFromSD(Vol3TokeiResource.A4_8_GOCHISOSAMA);
		A4_8_ITADAKI1 = loadSoundResourceFromSD(Vol3TokeiResource.A4_8_ITADAKI1);
		A4_2_GOZEN = loadSoundResourceFromSD(Vol3TokeiResource.A4_2_GOZEN);
		A4_9_ITEKIMASU = loadSoundResourceFromSD(Vol3TokeiResource.A4_9_ITEKIMASU);
		A4_B2_KONCHIWA = loadSoundResourceFromSD(Vol3TokeiResource.A4_B2_KONCHIWA);
		A4_C2_HENSHIN = loadSoundResourceFromSD(Vol3TokeiResource.A4_C2_HENSHIN);
		A4_19A_ATAKAI = loadSoundResourceFromSD(Vol3TokeiResource.A4_19A_ATAKAI);
		A4_7_HAMIGAKI = loadSoundResourceFromSD(Vol3TokeiResource.A4_7_HAMIGAKI);
		A4_21_NEIKI_MUNYA = loadSoundResourceFromSD(Vol3TokeiResource.A4_21_NEIKI_MUNYA);
		A4_10_DOREMI = loadSoundResourceFromSD(Vol3TokeiResource.A4_10_DOREMI);
		A4_13_ITAI = loadSoundResourceFromSD(Vol3TokeiResource.A4_13_ITAI);
		A4_3_TOBIRA = loadSoundResourceFromSD(Vol3TokeiResource.A4_3_TOBIRA);
	}
    //TODO Load Resources
	@Override
	protected void loadKaraokeResources() {
		//Load Texture Packer
		Vol3TokeiPacker_1 = mTexturePackLoaderFromSourceTokei.load("Vol3TokeiPacker1.xml");
		Vol3TokeiPacker_1.loadTexture();
		Vol3TokeiLibrary1 = Vol3TokeiPacker_1.getTexturePackTextureRegionLibrary();
		
		Vol3TokeiPacker_2 = mTexturePackLoaderFromSourceTokei.load("Vol3TokeiPacker2.xml");
		Vol3TokeiPacker_2.loadTexture();
		Vol3TokeiLibrary2 = Vol3TokeiPacker_2.getTexturePackTextureRegionLibrary();
		
		Vol3TokeiPacker_3 = mTexturePackLoaderFromSourceTokei.load("Vol3TokeiPacker3.xml");
		Vol3TokeiPacker_3.loadTexture();
		Vol3TokeiLibrary3 = Vol3TokeiPacker_3.getTexturePackTextureRegionLibrary();
		
		Vol3TokeiPacker_4 = mTexturePackLoaderFromSourceTokei.load("Vol3TokeiPacker4.xml");
		Vol3TokeiPacker_4.loadTexture();
		Vol3TokeiLibrary4 = Vol3TokeiPacker_4.getTexturePackTextureRegionLibrary();
		
		Vol3TokeiPacker_5 = mTexturePackLoaderFromSourceTokei.load("Vol3TokeiPacker5.xml");
		Vol3TokeiPacker_5.loadTexture();
		
		//Assign memory area for TextureRegion and TiledTextureRegion
		iTiledTextureRegionBackground = getTiledTextureFromPacker(Vol3TokeiPacker_2,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_21_1_1_IPHONE_ASA_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_21_1_2_IPHONE_HIRU_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_21_1_3_IPHONE_YORU_ID
		);
		//Clock
		iTextureRegionMainClock = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_1_1_IPHONE_CLOCK_ID);
		iTextureRegionSubClock1 = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_1_2_IPHONE_CLOCK_ID);
		iTextureRegionSubClock2 = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_1_3_IPHONE_CLOCK_ID);
		iTextureRegionSubClock3 = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_1_4_IPHONE_CLOCK_ID);
		iTextureRegionChangeClock1 = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_2_1_IPHONE_D_CLOCK_ID);
		iTextureRegionChangeClock2 = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
														Vol3TokeiResource.Vol3TokeiPacker1.A4_2_2_IPHONE_D_CLOCK_ID,
														Vol3TokeiResource.Vol3TokeiPacker1.A4_2_3_IPHONE_D_CLOCK_ID
		);
		//Background AM-PM from 0h den 12h
		iTiledTextureRegionBG_AM_PM_0_23 = getTiledTextureFromPacker(Vol3TokeiPacker_4, 
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_1_IPHONE_AM0_4_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_2_IPHONE_AM5_6_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_3_IPHONE_AM7_9_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_4_IPHONE_AM10_12_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_5_IPHONE_PM13_15_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_6_IPHONE_PM16_17_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_7_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_8_IPHONE_PM19_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_9_IPHONE_PM20_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_10_IPHONE_PM21_ID,
				Vol3TokeiResource.Vol3TokeiPacker4.A4_22_11_IPHONE_PM22_23_ID
				);
		iTextureRegionChangeClock3 = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_2_4_IPHONE_D_CLOCK_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_2_5_IPHONE_D_CLOCK_ID
		);
		//Door
		iTiledTextureRegionLeftDoor = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_1_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_2_1_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_2_2_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_3_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_4_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_5_IPHONE_LEFT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_6_IPHONE_LEFT_DOOR_ID
		);

		iTiledTextureRegionRightDoor = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_7_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_8_1_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_8_2_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_9_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_10_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_11_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_12_IPHONE_RIGHT_DOOR_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_3_13_IPHONE_DOOR_ID
		);
		//Retro
		iTiledTextureRegionRetro = getTiledTextureFromPacker(Vol3TokeiPacker_5,
					Vol3TokeiResource.Vol3TokeiPacker5.A4_23_1_IPHONE_RETRO_ID,
					Vol3TokeiResource.Vol3TokeiPacker5.A4_23_2_IPHONE_RETRO_ID
		);
		//Dream Background ABC
		iTiledTextureRegionDreamABC = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_1_1_IPHONE_DREAMA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_1_IPHONE_DREAMB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_1_IPHONE_DREAMC_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_1_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_2_1_IPHONE_BOOKB_ID
				);
		
		//Dream A
		ITextureRegionDreamA_A = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_4_1_2_IPHONE_DREAMA_ID);
		iTiledTextureRegionDreamA_B = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_1_3_IPHONE_DREAMA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_1_4_IPHONE_DREAMA_ID);
		//Dream B
		ITextureRegionDreamB_A = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_2_IPHONE_DREAMB_ID);
		iTiledTextureRegionDreamB_B = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_3_IPHONE_DREAMB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_4_IPHONE_DREAMB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_5_IPHONE_DREAMB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_6_IPHONE_DREAMB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_2_7_IPHONE_DREAMB_ID);
		//Dream C
		ITextureRegionDreamC_A = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_2_IPHONE_DREAMC_ID);
		ITextureRegionDreamC_B = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_3_IPHONE_DREAMC_ID);
		ITextureRegionDreamC_C = Vol3TokeiLibrary1.get(Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_4_IPHONE_DREAMC_ID);
		iTiledTextureRegionDreamC_D = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_5_IPHONE_DREAMC_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_4_3_6_IPHONE_DREAMC_ID
				);
		//Booka boy red
		iTiledTextureRegionBookaBoyRed = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_2_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_3_IPHONE_BOOKA_ID
				);
		//Booka boy green
		iTiledTextureRegionBookaBoyGreen = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_4_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_5_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_6_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_7_IPHONE_BOOKA_ID
				);
		//Booka girl green
		iTiledTextureRegionBookaGirlGreen = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_8_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_9_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_10_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_11_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_12_IPHONE_BOOKA_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_1_13_IPHONE_BOOKA_ID
				);
		//Bookb girl purple
		iTiledTextureRegionBookbGirlPurple = getTiledTextureFromPacker(Vol3TokeiPacker_1, 
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_2_2_IPHONE_BOOKB_ID,
				Vol3TokeiResource.Vol3TokeiPacker1.A4_5_2_3_IPHONE_BOOKB_ID
				);
		//AM7
		iTiledTextureRegionAM7 = getTiledTextureFromPacker(Vol3TokeiPacker_2, 
				Vol3TokeiResource.Vol3TokeiPacker2.A4_7_1_IPHONE_AM7_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_7_2_IPHONE_AM7_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_7_3_IPHONE_AM7_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_7_4_IPHONE_AM7_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_7_5_IPHONE_AM7_ID
				);
		//AM8
		iTiledTextureRegionAM8 = getTiledTextureFromPacker(Vol3TokeiPacker_2, 
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_1_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_2_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_3_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_4_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_5_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_6_IPHONE_AM8_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_8_7_IPHONE_AM8_ID
				);
		//AM9
		iTiledTextureRegionAM9 = getTiledTextureFromPacker(Vol3TokeiPacker_2, 
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_1_IPHONE_AM9_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_2_IPHONE_AM9_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_3_IPHONE_AM9_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_4_IPHONE_AM9_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_5_IPHONE_AM9_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_9_6_IPHONE_AM9_ID
				);
		//AM10_A
		ITextureRegionAM10_A = Vol3TokeiLibrary2.get(Vol3TokeiResource.Vol3TokeiPacker2.A4_10_1_IPHONE_AM10_ID);
		//AM10_B
		ITextureRegionAM10_B = Vol3TokeiLibrary2.get(Vol3TokeiResource.Vol3TokeiPacker2.A4_10_2_IPHONE_AM10_ID);
		//AM10_C
		ITextureRegionAM10_C = Vol3TokeiLibrary2.get(Vol3TokeiResource.Vol3TokeiPacker2.A4_10_3_IPHONE_AM10_ID);
		//AM10_D
		iTiledTextureRegionAM10_D = getTiledTextureFromPacker(Vol3TokeiPacker_2, 
				Vol3TokeiResource.Vol3TokeiPacker2.A4_10_4_IPHONE_AM10_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_10_5_IPHONE_AM10_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_10_6_IPHONE_AM10_ID
				);
		//AM12
		iTiledTextureRegionAM12 = getTiledTextureFromPacker(Vol3TokeiPacker_2, 
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_1_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_2_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_3_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_4_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_5_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_6_IPHONE_AM12_ID,
				Vol3TokeiResource.Vol3TokeiPacker2.A4_12_7_IPHONE_AM12_ID
				);
		//PM13
		iTiledTextureRegionPM13 = getTiledTextureFromPacker(Vol3TokeiPacker_3, 
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_1_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_2_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_3_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_4_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_5_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_6_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_7_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_8_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_9_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_10_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_11_IPHONE_PM13_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_13_12_IPHONE_PM13_ID
				);
		//PM15
		iTextureRegionPM15 = Vol3TokeiLibrary3.get(Vol3TokeiResource.Vol3TokeiPacker3.A4_15_IPHONE_PM15_ID);
		//PM16
		iTiledTextureRegionPM16 = getTiledTextureFromPacker(Vol3TokeiPacker_3, 
				Vol3TokeiResource.Vol3TokeiPacker3.A4_16_1_IPHONE_PM16_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_16_2_IPHONE_PM16_ID
				);
		//PM18
		iTiledTextureRegionPM18 = getTiledTextureFromPacker(Vol3TokeiPacker_3, 
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18_1_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18A_1_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18A_2_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18A_3_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18A_4_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18A_5_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18B_1_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18B_2_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18B_3_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18B_4_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18B_5_IPHONE_PM18_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_18_2_IPHONE_PM18_ID
				);
		//PM19
		iTiledTextureRegionPM19 = getTiledTextureFromPacker(Vol3TokeiPacker_3, 
				Vol3TokeiResource.Vol3TokeiPacker3.A4_19_1_IPHONE_PM19_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_19_2_IPHONE_PM19_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_19_3_IPHONE_PM19_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_19_4_IPHONE_PM19_ID
				);
		//PM19_A
		iTextureRegionPM19_A = Vol3TokeiLibrary3.get(Vol3TokeiResource.Vol3TokeiPacker3.A4_19_5_IPHONE_PM19_ID);
		//PM19_B
		iTextureRegionPM19_B = Vol3TokeiLibrary3.get(Vol3TokeiResource.Vol3TokeiPacker3.A4_19_6_IPHONE_PM19_ID);
		//PM20
		iTextureRegionPM20 = Vol3TokeiLibrary3.get(Vol3TokeiResource.Vol3TokeiPacker3.A4_20_IPHONE_PM20_ID);
		//PM21
		iTiledTextureRegionPM21 = getTiledTextureFromPacker(Vol3TokeiPacker_3, 
				Vol3TokeiResource.Vol3TokeiPacker3.A4_21_1_IPHONE_PM21_ID,
				Vol3TokeiResource.Vol3TokeiPacker3.A4_21_2_IPHONE_PM21_ID
				);
		//Itadakimasu
		iTextureRegionItadakimasu = Vol3TokeiLibrary4.get(Vol3TokeiResource.Vol3TokeiPacker4.A4_ITADAKIMASU_IPHONE_ID);
	}
	//TODO Load Scenes
	@Override
	protected void loadKaraokeScene() {
		//Load Scene
		this.mScene = new Scene();
		rand = new Random();
		mAnimatedSpriteBackground = new AnimatedSprite(0, 0, iTiledTextureRegionBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBackground);
		//Background AM-PM from 0h to 23h
		mAnimatedSpriteBG_AM_PM_0_23 = new AnimatedSprite(41, 78, iTiledTextureRegionBG_AM_PM_0_23, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBG_AM_PM_0_23);
		mAnimatedSpriteBG_AM_PM_0_23.setVisible(false);
		//AM7
		mAnimatedSpriteAM7 = new AnimatedSprite(119, 216, iTiledTextureRegionAM7, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteAM7);
		mAnimatedSpriteAM7.setVisible(false);
		//AM8
		mAnimatedSpriteAM8 = new AnimatedSprite(144, 212, iTiledTextureRegionAM8, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteAM8);
		mAnimatedSpriteAM8.setVisible(false);
		//AM9
		mAnimatedSpriteAM9 = new AnimatedSprite(161, 230, iTiledTextureRegionAM9, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteAM9);
		mAnimatedSpriteAM9.setVisible(false);
		//AM10_A
		mSpriteAM10_A1 = new Sprite(88, 268, ITextureRegionAM10_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_A1);
		mSpriteAM10_A1.setVisible(false);
		
		mSpriteAM10_A2 = new Sprite(108, 268, ITextureRegionAM10_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_A2);
		mSpriteAM10_A2.setVisible(false);
		
		mSpriteAM10_A3 = new Sprite(138, 268, ITextureRegionAM10_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_A3);
		mSpriteAM10_A3.setVisible(false);
		//AM10_B
		mSpriteAM10_B1  = new Sprite(88, 268, ITextureRegionAM10_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_B1);
		mSpriteAM10_B1.setVisible(false);
		
		mSpriteAM10_B2  = new Sprite(108, 268, ITextureRegionAM10_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_B2);
		mSpriteAM10_B2.setVisible(false);
		
		mSpriteAM10_B3  = new Sprite(138, 268, ITextureRegionAM10_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_B3);
		mSpriteAM10_B3.setVisible(false);
		//AM10_C
		mSpriteAM10_C1  = new Sprite(88, 268, ITextureRegionAM10_C, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_C1);
		mSpriteAM10_C1.setVisible(false);
		
		mSpriteAM10_C2  = new Sprite(108, 268, ITextureRegionAM10_C, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_C2);
		mSpriteAM10_C2.setVisible(false);
		
		mSpriteAM10_C3  = new Sprite(138, 268, ITextureRegionAM10_C, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteAM10_C3);
		mSpriteAM10_C3.setVisible(false);
		//AM10_D
		mAnimatedSpriteAM10_D = new AnimatedSprite(65, 236, iTiledTextureRegionAM10_D, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteAM10_D);
		mAnimatedSpriteAM10_D.setVisible(false);
		//AM12
		mAnimatedSpriteAM12 = new AnimatedSprite(144, 214, iTiledTextureRegionAM12, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteAM12);
		mAnimatedSpriteAM12.setVisible(false);
		//PM13
		mAnimatedSpritePM13 = new AnimatedSprite(82, 215, iTiledTextureRegionPM13, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePM13);
		mAnimatedSpritePM13.setVisible(false);
		//PM15
		mSpritePM15 = new Sprite(14, 376, iTextureRegionPM15, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpritePM15);
		mSpritePM15.setVisible(false);
		//PM16
		mAnimatedSpritePM16 = new AnimatedSprite(72, 360, iTiledTextureRegionPM16, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePM16);
		mAnimatedSpritePM16.setVisible(false);
		//PM18
		mAnimatedSpritePM18 = new AnimatedSprite(144, 211, iTiledTextureRegionPM18, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePM18);
		mAnimatedSpritePM18.setVisible(false);
		//PM19
		mAnimatedSpritePM19 = new AnimatedSprite(90, 259, iTiledTextureRegionPM19, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePM19);
		mAnimatedSpritePM19.setVisible(false);
		//PM19_A
		mSpritePM19_A = new Sprite(104, 340, iTextureRegionPM19_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpritePM19_A);
		mSpritePM19_A.setVisible(false);
		//PM19_B
		mSpritePM19_B = new Sprite(316, 308, iTextureRegionPM19_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpritePM19_B);
		mSpritePM19_B.setVisible(false);
		//PM20
		mSpritePM20 = new Sprite(60, 307, iTextureRegionPM20, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpritePM20);
		mSpritePM20.setVisible(false);
		//PM21
		mAnimatedSpritePM21 = new AnimatedSprite(95, 378, iTiledTextureRegionPM21, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePM21);
		mAnimatedSpritePM21.setVisible(false);
		//Itadakimasu
		mSpriteItadakimasu = new Sprite(144, 211, iTextureRegionItadakimasu, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteItadakimasu);
		mSpriteItadakimasu.setVisible(false);
		//Dream ABC
		mAnimatedSpriteDreamABC = new AnimatedSprite(-70, -137, iTiledTextureRegionDreamABC, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteDreamABC); 
		mAnimatedSpriteDreamABC.setVisible(false);
		//mAnimatedSpriteDreamABC.setPosition(-92, -137);//Toa do nay dung cho current 3 va 4
		//mAnimatedSpriteDreamABC.setCurrentTileIndex(4);
		//Dream A
		mSpriteDreamA_A = new Sprite(100, 46, ITextureRegionDreamA_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDreamA_A);
		mSpriteDreamA_A.setVisible(false);
		mAnimatedSpriteDreamA_B = new AnimatedSprite(160, 47, iTiledTextureRegionDreamA_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteDreamA_B);
		mAnimatedSpriteDreamA_B.setVisible(false);
		//Dream B
		mSpriteDreamB_A = new Sprite(100, 46, ITextureRegionDreamB_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDreamB_A);
		mSpriteDreamB_A.setVisible(false);
		mAnimatedSpriteDreamB_B = new AnimatedSprite(154, 43, iTiledTextureRegionDreamB_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteDreamB_B);
		mAnimatedSpriteDreamB_B.setVisible(false);
		//Dream C
		mSpriteDreamC_A = new Sprite(45, -15, ITextureRegionDreamC_A, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDreamC_A);
		mSpriteDreamC_A.setVisible(false);
		mSpriteDreamC_B = new Sprite(14, 0, ITextureRegionDreamC_B, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDreamC_B);
		mSpriteDreamC_B.setVisible(false);
		mSpriteDreamC_C = new Sprite(294, 181, ITextureRegionDreamC_C, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDreamC_C);
		mSpriteDreamC_C.setVisible(false);
		mAnimatedSpriteDreamC_D = new AnimatedSprite(54, 10, iTiledTextureRegionDreamC_D, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteDreamC_D);
		mAnimatedSpriteDreamC_D.setVisible(false);
		//Booka boy Red
		mAnimatedSpriteBookaBoyRed = new AnimatedSprite(288, 129, iTiledTextureRegionBookaBoyRed, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBookaBoyRed);
		mAnimatedSpriteBookaBoyRed.setVisible(false);
		//Booka boy green
		mAnimatedSpriteBookaBoyGreen = new AnimatedSprite(45, 131, iTiledTextureRegionBookaBoyGreen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBookaBoyGreen);
		mAnimatedSpriteBookaBoyGreen.setVisible(false);
		//Booka girl green
		mAnimatedSpriteBookaGirlGreen = new AnimatedSprite(157, 41, iTiledTextureRegionBookaGirlGreen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBookaGirlGreen);
		mAnimatedSpriteBookaGirlGreen.setVisible(false);
		//Bookb girl purple
		mAnimatedSpriteBookbGirlPurple = new AnimatedSprite(137, 10, iTiledTextureRegionBookbGirlPurple, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteBookbGirlPurple);
		mAnimatedSpriteBookbGirlPurple.setVisible(false);
		//Clock
		mSpriteMainClock = new Sprite(438, 45, iTextureRegionMainClock, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteMainClock);
		
		mSpriteDoor = new Sprite(550, 301, iTiledTextureRegionRightDoor.getTextureRegion(7), this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteDoor);
		
		mAnimatedSpriteLeftDoor = new AnimatedSprite(534, 222, iTiledTextureRegionLeftDoor, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteLeftDoor); 
		
		mAnimatedSpriteRightDoor = new AnimatedSprite(709, 222, iTiledTextureRegionRightDoor, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteRightDoor);
		
		mSpriteGirlDoor = new Sprite(709, 222, iTiledTextureRegionRightDoor.getTextureRegion(2), this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteGirlDoor);
		mSpriteGirlDoor.setVisible(false);
		
		mSpriteBoyDoor = new Sprite(709, 222, iTiledTextureRegionLeftDoor.getTextureRegion(2), this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteBoyDoor);
		mSpriteBoyDoor.setVisible(false);
		
		mSpriteSubClock2 = new Sprite(658, 181, iTextureRegionSubClock2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteSubClock2);
		
		mSpriteSubClock1 = new Sprite(630, 123, iTextureRegionSubClock1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteSubClock1);
		
		mSpriteSubClock3 = new Sprite(650, 264, iTextureRegionSubClock3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteSubClock3);
		
		mSpriteChangeClock1 = new Sprite(781, 562, iTextureRegionChangeClock1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteChangeClock1);
		
		mAnimatedSpriteChangeClock2 = new AnimatedSprite(782, 527, iTextureRegionChangeClock2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteChangeClock2);
		
		mAnimatedSpriteChangeClock3 =  new AnimatedSprite(866, 527, iTextureRegionChangeClock3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteChangeClock3);
		//Text Hour
		pTextHour = new Text(820, 572, mFont, sHour, 1000, new TextOptions(AutoWrap.WORDS, 1.0f,  Text.LEADING_DEFAULT, HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		pTextHour.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		pTextHour.setColor(0.4f, 0.2f, 0);
        this.mScene.attachChild(pTextHour);
        
        pTextMinutes = new Text(910, 572, mFont, sMinutes, 1000, new TextOptions(AutoWrap.WORDS, 1.0f,  Text.LEADING_DEFAULT, HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        pTextMinutes.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        pTextMinutes.setColor(0.4f, 0.2f, 0);
        this.mScene.attachChild(pTextMinutes);
		//Add gimic button
		addGimmicsButton(this.mScene, Vol3TokeiResource.SOUND_GIMMIC,  Vol3TokeiResource.IMAGE_GIMMIC, Vol3TokeiLibrary4);
		//Retro
		mAnimatedSpriteRetro  = new AnimatedSprite(0, 0, iTiledTextureRegionRetro, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteRetro);
		mAnimatedSpriteRetro.setVisible(false);
		mScene.setOnSceneTouchListener(this);
	}
	//TODO onResumeGame
	@Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        //Set Minutes and Hours for clock
		mCalendar.setToNow();
        hour = mCalendar.hour;
        minute = mCalendar.minute;
        //second = mCalendar.second;
        processGimic(hour,minute);
        //Cal init iTotalMinutes
        iTotalMinutes = 24*60+hour*60 + minute;
        //Cal init iMunites
        iMinutes = minute;
        //Cal init iHour
        iHour = hour + iMinutes / 60.0f;
		//Set Rotate for Minutes
		mSpriteSubClock1.setRotationCenter(mSpriteSubClock1.getWidth()/2, mSpriteSubClock1.getHeight()-10); 
		mSpriteSubClock1.setRotation(iMinutes / 60.0f * 360.0f);
		//Set Rotate for Hours
		mSpriteSubClock2.setRotationCenter(mSpriteSubClock2.getWidth()/2, mSpriteSubClock2.getHeight()-10); 
		mSpriteSubClock2.setRotation(iHour / 12.0f * 360.0f);
		//Set value for Text Hour
		pTextHour.setText(showHour(iHour));
		setAmPm(iHour);
		//Set Value for Text Minutes
		pTextMinutes.setText(showMinutes(iMinutes));
		times = new TimerHandler(0.01f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(isMoveClock){
					if(iTempMinutes!= iTotalMinutes && iTempMinutes!= iTotalMinutes+12*60&& iTempMinutes!= iTotalMinutes-12*60) {
						if (!mAnimatedSpriteRetro.isVisible()) {
							A4_1_HARI.play();
						}
						else {
							A4_1_GYAKU_HARI.play();
						}
					}
					iTempMinutes = iTotalMinutes;
				}
			}
		});
		mSpriteSubClock3.registerUpdateHandler(times);
    }
	//TODO onPauseGame
    @Override
    public void onPauseGame() {
    	super.onPauseGame();
    	isTouchAM7 = false;
		isTouchAM8 = false;
		isTouchAM9 = false;
		isTouchAM10 = false;
		isTouchAM12 = false;
		isTouchPM13 = false;
		isTouchPM15 = false;
		isTouchPM16 = false;
		isTouchPM18 = false;
		isTouchPM19 = false;
		isTouchPM20 = false;
		isTouchPM21 = false;
		isTouchDreamA = false;
		isTouchDreamB = false;
		isTouchDreamC = false;
		touchPM16 = 0;
		touchPM20 = 0;
		rOpenLeftDoor = 3;
		rOpenRightDoor = 3;
	    bOpenLeftDoor = false;
	    bOpenRightDoor = false;
	    isMoveClock = false;
	    isTouchAm = false;
	    isTouchPm = false;
	    backwardTime = true;
	    boyDoor = false;
	    girlDoor = false;
	    if(times!=null){
	    	times = null;
	    }
	    //Girl Door
		setUnVisibleS(mSpriteGirlDoor);
		//Boy Door
		setUnVisibleS(mSpriteBoyDoor);
    	//Background
		setUnVisibleAS(mAnimatedSpriteBackground);
		//Dream ABC
		setUnVisibleAS(mAnimatedSpriteDreamABC);
		//Dream A
		setUnVisibleS(mSpriteDreamA_A); 
		//Dream A
		setUnVisibleAS(mAnimatedSpriteDreamA_B); 
		//Dream B
		setUnVisibleS(mSpriteDreamB_A);
		setUnVisibleAS(mAnimatedSpriteDreamB_B); 
		//Dream C
		setUnVisibleS(mSpriteDreamC_A); 
		setUnVisibleS(mSpriteDreamC_B); 
		setUnVisibleS(mSpriteDreamC_C);
		setUnVisibleAS(mAnimatedSpriteDreamC_D);
		//Booka boy Red
		setUnVisibleAS(mAnimatedSpriteBookaBoyRed);
		//Booka boy green
		setUnVisibleAS(mAnimatedSpriteBookaBoyGreen); 
		//Booka girl green
		setUnVisibleAS(mAnimatedSpriteBookaGirlGreen);
		//Bookb girl purple
		setUnVisibleAS(mAnimatedSpriteBookbGirlPurple);
		//AM7
		setUnVisibleAS(mAnimatedSpriteAM7);
		//AM8
		setUnVisibleAS(mAnimatedSpriteAM8); 
		//AM9
		setUnVisibleAS(mAnimatedSpriteAM9); 
		//AM10_A
		setUnVisibleS(mSpriteAM10_A1);
		setUnVisibleS(mSpriteAM10_A2);
		setUnVisibleS(mSpriteAM10_A3);
		//AM10_B
		setUnVisibleS(mSpriteAM10_B1);
		setUnVisibleS(mSpriteAM10_B2); 
		setUnVisibleS(mSpriteAM10_B3); 
		//AM10_C
		setUnVisibleS(mSpriteAM10_C1);
		setUnVisibleS(mSpriteAM10_C2); 
		setUnVisibleS(mSpriteAM10_C3); 
		//AM10_D
		setUnVisibleAS(mAnimatedSpriteAM10_D);
		//AM12
		setUnVisibleAS(mAnimatedSpriteAM12);
		//PM13
		setUnVisibleAS(mAnimatedSpritePM13);
		//PM15
		setUnVisibleS(mSpritePM15);
		//PM16
		setUnVisibleAS(mAnimatedSpritePM16);
		//Itadakimasu
		setUnVisibleS(mSpriteItadakimasu);
		//PM18
		setUnVisibleAS(mAnimatedSpritePM18);
		//PM19
		setUnVisibleAS(mAnimatedSpritePM19); 
		//PM19_A
		setUnVisibleS(mSpritePM19_A);
		//PM19_B
		setUnVisibleS(mSpritePM19_B); 
		//PM20
		setUnVisibleS(mSpritePM20);
		//PM21
		setUnVisibleAS(mAnimatedSpritePM21);
		//Background AM-PM from 0h to 23h
		setUnVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
		//Clock
		clearRUSprite(mSpriteMainClock);
		//Door
		clearRUSprite(mSpriteDoor); 
		clearRUAnimatedSprite(mAnimatedSpriteLeftDoor);
		clearRUAnimatedSprite(mAnimatedSpriteRightDoor); 
		//Sub clock
		clearRUSprite(mSpriteSubClock2);
		clearRUSprite(mSpriteSubClock1);
		clearRUSprite(mSpriteSubClock3);
		//Changeclock
		clearRUSprite(mSpriteChangeClock1);
		clearRUAnimatedSprite(mAnimatedSpriteChangeClock2); 
		clearRUAnimatedSprite(mAnimatedSpriteChangeClock3); 
		//Text Hour
		clearRUText(pTextHour);
		clearRUText(pTextMinutes); 
    	//Rettro
		setUnVisibleAS(mAnimatedSpriteRetro);
    }
    //TODO load Karaoke Complete
    @Override
    protected void loadKaraokeComplete() {
        super.loadKaraokeComplete();
    }
    //TODO combine Gimic 3
	@Override
	public void combineGimmic3WithAction() {
		if(!bOpenLeftDoor && !bOpenRightDoor){
			touch_Gimic++;
			setTouchGimmic3(false);
			A4_3_HATO.play();
			if(touch_Gimic%2==0){
				if(!bOpenLeftDoor){
					bOpenLeftDoor = true;
					leftDoor();
				}
			}
			else{
				if(!bOpenRightDoor){
					bOpenRightDoor = true;
					rightDoor();
				}
			}
		}
	}
	//TODO All Function Process for Game
	private void boyGirlDoor(){
		if(!mSpriteBoyDoor.isVisible() && !mSpriteGirlDoor.isVisible()){
			randBoyGirlDoor = rand.nextInt(2);
			if(randBoyGirlDoor==0 && !boyDoor && !girlDoor){
				boyDoor= true;
				A4_3_TOBIRA.play();
				A4_3_ASHIATO.play();
				mAnimatedSpriteLeftDoor.setCurrentTileIndex(1);
				mAnimatedSpriteRightDoor.setCurrentTileIndex(1);
				mSpriteBoyDoor.setVisible(true);
				mSpriteBoyDoor.registerEntityModifier(new SequenceEntityModifier(this, new MoveModifier(1.0f, mSpriteBoyDoor.getmXFirst(), 624, mSpriteBoyDoor.getmYFirst(), 252, EaseLinear.getInstance()),
													new MoveModifier(1.0f, 624, 539, 252, mSpriteBoyDoor.getmYFirst(), EaseLinear.getInstance())));
			}
			if(randBoyGirlDoor==1 && !girlDoor && !boyDoor){
				girlDoor= true;
				A4_3_TOBIRA.play();
				A4_3_ASHIATO.play();
				mAnimatedSpriteLeftDoor.setCurrentTileIndex(1);
				mAnimatedSpriteRightDoor.setCurrentTileIndex(1);
				mSpriteGirlDoor.setVisible(true);
				mSpriteGirlDoor.registerEntityModifier(new SequenceEntityModifier(this, new MoveModifier(1.0f, mSpriteGirlDoor.getmXFirst(), 624, mSpriteGirlDoor.getmYFirst(), 252, EaseLinear.getInstance()),
														new MoveModifier(1.0f, 624, 539, 252, mSpriteGirlDoor.getmYFirst(), EaseLinear.getInstance())));
			}
			
		}
	}
	
	private void runAnimatedSpritePM_AM(final int iFrames[], final long lDurations[], final AnimatedSprite anis, final int iRepeat){
		anis.animate(lDurations, iFrames, iRepeat, this);
	}
	private void touchGimicAM_0_4(){
		setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
		if(mAnimatedSpriteBG_AM_PM_0_23.getCurrentTileIndex()!=0 || !mAnimatedSpritePM21.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(2);
			mAnimatedSpriteBackground.setFlippedHorizontal(true);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(0);
			mAnimatedSpritePM21.setVisible(true);
			mAnimatedSpritePM21.setFlippedHorizontal(true);
			if(mAnimatedSpritePM21.isAnimationRunning()){
				mAnimatedSpritePM21.stopAnimation();
			}
			int iFrames[] = {0,1};
			long lDurations[] = {1000,1000};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM21,-1);
		}
	}
	private void touchGimicAM_5_6(){
		setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
		if(mAnimatedSpriteBG_AM_PM_0_23.getCurrentTileIndex()!=1){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(2);
			mAnimatedSpriteBackground.setFlippedHorizontal(true);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(1);
			mAnimatedSpritePM21.setVisible(true);
			mAnimatedSpritePM21.setFlippedHorizontal(true);
			if(mAnimatedSpritePM21.isAnimationRunning()){
				mAnimatedSpritePM21.stopAnimation();
			}
			int iFrames[] = {0,1};
			long lDurations[] = {1000,1000};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM21,-1);
		}
	}
	private void touchGimicAM_7(){
		if(!mAnimatedSpriteAM7.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(2);
			mAnimatedSpriteAM7.setVisible(true);
			int iFrames[] = {0,1,2,0};
			long lDurations[] = {350,350,350,350};
			runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM7, -1);
		}
	}
	private void touchGimicAM_8(){
		if(!mAnimatedSpriteAM8.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(2);
			mAnimatedSpriteAM8.setVisible(true);
		}
	}
	private void touchGimicAM_9(){
		if(!mAnimatedSpriteAM9.isVisible()){
			setUnVisible_S_AS();	
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(2);
			mAnimatedSpriteAM9.setVisible(true);
		}
	}
	private void touchGimicAM_10_11(){
		if(!mAnimatedSpriteAM10_D.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(3);
			mAnimatedSpriteAM10_D.setVisible(true);
		}
	}
	private void touchGimicAM_12(){
		if(!mAnimatedSpriteAM12.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(3);
			mAnimatedSpriteAM12.setVisible(true);
		}
	}
	private void touchGimicPM_13_14(){
		if(!mAnimatedSpritePM13.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(4);
			mAnimatedSpritePM13.setVisible(true);
			int iFrames[] = {0,1,2,3,4,5};
			long lDurations[] = {300,300,300,300,300,300};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM13,-1);
		}
	}
	private void touchGimicPM_15(final float ihour, final float minutes){
		if(!mSpritePM15.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(4);
			mSpritePM15.setVisible(true);
			if(minutes==0.0){
				isTouchPM15 = true;
				A4_21_NEIKI_MUNYA.play();
				mSpritePM15.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchPM15 = false;
					}
				}));
			}
			if(!backwardTime){
				if(minutes==59.0){
					isTouchPM15 = true;
					A4_21_NEIKI_MUNYA.play();
					mSpritePM15.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchPM15 = false;
						}
					}));
				}
			}
		}
	}
	private void touchGimicPM_16_17(){
		if(!mAnimatedSpritePM16.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(0);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(5);
			mAnimatedSpritePM16.setVisible(true);
			int iFrames[] = {0,1};
			long lDurations[] = {750,750};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM16,-1);
		}
	}
	private void touchGimicPM_18(){
		if(!mAnimatedSpritePM18.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(0);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(6);
			mAnimatedSpritePM18.setVisible(true);
		}
	}
	private void touchGimicPM_19(){
		if(!mAnimatedSpritePM19.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(1);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(7);
			mAnimatedSpritePM19.setVisible(true);
			mSpritePM19_A.setVisible(true);
			mSpritePM19_B.setVisible(true);
			moveScaleSprite(mSpritePM19_A,0,mSpritePM19_A.getHeight()-40);
			moveScaleSprite(mSpritePM19_B,0,mSpritePM19_B.getHeight()-40);
		}
	}
	private void touchGimicPM_20(){
		if(!mSpritePM20.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(0);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(8);
			mSpritePM20.setVisible(true);
		}
	}
	private void touchGimicPM_21(final float minutes){
		setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
		if(!mAnimatedSpritePM21.isVisible()){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(2);
			mAnimatedSpriteBackground.setFlippedHorizontal(true);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(9);
			mAnimatedSpritePM21.setVisible(true);
			mAnimatedSpritePM21.setFlippedHorizontal(true);
			int iFrames[] = {0,1};
			long lDurations[] = {1000,1000};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM21,-1);
			if(minutes==0.0){
				isTouchPM21 = true;
				A4_21_NEIKI_MUNYA.play();
				mAnimatedSpritePM21.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchPM21 = false;
					}
				}));
			}
		}
	}
	private void touchGimicPM_22_23(){
		setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
		if(mAnimatedSpriteBG_AM_PM_0_23.getCurrentTileIndex()!=10){
			setUnVisible_S_AS();
			setVisibleAS(mAnimatedSpriteBackground);
			mAnimatedSpriteBackground.setCurrentTileIndex(2);
			mAnimatedSpriteBackground.setFlippedHorizontal(true);
			setVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
			mAnimatedSpriteBG_AM_PM_0_23.setCurrentTileIndex(10);
			mAnimatedSpritePM21.setVisible(true);
			int iFrames[] = {0,1};
			long lDurations[] = {1000,1000};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM21,-1);
			mAnimatedSpritePM21.setFlippedHorizontal(true);
		}
	}
	private void processGimic(final float hours, final float minutes){
		if((int)minutes==0){
			boyGirlDoor();
			bOpenLeftDoor = true;
			bOpenRightDoor = true;
			setTouchGimmic3(false);
		}
		switch((int)hours){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			touchGimicAM_0_4();
			break;
		case 5:
		case 6:
			touchGimicAM_5_6();
			break;
		case 7:
			touchGimicAM_7();
			break;
		case 8:
			touchGimicAM_8();
			break;
		case 9: 
			touchGimicAM_9();
			break;
		case 10:
		case 11:
			touchGimicAM_10_11();
			break;
		case 12:
			touchGimicAM_12();
			break;
		case 13:
		case 14:
			touchGimicPM_13_14();
			break;
		case 15:
			touchGimicPM_15(hours,minutes);
			break;
		case 16:
		case 17:
			touchGimicPM_16_17();
			break;
		case 18:
			touchGimicPM_18();
			break;
		case 19:
			touchGimicPM_19();
			break;
		case 20:
			touchGimicPM_20();
			break;
		case 21:
			touchGimicPM_21(minutes);
			break;
		case 22:
		case 23:
			touchGimicPM_22_23();
			break;
		}
	}
	private String showHour(final float hours){
		if(Math.abs((int)iHour) < 10){
			sHour = "0"+Math.abs((int)iHour);
		}else{
			if(Math.abs((int)iHour)>12){
				if((Math.abs((int)iHour)-12) < 10){
					sHour = "0"+(Math.abs((int)iHour)-12);
				}else{
					sHour = ""+(Math.abs((int)iHour)-12);
				}
			}else{
				sHour = ""+Math.abs((int)iHour);
			}
		}
		return sHour;
	}
	private String showMinutes(final float minutes){
		if((int)minutes<10){
			sMinutes = "0"+(int)iMinutes;
		}else{
			sMinutes = ""+(int)iMinutes;
		}
		return sMinutes;
	}
	private void setAmPm(final float hours){
		if((int)hours<12){
			mAnimatedSpriteChangeClock2.setCurrentTileIndex(0);
			mAnimatedSpriteChangeClock3.setCurrentTileIndex(1);
		}else{
			mAnimatedSpriteChangeClock2.setCurrentTileIndex(1);
			mAnimatedSpriteChangeClock3.setCurrentTileIndex(0);
		}
	}
	private void backwardTime(){
		if(mAnimatedSpriteRetro!=null){
			if(!mAnimatedSpriteRetro.isAnimationRunning()){
				int fFrame[] = {0,1};
				long fDuration[] = {200,200};
				mAnimatedSpriteRetro.animate(fDuration, fFrame, -1);
			}
		}
	}
	private void randomDream15PM(){
		randDream15PM = rand.nextInt(3);
		switch (randDream15PM) {
		case 0:
			mAnimatedSpriteDreamA_B.setVisible(true);
			mAnimatedSpriteDreamA_B.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mSpritePM15.registerEntityModifier(new DelayModifier(5.0f, this));
			break;
		case 1:
			mAnimatedSpriteDreamB_B.setVisible(true);
			mAnimatedSpriteDreamB_B.setFlippedHorizontal(true);
			int iFramesB[] = {0,1,2};
			long lDurationsB[] = {500,500,500};
			runAnimatedSpritePM_AM(iFramesB,lDurationsB,mAnimatedSpriteDreamB_B,-1);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setCurrentTileIndex(1);
			mSpritePM15.registerEntityModifier(new DelayModifier(5.0f, this));
			break;
		case 2:
			mSpriteDreamC_C.setVisible(true);
			mSpriteDreamC_C.setFlippedHorizontal(true);
			mAnimatedSpriteDreamC_D.setVisible(true);
			mAnimatedSpriteDreamC_D.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setCurrentTileIndex(2);
			mSpritePM15.registerEntityModifier(new DelayModifier(5.0f, this));
			break;

		default:
			break;
		}
	}
	private void dream16_17_20A(){
		mAnimatedSpriteDreamABC.setVisible(true);
		mAnimatedSpriteDreamABC.setCurrentTileIndex(3);
		mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
		mAnimatedSpriteDreamABC.setPosition(-92, -137);
		
		mAnimatedSpriteBookaBoyRed.setVisible(true);
		mAnimatedSpriteBookaBoyRed.setFlippedHorizontal(true);
		int iFramesRed[] = {0,1};
		long lDurationsRed[] = {500,500};
		runAnimatedSpritePM_AM(iFramesRed,lDurationsRed,mAnimatedSpriteBookaBoyRed,1);
		
		mAnimatedSpriteBookaBoyGreen.setVisible(true);
		mAnimatedSpriteBookaBoyGreen.setFlippedHorizontal(true);
		int iFramesGreen[] = {0,1,2,3};
		long lDurationsGreen[] = {600,600,600,600};
		runAnimatedSpritePM_AM(iFramesGreen,lDurationsGreen,mAnimatedSpriteBookaBoyGreen,0);
		
		mAnimatedSpriteBookaGirlGreen.setVisible(true);
		mAnimatedSpriteBookaGirlGreen.setFlippedHorizontal(true);
		int iFrames[] = {0,1,2,3,4,5};
		long lDurations[] = {400,400,400,400,400,400};
		runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpriteBookaGirlGreen,0);
	}
	private void finishDream16_17_20A(){
		if(mAnimatedSpriteBookaGirlGreen!=null){
			mAnimatedSpriteBookaGirlGreen.stopAnimation();
		}
		if(mAnimatedSpriteBookaBoyRed!=null){
			mAnimatedSpriteBookaBoyRed.stopAnimation();
		}
		if(mAnimatedSpriteBookaBoyGreen!=null){
			mAnimatedSpriteBookaBoyGreen.stopAnimation();
		}
		mAnimatedSpriteBookaGirlGreen.setVisible(false);
		mAnimatedSpriteBookaBoyRed.setVisible(false);
		mAnimatedSpriteBookaBoyGreen.setVisible(false);
	}
	private void dream16_17_20B(){
		mAnimatedSpriteDreamABC.setVisible(true);
		mAnimatedSpriteDreamABC.setCurrentTileIndex(4);
		mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
		mAnimatedSpriteDreamABC.setPosition(-92, -137);
		mAnimatedSpriteBookbGirlPurple.setVisible(true);
		mAnimatedSpriteBookbGirlPurple.setFlippedHorizontal(true);
		int iFrames[] = {0,1};
		long lDurations[] = {500,500};
		runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpriteBookbGirlPurple,1);
	}
	private void finishDream16_17_20B(){
		if(mAnimatedSpriteBookbGirlPurple!=null){
			mAnimatedSpriteBookbGirlPurple.stopAnimation();
		}
		mAnimatedSpriteBookbGirlPurple.setVisible(false);
	}
	private void defaultDream16_17_20(){
		mAnimatedSpriteDreamABC.setVisible(false);
		mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
		mAnimatedSpriteDreamABC.setCurrentTileIndex(0);
		mAnimatedSpriteDreamABC.setPosition(mAnimatedSpriteDreamABC.getmXFirst(), mAnimatedSpriteDreamABC.getmYFirst());
	}
	private void randomDream21PM(){
		randDream21PM = rand.nextInt(3);
		switch (randDream21PM) {
		case 0:
			mAnimatedSpriteDreamA_B.setVisible(true);
			mAnimatedSpriteDreamA_B.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mAnimatedSpritePM21.registerEntityModifier(new DelayModifier(5.0f, this));
			break;
		case 1:
			mAnimatedSpriteDreamB_B.setVisible(true);
			mAnimatedSpriteDreamB_B.setFlippedHorizontal(true);
			int iFramesB[] = {0,1,2};
			long lDurationsB[] = {500,500,500};
			runAnimatedSpritePM_AM(iFramesB,lDurationsB,mAnimatedSpriteDreamB_B,-1);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setCurrentTileIndex(1);
			mAnimatedSpritePM21.registerEntityModifier(new DelayModifier(5.0f, this));
			break;
		case 2:
			mSpriteDreamC_C.setVisible(true);
			mSpriteDreamC_C.setFlippedHorizontal(true);
			mAnimatedSpriteDreamC_D.setVisible(true);
			mAnimatedSpriteDreamC_D.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setVisible(true);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(true);
			mAnimatedSpriteDreamABC.setCurrentTileIndex(2);
			mAnimatedSpritePM21.registerEntityModifier(new DelayModifier(5.0f, this));
			break;

		default:
			break;
		}
	}
	private void leftDoor(){
		if(rOpenLeftDoor >6){
			rOpenLeftDoor = 3;
		}
		mAnimatedSpriteLeftDoor.setCurrentTileIndex(rOpenLeftDoor);
		if(bOpenLeftDoor){
			mAnimatedSpriteLeftDoor.clearEntityModifiers();
			mAnimatedSpriteLeftDoor.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
							mAnimatedSpriteLeftDoor.setCurrentTileIndex(0);
							mAnimatedSpriteLeftDoor.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								}
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									bOpenLeftDoor = false;
									if(!bOpenRightDoor && !bOpenLeftDoor){
										setTouchGimmic3(true);
									}
								}
							}));
					}
				}
			));
		}
		rOpenLeftDoor++;
	}
	private void rightDoor(){
		if(rOpenRightDoor >6){
			rOpenRightDoor = 3;
		}
		mAnimatedSpriteRightDoor.setCurrentTileIndex(rOpenRightDoor);
		if(bOpenRightDoor){
			mAnimatedSpriteRightDoor.clearEntityModifiers();
			mAnimatedSpriteRightDoor.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> paramIModifier,
							IEntity paramT) {
							mAnimatedSpriteRightDoor.setCurrentTileIndex(0);
							mAnimatedSpriteRightDoor.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								}
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									bOpenRightDoor = false;
									if(!bOpenRightDoor && !bOpenLeftDoor){
										setTouchGimmic3(true);
									}
								}
							}));
					}
				}
			));
		}
		rOpenRightDoor++;
	}
	private void setVisibleAS(final AnimatedSprite anis){
		if(anis!=null && !anis.isVisible()){
			if(anis.isAnimationRunning()){
				anis.stopAnimation();
			}
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
			anis.setVisible(true);
		}
	}
	private void setUnVisibleAS(final AnimatedSprite anis){
		if(anis!=null && anis.isVisible()){
			if(anis.isAnimationRunning()){
				anis.stopAnimation();
			}
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
			anis.setVisible(false);
		}
	}
	private void setUnVisibleS(final Sprite s){
		if(s!=null && s.isVisible()){
			s.clearEntityModifiers();
			s.clearUpdateHandlers();
			s.setPosition(s.getmXFirst(), s.getmYFirst());
			s.setVisible(false);
		}
	}
	private void stopSound(){
		A4_A2_UHU.stop();
		A4_10_REMIFA.stop();
		A4_8_MOGU.stop();
		A4_B2_ARIGATO.stop();
		A4_5_2_HAIHILU.stop();
		A4_13_KOROBU.stop();
		A4_15_KIRA10.stop();
		A4_21_MUNYA.stop();
		A4_5_1_KOBITO.stop();
		A4_16_EHON.stop();
		A4_19A_IIYUDANE.stop();
		A4_18B_TABERU2.stop();
		A4_7_OHAYO.stop();
		A4_19B_ATAMAARAU.stop();
		A4_3_ASHIATO.stop();
		A4_3_HATO.stop();
		A4_8_GOCHISOSAMA.stop();
		A4_8_ITADAKI1.stop();
		A4_9_ITEKIMASU.stop();
		A4_B2_KONCHIWA.stop();
		A4_C2_HENSHIN.stop();
		A4_19A_ATAKAI.stop();
		A4_7_HAMIGAKI.stop();
		A4_21_NEIKI_MUNYA.stop();
		A4_10_DOREMI.stop();
		A4_13_ITAI.stop();
		A4_3_TOBIRA.stop();
	}
	private void setUnVisible_S_AS(){
		isTouchAM7 = false;
		isTouchAM8 = false;
		isTouchAM9 = false;
		isTouchAM10 = false;
		isTouchAM12 = false;
		isTouchPM13 = false;
		isTouchPM15 = false;
		isTouchPM16 = false;
		isTouchPM18 = false;
		isTouchPM19 = false;
		isTouchPM20 = false;
		isTouchPM21 = false;
		isTouchDreamA = false;
		isTouchDreamB = false;
		isTouchDreamC = false;
		touchPM16 = 0;
		touchPM20 = 0;
		rand19PMA = 0;
		rand19PM = 0;
		touch_Gimic = 0;
		//Girl Door
		if(!girlDoor){
			setUnVisibleS(mSpriteGirlDoor);
		}
		//Boy Door
		if(!boyDoor){
			setUnVisibleS(mSpriteBoyDoor);
		}
		//Background
		setUnVisibleAS(mAnimatedSpriteBackground);
		//Dream ABC
		setUnVisibleAS(mAnimatedSpriteDreamABC);
		//Dream A
		setUnVisibleS(mSpriteDreamA_A); 
		//Dream A
		setUnVisibleAS(mAnimatedSpriteDreamA_B); 
		//Dream B
		setUnVisibleS(mSpriteDreamB_A);
		setUnVisibleAS(mAnimatedSpriteDreamB_B); 
		//Dream C
		setUnVisibleS(mSpriteDreamC_A); 
		setUnVisibleS(mSpriteDreamC_B); 
		setUnVisibleS(mSpriteDreamC_C);
		setUnVisibleAS(mAnimatedSpriteDreamC_D);
		//Booka boy Red
		setUnVisibleAS(mAnimatedSpriteBookaBoyRed);
		//Booka boy green
		setUnVisibleAS(mAnimatedSpriteBookaBoyGreen); 
		//Booka girl green
		setUnVisibleAS(mAnimatedSpriteBookaGirlGreen);
		//Bookb girl purple
		setUnVisibleAS(mAnimatedSpriteBookbGirlPurple);
		//AM7
		setUnVisibleAS(mAnimatedSpriteAM7);
		//AM8
		setUnVisibleAS(mAnimatedSpriteAM8); 
		//AM9
		setUnVisibleAS(mAnimatedSpriteAM9); 
		//AM10_A
		setUnVisibleS(mSpriteAM10_A1);
		setUnVisibleS(mSpriteAM10_A2);
		setUnVisibleS(mSpriteAM10_A3);
		//AM10_B
		setUnVisibleS(mSpriteAM10_B1);
		setUnVisibleS(mSpriteAM10_B2); 
		setUnVisibleS(mSpriteAM10_B3); 
		//AM10_C
		setUnVisibleS(mSpriteAM10_C1);
		setUnVisibleS(mSpriteAM10_C2); 
		setUnVisibleS(mSpriteAM10_C3); 
		//AM10_D
		setUnVisibleAS(mAnimatedSpriteAM10_D);
		//AM12
		setUnVisibleAS(mAnimatedSpriteAM12);
		//PM13
		setUnVisibleAS(mAnimatedSpritePM13);
		//PM15
		setUnVisibleS(mSpritePM15);
		//PM16
		setUnVisibleAS(mAnimatedSpritePM16);
		//Itadakimasu
		setUnVisibleS(mSpriteItadakimasu);
		//PM18
		setUnVisibleAS(mAnimatedSpritePM18);
		//PM19
		setUnVisibleAS(mAnimatedSpritePM19); 
		//PM19_A
		setUnVisibleS(mSpritePM19_A);
		//PM19_B
		setUnVisibleS(mSpritePM19_B); 
		//PM20
		setUnVisibleS(mSpritePM20);
		//PM21
		setUnVisibleAS(mAnimatedSpritePM21);
		//Background AM-PM from 0h to 23h
		setUnVisibleAS(mAnimatedSpriteBG_AM_PM_0_23);
	}
	 //return default first value
	private void clearRUAnimatedSprite(final AnimatedSprite anis){
		if(anis!=null){
			if(anis.isAnimationRunning()){
				anis.stopAnimation();
			}
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
		}
	}
	private void clearRUSprite(final Sprite sps){
		if(sps!=null){
			sps.clearEntityModifiers();
			sps.clearUpdateHandlers();
			sps.setPosition(sps.getmXFirst(), sps.getmYFirst());
		}
	}
	private void clearRUText(final Text txts){
		if(txts!=null){
			txts.clearEntityModifiers();
			txts.clearUpdateHandlers();
			txts.setPosition(txts.getmXFirst(), txts.getmYFirst());
		}
	}
	//TODO Register All Listener Modifier
	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, final IEntity arg1) {
		runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				if(arg1.equals(mSpriteBoyDoor)){
					A4_3_TOBIRA.play();
					mAnimatedSpriteLeftDoor.setCurrentTileIndex(0);
					mAnimatedSpriteRightDoor.setCurrentTileIndex(0);
					setUnVisibleS(mSpriteBoyDoor);
					boyDoor = false;
					bOpenLeftDoor = false;
					bOpenRightDoor = false;
					setTouchGimmic3(true);
				}
				if(arg1.equals(mSpriteGirlDoor)){
					A4_3_TOBIRA.play();
					mAnimatedSpriteLeftDoor.setCurrentTileIndex(0);
					mAnimatedSpriteRightDoor.setCurrentTileIndex(0);
					setUnVisibleS(mSpriteGirlDoor);
					girlDoor = false;
					bOpenLeftDoor = false;
					bOpenRightDoor = false;
					setTouchGimmic3(true);
				}
				if(arg1.equals(mAnimatedSpriteChangeClock2)){
					isTouchAm = false;
				}
				if(arg1.equals(mAnimatedSpriteChangeClock3)){
					isTouchPm = false;
				}
				if(arg1.equals(mSpritePM15)){
					A4_A2_UHU.stop();
					A4_B2_KONCHIWA.stop();
					A4_B2_ARIGATO.stop();
					A4_C2_HENSHIN.stop();
					switch (randDream15PM) {
					case 0:
						mSpriteDreamA_A.setVisible(false);
						mAnimatedSpriteDreamA_B.setVisible(false);
						mAnimatedSpriteDreamA_B.setCurrentTileIndex(0);
						break;
					case 1:
						mSpriteDreamB_A.setVisible(false);
						if(mAnimatedSpriteDreamB_B!=null){
							mAnimatedSpriteDreamB_B.stopAnimation();
						}
						mAnimatedSpriteDreamB_B.setVisible(false);
						mAnimatedSpriteDreamB_B.setCurrentTileIndex(0);
						break;
					case 2:
						setVisibleSpritesArr(false, mSpriteDreamC_A,mSpriteDreamC_B,mSpriteDreamC_C);
						mAnimatedSpriteDreamC_D.setVisible(false);
						mAnimatedSpriteDreamC_D.setCurrentTileIndex(0);
						break;
					default:
						break;
					}
					mAnimatedSpriteDreamABC.setVisible(false);
					mAnimatedSpriteDreamABC.setCurrentTileIndex(0);
					mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
					isTouchPM15 = false;
					isTouchPM21 = false;
				}
				if(arg1.equals(mAnimatedSpritePM21)){
					A4_A2_UHU.stop();
					A4_B2_KONCHIWA.stop();
					A4_B2_ARIGATO.stop();
					A4_C2_HENSHIN.stop();
					switch (randDream21PM) {
					case 0:
						mSpriteDreamA_A.setVisible(false);
						mAnimatedSpriteDreamA_B.setVisible(false);
						mAnimatedSpriteDreamA_B.setCurrentTileIndex(0);
						break;
					case 1:
						mSpriteDreamB_A.setVisible(false);
						if(mAnimatedSpriteDreamB_B!=null){
							mAnimatedSpriteDreamB_B.stopAnimation();
						}
						mAnimatedSpriteDreamB_B.setVisible(false);
						mAnimatedSpriteDreamB_B.setCurrentTileIndex(0);
						break;
					case 2:
						setVisibleSpritesArr(false, mSpriteDreamC_A,mSpriteDreamC_B,mSpriteDreamC_C);
						mAnimatedSpriteDreamC_D.setVisible(false);
						mAnimatedSpriteDreamC_D.setCurrentTileIndex(0);
						break;
					default:
						break;
					}
					mAnimatedSpriteDreamABC.setVisible(false);
					mAnimatedSpriteDreamABC.setCurrentTileIndex(0);
					mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
					isTouchPM15 = false;
					isTouchPM21 = false;
				}
				
				if(arg1.equals(mAnimatedSpriteDreamA_B)){
					A4_A2_UHU.stop();
					A4_B2_KONCHIWA.stop();
					A4_B2_ARIGATO.stop();
					A4_C2_HENSHIN.stop();
					mSpriteDreamA_A.setVisible(false);
					mAnimatedSpriteDreamA_B.setVisible(false);
					mAnimatedSpriteDreamA_B.setCurrentTileIndex(0);
					mAnimatedSpriteDreamABC.setVisible(false);
					mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
					isTouchPM15 = false;
					isTouchPM21 = false;
				}
				if(arg1.equals(mAnimatedSpriteDreamC_D)){
					A4_A2_UHU.stop();
					A4_B2_KONCHIWA.stop();
					A4_B2_ARIGATO.stop();
					A4_C2_HENSHIN.stop();
					setVisibleSpritesArr(false, mSpriteDreamC_A,mSpriteDreamC_B,mSpriteDreamC_C);
					mAnimatedSpriteDreamC_D.setVisible(false);
					mAnimatedSpriteDreamC_D.setCurrentTileIndex(0);
					mAnimatedSpriteDreamABC.setVisible(false);
					mAnimatedSpriteDreamABC.setCurrentTileIndex(0);
					mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
					isTouchPM15 = false;
					isTouchPM21 = false;
				}
				
				if(arg1.equals(mAnimatedSpritePM16)){
					if(touchPM16==1){
						finishDream16_17_20A();
					}
					if(touchPM16==0){
						finishDream16_17_20B();
					}
					defaultDream16_17_20();
					isTouchPM16 = false;
				}
				if(arg1.equals(mSpritePM20)){
					if(touchPM20==1){
						finishDream16_17_20A();
					}
					if(touchPM20==0){
						finishDream16_17_20B();
					}
					defaultDream16_17_20();
					isTouchPM20 = false;
				}
				
				if(arg1.equals(mAnimatedSpritePM19)){
					isTouchPM19 = false;
					mAnimatedSpritePM19.setCurrentTileIndex(0);
				}
			}
		});
	}
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}
	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		if(arg0.equals(mAnimatedSpriteAM7)){
			int iFrames[] = {0,1,2,0};
			long lDurations[] = {350,350,350,350};
			runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM7, -1);
			isTouchAM7 = false;
		}
		if(arg0.equals(mAnimatedSpriteAM8)){
			isTouchAM8 = false;
		}
		if(arg0.equals(mAnimatedSpriteAM9)){
			isTouchAM9 = false;
		}
		if(arg0.equals(mAnimatedSpriteAM10_D)){
			setVisibleSpritesArr(false,mSpriteAM10_A1,mSpriteAM10_A2,mSpriteAM10_A3,
					mSpriteAM10_B1,mSpriteAM10_B2,mSpriteAM10_B3,
			mSpriteAM10_C1,mSpriteAM10_C2,mSpriteAM10_C3);
			isTouchAM10 = false;
		}
		if(arg0.equals(mAnimatedSpriteAM12)){
			isTouchAM12 = false;
		}
		if(arg0.equals(mAnimatedSpritePM13)){
			mAnimatedSpritePM13.stopAnimation();
			int iFrames[] = {0,1,2,3,4,5};
			long lDurations[] = {300,300,300,300,300,300};
			runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM13,-1);
			isTouchPM13 = false;
		}
		if(arg0.equals(mAnimatedSpriteDreamB_B)){
			A4_A2_UHU.stop();
			A4_B2_KONCHIWA.stop();
			A4_B2_ARIGATO.stop();
			A4_C2_HENSHIN.stop();
			mSpriteDreamB_A.setVisible(false);
			if(mAnimatedSpriteDreamB_B!=null){
				mAnimatedSpriteDreamB_B.stopAnimation();
			}
			mAnimatedSpriteDreamB_B.setVisible(false);
			mAnimatedSpriteDreamABC.setVisible(false);
			mAnimatedSpriteDreamABC.setCurrentTileIndex(0);
			mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
		}
		if(arg0.equals(mAnimatedSpritePM18)){
			isTouchPM18 = false;
		}
		if(arg0.equals(mAnimatedSpritePM19)){
			isTouchPM19 = false;
		}
	}
	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
		if(arg0.equals(mAnimatedSpriteAM7)){
			if(arg2==4 && isTouchAM7){
				A4_7_HAMIGAKI.play();
			}
		}
		if(arg0.equals(mAnimatedSpriteAM8)){
			if(arg2==1 && isTouchAM8){
				mSpriteItadakimasu.setVisible(false);
			}
			if(arg2==2 && isTouchAM8){
				A4_8_MOGU.play();
			}
			if(arg2==6 && isTouchAM8){
				A4_8_GOCHISOSAMA.play();
			}
		}
		if(arg0.equals(mAnimatedSpriteAM12)){
			if(arg2==1 && isTouchAM12){
				mSpriteItadakimasu.setVisible(false);
			}
			if(arg2==2 && isTouchAM12){
				A4_8_MOGU.play();
			}
			if(arg2==6 && isTouchAM12){
				A4_8_GOCHISOSAMA.play();
				
			}
		}
		if(arg0.equals(mAnimatedSpritePM13)){
			if(arg2==5 && isTouchPM13){
				A4_13_ITAI.play();
			}
		}
		if(arg0.equals(mAnimatedSpritePM18)){
			if(rand18PM==0 && isTouchPM18){
				if(arg2==1){
					mSpriteItadakimasu.setVisible(false);
				}
				if(arg2==2){
					A4_8_MOGU.play();
				}
				if(arg2==6){
					A4_8_GOCHISOSAMA.play();
				}
			}
			if(rand18PM==1 && isTouchPM18){
				if(arg2==1){
					mSpriteItadakimasu.setVisible(false);
				}
				if(arg2==2){
					A4_18B_TABERU2.play();
				}
				if(arg2==6){
					A4_8_GOCHISOSAMA.play();
				}
			}
		}
		if(arg0.equals(mAnimatedSpritePM19)){
			if(rand19PM ==0 && isTouchPM19){
				if(arg2==3){
					A4_19B_ATAMAARAU.play();
				}
			}
		}
	}
	private void moveAlphaSprite(final Sprite sp, final float x, final float y){
		if(sp!=null){
			sp.setPosition(sp.getmXFirst(), sp.getmYFirst());
			sp.clearEntityModifiers();
			sp.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(1.0f, sp.getmXFirst(), sp.getmXFirst() - x,
							sp.getmYFirst(), sp.getmYFirst() - y, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								}
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									sp.setVisible(false);
								}
							}),
						new AlphaModifier(0.5f, 0.5f, 1.0f)
			));
		}
	}
	private void moveScaleSprite(final Sprite sp, final float x, final float y){
		if(sp!=null){
			sp.setPosition(sp.getmXFirst(), sp.getmYFirst());
			sp.clearEntityModifiers();
			final LoopEntityModifier test = new LoopEntityModifier(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				}
			}, -1, new ILoopEntityModifierListener() {
				@Override
				public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
					sp.setVisible(true);
				}
				@Override
				public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
					sp.setVisible(false);
					sp.setAlpha(1.0f);
				}
			}, new SequenceEntityModifier(
					new MoveModifier(3.5f, sp.getmXFirst(), sp.getmXFirst() - x,
							sp.getmYFirst(), sp.getmYFirst() - y, EaseBackIn.getInstance()),
						new AlphaModifier(1.0f, 0.5f, 0.0f)
			));
			sp.registerEntityModifier(test);
		}
	}
	private void setVisibleSpritesArr(final boolean bol, Sprite ...sprites){
		for(int i = 0; i<sprites.length;i++){
			if(sprites[i]!=null){
				sprites[i].setVisible(bol);
			}
		}
	}
	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
	}
	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
	}
	//TODO Touch All Action
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		int pX =  (int) arg1.getX();
		int pY =  (int) arg1.getY();
		if(arg1.isActionDown()){
			//Process left door in Clock
			if(mSpriteSubClock1.contains(pX, pY)){
				isMoveClock = true;
			}
			if(mAnimatedSpriteLeftDoor.contains(pX, pY)){
				if(!bOpenLeftDoor){
					A4_3_HATO.play();
					bOpenLeftDoor = true;
					setTouchGimmic3(false);
					leftDoor();
					return true;
				}
			}
			//Process right door in Clock
			if(mAnimatedSpriteRightDoor.contains(pX, pY)){
				if(!bOpenRightDoor){
					A4_3_HATO.play();
					bOpenRightDoor = true;
					setTouchGimmic3(false);
					rightDoor();
					return true;
				}
			}
			if(mAnimatedSpriteChangeClock2.contains(pX, pY)){
				if(!isTouchAm && !isTouchPm && mAnimatedSpriteChangeClock2.getCurrentTileIndex()==1){
					stopSound();
					A4_2_GOZEN.play();
					isTouchAm = true;
					iTotalMinutes += 12*60;	
					if (iTotalMinutes<=0.0) {
						iTotalMinutes = iTotalMinutes+24*60;
					}
	        		//Vuot qua muoi vong quay
					if (iTotalMinutes>=24*10*60) {
						iTotalMinutes = iTotalMinutes-24*60;
					}
					iHour = (iTotalMinutes/60)%24;
					processGimic(iHour, iMinutes);
					pTextHour.setText(showHour(iHour));
					setAmPm(iHour);
					mAnimatedSpriteChangeClock2.registerEntityModifier(new DelayModifier(1.0f, this));
				}
			}
			if(mAnimatedSpriteChangeClock3.contains(pX, pY)){
				if(!isTouchAm && !isTouchPm && mAnimatedSpriteChangeClock3.getCurrentTileIndex()==1){
					stopSound();
					A4_2_GOGO.play();
					isTouchPm = true;
					iTotalMinutes -= 12*60;
					if (iTotalMinutes<=0.0) {
						iTotalMinutes = iTotalMinutes+24*60;
					}
	        		//Vuot qua muoi vong quay
					if (iTotalMinutes>=24*10*60) {
						iTotalMinutes = iTotalMinutes-24*60;
					}
					iHour = (iTotalMinutes/60)%24;
					processGimic(iHour, iMinutes);
					pTextHour.setText(showHour(iHour));
					setAmPm(iHour);
					mAnimatedSpriteChangeClock3.registerEntityModifier(new DelayModifier(1.0f, this));
				}
			}
			if(backwardTime){
				//AM7
				if(mAnimatedSpriteAM7.contains(pX, pY)){
					if((int)iHour == 7 && !isTouchAM7){
						isTouchAM7 = true;
						A4_7_OHAYO.play(); 
						mAnimatedSpriteAM7.stopAnimation();
						int iFrames[] = {0,1,2,0,3,4,3,4,0};
						long lDurations[] = {350,350,350,350,350,350,350,350,1000};
						runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM7, 0);
					}
				}
				//AM8
				if(mAnimatedSpriteAM8.contains(pX, pY)){
					if((int)iHour == 8 && !isTouchAM8){
						isTouchAM8 = true;
						A4_8_ITADAKI1.play();
						mSpriteItadakimasu.setVisible(true);
						int iFrames[] = {0,1,2,3,4,5,6,0};
						long lDurations[] = {1500,400,400,400,400,400,1500,100};
						runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM8, 0);
					}
				}
				//AM9
				if(mAnimatedSpriteAM9.contains(pX, pY)){
					if((int)iHour == 9 && !isTouchAM9){
						isTouchAM9 = true;
						A4_9_ITEKIMASU.play();
						int iFrames[] = {1,2,3,4,5,0,0,0};
						long lDurations[] = {200,200,200,200,200,200,500,500};
						runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM9, 0);
					}
				}
				//AM10_11
				if(mAnimatedSpriteAM10_D.contains(pX, pY)){
					if(((int)iHour==10 || (int)iHour==11) && !isTouchAM10){
						
						if(!isTouchAM10 && DRM_RMF==0){
							isTouchAM10 = true;
							A4_10_REMIFA.play();
							DRM_RMF = 1;
						}
						if(!isTouchAM10 && DRM_RMF==1){
							isTouchAM10 = true;
							A4_10_DOREMI.play();
							DRM_RMF = 0;
						}
						int iFrames[] = {1,2,1,2,0};
						long lDurations[] = {250,250,250,1000,250};
						randNote = rand.nextInt(3);
						switch(randNote){
							case 0:
								setVisibleSpritesArr(true,mSpriteAM10_A1,mSpriteAM10_B1,mSpriteAM10_A3);
								moveAlphaSprite(mSpriteAM10_A1,65,160);
								moveAlphaSprite(mSpriteAM10_B1,0,160);
								moveAlphaSprite(mSpriteAM10_A3,-65,160);
								mSpriteAM10_A1.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										setVisibleSpritesArr(true,mSpriteAM10_C1,mSpriteAM10_C2,mSpriteAM10_B2);
										moveAlphaSprite(mSpriteAM10_C1,65,120);
										moveAlphaSprite(mSpriteAM10_C2,0,120);
										moveAlphaSprite(mSpriteAM10_B2,-65,120);
									}
								}));
								break;
							case 1: 
								setVisibleSpritesArr(true,mSpriteAM10_B1,mSpriteAM10_B2,mSpriteAM10_C1);
								moveAlphaSprite(mSpriteAM10_B1,65,160);
								moveAlphaSprite(mSpriteAM10_B2,20,160);
								moveAlphaSprite(mSpriteAM10_C1,-65,160);
								mSpriteAM10_B1.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										setVisibleSpritesArr(true,mSpriteAM10_A1,mSpriteAM10_A2,mSpriteAM10_C2);
										moveAlphaSprite(mSpriteAM10_A1,65,120);
										moveAlphaSprite(mSpriteAM10_A2,0,120);
										moveAlphaSprite(mSpriteAM10_C2,-65,120);
									}
								}));
								break;
							case 2: 
								setVisibleSpritesArr(true,mSpriteAM10_C1,mSpriteAM10_C2,mSpriteAM10_B1);
								moveAlphaSprite(mSpriteAM10_C1,65,160);
								moveAlphaSprite(mSpriteAM10_C2,0,160);
								moveAlphaSprite(mSpriteAM10_B1,-65,160);
								mSpriteAM10_B1.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										setVisibleSpritesArr(true,mSpriteAM10_A1,mSpriteAM10_A3,mSpriteAM10_B3);
										moveAlphaSprite(mSpriteAM10_A1,65,120);
										moveAlphaSprite(mSpriteAM10_A3,0,120);
										moveAlphaSprite(mSpriteAM10_B3,-65,120);
									}
								}));
								break;
						}
						runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM10_D, 0);
					}
				}
				//AM12
				if(mAnimatedSpriteAM12.contains(pX, pY)){
					if(((int)iHour==12) && !isTouchAM12){
						isTouchAM12 = true;
						A4_8_ITADAKI1.play();
						mSpriteItadakimasu.setVisible(true);
						int iFrames[] = {0,1,2,3,4,5,6,0};
						long lDurations[] = {1500,400,400,400,400,400,1500,100};
						runAnimatedSpritePM_AM(iFrames, lDurations, mAnimatedSpriteAM12, 0);
					}
				}
				//PM13_14
				if(mAnimatedSpritePM13.contains(pX, pY)){
					if(((int)iHour==13 || (int)iHour==14) && !isTouchPM13){
						isTouchPM13 = true;
						A4_13_KOROBU.play();
						mAnimatedSpritePM13.stopAnimation();
						int iFrames[] = {6,7,8,9,10,11};
						long lDurations[] = {350,350,350,350,350,1750};
						runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM13,0);
					}
				}
				//PM15
				if(mSpritePM15.contains(pX, pY)){
					if((int)iHour==15 && !isTouchPM15){
						//Stop Sound
						A4_A2_UHU.stop();
						A4_B2_KONCHIWA.stop();
						A4_B2_ARIGATO.stop();
						A4_C2_HENSHIN.stop();
						//A Unvisible
						setUnVisibleS(mSpriteDreamA_A);
						setUnVisibleAS(mAnimatedSpriteDreamA_B);
						isTouchDreamA = false;
						isTouchDreamB = false;
						isTouchDreamC = false;
						isTouchPM15 = true;
						//B Unvisible
						setUnVisibleS(mSpriteDreamB_A);
						setUnVisibleAS(mAnimatedSpriteDreamB_B);
						//C Unvisible
						setUnVisibleS(mSpriteDreamC_A);
						setUnVisibleS(mSpriteDreamC_B);
						setUnVisibleS(mSpriteDreamC_C);
						setUnVisibleAS(mAnimatedSpriteDreamC_D);
						setUnVisibleAS(mAnimatedSpriteDreamABC);
						mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
						mSpritePM15.clearEntityModifiers();
						mSpritePM15.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if(!isTouchDreamA && !isTouchDreamB && !isTouchDreamC){
									isTouchPM15 = false;
								}
							}
						}));
						A4_15_KIRA10.play();
						randomDream15PM();
					}
				}
				//PM16_17
				if(mAnimatedSpritePM16.contains(pX, pY)){
					if(((int)iHour==16 || (int)iHour==17) && !isTouchPM16){
						A4_16_EHON.play();
						if(touchPM16==0 && !isTouchPM16){
							isTouchPM16 = true;
							touchPM16 = 1;
							A4_A2_UHU.play();
							A4_5_1_KOBITO.play();
							dream16_17_20A();
							mAnimatedSpritePM16.registerEntityModifier(new DelayModifier(2.5f, this));
						}
						if(touchPM16==1 && !isTouchPM16){
							isTouchPM16 = true;
							touchPM16 = 0;
							A4_5_2_HAIHILU.play();
							dream16_17_20B();
							mAnimatedSpritePM16.registerEntityModifier(new DelayModifier(2.0f, this));
						}
					}
				}
				//PM18
				if(mAnimatedSpritePM18.contains(pX, pY)){
					if((int)iHour==18 && !isTouchPM18){
						isTouchPM18 = true;
						A4_8_ITADAKI1.play();
						mSpriteItadakimasu.setVisible(true);
						rand18PM = rand.nextInt(2);
						if(rand18PM==0){
							int iFrames[] = {0,1,2,3,4,5,11,0};
							long lDurations[] = {1500,400,400,400,400,400,1500,100};
							runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM18,0);
						}else{
							int iFrames[] = {0,6,7,8,9,10,11,0};
							long lDurations[] = {1500,400,400,400,400,400,1500,100};
							runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM18,0);
						}
					}
				}
				//PM19
				if(mAnimatedSpritePM19.contains(pX, pY)){
					if((int)iHour==19 && !isTouchPM19){
						if(rand19PM ==0 && !isTouchPM19){
							rand19PMA++;
							rand19PM = 1;
							isTouchPM19 = true;
							if(rand19PMA%2==0){
								A4_19A_IIYUDANE.play();
							}else{
								A4_19A_ATAKAI.play();
							}
							mAnimatedSpritePM19.setCurrentTileIndex(1);
							mAnimatedSpritePM19.registerEntityModifier(new DelayModifier(1.9f, this));
						}
						if(rand19PM ==1 && !isTouchPM19){
							isTouchPM19 = true;
							rand19PM = 0;
							A4_19B_ATAMAARAU.play();
							int iFrames[] = {2,3,2,3,0};
							long lDurations[] = {300,300,300,900,100};
							runAnimatedSpritePM_AM(iFrames,lDurations,mAnimatedSpritePM19,0);
						}
					}
				}
				//PM20
				if(mSpritePM20.contains(pX, pY)){
					if(((int)iHour==20) && !isTouchPM20){
						A4_16_EHON.play();
						if(touchPM20==0 && !isTouchPM20){
							isTouchPM20 = true;
							touchPM20 = 1;
							A4_A2_UHU.play();
							A4_5_1_KOBITO.play();
							dream16_17_20A();
							mSpritePM20.registerEntityModifier(new DelayModifier(2.5f, this));
						}
						if(touchPM20==1 && !isTouchPM20){
							isTouchPM20 = true;
							touchPM20 = 0;
							A4_5_2_HAIHILU.play();
							dream16_17_20B();
							mSpritePM20.registerEntityModifier(new DelayModifier(2.9f, this));
						}
					}
				}
				//PM21 -> 6:59AM
				if(mAnimatedSpritePM21.contains(pX, pY)){
					if(((int)iHour==21 || (int)iHour==22 || (int)iHour==23 || (int)iHour==0 || (int)iHour==1 || (int)iHour==2 || (int)iHour==3 || (int)iHour==4 || (int)iHour==5 || (int)iHour==6) 
						&& !isTouchPM21){
						//Stop Sound
						A4_A2_UHU.stop();
						A4_B2_KONCHIWA.stop();
						A4_B2_ARIGATO.stop();
						A4_C2_HENSHIN.stop();
						//A Unvisible
						setUnVisibleS(mSpriteDreamA_A);
						setUnVisibleAS(mAnimatedSpriteDreamA_B);
						isTouchDreamA = false;
						isTouchDreamB = false;
						isTouchDreamC = false;
						isTouchPM21 = true;
						//B Unvisible
						setUnVisibleS(mSpriteDreamB_A);
						setUnVisibleAS(mAnimatedSpriteDreamB_B);
						//C Unvisible
						setUnVisibleS(mSpriteDreamC_A);
						setUnVisibleS(mSpriteDreamC_B);
						setUnVisibleS(mSpriteDreamC_C);
						setUnVisibleAS(mAnimatedSpriteDreamC_D);
						setUnVisibleAS(mAnimatedSpriteDreamABC);
						mAnimatedSpriteDreamABC.setFlippedHorizontal(false);
						mAnimatedSpritePM21.clearEntityModifiers();
						mAnimatedSpritePM21.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if(!isTouchDreamA && !isTouchDreamB && !isTouchDreamC){
									isTouchPM21 = false;
								}
							}
						}));
						A4_15_KIRA10.play();
						randomDream21PM();
					}
				}
				//Dream Touch
				//Dream A
				if(mAnimatedSpriteDreamA_B.contains(pX, pY)){
					if(mAnimatedSpriteDreamA_B.isVisible()){
						if(!isTouchDreamA){
							isTouchDreamA = true;
							isTouchPM15 = true;
							isTouchPM21 = true;
							A4_A2_UHU.play();
							mSpriteDreamA_A.setVisible(true);
							mAnimatedSpriteDreamA_B.setCurrentTileIndex(1);
							mAnimatedSpriteDreamA_B.setFlippedHorizontal(false);
							mAnimatedSpriteDreamA_B.registerEntityModifier(new DelayModifier(2.0f, this));
						}
					}
				}
				if(mAnimatedSpriteDreamB_B.contains(pX, pY)){
					if(mAnimatedSpriteDreamB_B.isVisible()){
						if(!isTouchDreamB){
							mAnimatedSpriteDreamB_B.stopAnimation();
							mSpriteDreamB_A.setVisible(true);
							mSpriteDreamB_A.setFlippedHorizontal(true);
							if(DreamB==0 && !isTouchDreamB){
								isTouchPM15 = true;
								isTouchPM21 = true;
								A4_B2_KONCHIWA.play();
								DreamB = 1;
								isTouchDreamB = true;
								int iFramesB[] = {3,4};
								long lDurationsB[] = {500,500};
								runAnimatedSpritePM_AM(iFramesB,lDurationsB,mAnimatedSpriteDreamB_B,4);
							}
							if(DreamB==1 && !isTouchDreamB){
								isTouchPM15 = true;
								isTouchPM21 = true;
								A4_B2_ARIGATO.play();
								DreamB = 0;
								isTouchDreamB = true;
								int iFramesB[] = {3,4};
								long lDurationsB[] = {500,500};
								runAnimatedSpritePM_AM(iFramesB,lDurationsB,mAnimatedSpriteDreamB_B,3);
							}
						}
					}
				}
				if(mAnimatedSpriteDreamC_D.contains(pX, pY)){
					if(mAnimatedSpriteDreamC_D.isVisible()){
						if(!isTouchDreamC){
							isTouchDreamC = true;
							isTouchPM15 = true;
							isTouchPM21 = true;
							A4_C2_HENSHIN.play();
							mSpriteDreamC_A.setVisible(true);
							mSpriteDreamC_A.setFlippedHorizontal(true);
							mSpriteDreamC_B.setVisible(true);
							mSpriteDreamC_B.setFlippedHorizontal(true);
							mAnimatedSpriteDreamC_D.setCurrentTileIndex(1);
							mAnimatedSpriteDreamC_D.registerEntityModifier(new DelayModifier(2.5f, this));
						}
					}
				}
			}
		}
		
		if(arg1.isActionUp()){
			isMoveClock = false;
		}
		final float pXX = arg1.getX();
        final float pYY = arg1.getY();
        if(arg1.isActionMove()){
        	if(isMoveClock){
        		//Check ItotalMinutes passing 24h and lesstrick 0h
        		//Quay lui nhieu qua den 0 thi set 1 ngay
        		if (iTotalMinutes<=0.0) {
					iTotalMinutes = iTotalMinutes+24*60;
				}
        		//Vuot qua muoi vong quay
				if (iTotalMinutes>=24*10*60) {
					iTotalMinutes = iTotalMinutes-24*60;
				}
				//Cal direction
				final float fDirectionX = pXX - fMinute_X;
				final float fDirectionY = pYY - fMinute_Y;
				//Binh phuong canh huyen
				final double dCanhhuyen = FloatMath.sqrt((fDirectionX*fDirectionX) + (fDirectionY*fDirectionY));
				float fAngle;	
				//Tinh goc quay
				fAngle = (float)Math.acos(-fDirectionY/dCanhhuyen);		
				if (fDirectionX < 0) {
					fAngle = (float)(Math.PI*2 - fAngle);
				}
				//Set Rotate For Minutes
				mSpriteSubClock1.setRotationCenter(mSpriteSubClock1.getWidth()/2, mSpriteSubClock1.getHeight()-10); 
				mSpriteSubClock1.setRotation(MathUtils.radToDeg(fAngle));
				
				//Assign And Check
				float iMinutes_Temp;
				iMinutes_Temp = iMinutes;
				//Cal iMinutes and append Text Minutes and Check Condition
				iMinutes = (int)(60.*fAngle/Math.PI/2);
				//Neu quay cung chieu kim dong ho
				if (((iMinutes_Temp < iMinutes) && ((iMinutes - iMinutes_Temp) < 30))||((iMinutes_Temp > iMinutes)&& ((iMinutes_Temp - iMinutes) >30))) 
				{				
					mAnimatedSpriteRetro.setVisible(false);
					backwardTime = true;
					if(mAnimatedSpriteRetro!=null){
						if(mAnimatedSpriteRetro.isAnimationRunning()){
							mAnimatedSpriteRetro.stopAnimation();
						}
					}
					if (Math.abs(iTotalMinutes%60 - iMinutes)>30) {
						iTotalMinutes = iTotalMinutes + (60 - iTotalMinutes%60) + iMinutes; 
					}
					else {
						iTotalMinutes = iTotalMinutes +(iMinutes - iTotalMinutes%60);
					}				
				}
				//Neu quay nguoc chieu kim dong ho
				if (((iMinutes_Temp > iMinutes) && ((iMinutes_Temp - iMinutes)<30))||((iMinutes_Temp < iMinutes) && ((iMinutes - iMinutes_Temp)>30))) 
				{
					//Set Visible true for Retro
					mAnimatedSpriteRetro.setVisible(true);
					//Start backward Time
					backwardTime = false;
					backwardTime();
					if (Math.abs(iTotalMinutes%60 - iMinutes)<30) {
						iTotalMinutes = iTotalMinutes - Math.abs((iTotalMinutes%60) - iMinutes);
					}
					else {
						iTotalMinutes = iTotalMinutes - iTotalMinutes%60 - (60 - iMinutes);
					}	
				}
				//Set Rotate For Hours
				final float hour = iTotalMinutes / 60.0f * 360.0f / 12f;
				mSpriteSubClock2.setRotationCenter(mSpriteSubClock2.getWidth()/2, mSpriteSubClock2.getHeight()-10); 
				mSpriteSubClock2.setRotation(hour);
				//Append vao Text Minutes
				pTextMinutes.setText(showMinutes(iMinutes));
				//Append Text Hours
				iHour = (iTotalMinutes/60)%24;
				pTextHour.setText(showHour(iHour));
				setAmPm(iHour);
				processGimic(iHour,iMinutes);
				
        	}
        } 
		return false;
	}

}
