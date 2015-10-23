package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OnakaResource;

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
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
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

import android.util.Log;

public class Vol3Onaka extends BaseGameActivity implements
		IOnSceneTouchListener, IEntityModifierListener, IAnimationListener {

	private static final String TAG = "LOG_ONAKA";
	
	private TexturePack ttpOnaka1, ttpOnaka2, ttpOnaka3, ttpOnaka4, ttpOnaka5, ttpOnaka6, ttpOnaka7;
	private TexturePackTextureRegionLibrary ttpLibOnaka1, ttpLibOnaka2, ttpLibOnaka3, ttpLibOnaka4, ttpLibOnaka5, ttpLibOnaka6,
											ttpLibOnaka7;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion ttrBackground, ttrOnaka4Boy, ttrOnaka4aBoy, ttrOnaka4bBoy, ttrOnaka4cBoy, ttrOnaka4eBoy, ttrOnaka4gBoy, ttrOnaka4fBoy, ttrOnaka61Toire,
						  ttrOnaka611Toire, ttrOnaka62Toire, ttrOnaka621Toire, ttrOnaka71Houtyou,
						  ttrOnaka72Huta, ttrOnaka73Sara, ttrOnaka7a1Tamago, ttrOnaka7a2Tamago, ttrOnaka7b1Ringo, ttrOnaka7b2Ringo,
						  ttrOnaka7c1Cake, ttrOnaka7c2Cake, ttrOnaka7d1Curry, ttrOnaka7d2Curry, ttrOnaka7e1Pizza, ttrOnaka7e2Pizza,
						  ttrOnaka7f1Futomaki, ttrOnaka7f2Futomaki, ttrOnaka5aInonaka, ttrOnaka5bInonaka, ttrOnaka8Boy, ttrOnaka9aBen,
						  ttrOnaka9bBen;
	
	private Sprite sOnaka4Boy, sOnaka4aBoy, sOnaka4bBoy, sOnaka4cBoy, sOnaka4eBoy, sOnaka4fBoy, sOnaka4gBoy, sOnaka611Toire, sOnaka61Toire, sOnaka62Toire, sOnaka621Toire, 
				   sOnaka72Huta, sOnaka73Sara, sOnaka71Houtyou, sOnaka7a1Tamago, sOnaka7a2Tamago, sOnaka7b1Ringo, sOnaka7b2Ringo, sOnaka7c1Cake,
				   sOnaka7c2Cake, sOnaka7d1Curry, sOnaka7d2Curry, sOnaka7e1Pizza, sOnaka7e2Pizza, sOnaka7f1Futomaki, sOnaka7f2Futomaki, 
				   sOnaka5aInonaka, sOnaka8Boy, sOnaka9aBen, sOnaka9bBen;
	
	private Sprite mySprite;
	private Sprite sOnaka5bInonaka[] = new Sprite[3];
	
	private TiledTextureRegion tttrOnakaInonaka, tttrOnakaBoy, tttrOnakaSmoke, tttrOnakaTamago, tttrOnakaRingo, tttrOnakaCake,
							   tttrOnakaCurry, tttrOnakaPizza, tttrOnakaFutomaki, tttrOnaka9aBen, tttrOnaka9bBen;
	
	private AnimatedSprite aniOnakaInonaka, aniOnakaBoy, aniOnakaSmoke, aniOnakaTamago, aniOnakaRingo, aniOnakaCake,
						   aniOnakaCurry, aniOnakaPizza, aniOnakaFutomaki, aniOnaka9aBen, aniOnaka9bBen;
	
	private SequenceEntityModifier hutaModifier, foodModifier, fadeOutMOdifier, inonakaModifier, benModifier;
	
	private TimerHandler timerBoy, timerToire, timerHuta;
	
	
	private int iFlagFood = 1, iFlagInonaka = 1, iPY = 533;
	private int iCountInonaka = 0;
	private int pXBoyNew = 537, pYBoyNew = 46, pXBoy = -999, pYBoy = -999;
	private boolean isInonaka, isTouchHuta, isTouchFood, isTouchHoutyou, isSmoke, is8Boy, isToire, 
					isFaceBoy, isGimmick, isTouchToire, isToireOr8Boy, isVL;
	
	private Sound mp3OnakaItadakimasu, mp3Taberuoto, mp3Oishi, mp3Gochisou, mp3Onakasuita, mp3OnakaDudu,
				  mp3OnakaKonkon, mp3OnakaToire, mp3OnakaBowan, mp3OnakaSakuton, mp3OnakaPaka, mp3OnakaUnchi,
				  mp3OnakaUn, mp3OnakaBoyon, mp3OnakaMoni, mp3OnakaPowa, mp3OnakaHa, mp3OnakaNyun, mp3OnakaMokin;
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		Log.d(TAG, "ACTION_DOWN");
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if(aniOnakaInonaka.contains(pX, pY)) {
				if(isInonaka) {
					isInonaka = false;
					isTouchFood = false;
					aniOnakaInonaka.setVisible(true);
					aniOnakaSprite(aniOnakaInonaka, 1);					
				}
				
			} else if(sOnaka72Huta.contains(pX, pY)) {
				onakaTouchHutaEvent();
			} else if(checkContains(mySprite, 98, 151, 211, 291, pX, pY)) {
				onakaTouchFood(mySprite);
			} else if(sOnaka71Houtyou.contains(pX, pY)) {
				Log.d(TAG, "Houtyou..........................");
				onakaTouchHoutyou();
			} else if(sOnaka8Boy.contains(pX, pY)) {
				Log.d(TAG, "Touch Onaka8Boy....");
				if(isToireOr8Boy) {
					isToireOr8Boy = false;
					onakaTouch8Boy();
				}				
			} else if (checkContains(sOnaka61Toire, 184, 0, 383, 272, pX, pY)) {				
				onakaTouchOpenToire();					
			} else if(checkContains(sOnaka62Toire, 219, 16, 349, 252, pX, pY)) {
				Log.d(TAG, "tap vao de phot'.......");
				onakaTouchToire();
			} else if(checkContains(sOnaka4Boy, 73, 106, 349, 339, pX, pY)) {
				Log.d(TAG, "face boy'.......");
				onakaTouchFaceBoy();
			} else if(checkContains(sOnaka4bBoy, 73, 106, 349, 339, pX, pY)) {
				onakaTouchFaceBoy();
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources");
		
		ttpOnaka1 = mTexturePackLoaderFromSource.load("onaka1.xml");
		ttpOnaka1.loadTexture();
		ttpLibOnaka1 = ttpOnaka1.getTexturePackTextureRegionLibrary();
		
		ttpOnaka2 = mTexturePackLoaderFromSource.load("onaka2.xml");
		ttpOnaka2.loadTexture();
		ttpLibOnaka2 = ttpOnaka2.getTexturePackTextureRegionLibrary();
		
		ttpOnaka3 = mTexturePackLoaderFromSource.load("onaka3.xml");
		ttpOnaka3.loadTexture();
		ttpLibOnaka3 = ttpOnaka3.getTexturePackTextureRegionLibrary();
		
		ttpOnaka4 = mTexturePackLoaderFromSource.load("onaka4.xml");
		ttpOnaka4.loadTexture();
		ttpLibOnaka4 = ttpOnaka4.getTexturePackTextureRegionLibrary();
		
		ttpOnaka5 = mTexturePackLoaderFromSource.load("onaka5.xml");
		ttpOnaka5.loadTexture();
		ttpLibOnaka5 = ttpOnaka5.getTexturePackTextureRegionLibrary();
		
		ttpOnaka6 = mTexturePackLoaderFromSource.load("onaka6.xml");
		ttpOnaka6.loadTexture();
		ttpLibOnaka6 = ttpOnaka6.getTexturePackTextureRegionLibrary();
		
		ttpOnaka7 = mTexturePackLoaderFromSource.load("onaka7.xml");
		ttpOnaka7.loadTexture();
		ttpLibOnaka7 = ttpOnaka7.getTexturePackTextureRegionLibrary();
		
		this.ttrBackground = ttpLibOnaka7.get(Vol3OnakaResource.ONAKA_10_IPHONE_HAIKEI_ID);
		
		this.ttrOnaka4Boy = ttpLibOnaka1.get(Vol3OnakaResource.ONAKA_4_IPHONE_BOY_ID);
		this.ttrOnaka4aBoy = ttpLibOnaka1.get(Vol3OnakaResource.ONAKA_4A_IPHONE_BOY_ID);
		this.ttrOnaka4bBoy = ttpLibOnaka1.get(Vol3OnakaResource.ONAKA_4B_IPHONE_BOY_ID);
		this.tttrOnakaBoy = getTiledTextureFromPacker(ttpOnaka2,
				Vol3OnakaResource.ONAKA_4D_1_IPHONE_BOY_ID,
				Vol3OnakaResource.ONAKA_4D_2_IPHONE_BOY_ID);
		this.ttrOnaka4cBoy = ttpLibOnaka1.get(Vol3OnakaResource.ONAKA_4C_IPHONE_BOY_ID);
		this.ttrOnaka4eBoy = ttpLibOnaka2.get(Vol3OnakaResource.ONAKA_4E_IPHONE_BOY_ID);
		this.ttrOnaka4gBoy = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_4G_IPHONE_BOY_ID);
		this.ttrOnaka4fBoy = ttpLibOnaka2.get(Vol3OnakaResource.ONAKA_4F_IPHONE_BOY_ID);
		
		this.tttrOnakaInonaka = getTiledTextureFromPacker(ttpOnaka3, 
				Vol3OnakaResource.ONAKA_5_1_IPHONE_INONAKA_ID,
				Vol3OnakaResource.ONAKA_5_2_IPHONE_INONAKA_ID);
		this.ttrOnaka5aInonaka = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_5A_IPHONE_INONAKA_ID);
		this.ttrOnaka5bInonaka = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_5B_IPHONE_INONAKA_ID);
		
		this.ttrOnaka61Toire = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_6_1_IPHONE_TOIRE_ID);
		this.ttrOnaka611Toire = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_6_1_1_IPHONE_TOIRE_ID);
		this.ttrOnaka62Toire = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_6_2_IPHONE_TOIRE_ID);
		this.ttrOnaka621Toire = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_6_2_1_IPHONE_TOIRE_ID);
		
		this.ttrOnaka71Houtyou = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_7_1_IPHONE_HOUTYOU_ID);
		
		this.ttrOnaka72Huta = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_7_2_IPHONE_HUTA_ID);
		this.ttrOnaka73Sara = ttpLibOnaka3.get(Vol3OnakaResource.ONAKA_7_3_IPHONE_SARA_ID);
		
		this.tttrOnakaSmoke = getTiledTextureFromPacker(ttpOnaka3,
				Vol3OnakaResource.ONAKA_7_4_IPHONE_SMOKE_ID,
				Vol3OnakaResource.ONAKA_7_5_IPHONE_SMOKE_ID);
		
		this.ttrOnaka7a1Tamago = ttpLibOnaka4.get(Vol3OnakaResource.ONAKA_7A_1_IPHONE_TAMAGO_ID);
		this.ttrOnaka7a2Tamago = ttpLibOnaka4.get(Vol3OnakaResource.ONAKA_7A_2_IPHONE_TAMAGO_ID);
		this.tttrOnakaTamago = getTiledTextureFromPacker(ttpOnaka4,
				Vol3OnakaResource.ONAKA_7A_3_1_IPHONE_TAMAGO_ID,
				Vol3OnakaResource.ONAKA_7A_3_2_IPHONE_TAMAGO_ID);
		
		this.ttrOnaka7b1Ringo = ttpLibOnaka4.get(Vol3OnakaResource.ONAKA_7B_1_IPHONE_RINGO_ID);
		this.ttrOnaka7b2Ringo = ttpLibOnaka4.get(Vol3OnakaResource.ONAKA_7B_2_IPHONE_RINGO_ID);
		this.tttrOnakaRingo = getTiledTextureFromPacker(ttpOnaka4,
				Vol3OnakaResource.ONAKA_7B_3_1_IPHONE_RINGO_ID,
				Vol3OnakaResource.ONAKA_7B_3_2_IPHONE_RINGO_ID);
		
		this.ttrOnaka7c1Cake = ttpLibOnaka4.get(Vol3OnakaResource.ONAKA_7C_1_IPHONE_CAKE_ID);
		this.ttrOnaka7c2Cake = ttpLibOnaka5.get(Vol3OnakaResource.ONAKA_7C_2_IPHONE_CAKE_ID);
		this.tttrOnakaCake = getTiledTextureFromPacker(ttpOnaka5, 
				Vol3OnakaResource.ONAKA_7C_3_1_IPHONE_CAKE_ID,
				Vol3OnakaResource.ONAKA_7C_3_2_IPHONE_CAKE_ID);
		
		this.ttrOnaka7d1Curry = ttpLibOnaka5.get(Vol3OnakaResource.ONAKA_7D_1_IPHONE_CURRY_ID);
		this.ttrOnaka7d2Curry = ttpLibOnaka5.get(Vol3OnakaResource.ONAKA_7D_2_IPHONE_CURRY_ID);
		this.tttrOnakaCurry = getTiledTextureFromPacker(ttpOnaka5,
				Vol3OnakaResource.ONAKA_7D_3_1_IPHONE_CURRY_ID,
				Vol3OnakaResource.ONAKA_7D_3_2_IPHONE_CURRY_ID);
		
		this.ttrOnaka7e1Pizza = ttpLibOnaka5.get(Vol3OnakaResource.ONAKA_7E_1_IPHONE_PIZZA_ID);
		this.ttrOnaka7e2Pizza = ttpLibOnaka5.get(Vol3OnakaResource.ONAKA_7E_2_IPHONE_PIZZA_ID);
		this.tttrOnakaPizza = getTiledTextureFromPacker(ttpOnaka6, 
				Vol3OnakaResource.ONAKA_7E_3_1_IPHONE_PIZZA_ID,
				Vol3OnakaResource.ONAKA_7E_3_2_IPHONE_PIZZA_ID);
		
		this.ttrOnaka7f1Futomaki = ttpLibOnaka6.get(Vol3OnakaResource.ONAKA_7F_1_IPHONE_FUTOMAKI_ID);
		this.ttrOnaka7f2Futomaki = ttpLibOnaka7.get(Vol3OnakaResource.ONAKA_7F_2_IPHONE_FUTOMAKI_ID);
		this.tttrOnakaFutomaki = getTiledTextureFromPacker(ttpOnaka6, 
				Vol3OnakaResource.ONAKA_7F_3_1_IPHONE_FUTOMAKI_ID,
				Vol3OnakaResource.ONAKA_7F_3_2_IPHONE_FUTOMAKI_ID);
		
		this.ttrOnaka8Boy = ttpLibOnaka6.get(Vol3OnakaResource.ONAKA_8_IPHONE_BOY_ID);
		
		this.ttrOnaka9aBen = ttpLibOnaka7.get(Vol3OnakaResource.ONAKA_9A_1_IPHONE_BEN_ID);
		this.tttrOnaka9aBen = getTiledTextureFromPacker(ttpOnaka7, 
				Vol3OnakaResource.ONAKA_9A_2_IPHONE_BEN_ID,
				Vol3OnakaResource.ONAKA_9A_3_IPHONE_BEN_ID);
		
		this.ttrOnaka9bBen = ttpLibOnaka7.get(Vol3OnakaResource.ONAKA_9B_1_IPHONE_BEN_ID);
		this.tttrOnaka9bBen = getTiledTextureFromPacker(ttpOnaka7,
				Vol3OnakaResource.ONAKA_9B_2_IPHONE_BEN_ID,
				Vol3OnakaResource.ONAKA_9B_3_IPHONE_BEN_ID,
				Vol3OnakaResource.ONAKA_9B_4_IPHONE_BEN_ID,
				Vol3OnakaResource.ONAKA_9B_5_IPHONE_BEN_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		mp3OnakaItadakimasu = loadSoundResourceFromSD(Vol3OnakaResource.A3_4A_ITADAKIMASU);
		mp3OnakaHa = loadSoundResourceFromSD(Vol3OnakaResource.A3_4C_HA);
		mp3Taberuoto = loadSoundResourceFromSD(Vol3OnakaResource.A3_4D_TABERUOTO);
		mp3Oishi = loadSoundResourceFromSD(Vol3OnakaResource.A3_4E_OISHI);
		mp3Gochisou = loadSoundResourceFromSD(Vol3OnakaResource.A3_4F_GOCHISOU);
		mp3Onakasuita = loadSoundResourceFromSD(Vol3OnakaResource.A3_4G_ONAKASUITA);
		mp3OnakaDudu = loadSoundResourceFromSD(Vol3OnakaResource.A3_5A_DURU);
		mp3OnakaNyun = loadSoundResourceFromSD(Vol3OnakaResource.A3_5B_NYUN);
		mp3OnakaKonkon = loadSoundResourceFromSD(Vol3OnakaResource.A3_06_KONKON);
		mp3OnakaToire = loadSoundResourceFromSD(Vol3OnakaResource.A3_06_TOIRE);
		mp3OnakaBowan = loadSoundResourceFromSD(Vol3OnakaResource.A3_07_BOWAN);
		mp3OnakaSakuton = loadSoundResourceFromSD(Vol3OnakaResource.A3_07_SAKUTON);
		mp3OnakaPaka = loadSoundResourceFromSD(Vol3OnakaResource.A3_07_PAKA);
		mp3OnakaUnchi = loadSoundResourceFromSD(Vol3OnakaResource.A3_08_UNCHI);
		mp3OnakaUn = loadSoundResourceFromSD(Vol3OnakaResource.A3_08_UN);
		mp3OnakaBoyon = loadSoundResourceFromSD(Vol3OnakaResource.A3_08_BOYON);
		mp3OnakaMoni = loadSoundResourceFromSD(Vol3OnakaResource.A3_08_MONI);
		mp3OnakaPowa = loadSoundResourceFromSD(Vol3OnakaResource.A3_9_POWA);
		mp3OnakaMokin = loadSoundResourceFromSD(Vol3OnakaResource.A3_4B__MOKIN_5);
	}

	@Override
	protected void loadKaraokeScene() {
		
		mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrBackground, this.getVertexBufferObjectManager())));
		
		sOnaka61Toire = new Sprite(216, 41, ttrOnaka61Toire, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka61Toire);
		
		sOnaka611Toire = new Sprite(419, 52, ttrOnaka611Toire, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka611Toire);
		
		sOnaka62Toire = new Sprite(216, 41, ttrOnaka62Toire, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka62Toire);
		sOnaka62Toire.setVisible(false);
		
		sOnaka621Toire = new Sprite(481, 187, ttrOnaka621Toire, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka621Toire);
		sOnaka621Toire.setVisible(false);
		
		sOnaka4Boy = new Sprite(pXBoy, pYBoy, ttrOnaka4Boy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4Boy);
		
		sOnaka4aBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4aBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4aBoy);
		sOnaka4aBoy.setVisible(false);
		
		sOnaka4bBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4bBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4bBoy);
		sOnaka4bBoy.setVisible(false);
		
		sOnaka4cBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4cBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4cBoy);
		sOnaka4cBoy.setVisible(false);
		
		aniOnakaBoy = new AnimatedSprite(pXBoy, pYBoy, tttrOnakaBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaBoy);
		aniOnakaBoy.setVisible(false);
		
		sOnaka4eBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4eBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4eBoy);
		sOnaka4eBoy.setVisible(false);
		
		sOnaka4fBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4fBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4fBoy);
		sOnaka4fBoy.setVisible(false);
		
		sOnaka4gBoy = new Sprite(pXBoy, pYBoy, ttrOnaka4gBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka4gBoy);
		sOnaka4gBoy.setVisible(false);
		
		aniOnakaInonaka = new AnimatedSprite(688, 383, tttrOnakaInonaka, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaInonaka);
		aniOnakaInonaka.setVisible(false);
		
		sOnaka5aInonaka = new Sprite(745, 400, ttrOnaka5aInonaka, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka5aInonaka);
		sOnaka5aInonaka.setVisible(false);
		
		sOnaka73Sara = new Sprite(112, 415, ttrOnaka73Sara, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka73Sara);
		
		aniOnakaSmoke = new AnimatedSprite(85, 221, tttrOnakaSmoke, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaSmoke);
		aniOnakaSmoke.setVisible(false);
		
		sOnaka7a1Tamago = new Sprite(-999, -999, ttrOnaka7a1Tamago, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7a1Tamago);
		sOnaka7a1Tamago.setVisible(false);
		
		sOnaka7a2Tamago = new Sprite(-999, -999, ttrOnaka7a2Tamago, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7a2Tamago);
		sOnaka7a2Tamago.setVisible(false);
		
		aniOnakaTamago = new AnimatedSprite(-999, -999, tttrOnakaTamago, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaTamago);
		aniOnakaTamago.setVisible(false);
		
		sOnaka7b1Ringo = new Sprite(-999, -999, ttrOnaka7b1Ringo, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7b1Ringo);
		sOnaka7b1Ringo.setVisible(false);
		
		sOnaka7b2Ringo = new Sprite(-999, -999, ttrOnaka7b2Ringo, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7b2Ringo);
		sOnaka7b2Ringo.setVisible(false);
		
		aniOnakaRingo = new AnimatedSprite(-999, -999, tttrOnakaRingo, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaRingo);
		aniOnakaRingo.setVisible(false);
		
		sOnaka7c1Cake = new Sprite(-999, -999, ttrOnaka7c1Cake, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7c1Cake);
		sOnaka7c1Cake.setVisible(false);
		
		sOnaka7c2Cake = new Sprite(-999, -999, ttrOnaka7c2Cake, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7c2Cake);
		sOnaka7c2Cake.setVisible(false);
		
		aniOnakaCake = new AnimatedSprite(-999, -999, tttrOnakaCake, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaCake);
		aniOnakaCake.setVisible(false);
		
		sOnaka7d1Curry = new Sprite(-999, -999, ttrOnaka7d1Curry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7d1Curry);
		sOnaka7d1Curry.setVisible(false);
		
		sOnaka7d2Curry = new Sprite(-999, -999, ttrOnaka7d2Curry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7d2Curry);
		sOnaka7d2Curry.setVisible(false);
		
		aniOnakaCurry = new AnimatedSprite(-999, -999, tttrOnakaCurry, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaCurry);
		aniOnakaCurry.setVisible(false);
		
		sOnaka7e1Pizza = new Sprite(-999, -999, ttrOnaka7e1Pizza, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7e1Pizza);
		sOnaka7e1Pizza.setVisible(false);
		
		sOnaka7e2Pizza = new Sprite(-999, -999, ttrOnaka7e2Pizza, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7e2Pizza);
		sOnaka7e2Pizza.setVisible(false);
		
		aniOnakaPizza = new AnimatedSprite(-999, -999, tttrOnakaPizza, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaPizza);
		aniOnakaPizza.setVisible(false);
		
		sOnaka7f1Futomaki = new Sprite(-999, -999, ttrOnaka7f1Futomaki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7f1Futomaki);
		sOnaka7f1Futomaki.setVisible(false);
		
		sOnaka7f2Futomaki = new Sprite(-999, -999, ttrOnaka7f2Futomaki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka7f2Futomaki);
		sOnaka7f2Futomaki.setVisible(false);
		
		aniOnakaFutomaki = new AnimatedSprite(-999, -999, tttrOnakaFutomaki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnakaFutomaki);
		aniOnakaFutomaki.setVisible(false);
		
		sOnaka71Houtyou = new Sprite(-999, -999, ttrOnaka71Houtyou, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka71Houtyou);
		sOnaka71Houtyou.setVisible(false);
		
		sOnaka72Huta = new Sprite(113, 284, ttrOnaka72Huta, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka72Huta);
		
		sOnaka8Boy = new Sprite(537, 46, ttrOnaka8Boy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka8Boy);
		sOnaka8Boy.setVisible(false);
		
		sOnaka9aBen = new Sprite(-999, -999, ttrOnaka9aBen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka9aBen);
		sOnaka9aBen.setVisible(false);
		
		aniOnaka9aBen = new AnimatedSprite(-999, -999, tttrOnaka9aBen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnaka9aBen);
		aniOnaka9aBen.setVisible(false);
		
		sOnaka9bBen = new Sprite(-999, -999, ttrOnaka9bBen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka9bBen);
		sOnaka9bBen.setVisible(false);
		
		aniOnaka9bBen = new AnimatedSprite(-999, -999, tttrOnaka9bBen, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniOnaka9bBen);
		aniOnaka9bBen.setVisible(false);
		
		setVisibleBoy(sOnaka4Boy);
		
		addGimmicsButton(mScene, Vol3OnakaResource.SOUND_GIMMIC, Vol3OnakaResource.IMAGE_GIMMIC, ttpLibOnaka7);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("onaka/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("onaka/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "onaka/gfx/");
		super.onCreateResources();
	}
	
	@Override
	public synchronized void onResumeGame() {
		initial();
		hutaSleep();
		boySleep();
		sOnaka611Toire.registerUpdateHandler(new TimerHandler(4.0f, true, 
				new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						sOnaka611Toire.registerEntityModifier(new SequenceEntityModifier(
								new RotationAtModifier(0.5f, 0, -5, sOnaka611Toire.getWidth()/2, 0),
								new RotationAtModifier(0.5f, -5, 5, sOnaka611Toire.getWidth()/2, 0),
								new RotationAtModifier(0.5f, 5, 0, sOnaka611Toire.getWidth()/2, 0)
						));						
					}
				}));
				
		super.onResumeGame();
	}
	
	

	@Override
	public synchronized void onPauseGame() {
		resetData();
		initial();
		super.onPauseGame();
	}

	/**
	 * Initial all variable
	 */
	private void initial() {
		isTouchToire = true;
		isToireOr8Boy = false;
		isInonaka = true;
		isTouchHuta = true;
		isTouchFood = true;
		isTouchHoutyou = false;
		isSmoke = true;
		is8Boy = false;
		isToire = true;
		isFaceBoy = true;
		isGimmick = true;
		isVL = true;
		timerToire = new TimerHandler(3.0f, true, 
				new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						if (!isToire) {
							mEngine.unregisterUpdateHandler(timerToire);
							sOnaka62Toire.setVisible(false);
							sOnaka61Toire.setPosition(216, 41);
							sOnaka61Toire.setVisible(true);
							sOnaka611Toire.setVisible(true);		
							isToire = true;
							isGimmick = true;
							if(sOnaka8Boy.isVisible()) {
								iFlagInonaka = 1;
								is8Boy = true;
								isToireOr8Boy = true;
								isTouchToire = false;
							}
						}
					}
				});
	}
	
	private void resetData() {
		mEngine.clearUpdateHandlers();
		mScene.clearUpdateHandlers();
		
		setVisibleBoy(sOnaka4Boy);
		iPY = 533;
		iFlagFood = 1;
		sOnaka72Huta.setPosition(113, 284);
		
		sOnaka5aInonaka.setPosition(745, 400);
		sOnaka5aInonaka.clearEntityModifiers();
		sOnaka5aInonaka.clearEntityModifiers();
		sOnaka5aInonaka.setVisible(false);
		
		aniOnakaSmoke.stopAnimation();
		aniOnakaSmoke.clearEntityModifiers();
		aniOnakaSmoke.setVisible(false);
		
		aniOnakaTamago.stopAnimation();
		aniOnakaTamago.clearEntityModifiers();
		
		aniOnakaRingo.stopAnimation();
		aniOnakaRingo.clearEntityModifiers();
		
		aniOnakaCake.stopAnimation();
		aniOnakaCake.clearEntityModifiers();
		
		aniOnakaCurry.stopAnimation();
		aniOnakaCurry.clearEntityModifiers();
		
		aniOnakaPizza.stopAnimation();
		aniOnakaPizza.clearEntityModifiers();
		
		aniOnakaFutomaki.stopAnimation();
		aniOnakaFutomaki.clearEntityModifiers();
		
		setPositionVisibleAniFood();
		if(aniOnakaInonaka != null) {
			aniOnakaInonaka.stopAnimation();
			aniOnakaInonaka.clearEntityModifiers();
			aniOnakaInonaka.setPosition(688, 383);
			aniOnakaInonaka.setVisible(false);
		}
		
		aniOnakaBoy.stopAnimation();
		aniOnakaBoy.clearEntityModifiers();
		
		if(mySprite.isVisible()) {
			mySprite.clearEntityModifiers();
			mySprite.setPosition(-999, -999);
			mySprite.setVisible(false);
		}
		
		if(sOnaka71Houtyou.isVisible()) {
			sOnaka71Houtyou.clearEntityModifiers();
			sOnaka71Houtyou.setPosition(-999, -999);
			sOnaka71Houtyou.setVisible(false);
		}
		
		for (int i = 0; i < sOnaka5bInonaka.length; i++) {
			this.mScene.detachChild(sOnaka5bInonaka[i]);
			//sOnaka5bInonaka[i].setVisible(false);
		}
		
		iCountInonaka = 0;
		iFlagInonaka = 1;
		
		sOnaka611Toire.clearUpdateHandlers();
		sOnaka611Toire.setVisible(true);
		sOnaka61Toire.setPosition(216, 41);
		sOnaka61Toire.setVisible(true);
		sOnaka62Toire.setVisible(false);
		
		aniOnaka9aBen.stopAnimation();
		aniOnaka9aBen.clearEntityModifiers();
		aniOnaka9aBen.setPosition(-999, -999);
		aniOnaka9aBen.setVisible(false);
		
		aniOnaka9bBen.stopAnimation();
		aniOnaka9bBen.clearEntityModifiers();
		aniOnaka9bBen.setPosition(-999, -999);
		aniOnaka9bBen.setVisible(false);
		
		sOnaka9aBen.clearEntityModifiers();
		sOnaka9aBen.setPosition(-999, -999);
		sOnaka9aBen.setVisible(false);
		
		sOnaka9bBen.clearEntityModifiers();
		sOnaka9bBen.setPosition(-999, -999);
		sOnaka9bBen.setVisible(false);
		
		mp3Gochisou.pause();
		mp3Oishi.pause();
		mp3OnakaBowan.pause();
		mp3OnakaBoyon.pause();
		mp3OnakaDudu.pause();
		mp3OnakaHa.pause();
		mp3OnakaItadakimasu.pause();
		mp3OnakaKonkon.pause();
		mp3OnakaMokin.pause();
		mp3OnakaMoni.pause();
		mp3OnakaNyun.pause();
		mp3OnakaPaka.pause();
		mp3OnakaPowa.pause();
		mp3OnakaSakuton.pause();
		mp3Onakasuita.pause();
		mp3OnakaToire.pause();
		mp3OnakaUn.pause();
		mp3OnakaUnchi.pause();
		mp3Taberuoto.pause();
	}
	
	/**
	 * Animation Sprite
	 * @param AnimatedSprite
	 */
	private void aniOnakaSprite(AnimatedSprite aniSprite, int iLoop) {
		long pDurations[] = new long[] {400, 400};
		aniSprite.animate(pDurations, iLoop, this);
	}
	
	private void onakaTouchFaceBoy() {
		if ((isFaceBoy && sOnaka4Boy.isVisible()) || (isFaceBoy && sOnaka4bBoy.isVisible())) {
			mp3Onakasuita.play();
			isTouchFood = false;
			setVisibleBoy(sOnaka4gBoy);
			sOnaka4gBoy.registerEntityModifier(new DelayModifier(1.2f,
					new IEntityModifierListener() {						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							mScene.unregisterUpdateHandler(timerBoy);
						}						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							setVisibleBoy(sOnaka4Boy);
							isTouchFood = true;
							if(isVL)
								isFaceBoy = true;
							boySleep();
						}
					}));
		}
	}
	
	/**
	 * Event Touch HUTA
	 */
	private void onakaTouchHutaEvent() {
		if(isTouchHuta) {
			isTouchHuta = false;
			mScene.unregisterUpdateHandler(timerHuta);
			iFlagFood++;
			mp3OnakaPaka.play();
			sOnaka72Huta.registerEntityModifier(hutaModifier = new SequenceEntityModifier(
					new MoveYModifier(0.1f, 284, 20)));
			hutaModifier.addModifierListener(this);
		}
	}
	
	/**
	 * 
	 */
	private void onakaTouchFood(Sprite mySprite) {
		if(isTouchFood) {
			isTouchFood = false;
			isTouchHoutyou = false;
			isFaceBoy = false;
			isInonaka = false;
			mScene.unregisterUpdateHandler(timerBoy);
			mp3OnakaItadakimasu.play();
			mySprite.registerEntityModifier(foodModifier = new SequenceEntityModifier(
					new MoveModifier(0.7f, 126, 440, 194, 100)));
			foodModifier.addModifierListener(this);
		}
	}
	
	private void onakaTouchHoutyou() {
		
		if(isTouchHoutyou) {
			mySprite.setPosition(-999, -999);
			mySprite.setVisible(false);
			sOnaka71Houtyou.setVisible(false);
			isTouchHoutyou = false;
			mp3OnakaSakuton.play();
			if(mySprite.equals(sOnaka7a1Tamago)) {
				mySprite = sOnaka7a2Tamago;
			} else if(mySprite.equals(sOnaka7b1Ringo)) {
				mySprite = sOnaka7b2Ringo;
			} else if(mySprite.equals(sOnaka7c1Cake)) {
				mySprite = sOnaka7c2Cake;
			} else if(mySprite.equals(sOnaka7d1Curry)) {
				mySprite = sOnaka7d2Curry;
			} else if(mySprite.equals(sOnaka7e1Pizza)) {
				mySprite = sOnaka7e2Pizza;
			} else {
				mySprite = sOnaka7f2Futomaki;
			}
			
			mySprite.setPosition(126, 194);
			mySprite.setVisible(true);
		}
		
	}
	
	private void onakaTouch8Boy() {
		if(is8Boy) {
			is8Boy = false;
			mp3OnakaUn.play();			
			Random random = new Random();
			int iRandom = random.nextInt(2);
			
			if(iRandom == 0) {
			sOnaka9bBen.setPosition(-999, -999);
				sOnaka9bBen.setVisible(false);
				
				sOnaka9aBen.setPosition(741, 501);
				sOnaka9aBen.registerEntityModifier(new SequenceEntityModifier(
						new DelayModifier(1.5f, new IEntityModifierListener() {					
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {						
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {	
								mp3OnakaPowa.play();
								sOnaka9aBen.setVisible(true);
								sOnaka9aBen.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										isFaceBoy = true;						
										setVisibleBoy(sOnaka4Boy);
										isInonaka = true;
										aniOnakaInonaka.setPosition(688, 383);
										aniOnakaInonaka.setVisible(false);
										boySleep();
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOnaka61Toire.setPosition(-999, -999);
										sOnaka61Toire.setVisible(false);
										sOnaka611Toire.setVisible(false);
										sOnaka62Toire.setVisible(true);
										
										sOnaka9aBen.setVisible(false);
										aniOnaka9aBen.setPosition(741, 501);
										aniOnaka9aBen.setVisible(true);			
										
										long pDurations[] = new long[] {400, 400};
										aniOnaka9aBen.animate(pDurations);
										mp3OnakaBoyon.play();
										benMove(aniOnaka9aBen);
									}
								}));
								
							}
						})));
				
				
			} else {
				sOnaka9aBen.setPosition(-999, -999);
				sOnaka9aBen.setVisible(false);
				
				sOnaka9bBen.setPosition(741, 501);
				
				sOnaka9bBen.registerEntityModifier(new SequenceEntityModifier(
						new DelayModifier(1.5f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								mp3OnakaPowa.play();
								sOnaka9bBen.setVisible(true);
								sOnaka9bBen.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										isFaceBoy = true;						
										setVisibleBoy(sOnaka4Boy);
										boySleep();
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									sOnaka9bBen.setVisible(false);
										aniOnaka9bBen.setPosition(741, 501);
										aniOnaka9bBen.setVisible(true);
										long pDurations[] = new long[] {400, 400};
										int pFrame[] = new int[] {0, 1};
										aniOnaka9bBen.animate(pDurations, pFrame, 4);
										
										aniOnaka9bBen.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
											
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												isInonaka = true;
												aniOnakaInonaka.setPosition(688, 383);
												aniOnakaInonaka.setVisible(false);
											}
											
											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												mp3OnakaMoni.play();	
												
												sOnaka61Toire.setPosition(-999, -999);
												sOnaka61Toire.setVisible(false);
												sOnaka611Toire.setVisible(false);
												sOnaka62Toire.setVisible(true);
												
												long pDurations[] = new long[] {400, 400};
												int pFrame[] = new int[] {2, 3};
												aniOnaka9bBen.animate(pDurations, pFrame, 4);
												benMove(aniOnaka9bBen);
												
											}
										}));
										
									}
								}));
				}
						})
						));
				
			}
			
		}
	}

	private void onakaTouchOpenToire() {
		
		if(isToireOr8Boy) {
			mEngine.unregisterUpdateHandler(timerToire);
			isToireOr8Boy = false;
			onakaTouch8Boy();			
			
		} 
		if(isTouchToire){
			isToire = false;
			sOnaka61Toire.setPosition(-999, -999);
			sOnaka61Toire.setVisible(false);
			mp3OnakaKonkon.play();
			sOnaka62Toire.setVisible(true);
			Log.d(TAG, "isToire = true.................");
			timerToire.reset();
			mEngine.registerUpdateHandler(timerToire);
		}
	}
	
	private void onakaTouchToire() {
		mEngine.unregisterUpdateHandler(timerToire);
		if(!isToire) {
			isToire = true;
			mp3OnakaToire.play();
			sOnaka621Toire.setVisible(true);
			sOnaka621Toire.registerEntityModifier(new SequenceEntityModifier(
					new AlphaModifier(2.5f, 1.0f, 0.0f, new IEntityModifierListener() {									
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}									
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							sOnaka62Toire.setVisible(false);
							sOnaka61Toire.setPosition(216, 41);
							sOnaka61Toire.setVisible(true);
							sOnaka611Toire.setVisible(true);
							isGimmick = true;
							if(sOnaka8Boy.isVisible()) {
								iFlagInonaka = 1;
								is8Boy = true;
								isToireOr8Boy = true;
								isTouchToire = false;
							}
						}
					})));
		}
	}
	
	/**
	 * Random food
	 * @return Sprite
	 */
	private Sprite randomFood() {
		
		Random random = new Random();
		int iRandom = random.nextInt(6);
		
		if(iRandom == 0)
			return this.sOnaka7a1Tamago;
		if(iRandom == 1)
			return this.sOnaka7b1Ringo;
		if(iRandom == 2)
			return this.sOnaka7c1Cake;
		if(iRandom == 3)
			return this.sOnaka7d1Curry;
		if(iRandom == 4)
			return this.sOnaka7e1Pizza;
		else
			return this.sOnaka7f1Futomaki;
	}
	
	private void fadeOut() {
		mp3OnakaDudu.play();
		sOnaka5aInonaka.registerEntityModifier(fadeOutMOdifier = new SequenceEntityModifier(
				new ParallelEntityModifier(
						new MoveYModifier(1.0f, 400, 440),
						new AlphaModifier(1.0f, 1.0f, 0.0f))
				));
		fadeOutMOdifier.addModifierListener(this);
		
	}
	
	private void inonaka5BMove(int i) {
		iFlagInonaka++;
		Path pPath = new Path(4).to(702, 495).to(702, 475).to(775, 470).to(745, iPY);
		
		sOnaka5bInonaka[i] = new Sprite(702, 495, ttrOnaka5bInonaka, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOnaka5bInonaka[i]);
		mp3OnakaNyun.play();
		sOnaka5bInonaka[i].registerEntityModifier(inonakaModifier = new SequenceEntityModifier(
				new PathModifier(1.5f, pPath)));
		iPY -= 8;
		inonakaModifier.addModifierListener(this);
	}
	
	private void benMove(final AnimatedSprite aniSprite) {
		Path pPath = new Path(3).to(741, 501).to(400, 501).to(400, 100);
		aniSprite.registerEntityModifier(new SequenceEntityModifier(
				new PathModifier(3.0f, pPath, new IEntityModifierListener() {					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {						
					}					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aniSprite.stopAnimation();
						aniSprite.setPosition(-999, -999);
						aniSprite.setVisible(false);
						sOnaka621Toire.setVisible(true);
						sOnaka621Toire.registerEntityModifier(new SequenceEntityModifier(
								new AlphaModifier(2.5f, 1.0f, 0.0f, new IEntityModifierListener() {									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3OnakaToire.play();
									}									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOnaka62Toire.setVisible(false);
										sOnaka61Toire.setPosition(216, 41);
										sOnaka61Toire.setVisible(true);
										sOnaka611Toire.setVisible(true);
										isTouchToire = true;
										isGimmick = true;
										isTouchFood = true;
										isTouchHuta = true;
										hutaSleep();
									}
								})));
					}
				})));
	}

	private void setPositionVisibleAniFood() {
		aniOnakaTamago.setPosition(-999, -999);
		aniOnakaTamago.setVisible(false);
		aniOnakaRingo.setPosition(-999, -999);
		aniOnakaRingo.setVisible(false);
		aniOnakaCake.setPosition(-999, -999);
		aniOnakaCake.setVisible(false);
		aniOnakaCurry.setPosition(-999, -999);
		aniOnakaCurry.setVisible(false);
		aniOnakaPizza.setPosition(-999, -999);
		aniOnakaPizza.setVisible(false);
		aniOnakaFutomaki.setPosition(-999, -999);
		aniOnakaFutomaki.setVisible(false);
	}
	
	private void setVisibleBoy(Sprite sprite) {
		
		if(sprite.equals(sOnaka4Boy)) {
			sOnaka4Boy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4Boy.setVisible(true);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
			
		}
		if(sprite.equals(sOnaka4aBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4aBoy.setVisible(true);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if(sprite.equals(sOnaka4cBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4cBoy.setVisible(true);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if(sprite.equals(sOnaka4eBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4eBoy.setVisible(true);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if(sprite.equals(sOnaka4fBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4fBoy.setVisible(true);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if(sprite.equals(aniOnakaBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoyNew, pYBoyNew);
			aniOnakaBoy.setVisible(true);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if(sprite.equals(sOnaka8Boy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka8Boy.setVisible(true);
		}
		if(sprite.equals(sOnaka4gBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoy, pYBoy);
			sOnaka4bBoy.setVisible(false);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4gBoy.setVisible(true);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
		if (sprite.equals(sOnaka4bBoy)) {
			sOnaka4Boy.setPosition(pXBoy, pYBoy);
			sOnaka4Boy.setVisible(false);
			
			sOnaka4aBoy.setPosition(pXBoy, pYBoy);
			sOnaka4aBoy.setVisible(false);
			
			sOnaka4bBoy.setPosition(pXBoyNew, pYBoyNew);
			sOnaka4bBoy.setVisible(true);
			
			sOnaka4cBoy.setPosition(pXBoy, pYBoy);
			sOnaka4cBoy.setVisible(false);
			
			sOnaka4eBoy.setPosition(pXBoy, pYBoy);
			sOnaka4eBoy.setVisible(false);
			
			sOnaka4fBoy.setPosition(pXBoy, pYBoy);
			sOnaka4fBoy.setVisible(false);
			
			sOnaka4gBoy.setPosition(pXBoy, pYBoy);
			sOnaka4gBoy.setVisible(false);
			
			aniOnakaBoy.setPosition(pXBoy, pYBoy);
			aniOnakaBoy.setVisible(false);
			
			sOnaka8Boy.setPosition(pXBoy, pYBoy);
			sOnaka8Boy.setVisible(false);
		}
	}
	
	private void hutaSleep() {
		mScene.unregisterUpdateHandler(timerHuta);
		timerHuta = new TimerHandler(6.0f, true, 
				new ITimerCallback() {					
					@Override
					public void onTimePassed(TimerHandler arg0) {						
						sOnaka72Huta.registerEntityModifier(new SequenceEntityModifier(
								new MoveXModifier(0.1f, 113, 130),
								new MoveXModifier(0.1f, 130, 113)
								));												
					}
				});
		mScene.registerUpdateHandler(timerHuta);
	}
	
	private void boySleep() {
		mScene.unregisterUpdateHandler(timerBoy);
		timerBoy = new TimerHandler(20.0f, true, 
				new ITimerCallback() {				
					@Override
					public void onTimePassed(TimerHandler arg0) {
						mp3OnakaMokin.play();
						setVisibleBoy(sOnaka4bBoy);
						isFaceBoy = true;
						mScene.unregisterUpdateHandler(timerBoy);
					}
				});
		mScene.registerUpdateHandler(timerBoy);
	}
	
	/** Entity modifier listener */
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		if(pModifier.equals(hutaModifier)) {
			
			if(iFlagFood == 6) {
				Log.d(TAG, "............." + Integer.toString(iFlagFood));
				isVL = false;
				mySprite.setPosition(-999, -999);
				mySprite.setVisible(false);
				sOnaka71Houtyou.setVisible(false);
				isTouchHoutyou = false;
				isTouchHuta = false;
				isTouchFood = false;
				if(isSmoke) {
					isSmoke = false;
					aniOnakaSmoke.setVisible(true);
					mp3OnakaBowan.play();
					aniOnakaSprite(aniOnakaSmoke, 1);
					mScene.unregisterUpdateHandler(timerBoy);
				}
				iFlagFood = 1;
				isFaceBoy = false;
			} else {
				mySprite = randomFood();
				mySprite.setPosition(126, 194);
				mySprite.setVisible(true);
				sOnaka71Houtyou.setPosition(223, 250);
				sOnaka71Houtyou.setVisible(true);
				isTouchHoutyou = true;
			}
		}
		if(pModifier.equals(foodModifier)) {
			
			setVisibleBoy(sOnaka4aBoy);
			sOnaka4aBoy.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mySprite.setPosition(-999, -999);
					mySprite.setVisible(false);
					
					setVisibleBoy(aniOnakaBoy);
					aniOnakaSprite(aniOnakaBoy, 2);
					mp3Taberuoto.play();
				}
			}));			
		}
		
		if(pModifier.equals(fadeOutMOdifier)) {
			isTouchFood = true;
			isTouchHuta = true;
			sOnaka71Houtyou.setPosition(-999, -999);
			sOnaka71Houtyou.setVisible(false);
			sOnaka72Huta.setPosition(sOnaka72Huta.getmXFirst(), sOnaka72Huta.getmYFirst());
			hutaSleep();
			setVisibleBoy(sOnaka4Boy);
			isFaceBoy = true;
			boySleep();
			inonaka5BMove(iCountInonaka);
			iCountInonaka++;
			if(iFlagInonaka == 4) {
				isFaceBoy = false;
				isTouchHuta = false;
				isTouchFood = false;
				mScene.unregisterUpdateHandler(timerHuta);
			}
		}
		if(pModifier.equals(inonakaModifier)) {
			if(iFlagInonaka == 4) {
				Log.d(TAG, "iFlagInonaka: = " + Integer.toString(iFlagInonaka));
				
				for (int i = 0; i < sOnaka5bInonaka.length; i++) {
					//this.mScene.detachChild(sOnaka5bInonaka[i]);
					sOnaka5bInonaka[i].setVisible(false);
				}
				iCountInonaka = 0;
				aniOnakaInonaka.setPosition(-999, -999);
				aniOnakaInonaka.setVisible(false);
				mScene.unregisterUpdateHandler(timerBoy);
				
				mp3OnakaUnchi.play();
				setVisibleBoy(sOnaka8Boy);
				
				iPY = 533;
				isFaceBoy = false;
				
				if(sOnaka61Toire.isVisible() && sOnaka8Boy.isVisible()) {
					iFlagInonaka = 1;
					is8Boy = true;
					isToireOr8Boy = true;
					isTouchToire = false;
					isToire = true;
					mEngine.unregisterUpdateHandler(timerToire);
				}
			}
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> pModifier, IEntity arg1) {		
		if (pModifier.equals(inonakaModifier) && iFlagInonaka == 4) {
			isTouchFood = false;
		}
	}
	
	/** Animation listener */
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if(pAnimatedSprite.equals(aniOnakaSmoke)) {
			isSmoke = true;
			aniOnakaSmoke.setVisible(false);
			mp3OnakaHa.play();
			if(mySprite.equals(sOnaka7a1Tamago) || mySprite.equals(sOnaka7a2Tamago)) {
				aniOnakaTamago.setPosition(126, 194);
				aniOnakaTamago.setVisible(true);
				aniOnakaSprite(aniOnakaTamago, 2);
			} else if(mySprite.equals(sOnaka7b1Ringo) || mySprite.equals(sOnaka7b2Ringo)) {
				aniOnakaRingo.setPosition(126, 194);
				aniOnakaRingo.setVisible(true);
				aniOnakaSprite(aniOnakaRingo, 2);
			} else if(mySprite.equals(sOnaka7c1Cake) || mySprite.equals(sOnaka7c2Cake)) {
				aniOnakaCake.setPosition(126, 194);
				aniOnakaCake.setVisible(true);
				aniOnakaSprite(aniOnakaCake, 2);
			} else if(mySprite.equals(sOnaka7d1Curry) || mySprite.equals(sOnaka7d2Curry)) {				
				aniOnakaCurry.setPosition(126, 194);
				aniOnakaCurry.setVisible(true);
				aniOnakaSprite(aniOnakaCurry, 2);
			} else if(mySprite.equals(sOnaka7e1Pizza) || mySprite.equals(sOnaka7e2Pizza)) {				
				aniOnakaPizza.setPosition(126, 194);
				aniOnakaPizza.setVisible(true);
				aniOnakaSprite(aniOnakaPizza, 2);
			} else {
				aniOnakaFutomaki.setPosition(126, 194);
				aniOnakaFutomaki.setVisible(true);
				aniOnakaSprite(aniOnakaFutomaki, 2);
			}
			
			setVisibleBoy(sOnaka4cBoy);
		}
		if(pAnimatedSprite.equals(aniOnakaBoy)) {
			
			if(mySprite.equals(sOnaka7a1Tamago) || mySprite.equals(sOnaka7b1Ringo) ||
					mySprite.equals(sOnaka7c1Cake) || mySprite.equals(sOnaka7d1Curry) ||
					mySprite.equals(sOnaka7e1Pizza) || mySprite.equals(sOnaka7f1Futomaki)) {
				mp3Gochisou.play();
				setVisibleBoy(sOnaka4fBoy);
			}
				
			else {
				mp3Oishi.play();
				setVisibleBoy(sOnaka4eBoy);
			}
			//isFaceBoy = true;
			sOnaka5aInonaka.setVisible(true);
			fadeOut();			
		}
		
		if(pAnimatedSprite.equals(aniOnakaInonaka)) {
			isInonaka = true;
			isTouchFood = true;
			aniOnakaInonaka.setPosition(688, 383);
			aniOnakaInonaka.setVisible(false);
		}
		
		if(pAnimatedSprite.equals(aniOnakaTamago) || pAnimatedSprite.equals(aniOnakaRingo) || pAnimatedSprite.equals(aniOnakaCake)
				|| pAnimatedSprite.equals(aniOnakaCurry) || pAnimatedSprite.equals(aniOnakaPizza) || pAnimatedSprite.equals(aniOnakaFutomaki)) {
			
			setPositionVisibleAniFood();
			setVisibleBoy(sOnaka4Boy);		
			boySleep();
			sOnaka72Huta.setPosition(113, 284);
			isTouchHuta = true;
			hutaSleep();
			isTouchFood = true;
			isFaceBoy = true;
			isVL = true;
		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {		
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {		
	}

	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int arg1) {
		if(pAnimatedSprite.equals(aniOnakaBoy)) {
			isInonaka = false;
			aniOnakaInonaka.setPosition(688, 383);
			aniOnakaInonaka.setVisible(true);
		}
		
		if(pAnimatedSprite.equals(benModifier)) {
			
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		if(isGimmick) {
			isGimmick = false;
			onakaTouchOpenToire();
		}		
	}
}