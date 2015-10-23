package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.PhysicsEditorShapeLibrary;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OsyougatsuResource;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Vol3OsyougatsuAScene extends Vol3OsyougatsuScene {

  private static final String TAG = "VOL3OSYOUGATSU---A---SCENE";
  private static final int CUBIC_ORANGE = 0;
  private static final int CUBIC_BLUE = 1;
  private static final int CUBIC_GREEN = 2;
  private static final int CUBIC_YELLOW = 3;
  private static final float POINTER_CUBIC_SLOT[][] = new float[][] {
          {150,260},
          {550,260}
  };
  
  private int indexFace = 0;
  
  private boolean CUBIC_SLOT[] = new boolean[2];
  private boolean isLoaded = false;
  private boolean isDrawView = false;
  private boolean isPreView = false;
  private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
  private VertexBufferObjectManager mVertexBufferObjectManager;
  private TextureManager mTextureManager;
  private PhysicsEditorShapeLibrary physicsEditorShapeLibrary;
  private PhysicsWorld mPhysicsWorld;
  

  private TexturePack tpBackGround;
  private TexturePack tpOne;
  private TexturePack tpTwo;
  private TexturePack tpThree;
  private TexturePack tpTab[] = new TexturePack[16];

  private BitmapTextureAtlas bitmapTexture[] = new BitmapTextureAtlas[2];
  
  private ITextureRegion mBackGroundTextureRegion;
  private ITextureRegion mScrollLeftTextureRegion;
  
  private ITiledTextureRegion mSuzuTiledTexTure;
  private ITiledTextureRegion mShishiTiledTexTure;
  private ITiledTextureRegion mCubicStartTiledTexture[] = new ITiledTextureRegion[4];
  private ITiledTextureRegion mCubicTurnTiledTexture[] = new ITiledTextureRegion[4];
  private ITiledTextureRegion mCubicStopTiledTexture[] = new ITiledTextureRegion[4];
  private ITiledTextureRegion mCubicTurnOptionTiledTexture[] = new ITiledTextureRegion[4];

  private Sprite mArrowSprite;
  private Sprite mTabSprite[] = new Sprite[16];
  private Sprite mCubicChooseSpite[] = new Sprite[4];
  private CubicEntity mCubicEntity[] = new CubicEntity[4];
  private CubicTouch cubicTouch;
  
  
  private AnimatedSprite mSuzuAniSprite;
  private AnimatedSprite mShishiAniSprite;

  private SequenceEntityModifier faceSequenceEntityModifier;
  private IUpdateHandler updateHandler;

  private static final float POINTER_FACE[][] = new float[][] {
      {73, 73, 241, 604, 904, 1083, 1083, 809, 755, 454, 210, 73},
      {242, 388, 474, 528, 473, 379, 253, 284, 116, 110, 167, 242}};

  private static final String CUBIC_BODYNAME[] = new String[]{
         "a3_6a_1_iphone_koma",  "a3_6b_1_iphone_koma", "a3_6c_1_iphone_koma", "a3_6b_1_iphone_koma"                                              
  };
  public Vol3OsyougatsuAScene(final TexturePackLoaderFromSource pTexturePackLoaderFromSource,
      VertexBufferObjectManager pVertexBufferObjectManager, final TextureManager pTextureManager) {
    mTexturePackLoaderFromSource = pTexturePackLoaderFromSource;
    mVertexBufferObjectManager = pVertexBufferObjectManager;
  }

  public void loadResource() {
    if (!isLoaded) {
      loadTexture();
      isLoaded = true;
    }
  }

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
  
  public boolean isPreView() {
    return isPreView;
  }

  public void destroyView() {
    isPreView = false;
  }

  private void loadTexture() {
    Log.i(TAG, "----------loadTexture------------------------------");
    bitmapTexture[0] = new BitmapTextureAtlas( mTextureManager, 2, 2,
      TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    bitmapTexture[1] = new BitmapTextureAtlas( mTextureManager, 2, 2,
      TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    
    tpBackGround = mTexturePackLoaderFromSource.load("packer_parta_4.xml");
    tpOne = mTexturePackLoaderFromSource.load("packer_parta_1.xml");
    tpTwo = mTexturePackLoaderFromSource.load("packer_parta_2.xml");
    tpThree = mTexturePackLoaderFromSource.load("packer_parta_3.xml");
    tpTab[0] = mTexturePackLoaderFromSource.load("packer_parta_tab_1.xml");
    tpTab[1] = mTexturePackLoaderFromSource.load("packer_parta_tab_2.xml");
    tpTab[2] = mTexturePackLoaderFromSource.load("packer_parta_tab_3.xml");
    tpTab[3] = mTexturePackLoaderFromSource.load("packer_parta_tab_4.xml");
    tpTab[4] = mTexturePackLoaderFromSource.load("packer_parta_tab_5.xml");
    tpTab[5] = mTexturePackLoaderFromSource.load("packer_parta_tab_6.xml");
    tpTab[6] = mTexturePackLoaderFromSource.load("packer_parta_tab_7.xml");
    tpTab[7] = mTexturePackLoaderFromSource.load("packer_parta_tab_8.xml");
    tpTab[8] = mTexturePackLoaderFromSource.load("packer_parta_tab_9.xml");
    tpTab[9] = mTexturePackLoaderFromSource.load("packer_parta_tab_10.xml");
    tpTab[10] = mTexturePackLoaderFromSource.load("packer_parta_tab_11.xml");
    tpTab[11] = mTexturePackLoaderFromSource.load("packer_parta_tab_12.xml");
    tpTab[12] = mTexturePackLoaderFromSource.load("packer_parta_tab_13.xml");
    tpTab[13] = mTexturePackLoaderFromSource.load("packer_parta_tab_14.xml");
    tpTab[14] = mTexturePackLoaderFromSource.load("packer_parta_tab_15.xml");
    tpTab[15] = mTexturePackLoaderFromSource.load("packer_parta_tab_16.xml");
    this.physicsEditorShapeLibrary = new PhysicsEditorShapeLibrary();
    this.physicsEditorShapeLibrary.open(Vol3Osyougatsu.context, "osyougatsu/gfx/body_tab.xml");

    tpBackGround.loadTexture();
    tpOne.loadTexture();
    tpTwo.loadTexture();
    tpThree.loadTexture();
    for (int i = 0; i < tpTab.length; i++) {
      tpTab[i].loadTexture();
    }

    mBackGroundTextureRegion = tpBackGround.getTexturePackTextureRegionLibrary().get(
      Vol3OsyougatsuResource.packer_parta_4.A3_7_IPHONE_HAIKEI_ID);
    
    mSuzuTiledTexTure = getTiledTextureFromPacker(tpOne, 
      Vol3OsyougatsuResource.packer_parta_1.A3_4_1_IPHONE_SUZU_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_4_2_IPHONE_SUZU_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_4_3_IPHONE_SUZU_ID
      );
    
    mShishiTiledTexTure = getTiledTextureFromPacker(tpThree, 
      Vol3OsyougatsuResource.packer_parta_3.A3_4_4_IPHONE_SHISHI_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_4_5_IPHONE_SHISHI_ID
      );
    
    mCubicStartTiledTexture[CUBIC_ORANGE] = getTiledTextureFromPacker(tpTwo, 
      Vol3OsyougatsuResource.packer_parta_2.A3_6A_1_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_2.A3_6A_2_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_2.A3_6A_3_IPHONE_KOMA_ID
      );
    mCubicStartTiledTexture[CUBIC_BLUE] = getTiledTextureFromPacker(tpTwo, 
      Vol3OsyougatsuResource.packer_parta_2.A3_6B_1_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_2.A3_6B_2_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_2.A3_6B_3_IPHONE_KOMA_ID
      );
    mCubicStartTiledTexture[CUBIC_GREEN] = getTiledTextureFromPacker(tpThree, 
      Vol3OsyougatsuResource.packer_parta_3.A3_6C_1_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_6C_2_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_6C_3_IPHONE_KOMA_ID
      );
    mCubicStartTiledTexture[CUBIC_YELLOW] = getTiledTextureFromPacker(tpThree, 
      Vol3OsyougatsuResource.packer_parta_3.A3_6D_1_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_6D_2_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_6D_3_IPHONE_KOMA_ID
      );
    
    mCubicTurnTiledTexture[CUBIC_ORANGE] = getTiledTextureFromPacker(tpOne, 
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_4_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_5_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_6_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_7_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_8_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6A_9_IPHONE_KOMA_ID
      );
    
    mCubicTurnTiledTexture[CUBIC_BLUE] = getTiledTextureFromPacker(tpOne, 
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_4_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_5_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_6_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_7_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_8_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6B_9_IPHONE_KOMA_ID
      );
    
    mCubicTurnTiledTexture[CUBIC_GREEN] = getTiledTextureFromPacker(tpOne, 
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_4_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_5_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_6_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_7_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_8_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6C_9_IPHONE_KOMA_ID
      );
    
    mCubicTurnTiledTexture[CUBIC_YELLOW] = getTiledTextureFromPacker(tpOne, 
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_4_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_5_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_6_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_7_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_8_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_1.A3_6D_9_IPHONE_KOMA_ID
      );
    
    mCubicStopTiledTexture[CUBIC_ORANGE] = getTiledTextureFromPacker(tpBackGround,
      Vol3OsyougatsuResource.packer_parta_4.A3_6A_10_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_4.A3_6A_11_IPHONE_KOMA_ID
      ); 
    mCubicStopTiledTexture[CUBIC_BLUE] = getTiledTextureFromPacker(tpBackGround,
      Vol3OsyougatsuResource.packer_parta_4.A3_6B_10_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_4.A3_6B_11_IPHONE_KOMA_ID
      ); 
    mCubicStopTiledTexture[CUBIC_GREEN] = getTiledTextureFromPacker(tpBackGround,
      Vol3OsyougatsuResource.packer_parta_4.A3_6C_10_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_4.A3_6C_11_IPHONE_KOMA_ID
      ); 
    mCubicStopTiledTexture[CUBIC_YELLOW] = getTiledTextureFromPacker(tpBackGround,
      Vol3OsyougatsuResource.packer_parta_4.A3_6D_10_IPHONE_KOMA_ID,
      Vol3OsyougatsuResource.packer_parta_4.A3_6D_11_IPHONE_KOMA_ID
      ); 
    
    mCubicTurnOptionTiledTexture[0] = getTiledTextureFromPacker(tpThree,
      Vol3OsyougatsuResource.packer_parta_3.A3_6E_1_1_IPHONE_PRESENT_ID,
      Vol3OsyougatsuResource.packer_parta_3.A3_6E_1_2_IPHONE_PRESENT_ID
      );
    mCubicTurnOptionTiledTexture[1] = getTiledTextureFromPacker(tpTwo,
      Vol3OsyougatsuResource.packer_parta_2.A3_6E_2_1_IPHONE_PRESENT_ID,
      Vol3OsyougatsuResource.packer_parta_2.A3_6E_2_2_IPHONE_PRESENT_ID
      );
    mCubicTurnOptionTiledTexture[2] = getTiledTextureFromPacker(tpBackGround,
      Vol3OsyougatsuResource.packer_parta_4.A3_6E_3_1_IPHONE_PRESENT_ID,
      Vol3OsyougatsuResource.packer_parta_4.A3_6E_3_2_IPHONE_PRESENT_ID
      );
    
    mScrollLeftTextureRegion = tpOne.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_parta_1.A3_8_2_IPHONE_YAJIRUSHI_ID);
    
    Log.i(TAG, "----------End loadTexture------------------------------");
  }


  protected void drawScene() {
    Log.i(TAG, "----------drawScene------------------------------");
    
    this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
    this.registerUpdateHandler(mPhysicsWorld);
    this.mPhysicsWorld.setContactListener(contactListener);
    
    final Sprite spriteBackGround =
        new Sprite(960/2 - mBackGroundTextureRegion.getWidth()/2, 0, mBackGroundTextureRegion, mVertexBufferObjectManager);
    this.attachChild(spriteBackGround);

    for (int i = 0; i < mTabSprite.length; i++) {
      mTabSprite[i] =
          new Sprite(960 / 2 - 1150 / 2, 89.5f, tpTab[i].getTexturePackTextureRegionLibrary()
              .get(0), mVertexBufferObjectManager);
      this.attachChild(mTabSprite[i]);
      mTabSprite[i].setVisible(false);
    }
    mTabSprite[0].setVisible(true);
    
    final Body body = this.physicsEditorShapeLibrary.createBody("a3_5_1_iphone_dai", mTabSprite[0], this.mPhysicsWorld);
    this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mTabSprite[0], body, true, true));
    mTabSprite[0].setUserData(body);
        
    // Cubic Choose
    mCubicChooseSpite[CUBIC_YELLOW] = new Sprite(279, 70,
      tpOne.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_parta_1.A3_6_4_IPHONE_KOMA_ID),
      mVertexBufferObjectManager);
    this.attachChild(mCubicChooseSpite[CUBIC_YELLOW]);
    
    mCubicChooseSpite[CUBIC_GREEN] = new Sprite(200, 90f,
      tpOne.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_parta_1.A3_6_3_IPHONE_KOMA_ID),
      mVertexBufferObjectManager);
    this.attachChild(mCubicChooseSpite[CUBIC_GREEN]);
    
    mCubicChooseSpite[CUBIC_BLUE] = new Sprite(110, 107,
      tpOne.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_parta_1.A3_6_2_IPHONE_KOMA_ID),
      mVertexBufferObjectManager);
    this.attachChild(mCubicChooseSpite[CUBIC_BLUE]);
    
    mCubicChooseSpite[CUBIC_ORANGE] = new Sprite(22f, 135,
      tpOne.getTexturePackTextureRegionLibrary().get(Vol3OsyougatsuResource.packer_parta_1.A3_6_1_IPHONE_KOMA_ID),
      mVertexBufferObjectManager);
    this.attachChild(mCubicChooseSpite[CUBIC_ORANGE]);
    
   cubicTouch = new CubicTouch(0, 0, 960, 640,null);
   this.attachChild(cubicTouch);
   this.registerTouchArea(cubicTouch);
   
   for (int i = CUBIC_ORANGE; i <= CUBIC_YELLOW; i++) {
     final TextureRegion iOTextureRegion = new TextureRegion(bitmapTexture[i%2],
       2, 2, mCubicStartTiledTexture[i].getWidth(), mCubicStartTiledTexture[i].getHeight());
     mCubicEntity[i] = new CubicEntity(0, 100, i,
       mCubicStartTiledTexture[i],
       mCubicTurnTiledTexture[i],
       mCubicStopTiledTexture[i],
       mCubicTurnOptionTiledTexture,
       iOTextureRegion);
     this.attachChild(mCubicEntity[i]);
     mCubicEntity[i].setVisible(false);
     // Body
     final Body bodyCubic= this.physicsEditorShapeLibrary.createBody(CUBIC_BODYNAME[i], mCubicEntity[i], this.mPhysicsWorld);
     bodyCubic.setFixedRotation(true);
     bodyCubic.setActive(false);
     mCubicEntity[i].setUserData(bodyCubic);
     mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mCubicEntity[i], bodyCubic, true, true));
   }
   
   final SceneTouch sceneTouch = new SceneTouch(0, 0, 960, 640,null);
   this.attachChild(sceneTouch);
   this.registerTouchArea(sceneTouch);
   
   mSuzuAniSprite = new AnimatedSprite(960/2 - mSuzuTiledTexTure.getWidth()/2, -19, mSuzuTiledTexTure, mVertexBufferObjectManager);
   this.attachChild(mSuzuAniSprite);
   
   mShishiAniSprite = new AnimatedSprite(960, 59.5f, mShishiTiledTexTure, mVertexBufferObjectManager);
   this.attachChild(mShishiAniSprite);
   mShishiAniSprite.setVisible(false);
   
   mArrowSprite = new Sprite(120, 133, tpThree.getTexturePackTextureRegionLibrary().get(
     Vol3OsyougatsuResource.packer_parta_3.A3_8_1_IPHONE_YAJIRUSHI_ID), mVertexBufferObjectManager);
   this.attachChild(mArrowSprite);
   mArrowSprite.setVisible(false);
   
  }
  
  /**
   * SceneTouch listening all event scene
   * @author PQHAI
   */
  private class SceneTouch extends RectangularShape {

    public SceneTouch(float pX, float pY, float pWidth, float pHeight, ShaderProgram pShaderProgram) {
      super(pX, pY, pWidth, pHeight, pShaderProgram);
    }
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
        float pTouchAreaLocalY) {
      float pX = pSceneTouchEvent.getX();
      float pY = pSceneTouchEvent.getY();

      if (pSceneTouchEvent.isActionDown()) {
        if (checkContainsPolygon(mTabSprite[0], POINTER_FACE, 8, pX, pY)) {
          Log.i(TAG, "Touch to body Face !");
          actionFace();
        }
        
        if (mCubicChooseSpite[CUBIC_ORANGE].contains(pX, pY) && mCubicChooseSpite[CUBIC_ORANGE].isVisible()) {
          insertCubicToTab(CUBIC_ORANGE);
        } else if (mCubicChooseSpite[CUBIC_BLUE].contains(pX, pY) && mCubicChooseSpite[CUBIC_BLUE].isVisible()) {
          insertCubicToTab(CUBIC_BLUE);
        }else if(mCubicChooseSpite[CUBIC_GREEN].contains(pX, pY) && mCubicChooseSpite[CUBIC_GREEN].isVisible()) {
          insertCubicToTab(CUBIC_GREEN);
        }else if(mCubicChooseSpite[CUBIC_YELLOW].contains(pX, pY) && mCubicChooseSpite[CUBIC_YELLOW].isVisible()) {
          insertCubicToTab(CUBIC_YELLOW);
        }
        
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
  
  @Override
  public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
    if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
      cubicTouch.onAreaTouched(pSceneTouchEvent, 0, 0);
    }
    return super.onSceneTouchEvent(pSceneTouchEvent);
  }
  
  
  private class CubicTouch extends RectangularShape{
    
    private int CUBICID = -1;
    private float pxMOVE = 0;

    public CubicTouch(float pX, float pY, float pWidth, float pHeight, ShaderProgram pShaderProgram) {
      super(pX, pY, pWidth, pHeight, pShaderProgram);
    }
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
        float pTouchAreaLocalY) {
        
        if(pSceneTouchEvent.isActionDown()){
         // Log.i(TAG, "Touch CubicTouch isActionDown");
          if(checkContains(mSuzuAniSprite, 113, 30, 251, 202, (int)pTouchAreaLocalX, (int)pTouchAreaLocalY)){
            Log.i(TAG, "Touch to ActionSuzuShiShi !");
            actionSuzuShiShi();
            return true;
          }
            for (int i = 0; i < mCubicEntity.length; i++) {
              if(mCubicEntity[i].isVisible() && mCubicEntity[i].getCubicBody().contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())){
                  pxMOVE = pSceneTouchEvent.getX();
                  if(mCubicEntity[i].actionDown() > 0){
                    Log.i(TAG, "Touch CubicTouch > 0");
                  }else{
                    Log.i(TAG, "Touch CubicTouch < 0 " + i);
                    CUBICID = i;
                  }
                  return true;
              }
            }
        }else if(pSceneTouchEvent.isActionMove()){
          //Log.i(TAG, "Touch CubicTouch isActionMove");
        }else if(pSceneTouchEvent.isActionUp()){
          Log.i(TAG, "Touch CubicTouch isActionUp" + CUBICID);
          if(CUBICID>=0 && mCubicEntity[CUBICID].getCubicAction() == CubicEntity.ACTION_DEFAULT){
            float offset = pSceneTouchEvent.getX() - pxMOVE ;
            if(offset < 0){
                if(Math.abs(offset) >= 350){
                    Log.i(TAG, "Level 3");
                    mCubicEntity[CUBICID].actionUp(CubicEntity.ACTION_TURN_LEVEL_3);
                }else if(Math.abs(offset) >= 200){
                    Log.i(TAG, "Level 2");
                    mCubicEntity[CUBICID].actionUp(CubicEntity.ACTION_TURN_LEVEL_2);
                }else if(Math.abs(offset) >= 0){
                    Log.i(TAG, "Level 1");
                    mCubicEntity[CUBICID].actionUp(CubicEntity.ACTION_TURN_LEVEL_1);
                }
            }
          }
          
          CUBICID = -1;
        }
      return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    @Override
    public IVertexBufferObject getVertexBufferObject() {
      return null;
    }

    @Override
    protected void onUpdateVertices() { }
    
  }


  // =======================================================
  // Function
  // =======================================================
  
  private void insertCubicToTab(int index){
    Log.i(TAG, "Touch insertCubicToTab " + index );
    if(CUBIC_SLOT[0] == false){
      mCubicEntity[index].newEvent(POINTER_CUBIC_SLOT[0][0], POINTER_CUBIC_SLOT[0][1], 0);
    }else if(CUBIC_SLOT[1] == false){
      mCubicEntity[index].newEvent(POINTER_CUBIC_SLOT[1][0], POINTER_CUBIC_SLOT[1][1], 1);
    }
   
  }
  
  private void actionSuzuShiShi(){
    // If running return false
    if(mSuzuAniSprite.isAnimationRunning() || mShishiAniSprite.isAnimationRunning()){
        return;
    }
    Vol3Osyougatsu.OGG_A3_A_4_SUZU2.play();
    mSuzuAniSprite.animate(new long[]{150,150}, new int[]{1,2}, 4, new IAnimationListener() {
      
      @Override
      public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
      
      @Override
      public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
      
      @Override
      public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) { }
      
      @Override
      public void onAnimationFinished(AnimatedSprite arg0) {
        mSuzuAniSprite.setCurrentTileIndex(0);
        mShishiAniSprite.animate(new long[]{300,300});
        mShishiAniSprite.registerEntityModifier(new MoveXModifier(4.5f, mShishiAniSprite.getmXFirst(), -mShishiAniSprite.getWidth(), new IEntityModifierListener() {
          
          @Override
          public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
            mShishiAniSprite.setVisible(true);
            Vol3Osyougatsu.OGG_A3_A_4_KACHI2.play();
          }
          
          @Override
          public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
            mShishiAniSprite.stopAnimation();
            mShishiAniSprite.resetLocalToFirst();
            mShishiAniSprite.setVisible(false);
          }
        }));
      }
    });
  }

  private void actionFace() {
    if (faceSequenceEntityModifier != null && !faceSequenceEntityModifier.isFinished()) {
      return;
    }
    Vol3Osyougatsu.OGG_A3_A_5_IYO.play();
    final int ran = new Random().nextInt(3);
    this.unregisterEntityModifier(faceSequenceEntityModifier);
    this.registerEntityModifier(faceSequenceEntityModifier = new SequenceEntityModifier(new IEntityModifierListener() {
      @Override
      public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
        
      }
      
      @Override
      public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
        mTabSprite[indexFace].setVisible(false);
      }
    }, new LoopEntityModifier(new DelayModifier(0.4f), 5, new ILoopEntityModifierListener() {

      @Override
      public void onLoopStarted(LoopModifier<IEntity> arg0, int pLoop, int pLoopCount) {
        //Log.i(TAG, "onLoopStarted pLoop " + pLoop + " pLoopCount " + pLoopCount);
        indexFace = ran * 5 + pLoop + 1;
        if (pLoop > 0) {
          mTabSprite[indexFace - 1].setVisible(false);
        }
        mTabSprite[indexFace].setVisible(true);
      }

      @Override
      public void onLoopFinished(LoopModifier<IEntity> arg0, int pLoop, int pLoopCount) {
        indexFace = ran * 5 + pLoop + 1;
      }
    }), new DelayModifier(1.8f)));
    /*this.registerEntityModifier(faceSequenceEntityModifier =
        new LoopEntityModifier(new DelayModifier(0.4f), 5, new ILoopEntityModifierListener() {

          @Override
          public void onLoopStarted(LoopModifier<IEntity> arg0, int pLoop, int pLoopCount) {
            //Log.i(TAG, "onLoopStarted pLoop " + pLoop + " pLoopCount " + pLoopCount);
            int index = ran * 5 + pLoop + 1;
            if (pLoop > 0) {
              mTabSprite[index - 1].setVisible(false);
            }
            mTabSprite[index].setVisible(true);
          }

          @Override
          public void onLoopFinished(LoopModifier<IEntity> arg0, int pLoop, int pLoopCount) {
            int index = ran * 5 + pLoop + 1;
            mTabSprite[index].setVisible(false);
          }
        }));*/
  }
  
  
  //=====================================================================
  // Class Cubic
  //=====================================================================
  
  private class CubicEntity extends Sprite{
    
    private static final int ACTION_DEFAULT = 0;
    private static final int ACTION_TURN_START = 1;
    private static final int ACTION_TURN_LEVEL_1 = 2;
    private static final int ACTION_TURN_LEVEL_2 = 3;
    private static final int ACTION_TURN_LEVEL_3 = 4;
    private static final int ACTION_TURN_GIRL = 5;
    private static final int ACTION_TURN_BOY = 6;
    private static final int ACTION_TURN_FUNGI = 7;
    private static final int ACTION_TURN_STOP = 8;
   
    private int ACTION = ACTION_DEFAULT;
    private int TYPE = 0;
    private int SLOT = 0;
    
    private AnimatedSprite mCubicStartAniSprite;
    private AnimatedSprite mCubicTurnAniSprite;
    private AnimatedSprite mCubicStopAniSprite;
    private AnimatedSprite mCubicTurnOptionAniSprite[] = new AnimatedSprite[3];
    private Sprite mScrollLeftSprite;
    private TimerHandler timerHandler;
    
    public CubicEntity(float pX, float pY, int pCubic, final ITiledTextureRegion pCubicStart,
                       final ITiledTextureRegion pCubicTurn, final ITiledTextureRegion pCubicStop,
                       final ITiledTextureRegion pCubicTurnOption[],
                       ITextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion, mVertexBufferObjectManager);
        setAlpha(0.0f);
        TYPE = pCubic;
        mCubicStartAniSprite = new AnimatedSprite(0, 0, pCubicStart, mVertexBufferObjectManager);
        this.attachChild(mCubicStartAniSprite);
        mCubicStartAniSprite.setVisible(false);
        
        float turnX = getWidth() - pCubicTurn.getWidth();
        float turnY = getHeight() - pCubicTurn.getHeight();
        mCubicTurnAniSprite = new AnimatedSprite(turnX-15, turnY - 15, pCubicTurn, mVertexBufferObjectManager);
        this.attachChild(mCubicTurnAniSprite);
        mCubicTurnAniSprite.setVisible(false);
        
        for (int i = 0; i < mCubicTurnOptionAniSprite.length; i++) {
          float turnOpX = getWidth() - pCubicTurnOption[i].getWidth();
          float turnOpY = getHeight() - pCubicTurnOption[i].getHeight();
          mCubicTurnOptionAniSprite[i] = new AnimatedSprite(turnOpX, turnOpY, pCubicTurnOption[i], mVertexBufferObjectManager);
          this.attachChild(mCubicTurnOptionAniSprite[i]);
          mCubicTurnOptionAniSprite[i].setVisible(false);
        }
        
        float stopX = getWidth() - pCubicTurn.getWidth();
        float stopY = getHeight() - pCubicTurn.getHeight();
        mCubicStopAniSprite = new AnimatedSprite(stopX-15, stopY- 15, pCubicStop, mVertexBufferObjectManager);
        this.attachChild(mCubicStopAniSprite);
        mCubicStopAniSprite.setVisible(false);
        
        float scrollX = turnX + pCubicTurn.getWidth()/2 - mScrollLeftTextureRegion.getWidth() ;
        float scrollY = turnY + pCubicTurn.getHeight() - mScrollLeftTextureRegion.getHeight();
        mScrollLeftSprite = new Sprite(scrollX, scrollY, mScrollLeftTextureRegion, mVertexBufferObjectManager);
        this.attachChild(mScrollLeftSprite);
        mScrollLeftSprite.setVisible(false);
        
        mScrollLeftSprite.registerEntityModifier(new LoopEntityModifier(
              new SequenceEntityModifier(
                new AlphaModifier(0.5f, 0.2f, 1.0f),
                new AlphaModifier(0.5f, 1.0f, 0.2f)
                )
            ));
    }
    
    public int getCubicAction(){
      return ACTION;
    }
    
    public AnimatedSprite getCubicBody(){
      return this.mCubicTurnAniSprite;
    }
    
    public void newEvent(float pX, float pY, int pSlot){
      mCubicChooseSpite[TYPE].setVisible(false);
      SLOT = pSlot;
      CUBIC_SLOT[SLOT] = true;
      checkArrow();
      this.setPosition(0, 100);
      ACTION = ACTION_DEFAULT;
      mCubicStartAniSprite.setVisible(true);
      mScrollLeftSprite.clearEntityModifiers();
      mScrollLeftSprite.registerEntityModifier(new LoopEntityModifier(
        new SequenceEntityModifier(
          new AlphaModifier(0.5f, 0.2f, 1.0f),
          new AlphaModifier(0.5f, 1.0f, 0.2f)
          )
      ));
      mScrollLeftSprite.setVisible(true);
      this.setVisible(true);
      final Body body = (Body)CubicEntity.this.getUserData();
      Vector2 vector2 = Vector2Pool.obtain(pX/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pY /PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
      body.setTransform(vector2, 0);
      Vector2Pool.recycle(vector2);
     
    }
    
    private void action(int action){
      mCubicStartAniSprite.setVisible(false);
      mCubicStartAniSprite.stopAnimation(0);
        switch (action) {
          case ACTION_TURN_LEVEL_1:
          case ACTION_TURN_LEVEL_2:
          case ACTION_TURN_LEVEL_3:
            ACTION = action;
            final Body body = (Body)this.getUserData();
            final Vector2 vector2 = Vector2Pool.obtain(body.getPosition());
            int frame[] = new int[] {0,1};
              body.setAngularDamping(1.2f);
              vector2.x = vector2.x /3;
              vector2.y = vector2.y /3;
            if(action == ACTION_TURN_LEVEL_2){
              frame = new int[] {2,3};
              body.setAngularDamping(2.2f);
              vector2.x = vector2.x /2;
              vector2.y = vector2.y /2;
            }else if(action == ACTION_TURN_LEVEL_3){
              frame = new int[] {4,5};
              body.setAngularDamping(3.2f);
            }
            Vol3Osyougatsu.OGG_A3_A_5_KOMATEI.play();
            mCubicTurnAniSprite.animate(new long[]{250,250}, frame,-1);
            mCubicTurnAniSprite.setVisible(true);
            
            body.setActive(true);
            Log.i(TAG, "body.getPosition " + vector2);
            vector2.x = random(vector2.x) + new Random().nextFloat();
            vector2.y = random(vector2.y) + new Random().nextFloat();
            body.setLinearVelocity(vector2);
            Log.i(TAG, "body.getPosition change " + vector2);
            this.unregisterUpdateHandler(timerHandler);
            this.registerUpdateHandler(timerHandler = new TimerHandler(6f, new ITimerCallback() {
              
              @Override
              public void onTimePassed(TimerHandler timerHandler) {
                // Stop Turn
                stopTurn();
              }
            }));
            break;
        }
    }
    
    // Function Stop Turn when out time and collision "shishi"
    public void stopTurn(){
      Vol3Osyougatsu.OGG_A3_A_5_KOMATEI.stop();
      ACTION = ACTION_TURN_STOP;
      for (int i = 0; i < mCubicTurnOptionAniSprite.length; i++) {
        mCubicTurnOptionAniSprite[i].stopAnimation();
        mCubicTurnOptionAniSprite[i].setVisible(false);
      }
      mCubicTurnAniSprite.stopAnimation(0);
      mCubicTurnAniSprite.setVisible(false);
      removePhysics(CubicEntity.this);
      mCubicStopAniSprite.animate(new long[]{200,1000}, new int[]{0,1}, 0, new IAnimationListener() {
        
        @Override
        public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
          mCubicStopAniSprite.setVisible(true);
        }
        
        @Override
        public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
          
        }
        
        @Override
        public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
          Log.i("XXX", arg1 + " Changed ID : " + arg2 );
          int index = arg2;
          if(index == - 1 ){
            index = 0;
          }
          if (index >= 2) return; 
          float width = mCubicStopAniSprite.getTiledTextureRegion().getWidth(index);
          float height = mCubicStopAniSprite.getTiledTextureRegion().getHeight(index);
          Log.i("XXX", width + " ~ " + height );
          mCubicStopAniSprite.setWidth(width);
          mCubicStopAniSprite.setHeight(height);
        }
        
        @Override
        public void onAnimationFinished(AnimatedSprite arg0) {
              CubicEntity.this.clearAll();
              mCubicChooseSpite[CubicEntity.this.TYPE].setVisible(true);
              CUBIC_SLOT[SLOT] = false;
              checkArrow();
        }
      });
    }
    
    public int actionDown(){
        if(ACTION == ACTION_DEFAULT || ACTION == ACTION_TURN_START){
          return -1;
        }else
        if(ACTION >= ACTION_TURN_LEVEL_1 && ACTION <= ACTION_TURN_LEVEL_3){
          touchToTurn(ACTION);
        }
        return 1;
    }
    
    private void touchToTurn(final int pAction){
        Vol3Osyougatsu.OGG_A3_A_5_SPACE6.play();
        final int ran = new Random().nextInt(mCubicTurnOptionAniSprite.length);
        if(ran == 0){
          ACTION = ACTION_TURN_GIRL;
        }else if(ran == 1){
          ACTION = ACTION_TURN_BOY;
        }else if(ran == 2){
          ACTION = ACTION_TURN_FUNGI;
        }
        mCubicTurnAniSprite.stopAnimation();
        mCubicTurnAniSprite.setVisible(false);
        mCubicTurnOptionAniSprite[ran].animate(new long[]{250,250}, new int[]{0,1},2, new IAnimationListener() {
          
          @Override
          public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
            mCubicTurnOptionAniSprite[ran].setVisible(true);
          }
          
          @Override
          public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
          }
          
          @Override
          public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
          }
          
          @Override
          public void onAnimationFinished(AnimatedSprite arg0) {
            mCubicTurnOptionAniSprite[ran].setVisible(false);
            mCubicTurnOptionAniSprite[ran].stopAnimation();
            ACTION = pAction;
            int frame[] = new int[] {0,1};
           if(ACTION == ACTION_TURN_LEVEL_2){
            frame = new int[] {2,3};
           }else if(ACTION == ACTION_TURN_LEVEL_3){
            frame = new int[] {4,5};
           }
           // run back...
           mCubicTurnAniSprite.animate(new long[]{250,250}, frame,-1);
           mCubicTurnAniSprite.setVisible(true);
           
          }
        });
        
    }
    
    public void actionUp(final int action){
      ACTION = ACTION_TURN_START;
      mScrollLeftSprite.setVisible(false);
      mScrollLeftSprite.clearEntityModifiers();
      mCubicStartAniSprite.animate(new long[]{300, 300, 300}, new int[]{0, 1, 2}, 0, new IAnimationListener() {
        
        @Override
        public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
          mCubicStartAniSprite.setVisible(true);
        }
        
        @Override
        public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) { }
        
        @Override
        public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) { }
        
        @Override
        public void onAnimationFinished(AnimatedSprite animatedSprite) {
          action(action);
        }
      });
    }
    
    
    private float random(float pVector){
      int ran = new Random().nextInt(2);
      return  ran ==0 ? pVector : - pVector;
    }
    
    public void clearAll(){
      this.setVisible(false);
      ACTION = ACTION_DEFAULT;
      this.unregisterUpdateHandler(timerHandler);
      mCubicStartAniSprite.clearEntityModifiers();
      mCubicStartAniSprite.clearUpdateHandlers();
      mCubicStartAniSprite.stopAnimation(0);
      mCubicStartAniSprite.setVisible(false);
      
      mCubicTurnAniSprite.clearEntityModifiers();
      mCubicTurnAniSprite.clearUpdateHandlers();
      mCubicTurnAniSprite.stopAnimation(0);
      mCubicTurnAniSprite.setVisible(false);
      
      mCubicStopAniSprite.clearEntityModifiers();
      mCubicStopAniSprite.clearUpdateHandlers();
      mCubicStopAniSprite.stopAnimation(0);
      mCubicStopAniSprite.setVisible(false);
      
      mScrollLeftSprite.clearEntityModifiers();
      mScrollLeftSprite.setVisible(false);
      
      for (int i = 0; i < mCubicTurnOptionAniSprite.length; i++) {
        mCubicTurnOptionAniSprite[i].setVisible(false);
        mCubicTurnOptionAniSprite[i].clearEntityModifiers();
        mCubicTurnOptionAniSprite[i].clearUpdateHandlers();
        mCubicTurnOptionAniSprite[i].stopAnimation();
      }
      
      removePhysics(this);
      
    }
    
  }
  
  ContactListener  contactListener = new ContactListener() {
    
    @Override
    public void preSolve(Contact arg0, Manifold arg1) {      
    }
    
    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {      
    }
    
    @Override
    public void endContact(Contact contact) {      
    }
    
    @Override
    public void beginContact(Contact contact) {
      final Body bodyTab = (Body)mTabSprite[0].getUserData();
      if(contact.getFixtureA().getBody() != bodyTab && contact.getFixtureB().getBody() != bodyTab ){
        Vol3Osyougatsu.OGG_A3_A_6_GO.stop();
        Vol3Osyougatsu.OGG_A3_A_6_KACHIN.stop();
        Vol3Osyougatsu.OGG_A3_A_6_GO.play();
        Vol3Osyougatsu.OGG_A3_A_6_KACHIN.play();
        Log.i(TAG, "Va cham rui` ! ---------------");
      }
        Log.i(TAG, "Va cham rui` !");
    }
  };
  
  
  private void removePhysics(final IShape pShape) {
    final Body body = (Body) pShape.getUserData();
    body.setActive(false);
  }
  
  public void checkArrow(){
    if(CUBIC_SLOT[0] == false || CUBIC_SLOT[1] == false){
      mArrowSprite.setVisible(true);
      mArrowSprite.clearEntityModifiers();
      mArrowSprite.registerEntityModifier(new LoopEntityModifier(
        new SequenceEntityModifier(
          new AlphaModifier(0.5f, 0.2f, 1.0f),
          new AlphaModifier(0.5f, 1.0f, 0.2f)
          )
      ));
    }else{
      mArrowSprite.setVisible(false);
      mArrowSprite.clearEntityModifiers();
    }
  }
  
  @Override
  public void onResumeGame() {
    for (int i = 0; i < mCubicChooseSpite.length; i++) {
      mCubicChooseSpite[i].setVisible(true);
    }
    CUBIC_SLOT[0] = false;
    CUBIC_SLOT[1] = false;
    this.unregisterUpdateHandler(updateHandler);
    this.registerUpdateHandler(updateHandler = new IUpdateHandler() {
      
      @Override
      public void reset() {}
      
      @Override
      public void onUpdate(float f) {
            for (int i = 0; i < mCubicEntity.length; i++) {
              if(mCubicEntity[i].isVisible() && mCubicEntity[i].getCubicAction() >= CubicEntity.ACTION_TURN_LEVEL_1 
                  && mCubicEntity[i].getCubicAction() <= CubicEntity.ACTION_TURN_FUNGI 
                  && mShishiAniSprite.isVisible() == true
                  && mShishiAniSprite.collidesWith(mCubicEntity[i].getCubicBody())){
                Log.i(TAG, i + " va cham voi shishi ");
                Vol3Osyougatsu.OGG_A3_A_6_GO.play();
                Vol3Osyougatsu.OGG_A3_A_6_KACHIN.play();
                mCubicEntity[i].stopTurn();
              }
            }
      }
    });
    mArrowSprite.setVisible(true);
    mArrowSprite.clearEntityModifiers();
    mArrowSprite.registerEntityModifier(new LoopEntityModifier(
      new SequenceEntityModifier(
        new AlphaModifier(0.5f, 0.2f, 1.0f),
        new AlphaModifier(0.5f, 1.0f, 0.2f)
        )
    ));
    Log.i(TAG, "-- onResumeGame --");
  }
  
  @Override
  public void onPauseGame() {
    if(!isDrawView || !isPreView){
      return;
    }
    Log.i(TAG, "-- onPauseGame --");
    this.unregisterUpdateHandler(updateHandler);
    CUBIC_SLOT[0] = false;
    CUBIC_SLOT[1] = false;
    mArrowSprite.setVisible(false);
    mSuzuAniSprite.stopAnimation(0);
    mShishiAniSprite.stopAnimation();
    mShishiAniSprite.clearEntityModifiers();
    mShishiAniSprite.resetLocalToFirst();
    mShishiAniSprite.setVisible(false);
    
    for (int i = 0; i < mCubicEntity.length; i++) {
      if(mCubicEntity[i] != null){
        mCubicEntity[i].clearAll();
      }
    }
    
    if(faceSequenceEntityModifier != null){
      this.unregisterEntityModifier(faceSequenceEntityModifier);
      faceSequenceEntityModifier = null;
    }
    
    for (int i = 1; i < mTabSprite.length; i++) {
      mTabSprite[i].setVisible(false);
    }
    
  }

}
