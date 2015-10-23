package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KintaroResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.input.touch.controller.ITouchController;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.input.touch.controller.SingleTouchController;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.util.Log;

public class Vol3Kintaro extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity>, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_KIRANTO";
	private static final int LEFT_WIN = 0, RIGHT_WIN = 1;
	private static final int MAX_WIN = 10;
	private static final int TOP_KIRANTO_ON_STAGE = 432;
	private static final int LEFT_KIRANTO_LEFT_ON_STAGE = 215;
	private static final int LEFT_KIRANTO_RIGHT_ON_STAGE = 600;
	private static final int DISTANCE_MOVE_ON_STAGE = 5;

	// ===========================================================
	// Fields
	// ===========================================================
	private ITouchController defaultTouchController = new SingleTouchController();
	/**
	 * Texture, TextureRegion, TiledTextureRegion, Sprite, AnimatedSprite
	 */

	private ITextureRegion ttrRBackground, ttrRBackground2,
			arrTtrRKumo[] = new TextureRegion[4];

	private TiledTextureRegion tiledTtrSunInline, tiledTtrSunOutline,
			tiledTtrFuji, tiledTtrBird,
			arrTiledTtrFlowers[] = new TiledTextureRegion[7], tiledTtrKintaro,
			tiledTtrBear, tiledTtrHedgehod, tiledTtrSquirrel, tiledTtrMonkey,
			tiledTtrBadger, tiledTtrRabbit, tiledTtrBoar, tiledTtrStage,
			tiledTtrFlower14, tiledTtrKenmushi, tiledTtrKamihubuki,
			tiledTtrTaikoLeft, tiledTtrTaikoRight;

	private Sprite sprBackground2, arrSprKumo[] = new Sprite[4];

	private AnimatedSprite anmSprSunInline, anmSprSunOutline, anmSprFuji,
			anmSprBird, arrAnmSprFlowers[] = new AnimatedSprite[7],
			arrAnmSprKintaro[] = new AnimatedSprite[8], anmSprStage,
			anmSprFlower14, anmSprKenmushi, anmSprKamihubuki, anmSprTaikoLeft,
			anmSprTaikoRight;

	private int arrPointKumo[][] = { { 91, 233, 499, 739 }, { 7, 12, 1, 43 } };

	private int arrPointFlowers[][] = { { 58, 637, 245, 379, 539, 732, 927 },
			{ 205, 194, 313, 214, 296, 245, 363 } };

	private float arrPositionKintaro[][] = {
			{ 104, 60, 6, 44, 707, 777, 840, 810 },
			{ 36, 166, 280, 436, 85, 175, 270, 435 } };

	private boolean isTouchSun, arrIsTouchKumo[] = { true, true, true, true },
			isTouchBird = true;

	private boolean leftIsStart, rightIsStart, stageIsStart, isWinner;

	private int indexStageLeft, indexStageRight;
	private int countLeftAfterMeet, countRightAfterMeet;
	private int winner;

	private RotationModifier sunOutlineModifier;

	@SuppressWarnings("unused")
	private SequenceEntityModifier arrKumoModifier[] = new SequenceEntityModifier[4],
			winnerScale, winnerRotationLeft, winnerRotationRight;
	@SuppressWarnings("unused")
	private LoopEntityModifier birdModifier;
	private ScaleModifier loserScale;

	private Sound mp3Bye, mp3Hakey, mp3Poyo, mp3Tanuki, mp3Boyon, mp3Pyoi,
			mp3Mokin, mp3Byon, mp3Kira2, mp3Kra3, mp3Kansei, mp3Pomi;

	private TexturePack mBackgroundTexturePack;
	private TexturePack mItemTexturePack;

	private TexturePackTextureRegionLibrary mBackgroundTexturePackTextureRegionLibrary;
	private TexturePackTextureRegionLibrary mItemexturePackTextureRegionLibrary;
	private boolean isTouchKintaro = false;
	private boolean isReadly = false;
	private boolean updateHandler = false;
	private int countTouchScreen = 0;
	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		// Set Backgroud For Scene
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground, this
						.getVertexBufferObjectManager())));

		// Add Fuji
		this.anmSprFuji = new AnimatedSprite(539, 47, tiledTtrFuji,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFuji);

		// Add Background 2
		this.sprBackground2 = new Sprite(0, 0, ttrRBackground2,
				this.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		            float pTouchAreaLocalX, float pTouchAreaLocalY) {
		        // TODO Auto-generated method stub
		        if(pSceneTouchEvent.isActionDown()){
		            countTouchScreen = countTouchScreen+1;
		            Log.d("Than kinh","Day nay"+countTouchScreen);
		            return false;
		        }
		        if(pSceneTouchEvent.isActionUp()){
		            countTouchScreen = 0;
		        }
		        return super
		                .onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		    }
		};
		this.mScene.attachChild(sprBackground2);
		this.mScene.registerTouchArea(sprBackground2);
		// Add Sun
		this.anmSprSunInline = new AnimatedSprite(298, 14, tiledTtrSunInline,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprSunInline);
		this.anmSprSunOutline = new AnimatedSprite(298, 14, tiledTtrSunOutline,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprSunOutline);

		// Add Kumo
		for (int i = 0; i < 4; i++) {
			this.arrSprKumo[i] = new Sprite(arrPointKumo[0][i],
					arrPointKumo[1][i], this.arrTtrRKumo[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSprKumo[i]);
		}

		// Add Stage
		this.anmSprStage = new AnimatedSprite(140, 335, this.tiledTtrStage,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprStage);

		// Add Flower 14
		this.anmSprFlower14 = new AnimatedSprite(143, 432,
				this.tiledTtrFlower14, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprFlower14);

		// Add Kenmushi
		this.anmSprKenmushi = new AnimatedSprite(713, 443,
				this.tiledTtrKenmushi, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprKenmushi);

		// Add Flowers
		for (int i = 0; i < 7; i++) {
			arrAnmSprFlowers[i] = new AnimatedSprite(arrPointFlowers[0][i],
					arrPointFlowers[1][i], arrTiledTtrFlowers[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(arrAnmSprFlowers[i]);
		}

		// Add Bird
		this.anmSprBird = new AnimatedSprite(369, 92, this.tiledTtrBird,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprBird);

		// Add Kiranto
		for (int i = 0; i < 8; i++) {
			switch (i) {
			case 0:
				arrAnmSprKintaro[i] = new myAnimatedSprite(104, 36,
						tiledTtrBear, 0);
				break;
			case 1:
				arrAnmSprKintaro[i] = new myAnimatedSprite(60, 166,
						tiledTtrBadger, 1);
				break;
			case 2:
				arrAnmSprKintaro[i] = new myAnimatedSprite(6, 280,
						tiledTtrMonkey, 2);
				break;

			case 3:
				arrAnmSprKintaro[i] = new myAnimatedSprite(44, 436,
						tiledTtrHedgehod, 3);
				break;
			case 4:
				arrAnmSprKintaro[i] = new myAnimatedSprite(707, 85,
						tiledTtrKintaro, 4);
				break;

			case 5:
				arrAnmSprKintaro[i] = new myAnimatedSprite(777, 175,
						tiledTtrBoar, 5);
				break;
			case 6:
				arrAnmSprKintaro[i] = new myAnimatedSprite(840, 270,
						tiledTtrRabbit, 6);
				break;
			case 7:
				arrAnmSprKintaro[i] = new myAnimatedSprite(810, 435,
						tiledTtrSquirrel, 7);
				break;
			default:
				break;
			}
			this.mScene.attachChild(arrAnmSprKintaro[i]);
			this.mScene.registerTouchArea(arrAnmSprKintaro[i]);
		}

		// Add Kamihubuki
		this.anmSprKamihubuki = new AnimatedSprite(0, 7,
				this.tiledTtrKamihubuki, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprKamihubuki);
		this.anmSprKamihubuki.setVisible(false);
		this.anmSprKamihubuki.setZIndex(1132);

		// Add Taiko
		this.anmSprTaikoLeft = new myAnimatedSprite(-40, 525,
				this.tiledTtrTaikoLeft, 8);
		this.mScene.attachChild(this.anmSprTaikoLeft);
		this.anmSprTaikoLeft.setVisible(false);
		this.anmSprTaikoLeft.setZIndex(1112);
		
		this.anmSprTaikoRight = new myAnimatedSprite(838, 525,
				this.tiledTtrTaikoRight, 9);
		this.mScene.attachChild(this.anmSprTaikoRight);
		this.anmSprTaikoRight.setVisible(false);
		this.anmSprTaikoRight.setZIndex(1113);

		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3KintaroResource.SOUND_GIMMIC_KIRATO,
				Vol3KintaroResource.IMAGE_GIMMIC_KIRANTO,
				mItemexturePackTextureRegionLibrary);

		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		this.mScene.registerUpdateHandler(new IUpdateHandler() {
                    @Override
                    public void reset() {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onUpdate(float arg0) {
                        //Kintaro Left
                        if(checkDoubleTouch(arrAnmSprKintaro[0], arrAnmSprKintaro[1])){
                            arrAnmSprKintaro[0].setPosition(104, 36);
                            arrAnmSprKintaro[1].setPosition(60, 166);
                            setZindexLeft(arrAnmSprKintaro[0], arrAnmSprKintaro[1]);
                        }
                        if(checkDoubleTouch(arrAnmSprKintaro[0], arrAnmSprKintaro[2])){
                            arrAnmSprKintaro[0].setPosition(104, 36);
                            arrAnmSprKintaro[2].setPosition(6, 280);
                            setZindexLeft(arrAnmSprKintaro[0], arrAnmSprKintaro[2]);
                        }
                        if(checkDoubleTouch(arrAnmSprKintaro[0], arrAnmSprKintaro[3])){
                            arrAnmSprKintaro[0].setPosition(104, 36);
                            arrAnmSprKintaro[3].setPosition(44, 436);
                            setZindexLeft(arrAnmSprKintaro[0], arrAnmSprKintaro[3]);
                        }
                        if(checkDoubleTouch(arrAnmSprKintaro[1], arrAnmSprKintaro[2])){
                            arrAnmSprKintaro[1].setPosition(60, 166);
                            arrAnmSprKintaro[2].setPosition(6, 280);
                            setZindexLeft(arrAnmSprKintaro[1], arrAnmSprKintaro[2]);
                        }
                        if(checkDoubleTouch(arrAnmSprKintaro[1], arrAnmSprKintaro[3])){
                            arrAnmSprKintaro[1].setPosition(60, 166);
                            arrAnmSprKintaro[3].setPosition(44, 436);
                            setZindexLeft(arrAnmSprKintaro[1], arrAnmSprKintaro[3]);
                        }
                        if(checkDoubleTouch(arrAnmSprKintaro[2], arrAnmSprKintaro[3])){
                            arrAnmSprKintaro[2].setPosition(6, 280);
                            arrAnmSprKintaro[3].setPosition(44, 436);
                            setZindexLeft(arrAnmSprKintaro[2], arrAnmSprKintaro[3]);
                        }
                        if(arrAnmSprKintaro[0].getX()<0 || arrAnmSprKintaro[0].getY()<0 || arrAnmSprKintaro[0].getY()>380){
                            disableStage(arrAnmSprKintaro[0], 0);
                        }

                        if(arrAnmSprKintaro[1].getX()<0 || arrAnmSprKintaro[1].getY()<0 || arrAnmSprKintaro[1].getY()>450){
                            disableStage(arrAnmSprKintaro[1],1);
                        }
                        if(arrAnmSprKintaro[2].getX()<-50 || arrAnmSprKintaro[2].getY()<0 || arrAnmSprKintaro[2].getY()>470){
                            disableStage(arrAnmSprKintaro[2],2);
                        }
                        if(arrAnmSprKintaro[3].getX()<0 || arrAnmSprKintaro[3].getY()<0 || arrAnmSprKintaro[3].getY()>550){
                            disableStage(arrAnmSprKintaro[3],3);
                        }
                        //Kintaro Right
                        if(checkDoubleTouch(arrAnmSprKintaro[4], arrAnmSprKintaro[5])){
                                arrAnmSprKintaro[4].setPosition(707, 85);
                                arrAnmSprKintaro[5].setPosition(777, 175);
                                setZindexRight(arrAnmSprKintaro[4], arrAnmSprKintaro[5]);
                            }
                        if(checkDoubleTouch(arrAnmSprKintaro[4], arrAnmSprKintaro[6])){
                                arrAnmSprKintaro[4].setPosition(707, 85);
                                arrAnmSprKintaro[6].setPosition(840, 270);
                                setZindexRight(arrAnmSprKintaro[4], arrAnmSprKintaro[6]);
                            }
                        if(checkDoubleTouch(arrAnmSprKintaro[4], arrAnmSprKintaro[7])){
                                arrAnmSprKintaro[4].setPosition(707, 85);
                                arrAnmSprKintaro[7].setPosition(810, 435);
                                setZindexRight(arrAnmSprKintaro[4], arrAnmSprKintaro[7]);
                            }
                        if(checkDoubleTouch(arrAnmSprKintaro[5], arrAnmSprKintaro[6])){
                                arrAnmSprKintaro[5].setPosition(777, 175);
                                arrAnmSprKintaro[6].setPosition(840, 270);
                                setZindexRight(arrAnmSprKintaro[5], arrAnmSprKintaro[6]);
                            }
                        if(checkDoubleTouch(arrAnmSprKintaro[5], arrAnmSprKintaro[7])){
                                arrAnmSprKintaro[5].setPosition(777, 175);
                                arrAnmSprKintaro[7].setPosition(810, 435);
                                setZindexRight(arrAnmSprKintaro[5], arrAnmSprKintaro[7]);
                            }
                        if(checkDoubleTouch(arrAnmSprKintaro[6], arrAnmSprKintaro[7])){
                                arrAnmSprKintaro[6].setPosition(840, 270);
                                arrAnmSprKintaro[7].setPosition(810, 435);
                                setZindexRight(arrAnmSprKintaro[6], arrAnmSprKintaro[7]);
                            }
                        if(arrAnmSprKintaro[4].getX()>825 || arrAnmSprKintaro[4].getY()<0 || arrAnmSprKintaro[4].getY()>400){
                            disableStage(arrAnmSprKintaro[4],4);
                        }
                        if(arrAnmSprKintaro[5].getX()>825 || arrAnmSprKintaro[5].getY()<0 || arrAnmSprKintaro[5].getY()>440){
                            disableStage(arrAnmSprKintaro[5],5);
                        }
                        if(arrAnmSprKintaro[6].getX()>900 || arrAnmSprKintaro[6].getY()<0 || arrAnmSprKintaro[6].getY()>450){
                            disableStage(arrAnmSprKintaro[6],6);
                        }
                        if(arrAnmSprKintaro[7].getX()>900 || arrAnmSprKintaro[7].getY()<0 || arrAnmSprKintaro[7].getY()>520){
                            disableStage(arrAnmSprKintaro[7],7);
                        }
                        if(!leftIsStart && rightIsStart && !updateHandler && !isWinner){
                            if(countTouchScreen >1){
                                isReadly = true;
                                switch(indexStageRight){
                                case 4:
                                    sortOrder(4,600,231);
                                    break;
                                case 5:
                                    sortOrder(5,600,250);
                                    break;
                                case 6:
                                    sortOrder(6,626,240);
                                    break;
                                case 7:
                                    sortOrder(7,626,324);
                                    break;
                                }
                                for(int i = 0;i<4;i++){
                                    if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                    }
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            mEngine.registerUpdateHandler(new TimerHandler(0.5f,
                                    new ITimerCallback() {
                                            @Override
                                            public void onTimePassed(TimerHandler arg0) {
                                                isReadly = false;
                                            }
                                    }));
                            }
                        }
                        if(leftIsStart && !rightIsStart && !updateHandler && !isWinner){
                            if(countTouchScreen >1){
                                isReadly = true;
                                switch(indexStageLeft){
                                case 0:
                                    sortOrder(0,215,151);
                                    break;
                                case 1:
                                    sortOrder(1,215,248);
                                    break;
                                case 2:
                                    sortOrder(2,215,262);
                                    break;
                                case 3:
                                    sortOrder(3,247,324);
                                    break;
                                }
                                for(int i = 4;i<8;i++){
                                    if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                    }
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            mEngine.registerUpdateHandler(new TimerHandler(0.5f,
                                    new ITimerCallback() {
                                            @Override
                                            public void onTimePassed(TimerHandler arg0) {
                                                isReadly = false;
                                            }
                                    }));
                            }
                        }
                        if(countTouchScreen >2 && !updateHandler && !isWinner){
                            isReadly = true;
                            if(!leftIsStart && !rightIsStart){
                                for(int i = 0;i<8;i++){
                                        if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                                            arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                        }
                                        arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                                arrPositionKintaro[1][i]);
                                        arrAnmSprKintaro[i].setZIndex(888 + i);
                                        arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            if(leftIsStart && !rightIsStart){
                                switch(indexStageLeft){
                                case 0:
                                    sortOrder(0,215,151);
                                    break;
                                case 1:
                                    sortOrder(1,215,248);
                                    break;
                                case 2:
                                    sortOrder(2,215,262);
                                    break;
                                case 3:
                                    sortOrder(3,247,324);
                                    break;
                                }
                                switch(indexStageRight){
                                case 4:
                                    sortOrder(4,600,231);
                                    break;
                                case 5:
                                    sortOrder(5,600,250);
                                    break;
                                case 6:
                                    sortOrder(6,626,240);
                                    break;
                                case 7:
                                    sortOrder(7,626,324);
                                    break;
                                }
                                for(int i = 4;i<8;i++){
                                    if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                    }
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            if(!leftIsStart && rightIsStart){
                                switch(indexStageLeft){
                                case 0:
                                    sortOrder(0,215,151);
                                    break;
                                case 1:
                                    sortOrder(1,215,248);
                                    break;
                                case 2:
                                    sortOrder(2,215,262);
                                    break;
                                case 3:
                                    sortOrder(3,247,324);
                                    break;
                                }
                                switch(indexStageRight){
                                case 4:
                                    sortOrder(4,600,231);
                                    break;
                                case 5:
                                    sortOrder(5,600,250);
                                    break;
                                case 6:
                                    sortOrder(6,626,240);
                                    break;
                                case 7:
                                    sortOrder(7,626,324);
                                    break;
                                }
                                for(int i = 0;i<4;i++){
                                    if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                    }
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            mEngine.registerUpdateHandler(new TimerHandler(0.5f,
                                    new ITimerCallback() {
                                            @Override
                                            public void onTimePassed(TimerHandler arg0) {
                                                isReadly = false;
                                            }
                                    }));
                        }
                        //Update Hander
                        if((int)arrAnmSprKintaro[0].getX()==215 && (int)arrAnmSprKintaro[0].getY() == 151 && !updateHandler && !isWinner){
                            indexStageLeft = 0;
                            for(int i = 0;i<4;i++){
                                if(i!=0){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            leftIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[1].getX()==215 && (int)arrAnmSprKintaro[1].getY() == 248 && !updateHandler && !isWinner){
                            indexStageLeft = 1;
                            for(int i = 0;i<4;i++){
                                if(i!=1){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            leftIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[2].getX()==215 && (int)arrAnmSprKintaro[2].getY() == 262 && !updateHandler && !isWinner){
                            indexStageLeft = 2;
                            for(int i = 0;i<4;i++){
                                if(i!=2){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            leftIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[3].getX()==215 && (int)arrAnmSprKintaro[3].getY() == 324 && !updateHandler && !isWinner){
                            indexStageLeft = 3;
                            for(int i = 0;i<4;i++){
                                if(i!=3){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            leftIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[4].getX()==600 && (int)arrAnmSprKintaro[4].getY() == 231 && !updateHandler && !isWinner){
                            indexStageRight = 4;
                            for(int i = 4;i<8;i++){
                                if(i!=4){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            rightIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[5].getX()==600 && (int)arrAnmSprKintaro[5].getY() == 250 && !updateHandler && !isWinner){
                            indexStageRight = 5;
                            for(int i = 4;i<8;i++){
                                if(i!=5){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            rightIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[6].getX()==626 && (int)arrAnmSprKintaro[6].getY() == 240 && !updateHandler && !isWinner){
                            indexStageRight = 6;
                            for(int i = 6;i<8;i++){
                                if(i!=6){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            rightIsStart = true;
                        }
                        if((int)arrAnmSprKintaro[7].getX()==626 && (int)arrAnmSprKintaro[7].getY() == 324 && !updateHandler && !isWinner){
                            indexStageRight = 7;
                            for(int i = 4;i<8;i++){
                                if(i!=7){
                                    arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                            arrPositionKintaro[1][i]);
                                    arrAnmSprKintaro[i].setZIndex(888 + i);
                                    arrAnmSprKintaro[i].getParent().sortChildren();
                                }
                            }
                            rightIsStart = true;
                        }
                        if (leftIsStart && rightIsStart && !updateHandler && !isWinner) {
                            switch(indexStageLeft){
                            case 0:
                                sortOrder(0,215,151);
                                break;
                            case 1:
                                sortOrder(1,215,248);
                                break;
                            case 2:
                                sortOrder(2,215,262);
                                break;
                            case 3:
                                sortOrder(3,247,324);
                                break;
                            }
                            switch(indexStageRight){
                            case 4:
                                sortOrder(4,600,231);
                                break;
                            case 5:
                                sortOrder(5,600,250);
                                break;
                            case 6:
                                sortOrder(6,626,240);
                                break;
                            case 7:
                                sortOrder(7,626,324);
                                break;
                            }
                            updateHandler = true;
                            mp3Hakey.play();
                            isReadly = true;
                            mEngine.registerUpdateHandler(new TimerHandler(3f,
                                    new ITimerCallback() {
                                            @Override
                                            public void onTimePassed(TimerHandler arg0) {
                                                isReadly = false;
                                            }
                                    }));
                            Vol3Kintaro.this.mScene.registerTouchArea(anmSprTaikoLeft);
                            Vol3Kintaro.this.mScene.registerTouchArea(anmSprTaikoRight);
                            stageIsStart = true;
                            anmSprTaikoLeft.setPosition(-40, 525);
                            anmSprTaikoLeft.setVisible(true);
                            anmSprTaikoRight.setVisible(true);
                            anmSprTaikoRight.setPosition(838, 525);
                             try {
                                Vol3Kintaro.this.mEngine.setTouchController(new MultiTouchController());
                            } catch (final Exception e) {
                                Log.d("MultiTouch detected --> Drag multiple Sprites with multiple fingers!", "Loi 4"+e);
                            }
                        }
                    }
                });
	}
	private void sortOrder(int orders, int x, int y){
	    arrAnmSprKintaro[orders].setPosition(x,y);
	    if(orders<=3){
	        for(int i = 0;i<4;i++){
	            if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
	                arrAnmSprKintaro[i].setCurrentTileIndex(0);
	            }
                    if(i!=orders){
                        arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                arrPositionKintaro[1][i]);
                        arrAnmSprKintaro[i].setZIndex(888 + i);
                        arrAnmSprKintaro[i].getParent().sortChildren();
                    }
                }
	    }else{
	        for(int i = 4;i<8;i++){
	            if(arrAnmSprKintaro[i].getCurrentTileIndex()!=0){
                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                    }
                    if(i!=orders){
                        arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                arrPositionKintaro[1][i]);
                        arrAnmSprKintaro[i].setZIndex(888 + i);
                        arrAnmSprKintaro[i].getParent().sortChildren();
                    }
                }
	    }
	}
	private void disableStage(final AnimatedSprite anis, final int index){
    	        isTouchKintaro = true;
    	        arrAnmSprKintaro[index].registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                }
                @Override
                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    isTouchKintaro = false;
                    stageIsStart = false;
                    leftIsStart = false;
                    rightIsStart = false;
                    isWinner = false;
                    indexStageLeft = -1;
                    indexStageRight = -1;
                    winner = -1;
                }
            }));
        	 switch(index){
                 case 0: case 1: case 2: case 3:
                     for (int i = 0; i < 4; i++) {
                         arrAnmSprKintaro[i].setCurrentTileIndex(0);
                         arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                         arrPositionKintaro[1][i]);
                         arrAnmSprKintaro[i].setZIndex(888 + i);
                         anis.getParent().sortChildren();
                 }
                 break;
                 case 4: case 5: case 6: case 7:
                     for (int i = 4; i < 8; i++) {
                         arrAnmSprKintaro[i].setCurrentTileIndex(0);
                         arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
                                         arrPositionKintaro[1][i]);
                         arrAnmSprKintaro[i].setZIndex(888 + i);
                         anis.getParent().sortChildren();
                 }
                     break;
             }
	}
	private boolean checkDoubleTouch(AnimatedSprite anis1, AnimatedSprite anis2){
	    if((anis1.getX()>=150 && anis1.getX()<=700 && anis1.getY()>=10 && anis1.getY()<=720)
                    && (anis2.getX()>=150 && anis2.getX()<=700 && anis2.getY()>=10 && anis2.getY()<=720)    
                ){
	        anis1.setCurrentTileIndex(0);
	        anis2.setCurrentTileIndex(0);
	        return true;
	    }else{
	        return false;
	    }
	}
	private void setZindexLeft(AnimatedSprite anis1, AnimatedSprite anis2){
	    for (int i = 0; i < 4; i++) {
                arrAnmSprKintaro[i].setZIndex(888 + i);
                anis1.getParent().sortChildren();
                anis2.getParent().sortChildren();
            }
	}
	private void setZindexRight(AnimatedSprite anis1, AnimatedSprite anis2){
	    for (int i = 4; i < 8; i++) {
                arrAnmSprKintaro[i].setZIndex(888 + i);
                anis1.getParent().sortChildren();
                anis2.getParent().sortChildren();
            }
        }
	
	@Override
	protected void loadKaraokeResources() {
		mBackgroundTexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "kintaro/gfx/")
				.load("1.xml");
		mBackgroundTexturePack.loadTexture();
		mBackgroundTexturePackTextureRegionLibrary = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary();

		mItemTexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "kintaro/gfx/")
				.load("2.xml");
		mItemTexturePack.loadTexture();
		mItemexturePackTextureRegionLibrary = mItemTexturePack
				.getTexturePackTextureRegionLibrary();

		// ITextureRegion abc =
		// mBackgroundTexturePackTextureRegionLibrary.get(pID);
		// TiledTextureRegion sas =
		// getTiledTextureFromPacker(mBackgroundTexturePack, 1,23,3,4,5,5);

		this.ttrRBackground = mBackgroundTexturePackTextureRegionLibrary
				.get(Vol3KintaroResource.A6_28_1_IPHONE_HAIKEI_ID);

		this.ttrRBackground2 = mBackgroundTexturePackTextureRegionLibrary
				.get(Vol3KintaroResource.A6_28_2_IPHONE_HAIKEI_ID);

		for (int i = 0; i < 4; i++) {
			Log.d(TAG, "/" + i);
			this.arrTtrRKumo[i] = mItemexturePackTextureRegionLibrary
					.get(Vol3KintaroResource.ARR_RESOURCE_KUMO[i]);
		}

		this.tiledTtrSunInline = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_24_1_1_IPHONE_SUN_ID,
				Vol3KintaroResource.A6_24_2_IPHONE_SUN_ID);

		this.tiledTtrSunOutline = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_24_1_2_IPHONE_SUN_ID,
				Vol3KintaroResource.A6_24_3_IPHONE_SUN_ID);

		// Load Fuji

		this.tiledTtrFuji = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_25_1_IPHONE_FUJI_ID,
				Vol3KintaroResource.A6_25_2_IPHONE_FUJI_ID,
				Vol3KintaroResource.A6_25_3_IPHONE_FUJI_ID);

		// Load Bird

		this.tiledTtrBird = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_13_1_IPHONE_BIRD_ID,
				Vol3KintaroResource.A6_13_2_IPHONE_BIRD_ID,
				Vol3KintaroResource.A6_13_3_IPHONE_BIRD_ID);

		// Load All Flowers

		for (int i = 0; i < 7; i++) {
			arrTiledTtrFlowers[i] = getTiledTextureFromPacker(mItemTexturePack,
					Vol3KintaroResource.ARR_RESOURCE_FLOWERS[2 * i],
					Vol3KintaroResource.ARR_RESOURCE_FLOWERS[2 * i + 1]);
		}

		// Load Kintaro
		this.tiledTtrKintaro = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_KINTARO);

		// Load Bear
		this.tiledTtrBear = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_BEAR);

		// Load Hedgehog
		this.tiledTtrHedgehod = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_HEDGEHOG);

		// Load Squirrel

		this.tiledTtrSquirrel = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_SQUIRREL);

		// Load Monkey
		this.tiledTtrMonkey = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_MONKEY);

		// Load BadgerkenmushiModifier

		this.tiledTtrBadger = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_BADGER);

		// Load Rabbit

		this.tiledTtrRabbit = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_RABBIT);

		// Load Boar

		this.tiledTtrBoar = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_BOAR);

		// Load Stage
		this.tiledTtrStage = getTiledTextureFromPacker(mBackgroundTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_STAGE);

		// Load Flower14
		this.tiledTtrFlower14 = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_FLOWER14);

		// Load Kenmushi
		this.tiledTtrKenmushi = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.ARR_RESOURCE_KENMUSHI);

		// Load Kamihubuki

		this.tiledTtrKamihubuki = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_26_1_IPHONE_KAMIHUBUKI_ID,
				Vol3KintaroResource.A6_26_2_IPHONE_KAMIHUBUKI_ID);

		// Load Taiko
		this.tiledTtrTaikoLeft = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_27_IPHONE_TAIKO_ID);
		this.tiledTtrTaikoRight = getTiledTextureFromPacker(mItemTexturePack,
				Vol3KintaroResource.A6_27_IPHONE_TAIKO_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3Bye = loadSoundResourceFromSD(Vol3KintaroResource.A6_03_BYE);
		mp3Hakey = loadSoundResourceFromSD(Vol3KintaroResource.A6_04_11_HAKEYOINOKOTA);
		mp3Poyo = loadSoundResourceFromSD(Vol3KintaroResource.A6_04_11_POYO);
		mp3Tanuki = loadSoundResourceFromSD(Vol3KintaroResource.A6_04_11_TANUKIDRUM);
		mp3Boyon = loadSoundResourceFromSD(Vol3KintaroResource.A6_14_BOYON);
		mp3Pyoi = loadSoundResourceFromSD(Vol3KintaroResource.A6_15_PYOI);
		mp3Mokin = loadSoundResourceFromSD(Vol3KintaroResource.A6_16_MOKIN);
		mp3Byon = loadSoundResourceFromSD(Vol3KintaroResource.A6_17_18_BYON);
		mp3Kira2 = loadSoundResourceFromSD(Vol3KintaroResource.A6_19_23_KIRA2);
		mp3Kra3 = loadSoundResourceFromSD(Vol3KintaroResource.A6_24_KIRA3);
		mp3Kansei = loadSoundResourceFromSD(Vol3KintaroResource.A6_26_KANSEI);
		mp3Pomi = loadSoundResourceFromSD(Vol3KintaroResource.A6_29_POMI);
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("kintaro/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kintaro/gfx/");
		super.onCreateResources();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoLeft);
                Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoRight);
		this.isTouchBird = true;
		for (int i = 0; i < 4; i++) {
			if (arrSprKumo[i] != null) {
				arrSprKumo[i].clearEntityModifiers();
				arrSprKumo[i].setPosition(arrPointKumo[0][i],
						arrPointKumo[1][i]);
				arrIsTouchKumo[i] = true;
			}
		}
		if(anmSprFuji != null){
			anmSprFuji.setCurrentTileIndex(0);
		}		
		for (int i = 0; i < arrAnmSprFlowers.length; i++) {
			if (arrAnmSprFlowers[i] != null) {
				arrAnmSprFlowers[i].setCurrentTileIndex(0);
			}
		}
		if (anmSprFlower14 != null) {
			anmSprFlower14.setCurrentTileIndex(0);
		}
		if (anmSprKenmushi != null) {
			anmSprKenmushi.setCurrentTileIndex(0);
		}
		if (anmSprStage != null) {
			anmSprStage.setCurrentTileIndex(0);
		}
		if (anmSprBird != null) {
			anmSprBird.clearEntityModifiers();
			anmSprBird.stopAnimation();
			anmSprBird.setCurrentTileIndex(0);
			anmSprBird.setPosition(369, 92);
		}
		for (int i = 0; i < 8; i++) {
			if (arrAnmSprKintaro[i] != null) {
				arrAnmSprKintaro[i].stopAnimation();
				arrAnmSprKintaro[i].clearEntityModifiers();
				arrAnmSprKintaro[i].setCurrentTileIndex(0);
				arrAnmSprKintaro[i].setPosition(arrPositionKintaro[0][i],
						arrPositionKintaro[1][i]);
				arrAnmSprKintaro[i].setZIndex(888 + i);
				arrAnmSprKintaro[i].setScale(1.0f);
				arrAnmSprKintaro[i].setRotation(0);
			}
		}
		if (anmSprKamihubuki != null) {
			anmSprKamihubuki.stopAnimation();
			anmSprKamihubuki.setVisible(false);
		}
		if (anmSprTaikoLeft != null) {
			anmSprTaikoLeft.setPosition(-40, 525);
			anmSprTaikoLeft.setVisible(false);
		}
		if (anmSprTaikoRight != null) {
			anmSprTaikoRight.setPosition(838, 525);
			anmSprTaikoRight.setVisible(false);
		}
		if (anmSprSunInline != null) {
			anmSprSunInline.setCurrentTileIndex(0);
			anmSprSunInline.clearEntityModifiers();
			anmSprSunInline.setPosition(298, 14);
		}
		if (anmSprSunOutline != null) {
			anmSprSunOutline.setCurrentTileIndex(0);
			anmSprSunOutline.clearEntityModifiers();
			anmSprSunOutline.setPosition(298, 14);
		}
		if (anmSprStage != null) {
			anmSprStage.stopAnimation();
		}
		if(this.mEngine!=null){
		    this.mEngine.clearUpdateHandlers();
		}

		if (this.mScene != null) {
			this.mScene.sortChildren();
		}
		// Reset status
		this.indexStageLeft = -1;
		this.indexStageRight = -1;
		isTouchSun = true;
		this.leftIsStart = false;
		this.rightIsStart = false;
		this.stageIsStart = false;
		isWinner = false;
		isReadly = false;
		this.countLeftAfterMeet = 0;
		this.countRightAfterMeet = 0;
		updateHandler = false;
		countTouchScreen = 0;
	}
	@Override
	public synchronized void onResumeGame() {
                        // TODO Auto-generated method stub
                        this.isTouchBird = true;
                        Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoLeft);
                        Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoRight);
                        for (int i = 0; i < 4; i++) {
                                arrIsTouchKumo[i] = true;
                        }
                        stageIsStart = false;
                        isTouchSun = true;
                        leftIsStart = false;
                        rightIsStart = false;
                        isWinner = false;
                        countLeftAfterMeet = 0;
                        countRightAfterMeet = 0;
                        indexStageLeft = -1;
                        indexStageRight = -1;
                        winner = -1;
                        isReadly = false;
                        updateHandler = false;
                        countTouchScreen = 0;
                        if (defaultTouchController != null) {
                        try {
                            this.mEngine.setTouchController(new MultiTouchController());
                        } catch (final Exception e) {
                            Log.d("MultiTouch detected --> Drag multiple Sprites with multiple fingers!", "Loi 4"+e);
                        }
                        birdModifier();
                }
	        super.onResumeGame();
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (checkContains(anmSprFuji, 0, 0, (int) anmSprFuji.getWidth(),
					(int) anmSprFuji.getHeight(), pX, pY)) {
				fujiAnimate();
			} else if (checkContains(anmSprSunOutline, 0, 0,
					(int) anmSprSunOutline.getWidth(),
					(int) anmSprSunOutline.getHeight(), pX, pY)) {
				sunAnimate();
			} else if (checkContains(arrSprKumo[0], 0, 0,
					(int) arrSprKumo[0].getWidth(),
					(int) arrSprKumo[0].getHeight(), pX, pY)) {
				kumoModifier(0);
			} else if (checkContains(arrSprKumo[1], 0, 0,
					(int) arrSprKumo[1].getWidth(),
					(int) arrSprKumo[1].getHeight(), pX, pY)) {
				kumoModifier(1);
			} else if (checkContains(arrSprKumo[2], 0, 0,
					(int) arrSprKumo[2].getWidth(),
					(int) arrSprKumo[2].getHeight(), pX, pY)) {
				kumoModifier(2);
			} else if (checkContains(arrSprKumo[3], 0, 0,
					(int) arrSprKumo[3].getWidth(),
					(int) arrSprKumo[3].getHeight(), pX, pY)) {
				kumoModifier(3);
			} else if (checkContains(arrAnmSprFlowers[0], 0, 0,
					(int) arrAnmSprFlowers[0].getWidth(),
					(int) arrAnmSprFlowers[0].getHeight(), pX, pY)) {
				changeFrameFlowers(0);
			} else if (checkContains(arrAnmSprFlowers[1], 0, 0,
					(int) arrAnmSprFlowers[1].getWidth(),
					(int) arrAnmSprFlowers[1].getHeight(), pX, pY)) {
				changeFrameFlowers(1);
			} else if (checkContains(arrAnmSprFlowers[2], 0, 0,
					(int) arrAnmSprFlowers[2].getWidth(),
					(int) arrAnmSprFlowers[2].getHeight(), pX, pY)) {
				changeFrameFlowers(2);
			} else if (checkContains(arrAnmSprFlowers[3], 0, 0,
					(int) arrAnmSprFlowers[3].getWidth(),
					(int) arrAnmSprFlowers[3].getHeight(), pX, pY)) {
				changeFrameFlowers(3);
			} else if (checkContains(arrAnmSprFlowers[4], 0, 0,
					(int) arrAnmSprFlowers[4].getWidth(),
					(int) arrAnmSprFlowers[4].getHeight(), pX, pY)) {
				changeFrameFlowers(4);
			} else if (checkContains(arrAnmSprFlowers[5], 0, 0,
					(int) arrAnmSprFlowers[5].getWidth(),
					(int) arrAnmSprFlowers[5].getHeight(), pX, pY)) {
				changeFrameFlowers(5);
			} else if (checkContains(arrAnmSprFlowers[6], 0, 0,
					(int) arrAnmSprFlowers[6].getWidth(),
					(int) arrAnmSprFlowers[6].getHeight(), pX, pY)) {
				changeFrameFlowers(6);
			} else if (checkContains(anmSprFlower14, 0, 0,
					(int) anmSprFlower14.getWidth(),
					(int) anmSprFlower14.getHeight(), pX, pY)) {
				flower14Modifier();
			} else if (checkContains(anmSprKenmushi, 0, 0,
					(int) anmSprKenmushi.getWidth(),
					(int) anmSprKenmushi.getHeight(), pX, pY)) {
				kenmushiModifier();
			} else if (checkContains(anmSprStage, 105, 50, 520, 198, pX, pY)) {
				stageAnimate();
			} else if (checkContains(this.anmSprBird,
					(int) this.anmSprBird.getWidth() / 4, 0,
					(int) this.anmSprBird.getWidth() * 3 / 4,
					(int) this.anmSprBird.getHeight(), pX, pY)) {
				birdTouch();
			}
			return true;
		}
		return true;
	}

	@Override
	public void combineGimmic3WithAction() {

		fujiAnimate();
	}

	private void fujiAnimate() {
		Log.d(TAG, "fujiAnimate");
		if (!anmSprFuji.isAnimationRunning()) {
			mp3Bye.play();
			long durations[] = { 200, 200, 200, 200, 100 };
			int frames[] = { 1, 2, 1, 2, 0 };
			anmSprFuji.animate(durations, frames, 0, this);
		}
		return;
	}

	private void sunAnimate() {
		if (isTouchSun) {
			mp3Kra3.play();
			isTouchSun = false;
			anmSprSunInline.setCurrentTileIndex(1);
			anmSprSunOutline.setCurrentTileIndex(1);
			anmSprSunOutline
					.registerEntityModifier(sunOutlineModifier = new RotationModifier(
							2.0f, 0.0f, 359.0f, EaseQuadOut.getInstance()));
			if (sunOutlineModifier != null) {
				sunOutlineModifier.addModifierListener(this);
			}
		}
		return;
	}

	private void kumoModifier(int indexKumo) {
		if (arrIsTouchKumo[indexKumo]) {
			mp3Pomi.play();
			arrIsTouchKumo[indexKumo] = false;
			float duration1 = arrSprKumo[indexKumo].getX() * 5.0f
					/ this.getEngine().getCamera().getWidth();
			float duration2 = ((this.getEngine().getCamera().getWidth() - arrSprKumo[indexKumo]
					.getX()) * 5.0f) / this.getEngine().getCamera().getWidth();

			arrSprKumo[indexKumo]
					.registerEntityModifier((IEntityModifier) (arrKumoModifier[indexKumo] = new SequenceEntityModifier(
							new MoveXModifier(duration1, arrSprKumo[indexKumo]
									.getX(), -arrSprKumo[indexKumo].getWidth()),
							new MoveXModifier(duration2, this.getEngine()
									.getCamera().getWidth(),
									arrSprKumo[indexKumo].getX(), EaseQuadOut
											.getInstance()))));
			if (arrKumoModifier[indexKumo] != null) {
				arrKumoModifier[indexKumo].addModifierListener(this);
			}
		}
	}

	private void birdModifier() {
		if (this.anmSprBird != null) {
			this.anmSprBird.setCurrentTileIndex(0);
			this.anmSprBird
					.registerEntityModifier(birdModifier = new LoopEntityModifier(
							new SequenceEntityModifier(new MoveYModifier(1.0f,
									anmSprBird.getY(), anmSprBird.getY() - 20),
									new MoveYModifier(1.0f,
											anmSprBird.getY() - 20, anmSprBird
													.getY()))));
		}
	}

	private void changeFrameFlowers(int index) {
		if (index == 0 || index == 1) {
			mp3Byon.play();
		} else {
			mp3Kira2.play();
		}
		arrAnmSprFlowers[index].setCurrentTileIndex(arrAnmSprFlowers[index]
				.getCurrentTileIndex() == 0 ? 1 : 0);
		return;
	}

	private void flower14Modifier() {
		mp3Boyon.play();
		anmSprFlower14
				.setCurrentTileIndex(anmSprFlower14.getCurrentTileIndex() == 0 ? 1
						: 0);
		return;
	}

	private void kenmushiModifier() {
		mp3Pyoi.play();
		anmSprKenmushi
				.setCurrentTileIndex(anmSprKenmushi.getCurrentTileIndex() == 0 ? 1
						: 0);
		return;
	}

	private void stageAnimate() {
		if (!isWinner) {
			mp3Mokin.play();
			anmSprStage
					.setCurrentTileIndex(anmSprStage.getCurrentTileIndex() == 3 ? 0
							: anmSprStage.getCurrentTileIndex() + 1);
		}
	}

	private void birdTouch() {
		if (stageIsStart && isTouchBird) {
			isTouchBird = false;
			isReadly = true;
			mp3Hakey.play();
			mEngine.registerUpdateHandler(new TimerHandler(3f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							isTouchBird = true;
							isReadly = false;
						}
					}));
		}
	}
	// //////////////////////////////////////////////////////////////
	// Inner Class
	// //////////////////////////////////////////////////////////////
	private class myAnimatedSprite extends AnimatedSprite {
		private int index;
		private boolean checkTouch = false;
		public myAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index) {
			super(pX, pY, pTiledTextureRegion, Vol3Kintaro.this
					.getVertexBufferObjectManager());
			this.index = index;
		}
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
    		            if(!isReadly){
                			int action = pSceneTouchEvent.getAction();
                			float pX = pSceneTouchEvent.getX();
                			float pY = pSceneTouchEvent.getY();
                			int distance = 80;
                			int distanceRight = 0;
                			countTouchScreen = pSceneTouchEvent.getMotionEvent().getPointerCount();
                    			if(pSceneTouchEvent.getMotionEvent().getPointerCount()>2){
                    			    return true;
                    			}
                    			if(isTouchKintaro){
                    			    try{
                        			    for (int i = 0; i < 8; i++) {
                        			        this.setPosition(
                                                                Vol3Kintaro.this.arrPositionKintaro[0][this.index],
                                                                Vol3Kintaro.this.arrPositionKintaro[1][this.index]);
                                                        arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                                        this.getParent().sortChildren();
                                                    }
                                                    stageIsStart = false;
                                                    leftIsStart = false;
                                                    rightIsStart = false;
                                                    isWinner = false;
                                                    indexStageLeft = -1;
                                                    indexStageRight = -1;
                                                    winner = -1;
                    			    return true;
                    			    }catch (Exception e) {
                                                    // TODO: handle exception
                                                }
                    			}
                    			switch (action) {
                    			case TouchEvent.ACTION_DOWN:
                    			    if(!checkTouch){
                            				if (this.index != indexStageLeft && index != indexStageRight
                            						&& this.index != 8 && this.index != 9) {
                            					mp3Poyo.play();
                            					this.setCurrentTileIndex(1);
                            				}
                            				if (!isWinner
                            						&& stageIsStart
                            						&& (this.index == indexStageLeft
                            								|| this.index == indexStageRight
                            								|| this.index == 8 || this.index == 9)) {
                            					mp3Tanuki.play();
                            					if (this.index <= 3 || this.index == 8) {
                            						if (indexStageLeft == 3) {
                            							distance = 40;
                            						}
                            						if (indexStageLeft == 3
                            								&& (indexStageRight == 6 || indexStageRight == 7)) {
                            							distance = 0;
                            							distanceRight = 30;
                            						}
                            						arrAnmSprKintaro[indexStageLeft].setPosition(
                            								arrAnmSprKintaro[indexStageLeft].getX()
                            										+ DISTANCE_MOVE_ON_STAGE,
                            								arrAnmSprKintaro[indexStageLeft].getY());
                            						if (arrAnmSprKintaro[indexStageLeft].getX()
                            								+ arrAnmSprKintaro[indexStageLeft].getWidth()
                            								- distance >= arrAnmSprKintaro[indexStageRight]
                            								.getX() + distanceRight) {
                            							arrAnmSprKintaro[indexStageRight].setPosition(
                            									arrAnmSprKintaro[indexStageRight].getX()
                            											+ DISTANCE_MOVE_ON_STAGE,
                            									arrAnmSprKintaro[indexStageRight].getY());
                            							countLeftAfterMeet += 1;
                            						}
                            					} else if ((this.index > 3 && this.index < 8)
                            							|| this.index == 9) {
                            						if (indexStageLeft == 3) {
                            							distance = 40;
                            						}
                            						if (indexStageLeft == 3
                            								&& (indexStageRight == 6 || indexStageRight == 7)) {
                            							distance = 0;
                            							distanceRight = 30;
                            						}
                            						arrAnmSprKintaro[indexStageRight].setPosition(
                            								arrAnmSprKintaro[indexStageRight].getX()
                            										- DISTANCE_MOVE_ON_STAGE,
                            								arrAnmSprKintaro[indexStageRight].getY());
                            						if (arrAnmSprKintaro[indexStageLeft].getX()
                            								+ arrAnmSprKintaro[indexStageLeft].getWidth()
                            								- distance >= arrAnmSprKintaro[indexStageRight]
                            								.getX() + distanceRight) {
                            							arrAnmSprKintaro[indexStageLeft].setPosition(
                            									arrAnmSprKintaro[indexStageLeft].getX()
                            											- DISTANCE_MOVE_ON_STAGE,
                            									arrAnmSprKintaro[indexStageLeft].getY());
                            							countRightAfterMeet += 1;
                            						}
                            					}
                            					if (countLeftAfterMeet == MAX_WIN
                            							|| countRightAfterMeet == MAX_WIN) {
                            					        isWinner = true;
                            						if (countLeftAfterMeet == MAX_WIN) {
                            							winner = LEFT_WIN;
                            							countRightAfterMeet = 0;
                            						} else if (countRightAfterMeet == MAX_WIN) {
                            							winner = RIGHT_WIN;
                            							countRightAfterMeet = 0;
                            						}
                            						// Animation when a Kintaro win
                            						Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoLeft);
                            				                Vol3Kintaro.this.mScene.unregisterTouchArea(anmSprTaikoRight);
                            						animationWin();
                            					}
                            				}
                            				if (!stageIsStart) {
                            					this.setCurrentTileIndex(1);
                            				}
                            				checkTouch = true;
                    			    }
                    			    return true;
                    			case TouchEvent.ACTION_MOVE:
                    				if(checkTouch){
                            				if (!stageIsStart) {
                            					if (this.index != 8 && this.index != 9) {
                            					        if(this.getCurrentTileIndex()!=1){
                            					            this.setCurrentTileIndex(1);
                            					        }
                            						if ((this.index <= 3 && indexStageLeft == -1)
                            								|| (this.index > 3 && indexStageRight == -1)) {
                            							this.setZIndex(999);
                            							this.getParent().sortChildren();
                            						}
                            						if (this.index <= 3
                            								&& (indexStageLeft == -1 || this.index == indexStageLeft)) {
                            							this.setPosition(pX - this.getWidth() / 2, pY
                            									- this.getHeight() / 2);
                            						}
                            						if (this.index > 3
                            								&& (indexStageRight == -1 || this.index == indexStageRight)) {
                            							this.setPosition(pX - this.getWidth() / 2, pY
                            									- this.getHeight() / 2);
                            						}
                            					}
                            				}
                    				}
                    				return true;
                    			case TouchEvent.ACTION_UP:
                    			    if(checkTouch){
                            			if (!isWinner){
                            			        try{
                                                        for (int i = 0; i < 8; i++) {
                                                            arrAnmSprKintaro[i].setCurrentTileIndex(0);
                                                        }
                                                        }catch (Exception e) {
                                                            // TODO: handle exception
                                                        }
                            			}
                    				if (!stageIsStart) {
                    					this.setCurrentTileIndex(0);
                    					if (index <= 3) {
                    						if (isKintaroOnStage(this)) {
                    							float top = TOP_KIRANTO_ON_STAGE;
                    							float left = LEFT_KIRANTO_LEFT_ON_STAGE;
                    							if (this.index == 1 || this.index == 2) {
                    								top += 22;
                    							}
                    							if (this.index == 3) {
                    								left += 32;
                    							}
                    							indexStageLeft = this.index;
                    							leftIsStart = true;
                    							this.setPosition(left, top - this.getHeight());
                    						} else {
                    						    
                    						    if ((indexStageLeft == this.index || indexStageLeft == -1)) {
                    								leftIsStart = false;
                    								this.setPosition(
                    										Vol3Kintaro.this.arrPositionKintaro[0][this.index],
                    										Vol3Kintaro.this.arrPositionKintaro[1][this.index]);
                    								for (int i = 0; i < 4; i++) {
                    									arrAnmSprKintaro[i].setZIndex(888 + i);
                    									this.getParent().sortChildren();
                    								}
                    								indexStageLeft = -1;
                    						    }
                    						}
                    					} else{
                    					    if (this.index != 8 && this.index != 9) {
                    						if (isKintaroOnStage(this)) {
                    							float top = TOP_KIRANTO_ON_STAGE;
                    							float left = LEFT_KIRANTO_RIGHT_ON_STAGE;
                    							if (this.index == 5) {
                    								top += 13;
                    							}
                    							if (this.index == 6) {
                    								top += 12;
                    								left += 26;
                    							}
                    							if (this.index == 7) {
                    								left += 26;
                    							}
                    
                    							indexStageRight = this.index;
                    							rightIsStart = true;
                    							this.setPosition(left, top - this.getHeight());
                    						} else {
                    						        
                    							if ((indexStageRight == this.index || indexStageRight == -1)) {
                    								rightIsStart = false;
                    								this.setPosition(
                    										Vol3Kintaro.this.arrPositionKintaro[0][this.index],
                    										Vol3Kintaro.this.arrPositionKintaro[1][this.index]);
                    								for (int i = 4; i < 8; i++) {
                    									arrAnmSprKintaro[i].setZIndex(888 + i);
                    									this.getParent().sortChildren();
                    								}
                    								indexStageRight = -1;
                    							}
                    						}
                    					}
                    					}
                    				} else {
                    					if (this.index != indexStageLeft
                    							&& this.index != indexStageRight) {
                    						this.setCurrentTileIndex(0);
                    					}
                    				}
                    				checkTouch = false;
                    			    }
                    			    return true;
                    			default:
                    				break;
            			}
		        }
			return false;
		}
		        
	}
	private boolean isKintaroOnStage(myAnimatedSprite animatedSprite) {
		int distanceTop = 0;
		int distanceLeft = 0;

		if (animatedSprite.index == 0) {

			distanceTop = 50;
			if (indexStageLeft == -1) {
				distanceLeft = 100;
			}
		}
		if (animatedSprite.getX() + animatedSprite.getWidth() / 3 >= anmSprStage
				.getX() + 80 + distanceLeft
				&& animatedSprite.getX() + animatedSprite.getWidth() / 3 <= anmSprStage
						.getX() + anmSprStage.getWidth() - 60
				&& animatedSprite.getY() + animatedSprite.getHeight() * 3 / 4 >= anmSprStage
						.getY() + 40 - distanceTop
				&& animatedSprite.getY() + animatedSprite.getHeight() * 3 / 4 <= anmSprStage
						.getY() + anmSprStage.getHeight() - 30) {
			return true;
		}
		return false;
	}

	private void animationWin() {
	        isReadly = true;
		this.anmSprKenmushi.setVisible(true);
		// Stage animation
		anmSprStage.animate(300);

		// Scale Winner And Loser
		winnerScale = new SequenceEntityModifier(new ScaleModifier(0.5f, 1.0f,
				2.0f), new ScaleModifier(0.5f, 2.0f, 1.0f));

		if (winner == RIGHT_WIN && indexStageRight == 4) {
			winnerScale = new SequenceEntityModifier(new ScaleModifier(0.1f,
					1.0f, 1.0f));
		}
		winnerScale.addModifierListener(this);
		loserScale = new ScaleModifier(0.5f, 1.0f, 0.5f);
		loserScale.addModifierListener(this);

		// Clear Bird Modifier
		// anmSprBird.clearEntityModifiers();
		// Kami Animation
		if (winner == LEFT_WIN) {
			anmSprBird.setCurrentTileIndex(1);
			this.arrAnmSprKintaro[indexStageLeft].setZIndex(1111);
			this.arrAnmSprKintaro[indexStageRight].setZIndex(1110);
			this.mScene.sortChildren();
			this.arrAnmSprKintaro[indexStageLeft].setCurrentTileIndex(3);
			this.arrAnmSprKintaro[indexStageRight].setCurrentTileIndex(2);

			this.arrAnmSprKintaro[indexStageLeft]
					.registerEntityModifier(winnerScale);
			this.arrAnmSprKintaro[indexStageRight]
					.registerEntityModifier(loserScale);

			this.anmSprKamihubuki.setPosition(
					this.arrAnmSprKintaro[indexStageLeft].getX(), 7);
		} else {

			anmSprBird.setCurrentTileIndex(2);
			this.arrAnmSprKintaro[indexStageRight].setZIndex(1111);
			this.arrAnmSprKintaro[indexStageLeft].setZIndex(1110);
			this.mScene.sortChildren();
			this.arrAnmSprKintaro[indexStageLeft].setCurrentTileIndex(2);
			this.arrAnmSprKintaro[indexStageRight].setCurrentTileIndex(3);
			this.arrAnmSprKintaro[indexStageLeft]
					.registerEntityModifier(loserScale);
			this.arrAnmSprKintaro[indexStageRight]
					.registerEntityModifier(winnerScale);
			this.anmSprKamihubuki.setPosition(
					this.arrAnmSprKintaro[indexStageRight].getX(), 7);
		}
		mp3Kansei.play();
		this.anmSprKamihubuki.setVisible(true);
		this.anmSprKamihubuki.animate(400);
	}

	private void resetStage() {
		anmSprTaikoLeft.setPosition(-40, 525);
		anmSprTaikoLeft.setVisible(false);
		anmSprTaikoRight.setVisible(false);
		anmSprTaikoRight.setPosition(838, 525);
		// Stop Kamihubuki Animation
		this.anmSprKamihubuki.stopAnimation();
		this.anmSprKamihubuki.setVisible(false);
		this.anmSprBird.setCurrentTileIndex(0);

		// Stop Stage Animation
		this.anmSprStage.stopAnimation();
		this.anmSprStage.setCurrentTileIndex(0);

		// Reset position tow Kintaro on Stage
		this.arrAnmSprKintaro[indexStageLeft].setPosition(
				arrPositionKintaro[0][indexStageLeft],
				arrPositionKintaro[1][indexStageLeft]);
		this.arrAnmSprKintaro[indexStageRight].setPosition(
				arrPositionKintaro[0][indexStageRight],
				arrPositionKintaro[1][indexStageRight]);
		this.arrAnmSprKintaro[indexStageLeft].setCurrentTileIndex(0);
		this.arrAnmSprKintaro[indexStageRight].setCurrentTileIndex(0);
		this.arrAnmSprKintaro[indexStageLeft].setScale(1.0f);
		this.arrAnmSprKintaro[indexStageRight].setScale(1.0f);

		for (int i = 0; i < 8; i++) {
			arrAnmSprKintaro[i].setZIndex(888 + i);
		}
		this.mScene.sortChildren();
		// Reset status
		this.indexStageLeft = -1;
		this.indexStageRight = -1;
		this.leftIsStart = false;
		this.rightIsStart = false;
		this.stageIsStart = false;
		updateHandler = false;
		//regisTouchArea();
		isWinner = false;
		isReadly = false;
		this.countLeftAfterMeet = 0;
		this.countRightAfterMeet = 0;
		return;
	}

	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		// TODO Auto-generated method stub
		if (pModifier.equals(sunOutlineModifier)) {
			isTouchSun = true;
			anmSprSunInline.setCurrentTileIndex(0);
			anmSprSunOutline.setCurrentTileIndex(0);
		}
		if (pModifier.equals(arrKumoModifier[0])) {
			arrIsTouchKumo[0] = true;
		}
		if (pModifier.equals(arrKumoModifier[1])) {
			arrIsTouchKumo[1] = true;
		}
		if (pModifier.equals(arrKumoModifier[2])) {
			arrIsTouchKumo[2] = true;
		}
		if (pModifier.equals(arrKumoModifier[3])) {
			arrIsTouchKumo[3] = true;
		}
		if (pModifier.equals(winnerScale)) {

			if (winner == RIGHT_WIN && indexStageRight == 4) {
				int frames[] = { 3, 4, 5, 6, 7, 5 };
				long durations[] = { 600, 600, 600, 600, 600, 600 };
				this.arrAnmSprKintaro[indexStageRight].animate(durations,
						frames, 0, this);
				mEngine.registerUpdateHandler(new TimerHandler(3.9f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler arg0) {
								resetStage();
							}
						}));
			} else {
				if (winner == LEFT_WIN) {
					this.arrAnmSprKintaro[indexStageLeft]
							.setRotationCenterX(this.arrAnmSprKintaro[indexStageLeft]
									.getWidth() / 3);
					this.arrAnmSprKintaro[indexStageLeft]
							.setRotationCenterY(this.arrAnmSprKintaro[indexStageLeft]
									.getHeight());
					// this.arrAnmSprKintaro[indexStageLeft].setRotationCenter(0.33f,
					// 1.0f);
					if (indexStageLeft == 3) {

						this.arrAnmSprKintaro[indexStageLeft]
								.registerEntityModifier(winnerRotationRight = new SequenceEntityModifier(
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY(),
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30,
												arrAnmSprKintaro[indexStageLeft]
														.getY()),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY(),
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30,
												arrAnmSprKintaro[indexStageLeft]
														.getY()),
										new RotationModifier(0.6f, 0.0f, -10.0f),
										new RotationModifier(1.2f, -10f, 10.0f),
										new RotationModifier(0.6f, 10.0f, 0.0f)));
					} else {
						this.arrAnmSprKintaro[indexStageLeft]
								.registerEntityModifier(winnerRotationLeft = new SequenceEntityModifier(
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY(),
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30,
												arrAnmSprKintaro[indexStageLeft]
														.getY()),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY(),
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30),
										new MoveYModifier(
												0.3f,
												arrAnmSprKintaro[indexStageLeft]
														.getY() - 30,
												arrAnmSprKintaro[indexStageLeft]
														.getY()),
										new RotationModifier(0.6f, 0.0f, 10.0f),
										new RotationModifier(1.2f, 10f, -10.0f),
										new RotationModifier(0.6f, -10.0f, 0.0f)));
					}

				} else if (winner == RIGHT_WIN) {
					// this.arrAnmSprKintaro[indexStageRight].setRotationCenter(0.33f,
					// 1.0f);
					this.arrAnmSprKintaro[indexStageRight]
							.setRotationCenterX(this.arrAnmSprKintaro[indexStageRight]
									.getWidth() / 3);
					this.arrAnmSprKintaro[indexStageRight]
							.setRotationCenterY(this.arrAnmSprKintaro[indexStageRight]
									.getHeight());
					this.arrAnmSprKintaro[indexStageRight]
							.registerEntityModifier(winnerRotationLeft = new SequenceEntityModifier(
									new MoveYModifier(0.3f,
											arrAnmSprKintaro[indexStageRight]
													.getY(),
											arrAnmSprKintaro[indexStageRight]
													.getY() - 30),
									new MoveYModifier(0.3f,
											arrAnmSprKintaro[indexStageRight]
													.getY() - 30,
											arrAnmSprKintaro[indexStageRight]
													.getY()),
									new MoveYModifier(0.3f,
											arrAnmSprKintaro[indexStageRight]
													.getY(),
											arrAnmSprKintaro[indexStageRight]
													.getY() - 30),
									new MoveYModifier(0.3f,
											arrAnmSprKintaro[indexStageRight]
													.getY() - 30,
											arrAnmSprKintaro[indexStageRight]
													.getY()),
									new RotationModifier(0.6f, 0.0f, 10.0f),
									new RotationModifier(1.2f, 10f, -10.0f),
									new RotationModifier(0.6f, -10.0f, 0.0f)));
				}
				mEngine.registerUpdateHandler(new TimerHandler(3.9f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler arg0) {
								resetStage();
							}
						}));
			}
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		// TODO Auto-generated method stub

	}

}
