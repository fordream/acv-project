package jp.co.xing.utaehon03.songs;

import java.util.LinkedList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ChiisanasekaiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.util.Log;

/**
 * @author HiepNT
 * @return Vol3Chiisanasekai
 */
public class Vol3Chiisanasekai extends BaseGameActivity implements IOnSceneTouchListener{
	/** TAG */
	private String TAG = "CHIISANASEKAI";
	
	/** LOAD */
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack texpackBackGround, texpackItem, texpackVictory;
	private TexturePackTextureRegionLibrary texpackLibBackGround, texpackLibItem, texpackLibVictory;
	
	/** BACK GROUND */
	private Sprite sprBackGround;
	private TextureRegion ttrBackGround;
	
	/** GROUND HIDE */
	private Sprite sprHidingPlace[] = new Sprite[23];
	private TextureRegion ttrHidingPlace[] = new TextureRegion[23];
	private float pPosition_GroundHide[][] = {{518,249},{477,35},{746,195},{42,155},{294,211},{38,216},{602,258},
		{584,380},{787,338},{145,215},{321,108},{337,316},{388,183},{54,393},{81,421},{244,45},{188,93},{96,54},
		{619,102},{41,0},{380,-45},{793,74},{497,455}};
	
	/** VICTORY */
	/** BACKGROUND VICTORY */
	private AnimatedSprite animspr_BGVictory;
	private ITiledTextureRegion tttr_BGVictory;
	
	/** HAND VICTORY */
	private Sprite spr_BGHand;
	private TextureRegion ttr_BGHand;
	
	/** FACE VICTORY ROTATE */
	private Sprite sprRotate[] = new Sprite[6];
	private TextureRegion ttrRotate[] = new TextureRegion[6];
	private float pPosition_Rotate[][] = {{115,43},{226,15},{373,9},{470,17},{610,12},{734,12}};
	
	/** FACE VICTORY MOVE */
	private Sprite sprMove[] = new Sprite[2];
	private TextureRegion ttrMove[] = new TextureRegion[2];
	private float pPosition_Move[][] = {{707,327},{56,326}};
	
	/** BALLON VICTORY */
	private Sprite sprBaloon[] = new Sprite[4];
	private TextureRegion ttrBaloon[] = new TextureRegion[4];
	private float pPosition_Baloon[][] = {{22,273},{26,95},{825,283},{840,126}};
	
	/** GIRL */
	private AnimatedSprite animsprGIRL;
	private ITiledTextureRegion tttrGIRL;
	private int seGirl_DOWN;
	private int seGirl_MOVE;
	private boolean isTouchGIRL;
	private boolean isMoveGIRL;
	private boolean isMp3Girl;
	
	/** BOY */
	private AnimatedSprite animsprBOY;
	private ITiledTextureRegion tttrBOY;
	private int seBoy_MOVE;
	private boolean isTouchBOY;
	private boolean isMoveBOY;
	private boolean isMp3Boy;
	
	/** SOLDIER */
	private AnimatedSprite animsprSoldier;
	private ITiledTextureRegion tttrSoldier;
	private boolean isTouchSoldier;
	
	/** START GAME */
	private boolean beginGame;
	
	private int finish_COUNT;
	
	private LinkedList<AnimFace> linkedAnimFACE = new LinkedList<Vol3Chiisanasekai.AnimFace>();
	
	/** ITEM */
	/** HAND */
	private Sprite sprHand[] = new Sprite[8];
	private TextureRegion ttrHand[] = new TextureRegion[8];
	
	/** POSITION HAND */
	private float arrpPosition_Hand[][] = {{174,49},{285,205},{82,330},{108,414},{290,305},{400,180},{495,120},{420,313},
		{629,255},{627,376},{680,-4},{795,145},{807,187},{745,300},{805,334}};
	
	/** POSITION FACE */
	private float arrpPosition_Face[][]= {{171,11},{276,170},{74,260},{100,345},{283,235},{394,110},{487,106},{412,243},
		{622,187},{620,307},{673,-16},{743,73},{799,118},{694,226},{797,263}};
	
	/** POSITION HOUSE */
	private float arrpPosition_House[] = {802,374,481,266,588,159,695,52};
	
	/** POSITION HOUSE TRUE */
	private float house_TRUE[] = {825,395,501,287,609,180,716,74};
	
	/** FACE MOVE */
	private AnimatedSprite animspr_FACE_MOVE[] = new AnimatedSprite[8];
	private ITiledTextureRegion tttr_Face_Move[] = new ITiledTextureRegion[8];
	
	/** FACE */
	private AnimFace animspr_FACE[] = new AnimFace[8];
	private ITiledTextureRegion tttr_Face[] = new ITiledTextureRegion[8];
	
	/** HOUSE */
	private AnimHouse animsprHouse[] = new AnimHouse[8];
	private ITiledTextureRegion tttrHouse[] = new ITiledTextureRegion[8];
	
	/** POSITION FACE IN HOUSE */
	private float arrPointtHouse[][] = {{0,54,107,107,0,0},{60,0,60,120,120,60}};
	
	/** RANDOM FACE APPEAR */
	RanDomNoRepeat rdNoRepeat;
	private int rdPositionHand[] = new int[8];
	
	/** TIMER HANDLER */
	/** TIMER FACE APPEAR */
	private TimerHandler timerFace;
	
	/** SOUND */
	private Sound OGG_A78_1_OMEDETO;
	private Sound OGG_A78_2_TOKO;
	private Sound OGG_A78_3_DON;
	private Sound OGG_A78_3_HIE_GIRL;
	private Sound OGG_A78_3_WA_GIRL;
	private Sound OGG_A78_3_YAMETEYO_GIRL;
	private Sound OGG_A78_3_YAMETE_GIRL;
	private Sound OGG_A78_3_YAN_GIRL;
	private Sound OGG_A78_4_HIE_BOY;
	private Sound OGG_A78_4_YAMERO_BOY;
	private Sound OGG_A78_4_YAMETE_BOY;
	private Sound OGG_A78_5_1_2;
	private Sound OGG_A78_5_2_2;
	private Sound OGG_A78_5_3_2;
	private Sound OGG_A78_5_4_2;
	private Sound OGG_A78_5_5_2;
	private Sound OGG_A78_5_6_2;
	private Sound OGG_A78_5_7_2;
	private Sound OGG_A78_5_8_2;
	private Sound OGG_A78_5_BATA;
	private Sound OGG_A78_5_HYOKO;
	private Sound OGG_A78_5_SYU;
	private Sound OGG_A78_6_PACHI;
	private Sound OGG_A78_6_PINPON;
	private Sound OGG_A78_6_TIGAUYO1;
	private Sound OGG_A78_6_TIGAUYO2;
	private Sound OGG_A78_KOBITO_VO;
	
	private Sound OGG_NAME_COLOR[];
	private Sound OGG_HOUSE_FAIL[];

	/** ON CREAT RESOURCES */
	@Override
	public void onCreateResources() {
		Log.d(TAG, "Load --- onCreateResources");
		SoundFactory.setAssetBasePath("chiisanasekai/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("chiisanasekai/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
			getTextureManager(), pAssetManager, "chiisanasekai/gfx/");
		super.onCreateResources();
		Log.d(TAG, "Load --- onCreateResources --- End");
	}
	
	/** LOAD KARAOKE RESOURCES */
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "Load --- loadKaraokeResources");
		// LIBRARY
		// BACKGROUND LIB
		texpackBackGround = mTexturePackLoaderFromSource.load("background.xml");
		texpackBackGround.loadTexture();
		texpackLibBackGround = texpackBackGround.getTexturePackTextureRegionLibrary();
		
		// ITEM LIB
		texpackItem = mTexturePackLoaderFromSource.load("item.xml");
		texpackItem.loadTexture();
		texpackLibItem = texpackItem.getTexturePackTextureRegionLibrary();
		
		// VICTORY LIB
		texpackVictory = mTexturePackLoaderFromSource.load("victory.xml");
		texpackVictory.loadTexture();
		texpackLibVictory = texpackVictory.getTexturePackTextureRegionLibrary();
		
		
		// ttrBackGround
		ttrBackGround = texpackLibBackGround.get(Vol3ChiisanasekaiResource.background.A78_1_1_IPHONE_ID);
		
		// ttrHidingPlace
		for (int i = 0; i < ttrHidingPlace.length; i++) {
			ttrHidingPlace[i] =  texpackLibBackGround.get(Vol3ChiisanasekaiResource.int_Hiding_Place[i]);
		}
		
		// VICTORY
		tttr_BGVictory = getTiledTextureFromPacker(texpackVictory, Vol3ChiisanasekaiResource.victory.A78_1_25_13_1_IPHONE_ID,
			Vol3ChiisanasekaiResource.victory.A78_1_25_13_2_IPHONE_ID);
		
		ttr_BGHand = texpackLibVictory.get(Vol3ChiisanasekaiResource.victory.A78_1_25_14_IPHONE_ID);
		
		for (int i = 0; i < ttrRotate.length; i++) {
			ttrRotate[i] = texpackLibVictory.get(Vol3ChiisanasekaiResource.int_Rotate[i]);
		}
		
		for (int i = 0; i < ttrBaloon.length; i++) {
			ttrBaloon[i] = texpackLibVictory.get(Vol3ChiisanasekaiResource.int_Baloon[i]);
		}
		
		for (int i = 0; i < ttrMove.length; i++) {
			ttrMove[i] = texpackLibVictory.get(Vol3ChiisanasekaiResource.int_Move[i]);
		}
		
		// tttrGIRL
		tttrGIRL = getTiledTextureFromPacker(texpackItem, 
			Vol3ChiisanasekaiResource.item.A78_3_1_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_3_2_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_3_3_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_3_4_IPHONE_ID);
		
		// tttrBOY
		tttrBOY = getTiledTextureFromPacker(texpackItem, 
			Vol3ChiisanasekaiResource.item.A78_4_1_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_4_2_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_4_3_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_4_4_IPHONE_ID);
		
		// tttrSoldier
		tttrSoldier = getTiledTextureFromPacker(texpackItem, 
			Vol3ChiisanasekaiResource.item.A78_2_1_IPHONE_ID,
			Vol3ChiisanasekaiResource.item.A78_2_2_IPHONE_ID);
		
		// ttrHand
		for (int i = 0; i < ttrHand.length; i++) {
			ttrHand[i] = texpackLibItem.get(Vol3ChiisanasekaiResource.int_Hand[i]);
		}
		
		// tttr_Face_Move
		for (int i = 0; i < tttr_Face_Move.length; i++) {
			ITextureRegion[] tmpFace_MOVE = new ITextureRegion[Vol3ChiisanasekaiResource.int_Face_Move[i].length];
			
			for (int j = 0; j < Vol3ChiisanasekaiResource.int_Face_Move[i].length; j++) {
				tmpFace_MOVE[j] = texpackLibItem.get(Vol3ChiisanasekaiResource.int_Face_Move[i][j]);
			}
			
			tttr_Face_Move[i] = new TiledTextureRegion (texpackItem.getTexture(), tmpFace_MOVE);
		}
		
		// tttr_Face
		for (int i = 0; i < tttr_Face.length; i++) {
			tttr_Face[i] = getTiledTextureFromPacker(texpackItem, Vol3ChiisanasekaiResource.int_Face[i]);
		}
		
		// tttrHouse
		for (int i = 0; i < tttrHouse.length; i++) {
			ITextureRegion[] tmpHouse = new ITextureRegion[Vol3ChiisanasekaiResource.int_House[i].length];
			
			for (int j = 0; j < Vol3ChiisanasekaiResource.int_House[i].length; j++) {
				tmpHouse[j] = texpackLibItem.get(Vol3ChiisanasekaiResource.int_House[i][j]);
			}
			
			tttrHouse[i] = new TiledTextureRegion (texpackItem.getTexture(), tmpHouse);
		}
		
		Log.d(TAG, "Load --- loadKaraokeResources --- End");
	}

	/** LOAD KARAOKE SOUND */
	@Override
	protected void loadKaraokeSound() {
		OGG_A78_1_OMEDETO = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_1_OMEDETO);
		OGG_A78_2_TOKO = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_2_TOKO);
		OGG_A78_3_DON = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_DON);
		OGG_A78_3_HIE_GIRL = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_HIE_GIRL);
		OGG_A78_3_WA_GIRL = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_WA_GIRL);
		OGG_A78_3_YAMETEYO_GIRL = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_YAMETEYO_GIRL);
		OGG_A78_3_YAMETE_GIRL = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_YAMETE_GIRL);
		OGG_A78_3_YAN_GIRL = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_3_YAN_GIRL);
		OGG_A78_4_HIE_BOY = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_4_HIE_BOY);
		OGG_A78_4_YAMERO_BOY = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_4_YAMERO_BOY);
		OGG_A78_4_YAMETE_BOY = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_4_YAMETE_BOY);
		OGG_A78_5_1_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_1_2);
		OGG_A78_5_2_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_2_2);
		OGG_A78_5_3_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_3_2);
		OGG_A78_5_4_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_4_2);
		OGG_A78_5_5_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_5_2);
		OGG_A78_5_6_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_6_2);
		OGG_A78_5_7_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_7_2);
		OGG_A78_5_8_2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_8_2);
		OGG_A78_5_BATA = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_BATA);
		OGG_A78_5_HYOKO = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_HYOKO);
		OGG_A78_5_SYU = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_5_SYU);
		OGG_A78_6_PACHI = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_6_PACHI);
		OGG_A78_6_PINPON = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_6_PINPON);
		OGG_A78_6_TIGAUYO1 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_6_TIGAUYO1);
		OGG_A78_6_TIGAUYO2 = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_6_TIGAUYO2);
		OGG_A78_KOBITO_VO = loadSoundResourceFromSD(Vol3ChiisanasekaiResource.A78_KOBITO_VO);
		
		OGG_NAME_COLOR = new Sound[]{OGG_A78_5_1_2,OGG_A78_5_2_2,OGG_A78_5_3_2,OGG_A78_5_4_2,
				OGG_A78_5_5_2,OGG_A78_5_6_2,OGG_A78_5_7_2,OGG_A78_5_8_2};
		
		OGG_HOUSE_FAIL = new Sound[]{OGG_A78_6_TIGAUYO1,OGG_A78_6_TIGAUYO2};
	}
	/** INT POSITION CURENT*/
	int lastGx = 0;
	int lastGy = 0;
	
	int lastBx = 0;
	int lastBy = 0;
	
	int lastX ;
	int lastY ;
	
	/** LOAD KARAOKE SCENE */
	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "Load --- loadKaraokeScene");
		mScene = new Scene();
		
		// Background
		sprBackGround = new Sprite(0, 0, ttrBackGround, this.getVertexBufferObjectManager());
		mScene.attachChild(sprBackGround);
		
		// Hiding place
		for (int i = 0; i < sprHidingPlace.length; i++) {
			sprHidingPlace[i] = new Sprite(pPosition_GroundHide[i][0], pPosition_GroundHide[i][1], 
				ttrHidingPlace[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprHidingPlace[i]);
		}
		
		// Hand
		for (int i = 0; i < sprHand.length; i++) {
			sprHand[i] = new Sprite(0, 0, ttrHand[i], this.getVertexBufferObjectManager());
			sprHand[i].setVisible(false);
			mScene.attachChild(sprHand[i]);
		}
		
		//Girl
		animsprGIRL = new AnimatedSprite(590, 278, tttrGIRL, this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				int pGirlx = (int) pSceneTouchEvent.getX();
				int pGirly = (int) pSceneTouchEvent.getY();
				
				switch (pSceneTouchEvent.getAction()) {
				case  TouchEvent.ACTION_DOWN:
					if(checkContains(animsprGIRL, 70, 0, 243, 330, pGirlx, pGirly) 
							&& isTouchGIRL 
							&& !isMoveGIRL) {
						isTouchGIRL = false;
						isMoveGIRL = true;
						isMp3Girl = true;
						animsprGIRL.setCurrentTileIndex(2);
						if(seGirl_DOWN % 2 == 0){
							OGG_A78_3_WA_GIRL.play();
						} else OGG_A78_3_HIE_GIRL.play();
						
						lastGx = pGirlx;
						lastGy = pGirly;
						return true;
					}
					break;
					
				case  TouchEvent.ACTION_MOVE:
					// If move > 20 px
					if(isMoveGIRL 
							&& !isTouchGIRL 
							&& Math.abs(((int)pSceneTouchEvent.getX() + (int) pSceneTouchEvent.getY()) - (lastGx + lastGy)) > 20) {
						if(isMp3Girl){
							isMp3Girl = false;
							
							OGG_A78_3_WA_GIRL.stop();
							OGG_A78_3_HIE_GIRL.stop();
							
							if(seGirl_MOVE % 3 == 0){
								OGG_A78_3_YAN_GIRL.play();
							} else if (seGirl_MOVE % 3 == 1){
								OGG_A78_3_YAMETE_GIRL.play();
							} else if (seGirl_MOVE % 3 == 2){
								OGG_A78_3_YAMETEYO_GIRL.play();
							}
						}
						
						if(animsprGIRL.getX() < -60){
							animsprGIRL.setPosition(-60, pGirly - animsprGIRL.getHeight()/2);
						} else if (animsprGIRL.getX() > 750) {
							animsprGIRL.setPosition(750, pGirly - animsprGIRL.getHeight()/2);
						} else {
							animsprGIRL.setPosition(pGirlx - animsprGIRL.getWidth()/2, pGirly - animsprGIRL.getHeight()/2);
						}
						
						// If animsprGIRL is running
						if(!animsprGIRL.isAnimationRunning()){
							animsprGIRL.animate(new long []{500,500}, 2, 3, -1);
						}
						animsprGIRL.setZIndex(11);
						mScene.sortChildren();
					}
					break;	
					
				case  TouchEvent.ACTION_UP:
					if(!isTouchGIRL 
							&& isMoveGIRL) {
						mScene.setOnAreaTouchTraversalBackToFront();
						seGirl_DOWN++;
						seGirl_MOVE++;
						isMoveGIRL = false;
						animsprGIRL.stopAnimation();
						animsprGIRL.setZIndex(2);
						mScene.sortChildren();
						
						// If animsprGIRL.getY() >= animsprGIRL.getmYFirst()
						if(animsprGIRL.getY() >= animsprGIRL.getmYFirst()){
							animsprGIRL.setCurrentTileIndex(0);
							animsprGIRL.setPosition(animsprGIRL.getX(), animsprGIRL.getmYFirst());
							animsprGIRL.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									isTouchGIRL = true;
								}
							}));
						} else {
							// If animsprGIRL.getY() < animsprGIRL.getmYFirst()
							float timeG = Math.abs(animsprGIRL.getY() - 570) / 500  ;
							timerSoundDelay(OGG_A78_3_DON, 0.3f);
							animsprGIRL.setCurrentTileIndex(1);
							animsprGIRL.registerEntityModifier(
								new MoveYModifier(timeG, animsprGIRL.getY(), animsprGIRL.getmYFirst(), new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isTouchGIRL = true;
										animsprGIRL.setCurrentTileIndex(0);
									}
								}, EaseBounceOut.getInstance()));
						}
					}
					break;	
				}
				return false;
			}
		};
		mScene.attachChild(animsprGIRL);
		mScene.registerTouchArea(animsprGIRL);
		animsprGIRL.setScaleCenter(animsprGIRL.getWidth()/2, animsprGIRL.getHeight()/2);
		animsprGIRL.setScale(0.7f);
		animsprGIRL.setZIndex(2);

		// FACE 
		for (int i = 0; i < animspr_FACE.length; i++) {
			animspr_FACE[i] = new AnimFace(0, 0, tttr_Face[i], this.getVertexBufferObjectManager(), i);
			mScene.attachChild(animspr_FACE[i]);
			mScene.registerTouchArea(animspr_FACE[i]);
		}
		
		// Boy
		animsprBOY = new AnimatedSprite(202, 358, tttrBOY, this.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				int pBoyx = (int) pSceneTouchEvent.getX();
				int pBoyy = (int) pSceneTouchEvent.getY();
				
				switch (pSceneTouchEvent.getAction()) {
				case  TouchEvent.ACTION_DOWN:
					if(checkContains(animsprBOY, 48, 5, 198, 233, pBoyx, pBoyy) 
							&& isTouchBOY 
							&& !isMoveBOY) { 
						OGG_A78_4_HIE_BOY.play();
						isTouchBOY = false;
						isMoveBOY = true;
						isMp3Boy = true;
						animsprBOY.setCurrentTileIndex(2);
						
						lastBx = pBoyx;
						lastBy = pBoyy;
						
						return true;
					}
					break;
					
				case  TouchEvent.ACTION_MOVE:
					// If move > 20 px
					if(isMoveBOY 
							&& !isTouchBOY 
							&& Math.abs(((int)pSceneTouchEvent.getX() + (int) pSceneTouchEvent.getY()) - (lastBx + lastBy)) > 20) {
						if(isMp3Boy){
							isMp3Boy = false;
							
							OGG_A78_4_HIE_BOY.stop();
							
							if(seBoy_MOVE % 2 == 0 ){
								OGG_A78_4_YAMETE_BOY.play();
							}else {
								OGG_A78_4_YAMERO_BOY.play();
							}
						}
						
						if(animsprBOY.getX() < -40){
							animsprBOY.setPosition(-40, pBoyy - animsprBOY.getHeight()/2);
						} else if (animsprBOY.getX() > 750) {
							animsprBOY.setPosition(750, pBoyy - animsprBOY.getHeight()/2);
						} else {
							animsprBOY.setPosition(pBoyx - animsprBOY.getWidth()/2, pBoyy - animsprBOY.getHeight()/2);
						}
						
						// If animsprBoy is running
						if(!animsprBOY.isAnimationRunning()){
							animsprBOY.animate(new long []{500,500}, 2, 3, -1);
						}
						animsprBOY.setZIndex(11);
						mScene.sortChildren();
					}
					break;	
					
				case  TouchEvent.ACTION_UP:
					if(!isTouchBOY 
							&& isMoveBOY) {
						mScene.setOnAreaTouchTraversalFrontToBack();
						isMoveBOY = false;
						animsprBOY.stopAnimation();
						animsprBOY.setZIndex(2);
						mScene.sortChildren();
						
						// If animsprBOY.getY() >= animsprBOY.getmYFirst()
						if(animsprBOY.getY() >= animsprBOY.getmYFirst()){
							animsprBOY.setCurrentTileIndex(0);
							animsprBOY.setPosition(animsprBOY.getX(), animsprBOY.getmYFirst());
							animsprBOY.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									isTouchBOY = true;
								}
							}));
						} else {
							// If animsprBOY.getY() < animsprBOY.getmYFirst()
							float timeB = Math.abs(animsprBOY.getY() - 570) / 500  ;
							timerSoundDelay(OGG_A78_3_DON, 0.3f);
							animsprBOY.setCurrentTileIndex(1);
							animsprBOY.registerEntityModifier(
								new MoveYModifier(timeB, animsprBOY.getY(), animsprBOY.getmYFirst(), new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isTouchBOY = true;
										animsprBOY.setCurrentTileIndex(0);
									}
								}, EaseBounceOut.getInstance()));
						}
					}
					break;	
				}
				return false;
			}
		};
		mScene.attachChild(animsprBOY);
		mScene.registerTouchArea(animsprBOY);
		animsprBOY.setScaleCenter(animsprBOY.getWidth()/2, animsprBOY.getHeight()/2);
		animsprBOY.setScale(0.7f);
		animsprBOY.setZIndex(2);
		
		// Soldier
		animsprSoldier = new AnimatedSprite(790, 440, tttrSoldier, this.getVertexBufferObjectManager());
		mScene.attachChild(animsprSoldier);
		animsprSoldier.setZIndex(3);
		
		// House
		for (int i = 0; i < animsprHouse.length; i++) {
			animsprHouse[i] = new AnimHouse(arrpPosition_House[i], 515, tttrHouse[i], this.getVertexBufferObjectManager(),i);
			mScene.attachChild(animsprHouse[i]);
			animsprHouse[i].setZIndex(4);
			mScene.registerTouchArea(animsprHouse[i]);
		}
		
		// Face move
		for (int i = 0; i < animspr_FACE_MOVE.length; i++) {
			animspr_FACE_MOVE[i] = new AnimatedSprite(0, 0, tttr_Face_Move[i], this.getVertexBufferObjectManager());
			animspr_FACE_MOVE[i].setVisible(false);
			mScene.attachChild(animspr_FACE_MOVE[i]);
		}

		// VICTORY
		// Background victory
		animspr_BGVictory = new AnimatedSprite(123, 175, tttr_BGVictory, this.getVertexBufferObjectManager());
		mScene.attachChild(animspr_BGVictory);
		animspr_BGVictory.setVisible(false);
		animspr_BGVictory.setZIndex(6);
		
		// Background hand
		spr_BGHand = new Sprite(140, 149, ttr_BGHand, this.getVertexBufferObjectManager());
		mScene.attachChild(spr_BGHand);
		spr_BGHand.setVisible(false);
		spr_BGHand.setZIndex(7);
		
		// Sprite rotate
		for (int i = 0; i < sprRotate.length; i++) {
			sprRotate[i] = new Sprite(pPosition_Rotate[i][0], pPosition_Rotate[i][1], 
				ttrRotate[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprRotate[i]);
			sprRotate[i].setVisible(false);
			sprRotate[i].setZIndex(8);
		}
		
		// Baloon victory
		for (int i = 0; i < sprBaloon.length; i++) {
			sprBaloon[i] = new Sprite(pPosition_Baloon[i][0], pPosition_Baloon[i][1], 
				ttrBaloon[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprBaloon[i]);
			sprBaloon[i].setVisible(false);
			sprBaloon[i].setZIndex(9);
		}
		
		// Sprite move
		for (int i = 0; i < sprMove.length; i++) {
			sprMove[i] = new Sprite(pPosition_Move[i][0], pPosition_Move[i][1], 
					ttrMove[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sprMove[i]);
			sprMove[i].setVisible(false);
			sprMove[i].setZIndex(10);
		}
		
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "Load --- loadKaraokeScene --- End");
	}

	/** GIMMIC */
	@Override
	public void combineGimmic3WithAction() {
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean focus){
		super.onWindowFocusChanged(focus);
		beginGame = focus;
	}
	
	/** RESUME GAME*/
	@Override
	public synchronized void onResumeGame() {
//		mScene.registerUpdateHandler(new IUpdateHandler() {
//           @Override
//           public void reset() {
//
//           }
//
//           @Override
//           public void onUpdate(float arg0) {
//               try {
//                   beginGame = getActivity().hasWindowFocus();
//               } catch (Exception e) {
//                   beginGame = true;
//               }
//               if(beginGame) {
//                   getActivity().runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                          Vol3Chiisanasekai.this.mScene.clearUpdateHandlers();
//							Log.i(TAG, "START GAMEEEEEEEEEEEEEEEEEEEEEEEEE");
//							timerSoundDelay(OGG_A78_KOBITO_VO, 0.5f);
//                          actionVictory();
//							visibleHand();
//							initializationGame();
//							mScene.sortChildren();
//							displayFace();
//                       }
//                   });
//               }
//           }
//       });
		
		  this.mScene.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}
				@Override
				public void onUpdate(float arg0) {
					if(beginGame){
						Vol3Chiisanasekai.this.mScene.clearUpdateHandlers();
						timerSoundDelay(OGG_A78_KOBITO_VO, 0.5f);
						actionVictory();
						visibleHand();
						initializationGame();
						mScene.sortChildren();
						displayFace();
			        }
				}
			});
		super.onResumeGame();
	}

	/** PAUSE GAME */
	@Override
	public synchronized void onPauseGame() {
		victoryVisible(false);
		mp3Stop();
		resetALL();
		super.onPauseGame();
	}
	
	// ===========================================================
	// GETTER & SETTER
	// ===========================================================

	// ===========================================================
	// METHODS FOR/ FROM SUPERCLASS / INTERFACES
	// ===========================================================
	
	/** ON TOUCH SCENE */
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		// Action Down
		if(pSceneTouchEvent.isActionDown()){
			lastX = pX;
			lastY = pY;
			
			// Touch Soldier
			if(checkContains(animsprSoldier, 9, 9, 80, 130, pX, pY) 
					&& isTouchSoldier) {
				isTouchSoldier = false;
				touchSoldier();
			}
		}
		
		// Action Move
		if(pSceneTouchEvent.isActionMove()){
			
		}
		
		// Action Up
		if(pSceneTouchEvent.isActionUp()){
			
		}
		return false;
	}
	
	// ===========================================================
	// METHODS
	// ===========================================================
	/**
	 * ACTION WHEN AFTER VICTORY
	 * */
	private void actionVictory(){
		animspr_BGVictory.animate(new long [] {700,700} , 0, 1, -1);
		
		for (int i = 0; i < sprRotate.length; i++) {
			sprRotate[i].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.3f, 0, 12, sprRotate[i].getWidth()/2,  sprRotate[i].getHeight()),
				new RotationAtModifier(0.6f, 12, -12, sprRotate[i].getWidth()/2,  sprRotate[i].getHeight()),
				new RotationAtModifier(0.3f, -12, 0, sprRotate[i].getWidth()/2,  sprRotate[i].getHeight())
				), -1));
		}
		
		for (int i = 0; i < sprBaloon.length; i++) {
			sprBaloon[i].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.5f, sprBaloon[i].getY(), sprBaloon[i].getY() - 30),
				new MoveYModifier(0.5f, sprBaloon[i].getY() - 30, sprBaloon[i].getY())
				), -1));
		}
		//10 270
		sprMove[1].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
			new MoveModifier(0.5f, sprMove[1].getX(), 10 , sprMove[1].getY(), 270),
			new MoveModifier(0.5f, 10 , sprMove[1].getX(), 270 , sprMove[1].getY())
			), -1));
		
		// 753 271
		sprMove[0].registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
			new MoveModifier(0.5f, sprMove[0].getX(), 753 , sprMove[0].getY(), 271),
			new MoveModifier(0.5f, 753 , sprMove[0].getX(), 271 , sprMove[0].getY())
			), -1));
	}
	
	/**
	 * MSCENE VICTORY VISIBLE
	 * */
	private void victoryVisible(boolean value){
		animspr_BGVictory.setVisible(value);
		spr_BGHand.setVisible(value);
		for (int i = 0; i < sprRotate.length; i++) {
			sprRotate[i].setVisible(value);
		}
		for (int i = 0; i < sprBaloon.length; i++) {
			sprBaloon[i].setVisible(value);
		}
		for (int i = 0; i < sprMove.length; i++) {
			sprMove[i].setVisible(value);
		}
	}
	
	/**
	 * INITIALIZATION GAME
	 * */
	private void initializationGame(){
		seGirl_DOWN = 0;
		seGirl_MOVE = 0;
		finish_COUNT = 0;
		booleanTrueFalse();
	}
	
	/**
	 * BOOLEAN AFTER START GAME
	 * */
	private void booleanTrueFalse(){
		isTouchBOY = true;
		isMoveBOY = false;
		isMp3Boy = false;
		
		isTouchGIRL = true;
		isMoveGIRL = false;
		isMp3Girl = false;
		
		isTouchSoldier = true;
	}
	
	/**
	 * VISIBLE HAND 
	 * */
	private void visibleHand(){
		rdNoRepeat = new RanDomNoRepeat();
		rdNoRepeat.setLenghNoRepeat(0, 15);
		linkedAnimFACE.clear();
		
		for (int i = 0; i < 8; i++) {
			rdPositionHand[i] = rdNoRepeat.getNextIntNoRepeat(true);
		}
		
		for (int i = 0; i < animspr_FACE.length; i++) {
			animspr_FACE[i].setRotation(0);
			animspr_FACE[i].setVisible(false);
			animspr_FACE[i].setZIndex(0);
			
			linkedAnimFACE.add(animspr_FACE[i]);
		}
		
		for (int i = 0; i < sprHand.length; i++) {
			sprHand[i].setRotation(0);
			sprHand[i].setVisible(true);
		}
		
		for (int i = 0; i < sprHand.length; i++) {
			if (rdPositionHand[i] == 0 ){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(0, 0);
				sprHand[i].setRotation(65);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(65);
			} 
			else if (rdPositionHand[i] == 1){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(0, 0);
				sprHand[i].setRotation(80);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(80);
			} 
			else if (rdPositionHand[i] == 6){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(sprHand[i].getWidth()/2, sprHand[i].getHeight()/2);
				sprHand[i].setRotation(180);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(180);
			}
			else if (rdPositionHand[i] == 10){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(sprHand[i].getWidth()/2, sprHand[i].getHeight()/2);
				sprHand[i].setRotation(180);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(180);
			}
			else if (rdPositionHand[i] == 11){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(0, 0);
				sprHand[i].setRotation(-90);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(-90);
			}
			else if (rdPositionHand[i] == 13){
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				sprHand[i].setRotationCenter(0, 0);
				sprHand[i].setRotation(-90);
				
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
				animspr_FACE[i].setRotationCenter(animspr_FACE[i].getWidth()/2, animspr_FACE[i].getHeight()/2);
				animspr_FACE[i].setRotation(-90);
			}
			else {
				sprHand[i].setPosition(arrpPosition_Hand[rdPositionHand[i]][0], arrpPosition_Hand[rdPositionHand[i]][1]);
				animspr_FACE[i].setPosition(arrpPosition_Face[rdPositionHand[i]][0], arrpPosition_Face[rdPositionHand[i]][1]);
			}
		}
	}
	
	/**
	 * BEFORE TOUCH SOLDIER
	 * */
	private void touchSoldier(){
		OGG_A78_2_TOKO.play();
		timerSoundDelay(OGG_A78_2_TOKO, 2.0f);
		timerSoundDelay(OGG_A78_2_TOKO, 4.0f);
		timerSoundDelay(OGG_A78_2_TOKO, 6.0f);
		animsprSoldier.animate(new long[] {250,250} , 0 , 1, -1);
		animsprSoldier.registerEntityModifier(
			new MoveXModifier(5.5f, animsprSoldier.getX(), 0 - animsprSoldier.getWidth(), new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					animsprSoldier.setPosition(960, animsprSoldier.getmYFirst());
					animsprSoldier.registerEntityModifier(
						new MoveXModifier(1.0f, 960, animsprSoldier.getmXFirst(), new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								OGG_A78_2_TOKO.stop();
								animsprSoldier.stopAnimation();
								animsprSoldier.setCurrentTileIndex(0);
								isTouchSoldier = true;
							}
						}
					));
				}
			})
		);
	}
	
	/**
	 * CHECK GAME FINISH STATE
	 * */
	private void checkFinishCount(){
		// if finish game
		if(finish_COUNT == 8) {
			linkedAnimFACE.clear();
			mScene.unregisterUpdateHandler(timerFace);
			
			OGG_A78_2_TOKO.stop();
			animsprSoldier.stopAnimation();
			animsprSoldier.clearEntityModifiers();
			animsprSoldier.setCurrentTileIndex(0);
			
			isTouchBOY = false;
			isTouchGIRL = false;
			isTouchSoldier = false;
			
			mScene.clearUpdateHandlers();
			
			animsprBOY.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					mp3Stop();
					finish_COUNT = 0;
					
					animsprBOY.setVisible(false);
					animsprGIRL.setVisible(false);
					animsprSoldier.setVisible(false);
					
					for (int i = 0; i < animspr_FACE.length; i++) {
						animspr_FACE[i].face_DOWN = false;
						animspr_FACE[i].setVisible(false);
						animspr_FACE[i].setZIndex(0);
					}
					for (int i = 0; i < animsprHouse.length; i++) {
						animsprHouse[i].setCurrentTileIndex(1);
						animsprHouse[i].house_DOWN = false;
					}
					
					mScene.sortChildren();
					
					victoryVisible(true);
					OGG_A78_1_OMEDETO.play();
					spr_BGHand.registerEntityModifier(new DelayModifier(3.5f, new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							OGG_A78_KOBITO_VO.play();
							isTouchBOY = true;
							isTouchGIRL = true;
							isTouchSoldier = true;
							
							animsprBOY.setVisible(true);
							animsprBOY.setPosition(animsprBOY.getmXFirst(), animsprBOY.getmYFirst());
							
							animsprGIRL.setVisible(true);
							animsprGIRL.setPosition(animsprGIRL.getmXFirst(), animsprGIRL.getmYFirst());
							
							animsprSoldier.setVisible(true);
							animsprSoldier.setPosition(animsprSoldier.getmXFirst(), animsprSoldier.getmYFirst());
							
							
							victoryVisible(false);
							
							visibleHand();
							
							for (int i = 0; i < animspr_FACE.length; i++) {
								animspr_FACE[i].face_DOWN = true;
								animspr_FACE[i].face_MOVE = false;
								animspr_FACE[i].face_UP = false;
								animspr_FACE[i].face_NO_HOUSE = true;
								animspr_FACE[i].face_SE_MOVE = false;
								animspr_FACE[i].dont_Coll = true;
								animspr_FACE[i].check = true;
							}
							
							for (int j = 0; j < animsprHouse.length; j++) {
								animsprHouse[j].setCurrentTileIndex(0);
								animsprHouse[j].house_DOWN = true;
							}
							
							displayFace();
						}
					}));
				}
			}));
		}
	}
	
	/** DISPLAY FACE WITH TIMER */
	private void displayFace(){
		mScene.unregisterUpdateHandler(timerFace);
		timerFace = new TimerHandler(4.0f,true ,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				if(linkedAnimFACE.size() != 0){
					Random r = new Random();
					final int display = r.nextInt(linkedAnimFACE.size());
					
					Log.i(TAG," Display =" + display);
					
					linkedAnimFACE.get(display).face_DOWN = false;
					animsprHouse[display].house_DOWN = false;
					
					linkedAnimFACE.get(display).setVisible(true);
					
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							linkedAnimFACE.get(display).registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
								final int index = display;
							    @Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity entity) {
									Log.i(TAG,"index = "+ index + " Display = " + display);
								    ((AnimFace) entity ).setVisible(false);
								    ((AnimFace) entity ).face_DOWN = true;
									animsprHouse[index].house_DOWN = true;
								}
							}));
						}
					});
				}
			}
		});
		
		mScene.registerUpdateHandler(timerFace);
	}
	
	
	/**
	 * STOP MP3
	 * */
	public void mp3Stop(){
		OGG_A78_1_OMEDETO.stop();
		OGG_A78_2_TOKO.stop();
		OGG_A78_3_DON.stop();
		OGG_A78_3_HIE_GIRL.stop();
		OGG_A78_3_WA_GIRL.stop();
		OGG_A78_3_YAMETEYO_GIRL.stop();
		OGG_A78_3_YAMETE_GIRL.stop();
		OGG_A78_3_YAN_GIRL.stop();
		OGG_A78_4_HIE_BOY.stop();
		OGG_A78_4_YAMERO_BOY.stop();
		OGG_A78_4_YAMETE_BOY.stop();
		OGG_A78_5_1_2.stop();
		OGG_A78_5_2_2.stop();
		OGG_A78_5_3_2.stop();
		OGG_A78_5_4_2.stop();
		OGG_A78_5_5_2.stop();
		OGG_A78_5_6_2.stop();
		OGG_A78_5_7_2.stop();
		OGG_A78_5_8_2.stop();
		OGG_A78_5_BATA.stop();
		OGG_A78_5_HYOKO.stop();
		OGG_A78_5_SYU.stop();
		OGG_A78_6_PACHI.stop();
		OGG_A78_6_PINPON.stop();
		OGG_A78_6_TIGAUYO1.stop();
		OGG_A78_6_TIGAUYO2.stop();
		OGG_A78_KOBITO_VO.stop();
	}
	
	/**
	 * RESET GAME
	 * */
	public void resetALL(){
		linkedAnimFACE.clear();
		mScene.unregisterUpdateHandler(timerFace);
		
		// soldier
		if(animsprSoldier != null){
			animsprSoldier.clearEntityModifiers();
			animsprSoldier.stopAnimation();
			animsprSoldier.setCurrentTileIndex(0);
			animsprSoldier.setPosition(animsprSoldier.getmXFirst(), animsprSoldier.getmYFirst());
		}
		
		// boy
		if(animsprBOY != null){
			animsprBOY.clearEntityModifiers();
			animsprBOY.stopAnimation();
			animsprBOY.setCurrentTileIndex(0);
			animsprBOY.setPosition(animsprBOY.getmXFirst(), animsprBOY.getmYFirst());
		}
		
		// girl
		if(animsprGIRL != null){
			animsprGIRL.clearEntityModifiers();
			animsprGIRL.stopAnimation();
			animsprGIRL.setCurrentTileIndex(0);
			animsprGIRL.setPosition(animsprGIRL.getmXFirst(), animsprGIRL.getmYFirst());
		}
		
		// VICTORY
		// back gound victory
		if(animspr_BGVictory != null ){
			animspr_BGVictory.stopAnimation();
		}
		
		// background hand
		if(spr_BGHand != null){
			spr_BGHand.clearEntityModifiers();
		}
		
		// sprite rotate
		for (int i = 0; i < sprRotate.length; i++) {
			if(sprRotate[i] != null){
				sprRotate[i].clearEntityModifiers();
				sprRotate[i].setRotation(0);
			}
		}
		
		// baloon
		for (int i = 0; i < sprBaloon.length; i++) {
			if(sprBaloon[i] != null){
				sprBaloon[i].clearEntityModifiers();
			}
		}
		
		// sprite move
		for (int i = 0; i < sprMove.length; i++) {
			if(sprMove[i] != null){
				sprMove[i].clearEntityModifiers();
			}
		}
		
		// ITEM
		// hand
		for (int i = 0; i < sprHand.length; i++) {
			if(sprHand[i] != null){
				sprHand[i].clearEntityModifiers();
				sprHand[i].setRotation(0);
				sprHand[i].setVisible(true);
			}
		}
		
		// face
		for (int i = 0; i < animspr_FACE.length; i++) {
			if(animspr_FACE[i] != null){
				animspr_FACE[i].clearEntityModifiers();
				animspr_FACE[i].stopAnimation();
				animspr_FACE[i].setVisible(false);
				
				animspr_FACE[i].face_DOWN = true;
				animspr_FACE[i].face_MOVE = false;
				animspr_FACE[i].face_UP = false;
				animspr_FACE[i].face_NO_HOUSE = true;
				animspr_FACE[i].face_SE_MOVE = false;
				animspr_FACE[i].dont_Coll = true;
				animspr_FACE[i].check = true;
			}
		}
		
		// face move
		for (int i = 0; i < animspr_FACE_MOVE.length; i++) {
			if(animspr_FACE_MOVE[i] != null){
				animspr_FACE_MOVE[i].clearEntityModifiers();
				animspr_FACE_MOVE[i].stopAnimation();
				animspr_FACE_MOVE[i].setVisible(false);
			}
		}
		
		// house
		for (int i = 0; i < animsprHouse.length; i++) {
			if(animsprHouse[i] != null){
				animsprHouse[i].clearEntityModifiers();
				animsprHouse[i].setCurrentTileIndex(0);
			}
		}
		
		mScene.clearUpdateHandlers();
	}
		
	// ===========================================================
	// INNER AND ANONYMOUS CLASSES
	// ===========================================================
	/**
	 * @author HiepNT
	 * @return CLASS ANIMATEDSPRITE FACE
	 */
	private class AnimFace extends AnimatedSprite{
		private int fIndex;
		private boolean face_DOWN = true;
		private boolean face_MOVE = false;
		private boolean face_UP = false;
		private boolean face_NO_HOUSE = true;

		private boolean face_SE_MOVE = false;
		private boolean dont_Coll = true;
		private boolean check = true;
		
		private AnimFace thisFace;
		private Vol3Chiisanasekai classScene = Vol3Chiisanasekai.this;
		
		int animfaceX;
		int animfaceY;
		
		public AnimFace(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager,
				int fIndex) {
			super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
			
			this.thisFace = this;
			this.fIndex = fIndex;
			
		}
		
		float rotate = 0;
		
		@Override
		public boolean onAreaTouched(TouchEvent pTouchAreEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			int faceX = (int) pTouchAreEvent.getX();
			int faceY = (int) pTouchAreEvent.getY();
			
			switch (pTouchAreEvent.getAction()) {
			case TouchEvent.ACTION_DOWN :
				if(face_DOWN){
					check = false;
					face_DOWN = false;
					
					timerSoundDelay(OGG_NAME_COLOR[thisFace.fIndex], 0.3f);
					
					rotate = thisFace.getRotation();

					animfaceX = (int) pTouchAreEvent.getX();
					animfaceY = (int) pTouchAreEvent.getY();
					
					classScene.mScene.unregisterUpdateHandler(timerFace);
					thisFace.clearEntityModifiers();
					
					// if this Face visible = false
					if(!thisFace.isVisible()){
						OGG_A78_5_HYOKO.play();
						thisFace.setVisible(true);
						linkedAnimFACE.remove(this);
					}
					// if this Face visible = true
					else {
						// if don't face in house
						if(face_NO_HOUSE){
							face_MOVE = true;
						} 
						// if face in house
						else {
							thisFace.registerEntityModifier(new SequenceEntityModifier(
								new ScaleAtModifier(0.4f, 1.0f, 1.4f, 31, 78),
								new ScaleAtModifier(0.4f, 1.4f, 1.0f, 31, 78 ,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
											face_DOWN = true;
										}
									})));
						}
					}
					return true;
				}
				break;
			
			case TouchEvent.ACTION_MOVE :
				if(face_MOVE 
						&& !face_DOWN 
						&& !check 
						&& Math.abs(((int)pTouchAreEvent.getX() + (int) pTouchAreEvent.getY()) - (animfaceX + animfaceY)) > 10) {
					face_SE_MOVE = true;
					face_UP = true;
					
					animspr_FACE_MOVE[thisFace.fIndex].setZIndex(11);
					classScene.mScene.sortChildren();
					
					thisFace.setVisible(false);
					thisFace.setPosition(faceX - thisFace.getWidth()/2 -25, faceY - thisFace.getHeight()/2 +5);
					sprHand[thisFace.fIndex].setVisible(false);
					
					animspr_FACE_MOVE[thisFace.fIndex].setVisible(true);
					
					if(!animspr_FACE_MOVE[thisFace.fIndex].isAnimationRunning()){
						animspr_FACE_MOVE[thisFace.fIndex].animate(new long[] {400,400}, 0 , 1 , -1);
					}
					animspr_FACE_MOVE[thisFace.fIndex].setPosition(faceX - thisFace.getWidth()/2 - 70 , faceY - thisFace.getHeight()/2);
					
				}
				break;
			
			case TouchEvent.ACTION_UP :
				// face move
				if(face_MOVE 
						&& !face_DOWN 
						&& !check 
						&& face_UP) {
					face_MOVE = false;
					face_UP = false;
					// check face collidesWith house
					for (int i = 0; i < animsprHouse.length; i++) {
						// collidesWith house
						if(checkContainsPolygon(animsprHouse[i], arrPointtHouse, 5, faceX, faceY) &&
								animsprHouse[i].getCurrentTileIndex() == 0){
							dont_Coll = false;
							if(thisFace.fIndex == animsprHouse[i].hIndex){
								// collidesWith true house
								face_NO_HOUSE = false;
								face_NO_HOUSE = false;
								OGG_A78_6_PACHI.play();
								timerSoundDelay(OGG_A78_6_PINPON, 0.2f);
								
								thisFace.setRotation(0);
								thisFace.setVisible(true);
								thisFace.setPosition(house_TRUE[fIndex], 554);
								
								thisFace.setZIndex(5);
								animspr_FACE_MOVE[thisFace.fIndex].setZIndex(5);
								classScene.mScene.sortChildren();
								
								animspr_FACE_MOVE[thisFace.fIndex].setVisible(false);
								animspr_FACE_MOVE[thisFace.fIndex].stopAnimation();
								
								animsprHouse[thisFace.fIndex].setCurrentTileIndex(1);
								
								thisFace.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										face_DOWN = true;
										displayFace();
									}
								}));
								
								finish_COUNT++;
								checkFinishCount();
								
								break;
							} else {
								// collidesWith false house
								face_NO_HOUSE = false;
								Random rSe = new Random();
								int rsSe = rSe.nextInt(2);
								OGG_HOUSE_FAIL[rsSe].play();
								
								thisFace.setRotation(0);
								thisFace.setVisible(true);
								thisFace.setPosition(house_TRUE[i], 554);
								
								thisFace.setZIndex(5);
								animspr_FACE_MOVE[thisFace.fIndex].setZIndex(5);
								classScene.mScene.sortChildren();
								
								animspr_FACE_MOVE[thisFace.fIndex].setVisible(false);
								animspr_FACE_MOVE[thisFace.fIndex].stopAnimation();
								
								thisFace.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										dont_Coll = true;
										CaseElse();
									}
								}));	
							}
						}
					}
					
					// don't collidesWith house
					if(dont_Coll) {
						CaseElse();
					}
				}

				// face don't move
				else {
					if(face_NO_HOUSE 
							&& !check) {
						check = true;
						thisFace.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								face_DOWN = true;
								displayFace();
							}
						}));
					}
				}
				break;
			}
			return false;
		}
		
		/** ELSE */
		private void CaseElse(){
			face_DOWN = true;
			face_NO_HOUSE = true;

			displayFace();
			
			if(face_SE_MOVE){
				face_SE_MOVE = false;
				OGG_A78_5_SYU.play();
			}
			
			thisFace.setZIndex(0);
			classScene.mScene.sortChildren();
			
			sprHand[thisFace.fIndex].setVisible(true);
			
			thisFace.setVisible(true);
			thisFace.setRotation(rotate);
			thisFace.setPosition(arrpPosition_Face[rdPositionHand[thisFace.fIndex]][0],
					arrpPosition_Face[rdPositionHand[thisFace.fIndex]][1]);
			
			animspr_FACE_MOVE[thisFace.fIndex].setVisible(false);
			animspr_FACE_MOVE[thisFace.fIndex].stopAnimation();
		}
	}
	
	/**
	 * @author HiepNT
	 * @return CLASS ANIMATEDSPRITE FACE
	 */
	private class AnimHouse extends AnimatedSprite{
		private int hIndex;
		private AnimHouse thisHouse;
		
		private boolean house_DOWN = true;
		
		public AnimHouse(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager,
				int hIndex) {
			super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
			
			this.thisHouse = this;
			this.hIndex = hIndex;
			
		}
		
		@Override
		public boolean onAreaTouched(TouchEvent pTouchAreEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			switch (pTouchAreEvent.getAction()) {
			case TouchEvent.ACTION_DOWN :
				// if thishouse not contains face
				if(thisHouse.getCurrentTileIndex() == 0 
					&& house_DOWN) {
					house_DOWN = false;
					OGG_NAME_COLOR[thisHouse.hIndex].play();
					
					mScene.unregisterUpdateHandler(timerFace);
					animspr_FACE[thisHouse.hIndex].clearEntityModifiers();
					
					thisHouse.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(0.05f, thisHouse.getmXFirst(), thisHouse.getmXFirst() - 3 ),
						new MoveXModifier(0.1f, thisHouse.getmXFirst() - 3, thisHouse.getmXFirst() + 3 ),
						new MoveXModifier(0.05f, thisHouse.getmXFirst() + 3, thisHouse.getmXFirst())),3));
					
					// if face house don't visible
					if(!animspr_FACE[thisHouse.hIndex].isVisible()){
						animspr_FACE[thisHouse.hIndex].setVisible(true);
						animspr_FACE[thisHouse.hIndex].face_DOWN = false;
						
						thisHouse.registerEntityModifier(new DelayModifier(1.3f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animspr_FACE[thisHouse.hIndex].setVisible(false);
								animspr_FACE[thisHouse.hIndex].face_DOWN = true;
								house_DOWN = true;
								displayFace();
							}
						}));
					} else {
						// if face house visible
						animspr_FACE[thisHouse.hIndex].face_DOWN = false;
						
						animspr_FACE[thisHouse.hIndex].registerEntityModifier(new SequenceEntityModifier(
							new ScaleAtModifier(0.4f, 1.0f, 1.4f, 31, 78),
							new ScaleAtModifier(0.4f, 1.4f, 1.0f, 31, 78),
							new DelayModifier(0.5f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									animspr_FACE[thisHouse.hIndex].face_DOWN = true;
									house_DOWN = true;
									displayFace();
								}
							})));
					}
				}
				break;
			
			case TouchEvent.ACTION_MOVE :
				break;
			
			case TouchEvent.ACTION_UP :
				break;
			}
			return false;
		}
	}
	
	//
	
	private void timerSoundDelay(final Sound pSound, final float timeDelay){
		mScene.registerUpdateHandler(new TimerHandler(timeDelay, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				pSound.play();
			}
		}));
	}
}
