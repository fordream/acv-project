/* Vol3Cyoucyo.java
* Create on Apr 9, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3CyoucyoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

public class Vol3Cyoucyo extends BaseGameFragment implements 
IOnSceneTouchListener, IModifierListener<IEntity> {
	
	private TexturePack ttpCyoucyo;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TexturePackTextureRegionLibrary ttpLibCyoucyo;
	private TextureRegion ttrBackground, ttrEarth, ttrFlower, ttrTree, 
			ttrHanabira1, ttrHanabira2, ttrHanabira3, ttrHeart;
	private Sprite sBackground, sEarth, sFlower, sTree, sHanabira1, sHanabira2, sHanabira3, sHeart;
	private TiledTextureRegion tttrSun, tttrKumo, tttrHouse, tttrKuma, tttrBoy, tttrGirl, tttrBird,
			tttrMushi;
	
	private AnimatedSprite aniSun, aniKumo, aniHouse, aniKuma, aniBoy, aniGirl , aniMushi, aniBird;
	private SequenceEntityModifier seKumaModifier, seGirlModifier, seBoyModifier;
	private MoveXModifier moveHanabira1, moveHanabira2, moveHanabira3;
	private MoveYModifier moveHeart;
	
	private int currentMushi = 0;
	private int currentSun = 0;
	private int currentTree = 0;
//	private int currentGirl = 0;
	private boolean istouchKumo = true;
	private boolean istouchHouse = true;
	private boolean istouchKuma = true;
	private boolean istouchTree = true;
	private boolean istouchGirl = true;
	private boolean istouchBoy = true;
	private boolean istouchGirlKiss = false;
	private boolean istouchGirlFly = false;
	private Sound E8041_KISS, E8042_CHANGE, E8044_OPENDOOR, E8043_CROWD,
			E8045_SANAGI, E8046_BEAR, E8047_SAKURA, E8048_SUN, E8082_FLY;
	private Timer timerBird1 = new Timer();
//	private Timer timerBird2 = new Timer();
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("cyoucyo/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("cyoucyo/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "cyoucyo/gfx/");
		super.onCreateResources();
	}
	
	
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d("Vol3Cyoucyo ", "loadKaraokeResources");
		ttpCyoucyo = mTexturePackLoaderFromSource.load("cyoucyo.xml");
		ttpCyoucyo.loadTexture();
		ttpLibCyoucyo = ttpCyoucyo.getTexturePackTextureRegionLibrary();

		this.ttrBackground = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_00_IPHONE3G_HAIKEI_CYOCYO_ID);
		
		this.ttrEarth = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_09_IPHONE3G_EARTH_ID);

		this.ttrFlower = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_01_1_IPHONE3G_FLOWER_ID);

		this.ttrTree = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_10_IPHONE3G_TREE_ID);

		this.tttrSun = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_05_1_IPHONE3G_SUN_ID,
				Vol3CyoucyoResource.A3_05_2_IPHONE3G_SUN_ID,
				Vol3CyoucyoResource.A3_05_3_IPHONE3G_SUN_ID);
		
		this.tttrKumo = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_07_1_IPHONE3G_KUMO_ID,
				Vol3CyoucyoResource.A3_07_2_IPHONE3G_KUMO_ID,
				Vol3CyoucyoResource.A3_07_3_IPHONE3G_KUMO_ID,
				Vol3CyoucyoResource.A3_07_4_IPHONE3G_KUMO_ID);
		
		this.tttrHouse = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_11_1_IPHONE3G_HOUSE_ID,
				Vol3CyoucyoResource.A3_11_2_IPHONE3G_HOUSE_ID);
	
		this.tttrKuma = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_04_1_IPHONE3G_KUMA_ID,
				Vol3CyoucyoResource.A3_04_2_IPHONE3G_KUMA_ID);
		
		this.tttrBoy = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_12_1_IPHONE3G_BOY_ID,
				Vol3CyoucyoResource.A3_12_2_IPHONE3G_BOY_ID,
				Vol3CyoucyoResource.A3_12_3_IPHONE3G_BOY_ID);
		this.tttrGirl = getTiledTextureFromPacker(ttpCyoucyo, Vol3CyoucyoResource.PACK_GIRL);
		
		this.tttrMushi = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_03_1_IPHONE3G_MUSHI_ID,
				Vol3CyoucyoResource. A3_03_2_IPHONE3G_MUSHI_ID,
				Vol3CyoucyoResource.A3_03_3_IPHONE3G_MUSHI_ID);
		this.ttrHanabira1 = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_08_1_IPHONE3G_HANABIRA_ID);
		this.ttrHanabira2 = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_08_2_IPHONE3G_HANABIRA_ID);
		this.ttrHanabira3 = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_08_3_IPHONE3G_HANABIRA_ID);
		this.ttrHeart = ttpLibCyoucyo.get(Vol3CyoucyoResource.A3_16_IPHONE3G_HEART_ID);
		this.tttrBird = getTiledTextureFromPacker(ttpCyoucyo,
				Vol3CyoucyoResource.A3_06_1_IPHONE3G_BIRD_ID,
				Vol3CyoucyoResource.A3_06_2_IPHONE3G_BIRD_ID,
				Vol3CyoucyoResource.A3_06_3_IPHONE3G_BIRD_ID);
		Log.d("Vol3Cyoucyo ", "loadKaraokeResources end");
		
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d("Vol3Cyoucyo ", "loadKaraokeScene");
		mScene = new Scene();
		this.sBackground = new Sprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground);
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.sTree= new Sprite(672, 320, ttrTree, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sTree);
		Log.d("Vol3Cyoucyo ", "loadKaraokeScene 1");
		this.sHanabira1= new Sprite(780, 282, ttrHanabira1, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sHanabira1);
		this.sHanabira1.setVisible(false);
		this.sHanabira2= new Sprite(780, 282, ttrHanabira2, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sHanabira2);
		this.sHanabira2.setVisible(false);
		this.sHanabira3= new Sprite(720, 282, ttrHanabira3, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sHanabira3);
		this.sHanabira3.setVisible(false);
		Log.d("Vol3Cyoucyo ", "loadKaraokeScene 2");
		this.aniHouse= new AnimatedSprite(96, 394, tttrHouse, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniHouse);
		this.sEarth= new Sprite(136, 320, ttrEarth, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sEarth);
		
		this.aniSun= new AnimatedSprite(664, 16, tttrSun, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniSun);
		this.aniKumo= new AnimatedSprite(40, 34, tttrKumo, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniKumo);
		
		this.aniBird = new AnimatedSprite(1100, 2, tttrBird, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniBird);
		
		this.aniKuma= new AnimatedSprite(730, 500, tttrKuma, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniKuma);
		
		this.aniMushi= new AnimatedSprite(26, 140, tttrMushi, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniMushi);
		
		this.aniGirl= new AnimatedSprite(480, 70, tttrGirl, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniGirl);
		this.aniBoy= new AnimatedSprite(200, 70, tttrBoy, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(aniBoy);
		
		this.sHeart =  new Sprite(426, 178, ttrHeart, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHeart);
		this.sHeart.setVisible(false);
		
		this.sFlower= new Sprite(312, 488, ttrFlower, this.getVertexBufferObjectManager()); 
		this.mScene.attachChild(sFlower);
		
		addGimmicsButton(this.mScene,Vol3CyoucyoResource.SOUND_GIMMIC,
				Vol3CyoucyoResource.IMAGE_GIMMIC, ttpLibCyoucyo);
		Log.d("Vol3Cyoucyo ", "loadKaraokeScene + end");
		this.mScene.setOnSceneTouchListener(this);
		
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContains(aniMushi, 2, 2, 112, 143, pX, pY)) {
				currentMushi++;
				touchMushi(currentMushi);
				if (currentMushi==2) {
					currentMushi=-1;
				}
			}
			if (checkContains(aniKumo, 6, 7, 167, 95, pX, pY)) {
				if (istouchKumo) {
					istouchKumo=false;
					touchKumo();
				}
			}
			if (checkContains(aniSun, 30, 12, 252, 226, pX, pY)) {
				currentSun++;
				touchSun(currentSun);
				if (currentSun == 2) {
					currentSun = -1;
				}
			}
			if (checkContains(aniHouse, 8, 6, 192, 142, pX, pY)) {
				if (istouchHouse) {
					istouchHouse=false;
					long pDurations[] = new long[] {500, 500, 500};
					int pFrames[] = new int[]{0,1,0};
					E8044_OPENDOOR.play();
					aniHouse.animate(pDurations, pFrames, 0, new IAnimationListener() {
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							istouchHouse=true;
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {}
					});
				}
			}
			if (checkContains(aniKuma, 5, 5, 195, 82, pX, pY)) {
				if (istouchKuma) {
					istouchKuma = false;
					touchKuma();
				}
			}
			
			if (checkContains(sTree, 2, 2, 190, 208, pX, pY)) {
				if (istouchTree){
					istouchTree=false;
					touchTree(currentTree);
					currentTree++;
					if (currentTree == 3) {
						currentTree = 0;
					}
				}
			}
			
			if (checkContains(aniGirl, 12, 28, 248, 296, pX, pY)) {
				if (istouchGirl) {
					touchGirl();
				} else
				if (istouchGirlKiss) {
					touchGirlKiss();
				} else
				if (istouchGirlFly) {
					touchGirlFly();
				}
			}
			if (checkContains(aniBoy, 14, 16, 250, 282, pX, pY)) {
				if (istouchBoy) {
					istouchBoy = false;
					istouchGirl = false;
					istouchGirlFly = true;
					isTouchGimmic[2]= false;
					E8082_FLY.play();
					aniBoy.clearEntityModifiers();
					aniGirl.clearEntityModifiers();
					aniBoy.setCurrentTileIndex(1);
					aniBoy.setPosition(200, 70);
					aniGirl.setZIndex(1);
					aniBoy.setZIndex(0);
					mScene.sortChildren();
					touchBoy();
				}
			}
					
		}
		return false;
	}
	private void touchGirl() {
		E8042_CHANGE.play();
//		Log.d("", "gia tri current girl: " + currentGirl);
		if (aniGirl.getCurrentTileIndex() == 0) {
			aniGirl.setCurrentTileIndex(1);
		} else if (aniGirl.getCurrentTileIndex() == 1) {
			aniGirl.setCurrentTileIndex(2);
		} else if (aniGirl.getCurrentTileIndex() == 2) {
			aniGirl.setCurrentTileIndex(3);
		} else if (aniGirl.getCurrentTileIndex() == 3) {
			aniGirl.setCurrentTileIndex(0);
		}
		return;
	}
	private void touchGirlFly(){
		E8042_CHANGE.play();
		if (aniGirl.getCurrentTileIndex()==4) {
			aniGirl.setCurrentTileIndex(5);
		} else if (aniGirl.getCurrentTileIndex()==5) {
			aniGirl.setCurrentTileIndex(6);
		} else if (aniGirl.getCurrentTileIndex()==6) {
			aniGirl.setCurrentTileIndex(7);
		} else if (aniGirl.getCurrentTileIndex()==7) {
			aniGirl.setCurrentTileIndex(4);
		}
	}
	private void touchBoy() {
		
		if (aniGirl.getCurrentTileIndex()==0) {
			aniGirl.setCurrentTileIndex(4);
		} else if (aniGirl.getCurrentTileIndex() == 1) {
			aniGirl.setCurrentTileIndex(5);
		} else if (aniGirl.getCurrentTileIndex() == 2) {
			aniGirl.setCurrentTileIndex(6);
		} else if (aniGirl.getCurrentTileIndex() == 3) {
			aniGirl.setCurrentTileIndex(7);
		}
//		aniGirl.setPosition(480, 70);
//		aniBoy.setPosition(200, 70);
		Log.d("Vol3Cyoucyo", "tap boy: @@@@@@@@@@@@@@@@@" );
		aniGirl.registerEntityModifier(
				new MoveModifier(0.1f, 480, 440, 70, 40,
						new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						Log.d("Vol3Cyoucyo", " boy and girl move: $$$$$$$$$$$$$$$$$$$$$$$" );
						Log.d("Vol3Cyoucyo", " toa do boy X: " +aniBoy.getX() );
						Log.d("Vol3Cyoucyo", " toa do boy Y: " +aniBoy.getY() );
						Log.d("Vol3Cyoucyo", " toa do girl X:  " +aniGirl.getX() );
						Log.d("Vol3Cyoucyo", " toa do girl Y:" +aniGirl.getY() );
						moveTowPerson();
					}
				}));
		
		return;
	}
	private void moveTowPerson() {
		aniBoy.registerEntityModifier(seBoyModifier = new SequenceEntityModifier(
				new MoveXModifier(2.0f, 200, -300),
				new MoveXModifier(3.1f, 1200, 200)));
		aniGirl.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(3.0f, 440, -300), 
				new MoveXModifier(2.0f, 1120, 480)));
		seBoyModifier.addModifierListener(this);
		return;
	}
	private void movePerson() {
		aniBoy.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1.0f, 70, 40),
				new MoveYModifier(1.0f, 40, 70))));
		
		aniGirl.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1.0f, 70, 40),
				new MoveYModifier(1.0f, 40, 70))));
	}
	private void boyKissGirl(){
		aniBoy.setCurrentTileIndex(2);
		long pDurations[] = new long[] {1200, 200};
		int pFrames[] = new int[]{2,0};
		aniBoy.clearEntityModifiers();
		aniGirl.clearEntityModifiers();
		aniGirl.setZIndex(0);
		aniBoy.setZIndex(1);
		mScene.sortChildren();
		aniBoy.setPosition(200, 70);
		aniGirl.setPosition(480, 70);
		aniBoy.animate(pDurations, pFrames, 0, new IAnimationListener() {
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				isTouchGimmic[2] = true;
				istouchGirl = true;
				istouchGirlKiss=false;
				istouchBoy=true;
				aniBoy.clearEntityModifiers();
				aniGirl.clearEntityModifiers();
				aniBoy.setPosition(200, 70);
				if (aniGirl.getCurrentTileIndex()==8) {
					aniGirl.setCurrentTileIndex(0);
				} else if (aniGirl.getCurrentTileIndex()==9) {
					aniGirl.setCurrentTileIndex(1);
				} else if (aniGirl.getCurrentTileIndex()== 10) {
					aniGirl.setCurrentTileIndex(2);
				} else if (aniGirl.getCurrentTileIndex()== 11){
					aniGirl.setCurrentTileIndex(3);
				}
				movePerson();
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
		});
		E8041_KISS.play();
//		Log.d("", "gia tri current girl: " + currentGirl);
		if (aniGirl.getCurrentTileIndex()==0) {
			aniGirl.setCurrentTileIndex(8);
		} else if (aniGirl.getCurrentTileIndex()==1){
			aniGirl.setCurrentTileIndex(9);
		} else if (aniGirl.getCurrentTileIndex()==2) {
			aniGirl.setCurrentTileIndex(10);
		} else if (aniGirl.getCurrentTileIndex()==3) {
			aniGirl.setCurrentTileIndex(11);
		}
		
		aniBoy.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(0.1f, aniBoy.getX(), aniBoy.getX()+15, aniBoy.getY(), aniBoy.getY()+30),
				new DelayModifier(1.0f),
				new MoveModifier(0.1f, aniBoy.getX()+15, aniBoy.getX(), aniBoy.getY()+30, aniBoy.getY())));
		aniGirl.registerEntityModifier(seGirlModifier = new SequenceEntityModifier(
				new MoveXModifier(0.1f, aniGirl.getX(), aniGirl.getX()- 100),
				new DelayModifier(1.0f),
				new MoveXModifier(0.1f, aniGirl.getX() - 100, aniGirl.getX())));
		seGirlModifier.addModifierListener(this);
		sHeart.setVisible(true);
		sHeart.registerEntityModifier(moveHeart = new MoveYModifier(1.0f, sHeart.getY(), - 53));
		moveHeart.addModifierListener(this);
		return;
	}
	private void touchGirlKiss(){
		E8042_CHANGE.play();
		if (aniGirl.getCurrentTileIndex()==8) {
			aniGirl.setCurrentTileIndex(9);
		} else if (aniGirl.getCurrentTileIndex()==9) {
			aniGirl.setCurrentTileIndex(10);
		} else if (aniGirl.getCurrentTileIndex()== 10) {
			aniGirl.setCurrentTileIndex(11);
		} else if (aniGirl.getCurrentTileIndex()== 11){
			aniGirl.setCurrentTileIndex(8);
		}
	}
	private void touchTree(int index) {
		E8047_SAKURA.play();
		switch (index) {
		case 0:
			sHanabira1.setVisible(true);
			sHanabira1.registerEntityModifier(moveHanabira1 = new MoveXModifier(
					0.4f, sHanabira1.getX(), sHanabira1.getX()+20));
			moveHanabira1.addModifierListener(this);
			break;
		case 1:
			sHanabira2.setVisible(true);
			sHanabira2.registerEntityModifier(moveHanabira2 = new MoveXModifier(
					0.4f, sHanabira2.getX(), sHanabira2.getX()+20));
			moveHanabira2.addModifierListener(this);
			
			break;
		case 2:
			sHanabira3.setVisible(true);
			sHanabira3.registerEntityModifier(moveHanabira3 = new MoveXModifier(
					0.4f, sHanabira3.getX(), sHanabira3.getX()+20));
			moveHanabira3.addModifierListener(this);
			break;
		default:
			break;
		}
		return;
	}
	private void touchKuma() {
		E8046_BEAR.play();
		aniKuma.setCurrentTileIndex(1);
		aniKuma.registerEntityModifier(seKumaModifier = new SequenceEntityModifier(
				new MoveYModifier(3.0f, aniKuma.getY(), -220), new DelayModifier(1.0f)));
		seKumaModifier.addModifierListener(this);
		return;
	}
	private void touchSun (int index) {
		E8048_SUN.play();
		aniSun.setCurrentTileIndex(index);
		return;
	}
	private void touchKumo() {
		long pDurations[] = new long[] {500, 500, 500, 500};
		int pFrames[] = new int[]{2,1,3,0};
		E8043_CROWD.play();
		aniKumo.animate(pDurations, pFrames, 0, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				istouchKumo = true;
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
	private void touchMushi (int id) {
		E8045_SANAGI.play();
		aniMushi.setCurrentTileIndex(id);
		return;
	}
	private void moveBird() {
//		aniBird.registerEntityModifier(new LoopEntityModifier(new MoveXModifier(15.0f, 1100, -200)));
		
		aniBird.registerEntityModifier(new MoveXModifier(15.0f, 1100, -200, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				aniBird.setCurrentTileIndex(0);
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				moveBird();
			}
		}));
		timerBird1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				aniBird.setCurrentTileIndex(1);
			}
		},6000);
		timerBird1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				aniBird.setCurrentTileIndex(2);
			}
		},10000);
		return;
	}
	private void moveEarth() {
		sEarth.registerEntityModifier(new LoopEntityModifier(
				new RotationModifier(30.0f, 0.0f, 360.0f,EaseLinear.getInstance())));
		return;
	}
	
	
	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		E8041_KISS = loadSoundResourceFromSD(Vol3CyoucyoResource.E8041_KISS);
		E8042_CHANGE = loadSoundResourceFromSD(Vol3CyoucyoResource.E8042_CHANGE);
		E8044_OPENDOOR = loadSoundResourceFromSD(Vol3CyoucyoResource.E8044_OPENDOOR);
		E8045_SANAGI = loadSoundResourceFromSD(Vol3CyoucyoResource.E8045_SANAGI);
		E8046_BEAR = loadSoundResourceFromSD(Vol3CyoucyoResource.E8046_BEAR);
		E8047_SAKURA = loadSoundResourceFromSD(Vol3CyoucyoResource.E8047_SAKURA);
		E8048_SUN = loadSoundResourceFromSD(Vol3CyoucyoResource.E8048_SUN);
		E8082_FLY = loadSoundResourceFromSD(Vol3CyoucyoResource.E8082_FLY);
		E8043_CROWD = loadSoundResourceFromSD(Vol3CyoucyoResource.E8043_CROWD);
	}
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		Log.e("Vol3Cyoucyo", "onResumeGame");
		moveEarth();
		movePerson();
		moveBird();
		super.onResumeGame();
	}
	private void loadDefault (){
		Log.e("Vol3Cyoucyo", "loadDefault");
		if (aniMushi!=null) {
			currentMushi=0;
			aniMushi.setCurrentTileIndex(0);
		}
		if (aniSun != null) {
			currentSun = 0;
			aniSun.setCurrentTileIndex(0);
		}
		currentTree = 0;
		if (sHanabira1!=null) {
			sHanabira1.clearEntityModifiers();
			sHanabira1.setVisible(false);
			sHanabira1.setPosition(780, 282);
		}
		if (sHanabira2!=null) {
			sHanabira2.clearEntityModifiers();
			sHanabira2.setVisible(false);
			sHanabira2.setPosition(780, 282);
		}
		if (sHanabira3!=null) {
			sHanabira3.clearEntityModifiers();
			sHanabira3.setVisible(false);
			sHanabira3.setPosition(720, 282);
		}
		if (aniGirl != null) {
			aniGirl.clearEntityModifiers();
			aniGirl.setPosition(480, 70);
			aniGirl.setCurrentTileIndex(0);
		}
		if (aniBoy != null) {
			aniBoy.clearEntityModifiers();
			aniBoy.setCurrentTileIndex(0);
			aniBoy.setPosition(200, 70);
			
		}
		if (sHeart != null) {
			sHeart.clearEntityModifiers();
			sHeart.setVisible(false);
			sHeart.setPosition(426, 178);
		}
		
		if (aniKuma != null) {
			aniKuma.clearEntityModifiers();
			aniKuma.setPosition(730, 500);
			aniKuma.setCurrentTileIndex(0);
		}
		if (aniBird != null) {
			aniBird.clearEntityModifiers();
			aniBird.setPosition(1100, 2);
		}
		istouchKumo = true;
		istouchHouse = true;
		istouchKuma = true;
		istouchTree = true;
		istouchGirl = true;
		istouchBoy = true;
		istouchGirlKiss = false;
		istouchGirlFly = false;
		isTouchGimmic[2] = true;
		return;
	}
	@Override
	public synchronized void onPauseGame() {
		Log.e("Vol3Cyoucyo", "onPauseGame");
		loadDefault();
		super.onPauseGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.e("Vol3Cyoucyo", "loadKaraokeComplete");
		super.loadKaraokeComplete();
	}
	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		if (isTouchGimmic[2]) {
			isTouchGimmic[2] = false;
			istouchBoy=false;
			istouchGirl = false;
			istouchGirlKiss=true;
			boyKissGirl();

		}
	}
	
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(seKumaModifier)) {
			aniKuma.setCurrentTileIndex(0);
			aniKuma.setPosition(730, 500);
			istouchKuma=true;
		}
		if (pModifier.equals(moveHanabira1)) {
			istouchTree = true;
			sHanabira1.setVisible(false);
			sHanabira1.setPosition(780, 282);
		}
		if (pModifier.equals(moveHanabira2)) {
			istouchTree = true;
			sHanabira2.setVisible(false);
			sHanabira2.setPosition(780, 282);
		}
		if (pModifier.equals(moveHanabira3)) {
			istouchTree = true;
			sHanabira3.setVisible(false);
			sHanabira3.setPosition(720, 282);
		}
		if (pModifier.equals(moveHeart)) {
			sHeart.setVisible(false);
			sHeart.setPosition(426, 178);
		}
		if (pModifier.equals(seBoyModifier)) {
			aniBoy.setCurrentTileIndex(0);
			if (aniGirl.getCurrentTileIndex()==4) {
				aniGirl.setCurrentTileIndex(0);
			} else if (aniGirl.getCurrentTileIndex() == 5) {
				aniGirl.setCurrentTileIndex(1);
			} else if (aniGirl.getCurrentTileIndex() == 6) {
				aniGirl.setCurrentTileIndex(2);
			} else if (aniGirl.getCurrentTileIndex() == 7) {
				aniGirl.setCurrentTileIndex(3);
			}
			aniGirl.setPosition(480, 70);
			aniBoy.setPosition(200, 70);
			istouchBoy = true;
			istouchGirl = true;
			isTouchGimmic[2] = true;
			istouchGirlFly = false;
			movePerson();
			
		}
	}
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	

}
