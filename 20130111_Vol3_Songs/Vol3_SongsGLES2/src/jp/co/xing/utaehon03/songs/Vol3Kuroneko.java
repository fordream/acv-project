package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KuronekoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
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
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Kuroneko extends BaseGameActivity implements IOnSceneTouchListener {

	private static final String TAG = "LOG_KURONEKO";
	
	private TexturePack ttpKuroneko1, ttpKuroneko2, ttpKuroneko3, ttpKuroneko4, ttpKuroneko5;
	private TexturePackTextureRegionLibrary ttpLibKuroneko1, ttpLibKuroneko2, ttpLibKuroneko3, ttpLibKuroneko4, ttpLibKuroneko5;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion ttrBackground, ttrA3_12, ttrA3_13, ttrA3_14, ttrA3_26, ttrA3_211, ttrA3_21, ttrA3_323,
						  ttrA3_319, ttrA3_316, ttrA3_313, ttrA3_39, ttrA3_36, ttrA3_37, ttrA3_38, ttrA3_310, ttrA3_311, ttrA3_312,
						  ttrA3_314, ttrA3_315, ttrA3_317, ttrA3_318, ttrA3_320, ttrA3_321, ttrA3_322, ttrA3_51, ttrA3_52, ttrA3_53,
						  ttrA3_54, ttrGimmic, ttrA3_31, ttrA3_32, ttrA3_33, ttrA3_34, ttrA3_35;
	private Sprite sBackground, sA3_12, sA3_13, sA3_14, sA3_26, sA3_211, sA3_21, sA3_323, sA3_319, sA3_316, sA3_313, sA3_39,
				   sA3_36, sA3_37, sA3_38, sA3_310, sA3_311, sA3_312, sA3_314, sA3_315, sA3_317, sA3_318, sA3_320, sA3_321, sA3_322,
				   sA3_51, sA3_52, sA3_53, sA3_54, sGimmic, sA3_31, sA3_32, sA3_33, sA3_34, sA3_35;
	
	private TiledTextureRegion tttrA3_334, tttrA3_338, tttrA3_342, tttrA3_346, tttrA3_350, tttrA3_354, tttrA3_358, tttrA3_362, tttrA3_366,
							   tttrA3_370, tttrA3_61, tttrA3_63, tttrA3_68, tttrA3_613, tttrA3_618, tttrA3_623, tttrA3_628, tttrA3_22,
							   tttrA3_27, tttrA3_41, tttrA3_324, tttrA3_326, tttrA3_328, tttrA3_330, tttrA3_332, tttrA3_29, tttrA3_24;
	private AnimatedSprite asA3_334, asA3_338, asA3_342, asA3_346, asA3_350, asA3_354, asA3_358, asA3_362, asA3_366, asA3_370,
							asA3_61, asA3_63, asA3_68, asA3_613, asA3_618, asA3_623, asA3_628, asA3_22, asA3_27, asA3_41, asA3_324,
							asA3_326, asA3_328, asA3_330, asA3_332, asA3_29, asA3_24;
	
	private AnimatedSprite asGirl_ = null;
	
	private Rectangle rBackround, myHearts;
	private Sprite sHeart1, sHeart2, sHeart3;
	
	private TimerHandler failChild, winner1, winner2, winner3, touchGirl;
	private DelayModifier hightLight1;
	
	private boolean isMoving, isTouchA3_334, isTouchA3_338, isTouchA3_342, isTouchA3_346, isTouchA3_350, isTouchA322,
					isTouchA329, isTouchMouse, isTouchScene, isDance, isTouchBoy, is39, is313, is316, is319, is323, isWin, isSoundLight;
	
	private Sound mp3A3_3Cat1, mp3A3_3Cat2, mp3A3_3Cat3, mp3A3_3Cat4, mp3A3_3Cat5, mp3A3_3Boy_sta, mp3A3_3Hart, mp3A3_3_5Kumipea,
				  mp3A3_3_5Kumitango, mp3A3_5_Kiran, m3pA3_4_Nezumiodori, mp3A3_356, mp3A3_360, mp3A3_364, mp3A3_368, mp3A3_372,
				  mp3A3_2_Powan1, mp3A3_2_Powan2;
	
	private ArrayList<Integer> alRandom;
	
	private int lastX = 0;
	private int lastY = 0;
	private int zIndex = 0;
	private int[][] iPosition = {
			{254, 217}, {58, 224}, {398, 112}, {722, 191}, {550, 195}
	};
	private Sprite test = null;
	private int idex = 0;
	
	@Override
	protected void loadKaraokeResources() {
		
		ttpKuroneko1 = mTexturePackLoaderFromSource.load("kuroneko1.xml");
		ttpKuroneko1.loadTexture();
		ttpLibKuroneko1 = ttpKuroneko1.getTexturePackTextureRegionLibrary();
		
		ttpKuroneko2 = mTexturePackLoaderFromSource.load("kuroneko2.xml");
		ttpKuroneko2.loadTexture();
		ttpLibKuroneko2 = ttpKuroneko2.getTexturePackTextureRegionLibrary();
		
		ttpKuroneko3 = mTexturePackLoaderFromSource.load("kuroneko3.xml");
		ttpKuroneko3.loadTexture();
		ttpLibKuroneko3 = ttpKuroneko3.getTexturePackTextureRegionLibrary();
		
		ttpKuroneko4 = mTexturePackLoaderFromSource.load("kuroneko4.xml");
		ttpKuroneko4.loadTexture();
		ttpLibKuroneko4 = ttpKuroneko4.getTexturePackTextureRegionLibrary();
		
		ttpKuroneko5 = mTexturePackLoaderFromSource.load("kuroneko5.xml");
		ttpKuroneko5.loadTexture();
		ttpLibKuroneko5 = ttpKuroneko5.getTexturePackTextureRegionLibrary();
		
		this.ttrBackground = ttpLibKuroneko2.get(Vol3KuronekoResource.A3_7_3_IPHONE_ID);
		this.ttrA3_12 = ttpLibKuroneko3.get(Vol3KuronekoResource.A3_1_2_IPHONE_ID);
		this.ttrA3_13 = ttpLibKuroneko4.get(Vol3KuronekoResource.A3_1_3_IPHONE_ID);
		this.ttrA3_14 = ttpLibKuroneko5.get(Vol3KuronekoResource.A3_1_4_IPHONE_ID);
		
		this.ttrA3_26 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_2_6_IPHONE_ID);
		this.ttrA3_211 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_2_11_IPHONE_ID);
		this.ttrA3_21 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_2_1_IPHONE_ID);
		
		this.tttrA3_22 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_2_2_IPHONE_ID,
				Vol3KuronekoResource.A3_2_3_IPHONE_ID,
				Vol3KuronekoResource.A3_2_4_IPHONE_ID,
				Vol3KuronekoResource.A3_2_5_IPHONE_ID);
		
		this.tttrA3_27 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_2_7_IPHONE_ID,
				Vol3KuronekoResource.A3_2_8_IPHONE_ID,
				Vol3KuronekoResource.A3_2_9_IPHONE_ID,
				Vol3KuronekoResource.A3_2_10_IPHONE_ID);
		
		this.tttrA3_334 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_34_IPHONE_ID,
				Vol3KuronekoResource.A3_3_35_IPHONE_ID,
				Vol3KuronekoResource.A3_3_36_IPHONE_ID,
				Vol3KuronekoResource.A3_3_37_IPHONE_ID);
		
		this.tttrA3_338 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_38_IPHONE_ID,
				Vol3KuronekoResource.A3_3_39_IPHONE_ID,
				Vol3KuronekoResource.A3_3_40_IPHONE_ID,
				Vol3KuronekoResource.A3_3_41_IPHONE_ID);
		
		this.tttrA3_342 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_42_IPHONE_ID,
				Vol3KuronekoResource.A3_3_43_IPHONE_ID,
				Vol3KuronekoResource.A3_3_44_IPHONE_ID,
				Vol3KuronekoResource.A3_3_45_IPHONE_ID);
		
		this.tttrA3_346 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_46_IPHONE_ID,
				Vol3KuronekoResource.A3_3_47_IPHONE_ID,
				Vol3KuronekoResource.A3_3_48_IPHONE_ID,
				Vol3KuronekoResource.A3_3_49_IPHONE_ID);
		
		this.tttrA3_350 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_50_IPHONE_ID,
				Vol3KuronekoResource.A3_3_51_IPHONE_ID,
				Vol3KuronekoResource.A3_3_52_IPHONE_ID,
				Vol3KuronekoResource.A3_3_53_IPHONE_ID);
		
		this.tttrA3_354 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_54_IPHONE_ID,
				Vol3KuronekoResource.A3_3_55_IPHONE_ID,
				Vol3KuronekoResource.A3_3_56_IPHONE_ID,
				Vol3KuronekoResource.A3_3_57_IPHONE_ID);
		
		this.tttrA3_358 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_3_58_IPHONE_ID,
				Vol3KuronekoResource.A3_3_59_IPHONE_ID,
				Vol3KuronekoResource.A3_3_60_IPHONE_ID,
				Vol3KuronekoResource.A3_3_61_IPHONE_ID);
		
		this.tttrA3_362 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_3_62_IPHONE_ID,
				Vol3KuronekoResource.A3_3_63_IPHONE_ID,
				Vol3KuronekoResource.A3_3_64_IPHONE_ID,
				Vol3KuronekoResource.A3_3_65_IPHONE_ID);
		
		this.tttrA3_366 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_3_66_IPHONE_ID,
				Vol3KuronekoResource.A3_3_67_IPHONE_ID,
				Vol3KuronekoResource.A3_3_68_IPHONE_ID,
				Vol3KuronekoResource.A3_3_69_IPHONE_ID);
		
		this.tttrA3_370 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_3_70_IPHONE_ID,
				Vol3KuronekoResource.A3_3_71_IPHONE_ID,
				Vol3KuronekoResource.A3_3_72_IPHONE_ID,
				Vol3KuronekoResource.A3_3_73_IPHONE_ID);
		
		this.tttrA3_61 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_1_IPHONE_ID,
				Vol3KuronekoResource.A3_6_2_IPHONE_ID);
		
		this.tttrA3_63 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_3_IPHONE_ID,
				Vol3KuronekoResource.A3_6_4_IPHONE_ID,
				Vol3KuronekoResource.A3_6_5_IPHONE_ID,
				Vol3KuronekoResource.A3_6_6_IPHONE_ID,
				Vol3KuronekoResource.A3_6_7_IPHONE_ID);
		
		this.tttrA3_68 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_8_IPHONE_ID,
				Vol3KuronekoResource.A3_6_9_IPHONE_ID,
				Vol3KuronekoResource.A3_6_10_IPHONE_ID,
				Vol3KuronekoResource.A3_6_11_IPHONE_ID,
				Vol3KuronekoResource.A3_6_12_IPHONE_ID);
		
		this.tttrA3_613 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_13_IPHONE_ID,
				Vol3KuronekoResource.A3_6_14_IPHONE_ID,
				Vol3KuronekoResource.A3_6_15_IPHONE_ID,
				Vol3KuronekoResource.A3_6_16_IPHONE_ID,
				Vol3KuronekoResource.A3_6_17_IPHONE_ID);
		
		this.tttrA3_618 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_18_IPHONE_ID,
				Vol3KuronekoResource.A3_6_19_IPHONE_ID,
				Vol3KuronekoResource.A3_6_20_IPHONE_ID,
				Vol3KuronekoResource.A3_6_21_IPHONE_ID,
				Vol3KuronekoResource.A3_6_22_IPHONE_ID);
		
		this.tttrA3_623 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_23_IPHONE_ID,
				Vol3KuronekoResource.A3_6_24_IPHONE_ID,
				Vol3KuronekoResource.A3_6_25_IPHONE_ID,
				Vol3KuronekoResource.A3_6_26_IPHONE_ID,
				Vol3KuronekoResource.A3_6_27_IPHONE_ID);
		
		this.tttrA3_628 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_6_28_IPHONE_ID,
				Vol3KuronekoResource.A3_6_29_IPHONE_ID,
				Vol3KuronekoResource.A3_6_30_IPHONE_ID,
				Vol3KuronekoResource.A3_6_31_IPHONE_ID,
				Vol3KuronekoResource.A3_6_32_IPHONE_ID);
		
		this.ttrA3_323 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_23_IPHONE_ID);
		this.ttrA3_319 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_19_IPHONE_ID);
		this.ttrA3_316 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_16_IPHONE_ID);
		this.ttrA3_313 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_13_IPHONE_ID);
		this.ttrA3_39 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_9_IPHONE_ID);
		
		this.tttrA3_41 = getTiledTextureFromPacker(ttpKuroneko2, 
				Vol3KuronekoResource.A3_4_1_IPHONE_ID,
				Vol3KuronekoResource.A3_4_2_IPHONE_ID);
		
		this.tttrA3_324 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_24_IPHONE_ID,
				Vol3KuronekoResource.A3_3_25_IPHONE_ID);
		this.tttrA3_326 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_26_IPHONE_ID,
				Vol3KuronekoResource.A3_3_27_IPHONE_ID);
		this.tttrA3_328 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_28_IPHONE_ID,
				Vol3KuronekoResource.A3_3_29_IPHONE_ID);
		this.tttrA3_330 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_30_IPHONE_ID,
				Vol3KuronekoResource.A3_3_31_IPHONE_ID);
		this.tttrA3_332 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_3_32_IPHONE_ID,
				Vol3KuronekoResource.A3_3_33_IPHONE_ID);
		
		this.ttrA3_36 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_6_IPHONE_ID);
		this.ttrA3_37 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_7_IPHONE_ID);
		this.ttrA3_38 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_8_IPHONE_ID);
		
		this.ttrA3_310 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_10_IPHONE_ID);
		this.ttrA3_311 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_11_IPHONE_ID);
		this.ttrA3_312 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_12_IPHONE_ID);
		
		this.ttrA3_314 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_14_IPHONE_ID);
		this.ttrA3_315 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_15_IPHONE_ID);
		
		this.ttrA3_317 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_17_IPHONE_ID);
		this.ttrA3_318 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_18_IPHONE_ID);
		
		this.ttrA3_320 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_20_IPHONE_ID);
		this.ttrA3_321 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_21_IPHONE_ID);
		this.ttrA3_322 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_22_IPHONE_ID);
		
		this.ttrA3_51 = ttpLibKuroneko2.get(Vol3KuronekoResource.A3_5_1_IPHONE_ID);
		this.ttrA3_52 = ttpLibKuroneko2.get(Vol3KuronekoResource.A3_5_2_IPHONE_ID);
		this.ttrA3_53 = ttpLibKuroneko2.get(Vol3KuronekoResource.A3_5_3_IPHONE_ID);
		this.ttrA3_54 = ttpLibKuroneko2.get(Vol3KuronekoResource.A3_5_4_IPHONE_ID);
		
		this.tttrA3_24 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_2_4_IPHONE_ID,
				Vol3KuronekoResource.A3_2_5_IPHONE_ID);
		
		this.tttrA3_29 = getTiledTextureFromPacker(ttpKuroneko1, 
				Vol3KuronekoResource.A3_2_9_IPHONE_ID,
				Vol3KuronekoResource.A3_2_10_IPHONE_ID);
		
		this.ttrGimmic = ttpLibKuroneko5.get(Vol3KuronekoResource.A2_ICHINEN_3_IPHONE_ID);
		
		this.ttrA3_31 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_1_IPHONE_ID);
		this.ttrA3_32 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_2_IPHONE_ID);
		this.ttrA3_33 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_3_IPHONE_ID);
		this.ttrA3_34 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_4_IPHONE_ID);
		this.ttrA3_35 = ttpLibKuroneko1.get(Vol3KuronekoResource.A3_3_5_IPHONE_ID);
		alRandom = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			alRandom.add(i);
			Log.d(TAG, "alRandom ..................." + alRandom.get(i));
		}
		Log.d(TAG, "alRandom ..................." + alRandom.size());
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3A3_3Cat1 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3CAT1);
		mp3A3_3Cat2 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3CAT2);
		mp3A3_3Cat3 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3CAT3);
		mp3A3_3Cat4 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3CAT4);
		mp3A3_3Cat5 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3CAT5);
		mp3A3_3Boy_sta = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3BOY_STA);
		mp3A3_3Hart = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3HART);
		mp3A3_3_5Kumipea = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_5KUMIPEA);
		mp3A3_3_5Kumitango = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_5KUMITANGO);
		mp3A3_5_Kiran = loadSoundResourceFromSD(Vol3KuronekoResource.A3_5_KIRAN);
		m3pA3_4_Nezumiodori = loadSoundResourceFromSD(Vol3KuronekoResource.A3_4_NEZUMIODORI);
		mp3A3_356 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_56);
		mp3A3_360 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_60);
		mp3A3_364 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_64);
		mp3A3_368 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_68);
		mp3A3_372 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_3_72);
		mp3A3_2_Powan1 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_2_POWAN1);
		mp3A3_2_Powan2 = loadSoundResourceFromSD(Vol3KuronekoResource.A3_2_POWAN2);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		rBackround = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		mScene.attachChild(rBackround);
		rBackround.setColor(Color.WHITE);
//		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
//				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrBackground, this.getVertexBufferObjectManager())));
		sBackground = new Sprite(0, 0, ttrBackground, getVertexBufferObjectManager());
		mScene.attachChild(sBackground);
		
		//Music
		asA3_63 = new AnimatedSprite(101, 20, tttrA3_63, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_63);
		
		asA3_68 = new AnimatedSprite(206, 18, tttrA3_68, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_68);
		
		asA3_613 = new AnimatedSprite(341, -28, tttrA3_613, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_613);
		
		asA3_618 = new AnimatedSprite(440, 24, tttrA3_618, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_618);
		
		asA3_623 = new AnimatedSprite(698, 22, tttrA3_623, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_623);
		
		asA3_628 = new AnimatedSprite(793, 19, tttrA3_628, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_628);
		
		asA3_61 = new AnimatedSprite(67, 35, tttrA3_61, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_61);
		
		//den chieu sang
		sA3_51 = new Sprite(684, -120, ttrA3_51, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_51);
		sA3_51.setVisible(false);
		
		sA3_52 = new Sprite(68, -120, ttrA3_52, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_52);
		sA3_52.setVisible(false);
		
		sA3_53 = new Sprite(322, -41, ttrA3_53, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_53);
		sA3_53.setVisible(false);
		
		sA3_54 = new Sprite(470, -112, ttrA3_54, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_54);
		sA3_54.setVisible(false);
		
		//Children result
		sA3_323 = new Sprite(290, 100, ttrA3_323, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_323);
		sA3_323.setVisible(false);
		
		sA3_320 = new Sprite(0, 10, ttrA3_320, this.getVertexBufferObjectManager());
		sA3_323.attachChild(sA3_320);
		
		sA3_321 = new Sprite(140, 20, ttrA3_321, this.getVertexBufferObjectManager());
		sA3_323.attachChild(sA3_321);
		
		sA3_322 = new Sprite(100, 180, ttrA3_322, this.getVertexBufferObjectManager());
		sA3_323.attachChild(sA3_322);
		
		//A3_19
		sA3_319 = new Sprite(750, 191, ttrA3_319, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_319);
		sA3_319.setVisible(false);
		
		sA3_317 = new Sprite(0, 20, ttrA3_317, this.getVertexBufferObjectManager());
		sA3_319.attachChild(sA3_317);
		
		sA3_318 = new Sprite(150, 10, ttrA3_318, this.getVertexBufferObjectManager());
		sA3_319.attachChild(sA3_318);
		
		//A3_16
		sA3_316 = new Sprite(521, 95, ttrA3_316, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_316);
		sA3_316.setVisible(false);
		
		sA3_314 = new Sprite(0, 100, ttrA3_314, this.getVertexBufferObjectManager());
		sA3_316.attachChild(sA3_314);
		
		sA3_315 = new Sprite(170, 10, ttrA3_315, this.getVertexBufferObjectManager());
		sA3_316.attachChild(sA3_315);
		
		//A3_13
		sA3_313 = new Sprite(28, 124, ttrA3_313, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_313);
		sA3_313.setVisible(false);
		
		sA3_310 = new Sprite(10, 20, ttrA3_310, this.getVertexBufferObjectManager());
		sA3_313.attachChild(sA3_310);
		
		sA3_311 = new Sprite(160, 0, ttrA3_311, this.getVertexBufferObjectManager());
		sA3_313.attachChild(sA3_311);
		
		sA3_312 = new Sprite(155, 147, ttrA3_312, this.getVertexBufferObjectManager());
		sA3_313.attachChild(sA3_312);
		
		//children result ngac nhien
		sA3_32 = new Sprite(58, 124, ttrA3_32, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_32);
		sA3_32.setVisible(false);
		
		sA3_33 = new Sprite(398, 112, ttrA3_33, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_33);
		sA3_33.setVisible(false);
		
		sA3_35 = new Sprite(521, 95, ttrA3_35, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_35);
		sA3_35.setVisible(false);
		
		
		
		
				
		sA3_34 = new Sprite(722, 191, ttrA3_34, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_34);
		sA3_34.setVisible(false);

		//Children Cat girl
		asA3_354 = new AnimatedSprite(239, 201, tttrA3_354, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_354);
		
		asA3_358 = new AnimatedSprite(74, 206, tttrA3_358, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_358);
		
		asA3_362 = new AnimatedSprite(397, 159, tttrA3_362, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_362);
		
		asA3_366 = new AnimatedSprite(702, 221, tttrA3_366, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_366);
		
		asA3_370 = new AnimatedSprite(535, 187, tttrA3_370, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_370);
				
		// A3_39
				sA3_39 = new Sprite(320, 220, ttrA3_39, this.getVertexBufferObjectManager());
				mScene.attachChild(sA3_39);
				sA3_39.setVisible(false);
				sA3_39.setZIndex(50);
				
				sA3_36 = new Sprite(15, 0, ttrA3_36, this.getVertexBufferObjectManager());
				sA3_39.attachChild(sA3_36);
				
				sA3_37 = new Sprite(165, 0, ttrA3_37, this.getVertexBufferObjectManager());
				sA3_39.attachChild(sA3_37);
				
				sA3_38 = new Sprite(140, 150, ttrA3_38, this.getVertexBufferObjectManager());
				sA3_39.attachChild(sA3_38);
				
				sA3_31 = new Sprite(254, 117, ttrA3_31, this.getVertexBufferObjectManager());
				mScene.attachChild(sA3_31);
				sA3_31.setZIndex(20);
				sA3_31.setVisible(false);
				//mouse
				asA3_41 = new AnimatedSprite(0, 292, tttrA3_41, this.getVertexBufferObjectManager());
				mScene.attachChild(asA3_41);
				asA3_41.setVisible(false);
		
		//cat boy
		asA3_334 = new AnimatedSprite(148, 358, tttrA3_334, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_334);
		
		asA3_338 = new AnimatedSprite(269, 397, tttrA3_338, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_338);
		
		asA3_342 = new AnimatedSprite(406, 366, tttrA3_342, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_342);
		
		asA3_346 = new AnimatedSprite(546, 363, tttrA3_346, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_346);
		
		asA3_350 = new AnimatedSprite(673, 342, tttrA3_350, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_350);
		
		//cat win animation
		asA3_324 = new AnimatedSprite(155, 383, tttrA3_324, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_324);
		asA3_324.setVisible(false);
		
		asA3_326 = new AnimatedSprite(180, 178, tttrA3_326, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_326);
		asA3_326.setVisible(false);
		
		asA3_328 = new AnimatedSprite(613, 109, tttrA3_328, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_328);
		asA3_328.setVisible(false);
		
		asA3_330 = new AnimatedSprite(635, 329, tttrA3_330, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_330);
		asA3_330.setVisible(false);
		
		asA3_332 = new AnimatedSprite(407, 253, tttrA3_332, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_332);
		asA3_332.setVisible(false);
		
		//Food
		sA3_26 = new Sprite(856, 477, ttrA3_26, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_26);
		
		sA3_211 = new Sprite(-14, 529, ttrA3_211, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_211);
		
		asA3_22 = new AnimatedSprite(765, 358, tttrA3_22, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_22);
		
		asA3_24= new AnimatedSprite(765, 358, tttrA3_24, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_24);
		asA3_24.setVisible(false);
		
		asA3_29 = new AnimatedSprite(40, 361, tttrA3_29, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_29);
		asA3_29.setVisible(false);
		asA3_29.setZIndex(200);
		
		asA3_27 = new AnimatedSprite(40, 361, tttrA3_27, this.getVertexBufferObjectManager());
		mScene.attachChild(asA3_27);
		
		sA3_21 = new Sprite(855, 524, ttrA3_21, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_21);
		
		//my heart
		myHearts = new Rectangle(0, 0, 100, 100, this.getVertexBufferObjectManager());
		mScene.attachChild(myHearts);
		myHearts.setVisible(false);
		myHearts.setColor(Color.TRANSPARENT);
		
		sHeart1 = new Sprite(40, -10, ttrA3_38, this.getVertexBufferObjectManager());
		myHearts.attachChild(sHeart1);
		sHeart2 = new Sprite(0, 120, ttrA3_38, this.getVertexBufferObjectManager());
		myHearts.attachChild(sHeart2);
		sHeart3 = new Sprite(80, 120, ttrA3_38, this.getVertexBufferObjectManager());
		myHearts.attachChild(sHeart3);
		
		//Flash Light
		sA3_12 = new Sprite(0, 0, ttrA3_12, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_12);
		sA3_12.setVisible(false);
		sA3_12.setZIndex(502);
		
		sA3_13 = new Sprite(0, 0, ttrA3_13, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_13);
		sA3_13.setVisible(false);
		sA3_13.setZIndex(503);
		
		sA3_14 = new Sprite(0, 0, ttrA3_14, this.getVertexBufferObjectManager());
		mScene.attachChild(sA3_14);
		sA3_14.setVisible(false);
		sA3_14.setZIndex(504);
		
		sGimmic = new Sprite(612, 496, ttrGimmic, this.getVertexBufferObjectManager());
		mScene.attachChild(sGimmic);
		sGimmic.setZIndex(1000);
		mScene.sortChildren();
		
		this.mScene.setOnSceneTouchListener(this);
	}
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("kuroneko/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kuroneko/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "kuroneko/gfx/");
		super.onCreateResources();
	}
	
	@Override
	public synchronized void onPauseGame() {
		reStartGame();
		initial();
		if(failChild != null) {
			mScene.unregisterUpdateHandler(failChild);
		}
		if(winner1 != null) {
			mScene.unregisterUpdateHandler(winner1);
		}
		if(winner2 != null) {
			mScene.unregisterUpdateHandler(winner2);
		}
		if(winner3 != null) {
			mScene.unregisterUpdateHandler(winner3);
		}
		super.onPauseGame();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		
		flashLight();
		this.mScene.registerUpdateHandler(new TimerHandler(3.5f, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler arg0) {
						flashLight();
					}
		}));
		animateMusic(asA3_61, 0, 1);
		animateMusic(asA3_63, 0, 1);
		animateMusic(asA3_68, 0, 1);
		animateMusic(asA3_613, 0, 1);
		animateMusic(asA3_618, 0, 1);
		animateMusic(asA3_623, 0, 1);
		animateMusic(asA3_628, 0, 1);
		animateMusic(asA3_22, 0, 1);
		animateMusic(asA3_27, 0, 1);
		initial();
		
	}
	
	private void initial() {
		Log.d(TAG, "initial.......................");
		isWin = false;
		isSoundLight = false;
		isDance = false;
		isMoving = true;
		isTouchA3_338 = false;
		isTouchA3_334 = false;
		isTouchA3_342 = false;
		isTouchA3_346 = false;
		isTouchA3_350 = false;
		isTouchA322 = true;
		isTouchA329 = true;
		isTouchMouse = true;
		isTouchScene = false;
		isTouchBoy = true;
		is39 = false;
		is313 = false;
		is316 = false;
		is319 = false;
		is323 = false;
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
//		if(isTouchScene){
//			return false;
//		}
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			lastX = pX;
			lastY = pY;
			if(asA3_358.contains(pX, pY) && !isTouchScene) {
				Log.d(TAG, "ACTION_DOWN: A3_3_38");
				touchGirl(asA3_338, asA3_358);
			} else if(asA3_354.contains(pX, pY) && !isTouchScene) {
				touchGirl(asA3_334, asA3_354);
			} else if(asA3_362.contains(pX, pY) && !isTouchScene) {
				touchGirl(asA3_342, asA3_362);
			} else if(asA3_366.contains(pX, pY) && !isTouchScene) {
				touchGirl(asA3_346, asA3_366);
			} else if(asA3_370.contains(pX, pY) && !isTouchScene) {
				touchGirl(asA3_350, asA3_370);
			} else if(asA3_334.contains(pX, pY) && asA3_334.isVisible() && isTouchBoy && !isTouchScene) {
				isTouchBoy = false;
				touchBoy(asA3_334, asA3_354);
				isTouchA3_334 = true;
			} else if(asA3_338.contains(pX, pY) && asA3_338.isVisible() && isTouchBoy && !isTouchScene) {
				isTouchBoy = false;
				touchBoy(asA3_338, asA3_358);
				isTouchA3_338 = true;
			} else if(asA3_342.contains(pX, pY) && asA3_342.isVisible() && isTouchBoy && !isTouchScene) {
				isTouchBoy = false;
				touchBoy(asA3_342, asA3_362);
				isTouchA3_342 = true;
			} else if(checkContains(asA3_346, 0, 48, 117, 144, pX, pY) && asA3_346.isVisible() && isTouchBoy && !isTouchScene) {
				isTouchBoy = false;
				touchBoy(asA3_346, asA3_366);
				isTouchA3_346 = true;
			} else if(checkContains(asA3_350, 0, 48, 123, 152, pX, pY) && asA3_350.isVisible() && isTouchBoy && !isTouchScene) {
				isTouchBoy = false;
				touchBoy(asA3_350, asA3_370);
				isTouchA3_350 = true;
			} else if((139 < pX && pX < 799) && (120 < pY && pY < 204)) {
				
			} else if(asA3_22.contains(pX, pY)) {
				if(isTouchA322) {
					isTouchA322 = false;
					Random random = new Random();
					int iRandom = random.nextInt(2);
					if(iRandom == 0) {
						mp3A3_2_Powan1.play();
					} else {
						mp3A3_2_Powan2.play();
					}
					asA3_22.setVisible(false);
					asA3_24.setVisible(true);
					long pDurations[] = new long[] {400, 400};
					asA3_24.animate(pDurations, 1, new IAnimationListener() {
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
							asA3_22.setVisible(true);
							asA3_24.setVisible(false);
							isTouchA322 = true;
						}
					});
				}
			} else if(asA3_29.contains(pX, pY)) {
				if(isTouchA329) {
					isTouchA329 = false;
					Random random = new Random();
					int iRandom = random.nextInt(2);
					if(iRandom == 0) {
						mp3A3_2_Powan1.play();
					} else {
						mp3A3_2_Powan2.play();
					}
					asA3_27.setVisible(false);
					asA3_29.setVisible(true);
					long pDurations[] = new long[] {400, 400};
					asA3_29.animate(pDurations, 1, new IAnimationListener() {
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
							asA3_27.setVisible(true);
							asA3_29.setVisible(false);
							isTouchA329 = true;
						}
					});
				}
			} else if(sGimmic.contains(pX, pY)) {
				actionMouse();
			} else if(asA3_63.contains(pX, pY) || asA3_68.contains(pX, pY)) {
				highLight(sA3_52);
			} else if(asA3_613.contains(pX, pY)) {
				highLight(sA3_53);
			} else if(asA3_618.contains(pX, pY)) {
				highLight(sA3_54);
			} else if(asA3_623.contains(pX, pY) || asA3_628.contains(pX, pY)) {
				highLight(sA3_51);
			}
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE && Math.abs((lastX + lastY) - (pX + pY)) >= 20) {
			if(isTouchA3_338) {
				moveChild(asA3_338, pX, pY);
			} else if(isTouchA3_334) {
				moveChild(asA3_334, pX, pY);
			} else if(isTouchA3_342) {
				moveChild(asA3_342, pX, pY);
			} else if(isTouchA3_346) {
				moveChild(asA3_346, pX, pY);
			} else if(isTouchA3_350) {
				moveChild(asA3_350, pX, pY);
			}
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if(isTouchA3_338) {
				isTouchA3_338 = false;
				collidesChild(asA3_338, asA3_358, pX, pY);
			} else if(isTouchA3_334) {
				isTouchA3_334 = false;
				collidesChild(asA3_334, asA3_354, pX, pY);
			} else if(isTouchA3_342) {
				isTouchA3_342 = false;
				collidesChild(asA3_342, asA3_362, pX, pY);
			} else if(isTouchA3_346) {
				isTouchA3_346 = false;
				collidesChild(asA3_346, asA3_366, pX, pY);
			} else if(isTouchA3_350) {
				isTouchA3_350 = false;
				collidesChild(asA3_350, asA3_370, pX, pY);
			}
		}
		
		return false;
	}
	
	private void randomChilds() {
		
		Collections.shuffle(alRandom);
		Log.d(TAG, "alRandom.get(0): "+ alRandom.get(0));
		setPositionChild(asA3_354, alRandom.get(0));
		setPositionChild(asA3_358, alRandom.get(1));
		setPositionChild(asA3_362, alRandom.get(2));
		setPositionChild(asA3_366, alRandom.get(3));
		setPositionChild(asA3_370, alRandom.get(4));
	}
	
	private void setPositionChild(Sprite mySprite, int x) {
		if(x == 0) {
			mySprite.setPosition(398 - mySprite.getWidth(), 366 - mySprite.getHeight());
		} else if(x == 1) {
			mySprite.setPosition(237 - mySprite.getWidth(), 369 - mySprite.getHeight());
		} else if(x == 2) {
			mySprite.setPosition(544 - mySprite.getWidth(), 361 - mySprite.getHeight());
		} else if(x == 3) {
			mySprite.setPosition(838 - mySprite.getWidth(), 373 - mySprite.getHeight());
		} else if(x == 4) {
			mySprite.setPosition(697 - mySprite.getWidth(), 369 - mySprite.getHeight());
		}
		
		mySprite.setmXFirst(mySprite.getX());
		mySprite.setmYFirst(mySprite.getY());
	}

	private void flashLight() {
		
		sA3_12.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				sA3_12.setVisible(true);
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sA3_12.setVisible(false);
				sA3_13.registerEntityModifier(new DelayModifier(1.0f, 
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sA3_13.setVisible(true);
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sA3_13.setVisible(false);
								sA3_14.registerEntityModifier(new DelayModifier(1.0f, 
										new IEntityModifierListener() {
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												sA3_14.setVisible(true);
											}
											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												sA3_14.setVisible(false);
												sA3_12.setVisible(true);
											}
										}));
							}
						}));
			}
		}));
	}
	
	private void animateMusic(AnimatedSprite asSprite, int x, int y) {
		long pDurations[] = new long[] {600, 600};
		int pFrames[] = new int[] {x, y};
		asSprite.animate(pDurations, pFrames, -1);
	}
	
	private void touchGirl(final AnimatedSprite asBoy, final AnimatedSprite asGirl) {
		if(asGirl.getCurrentTileIndex() == 0 && asGirl.isVisible()) {
			isTouchMouse = false;
			if(asGirl.equals(asA3_354))
				mp3A3_3Cat1.play();
			if(asGirl.equals(asA3_358))
				mp3A3_3Cat2.play();
			if(asGirl.equals(asA3_362))
				mp3A3_3Cat3.play();
			if(asGirl.equals(asA3_366))
				mp3A3_3Cat4.play();
			if(asGirl.equals(asA3_370))
				mp3A3_3Cat5.play();
			
			asBoy.setCurrentTileIndex(1);
			asGirl.setCurrentTileIndex(1);
			mScene.registerUpdateHandler(touchGirl = new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					asBoy.setCurrentTileIndex(0);
					asGirl.setCurrentTileIndex(0);
					isTouchMouse = true;
				}
			}));
//			asBoy.registerEntityModifier(new DelayModifier(0.8f, new IEntityModifierListener() {
//				@Override
//				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
//					
//				}
//				
//				@Override
//				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
//					asBoy.setCurrentTileIndex(0);
//					asGirl.setCurrentTileIndex(0);
//					isTouchMouse = true;
//					isTouchGirl = true;
//				}
//			}));
		}
	}
	
	private void touchBoy(final AnimatedSprite asBoy, final AnimatedSprite asGirl) {
		if(asBoy.getCurrentTileIndex() == 0 && asBoy.isVisible()) {
			//isTouchBoy = false;
			mp3A3_3Boy_sta.play();
			asBoy.setCurrentTileIndex(1);
			asGirl.setCurrentTileIndex(1);
			myHearts.detachSelf();
			asGirl.attachChild(myHearts);
			myHearts.setPosition(10, 10);
			myHearts.setVisible(true);
			sHeart1.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new AlphaModifier(0.2f, 1, 0),
						new AlphaModifier(0.2f, 0, 1)
					)));
			sHeart2.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new AlphaModifier(0.2f, 1, 0),
						new AlphaModifier(0.2f, 0, 1)
					)));
			sHeart3.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
						new AlphaModifier(0.2f, 1, 0),
						new AlphaModifier(0.2f, 0, 1)
					)));
		}
	}
	
	private void moveChild(AnimatedSprite asBoy, int pX, int pY) {
		if(isMoving) {
			Log.d(TAG, "move");
			isMoving = false;
//			asBoy.setCurrentTileIndex(1);
//			asBoy.clearEntityModifiers();
			zIndex = asBoy.getZIndex();
			asBoy.setZIndex(201);
			mScene.sortChildren();
			if(asA3_354.isVisible() && asA3_354.getCurrentTileIndex() == 0)
				asA3_354.setCurrentTileIndex(1);
			if(asA3_358.isVisible() && asA3_358.getCurrentTileIndex() == 0)
				asA3_358.setCurrentTileIndex(1);
			if(asA3_362.isVisible() && asA3_362.getCurrentTileIndex() == 0) 
				asA3_362.setCurrentTileIndex(1);
			if(asA3_366.isVisible() && asA3_366.getCurrentTileIndex() == 0)
				asA3_366.setCurrentTileIndex(1);
			if(asA3_370.isVisible() && asA3_370.getCurrentTileIndex() == 0)
				asA3_370.setCurrentTileIndex(1);
		}
		asBoy.setPosition(pX - asBoy.getWidth() / 2, pY - asBoy.getHeight() / 2);
	}

	private void collidesChild(final AnimatedSprite asBoy, final AnimatedSprite asGirl, int pX, int pY) {
//		if(isMoving)
//			return ;
		
		if((checkContains(asA3_354, 0, 0, 159, 165, pX, pY) && asA3_354.isVisible())
				|| (checkContains(asA3_358, 0, 0, 163, 163, pX, pY) && asA3_358.isVisible())
				|| (checkContains(asA3_362, 0, 0, 147, 202, pX, pY) && asA3_362.isVisible())
				|| (checkContains(asA3_366, 0, 0, 136, 152, pX, pY) && asA3_366.isVisible())
				|| (checkContains(asA3_370, 0, 0, 162, 182, pX, pY) && asA3_370.isVisible())) {
			
			Log.d(TAG, "Collides.....");
			if((checkContains(asA3_354, 0, 0, 159, 165, pX, pY) && asBoy.equals(asA3_334))
					|| (checkContains(asA3_358, 0, 0, 163, 163, pX, pY) && asBoy.equals(asA3_338))
					|| (checkContains(asA3_362, 0, 0, 147, 202, pX, pY) && asBoy.equals(asA3_342))
					|| (checkContains(asA3_366, 0, 0, 136, 152, pX, pY) && asBoy.equals(asA3_346))
					|| (checkContains(asA3_370, 0, 0, 162, 182, pX, pY) && asBoy.equals(asA3_350))) {
				Log.d(TAG, "hehe");
				isTouchBoy = true;
				mp3A3_3Hart.play();
				if(asBoy.equals(asA3_334)) {
					sA3_39.setVisible(true);
					sA3_39.setScale(0.8f);
					test = sA3_39;
					idex = alRandom.get(0);
					sA3_36.setVisible(true);
					sA3_37.setVisible(true);
					sA3_38.setVisible(true);
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sA3_36.setVisible(false);
							sA3_37.setVisible(false);
							sA3_38.setVisible(false);
						}
					}));
				}
				if(asBoy.equals(asA3_338)) {
					sA3_313.setVisible(true);
					sA3_313.setScale(0.8f);
					test = sA3_313;
					idex = alRandom.get(1);
					sA3_310.setVisible(true);
					sA3_311.setVisible(true);
					sA3_312.setVisible(true);
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sA3_310.setVisible(false);
							sA3_311.setVisible(false);
							sA3_312.setVisible(false);
						}
					}));
				}
				if(asBoy.equals(asA3_342)) {
					sA3_323.setVisible(true);
					sA3_323.setScale(0.8f);
					test = sA3_323;
					idex = alRandom.get(2);
					sA3_320.setVisible(true);
					sA3_321.setVisible(true);
					sA3_322.setVisible(true);
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sA3_320.setVisible(false);
							sA3_321.setVisible(false);
							sA3_322.setVisible(false);
						}
					}));
				}
				if(asBoy.equals(asA3_346)) {
					sA3_319.setVisible(true);
					sA3_319.setScale(0.8f);
					test = sA3_319;
					idex = alRandom.get(3);
					sA3_317.setVisible(true);
					sA3_318.setVisible(true);
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sA3_317.setVisible(false);
							sA3_318.setVisible(false);
						}
					}));
				}
				if(asBoy.equals(asA3_350)) {
					sA3_316.setVisible(true);
					sA3_316.setScale(0.8f);
					test = sA3_316;
					idex = alRandom.get(4);
					sA3_314.setVisible(true);
					sA3_315.setVisible(true);
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sA3_314.setVisible(false);
							sA3_315.setVisible(false);
						}
					}));
				}
				
				if(test != null) {
					if(idex == 0) {
						test.setPosition(iPosition[0][0], iPosition[0][1]);
					} else if(idex == 1) {
						test.setPosition(iPosition[1][0], iPosition[1][1]);
						
					} else if(idex == 2) {
						test.setPosition(iPosition[2][0], iPosition[2][1]);
					} else if(idex == 3) {
						test.setPosition(iPosition[3][0], iPosition[3][1]);
					} else {
						test.setPosition(iPosition[4][0], iPosition[4][1]);
					}
				}
				
				asBoy.setPosition(asBoy.getmXFirst(), asBoy.getmYFirst());
				asBoy.setVisible(false);
				asGirl.setPosition(asGirl.getmXFirst(), asGirl.getmYFirst());
				asGirl.setVisible(false);
				
				asBoy.setZIndex(zIndex);
				zIndex = 0;
				mScene.sortChildren();
				
				if(asA3_354.isVisible() && asA3_354.getCurrentTileIndex() == 1)
					asA3_354.setCurrentTileIndex(0);
				if(asA3_358.isVisible() && asA3_358.getCurrentTileIndex() == 1)
					asA3_358.setCurrentTileIndex(0);
				if(asA3_362.isVisible() && asA3_362.getCurrentTileIndex() == 1) 
					asA3_362.setCurrentTileIndex(0);
				if(asA3_366.isVisible() && asA3_366.getCurrentTileIndex() == 1)
					asA3_366.setCurrentTileIndex(0);
				if(asA3_370.isVisible() && asA3_370.getCurrentTileIndex() == 1)
					asA3_370.setCurrentTileIndex(0);
				if(sA3_39.isVisible() && sA3_313.isVisible()
						&& sA3_323.isVisible() && sA3_319.isVisible() && sA3_316.isVisible()) {
					Log.d(TAG, "win game...");
					//isTouchScene = true;
					isWin = true;
					mScene.registerUpdateHandler(winner1 = new TimerHandler(1.7f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							// TODO Auto-generated method stub
							mp3A3_3_5Kumipea.play();
							
							//music
							animateMusic(asA3_63, 2, 3);
							animateMusic(asA3_68, 2, 3);
							animateMusic(asA3_613, 2, 3);
							animateMusic(asA3_618, 2, 3);
							animateMusic(asA3_623, 2, 3);
							animateMusic(asA3_628, 2, 3);
							
							//cat
							sA3_39.setVisible(false);
							sA3_313.setVisible(false);
							sA3_323.setVisible(false);
							sA3_319.setVisible(false);
							sA3_316.setVisible(false);
							
							asA3_324.setVisible(true);
							asA3_326.setVisible(true);
							asA3_328.setVisible(true);
							asA3_330.setVisible(true);
							asA3_332.setVisible(true);
						}
					}));
					
					mScene.registerUpdateHandler(winner2 = new TimerHandler(4.0f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							// TODO Auto-generated method stub
							
							//couple cat
							mp3A3_3_5Kumitango.play();
							animateMusic(asA3_324, 0, 1);
							animateMusic(asA3_326, 0, 1);
							animateMusic(asA3_328, 0, 1);
							animateMusic(asA3_330, 0, 1);
							animateMusic(asA3_332, 0, 1);
							
							//music
							animateMusic(asA3_63, 0, 1);
							animateMusic(asA3_68, 0, 1);
							animateMusic(asA3_613, 0, 1);
							animateMusic(asA3_618, 0, 1);
							animateMusic(asA3_623, 0, 1);
							animateMusic(asA3_628, 0, 1);
							
							isSoundLight = true;
							highLight(sA3_52);
							highLight(sA3_53);
							highLight(sA3_54);
							highLight(sA3_51);
							mScene.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									isTouchScene = true;
								}
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									highLight(sA3_52);
									highLight(sA3_53);
									highLight(sA3_54);
									highLight(sA3_51);
									mScene.registerEntityModifier(new DelayModifier(1.1f, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											highLight(sA3_52);
											highLight(sA3_53);
											highLight(sA3_54);
											highLight(sA3_51);
											mScene.registerUpdateHandler(winner3 = new TimerHandler(1.5f, new ITimerCallback() {
												
												@Override
												public void onTimePassed(TimerHandler arg0) {
													// TODO Auto-generated method stub
													reStartGame();
												}
											}));
										}
									}));
								}
							}));
						}
					}));
				}
			} else {
				Log.d(TAG, "huhu");
				//asGirl.setCurrentTileIndex(0);
				//isTouchScene = true;
				
				if(checkContains(asA3_354, 0, 0, 159, 165, pX, pY)) {
					mp3A3_356.play();
					asGirl_ = asA3_354;
				}
				if(checkContains(asA3_358, 0, 0, 163, 163, pX, pY)) {
					mp3A3_360.play();
					asGirl_ = asA3_358;
				}
				if(checkContains(asA3_362, 0, 0, 147, 202, pX, pY)) {
					mp3A3_364.play();
					asGirl_ = asA3_362;
				}
				if(checkContains(asA3_366, 0, 0, 136, 152, pX, pY)) {
					mp3A3_368.play();
					asGirl_ = asA3_366;
				}
				if(checkContains(asA3_370, 0, 0, 162, 182, pX, pY)) {
					mp3A3_372.play();
					asGirl_ = asA3_370;
				}
				if(asGirl_ != null) {
					
					asGirl_.setCurrentTileIndex(2);
					asGirl_.setPosition(asGirl_.getmXFirst() - 50, asGirl_.getmYFirst());
					asBoy.setCurrentTileIndex(2);
					asBoy.setPosition(asGirl_.getmXFirst() + 50, 366 - asBoy.getHeight());
					asBoy.setZIndex(10);
					mScene.sortChildren();
					Log.d(TAG, "zIndex Boy: " + asBoy.getZIndex());
					Log.d(TAG, "zIndex sA3_39: " + sA3_39.getZIndex());
				}
				
				mScene.registerUpdateHandler(failChild = new TimerHandler(1.5f, false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler arg0) {
						Log.d(TAG, "cos nhay vao day.......huhu");
						isTouchBoy = true;
						//asGirl.setCurrentTileIndex(0);
						asBoy.setCurrentTileIndex(0);
						asBoy.setPosition(asBoy.getmXFirst(), asBoy.getmYFirst());
						if(asGirl_ != null) {
							asGirl_.setCurrentTileIndex(0);
							asGirl_.setPosition(asGirl_.getmXFirst(), asGirl_.getmYFirst());
						}
						isTouchScene = false;
						if(asA3_354.isVisible() && asA3_354.getCurrentTileIndex() == 1)
							asA3_354.setCurrentTileIndex(0);
						if(asA3_358.isVisible() && asA3_358.getCurrentTileIndex() == 1)
							asA3_358.setCurrentTileIndex(0);
						if(asA3_362.isVisible() && asA3_362.getCurrentTileIndex() == 1) 
							asA3_362.setCurrentTileIndex(0);
						if(asA3_366.isVisible() && asA3_366.getCurrentTileIndex() == 1)
							asA3_366.setCurrentTileIndex(0);
						if(asA3_370.isVisible() && asA3_370.getCurrentTileIndex() == 1)
							asA3_370.setCurrentTileIndex(0);
						
						asBoy.setZIndex(zIndex);
						mScene.sortChildren();
					}
				}));
			}
			
		} else {
			Log.d(TAG, "cos nhay vao day.......");
			
			asBoy.setCurrentTileIndex(0);
			asBoy.setPosition(asBoy.getmXFirst(), asBoy.getmYFirst());
			if(asA3_354.isVisible() && asA3_354.getCurrentTileIndex() == 1)
				asA3_354.setCurrentTileIndex(0);
			if(asA3_358.isVisible() && asA3_358.getCurrentTileIndex() == 1)
				asA3_358.setCurrentTileIndex(0);
			if(asA3_362.isVisible() && asA3_362.getCurrentTileIndex() == 1) 
				asA3_362.setCurrentTileIndex(0);
			if(asA3_366.isVisible() && asA3_366.getCurrentTileIndex() == 1)
				asA3_366.setCurrentTileIndex(0);
			if(asA3_370.isVisible() && asA3_370.getCurrentTileIndex() == 1)
				asA3_370.setCurrentTileIndex(0);
			
			isTouchBoy = true;
			asBoy.setZIndex(zIndex);
			zIndex = 0;
			mScene.sortChildren();
		}
		
		myHearts.setVisible(false);
//		asBoy.setZIndex(zIndex);
//		mScene.sortChildren();
//		zIndex = 0;
		isMoving = true;
	}
	
	private void actionMouse() {
		if(isTouchMouse) {
			isTouchScene = true;
			isTouchMouse = false;
			m3pA3_4_Nezumiodori.play();
			
			if(failChild != null) {
				mScene.unregisterUpdateHandler(failChild);
			}
			if(winner1 != null) {
				mScene.unregisterUpdateHandler(winner1);
			}
			if(winner2 != null) {
				mScene.unregisterUpdateHandler(winner2);
			}
			if(winner3 != null) {
				mScene.unregisterUpdateHandler(winner3);
			}
			if(touchGirl != null) {
				mScene.unregisterUpdateHandler(touchGirl);
			}
			//boy
			if(asA3_334.isVisible())
				asA3_334.setCurrentTileIndex(3);
			if(asA3_338.isVisible())
				asA3_338.setCurrentTileIndex(3);
			if(asA3_342.isVisible())
				asA3_342.setCurrentTileIndex(3);
			if(asA3_346.isVisible())
				asA3_346.setCurrentTileIndex(3);
			if(asA3_350.isVisible())
				asA3_350.setCurrentTileIndex(3);
			//girl
			if(asA3_354.isVisible())
				asA3_354.setCurrentTileIndex(3);
			if(asA3_358.isVisible())
				asA3_358.setCurrentTileIndex(3);
			if(asA3_362.isVisible())
				asA3_362.setCurrentTileIndex(3);
			if(asA3_366.isVisible())
				asA3_366.setCurrentTileIndex(3);
			if(asA3_370.isVisible())
				asA3_370.setCurrentTileIndex(3);
			
			//music band
			asA3_61.stopAnimation();
			asA3_61.setVisible(false);
			asA3_63.stopAnimation();
			asA3_63.setCurrentTileIndex(4);
			asA3_68.stopAnimation();
			asA3_68.setCurrentTileIndex(4);
			asA3_613.stopAnimation();
			asA3_613.setCurrentTileIndex(4);
			asA3_618.stopAnimation();
			asA3_618.setCurrentTileIndex(4);
			asA3_623.stopAnimation();
			asA3_623.setCurrentTileIndex(4);
			asA3_628.stopAnimation();
			asA3_628.setCurrentTileIndex(4);
			
			//child result
			if(sA3_39.isVisible()) {
				is39 = true;
				sA3_39.setVisible(false);
				sA3_31.setPosition(sA3_39.getX(), sA3_39.getY());
				sA3_31.setScale(0.8f);
				sA3_31.setVisible(true);
			}
			if(sA3_313.isVisible()) {
				is313 = true;
				sA3_313.setVisible(false);
				sA3_32.setPosition(sA3_313.getX(), sA3_313.getY());
				sA3_32.setScale(0.8f);
				sA3_32.setVisible(true);
			}
			if(sA3_316.isVisible()) {
				is316 = true;
				sA3_316.setVisible(false);
				sA3_35.setPosition(sA3_316.getX(), sA3_316.getY());
				sA3_35.setScale(0.8f);
				sA3_35.setVisible(true);
			}
			if(sA3_319.isVisible()) {
				is319 = true;
				sA3_319.setVisible(false);
				sA3_34.setPosition(sA3_319.getX(), sA3_319.getY());
				sA3_34.setScale(0.8f);
				sA3_34.setVisible(true);
			}
			if(sA3_323.isVisible()) {
				is323 = true;
				sA3_323.setVisible(false);
				sA3_33.setPosition(sA3_323.getX(), sA3_323.getY());
				sA3_33.setScale(0.8f);
				sA3_33.setVisible(true);
			}
			
			//dance child
			if(asA3_324.isVisible()) {
				isDance = true;
				asA3_324.setVisible(false);
				sA3_31.setVisible(true);
				sA3_31.setPosition(asA3_324.getmXFirst(), asA3_324.getmYFirst());
				sA3_31.setScale(1);
				
				asA3_326.setVisible(false);
				sA3_32.setVisible(true);
				sA3_32.setPosition(asA3_326.getmXFirst(), asA3_326.getmYFirst());
				sA3_32.setScale(1);
				
				asA3_328.setVisible(false);
				sA3_33.setVisible(true);
				sA3_33.setPosition(asA3_328.getmXFirst(), asA3_328.getmYFirst());
				sA3_33.setScale(1);
				
				asA3_330.setVisible(false);
				sA3_34.setVisible(true);
				sA3_34.setPosition(asA3_330.getmXFirst(), asA3_330.getmYFirst());
				sA3_34.setScale(1);
				
				asA3_332.setVisible(false);
				sA3_35.setVisible(true);
				sA3_35.setPosition(asA3_332.getmXFirst(), asA3_332.getmYFirst());
				sA3_35.setScale(1);
			}
			
			asA3_41.setVisible(true);
			animateMusic(asA3_41, 0, 1);
			Path pPath = new Path(3).to(0, 392).to(436, 352).to(900, 392);
			asA3_41.registerEntityModifier(new PathModifier(2.0f, pPath, new IPathModifierListener() {
				@Override
				public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
					
				}
				@Override
				public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
				}
				@Override
				public void onPathStarted(PathModifier arg0, IEntity arg1) {
				}
				
				@Override
				public void onPathFinished(PathModifier arg0, IEntity arg1) {
					isTouchScene = false;
					isTouchMouse = true;
					isTouchBoy = true;
					asA3_41.setPosition(asA3_41.getmXFirst(), asA3_41.getmYFirst());
					asA3_41.setVisible(false);
					
					if(failChild != null && isWin) {
						mScene.unregisterUpdateHandler(failChild);
						mScene.registerUpdateHandler(failChild);
					}
					if(winner1 != null && isWin) {
						mScene.unregisterUpdateHandler(winner1);
						mScene.registerUpdateHandler(winner1);
					}
					if(winner2 != null && isWin) {
						mScene.unregisterUpdateHandler(winner2);
						mScene.registerUpdateHandler(winner2);
					}
					if(winner3 != null && isWin) {
						mScene.unregisterUpdateHandler(winner3);
						mScene.registerUpdateHandler(winner3);
					}
					//boy
					if(asA3_334.isVisible()) {
						asA3_334.setCurrentTileIndex(0);
						asA3_334.setPosition(asA3_334.getmXFirst(), asA3_334.getmYFirst());
					}
					if(asA3_338.isVisible()) {
						asA3_338.setCurrentTileIndex(0);
						asA3_338.setPosition(asA3_338.getmXFirst(), asA3_338.getmYFirst());
					}
					if(asA3_342.isVisible()) {
						asA3_342.setCurrentTileIndex(0);
						asA3_342.setPosition(asA3_342.getmXFirst(), asA3_342.getmYFirst());
					}
					if(asA3_346.isVisible()) {
						asA3_346.setCurrentTileIndex(0);
						asA3_346.setPosition(asA3_346.getmXFirst(), asA3_346.getmYFirst());
					}
					if(asA3_350.isVisible()) {
						asA3_350.setCurrentTileIndex(0);
						asA3_350.setPosition(asA3_350.getmXFirst(), asA3_350.getmYFirst());
					}
					
					//girl
					if(asA3_354.isVisible()) {
						asA3_354.setCurrentTileIndex(0);
						asA3_354.setPosition(asA3_354.getmXFirst(), asA3_354.getmYFirst());
					}
					if(asA3_358.isVisible()) {
						asA3_358.setCurrentTileIndex(0);
						asA3_358.setPosition(asA3_358.getmXFirst(), asA3_358.getmYFirst());
					}
					if(asA3_362.isVisible()) {
						asA3_362.setCurrentTileIndex(0);
						asA3_362.setPosition(asA3_362.getmXFirst(), asA3_362.getmYFirst());
					}
					if(asA3_366.isVisible()) {
						asA3_366.setCurrentTileIndex(0);
						asA3_366.setPosition(asA3_366.getmXFirst(), asA3_366.getmYFirst());
					}
					if(asA3_370.isVisible()) {
						asA3_370.setCurrentTileIndex(0);
						asA3_370.setPosition(asA3_370.getmXFirst(), asA3_370.getmYFirst());
					}
					//child result
					if(sA3_31.isVisible()) {
						sA3_31.setVisible(false);
						if(is39) {
							is39 = false;
							sA3_39.setVisible(true);
						}
					}
					if(sA3_32.isVisible()) {
						sA3_32.setVisible(false);
						if(is313) {
							is313= false;
							sA3_313.setVisible(true);
						}
					}
					if(sA3_33.isVisible()) {
						sA3_33.setVisible(false);
						if(is323) {
							is323 = false;
							sA3_323.setVisible(true);
						}
					}
					if(sA3_34.isVisible()) {
						sA3_34.setVisible(false);
						if(is319) {
							is319 = false;
							sA3_319.setVisible(true);
						}
					}
					if(sA3_35.isVisible()) {
						sA3_35.setVisible(false);
						if(is316) {
							is316 = false;
							sA3_316.setVisible(true);
						}
					}
					
					//dance child
					if(isDance) {
						isDance = false;
						asA3_324.setVisible(true);
						asA3_326.setVisible(true);
						asA3_328.setVisible(true);
						asA3_330.setVisible(true);
						asA3_332.setVisible(true);
					}
					
					asA3_61.setVisible(true);
					animateMusic(asA3_61, 0, 1);
					animateMusic(asA3_63, 0, 1);
					animateMusic(asA3_68, 0, 1);
					animateMusic(asA3_613, 0, 1);
					animateMusic(asA3_618, 0, 1);
					animateMusic(asA3_623, 0, 1);
					animateMusic(asA3_628, 0, 1);
					animateMusic(asA3_22, 0, 1);
					animateMusic(asA3_27, 0, 1);
				}
			}));
		}
	}
	
	private void highLight(final Sprite mySprite) {
		if(!mySprite.isVisible()) {
			mScene.registerEntityModifier(hightLight1 = new DelayModifier(1.0f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					if(!isSoundLight) {
						mp3A3_5_Kiran.play();
					}
					mySprite.setVisible(true);
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					mySprite.setVisible(false);
				}
			}));
		}
	}
	
	private void reStartGame() {
		isTouchScene = false;
		isWin = false;
		isSoundLight = false;
		animateMusic(asA3_63, 0, 1);
		animateMusic(asA3_68, 0, 1);
		animateMusic(asA3_613, 0, 1);
		animateMusic(asA3_618, 0, 1);
		animateMusic(asA3_623, 0, 1);
		animateMusic(asA3_628, 0, 1);
		
		idex = 0;
		if(test != null) {
			test = null;
			Log.d(TAG, "test.....................");
		}
		
		asA3_324.stopAnimation();
		asA3_324.setVisible(false);
		asA3_326.stopAnimation();
		asA3_326.setVisible(false);
		asA3_328.stopAnimation();
		asA3_328.setVisible(false);
		asA3_330.stopAnimation();
		asA3_330.setVisible(false);
		asA3_332.stopAnimation();
		asA3_332.setVisible(false);
		
		//girl
		asA3_354.setCurrentTileIndex(0);
		asA3_354.setVisible(true);
		asA3_358.setCurrentTileIndex(0);
		asA3_358.setVisible(true);
		asA3_362.setCurrentTileIndex(0);
		asA3_362.setVisible(true);
		asA3_366.setCurrentTileIndex(0);
		asA3_366.setVisible(true);
		asA3_370.setCurrentTileIndex(0);
		asA3_370.setVisible(true);
		//boy
		asA3_334.setCurrentTileIndex(0);
		asA3_334.setVisible(true);
		asA3_334.setPosition(asA3_334.getmXFirst(), asA3_334.getmYFirst());
		asA3_338.setCurrentTileIndex(0);
		asA3_338.setVisible(true);
		asA3_338.setPosition(asA3_338.getmXFirst(), asA3_338.getmYFirst());
		asA3_342.setCurrentTileIndex(0);
		asA3_342.setVisible(true);
		asA3_342.setPosition(asA3_342.getmXFirst(), asA3_342.getmYFirst());
		asA3_346.setCurrentTileIndex(0);
		asA3_346.setVisible(true);
		asA3_346.setPosition(asA3_346.getmXFirst(), asA3_346.getmYFirst());
		asA3_350.setCurrentTileIndex(0);
		asA3_350.setVisible(true);
		asA3_350.setPosition(asA3_350.getmXFirst(), asA3_350.getmYFirst());
		//child result
		if(sA3_31.isVisible() && sA3_31 != null) {
			sA3_31.setVisible(false);
		}
		if(sA3_32.isVisible() && sA3_32 != null) {
			sA3_32.setVisible(false);
		}
		if(sA3_33.isVisible() && sA3_33 != null) {
			sA3_33.setVisible(false);
		}
		if(sA3_34.isVisible() && sA3_34 != null) {
			sA3_34.setVisible(false);
		}
		if(sA3_35.isVisible() && sA3_35 != null) {
			sA3_35.setVisible(false);
		}
		
		//child result
		if(sA3_39.isVisible() && sA3_39 != null) {
			sA3_39.setVisible(false);
		}
		if(sA3_313.isVisible() && sA3_313 != null) {
			sA3_313.setVisible(false);
		}
		if(sA3_316.isVisible() && sA3_316 != null) {
			sA3_316.setVisible(false);
		}
		if(sA3_319.isVisible() && sA3_319 != null) {
			sA3_319.setVisible(false);
		}
		if(sA3_323.isVisible() && sA3_323 != null) {
			sA3_323.setVisible(false);
		}
		
		//dance
		if(asA3_324 != null) {
			asA3_324.setVisible(false);
		}
		if(asA3_326 != null) {
			asA3_326.setVisible(false);
		}
		if(asA3_328 != null) {
			asA3_328.setVisible(false);
		}
		if(asA3_330 != null) {
			asA3_330.setVisible(false);
		}
		if(asA3_332 != null) {
			asA3_332.setVisible(false);
		}
		if(asGirl_ != null) {
			asGirl_ = null;
		}
		if(alRandom.isEmpty()) {
			//alRandom.clear();
		}
		
		//den chieu sang
		if(sA3_51 != null) {
			sA3_51.clearEntityModifiers();
			sA3_51.setVisible(false);
		}
		if(sA3_52 != null) {
			sA3_52.clearEntityModifiers();
			sA3_52.setVisible(false);
		}
		if(sA3_53 != null) {
			sA3_53.clearEntityModifiers();
			sA3_53.setVisible(false);
		}
		if(sA3_54 != null) {
			sA3_54.clearEntityModifiers();
			sA3_54.setVisible(false);
		}
		mScene.unregisterEntityModifier(hightLight1);
		mScene.clearEntityModifiers();

		
		mp3A3_3_5Kumitango.stop();
		mp3A3_3_5Kumipea.stop();
		mp3A3_3Hart.stop();
		mp3A3_5_Kiran.stop();
		is39 = false;
		is313 = false;
		is316 = false;
		is319 = false;
		is323 = false;
		isDance = false;
		//--------------------
		randomChilds();
		return;
	}

	@Override
	public void combineGimmic3WithAction() {
		actionMouse();
	}
}