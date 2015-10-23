package jp.co.xing.utaehon03.songs;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3PonyoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Ponyo extends BaseGameActivity implements
	IOnSceneTouchListener, IModifierListener<IEntity>, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int CAMERA_WIDTH = 960;
	private static final int CAMERA_HEIGHT = 640;
	private static final String TAG = "LOG_PONYO";

	// ===========================================================
	// Fields Love
	// ===========================================================

	/**
	 * Texture, TextureRegion, TiledTextureRegion, Sprite, AnimatedSprite
	 */
	private BitmapTextureAtlas tttrLayerAwa, ttrLayerSwimer;

	private TextureRegion ttrRLeftIva, ttrRRightIva, ttrRShip, ttrRSmoke, arrTtrRLeft[] = new TextureRegion[28],
		arrTtrRright[] = new TextureRegion[36],
		ttrRLayerAwa, ttrRlayerSwimer, ttrRSlide1, ttrRSlide2, ttrRSlide3, ttrRSlide4, ttrRSlide5, ttrRSlide6,
		ttrKumo1, ttrKumo2, ttrKumo3, ttrKumo4;

	private TiledTextureRegion ttrRBackground, ttrRManPack, arrTtrRSwimer[] = new TiledTextureRegion[15],arrTtrRAwa,
		
		ttrRRedTako, ttrROrangeTako, ttrRGreenBeda, ttrRRedBeda, ttrRKame, ttrRGreenFish, ttrRRedFish, ttrRGreenSmallFish,
		ttrROrangeSmallFish, ttrRManbo, ttrRHakofugu, ttrRAobudai, ttrROtoshigo, ttrRKumanomi;
	
	private AnimatedSprite anmSprBackground, anmSprManPack, arrAnmSprSwimer[] = new AnimatedSprite[15],
		arrSprAwaBall[] = new AnimatedSprite[14];
	private AwaAnimatedSprite arrSprAwa[] = new AwaAnimatedSprite[14];
	private Sprite sprLeftIva, sprRightIva, sprShip, sprSmoke, arrSprLeft[] = new Sprite[28], arrSprRight[] = new Sprite[36],
		sprLayerAwa, sprLayerSwimer, sprKumo1, sprKumo2, sprKumo3, sprKumo4;
	private int indexSwimer, indexAwa, indexAwaLeft, indexAwaRight, disPlayLeftRight;
	private SlideSpritePool slidePool1, slidePool2, slidePool3, slidePool4, slidePool5, slidePool6;
	private boolean isDisplaySwimer, isRecycleAwa = false;
	private Random rdAwa = new Random();
	private SequenceEntityModifier smokeModifier;
	private int arrAwa[] = {-1, -1,-1, -1,-1, -1,-1, -1,-1, -1,-1, -1,-1, -1};
	private float arrPoiterSwimer[][] = 
		{
			{445, 469, 426, 331, 458, 278, 510, 350, 429, 395, 402, 307, 414, 445, 331},
			{179, 273, 316, 371, 345, 205, 234, 392, 371, 259, 247, 206, 209, 253, 234}
		};
	
	private int arrPointerLeft[][] = 
	{
			{-29, 228, 293, 194, 355, 340, -14, 145, 153, 185, 33, 21, 237, 83, 287,
				83, 287, 222, 198, 139, 61, 7, 51, 108, 59, 251, 11, 240, 62, -25},
			{563, 586, 618, 621, 620, 611, 539, 602, 543, 582, 569, 513, 612, 523,
					612, 523, 570, 532, 545, 517, 551, 562, 597, 540, 581, 557, 610, 509, 462, 458}
	};
	
	private int arrPointerRight[][] = 
	{
			{651, 723, 636, 736, 591, 618, 949, 777, 763, 759, 913, 891, 684, 865, 634, 736, 756,
				800, 870, 944, 875, 833, 692, 937, 827, 916, 875, 815, 781, 846, 751, 718, 667, 629, 689, 725},
			{530, 586, 618, 621, 619, 611, 543, 600, 542, 583, 570, 514, 612, 525, 570, 534, 545, 
				515, 551, 563, 600, 582, 558, 609, 455, 463, 448, 461, 451, 453, 477, 480, 518, 536, 497,475 }
	};
	

	
	//==============================================

	private float arrPointerTouchAwaLeft[][] = 
	{
			{
				25, 27, 125, 269, 459, 25
			},
			{
				185, 84, 62, 79, 185, 185
			}
	};
	
	private float arrPointerTouchAwaRight[][] = 
	{
			{
				232, 100, 115, 141, 179, 288, 423, 423, 32
			},
			{
				192, 110, 110, 124, 70, 36, 52, 192, 192
			}
	};
	
	private boolean arrIsSwimerRightToLeft[] = 
	{
			true, true, true, true, true,
			true, true, true, true, true,
			true, true, true, true, true
	};
	
	
	private boolean arrIsSwimerTouched[] = 
	{
			false, false, false, false, false,
			false, false, false, false, false,
			false, false, false, false, false,
	};
	
	/**
	 * Timer Handler
	 */
	private TimerHandler timerHandlerShip, timerHandlerMan;

	
	/**
	 * Modifier
	 */

	/**
	 * All Sound
	 */
	

	private Sound  A2_4_AWABOKO2;
	private Sound  A2_4_KIRA3;
	private Sound  A2_4_SAKANA;
	private Sound  A2_5_AWABOKO1;
	private Sound  A2_BO1;
	private Sound  A2_BO1_5;
	private Sound  A2_BOCYA_BUKU;
	private Sound  A2_BOMI2;
	private Sound  A2_BUJU3;
	private Sound  A2_HAGESHIAWA;
	private Sound  A2_KIRA4;
	
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TexturePack mBackgroundTexturePack;
    private TexturePack mItemTexturePack;
    
    private TexturePackTextureRegionLibrary mItemexturePackTextureRegionLibrary;
	@Override
	public void onCreateResources() {
		 SoundFactory.setAssetBasePath("ponyo/mfx/");
         BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("ponyo/gfx/");
         mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets());
         Log.d(TAG, "___________________COME HERE_________________");
         super.onCreateResources();
	}

	@Override
	public void loadKaraokeResources() {
		

			mBackgroundTexturePack = mTexturePackLoaderFromSource.load("background.xml");
			mBackgroundTexturePack.loadTexture();
			
			mItemTexturePack = mTexturePackLoaderFromSource.load("items.xml"); 
			mItemTexturePack.loadTexture();
			mItemexturePackTextureRegionLibrary  = mItemTexturePack.getTexturePackTextureRegionLibrary();


		// //////////////Load Background ////////////////////
		this.ttrRBackground = getTiledTextureFromPacker(mBackgroundTexturePack,
				Vol3PonyoResource.A2_23_1_IPHONE_HAIKEI_ID,
				Vol3PonyoResource.A2_23_2_IPHONE_HAIKEI_ID
		);
		
		this.ttrRLeftIva = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_1_29_IPHONE_LEFT_IWA_ID);
		
		this.ttrRRightIva = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_2_37_IPHONE_RIGHT_IWA_ID);
		
		this.ttrRShip = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_3_1_IPHONE_SHIP_ID);
		// kumo1
		this.ttrKumo1 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_22_1_IPHONE_KUMO_ID);
		// kumo2
		this.ttrKumo2 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_22_2_IPHONE_KUMO_ID);
		// kumo3
		this.ttrKumo3 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_22_3_IPHONE_KUMO_ID);
		// kumo4
		this.ttrKumo4 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_22_4_IPHONE_KUMO_ID);
		// Load Smoke
		this.ttrRSmoke = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_3_2_IPHONE_SMOKE_ID);
		
		this.ttrRManPack = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_3_3_IPHONE_MAN_ID,
				Vol3PonyoResource.A2_3_4_IPHONE_MAN_ID		
		);
		
		// Load resource Swimer
		for (int i = 0; i < 15; i++)
		{
			this.arrTtrRSwimer[i] = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.ARR_RESOURCE_SWIMER[i * 2],
					Vol3PonyoResource.ARR_RESOURCE_SWIMER[i * 2 + 1]		
			);
		}
		// Load Resource Left
		for (int i = 0; i < 28; i++)
		{
			arrTtrRLeft[i] = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.ARR_RESOURCE_LEFT[i]);
		}
		
		// Load Resource Right
		for (int i = 0; i < 36; i++)
		{
			arrTtrRright[i] = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.ARR_RESOURCE_RIGHT[i]);
		}

		// Load Marinelife
		this.ttrRRedTako = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_8_1_1_IPHONE_TAKO_ID,
				Vol3PonyoResource.A2_8_1_2_IPHONE_TAKO_ID
				
		);
		
		this.ttrROrangeTako = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_8_2_1_IPHONE_TAKO_ID,
				Vol3PonyoResource.A2_8_2_2_IPHONE_TAKO_ID
		);
		
		this.ttrRGreenBeda = getTiledTextureFromPacker(mItemTexturePack, 
				Vol3PonyoResource.A2_9_1_1_IPHONE_BEDA_ID,
				Vol3PonyoResource.A2_9_1_2_IPHONE_BEDA_ID);
		
		this.ttrRRedBeda = getTiledTextureFromPacker(mItemTexturePack, 
				Vol3PonyoResource.A2_9_2_1_IPHONE_BEDA_ID,
				Vol3PonyoResource.A2_9_2_2_IPHONE_BEDA_ID		
		);
		
		this.ttrRKame = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_10_1_IPHONE_KAME_ID,
				Vol3PonyoResource.A2_10_2_IPHONE_KAME_ID
		);	
		
		this.ttrRGreenFish = getTiledTextureFromPacker(mItemTexturePack, 
				Vol3PonyoResource.A2_11_IPHONE_FISH_ID
		);
		this.ttrRRedFish = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_12_IPHONE_FISH_ID);
		
		this.ttrRGreenSmallFish = getTiledTextureFromPacker(mItemTexturePack, 
				Vol3PonyoResource.A2_13_1_IPHONE_FISH_ID);
		this.ttrROrangeSmallFish = getTiledTextureFromPacker(mItemTexturePack,Vol3PonyoResource.A2_13_2_IPHONE_FISH_ID);
		
		this.ttrRManbo = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.A2_14_IPHONE_MANBO_ID);
		
		this.ttrRHakofugu = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.A2_15_IPHONE_HAKOFUGU_ID);
		
		this.ttrRAobudai = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.A2_16_IPHONE_AOBUDAI_ID);
		
		this.ttrROtoshigo = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.A2_6_IPHONE_OTOSHIGO_ID);
		
		this.ttrRKumanomi = getTiledTextureFromPacker(mItemTexturePack, Vol3PonyoResource.A2_7_IPHONE_KUMANOMI_ID);
		
		// Load Awa
		
		arrTtrRAwa = getTiledTextureFromPacker(mItemTexturePack,
				Vol3PonyoResource.A2_4_1_IPHONE_AWA_ID,
				Vol3PonyoResource.A2_4_2_IPHONE_AWA_ID,
				Vol3PonyoResource.A2_4_3_IPHONE_AWA_ID
				
		);
		// Load Layers
		 
		tttrLayerAwa = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrRLayerAwa = new TextureRegion(tttrLayerAwa, 0, 0, 960, 640);
		tttrLayerAwa.load();
		
		ttrLayerSwimer = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrRlayerSwimer = new TextureRegion(ttrLayerSwimer, 0, 0, 960, 640);
		ttrLayerSwimer.load();
		ttrRSlide1 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_1_IPHONE_SLIDE_ID);
		ttrRSlide2 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_2_IPHONE_SLIDE_ID);
		ttrRSlide3 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_3_IPHONE_SLIDE_ID);
		ttrRSlide4 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_4_IPHONE_SLIDE_ID);
		ttrRSlide5 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_5_IPHONE_SLIDE_ID);
		ttrRSlide6 = mItemexturePackTextureRegionLibrary.get(Vol3PonyoResource.A2_5_6_IPHONE_SLIDE_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		// Load All Sound
		A2_4_AWABOKO2 = loadSoundResourceFromSD(Vol3PonyoResource.A2_4_AWABOKO2);
		A2_4_KIRA3 = loadSoundResourceFromSD(Vol3PonyoResource.A2_4_KIRA3);
		A2_4_SAKANA = loadSoundResourceFromSD(Vol3PonyoResource.A2_4_SAKANA);
		A2_5_AWABOKO1 = loadSoundResourceFromSD(Vol3PonyoResource.A2_5_AWABOKO1);
		A2_BO1 = loadSoundResourceFromSD(Vol3PonyoResource.A2_BO1);
		A2_BO1_5 = loadSoundResourceFromSD(Vol3PonyoResource.A2_BO1_5);
		A2_BOCYA_BUKU = loadSoundResourceFromSD(Vol3PonyoResource.A2_BOCYA_BUKU);
		A2_BOMI2 = loadSoundResourceFromSD(Vol3PonyoResource.A2_BOMI2);
		A2_BUJU3 = loadSoundResourceFromSD(Vol3PonyoResource.A2_BUJU3);
		A2_HAGESHIAWA = loadSoundResourceFromSD(Vol3PonyoResource.A2_1_CYMBAL);
		A2_KIRA4 = loadSoundResourceFromSD(Vol3PonyoResource.A2_KIRA4);
	}

	@Override
	public void loadKaraokeScene() {
		//this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		
		// Set Backgroud For Scene
		anmSprBackground = new AnimatedSprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprBackground);
		
		sprLayerAwa = new Sprite(0, 0, ttrRLayerAwa, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprLayerAwa);
		sprLayerAwa.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprLayerAwa.setAlpha(0.0f);
		sprLayerSwimer = new Sprite(0, 0, ttrRlayerSwimer, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprLayerSwimer);
		sprLayerSwimer.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprLayerSwimer.setAlpha(0.0f);
		this.sprKumo1 = new Sprite(45, 11, this.ttrKumo1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKumo1);
		this.sprKumo2 = new Sprite(273, 9, this.ttrKumo2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKumo2);
		this.sprKumo3 = new Sprite(418, 8, this.ttrKumo3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKumo3);
		this.sprKumo4 = new Sprite(159, 9, this.ttrKumo4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKumo4);
		// Attach Left Iva
		this.sprLeftIva = new Sprite(-31, 465, this.ttrRLeftIva, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprLeftIva);
		
		// Attach Right Iva
		this.sprRightIva = new Sprite(542, 453, this.ttrRRightIva, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprRightIva);
		
		// Attach Man Pack
		this.anmSprManPack = new AnimatedSprite(530, 3, this.ttrRManPack, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprManPack);
		AnimatedRun(anmSprManPack);
		// Attach Ship
		this.sprShip = new Sprite(530, 3, this.ttrRShip, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprShip);
		
		// Attach Smoke
		this.sprSmoke = new Sprite(534, 3, this.ttrRSmoke, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprSmoke);
		this.sprSmoke.setVisible(false);
		
		// Attach Swimer
		for (int i = 0; i < 15; i++)
		{
			final int index = i;
			this.arrAnmSprSwimer[i] = new AnimatedSprite(0, 0, this.arrTtrRSwimer[i], this.getVertexBufferObjectManager())
			{
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
					{	
						Log.d(TAG, "_____________onAreaTouched______________" + index);
						
						if (indexSwimer >= 15)
						{
							return false;
						}
						
//						if (!Polygon.contains((int)pTouchAreaLocalX, (int)pTouchAreaLocalY, 
//								arrPointerSwimerTouchX, arrPointerSwimerTouchY, 28))
						
						if (!this.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))
						{
							return false;
						}
						if (!this.isVisible())
						{							
							return false;
						}
						if ((arrIsSwimerTouched[index]
								))
						{	
							Log.d(TAG, "TOUCHED SWIMER");
							A2_KIRA4.play();
							arrIsSwimerTouched[index] = true;
							if (arrIsSwimerRightToLeft[index])
							{
								arrIsSwimerRightToLeft[index] = false;
								float duration = (960 - arrAnmSprSwimer[index].getX()) * 8.0f / (960 + arrAnmSprSwimer[index].getWidth());
								arrAnmSprSwimer[index].clearEntityModifiers();
								arrAnmSprSwimer[index].setFlippedHorizontal(true);
								arrAnmSprSwimer[index].registerEntityModifier(
										new SequenceEntityModifier(
												new MoveXModifier(duration, arrAnmSprSwimer[index].getX(), 960),
												new LoopEntityModifier(new MoveXModifier(8.0f, -arrAnmSprSwimer[index].getWidth(), 960)) 
										)
								);
							}
							else
							{
								arrIsSwimerRightToLeft[index] = true;
								float duration = (arrAnmSprSwimer[index].getX() + arrAnmSprSwimer[index].getWidth()) * 8.0f / (960 + arrAnmSprSwimer[index].getWidth());
								arrAnmSprSwimer[index].clearEntityModifiers();
								arrAnmSprSwimer[index].setFlippedHorizontal(false);
								arrAnmSprSwimer[index].registerEntityModifier(new SequenceEntityModifier(
										new SequenceEntityModifier(
												new MoveXModifier(duration, arrAnmSprSwimer[index].getX(), -arrAnmSprSwimer[index].getWidth()),
												new LoopEntityModifier(
														new MoveXModifier(8.0f, 960, -arrAnmSprSwimer[index].getWidth())
												)
										)
								)
								);
							}
						}
						return true;
					}
					return true;
				}
			};			
			this.sprLayerSwimer.attachChild(this.arrAnmSprSwimer[i]);
			this.arrAnmSprSwimer[i].setVisible(false);
			this.mScene.registerTouchArea(this.arrAnmSprSwimer[i]);
			
			slidePool1 = new SlideSpritePool(ttrRSlide1, this.getVertexBufferObjectManager());
			slidePool2 = new SlideSpritePool(ttrRSlide2, this.getVertexBufferObjectManager());
			slidePool3 = new SlideSpritePool(ttrRSlide3, this.getVertexBufferObjectManager());
			slidePool4 = new SlideSpritePool(ttrRSlide4, this.getVertexBufferObjectManager());
			slidePool5 = new SlideSpritePool(ttrRSlide5, this.getVertexBufferObjectManager());
			slidePool6 = new SlideSpritePool(ttrRSlide6, this.getVertexBufferObjectManager());
		}
		
		// Attach Left
		for (int i = 1; i < 27; i++)
		{
			arrSprLeft[i] = new Sprite(arrPointerLeft[0][i], arrPointerLeft[1][i], arrTtrRLeft[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSprLeft[i]);
			arrSprLeft[i].setVisible(false);
		}
		arrSprLeft[0] = new Sprite(arrPointerLeft[0][0], arrPointerLeft[1][0], arrTtrRLeft[0], this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.arrSprLeft[0]);
		arrSprLeft[0].setVisible(false);
		arrSprLeft[27] = new Sprite(arrPointerLeft[0][27], arrPointerLeft[1][27], arrTtrRLeft[27], this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.arrSprLeft[27]);
		arrSprLeft[27].setVisible(false);
		
		// Attach Right
		for (int i = 1; i < 36; i++)
		{
			arrSprRight[i] = new Sprite(arrPointerRight[0][i], arrPointerRight[1][i], arrTtrRright[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSprRight[i]);
			arrSprRight[i].setVisible(false);
		}
		arrSprRight[0] = new Sprite(arrPointerRight[0][0], arrPointerRight[1][0], arrTtrRright[0], this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.arrSprRight[0]);
		arrSprRight[0].setVisible(false);
		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3PonyoResource.SOUND_GIMMIC_PONYO,
				Vol3PonyoResource.IMAGE_GIMMIC_PONYO, mItemexturePackTextureRegionLibrary);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
		
	}

	@Override
	public void onResumeGame() {
		Log.d(TAG,
				"================= onResumeGame ==============="
						+ System.currentTimeMillis());
		indexSwimer = 0;
		indexAwa = 0;
		indexAwaLeft = 0;
		indexAwaRight = 0;
		disPlayLeftRight = 0;
		isDisplaySwimer = true;
		isRecycleAwa = false;
		sprShip.clearEntityModifiers();
		sprShip.setPosition(530, 3);
		sprSmoke.clearEntityModifiers();
		sprSmoke.setPosition(530, 3);
		sprSmoke.setVisible(false);
		anmSprManPack.clearEntityModifiers();
		anmSprManPack.setPosition(530, 3);
		//anmSprManPack.setCurrentTileIndex(0);
		AnimatedRun(anmSprManPack);
		smokeModifier = null;
		if (sprKumo1!=null) {
			sprKumo1.setPosition(45, 11);
		}
		if (sprKumo2!=null) {
			sprKumo2.setPosition(273, 9);
		}
		if (sprKumo3!=null) {
			sprKumo3.setPosition(418, 8);
		}
		if (sprKumo4!=null) {
			sprKumo4.setPosition(159, 9);
		}
		// Loop Change Background
		anmSprBackground.animate(800);
		shipModifier();
		moveKumo();
		scaleShipAfterFiveSeconds();
		this.mEngine.registerUpdateHandler(autodetect);
		
		for (int i = 0; i < 15; i++)
		{
			if (arrAnmSprSwimer[i] != null)
			{
				arrAnmSprSwimer[i].clearEntityModifiers();
				arrAnmSprSwimer[i].setFlippedHorizontal(false);
				arrAnmSprSwimer[i].setVisible(false);
				arrAnmSprSwimer[i].setPosition(0, 0);
				arrIsSwimerTouched[i] = false;
			}
		}
		for (int i = 0; i < 14; i++)
		{
			if (arrSprAwaBall[i] != null)
			{
				arrSprAwaBall[i].clearEntityModifiers();
				arrSprAwaBall[i].setVisible(false);
				arrSprAwaBall[i].setPosition(0, 0);
				
			}
		}
		for (int i = 0; i < 14; i++)
		{
			arrAwa[i] = -1;
		}
		for (int i = 0; i < 14; i++)
		{
			if (arrSprAwa[i] != null)
			{
				arrSprAwa[i].setFlippedHorizontal(false);
				arrSprAwa[i].clearEntityModifiers();
				arrSprAwa[i].setVisible(false);
				arrSprAwa[i].setPosition(0, 0);
				arrSprAwa[i].setFree(true);
			}
		}
		for (int i = 0; i < 27; i++)
		{
			if (arrSprLeft[i] != null)
			{
				arrSprLeft[i].setVisible(false);
			}
		}
		for (int i = 0; i < 35; i++)
		{
			if (arrSprRight[i] != null)
			{
				arrSprRight[i].setVisible(false);
			}
		}
		
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		Log.d(TAG, "================= onPauseGame ===============");
		if (sprKumo1!=null) {
			sprKumo1.clearEntityModifiers();
			sprKumo1.setPosition(45, 11);
			
		}
		if (sprKumo2!=null) {
			sprKumo2.clearEntityModifiers();
			sprKumo2.setPosition(273, 9);
			
		}
		if (sprKumo3!=null) {
			sprKumo3.clearEntityModifiers();
			sprKumo3.setPosition(418, 8);
		}
		if (sprKumo4!=null) {
			sprKumo4.clearEntityModifiers();
			sprKumo4.setPosition(159, 9);
		}
		super.onPauseGame();
		return;
	}
	private void moveKumo() {
		sprKumo1.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(5.0f, sprKumo1.getX(), -132),
				new MoveXModifier(20.0f, 1092, sprKumo1.getX()))));
		
		sprKumo2.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(8.0f, sprKumo2.getX(), -110),
				new MoveXModifier(16.0f, 1092, sprKumo2.getX()))));
		sprKumo3.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(14.0f, sprKumo3.getX(), -290),
				new MoveXModifier(16.0f, 1092, sprKumo3.getX()))));
		sprKumo4.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(10.0f, sprKumo4.getX(), -215),
				new MoveXModifier(20.0f, 1092, sprKumo4.getX()))));
		
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			Log.d(TAG, "__________SCENE TOUCH EVENT__________");
			if (this.sprShip.contains(pX, pY))
			{
				touchShip();
			}
			
			else if (checkContainsPolygon(sprLeftIva, arrPointerTouchAwaLeft, 5, pX, pY))
			{
				displayAwaLeft(false);
				return true;
			}
			else if (checkContainsPolygon(sprRightIva, arrPointerTouchAwaRight, 8, pX, pY))
			{
				displayAwaRight(false);
				return true;
			}
			else if (pY >= 170)
			{
				if (indexSwimer < 15)
				{
					int index = -1;
					if (!isRecycleAwa)
					{
						if (indexAwa < 13)
						{
							index = getRandomAwa();
						}
						if (indexAwa == 13)
						{
							isRecycleAwa = true;
						}
						if (index > -1)
						{
							displayAwa(pX, pY, indexAwa, index);
						}
					}
					
					else
					{
						indexAwa = recyleIndexAwa();
						if (indexAwa > -1)
						{
							displayAwa(pX, pY, indexAwa, arrSprAwa[indexAwa].getIndex());
						}
					}
					
					Log.d(TAG, "_______________Index____________" + index);
					Log.d(TAG, "_______________Index Awa____________" + indexAwa);
					if (indexAwa <= 12)
					{
						indexAwa++;
					}
				}
			}
			return true;
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE && pY >= 170)
		{
			Log.d(TAG, "____________COME HERE____________");
			int rd = new Random().nextInt(6);
			displaySlide(rd, pX, pY);
		}
		
		
		return true;
	}

	@Override
	public void combineGimmic3WithAction() {
		if (disPlayLeftRight == 0)
		{
			disPlayLeftRight = 1;
			displayAwaLeft(false);
		}else
		{
			disPlayLeftRight = 0;
			displayAwaRight(false);
		}
	}

	public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
		
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		
	}
	
	private void shipModifier()
	{
		sprShip.setPosition(530,3);
		anmSprManPack.setPosition(530,3);
		anmSprManPack.animate(800);
		sprShip.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new MoveYModifier(1.0f,anmSprManPack.getY(), anmSprManPack.getY() + 5),
						new MoveYModifier(1.0f,anmSprManPack.getY() + 5, anmSprManPack.getY())
				)	
			));
	}
	
	
	private void scaleShipAfterFiveSeconds()
	{	
		this.mEngine.unregisterUpdateHandler(timerHandlerShip);
		timerHandlerShip = new TimerHandler(5.0f, false,new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				//sprShip.clearEntityModifiers();
				sprShip.setPosition(530, 3);
				anmSprManPack.setPosition(530, 3);
				sprShip.registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(
								new DelayModifier(0.5f),
								new ScaleModifier(0.3f, 1.0f, 1.1f),
								new ScaleModifier(0.3f, 1.2f, 1.1f)
						)
				));
				
			}
		});
		this.mEngine.registerUpdateHandler(timerHandlerShip);
		return;
	}
	
	/**
	 * Ship Move To Left when the Sea has 15 human
	 */
	private void swimerAndShipMoveWhenHasFifty()
	{
		A2_BO1_5.play();
		A2_HAGESHIAWA.play();
		this.sprShip.setScale(1.0f);
		this.sprShip.clearEntityModifiers();
		this.sprShip.registerEntityModifier(
				new SequenceEntityModifier(
						new IEntityModifierListener() {
							
							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								anmSprManPack.setVisible(true);
								sprSmoke.clearEntityModifiers();
								sprSmoke.setVisible(false);
								sprSmoke.setScale(0.5f);
								AnimatedRun(anmSprManPack);
								anmSprManPack.clearEntityModifiers();
								anmSprManPack.setCurrentTileIndex(0);
								indexSwimer = 0;
								for (int i = 0; i < 15; i++)
								{
									arrIsSwimerTouched[i] = false;
								}
								shipModifier();
								scaleShipAfterFiveSeconds();	
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								
								
							}
						},
						new MoveXModifier(9.0f, sprShip.getX(), -sprShip.getWidth()),
						new MoveXModifier(6.0f, 1060, sprShip.getX())
				)
		);
		this.anmSprManPack.setVisible(true);
		AnimatedRun(anmSprManPack);
		this.anmSprManPack.registerEntityModifier(new SequenceEntityModifier(
							new MoveXModifier(9.0f, sprSmoke.getX(), -sprSmoke.getWidth()),
							new MoveXModifier(6.0f, 1060, sprSmoke.getX())
						));
		this.sprSmoke.setVisible(true);
		this.sprSmoke.registerEntityModifier(
				new ParallelEntityModifier(
						new LoopEntityModifier(
								new SequenceEntityModifier(
									new ScaleModifier(1.0f, 0.0f, 1.0f),
									new ScaleModifier(1.0f, 1.0f, 0.0f)
								)
						),
						new SequenceEntityModifier(
							new MoveXModifier(9.0f, sprSmoke.getX(), -sprSmoke.getWidth()),
							new MoveXModifier(6.0f, 1060, sprSmoke.getX())
						)
				)
		);
		for (int i = 0; i < 15; i++)
		{
			
			//if ((arrAnmSprSwimer[i].getX() < 960) && (arrAnmSprSwimer[i].getX() +  arrAnmSprSwimer[i].getWidth()> 0)){
				float duration = (float) (arrAnmSprSwimer[i].getX() + this.arrAnmSprSwimer[i].getWidth()) * 8 /
					(960 + this.arrAnmSprSwimer[i].getWidth());
				this.arrAnmSprSwimer[i].setFlippedHorizontal(false);
				this.arrAnmSprSwimer[i].setScale(1.0f);
				this.arrAnmSprSwimer[i].setRotation(0.0f);
				this.arrAnmSprSwimer[i].clearEntityModifiers();
				this.arrAnmSprSwimer[i].registerEntityModifier(
						new MoveXModifier(duration, arrAnmSprSwimer[i].getX(), -arrAnmSprSwimer[i].getWidth())
				);
			//}
		}
		
		for (int i = 0; i < 14; i++)
		{
			if (arrSprAwa[i] != null)
			{

				this.arrSprAwa[i].setFlippedHorizontal(false);
				this.arrSprAwa[i].clearEntityModifiers();
				float duration = 0;
				if ((arrSprAwa[i].getX() < 960) && (arrSprAwa[i].getX() + arrSprAwa[i].getWidth() > 0))
				{
					
					switch (arrSprAwa[i].getIndex()) {
					case 5:
					case 6:
					case 7:
					case 8:
					case 11:
						duration = (float) (arrSprAwa[i].getX() + arrSprAwa[i].getWidth()) * 8 /
						(960 + arrSprAwa[i].getWidth());	
						break;
					case 12:
						duration = (float) (arrSprAwa[i].getX() + arrSprAwa[i].getWidth()) * 20 /
						(960 + arrSprAwa[i].getWidth());	
						break;
					default:
						duration = (float) (arrSprAwa[i].getX() + arrSprAwa[i].getWidth()) * 12 /
						(960 + arrSprAwa[i].getWidth());
						break;
					}
					switch (arrSprAwa[i].getIndex()) {
					
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 13:
						arrSprAwa[i].registerEntityModifier(
								new ParallelEntityModifier(
									
										new SequenceEntityModifier(
												new DelayModifier(0.2f),
												new MoveXModifier(duration, arrSprAwa[i].getX(), -arrSprAwa[i].getWidth()),
												new MoveXModifier(1.0f, 1060, 961)
								
								)
								
						));
						break;
					case 12:
						arrSprAwa[i].registerEntityModifier(
								new ParallelEntityModifier(
										new LoopEntityModifier(
												new SequenceEntityModifier(
														new MoveYModifier(0.8f, arrSprAwa[i].getY(),arrSprAwa[i].getY() - 15),
														new MoveYModifier(0.8f, arrSprAwa[i].getY() - 15,arrSprAwa[i].getY())
												)
										),
										new LoopEntityModifier(
												new SequenceEntityModifier(
														new RotationModifier(0.8f, 0, 5),
														new RotationModifier(0.8f, 5 ,0)
												)
										),
										new SequenceEntityModifier(
												new DelayModifier(0.2f),
												new MoveXModifier(duration, arrSprAwa[i].getX(), -arrSprAwa[i].getWidth()),
												new MoveXModifier(1.0f, 1060, 961)
									
												
								)
								
								
						));
						break;

					default:
						arrSprAwa[i].registerEntityModifier(new SequenceEntityModifier(
								new MoveXModifier(duration, arrSprAwa[i].getX(), -arrSprAwa[i].getWidth()),
								new MoveXModifier(1.0f, 1060, 961)
							
						));
						break;
					}
					
				}
			}
		}
		return;
	}
	
	/**
	 * Touch Ship
	 */
	private void touchShip()
	{
		Log.d(TAG, "Index Swimer:" + indexSwimer);
		if (smokeModifier == null || smokeModifier.isFinished() && indexSwimer < 15)
		{
			this.sprShip.clearEntityModifiers();
			shipModifier();
			this.mEngine.unregisterUpdateHandler(timerHandlerShip);
			if (indexSwimer < 15)
			{
				this.sprShip.setScale(1.0f);
				displaySmoke(false);
				if (isDisplaySwimer)
				{
					
					A2_BOCYA_BUKU.play();
					anmSprManPack.animate(200, 0, new IAnimationListener() {
						
						@Override
						public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
							if (isDisplaySwimer)
							{
								// Call Display Swimer
								isDisplaySwimer = false;
								displaySwimer(indexSwimer ++);
								//anmSprManPack.stopAnimation();
								anmSprManPack.setCurrentTileIndex(0);
								anmSprManPack.setVisible(false);
								
								mEngine.unregisterUpdateHandler(timerHandlerMan);
								mEngine.registerUpdateHandler(timerHandlerMan = new TimerHandler(0.8f, false, new ITimerCallback() {
									
									@Override
									public void onTimePassed(TimerHandler pTimerHandler) {
										anmSprManPack.setVisible(true);
										anmSprManPack.animate(800);
										AnimatedRun(anmSprManPack);
									}
								}));
							}	
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {
							
							
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {
							
							
						}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {
							
							
						}
					});
				}
				else
				{
					// Display Smoke
					A2_BO1.play();
					displaySmoke(true);
				}
		}
		}
		return;
	}
	private boolean check(int index)
	{
		if (indexAwa < 13)
		{
			for (int i = 0; i < 14; i++)
			{
				if (arrAwa[i] == index)
				{
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	private int getRandomAwa()
	{
		
		int index = rdAwa.nextInt(14);
		if (indexAwa < 13)
		{
			if (check(index))
			{
				arrAwa[indexAwa] = index;
				return index;
			}
			else
			{
				return getRandomAwa();
			}
			
		}

		
		return 0;
		
	}
	
	private int recyleIndexAwa()
	{
		
			Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			for (int i = 0; i < 14; i++)
			{
				if (arrSprAwa[i] != null){
					final int indexTemp = i;
					if (arrSprAwa[i].isFree())
					{
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								if(arrSprAwa[indexTemp] != null){
									arrSprAwa[indexTemp].clearEntityModifiers();
									arrSprAwa[indexTemp].detachSelf();
									arrSprAwa[indexTemp] = null;
								}
							}
						});
						return i;
					}
				}
			}
			return -1;
	}
	
	private void displaySlide(int index, float pX, float pY)
	{
		A2_5_AWABOKO1.play();
		Sprite slide = null;
		switch (index) {
		case 0:
			slide = slidePool1.obtainPoolItem();
			break;
		case 1:
			slide = slidePool2.obtainPoolItem();
			break;
		case 2:
			slide = slidePool3.obtainPoolItem();
			break;
		case 3:
			slide = slidePool4.obtainPoolItem();
			break;
		case 4:
			slide = slidePool5.obtainPoolItem();
			break;
		case 5:
			slide = slidePool6.obtainPoolItem();
			break;
		default:
			break;
		}
		if(slide == null){
			return;
		}
		slide.setAlpha(1.0f);
		slide.setPosition(pX, pY);
		slide.setVisible(true);
		sprLayerAwa.attachChild(slide);
		registerModifier(slide, index);
	}
	private void registerModifier(final Sprite slide, final int index)
	{
		float duration = 0;
		
		switch(index)
		{
			case 0:
				duration = 1.2f; 
				break;
			case 1:
				duration = 1.3f; 
				break;
			case 2:
				duration = 1.6f; 
				break;
			case 3:
				duration = 1.8f; 
				break;
			case 4:
				duration = 2.0f; 
				break;
			case 5:
				duration = 2.2f; 
				break;
		}
		slide.registerEntityModifier(new ParallelEntityModifier(
				new IEntityModifierListener() {
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								switch (index) {
								case 0:
									slidePool1.recyclePoolItem(slide);
									
									break;
								case 1:
									slidePool2.recyclePoolItem(slide);
									break;
								case 2:
									slidePool3.recyclePoolItem(slide);
									break;
								case 3:
									slidePool4.recyclePoolItem(slide);
									break;
								case 4:
									slidePool5.recyclePoolItem(slide);
									break;
								case 5:
									slidePool6.recyclePoolItem(slide);
									break;
								default:
									break;
								}
								
							}
						});
					
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						
						
					}
				},
				new MoveYModifier(duration, slide.getY(), 150),
				new AlphaModifier(duration, 1.0f, 0.0f)
		));
	}
	
	/**
	 * Display Swimer and Move It.
	 * @param index
	 */
	private void displaySwimer(final int index)
	{	
		Log.d(TAG, "_____________Come Here____________" + indexSwimer);		
		this.arrAnmSprSwimer[index].setPosition(440, 83);		
		float duration = ((float) Math.sqrt((arrPoiterSwimer[1][index] - this.arrAnmSprSwimer[index].getY()) * (arrPoiterSwimer[1][index] - this.arrAnmSprSwimer[index].getY()) +
				(arrPoiterSwimer[0][index] - this.arrAnmSprSwimer[index].getX()) * (arrPoiterSwimer[0][index] - this.arrAnmSprSwimer[index].getX())
				)) / 200;
		Log.d(TAG, "Duration:" + duration);
		float duration2 =(float) (arrPoiterSwimer[0][index] + this.arrAnmSprSwimer[index].getWidth()) * 8 / (960 + this.arrAnmSprSwimer[index].getWidth());
		this.arrAnmSprSwimer[index].animate(400);
		this.arrAnmSprSwimer[index].setScale(0.1f);
		this.arrAnmSprSwimer[index].setVisible(true);
		this.arrAnmSprSwimer[index].registerEntityModifier(
				new SequenceEntityModifier(
					new IEntityModifierListener() {
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							
							arrAnmSprSwimer[index].registerEntityModifier(
									new LoopEntityModifier(new MoveXModifier(8.0f, 960, -arrAnmSprSwimer[index].getWidth()))
							);
							if (indexSwimer == 15)
							{
								
								return;
							}
							scaleShipAfterFiveSeconds();
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							
							
						}
					}
					,
					new ParallelEntityModifier(
						new ScaleModifier(duration, 0.1f, 1.0f),
						new RotationModifier(duration, -90, 0),
						new MoveModifier(duration, this.arrAnmSprSwimer[index].getX(), arrPoiterSwimer[0][index],
								this.arrAnmSprSwimer[index].getY(), arrPoiterSwimer[1][index])
						)
					,
					new MoveXModifier(duration2 ,arrPoiterSwimer[0][index], -this.arrAnmSprSwimer[index].getWidth())
					)
				);
		return;
	}
	
	/**
	 * Display Smoke 
	 */
	private void displaySmoke(final boolean isShow)
	{
		float duration = 0.0f;
		if (isShow)
		{
			isDisplaySwimer = false;
			duration = 1.0f;
		}
		else
		{
			duration = 1.0f;
		}
		this.sprSmoke.setVisible(isShow);
		this.sprSmoke.setScale(0.5f);
		this.sprSmoke.registerEntityModifier(smokeModifier = new SequenceEntityModifier(
				new IEntityModifierListener() {
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						if (isShow)
						{
							sprSmoke.setVisible(false);
							isDisplaySwimer = true;
							scaleShipAfterFiveSeconds();
						}
						else
						{
							if (indexSwimer <= 15)
							{
								arrIsSwimerTouched[indexSwimer-1] = true;
							}
							if (indexSwimer == 15)
							{
							
								anmSprManPack.setVisible(false);
								sprShip.clearEntityModifiers();
								mEngine.unregisterUpdateHandler(timerHandlerShip);
								mEngine.unregisterUpdateHandler(timerHandlerMan);
								swimerAndShipMoveWhenHasFifty();
								isDisplaySwimer = true;
							}
						}
						
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						
						
					}
				},
				new ScaleAtModifier(duration, 0.5f, 1.0f, 77.0f, 37.0f),
				new ScaleAtModifier(duration, 1.0f, 0.5f, 77.0f, 37.0f)
		));
		
//		TimerHandler timerHandler = new TimerHandler(2.0f, new ITimerCallback() {
//			
//			@Override
//			public void onTimePassed(TimerHandler pTimerHandler) {
//			
//				
//			}
//		});
//		this.mEngine.registerUpdateHandler(timerHandler);
		return;
	}
	
	/**
	 * Display Awa
	 */
	private void displayAwa(final float pX, final float pY,final int indexAwa, final int index)
	{
		if (index > -1)
		{
			if (indexAwa < 13)
			{
				A2_4_AWABOKO2.play();
				
				arrSprAwaBall[indexAwa] = new AnimatedSprite(pX, pY, arrTtrRAwa.deepCopy(), getVertexBufferObjectManager()){
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
						{
							if (!this.isAnimationRunning() && this.isVisible())
							{
								this.clearEntityModifiers();
								A2_4_SAKANA.play();
								Log.d(TAG, "Index Awa:" + index);
								float duration = 0;
								float durationLoop = 0;
								switch (index) {
								case 0:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedTako.deepCopy(), 0, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									arrSprAwa[indexAwa].animate(300);
									break;
								case 1:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROrangeTako.deepCopy(), 1, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									arrSprAwa[indexAwa].animate(300);
									break;
								case 2:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenBeda.deepCopy(), 2, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									arrSprAwa[indexAwa].animate(300);
									break;
								case 3:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedBeda.deepCopy(), 3, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									arrSprAwa[indexAwa].animate(300);
									break;
								case 4:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRKame.deepCopy(), 4, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									arrSprAwa[indexAwa].animate(300);
									break;
								case 5:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenFish.deepCopy(), 5, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 6:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedFish.deepCopy(), 6, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 7:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenSmallFish.deepCopy(), 7, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 8:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROrangeSmallFish.deepCopy(), 8, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 9:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRManbo.deepCopy(), 9, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 10:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRHakofugu.deepCopy(), 10, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 11:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRAobudai.deepCopy(), 11, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 12:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROtoshigo.deepCopy(), 12, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								case 13:
									arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRKumanomi.deepCopy(), 13, getVertexBufferObjectManager());
									sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
									break;
								default:
									break;
								}
								
								switch (index) {
								case 5:
								case 6:
								case 7:
								case 8:
								case 11:
									duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 8 /
									(1060 + arrSprAwa[indexAwa].getWidth());	
									durationLoop = 8;
									break;
								case 12:
									duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 20 /					
									(1060 + arrSprAwa[indexAwa].getWidth());	
									durationLoop = 20;
									break;
								default:
									duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 12 /
									(1060 + arrSprAwa[indexAwa].getWidth());
									durationLoop = 12;
									break;
								}
								arrSprAwaBall[indexAwa].setVisible(false);
								arrSprAwaBall[indexAwa].setPosition(-200, -200);
								mScene.registerTouchArea(arrSprAwa[indexAwa]);
								
								
								switch (index) {
								
								
								case 12:
									arrSprAwa[indexAwa].registerEntityModifier(
											new ParallelEntityModifier(
												
													new LoopEntityModifier(
															new SequenceEntityModifier(
																	new RotationModifier(0.8f, 0, 12),
																	new RotationModifier(0.8f, 12, -12),
																	new RotationModifier(0.8f, -12 ,0)
															)
													),
													new SequenceEntityModifier(
															new DelayModifier(0.2f),
															new MoveXModifier(duration, arrSprAwa[indexAwa].getX(), -arrSprAwa[indexAwa].getWidth())
													,
													new LoopEntityModifier(
															new ParallelEntityModifier(
																	new MoveXModifier(durationLoop, 1060, -arrSprAwa[indexAwa].getWidth())
																	
															)
																	
															)
															
											)
											
											
									));
									break;
			
								default:
									arrSprAwa[indexAwa].registerEntityModifier(new SequenceEntityModifier(
											
											new DelayModifier(0.2f),
											new MoveXModifier(duration, arrSprAwa[indexAwa].getX(), -arrSprAwa[indexAwa].getWidth()),
											new LoopEntityModifier(new MoveXModifier(durationLoop, 1060, -arrSprAwa[indexAwa].getWidth()))
									));
									break;
								}
							}
							return true;
						}
						else
						{
							return false;
						}
						
					};
				};
				this.sprLayerAwa.attachChild(arrSprAwaBall[indexAwa]);
				this.mScene.registerTouchArea(arrSprAwaBall[indexAwa]);
				arrSprAwaBall[indexAwa].animate(150, false, new IAnimationListener() {
					
					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						arrSprAwaBall[indexAwa].registerEntityModifier(
								
								new SequenceEntityModifier(
									new IEntityModifierListener() {
										
										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											
											Log.i(TAG, "onModifierFinished Index Awa:" + index);
											A2_4_SAKANA.play();
											float duration = 0;
											float durationLoop = 0;
											switch (index) {
											case 0:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedTako.deepCopy(), 0, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												arrSprAwa[indexAwa].animate(300);
												break;
											case 1:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROrangeTako.deepCopy(), 1, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												arrSprAwa[indexAwa].animate(300);
												break;
											case 2:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenBeda.deepCopy(), 2, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												arrSprAwa[indexAwa].animate(300);
												break;
											case 3:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedBeda.deepCopy(), 3, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												arrSprAwa[indexAwa].animate(300);
												break;
											case 4:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRKame.deepCopy(), 4, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												arrSprAwa[indexAwa].animate(300);
												break;
											case 5:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenFish.deepCopy(), 5, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 6:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRRedFish.deepCopy(), 6, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 7:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRGreenSmallFish.deepCopy(), 7, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 8:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROrangeSmallFish.deepCopy(), 8, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 9:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRManbo.deepCopy(), 9, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 10:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRHakofugu.deepCopy(), 10, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 11:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRAobudai.deepCopy(), 11, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 12:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrROtoshigo.deepCopy(), 12, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											case 13:
												arrSprAwa[indexAwa] = new AwaAnimatedSprite(arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getY() + arrSprAwaBall[indexAwa].getHeight() / 2, ttrRKumanomi.deepCopy(), 13, getVertexBufferObjectManager());
												sprLayerAwa.attachChild(arrSprAwa[indexAwa]);
												break;
											default:
												break;
											}
											
											switch (index) {
											case 5:
											case 6:
											case 7:
											case 8:
											case 11:
												duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 8 /
												(1060 + arrSprAwa[indexAwa].getWidth());	
												durationLoop = 8;
												break;
											case 12:
												duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 20 /
												(1060 + arrSprAwa[indexAwa].getWidth());	
												durationLoop = 20;
												break;
											default:
												duration = (float) (arrSprAwa[indexAwa].getX() + arrSprAwa[indexAwa].getWidth()) * 12 /
												(1060 + arrSprAwa[indexAwa].getWidth());
												durationLoop = 12;
												break;
											}
											arrSprAwaBall[indexAwa].setVisible(false);
											arrSprAwaBall[indexAwa].setPosition(-200, -200);
											mScene.registerTouchArea(arrSprAwa[indexAwa]);
											
											
											switch (index) {
																						
											case 12:
												arrSprAwa[indexAwa].registerEntityModifier(
														new ParallelEntityModifier(
																
																new LoopEntityModifier(
																		new SequenceEntityModifier(
																				new RotationModifier(0.8f, 0, 12),
																				new RotationModifier(0.8f, 12, -12),
																				new RotationModifier(0.8f, -12 ,0)
																		)
																),
																new SequenceEntityModifier(
																		new DelayModifier(0.2f),
																		new MoveXModifier(duration, arrSprAwa[indexAwa].getX(), -arrSprAwa[indexAwa].getWidth())
																,
																new LoopEntityModifier(
																		new ParallelEntityModifier(
																				new MoveXModifier(durationLoop, 1060, -arrSprAwa[indexAwa].getWidth())
																				
																		)
																				
																		)
																		
														)
														
														
												));
												break;
	
											default:
												arrSprAwa[indexAwa].registerEntityModifier(new SequenceEntityModifier(
														
														new DelayModifier(0.2f),
														new MoveXModifier(duration, arrSprAwa[indexAwa].getX(), -arrSprAwa[indexAwa].getWidth()),
														new LoopEntityModifier(new MoveXModifier(durationLoop, 1060, -arrSprAwa[indexAwa].getWidth()))
												));
												break;
											}
											
											
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											
											
										}
									},
									new DelayModifier(2.5f),
									new LoopEntityModifier( 
											new SequenceEntityModifier(
													new MoveXModifier(0.1f,arrSprAwaBall[indexAwa].getX(), arrSprAwaBall[indexAwa].getX() + 10),
													new MoveXModifier(0.1f,arrSprAwaBall[indexAwa].getX() + 10, arrSprAwaBall[indexAwa].getX())
											), 4
									)
							));
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
						
						
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
						
						
					}

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						
						
					}
				});
			}
		}
	}
	
	private boolean displayAwaLeft(boolean gimmicTouch)
	{
		boolean result = false;
		
		Log.d(TAG, "__________indexAwaLeft__________" + indexAwaLeft);
		if (indexAwaLeft < 26)
		{
			if (!gimmicTouch)
			{
				A2_BOMI2.play();
			}
			arrSprLeft[indexAwaLeft++].setVisible(true);
			arrSprLeft[indexAwaLeft++].setVisible(true);
			Log.d(TAG, "indexAwaLeft:" + indexAwaLeft);
			result = true;
		}
		else if (indexAwaLeft == 26)
		{
			if (!gimmicTouch)
			{
				A2_BOMI2.play();
			}
			arrSprLeft[indexAwaLeft++].setVisible(true);
			arrSprLeft[indexAwaLeft++].setVisible(true);
			this.mEngine.registerUpdateHandler(new TimerHandler(0.5f, false, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {					
						A2_BUJU3.play();
					for (int i = 0; i < 28; i++)
					{
						arrSprLeft[i].setVisible(false);
					}
					indexAwaLeft = 0;	
				}
			}));
			
			result = false;
		}
		return result;
	}
	
	private boolean displayAwaRight(boolean gimmicTouch)
	{
		boolean result = false;
		
		if (indexAwaRight < 34)
		{
			if (!gimmicTouch)
			{
				A2_BOMI2.play();
			}
			arrSprRight[indexAwaRight++].setVisible(true);
			arrSprRight[indexAwaRight++].setVisible(true);
			
			Log.d(TAG, "indexAwaRight:" + indexAwaRight);
			result = true;
		}
		else if (indexAwaRight == 34)
		{
			if (!gimmicTouch)
			{
				A2_BOMI2.play();
			}
			arrSprRight[indexAwaRight++].setVisible(true);
			arrSprRight[indexAwaRight++].setVisible(true);
			this.mEngine.registerUpdateHandler(new TimerHandler(0.5f, false, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {					
						A2_BUJU3.play();
					for (int i = 0; i < 36; i++)
					{
						arrSprRight[i].setVisible(false);
					}
					indexAwaRight = 0;
				}
			}));
			
			
			result = false;
		}
		return result;
	}
	
	class AwaAnimatedSprite extends AnimatedSprite
	{
		
		private int index;
		private boolean isRightToleft;
		private boolean isFree;
		public boolean isFree() {
			return isFree;
		}

		public void setFree(boolean isFree) {
			this.isFree = isFree;
		}

		public boolean isRightToleft() {
			return isRightToleft;
		}

		public void setRightToleft(boolean isRightToleft) {
			this.isRightToleft = isRightToleft;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		public AwaAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.isRightToleft = true;
			this.isFree = true;
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (!checkContainsPolygon(sprLeftIva, arrPointerTouchAwaLeft, 5, pSceneTouchEvent.getX(), pSceneTouchEvent.getY()) &&
					!checkContainsPolygon(sprRightIva, arrPointerTouchAwaRight, 8, pSceneTouchEvent.getX(), pSceneTouchEvent.getY())	
			)
			{
			
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
				{
					if (indexSwimer != 15)
					{
						A2_4_KIRA3.play();
						this.clearEntityModifiers();
						float duration = 0;
						float durationLoop = 0;
						switch (this.index) {
						case 5:
						case 6:
						case 7:
						case 8:
						case 11:
							if (!this.isRightToleft)
							{
								duration = (float) (this.getX() + this.getWidth()) * 8 /
								(1060 + this.getWidth());	
								durationLoop = 8;
								this.setFlippedHorizontal(false);
								
								switch (index) {
								
								case 12:
									duration = (float) (this.getX() + this.getWidth()) * 20 /
									(1060 + this.getWidth());	
									durationLoop = 20;
									this.registerEntityModifier(
											new ParallelEntityModifier(
												
													new LoopEntityModifier(
															new SequenceEntityModifier(
																	new RotationModifier(0.8f, 0, 12),
																	new RotationModifier(0.8f, 12, -12),
																	new RotationModifier(0.8f, -12 ,0)
															)
													),
													new SequenceEntityModifier(
															new MoveXModifier(duration, this.getX(), -this.getWidth())
													,
													new LoopEntityModifier(
															new ParallelEntityModifier(
																	new MoveXModifier(durationLoop, 1060, -this.getWidth())
																	
															)
																	
															)
															
											)
											
											
									));
									break;
		
								default:
									this.registerEntityModifier(new SequenceEntityModifier(
											new MoveXModifier(duration, this.getX(), -this.getWidth()),
											new LoopEntityModifier(new MoveXModifier(durationLoop, 1060, -this.getWidth()))
									));
									break;
								}
							}
							else
							{
								duration = (1060 - this.getX()) * 8.0f / (1060 + this.getWidth());
								durationLoop = 8;
								this.setFlippedHorizontal(true);
								
								switch (index) {
								
							
								case 12:
									duration = (1060 - this.getX()) * 20.0f / (1060 + this.getWidth());
									durationLoop = 20;
									this.registerEntityModifier(
											new ParallelEntityModifier(
													
													new LoopEntityModifier(
															new SequenceEntityModifier(
																	new RotationModifier(0.8f, 0, 12),
																	new RotationModifier(0.8f, 12, -12),
																	new RotationModifier(0.8f, -12 ,0)
															)
													),
													new SequenceEntityModifier(
															new MoveXModifier(duration, this.getX(), 1060)
													,
													new LoopEntityModifier(
															new ParallelEntityModifier(
																	new MoveXModifier(durationLoop, -this.getWidth(), 1060)
																	
															)
																	
															)
															
											)
											
											
									));
									break;
		
								default:
									this.registerEntityModifier(new SequenceEntityModifier(
											new MoveXModifier(duration, this.getX(), 1060),
											new LoopEntityModifier(new MoveXModifier(durationLoop, -this.getWidth(), 1060)) 
									));
									break;
								}
							}
							break;
							
						default:
							if (!this.isRightToleft)
							{
								duration = (float) (this.getX() + this.getWidth()) * 12 /
								(960 + this.getWidth());	
								durationLoop = 12;
								this.setFlippedHorizontal(false);
								switch (index) {
								case 12:
									duration = (float) (this.getX() + this.getWidth()) * 20 /
									(1060 + this.getWidth());	
									durationLoop = 20;
									this.registerEntityModifier(
											new ParallelEntityModifier(
												
													new LoopEntityModifier(
															new SequenceEntityModifier(
																	new RotationModifier(0.8f, 0, 12),
																	new RotationModifier(0.8f, 12, -12),
																	new RotationModifier(0.8f, -12 ,0)
															)
													),
													new SequenceEntityModifier(
															new MoveXModifier(duration, this.getX(), -this.getWidth())
													,
													new LoopEntityModifier(
															new ParallelEntityModifier(
																	new MoveXModifier(durationLoop, 1060, -this.getWidth())
																	
															)
																	
															)
															
											)
											
											
									));
									break;
		
								default:
									this.registerEntityModifier(new SequenceEntityModifier(
											new MoveXModifier(duration, this.getX(), -this.getWidth()),
											new LoopEntityModifier(new MoveXModifier(durationLoop, 1060, -this.getWidth()))
									));
									break;
								}						
							}
							else
							{
								duration = (1060 - this.getX()) * 12.0f / (1060 + this.getWidth());
								durationLoop = 12;
								this.setFlippedHorizontal(true);
								switch (index) {
								case 12:
									duration = (1060 - this.getX()) * 20.0f / (1060 + this.getWidth());
									durationLoop = 20;
									this.registerEntityModifier(
											new ParallelEntityModifier(
													
													new LoopEntityModifier(
															new SequenceEntityModifier(
																	new RotationModifier(0.8f, 0, 12),
																	new RotationModifier(0.8f, 12, -12),
																	new RotationModifier(0.8f, -12 ,0)
															)
													),
													new SequenceEntityModifier(
															new MoveXModifier(duration, this.getX(), 1060)
													,
													new LoopEntityModifier(
															new ParallelEntityModifier(
																	new MoveXModifier(durationLoop, -this.getWidth(), 1060)
																	
															)
																	
													)
											)
											
									));
									break;
		
								default:
									this.registerEntityModifier(new SequenceEntityModifier(
											new MoveXModifier(duration, this.getX(), 1060),
											new LoopEntityModifier(new MoveXModifier(durationLoop, -this.getWidth(), 1060)) 
									));
									break;
								}
							}
							break;
						}
						this.isRightToleft = !this.isRightToleft;
			
						return true;
					}
				}
				
			}
			else
			{
				return false;
//				if (checkContainsPolygon(sprLeftIva, arrPointerTouchAwaLeft, 5, pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))
//				{
//					displayAwaLeft(false);
//				}
//				if (checkContainsPolygon(sprRightIva, arrPointerTouchAwaRight, 8, pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))
//				{
//					displayAwaRight(false);
//				}
			}
			return true;
		}
		
	}
	IUpdateHandler autodetect  = new IUpdateHandler() {
		
		@Override
		public void reset() {}
		
		@Override
		public void onUpdate(float pSecondsElapsed) {
			
			for (int i = 0; i < 14; i++)
			{
				if (arrSprAwa[i] != null)
				{
					if (arrSprAwa[i].getX() > 960)
					{
						arrSprAwa[i].setFree(true);
					}
					else
					{
						arrSprAwa[i].setFree(false);
					}
				}
			}
		}
	};
	
	public class SlideSpritePool extends GenericPool<Sprite> {
		
		private TextureRegion mTextureRegion;
		
		private VertexBufferObjectManager mVertexBufferObjectManager;
		public SlideSpritePool(
				TextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
			this.mTextureRegion = pTextureRegion;
			this.mVertexBufferObjectManager = pVertexBufferObjectManager;
		}
		
		@Override
		protected Sprite onAllocatePoolItem() {
			final Sprite sp = new Sprite(0, 0, this.mTextureRegion.deepCopy(), mVertexBufferObjectManager);
			return sp;
		}
		
		@Override
		protected void onHandleRecycleItem(final Sprite pSprite) {
			pSprite.clearEntityModifiers();
			pSprite.clearUpdateHandlers();
			pSprite.setVisible(false);
			pSprite.detachSelf();
			pSprite.setIgnoreUpdate(true);
		}
		
		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
			pItem.reset();
			pItem.setPosition(0, 0);
		}
	}

	
	public static class Polygon {

		public static boolean contains(int x, int y, int[] polyX, int[] polyY,
				int polySides) {
			boolean c = false;
			int i, j = 0;
			for (i = 0, j = polySides - 1; i < polySides; j = i++) {
				if (((polyY[i] > y) != (polyY[j] > y))
						&& (x < (polyX[j] - polyX[i]) * (y - polyY[i])
								/ (polyY[j] - polyY[i ]) + polyX[i]))
					c = !c;
			}
			return c;
		}
	}


	@Override
	public void onAnimationFinished(AnimatedSprite arg0) {
		
		
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
		
		
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
		
		
	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
		
		
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		
		
	}
	
	private void AnimatedRun(AnimatedSprite pAnimatedSprite){
		pAnimatedSprite.animate(new long[]{800,800}, new int[]{0,1}, -1);
	}
	
}

