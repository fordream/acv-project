package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AmefuriResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
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

import android.os.Handler;
import android.util.Log;

public class Vol3Amefuri extends BaseGameFragment implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {

	private static final String TAG = "LOG_AMEFURI";
	private TexturePack ttpAmefuri1, ttpAmefuri2, ttpAmefuri3;
	private TexturePackTextureRegionLibrary ttpLibAmefuri1, ttpLibAmefuri3;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TextureRegion ttrBackground, ttrRBus, ttrRWheel, ttrRHumanBound;
	private TiledTextureRegion ttrRHuman, ttrRHuman2, ttrRCat, ttrRFlog, ttrRain;
	private Sprite sBackground, sBus, sWheelPre, sWheelSecond, sHumanBound;
	private AnimatedSprite anmSHuman, anmSHuman2, anmSCat, anmSFlog, aniRain;
	private SequenceEntityModifier busSequenceModifier, humanModifier;
	private Timer timeHuman,timeCat, timeFlog,timeHumanTouch;
	private TimerHandler timerMove;
	private float humanX;
	private boolean isTouchHuman, isTouchCat, isTouchFlog, isTouchBus;
	private Sound mp3Cat, mp3Oyako, mp3Flog, mp3Bus, mp3Flog2;
	
	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("amefuri/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("amefuri/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "amefuri/gfx/");
		super.onCreateResources();
		
	}
	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();
		this.sBackground = new Sprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground);
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		anmSCat = new AnimatedSprite(586, 106, ttrRCat, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSCat);
		

		sBus = new Sprite(-360, 60, ttrRBus, this.getVertexBufferObjectManager());
		sWheelPre = new Sprite(133, 198, ttrRWheel, this.getVertexBufferObjectManager());
		sWheelSecond = new Sprite(372, 198, ttrRWheel, this.getVertexBufferObjectManager());
		sBus.attachChild(sWheelPre);
		sBus.attachChild(sWheelSecond);

		this.mScene.attachChild(sBus);
		anmSFlog = new AnimatedSprite(700, 412, ttrRFlog, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSFlog);
		anmSHuman = new AnimatedSprite(0, 0, this.ttrRHuman, this.getVertexBufferObjectManager());
		anmSHuman2 = new AnimatedSprite(0, 0, this.ttrRHuman2, this.getVertexBufferObjectManager());
		anmSHuman2.setVisible(false);
		anmSHuman.animate(1000, -1);
		sHumanBound = new Sprite(0, 160, ttrRHumanBound, this.getVertexBufferObjectManager());
		sHumanBound.attachChild(anmSHuman);
		sHumanBound.attachChild(anmSHuman2);
		humanX = sHumanBound.getX();
		this.mScene.attachChild(sHumanBound);
		sHumanBound.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sHumanBound.setAlpha(0.0f);
		Log.d(TAG, "loadKaraokeScene: 3");
		aniRain = new AnimatedSprite(0, 0, ttrRain, this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniRain);
		addGimmicsButton(mScene, Vol3AmefuriResource.SOUND_GIMMIC_AMEFURI,
				Vol3AmefuriResource.IMAGE_GIMMIC_AMEFURI, ttpLibAmefuri1);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources: ");
		ttpAmefuri1 = mTexturePackLoaderFromSource.load("amefuri1.xml");
		ttpAmefuri1.loadTexture();
		ttpLibAmefuri1 = ttpAmefuri1.getTexturePackTextureRegionLibrary();
		ttpAmefuri2 = mTexturePackLoaderFromSource.load("amefuri2.xml");
		ttpAmefuri2.loadTexture();
//		ttpLibAmefuri2 = ttpAmefuri2.getTexturePackTextureRegionLibrary();
		ttpAmefuri3 = mTexturePackLoaderFromSource.load("amefuri3.xml");
		ttpAmefuri3.loadTexture();
		ttpLibAmefuri3 = ttpAmefuri3.getTexturePackTextureRegionLibrary();
		
		ttrBackground = ttpLibAmefuri3.get(Vol3AmefuriResource.A3_04_IPHONE3G_AMEFURI_HAIKEI_ID);
		ttrRBus = ttpLibAmefuri1.get(Vol3AmefuriResource.A3_06_1_IPHONE3G_E0003_BUS_ID);
		// Whell
		ttrRWheel = ttpLibAmefuri1.get(Vol3AmefuriResource.A3_06_2_IPHONE3G_E0003_BUS_ID);
		
		ttrRHuman = getTiledTextureFromPacker(ttpAmefuri1, 
				Vol3AmefuriResource.A3_12_1_IPHONE3G_HUMAN_ID,
				Vol3AmefuriResource.A3_12_2_IPHONE3G_HUMAN_ID,
				Vol3AmefuriResource.A3_12_3_IPHONE3G_HUMAN_ID,
				Vol3AmefuriResource.A3_12_4_IPHONE3G_HUMAN_ID);

		ttrRHuman2 = getTiledTextureFromPacker(ttpAmefuri1,
				Vol3AmefuriResource.A3_16_1_IPHONE3G_HUMAN_ACTION_ID,
				Vol3AmefuriResource.A3_16_2_IPHONE3G_HUMAN_ACTION_ID);
		ttrRHumanBound = ttpLibAmefuri3.get(Vol3AmefuriResource.HUMAN_TEMP_ID);
		ttrRCat = getTiledTextureFromPacker(ttpAmefuri1,
				Vol3AmefuriResource.A3_17_1_IPHONE3G_CATS_BEFORE_ID,
				Vol3AmefuriResource.A3_17_2_IPHONE3G_CATS_AFTER_ID);
		
		ttrRFlog = getTiledTextureFromPacker(ttpAmefuri1, 
				Vol3AmefuriResource.A3_11_1_IPHONE3G_E0002_FROG_ID,
				Vol3AmefuriResource.A3_11_2_IPHONE3G_E0002_FROG_ID,
				Vol3AmefuriResource.A3_11_3_IPHONE3G_E0002_FROG_ID,
				Vol3AmefuriResource.A3_11_4_IPHONE3G_E0002_FROG_ID);
		
		ttrRain = getTiledTextureFromPacker(ttpAmefuri2, 
				Vol3AmefuriResource.A3_14_1_IPHONE3G_RAIN_ID,
				Vol3AmefuriResource.A3_14_2_IPHONE3G_RAIN_ID,
				Vol3AmefuriResource.A3_14_3_IPHONE3G_RAIN_ID,
				Vol3AmefuriResource.A3_14_4_IPHONE3G_RAIN_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		Log.d(TAG, "loadKaraokeSound: ");
		mp3Cat = loadSoundResourceFromSD(Vol3AmefuriResource.E00059_CAT);
		mp3Oyako = loadSoundResourceFromSD(Vol3AmefuriResource.E0084_OYAKO);
		mp3Flog = loadSoundResourceFromSD(Vol3AmefuriResource.E0002_1_FLOG);
		mp3Flog2 = loadSoundResourceFromSD(Vol3AmefuriResource.E0002_2_FLOG);
		mp3Bus = loadSoundResourceFromSD(Vol3AmefuriResource.E0003_BUS);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		Log.d(TAG, "ACTION_DOWN");
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sBus, 0, 0, (int) sBus.getWidth(), (int) sBus.getHeight(), pX, pY)) {
				busModifier();
			} else if (checkContains(sHumanBound, (int) sHumanBound.getWidth() / 4, 0,
					(int) sHumanBound.getWidth() / 2, (int) sHumanBound.getHeight(), pX, pY)
					|| checkContains(sHumanBound, (int) sHumanBound.getWidth() * 2 / 3,
							(int) sHumanBound.getHeight() / 3, (int) sHumanBound.getWidth(),
							(int) sHumanBound.getHeight(), pX, pY)) {
				humanChangeResoure();
			} else if (checkContains(anmSCat, (int) anmSCat.getWidth() / 3, 0,
					(int) anmSCat.getWidth() * 2 / 3,
					(int) anmSCat.getHeight() / 2, pX, pY)) {
				catChangeResource();
			} else if (checkContains(anmSFlog, (int) anmSFlog.getWidth() / 3, (int) anmSFlog.getHeight() / 3,
					(int) anmSFlog.getWidth() * 2 / 3, (int) anmSFlog.getHeight() * 2 / 3, pX, pY)) {
				flogChangeResource();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		super.onResumeGame();
		humanX = 0;
		isTouchHuman = true;
		isTouchCat = true;
		isTouchFlog = true;
		isTouchBus=true;
		timeHuman = new Timer();
		timeCat = new Timer();
		timeFlog = new Timer();
		timeHumanTouch = new Timer();
		frameAnimationRain();
		humanModifier();
		
	}
	private void resetData() {
		humanX = 0;
		isTouchHuman = true;
		isTouchCat = true;
		isTouchFlog = true;
		isTouchBus=true;
		if (anmSFlog != null ) {
			anmSFlog.clearEntityModifiers();
			anmSFlog.setCurrentTileIndex(0);
			anmSFlog.setRotation(0.0f);
		}
		
		if (sBus != null) {
			sBus.clearEntityModifiers();
			sBus.setPosition(-360, 60);
			sWheelPre.clearEntityModifiers();
			sWheelPre.setRotation(0.0f);
			sWheelSecond.clearEntityModifiers();
			sWheelSecond.setRotation(0.0f);
		}
		if (anmSCat != null) {
			anmSCat.setCurrentTileIndex(0);
		}
		if (sHumanBound != null) {
			sHumanBound.clearEntityModifiers();
			sHumanBound.setPosition(0, 160);
		}
		
		return;
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, "loadKaraokeComplete : ");
		super.loadKaraokeComplete();
	}
	
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame: ");
		resetData();
		super.onPauseGame();
	}
	private void humanModifier() {
		try {
			if (!anmSHuman2.isAnimationRunning()) {
				
				float duration_1 = 20.0f * ((this.getEngine().getCamera()
						.getWidth() - sHumanBound.getX()) / (this.getEngine()
								.getCamera().getWidth() - humanX));
				
				float duration_2 = 8.0f * ((sHumanBound.getWidth() + Math.abs(Math
						.abs(sHumanBound.getX()))) / (sHumanBound.getWidth() + humanX));
				
				sHumanBound
				.registerEntityModifier(humanModifier = new SequenceEntityModifier(
						new MoveXModifier(duration_1, sHumanBound.getX(),
								this.getEngine().getCamera().getWidth()),
								new MoveXModifier(duration_2, -sHumanBound
										.getWidth(), sHumanBound.getX())));
				timeHuman.schedule(new TimerTask() {
					
					@Override
					public void run() {
						humanModifier();
					}
				},(long) (humanModifier.getDuration() * 1000));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void busModifier() {

		if (isTouchBus) {
			isTouchBus=false;
			mp3Bus.play();
			sBus.registerEntityModifier(busSequenceModifier = new SequenceEntityModifier(
					new MoveXModifier(3.0f, sBus.getX(), this.getEngine()
							.getCamera().getWidth()
							+ sBus.getWidth()),

					new MoveXModifier(0.6f, -sBus.getWidth(), sBus.getX())));

			sWheelPre
					.registerEntityModifier(new LoopEntityModifier(
							new RotationModifier(1.0f, 0.0f, 359.0f)));
			sWheelSecond
					.registerEntityModifier(new LoopEntityModifier(
							new RotationModifier(1.0f, 0.0f, 359.0f)));
			busSequenceModifier.addModifierListener(this);
		}

		
		
	}

	private void humanChangeResoure() {
		if (isTouchHuman) {
			mp3Oyako.play();
			isTouchHuman = false;
			anmSHuman.setVisible(false);
			anmSHuman2.setVisible(true);
			anmSHuman2.animate(800, false, this);
			sHumanBound.clearEntityModifiers();
			timeHumanTouch.schedule(new TimerTask() {
				@Override
				public void run() {
					isTouchHuman = true;
					
				}
			}, 1800);
		}
	}

	private void catChangeResource() {
		if (isTouchCat) {
			mp3Cat.play();
			isTouchCat = false;
			long pDurationCat[] = new long[] { 800, 100 };
			int pFramsCat[] = new int[] { 1, 0 };
			anmSCat.animate(pDurationCat, pFramsCat, 0);
			timeCat.schedule(new TimerTask() {
				
				@Override
				public void run() {
					isTouchCat = true;
					
				}
			}, 1100);
		}
	}

	private void flogChangeResource() {
		if (isTouchFlog) {
			mp3Flog.play();
			isTouchFlog = false;
			anmSFlog.registerEntityModifier(new SequenceEntityModifier(new RotationModifier(0.1f, 0, -10),
					new RotationModifier(0.1f, -10, 0),
					new RotationModifier(0.1f, 0, 10),
					new RotationModifier(0.1f, 10, 0, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							anmSFlog.setRotation(0.0f);
						}
					})));
			long pDurationFlog[] = new long[] { 600, 100, 400, 100, 400 };
			int pFramsFlog[] = new int[] { 0,1, 2, 3, 0 };
			anmSFlog.animate(pDurationFlog, pFramsFlog, 0, this);
			timeFlog.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mp3Flog2.play();
				}
			},600);
			
		}

		return;
	}

	private void frameAnimationRain() {
		aniRain.animate(600, -1);
	}

	@Override
	public void combineGimmic3WithAction() {
		flogChangeResource();
	}
	
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

		if (pAnimatedSprite.equals(anmSHuman2)) {
			anmSHuman2.setVisible(false);
			anmSHuman.setVisible(true);
			humanModifier();
		}
		if (pAnimatedSprite.equals(anmSFlog)) {
			isTouchFlog = true;
		}
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(busSequenceModifier)) {
			isTouchBus=true;
			sWheelPre.clearEntityModifiers();
			sWheelPre.setRotation(0.0f);
			sWheelSecond.clearEntityModifiers();
			sWheelSecond.setRotation(0.0f);
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
}