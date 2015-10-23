/* Vol3Kuri.java
* Create on Apr 3, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KuriResource;

public class Vol3Kuri extends BaseGameActivity implements IOnSceneTouchListener,
IEntityModifierListener,IAnimationListener {
	private static final String TAG = "Vol3 Kuri";
	
	private TexturePack packKuri;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary packLibKuri;
	
	private TextureRegion ttrBackground,ttrTreeLeft, ttrTreeRight, ttrLeafLeft, ttrLeafRight, ttrKagoMini,
			ttrKago, ttrOjoco1, ttrOjoco2, ttrGrassFront, ttrSquirrel1, ttrLeafSquirrel,
			ttrLeaf, ttrKuriLeft, ttrKuriBoy;
	private TextureRegion arrttrKuri[] = new TextureRegion[5];
	private Sprite sBackground, sTreeLeft, sTreeRight, sLeafLeft, sLeafRight, sKagoMini, sKago,
			sOjoco1, sOjoco2, sOjoco3, sOjoco4, sGrassFront, sSquirrel1, sSquirrel2, sLeafSquirrel, sLeaf,
			sKuriLeft, sKuriBoy;
	private Sprite arrsKuri[] = new Sprite[5];
	private TiledTextureRegion tttrTanuki, tttrBoy, tttrGirl, tttrBird, tttrMogura;
	private AnimatedSprite aniTanuki, aniBoy, aniGirl, aniBird, aniMogura;
	
	private float arrPointKuri[][]= {
			{ 740, 696, 823, 875, 646 },
			{ 68, 136, 28, 109, 94 }
	};
	private SequenceEntityModifier seLeafModifier, seOjocoModifier, seKuriModifier;
	private ParallelEntityModifier paTreeLeftModifer;
	private boolean isTouchBird = true;
	private boolean isTouchBoy = true;
	private boolean isTouchGirl = true;
	private boolean isTouchTanuki = true;
	private boolean isTouchTreeLeft = true;
	private boolean isTouchTreeRight = true;
	private boolean isTouchLeaf = true;
	private boolean isTouchKago = true;
	private boolean isTouchMogura = true;
	private Timer timerBoy = new Timer();
	private Timer timerTreeLeft = new Timer();
	private RanDomNoRepeat randomOjoco;
	
	private Sound E00120_PYOI, E00121_MONI, E00122_KASA2, E00123_PYON2,
					E00124_POKA, E00125_BIRD, E00126_BOTO, E00185_TINPANI;
	
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("kuri/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kuri/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "kuri/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.e(TAG, "loadKaraokeResources");
		this.packKuri = mTexturePackLoaderFromSource.load("kuri.xml");
		packKuri.loadTexture();
		packLibKuri = packKuri.getTexturePackTextureRegionLibrary();
		Log.d(TAG, "duong dan:" + mTexturePackLoaderFromSource);
		this.ttrBackground = packLibKuri.get(Vol3KuriResource.A2_13_IPHONE4_HAIKEI_ID);
		
		this.ttrTreeLeft = packLibKuri.get(Vol3KuriResource.A2_06_1_IPHONE4_TREE_LEFT_ID);
		this.ttrTreeRight = packLibKuri.get(Vol3KuriResource.A2_10_1_IPHONE4_TREE_RIGHT_ID);
		this.ttrLeafLeft = packLibKuri.get(Vol3KuriResource.A2_06_2_IPHONE4_LEAF_LEFT_ID);
		this.ttrLeafRight = packLibKuri.get(Vol3KuriResource.A2_10_2_IPHONE4_LEAF_RIGHT_ID);
		this.ttrKagoMini = packLibKuri.get(Vol3KuriResource.A2_14_IPHONE4_KAGO_MINI_ID);
		this.ttrKago = packLibKuri.get(Vol3KuriResource.A2_04_1_IPHONE4_KAGO_ID);
		this.ttrOjoco1 = packLibKuri.get(Vol3KuriResource.A2_04_2_IPHONE4_OJOCO_ID);
		this.ttrOjoco2 = packLibKuri.get(Vol3KuriResource.A2_04_3_IPHONE4_OJOCO_ID);
		this.ttrGrassFront = packLibKuri.get(Vol3KuriResource.A2_12_IPHONE4_GRASS_FRONT_ID);
		this.ttrSquirrel1 = packLibKuri.get(Vol3KuriResource.A2_11_3_IPHONE4_SQUIRREL_02_ID);
		this.ttrLeafSquirrel = packLibKuri.get(Vol3KuriResource.A2_11_2_IPHONE4_SQUIRREL_ID);
		this.ttrLeaf = packLibKuri.get(Vol3KuriResource.A2_11_1_IPHONE4_SQUIRREL_ID);
		this.ttrKuriLeft = packLibKuri.get(Vol3KuriResource.A2_06_3_IPHONE4_KURI_ID);
		this.ttrKuriBoy = packLibKuri.get(Vol3KuriResource.A2_08_3_IPHONE4_KURI_ID);
		
		this.arrttrKuri[0] = packLibKuri.get(Vol3KuriResource.A2_10_3_IPHONE4_KURI1_ID);
		this.arrttrKuri[1] = packLibKuri.get(Vol3KuriResource.A2_10_4_IPHONE4_KURI2_ID);
		this.arrttrKuri[2] = packLibKuri.get(Vol3KuriResource.A2_10_5_IPHONE4_KURI3_RIGHT_ID);
		this.arrttrKuri[3] = packLibKuri.get(Vol3KuriResource.A2_10_3_IPHONE4_KURI1_ID);
		this.arrttrKuri[4] = packLibKuri.get(Vol3KuriResource.A2_10_4_IPHONE4_KURI2_ID);
		
		this.tttrBoy = getTiledTextureFromPacker(packKuri,
				Vol3KuriResource.A2_08_1_IPHONE4_BOY_DEFAULT_ID,
				Vol3KuriResource.A2_08_2_IPHONE4_BOY_AFTER_ID);
		this.tttrGirl = getTiledTextureFromPacker(packKuri,
				Vol3KuriResource.A2_07_1_IPHONE4_GIRL_ID,
				Vol3KuriResource.A2_07_2_IPHONE4_GIRL_ID,
				Vol3KuriResource.A2_07_3_IPHONE4_GIRL_ID,
				Vol3KuriResource.A2_08_4_IPHONE4_GIRL_AFTER_ID);
		this.tttrMogura = getTiledTextureFromPacker(packKuri,
				Vol3KuriResource.A2_05_1_IPHONE4_MOGURA_DEFAULT_ID,
				Vol3KuriResource.A2_05_2_IPHONE4_MOGURA1_ID,
				Vol3KuriResource.A2_05_3_IPHONE4_MOGURA1_ID,
				Vol3KuriResource.A2_05_4_IPHONE4_MOGURA1_ID);
		this.tttrBird = getTiledTextureFromPacker(packKuri,
				Vol3KuriResource.A2_09_1_IPHONE4_BIRD_ID,
				Vol3KuriResource.A2_09_2_IPHONE4_BIRD_ID);
		this.tttrTanuki = getTiledTextureFromPacker(packKuri, Vol3KuriResource.PACK_TANUKI);
		Log.d(TAG, "loadKaraokeResources end");
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		this.mScene = new Scene();
		this.sBackground = new Sprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground);
		
		this.aniBird = new AnimatedSprite(588, 229, tttrBird, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniBird);
		
		this.sTreeRight = new Sprite(587, 0, ttrTreeRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeRight);
		this.sTreeLeft = new Sprite(0, 0, ttrTreeLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeLeft);
		this.aniBoy = new AnimatedSprite(346, 198, tttrBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniBoy);
		
		this.aniGirl = new AnimatedSprite(53, 216, tttrGirl, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniGirl);
		
		this.sKuriBoy = new Sprite(423, 50, ttrKuriBoy, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKuriBoy);
		
		this.aniMogura = new AnimatedSprite(49, 473, tttrMogura, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniMogura);
		
		this.sLeafLeft = new Sprite(-98, -112, ttrLeafLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeafLeft);
		this.sLeafRight = new Sprite(565,-82, ttrLeafRight, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeafRight);
		this.sLeafSquirrel = new Sprite(456, 0, ttrLeafSquirrel, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeafSquirrel);
		
		this.sLeaf = new Sprite(456, 0, ttrLeaf, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeaf);
		
		this.sKuriLeft = new Sprite(53, 0, ttrKuriLeft, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKuriLeft);
		this.sKagoMini = new Sprite(524, 405, ttrKagoMini, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKagoMini);
		
		this.aniTanuki = new AnimatedSprite(504, 276, tttrTanuki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniTanuki);
		
		
		for (int i = 0; i < arrsKuri.length; i++) {
			arrsKuri[i] = new Sprite(arrPointKuri[0][i], arrPointKuri[1][i], arrttrKuri[i], 
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(arrsKuri[i]);
		}
		this.sOjoco1 = new Sprite(760, 499, ttrOjoco1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOjoco1);
		this.sOjoco2 = new Sprite(830, 499, ttrOjoco2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOjoco2);
		this.sOjoco3 = new Sprite(840, 499, ttrOjoco1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOjoco3);
		this.sOjoco4 = new Sprite(760, 499, ttrOjoco2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOjoco4);
		this.sSquirrel1 = new Sprite(190, -100, ttrSquirrel1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSquirrel1);
		this.sSquirrel2 = new Sprite(300, -100, ttrSquirrel1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSquirrel2);
		this.sKago = new Sprite(740, 398, ttrKago, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKago);
		this.sGrassFront = new Sprite(0, 552, ttrGrassFront, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGrassFront);
		addGimmicsButton(this.mScene, Vol3KuriResource.SOUND_GIMMIC,
				Vol3KuriResource.IMAGE_GIMMIC, packLibKuri);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene end ");
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()){
			if (checkContains(aniBird, 2, 2, 46, 72, pX, pY)) {
				if (isTouchBird) {
					isTouchBird = false;
					touchBird(); 
				}
			}
			if (checkContains(sTreeLeft, 236, 198, 410, 490, pX, pY)) {
				if (isTouchTreeLeft) {
					isTouchTreeLeft = false;
					touchTreeLeft();
				}
			} else
			if (checkContains(aniBoy, 55, 56, 183, 306, pX, pY)) {
				if (isTouchBoy) {
					isTouchBoy = false;
					isTouchGirl = false;
					touchBoy();
					
				}
			} else
			if (checkContains(aniGirl, 57, 42, 206, 276, pX, pY)) {
				if (isTouchGirl) {
					isTouchBoy = false;
					isTouchGirl = false;
					touchGirl();
				}
			}
			if (checkContains(sLeaf, 53, 10, 141, 99, pX, pY)
					|| checkContains(sBackground, 536, 118, 588, 308, pX, pY)) {
				if (isTouchLeaf) {
					isTouchLeaf = false;
					touchLeaf();
				}
			}
			if (checkContains(aniTanuki, 112, 94, 228, 218, pX, pY)) {
				if (isTouchTanuki) {
//					isTouchTanuki=false;
//					Vol3Kuri.this.isTouchGimmic[2] = false;
					touchTanuki();
				}
			}
			
			if (checkContains(aniMogura, 8, 54, 122, 85, pX, pY)) {
				if (isTouchMogura) {
					isTouchMogura = false;
					touchMogura();
				}
			}
			if (checkContains(sKago, 16, 98, 209, 240, pX, pY)) {
				if (isTouchKago) {
					isTouchKago=false;
					touchKago();
				}
			}
			if (checkContains(sTreeRight, 155, 240, 255, 392, pX, pY)
					|| checkContains(sLeafRight, 112, 55, 443, 283, pX, pY)) {
				if (isTouchTreeRight){
					isTouchTreeRight=false;
					touchTreeRight();
				}
			}
		}
		return false;
	}
	private void touchTreeRight() {
		E00126_BOTO.play();
		sLeafRight.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.3f, sLeafRight.getX(), sLeafRight.getX()-50),
				new MoveXModifier(0.3f, sLeafRight.getX()-50, sLeafRight.getX())));
		arrsKuri[0].registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.1f),
				new MoveYModifier(0.8f, arrsKuri[0].getY(), arrsKuri[0].getY() + 400),
				new DelayModifier(2.0f)));
		arrsKuri[1].registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.2f),
				new MoveYModifier(0.8f, arrsKuri[1].getY(), arrsKuri[1].getY() + 400),
				new DelayModifier(2.0f)));
		arrsKuri[2].registerEntityModifier(seKuriModifier = new SequenceEntityModifier(
				new DelayModifier(0.3f),
				new MoveYModifier(0.8f, arrsKuri[2].getY(), arrsKuri[2].getY() + 400),
				new DelayModifier(2.0f)));
		arrsKuri[3].registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.2f),
				new MoveYModifier(0.8f, arrsKuri[3].getY(), arrsKuri[3].getY() + 400),
				new DelayModifier(2.0f)));
		arrsKuri[4].registerEntityModifier( new SequenceEntityModifier(
				new DelayModifier(0.1f),
				new MoveYModifier(0.8f, arrsKuri[4].getY(), arrsKuri[4].getY() + 400),
				new DelayModifier(2.0f)));
		seKuriModifier.addModifierListener(this);
		return;
	}
	
	private void touchKago() {
		randomOjoco.setLenghNoRepeat(6);
		int random = randomOjoco.getNextIntNoRepeat(true);
//		int random = new Random().nextInt(6);
		Log.d(TAG, "gia tri random : "+ random);
		E00120_PYOI.play();
		switch (random) {
		case 0:
			sOjoco1.setPosition(795, sOjoco1.getmYFirst());
			sOjoco1.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco1.getY(),  sOjoco1.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco1.getY()-95,  sOjoco1.getY())));
			break;
		case 1:
			sOjoco2.setPosition(795, sOjoco2.getmYFirst());
			sOjoco2.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco2.getY(),  sOjoco2.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco2.getY()-95,  sOjoco2.getY())));
			break;
		case 2:
			sOjoco3.setPosition(795, sOjoco3.getmYFirst());
			sOjoco3.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco3.getY(),  sOjoco3.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco3.getY()-95,  sOjoco3.getY())));
			break;
		case 3:
			sOjoco4.setPosition(795, sOjoco4.getmYFirst());
			sOjoco4.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco4.getY(),  sOjoco4.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco4.getY()-95,  sOjoco4.getY())));
			break;
		case 4:
			sOjoco1.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco1.getY(),  sOjoco1.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco1.getY() - 95,  sOjoco1.getY())));
			sOjoco2.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco2.getY(),  sOjoco2.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco2.getY() - 95,  sOjoco2.getY())));
			break;
		case 5:
			sOjoco3.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco3.getY(),  sOjoco3.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco3.getY()-95,  sOjoco3.getY())));
			sOjoco4.registerEntityModifier(seOjocoModifier = new SequenceEntityModifier(
					new MoveYModifier(0.4f, sOjoco4.getY(),  sOjoco4.getY()-95),
					new DelayModifier(0.5f),
					new MoveYModifier(0.4f, sOjoco4.getY()-95,  sOjoco4.getY())));
			break;
		default:
			break;
		}
		seOjocoModifier.addModifierListener(this);
	}
	private void touchMogura() {
		long pDurations[] = new long[]{400, 400, 400, 400};
		int pFrame[] = new int[]{3, 2, 1, 0};
		E00121_MONI.play();
		aniMogura.animate(pDurations, pFrame, 0, this);
		return;
	}
	private void touchTreeLeft() {
		E00122_KASA2.play();
		sLeafLeft.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.3f, sLeafLeft.getX(), sLeafLeft.getX()-40),
				new MoveXModifier(0.3f, sLeafLeft.getX() - 40, sLeafLeft.getX())));
		sKuriLeft.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.3f, sKuriLeft.getX(), sKuriLeft.getX()-40),
				new MoveXModifier(0.3f, sKuriLeft.getX() - 40, sKuriLeft.getX())));
		
		sSquirrel1.registerEntityModifier(new ParallelEntityModifier(
				new MoveYModifier(0.8f, sSquirrel1.getY(), sSquirrel1.getY()+ 520),
				new RotationModifier(0.8f, 0.0f, -360.0f)));
		
		mEngine.registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				sSquirrel2.registerEntityModifier( paTreeLeftModifer = new ParallelEntityModifier(
						new MoveYModifier(0.8f, sSquirrel2.getY(), sSquirrel2.getY()+ 520),
						new RotationModifier(0.8f, 0.0f, -360.0f)));
				paTreeLeftModifer.addModifierListener(Vol3Kuri.this);
				
			}
		}));
		
		return;
	}
	private void touchTanuki() {
		if (isTouchTanuki) {
			isTouchTanuki=false;
			long pDurations[] = new long[]{200, 200, 200, 200, 200, 200};
			int pFrame[] = new int[]{2, 3, 4, 5, 6, 7};
			E00185_TINPANI.play();
			aniTanuki.animate(pDurations, pFrame, 0, this);
		}
		return;
	}
	private void touchLeaf() {
		E00122_KASA2.play();
		sLeaf.registerEntityModifier(seLeafModifier = new SequenceEntityModifier(
				new MoveXModifier(0.5f, sLeaf.getX(), sLeaf.getX() - 100),
				new DelayModifier(0.5f),
				new MoveXModifier(0.5f, sLeaf.getX() - 100, sLeaf.getX())));
		seLeafModifier.addModifierListener(this);
		return;
	}
	private void touchGirl() {
		E00123_PYON2.play();
		long pDurations[] = new long[]{400, 400, 400};
		int pFrame[] = new int[]{1,2,0};
		aniGirl.animate(pDurations, pFrame, 0,this);
		return;
	}
	private void touchBoy() {
		E00124_POKA.play();
		sKuriBoy.registerEntityModifier(new MoveYModifier(0.6f, (int)sKuriBoy.getY(),
				(int)aniBoy.getY()+(int)aniBoy.getHeight()/3-(int)sKuriBoy.getHeight()-(int)sKuriBoy.getHeight()/2, 
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						aniBoy.setCurrentTileIndex(1);
						aniGirl.setCurrentTileIndex(3);
						timerBoy.schedule(new TimerTask() {
							@Override
							public void run() {
								aniBoy.setCurrentTileIndex(0);
								aniGirl.setCurrentTileIndex(0);
								sKuriBoy.setPosition(423, 50);
								isTouchBoy = true;
								isTouchGirl = true;
							}
						}, 1000);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}
				}));
		return;
	}
	private void touchBird() {
		long pDurations[] = new long[]{50,50};
		int pFrame[] = new int[]{1,0};
		E00125_BIRD.play();
		aniBird.animate(pDurations, pFrame, 5, this);
		return;
	}
	private void moveTanuki() {
		long pDurations[] = new long[]{400,400};
		int pFrame[] = new int[]{1,0};
		aniTanuki.animate(pDurations, pFrame, -1);
		return;
	}
	@Override
	protected void loadKaraokeSound() {
		E00120_PYOI = loadSoundResourceFromSD(Vol3KuriResource.E00120_PYOI);
		E00121_MONI = loadSoundResourceFromSD(Vol3KuriResource.E00121_MONI);
		E00122_KASA2 = loadSoundResourceFromSD(Vol3KuriResource.E00122_KASA2);
		E00123_PYON2 = loadSoundResourceFromSD(Vol3KuriResource.E00123_PYON2);
		E00124_POKA = loadSoundResourceFromSD(Vol3KuriResource.E00124_POKA);
		E00125_BIRD = loadSoundResourceFromSD(Vol3KuriResource.E00125_BIRD);
		E00126_BOTO = loadSoundResourceFromSD(Vol3KuriResource.E00126_BOTO);
		E00185_TINPANI = loadSoundResourceFromSD(Vol3KuriResource.E00185_TINPANI);
		 
	}
	@Override
	public void onResumeGame() {
		randomOjoco = new RanDomNoRepeat();
		moveTanuki();
		super.onResumeGame();
	}
	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		resetData();
		super.onPauseGame();
	}
	private void resetData() {
		isTouchBird = true;
		isTouchBoy = true;
		isTouchGirl = true;
		isTouchTanuki = true;
		isTouchTreeLeft = true;
		isTouchTreeRight = true;
		isTouchLeaf = true;
		isTouchKago = true;
		isTouchMogura = true;
		if (aniGirl != null) {
			aniGirl.setCurrentTileIndex(0);
		}
		if (aniBoy != null) {
			aniBoy.setCurrentTileIndex(0);
			sKuriBoy.setPosition(423, 50);
		}
		if (aniMogura!=null) {
			aniMogura.setCurrentTileIndex(0);
		}
		if (sLeafLeft!=null) {
			sLeafLeft.clearEntityModifiers();
			sLeafLeft.setPosition(-98, -112);
			sKuriLeft.clearEntityModifiers();
			sKuriLeft.setPosition(53, 0);
		}
		if (sLeafRight!=null) {
			sLeafRight.clearEntityModifiers();
			sLeafRight.setPosition(565,-82);
			for (int i = 0; i < arrsKuri.length; i++) {
				arrsKuri[i].clearEntityModifiers();
				arrsKuri[i].setPosition(arrPointKuri[0][i], arrPointKuri[1][i]);
				
			}
		}
		if (aniBird != null) {
			aniBird.stopAnimation();
			aniBird.setCurrentTileIndex(0);
		}
		if (sOjoco1!=null && sOjoco2 != null
				&& sOjoco3 != null&&sOjoco4 != null) {
			sOjoco1.clearEntityModifiers();
			sOjoco2.clearEntityModifiers();
			sOjoco3.clearEntityModifiers();
			sOjoco4.clearEntityModifiers();
			sOjoco1.setPosition(770, 499);
			sOjoco2.setPosition(824, 499);
			sOjoco3.setPosition(824, 499);
			sOjoco4.setPosition(770, 499);
			
		}
		return;
	}
	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}
	@Override
	public void combineGimmic3WithAction() {
		touchTanuki();
		
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(seLeafModifier)) {
			isTouchLeaf = true;
		}
		if (pModifier.equals(paTreeLeftModifer)) {
			timerTreeLeft.schedule(new TimerTask() {
				@Override
				public void run() {
					isTouchTreeLeft = true;
					sSquirrel1.setPosition(190, -100);
					sSquirrel1.setRotation(0);
					sSquirrel1.reset();
					sSquirrel2.setPosition(300, -100);
					sSquirrel2.setRotation(0);
					sSquirrel2.reset();
				}
			},1000 );
		}
		if (pModifier.equals(seOjocoModifier)) {
			sOjoco3.setPosition(sOjoco3.getmXFirst(), sOjoco3.getmYFirst());
			sOjoco1.setPosition(sOjoco1.getmXFirst(), sOjoco1.getmYFirst());
			sOjoco2.setPosition(sOjoco2.getmXFirst(), sOjoco2.getmYFirst());
			sOjoco4.setPosition(sOjoco4.getmXFirst(), sOjoco4.getmYFirst());
			isTouchKago=true;
		}
		if (pModifier.equals(seKuriModifier)) {
			isTouchTreeRight = true;
			for (int i = 0; i < arrsKuri.length; i++) {
				arrsKuri[i].setPosition(arrPointKuri[0][i], arrPointKuri[1][i]);
			}
		}
	}

	

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		
		if (pAnimatedSprite.equals(aniBird)) {
			isTouchBird=true;
		}
		if (pAnimatedSprite.equals(aniGirl)) {
			isTouchGirl = true;
			isTouchBoy = true;
		}
		if (pAnimatedSprite.equals(aniTanuki)) {
			isTouchTanuki = true;
//			Vol3Kuri.this.isTouchGimmic[2] = true;
			moveTanuki();
		}
		if (pAnimatedSprite.equals(aniMogura)) {
			isTouchMogura = true;
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
