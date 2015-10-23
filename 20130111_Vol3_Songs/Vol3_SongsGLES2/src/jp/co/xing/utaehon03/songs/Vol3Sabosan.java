package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3SabosanResource;

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
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Sabosan extends BaseGameActivity implements
		IOnSceneTouchListener {
	public static final String gfxPath = "sabosan/gfx/";
	public static final String mfxPath = "sabosan/mfx/";
	public static final String xmlFile1 = "Vol3SabosanPacker1.xml";
	public static final String xmlFile2 = "Vol3SabosanPacker2.xml";
	public static final String xmlFile3 = "Vol3SabosanPacker3.xml";
	public static final String xmlFile4 = "Vol3SabosanPacker4.xml";

	private String LOG = Vol3Sabosan.this.getClass().toString();
	private TexturePack Vol3SabosanPack1, Vol3SabosanPack2, Vol3SabosanPack3,
			Vol3SabosanPack4;
	private TexturePackLoaderFromSource mFromSource;
	private TexturePackTextureRegionLibrary mRegionLibrary1, mRegionLibrary2,
			mRegionLibrary3, mRegionLibrary4;
	private ITextureRegion mRegionA2_14_1, mRegionA2_14_2, mRegionA2_14_3;
	private Sprite mSpriteA2_14_1, mSpriteA2_14_2, mSpriteA2_14_3;
	private ITextureRegion mRegionA2_16_1, mRegionA2_16_2, mRegionA2_16_3,
			mRegionA2_13_1, mRegionA2_13_2;
	private Sprite mSpriteA2_16_1, mSpriteA2_16_2, mSpriteA2_16_3,
			mSpriteA2_13_1, mSpriteA2_13_2;
	private ITextureRegion mRegionA2_15_1_1, mRegionA2_15_1_2,
			mRegionA2_15_2_1, mRegionA2_15_2_2, mRegionA2_15_3_1,
			mRegionA2_15_3_2;
	private Sprite mSpriteA2_15_1_1, mSpriteA2_15_1_2, mSpriteA2_15_2_1,
			mSpriteA2_15_2_2, mSpriteA2_15_3_1, mSpriteA2_15_3_2;
	// A2_17
	private ITextureRegion mRegionA2_17, mRegionA2_11, mRegionA2_12;
	private Sprite mSpriteA2_17, mSpriteA2_11, mSpriteA2_12;
	// A2_2_
	private ITextureRegion mRegionA2_2_1, mRegionA2_2_2, mRegionA2_2_3,
			mRegionA2_4;
	private Sprite mSpriteA2_2_1, mSpriteA2_2_2, mSpriteA2_2_3, mSpriteA2_4;
	// A2_3_
	private ITextureRegion mRegionA2_3_1_Left, mRegionA2_3_2_Left,
			mRegionA2_3_3_Left, mRegionA2_3_4_Left;
	private Sprite mSpriteA2_3_1, mSpriteA2_3_2, mSpriteA2_3_3, mSpriteA2_3_4;
	// A2_5_
	private ITextureRegion mRegionA2_5_1, mRegionA2_5_2, mRegionA2_5_7_1,
			mRegionA2_5_7_2, mRegionA2_5_7_3, mRegionA2_5_7_4, mRegionA2_5_7_5;
	private Sprite mSpriteA2_5_1, mSpriteA2_5_2, mSpriteA2_5_7_1,
			mSpriteA2_5_7_2, mSpriteA2_5_7_3, mSpriteA2_5_7_4, mSpriteA2_5_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_5_;
	private AnimatedSprite mAnimatedSpriteA2_5_;
	// A2_6_
	private ITextureRegion mRegionA2_6_1, mRegionA2_6_2, mRegionA2_6_7_1,
			mRegionA2_6_7_2, mRegionA2_6_7_3, mRegionA2_6_7_4, mRegionA2_6_7_5;
	private Sprite mSpriteA2_6_1, mSpriteA2_6_2, mSpriteA2_6_7_1,
			mSpriteA2_6_7_2, mSpriteA2_6_7_3, mSpriteA2_6_7_4, mSpriteA2_6_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_6_;
	private AnimatedSprite mAnimatedSpriteA2_6_;
	// A2_7_
	private ITextureRegion mRegionA2_7_1, mRegionA2_7_2, mRegionA2_7_7_1,
			mRegionA2_7_7_2, mRegionA2_7_7_3, mRegionA2_7_7_4, mRegionA2_7_7_5;
	private Sprite mSpriteA2_7_1, mSpriteA2_7_2, mSpriteA2_7_7_1,
			mSpriteA2_7_7_2, mSpriteA2_7_7_3, mSpriteA2_7_7_4, mSpriteA2_7_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_7_;
	private AnimatedSprite mAnimatedSpriteA2_7_;
	// A2_8_
	private ITextureRegion mRegionA2_8_1, mRegionA2_8_2, mRegionA2_8_7_1,
			mRegionA2_8_7_2, mRegionA2_8_7_3, mRegionA2_8_7_4, mRegionA2_8_7_5;
	private Sprite mSpriteA2_8_1, mSpriteA2_8_2, mSpriteA2_8_7_1,
			mSpriteA2_8_7_2, mSpriteA2_8_7_3, mSpriteA2_8_7_4, mSpriteA2_8_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_8_;
	private AnimatedSprite mAnimatedSpriteA2_8_;
	// A2_9_
	private ITextureRegion mRegionA2_9_1, mRegionA2_9_2, mRegionA2_9_7_1,
			mRegionA2_9_7_2, mRegionA2_9_7_3, mRegionA2_9_7_4, mRegionA2_9_7_5;
	private Sprite mSpriteA2_9_1, mSpriteA2_9_2, mSpriteA2_9_7_1,
			mSpriteA2_9_7_2, mSpriteA2_9_7_3, mSpriteA2_9_7_4, mSpriteA2_9_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_9_;
	private AnimatedSprite mAnimatedSpriteA2_9_;
	// A2_10_
	private ITextureRegion mRegionA2_10_1, mRegionA2_10_2, mRegionA2_10_7_1,
			mRegionA2_10_7_2, mRegionA2_10_7_3, mRegionA2_10_7_4,
			mRegionA2_10_7_5;
	private Sprite mSpriteA2_10_1, mSpriteA2_10_2, mSpriteA2_10_7_1,
			mSpriteA2_10_7_2, mSpriteA2_10_7_3, mSpriteA2_10_7_4,
			mSpriteA2_10_7_5;
	private ITiledTextureRegion mITiledTextureRegionA2_10_;
	private AnimatedSprite mAnimatedSpriteA2_10_;
	// A2_1
	private ITextureRegion mRegionA2_1_1_Left, mRegionA2_1_2_Left,
			mRegionA2_1_1_Right, mRegionA2_1_2_Right;
	private Sprite mSpriteA2_1_1_Left, mSpriteA2_1_2_Left, mSpriteA2_1_1_Right,
			mSpriteA2_1_2_Right;
	// A2_8_8_,A2_9_8_,A2_10_8
	private ITextureRegion mRegionA2_8_8_1, mRegionA2_8_8_2, mRegionA2_8_8_3,
			mRegionA2_8_8_4, mRegionA2_8_8_5, mRegionA2_9_8_1, mRegionA2_9_8_2,
			mRegionA2_9_8_3, mRegionA2_9_8_4, mRegionA2_9_8_5,
			mRegionA2_10_8_1, mRegionA2_10_8_2, mRegionA2_10_8_3,
			mRegionA2_10_8_4, mRegionA2_10_8_5;
	private Sprite mSpriteA2_8_8_1, mSpriteA2_8_8_2, mSpriteA2_8_8_3,
			mSpriteA2_8_8_4, mSpriteA2_8_8_5, mSpriteA2_9_8_1, mSpriteA2_9_8_2,
			mSpriteA2_9_8_3, mSpriteA2_9_8_4, mSpriteA2_9_8_5,
			mSpriteA2_10_8_1, mSpriteA2_10_8_2, mSpriteA2_10_8_3,
			mSpriteA2_10_8_4, mSpriteA2_10_8_5;
	// A2_8_9,A2_9_9,A2_10_9
	private ITextureRegion mRegionA2_8_9, mRegionA2_9_9, mRegionA2_10_9;
	private Sprite mSpriteA2_8_9, mSpriteA2_9_9, mSpriteA2_10_9;
	// Random
	private Random randomA2_5_, randomA2_15_;
	private RanDomNoRepeat nore;
	// Boolean
	boolean checkOnClick = true, checkSunOnclick = true;
	// Integer count
	private int countOnClickLeft = 0, countOnClickRight = 0, chooseLeft = 0,
			chooseRight = 0, checkChooseRight = 0, checkChooseLeft = 0;
	// Time handler
	TimerHandler timerHandlerA2_14_3, timerHandlerA2_15_, timerHandlerA2_15_1;
	private Sprite mArraySprite[], mArraySpriteFlower[];
	// Sound
	private Sound OGG_A1_10_HANA3, OGG_A1_10_ODORI6, OGG_A1_14_1_KUMOHARE,
			OGG_A1_14_1_WINK, OGG_A1_14_2_TAIYOUTAUCH, OGG_A1_15_RAKUDAGAKU,
			OGG_A1_15__JUMP, OGG_A1_1_1_ZAZA, OGG_A1_3_1_ZA1, OGG_A1_3_2_ZA2,
			OGG_A1_3_3_ZA3, OGG_A1_3_4_ZA4, OGG_A1_4_1_SABOSYUTUGEN,
			OGG_A1_4_2_PYU, OGG_A1_5_ODORI1, OGG_A1_6_ODORI2,
			OGG_A1_7_1TOGENUKE2, OGG_A1_7_ODORI5, OGG_A1_8_HANA1,
			OGG_A1_8_ODORI3, OGG_A1_9_HANA2, OGG_A1_9_ODORI4, OGG_A1_BOY_SABO1,
			OGG_A1_BOY_SABO2, OGG_A1_BOY_SABO3, OGG_A1_BOY_SABO4,
			OGG_A1_BOY_SABO5, OGG_A1_BOY_SABO6, OGG_A1_BOY_SABO7,
			OGG_A1_BOY_SABO8, OGG_A1_BOY_SABO9, OGG_A1_BOY_SABO10,
			OGG_A1_GIRL_SABO1, OGG_A1_GIRL_SABO2, OGG_A1_GIRL_SABO3,
			OGG_A1_GIRL_SABO4, OGG_A1_GIRL_SABO5, OGG_A1_GIRL_SABO6,
			OGG_A1_GIRL_SABO7;
	private Sound sound[];
	private LoopEntityModifier mLoopEntity, moveX;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath(mfxPath);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(gfxPath);
		mFromSource = new TexturePackLoaderFromSource(getTextureManager(),
				pAssetManager, gfxPath);
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		Log.d(LOG, "LOAD KARAOKE RESOURCE");
		Vol3SabosanPack1 = mFromSource.load(xmlFile1);
		Vol3SabosanPack1.loadTexture();
		mRegionLibrary1 = Vol3SabosanPack1.getTexturePackTextureRegionLibrary();

		Vol3SabosanPack2 = mFromSource.load(xmlFile2);
		Vol3SabosanPack2.loadTexture();
		mRegionLibrary2 = Vol3SabosanPack2.getTexturePackTextureRegionLibrary();

		Vol3SabosanPack3 = mFromSource.load(xmlFile3);
		Vol3SabosanPack3.loadTexture();
		mRegionLibrary3 = Vol3SabosanPack3.getTexturePackTextureRegionLibrary();

		Vol3SabosanPack4 = mFromSource.load(xmlFile4);
		Vol3SabosanPack4.loadTexture();
		mRegionLibrary4 = Vol3SabosanPack4.getTexturePackTextureRegionLibrary();
		// a2_17
		mRegionA2_17 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_17_IPHONE_ID);
		// a2_14_
		mRegionA2_14_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_14_1_IPHONE_ID);
		mRegionA2_14_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_14_2_IPHONE_ID);
		mRegionA2_14_3 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_14_3_IPHONE_ID);
		// a2_13
		mRegionA2_13_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_13_1_IPHONE_ID);
		mRegionA2_13_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_13_2_IPHONE_ID);
		// a2_16
		mRegionA2_16_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_16_1_IPHONE_ID);
		mRegionA2_16_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_16_2_IPHONE_ID);
		mRegionA2_16_3 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_16_3_IPHONE_ID);
		// a2_15_
		mRegionA2_15_1_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_1_1_IPHONE_ID);
		mRegionA2_15_1_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_1_2_IPHONE_ID);
		mRegionA2_15_2_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_2_1_IPHONE_ID);
		mRegionA2_15_2_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_2_2_IPHONE_ID);
		mRegionA2_15_3_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_3_1_IPHONE_ID);
		mRegionA2_15_3_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_15_3_2_IPHONE_ID);
		// a2_11
		mRegionA2_11 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_11_IPHONE_ID);
		// a2_12
		mRegionA2_12 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_12_IPHONE_ID);
		// a2_2_
		mRegionA2_2_1 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_2_1_IPHONE_ID);
		mRegionA2_2_2 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_2_2_IPHONE_ID);
		mRegionA2_2_3 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_2_3_IPHONE_ID);
		mRegionA2_4 = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_4_IPHONE_ID);
		// A2_3_
		mRegionA2_3_1_Left = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_3_1_IPHONE_ID);
		mRegionA2_3_2_Left = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_3_2_IPHONE_ID);
		mRegionA2_3_3_Left = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_3_3_IPHONE_ID);
		mRegionA2_3_4_Left = mRegionLibrary1
				.get(Vol3SabosanResource.Vol3SabosanPacker1.A2_3_4_IPHONE_ID);
		// A2_5_1
		mRegionA2_5_1 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_1_IPHONE_ID);
		mRegionA2_5_2 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_2_IPHONE_ID);
		mRegionA2_5_7_1 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_7_1_IPHONE_ID);
		mRegionA2_5_7_2 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_7_2_IPHONE_ID);
		mRegionA2_5_7_3 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_7_3_IPHONE_ID);
		mRegionA2_5_7_4 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_7_4_IPHONE_ID);
		mRegionA2_5_7_5 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_5_7_5_IPHONE_ID);
		mITiledTextureRegionA2_5_ = getTiledTextureFromPacker(Vol3SabosanPack2,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_5_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_5_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_5_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_5_6_IPHONE_ID);
		// A2_6_1
		mRegionA2_6_1 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_1_IPHONE_ID);
		mRegionA2_6_2 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_2_IPHONE_ID);
		mRegionA2_6_7_1 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_7_1_IPHONE_ID);
		mRegionA2_6_7_2 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_7_2_IPHONE_ID);
		mRegionA2_6_7_3 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_7_3_IPHONE_ID);
		mRegionA2_6_7_4 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_7_4_IPHONE_ID);
		mRegionA2_6_7_5 = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_6_7_5_IPHONE_ID);
		mITiledTextureRegionA2_6_ = getTiledTextureFromPacker(Vol3SabosanPack2,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_6_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_6_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_6_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker2.A2_6_6_IPHONE_ID);
		mRegionA2_7_1 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_1_IPHONE_ID);
		mRegionA2_7_2 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_2_IPHONE_ID);
		mRegionA2_7_7_1 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_7_1_IPHONE_ID);
		mRegionA2_7_7_2 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_7_2_IPHONE_ID);
		mRegionA2_7_7_3 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_7_3_IPHONE_ID);
		mRegionA2_7_7_4 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_7_4_IPHONE_ID);
		mRegionA2_7_7_5 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_7_7_5_IPHONE_ID);
		mITiledTextureRegionA2_7_ = getTiledTextureFromPacker(Vol3SabosanPack3,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_7_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_7_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_7_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_7_6_IPHONE_ID);
		mRegionA2_8_1 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_1_IPHONE_ID);
		mRegionA2_8_2 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_2_IPHONE_ID);
		mRegionA2_8_7_1 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_7_1_IPHONE_ID);
		mRegionA2_8_7_2 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_7_2_IPHONE_ID);
		mRegionA2_8_7_3 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_7_3_IPHONE_ID);
		mRegionA2_8_7_4 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_7_4_IPHONE_ID);
		mRegionA2_8_7_5 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_7_5_IPHONE_ID);
		mITiledTextureRegionA2_8_ = getTiledTextureFromPacker(Vol3SabosanPack3,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_8_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_8_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_8_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker3.A2_8_6_IPHONE_ID);
		mRegionA2_8_8_1 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_8_1_IPHONE_ID);
		mRegionA2_8_8_2 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_8_2_IPHONE_ID);
		mRegionA2_8_8_3 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_8_3_IPHONE_ID);
		mRegionA2_8_8_4 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_8_4_IPHONE_ID);
		mRegionA2_8_8_5 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_8_5_IPHONE_ID);
		mRegionA2_8_9 = mRegionLibrary3
				.get(Vol3SabosanResource.Vol3SabosanPacker3.A2_8_9_IPHONE_ID);
		mRegionA2_9_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_1_IPHONE_ID);
		mRegionA2_9_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_2_IPHONE_ID);
		mRegionA2_9_7_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_7_1_IPHONE_ID);
		mRegionA2_9_7_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_7_2_IPHONE_ID);
		mRegionA2_9_7_3 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_7_3_IPHONE_ID);
		mRegionA2_9_7_4 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_7_4_IPHONE_ID);
		mRegionA2_9_7_5 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_7_5_IPHONE_ID);
		mITiledTextureRegionA2_9_ = getTiledTextureFromPacker(Vol3SabosanPack4,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_9_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_9_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_9_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_9_6_IPHONE_ID);
		mRegionA2_9_8_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_8_1_IPHONE_ID);
		mRegionA2_9_8_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_8_2_IPHONE_ID);
		mRegionA2_9_8_3 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_8_3_IPHONE_ID);
		mRegionA2_9_8_4 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_8_4_IPHONE_ID);
		mRegionA2_9_8_5 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_8_5_IPHONE_ID);
		mRegionA2_9_9 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_9_9_IPHONE_ID);
		mRegionA2_10_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_1_IPHONE_ID);
		mRegionA2_10_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_2_IPHONE_ID);
		mRegionA2_10_7_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_7_1_IPHONE_ID);
		mRegionA2_10_7_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_7_2_IPHONE_ID);
		mRegionA2_10_7_3 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_7_3_IPHONE_ID);
		mRegionA2_10_7_4 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_7_4_IPHONE_ID);
		mRegionA2_10_7_5 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_7_5_IPHONE_ID);
		mITiledTextureRegionA2_10_ = getTiledTextureFromPacker(
				Vol3SabosanPack4,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_10_3_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_10_4_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_10_5_IPHONE_ID,
				Vol3SabosanResource.Vol3SabosanPacker4.A2_10_6_IPHONE_ID);
		mRegionA2_10_8_1 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_8_1_IPHONE_ID);
		mRegionA2_10_8_2 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_8_2_IPHONE_ID);
		mRegionA2_10_8_3 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_8_3_IPHONE_ID);
		mRegionA2_10_8_4 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_8_4_IPHONE_ID);
		mRegionA2_10_8_5 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_8_5_IPHONE_ID);
		mRegionA2_10_9 = mRegionLibrary4
				.get(Vol3SabosanResource.Vol3SabosanPacker4.A2_10_9_IPHONE_ID);
		mRegionA2_1_1_Left = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_1_1_IPHONE_ID);
		mRegionA2_1_2_Left = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_1_2_IPHONE_ID);
		mRegionA2_1_1_Right = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_1_1_IPHONE_ID);
		mRegionA2_1_2_Right = mRegionLibrary2
				.get(Vol3SabosanResource.Vol3SabosanPacker2.A2_1_2_IPHONE_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A1_10_HANA3 = loadSoundResourceFromSD(Vol3SabosanResource.A1_10_HANA3);
		OGG_A1_10_ODORI6 = loadSoundResourceFromSD(Vol3SabosanResource.A1_10_ODORI6);
		OGG_A1_14_1_KUMOHARE = loadSoundResourceFromSD(Vol3SabosanResource.A1_14_1_KUMOHARE);
		OGG_A1_14_1_WINK = loadSoundResourceFromSD(Vol3SabosanResource.A1_14_1_WINK);
		OGG_A1_14_2_TAIYOUTAUCH = loadSoundResourceFromSD(Vol3SabosanResource.A1_14_2_TAIYOUTAUCH);
		OGG_A1_15_RAKUDAGAKU = loadSoundResourceFromSD(Vol3SabosanResource.A1_15_RAKUDAGAKU);
		OGG_A1_15__JUMP = loadSoundResourceFromSD(Vol3SabosanResource.A1_15__JUMP);
		OGG_A1_1_1_ZAZA = loadSoundResourceFromSD(Vol3SabosanResource.A1_1_1_ZAZA);
		OGG_A1_3_1_ZA1 = loadSoundResourceFromSD(Vol3SabosanResource.A1_3_1_ZA1);
		OGG_A1_3_2_ZA2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_3_2_ZA2);
		OGG_A1_3_3_ZA3 = loadSoundResourceFromSD(Vol3SabosanResource.A1_3_3_ZA3);
		OGG_A1_3_4_ZA4 = loadSoundResourceFromSD(Vol3SabosanResource.A1_3_4_ZA4);
		OGG_A1_4_1_SABOSYUTUGEN = loadSoundResourceFromSD(Vol3SabosanResource.A1_4_1_SABOSYUTUGEN);
		OGG_A1_4_2_PYU = loadSoundResourceFromSD(Vol3SabosanResource.A1_4_2_PYU);
		OGG_A1_5_ODORI1 = loadSoundResourceFromSD(Vol3SabosanResource.A1_5_ODORI1);
		OGG_A1_6_ODORI2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_6_ODORI2);
		OGG_A1_7_1TOGENUKE2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_7_1TOGENUKE2);
		OGG_A1_7_ODORI5 = loadSoundResourceFromSD(Vol3SabosanResource.A1_7_ODORI5);
		OGG_A1_8_HANA1 = loadSoundResourceFromSD(Vol3SabosanResource.A1_8_HANA1);
		OGG_A1_8_ODORI3 = loadSoundResourceFromSD(Vol3SabosanResource.A1_8_ODORI3);
		OGG_A1_9_HANA2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_9_HANA2);
		OGG_A1_9_ODORI4 = loadSoundResourceFromSD(Vol3SabosanResource.A1_9_ODORI4);
		OGG_A1_BOY_SABO1 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO1);
		OGG_A1_BOY_SABO2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO2);
		OGG_A1_BOY_SABO3 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO3);
		OGG_A1_BOY_SABO4 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO4);
		OGG_A1_BOY_SABO5 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO5);
		OGG_A1_BOY_SABO6 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO6);
		OGG_A1_BOY_SABO7 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO7);
		OGG_A1_BOY_SABO8 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO8);
		OGG_A1_BOY_SABO9 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO9);
		OGG_A1_BOY_SABO10 = loadSoundResourceFromSD(Vol3SabosanResource.A1_BOY_SABO10);
		OGG_A1_GIRL_SABO1 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO1);
		OGG_A1_GIRL_SABO2 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO2);
		OGG_A1_GIRL_SABO3 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO3);
		OGG_A1_GIRL_SABO4 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO4);
		OGG_A1_GIRL_SABO5 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO5);
		OGG_A1_GIRL_SABO6 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO6);
		OGG_A1_GIRL_SABO7 = loadSoundResourceFromSD(Vol3SabosanResource.A1_GIRL_SABO7);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(LOG, "LOAD KARAOKE SCENE");
		mScene = new Scene();
		mScene.setBackground(new Background(Color.WHITE));
		mSpriteA2_17 = new Sprite(0, 0, mRegionA2_17,
				this.getVertexBufferObjectManager());
		mScene.attachChild(mSpriteA2_17);
		mSpriteA2_14_1 = new Sprite(392, -8, mRegionA2_14_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_14_1);
		mSpriteA2_14_2 = new Sprite(392, -8, mRegionA2_14_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_14_2);
		mSpriteA2_14_2.setVisible(false);
		mSpriteA2_14_3 = new Sprite(392, -8, mRegionA2_14_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_14_3);
		mSpriteA2_14_3.setVisible(false);
		mSpriteA2_13_1 = new Sprite(399, 38, mRegionA2_13_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_13_1);
		mSpriteA2_13_2 = new Sprite(497, 81, mRegionA2_13_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_13_2);
		mSpriteA2_16_1 = new Sprite(32, 114, mRegionA2_16_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_16_1);
		mSpriteA2_16_2 = new Sprite(153, 67, mRegionA2_16_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_16_2);
		mSpriteA2_16_3 = new Sprite(748, 47, mRegionA2_16_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_16_3);
		mSpriteA2_15_1_1 = new Sprite(960, 60, mRegionA2_15_1_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_1_1);
		mSpriteA2_15_1_2 = new Sprite(960, 60, mRegionA2_15_1_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_1_2);
		mSpriteA2_15_1_2.setVisible(false);
		mSpriteA2_15_2_1 = new Sprite(960, 60, mRegionA2_15_2_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_2_1);
		mSpriteA2_15_2_2 = new Sprite(960, 60, mRegionA2_15_2_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_2_2);
		mSpriteA2_15_2_2.setVisible(false);
		mSpriteA2_15_3_1 = new Sprite(960, 60, mRegionA2_15_3_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_3_1);
		mSpriteA2_15_3_2 = new Sprite(960, 60, mRegionA2_15_3_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_15_3_2);
		mSpriteA2_15_3_2.setVisible(false);
		mSpriteA2_12 = new Sprite(-25, 142, mRegionA2_12,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_12);
		mSpriteA2_11 = new Sprite(59, 151, mRegionA2_11,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_11);
		mSpriteA2_5_1 = new Sprite(121, 258, mRegionA2_5_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_1);
		mSpriteA2_5_1.setVisible(false);
		mSpriteA2_5_2 = new Sprite(116, 123, mRegionA2_5_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_2);
		mSpriteA2_5_2.setVisible(false);
		mSpriteA2_5_7_1 = new Sprite(215, 391, mRegionA2_5_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_7_1);
		mSpriteA2_5_7_1.setVisible(false);
		mSpriteA2_5_7_2 = new Sprite(272, 224, mRegionA2_5_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_7_2);
		mSpriteA2_5_7_2.setVisible(false);
		mSpriteA2_5_7_3 = new Sprite(348, 136, mRegionA2_5_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_7_3);
		mSpriteA2_5_7_3.setVisible(false);
		mSpriteA2_5_7_4 = new Sprite(432, 317, mRegionA2_5_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_7_4);
		mSpriteA2_5_7_4.setVisible(false);
		mSpriteA2_5_7_5 = new Sprite(375, 383, mRegionA2_5_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_5_7_5);
		mSpriteA2_5_7_5.setVisible(false);
		mAnimatedSpriteA2_5_ = new AnimatedSprite(121, 120,
				mITiledTextureRegionA2_5_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_5_);
		mAnimatedSpriteA2_5_.setVisible(false);
		mSpriteA2_6_1 = new Sprite(121, 258, mRegionA2_6_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_1);
		mSpriteA2_6_1.setVisible(false);
		mSpriteA2_6_2 = new Sprite(121, 115, mRegionA2_6_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_2);
		mSpriteA2_6_2.setVisible(false);
		mSpriteA2_6_7_1 = new Sprite(254, 204, mRegionA2_6_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_7_1);
		mSpriteA2_6_7_1.setVisible(false);
		mSpriteA2_6_7_2 = new Sprite(316, 236, mRegionA2_6_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_7_2);
		mSpriteA2_6_7_2.setVisible(false);
		mSpriteA2_6_7_3 = new Sprite(397, 330, mRegionA2_6_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_7_3);
		mSpriteA2_6_7_3.setVisible(false);
		mSpriteA2_6_7_4 = new Sprite(421, 223, mRegionA2_6_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_7_4);
		mSpriteA2_6_7_4.setVisible(false);
		mSpriteA2_6_7_5 = new Sprite(445, 324, mRegionA2_6_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_6_7_5);
		mSpriteA2_6_7_5.setVisible(false);
		mAnimatedSpriteA2_6_ = new AnimatedSprite(121, 120,
				mITiledTextureRegionA2_6_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_6_);
		mAnimatedSpriteA2_6_.setVisible(false);
		mSpriteA2_7_1 = new Sprite(121, 258, mRegionA2_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_1);
		mSpriteA2_7_1.setVisible(false);
		mSpriteA2_7_2 = new Sprite(118, 120, mRegionA2_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_2);
		mSpriteA2_7_2.setVisible(false);
		mSpriteA2_7_7_1 = new Sprite(161, 369, mRegionA2_7_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_7_1);
		mSpriteA2_7_7_1.setVisible(false);
		mSpriteA2_7_7_2 = new Sprite(229, 448, mRegionA2_7_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_7_2);
		mSpriteA2_7_7_2.setVisible(false);
		mSpriteA2_7_7_3 = new Sprite(357, 449, mRegionA2_7_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_7_3);
		mSpriteA2_7_7_3.setVisible(false);
		mSpriteA2_7_7_4 = new Sprite(410, 258, mRegionA2_7_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_7_4);
		mSpriteA2_7_7_4.setVisible(false);
		mSpriteA2_7_7_5 = new Sprite(418, 389, mRegionA2_7_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_7_7_5);
		mSpriteA2_7_7_5.setVisible(false);
		mAnimatedSpriteA2_7_ = new AnimatedSprite(121, 120,
				mITiledTextureRegionA2_7_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_7_);
		mAnimatedSpriteA2_7_.setVisible(false);
		mSpriteA2_8_1 = new Sprite(480, 258, mRegionA2_8_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_1);
		mSpriteA2_8_1.setVisible(false);
		mSpriteA2_8_2 = new Sprite(480, 156, mRegionA2_8_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_2);
		mSpriteA2_8_2.setVisible(false);
		mSpriteA2_8_7_1 = new Sprite(555, 433, mRegionA2_8_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_7_1);
		mSpriteA2_8_7_1.setVisible(false);
		mSpriteA2_8_7_2 = new Sprite(562, 335, mRegionA2_8_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_7_2);
		mSpriteA2_8_7_2.setVisible(false);
		mSpriteA2_8_7_3 = new Sprite(678, 291, mRegionA2_8_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_7_3);
		mSpriteA2_8_7_3.setVisible(false);
		mSpriteA2_8_7_4 = new Sprite(798, 343, mRegionA2_8_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_7_4);
		mSpriteA2_8_7_4.setVisible(false);
		mSpriteA2_8_7_5 = new Sprite(791, 436, mRegionA2_8_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_7_5);
		mSpriteA2_8_7_5.setVisible(false);
		mAnimatedSpriteA2_8_ = new AnimatedSprite(480, 156,
				mITiledTextureRegionA2_8_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_8_);
		mAnimatedSpriteA2_8_.setVisible(false);
		mSpriteA2_8_8_1 = new Sprite(575, 433, mRegionA2_8_8_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_8_1);
		mSpriteA2_8_8_1.setVisible(false);
		mSpriteA2_8_8_2 = new Sprite(572, 335, mRegionA2_8_8_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_8_2);
		mSpriteA2_8_8_2.setVisible(false);
		mSpriteA2_8_8_3 = new Sprite(678, 291, mRegionA2_8_8_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_8_3);
		mSpriteA2_8_8_3.setVisible(false);
		mSpriteA2_8_8_4 = new Sprite(788, 343, mRegionA2_8_8_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_8_4);
		mSpriteA2_8_8_4.setVisible(false);
		mSpriteA2_8_8_5 = new Sprite(771, 436, mRegionA2_8_8_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_8_5);
		mSpriteA2_8_8_5.setVisible(false);
		mSpriteA2_8_9 = new Sprite(490, 160, mRegionA2_8_9,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_8_9);
		mSpriteA2_8_9.setVisible(false);
		mSpriteA2_9_1 = new Sprite(480, 258, mRegionA2_9_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_1);
		mSpriteA2_9_1.setVisible(false);
		mSpriteA2_9_2 = new Sprite(480, 156, mRegionA2_9_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_2);
		mSpriteA2_9_2.setVisible(false);
		mSpriteA2_9_7_1 = new Sprite(581, 408, mRegionA2_9_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_7_1);
		mSpriteA2_9_7_1.setVisible(false);
		mSpriteA2_9_7_2 = new Sprite(550, 292, mRegionA2_9_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_7_2);
		mSpriteA2_9_7_2.setVisible(false);
		mSpriteA2_9_7_3 = new Sprite(665, 209, mRegionA2_9_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_7_3);
		mSpriteA2_9_7_3.setVisible(false);
		mSpriteA2_9_7_4 = new Sprite(786, 296, mRegionA2_9_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_7_4);
		mSpriteA2_9_7_4.setVisible(false);
		mSpriteA2_9_7_5 = new Sprite(762, 410, mRegionA2_9_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_7_5);
		mSpriteA2_9_7_5.setVisible(false);
		mAnimatedSpriteA2_9_ = new AnimatedSprite(480, 156,
				mITiledTextureRegionA2_9_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_9_);
		mAnimatedSpriteA2_9_.setVisible(false);
		mSpriteA2_9_8_1 = new Sprite(591, 408, mRegionA2_9_8_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_8_1);
		mSpriteA2_9_8_1.setVisible(false);
		mSpriteA2_9_8_2 = new Sprite(555, 292, mRegionA2_9_8_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_8_2);
		mSpriteA2_9_8_2.setVisible(false);
		mSpriteA2_9_8_3 = new Sprite(665, 209, mRegionA2_9_8_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_8_3);
		mSpriteA2_9_8_3.setVisible(false);
		mSpriteA2_9_8_4 = new Sprite(781, 296, mRegionA2_9_8_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_8_4);
		mSpriteA2_9_8_4.setVisible(false);
		mSpriteA2_9_8_5 = new Sprite(752, 410, mRegionA2_9_8_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_8_5);
		mSpriteA2_9_8_5.setVisible(false);
		mSpriteA2_9_9 = new Sprite(480, 156, mRegionA2_9_9,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_9_9);
		mSpriteA2_9_9.setVisible(false);
		mSpriteA2_10_1 = new Sprite(480, 258, mRegionA2_10_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_1);
		mSpriteA2_10_1.setVisible(false);
		mSpriteA2_10_2 = new Sprite(480, 156, mRegionA2_10_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_2);
		mSpriteA2_10_2.setVisible(false);
		mSpriteA2_10_7_1 = new Sprite(564, 411, mRegionA2_10_7_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_7_1);
		mSpriteA2_10_7_1.setVisible(false);
		mSpriteA2_10_7_2 = new Sprite(586, 316, mRegionA2_10_7_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_7_2);
		mSpriteA2_10_7_2.setVisible(false);
		mSpriteA2_10_7_3 = new Sprite(689, 307, mRegionA2_10_7_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_7_3);
		mSpriteA2_10_7_3.setVisible(false);
		mSpriteA2_10_7_4 = new Sprite(772, 329, mRegionA2_10_7_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_7_4);
		mSpriteA2_10_7_4.setVisible(false);
		mSpriteA2_10_7_5 = new Sprite(775, 426, mRegionA2_10_7_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_7_5);
		mSpriteA2_10_7_5.setVisible(false);
		mAnimatedSpriteA2_10_ = new AnimatedSprite(480, 156,
				mITiledTextureRegionA2_10_, this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mAnimatedSpriteA2_10_);
		mAnimatedSpriteA2_10_.setVisible(false);
		mSpriteA2_10_8_1 = new Sprite(564, 411, mRegionA2_10_8_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_8_1);
		mSpriteA2_10_8_1.setVisible(false);
		mSpriteA2_10_8_2 = new Sprite(586, 326, mRegionA2_10_8_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_8_2);
		mSpriteA2_10_8_2.setVisible(false);
		mSpriteA2_10_8_3 = new Sprite(689, 307, mRegionA2_10_8_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_8_3);
		mSpriteA2_10_8_3.setVisible(false);
		mSpriteA2_10_8_4 = new Sprite(772, 339, mRegionA2_10_8_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_8_4);
		mSpriteA2_10_8_4.setVisible(false);
		mSpriteA2_10_8_5 = new Sprite(770, 426, mRegionA2_10_8_5,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_8_5);
		mSpriteA2_10_8_5.setVisible(false);
		mSpriteA2_10_9 = new Sprite(480, 156, mRegionA2_10_9,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_10_9);
		mSpriteA2_10_9.setVisible(false);
		mSpriteA2_1_1_Left = new Sprite(187, 408, mRegionA2_1_1_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_1_1_Left);
		mSpriteA2_1_1_Left.setVisible(false);
		mSpriteA2_1_2_Left = new Sprite(337, 408, mRegionA2_1_2_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_1_2_Left);
		mSpriteA2_1_2_Left.setVisible(false);
		mSpriteA2_1_1_Right = new Sprite(596, 408, mRegionA2_1_1_Right,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_1_1_Right);
		mSpriteA2_1_1_Right.setVisible(false);
		mSpriteA2_1_2_Right = new Sprite(746, 408, mRegionA2_1_2_Right,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_1_2_Right);
		mSpriteA2_1_2_Right.setVisible(false);
		mSpriteA2_4 = new Sprite(-40, 476, mRegionA2_4,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_4);
		mSpriteA2_2_3 = new Sprite(-52, 573, mRegionA2_2_3,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_2_3);
		mSpriteA2_3_1 = new Sprite(53, 398, mRegionA2_3_1_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_3_1);
		mSpriteA2_3_1.setVisible(false);
		mSpriteA2_3_2 = new Sprite(53, 398, mRegionA2_3_2_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_3_2);
		mSpriteA2_3_2.setVisible(false);
		mSpriteA2_3_3 = new Sprite(53, 398, mRegionA2_3_3_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_3_3);
		mSpriteA2_3_3.setVisible(false);
		mSpriteA2_3_4 = new Sprite(53, 398, mRegionA2_3_4_Left,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_3_4);
		mSpriteA2_3_4.setVisible(false);

		mSpriteA2_2_1 = new Sprite(17, 548, mRegionA2_2_1,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_2_1);
		mSpriteA2_2_2 = new Sprite(671, 548, mRegionA2_2_2,
				this.getVertexBufferObjectManager());
		mSpriteA2_17.attachChild(mSpriteA2_2_2);
		mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void combineGimmic3WithAction() {
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		int pX = (int) arg1.getX();
		int pY = (int) arg1.getY();
		if (arg1.isActionDown()) {
			if (mSpriteA2_5_1.isVisible() || mSpriteA2_5_2.isVisible()) {

				if ((mSpriteA2_5_7_1.contains(pX + 20, pY + 20)
						|| mSpriteA2_5_7_1.contains(pX - 20, pY - 20) || mSpriteA2_5_7_1
							.contains(pX, pY))
						&& mSpriteA2_5_7_1.isVisible()
						&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_5_7_1);
				}
				if ((mSpriteA2_5_7_2.contains(pX + 20, pY + 20)
						|| mSpriteA2_5_7_2.contains(pX - 20, pY - 20) || mSpriteA2_5_7_2
							.contains(pX, pY))
						&& mSpriteA2_5_7_2.isVisible()
						&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_5_7_2);
				}
				if ((mSpriteA2_5_7_3.contains(pX + 20, pY + 20)
						|| mSpriteA2_5_7_3.contains(pX - 20, pY - 20) || mSpriteA2_5_7_3
							.contains(pX, pY))
						&& mSpriteA2_5_7_3.isVisible()
						&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_5_7_3);
				}
				if ((mSpriteA2_5_7_4.contains(pX + 20, pY + 20)
						|| mSpriteA2_5_7_4.contains(pX - 20, pY - 20) || mSpriteA2_5_7_4
						.contains(pX, pY))
					&& mSpriteA2_5_7_4.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_5_7_4);
				}
				if ((mSpriteA2_5_7_5.contains(pX + 20, pY + 20)
						|| mSpriteA2_5_7_5.contains(pX - 20, pY - 20) || mSpriteA2_5_7_5
						.contains(pX, pY))
					&& mSpriteA2_5_7_5.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_5_7_5);
				}
			}
			if (mSpriteA2_6_1.isVisible() || mSpriteA2_6_2.isVisible()) {
				if ((mSpriteA2_6_7_1.contains(pX + 20, pY + 20)
						|| mSpriteA2_6_7_1.contains(pX - 20, pY - 20) || mSpriteA2_6_7_1
						.contains(pX, pY))
					&& mSpriteA2_6_7_1.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_6_7_1);
				}
				if ((mSpriteA2_6_7_2.contains(pX + 20, pY + 20)
						|| mSpriteA2_6_7_2.contains(pX - 20, pY - 20) || mSpriteA2_6_7_2
						.contains(pX, pY))
					&& mSpriteA2_6_7_2.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_6_7_2);
				}
				if ((mSpriteA2_6_7_3.contains(pX + 20, pY + 20)
						|| mSpriteA2_6_7_3.contains(pX - 20, pY - 20) || mSpriteA2_6_7_3
						.contains(pX, pY))
					&& mSpriteA2_6_7_3.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_6_7_3);
				}
				if ((mSpriteA2_6_7_4.contains(pX + 20, pY + 20)
						|| mSpriteA2_6_7_4.contains(pX - 20, pY - 20) || mSpriteA2_6_7_4
						.contains(pX, pY))
					&& mSpriteA2_6_7_4.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_6_7_4);
				}
				if ((mSpriteA2_6_7_5.contains(pX + 20, pY + 20)
						|| mSpriteA2_6_7_5.contains(pX - 20, pY - 20) || mSpriteA2_6_7_5
						.contains(pX, pY))
					&& mSpriteA2_6_7_5.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_6_7_5);
				}
			}
			if (mSpriteA2_7_1.isVisible() || mSpriteA2_7_2.isVisible()) {
				if ((mSpriteA2_7_7_1.contains(pX + 20, pY + 20)
						|| mSpriteA2_7_7_1.contains(pX - 20, pY - 20) || mSpriteA2_7_7_1
						.contains(pX, pY))
					&& mSpriteA2_7_7_1.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_7_7_1);
				}
				if ((mSpriteA2_7_7_2.contains(pX + 20, pY + 20)
						|| mSpriteA2_7_7_2.contains(pX - 20, pY - 20) || mSpriteA2_7_7_2
						.contains(pX, pY))
					&& mSpriteA2_7_7_2.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_7_7_2);
				}
				if ((mSpriteA2_7_7_3.contains(pX + 20, pY + 20)
						|| mSpriteA2_7_7_3.contains(pX - 20, pY - 20) || mSpriteA2_7_7_3
						.contains(pX, pY))
					&& mSpriteA2_7_7_3.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_7_7_3);
				}
				if ((mSpriteA2_7_7_4.contains(pX + 20, pY + 20)
						|| mSpriteA2_7_7_4.contains(pX - 20, pY - 20) || mSpriteA2_7_7_4
						.contains(pX, pY))
					&& mSpriteA2_7_7_4.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_7_7_4);
				}
				if ((mSpriteA2_7_7_5.contains(pX + 20, pY + 20)
						|| mSpriteA2_7_7_5.contains(pX - 20, pY - 20) || mSpriteA2_7_7_5
						.contains(pX, pY))
					&& mSpriteA2_7_7_5.isVisible()
					&& checkOnClick) {
					onClickVaoCayCoGai(mSpriteA2_7_7_5);
				}
			}
			if (mSpriteA2_8_1.isVisible() || mSpriteA2_8_2.isVisible()) {
				if (mSpriteA2_8_7_1.isVisible()
						&& mSpriteA2_8_7_1.contains(pX, pY) && checkOnClick) {
					onClickA2_8_(mSpriteA2_8_7_1);
				}
				if (mSpriteA2_8_7_2.isVisible()
						&& mSpriteA2_8_7_2.contains(pX, pY) && checkOnClick) {
					onClickA2_8_(mSpriteA2_8_7_2);
				}
				if (mSpriteA2_8_7_3.isVisible()
						&& mSpriteA2_8_7_3.contains(pX, pY) && checkOnClick) {
					onClickA2_8_(mSpriteA2_8_7_3);
				}
				if (mSpriteA2_8_7_4.isVisible()
						&& mSpriteA2_8_7_4.contains(pX, pY) && checkOnClick) {
					onClickA2_8_(mSpriteA2_8_7_4);
				}
				if (mSpriteA2_8_7_5.isVisible()
						&& mSpriteA2_8_7_5.contains(pX, pY) && checkOnClick) {
					if (!((mSpriteA2_3_1.isVisible() && mSpriteA2_3_1.getX() == 702)
							|| (mSpriteA2_3_2.isVisible() && mSpriteA2_3_2
									.getX() == 702) || (mSpriteA2_3_4
							.isVisible() && mSpriteA2_3_3.getX() == 702))) {
						onClickA2_8_(mSpriteA2_8_7_5);
					}
				}
			}
			if (mSpriteA2_9_1.isVisible() || mSpriteA2_9_2.isVisible()) {
				if (mSpriteA2_9_7_1.isVisible()
						&& mSpriteA2_9_7_1.contains(pX, pY) && checkOnClick) {
					onClickA2_9_(mSpriteA2_9_7_1);
				}
				if (mSpriteA2_9_7_2.isVisible()
						&& mSpriteA2_9_7_2.contains(pX, pY) && checkOnClick) {
					onClickA2_9_(mSpriteA2_9_7_2);
				}
				if (mSpriteA2_9_7_3.isVisible()
						&& mSpriteA2_9_7_3.contains(pX, pY) && checkOnClick) {
					onClickA2_9_(mSpriteA2_9_7_3);
				}
				if (mSpriteA2_9_7_4.isVisible()
						&& mSpriteA2_9_7_4.contains(pX, pY) && checkOnClick) {
					onClickA2_9_(mSpriteA2_9_7_4);
				}
				if (mSpriteA2_9_7_5.isVisible()
						&& mSpriteA2_9_7_5.contains(pX, pY) && checkOnClick) {
					if (!((mSpriteA2_3_1.isVisible() && mSpriteA2_3_1.getX() == 702)
							|| (mSpriteA2_3_2.isVisible() && mSpriteA2_3_2
									.getX() == 702) || (mSpriteA2_3_4
							.isVisible() && mSpriteA2_3_3.getX() == 702))) {
						onClickA2_9_(mSpriteA2_9_7_5);
					}
				}
			}
			if (mSpriteA2_10_1.isVisible() || mSpriteA2_10_2.isVisible()) {
				if (mSpriteA2_10_7_1.isVisible()
						&& mSpriteA2_10_7_1.contains(pX, pY) && checkOnClick) {
					onClickA2_10_(mSpriteA2_10_7_1);
				}
				if (mSpriteA2_10_7_2.isVisible()
						&& mSpriteA2_10_7_2.contains(pX, pY) && checkOnClick) {
					onClickA2_10_(mSpriteA2_10_7_2);
				}
				if (mSpriteA2_10_7_3.isVisible()
						&& mSpriteA2_10_7_3.contains(pX, pY) && checkOnClick) {
					onClickA2_10_(mSpriteA2_10_7_3);
				}
				if (mSpriteA2_10_7_4.isVisible()
						&& mSpriteA2_10_7_4.contains(pX, pY) && checkOnClick) {
					onClickA2_10_(mSpriteA2_10_7_4);
				}
				if (mSpriteA2_10_7_5.isVisible()
						&& mSpriteA2_10_7_5.contains(pX, pY) && checkOnClick) {
					if (!((mSpriteA2_3_1.isVisible() && mSpriteA2_3_1.getX() == 702)
							|| (mSpriteA2_3_2.isVisible() && mSpriteA2_3_2
									.getX() == 702) || (mSpriteA2_3_4
							.isVisible() && mSpriteA2_3_3.getX() == 702))) {
						onClickA2_10_(mSpriteA2_10_7_5);
					}
				}
			}
			if (mSpriteA2_14_3.isVisible() && checkSunOnclick
					&& mSpriteA2_14_3.contains(pX, pY) && pY < 120 && pX > 440
					&& pX < 520 && pY > 5) {
				mSpriteA2_14_3.unregisterUpdateHandler(timerHandlerA2_14_3);
				mSpriteA2_14_3.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = false;
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = true;
							}
						}));
				mSpriteA2_14_3.setVisible(false);
				mSpriteA2_14_1.setVisible(true);
				mSpriteA2_13_1.setVisible(true);
				mSpriteA2_13_2.setVisible(true);
			} else if (mSpriteA2_14_2.isVisible()
					&& mSpriteA2_14_2.contains(pX, pY) && pY < 120 && pX > 440
					&& pX < 520 && pY > 5 && checkSunOnclick) {
				mSpriteA2_14_2.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = false;
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = true;
							}
						}));
				mSpriteA2_14_2.setVisible(false);
				mSpriteA2_14_3.setVisible(true);
				checkVisibleA2_14_3();
				OGG_A1_14_2_TAIYOUTAUCH.play();
			} else if (mSpriteA2_14_1.isVisible()
					&& mSpriteA2_14_1.contains(pX, pY) && pY < 120 && pX > 440
					&& pX < 520 && pY > 5 && checkSunOnclick) {
				Log.d(LOG, "********* X : " + pX);
				Log.d(LOG, "********* Y : " + pY);
				mSpriteA2_14_1.setVisible(false);
				mSpriteA2_14_1.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = false;
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								checkSunOnclick = true;
							}
						}));
				mSpriteA2_14_2.setVisible(true);
				mSpriteA2_13_1.setVisible(false);
				mSpriteA2_13_2.setVisible(false);
				OGG_A1_14_1_KUMOHARE.play();
				OGG_A1_14_1_WINK.play();
			}
			if (mSpriteA2_2_1.isVisible() && mSpriteA2_2_1.contains(pX, pY)) {
				switch (nore.getNextIntNoRepeatLast()) {
				case 0:
					OGG_A1_3_1_ZA1.play();
					processOnClickA2_2_1(mSpriteA2_3_1);
					break;
				case 1:
					OGG_A1_3_2_ZA2.play();
					processOnClickA2_2_1(mSpriteA2_3_2);
					break;
				case 2:
					OGG_A1_3_3_ZA3.play();
					processOnClickA2_2_1(mSpriteA2_3_3);
					break;
				default:
					OGG_A1_3_4_ZA4.play();
					processOnClickA2_2_1(mSpriteA2_3_4);
					break;
				}
			}
			if (mSpriteA2_2_2.isVisible() && mSpriteA2_2_2.contains(pX, pY)) {
				switch (nore.getNextIntNoRepeatLast()) {
				case 0:
					OGG_A1_3_1_ZA1.play();
					processOnClickA2_2_2(mSpriteA2_3_1);
					break;
				case 1:
					OGG_A1_3_2_ZA2.play();
					processOnClickA2_2_2(mSpriteA2_3_2);
					break;
				case 2:
					OGG_A1_3_3_ZA3.play();
					processOnClickA2_2_2(mSpriteA2_3_3);
					break;
				default:
					OGG_A1_3_4_ZA4.play();
					processOnClickA2_2_2(mSpriteA2_3_4);
					break;
				}
			}
			if (mSpriteA2_15_1_1.isVisible()
					&& mSpriteA2_15_1_1.contains(pX, pY)
					&& (pY > 95 && pX < 440 || pX > 520 && pY > 95)
					|| mSpriteA2_15_1_1.isVisible()
					&& mSpriteA2_15_1_1.contains(pX, pY)
					&& (pY > 125 && pX > 440 || pX < 520 && pY > 125)) {
				Log.d(LOG, "************ X = " + pX);
				Log.d(LOG, "************ Y = " + pY);
				if (!mSpriteA2_5_7_2.contains(pX, pY)
						&& !mSpriteA2_5_7_3.contains(pX, pY)
						&& !mSpriteA2_6_7_2.contains(pX, pY)
						&& !mSpriteA2_6_7_4.contains(pX, pY)
						&& !mSpriteA2_6_7_1.contains(pX, pY)
						&& !mSpriteA2_7_7_4.contains(pX, pY)
						&& !mSpriteA2_9_7_3.contains(pX, pY)
						&& !mSpriteA2_10_7_3.contains(pX, pY)
						&& !mSpriteA2_8_7_3.contains(pX, pY)) {
					if (mSpriteA2_5_1.getY() < 115
							|| mSpriteA2_6_1.getY() < 115
							|| mSpriteA2_7_1.getY() < 115
							|| mSpriteA2_8_1.getY() < 150
							|| mSpriteA2_9_1.getY() < 150
							|| mSpriteA2_10_1.getY() < 150) {
						if ((mSpriteA2_5_1.isVisible()
								&& !mSpriteA2_5_1.contains(pX, pY)
								|| mSpriteA2_6_1.isVisible()
								&& !mSpriteA2_6_1.contains(pX, pY) || mSpriteA2_7_1
								.isVisible() && !mSpriteA2_7_1.contains(pX, pY))
								&& (mSpriteA2_8_1.isVisible()
										&& !mSpriteA2_8_1.contains(pX, pY)
										|| mSpriteA2_9_1.isVisible()
										&& !mSpriteA2_9_1.contains(pX, pY) || mSpriteA2_10_1
										.isVisible()
										&& !mSpriteA2_10_1.contains(pX, pY))) {
							if (mSpriteA2_5_1.isVisible()
									|| mSpriteA2_5_2.isVisible()) {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 200 && pX < 530 && pX > 360
										|| pY < 200 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_1_1.setVisible(false);
									mSpriteA2_15_1_2.setVisible(true);
									mSpriteA2_15_1_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_1_1
																	.getX(),
															mSpriteA2_15_1_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.stop();
																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_1_1
																	.getX() - 39.26f,
															mSpriteA2_15_1_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mSpriteA2_15_1_1
																			.setVisible(true);
																	mSpriteA2_15_1_2
																			.setVisible(false);
																	mSpriteA2_15_1_1
																			.setPosition(
																					mSpriteA2_15_1_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
																}
															})));
								}
							} else {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 230 && pX < 530 && pX > 360
										|| pY < 230 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_1_1.setVisible(false);
									mSpriteA2_15_1_2.setVisible(true);
									mSpriteA2_15_1_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_1_1
																	.getX(),
															mSpriteA2_15_1_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.stop();
																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_1_1
																	.getX() - 39.26f,
															mSpriteA2_15_1_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {

																	mSpriteA2_15_1_1
																			.setVisible(true);
																	mSpriteA2_15_1_2
																			.setVisible(false);
																	mSpriteA2_15_1_1
																			.setPosition(
																					mSpriteA2_15_1_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
												}
										})));
								}
							}

						}
					} else {

						if (mSpriteA2_5_1.isVisible()
								|| mSpriteA2_5_2.isVisible()) {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 200
									&& pX < 530 && pX > 360 || pY < 200
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_1_1.setVisible(false);
								mSpriteA2_15_1_2.setVisible(true);
								mSpriteA2_15_1_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_1_1.getX(),
														mSpriteA2_15_1_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.stop();
															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_1_1.getX() - 39.26f,
														mSpriteA2_15_1_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {

																mSpriteA2_15_1_1
																		.setVisible(true);
																mSpriteA2_15_1_2
																		.setVisible(false);
																mSpriteA2_15_1_1
																		.setPosition(
																				mSpriteA2_15_1_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
															}
														})));
							}
						} else {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 230
									&& pX < 530 && pX > 360 || pY < 230
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_1_1.setVisible(false);
								mSpriteA2_15_1_2.setVisible(true);
								mSpriteA2_15_1_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_1_1.getX(),
														mSpriteA2_15_1_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.stop();
															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_1_1.getX() - 39.26f,
														mSpriteA2_15_1_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {

																mSpriteA2_15_1_1
																		.setVisible(true);
																mSpriteA2_15_1_2
																		.setVisible(false);
																mSpriteA2_15_1_1
																		.setPosition(
																				mSpriteA2_15_1_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
											}
									})));
							}
						}

					}
				}
			}
			if (mSpriteA2_15_2_1.isVisible()
					&& mSpriteA2_15_2_1.contains(pX, pY)
					&& (pY > 95 && pX < 440 || pX > 520 && pY > 95)
					|| mSpriteA2_15_2_1.isVisible()
					&& mSpriteA2_15_2_1.contains(pX, pY)
					&& (pY > 125 && pX > 440 || pX < 520 && pY > 125)) {
				if (!mSpriteA2_5_7_2.contains(pX, pY)
						&& !mSpriteA2_5_7_3.contains(pX, pY)
						&& !mSpriteA2_6_7_2.contains(pX, pY)
						&& !mSpriteA2_6_7_4.contains(pX, pY)
						&& !mSpriteA2_6_7_1.contains(pX, pY)
						&& !mSpriteA2_7_7_4.contains(pX, pY)
						&& !mSpriteA2_9_7_3.contains(pX, pY)
						&& !mSpriteA2_8_7_3.contains(pX, pY)) {
					if (mSpriteA2_5_1.getY() < 115
							|| mSpriteA2_6_1.getY() < 115
							|| mSpriteA2_7_1.getY() < 115
							|| mSpriteA2_8_1.getY() < 150
							|| mSpriteA2_9_1.getY() < 150
							|| mSpriteA2_10_1.getY() < 150) {
						if ((mSpriteA2_5_1.isVisible()
								&& !mSpriteA2_5_1.contains(pX, pY)
								|| mSpriteA2_6_1.isVisible()
								&& !mSpriteA2_6_1.contains(pX, pY) || mSpriteA2_7_1
								.isVisible() && !mSpriteA2_7_1.contains(pX, pY))
								&& (mSpriteA2_8_1.isVisible()
										&& !mSpriteA2_8_1.contains(pX, pY)
										|| mSpriteA2_9_1.isVisible()
										&& !mSpriteA2_9_1.contains(pX, pY) || mSpriteA2_10_1
										.isVisible()
										&& !mSpriteA2_10_1.contains(pX, pY))) {

							if (mSpriteA2_5_1.isVisible()
									|| mSpriteA2_5_2.isVisible()) {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 200 && pX < 530 && pX > 360
										|| pY < 200 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_2_1.setVisible(false);
									mSpriteA2_15_2_2.setVisible(true);
									mSpriteA2_15_2_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_2_1
																	.getX(),
															mSpriteA2_15_2_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_2_1
																	.getX() - 39.26f,
															mSpriteA2_15_2_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mSpriteA2_15_2_1
																			.setVisible(true);
																	mSpriteA2_15_2_2
																			.setVisible(false);
																	mSpriteA2_15_2_1
																			.setPosition(
																					mSpriteA2_15_2_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
																}
															})));
								}
							} else {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 230 && pX < 530 && pX > 360
										|| pY < 230 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_2_1.setVisible(false);
									mSpriteA2_15_2_2.setVisible(true);
									mSpriteA2_15_2_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_2_1
																	.getX(),
															mSpriteA2_15_2_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_2_1
																	.getX() - 39.26f,
															mSpriteA2_15_2_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mSpriteA2_15_2_1
																			.setVisible(true);
																	mSpriteA2_15_2_2
																			.setVisible(false);
																	mSpriteA2_15_2_1
																			.setPosition(
																					mSpriteA2_15_2_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
																}
															})));
								}
							}

						}
					} else {
						if (mSpriteA2_5_1.isVisible()
								|| mSpriteA2_5_2.isVisible()) {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 200
									&& pX < 530 && pX > 360 || pY < 200
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_2_1.setVisible(false);
								mSpriteA2_15_2_2.setVisible(true);
								mSpriteA2_15_2_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_2_1.getX(),
														mSpriteA2_15_2_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_2_1.getX() - 39.26f,
														mSpriteA2_15_2_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																mSpriteA2_15_2_1
																		.setVisible(true);
																mSpriteA2_15_2_2
																		.setVisible(false);
																mSpriteA2_15_2_1
																		.setPosition(
																				mSpriteA2_15_2_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
															}
														})));
							}
						} else {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 230
									&& pX < 530 && pX > 360 || pY < 230
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_2_1.setVisible(false);
								mSpriteA2_15_2_2.setVisible(true);
								mSpriteA2_15_2_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_2_1.getX(),
														mSpriteA2_15_2_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_2_1.getX() - 39.26f,
														mSpriteA2_15_2_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																mSpriteA2_15_2_1
																		.setVisible(true);
																mSpriteA2_15_2_2
																		.setVisible(false);
																mSpriteA2_15_2_1
																		.setPosition(
																				mSpriteA2_15_2_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
											}
									})));
							}
						}
					}
				}
			}
			if (mSpriteA2_15_3_1.isVisible()
					&& mSpriteA2_15_3_1.contains(pX, pY)
					&& (pY > 95 && pX < 440 || pX > 520 && pY > 95)
					|| mSpriteA2_15_3_1.isVisible()
					&& mSpriteA2_15_3_1.contains(pX, pY)
					&& (pY > 125 && pX > 440 || pX < 520 && pY > 125)) {
				if (!mSpriteA2_5_7_2.contains(pX, pY)
						&& !mSpriteA2_5_7_3.contains(pX, pY)
						&& !mSpriteA2_6_7_2.contains(pX, pY)
						&& !mSpriteA2_6_7_4.contains(pX, pY)
						&& !mSpriteA2_6_7_1.contains(pX, pY)
						&& !mSpriteA2_7_7_4.contains(pX, pY)
						&& !mSpriteA2_9_7_3.contains(pX, pY)
						&& !mSpriteA2_8_7_3.contains(pX, pY)) {
					if (mSpriteA2_5_1.getY() < 115
							|| mSpriteA2_6_1.getY() < 115
							|| mSpriteA2_7_1.getY() < 115
							|| mSpriteA2_8_1.getY() < 150
							|| mSpriteA2_9_1.getY() < 150
							|| mSpriteA2_10_1.getY() < 150) {
						if ((mSpriteA2_5_1.isVisible()
								&& !mSpriteA2_5_1.contains(pX, pY)
								|| mSpriteA2_6_1.isVisible()
								&& !mSpriteA2_6_1.contains(pX, pY) || mSpriteA2_7_1
								.isVisible() && !mSpriteA2_7_1.contains(pX, pY))
								&& (mSpriteA2_8_1.isVisible()
										&& !mSpriteA2_8_1.contains(pX, pY)
										|| mSpriteA2_9_1.isVisible()
										&& !mSpriteA2_9_1.contains(pX, pY) || mSpriteA2_10_1
										.isVisible()
										&& !mSpriteA2_10_1.contains(pX, pY))) {
							if (mSpriteA2_5_1.isVisible()
									|| mSpriteA2_5_2.isVisible()) {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 200 && pX < 530 && pX > 360
										|| pY < 200 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_3_1.setVisible(false);
									mSpriteA2_15_3_2.setVisible(true);
									mSpriteA2_15_3_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_3_1
																	.getX(),
															mSpriteA2_15_3_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {

																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_3_1
																	.getX() - 39.26f,
															mSpriteA2_15_3_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mSpriteA2_15_3_1
																			.setVisible(true);
																	mSpriteA2_15_3_2
																			.setVisible(false);
																	mSpriteA2_15_3_1
																			.setPosition(
																					mSpriteA2_15_3_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
																}
															})));
								}
							} else {
								if (pY < 250 && pX < 930 && pX > 750
										|| pY < 230 && pX < 530 && pX > 360
										|| pY < 230 && pX < 240 && pX > 80
										|| pY < 150) {
									mSpriteA2_15_3_1.setVisible(false);
									mSpriteA2_15_3_2.setVisible(true);
									mSpriteA2_15_3_2
											.registerEntityModifier(new SequenceEntityModifier(
													new MoveModifier(
															0.5f,
															mSpriteA2_15_3_1
																	.getX(),
															mSpriteA2_15_3_1
																	.getX() - 39.26f,
															60,
															-30,
															new IEntityModifierListener() {
																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15__JUMP
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																}
															}),
													new MoveXModifier(
															0.5f,
															mSpriteA2_15_3_1
																	.getX() - 39.26f,
															mSpriteA2_15_3_1
																	.getX() - 39.26f * 2,
															new IEntityModifierListener() {

																@Override
																public void onModifierStarted(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	OGG_A1_15_RAKUDAGAKU
																			.play();
																}

																@Override
																public void onModifierFinished(
																		IModifier<IEntity> arg0,
																		IEntity arg1) {
																	mSpriteA2_15_3_1
																			.setVisible(true);
																	mSpriteA2_15_3_2
																			.setVisible(false);
																	mSpriteA2_15_3_1
																			.setPosition(
																					mSpriteA2_15_3_1
																							.getX(),
																					60);
																	OGG_A1_15_RAKUDAGAKU
																			.stop();
												}
										})));
								}
							}

						}
					} else {

						if (mSpriteA2_5_1.isVisible()
								|| mSpriteA2_5_2.isVisible()) {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 200
									&& pX < 530 && pX > 360 || pY < 200
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_3_1.setVisible(false);
								mSpriteA2_15_3_2.setVisible(true);
								mSpriteA2_15_3_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_3_1.getX(),
														mSpriteA2_15_3_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {

															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_3_1.getX() - 39.26f,
														mSpriteA2_15_3_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																mSpriteA2_15_3_1
																		.setVisible(true);
																mSpriteA2_15_3_2
																		.setVisible(false);
																mSpriteA2_15_3_1
																		.setPosition(
																				mSpriteA2_15_3_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
															}
														})));
							}
						} else {
							if (pY < 250 && pX < 930 && pX > 750 || pY < 230
									&& pX < 530 && pX > 360 || pY < 230
									&& pX < 240 && pX > 80 || pY < 150) {
								mSpriteA2_15_3_1.setVisible(false);
								mSpriteA2_15_3_2.setVisible(true);
								mSpriteA2_15_3_2
										.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(
														0.5f,
														mSpriteA2_15_3_1.getX(),
														mSpriteA2_15_3_1.getX() - 39.26f,
														60,
														-30,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15__JUMP
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {

															}
														}),
												new MoveXModifier(
														0.5f,
														mSpriteA2_15_3_1.getX() - 39.26f,
														mSpriteA2_15_3_1.getX() - 39.26f * 2,
														new IEntityModifierListener() {

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																OGG_A1_15_RAKUDAGAKU
																		.play();
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																mSpriteA2_15_3_1
																		.setVisible(true);
																mSpriteA2_15_3_2
																		.setVisible(false);
																mSpriteA2_15_3_1
																		.setPosition(
																				mSpriteA2_15_3_1
																						.getX(),
																				60);
																OGG_A1_15_RAKUDAGAKU
																		.stop();
										}
								})));
							}
						}

					}
				}
			}
		}
		return false;
	}

	public void stopSound() {
		for (int i = 0; i < sound.length; i++) {
			if (sound[i] != null) {
				sound[i].stop();
			}
		}
	}

	public void a2_15_movex() {
		moveX = new LoopEntityModifier(new MoveXModifier(15.0f, 958, -220,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						checkMovieA2_15_(mSpriteA2_15_2_1);
						checkMovieA2_15_1(mSpriteA2_15_3_1);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
					}
				}), -1);
		mSpriteA2_15_1_1.registerEntityModifier(moveX);
	}

	public void checkSpriteLeft(final Sprite mSprite) {
		mSprite.registerEntityModifier(new DelayModifier(0.2f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						switch (randomA2_15_.nextInt(9)) {
						case 0:
							OGG_A1_BOY_SABO1.play();
							break;
						case 1:
							OGG_A1_BOY_SABO2.play();
							break;
						case 2:
							OGG_A1_BOY_SABO3.play();
							break;
						case 3:
							OGG_A1_BOY_SABO4.play();
							break;
						case 4:
							OGG_A1_BOY_SABO5.play();
							break;
						case 5:
							OGG_A1_BOY_SABO6.play();
							break;
						case 6:
							OGG_A1_BOY_SABO7.play();
							break;
						case 7:
							OGG_A1_BOY_SABO8.play();
							break;
						case 8:
							OGG_A1_BOY_SABO9.play();
							break;
						default:
							OGG_A1_BOY_SABO10.play();
							break;
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						if (mSprite.equals(mSpriteA2_5_1)) {
							mSpriteA2_5_1.setVisible(false);
							mAnimatedSpriteA2_5_.setVisible(true);
							OGG_A1_5_ODORI1.play();
							ifCountLeftMax(mAnimatedSpriteA2_5_);
							chooseLeft = -1;
						} else if (mSprite.equals(mSpriteA2_6_1)) {
							mSpriteA2_6_1.setVisible(false);
							mAnimatedSpriteA2_6_.setVisible(true);
							OGG_A1_6_ODORI2.play();
							ifCountLeftMax(mAnimatedSpriteA2_6_);
							chooseLeft = -1;
						} else {
							mSpriteA2_7_1.setVisible(false);
							mAnimatedSpriteA2_7_.setVisible(true);
							OGG_A1_7_ODORI5.play();
							ifCountLeftMax(mAnimatedSpriteA2_7_);
							chooseLeft = -1;
						}
					}
				}));
	}

	public void onClickVaoCayCoGai(final Sprite mSprite) {
		if (mSprite.isVisible() && mSprite.getAlpha() == 1.0f) {
			mSprite.clearEntityModifiers();
			mSprite.registerEntityModifier(new ParallelEntityModifier(
					new MoveModifier(1.5f, mSprite.getX(), mSprite.getX(),
							mSprite.getY(), mSprite.getY() + 250,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A1_7_1TOGENUKE2.play();
									if (mSprite.equals(mSpriteA2_5_7_1)
											|| mSprite.equals(mSpriteA2_5_7_2)
											|| mSprite.equals(mSpriteA2_5_7_3)
											|| mSprite.equals(mSpriteA2_5_7_4)
											|| mSprite.equals(mSpriteA2_5_7_5)) {
										if (mSprite.equals(mSpriteA2_5_7_1)
												|| mSprite
														.equals(mSpriteA2_5_7_2)) {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, -90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										} else {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, 90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										}
										if (mSpriteA2_5_1.isVisible()) {
											mSpriteA2_5_1.setVisible(false);
										}
										if (!mSpriteA2_5_2.isVisible()) {
											mSpriteA2_5_2.setVisible(true);
										}
									} else if (mSprite.equals(mSpriteA2_6_7_1)
											|| mSprite.equals(mSpriteA2_6_7_2)
											|| mSprite.equals(mSpriteA2_6_7_3)
											|| mSprite.equals(mSpriteA2_6_7_4)
											|| mSprite.equals(mSpriteA2_6_7_5)) {
										if (mSprite.equals(mSpriteA2_6_7_1)
												|| mSprite
														.equals(mSpriteA2_6_7_2)) {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, -90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										} else {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, 90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										}
										if (mSpriteA2_6_1.isVisible()) {
											mSpriteA2_6_1.setVisible(false);
										}
										if (!mSpriteA2_6_2.isVisible()) {
											mSpriteA2_6_2.setVisible(true);
										}
									} else {
										if (mSprite.equals(mSpriteA2_7_7_1)
												|| mSprite
														.equals(mSpriteA2_7_7_2)) {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, -90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										} else {
											mSprite.registerEntityModifier(new RotationAtModifier(
													0.5f, 0, 90, mSprite
															.getWidth(),
													mSprite.getHeight()));
										}
										if (mSpriteA2_7_1.isVisible()) {
											mSpriteA2_7_1.setVisible(false);
										}
										if (!mSpriteA2_7_2.isVisible()) {
											mSpriteA2_7_2.setVisible(true);
										}
									}
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									mSprite.setVisible(false);
									mSprite.registerEntityModifier(new RotationAtModifier(
											0.1f, 0, 0, mSprite.getWidth(),
											mSprite.getHeight()));
									mSprite.setPosition(mSprite.getmXFirst(),
											mSprite.getmYFirst());
									if (mSprite.equals(mSpriteA2_5_7_1)
											|| mSprite.equals(mSpriteA2_5_7_2)
											|| mSprite.equals(mSpriteA2_5_7_3)
											|| mSprite.equals(mSpriteA2_5_7_4)
											|| mSprite.equals(mSpriteA2_5_7_5)) {
										if (!mSpriteA2_5_1.isVisible()) {
											mSpriteA2_5_1.setVisible(true);
										}
										if (mSpriteA2_5_2.isVisible()) {
											mSpriteA2_5_2.setVisible(false);
										}
										countOnClickLeft = countOnClickLeft + 1;
										if (countOnClickLeft == 5) {
											checkSpriteLeft(mSpriteA2_5_1);
										}
									} else if (mSprite.equals(mSpriteA2_6_7_1)
											|| mSprite.equals(mSpriteA2_6_7_2)
											|| mSprite.equals(mSpriteA2_6_7_3)
											|| mSprite.equals(mSpriteA2_6_7_4)
											|| mSprite.equals(mSpriteA2_6_7_5)) {
										if (!mSpriteA2_6_1.isVisible()) {
											mSpriteA2_6_1.setVisible(true);
										}
										if (mSpriteA2_6_2.isVisible()) {
											mSpriteA2_6_2.setVisible(false);
										}
										countOnClickLeft = countOnClickLeft + 1;
										if (countOnClickLeft == 5) {
											checkSpriteLeft(mSpriteA2_6_1);
										}
									} else {
										if (!mSpriteA2_7_1.isVisible()) {
											mSpriteA2_7_1.setVisible(true);
										}
										if (mSpriteA2_7_2.isVisible()) {
											mSpriteA2_7_2.setVisible(false);
										}
										countOnClickLeft = countOnClickLeft + 1;
										if (countOnClickLeft == 5) {
											checkSpriteLeft(mSpriteA2_7_1);
										}
									}
								}
							}), new AlphaModifier(1.5f, 1.0f, 0.0f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									mSprite.setAlpha(1.0f);
								}
							})));
			}
	}

	public void onClickA2_8_(final Sprite mSprite) {
		if (mSpriteA2_8_1.isVisible() 
				|| mSpriteA2_8_2.isVisible()) {
			mSpriteA2_8_1.registerEntityModifier(new AlphaModifier(0.5f, 1.0f,
					1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (mSpriteA2_8_1.isVisible()) {
								mSpriteA2_8_1.setVisible(false);
							}
							if (!mSpriteA2_8_2.isVisible()) {
								mSpriteA2_8_2.setVisible(true);
							}
							OGG_A1_8_HANA1.play();
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (!mSpriteA2_8_1.isVisible()) {
								mSpriteA2_8_1.setVisible(true);
							}
							if (mSpriteA2_8_2.isVisible()) {
								mSpriteA2_8_2.setVisible(false);
							}
							checkSpriteRight(mSpriteA2_8_1);
						}
					}));
			if (mSprite.equals(mSpriteA2_8_7_1)) {
				mSpriteA2_8_7_1.setVisible(false);
				mSpriteA2_8_8_1.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_8_7_2)) {
				mSpriteA2_8_7_2.setVisible(false);
				mSpriteA2_8_8_2.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_8_7_3)) {
				mSpriteA2_8_7_3.setVisible(false);
				mSpriteA2_8_8_3.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_8_7_4)) {
				mSpriteA2_8_7_4.setVisible(false);
				mSpriteA2_8_8_4.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else {
				mSpriteA2_8_7_5.setVisible(false);
				mSpriteA2_8_8_5.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			}
		}
	}

	public void onClickA2_9_(final Sprite mSprite) {
		if (mSpriteA2_9_1.isVisible() || mSpriteA2_9_2.isVisible()) {
			mSpriteA2_9_1.registerEntityModifier(new AlphaModifier(0.5f, 1.0f,
					1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (mSpriteA2_9_1.isVisible()) {
								mSpriteA2_9_1.setVisible(false);
							}
							if (!mSpriteA2_9_2.isVisible()) {
								mSpriteA2_9_2.setVisible(true);
							}
							OGG_A1_9_HANA2.play();
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (!mSpriteA2_9_1.isVisible()) {
								mSpriteA2_9_1.setVisible(true);
							}
							if (mSpriteA2_9_2.isVisible()) {
								mSpriteA2_9_2.setVisible(false);
							}
							checkSpriteRight(mSpriteA2_9_1);
						}
					}));
			if (mSprite.equals(mSpriteA2_9_7_1)) {
				mSpriteA2_9_7_1.setVisible(false);
				mSpriteA2_9_8_1.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_9_7_2)) {
				mSpriteA2_9_7_2.setVisible(false);
				mSpriteA2_9_8_2.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_9_7_3)) {
				mSpriteA2_9_7_3.setVisible(false);
				mSpriteA2_9_8_3.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_9_7_4)) {
				mSpriteA2_9_7_4.setVisible(false);
				mSpriteA2_9_8_4.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else {
				mSpriteA2_9_7_5.setVisible(false);
				mSpriteA2_9_8_5.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			}
		}
	}

	public void onClickA2_10_(final Sprite mSprite) {
		if (mSpriteA2_10_1.isVisible() || mSpriteA2_10_2.isVisible()) {
			mSpriteA2_10_1.registerEntityModifier(new AlphaModifier(0.5f, 1.0f,
					1.0f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (mSpriteA2_10_1.isVisible()) {
								mSpriteA2_10_1.setVisible(false);
							}
							if (!mSpriteA2_10_2.isVisible()) {
								mSpriteA2_10_2.setVisible(true);
							}
							OGG_A1_10_HANA3.play();
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							if (!mSpriteA2_10_1.isVisible()) {
								mSpriteA2_10_1.setVisible(true);
							}
							if (mSpriteA2_10_2.isVisible()) {
								mSpriteA2_10_2.setVisible(false);
							}
							checkSpriteRight(mSpriteA2_10_1);
						}
					}));
			if (mSprite.equals(mSpriteA2_10_7_1)) {
				mSpriteA2_10_7_1.setVisible(false);
				mSpriteA2_10_8_1.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_10_7_2)) {
				mSpriteA2_10_7_2.setVisible(false);
				mSpriteA2_10_8_2.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_10_7_3)) {
				mSpriteA2_10_7_3.setVisible(false);
				mSpriteA2_10_8_3.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else if (mSprite.equals(mSpriteA2_10_7_4)) {
				mSpriteA2_10_7_4.setVisible(false);
				mSpriteA2_10_8_4.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			} else {
				mSpriteA2_10_7_5.setVisible(false);
				mSpriteA2_10_8_5.setVisible(true);
				countOnClickRight = countOnClickRight + 1;
			}
		}
	}

	public void checkSpriteRight(final Sprite mSprite) {
		if (countOnClickRight == 5) {
			switch (randomA2_15_.nextInt(7)) {
			case 0:
				OGG_A1_GIRL_SABO1.play();
				break;
			case 1:
				OGG_A1_GIRL_SABO2.play();
				break;
			case 2:
				OGG_A1_GIRL_SABO3.play();
				break;
			case 3:
				OGG_A1_GIRL_SABO4.play();
				break;
			case 4:
				OGG_A1_GIRL_SABO5.play();
				break;
			case 5:
				OGG_A1_GIRL_SABO6.play();
				break;
			default:
				OGG_A1_GIRL_SABO7.play();
				break;
			}
			mSprite.setVisible(false);
			if (mSprite.equals(mSpriteA2_8_1)) {
				saoNhapNhay(mSpriteA2_8_9);
			} else if (mSprite.equals(mSpriteA2_9_1)) {
				saoNhapNhay(mSpriteA2_9_9);
			} else {
				saoNhapNhay(mSpriteA2_10_9);
			}
		}
	}

	public void saoNhapNhay(final Sprite mSprite) {
		mSprite.clearEntityModifiers();
		mSprite.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.25f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(true);
						if (mSprite.equals(mSpriteA2_8_9)) {
							mSpriteA2_8_1.setVisible(false);
							mSpriteA2_8_2.setVisible(true);
						} else if (mSprite.equals(mSpriteA2_9_9)) {
							mSpriteA2_9_1.setVisible(false);
							mSpriteA2_9_2.setVisible(true);
						} else {
							mSpriteA2_10_1.setVisible(false);
							mSpriteA2_10_2.setVisible(true);
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(false);
					}
				}), new DelayModifier(0.25f), new DelayModifier(0.25f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								mSprite.setVisible(true);
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								mSprite.setVisible(false);
								if (mSprite.equals(mSpriteA2_8_9)) {
									mSpriteA2_8_1.setVisible(true);
									mSpriteA2_8_2.setVisible(false);
								} else if (mSprite.equals(mSpriteA2_9_9)) {
									mSpriteA2_9_1.setVisible(true);
									mSpriteA2_9_2.setVisible(false);
								} else {
									mSpriteA2_10_1.setVisible(true);
									mSpriteA2_10_2.setVisible(false);
								}
							}
						}), new DelayModifier(0.25f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								if (mSprite.equals(mSpriteA2_8_9)) {
									mSpriteA2_8_1.setVisible(false);
									OGG_A1_8_ODORI3.play();
									ifCountRightMax(mAnimatedSpriteA2_8_);
									chooseRight = -1;
								} else if (mSprite.equals(mSpriteA2_9_9)) {
									mSpriteA2_9_1.setVisible(false);
									OGG_A1_9_ODORI4.play();
									ifCountRightMax(mAnimatedSpriteA2_9_);
									chooseRight = -1;
								} else {
									mSpriteA2_10_1.setVisible(false);
									OGG_A1_10_ODORI6.play();
									ifCountRightMax(mAnimatedSpriteA2_10_);
									chooseRight = -1;
								}
								for (int i = 0; i < 15; i++) {
									mArraySpriteFlower[i].setVisible(false);
								}
							}
						})));
	}

	public void ifCountLeftMax(final AnimatedSprite mAnimatedSprite) {
		mAnimatedSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveYModifier(0.7f / 2, 120, 50),
						new MoveYModifier(0.7f / 2, 50, 120)), 4));
		mAnimatedSprite.animate(700, false, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				mAnimatedSprite.setVisible(false);
				if (mAnimatedSprite.equals(mAnimatedSpriteA2_5_)) {
					mSpriteA2_5_1.setVisible(true);
					mSpriteA2_5_1.registerEntityModifier(new MoveYModifier(
							1.0f, mSpriteA2_5_1.getY(),
							mSpriteA2_5_1.getY() - 600,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A1_4_2_PYU.play();
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									mSpriteA2_5_1.setVisible(false);
									mSpriteA2_5_1.setPosition(
											mSpriteA2_5_1.getmXFirst(),
											mSpriteA2_5_1.getmYFirst());
									countOnClickLeft = 0;
									a2_5_1_MoveYLeft();
									OGG_A1_4_2_PYU.stop();
								}
							}));
				} else if (mAnimatedSprite.equals(mAnimatedSpriteA2_6_)) {
					mSpriteA2_6_1.setVisible(true);
					mSpriteA2_6_1.registerEntityModifier(new MoveYModifier(
							1.0f, mSpriteA2_6_1.getY(),
							mSpriteA2_6_1.getY() - 600,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A1_4_2_PYU.play();
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									mSpriteA2_6_1.setVisible(false);
									mSpriteA2_6_1.setPosition(
											mSpriteA2_6_1.getmXFirst(),
											mSpriteA2_6_1.getmYFirst());
									countOnClickLeft = 0;
									a2_5_1_MoveYLeft();
									OGG_A1_4_2_PYU.stop();
								}
							}));
				} else {
					mSpriteA2_7_1.setVisible(true);
					mSpriteA2_7_1.registerEntityModifier(new MoveYModifier(
							1.0f, mSpriteA2_7_1.getY(),
							mSpriteA2_7_1.getY() - 600,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									OGG_A1_4_2_PYU.play();
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									mSpriteA2_7_1.setVisible(false);
									mSpriteA2_7_1.setPosition(
											mSpriteA2_7_1.getmXFirst(),
											mSpriteA2_7_1.getmYFirst());
									countOnClickLeft = 0;
									a2_5_1_MoveYLeft();
									OGG_A1_4_2_PYU.stop();
								}
							}));
				}
			}
		});
	}

	public void ifCountRightMax(final AnimatedSprite mAnimatedSprite) {
		mAnimatedSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveYModifier(0.7f / 2, 120, 60),
						new MoveYModifier(0.7f / 2, 60, 120)), 4));
		mAnimatedSprite.animate(700, false, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mSpriteA2_8_9.setVisible(false);
				mSpriteA2_9_9.setVisible(false);
				mSpriteA2_10_9.setVisible(false);
				mAnimatedSprite.setVisible(true);
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				mAnimatedSprite.setVisible(false);
				if (mAnimatedSprite.equals(mAnimatedSpriteA2_8_)) {
					if (!mSpriteA2_8_1.isVisible()) {
						mSpriteA2_8_1.setVisible(true);
					}
					for (int i = 0; i < 5; i++) {
						mArraySpriteFlower[i].setVisible(true);
						sulyhoabaylen(mArraySpriteFlower[i]);
					}
					sulycaysuongrongbaylen(mSpriteA2_8_1);

				} else if (mAnimatedSprite.equals(mAnimatedSpriteA2_9_)) {
					if (!mSpriteA2_9_1.isVisible()) {
						mSpriteA2_9_1.setVisible(true);
					}
					for (int i = 5; i < 10; i++) {
						mArraySpriteFlower[i].setVisible(true);
						sulyhoabaylen(mArraySpriteFlower[i]);
					}
					sulycaysuongrongbaylen(mSpriteA2_9_1);

				} else {
					if (!mSpriteA2_10_1.isVisible()) {
						mSpriteA2_10_1.setVisible(true);
					}
					for (int i = 10; i < 15; i++) {
						mArraySpriteFlower[i].setVisible(true);
						sulyhoabaylen(mArraySpriteFlower[i]);
					}
					sulycaysuongrongbaylen(mSpriteA2_10_1);
				}
			}
		});
	}

	public void sulycaysuongrongbaylen(final Sprite mSprite) {
		mSprite.clearEntityModifiers();
		mSprite.registerEntityModifier(new MoveYModifier(1.0f, mSprite.getY(),
				mSprite.getY() - 638, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						OGG_A1_4_2_PYU.play();
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(false);
						mSprite.setPosition(mSprite.getmXFirst(),
								mSprite.getmYFirst());
						countOnClickRight = 0;
						a2_5_1_MoveYRight();
						OGG_A1_4_2_PYU.stop();
					}
				}));
	}

	public void sulyhoabaylen(final Sprite mSprite) {
		mSprite.clearEntityModifiers();
		mSprite.registerEntityModifier(new MoveYModifier(1.0f,
				mSprite.getY() + 20, mSprite.getY() - 638,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(false);
						mSprite.setPosition(mSprite.getmXFirst(),
								mSprite.getmYFirst());
					}
				}));
	}

	public void processA2_1_1_Left() {
		mSpriteA2_1_2_Left.setVisible(true);
		mSpriteA2_1_2_Left.registerEntityModifier(new MoveYModifier(1.0f, 408,
				270));
		mSpriteA2_1_1_Left.setVisible(true);
		mSpriteA2_1_1_Left.registerEntityModifier(new MoveYModifier(1.0f, 408,
				270, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						checkOnClick = false;
						mSpriteA2_1_2_Left.setAlpha(1.0f);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSpriteA2_1_2_Left
								.registerEntityModifier(new ParallelEntityModifier(
										new MoveYModifier(1.0f, 270, 408),
										new AlphaModifier(1.0f, 1.0f, 0.0f)));
						mSpriteA2_1_1_Left
								.registerEntityModifier(new ParallelEntityModifier(
										new MoveYModifier(1.0f, 270, 408),
										new AlphaModifier(1.0f, 1.0f, 0.0f,
												new IEntityModifierListener() {
													@Override
													public void onModifierStarted(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														OGG_A1_1_1_ZAZA.play();
														mSpriteA2_1_2_Left
																.setAlpha(1.0f);
													}

													@Override
													public void onModifierFinished(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														mSpriteA2_1_2_Left
																.setVisible(false);
														mSpriteA2_1_2_Left
																.setAlpha(1.0f);
														mSpriteA2_1_1_Left
																.setVisible(false);
														mSpriteA2_1_1_Left
																.setAlpha(1.0f);
														if (!mSpriteA2_1_1_Right
																.isVisible()) {
															checkOnClick = true;
														}
														OGG_A1_1_1_ZAZA.stop();
													}
												})));
					}
				}));
	}

	public void processa2_1_1Right() {
		mSpriteA2_1_2_Right.setVisible(true);
		mSpriteA2_1_2_Right.registerEntityModifier(new MoveYModifier(1.0f, 438,
				336));
		mSpriteA2_1_1_Right.setVisible(true);
		mSpriteA2_1_1_Right.registerEntityModifier(new MoveYModifier(1.0f, 438,
				336, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						checkOnClick = false;
						mSpriteA2_1_2_Right.setAlpha(1.0f);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSpriteA2_1_2_Right
								.registerEntityModifier(new ParallelEntityModifier(
										new MoveYModifier(1.0f, 336, 438),
										new AlphaModifier(1.0f, 1.0f, 0.0f)));
						mSpriteA2_1_1_Right
								.registerEntityModifier(new ParallelEntityModifier(
										new MoveYModifier(1.0f, 336, 438,
												new IEntityModifierListener() {
													@Override
													public void onModifierStarted(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														OGG_A1_1_1_ZAZA.play();
														mSpriteA2_1_2_Right
																.setAlpha(1.0f);
													}

													@Override
													public void onModifierFinished(
															IModifier<IEntity> arg0,
															IEntity arg1) {
														mSpriteA2_1_2_Right
																.setVisible(false);
														mSpriteA2_1_2_Right
																.setAlpha(1.0f);
														mSpriteA2_1_1_Right
																.setVisible(false);
														mSpriteA2_1_1_Right
																.setAlpha(1.0f);
														if (!mSpriteA2_1_1_Left
																.isVisible()) {
															checkOnClick = true;
														}
														OGG_A1_1_1_ZAZA.stop();
													}
												}), new AlphaModifier(1.0f,
												1.0f, 0.0f)));
					}
				}));
	}

	public void setVisibleRunningGameLeft(final Sprite mSpriteLeft, final int i) {
		mSpriteLeft.setVisible(true);
		mSpriteLeft.registerEntityModifier(new MoveYModifier(1.0f, 258, 120,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						OGG_A1_4_1_SABOSYUTUGEN.play();
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						if (i == 0) {
							mSpriteA2_5_7_1.setVisible(true);
							mSpriteA2_5_7_2.setVisible(true);
							mSpriteA2_5_7_3.setVisible(true);
							mSpriteA2_5_7_4.setVisible(true);
							mSpriteA2_5_7_5.setVisible(true);
						} else if (i == 1) {
							mSpriteA2_6_7_1.setVisible(true);
							mSpriteA2_6_7_2.setVisible(true);
							mSpriteA2_6_7_3.setVisible(true);
							mSpriteA2_6_7_4.setVisible(true);
							mSpriteA2_6_7_5.setVisible(true);
						} else {
							mSpriteA2_7_7_1.setVisible(true);
							mSpriteA2_7_7_2.setVisible(true);
							mSpriteA2_7_7_3.setVisible(true);
							mSpriteA2_7_7_4.setVisible(true);
							mSpriteA2_7_7_5.setVisible(true);
						}
						OGG_A1_4_1_SABOSYUTUGEN.stop();
					}
				}));
	}

	public void setVisibleRunningGameRight(final Sprite mSprite, final int i) {
		mSprite.setVisible(true);
		mSprite.registerEntityModifier(new MoveYModifier(1.0f, 258, 156,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						Log.d(LOG,
								"NHAC NEN CAY XUONG RONG BEN PHAI XUAT HIEN     --------------");
						OGG_A1_4_1_SABOSYUTUGEN.play();
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						OGG_A1_4_1_SABOSYUTUGEN.stop();
						if (i == 0) {
							mSpriteA2_8_7_1.setVisible(true);
							mSpriteA2_8_7_2.setVisible(true);
							mSpriteA2_8_7_3.setVisible(true);
							mSpriteA2_8_7_4.setVisible(true);
							mSpriteA2_8_7_5.setVisible(true);
						} else if (i == 1) {
							mSpriteA2_9_7_1.setVisible(true);
							mSpriteA2_9_7_2.setVisible(true);
							mSpriteA2_9_7_3.setVisible(true);
							mSpriteA2_9_7_4.setVisible(true);
							mSpriteA2_9_7_5.setVisible(true);
						} else {
							mSpriteA2_10_7_1.setVisible(true);
							mSpriteA2_10_7_2.setVisible(true);
							mSpriteA2_10_7_3.setVisible(true);
							mSpriteA2_10_7_4.setVisible(true);
							mSpriteA2_10_7_5.setVisible(true);
						}
					}
				}));
	}

	public void a2_5_1_MoveYLeft() {
		if (!mSpriteA2_5_1.isVisible() && !mSpriteA2_6_1.isVisible()
				&& !mSpriteA2_7_1.isVisible()) {
			if (checkChooseLeft == 0) {
				chooseLeft = randomA2_5_.nextInt(3);
			} else if (checkChooseLeft == 1) {
				chooseLeft = randomA2_5_.nextInt(2);
				chooseLeft = chooseLeft + 1;
			} else if (checkChooseLeft == 2) {
				chooseLeft = randomA2_5_.nextInt(2);
				if (chooseLeft == 1) {
					chooseLeft = chooseLeft + 1;
				}
			} else {
				chooseLeft = randomA2_5_.nextInt(2);
			}
			switch (chooseLeft) {
			case 0:
				processA2_1_1_Left();
				setVisibleRunningGameLeft(mSpriteA2_5_1, 0);
				checkChooseLeft = 1;
				break;
			case 1:
				processA2_1_1_Left();
				setVisibleRunningGameLeft(mSpriteA2_6_1, 1);
				checkChooseLeft = 2;
				break;
			default:
				processA2_1_1_Left();
				setVisibleRunningGameLeft(mSpriteA2_7_1, 2);
				checkChooseLeft = 3;
				break;
			}
			if (mArraySprite != null) {
				for (int i = 0; i < 15; i++) {
					timeHandlerMoveSpriteLeft(mArraySprite[i]);
				}
			}
		}
	}

	public void a2_5_1_MoveYRight() {
		if (!mSpriteA2_8_1.isVisible() && !mSpriteA2_9_1.isVisible()
				&& !mSpriteA2_10_1.isVisible()) {
			if (checkChooseRight == 0) {
				chooseRight = randomA2_5_.nextInt(3);
			} else if (checkChooseRight == 1) {
				chooseRight = randomA2_5_.nextInt(2);
				chooseRight = chooseRight + 1;
			} else if (checkChooseRight == 2) {
				chooseRight = randomA2_5_.nextInt(2);
				if (chooseRight == 1) {
					chooseRight = chooseRight + 1;
				}
			} else {
				chooseRight = randomA2_5_.nextInt(2);
			}
			switch (chooseRight) {
			case 0:
				processa2_1_1Right();
				setVisibleRunningGameRight(mSpriteA2_8_1, 0);
				checkChooseRight = 1;
				break;
			case 1:
				processa2_1_1Right();
				setVisibleRunningGameRight(mSpriteA2_9_1, 1);
				checkChooseRight = 2;
				break;
			default:
				processa2_1_1Right();
				setVisibleRunningGameRight(mSpriteA2_10_1, 2);
				checkChooseRight = 3;
				break;
			}
			if (mArraySprite != null) {
				for (int i = 15; i < mArraySprite.length; i++) {
					timeHandlerMoveSpriteRight(mArraySprite[i]);
				}
			}
		}
	}

	public void processOnClickA2_2_1(final Sprite mSprite) {
		mSprite.setPosition(53, 398);
		mSprite.setVisible(true);
		mSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new RotationAtModifier(0.2f, 0, 10,
						mSprite.getWidth() / 2, mSprite.getHeight()),
						new RotationAtModifier(0.4f, 10, -10, mSprite
								.getWidth() / 2, mSprite.getHeight()),
						new RotationAtModifier(0.2f, -10, 0,
								mSprite.getWidth() / 2, mSprite.getHeight())),
				1, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSpriteA2_2_1.setVisible(false);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(false);
						mSpriteA2_2_1.setVisible(true);
					}
				}));
	}

	public void processOnClickA2_2_2(final Sprite mSprite) {
		mSprite.setPosition(702, 407);
		mSprite.setVisible(true);
		mSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new RotationAtModifier(0.2f, 0, 10,
						mSprite.getWidth() / 2, mSprite.getHeight()),
						new RotationAtModifier(0.4f, 10, -10, mSprite
								.getWidth() / 2, mSprite.getHeight()),
						new RotationAtModifier(0.2f, -10, 0,
								mSprite.getWidth() / 2, mSprite.getHeight())),
				1, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSpriteA2_2_2.setVisible(false);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setVisible(false);
						mSpriteA2_2_2.setVisible(true);
					}
				}));
	}

	public void checkVisibleA2_14_3() {
		if (mSpriteA2_14_3.isVisible()) {
			mSpriteA2_14_3.unregisterUpdateHandler(timerHandlerA2_14_3);
			timerHandlerA2_14_3 = new TimerHandler(3.0f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mSpriteA2_14_3.setVisible(false);
					mSpriteA2_14_1.setVisible(true);
					mSpriteA2_13_1.setVisible(true);
					mSpriteA2_13_2.setVisible(true);
				}
			});
			mSpriteA2_14_3.registerUpdateHandler(timerHandlerA2_14_3);
		}
	}

	public void checkMovieA2_15_(final Sprite mSprite) {
		if (mSprite.isVisible()) {
			mSprite.unregisterUpdateHandler(timerHandlerA2_15_);
			timerHandlerA2_15_ = new TimerHandler(4.8f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mSprite.registerEntityModifier(new LoopEntityModifier(
							new MoveXModifier(15.0f, 958, -220), -1));
				}
			});
			mSprite.registerUpdateHandler(timerHandlerA2_15_);
		}
	}

	public void checkMovieA2_15_1(final Sprite mSprite) {
		if (mSprite.isVisible()) {
			mSprite.unregisterUpdateHandler(timerHandlerA2_15_1);
			timerHandlerA2_15_1 = new TimerHandler(9.6f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mSprite.registerEntityModifier(new LoopEntityModifier(
							new MoveXModifier(15.0f, 958, -220), -1));
				}
			});
			mSprite.registerUpdateHandler(timerHandlerA2_15_1);
		}
	}

	public void timeHandlerMoveSpriteLeft(final Sprite mSprite) {
		mSpriteA2_11.reset();
		mLoopEntity = new LoopEntityModifier(new MoveXModifier(3.2f, 59, 59,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						if (mSprite.isVisible() && mSprite.getAlpha() == 1) {
							mSprite.clearEntityModifiers();
							mSprite.registerEntityModifier(new LoopEntityModifier(
									new SequenceEntityModifier(
											new RotationAtModifier(0.15f, 0, 5,
													mSprite.getWidth() / 2,
													mSprite.getHeight()),
											new RotationAtModifier(0.3f, 5, -5,
													mSprite.getWidth() / 2,
													mSprite.getHeight()),
											new RotationAtModifier(0.15f, -5,
													0, mSprite.getWidth() / 2,
													mSprite.getHeight())), 2));
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setPosition(mSprite.getmXFirst(),
								mSprite.getmYFirst());
					}
				}), -1);
		mSpriteA2_11.registerEntityModifier(mLoopEntity);
	}

	public void timeHandlerMoveSpriteRight(final Sprite mSprite) {
		mSpriteA2_12.reset();
		mLoopEntity = new LoopEntityModifier(new MoveXModifier(3.2f, -25, -25,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						if (mSprite.isVisible()) {
							mSprite.clearEntityModifiers();
							mSprite.registerEntityModifier(new LoopEntityModifier(
									new SequenceEntityModifier(
											new RotationAtModifier(0.15f, 0, 5,
													mSprite.getWidth() / 2,
													mSprite.getHeight()),
											new RotationAtModifier(0.3f, 5, -5,
													mSprite.getWidth() / 2,
													mSprite.getHeight()),
											new RotationAtModifier(0.15f, -5,
													0, mSprite.getWidth() / 2,
													mSprite.getHeight())), 2));
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mSprite.setPosition(mSprite.getmXFirst(),
								mSprite.getmYFirst());
					}
				}), -1);
		mSpriteA2_12.registerEntityModifier(mLoopEntity);
	}

	public void theCloudMoveY(final Sprite mSprite) {
		mSprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveYModifier(1.0f, mSprite
						.getmYFirst(), mSprite.getmYFirst() - 8),
						new MoveYModifier(2.0f, mSprite.getmYFirst() - 8,
								mSprite.getmYFirst() + 8), new MoveYModifier(
								1.0f, mSprite.getmYFirst() + 8, mSprite
										.getmYFirst())), -1));
	}

	@Override
	public synchronized void onResumeGame() {
		nore = new RanDomNoRepeat();
		nore.setLenghNoRepeat(0, 4);
		mArraySprite = new Sprite[] { mSpriteA2_5_7_1, mSpriteA2_5_7_2,
				mSpriteA2_5_7_3, mSpriteA2_5_7_4, mSpriteA2_5_7_5,
				mSpriteA2_6_7_1, mSpriteA2_6_7_2, mSpriteA2_6_7_3,
				mSpriteA2_6_7_4, mSpriteA2_6_7_5, mSpriteA2_7_7_1,
				mSpriteA2_7_7_2, mSpriteA2_7_7_3, mSpriteA2_7_7_4,
				mSpriteA2_7_7_5, mSpriteA2_8_7_1, mSpriteA2_8_7_2,
				mSpriteA2_8_7_3, mSpriteA2_8_7_4, mSpriteA2_8_7_5,
				mSpriteA2_9_7_1, mSpriteA2_9_7_2, mSpriteA2_9_7_3,
				mSpriteA2_9_7_4, mSpriteA2_9_7_5, mSpriteA2_10_7_1,
				mSpriteA2_10_7_2, mSpriteA2_10_7_3, mSpriteA2_10_7_4,
				mSpriteA2_10_7_5 };
		mArraySpriteFlower = new Sprite[] { mSpriteA2_8_8_1, mSpriteA2_8_8_2,
				mSpriteA2_8_8_3, mSpriteA2_8_8_4, mSpriteA2_8_8_5,
				mSpriteA2_9_8_1, mSpriteA2_9_8_2, mSpriteA2_9_8_3,
				mSpriteA2_9_8_4, mSpriteA2_9_8_5, mSpriteA2_10_8_1,
				mSpriteA2_10_8_2, mSpriteA2_10_8_3, mSpriteA2_10_8_4,
				mSpriteA2_10_8_5 };
		sound = new Sound[] { OGG_A1_10_HANA3, OGG_A1_10_ODORI6,
				OGG_A1_14_1_KUMOHARE, OGG_A1_14_1_WINK,
				OGG_A1_14_2_TAIYOUTAUCH, OGG_A1_15_RAKUDAGAKU, OGG_A1_15__JUMP,
				OGG_A1_1_1_ZAZA, OGG_A1_3_1_ZA1, OGG_A1_3_2_ZA2,
				OGG_A1_3_3_ZA3, OGG_A1_3_4_ZA4, OGG_A1_4_1_SABOSYUTUGEN,
				OGG_A1_4_2_PYU, OGG_A1_5_ODORI1, OGG_A1_6_ODORI2,
				OGG_A1_7_1TOGENUKE2, OGG_A1_7_ODORI5, OGG_A1_8_HANA1,
				OGG_A1_8_ODORI3, OGG_A1_9_HANA2, OGG_A1_9_ODORI4,
				OGG_A1_BOY_SABO1, OGG_A1_BOY_SABO2, OGG_A1_BOY_SABO3,
				OGG_A1_BOY_SABO4, OGG_A1_BOY_SABO5, OGG_A1_BOY_SABO6,
				OGG_A1_BOY_SABO7, OGG_A1_BOY_SABO8, OGG_A1_BOY_SABO9,
				OGG_A1_BOY_SABO10, OGG_A1_GIRL_SABO1, OGG_A1_GIRL_SABO2,
				OGG_A1_GIRL_SABO3, OGG_A1_GIRL_SABO4, OGG_A1_GIRL_SABO5,
				OGG_A1_GIRL_SABO6, OGG_A1_GIRL_SABO7 };
		checkOnClick = true;
		checkSunOnclick = true;
		countOnClickLeft = 0;
		countOnClickRight = 0;
		checkChooseRight = 0;
		checkChooseLeft = 0;
		chooseLeft = -1;
		chooseRight = -1;
		randomA2_5_ = new Random();
		randomA2_15_ = new Random();
		mLoopEntity = null;
		a2_5_1_MoveYLeft();
		a2_5_1_MoveYRight();
		a2_15_movex();
		timerHandlerA2_14_3 = null;
		if (mArraySprite != null) {
			for (int i = 0; i < mArraySprite.length; i++) {
				if (i < 15) {
					timeHandlerMoveSpriteLeft(mArraySprite[i]);
				} else {
					timeHandlerMoveSpriteRight(mArraySprite[i]);
				}
			}
		}
		theCloudMoveY(mSpriteA2_16_1);
		theCloudMoveY(mSpriteA2_16_2);
		theCloudMoveY(mSpriteA2_16_3);
		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		resetData();
		stopSound();
		super.onPauseGame();
	}

	public void resetData() {

		if (mSpriteA2_1_1_Left != null) {
			mSpriteA2_1_1_Left.setAlpha(1.0f);
			mSpriteA2_1_1_Left.clearEntityModifiers();
			mSpriteA2_1_1_Left.setPosition(187, 408);
			mSpriteA2_1_1_Left.setVisible(false);
		}
		if (mSpriteA2_1_2_Left != null) {
			mSpriteA2_1_2_Left.setAlpha(1.0f);
			mSpriteA2_1_2_Left.clearEntityModifiers();
			mSpriteA2_1_2_Left.setPosition(337, 408);
			mSpriteA2_1_2_Left.setVisible(false);
		}
		if (mSpriteA2_1_1_Right != null) {
			mSpriteA2_1_1_Right.setAlpha(1.0f);
			mSpriteA2_1_1_Right.clearEntityModifiers();
			mSpriteA2_1_1_Right.setPosition(596, 408);
			mSpriteA2_1_1_Right.setVisible(false);
		}
		if (mSpriteA2_1_2_Right != null) {
			mSpriteA2_1_2_Right.setAlpha(1.0f);
			mSpriteA2_1_2_Right.clearEntityModifiers();
			mSpriteA2_1_2_Right.setPosition(746, 408);
			mSpriteA2_1_2_Right.setVisible(false);
		}
		if (mSpriteA2_15_1_1 != null) {
			mSpriteA2_15_1_1.clearEntityModifiers();
			mSpriteA2_15_1_1.setPosition(960, 60);
			mSpriteA2_15_1_1.setVisible(true);
		}
		if (mSpriteA2_15_1_2 != null) {
			mSpriteA2_15_1_2.clearEntityModifiers();
			mSpriteA2_15_1_2.setPosition(960, 60);
			mSpriteA2_15_1_2.setVisible(false);
		}
		if (mSpriteA2_15_2_1 != null) {
			mSpriteA2_15_2_1.clearEntityModifiers();
			mSpriteA2_15_2_1.setPosition(960, 60);
			mSpriteA2_15_2_1.setVisible(true);
		}
		if (mSpriteA2_15_2_2 != null) {
			mSpriteA2_15_2_2.clearEntityModifiers();
			mSpriteA2_15_2_2.setPosition(960, 60);
			mSpriteA2_15_2_2.setVisible(false);
		}
		if (mSpriteA2_15_3_1 != null) {
			mSpriteA2_15_3_1.clearEntityModifiers();
			mSpriteA2_15_3_1.setPosition(960, 60);
			mSpriteA2_15_3_1.setVisible(true);
		}
		if (mSpriteA2_15_3_2 != null) {
			mSpriteA2_15_3_2.clearEntityModifiers();
			mSpriteA2_15_3_2.setPosition(960, 60);
			mSpriteA2_15_3_2.setVisible(false);
		}
		if (mSpriteA2_14_1 != null) {
			mSpriteA2_14_1.clearEntityModifiers();
			mSpriteA2_14_1.setVisible(true);
		}
		if (mSpriteA2_14_2 != null) {
			mSpriteA2_14_2.clearEntityModifiers();
			mSpriteA2_14_2.setVisible(false);
		}
		if (mSpriteA2_14_3 != null) {
			mSpriteA2_14_3.clearUpdateHandlers();
			mSpriteA2_14_3.clearEntityModifiers();
			mSpriteA2_14_3.setVisible(false);
		}
		if (mSpriteA2_3_1 != null) {
			mSpriteA2_3_1.clearEntityModifiers();
			mSpriteA2_3_1.setPosition(53, 398);
			mSpriteA2_3_1.setVisible(false);
		}

		if (mSpriteA2_3_2 != null) {
			mSpriteA2_3_2.clearEntityModifiers();
			mSpriteA2_3_2.setPosition(53, 398);
			mSpriteA2_3_2.setVisible(false);
		}
		if (mSpriteA2_3_3 != null) {
			mSpriteA2_3_3.clearEntityModifiers();
			mSpriteA2_3_3.setPosition(53, 398);
			mSpriteA2_3_3.setVisible(false);
		}
		if (mSpriteA2_3_4 != null) {
			mSpriteA2_3_4.clearEntityModifiers();
			mSpriteA2_3_4.setPosition(53, 398);
			mSpriteA2_3_4.setVisible(false);
		}

		for (int i = 0; i < mArraySprite.length; i++) {
			mArraySprite[i].clearEntityModifiers();
			mArraySprite[i].clearUpdateHandlers();
			mArraySprite[i].setAlpha(1.0f);
			mArraySprite[i].reset();
			mArraySprite[i].setPosition(mArraySprite[i].getmXFirst(),
					mArraySprite[i].getmYFirst());
			if (mArraySprite[i].isVisible()) {
				mArraySprite[i].setVisible(false);
			}
		}
		for (int i = 0; i < mArraySpriteFlower.length; i++) {
			mArraySpriteFlower[i].setPosition(
					mArraySpriteFlower[i].getmXFirst(),
					mArraySpriteFlower[i].getmYFirst());
			if (mArraySpriteFlower[i].isVisible()) {
				mArraySpriteFlower[i].setVisible(false);
			}
		}
		if (mAnimatedSpriteA2_5_ != null) {
			mAnimatedSpriteA2_5_.stopAnimation();
			mAnimatedSpriteA2_5_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_5_.setPosition(121, 120);
			mAnimatedSpriteA2_5_.clearEntityModifiers();
			mAnimatedSpriteA2_5_.setVisible(false);
		}
		if (mAnimatedSpriteA2_6_ != null) {
			mAnimatedSpriteA2_6_.stopAnimation();
			mAnimatedSpriteA2_6_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_6_.setPosition(121, 120);
			mAnimatedSpriteA2_6_.clearEntityModifiers();
			mAnimatedSpriteA2_6_.setVisible(false);
		}
		if (mAnimatedSpriteA2_7_ != null) {
			mAnimatedSpriteA2_7_.stopAnimation();
			mAnimatedSpriteA2_7_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_7_.setPosition(121, 120);
			mAnimatedSpriteA2_7_.clearEntityModifiers();
			mAnimatedSpriteA2_7_.setVisible(false);
		}
		if (mAnimatedSpriteA2_8_ != null) {
			mAnimatedSpriteA2_8_.setVisible(false);
			mAnimatedSpriteA2_8_.stopAnimation();
			mAnimatedSpriteA2_8_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_8_.setPosition(480, 156);
			mAnimatedSpriteA2_8_.clearUpdateHandlers();
			mAnimatedSpriteA2_8_.clearEntityModifiers();
		}
		if (mAnimatedSpriteA2_9_ != null) {
			mAnimatedSpriteA2_9_.setVisible(false);
			mAnimatedSpriteA2_9_.stopAnimation();
			mAnimatedSpriteA2_9_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_9_.setPosition(480, 156);
			mAnimatedSpriteA2_9_.clearUpdateHandlers();
			mAnimatedSpriteA2_9_.clearEntityModifiers();
		}
		if (mAnimatedSpriteA2_10_ != null) {
			mAnimatedSpriteA2_10_.setVisible(false);
			mAnimatedSpriteA2_10_.stopAnimation();
			mAnimatedSpriteA2_10_.setCurrentTileIndex(0);
			mAnimatedSpriteA2_10_.setPosition(480, 156);
			mAnimatedSpriteA2_10_.clearUpdateHandlers();
			mAnimatedSpriteA2_10_.clearEntityModifiers();
		}
		if (mSpriteA2_8_9 != null) {
			mSpriteA2_8_9.clearEntityModifiers();
			mSpriteA2_8_9.setPosition(490, 160);
			if (mSpriteA2_8_9.isVisible()) {
				mSpriteA2_8_9.setVisible(false);
			}
		}
		if (mSpriteA2_9_9 != null) {
			mSpriteA2_9_9.setPosition(480, 156);
			mSpriteA2_9_9.clearEntityModifiers();
			if (mSpriteA2_9_9.isVisible()) {
				mSpriteA2_9_9.setVisible(false);
			}
		}
		if (mSpriteA2_10_9 != null) {
			mSpriteA2_10_9.setPosition(480, 156);
			mSpriteA2_10_9.clearEntityModifiers();
			if (mSpriteA2_10_9.isVisible()) {
				mSpriteA2_10_9.setVisible(false);
			}
		}
		if (mSpriteA2_5_1 != null) {
			mSpriteA2_5_1.clearEntityModifiers();
			mSpriteA2_5_1.setPosition(121, 258);
			mSpriteA2_5_1.setVisible(false);
		}
		if (mSpriteA2_5_2 != null) {
			mSpriteA2_5_2.setVisible(false);
		}
		if (mSpriteA2_6_1 != null) {
			mSpriteA2_6_1.clearEntityModifiers();
			mSpriteA2_6_1.setPosition(121, 258);
			mSpriteA2_6_1.setVisible(false);
		}
		if (mSpriteA2_6_2 != null) {
			mSpriteA2_6_2.setVisible(false);
		}
		if (mSpriteA2_7_1 != null) {
			mSpriteA2_7_1.clearEntityModifiers();
			mSpriteA2_7_1.setPosition(121, 258);
			mSpriteA2_7_1.setVisible(false);
		}
		if (mSpriteA2_7_2 != null) {
			mSpriteA2_7_2.setVisible(false);
		}
		if (mSpriteA2_8_1 != null) {
			mSpriteA2_8_1.clearEntityModifiers();
			mSpriteA2_8_1.setPosition(480, 258);
			mSpriteA2_8_1.setVisible(false);
		}
		if (mSpriteA2_8_2 != null) {
			mSpriteA2_8_2.setVisible(false);
		}
		if (mSpriteA2_9_1 != null) {
			mSpriteA2_9_1.clearEntityModifiers();
			mSpriteA2_9_1.setPosition(480, 258);
			mSpriteA2_9_1.setVisible(false);
		}
		if (mSpriteA2_9_2 != null) {
			mSpriteA2_9_2.setVisible(false);
		}
		if (mSpriteA2_10_1 != null) {
			mSpriteA2_10_1.clearEntityModifiers();
			mSpriteA2_10_1.setPosition(480, 258);
			mSpriteA2_10_1.setVisible(false);
		}
		if (mSpriteA2_10_2 != null) {
			mSpriteA2_10_2.setVisible(false);
		}
		if (mSpriteA2_17 != null) {
			mSpriteA2_17.clearEntityModifiers();
			mSpriteA2_17.clearUpdateHandlers();
		}
		if (mSpriteA2_2_1 != null) {
			if (!mSpriteA2_2_1.isVisible()) {
				mSpriteA2_2_1.setVisible(true);
			}
		}
		if (mSpriteA2_2_2 != null) {
			if (!mSpriteA2_2_2.isVisible()) {
				mSpriteA2_2_2.setVisible(true);
			}
		}
		if (mSpriteA2_13_1 != null) {
			mSpriteA2_13_1.setVisible(true);
		}
		if (mSpriteA2_13_2 != null) {
			mSpriteA2_13_2.setVisible(true);
		}
		mSpriteA2_16_1.clearEntityModifiers();
		mSpriteA2_16_1.setPosition(32, 114);

		mSpriteA2_16_2.clearEntityModifiers();
		mSpriteA2_16_2.setPosition(153, 67);

		mSpriteA2_16_3.clearEntityModifiers();
		mSpriteA2_16_3.setPosition(748, 47);

		mSpriteA2_11.clearEntityModifiers();
		mSpriteA2_12.clearEntityModifiers();
	}
}