package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.SpritePool;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3JinguruResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationAtModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.util.Log;

public class Vol3Jinguru extends BaseGameFragment implements
		IOnSceneTouchListener {
	private static final String TAG = "LOG_JINGURU";

	private TexturePack tpJinguru1, tpJinguru2, tpJinguru3, tpJinguru4;
	private TexturePackTextureRegionLibrary ttpLibJinguru1, ttpLibJinguru2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private TextureRegion trWall, trWindChime, trSnowmanBlue, trSnowmanYellow, trStar;
	private Sprite sWall, sWindChime, sSnowmanBlue, sSnowmanYellow;
	private SpritePool spStar;

	private TiledTextureRegion ttxrBackground, ttxrPine, ttxrBackgroundWin, ttxrReindeer, ttxrSantaClaus, ttxrSnowmanRed;
	private AnimatedSprite asBackground, asPine, asBackgroundWin, asReindeer, asSantaClaus, asSnowmanRed;

	private ITiledTextureRegion[] ttxrGirl = new ITiledTextureRegion[8];
	private Children[] asGirl = new Children[8];
	private int[][] pGirl = { { 73, 399 }, { 172, 399 }, { 271, 399 },
			{ 376, 399 }, { 71, 499 }, { 171, 499 }, { 273, 499 }, { 371, 499 } };
	
	private ITextureRegion[] trGiftGirl = new ITextureRegion[10];
	private Sprite[] sGiftGirl = new Sprite[10];
	private int[][] idPositionGiftGirl = {
			{371, 450}, {271, 450}, {171, 450}, {71, 450},
			{371, 350}, {271, 350}, {171, 350}, {71, 350}
	};

	private ITiledTextureRegion[] ttxrBoy = new ITiledTextureRegion[8];
	private Children[] asBoy = new Children[8];
	private int[][] pBoy = { { 485, 399 }, { 588, 399 }, { 678, 399 },
			{ 771, 399 }, { 477, 499 }, { 575, 499 }, { 667, 499 },
			{ 762, 499 } };
	
	private ITextureRegion[] trGiftBoy = new ITextureRegion[10];
	private Sprite[] sGiftBoy = new Sprite[10];
	private int[][] idPositionGiftBoy = {
			{477, 450}, {575, 450}, {667, 450}, {762, 450},
			{485, 350}, {588, 350}, {678, 350}, {771, 350}
	};

	private ITiledTextureRegion[] ttxrGift = new ITiledTextureRegion[10];
	private AniGift[] asGiftLeft = new AniGift[10];
	private AniGift[] asGiftRight = new AniGift[10];
	
	private TimerHandler giftHandlerLeft, giftHandlerRight, starHandler, snowmanRedHandler, santaHandle;
	private PathModifier pathLeftModifier, pathRightModifier;
	private Random rdJinguru = new Random();
	private RanDomNoRepeat rdNoRepeat = new RanDomNoRepeat();
	private Path[] pPathLeft = new Path[3];
	private Path[] pPathRight = new Path[3];
	private int iIndexGiftLeft, iIndexGiftRight;
	private ArrayList<Integer>alIdGiftLeft = new ArrayList<Integer>();
	private ArrayList<Integer>alIdGiftRight = new ArrayList<Integer>();
	private boolean isSnowBlue, isSnowRed, isSnowYellow, isSantaClaus, isWindChime, isPine, isSoundSnowRed;
	
	private Sound OGG_A81_F,OGG_A81_3_2_HELLO,OGG_A81_5_2_YEAH1,OGG_A81_5_2_YEAH2,OGG_A81_1_TEKKIN,OGG_A81_2_KIRAKIRA,
				  OGG_A81_4_ZAZA,OGG_A81_5_2_WAAI2,OGG_A81_3_2_YAA,OGG_A81_6_KIRAKIRA,OGG_A81_5_2_WAAI3, OGG_A81_3_1_MARRYXMAS,
				  OGG_A81_5_2_YEAH3,OGG_A81_2_3_BELL,OGG_A81_5_2_WAAI1, OGG_A81_6_PAN, OGG_A81_3_3_OMEDETOU2, OGG_A81_5_2_URESHI,
				  OGG_A81_5_2_YATTA1, OGG_A81_5_2_PRESENTDA, OGG_A81_5_2_YATTA2;
	
	private Sound soundChildLeft[] = new Sound[8];
	private Sound soundChildRight[] = new Sound[8];
	
	private int[] dkmid = {3, 2, 1, 0, 7, 6, 5, 4};
	private int pX = 960;
	private ArrayList<Sprite> alStar = new ArrayList<Sprite>();
	
	@Override
	protected void loadKaraokeResources() {
		tpJinguru1 = mTexturePackLoaderFromSource.load("jinguru1.xml");
		tpJinguru1.loadTexture();
		ttpLibJinguru1 = tpJinguru1.getTexturePackTextureRegionLibrary();

		tpJinguru2 = mTexturePackLoaderFromSource.load("jinguru2.xml");
		tpJinguru2.loadTexture();
		ttpLibJinguru2 = tpJinguru2.getTexturePackTextureRegionLibrary();

		tpJinguru3 = mTexturePackLoaderFromSource.load("jinguru3.xml");
		tpJinguru3.loadTexture();

		tpJinguru4 = mTexturePackLoaderFromSource.load("jinguru4.xml");
		tpJinguru4.loadTexture();

		this.ttxrBackground = getTiledTextureFromPacker(tpJinguru3, 
				Vol3JinguruResource.A81_1_1_IPHONE_ID,
				Vol3JinguruResource.A81_1_2_IPHONE_ID);
		
		this.ttxrBackgroundWin = getTiledTextureFromPacker(tpJinguru4,
				Vol3JinguruResource.A81_1_3_1_IPHONE_ID,
				Vol3JinguruResource.A81_1_3_2_IPHONE_ID);

		this.ttxrPine = getTiledTextureFromPacker(tpJinguru1,
				Vol3JinguruResource.A81_2_1_IPHONE_ID,
				Vol3JinguruResource.A81_2_2_IPHONE_ID);
		
		this.trWall = ttpLibJinguru1.get(Vol3JinguruResource.A81_6_IPHONE_ID);
		this.trWindChime = ttpLibJinguru1.get(Vol3JinguruResource.A81_2_3_IPHONE_ID);
		this.ttxrReindeer = getTiledTextureFromPacker(tpJinguru1, 
				Vol3JinguruResource.A81_3_3_1_IPHONE_ID,
				Vol3JinguruResource.A81_3_3_2_IPHONE_ID);
		
		this.ttxrSantaClaus = getTiledTextureFromPacker(tpJinguru1,
				Vol3JinguruResource.A81_3_1_1_IPHONE_ID,
				Vol3JinguruResource.A81_3_1_2_IPHONE_ID);
		
		this.ttxrSnowmanRed = getTiledTextureFromPacker(tpJinguru1, 
				Vol3JinguruResource.A81_3_2_1_IPHONE_ID,
				Vol3JinguruResource.A81_3_2_2_IPHONE_ID);
		this.trSnowmanBlue = ttpLibJinguru1.get(Vol3JinguruResource.A81_4_1_IPHONE_ID);
		this.trSnowmanYellow = ttpLibJinguru1.get(Vol3JinguruResource.A81_4_2_IPHONE_ID);
		
		this.trStar = ttpLibJinguru1.get(Vol3JinguruResource.A81_3_3_3_IPHONE_ID);
		
		// Girl
		for (int i = 0; i < ttxrGirl.length; i++) {
			this.ttxrGirl[i] = getTiledTextureFromPacker(tpJinguru1, Vol3JinguruResource.idGirl[i]);
		}
		
		//Girl Gift
		for (int i = 0; i < trGiftGirl.length; i++) {
			if(i == 0) {
				this.trGiftGirl[i] = ttpLibJinguru1.get(Vol3JinguruResource.idGiftGirl[i]);
			} else {
				this.trGiftGirl[i] = ttpLibJinguru2.get(Vol3JinguruResource.idGiftGirl[i]);
			}
		}

		// Boy
		for (int i = 0; i < ttxrBoy.length; i++) {
			this.ttxrBoy[i] = getTiledTextureFromPacker(tpJinguru1, Vol3JinguruResource.idBoy[i]);
		}
		
		//Boy Gift
		for (int i = 0; i < trGiftBoy.length; i++) {
			if(i == 0) {
				this.trGiftBoy[i] = ttpLibJinguru1.get(Vol3JinguruResource.idGiftBoy[i]);
			} else {
				this.trGiftBoy[i] = ttpLibJinguru2.get(Vol3JinguruResource.idGiftBoy[i]);
			}
		}
		
		//Gift
		for (int i = 0; i < ttxrGift.length; i++) {
			if(i == 0) {
				this.ttxrGift[i] = getTiledTextureFromPacker(tpJinguru1, Vol3JinguruResource.idTxrGift[i]);
			} else {
				this.ttxrGift[i] = getTiledTextureFromPacker(tpJinguru2, Vol3JinguruResource.idTxrGift[i]);
			}
		}
		
		//Path Left
		pPathLeft[0] = new Path(4).to(10, -216).to(160, 50).to(10, 150).to(160, 250);
		pPathLeft[1] = new Path(4).to(160, -216).to(310, 50).to(160, 150).to(310, 250);
		pPathLeft[2] = new Path(4).to(310, -216).to(460, 50).to(310, 150).to(460, 250);
		//Path Right
		pPathRight[0] = new Path(4).to(480, -216).to(630, 50).to(480, 150).to(630, 250);
		pPathRight[1] = new Path(4).to(630, -216).to(780, 50).to(630, 150).to(780, 250);
		pPathRight[2] = new Path(4).to(720, -216).to(830, 50).to(720, 150).to(830, 250);
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A81_F = loadSoundResourceFromSD(Vol3JinguruResource.A81_F);
		OGG_A81_3_2_HELLO = loadSoundResourceFromSD(Vol3JinguruResource.A81_3_2_HELLO);
		OGG_A81_5_2_YEAH1 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_YEAH1);
		OGG_A81_5_2_YEAH2 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_YEAH2);
		OGG_A81_1_TEKKIN = loadSoundResourceFromSD(Vol3JinguruResource.A81_1_TEKKIN);
		OGG_A81_2_KIRAKIRA = loadSoundResourceFromSD(Vol3JinguruResource.A81_2_KIRAKIRA);
		OGG_A81_4_ZAZA = loadSoundResourceFromSD(Vol3JinguruResource.A81_4_ZAZA);
		OGG_A81_5_2_WAAI2 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_WAAI2);
		OGG_A81_3_2_YAA = loadSoundResourceFromSD(Vol3JinguruResource.A81_3_2_YAA);
		OGG_A81_6_KIRAKIRA = loadSoundResourceFromSD(Vol3JinguruResource.A81_6_KIRAKIRA);
		OGG_A81_5_2_WAAI3 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_WAAI3);
		OGG_A81_3_1_MARRYXMAS = loadSoundResourceFromSD(Vol3JinguruResource.A81_3_1_MARRYXMAS);
		OGG_A81_5_2_YEAH3 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_YEAH3);
		OGG_A81_2_3_BELL = loadSoundResourceFromSD(Vol3JinguruResource.A81_2_3_BELL);
		OGG_A81_5_2_WAAI1 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_WAAI1);
		OGG_A81_6_PAN = loadSoundResourceFromSD(Vol3JinguruResource.A81_6_PAN);
		OGG_A81_3_3_OMEDETOU2 = loadSoundResourceFromSD(Vol3JinguruResource.A81_3_3_OMEDETOU2);
		OGG_A81_5_2_URESHI = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_URESHI);
		OGG_A81_5_2_YATTA1 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_YATTA1);
		OGG_A81_5_2_PRESENTDA = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_PRESENTDA);
		OGG_A81_5_2_YATTA2 = loadSoundResourceFromSD(Vol3JinguruResource.A81_5_2_YATTA2);
		for (int i = 0; i < soundChildLeft.length; i++) {
			soundChildLeft[i] = loadSoundResourceFromSD(Vol3JinguruResource.idSoundLeft[i]);
		}
		for (int i = 0; i < soundChildRight.length; i++) {
			soundChildRight[i] = loadSoundResourceFromSD(Vol3JinguruResource.idSoundRight[i]);
		}
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene");
		mScene = new Scene();
		
		asBackground = new AnimatedSprite(0, 0, ttxrBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(asBackground);
		
		asBackgroundWin = new AnimatedSprite(0, 0, ttxrBackgroundWin, this.getVertexBufferObjectManager());
		mScene.attachChild(asBackgroundWin);
		asBackgroundWin.setVisible(false);

		// Pine
		asPine = new AnimatedSprite(639, 58, ttxrPine, this.getVertexBufferObjectManager());
		mScene.attachChild(asPine);

		//wall
		sWall = new Sprite(4, 550, trWall, this.getVertexBufferObjectManager());
		mScene.attachChild(sWall);
		
		//Wind Chimes
		sWindChime = new Sprite(758, 110, trWindChime, this.getVertexBufferObjectManager());
		mScene.attachChild(sWindChime);
		
		//Reindeer
		asReindeer = new AnimatedSprite(960, 113, ttxrReindeer, this.getVertexBufferObjectManager());
		mScene.attachChild(asReindeer);
		asReindeer.setVisible(false);
		
		//Snowman
		sSnowmanBlue = new Sprite(88, 247, trSnowmanBlue, this.getVertexBufferObjectManager());
		mScene.attachChild(sSnowmanBlue);
		
		asSnowmanRed = new AnimatedSprite(169, 183, ttxrSnowmanRed, this.getVertexBufferObjectManager());
		mScene.attachChild(asSnowmanRed);
		
		sSnowmanYellow = new Sprite(249, 239, trSnowmanYellow, this.getVertexBufferObjectManager());
		mScene.attachChild(sSnowmanYellow);
		
		//SantaClaus
		asSantaClaus = new AnimatedSprite(380, 0, ttxrSantaClaus, this.getVertexBufferObjectManager());
		mScene.attachChild(asSantaClaus);
		
		spStar = new SpritePool(trStar, this.getVertexBufferObjectManager());
		
		// Girl
		for (int i = 0; i < asGirl.length; i++) {
			asGirl[i] = new Children(pGirl[i][0], pGirl[i][1],
					ttxrGirl[i], this.getVertexBufferObjectManager());
			mScene.attachChild(asGirl[i]);
			asGirl[i].setTouch(true);
			asGirl[i].setStatus(false);
			asGirl[i].setZIndex(2);
		}

		// Boy
		for (int i = 0; i < asBoy.length; i++) {
			asBoy[i] = new Children(pBoy[i][0], pBoy[i][1], ttxrBoy[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(asBoy[i]);
			asBoy[i].setTouch(true);
			asBoy[i].setStatus(false);
			asBoy[i].setZIndex(2);
		}
		
		//Girl Gift
		for (int i = 0; i < sGiftGirl.length; i++) {
			sGiftGirl[i] = new Sprite(0, 0, 
					trGiftGirl[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sGiftGirl[i]);
			sGiftGirl[i].setScale(0.6f);
			sGiftGirl[i].setVisible(false);
			sGiftGirl[i].setZIndex(3);
		}
		
		//Boy Gift
		for (int i = 0; i < sGiftBoy.length; i++) {
			sGiftBoy[i] = new Sprite(100, 100, 
					trGiftBoy[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sGiftBoy[i]);
			sGiftBoy[i].setScale(0.6f);
			sGiftBoy[i].setVisible(false);
			sGiftBoy[i].setZIndex(3);
		}
		
		//Gift Left
		for (int i = 0; i < asGiftLeft.length; i++) {
			asGiftLeft[i] = new AniGift(10, 10, ttxrGift[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(asGiftLeft[i]);
			asGiftLeft[i].setVisible(false);
			asGiftLeft[i].setIdGift(i);
			asGiftLeft[i].setTouch(true);
			asGiftLeft[i].setZIndex(4);
		}
		
		//Gift Right
		for (int i = 0; i < asGiftRight.length; i++) {
			asGiftRight[i] = new AniGift(10, 10, ttxrGift[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(asGiftRight[i]);
			asGiftRight[i].setVisible(false);
			asGiftRight[i].setIdGift(i);
			asGiftRight[i].setTouch(true);
			asGiftRight[i].setZIndex(4);
		}
		
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void combineGimmic3WithAction() {

	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("jinguru/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("jinguru/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "jinguru/gfx/");
		super.onCreateResources();
	}

	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame");
		super.onResumeGame();
		initGame();
		startGame();
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		mScene.clearEntityModifiers();
		mScene.clearUpdateHandlers();
		stopSound();
		resetGame();
	}

	private void initGame() {
		rdNoRepeat.setLenghNoRepeat(10);
		iIndexGiftLeft = 0;
		iIndexGiftRight = 0;
		
		if (alIdGiftLeft.size() > 0) {
			alIdGiftLeft.clear();
		}
		if (alIdGiftRight.size() > 0) {
			alIdGiftRight.clear();
		}
		for (int i = 0; i < 10; i++) {
			alIdGiftLeft.add(i);
		}
		for (int i = 0; i < 10; i++) {
			alIdGiftRight.add(i);
		}
		
		isSnowBlue = true;
		isSnowRed = true;
		isSnowYellow = true;
		isSantaClaus = true;
		isWindChime = true;
		isPine = true;
		isSoundSnowRed = true;
	}
	
	private void startGame() {
		//Left
		mScene.unregisterUpdateHandler(giftHandlerLeft);
		giftHandlerLeft = new TimerHandler(6.0f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				int idPath = rdJinguru.nextInt(3);
				if(alIdGiftLeft.size() > 0) {
					int index = rdJinguru.nextInt(alIdGiftLeft.size());
					int idGift = alIdGiftLeft.get(index);
					alIdGiftLeft.remove(index);
					moveGiftLeft(asGiftLeft[idGift], pPathLeft[idPath]);
				}
			}
		});
		mScene.registerUpdateHandler(giftHandlerLeft);
		
		//Right
		mScene.registerUpdateHandler(new TimerHandler(3.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				mScene.unregisterUpdateHandler(giftHandlerRight);
				giftHandlerRight = new TimerHandler(6.0f, true, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						int idPath = rdJinguru.nextInt(3);
						Log.d(TAG, "idPath:        " + idPath);
						if(alIdGiftRight.size() > 0) {
							int index = rdJinguru.nextInt(alIdGiftRight.size());
							int idGift = alIdGiftRight.get(index);
							alIdGiftRight.remove(index);
							moveGiftRight(asGiftRight[idGift], pPathRight[idPath]);
						}
					}
				});
				mScene.registerUpdateHandler(giftHandlerRight);
			}
		}));
		
	}
	
	private void moveGiftLeft(final AniGift asGift, final Path pPath) {
		asGift.registerEntityModifier(pathLeftModifier = new PathModifier(5.0f, pPath, 
				new IPathModifierListener() {
					@Override
					public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
					}
					@Override
					public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
					}
					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						asGift.setVisible(true);
						asGift.setCurrentTileIndex(0);
					}
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						asGift.registerEntityModifier(new MoveYModifier(1.0f, asGift.getY(), 700, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								asGift.setTouch(false);
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								asGift.setTouch(true);
								asGift.setVisible(false);
								alIdGiftLeft.add(asGift.getIdGift());
							}
						}));
					}
				}));
	}
	
	private void moveGiftRight(final AniGift asGift, final Path pPath) {
		asGift.registerEntityModifier(pathRightModifier = new PathModifier(5.0f, pPath, 
				new IPathModifierListener() {
					@Override
					public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
					}
					@Override
					public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
					}
					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						asGift.setVisible(true);
						asGift.setCurrentTileIndex(0);
					}
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						asGift.registerEntityModifier(new MoveYModifier(1.0f, asGift.getY(), 700, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								asGift.setTouch(false);
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								asGift.setTouch(true);
								asGift.setVisible(false);
								alIdGiftRight.add(asGift.getIdGift());
							}
						}));
					}
				}));
	}
	
	private void touchGiftLeft(final AniGift asGift, int pFromY) {
		if (asGift.isTouch()) {
			asGift.setTouch(false);
			asGift.unregisterEntityModifier(pathLeftModifier);
			asGift.clearEntityModifiers();
			asGift.registerEntityModifier(new MoveYModifier(1.5f, asGift.getY(), 250, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					asGift.setCurrentTileIndex(1);
					OGG_A81_6_PAN.play();
					for (int i = 7 - iIndexGiftLeft; i >= 0; i--) {
						asGirl[i].setCurrentTileIndex(1);
					}
					mScene.registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							OGG_A81_6_KIRAKIRA.play();
							long pDurations[] = new long[] {100, 100};
							int pFrame[] = new int[] {2, 3};
							asGift.animate(pDurations, pFrame, 1);
						}
					}));
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					if (iIndexGiftLeft == 8) {
						asGift.setTouch(true);
						asGift.setVisible(false);
						alIdGiftLeft.add(asGift.getIdGift());
						sGiftGirl[asGift.getIdGift()].setPosition(asGift.getX(), asGift.getY());
						sGiftGirl[asGift.getIdGift()].setVisible(true);
						sGiftGirl[asGift.getIdGift()].registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftGirl[asGift.getIdGift()].setZIndex(0);
								sWall.setZIndex(1);
								//sGiftGirl[asGift.getIdGift()].setZIndex(1);
								mScene.sortChildren();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftGirl[asGift.getIdGift()].setZIndex(3);
								mScene.sortChildren();
							}
						},
								new ScaleModifier(0.3f, 1.0f, 1.5f),
								new ScaleModifier(0.3f, 1.5f, 1.0f),
								new MoveYModifier(2.0f, asGift.getY(), 600)));
					}
					
					if(iIndexGiftLeft < 8) {
						asGift.setTouch(true);
						asGift.setVisible(false);
						sGiftGirl[asGift.getIdGift()].setPosition(asGift.getX(), asGift.getY());
						sGiftGirl[asGift.getIdGift()].setVisible(true);
						sGiftGirl[asGift.getIdGift()].registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftGirl[asGift.getIdGift()].setZIndex(5);
								mScene.sortChildren();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if (iIndexGiftLeft < 8) {
									iIndexGiftLeft++;
								}
								sGiftGirl[asGift.getIdGift()].setZIndex(3);
								mScene.sortChildren();
								
								//Random Sound
								int iRandom = rdJinguru.nextInt(5);
								if(iRandom == 0) {
									OGG_A81_5_2_WAAI3.play();
								} else if(iRandom == 1) {
									OGG_A81_5_2_YEAH2.play();
								} else if(iRandom == 2) {
									OGG_A81_5_2_YEAH3.play();
								} else if(iRandom == 3) {
									OGG_A81_5_2_URESHI.play();
								} else {
									OGG_A81_5_2_YATTA1.play();
								}
								
								sGiftGirl[asGift.getIdGift()].detachSelf();
								asGirl[8 - iIndexGiftLeft].attachChild(sGiftGirl[asGift.getIdGift()]);
								asGirl[8 - iIndexGiftLeft].setStatus(true);
								sGiftGirl[asGift.getIdGift()].setScale(0.6f);
								sGiftGirl[asGift.getIdGift()].setPosition(-5, -50);
								sGiftGirl[asGift.getIdGift()].setVisible(true);
								
								for (int i = 7 - iIndexGiftLeft; i >= 0; i--) {
									asGirl[i].setCurrentTileIndex(0);
								}
								if(iIndexGiftRight == 8 && iIndexGiftLeft == 8) {
									Log.d(TAG, "win");
									mScene.unregisterUpdateHandler(giftHandlerLeft);
									mScene.unregisterUpdateHandler(giftHandlerRight);
									for (int i = 0; i < asGiftLeft.length; i++) {
										asGiftLeft[i].reset();
									}
									for (int i = 0; i < asGiftRight.length; i++) {
										asGiftRight[i].reset();
									}
									mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
										
										@Override
										public void onTimePassed(TimerHandler arg0) {
											winGame();
										}
									}));
								}
							}
						},
						new ScaleModifier(0.3f, 1.0f, 1.5f),
						new ScaleModifier(0.3f, 1.5f, 1.0f),
						new MoveModifier(1.0f, asGift.getX(), idPositionGiftGirl[iIndexGiftLeft][0],
								asGift.getY(), idPositionGiftGirl[iIndexGiftLeft][1]),
								new ScaleModifier(0.1f, 1.0f, 0.6f)));

						Log.d(TAG, "iIndexGiftLeft:      " + iIndexGiftLeft);
					}
				}
			}));
		}
	}
	
	private void touchGiftRight(final AniGift asGift, int pFromY) {
		if (asGift.isTouch()) {
			asGift.setTouch(false);
			asGift.unregisterEntityModifier(pathRightModifier);
			asGift.clearEntityModifiers();
			asGift.registerEntityModifier(new MoveYModifier(1.5f, asGift.getY(), 250, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					asGift.setCurrentTileIndex(1);
					OGG_A81_6_PAN.play();
					for (int i = 7 - iIndexGiftRight; i >= 0; i--) {
						asBoy[dkmid[i]].setCurrentTileIndex(1);
					}
					mScene.registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							OGG_A81_6_KIRAKIRA.play();
							long pDurations[] = new long[] {100, 100};
							int pFrame[] = new int[] {2, 3};
							asGift.animate(pDurations, pFrame, 1);
						}
					}));
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					if (iIndexGiftRight == 8) {
						asGift.setTouch(true);
						asGift.setVisible(false);
						alIdGiftRight.add(asGift.getIdGift());
						sGiftBoy[asGift.getIdGift()].setPosition(asGift.getX(), asGift.getY());
						sGiftBoy[asGift.getIdGift()].setVisible(true);
						sGiftBoy[asGift.getIdGift()].registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftBoy[asGift.getIdGift()].setZIndex(0);
								sWall.setZIndex(1);
								//sGiftBoy[asGift.getIdGift()].setZIndex(1);
								mScene.sortChildren();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftBoy[asGift.getIdGift()].setZIndex(3);
								mScene.sortChildren();
							}
						},
								new ScaleModifier(0.3f, 1.0f, 1.5f),
								new ScaleModifier(0.3f, 1.5f, 1.0f),
								new MoveYModifier(2.0f, asGift.getY(), 600)));
					}
					
					if (iIndexGiftRight < 8) {
						asGift.setTouch(true);
						asGift.setVisible(false);
						sGiftBoy[asGift.getIdGift()].setPosition(asGift.getX(), asGift.getY());
						sGiftBoy[asGift.getIdGift()].setVisible(true);
						sGiftBoy[asGift.getIdGift()].registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sGiftBoy[asGift.getIdGift()].setZIndex(5);
								mScene.sortChildren();
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if (iIndexGiftRight < 8) {
									iIndexGiftRight++;
								}
								sGiftBoy[asGift.getIdGift()].setZIndex(3);
								mScene.sortChildren();
								
								//Random Sound
								int iRandom = rdJinguru.nextInt(5);
								if(iRandom == 0) {
									OGG_A81_5_2_WAAI1.play();
								} else if(iRandom == 1) {
									OGG_A81_5_2_WAAI2.play();
								} else if(iRandom == 2) {
									OGG_A81_5_2_YEAH1.play();
								} else if(iRandom == 3) {
									OGG_A81_5_2_PRESENTDA.play();
								} else {
									OGG_A81_5_2_YATTA2.play();
								}
								
								sGiftBoy[asGift.getIdGift()].detachSelf();
								asBoy[dkmid[8 - iIndexGiftRight]].attachChild(sGiftBoy[asGift.getIdGift()]);
								asBoy[dkmid[8 - iIndexGiftRight]].setStatus(true);
								sGiftBoy[asGift.getIdGift()].setScale(0.6f);
								sGiftBoy[asGift.getIdGift()].setPosition(-5, -50);
								sGiftBoy[asGift.getIdGift()].setVisible(true);
								
								for (int i = 7 - iIndexGiftRight; i >= 0; i--) {
									asBoy[dkmid[i]].setCurrentTileIndex(0);
								}
								if(iIndexGiftRight == 8 && iIndexGiftLeft == 8) {
									Log.d(TAG, "win");
									mScene.unregisterUpdateHandler(giftHandlerLeft);
									mScene.unregisterUpdateHandler(giftHandlerRight);
									for (int i = 0; i < asGiftLeft.length; i++) {
										asGiftLeft[i].reset();
									}
									for (int i = 0; i < asGiftRight.length; i++) {
										asGiftRight[i].reset();
									}
									mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
										
										@Override
										public void onTimePassed(TimerHandler arg0) {
											winGame();
										}
									}));
								}
							}
						},
						new ScaleModifier(0.3f, 1.0f, 1.5f),
						new ScaleModifier(0.3f, 1.5f, 1.0f),
						new MoveModifier(1.0f, asGift.getX(), idPositionGiftBoy[iIndexGiftRight][0],
								asGift.getY(), idPositionGiftBoy[iIndexGiftRight][1]),
								new ScaleModifier(0.1f, 1.0f, 0.6f)));
					}
				}
			}));
		}
	}
	
	private void winGame() {
		mScene.unregisterUpdateHandler(snowmanRedHandler);
		long pDurations[] = new long[] {500, 500};
		int pFrame[] = new int[] {0, 1};
		asBackgroundWin.setVisible(true);
		asBackgroundWin.animate(pDurations, pFrame, -1);
		
		OGG_A81_3_3_OMEDETOU2.play();
		for (int i = 0; i < asGirl.length; i++) {
			actionChildWin(asGirl[i]);
		}
		for (int i = 0; i < asBoy.length; i++) {
			actionChildWin(asBoy[i]);
		}
		
		sSnowmanBlue.setVisible(false);
		sSnowmanYellow.setVisible(false);
		asSnowmanRed.setVisible(false);
		asSantaClaus.setVisible(false);
		asPine.setVisible(false);
		sWindChime.setVisible(false);
		
		asReindeer.setVisible(true);
		asReindeer.animate(pDurations, pFrame, -1);
		asReindeer.registerEntityModifier(new MoveXModifier(5.0f, asReindeer.getmXFirst(), -asReindeer.getWidth(),
				new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						moveStar();
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
							
							@Override
							public void onTimePassed(TimerHandler arg0) {
								// TODO Auto-generated method stub
								resetGame();
							}
						}));
						//resetGame();
					}
				}));
	}
	
	private void moveStar() {
		mScene.registerUpdateHandler(starHandler = new TimerHandler(0.17f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				
				final Sprite star1 = spStar.obtainPoolItem();
				mScene.attachChild(star1);
				star1.setPosition(pX, 278);
				alStar.add(star1);
				star1.registerEntityModifier(new ParallelEntityModifier(
						new MoveModifier(2.0f, pX, pX + 100, 280, 350, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								runOnUpdateThread(new Runnable() {
									
									@Override
									public void run() {
										star1.setVisible(false);
//										spStar.onHandleRecycleItem(star1);
//										alStar.remove(star1);
									}
								});
							}
						}),
						new ScaleModifier(2.0f, 2, 1),
						new AlphaModifier(2.0f, 1, 0)));
				
				
				int pFromY;
				int rd = rdJinguru.nextInt(2);
				if(rd == 0) {
					pFromY = 290 + rdJinguru.nextInt(20);
				} else {
					pFromY = 250 + rdJinguru.nextInt(20);
				}
				
				final Sprite star2 = spStar.obtainPoolItem();
				mScene.attachChild(star2);
				alStar.add(star2);
				star2.registerEntityModifier(new ParallelEntityModifier(
						new MoveModifier(2.0f, pX, pX + 100, pFromY, pFromY + 50, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								runOnUpdateThread(new Runnable() {
									
									@Override
									public void run() {
										star2.setVisible(false);
//										spStar.onHandleRecycleItem(star2);
//										alStar.remove(star2);
									}
								});
							}
						}),
						new ScaleModifier(2.0f, 1, 0.8f),
						new AlphaModifier(2.0f, 1, 0)));
				pX = pX - 30;
			}
		}));
	}
	
	private void actionChildWin(final Children mySprite) {
		mySprite.clearEntityModifiers();
		mySprite.setTouch(false);
		mySprite.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveYModifier(0.3f, mySprite.getmYFirst(), mySprite.getmYFirst() - 20),
						new MoveYModifier(0.3f, mySprite.getmYFirst() - 20, mySprite.getmYFirst())), -1, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					}
				}));
	}
	
	private void stopSound() {
		OGG_A81_F.stop();
		OGG_A81_3_2_HELLO.stop();
		OGG_A81_5_2_YEAH1.stop();
		OGG_A81_5_2_YEAH2.stop();
		OGG_A81_1_TEKKIN.stop();
		OGG_A81_2_KIRAKIRA.stop();
		OGG_A81_4_ZAZA.stop();
		OGG_A81_5_2_WAAI2.stop();
		OGG_A81_3_2_YAA.stop();
		OGG_A81_6_KIRAKIRA.stop();
		OGG_A81_5_2_WAAI3.stop();
		OGG_A81_3_1_MARRYXMAS.stop();
		OGG_A81_5_2_YEAH3.stop();
		OGG_A81_2_3_BELL.stop();
		OGG_A81_5_2_WAAI1.stop();
		OGG_A81_6_PAN.stop();
		OGG_A81_3_3_OMEDETOU2.stop();
		for (int i = 0; i < soundChildLeft.length; i++) {
			soundChildLeft[i].stop();
		}
		for (int i = 0; i < soundChildRight.length; i++) {
			soundChildRight[i].stop();
		}
	}
	
	private void resetGame() {
		iIndexGiftLeft = 0;
		iIndexGiftRight = 0;
		pX = 960;
		if(!alIdGiftLeft.isEmpty()) {
			alIdGiftLeft.clear();
		}
		if (!alIdGiftRight.isEmpty()) {
			alIdGiftRight.clear();
		}
		
		if (starHandler != null) {
			mScene.unregisterUpdateHandler(starHandler);
		}
		if(alStar.size() > 0) {
			for (int i = 0; i < alStar.size(); i++) {
				alStar.get(i).clearEntityModifiers();
				alStar.get(i).clearUpdateHandlers();
				spStar.onHandleRecycleItem(alStar.get(i));
			}
			alStar.clear();
		}
		
		for (int i = 0; i < asGiftLeft.length; i++) {
			if (asGiftLeft[i] != null) {
				asGiftLeft[i].reset();
			}
		}
		for (int i = 0; i < asGiftRight.length; i++) {
			if(asGiftRight[i] != null) {
				asGiftRight[i].reset();
			}
		}
		
		for (int i = 0; i < asGirl.length; i++) {
			if(asGirl[i] != null) {
				asGirl[i].reset();
			}
		}
		
		for (int i = 0; i < asBoy.length; i++) {
			if(asBoy[i] != null) {
				asBoy[i].reset();
			}
		}
		
		for (int i = 0; i < sGiftGirl.length; i++) {
			if(sGiftGirl[i] != null) {
				sGiftGirl[i].detachSelf();
				mScene.attachChild(sGiftGirl[i]);
				sGiftGirl[i].setPosition(sGiftGirl[i].getmXFirst(), sGiftGirl[i].getmYFirst());
				sGiftGirl[i].setVisible(false);
				sGiftGirl[i].setScale(1);
				sGiftGirl[i].clearEntityModifiers();
				sGiftGirl[i].clearUpdateHandlers();
			}
		}
		
		for (int i = 0; i < sGiftBoy.length; i++) {
			if(sGiftBoy[i] != null) {
				sGiftBoy[i].detachSelf();
				mScene.attachChild(sGiftBoy[i]);
				sGiftBoy[i].setPosition(sGiftBoy[i].getmXFirst(), sGiftBoy[i].getmYFirst());
				sGiftBoy[i].setVisible(false);
				sGiftBoy[i].setScale(1);
				sGiftBoy[i].clearEntityModifiers();
				sGiftBoy[i].clearUpdateHandlers();
			}
		}
		
		if (asBackgroundWin != null) {
			asBackgroundWin.stopAnimation();
			asBackgroundWin.setVisible(false);
		}
		
		if (asReindeer != null) {
			asReindeer.clearEntityModifiers();
			asReindeer.setPosition(asReindeer.getmXFirst(), asReindeer.getmYFirst());
			asReindeer.setVisible(false);
			asReindeer.stopAnimation();
		}
		
		sSnowmanBlue.setVisible(true);
		sSnowmanYellow.setVisible(true);
		asSnowmanRed.setVisible(true);
		asSantaClaus.setVisible(true);
		asPine.setVisible(true);
		sWindChime.setVisible(true);
		
		initGame();
		startGame();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			
			for (int i = 0; i < asGiftLeft.length; i++) {
				if(asGiftLeft[i].contains(pX, pY) && asGiftLeft[i].isVisible() 
						&& asGiftLeft[i].getCurrentTileIndex() == 0 && asGiftLeft[i].isTouch()) {
					touchGiftLeft(asGiftLeft[i], pY);
					return true;
				}
			}
			
			for (int i = 0; i < asGiftRight.length; i++) {
				if(asGiftRight[i].contains(pX, pY) && asGiftRight[i].isVisible()
						&& asGiftRight[i].getCurrentTileIndex() == 0 && asGiftRight[i].isTouch()) {
					touchGiftRight(asGiftRight[i], pY);
					return true;
				}
			}
			
			for (int i = 0; i < asGirl.length; i++) {
				if(checkContains(asGirl[i], 22, 27, 106, 116, pX, pY)) {
					Sound mp3Random = null;
					if(!asGirl[i].isStatus()) {
						mp3Random = soundChildLeft[i];
					} else {
						int iRandom = rdJinguru.nextInt(5);
						if(iRandom == 0) {
							mp3Random = OGG_A81_5_2_WAAI3;
						} else if(iRandom == 1) {
							mp3Random = OGG_A81_5_2_YEAH2;
						} else if(iRandom == 2) {
							mp3Random = OGG_A81_5_2_YEAH3;
						} else if(iRandom == 3) {
							mp3Random = OGG_A81_5_2_URESHI;
						} else {
							mp3Random = OGG_A81_5_2_YATTA1;
						}
					}
					actionTouchChid(asGirl[i], mp3Random);
				}
			}
			
			for (int i = 0; i < asBoy.length; i++) {
				if (checkContains(asBoy[i], 22, 27, 106, 116, pX, pY)) {
					Sound mp3Random = null;
					if(!asBoy[i].isStatus()) {
						mp3Random = soundChildRight[i];
					} else {
						int iRandom = rdJinguru.nextInt(5);
						if(iRandom == 0) {
							mp3Random = OGG_A81_5_2_WAAI1;
						} else if(iRandom == 1) {
							mp3Random = OGG_A81_5_2_WAAI2;
						} else if(iRandom == 2) {
							mp3Random = OGG_A81_5_2_YEAH1;
						} else if(iRandom == 3) {
							mp3Random = OGG_A81_5_2_PRESENTDA;
						} else {
							mp3Random = OGG_A81_5_2_YATTA2;
						}
					}
					actionTouchChid(asBoy[i], mp3Random);
				}
			}
			
			if(checkContains(asPine, 77, 108, 201, 359, pX, pY) && asPine.isVisible()) {
				if(isPine) {
					isPine = false;
					OGG_A81_2_KIRAKIRA.play();
					long pDurations[] = new long[] {400, 400};
					asPine.animate(pDurations, 2, new IAnimationListener() {
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
							isPine = true;
							asPine.setCurrentTileIndex(0);
						}
					});
				}
			}
			
			if(sSnowmanBlue.contains(pX, pY) && sSnowmanBlue.isVisible()) {
				if (isSnowBlue) {
					sSnowmanBlue.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							isSnowBlue = false;
							OGG_A81_4_ZAZA.play();
						}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isSnowBlue = true;
						}
					},
					new RotationAtModifier(0.2f, 0, -5, sSnowmanBlue.getWidth() / 2, sSnowmanBlue.getHeight()),
					new RotationAtModifier(0.2f, -5, 5, sSnowmanBlue.getWidth() / 2, sSnowmanBlue.getHeight()),
					new RotationAtModifier(0.2f, 5, 0, sSnowmanBlue.getWidth() / 2, sSnowmanBlue.getHeight())
							));
				}
			}
			
			if(sSnowmanYellow.contains(pX, pY) && sSnowmanYellow.isVisible()) {
				if (isSnowYellow) {
					sSnowmanYellow.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							isSnowYellow = false;
							OGG_A81_4_ZAZA.play();
						}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isSnowYellow = true;
						}
					},
					new RotationAtModifier(0.2f, 0, -5, sSnowmanYellow.getWidth() / 2, sSnowmanYellow.getHeight()),
					new RotationAtModifier(0.2f, -5, 5, sSnowmanBlue.getWidth() / 2, sSnowmanYellow.getHeight()),
					new RotationAtModifier(0.2f, 5, 0, sSnowmanYellow.getWidth() / 2, sSnowmanYellow.getHeight())
							));
				}
			}
			
			if(asSnowmanRed.contains(pX, pY) && asSnowmanRed.isVisible()) {
				if (isSnowRed) {
					isSnowRed = false;
					//isSantaClaus = false;
					asSantaClaus.setVisible(false);
					mScene.unregisterUpdateHandler(santaHandle);
					asSnowmanRed.setCurrentTileIndex(1);
					OGG_A81_3_1_MARRYXMAS.stop();
					OGG_A81_4_ZAZA.play();
					if(isSoundSnowRed) {
						isSoundSnowRed = false;
						OGG_A81_3_2_YAA.play();
					} else {
						isSoundSnowRed = true;
						OGG_A81_3_2_HELLO.play();
					}
					mScene.registerUpdateHandler(snowmanRedHandler = new TimerHandler(1.0f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							isSnowRed = true;
							isSantaClaus = true;
							asSnowmanRed.setCurrentTileIndex(0);
							asSantaClaus.setCurrentTileIndex(0);
							asSantaClaus.setVisible(true);
						}
					}));
				}
			}
			
			if(asSantaClaus.contains(pX, pY) && asSantaClaus.isVisible()) {
				if (isSantaClaus) {
					//isSnowRed = false;
					isSantaClaus = false;
					OGG_A81_3_1_MARRYXMAS.play();
					asSantaClaus.setCurrentTileIndex(1);
					mScene.registerUpdateHandler(santaHandle = new TimerHandler(2.0f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							isSnowRed = true;
							isSantaClaus = true;
							asSantaClaus.setCurrentTileIndex(0);
						}
					}));
				}
			}
			
			if (sWindChime.contains(pX, pY) && sWindChime.isVisible()) {
				if (isWindChime) {
					isWindChime = false;
					OGG_A81_2_3_BELL.play();
					sWindChime.registerEntityModifier(new LoopEntityModifier(
							new SequenceEntityModifier(
									new RotationAtModifier(0.2f, 0, -10, sWindChime.getWidth() / 2, 0),
									new RotationAtModifier(0.2f, -10, 10, sWindChime.getWidth() / 2, 0),
									new RotationAtModifier(0.2f, 10, 0, sWindChime.getWidth() / 2, 0)), 2, 
									new ILoopEntityModifierListener() {
										
										@Override
										public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
											
										}
										
										@Override
										public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
											isWindChime = true;
										}
									}));
				}
			}
		}
		return false;
	}
	
	private void actionTouchChid(final Children mySprite, Sound mp3Speak) {
		if (mySprite.isTouch()) {
			if (mp3Speak != null) {
				mySprite.speak(mp3Speak);
			}
			mySprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					mySprite.setTouch(false);
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mySprite.setPosition(mySprite.getmXFirst(), mySprite.getmYFirst());
					mySprite.setTouch(true);
				}
			}, 
			new MoveYModifier(0.3f, mySprite.getmYFirst(), mySprite.getmYFirst() - 20),
			new MoveYModifier(0.3f, mySprite.getmYFirst() - 20, mySprite.getmYFirst())));
		}
	}
	
	private class Children extends AnimatedSprite {
		private boolean isTouch;
		private int iIdChild;
		private boolean bStatus;
		
		public Children(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}

		@SuppressWarnings("unused")
		public int getiIdChild() {
			return iIdChild;
		}

		public boolean isStatus() {
			return bStatus;
		}

		public void setStatus(boolean bStatus) {
			this.bStatus = bStatus;
		}

		@SuppressWarnings("unused")
		public void setiIdChild(int iIdChild) {
			this.iIdChild = iIdChild;
		}
		
		public void speak(Sound mp3Sound) {
			mp3Sound.play();
		}
		public void reset() {
			this.clearEntityModifiers();
			this.clearUpdateHandlers();
			this.setPosition(this.getmXFirst(), this.getmYFirst());
			this.setCurrentTileIndex(0);
			this.setTouch(true);
			this.setStatus(false);
		}
	}
	
	private class AniGift extends AnimatedSprite {
		private int iIdGift;
		private boolean isTouch;

		public AniGift(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		}

		public int getIdGift() {
			return iIdGift;
		}

		public void setIdGift(int iIdGift) {
			this.iIdGift = iIdGift;
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}
		
		public void reset() {
			this.clearEntityModifiers();
			this.clearUpdateHandlers();
			this.setPosition(this.getmXFirst(), this.getmYFirst());
			this.setVisible(false);
			this.setTouch(true);
		}
	}
}
