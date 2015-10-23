/* Vol3Gonbesan.java
* Create on Mar 22, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
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

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3GonbesanResouce;

import android.util.Log;


public class Vol3Gonbesan extends BaseGameActivity implements
IOnSceneTouchListener, IModifierListener<IEntity> {
	
	private static final String TAG = "Vol3 Gonbesan";
	private static final float TIME_KARASU = 40.0f / 960;

	
	private TexturePack ttpGonbesan;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary ttpLibGonbesan;
	
	private TextureRegion ttrBackground1,ttrBackground2,ttrBackground3,ttrSuisya, ttrKaki, ttrBanana,
			ttrMonkey1,ttrMonkey2,ttrMonkey3;
	private Sprite sBackground1,sBackground2,sBackground3,sSuisya,sKaki, 
			sBanana, sMonkey1, sMonkey2, sMonkey3;
	private TiledTextureRegion tttrRiver,tttrDog, tttrPerson, tttrKarasu;
	private TiledTextureRegion[] tttrDadada = new TiledTextureRegion[6];
	private AnimatedSprite aniRiver,aniDog, aniPerson, aniKarasu;
	private AnimatedSprite[] aniDadada = new AnimatedSprite[6];
	private boolean istouchDog = true;
	private boolean istouchKaki = true;
	private boolean istouchPerson = true;
	private boolean istouchKarasu = true;
	private boolean istouchBanana = true;
	private boolean istouchDadada = true;
	private boolean ischeckState = true;
	private SequenceEntityModifier seDogModifier,seMonkey1Modifier, 
			seMonkey2Modifier, seMonkey3Modifier;
	private MoveModifier moveKakiModifier;
	private MoveXModifier moveKarasuModifier;
	private PathModifier[] pDadadaModifier = new PathModifier[6];
	private RanDomNoRepeat randomBanana;
	private int currentDadada = 0;
	private Timer timer = new Timer();
	
	/**
	 * Sounds
	 */
	private Sound E00018_QUESTION, E00019_LAUGH, E00067_DADADA, E00068_MONKEY,
			E00069_DOG, E00070_POMI;
				
	@Override
	protected void loadKaraokeResources() {
		//load background
		ttpGonbesan = mTexturePackLoaderFromSource.load("gonbesan.xml");
		ttpGonbesan.loadTexture();
		ttpLibGonbesan = ttpGonbesan.getTexturePackTextureRegionLibrary();
		
		this.ttrBackground1 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_14_1_IPHONE3G_HAIKEI_GONBESAN_ID );
		
		//load background
		this.ttrBackground2 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_14_2_IPHONE3G_HAIKEI_GONBESAN_ID );
		//load background
		
		this.ttrBackground3 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_14_3_IPHONE3G_HAIKEI_GONBESAN_ID );
		
		this.tttrRiver = getTiledTextureFromPacker(ttpGonbesan,
				Vol3GonbesanResouce.A3_13_1_IPHONE3G_RIVER_ID,
				Vol3GonbesanResouce.A3_13_2_IPHONE3G_RIVER_ID);
		
		this.ttrSuisya = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_05_IPHONE3G_SUISYA_ID );
		
		this.ttrKaki = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_12_IPHONE3G_KAKI_ID );
		
		this.ttrBanana = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_10_IPHONE3G_BANANA_ID );
		this.tttrDog = getTiledTextureFromPacker(ttpGonbesan, 
				Vol3GonbesanResouce.A3_08_1_IPHONE3G_E00069_DOG_ID,
				Vol3GonbesanResouce.A3_08_2_IPHONE3G_E00069_DOG_ID);
		
		this.tttrPerson = getTiledTextureFromPacker(ttpGonbesan,
				Vol3GonbesanResouce.A3_04_1_IPHONE3G_E00019_LAUGH_ID,
				Vol3GonbesanResouce.A3_04_2_IPHONE3G_E00019_LAUGH_ID,
				Vol3GonbesanResouce.A3_07_1_IPHONE3G_E00017_CRY_ID,
				Vol3GonbesanResouce.A3_07_2_IPHONE3G_E00017_CRY_ID);
		
		this.ttrMonkey1 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_11_1_IPHONE3G_E00068_MONKEY_ID );
		
		
		this.ttrMonkey2 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_11_2_IPHONE3G_E00069_MONKEY_ID );
		
		
		this.ttrMonkey3 = ttpLibGonbesan.get(Vol3GonbesanResouce.A3_11_3_IPHONE3G_E00070_MONKEY_ID );
		
		this.tttrKarasu = getTiledTextureFromPacker(ttpGonbesan, Vol3GonbesanResouce.PACK_KARASU);
		
		for (int i = 0; i < tttrDadada.length; i++) {
			this.tttrDadada[i] = getTiledTextureFromPacker(ttpGonbesan,
					Vol3GonbesanResouce.A3_09_1_IPHONE3G_E00067_DADADA_ID,
					Vol3GonbesanResouce.A3_09_2_IPHONE3G_E00067_DADADA_ID);
		}
		
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		mScene = new Scene();
		this.sBackground1 = new Sprite(0, 0, ttrBackground1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground1);
//		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.sMonkey1 = new Sprite(285, 248, ttrMonkey1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMonkey1);
		
		this.sMonkey2 = new Sprite(400, 254, ttrMonkey2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMonkey2);
		
		this.sMonkey3 = new Sprite(524, 254, ttrMonkey3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMonkey3);
		this.sBackground2 = new Sprite(0, 152,ttrBackground2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground2);
		for (int i = 0; i < 6; i++) {
			this.aniDadada[i] = new AnimatedSprite(860,136, tttrDadada[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(aniDadada[i]);
			this.aniDadada[i].setVisible(false);
		}
		this.aniRiver = new AnimatedSprite(2, 382, tttrRiver, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniRiver);
		this.sKaki = new Sprite(720, 420, ttrKaki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKaki);
		this.sBanana = new Sprite(432, 234, ttrBanana, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBanana);
		this.sBackground3 = new Sprite(0, 216,ttrBackground3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground3);
		this.sSuisya = new Sprite(778, 300,ttrSuisya, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSuisya);
		this.aniDog = new AnimatedSprite(440, 340,tttrDog, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDog);
		this.aniPerson = new AnimatedSprite(14, 86,tttrPerson, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniPerson);
		this.aniKarasu = new AnimatedSprite(-60, 14, tttrKarasu, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniKarasu);

		addGimmicsButton(this.mScene, Vol3GonbesanResouce.SOUND_GIMMIC,
				Vol3GonbesanResouce.IMAGE_GIMMIC, ttpLibGonbesan);
		this.mScene.setOnSceneTouchListener(this);
	}
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("gonbesan/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gonbesan/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
                getTextureManager(), pAssetManager, "gonbesan/gfx/");
		super.onCreateResources();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if (pSceneTouchEvent.isActionDown()) {
			if (checkContains(aniDog, 2, 2, 85, 66, pX, pY)) {
				if (istouchDog) {
					istouchDog = false;
					touchDog();
				}
			}
			if (checkContains(sKaki, 2, 2, 29, 28, pX, pY)) {
				if (istouchKaki) {
					istouchKaki = false;
					touchKaki();
				}
			}
			if (checkContains(aniPerson, 57, 56, 218, 369, pX, pY)) {
				if (istouchPerson) {
					istouchPerson=false;
					touchPerson();
				}
			}
			if (checkContains(aniKarasu, 2, 2, 114, 84, pX, pY)) {
				if (istouchKarasu) {
					istouchKarasu = false;
					touchKarasu();
				}
			}
//			if (sBanana.contains(pX, pY)) {
//				if (istouchBanana) {
//					istouchBanana=false;
//					touchBanana();
//				}
//			}
			if (checkContains(sBanana, 2, 2, 52, 36, pX, pY)||
					checkContains(sBackground2, 392, 74, 542, 148, pX, pY)) {
				if (istouchBanana) {
					istouchBanana=false;
					touchBanana();
				}
			}
			if (checkContains(sBackground1, 864, 127, 954, 181, pX, pY)){
				if (istouchDadada && ischeckState) {
					ischeckState=false;
					touchDadada(currentDadada);
					currentDadada++;
					if (currentDadada==6){
						istouchDadada=false;
						currentDadada = 0;
					}
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							ischeckState = true;
						}
					},400);
				}
			}
		}
		return false;
	}
	private void touchDadada(final int index) {
		aniDadada[index].setVisible(true);
		aniDadada[index].animate(400, -1);
		final Path pDadada = new Path(6)
		.to(aniDadada[index].getX(), aniDadada[index].getY())
		.to(622, sBackground2.getY() + 100)
		.to(462, sBackground2.getY() + 38)
		.to(326, sBackground2.getY() + 84)
		.to(160, sBackground2.getY())
		.to(-115, sBackground2.getY() + 48);
		E00067_DADADA.play();
		pDadadaModifier[index] = new PathModifier(6.0f, pDadada );
		aniDadada[index].registerEntityModifier(pDadadaModifier[index]);
		pDadadaModifier[index].addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				aniDadada[index].setVisible(false);
				aniDadada[index].setPosition(860, 136);
				if (index==5) {
					istouchDadada=true;
				}
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void touchBanana() {
		randomBanana.setLenghNoRepeat(7);
		int i = randomBanana.getNextIntNoRepeat(true);
		E00068_MONKEY.play();
		Log.e(TAG, "gia tri i: " + i);
		switch (i) {
		case 0:
			sMonkey1.registerEntityModifier(seMonkey1Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey1.getY(), sMonkey1.getY()-160),
					new MoveYModifier(0.5f, sMonkey1.getY()-160, sMonkey1.getY())));
			sMonkey2.registerEntityModifier(seMonkey2Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey2.getY(), sMonkey2.getY()-160),
					new MoveYModifier(0.5f, sMonkey2.getY()-160, sMonkey2.getY())));
			seMonkey2Modifier.addModifierListener(this);
			seMonkey1Modifier.addModifierListener(this);
			break;
		case 1:
			sMonkey2.registerEntityModifier(seMonkey2Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey2.getY(), sMonkey2.getY()-160),
					new MoveYModifier(0.5f, sMonkey2.getY()-160, sMonkey2.getY())));
			seMonkey2Modifier.addModifierListener(this);
			break;
		case 2:
			sMonkey2.registerEntityModifier(seMonkey2Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey2.getY(), sMonkey2.getY()-160),
					new MoveYModifier(0.5f, sMonkey2.getY()-160, sMonkey2.getY())));
			sMonkey3.registerEntityModifier(seMonkey3Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey3.getY(), sMonkey3.getY()-160),
					new MoveYModifier(0.5f, sMonkey3.getY()-160, sMonkey3.getY())));
			seMonkey2Modifier.addModifierListener(this);
			seMonkey3Modifier.addModifierListener(this);
			break;
		case 3:
			sMonkey3.registerEntityModifier(seMonkey3Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey3.getY(), sMonkey3.getY()-160),
					new MoveYModifier(0.5f, sMonkey3.getY()-160, sMonkey3.getY())));
			seMonkey3Modifier.addModifierListener(this);
			break;
		case 4:
			sMonkey1.registerEntityModifier(seMonkey1Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey1.getY(), sMonkey1.getY()-160),
					new MoveYModifier(0.5f, sMonkey1.getY()-160, sMonkey1.getY())));
			seMonkey1Modifier.addModifierListener(this);
			break;
		case 5:
			sMonkey1.registerEntityModifier(seMonkey1Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey1.getY(), sMonkey1.getY()-160),
					new MoveYModifier(0.5f, sMonkey1.getY()-160, sMonkey1.getY())));
			sMonkey3.registerEntityModifier(seMonkey3Modifier=new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey3.getY(), sMonkey3.getY()-160),
					new MoveYModifier(0.5f, sMonkey3.getY()-160, sMonkey3.getY())));
			seMonkey1Modifier.addModifierListener(this);
			seMonkey3Modifier.addModifierListener(this);
			break;
		case 6:
			sMonkey1.registerEntityModifier(seMonkey1Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey1.getY(), sMonkey1.getY()-160),
					new MoveYModifier(0.5f, sMonkey1.getY()-160, sMonkey1.getY())));
			sMonkey2.registerEntityModifier(seMonkey2Modifier = new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey2.getY(), sMonkey2.getY()-160),
					new MoveYModifier(0.5f, sMonkey2.getY()-160, sMonkey2.getY())));
			sMonkey3.registerEntityModifier(seMonkey3Modifier=new SequenceEntityModifier(
					new MoveYModifier(0.5f, sMonkey3.getY(), sMonkey3.getY()-160),
					new MoveYModifier(0.5f, sMonkey3.getY()-160, sMonkey3.getY())));
			seMonkey1Modifier.addModifierListener(this);
			seMonkey2Modifier.addModifierListener(this);
			seMonkey3Modifier.addModifierListener(this);
			break;
		default:
			break;
		}
		return;
	}
	private void touchKarasu() {
		E00018_QUESTION.play();
		if (aniKarasu.getCurrentTileIndex() == 1 || aniKarasu.getCurrentTileIndex() == 2) {
			aniKarasu.clearEntityModifiers();
			Log.d(TAG, "value aniKarasu = 1 or 2 ");
			aniKarasu.stopAnimation();
			aniKarasu.setCurrentTileIndex(0);
			TimerHandler timeDown = new TimerHandler(1.0f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					mEngine.unregisterUpdateHandler(pTimerHandler);
					moveKarasu();
					isTouchGimmic[2]=true;
					istouchKarasu=true;
				}
			});
			this.mEngine.registerUpdateHandler(timeDown);
			return;
		} 
		if (aniKarasu.getCurrentTileIndex() == 3 || aniKarasu.getCurrentTileIndex() == 4) {
			aniKarasu.clearEntityModifiers();
			Log.d(TAG, "value aniKarasu = 3 or 4 ");
			aniKarasu.stopAnimation();
			aniKarasu.setCurrentTileIndex(5);
			TimerHandler timeUp = new TimerHandler(1.0f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					mEngine.unregisterUpdateHandler(pTimerHandler);
					moveKarasu();
					isTouchGimmic[2]=true;
					istouchKarasu=true;
				}
			});
			this.mEngine.registerUpdateHandler(timeUp);
			return;
		}
		
	}
	private void touchPerson() {
		long[] pDuration = new long[]{300, 300, 300, 100};
		int[] pFrame = new int[] {0, 1, 0, 1};
		E00019_LAUGH.play();
		aniPerson.animate(pDuration, pFrame, 0, new IAnimationListener() {
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				istouchPerson = true;
				movePerson();
			}
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {
			}
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {
			}
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			}
		});
	}
	private void touchKaki() {
		E00070_POMI.play();
//		Debug.w("value sKaki:"+ sKaki.getX());
		sKaki.registerEntityModifier(moveKakiModifier = new MoveModifier(
				3.0f, sKaki.getX(), -300, sKaki.getY(), 780));
		moveKakiModifier.addModifierListener(this);
		return;
	}
	private void touchDog() {
		long[] pDuration = new long[]{400, 400};
		int[] pFrame = new int[] {1, 0};
		E00069_DOG.play();
		aniDog.setRotationCenter(77, 30);
		aniDog.registerEntityModifier(seDogModifier = new SequenceEntityModifier(
				new RotationModifier(0.4f, 0.0f, 15.0f),
				new RotationModifier(0.4f, 15.0f, 0.0f)));
		seDogModifier.addModifierListener(this);
		aniDog.animate(pDuration, pFrame, 0);
		
		return;
	}
	
	private void moveKarasu() {
		long[] pDuration = new long[]{600, 600, 600, 600};
		int[] pFrame = new int[] {1, 3, 2, 4};
		float duration = (980-aniKarasu.getX())*TIME_KARASU;
		aniKarasu.animate(pDuration, pFrame, -1);
		aniKarasu.registerEntityModifier( 
				moveKarasuModifier=new MoveXModifier(duration, aniKarasu.getX(), 980 ));
		moveKarasuModifier.addModifierListener(Vol3Gonbesan.this);
		return;
	}
	private void movePerson() {
		long[] pDuration = new long[]{400, 400};
		int[] pFrame = new int[] {2, 3};
		aniPerson.animate(pDuration, pFrame, -1);
		return;
		
	} 
	private void moveRiver () {
		long[] pDuration = new long[]{600, 600};
		int[] pFrame = new int[] {1, 0};
		aniRiver.animate(pDuration, pFrame, -1);
		return;
	}
	private void moveSuisya () {
		sSuisya.registerEntityModifier(new LoopEntityModifier(
				new RotationModifier(8.0f, 0.0f, 360.0f,EaseLinear.getInstance())));
		return;
	}
	@Override
	protected void loadKaraokeSound() {
		E00018_QUESTION = loadSoundResourceFromSD(Vol3GonbesanResouce.E00018_QUESTION);
		E00019_LAUGH = loadSoundResourceFromSD(Vol3GonbesanResouce.E00019_LAUGH);
		E00067_DADADA = loadSoundResourceFromSD(Vol3GonbesanResouce.E00067_DADADA);
		E00068_MONKEY = loadSoundResourceFromSD(Vol3GonbesanResouce.E00068_MONKEY);
		E00069_DOG = loadSoundResourceFromSD(Vol3GonbesanResouce.E00069_DOG);
		E00070_POMI = loadSoundResourceFromSD(Vol3GonbesanResouce.E00070_POMI);
	}
	@Override
	public void onPauseGame() {
		Log.d(TAG, "ham onpauseGame ");
		super.onPauseGame();
		resetData();
	}
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		Log.d(TAG, " onResumeGame ");
		loadDefault();
		super.onResumeGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, " loadKaraokeComplete ");
		super.loadKaraokeComplete();
	}

	public void loadDefault() {
		randomBanana = new RanDomNoRepeat();
		
		moveKarasu();
		movePerson();
		moveSuisya();
		moveRiver();
	}
	private void resetData (){
		//Karasu
		if (aniKarasu != null) {
			aniKarasu.setPosition(-60, 14);
			aniKarasu.stopAnimation();
			aniKarasu.clearEntityModifiers();
			istouchKarasu=true;
			isTouchGimmic[2]=true;
			
		}
		if (sKaki != null) {
			sKaki.clearEntityModifiers();
			sKaki.setPosition(720, 420);
			istouchKaki = true;
		}
		if (aniPerson != null) {
			istouchPerson = true;
		}
		for (int i = 0; i < 6; i++) {
			if (aniDadada[i] != null) {
				aniDadada[i].setVisible(false);
				aniDadada[i].stopAnimation();
				aniDadada[i].clearEntityModifiers();
				aniDadada[i].setPosition(860, 136);
			}
		}
		
		if (aniDog != null) {
			istouchDog = true;
			aniDog.clearEntityModifiers();
			aniDog.setRotation(0);
			aniDog.setPosition(440, 340);
		}
		if (sMonkey1 != null) {
			sMonkey1.clearEntityModifiers();
			sMonkey1.setPosition(285, 248);
		}
		if (sMonkey2 != null) {
			sMonkey2.clearEntityModifiers();
			sMonkey2.setPosition(400, 254);
		}
		if (sMonkey3 != null) {
			sMonkey3.clearEntityModifiers();
			sMonkey3.setPosition(524, 254);
		}
		istouchBanana = true;
		istouchDadada=true;
		currentDadada = 0;
		ischeckState = true;
		return;
	}
	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimmic[2]&& istouchKarasu) {
			isTouchGimmic[2]=false;
			istouchKarasu = false;
			touchKarasu();
		}
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(seDogModifier)) {
			istouchDog = true;
			aniDog.setRotation(0);
		}
		if (pModifier.equals(moveKakiModifier)) {
			istouchKaki = true;
			sKaki.setPosition(720, 420);
		}
		if (pModifier.equals(moveKarasuModifier)) {
			aniKarasu.setPosition(-60, 14);
			moveKarasu();
			istouchKarasu=true;
		}
		if (pModifier.equals(seMonkey1Modifier) ||
			pModifier.equals(seMonkey2Modifier) ||
			pModifier.equals(seMonkey3Modifier)) {
			istouchBanana=true;
		}
	}

	

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		// TODO Auto-generated method stub
		
	}

}
