/* Vol3Omotya.java
* Create on May 18, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OmotyaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Omotya extends BaseGameActivity implements IOnSceneTouchListener,
	IEntityModifierListener, IAnimationListener {
	
	public static final String TAG = " Vol3Omotya ";
	
	
	private TexturePack trpOmotya1, trpOmotya2, trpOmotya3;
	private TexturePackTextureRegionLibrary trpLibOmotya1, trpLibOmotya2, trpLibOmotya3;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion trBackground1, trBackground2, trOrange, trPurple, trYellow, trStarLeft, trStarCenter,
			trStarRight, trStarBig, trPrince, trCat,trBoy, trFire, trAirplane, trMoon, trZZZ, trToyBoxBuck, 
			trToyBoxFont, tmpWalk;
	
	private Sprite sBackground1, sBackground2, sOrange1, sPurple1, sYellow1, sOrange2, sPurple2, sYellow2,
			sOrange3, sPurple3, sYellow3, sOrange4, sPurple4, sYellow4,
			sStarLeft, sStarCenter, sStarRight, sStarBig, sPrince, sCat,sBoy, sFire, sAirplane, sMoon, sZZZ,
			sToyBoxBuck, sToyBoxFont;
	
	private TiledTextureRegion ttrWalk, ttrPrincessGirl, ttrDoor, ttrMonster, ttrBear;
	private AnimatedSprite aniWalk, aniPrincessGirl, aniDoor, aniMonster, aniBear, aniWalkTow, aniWalkLoop1,
			aniWalkLoop2;
	private Sprite sWalkAll, sWalkLoop;
	private boolean istouchAirplane = true, istouchBear=true, istouchMonster= true, istouchMoon = true,
			istouchCat=true, istouchStarLeft=true, istouchGirl = true;
	private boolean istouchWalk[] = new boolean[12];
	private int currentDoor=0, currentStar =0, currenGirl=0;
	private Timer timerFire = new Timer();
	private Timer timerWalk = new Timer();
	private Sound  E0006_TRIANGLE, E00134_SYARARAN, E00135_GATYA, E00136_BATAN, E00137_PYON2, 
			E00138_TRUMPET, E00139_KODAIKO, E00140_OODAIKO, E00141_GUSBANA, E00142_RAJIKON, 
			E0045_SANAGI, E0047_SAKURA;
	
	
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("omotya/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("omotya/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "omotya/gfx/");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		trpOmotya1 = mTexturePackLoaderFromSource.load("omotya1.xml");
		trpOmotya1.loadTexture();
		trpLibOmotya1 = trpOmotya1.getTexturePackTextureRegionLibrary();
		
		trpOmotya2 = mTexturePackLoaderFromSource.load("omotya2.xml");
		trpOmotya2.loadTexture();
		trpLibOmotya2 = trpOmotya2.getTexturePackTextureRegionLibrary();
		
		trpOmotya3 = mTexturePackLoaderFromSource.load("omotya3.xml");
		trpOmotya3.loadTexture();
		trpLibOmotya3 = trpOmotya3.getTexturePackTextureRegionLibrary();
		
		trBackground1 = trpLibOmotya2.get(Vol3OmotyaResource.A4_12_1_IPHONE4_HAIKEI_OUTSIDE_ID);
		trBackground2 = trpLibOmotya2.get(Vol3OmotyaResource.A4_12_2_IPHONE4_HAIKEI_ROOM_ID);
		tmpWalk = trpLibOmotya3.get(Vol3OmotyaResource.TEMP_WALK_ID);
		
		trOrange = trpLibOmotya1.get(Vol3OmotyaResource.A4_07_3_IPHONE4_NOTE_ORANGE_ID);
		trPurple = trpLibOmotya1.get(Vol3OmotyaResource.A4_07_4_IPHONE4_NOTE_PURPLE_ID);
		trYellow = trpLibOmotya1.get(Vol3OmotyaResource.A4_07_5_IPHONE4_NOTE_YELLOW_ID);
		
		trStarLeft = trpLibOmotya1.get(Vol3OmotyaResource.A4_03_1_IPHONE4_STAR_LEFT_ID);
		trStarCenter = trpLibOmotya1.get(Vol3OmotyaResource.A4_03_2_IPHONE4_STAR_CENTER_ID);
		
		trStarRight = trpLibOmotya1.get(Vol3OmotyaResource.A4_03_3_IPHONE4_STAR_RIGHT_ID);
		trStarBig = trpLibOmotya3.get(Vol3OmotyaResource.A4_03_4_IPHONE4_STAR_BIG_ID);
		
		trPrince = trpLibOmotya1.get(Vol3OmotyaResource.A4_04_1_IPHONE4_PRINCESS_ARM_15_ID);
		trCat = trpLibOmotya1.get(Vol3OmotyaResource.A4_06_IPHONE4_CAT_ID);
		
		trBoy = trpLibOmotya1.get(Vol3OmotyaResource.A4_05_3_IPHONE4_BOY_ID);
		trFire = trpLibOmotya1.get(Vol3OmotyaResource.A4_08_4_IPHONE4_FIRE_ID);
		trAirplane = trpLibOmotya1.get(Vol3OmotyaResource.A4_10_1_IPHONE4_AIRPLANE_ID);
		trMoon = trpLibOmotya1.get(Vol3OmotyaResource.A4_12_3_IPHONE4_HAIKEI_MOON_ID);
		trZZZ = trpLibOmotya1.get(Vol3OmotyaResource.A4_09_3_IPHONE4_ZZZ_ID);
		trToyBoxBuck = trpLibOmotya1.get(Vol3OmotyaResource.A4_11_1_IPHONE4_TOYBOX_BUCK_ID);
		trToyBoxFont = trpLibOmotya1.get(Vol3OmotyaResource.A4_11_2_IPHONE4_TOYBOX_FRONT_ID);
		
		ttrPrincessGirl =  getTiledTextureFromPacker(trpOmotya1,
				Vol3OmotyaResource.A4_04_2_IPHONE4_PRINCESS_PINK_ID,
				Vol3OmotyaResource.A4_04_3_IPHONE4_PRINCESS_PURPLE_ID,
				Vol3OmotyaResource.A4_04_4_IPHONE4_PRINCESS_ORANGE_ID);
		
		ttrDoor = getTiledTextureFromPacker(trpOmotya1,
				Vol3OmotyaResource.A4_05_1_IPHONE4_DOOR_CLOSE_ID,
				Vol3OmotyaResource.A4_05_2_IPHONE4_DOOR_OPEN_ID);
		
		ttrMonster = getTiledTextureFromPacker(trpOmotya1,
				Vol3OmotyaResource.A4_08_1_IPHONE4_MONSTER1_ID,
				Vol3OmotyaResource.A4_08_2_IPHONE4_MONSTER2_ID,
				Vol3OmotyaResource.A4_08_3_IPHONE4_MONSTER3_ID);
		ttrBear = getTiledTextureFromPacker(trpOmotya1,
				Vol3OmotyaResource.A4_09_1_IPHONE4_BEAR_SLEEP_ID,
				Vol3OmotyaResource.A4_09_2_IPHONE4_BEAR_WAKE_ID);
		
		ttrWalk = getTiledTextureFromPacker(trpOmotya1,
				Vol3OmotyaResource.A4_07_1_IPHONE4_WALK1_ID,
				Vol3OmotyaResource.A4_07_2_IPHONE4_WALK2_ID);

	}
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene ");
		this.mScene = new Scene();
		this.sBackground1 = new Sprite(0, 0, trBackground1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground1);
		
		this.sStarRight =new Sprite(849, 32, trStarRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sStarRight);
		this.sStarLeft =new Sprite(547, 27, trStarLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sStarLeft);
		this.sStarCenter =new Sprite(638, 126, trStarCenter, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sStarCenter);
		this.sStarBig =new Sprite(50, -800, trStarBig, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sStarBig);
		this.sMoon = new Sprite(380, 35, trMoon	, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMoon);
		this.sBackground2 = new Sprite(0, 0, trBackground2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground2);
		
		this.sBoy = new Sprite(121, 230, trBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBoy);
		sBoy.setVisible(false);
		this.aniDoor = new AnimatedSprite(78, 106, ttrDoor, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDoor);
		aniDoor.setAlpha(1.0f);
		this.sToyBoxBuck = new Sprite(303, 349, trToyBoxBuck,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sToyBoxBuck);
		
		
		this.sAirplane = new Sprite(170, 35, trAirplane, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sAirplane);
		this.aniBear = new AnimatedSprite(463, 173, ttrBear	, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniBear);
		this.sZZZ = new Sprite(587, 130, trZZZ, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sZZZ);
		this.sFire = new Sprite(222, 138, trFire, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFire);
		sFire.setVisible(false);
		this.aniMonster = new AnimatedSprite(292, 131, ttrMonster	, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniMonster);
		this.sToyBoxFont = new Sprite(304, 405, trToyBoxFont,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sToyBoxFont);
		
		this.sWalkAll = new Sprite(0, 340, tmpWalk, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sWalkAll);
		this.sWalkAll.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sWalkAll.setAlpha(0.0f);
		
		this.aniWalk = new AnimatedSprite(10, 5, ttrWalk, this.getVertexBufferObjectManager());
		this.sWalkAll.attachChild(aniWalk);
		this.aniWalkTow = new AnimatedSprite(640, 5, ttrWalk, this.getVertexBufferObjectManager());
		this.sWalkAll.attachChild(aniWalkTow);
		
		this.sOrange1 = new Sprite(437, 22, trOrange, this.getVertexBufferObjectManager());
		this.aniWalk.attachChild(sOrange1);
		sOrange1.setVisible(false);
		this.sPurple1 = new Sprite(52, 32, trPurple, this.getVertexBufferObjectManager());
		this.aniWalk.attachChild(sPurple1);
		sPurple1.setVisible(false);
		this.sYellow1 = new Sprite(245, 40, trYellow, this.getVertexBufferObjectManager());
		this.aniWalk.attachChild(sYellow1);
		sYellow1.setVisible(false);
		
		this.sOrange2 = new Sprite(437, 22, trOrange, this.getVertexBufferObjectManager());
		this.aniWalkTow.attachChild(sOrange2);
		sOrange2.setVisible(false);
		this.sPurple2 = new Sprite(52, 32, trPurple, this.getVertexBufferObjectManager());
		this.aniWalkTow.attachChild(sPurple2);
		sPurple2.setVisible(false);
		this.sYellow2 = new Sprite(245, 40, trYellow, this.getVertexBufferObjectManager());
		this.aniWalkTow.attachChild(sYellow2);
		sYellow2.setVisible(false);
		
		sWalkLoop = new Sprite(1260, 340, tmpWalk, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sWalkLoop);
		this.sWalkLoop.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sWalkLoop.setAlpha(0.0f);
		
		this.aniWalkLoop1 = new AnimatedSprite(10, 5, ttrWalk, this.getVertexBufferObjectManager());
		this.sWalkLoop.attachChild(aniWalkLoop1);
		this.aniWalkLoop2 = new AnimatedSprite(640, 5, ttrWalk, this.getVertexBufferObjectManager());
		this.sWalkLoop.attachChild(aniWalkLoop2);
		
		this.sOrange3 = new Sprite(437, 22, trOrange, this.getVertexBufferObjectManager());
		this.aniWalkLoop1.attachChild(sOrange3);
		this.sOrange3.setVisible(false);
		this.sPurple3 = new Sprite(52, 32, trPurple, this.getVertexBufferObjectManager());
		this.aniWalkLoop1.attachChild(sPurple3);
		this.sPurple3.setVisible(false);
		this.sYellow3 = new Sprite(245, 40, trYellow, this.getVertexBufferObjectManager());
		this.aniWalkLoop1.attachChild(sYellow3);
		this.sYellow3.setVisible(false);
		
		this.sOrange4 = new Sprite(437, 22, trOrange, this.getVertexBufferObjectManager());
		this.aniWalkLoop2.attachChild(sOrange4);
		sOrange4.setVisible(false);
		this.sPurple4 = new Sprite(52, 32, trPurple, this.getVertexBufferObjectManager());
		this.aniWalkLoop2.attachChild(sPurple4);
		sPurple4.setVisible(false);
		this.sYellow4 = new Sprite(245, 40, trYellow, this.getVertexBufferObjectManager());
		this.aniWalkLoop2.attachChild(sYellow4);
		sYellow4.setVisible(false);
		
		this.aniPrincessGirl = new AnimatedSprite(604, 131, ttrPrincessGirl, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPrincessGirl);
		this.sPrince = new Sprite(606, 191, trPrince, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sPrince);
		
		this.sCat = new Sprite(773, 489, trCat, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCat);
		
		addGimmicsButton(mScene, Vol3OmotyaResource.SOUND_GIMMIC, 
				Vol3OmotyaResource.IMAGE_GIMMIC, trpLibOmotya1);
		
		this.mScene.setOnSceneTouchListener(this);

	}

	@Override
	protected void loadKaraokeSound() {
		E0006_TRIANGLE = loadSoundResourceFromSD(Vol3OmotyaResource.E0006_TRIANGLE);
		E00134_SYARARAN = loadSoundResourceFromSD(Vol3OmotyaResource.E00134_SYARARAN);
		E00135_GATYA = loadSoundResourceFromSD(Vol3OmotyaResource.E00135_GATYA);
		E00136_BATAN = loadSoundResourceFromSD(Vol3OmotyaResource.E00136_BATAN);
		E00137_PYON2 = loadSoundResourceFromSD(Vol3OmotyaResource.E00137_PYON2);
		E00138_TRUMPET = loadSoundResourceFromSD(Vol3OmotyaResource.E00138_TRUMPET);
		E00139_KODAIKO = loadSoundResourceFromSD(Vol3OmotyaResource.E00139_KODAIKO);
		E00140_OODAIKO = loadSoundResourceFromSD(Vol3OmotyaResource.E00140_OODAIKO);
		E00141_GUSBANA = loadSoundResourceFromSD(Vol3OmotyaResource.E00141_GUSBANA);
		E00142_RAJIKON = loadSoundResourceFromSD(Vol3OmotyaResource.E00142_RAJIKON);
		
		E0045_SANAGI = loadSoundResourceFromSD(Vol3OmotyaResource.E0045_SANAGI);
		E0047_SAKURA = loadSoundResourceFromSD(Vol3OmotyaResource.E0047_SAKURA);
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()){
			if (checkContains(sAirplane, 7, 27, 175, 84, pX, pY)&& istouchAirplane) {
				istouchAirplane = false;
				touchAirplane();
			}
			if (checkContains(aniDoor, 8, 32, 128, 207, pX, pY)) {
				touchDoor(currentDoor);
				currentDoor++;
				if (currentDoor==2) {
					currentDoor = 0;
				}
			}
			if (checkContains(aniBear, 20, 44, 166, 168, pX, pY)&&istouchBear) {
				istouchBear = false;
				touchBear();
			}
			if (checkContains(aniMonster, 10, 34, 166, 202, pX, pY)&& istouchMonster) {
				istouchMonster = false;
				touchMonster();
			}
			if (checkContains(sMoon, 16, 9, 110, 100, pX, pY) && istouchMoon) {
				istouchMoon = false;
				touchMoon();
			}
			
			if ((checkContains(sStarLeft, 2, 2, 52, 52, pX, pY)||
					checkContains(sBackground1, 496, 10, 950, 115, pX, pY))&& istouchStarLeft) {
				istouchStarLeft = false;
				isTouchGimmic[2]=false;
				touchStar();
			}
			if (checkContains(sCat, 10, 30, 120, 140, pX, pY)){
				if (istouchCat){
					istouchCat = false;
					touchCat();
				}
			} else
			if (checkContains(aniPrincessGirl, 152, 20, 355, 394, pX, pY)) {
				if (istouchGirl) {
					istouchGirl = false;
					touchGirl(currenGirl);
					currenGirl++;
					if (currenGirl==3) {
						currenGirl = 0;
					}
				}
				
			} else
			// sWalkAll
			if (checkContains(sWalkAll, 139, 30, 222, 274, pX, pY)&&istouchWalk[0]) {
				istouchWalk[0] = false;
				Log.i(TAG, "blue 1: ");
				touchWalk(sPurple1,E00138_TRUMPET,0);
			} else
			if (checkContains(sWalkAll, 330, 30, 440, 274, pX, pY)&&istouchWalk[1]) {
				istouchWalk[1] = false;
				Log.i(TAG, "yellow 1: ");
				touchWalk(sYellow1,E00140_OODAIKO,1);
			} else
			if (checkContains(sWalkAll, 510, 30, 618, 274, pX, pY)&&istouchWalk[2]) {
				istouchWalk[2] = false;
				Log.i(TAG, "sOrange 1: ");
				touchWalk(sOrange1,E00139_KODAIKO,2);
			} else
			if (checkContains(sWalkAll, 766, 30, 846, 274, pX, pY)&&istouchWalk[3]) {
				istouchWalk[3] = false;
				Log.i(TAG, "blue 2: ");
				touchWalk(sPurple2,E00138_TRUMPET,3);
			} else
			if (checkContains(sWalkAll, 956, 30, 1076, 274, pX, pY)&&istouchWalk[4]) {
				istouchWalk[4] = false;
				Log.i(TAG, "yellow 2: ");
				touchWalk(sYellow2,E00140_OODAIKO,4);
			} else
			if (checkContains(sWalkAll, 1134, 30, 1248, 274, pX, pY)&&istouchWalk[5]) {
				istouchWalk[5] = false;
				Log.i(TAG, "sOrange 2: ");
				touchWalk(sOrange2,E00139_KODAIKO,5);
			} else
			
			// sWalkLoop
			if (checkContains(sWalkLoop, 139, 30, 222, 274, pX, pY)&&istouchWalk[6]) {
				istouchWalk[6] = false;
				Log.i(TAG, "blue 3: ");
				touchWalk(sPurple3,E00138_TRUMPET,6);
			}else
			if (checkContains(sWalkLoop, 330, 30, 440, 274, pX, pY)&&istouchWalk[7]) {
				istouchWalk[7] = false;
				Log.i(TAG, "yellow 3: ");
				touchWalk(sYellow3,E00140_OODAIKO,7);
			} else
			if (checkContains(sWalkLoop, 510, 30, 618, 274, pX, pY)&&istouchWalk[8]) {
				istouchWalk[8] = false;
				Log.i(TAG, "sOrange 3: ");
				touchWalk(sOrange3,E00139_KODAIKO,8);
			}else
			if (checkContains(sWalkLoop, 766, 30, 846, 274, pX, pY)&&istouchWalk[9]) {
				istouchWalk[9] = false;
				Log.i(TAG, "blue 4: ");
				touchWalk(sPurple4,E00138_TRUMPET,9);
			} else
			if (checkContains(sWalkLoop, 956, 30, 1076, 274, pX, pY)&&istouchWalk[10]) {
				istouchWalk[10] = false;
				Log.i(TAG, "yellow 4: ");
				touchWalk(sYellow4,E00140_OODAIKO,10);
			} else
			if (checkContains(sWalkLoop, 1134, 30, 1248, 274, pX, pY)&&istouchWalk[11]) {
				istouchWalk[11] = false;
				Log.i(TAG, "sOrange 4: ");
				touchWalk(sOrange4,E00139_KODAIKO,11);
			}
			
		}
		
		return false;
	}
	private void touchWalk(final Sprite sprWalk, final Sound sudWalk, final int id){
		sprWalk.setVisible(true);
		sudWalk.play();
		timerWalk.schedule(new TimerTask() {
			
			@Override
			public void run() {
				sprWalk.setVisible(false);
				istouchWalk[id] = true;
			}
		}, 2000);
		return;
	}
	private void moveWalk() {
		aniWalk.animate(600, -1);
		aniWalkTow.animate(600, -1);
		aniWalkLoop1.animate(600, -1);
		aniWalkLoop2.animate(600, -1);
		sWalkAll.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveXModifier(28.0f, 0, -1260),
						new MoveXModifier(28.0f, 1260, 0)
						)));
		sWalkLoop.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveXModifier(28.0f, 1260, 0),
						new MoveXModifier(28.0f, 0, -1260)
						
						)));
	
	}
	private void touchGirl(final int index) {
		sPrince.setRotationCenter(150, 227);
		E0047_SAKURA.play();
		sPrince.registerEntityModifier(new RotationModifier(0.2f, 0, -10, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				istouchGirl=true;
				if (index==0) {
					aniPrincessGirl.setCurrentTileIndex(1);
				} else if (index ==1) {
					aniPrincessGirl.setCurrentTileIndex(2);
				} else if (index ==2) {
					aniPrincessGirl.setCurrentTileIndex(0);
				}
				sPrince.setRotation(0);
			}
		}));
		return;
	}
	private void touchStar() {
		E00134_SYARARAN.play();
		if (currentStar==0) {
			sStarLeft.registerEntityModifier(new MoveModifier(1.5f, sStarLeft.getX(), 
					sStarLeft.getX() + 300, sStarLeft.getY(), sStarLeft.getY()+ 350, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sStarLeft.setPosition(547, 27);
					istouchStarLeft=true;
					isTouchGimmic[2]=true;
				}
			}));
			currentStar = 1;
		} else if (currentStar==1) {
			sStarLeft.registerEntityModifier(new MoveModifier(1.5f, sStarLeft.getX(), 
					sStarLeft.getX()+ 300, sStarLeft.getY(), sStarLeft.getY()+ 350, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sStarLeft.setPosition(547, 27);
					istouchStarLeft=true;
					isTouchGimmic[2]=true;
				}
			}));
			currentStar=2;
		} else if (currentStar == 2) {
			
			sStarBig.registerEntityModifier(new MoveModifier(1.5f, 
				sStarBig.getX(), sStarBig.getX() + 200, sStarBig.getY(), sStarBig.getY() + 980,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sStarBig.setPosition(50, -800);
						istouchStarLeft=true;
						isTouchGimmic[2]=true;
					}
				}));
			currentStar=0;
		}
	}
	private void touchMoon(){
		E0045_SANAGI.play();
		sMoon.registerEntityModifier(new ParallelEntityModifier(
				new SequenceEntityModifier(
						new DelayModifier(0.2f),
						new RotationAtModifier(0.4f, 0, -30, 74,12),
						new RotationAtModifier(0.4f, -30, 5,74,12),
						new DelayModifier(0.2f),
						new RotationAtModifier(0.2f, 5, 0, 74, 12)),
				new  SequenceEntityModifier(
						new MoveXModifier(0.6f, sMoon.getX(), sMoon.getX()+ 40),
						new MoveModifier(0.2f,sMoon.getX()+40,sMoon.getX()+45, sMoon.getY(), sMoon.getY()+10),
						new MoveModifier(0.6f, sMoon.getX()+45, sMoon.getX(),
								sMoon.getY()+10, sMoon.getY(),new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sMoon.setRotation(0);
						istouchMoon = true;
						
					}
				}))));
		
		return;
	}
	private void touchCat() {
		E00137_PYON2.play();
		sCat.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(0.3f, sCat.getX(), sCat.getX()-10, sCat.getY(), sCat.getY()-70),
				new MoveModifier(0.3f, sCat.getX()-10, sCat.getX(), sCat.getY()-70, sCat.getY(), 
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						istouchCat = true;
					}
				})));
		return;
	}
	private void touchMonster() {
		long pDurations[]=new long[]{200,1400};
		int pFreame[]=new int[]{1,2};
		E00141_GUSBANA.play();
		aniMonster.animate(pDurations, pFreame, 0, this);
		timerFire.schedule(new TimerTask() {
			
			@Override
			public void run() {
				moveFire();
			}
		}, 200);
		return;
	}
	private void moveFire() {
		sFire.setVisible(true);
		sFire.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(0.2f, sFire.getX(), sFire.getX()-20, sFire.getY(), sFire.getY()-20),
				new MoveModifier(0.2f, sFire.getX()-20, sFire.getX(), sFire.getY()-20, sFire.getY()),
				new MoveModifier(0.2f, sFire.getX(), sFire.getX()-20, sFire.getY(), sFire.getY()-20),
				new MoveModifier(0.2f, sFire.getX()-20, sFire.getX(), sFire.getY()-20, sFire.getY()),
				new MoveModifier(0.2f, sFire.getX(), sFire.getX()-20, sFire.getY(), sFire.getY()-20),
				new MoveModifier(0.2f, sFire.getX()-20, sFire.getX(), sFire.getY()-20, sFire.getY())
//				new MoveModifier(0.4f, sFire.getX(), sFire.getX()-20, sFire.getY(), sFire.getY()-20),
//				new MoveModifier(0.4f, sFire.getX()-20, sFire.getX(), sFire.getY()-20, sFire.getY())
				));
		return;
	}
	private void touchAirplane() {
		E00142_RAJIKON.play();
		sAirplane.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.5f, sAirplane.getX(), -200), new DelayModifier(0.5f),
				new MoveXModifier(1.5f, 1150, sAirplane.getX(), new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						istouchAirplane = true;
					}
				})));
		return;
	}
	private void touchBear() {
		sZZZ.setVisible(false);
		E0006_TRIANGLE.play();
		aniBear.setCurrentTileIndex(1);
		mEngine.registerUpdateHandler(new TimerHandler(5.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sZZZ.setVisible(true);
				aniBear.setCurrentTileIndex(0);
				istouchBear = true;
			}
		}));
		return;
	}
	private void touchDoor(int index) {
		if (index == 0) {
			E00135_GATYA.play();
			aniDoor.setCurrentTileIndex(1);
			sBoy.setVisible(true);
		}else if (index == 1) {
			E00136_BATAN.play();
			aniDoor.setCurrentTileIndex(0);
			sBoy.setVisible(false);
		}
	}
	private void bearSleep() {
		sZZZ.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveModifier(0.8f, sZZZ.getX(), sZZZ.getX()+20, sZZZ.getY(), sZZZ.getY()-20),
				new MoveModifier(0.8f, sZZZ.getX()+ 20, sZZZ.getX(), sZZZ.getY()- 20, sZZZ.getY()))));
	}
	private synchronized void resetData() {
		istouchAirplane = true;
		istouchBear = true;
		istouchMonster= true;
		istouchMoon = true;
		istouchCat = true;
		istouchStarLeft = true;
		istouchGirl = true;
		isTouchGimmic[2]=true;
		currentDoor=0;
		currentStar =0;
		currenGirl=0;
		if (aniMonster != null) {
			sFire.setVisible(false);
			sFire.clearEntityModifiers();
			sFire.setPosition(222, 138);
			aniMonster.setCurrentTileIndex(0);
		}
		if (aniPrincessGirl != null) {
			sPrince.clearEntityModifiers();
			sPrince.setRotation(0);
			aniPrincessGirl.setCurrentTileIndex(0);
		}
		if (sAirplane != null) {
			sAirplane.clearEntityModifiers();
			sAirplane.setPosition(170, 35);
		}
		if (sMoon != null) {
			sMoon.clearEntityModifiers();
			sMoon.setRotation(0);
			sMoon.setPosition(380, 35);
		}
		if (sStarLeft!=null) {
			sStarLeft.clearEntityModifiers();
			sStarLeft.setPosition(547, 27);
			sStarBig.clearEntityModifiers();
			sStarBig.setPosition(50, -800);
		}
		if (aniDoor != null) {
			aniDoor.setCurrentTileIndex(0);
			aniDoor.setPosition(78, 106);
			sBoy.setVisible(false);
		}
		for (int i = 0; i < istouchWalk.length; i++) {
			istouchWalk[i] = true;
		}
		
		return;
	}
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		resetData();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		for (int i = 0; i < istouchWalk.length; i++) {
			istouchWalk[i] = true;
		}
		bearSleep();
		moveWalk();
		super.onResumeGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, "onResumeGame: ");
		super.loadKaraokeComplete();
	}
	
	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimmic[2]) {
			isTouchGimmic[2] = false;
			istouchStarLeft = false;
			touchStar();
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimateSprite) {
		if (pAnimateSprite.equals(aniMonster)) {
			sFire.setVisible(false);
			sFire.clearEntityModifiers();
			aniMonster.setCurrentTileIndex(0);
			istouchMonster = true;
			
		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
	}
}
