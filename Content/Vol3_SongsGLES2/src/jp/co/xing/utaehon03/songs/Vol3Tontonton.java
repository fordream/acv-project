package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TontontonResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Tontonton extends BaseGameActivity implements IOnSceneTouchListener {
	
	private static final String TAG = "LOG_VOL3TONTONTON";
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mBacgroundATp;
	private TexturePack mBacgroundBTp;
	private TexturePack mDefaultTp;
	private TexturePack mRedBlackTp;
	
	private ITiledTextureRegion mBacgroundATiledTexture;
	private ITiledTextureRegion mBacgroundBTiledTexture;
	private ITiledTextureRegion mFaceBigTiledTexture;
	private ITiledTextureRegion mFaceSmallTiledTexture;
	private ITiledTextureRegion mRedBlackFaceBigTiledTexture;
	private ITiledTextureRegion mRedBlackFaceSmallTiledTexture;
	
	private ITextureRegion mEyeBigTexture;
	private ITextureRegion mEyeSmallTexture;
	private ITextureRegion mBlankTexture;
	private ITextureRegion mFlashTexture;
	
	private AnimatedSprite mBackgroundA;
	private AnimatedSprite mBackgroundB;
	private Sprite mHikariSprite;
	
	private AmazingFace mAmazingFace[] = new AmazingFace[12];
	
	private GimicSprite mGimicSprite[] = new GimicSprite[3];
	
	private Sound A2_1_3_KAOTOUCH, A2_2_1_KAOMODORU, A2_4_HAWAI, A2_5_KAPON, A2_6_UTYU, A2_8_HAIKEIHENAK, A2_ZENINKIERU;
	
	private int BACKGOUND_NEXT = -1;
	private boolean isRUNFIRSTBG = true;
	private boolean isFaceTouch = true;
	private boolean isMoving = true;
	
	private IUpdateHandler updateMove;
	private TimerHandler mTimerHandler;
	private RanDomNoRepeat mRandom;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("tontonton/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tontonton/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets(), "tontonton/gfx/");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		mBacgroundATp = mTexturePackLoaderFromSource.load("backgroundA.xml");
		mBacgroundBTp = mTexturePackLoaderFromSource.load("backgroundB.xml");
		mDefaultTp = mTexturePackLoaderFromSource.load("default.xml");
		mRedBlackTp = mTexturePackLoaderFromSource.load("red_black.xml");
		mBacgroundATp.loadTexture();
		mBacgroundBTp.loadTexture();
		mDefaultTp.loadTexture();
		mRedBlackTp.loadTexture();
		
		mBacgroundATiledTexture = getTiledTextureFromPacker(mBacgroundATp,
				Vol3TontontonResource.A2_8_1_IPHONE_HAIKEI_ID,Vol3TontontonResource.A2_8_2_IPHONE_HAIKEI_ID,
				Vol3TontontonResource.A2_8_3_IPHONE_HAIKEI_ID);
		
		mBacgroundBTiledTexture = getTiledTextureFromPacker(mBacgroundBTp,
				Vol3TontontonResource.A2_8_4_IPHONE_HAIKEI_ID,Vol3TontontonResource.A2_8_5_IPHONE_HAIKEI_ID,
				Vol3TontontonResource.A2_8_6_IPHONE_HAIKEI_ID);
		
		mFaceBigTiledTexture = getTiledTextureFromPacker(mDefaultTp,
				Vol3TontontonResource.A2_2_1_IPHONE_DEFAULT_ID,Vol3TontontonResource.A2_2_2_IPHONE_DEFAULT_ID,
				Vol3TontontonResource.A2_2_3_IPHONE_DEFAULT_ID,Vol3TontontonResource.A2_2_4_IPHONE_DEFAULT_ID, // DEFAULT
				Vol3TontontonResource.A2_4_1_IPHONE_MEGANE_ID,Vol3TontontonResource.A2_4_2_IPHONE_MEGANE_ID,
				Vol3TontontonResource.A2_4_3_IPHONE_MEGANE_ID,Vol3TontontonResource.A2_4_4_IPHONE_MEGANE_ID, // MEGANE
				Vol3TontontonResource.A2_5_1_IPHONE_HIGE_ID,Vol3TontontonResource.A2_5_2_IPHONE_HIGE_ID,
				Vol3TontontonResource.A2_5_3_IPHONE_HIGE_ID,Vol3TontontonResource.A2_5_4_IPHONE_HIGE_ID, // HIGE
				Vol3TontontonResource.A2_6_1_IPHONE_KOBU_ID,Vol3TontontonResource.A2_6_2_IPHONE_KOBU_ID,
				Vol3TontontonResource.A2_6_3_IPHONE_KOBU_ID,Vol3TontontonResource.A2_6_4_IPHONE_KOBU_ID // KOBU
				);
		mFaceSmallTiledTexture = getTiledTextureFromPacker(mDefaultTp,
				Vol3TontontonResource.A2_2_1_IPHONE_DEFAULT_TH_ID,Vol3TontontonResource.A2_2_2_IPHONE_DEFAULT_TH_ID,
				Vol3TontontonResource.A2_2_3_IPHONE_DEFAULT_TH_ID,Vol3TontontonResource.A2_2_4_IPHONE_DEFAULT_TH_ID, // DEFAULT
				Vol3TontontonResource.A2_4_1_IPHONE_MEGANE_TH_ID,Vol3TontontonResource.A2_4_2_IPHONE_MEGANE_TH_ID,
				Vol3TontontonResource.A2_4_3_IPHONE_MEGANE_TH_ID,Vol3TontontonResource.A2_4_4_IPHONE_MEGANE_TH_ID, // MEGANE
				Vol3TontontonResource.A2_5_1_IPHONE_HIGE_TH_ID,Vol3TontontonResource.A2_5_2_IPHONE_HIGE_TH_ID,
				Vol3TontontonResource.A2_5_3_IPHONE_HIGE_TH_ID,Vol3TontontonResource.A2_5_4_IPHONE_HIGE_TH_ID, // HIGE
				Vol3TontontonResource.A2_6_1_IPHONE_KOBU_TH_ID,Vol3TontontonResource.A2_6_2_IPHONE_KOBU_TH_ID,
				Vol3TontontonResource.A2_6_3_IPHONE_KOBU_TH_ID,Vol3TontontonResource.A2_6_4_IPHONE_KOBU_TH_ID // KOBU
				);
		
		mRedBlackFaceBigTiledTexture = getTiledTextureFromPacker(mRedBlackTp, 
				Vol3TontontonResource.A2_1_1_IPHONE_TENGU_ID, Vol3TontontonResource.A2_1_2_IPHONE_TENGU_ID, 
				Vol3TontontonResource.A2_1_3_IPHONE_TENGU_ID, Vol3TontontonResource.A2_1_4_IPHONE_TENGU_ID, // TENGU
				Vol3TontontonResource.A2_3_1_IPHONE_AFRO_ID, Vol3TontontonResource.A2_3_2_IPHONE_AFRO_ID, 
				Vol3TontontonResource.A2_3_3_IPHONE_AFRO_ID, Vol3TontontonResource.A2_3_4_IPHONE_AFRO_ID  // AFRO
				);
		
		mRedBlackFaceSmallTiledTexture = getTiledTextureFromPacker(mRedBlackTp, 
				Vol3TontontonResource.A2_1_1_IPHONE_TENGU_TH_ID, Vol3TontontonResource.A2_1_2_IPHONE_TENGU_TH_ID, 
				Vol3TontontonResource.A2_1_3_IPHONE_TENGU_TH_ID, Vol3TontontonResource.A2_1_4_IPHONE_TENGU_TH_ID, // TENGU
				Vol3TontontonResource.A2_3_1_IPHONE_AFRO_TH_ID, Vol3TontontonResource.A2_3_2_IPHONE_AFRO_TH_ID, 
				Vol3TontontonResource.A2_3_3_IPHONE_AFRO_TH_ID, Vol3TontontonResource.A2_3_4_IPHONE_AFRO_TH_ID  // AFRO
				);
		
		mEyeBigTexture = mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.A2_1_IPHONE_EYE_ID);
		mEyeSmallTexture = mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.A2_1_IPHONE_EYE_TH_ID);
		
		final BitmapTextureAtlas pAtlas = new BitmapTextureAtlas(getTextureManager(), 2, 2, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBlankTexture = new TextureRegion(pAtlas , 2, 2, 2, 2);
		
		mFlashTexture = mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.A2_7_1_IPHONE_HIKARI_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		A2_1_3_KAOTOUCH = loadSoundResourceFromSD(Vol3TontontonResource.A2_1_3_KAOTOUCH);
		A2_2_1_KAOMODORU = loadSoundResourceFromSD(Vol3TontontonResource.A2_2_1_KAOMODORU);
		A2_4_HAWAI = loadSoundResourceFromSD(Vol3TontontonResource.A2_4_HAWAI);
		A2_5_KAPON = loadSoundResourceFromSD(Vol3TontontonResource.A2_5_KAPON);
		A2_6_UTYU = loadSoundResourceFromSD(Vol3TontontonResource.A2_6_UTYU);
		A2_8_HAIKEIHENAK = loadSoundResourceFromSD(Vol3TontontonResource.A2_8_HAIKEIHENAK);
		A2_ZENINKIERU = loadSoundResourceFromSD(Vol3TontontonResource.A2_ZENINKIERU);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		
		// Background
		mBackgroundA = new AnimatedSprite(0, 0, mBacgroundATiledTexture, getVertexBufferObjectManager());
		mBackgroundB = new AnimatedSprite(0, 0, mBacgroundBTiledTexture, getVertexBufferObjectManager());
		mScene.attachChild(mBackgroundA);
		mScene.attachChild(mBackgroundB);
		mBackgroundA.setAlpha(0.0f);
		mBackgroundB.setAlpha(0.0f);
		
		final ITextureRegion mTextureHikari = mRedBlackTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.A2_7_2_IPHONE_HIKARI_ID);
		mHikariSprite = new Sprite(CAMERA_WIDTH /2 - mTextureHikari.getWidth()/2, CAMERA_HEIGHT/2 - mTextureHikari.getHeight()/2, mTextureHikari, getVertexBufferObjectManager());
		this.mScene.attachChild(mHikariSprite);
		
		float X_BIG = 16;
		float Y_BIG = -76;
		
		float X_SMALL = 90.5f;
		float Y_SMALL = 340;
		for(int i = 0; i < mAmazingFace.length; i++){
			if(i%2 ==0){ // Big Face
				Log.i(TAG, "X_BIG:" + X_BIG );
				mAmazingFace[i] = new AmazingFace(
						AmazingFace.TYPE_BIGFACE, X_BIG, Y_BIG,
						mFaceBigTiledTexture, mRedBlackFaceBigTiledTexture, 
						mEyeBigTexture, getVertexBufferObjectManager());
				X_BIG = X_BIG +16 + 253;
				if(Y_BIG == -76){
					Y_BIG = 214;
				}else{
					Y_BIG = -76;
				}
			}else{ // Small Face
				Log.i(TAG, "X_SMALL: " + X_SMALL);
				mAmazingFace[i] = new AmazingFace(
						AmazingFace.TYPE_SMALLFACE, X_SMALL, Y_SMALL,
						mFaceSmallTiledTexture, mRedBlackFaceSmallTiledTexture, 
						mEyeSmallTexture, getVertexBufferObjectManager());
				X_SMALL  = X_SMALL  + 104 + 181 - 16;
				if(Y_SMALL == 340){
					Y_SMALL = 50;
				}else{
					Y_SMALL = 340;
				}
				
			}
			mScene.attachChild(mAmazingFace[i]);
			mScene.registerTouchArea(mAmazingFace[i]);
			mRandom = new RanDomNoRepeat();
			mRandom.setLenghNoRepeat(6);
		}
		
		
		
		// add Gimic
		mGimicSprite[0] = new GimicSprite(244, 
				mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.IMAGE_GIMMIC[0]),
				getVertexBufferObjectManager(), loadSoundResourceFromSD(Vol3TontontonResource.A2_2_AHAHA), GimicSprite.GIMIC_1);
		mGimicSprite[1] = new GimicSprite(436, 
				mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.IMAGE_GIMMIC[1]),
				getVertexBufferObjectManager(), loadSoundResourceFromSD(Vol3TontontonResource.A2_3_UWA), GimicSprite.GIMIC_2);
		mGimicSprite[2] = new GimicSprite(612, 
				mDefaultTp.getTexturePackTextureRegionLibrary().get(Vol3TontontonResource.IMAGE_GIMMIC[2]),
				getVertexBufferObjectManager(),  loadSoundResourceFromSD(Vol3TontontonResource.A2_4_UEN), GimicSprite.GIMIC_3);
		
		mScene.attachChild(mGimicSprite[0]);
		mScene.attachChild(mGimicSprite[1]);
		mScene.attachChild(mGimicSprite[2]);
		mScene.registerTouchArea(mGimicSprite[0]);
		mScene.registerTouchArea(mGimicSprite[1]);
		mScene.registerTouchArea(mGimicSprite[2]);
		
	}

	@Override
	public void combineGimmic3WithAction() {
		
	}
	
	@Override
	public synchronized void onResumeGame() {
		mHikariSprite.setVisible(false);
		BACKGOUND_NEXT = -1;
		isRUNFIRSTBG = true;
		nextBackground();
		for(AmazingFace face : mAmazingFace){
			face.clearAll();
		}
		updateMove = new IUpdateHandler() {
			float nextMove = 4;
			@Override
			public void reset() {
				
			}
			
			@Override
			public void onUpdate(float arg0) {
				if(isMoving){
					for(int i = 0; i< mAmazingFace.length; i++){
						if(mAmazingFace[i] !=null){
							mAmazingFace[i] .onMove(nextMove);
							}
					}
				}
			}
		};
		mScene.registerUpdateHandler(updateMove);
		Log.d(TAG, "onResumeGame");
		super.onResumeGame();
	}
	
	@Override
	public synchronized void onPauseGame() {
		isMoving = true;
		isFaceTouch = true;
		isRUNFIRSTBG = true;
		BACKGOUND_NEXT = -1;
		try {
			mScene.unregisterUpdateHandler(mTimerHandler);
			mScene.unregisterUpdateHandler(updateMove);
			for(AmazingFace face : mAmazingFace){
				face.setVisible(true);
			}
		} catch (Exception e) {
			
		}
		Log.d(TAG, "onPauseGame");
		super.onPauseGame();
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		moveAllEyes(pTouchEvent.getX(),pTouchEvent.getY());
		if(pTouchEvent.isActionDown()){
			nextBackground();
		}
		return true;
	}
	
	private void moveAllEyes(float pX, float pY){
		if(isFaceTouch){
			for(AmazingFace pFace : mAmazingFace){
				if(pFace != null){
					pFace.moveEye(pX, pY);
				}
			}
		}
	}
	
	///============================================================
	private void nextBackground(){
		BACKGOUND_NEXT++;
		if(BACKGOUND_NEXT>=6){
			BACKGOUND_NEXT = 0;
		}
		if(!isRUNFIRSTBG){
			A2_8_HAIKEIHENAK.play();
		}
		isRUNFIRSTBG = false;
		if(BACKGOUND_NEXT <3){
			
			mBackgroundA.setAlpha(1.0f);
			mBackgroundB.setAlpha(0.0f);
			mBackgroundA.setCurrentTileIndex(BACKGOUND_NEXT);
		}else{
			mBackgroundA.setAlpha(0.0f);
			mBackgroundB.setAlpha(1.0f);
			mBackgroundB.setCurrentTileIndex(BACKGOUND_NEXT - 3);
		}
	}
	
	// Class Gimic
	class GimicSprite extends Sprite{
		private int GIMIC_ID= 0;
		public static final int GIMIC_1 = 0;
		public static final int GIMIC_2 = 1;
		public static final int GIMIC_3 = 2;
		private Sound mSound;
		private boolean isTouch = true;
		private boolean isTouchGimic = true;
		
		public GimicSprite(float pX,ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager,final Sound pSound, int pGimic) {
			super(pX, 496, pTextureRegion, pVertexBufferObjectManager);
			this.mSound = pSound;
			this.GIMIC_ID = pGimic;
		}
		
		public void setEnableTouch(boolean pTouch){
			this.isTouch = pTouch;
		}
		
		private void GimicAction(int pFaceStatus){
			isFaceTouch = false;
			isMoving = false;
			int ranFace = mRandom.getNextIntNoRepeatLast();
			mHikariSprite.setVisible(true);
			for(AmazingFace face : mAmazingFace){
				face.switchFace(ranFace);
				face.changeFaceStatus(pFaceStatus);
			}
			mScene.registerUpdateHandler(mTimerHandler = new TimerHandler(1.5f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					for(AmazingFace face : mAmazingFace){
						face.resetDefault();
					}
					mHikariSprite.setVisible(false);
					isFaceTouch = true;
					isMoving = true;
					mScene.unregisterUpdateHandler(mTimerHandler);
					mTimerHandler = null;
				}
			}));
		
		}
		
		private void GimicAction3(){
			isFaceTouch = false;
			isMoving = false;
			mHikariSprite.setVisible(true);
			final int ranFace = mRandom.getNextIntNoRepeatLast();
			final int FaceStatus = AmazingFace.STATUS_CRY;
			for(AmazingFace face : mAmazingFace){
				face.switchFace(ranFace);
				face.changeFaceStatus(FaceStatus);
			}
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					A2_ZENINKIERU.play();
					for(AmazingFace face : mAmazingFace){
						face.gimic3Action();
					}
				}
			}) );
			
			mScene.registerUpdateHandler(mTimerHandler = new TimerHandler(2.8f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mHikariSprite.setVisible(false);
					for(AmazingFace face : mAmazingFace){
						face.setVisible(true);
					}
					A2_2_1_KAOMODORU.play();
					mScene.registerUpdateHandler(mTimerHandler = new TimerHandler(0.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							isFaceTouch = true;
							isMoving = true;
							mScene.unregisterUpdateHandler(mTimerHandler);
							mTimerHandler = null;
						}
					}));
				}
			}));
		}
		
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(pSceneTouchEvent.isActionDown() && isTouch && isTouchGimic && isFaceTouch){
				this.onTouch();
				switch (GIMIC_ID) {
				case GIMIC_1:
					GimicAction(AmazingFace.STATUS_LAUGHTER);
					break;
				case GIMIC_2:
					GimicAction(AmazingFace.STATUS_SURPRISE);
					break;
				case GIMIC_3:
					GimicAction3();
					break;
				}
			}
			return true;
		}
		
		private void onTouch(){
			Log.i(TAG, "Gimic touch:" + GIMIC_ID);
			runOnUpdateThread(new Runnable() {
				
				@Override
				public void run() {
					GimicSprite.this.mSound.play();
					isTouchGimic = false;
					GimicSprite.this.registerEntityModifier(new SequenceEntityModifier(
							new ScaleModifier(0.35f, 1, 1.3f),
							new ScaleModifier(0.35f, 1.3f, 1f, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									isTouchGimic = true;
								}
							})));
				}
			});
		}
	}
	
	//========================== Update Handler ========================

	
	//===================== AmazingFace Class ==========================
	
	private class AmazingFace extends Sprite{
		
		// Type Face
		private static final int TYPE_BIGFACE = 0;
		private static final int TYPE_SMALLFACE = 1;
		
		// Status Face
		private static final int STATUS_DEFAULT = 0;
		private static final int STATUS_LAUGHTER = 1;
		private static final int STATUS_SURPRISE = 2;
		private static final int STATUS_CRY = 3;
		
		// Action Face
		private static final int FACE_DEFAULT= 0;
		private static final int FACE_MEGANE= 1;
		private static final int FACE_HIGE = 2;
		private static final int FACE_KOBU = 3;
		private static final int FACE_TENGU = 4;
		private static final int FACE_AFRO = 5;
		
		// POINT X & Y for Face
		private float POINT_BIG_EYS_LEFT[] = new float[] {90,185};
		private float POINT_BIG_EYS_RIGHT[] = new float[] {148,185};
		private float POINT_SMALL_EYS_LEFT[] = new float[] {37,76};
		private float POINT_SMALL_EYS_RIGHT[] = new float[] {61,76};
		
		private float BOX_FACE_BIG[] = new float[] {90, 300};
		private float BOX_FACE_SMALL[] = new float[] {35, 125};
		
		// POINT X & Y for Face Auto
		private static final float POINT_BIG_X = 1361;
		private static final float POINT_SMALL_X = 1509.5f;
		
		// LEFT & RIGHT
		private static final int LEFT = 0;
		private static final int RIGHT = 1;
		
		private AnimatedSprite mAniFace;
		private AnimatedSprite mAniRedBlackFace;
		private AnimatedSprite mFace;
		private Sprite mLeftEye;
		private Sprite mRightEye;
		private Sprite mFlashSprite;
		
		private int tmpEys = 8;
		private int isFace = 0;
		private int isTypeFace = 0;
		private int isStatus = 0;
		private int DIRECTION = 0;
		
		private RanDomNoRepeat mRanDomNoRepeat;
		private TimerHandler mTimerHandler;
		
		public AmazingFace(int pTypeFace,float pX, float pY, ITiledTextureRegion pTitledFace,  ITiledTextureRegion pTitledRedBlackFace, 
				ITextureRegion mEyesTextureRegion,
				VertexBufferObjectManager pBufferObjectManager){
			super(pX, pY, mBlankTexture , pBufferObjectManager);
			this.setAlpha(0.0f);
			this.isTypeFace = pTypeFace;
			this.isStatus = STATUS_DEFAULT;
			mAniFace = new AnimatedSprite(0, 0, pTitledFace, pBufferObjectManager);
			this.attachChild(mAniFace);
			mAniRedBlackFace = new AnimatedSprite(0, 0, pTitledRedBlackFace, pBufferObjectManager);
			mAniRedBlackFace.setVisible(true);
			this.attachChild(mAniRedBlackFace);
			this.setWidth(this.mAniFace.getWidth());
			this.setHeight(this.mAniFace.getHeight());
			// Eyes
			if(pTypeFace == TYPE_BIGFACE){
				mLeftEye = new Sprite(POINT_BIG_EYS_LEFT[0], POINT_BIG_EYS_LEFT[1], mEyesTextureRegion, pBufferObjectManager);
				mRightEye = new Sprite(POINT_BIG_EYS_RIGHT[0], POINT_BIG_EYS_RIGHT[1], mEyesTextureRegion, pBufferObjectManager);
			}else{
				mLeftEye = new Sprite(POINT_SMALL_EYS_LEFT[0], POINT_SMALL_EYS_LEFT[1], mEyesTextureRegion, pBufferObjectManager);
				mRightEye = new Sprite(POINT_SMALL_EYS_RIGHT[0], POINT_SMALL_EYS_RIGHT[1], mEyesTextureRegion, pBufferObjectManager);
				tmpEys = 3;
			}
			this.attachChild(mLeftEye);
			this.attachChild(mRightEye);
			
			mFlashSprite = new Sprite(this.getWidth()/2 - mFlashTexture.getWidth()/2, 0, mFlashTexture, pBufferObjectManager);
			mFlashSprite.setVisible(false);
			this.attachChild(mFlashSprite);
			mRanDomNoRepeat = new RanDomNoRepeat();
			mRanDomNoRepeat.setLenghNoRepeat(6);
			//Log.i(TAG, "Face Create: TYPE:" + isTypeFace + " Width: " + this.getWidth() + " Height: " + this.getHeight());
		}
		
		private void clearAll(){
			this.resetLocalToFirst();
			resetDefault();
		}
		
		private void resetDefault(){
			switchFace(FACE_DEFAULT);
			changeFaceStatus(STATUS_DEFAULT);
		}
		
		private void showFlash(){
			if(this.isTypeFace == TYPE_BIGFACE){
			this.unregisterUpdateHandler(mTimerHandler);
			this.mFlashSprite.setVisible(true);
			this.registerUpdateHandler(mTimerHandler= new TimerHandler(0.2f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler paramTimerHandler) {
					mFlashSprite.setVisible(false);
					Log.i(TAG, "Face mTimerHandler");
				}
			}));
			}
		}
		
		protected int getRealFrame(int pframe,int pFaceAction){
			int result = 0;
			switch (pFaceAction) {
			case FACE_DEFAULT:
				result = pframe;
				break;
			case FACE_MEGANE:
				result = 4 + pframe;
				break;
			case FACE_HIGE:
				result = 8 + pframe;
				break;
			case FACE_KOBU:
				result = 12 + pframe;
				break;
			case FACE_TENGU:
				result = pframe;
				break;
			case FACE_AFRO:
				result = 4 + pframe;
				break;
			}
			return result;
		}
		
		private void switchFace(int pFaceAction){
			isFace = pFaceAction;
			isStatus = STATUS_DEFAULT;
			int frame = getRealFrame(0, pFaceAction);
			switch (pFaceAction) {
			case FACE_DEFAULT:
			case FACE_MEGANE:
			case FACE_HIGE:
			case FACE_KOBU:
				mAniRedBlackFace.setVisible(false);
				mAniFace.setCurrentTileIndex(frame);
				mAniFace.setVisible(true);
				mFace = mAniFace;
				break;
			case FACE_TENGU:
			case FACE_AFRO:
				mAniFace.setVisible(false);
				mAniRedBlackFace.setCurrentTileIndex(frame);
				mAniRedBlackFace.setVisible(true);
				mFace = mAniRedBlackFace;
				break;
			}
		}
		
		private void changeFaceStatus(int pStatus){
			if(mFace != null){
				isStatus = pStatus;
				mFace.setCurrentTileIndex(getRealFrame(pStatus, getFace()));
				switchEyes();
			}
		}
		
		// Return random Face action 
		private int randomFaceAction(){
			return mRanDomNoRepeat.getNextIntNoRepeatLast();
		}
		
		/** 
		 * @return Return Face Action: FACE_DEFAULT= 0; FACE_MEGANE= 1; FACE_HIGE = 2; FACE_KOBU = 3; FACE_TENGU = 4; FACE_AFRO = 5;
		 * */
		public int getFace(){
			return this.isFace;
		}
		
		/** Auto Visible & Invisible Eyes*/
		protected void switchEyes(){
				switch (this.isStatus) {
				case STATUS_DEFAULT:
				case STATUS_SURPRISE:	
					mLeftEye.setVisible(true);
					mRightEye.setVisible(true);
					break;
				case STATUS_LAUGHTER:
				case STATUS_CRY:	
					mLeftEye.setVisible(false);
					mRightEye.setVisible(false);
					break;	
				}
		}
		
		protected float getHiddenX(){
			if(isTypeFace == TYPE_BIGFACE){
				return POINT_BIG_X;
			}else{
				return POINT_SMALL_X;
			}
		}
		
		
		protected float[] getBoxTouch(){
			if(isTypeFace == TYPE_BIGFACE){
				return BOX_FACE_BIG;
			}else{
				return BOX_FACE_SMALL;
			}
		}
		
		public void moveEye(float pX, float pY){
				if(this.mLeftEye.isVisible() && this.mRightEye.isVisible()){
				//	Log.d(TAG, "moveEye :" + pX + "/" + pY);
					// set new layout for left eye
					float xDisEye = pX - this.mLeftEye.getmXFirst() - this.getX();
					float yDisEye = pY - this.mLeftEye.getmYFirst() - this.getY();
					float disEye = (float) Math.sqrt(xDisEye * xDisEye + yDisEye * yDisEye);
					float x = Math.round(tmpEys * xDisEye / disEye);
					float y = Math.round(tmpEys * yDisEye / disEye);
					float newXLeft = this.mLeftEye.getmXFirst() + x;
					float newYLeft = this.mLeftEye.getmYFirst() + y;
					this.mLeftEye.setPosition(newXLeft, newYLeft);
					
					// set new layout for right eye
					float xDisEyeRight = pX - this.mRightEye.getmXFirst()  - this.getX();
					float yDisEyeRight = pY - this.mRightEye.getmYFirst() - this.getY();
					float disEyeRight = (float) Math.sqrt(xDisEyeRight * xDisEyeRight + yDisEyeRight * yDisEyeRight);
					float xR = Math.round(tmpEys * xDisEyeRight / disEyeRight);
					float yR = Math.round(tmpEys * yDisEyeRight / disEyeRight);
					float newXRight = this.mRightEye.getmXFirst() + xR;
					float newYRight = this.mRightEye.getmYFirst() + yR;
					this.mRightEye.setPosition(newXRight, newYRight);
				}
		}
		
		protected void onMove(float pMove){
			AmazingFace.this.setPosition(this.getX() - pMove, AmazingFace.this.getmYFirst());
			if((AmazingFace.this.getX() + AmazingFace.this.getWidth()) < 0){
				float XMOVE = AmazingFace.this.getHiddenX() + (AmazingFace.this.getWidth() + AmazingFace.this.getX() );
				//Log.i(TAG,  "X: " + AmazingFace.this.getX() + "/Y"+ AmazingFace.this .getmYFirst()+" ->" + XMOVE);
			this.setPosition(XMOVE, AmazingFace.this.getY());
			switchFace(FACE_DEFAULT);
			changeFaceStatus(STATUS_DEFAULT);
			}
			if((this.getX() + this.getWidth()/2) >= 480){
				this.DIRECTION = RIGHT;
			}else{
				this.DIRECTION = LEFT;
			}
		}
		
		// Action Touch Face
		private void actionFaceTouch(){
			playSoundTouchFace();
			showFlash();
			int random  = randomFaceAction();
			switchFace(random);
			switchEyes();
		}
		
		// Play Sound Touch Face
		private void playSoundTouchFace(){
			switch (BACKGOUND_NEXT) {
			case 0:
			case 1:
			case 2:
				A2_1_3_KAOTOUCH.play();
				break;
			case 3:
				A2_4_HAWAI.play();
				break;
			case 4:
				A2_5_KAPON.play();
				break;
			default:
				A2_6_UTYU.play();
				break;
			}
		}
	/*	
	
		
		private  float lastX = 0;
		private  float lastY = 0;
		
		private void resetLastLocation(){
			this.setPosition(lastX, lastY);
		}*/
		
		/** Action Gimic 3 */
		private void gimic3Action(){
			final float lastX = this.getX();
			final float lastY = this.getY();
			float toX = 0;
			float toY = 0;
			
			if(this.DIRECTION == LEFT){
				toX = lastX - this.getWidth() * 2;
				if(this.getY() == -76 || this.getY() ==50 ){
					toY = lastY - this.getHeight() * 2;
				}else{
					toY = lastY + this.getHeight() * 2;
				}
			}else{
				toX = lastX + this.getWidth() * 2;
				if(this.getY() == -76 || this.getY() ==50 ){
					toY = lastY - this.getHeight() * 2;
				}else{
					toY = lastY + this.getHeight() * 2;
				}
			}
			float timeDuration = 1.5f;
			if(this.isTypeFace == TYPE_SMALLFACE){
				timeDuration = 1.0f;
			}
			this.registerEntityModifier(new MoveModifier(timeDuration, lastX, toX, lastY, toY, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					AmazingFace.this.resetDefault();
					AmazingFace.this.setPosition(lastX, lastY);
					AmazingFace.this.setVisible(false);
					//Log.d(TAG,"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + timeDuration);
				}
			}));

		}
		
		/** onAreaTouched */
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(!isFaceTouch){
				return true;
			}
			moveEye(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			float BOXFACE[] = getBoxTouch();
			if(pTouchAreaLocalY >= BOXFACE[0] && pTouchAreaLocalY <= BOXFACE[1]){
					switch (pSceneTouchEvent.getAction()) {
					case  TouchEvent.ACTION_DOWN:
						Log.i(TAG, "ACTION_DOWN");
						this.actionFaceTouch();
						break;
					case  TouchEvent.ACTION_MOVE:
						Log.i(TAG, "ACTION_MOVE");
						break;	
					case  TouchEvent.ACTION_UP:
						Log.i(TAG, "ACTION_UP");
						break;	
					}
					return true;
			}
			return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
	}
	//===================== End Class AmazingFace ===========================

}
