package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OsyougatsuResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Osyougatsu extends BaseGameActivity implements IOnSceneTouchListener{
  
  private static final String TAG = "VOL3OSYOUGATSUA";
  
  private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
  private TexturePack tpGimic;
  private GimicSprite mGimicSprite[] = new GimicSprite[3];
  
  private HUD mHud;
  // Scene
  private Vol3OsyougatsuAScene mPartAScene;
  private Vol3OsyougatsuBScene mPartBScene;
  private Vol3OsyougatsuCScene mPartCScene;
  
  public static Vol3Osyougatsu context;
  
  public static Sound OGG_A3_A_4_KACHI2,OGG_A3_A_4_SUZU2,
                OGG_A3_A_5_IYO,OGG_A3_A_5_KOMA1,OGG_A3_A_5_KOMA2,OGG_A3_A_5_KOMA3,OGG_A3_A_5_KOMA4,OGG_A3_A_5_KOMATEI,
                OGG_A3_A_5_KOMATEI2,OGG_A3_A_5_KOMATEI3,OGG_A3_A_5_KOMATEI4,OGG_A3_A_5_SPACE6,OGG_A3_A_6_GO,OGG_A3_A_6_KACHIN,
                OGG_A3_B_10_MOKIN,OGG_A3_B_10_NYUN,OGG_A3_B_11_HAKUSYU,OGG_A3_B_11_PON,OGG_A3_B_7_SYU2,OGG_A3_B_8_ASOBANAI,OGG_A3_B_8_NEZUMI,
                OGG_A3_B_8_SYUHUSUMA2,OGG_A3_B_8_WA4,OGG_A3_B_8_YUBINDESU3,OGG_A3_B_9A_ONIPANTS,OGG_A3_B_9A_POKA,OGG_A3_B_9B_DON,
                OGG_A3_B_9_DONPUSYU2,OGG_A3_C_12_AME_LONG,OGG_A3_C_12_AME_SHORT,OGG_A3_C_13_PYU2,OGG_A3_C_14A_KAMOME,
                OGG_A3_C_14B_POWAN,OGG_A3_C_14C_BOCYAN,OGG_A3_C_15A_SYUTA,OGG_A3_C_15B_UN,OGG_A3_C_16_CYAPA,OGG_A3_C_16_PYONBASYAN;
 
  
  @Override
  public void onCreateResources() {
    Log.i(TAG, "----------onCreateResources------------------------------");
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("osyougatsu/gfx/");
    mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "osyougatsu/gfx/");
    SoundFactory.setAssetBasePath("osyougatsu/mfx/");
    Log.i(TAG, "----------End onCreateResources------------------------------");
    context = Vol3Osyougatsu.this;
    super.onCreateResources();
  }
  
  @Override
  protected void loadKaraokeResources() {
    tpGimic = mTexturePackLoaderFromSource.load("packer_gimic.xml");
    tpGimic.loadTexture();
    
    mPartAScene = new Vol3OsyougatsuAScene(mTexturePackLoaderFromSource, 
      getVertexBufferObjectManager(), getTextureManager());
    mPartBScene = new Vol3OsyougatsuBScene(mTexturePackLoaderFromSource, 
      getVertexBufferObjectManager(), getTextureManager());
    mPartCScene = new Vol3OsyougatsuCScene(mTexturePackLoaderFromSource, 
      getVertexBufferObjectManager(), getTextureManager());
    mPartAScene.loadResource();
    mPartBScene.loadResource();
    mPartCScene.loadResource();
  }

  @Override
  protected void loadKaraokeSound() {
    OGG_A3_A_4_KACHI2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_4_KACHI2);
    OGG_A3_A_4_SUZU2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_4_SUZU2);
    OGG_A3_A_5_IYO = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_IYO);
    OGG_A3_A_5_KOMA1 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_KOMA1);
    OGG_A3_A_5_KOMA2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_KOMA2);
    OGG_A3_A_5_KOMA3 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_KOMA3);
    OGG_A3_A_5_KOMA4 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_KOMA4);
    OGG_A3_A_5_KOMATEI = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_KOMATEI);
    OGG_A3_A_5_SPACE6 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_5_SPACE6);
    OGG_A3_A_6_GO = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_6_GO);
    OGG_A3_A_6_KACHIN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_A_6_KACHIN);
    OGG_A3_B_10_MOKIN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_10_MOKIN);
    OGG_A3_B_10_NYUN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_10_NYUN);
    OGG_A3_B_11_HAKUSYU = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_11_HAKUSYU);
    OGG_A3_B_11_PON = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_11_PON);
    OGG_A3_B_7_SYU2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_7_SYU2);
    OGG_A3_B_8_ASOBANAI = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_8_ASOBANAI);
    OGG_A3_B_8_NEZUMI = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_8_NEZUMI);
    OGG_A3_B_8_SYUHUSUMA2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_8_SYUHUSUMA2);
    OGG_A3_B_8_WA4 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_8_WA4);
    OGG_A3_B_8_YUBINDESU3 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_8_YUBINDESU3);
    OGG_A3_B_9A_ONIPANTS = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_9A_ONIPANTS);
    OGG_A3_B_9A_POKA = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_9A_POKA);
    OGG_A3_B_9B_DON = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_9B_DON);
    OGG_A3_B_9_DONPUSYU2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_B_9_DONPUSYU2);
    OGG_A3_C_12_AME_LONG = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_12_AME_LONG);
    OGG_A3_C_12_AME_SHORT = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_12_AME_SHORT);
    OGG_A3_C_13_PYU2 = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_13_PYU2);
    OGG_A3_C_14A_KAMOME = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_14A_KAMOME);
    OGG_A3_C_14B_POWAN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_14B_POWAN);
    OGG_A3_C_14C_BOCYAN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_14C_BOCYAN);
    OGG_A3_C_15A_SYUTA = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_15A_SYUTA);
    OGG_A3_C_15B_UN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_15B_UN);
    OGG_A3_C_16_CYAPA = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_16_CYAPA);
    OGG_A3_C_16_PYONBASYAN = loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_C_16_PYONBASYAN);
  }

  @Override
  protected void loadKaraokeScene() {
    mHud = new HUD();
    mCamera.setHUD(mHud);
    mScene = new Scene();
    mScene.setOnSceneTouchListener(this);
    mScene.setTouchAreaBindingOnActionDownEnabled(true);
    mScene.setTouchAreaBindingOnActionMoveEnabled(true);
   
    
    mGimicSprite[0] = new GimicSprite(244, 
      tpGimic.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_gimic.A3_ABC_1_IPHONE_WADAIKO_ID),
      getVertexBufferObjectManager(), loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_ABC_1_WADAIKO), GimicSprite.GIMIC_1);
    mGimicSprite[1] = new GimicSprite(436, 
      tpGimic.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_gimic.A3_ABC_2_IPHONE_KOTO_ID),
      getVertexBufferObjectManager(), loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_ABC_2_KOTO), GimicSprite.GIMIC_2);
    mGimicSprite[2] = new GimicSprite(612, 
      tpGimic.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_gimic.A3_ABC_3_IPHONE_CHIKICHIN_ID),
      getVertexBufferObjectManager(),  loadSoundResourceFromSD(Vol3OsyougatsuResource.A3_ABC_3_CHIKICHIN), GimicSprite.GIMIC_3);
    
    mHud.attachChild(mGimicSprite[0]);
    mHud.attachChild(mGimicSprite[1]);
    mHud.attachChild(mGimicSprite[2]);
    mHud.registerTouchArea(mGimicSprite[0]);
    mHud.registerTouchArea(mGimicSprite[1]);
    mHud.registerTouchArea(mGimicSprite[2]);
    
    mPartAScene.preView();
    mGimicSprite[0].setEnableTouch(false);
    mGimicSprite[1].setEnableTouch(true);
    mGimicSprite[2].setEnableTouch(true);
    mScene.setChildScene(mPartAScene, false, true, true);
    
  }

  @Override
  public void combineGimmic3WithAction() {
    
  }

 
  
  @Override
  public synchronized void onResumeGame() {
    super.onResumeGame();
    if(mPartAScene!=null && mPartAScene.isPreView()){
      mPartAScene.onResumeGame();
    }
    if(mPartBScene!=null && mPartBScene.isPreView()){
      mPartBScene.onResumeGame();
    }
    if(mPartCScene!=null && mPartCScene.isPreView()){
      mPartCScene.onResumeGame();
    }
  }
  
  @Override
  public synchronized void onPauseGame() {
    stopSound();
    if(mPartAScene!=null && mPartAScene.isPreView()){
      mPartAScene.onPauseGame();
    }
    if(mPartBScene!=null && mPartBScene.isPreView()){
      mPartBScene.onPauseGame();
    }
    if(mPartCScene!=null && mPartCScene.isPreView()){
      mPartCScene.onPauseGame();
    }
    super.onPauseGame();
  }
  
  //////////////////////////////////////////////////////////////
  //    Class Gimic
  //////////////////////////////////////////////////////////////
  
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
      
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
              float pTouchAreaLocalX, float pTouchAreaLocalY) {
          if(pSceneTouchEvent.isActionDown() && isTouch && isTouchGimic){
              this.onTouch();
              System.gc();
              switch (GIMIC_ID) {
              case GIMIC_1:
                  stopSound();
                  mGimicSprite[0].setEnableTouch(false);
                  mGimicSprite[1].setEnableTouch(true);
                  mGimicSprite[2].setEnableTouch(true);
                  
                  
                  mPartBScene.onPauseGame();
                  mPartCScene.onPauseGame();
                  mPartCScene.back();
                  mPartBScene.back();
                  mPartCScene.destroyView();
                  mPartBScene.destroyView();
                  mPartAScene.preView();
                  mPartAScene.onResumeGame();
                  mScene.setChildScene(mPartAScene, false, true, true);
                
                  break;
              case GIMIC_2:
                stopSound();
                 mGimicSprite[0].setEnableTouch(true);
                 mGimicSprite[1].setEnableTouch(false);
                 mGimicSprite[2].setEnableTouch(true);
                 
                 mPartAScene.back();
                 mPartCScene.back();
                 mPartAScene.onPauseGame();
                 mPartCScene.onPauseGame();
                 mPartAScene.destroyView();
                 mPartCScene.destroyView();
                 mPartBScene.preView();
                 mPartBScene.onResumeGame();
                 mScene.setChildScene(mPartBScene, false, true, true);
                 
                  break;
              case GIMIC_3:
                stopSound();
                 mGimicSprite[0].setEnableTouch(true);
                 mGimicSprite[1].setEnableTouch(true);
                 mGimicSprite[2].setEnableTouch(false);
                 
                 mPartAScene.back();
                 mPartBScene.back();
                 mPartAScene.onPauseGame();
                 mPartBScene.onPauseGame();
                 mPartAScene.destroyView();
                 mPartBScene.destroyView();
                 mPartCScene.preView();
                 mPartCScene.onResumeGame();
                 mScene.setChildScene(mPartCScene, false, true, true);
                  break;
              }
              System.gc();
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
  
  private void stopSound(){
    OGG_A3_A_4_KACHI2.stop();
    OGG_A3_A_4_SUZU2 .stop();
    OGG_A3_A_5_IYO .stop();
    OGG_A3_A_5_KOMA1.stop();
    OGG_A3_A_5_KOMA2 .stop();
    OGG_A3_A_5_KOMA3.stop();
    OGG_A3_A_5_KOMA4 .stop();
    OGG_A3_A_5_KOMATEI .stop();
    OGG_A3_A_5_SPACE6 .stop();
    OGG_A3_A_6_GO .stop();
    OGG_A3_A_6_KACHIN .stop();
    OGG_A3_B_10_MOKIN.stop();
    OGG_A3_B_10_NYUN .stop();
    OGG_A3_B_11_HAKUSYU .stop();
    OGG_A3_B_11_PON .stop();
    OGG_A3_B_7_SYU2 .stop();
    OGG_A3_B_8_ASOBANAI .stop();
    OGG_A3_B_8_NEZUMI .stop();
    OGG_A3_B_8_SYUHUSUMA2 .stop();
    OGG_A3_B_8_WA4 .stop();
    OGG_A3_B_8_YUBINDESU3 .stop();
    OGG_A3_B_9A_ONIPANTS .stop();
    OGG_A3_B_9A_POKA .stop();
    OGG_A3_B_9B_DON .stop();
    OGG_A3_B_9_DONPUSYU2 .stop();
    OGG_A3_C_12_AME_LONG .stop();
    OGG_A3_C_12_AME_SHORT .stop();
    OGG_A3_C_13_PYU2 .stop();
    OGG_A3_C_14A_KAMOME .stop();
    OGG_A3_C_14B_POWAN.stop();
    OGG_A3_C_14C_BOCYAN.stop();
    OGG_A3_C_15A_SYUTA.stop();
    OGG_A3_C_15B_UN .stop();
    OGG_A3_C_16_CYAPA .stop();
    OGG_A3_C_16_PYONBASYAN .stop();
  }

  @Override
  public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
      Log.i(TAG, "----------------------");
    return false;
  }

}
