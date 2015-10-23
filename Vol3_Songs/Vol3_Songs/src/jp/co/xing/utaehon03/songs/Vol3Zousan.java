
package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
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
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseCubicInOut;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ZousanResource;

import android.util.Log;

public class Vol3Zousan extends BaseGameActivity implements 
	IOnSceneTouchListener, IEntityModifierListener,IAnimationListener {
	private static final String TAG = "Vol3 Zousan";
	
	private TexturePack ttpZousan, ttpZousan1;
	private TexturePackTextureRegionLibrary ttpLibZousan, ttpLibZousan1;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion ttrBackground1,ttrBackground2,ttrBackground3,ttrIke, ttrGlass, 
			ttrTree,ttrFruitLeft, ttrFruitCenter, ttrFruitRight,ttrLeaf,ttrMama2, ttrZebra;
	private Sprite sBackground1,sBackground2,sBackground3, sIke, sGlass,
			sTree, sFruitLeft, sFruitCenter, sFruitRight, sLeaf, sMama2, sZebra;
	
	private TiledTextureRegion tttrZou, tttrZouChild,tttrChild,tttrMama1,tttrBird,
			tttrKabaPink,tttrKabaBlue, tttrLion, tttrTail, tttrGiraffe, tttrCheetah,
			tttrImpala;
	private AnimatedSprite aniZou, aniZouChild,aniChild,aniMama1,aniBird,
			aniKabaPink,aniKabaBlue, aniLion, aniTail, aniGiraffe, aniCheetah,
			aniImpala;
	
	private int checkBg = 1;
	private int currentGiraffe = 0;
	private int currentLion = 0;
	private int currentZou = 1;
	private int currentChild = 0;
	private boolean istouchImpala = true;
	private boolean istouchZebra = true;
	private boolean istouchMama = true;
	private boolean istouchChild = true;
	private boolean istouchTail = true;
	private boolean istouchBird = true;
	private boolean istouchKabaPink = true;
	private boolean istouchKabaBlue= true;
	private boolean istouchZou = true;
	private Timer timerZou = new Timer();
	private Timer timerTail = new Timer();
	private Timer timerZouSound = new Timer();
	private Timer timeGimmic = new Timer();
	private Timer timeTouchZou = new Timer();
	private Timer timeZouLast = new Timer();
	private Timer timeKabaPink = new Timer();
	private SequenceEntityModifier seZebraModifier, seFruitModifier;
	private RanDomNoRepeat randomKabaPink;
	private float arrPointLion[][] = {
			{26, 56, 75, 99, 113, 126, 130, 149, 178, 203, 210, 225,
				229, 205, 192, 174, 138, 110, 83, 57, 28, 4, 0, 5, 13, 16},
			{15, 3, 1, 6, 13, 31, 49, 48, 42, 57, 87, 109, 116, 116,
				114, 96, 96, 116, 110, 124, 113, 90, 72, 41, 30, 25}
			};
	private float arrPointLeaf[][] = {
			{142, 95, 68, 17, 9, 4, 35, 73, 102, 105, 75, 32, 7, 6, 38, 89, 113, 133,
				150, 149, 168, 202, 250, 304, 301, 243, 295, 306, 302, 271, 251, 224},
			{138, 127, 127, 168, 177, 142, 93, 74, 78, 76, 55, 49, 62, 34, 3, 4, 16,
				41, 92, 103, 67, 28, 9, 7, 14, 77, 112, 156, 201, 250, 187, 138}
	};
	/**
	 * sound
	 */
	private Sound E00022_TALKINGDRUM, E00025_WAVE, E00029_SWING, E00053_PYON,
			E00137_PYON2_ZOUSAN, E00151_PAON, E00152_ZABA, E00153_RINGO, E00154_KASA,
			E00155_BASA2, E00156_NAMIKARUI, E00157_DURURUN, E00158_SOUGEN, E00159_HARP;
	
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("zousan/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("zousan/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
                        getTextureManager(), pAssetManager, "zousan/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		try {
			ttpZousan = mTexturePackLoaderFromSource.load("zousan1.xml");
			ttpZousan.loadTexture();
			ttpLibZousan = ttpZousan.getTexturePackTextureRegionLibrary();
			ttpZousan1 = mTexturePackLoaderFromSource.load("zousan2.xml");
			ttpZousan1.loadTexture();
			ttpLibZousan1 = ttpZousan1.getTexturePackTextureRegionLibrary();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		//load background
		this.ttrBackground1= ttpLibZousan1.get(Vol3ZousanResource.A6_14_1_IPHONE4_BG_DAY_ID);
		this.ttrBackground2= ttpLibZousan1.get(Vol3ZousanResource.A6_14_2_IPHONE4_BG_EVENING_ID);
		this.ttrBackground3= ttpLibZousan1.get(Vol3ZousanResource.A6_14_3_IPHONE4_BG_NIGHT_ID);
		
		this.ttrIke= ttpLibZousan.get(Vol3ZousanResource.A6_17_IPHONE4_IKE_ID);
		this.ttrGlass= ttpLibZousan.get(Vol3ZousanResource.A6_15_IPHONE4_GLASS_ID);
		this.ttrTree= ttpLibZousan.get(Vol3ZousanResource.A6_06_3_IPHONE4_TREE_ID);
		this.ttrFruitLeft= ttpLibZousan.get(Vol3ZousanResource.A6_06_4_IPHONE4_FRUIT_LEFT_ID);
		this.ttrFruitCenter= ttpLibZousan.get(Vol3ZousanResource.A6_06_5_IPHONE4_FRUIT_CENTER_ID);
		this.ttrFruitRight= ttpLibZousan.get(Vol3ZousanResource.A6_06_6_IPHONE4_FRUIT_RIGHT_ID);
		this.ttrLeaf= ttpLibZousan.get(Vol3ZousanResource.A6_07_1_IPHONE4_LEAF_ID);
		this.ttrMama2= ttpLibZousan.get(Vol3ZousanResource.A6_06_2_1_IPHONE4_MAMA2_ID);
		this.ttrZebra= ttpLibZousan.get(Vol3ZousanResource.A6_16_IPHONE4_ZEBRA_ID);
		
		this.tttrChild = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.PACK_CHILD);
		
		this.tttrZou = getTiledTextureFromPacker(ttpZousan, 
				Vol3ZousanResource.PACK_ZOU ); 
		
		this.tttrZouChild = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_05_6_IPHONE4_ZOU1_ID,
				Vol3ZousanResource.A6_05_7_IPHONE4_ZOU2_ID,
				Vol3ZousanResource.A6_05_8_IPHONE4_ZOU3_ID);
				
		this.tttrMama1 = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_06_1_IPHONE4_MAMA1_ID,
				Vol3ZousanResource.A6_06_2_IPHONE4_MAMA2_ID);
		this.tttrBird = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_07_2_IPHONE4_BIRD1_ID,
				Vol3ZousanResource.A6_07_3_IPHONE4_BIRD2_ID);
		this.tttrKabaPink = getTiledTextureFromPacker(ttpZousan, 
				Vol3ZousanResource.PACK_PINK_KAPA);
		this.tttrKabaBlue = getTiledTextureFromPacker(ttpZousan, 
				Vol3ZousanResource.A6_09_1_IPHONE4_BLUE_KABA1_ID,
				Vol3ZousanResource.A6_09_2_IPHONE4_BLUE_KABA1_ID);  
		this.tttrLion = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_10_1_IPHONE4_LION1_ID,
				Vol3ZousanResource.A6_10_2_IPHONE4_LION2_ID,
				Vol3ZousanResource.A6_10_3_IPHONE4_LION3_ID);
		this.tttrTail = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_11_1_IPHONE4_TAIL1_ID,
				Vol3ZousanResource.A6_11_2_IPHONE4_TAIL2_ID,
				Vol3ZousanResource.A6_11_3_IPHONE4_LION_FACE_ID);
		this.tttrGiraffe = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_12_1_IPHONE4_GIRAFFE1_ID,
				Vol3ZousanResource.A6_12_2_IPHONE4_GIRAFFE2_ID,
				Vol3ZousanResource.A6_12_3_IPHONE4_GIRAFFE3_ID);
		this.tttrCheetah = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_13_3_IPHONE4_CHEETAH1_ID,
				Vol3ZousanResource.A6_13_4_IPHONE4_CHEETAH2_ID);
		
		this.tttrImpala = getTiledTextureFromPacker(ttpZousan,
				Vol3ZousanResource.A6_13_1_IPHONE4_IMPALA1_ID,
				Vol3ZousanResource.A6_13_2_IPHONE4_IMPALA2_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		this.sBackground1 = new Sprite(0, 0, ttrBackground1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground1);
		this.sBackground2 = new Sprite(0, 0, ttrBackground2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground2);
		this.sBackground2.setVisible(false);
		this.sBackground3 = new Sprite(0, 0, ttrBackground3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground3);
		this.sBackground3.setVisible(false);
		this.aniImpala = new AnimatedSprite(457, 3, tttrImpala, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniImpala);
		this.aniCheetah = new AnimatedSprite(960, 62, tttrCheetah, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniCheetah);
		this.sZebra = new Sprite(364, 63, ttrZebra, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sZebra);
		this.aniGiraffe = new AnimatedSprite(683, 3, tttrGiraffe, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniGiraffe);
		this.aniTail = new AnimatedSprite(580, 162, tttrTail, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTail);
		this.aniLion = new AnimatedSprite(451, 151, tttrLion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniLion);
		this.sIke = new Sprite(120, 211, ttrIke, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sIke);
		this.aniKabaPink = new AnimatedSprite(161, 202, tttrKabaPink, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniKabaPink);
		this.aniKabaBlue = new AnimatedSprite(355, 284, tttrKabaBlue, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniKabaBlue);
		this.sTree = new Sprite(0, 150, ttrTree, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree);
		
		this.sFruitLeft = new Sprite(80, 144, ttrFruitLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFruitLeft);
		
		this.aniBird = new AnimatedSprite(241, 120, tttrBird, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniBird);
		this.sLeaf = new Sprite(5, 14, ttrLeaf, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeaf);
		this.aniMama1 = new AnimatedSprite(61, 307, tttrMama1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniMama1);
		this.sFruitCenter = new Sprite(98, 148, ttrFruitCenter, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFruitCenter);
		this.sFruitRight = new Sprite(141, 151, ttrFruitRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFruitRight);
		this.sMama2 = new Sprite(86, 402, ttrMama2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMama2);
		this.aniZou = new AnimatedSprite(521, 173, tttrZou, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniZou);
		this.aniZouChild = new AnimatedSprite(330, 375, tttrZouChild, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniZouChild);
		this.aniChild = new AnimatedSprite(201, 350, tttrChild, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniChild);
		this.sGlass = new Sprite(0, 499, ttrGlass, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGlass);
		addGimmicsButton(mScene, Vol3ZousanResource.SOUND_GIMMIC,
				Vol3ZousanResource.IMAGE_GIMMIC, ttpLibZousan );
		this.mScene.setOnSceneTouchListener(this);
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContains(aniImpala, 8, 34, 121, 114, pX, pY)) {
				if (istouchImpala) {
					istouchImpala = false;
					touchImpala();
				}
			} else if (checkContains(aniGiraffe, 5, 30, 176, 218, pX, pY)) {
				currentGiraffe++;
				Log.d(TAG, "tap vao Giraffe ");
				E00022_TALKINGDRUM.play();
				aniGiraffe.setCurrentTileIndex(currentGiraffe);
				if (currentGiraffe == 2) {
					currentGiraffe = -1;
				}
				
			} else if (checkContainsPolygon(sLeaf, arrPointLeaf, 31, pX, pY)) {
				if (istouchBird){
					istouchBird=false;
					touchBird();
				}
			} else if (checkContains(sBackground1, 6, 6, 952, 104, pX, pY) && checkBg==1) {
				Log.d(TAG, "buoi chieu ");
				touchBackgroucd(1);
				checkBg = 2;
			} else if (checkContains(sBackground2, 6, 6, 952, 104, pX, pY) && checkBg==2) {
				Log.d(TAG, "buoi toi ");
				touchBackgroucd(2);
				checkBg = 0;
			} else if (checkContains(sBackground3, 6, 6, 952, 104, pX, pY) && checkBg==0) {
				Log.d(TAG, "buoi sang ");
				touchBackgroucd(0);
				checkBg = 1;
			}
			
			if (checkContains(sZebra, 4, 16, 85, 138, pX, pY)) {
				if (istouchZebra) {
					istouchZebra=false;
					touchZebra();
				}
			}
			
			if (checkContains(aniTail, 8, 4, 46, 41, pX, pY)) {
				if (istouchTail) {
					istouchTail=false;
					touchTail();
				}
			} 
			if (checkContainsPolygon(aniLion, arrPointLion, 25, pX, pY)) {
				currentLion++;
				Log.d(TAG, "tap vao Lion ");
				E00157_DURURUN.play();
				aniLion.setCurrentTileIndex(currentLion);
				if (currentLion == 2) {
					currentLion = -1;
				}
			}
			if (checkContains(aniZou, 56, 114, 390, 353, pX, pY)) {
				if (istouchZou) {
					istouchZou = false;
					Log.d(TAG, "tap vao zou father ");
					touchZou();
					timeTouchZou.schedule(new TimerTask() {
						@Override
						public void run() {
							istouchZou = true;
						}
					}, 800);
				}
			}
			if (checkContains(aniKabaBlue, 38, 29, 106, 74, pX, pY)) {
				if (istouchKabaBlue) {
					istouchKabaBlue=false;
					long pDurations[] = new long[]{1000,400};
					int pFrame[] = new int[]{1,0};
					E00156_NAMIKARUI.play();
					aniKabaBlue.animate(pDurations, pFrame, 0,this);
				}
			}
			
			if (checkContains(aniKabaPink, 64, 57, 166, 121, pX, pY)) {
				if (istouchKabaPink) {
					istouchKabaPink = false;
					touchKabaPink();
				}
			}
			if (checkContains(sFruitCenter, 2, 2, 60, 46, pX, pY)||
					checkContains(aniMama1, 42, 6, 128, 198, pX, pY)) {
				if (istouchMama) {
					istouchMama = false;
					touchMama();
				}
			}
			if (checkContains(aniChild, 14, 14, 113, 169, pX, pY)||
					checkContains(aniZouChild, 56, 12, 204, 119, pX, pY)) {
				if (istouchChild) {
					istouchChild=false;
					touchChild(currentChild);
					currentChild++;
					if (currentChild==3) {
						currentChild = 0;
					}
				}
			}
		}
		return false;
	}
	private void touchBird() {
		aniBird.animate(400, -1);
		E00155_BASA2.play();
		aniBird.registerEntityModifier(new MoveModifier(2.0f, aniBird.getX(), 
				aniBird.getX() + 250, aniBird.getY(), -50, new IEntityModifierListener() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				aniBird.setPosition(241, 120);
				istouchBird = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		}));
		return;
	}
	private void touchTail() {
		aniTail.stopAnimation();
		aniTail.setCurrentTileIndex(2);
		E00053_PYON.play();

		timerTail.schedule(new TimerTask() {
			@Override
			public void run() {
				istouchTail = true;
				moveTail();
			}
		}, 600);
		return;
	}
	private void touchChild(int index) {
		long pDurationsChild[] = new long[]{1200,600,300};
		E00029_SWING.play();
		timerZouSound.schedule(new TimerTask() {
			@Override
			public void run() {
				E00153_RINGO.play();
			}
		}, 1200);
		switch (index) {
		case 0:
			int pFrame1[] = new int[]{1,4,0};
			aniChild.animate(pDurationsChild, pFrame1, 0,this);
			break;
		case 1:
			int pFrame2[] = new int[]{2,4,0};
			aniChild.animate(pDurationsChild, pFrame2, 0,this);
			break;
		case 2:
			int pFrame3[] = new int[]{3,4,0};
			aniChild.animate(pDurationsChild, pFrame3, 0,this);
			break;
		default:
			break;
		}
		timerZou.schedule(new TimerTask() {
			@Override
			public void run() {
				long pDurations[] = new long[]{400,700,400};
				int pFrame[] = new int[]{1,2,0};
				aniZouChild.animate(pDurations, pFrame, 0);
			}
		}, 300);
		return;
	}
	private void touchMama() {
		E00154_KASA.play();
		aniMama1.setCurrentTileIndex(1);
		sFruitCenter.registerEntityModifier(seFruitModifier = new SequenceEntityModifier(new MoveYModifier(1.2f, sFruitCenter.getY(),
				sMama2.getY()- (sFruitCenter.getHeight()*2/3)+ sMama2.getHeight()*2/3,
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						sFruitCenter.setAlpha(0.0f);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {}
				}, EaseCubicInOut.getInstance()), new DelayModifier(0.5f)));
		seFruitModifier.addModifierListener(this);
		return;
	}
	private void touchKabaPink() {
		E00025_WAVE.play();
		timeKabaPink.schedule(new TimerTask() {
			@Override
			public void run() {
				istouchKabaPink=true;
			}
		},1000);
		randomKabaPink.setLenghNoRepeat(3);
		int id = randomKabaPink.getNextIntNoRepeat(true);
		long pDurations[] = new long[]{1000,400};
		switch (id) {
		case 0:
			int pFrame[] = new int[]{1,0};
			aniKabaPink.animate(pDurations, pFrame, 0);
			break;
		case 1:
			int pFrame1[] = new int[]{2,0};
			aniKabaPink.animate(pDurations, pFrame1, 0);
			break;
		case 2:
			int pFrame2[] = new int[]{3,0};
			aniKabaPink.animate(pDurations, pFrame2, 0);
			break;
		}
		return;
	}
	private void touchZou() {
		
		if (currentZou==1) {
			E00151_PAON.play();
			aniZou.setCurrentTileIndex(currentZou);
			currentZou = 2;
		} else if (currentZou==2) {
			E00152_ZABA.play();
			aniZou.setCurrentTileIndex(currentZou);
			currentZou = 3;
		} else if (currentZou==3) {
			E00152_ZABA.play();
			aniZou.setCurrentTileIndex(currentZou);
			timeZouLast.schedule(new TimerTask() {
				@Override
				public void run() {
					aniZou.setCurrentTileIndex(0);
					currentZou=1;
				}
			},800);
		}
		return;
	}
	private void touchZebra () {
		E00137_PYON2_ZOUSAN.play();
		sZebra.registerEntityModifier(seZebraModifier = new SequenceEntityModifier(
				new MoveYModifier(0.5f, sZebra.getY(), sZebra.getY() - 50),
				new MoveYModifier(0.5f, sZebra.getY() - 50, sZebra.getY())));
		seZebraModifier.addModifierListener(this);
		return;
	}
	private void touchImpala() {
		E00158_SOUGEN.play();
		aniImpala.animate(400,-1);
		aniImpala.registerEntityModifier( new MoveXModifier(
				1.5f, aniImpala.getX(), -125));
		aniCheetah.animate(400, -1);
		aniCheetah.registerEntityModifier(new MoveXModifier(2.5f, aniCheetah.getX(), 
				-144, new IEntityModifierListener() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				aniImpala.stopAnimation();
				aniImpala.setPosition(457, 3);
				aniCheetah.stopAnimation();
				aniCheetah.setPosition(960, 62);
				istouchImpala = true;
			}
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
		}));
	}
	private void touchBackgroucd(int index) {
		E00159_HARP.play();
		switch (index) {
		case 0:
			//sang
			sBackground1.setVisible(true);
			sBackground2.setVisible(false);
			sBackground3.setVisible(false);
			break;
		case 1:
			//chieu
			sBackground1.setVisible(false);
			sBackground2.setVisible(true);
			sBackground3.setVisible(false);
			break;
		case 2:
			//toi
			sBackground1.setVisible(false);
			sBackground2.setVisible(false);
			sBackground3.setVisible(true);
			break;
		}
		return;
	}
	private void moveTail() {
		long pDurations[] = new long[]{500,500};
		int pFrame[] = new int[]{1,0};
		aniTail.animate(pDurations, pFrame, -1);
		return;
	}
	private void resetData() {
		checkBg = 1;
		currentGiraffe = 0;
		currentLion = 0;
		currentZou = 1;
		currentChild = 0;
		istouchImpala = true;
		istouchZebra = true;
		istouchMama = true;
		istouchChild = true;
		istouchTail = true;
		istouchBird = true;
		istouchKabaPink = true;
		istouchKabaBlue = true;
		istouchZou = true;
		if (sBackground1 != null) {
			sBackground1.setVisible(true);
			sBackground2.setVisible(false);
			sBackground3.setVisible(false);
		}
		if (aniZou != null) {
			aniZou.setCurrentTileIndex(0);
		}
		if (aniZouChild != null){
			aniZouChild.setCurrentTileIndex(0);
		}
		if (aniChild != null) {
			aniChild.setCurrentTileIndex(0);
		}
		if (aniMama1 != null) {
			aniMama1.setCurrentTileIndex(0);
		}
		if (aniKabaBlue != null) {
			aniKabaBlue.setCurrentTileIndex(0);
		}
		if (aniKabaPink != null) {
			aniKabaPink.setCurrentTileIndex(0);
		}
		if (aniLion != null) {
			aniLion.setCurrentTileIndex(0);
		}
		if (aniBird != null) {
			aniBird.clearEntityModifiers();
			aniBird.stopAnimation();
			aniBird.setPosition(241, 120);
		}
		if (sZebra != null) {
			sZebra.clearEntityModifiers();
			sZebra.setPosition(364, (float)62.58);
		}
		if (aniImpala != null) {
			aniImpala.stopAnimation();
			aniImpala.clearEntityModifiers();
			aniImpala.setCurrentTileIndex(0);
			aniImpala.setPosition(457, 3);
			aniCheetah.stopAnimation();
			aniCheetah.clearEntityModifiers();
			aniCheetah.setCurrentTileIndex(0);
			aniCheetah.setPosition(960, 62);
		}
		if (aniGiraffe != null) {
			aniGiraffe.setCurrentTileIndex(0);
		}
		if (sFruitCenter!=null) {
			sFruitCenter.clearEntityModifiers();
			sFruitCenter.setPosition(98, 148);
		}
		return;
	}
	@Override
	public void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		checkBg = 1;
		randomKabaPink= new RanDomNoRepeat();
		moveTail();
		super.onResumeGame();
	}
	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		Log.e(TAG, "onPauseGame: rr");
		resetData();
		super.onPauseGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.e(TAG, "loadKaraokeComplete: ");
		super.loadKaraokeComplete();
	}
	
	@Override
	protected void loadKaraokeSound() {
		Log.e(TAG, "loadKaraokeSound: ");
		E00022_TALKINGDRUM = loadSoundResourceFromSD(Vol3ZousanResource.E00022_TALKINGDRUM);
		E00025_WAVE = loadSoundResourceFromSD(Vol3ZousanResource.E00025_WAVE);
		E00029_SWING = loadSoundResourceFromSD(Vol3ZousanResource.E00029_SWING);
		E00053_PYON = loadSoundResourceFromSD(Vol3ZousanResource.E00053_PYON);
		E00137_PYON2_ZOUSAN = loadSoundResourceFromSD(Vol3ZousanResource.E00137_PYON2_ZOUSAN);
		E00151_PAON = loadSoundResourceFromSD(Vol3ZousanResource.E00151_PAON);
		E00152_ZABA = loadSoundResourceFromSD(Vol3ZousanResource.E00152_ZABA);
		E00153_RINGO = loadSoundResourceFromSD(Vol3ZousanResource.E00153_RINGO);
		E00154_KASA = loadSoundResourceFromSD(Vol3ZousanResource.E00154_KASA);
		E00155_BASA2 = loadSoundResourceFromSD(Vol3ZousanResource.E00155_BASA2);
		E00156_NAMIKARUI = loadSoundResourceFromSD(Vol3ZousanResource.E00156_NAMIKARUI);
		E00157_DURURUN = loadSoundResourceFromSD(Vol3ZousanResource.E00157_DURURUN);
		E00158_SOUGEN = loadSoundResourceFromSD(Vol3ZousanResource.E00158_SOUGEN);
		E00159_HARP = loadSoundResourceFromSD(Vol3ZousanResource.E00159_HARP);
		Log.d("SOUND","OKDDDDDD");
	}
	@Override
	public void combineGimmic3WithAction() {
		if (currentZou == 1&& isTouchGimmic[2]){
			isTouchGimmic[2]=false;
			istouchZou=false;
			E00151_PAON.play();
			aniZou.setCurrentTileIndex(currentZou);
			timeGimmic.schedule(new TimerTask() {
				@Override
				public void run() {
					isTouchGimmic[2] = true;
					istouchZou=true;
					aniZou.setCurrentTileIndex(0);
				}
			}, 1000);
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier == seZebraModifier) {
			istouchZebra = true;
		}
		if (pModifier.equals(seFruitModifier)){
			istouchMama = true;
			sFruitCenter.setAlpha(1.0f);
			sFruitCenter.setPosition(98, 148);
			aniMama1.setCurrentTileIndex(0);
		}
	}
	
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// TODO Auto-generated method stub
		if (pAnimatedSprite == aniChild) {
			istouchChild = true;
		}
		if (pAnimatedSprite == aniKabaBlue) {
			istouchKabaBlue = true;
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
