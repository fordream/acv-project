package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YakiimoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Yakiimo extends BaseGameActivity implements IOnSceneTouchListener{
	// TAG 
	private String TAG = "VOL3_YAKIIMO";
	
	private int INT_COUNT;
	
	//STATUS
	private int STATUS_B = 0;
	private int STATUS_C = 1;
	private int STATUS_D = 2;
	private int STATUS_E = 3;
	private int STATUS_G = 4;
	private int STATUS_H = 5;
	private int STATUS_I = 6;
	
	//EAT STATUS
	private int EAT_STATUS = 0;
	
	// INT INDEX
	private int MEAT = 0;
	private int FISH = 1;
	private int POTATO = 2;
	private int CORN = 3;
	
	private int ANIMSPR_GIRL = 0;
	private int ANIMSPR_RABIT = 1;
	private int ANIMSPR_BOY = 2;
	private int ANIMSPR_BEAR = 3;
	
	//INT STATUS DETECTED
	private int NO_IS_DETECTED = 0;
	private int IS_DETECTED = 1;
	
	//INT STATUS WITH HUMAN APPER
	private int GIRL_HM;
	private int RABIT_HM;
	private int BOY_HM;
	private int BEAR_HM;
	
	private int FAIRY10_HM;
	private int FAIRY11_HM;
	
	private int eat_FOOD;
	
	// LOAD
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack texpackBackGround,texpackFire,texpackFire_Human,texpack_Normal;
	private TexturePackTextureRegionLibrary texpackLibBackGround,texpackLibFire_Human,texpackLib_Normal;
	
	// GIRL
	private TexturePack texpackGIRL_MEAT;
	private TexturePack texpackGIRL_FISH;
	private TexturePack texpackGIRL_POTATO;
	private TexturePack texpackGIRL_CORN;
	private TexturePackTextureRegionLibrary texpackLibGIRL_MEAT;
	private TexturePackTextureRegionLibrary texpackLibGIRL_FISH;
	private TexturePackTextureRegionLibrary texpackLibGIRL_POTATO;
	private TexturePackTextureRegionLibrary texpackLibGIRL_CORN;
	
	// RABIT
	private TexturePack texpackRABIT_MEAT;
	private TexturePack texpackRABIT_FISH;
	private TexturePack texpackRABIT_POTATO;
	private TexturePack texpackRABIT_CORN;
	private TexturePackTextureRegionLibrary texpackLibRABIT_MEAT;
	private TexturePackTextureRegionLibrary texpackLibRABIT_FISH;
	private TexturePackTextureRegionLibrary texpackLibRABIT_POTATO;
	private TexturePackTextureRegionLibrary texpackLibRABIT_CORN;
	
	// BOY
	private TexturePack texpackBOY_MEAT;
	private TexturePack texpackBOY_FISH;
	private TexturePack texpackBOY_POTATO;
	private TexturePack texpackBOY_CORN;
	private TexturePackTextureRegionLibrary texpackLibBOY_MEAT;
	private TexturePackTextureRegionLibrary texpackLibBOY_FISH;
	private TexturePackTextureRegionLibrary texpackLibBOY_POTATO;
	private TexturePackTextureRegionLibrary texpackLibBOY_CORN;
		
	// BEAR
	private TexturePack texpackBEAR_MEAT;
	private TexturePack texpackBEAR_FISH;
	private TexturePack texpackBEAR_POTATO;
	private TexturePack texpackBEAR_CORN;
	private TexturePackTextureRegionLibrary texpackLibBEAR_MEAT;
	private TexturePackTextureRegionLibrary texpackLibBEAR_FISH;
	private TexturePackTextureRegionLibrary texpackLibBEAR_POTATO;
	private TexturePackTextureRegionLibrary texpackLibBEAR_CORN;
	
	//BACK GROUND
	private Sprite sprBackGround;
	private TextureRegion ttrBackGround;
	
	// TREE LEFT
	private Sprite sprTreeLeft;
	private TextureRegion ttrTreeLeft;
	
	private Sprite sprTreeLeafLeft;
	private TextureRegion ttrTreeLeafLeft;
	
	// TREE RIGHT
	private Sprite sprTreeRight;
	private TextureRegion ttrTreeRight;
	
	private SpritePool sprPoolStar1;
	private SpritePool sprPoolStar2;
	private SpritePool sprPoolStar3;
	private ITextureRegion ttrPoolStar;
	private ArrayList<Sprite> arrayPoolStar1 = new ArrayList<Sprite>();
	private ArrayList<Sprite> arrayPoolStar2 = new ArrayList<Sprite>();
	private ArrayList<Sprite> arrayPoolStar3 = new ArrayList<Sprite>();
	
	// LEAF FLY
	private Sprite sprLeafFly[] = new Sprite[4];
	private TextureRegion ttrLeafFly[] = new TextureRegion[4];
	private int pPositionLeafFly[][] = {{312,102}, {248,106}, {271,126}, {274,87}};
	
	// FAIRY 11
	private AnimatedSprite animsprFairy11;
	private ITiledTextureRegion tttrFairy11;
	
	private Sprite sprTreeLeafRight[] = new Sprite[5];
	private TextureRegion ttrTreeLeafRight[] = new TextureRegion[5];
	private int pPositionLeaf[][] = {{557,-90},{607,-90},{672,-88},{812,-91},{822,38}};
	
	//FAIRY 10
	private AnimatedSprite animsprFairy10;
	private ITiledTextureRegion tttrFairy10;
	
	// FIRE
	private AnimatedSprite animsprFire_Normal;
	private ITiledTextureRegion tttrFire_Normal;
	
	private AnimatedSprite animsprFire;
	private ITiledTextureRegion ttrFire;
	
	// FIRE_LEAF
	private Sprite spr_Fire_Leaf;
	private TextureRegion ttr_Fire_Leaf;
	
	// GIRL
	private AnimatedSprite animsprGirl[] = new AnimatedSprite[4];
	private ITiledTextureRegion tttrGirl[] = new ITiledTextureRegion[4];
	
	// RABIT
	private AnimatedSprite animsprRabit[] = new AnimatedSprite[4];
	private ITiledTextureRegion tttrRabit[] = new ITiledTextureRegion[4];
	
	// BOY
	private AnimatedSprite animsprBoy[] = new AnimatedSprite[4];
	private ITiledTextureRegion tttrBoy[] = new ITiledTextureRegion[4];
		
	// BEAR
	private AnimatedSprite animsprBear[] = new AnimatedSprite[4];
	private ITiledTextureRegion tttrBear[] = new ITiledTextureRegion[4];
	
	// NORMAL
	private AnimatedSprite animsprNORMAL[] = new AnimatedSprite[4];
	private ITiledTextureRegion tttrNORMAL[] = new ITiledTextureRegion[4];
	private int pPositionNORMAL[][] = {{115,217},{220,90},{433,0},{538,76}};
	
	// HUMAN
	private AnimatedSprite animsprHUMAN;
	private ITiledTextureRegion tttrHUMAN;
	
	// FOOD MEAT POSITION 
	private int pPosition_MEAT[][] = {{700,395}, {758,395},{815,395},{875,395}};
	
	// FOOD FISH POSITION 
	private int pPosition_FISH[][] = {{675,380}, {740,380},{800,380},{860,380}};
	
	// FOOD POTATO POSITION 
	private int pPosition_POTATO[][] = {{660,395}, {725,395},{790,395},{855,395}};
	
	// FOOD CORN POSITION 
	private int pPosition_CORN[][] = {{665,387}, {725,387},{785,387},{845,387}};
	
	// FOOD
	private AnimFOOD animspr_FOOD[][] = new AnimFOOD[4][4];
	private TiledTextureRegion tttrFOOD[] = new TiledTextureRegion[4];
	
	// FOOD CURRENT
	private int foodCURRENT;
	
	// FLOAT ARRAY ANIMSPR
	//10
	private float arrPoint_GIRL[][] = {{97,165,200,180,230,220,150,65,92,15,97}, {10,35,85,150,155,280,300,225,155,150,10}};
	//4
	private float arrPoint_RABIT[][] = {{101,243,243,101,101}, {25,25,276,276,25}};
	//4
	private float arrPoint_BOY[][] = {{33,175,175,33,33}, {38,38,338,338,38}};
	//8
	private float arrPoint_BEAR[][] = {{120,120,300,330,300,135,17,17,120}, {185,40,40,255,405,438,325,244,185}};
	// CART 
	private Sprite sprCart;
	private TextureRegion ttrCart;
	
	// RETANGULAR
	private RectangularShape shapeStar;
	
	// BOOLEAN
	private boolean isTouchGimmic;
	private boolean isTouchFairy;
	private boolean isTouchFire;
	
	private boolean touch_GIRL;
	private boolean touch_RABIT;
	private boolean touch_BOY;
	private boolean touch_BEAR;
	
	private boolean eat_ALLOW;
	
	private boolean touchHuman;
	
	// TIMER
	private PauseResumeTimerHandler timerFOOD_BURN;
	private PauseResumeTimerHandler timerFOOD;
	private TimerHandler timerStar;
	private PauseResumeTimerHandler timerPlaySE;
	
	private TimerHandler timerTouchHuman;
	
	private int girlSE;
	private int rabitSE;
	private int boySE;
	private int bearSE;
	
	// POINT EAT
	private float arrPoint_EAT[][] = {
		{130,212,310,332,442,428,506,586,608,672,825,875,846,728,550,446,306,194,130}, 
		{350,221,270,110,130,252,32,31,275,108,110,290,467,498,296,307,496,499,350}};
	
	// SOUND
	private Sound OGG_A2_04_05_WATASHIMO;
	private Sound OGG_A2_06_07_BOKUNIMO;
	private Sound OGG_A2_10_KIRAN;
	private Sound OGG_A2_11_KIRA6;
	private Sound OGG_A2_8_YAITERU2;
	private Sound OGG_A2_9_BOWA;
	private Sound OGG_A2_9_GASBANA;
	private Sound OGG_A2_9_PATI;
	private Sound OGG_A2_A_KEIKOKU2;
	private Sound OGG_A2_B_POMI;
	private Sound OGG_A2_C_KASA;
	private Sound OGG_A2_D_YAKETA;
	private Sound OGG_A2_F_TABERU;
	private Sound OGG_A2_G_OISHI;
	private Sound OGG_A2_H_YAKETENAI2;
	private Sound OGG_A2_I_KOGE7;
	
	// onCreateResources
	@Override
	public void onCreateResources() {
		Log.d(TAG, "Load --- onCreateResources");
		SoundFactory.setAssetBasePath("yakiimo/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("yakiimo/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
			getTextureManager(), pAssetManager, "yakiimo/gfx/");
		super.onCreateResources();
		Log.d(TAG, "Load --- onCreateResources --- End");
	}
	
	// loadKaraokeResources
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "Load --- loadKaraokeResources");
		//LIBRARY
		texpackBackGround = mTexturePackLoaderFromSource.load("background.xml");
		texpackBackGround.loadTexture();
		texpackLibBackGround = texpackBackGround.getTexturePackTextureRegionLibrary();
		
		//FIRE
		texpackFire_Human = mTexturePackLoaderFromSource.load("fire_human.xml");
		texpackFire_Human.loadTexture();
		texpackLibFire_Human = texpackFire_Human.getTexturePackTextureRegionLibrary();
		
		texpackFire = mTexturePackLoaderFromSource.load("fire.xml");
		texpackFire.loadTexture();
		
		//GIRL TEXTURE
		/************* Load GIRL MEAT *****************/
		texpackGIRL_MEAT = mTexturePackLoaderFromSource.load("girl_meat.xml");
		texpackGIRL_MEAT.loadTexture();
		texpackLibGIRL_MEAT = texpackGIRL_MEAT.getTexturePackTextureRegionLibrary();
		
		/************* Load GIRL FISH *****************/
		texpackGIRL_FISH = mTexturePackLoaderFromSource.load("girl_fish.xml");
		texpackGIRL_FISH.loadTexture();
		texpackLibGIRL_FISH = texpackGIRL_FISH.getTexturePackTextureRegionLibrary();
		
		/************* Load GIRL POTATO *****************/
		texpackGIRL_POTATO = mTexturePackLoaderFromSource.load("girl_potato.xml");
		texpackGIRL_POTATO.loadTexture();
		texpackLibGIRL_POTATO = texpackGIRL_POTATO.getTexturePackTextureRegionLibrary();
		
		/************* Load GIRL CORN *****************/
		texpackGIRL_CORN = mTexturePackLoaderFromSource.load("girl_corn.xml");
		texpackGIRL_CORN.loadTexture();
		texpackLibGIRL_CORN = texpackGIRL_CORN.getTexturePackTextureRegionLibrary();
		
		//RABIT TEXTURE
		/************* Load RABIT MEAT *****************/
		texpackRABIT_MEAT = mTexturePackLoaderFromSource.load("rabit_meat.xml");
		texpackRABIT_MEAT.loadTexture();
		texpackLibRABIT_MEAT = texpackRABIT_MEAT.getTexturePackTextureRegionLibrary();
		
		/************* Load RABIT FISH *****************/
		texpackRABIT_FISH = mTexturePackLoaderFromSource.load("rabit_fish.xml");
		texpackRABIT_FISH.loadTexture();
		texpackLibRABIT_FISH = texpackRABIT_FISH.getTexturePackTextureRegionLibrary();
		
		/************* Load RABIT POTATO *****************/
		texpackRABIT_POTATO = mTexturePackLoaderFromSource.load("rabit_potato.xml");
		texpackRABIT_POTATO.loadTexture();
		texpackLibRABIT_POTATO = texpackRABIT_POTATO.getTexturePackTextureRegionLibrary();
		
		/************* Load RABIT CORN *****************/
		texpackRABIT_CORN = mTexturePackLoaderFromSource.load("rabit_corn.xml");
		texpackRABIT_CORN.loadTexture();
		texpackLibRABIT_CORN = texpackRABIT_CORN.getTexturePackTextureRegionLibrary();
		
		//BOY TEXTURE
		/************* Load BOY MEAT *****************/
		texpackBOY_MEAT = mTexturePackLoaderFromSource.load("boy_meat.xml");
		texpackBOY_MEAT.loadTexture();
		texpackLibBOY_MEAT = texpackBOY_MEAT.getTexturePackTextureRegionLibrary();
		
		/************* Load BOY FISH *****************/
		texpackBOY_FISH = mTexturePackLoaderFromSource.load("boy_fish.xml");
		texpackBOY_FISH.loadTexture();
		texpackLibBOY_FISH = texpackBOY_FISH.getTexturePackTextureRegionLibrary();
		
		/************* Load BOY POTATO *****************/
		texpackBOY_POTATO = mTexturePackLoaderFromSource.load("boy_potato.xml");
		texpackBOY_POTATO.loadTexture();
		texpackLibBOY_POTATO = texpackBOY_POTATO.getTexturePackTextureRegionLibrary();
		
		/************* Load BOY CORN *****************/
		texpackBOY_CORN = mTexturePackLoaderFromSource.load("boy_corn.xml");
		texpackBOY_CORN.loadTexture();
		texpackLibBOY_CORN = texpackBOY_CORN.getTexturePackTextureRegionLibrary();
		
		//BEAR TEXTURE
		/************* Load BEAR MEAT *****************/
		texpackBEAR_MEAT = mTexturePackLoaderFromSource.load("bear_meat.xml");
		texpackBEAR_MEAT.loadTexture();
		texpackLibBEAR_MEAT = texpackBEAR_MEAT.getTexturePackTextureRegionLibrary();
		
		/************* Load BEAR FISH *****************/
		texpackBEAR_FISH = mTexturePackLoaderFromSource.load("bear_fish.xml");
		texpackBEAR_FISH.loadTexture();
		texpackLibBEAR_FISH = texpackBEAR_FISH.getTexturePackTextureRegionLibrary();
		
		/************* Load BEAR POTATO *****************/
		texpackBEAR_POTATO = mTexturePackLoaderFromSource.load("bear_potato.xml");
		texpackBEAR_POTATO.loadTexture();
		texpackLibBEAR_POTATO = texpackBEAR_POTATO.getTexturePackTextureRegionLibrary();
		
		/************* Load BEAR CORN *****************/
		texpackBEAR_CORN = mTexturePackLoaderFromSource.load("bear_corn.xml");
		texpackBEAR_CORN.loadTexture();
		texpackLibBEAR_CORN = texpackBEAR_CORN.getTexturePackTextureRegionLibrary();
		
		//NORMAL
		texpack_Normal = mTexturePackLoaderFromSource.load("normal.xml");
		texpack_Normal.loadTexture();
		texpackLib_Normal = texpack_Normal.getTexturePackTextureRegionLibrary();
		
		//BACK GROUND
		ttrBackGround = texpackLibBackGround.get(Vol3YakiimoResource.A2_13_IPHONE_HAIKEI_ID);
		
		//TREE LEFT
		ttrTreeLeft = texpackLibBackGround.get(Vol3YakiimoResource.A2_10_10_IPHONE_TREE_ID);
		ttrTreeLeafLeft = texpackLibBackGround.get(Vol3YakiimoResource.A2_10_9_IPHONE_TREE_ID);
		
		//TREE RIGHT
		ttrTreeRight = texpackLibBackGround.get(Vol3YakiimoResource.A2_11_9_IPHONE_TREE_ID);
		for (int i = 0; i < ttrTreeLeafRight.length; i++) {
			ttrTreeLeafRight[i] = texpackLibBackGround.get(Vol3YakiimoResource.spr_TREE_RIGHT[i]);
		}
		
		//FAIRY
		//11
		tttrFairy11 = getTiledTextureFromPacker(texpackBackGround, Vol3YakiimoResource.A2_11_1_IPHONE_YOUSEI_ID,
			Vol3YakiimoResource.A2_11_2_IPHONE_YOUSEI_ID);
		//STAR
		ttrPoolStar = texpackLibBackGround.get(Vol3YakiimoResource.A2_11_3_IPHONE_STAR_ID);
		
		//10
		tttrFairy10 = getTiledTextureFromPacker(texpackBackGround, Vol3YakiimoResource.A2_10_1_IPHONE_YOUSEI_ID,
			Vol3YakiimoResource.A2_10_2_IPHONE_YOUSEI_ID,Vol3YakiimoResource.A2_10_3_IPHONE_YOUSEI_ID,
			Vol3YakiimoResource.A2_10_4_IPHONE_YOUSEI_ID);
		
		//LEAF FLY
		for (int i = 0; i < ttrLeafFly.length; i++) {
			ttrLeafFly[i] = texpackLibBackGround.get(Vol3YakiimoResource.spr_LEAF_FLY[i]);
		}
		
		//FIRE NORMAL
		tttrFire_Normal = getTiledTextureFromPacker(texpackFire_Human, Vol3YakiimoResource.animspr_FIRE_NORMAL);
		ttrFire = getTiledTextureFromPacker(texpackFire, Vol3YakiimoResource.animspr_FIRE);
		
		//FIRE_LEAF
		ttr_Fire_Leaf = texpackLibFire_Human.get(Vol3YakiimoResource.A2_9B_IPHONE_FIRE_ID);
		
		//GIRL
		for (int i = 0; i < tttrGirl.length; i++) {
			ITextureRegion[] tmpGirl = new ITextureRegion[Vol3YakiimoResource.int_GIRL_ID[i].length];
			for (int j = 0; j < Vol3YakiimoResource.int_GIRL_ID[i].length; j++) {
				if (i == 0) {
					tmpGirl[j] = texpackLibGIRL_MEAT.get(Vol3YakiimoResource.int_GIRL_ID[i][j]);
				} 
				if (i == 1) {
					tmpGirl[j] = texpackLibGIRL_FISH.get(Vol3YakiimoResource.int_GIRL_ID[i][j]);
				}
				if (i == 2) {
					tmpGirl[j] = texpackLibGIRL_POTATO.get(Vol3YakiimoResource.int_GIRL_ID[i][j]);
				}
				if (i == 3) {
					tmpGirl[j] = texpackLibGIRL_CORN.get(Vol3YakiimoResource.int_GIRL_ID[i][j]);
				}
			}
			
			if (i == 0) {
				tttrGirl[MEAT] = new TiledTextureRegion(texpackGIRL_MEAT.getTexture(), tmpGirl);
			} 
			if (i == 1) {
				tttrGirl[FISH] = new TiledTextureRegion(texpackGIRL_FISH.getTexture(), tmpGirl);
			}
			if (i == 2) {
				tttrGirl[POTATO] = new TiledTextureRegion(texpackGIRL_POTATO.getTexture(), tmpGirl);
			} 
			if (i == 3) {
				tttrGirl[CORN] = new TiledTextureRegion(texpackGIRL_CORN.getTexture(), tmpGirl);
			}
		}

		//RABIT
		for (int i = 0; i < tttrRabit.length; i++) {
			ITextureRegion[] tmpRabit = new ITextureRegion[Vol3YakiimoResource.int_RABIT_ID[i].length];
			for (int j = 0; j < Vol3YakiimoResource.int_RABIT_ID[i].length; j++) {
				if (i == 0) {
					tmpRabit[j] = texpackLibRABIT_MEAT.get(Vol3YakiimoResource.int_RABIT_ID[i][j]);
				} 
				if (i == 1) {
					tmpRabit[j] = texpackLibRABIT_FISH.get(Vol3YakiimoResource.int_RABIT_ID[i][j]);
				}
				if (i == 2) {
					tmpRabit[j] = texpackLibRABIT_POTATO.get(Vol3YakiimoResource.int_RABIT_ID[i][j]);
				}
				if (i == 3) {
					tmpRabit[j] = texpackLibRABIT_CORN.get(Vol3YakiimoResource.int_RABIT_ID[i][j]);
				}
			}
			
			if (i == 0) {
				tttrRabit[MEAT] = new TiledTextureRegion(texpackRABIT_MEAT.getTexture(), tmpRabit);
			} 
			if (i == 1) {
				tttrRabit[FISH] = new TiledTextureRegion(texpackRABIT_FISH.getTexture(), tmpRabit);
			}
			if (i == 2) {
				tttrRabit[POTATO] = new TiledTextureRegion(texpackRABIT_POTATO.getTexture(), tmpRabit);
			} 
			if (i == 3) {
				tttrRabit[CORN] = new TiledTextureRegion(texpackRABIT_CORN.getTexture(), tmpRabit);
			}
		}
		
		//BOY
		for (int i = 0; i < tttrBoy.length; i++) {
			ITextureRegion[] tmpBoy = new ITextureRegion[Vol3YakiimoResource.int_BOY_ID[i].length];
			for (int j = 0; j < Vol3YakiimoResource.int_BOY_ID[i].length; j++) {
				if (i == 0) {
					tmpBoy[j] = texpackLibBOY_MEAT.get(Vol3YakiimoResource.int_BOY_ID[i][j]);
				} 
				if (i == 1) {
					tmpBoy[j] = texpackLibBOY_FISH.get(Vol3YakiimoResource.int_BOY_ID[i][j]);
				}
				if (i == 2) {
					tmpBoy[j] = texpackLibBOY_POTATO.get(Vol3YakiimoResource.int_BOY_ID[i][j]);
				}
				if (i == 3) {
					tmpBoy[j] = texpackLibBOY_CORN.get(Vol3YakiimoResource.int_BOY_ID[i][j]);
				}
			}
			
			if (i == 0) {
				tttrBoy[MEAT] = new TiledTextureRegion(texpackBOY_MEAT.getTexture(), tmpBoy);
			} 
			if (i == 1) {
				tttrBoy[FISH] = new TiledTextureRegion(texpackBOY_FISH.getTexture(), tmpBoy);
			}
			if (i == 2) {
				tttrBoy[POTATO] = new TiledTextureRegion(texpackBOY_POTATO.getTexture(), tmpBoy);
			} 
			if (i == 3) {
				tttrBoy[CORN] = new TiledTextureRegion(texpackBOY_CORN.getTexture(), tmpBoy);
			}
		}
		
		//BEAR
		for (int i = 0; i < tttrBear.length; i++) {
			ITextureRegion[] tmpBear = new ITextureRegion[Vol3YakiimoResource.int_BEAR_ID[i].length];
			for (int j = 0; j < Vol3YakiimoResource.int_BOY_ID[i].length; j++) {
				if (i == 0) {
					tmpBear[j] = texpackLibBEAR_MEAT.get(Vol3YakiimoResource.int_BEAR_ID[i][j]);
				} 
				if (i == 1) {
					tmpBear[j] = texpackLibBEAR_FISH.get(Vol3YakiimoResource.int_BEAR_ID[i][j]);
				}
				if (i == 2) {
					tmpBear[j] = texpackLibBEAR_POTATO.get(Vol3YakiimoResource.int_BEAR_ID[i][j]);
				}
				if (i == 3) {
					tmpBear[j] = texpackLibBEAR_CORN.get(Vol3YakiimoResource.int_BEAR_ID[i][j]);
				}
			}
			
			if (i == 0) {
				tttrBear[MEAT] = new TiledTextureRegion(texpackBEAR_MEAT.getTexture(), tmpBear);
			} 
			if (i == 1) {
				tttrBear[FISH] = new TiledTextureRegion(texpackBEAR_FISH.getTexture(), tmpBear);
			}
			if (i == 2) {
				tttrBear[POTATO] = new TiledTextureRegion(texpackBEAR_POTATO.getTexture(), tmpBear);
			} 
			if (i == 3) {
				tttrBear[CORN] = new TiledTextureRegion(texpackBEAR_CORN.getTexture(), tmpBear);
			}
		}
		
		//NORMAL
		for (int i = 0; i < tttrNORMAL.length; i++) {
			ITextureRegion[] tmpNORMAL = new ITextureRegion[Vol3YakiimoResource.int_NORMAL[i].length];
			for (int j = 0; j < Vol3YakiimoResource.int_NORMAL[i].length; j++) {
				if (i == 0) {
					tmpNORMAL[j] = texpackLib_Normal.get(Vol3YakiimoResource.int_NORMAL[i][j]);
				} 
				if (i == 1) {
					tmpNORMAL[j] = texpackLib_Normal.get(Vol3YakiimoResource.int_NORMAL[i][j]);
				}
				if (i == 2) {
					tmpNORMAL[j] = texpackLib_Normal.get(Vol3YakiimoResource.int_NORMAL[i][j]);
				}
				if (i == 3) {
					tmpNORMAL[j] = texpackLib_Normal.get(Vol3YakiimoResource.int_NORMAL[i][j]);
				}
			}
			
			if (i == 0) {
				tttrNORMAL[0] = new TiledTextureRegion(texpack_Normal.getTexture(), tmpNORMAL);
			} 
			if (i == 1) {
				tttrNORMAL[1] = new TiledTextureRegion(texpack_Normal.getTexture(), tmpNORMAL);
			}
			if (i == 2) {
				tttrNORMAL[2] = new TiledTextureRegion(texpack_Normal.getTexture(), tmpNORMAL);
			} 
			if (i == 3) {
				tttrNORMAL[3] = new TiledTextureRegion(texpack_Normal.getTexture(), tmpNORMAL);
			}
		}
		
		//FOOD
		//tttrFOOD
		for (int i = 0; i < tttrFOOD.length; i++) {
			tttrFOOD[i] = getTiledTextureFromPacker(texpackBackGround, Vol3YakiimoResource.int_Food[i]);
		}
		
		//CART
		ttrCart = texpackLibBackGround.get(Vol3YakiimoResource.A2_12_5_IPHONE_FOOD_ID);
		
		//HUMAN
		tttrHUMAN = getTiledTextureFromPacker(texpackFire_Human, Vol3YakiimoResource.A2_8_1_IPHONE_OJISAN_ID,
				Vol3YakiimoResource.A2_8_2_IPHONE_OJISAN_ID);
		
		Log.d(TAG, "Load --- loadKaraokeResources --- End");
	}

	// loadKaraokeSound
	@Override
	protected void loadKaraokeSound() {
		OGG_A2_04_05_WATASHIMO = loadSoundResourceFromSD(Vol3YakiimoResource.A2_04_05_WATASHIMO);
		OGG_A2_06_07_BOKUNIMO = loadSoundResourceFromSD(Vol3YakiimoResource.A2_06_07_BOKUNIMO);
		OGG_A2_10_KIRAN = loadSoundResourceFromSD(Vol3YakiimoResource.A2_10_KIRAN);
		OGG_A2_11_KIRA6 = loadSoundResourceFromSD(Vol3YakiimoResource.A2_11_KIRA6);
		OGG_A2_8_YAITERU2 = loadSoundResourceFromSD(Vol3YakiimoResource.A2_8_YAITERU2);
		OGG_A2_9_BOWA = loadSoundResourceFromSD(Vol3YakiimoResource.A2_9_BOWA);
		OGG_A2_9_GASBANA = loadSoundResourceFromSD(Vol3YakiimoResource.A2_9_GASBANA);
		OGG_A2_9_PATI = loadSoundResourceFromSD(Vol3YakiimoResource.A2_9_PATI);
		OGG_A2_A_KEIKOKU2 = loadSoundResourceFromSD(Vol3YakiimoResource.A2_A_KEIKOKU2);
		OGG_A2_B_POMI = loadSoundResourceFromSD(Vol3YakiimoResource.A2_B_POMI);
		OGG_A2_C_KASA = loadSoundResourceFromSD(Vol3YakiimoResource.A2_C_KASA);
		OGG_A2_D_YAKETA = loadSoundResourceFromSD(Vol3YakiimoResource.A2_D_YAKETA);
		OGG_A2_F_TABERU = loadSoundResourceFromSD(Vol3YakiimoResource.A2_F_TABERU);
		OGG_A2_G_OISHI = loadSoundResourceFromSD(Vol3YakiimoResource.A2_G_OISHI);
		OGG_A2_H_YAKETENAI2 = loadSoundResourceFromSD(Vol3YakiimoResource.A2_H_YAKETENAI2);
		OGG_A2_I_KOGE7 = loadSoundResourceFromSD(Vol3YakiimoResource.A2_I_KOGE7);

	}

	// loadKaraokeScene
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "Load --- loadKaraokeScene");
		mScene = new Scene();
		//BACK GROUND
		sprBackGround = new Sprite(0, 0, ttrBackGround, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackGround);
		
		//RETANGLE
		shapeStar = new Rectangle(0, 0, 960, 640, this.getVertexBufferObjectManager());
		mScene.attachChild(shapeStar);
		shapeStar.setAlpha(0.0f);
		
		//NORMAL
		for (int i = 0; i < animsprNORMAL.length; i++) {
			animsprNORMAL[i] = new AnimatedSprite(pPositionNORMAL[i][0], pPositionNORMAL[i][1], tttrNORMAL[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprNORMAL[i]);
		}
		
		//GIRL
		for (int i = 0; i < animsprGirl.length; i++) {
			animsprGirl[i] = new AnimatedSprite(115, 217, tttrGirl[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprGirl[i]);
			animsprGirl[i].setVisible(false);
		}
		
		//RABIT
		for (int i = 0; i < animsprRabit.length; i++) {
			animsprRabit[i] = new AnimatedSprite(220, 90, tttrRabit[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprRabit[i]);
			animsprRabit[i].setVisible(false);
		}
		
		//BOY
		for (int i = 0; i < animsprBoy.length; i++) {
			animsprBoy[i] = new AnimatedSprite(433, 0, tttrBoy[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprBoy[i]);
			animsprBoy[i].setVisible(false);
		}
		
		//BEAR
		for (int i = 0; i < animsprBear.length; i++) {
			animsprBear[i] = new AnimatedSprite(538, 76, tttrBear[i], this.getVertexBufferObjectManager());
			mScene.attachChild(animsprBear[i]);
			animsprBear[i].setVisible(false);
		}
		
		//HUMAN
		animsprHUMAN = new AnimatedSprite(57, 226, tttrHUMAN, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprHUMAN);
		
		//TREE RIGHT
		sprTreeRight = new Sprite(754, 199, ttrTreeRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTreeRight);
		
		for (int i = 0; i < sprTreeLeafRight.length-1; i++) {
			sprTreeLeafRight[i] = new Sprite(pPositionLeaf[i][0], pPositionLeaf[i][1], ttrTreeLeafRight[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprTreeLeafRight[i]);
		}
		
		//FAIRY 11
		animsprFairy11 = new AnimatedSprite(705, 11, tttrFairy11, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFairy11);
		
		sprTreeLeafRight[4] = new Sprite(pPositionLeaf[4][0], pPositionLeaf[4][1], ttrTreeLeafRight[4], this.getVertexBufferObjectManager());
		mScene.attachChild(sprTreeLeafRight[4]);
		
		//TREE LEFT
		sprTreeLeft = new Sprite(-73, 59, ttrTreeLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTreeLeft);
		
		for (int i = 0; i < sprLeafFly.length; i++) {
			sprLeafFly[i] = new Sprite(pPositionLeafFly[i][0], pPositionLeafFly[i][1], ttrLeafFly[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprLeafFly[i]);
		}
		
		sprTreeLeafLeft = new Sprite(-50, -82, ttrTreeLeafLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTreeLeafLeft);
		
		//FAIRY 10
		animsprFairy10 = new AnimatedSprite(118, 23, tttrFairy10, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFairy10);
		
		//FIRE_NORMAL
		animsprFire_Normal = new AnimatedSprite(246, 204, tttrFire_Normal, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFire_Normal);
		
		animsprFire = new AnimatedSprite(246, 204, ttrFire, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprFire);
		animsprFire.setVisible(false);
		
		//FIRE_LEAF
		spr_Fire_Leaf = new Sprite(246, 204, ttr_Fire_Leaf, this.getVertexBufferObjectManager());
		mScene.attachChild(spr_Fire_Leaf);
		spr_Fire_Leaf.setVisible(false);
		
		//FOOD
		//MEAT
		for (int i = 0; i < 4; i++) {
			animspr_FOOD[0][i] = new AnimFOOD(pPosition_MEAT[i][0], pPosition_MEAT[i][1], tttrFOOD[0], this.getVertexBufferObjectManager());
			mScene.attachChild(animspr_FOOD[0][i]);
			animspr_FOOD[0][i].setVisible(false);
			mScene.registerTouchArea(animspr_FOOD[0][i]);
		}
		
		//FISH
		for (int i = 0; i < 4; i++) {
			animspr_FOOD[1][i] = new AnimFOOD(pPosition_FISH[i][0], pPosition_FISH[i][1], tttrFOOD[1], this.getVertexBufferObjectManager());
			mScene.attachChild(animspr_FOOD[1][i]);
			animspr_FOOD[1][i].setVisible(false);
			mScene.registerTouchArea(animspr_FOOD[1][i]);
		}
		
		//POTATO
		for (int i = 0; i < 4; i++) {
			animspr_FOOD[2][i] = new AnimFOOD(pPosition_POTATO[i][0], pPosition_POTATO[i][1], tttrFOOD[2], this.getVertexBufferObjectManager());
			mScene.attachChild(animspr_FOOD[2][i]);
			animspr_FOOD[2][i].setRotation(20);
			animspr_FOOD[2][i].setVisible(false);
			mScene.registerTouchArea(animspr_FOOD[2][i]);
		}
		
		//CORN
		for (int i = 0; i < 4; i++) {
			animspr_FOOD[3][i] = new AnimFOOD(pPosition_CORN[i][0], pPosition_CORN[i][1], tttrFOOD[3], this.getVertexBufferObjectManager());
			mScene.attachChild(animspr_FOOD[3][i]);
			animspr_FOOD[3][i].setVisible(false);
			mScene.registerTouchArea(animspr_FOOD[3][i]);
		}
		
		//CART
		sprCart = new Sprite(720, 550, ttrCart, this.getVertexBufferObjectManager());
		mScene.attachChild(sprCart);
		sprCart.setScale(1.2f);
		
		//GIMMIC
		addGimmicsButton(mScene, Vol3YakiimoResource.SOUND_GIMMIC_YAKIIMO,
			Vol3YakiimoResource.IMAGE_GIMMIC_YAKIIMO, texpackLibBackGround);
		
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "Load --- loadKaraokeScene --- End");
	}

	//GIMMIC
	@Override
	public void combineGimmic3WithAction() {
		gimmicYakiimo();
	}
	//RESUME GAME
	@Override
	public synchronized void onResumeGame() {
		sprPoolStar1 = new SpritePool(ttrPoolStar);
		sprPoolStar2 = new SpritePool(ttrPoolStar);
		sprPoolStar3 = new SpritePool(ttrPoolStar);
		
		INT_COUNT = 0;
		
		eat_FOOD = 0;
		
		animatedAnim(animsprFire_Normal, 1000, 1000, 1000, 0, 1, 2, -1);
		
		foodCURRENT = MEAT;
		visibleFOOD(foodCURRENT);
		
		initializationGame();
		super.onResumeGame();
	}
	
	//PAUSE GAME
	@Override
	public synchronized void onPauseGame() {
		mp3Off();
		resetAll();
		super.onPauseGame();
	}
	
	// ===========================================================
	// GETTER & SETTER
	// ===========================================================

	// ===========================================================
	// METHODS FOR/ FROM SUPERCLASS / INTERFACES
	// ===========================================================

	//ON TOUCH SCENE
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.isActionDown()){
			//CHECK EAT
			if(checkContainsPolygon(sprBackGround, arrPoint_EAT, 18, pX, pY) && eat_ALLOW){
				eatFOOD();
			}
			
			if(checkContains(animsprHUMAN, 8, 80, 74, 280, pX, pY) && touchHuman){
				animsprHUMAN.setCurrentTileIndex(1);
				touchHuman = false;
				isTouchHuman();
			}
			
			//FAIRY_ GIMMIC
			if(checkContains(animsprFairy11, 58, 12, 177, 154, pX, pY)){
				gimmicYakiimo();
			}
			
			//FAIRY 10
			if(checkContains(animsprFairy10, 20, 20, 130, 160, pX, pY) && isTouchFairy){
				isTouchFairy = false;
				FAIRY10_HM = IS_DETECTED;
				OGG_A2_10_KIRAN.play();
				touchFairy10();
			}
			
			//FIRE
			if(checkContains(animsprFire_Normal, 140, 120, 360, 390, pX, pY) && isTouchFire){
				OGG_A2_9_BOWA.play();
				OGG_A2_9_PATI.play();
				isTouchFire = false;
				animsprFire_Normal.setVisible(false);
				animsprFire.setVisible(true);
				animatedAnim(animsprFire, 1000, 1000, 1000, 0, 1, 2, 0);
			}
			
			// TOUCH GIRL
			if(touch_GIRL && checkContainsPolygon(animsprNORMAL[ANIMSPR_GIRL], arrPoint_GIRL, 10, pX, pY)){
				girlSE = 1;
				GIRL_HM = IS_DETECTED;
				timerPlaySeStart();
				touch_GIRL = false;
				
				if(animspr_FOOD[foodCURRENT][0].getTouch() == true && animspr_FOOD[foodCURRENT][0].isVisible()){
					animspr_FOOD[foodCURRENT][0].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][0], animsprNORMAL[ANIMSPR_GIRL]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][1].getTouch() == true && animspr_FOOD[foodCURRENT][1].isVisible()){
					animspr_FOOD[foodCURRENT][1].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][1], animsprNORMAL[ANIMSPR_GIRL]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][2].getTouch() == true && animspr_FOOD[foodCURRENT][2].isVisible()){
					animspr_FOOD[foodCURRENT][2].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][2], animsprNORMAL[ANIMSPR_GIRL]);
				} 
				
				else if(animspr_FOOD[foodCURRENT][3].getTouch() == true && animspr_FOOD[foodCURRENT][3].isVisible()){
					animspr_FOOD[foodCURRENT][3].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][3], animsprNORMAL[ANIMSPR_GIRL]);
				}
			}
			
			// TOUCH RABIT
			if(touch_RABIT && checkContainsPolygon(animsprNORMAL[ANIMSPR_RABIT], arrPoint_RABIT, 4, pX, pY)){
				rabitSE = 1;
				RABIT_HM = IS_DETECTED;
				timerPlaySeStart();
				touch_RABIT = false;
				
				if(animspr_FOOD[foodCURRENT][0].getTouch() == true && animspr_FOOD[foodCURRENT][0].isVisible()){
					animspr_FOOD[foodCURRENT][0].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][0], animsprNORMAL[ANIMSPR_RABIT]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][1].getTouch() == true && animspr_FOOD[foodCURRENT][1].isVisible()){
					animspr_FOOD[foodCURRENT][1].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][1], animsprNORMAL[ANIMSPR_RABIT]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][2].getTouch() == true && animspr_FOOD[foodCURRENT][2].isVisible()){
					animspr_FOOD[foodCURRENT][2].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][2], animsprNORMAL[ANIMSPR_RABIT]);
				} 
				
				else if(animspr_FOOD[foodCURRENT][3].getTouch() == true && animspr_FOOD[foodCURRENT][3].isVisible()){
					animspr_FOOD[foodCURRENT][3].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][3], animsprNORMAL[ANIMSPR_RABIT]);
				}
			}
			
			// TOUCH BOY
			if(touch_BOY && checkContainsPolygon(animsprNORMAL[ANIMSPR_BOY], arrPoint_BOY, 4, pX, pY)){
				boySE = 1;
				BOY_HM = IS_DETECTED;
				timerPlaySeStart();
				touch_BOY = false;
				
				if(animspr_FOOD[foodCURRENT][0].getTouch() == true && animspr_FOOD[foodCURRENT][0].isVisible()){
					animspr_FOOD[foodCURRENT][0].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][0], animsprNORMAL[ANIMSPR_BOY]);
				}
				
				else if (animspr_FOOD[foodCURRENT][1].getTouch() == true && animspr_FOOD[foodCURRENT][1].isVisible()){
					animspr_FOOD[foodCURRENT][1].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][1], animsprNORMAL[ANIMSPR_BOY]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][2].getTouch() == true && animspr_FOOD[foodCURRENT][2].isVisible()){
					animspr_FOOD[foodCURRENT][2].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][2], animsprNORMAL[ANIMSPR_BOY]);
				} 
				
				else if(animspr_FOOD[foodCURRENT][3].getTouch() == true && animspr_FOOD[foodCURRENT][3].isVisible()){
					animspr_FOOD[foodCURRENT][3].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][3], animsprNORMAL[ANIMSPR_BOY]);
				}
			}
			
			// TOUCH BEAR
			if(touch_BEAR && checkContainsPolygon(animsprNORMAL[ANIMSPR_BEAR], arrPoint_BEAR, 8, pX, pY)){
				bearSE = 1;
				BEAR_HM = IS_DETECTED;
				timerPlaySeStart();
				touch_BEAR = false;
				
				if(animspr_FOOD[foodCURRENT][0].getTouch() == true && animspr_FOOD[foodCURRENT][0].isVisible()){
					animspr_FOOD[foodCURRENT][0].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][0], animsprNORMAL[ANIMSPR_BEAR]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][1].getTouch() == true && animspr_FOOD[foodCURRENT][1].isVisible()){
					animspr_FOOD[foodCURRENT][1].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][1], animsprNORMAL[ANIMSPR_BEAR]);
				} 
				
				else if (animspr_FOOD[foodCURRENT][2].getTouch() == true && animspr_FOOD[foodCURRENT][2].isVisible()){
					animspr_FOOD[foodCURRENT][2].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][2], animsprNORMAL[ANIMSPR_BEAR]);
				} 
				
				else if(animspr_FOOD[foodCURRENT][3].getTouch() == true && animspr_FOOD[foodCURRENT][3].isVisible()){
					animspr_FOOD[foodCURRENT][3].setTouch(false);
					foodModifier(animspr_FOOD[foodCURRENT][3], animsprNORMAL[ANIMSPR_BEAR]);
				}
			}
		}
		return false;
	}
	// ===========================================================
	// METHODS
	// ===========================================================
	
	// INITIALIZATION GAME
	private void initializationGame(){
		// SE STATUS
		currentSE();
		
		// STATUS WITH HUMAN
		statusHM(NO_IS_DETECTED);
		
		FAIRY10_HM = NO_IS_DETECTED;
		FAIRY11_HM = NO_IS_DETECTED;
		
		booleanTrue();
	}
	
	//BOOLEAN
	private void booleanTrue(){
		isTouchFairy = true;
		isTouchGimmic = true;
		isTouchFire = true;
		
		touchVALUE(true);
		
		// FOOD BOOLEAN
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				animspr_FOOD[i][j].setTouch(true);
			}
		}
		
		eat_ALLOW = false;
		
		touchHuman = true;
	}
	
	//VISIBLE FOOD
	private void visibleFOOD(int x ){
		switch (x) {
		case 0:
			setFoodVisible(true, 0);
			setFoodVisible(false, 1);
			setFoodVisible(false, 2);
			setFoodVisible(false, 3);
			break;
		case 1:
			setFoodVisible(false, 0);
			setFoodVisible(true, 1);
			setFoodVisible(false, 2);
			setFoodVisible(false, 3);
			break;
		case 2:
			setFoodVisible(false, 0);
			setFoodVisible(false, 1);
			setFoodVisible(true, 2);
			setFoodVisible(false, 3);
			break;
		case 3:
			setFoodVisible(false, 0);
			setFoodVisible(false, 1);
			setFoodVisible(false, 2);
			setFoodVisible(true, 3);
			break;
		default:
			break;
		}
	}
	
	// SET FOOD VISIBLE 
	private void setFoodVisible(boolean value,int x){
		if(value == true){
			for (int i = 0; i < 4; i++) {
				animspr_FOOD[x][i].setVisible(value);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				animspr_FOOD[x][i].setVisible(value);
			}
		}
	}
	
	// ANIMATED ANIMATEDSPRITE
		private void animatedAnim(AnimatedSprite animSpr, int a,int b, int c, int x , int y, int z, int loop){
			animSpr.stopAnimation();
			int pFrames[] = { x, y, z};
			long pFrameDurations[] = { a, b, c};
			animSpr.animate(pFrameDurations, pFrames, loop, new IAnimationListener() {
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
					if(arg0 == animsprFire){
						isTouchFire = true;
						animsprFire_Normal.setVisible(true);
						animsprFire.setVisible(false);
					}
				}
			});
		}
	
	//GIMMIC
	private void gimmicYakiimo(){
		if(isTouchGimmic){
			FAIRY11_HM = IS_DETECTED;
			isTouchGimmic = false;
			final Path path = new Path(4).to(705, 11).to(480, -10).to(150, 11).to(-200, -10);
			animsprFairy11.registerEntityModifier(new PathModifier(4.0f, path ,new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mScene.unregisterUpdateHandler(timerStar);
					animsprFairy11.setPosition(960, animsprFairy11.getmYFirst());
					animsprFairy11.registerEntityModifier(new MoveXModifier(1.0f, 960, 705, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchGimmic = true;
							FAIRY11_HM = NO_IS_DETECTED;
							animsprFairy11.setPosition(animsprFairy11.getmXFirst(), animsprFairy11.getmYFirst());
						}
					}));
				}
			}));
			visibleStar();
			addStar();
		}
	}
	
	// VISIBLE STAR
	private void visibleStar(){
		OGG_A2_11_KIRA6.play();
		mScene.unregisterUpdateHandler(timerStar);
		timerStar = new TimerHandler(0.5f, true,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				addStar();
			}
		});
		mScene.registerUpdateHandler(timerStar);
	}
	
	// ADD STAR
	private void addStar(){
		final Sprite _sprPoolStar1 = sprPoolStar1.obtainPoolItem();
		final Sprite _sprPoolStar2 = sprPoolStar2.obtainPoolItem();
		final Sprite _sprPoolStar3 = sprPoolStar3.obtainPoolItem();
		arrayPoolStar1.add(_sprPoolStar1);
		arrayPoolStar2.add(_sprPoolStar2);
		arrayPoolStar3.add(_sprPoolStar3);
		_sprPoolStar1.detachSelf();
		_sprPoolStar2.detachSelf();
		_sprPoolStar3.detachSelf();
		shapeStar.attachChild(_sprPoolStar1);
		shapeStar.attachChild(_sprPoolStar2);
		shapeStar.attachChild(_sprPoolStar3);
		_sprPoolStar1.setPosition(animsprFairy11.getX() + getNextInt(30, 60), animsprFairy11.getY() + getNextInt(50, 90));
		_sprPoolStar2.setPosition(animsprFairy11.getX() + getNextInt(80, 100) , animsprFairy11.getY() + getNextInt(65, 100));
		_sprPoolStar3.setPosition(animsprFairy11.getX() + getNextInt(140, 160) , animsprFairy11.getY() + getNextInt(30, 100));
		_sprPoolStar1.setScale((float) randomInRange(0.2, 0.9));
		_sprPoolStar2.setScale((float) randomInRange(0.2, 0.9));
		_sprPoolStar3.setScale((float) randomInRange(0.2, 0.9));
		animationForStar(_sprPoolStar1, arrayPoolStar1, sprPoolStar1,360);
		animationForStar(_sprPoolStar2, arrayPoolStar2, sprPoolStar2,-350);
		animationForStar(_sprPoolStar3, arrayPoolStar3, sprPoolStar3,360);
	}
	
	//ANIMATION FOR STAR
	private void animationForStar(final Sprite sprStar,final ArrayList<Sprite> array,final SpritePool sprPool, float sRotate){
		sprStar.clearEntityModifiers();
		sprStar.registerEntityModifier(new ParallelEntityModifier(
			new AlphaModifier(2.0f, 1.0f, 0.0f),
			new RotationAtModifier(2.5f, 0f, sRotate, sprStar.getWidth()/2, sprStar.getHeight()/2),
			new ScaleAtModifier(2.0f, sprStar.getScaleX(), 1.5f, sprStar.getWidth()/2, sprStar.getHeight()/2),
			new MoveYModifier(3.0f, sprStar.getY(), 360, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							array.remove(sprStar);
							sprPool.recyclePoolItem(sprStar);
						}
					});
				}
			})));
	}
	
	//RANDOM SCALE
	private double randomInRange(double min,double max) {
		return Math.random() < 0.5 ? ((1-Math.random()) * (max-min) + min) : (Math.random() * (max-min) + min);
	}
	
	//RANDOM INT
	public int getNextInt(int min, int max){
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}
	
	//FAIRY 10
	private void touchFairy10(){
		long sDuration[] = new long[] { 150, 300 , 300 };
		int sFrame[] = new int[] {0, 1, 2 };
		
		animsprFairy10.animate(sDuration, sFrame, 1);
		leafFly(sprLeafFly[0],380,195,420,405);
		leafFly(sprLeafFly[1],305,260,470,390);
		leafFly(sprLeafFly[2],340,265,450,400);
		leafFly(sprLeafFly[3],370,250,425,410);
	}
	
	//LEAF FLY
	private void leafFly(Sprite sprFly , float x1, float y1, float x2 , float y2){
		final Path path = new Path(3).to(sprFly.getmXFirst(), sprFly.getmYFirst()).to(x1, y1).to(x2, y2);
		sprFly.registerEntityModifier(new ParallelEntityModifier(
			new RotationAtModifier(2.0f, 0, 360, sprFly.getWidth()/2, sprFly.getHeight()/2),
			new PathModifier(2.0f, path , new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				OGG_A2_9_GASBANA.play();
				OGG_A2_9_PATI.stop();
				animsprFire.stopAnimation();
				isTouchFire = false;
				animsprFire.setVisible(false);
				spr_Fire_Leaf.setVisible(true);
				animsprFire_Normal.setVisible(false);
				spr_Fire_Leaf.registerEntityModifier(new DelayModifier(1.7f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						spr_Fire_Leaf.setVisible(false);
						animsprFire_Normal.setVisible(true);
						isTouchFairy = true;
						isTouchFire = true;
						animsprFairy10.setCurrentTileIndex(0);
						FAIRY10_HM = NO_IS_DETECTED;
					}
				}));
			}
		})));
	}
	
	// FOOD MODIFIER
	private void foodModifier(final AnimatedSprite sFrom, final AnimatedSprite sTo){
		touchHuman = false;
		timertouchHuman();
		OGG_A2_B_POMI.play();
		final float fromX = sFrom.getX();
		float toX = sTo.getX() + sTo.getWidth() / 2 - sFrom.getWidth() / 2;

		final float fromY = sFrom.getY();
		float toY = sTo.getY() + sTo.getHeight() / 2 - sFrom.getHeight() / 2;
		
		sFrom.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(1.0f, fromX, toX, fromY, toY),
				new DelayModifier(0.1f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
//						touchHuman = true;
						sFrom.setVisible(false);
						sFrom.setPosition(sFrom.getmXFirst(), sFrom.getmYFirst());
						if(sTo == animsprNORMAL[ANIMSPR_GIRL]){
							animsprNORMAL[ANIMSPR_GIRL].setVisible(false);
							animsprGirl[foodCURRENT].setVisible(true);
							animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_B);
							INT_COUNT++;
							checkCount();
						}
						
						if(sTo == animsprNORMAL[ANIMSPR_RABIT]){
							animsprNORMAL[ANIMSPR_RABIT].setVisible(false);
							animsprRabit[foodCURRENT].setVisible(true);
							animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_B);
							INT_COUNT++;
							checkCount();
						}
						
						if(sTo == animsprNORMAL[ANIMSPR_BOY]){
							animsprNORMAL[ANIMSPR_BOY].setVisible(false);
							animsprBoy[foodCURRENT].setVisible(true);
							animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_B);
							INT_COUNT++;
							checkCount();
						}
						
						if(sTo == animsprNORMAL[ANIMSPR_BEAR]){
							animsprNORMAL[ANIMSPR_BEAR].setVisible(false);
							animsprBear[foodCURRENT].setVisible(true);
							animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_B);
							INT_COUNT++;
							checkCount();
						}
					}
				})));
	}
	
	// CHECK STATUS COUNT
	private void checkCount(){
		if(INT_COUNT == 4){
			mScene.unregisterUpdateHandler(timerPlaySE);
			OGG_A2_C_KASA.play();
			OGG_A2_9_BOWA.play();
			EAT_STATUS = 1;
			eat_ALLOW = true;
			animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_C);
			animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_C);
			animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_C);
			animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_C);
			
			mScene.unregisterUpdateHandler(timerFOOD);
			timerFOOD = new PauseResumeTimerHandler(5.0f, false ,new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					EAT_STATUS = 2;
					OGG_A2_10_KIRAN.play();
					playSoundDelay(OGG_A2_D_YAKETA, 1.0f);
					burningFOOD();
					animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_D);
					animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_D);
					animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_D);
					animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_D);
					
					
				}
			});
			mScene.registerUpdateHandler(timerFOOD);
			}
		}
	
	// TIMER PLAY SE START
	private void timerPlaySeStart(){
		mScene.unregisterUpdateHandler(timerPlaySE);
		timerPlaySE = new PauseResumeTimerHandler(5.0f, false, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				if(boySE == 0 || bearSE == 0) {
					OGG_A2_06_07_BOKUNIMO.play();
				}
				if(girlSE == 0 || rabitSE == 0) {
					OGG_A2_04_05_WATASHIMO.play();
				}
			}
		});
		mScene.registerUpdateHandler(timerPlaySE);
	}
	
	//TIMER FOOD BURNING  
	private void burningFOOD(){
		mScene.unregisterUpdateHandler(timerFOOD_BURN);
		timerFOOD_BURN = new PauseResumeTimerHandler(4.0f, false, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				EAT_STATUS = 3;
				animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_E);
				animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_E);
				animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_E);
				animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_E);
			}
		});
		mScene.registerUpdateHandler(timerFOOD_BURN);
	}
	
	// EAT FOOD
	private void eatFOOD(){
		INT_COUNT = 0;
		eat_FOOD = 1;
		eat_ALLOW = false;
		// LIVE FEED
		if(EAT_STATUS == 1){
			mScene.unregisterUpdateHandler(timerFOOD);
			mScene.unregisterUpdateHandler(timerFOOD_BURN);

			OGG_A2_H_YAKETENAI2.play();
			
			animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_H);
			animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_H);
			animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_H);
			animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_H);
			
			animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(2.5f , new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					resetEAT();
				}
			}));
		}
		
		// FOOD BURNING
		if(EAT_STATUS == 3){
			mScene.unregisterUpdateHandler(timerFOOD);
			mScene.unregisterUpdateHandler(timerFOOD_BURN);
			
			OGG_A2_I_KOGE7.play();
			
			animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_I);
			animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_I);
			animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_I);
			animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_I);
			
			animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(2.5f , new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					resetEAT();
				}
			}));
		}
		
		// FOOD GOOD
		if(EAT_STATUS == 2){
			mScene.unregisterUpdateHandler(timerFOOD);
			mScene.unregisterUpdateHandler(timerFOOD_BURN);
			
			playSoundDelay(OGG_A2_F_TABERU, 0.3f);
			
			animsprBear[foodCURRENT].animate(new long [] {250, 250}, 7, 8, 2);
			animsprRabit[foodCURRENT].animate(new long [] {250, 250}, 7, 8, 2);
			animsprBoy[foodCURRENT].animate(new long [] {250, 250}, 7, 8, 2);
			animsprGirl[foodCURRENT].animate(new long [] {250, 250}, 7, 8, 2, new IAnimationListener() {
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
					OGG_A2_G_OISHI.play();
					
					animsprGirl[foodCURRENT].setCurrentTileIndex(STATUS_G);
					animsprRabit[foodCURRENT].setCurrentTileIndex(STATUS_G);
					animsprBoy[foodCURRENT].setCurrentTileIndex(STATUS_G);
					animsprBear[foodCURRENT].setCurrentTileIndex(STATUS_G);
					
					animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(2.0f , new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							resetEAT();
						}
					}));
				}
			});
		}
	}
	
	// CURRENT SE
	private void currentSE(){
		girlSE = 0;
		rabitSE = 0;
		boySE = 0;
		bearSE = 0;
	}
	
	// STATUS WITH HUMAN
	private void statusHM(int x){
		GIRL_HM = x;
		RABIT_HM = x;
		BOY_HM = x;
		BEAR_HM = x;
	}
	
	// TOUCH TRUE
	private void touchVALUE(boolean value){
		touch_GIRL = value;
		touch_RABIT = value;
		touch_BOY = value;
		touch_BEAR = value;
	}
	
	// RESET EAT
	private void resetEAT(){
		setVisibleRetangle(false, animsprGirl[foodCURRENT], animsprRabit[foodCURRENT],
				animsprBoy[foodCURRENT], animsprBear[foodCURRENT]);
			
			for (int i = 0; i < animsprNORMAL.length; i++) {
				animsprNORMAL[i].setVisible(true);
				animsprNORMAL[i].setCurrentTileIndex(0);
			}
			
			currentSE();
			statusHM(NO_IS_DETECTED);
			
			touchVALUE(true);
			
			animspr_FOOD[foodCURRENT][0].setTouch(true);
			animspr_FOOD[foodCURRENT][1].setTouch(true);
			animspr_FOOD[foodCURRENT][2].setTouch(true);
			animspr_FOOD[foodCURRENT][3].setTouch(true);
			
			foodCURRENT++;
			if(foodCURRENT > 3) foodCURRENT = 0;
			visibleFOOD(foodCURRENT);
			
			EAT_STATUS = 0;
			eat_FOOD = 0;
	}
	
	// SET VISIBLE RETANGLE
	private void setVisibleRetangle(boolean value, RectangularShape ...rectangularShapes){
		for (int i = 0; i < rectangularShapes.length; i++) {
			rectangularShapes[i].setVisible(value);
		}
	}
	
	//TOUCH HUMAN
	private void isTouchHuman(){
		touchVALUE(false);
		isTouchFire = false;
		
		if(FAIRY11_HM == NO_IS_DETECTED) {
			isTouchGimmic = false;
		}
		
		if(FAIRY10_HM == NO_IS_DETECTED){
			isTouchFairy = false;
		}
		
		eat_ALLOW = false;
		
		OGG_A2_8_YAITERU2.play();
		
		animsprHUMAN.registerEntityModifier(new DelayModifier(1.2f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				if(eat_FOOD == 1) {
					mScene.unregisterUpdateHandler(timerFOOD);
					mScene.unregisterUpdateHandler(timerFOOD_BURN);
					animsprGirl[foodCURRENT].stopAnimation();
					animsprGirl[foodCURRENT].clearEntityModifiers();
				}
				
				if(FAIRY11_HM == NO_IS_DETECTED) {
					animsprFairy11.setCurrentTileIndex(1);
				}
				
				if(FAIRY10_HM == NO_IS_DETECTED){
					animsprFire.stopAnimation();
					animsprFairy10.setCurrentTileIndex(3);
					animsprFire_Normal.setVisible(false);
					animatedAnim(animsprFire, 1000, 1000, 1000, 3, 4, 5, -1);
					animsprFire.setVisible(true);
				} else {
					animsprFairy10.setCurrentTileIndex(3);
				}
				
				if(GIRL_HM == IS_DETECTED || RABIT_HM == IS_DETECTED || BOY_HM == IS_DETECTED || BEAR_HM == IS_DETECTED){
					timerPlaySE.pause();
				}
				
				if(EAT_STATUS == 1 && eat_FOOD == 0){
					timerFOOD.pause();
				}
				
				if(EAT_STATUS == 2 && eat_FOOD == 0){
					timerFOOD_BURN.pause();
				}
				
				mp3Off();
				
				OGG_A2_A_KEIKOKU2.play();
				
				for (int i = 0; i < animsprNORMAL.length; i++) {
					animsprNORMAL[i].setVisible(true);
					animsprNORMAL[i].setCurrentTileIndex(1);
				}

				animsprGirl[foodCURRENT].setVisible(false);
				animsprRabit[foodCURRENT].setVisible(false);
				animsprBoy[foodCURRENT].setVisible(false);
				animsprBear[foodCURRENT].setVisible(false);
				
				/*
				 *  EAT_STATUS == 0
				 * */
				if(EAT_STATUS == 0){
					animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							returnHuman();
						}
					}));
				}
				
				/*
				 *  EAT_STATUS == 1
				 * */
				if(EAT_STATUS == 1){
					animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							returnHuman();
							
							if(EAT_STATUS == 1 && eat_FOOD == 0){
								timerFOOD.resume();
								eat_ALLOW = true;
								OGG_A2_C_KASA.play();
							}
							if(eat_FOOD == 1) {
								resetEAT();
							}
						}
					}));
				}
				
				/*
				 *  EAT_STATUS == 2
				 * */
				if(EAT_STATUS == 2){
					animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							returnHuman();
							
							if(EAT_STATUS == 2 && eat_FOOD == 0){
								timerFOOD_BURN.resume();
								eat_ALLOW = true;
							}
							if(eat_FOOD == 1) {
								resetEAT();
							}
						}
					}));
				}
				
				/*
				 *  EAT_STATUS == 3
				 * */
				if(EAT_STATUS == 3){
					animsprGirl[foodCURRENT].registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							returnHuman();
							
							if(EAT_STATUS == 3 && eat_FOOD == 0){
								eat_ALLOW = true;
							}
							if(eat_FOOD == 1) {
								resetEAT();
							}
						}
					}));
				}
			}
		}));
	}
	
	// RETURN HUMAN
	private void returnHuman(){
		touchHuman = true;
		animsprHUMAN.setCurrentTileIndex(0);
		
		if(GIRL_HM == IS_DETECTED || RABIT_HM == IS_DETECTED || BOY_HM == IS_DETECTED || BEAR_HM == IS_DETECTED){
			timerPlaySE.resume();
		}
		
		if(FAIRY11_HM == NO_IS_DETECTED) {
			isTouchGimmic = true;
			animsprFairy11.setCurrentTileIndex(0);
		}
		if(FAIRY10_HM == NO_IS_DETECTED && !isTouchFairy){
			isTouchFairy = true;
			isTouchFire = true;
			animsprFairy10.setCurrentTileIndex(0);
			animsprFire_Normal.setVisible(true);
			animsprFire.setVisible(false);
		} else {
			animsprFairy10.setCurrentTileIndex(0);
		}
		
		for (int i = 0; i < animsprNORMAL.length; i++) {
			animsprNORMAL[i].setCurrentTileIndex(0);
		}
		
		if(GIRL_HM == IS_DETECTED){
			animsprGirl[foodCURRENT].setVisible(true);
			animsprNORMAL[ANIMSPR_GIRL].setVisible(false);
		} else {
			touch_GIRL = true;
		}
		
		if(RABIT_HM == IS_DETECTED){
			animsprRabit[foodCURRENT].setVisible(true);
			animsprNORMAL[ANIMSPR_RABIT].setVisible(false);
		} else {
			touch_RABIT = true;
		}
		
		if(BOY_HM == IS_DETECTED){
			animsprBoy[foodCURRENT].setVisible(true);
			animsprNORMAL[ANIMSPR_BOY].setVisible(false);
		} else {
			touch_BOY = true;
		}
		
		if(BEAR_HM == IS_DETECTED){
			animsprBear[foodCURRENT].setVisible(true);
			animsprNORMAL[ANIMSPR_BEAR].setVisible(false);
		} else {
			touch_BEAR = true;
		}
	}
	
	// MP3 OFF
	private void mp3Off(){
		OGG_A2_04_05_WATASHIMO.stop();
		OGG_A2_06_07_BOKUNIMO.stop();
		OGG_A2_10_KIRAN.stop();
		OGG_A2_11_KIRA6.stop();
		OGG_A2_8_YAITERU2.stop();
		OGG_A2_9_BOWA.stop();
		OGG_A2_9_GASBANA.stop();
		OGG_A2_9_PATI.stop();
		OGG_A2_A_KEIKOKU2.stop();
		OGG_A2_B_POMI.stop();
		OGG_A2_C_KASA.stop();
		OGG_A2_D_YAKETA.stop();
		OGG_A2_F_TABERU.stop();
		OGG_A2_G_OISHI.stop();
		OGG_A2_H_YAKETENAI2.stop();
		OGG_A2_I_KOGE7.stop();
	}
	
	// RESET
	private void resetAll(){
		mScene.unregisterUpdateHandler(timerStar);
		mScene.unregisterUpdateHandler(timerPlaySE);
		mScene.unregisterUpdateHandler(timerFOOD);
		mScene.unregisterUpdateHandler(timerFOOD_BURN);
		
		runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
			
				for (Iterator<Sprite> iterator = arrayPoolStar1.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolStar1.onHandleRecycleItem(_iterator);
				}
				arrayPoolStar1.clear();
				
				for (Iterator<Sprite> iterator = arrayPoolStar2.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolStar2.onHandleRecycleItem(_iterator);
				}
				arrayPoolStar2.clear();
				
				for (Iterator<Sprite> iterator = arrayPoolStar3.iterator(); iterator.hasNext();) {
					Sprite _iterator = iterator.next();
					sprPoolStar3.onHandleRecycleItem(_iterator);
				}
				arrayPoolStar3.clear();
			}
		});
		
		// LEAF
		for (int i = 0; i < sprLeafFly.length; i++) {
			if(sprLeafFly[i] != null ){
				sprLeafFly[i].clearEntityModifiers();
				sprLeafFly[i].setRotation(0);
			}
		}
		
		// FAIRY
		if(animsprFairy11 != null ){
			animsprFairy11.clearEntityModifiers();
			animsprFairy11.setPosition(animsprFairy11.getmXFirst(), animsprFairy11.getmYFirst());
			animsprFairy11.setCurrentTileIndex(0);
		}
		
		if(animsprFairy10 != null ){
			animsprFairy10.stopAnimation();
			animsprFairy10.clearEntityModifiers();
			animsprFairy10.setCurrentTileIndex(0);
		}
		
		// FIRE
		if(animsprFire_Normal != null){
			animsprFire_Normal.stopAnimation();
			animsprFire_Normal.setVisible(true);
		}
		
		if(animsprFire != null ){
			animsprFire.stopAnimation();
			animsprFire.setVisible(false);
		}
		
		if(spr_Fire_Leaf != null ){
			spr_Fire_Leaf.clearEntityModifiers();
			spr_Fire_Leaf.setVisible(false);
		}
		
		//GIRL
		for (int i = 0; i < animsprGirl.length; i++) {
			if(animsprGirl[i] != null ){
				animsprGirl[i].clearEntityModifiers();
				animsprGirl[i].setVisible(false);
			}
		}
		
		// RABIT
		for (int i = 0; i < animsprRabit.length; i++) {
			if(animsprRabit[i] != null ){
				animsprRabit[i].clearEntityModifiers();
				animsprRabit[i].setVisible(false);
			}
		}
		
		// BOY
		for (int i = 0; i < animsprBoy.length; i++) {
			if(animsprBoy[i] != null ){
				animsprBoy[i].clearEntityModifiers();
				animsprBoy[i].setVisible(false);
			}
		}
		
		// BEAR
		for (int i = 0; i < animsprBear.length; i++) {
			if(animsprBear[i] != null ){
				animsprBear[i].clearEntityModifiers();
				animsprBear[i].setVisible(false);
			}
		}
		
		// NORMAL
		for (int i = 0; i < animsprNORMAL.length; i++) {
			if(animsprNORMAL[i] != null ){
				animsprNORMAL[i].clearEntityModifiers();
				animsprNORMAL[i].setCurrentTileIndex(0);
				animsprNORMAL[i].setVisible(true);
			}
		}
		
		// HUMAN
		if(animsprHUMAN != null){
			animsprHUMAN.clearEntityModifiers();
			animsprHUMAN.setCurrentTileIndex(0);
		}
		
		// FOOD
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				animspr_FOOD[i][j].clearEntityModifiers();
				animspr_FOOD[i][j].setVisible(false);
				animspr_FOOD[i][j].setPosition(animspr_FOOD[i][j].getmXFirst(), animspr_FOOD[i][j].getmYFirst());
			}
		}
	}
	
	// TIMER TOUCH HUMAN
	private void timertouchHuman(){
		mScene.unregisterUpdateHandler(timerTouchHuman);
		timerTouchHuman = new TimerHandler(1.2f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				touchHuman = true;
			}
		});
		mScene.registerUpdateHandler(timerTouchHuman);
	}
	
	// ===========================================================
	// INNER AND ANONYMOUS CLASSES
	// ===========================================================
	
	//CLASS SPRITE POOL
	class SpritePool extends GenericPool<Sprite> {
		private ITextureRegion mTextureRegion;

		public SpritePool(ITextureRegion mTextureRegion) {
			if (mTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have a TextureRegion
				throw new IllegalArgumentException("The texture region must not be NULL");
			}
			this.mTextureRegion = mTextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			return new Sprite(0, 0, mTextureRegion, Vol3Yakiimo.this.getVertexBufferObjectManager());
		}

		@Override
		protected void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.detachSelf();	
		}

		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
			pItem.reset();
		}
	}
	
	//CLASS ANIMSPRITE FOOD
	public class AnimFOOD extends AnimatedSprite {
		private boolean isTouch;
		
		public AnimFOOD(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);

		}
		
		public boolean getTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}
		
		@Override
		public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			
			if (pTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
				if(AnimFOOD.this.getTouch() == true){
					if(Vol3Yakiimo.this.touch_GIRL && AnimFOOD.this.isVisible()){
						AnimFOOD.this.setTouch(false);
						
						Vol3Yakiimo.this.touch_GIRL = false;
						Vol3Yakiimo.this.girlSE = 1;
						Vol3Yakiimo.this.GIRL_HM = IS_DETECTED;
						Vol3Yakiimo.this.timerPlaySeStart();
						
						foodModifier(AnimFOOD.this, Vol3Yakiimo.this.animsprNORMAL[ANIMSPR_GIRL]);
						return true;
					}
					else if(Vol3Yakiimo.this.touch_RABIT && AnimFOOD.this.isVisible()){
						AnimFOOD.this.setTouch(false);
						
						Vol3Yakiimo.this.rabitSE = 1;
						Vol3Yakiimo.this.touch_RABIT = false;
						Vol3Yakiimo.this.RABIT_HM = IS_DETECTED;
						Vol3Yakiimo.this.timerPlaySeStart();
						
						foodModifier(AnimFOOD.this, Vol3Yakiimo.this.animsprNORMAL[ANIMSPR_RABIT]);
						return true;
					}
					else if(Vol3Yakiimo.this.touch_BOY && AnimFOOD.this.isVisible()){
						AnimFOOD.this.setTouch(false);
						
						Vol3Yakiimo.this.boySE = 1;
						Vol3Yakiimo.this.touch_BOY = false;
						Vol3Yakiimo.this.BOY_HM = IS_DETECTED;
						Vol3Yakiimo.this.timerPlaySeStart();
						
						foodModifier(AnimFOOD.this, Vol3Yakiimo.this.animsprNORMAL[ANIMSPR_BOY]);
						return true;
					}
					else if(Vol3Yakiimo.this.touch_BEAR && AnimFOOD.this.isVisible()){
						AnimFOOD.this.setTouch(false);
						
						Vol3Yakiimo.this.bearSE = 1;
						Vol3Yakiimo.this.touch_BEAR = false;
						Vol3Yakiimo.this.BEAR_HM = IS_DETECTED;
						Vol3Yakiimo.this.timerPlaySeStart();
						
						foodModifier(AnimFOOD.this, Vol3Yakiimo.this.animsprNORMAL[ANIMSPR_BEAR]);
						return true;
					}
				}
			}
			return false;
		}
	}
	
	// PAUSE AND RESUME TIMER HANDER
	public class PauseResumeTimerHandler extends TimerHandler {
		private boolean mPause = false;
		
		float x = this.getTimerSeconds();
		  
		public PauseResumeTimerHandler(float pTimerSeconds, boolean pAutoReset,
				ITimerCallback pTimerCallback) {
			super(pTimerSeconds, pAutoReset, pTimerCallback);
		}
  
		public void pause() {
			this.mPause = true;
		}
  
		public void resume() {
			this.mPause = false;
		}
 
		@Override
		public void onUpdate(float pSecondsElapsed) {
			if(!this.mPause) {
				super.onUpdate(pSecondsElapsed);
			}
		}
	}
}
