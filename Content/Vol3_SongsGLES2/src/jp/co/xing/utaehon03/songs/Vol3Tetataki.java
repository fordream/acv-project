/* Vol3Tetataki.java
* Create on Aug 20, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
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

import android.util.Log;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TetatakiResource;

public class Vol3Tetataki extends BaseGameActivity implements IAnimationListener,
	IEntityModifierListener, IOnSceneTouchListener {

	public final static String TAG = " Vol3Tetataki "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpTetataki1, ttpTetataki3, ttpTetataki4;
	private TexturePackTextureRegionLibrary ttpLibTetataki1, ttpLibTetataki3, ttpLibTetataki4;
	
	private TextureRegion mGimmicText, mHandText, mWaterText1, mWaterText2, mTissuesText, mBimText1, mBimText2, mBimText3,
			mPanText, mSpoonText, mBoxText, mBoxStarText, mPanStarText, mPanGlitText, mChickenText;
	private Sprite sprGimmic, sprHand, sprWater1, sprWater2, sprTissues, sprBim1, sprBim2,sprBim3,
			sprPan, sprSpoon, sprBox, sprBoxStar, sprPanStar, sprPanGlit, sprChicken;
	private TiledTextureRegion  mToiletTextRegion, mFaceTextRegion, mBreastTextRegion, 
			mVacuumTextRegion, mDoorTextRegion, mBucketTextRegion, mDrumTextRegion, mBalloonTextRegion,
			mPhoneTextRegion, mTissuesTextRegion, mBimTextRegion, mBowlTextRegion, mBottleTextRegion, mPotTextRegion,
			mGlass1, mGlass2, mGlass3, mGlass4, mChicken, mBoard;
	private AnimatedSprite  mToiletAnimateSprite, mFaceAniSpr, mBreastAniSpr, mVacuumAniSpr,
			mDoorAniSpr, mBucketAniSpr, mDrumAniSpr, mBalloonAniSpr, mPhoneAniSpr, mTissuesAniSpr, mBimAniSpr, 
			mBowlAniSpr, mBottleAniSpr, mPotAniSpr, mGlassAniSpr1, mGlassAniSpr2, mGlassAniSpr3,
			mGlassAniSpr4, mChickenAniSpr, mBoardAniSpr;
	private Rectangle mBackground; 
	private TimerHandler timerHand;
	private Sound A1_10_PAN,A1_11_TISH1,A1_11_TISH2,A1_12_TAMBALIN,A1_13_KEITAI1,A1_13_KEITAI2,A1_14_GATA,
		A1_15_FUSENWARERU,A1_16_DOOR,A1_18_TOIRE,A1_19_SOUJIKI, A1_1_PET1,A1_1_PET2,A1_20_PURIPURI,A1_22_AHAHA,
		A1_2_NABE1,A1_2_NABE2,A1_3_OKASHI1,A1_3_OKASHI2,A1_5_TON2,A1_5_TON3,A1_6_FLYPAN1,A1_6_FLYPAN2,A1_7_TYAWAN1,
		A1_7_TYAWAN2, A1_8_GOCHISOUSAMA,A1_8_ITADAKIMASU,A1_HATENA_MOKIN,A1_16_BOY1,A1_16_BOY2,
		A1_16_BOY3,A1_16_BOY4,A1_4_5_1,A1_4_5_2,A1_4_5_3,A1_4_6_1,A1_4_6_2,
		A1_4_6_3,A1_4_7_1,A1_4_7_2,A1_4_7_3,A1_4_7_4,A1_4_8_1,A1_4_8_2,A1_4_8_3, A1_4_8_4,A1_20_ONARA10,
		A1_20_ONARA0,A1_20_ONARA12,A1_20_ONARA2,A1_20_ONARA11, A1_20_ONARA8, A1_20_ONARA7, A1_20_ONARA1,
		A1_20_ONARA9,A1_20_ONARA15, A1_20_ONARA5,A1_20_ONARA14,A1_20_ONARA4,A1_20_ONARA13,A1_20_ONARA6,A1_20_ONARA3;
	
	private int countBg, countBoard,countPan, countBottle, countPot, countchicken, countBowl, 
				countBox, countPhone,countTissues;
	private boolean touchFace,touchBowl, touchDrum, touchBoard, touchBottle, touchPot, touchPan,touchChicken, touchBox,
					touchVacuum,touchGlass1, touchGlass2, touchGlass3, touchGlass4, touchBreast, touchToilet1, touchToilet2,
					touchPhone, touchDoor, touchBucket, touchBall, touchBim, touchTissues;
	
	private Sound arrSoundOnara[];
	private Sound arrSoundBoy[];
	private void initial() {
		countBg = countBoard = countBottle = countPot = countPhone = 0;
		countchicken = countBowl = countPan = countBox = countTissues= 0;
		touchFace = touchDrum = touchBoard = touchBottle = true;
		touchPot = touchChicken = touchBowl = touchPan =  touchTissues = true;
		touchGlass1 = touchGlass2 = touchGlass3 = touchGlass4 = true;
		touchBox = touchBreast = touchVacuum = touchToilet1 = touchToilet2 =true;
		touchPhone = touchDoor = touchBucket = touchBall =  touchBim = true;
		
	}
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("tetataki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tetataki/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "tetataki/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources ");
		ttpTetataki1 = mTexturePackLoaderFromSource.load("tetataki1.xml");
		ttpTetataki1.loadTexture();
		ttpLibTetataki1 = ttpTetataki1.getTexturePackTextureRegionLibrary();
		ttpTetataki3 = mTexturePackLoaderFromSource.load("tetataki3.xml");
		ttpTetataki3.loadTexture();
		ttpLibTetataki3 = ttpTetataki3.getTexturePackTextureRegionLibrary();
		ttpTetataki4 = mTexturePackLoaderFromSource.load("tetataki4.xml");
		ttpTetataki4.loadTexture();
		ttpLibTetataki4 = ttpTetataki4.getTexturePackTextureRegionLibrary();
		
		mToiletTextRegion = getTiledTextureFromPacker(ttpTetataki1, 
				Vol3TetatakiResource.A1_18_1_IPHONE_ID,Vol3TetatakiResource.A1_18_2_IPHONE_ID);
		
		mGimmicText = ttpLibTetataki1.get(Vol3TetatakiResource.A3_TETATAKI_3_IPHONE_ID);
		mHandText = ttpLibTetataki1.get(Vol3TetatakiResource.A1_21_1_IPHONE_ID);
		
		mFaceTextRegion = getTiledTextureFromPacker(ttpTetataki1,
				Vol3TetatakiResource.A1_22_1_IPHONE_ID,Vol3TetatakiResource.A1_22_2_IPHONE_ID);
		
		mBreastTextRegion = getTiledTextureFromPacker(ttpTetataki1, Vol3TetatakiResource.A1_20_1_IPHONE_ID,
				Vol3TetatakiResource.A1_20_2_IPHONE_ID,Vol3TetatakiResource.A1_20_3_IPHONE_ID);
		
		mVacuumTextRegion = getTiledTextureFromPacker(ttpTetataki1,
				Vol3TetatakiResource.A1_19_1_IPHONE_ID, Vol3TetatakiResource.A1_19_2_IPHONE_ID);
		
		mDoorTextRegion = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_16_1_IPHONE_ID, Vol3TetatakiResource.A1_16_2_IPHONE_ID);
		mBucketTextRegion = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_14_1_IPHONE_ID, Vol3TetatakiResource.A1_14_2_IPHONE_ID);
		mDrumTextRegion = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_12_1_IPHONE_ID, Vol3TetatakiResource.A1_12_2_IPHONE_ID); 
		mBalloonTextRegion = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_15_1_IPHONE_ID,Vol3TetatakiResource.A1_15_2_IPHONE_ID,
				Vol3TetatakiResource.A1_15_1_IPHONE_TEMP_ID);
		mPhoneTextRegion = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_13_1_IPHONE_ID, Vol3TetatakiResource.A1_13_2_IPHONE_ID);
		mTissuesTextRegion  = getTiledTextureFromPacker(ttpTetataki4,
				Vol3TetatakiResource.A1_11_1_IPHONE_ID, Vol3TetatakiResource.A1_11_2_IPHONE_ID);
		mBimTextRegion = getTiledTextureFromPacker(ttpTetataki3,
				Vol3TetatakiResource.A1_10_1_IPHONE_ID, Vol3TetatakiResource.A1_10_2_3_IPHONE_ID);
		
		mBimText1 = ttpLibTetataki3.get(Vol3TetatakiResource.A1_10_2_1_IPHONE_ID);
		mBimText2 = ttpLibTetataki3.get(Vol3TetatakiResource.A1_10_2_2_IPHONE_ID); 
		mBimText3 = ttpLibTetataki3.get(Vol3TetatakiResource.A1_10_2_4_IPHONE_ID); 
		mWaterText1 = ttpLibTetataki4.get(Vol3TetatakiResource.A1_14_3_IPHONE_ID); 
		mWaterText2 = ttpLibTetataki4.get(Vol3TetatakiResource.A1_14_4_IPHONE_ID);
		mTissuesText = ttpLibTetataki4.get(Vol3TetatakiResource.A1_11_3_IPHONE_ID);
		//-----------------------man hinh 3--------------------------------
		mBottleTextRegion = getTiledTextureFromPacker(ttpTetataki3,
				Vol3TetatakiResource.A1_1_1_IPHONE_ID, Vol3TetatakiResource.A1_1_2_IPHONE_ID);
		
		mPotTextRegion = getTiledTextureFromPacker(ttpTetataki3,
				Vol3TetatakiResource.A1_2_1_IPHONE_ID, Vol3TetatakiResource.A1_2_2_IPHONE_ID);
		
		mBowlTextRegion = getTiledTextureFromPacker(ttpTetataki3,
				Vol3TetatakiResource.A1_8_1_IPHONE_ID, Vol3TetatakiResource.A1_8_2_IPHONE_ID);
		
		mChicken = getTiledTextureFromPacker(ttpTetataki3,
				Vol3TetatakiResource.A1_7_1_IPHONE_ID, Vol3TetatakiResource.A1_7_2_IPHONE_ID);
		
		mChickenText =ttpLibTetataki3.get(Vol3TetatakiResource.A1_7_2_IPHONE_ID);
		
		mBoard = getTiledTextureFromPacker(ttpTetataki3, Vol3TetatakiResource.A1_5_1_IPHONE_ID, 
				Vol3TetatakiResource.A1_5_2_IPHONE_ID, Vol3TetatakiResource.A1_5_3_IPHONE_ID);
		
		mGlass1 = getTiledTextureFromPacker(ttpTetataki3, 
				Vol3TetatakiResource.A1_4_1_IPHONE_ID, Vol3TetatakiResource.A1_4_5_IPHONE_ID);
		mGlass2 = getTiledTextureFromPacker(ttpTetataki3, Vol3TetatakiResource.A1_4_2_IPHONE_ID, 
				Vol3TetatakiResource.A1_4_6_1_IPHONE_ID, Vol3TetatakiResource.A1_4_6_2_IPHONE_ID);
		
		mGlass3 = getTiledTextureFromPacker(ttpTetataki3, Vol3TetatakiResource.A1_4_3_IPHONE_ID, 
				Vol3TetatakiResource.A1_4_7_1_IPHONE_ID, Vol3TetatakiResource.A1_4_7_2_IPHONE_ID);
		mGlass4 = getTiledTextureFromPacker(ttpTetataki3, Vol3TetatakiResource.A1_4_4_IPHONE_ID, 
				Vol3TetatakiResource.A1_4_8_1_IPHONE_ID, Vol3TetatakiResource.A1_4_8_2_IPHONE_ID);
		
		mBoxText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_3_1_IPHONE_ID);
		mPanText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_6_1_IPHONE_ID);
		mSpoonText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_6_2_IPHONE_ID);
		mPanText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_6_1_IPHONE_ID);
		
		mBoxStarText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_3_2_IPHONE_ID); 
		mPanStarText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_6_4_IPHONE_ID);
		mPanGlitText = ttpLibTetataki3.get(Vol3TetatakiResource.A1_6_3_IPHONE_ID);
		
	}

	
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		mBackground = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		mScene.attachChild(mBackground);
		mBackground.setColor(0.8352941f,1f,0.45f);
		//---------man hinh 1--------------------------------
		mToiletAnimateSprite = new AnimatedSprite(0, 206, mToiletTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mToiletAnimateSprite);
		
		mVacuumAniSpr = new AnimatedSprite(450, -5, mVacuumTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mVacuumAniSpr);
		
		mFaceAniSpr = new AnimatedSprite(544, 340, mFaceTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mFaceAniSpr);
		
		mBreastAniSpr = new AnimatedSprite(120, 14, mBreastTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBreastAniSpr);
		//---------------------------man hinh 2------------------------------
		
		mDoorAniSpr = new AnimatedSprite(615, -38, mDoorTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mDoorAniSpr);
		
		mTissuesAniSpr = new AnimatedSprite(335, -44, mTissuesTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mTissuesAniSpr);
		sprTissues = new Sprite(335, -44, mTissuesText, this.getVertexBufferObjectManager());
		mScene.attachChild(sprTissues);
		sprTissues.setVisible(false);
		
		sprBim1 = new Sprite(9, -55, mBimText1, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBim1);
		sprBim2 = new Sprite(9, -65, mBimText2, this.getVertexBufferObjectManager());
		mBimAniSpr = new AnimatedSprite(9, -55, mBimTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBimAniSpr);
		mScene.attachChild(sprBim2);
		sprBim3= new Sprite(9, -55, mBimText3, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBim3);
		
		setvisiableSprite(false, sprBim1,sprBim2,sprBim3);
		
		mBucketAniSpr = new AnimatedSprite(256, 339, mBucketTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBucketAniSpr); 
		
		mDrumAniSpr = new AnimatedSprite(671, 443, mDrumTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mDrumAniSpr); 
		
		mBalloonAniSpr = new AnimatedSprite(485, 253, mBalloonTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBalloonAniSpr);
		sprWater1 = new Sprite(207, 406, mWaterText1,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprWater1);
		sprWater2 = new Sprite(514, 406, mWaterText2,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprWater2);
		setvisiableSprite(false, sprWater1,sprWater2);
		mPhoneAniSpr = new AnimatedSprite(34, 343, mPhoneTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mPhoneAniSpr);
		
		setvisiableAniSprite(false, mDoorAniSpr, mTissuesAniSpr, mBucketAniSpr, mBalloonAniSpr, mPhoneAniSpr,
						mDrumAniSpr, mBimAniSpr);
		//-----------------man hinh 3 --------------------------
		mBottleAniSpr = new AnimatedSprite(51, 307, mBottleTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBottleAniSpr);
		
		mPotAniSpr = new AnimatedSprite(202, 371, mPotTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mPotAniSpr);
		
		mGlassAniSpr1 = new AnimatedSprite(424, 43, mGlass1, this.getVertexBufferObjectManager());
		mScene.attachChild(mGlassAniSpr1);
		mGlassAniSpr2 = new AnimatedSprite(576, 36, mGlass2, this.getVertexBufferObjectManager());
		mScene.attachChild(mGlassAniSpr2);
		mGlassAniSpr3 = new AnimatedSprite(298, 43, mGlass3, this.getVertexBufferObjectManager());
		mScene.attachChild(mGlassAniSpr3);
		mGlassAniSpr4 = new AnimatedSprite(136, 40, mGlass4, this.getVertexBufferObjectManager());
		mScene.attachChild(mGlassAniSpr4);
		
		sprBox = new Sprite(179, 207, mBoxText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprBox);
		sprBoxStar = new Sprite(166, 177, mBoxStarText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprBoxStar);
		mBowlAniSpr = new AnimatedSprite(719, 77, mBowlTextRegion, this.getVertexBufferObjectManager());
		mScene.attachChild(mBowlAniSpr);
		
		mChickenAniSpr = new AnimatedSprite(760, 268, mChicken, this.getVertexBufferObjectManager());
		mScene.attachChild(mChickenAniSpr);
		sprChicken = new Sprite(760, 268, mChickenText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprChicken);
		sprPan = new Sprite(460, 119, mPanText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprPan);
		sprSpoon = new Sprite(553, 148, mSpoonText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprSpoon); 
		
		sprPanStar = new Sprite(496, 139, mPanStarText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprPanStar);
		sprPanGlit = new Sprite(492, 190, mPanGlitText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprPanGlit);
		
		setvisiableSprite(false, sprPanStar, sprPanGlit, sprBoxStar, sprChicken);
		
		mBoardAniSpr = new AnimatedSprite(495, 341, mBoard, this.getVertexBufferObjectManager());
		mScene.attachChild(mBoardAniSpr);
		setvisiableAniSprite(false, mBoardAniSpr,mChickenAniSpr,mBowlAniSpr,mPotAniSpr,mBottleAniSpr,
				mGlassAniSpr1,mGlassAniSpr2,mGlassAniSpr3,mGlassAniSpr4);
		setvisiableSprite(false, sprSpoon,sprPan,sprBox);
		sprHand = new Sprite(0, 0, mHandText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprHand);
		setvisiableSprite(false, sprHand);
		sprGimmic = new Sprite(436, 496, mGimmicText,  this.getVertexBufferObjectManager());
		mScene.attachChild(sprGimmic);
		
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		
		
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		int pX = (int) pTouchEvent.getX();
		int pY = (int) pTouchEvent.getY();
		if (pTouchEvent.isActionDown()) {
			if (sprGimmic.contains(pX, pY)){
				touchGimmic();
			} else if (checkContains(mFaceAniSpr, 46, 40, 335, 272, pX, pY) && mFaceAniSpr.isVisible()) {
				touchFace(pX, pY);
			} else if (checkContains(mBucketAniSpr, 31, 18, 249, 220, pX, pY) && mBucketAniSpr.isVisible()) {
				touchBucket(pX, pY);
			} else if (checkContains(mBoardAniSpr, 51, 128, 380, 260, pX, pY) && mBoardAniSpr.isVisible()) {
				touchBoard(pX, pY);
			}
			//----------------man hinh 1---------------------
			if (checkContains(mBreastAniSpr, 14, 18, 239, 199, pX, pY)&&mBreastAniSpr.isVisible()) {
				touchBreast(pX,pY);
			}
			if (checkContains(mVacuumAniSpr, 22, 4, 462, 335, pX, pY)&&mVacuumAniSpr.isVisible()) {
				touchVacuum(pX, pY);
			}
			if (checkContains(mToiletAnimateSprite, 366, 147, 431, 329, pX, pY)&& mToiletAnimateSprite.isVisible()) {
				touchToilet1(pX, pY);
			}
			if (checkContains(mToiletAnimateSprite, 97, 15, 346, 408, pX, pY)&& mToiletAnimateSprite.isVisible()) {
				touchToilet2();
			}
			//------------- man hinh 2--------------------
			if (checkContains(mPhoneAniSpr, 41, 14, 213, 260, pX, pY)&& mPhoneAniSpr.isVisible()) {
				touchPhone(pX, pY);
			} 
			if (checkContains(mDoorAniSpr, 75, 51, 337, 464, pX, pY)&&mDoorAniSpr.isVisible()) {
				touchDoor(pX, pY);
			}
			if (checkContains(mDrumAniSpr, 6, 28, 188, 160, pX, pY) && mDrumAniSpr.isVisible()) {
				touchDrum(pX, pY);
			}
			if (checkContains(mBalloonAniSpr, 21, 9, 182, 200, pX, pY)&& mBalloonAniSpr.isVisible()) {
				touchBall(pX, pY);
			} 
			if (checkContains(mBimAniSpr, 60, 145, 289, 384, pX, pY)&& mBimAniSpr.isVisible()) {
				touchBim(pX, pY);
			}	
			if (checkContains(mTissuesAniSpr, 48, 168, 274, 285, pX, pY)&& mTissuesAniSpr.isVisible()) {
				touchTissues(pX, pY);
			}	
			//-------------------- man hinh 3-------------------
			if (mBottleAniSpr.contains(pX, pY) && mBottleAniSpr.isVisible()) {
				touchBottle(pX, pY);
			}
			if (checkContains(mPotAniSpr, 32, 64, 214, 194, pX, pY)&& mPotAniSpr.isVisible()) {
				touchPot(pX, pY);
			}
			if (checkContains(mChickenAniSpr, 2, 51, 174, 150, pX, pY)&& mChickenAniSpr.isVisible()) {
				touchChicken(pX, pY);
			}
			if (checkContains(mBowlAniSpr, 2, 10, 141, 88, pX, pY)&& mBowlAniSpr.isVisible()) {
				touchBowl(pX, pY);
			}
			if (checkContains(sprPan, 7, 96, 278, 281, pX, pY)&& sprPan.isVisible()) {
				touchPan(pX, pY);
			}
			if (checkContains(mGlassAniSpr1, 19, 17, 94, 129, pX, pY)&&mGlassAniSpr1.isVisible()) {
				touchGlass1(pX, pY);
			}
			if (checkContains(mGlassAniSpr2, 2, 36, 92, 129, pX, pY)&&mGlassAniSpr2.isVisible()) {
				touchGlass2(pX, pY);
			}
			if (checkContains(mGlassAniSpr3, 2, 19, 75, 129, pX, pY)&&mGlassAniSpr3.isVisible()) {
				touchGlass3(pX, pY);
			}
			if (checkContains(mGlassAniSpr4, 12, 12, 92, 129, pX, pY)&&mGlassAniSpr4.isVisible()) {
				touchGlass4(pX, pY);
			}
			if (checkContains(sprBox, 1, 11, 250, 130, pX, pY) && sprBox.isVisible()) {
				touchBox(pX, pY);
			}
		}
		
		return false;
	}
	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		if (arg0.equals(mFaceAniSpr)){
			touchFace =true;
		}
		if (arg0.equals(mBoardAniSpr)) {
			touchBoard = true;
		}
		if (arg0.equals(mDrumAniSpr)) {
			touchDrum = true;
		}
		if (arg0.equals(mBottleAniSpr)) {
			touchBottle = true;
		}
		if (arg0.equals(mPotAniSpr)) {
			touchPot = true;
		}
		if (arg0.equals(mChickenAniSpr)) {
			touchChicken = true;
		}
		if (arg0.equals(mBowlAniSpr)) {
			touchBowl = true;
		}
		if (arg0.equals(mGlassAniSpr2)) {
			touchGlass2 = true;
		}
		if (arg0.equals(mGlassAniSpr3)) {
			touchGlass3 = true;
		}
		if (arg0.equals(mGlassAniSpr4)) {
			touchGlass4 = true;
		}
		if (arg0.equals(mBreastAniSpr)) {
			touchBreast = true;
			touchToilet2 = true;
		}
		if (arg0.equals(mPhoneAniSpr)) {
			touchPhone = true;
		}
		if (arg0.equals(mBucketAniSpr)) {
			touchBucket = true;
			sprWater1.setVisible(false);
			sprWater2.setVisible(false);
		}
		if (arg0.equals(mBalloonAniSpr)) {
			touchBall = true;
		}
		
		
	}
	private void appearHand(int pX, int pY) {
		sprHand.setPosition(pX-80, pY-80);
		sprHand.setVisible(true);
		mEngine.unregisterUpdateHandler(timerHand);
		timerHand = new TimerHandler(0.5f,false, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sprHand.setVisible(false);
			}
		});
		mEngine.registerUpdateHandler(timerHand);
	}
	
	//--------------------screen 1-----------------
	private void touchToilet1(int pX, int pY){
		if (touchToilet1) {
			touchToilet1 = false;
			touchToilet2 = false;
			appearHand(pX, pY);
			A1_18_TOIRE.play();
			mToiletAnimateSprite.animate(new long[]{1000,400}, new int[]{1,0},0,new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchToilet1 = true;
					touchToilet2 = true;
				}
			});
			
		}
	}
	private void touchToilet2() {
		if (touchToilet2) {
			touchToilet1 = false;
			touchToilet2 = false;
			touchBreast = false;
			int randomSound = new Random().nextInt(16);
			for (int i = 0; i < arrSoundOnara.length; i++) {
				Log.d(TAG, "gia tri i: "+i);
				if (i==randomSound) {
					Log.d(TAG, "gia tri random: "+randomSound);
					arrSoundOnara[randomSound].play();
				}
			}
			A1_18_TOIRE.stop();
			mToiletAnimateSprite.stopAnimation(0);
			mBreastAniSpr.stopAnimation();
			mBreastAniSpr.setCurrentTileIndex(0);
			mBreastAniSpr.setPosition(mBreastAniSpr.getmXFirst()-30, mBreastAniSpr.getmYFirst()+245);
			TimerHandler timeToilet = new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					mBreastAniSpr.setPosition(mBreastAniSpr.getmXFirst(), mBreastAniSpr.getmYFirst());
					
					touchToilet1 = true;
					touchToilet2 = true;
					touchBreast = true;
				}
			});
			mEngine.registerUpdateHandler(timeToilet);
		}
	}
	private void touchVacuum(int pX, int pY){
		if (touchVacuum) {
			touchVacuum= false;
			appearHand(pX,pY);
			A1_19_SOUJIKI.play();
			mVacuumAniSpr.setCurrentTileIndex(1);
			mVacuumAniSpr.registerEntityModifier(new SequenceEntityModifier(
					new MoveXModifier(0.45f, mVacuumAniSpr.getX(), mVacuumAniSpr.getX()-18),
					new MoveXModifier(0.85f, mVacuumAniSpr.getX()-18, mVacuumAniSpr.getX()+18),
					new MoveXModifier(0.85f, mVacuumAniSpr.getX()+18, mVacuumAniSpr.getX()-18),
					new MoveXModifier(0.45f, mVacuumAniSpr.getX()-18, mVacuumAniSpr.getX(), new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							mVacuumAniSpr.setCurrentTileIndex(0);
							touchVacuum = true;
						}
					})));
		}
	}
	private void touchBreast(int pX,int pY) {
		if (touchBreast) {
			touchBreast = false;
			touchToilet2 = false;
			A1_20_PURIPURI.play();
			appearHand(pX, pY);
			mBreastAniSpr.animate(new long[]{100,100,100,100,100}, new int[]{1,2,1,2,0},0,this);
			
		}
	}
	//-----------------------screen 2----------------
	private void touchBim(int pX, int pY) {
		if (touchBim) {
			touchBim = false;
			setvisiableSprite(true, sprBim1,sprBim2,sprBim3);
			mBimAniSpr.setCurrentTileIndex(1);
			A1_10_PAN.play();
			appearHand(pX, pY);
			sprBim2.registerEntityModifier(new MoveYModifier(0.8f, sprBim2.getY(), -210,
					new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					setvisiableSprite(false, sprBim1,sprBim2,sprBim3);
					mBimAniSpr.setCurrentTileIndex(0);
					touchBim = true;
				}
			}));
			
		}
	}
	private void touchTissues(int pX, int pY) {
		if (touchTissues) {
			touchTissues = false;
			sprTissues.setVisible(true);
			mTissuesAniSpr.setCurrentTileIndex(1);
			if (countTissues == 0) {
				countTissues = 1;
				A1_11_TISH1.play();
			} else if (countTissues == 1) {
				countTissues = 0;
				A1_11_TISH2.play();
			}
			appearHand(pX, pY);
			sprTissues.registerEntityModifier(new MoveYModifier(0.8f, sprTissues.getY(), -291,
					new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					sprTissues.setVisible(false);
					sprTissues.setPosition(sprTissues.getmXFirst(), sprTissues.getmYFirst());
					mTissuesAniSpr.setCurrentTileIndex(0);
					touchTissues = true;
				}
			}));
		}
	}
	private void touchBall(int pX, int pY) {
		if (touchBall) {
			touchBall = false;
			A1_15_FUSENWARERU.play();
			appearHand(pX, pY);
			mBalloonAniSpr.animate(new long[]{800,600,400},new int[]{1,2,0},0,this);
			
		}
	}
	private void touchPhone(int pX, int pY) {
		if (touchPhone) {
			touchPhone = false;
			if (countPhone == 0) {
				countPhone = 1;
				A1_13_KEITAI1.play();
			} else if (countPhone == 1) {
				countPhone = 0;
				A1_13_KEITAI2.play();
			}
			appearHand(pX, pY);
			mPhoneAniSpr.animate(new long[]{1100,400}, new int[]{1,0},0,this );
		}
	}
	private void touchDoor(int pX, int pY) {
		if (touchDoor) {
			touchDoor = false;
			A1_16_DOOR.play();
			appearHand(pX, pY);
			int idSound = new Random().nextInt(4);
			for (int i = 0; i < arrSoundBoy.length; i++) {
				if (i==idSound) {
					arrSoundBoy[idSound].play();
				}
			}
			mDoorAniSpr.animate(new long[]{1200,400},new int []{1,0},0, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					touchDoor = true;
				}
			});
			
		}
	}
	private void touchDrum(int pX, int pY) {
		if (touchDrum) {
			touchDrum = false;
			A1_12_TAMBALIN.play();
			appearHand(pX, pY);
			mDrumAniSpr.animate(new long[]{1200,400}, new int[]{1,0},0 ,this);
		}
	}
	private void touchBucket(int pX, int pY) {
		if (touchBucket) {
			touchBucket = false;
			A1_14_GATA.play();
			sprWater1.setVisible(true);
			sprWater2.setVisible(true);
			appearHand(pX, pY);
			mBucketAniSpr.animate(new long[]{800,100}, new int[]{1,0},0,this);
			
		}
	}
	//--------------- screen 3 ---------------
	private void touchBox(int pX, int pY) {
		if (touchBox) {
			touchBox = false;
			sprBoxStar.setVisible(true);
			if (countBox == 0) {
				countBox = 1;
				A1_3_OKASHI1.play();
			} else if (countBox == 1) {
				countBox = 0;
				A1_3_OKASHI2.play();
			}
			appearHand(pX, pY);
			sprBox.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.3f, 0, -15, sprBox.getWidth()/2, sprBox.getHeight()/2),
					new RotationAtModifier(0.6f, -15, 15, sprBox.getWidth()/2, sprBox.getHeight()/2),
					new RotationAtModifier(0.3f, 15, 0, sprBox.getWidth()/2, sprBox.getHeight()/2, 
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchBox = true;
							sprBoxStar.setVisible(false);
						}
					})));
		}
	}
	private void touchGlass1(int pX, int pY) {
		if (touchGlass1) {
			touchGlass1 = false;
			int randomSound = new Random().nextInt(3);
			switch (randomSound) {
			case 0:
				A1_4_5_1.play();
				break;
			case 1:
				A1_4_5_2.play();
				break;	
			
			case 2:
				A1_4_5_3.play();
				break;		
			default:
				break;
			}
			appearHand(pX, pY);
			mGlassAniSpr1.setCurrentTileIndex(1);
			mGlassAniSpr1.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.3f, 0, 5, 61, 127),
					new RotationAtModifier(0.6f, 5, -5, 61, 127),
					new RotationAtModifier(0.3f, -5, 0, 61, 127, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							touchGlass1 = true;
							mGlassAniSpr1.setCurrentTileIndex(0);
						}
					})));
		}
	}
	private void touchGlass2(int pX, int pY) {
		if (touchGlass2) {
			touchGlass2 = false;
			int randomSound = new Random().nextInt(3);
			switch (randomSound) {
			case 0:
				A1_4_6_1.play();
				break;
			case 1:
				A1_4_6_2.play();
				break;	
			
			case 2:
				A1_4_6_3.play();
				break;		
			default:
				break;
			}
			appearHand(pX, pY);
			mGlassAniSpr2.animate(new long[]{500,500,400}, new int[]{1,2,0},0, this);
			
		}
	}
	private void touchGlass3(int pX, int pY) {
		if (touchGlass3) {
			touchGlass3 = false;
			int randomSound = new Random().nextInt(3);
			switch (randomSound) {
			case 0:
				A1_4_7_1.play();
				break;
			case 1:
				A1_4_7_2.play();
				break;	
			
			case 2:
				A1_4_7_3.play();
				break;		
			default:
				break;
			}
			appearHand(pX, pY);
			mGlassAniSpr3.animate(new long[]{500,500,400}, new int[]{1,2,0},0, this);
		}
	}
	private void touchGlass4(int pX, int pY) {
		if (touchGlass4) {
			touchGlass4 = false;
			int randomSound = new Random().nextInt(3);
			switch (randomSound) {
			case 0:
				A1_4_8_1.play();
				break;
			case 1:
				A1_4_8_2.play();
				break;	
			
			case 2:
				A1_4_8_3.play();
				break;		
			default:
				break;
			}
			appearHand(pX, pY);
			mGlassAniSpr4.animate(new long[]{500,500,400}, new int[]{1,2,0},0, this);
		}
	}
	private void touchBowl(int pX, int pY) {
		if (touchBowl) {
			touchBowl = false;
			if (countBowl == 0) {
				countBowl = 1;
				A1_8_GOCHISOUSAMA.play();
			} else if (countBowl == 1) {
				countBowl = 0;
				A1_8_ITADAKIMASU.play();
			}
			appearHand(pX, pY);
			mBowlAniSpr.animate(new long[]{1200,300}, new int[]{1,0},0 ,this);
		}
	}
	private void touchPan(int pX, int pY) {
		if (touchPan) {
			touchPan = false;
			setvisiableSprite(true, sprPanGlit,sprPanStar);
			if (countPan == 0) {
				countPan = 1;
				A1_6_FLYPAN1.play();
			} else if (countPan == 1) {
				countPan = 0;
				A1_6_FLYPAN2.play();
			}
			appearHand(pX, pY);
			sprSpoon.setRotation(30);
			
			TimerHandler timerPan = new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					setvisiableSprite(false, sprPanGlit,sprPanStar);
					sprSpoon.setRotation(0);
					touchPan = true;
				}
			});
			mEngine.registerUpdateHandler(timerPan);
			
		}
	}
	private void touchChicken(int pX, int pY) {
		if (touchChicken) {
			touchChicken = false;
			if (countchicken == 0) {
				countchicken = 1;
				A1_7_TYAWAN1.play();
			} else if (countchicken == 1) {
				countchicken = 0;
				A1_7_TYAWAN2.play();
			}
			appearHand(pX, pY);
			sprChicken.setVisible(true);
			TimerHandler timeChicken = new TimerHandler(1.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mEngine.unregisterUpdateHandler(arg0);
					sprChicken.setVisible(false);
					touchChicken = true;
				}
			});
			mEngine.registerUpdateHandler(timeChicken);
		}
	}
	private void touchPot(int pX, int pY) {
		if (touchPot) {
			touchPot = false;
			if (countPot == 0) {
				countPot = 1;
				A1_2_NABE1.play();
			} else if (countPot == 1) {
				countPot = 0;
				A1_2_NABE2.play();
			}
			appearHand(pX, pY);
			mPotAniSpr.animate(new long[]{900,400}, new int[]{1,0},0 ,this);
		}
	}
	private void touchBottle(int pX, int pY) {
		if (touchBottle) {
			touchBottle = false;
			if (countBottle == 0) {
				countBottle = 1;
				A1_1_PET1.play();
			} else if (countBottle == 1) {
				countBottle = 0;
				A1_1_PET2.play();
			}
			appearHand(pX, pY);
			mBottleAniSpr.animate(new long[]{800,400}, new int[]{1,0},0 ,this);
		}
	}
	
	private void touchBoard(int pX, int pY) {
		if (touchBoard) {
			touchBoard = false;
			if (countBoard == 0) {
				countBoard = 1;
				A1_5_TON3.play();
			} else if (countBoard == 1) {
				countBoard = 0;
				A1_5_TON2.play();
			}
			appearHand(pX, pY);
			mBoardAniSpr.animate(new long[]{400,400,400}, new int[]{1,2,0},0 ,this);
			
		}
	}
	private void touchFace(int pX,int pY) {
		if (touchFace) {
			touchFace =false;
			appearHand(pX,pY);
			A1_22_AHAHA.play();
			mFaceAniSpr.animate(new long[]{1400,400}, new int[]{1,0},0 ,this);
			
		}
	}
	private void touchGimmic() {
		A1_HATENA_MOKIN.play();
		sprGimmic.registerEntityModifier(new SequenceEntityModifier(
				new ScaleModifier(0.35f, 1, 1.3f),
				new ScaleModifier(0.35f, 1.3f, 1f)));
		if (countBg == 0) {
			//screen 2
			countBg = 1;
			stopSound();
			mBackground.setColor(0.56f, 0.937254f, 1f);
			screen2();
			//clear action screen 1
			mVacuumAniSpr.clearEntityModifiers();
			mVacuumAniSpr.setCurrentTileIndex(0);
			touchVacuum = true;
			stopAniSpr(mBreastAniSpr);
			touchBreast = true;
		} else if (countBg == 1) {
			//screen 3
			countBg = 2;
			stopSound();
			mBackground.setColor(0.9764706f,0.73333333f,0.7686275f);
			screen3();
			//clear action screen 2
			sprBim2.clearEntityModifiers();
			setvisiableSprite(false, sprBim1,sprBim2,sprBim3);
			mBimAniSpr.setCurrentTileIndex(0);
			touchBim = true;
			sprTissues.clearEntityModifiers();
			sprTissues.setVisible(false);
			sprTissues.setPosition(sprTissues.getmXFirst(), sprTissues.getmYFirst());
			mTissuesAniSpr.setCurrentTileIndex(0);
			touchTissues = true;
			stopAniSpr(mBalloonAniSpr, mDoorAniSpr, mPhoneAniSpr, mDrumAniSpr, mBucketAniSpr);
			touchBall=true;
			touchDoor = true;
			touchPhone = true;
			touchDrum = true;
			touchBucket = true;
			setvisiableSprite(false, sprWater1,sprWater2);
			
		} else if (countBg == 2) {
			// screen 1
			countBg = 0;
			stopSound();
			mBackground.setColor(0.8352941f, 1f, 0.45f);
			screen1();
			//clear action screen 3
			setvisiableSprite(false, sprPanGlit,sprPanStar);
			sprSpoon.setRotation(0);
			sprBox.clearEntityModifiers();
			sprBox.setRotation(0);
			touchBox = true;
			sprBoxStar.setVisible(false);
			stopAniSpr(mChickenAniSpr, mBowlAniSpr);
			touchChicken = true;
			touchBowl = true;
		}
	}
	private void stopAniSpr(AnimatedSprite... aniSpr) {
		for (int i = 0; i < aniSpr.length; i++) {
			aniSpr[i].stopAnimation();
			aniSpr[i].setCurrentTileIndex(0);
		}
	}
	private void screen1(){
		//hide screen 3
		setvisiableAniSprite(false, mBoardAniSpr,mChickenAniSpr,mBowlAniSpr,mPotAniSpr,mBottleAniSpr,
				mGlassAniSpr1,mGlassAniSpr2,mGlassAniSpr3,mGlassAniSpr4);
		setvisiableSprite(false, sprSpoon,sprPan,sprBox);
		setvisiableSprite(false, sprPanStar, sprPanGlit, sprBoxStar);
		// appear screen 1
		sprHand.setVisible(false);
		setvisiableAniSprite(true, mToiletAnimateSprite,mVacuumAniSpr,mFaceAniSpr,mBreastAniSpr);
	}
	private void screen2(){
		// hide screen 1
		setvisiableAniSprite(false, mToiletAnimateSprite,mVacuumAniSpr,mFaceAniSpr,mBreastAniSpr);
		// appear screen 2
		sprHand.setVisible(false);
		setvisiableAniSprite(true, mDoorAniSpr, mTissuesAniSpr, mBucketAniSpr, mBalloonAniSpr, mPhoneAniSpr,
				mDrumAniSpr, mBimAniSpr);
	}
	private void screen3(){
		//hide screen 2
		setvisiableAniSprite(false, mDoorAniSpr, mTissuesAniSpr, mBucketAniSpr, mBalloonAniSpr, mPhoneAniSpr,
				mDrumAniSpr, mBimAniSpr);
		// appear screen 3
		sprHand.setVisible(false);
		setvisiableAniSprite(true, mBoardAniSpr,mChickenAniSpr,mBowlAniSpr,mPotAniSpr,mBottleAniSpr,
				mGlassAniSpr1,mGlassAniSpr2,mGlassAniSpr3,mGlassAniSpr4);
		setvisiableSprite(true, sprSpoon,sprPan,sprBox);
	}
	private void resetData(){
		mBackground.setColor(0.8352941f, 1f, 0.45f);
		setvisiableAniSprite(true, mToiletAnimateSprite,mVacuumAniSpr,mFaceAniSpr,mBreastAniSpr);
		setvisiableAniSprite(false, mDoorAniSpr, mTissuesAniSpr, mBucketAniSpr, mBalloonAniSpr, mPhoneAniSpr,
				mDrumAniSpr, mBimAniSpr);
		setvisiableSprite(false, sprPanStar, sprPanGlit, sprBoxStar, sprChicken);
		setvisiableAniSprite(false, mBoardAniSpr,mChickenAniSpr,mBowlAniSpr,mPotAniSpr,mBottleAniSpr,
				mGlassAniSpr1,mGlassAniSpr2,mGlassAniSpr3,mGlassAniSpr4);
		setvisiableSprite(false, sprSpoon,sprPan,sprBox);
		if (mVacuumAniSpr!=null) {
			mVacuumAniSpr.clearEntityModifiers();
			mVacuumAniSpr.setCurrentTileIndex(0);
		}
		if (mTissuesAniSpr != null) {
			mTissuesAniSpr.clearEntityModifiers();
			mTissuesAniSpr.setCurrentTileIndex(0);
			
		}
		if (mGlassAniSpr1 != null) {
			mGlassAniSpr1.clearEntityModifiers();
			mGlassAniSpr1.stopAnimation();
			mGlassAniSpr1.setCurrentTileIndex(0);
		}
		if (mGlassAniSpr2 != null) {
			mGlassAniSpr2.clearEntityModifiers();
			mGlassAniSpr2.stopAnimation();
			mGlassAniSpr2.setCurrentTileIndex(0);
			
		}
		if (mGlassAniSpr3 != null) {
			mGlassAniSpr3.clearEntityModifiers();
			mGlassAniSpr3.stopAnimation();
			mGlassAniSpr3.setCurrentTileIndex(0);
		}
		if (mGlassAniSpr4 != null) {
			mGlassAniSpr4.clearEntityModifiers();
			mGlassAniSpr4.stopAnimation();
			mGlassAniSpr4.setCurrentTileIndex(0);
		}
		if (mChickenAniSpr!= null) {
			mChickenAniSpr.clearEntityModifiers();
			mChickenAniSpr.stopAnimation();
			mChickenAniSpr.setCurrentTileIndex(0);
		}
		
	}
	@Override
	public synchronized void onPauseGame() {
		resetData();
		stopSound();
		super.onPauseGame();
	}
	
	@Override
	public synchronized void onResumeGame() {
		initial();
		super.onResumeGame();
	}
	private void stopSound() {
		
			A1_10_PAN.stop(); 
			A1_11_TISH1.stop();
			A1_11_TISH2.stop();
			A1_12_TAMBALIN.stop();
			A1_13_KEITAI1.stop();
			A1_13_KEITAI2.stop();
			A1_14_GATA.stop();
			A1_15_FUSENWARERU.stop();
			A1_16_DOOR.stop();
			A1_18_TOIRE.stop();
			A1_19_SOUJIKI.stop();
			A1_1_PET1.stop();
			A1_1_PET2.stop();
			A1_20_PURIPURI.stop();
			A1_22_AHAHA.stop();
			A1_2_NABE1.stop();
			A1_2_NABE2.stop();
			A1_3_OKASHI1.stop();
			A1_3_OKASHI2.stop();
			A1_5_TON2.stop();
			A1_5_TON3.stop();
			A1_6_FLYPAN1.stop();
			A1_6_FLYPAN2.stop();
			A1_7_TYAWAN1.stop();
			A1_7_TYAWAN2.stop();
			A1_8_GOCHISOUSAMA.stop();
			A1_8_ITADAKIMASU.stop();
			A1_16_BOY1.stop();
			A1_16_BOY2.stop();
			A1_16_BOY3.stop();
			A1_16_BOY4.stop();
			A1_4_8_3.stop();
			A1_4_5_1.stop();
			A1_4_5_2.stop();
			A1_4_5_3.stop();
			A1_4_6_1.stop();
			A1_4_6_2.stop();
			A1_4_6_3.stop();
			A1_4_7_1.stop();
			A1_4_7_2.stop();
			A1_4_7_3.stop();
			A1_4_7_4.stop();
			A1_4_8_1.stop();
			A1_4_8_2.stop();
			A1_4_8_4.stop();
			A1_20_ONARA10.stop();
			A1_20_ONARA0.stop();
			A1_20_ONARA12.stop();
			A1_20_ONARA2.stop();
			A1_20_ONARA11.stop();
			A1_20_ONARA8.stop(); 
			A1_20_ONARA7.stop(); 
			A1_20_ONARA1.stop();
			A1_20_ONARA9.stop();
			A1_20_ONARA15.stop();
			A1_20_ONARA5.stop(); 
			A1_20_ONARA14.stop();
			A1_20_ONARA4.stop();
			A1_20_ONARA13.stop();
			A1_20_ONARA6.stop();
			A1_20_ONARA3.stop();
		
	}
	@Override
	public void combineGimmic3WithAction() {}

	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}
	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
	
	private void setvisiableSprite(boolean value, Sprite ...sprites){
		if (sprites != null) {
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].setPosition(sprites[i].getmXFirst(), sprites[i].getmYFirst());
				sprites[i].setVisible(value);
				
			}
		}
	}
	private void setvisiableAniSprite(boolean value, AnimatedSprite ...aniSprites){
		if (aniSprites != null) {
			for (int i = 0; i < aniSprites.length; i++) {
				aniSprites[i].setVisible(value);
				aniSprites[i].setPosition(aniSprites[i].getmXFirst(), aniSprites[i].getmYFirst());
				aniSprites[i].setCurrentTileIndex(0);
				
			}
			
		}
	}
	
	@Override
	protected void loadKaraokeSound() {
		A1_10_PAN = loadSoundResourceFromSD(Vol3TetatakiResource.A1_10_PAN);
		A1_11_TISH1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_11_TISH1);
		A1_11_TISH2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_11_TISH2);
		A1_12_TAMBALIN = loadSoundResourceFromSD(Vol3TetatakiResource.A1_12_TAMBALIN);
		A1_13_KEITAI1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_13_KEITAI1);
		A1_13_KEITAI2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_13_KEITAI2);
		A1_14_GATA = loadSoundResourceFromSD(Vol3TetatakiResource.A1_14_GATA);
		A1_15_FUSENWARERU = loadSoundResourceFromSD(Vol3TetatakiResource.A1_15_FUSENWARERU);
		A1_16_DOOR = loadSoundResourceFromSD(Vol3TetatakiResource.A1_16_DOOR);
		A1_18_TOIRE = loadSoundResourceFromSD(Vol3TetatakiResource.A1_18_TOIRE);
		A1_19_SOUJIKI = loadSoundResourceFromSD(Vol3TetatakiResource.A1_19_SOUJIKI);
		A1_1_PET1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_1_PET1);
		A1_1_PET2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_1_PET2);
		A1_20_PURIPURI = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_PURIPURI);
		A1_22_AHAHA = loadSoundResourceFromSD(Vol3TetatakiResource.A1_22_AHAHA);
		A1_2_NABE1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_2_NABE1);
		A1_2_NABE2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_2_NABE2);
		A1_3_OKASHI1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_3_OKASHI1);
		A1_3_OKASHI2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_3_OKASHI2);
		A1_5_TON2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_5_TON2);
		A1_5_TON3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_5_TON3);
		A1_6_FLYPAN1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_6_FLYPAN1);
		A1_6_FLYPAN2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_6_FLYPAN2);
		A1_7_TYAWAN1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_7_TYAWAN1);
		A1_7_TYAWAN2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_7_TYAWAN2);
		A1_8_GOCHISOUSAMA = loadSoundResourceFromSD(Vol3TetatakiResource.A1_8_GOCHISOUSAMA);
		A1_8_ITADAKIMASU = loadSoundResourceFromSD(Vol3TetatakiResource.A1_8_ITADAKIMASU);
		A1_HATENA_MOKIN = loadSoundResourceFromSD(Vol3TetatakiResource.A1_HATENA_MOKIN);
		A1_16_BOY1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_16_BOY1);
		A1_16_BOY2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_16_BOY2);
		A1_16_BOY3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_16_BOY3);
		A1_16_BOY4 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_16_BOY4);
		A1_4_5_1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_5_1);
		A1_4_5_2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_5_2);
		A1_4_5_3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_5_3);
		A1_4_6_1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_6_1);
		A1_4_6_2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_6_2);
		A1_4_6_3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_6_3);
		A1_4_7_1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_7_1);
		A1_4_7_2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_7_2);
		A1_4_7_3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_7_3);
		A1_4_7_4 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_7_4);
		A1_4_8_1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_8_1);
		A1_4_8_2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_8_2);
		A1_4_8_3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_8_3);
		A1_4_8_4 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_4_8_4);
		A1_20_ONARA0 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA0);
		A1_20_ONARA1 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA1);
		A1_20_ONARA2 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA2);
		A1_20_ONARA3 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA3);
		A1_20_ONARA4 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA4);
		A1_20_ONARA5 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA5);
		A1_20_ONARA6 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA6);
		A1_20_ONARA7 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA7);
		A1_20_ONARA8 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA8);
		A1_20_ONARA9 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA9);
		A1_20_ONARA10 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA16);
		A1_20_ONARA11 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA11);
		A1_20_ONARA12 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA12);
		A1_20_ONARA13 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA13);
		A1_20_ONARA14 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA14);
		A1_20_ONARA15 = loadSoundResourceFromSD(Vol3TetatakiResource.A1_20_ONARA15);
		arrSoundBoy = new Sound[]{A1_16_BOY1,A1_16_BOY2,A1_16_BOY3,A1_16_BOY4};
		arrSoundOnara = new Sound[] {A1_20_ONARA0,A1_20_ONARA1,A1_20_ONARA2,A1_20_ONARA3,A1_20_ONARA4, 
				A1_20_ONARA5, A1_20_ONARA6, A1_20_ONARA7,A1_20_ONARA8,A1_20_ONARA9, A1_20_ONARA10,A1_20_ONARA11,
				A1_20_ONARA12,A1_20_ONARA13,A1_20_ONARA14,A1_20_ONARA15
		};
	}

}
