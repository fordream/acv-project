package jp.co.xing.utaehon03.songs;

import java.util.LinkedList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OsyougatsuResource;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;


public class Vol3OsyougatsuBScene extends Vol3OsyougatsuScene {
  
  private static final String TAG= "VOL3OSYOUGATSU---B---SCENE";

  private boolean isLoaded = false;
  private boolean isDrawView = false;
  private boolean isPreView = false;
  private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
  private VertexBufferObjectManager mVertexBufferObjectManager;
  private TexturePack mTexturePack[] = new TexturePack[7];
  
  private ITextureRegion mBackGroundTextureRegion;
  
  private ITiledTextureRegion mKakejikuTiledTexture;
  private ITiledTextureRegion mDoorTiledTexture;
  private ITiledTextureRegion mDoorATiledTexture;
  private ITiledTextureRegion mDoorBTiledTexture;
  private ITiledTextureRegion mDoorCTiledTexture;
  private ITiledTextureRegion mNusuTiledTexture;
  private ITiledTextureRegion mToriTiledTexture; 
  private ITiledTextureRegion mFujisanTiledTexture;
  private ITiledTextureRegion mFlashEndTiledTexture;
  private ITiledTextureRegion mMochiTiledTexture[] = new ITiledTextureRegion[10];
  
  private Sprite spriteBackGround;
  private Sprite mBoxSprite;
  private Sprite mSanpowSprite;
  private Sprite mArrowSprite;
  
  private AnimatedSprite mKakejikuAniSprite;
  private AnimatedSprite mDoorAniSprite;
  private AnimatedSprite mDoorAAniSprite;
  private AnimatedSprite mDoorBAniSprite;
  private AnimatedSprite mDoorCAniSprite;
  private AnimatedSprite mNusuAniSprite[] = new AnimatedSprite[2];
  private AnimatedSprite mToriAniSprite[] = new AnimatedSprite[2];
  private AnimatedSprite mFujisanAniSprite;
  private AnimatedSprite mFlashEndAniSprite;
  private MochiAniSprite mMochiAniSprite[] = new MochiAniSprite[10];
  
  private RectangularShape mColCenter;
  private RectangularShape mColLeft;
  private RectangularShape mColRight;
  private MochiTouch mMochiTouch;
  
  private RanDomNoRepeat ranDomNoRepeat;
  
  private LinkedList<MochiAniSprite> listMochiUsed = new LinkedList<Vol3OsyougatsuBScene.MochiAniSprite>();
  
  private boolean isActionDoor = false;
  private int countFujisan = 0; 
  private float pointer_mochi = 465;

  public Vol3OsyougatsuBScene(final TexturePackLoaderFromSource pTexturePackLoaderFromSource,
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
    for (int i = 0; i < mTexturePack.length; i++) {
      final String path = "packer_partb_"+(i+1)+".xml";
      mTexturePack[i] = mTexturePackLoaderFromSource.load(path);
      mTexturePack[i].loadTexture();
    }
    
    mBackGroundTextureRegion =
        mTexturePack[0].getTexturePackTextureRegionLibrary().get(
            Vol3OsyougatsuResource.packer_partb_1.A3_12_IPHONE_HAIKEI_ID);

    mKakejikuTiledTexture = getTiledTextureFromPacker(mTexturePack[0],
            Vol3OsyougatsuResource.packer_partb_1.A3_7_1_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_7_2_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_7_3_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_7_4_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_7_5_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_7_6_IPHONE_KAKEJIKU_ID);

    mDoorTiledTexture = getTiledTextureFromPacker(mTexturePack[0],
            Vol3OsyougatsuResource.packer_partb_1.A3_8_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_8A_9_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_8B_7_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_1.A3_8C_IPHONE_FUSUMA_ID);

    mDoorATiledTexture = getTiledTextureFromPacker(mTexturePack[1],
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_1_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_2_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_3_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_4_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_5_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_6_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_7_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_2.A3_8A_8_IPHONE_FUSUMA_ID);
    mDoorBTiledTexture =  getTiledTextureFromPacker(mTexturePack[2],
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_1_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_2_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_3_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_4_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_5_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_3.A3_8B_6_IPHONE_FUSUMA_ID);

    mDoorCTiledTexture = getTiledTextureFromPacker(mTexturePack[3],
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_1_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_2_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_3_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_4_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_5_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_1_6_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_2_1_IPHONE_FUSUMA_ID,
            Vol3OsyougatsuResource.packer_partb_4.A3_8C_2_2_IPHONE_FUSUMA_ID);
    mNusuTiledTexture = getTiledTextureFromPacker(mTexturePack[4],
            Vol3OsyougatsuResource.packer_partb_5.A3_9A_1_IPHONE_NASU_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9A_2_IPHONE_NASU_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9A_3_IPHONE_NASU_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9A_4_IPHONE_NASU_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9A_5_IPHONE_NASU_ID);
    mToriTiledTexture = getTiledTextureFromPacker(mTexturePack[4],
            Vol3OsyougatsuResource.packer_partb_5.A3_9B_1_IPHONE_TORI_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9B_2_IPHONE_TORI_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9B_3_IPHONE_TORI_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9B_4_IPHONE_TORI_ID,
            Vol3OsyougatsuResource.packer_partb_5.A3_9B_5_IPHONE_TORI_ID);
    mFujisanTiledTexture = getTiledTextureFromPacker(mTexturePack[6],
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_1_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_2_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_3_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_4_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_5_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_6_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_7_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_8_IPHONE_FUJISAN_ID,
            Vol3OsyougatsuResource.packer_partb_7.A3_9C_9_IPHONE_FUJISAN_ID);
    mMochiTiledTexture = new ITiledTextureRegion[] {
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_1_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_1_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_2_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_2_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_3_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_3_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_4_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_4_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_5_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_5_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_6_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_6_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_7_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_7_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_8_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_8_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_9_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_9_IPHONE_MOCHI_ID),
            getTiledTextureFromPacker(mTexturePack[0],
                Vol3OsyougatsuResource.packer_partb_1.A3_10A_10_IPHONE_MOCHI_ID,
                Vol3OsyougatsuResource.packer_partb_1.A3_10B_10_IPHONE_MOCHI_ID)
        };
    
    mFlashEndTiledTexture = getTiledTextureFromPacker(mTexturePack[5],
            Vol3OsyougatsuResource.packer_partb_6.A3_11_1_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_2_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_3_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_4_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_5_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_6_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_7_IPHONE_KAKEJIKU_ID,
            Vol3OsyougatsuResource.packer_partb_6.A3_11_8_IPHONE_KAKEJIKU_ID);
    
    Log.i(TAG, "-- end loadTexture --");
  }

  @Override
  protected void drawScene() {
    Log.i(TAG, "-- drawScene --");
    ranDomNoRepeat = new RanDomNoRepeat();
    spriteBackGround =
        new Sprite(960/2 - mBackGroundTextureRegion.getWidth()/2, 0, mBackGroundTextureRegion, mVertexBufferObjectManager);
    this.attachChild(spriteBackGround);
    
    mKakejikuAniSprite = new AnimatedSprite(164, 1, mKakejikuTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mKakejikuAniSprite);
    
    mDoorAniSprite = new AnimatedSprite(404, -60, mDoorTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mDoorAniSprite);
    
    mDoorAAniSprite = new AnimatedSprite(404, -60, mDoorATiledTexture, mVertexBufferObjectManager);
    this.attachChild(mDoorAAniSprite);
    mDoorAAniSprite.setVisible(false);
    mDoorAAniSprite.setScaleCenter(mDoorAAniSprite.getWidth()/2, mDoorAAniSprite.getHeight()/2);
    
    mDoorBAniSprite = new AnimatedSprite(404, -60, mDoorBTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mDoorBAniSprite);
    mDoorBAniSprite.setVisible(false);
    
    mDoorCAniSprite = new AnimatedSprite(404, -60, mDoorCTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mDoorCAniSprite);
    mDoorCAniSprite.setVisible(false);
    
    mNusuAniSprite[0] = new AnimatedSprite(0, 0, mNusuTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mNusuAniSprite[0]);
    mNusuAniSprite[0].setVisible(false);
    
    mNusuAniSprite[1] = new AnimatedSprite(0, 0, mNusuTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mNusuAniSprite[1]);
    mNusuAniSprite[1].setVisible(false);
    
    mToriAniSprite[0] = new AnimatedSprite(0, 0, mToriTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mToriAniSprite[0]);
    mToriAniSprite[0].setVisible(false);
    
    mToriAniSprite[1] = new AnimatedSprite(0, 0, mToriTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mToriAniSprite[1]);
    mToriAniSprite[1].setVisible(false);
    
    mFujisanAniSprite = new AnimatedSprite(0, 0, mFujisanTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mFujisanAniSprite);
    mFujisanAniSprite.setVisible(false);
    
    mBoxSprite = new Sprite(364, 442,
      mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partb_1.A3_10_1_IPHONE_SANPOW_ID),
      mVertexBufferObjectManager);
    this.attachChild(mBoxSprite);
    mSanpowSprite = new Sprite(662, 408,
      mTexturePack[0].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partb_1.A3_10_2_IPHONE_SANPOW_ID),
      mVertexBufferObjectManager);
    this.attachChild(mSanpowSprite);
    
    for (int i = 0; i < mMochiAniSprite.length; i++) {
      float pX = mSanpowSprite.getX() + mSanpowSprite.getWidth()/2 - mMochiTiledTexture[i].getWidth()/2;
      float pY = mSanpowSprite.getY() + mSanpowSprite.getHeight()/2 - mMochiTiledTexture[i].getHeight()/2;
      mMochiAniSprite[i] = new MochiAniSprite(i,pX, pY, mMochiTiledTexture[i], mVertexBufferObjectManager);
      this.attachChild(mMochiAniSprite[i]);
      mMochiAniSprite[i].setVisible(false);
    }
    
    mArrowSprite = new Sprite(498, 326,
      mTexturePack[4].getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_partb_5.A3_13_IPHONE_YAJIRUSHI_ID),
      mVertexBufferObjectManager);
    this.attachChild(mArrowSprite);
    mArrowSprite.setVisible(false);
    
    mColCenter = new Rectangle(mBoxSprite.getX()+ mBoxSprite.getWidth()/2 - 25, 0, 50, 640, mVertexBufferObjectManager);
    this.attachChild(mColCenter);
    mColCenter.setAlpha(0.0f);
    
    mColLeft = new Rectangle(mColCenter.getX() - 40, 0, 40, 640, mVertexBufferObjectManager);
    this.attachChild(mColLeft);
    mColLeft.setColor(Color.RED);
    mColLeft.setAlpha(0.0f);
    
    mColRight = new Rectangle(mColCenter.getX() + mColCenter.getWidth(), 0, 40, 640, mVertexBufferObjectManager);
    this.attachChild(mColRight);
    mColRight.setColor(Color.RED);
    mColRight.setAlpha(0.0f);
    
    mMochiTouch = new MochiTouch();
    this.attachChild(mMochiTouch);
    this.registerTouchArea(mMochiTouch);
    
    SceneTouch sceneTouch = new SceneTouch();
    this.attachChild(sceneTouch);
    this.registerTouchArea(sceneTouch);
    sceneTouch.setAlpha(0.0f);
    
    mFlashEndAniSprite = new AnimatedSprite(960/2 - mFlashEndTiledTexture.getWidth(0)/2, -6, mFlashEndTiledTexture, mVertexBufferObjectManager);
    this.attachChild(mFlashEndAniSprite);
    mFlashEndAniSprite.setVisible(false);
    
  }
  
  public boolean isPreView() {
    return isPreView;
  }
  
  @Override
  public void preView() {
    Log.i(TAG, "-- preView --");
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
  
  class SceneTouch extends Rectangle{

    public SceneTouch() {
      super(0, 0, 960 , 640, mVertexBufferObjectManager);
    }
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
        float pTouchAreaLocalY) {
     
      if(pSceneTouchEvent.isActionDown()){
        float pX = pSceneTouchEvent.getX();
        float pY = pSceneTouchEvent.getY();
        if(mSanpowSprite.contains(pX, pY)){
          // todo nothing
        }else
        if(checkContains(mKakejikuAniSprite, 0, 0, 72, 63, (int)pX, (int)pY)&& !mKakejikuAniSprite.isAnimationRunning()){
          actionKakejiku();
        }else
        if(checkContains(mDoorAniSprite, 126, 60, 446, 352, (int)pX, (int)pY) && !isActionDoor){
          actionDoor();
        }else
        if(checkContains(spriteBackGround, 15, 295, 955, 638, (int)pX, (int)pY)){
          actionTabToFront();
        }
    }
      return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }
  }
  
  @Override
  public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
   
      if(pSceneTouchEvent.isActionUp()){
        mMochiTouch.onAreaTouched(pSceneTouchEvent, 0, 0);
      }
    return super.onSceneTouchEvent(pSceneTouchEvent);
  }
  
  class MochiTouch extends RectangularShape{
    private int MOCHI_ID = -1;
    private float lastPoint = 0;
    public MochiTouch() {
      super(0, 0, 960 , 640, null);
    }
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
        float pTouchAreaLocalY) {
      float pX = pSceneTouchEvent.getX();
      float pY = pSceneTouchEvent.getY();
      
      if(pSceneTouchEvent.isActionDown()){
        Log.i(TAG, "MochiTouch isActionDown");
        lastPoint = pX + pY;
          for (int i = 0; i < mMochiAniSprite.length; i++) {
            if(mMochiAniSprite[i].contains(pX, pY)){
              if(mMochiAniSprite[i].isVisible() && mMochiAniSprite[i].isTouch){
                MOCHI_ID = i;
                mArrowSprite.setVisible(false);
                mArrowSprite.clearEntityModifiers();
                return true;
              }
            }
          }
      }else if(pSceneTouchEvent.isActionMove() && MOCHI_ID >= 0 && Math.abs(lastPoint - (pX + pY)) >= 20){
        if(mMochiAniSprite[MOCHI_ID].getCurrentTileIndex() != 1){
          Vol3Osyougatsu.OGG_A3_B_10_NYUN.play();
          mMochiAniSprite[MOCHI_ID].setCurrentTileIndex(1);
          mMochiAniSprite[MOCHI_ID].setWidth(mMochiTiledTexture[MOCHI_ID].getWidth(1));
          mMochiAniSprite[MOCHI_ID].setHeight(mMochiTiledTexture[MOCHI_ID].getHeight(1));
              
          Log.i(TAG, "MochiTouch isActionMove");
        }
        mMochiAniSprite[MOCHI_ID].setPosition(pX - mMochiAniSprite[MOCHI_ID].getWidth()/2, pY - mMochiAniSprite[MOCHI_ID].getHeight()/2);
      }else if(pSceneTouchEvent.isActionUp()){
        // check
        Log.i(TAG, "MochiTouch isActionUp");
        if(MOCHI_ID >= 0){
          try {
            mMochiAniSprite[MOCHI_ID].actionMoveUp();
          } catch (Exception e) {
            
          }
         
        }
        MOCHI_ID = -1;
      }
      return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    @Override
    public IVertexBufferObject getVertexBufferObject() {
      return null;
    }

    @Override
    protected void onUpdateVertices() {
    }
    
  }
  
  //=====================================================
  // Function
  //=====================================================
  
  private static final int POINT_NUSU[][] = new int[][]{
                {120,400} , {130,600} , {500,400}                                   
  };
  
  private void actionTabToFront(){
      int ran = new Random().nextInt(2);
      if(countFujisan == 10){
        ran = 2;
      }
      
      if(ran == 0 && !mNusuAniSprite[1].isVisible()){ // Nusu
        countFujisan ++;
        int ranPoint = new Random().nextInt(POINT_NUSU.length);
        mNusuAniSprite[1].setPosition(POINT_NUSU[ranPoint][0], -mNusuAniSprite[1].getHeight());
        mNusuAniSprite[1].setCurrentTileIndex(0);
        mNusuAniSprite[1].setVisible(true);
        final float offsetDuration = 1600/960;
        final float pY = Math.abs(-mNusuAniSprite[1].getWidth() - mNusuAniSprite[1].getX());
        mNusuAniSprite[1].registerEntityModifier(new MoveYModifier(1.0f, mNusuAniSprite[1].getY(), mNusuAniSprite[1].getY() + POINT_NUSU[ranPoint][1], new IEntityModifierListener() {
          
          @Override
          public void onModifierStarted(IModifier<IEntity> modifier, IEntity entity) {
            Vol3Osyougatsu.OGG_A3_B_9A_ONIPANTS.play();
          }
          
          @Override
          public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
            Vol3Osyougatsu.OGG_A3_B_9A_POKA.play();
            mNusuAniSprite[1].animate(new long[]{150,250}, new int[]{1,2},0);
            mNusuAniSprite[1].registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(0.4f),
                new MoveXModifier((offsetDuration*pY)/1000 , mNusuAniSprite[1].getX(), -mNusuAniSprite[1].getWidth(), new IEntityModifierListener() {
                  
                  @Override
                  public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                    mNusuAniSprite[1].animate(new long[]{250,250}, new int[]{3,4},1);
                  }
                  
                  @Override
                  public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    mNusuAniSprite[1].stopAnimation();
                    mNusuAniSprite[1].setVisible(false);
                  }
                })
                ));
          }
        }));
    }else if(ran == 0 && !mNusuAniSprite[0].isVisible()){ // Nusu
          countFujisan ++;
          int ranPoint = new Random().nextInt(POINT_NUSU.length);
          mNusuAniSprite[0].setPosition(POINT_NUSU[ranPoint][0], -mNusuAniSprite[0].getHeight());
          mNusuAniSprite[0].setCurrentTileIndex(0);
          mNusuAniSprite[0].setVisible(true);
          final float offsetDuration = 1600/960;
          final float pY = Math.abs(-mNusuAniSprite[0].getWidth() - mNusuAniSprite[0].getX());
          mNusuAniSprite[0].registerEntityModifier(new MoveYModifier(1.0f, mNusuAniSprite[0].getY(), mNusuAniSprite[0].getY() + POINT_NUSU[ranPoint][1], new IEntityModifierListener() {
            
            @Override
            public void onModifierStarted(IModifier<IEntity> modifier, IEntity entity) {
              Vol3Osyougatsu.OGG_A3_B_9A_ONIPANTS.play();
            }
            
            @Override
            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
              Vol3Osyougatsu.OGG_A3_B_9A_POKA.play();
              mNusuAniSprite[0].animate(new long[]{150,250}, new int[]{1,2},0);
              mNusuAniSprite[0].registerEntityModifier(new SequenceEntityModifier(
                  new DelayModifier(0.4f),
                  new MoveXModifier((offsetDuration*pY)/1000 , mNusuAniSprite[0].getX(), -mNusuAniSprite[0].getWidth(), new IEntityModifierListener() {
                    
                    @Override
                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                      mNusuAniSprite[0].animate(new long[]{250,250}, new int[]{3,4},1);
                    }
                    
                    @Override
                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                      mNusuAniSprite[0].stopAnimation();
                      mNusuAniSprite[0].setVisible(false);
                    }
                  })
                  ));
            }
          }));
      }else if(ran == 1 && !mToriAniSprite[0].isVisible()){ // Tori
        
        countFujisan ++;
        int ranPoint = new Random().nextInt(POINT_NUSU.length);
        mToriAniSprite[0].setPosition(POINT_NUSU[ranPoint][0], -mToriAniSprite[0].getHeight());
        mToriAniSprite[0].setCurrentTileIndex(0);
        mToriAniSprite[0].setVisible(true);
        final float offsetDuration = 1600/960;
        final float pY = Math.abs(-mToriAniSprite[0].getWidth() - mToriAniSprite[0].getX());
        mToriAniSprite[0].registerEntityModifier(new MoveYModifier(1.0f, mToriAniSprite[0].getY(), mToriAniSprite[0].getY() + POINT_NUSU[ranPoint][1], new IEntityModifierListener() {
          
          @Override
          public void onModifierStarted(IModifier<IEntity> modifier, IEntity entity) {
            Vol3Osyougatsu.OGG_A3_B_9B_DON.play();
          }
          
          @Override
          public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
            mToriAniSprite[0].animate(new long[]{150,250}, new int[]{1,2},0);
            mToriAniSprite[0].registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(0.4f),
                new MoveXModifier((offsetDuration*pY)/1000 , mToriAniSprite[0].getX(), -mToriAniSprite[0].getWidth(), new IEntityModifierListener() {
                  
                  @Override
                  public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                    mToriAniSprite[0].animate(new long[]{250,250}, new int[]{3,4},1);
                  }
                  
                  @Override
                  public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    mToriAniSprite[0].stopAnimation();
                    mToriAniSprite[0].setVisible(false);
                  }
                })
                ));
          }
        }));
      }else if(ran == 1 && !mToriAniSprite[1].isVisible()){ // Tori
        
        countFujisan ++;
        int ranPoint = new Random().nextInt(POINT_NUSU.length);
        mToriAniSprite[1].setPosition(POINT_NUSU[ranPoint][1], -mToriAniSprite[1].getHeight());
        mToriAniSprite[1].setCurrentTileIndex(0);
        mToriAniSprite[1].setVisible(true);
        final float offsetDuration = 1600/960;
        final float pY = Math.abs(-mToriAniSprite[1].getWidth() - mToriAniSprite[1].getX());
        mToriAniSprite[1].registerEntityModifier(new MoveYModifier(1.0f, mToriAniSprite[1].getY(), mToriAniSprite[1].getY() + POINT_NUSU[ranPoint][1], new IEntityModifierListener() {
          
          @Override
          public void onModifierStarted(IModifier<IEntity> modifier, IEntity entity) {
            Vol3Osyougatsu.OGG_A3_B_9B_DON.play();
          }
          
          @Override
          public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
            mToriAniSprite[1].animate(new long[]{150,250}, new int[]{1,2},0);
            mToriAniSprite[1].registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(0.4f),
                new MoveXModifier((offsetDuration*pY)/1000 , mToriAniSprite[1].getX(), -mToriAniSprite[1].getWidth(), new IEntityModifierListener() {
                  
                  @Override
                  public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                    mToriAniSprite[1].animate(new long[]{250,250}, new int[]{3,4},1);
                  }
                  
                  @Override
                  public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    mToriAniSprite[1].stopAnimation();
                    mToriAniSprite[1].setVisible(false);
                  }
                })
                ));
          }
        }));
      }else if(ran == 2 && !mFujisanAniSprite.isVisible()){ //Fujisan
        mFujisanAniSprite.animate(new long[]{250,250,250,250,250,250,250,250,250,250,250},
            new int[]{0,1,2,3,4,5,6,7,8,8,8},0, new IAnimationListener() {
              int countFrame = 0;
              @Override
              public void onAnimationStarted(AnimatedSprite animatedSprite, int arg1) {
                animatedSprite.setPosition(960/2 - mFujisanTiledTexture.getWidth(0)/2,
                  640/2 - mFujisanTiledTexture.getHeight(0)/2);
                animatedSprite.setWidth(mFujisanTiledTexture.getWidth(0));
                animatedSprite.setHeight(mFujisanTiledTexture.getHeight(0));
                animatedSprite.setAlpha(1.0f);
                mFujisanAniSprite.setVisible(true);
                Vol3Osyougatsu.OGG_A3_B_9_DONPUSYU2.play();
              }
              
              @Override
              public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
                
              }
              
              @Override
              public void onAnimationFrameChanged(AnimatedSprite animatedSprite, int arg1, int arg2) {
                countFrame++;
                if(countFrame == 10){
                  animatedSprite.setAlpha(0.8f);
                }else if(countFrame == 11){
                  animatedSprite.setAlpha(0.5f);
                }
                int frame = countFrame - 1 >= 8 ? 8 : countFrame - 1;
                animatedSprite.setPosition(960/2 - mFujisanTiledTexture.getWidth(frame)/2,
                  640/2 - mFujisanTiledTexture.getHeight(frame)/2);
                animatedSprite.setWidth(mFujisanTiledTexture.getWidth(frame));
                animatedSprite.setHeight(mFujisanTiledTexture.getHeight(frame));
                Log.i(TAG, "countFrame " + countFrame + "/" + arg1 + "/" + frame);
              }
              
              @Override
              public void onAnimationFinished(AnimatedSprite arg0) {
                mFujisanAniSprite.setVisible(false);
                countFujisan = 0;
              }
            }
            );
      }
  }
  
  private void actionDoor(){
    isActionDoor = true;
    int ran = new Random().nextInt(3);
    Vol3Osyougatsu.OGG_A3_B_8_SYUHUSUMA2.play();
    if(ran == 0){ // Action A
      mDoorAAniSprite.animate(
        new long[]{250,250,250,250,250,250,250,250,250,250,250,250,250,250},
        new int[]{0,1,2,3,4,5,6,7,6,7,6,7,6,7},0,
        new IAnimationListener() {
        int count = 0;
        @Override
        public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
          mDoorAniSprite.setCurrentTileIndex(1);
          mDoorAAniSprite.setScale(1.0f);
          mDoorAAniSprite.setVisible(true);
          Vol3Osyougatsu.OGG_A3_B_8_ASOBANAI.play();
        }
        
        @Override
        public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
          
        }
        
        @Override
        public void onAnimationFrameChanged(AnimatedSprite animatedSprite, int arg1, int arg2) {
          count ++;
          if(count == 7){
            animatedSprite.registerEntityModifier(new ScaleModifier(2.0f, 1.0f, 0.3f));
          }
        }
        
        @Override
        public void onAnimationFinished(AnimatedSprite animatedSprite) {
          mDoorAAniSprite.setVisible(false);
          mDoorAAniSprite.clearEntityModifiers();
          mDoorAAniSprite.clearUpdateHandlers();
          mDoorAniSprite.setCurrentTileIndex(0);
          animatedSprite.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
            
            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
              
            }
            
            @Override
            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
              isActionDoor = false;
            }
          }));
         
        }
      });
    }else if(ran == 1){ // Action B
        mDoorBAniSprite.animate(new long[] {1000,250,250,200,200,200},0,new IAnimationListener() {
          
          @Override
          public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
            mDoorAniSprite.setCurrentTileIndex(2);
            mDoorBAniSprite.setVisible(true);
            Vol3Osyougatsu.OGG_A3_B_8_YUBINDESU3.play();
          }
          
          @Override
          public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
          }
          
          @Override
          public void onAnimationFrameChanged(AnimatedSprite animatedSprite, int arg1, int arg2) {
              if(arg2 == 1){
                Vol3Osyougatsu.OGG_A3_B_8_WA4.play();
              }
          }
          
          @Override
          public void onAnimationFinished(AnimatedSprite animatedSprite) {
            mDoorBAniSprite.setVisible(false);
            mDoorAniSprite.setCurrentTileIndex(0);
            animatedSprite.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
              
              @Override
              public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                
              }
              
              @Override
              public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                isActionDoor = false;
              }
            }));
          }
        });
    }else if(ran == 2){ // Action C
      int ranTypeC = new Random().nextInt(2);
      long duration[] = new long[] {350,350,350,350,350,350};
      int frame[] = new int[]{0,1,2,3,4,5};
      if(ranTypeC ==1 ){
        frame = new int[]{6,7};
        duration = new long[]{1000,1000};
      }
      mDoorCAniSprite.animate(duration,frame,0, new IAnimationListener() {
        
        @Override
        public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
          mDoorAniSprite.setCurrentTileIndex(3);
          mDoorCAniSprite.setVisible(true);
          Vol3Osyougatsu.OGG_A3_B_8_NEZUMI.play();
        }
        
        @Override
        public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {         
        }
        
        @Override
        public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {          
        }
        
        @Override
        public void onAnimationFinished(AnimatedSprite animatedSprite) {
          mDoorCAniSprite.setVisible(false);
          mDoorAniSprite.setCurrentTileIndex(0);
          animatedSprite.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
            
            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
              
            }
            
            @Override
            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
              isActionDoor = false;
            }
          }));
        }
      });
      
    }
  }
  
  private void actionKakejiku(){
    Vol3Osyougatsu.OGG_A3_B_7_SYU2.play();
    mKakejikuAniSprite.animate(new long[]{150,150,150,150,150,400,100} ,new int[]{0,1,2,3,4,5,0},0);
    mKakejikuAniSprite.registerUpdateHandler(new TimerHandler(1.2f, new ITimerCallback() {
      
      @Override
      public void onTimePassed(TimerHandler arg0) {
        if(mKakejikuAniSprite!=null ) mKakejikuAniSprite.stopAnimation(0);
      }
    }));
  }
  
  private void showArrow(){
    if(listMochiUsed.size() == 0){
      mArrowSprite.setVisible(true);
      mArrowSprite.clearEntityModifiers();
      mArrowSprite.registerEntityModifier(new LoopEntityModifier(
        new SequenceEntityModifier(
          new AlphaModifier(0.5f, 0.2f, 1.0f),
          new AlphaModifier(0.5f, 1.0f, 0.2f)
          )
      ));
    }
  }
  
  private void newActionMochi(int pMochiId){
    if(listMochiUsed.size() == mMochiAniSprite.length){  // Finish
      //listMochiUsed.clear();
      Vol3Osyougatsu.OGG_A3_B_11_HAKUSYU.play();
      Vol3Osyougatsu.OGG_A3_B_11_PON.play();
      mFlashEndAniSprite.setPosition(mFlashEndAniSprite.getX() + mFlashEndAniSprite.getWidth()/2 - mFlashEndTiledTexture.getWidth(0)/2, mFlashEndAniSprite.getY());
      mFlashEndAniSprite.animate(new long[]{500,500,500,500,500,500,500,500}, 0, new IAnimationListener() {
        
        @Override
        public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
          mFlashEndAniSprite.setZIndex(Vol3OsyougatsuBScene.this.getLastChild().getZIndex() + 1);
          Vol3OsyougatsuBScene.this.sortChildren();
          mFlashEndAniSprite.setWidth( mFlashEndTiledTexture.getWidth(0));
          mFlashEndAniSprite.setX(960/2 - mFlashEndTiledTexture.getWidth(0)/2);
          mFlashEndAniSprite.setVisible(true);
        }
        
        @Override
        public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
          
        }
        
        @Override
        public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
            Log.i(TAG, "mFlashEndAniSprite frame " + arg2);
            mFlashEndAniSprite.setWidth( mFlashEndTiledTexture.getWidth(arg2));
            mFlashEndAniSprite.setX(960/2 - mFlashEndTiledTexture.getWidth(arg2)/2);
        }
        
        @Override
        public void onAnimationFinished(AnimatedSprite arg0) {
          mFlashEndAniSprite.setVisible(false);
          for (int i = 0; i < listMochiUsed.size(); i++) {
            listMochiUsed.get(i).resetAll();
          }
          listMochiUsed.clear();
          pointer_mochi = 465;
          newActionMochi(-1);
        }
      });
      
    }else{  // New Event
      if(pMochiId < 0){
        pMochiId = ranDomNoRepeat.getNextIntNoRepeat(true);
      }
      mMochiAniSprite[pMochiId].newEvent();
      showArrow();
    }
    
  }

  @Override
  public void onResumeGame() {
    // XXX onResumeGame
    pointer_mochi = 465;
    countFujisan = 0;
    ranDomNoRepeat.setLenghNoRepeat(mMochiAniSprite.length);
    newActionMochi(-1);
    Log.i(TAG, "-- onResumeGame --");
  }

  @Override
  public void onPauseGame() {
    // XXX onPauseGame
    if(!isDrawView || !isPreView){
      return;
    }
    this.clearUpdateHandlers();
    listMochiUsed.clear();
    ranDomNoRepeat.clearTmp();
    countFujisan = 0;
    isActionDoor = false;
    for (int i = 0; i < 2; i++) {
      mToriAniSprite[i].stopAnimation(0);
      mToriAniSprite[i].setVisible(false);
      mToriAniSprite[i].clearUpdateHandlers();
      mToriAniSprite[i].clearEntityModifiers();
      mNusuAniSprite[i].stopAnimation(0);
      mNusuAniSprite[i].setVisible(false);
      mNusuAniSprite[i].clearUpdateHandlers();
      mNusuAniSprite[i].clearEntityModifiers();
    }
    for (int i = 0; i < mMochiAniSprite.length; i++) {
      mMochiAniSprite[i].resetAll();
    }
    mFlashEndAniSprite.setVisible(false);
    mFlashEndAniSprite.clearEntityModifiers();
    mFlashEndAniSprite.stopAnimation(0);
    mArrowSprite.setVisible(false);
    mArrowSprite.clearEntityModifiers();
    mFujisanAniSprite.setVisible(false);
    mFujisanAniSprite.clearEntityModifiers();
    mFujisanAniSprite.stopAnimation(0);
    mDoorCAniSprite.stopAnimation(0);
    mDoorCAniSprite.clearEntityModifiers();
    mDoorCAniSprite.clearUpdateHandlers();
    mDoorCAniSprite.setVisible(false);
    mDoorBAniSprite.stopAnimation(0);
    mDoorBAniSprite.setVisible(false);
    mDoorAAniSprite.clearEntityModifiers();
    mDoorAAniSprite.clearUpdateHandlers();
    mDoorAAniSprite.setScale(1.0f);
    mDoorAAniSprite.setVisible(false);
    mDoorAAniSprite.stopAnimation(0);
    mKakejikuAniSprite.stopAnimation(0);
    mDoorAniSprite.clearEntityModifiers();
    mDoorAniSprite.clearUpdateHandlers();
    mDoorAniSprite.stopAnimation(0);
    
    Log.i(TAG, "-- onPauseGame --");
  }
  
  //=====================================================
  // Class Mochi
  //=====================================================
  
  class MochiAniSprite extends AnimatedSprite{
    
    private static final int DIREC_LEFT = 1;
    private static final int DIREC_RIGHT = 2;
    
    private boolean isTouch  = true;
    private int index = -1;
    private int direction = -1;
      
    public MochiAniSprite(int pIndex, float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
        VertexBufferObjectManager pVertexBufferObjectManager) {
      super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
      this.index = pIndex;
      setRotationCenter(this.getWidth()/2, this.getHeight()/2);
    }
    
    public void newEvent(){
        setCurrentTileIndex(0);
        resetLocalToFirst();
        setVisible(true);
        setRotation(1.0f);
        isTouch = true;
        int maxZindex = Vol3OsyougatsuBScene.this.getLastChild().getZIndex() + 1;
        setZIndex(maxZindex);
        Vol3OsyougatsuBScene.this.sortChildren();
    }
    
    public void actionMoveUp(){
      Log.i(TAG, "-- actionMoveUp --");
      isTouch = false;
      float pX = this.getX() + this.getWidth()/2;
      float pY = this.getY() + this.getHeight()/2;
     /* this.setCurrentTileIndex(0);
      this.setWidth(mMochiTiledTexture[index].getWidth(0));
      this.setHeight(mMochiTiledTexture[index].getHeight(0));*/
      direction = (this.getX() + this.getWidth()/2) >= (mColCenter.getX() + mColCenter.getWidth()/2) ? DIREC_RIGHT : DIREC_LEFT;
      Log.i(TAG, "Direction " + direction);
      if(mColCenter.contains(pX, pY)){
        
        if(pointer_mochi - (this.getY() + this.getHeight()) >= 5){
          listMochiUsed.add(this);
          float offset = 15;
          float offsetToY = (pointer_mochi - this.getHeight() + offset) - this.getY();
          pointer_mochi = pointer_mochi - this.getHeight() + offset;
          Log.i(TAG, "Add new id " + index);
          float duration = 1500/650 * offsetToY / 1000;
          this.clearEntityModifiers();
          this.registerEntityModifier(new MoveYModifier(duration, this.getY(), pointer_mochi, new IEntityModifierListener() {
            
            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
              
            }
            
            @Override
            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
              Vol3Osyougatsu.OGG_A3_B_7_SYU2.play();
              newActionMochi(-1);
            }
          }));
          
        }else{
          actionUpFall();
        }
        
      }else
      if(mColLeft.contains(pX, pY) || mColRight.contains(pX, pY)){
        /*MochiAniSprite.this.resetAll();
        newActionMochi(index);*/

        if(pointer_mochi - (this.getY() + this.getHeight()) >= 10){
          float offset = 15;
          float offsetToY = (pointer_mochi - this.getHeight() + offset) - this.getY();
          pointer_mochi = pointer_mochi - this.getHeight() + offset;
          Log.i(TAG, "Add new id " + index);
          float duration = 1500/650 * offsetToY / 1000;
          this.clearEntityModifiers();
          this.registerEntityModifier(new MoveYModifier(duration, this.getY(), pointer_mochi, new IEntityModifierListener() {
            
            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
              
            }
            
            @Override
            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                // Big Fall Action
              
                Log.i(TAG, "Big Fall Action"); 
              
                Vol3Osyougatsu.OGG_A3_B_10_MOKIN.play();
                float toX = direction == DIREC_RIGHT ? 250 : -250;
                float roate = direction == DIREC_RIGHT ? 45 : -45;
                MochiAniSprite.this.registerEntityModifier(new ParallelEntityModifier(
                    new MoveModifier(1.0f, MochiAniSprite.this.getX(),MochiAniSprite.this.getX() + toX, MochiAniSprite.this.getY(), MochiAniSprite.this.getHeight() + 640),
                    new RotationModifier(1.0f, 0, roate)
                  ));
                for (int i = 0; i < listMochiUsed.size(); i++) {
                    final MochiAniSprite aniSprite = listMochiUsed.get(i);
                    toX = aniSprite.direction == DIREC_RIGHT ? 250 : -250;
                    roate = aniSprite.direction == DIREC_RIGHT ? 45 : -45;
                    aniSprite.registerEntityModifier(new ParallelEntityModifier(
                      new MoveModifier(1.0f, aniSprite.getX(), aniSprite.getX() + toX, aniSprite.getY(), aniSprite.getHeight() + 640),
                      new RotationModifier(1.0f, 0, roate)
                    ));
                }
                
                Vol3OsyougatsuBScene.this.registerUpdateHandler(new TimerHandler(1.2f, new ITimerCallback() {
                  
                  @Override
                  public void onTimePassed(TimerHandler arg0) {
                    for (int i = 0; i < listMochiUsed.size(); i++) {
                      listMochiUsed.get(i).resetAll();
                    }
                    listMochiUsed.clear();
                    pointer_mochi = 465;
                    ranDomNoRepeat.clearTmp();
                    newActionMochi(-1);
                    Log.i(TAG, "End Big Fall Action");
                  }
                }));
                
                // End Big Fall Action
            }
          }));
          
        }else{
          actionUpFall();
        }
        
      }else{
        actionUpFall();
      }
    }
    
    private void actionUpFall(){
      float offsetToY = 650  - this.getY();
      float duration = 1500/650 * offsetToY / 1000;
      this.clearEntityModifiers();
      this.registerEntityModifier(new MoveYModifier(duration, this.getY(), 650, new IEntityModifierListener() {
       
       @Override
       public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
       }
       
       @Override
       public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
         Vol3Osyougatsu.context.runOnUpdateThread(new Runnable() {
           
           @Override
           public void run() {
             MochiAniSprite.this.resetAll();
             newActionMochi(index);
           }
         });
           
       }
     }));
    }
    
    public void resetAll(){
      isTouch  = true;
      setRotation(1.0f);
      clearUpdateHandlers();
      clearEntityModifiers();
      setVisible(false);
      setRotation(1.0f);
      stopAnimation(0);
    }
    
  }

}
