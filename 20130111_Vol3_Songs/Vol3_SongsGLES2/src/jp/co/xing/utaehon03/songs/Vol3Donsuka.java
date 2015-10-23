/* Vol3Donsuka.java
* Create on Sep 28, 2012
*/
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3DonsukaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.util.modifier.ease.EaseStrongIn;

import android.util.Log;

public class Vol3Donsuka extends BaseGameFragment implements IOnSceneTouchListener, IAnimationListener {

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpDonsuka1, ttpDonsuka2, ttpDonsuka5, ttpDonsuka3, ttpDonsuka4;
	private TexturePackTextureRegionLibrary ttpLibDonsuka1,ttpLibDonsuka5;
	
	public final static String TAG = " Vol3Donsuka "; 
	private TextureRegion trGimmic, trSky, trBall, trRainbow, trPaper;
	private Sprite sGimmic, sSky, sBall, sRainbow;
	
	private TextureRegion txrNotes1[] = new TextureRegion[6];
	private TextureRegion txrNotes2[] = new TextureRegion[6];
	private TextureRegion txrInstrument[] = new TextureRegion[10];
	private Sprite sInstrument[] = new Sprite[10];
	private Sprite sPaper[] = new Sprite[4];
	private Sprite sNotes1[] = new Sprite[6];
	private Sprite sNotes2[] = new Sprite[6];
	private Sprite sNotes3[] = new Sprite[6];
	private Sprite sNotes4[] = new Sprite[6];
	private Sprite sNotes5[] = new Sprite[6];
	private Sprite sNotes7[] = new Sprite[6];
	private Sprite sNotes8[] = new Sprite[6];
	private Sprite sNotes9[] = new Sprite[6];
	
	private TiledTextureRegion ttrBackground;
	private AnimatedSprite aniBackground;
	private int countSound=0;
	private TiledTextureRegion ttrMusicians[] = new TiledTextureRegion[10];
	
	private AnimatedSprite aniMusicians[] = new AnimatedSprite[10];
	
	private Sound A75_5_HORUN,A75_8_ODAIKO,A75_3_GO,A75_13_2_2_KYUJO,A75_4_7_TRUMPET,
		A75_1_8_HOISULU,A75_10_8_KODAIKO,A75_2_9_PON,A75_14_OMEDETO,A75_7_8_SYMBLE,A75_3_7_FLAG,
		A75_6_7_YUFONIUMU,A75_4_TRUMPET,A75_1_HOISULU,A75_6_YUFONIUMU,A75_2_LETS,A75_13_1_2_PAPAN,A75_9_TORONBON,
		A75_9_7_TORONBON,A75_8_7_ODAIKO,A75_5_7_HORUN,A75_10_KODAIKO,A75_13_DURU,A75_7_SYMBLE,A75_2_GANBATE;
	
	private int intNotes1[] = {Vol3DonsukaResource.A75_11_1_IPHONE_ID,
			Vol3DonsukaResource.A75_11_2_IPHONE_ID,
			Vol3DonsukaResource.A75_11_3_IPHONE_ID,
			Vol3DonsukaResource.A75_11_4_IPHONE_ID,
			Vol3DonsukaResource.A75_11_5_IPHONE_ID,
			Vol3DonsukaResource.A75_11_2_IPHONE_ID
	};
	private int intNotes2[] = {
			Vol3DonsukaResource.A75_12_6_IPHONE_ID,
			Vol3DonsukaResource.A75_12_3_IPHONE_ID,
			Vol3DonsukaResource.A75_12_1_IPHONE_ID,
			Vol3DonsukaResource.A75_12_4_IPHONE_ID,
			Vol3DonsukaResource.A75_12_2_IPHONE_ID,
			Vol3DonsukaResource.A75_12_5_IPHONE_ID
			
	};
	private int intInstrument[] = {
			Vol3DonsukaResource.A75_1_8_IPHONE_ID,
			Vol3DonsukaResource.A75_2_9_IPHONE_ID,
			Vol3DonsukaResource.A75_3_7_IPHONE_ID,
			Vol3DonsukaResource.A75_4_7_IPHONE_ID,
			Vol3DonsukaResource.A75_5_7_IPHONE_ID,
			Vol3DonsukaResource.A75_6_7_IPHONE_ID,
			Vol3DonsukaResource.A75_7_8_IPHONE_ID,
			Vol3DonsukaResource.A75_8_7_IPHONE_ID,
			Vol3DonsukaResource.A75_9_7_IPHONE_ID,
			Vol3DonsukaResource.A75_10_8_IPHONE_ID
			
	};
	private float arrPointInstrument[][] = {{416, 478},{731, 489},{83, 467},
			{260, 361}, {381 ,183}, {532, 328}, {97,275}, {151,68},{679,138},{551, 117}};
	private float arrPointMusicians[][] ={ {346,330},{668,380},{52,350},
			{210,268},{373,118},{523,230},{51,188},{144,13},{668,56},{496,0}
	};
	private float arrPointer6[][] = {
			{61,156,177,154,19,31,58},{114,216,244,277,168,131,112}};
	private float arrPoint9[][]= {{124,205,203,230,213,148,139,118,119,30,36,106,125,125},
			{110,104,157,185,205,171,189,192,272,279,152,152,134,113}};
	private float arrPoint4[][] = {{21,27,36,48,118,136,186,181,121,116,63,34,22},
			{116,88,87,104,122,147,151,265,259,185,154,154,144}};
	private float arrPointNotes1[][]= {{806, 400},{809, 395},{808, 410},{793, 400},{788, 410}, {801, 400}};
	private float arrPointNotes2[][]= {{133, 420},{127, 400},{138, 445},{120, 420},{115, 435}, {128, 420}};
	private float arrPointNotes3[][]= {{315, 361},{310, 341},{315, 381},{301, 361},{297, 361}, {301, 361}};
	private float arrPointNotes4[][]= {{401, 250},{396, 230},{401, 270},{389, 250},{403, 250}, {411, 250}};
	private float arrPointNotes5[][]= {{542, 330},{532, 310},{542, 350},{517, 330},{527, 330}, {532, 330}};
	private float arrPointNotes7[][]= {{226, 90}, {221, 70}, {226, 110}, {224, 90}, {226, 90}, {221, 90}};
	private float arrPointNotes8[][]= {{881, 145}, {876, 125}, {881, 165}, {869, 145}, {864, 145}, {876, 145}};
	private float arrPointNotes9[][]= {{653, 140},{646, 120},{653, 160},{641, 140},{636, 140}, {648, 140}};
	private float pointTopPaper[]= {-160, 0, 160, 320};
	private boolean istouchGimmic,isTouchBg, checkTouchInstrument, touchInstrument, isTouchWin, isTouchBgGimmic;
	private boolean arrtouchMusicians[]= {false,false,false,false,false,false,false,false, false, false};
	private boolean arrtouchInstrument[]= {false,false,false,false,false,false,false,false, false, false};
	
	private void initial() {
		istouchGimmic = true;
		isTouchBg = true;
		checkTouchInstrument = true;
		touchInstrument = true;
		isTouchBgGimmic = true;
		isTouchWin=false;
		countSound=0;
		for (int i = 0; i < arrtouchMusicians.length; i++) {
			arrtouchMusicians[i] = false;
			arrtouchInstrument[i] = false;
		}
	}
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("donsuka/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("donsuka/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "donsuka/gfx/");
		super.onCreateResources();
	}
	
	
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		ttpDonsuka1 = mTexturePackLoaderFromSource.load("donsuka1.xml");
		ttpDonsuka1.loadTexture();
		ttpLibDonsuka1 = ttpDonsuka1.getTexturePackTextureRegionLibrary();
		
		ttpDonsuka2 = mTexturePackLoaderFromSource.load("donsuka2.xml");
		ttpDonsuka2.loadTexture();
		
		ttpDonsuka3 = mTexturePackLoaderFromSource.load("donsuka3.xml");
		ttpDonsuka3.loadTexture();
		
		ttpDonsuka4 = mTexturePackLoaderFromSource.load("donsuka4.xml");
		ttpDonsuka4.loadTexture();
		
		ttpDonsuka5 = mTexturePackLoaderFromSource.load("donsuka5.xml");
		ttpDonsuka5.loadTexture();
		ttpLibDonsuka5 = ttpDonsuka5.getTexturePackTextureRegionLibrary();
		
		trGimmic = ttpLibDonsuka5.get(Vol3DonsukaResource.A76_DONSUKA_SE3_IPHONE_ID);
		
		trSky = ttpLibDonsuka1.get(Vol3DonsukaResource.A75_13_1_3_IPHONE_ID);
		
		trBall = ttpLibDonsuka5.get(Vol3DonsukaResource.A75_13_2_2_IPHONE_ID);
		trRainbow = ttpLibDonsuka5.get(Vol3DonsukaResource.A75_13_1_2_IPHONE_ID);
		
		ttrBackground = getTiledTextureFromPacker(ttpDonsuka1, Vol3DonsukaResource.A75_13_1_1_IPHONE_ID,
				Vol3DonsukaResource.A75_13_2_1_IPHONE_ID);
		trPaper = ttpLibDonsuka5.get(Vol3DonsukaResource.A75_14_IPHONE_ID);
		for (int i = 0; i < txrNotes1.length; i++) {
			txrNotes1[i]=ttpLibDonsuka5.get(intNotes1[i]);
		}
		for (int i = 0; i < txrNotes2.length; i++) {
			txrNotes2[i]=ttpLibDonsuka5.get(intNotes2[i]);
		}
		ttrMusicians[0]=getTiledTextureFromPacker(ttpDonsuka2, Vol3DonsukaResource.A75_1_1_IPHONE_ID,
				Vol3DonsukaResource.A75_1_2_IPHONE_ID,Vol3DonsukaResource.A75_1_3_IPHONE_ID,
				Vol3DonsukaResource.A75_1_4_IPHONE_ID,Vol3DonsukaResource.A75_1_5_IPHONE_ID,
				Vol3DonsukaResource.A75_1_6_IPHONE_ID,Vol3DonsukaResource.A75_1_7_IPHONE_ID);
		
		ttrMusicians[1]=getTiledTextureFromPacker(ttpDonsuka2, Vol3DonsukaResource.A75_2_1_IPHONE_ID,
				Vol3DonsukaResource.A75_2_2_IPHONE_ID,Vol3DonsukaResource.A75_2_3_IPHONE_ID,
				Vol3DonsukaResource.A75_2_4_IPHONE_ID,Vol3DonsukaResource.A75_2_5_IPHONE_ID,
				Vol3DonsukaResource.A75_2_6_IPHONE_ID,Vol3DonsukaResource.A75_2_7_IPHONE_ID,
				Vol3DonsukaResource.A75_2_8_IPHONE_ID);
		ttrMusicians[2]=getTiledTextureFromPacker(ttpDonsuka2, Vol3DonsukaResource.A75_3_1_IPHONE_ID,
				Vol3DonsukaResource.A75_3_2_IPHONE_ID,Vol3DonsukaResource.A75_3_3_IPHONE_ID,
				Vol3DonsukaResource.A75_3_4_IPHONE_ID,Vol3DonsukaResource.A75_3_5_IPHONE_ID,
				Vol3DonsukaResource.A75_3_6_IPHONE_ID);
		//------------------------------
		ttrMusicians[3]=getTiledTextureFromPacker(ttpDonsuka3, Vol3DonsukaResource.A75_4_1_IPHONE_ID,
				Vol3DonsukaResource.A75_4_2_IPHONE_ID,Vol3DonsukaResource.A75_4_3_IPHONE_ID,
				Vol3DonsukaResource.A75_4_4_IPHONE_ID,Vol3DonsukaResource.A75_4_5_IPHONE_ID,
				Vol3DonsukaResource.A75_4_6_IPHONE_ID);
		
		ttrMusicians[4]=getTiledTextureFromPacker(ttpDonsuka3, Vol3DonsukaResource.A75_5_1_IPHONE_ID,
				Vol3DonsukaResource.A75_5_2_IPHONE_ID,Vol3DonsukaResource.A75_5_3_IPHONE_ID,
				Vol3DonsukaResource.A75_5_4_IPHONE_ID,Vol3DonsukaResource.A75_5_5_IPHONE_ID,
				Vol3DonsukaResource.A75_5_6_IPHONE_ID);
		
		ttrMusicians[5]=getTiledTextureFromPacker(ttpDonsuka3, Vol3DonsukaResource.A75_6_1_IPHONE_ID,
				Vol3DonsukaResource.A75_6_2_IPHONE_ID,Vol3DonsukaResource.A75_6_3_IPHONE_ID,
				Vol3DonsukaResource.A75_6_4_IPHONE_ID,Vol3DonsukaResource.A75_6_5_IPHONE_ID,
				Vol3DonsukaResource.A75_6_6_IPHONE_ID);
		
		ttrMusicians[6]=getTiledTextureFromPacker(ttpDonsuka3, Vol3DonsukaResource.A75_7_1_IPHONE_ID,
				Vol3DonsukaResource.A75_7_2_IPHONE_ID,Vol3DonsukaResource.A75_7_3_IPHONE_ID,
				Vol3DonsukaResource.A75_7_4_IPHONE_ID,Vol3DonsukaResource.A75_7_5_IPHONE_ID,
				Vol3DonsukaResource.A75_7_6_IPHONE_ID,Vol3DonsukaResource.A75_7_7_IPHONE_ID);
		
		ttrMusicians[7]=getTiledTextureFromPacker(ttpDonsuka4, Vol3DonsukaResource.A75_8_1_IPHONE_ID,
				Vol3DonsukaResource.A75_8_2_IPHONE_ID,Vol3DonsukaResource.A75_8_3_IPHONE_ID,
				Vol3DonsukaResource.A75_8_4_IPHONE_ID,Vol3DonsukaResource.A75_8_5_IPHONE_ID,
				Vol3DonsukaResource.A75_8_6_IPHONE_ID);
		
		ttrMusicians[8]=getTiledTextureFromPacker(ttpDonsuka4, Vol3DonsukaResource.A75_9_1_IPHONE_ID,
				Vol3DonsukaResource.A75_9_2_IPHONE_ID,Vol3DonsukaResource.A75_9_3_IPHONE_ID,
				Vol3DonsukaResource.A75_9_4_IPHONE_ID,Vol3DonsukaResource.A75_9_5_IPHONE_ID,
				Vol3DonsukaResource.A75_9_6_IPHONE_ID);
		
		ttrMusicians[9]=getTiledTextureFromPacker(ttpDonsuka4, Vol3DonsukaResource.A75_10_1_IPHONE_ID,
				Vol3DonsukaResource.A75_10_2_IPHONE_ID,Vol3DonsukaResource.A75_10_3_IPHONE_ID,
				Vol3DonsukaResource.A75_10_4_IPHONE_ID,Vol3DonsukaResource.A75_10_5_IPHONE_ID,
				Vol3DonsukaResource.A75_10_6_IPHONE_ID,Vol3DonsukaResource.A75_10_7_IPHONE_ID);
		
		for (int i = 0; i < txrInstrument.length; i++) {
			txrInstrument[i] = ttpLibDonsuka5.get(intInstrument[i]);
		}
		
	}
	
	

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		sSky = new Sprite(0, 0, trSky, this.getVertexBufferObjectManager());
		mScene.attachChild(sSky);
		
		sRainbow = new Sprite(329,7, trRainbow, this.getVertexBufferObjectManager());
		mScene.attachChild(sRainbow);
		sRainbow.setVisible(false);
		aniBackground = new AnimatedSprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBackground);
		
		for (int i = aniMusicians.length-1; i >=0 ; i--) {
			
			aniMusicians[i] = new AnimatedSprite(arrPointMusicians[i][0], arrPointMusicians[i][1],
					ttrMusicians[i], this.getVertexBufferObjectManager());
			mScene.attachChild(aniMusicians[i]);
		}
		
		for (int k = 0; k < sNotes1.length; k++) {
			sNotes1[k] = new Sprite(arrPointNotes1[k][0],arrPointNotes1[k][1], txrNotes2[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes1[k]);
			sNotes1[k].setVisible(false);
		}
		for (int k = 0; k < sNotes2.length; k++) {
			sNotes2[k] = new Sprite(arrPointNotes2[k][0],arrPointNotes2[k][1], txrNotes2[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes2[k]);
			sNotes2[k].setVisible(false);
		}
		for (int k = 0; k < sNotes3.length; k++) {
			sNotes3[k] = new Sprite(arrPointNotes3[k][0],arrPointNotes3[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes3[k]);
			sNotes3[k].setVisible(false);
		}
		for (int k = 0; k < sNotes4.length; k++) {
			sNotes4[k] = new Sprite(arrPointNotes4[k][0],arrPointNotes4[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes4[k]);
			sNotes4[k].setVisible(false);
		}
		
		for (int k = 0; k < sNotes5.length; k++) {
			sNotes5[k] = new Sprite(arrPointNotes5[k][0],arrPointNotes5[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes5[k]);
			sNotes5[k].setVisible(false);
		}
		for (int k = 0; k < sNotes7.length; k++) {
			sNotes7[k] = new Sprite(arrPointNotes7[k][0],arrPointNotes7[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes7[k]);
			sNotes7[k].setVisible(false);
		}
		for (int k = 0; k < sNotes8.length; k++) {
			sNotes8[k] = new Sprite(arrPointNotes8[k][0],arrPointNotes8[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes8[k]);
			sNotes8[k].setVisible(false);
		}
		for (int k = 0; k < sNotes9.length; k++) {
			sNotes9[k] = new Sprite(arrPointNotes9[k][0],arrPointNotes9[k][1], txrNotes1[k], this.getVertexBufferObjectManager());
			mScene.attachChild(sNotes9[k]);
			sNotes9[k].setVisible(false);
			
		}
		for (int j = 0; j < sInstrument.length; j++) {
			
			sInstrument[j] = new Sprite(arrPointInstrument[j][0], arrPointInstrument[j][1], 
						txrInstrument[j], this.getVertexBufferObjectManager());
			
			mScene.attachChild(sInstrument[j]);
			sInstrument[j].setVisible(false);
		}
		sBall = new Sprite(0, 580, trBall, this.getVertexBufferObjectManager());
		mScene.attachChild(sBall);
		sBall.setVisible(false);
		
		for (int i = 0; i < sPaper.length; i++) {
			sPaper[i] = new Sprite(0, pointTopPaper[i], trPaper, this.getVertexBufferObjectManager());
			mScene.attachChild(sPaper[i]);
			sPaper[i].setVisible(false);
		}
		
		sGimmic = new Sprite(582, 516, trGimmic, this.getVertexBufferObjectManager());
		mScene.attachChild(sGimmic);
		
		Log.d(TAG, "loadKaraokeScene end: ");
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		
	}
	
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		super.onPauseGame();
		stopSound();
		resetData();
	}
	
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		initial();
		animateMusicians();
		if (aniBackground.getCurrentTileIndex()==1) {
			aniBackground.setCurrentTileIndex(0);
		}
		super.onResumeGame();
	}
	public void resetData() {
		if (aniBackground!=null) {
			aniBackground.setCurrentTileIndex(0);
		}
		if (sBall!=null) {
			sBall.clearEntityModifiers();
			setvisiableSprite(false, sBall,sRainbow);
		}
		for (int i = 0; i < aniMusicians.length; i++) {
			aniMusicians[i].clearEntityModifiers();
			aniMusicians[i].stopAnimation();
		}
		for (int i = 0; i < sNotes1.length; i++) {
			sNotes1[i].clearEntityModifiers();
			sNotes2[i].clearEntityModifiers();
			sNotes3[i].clearEntityModifiers();
			sNotes4[i].clearEntityModifiers();
			sNotes5[i].clearEntityModifiers();
			sNotes7[i].clearEntityModifiers();
			sNotes8[i].clearEntityModifiers();
			sNotes9[i].clearEntityModifiers();
			setvisiableSprite(false, sNotes1[i],sNotes2[i],sNotes3[i],sNotes4[i],sNotes5[i],sNotes7[i], sNotes8[i], sNotes9[i]);
		}
		for (int i = 0; i < sInstrument.length; i++) {
			sInstrument[i].clearEntityModifiers();
			sInstrument[i].setScale(0);
			setvisiableSprite(false, sInstrument[i]);
		}
		for (int i = 0; i < sPaper.length; i++) {
			sPaper[i].clearEntityModifiers();
			setvisiableSprite(false, sPaper[i]);
		}
		mScene.clearUpdateHandlers();
		
	}
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (isTouchWin) {
			return false;
		}
		if (pSceneTouchEvent.isActionDown()) {
			
			if (sGimmic.contains(pX, pY) && istouchGimmic) {
				istouchGimmic = false;
				isTouchBgGimmic = false;
				checkTouchInstrument=false;
				for (int i = 0; i < arrtouchMusicians.length; i++) {
					arrtouchMusicians[i]=true;
				}
				touchGimmic();
			} else if(checkContains(aniMusicians[0], 85, 42, 172, 150, pX, pY)&&!arrtouchMusicians[0]) {
					//tap vao mat
				Log.d(TAG, "Tap mat aniMusicians[0]: ");
				isTouchBg = false;
				arrtouchMusicians[0]=true;
				touchFaceMusicians(0);
				
			} else if(checkContains(aniMusicians[0], 85, 150, 181, 297, pX, pY)
						&& touchInstrument&& !arrtouchMusicians[0]&&!sInstrument[0].isVisible()){
					//tap vao nhac cu
				arrtouchInstrument[0]=true;
				isTouchBg = false;
				A75_1_8_HOISULU.play();
				touchInstrument(0);
			} else if (checkContains(aniMusicians[1], 65, 33, 166, 129, pX, pY)&&!arrtouchMusicians[1]) {
					//tap vao mat
				Log.d(TAG, "Tap mat aniMusicians[0]: ");
					arrtouchMusicians[1]=true;
					isTouchBg = false;
					touchFaceMusicians(1);
			}else if (checkContains(aniMusicians[1], 27, 132, 206, 260, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[1]&&!sInstrument[1].isVisible()) {
					//tap vao nhac cu
					arrtouchInstrument[1]=true;
					isTouchBg = false;
					A75_2_9_PON.play();
					touchInstrument(1);
			} else if (checkContains(aniMusicians[2], 87, 57, 197, 150, pX, pY)&&!arrtouchMusicians[2]) {
					arrtouchMusicians[2]=true;
					isTouchBg = false;
					touchFaceMusicians(2);
			}else if (checkContains(aniMusicians[2], 4, 178, 180, 275, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[2]&&!sInstrument[2].isVisible()) {
					arrtouchInstrument[2]=true;
					isTouchBg = false;
					A75_3_7_FLAG.play();
					touchInstrument(2);
			}else if (checkContains(aniMusicians[3], 126, 25, 207, 148, pX, pY)&&!arrtouchMusicians[3]) {
					arrtouchMusicians[3]=true;
					isTouchBg = false;
					touchFaceMusicians(3);
			}else if (checkContainsPolygon(aniMusicians[3], arrPoint4, 12, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[3]&&!sInstrument[3].isVisible()) {
					//tap vao nhac cu
					arrtouchInstrument[3]=true;
					isTouchBg = false;
					A75_4_7_TRUMPET.play();
					touchInstrument(3);
			}else if (checkContains(aniMusicians[4], 55, 22, 163, 116, pX, pY)&&!arrtouchMusicians[4]){
					arrtouchMusicians[4]=true;
					isTouchBg = false;
					touchFaceMusicians(4);
			}else if(checkContains(aniMusicians[4], 55, 118, 173, 217, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[4]&&!sInstrument[4].isVisible()){
					//tap vao nhac cu
					arrtouchInstrument[4]=true;
					isTouchBg = false;
					A75_5_7_HORUN.play();
					touchInstrument(4);
			}else if (checkContains(aniMusicians[5], 95, 80, 196, 176, pX, pY)&&!arrtouchMusicians[5]) {
					arrtouchMusicians[5]=true;
					isTouchBg = false;
					touchFaceMusicians(5);
			}else if (checkContainsPolygon(aniMusicians[5], arrPointer6, 6, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[5]&&!sInstrument[5].isVisible()) {
					//tap vao nhac cu
					arrtouchInstrument[5]=true;
					isTouchBg = false;
					A75_6_7_YUFONIUMU.play();
					touchInstrument(5);
			}else if (checkContains(aniMusicians[6], 20, 12, 128, 97, pX, pY)&&!arrtouchMusicians[6]) {
					arrtouchMusicians[6]=true;
					isTouchBg = false;
					touchFaceMusicians(6);
			}else if(checkContains(aniMusicians[6], 53, 100, 163, 209, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[6]&&!sInstrument[6].isVisible()) {
					//tap vao nhac cu
					arrtouchInstrument[6]=true;
					isTouchBg = false;
					A75_7_8_SYMBLE.play();
					touchInstrument(6);
			}else if (checkContains(aniMusicians[7], 177, 26, 269, 142, pX, pY)&&!arrtouchMusicians[7]) {
					arrtouchMusicians[7]=true;
					isTouchBg = false;
					touchFaceMusicians(7);
			} else if (checkContains(aniMusicians[7], 9, 25, 174, 241, pX, pY) 
					&& touchInstrument && !arrtouchMusicians[7]&& !sInstrument[7].isVisible()){
					//tap vao nhac cu
					arrtouchInstrument[7]=true;
					isTouchBg = false;
					A75_8_7_ODAIKO.play();
					touchInstrument(7);
			}else if (checkContains(aniMusicians[8], 15, 56, 122, 150, pX, pY)&&!arrtouchMusicians[8]){
					arrtouchMusicians[8]=true;
					Log.d(TAG, "tap mat aniMusicians[8] ");
					isTouchBg = false;
					touchFaceMusicians(8);
			}else if (checkContainsPolygon(aniMusicians[8], arrPoint9, 13, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[8]&&!sInstrument[8].isVisible()){
					//tap vao nhac cu
				arrtouchInstrument[8]=true;
				isTouchBg = false;
				A75_9_7_TORONBON.play();
				touchInstrument(8);
			}else if (checkContains(aniMusicians[9], 50, 37, 158, 132, pX, pY)&&!arrtouchMusicians[9]){
				Log.d(TAG, "tap mat aniMusicians[9] ");
				isTouchBg = false;
				arrtouchMusicians[9]=true;
				touchFaceMusicians(9);
			}else if (checkContains(aniMusicians[9], 74, 148, 172, 246, pX, pY)
						&&touchInstrument&&!arrtouchMusicians[9]&&!sInstrument[9].isVisible()){
					//tap vao nhac cu
				arrtouchInstrument[9]=true;
				isTouchBg = false;
				A75_10_8_KODAIKO.play();
				touchInstrument(9);
			}else if(sInstrument[0].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[0].setVisible(false);
			}else if(sInstrument[1].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[1].setVisible(false);
			}else if(sInstrument[2].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[2].setVisible(false);
			}else if(sInstrument[3].contains(pX, pY)&&checkTouchInstrument&& sInstrument[3].isVisible()) {
					sInstrument[3].setVisible(false);
			}else if(sInstrument[4].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[4].setVisible(false);
			}else if(sInstrument[5].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[5].setVisible(false);
			}else if(sInstrument[6].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[6].setVisible(false);
			}else if(sInstrument[7].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[7].setVisible(false);
			}else if(sInstrument[8].contains(pX, pY)&&checkTouchInstrument && sInstrument[8].isVisible()) {
					sInstrument[8].setVisible(false);
			}else if(sInstrument[9].contains(pX, pY)&&checkTouchInstrument) {
					sInstrument[9].setVisible(false);
			}else if (aniBackground.contains(pX, pY)&&isTouchBg&&isTouchBgGimmic) {
				Log.d(TAG, "Tap background : ");
				touchBackGround();
			}
			
		}
		return true;
	}
	
	public void touchFaceMusicians(int id) {
		//tap mat
		for (int i = 0; i < sInstrument.length; i++) {
			sInstrument[i].setVisible(false);
		}
		switch (id) {
		case 0:
			A75_1_HOISULU.play();
			long pDuration0[]={300,300,300};
			int pFrame0[]={2,3,4};
			animateMusicians(pDuration0,pFrame0,0);
			break;
		case 1:
			if (countSound==0) {
				countSound =1;
				A75_2_LETS.play();
			}else if(countSound==1) {
				countSound=0;
				A75_2_GANBATE.play();
			}
			moveSpriteNotes(sNotes1,0.8f,0.0f,0.3f,0.5f,0.7f);
			
			long pDuration1[]={300,300,300,300};
			int pFrame1[]={2,3,4,5};
			animateMusicians(pDuration1,pFrame1,1);
			break;
		case 2:
			A75_3_GO.play();
			long pDuration2[]={400,400,400,400};
			int pFrame2[]={2,3,2,3};
			moveSpriteNotes(sNotes2,0.9f,0.0f,0.45f,0.65f,0.8f);
			animateMusicians(pDuration2,pFrame2,2);
			break;
		case 3:
			A75_4_TRUMPET.play();
			long pDuration3[]={400,400,400,500};
			int pFrame3[]={2,3,2,3};
			moveSpriteNotes(sNotes3,0.9f, 0.0f, 0.3f, 0.5f, 0.8f);
			animateMusicians(pDuration3,pFrame3,3);
			break;
		case 4:
			A75_5_HORUN.play();
			long pDuration4[]={600,600};
			int pFrame4[]={2,3};
			moveSpriteNotes(sNotes4,0.6f,0.0f,0.2f,0.4f,0.6f);
			animateMusicians(pDuration4,pFrame4,4);
			break;
		case 5:
			A75_6_YUFONIUMU.play();
			long pDuration5[]={500, 600, 500, 800};
			int pFrame5[]={2,3,2,3};
			moveSpriteNotes(sNotes5,0.8f,0.0f,0.75f,0.95f,1.2f);
			animateMusicians(pDuration5,pFrame5,5);
			break;
		case 6:
			A75_7_SYMBLE.play();
			long pDuration6[]={200, 300, 400};
			int pFrame6[]={2,3,4};
			animateMusicians(pDuration6,pFrame6,6);
			break;
		case 7:
			A75_8_ODAIKO.play();
			long pDuration7[]={280, 800};
			int pFrame7[]={3,2};
			moveSpriteNotes(sNotes7,0.4f,0.28f,0.35f,0.35f,0.45f);
			animateMusicians(pDuration7,pFrame7, 7);
			break;
		case 8:
			A75_9_TORONBON.play();
			long pDuration8[]={500, 500, 500, 500};
			int pFrame8[]={2,3,2,3};
			moveSpriteNotes(sNotes8,0.7f,0.0f,0.5f,0.8f,1.1f);
			animateMusicians(pDuration8,pFrame8, 8);
			break;
		case 9:
			A75_10_KODAIKO.play();
			long pDuration9[]={250, 250, 250};
			int pFrame9[]={2,3,4};
			moveSpriteNotes(sNotes9,0.6f,0.0f,0.1f,0.15f,0.2f);
			animateMusicians(pDuration9,pFrame9, 9);
			break;
	
		default:
			break;
			
			
		}
	}
	public void animateMusicians(long[] pDuration,int[] pFrame, final int index) {
		aniMusicians[index].stopAnimation();
		aniMusicians[index].animate(pDuration,pFrame,0, new IAnimationListener() {
			
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
				mScene.registerEntityModifier(new DelayModifier(0.3f,new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						aniMusicians[index].animate(new long[]{500,500},new int[]{0,1},-1);
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchBg = true;
						arrtouchMusicians[index]=false;
						
					}
				}));
			}
		});
	}
	public void moveSpriteNotes(final Sprite[] arrSprite, float duration,float timer0, float timer1, float timer2, float timer3) {
		Log.d(TAG, "moveSprite: ");
		arrSprite[0].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer0),
				new ParallelEntityModifier(
				new MoveYModifier(duration, arrSprite[0].getmYFirst(), arrSprite[0].getmYFirst()-90, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[0]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[0]);
						arrSprite[0].setAlpha(1);
					}
				}),
				new AlphaModifier(duration, 1, 0 , EaseStrongIn.getInstance()))));
		
		arrSprite[1].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer0),
				new ParallelEntityModifier(
				new AlphaModifier(duration, 1, 0, EaseStrongIn.getInstance()),
				new MoveYModifier(duration, arrSprite[1].getmYFirst(), arrSprite[1].getmYFirst()-90, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[1]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[1]);
						arrSprite[1].setAlpha(1);
					}
				}))));
		arrSprite[2].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer0),
				new ParallelEntityModifier(
				new AlphaModifier(duration, 1, 0, EaseStrongIn.getInstance()),
				new MoveYModifier(duration, arrSprite[2].getmYFirst(), arrSprite[2].getmYFirst()-80, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[2]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[2]);
						arrSprite[2].setAlpha(1);
					}
				}))));
		arrSprite[3].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer1),new ParallelEntityModifier(
				new AlphaModifier(duration, 1, 0, EaseStrongIn.getInstance()),
				new MoveModifier(duration,arrSprite[3].getmXFirst(),arrSprite[3].getmXFirst()-50, 
						arrSprite[3].getmYFirst(), arrSprite[3].getmYFirst()-80, 
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[3]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[3]);
						arrSprite[3].setAlpha(1);
					}
				}))));
		arrSprite[4].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer2),new ParallelEntityModifier(
				new AlphaModifier(duration, 1, 0),
				new MoveYModifier(duration, arrSprite[4].getmYFirst(), arrSprite[4].getmYFirst()-80, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[4]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[4]);
						arrSprite[4].setAlpha(1);
					}
				}))));
		arrSprite[5].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer3),new ParallelEntityModifier(
				new AlphaModifier(duration, 1, 0),
				new MoveModifier(duration, arrSprite[5].getmXFirst(),arrSprite[5].getmXFirst()+40,
						arrSprite[5].getmYFirst(), arrSprite[5].getmYFirst()-70,
						new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(true, arrSprite[5]);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableSprite(false, arrSprite[5]);
						arrSprite[5].setAlpha(1);
					}
				}))));
		
	}
	
	public void touchInstrument(int id){
		//tap vao nhac cu
		for (int i = 0; i < sInstrument.length; i++) {
			sInstrument[i].setVisible(false);
		}
		touchInstrument=false;
		sInstrument[id].setVisible(true);
		sInstrument[id].registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.4f, 0, 1),
				new DelayModifier(0.2f,new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						touchInstrument=true;
						isTouchBg = true;
						if (arrtouchInstrument[0]&& arrtouchInstrument[1]&& arrtouchInstrument[2]&& arrtouchInstrument[3]
						     && arrtouchInstrument[4]&& arrtouchInstrument[5]&& arrtouchInstrument[6]&& arrtouchInstrument[7]
						     && arrtouchInstrument[8]&& arrtouchInstrument[9]) {
							checkTouchInstrument = false;
							istouchGimmic = false;
							isTouchBg = false;
							isTouchWin = true;
							for (int i = 0; i < arrtouchInstrument.length; i++) {
								arrtouchInstrument[i] = true;
							}
							successInstrument();
						}
						
					}
				})));	
		
	}
	public void successInstrument(){
		for (int i = 0; i < sInstrument.length; i++) {
			sInstrument[i].setVisible(false);
		}
		A75_14_OMEDETO.play();
		sPaper[0].setVisible(true);
		sPaper[1].setVisible(true);
		sPaper[2].setVisible(true);
		sPaper[3].setVisible(true);
		movePaper(sPaper[0],5.8f,0.2f);
		movePaper(sPaper[1],5.5f,0.25f);
		movePaper(sPaper[2],5.0f,0.2f);
		movePaper(sPaper[3],4.7f,0.1f);
		
		aniMusicians[0].animate(new long[]{500,500}, new int[]{5,6},4);
		aniMusicians[1].animate(new long[]{500,500}, new int[]{6,7},4);
		aniMusicians[2].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[3].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[4].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[5].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[6].animate(new long[]{500,500}, new int[]{5,6},4);
		aniMusicians[7].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[8].animate(new long[]{500,500}, new int[]{4,5},4);
		aniMusicians[9].animate(new long[]{500,500}, new int[]{5,6},4, new IAnimationListener() {
			
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
				for (int i = 0; i < arrtouchInstrument.length; i++) {
					arrtouchInstrument[i] = false;
				}
				checkTouchInstrument = true;
				istouchGimmic = true;
				isTouchBg = true;
				isTouchWin = false;
				setvisiableSprite(false, sPaper[0],sPaper[1],sPaper[2],sPaper[3]);
				animateMusicians();
				
			}
		});
	}
	public void movePaper(Sprite sprPaper, float duration, float timer) {
		
		sprPaper.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(timer),
				new MoveYModifier(duration, sprPaper.getmYFirst(), 800)));
		
		
	}
	public void animateMusicians() {
		for (int i = 0; i < aniMusicians .length; i++) {
			aniMusicians[i].animate(new long[]{500,500},new int[]{0,1},-1);
		}
	}
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		pAssetManager = getActivity().getAssets();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().getSoundOptions().setMaxSimultaneousStreams(12);
		return engineOptions;
		
	}
	public void touchGimmic() {
		isTouchBgGimmic = false;
		for (int i = 0; i < sInstrument.length; i++) {
			sInstrument[i].setVisible(false);
		}
		sGimmic.registerEntityModifier(new SequenceEntityModifier(
				new ScaleModifier(0.35f, 1, 1.3f),
				new ScaleModifier(0.35f, 1.3f, 1f)));
		if (aniBackground.getCurrentTileIndex()==0) {
			aniBackground.setCurrentTileIndex(1);
			sBall.setVisible(true);
			A75_13_2_2_KYUJO.play();
			sBall.setScale(1.0f);
			sBall.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(
					new MoveModifier(1.2f, sBall.getmXFirst(), mCamera.getWidth()-2*sBall.getWidth(),
					sBall.getmYFirst(), -100), new ScaleModifier(1.2f, 1.1f, 0.6f)), new DelayModifier(2.3f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							
							for (int i = 0; i < aniMusicians.length; i++) {
								touchFaceMusicians(i);
							}
						
							mScene.registerUpdateHandler(new TimerHandler(2.8f, new ITimerCallback() {
								
								@Override
								public void onTimePassed(TimerHandler arg0) {
									setvisiableSprite(false, sBall);
									checkTouchInstrument = true;
									isTouchBgGimmic = true;
									for (int i = 0; i < arrtouchMusicians.length; i++) {
										arrtouchMusicians[i]=false;
									}
									istouchGimmic = true;
								}
							}));
						}
					})));
			
		}else if (aniBackground.getCurrentTileIndex()==1) {
			aniBackground.setCurrentTileIndex(0);
			// xuat hien cau vong ban nhac choi nhac sau do cho tap vao nguoi va background
			A75_13_1_2_PAPAN.play();
			sRainbow.setVisible(true);
			
			sRainbow.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(2.0f),
					new DelayModifier(1.0f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					for (int i = 0; i < aniMusicians.length; i++) {
						touchFaceMusicians(i);
					}
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sRainbow.setVisible(false);
					mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							checkTouchInstrument = true;
							isTouchBgGimmic = true;
							istouchGimmic = true;
							for (int i = 0; i < arrtouchMusicians.length; i++) {
								arrtouchMusicians[i]=false;
							}
							
						}
					}));
					
				}
			})));
			
		}
	}
	public void touchBackGround() {
		
			A75_13_DURU.play();
			if (aniBackground.getCurrentTileIndex()==0) {
				aniBackground.setCurrentTileIndex(1);
			}else if (aniBackground.getCurrentTileIndex()==1) {
				aniBackground.setCurrentTileIndex(0);
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
		// TODO Auto-generated method stub
		A75_5_HORUN = loadSoundResourceFromSD(Vol3DonsukaResource.A75_5_HORUN);
		A75_8_ODAIKO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_8_ODAIKO);
		A75_3_GO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_3_GO);
		A75_13_2_2_KYUJO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_13_2_2_KYUJO);
		A75_4_7_TRUMPET = loadSoundResourceFromSD(Vol3DonsukaResource.A75_4_7_TRUMPET);
		A75_1_8_HOISULU = loadSoundResourceFromSD(Vol3DonsukaResource.A75_1_8_HOISULU);
		A75_10_8_KODAIKO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_10_8_KODAIKO);
		A75_2_9_PON = loadSoundResourceFromSD(Vol3DonsukaResource.A75_2_9_PON);
		A75_14_OMEDETO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_14_OMEDETO);
		A75_7_8_SYMBLE = loadSoundResourceFromSD(Vol3DonsukaResource.A75_7_8_SYMBLE);
		A75_3_7_FLAG = loadSoundResourceFromSD(Vol3DonsukaResource.A75_3_7_FLAG);
		A75_6_7_YUFONIUMU = loadSoundResourceFromSD(Vol3DonsukaResource.A75_6_7_YUFONIUMU);
		A75_4_TRUMPET = loadSoundResourceFromSD(Vol3DonsukaResource.A75_4_TRUMPET);
		A75_1_HOISULU = loadSoundResourceFromSD(Vol3DonsukaResource.A75_1_HOISULU);
		A75_6_YUFONIUMU = loadSoundResourceFromSD(Vol3DonsukaResource.A75_6_YUFONIUMU);
		A75_2_LETS = loadSoundResourceFromSD(Vol3DonsukaResource.A75_2_LETS);
		A75_13_1_2_PAPAN = loadSoundResourceFromSD(Vol3DonsukaResource.A75_13_1_2_PAPAN);
		A75_9_TORONBON = loadSoundResourceFromSD(Vol3DonsukaResource.A75_9_TORONBON);
		A75_9_7_TORONBON = loadSoundResourceFromSD(Vol3DonsukaResource.A75_9_7_TORONBON);
		A75_8_7_ODAIKO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_8_7_ODAIKO);
		A75_5_7_HORUN = loadSoundResourceFromSD(Vol3DonsukaResource.A75_5_7_HORUN);
		A75_10_KODAIKO = loadSoundResourceFromSD(Vol3DonsukaResource.A75_10_KODAIKO);
		A75_13_DURU = loadSoundResourceFromSD(Vol3DonsukaResource.A75_13_DURU);
		A75_7_SYMBLE = loadSoundResourceFromSD(Vol3DonsukaResource.A75_7_SYMBLE);
		A75_2_GANBATE = loadSoundResourceFromSD(Vol3DonsukaResource.A75_2_GANBATE);

	}
	private void stopSound() {
		A75_5_HORUN.stop();
		A75_8_ODAIKO.stop();
		A75_3_GO.stop();
		A75_13_2_2_KYUJO.stop();
		A75_4_7_TRUMPET.stop();
		A75_1_8_HOISULU.stop();
		A75_10_8_KODAIKO.stop();
		A75_2_9_PON.stop();
		A75_14_OMEDETO.stop();
		A75_7_8_SYMBLE.stop();
		A75_3_7_FLAG.stop();
		A75_6_7_YUFONIUMU.stop();
		A75_4_TRUMPET.stop();
		A75_1_HOISULU.stop();
		A75_6_YUFONIUMU.stop();
		A75_2_LETS.stop();
		A75_13_1_2_PAPAN.stop();
		A75_9_TORONBON.stop();
		A75_9_7_TORONBON.stop();
		A75_8_7_ODAIKO.stop();
		A75_5_7_HORUN.stop();
		A75_10_KODAIKO.stop();
		A75_13_DURU.stop();
		A75_7_SYMBLE.stop();
		A75_2_GANBATE.stop();
	}
	
}
