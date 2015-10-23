/* Vol3Katatsumuri.java
* Create on May 14, 2012
*/
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KatatsumuriResource;

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
import org.andengine.entity.modifier.MoveYModifier;
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

public class Vol3Katatsumuri extends BaseGameActivity implements 
IOnSceneTouchListener, IEntityModifierListener,IAnimationListener {
	private static final String TAG = " Vol3Katatsumuri ";
	private TexturePack ttpKatatsumuri, ttpKatatsumuri1;
	private TexturePackTextureRegionLibrary ttpLibKatatsumuri, ttpLibKatatsumuri1;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion trBackground, trOkaBack, trBigLeaf, trFlowerLeaf, trShizuku, trMizutamari, 
			trKumo, trTerubouzu, trFlower1, trFlower2, trFlower3, trFlower4 ;
	private Sprite sBackground, sOkaBack, sBigleaf, sFlowerLeaf, sShizuku, sMizutamari,
			sKumo, sTerubouzu, sFlower1, sFlower2, sFlower3, sFlower4;
	private TiledTextureRegion ttrChildmae, ttrSnailRight, ttrSnailLeftTop, ttrSnailLeftUnder, ttrRainbow;
	private AnimatedSprite aniChildmae, aniSnailRight, aniSnailLeftTop, aniSnailLeftUnder, aniRainbow;
	private int arrPointChild[][] = {
			{440, 415, 300, 312, 358, 332, 310},
			{330, 215, 130, 60, 20, 60, 140}
	};
	private float arrPointRainbow[][] = {
			{0, 26, 66, 177, 293, 376, 427, 480, 502, 512, 481, 447, 393, 318, 214, 180, 0},
			{181, 113, 75, 21, 1, 12, 35, 91, 136, 165, 171, 138, 112, 113, 158, 205, 181}
	};
	private boolean istouchSnailRight = true, istouchTerubouzu=true, 
			istouchSnailUnder=true,	istouchRainbow=true, istouchKumo=true, istouchSnailTop=true;
	
	private SequenceEntityModifier seTerubouzuModifier, seKumoModifier;
	private int currentSnailLeftTop = 0, currentSnailLeftUnder=0, currentChild =0 ;
	private Sound E00023_BIYON, E00024_KENPA, E00027_RAINBOW,
			E00028_MOVE, E00029_SWING, E00030_CLOUD;
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("katatsumuri/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("katatsumuri/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "katatsumuri/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		ttpKatatsumuri = mTexturePackLoaderFromSource.load("katatsumuri1.xml");
		ttpKatatsumuri.loadTexture();
		ttpLibKatatsumuri = ttpKatatsumuri.getTexturePackTextureRegionLibrary();
		ttpKatatsumuri1 = mTexturePackLoaderFromSource.load("katatsumuri2.xml");
		ttpKatatsumuri1.loadTexture();
		ttpLibKatatsumuri1 = ttpKatatsumuri1.getTexturePackTextureRegionLibrary();
		trBackground = ttpLibKatatsumuri1.get(Vol3KatatsumuriResource.A3_00_IPHONE3G_HAIKEI_KATATSUMURI_ID);
		
		trOkaBack = ttpLibKatatsumuri1.get(Vol3KatatsumuriResource.A3_13_IPHONE3G_OKA_BACK_ID);
		
		trBigLeaf = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_14_IPHONE3G_BIG_LEAF_ID);
		
		trFlowerLeaf = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_07_5_IPHONE3G_FLOWER_ID);
		
		trKumo = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_08_IPHONE3G_KUMO_ID); 
		trTerubouzu =ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_09_IPHONE3G_TERUTERUBOUZU_ID);
		trFlower1 = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_07_1_IPHONE3G_FLOWER_ID);
		trFlower2 = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_07_2_IPHONE3G_FLOWER_ID);
		trFlower3 = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_07_3_IPHONE3G_FLOWER_ID);
		trFlower4 = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_07_4_IPHONE3G_FLOWER_ID);
		
		trShizuku = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_11_IPHONE3G_SHIZUKU_ID);
		
		trMizutamari = ttpLibKatatsumuri.get(Vol3KatatsumuriResource.A3_12_IPHONE3G_MIZUTAMARI_ID);
		
		ttrChildmae = getTiledTextureFromPacker(ttpKatatsumuri, Vol3KatatsumuriResource.PACK_CHILDMAE);
		
		ttrRainbow = getTiledTextureFromPacker(ttpKatatsumuri, Vol3KatatsumuriResource.PACK_RAINBOW);
		ttrSnailRight = getTiledTextureFromPacker(ttpKatatsumuri, 
				Vol3KatatsumuriResource.A3_04_1_IPHONE3G_RIGHTSNAIL_SMALL_ID,
				Vol3KatatsumuriResource.A3_04_3_IPHONE3G_RIGHTSNAIL_BIG_ID);
		ttrSnailLeftTop = getTiledTextureFromPacker(ttpKatatsumuri,
				Vol3KatatsumuriResource.A3_05_IPHONE3G_LEFTSNAIL_TOP_ID,
				Vol3KatatsumuriResource.A3_05_IPHONE3G_LEFTSNAIL_TOP_REVERT_ID);
		ttrSnailLeftUnder = getTiledTextureFromPacker(ttpKatatsumuri,
				Vol3KatatsumuriResource.A3_06_IPHONE3G_LEFTSNAIL_UNDER_ID,
				Vol3KatatsumuriResource.A3_06_IPHONE3G_LEFTSNAIL_UNDER_REVERT_ID);
		
	}

	@Override
	protected void loadKaraokeSound() {
		
		E00023_BIYON = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00023_BIYON);
		E00024_KENPA = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00024_KENPA);
		E00027_RAINBOW = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00027_RAINBOW);
		E00028_MOVE = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00028_MOVE);
		E00029_SWING = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00029_SWING);
		E00030_CLOUD = loadSoundResourceFromSD(Vol3KatatsumuriResource.E00030_CLOUD);
		
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene ");
		this.mScene = new Scene();
		this.sBackground = new Sprite(0, 0, trBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground);
		
		this.aniRainbow = new AnimatedSprite(345, 90, ttrRainbow, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniRainbow);
		this.sOkaBack = new Sprite(0, 170, trOkaBack, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOkaBack);
		
		this.sKumo = new Sprite(300, 0, trKumo, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKumo);
		
		this.sTerubouzu = new Sprite(700, -5, trTerubouzu, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTerubouzu);
		
		this.sFlowerLeaf = new Sprite(785, 180, trFlowerLeaf, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlowerLeaf);
		
		this.sBigleaf = new Sprite(644, 285, trBigLeaf, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBigleaf);
		this.aniSnailRight = new AnimatedSprite(720, 336, ttrSnailRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniSnailRight);
		
		this.sFlower1 = new Sprite(0, 300, trFlower1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlower1);
		
		this.sFlower2 = new Sprite(30, 300, trFlower2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlower2);
		
		this.sFlower3 = new Sprite(-15, 407, trFlower3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlower3);
		
		this.sFlower4 = new Sprite(80, 435, trFlower4, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFlower4);
		this.aniSnailLeftTop = new AnimatedSprite(60, 320, ttrSnailLeftTop, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniSnailLeftTop);
		
		this.aniSnailLeftUnder = new AnimatedSprite(180, 344, ttrSnailLeftUnder, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniSnailLeftUnder);
		
		this.sMizutamari = new Sprite(580, 525, trMizutamari, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMizutamari);
		sMizutamari.setVisible(false);
		this.sShizuku = new Sprite(630, 370, trShizuku, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sShizuku);
		this.aniChildmae = new AnimatedSprite(arrPointChild[0][0], arrPointChild[1][0], 
				ttrChildmae, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniChildmae);
		addGimmicsButton(mScene, Vol3KatatsumuriResource.SOUND_GIMMIC,
				Vol3KatatsumuriResource.IMAGE_GIMMIC, ttpLibKatatsumuri);
		
		this.mScene.setOnSceneTouchListener(this);

	}
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		moveFlower();
		moveShizuku();
		super.onResumeGame();
	}
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		resetData();
		super.onPauseGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, "loadKaraokeComplete: ");
		super.loadKaraokeComplete();
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContains(aniSnailRight, 41, 46, 163, 182, pX, pY) && istouchSnailRight) {
				istouchSnailRight = false;
				touchSnailRight() ;
			}
			if (checkContains(aniChildmae, 7, 24, 108, 234, pX, pY)) {
				touchChild(currentChild);
				currentChild++;
				if (currentChild == 7) {
					currentChild = 0;
				}
			} else if (checkContains(sTerubouzu, 2, 47, 46, 146, pX, pY) && istouchTerubouzu) {
				istouchTerubouzu=false;
				touchTerubouzu();
				
			} else if (checkContainsPolygon(aniRainbow, arrPointRainbow, 16, pX, pY) && istouchRainbow) {
				
				istouchRainbow = false;
				touchRainbow();
				
			}
			if (checkContains(sKumo, 10, 10, 122, 94, pX, pY) && istouchKumo) {
				istouchKumo = false;
				touchKumo();
			}
			if (checkContains(aniSnailLeftTop, 12, 12, 84, 77, pX, pY) && istouchSnailTop) {
				istouchSnailTop = false;
				touchSnailTop(currentSnailLeftTop);
				currentSnailLeftTop++;
				if (currentSnailLeftTop==3) {
					currentSnailLeftTop=0;
				}
			}
			if (checkContains(aniSnailLeftUnder, 9, 46, 86, 165, pX, pY) && istouchSnailUnder){
				istouchSnailUnder = false;
				touchSnailUnder(currentSnailLeftUnder);
				currentSnailLeftUnder++;
				if (currentSnailLeftUnder==3) {
					currentSnailLeftUnder=0;
				}
			}
			
			
		}
		
		return false;
	}
	private void touchChild(int index){
		E00024_KENPA.play();
		switch (index) {
		case 0:
			aniChildmae.setPosition(arrPointChild[0][1], arrPointChild[1][1]);
			aniChildmae.setCurrentTileIndex(1);
			break;
		case 1:
			aniChildmae.setPosition(arrPointChild[0][2], arrPointChild[1][2]);
			aniChildmae.setCurrentTileIndex(2);
			break;
		case 2:
			aniChildmae.setPosition(arrPointChild[0][3], arrPointChild[1][3]);
			aniChildmae.setCurrentTileIndex(3);
			break;
		case 3:
			aniChildmae.setPosition(arrPointChild[0][4], arrPointChild[1][4]);
			aniChildmae.setCurrentTileIndex(4);
			break;
		case 4:
			aniChildmae.setPosition(arrPointChild[0][5], arrPointChild[1][5]);
			aniChildmae.setCurrentTileIndex(5);
			break;
		case 5:
			aniChildmae.setPosition(arrPointChild[0][6], arrPointChild[1][6]);
			aniChildmae.setCurrentTileIndex(6);
	
			break;
	
		case 6:
			aniChildmae.setPosition(arrPointChild[0][0], arrPointChild[1][0]);
			aniChildmae.setCurrentTileIndex(0);
			break;

		default:
			break;
		}
	}
	private void touchSnailUnder(int index){
		Log.d(TAG, "gia tri currentSnailLeftUnder:"+index);
		E00028_MOVE.play();
		if (index==0) {
			aniSnailLeftUnder.registerEntityModifier(new MoveModifier(0.5f,180, 240, 344, 390, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailUnder=true;
				}
			}));
		} else if (index==1) {
			aniSnailLeftUnder.setCurrentTileIndex(1);
			aniSnailLeftUnder.registerEntityModifier(new MoveXModifier(0.5f, 240, 160, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailUnder = true;
				}
			}));
			
		} else if (index ==2) {
			aniSnailLeftUnder.setCurrentTileIndex(0);
			aniSnailLeftUnder.registerEntityModifier(new MoveModifier(0.5f, 160, 180, 390, 344, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailUnder=true;
				}
			}));
		}
	}
	private void touchSnailTop(int index){
		Log.d(TAG, "gia tri currentsnailLeftTop:"+index);
		E00028_MOVE.play();
		if (index==0) {
			aniSnailLeftTop.registerEntityModifier(new MoveModifier(0.5f,60, 110, 320, 380,new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailTop=true;
				}
			}));
		} else if (index==1) {
			aniSnailLeftTop.setCurrentTileIndex(1);
			aniSnailLeftTop.registerEntityModifier(new MoveXModifier(0.5f,110, 60, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailTop=true;
				}
			}));
			
		} else if (index ==2) {
			aniSnailLeftTop.setCurrentTileIndex(0);
			aniSnailLeftTop.registerEntityModifier(new MoveYModifier(0.5f, 380, 320, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					istouchSnailTop=true;
				}
			}));
		}
	}
	private void touchKumo() {
		E00030_CLOUD.play();
		sKumo.registerEntityModifier(seKumoModifier = new SequenceEntityModifier(
				new MoveXModifier(1.0f, sKumo.getX(), 1090),
				new MoveXModifier(1.0f, -130, sKumo.getX())));
		seKumoModifier.addModifierListener(this);
		return;
	}
	private void touchRainbow(){
		E00027_RAINBOW.play();
		long pDuration[] =  new long[] {400,400,400,400,400,400,400};
		int pFrame[] = new int[]{1,2,3,4,5,6,0};
		aniRainbow.animate(pDuration, pFrame,0, this);
		return;
	}
	private void touchSnailRight() {
		E00023_BIYON.play();
		long pDuration[] =  new long[] {1000,400};
		int pFrame[] = new int[]{1,0};
		aniSnailRight.animate(pDuration, pFrame, 0, this);
		
		return;
	}
	
	private void touchTerubouzu() {
		E00029_SWING.play();
		sTerubouzu.setRotationCenter(43, 2);
		sTerubouzu.registerEntityModifier(seTerubouzuModifier = new SequenceEntityModifier(
				new RotationModifier(0.3f, 0, 15),
				new RotationModifier(0.3f, 15, 0),
				new RotationModifier(0.3f, 0, -15),
				new RotationModifier(0.3f, -15, 0)));
		seTerubouzuModifier.addModifierListener(this);
		return;
	}
	private void moveFlower() {
		 moveSprite(sFlower1);
		 moveSprite(sFlower2);
		 moveSprite(sFlower3);
		 moveSprite(sFlower4);
		 moveSprite(sFlowerLeaf);
		return;
	}
	private void moveSprite(final Sprite sFlower){
		sFlower.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new RotationModifier(0.3f, 0, -5),
				new RotationModifier(0.3f, -5, 0), 
				new DelayModifier(1.0f))));
		return;
	}
	private void moveShizuku() {
		sShizuku.setVisible(true);
		sShizuku.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.6f, sShizuku.getY(), sShizuku.getY() + 80, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						sMizutamari.setVisible(true);
					}
				}),
				new DelayModifier(0.6f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub				
				sMizutamari.setVisible(false);
				sShizuku.setVisible(false);
				mEngine.registerUpdateHandler(new TimerHandler(1.0f,false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler arg0) {
						sShizuku.setPosition(630, 370);
						moveShizuku();
					}
				}));
			}
		})));
		return;
	}
	@Override
	public void combineGimmic3WithAction() {
		if (istouchSnailRight) {
			istouchSnailRight = false;
			touchSnailRight() ;
		}
	}
	private void resetData() {
		istouchSnailRight = true;
		istouchTerubouzu = true; 
		istouchRainbow = true;
		istouchKumo = true;
		istouchSnailUnder = true;
		currentSnailLeftTop = 0;
		currentSnailLeftUnder=0;
		currentChild =0 ;
		if (aniSnailLeftTop != null) {
			aniSnailLeftTop.clearEntityModifiers();
			aniSnailLeftTop.setPosition(60, 320);
			aniSnailLeftTop.setCurrentTileIndex(0);
		}
		if (aniSnailLeftUnder != null) {
			aniSnailLeftUnder.clearEntityModifiers();
			aniSnailLeftUnder.setPosition(180, 344);
			aniSnailLeftUnder.setCurrentTileIndex(0);
		}
		if (aniSnailRight != null) {
			aniSnailRight.setPosition(720, 336);
			aniSnailRight.setCurrentTileIndex(0);
		}
		if (aniChildmae != null) {
			aniChildmae.setPosition(arrPointChild[0][0], arrPointChild[1][0]);
			aniChildmae.setCurrentTileIndex(0);
		}
		if (sKumo != null) {
			sKumo.clearEntityModifiers();
			sKumo.setPosition(300, 0);
		}
		if (sTerubouzu != null) {
			sTerubouzu.clearEntityModifiers();
			sTerubouzu.setRotation(0);
			sTerubouzu.setPosition(700, -5);
		}
		if (sShizuku!=null){
			sShizuku.setVisible(true);
			sMizutamari.setVisible(false);
			sShizuku.clearEntityModifiers();
			sShizuku.setPosition(630, 370);
			mEngine.clearUpdateHandlers();
		}
		return;
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(seTerubouzuModifier)) {
			istouchTerubouzu = true;
			sTerubouzu.setRotation(0);
			sTerubouzu.setPosition(700, -5);
		}
		if (pModifier.equals(seKumoModifier)){
			istouchKumo = true;
		}
	}
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimateSprite) {
		if (pAnimateSprite.equals(aniSnailRight)) {
			istouchSnailRight = true;
		}
		if (pAnimateSprite.equals(aniRainbow)) {
			istouchRainbow = true;
		}
	}
	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}
	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}

}
