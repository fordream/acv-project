/* Vol3Obake.java
* Create on Jul 31, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ObakeResource;

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
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationAtModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Obake extends BaseGameActivity implements 
	IEntityModifierListener, IAnimationListener, IOnSceneTouchListener {
	
	public final static String TAG = " Vol3Obake "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpObake1, ttpObake2, ttpObake3, ttpObake4;
	private TexturePackTextureRegionLibrary ttpLibObake1, ttpLibObake2, ttpLibObake4;
	
	private ITextureRegion trBoyisu, trBgr2, trBoybazul1, trBoybazul2, trBoybazul3, trGirlbazul1, trGirlbazul2, 
			trGirlbazul3,trGray1, trGray2, trGray3, trPiano1, trPiano2, trBoymobil1, trBoymobil2,trBoymobil3,
			trBoymobil4,trBoymobil5, trGirlmobil1, trGirlmobil2, trGirlmobil3, trGirlmobil4, trGirlmobil5;
	private Sprite sBoyisu, sBgr2, sBoybazul1, sBoybazul2, sBoybazul3, sGirlbazul1, sGirlbazul2, sGirlbazul3,
			sGrayGirl1, sGrayGirl2, sGrayGirl3, sPiano1, sPiano2, sBoymobil1, sBoymobil2, sBoymobil3, sBoymobil4, 
			sBoymobil5, sGirlmobil1, sGirlmobil2, sGirlmobil3, sGirlmobil4, sGirlmobil5,sGrayBoy1, sGrayBoy2,sGrayBoy3;
	private ITextureRegion[] trGimmic = new ITextureRegion[3];
	private Sprite[] sGimmic = new Sprite[3];
	boolean[] touchGimmic = { true, true, true };
	private TiledTextureRegion ttrBgr1, ttrGirlcloest, ttrBoycloest, ttrBoybazul4, ttrGirlbazul4, ttrBoyhikouki, ttrBoyDoor,
			ttrGirlDoor, ttrGhost, ttrObake1, ttrObake2, ttrRobot1, ttrRobot2, ttrTsumiki,
			ttrHime, ttrSteki1, ttrSteki2;
	private TiledTextureRegion arrTtrKobito[] = new TiledTextureRegion[7];
	private AnimatedSprite aniBgr1, aniGirlcloest, aniBoycloest, aniBoybazul4, aniGirlbazul4, aniBoyhikouki, aniTsumiki, 
			aniBoyDoor, aniGirlDoor, aniGhost, aniObake1, aniRobot1, aniRobot2, aniHime, aniSteki1, aniSteki2;
	
	private kobitoAniSprites arrAniKobito[] = new kobitoAniSprites[7];
	
	private AnimatedSprite arrAniObake[] = new AnimatedSprite[7];
	
	private float arrPointKobito[][] = {
			{163,362,458,720,170,318,35},
			{64,315,372,500,491,509,491}
		};
	private Path pathKobito[] = new Path[7];
	private ArrayList<Integer> arlKobito;
	int[] pointGimmic = { 244, 436, 612 };
	private int countGirlBazul, countBoyBazul;
	private Sound A3_2_3_1_FURANKEN, A3_6_BOYON, A3_2_1_1_KUMA,A3_OBAKE_1,
		A3_11_PIANO, A3_6_2_3_RAIDA, A3_9_ROBOT, A3_11_PIANOK, A3_12_HOISUL,
		A3_13_SUTEKI, A3_10_TUMIKI, A3_6_PAZUL,A3_18_SAWA, A3_19_20_TUKITOHOSI,
		A3_8_HIKOUKI, A3_14_BYON, A3_7_KUKIODORU, A3_14_KOBITO, A3_2_2_1_DORAQURA,
		A3_LITE, A3_14_KOBITO_OK,A3_4_KOWAI_BOY, A3_OBAKE_2,A3_4_KYA_GIRL, A3_5_OBAKE,
		A3_4_DOASHIME, A3_HATENA, A3_6_PAZULHAMARU, A3_11_PIANOB, A3_2_TANCESHIME;
	
	private boolean checkRoom, checkMoveBoy1,checkMoveBoy2, checkMoveBoy3, touchBazul1, touchBazul2,touchGirlBazul1,
			touchGirlBazul2,touchGirlBazul3,touchBazul3, checkMoveGirl1,checkMoveGirl2,checkMoveGirl3, touchSteki, 
			touchCloest, touchDoor, touchPinao,touchGirlMobil, touchTsumiki, touchRobot, touchIsu, touchHikouki, 
			touchLight, touchKobito, touchHime,touchGirlCloest;
	
	private void initial () {
		checkMoveBoy1 = checkMoveBoy2 = checkMoveBoy3 = false;
		checkMoveGirl1 = checkMoveGirl2 = checkMoveGirl3 = false;
		touchGirlBazul1 = touchGirlBazul2 = touchGirlBazul3 = true;
		touchBazul1 = touchBazul2 = touchBazul3 = touchRobot = true;
		checkRoom = touchSteki = touchPinao = touchTsumiki = true;
		touchCloest = touchDoor = touchGirlMobil = touchIsu = true;
		touchHikouki = touchLight= touchKobito = touchHime =  true;
		touchGirlCloest= true;
		countGirlBazul = 0; 
		countBoyBazul = 0;
	}
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("obake/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("obake/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "obake/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		ttpObake1 = mTexturePackLoaderFromSource.load("obake1.xml");
		ttpObake1.loadTexture();
		ttpLibObake1 = ttpObake1.getTexturePackTextureRegionLibrary();
		
		ttpObake2 = mTexturePackLoaderFromSource.load("obake2.xml");
		ttpObake2.loadTexture();
		ttpLibObake2 = ttpObake2.getTexturePackTextureRegionLibrary();
		
		ttpObake3 = mTexturePackLoaderFromSource.load("obake3.xml");
		ttpObake3.loadTexture();
		
		ttpObake4 = mTexturePackLoaderFromSource.load("obake4.xml");
		ttpObake4.loadTexture();
		ttpLibObake4 = ttpObake4.getTexturePackTextureRegionLibrary();
		
		//---------load background-----------
		ttrBgr1 = getTiledTextureFromPacker(ttpObake4,
				Vol3ObakeResource.A3_16_IPHONE_GIRL_HAIKEI_ID,
				Vol3ObakeResource.A3_15_IPHONE_BOY_HAIKEI_ID);
		trBgr2 = ttpLibObake4.get(Vol3ObakeResource.A3_17_IPHONE_KURAI_HAIKEI_ID);
		//-----------------load pazul------------------
		trBoybazul1 = ttpLibObake2.get(Vol3ObakeResource.A3_6_1_1_IPHONE_BOY_PAZUL_ID);
		trBoybazul2 = ttpLibObake2.get(Vol3ObakeResource.A3_6_1_2_IPHONE_BOY_PAZUL_ID);
		trBoybazul3 = ttpLibObake2.get(Vol3ObakeResource.A3_6_1_3_IPHONE_BOY_PAZUL_ID);
		
		trGirlbazul1 = ttpLibObake2.get(Vol3ObakeResource.A3_7_1_1_IPHONE_GIRL_PAZUL_ID);
		trGirlbazul2 = ttpLibObake2.get(Vol3ObakeResource.A3_7_1_2_IPHONE_GIRL_PAZUL_ID);
		trGirlbazul3 = ttpLibObake2.get(Vol3ObakeResource.A3_7_1_3_IPHONE_GIRL_PAZUL_ID);
		
		trGray1 = ttpLibObake1.get(Vol3ObakeResource.A3_21_1_IPHONE_GRAY_ID);
		trGray2 = ttpLibObake1.get(Vol3ObakeResource.A3_21_2_IPHONE_GRAY_ID);
		trGray3 = ttpLibObake1.get(Vol3ObakeResource.A3_21_3_IPHONE_GRAY_ID);
		
		ttrBoybazul4 = getTiledTextureFromPacker(ttpObake3,
						Vol3ObakeResource.A3_6_2_1_IPHONE_BOY_PAZUL_ID,
						Vol3ObakeResource.A3_6_2_2_IPHONE_BOY_PAZUL_ID,
						Vol3ObakeResource.A3_6_2_3_IPHONE_BOY_PAZUL_ID);
		
		ttrGirlbazul4 = getTiledTextureFromPacker(ttpObake3,
						Vol3ObakeResource.A3_7_2_1_IPHONE_GIRL_PAZUL_ID,
						Vol3ObakeResource.A3_7_2_2_IPHONE_GIRL_PAZUL_ID,
						Vol3ObakeResource.A3_7_2_3_IPHONE_GIRL_PAZUL_ID,
						Vol3ObakeResource.A3_7_2_4_IPHONE_GIRL_PAZUL_ID);
		//---------------load door--------------------
		ttrBoyDoor = getTiledTextureFromPacker(ttpObake3,
				Vol3ObakeResource.A3_4_1_IPHONE_BOY_DOOR_ID,
				Vol3ObakeResource.A3_4_2_IPHONE_BOY_DOOR_ID,
				Vol3ObakeResource.A3_4_3_IPHONE_BOY_DOOR_ID);
		
		ttrGirlDoor = getTiledTextureFromPacker(ttpObake1,
				Vol3ObakeResource.A3_3_1_IPHONE_GIRL_DOOR_ID,
				Vol3ObakeResource.A3_3_2_IPHONE_GIRL_DOOR_ID,
				Vol3ObakeResource.A3_3_3_IPHONE_GIRL_DOOR_ID);
		
		//---------------load hikouki------------
		ttrBoyhikouki = getTiledTextureFromPacker(ttpObake2,
				Vol3ObakeResource.A3_8_1_IPHONE_BOY_HIKOUKI_ID,
				Vol3ObakeResource.A3_8_2_IPHONE_BOY_HIKOUKI_ID,
				Vol3ObakeResource.A3_8_3_IPHONE_BOY_HIKOUKI_ID);
		//------------------load cloest----------------
		ttrGirlcloest = getTiledTextureFromPacker(ttpObake1,
				Vol3ObakeResource.A3_1_1_1_IPHONE_GIRL_CLOSET_ID,
				Vol3ObakeResource.A3_1_1_2_IPHONE_GIRL_CLOSET_ID,
				Vol3ObakeResource.A3_1_1_3_IPHONE_GIRL_CLOSET_ID);
		ttrBoycloest = getTiledTextureFromPacker(ttpObake1,
				Vol3ObakeResource.A3_1_2_1_IPHONE_BOY_CLOSET_ID,
				Vol3ObakeResource.A3_1_2_2_IPHONE_BOY_CLOSET_ID,
				Vol3ObakeResource.A3_1_2_3_IPHONE_BOY_CLOSET_ID);
		//------------------load ghost-------------
		
		ttrGhost = getTiledTextureFromPacker(ttpObake2,
				Vol3ObakeResource.A3_2_1_2_IPHONE_A_CLOSET_ID,
				Vol3ObakeResource.A3_2_1_1_IPHONE_A_CLOSET_ID,
				Vol3ObakeResource.A3_2_1_3_IPHONE_A_CLOSET_ID,
				Vol3ObakeResource.A3_2_2_2_IPHONE_B_CLOSET_ID,
				Vol3ObakeResource.A3_2_2_1_IPHONE_B_CLOSET_ID,
				Vol3ObakeResource.A3_2_2_3_IPHONE_B_CLOSET_ID,
				Vol3ObakeResource.A3_2_3_2_IPHONE_C_CLOSET_ID,
				Vol3ObakeResource.A3_2_3_1_IPHONE_C_CLOSET_ID,
				Vol3ObakeResource.A3_2_3_3_IPHONE_C_CLOSET_ID);

		//--------------load obake------------------
		ttrObake1 = getTiledTextureFromPacker(ttpObake3, 
				Vol3ObakeResource.A3_5_1_IPHONE_OBAKE_ID,
				Vol3ObakeResource.A3_5_2_IPHONE_OBAKE_ID);
		ttrObake2 = getTiledTextureFromPacker(ttpObake3, 
				Vol3ObakeResource.A3_5_3_IPHONE_OBAKE_ID,
				Vol3ObakeResource.A3_5_4_IPHONE_OBAKE_ID);
		//----------load robot----------------
		ttrRobot1 = getTiledTextureFromPacker(ttpObake3, 
				Vol3ObakeResource.A3_9_1_IPHONE_BOY_ROBOT_ID,
				Vol3ObakeResource.A3_9_2_IPHONE_BOY_ROBOT_ID);
		ttrRobot2 = getTiledTextureFromPacker(ttpObake3, 
				Vol3ObakeResource.A3_9_6_IPHONE_BOY_ROBOT_ID,
				Vol3ObakeResource.A3_9_5_IPHONE_BOY_ROBOT_ID,
				Vol3ObakeResource.A3_9_3_IPHONE_BOY_ROBOT_ID,
				Vol3ObakeResource.A3_9_4_IPHONE_BOY_ROBOT_ID);
		//-----------------load tsumiki------------------
		
		ttrTsumiki = getTiledTextureFromPacker(ttpObake2, 
				Vol3ObakeResource.A3_10_1_IPHONE_TSUMIKI_ID,
				Vol3ObakeResource.A3_10_2_IPHONE_TSUMIKI_ID,
				Vol3ObakeResource.A3_10_3_IPHONE_TSUMIKI_ID);
		//---------------------load piano
		trPiano1 = ttpLibObake2.get(Vol3ObakeResource.A3_11_1_IPHONE_GIRL_PIANO_ID);
		trPiano2 = ttpLibObake2.get(Vol3ObakeResource.A3_11_2_IPHONE_GIRL_PIANO_ID);
		//----------------load hime-----------------
		ttrHime = getTiledTextureFromPacker(ttpObake1, 
				Vol3ObakeResource.A3_12_1_IPHONE_HIME_ID,
				Vol3ObakeResource.A3_12_2_IPHONE_HIME_ID);
		//---------------load steki----------------
		ttrSteki1 = getTiledTextureFromPacker(ttpObake1, 
				Vol3ObakeResource.A3_13_1_IPHONE_STEKI_ID,
				Vol3ObakeResource.A3_13_2_IPHONE_STEKI_ID,
				Vol3ObakeResource.A3_13_3_IPHONE_STEKI_ID,
				Vol3ObakeResource.A3_13_4_IPHONE_STEKI_ID);
		ttrSteki2 = getTiledTextureFromPacker(ttpObake1, 
				Vol3ObakeResource.A3_13_5_IPHONE_STEKI_ID,
				Vol3ObakeResource.A3_13_6_IPHONE_STEKI_ID);
		//-------------load Boy isu----------------
		trBoyisu = ttpLibObake2.get(Vol3ObakeResource.A3_18_IPHONE_BOY_ISU_ID);
		
		//--------------load mobil-------------------------------
		trBoymobil1 = ttpLibObake2.get(Vol3ObakeResource.A3_20_1_IPHONE_BOY_MOBIL_ID);
		trBoymobil2 = ttpLibObake2.get(Vol3ObakeResource.A3_20_2_IPHONE_BOY_MOBIL_ID); 
		trBoymobil3 = ttpLibObake2.get(Vol3ObakeResource.A3_20_3_IPHONE_BOY_MOBIL_ID);
		trBoymobil4 = ttpLibObake2.get(Vol3ObakeResource.A3_20_4_IPHONE_BOY_MOBIL_ID);
		trBoymobil5 = ttpLibObake2.get(Vol3ObakeResource.A3_20_5_IPHONE_BOY_MOBIL_ID);
		
		trGirlmobil1 = ttpLibObake1.get(Vol3ObakeResource.A3_19_1_IPHONE_GIRL_MOBIL_ID);
		trGirlmobil2 = ttpLibObake1.get(Vol3ObakeResource.A3_19_2_IPHONE_BOY_MOBIL_ID);
		trGirlmobil3 = ttpLibObake1.get(Vol3ObakeResource.A3_19_3_IPHONE_GIRL_MOBIL_ID); 
		trGirlmobil4 = ttpLibObake1.get(Vol3ObakeResource.A3_19_4_IPHONE_GIRL_MOBIL_ID);
		trGirlmobil5 = ttpLibObake1.get(Vol3ObakeResource.A3_19_5_IPHONE_GIRL_MOBIL_ID);
		
		//--------------------load kobito-----------------------------
		arrTtrKobito[0] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO1);
		arrTtrKobito[1] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO2);
		arrTtrKobito[2] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO3);
		arrTtrKobito[3] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO4);
		arrTtrKobito[4] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO5);
		arrTtrKobito[5] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO6);
		arrTtrKobito[6] = getTiledTextureFromPacker(ttpObake3, Vol3ObakeResource.PACK_KOBITO7);
		
		trGimmic[0] = ttpLibObake1.get(Vol3ObakeResource.A3_OBAKE_1_IPHONE_ID);
		trGimmic[1] = ttpLibObake1.get(Vol3ObakeResource.A3_OBAKE_2_IPHONE_ID);
		trGimmic[2] = ttpLibObake1.get(Vol3ObakeResource.A3_OBAKE_3_IPHONE_ID);
		//load arlKobito
		arlKobito = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			arlKobito.add(i);
		}

	}

	
	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		aniBgr1 = new AnimatedSprite(0, 0, ttrBgr1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBgr1);
		//--------------------girl room------------------------------
		
		aniGirlcloest = new AnimatedSprite(65, 89, ttrGirlcloest, this.getVertexBufferObjectManager());
		mScene.attachChild(aniGirlcloest);
		
		aniGirlDoor =  new AnimatedSprite(609, 138, ttrGirlDoor, this.getVertexBufferObjectManager());
		mScene.attachChild(aniGirlDoor);
		sPiano1 = new Sprite(556,387, trPiano1, this.getVertexBufferObjectManager());
		mScene.attachChild(sPiano1);
		
		sPiano2 = new Sprite(564,485, trPiano2, this.getVertexBufferObjectManager());
		mScene.attachChild(sPiano2);
		
		sGirlmobil3 = new Sprite(697, 28, trGirlmobil3, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlmobil3);
		
		sGirlmobil4 = new Sprite(726, 75, trGirlmobil4, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlmobil4);
		
		sGirlmobil5 = new Sprite(830, 75, trGirlmobil5, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlmobil5);
		sGirlmobil2 = new Sprite(757, 26, trGirlmobil2, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlmobil2);
		sGirlmobil1 = new Sprite(700,-27, trGirlmobil1, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlmobil1);
		
		sGrayGirl1 = new Sprite(429, 73, trGray1, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayGirl1);
		sGrayGirl2 = new Sprite(393, 181, trGray2, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayGirl2);
		sGrayGirl3 = new Sprite(435, 276, trGray3, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayGirl3);
		setvisiableSprite(false, sGrayGirl1,sGrayGirl2,sGrayGirl3);
		aniSteki1 = new AnimatedSprite(283, 423, ttrSteki1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSteki1);
		aniSteki1.setZIndex(1);
		aniSteki2 = new AnimatedSprite(0, 0, ttrSteki2, this.getVertexBufferObjectManager());
		aniSteki1.attachChild(aniSteki2);
		aniSteki2.setVisible(false);
		sGirlbazul1 = new Sprite(556, 252, trGirlbazul1, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlbazul1); 
		sGirlbazul1.setZIndex(25);
		sGirlbazul2 = new Sprite(235, 26, trGirlbazul2, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlbazul2); 
		sGirlbazul2.setZIndex(25);
		sGirlbazul3 = new Sprite(584, 85, trGirlbazul3, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirlbazul3); 
		sGirlbazul3.setZIndex(25);
		for (int i = 0; i < arrAniKobito.length; i++) {
			arrAniKobito[i] = new kobitoAniSprites(arrPointKobito[0][i], arrPointKobito[1][i],
					arrTtrKobito[i], this.getVertexBufferObjectManager());
			mScene.attachChild(arrAniKobito[i]);
			mScene.registerTouchArea(arrAniKobito[i]);
		}
		aniHime = new AnimatedSprite(26, 440, ttrHime, this.getVertexBufferObjectManager());
		mScene.attachChild(aniHime);
		aniHime.setZIndex(20);
		
		
		
		aniGirlbazul4 = new AnimatedSprite(365, 45, ttrGirlbazul4, this.getVertexBufferObjectManager());
		mScene.attachChild(aniGirlbazul4);
		aniGirlbazul4.setVisible(false);
		aniGirlbazul4.setZIndex(25);
		
		//-------------boy room-------------------------
		aniBoycloest = new AnimatedSprite(65, 89, ttrBoycloest, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoycloest);
		aniBoycloest.setVisible(false);
		aniGhost = new AnimatedSprite(65, 89, ttrGhost, this.getVertexBufferObjectManager());
		mScene.attachChild(aniGhost);
		aniGhost.setVisible(false);
		sBoyisu = new Sprite(600, 363, trBoyisu, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoyisu);
		sBoyisu.setVisible(false);
		
		aniBoyDoor =  new AnimatedSprite(609, 138, ttrBoyDoor, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoyDoor);
		aniBoyDoor.setVisible(false);
		
		sGrayBoy1 = new Sprite(429, 73, trGray1, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayBoy1);
		sGrayBoy2 = new Sprite(393, 181, trGray2, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayBoy2);
		sGrayBoy3 = new Sprite(435, 276, trGray3, this.getVertexBufferObjectManager());
		mScene.attachChild(sGrayBoy3);
		setvisiableSprite(false, sGrayBoy1, sGrayBoy2,sGrayBoy3);
		
		sBoymobil1 = new Sprite(699, -27, trBoymobil1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoymobil1);
		
		sBoymobil2 = new Sprite(757, 29, trBoymobil2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoymobil2);
		
		sBoymobil3 = new Sprite(682, 30, trBoymobil3, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoymobil3);
		
		sBoymobil4 = new Sprite(725, 77, trBoymobil4, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoymobil4);
		
		sBoymobil5 = new Sprite(851, 76, trBoymobil5, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoymobil5);
		setvisiableSprite(false, sBoymobil1, sBoymobil2,sBoymobil3,sBoymobil4,sBoymobil5);
		
		aniTsumiki =  new AnimatedSprite(268, 410, ttrTsumiki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTsumiki);
		aniTsumiki.setVisible(false);
		
		sBoybazul1 = new Sprite(556, 252, trBoybazul1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoybazul1);
		sBoybazul1.setVisible(false);
		sBoybazul2 = new Sprite(235, 26, trBoybazul2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoybazul2);
		sBoybazul2.setVisible(false);
		sBoybazul3 = new Sprite(584, 85, trBoybazul3, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoybazul3);
		sBoybazul3.setVisible(false);
		aniBoybazul4 = new AnimatedSprite(365, 45, ttrBoybazul4, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoybazul4);
		aniBoybazul4.setVisible(false);
		aniBoyhikouki = new AnimatedSprite(173, 126, ttrBoyhikouki, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoyhikouki);
		aniBoyhikouki.setVisible(false);
		aniRobot1 = new AnimatedSprite(56, 412, ttrRobot1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRobot1);
		aniRobot1.setVisible(false);
		aniRobot2 = new AnimatedSprite(56, 412, ttrRobot2, this.getVertexBufferObjectManager());
		mScene.attachChild(aniRobot2);
		aniRobot2.setVisible(false);
		sBgr2 = new Sprite(0, 0, trBgr2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBgr2);
		sBgr2.setZIndex(40);
		sBgr2.setVisible(false);
		for (int i = 0; i < arrAniObake.length; i++) {
			arrAniObake[i] = new AnimatedSprite(0, 0, ttrObake2, this.getVertexBufferObjectManager());
			mScene.attachChild(arrAniObake[i]);
			arrAniObake[i].setZIndex(50+i);
			arrAniObake[i].setVisible(false);
		} 
		aniObake1 = new AnimatedSprite(615, 264, ttrObake1, this.getVertexBufferObjectManager());
		mScene.attachChild(aniObake1);
		aniObake1.setZIndex(80);
		for (int i = 0; i < sGimmic.length; i++) {
			sGimmic[i] = new Sprite(pointGimmic[i], 496, trGimmic[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sGimmic[i]);
			sGimmic[i].setZIndex(100+i);
		}
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		this.mScene.sortChildren();
		
	}
	
	@Override
	public synchronized void onPauseGame() {
		resetData();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onResumeGame :");
		initial();
		moveObake();
		loadRandomKobito();
		super.onResumeGame();
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pTouchEvent.getX();
		int pY = (int) pTouchEvent.getY();
		Log.d(TAG, "onSceneTouchEvent");
		if (pTouchEvent.isActionDown()) {
			if (sGimmic[0].contains(pX, pY)) {
				touchGimmic(0);
			}else if (sGimmic[1].contains(pX, pY)) {
				touchGimmic(1);
			}else if (sGimmic[2].contains(pX, pY)) {
				touchGimmic(2);
			}
			if (checkContains(aniBgr1, 370, 2, 588, 62, pX, pY)) {
				touchLight();
			}
			if (checkContains(aniObake1, 6, 24, 140, 188, pX, pY) && (aniObake1.getCurrentTileIndex()==0)) {
				touchObake(pX, pY,0);
			} else if (checkContains(aniObake1, 6, 24, 140, 188, pX, pY) && (aniObake1.getCurrentTileIndex()==1)) {
				touchObake(pX, pY,1);
			} else if (sBoybazul1.contains(pX, pY) && touchBazul1 && sBoybazul1.isVisible()) {
				//sound
				touchBazul1 = false;
				checkMoveBoy1 = true;
				A3_6_BOYON.play();
				sGrayBoy1.setVisible(true);
			}
			else if (checkContains(sPiano1, 22, 8, 155, 152, pX, pY)&&sPiano1.isVisible()) {
					touchPiano();
			} else if (checkContains(aniGirlcloest, 104, 96, 268, 326, pX, pY)&& aniGirlcloest.isVisible()) {
					touchGirlCloest();
			} else if (checkContains(aniGirlDoor, 120, 87, 300, 406, pX, pY) && aniGirlDoor.isVisible()) {
					touchGirlDoor();
			} else if ((sGirlbazul1.contains(pX, pY) && touchGirlBazul1 && sGirlbazul1.isVisible())) {
				//sound
				Log.d(TAG, "gia tri :"+touchBazul1);
				touchGirlBazul1 = false;
				A3_6_BOYON.play();
				checkMoveGirl1 = true;
				sGrayGirl1.setVisible(true);
			} else if (sGirlbazul2.contains(pX, pY)&& touchGirlBazul2 && sGirlbazul2.isVisible()) {
				//sound
				touchGirlBazul2 = false;
				checkMoveGirl2 = true;
				A3_6_BOYON.play();
				sGrayGirl2.setVisible(true);
			} else if (sGirlbazul3.contains(pX, pY)&& touchGirlBazul3 && sGirlbazul3.isVisible()) {
				//sound
				touchGirlBazul3 = false;
				checkMoveGirl3 = true;
				A3_6_BOYON.play();
				sGrayGirl3.setVisible(true);
			} else if (aniSteki1.contains(pX, pY)&&aniSteki1.isVisible()) {
				touchSteki();
			}
			if((sGirlmobil1.contains(pX, pY)||sGirlmobil2.contains(pX, pY)||sGirlmobil3.contains(pX, pY)||
					sGirlmobil5.contains(pX, pY)||sGirlmobil4.contains(pX, pY))&&(sGirlmobil1.isVisible())){
				touchGirlMobil();
			}
			if (checkContains(aniHime, 80, 20, 145, 166, pX, pY)&& aniHime.isVisible()) {
				touchHime();
			}
			//-------------boy---------------
			if (sBoybazul2.contains(pX, pY)&&touchBazul2&&sBoybazul2.isVisible()) {
				//sound
				touchBazul2 = false;
				checkMoveBoy2 = true;
				A3_6_BOYON.play();
				sGrayBoy2.setVisible(true);
			}
			if (sBoybazul3.contains(pX, pY)&&touchBazul3&&sBoybazul3.isVisible()) {
				//sound
				touchBazul3 = false;
				checkMoveBoy3 = true;
				A3_6_BOYON.play();
				sGrayBoy3.setVisible(true);
			}
			if ((sBoymobil1.contains(pX, pY)||sBoymobil2.contains(pX, pY)||sBoymobil4.contains(pX, pY)||
					sBoymobil3.contains(pX, pY)||sBoymobil5.contains(pX, pY))&&sBoymobil1.isVisible()) {
				touchBoyMobil();
			}
			if (checkContains(aniBoycloest, 104, 96, 268, 326, pX, pY)&&aniBoycloest.isVisible()) {
				touchBoyCloest();
			} else if (aniBoyhikouki.contains(pX, pY)&&aniBoyhikouki.isVisible()) {
				touchHikouki();
			}
			if (checkContains(aniTsumiki, 79, 23, 161, 145, pX, pY)&&aniTsumiki.isVisible()) {
				touchTsumiki();
			}
			if (checkContains(aniBoyDoor, 120, 87, 300, 406, pX, pY)&& aniBoyDoor.isVisible()) {
				touchBoyDoor();
			}else if (checkContains(sBoyisu, 2, 10, 136, 125, pX, pY)&& sBoyisu.isVisible()){
				touchIsu();
			}
			if (checkContains(aniRobot1, 46, 35, 122, 176, pX, pY)&& aniRobot1.isVisible()) {
				touchRobot();
			}
			
			
		}
		if (pTouchEvent.isActionMove()) {
			if (checkMoveGirl1) {
				sGirlbazul1.setPosition(pX, pY);
			}
			if (checkMoveGirl2) {
				sGirlbazul2.setPosition(pX-100, pY-100);
			}
			if (checkMoveGirl3) {
				sGirlbazul3.setPosition(pX, pY);
			}
			if (checkMoveBoy1) {
				sBoybazul1.setPosition(pX, pY);
			}
			if (checkMoveBoy2) {
				sBoybazul2.setPosition(pX-100, pY-100);
			}
			if (checkMoveBoy3) {
				sBoybazul3.setPosition(pX, pY);
			}
			
		}
		if (pTouchEvent.isActionUp()) {
			if (sGirlbazul1.collidesWith(sGrayGirl1)&& checkMoveGirl1) {
				sGirlbazul1.setPosition(429, 73);
				checkMoveGirl1 = false;
				touchGirlBazul1 = false;
				sGrayGirl1.setVisible(false);
				A3_6_PAZUL.play();
				countGirlBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countGirlBazul==3) {
					moveGirlBazul();
				}
			}else if (sGrayGirl1.isVisible()){
				sGirlbazul1.setPosition(sGirlbazul1.getmXFirst(), sGirlbazul1.getmYFirst());
				checkMoveGirl1 = false;
				touchGirlBazul1= true;
				sGrayGirl1.setVisible(false);
			}
			if (sGirlbazul2.collidesWith(sGrayGirl2)&&checkMoveGirl2) {
				sGirlbazul2.setPosition(393, 181);
				checkMoveGirl2 = false;
				touchGirlBazul2= false;
				sGrayGirl2.setVisible(false);
				A3_6_PAZUL.play();
				countGirlBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countGirlBazul==3) {
					moveGirlBazul();
				}
			}else if (sGrayGirl2.isVisible()) {
				sGirlbazul2.setPosition(sGirlbazul2.getmXFirst(), sGirlbazul2.getmYFirst());
				checkMoveGirl2 = false;
				touchGirlBazul2= true;
				sGrayGirl2.setVisible(false);
			}
			if (sGirlbazul3.collidesWith(sGrayGirl3) && checkMoveGirl3) {
				sGirlbazul3.setPosition(435, 276);
				checkMoveGirl3 = false;
				touchGirlBazul3 = false;
				sGrayGirl3.setVisible(false);
				A3_6_PAZUL.play();
				countGirlBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countGirlBazul==3) {
					moveGirlBazul();
				}
				
			}else if (sGrayGirl3.isVisible()) {
				sGirlbazul3.setPosition(sGirlbazul3.getmXFirst(), sGirlbazul3.getmYFirst());
				checkMoveGirl3 = false;
				touchGirlBazul3= true;
				sGrayGirl3.setVisible(false);
			}
			//-----------boy----------------
			if (sBoybazul1.collidesWith(sGrayBoy1)&& checkMoveBoy1) {
				sBoybazul1.setPosition(429, 73);
				touchBazul1 = false;
				checkMoveBoy1 = false;
				sGrayBoy1.setVisible(false);
				A3_6_PAZUL.play();
				countBoyBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countBoyBazul==3) {
					moveBoyBazul();
				}
			
			}else if (sGrayBoy1.isVisible()) {
				sBoybazul1.setPosition(sBoybazul1.getmXFirst(), sBoybazul1.getmYFirst());
				checkMoveBoy1 = false;
				touchBazul1= true;
				sGrayBoy1.setVisible(false);
			}
			if (sBoybazul2.collidesWith(sGrayBoy2)&& checkMoveBoy2) {
				sBoybazul2.setPosition(393, 181);
				touchBazul2 = false;
				checkMoveBoy2 = false;
				sGrayBoy2.setVisible(false);
				A3_6_PAZUL.play();
				countBoyBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countBoyBazul==3) {
					moveBoyBazul();
				}
			
			}else if (sGrayBoy2.isVisible()) {
				sBoybazul2.setPosition(sBoybazul2.getmXFirst(), sBoybazul2.getmYFirst());
				checkMoveBoy2 = false;
				touchBazul2= true;
				sGrayBoy2.setVisible(false);
			}
			if (sBoybazul3.collidesWith(sGrayBoy3)&& checkMoveBoy3) {
				sBoybazul3.setPosition(435, 276);
				touchBazul3 = false;
				checkMoveBoy3 = false;
				sGrayBoy3.setVisible(false);
				A3_6_PAZUL.play();
				countBoyBazul++;
				Log.d(TAG, " countGirlBazul : "+countGirlBazul);
				if (countBoyBazul==3) {
					moveBoyBazul();
				}
			
			}else if (sGrayBoy3.isVisible()){
				sBoybazul3.setPosition(sBoybazul3.getmXFirst(), sBoybazul3.getmYFirst());
				checkMoveBoy3 = false;
				touchBazul3= true;
				sGrayBoy3.setVisible(false);
			}
		}
		
		
		return false;
	}
	private void touchHime() {
		if (touchHime) {
			touchHime = false;
			touchKobito = false;
			aniHime.setCurrentTileIndex(1);
			A3_12_HOISUL.play();
			A3_14_KOBITO.play();
			
			for (int i = 0; i < 7; i++) {
				final int id = i;
				arrAniKobito[arlKobito.get(i)].setRotation(0);
				arrAniKobito[arlKobito.get(i)].animate(new long[]{400,400},new int[]{1,2},0, new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}
					
					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						if (id==6) {
							moveKobitto();
						}
					}
				});
			}
			
		}
	}
	
	private void moveKobitto() {
		float CoordirateFirst[][] = new float[][] {
				{163,300}, {362,372}, {458,444}, {720,270},
				{170,342}, {318,414}, {35,486}
				};
		float CoordirateEnd[][] = new float[][]{
				{64,337}, {315,337}, {372,337},{500,390},
				{491,390}, {509,390}, {491,390},
		};
		
		for (int i = 0; i < 7; i++) {
			
			pathKobito[i] = new Path(CoordirateFirst[i], CoordirateEnd[i]);
			final int index = i;
			arrAniKobito[arlKobito.get(i)].animate(new long[]{300,300}, new int []{3,4},-1);
			arrAniKobito[arlKobito.get(i)].registerEntityModifier( new PathModifier(2.4f, pathKobito[i], 
					new IPathModifierListener() {
				
				@Override
				public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {}
				
				@Override
				public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {}
				
				@Override
				public void onPathStarted(PathModifier arg0, IEntity arg1) {}
				
				@Override
				public void onPathFinished(PathModifier arg0, IEntity arg1) {
					arrAniKobito[arlKobito.get(index)].stopAnimation();
					arrAniKobito[arlKobito.get(index)].setCurrentTileIndex(2);
					if (index==6){
						A3_14_KOBITO_OK.play();
						aniHime.setCurrentTileIndex(0);
					}
				}
			}));
			arrAniKobito[arlKobito.get(i)].setZIndex(i+2);
		}
		mScene.sortChildren();
		TimerHandler timerKobito = new TimerHandler(3.5f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				mEngine.unregisterUpdateHandler(arg0);
				touchHime = true;
				touchKobito = true;
				
				loadRandomKobito();
				
			}
		});
		mEngine.registerUpdateHandler(timerKobito);
	}
	
	
	private void loadRandomKobito() {
		Collections.shuffle(arlKobito);
		for (int i = 0; i< 7;i++) {
			arrAniKobito[arlKobito.get(i)].setPosition(arrPointKobito[0][i], arrPointKobito[1][i]);
			arrAniKobito[arlKobito.get(i)].setVisible(true);
			arrAniKobito[arlKobito.get(i)].setCurrentTileIndex(0);
			arrAniKobito[arlKobito.get(i)].setZIndex(0);
		}
		arrAniKobito[new Random().nextInt(7)].setRotation(-90);
	}
	
	
	
	private void touchGimmic(int id) {
		switch (id) {
		case 0:
			sGimmic[0].registerEntityModifier(new SequenceEntityModifier(
								new ScaleModifier(0.35f, 1, 1.3f),
								new ScaleModifier(0.35f, 1.3f, 1f)));
			A3_OBAKE_1.play();
			break;
		case 1:
			sGimmic[1].registerEntityModifier(new SequenceEntityModifier(
					new ScaleModifier(0.35f, 1, 1.3f),
					new ScaleModifier(0.35f, 1.3f, 1f)));
			touchLight();
			break;
		case 2:
			sGimmic[2].registerEntityModifier(new SequenceEntityModifier(
					new ScaleModifier(0.35f, 1, 1.3f),
					new ScaleModifier(0.35f, 1.3f, 1f)));
			A3_HATENA.play();
			changeRoom();
			break;
		default:
			break;
		}
	}
	
	private void touchLight() {
		if (touchLight) {
			//off light
			touchLight=false;
			A3_LITE.play();
			sBgr2.setVisible(true);
		} else {
			// on light
			touchLight=true;
			A3_LITE.play();
			sBgr2.setVisible(false);
		}
	}
	
	private void touchObake(int left, int top, int index) {
		if (!arrAniObake[0].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[0], left+20, top-220,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[0], left-240, top-220,index);
			}
		} else if (!arrAniObake[1].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[1], left+20, top+40,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[1], left+60, top-240,index);
			}
		} else if (!arrAniObake[2].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[2], left-120, top-250,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[2], left-120, top-320,index);
			}
		} else if (!arrAniObake[3].isVisible()){
			if (index == 0) {
				moveObakeChild(arrAniObake[3], left-200, top+10,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[3], left+60, top+30,index);
			}
		} else if (!arrAniObake[4].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[4], left+180, top-210,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[4], left-300, top+10,index);
			}
		} else if (!arrAniObake[5].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[5], left-240, top-170,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[5], left-280, top-80,index);
			}
		} else if (!arrAniObake[6].isVisible()) {
			if (index == 0) {
				moveObakeChild(arrAniObake[6], left+150, top-70,index);
			} else if (index==1) {
				moveObakeChild(arrAniObake[6], left-200, top+50,index);
			}
		}
	}
	private void moveObakeChild(final AnimatedSprite aSprObake, int left,int top, int index) {
		aSprObake.setPosition(left,top);
		aSprObake.setCurrentTileIndex(index);
		aSprObake.setScale(0.4f);
		aSprObake.setVisible(true);
		A3_5_OBAKE.play();
		if (index==0) {
		aSprObake.registerEntityModifier(new SequenceEntityModifier( new DelayModifier(0.4f),
				new ParallelEntityModifier(
				new MoveXModifier(3.2f, aSprObake.getX(), aSprObake.getX()-90),
				new AlphaModifier(3.2f, 1, 0, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aSprObake.setVisible(false);
						aSprObake.setCurrentTileIndex(0);
					}
				}))));
		
		} else if (index==1) {
			aSprObake.registerEntityModifier(new SequenceEntityModifier( new DelayModifier(0.4f),
					new ParallelEntityModifier(
					new MoveXModifier(3.2f, aSprObake.getX(), aSprObake.getX()+90),
					new AlphaModifier(3.2f, 1, 0, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							aSprObake.setVisible(false);
							aSprObake.setCurrentTileIndex(0);
						}
					}))));
		}
		
	}
	//----------------tap boy room--------------
	private void touchHikouki() {
		if (touchHikouki) {
			touchHikouki = false;
			aniBoyhikouki.animate(200,-1);
			A3_8_HIKOUKI.play();
			aniBoyhikouki.registerEntityModifier(new ParallelEntityModifier(
					new DelayModifier(3.0f, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					A3_8_HIKOUKI.play();
				}
			}),
				new SequenceEntityModifier(
				new MoveXModifier(0.8f, aniBoyhikouki.getX(), -80),
				new MoveXModifier(3.4f, -80, 900, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						aniBoyhikouki.setFlippedHorizontal(true);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {}
				}),
				new MoveXModifier(3.0f, 900, aniBoyhikouki.getmXFirst(), new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						aniBoyhikouki.setFlippedHorizontal(false);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						aniBoyhikouki.stopAnimation();
						aniBoyhikouki.setCurrentTileIndex(0);
						touchHikouki = true;
					}
				}))));
		}
	}
	private void touchIsu() {
		if (touchIsu) {
			touchIsu = false;
			A3_18_SAWA.play();
			sBoyisu.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.15f, sBoyisu.getX(), sBoyisu.getX()-15),
					new MoveXModifier(0.25f, sBoyisu.getX()-15, sBoyisu.getX()+12),
					new MoveXModifier(0.2f, sBoyisu.getX()+12, sBoyisu.getX()-8),
					new MoveXModifier(0.15f, sBoyisu.getX()-8, sBoyisu.getX()+6),
					new MoveXModifier(0.1f, sBoyisu.getX()+6, sBoyisu.getX(),
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchIsu = true;
						}
					})
					
			));
		}
	}
	private void touchRobot(){
		if (touchRobot) {
			touchRobot = false;
			A3_9_ROBOT.play();
			aniRobot1.animate(new long[]{600,500}, new int[]{1,0},0, this);
			nextTileAnimatedSprite(aniRobot2);
		}
	}
	private void touchTsumiki() {
		if (touchTsumiki) {
			touchTsumiki = false;
			A3_10_TUMIKI.play();
			aniTsumiki.animate(new long[]{200,500,400}, new int[]{1,2,0},0, this);
			
		}
	}
	private void touchBoyDoor() {
		if (touchDoor) {
			touchCloest= false;
			touchDoor = false;
			A3_4_KOWAI_BOY.play();
			aniBoyDoor.animate(new long[]{500,800,400}, new int[]{1,2,0},0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg1==0) {
						A3_4_DOASHIME.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchCloest= true;
					touchDoor = true;
				}
			});
			
		}
	}
	
	private void touchBoyCloest(){
		if (touchCloest) {
			int randomGhost = new Random().nextInt(3);
			aniGhost.setVisible(true);
			touchCloest= false;
			aniBoycloest.animate(new long[]{800,400}, new int[]{1,2},0, this);
			TimerHandler timerBoyDoor = new TimerHandler(0.5f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					touchBoyDoor();
				}
			});
			mEngine.registerUpdateHandler(timerBoyDoor);
			switch (randomGhost) {
			case 0:
				A3_2_1_1_KUMA.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{0,1,2},0, this);
				break;
			case 1:
				A3_2_2_1_DORAQURA.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{3,4,5},0, this);
				break;
			case 2:
				A3_2_3_1_FURANKEN.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{6,7,8},0, this);
				break;
			default:
				break;
			}
			TimerHandler timerGhost = new TimerHandler(1.2f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					aniBoycloest.setCurrentTileIndex(0);
					A3_2_TANCESHIME.play();
					aniGhost.setVisible(false);
					aniGhost.setCurrentTileIndex(0);
					
				}
			});
			mEngine.registerUpdateHandler(timerGhost);
			
		}
	}
	private void touchBoyMobil () {
		if (touchGirlMobil) {
			touchGirlMobil=false;
			A3_19_20_TUKITOHOSI.play();
			rotationMobil(sBoymobil3,20,2);
			rotationMobil(sBoymobil4,36,2);
			rotationMobil(sBoymobil5,25,2);
			
		}
	}
	private void moveBoyBazul() {
		setvisiableSprite(false, sBoybazul1, sBoybazul2, sBoybazul3);
		A3_6_PAZULHAMARU.play();
		aniBoybazul4.setVisible(true);
		aniBoybazul4.setCurrentTileIndex(1);
		aniBoybazul4.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.8f),
				new MoveModifier(1.6f, aniBoybazul4.getX(), 
						aniBoybazul4.getX()+250, aniBoybazul4.getY(), -347, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						aniBoybazul4.setCurrentTileIndex(2);
						A3_6_2_3_RAIDA.play();
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						setvisiableAniSprite(false, aniBoybazul4);
						setvisiableSprite(true, sBoybazul1, sBoybazul2, sBoybazul3);
						countBoyBazul=0;
						touchBazul3 = true;
						touchBazul2 = true;
						touchBazul1 = true;
					}
				})));
		
		
	}
	//------------------tap girl room--------------
	private void touchGirlMobil(){
		if (touchGirlMobil) {
			touchGirlMobil=false;
			A3_19_20_TUKITOHOSI.play();
			rotationMobil(sGirlmobil3,16,2);
			rotationMobil(sGirlmobil4,42,2);
			rotationMobil(sGirlmobil5,36,2);
			
		}
	}
	private void rotationMobil(Sprite sp, float centerX, float centerY) {
		
		sp.registerEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.4f, 0, 16, centerX, centerY),
				new RotationAtModifier(0.8f, 16, -16, centerX, centerY),
				new RotationAtModifier(0.8f, -16, 16, centerX, centerY),
				new RotationAtModifier(0.8f, 16, -16, centerX, centerY),
				new RotationAtModifier(0.8f, -16, 16, centerX, centerY),
				new RotationAtModifier(0.4f, 16, 0, centerX, centerY, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						touchGirlMobil=true;
					}
				})));
		
	}
	private void touchPiano(){
		if (touchPinao) {
			touchPinao = false;
			int id_sound = new Random().nextInt(3);
			switch (id_sound) {
			case 0:
				A3_11_PIANO.play();
				sPiano1.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.4f, sPiano1.getX(), sPiano1.getX()-20),
						new MoveXModifier(0.8f, sPiano1.getX()-20, sPiano1.getX()+20),
						new MoveXModifier(0.8f, sPiano1.getX()+20, sPiano1.getX()-20),
						new MoveXModifier(0.4f, sPiano1.getX()-20, sPiano1.getX(), new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								touchPinao = true;
							}
						})
						));
				sPiano2.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.4f, sPiano2.getX(), sPiano2.getX()+30),
						new MoveXModifier(0.8f, sPiano2.getX()+30, sPiano2.getX()-30),
						new MoveXModifier(0.8f, sPiano2.getX()-30, sPiano2.getX()+30),
						new MoveXModifier(0.4f, sPiano2.getX()+30, sPiano2.getX())));
				break;
			case 1:
				A3_11_PIANOB.play();
				sPiano1.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.15f, sPiano1.getX(), sPiano1.getX()-10),
						new MoveXModifier(0.3f, sPiano1.getX()-10, sPiano1.getX()+10),
						new MoveXModifier(0.3f, sPiano1.getX()+10, sPiano1.getX()-10),
						new MoveXModifier(0.15f, sPiano1.getX()-10, sPiano1.getX(), new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								touchPinao = true;
							}
						})
						));
				sPiano2.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.15f, sPiano2.getX(), sPiano2.getX()+20),
						new MoveXModifier(0.3f, sPiano2.getX()+20, sPiano2.getX()-20),
						new MoveXModifier(0.3f, sPiano2.getX()-20, sPiano2.getX()+20),
						new MoveXModifier(0.15f, sPiano2.getX()+20, sPiano2.getX())));
				break;
			case 2:
				A3_11_PIANOK.play();
				sPiano1.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.15f, sPiano1.getX(), sPiano1.getX()-10),
						new MoveXModifier(0.3f, sPiano1.getX()-10, sPiano1.getX()+10),
						new MoveXModifier(0.3f, sPiano1.getX()+10, sPiano1.getX()-10),
						new MoveXModifier(0.15f, sPiano1.getX()-10, sPiano1.getX(), new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								touchPinao = true;
							}
						})
						));
				sPiano2.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.15f, sPiano2.getX(), sPiano2.getX()+20),
						new MoveXModifier(0.3f, sPiano2.getX()+20, sPiano2.getX()-20),
						new MoveXModifier(0.3f, sPiano2.getX()-20, sPiano2.getX()+20),
						new MoveXModifier(0.15f, sPiano2.getX()+20, sPiano2.getX())));
				break;
			default:
				break;
			}
		}
	}
	private void touchGirlDoor() {
		if (touchDoor) {
			touchGirlCloest= false;
			touchDoor = false;
			A3_4_KYA_GIRL.play();
			aniGirlDoor.animate(new long[]{500,800,400}, new int[]{1,2,0},0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					if (arg1==0) {
						A3_4_DOASHIME.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchGirlCloest= true;
					touchDoor = true;
				}
			});
			
		}
	}
	private void touchGirlCloest(){
		if (touchGirlCloest) {
			int randomGhost = new Random().nextInt(3);
			aniGhost.setVisible(true);
			touchGirlCloest= false;
			aniGirlcloest.animate(new long[]{800,400}, new int[]{1,2},0, this);
			TimerHandler timerDoor = new TimerHandler(0.5f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					touchGirlDoor();
					
				}
			});
			mEngine.registerUpdateHandler(timerDoor);
			switch (randomGhost) {
			case 0:
				A3_2_1_1_KUMA.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{0,1,2},0, this);
				break;
			case 1:
				A3_2_2_1_DORAQURA.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{3,4,5},0, this);
				break;
			case 2:
				A3_2_3_1_FURANKEN.play();
				aniGhost.animate(new long[]{400,400,400}, new int[]{6,7,8},0, this);
				break;
			default:
				break;
			}
			TimerHandler timerGhost = new TimerHandler(1.2f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					aniGirlcloest.setCurrentTileIndex(0);
					A3_2_TANCESHIME.play();
					aniGhost.setVisible(false);
					aniGhost.setCurrentTileIndex(0);
					
					
				}
			});
			mEngine.registerUpdateHandler(timerGhost);
			
		}
	}
	private void touchSteki() {
		if (touchSteki) {
			touchSteki = false;
			A3_13_SUTEKI.play();
			nextTileAnimatedSprite(aniSteki1);
			aniSteki1.setRotationCenter(160, 22);
			aniSteki1.setRotation(45);
			aniSteki2.setVisible(true);
			aniSteki2.animate(300,-1);
			TimerHandler timerSteki = new TimerHandler(1.0f,false, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					aniSteki1.setRotationCenter(160, 22);
					aniSteki1.setRotation(0);
					aniSteki2.setVisible(false);
					aniSteki2.stopAnimation();
					touchSteki = true;
				}
			});
			mEngine.registerUpdateHandler(timerSteki);
		}
	}
	private void moveObake() {
		float CoordirateX1[] = new float[] {615,515,415,315,215};
		float CoordirateY1[] = new float[] {264,204,264,204,264};
		float CoordirateX2[] = new float[] {180,315,415,515,580};
		float CoordirateY2[] = new float[] {264,204,264,204,264};
		final Path path1 = new Path(CoordirateX1,
				CoordirateY1);
		final Path path2 = new Path(CoordirateX2,
				CoordirateY2);
		aniObake1.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new PathModifier(16.0f,path1, new IPathModifierListener() {
					
					@Override
					public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {}
					
					@Override
					public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {}
					
					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {}
					
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						aniObake1.setCurrentTileIndex(1);
					}
				}),
				new PathModifier(16.0f, path2, new IPathModifierListener() {
					
					@Override
					public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {}
					
					@Override
					public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {}
					
					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {}
					
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						aniObake1.setCurrentTileIndex(0);
					}
				})
				)));
	}
	private void moveGirlBazul() {
		setvisiableSprite(false, sGirlbazul1,sGirlbazul2,sGirlbazul3);
		aniGirlbazul4.setVisible(true);
		aniGirlbazul4.setCurrentTileIndex(1);
		A3_6_PAZULHAMARU.play();
		A3_7_KUKIODORU.play();
		aniGirlbazul4.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(1.8f),
				new MoveYModifier(3.2f, aniGirlbazul4.getY(), 800, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
								aniGirlbazul4.animate(new long[]{300,300}, new int[]{2,3},-1);
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								aniGirlbazul4.stopAnimation();
								setvisiableAniSprite(false, aniGirlbazul4);
								setvisiableSprite(true, sGirlbazul1,sGirlbazul2,sGirlbazul3);
								countGirlBazul=0;
								touchGirlBazul1 = true;
								touchGirlBazul2 = true;
								touchGirlBazul3 = true;
							}
						})
				));
		
	}
	
	
	@Override
	public void combineGimmic3WithAction() {
	}
	private void changeRoom() {
		if (checkRoom) {
			checkRoom = false;
			//hide girl room
			stopSound();
			aniGirlbazul4.clearEntityModifiers();
			aniBoybazul4.clearEntityModifiers();
			
			sPiano1.clearEntityModifiers();
			sPiano2.clearEntityModifiers();
			aniSteki2.clearEntityModifiers();
			aniSteki1.clearEntityModifiers();
			aniSteki1.setRotation(0);
			sGirlmobil3.clearEntityModifiers();
			sGirlmobil3.setRotation(0);
			sGirlmobil4.clearEntityModifiers();
			sGirlmobil4.setRotation(0);
			sGirlmobil5.clearEntityModifiers();
			sGirlmobil5.setRotation(0);
			setvisiableSprite(false, sGirlmobil1,sGirlmobil2,sGirlmobil3,sGirlmobil4,sGirlmobil5, 
					sGirlbazul1,sGirlbazul2,sGirlbazul3,sPiano1,sPiano2);
			
			setvisiableAniSprite(false, aniGirlcloest, aniGirlDoor,aniSteki1,aniSteki2, aniGirlbazul4,aniBoybazul4,aniHime);
			//hide kobito
			for (int i = 0; i < arrAniKobito.length; i++) {
				arrAniKobito[arlKobito.get(i)].clearEntityModifiers();
				arrAniKobito[arlKobito.get(i)].stopAnimation();
				arrAniKobito[arlKobito.get(i)].setCurrentTileIndex(0);
				arrAniKobito[arlKobito.get(i)].setVisible(false);
				
			}
			mEngine.clearUpdateHandlers();
			
			//appear boy room
			touchKobito = false;
			touchHime = false;
			nextTileAnimatedSprite(aniBgr1);
			setvisiableSprite(true, sBoybazul1, sBoybazul2, sBoybazul3, sBoymobil1, sBoymobil2,
					sBoymobil3,sBoymobil4,sBoymobil5, sBoyisu);
			setvisiableAniSprite(true,  aniBoycloest,aniBoyDoor, aniBoyhikouki, aniTsumiki, aniRobot1,aniRobot2);
			//value boolean
			countBoyBazul = 0;
			touchBazul3 = true;
			touchBazul2 = true;
			touchBazul1 = true;
			touchGirlMobil=true;
			
		}else {
			nextTileAnimatedSprite(aniBgr1);
			girlRoom();
			loadRandomKobito();
		}
		
	}
	private void girlRoom() {
		checkRoom = true;
		//appear boy room
		stopSound();
		aniBoyhikouki.clearEntityModifiers();
		aniBoyhikouki.stopAnimation();
		aniBoyhikouki.setCurrentTileIndex(0);
		if (aniBoyhikouki.isFlippedHorizontal()) {
			aniBoyhikouki.setFlippedHorizontal(false);
			
		} 
		touchHikouki = true;
		setvisiableSprite(true, sGirlmobil1,sGirlmobil2,sGirlmobil3,sGirlmobil4,sGirlmobil5, 
				sGirlbazul1,sGirlbazul2,sGirlbazul3,sPiano1,sPiano2, aniHime);
		
		setvisiableAniSprite(true, aniGirlcloest, aniGirlDoor,aniSteki1);
		
		//hide boy room
		setvisiableSprite(false, sBoybazul1, sBoybazul2, sBoybazul3, sBoymobil1, sBoymobil2,
				sBoymobil3,sBoymobil4,sBoymobil5, sBoyisu);
		setvisiableAniSprite(false,  aniBoycloest,aniBoyDoor, aniBoyhikouki, aniTsumiki, aniRobot1,aniRobot2, 
				aniBoybazul4,aniGirlbazul4);
		
		//value boolean
		sBoymobil3.clearEntityModifiers();
		sBoymobil3.setRotation(0);
		sBoymobil4.clearEntityModifiers();
		sBoymobil4.setRotation(0);
		sBoymobil5.clearEntityModifiers();
		sBoymobil5.setRotation(0);
		aniGirlbazul4.clearEntityModifiers();
		aniBoybazul4.clearEntityModifiers();
		countGirlBazul=0;
		touchGirlBazul1 = true;
		touchGirlBazul2 = true;
		touchGirlBazul3 = true;
		touchKobito = true;
		touchHime = true;
		touchSteki = true;
		touchGirlMobil=true;
		touchPinao = true;
		touchGirlMobil=true;
	}
	private void resetData() {
		//stop sound
		stopSound();
		//clear data
		if (aniBgr1!=null) {
			aniBgr1.setCurrentTileIndex(0);
			sBgr2.setVisible(false);
		}
		girlRoom();
		//reset obake
		if (aniObake1!=null) {
			aniObake1.clearEntityModifiers();
			aniObake1.setCurrentTileIndex(0);
			aniObake1.setPosition(aniObake1.getmXFirst(), aniObake1.getmYFirst());
		}
		for (int i = 0; i < arrAniKobito.length; i++) {
			arrAniKobito[arlKobito.get(i)].clearEntityModifiers();
			arrAniKobito[arlKobito.get(i)].stopAnimation();
			arrAniKobito[arlKobito.get(i)].setCurrentTileIndex(0);
			arrAniKobito[arlKobito.get(i)].setVisible(false);
			
		}
		mEngine.clearUpdateHandlers();
		if (aniHime!=null){
			aniHime.setCurrentTileIndex(0);
		}
		
	}
	private void stopSound() {
		
		A3_2_3_1_FURANKEN.stop();
		A3_6_BOYON.stop();
		A3_2_1_1_KUMA.stop();
		A3_OBAKE_1.stop();
		A3_11_PIANO.stop(); 
		A3_6_2_3_RAIDA.stop();
		A3_9_ROBOT.stop();
		A3_11_PIANOK.stop();
		A3_12_HOISUL.stop();
		A3_13_SUTEKI.stop();
		A3_10_TUMIKI.stop();
		A3_6_PAZUL.stop();
		A3_18_SAWA.stop();
		A3_19_20_TUKITOHOSI.stop();
		A3_8_HIKOUKI.stop();
		A3_14_BYON.stop();
		A3_7_KUKIODORU.stop();
		A3_14_KOBITO.stop();
		A3_2_2_1_DORAQURA.stop();
		A3_LITE.stop();
		A3_14_KOBITO_OK.stop();
		A3_4_KOWAI_BOY.stop();
		A3_OBAKE_2.stop();
		A3_4_KYA_GIRL.stop();
		A3_5_OBAKE.stop();
		A3_4_DOASHIME.stop();
		A3_6_PAZULHAMARU.stop();
		A3_11_PIANOB.stop();
		A3_2_TANCESHIME.stop();
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		if (arg0.equals(aniGirlcloest)) {
			touchCloest=true;
			touchDoor=true;
		}
		if (arg0.equals(aniGhost)) {
			setvisiableAniSprite(false, aniGhost);
		}
		if (arg0.equals(aniTsumiki)) {
			touchTsumiki=true;
		}
		if (arg0.equals(aniRobot1)) {
			touchRobot = true;
		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
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
	public class kobitoAniSprites extends AnimatedSprite  {
		
		public kobitoAniSprites(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
				 VertexBufferObjectManager verBufObjManager) {
			super(pX, pY, pTiledTextureRegion, verBufObjManager);
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (pSceneTouchEvent.isActionDown()&&this.isVisible()&& this.contains(pX, pY)) {
				touchKobito();
			}
			return false;
		}
		private void touchKobito() {
			if (touchKobito) {
				touchKobito = false;
				touchHime = false;
				this.setRotation(0);
				A3_14_BYON.play();
				this.animate(new long[]{400,400,400},new int[]{3,4,0},0, new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}
					
					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						touchKobito = true;
						touchHime = true;
					}
				});
			}
		}
		
	}
	
	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		A3_2_3_1_FURANKEN = loadSoundResourceFromSD(Vol3ObakeResource.A3_2_3_1_FURANKEN);
		A3_6_BOYON = loadSoundResourceFromSD(Vol3ObakeResource.A3_6_BOYON);
		A3_2_1_1_KUMA = loadSoundResourceFromSD(Vol3ObakeResource.A3_2_1_1_KUMA);
		A3_OBAKE_1 = loadSoundResourceFromSD(Vol3ObakeResource.A3_OBAKE_1);
		A3_11_PIANO = loadSoundResourceFromSD(Vol3ObakeResource.A3_11_PIANO);
		A3_6_2_3_RAIDA = loadSoundResourceFromSD(Vol3ObakeResource.A3_6_2_3_RAIDA);
		A3_9_ROBOT = loadSoundResourceFromSD(Vol3ObakeResource.A3_9_ROBOT);
		A3_11_PIANOK = loadSoundResourceFromSD(Vol3ObakeResource.A3_11_PIANOK);
		A3_12_HOISUL = loadSoundResourceFromSD(Vol3ObakeResource.A3_12_HOISUL);
		A3_13_SUTEKI = loadSoundResourceFromSD(Vol3ObakeResource.A3_13_SUTEKI);
		A3_10_TUMIKI = loadSoundResourceFromSD(Vol3ObakeResource.A3_10_TUMIKI);
		A3_6_PAZUL = loadSoundResourceFromSD(Vol3ObakeResource.A3_6_PAZUL);
		A3_18_SAWA = loadSoundResourceFromSD(Vol3ObakeResource.A3_18_SAWA);
		A3_19_20_TUKITOHOSI = loadSoundResourceFromSD(Vol3ObakeResource.A3_19_20_TUKITOHOSI);
		A3_8_HIKOUKI = loadSoundResourceFromSD(Vol3ObakeResource.A3_8_HIKOUKI);
		A3_14_BYON = loadSoundResourceFromSD(Vol3ObakeResource.A3_14_BYON);
		A3_7_KUKIODORU = loadSoundResourceFromSD(Vol3ObakeResource.A3_7_KUKIODORU);
		A3_14_KOBITO = loadSoundResourceFromSD(Vol3ObakeResource.A3_14_KOBITO);
		A3_2_2_1_DORAQURA = loadSoundResourceFromSD(Vol3ObakeResource.A3_2_2_1_DORAQURA);
		A3_LITE = loadSoundResourceFromSD(Vol3ObakeResource.A3_LITE);
		A3_14_KOBITO_OK = loadSoundResourceFromSD(Vol3ObakeResource.A3_14_KOBITO_OK);
		A3_4_KOWAI_BOY = loadSoundResourceFromSD(Vol3ObakeResource.A3_4_KOWAI_BOY);
		A3_OBAKE_2 = loadSoundResourceFromSD(Vol3ObakeResource.A3_OBAKE_2);
		A3_4_KYA_GIRL = loadSoundResourceFromSD(Vol3ObakeResource.A3_4_KYA_GIRL);
		A3_5_OBAKE = loadSoundResourceFromSD(Vol3ObakeResource.A3_5_OBAKE);
		A3_4_DOASHIME = loadSoundResourceFromSD(Vol3ObakeResource.A3_4_DOASHIME);
		A3_HATENA = loadSoundResourceFromSD(Vol3ObakeResource.A3_HATENA);
		A3_6_PAZULHAMARU = loadSoundResourceFromSD(Vol3ObakeResource.A3_6_PAZULHAMARU);
		A3_11_PIANOB = loadSoundResourceFromSD(Vol3ObakeResource.A3_11_PIANOB);
		A3_2_TANCESHIME = loadSoundResourceFromSD(Vol3ObakeResource.A3_2_TANCESHIME);
	}

}
