package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OsyougatsuResource;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;


public class Vol3OsyougatsuCScene extends Vol3OsyougatsuScene {
  
  private static final String TAG= "VOL3OSYOUGATSU---C---SCENE";
  private static final int BG_DEFAULT = 0;
  private static final int BG_RAINING = 1;
  
  
  private boolean isLoaded = false;
  private boolean isDrawView = false;
  private boolean isPreView = false;
  private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
  private VertexBufferObjectManager mVertexBufferObjectManager;
  
  private TexturePack mTexturePack[] = new TexturePack[4];
  private TexturePack mBackGroundTexturePack[] = new TexturePack[12];
  
  private ITextureRegion mTakonEnd;
  private ITextureRegion mTanKo2StartTexture;
  private ITextureRegion mHimonTextureRegion[] = new ITextureRegion[8];
  private ITextureRegion mCloudSmokeTexture;
  
  private ITiledTextureRegion mCloudTiledTexture;
  private ITiledTextureRegion mBoyTiledTexture;
  private ITiledTextureRegion mAnimalsTiledTexture;
  private ITiledTextureRegion mTakoTiledTexture[] = new  ITiledTextureRegion[4];
  
  private Rectangle mBackGroundLayer[] = new Rectangle[3];
  
  private Sprite mBackGround[][] = new Sprite[3][4];
  private Sprite mSmokeSprite;
  
  private AnimatedSprite mCloudAniSprite;
  private AnimatedSprite mAnimalsAniSprite;
  
  private BoyLayer mBoyLayer;
  
  private IUpdateHandler iUpdateHandler;
  private LoopEntityModifier loopEntityModifier;
  private float xMove = 3;
  private boolean isRun = true;
  private boolean isBgRain = false;
  
  private static final float POINTER_HIMON[][] = new float[][]{
            {269, 529}, {303, 316},{302, 316}, {303, 316}, {302, 316}, {314, 266}, {318, 320}, {319, 332}                                                                          
  };

  public Vol3OsyougatsuCScene(final TexturePackLoaderFromSource pTexturePackLoaderFromSource,
      VertexBufferObjectManager pVertexBufferObjectManager, final TextureManager pTextureManager) {
    mTexturePackLoaderFromSource = pTexturePackLoaderFromSource;
    mVertexBufferObjectManager = pVertexBufferObjectManager;
  }

  @Override
  public void loadResource() {
    if (!isLoaded) {
      loadTexture();
      isLoaded = true;
    }
  }

  private void loadTexture() {
    Log.i(TAG, "-- loadTexture --");
      for (int i = 0; i < mBackGroundTexturePack.length; i++) {
        int index = i+1 ;
        if(i <= 3){
          mTexturePack[i] = mTexturePackLoaderFromSource.load("packer_partc_"+index+".xml");
          mTexturePack[i].loadTexture();
        }
        mBackGroundTexturePack[i] = mTexturePackLoaderFromSource.load("packer_partc_bg_"+index+".xml");
        mBackGroundTexturePack[i].loadTexture();
      }
      
    mTanKo2StartTexture = mTexturePack[1].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_2.A3_15_4_IPHONE_TAKO2_ID);  
      
    mCloudTiledTexture = getTiledTextureFromPacker(mTexturePack[3],
            Vol3OsyougatsuResource.packer_partc_4.A3_13_1_IPHONE_KUMO_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_13_1_IPHONE_KUMO_ID
            );
    mCloudSmokeTexture =mTexturePack[3].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_4.A3_13_3_IPHONE_KUMO_ID);
    
    mBoyTiledTexture = getTiledTextureFromPacker(mTexturePack[3],
            Vol3OsyougatsuResource.packer_partc_4.A3_15_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15A_1_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15A_2_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15A_3_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15A_4_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15B_1_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_15B_2_1_IPHONE_BOY_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_16_1_IPHONE_BOY_ID
      );
    
    mAnimalsTiledTexture = getTiledTextureFromPacker(mTexturePack[3],
            Vol3OsyougatsuResource.packer_partc_4.A3_14_1_IPHONE_JIMEN_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14_2_IPHONE_JIMEN_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14A_1_IPHONE_MOGURA_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14A_2_IPHONE_MOGURA_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14B_1_IPHONE_PINK_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14B_2_IPHONE_PINK_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14C_1_IPHONE_WATER_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14C_2_IPHONE_WATER_ID,
            Vol3OsyougatsuResource.packer_partc_4.A3_14C_3_IPHONE_WATER_ID
      );
    
    mHimonTextureRegion = new ITextureRegion[] {
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15A_1_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15A_2_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15A_3_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15A_4_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15B_1_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_15B_2_2_IPHONE_HIMO_ID), 
         mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_1.A3_16_2_IPHONE_HIMO_ID)
    };
    
    
    mTakoTiledTexture[0] = getTiledTextureFromPacker(mTexturePack[2],
            Vol3OsyougatsuResource.packer_partc_3.A3_15_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_1_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_1_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_1_5_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_2_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_2_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_2_5_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_3_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_3_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_3_5_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_4_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_4_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15A_4_5_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_1_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_1_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_1_5_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_2_3_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_2_4_IPHONE_TAKO_ID,
            Vol3OsyougatsuResource.packer_partc_3.A3_15B_2_5_IPHONE_TAKO_ID
     );
    
    mTakoTiledTexture[1] = getTiledTextureFromPacker(mTexturePack[1],
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_1_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_1_7_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_1_8_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_2_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_2_7_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_2_8_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_3_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_3_7_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_3_8_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_4_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_4_7_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_2.A3_15A_4_8_IPHONE_TAKO2_ID
    );
    
    mTakoTiledTexture[2] = getTiledTextureFromPacker(mTexturePack[0],
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_1_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_1_7_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_1_8_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_2_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_2_6_IPHONE_TAKO2_ID,
            Vol3OsyougatsuResource.packer_partc_1.A3_15B_2_6_IPHONE_TAKO2_ID
    );
    
    mTakonEnd = mTexturePack[2].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partc_3.A3_16_3_IPHONE_TAKO2_ID);
      
     Log.i(TAG, "-- End loadTexture --"); 
  }
  
  public boolean isPreView() {
    return isPreView;
  }

  @Override
  public void preView() {
    if (!isLoaded) {
      loadTexture();
      isLoaded = true;
    }
    if (!isDrawView) {
      drawScene();
      isDrawView = true;
    }
    isPreView = true;
  }

  public void destroyView() {
    isPreView = false;
  }

  @Override
  protected void drawScene() {
    Log.i(TAG, "-- drawScene --");
    mBackGroundLayer[0] = new Rectangle(960 - 1285 + 275, 0, 1285, 640, mVertexBufferObjectManager);
    mBackGroundLayer[0].setAlpha(0.0f);
    mBackGroundLayer[1] = new Rectangle(960 - 1285 - 1285 + 275, 0, 1285, 640, mVertexBufferObjectManager);
    mBackGroundLayer[1].setAlpha(0.0f);
    mBackGroundLayer[2] = new Rectangle(960 - 1285 - 1285 - 1285 + 275, 0, 1285, 640, mVertexBufferObjectManager);
    mBackGroundLayer[2].setAlpha(0.0f);
    
    this.attachChild(mBackGroundLayer[0]);
    this.attachChild(mBackGroundLayer[1]);
    this.attachChild(mBackGroundLayer[2]);
    
    for (int i = 0; i < mBackGroundLayer.length; i++) {
      int index = i * 4;
      mBackGround[i][0] = new Sprite(0, 0,
        mBackGroundTexturePack[index].getTexturePackTextureRegionLibrary().get(0), mVertexBufferObjectManager);
      mBackGround[i][1] = new Sprite(0, 0,
        mBackGroundTexturePack[index + 1].getTexturePackTextureRegionLibrary().get(0), mVertexBufferObjectManager);
      mBackGround[i][2] = new Sprite(0, 0,
        mBackGroundTexturePack[index + 2].getTexturePackTextureRegionLibrary().get(0), mVertexBufferObjectManager);
      mBackGround[i][3] = new Sprite(0, 0,
        mBackGroundTexturePack[index + 3].getTexturePackTextureRegionLibrary().get(0), mVertexBufferObjectManager);
      
      mBackGround[i][0].setVisible(false);
      mBackGround[i][1].setVisible(false);
      mBackGround[i][2].setVisible(false);
      mBackGround[i][3].setVisible(false);
    }
    
    for (int j = 0; j < mBackGround.length; j++) {
      int x = 0;
      if(j == 0 ){
        x = 0;
      }else if(j == 1 ){
        x = 2;
      }else{
        x = 1;
      }
      for (int k = 0; k < mBackGround[j].length; k++) {
        mBackGroundLayer[x].attachChild(mBackGround[j][k]);
      }
    }
    
    mAnimalsAniSprite = new AnimatedSprite(478 , 90, mAnimalsTiledTexture, mVertexBufferObjectManager);
    mBackGroundLayer[0].attachChild(mAnimalsAniSprite);
        
    mBoyLayer = new BoyLayer();
    this.attachChild(mBoyLayer);
    
    
    mCloudAniSprite = new AnimatedSprite(85, 31, mCloudTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mCloudAniSprite);
    
    mSmokeSprite = new Sprite(287, 133, mCloudSmokeTexture, mVertexBufferObjectManager);
    mSmokeSprite.setVisible(false);
    this.attachChild(mSmokeSprite);
    
    Log.i(TAG, "-- End drawScene --");
  }
  
  @Override
  public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
      if(pSceneTouchEvent.isActionDown()){
        
       if (mBoyLayer.onAreaTouch(pSceneTouchEvent)) return true;
        
        Log.i(TAG, "-- onSceneTouchEvent isActionDown --");
        
        int pX = (int)pSceneTouchEvent.getX();
        int pY = (int)pSceneTouchEvent.getY();
        
        if(checkContains(mBackGroundLayer[0], 1100, 22, 1283, 223, pX, pY) ||
           checkContains(mBackGroundLayer[1], 460, 50, 653, 213, pX, pY)
            ){
          Log.i(TAG, "-- onSceneTouchEvent  BG_RAINING--");
          bacgroundAction(BG_RAINING);
        }else if(checkContains(mBackGroundLayer[0], 523, 240, 718, 403, pX, pY)){
          Log.i(TAG, "-- onSceneTouchEvent  Animals--");
          if(!mAnimalsAniSprite.isAnimationRunning() && mAnimalsAniSprite.getCurrentTileIndex() == 0){
            animalsAction();
          }
        }
        
      }
    return super.onSceneTouchEvent(pSceneTouchEvent);
  }
  
  //=============================================================
  // Function
  //=============================================================
  
  // Animals Action
  
  private void animalsAction(){
    Log.i(TAG, "-- animalsAction  running--");
    int ran = new Random().nextInt(3);
    long duration[];
    int frame[];
    
    if(ran == 0){
      frame = new int[]{0,1,2,3,0};
      duration = new long[]{300,300,300,500,100};
      playSoundDelay(Vol3Osyougatsu.OGG_A3_C_14A_KAMOME, 0.9f);
    }else if(ran == 1){
      frame = new int[]{0,1,4,5,0};
      duration = new long[]{300,300,300,500,100};
      playSoundDelay(Vol3Osyougatsu.OGG_A3_C_14B_POWAN, 0.9f);
    }else{
      frame = new int[]{0,1,6,7,8,7,8,7,8,0};
      duration = new long[]{300,300,300,300,300,300,300,300,300,100};
      playSoundDelay(Vol3Osyougatsu.OGG_A3_C_14C_BOCYAN, 0.9f);
    }
    mAnimalsAniSprite.animate(duration, frame, 0);
  }
  
  // Background Action
 
  private void bacgroundAction(int pAction){
    if(pAction == BG_DEFAULT){ // Default action
      Log.i(TAG, "-- bacgroundAction  BG_DEFAULT--");
      for (int i = 0; i < mBackGround.length; i++) {
        for (int j = 0; j < mBackGround[i].length; j++) {
            if(j==0){
              mBackGround[i][j].setVisible(true);
            }else{
              mBackGround[i][j].setVisible(false);
            }
        }
      }
      
    }else if(pAction == BG_RAINING && !isBgRain){ // Action Raining
      Log.i(TAG, "-- bacgroundAction  BG_RAINING--");
      this.unregisterEntityModifier(loopEntityModifier);
      this.registerEntityModifier(loopEntityModifier = new LoopEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
        int count = 0;
        @Override
        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
          Log.i(TAG, "DelayModifier Count " + count);
          if(count == 1){
            Vol3Osyougatsu.OGG_A3_C_12_AME_SHORT.play();
          }
          if(count == 0){
            for (int i = 0; i < mBackGround.length; i++) {
              for (int j = 0; j < mBackGround[i].length; j++) {
                  if(j==1){
                    mBackGround[i][j].setVisible(true);
                  }else{
                    mBackGround[i][j].setVisible(false);
                  }
              }
            }
          }else{
            int index = count % 2 == 1 ? 2 : 3;
            for (int i = 0; i < mBackGround.length; i++) {
              for (int j = 0; j < mBackGround[i].length; j++) {
                  if(j==index){
                    mBackGround[i][j].setVisible(true);
                  }else{
                    mBackGround[i][j].setVisible(false);
                  }
              }
            }
          }
          count++;
        }
        
        @Override
        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
          
        }
      }), 6, new IEntityModifierListener() {
        
        @Override
        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
          Log.i(TAG, "onModifierStarted");
          isBgRain = true;
        }
        
        @Override
        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
          Log.i(TAG, "onModifierFinished  ");
          bacgroundAction(BG_DEFAULT);
          isBgRain = false;
        }
      }));
    }
  }

  @Override
  public void onResumeGame() {
    Log.i(TAG, "-- onResumeGame --");
    bacgroundAction(BG_DEFAULT);
    isRun = false;
    isBgRain = false;
    xMove = 3;
    mBoyLayer.boyAction(BoyLayer.STATE_DEFAULT);
    this.unregisterUpdateHandler(iUpdateHandler);
    this.registerUpdateHandler(iUpdateHandler = new IUpdateHandler() {
      @Override
      public void reset() {
          
      }
      
      @Override
      public void onUpdate(float arg0) {
          if(isRun){
                for (int i = 0; i < mBackGroundLayer.length; i++) {
                  mBackGroundLayer[i].setX(mBackGroundLayer[i].getX() + xMove);
                  
                  if(mBackGroundLayer[i].getX() >= 960){
                    mBackGroundLayer[i].setX( mBackGroundLayer[i].getX() - 1285 - 1285 - 1285);
                  }
                }
                if(System.currentTimeMillis() > mBoyLayer.timeFinish){
                    isRun = false;
                    mBoyLayer.resetAll();
                    mBoyLayer.boyAction(BoyLayer.STATE_DEFAULT);
                    for (int i = 0; i < mBackGroundLayer.length; i++) {
                      mBackGroundLayer[i].resetLocalToFirst();
                    }
                }
          }
      }
    });
  }

  @Override
  public void onPauseGame() {
    if(!isDrawView || !isPreView){
      return;
    }
    for (int i = 0; i < mBackGroundLayer.length; i++) {
      mBackGroundLayer[i].resetLocalToFirst();
    }
    mBoyLayer.resetAll();
    mSmokeSprite.setVisible(false);
    mCloudAniSprite.stopAnimation(0);
    mAnimalsAniSprite.stopAnimation(0);
    this.unregisterEntityModifier(loopEntityModifier);
    this.unregisterUpdateHandler(iUpdateHandler);
    this.clearEntityModifiers();
    this.clearUpdateHandlers();
    Log.i(TAG, "-- onPauseGame --");
  }

  //==========================================================================
  // Class Boy Layout
  //==========================================================================
  
  class BoyLayer extends Entity{
      
      private static final int STATE_DEFAULT = 0;
      private static final int STATE_A = 1;
      private static final int STATE_B = 2;
      private static final int STATE_END = 3;
      private static final long TIME_RUNNING = 3000;
      
      private int STATE = STATE_DEFAULT;
      
      private long timeFinish = 0;
      
      private AnimatedSprite mBoyAniSprite;
      private AnimatedSprite mTakoAniSprite;
      private AnimatedSprite mTakoIIAniSprite;
      private AnimatedSprite mTakoIIBlackAniSprite;
      private Sprite mHimonSprite[] = new Sprite[8];
      private Sprite mTakoEnd;
      private Sprite mTako2StartSprite;
      
      private TimerHandler timerHandler;
      
      private int POINT_TAKOA[] = new int[]{ 562,57 };
      private int POINT_TAKOAII[] = new int[]{ 537,-114 };
      private int POINT_TAKOB[][] = new int[][]{
                { 565,6 }, {581, 65}
                                     };
      private int POINT_TAKOBIIBLACK[][] = new int[][]{
                { 535,-138 }, {552, -79}
      };
         
      public BoyLayer(){

        for (int i = 0; i < mHimonSprite.length; i++) {
          mHimonSprite[i] = new Sprite(POINTER_HIMON[i][0], POINTER_HIMON[i][1], mHimonTextureRegion[i], mVertexBufferObjectManager);
          mHimonSprite[i].setVisible(false);
          this.attachChild( mHimonSprite[i] );
        }
  
        mBoyAniSprite = new AnimatedSprite(102, 306, mBoyTiledTexture, mVertexBufferObjectManager);
        this.attachChild(mBoyAniSprite);
        
        mTakoAniSprite = new AnimatedSprite(559, 393, mTakoTiledTexture[0], mVertexBufferObjectManager);
        this.attachChild(mTakoAniSprite);
        
        mTakoIIAniSprite = new AnimatedSprite(POINT_TAKOAII[0], POINT_TAKOAII[1], mTakoTiledTexture[1], mVertexBufferObjectManager);
        this.attachChild(mTakoIIAniSprite);
        mTakoIIAniSprite.setVisible(false);
        
        mTakoIIBlackAniSprite = new AnimatedSprite(535, -183, mTakoTiledTexture[2], mVertexBufferObjectManager);
        this.attachChild(mTakoIIBlackAniSprite);
        mTakoIIBlackAniSprite.setVisible(false);
        
        mTakoEnd = new Sprite(539, 48, mTakonEnd, mVertexBufferObjectManager);
        this.attachChild(mTakoEnd);
        mTakoEnd.setVisible(false);
        
        mTako2StartSprite = new Sprite(559, 330, mTanKo2StartTexture, mVertexBufferObjectManager);
        this.attachChild(mTako2StartSprite);
        mTako2StartSprite.setVisible(false);
        
      }
      
      public boolean onAreaTouch(TouchEvent touchEvent){
            if(touchEvent.isActionDown()){
                  int pX = (int)touchEvent.getX();
                  int pY = (int)touchEvent.getY();
              if(mTakoAniSprite.contains(pX, pY) && mTakoAniSprite.isVisible()){
                Log.i(TAG, "Tako touched !");
                takoAction();
                return true;
              }else if(checkContains(mBoyAniSprite, 17, 14, 229, 200, pX, pY)){
                faceBoyTouch();
                return true;
              }else if(mCloudAniSprite.contains(pX, pY)){
                cloudTouch();
                return true;
              }
            }
        return false;
      }
      
      
      private void cloudTouch(){
        if(!mCloudAniSprite.isAnimationRunning()){
          mCloudAniSprite.animate(new long[]{1700,200}, new int[]{1,0},0, new IAnimationListener() {
            
            @Override
            public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
              mSmokeSprite.setVisible(true);
              Vol3Osyougatsu.OGG_A3_C_13_PYU2.play();
            }
            
            @Override
            public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
              
            }
            
            @Override
            public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
                if(arg2 == 1){
                  mSmokeSprite.setVisible(false);
                }
            }
            
            @Override
            public void onAnimationFinished(AnimatedSprite arg0) {
            }
          });
        }
        
        if(STATE == STATE_DEFAULT){
          boyAction(STATE_A);
        }else if(STATE == STATE_A){
          timeFinish = System.currentTimeMillis() + TIME_RUNNING;
        }else if(STATE == STATE_B){
          timeFinish = System.currentTimeMillis() + TIME_RUNNING;
        }
      }
      
      private void faceBoyTouch(){
        if(STATE == STATE_DEFAULT){
          Vol3Osyougatsu.OGG_A3_C_15A_SYUTA.play();
          boyAction(STATE_A);
        }else if(STATE == STATE_A){
          timeFinish = System.currentTimeMillis() + TIME_RUNNING;
          Vol3Osyougatsu.OGG_A3_C_15B_UN.play();
          boyAction(STATE_B);
        }else if(STATE == STATE_B){
          timeFinish = System.currentTimeMillis() + TIME_RUNNING;
        }
      }
      
      private void boyAction(int pAction){
        mBoyAniSprite.stopAnimation();
        mTakoAniSprite.stopAnimation();
        mTakoIIAniSprite.setVisible(false);
        mTakoIIAniSprite.stopAnimation();
        mTakoIIBlackAniSprite.stopAnimation();
        mTakoIIBlackAniSprite.setVisible(false);
        mTako2StartSprite.setVisible(false);
        if(pAction == STATE_DEFAULT){
          isRun = false;
          STATE = STATE_DEFAULT;
          mTakoAniSprite.setWidth(mTakoTiledTexture[0].getWidth(0));
          mTakoAniSprite.setHeight(mTakoTiledTexture[0].getHeight(0));
          mTakoAniSprite.setVisible(true);
          mBoyAniSprite.setCurrentTileIndex(0);
          mTakoAniSprite.setCurrentTileIndex(0);
          setActivateHimonID(0);
          mTakoAniSprite.resetLocalToFirst();
        }else if(pAction == STATE_A){
          timeFinish = System.currentTimeMillis() + TIME_RUNNING;
          xMove = 3;
          isRun = true;
          STATE = STATE_A;
          mBoyAniSprite.animate(new long[]{450,450,450,450}, new int[]{1,2,3,4}, -1, new IAnimationListener() {
            
            @Override
            public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
              setActivateHimonID(1);
            }
            
            @Override
            public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
            }
            
            @Override
            public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
              Log.i(TAG, "Frame > " + arg2);
              setActivateHimonID(arg2 + 1);
            }
            
            @Override
            public void onAnimationFinished(AnimatedSprite arg0) {
            }
          });
          mTakoAniSprite.setWidth(mTakoTiledTexture[0].getWidth(1));
          mTakoAniSprite.setHeight(mTakoTiledTexture[0].getHeight(1));
          mTakoAniSprite.setVisible(true);
          mTakoAniSprite.setPosition(POINT_TAKOA[0], POINT_TAKOA[1]);
          mTakoAniSprite.animate(new long[]{150,150,150,150,150,150,150,150,150,150,150,150}, new int[]{1,2,3,4,5,6,7,8,9,10,11,12},-1);
          mTakoIIAniSprite.animate(new long[]{150,150,150,150,150,150,150,150,150,150,150,150}, new int[]{0,1,2,3,4,5,6,7,8,9,10,11},-1);
        }else if(pAction == STATE_B){
          isRun = true;
          STATE = STATE_B;
          xMove = 6;
          mBoyAniSprite.animate(new long[]{450,450}, new int[]{5,6}, -1, new IAnimationListener() {
            
            @Override
            public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
              setActivateHimonID(5);
            }
            
            @Override
            public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {            }
            
            @Override
            public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
              setActivateHimonID(arg2 + 5);
              mTakoAniSprite.setPosition(POINT_TAKOB[arg2][0], POINT_TAKOB[arg2][1]);
              mTakoIIBlackAniSprite.setPosition(POINT_TAKOBIIBLACK[arg2][0], POINT_TAKOBIIBLACK[arg2][1]);
            }
            
            @Override
            public void onAnimationFinished(AnimatedSprite arg0) {           }
          });
          
          mTakoAniSprite.setWidth(mTakoTiledTexture[0].getWidth(13));
          mTakoAniSprite.setHeight(mTakoTiledTexture[0].getHeight(13));
          mTakoAniSprite.setVisible(true);
          mTakoIIBlackAniSprite.setVisible(false);
          mTakoAniSprite.setPosition(POINT_TAKOB[0][0], POINT_TAKOB[0][1]);
          mTakoAniSprite.animate(new long[]{150,150,150,150,150,150}, new int[]{13,14,15,16,17,18}, -1);
          mTakoIIBlackAniSprite.animate(new long[]{150,150,150,150,150,150}, new int[]{0,1,2,3,4,5}, -1);
          
        }
      }
      
      private void takoAction(){
          if(STATE == STATE_DEFAULT && !mTako2StartSprite.isVisible()){
            mTako2StartSprite.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
              
              @Override
              public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                Vol3Osyougatsu.OGG_A3_C_16_CYAPA.play();
                mTakoAniSprite.setVisible(false);
                mTako2StartSprite.setVisible(true);
              }
              
              @Override
              public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                mTakoAniSprite.setVisible(true);
                mTako2StartSprite.setVisible(false);
              }
            }));
          
          }else if(STATE == STATE_A){
            mTakoAniSprite.stopAnimation();
            mTakoAniSprite.setVisible(false);
            mTakoIIAniSprite.setVisible(true);
            actionEnd();
          }else if(STATE == STATE_B){
            mTakoAniSprite.stopAnimation();
            mTakoAniSprite.setVisible(false);
            mTakoIIBlackAniSprite.setVisible(true);
            actionEnd();
          }
      }
      
      private void actionEnd(){
        
          STATE  = STATE_END;
          Vol3Osyougatsu.OGG_A3_C_16_PYONBASYAN.play();
          
          this.unregisterUpdateHandler(timerHandler);
          this.registerUpdateHandler(timerHandler = new TimerHandler(0.5f, new ITimerCallback() {
            
            @Override
            public void onTimePassed(TimerHandler arg0) {
                Vol3Osyougatsu.OGG_A3_C_15B_UN.stop();
                mTakoAniSprite.stopAnimation();
                mTakoAniSprite.setVisible(false);
                mTakoIIAniSprite.stopAnimation();
                mTakoIIAniSprite.setVisible(false);
                mTakoIIBlackAniSprite.stopAnimation();
                mTakoIIBlackAniSprite.setVisible(false);
                mBoyAniSprite.stopAnimation();
                mBoyAniSprite.setCurrentTileIndex(7);
                mTakoEnd.setVisible(true);
                setActivateHimonID(7);
                
                Vol3OsyougatsuCScene.this.unregisterUpdateHandler(timerHandler);
                Vol3OsyougatsuCScene.this.registerUpdateHandler(timerHandler = new TimerHandler(0.4f, new ITimerCallback() {
                  
                  @Override
                  public void onTimePassed(TimerHandler arg0) {
                    mTakoEnd.setVisible(false);
                    boyAction(BG_DEFAULT);
                    for (int i = 0; i < mBackGroundLayer.length; i++) {
                      mBackGroundLayer[i].resetLocalToFirst();
                    }
                  }
                }));
                
            }
          }));
      }
      
      private void setActivateHimonID(int index){
        for (int i = 0; i < mHimonSprite.length; i++) {
          if(i == index){
            mHimonSprite[i].setVisible(true);
          }else{
            mHimonSprite[i].setVisible(false);
          }
        }
      }
      
      public void resetAll(){
        for (int i = 0; i < mHimonSprite.length; i++) {
          mHimonSprite[i].setVisible(false);
        }
        timeFinish = 0;
        this.clearEntityModifiers();
        this.clearUpdateHandlers();
        this.unregisterUpdateHandler(timerHandler);
        mTakoEnd.setVisible(false);
        mTako2StartSprite.setVisible(false);
        mTakoIIBlackAniSprite.stopAnimation(0);
        mTakoIIBlackAniSprite.setVisible(false);
        mTakoIIAniSprite.stopAnimation(0);
        mTakoIIAniSprite.setVisible(false);
        mTakoAniSprite.stopAnimation(0);
        mTakoAniSprite.resetLocalToFirst();
        mBoyAniSprite.stopAnimation(0);
        mBoyAniSprite.clearEntityModifiers();
        mBoyAniSprite.clearUpdateHandlers();
        STATE = STATE_DEFAULT;
      }
      
  }
  
  protected void playSoundDelay(final Sound pSound, float pTimerSeconds){
    this.registerUpdateHandler(new TimerHandler(pTimerSeconds, new ITimerCallback() {
      
      @Override
      public void onTimePassed(TimerHandler arg0) {
        pSound.play();
      }
    }));
  }

}
