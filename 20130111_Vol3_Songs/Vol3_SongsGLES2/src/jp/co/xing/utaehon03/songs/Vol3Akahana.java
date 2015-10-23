/* Vol3Akahana.java
* Create on Oct 19, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AkahanaResource;

public class Vol3Akahana extends BaseGameActivity implements IAnimationListener, IOnSceneTouchListener {
	public final static String TAG = " Vol3Akahana "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpAkahana1, ttpAkahana2, ttpAkahana3 ;
	private TexturePackTextureRegionLibrary ttpLibakAhana1, ttpLibakAhana3;
	
	private TextureRegion trBackground1;
	private Sprite sBackground1;
	private TextureRegion trPresent[]= new TextureRegion[5];
	private Sprite sPresent[] = new Sprite[5];
	private TiledTextureRegion ttrBackground2, ttrHouse, ttrCaribou, ttrCaribouEnd, ttrOldMan;
	private TiledTextureRegion ttrNose3[] = new TiledTextureRegion[12];
	private TiledTextureRegion ttrNose2[] = new TiledTextureRegion[8];
	private TiledTextureRegion ttrNose1[] = new TiledTextureRegion[6];
	private TiledTextureRegion ttrColor[] = new TiledTextureRegion[6];
	private AnimatedSprite aniBackground2, aniHouse, aniOldMan1, aniOldMan2, aniOldMan3;
	private AnimatedSprite aniCaribou1[]= new AnimatedSprite[6];
	private AnimatedSprite aniCaribou2[]= new AnimatedSprite[8];
	
	private AnimalAniSprite aniCaribou3[]= new AnimalAniSprite[12];
	private AnimalAniSprite aniNose1[] = new AnimalAniSprite[6];
	private AnimalAniSprite aniNose2[] = new AnimalAniSprite[8];
	private AnimalAniSprite aniNose3[] = new AnimalAniSprite[12];
	private Rectangle recCaribou1, recCaribou2, recCaribou3;
	//toa do tuan loc
	private float pointCaribou1[][] = {{165,86},{367,96},{565,106},{165,259},{367,269},{565,279}};
	private float pointCaribou2[][] = {{104,90},{306,100},{504,110},{706,120},{104,259},{306,269},{504,279},{706,289}};
	private float pointCaribou3[][]={{108,22},{294,32},{493,42},{694, 52},{108,169},{294,179},{493,189},{694, 199},
			{108,310},{294,320},{493,330},{694, 340}};
	//toa do cua mui
	private float pointNose3[][]={{48,0},{237,10},{432,20},{636,30},{48,142},{237,152},
			{432,162},{636,172},{48,293},{237,303},{432,313},{636,323}};
	private float pointNose2[][]={{47,62},{253,72},{454,82},{652,92},{47,243},{253,253},
			{454,263},{652,273}};
	private float pointNose1[][]={{112,62},{324,72},{512,82},{112,230},{324,240},{512,250}};
	//toa do cua present
	private float pointPresent[][] = {{26, 171},{192, 315},{377, 429},{603, 345},{764, 194}};
	
	private float arrPointTapNose[][]={{2,18,34,74,85,123,114,166,166,91,74,34,12,2},
			{33,32,47,50,12,8,74,88,132,132,99,93,66,59}};
	private float arrPointMan[][]={{50, 257, 383, 601, 656, 634, 599, 522, 474, 460, 180, 109, 90, 50},
			{325, 325, 103, 57, 108, 238, 288, 329, 452, 503, 497, 402, 394, 394}};
	private float arrPointMan3[][]={{40, 257, 383, 601, 656, 634, 599, 522, 474, 460, 180, 139, 153, 40},
			{338, 338, 118, 69, 120, 248, 298, 339, 462, 523, 507, 462, 399, 399}};
	private ArrayList<Integer> arltempNose3, arlNosePoor3, arlCheckCaribou3;
	private ArrayList<Integer> arltempNose2, arlNosePoor2, arlCheckCaribou2;
	private ArrayList<Integer> arltempNose1, arlNosePoor1, arlCheckCaribou1;
	private ArrayList<Integer> arlTempRandom1, arlTempRandom2, arlTempRandom3;
	
	
	private boolean ischeckBusy1 = false, ischeckGame;
	private boolean ischeckBusy2 = false;
	private boolean ischeckBusy3 = false;
	private boolean checktouchCaribou1 = true;
	private boolean checktouchCaribou2 = true;
	private boolean checktouchCaribou3 = true;
	private boolean checkGame3 = false;
	private boolean checkGame2 = false;
	private boolean checkGame1 = false;
	private boolean touchMan1 = true;
	private boolean touchMan2 = true;
	private boolean touchMan3 = true;
	private boolean checkMan1 = false;
	private boolean checkMan2 = false;
	private boolean checkMan3 = false;
	private boolean isTouchCaribou3[]= new boolean[12];
	private boolean isTouchCaribou2[]= new boolean[8];
	private boolean isTouchCaribou1[]= new boolean[6];
	private int currentOpen3= -1;
	private int currentOpen1= -1;
	private int currentOpen2= -1;
	private int countOpenPoor3 = 0;
	private int countOpenPoor2 = 0;
	private int countOpenPoor1 = 0;
	int count =0;
	TimerHandler timeCorrectNose3, timeNose3;
	private Sound A82_1_3_GATYA, A82_4_OMEDETOU, A82_5_KIRAN, A82_5_PINPON, A82_7_NAKANAKA,
	 			A82_7_ONAJIHANA, A82_7_STUGIHA, A82_TONAKAI;


	private void initial(){
		ischeckBusy1 = false;
		ischeckBusy2 = false;
		ischeckBusy3 = false;
		checkGame2 = false;
		checkGame1 = false;
		checkGame3 = false;
		touchMan1 = true;
		touchMan2 = true;
		touchMan3 = true;
		checktouchCaribou1 = true;
		checktouchCaribou2 = true;
		checktouchCaribou3 = true;
		currentOpen3= -1;
		currentOpen1= -1;
		currentOpen2= -1;
		countOpenPoor3 = 0;
		countOpenPoor2 = 0;
		countOpenPoor1 = 0;
		for (int i = 0; i < isTouchCaribou1.length; i++) {
			isTouchCaribou1[i] = true;
		}
		for (int i = 0; i < isTouchCaribou2.length; i++) {
			isTouchCaribou2[i] = true;
		}
		for (int i = 0; i < isTouchCaribou3.length; i++) {
			isTouchCaribou3[i]=true;
		}
		for (int i = 0; i < isTouchCaribou2.length; i++) {
			isTouchCaribou2[i]=true;
		}
		for (int i = 0; i < isTouchCaribou1.length; i++) {
			isTouchCaribou1[i]=true;
		}
		if (arlCheckCaribou3 != null) {
			arlCheckCaribou3.clear();
			arlCheckCaribou2.clear();
			arlCheckCaribou1.clear();
		}
	}
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("akahana/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("akahana/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "akahana/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources ");
		ttpAkahana1 = mTexturePackLoaderFromSource.load("akahana1.xml");
		ttpAkahana1.loadTexture();
		ttpLibakAhana1 = ttpAkahana1.getTexturePackTextureRegionLibrary();
		
		ttpAkahana2 = mTexturePackLoaderFromSource.load("akahana2.xml");
		ttpAkahana2.loadTexture();
		
		ttpAkahana3 = mTexturePackLoaderFromSource.load("akahana3.xml");
		ttpAkahana3.loadTexture();
		ttpLibakAhana3 = ttpAkahana3.getTexturePackTextureRegionLibrary();
		
		//--------------------------------
		ttrColor[0] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSEVIOLET);
		ttrColor[1] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSEPINK);
		ttrColor[2] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSEGREEN);
		ttrColor[3] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSEYELLOW);
		ttrColor[4] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSEBLUE);
		ttrColor[5] = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.PACK_NOSERED);
		
		trBackground1 = ttpLibakAhana3.get(Vol3AkahanaResource.A82_1_1_IPHONE_ID);
		
		ttrBackground2 = getTiledTextureFromPacker(ttpAkahana3, Vol3AkahanaResource.A82_1_2_1_IPHONE_ID,
				Vol3AkahanaResource.A82_1_2_2_IPHONE_ID);
		
		ttrHouse=getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.A82_1_3_1_IPHONE_ID,
				Vol3AkahanaResource.A82_1_3_2_IPHONE_ID);
		
		ttrCaribou = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.A82_2_1_IPHONE_ID,
				Vol3AkahanaResource.A82_2_3_IPHONE_ID,Vol3AkahanaResource.A82_2_2_IPHONE_ID,
				Vol3AkahanaResource.A82_2_4_IPHONE_ID);
		ttrCaribouEnd = getTiledTextureFromPacker(ttpAkahana1, Vol3AkahanaResource.A82_6_1_IPHONE_ID,
				Vol3AkahanaResource.A82_6_2_IPHONE_ID,Vol3AkahanaResource.A82_6_3_IPHONE_ID,
				Vol3AkahanaResource.A82_6_4_IPHONE_ID);
		
		ttrOldMan = getTiledTextureFromPacker(ttpAkahana2, Vol3AkahanaResource.A82_7_1_IPHONE_ID,
				Vol3AkahanaResource.A82_7_2_IPHONE_ID,Vol3AkahanaResource.A82_7_3_IPHONE_ID,
				Vol3AkahanaResource.A82_7_4_IPHONE_ID);
		arlCheckCaribou1 = new ArrayList<Integer>();
		arlCheckCaribou2 = new ArrayList<Integer>();		
		arlCheckCaribou3 = new ArrayList<Integer>();
		arltempNose1 = new ArrayList<Integer>();
		arltempNose2 = new ArrayList<Integer>();
		arltempNose3 = new ArrayList<Integer>();
		
		for (int k=0; k<6; k++) {
			arltempNose1.add(k);
		}
		for (int k=0; k<8; k++) {
			arltempNose2.add(k);
		}
		for (int k=0; k<12; k++) {
			arltempNose3.add(k);
		}
		//----------------------------------------------
		trPresent[0] = ttpLibakAhana1.get(Vol3AkahanaResource.A82_4_1_IPHONE_ID);
		trPresent[1] = ttpLibakAhana1.get(Vol3AkahanaResource.A82_4_2_IPHONE_ID);
		trPresent[2] = ttpLibakAhana1.get(Vol3AkahanaResource.A82_4_3_IPHONE_ID);
		trPresent[3] = ttpLibakAhana1.get(Vol3AkahanaResource.A82_4_4_IPHONE_ID);
		trPresent[4] = ttpLibakAhana1.get(Vol3AkahanaResource.A82_4_5_IPHONE_ID);
	}

	
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		sBackground1 = new Sprite(0, 0, trBackground1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground1);
		aniBackground2 = new AnimatedSprite(0, 0, ttrBackground2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBackground2);
		aniBackground2.animate(600, -1);
		aniHouse = new AnimatedSprite(0, 507, ttrHouse, this.getVertexBufferObjectManager());
		mScene.attachChild(aniHouse);
		recCaribou1 = new Rectangle(960, 0, 960, 640, this.getVertexBufferObjectManager());
		recCaribou1.setColor(Color.TRANSPARENT);
		mScene.attachChild(recCaribou1);
		
		aniOldMan1 = new AnimatedSprite(668, 20, ttrOldMan, this.getVertexBufferObjectManager());
		recCaribou1.attachChild(aniOldMan1);
		for (int i = 0; i < aniCaribou1.length; i++) {
			if (i==2||i==5) {
				aniCaribou1[i] = new AnimatedSprite(pointCaribou1[i][0], pointCaribou1[i][1], 
						ttrCaribouEnd, this.getVertexBufferObjectManager());
				recCaribou1.attachChild(aniCaribou1[i]);
			}else{
			
				aniCaribou1[i] = new AnimatedSprite(pointCaribou1[i][0], pointCaribou1[i][1], 
						ttrCaribou, this.getVertexBufferObjectManager());
				recCaribou1.attachChild(aniCaribou1[i]);
			}
		}
		
		recCaribou2 = new Rectangle(990, 20, 960, 640, this.getVertexBufferObjectManager());
		recCaribou2.setColor(Color.TRANSPARENT);
		mScene.attachChild(recCaribou2);
		aniOldMan2 = new AnimatedSprite(800, 30, ttrOldMan, this.getVertexBufferObjectManager());
		recCaribou2.attachChild(aniOldMan2);
		
		for (int i = 0; i < aniCaribou2.length; i++) {
			if(i==3||i==7) {
				aniCaribou2[i] = new AnimatedSprite(pointCaribou2[i][0], pointCaribou2[i][1], 
						ttrCaribouEnd, this.getVertexBufferObjectManager());
				recCaribou2.attachChild(aniCaribou2[i]);
			} else {
				aniCaribou2[i] = new AnimatedSprite(pointCaribou2[i][0], pointCaribou2[i][1], 
						ttrCaribou, this.getVertexBufferObjectManager());
				recCaribou2.attachChild(aniCaribou2[i]);
			}
		}
		
		recCaribou3 = new Rectangle(990, 10, 960, 640, this.getVertexBufferObjectManager());
		recCaribou3.setColor(Color.TRANSPARENT);
		mScene.attachChild(recCaribou3);
		aniOldMan3 = new AnimatedSprite(800, -70, ttrOldMan, this.getVertexBufferObjectManager());
		recCaribou3.attachChild(aniOldMan3);
		
		for (int i = 0; i < aniCaribou3.length; i++) {
			
			if(i==3||i==7||i==11) {
				aniCaribou3[i]=new AnimalAniSprite(pointCaribou3[i][0], pointCaribou3[i][1], 
						ttrCaribouEnd, this.getVertexBufferObjectManager());
				recCaribou3.attachChild(aniCaribou3[i]);
			} else {
				aniCaribou3[i] = new AnimalAniSprite(pointCaribou3[i][0], pointCaribou3[i][1], 
						ttrCaribou, this.getVertexBufferObjectManager());
				recCaribou3.attachChild(aniCaribou3[i]);
			}
			
		}
		for (int i = 0; i < sPresent.length; i++) {
			sPresent[i] = new Sprite(690,30, trPresent[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sPresent[i]);
			sPresent[i].setScale(0.2f);
			sPresent[i].setVisible(false);
			
		}
		
		Log.d(TAG, "loadKaraokeScene end ");
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

	}
	
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContainsPolygon(aniOldMan1, arrPointMan, 13, pX, pY) && touchMan1 && checkMan1) {
				touchMan1 = false;
				A82_7_ONAJIHANA.play();
				mScene.registerUpdateHandler(new TimerHandler(2.8f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						touchMan1 = true;
					}
				}));
			}
			if (checkContainsPolygon(aniOldMan2, arrPointMan, 13, pX, pY) && touchMan2 && checkMan2) {
				A82_7_ONAJIHANA.play();
				touchMan2 = false;
				mScene.registerUpdateHandler(new TimerHandler(2.8f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						touchMan2 = true;
					}
				}));
			}
			if (checkContainsPolygon(aniOldMan3, arrPointMan3, 13, pX, pY) && touchMan3 && checkMan3) {
				A82_7_ONAJIHANA.play();
				touchMan3 = false;
				mScene.registerUpdateHandler(new TimerHandler(2.8f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						touchMan3 = true;
					}
				}));
			}
			if (checkContains(aniHouse, 2, 23, 957, 130, pX, pY)) {
				A82_1_3_GATYA.play();
				if (aniHouse.getCurrentTileIndex()==0) {
					aniHouse.setCurrentTileIndex(1);
				} else {
					aniHouse.setCurrentTileIndex(0);
				}
			
			}
			// ----------------------- game 1 ----------------------
			for (int i = 0; i < aniCaribou1.length; i++) {
				if (checkContainsPolygon(aniCaribou1[i], arrPointTapNose, 13, pX, pY)
						&& isTouchCaribou1[i]&& !ischeckBusy1&& checkGame1&& checktouchCaribou1 ) {
					Log.d(TAG, "co vao day khong aniNose1: ");
					Log.d(TAG, "gia tri i: " +i);
					checktouchCaribou1 = false;
					isTouchCaribou1[i]=false;
					aniCaribou1[i].setCurrentTileIndex(2);
					mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							checktouchCaribou1 = true;
						}
					}));
					arlCheckCaribou1.add(i);
					touchCaribou1(arltempNose1.get(i));
				}
				
			}
			// --------------------- game 2 -------------------
			for (int i = 0; i < aniCaribou2.length; i++) {
				if (checkContainsPolygon(aniCaribou2[i], arrPointTapNose, 13, pX, pY)
						&&isTouchCaribou2[i]&& !ischeckBusy2&& checkGame2&&checktouchCaribou2) {
					Log.e(TAG, "co vao day khong aniNose2: ");
					checktouchCaribou2 = false;
					isTouchCaribou2[i] = false;
					mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							checktouchCaribou2 = true;
						}
					}));
					aniCaribou2[i].setCurrentTileIndex(2);
					arlCheckCaribou2.add(i);	
					touchCaribou2(arltempNose2.get(i));
				}

			}
			// ------------------- game 3 -------------------
			for (int i = 0; i < aniCaribou3.length; i++) {
				if (checkContains(aniCaribou3[i], 8, 28, 170, 158, pX, pY)
						&&isTouchCaribou3[i]&&!ischeckBusy3&& checkGame3&& checktouchCaribou3 ) {
					Log.d(TAG, "co vao day khong: ");
					
					checktouchCaribou3 = false;
					isTouchCaribou3[i]=false;
					mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							checktouchCaribou3 = true;
						}
					}));
					aniCaribou3[i].setCurrentTileIndex(2);
					arlCheckCaribou3.add(i);
					touchCaribou3(arltempNose3.get(i));
				}
				
			}
			
		}
		return false;
	}
	// ----------------------game 1----------------------------
	public void touchCaribou1(final int index) {
		
		A82_5_KIRAN.play();
		aniNose1[index].setVisible(true);
		
		if (currentOpen1!=-1) {
			if(aniNose1[index].getImgIndex()!=aniNose1[currentOpen1].getImgIndex()) {
				ischeckBusy1 = true;
				mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						aniNose1[index].setVisible(false);
						aniNose1[currentOpen1].setVisible(false);
						if (arlCheckCaribou1.size()%2==0&&arlCheckCaribou1.size()>1) {
							aniCaribou1[arlCheckCaribou1.get(arlCheckCaribou1.size()-1)].setCurrentTileIndex(0);
							aniCaribou1[arlCheckCaribou1.get(arlCheckCaribou1.size()-2)].setCurrentTileIndex(0);
							isTouchCaribou1[arlCheckCaribou1.get(arlCheckCaribou1.size()-1)]=true;
							isTouchCaribou1[arlCheckCaribou1.get(arlCheckCaribou1.size()-2)]=true;
							arlCheckCaribou1.clear();
						}
						ischeckBusy1 = false;
						currentOpen1 = -1;
					}
				}));
			}else {
				
				mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						A82_5_PINPON.play();
						aniNose1[index].setCurrentTileIndex(1);
						aniNose1[currentOpen1].setCurrentTileIndex(1);
						currentOpen1 = -1;
						countOpenPoor1++;
						if (countOpenPoor1==3){
							countOpenPoor1 = 0;
							movebackCaribou1();
						}
					}
				}));
			}
		}else {
			currentOpen1 = index;
		}
	}
	private void movebackCaribou1() {
		checkGame1 = false;
		checkMan1 = false;
		A82_7_ONAJIHANA.stop();
		if (!arlCheckCaribou1.isEmpty()){
			arlCheckCaribou1.clear();
		}
		
		
		A82_TONAKAI.play();
		A82_7_NAKANAKA.play();
		recCaribou1.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(1.0f), new MoveXModifier(4.6f, recCaribou1.getX(), -1490,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						for (int i = 0; i < aniNose1.length; i++) {
							aniNose1[i].setVisible(false);
							aniNose1[i].setCurrentTileIndex(0);
						}
						aniOldMan1.animate(new long[]{400,400}, new int[]{0,1},-1);
						for (int i = 0; i < aniCaribou1.length; i++) {
							aniCaribou1[i].animate(new long[]{400,400},new int[]{2,3},-1);
						}
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						
						loadRandomNose2();
						moveComeCaribou2();
						aniOldMan1.stopAnimation(0);
						for (int i = 0; i < aniCaribou1.length; i++) {
							aniCaribou1[i].stopAnimation(0);
						}
						recCaribou1.setPosition(recCaribou1.getmXFirst(), recCaribou1.getmYFirst());
						aniOldMan1.stopAnimation(0);
					}
				})));
	}
	//-----------------------------game 2----------------------
	private void touchCaribou2(final int index) {
		A82_5_KIRAN.play();
		aniNose2[index].setVisible(true);
		if (currentOpen2 !=-1) {
			if (aniNose2[index].getImgIndex()!= aniNose2[currentOpen2].getImgIndex()) {
				ischeckBusy2 = true;
				mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						aniNose2[index].setVisible(false);
						aniNose2[currentOpen2].setVisible(false);
						if (arlCheckCaribou2.size()%2==0&&arlCheckCaribou2.size()>1) {
							aniCaribou2[arlCheckCaribou2.get(arlCheckCaribou2.size()-1)].setCurrentTileIndex(0);
							aniCaribou2[arlCheckCaribou2.get(arlCheckCaribou2.size()-2)].setCurrentTileIndex(0);
							isTouchCaribou2[arlCheckCaribou2.get(arlCheckCaribou2.size()-1)]=true;
							isTouchCaribou2[arlCheckCaribou2.get(arlCheckCaribou2.size()-2)]=true;
							arlCheckCaribou2.clear();
						}
						ischeckBusy2 = false;
						currentOpen2 = -1;
					}
				}));
			} else {
				mScene.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						A82_5_PINPON.play();
						aniNose2[index].setCurrentTileIndex(1);
						aniNose2[currentOpen2].setCurrentTileIndex(1);
						currentOpen2 = -1;
						countOpenPoor2++;
						if (countOpenPoor2==4){
							countOpenPoor2 = 0;
							movebackCaribou2();
						}
					}
				}));
			}
		}else {
			currentOpen2 = index;
		}
	}
	
	private void movebackCaribou2() {
		checkGame2 = false;
		checkMan2 = false;
		A82_7_ONAJIHANA.stop();
		if (arlCheckCaribou2.size()%2==0) {
			arlCheckCaribou2.clear();
		}
		
		A82_TONAKAI.play();
		A82_7_STUGIHA.play();
		recCaribou2.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(1.0f), new MoveXModifier(4.6f, recCaribou2.getX(), -1490,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						for (int i = 0; i < aniNose2.length; i++) {
							aniNose2[i].setVisible(false);
							aniNose2[i].setCurrentTileIndex(0);
						}
						for (int i = 0; i < aniCaribou2.length; i++) {
							aniCaribou2[i].animate(new long[]{400,400},new int[]{2,3},-1);
						}
						aniOldMan2.animate(new long[]{400,400}, new int[]{0,1},-1);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						loadRandomNose3();
						moveComeCaribou3();
						aniOldMan2.stopAnimation(0);
						for (int i = 0; i < aniCaribou2.length; i++) {
							aniCaribou2[i].stopAnimation(0);
						}
						recCaribou2.setPosition(recCaribou2.getmXFirst(), recCaribou2.getmYFirst());
						aniOldMan2.stopAnimation(0);
					}
				})));
	}
	//----------------------------game 3------------------------
	public void touchCaribou3(final int index){
		Log.d(TAG, "gia tri index: " + index);
		A82_5_KIRAN.play();
		aniNose3[index].setVisible(true);
		if (currentOpen3 != -1) {
			//compare two nose
			if(aniNose3[index].getImgIndex()!=aniNose3[currentOpen3].getImgIndex()) {
				// neu 2 mui khac nhau
				ischeckBusy3 = true;
				timeNose3 = new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						aniNose3[index].setVisible(false);
						aniNose3[currentOpen3].setVisible(false);
						if (arlCheckCaribou3.size()%2==0&&arlCheckCaribou3.size()>1) {
							Log.d(TAG, "gia tri cua phan tu i arlCheckCaribou3 : " +arlCheckCaribou3.get(arlCheckCaribou3.size()-1));
							aniCaribou3[arlCheckCaribou3.get(arlCheckCaribou3.size()-2)].setCurrentTileIndex(0);
							aniCaribou3[arlCheckCaribou3.get(arlCheckCaribou3.size()-1)].setCurrentTileIndex(0);
							isTouchCaribou3[arlCheckCaribou3.get(arlCheckCaribou3.size()-1)]=true;
							isTouchCaribou3[arlCheckCaribou3.get(arlCheckCaribou3.size()-2)]=true;
							arlCheckCaribou3.clear();
						}
						ischeckBusy3 = false;
						currentOpen3 = -1;
						
					}
				});
				mScene.registerUpdateHandler(timeNose3);
			}else{
				//neu dung
				// neu countOpenPoor3=6 thi chien thang
				timeCorrectNose3 = new TimerHandler(0.4f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						A82_5_PINPON.play();
						aniNose3[index].setCurrentTileIndex(1);
						aniNose3[currentOpen3].setCurrentTileIndex(1);
						currentOpen3 = -1;
						countOpenPoor3++;
						
						if (countOpenPoor3==6){
							countOpenPoor3 = 0;
							arlCheckCaribou3.clear();
						// goi ham chien thang
							moveWin3();
						}
					}
				});
				mScene.registerUpdateHandler(timeCorrectNose3);
			}
		}else {
			currentOpen3 = index;
		}

		
	}
	
	public void moveWin3(){
		checkGame3 =false;
		checkMan3 = false;
		A82_7_ONAJIHANA.stop();
		
		
		recCaribou3.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.9f), new MoveXModifier(1.8f, recCaribou3.getX(), -650, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A82_TONAKAI.play();
						for (int i = 0; i < aniNose3.length; i++) {
							aniNose3[i].setVisible(false);
							aniNose3[i].setCurrentTileIndex(0);
						}
						for (int i = 0; i < aniCaribou3.length; i++) {
							aniCaribou3[i].animate(new long[]{400,400}, new int[]{2,3},-1);
						}
						
						aniOldMan3.animate(new long[]{500,500}, new int[]{2,3},-1);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						A82_4_OMEDETOU.play();
						for (int i = 0; i < aniCaribou3.length; i++) {
							aniCaribou3[i].stopAnimation(2);
						}
						movePresent(0,0.85f,0.95f,0.9f);
						movePresent(1,0.9f,1.05f,0.95f);
						movePresent(2,0.95f,1.15f,1.0f);
						movePresent(3,1.15f,1.25f,1.1f);
						movePresent(4,1.25f,1.5f,1.25f);
						
					}
				})));
		
	}
	public void movePresent(final int index, float time, float duration1, float duration2) {
		sPresent[index].registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(time, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sPresent[index].setVisible(true);
					}
				}),new ParallelEntityModifier( new ScaleModifier(duration1, 0, 1),
				new MoveModifier(duration2, sPresent[index].getmXFirst(),pointPresent[index][0], 
						sPresent[index].getmYFirst(), pointPresent[index][1], new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if(index==4) {
									//ong gia chuyen dong vao
									moveAfterWin();
								}
							}
						}))));
	}
	private void moveAfterWin(){
		recCaribou3.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.9f),
				new MoveXModifier(2.4f, recCaribou3.getX(), -1560, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A82_TONAKAI.play();
						for (int i = 0; i < aniCaribou3.length; i++) {
							aniCaribou3[i].animate(new long[]{400,400}, new int[]{2,3},-1);
						}
						setvisiableSprite(false,sPresent[0],sPresent[1],sPresent[2],sPresent[3],sPresent[4]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						recCaribou3.setPosition(recCaribou3.getmXFirst(),recCaribou3.getmYFirst());
						aniOldMan3.stopAnimation(2);
						loadRandomNose1();
						moveComeCaribou1();
					}
				})));
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		ischeckGame = hasFocus;
		Log.d(TAG, "onWindowFocusChanged: ");
		Log.d(TAG, "ischeckGame: "+ischeckGame);
	}
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		stopSound();
		resetData();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		super.onResumeGame();
		
		initial();
		loadRandomNose1();
		Log.d(TAG, "ischeckGame: "+ischeckGame);
		mEngine.registerUpdateHandler(new TimerHandler(0.2f,true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				ischeckGame = WINDOW_FOCUS_CHANGE;
				if (ischeckGame) {
					ischeckGame = false;
					moveComeCaribou1();
				}
			}
		}));
		
		
	}
	public static ArrayList<Integer> getRandomArray(int input, int iNumbers) {
		if (input < iNumbers) {
			System.out.println("error: lenght array > iNumbers");
			return null;
		} else {
			ArrayList<Integer> alOuput = new ArrayList<Integer>();
			ArrayList<Integer> alInput = new ArrayList<Integer>();
			Random rd = new Random();
			for (int i = 0; i < input; i++) {
				alInput.add(i);
			}
			for (int i = 0; i < iNumbers; i++) {
				int index = rd.nextInt(alInput.size());
				alOuput.add(alInput.get(index));
				alInput.remove(index);
			}
			return alOuput;
		}
	}
	private void loadRandomNose1() {
		
		//lay random
		arlTempRandom1 = new ArrayList<Integer>();
		arlNosePoor1 = new ArrayList<Integer>();
		arlTempRandom1 = getRandomArray(6,3);
		//lay 6 gia tri co 3 gia tri giong nhau
		 for (int i = 0; i < 3; i++) {
			 arlNosePoor1.add(arlTempRandom1.get(i));
			 arlNosePoor1.add(arlTempRandom1.get(i));
		}
		 //get TiledTextureRegion 
		for (int i = 0; i < 6; i++) {
			ttrNose1[i] = ttrColor[arlNosePoor1.get(i)];
		}
		// animate sprite
		for (int i = 0; i < 6; i++) {
			aniNose1[i] = new AnimalAniSprite(pointNose1[i][0], pointNose1[i][1], ttrNose1[i], this.getVertexBufferObjectManager());
			aniNose1[i].setImgIndex(arlNosePoor1.get(i));
			aniNose1[i].setVisible(false);
			recCaribou1.attachChild(aniNose1[i]);
		}
		count=0;
		checkLap1();
	}
	
	private void checkLap1(){
		Collections.shuffle(arltempNose1);
		for (int i = 0; i < 6; i++) {
			aniNose1[arltempNose1.get(i)].setPosition(pointNose1[i][0], pointNose1[i][1]);
		}
		count++;
	
		if (aniNose1[arltempNose1.get(0)].getImgIndex()==aniNose1[arltempNose1.get(1)].getImgIndex()
				||aniNose1[arltempNose1.get(1)].getImgIndex()==aniNose1[arltempNose1.get(2)].getImgIndex()
				||aniNose1[arltempNose1.get(3)].getImgIndex()==aniNose1[arltempNose1.get(4)].getImgIndex()
				||aniNose1[arltempNose1.get(4)].getImgIndex()==aniNose1[arltempNose1.get(5)].getImgIndex()
				||aniNose1[arltempNose1.get(0)].getImgIndex()==aniNose1[arltempNose1.get(3)].getImgIndex()
				||aniNose1[arltempNose1.get(1)].getImgIndex()==aniNose1[arltempNose1.get(4)].getImgIndex()
				||aniNose1[arltempNose1.get(2)].getImgIndex()==aniNose1[arltempNose1.get(5)].getImgIndex()) {
			checkLap1();
		}else {
			Log.e(TAG,  "count lap::: " +count);
			return;
		}
		
	}
	private void loadRandomNose2() {
		arlTempRandom2 = new ArrayList<Integer>();
		arlNosePoor2 = new ArrayList<Integer>();
		arlTempRandom2 = getRandomArray(6, 4);
		
		//lay 8 gia tri co 4 gia tri giong nhau
		 for (int i = 0; i < 4; i++) {
			 arlNosePoor2.add(arlTempRandom2.get(i));
			 arlNosePoor2.add(arlTempRandom2.get(i));
		}
		 //get TiledTextureRegion 
		for (int i = 0; i < 8; i++) {
			ttrNose2[i] = ttrColor[arlNosePoor2.get(i)];
		}
		for (int i = 0; i < 8; i++) {
			aniNose2[i] = new AnimalAniSprite(pointNose2[i][0], pointNose2[i][1], ttrNose2[i], this.getVertexBufferObjectManager());
			aniNose2[i].setImgIndex(arlNosePoor2.get(i));
			recCaribou2.attachChild(aniNose2[i]);
			aniNose2[i].setVisible(false);
		}
		count=0;
		checkLap2();
	}
	private void checkLap2(){
		Collections.shuffle(arltempNose2);
		for (int i = 0; i < 8; i++) {
			aniNose2[arltempNose2.get(i)].setPosition(pointNose2[i][0], pointNose2[i][1]);
		}
		count++;
		
		if (aniNose2[arltempNose2.get(0)].getImgIndex()==aniNose2[arltempNose2.get(1)].getImgIndex()
				||aniNose2[arltempNose2.get(1)].getImgIndex()==aniNose2[arltempNose2.get(2)].getImgIndex()
				||aniNose2[arltempNose2.get(2)].getImgIndex()==aniNose2[arltempNose2.get(3)].getImgIndex()
				||aniNose2[arltempNose2.get(5)].getImgIndex()==aniNose2[arltempNose2.get(6)].getImgIndex()
				||aniNose2[arltempNose2.get(4)].getImgIndex()==aniNose2[arltempNose2.get(5)].getImgIndex()
				||aniNose2[arltempNose2.get(6)].getImgIndex()==aniNose2[arltempNose2.get(7)].getImgIndex()
				||aniNose2[arltempNose2.get(0)].getImgIndex()==aniNose2[arltempNose2.get(4)].getImgIndex()
				||aniNose2[arltempNose2.get(1)].getImgIndex()==aniNose2[arltempNose2.get(5)].getImgIndex()
				||aniNose2[arltempNose2.get(2)].getImgIndex()==aniNose2[arltempNose2.get(6)].getImgIndex()
				||aniNose2[arltempNose2.get(3)].getImgIndex()==aniNose2[arltempNose2.get(7)].getImgIndex()) {
			checkLap2();
		}else {
			Log.e(TAG,  "count lap::: " +count);
			return;
		}
		
	}
	private void loadRandomNose3() {
		arlTempRandom3 = new ArrayList<Integer>();
		arlNosePoor3 = new ArrayList<Integer>();
		arlTempRandom3 = getRandomArray(6, 6);
		for (int k =0; k<6; k++) {
			arlNosePoor3.add(arlTempRandom3.get(k));
			arlNosePoor3.add(arlTempRandom3.get(k));
		}
		 //get TiledTextureRegion 
		for (int i = 0; i < 12; i++) {
			ttrNose3[i] = ttrColor[arlNosePoor3.get(i)];
		}
		
		for (int i = 0; i < pointNose3.length; i++) {
			aniNose3[i] = new AnimalAniSprite(pointNose3[i][0], pointNose3[i][1], ttrNose3[i], this.getVertexBufferObjectManager());
			aniNose3[i].setImgIndex(arlNosePoor3.get(i));
			recCaribou3.attachChild(aniNose3[i]);
			aniNose3[i].setVisible(false);
		}
		count=0;
		checkLap3();
	}
	private void checkLap3(){
		Collections.shuffle(arltempNose3);
		for (int i = 0; i < 12; i++) {
			aniNose3[arltempNose3.get(i)].setPosition(pointNose3[i][0], pointNose3[i][1]);
		}
		count++;
		
		if (aniNose3[arltempNose3.get(0)].getImgIndex()==aniNose3[arltempNose3.get(1)].getImgIndex()
				||aniNose3[arltempNose3.get(1)].getImgIndex()==aniNose3[arltempNose3.get(2)].getImgIndex()
				||aniNose3[arltempNose3.get(2)].getImgIndex()==aniNose3[arltempNose3.get(3)].getImgIndex()
				||aniNose3[arltempNose3.get(4)].getImgIndex()==aniNose3[arltempNose3.get(5)].getImgIndex()
				||aniNose3[arltempNose3.get(5)].getImgIndex()==aniNose3[arltempNose3.get(6)].getImgIndex()
				||aniNose3[arltempNose3.get(6)].getImgIndex()==aniNose3[arltempNose3.get(7)].getImgIndex()
				||aniNose3[arltempNose3.get(8)].getImgIndex()==aniNose3[arltempNose3.get(9)].getImgIndex()
				||aniNose3[arltempNose3.get(9)].getImgIndex()==aniNose3[arltempNose3.get(10)].getImgIndex()
				||aniNose3[arltempNose3.get(10)].getImgIndex()==aniNose3[arltempNose3.get(11)].getImgIndex()
				||aniNose3[arltempNose3.get(0)].getImgIndex()==aniNose3[arltempNose3.get(4)].getImgIndex()
				||aniNose3[arltempNose3.get(4)].getImgIndex()==aniNose3[arltempNose3.get(8)].getImgIndex()
				||aniNose3[arltempNose3.get(1)].getImgIndex()==aniNose3[arltempNose3.get(5)].getImgIndex()
				||aniNose3[arltempNose3.get(5)].getImgIndex()==aniNose3[arltempNose3.get(9)].getImgIndex()
				||aniNose3[arltempNose3.get(2)].getImgIndex()==aniNose3[arltempNose3.get(6)].getImgIndex()
				||aniNose3[arltempNose3.get(6)].getImgIndex()==aniNose3[arltempNose3.get(10)].getImgIndex()
				||aniNose3[arltempNose3.get(3)].getImgIndex()==aniNose3[arltempNose3.get(7)].getImgIndex()
				||aniNose3[arltempNose3.get(7)].getImgIndex()==aniNose3[arltempNose3.get(11)].getImgIndex()) {
			checkLap3();
		}else {
			Log.e(TAG,  "count lap::: " +count);
			return;
		}
		
	}
	public void moveComeCaribou1() {
		
		recCaribou1.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.9f),
				new MoveXModifier(3.4f, recCaribou1.getmXFirst(), 0, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A82_TONAKAI.play();
						for (int i = 0; i < aniCaribou1.length; i++) {
							aniCaribou1[i].animate(new long[]{400,400},new int[]{0,1}, -1);
						}
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkGame1=true;
						checkMan1 = true;
						
						for (int i = 0; i < 6; i++) {
							isTouchCaribou1[i] = true;
						}
						for (int i = 0; i < aniCaribou1.length; i++) {
							aniCaribou1[i].stopAnimation(0);
						}
					}
				})));
	}
	public void moveComeCaribou2(){
		
		recCaribou2.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), 
				new MoveXModifier(3.4f, recCaribou2.getmXFirst(), 30, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A82_TONAKAI.play();
						for (int i = 0; i < aniCaribou2.length; i++) {
							aniCaribou2[i].animate(new long[]{400,400},new int[]{0,1}, -1);
						}
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkGame2 = true;
						checkMan2 = true;
						for (int i = 0; i < 8; i++) {
							isTouchCaribou2[i] = true;
						}
						for (int i = 0; i < aniCaribou2.length; i++) {
							aniCaribou2[i].stopAnimation(0);
						}
					}
				})));
	}
	public void moveComeCaribou3(){
		
		recCaribou3.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), new MoveXModifier(3.4f, recCaribou3.getmXFirst(), 50, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A82_TONAKAI.play();
						for (int i = 0; i < aniCaribou3.length; i++) {
							aniCaribou3[i].animate(new long[]{400,400},new int[]{0,1}, -1);
						}
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						checkGame3=true;
						checkMan3 = true;
						for (int i = 0; i < 12; i++) {
							isTouchCaribou3[i] = true;
						}
						for (int i = 0; i < aniCaribou3.length; i++) {
							aniCaribou3[i].stopAnimation(0);
						}
					}
				})));
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
	public void combineGimmic3WithAction() {
	}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
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
	@Override
	protected void loadKaraokeSound() {
		A82_1_3_GATYA = loadSoundResourceFromSD(Vol3AkahanaResource.A82_1_3_GATYA);
		A82_4_OMEDETOU = loadSoundResourceFromSD(Vol3AkahanaResource.A82_4_OMEDETOU);
		A82_5_KIRAN = loadSoundResourceFromSD(Vol3AkahanaResource.A82_5_KIRAN);
		A82_5_PINPON = loadSoundResourceFromSD(Vol3AkahanaResource.A82_5_PINPON);
		A82_7_NAKANAKA = loadSoundResourceFromSD(Vol3AkahanaResource.A82_7_NAKANAKA);
		A82_7_ONAJIHANA = loadSoundResourceFromSD(Vol3AkahanaResource.A82_7_ONAJIHANA);
		A82_7_STUGIHA = loadSoundResourceFromSD(Vol3AkahanaResource.A82_7_STUGIHA);
		A82_TONAKAI = loadSoundResourceFromSD(Vol3AkahanaResource.A82_TONAKAI);


	}
	
	private void  stopSound() {
		
		A82_1_3_GATYA.stop();
		A82_4_OMEDETOU.stop();
		A82_5_KIRAN.stop();
		A82_5_PINPON.stop();
		A82_7_NAKANAKA.stop();
		A82_7_ONAJIHANA.stop(); 
		A82_7_STUGIHA.stop();
		A82_TONAKAI.stop();
	}
	private void resetData() {
		if (aniBackground2!=null) {
			aniBackground2.animate(600, -1);
		}
		if (recCaribou1!=null) {
			recCaribou1.clearEntityModifiers();
			recCaribou1.setPosition(recCaribou1.getmXFirst(), recCaribou1.getmYFirst());
			aniOldMan1.stopAnimation(0);
		}
		if (recCaribou2!=null) {
			recCaribou2.clearEntityModifiers();
			recCaribou2.setPosition(recCaribou2.getmXFirst(), recCaribou2.getmYFirst());
			aniOldMan2.stopAnimation(0);
		}
		if (recCaribou3!=null) {
			recCaribou3.clearEntityModifiers();
			recCaribou3.setPosition(recCaribou3.getmXFirst(), recCaribou3.getmYFirst());
			aniOldMan3.stopAnimation(2);
		}
		for (int i = 0; i < 6; i++) {
			if (aniNose1[i] != null){
				aniCaribou1[i].setCurrentTileIndex(0);
				aniNose1[i].setCurrentTileIndex(0);
				aniNose1[i].setVisible(false);
			}
		}
		for (int i = 0; i < 8; i++) {
			if (aniNose2[i] != null){
				aniCaribou2[i].setCurrentTileIndex(0);
				aniNose2[i].setCurrentTileIndex(0);
				aniNose2[i].setVisible(false);
			}
		}
		for (int i = 0; i < 12; i++) {
			if (aniNose3[i] != null){
				aniCaribou3[i].setCurrentTileIndex(0);
				aniNose3[i].setCurrentTileIndex(0);
				aniNose3[i].setVisible(false);
			}
		}
		if (aniHouse!=null) {
			aniHouse.setCurrentTileIndex(0);
		}
		for (int i = 0; i < sPresent.length; i++) {
			if (sPresent[i]!=null) {
				sPresent[i].clearEntityModifiers();
				sPresent[i].setVisible(false);
				sPresent[i].setScale(0.2f);
				sPresent[i].setPosition(sPresent[i].getmXFirst(), sPresent[i].getmYFirst());
				
			}
		}
	}
	/////////////////////////////////////////////////////
	// Inner Class
	/////////////////////////////////////////////////////
	public class AnimalAniSprite extends AnimatedSprite {
		
		int index;
		public int getImgIndex() {
			return index;
		}
		
		public void setImgIndex(int index) {
			this.index = index;
		}
		
		public AnimalAniSprite(float pX, float pY, 
				 TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager verBufObjManager) {
			super(pX, pY , pTiledTextureRegion, verBufObjManager);
		}
	}

}
