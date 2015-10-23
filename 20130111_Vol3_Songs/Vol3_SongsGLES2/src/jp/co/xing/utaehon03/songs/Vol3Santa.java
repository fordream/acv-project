/* Vol3Santa.java
* Create on Nov 8, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3SantaResource;

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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Santa extends BaseGameActivity implements IOnSceneTouchListener{
	public final static String TAG = " Vol3Santa "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpSanta1, ttpSanta2, ttpSanta3, ttpSanta4, ttpSanta5, ttpSanta6, ttpSanta7, ttpSanta8 ;
	private TexturePackTextureRegionLibrary ttpLibSanta6, ttpLibSanta7, ttpLibSanta8 ;
	
	private TextureRegion trBackground, trSantaStar, trSantaWater1, trSantaWater2,trRecMan;
	private TextureRegion trFukidashi[] = new TextureRegion[4];
	private TextureRegion trPresent[] = new TextureRegion[22];
	private Sprite sBackground, sSantaStar, sSantaWater1, sSantaWater2, recSanta, sprTemp;
	private Sprite sFukidashi[] = new Sprite[4];
	private TiledTextureRegion ttrCity, ttrSnow, ttrShika, ttrSantaMan;
	private TiledTextureRegion ttrAnimal[]= new TiledTextureRegion[4];
	private TiledTextureRegion ttrKodomo[]= new TiledTextureRegion[10];
	private AnimatedSprite aniCity, aniSnow, aniShika, aniSantaMan, aniSnow1;
	private AnimalAniSprite aniAnimal[]=new AnimalAniSprite[4];
	private KodomoAniSprite aniKodomo[] = new KodomoAniSprite[10];
	private DreamSprite sPresentDream[] = new DreamSprite[22];
	private PresentSprite sPresentSanta[] = new PresentSprite[22];
	private ArrayList<Integer> arlPresentGirl;
	private ArrayList<Integer> arlPresentGirl1;
	private ArrayList<Integer> arlPresentBoy1;
	private ArrayList<Integer> arlPresentBoy2;
	private ArrayList<Integer> arlPresentBoy3;
	
	private ArrayList<Integer> arlKodomo;
	private int countAnimal = 0;
	private int countKodomo = 0;
	private int houseShow = 0;
	private int movePresent = 0;
	private int countBoy = 0;
	private int countGirl = 0;
	float presentFirstX;
	float touchFirstX;
	float touchFirstY;
	float presentFirstY;
	private float pointFukidashi[][]={{257,310},{281,279},{272,94},{313,250}};
	private float arrPointSantaMan[][]={{159,153,182,236,300,286,370,366,159},
			{311,196,142,113,182,232,242,333,333}};
//	private float arrPointShika[][]={{30,47,55,58,75,94,94,143,143,157,144,180,200,200,76,59,73,65,41,24,21},
//			{67,60,69,80,52,42,3,3,56,67,90,90,100,164,164,140,126,111,93,89,77}};
	private boolean touchAnimal = false;
	private boolean touchPresent = false;
	private boolean isMovePresent =false;
	private boolean isUpPresent = false;
	private boolean touchSantaMan = true;
	private boolean touchKodomo = true;
	private boolean checkMoveHouse = false;
	private boolean touchShika =true;
	private float blue,green,red;
	private Sound A1_8_NEIKI2,A1_1_8_7_TIGAU2,A1_1_8_7_YATA1,A1_1_9_IBIKI,
		A1_2_5_TIGAU1,A1_2_5_YATA4,A1_2_6_MUNYA1, A1_3_10_TIGAU2, A1_3_10_UHU,
		A1_3_MUNYA2,A1_4_MUNYA4, A1_04_SYU2,A1_4_URESHINA,A1_5_MUNYA3,A1_6_9_TIGAU1,
		A1_6_9_URESHI,A1_06_CYU, A1_06_IIKODANE, A1_06_PRESENT6,A1_06_SANTADAYO,A1_07_KACHI,
		A1_7_NEIKI1,A1_08_KIRA10,A1_09_MOKIN,A1_09_BOMI2;
	
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("santa/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("santa/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "santa/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources ");
		ttpSanta1 = mTexturePackLoaderFromSource.load("santa1.xml");
		ttpSanta1.loadTexture();
		ttpSanta2= mTexturePackLoaderFromSource.load("santa2.xml");
		ttpSanta2.loadTexture();
		ttpSanta3= mTexturePackLoaderFromSource.load("santa3.xml");
		ttpSanta3.loadTexture();
		ttpSanta4= mTexturePackLoaderFromSource.load("santa4.xml");
		ttpSanta4.loadTexture();
		ttpSanta5= mTexturePackLoaderFromSource.load("santa5.xml");
		ttpSanta5.loadTexture();
		ttpSanta6= mTexturePackLoaderFromSource.load("santa6.xml");
		ttpSanta6.loadTexture();
		ttpLibSanta6 = ttpSanta6.getTexturePackTextureRegionLibrary();
		ttpSanta7= mTexturePackLoaderFromSource.load("santa7.xml");
		ttpSanta7.loadTexture();
		ttpLibSanta7 = ttpSanta7.getTexturePackTextureRegionLibrary();
		ttpSanta8 = mTexturePackLoaderFromSource.load("santa8.xml");
		ttpSanta8.loadTexture();
		ttpLibSanta8 = ttpSanta8.getTexturePackTextureRegionLibrary();
		
		trBackground = ttpLibSanta8.get(Vol3SantaResource.A1_12_IPHONE_HAIKEI_ID);
		
		ttrCity = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.A1_08_1_IPHONE_CITY_ID,
				Vol3SantaResource.A1_08_2_IPHONE_CITY_ID);
		
		ttrSnow = getTiledTextureFromPacker(ttpSanta8, Vol3SantaResource.A1_11_1_IPHONE_SNOW_ID,
				Vol3SantaResource.A1_11_2_IPHONE_SNOW_ID);
		
		ttrAnimal[0] = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.PACK_KAERU);
		ttrAnimal[1] = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.PACK_NEKO);
		ttrAnimal[2] = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.PACK_DOROBOU);
		ttrAnimal[3] = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.PACK_TORI);
		arlPresentGirl = new ArrayList<Integer>();
		arlPresentGirl1 = new ArrayList<Integer>();
		arlKodomo = new ArrayList<Integer>();
		arlPresentBoy1 = new ArrayList<Integer>();
		arlPresentBoy2 = new ArrayList<Integer>();
		arlPresentBoy3 = new ArrayList<Integer>();
		for (int i = 0; i < trFukidashi.length; i++) {
			trFukidashi[i]=ttpLibSanta6.get(Vol3SantaResource.PACK_FUKIDASHI[i]);
		}
		ttrKodomo[0]= getTiledTextureFromPacker(ttpSanta1, Vol3SantaResource.PACK_KODOMO1);
		ttrKodomo[1]= getTiledTextureFromPacker(ttpSanta1, Vol3SantaResource.PACK_KODOMO2);
		ttrKodomo[2]= getTiledTextureFromPacker(ttpSanta2, Vol3SantaResource.PACK_KODOMO3);
		ttrKodomo[3]= getTiledTextureFromPacker(ttpSanta2, Vol3SantaResource.PACK_KODOMO4);
		ttrKodomo[4]= getTiledTextureFromPacker(ttpSanta3, Vol3SantaResource.PACK_KODOMO5);
		ttrKodomo[5]= getTiledTextureFromPacker(ttpSanta3, Vol3SantaResource.PACK_KODOMO6);
		ttrKodomo[6]= getTiledTextureFromPacker(ttpSanta4, Vol3SantaResource.PACK_KODOMO7);
		ttrKodomo[7]= getTiledTextureFromPacker(ttpSanta4, Vol3SantaResource.PACK_KODOMO8);
		ttrKodomo[8]= getTiledTextureFromPacker(ttpSanta5, Vol3SantaResource.PACK_KODOMO9);
		ttrKodomo[9]= getTiledTextureFromPacker(ttpSanta5, Vol3SantaResource.PACK_KODOMO10);
		
		for (int i = 0; i < trPresent.length; i++) {
			trPresent[i] =ttpLibSanta6.get(Vol3SantaResource.PACK_PRESENT[i]);
			
		}
		trSantaStar = ttpLibSanta7.get(Vol3SantaResource.A1_06_2_2_IPHONE_SANTA_ID);
		trSantaWater1 = ttpLibSanta7.get(Vol3SantaResource.A1_06_2_3_IPHONE_SANTA_ID);
		trSantaWater2 = ttpLibSanta7.get(Vol3SantaResource.A1_06_3_2_IPHONE_SANTA_ID);
		ttrShika = getTiledTextureFromPacker(ttpSanta6, Vol3SantaResource.A1_07_1_IPHONE_SHIKA_ID,
				Vol3SantaResource.A1_07_2_IPHONE_SHIKA_ID);
		ttrSantaMan = getTiledTextureFromPacker(ttpSanta7, Vol3SantaResource.A1_06_1_IPHONE_SANTA_ID,
				Vol3SantaResource.A1_06_2_1_IPHONE_SANTA_ID,Vol3SantaResource.A1_06_3_1_IPHONE_SANTA_ID);
		trRecMan = ttpLibSanta6.get(Vol3SantaResource.RECTANGLE_MAN_ID);
	}

	
	
	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		sBackground = new Sprite(0, 0, trBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground);
		aniSnow1 = new AnimatedSprite(-480, 0, ttrSnow, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSnow1);
		aniSnow1.animate(1000,-1);
		
		
		for (int i = 0; i < aniKodomo.length; i++) {
			aniKodomo[i] = new KodomoAniSprite(-18, 48, ttrKodomo[i], this.getVertexBufferObjectManager());
			mScene.attachChild(aniKodomo[i]);
			setvisiableAniSprite(false,aniKodomo[i]);
			aniKodomo[i].setIdSound(i);
			if(i==0||i==5||i==6||i==7||i==8){
				aniKodomo[i].setImgIndex(1);
			}else {
				aniKodomo[i].setImgIndex(0);
			}
			
		}
		recSanta = new Sprite(427, 170,trRecMan, this.getVertexBufferObjectManager());
		blue = recSanta.getBlue();
		green = recSanta.getGreen();
		red = recSanta.getRed();
		mScene.attachChild(recSanta);
		sSantaStar = new Sprite(0, 0, trSantaStar, this.getVertexBufferObjectManager());
		recSanta.attachChild(sSantaStar);
		sSantaWater1 = new Sprite(40, -10, trSantaWater1, this.getVertexBufferObjectManager());
		recSanta.attachChild(sSantaWater1);
		sSantaWater2 = new Sprite(40, -10, trSantaWater2, this.getVertexBufferObjectManager());
		recSanta.attachChild(sSantaWater2);
		setvisiableSprite(false, sSantaStar,sSantaWater1, sSantaWater2);
		aniShika = new AnimatedSprite(-20, 136, ttrShika, this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()&& aniShika.isVisible()&&touchShika) {
					Log.d(TAG, "touch shika!!!!!!!!!!!");
					Log.d(TAG, "touch shika  ");
					A1_07_KACHI.play();
					if (aniShika.getCurrentTileIndex()==0) {
						aniShika.setCurrentTileIndex(1);
					}else {
						aniShika.setCurrentTileIndex(0);
					}
				}
				return false;
			};
		};
		mScene.registerTouchArea(aniShika);
		aniShika.setVisible(false);
		recSanta.attachChild(aniShika);
		aniSantaMan = new AnimatedSprite(34,-10,ttrSantaMan,this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() &&touchSantaMan&&aniSantaMan.isVisible()
						&&checkContainsPolygon(aniSantaMan, arrPointSantaMan, 9, pTouchAreaLocalX, pTouchAreaLocalY)) {
					Log.d(TAG, "touch santa man:");
						Log.d(TAG, "touch santa man:!!!!!!!!!!!");
						A1_06_SANTADAYO.play();
						touchSantaMan = false;
						mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
							
							@Override
							public void onTimePassed(TimerHandler arg0) {
								touchSantaMan = true;
							}
						}));
				}
				return false;
			};
		};
		recSanta.attachChild(aniSantaMan);
		mScene.registerTouchArea(aniSantaMan);
		mScene.registerTouchArea(recSanta);
		aniSantaMan.setVisible(false);
		recSanta.setScale(0.1f);
		recSanta.setVisible(false);
		aniSnow = new AnimatedSprite(0, 0, ttrSnow, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSnow);
		aniSnow.animate(1000,-1);
		for (int i = 0; i < sFukidashi.length; i++) {
			sFukidashi[i] = new Sprite(pointFukidashi[i][0], pointFukidashi[i][1], trFukidashi[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sFukidashi[i]);
			setvisiableSprite(false, sFukidashi[i]);
		}
		for (int i = 0; i < aniAnimal.length; i++) {
			
			aniAnimal[i] = new AnimalAniSprite(-186, -22, ttrAnimal[i], this.getVertexBufferObjectManager());
			mScene.registerTouchArea(aniAnimal[i]);
			mScene.attachChild(aniAnimal[i]);
		}
		for (int i = 0; i < sPresentDream.length; i++) {
			sPresentDream[i] = new DreamSprite(0, 0, trPresent[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sPresentDream[i]);
			sPresentDream[i].setImgIndex(i);
			sPresentDream[i].setVisible(false);
			sPresentSanta[i] = new PresentSprite(0, 0, trPresent[i], this.getVertexBufferObjectManager());
			recSanta.attachChild(sPresentSanta[i]);
			sPresentSanta[i].setImgIndex(i);
			sPresentSanta[i].setVisible(false);
			//TODO
			if(i==1||i==2){
				sPresentDream[i].setScale(0.65f);
				sPresentSanta[i].setScale(0.65f);
			}else if (i==21||i==7||i==0||i==15||i==11||i==12||i==4||i==13||i==5||i==6){
				sPresentDream[i].setScale(0.85f);
				sPresentSanta[i].setScale(0.85f);
			}else{
				sPresentDream[i].setScale(0.95f);
				sPresentSanta[i].setScale(0.95f);
			}
			if (i==4||i==11||i==12) {
				arlPresentBoy1.add(i);
			}else if(i==17||i==19||i==20||i==8){
				arlPresentBoy2.add(i);
			}else if (i==0||i==7||i==16) {
				arlPresentBoy3.add(i);
			} else if (i==1||i==2) {
				arlPresentGirl1.add(i);
			} else if(i==6||i==3||i==5||i==9||i==10||i==13||i==15||i==18||i==21) {
				arlPresentGirl.add(i);
			}
			mScene.registerTouchArea(sPresentSanta[i]);
			
		}
		
		aniCity = new AnimatedSprite(342,491, ttrCity, this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					
					A1_08_KIRA10.play();
					if(aniCity.getCurrentTileIndex()==0) {
						aniCity.setCurrentTileIndex(1);
					}else {
						aniCity.setCurrentTileIndex(0);
					}
				}
				return false;
			};
		};
		mScene.registerTouchArea(aniCity);
		mScene.attachChild(aniCity);
		
		addGimmicsButton(mScene, Vol3SantaResource.SOUND_GIMMIC, Vol3SantaResource.IMAGE_GIMMIC, ttpLibSanta6);
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
		initial();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		initial();
		arlKodomo = getRandomArray(10, 10);
		mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				
				loadRandomKodomo(countKodomo);
			}
		}));
		super.onResumeGame();
	}
	private void initial(){
		touchAnimal = false;
		touchPresent = false;
		isUpPresent = false;
		isMovePresent = false;
		touchSantaMan = true;
		touchKodomo = true;
		checkMoveHouse = false;
		touchShika =true;
		countAnimal = 0;
		countKodomo = 0;
		houseShow = 0;
		movePresent = 0;
		countBoy = 0;
		countGirl = 0;
		for (int i = 0; i < arlPresentBoy1.size(); i++) {
			
			sPresentDream[arlPresentBoy1.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentBoy2.size(); i++) {
			
			sPresentDream[arlPresentBoy2.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentBoy3.size(); i++) {
	
			sPresentDream[arlPresentBoy3.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentGirl.size(); i++) {
			
			sPresentDream[arlPresentGirl.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentGirl1.size(); i++) {
			
			sPresentDream[arlPresentGirl1.get(i)].setVisible(false);
		}
	}
	private void resetData() {
		if (recSanta!=null) {
			recSanta.clearEntityModifiers();
			recSanta.setPosition(recSanta.getmXFirst(), recSanta.getmYFirst());
			recSanta.setVisible(false);
		}
		if (sprTemp!=null){
			sprTemp.clearEntityModifiers();
			sprTemp.setVisible(false);
			
		}
		
		setvisiableSprite(false, sFukidashi[0], sFukidashi[1], sFukidashi[2], sFukidashi[3]);
		for (int j = 0; j < aniAnimal.length; j++) {
			if (aniAnimal[j]!=null) {
				aniAnimal[j].clearEntityModifiers();
				aniAnimal[j].setPosition(aniAnimal[j].getmXFirst(), aniAnimal[j].getmYFirst());
				aniAnimal[j].setCurrentTileIndex(0);
			}
		}
		
		for (int i = 0; i < sPresentSanta.length; i++) {
			
			if (sPresentSanta[i]!= null){
				sPresentSanta[i].setPosition(0, 0);
				sPresentSanta[i].setVisible(false);
				
			}
		}
		for (int i = 0; i < sPresentDream.length; i++) {
			sPresentDream[i].setVisible(false);
			sPresentDream[i].setPosition(sPresentDream[i].getmXFirst(), sPresentDream[i].getmYFirst());
		}
		for (int i = 0; i < arlPresentBoy1.size(); i++) {
			sPresentDream[arlPresentBoy1.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentBoy2.size(); i++) {
			sPresentDream[arlPresentBoy2.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentBoy3.size(); i++) {
			sPresentDream[arlPresentBoy3.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentGirl.size(); i++) {
			sPresentDream[arlPresentGirl.get(i)].setVisible(false);
		}
		for (int i = 0; i < arlPresentGirl1.size(); i++) {
			sPresentDream[arlPresentGirl1.get(i)].setVisible(false);
		}
		for (int i = 0; i < aniKodomo.length; i++) {
			aniKodomo[i].clearEntityModifiers();
			aniKodomo[i].setCurrentTileIndex(0);
			aniKodomo[i].setVisible(false);
			
		}
		if (sSantaStar!=null) {
			sSantaStar.clearEntityModifiers();
			sSantaStar.setVisible(false);
		}
		if (sSantaWater1!=null) {
			sSantaWater1.clearEntityModifiers();
			sSantaWater1.setVisible(false);
		}
		if (sSantaWater2!=null) {
			sSantaWater2.clearEntityModifiers();
			sSantaWater2.setVisible(false);
		}
		if (aniShika!=null) {
			aniShika.setCurrentTileIndex(0);
		}
		if (aniSantaMan!=null) {
			aniSantaMan.setCurrentTileIndex(0);
		}
		if(aniCity != null) {
			aniCity.setCurrentTileIndex(0);
		}
		if (mScene != null) {
			mScene.clearEntityModifiers();
		}
		
	}
	
	public void loadRandomKodomo(final int index) {
		houseShow = index;
		Collections.shuffle(arlPresentBoy1);
		Collections.shuffle(arlPresentBoy2);
		Collections.shuffle(arlPresentBoy3);
		Collections.shuffle(arlPresentGirl);
		Collections.shuffle(arlPresentGirl1);
		aniKodomo[arlKodomo.get(index)].setVisible(true);
		touchKodomo =true;
		A1_04_SYU2.play();
		//TODO
		if(aniKodomo[arlKodomo.get(index)].getImgIndex()==1){
			sPresentSanta[arlPresentBoy3.get(0)].setPosition(380,50);
			sPresentSanta[arlPresentBoy3.get(0)].setVisible(true);
			sPresentSanta[arlPresentBoy1.get(0)].setPosition(310,20);
			sPresentSanta[arlPresentBoy1.get(0)].setVisible(true);
			sPresentSanta[arlPresentBoy2.get(0)].setPosition(374,116);
			sPresentSanta[arlPresentBoy2.get(0)].setVisible(true);
		}else {
			sPresentSanta[arlPresentGirl.get(1)].setPosition(312,25);
			sPresentSanta[arlPresentGirl.get(1)].setVisible(true);
			sPresentSanta[arlPresentGirl1.get(0)].setPosition(384,4);
			sPresentSanta[arlPresentGirl1.get(0)].setVisible(true);
			sPresentSanta[arlPresentGirl.get(0)].setPosition(373,131);
			sPresentSanta[arlPresentGirl.get(0)].setVisible(true);
		}
		
		A1_09_BOMI2.stop();
		
		moveMan();
		sFukidashi[0].setVisible(true);
		mScene.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.1f,new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sFukidashi[1].setVisible(true);
				
			}
		}),new DelayModifier(0.2f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sFukidashi[3].setVisible(true);
			}
		}), new DelayModifier(0.6f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sFukidashi[2].setVisible(true);
				loadRandomPresent(index);
				isTouchGimmic[2]= false;
				touchAnimal=false;
				moveAnimal(countAnimal);
			}
		})));
	}
	public void moveMan() {
		Log.d(TAG, "moveMan......... ");
		recSanta.setPosition(650, 340);
		aniShika.setVisible(false);
		aniShika.setCurrentTileIndex(0);
		aniSantaMan.setVisible(false);
		recSanta.setVisible(true);
		touchSantaMan=false;
		touchShika =false;
		recSanta.setColor(red, green, blue, 1);
		recSanta.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f), new ParallelEntityModifier(
					new ScaleModifier(1.5f,0.1f,0.3f),
					new MoveModifier(1.5f, recSanta.getX(), recSanta.getX()-400,recSanta.getY(),recSanta.getY()-80,
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							recSanta.setFlippedHorizontal(true);
							if(aniKodomo[arlKodomo.get(houseShow)].getImgIndex()==1){
								sPresentSanta[arlPresentBoy3.get(0)].setPosition(50, 34);
								sPresentSanta[arlPresentBoy1.get(0)].setPosition(125, 10);
								sPresentSanta[arlPresentBoy2.get(0)].setPosition(50, 120);
							}else {
								sPresentSanta[arlPresentGirl1.get(0)].setPosition(56, 5);
								sPresentSanta[arlPresentGirl.get(1)].setPosition(130, 15);
								sPresentSanta[arlPresentGirl.get(0)].setPosition(58, 132);
							}
							moveBackMan();
						}
					}))
				));
		
	}
	private void moveBackMan(){
		checkMoveHouse=true;
		recSanta.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.1f),
				new ParallelEntityModifier(new ScaleModifier(2.0f,0.3f,0.1f),
						new MoveModifier(2.0f, recSanta.getX(), recSanta.getX()+400, recSanta.getY(), recSanta.getY()-200, 
								new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								recSanta.setFlippedHorizontal(false);
								//TODO
								if(aniKodomo[arlKodomo.get(houseShow)].getImgIndex()==1){
									sPresentSanta[arlPresentBoy3.get(0)].setPosition(388,51);
									sPresentSanta[arlPresentBoy1.get(0)].setPosition(315,18);
									sPresentSanta[arlPresentBoy2.get(0)].setPosition(374,116);
								}else {
									sPresentSanta[arlPresentGirl1.get(0)].setPosition(384,4);
									sPresentSanta[arlPresentGirl.get(1)].setPosition(312, 25);
									sPresentSanta[arlPresentGirl.get(0)].setPosition(373,131);
								}
								moveFirstMan();
							}
						}))));
	}
	
	private void moveFirstMan() {
		aniShika.setVisible(true);
		aniSantaMan.setVisible(true);
		recSanta.setColor(Color.TRANSPARENT);
		recSanta.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.1f),
				new ParallelEntityModifier(new ScaleModifier(1.5f,0.1f,1f),
				new MoveModifier(1.5f, recSanta.getX(), recSanta.getmXFirst(), recSanta.getY(), recSanta.getmYFirst(),
						new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								
								touchPresent = true;
								touchSantaMan=true;
								touchShika =true;
							}
						}))));
	}
	
	private void loadRandomPresent(int index){
		
		if(aniKodomo[arlKodomo.get(houseShow)].getImgIndex()==1){
			//hien thi do choi cho em be nam
			if (countBoy==0) {
				countBoy=1;
			sPresentDream[arlPresentBoy1.get(0)].setPosition(356, 155);
			sPresentDream[arlPresentBoy1.get(0)].setVisible(true);
			}else if (countBoy==1){
				countBoy = 2;
				sPresentDream[arlPresentBoy2.get(0)].setPosition(356, 155);
				sPresentDream[arlPresentBoy2.get(0)].setVisible(true);
			}else if (countBoy ==2) {
				countBoy = 0;
				sPresentDream[arlPresentBoy3.get(0)].setPosition(356, 155);
				sPresentDream[arlPresentBoy3.get(0)].setVisible(true);
			}
		}else {
			//hien thi do choi cho em be nu
			if(countGirl ==0){
				countGirl = 1;
				sPresentDream[arlPresentGirl.get(0)].setPosition(360, 140);
				sPresentDream[arlPresentGirl.get(0)].setVisible(true);
			}else if (countGirl==1){
				countGirl = 0;
				sPresentDream[arlPresentGirl1.get(0)].setPosition(360, 140);
				sPresentDream[arlPresentGirl1.get(0)].setVisible(true);
			}
		}
	}
	public void moveAnimal(final int index) {
		runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				aniAnimal[index].detachSelf();
				aniAnimal[index].setPosition(-150, -40);
				aniKodomo[arlKodomo.get(houseShow)].attachChild(aniAnimal[index]);
			}
		});
		aniAnimal[index].animate(new long[]{400,400}, new int[]{0,1},-1);
		aniAnimal[index].clearEntityModifiers();
		aniAnimal[index].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.2f),
				new MoveXModifier(1.6f, -150, 180, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						A1_09_BOMI2.play();
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aniAnimal[index].stopAnimation(2);
						touchAnimal=true;
						isTouchGimmic[2]=true;
						
					}
				})));
		
		
	}
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if(pSceneTouchEvent.isActionDown()) {
			for (int i = 0; i < aniKodomo.length; i++) {
				
				if (checkContains(aniKodomo[i], 130, 330, 270, 470, pX, pY)&&aniKodomo[i].isVisible()&&touchKodomo) {
					touchKodomo=false;
					switch (aniKodomo[i].getIdSound()) {
					case 0:
					case 8:
						A1_1_9_IBIKI.play();
						break;
					case 1:
					case 5:
						A1_2_6_MUNYA1.play();
						break;
					case 2:
						A1_3_MUNYA2.play();
						break;
					case 3:
					case 9:
						A1_4_MUNYA4.play();
						break;
					case 4:
						A1_5_MUNYA3.play();
						break;
					case 6:
						A1_7_NEIKI1.play();
						break;
					case 7:
						A1_8_NEIKI2.play();
						break;

					default:
						break;
					}
					mScene.registerUpdateHandler(new TimerHandler(2.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							touchKodomo=true;
						}
					}));
				}
			}
		}
		return false;
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
	private void setvisiableSprite(boolean value, Sprite ...sprites){
		if (sprites != null) {
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].setPosition(sprites[i].getmXFirst(), sprites[i].getmYFirst());
				sprites[i].setVisible(value);
				
			}
		}
	}
	private void setvisiableAniSprite(boolean value, AnimatedSprite ...aniSprites){
		if (aniSprites != null) {
			for (int i = 0; i < aniSprites.length; i++) {
				aniSprites[i].setVisible(value);
				aniSprites[i].setPosition(aniSprites[i].getmXFirst(), aniSprites[i].getmYFirst());
				aniSprites[i].setCurrentTileIndex(0);
				
			}
			
		}
	}
	
	/////////////////////////////////////////////////////
	// Inner Class
	/////////////////////////////////////////////////////
	public class KodomoAniSprite extends AnimatedSprite {
		
		int index;
		int idSound;
		public int getIdSound() {
			return idSound;
		}
		public void setIdSound(int idSound) {
			this.idSound = idSound;
		}
		public int getImgIndex() {
			return index;
		}
		
		public void setImgIndex(int index) {
			this.index = index;
		}
		
		public KodomoAniSprite(float pX, float pY, 
				 TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager verBufObjManager) {
			super(pX, pY , pTiledTextureRegion, verBufObjManager);
		}
	}
	
	public class PresentSprite extends Sprite{
		
		int index;
		public int getImgIndex() {
			return index;
		}
		
		public void setImgIndex(int index) {
			this.index = index;
		}
		public PresentSprite(float pX, float pY,TextureRegion pTextureRegion, 
				VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTextureRegion, verBufObjManager);
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int action = pSceneTouchEvent.getAction();
			final float pX = pSceneTouchEvent.getX();
			final float pY = pSceneTouchEvent.getY();
			switch (action) {
			case TouchEvent.ACTION_DOWN:
				Log.d(TAG, "ACTION_DOWN:::::");
					touchFirstX = pX;
					touchFirstY = pY;
					Log.d(TAG, "pX:::: "+pX+"/ pY::::"+pY);
			case TouchEvent.ACTION_MOVE:
				Log.d(TAG, "ACTION_MOVE:::::");
				if ((pX < touchFirstX - 15
						|| pY < touchFirstY - 15
						|| pX > touchFirstX + 15
						|| pY > touchFirstY + 15) && touchPresent && this.contains(pX, pY) && this.isVisible()) {
					Log.d(TAG, "pX:::: "+pX+"/ pY::::" +pY);
					Log.d(TAG, "movePresent:::: "+movePresent);
					touchPresent = false;
					presentFirstX = this.getX();
					presentFirstY = this.getY();
					isMovePresent = true;
					isUpPresent = true;
					A1_06_CYU.play();
					
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							PresentSprite.this.detachSelf();
							PresentSprite.this.setPosition(pX-(PresentSprite.this.getWidth()/2),
									pY - (PresentSprite.this.getHeight()/2));
							mScene.attachChild(PresentSprite.this);
						}
					});
				}
				if (isMovePresent) {
					Log.d(TAG, "ACTION_MOVE MMMMMMMMMMMMMM: ");
					Log.d(TAG, "pX:: "+pX+"pY:::"+pY);
					this.setPosition(pX-(this.getWidth()/2), 
							pY - (this.getHeight()/2));
				}
				
				break;
			case TouchEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP ::: ");
				Log.d(TAG, "presentFirstX ::: "+presentFirstX+" presentFirstY:::"+presentFirstY);
				if (isUpPresent) {
					isUpPresent = false;
					isMovePresent = false;
					
					if ((this.collidesWith(sPresentDream[arlPresentBoy1.get(0)]))||
							this.collidesWith(sPresentDream[arlPresentBoy2.get(0)])||
							this.collidesWith(sPresentDream[arlPresentBoy3.get(0)])||
							this.collidesWith(sPresentDream[arlPresentGirl.get(0)])
							 ||this.collidesWith(sPresentDream[arlPresentGirl1.get(0)])) {
						touchKodomo =false;
						this.setPosition(pX-(this.getWidth()/2),
								pY-(this.getHeight()/2));
						if((this.getImgIndex()==sPresentDream[arlPresentBoy1.get(0)].getImgIndex()
								&& sPresentDream[arlPresentBoy1.get(0)].isVisible())||
								(this.getImgIndex()==sPresentDream[arlPresentBoy2.get(0)].getImgIndex()
								&& sPresentDream[arlPresentBoy2.get(0)].isVisible())||
								(this.getImgIndex()==sPresentDream[arlPresentBoy3.get(0)].getImgIndex()
										&& sPresentDream[arlPresentBoy3.get(0)].isVisible())||
								(this.getImgIndex()==sPresentDream[arlPresentGirl1.get(0)].getImgIndex()
										&& sPresentDream[arlPresentGirl1.get(0)].isVisible())||
								(this.getImgIndex()==sPresentDream[arlPresentGirl.get(0)].getImgIndex()
										&& sPresentDream[arlPresentGirl.get(0)].isVisible())) {
							touchSantaMan=false;
							Log.e(TAG, "co vao day ko&&&&&&&&&&&&&&&&&&&");
							switch (aniKodomo[arlKodomo.get(houseShow)].getIdSound()) {
							case 0:
							case 6:
							case 7:	
								A1_1_8_7_YATA1.play();
								break;
							case 1:	
							case 4:	
								A1_2_5_YATA4.play();
								break;
							case 2:
							case 9:
								A1_3_10_UHU.play();
								break;
							case 3:
								A1_4_URESHINA.play();
								break;
							case 5:	
							case 8:	
								A1_6_9_URESHI.play();
								break;
							default:
								break;
							}
							aniKodomo[arlKodomo.get(houseShow)].setCurrentTileIndex(1);
							aniSantaMan.setCurrentTileIndex(1);
							sSantaStar.setVisible(true);
							sSantaWater1.setVisible(true);
							sSantaStar.registerEntityModifier(new SequenceEntityModifier(
									new AlphaModifier(0.2f,1.0f,0.0f),new AlphaModifier(0.2f,0.0f,1.0f),
									new AlphaModifier(0.2f,1.0f,0.0f),new AlphaModifier(0.2f,0.0f,1.0f)));
							sSantaWater1.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.3f),
									new AlphaModifier(0.2f,1.0f,0.0f),new AlphaModifier(0.2f,0.0f,1.0f),
									new AlphaModifier(0.2f,1.0f,0.0f),new AlphaModifier(0.2f,0.0f,1.0f, 
											new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											aniSantaMan.setCurrentTileIndex(0);
											sSantaStar.setVisible(false);
											sSantaWater1.setVisible(false);
											
											Log.d(TAG, " touchPresent:::" +touchPresent+"/isMovePresent::: "+isMovePresent);
											runOnUpdateThread(new Runnable() {
												
												@Override
												public void run() {
													PresentSprite.this.detachSelf();
													Log.d(TAG, "ACTION_UP uu222222222222222: ");
													PresentSprite.this.setPosition(presentFirstX, presentFirstY);
													recSanta.attachChild(PresentSprite.this);
													PresentSprite.this.setVisible(false);
													
												}
											});
											if(sPresentDream[arlPresentBoy1.get(0)].isVisible()){
												moveEndGame(sPresentDream[arlPresentBoy1.get(0)]);
												
											}else if (sPresentDream[arlPresentBoy2.get(0)].isVisible()) {
												moveEndGame(sPresentDream[arlPresentBoy2.get(0)]);
												
											}else if (sPresentDream[arlPresentBoy3.get(0)].isVisible()){
												moveEndGame(sPresentDream[arlPresentBoy3.get(0)]);
											}
											else if(sPresentDream[arlPresentGirl.get(0)].isVisible()){
												moveEndGame(sPresentDream[arlPresentGirl.get(0)]);
											}else if(sPresentDream[arlPresentGirl1.get(0)].isVisible()){
												moveEndGame(sPresentDream[arlPresentGirl1.get(0)]);
											}
										}
									})));
							
						}else {
							//neu sai
							switch (aniKodomo[arlKodomo.get(houseShow)].getIdSound()) {
							case 0:
							case 6:
							case 7:	
								A1_1_8_7_TIGAU2.play();
								break;
							case 1:	
							case 3:
							case 4:	
								A1_2_5_TIGAU1.play();
								break;
							case 2:
							case 9:
								A1_3_10_TIGAU2.play();
								break;
							case 5:	
							case 8:	
								A1_6_9_TIGAU1.play();
								break;
							default:
								break;
							}
							aniKodomo[arlKodomo.get(houseShow)].setCurrentTileIndex(2);
							aniSantaMan.setCurrentTileIndex(2);
							sSantaWater2.setVisible(true);
							sSantaWater2.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.3f),
									new AlphaModifier(0.2f, 1.0f, 0.0f),new AlphaModifier(0.2f, 0.0f, 1.0f),
									new AlphaModifier(0.2f, 1.0f, 0.0f),new AlphaModifier(0.2f, 0.0f, 1.0f, 
											new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											sSantaWater2.setVisible(false);
											aniKodomo[arlKodomo.get(houseShow)].setCurrentTileIndex(0);
											aniSantaMan.setCurrentTileIndex(0);
											runOnUpdateThread(new Runnable() {
												
												@Override
												public void run() {
													PresentSprite.this.detachSelf();
													Log.d(TAG, "ACTION_UP uu22222 ");
													PresentSprite.this.setPosition(presentFirstX, presentFirstY);
													recSanta.attachChild(PresentSprite.this);
													touchPresent = true;
													touchKodomo =true;
												}
											});
										}
								})));
						}
					}else {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								PresentSprite.this.detachSelf();
								Log.d(TAG, "ACTION_UP uuuuuu2222222: ");
								PresentSprite.this.setPosition(presentFirstX, presentFirstY);
								recSanta.attachChild(PresentSprite.this);
								touchPresent = true;
								
							}
						});
					}
				}
				break;
			default:
				break;
			}
			return true;
		}
	}
	public void moveEndGame(final Sprite sprPresent) {
		sprTemp=sprPresent;
		sprTemp.setVisible(true);
		
		sprTemp.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.6f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				A1_06_PRESENT6.play();
			}
		}),new MoveModifier(1.5f, sprPresent.getX(), sprPresent.getX()-110, 
						sprPresent.getY(), sprPresent.getY()+250), 
						new DelayModifier(0.3f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								checkMoveHouse = false;
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								for (int j = 0; j < sFukidashi.length; j++) {
									setvisiableSprite(false, sFukidashi[j], sprTemp, sprPresent);
								}
								float timeDelay = 0.5f;
								countKodomo++;
								if(countKodomo==10){
									countKodomo=0;
									A1_06_IIKODANE.play();
									timeDelay = 3.5f;
									setvisiableSprite(false, sPresentSanta[arlPresentBoy1.get(0)], sPresentSanta[arlPresentBoy2.get(0)],
											sPresentSanta[arlPresentBoy3.get(0)], sPresentSanta[arlPresentGirl.get(0)],
											sPresentSanta[arlPresentGirl.get(1)], sPresentSanta[arlPresentGirl1.get(0)]);
								}
								moveHouse(timeDelay);
							}
						})));
	}
	public void moveHouse(float timeDelay) {
		touchAnimal = false;
		isTouchGimmic[2] = false;
		
		
		A1_09_BOMI2.stop();
		
		aniKodomo[arlKodomo.get(houseShow)].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timeDelay),
				new MoveXModifier(1.5f, aniKodomo[arlKodomo.get(houseShow)].getX(), -430)));
		recSanta.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timeDelay), 
				new ParallelEntityModifier(new ScaleModifier(2.5f,1f,0.1f),
						new MoveXModifier(2.5f, recSanta.getX(), -530, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								touchShika =false;
								
								A1_09_BOMI2.stop();
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								setvisiableSprite(false, sPresentSanta[arlPresentBoy1.get(0)], sPresentSanta[arlPresentBoy2.get(0)],
										sPresentSanta[arlPresentBoy3.get(0)], sPresentSanta[arlPresentGirl.get(0)],
										sPresentSanta[arlPresentGirl.get(1)], sPresentSanta[arlPresentGirl1.get(0)]);
								setvisiableAniSprite(false, aniKodomo[arlKodomo.get(houseShow)]);
								Log.d(TAG, "countKodomo::::" +countKodomo);
								aniAnimal[countAnimal].setPosition(-150, -40);
								aniAnimal[countAnimal].setCurrentTileIndex(0);
								aniAnimal[countAnimal].clearEntityModifiers();
								countAnimal++;
								
								A1_09_BOMI2.stop();
								
								if(countAnimal==4) {
									countAnimal=0;
								}
								loadRandomKodomo(countKodomo);
							}
						}))));
		
	}
	
	public class DreamSprite extends Sprite {
		int index;
		
		public int getImgIndex() {
			return index;
		}
		
		public void setImgIndex(int index) {
			this.index = index;
		}
		public DreamSprite(float pX, float pY,TextureRegion pTextureRegion, VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTextureRegion, verBufObjManager);
		}
	}
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
		
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(pSceneTouchEvent.isActionDown()&&touchAnimal) {
				touchAnimal=false;
				isTouchGimmic[2]= false;
				touchAnimal();
				
			}
			return true;
		}
	}
	public void touchAnimal() {
		aniAnimal[countAnimal].setCurrentTileIndex(3);
		A1_09_MOKIN.play();
		
		aniAnimal[countAnimal].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.1f),
				new MoveYModifier(0.1f, aniAnimal[countAnimal].getY(), aniAnimal[countAnimal].getY()-50),
				new MoveYModifier(0.6f, aniAnimal[countAnimal].getY()-50, aniAnimal[countAnimal].getY()+500,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						
						aniAnimal[countAnimal].setPosition(-150, -40);
						aniAnimal[countAnimal].setCurrentTileIndex(0);
						countAnimal++;
						if(countAnimal<=3){
							moveAnimal(countAnimal);
						}else {
							countAnimal=0;
							moveAnimal(countAnimal);
						}
					}
				})));
	}
	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimmic[2]&&checkMoveHouse) {
			isTouchGimmic[2]= false;
			touchAnimal=false;
			touchAnimal();
		}
	}
	@Override
	protected void loadKaraokeSound() {
		A1_8_NEIKI2 = loadSoundResourceFromSD(Vol3SantaResource.A1_8_NEIKI2);
		A1_1_8_7_TIGAU2 = loadSoundResourceFromSD(Vol3SantaResource.A1_1_8_7_TIGAU2);
		A1_1_8_7_YATA1 = loadSoundResourceFromSD(Vol3SantaResource.A1_1_8_7_YATA1);
		A1_1_9_IBIKI = loadSoundResourceFromSD(Vol3SantaResource.A1_1_9_IBIKI);
		A1_2_5_TIGAU1 = loadSoundResourceFromSD(Vol3SantaResource.A1_2_5_TIGAU1);
		A1_2_5_YATA4 = loadSoundResourceFromSD(Vol3SantaResource.A1_2_5_YATA4);
		A1_2_6_MUNYA1 = loadSoundResourceFromSD(Vol3SantaResource.A1_2_6_MUNYA1);
		A1_3_10_TIGAU2 = loadSoundResourceFromSD(Vol3SantaResource.A1_3_10_TIGAU2);
		A1_3_10_UHU = loadSoundResourceFromSD(Vol3SantaResource.A1_3_10_UHU);
		A1_3_MUNYA2 = loadSoundResourceFromSD(Vol3SantaResource.A1_3_MUNYA2);
		A1_4_MUNYA4 = loadSoundResourceFromSD(Vol3SantaResource.A1_4_MUNYA4);
		A1_04_SYU2 = loadSoundResourceFromSD(Vol3SantaResource.A1_04_SYU2);
		A1_4_URESHINA = loadSoundResourceFromSD(Vol3SantaResource.A1_4_URESHINA);
		A1_5_MUNYA3 = loadSoundResourceFromSD(Vol3SantaResource.A1_5_MUNYA3);
		A1_6_9_TIGAU1 = loadSoundResourceFromSD(Vol3SantaResource.A1_6_9_TIGAU1);
		A1_6_9_URESHI = loadSoundResourceFromSD(Vol3SantaResource.A1_6_9_URESHI);
		A1_06_CYU = loadSoundResourceFromSD(Vol3SantaResource.A1_06_CYU);
		A1_06_IIKODANE = loadSoundResourceFromSD(Vol3SantaResource.A1_06_IIKODANE);
		A1_06_PRESENT6 = loadSoundResourceFromSD(Vol3SantaResource.A1_06_PRESENT6);
		A1_06_SANTADAYO = loadSoundResourceFromSD(Vol3SantaResource.A1_06_SANTADAYO);
		A1_07_KACHI = loadSoundResourceFromSD(Vol3SantaResource.A1_07_KACHI);
		A1_7_NEIKI1 = loadSoundResourceFromSD(Vol3SantaResource.A1_7_NEIKI1);
		A1_08_KIRA10 = loadSoundResourceFromSD(Vol3SantaResource.A1_08_KIRA10);
		A1_09_MOKIN = loadSoundResourceFromSD(Vol3SantaResource.A1_09_MOKIN);
		A1_09_BOMI2 = loadSoundResourceFromSD(Vol3SantaResource.A1_09_BOMI2);

	}
	private void stopSound(){
		A1_8_NEIKI2.stop();
		A1_1_8_7_TIGAU2.stop();
		A1_1_8_7_YATA1.stop();
		A1_1_9_IBIKI.stop();
		A1_2_5_TIGAU1.stop();
		A1_2_5_YATA4.stop();
		A1_2_6_MUNYA1.stop(); 
		A1_3_10_TIGAU2.stop();
		A1_3_10_UHU.stop();
		A1_3_MUNYA2.stop();
		A1_4_MUNYA4.stop();
		A1_04_SYU2.stop();
		A1_4_URESHINA.stop();
		A1_5_MUNYA3.stop();
		A1_6_9_TIGAU1.stop();
		A1_6_9_URESHI.stop();
		A1_06_CYU.stop();
		A1_06_IIKODANE.stop();
		A1_06_PRESENT6.stop();
		A1_06_SANTADAYO.stop();
		A1_07_KACHI.stop();
		A1_7_NEIKI1.stop();
		A1_08_KIRA10.stop();
		A1_09_MOKIN.stop();
		A1_09_BOMI2.stop();
	}
	
}
