package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TizukokkiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class Vol3Tizukokki extends BaseGameFragment implements IOnSceneTouchListener {
	
	private static final String TAG = "LOG_TIZUKOKKI";

	private TexturePack tpTizukokki1, tpTizukokki2, tpTizukokki3, tpTizukokki4, tpTizukokki5, tpTizukokki6, tpTizukokki7,
						tpTizukokki8, tpTizukokki9, tpTizukokki10;
	private TexturePackTextureRegionLibrary tpLibTizukokki1, tpLibTizukokki2, tpLibTizukokki3, tpLibTizukokki4, tpLibTizukokki5,
						tpLibTizukokki6, tpLibTizukokki7, tpLibTizukokki8, tpLibTizukokki9, tpLibTizukokki10;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion trBackground, trInfor, trInforDetail, trUrlFree, trUrlNoFree, trUrlWeb;
	private TiledTextureRegion ttrClosed, ttrSpeaker;
	private Sprite sInfor, sInforDetail, sUrlFree, sUrlNoFree, sUrlWeb;
	private AnimatedSprite asClosedInfo;
	
	private ITextureRegion[] trCountryLabel = new ITextureRegion[14];
	private Sprite[] sCountryLabel = new Sprite[14];
	
	private ITextureRegion[] trCountryFlag = new ITextureRegion[16];
	private Sprite[] sCountryFlag = new Sprite[16];
	private int[][] idPostionCountryFlag = {
			{640, 502}, {673, 407}, {528, 458}, {415, 387}, {548, 353}, {319, 150}, {477, 267}, {596, 242},
			{546, 47}, {754, 243}, {489, 151}, {409, 292}, {285, 405}, {225, 327}, {60, 333}, {67, 193}
	};
	
	private ITextureRegion[] trCountryDetail = new ITextureRegion[16];
	private ObjTizukokkiDetail[] sCountryDetail = new ObjTizukokkiDetail[16];
	
	private ITiledTextureRegion[] ttrHuman = new ITiledTextureRegion[14];
	private AniTizukokki[] atHuman = new AniTizukokki[14];
	
	private boolean isTouchCountryFlag, isTouchCountryDetail, isTouchInfor, isTouchClosedInfo, isTouchSpeakerHuman;
	private boolean[] isTouchFlag = new boolean[16];
	private int iIdCountryDetail;
	
	private Sound OGG_A83_KACHI;
	private Sound[] soundGreet = new Sound[14];
	private Sound[] soundCountry = new Sound[16];
	
	private int[][] iScaleCenter = {
			{0, 84}, {0, 84}, {0, 84}, {104, 84}, {0, 84}, {0, 84}, {0, 84}, {104, 84}, 
			{104, 84}, {0, 84}, {0, 84}, {0, 84}, {104, 84}, {0, 84}, {104, 84}, {104, 84}
	};
	
	@Override
	protected void loadKaraokeResources() {
		tpTizukokki1 = mTexturePackLoaderFromSource.load("tizukokki1.xml");
		tpTizukokki1.loadTexture();
		tpLibTizukokki1 = tpTizukokki1.getTexturePackTextureRegionLibrary();
		
		tpTizukokki2 = mTexturePackLoaderFromSource.load("tizukokki2.xml");
		tpTizukokki2.loadTexture();
		tpLibTizukokki2 = tpTizukokki2.getTexturePackTextureRegionLibrary();
		
		tpTizukokki3 = mTexturePackLoaderFromSource.load("tizukokki3.xml");
		tpTizukokki3.loadTexture();
		tpLibTizukokki3 = tpTizukokki3.getTexturePackTextureRegionLibrary();
		
		tpTizukokki4 = mTexturePackLoaderFromSource.load("tizukokki4.xml");
		tpTizukokki4.loadTexture();
		tpLibTizukokki4 = tpTizukokki4.getTexturePackTextureRegionLibrary();
		
		tpTizukokki5 = mTexturePackLoaderFromSource.load("tizukokki5.xml");
		tpTizukokki5.loadTexture();
		tpLibTizukokki5 = tpTizukokki5.getTexturePackTextureRegionLibrary();
		
		tpTizukokki6 = mTexturePackLoaderFromSource.load("tizukokki6.xml");
		tpTizukokki6.loadTexture();
		tpLibTizukokki6 = tpTizukokki6.getTexturePackTextureRegionLibrary();
		
		tpTizukokki7 = mTexturePackLoaderFromSource.load("tizukokki7.xml");
		tpTizukokki7.loadTexture();
		tpLibTizukokki7 = tpTizukokki7.getTexturePackTextureRegionLibrary();
		
		tpTizukokki8 = mTexturePackLoaderFromSource.load("tizukokki8.xml");
		tpTizukokki8.loadTexture();
		tpLibTizukokki8 = tpTizukokki8.getTexturePackTextureRegionLibrary();
		
		tpTizukokki9 = mTexturePackLoaderFromSource.load("tizukokki9.xml");
		tpTizukokki9.loadTexture();
		tpLibTizukokki9 = tpTizukokki9.getTexturePackTextureRegionLibrary();
		
		tpTizukokki10 = mTexturePackLoaderFromSource.load("tizukokki10.xml");
		tpTizukokki10.loadTexture();
		tpLibTizukokki10 = tpTizukokki10.getTexturePackTextureRegionLibrary();
		
		this.trBackground = tpLibTizukokki1.get(Vol3TizukokkiResource.A83_1_1_IPHONE_ID);
		
		for (int i = 0; i < trCountryFlag.length; i++) {
			trCountryFlag[i] = tpLibTizukokki1.get(Vol3TizukokkiResource.idCountryFlag[i]);
		}
		
		trCountryDetail[0] = tpLibTizukokki2.get(Vol3TizukokkiResource.A83_2_1_1_IPHONE_ID);
		trCountryDetail[1] = tpLibTizukokki2.get(Vol3TizukokkiResource.A83_2_1_2_IPHONE_ID);
		trCountryDetail[2] = tpLibTizukokki3.get(Vol3TizukokkiResource.A83_2_1_3_IPHONE_ID);
		trCountryDetail[3] = tpLibTizukokki3.get(Vol3TizukokkiResource.A83_2_1_4_IPHONE_ID);
		trCountryDetail[4] = tpLibTizukokki4.get(Vol3TizukokkiResource.A83_2_1_5_IPHONE_ID);
		trCountryDetail[5] = tpLibTizukokki4.get(Vol3TizukokkiResource.A83_2_1_6_IPHONE_ID);
		trCountryDetail[6] = tpLibTizukokki5.get(Vol3TizukokkiResource.A83_2_1_7_IPHONE_ID);
		trCountryDetail[7] = tpLibTizukokki5.get(Vol3TizukokkiResource.A83_2_1_8_IPHONE_ID);
		trCountryDetail[8] = tpLibTizukokki6.get(Vol3TizukokkiResource.A83_2_1_9_IPHONE_ID);
		trCountryDetail[9] = tpLibTizukokki6.get(Vol3TizukokkiResource.A83_2_1_10_IPHONE_ID);
		trCountryDetail[10] = tpLibTizukokki7.get(Vol3TizukokkiResource.A83_2_1_11_IPHONE_ID);
		trCountryDetail[11] = tpLibTizukokki7.get(Vol3TizukokkiResource.A83_2_1_12_IPHONE_ID);
		trCountryDetail[12] = tpLibTizukokki8.get(Vol3TizukokkiResource.A83_2_1_13_IPHONE_ID);
		trCountryDetail[13] = tpLibTizukokki8.get(Vol3TizukokkiResource.A83_2_1_14_IPHONE_ID);
		trCountryDetail[14] = tpLibTizukokki9.get(Vol3TizukokkiResource.A83_2_1_15_IPHONE_ID);
		trCountryDetail[15] = tpLibTizukokki9.get(Vol3TizukokkiResource.A83_2_1_16_IPHONE_ID);
		
		ttrClosed = getTiledTextureFromPacker(tpTizukokki1,
				Vol3TizukokkiResource.A83_3_2_IPHONE_ID,
				Vol3TizukokkiResource.A83_3_2_1_IPHONE1_ID);
		ttrSpeaker = getTiledTextureFromPacker(tpTizukokki1, 
				Vol3TizukokkiResource.A83_3_1_IPHONE_ID,
				Vol3TizukokkiResource.A83_3_1_2_IPHONE_ID);
		
		for (int i = 0; i < ttrHuman.length; i++) {
			ttrHuman[i] = getTiledTextureFromPacker(tpTizukokki1, Vol3TizukokkiResource.idHuman[i]);
		}
		
		for (int i = 0; i < trCountryLabel.length; i++) {
			trCountryLabel[i] = tpLibTizukokki1.get(Vol3TizukokkiResource.idCountryLabel[i]);
		}
		
		//infor
		trInfor = tpLibTizukokki10.get(Vol3TizukokkiResource.A83_4_1_IPHONE_ID);
		trInforDetail = tpLibTizukokki10.get(Vol3TizukokkiResource.A83_4_2_1_IPHONE_ID);
		trUrlFree = tpLibTizukokki10.get(Vol3TizukokkiResource.A83_4_2_4_IPHONE_ID);
		trUrlNoFree = tpLibTizukokki10.get(Vol3TizukokkiResource.A83_4_2_3_IPHONE_ID);
		trUrlWeb = tpLibTizukokki10.get(Vol3TizukokkiResource.A83_4_2_2_IPHONE_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		OGG_A83_KACHI = loadSoundResourceFromSD(Vol3TizukokkiResource.A83_KACHI);
		for (int i = 0; i < soundGreet.length; i++) {
			soundGreet[i] = loadSoundResourceFromSD(Vol3TizukokkiResource.idGreet[i]);
		}
		for (int i = 0; i < soundCountry.length; i++) {
			soundCountry[i] = loadSoundResourceFromSD(Vol3TizukokkiResource.idCountry[i]);
		}
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.trBackground, this.getVertexBufferObjectManager())));
		
		for (int i = 0; i < sCountryFlag.length; i++) {
			sCountryFlag[i] = new Sprite(idPostionCountryFlag[i][0], idPostionCountryFlag[i][1],
					trCountryFlag[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sCountryFlag[i]);
		}
		
		for (int i = 0; i < sCountryDetail.length; i++) {
			sCountryDetail[i] = new ObjTizukokkiDetail(103, 54, trCountryDetail[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sCountryDetail[i]);
			sCountryDetail[i].setVisible(false);
			sCountryDetail[i].setIdDetail(i);
		}
		
		for (int i = 0; i < atHuman.length; i++) {
			atHuman[i] = new AniTizukokki(90, 109, ttrHuman[i], this.getVertexBufferObjectManager());
			atHuman[i].setId(i);
			if(i < 5) {
				sCountryDetail[i].setAtHuman(atHuman[i]);
			} else {
				sCountryDetail[i + 1].setAtHuman(atHuman[i]);
			}
		}
		
		for (int i = 0; i < sCountryLabel.length; i++) {
			sCountryLabel[i] = new Sprite(299, 116, trCountryLabel[i], this.getVertexBufferObjectManager());
			if(i < 5) {
				sCountryDetail[i].setLabel(sCountryLabel[i]);
			} else {
				sCountryDetail[i + 1].setLabel(sCountryLabel[i]);
			}
			sCountryLabel[i].setVisible(false);
		}
		
		//Infor
		sInfor = new Sprite(60, 549, trInfor, this.getVertexBufferObjectManager());
		mScene.attachChild(sInfor);
		
		sInforDetail = new Sprite(104, 43, trInforDetail, this.getVertexBufferObjectManager());
		mScene.attachChild(sInforDetail);
		sInforDetail.setVisible(false);
		sInforDetail.setZIndex(10);
		
		sUrlFree = new Sprite(379, 343, trUrlFree, this.getVertexBufferObjectManager());
		sInforDetail.attachChild(sUrlFree);
		
		sUrlNoFree = new Sprite(14, 343, trUrlNoFree, this.getVertexBufferObjectManager());
		sInforDetail.attachChild(sUrlNoFree);
		
		sUrlWeb = new Sprite(192, 471, trUrlWeb, this.getVertexBufferObjectManager());
		sInforDetail.attachChild(sUrlWeb);
		
//		sClosedInfo = new Sprite(259, 533, trClosedInfo, this.getVertexBufferObjectManager());
//		sInforDetail.attachChild(sClosedInfo);
		
		asClosedInfo = new AnimatedSprite(259, 533, ttrClosed, this.getVertexBufferObjectManager());
		sInforDetail.attachChild(asClosedInfo);
		
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("tizukokki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tizukokki/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "tizukokki/gfx/");
		super.onCreateResources();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		initGame();
		initBoolean();
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		soundStop();
		resetGame();
		initBoolean();
	}

	private void initGame() {
		isTouchCountryFlag = true;
		isTouchCountryDetail = false;
		iIdCountryDetail = 0;
		isTouchInfor = true;
		isTouchClosedInfo = false;
		isTouchSpeakerHuman = false;
		for (int i = 0; i < atHuman.length; i++) {
			Log.d(TAG, "Id: " + atHuman[i].getId());
		}
	}
	
	private void initBoolean() {
		for (int i = 0; i < isTouchFlag.length; i++) {
			isTouchFlag[i] = false;
		}
	}
	
	private void resetGame() {
		isTouchCountryFlag = true;
		isTouchCountryDetail = false;
		sCountryDetail[iIdCountryDetail].reset();
		asClosedInfo.setCurrentTileIndex(0);
		sInforDetail.setVisible(false);
		sInfor.setVisible(true);
		isTouchSpeakerHuman = false;
	}
	
	private void soundStop() {
		OGG_A83_KACHI.stop();
		for (int i = 0; i < soundGreet.length; i++) {
			soundGreet[i].stop();
		}
		for (int i = 0; i < soundCountry.length; i++) {
			soundCountry[i].stop();
		}
	}
	
	private void touchCountryFlag(final ObjTizukokkiDetail sCountryDetail, 
			final Sprite sCountryFlag, int iScaleCenterX, int iScaleCenterY, final Sound mp3Contry) {
		if(isTouchCountryFlag) {
			isTouchCountryFlag = false;
			isTouchInfor = false;
			OGG_A83_KACHI.play();
			iIdCountryDetail = sCountryDetail.getIdDetail();
			sCountryFlag.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					sCountryFlag.setZIndex(1);
					mScene.sortChildren();
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mp3Contry.play();
					sCountryFlag.setZIndex(0);
					sCountryDetail.setZIndex(2);
					mScene.sortChildren();	
					sCountryFlag.setScale(1.0f);
					sCountryDetail.registerEntityModifier(new ParallelEntityModifier(new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							sCountryDetail.setVisible(true);
							if(sCountryDetail.getAtHuman() != null) {
								sCountryDetail.getAtHuman().setTouch(true);
								sCountryDetail.getAtHuman().stopAnimation();
								sCountryDetail.getAtHuman().setCurrentTileIndex(0);
							}
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchCountryDetail = true;
						}
					}, 
					new ScaleModifier(0.4f, 0.3f, 1.0f)));
				}
			}, 
					new ScaleAtModifier(0.1f, 1.0f, 1.3f, iScaleCenterX, iScaleCenterY),
					new ScaleAtModifier(0.1f, 1.3f, 1.2f, iScaleCenterX, iScaleCenterY),
					new ScaleAtModifier(0.1f, 1.2f, 1.3f, iScaleCenterX, iScaleCenterY)));
		}
	}
	
	private void closedCountryDetail(final ObjTizukokkiDetail sCountryDetail) {
		if(isTouchCountryDetail) {
			isTouchCountryDetail = false;
			sCountryDetail.getClosed().registerEntityModifier(new DelayModifier(0.25f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					sCountryDetail.getClosed().setCurrentTileIndex(1);
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sCountryDetail.setVisible(false);
					sCountryDetail.getClosed().setCurrentTileIndex(0);
					sCountryDetail.sSpeaker.setCurrentTileIndex(0);
					if(sCountryDetail.getAtHuman() != null) {
						sCountryDetail.getAtHuman().setTouch(false);
						sCountryDetail.getAtHuman().stopAnimation();
						sCountryDetail.getAtHuman().setCurrentTileIndex(0);
						sCountryDetail.getAtHuman().getSpeaker().setCurrentTileIndex(0);
					}
					if (sCountryDetail.getLabel() != null) {
						sCountryDetail.getLabel().clearEntityModifiers();
						sCountryDetail.getLabel().setVisible(false);
					}
					isTouchCountryFlag = true;
					isTouchInfor = true;
				}
			}));
			OGG_A83_KACHI.play();
		}
	}
	
	private void touchHuman(final AniTizukokki atHuman, final Sprite sLabel, final Sound soundGreet) {
		if(isTouchCountryDetail) {
			if (isTouchSpeakerHuman) {
				atHuman.getSpeaker().setCurrentTileIndex(1);
			} else {
				atHuman.getSpeaker().setCurrentTileIndex(0);
			}
			soundGreet.play();
			sLabel.setVisible(true);
			sLabel.registerEntityModifier(new SequenceEntityModifier(
					new ScaleAtModifier(0.3f, 0.3f, 1.0f, 0, sLabel.getHeight() / 2),
					new DelayModifier(0.3f)));
			
			long pDurations[]= new long[] {400, 400};
			int pFrame[] = new int[] {0, 1};
			if(atHuman.getId() == 13) {
				pFrame = new int[] {1, 2};
			} else {
				pFrame = new int[] {0, 1};
			}
			atHuman.animate(pDurations, pFrame, 1, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					atHuman.setTouch(true);
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					atHuman.setCurrentTileIndex(0);
					//atHuman.setTouch(true);
					sLabel.setVisible(false);
					if (isTouchSpeakerHuman) {
						isTouchSpeakerHuman = false;
						atHuman.getSpeaker().setCurrentTileIndex(0);
					}
				}
			});
		}
	}
	
	private void touchTitleCountry(final ObjTizukokkiDetail sCountryDetail) {
		sCountryDetail.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				sCountryDetail.sSpeaker.setCurrentTileIndex(1);
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				sCountryDetail.sSpeaker.setCurrentTileIndex(0);
			}
		}));
	}
	
	private void touchInfor() {
		if(isTouchInfor) {
			isTouchCountryFlag = false;
			isTouchInfor = false;
			OGG_A83_KACHI.play();
			sInforDetail.setVisible(true);
			sInforDetail.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					sInfor.setVisible(false);
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					isTouchClosedInfo = true;
				}
			}, new ScaleModifier(0.4f, 0.3f, 1.0f)));
		}
	}
	
	private void openUrl(final String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		Vol3Tizukokki.this.startActivity(intent);
	}
	
	private void closedInfor() {
		if (isTouchClosedInfo) {
			asClosedInfo.setCurrentTileIndex(1);
			mScene.registerUpdateHandler(new TimerHandler(0.25f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					// TODO Auto-generated method stub
					asClosedInfo.setCurrentTileIndex(0);
					isTouchClosedInfo = false;
					isTouchCountryFlag = true;
					isTouchInfor = true;
					sInforDetail.setVisible(false);
					sInfor.setVisible(true);
				}
			}));
			OGG_A83_KACHI.play();
		}
	}
	
	int x1, y1, x2, y2;
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			for (int i = 0; i < sCountryFlag.length; i++) {
				if (sCountryFlag[i].contains(pX, pY)) {
					isTouchFlag[i] = true;
					//touchCountryFlag(sCountryDetail[i], sCountryFlag[i], iScaleCenter[i][0], iScaleCenter[i][1], soundCountry[i]);
				}
			}
			
			for (int i = 0; i < sCountryDetail.length; i++) {
				if (sCountryDetail[i].getClosed().contains(pX, pY)
						&& sCountryDetail[i].isVisible()) {
					closedCountryDetail(sCountryDetail[i]);
				}
			}
			
			for (int i = 0; i < atHuman.length; i++) {
				if ((atHuman[i].contains(pX, pY)
						|| atHuman[i].getSpeaker().contains(pX, pY))
						&& atHuman[i].isTouch()) {
					if (atHuman[i].getSpeaker().contains(pX, pY)) {
						isTouchSpeakerHuman = true;
					} else {
						isTouchSpeakerHuman = false;
					}
					touchHuman(atHuman[i], sCountryLabel[i], soundGreet[i]);
				}
			}
			
			for (int i = 0; i < sCountryDetail.length; i++) {
				if(i == 5 || i == 15) {
					x1 = 217; y1 = 106;
					x2 = 537; y2 = 320;
				} else {
					x1 = 320; y1 = 106;
					x2 = 638; y2 = 320;
				}
				if((checkContains(sCountryDetail[i], 71, 23, 681, 72, pX, pY)
						|| checkContains(sCountryDetail[i], x1, y1, x2, y2, pX, pY))
						&& sCountryDetail[i].isVisible()) {
					Log.d(TAG, "id: " + i);
					if(isTouchCountryDetail) {
						if (sCountryDetail[i].sSpeaker.contains(pX, pY)) {
							touchTitleCountry(sCountryDetail[i]);
						} else {
							sCountryDetail[i].sSpeaker.setCurrentTileIndex(0);
						}
						soundCountry[i].play();
					}
				}
			}
			
			if (sInfor.contains(pX, pY)) {
				touchInfor();
			}
			
			if(sUrlFree.contains(pX, pY) && sInforDetail.isVisible()) {
				openUrl("https://play.google.com/store/apps/details?id=air.jp.co.xing.chizukokkifree");
			}
			
			if (sUrlNoFree.contains(pX, pY) && sInforDetail.isVisible()) {
				 openUrl("https://play.google.com/store/apps/details?id=air.jp.co.xing.chizukokki");
			}
			
			if(sUrlWeb.contains(pX, pY) && sInforDetail.isVisible()) {
				openUrl("http://sumahomama.com/geochallenge/");
			}
			
			if(asClosedInfo.contains(pX, pY)) {
				closedInfor();
			}
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			
		}
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			for (int i = 0; i < isTouchFlag.length; i++) {
				if(isTouchFlag[i] && sCountryFlag[i].contains(pX, pY)) {
					isTouchFlag[i] = false;
					touchCountryFlag(sCountryDetail[i], sCountryFlag[i], iScaleCenter[i][0], iScaleCenter[i][1], soundCountry[i]);
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void combineGimmic3WithAction() {

	}

	private class ObjTizukokkiDetail extends Sprite {
		private int iIdDetail;
		private AnimatedSprite sClosed;
		private AnimatedSprite sSpeaker;
		private AniTizukokki atHuman;
		private Sprite sLabel;
		
		public ObjTizukokkiDetail(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, vertexBufferObjectManager);
			
			sClosed = new AnimatedSprite(259, 520, ttrClosed, vertexBufferObjectManager);
			this.attachChild(sClosed);
			
			sSpeaker = new AnimatedSprite(647, 32, ttrSpeaker, vertexBufferObjectManager);
			this.attachChild(sSpeaker);
		}

		public int getIdDetail() {
			return iIdDetail;
		}

		public void setIdDetail(int iIdDetail) {
			this.iIdDetail = iIdDetail;
		}

		public AnimatedSprite getClosed() {
			return sClosed;
		}

		@SuppressWarnings("unused")
		public void setClosed(AnimatedSprite sClosed) {
			this.sClosed = sClosed;
		}

		public AniTizukokki getAtHuman() {
			return atHuman;
		}

		public void setAtHuman(AniTizukokki atHuman) {
			this.attachChild(atHuman);
			this.atHuman = atHuman;
		}
		
		public Sprite getLabel() {
			return sLabel;
		}

		public void setLabel(Sprite sLabel) {
			this.sLabel = sLabel;
			this.attachChild(sLabel);
		}
		
		public void reset() {
			this.clearEntityModifiers();
			this.clearUpdateHandlers();
			this.setPosition(this.getmXFirst(), this.getmYFirst());
			this.setVisible(false);
			this.setScale(1);
			sSpeaker.setCurrentTileIndex(0);
			if(this.atHuman != null) {
				this.atHuman.getSpeaker().setCurrentTileIndex(0);
				this.atHuman.setTouch(false);
			}
		}
	}
	
	private class AniTizukokki extends AnimatedSprite {
		private boolean isTouch;
		private int iId;
		private AnimatedSprite sSpeaker;
		
		public AniTizukokki(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
			
			sSpeaker = new AnimatedSprite(175, 10, ttrSpeaker, vertexBufferObjectManager);
			this.attachChild(sSpeaker);
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}

		public int getId() {
			return iId;
		}

		public void setId(int iId) {
			this.iId = iId;
		}

		public AnimatedSprite getSpeaker() {
			return sSpeaker;
		}

		@SuppressWarnings("unused")
		public void setSpeaker(AnimatedSprite sSpeaker) {
			this.sSpeaker = sSpeaker;
		}
	}
}
