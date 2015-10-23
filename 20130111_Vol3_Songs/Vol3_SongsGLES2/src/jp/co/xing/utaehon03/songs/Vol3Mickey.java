/* Vol3Mickey.java
* Create on Nov 29, 2012
*/
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MickeyResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Mickey extends BaseGameActivity implements IOnSceneTouchListener {
	public final static String TAG = " Vol3Mickey "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpMickey1, ttpMickey2, ttpMickey3, ttpMickey4, ttpMickey5;
	private TexturePackTextureRegionLibrary ttpLibMickey1, ttpLibMickey2, ttpLibMickey3, ttpLibMickey4, ttpLibMickey5;
	
	private TextureRegion trBackground1, trBackground2, trBupbe1, trBupbe2, trsStick, trTap1, trTap2, trSnake,
			trBird, trCat1, trCat2, trFace;
	private TextureRegion trPersons[] = new TextureRegion[7];
	private TextureRegion trMusic[] = new TextureRegion[6];
	private TextureRegion trMusicName[] = new TextureRegion[10];
	private Sprite sBackground1, sBackground2, sBupbe1, sBupbe2, sStick, sTap1, sTap2, sSnake, sBird, sCat1, sCat2, sFace;
	
	private Sprite sMusic1[] = new Sprite[6];
	private Sprite sMusic2[] = new Sprite[6];
	private Sprite sMusic3[] = new Sprite[6];
	private Sprite sMusic4[] = new Sprite[6];
	private Sprite sMusic5[] = new Sprite[6];
	private Sprite sMusic6[] = new Sprite[6];
	private Sprite sMusic7[] = new Sprite[6];
	private Sprite sMusic8[] = new Sprite[6];
	private Sprite sMusic9[] = new Sprite[6];
	
	private Sprite sPersons1[] = new Sprite[7];
	private Sprite sPersons2[] = new Sprite[7];
	private Sprite sMusicName[] = new Sprite[10];
	private TiledTextureRegion ttrMusican[] = new TiledTextureRegion[10];
	private AnimatedSprite aniMusican[] = new AnimatedSprite[10];
	
	
	
	private Rectangle recPerson1, recPerson2, recCollision;
	private int[] intPersonId = {Vol3MickeyResource.A79_2_3_IPHONE_ID,
			Vol3MickeyResource.A79_2_4_IPHONE_ID, Vol3MickeyResource.A79_2_5_IPHONE_ID,
			Vol3MickeyResource.A79_2_6_IPHONE_ID, Vol3MickeyResource.A79_2_7_IPHONE_ID,
			Vol3MickeyResource.A79_2_8_IPHONE_ID, Vol3MickeyResource.A79_2_9_IPHONE_ID };
	
	private int[] intMusicId= {Vol3MickeyResource.A79_13_1_IPHONE_ID,
			Vol3MickeyResource.A79_13_2_IPHONE_ID, Vol3MickeyResource.A79_13_3_IPHONE_ID,
			Vol3MickeyResource.A79_13_4_IPHONE_ID, Vol3MickeyResource.A79_13_5_IPHONE_ID,
			Vol3MickeyResource.A79_13_6_IPHONE_ID
	};
	private int countPerson = 0;
	private int countShow7 = 0;
	private int countShow10 = 0;
	private int countShow6 = 0;
	private int countShow8 = 0;
	private int countShow3 = 0;
	private int countSound[] = {0, 0, 0, 0, 0};
	private boolean touchWindown1 = true, touchWindown2 = true;
	private boolean touchPerson[] = new boolean[10];
	private boolean isCollision[] = new boolean[10];
	private float pointWindown1[][]= {{292, 109}, {252, 109}, {250,114}, {252, 114}, {280, 112}, {244, 112}, {260, 112}};
	private float pointWindown2[][]= {{684, 109}, {644, 109}, {642, 114}, {644, 114}, {674, 112}, {630, 112}, {654, 112}};
	
	private Sound A79_12_1_ACODION, A79_2_3_YA2, A79_2_3_YA,A79_2_4_BYE, A79_2_4_GENKI,A79_2_5_GOKIGENYO, A79_2_6_NYA, A79_2_7_KONNICHIWA2,
	A79_2_7_KONNICHIWA, A79_2_8_HAI2, A79_2_8_HAI, A79_2_9_HARO2,A79_2_9_HARO,A79_3_1, A79_3_1_NYAN, A79_3_1_SUZAFON, A79_4_1,A79_5_1,
	A79_5_1_KIRARIN, A79_6_1,A79_6_1_BASA, A79_6_1_SYMBALE,A79_7_1, A79_7_1_TRUMPET,A79_8_1, A79_8_1_KODAIKO,A79_8_1_ODAIKO,A79_8_1_PIERO,
	A79_9_1, A79_9_1_HOISUL,A79_10_1,A79_10_1_HYU, A79_10_1_KURARINET,A79_11_1, A79_11_1_KARAGADO, A79_12_1;

	private void initial() {
		countPerson = 0;
		countShow7 = 0;
		countShow10 = 0;
		countShow6 = 0;
		countShow8 = 0;
		countShow3 = 0;
		touchWindown1 = true;
		touchWindown2 = true;
		for (int i = 0; i < countSound.length; i++) {
			countSound[i] = 0;
		}
		for (int i = 0; i < isCollision.length; i++) {
			isCollision[i] = true;
		}
		for (int i = 0; i < touchPerson.length; i++) {
			touchPerson[i] = true;
		}
	}
	@Override
	public void onCreateResources() {
		super.onCreateResources();
		SoundFactory.setAssetBasePath("mickey/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("mickey/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "mickey/gfx/");
		
		
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		
		ttpMickey1 = mTexturePackLoaderFromSource.load("mickey1.xml");
		ttpMickey1.loadTexture();
		ttpLibMickey1 = ttpMickey1.getTexturePackTextureRegionLibrary();
		ttpMickey2 = mTexturePackLoaderFromSource.load("mickey2.xml");
		ttpMickey2.loadTexture();
		ttpLibMickey2 = ttpMickey2.getTexturePackTextureRegionLibrary();
		ttpMickey3 = mTexturePackLoaderFromSource.load("mickey3.xml");
		ttpMickey3.loadTexture();
		ttpLibMickey3 = ttpMickey3.getTexturePackTextureRegionLibrary();
		ttpMickey4 = mTexturePackLoaderFromSource.load("mickey4.xml");
		ttpMickey4.loadTexture();
		ttpLibMickey4 = ttpMickey4.getTexturePackTextureRegionLibrary();
		ttpMickey5 = mTexturePackLoaderFromSource.load("mickey5.xml");
		ttpMickey5.loadTexture();
		ttpLibMickey5 = ttpMickey5.getTexturePackTextureRegionLibrary();
		trBackground1 = ttpLibMickey5.get(Vol3MickeyResource.A79_1_1_IPHONE_ID);
		trBackground2 = ttpLibMickey5.get(Vol3MickeyResource.A79_1_2_IPHONE_ID);
		trBupbe1 = ttpLibMickey1.get(Vol3MickeyResource.A79_2_1_IPHONE_ID);
		trBupbe2 = ttpLibMickey1.get(Vol3MickeyResource.A79_2_2_IPHONE_ID);
		for (int i = 0; i < trPersons.length; i++) {
			trPersons[i] = ttpLibMickey1.get(intPersonId[i]);
		}	
		
		trMusicName[0] = ttpLibMickey1.get(Vol3MickeyResource.A79_3_7_IPHONE_ID);
		trMusicName[1] = ttpLibMickey1.get(Vol3MickeyResource.A79_4_6_IPHONE_ID);
		trMusicName[2] = ttpLibMickey1.get(Vol3MickeyResource.A79_5_7_IPHONE_ID);
		trMusicName[3] = ttpLibMickey2.get(Vol3MickeyResource.A79_6_7_IPHONE_ID);
		trMusicName[4] = ttpLibMickey2.get(Vol3MickeyResource.A79_7_8_IPHONE_ID);
		trMusicName[5] = ttpLibMickey3.get(Vol3MickeyResource.A79_8_7_IPHONE_ID);
		trMusicName[6] = ttpLibMickey3.get(Vol3MickeyResource.A79_9_6_IPHONE_ID);
		trMusicName[7] = ttpLibMickey3.get(Vol3MickeyResource.A79_10_7_IPHONE_ID);
		trMusicName[8] = ttpLibMickey4.get(Vol3MickeyResource.A79_11_6_IPHONE_ID);
		trMusicName[9] = ttpLibMickey4.get(Vol3MickeyResource.A79_12_7_IPHONE_ID);
		
		for (int i = 0; i < trMusic.length; i++) {
			trMusic[i]=ttpLibMickey4.get(intMusicId[i]);
		}
		ttrMusican[0] = getTiledTextureFromPacker(ttpMickey1, Vol3MickeyResource.PACK_MUSICAN0);
		ttrMusican[1] = getTiledTextureFromPacker(ttpMickey1, Vol3MickeyResource.PACK_MUSICAN1);
		ttrMusican[2] = getTiledTextureFromPacker(ttpMickey1, Vol3MickeyResource.PACK_MUSICAN2);
		ttrMusican[3] = getTiledTextureFromPacker(ttpMickey2, Vol3MickeyResource.PACK_MUSICAN3);
		ttrMusican[4] = getTiledTextureFromPacker(ttpMickey2, Vol3MickeyResource.PACK_MUSICAN4);
		ttrMusican[5] = getTiledTextureFromPacker(ttpMickey3, Vol3MickeyResource.PACK_MUSICAN5);
		ttrMusican[6] = getTiledTextureFromPacker(ttpMickey3, Vol3MickeyResource.PACK_MUSICAN6);
		ttrMusican[7] = getTiledTextureFromPacker(ttpMickey3, Vol3MickeyResource.PACK_MUSICAN7);
		ttrMusican[8] = getTiledTextureFromPacker(ttpMickey4, Vol3MickeyResource.PACK_MUSICAN8);
		ttrMusican[9] = getTiledTextureFromPacker(ttpMickey4, Vol3MickeyResource.PACK_MUSICAN9);
		
		trsStick = ttpLibMickey1.get(Vol3MickeyResource.A79_5_6_IPHONE_ID);
		trCat1 = ttpLibMickey1.get(Vol3MickeyResource.A79_3_6_IPHONE_ID);
		trCat2 = ttpLibMickey1.get(Vol3MickeyResource.A79_3_8_IPHONE_ID);
		
		trTap1 = ttpLibMickey2.get(Vol3MickeyResource.A79_7_6_IPHONE_ID);
		trTap2 = ttpLibMickey2.get(Vol3MickeyResource.A79_7_7_IPHONE_ID);
		trBird = ttpLibMickey2.get(Vol3MickeyResource.A79_6_6_IPHONE_ID);
		trSnake = ttpLibMickey3.get(Vol3MickeyResource.A79_10_6_IPHONE_ID);
		trFace = ttpLibMickey3.get(Vol3MickeyResource.A79_8_6_IPHONE_ID);
		
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		mScene.setBackground(new Background(Color.WHITE));
		
		sBackground1 = new Sprite(0, 0, trBackground1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground1);
		sBupbe1 = new Sprite(410, 205, trBupbe2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBupbe1);
		sBupbe2 = new Sprite(810, 208, trBupbe1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBupbe2);
		sBackground2 = new Sprite(-44, -3, trBackground2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);
		
		for (int i = 0; i < sPersons1.length; i++) {
			sPersons1[i] = new Sprite(0, 0, trPersons[i], this.getVertexBufferObjectManager());
			sPersons1[i].setVisible(false);
			mScene.attachChild(sPersons1[i]);
		}
		for (int i = 0; i < sPersons2.length; i++) {
			sPersons2[i] = new Sprite(0, 0, trPersons[i], this.getVertexBufferObjectManager());
			sPersons2[i].setVisible(false);
			mScene.attachChild(sPersons2[i]);
		}
		//-------------------rec 1---------------
		recPerson1 = new Rectangle(980, 0, 1260, 640, this.getVertexBufferObjectManager());
		
		recPerson1.setColor(Color.TRANSPARENT);
		mScene.attachChild(recPerson1);
		aniMusican[6] = new AnimatedSprite(-10, 178, ttrMusican[6], this.getVertexBufferObjectManager()){
			
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&& touchPerson[6]) {
					Log.d(TAG, "touch thu 1:::");
					touchPerson[6]=false;
					touchPerson9();
				}
				return false;
			};
		};
		recPerson1.attachChild(aniMusican[6]);
		mScene.registerTouchArea(aniMusican[6]);
		aniMusican[8] = new AnimatedSprite(170, 143, ttrMusican[8], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&& touchPerson[8]){
					Log.d(TAG, "touch thu 2:::");
					touchPerson[8] = false;
					touchPerson11();
				}
				return false;
			};
		};
		recPerson1.attachChild(aniMusican[8]);
		mScene.registerTouchArea(aniMusican[8]);
		mScene.registerTouchArea(recPerson1);
		aniMusican[2] = new AnimatedSprite(430, 258, ttrMusican[2], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&& touchPerson[2]) {
					Log.d(TAG, "touch thu 3::: ");
					touchPerson[2] = false;
					touchPerson5();
				}
				return false;
			};
		};
		recPerson1.attachChild(aniMusican[2]);
		mScene.registerTouchArea(aniMusican[2]);
		sTap1  = new Sprite(660, 402, trTap1, this.getVertexBufferObjectManager());
		recPerson1.attachChild(sTap1);
		sTap2  = new Sprite(600, 185, trTap2, this.getVertexBufferObjectManager());
		recPerson1.attachChild(sTap2);
		aniMusican[4] = new AnimatedSprite(690, 224, ttrMusican[4], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[4]) {
					Log.d(TAG, "touch thu 4::: ");
					touchPerson[4]= false;
					touchPerson7();
					
				}
				return false;
			};
		};
		recPerson1.attachChild(aniMusican[4]);
		mScene.registerTouchArea(aniMusican[4]);
		aniMusican[7] = new AnimatedSprite(980, 264, ttrMusican[7], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&& touchPerson[7]) {
					touchPerson[7] = false;
					touchPerson10();
				}
				return false;
			};
		};
		recPerson1.attachChild(aniMusican[7]);
		mScene.registerTouchArea(aniMusican[7]);
		sMusicName[6] = new Sprite(-30, 583, trMusicName[6], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusicName[6]);
		
		sMusicName[8] = new Sprite(170, 583, trMusicName[8], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusicName[8]);
		sMusicName[2] = new Sprite(470, 583, trMusicName[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusicName[2]);
		
		sMusicName[4] = new Sprite(730, 583, trMusicName[4], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusicName[4]);
		
		sMusicName[7] = new Sprite(960, 583, trMusicName[7], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusicName[7]);
		sStick = new Sprite(450, 108, trsStick, this.getVertexBufferObjectManager());
		recPerson1.attachChild(sStick);
		sSnake = new Sprite(1135, 210, trSnake, this.getVertexBufferObjectManager());
		recPerson1.attachChild(sSnake);
		setvisiableSprite(false, sSnake, sStick, sTap1, sTap2);
		//------------------------add music-----------------------
		sMusic1[0] = new Sprite(170, 200, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic1[0]);
		sMusic1[1] = new Sprite(310, 280, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic1[1]);
		sMusic1[2] = new Sprite(180, 420, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic1[2]);
		sMusic1[3] = new Sprite(310, 280, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic1[3]);
		sMusic1[4] = new Sprite(270, 420, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic1[4]);
		setvisiableSprite(false, sMusic1[0],sMusic1[1],sMusic1[2],sMusic1[3],sMusic1[4]);
		//---------------------2------------------
		sMusic2[0] = new Sprite(680, 350, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic2[0]);
		sMusic2[1] = new Sprite(580, 220, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic2[1]);
		sMusic2[2] = new Sprite(470, 320, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic2[2]);
		setvisiableSprite(false, sMusic2[0],sMusic2[1],sMusic2[2]);
		//---------------------3------------------
		
		sMusic3[0] = new Sprite(940, 400, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[0]);
		sMusic3[1] = new Sprite(780, 340, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[1]);
		sMusic3[2] = new Sprite(710, 450, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[2]);
		sMusic3[3] = new Sprite(810, 260, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[3]);
		sMusic3[4] = new Sprite(700, 180, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[4]);
		sMusic3[5] = new Sprite(780, 420, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic3[5]);
		setvisiableSprite(false, sMusic3[0],sMusic3[1],sMusic3[2],sMusic3[3],sMusic3[4],sMusic3[5]);
		//------------------4--------------------
		sMusic4[0] = new Sprite(1130, 400, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[0]);
		sMusic4[1] = new Sprite(999, 300, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[1]);
		sMusic4[2] = new Sprite(980, 390, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[2]);
		sMusic4[3] = new Sprite(990, 400, trMusic[0], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[3]);
		sMusic4[4] = new Sprite(1190, 360, trMusic[1], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[4]);
		sMusic4[5] = new Sprite(1080, 260, trMusic[2], this.getVertexBufferObjectManager());
		recPerson1.attachChild(sMusic4[5]);
		
		setvisiableSprite(false, sMusic4[0],sMusic4[1],sMusic4[2], sMusic4[3],sMusic4[4],sMusic4[5]);
		
		//----------------rec 2--------------
		recPerson2 = new Rectangle(980, 0, 1260, 640, this.getVertexBufferObjectManager());
		mScene.attachChild(recPerson2);
		recPerson2.setColor(Color.TRANSPARENT);
		aniMusican[9] = new AnimatedSprite(0, 235, ttrMusican[9], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[9]) {
					touchPerson[9]=false;
					touchPerson12();
				}
				return false;
			};
		};
		recPerson2.attachChild(aniMusican[9]);
		mScene.registerTouchArea(aniMusican[9]);
		aniMusican[1] = new AnimatedSprite(190, 274, ttrMusican[1], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[1]) {
					touchPerson[1]=false;
					touchPerson4();
				}
				return false;
			};
		};
		recPerson2.attachChild(aniMusican[1]);
		mScene.registerTouchArea(aniMusican[1]);
		sBird = new Sprite(415, 40, trBird, this.getVertexBufferObjectManager());
		recPerson2.attachChild(sBird);
		sBird.setScale(0.8f);
		aniMusican[3] = new AnimatedSprite(430, 188, ttrMusican[3], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[3]) {
					touchPerson[3] = false;
					touchPerson6();
				}
				return false;
			};
		};
		recPerson2.attachChild(aniMusican[3]);
		mScene.registerTouchArea(aniMusican[3]);
		aniMusican[5] = new AnimatedSprite(660, 278, ttrMusican[5], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[5]) {
					touchPerson[5] = false;
					touchPerson8();
				}
				return false;
			};
		};
		recPerson2.attachChild(aniMusican[5]);
		mScene.registerTouchArea(aniMusican[5]);
		aniMusican[0] = new AnimatedSprite(1000, 146, ttrMusican[0], this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()&&touchPerson[0]) {
					touchPerson[0] = false;
					touchPerson3();
				}
				return false;
			};
		};
		recPerson2.attachChild(aniMusican[0]);
		mScene.registerTouchArea(aniMusican[0]);
		//------------------add music name---------------------
		sMusicName[9] = new Sprite(0, 583, trMusicName[9], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusicName[9]);
		sMusicName[1] = new Sprite(200, 583, trMusicName[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusicName[1]);
		sMusicName[3] = new Sprite(445, 583, trMusicName[3], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusicName[3]);
		sMusicName[5] = new Sprite(740, 583, trMusicName[5], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusicName[5]);
		sMusicName[0] = new Sprite(990, 583, trMusicName[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusicName[0]);
		
		//---------------add bird, cat-----------------
		sFace = new Sprite(655, 280, trFace, this.getVertexBufferObjectManager());
		recPerson2.attachChild(sFace);
		
		sCat1 = new Sprite(1160, 55, trCat1, this.getVertexBufferObjectManager());
		recPerson2.attachChild(sCat1);
		sCat2 = new Sprite(890, 55, trCat2, this.getVertexBufferObjectManager());
		recPerson2.attachChild(sCat2);
		sCat1.setScale(0.8f);
		sCat2.setScale(0.8f);
		setvisiableSprite(false, sCat1, sCat2, sFace, sBird); 
		//----------------------add music---------
		//--------------------4-----------------
		sMusic5[0] = new Sprite(150, 350, trMusic[3], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic5[0]);
		sMusic5[1] = new Sprite(0, 320, trMusic[4], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic5[1]);
		sMusic5[2] = new Sprite(0, 360, trMusic[5], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic5[2]);
		
		sMusic5[3] = new Sprite(-10, 230, trMusic[4], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic5[3]);
		sMusic5[4] = new Sprite(130, 340, trMusic[5], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic5[4]);
		setvisiableSprite(false, sMusic5[0],sMusic5[1],sMusic5[2], sMusic5[3], sMusic5[4]);
		//--------------5------------------------
		sMusic6[0] = new Sprite(360, 410, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic6[0]);
		sMusic6[1] = new Sprite(220, 310, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic6[1]);
		sMusic6[2] = new Sprite(190, 360, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic6[2]);
		setvisiableSprite(false, sMusic6[0],sMusic6[1],sMusic6[2]);
		//--------------------6-------------------
		sMusic7[0] = new Sprite(590, 420, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic7[0]);
		sMusic7[1] = new Sprite(520, 210, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic7[1]);
		sMusic7[2] = new Sprite(410, 390, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic7[2]);
		setvisiableSprite(false, sMusic7[0],sMusic7[1],sMusic7[2]);
		//---------------7---------------------
		
		sMusic8[0] = new Sprite(690, 330, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[0]);
		sMusic8[1] = new Sprite(780, 310, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[1]);
		sMusic8[2] = new Sprite(660, 430, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[2]);
		sMusic8[3] = new Sprite(700, 265, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[3]);
		sMusic8[4] = new Sprite(960, 300, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[4]);
		sMusic8[5] = new Sprite(770, 270, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic8[5]);
		setvisiableSprite(false, sMusic8[0],sMusic8[1],sMusic8[2],sMusic8[3],sMusic8[4],sMusic8[5]);
		//-----------8------------------------
		
		sMusic9[0] = new Sprite(1160, 270, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[0]);
		sMusic9[1] = new Sprite(960, 150, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[1]);
		sMusic9[2] = new Sprite(960, 300, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[2]);
		sMusic9[3] = new Sprite(1120, 140, trMusic[1], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[3]);
		sMusic9[4] = new Sprite(1070, 340, trMusic[2], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[4]);
		sMusic9[5] = new Sprite(1220, 280, trMusic[0], this.getVertexBufferObjectManager());
		recPerson2.attachChild(sMusic9[5]);
		setvisiableSprite(false, sMusic9[0],sMusic9[1],sMusic9[2],sMusic9[4],sMusic9[3],sMusic9[5]);
		
		setvisiableSprite(false, sMusicName[0], sMusicName[1], sMusicName[2], sMusicName[3],
				sMusicName[4], sMusicName[5], sMusicName[6], sMusicName[7], sMusicName[8], sMusicName[9]);
		
		recCollision = new Rectangle(350, 0, 5, 640, this.getVertexBufferObjectManager());
		mScene.attachChild(recCollision);
		recCollision.setColor(Color.TRANSPARENT);
		Log.d(TAG, "loadKaraokeScene end ");
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		
	}
	@Override
	public synchronized void onPauseGame() {
		stopSound();
		resetData();
		super.onPauseGame();
		
	}
	
	private void resetData(){
		if (recPerson1 != null) {
			recPerson1.clearEntityModifiers();
			recPerson1.setPosition(recPerson1.getmXFirst(), recPerson1.getmYFirst());
		}
		if (recPerson2 != null) {
			recPerson2.clearEntityModifiers();
			recPerson2.setPosition(recPerson2.getmXFirst(), recPerson2.getmYFirst());
		}
		for (int i = 0; i < aniMusican.length; i++) {
			aniMusican[i].clearUpdateHandlers();
			aniMusican[i].stopAnimation(0);
			
		}
		for (int i = 0; i < sMusicName.length; i++) {
			sMusicName[i].setVisible(false);
		}
		setvisiableSprite(false, sMusic1[0],sMusic1[1],sMusic1[2],sMusic1[3],sMusic1[4]);
		setvisiableSprite(false, sMusic2[0],sMusic2[1],sMusic2[2]);
		setvisiableSprite(false, sMusic3[0],sMusic3[1],sMusic3[2],sMusic3[3],sMusic3[4],sMusic3[5]);
		setvisiableSprite(false, sMusic4[0],sMusic4[1],sMusic4[2], sMusic4[3],sMusic4[4],sMusic4[5]);
		setvisiableSprite(false, sSnake, sStick, sTap1, sTap2);
		setvisiableSprite(false, sCat1, sCat2, sFace, sBird);
		setvisiableSprite(false, sMusic5[0],sMusic5[1],sMusic5[2], sMusic5[3], sMusic5[4]);
		setvisiableSprite(false, sMusic6[0],sMusic6[1],sMusic6[2]);
		setvisiableSprite(false, sMusic7[0],sMusic7[1],sMusic7[2]);
		setvisiableSprite(false, sMusic8[0],sMusic8[1],sMusic8[2],sMusic8[3],sMusic8[4],sMusic8[5]);
		setvisiableSprite(false, sMusic9[0],sMusic9[1],sMusic9[2],sMusic9[4],sMusic9[3],sMusic9[5]);
		
		for (int i = 0; i < sPersons1.length; i++) {
			sPersons1[i].setVisible(false);
			sPersons1[i].setPosition(0, 0);
			sPersons2[i].setVisible(false);
			sPersons2[i].setPosition(0, 0);
		}
		if (sBupbe1!=null) {
			sBupbe1.clearEntityModifiers();
			sBupbe1.setPosition(sBupbe1.getmXFirst(), sBupbe1.getmYFirst());
			sBupbe1.setFlippedHorizontal(true);
		}
		if (sBupbe2!=null) {
			sBupbe2.clearEntityModifiers();
			sBupbe2.setPosition(sBupbe2.getmXFirst(), sBupbe2.getmYFirst());
			sBupbe2.setFlippedHorizontal(true);
		}
	}
	
	@Override
	public synchronized void onResumeGame() {
		initial();
		moveBupbe();
		for (int i = 0; i < aniMusican.length; i++) {
			animatePerson(aniMusican[i]);
		}
		moveWalk1();
		engineControlPerson();
		super.onResumeGame();
	}
	public void engineControlPerson(){
		mEngine.registerUpdateHandler(new TimerHandler(0.2f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				runOnUpdateThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.d(TAG, "0.2' update 1 lan");
						for (int i = 0; i < aniMusican.length; i++) {
							if(aniMusican[i].collidesWith(recCollision) && isCollision[i]) {
								Log.d(TAG, "khi va cham voi person:::");
								isCollision[i] = false;
								final int index = i;
								if (touchPerson[i]){
									aniMusican[i].stopAnimation(2);
									aniMusican[i].registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
										
										@Override
										public void onTimePassed(TimerHandler arg0) {
											animatePerson(aniMusican[index]);
										}
									}));
								}
								mScene.registerUpdateHandler(new TimerHandler(8.0f, new ITimerCallback() {
									
									@Override
									public void onTimePassed(TimerHandler arg0) {
										isCollision[index] = true;
									}
								}));
								
							}
						}
						
					}
				});
					
			}
		}));
	}
	private void moveWalk1() {
		recPerson1.registerEntityModifier((new SequenceEntityModifier(
				new MoveXModifier(18f, 960, -300, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						moveWalk2();
					}
				}),
				new MoveXModifier(18f, -300,-1560))));
		
	}
	private void moveWalk2() {
		recPerson2.registerEntityModifier((new SequenceEntityModifier(
				new MoveXModifier(18f, 960, -300, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						moveWalk1();
					}
				}),
				new MoveXModifier(18f, -300, -1560))));
	}
	public void animatePerson(AnimatedSprite aniPerson){
		aniPerson.animate(new long[]{500, 500}, new int[]{0, 1}, -1);
	}
	@Override
	public void combineGimmic3WithAction() {

	}
	private void moveBupbe(){
		sBupbe1.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(2.8f, sBupbe1.getX(), sBupbe1.getX()-109,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						sBupbe1.setFlippedHorizontal(false);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sBupbe1.setFlippedHorizontal(true);
					}
				}), new MoveXModifier(2.8f, sBupbe1.getX()-109, sBupbe1.getX()))));
		sBupbe2.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(3.0f, sBupbe2.getX(), sBupbe2.getX()-132,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						sBupbe2.setFlippedHorizontal(false);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sBupbe2.setFlippedHorizontal(true);
					}
				}), new MoveXModifier(3.0f, sBupbe2.getX()-132, sBupbe2.getX()))));
	}
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContains(sBackground2, 380, 201, 455, 276, pX, pY) && touchWindown1) {
				Log.d(TAG, "cua so thu 1::::");
				sBupbe1.setVisible(false);
				touchWindown1 = false;
				touchWindown(0);
			} else if (checkContains(sBackground2, 770, 198, 848, 276, pX, pY) && touchWindown2) {
				Log.d(TAG, "cua so thu 2::::");
				sBupbe2.setVisible(false);
				touchWindown2=false;
				touchWindown(1);
			}
			
		}
		return false;
	}
	private void touchPerson3(){
		A79_3_1.play();
		aniMusican[0].clearUpdateHandlers();
		setvisiableSprite(true, sMusic9[0],sMusic9[1],sMusic9[2], sMusicName[0]);
		if (countShow3==1) {
			countShow3 = 0;
			aniMusican[0].animate(new long[]{800,1500}, new int[]{3,4},0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg2==1) {
						A79_3_1_NYAN.play();
						setvisiableSprite(false, sMusic9[0],sMusic9[1],sMusic9[2]);
						setvisiableSprite(true, sMusic9[3], sMusic9[4], sMusic9[5], sCat1, sCat2);
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[0] = true;
					setvisiableSprite(false, sMusic9[0],sMusic9[1],sMusic9[2],sMusic9[3], sMusic9[4], sMusic9[5],
							sCat1, sCat2, sMusicName[0]);
					animatePerson(aniMusican[0]);
				}
			});
		}else if (countShow3 == 0) {
			countShow3 = 1;
			A79_3_1_SUZAFON.play();
			aniMusican[0].animate(new long[]{800,2500}, new int[]{3,4},0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg2==1) {
						setvisiableSprite(false, sMusic9[0],sMusic9[1],sMusic9[2]);
						setvisiableSprite(true, sMusic9[3], sMusic9[4],sMusic9[5]);
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[0] = true;
					setvisiableSprite(false, sMusic9[0], sMusic9[1], sMusic9[2], sMusic9[3], sMusic9[4], sMusic9[5], sMusicName[0]);
					animatePerson(aniMusican[0]);
				}
			});
		}
	}
	private void touchPerson8() {
		A79_8_1.play();
		aniMusican[5].clearUpdateHandlers();
		setvisiableSprite(true, sMusic8[0],sMusic8[2], sMusic8[1], sMusicName[5]);
		if (countShow8 == 0) {
			countShow8 = 1;
			aniMusican[5].animate(new long[]{600, 600, 800}, new int[]{3,4,3},0,new IAnimationListener() {
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg2==1) {
						setvisiableSprite(false, sMusic8[0],sMusic8[2], sMusic8[1]);
						setvisiableSprite(true, sMusic8[3],sMusic8[4], sMusic8[5]);
					}
					if(arg2==2) {
						setvisiableSprite(true, sMusic8[0],sMusic8[2], sMusic8[1]);
						setvisiableSprite(false, sMusic8[3],sMusic8[4], sMusic8[5]);
						A79_8_1_ODAIKO.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[5] = true;
					setvisiableSprite(false, sMusic8[0],sMusic8[1],sMusic8[2],sMusicName[5],sMusic8[3],sMusic8[4], sMusic8[5], sFace);
					animatePerson(aniMusican[5]);
				}
			});
		}else if (countShow8 == 1) {
			countShow8 = 0;
			aniMusican[5].animate(new long[]{600, 600, 900, 1900}, new int[]{3,4,3,4},0,new IAnimationListener() {
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg2==1) {
						setvisiableSprite(false, sMusic8[0],sMusic8[2], sMusic8[1]);
						setvisiableSprite(true, sMusic8[3],sMusic8[4], sMusic8[5]);
					}
					if(arg2==2) {
						A79_8_1_ODAIKO.play();
						setvisiableSprite(true, sMusic8[0],sMusic8[2], sMusic8[1]);
						setvisiableSprite(false, sMusic8[3],sMusic8[4], sMusic8[5]);
					}
					if(arg2==3) {
						A79_8_1_PIERO.play();
						sFace.setVisible(true);
						setvisiableSprite(false, sMusic8[0],sMusic8[2], sMusic8[1]);
						setvisiableSprite(true, sMusic8[3],sMusic8[4], sMusic8[5]);
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[5] = true;
					setvisiableSprite(false, sMusic8[0],sMusic8[1],sMusic8[2],sMusicName[5],sMusic8[3],sMusic8[4], sMusic8[5], sFace);
					animatePerson(aniMusican[5]);
				}
			});
		}
		
	}
	private void touchPerson6() {
		A79_6_1.play();
		aniMusican[3].clearUpdateHandlers();
		setvisiableSprite(true, sMusic7[0],sMusic7[2], sMusic7[1], sMusicName[3]);
		if (countShow6==0) {
			countShow6=1;
			aniMusican[3].animate(new long[]{600,600,600,300}, new int[]{3,4,3,3},0,new IAnimationListener() {
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg2==1) {
						sMusic7[0].setPosition(sMusic7[0].getmXFirst()+50, sMusic7[0].getmYFirst()+20);
						sMusic7[1].setPosition(sMusic7[1].getmXFirst()+180, sMusic7[1].getmYFirst()+5);
						sMusic7[2].setPosition(sMusic7[2].getmXFirst()+20, sMusic7[2].getmYFirst()-20);
					}
					if (arg2==2) {
						sMusic7[0].setPosition(sMusic7[0].getmXFirst(), sMusic7[0].getmYFirst());
						sMusic7[1].setPosition(sMusic7[1].getmXFirst(), sMusic7[1].getmYFirst());
						sMusic7[2].setPosition(sMusic7[2].getmXFirst(), sMusic7[2].getmYFirst());
						A79_6_1_SYMBALE.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[3] = true;
					setvisiableSprite(false, sMusic7[0],sMusic7[1],sMusic7[2],sMusicName[3]);
					
					animatePerson(aniMusican[3]);
				}
			});
		}else if (countShow6 == 1) {
			countShow6 = 0;
			aniMusican[3].animate(new long[]{600,600,600,1000}, new int[]{3,4,3,4},0,new IAnimationListener() {
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if(arg2==1){
						sMusic7[0].setPosition(sMusic7[0].getmXFirst()+50, sMusic7[0].getmYFirst()+20);
						sMusic7[1].setPosition(sMusic7[1].getmXFirst()+180, sMusic7[1].getmYFirst()+5);
						sMusic7[2].setPosition(sMusic7[2].getmXFirst()+20, sMusic7[2].getmYFirst()-20);
					}
					if (arg2==2) {
						sMusic7[0].setPosition(sMusic7[0].getmXFirst(), sMusic7[0].getmYFirst());
						sMusic7[1].setPosition(sMusic7[1].getmXFirst(), sMusic7[1].getmYFirst());
						sMusic7[2].setPosition(sMusic7[2].getmXFirst(), sMusic7[2].getmYFirst());
						A79_6_1_SYMBALE.play();
					}
					
					if(arg2==3) {
						sMusic7[0].setPosition(sMusic7[0].getmXFirst()+50, sMusic7[0].getmYFirst()+20);
						sMusic7[1].setPosition(sMusic7[1].getmXFirst()+180, sMusic7[1].getmYFirst()+5);
						sMusic7[2].setPosition(sMusic7[2].getmXFirst()+20, sMusic7[2].getmYFirst()-20);
						A79_6_1_BASA.play();
						sBird.setVisible(true);
					}
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchPerson[3] = true;
					setvisiableSprite(false, sMusic7[0],sMusic7[1],sMusic7[2],sMusicName[3], sBird);
					
					animatePerson(aniMusican[3]);
				}
			});
		}
		
	}
	private void touchPerson12() {
		A79_12_1.play();
		A79_12_1_ACODION.play();
		aniMusican[9].clearUpdateHandlers();
		setvisiableSprite(true, sMusic5[0],sMusic5[2], sMusicName[9]);
		aniMusican[9].animate(new long[]{800,800,800}, new int[]{3,4,5},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if (arg2 == 1) {
					setvisiableSprite(false, sMusic5[2]);
					setvisiableSprite(true, sMusic5[1]);
					
				}
				if (arg2 == 2) {
					setvisiableSprite(false, sMusic5[1],sMusic5[0]);
					setvisiableSprite(true, sMusic5[4], sMusic5[3]);
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[9] = true;
				setvisiableSprite(false, sMusic5[0],sMusic5[1],sMusic5[2], sMusic5[3],sMusic5[4],sMusicName[9]);
				animatePerson(aniMusican[9]);
			}
		});
	}
	private void touchPerson4() {
		A79_4_1.play();
		aniMusican[1].clearUpdateHandlers();
		setvisiableSprite(true, sMusic6[0],sMusic6[2], sMusic6[1], sMusicName[1]);
		
		aniMusican[1].animate(new long[]{700,700}, new int[]{4,3},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if (arg2 == 1) {
					sMusic6[1].setPosition(230, 270);
					A79_8_1_KODAIKO.play();
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[1] = true;
				setvisiableSprite(false, sMusic6[0],sMusic6[1],sMusic6[2],sMusicName[1]);
				animatePerson(aniMusican[1]);
			}
		});
	}
	private void touchPerson10(){
		A79_10_1.play();
		A79_10_1_KURARINET.play();
		aniMusican[7].clearUpdateHandlers();
		setvisiableSprite(true, sMusic4[0],sMusic4[1],sMusic4[2],sMusicName[7]);
		
		aniMusican[7].animate(new long[]{800,1200,800}, new int[]{3,4,4},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if (arg2 == 1) {
					setvisiableSprite(false, sMusic4[0],sMusic4[1],sMusic4[2]);
					setvisiableSprite(true, sMusic4[3], sMusic4[4], sMusic4[5]);
				}
				if (arg2 == 2) {
					if (countShow10==0) {
						countShow10 = 1;
					}else if (countShow10 == 1) {
						A79_10_1_HYU.play();
						sSnake.setVisible(true);
						countShow10 = 0;
					}
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[7]=true;
				setvisiableSprite(false, sMusic4[0],sMusic4[1],sMusic4[2], sMusic4[3],sMusic4[4],sMusic4[5],sMusicName[7]);
				sSnake.setVisible(false);
				animatePerson(aniMusican[7]);
			}
		});
	}
	private void touchPerson7(){
		A79_7_1.play();
		A79_7_1_TRUMPET.play();
		aniMusican[4].clearUpdateHandlers();
		if (countShow7==0) {
			countShow7 = 1;
		}else if (countShow7 == 1 ) {
			sTap1.setVisible(true);
			countShow7 = 0;
		}
		setvisiableSprite(true, sMusic3[0], sMusic3[1], sMusic3[2], sMusicName[4]);
		aniMusican[4].animate(new long[]{600,800}, new int[]{3,4},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if(arg2 == 1) {
					setvisiableSprite(false, sMusic3[0], sMusic3[1], sMusic3[2]);
					setvisiableSprite(true, sMusic3[4], sMusic3[3], sMusic3[5]);
					if (sTap1.isVisible()) {
						sTap1.setVisible(false);
						sTap2.setVisible(true);
					}
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[4]=true;
				setvisiableSprite(false, sMusic3[0], sMusic3[1], sMusic3[2], sMusic3[4], sMusic3[3], sMusic3[5], sMusicName[4]);
				sTap2.setVisible(false);
				animatePerson(aniMusican[4]);
			}
		});
	}
	private void touchPerson5(){
		A79_5_1.play();
		aniMusican[2].clearUpdateHandlers();
		setvisiableSprite(true, sMusicName[2], sMusic2[0],sMusic2[1],sMusic2[2]);
		aniMusican[2].animate(new long[]{600,800}, new int[]{3,4},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if (arg2 == 1) {
					A79_5_1_KIRARIN.play();
					sStick.setVisible(true);
					sStick.registerEntityModifier(new RotationAtModifier(0.8f, 0, 360, sStick.getWidth()/2,sStick.getHeight()/2 ));
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[2]=true;
				setvisiableSprite(false, sMusicName[2], sMusic2[0], sMusic2[1], sMusic2[2],sStick);
				animatePerson(aniMusican[2]);
			}
		});
	}
	private void touchPerson11(){
		A79_11_1.play();
		aniMusican[8].clearUpdateHandlers();
		setvisiableSprite(true, sMusic1[1], sMusic1[2], sMusicName[8]);
		aniMusican[8].animate(new long[]{600,900}, new int[]{3,4},0,new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if(arg2 == 1) {
					A79_11_1_KARAGADO.play();
					setvisiableSprite(false, sMusic1[1],sMusic1[2]);
					setvisiableSprite(true, sMusic1[0],sMusic1[3], sMusic1[4]);
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[8]=true;
				setvisiableSprite(false, sMusic1[0],sMusic1[3], sMusic1[4], sMusicName[8]);
				animatePerson(aniMusican[8]);
			}
		});
	}
	private void touchPerson9(){
		A79_9_1.play();
		aniMusican[6].clearUpdateHandlers();
		sMusicName[6].setVisible(true);
		
		aniMusican[6].animate(new long[]{300, 300, 800}, new int[]{3, 3, 4},0,
				new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				if (arg2 == 1) {
					A79_9_1_HOISUL.play();
				}
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				touchPerson[6]=true;
				sMusicName[6].setVisible(false);
				animatePerson(aniMusican[6]);
			}
		});
	}
	private void touchWindown(int id){
		
		switch (id) {
		case 0:
			if (countPerson == 0) {
				if (countSound[0]==0) {
					A79_2_3_YA.play();
					countSound[0] = 1;
				} else if (countSound[0] == 1) {
					A79_2_3_YA2.play();
					countSound[0] = 0;
				}
				sPersons1[0].setPosition(pointWindown1[0][0], pointWindown1[0][1]);
				sPersons1[0].setVisible(true);
				countPerson=1;
			} else if(countPerson==1){
				if (countSound[1]==0) {
					A79_2_4_BYE.play();
					countSound[1] = 1;
				}else if (countSound[1]==1) {
					A79_2_4_GENKI.play();
					countSound[1] = 0;
				}
				sPersons1[1].setPosition(pointWindown1[1][0], pointWindown1[1][1]);
				sPersons1[1].setVisible(true);
				countPerson=2;
			} else if (countPerson==2) {
				A79_2_5_GOKIGENYO.play();
				sPersons1[2].setPosition(pointWindown1[2][0], pointWindown1[2][1]);
				sPersons1[2].setVisible(true);
				countPerson = 3;
			}else if (countPerson==3) {
				A79_2_6_NYA.play();
				sPersons1[3].setPosition(pointWindown1[3][0], pointWindown1[3][1]);
				sPersons1[3].setVisible(true);
				countPerson=4;
			}else if (countPerson==4) {
				if (countSound[2]==0) {
					A79_2_7_KONNICHIWA.play();
					countSound[2] = 1;
				}else if (countSound[2] == 1) {
					A79_2_7_KONNICHIWA2.play();
					countSound[2] = 0;
				}
				sPersons1[4].setPosition(pointWindown1[4][0], pointWindown1[4][1]);
				sPersons1[4].setVisible(true);
				countPerson=5;
			}else if (countPerson==5) {
				if (countSound[3]==0) {
					A79_2_8_HAI.play();
					countSound[3] = 1;
				} else if (countSound[3]==1) {
					A79_2_8_HAI2.play();
					countSound[3] = 0;
				}
				sPersons1[5].setPosition(pointWindown1[5][0], pointWindown1[5][1]);
				sPersons1[5].setVisible(true);
				countPerson=6;
			}else if (countPerson==6) {
				if (countSound[4]==0) {
					A79_2_9_HARO.play();
					countSound[4] = 1;
				} else if (countSound[4] == 1) {
					A79_2_9_HARO2.play();
					countSound[4] = 0;
				}
				sPersons1[6].setPosition(pointWindown1[6][0], pointWindown1[6][1]);
				sPersons1[6].setVisible(true);
				countPerson=0;
			}
			mScene.registerUpdateHandler(new TimerHandler(1.2f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					if (sPersons1[0].isVisible()){
						setvisiableSprite(false, sPersons1[0]);
					} else if (sPersons1[1].isVisible()) {
						setvisiableSprite(false, sPersons1[1]);
					} else if (sPersons1[2].isVisible()) {
						setvisiableSprite(false, sPersons1[2]);
					} else if (sPersons1[3].isVisible()) {
						setvisiableSprite(false, sPersons1[3]);
					} else if (sPersons1[4].isVisible()) {
						setvisiableSprite(false, sPersons1[4]);
					} else if (sPersons1[5].isVisible()){
						setvisiableSprite(false, sPersons1[5]);
					} else if (sPersons1[6].isVisible()) {
						setvisiableSprite(false, sPersons1[6]);
					} 
					sBupbe1.setVisible(true);
					touchWindown1 = true;
				}
			}));
			break;
		case 1:
			if (countPerson==0) {
				if (countSound[0]==0) {
					A79_2_3_YA.play();
					countSound[0] = 1;
				} else if (countSound[0] == 1) {
					A79_2_3_YA2.play();
					countSound[0] = 0;
				}
				sPersons2[0].setPosition(pointWindown2[0][0], pointWindown2[0][1]);
				sPersons2[0].setVisible(true);
				countPerson=1;
			} else if(countPerson==1){
				if (countSound[1]==0) {
					A79_2_4_BYE.play();
					countSound[1] = 1;
				}else if (countSound[1]==1) {
					A79_2_4_GENKI.play();
					countSound[1] = 0;
				}
				sPersons2[1].setPosition(pointWindown2[1][0], pointWindown2[1][1]);
				sPersons2[1].setVisible(true);
				countPerson=2;
			} else if (countPerson==2) {
				A79_2_5_GOKIGENYO.play();
				sPersons2[2].setPosition(pointWindown2[2][0], pointWindown2[2][1]);
				sPersons2[2].setVisible(true);
				countPerson = 3;
			}else if (countPerson==3) {
				A79_2_6_NYA.play();
				sPersons2[3].setPosition(pointWindown2[3][0], pointWindown2[3][1]);
				sPersons2[3].setVisible(true);
				countPerson=4;
			}else if (countPerson==4) {
				if (countSound[2]==0) {
					A79_2_7_KONNICHIWA.play();
					countSound[2] = 1;
				}else if (countSound[2] == 1) {
					A79_2_7_KONNICHIWA2.play();
					countSound[2] = 0;
				}
				sPersons2[4].setPosition(pointWindown2[4][0], pointWindown2[4][1]);
				sPersons2[4].setVisible(true);
				countPerson=5;
			}else if (countPerson==5) {
				if (countSound[3]==0) {
					A79_2_8_HAI.play();
					countSound[3] = 1;
				} else if (countSound[3]==1) {
					A79_2_8_HAI2.play();
					countSound[3] = 0;
				}
				sPersons2[5].setPosition(pointWindown2[5][0], pointWindown2[5][1]);
				sPersons2[5].setVisible(true);
				countPerson=6;
			}else if (countPerson==6) {
				if (countSound[4]==0) {
					A79_2_9_HARO.play();
					countSound[4] = 1;
				} else if (countSound[4] == 1) {
					A79_2_9_HARO2.play();
					countSound[4] = 0;
				}
				sPersons2[6].setPosition(pointWindown2[6][0], pointWindown2[6][1]);
				sPersons2[6].setVisible(true);
				countPerson=0;
			}
			mScene.registerUpdateHandler(new TimerHandler(1.2f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					if (sPersons2[0].isVisible()){
						setvisiableSprite(false, sPersons2[0]);
					} else if (sPersons2[1].isVisible()) {
						setvisiableSprite(false, sPersons2[1]);
					} else if (sPersons2[2].isVisible()) {
						setvisiableSprite(false, sPersons2[2]);
					} else if (sPersons2[3].isVisible()) {
						setvisiableSprite(false, sPersons2[3]);
					} else if (sPersons2[4].isVisible()) {
						setvisiableSprite(false, sPersons2[4]);
					} else if (sPersons2[5].isVisible()){
						setvisiableSprite(false, sPersons2[5]);
					} else if (sPersons2[6].isVisible()) {
						setvisiableSprite(false, sPersons2[6]);
					} 
					sBupbe2.setVisible(true);
					touchWindown2 = true;
				}
			}));
			break;

		default:
			break;
		}
	}
	private void setvisiableSprite(boolean value, Sprite ...sprites){
		if (sprites != null) {
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].setPosition(sprites[i].getmXFirst(), sprites[i].getmYFirst());
				sprites[i].setVisible(value);
				
			}
		}
	}
	@Override
	protected void loadKaraokeSound() {
		A79_12_1_ACODION = loadSoundResourceFromSD(Vol3MickeyResource.A79_12_1_ACODION);
		A79_2_3_YA2 = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_3_YA2);
		A79_2_3_YA = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_3_YA);
		A79_2_4_BYE = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_4_BYE);
		A79_2_4_GENKI = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_4_GENKI);
		A79_2_5_GOKIGENYO = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_5_GOKIGENYO);
		A79_2_6_NYA = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_6_NYA);
		A79_2_7_KONNICHIWA2 = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_7_KONNICHIWA2);
		A79_2_7_KONNICHIWA = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_7_KONNICHIWA);
		A79_2_8_HAI2 = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_8_HAI2);
		A79_2_8_HAI = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_8_HAI);
		A79_2_9_HARO2 = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_9_HARO2);
		A79_2_9_HARO = loadSoundResourceFromSD(Vol3MickeyResource.A79_2_9_HARO);
		A79_3_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_3_1);
		A79_3_1_NYAN = loadSoundResourceFromSD(Vol3MickeyResource.A79_3_1_NYAN);
		A79_3_1_SUZAFON = loadSoundResourceFromSD(Vol3MickeyResource.A79_3_1_SUZAFON);
		A79_4_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_4_1);
		A79_5_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_5_1);
		A79_5_1_KIRARIN = loadSoundResourceFromSD(Vol3MickeyResource.A79_5_1_KIRARIN);
		A79_6_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_6_1);
		A79_6_1_BASA = loadSoundResourceFromSD(Vol3MickeyResource.A79_6_1_BASA);
		A79_6_1_SYMBALE = loadSoundResourceFromSD(Vol3MickeyResource.A79_6_1_SYMBALE);
		A79_7_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_7_1);
		A79_7_1_TRUMPET = loadSoundResourceFromSD(Vol3MickeyResource.A79_7_1_TRUMPET);
		A79_8_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_8_1);
		A79_8_1_KODAIKO = loadSoundResourceFromSD(Vol3MickeyResource.A79_8_1_KODAIKO);
		A79_8_1_ODAIKO = loadSoundResourceFromSD(Vol3MickeyResource.A79_8_1_ODAIKO);
		A79_8_1_PIERO = loadSoundResourceFromSD(Vol3MickeyResource.A79_8_1_PIERO);
		A79_9_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_9_1);
		A79_9_1_HOISUL = loadSoundResourceFromSD(Vol3MickeyResource.A79_9_1_HOISUL);
		A79_10_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_10_1);
		A79_10_1_HYU = loadSoundResourceFromSD(Vol3MickeyResource.A79_10_1_HYU);
		A79_10_1_KURARINET = loadSoundResourceFromSD(Vol3MickeyResource.A79_10_1_KURARINET);
		A79_11_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_11_1);
		A79_11_1_KARAGADO = loadSoundResourceFromSD(Vol3MickeyResource.A79_11_1_KARAGADO);
		A79_12_1 = loadSoundResourceFromSD(Vol3MickeyResource.A79_12_1);

	}
	private void stopSound(){
		A79_12_1_ACODION.stop(); 
		A79_2_3_YA2.stop(); 
		A79_2_3_YA.stop();
		A79_2_4_BYE.stop(); 
		A79_2_4_GENKI.stop();
		A79_2_5_GOKIGENYO.stop(); 
		A79_2_6_NYA.stop(); 
		A79_2_7_KONNICHIWA2.stop();
		A79_2_7_KONNICHIWA.stop();
		A79_2_8_HAI2.stop(); 
		A79_2_8_HAI.stop(); 
		A79_2_9_HARO2.stop();
		A79_2_9_HARO.stop();
		A79_3_1.stop();
		A79_3_1_NYAN.stop();
		A79_3_1_SUZAFON.stop();
		A79_4_1.stop();
		A79_5_1.stop();
		A79_5_1_KIRARIN.stop();
		A79_6_1.stop();
		A79_6_1_BASA.stop();
		A79_6_1_SYMBALE.stop();
		A79_7_1.stop();
		A79_7_1_TRUMPET.stop();
		A79_8_1.stop();
		A79_8_1_KODAIKO.stop();
		A79_8_1_ODAIKO.stop();
		A79_8_1_PIERO.stop();
		A79_9_1.stop(); 
		A79_9_1_HOISUL.stop();
		A79_10_1.stop();
		A79_10_1_HYU.stop(); 
		A79_10_1_KURARINET.stop();
		A79_11_1.stop(); 
		A79_11_1_KARAGADO.stop(); 
		A79_12_1.stop();
	}
	
}
