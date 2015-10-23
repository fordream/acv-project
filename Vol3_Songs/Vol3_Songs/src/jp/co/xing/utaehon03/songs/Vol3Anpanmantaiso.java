package jp.co.xing.utaehon03.songs;

import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AnpanmantaisoResource;

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
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

public class Vol3Anpanmantaiso extends BaseGameActivity {
	// Define tag log for this class
	private String TAG = Vol3Anpanmantaiso.this.getClass().toString();
	private TexturePackLoaderFromSource mTexturePackLoaderFromSourceAnpanmantaiso;
	private TexturePack Vol3AnpanmantaisoPacker_1, Vol3AnpanmantaisoPacker_2;
	private TexturePackTextureRegionLibrary Vol3AnpanmantaisoLibrary1,
			Vol3AnpanmantaisoLibrary2;
	private ITextureRegion iTextureRegionHaike1, iTextureRegionHaike2,
			iTextureRegionHaike3, iTextureRegionCar, iTextureRegionYajirushi;
	private Sprite mSpriteHaike1, mSpriteHaike2, mSpriteHaike3, mSpriteCar,
			mSpriteYajirushi;
	private TiledTextureRegion iTiledTextureRegionSwich,
			iTiledTextureRegionObun1, iTiledTextureRegionObun2,
			iTiledTextureRegionPanda, iTiledTextureRegionLion,
			iTiledTextureRegionUsagi;
	private AnimatedSprite mAnimatedSpriteSwich, mAnimatedSpriteObun1,
			mAnimatedSpriteObun2, mAnimatedSpritePanda, mAnimatedSpriteLion,
			mAnimatedSpriteUsagi;

	private TiledTextureRegion iTiledTextureRegionBoyHand,
			iTiledTextureRegionBoyFace, iTiledTextureRegionBoyHandMain,
			iTiledTextureRegionBoyBody;
	private AnimatedSprite mAnimatedSpriteBoyHand, mAnimatedSpriteBoyFace,
			mAnimatedSpriteBoyHandMain, mAnimatedSpriteBoyBody;

	private TiledTextureRegion iTiledTextureRegionKona;
	private AnimatedSprite mAnimatedSpriteKona;

	private TiledTextureRegion iTiledTextureRegionGirlFace,
			iTiledTextureRegionGirlHand, iTiledTextureRegionGirlHandMain,
			iTiledTextureRegionGirlBody1, iTiledTextureRegionGirlBody2;
	private AnimatedSprite mAnimatedSpriteGirlFace, mAnimatedSpriteGirlHand,
			mAnimatedSpriteGirlHandMain, mAnimatedSpriteGirlBody1,
			mAnimatedSpriteGirlBody2;

	private TiledTextureRegion iTiledTextureRegionCook;

	private TiledTextureRegion iTiledTextureRegionKiji;
	private AnimatedSprite mAnimatedSpriteKiji;

	private TiledTextureRegion iTiledTextureRegionRack;
	private Sprite mSpriteRack[] = new Sprite[7];

	private ITextureRegion iTextureRegionRackBackground,
			iTextureRegionRackItem;
	private Sprite mSpriteRackBackground[] = new Sprite[3],
			mSpriteRackItem[] = new Sprite[6];

	private ITextureRegion iTextureRegionConbea, iTextureRegionTable;
	private Sprite mSpriteConbea, mSpriteTable;

	private TiledTextureRegion iTiledTextureRegionAcodion;
	private AnimatedSprite mAnimatedSpriteAcodion;

	private ITextureRegion iTextureRegionToray[] = new ITextureRegion[3];
	private SpriteToray mSpriteToray[] = new SpriteToray[3];

	private ITextureRegion iTextureRegionCarService1,
			iTextureRegionCarService2, iTextureRegionCarService3;
	private Sprite mSpriteCarService1, mSpriteCarService2, mSpriteCarService3;

	private ITextureRegion iTextureRegionSmoke;
	private Sprite mSpriteSmoke;
	private AnimatedSpriteCake spriteCake = null;
	private LinkedList<AnimatedSprite> listAnimatedSprite = new LinkedList<AnimatedSprite>();
	private LinkedList<AnimatedSprite> mapLinkTemp = new LinkedList<AnimatedSprite>();

	private TimerHandler timeCakes;
	private TimerHandler timeSmoke;
	private TimerHandler timeYajirushi;
	private BitmapTextureAtlas mLayoutTexture = null;
	private ITextureRegion mLayoutTextureRegion = null;
	private TiledTextureRegion mLayoutTiledTextureRegion = null;
	private AnimatedSprite aniEate;
	private Sprite mSpriteAttach;
	private Random randNil2;
	private Random randomEateCake;
	private int pointArrayCake[][] = new int[][] { { 33, 485 }, { 96, 485 },
			{ 155, 485 }, { 33, 531 }, { 96, 531 }, { 155, 531 } };
	private int pointArrayCakeCooked[][] = new int[][] { { 27, 499 },
			{ 90, 499 }, { 149, 499 }, { 27, 537 }, { 90, 537 }, { 149, 537 } };
	private int pointArrayNewCakeCookedOne[][] = new int[][] { { 30, 30 },
			{ 90, 30 }, { 152, 30 }, { 30, 76 }, { 90, 76 }, { 152, 76 } };
	private int pointArrayNewCakeCookedTwo[][] = new int[][] { { 30, 69 },
			{ 90, 69 }, { 152, 69 }, { 30, 125 }, { 90, 125 }, { 152, 125 } };
	private int pointArrayNewCakeCookedThree[][] = new int[][] { { 30, 120 },
			{ 90, 120 }, { 152, 120 }, { 30, 176 }, { 90, 176 }, { 152, 176 } };
	private boolean touchAcodion = false;
	private int iTouchAcodion = 0;
	private int spaceAnimatedSpriteCake = 0;
	private float durationCake = 20f;
	private float durationCakeReset = 20f;
	private int spaceAnimatedSpriteCakeReset = 0;
	private int spaceAnimatedSpriteSwitchCake = 0;
	private float durationCakeSwitchCake = 20f;
	private int countTouchSwitch = 0;
	private int cakeCooked = 0;
	private int zIndex = 0;
	private int getTabObject = 0;
	private boolean isTouchBodyFace = false;
	private boolean isTouchGirlFace = false;
	private boolean isTouchKona = false;
	private int countKona = 0;
	private boolean boyEatCake = false;
	private boolean isTouchCarService2 = false;
	private boolean isMoveTrackToCar = false;
	private int countTouchRay = 0;
	private int moveToCar = 0;
	private boolean isTouchSwitch = false;
	private boolean checkSwitch = false;
	private boolean limitCake = false;
	private boolean finishRackOne = false, finishRackTwo = false,
			finishRackThree = false;
	private boolean isTouchGimic = false;
	private int ranEatCake = 0;
	private boolean fullCake = false;
	private int tempEat = 0;
	private float durations = 0;
	// Sound
	private Sound A1_4_1_SWICH, A1_9_POMI, A1_6_CAURTAIN, A1_7_ARE_BOY,
			A1_16_TORAC_OPEN, A1_11_HAKONITUMERU, A1_11_PAN, A1_8_GIRL_HU,
			A1_8_KONAPOWAN, A1_16_TORAC_CLOSE, A1_11_TORAY, A1_5_OBUN,
			A1_8_MOUYADA, A1_16_HAKUSYU, A1_6_4_CAR, A1_15_TORACNOSERU,
			A1_7_BOY_EAT, A1_7_BOY_EAT2, A1_16_YAKETANE, A1_4_2_CONBEA,
			A1_16_TORAC, A1_7_BOY_TACHI, A1_6_4_CAURTAIN, A1_8_KONAFURI,
			A1_16_TORAC_STOP;

	// Create Resource
	@Override
	public void onCreateResources() {
		Log.d(TAG, "on Create Rerource");
		SoundFactory.setAssetBasePath("anpanmantaiso/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("anpanmantaiso/gfx/");
		mTexturePackLoaderFromSourceAnpanmantaiso = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "anpanmantaiso/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		// Load Texture Packer
		Vol3AnpanmantaisoPacker_1 = mTexturePackLoaderFromSourceAnpanmantaiso
				.load("Vol3AnpanmantaisoPacker1.xml");
		Vol3AnpanmantaisoPacker_1.loadTexture();
		Vol3AnpanmantaisoLibrary1 = Vol3AnpanmantaisoPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3AnpanmantaisoPacker_2 = mTexturePackLoaderFromSourceAnpanmantaiso
				.load("Vol3AnpanmantaisoPacker2.xml");
		Vol3AnpanmantaisoPacker_2.loadTexture();
		Vol3AnpanmantaisoLibrary2 = Vol3AnpanmantaisoPacker_2
				.getTexturePackTextureRegionLibrary();

		iTextureRegionHaike1 = Vol3AnpanmantaisoLibrary2
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker2.A1_14_1_IPHONE_HAIKEI_ID);
		iTextureRegionHaike2 = Vol3AnpanmantaisoLibrary2
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker2.A1_14_2_IPHONE_HAIKEI_ID);
		iTextureRegionHaike3 = Vol3AnpanmantaisoLibrary2
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker2.A1_17_IPHONE_HAIKEI_ID);
		iTiledTextureRegionSwich = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_4_1_IPHONE_SWICH_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_4_2_IPHONE_SWICH_ID);
		iTextureRegionYajirushi = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_IPHONE_YAJIRUSHI_ID);

		iTiledTextureRegionObun1 = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_5_1_IPHONE_OBUN_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_5_2_IPHONE_OBUN_ID);
		iTiledTextureRegionObun2 = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_5_3_IPHONE_OBUN_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_5_4_IPHONE_OBUN_ID);
		iTiledTextureRegionPanda = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_1_2_IPHONE_PANDA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_1_3_IPHONE_PANDA_ID);
		iTiledTextureRegionLion = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_2_2_IPHONE_LION_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_2_3_IPHONE_LION_ID);
		iTiledTextureRegionUsagi = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_3_2_IPHONE_USAGI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_3_3_IPHONE_USAGI_ID);
		iTextureRegionCar = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_6_4_IPHONE_CAR_ID);

		iTiledTextureRegionBoyHand = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_1_1_IPHONE_BOY_HAND_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_1_2_IPHONE_BOY_HAND_ID);
		iTiledTextureRegionBoyFace = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_1_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_2_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_3_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_4_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_5_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_2_6_IPHONE_FACE_ID);
		iTiledTextureRegionBoyHandMain = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_3_1_IPHONE_HAND_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_3_2_IPHONE_HAND_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_3_3_IPHONE_HAND_ID);
		iTiledTextureRegionBoyBody = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_4_1_IPHONE_BOBY_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_4_2_IPHONE_BOBY_ID);
		iTiledTextureRegionKona = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_1_1_IPHONE_KONA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_1_2_IPHONE_KONA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_1_3_IPHONE_KONA_ID);
		iTiledTextureRegionGirlFace = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_2_1_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_2_2_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_2_3_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_2_4_IPHONE_FACE_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_2_5_IPHONE_FACE_ID);
		iTiledTextureRegionGirlHand = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_7_1_2_IPHONE_BOY_HAND_ID);
		iTiledTextureRegionGirlHandMain = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_3_1_IPHONE_HAND_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_3_2_IPHONE_HAND_ID);
		iTiledTextureRegionGirlBody1 = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_4_1_IPHONE_BODY_ID);
		iTiledTextureRegionGirlBody2 = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_8_4_2_IPHONE_BODY_ID);

		iTiledTextureRegionCook = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_9_1_IPHONE_MARU_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_9_2_IPHONE_MARU_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_1_1_IPHONE_HANA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_1_2_IPHONE_HANA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_2_1_IPHONE_TORI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_2_2_IPHONE_TORI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_3_1_IPHONE_LION_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_3_2_IPHONE_LION_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_4_1_IPHONE_USAGI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_4_2_IPHONE_USAGI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_5_1_IPHONE_PANDA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_5_2_IPHONE_PANDA_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_6_1_IPHONE_KAERU_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_6_2_IPHONE_KAERU_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_7_1_IPHONE_STAR_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_7_2_IPHONE_STAR_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_8_1_IPHONE_KUROWASAN_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_8_2_IPHONE_KUROWASAN_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_10_1_IPHONE_CAR_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_9_10_2_IPHONE_CAR_ID);

		iTiledTextureRegionKiji = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_10_1_IPHONE_KIJI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_10_2_IPHONE_KIJI_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_10_3_IPHONE_KIJI_ID);
		iTiledTextureRegionRack = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_1_1_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_1_2_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_2_1_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_2_2_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_3_1_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_3_2_IPHONE_RACK_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_4_IPHONE_RACK_ID);
		iTextureRegionRackBackground = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_5_IPHONE_RACK_ID);

		iTextureRegionRackItem = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_11_6_IPHONE_RACK_ID);

		iTextureRegionConbea = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_12_1_IPHONE_CONBEA_ID);
		iTextureRegionTable = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_12_2_IPHONE_TABLE_ID);
		iTiledTextureRegionAcodion = getTiledTextureFromPacker(
				Vol3AnpanmantaisoPacker_1,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_13_1_IPHONE_ACODION_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_13_2_IPHONE_ACODION_ID);
		int RegionToray[] = {
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_15_1_IPHONE_TORAY_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_15_2_IPHONE_TORAY_ID,
				Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_15_3_IPHONE_TORAY_ID };
		for (int i = 0; i < iTextureRegionToray.length; i++) {
			iTextureRegionToray[i] = Vol3AnpanmantaisoLibrary1
					.get(RegionToray[i]);
		}
		iTextureRegionCarService1 = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_16_1_1_IPHONE_CAR_ID);
		iTextureRegionCarService2 = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_16_1_2_IPHONE_CAR_ID);
		iTextureRegionCarService3 = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_16_1_3_IPHONE_CAR_ID);
		iTextureRegionSmoke = Vol3AnpanmantaisoLibrary1
				.get(Vol3AnpanmantaisoResource.Vol3AnpanmantaisoPacker1.A1_18_IPHONE_SMOKE_ID);

		mLayoutTexture = new BitmapTextureAtlas(this.getTextureManager(), 1, 1,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mLayoutTextureRegion = new TextureRegion(mLayoutTexture, 0, 0, 0, 0);
		mLayoutTiledTextureRegion = new TiledTextureRegion(mLayoutTexture,
				mLayoutTextureRegion);
	}

	@Override
	protected void loadKaraokeSound() {
		A1_4_1_SWICH = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_4_1_SWICH);
		A1_9_POMI = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_9_POMI);
		A1_6_CAURTAIN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_6_CAURTAIN);
		A1_7_ARE_BOY = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_7_ARE_BOY);
		A1_16_TORAC_OPEN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_TORAC_OPEN);
		A1_11_HAKONITUMERU = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_11_HAKONITUMERU);
		A1_11_PAN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_11_PAN);
		A1_8_GIRL_HU = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_8_GIRL_HU);
		A1_8_KONAPOWAN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_8_KONAPOWAN);
		A1_16_TORAC_CLOSE = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_TORAC_CLOSE);
		A1_11_TORAY = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_11_TORAY);
		A1_5_OBUN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_5_OBUN);
		A1_8_MOUYADA = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_8_MOUYADA);
		A1_16_HAKUSYU = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_HAKUSYU);
		A1_6_4_CAR = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_6_4_CAR);
		A1_15_TORACNOSERU = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_15_TORACNOSERU);
		A1_7_BOY_EAT = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_7_BOY_EAT);
		A1_7_BOY_EAT2 = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_7_BOY_EAT2);
		A1_16_YAKETANE = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_YAKETANE);
		A1_4_2_CONBEA = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_4_2_CONBEA);
		A1_16_TORAC = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_TORAC);
		A1_7_BOY_TACHI = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_7_BOY_TACHI);
		A1_6_4_CAURTAIN = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_6_4_CAURTAIN);
		A1_8_KONAFURI = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_8_KONAFURI);
		A1_16_TORAC_STOP = loadSoundResourceFromSD(Vol3AnpanmantaisoResource.A1_16_TORAC_STOP);
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		randNil2 = new Random();
		randomEateCake = new Random();
		// Haike2
		mSpriteHaike2 = new Sprite(0, 0, iTextureRegionHaike2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteHaike2);
		mSpriteHaike2.setVisible(true);

		mAnimatedSpritePanda = new AnimatedSprite(626, 6,
				iTiledTextureRegionPanda, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpritePanda);
		mAnimatedSpritePanda.setVisible(true);

		mAnimatedSpriteLion = new AnimatedSprite(626, 6,
				iTiledTextureRegionLion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteLion);
		mAnimatedSpriteLion.setVisible(true);

		mAnimatedSpriteUsagi = new AnimatedSprite(626, 6,
				iTiledTextureRegionUsagi, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteUsagi);
		mAnimatedSpriteUsagi.setVisible(true);

		mSpriteCar = new Sprite(626, 6, iTextureRegionCar,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteCar);
		mSpriteCar.setVisible(true);

		mSpriteHaike1 = new Sprite(0, 0, iTextureRegionHaike1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteHaike1);
		mSpriteHaike1.setVisible(true);

		mSpriteHaike3 = new Sprite(0, 0, iTextureRegionHaike3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteHaike3);
		mSpriteHaike3.setVisible(false);

		for (int i = 0; i < mSpriteToray.length; i++) {
			if (i == 0) {
				mSpriteToray[i] = new SpriteToray(69, 264,
						iTextureRegionToray[i],
						this.getVertexBufferObjectManager());
			}
			if (i == 1) {
				mSpriteToray[i] = new SpriteToray(69, 320,
						iTextureRegionToray[i],
						this.getVertexBufferObjectManager());
			}
			if (i == 2) {
				mSpriteToray[i] = new SpriteToray(69, 376,
						iTextureRegionToray[i],
						this.getVertexBufferObjectManager());
			}
			this.mScene.attachChild(mSpriteToray[i]);
			this.mScene.registerTouchArea(mSpriteToray[i]);
			mSpriteToray[i].setVisible(false);
		}
		mAnimatedSpriteAcodion = new AnimatedSprite(479, -37,
				iTiledTextureRegionAcodion, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (!touchAcodion) {

						if (iTouchAcodion > 3) {
							iTouchAcodion = 0;
						}
						touchAcodion = true;
						this.clearEntityModifiers();
						this.setCurrentTileIndex(1);
						switch (iTouchAcodion) {
						case 0:
							A1_6_CAURTAIN.play();
							startAndMoveThreeAnimated(mAnimatedSpritePanda);
							break;
						case 1:
							A1_6_CAURTAIN.play();
							startAndMoveThreeAnimated(mAnimatedSpriteLion);
							break;
						case 2:
							A1_6_CAURTAIN.play();
							startAndMoveThreeAnimated(mAnimatedSpriteUsagi);
							break;
						case 3:
							A1_6_4_CAR.play();
							A1_6_4_CAURTAIN.play();
							startMoveCar(mSpriteCar);
							break;
						}
						iTouchAcodion++;
						return true;
					}
				}
				return false;
			};
		};
		this.mScene.attachChild(mAnimatedSpriteAcodion);
		this.mScene.registerTouchArea(mAnimatedSpriteAcodion);
		mAnimatedSpriteAcodion.setVisible(true);
		mSpriteConbea = new Sprite(-11, 310, iTextureRegionConbea,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteConbea);
		mSpriteConbea.setVisible(true);

		// Xu ly touch vao em be trai
		mAnimatedSpriteBoyFace = new AnimatedSprite(-31, -112,
				iTiledTextureRegionBoyFace, this.getVertexBufferObjectManager()) {
			int begin = 0;

			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (!isTouchBodyFace
							&& spriteCake.getCountClickCooked() != 18
							&& !checkSwitch) {
						isTouchBodyFace = true;
						checkSwitch = true;
						setTouchGimmic3(false);
						if (cakeCooked > 0
								&& (int) listAnimatedSprite.get(0).getX() < 296) {
							// Cau be dang an thi khong duoc touch vao Can gat
							// (Se anh huong den tinh toa do)
							A1_7_BOY_TACHI.play();
							mAnimatedSpriteBoyHand.clearEntityModifiers();
							mAnimatedSpriteBoyHand
									.registerEntityModifier(new DelayModifier(
											0.5f,
											new IEntityModifierListener() {
												@Override
												public void onModifierStarted(
														IModifier<IEntity> arg0,
														IEntity arg1) {
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> arg0,
														IEntity arg1) {
													A1_7_BOY_EAT.play();
												}
											}));
							// Neu thoa dieu kien thi cho cau be an
							if (cakeCooked == 1
									&& (int) listAnimatedSprite.get(0).getX() < 296) {
								ranEatCake = 0;
							} else {
								ranEatCake = randomEateCake
										.nextInt(cakeCooked - 1);
							}
							// De khong anh huong den sap xep
							if (!isTouchSwitch) {
								if (ranEatCake == 8 || ranEatCake == 7
										|| ranEatCake == 6 || ranEatCake == 5) {
									ranEatCake = 0;
								}
							}
							Vol3Anpanmantaiso.this.mScene
									.unregisterTouchArea(listAnimatedSprite
											.get(ranEatCake));
							aniEate = listAnimatedSprite.get(ranEatCake);
							listAnimatedSprite.remove(ranEatCake);
							if (isTouchSwitch) {
								tempEat = (int) aniEate.getX();
							} else {
								tempEat = ranEatCake * 15;
							}
							// Cho thoi gian nhanh cham tuy thuoc vao tung thoi
							// diem
							float newDuration = 0;
							if (cakeCooked > 3) {
								newDuration = 2.0f;
								Log.d("TRuong hop nao day", "TRUONG HOP 1");
								begin = 0;
							} else if (cakeCooked > 2) {
								if (!isTouchSwitch) {
									newDuration = 2.0f;
									Log.d("TRuong hop nao day", "TRUONG HOP 2");
								} else {
									newDuration = 2.0f;
									Log.d("TRuong hop nao day", "TRUONG HOP 3");
								}
								begin = 0;
							} else {
								if (!isTouchSwitch) {
									newDuration = 4.0f;
									Log.d("TRuong hop nao day", "TRUONG HOP 4");
									begin = 1;
								} else {
									Log.d("TRuong hop nao day", "TRUONG HOP 5");
									newDuration = 2.0f;
									begin = 0;
								}
							}
							durations = (mAnimatedSpriteBoyBody.getmXFirst() - tempEat)
									* newDuration
									/ mAnimatedSpriteBoyBody.getmXFirst();
							Log.d("Test Touch case",
									":" + spriteCake.getCountClickCooked());
							int pFrames[] = { 1, 2 };
							long pFrameDurations[] = { 1000, 1000 };
							// Doi anh cho boy
							mAnimatedSpriteBoyFace.animate(pFrameDurations,
									pFrames, 0, new IAnimationListener() {
										@Override
										public void onAnimationStarted(
												AnimatedSprite arg0, int arg1) {
										}

										@Override
										public void onAnimationLoopFinished(
												AnimatedSprite arg0, int arg1,
												int arg2) {
										}

										@Override
										public void onAnimationFrameChanged(
												AnimatedSprite arg0, int arg1,
												int arg2) {
										}

										@Override
										public void onAnimationFinished(
												AnimatedSprite arg0) {
											// TODO
											Log.d(TAG,
													"Banh nau khi touch vao be trai"
															+ ranEatCake);
											// Ket thuc doi anh, dang ky di
											// chuyen cho boy
											int pos = 0;
											if (!isTouchSwitch) {
												if (ranEatCake == 5) {
													pos = tempEat + 15;
												} else {
													pos = tempEat - 25;
												}
											} else {
												pos = tempEat - 25;
											}
											mAnimatedSpriteBoyBody
													.registerEntityModifier(new MoveModifier(
															durations,
															mAnimatedSpriteBoyBody
																	.getmXFirst(),
															pos,
															mAnimatedSpriteBoyBody
																	.getmYFirst(),
															mAnimatedSpriteBoyBody
																	.getmYFirst(),
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mAnimatedSpriteBoyBody
																			.clearUpdateHandlers();
																	aniEate.clearEntityModifiers();
																	aniEate.registerEntityModifier(new MoveModifier(
																			0.1f,
																			aniEate.getX(),
																			aniEate.getX(),
																			aniEate.getY(),
																			aniEate.getY() - 50,
																			new IEntityModifierListener() {
																				@Override
																				public void onModifierStarted(
																						IModifier<IEntity> arg0,
																						IEntity arg1) {
																				}

																				@Override
																				public void onModifierFinished(
																						IModifier<IEntity> arg0,
																						IEntity arg1) {
																					// Lay
																					// banh
																					// xong
																					// Bat
																					// dau
																					// an
																					// Cho
																					// phep
																					// gat
																					// can
																					// gat
																					Vol3Anpanmantaiso.this.mScene
																							.unregisterUpdateHandler(timeCakes);
																					sortIndexBoyEatCake();
																					boyEatCake = true;
																					cakeCooked -= 1;
																					setUnvisibleYajirushi();
																					// Tinh
																					// lai
																					// toan
																					// bo
																					// banh
																					// hien
																					// co
																					switchCackeFalse();
																					checkSwitch = false;
																					setTouchGimmic3(true);
																					A1_7_BOY_EAT2
																							.play();
																					int pFrames1[] = {
																							3,
																							4,
																							3,
																							4 };
																					long pFrameDurations1[] = {
																							400,
																							400,
																							400,
																							400 };
																					mAnimatedSpriteBoyHandMain
																							.setAlpha(0.0f);
																					mAnimatedSpriteBoyHand
																							.setVisible(true);
																					mAnimatedSpriteBoyHand
																							.setCurrentTileIndex(0);
																					mAnimatedSpriteBoyBody
																							.setCurrentTileIndex(1);
																					mAnimatedSpriteBoyFace
																							.animate(
																									pFrameDurations1,
																									pFrames1,
																									0,
																									new IAnimationListener() {
																										@Override
																										public void onAnimationFinished(
																												AnimatedSprite arg0) {
																											// An
																											// xong
																											// va
																											// di
																											// chuyen
																											// lai
																											// cho
																											// cu
																											mAnimatedSpriteBoyBody
																													.setCurrentTileIndex(0);
																											mAnimatedSpriteBoyFace
																													.setCurrentTileIndex(0);
																											mAnimatedSpriteBoyHandMain
																													.setAlpha(1.0f);
																											mAnimatedSpriteBoyHand
																													.setVisible(false);
																											aniEate.setVisible(false);
																											if (ranEatCake == 0
																													&& begin == 1) {
																												durations = durations - 2.0f;
																											}
																											mAnimatedSpriteBoyBody
																													.registerEntityModifier(new MoveModifier(
																															durations,
																															mAnimatedSpriteBoyBody
																																	.getX(),
																															mAnimatedSpriteBoyBody
																																	.getmXFirst(),
																															mAnimatedSpriteBoyBody
																																	.getmYFirst(),
																															mAnimatedSpriteBoyBody
																																	.getmYFirst(),
																															new IEntityModifierListener() {
																																@Override
																																public void onModifierFinished(
																																		IModifier<IEntity> arg0,
																																		IEntity arg1) {
																																	isTouchBodyFace = false;
																																	boyEatCake = false;
																																}

																																@Override
																																public void onModifierStarted(
																																		IModifier<IEntity> arg0,
																																		IEntity arg1) {
																																}
																															}));
																										}

																										@Override
																										public void onAnimationFrameChanged(
																												AnimatedSprite arg0,
																												int arg1,
																												int arg2) {
																										}

																										@Override
																										public void onAnimationLoopFinished(
																												AnimatedSprite arg0,
																												int arg1,
																												int arg2) {
																										}

																										@Override
																										public void onAnimationStarted(
																												AnimatedSprite arg0,
																												int arg1) {
																										}
																									});
																				}
																			}));
																}
															},
															EaseLinear
																	.getInstance()));
										}
									});
						} else {
							// Truong hop khong co banh
							A1_7_ARE_BOY.play();
							if (mAnimatedSpriteBoyHandMain.isAnimationRunning()) {
								mAnimatedSpriteBoyHandMain.stopAnimation();
							}
							mAnimatedSpriteBoyFace.setCurrentTileIndex(5);
							mAnimatedSpriteBoyHandMain.setCurrentTileIndex(2);
							mAnimatedSpriteBoyHand.setVisible(true);
							mAnimatedSpriteBoyHand.setCurrentTileIndex(1);
							mAnimatedSpriteBoyFace
									.registerEntityModifier(new DelayModifier(
											1.5f,
											new IEntityModifierListener() {
												@Override
												public void onModifierStarted(
														IModifier<IEntity> arg0,
														IEntity arg1) {
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> arg0,
														IEntity arg1) {
													mAnimatedSpriteBoyFace
															.setCurrentTileIndex(0);
													mAnimatedSpriteBoyHandMain
															.setCurrentTileIndex(0);
													mAnimatedSpriteBoyHand
															.setVisible(false);
													mAnimatedSpriteBoyHand
															.setCurrentTileIndex(0);
													isTouchBodyFace = false;
													checkSwitch = false;
													setTouchGimmic3(true);
												}
											}));
						}
					}
					return true;
				}
				return false;
			};
		};
		mAnimatedSpriteBoyFace.setVisible(true);
		this.mScene.registerTouchArea(mAnimatedSpriteBoyFace);

		mAnimatedSpriteBoyHand = new AnimatedSprite(45, 100,
				iTiledTextureRegionBoyHand, this.getVertexBufferObjectManager());
		mAnimatedSpriteBoyHand.setVisible(false);

		mAnimatedSpriteBoyHandMain = new AnimatedSprite(-10, -14,
				iTiledTextureRegionBoyHandMain,
				this.getVertexBufferObjectManager());
		mAnimatedSpriteBoyHandMain.setVisible(true);

		mAnimatedSpriteBoyBody = new AnimatedSprite(423, 258,
				iTiledTextureRegionBoyBody, this.getVertexBufferObjectManager());
		mAnimatedSpriteBoyBody.setVisible(true);

		mAnimatedSpriteBoyBody.attachChild(mAnimatedSpriteBoyHandMain);
		mAnimatedSpriteBoyHandMain.attachChild(mAnimatedSpriteBoyFace);
		mAnimatedSpriteBoyFace.attachChild(mAnimatedSpriteBoyHand);
		this.mScene.attachChild(mAnimatedSpriteBoyBody);

		// Girl
		mAnimatedSpriteGirlBody1 = new AnimatedSprite(598, 104,
				iTiledTextureRegionGirlBody1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteGirlBody1);
		mAnimatedSpriteGirlBody1.setVisible(true);

		mSpriteTable = new Sprite(549, 165, iTextureRegionTable,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteTable);
		mSpriteTable.setVisible(true);

		mAnimatedSpriteKiji = new AnimatedSprite(605, 170,
				iTiledTextureRegionKiji, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteKiji);
		mAnimatedSpriteKiji.setVisible(true);
		// Xu ly bot mi
		mAnimatedSpriteKona = new AnimatedSprite(511, 34,
				iTiledTextureRegionKona, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				int pX = (int) pSceneTouchEvent.getX();
				int pY = (int) pSceneTouchEvent.getY();
				if (pSceneTouchEvent.isActionDown()) {

					if (checkContains(mAnimatedSpriteKona, 50, 138, 100, 200,
							pX, pY)) {
						if (!isTouchGirlFace && !isTouchKona) {
							Log.d("TEST Count", "CountKona" + countKona);
							if (countKona > 1) {
								countKona = 0;
							}
							if (countKona == 0) {
								A1_8_KONAFURI.play();
								A1_8_KONAPOWAN.play();
								isTouchKona = true;
								mAnimatedSpriteKona.setCurrentTileIndex(1);
								mAnimatedSpriteGirlFace.setCurrentTileIndex(1);
								mAnimatedSpriteKona
										.registerEntityModifier(new DelayModifier(
												1.0f,
												new IEntityModifierListener() {
													@Override
													public void onModifierStarted(
															IModifier<IEntity> arg0,
															IEntity arg1) {
													}

													@Override
													public void onModifierFinished(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														mAnimatedSpriteKona
																.setCurrentTileIndex(0);
														mAnimatedSpriteGirlFace
																.setCurrentTileIndex(0);
														isTouchKona = false;
														countKona++;
													}
												}));
							}
							if (countKona == 1) {
								A1_8_KONAFURI.play();
								A1_8_KONAPOWAN.play();
								isTouchKona = true;
								mAnimatedSpriteKona.setCurrentTileIndex(2);
								mAnimatedSpriteGirlFace.setCurrentTileIndex(2);
								mAnimatedSpriteKona
										.registerEntityModifier(new DelayModifier(
												1.0f,
												new IEntityModifierListener() {
													@Override
													public void onModifierStarted(
															IModifier<IEntity> arg0,
															IEntity arg1) {
													}

													@Override
													public void onModifierFinished(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														A1_8_MOUYADA.play();
														mAnimatedSpriteKona
																.setCurrentTileIndex(0);
														mAnimatedSpriteKona
																.setVisible(false);
														mAnimatedSpriteGirlFace
																.setCurrentTileIndex(3);
														mAnimatedSpriteGirlFace
																.registerEntityModifier(new DelayModifier(
																		1.7f,
																		new IEntityModifierListener() {
																			@Override
																			public void onModifierStarted(
																					IModifier<IEntity> arg0,
																					IEntity arg1) {
																			}

																			@Override
																			public void onModifierFinished(
																					IModifier<IEntity> arg0,
																					IEntity arg1) {
																				mAnimatedSpriteKona
																						.setVisible(true);
																				mAnimatedSpriteGirlFace
																						.setCurrentTileIndex(0);
																				isTouchKona = false;
																				countKona++;
																			}
																		}));
													}
												}));
							}
						}
						return true;
					}
				}
				return false;
			};
		};
		this.mScene.attachChild(mAnimatedSpriteKona);
		this.mScene.registerTouchArea(mAnimatedSpriteKona);
		mAnimatedSpriteKona.setVisible(true);

		mAnimatedSpriteGirlHandMain = new AnimatedSprite(623, 120,
				iTiledTextureRegionGirlHandMain,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteGirlHandMain);
		mAnimatedSpriteGirlHandMain.setVisible(true);

		mAnimatedSpriteGirlBody2 = new AnimatedSprite(598, 104,
				iTiledTextureRegionGirlBody2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteGirlBody2);
		mAnimatedSpriteGirlBody2.setVisible(false);
		// Xu ly co gai
		mAnimatedSpriteGirlFace = new AnimatedSprite(559, 27,
				iTiledTextureRegionGirlFace,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (!isTouchGirlFace && !isTouchKona
							&& spriteCake.getCountClickCooked() != 18) {
						A1_8_GIRL_HU.play();
						isTouchGirlFace = true;
						if (mAnimatedSpriteGirlHandMain.isAnimationRunning()) {
							mAnimatedSpriteGirlHandMain.stopAnimation();
						}
						if (mAnimatedSpriteKiji.isAnimationRunning()) {
							mAnimatedSpriteKiji.stopAnimation(0);
						}
						mAnimatedSpriteGirlHandMain.setVisible(false);
						mAnimatedSpriteGirlFace.setCurrentTileIndex(4);
						mAnimatedSpriteGirlBody2.setVisible(true);
						mAnimatedSpriteGirlHand.setVisible(true);
						mAnimatedSpriteGirlFace
								.registerEntityModifier(new DelayModifier(1.5f,
										new IEntityModifierListener() {
											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
											}

											@Override
											public void onModifierFinished(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												mAnimatedSpriteGirlHandMain
														.setVisible(true);
												mAnimatedSpriteGirlFace
														.setCurrentTileIndex(0);
												mAnimatedSpriteGirlBody2
														.setVisible(false);
												mAnimatedSpriteGirlHand
														.setVisible(false);
												startAnimationKiji();
												startAnimationGirl(mAnimatedSpriteGirlHandMain);
												isTouchGirlFace = false;
											}
										}));
						return true;
					}
				}
				return false;

			};
		};
		this.mScene.attachChild(mAnimatedSpriteGirlFace);
		this.mScene.registerTouchArea(mAnimatedSpriteGirlFace);
		mAnimatedSpriteGirlFace.setVisible(true);

		mAnimatedSpriteGirlHand = new AnimatedSprite(605, 94,
				iTiledTextureRegionGirlHand,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteGirlHand);
		mAnimatedSpriteGirlHand.setVisible(false);

		for (int i = 0; i < mSpriteRackBackground.length; i++) {
			if (i == 0) {
				mSpriteRackBackground[i] = new Sprite(168, 30,
						iTextureRegionRackBackground,
						this.getVertexBufferObjectManager());
			}
			if (i == 1) {
				mSpriteRackBackground[i] = new Sprite(168, 65,
						iTextureRegionRackBackground,
						this.getVertexBufferObjectManager());
			}
			if (i == 2) {
				mSpriteRackBackground[i] = new Sprite(168, 100,
						iTextureRegionRackBackground,
						this.getVertexBufferObjectManager());
			}
			this.mScene.attachChild(mSpriteRackBackground[i]);
			mSpriteRackBackground[i].setVisible(true);
		}
		for (int i = 0; i < mSpriteRack.length; i++) {
			if (i % 2 == 0) {
				mSpriteRack[i] = new Sprite(151, 10,
						iTiledTextureRegionRack.getTextureRegion(i),
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mSpriteRack[i]);
				mSpriteRack[i].setVisible(true);
			}

		}

		mAnimatedSpriteObun1 = new AnimatedSprite(308, 240,
				iTiledTextureRegionObun1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteObun1);
		mAnimatedSpriteObun1.setVisible(true);

		mAnimatedSpriteObun2 = new AnimatedSprite(112, 30,
				iTiledTextureRegionObun2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mAnimatedSpriteObun2);
		mAnimatedSpriteObun2.setCurrentTileIndex(2);
		mAnimatedSpriteObun2.setVisible(false);
		// Create Sprite layout check cake is cooked
		mSpriteAttach = new Sprite(338, 280, this.mLayoutTextureRegion,
				this.getVertexBufferObjectManager());
		mSpriteAttach.setSize(1, 100);
		mSpriteAttach.setAlpha(0.0f);
		mSpriteAttach.setColor(1, 0, 0);
		mSpriteAttach.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mScene.attachChild(mSpriteAttach);
		// Create aniEate
		aniEate = new AnimatedSprite(0, 0, this.mLayoutTiledTextureRegion,
				this.getVertexBufferObjectManager());

		for (int i = 0; i < mSpriteRack.length; i++) {

			if (i == 5) {
				mSpriteRack[i] = new Sprite(0, 349,
						iTiledTextureRegionRack.getTextureRegion(i),
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mSpriteRack[i]);
				mSpriteRack[i].setVisible(true);
			}
			if (i == 3) {
				mSpriteRack[i] = new Sprite(0, 400,
						iTiledTextureRegionRack.getTextureRegion(i),
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mSpriteRack[i]);
				mSpriteRack[i].setVisible(true);
			}
			if (i == 1) {
				mSpriteRack[i] = new Sprite(0, 444,
						iTiledTextureRegionRack.getTextureRegion(i),
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(mSpriteRack[i]);
				mSpriteRack[i].setVisible(true);
			}
		}
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			mSpriteRackItem[i] = new Sprite(pointArrayCake[i][0],
					pointArrayCake[i][1], iTextureRegionRackItem,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(mSpriteRackItem[i]);
			mSpriteRackItem[i].setVisible(true);
		}
		mSpriteRack[3].setVisible(false);
		mSpriteRack[5].setVisible(false);
		// Xu ly can gat
		mAnimatedSpriteSwich = new AnimatedSprite(744, 522,
				iTiledTextureRegionSwich, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (countTouchSwitch == 0 && !checkSwitch && cakeCooked < 8) {
						mAnimatedSpriteSwich.clearEntityModifiers();
						A1_4_1_SWICH.play();
						checkSwitch = true;
						isTouchSwitch = true;
						isTouchGimic = true;
						setTouchGimmic3(false);
						mAnimatedSpriteSwich.setCurrentTileIndex(1);
						countTouchSwitch = 1;
						setCakeWithSwitch(0);
					}
					if (countTouchSwitch == 1 && !checkSwitch && cakeCooked < 8) {
						mAnimatedSpriteSwich.clearEntityModifiers();
						countTouchSwitch = 0;
						A1_4_1_SWICH.play();
						checkSwitch = true;
						isTouchSwitch = false;
						isTouchGimic = true;
						setTouchGimmic3(false);
						mAnimatedSpriteSwich.setCurrentTileIndex(0);
						setCakeWithSwitch(1);
					}

					return true;
				}
				return false;
			};
		};
		this.mScene.attachChild(mAnimatedSpriteSwich);
		this.mScene.registerTouchArea(mAnimatedSpriteSwich);
		mAnimatedSpriteSwich.setVisible(true);

		mSpriteCarService2 = new Sprite(0, 0, iTextureRegionCarService2,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchCarService2) {
						// Translate Track cooked Finish
						A1_16_TORAC_OPEN.play();
						A1_16_TORAC_CLOSE.play();
						for (int i = 0; i < mSpriteToray.length; i++) {
							moveToray(mSpriteToray[i]);
						}
						isTouchCarService2 = false;
						mSpriteCarService2.clearEntityModifiers();
						mSpriteCarService2.setVisible(false);
						mSpriteCarService3.setVisible(true);
						isMoveTrackToCar = true;
						Log.d(TAG, "Bat dau cho touch vao khay banh");
						return true;
					}
				}
				return false;
			};
		};
		mSpriteCarService3 = new Sprite(-108, 0, iTextureRegionCarService3,
				this.getVertexBufferObjectManager());

		mSpriteSmoke = new Sprite(62, 254, iTextureRegionSmoke,
				this.getVertexBufferObjectManager());
		mSpriteCarService1 = new Sprite(267, 179, iTextureRegionCarService1,
				this.getVertexBufferObjectManager());
		mSpriteCarService1.attachChild(mSpriteCarService3);
		mSpriteCarService1.attachChild(mSpriteCarService2);
		mSpriteCarService1.attachChild(mSpriteSmoke);
		this.mScene.attachChild(mSpriteCarService1);
		mSpriteCarService1.setVisible(false);
		mSpriteCarService3.setVisible(false);
		this.mScene.registerTouchArea(mSpriteCarService2);
		addGimmicsButton(this.mScene, Vol3AnpanmantaisoResource.SOUND_GIMMIC,
				Vol3AnpanmantaisoResource.IMAGE_GIMMIC,
				Vol3AnpanmantaisoLibrary1);

		mSpriteYajirushi = new Sprite(15, 338, iTextureRegionYajirushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(mSpriteYajirushi);
		mSpriteYajirushi.setVisible(false);
	}

	private void setUnvisibleYajirushi() {
		limitCake = false;

		mSpriteConbea.clearEntityModifiers();
		mSpriteConbea.unregisterUpdateHandler(timeYajirushi);
		mSpriteYajirushi.setVisible(false);
		if (mSpriteRack[1].isVisible() && !finishRackOne) {
			mSpriteRack[1].clearEntityModifiers();
		}
		if (mSpriteRack[3].isVisible() && !finishRackTwo) {
			mSpriteRack[3].clearEntityModifiers();
		}
		if (mSpriteRack[5].isVisible() && !finishRackThree) {
			mSpriteRack[5].clearEntityModifiers();
		}
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			if (mSpriteRackItem[i].isVisible()) {
				mSpriteRackItem[i].clearEntityModifiers();
			}
		}

	}

	private void scaleRackAndYajirushi() {
		mSpriteConbea.registerEntityModifier(new DelayModifier(2f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSpriteYajirushi.setVisible(true);
						timeYajirushi = new TimerHandler(2.0f, true,
								new ITimerCallback() {
									@Override
									public void onTimePassed(
											TimerHandler pTimerHandler) {
										mSpriteYajirushi
												.registerEntityModifier(new SequenceEntityModifier(
														new ScaleModifier(0.1f,
																1f, 1.07f),
														new ScaleModifier(0.1f,
																1.07f, 1f)));
										if (mSpriteRack[1].isVisible()
												&& !finishRackOne) {
											mSpriteRack[1]
													.registerEntityModifier(new SequenceEntityModifier(
															new ScaleModifier(
																	0.1f, 1f,
																	1.07f),
															new ScaleModifier(
																	0.1f,
																	1.07f, 1f)));
										}
										if (mSpriteRack[3].isVisible()
												&& !finishRackTwo) {
											mSpriteRack[3]
													.registerEntityModifier(new SequenceEntityModifier(
															new ScaleModifier(
																	0.1f, 1f,
																	1.07f),
															new ScaleModifier(
																	0.1f,
																	1.07f, 1f)));
										}
										if (mSpriteRack[5].isVisible()
												&& !finishRackThree
												&& !fullCake) {
											mSpriteRack[5]
													.registerEntityModifier(new SequenceEntityModifier(
															new ScaleModifier(
																	0.1f, 1f,
																	1.07f),
															new ScaleModifier(
																	0.1f,
																	1.07f, 1f)));
										}
										for (int i = 0; i < mSpriteRackItem.length; i++) {
											if (mSpriteRackItem[i].isVisible()
													&& mSpriteRackItem[i] != null) {
												mSpriteRackItem[i]
														.clearEntityModifiers();
												mSpriteRackItem[i]
														.registerEntityModifier(new SequenceEntityModifier(
																new ScaleModifier(
																		0.1f,
																		1f,
																		1.07f),
																new ScaleModifier(
																		0.1f,
																		1.07f,
																		1f)));
											}
										}
										for (int i = 0; i < cakeCooked; i++) {
											listAnimatedSprite
													.get(i)
													.registerEntityModifier(
															new SequenceEntityModifier(
																	new ScaleModifier(
																			0.1f,
																			1f,
																			1.07f),
																	new ScaleModifier(
																			0.1f,
																			1.07f,
																			1f)));
										}
									}
								});
						mSpriteConbea.registerUpdateHandler(timeYajirushi);
					}
				}));
	}

	// Process cake when touch switch
	private void setCakeWithSwitch(final int doit) {
		Vol3Anpanmantaiso.this.mScene.unregisterUpdateHandler(timeCakes);
		for (int i = 0; i < listAnimatedSprite.size(); i++) {
			listAnimatedSprite.get(i).clearEntityModifiers();
		}
		mAnimatedSpriteSwich.registerEntityModifier(new DelayModifier(1.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						checkSwitch = false;
						isTouchGimic = false;
						setTouchGimmic3(true);
						if (doit == 1) {
							switchCacke();
						}
					}
				}));
	}

	private void switchCacke() {
		if (!isTouchSwitch) {
			resetDurationSpaceCake();
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				calDurationSwitch(i, spaceAnimatedSpriteSwitchCake);
				spaceAnimatedSpriteSwitchCake += 15;
			}
			spaceAnimatedSpriteCake = spaceAnimatedSpriteSwitchCake;
			Vol3Anpanmantaiso.this.mScene.registerUpdateHandler(timeCakes);
		}
	}

	private void calDurationSwitchTrue(final int anis, int spaces) {
		if (listAnimatedSprite.get(anis) != null) {
			if ((int) listAnimatedSprite.get(anis).getX() != spaces) {
				listAnimatedSprite.get(anis).clearEntityModifiers();
				listAnimatedSprite
						.get(anis)
						.registerEntityModifier(
								new MoveModifier(
										(listAnimatedSprite.get(anis).getX() - spaceAnimatedSpriteSwitchCake)
												* durationCakeSwitchCake
												/ listAnimatedSprite.get(anis)
														.getmXFirst(),
										(int) listAnimatedSprite.get(anis)
												.getX(),
										spaceAnimatedSpriteSwitchCake,
										listAnimatedSprite.get(anis).getY(),
										listAnimatedSprite.get(anis).getY(),
										EaseLinear.getInstance()));
			}
		}
	}

	private void switchCackeFalse() {
		if (!isTouchSwitch) {
			resetDurationSpaceCake();
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				calDurationSwitchTrue(i, spaceAnimatedSpriteSwitchCake);
				spaceAnimatedSpriteSwitchCake += 15;
			}
			spaceAnimatedSpriteCake = spaceAnimatedSpriteSwitchCake;
			Vol3Anpanmantaiso.this.mScene.registerUpdateHandler(timeCakes);
		}
	}

	private void calDurationSwitch(final int anis, int spaces) {
		if (listAnimatedSprite.get(anis) != null) {
			if ((int) listAnimatedSprite.get(anis).getX() != spaces) {
				listAnimatedSprite.get(anis).clearEntityModifiers();
				listAnimatedSprite
						.get(anis)
						.registerEntityModifier(
								new MoveModifier(
										(listAnimatedSprite.get(anis).getX() - spaceAnimatedSpriteSwitchCake)
												* durationCakeSwitchCake
												/ listAnimatedSprite.get(anis)
														.getmXFirst(),
										(int) listAnimatedSprite.get(anis)
												.getX(),
										spaceAnimatedSpriteSwitchCake,
										listAnimatedSprite.get(anis).getY(),
										listAnimatedSprite.get(anis).getY(),
										EaseLinear.getInstance()));
			}
		}
	}

	// Class Process Toray
	class SpriteToray extends Sprite {
		public SpriteToray(int pX, int pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		}

		private void moveToCar() {
			mSpriteCarService1.registerEntityModifier(new DelayModifier(0.7f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							mSpriteCarService3.setVisible(false);
							mSpriteCarService2.setVisible(true);
							for (int i = 0; i < mSpriteToray.length; i++) {
								setSpriteWhenWin(mSpriteToray[i]);
							}
							A1_16_YAKETANE.play();
							A1_16_HAKUSYU.play();
							mSpriteCarService1
									.registerEntityModifier(new MoveModifier(
											3.5f, mSpriteCarService1.getX(),
											960, mSpriteCarService1.getY(),
											mSpriteCarService1.getY(),
											new IEntityModifierListener() {
												@Override
												public void onModifierStarted(
														IModifier<IEntity> paramIModifier,
														IEntity paramT) {
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> paramIModifier,
														IEntity paramT) {
													Log.d(TAG,
															"Play song again");
													playAgain();
												}
											}, EaseLinear.getInstance()));
						}
					}));
		}

		private void registerMoveCar(final Sprite sprites, final int pX,
				final int pY) {
			A1_11_TORAY.play();
			if (sprites.equals(mSpriteToray[0])) {
				Vol3Anpanmantaiso.this.mScene
						.unregisterTouchArea(Vol3Anpanmantaiso.this.mSpriteToray[0]);
				Log.d("OK", "OBJECT mSpriteToray" + 0);
			}
			if (sprites.equals(mSpriteToray[1])) {
				Vol3Anpanmantaiso.this.mScene
						.unregisterTouchArea(Vol3Anpanmantaiso.this.mSpriteToray[1]);
				Log.d("OK", "OBJECT mSpriteToray" + 1);
			}
			if (sprites.equals(mSpriteToray[2])) {
				Vol3Anpanmantaiso.this.mScene
						.unregisterTouchArea(Vol3Anpanmantaiso.this.mSpriteToray[2]);
				Log.d("OK", "OBJECT mSpriteToray" + 2);
			}
			Vol3Anpanmantaiso.this.mScene.unregisterTouchArea(sprites);
			sprites.setZIndex(zIndex++);
			mSpriteSmoke.setZIndex(zIndex++);
			Vol3Anpanmantaiso.this.mScene.sortChildren();
			sprites.clearEntityModifiers();
			sprites.registerEntityModifier(new DelayModifier(0.5f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isMoveTrackToCar = true;
						}
					}));
			sprites.registerEntityModifier(new MoveModifier(1.0f, sprites
					.getX(), pX, sprites.getY(), pY,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							moveToCar++;
							if (moveToCar == 3) {
								moveToCar = 0;
								moveToCar();
							}
						}
					}, EaseLinear.getInstance()));
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (pSceneTouchEvent.isActionDown()) {
				if (isMoveTrackToCar) {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							isMoveTrackToCar = false;
							zIndex = Vol3Anpanmantaiso.this.mScene.getChild(
									mScene.getLastChild().getTag()).getZIndex();
							countTouchRay++;
							Log.d(TAG, "Touch vao khay:" + countTouchRay);
							if (countTouchRay == 1) {
								registerMoveCar(SpriteToray.this, 374, 380);
							}
							if (countTouchRay == 2) {
								registerMoveCar(SpriteToray.this, 374, 332);
							}
							if (countTouchRay == 3) {
								registerMoveCar(SpriteToray.this, 374, 284);
								countTouchRay = 0;
							}
						}
					});
				}
			}
			return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);
		}

	}

	// Class Process Cake
	class AnimatedSpriteCake extends GenericPool<AnimatedSprite> {
		private ITiledTextureRegion mTiledTextureRegion;
		private int countClickCooked = 0;

		public int getCountClickCooked() {
			return countClickCooked;
		}

		private boolean isTouchCake = true;

		public void setTouchCake(boolean isTouchCake) {
			this.isTouchCake = isTouchCake;
		}

		public boolean isTouchCake() {
			return isTouchCake;
		}

		public void setCountClickCooked(int countClickCooked) {
			this.countClickCooked = countClickCooked;
		}

		// Constructor
		public AnimatedSpriteCake(ITiledTextureRegion pITiledTextureRegion) {
			if (pITiledTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have
				// a TextureRegion
				throw new IllegalArgumentException(
						"The texture region must not be NULL");
			}
			this.mTiledTextureRegion = pITiledTextureRegion;
		}

		// Process Track
		private void moveTrackFinish(final Sprite one, final Sprite two,
				final int pX, final int pY, final int choose) {
			one.clearEntityModifiers();
			one.registerEntityModifier(new DelayModifier(0.5f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(
								IModifier<IEntity> paramIModifier,
								IEntity paramT) {
							sortIndexTrack(one);
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> paramIModifier,
								IEntity paramT) {
							A1_11_TORAY.play();
							if (choose != 3) {
								two.setVisible(true);
								for (int i = 0; i < mSpriteRackItem.length; i++) {
									mSpriteRackItem[i].setVisible(true);
								}
							}
							sortIndexTrack(one);
							one.registerEntityModifier(new MoveModifier(0.5f,
									one.getmXFirst(), pX, one.getY(), pY,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											sortIndexTrack(one);
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											if (choose == 1) {
												isTouchCake = true;
												sortZindexScreenTrackOne();
											}
											if (choose == 2) {
												isTouchCake = true;
												sortZindexScreenTrackTwo();
											}
											if (choose == 3) {
												fullCake = true;
												sortZindexScreenTrackThree();
												A1_15_TORACNOSERU.play();
												Vol3Anpanmantaiso.this.mScene
														.unregisterUpdateHandler(timeCakes);
												one.registerEntityModifier(new DelayModifier(
														1.5f,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> paramIModifier,
																	IEntity paramT) {
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> paramIModifier,
																	IEntity paramT) {
																runOnUpdateThread(new Runnable() {
																	@Override
																	public void run() {
																		A1_16_TORAC
																				.play();
																		finishWhenCakeCookedFull();
																	}
																});
															}
														}));
											}
										}
									}, EaseLinear.getInstance()));
						}
					}));
		}

		// Move Items when click cake cooked
		private void movePoolItems(final AnimatedSprite animateds,
				final Sprite mSpriteRackItems, final int pToX, final int pToY,
				final int pNewToX, final int pNewToY) {
			animateds.clearEntityModifiers();
			animateds.registerEntityModifier(new MoveModifier(0.2f, animateds
					.getX(), pToX, animateds.getY(), pToY,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									isTouchCake = true;
									mSpriteRackItems.setVisible(false);
									mSpriteRackItems
											.registerEntityModifier(new DelayModifier(
													0.2f,
													new IEntityModifierListener() {
														@Override
														public void onModifierStarted(
																IModifier<IEntity> arg0,
																IEntity arg1) {
														}

														@Override
														public void onModifierFinished(
																IModifier<IEntity> arg0,
																IEntity arg1) {
															mSpriteRackItems
																	.setVisible(false);
														}
													}));
									// Track One
									if (countClickCooked <= 6) {
										animateds.detachSelf();
										animateds.setPosition(pNewToX, pNewToY);
										mSpriteRack[1].attachChild(animateds);
									}
									if (countClickCooked > 6
											&& countClickCooked <= 12) {
										animateds.detachSelf();
										animateds.setPosition(pNewToX, pNewToY);
										mSpriteRack[3].attachChild(animateds);
									}
									if (countClickCooked > 12
											&& countClickCooked <= 18) {
										animateds.detachSelf();
										animateds.setPosition(pNewToX, pNewToY);
										mSpriteRack[5].attachChild(animateds);
									}
									if (countClickCooked == 6) {
										isTouchCake = false;
										finishRackOne = true;
										moveTrackFinish(mSpriteRack[1],
												mSpriteRack[3], 148, 15, 1);
									}
									// Track Two
									if (countClickCooked == 12) {
										isTouchCake = false;
										finishRackTwo = true;
										moveTrackFinish(mSpriteRack[3],
												mSpriteRack[5], 148, 15, 2);
									}
									// Track Three
									if (countClickCooked == 18) {
										isTouchCake = false;
										checkSwitch = true;
										finishRackThree = true;
										moveTrackFinish(mSpriteRack[5],
												mSpriteRack[3], 148, 10, 3);
										for (int i = 0; i < mSpriteRackItem.length; i++) {
											mSpriteRackItem[i]
													.setVisible(false);
										}
									}
								}
							});
						}
					}, EaseLinear.getInstance()));
		}

		private void setCountAndSound() {
			cakeCooked++;
			A1_5_OBUN.play();
			if (cakeCooked > 8) {
				A1_11_HAKONITUMERU.play();
				scaleRackAndYajirushi();
			}
		}

		private void setCurrentIndex(final AnimatedSprite anis,
				final int current) {
			anis.setCurrentTileIndex(current);
			if (anis.getCurrentTileIndex() == current) {
				setCountAndSound();
				startAnimationObun1();
			}
		}

		private void setCookedCake(final AnimatedSprite anis,
				final Sprite sprites, final int a, final int b, final int c,
				int d) {
			Vol3Anpanmantaiso.this.mScene.unregisterTouchArea(anis);
			listAnimatedSprite.remove(anis);
			movePoolItems(anis, sprites, a, b, c, d);
			cakeCooked -= 1;
			setUnvisibleYajirushi();
			tryRegisterModifier();
		}

		@Override
		protected AnimatedSprite onAllocatePoolItem() {
			final AnimatedSprite aniSprite = new AnimatedSprite(955, 312,
					this.mTiledTextureRegion,
					Vol3Anpanmantaiso.this.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						if (this.getX() > 340) {
							int nexInts = randNil2.nextInt(20);
							while (nexInts % 2 != 0) {
								nexInts = randNil2.nextInt(20);
							}
							this.setCurrentTileIndex(nexInts);
							A1_9_POMI.play();
						}
						if (this.getCurrentTileIndex() == 1
								|| this.getCurrentTileIndex() == 3
								|| this.getCurrentTileIndex() == 5
								|| this.getCurrentTileIndex() == 7
								|| this.getCurrentTileIndex() == 9
								|| this.getCurrentTileIndex() == 11
								|| this.getCurrentTileIndex() == 13
								|| this.getCurrentTileIndex() == 15
								|| this.getCurrentTileIndex() == 17
								|| this.getCurrentTileIndex() == 19) {
							if (isTouchCake && !checkSwitch) {
								A1_11_PAN.play();
								isTouchCake = false;
								Vol3Anpanmantaiso.this.mScene
										.unregisterUpdateHandler(timeCakes);
								getTabObject = (int) this.getX();
								countClickCooked++;
								switch (countClickCooked) {
								// Khay thu 1
								case 1:
									setCookedCake(this, mSpriteRackItem[0],
											pointArrayCakeCooked[0][0],
											pointArrayCakeCooked[0][1],
											pointArrayNewCakeCookedOne[0][0],
											pointArrayNewCakeCookedOne[0][1]);
									break;
								case 2:
									setCookedCake(this, mSpriteRackItem[1],
											pointArrayCakeCooked[1][0],
											pointArrayCakeCooked[1][1],
											pointArrayNewCakeCookedOne[1][0],
											pointArrayNewCakeCookedOne[1][1]);
									break;
								case 3:
									setCookedCake(this, mSpriteRackItem[2],
											pointArrayCakeCooked[2][0],
											pointArrayCakeCooked[2][1],
											pointArrayNewCakeCookedOne[2][0],
											pointArrayNewCakeCookedOne[2][1]);
									break;
								case 4:
									setCookedCake(this, mSpriteRackItem[3],
											pointArrayCakeCooked[3][0],
											pointArrayCakeCooked[3][1],
											pointArrayNewCakeCookedOne[3][0],
											pointArrayNewCakeCookedOne[3][1]);
									break;
								case 5:
									setCookedCake(this, mSpriteRackItem[4],
											pointArrayCakeCooked[4][0],
											pointArrayCakeCooked[4][1],
											pointArrayNewCakeCookedOne[4][0],
											pointArrayNewCakeCookedOne[4][1]);
									break;
								case 6:
									setCookedCake(this, mSpriteRackItem[5],
											pointArrayCakeCooked[5][0],
											pointArrayCakeCooked[5][1],
											pointArrayNewCakeCookedOne[5][0],
											pointArrayNewCakeCookedOne[5][1]);
									break;
								// Khay thu 2
								case 7:
									setCookedCake(this, mSpriteRackItem[0],
											pointArrayCakeCooked[0][0],
											pointArrayCakeCooked[0][1],
											pointArrayNewCakeCookedTwo[0][0],
											pointArrayNewCakeCookedTwo[0][1]);
									break;
								case 8:
									setCookedCake(this, mSpriteRackItem[1],
											pointArrayCakeCooked[1][0],
											pointArrayCakeCooked[1][1],
											pointArrayNewCakeCookedTwo[1][0],
											pointArrayNewCakeCookedTwo[1][1]);
									break;
								case 9:
									setCookedCake(this, mSpriteRackItem[2],
											pointArrayCakeCooked[2][0],
											pointArrayCakeCooked[2][1],
											pointArrayNewCakeCookedTwo[2][0],
											pointArrayNewCakeCookedTwo[2][1]);
									break;
								case 10:
									setCookedCake(this, mSpriteRackItem[3],
											pointArrayCakeCooked[3][0],
											pointArrayCakeCooked[3][1],
											pointArrayNewCakeCookedTwo[3][0],
											pointArrayNewCakeCookedTwo[3][1]);
									break;
								case 11:
									setCookedCake(this, mSpriteRackItem[4],
											pointArrayCakeCooked[4][0],
											pointArrayCakeCooked[4][1],
											pointArrayNewCakeCookedTwo[4][0],
											pointArrayNewCakeCookedTwo[4][1]);
									break;
								case 12:
									setCookedCake(this, mSpriteRackItem[5],
											pointArrayCakeCooked[5][0],
											pointArrayCakeCooked[5][1],
											pointArrayNewCakeCookedTwo[5][0],
											pointArrayNewCakeCookedTwo[5][1]);
									break;
								// Khay thu 3
								case 13:
									setCookedCake(this, mSpriteRackItem[0],
											pointArrayCakeCooked[0][0],
											pointArrayCakeCooked[0][1],
											pointArrayNewCakeCookedThree[0][0],
											pointArrayNewCakeCookedThree[0][1]);
									break;
								case 14:
									setCookedCake(this, mSpriteRackItem[1],
											pointArrayCakeCooked[1][0],
											pointArrayCakeCooked[1][1],
											pointArrayNewCakeCookedThree[1][0],
											pointArrayNewCakeCookedThree[1][1]);
									break;
								case 15:
									setCookedCake(this, mSpriteRackItem[2],
											pointArrayCakeCooked[2][0],
											pointArrayCakeCooked[2][1],
											pointArrayNewCakeCookedThree[2][0],
											pointArrayNewCakeCookedThree[2][1]);
									break;
								case 16:
									setCookedCake(this, mSpriteRackItem[3],
											pointArrayCakeCooked[3][0],
											pointArrayCakeCooked[3][1],
											pointArrayNewCakeCookedThree[3][0],
											pointArrayNewCakeCookedThree[3][1]);
									break;
								case 17:
									setCookedCake(this, mSpriteRackItem[4],
											pointArrayCakeCooked[4][0],
											pointArrayCakeCooked[4][1],
											pointArrayNewCakeCookedThree[4][0],
											pointArrayNewCakeCookedThree[4][1]);
									break;
								case 18:
									setCookedCake(this, mSpriteRackItem[5],
											pointArrayCakeCooked[5][0],
											pointArrayCakeCooked[5][1],
											pointArrayNewCakeCookedThree[5][0],
											pointArrayNewCakeCookedThree[5][1]);
									break;
								}
							}
							if (countClickCooked == 18) {
								isTouchCake = false;
								mAnimatedSpriteSwich.setCurrentTileIndex(1);
								// Vol3Anpanmantaiso.this.mScene.unregisterUpdateHandler(timeCakes);
							}
						}
						return true;
					}
					return super.onAreaTouched(pSceneTouchEvent,
							pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			// Modifier
			aniSprite.registerEntityModifier(new MoveModifier((aniSprite
					.getmXFirst() - spaceAnimatedSpriteCake)
					* durationCake
					/ aniSprite.getmXFirst(), aniSprite.getmXFirst(),
					spaceAnimatedSpriteCake, aniSprite.getY(),
					aniSprite.getY(), EaseLinear.getInstance()));
			// Update Handler for AnimatedSprite
			aniSprite.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float arg0) {
					// Process Boy make Cake
					if (((int) aniSprite.getX() > 470 && (int) aniSprite.getX() < 475)) {
						if (!isTouchBodyFace) {
							if (mAnimatedSpriteBoyHandMain.isAnimationRunning()) {
								mAnimatedSpriteBoyHandMain.stopAnimation();
							}
							startAnimationBoy(mAnimatedSpriteBoyHandMain);
						}
					}
					// Process Cake is Cooked
					if (aniSprite.collidesWith(mSpriteAttach)) {
						if (cakeCooked > 8) {
							if (!limitCake) {
								Log.d(TAG, "Limit cake");
								Vol3Anpanmantaiso.this.mScene
										.unregisterUpdateHandler(timeCakes);
								for (int i = 0; i < listAnimatedSprite.size(); i++) {
									listAnimatedSprite.get(i)
											.clearEntityModifiers();
								}
								limitCake = true;
							}
							if (!isTouchSwitch) {
								if (mAnimatedSpriteSwich.getCurrentTileIndex() != 1) {
									mAnimatedSpriteSwich.setCurrentTileIndex(1);
								}
							}
						} else {
							if (!isTouchSwitch) {
								if (mAnimatedSpriteSwich.getCurrentTileIndex() != 0) {
									mAnimatedSpriteSwich.setCurrentTileIndex(0);
								}
							}
						}
						switch (aniSprite.getCurrentTileIndex()) {
						case 0:
							setCurrentIndex(aniSprite, 1);
							break;
						case 2:
							setCurrentIndex(aniSprite, 3);
							break;
						case 4:
							setCurrentIndex(aniSprite, 5);
							break;
						case 6:
							setCurrentIndex(aniSprite, 7);
							break;
						case 8:
							setCurrentIndex(aniSprite, 9);
							break;
						case 10:
							setCurrentIndex(aniSprite, 11);
							break;
						case 12:
							setCurrentIndex(aniSprite, 13);
							break;
						case 14:
							setCurrentIndex(aniSprite, 15);
							break;
						case 16:
							setCurrentIndex(aniSprite, 17);
							break;
						case 18:
							setCurrentIndex(aniSprite, 19);
							break;
						}
					}
				}
			});
			spaceAnimatedSpriteCake += 15;
			Log.d("TEST SPACE REAL", "REAL" + spaceAnimatedSpriteCake);
			Vol3Anpanmantaiso.this.mScene.registerTouchArea(aniSprite);
			return aniSprite;
		}

		@Override
		protected void onHandleRecycleItem(final AnimatedSprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.detachSelf();
		}

		@Override
		protected void onHandleObtainItem(final AnimatedSprite pItem) {
			pItem.reset();
		}
	}

	// Try Register Modifier for all Cake
	public void tryRegisterModifier() {
		if (!isTouchSwitch) {
			resetDurationSpaceCake();
			int count = 0;
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				if ((int) listAnimatedSprite.get(i).getX() < getTabObject) {
					count++;
				}
			}
			spaceAnimatedSpriteCakeReset = 15 * count;
			for (int i = count; i < listAnimatedSprite.size(); i++) {
				listAnimatedSprite.get(i).clearEntityModifiers();
				listAnimatedSprite.get(i)
						.registerEntityModifier(
								new MoveModifier((listAnimatedSprite.get(i)
										.getX() - spaceAnimatedSpriteCakeReset)
										* durationCakeReset
										/ listAnimatedSprite.get(i)
												.getmXFirst(),
										listAnimatedSprite.get(i).getX(),
										spaceAnimatedSpriteCakeReset,
										listAnimatedSprite.get(i).getY(),
										listAnimatedSprite.get(i).getY(),
										EaseLinear.getInstance()));
				spaceAnimatedSpriteCakeReset += 15;
			}
			spaceAnimatedSpriteCake = spaceAnimatedSpriteCakeReset;
			Vol3Anpanmantaiso.this.mScene.registerUpdateHandler(timeCakes);
		}
	}

	// Start Animation Obun when Cake is Cooked
	public void startAnimationObun1() {
		int pFrames1[] = { 0, 1, 0 };
		long pFrameDurations[] = { 200, 200, 100 };
		mAnimatedSpriteObun1.animate(pFrameDurations, pFrames1, 0);
		int pFrames2[] = { 0, 1 };
		long pFrameDurations1[] = { 200, 200 };
		mAnimatedSpriteObun2.setVisible(true);
		mAnimatedSpriteObun2.animate(pFrameDurations1, pFrames2, 0,
				new IAnimationListener() {
					@Override
					public void onAnimationStarted(
							AnimatedSprite paramAnimatedSprite, int paramInt) {
					}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {
					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite paramAnimatedSprite, int paramInt1,
							int paramInt2) {
					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						mAnimatedSpriteObun2.setCurrentTileIndex(2);
						mAnimatedSpriteObun2.setVisible(false);
					}
				});
	}

	// Start Animation Kiji
	public void startAnimationKiji() {
		int pFrames[] = { 0, 1, 2 };
		long pFrameDurations[] = { 350, 350, 350 };
		mAnimatedSpriteKiji.animate(pFrameDurations, pFrames, -1);
	}

	// Start Animation Pet
	public void startAndMoveThreeAnimated(final AnimatedSprite ani) {
		int pFrames[] = { 0, 1 };
		long pFrameDurations[] = { 200, 200 };
		ani.animate(pFrameDurations, pFrames, -1);
		ani.clearEntityModifiers();
		ani.registerEntityModifier(new MoveModifier(1.5f, ani.getX(), 290, ani
				.getY(), ani.getY(), new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				if (ani.isAnimationRunning()) {
					ani.stopAnimation();
				}
				mAnimatedSpriteAcodion.setCurrentTileIndex(0);
				touchAcodion = false;
				ani.setPosition(626, 6);
				if (iTouchAcodion == 3) {
					mSpriteCar.setPosition(446, 6);
				}
			}
		}, EaseLinear.getInstance()));
	}

	// Start Animation Car
	public void startMoveCar(final Sprite sprite) {
		sprite.clearEntityModifiers();
		sprite.registerEntityModifier(new MoveModifier(1.5f, sprite.getX(),
				290, sprite.getY(), sprite.getY(),
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mAnimatedSpriteAcodion.setCurrentTileIndex(0);
						touchAcodion = false;
						sprite.setPosition(626, 6);
					}
				}, EaseLinear.getInstance()));
	}

	// Start Animation Girl
	public void startAnimationGirl(final AnimatedSprite ani) {
		int pFrames[] = { 0, 1 };
		long pFrameDurations[] = { 350, 350 };
		ani.animate(pFrameDurations, pFrames, -1);
	}

	// Start Animation Boy
	public void startAnimationBoy(final AnimatedSprite ani) {
		int pFrames[] = { 1, 0, 1, 0 };
		long pFrameDurations[] = { 150, 100, 150, 200 };
		ani.animate(pFrameDurations, pFrames, 0);
	}

	// Clear all linked Sprite and temp
	private void clearObjectCake() {
		if (listAnimatedSprite.size() > 0) {
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				listAnimatedSprite.get(i).clearEntityModifiers();
				listAnimatedSprite.get(i).clearUpdateHandlers();
			}
		}
		if (mapLinkTemp.size() > 0) {
			for (int i = 0; i < mapLinkTemp.size(); i++) {
				mapLinkTemp.get(i).clearEntityModifiers();
				mapLinkTemp.get(i).clearUpdateHandlers();
				spriteCake.onHandleRecycleItem(mapLinkTemp.get(i));
				Log.d(TAG, "Clear cake" + i);
			}
		}
	}

	private void setAnimatedSpriteWhenWin(final AnimatedSprite anis) {
		anis.setVisible(false);
		if (anis.isAnimationRunning()) {
			anis.stopAnimation();
		}
		anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
		anis.setCurrentTileIndex(0);
		anis.clearEntityModifiers();
		anis.clearUpdateHandlers();
	}

	private void setSpriteWhenWin(final Sprite anis) {
		anis.setVisible(false);
		anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
		anis.clearEntityModifiers();
		anis.clearUpdateHandlers();
	}

	private void setVisibleAnimatedSpriteBegin(final AnimatedSprite anis) {
		if (anis != null) {
			anis.setVisible(true);
			if (anis.isAnimationRunning()) {
				anis.stopAnimation();
			}
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
		}
	}

	private void setVisibleSpriteBegin(final Sprite anis) {
		if (anis != null) {
			anis.setVisible(true);
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
		}
	}

	private void setUnVisibleAnimatedSpriteBegin(final AnimatedSprite anis) {
		if (anis != null) {
			anis.setVisible(false);
			if (anis.isAnimationRunning()) {
				anis.stopAnimation();
			}
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
		}
	}

	private void setUnVisibleSpriteBegin(final Sprite anis) {
		if (anis != null) {
			anis.setVisible(false);
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
		}
	}

	private void setVisibleForSprite(final Sprite anis) {
		anis.setVisible(true);
	}

	private void setUnVisibleForSprite(final Sprite anis) {
		anis.setVisible(false);
	}

	private void setVisibleForAnimatedSprite(final AnimatedSprite anis) {
		anis.setVisible(true);
	}

	private void setUnVisibleForAnimatedSprite(final AnimatedSprite anis) {
		anis.setVisible(false);
	}

	// Finish when cake is full
	private void finishWhenCakeCookedFull() {
		// Khong cho touch vao rem
		touchAcodion = true;
		isTouchGimic = true;
		// Khong cho touch vao Body Boy
		isTouchBodyFace = true;
		// Khong cho touch vao bot my
		isTouchKona = true;
		// Khong cho touch vao Girl
		isTouchGirlFace = true;
		// Khong cho touch vao Can dieu khien
		checkSwitch = true;
		setTouchGimmic3(true);
		clearObjectCake();
		if (spriteCake != null) {
			spriteCake.setCountClickCooked(0);
			spriteCake.setTouchCake(false);
		}
		// Toray
		for (int i = 0; i < mSpriteToray.length; i++) {
			setVisibleForSprite(mSpriteToray[i]);
		}
		setVisibleForSprite(mSpriteHaike3);
		// Rack Background
		for (int i = 0; i < mSpriteRackBackground.length; i++) {
			setSpriteWhenWin(mSpriteRackBackground[i]);
		}
		// Rack
		for (int i = 0; i < mSpriteRack.length; i++) {
			setSpriteWhenWin(mSpriteRack[i]);
		}
		// Rack Items
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			setSpriteWhenWin(mSpriteRackItem[i]);
		}
		// Boy
		setAnimatedSpriteWhenWin(mAnimatedSpriteBoyBody);
		setAnimatedSpriteWhenWin(mAnimatedSpriteBoyFace);
		setAnimatedSpriteWhenWin(mAnimatedSpriteBoyHand);
		setAnimatedSpriteWhenWin(mAnimatedSpriteBoyHandMain);
		// Girl
		setAnimatedSpriteWhenWin(mAnimatedSpriteGirlBody1);
		setAnimatedSpriteWhenWin(mAnimatedSpriteGirlFace);
		setAnimatedSpriteWhenWin(mAnimatedSpriteGirlHand);
		setAnimatedSpriteWhenWin(mAnimatedSpriteGirlBody2);
		setAnimatedSpriteWhenWin(mAnimatedSpriteGirlHandMain);
		// Acodion
		setAnimatedSpriteWhenWin(mAnimatedSpriteAcodion);
		// Switch
		setAnimatedSpriteWhenWin(mAnimatedSpriteSwich);
		// Obun
		setAnimatedSpriteWhenWin(mAnimatedSpriteObun1);
		setAnimatedSpriteWhenWin(mAnimatedSpriteObun2);
		// KiJi
		setAnimatedSpriteWhenWin(mAnimatedSpriteKiji);
		// Conbea
		setSpriteWhenWin(mSpriteConbea);
		// Table
		setSpriteWhenWin(mSpriteTable);
		// Kona
		setAnimatedSpriteWhenWin(mAnimatedSpriteKona);
		// Panda
		setAnimatedSpriteWhenWin(mAnimatedSpritePanda);
		// Lion
		setAnimatedSpriteWhenWin(mAnimatedSpriteLion);
		// Usagi
		setAnimatedSpriteWhenWin(mAnimatedSpriteUsagi);
		// Car
		setSpriteWhenWin(mSpriteCar);
		setVisibleForSprite(mSpriteCarService1);
		mSpriteSmoke.registerEntityModifier(new LoopEntityModifier(
				new ScaleModifier(0.75f, 1f, 1.25f, EaseLinear.getInstance()),
				-1));

		mSpriteCarService1.registerEntityModifier(new MoveModifier(2.5f,
				-mSpriteCarService1.getWidth(), 350, mSpriteCarService1
						.getmYFirst(), mSpriteCarService1.getmYFirst(),
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
						// Translate Door of CarService
						final Path pPath = new Path(3)
								.to(mSpriteCarService2.getmXFirst(),
										mSpriteCarService2.getmYFirst())
								.to(mSpriteCarService2.getmXFirst(),
										mSpriteCarService2.getmYFirst() - 2)
								.to(mSpriteCarService2.getmXFirst(),
										mSpriteCarService2.getmYFirst() + 2);
						mSpriteCarService2
								.registerEntityModifier(new LoopEntityModifier(
										new PathModifier(0.5f, pPath,
												EaseLinear.getInstance()), -1));
						if (listAnimatedSprite != null) {
							listAnimatedSprite.clear();
						}
						if (mapLinkTemp != null) {
							mapLinkTemp.clear();
						}
						// Bat dau cho touch Vao thang Xe tai cho banh
						Log.d(TAG, "Bat dau cho touch vao thung xe");
						isTouchCarService2 = true;
					}
				}, EaseLinear.getInstance()));
	}

	private void moveToray(final Sprite sprites) {
		final Path pPath = new Path(3)
				.to(sprites.getmXFirst(), sprites.getmYFirst())
				.to(sprites.getmXFirst() - 2, sprites.getmYFirst())
				.to(sprites.getmXFirst() + 2, sprites.getmYFirst());
		sprites.registerEntityModifier(new LoopEntityModifier(new PathModifier(
				0.5f, pPath, EaseLinear.getInstance()), -1));
	}

	// TODO play Again
	private void playAgain() {
		resetVarialble();
		setUnVisibleForSprite(mSpriteHaike3);
		// Rack Background
		for (int i = 0; i < mSpriteRackBackground.length; i++) {
			setVisibleForSprite(mSpriteRackBackground[i]);
		}
		// Rack
		for (int i = 0; i < mSpriteRack.length; i++) {
			setVisibleForSprite(mSpriteRack[i]);
		}
		// Rack Items
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			setVisibleForSprite(mSpriteRackItem[i]);
		}
		// Boy
		setVisibleForAnimatedSprite(mAnimatedSpriteBoyBody);
		setVisibleForAnimatedSprite(mAnimatedSpriteBoyFace);
		setVisibleForAnimatedSprite(mAnimatedSpriteBoyHandMain);
		setUnVisibleForAnimatedSprite(mAnimatedSpriteBoyHand);
		// Girl
		setVisibleForAnimatedSprite(mAnimatedSpriteGirlBody1);
		setUnVisibleForAnimatedSprite(mAnimatedSpriteGirlBody2);
		setVisibleForAnimatedSprite(mAnimatedSpriteGirlFace);
		setVisibleForAnimatedSprite(mAnimatedSpriteGirlHandMain);
		setUnVisibleForAnimatedSprite(mAnimatedSpriteGirlHand);
		// Acodion
		setVisibleForAnimatedSprite(mAnimatedSpriteAcodion);
		// Switch
		setVisibleForAnimatedSprite(mAnimatedSpriteSwich);
		// Obun
		setVisibleForAnimatedSprite(mAnimatedSpriteObun1);
		// KiJi
		setVisibleForAnimatedSprite(mAnimatedSpriteKiji);
		// Conbea
		setVisibleForSprite(mSpriteConbea);
		// Table
		setVisibleForSprite(mSpriteTable);
		// Kona
		setVisibleForSprite(mAnimatedSpriteKona);
		// Panda
		setVisibleForAnimatedSprite(mAnimatedSpritePanda);
		// Lion
		setVisibleForAnimatedSprite(mAnimatedSpriteLion);
		// Usagi
		setVisibleForAnimatedSprite(mAnimatedSpriteUsagi);
		// Car
		setVisibleForSprite(mSpriteCar);
		setVisibleForSprite(mSpriteCarService1);
		startAnimationKiji();
		startAnimationGirl(mAnimatedSpriteGirlHandMain);
		setTouchGimmic3(true);
		Vol3Anpanmantaiso.this.mScene.registerTouchArea(mAnimatedSpriteSwich);
		Vol3Anpanmantaiso.this.mScene
				.registerTouchArea(mAnimatedSpriteGirlFace);
		Vol3Anpanmantaiso.this.mScene.registerTouchArea(mAnimatedSpriteBoyFace);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteSwich);
		for (int i = 0; i < mSpriteToray.length; i++) {
			Vol3Anpanmantaiso.this.mScene.registerTouchArea(mSpriteToray[i]);
		}
		if (spriteCake != null) {
			spriteCake.setCountClickCooked(0);
			spriteCake.setTouchCake(true);
		}
		zIndex = Vol3Anpanmantaiso.this.mScene.getChild(
				mScene.getLastChild().getTag()).getZIndex();
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			mSpriteRackItem[i].setVisible(true);
		}
		mSpriteRack[1].setVisible(true);
		Vol3Anpanmantaiso.this.mScene.registerUpdateHandler(timeCakes);
	}

	public void sortZindexScreenTrackOne() {
		for (int i = 0; i < mSpriteToray.length; i++) {
			mSpriteToray[i].setZIndex(zIndex++);
		}
		mSpriteCarService2.setZIndex(zIndex++);
		mSpriteCarService3.setZIndex(zIndex++);
		mSpriteCarService1.setZIndex(zIndex++);
		mSpriteSmoke.setZIndex(zIndex++);
		mSpriteRack[2].setZIndex(zIndex++);
		mSpriteRack[1].setZIndex(zIndex++);
		mSpriteRack[0].setZIndex(zIndex++);
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			mSpriteRackItem[i].setZIndex(zIndex++);
		}
		if (listAnimatedSprite.size() > 0) {
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				listAnimatedSprite.get(i).setZIndex(zIndex++);
			}
		}
		mAnimatedSpriteBoyBody.setZIndex(zIndex++);
		aniEate.setZIndex(zIndex++);
		mAnimatedSpriteObun1.setZIndex(zIndex++);
		mAnimatedSpriteObun2.setZIndex(zIndex++);
		Vol3Anpanmantaiso.this.mScene.sortChildren();
		setGimmicBringToFront();
	}

	public void sortZindexScreenTrackTwo() {
		mSpriteRack[4].setZIndex(zIndex++);
		mSpriteRack[3].setZIndex(zIndex++);
		mSpriteRack[2].setZIndex(zIndex++);
		mSpriteRack[1].setZIndex(zIndex++);
		mSpriteRack[0].setZIndex(zIndex++);
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			mSpriteRackItem[i].setZIndex(zIndex++);
		}
		if (listAnimatedSprite.size() > 0) {
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				listAnimatedSprite.get(i).setZIndex(zIndex++);
			}
		}
		mAnimatedSpriteBoyBody.setZIndex(zIndex++);
		aniEate.setZIndex(zIndex++);
		mAnimatedSpriteObun1.setZIndex(zIndex++);
		mAnimatedSpriteObun2.setZIndex(zIndex++);
		Vol3Anpanmantaiso.this.mScene.sortChildren();
		setGimmicBringToFront();
	}

	public void sortZindexScreenTrackThree() {
		mSpriteRack[6].setZIndex(zIndex++);
		mSpriteRack[5].setZIndex(zIndex++);
		mSpriteRack[4].setZIndex(zIndex++);
		mSpriteRack[3].setZIndex(zIndex++);
		mSpriteRack[2].setZIndex(zIndex++);
		mSpriteRack[1].setZIndex(zIndex++);
		mSpriteRack[0].setZIndex(zIndex++);
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			mSpriteRackItem[i].setZIndex(zIndex++);
		}
		if (listAnimatedSprite.size() > 0) {
			for (int i = 0; i < listAnimatedSprite.size(); i++) {
				listAnimatedSprite.get(i).setZIndex(zIndex++);
			}
		}
		mAnimatedSpriteBoyBody.setZIndex(zIndex++);
		aniEate.setZIndex(zIndex++);
		mAnimatedSpriteObun1.setZIndex(zIndex++);
		mAnimatedSpriteObun2.setZIndex(zIndex++);
		Vol3Anpanmantaiso.this.mScene.sortChildren();
		setGimmicBringToFront();
	}

	public void sortIndexTrack(final Sprite track) {
		zIndex = Vol3Anpanmantaiso.this.mScene.getChild(
				mScene.getLastChild().getTag()).getZIndex();
		aniEate.setZIndex(zIndex++);
		track.setZIndex(zIndex++);
		mAnimatedSpriteObun1.setZIndex(zIndex++);
		mAnimatedSpriteObun2.setZIndex(zIndex++);
		Vol3Anpanmantaiso.this.mScene.sortChildren();
		setGimmicBringToFront();
	}

	public void sortIndexBoyEatCake() {
		zIndex = Vol3Anpanmantaiso.this.mScene.getChild(
				mScene.getLastChild().getTag()).getZIndex();
		// mAnimatedSpriteBoyBody.setZIndex(zIndex++);
		aniEate.setZIndex(zIndex++);
		mAnimatedSpriteObun1.setZIndex(zIndex++);
		mAnimatedSpriteObun2.setZIndex(zIndex++);
		Vol3Anpanmantaiso.this.mScene.sortChildren();
		setGimmicBringToFront();
	}

	// Run when start
	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		startAnimationKiji();
		startAnimationGirl(mAnimatedSpriteGirlHandMain);
		if (listAnimatedSprite != null) {
			listAnimatedSprite.clear();
		}
		if (mapLinkTemp != null) {
			mapLinkTemp.clear();
		}
		zIndex = Vol3Anpanmantaiso.this.mScene.getChild(
				mScene.getLastChild().getTag()).getZIndex();
		spriteCake = new AnimatedSpriteCake(iTiledTextureRegionCook);
		timeCakes = new TimerHandler(1.5f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				A1_4_2_CONBEA.play();
				final AnimatedSprite aniSprite = spriteCake.obtainPoolItem();
				aniSprite.setCurrentTileIndex(0);
				Vol3Anpanmantaiso.this.mScene.attachChild(aniSprite);
				mapLinkTemp.add(aniSprite);
				listAnimatedSprite.add(aniSprite);
				for (int i = 0; i < mSpriteRackItem.length; i++) {
					mSpriteRackItem[i].setZIndex(zIndex++);
				}
				aniSprite.setZIndex(zIndex++);
				if (!boyEatCake) {
					mAnimatedSpriteBoyBody.setZIndex(zIndex++);
					// aniEate.setZIndex(zIndex++);
				}
				mAnimatedSpriteObun1.setZIndex(zIndex++);
				mAnimatedSpriteObun2.setZIndex(zIndex++);
				mSpriteYajirushi.setZIndex(zIndex++);
				Vol3Anpanmantaiso.this.mScene.sortChildren();
				setGimmicBringToFront();
			}
		});
		Vol3Anpanmantaiso.this.mScene.registerUpdateHandler(timeCakes);

		timeSmoke = new TimerHandler(1.5f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (mSpriteHaike3.isVisible()
						&& (isTouchCarService2 || isMoveTrackToCar
								|| countTouchRay == 1 || countTouchRay == 2 || countTouchRay == 3)) {
					A1_16_TORAC_STOP.play();
				}
				if (isTouchSwitch) {
					mAnimatedSpriteSwich
							.registerEntityModifier(new DelayModifier(5f,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											// TODO Auto-generated method stub
											mAnimatedSpriteSwich
													.registerEntityModifier(new SequenceEntityModifier(
															new ScaleModifier(
																	0.1f, 1f,
																	1.1f),
															new ScaleModifier(
																	0.1f, 1.1f,
																	1f)));
										}
									}));
				}
			}
		});
		mSpriteSmoke.registerUpdateHandler(timeSmoke);

		// TODO
		Vol3Anpanmantaiso.this.mScene
				.registerUpdateHandler(new IUpdateHandler() {
					@Override
					public void reset() {
					}

					@Override
					public void onUpdate(float arg0) {
						if (fullCake) {
							// setTouchGimmic3(false);
							Vol3Anpanmantaiso.this.mScene
									.unregisterTouchArea(mAnimatedSpriteSwich);
							Vol3Anpanmantaiso.this.mScene
									.unregisterTouchArea(mAnimatedSpriteBoyFace);
							Vol3Anpanmantaiso.this.mScene
									.unregisterUpdateHandler(timeCakes);
							mAnimatedSpriteBoyBody.clearEntityModifiers();
							mAnimatedSpriteBoyBody.clearUpdateHandlers();
							if (listAnimatedSprite.size() > 0) {
								for (int i = 0; i < listAnimatedSprite.size(); i++) {
									Vol3Anpanmantaiso.this.mScene
											.unregisterTouchArea(listAnimatedSprite
													.get(i));
								}
							}
							if (spriteCake != null) {
								spriteCake.setCountClickCooked(0);
								spriteCake.setTouchCake(false);
							}
							if (isTouchSwitch) {
								aniEate.clearEntityModifiers();
							}
						}
						if (isTouchSwitch) {
							Vol3Anpanmantaiso.this.mScene
									.unregisterUpdateHandler(timeCakes);
							for (int i = 0; i < listAnimatedSprite.size(); i++) {
								listAnimatedSprite.get(i)
										.clearEntityModifiers();
							}
						}
						if (cakeCooked > 8 && !fullCake) {
							setTouchGimmic3(false);
						}
						if (cakeCooked < 8 && !fullCake) {
							setTouchGimmic3(true);
						}
					}
				});
	}

	private void resetDurationSpaceCake() {
		spaceAnimatedSpriteCake = 0;
		spaceAnimatedSpriteSwitchCake = 0;
		spaceAnimatedSpriteCakeReset = 0;
		durationCake = 20f;
		durationCakeSwitchCake = 20f;
		durationCakeReset = 20f;
	}

	private void resetVarialble() {
		touchAcodion = false;
		iTouchAcodion = 0;
		resetDurationSpaceCake();
		countTouchSwitch = 0;
		cakeCooked = 0;
		getTabObject = 0;
		isTouchBodyFace = false;
		isTouchGirlFace = false;
		isTouchKona = false;
		countKona = 0;
		boyEatCake = false;
		isTouchCarService2 = false;
		isMoveTrackToCar = false;
		countTouchRay = 0;
		isTouchGimic = false;
		isTouchSwitch = false;
		checkSwitch = false;
		limitCake = false;
		finishRackOne = false;
		finishRackTwo = false;
		finishRackThree = false;
		moveToCar = 0;
		fullCake = false;
	}

	// return default first value
	@Override
	public void onPauseGame() {
		if (fullCake || spriteCake.getAvailableItemCount() == 18) {
			Vol3Anpanmantaiso.this.mScene.unregisterUpdateHandler(timeCakes);
		}
		resetVarialble();
		setTouchGimmic3(true);
		Vol3Anpanmantaiso.this.mScene.registerTouchArea(mAnimatedSpriteSwich);
		Vol3Anpanmantaiso.this.mScene
				.registerTouchArea(mAnimatedSpriteGirlFace);
		Vol3Anpanmantaiso.this.mScene.registerTouchArea(mAnimatedSpriteBoyFace);
		for (int i = 0; i < mSpriteToray.length; i++) {
			this.mScene.registerTouchArea(mSpriteToray[i]);
		}
		if (timeCakes != null) {
			Vol3Anpanmantaiso.this.mScene.unregisterUpdateHandler(timeCakes);
			timeCakes = null;
		}
		zIndex = 0;
		clearObjectCake();
		if (spriteCake != null) {
			spriteCake.setCountClickCooked(0);
			spriteCake.setTouchCake(true);
			spriteCake = null;
		}
		if (timeSmoke != null) {
			mSpriteSmoke.unregisterUpdateHandler(timeSmoke);
			timeSmoke = null;
		}
		if (timeYajirushi != null) {
			mSpriteYajirushi.unregisterUpdateHandler(timeYajirushi);
			timeYajirushi = null;
		}
		setVisibleSpriteBegin(mSpriteHaike2);
		setVisibleAnimatedSpriteBegin(mAnimatedSpritePanda);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteLion);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteUsagi);
		setVisibleSpriteBegin(mSpriteCar);
		setVisibleSpriteBegin(mSpriteHaike1);
		setUnVisibleSpriteBegin(mSpriteHaike3);
		for (int i = 0; i < mSpriteToray.length; i++) {
			setUnVisibleSpriteBegin(mSpriteToray[i]);
		}
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteAcodion);
		setVisibleSpriteBegin(mSpriteConbea);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteBoyFace);
		setUnVisibleAnimatedSpriteBegin(mAnimatedSpriteBoyHand);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteBoyHandMain);
		mAnimatedSpriteBoyHandMain.setAlpha(1.0f);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteBoyBody);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlBody1);
		setVisibleSpriteBegin(mSpriteTable);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteKiji);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteKona);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlHandMain);
		setUnVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlBody2);
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlFace);
		setUnVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlHand);
		setUnVisibleAnimatedSpriteBegin(mAnimatedSpriteGirlBody2);
		for (int i = 0; i < mSpriteRackBackground.length; i++) {
			setVisibleSpriteBegin(mSpriteRackBackground[i]);
		}
		for (int i = 0; i < mSpriteRack.length; i++) {
			setVisibleSpriteBegin(mSpriteRack[i]);
		}
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteObun1);
		setUnVisibleAnimatedSpriteBegin(mAnimatedSpriteObun2);
		for (int i = 0; i < mSpriteRackItem.length; i++) {
			setVisibleSpriteBegin(mSpriteRackItem[i]);
		}
		mSpriteRack[3].setVisible(false);
		mSpriteRack[5].setVisible(false);
		if (aniEate != null) {
			aniEate.clearEntityModifiers();
			aniEate.clearUpdateHandlers();
		}
		setVisibleAnimatedSpriteBegin(mAnimatedSpriteSwich);
		setUnVisibleSpriteBegin(mSpriteCarService1);
		setUnVisibleSpriteBegin(mSpriteCarService3);
		if (this.mScene != null) {
			this.mScene.clearEntityModifiers();
			this.mScene.clearUpdateHandlers();
		}
		super.onPauseGame();
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public void combineGimmic3WithAction() {
		// setTouchGimmic3(false);
		if (fullCake) {
			A1_4_1_SWICH.play();
			setTouchGimmic3(false);
			Vol3Anpanmantaiso.this.mScene.clearEntityModifiers();
			Vol3Anpanmantaiso.this.mScene
					.registerEntityModifier(new DelayModifier(1.5f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									setTouchGimmic3(true);
								}
							}));
		}
		if (!isTouchGimic) {
			A1_4_1_SWICH.play();
			if (countTouchSwitch == 0 && !checkSwitch && cakeCooked < 8) {
				mAnimatedSpriteSwich.clearEntityModifiers();
				checkSwitch = true;
				isTouchSwitch = true;
				isTouchGimic = true;
				setTouchGimmic3(false);
				mAnimatedSpriteSwich.setCurrentTileIndex(1);
				countTouchSwitch = 1;
				setCakeWithSwitch(0);
			}
			if (countTouchSwitch == 1 && !checkSwitch && cakeCooked < 8) {
				mAnimatedSpriteSwich.clearEntityModifiers();
				countTouchSwitch = 0;
				checkSwitch = true;
				isTouchSwitch = false;
				isTouchGimic = true;
				setTouchGimmic3(false);
				mAnimatedSpriteSwich.setCurrentTileIndex(0);
				setCakeWithSwitch(1);
			}
		} else {
			sprGimmic[2].clearEntityModifiers();
		}
	}

}
