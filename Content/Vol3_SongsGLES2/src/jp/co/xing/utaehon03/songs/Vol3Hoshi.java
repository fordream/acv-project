package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.SpritePool;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3HoshiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Hoshi extends BaseGameFragment implements IOnSceneTouchListener{
	
	private static final String TAG = "LOG_HOSHI";

	private TexturePack tpHoshi1, tpHoshi2, tpHoshi3, tpHoshi4, tpHoshi5, tpHoshi6;
	private TexturePackTextureRegionLibrary tpLibHoshi3, tpLibHoshi4, tpLibHoshi5, tpLibHoshi6;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TiledTextureRegion ttxrBackground, ttxrBackgroundSeason;
	private AnimatedSprite asBackground, asBackgroundSeason;
	
	private TiledTextureRegion ttxrGimmicSpring, ttxrGimmicSummer, ttxrGimmicAutumn, ttxrGimmicWinter;
	private Season asGimmicSpring, asGimmicSummer, asGimmicAutumn, asGimmicWinter;
	
	private ITextureRegion[] itrObjLeft = new ITextureRegion[8];
	private ObjHoshi[] sObjLeft = new ObjHoshi[8];
	
	private ITextureRegion[] itrObjCenter = new ITextureRegion[8];
	private ObjHoshi[] sObjCenter = new ObjHoshi[8];
	
	private ITextureRegion[] itrObjRight = new ITextureRegion[8];
	private ObjHoshi[] sObjRight = new ObjHoshi[8];
	
	private ITextureRegion trStar[] = new ITextureRegion[8];
	private SpritePool sStarPool[] = new SpritePool[8];
	
	private ITextureRegion trStarSpring, trStarSummer, trStarAutumn, trStarWinter;
	private ObjHoshi sStarSpring, sStarSummer, sStarAutumn, sStarWinter;
	
	private int iSeason;
	private int  pLastX = 0;
	private int  pLastY = 0;
	private float arrPointStar[][]= {
			{0, -60, -60, -60, 0, 60, 60, 60},
			{-60, -60, 0, 60, 60, 60, 0, -60}
	};
	private float[][][] idCheckPolygonCenter = {
			{{232, 10, 144, 294}, {21, 293, 346, 96}},
			{{102, 102, 294, 296}, {126, 317, 320, 129}},
			{{162, 25, 118, 312}, {114, 272, 348, 170}},
			{{109, 195, 323, 209}, {117, 341, 292, 84}}
	};
	private float[][][] idCheckPolygonLeft = {
			{{90, 132, 330, 266}, {103, 314, 172, 42}},
			{{243, 57, 272}, {33, 218, 288}},
			{{133, 87, 236, 302}, {47, 207, 267, 191}},
			{{75, 110, 215, 283}, {53, 280, 264, 85}}
	};
	private float[][][] idCheckPolygonRight = {
			{{262, 20, 76, 283}, {91, 222, 339, 227}},
			{{224, 51, 127, 252}, {57, 264, 300, 138}},
			{{223, 120, 264}, {179, 210, 328}},
			{{146, 23, 200, 311}, {0, 98, 329, 161}}
	};
	
	private boolean isTouchSky, isMoveStar;
	
	private Sound OGG_A77_3_1_1, OGG_A77_3_3_1, OGG_A77_2_SEIZA, OGG_A77_5_STAR, OGG_A77_3_4_1,
				  OGG_A77_3_2_1, OGG_A77_1_KIRA6;
	
	private Sound[] oggSoundObjLeft = new Sound[4];
	private Sound[] oggSoundObjCenter = new Sound[4];
	private Sound[] oggSoundObjRight = new Sound[4];
	
	@Override
	protected void loadKaraokeResources() {
		tpHoshi1 = mTexturePackLoaderFromSource.load("hoshi1.xml");
		tpHoshi1.loadTexture();
		
		tpHoshi2 = mTexturePackLoaderFromSource.load("hoshi2.xml");
		tpHoshi2.loadTexture();
		
		tpHoshi3 = mTexturePackLoaderFromSource.load("hoshi3.xml");
		tpHoshi3.loadTexture();
		tpLibHoshi3 = tpHoshi3.getTexturePackTextureRegionLibrary();
		
		tpHoshi4 = mTexturePackLoaderFromSource.load("hoshi4.xml");
		tpHoshi4.loadTexture();
		tpLibHoshi4 = tpHoshi4.getTexturePackTextureRegionLibrary();
		
		tpHoshi5 = mTexturePackLoaderFromSource.load("hoshi5.xml");
		tpHoshi5.loadTexture();
		tpLibHoshi5 = tpHoshi5.getTexturePackTextureRegionLibrary();
		
		tpHoshi6 = mTexturePackLoaderFromSource.load("hoshi6.xml");
		tpHoshi6.loadTexture();
		tpLibHoshi6 = tpHoshi6.getTexturePackTextureRegionLibrary();
		
		this.ttxrBackground = getTiledTextureFromPacker(tpHoshi1, Vol3HoshiResource.idBackground);
		this.ttxrBackgroundSeason = getTiledTextureFromPacker(tpHoshi2, Vol3HoshiResource.idBackgroundSeason);
		
		this.ttxrGimmicSpring = getTiledTextureFromPacker(tpHoshi6, 
				Vol3HoshiResource.A77_3_1_1_IPHONE_ID,
				Vol3HoshiResource.A77_3_1_2_IPHONE_ID);
		
		this.ttxrGimmicSummer = getTiledTextureFromPacker(tpHoshi6, 
				Vol3HoshiResource.A77_3_2_1_IPHONE_ID,
				Vol3HoshiResource.A77_3_2_2_IPHONE_ID);
		
		this.ttxrGimmicAutumn = getTiledTextureFromPacker(tpHoshi6, 
				Vol3HoshiResource.A77_3_3_1_IPHONE_ID,
				Vol3HoshiResource.A77_3_3_2_IPHONE_ID);
		
		this.ttxrGimmicWinter = getTiledTextureFromPacker(tpHoshi6, 
				Vol3HoshiResource.A77_3_4_1_IPHONE_ID,
				Vol3HoshiResource.A77_3_4_2_IPHONE_ID);
		
		for (int i = 0; i < itrObjLeft.length; i++) {
			this.itrObjLeft[i] = tpLibHoshi4.get(Vol3HoshiResource.idObjLeft[i]);
		}
		
		for (int i = 0; i < itrObjCenter.length; i++) {
			this.itrObjCenter[i] = tpLibHoshi3.get(Vol3HoshiResource.idObjCenter[i]);
		}
		
		for (int i = 0; i < itrObjRight.length; i++) {
			this.itrObjRight[i] = tpLibHoshi5.get(Vol3HoshiResource.idObjRight[i]);
		}
		
		for (int i = 0; i < trStar.length; i++) {
			trStar[i]= tpLibHoshi6.get(Vol3HoshiResource.idStar[i]);
		}
		
		this.trStarSpring = tpLibHoshi6.get(Vol3HoshiResource.A77_5_1_IPHONE_ID);
		this.trStarSummer = tpLibHoshi6.get(Vol3HoshiResource.A77_5_2_IPHONE_ID);
		this.trStarAutumn = tpLibHoshi6.get(Vol3HoshiResource.A77_5_3_IPHONE_ID);
		this.trStarWinter = tpLibHoshi6.get(Vol3HoshiResource.A77_5_4_IPHONE_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A77_3_1_1 = loadSoundResourceFromSD(Vol3HoshiResource.A77_3_1_1);
		OGG_A77_3_3_1 = loadSoundResourceFromSD(Vol3HoshiResource.A77_3_3_1);
		OGG_A77_2_SEIZA = loadSoundResourceFromSD(Vol3HoshiResource.A77_2_SEIZA);
		OGG_A77_5_STAR = loadSoundResourceFromSD(Vol3HoshiResource.A77_5_STAR);
		OGG_A77_3_4_1 = loadSoundResourceFromSD(Vol3HoshiResource.A77_3_4_1);
		OGG_A77_3_2_1 = loadSoundResourceFromSD(Vol3HoshiResource.A77_3_2_1);
		OGG_A77_1_KIRA6 = loadSoundResourceFromSD(Vol3HoshiResource.A77_1_KIRA6);
		for (int i = 0; i < oggSoundObjLeft.length; i++) {
			oggSoundObjLeft[i] = loadSoundResourceFromSD(Vol3HoshiResource.idSoundObjLeft[i]);
		}
		for (int i = 0; i < oggSoundObjLeft.length; i++) {
			oggSoundObjCenter[i] = loadSoundResourceFromSD(Vol3HoshiResource.idSoundObjCenter[i]);
		}
		for (int i = 0; i < oggSoundObjLeft.length; i++) {
			oggSoundObjRight[i] = loadSoundResourceFromSD(Vol3HoshiResource.idSoundObjRight[i]);
		}
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		
		asBackground = new AnimatedSprite(0, 0, ttxrBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(asBackground);
		
		for (int i = 0; i < sObjLeft.length; i++) {
			sObjLeft[i] = new ObjHoshi(86, 19, itrObjLeft[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sObjLeft[i]);
			sObjLeft[i].setVisible(false);
			sObjLeft[i].setTouch(true);
		}
		
		for (int i = 0; i < sObjCenter.length; i++) {
			sObjCenter[i] = new ObjHoshi(318, 107, itrObjCenter[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sObjCenter[i]);
			sObjCenter[i].setVisible(false);
			sObjCenter[i].setTouch(true);
		}
		
		for (int i = 0; i < sObjRight.length; i++) {
			sObjRight[i] = new ObjHoshi(584, 83, itrObjRight[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sObjRight[i]);
			sObjRight[i].setVisible(false);
			sObjRight[i].setTouch(true);
		}
		
		sStarSpring = new ObjHoshi(960, 0, trStarSpring, this.getVertexBufferObjectManager());
		mScene.attachChild(sStarSpring);
		sStarSpring.setVisible(false);
		sStarSpring.setTouch(true);
		
		sStarSummer = new ObjHoshi(960, 0, trStarSummer, this.getVertexBufferObjectManager());
		mScene.attachChild(sStarSummer);
		sStarSummer.setVisible(false);
		sStarSummer.setTouch(true);
		
		sStarAutumn = new ObjHoshi(0, 0, trStarAutumn, this.getVertexBufferObjectManager());
		mScene.attachChild(sStarAutumn);
		sStarAutumn.setVisible(false);
		sStarAutumn.setTouch(true);
		
		sStarWinter = new ObjHoshi(0, 0, trStarWinter, this.getVertexBufferObjectManager());
		mScene.attachChild(sStarWinter);
		sStarWinter.setVisible(false);
		sStarWinter.setTouch(true);
		
		for (int i = 0; i < sStarPool.length; i++) {
			sStarPool[i] = new SpritePool(trStar[i], this.getVertexBufferObjectManager());
		}
		
		asBackgroundSeason = new AnimatedSprite(0, 433, ttxrBackgroundSeason, this.getVertexBufferObjectManager());
		mScene.attachChild(asBackgroundSeason);
		asBackgroundSeason.setZIndex(5);
		
		asGimmicSpring = new Season(232, 512, ttxrGimmicSpring, this.getVertexBufferObjectManager());
		mScene.attachChild(asGimmicSpring);
		asGimmicSpring.setIndexSeason(0);
		asGimmicSpring.setTouch(true);
		asGimmicSpring.setZIndex(6);
		
		asGimmicSummer = new Season(362, 512, ttxrGimmicSummer, this.getVertexBufferObjectManager());
		mScene.attachChild(asGimmicSummer);
		asGimmicSummer.setIndexSeason(1);
		asGimmicSummer.setTouch(true);
		asGimmicSummer.setZIndex(6);
		
		asGimmicAutumn = new Season(492, 512, ttxrGimmicAutumn, this.getVertexBufferObjectManager());
		mScene.attachChild(asGimmicAutumn);
		asGimmicAutumn.setIndexSeason(2);
		asGimmicAutumn.setTouch(true);
		asGimmicAutumn.setZIndex(6);
		
		asGimmicWinter = new Season(622, 512, ttxrGimmicWinter, this.getVertexBufferObjectManager());
		mScene.attachChild(asGimmicWinter);
		asGimmicWinter.setIndexSeason(3);
		asGimmicWinter.setTouch(true);
		asGimmicWinter.setZIndex(6);
		
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("hoshi/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("hoshi/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "hoshi/gfx/");
		super.onCreateResources();
	}

	@Override
	public void combineGimmic3WithAction() {

	}
	
	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		initGame();
		startGame();
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		//this.onDestroy();
		resetGame();
	}

	private void initGame()	{
		Log.d(TAG, "initGame...");
		isTouchSky = false;
		isMoveStar =true;
		iSeason = 0;
		sObjLeft[1].setSound(oggSoundObjLeft[0]);
		sObjLeft[3].setSound(oggSoundObjLeft[1]);
		sObjLeft[5].setSound(oggSoundObjLeft[2]);
		sObjLeft[7].setSound(oggSoundObjLeft[3]);
		
		int j = 0;
		for (int i = 0; i < sObjCenter.length; i++) {
			if(i % 2 ==1) {
				sObjCenter[i].setSound(oggSoundObjCenter[j]);
				j++;
			}
		}
		int k = 0;
		for (int i = 0; i < sObjRight.length; i++) {
			if(i % 2 ==1) {
				sObjRight[i].setSound(oggSoundObjRight[k]);
				k++;
			}
		}
	}
	
	private void startGame() {
		long pDurations[] = new long[] {500, 500, 500};
		int pFrame[] = new int[] {0, 1, 2};
		asBackground.animate(pDurations, pFrame, -1);
		sObjLeft[iSeason].setVisible(true);
		sObjCenter[iSeason].setVisible(true);
		sObjRight[iSeason].setVisible(true);
	}
	
	private void resetGame() {
		isTouchSky = false;
		isMoveStar = true;
		asBackgroundSeason.setCurrentTileIndex(0);
		sObjLeft[2 * iSeason].setVisible(false);
		sObjLeft[2 * iSeason + 1].setTouch(true);
		sObjLeft[2 * iSeason + 1].stopSound();
		sObjLeft[2 * iSeason + 1].clearEntityModifiers();
		sObjLeft[2 * iSeason + 1].setVisible(false);
		
		sObjCenter[2 * iSeason].setVisible(false);
		sObjCenter[2 * iSeason + 1].setTouch(true);
		sObjCenter[2 * iSeason + 1].stopSound();
		sObjCenter[2 * iSeason + 1].clearEntityModifiers();
		sObjCenter[2 * iSeason + 1].setVisible(false);
		
		sObjRight[2 * iSeason].setVisible(false);
		sObjRight[2 * iSeason + 1].setTouch(true);
		sObjRight[2 * iSeason + 1].stopSound();
		sObjRight[2 * iSeason + 1].clearEntityModifiers();
		sObjRight[2 * iSeason + 1].setVisible(false);
		iSeason = 0;
		stopSound();
	}
	
	private void stopSound() {
		OGG_A77_5_STAR.stop();
		OGG_A77_1_KIRA6.stop();
		OGG_A77_3_1_1.stop();
		OGG_A77_3_2_1.stop();
		OGG_A77_3_3_1.stop();
		OGG_A77_3_4_1.stop();
		OGG_A77_2_SEIZA.stop();
		for (int i = 0; i < oggSoundObjLeft.length; i++) {
			oggSoundObjLeft[i].stop();
		}
		for (int i = 0; i < oggSoundObjCenter.length; i++) {
			oggSoundObjCenter[i].stop();
		}
		for (int i = 0; i < oggSoundObjRight.length; i++) {
			oggSoundObjRight[i].stop();
		}
	}
	
	private void touchSeason(final Season season) {
		if (season.isTouch()) {
			season.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					season.setTouch(false);
					season.setCurrentTileIndex(1);
					switch (iSeason) {
					case 0:
						OGG_A77_3_1_1.play();
						break;
					case 1:
						OGG_A77_3_2_1.play();
						break;
					case 2:
						OGG_A77_3_3_1.play();
						break;
					default:
						OGG_A77_3_4_1.play();
						break;
					}
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					season.setCurrentTileIndex(0);
					season.setTouch(true);
				}
			},
			new ScaleModifier(0.2f, 1.0f, 1.5f),
			new ScaleModifier(0.2f, 1.5f, 1.0f)));
		}
		
		if (iSeason != season.getIndexSeason()) {
			Log.d(TAG, "action");
			sObjLeft[2 * iSeason].setVisible(false);
			sObjLeft[2 * iSeason + 1].setTouch(true);
			sObjLeft[2 * iSeason + 1].stopSound();
			sObjLeft[2 * iSeason + 1].clearEntityModifiers();
			sObjLeft[2 * iSeason + 1].setVisible(false);
			
			sObjCenter[2 * iSeason].setVisible(false);
			sObjCenter[2 * iSeason + 1].setTouch(true);
			sObjCenter[2 * iSeason + 1].stopSound();
			sObjCenter[2 * iSeason + 1].clearEntityModifiers();
			sObjCenter[2 * iSeason + 1].setVisible(false);
			
			sObjRight[2 * iSeason].setVisible(false);
			sObjRight[2 * iSeason + 1].setTouch(true);
			sObjRight[2 * iSeason + 1].stopSound();
			sObjRight[2 * iSeason + 1].clearEntityModifiers();
			sObjRight[2 * iSeason + 1].setVisible(false);
			
			iSeason = season.getIndexSeason();
			asBackgroundSeason.setCurrentTileIndex(iSeason);
			
			sObjLeft[2 * iSeason].clearEntityModifiers();
			sObjLeft[2 * iSeason].setVisible(true);
			
			sObjCenter[2 * iSeason].clearEntityModifiers();
			sObjCenter[2 * iSeason].setVisible(true);
			
			sObjRight[2 * iSeason].clearEntityModifiers();
			sObjRight[2 * iSeason].setVisible(true);
		}
	}
	
	private void touchObj(final ObjHoshi mySprite) {
		if (mySprite.isTouch()) {
			mySprite.speak();
			mySprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					mySprite.setVisible(true);
					mySprite.setTouch(false);
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mySprite.setTouch(true);
					mySprite.setVisible(false);
					mySprite.setAlpha(1);
				}
			},
			new DelayModifier(1.0f),
			new AlphaModifier(2.0f, 1, 0)));
		}
	}
	
	private void touchSky(int pX, int pY) {
		if (Math.abs((pX + pY) - (pLastX + pLastY)) >= 100) {
			if (isMoveStar) {
				isMoveStar = false;
				OGG_A77_5_STAR.play();
				mScene.registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						isMoveStar = true;
					}
				}));
			}
			Sprite sStar[] = new Sprite[8];
			for (int i = 0; i < sStar.length; i++) {
				sStar[i] = sStarPool[i].obtainPoolItem();
				sStar[i].setPosition(pX, pY);
				sStar[i].setAlpha(0.7f);
				mScene.attachChild(sStar[i]);
				sStar[i].setZIndex(1);
				mScene.sortChildren();
				starMoveSky(sStar[i], pX, (int) arrPointStar[0][i], pY, (int) arrPointStar[1][i]);
			}
			pLastX = pX;
			pLastY = pY;
		}

	}

	private void starMoveSky(final Sprite starMove, int fromX, int toX, int fromY, int toY) {
		starMove.registerEntityModifier(new ParallelEntityModifier(
				new AlphaModifier(5.0f, 0.7f, 0.0f),
				new SequenceEntityModifier(new MoveModifier(2.5f, fromX, fromX
						+ toX, fromY, fromY + toY), new MoveModifier(2.5f,
						fromX + toX, fromX, fromY + toY, fromY,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								runOnUpdateThread(new Runnable() {
									public void run() {
										for (int i = 0; i < sStarPool.length; i++) {
											sStarPool[i]
													.onHandleRecycleItem(starMove);
										}
									}
								});

							}
						}))));
	}
	
	private void moveStar(final ObjHoshi mySprite, int pFromX, int pToX, int pFromY, int pToY) {
		if (mySprite.isTouch()) {
			OGG_A77_1_KIRA6.play();
			mySprite.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(1.5f, pFromX, pToX, pFromY, pToY, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							mySprite.setVisible(true);
							mySprite.setTouch(false);
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mySprite.setVisible(false);
							mySprite.setTouch(true);
						}
					})));
		}
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if(asGimmicSpring.contains(pX, pY)) {
				Log.d(TAG, "mua xuan");
				touchSeason(asGimmicSpring);
				return true;
			} else if(asGimmicSummer.contains(pX, pY)) {
				Log.d(TAG, "mua ha");
				touchSeason(asGimmicSummer);
				return true;
			} else if(asGimmicAutumn.contains(pX, pY)) {
				Log.d(TAG, "mua thu");
				touchSeason(asGimmicAutumn);
				return true;
			} else if(asGimmicWinter.contains(pX, pY)) {
				Log.d(TAG, "mua dong");
				touchSeason(asGimmicWinter);
				return true;
			} else if(checkContains(asBackgroundSeason, 0, 39, 241, 171, pX, pY)) {
				Log.d(TAG, "Trai");
				if(iSeason == 0 || iSeason == 1) {
					if(sStarWinter.isTouch()) {
						sStarSummer.setFlippedHorizontal(true);
						moveStar(sStarSummer, -20, 960, -20, 640);
					}
				} else {
					if(sStarSummer.isTouch()) {
						sStarWinter.setFlippedHorizontal(true);
						moveStar(sStarWinter, -20, 960, -20, 640);
					}
				}
			} else if(checkContains(asBackgroundSeason, 769, 22, 960, 196, pX, pY)) {
				Log.d(TAG, "Phai");
				if(iSeason == 0 || iSeason == 1) {
					if(sStarAutumn.isTouch()) {
						moveStar(sStarSpring, 960, -159, -95, 555);
					}
				} else {
					if(sStarSpring.isTouch()) {
						moveStar(sStarAutumn, 960, -159, -95, 555);
					}
				}
			}
			
			for (int i = 0; i < sObjLeft.length / 2; i++) {
				if(checkContainsPolygon(sObjLeft[2*i], idCheckPolygonLeft[i], 
						idCheckPolygonLeft[i][0].length, pX, pY) && sObjLeft[2*i].isVisible()) {
					touchObj(sObjLeft[2*i + 1]);
					return true;
				}
			}
			
			for (int i = 0; i < sObjCenter.length / 2; i++) {
				if(checkContainsPolygon(sObjCenter[2*i], idCheckPolygonCenter[i], 
						idCheckPolygonCenter[i][0].length, pX, pY) && sObjCenter[2*i].isVisible()) {
					touchObj(sObjCenter[2*i + 1]);
					return true;
				}
			}
			
			for (int i = 0; i < sObjRight.length / 2; i++) {
				if(checkContainsPolygon(sObjRight[2*i], idCheckPolygonRight[i], 
						idCheckPolygonRight[i][0].length, pX, pY) && sObjRight[2*i].isVisible()) {
					touchObj(sObjRight[2*i + 1]);
					return true;
				}
			}
			
			if(0 < pY && pY < 440) {
				isTouchSky = true;
				return true;
			}
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if(isTouchSky) {
				touchSky(pX, pY);
			}
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if(isTouchSky) {
				isTouchSky = false;
			}
		}
		
		return false;
	}
	
	private class Season extends AnimatedSprite {
		private int iIndexSeason;
		private boolean isTouch;
		
		public Season(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
		}

		public int getIndexSeason() {
			return iIndexSeason;
		}

		public void setIndexSeason(int iIndexSeason) {
			this.iIndexSeason = iIndexSeason;
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}
	}
	
	private class ObjHoshi extends Sprite {
		private boolean isTouch;
		private Sound mp3Sound;
		
		public ObjHoshi(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}
		
		public void speak() {
			if(mp3Sound != null) {
				OGG_A77_2_SEIZA.play();
				mp3Sound.play();
			}
		}
		
		public void stopSound() {
			if(mp3Sound != null) {
				OGG_A77_2_SEIZA.stop();
				mp3Sound.stop();
			}
		}
		
		public void setSound(Sound mp3Sound) {
			this.mp3Sound = mp3Sound;
		}
	}
}
