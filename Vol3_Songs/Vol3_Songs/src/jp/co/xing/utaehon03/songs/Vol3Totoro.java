/* Vol3Totoro.java
* Create on Mar 13, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;



import android.util.Log;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TotoroResource;

public class Vol3Totoro extends BaseGameActivity implements 
IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {
	
	private String TAG = "Vol3 Totoro";
	private TexturePack totoroPack1, totoroPack2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary libTotoroPack1, libTotoroPack2;
	
	private ITextureRegion ttrHill, ttrKumo1, ttrKumo2, ttrKumo3,ttrMoon,ttrTree5,
			ttrFlyBrid1, ttrFlyBrid2, ttrTmpTree7, ttrBGSuccess;
	private ITextureRegion arrttrHole[] = new ITextureRegion[14];
	private Sprite sHill, sKumo1, sKumo2, sKumo3, sMoon ,sTree5, sFlyBird1, sFlyBird2,
			sTmpTree7, sBGSuccess;
	private Sprite arrsHole[] = new Sprite[14];
	private TiledTextureRegion tttrBackground, tttrAsaSun, tttrYugataSun,
			tttrHapa, tttrTree1, tttrTree2, tttrTree3, tttrTree4, tttrTree6, tttrTree7,
			tttrStarFly, tttrStar, tttrFlower, tttrKame,tttrUsagi,tttrKuma, tttrKitsune, 
			tttrButa, tttrKirin, tttrUsagiSuccess, tttrKumaSuccess, tttrRisu, tttrBirdSuccess,
			tttrTanuki, tttrUma;
	
	private TiledTextureRegion arrtttrBird[] = new TiledTextureRegion[5];
	private TiledTextureRegion arrtttrAnimals[] = new TiledTextureRegion[14];
	
	private AnimatedSprite aniBackground, aniAsaSun, aniYugataSun,
			aniStarFly, aniStar, aniFlower, aniKame,aniHapa, aniTree1,
			aniTree2, aniTree3, aniTree4, aniTree6, aniTree7, aniUsagi, aniKuma, aniKitsune, 
			aniButa, aniKirin, aniUsagiSuccess, aniKumaSuccess, aniRisu, aniBirdSuccess,
			aniTanuki, aniUma;
	
	private AnimatedSprite arraniBird[] = new AnimatedSprite[5];
	private AnimalAniSprite arraniAnimals[] = new AnimalAniSprite[14];
	private MoveModifier  moveStarModifier, moveKameModifier ;
	private MoveYModifier moveTree7;
	private SequenceEntityModifier sqMoonModifier, sqFlyBirdModifier1, sqFlyBirdModifier2;
	private int currentBgr = 1;
	private int currentOpen = -1;
	private int countOpenPoor = 0;
	private int currentTree1 = 0;
	private int currentTree2 = 0;
	private int currentTree4 = 0;
	private boolean istouchAsaSun = true;
	private boolean istouchYugataSun = false;
	private boolean istouchStar = false;
	private boolean istouchMoon = false;
	private boolean ischeckBusy = false;
	private boolean istouchTree1 = true;
	private boolean istouchTree2 = true;
	private boolean istouchTree4 = true;
	private boolean istouchTree3 = true;
	private boolean istouchTree5 = true;
	private boolean istouchTree6 = true;
	private boolean istouchTree7 = true;
	private boolean istouchKame = true;
	private boolean istouchFlower = true;
	private boolean istouchUsagi = true;
	private boolean istouchKuma = false;
	private boolean istouchBg = true;
	private boolean[] isTouchHole = new boolean[14];
	private ArrayList<Integer> arlTemp, arlAnimalsPoor;
	private float arrpointBird[][] = new float[][]{
		{119, 248, 709, 725, 826},{39, 48, 76, 4, 66}
	};  
	private float arrpointHole[][] = new float[][]{
		{337, 476, 63, 200, 337, 476, 612, 747, 63, 200, 337, 476, 612, 747},
		{100, 100, 223, 223, 223, 223, 223, 223, 342, 342, 342, 342, 342, 342}
	};  
	
	private TimerHandler timeSuccess, timeHole, timeTree, timeKame;
	/**
	 * Sounds
	 */
	private Sound A1_10_KIRA2, A1_11_KIRA5, A1_11_POTO, A1_12_NYU_CHIN,A1_13_KIRA8, A1_14_BASA2,
				A1_15_BOWAN2, A1_15_TANUKI,A1_16_BO,A1_17_HAI3,A1_17_HAI4,A1_28_KIRAN,A1_29_KIRARIN,
				A1_30_KIRA7, A1_30_SYARARAN, A1_6_PINPON2, A1_8_NYU, A1_9_MOKIN, A1_9_SYAKA, A1_HARP,
				A1_OMEDETO, A1_PACHI_YUBIBUE, A1_SYU_IN, A1_SYU_OUT;
	@Override
	protected void loadKaraokeSound() {
		A1_10_KIRA2 = loadSoundResourceFromSD(Vol3TotoroResource.A1_10_KIRA2);
		A1_11_KIRA5 = loadSoundResourceFromSD(Vol3TotoroResource.A1_11_KIRA5);
		A1_11_POTO = loadSoundResourceFromSD(Vol3TotoroResource.A1_11_POTO);
		A1_12_NYU_CHIN = loadSoundResourceFromSD(Vol3TotoroResource.A1_12_NYU_CHIN);
		A1_13_KIRA8 = loadSoundResourceFromSD(Vol3TotoroResource.A1_13_KIRA8);
		A1_14_BASA2 = loadSoundResourceFromSD(Vol3TotoroResource.A1_14_BASA2);
		A1_15_BOWAN2 = loadSoundResourceFromSD(Vol3TotoroResource.A1_15_BOWAN2);
		A1_15_TANUKI = loadSoundResourceFromSD(Vol3TotoroResource.A1_15_TANUKI);
		A1_16_BO = loadSoundResourceFromSD(Vol3TotoroResource.A1_16_BO);
		A1_17_HAI3= loadSoundResourceFromSD(Vol3TotoroResource.A1_17_HAI3);
		A1_17_HAI4 = loadSoundResourceFromSD(Vol3TotoroResource.A1_17_HAI4);
		A1_28_KIRAN = loadSoundResourceFromSD(Vol3TotoroResource.A1_28_KIRAN);
		A1_29_KIRARIN = loadSoundResourceFromSD(Vol3TotoroResource.A1_29_KIRARIN);
		A1_30_KIRA7= loadSoundResourceFromSD(Vol3TotoroResource.A1_30_KIRA7);
		A1_30_SYARARAN = loadSoundResourceFromSD(Vol3TotoroResource.A1_30_SYARARAN);
		A1_6_PINPON2= loadSoundResourceFromSD(Vol3TotoroResource.A1_6_PINPON2);
		A1_8_NYU = loadSoundResourceFromSD(Vol3TotoroResource.A1_8_NYU);
		A1_9_MOKIN = loadSoundResourceFromSD(Vol3TotoroResource.A1_9_MOKIN);
		A1_9_SYAKA = loadSoundResourceFromSD(Vol3TotoroResource.A1_9_SYAKA);
		A1_HARP = loadSoundResourceFromSD(Vol3TotoroResource.A1_HARP);
		A1_OMEDETO = loadSoundResourceFromSD(Vol3TotoroResource.A1_OMEDETO);
		A1_PACHI_YUBIBUE = loadSoundResourceFromSD(Vol3TotoroResource.A1_PACHI_YUBIBUE);
		A1_SYU_IN = loadSoundResourceFromSD(Vol3TotoroResource.A1_SYU_IN);
		A1_SYU_OUT = loadSoundResourceFromSD(Vol3TotoroResource.A1_SYU_OUT);
	}
	@Override
	public void onCreateResources() {
		Log.i(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("totoro/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("totoro/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), getAssets(), "totoro/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreateResources: ");
		totoroPack1 = mTexturePackLoaderFromSource.load("totoro1.xml");
		totoroPack1.loadTexture();
		totoroPack2 = mTexturePackLoaderFromSource.load("totoro2.xml");
		totoroPack2.loadTexture();
		libTotoroPack1 = totoroPack1.getTexturePackTextureRegionLibrary();
		libTotoroPack2 = totoroPack2.getTexturePackTextureRegionLibrary();
		this.tttrBackground = getTiledTextureFromPacker(totoroPack2, Vol3TotoroResource.PACK_BACKGROUND);
		this.ttrBGSuccess = libTotoroPack2.get(Vol3TotoroResource.A1_32_IPHONE_IWAI_HAIKEI_ID);
		// load hill
		this.ttrHill = libTotoroPack2.get(Vol3TotoroResource.A1_31_1_IPHONE_HAIKEI_ID);	
		//load Sun
		this.tttrAsaSun = getTiledTextureFromPacker(totoroPack2, 
				Vol3TotoroResource.A1_28_1_IPHONE_ASA_SUN_ID,
				Vol3TotoroResource.A1_28_2_IPHONE_ASA_SUN_ID);
		//cloud 1
		this.ttrKumo1 = libTotoroPack2.get(Vol3TotoroResource.A1_28_3_IPHONE_ASA_KUMO_ID);
		//cloud 2
		this.ttrKumo2 = libTotoroPack2.get(Vol3TotoroResource.A1_28_3_IPHONE_ASA_KUMO_ID);
		//cloud 3
		this.ttrKumo3 = libTotoroPack2.get(Vol3TotoroResource.A1_28_3_IPHONE_ASA_KUMO_ID);
		// sun
		this.tttrYugataSun = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_1_IPHONE_YUGATA_SUN_ID,
				Vol3TotoroResource.A1_29_2_IPHONE_YUGATA_SUN_ID);
		// load bird
		this.arrtttrBird[0] = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_3_1_IPHONE_YUGATA_BIRD_ID,
				Vol3TotoroResource.A1_29_3_2_IPHONE_YUGATA_BIRD_ID);
		this.arrtttrBird[1] = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_4_1_IPHONE_YUGATA_BIRD_ID,
				Vol3TotoroResource.A1_29_4_2_IPHONE_YUGATA_BIRD_ID); 
		this.arrtttrBird[2] = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_5_1_IPHONE_YUGATA_BIRD_ID,
				Vol3TotoroResource.A1_29_5_2_IPHONE_YUGATA_BIRD_ID); 
		this.arrtttrBird[3] = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_6_1_IPHONE_YUGATA_BIRD_ID,
				Vol3TotoroResource.A1_29_6_2_IPHONE_YUGATA_BIRD_ID); 
		this.arrtttrBird[4] = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_29_7_1_IPHONE_YUGATA_BIRD_ID,
				Vol3TotoroResource.A1_29_7_2_IPHONE_YUGATA_BIRD_ID); 
		//load hole
		for (int k=0; k<14; k++) {
			this.arrttrHole[k]= libTotoroPack2.get(Vol3TotoroResource.A1_31_2_IPHONE_HOLE_ID);
		}
		arlTemp = new ArrayList<Integer>();
		arlAnimalsPoor = new ArrayList<Integer>();
		for (int k =0; k<7; k++) {
			arlAnimalsPoor.add(k);
			arlAnimalsPoor.add(k);
		}
		for (int k=0; k<14; k++) {
			arlTemp.add(k);
		}
		
		this.arrtttrAnimals[0] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_FUWARIN);
		this.arrtttrAnimals[1] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_FUWARIN);
		this.arrtttrAnimals[2] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_PYONKORO);
		this.arrtttrAnimals[3] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_PYONKORO);
		this.arrtttrAnimals[4] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_HAPAPA);
		this.arrtttrAnimals[5] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_HAPAPA);
		this.arrtttrAnimals[6] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_RAPADORI);
		this.arrtttrAnimals[7] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_RAPADORI);
		this.arrtttrAnimals[8] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_KOROZOU);
		this.arrtttrAnimals[9] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_KOROZOU);
		this.arrtttrAnimals[10] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_HEABEA);
		this.arrtttrAnimals[11] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_HEABEA);
		this.arrtttrAnimals[12] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_KOROWAN);
		this.arrtttrAnimals[13] = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_KOROWAN);
		
		//load moon
		this.ttrMoon = libTotoroPack2.get(Vol3TotoroResource.A1_30_1_IPHONE_YORU_MOON_ID);
		//load star
		this.tttrStarFly = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_30_2_IPHONE_YORU_STAR_ID,
				Vol3TotoroResource.A1_30_3_IPHONE_YORU_STAR_ID);
		
		this.tttrStar = getTiledTextureFromPacker(totoroPack2,
				Vol3TotoroResource.A1_30_4_IPHONE_YORU_STAR_ID,
				Vol3TotoroResource.A1_30_5_IPHONE_YORU_STAR_ID);
		
		//load flower
		this.tttrFlower = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_MOGURA);
		//load rua
		this.tttrKame = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_KAME);
		//load hapa
		this.tttrHapa = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_HAPA);
		//load tree 1
		this.tttrTree1 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE1);
		//load tree 2
		this.tttrTree2 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE2);
		//load tree 3
		this.tttrTree3 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE3);
		//load tree 4
		this.tttrTree4 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE4);
		//load tree 5
		this.ttrTree5 = libTotoroPack1.get(Vol3TotoroResource.A1_14_1_IPHONE_TREE_ID);
		//load tree 6
		this.tttrTree6 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE6);
		//load tree 7
		this.tttrTree7 = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.PACK_TREE7);
		this.ttrTmpTree7 = libTotoroPack1.get(Vol3TotoroResource.A1_16_7_2_IPHONE_TREE_ID);
		//load fly bird 1
		this.ttrFlyBrid1 = libTotoroPack1.get(Vol3TotoroResource.A1_14_2_IPHONE_BIRD_ID);
		this.ttrFlyBrid2 = libTotoroPack1.get(Vol3TotoroResource.A1_14_2_IPHONE_BIRD_ID);
		//load usagi
		this.tttrUsagi = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_17_1_1_IPHONE_USAGI_ID,
				Vol3TotoroResource.A1_17_1_2_IPHONE_USAGI_ID, Vol3TotoroResource.A1_17_1_3_IPHONE_USAGI_ID);
		//load Kuma
		this.tttrKuma = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_17_2_1_IPHONE_KUMA_ID,
				Vol3TotoroResource.A1_17_2_2_IPHONE_KUMA_ID, Vol3TotoroResource.A1_17_2_3_IPHONE_KUMA_ID);
		//load kitsune
		this.tttrKitsune = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_18_1_IPHONE_KITSUNE_ID,
				Vol3TotoroResource.A1_18_2_IPHONE_KITSUNE_ID);
		//load buta
		this.tttrButa = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_19_1_IPHONE_BUTA_ID,
				Vol3TotoroResource.A1_19_2_IPHONE_BUTA_ID);
		//load kirin
		this.tttrKirin = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_20_1_IPHONE_KIRIN_ID,
				Vol3TotoroResource.A1_20_2_IPHONE_KIRIN_ID);
		//load usagi success
		this.tttrUsagiSuccess = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_21_1_IPHONE_USAGI_ID,
				Vol3TotoroResource.A1_21_2_IPHONE_USAGI_ID);
		//load kuma success
		this.tttrKumaSuccess = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_22_1_IPHONE_KUMA_ID,
				Vol3TotoroResource.A1_22_2_IPHONE_KUMA_ID);
		//load risu
		this.tttrRisu = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_23_1_IPHONE_RISU_ID,
				Vol3TotoroResource.A1_23_2_IPHONE_RISU_ID);
		//load bird success
		this.tttrBirdSuccess = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_24_1_IPHONE_BIRD_ID,
				Vol3TotoroResource.A1_24_2_IPHONE_BIRD_ID);
		//load tanuki
		this.tttrTanuki = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_25_1_IPHONE_TANUKI_ID,
				Vol3TotoroResource.A1_25_2_IPHONE_TANUKI_ID);
		//load Uma
		this.tttrUma = getTiledTextureFromPacker(totoroPack1, Vol3TotoroResource.A1_26_1_IPHONE_UMA_ID,
				Vol3TotoroResource.A1_26_2_IPHONE_UMA_ID);
		
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(
				aniBackground = new AnimatedSprite(0, 0, CAMERA_WIDTH,
						CAMERA_HEIGHT, tttrBackground, this.getVertexBufferObjectManager())));
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		//display cloud
		this.sKumo1 = new Sprite(0, 51, ttrKumo1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKumo1);
		this.sKumo2 = new Sprite(400, 9, ttrKumo2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKumo2);
		this.sKumo3 = new Sprite(800, 9, ttrKumo3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKumo3);
		this.aniAsaSun = new AnimatedSprite(205, 8, tttrAsaSun, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniAsaSun);
		//display sun afternoon
		this.aniYugataSun = new AnimatedSprite(319, 17, tttrYugataSun, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniYugataSun);
		aniYugataSun.setVisible(false);
		//display bird
		for (int k=0; k<5; k++) {
			this.arraniBird[k] = new AnimatedSprite(arrpointBird[0][k],
					arrpointBird[1][k], arrtttrBird[k], this.getVertexBufferObjectManager());
			this.mScene.attachChild(arraniBird[k]);
			arraniBird[k].setVisible(false);
		}
		//display moon
		this.sMoon = new Sprite(233, 9, ttrMoon, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMoon);
		sMoon.setVisible(false);
		//display star fly
		this.aniStarFly = new AnimatedSprite(396, 2, tttrStarFly, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniStarFly);
		aniStarFly.setVisible(false);
		//display star fly
		this.aniStar = new AnimatedSprite(127, 16, tttrStar, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniStar);
		aniStar.setVisible(false);
		
		this.sBGSuccess = new Sprite(0, 0, ttrBGSuccess, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBGSuccess);
		sBGSuccess.setVisible(false);
		//display usagi
		this.aniUsagi = new AnimatedSprite(426, 11, tttrUsagi, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniUsagi);
		//display Kuma
		this.aniKuma = new AnimatedSprite(426, 11, tttrKuma, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniKuma);
		aniKuma.setVisible(false);
		//display kitsune
		this.aniKitsune = new AnimatedSprite(106, 118, tttrKitsune, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniKitsune);
		aniKitsune.setVisible(false);
		//display puta
		this.aniButa = new AnimatedSprite(195, 121, tttrButa, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniButa);
		aniButa.setVisible(false);
		//display kirin
		this.aniKirin = new AnimatedSprite(308, 12, tttrKirin, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniKirin);
		aniKirin.setVisible(false);
		// display usagi success
		this.aniUsagiSuccess = new AnimatedSprite(377, 7, tttrUsagiSuccess, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniUsagiSuccess);
		aniUsagiSuccess.setVisible(false);
		// display kuma success
		this.aniKumaSuccess = new AnimatedSprite(484, 25, tttrKumaSuccess, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniKumaSuccess);
		aniKumaSuccess.setVisible(false);
		// display Risu success
		this.aniRisu = new AnimatedSprite(597, 72, tttrRisu, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniRisu);
		aniRisu.setVisible(false);
		// display brid success
		this.aniBirdSuccess = new AnimatedSprite(670, 19, tttrBirdSuccess, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniBirdSuccess);
		aniBirdSuccess.setVisible(false);
		// display tanuki success
		this.aniTanuki = new AnimatedSprite(707, 140, tttrTanuki, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniTanuki);
		aniTanuki.setVisible(false);
		// display uma success
		this.aniUma = new AnimatedSprite(805, 123, tttrUma, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniUma);
		aniUma.setVisible(false);
		//display hill
		this.sHill = new Sprite(0, 0, ttrHill, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHill);
		//display tree 1
		this.aniTree1 = new AnimatedSprite(82, 104, tttrTree1,this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree1);
		//display tree 2
		this.aniTree2 = new AnimatedSprite(170, 89, tttrTree2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree2);
		//display tree 3
		this.aniTree3 = new AnimatedSprite(290, 7, tttrTree3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree3);
		//display fly bird 1
		this.sFlyBird1 = new Sprite(664, 120, ttrFlyBrid1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlyBird1);
		//display fly bird 2
		this.sFlyBird2 = new Sprite(664, 120, ttrFlyBrid2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlyBird2);
		//display tree 4
		this.aniTree4 = new AnimatedSprite(560, 20, tttrTree4, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree4);
		//display tree 5
		this.sTree5 = new Sprite(637, 106, ttrTree5, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree5);
		//display tree 6
		this.aniTree6 = new AnimatedSprite(699, 110, tttrTree6, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree6);
		//display tree 7
		this.aniTree7 = new AnimatedSprite(808, -37, tttrTree7, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTree7);
		this.sTmpTree7 = new Sprite(808, -37, ttrTmpTree7, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTmpTree7);
		this.sTmpTree7.setVisible(false);
		//display hole
		for (int k=0; k<14; k++) {
			this.arrsHole[k] = new Sprite(arrpointHole[0][k],
					arrpointHole[1][k], arrttrHole[k], this.getVertexBufferObjectManager());
			this.mScene.attachChild(arrsHole[k]);
		}
		//display animals
		for (int k=0; k<14; k++) {
			this.arraniAnimals[k] = new AnimalAniSprite(arrpointHole[0][k], 
					arrpointHole[1][k], arrtttrAnimals[k], this.getVertexBufferObjectManager());
			
			this.arraniAnimals[k].setImgIndex(arlAnimalsPoor.get(k));
			this.mScene.attachChild(arraniAnimals[k]);
			this.arraniAnimals[k].setVisible(false);
			isTouchHole[k] = true;
		}
		//display flower
		this.aniFlower = new AnimatedSprite(66, 485, tttrFlower, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniFlower);
		//display kame
		this.aniKame = new AnimatedSprite(750, 524, tttrKame, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniKame);
		//display hapa
		this.aniHapa = new AnimatedSprite(0, 0, tttrHapa, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniHapa);
		aniHapa.setVisible(false);
		addGimmicsButton(this.mScene, Vol3TotoroResource.SOUND_GIMMIC,
				Vol3TotoroResource.IMAGE_GIMMIC, libTotoroPack1);
		this.mScene.setOnSceneTouchListener(this);
		
	}
	@Override
	public void onPauseGame() {
		resetData();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		loadDefault();
		super.onResumeGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(aniAsaSun, 14, 15, 70, 70, pX, pY)) {
				Log.d(TAG, "tap vao mat troi buoi sang: ");
				if (istouchAsaSun) {
					istouchAsaSun=false;
					touchAsaSun();
				}
			} else if (checkContains(aniYugataSun, 48, 50, 150, 114, pX, pY)) {
				if (istouchYugataSun) {
					istouchYugataSun = false;
					touchYugataSun();
					
				}
			} else if (checkContains(aniStarFly, 1, 2, 72, 63, pX, pY)){
				if (istouchStar) {
					istouchStar=false;
					touchStarFly();
				}
			} else if (checkContains(sMoon, 14, 8, 72, 85, pX, pY)) {
				Log.d(TAG, "tap Moon :");
				if (istouchMoon) {
					istouchMoon=false;
					touchMoon();
				}
			} else if (checkContains(aniTree1, 7, 14, 86, 94, pX, pY) && istouchTree1) {
				currentTree1++;
				touchTree1(currentTree1);
				if (currentTree1==2) {
					currentTree1 = -1;
				}
			} else if (checkContains(aniTree2, 26, 15, 104, 88, pX, pY) && istouchTree2 ) {
				currentTree2++;
				touchTree2(currentTree2);
				if (currentTree2==4) {
					currentTree2 = 0;
				}
			} else if (checkContains(aniTree3, 6, 85, 82, 155, pX, pY)) {
				if (istouchTree3) {
					istouchTree3=false;
					touchTree3();
				}
			} else if (checkContains(aniTree4, 14, 27, 82, 102, pX, pY) && istouchTree4) {
				currentTree4++;
				touchTree4(currentTree4);
				if (currentTree4==2) {
					currentTree4 = -1;
				}
			} else if (checkContains(sTree5, 6, 8, 70, 81, pX, pY)) {
				if (istouchTree5) {
					istouchTree5 = false;
					touchTree5();
				}
			} else if (checkContains(aniTree6, 22, 36, 96, 104, pX, pY)) {
				if (istouchTree6) {
					istouchTree6 = false;
					touchTree6();
				}
				
			} else if (checkContains(aniTree7, 4, 184, 85, 257, pX, pY)) {
				if (istouchTree7){
					istouchTree7 = false;
					touchTree7();
				}
			} else if (checkContains(aniUsagi, 26, 56, 89, 88, pX, pY)) {
				if (istouchUsagi) {
					istouchUsagi = false;
					touchUsagi();
				}
				
			} else if (checkContains(aniKuma, 22, 78, 91, 98, pX, pY)) {
				if (istouchKuma) {
					istouchKuma = false;
					touchKuma();
				}
				
			} else if (checkContains(aniBackground, 0, 0, 960, 110, pX, pY)) {
				if (istouchBg) {
					changeBackground();
				}
			} 
						
			if (checkContains(aniFlower, 22, 44, 98, 140, pX, pY)) {
				if (istouchFlower) {
					istouchFlower=false;
					A1_SYU_IN.play();
					touchFlower();
				}
			}
			if (checkContains(aniKame, 40, 15, 114, 99, pX, pY)) {
				if (istouchKame){
					istouchKame=false;
					touchKame();
				}
			}
			
			for (int k=0; k<14; k++) {
				if (isTouchHole[k] && !ischeckBusy 
						&& checkContains(arraniAnimals[k], 28, 42, 135, 148, pX, pY)) {
					Log.e(TAG,k + "current open: " + currentOpen + 
							" count Poor open: " + countOpenPoor);
					touchHole(k);
				}
			}
		}
		return false;
	}
	
	private void touchHole(int index) {
		isTouchHole[index] = false;
		arraniAnimals[index].setScale(0.0f);
		arraniAnimals[index].setVisible(true);
		A1_SYU_OUT.play();
		arraniAnimals[index].registerEntityModifier(new ScaleModifier(0.5f, 0.0f, 1.0f));
		
		if (currentOpen != -1) {
			//compare two hole opened
			Log.d(TAG, "gia tri o tap: "+arraniAnimals[index].getImgIndex()
					+"gia tri o dang mo: "+arraniAnimals[currentOpen].getImgIndex());
			if (arraniAnimals[index].getImgIndex() != arraniAnimals[currentOpen].getImgIndex()) {
				ischeckBusy=true;
				final int temp = index;
				timeHole = new TimerHandler(0.6f, false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						arraniAnimals[temp].setVisible(false);
						arraniAnimals[currentOpen].setVisible(false);
						A1_SYU_IN.play();
						isTouchHole[currentOpen] = true;
						isTouchHole[temp] = true;
						ischeckBusy = false;
						currentOpen = -1;
					}
				}); 
				this.mEngine.registerUpdateHandler(timeHole);
			} else {
				currentOpen = -1;
				countOpenPoor++;
				TimerHandler timeSound = new TimerHandler(0.3f, false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						A1_6_PINPON2.play();
						if (countOpenPoor==7) {
							//successful
							countOpenPoor = 0;
							successOpen();
						}
					}
				});
				this.mEngine.registerUpdateHandler(timeSound);
			}
		} else {
			currentOpen = index;
		}
		return ;
	}
	private void successOpen() {
		//hide tree
		Log.d(TAG, " success open ");
		timeSuccess = new TimerHandler(0.3f, false, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				A1_OMEDETO.play();
				A1_PACHI_YUBIBUE.play();
				hideTree();
				showAnimals();
				long pDuration[] = new long[]{600,600,600,600,600,600};
				int pFrame[] = new int[]{1,2,3,1,2,3};
				for (int i = 0; i < arraniAnimals.length; i++) {
					arraniAnimals[i].animate(pDuration, pFrame, 0, Vol3Totoro.this);
				}
			}
		});
		this.mEngine.registerUpdateHandler(timeSuccess);
	}
	private void afterSuccess() {
		istouchTree1 = true;
		istouchTree2 = true;
		istouchTree4 = true;
		istouchTree3 = true;
		istouchTree5 = true;
		istouchTree6 = true;
		istouchTree7 = true;
		istouchUsagi = true;
		istouchBg = true;
		istouchKuma = false;
		aniTree1.setVisible(true);
		aniTree2.setVisible(true);
		aniTree3.setVisible(true);
		aniTree4.setVisible(true);
		sTree5.setVisible(true);
		sFlyBird1.setVisible(true);
		sFlyBird1.setPosition(664, 120);
		sFlyBird2.setVisible(true);
		sFlyBird2.setPosition(664, 120);
		aniTree6.setVisible(true);
		aniTree7.setVisible(true);
		aniUsagi.setVisible(true);
		aniKuma.setVisible(false);
		sBGSuccess.setVisible(false);
		currentTree1 = 0;
		currentTree2 = 0;
		currentTree4 = 0;
		moveKame();
		moveFlower();
		istouchFlower = true;
		istouchKame = true;
		aniKitsune.setVisible(false);
		aniButa.setVisible(false);
		aniKirin.setVisible(false);
		aniUsagiSuccess.setVisible(false);
		aniKumaSuccess.setVisible(false);
		aniRisu.setVisible(false);
		aniBirdSuccess.setVisible(false);
		aniTanuki.setVisible(false);
		aniUma.setVisible(false);
		aniHapa.setVisible(false);
		aniKitsune.stopAnimation();
		aniButa.stopAnimation();
		aniKirin.stopAnimation();
		aniUsagiSuccess.stopAnimation();
		aniKumaSuccess.stopAnimation();
		aniRisu.stopAnimation();
		aniBirdSuccess.stopAnimation();
		aniTanuki.stopAnimation();
		aniUma.stopAnimation();
	}
	private void hideTree () {
		istouchTree1 = false;
		istouchTree2 = false;
		istouchTree4 = false;
		istouchTree3 = false;
		istouchTree5 = false;
		istouchTree6 = false;
		istouchTree7 = false;
		istouchUsagi = false;
		istouchKuma = false;
		istouchFlower = false;
		istouchKame = false;
		istouchBg = false;
		aniTree1.setVisible(false);
		aniTree2.setVisible(false);
		aniTree3.stopAnimation();
		aniTree3.setVisible(false);
		aniTree4.setVisible(false);
		sTree5.setVisible(false);
		
		if (sqFlyBirdModifier1 != null) {
			sFlyBird1.clearEntityModifiers();
			sFlyBird1.setRotation(0);
		}
		if (sqFlyBirdModifier2 != null) {
			sFlyBird2.clearEntityModifiers();
		}
		sFlyBird1.setVisible(false);
		sFlyBird2.setVisible(false);
		aniTree6.setVisible(false);
		aniTree7.setVisible(false);
		aniUsagi.setVisible(false);
		aniKuma.setVisible(false);
		aniTree1.setCurrentTileIndex(0);
		aniTree2.setCurrentTileIndex(0);
		aniTree3.setCurrentTileIndex(0);
		aniTree4.setCurrentTileIndex(0);
		aniTree6.stopAnimation();
		aniTree6.setCurrentTileIndex(0);
		if (moveTree7 != null) {
			sTmpTree7.clearEntityModifiers();
			sTmpTree7.setVisible(false);
		}
		aniTree7.stopAnimation();
		aniTree7.setCurrentTileIndex(0);
		aniUsagi.setCurrentTileIndex(0);
		aniKuma.setCurrentTileIndex(0);
		
	}
	private void showAnimals() {
		long pDuration[] = new long[]{400,400};
		int pFrameF[] = new int[]{5,6};
		int pFrameK[] = new int[]{6,7};
		aniKitsune.setVisible(true);
		aniButa.setVisible(true);
		aniKirin.setVisible(true);
		aniUsagiSuccess.setVisible(true);
		aniKumaSuccess.setVisible(true);
		aniRisu.setVisible(true);
		aniBirdSuccess.setVisible(true);
		aniTanuki.setVisible(true);
		aniUma.setVisible(true);
		aniHapa.setVisible(true);
		sBGSuccess.setVisible(true);
		aniKitsune.animate(400, -1);
		aniButa.animate(400, -1);
		aniKirin.animate(400, -1);
		aniUsagiSuccess.animate(400, -1);
		aniKumaSuccess.animate(400, -1);
		aniRisu.animate(400, -1);
		aniBirdSuccess.animate(400, -1);
		aniTanuki.animate(400, -1);
		aniUma.animate(400, -1);
		aniHapa.animate(600, -1);
		aniFlower.animate(pDuration, pFrameF, -1);
		aniKame.animate(pDuration, pFrameK, -1);
		
	}
	private void changeBackground() {
		A1_HARP.play();
		if (currentBgr == 0) {
			//display morning  
			aniBackground.setCurrentTileIndex(0);
			sMoon.setVisible(false);
			sMoon.clearEntityModifiers();
			istouchMoon=false;
			aniStarFly.setVisible(false);
			istouchStar = false;
			aniStar.setVisible(false);
			aniAsaSun.setVisible(true);
			istouchAsaSun=true;
			moveCloud();
			currentBgr = 1;
		} else if (currentBgr == 1) {
			// display afternoon
			sKumo1.setVisible(false);
			sKumo1.clearEntityModifiers();
			sKumo2.setVisible(false);
			sKumo2.clearEntityModifiers();
			sKumo3.setVisible(false);
			sKumo3.clearEntityModifiers();
			aniBackground.setCurrentTileIndex(1);
			aniAsaSun.stopAnimation();
			aniAsaSun.setVisible(false);
			istouchAsaSun=false;
			aniYugataSun.setVisible(true);
			istouchYugataSun=true;
			moveBrid();
			currentBgr = 2;
		} else if (currentBgr == 2) {
			//display night
			aniBackground.setCurrentTileIndex(2);
			aniYugataSun.setVisible(false);
			istouchYugataSun=false;
			aniYugataSun.stopAnimation();
			for(int k=0; k<5; k++) {
				arraniBird[k].setVisible(false);
				arraniBird[k].stopAnimation();
			}
			sMoon.setVisible(true);
			istouchMoon = true;
			aniStarFly.setVisible(true);
			istouchStar = true;
			moveStar();
			currentBgr = 0;
		}
		return;
	}
	private void touchTree1(int index) {
		A1_10_KIRA2.play();
		aniTree1.setCurrentTileIndex(index);
		return;
	}
	private void touchTree2(int index) {
		if (index < 4) {
			A1_11_KIRA5.play();
		}
		aniTree2.setCurrentTileIndex(index);
		if (index==4) {
			A1_11_POTO.play();
			 timeTree = new TimerHandler(0.4f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					mEngine.unregisterUpdateHandler(pTimerHandler);
					aniTree2.setCurrentTileIndex(0);
				}
			});
			mEngine.registerUpdateHandler(timeTree);
		}
		return;
	}
	private void touchUsagi() {
		aniUsagi.stopAnimation();
		A1_17_HAI4.play();
		aniUsagi.setCurrentTileIndex(2);
		TimerHandler timeUsagi = new TimerHandler(1.0f, false, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				aniUsagi.setVisible(false);
				aniKuma.setVisible(true);
				moveKuma();
				istouchKuma=true;
			}
		});
		mEngine.registerUpdateHandler(timeUsagi);
	}
	private void touchKuma() {
		aniKuma.stopAnimation();
		A1_17_HAI3.play();
		aniKuma.setCurrentTileIndex(2);
		TimerHandler timeKuma = new TimerHandler(1.0f, false, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				aniKuma.setVisible(false);
				aniUsagi.setVisible(true);
				moveUsagi();
				istouchUsagi=true;
			}
		});
		mEngine.registerUpdateHandler(timeKuma);
	}
	private void touchTree3() {
		long pDuration[] = new long[]{400,400,400,400,400,400};
		int pFrame[] = new int[]{1,2,3,4,5,0};
		A1_12_NYU_CHIN.play();
		aniTree3.animate(pDuration, pFrame, 0,this);
		return;
	}
	private void touchTree4(int index) {
		A1_13_KIRA8.play();
		aniTree4.setCurrentTileIndex(index);
		return;
	}
	private void touchTree5() {
		A1_14_BASA2.play();
		sFlyBird1.registerEntityModifier(sqFlyBirdModifier1 = new SequenceEntityModifier(
				new MoveModifier(1.0f, sFlyBird1.getX(), 
						sFlyBird1.getX()+30, sFlyBird1.getY(), sFlyBird1.getY()-60),
				new RotationModifier(0.5f, 0.0f, 130.0f),
				new MoveModifier(0.6f, sFlyBird1.getX()+30, sFlyBird1.getX()+60,
						sFlyBird1.getY()-60, sFlyBird1.getY()-30),
				new RotationModifier(0.5f, 130.0f, 0.0f),
				new MoveModifier(1.0f, sFlyBird1.getX()+60, sFlyBird1.getX()+100,
						sFlyBird1.getY()-30, -25)));
		sqFlyBirdModifier1.addModifierListener(this);
		sFlyBird2.registerEntityModifier(sqFlyBirdModifier2 = new SequenceEntityModifier(
				new DelayModifier(0.4f),
				new MoveModifier(0.5f, sFlyBird2.getX(), sFlyBird2.getX()+5,
						sFlyBird1.getY(), sFlyBird1.getY()-20),
				new MoveYModifier(0.3f, sFlyBird1.getY()-20, sFlyBird1.getY()-10),
				new MoveYModifier(0.3f, sFlyBird1.getY()-10, sFlyBird1.getY()-20),
				new MoveYModifier(0.3f, sFlyBird1.getY()-20, sFlyBird1.getY()-10),
				new MoveYModifier(0.3f, sFlyBird1.getY()-10, sFlyBird1.getY()-20),
				new MoveModifier(2.0f, sFlyBird2.getX()+5, sFlyBird2.getX()+70,
						sFlyBird1.getY()-20, -25)
				));
		sqFlyBirdModifier2.addModifierListener(this);
		return;
	}
	private void touchTree6() {
		long pDuration[] = new long[]{300,300,300,300,300};
		int pFrame[] = new int[]{1,2,3,4,5};
		A1_15_BOWAN2.play();
		aniTree6.animate(pDuration, pFrame, 0, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				long pDuration[] = new long[]{300,300,300,300,300};
				int pFrame[] = new int[]{6,7,6,7,0};
				A1_15_TANUKI.play();
				aniTree6.animate(pDuration,pFrame,0,Vol3Totoro.this);
			}
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {}
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {}
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
		});
		return;
	}
	private void touchTree7() {
		long pDuration[] = new long[]{300,300,300,300,300};
		int pFrame[] = new int[]{1,2,3,4,5};
		A1_16_BO.play();
		aniTree7.animate(pDuration, pFrame, 0, this);
		return;
	}
	private void touchFlower(){
		aniFlower.stopAnimation();
		A1_8_NYU.play();
		aniFlower.setCurrentTileIndex(2);
		long pDuration[] = new long[]{500,500};
		int pFrame[] = new int[]{3,4};
		aniFlower.animate(pDuration, pFrame, 1, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				istouchFlower = true;
				moveFlower();
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
		});
		return;
	}
	private void touchKame() {
		aniKame.stopAnimation();
		A1_9_MOKIN.play();
		long pDuration[] = new long[]{400, 400, 400, 200};
		int pFrame[] = new int[]{2,3,4,5};
		aniKame.animate(pDuration, pFrame, 0, new IAnimationListener() {
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				A1_9_SYAKA.play();
				aniKame.registerEntityModifier(moveKameModifier = new MoveModifier(
						1.0f, aniKame.getX(), -100, aniKame.getY(), -136)); 
				moveKameModifier.addModifierListener(Vol3Totoro.this);
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
		});
		return;
	}
	private void touchAsaSun(){
		long pDuration[] = new long[]{400, 400};
		int pFrame[] = new int[]{1,0};
		A1_28_KIRAN.play();
		aniAsaSun.animate(pDuration, pFrame, 1, this);
		return;
	}
	private void touchYugataSun() {
		long pDuration[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		A1_29_KIRARIN.play();
		aniYugataSun.animate(pDuration, pFrame, 1, this);
		
		return;
	}
	private void touchStarFly() {
		aniStarFly.setCurrentTileIndex(1);
		A1_30_SYARARAN.play();
		aniStarFly.registerEntityModifier(moveStarModifier =new MoveModifier(
				1.0f, aniStarFly.getX(), aniStarFly.getX() - 150, 
				aniStarFly.getY(), aniStarFly.getY() + 220));
		moveStarModifier.addModifierListener(this);
		return;
	}
	private void touchMoon() {
		A1_30_KIRA7.play();
		sMoon.setRotationCenter(36.0f, 2.0f);
		sMoon.registerEntityModifier(sqMoonModifier = new SequenceEntityModifier(
				new RotationModifier(0.8f, 0.0f, 15.0f),
				new RotationModifier(0.8f, 15.0f, -15.0f),
				new RotationModifier(0.8f, -15.0f, 0.0f)));
		sqMoonModifier.addModifierListener(this);
		return;
	}
	
	private void moveUsagi() {
		long pDuration[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		aniUsagi.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveKuma() {
		long pDuration[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		aniKuma.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveFlower() {
		long pDuration[] = new long[]{500,500};
		int pFrame[] = new int[]{1,0};
		aniFlower.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveKame() {
		long pDuration[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		aniKame.setAlpha(1.0f);
		aniKame.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveStar() {
		long pDuration[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		aniStar.setVisible(true);
		aniStar.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveBrid() {
		long pDuration[] = new long[]{600,600};
		int pFrame[] = new int[]{1,0};
		for(int k=0; k<5; k++) {
			arraniBird[k].setVisible(true);
			arraniBird[k].animate(pDuration, pFrame, -1);
		}
		return;
	}
	private void moveCloud() {
		sKumo1.setVisible(true);
		sKumo1.setPosition(0, 51);
		sKumo2.setVisible(true);
		sKumo2.setPosition(400, 9);
		sKumo3.setVisible(true);
		sKumo3.setPosition(800, 9);
		sKumo1.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
				new MoveXModifier(2.0f, 0, -122),
				new MoveXModifier(18.0f, 1082, 0))));
		sKumo2.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
				new MoveXModifier(8.0f, 400, -122),
				new MoveXModifier(12.0f, 1082, 400))));
		sKumo3.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
				new MoveXModifier(16.0f, 800, -122),
				new MoveXModifier(5.0f, 1082, 800))));
		return;
	}
	
	private void resetData() {
		if (aniBackground != null) {
			aniBackground.setCurrentTileIndex(0);
			sBGSuccess.setVisible(false);
			sMoon.setVisible(false);
			sMoon.clearEntityModifiers();
			istouchMoon=false;
			aniStarFly.setVisible(false);
			istouchStar = false;
			aniStar.setVisible(false);
			aniYugataSun.setVisible(false);
			istouchYugataSun=false;
			aniYugataSun.stopAnimation();
			for(int k=0; k<5; k++) {
				arraniBird[k].setVisible(false);
				arraniBird[k].stopAnimation();
			}
			
			aniAsaSun.setVisible(true);
			istouchAsaSun=true;
			moveCloud();
			currentBgr = 1;
		}
		//Reset hole
		currentOpen = -1;
		countOpenPoor = 0;
		ischeckBusy = false;
		istouchBg = true;
		if (A1_OMEDETO != null) {
			A1_OMEDETO.stop();
			A1_PACHI_YUBIBUE.stop();
		}
		for (int k=0; k<14; k++) {
			if (arraniAnimals[k]!= null) {
				arraniAnimals[k].stopAnimation();
				arraniAnimals[k].setCurrentTileIndex(0);
				arraniAnimals[k].setVisible(false);
			}
			isTouchHole[k]=true;
		}
		if (aniTree1 != null) {
			currentTree1 = 0;
			istouchTree1 = true;
			aniTree1.setCurrentTileIndex(0);
			aniTree1.setVisible(true);
			aniKitsune.setVisible(false);
			aniKitsune.stopAnimation();
		}
		if (aniTree2 != null){
			currentTree2 = 0;
			istouchTree2 = true;
			aniTree2.setCurrentTileIndex(0);
			aniTree2.setVisible(true);
			aniButa.setVisible(false);
			aniButa.stopAnimation();
		}
		if (aniTree3 != null) {
			aniTree3.stopAnimation();
			istouchTree3 = true;
			aniTree3.setCurrentTileIndex(0);
			aniTree3.setVisible(true);
			aniKirin.setVisible(false);
			aniKirin.stopAnimation();
		}
		if (aniTree4 != null) {
			currentTree4 = 0;
			istouchTree4 = true;
			aniTree4.setCurrentTileIndex(0);
			aniTree4.setVisible(true);
			aniUsagiSuccess.setVisible(false);
			aniUsagiSuccess.stopAnimation();
			aniKumaSuccess.setVisible(false);
			aniKumaSuccess.stopAnimation();
		}
		if (sTree5 != null) {
			istouchTree5 = true;
			sTree5.setVisible(true);
			sFlyBird1.clearEntityModifiers();
			sFlyBird1.setRotation(0);
			sFlyBird2.clearEntityModifiers();
			sFlyBird1.setVisible(true);
			sFlyBird2.setVisible(true);
			aniBirdSuccess.setVisible(false);
			aniBirdSuccess.stopAnimation();
			sFlyBird1.setPosition(664, 120);
			sFlyBird2.setPosition(664, 120);
		}
		if (aniHapa != null) {
			aniHapa.setVisible(false);
			aniHapa.stopAnimation();
		}
		if (aniTree6 != null) {
			istouchTree6 = true;
			aniTree6.stopAnimation();
			aniTree6.setCurrentTileIndex(0);
			aniTree6.setVisible(true);
			aniRisu.setVisible(false);
			aniRisu.stopAnimation();
			aniTanuki.setVisible(false);
			aniTanuki.stopAnimation();
		}
		if (aniTree7 != null) {
			istouchTree7 = true;
			aniTree7.stopAnimation();
			aniTree7.setCurrentTileIndex(0);
			aniTree7.setVisible(true);
			aniUma.setVisible(false);
			aniUma.stopAnimation();
			sTmpTree7.setVisible(false);
			sTmpTree7.clearEntityModifiers();
			sTmpTree7.setPosition(808, -37);
		}
		if (aniFlower != null) {
			istouchFlower = true;
		}
		if (aniKame != null) {
			istouchKame = true;
		}
		if (aniUsagi != null){
			aniKuma.setVisible(false);
			istouchKuma=false;
			aniUsagi.setVisible(true);
			istouchUsagi = true;
		}
	}
	private void loadDefault() {
		moveCloud();
		moveFlower();
		moveKame();
		moveUsagi();
		loadRandomAnimal();
	}
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		
		if (pAnimatedSprite == aniAsaSun) {
			istouchAsaSun = true;
		}
		if (pAnimatedSprite==aniYugataSun) {
			istouchYugataSun = true;
		}
		if (pAnimatedSprite==aniTree3){
			istouchTree3 = true;
		}
		if (pAnimatedSprite == aniTree6) {
			istouchTree6 = true;
		}
		if (pAnimatedSprite == aniTree7) {
			aniTree7.setCurrentTileIndex(6);
			sTmpTree7.setVisible(true);
			sTmpTree7.registerEntityModifier(moveTree7 = new MoveYModifier(
					1.0f, sTmpTree7.getY(), -250));
			moveTree7.addModifierListener(this);
		}
		if (pAnimatedSprite==arraniAnimals[13]){
			afterSuccess();
			loadRandomAnimal();
		}
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(moveKameModifier)) {
			timeKame = new TimerHandler(0.4f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					mEngine.unregisterUpdateHandler(pTimerHandler);
					aniKame.setPosition(750, 524);
					moveKame();
				}
			});
			this.mEngine.registerUpdateHandler(timeKame);
		}
		if (pModifier.equals(moveStarModifier)) {
			aniStarFly.setCurrentTileIndex(0);
			aniStarFly.setPosition(396, 2);
			istouchStar = true;
		}
		if (pModifier.equals(sqMoonModifier)) {
			istouchMoon = true;
		}
		if (pModifier.equals(moveTree7)) {
			istouchTree7 = true;
			aniTree7.setCurrentTileIndex(0);
			sTmpTree7.setVisible(false);
			sTmpTree7.setPosition(808, -37);
		}
		if (pModifier.equals(moveKameModifier)) {
			istouchKame = true;
		}
		if (pModifier.equals(sqFlyBirdModifier1)) {
			sFlyBird1.setPosition(664, 120);
		}
		if (pModifier.equals(sqFlyBirdModifier2)) {
			sFlyBird2.setPosition(664, 120);
			istouchTree5 = true;
		}
	}
	private void loadRandomAnimal() {
		Collections.shuffle(arlTemp);
		for (int k=0; k<14; k++) {
			arraniAnimals[arlTemp.get(k)].setPosition(arrpointHole[0][k], 
					arrpointHole[1][k]);
			arraniAnimals[arlTemp.get(k)].setVisible(false);
			arraniAnimals[arlTemp.get(k)].setCurrentTileIndex(0);
			isTouchHole[k]=true;
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		if (istouchBg) {
			changeBackground();
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
	
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
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
