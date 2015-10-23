package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KoinoboriResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.util.Log;

public class Vol3Koinobori extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity>, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int CAMERA_WIDTH = 960;
	private static final int CAMERA_HEIGHT = 640;
	private static final String TAG = "LOG_KOINOBORI";

	// ===========================================================
	// Fields
	// ===========================================================

	/**
	 * Texture, TextureRegion, TiledTextureRegion, Sprite, AnimatedSprite
	 */

	private ITextureRegion ttrRBackground, ttrRBackground2, ttrRBackground3,
			arrTtrRRight[] = new ITextureRegion[8], ttrRYellowBall,
			ttrRYaguruma, ttrRCycle, ttrRTanpopoOne, ttrRTanpopoTwo,
			ttrRTanpopoThree, ttrRTanpopoFour;

	private TiledTextureRegion arrTiledTtrRFlag[] = new TiledTextureRegion[5],
			tiledTtrRRedBoy, tiledTtrRYellowBoy, tiledTtrRBear, tiledTtrRCat,
			tiledTtrRAudience, tiledTtrRKumo, tiledTtrRMogura,
			tiledTtrRTanpopoOne, tiledTtrRTanpopoTwo, tiledTtrRTanpopoThree,
			tiledTtrRTanpopoFour;

	private AnimalSprite anmSprRedBoy, anmSprYellowBoy, anmSprBear, anmSprCat;
	private AnimatedSprite anmSprAudience, anmSprKumo, anmSprMogura,
			anmSprTanpopoOne, anmSprTanpopoTwo, anmSprTanpopoThree,
			anmSprTanpopoFour;

	private FlagAnimatedSprite arrAnmSprFlag[] = new FlagAnimatedSprite[5];

	private Sprite sprBackground2, sprBackground3, sprYellowBall, sprYaguruma,
			sprCycle, sprTanpopoOne, sprTanpopoTwo, sprTanpopoThree,
			sprTanpopoFour;
	private Entity sprBoundFlags;
	private final MySprite arrSpriteRight[] = new MySprite[8];

	/**
	 * Modifier
	 */
	private RotationModifier rotateYaguruma, cycleModifier;
	@SuppressWarnings("unused")
	private LoopEntityModifier kumoModifier;
	private AlphaModifier kumoAlphaModifier;
	private SequenceEntityModifier tanpopoOneModifier, tanpopoTwoModifier,
			tanpopoThreeModifier, tanpopoFourModifier;
	private int countFlagOnTree = 0;
	private int indexFirst, indexSecond, indexThrew, indexFour;
	private boolean isResetTree = false;
	/**
	 * Array contains Pointer All Object Right
	 */
	private int[][] arrPoiterRight = {
			{ 838, 839, 839, 840, 841, 841, 843, 844, 842 },
			{ 95, 158, 228, 298, 367, 432, 495, 581 } };

	/**
	 * Array contains Pointer Animals
	 */
	private int[][] arrPointerAnimals = { { 703, 145, 621, 80 },
			{ 453, 336, 336, 479 } };

	/**
	 * Array contains pointer Huki on Tree
	 */
	private int arrHuKiOnTree_1[][] = new int[][] { { 125, 123, 124 },
			{ 1, 1, 6 } };
	private int arrHuKiOnTree_2[][] = new int[][] { { 132, 131, 131 },
			{ 47, 47, 53 } };
	private int arrHuKiOnTree_3[][] = new int[][] { { 149, 146, 147 },
			{ 102, 101, 107 } };
	private int arrHuKiOnTree_4[][] = new int[][] { { 164, 163, 163 },
			{ 146, 145, 151 } };
	private int arrHuKiOnTree_5[][] = new int[][] { { 180, 178, 178 },
			{ 187, 185, 192 } };

	/**
	 * List contains 5 Array Pointer Huki on Tree
	 */
	private ArrayList<int[][]> arrListHukiOntree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer Magoi on Tree
	 */
	private int arrMagoiOnTree_1[][] = new int[][] { { 123, 122, 125 },
			{ -3, -5, -2 } };
	private int arrMagoiOnTree_2[][] = new int[][] { { 133, 132, 135 },
			{ 47, 45, 48 } };
	private int arrMagoiOnTree_3[][] = new int[][] { { 146, 145, 150 },
			{ 98, 96, 99 } };
	private int arrMagoiOnTree_4[][] = new int[][] { { 162, 160, 165 },
			{ 142, 140, 143 } };
	private int arrMagoiOnTree_5[][] = new int[][] { { 177, 176, 180 },
			{ 183, 181, 184 } };

	/**
	 * List contains 5 Array Pointer Magoi on Tree
	 */
	private ArrayList<int[][]> arrListMagoiOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer Higoi on Tree
	 */
	private int arrHigoiOnTree_1[][] = new int[][] { { 125, 125, 122 },
			{ -4, -1, -8 } };
	private int arrHigoiOnTree_2[][] = new int[][] { { 134, 135, 133 },
			{ 49, 46, 43 } };
	private int arrHigoiOnTree_3[][] = new int[][] { { 149, 148, 146 },
			{ 100, 98, 94 } };
	private int arrHigoiOnTree_4[][] = new int[][] { { 164, 165, 161 },
			{ 144, 142, 137 } };
	private int arrHigoiOnTree_5[][] = new int[][] { { 180, 180, 177 },
			{ 184, 182, 179 } };

	/**
	 * List contains 5 Array Pointer Higoi on Tree
	 */
	private ArrayList<int[][]> arrListHigoiOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer KogoiA on Tree
	 */
	private int arrKogoiAOnTree_1[][] = new int[][] { { 122, 123, 125 },
			{ 8, 11, 13 } };
	private int arrKogoiAOnTree_2[][] = new int[][] { { 132, 134, 135 },
			{ 57, 61, 63 } };
	private int arrKogoiAOnTree_3[][] = new int[][] { { 146, 147, 149 },
			{ 109, 112, 115 } };
	private int arrKogoiAOnTree_4[][] = new int[][] { { 162, 162, 165 },
			{ 153, 156, 159 } };
	private int arrKogoiAOnTree_5[][] = new int[][] { { 177, 178, 180 },
			{ 193, 197, 199 } };

	/**
	 * List contains 5 Array Pointer KogoiA on Tree
	 */
	private ArrayList<int[][]> arrListKogoiAOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer KogoiB on Tree
	 */
	private int arrKogoiBOnTree_1[][] = new int[][] { { 123, 124, 125 },
			{ 8, 11, 13 } };
	private int arrKogoiBOnTree_2[][] = new int[][] { { 133, 134, 135 },
			{ 57, 61, 63 } };
	private int arrKogoiBOnTree_3[][] = new int[][] { { 148, 148, 149 },
			{ 116, 115, 121 } };
	private int arrKogoiBOnTree_4[][] = new int[][] { { 163, 163, 164 },
			{ 161, 160, 165 } };
	private int arrKogoiBOnTree_5[][] = new int[][] { { 178, 179, 179 },
			{ 201, 200, 206 } };

	/**
	 * List contains 5 Array Pointer KogoiB on Tree
	 */
	private ArrayList<int[][]> arrListKogoiBOnTree = new ArrayList<int[][]>();

	/**
	 * List contains all List On Tree
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> arrListOnTree = new ArrayList<ArrayList>();

	/**
	 * Array contains pointer Red Boy on Flags
	 */
	private int arrRedboyOnHuki[][] = new int[][] { { 139, 185, 266 },
			{ 3, 11, 13 } };
	private int arrRedboyOnMagoi[][] = new int[][] { { 156, 193, 231 },
			{ 13, 13, 3 } };
	private int arrRedboyOnHigoi[][] = new int[][] { { 147, 178, 220 },
			{ 14, 21, 20 } };
	private int arrRedboyOnKogoiA[][] = new int[][] { { 138, 172, 209 },
			{ 18, 29, 32 } };
	private int arrRedboyOnKogoiB[][] = new int[][] { { 138, 165, 190 },
			{ 10, 32, 40 } };
	/**
	 * List contains all 5 Array Pointer Red Boy On Flags
	 */
	private ArrayList<int[][]> arrListRedBoyOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer Yellow Boy on Flags
	 */
	private int arrYellowBoyOnHuki[][] = new int[][] { { 227, 252, 324 },
			{ 35, 26, 32 } };

	private int arrYellowBoyOnMagoi[][] = new int[][] { { 197, 278, 306 },
			{ 54, 39, 24 } };

	private int arrYellowBoyOnHigoi[][] = new int[][] { { 240, 255, 269 },
			{ 46, 42, 24 } };
	private int arrYellowBoyOnKogoiA[][] = new int[][] { { 175, 216, 254 },
			{ 57, 47, 42 } };

	private int arrYellowBoyOnKogoiB[][] = new int[][] { { 165, 197, 229 },
			{ 58, 51, 50 } };

	/**
	 * List contains all 5 Array Pointer Yellow Boy On Flags
	 */
	private ArrayList<int[][]> arrListYellowBoyOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer Bear on Flags
	 */
	private int arrBearOnHuki[][] = new int[][] { { 214, 238, 336 },
			{ 101, 79, 86 } };

	private int arrBearOnMagoi[][] = new int[][] { { 213, 215, 319 },
			{ 114, 89, 78 } };

	private int arrBearOnHigoi[][] = new int[][] { { 202, 208, 284 },
			{ 112, 91, 72 } };

	private int arrBearOnKogoiA[][] = new int[][] { { 198, 205, 268 },
			{ 114, 100, 94 } };

	private int arrBearOnKogoiB[][] = new int[][] { { 170, 181, 245 },
			{ 123, 108, 102 } };

	/**
	 * List contains all 5 Array Pointer Bear On Flags
	 */
	private ArrayList<int[][]> arrListBearOnTree = new ArrayList<int[][]>();

	/**
	 * Array contains pointer Cat on Flags
	 */
	private int arrCatOnHuki[][] = new int[][] { { 248, 278, 355 },
			{ 132, 116, 129 } };

	private int arrCatOnMagoi[][] = new int[][] { { 215, 236, 342 },
			{ 150, 125, 117 } };

	private int arrCatOnHigoi[][] = new int[][] { { 214, 226, 305 },
			{ 150, 129, 112 } };
	private int arrCatOnKogoiA[][] = new int[][] { { 196, 206, 293 },
			{ 157, 144, 136 } };

	private int arrCatOnKogoiB[][] = new int[][] { { 238, 242, 265 },
			{ 145, 144, 141 } };

	/**
	 * List contains all 5 Array Pointer Cat On Flags
	 */
	private ArrayList<int[][]> arrListCatOnTree = new ArrayList<int[][]>();

	/**
	 * List Animal On Tree
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> listAnimalOnTree = new ArrayList<ArrayList>();

	/**
	 * All Pointer X Axis of Polygon Bound Tree
	 */
	private int pxPolygon[] = new int[] { 175, 346, 553, 553, 175 };

	/**
	 * All Pointer Y Axis of Polygon Bound Tree
	 */
	private int pyPolygon[] = new int[] { 66, 596, 596, 66, 66 };

	/**
	 * All Pointer X Axis of Polygon Bound Tree
	 */
	private int pxPolygonAnimal[] = new int[] { 175, 346, 593, 593, 175 };

	/**
	 * All Pointer Y Axis of Polygon Bound Tree
	 */
	private int pyPolygonAnimal[] = new int[] { 66, 596, 596, 66, 66 };
	/**
	 * All Pointer X Axis of Polygon Bound Tree Touch
	 */
	private int pxPolygonTree[] = new int[] { 205, 323, 415, 260, 205 };

	/**
	 * All Pointer Y Axis of Polygon Bound Tree
	 */
	private int pyPolygonTree[] = new int[] { 78, 591, 591, 78, 78 };

	/**
	 * All Sound
	 */
	private Sound mp3Kara;
	private Sound mp3Basa;
	private Sound mp3Boyon;
	private Sound mp3Mogu;
	private Sound mp3Oishi;
	private Sound mp3Pomi;
	private Sound mp3Powa2;
	private Sound mp3Oho;
	private Sound mp3Kira;
	private Sound mp3Pyu;
	private Sound mp3Bell;
	private Sound mp3Block;
	private Sound mp3Powa;
	private Sound mp3Syu2;

	private TexturePack Vol3KoinoboriPacker_1, Vol3KoinoboriPacker_2, Vol3KoinoboriPacker_3, Vol3KoinoboriPacker_4;
	private TexturePackTextureRegionLibrary Vol3KoinoboriPackerLibrary1, Vol3KoinoboriPackerLibrary2, Vol3KoinoboriPackerLibrary3, Vol3KoinoboriPackerLibrary4;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("koinobori/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("koinobori/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
                        getTextureManager(), pAssetManager, "koinobori/gfx/");
		super.onCreateResources();
	}

	@Override
	public void loadKaraokeResources() {
		Vol3KoinoboriPacker_1 = mTexturePackLoaderFromSource
                .load("Vol3KoinoboriPacker1.xml");
		Vol3KoinoboriPacker_1.loadTexture();
		Vol3KoinoboriPackerLibrary1 = Vol3KoinoboriPacker_1
                .getTexturePackTextureRegionLibrary();
		
		Vol3KoinoboriPacker_2 = mTexturePackLoaderFromSource
                .load("Vol3KoinoboriPacker2.xml");
                Vol3KoinoboriPacker_2.loadTexture();
                Vol3KoinoboriPackerLibrary2 = Vol3KoinoboriPacker_2
                .getTexturePackTextureRegionLibrary();
                
                Vol3KoinoboriPacker_3 = mTexturePackLoaderFromSource
                .load("Vol3KoinoboriPacker3.xml");
                Vol3KoinoboriPacker_3.loadTexture();
                Vol3KoinoboriPackerLibrary3 = Vol3KoinoboriPacker_3
                .getTexturePackTextureRegionLibrary();
                
                Vol3KoinoboriPacker_4 = mTexturePackLoaderFromSource
                .load("KoinoboriPackerMain4.xml");
                Vol3KoinoboriPacker_4.loadTexture();
                Vol3KoinoboriPackerLibrary4 = Vol3KoinoboriPacker_4
                .getTexturePackTextureRegionLibrary();
                
		this.ttrRBackground = Vol3KoinoboriPackerLibrary4
				.get(Vol3KoinoboriResource.KoinoboriPackerMain4.A7_21_1_IPHONE_HAIKEI_ID);

		this.ttrRBackground2 = Vol3KoinoboriPackerLibrary4
				.get(Vol3KoinoboriResource.KoinoboriPackerMain4.A7_21_2_IPHONE_HAIKEI_ID);
		this.ttrRBackground3 = Vol3KoinoboriPackerLibrary1
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker1.A7_21_3_IPHONE_HAIKEI_ID);

		// Load Audience
		this.tiledTtrRAudience = getTiledTextureFromPacker(
		                Vol3KoinoboriPacker_1,
				Vol3KoinoboriResource.Vol3KoinoboriPacker1.A7_15_1_IPHONE_AUDIENCE_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker1.A7_15_2_IPHONE_AUDIENCE_ID);

		// Load All Right
		for (int i = 0; i < 8; i++) {
			this.arrTtrRRight[i] = Vol3KoinoboriPackerLibrary3
					.get(Vol3KoinoboriResource.ARR_RESOURCE_RIGHT[i]);
		}
		// Load RedBoy

		this.tiledTtrRRedBoy = getTiledTextureFromPacker(Vol3KoinoboriPacker_2,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_1_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_2_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_3_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_4_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_5_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_6_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_7_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_8_IPHONE_REDBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_10_9_IPHONE_REDBOY_ID);

		// Load YellowBoy
		this.tiledTtrRYellowBoy = getTiledTextureFromPacker(Vol3KoinoboriPacker_2,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_1_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_2_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_3_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_4_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_5_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_6_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_7_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_8_IPHONE_YELLOWBOY_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_11_9_IPHONE_YELLOWBOY_ID);

		// Load RedBear
		this.tiledTtrRBear = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_1_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_2_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_3_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_4_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_5_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_6_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_7_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_8_IPHONE_BEAR_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_12_9_IPHONE_BEAR_ID);

		// Load Cat
		this.tiledTtrRCat = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_1_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_2_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_3_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_4_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_5_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_6_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_7_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_8_IPHONE_CAT_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_13_9_IPHONE_CAT_ID);
		// Load YellowBall
		this.ttrRYellowBall = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_20_IPHONE_YELLOWBALL_ID);

		// Load Yaguruma
		this.ttrRYaguruma = Vol3KoinoboriPackerLibrary2
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker2.A7_04_IPHONE_YAGURUMA_ID);

		// Load Kumo
		this.tiledTtrRKumo = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_17_1_IPHONE_CLOUD_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_17_2_IPHONE_CLOUD_ID);

		// Load Cycle
		this.ttrRCycle = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_18_IPHONE_CYCLE_ID);

		// Load Mogura
		this.tiledTtrRMogura = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_14_1_IPHONE_MOGURA_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_14_2_IPHONE_MOGURA_ID);

		// Load Tanpopo
		this.tiledTtrRTanpopoOne = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16A_1_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16A_2_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16A_4_IPHONE_TANPOPO_ID);

		this.tiledTtrRTanpopoTwo = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16B_1_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16B_2_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16B_4_IPHONE_TANPOPO_ID);

		this.tiledTtrRTanpopoThree = getTiledTextureFromPacker(
		                Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16C_1_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16C_2_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16C_4_IPHONE_TANPOPO_ID);

		this.tiledTtrRTanpopoFour = getTiledTextureFromPacker(Vol3KoinoboriPacker_3,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16D_1_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16D_2_IPHONE_TANPOPO_ID,
				Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16D_4_IPHONE_TANPOPO_ID);

		this.ttrRTanpopoOne = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16_3_IPHONE_TANPOPO_ID);
		this.ttrRTanpopoTwo = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16_3_IPHONE_TANPOPO_ID);
		this.ttrRTanpopoThree = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16_3_IPHONE_TANPOPO_ID);
		this.ttrRTanpopoFour = Vol3KoinoboriPackerLibrary3
				.get(Vol3KoinoboriResource.Vol3KoinoboriPacker3.A7_16_3_IPHONE_TANPOPO_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		// Load All Sound
		mp3Kara = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_04_KARA);
		mp3Basa = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_05_09BASA);
		mp3Boyon = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_10_13_BOYON);
		mp3Mogu = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_10_13_MOGU);
		mp3Oishi = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_10_13_OISHI6);
		mp3Pomi = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_10_13_POMI);
		mp3Powa2 = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_14_POWA2);
		mp3Oho = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_15_OHO);
		mp3Kira = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_16_KIRA10);
		mp3Pyu = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_17_PYU);
		mp3Bell = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_18_BELL);
		mp3Block = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_19_BLOK2);
		mp3Powa = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_19_POWA);
		mp3Syu2 = loadSoundResourceFromSD(Vol3KoinoboriResource.A7_20_SYU2);
	}

	@Override
	public void loadKaraokeScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// Set Backgroud For Scene
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground, this
						.getVertexBufferObjectManager())));

		// Attach Audience To Screen
		this.anmSprAudience = new AnimatedSprite(97, 24,
				this.tiledTtrRAudience, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprAudience);

		// Attach Kumo
		this.anmSprKumo = new AnimatedSprite(9, 95, this.tiledTtrRKumo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprKumo);
		this.anmSprKumo.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// Attach Background 2
		this.sprBackground2 = new Sprite(0, 0, this.ttrRBackground2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBackground2);

		// Attach Cycle
		this.sprCycle = new Sprite(670, 6, this.ttrRCycle,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprCycle);

		// Attach Background 3
		this.sprBackground3 = new Sprite(0, 0, this.ttrRBackground3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBackground3);

		// Attach Mogura
		this.anmSprMogura = new AnimatedSprite(522, 489, this.tiledTtrRMogura,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprMogura);

		// Attach Tanpopo
		this.anmSprTanpopoOne = new AnimatedSprite(262, 445,
				this.tiledTtrRTanpopoOne, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprTanpopoOne);

		this.anmSprTanpopoTwo = new AnimatedSprite(422, 324,
				this.tiledTtrRTanpopoTwo, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprTanpopoTwo);

		this.anmSprTanpopoThree = new AnimatedSprite(431, 421,
				this.tiledTtrRTanpopoThree, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprTanpopoThree);

		this.anmSprTanpopoFour = new AnimatedSprite(649, 241,
				this.tiledTtrRTanpopoFour, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprTanpopoFour);

		this.sprTanpopoOne = new Sprite(400, 200, this.ttrRTanpopoOne,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprTanpopoOne);
		// this.sprTanpopoOne.setBlendFunction(GL10.GL_SRC_ALPHA,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		// this.sprTanpopoOne.setAlpha(0.0f);
		this.sprTanpopoOne.setVisible(false);

		this.sprTanpopoTwo = new Sprite(0, 0, this.ttrRTanpopoTwo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprTanpopoTwo);
		// this.sprTanpopoTwo.setBlendFunction(GL10.GL_SRC_ALPHA,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sprTanpopoTwo.setVisible(false);
		// this.sprTanpopoTwo.setAlpha(0.0f);

		this.sprTanpopoThree = new Sprite(0, 0, this.ttrRTanpopoThree,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprTanpopoThree);
		// this.sprTanpopoThree.setBlendFunction(GL10.GL_SRC_ALPHA,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sprTanpopoThree.setVisible(false);
		// this.sprTanpopoThree.setAlpha(0.0f);

		this.sprTanpopoFour = new Sprite(0, 0, this.ttrRTanpopoFour,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprTanpopoFour);
		// this.sprTanpopoFour.setBlendFunction(GL10.GL_SRC_ALPHA,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sprTanpopoFour.setVisible(false);
		// this.sprTanpopoFour.setAlpha(0.0f);

		// Attach All Right
		for (int i = 0; i < 8; i++) {
			this.arrSpriteRight[i] = new MySprite(arrPoiterRight[0][i],
					arrPoiterRight[1][i], arrTtrRRight[i], i,
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSpriteRight[i]);
			this.mScene.registerTouchArea(this.arrSpriteRight[i]);
		}

		this.anmSprYellowBoy = new AnimalSprite(145, 336,
				this.tiledTtrRYellowBoy, 1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprYellowBoy);
		this.mScene.registerTouchArea(this.anmSprYellowBoy);

		this.anmSprBear = new AnimalSprite(621, 336, this.tiledTtrRBear, 2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprBear);
		this.mScene.registerTouchArea(this.anmSprBear);

		this.anmSprCat = new AnimalSprite(80, 479, this.tiledTtrRCat, 3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprCat);
		this.mScene.registerTouchArea(this.anmSprCat);

		// Attach Bound Flag
		sprBoundFlags = new Entity(0, 0);
		this.sprBoundFlags.setZIndex(999);
		this.mScene.attachChild(this.sprBoundFlags);

		// Attach Red boy
		this.anmSprRedBoy = new AnimalSprite(703, 453, this.tiledTtrRRedBoy, 0,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprRedBoy);
		this.mScene.registerTouchArea(this.anmSprRedBoy);

		// Add YellowBall
		this.sprYellowBall = new Sprite(185, 12, this.ttrRYellowBall,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprYellowBall);

		// Add Yaguruma
		this.sprYaguruma = new Sprite(172, 91, this.ttrRYaguruma,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprYaguruma);
		this.sprYaguruma.setVisible(false);
		this.sprYaguruma.setZIndex(1000);

		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3KoinoboriResource.SOUND_GIMMIC,
				Vol3KoinoboriResource.IMAGE_GIMMIC,
				Vol3KoinoboriPackerLibrary3);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
		Log.d(TAG,"loadKaraokeComplete");
	}
	@Override
	public void onResumeGame() {
	    Log.d(TAG,"onResumeGame");
	    anmSprKumo.clearEntityModifiers();
	    anmSprKumo.stopAnimation();
	    anmSprKumo.setAlpha(1.0f);
	    anmSprKumo.setPosition(19, 135);
	    anmSprKumo.setCurrentTileIndex(0);
	    kumoAlphaModifier = null;
	    sprCycle.clearEntityModifiers();
	    sprCycle.setRotation(0.0f);
	    sprCycle.setPosition(670, 6);
	    cycleModifier = null;
	    anmSprAudience.clearEntityModifiers();
	    anmSprAudience.stopAnimation();
	    anmSprAudience.reset();
	    sprTanpopoOne.clearEntityModifiers();
	    tanpopoOneModifier = null;
	    sprTanpopoOne.setVisible(false);
	    sprTanpopoOne.setAlpha(1.0f);
	    sprTanpopoTwo.clearEntityModifiers();
	    tanpopoTwoModifier = null;
	    sprTanpopoTwo.setVisible(false);
	    sprTanpopoTwo.setAlpha(1.0f);
	    sprTanpopoThree.clearEntityModifiers();
	    tanpopoThreeModifier = null;
	    sprTanpopoThree.setVisible(false);
	    sprTanpopoThree.setAlpha(1.0f);
	    sprTanpopoFour.clearEntityModifiers();
	    tanpopoFourModifier = null;
	    sprTanpopoFour.setVisible(false);
	    sprTanpopoFour.setAlpha(1.0f);
	    anmSprMogura.stopAnimation();
	    anmSprMogura.setCurrentTileIndex(0);
	    anmSprTanpopoOne.stopAnimation();
	    anmSprTanpopoOne.setCurrentTileIndex(0);
	    anmSprTanpopoTwo.stopAnimation();
	    anmSprTanpopoTwo.setCurrentTileIndex(0);
	    anmSprTanpopoThree.stopAnimation();
	    anmSprTanpopoThree.setCurrentTileIndex(0);
	    anmSprTanpopoFour.stopAnimation();
	    anmSprTanpopoFour.setCurrentTileIndex(0);
	    anmSprRedBoy.stopAnimation();
	    anmSprRedBoy.setPosition(703, 453);
	    anmSprRedBoy.setHasHat(false);
	    anmSprRedBoy.setOnTree(false);
	    anmSprRedBoy.setCurrentTileIndex(0);
	    anmSprYellowBoy.stopAnimation();
	    anmSprYellowBoy.setPosition(165, 336);
	    anmSprYellowBoy.setHasHat(false);
	    anmSprYellowBoy.setOnTree(false);
	    anmSprYellowBoy.setCurrentTileIndex(0);
	    anmSprBear.stopAnimation();
	    anmSprBear.setPosition(621, 336);
	    anmSprBear.setHasHat(false);
	    anmSprBear.setOnTree(false);
	    anmSprBear.setCurrentTileIndex(0);
	    anmSprCat.stopAnimation();
	    anmSprCat.setPosition(80, 479);
	    anmSprCat.setHasHat(false);
	    anmSprCat.setOnTree(false);
	    anmSprCat.setCurrentTileIndex(0);
	    resetTree();
	    countFlagOnTree = 0;
	    arrListHukiOntree.add(arrHuKiOnTree_1);
	    arrListHukiOntree.add(arrHuKiOnTree_2);
	    arrListHukiOntree.add(arrHuKiOnTree_3);
	    arrListHukiOntree.add(arrHuKiOnTree_4);
	    arrListHukiOntree.add(arrHuKiOnTree_5);
	    arrListMagoiOnTree.add(arrMagoiOnTree_1);
	    arrListMagoiOnTree.add(arrMagoiOnTree_2);
	    arrListMagoiOnTree.add(arrMagoiOnTree_3);
	    arrListMagoiOnTree.add(arrMagoiOnTree_4);
	    arrListMagoiOnTree.add(arrMagoiOnTree_5);
	    
	    arrListHigoiOnTree.add(arrHigoiOnTree_1);
	    arrListHigoiOnTree.add(arrHigoiOnTree_2);
	    arrListHigoiOnTree.add(arrHigoiOnTree_3);
	    arrListHigoiOnTree.add(arrHigoiOnTree_4);
	    arrListHigoiOnTree.add(arrHigoiOnTree_5);
	    
	    arrListKogoiAOnTree.add(arrKogoiAOnTree_1);
	    arrListKogoiAOnTree.add(arrKogoiAOnTree_2);
	    arrListKogoiAOnTree.add(arrKogoiAOnTree_3);
	    arrListKogoiAOnTree.add(arrKogoiAOnTree_4);
	    arrListKogoiAOnTree.add(arrKogoiAOnTree_5);
	    
	    arrListKogoiBOnTree.add(arrKogoiBOnTree_1);
	    arrListKogoiBOnTree.add(arrKogoiBOnTree_2);
	    arrListKogoiBOnTree.add(arrKogoiBOnTree_3);
	    arrListKogoiBOnTree.add(arrKogoiBOnTree_4);
	    arrListKogoiBOnTree.add(arrKogoiBOnTree_5);
	    
	    arrListOnTree.add(arrListHukiOntree);
	    arrListOnTree.add(arrListMagoiOnTree);
	    arrListOnTree.add(arrListHigoiOnTree);
	    arrListOnTree.add(arrListKogoiAOnTree);
	    arrListOnTree.add(arrListKogoiBOnTree);
	    
	    arrListRedBoyOnTree.add(arrRedboyOnHuki);
	    arrListRedBoyOnTree.add(arrRedboyOnMagoi);
	    arrListRedBoyOnTree.add(arrRedboyOnHigoi);
	    arrListRedBoyOnTree.add(arrRedboyOnKogoiA);
	    arrListRedBoyOnTree.add(arrRedboyOnKogoiB);
	    
	    arrListYellowBoyOnTree.add(arrYellowBoyOnHuki);
	    arrListYellowBoyOnTree.add(arrYellowBoyOnMagoi);
	    arrListYellowBoyOnTree.add(arrYellowBoyOnHigoi);
	    arrListYellowBoyOnTree.add(arrYellowBoyOnKogoiA);
	    arrListYellowBoyOnTree.add(arrYellowBoyOnKogoiB);
	    
	    arrListBearOnTree.add(arrBearOnHuki);
	    arrListBearOnTree.add(arrBearOnMagoi);
	    arrListBearOnTree.add(arrBearOnHigoi);
	    arrListBearOnTree.add(arrBearOnKogoiA);
	    arrListBearOnTree.add(arrBearOnKogoiB);
	    
	    arrListCatOnTree.add(arrCatOnHuki);
	    arrListCatOnTree.add(arrCatOnMagoi);
	    arrListCatOnTree.add(arrCatOnHigoi);
	    arrListCatOnTree.add(arrCatOnKogoiA);
	    arrListCatOnTree.add(arrCatOnKogoiB);
	    
	    listAnimalOnTree.add(arrListRedBoyOnTree);
	    listAnimalOnTree.add(arrListYellowBoyOnTree);
	    listAnimalOnTree.add(arrListBearOnTree);
	    listAnimalOnTree.add(arrListCatOnTree);
	    
	    indexFirst = -1;
	    indexSecond = -1;
	    indexThrew = -1;
	    indexFour = -1;
	    this.anmSprKumo
	    .registerEntityModifier(kumoModifier = new LoopEntityModifier(
	            new SequenceEntityModifier(new MoveXModifier(2.0f,
	                    this.anmSprKumo.getX(),
	                    this.anmSprKumo.getX() + 50, EaseQuadOut
	                    .getInstance()), new MoveXModifier(
	                            2.0f, this.anmSprKumo.getX() + 50,
	                            this.anmSprKumo.getX(), EaseQuadOut
	                            .getInstance()))));
		Log.d(TAG, "================= onResumeGame ==============="+ System.currentTimeMillis());
		super.onResumeGame();

	}

	@Override
	public void onPauseGame() {
		Log.d(TAG, "================= onPauseGame ===============");
		super.onPauseGame();
		return;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (this.sprYaguruma.contains(pX, pY)
					&& this.sprYaguruma.isVisible()) {
				if (this.sprYaguruma.isVisible()) {
					touchYaguruma();
				}
			} else if (this.sprYellowBall.contains(pX, pY)
					|| Polygon
							.contains(pX, pY, pxPolygonTree, pyPolygonTree, 4)) {
				if (isResetTree) {
					mp3Syu2.play();
					resetTree();
				}
			} else if (this.anmSprKumo.contains(pX, pY)) {
				touchKumo();
			} else if (this.sprCycle.contains(pX, pY)) {
				touchCycle();
			} else if (this.anmSprMogura.contains(pX, pY)) {
				touchMogura();
			} else if (this.anmSprTanpopoOne.contains(pX, pY)) {
				touchTanpopo(anmSprTanpopoOne, sprTanpopoOne, 0);
			} else if (this.anmSprTanpopoTwo.contains(pX, pY)) {
				touchTanpopo(anmSprTanpopoTwo, sprTanpopoTwo, 1);
			} else if (this.anmSprTanpopoThree.contains(pX, pY)) {
				touchTanpopo(anmSprTanpopoThree, sprTanpopoThree, 2);
			} else if (this.anmSprTanpopoFour.contains(pX, pY)) {
				touchTanpopo(anmSprTanpopoFour, sprTanpopoFour, 3);
			}
		}
		return false;
	}

	@Override
	public void combineGimmic3WithAction() {
		touchCycle();
	}

	// ===========================================================
	// Touch Yaguruma
	// Yaguruma Rotation
	// ===========================================================
	private void touchYaguruma() {
		if (rotateYaguruma == null || rotateYaguruma.isFinished()) {
			mp3Kara.play();
			this.sprYaguruma
					.registerEntityModifier(rotateYaguruma = new RotationModifier(
							1.5f, 0, 360, EaseQuadOut.getInstance()));
		}
		return;
	}

	// ===========================================================
	// Touch Kumo, Alpha Kumo
	// ===========================================================
	private void touchKumo() {
		if (kumoAlphaModifier == null || kumoAlphaModifier.isFinished()) {
			mp3Pyu.play();
			anmSprKumo.setCurrentTileIndex(1);
			anmSprKumo
					.registerEntityModifier(kumoAlphaModifier = new AlphaModifier(
							1.0f, 1.0f, 0.0f, EaseQuadOut.getInstance()));
			if (kumoAlphaModifier != null) {
				kumoAlphaModifier.addModifierListener(this);
			}
		}

		return;
	}

	// ===========================================================
	// Touch Cycle
	// ===========================================================
	private void touchCycle() {
		if (cycleModifier == null || cycleModifier.isFinished()) {
			mp3Bell.play();
			this.sprCycle
					.registerEntityModifier(cycleModifier = new RotationAtModifier(
							3.5f, 0.0f, -67.0f, 160, 940, EaseQuadIn
									.getInstance()));
			if (cycleModifier != null) {
				cycleModifier.addModifierListener(this);
			}
		}
		return;
	}

	// ===========================================================
	// Touch Mogura
	// ===========================================================
	private void touchMogura() {
		if (!this.anmSprMogura.isAnimationRunning()) {
			mp3Powa2.play();
			long durations[] = { 900, 50 };
			int frames[] = { 1, 0 };
			this.anmSprMogura.animate(durations, frames, 0);
		}
		return;
	}

	// ===========================================================
	// Touch Tanpopo
	// ===========================================================
	private void touchTanpopo(final AnimatedSprite sprTanpopoTouch,
			final Sprite sprTanpopoFade, int index) {

		// sprTanpopoFade.setAlpha(1.0f);
		// sprTanpopoFade.setVisible(true);

		switch (index) {
		case 0:
			if (tanpopoOneModifier == null || tanpopoOneModifier.isFinished()) {
				long durations[] = { 300, 2600, 100 };
				int frames[] = { 0, 1, 2 };
				sprTanpopoTouch.animate(durations, frames, 0);
				// sprTanpopoFade.setBlendFunction(GL10.GL_SRC_ALPHA,
				// GL10.GL_ONE_MINUS_SRC_ALPHA);
				TimerHandler timerHandler = new TimerHandler(0.6f, false,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {

								sprTanpopoFade.setVisible(true);
							}
						});
				this.mEngine.registerUpdateHandler(timerHandler);
				sprTanpopoFade.setPosition(
						(float) (sprTanpopoTouch.getX() - sprTanpopoFade
								.getWidth() / 4), (float) (sprTanpopoTouch
								.getY() - sprTanpopoFade.getHeight()));
				mp3Kira.play();
				sprTanpopoFade
						.registerEntityModifier(tanpopoOneModifier = new SequenceEntityModifier(

						new DelayModifier(0.6f), new ParallelEntityModifier(
								new MoveModifier(2.5f, sprTanpopoFade.getX(),
										sprTanpopoFade.getX() + 200,
										sprTanpopoFade.getY(), sprTanpopoFade
												.getY() - 120),
								new AlphaModifier(2.5f, 0.0f, 1.0f))));
				if (tanpopoOneModifier != null) {
					tanpopoOneModifier.addModifierListener(this);
				}
			}
			break;
		case 1:
			if (tanpopoTwoModifier == null || tanpopoTwoModifier.isFinished()) {
				long durations[] = { 300, 2600, 50 };
				int frames[] = { 0, 1, 2 };
				sprTanpopoTouch.animate(durations, frames, 0);
				// sprTanpopoFade.setBlendFunction(GL10.GL_SRC_ALPHA,
				// GL10.GL_ONE_MINUS_SRC_ALPHA);
				TimerHandler timerHandler = new TimerHandler(0.6f, false,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {

								sprTanpopoFade.setVisible(true);
							}
						});
				this.mEngine.registerUpdateHandler(timerHandler);
				sprTanpopoFade.setPosition(
						(float) (sprTanpopoTouch.getX() - sprTanpopoFade
								.getWidth() / 4), (float) (sprTanpopoTouch
								.getY() - sprTanpopoFade.getHeight()));
				mp3Kira.play();
				sprTanpopoFade
						.registerEntityModifier(tanpopoTwoModifier = new SequenceEntityModifier(
								new DelayModifier(0.6f),
								new ParallelEntityModifier(new MoveModifier(
										2.5f, sprTanpopoFade.getX(),
										sprTanpopoFade.getX() + 200,
										sprTanpopoFade.getY(), sprTanpopoFade
												.getY() - 120),
										new AlphaModifier(2.5f, 0.0f, 1.0f))));
				if (tanpopoTwoModifier != null) {
					tanpopoTwoModifier.addModifierListener(this);
				}
			}
			break;
		case 2:
			if (tanpopoThreeModifier == null
					|| tanpopoThreeModifier.isFinished()) {
				long durations[] = { 300, 2600, 50 };
				int frames[] = { 0, 1, 2 };
				sprTanpopoTouch.animate(durations, frames, 0);
				// sprTanpopoFade.setBlendFunction(GL10.GL_SRC_ALPHA,
				// GL10.GL_ONE_MINUS_SRC_ALPHA);
				TimerHandler timerHandler = new TimerHandler(0.6f, false,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {

								sprTanpopoFade.setVisible(true);
							}
						});
				this.mEngine.registerUpdateHandler(timerHandler);
				sprTanpopoFade.setPosition(
						(float) (sprTanpopoTouch.getX() - sprTanpopoFade
								.getWidth() / 4), (float) (sprTanpopoTouch
								.getY() - sprTanpopoFade.getHeight()));
				mp3Kira.play();
				sprTanpopoFade
						.registerEntityModifier(tanpopoThreeModifier = new SequenceEntityModifier(
								new DelayModifier(0.6f),
								new ParallelEntityModifier(new MoveModifier(
										2.5f, sprTanpopoFade.getX(),
										sprTanpopoFade.getX() + 200,
										sprTanpopoFade.getY(), sprTanpopoFade
												.getY() - 120),
										new AlphaModifier(2.5f, 0.0f, 1.0f))));
				if (tanpopoThreeModifier != null) {
					tanpopoThreeModifier.addModifierListener(this);
				}
			}
			break;
		case 3:
			if (tanpopoFourModifier == null || tanpopoFourModifier.isFinished()) {
				long durations[] = { 300, 2600, 50 };
				int frames[] = { 0, 1, 2 };
				sprTanpopoTouch.animate(durations, frames, 0);
				// sprTanpopoFade.setBlendFunction(GL10.GL_SRC_ALPHA,
				// GL10.GL_ONE_MINUS_SRC_ALPHA);
				TimerHandler timerHandler = new TimerHandler(0.6f, false,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {

								sprTanpopoFade.setVisible(true);
							}
						});
				this.mEngine.registerUpdateHandler(timerHandler);
				sprTanpopoFade.setPosition(
						(float) (sprTanpopoTouch.getX() - sprTanpopoFade
								.getWidth() / 4), (float) (sprTanpopoTouch
								.getY() - sprTanpopoFade.getHeight()));
				mp3Kira.play();
				sprTanpopoFade
						.registerEntityModifier(tanpopoFourModifier = new SequenceEntityModifier(
								new DelayModifier(0.6f),
								new ParallelEntityModifier(new MoveModifier(
										2.5f, sprTanpopoFade.getX(),
										sprTanpopoFade.getX() + 200,
										sprTanpopoFade.getY(), sprTanpopoFade
												.getY() - 120),
										new AlphaModifier(2.5f, 0.0f, 1.0f))));
				if (tanpopoFourModifier != null) {
					tanpopoFourModifier.addModifierListener(this);
				}
			}
			break;
		default:
			break;
		}

		return;
	}

	// ===========================================================
	// Audience Animation
	// ===========================================================
	private void animateAudience() {
		this.anmSprAudience.stopAnimation();
		this.anmSprAudience.clearEntityModifiers();
		this.anmSprAudience.setPosition(97, 24);
		if (!this.anmSprAudience.isAnimationRunning()) {
			long durations[] = { 300, 300 };
			int frames[] = { 1, 0 };
			this.anmSprAudience.animate(durations, frames, 5, this);

			this.anmSprAudience.registerEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(new RotationAtModifier(0.6f,
							0.0f, -3.0f, 773, 1280), new RotationAtModifier(
							0.6f, -3.0f, 0.0f, 773, 1280))));
		}
		return;
	}

	// ===========================================================
	// Reset all on tree
	// ===========================================================
	private void resetTree() {

		Log.d(TAG, "------AFTER------");
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					if (arrAnmSprFlag[i] != null) {
						arrAnmSprFlag[i].stopAnimation();
						arrAnmSprFlag[i].detachSelf();
						arrAnmSprFlag[i].setVisible(false);
						arrAnmSprFlag[i].setPosition(-999, -999);
					}
				}

			}
		});

		if (anmSprRedBoy.isOnTree()) {
			anmSprRedBoy.stopAnimation();
			anmSprRedBoy.setPosition(703, 453);
			anmSprRedBoy.setHasHat(false);
			anmSprRedBoy.setOnTree(false);
			anmSprRedBoy.setCurrentTileIndex(0);
		}
		if (anmSprYellowBoy.isOnTree()) {
			anmSprYellowBoy.stopAnimation();
			anmSprYellowBoy.setPosition(165, 336);
			anmSprYellowBoy.setHasHat(false);
			anmSprYellowBoy.setOnTree(false);
			anmSprYellowBoy.setCurrentTileIndex(0);
		}
		if (anmSprBear.isOnTree()) {
			anmSprBear.stopAnimation();
			anmSprBear.setPosition(621, 336);
			anmSprBear.setHasHat(false);
			anmSprBear.setOnTree(false);
			anmSprBear.setCurrentTileIndex(0);
		}
		if (anmSprCat.isOnTree()) {
			anmSprCat.stopAnimation();
			anmSprCat.setPosition(80, 479);
			anmSprCat.setHasHat(false);
			anmSprCat.setOnTree(false);
			anmSprCat.setCurrentTileIndex(0);
		}
		this.sprBoundFlags.setZIndex(999);
		anmSprBear.setZIndex(998);
		// this.anmSprRedBoy.setZIndex(1001);
		this.mScene.sortChildren();
		sprYaguruma.setVisible(false);
		countFlagOnTree = 0;
		indexFirst = -1;
		indexSecond = -1;
		indexThrew = -1;
		indexFour = -1;
		isResetTree = false;
		setGimmicBringToFront();
	}

	// ===========================================================
	// Inner Class
	// ===========================================================

	/**
	 * All Object on Right
	 * 
	 * @index: index of Object
	 * @instance: an Instance of Object
	 * 
	 */
	class MySprite extends Sprite {

		private int index;
		private VertexBufferObjectManager mVertexBufferObjectManager;
		private MySprite instance;

		public int getIndex() {
			return this.index;
		}

		// ===========================================================
		// Contructor
		// ===========================================================
		public MySprite(float pX, float pY, ITextureRegion pTextureRegion,
				int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.mVertexBufferObjectManager = pVertexBufferObjectManager;
		}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			int action = pSceneTouchEvent.getAction();
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			/*
			 * Check if Object is Flag If All Flag drag on the Tree then return
			 * false
			 */
			if (this.index <= 4) {
				if (countFlagOnTree == 5) {
					return false;
				}
			}

			/*
			 * Check if Object is Food If All Animal drag on the Tree then
			 * return false
			 */
			else if (this.index == 5) {
				if (anmSprRedBoy.isOnTree() && anmSprYellowBoy.isOnTree()
						&& anmSprBear.isOnTree() && anmSprCat.isOnTree()) {
					return false;
				}
			}

			/*
			 * Check if Object is Yaguruma If Yaguruma is Visible then return
			 * false
			 */
			else if (this.index == 6) {
				if (sprYaguruma.isVisible()) {
					return false;
				}
			}

			/*
			 * Check if Object is hat If All Animal has hat then return false
			 */
			else if (this.index == 7) {
				if (anmSprRedBoy.isHasHat() && anmSprYellowBoy.isHasHat()
						&& anmSprBear.isHasHat() && anmSprCat.isHasHat()) {
					return false;
				}
			}

			switch (action) {
			case TouchEvent.ACTION_DOWN:

				/*
				 * Create A new Instance For Object and Attach it to Screen
				 */
				mp3Block.play();

				instance = new MySprite(arrPoiterRight[0][this.index],
						arrPoiterRight[1][this.index],
						arrTtrRRight[this.index], this.index,
						this.mVertexBufferObjectManager);
				instance.setVisible(true);
				Vol3Koinobori.this.mScene.attachChild(instance);

				break;
			case TouchEvent.ACTION_MOVE:

				/*
				 * Move instance created
				 */
			        if(instance!=null){
			            instance.setPosition(pX - this.getWidth() * 2 / 3,
						pY - this.getHeight() / 2);
			        }
				break;
			case TouchEvent.ACTION_UP:

				// Disable Instance
			        try{
			            instance.setVisible(false);
				/*
				 * Check if Object is Flag
				 */
				if (index <= 4) {

					/*
					 * Check if Polygon bound of tree contains instance
					 */
					if (Polygon.contains(
							(int) (instance.getX() + instance.getWidth()),
							(int) (instance.getY() + instance.getHeight()),
							pxPolygon, pyPolygon, 4)) {
						isResetTree = true;
						mp3Powa.play();
						// Call method audience Animate
						animateAudience();

						// Create Flag And Attach It On Tree
						switch (index) {
						case 0:
							Vol3Koinobori.this.arrTiledTtrRFlag[index] = getTiledTextureFromPacker(
							                Vol3KoinoboriPacker_2,
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[0],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[1],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[2]);
							break;
						case 1:
							Vol3Koinobori.this.arrTiledTtrRFlag[index] = getTiledTextureFromPacker(
							                Vol3KoinoboriPacker_2,
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[3],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[4],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[5]);
							break;
						case 2:
							Vol3Koinobori.this.arrTiledTtrRFlag[index] = getTiledTextureFromPacker(
							                Vol3KoinoboriPacker_2,
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[6],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[7],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[8]);
							break;
						case 3:
							Vol3Koinobori.this.arrTiledTtrRFlag[index] = getTiledTextureFromPacker(
							                Vol3KoinoboriPacker_2,
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[9],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[10],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[11]);
							break;
						case 4:
							Vol3Koinobori.this.arrTiledTtrRFlag[index] = getTiledTextureFromPacker(
							                Vol3KoinoboriPacker_2,
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[12],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[13],
									Vol3KoinoboriResource.ARR_RESOURCE_FLAG[14]);
							break;
						default:
							break;
						}
						// Vol3Koinobori.this.arrTiledTtrRFlag[index] =
						// getTiledTextureFromPacker(mItemTexturePack,
						// Vol3KoinoboriResource.ARR_RESOURCE_FLAG[index],
						// Vol3KoinoboriResource.ARR_RESOURCE_FLAG[index + 1],
						// Vol3KoinoboriResource.ARR_RESOURCE_FLAG[index + 2]);

						arrAnmSprFlag[countFlagOnTree] = new FlagAnimatedSprite(
								0, 0, arrTiledTtrRFlag[this.index], this.index,
								mVertexBufferObjectManager);
						arrAnmSprFlag[countFlagOnTree].setBlendFunction(
								GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

						arrAnmSprFlag[countFlagOnTree]
								.setIndexOnTree(countFlagOnTree);
						arrAnmSprFlag[countFlagOnTree].setVisible(true);
						arrAnmSprFlag[countFlagOnTree].setPosition(
								((int[][]) (arrListOnTree.get(index)
										.get(countFlagOnTree)))[0][0] * 2,
								((int[][]) (arrListOnTree.get(index)
										.get(countFlagOnTree)))[1][0] * 2);

						Vol3Koinobori.this.sprBoundFlags
								.attachChild(arrAnmSprFlag[countFlagOnTree]);
						Vol3Koinobori.this.mScene
								.registerTouchArea(arrAnmSprFlag[countFlagOnTree]);
						// End Create Flag And Attach It On Tree

						// Grand index For Flag
						switch (countFlagOnTree) {
						case 1:
							indexFirst = index;
							break;
						case 2:
							indexSecond = index;
							break;
						case 3:
							indexThrew = index;
							break;
						case 4:
							indexFour = index;
							break;
						default:
							break;
						}
						countFlagOnTree++;
					}
				}
				/*
				 * Check if Instance is Yaguruma
				 */
				else if (index == 6) {
					// Check if Polygon bound of Tree contains instance
					if (Polygon.contains(
							(int) (instance.getX() + instance.getWidth()),
							(int) (instance.getY() + instance.getHeight()),
							pxPolygon, pyPolygon, 4)) {
						mp3Powa.play();
						// Visible Yaguruma on Tree
						Vol3Koinobori.this.sprYaguruma.setVisible(true);
					}
				}
				/*
				 * Check if Instance is Food
				 */
				else if (index == 5) {
					Log.d(TAG, "COME HERE");

					// Check If Food drag to Red boy
					if (checkContains(anmSprRedBoy, 0, 0,
							(int) anmSprRedBoy.getWidth(),
							(int) anmSprRedBoy.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {

						// Check if Red boy not on Tree
						if (!anmSprRedBoy.isOnTree()) {
							// Check if Red boy has Hat
							if (!anmSprRedBoy.isAnimationRunning()
									&& (anmSprRedBoy.modifier == null || anmSprRedBoy.modifier
											.isFinished())) {
								mp3Powa.play();
								mp3Mogu.play();
								if (!anmSprRedBoy.isHasHat()) {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 1, 2, 1, 2, 1, 2, 0 };
									anmSprRedBoy.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();

												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								} else {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 5, 2, 5, 2, 5, 2, 8 };
									anmSprRedBoy.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();

												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								}

							}
						}
					}
					// Check if Food drag to Yellow Boy
					else if (checkContains(anmSprYellowBoy, 0, 0,
							(int) anmSprYellowBoy.getWidth(),
							(int) anmSprYellowBoy.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						// Check if Yellow Boy not On Tree
						if (!anmSprYellowBoy.isOnTree()) {
							// Check if Yellow Boy has Hat
							if (!anmSprYellowBoy.isAnimationRunning()
									&& (anmSprYellowBoy.modifier == null || anmSprYellowBoy.modifier
											.isFinished())) {
								mp3Powa.play();
								mp3Mogu.play();
								if (!anmSprYellowBoy.isHasHat()) {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 1, 2, 1, 2, 1, 2, 0 };
									anmSprYellowBoy.animate(durations, frames,
											0, new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();

												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								} else {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 5, 2, 5, 2, 5, 2, 8 };
									anmSprYellowBoy.animate(durations, frames,
											0, new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();
												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {
												}
											});
								}
							}
						}
					}
					// Check if Food drag to Bear
					else if (checkContains(anmSprBear, 0, 0,
							(int) anmSprBear.getWidth(),
							(int) anmSprBear.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						// Check if Bear not On Tree
						if (!anmSprBear.isOnTree()) {
							// Check if Bear has Hat
							if (!anmSprBear.isAnimationRunning()
									&& (anmSprBear.modifier == null || anmSprBear.modifier
											.isFinished())) {
								mp3Powa.play();
								mp3Mogu.play();
								if (!anmSprBear.isHasHat()) {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 1, 2, 1, 2, 1, 2, 0 };
									anmSprBear.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();
												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {
												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								} else {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 5, 2, 5, 2, 5, 2, 8 };
									anmSprBear.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();
												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {
												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								}
							}
						}
					}
					// Check if Food drag to Cat
					else if (checkContains(anmSprCat, 0, 0,
							(int) anmSprCat.getWidth(),
							(int) anmSprCat.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						// Check if Cat not On Tree
						if (!anmSprCat.isOnTree()) {
							// Check if Cat Has hat
							if (!anmSprCat.isAnimationRunning()
									&& (anmSprCat.modifier == null || anmSprCat.modifier
											.isFinished())) {
								mp3Powa.play();
								mp3Mogu.play();
								if (!anmSprCat.isHasHat()) {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 1, 2, 1, 2, 1, 2, 0 };
									anmSprCat.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();
												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								} else {
									long durations[] = { 400, 400, 400, 400,
											400, 400, 50 };
									int frames[] = { 5, 2, 5, 2, 5, 2, 8 };
									anmSprCat.animate(durations, frames, 0,
											new IAnimationListener() {

												@Override
												public void onAnimationFinished(
														AnimatedSprite arg0) {
													mp3Oishi.play();

												}

												@Override
												public void onAnimationFrameChanged(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationLoopFinished(
														AnimatedSprite arg0,
														int arg1, int arg2) {

												}

												@Override
												public void onAnimationStarted(
														AnimatedSprite arg0,
														int arg1) {

												}
											});
								}
							}
						}
					}
				}
				/*
				 * Check If Instance is Hat
				 */
				else if (index == 7) {

					// Drag Instance to Red boy
					if (checkContains(anmSprRedBoy, 0, 0,
							(int) anmSprRedBoy.getWidth(),
							(int) anmSprRedBoy.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						// Check if Red Boy is On Tree
						if (!anmSprRedBoy.isAnimationRunning()
								&& (anmSprRedBoy.modifier == null || anmSprRedBoy.modifier
										.isFinished())) {
							mp3Powa.play();
							if (anmSprRedBoy.isOnTree()) {
								// Check If Red Boy on Tree Contains Instance
								if (checkContains(anmSprRedBoy, 0,
										(int) anmSprRedBoy.getHeight() / 2,
										(int) anmSprRedBoy.getWidth(),
										(int) anmSprRedBoy.getHeight(),
										(int) (instance.getX() + instance
												.getWidth() / 2),
										(int) (instance.getY() + instance
												.getHeight() / 2))) {
									anmSprRedBoy.setCurrentTileIndex(3);
									anmSprRedBoy.setHasHat(true);
								}
							} else {
								anmSprRedBoy.setCurrentTileIndex(8);
								anmSprRedBoy.setHasHat(true);
							}
						}
					}
					// Check if Yellow Boy is On Tree
					else if (checkContains(anmSprYellowBoy, 0, 0,
							(int) anmSprYellowBoy.getWidth(),
							(int) anmSprYellowBoy.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						if (!anmSprYellowBoy.isAnimationRunning()
								&& (anmSprYellowBoy.modifier == null || anmSprYellowBoy.modifier
										.isFinished())) {
							mp3Powa.play();
							// Check If Yellow Boy on Tree Contains Instance
							if (anmSprYellowBoy.isOnTree()) {

								if (checkContains(anmSprYellowBoy, 0,
										(int) anmSprYellowBoy.getHeight() / 2,
										(int) anmSprYellowBoy.getWidth(),
										(int) anmSprYellowBoy.getHeight(),
										(int) (instance.getX() + instance
												.getWidth() / 2),
										(int) (instance.getY() + instance
												.getHeight() / 2))) {
									anmSprYellowBoy.setCurrentTileIndex(3);
									anmSprYellowBoy.setHasHat(true);
								}
							} else {
								anmSprYellowBoy.setCurrentTileIndex(8);
								anmSprYellowBoy.setHasHat(true);
							}
						}

					}
					// Check if Bear is On Tree
					else if (checkContains(anmSprBear, 0, 0,
							(int) anmSprBear.getWidth(),
							(int) anmSprBear.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						if (!anmSprBear.isAnimationRunning()
								&& (anmSprBear.modifier == null || anmSprBear.modifier
										.isFinished())) {
							mp3Powa.play();
							// Check If Bear on Tree Contains Instance
							if (anmSprBear.isOnTree()) {
								if (checkContains(anmSprBear, 0,
										(int) anmSprBear.getHeight() / 2,
										(int) anmSprBear.getWidth(),
										(int) anmSprBear.getHeight(),
										(int) (instance.getX() + instance
												.getWidth() / 2),
										(int) (instance.getY() + instance
												.getHeight() / 2))) {
									anmSprBear.setCurrentTileIndex(3);
									anmSprBear.setHasHat(true);
								}
							} else {
								anmSprBear.setCurrentTileIndex(8);
								anmSprBear.setHasHat(true);
							}
						}
					}

					// Check if Cat is On Tree
					else if (checkContains(anmSprCat, 0, 0,
							(int) anmSprCat.getWidth(),
							(int) anmSprCat.getHeight(),
							(int) (instance.getX() + instance.getWidth() / 2),
							(int) (instance.getY() + instance.getHeight() / 2))) {
						if (!anmSprCat.isAnimationRunning()
								&& (anmSprCat.modifier == null || anmSprCat.modifier
										.isFinished())) {
							mp3Powa.play();
							// Check If Cat on Tree Contains Instance
							if (anmSprCat.isOnTree()) {
								if (checkContains(anmSprCat, 0,
										(int) anmSprCat.getHeight() / 2,
										(int) anmSprCat.getWidth(),
										(int) anmSprCat.getHeight(),
										(int) (instance.getX() + instance
												.getWidth() / 2),
										(int) (instance.getY() + instance
												.getHeight() / 2))) {
									anmSprCat.setCurrentTileIndex(3);
									anmSprCat.setHasHat(true);
								}
							} else {
								anmSprCat.setCurrentTileIndex(8);
								anmSprCat.setHasHat(true);
							}
						}
					}
				}
			}catch (Exception e) {
                            // TODO: handle exception
                        }
				break;
			default:
				break;
			}
			return true;
		}
	}

	/**
	 * Class Instance Of Flag
	 * 
	 * @index: index of Flag
	 * @indexOnTree: Index Of Flag when drag to tree
	 * @handler: Handler when Flag Change Resource
	 * @callback_1, callback_2, callback_3: Call Back when Flag Change Resource
	 * 
	 */
	class FlagAnimatedSprite extends AnimatedSprite {
		private int index;
		private int indexOnTree;
		private AnimalSprite animalSprite;
		@SuppressWarnings("unused")
		private VertexBufferObjectManager mVertexBufferObjectManager;

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}

		public void setIndexOnTree(int indexOnTree) {
			this.indexOnTree = indexOnTree;
		}

		public int getIndexOnTree() {
			return this.indexOnTree;
		}

		// ===========================================================
		// Contructor
		// ===========================================================
		public FlagAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.mVertexBufferObjectManager = pVertexBufferObjectManager;
			this.index = index;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();
			float pTouch[][] = new float[2][5];
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

				// Get Position Bound Touch Flag
				if (this.getIndex() == 0) {
					pTouch[0][0] = 28;
					pTouch[1][0] = 87;
					pTouch[0][1] = 406;
					pTouch[1][1] = 5;
					pTouch[0][2] = 407;
					pTouch[1][2] = 83;
					pTouch[0][3] = 48;
					pTouch[1][3] = 156;
					pTouch[0][4] = 28;
					pTouch[1][4] = 87;
				} else if (this.getIndex() == 1) {
					pTouch[0][0] = 25;
					pTouch[1][0] = 109;
					pTouch[0][1] = 388;
					pTouch[1][1] = 23;
					pTouch[0][2] = 387;
					pTouch[1][2] = 98;
					pTouch[0][3] = 40;
					pTouch[1][3] = 167;
					pTouch[0][4] = 25;
					pTouch[1][4] = 109;
				} else if (this.getIndex() == 2) {
					pTouch[0][0] = 23;
					pTouch[1][0] = 106;
					pTouch[0][1] = 313;
					pTouch[1][1] = 19;
					pTouch[0][2] = 331;
					pTouch[1][2] = 68;
					pTouch[0][3] = 43;
					pTouch[1][3] = 157;
					pTouch[0][4] = 22;
					pTouch[1][4] = 109;
				} else if (this.getIndex() == 3) {
					pTouch[0][0] = 23;
					pTouch[1][0] = 93;
					pTouch[0][1] = 264;
					pTouch[1][1] = 4;
					pTouch[0][2] = 288;
					pTouch[1][2] = 53;
					pTouch[0][3] = 38;
					pTouch[1][3] = 135;
					pTouch[0][4] = 23;
					pTouch[1][4] = 93;
				} else if (this.getIndex() == 4) {
					pTouch[0][0] = 20;
					pTouch[1][0] = 79;
					pTouch[0][1] = 221;
					pTouch[1][1] = 1;
					pTouch[0][2] = 235;
					pTouch[1][2] = 45;
					pTouch[0][3] = 35;
					pTouch[1][3] = 116;
					pTouch[0][4] = 20;
					pTouch[1][4] = 79;
				}
				// End Get Position Bound Flags

				/*
				 * Check If Animal Drag To Polygon Bound Tree
				 */
				if (checkContainsPolygon(this, pTouch, 4, pX, pY)) {
					// Flags Animation
					if (!FlagAnimatedSprite.this.isAnimationRunning()) {

						// sprBoundFlags.setZIndex(1000);
						// mScene.sortChildren();
						mp3Basa.play();
						int frames[] = { 1, 2, 0 };
						int framesAnimals[] = new int[3];

						long durations[] = { 500, 500, 100 };

						Log.d(TAG, "INDEX:" + this.index);
						Log.d(TAG, "INDEX_ON_TREE:" + this.indexOnTree);

						switch (this.indexOnTree) {
						case 1:
							animalSprite = anmSprRedBoy;
							break;
						case 2:
							animalSprite = anmSprYellowBoy;
							break;
						case 3:
							animalSprite = anmSprBear;
							break;
						case 4:
							animalSprite = anmSprCat;
							break;
						default:
							break;
						}
						// Check If Animal Drag To Tree
						if (animalSprite != null && animalSprite.isOnTree()) {
							// Check if Animal Has Hat
							if (animalSprite.isHasHat()) {
								framesAnimals[0] = 4;
								framesAnimals[1] = 4;
								framesAnimals[2] = 3;
							} else {
								framesAnimals[0] = 7;
								framesAnimals[1] = 7;
								framesAnimals[2] = 6;
							}

							animalSprite.animate(durations, framesAnimals, 0);
						}
						FlagAnimatedSprite.this.animate(durations, frames, 0);
						FlagAnimatedSprite.this
								.setPosition(
										((int[][]) (Vol3Koinobori.this.arrListOnTree
												.get(FlagAnimatedSprite.this
														.getIndex())
												.get(FlagAnimatedSprite.this
														.getIndexOnTree())))[0][1] * 2,
										((int[][]) (Vol3Koinobori.this.arrListOnTree
												.get(FlagAnimatedSprite.this
														.getIndex())
												.get(FlagAnimatedSprite.this
														.getIndexOnTree())))[1][1] * 2);
						if (FlagAnimatedSprite.this.getIndexOnTree() >= 1
								&& animalSprite.isOnTree()) {
							animalSprite
									.setPosition(
											((int[][]) (Vol3Koinobori.this.listAnimalOnTree
													.get(FlagAnimatedSprite.this
															.getIndexOnTree() - 1)
													.get(FlagAnimatedSprite.this
															.getIndex())))[0][1] * 2,
											((int[][]) (Vol3Koinobori.this.listAnimalOnTree
													.get(FlagAnimatedSprite.this
															.getIndexOnTree() - 1)
													.get(FlagAnimatedSprite.this
															.getIndex())))[1][1] * 2);
						}
						Vol3Koinobori.this.mEngine
								.registerUpdateHandler(new TimerHandler(0.5f,
										false, new ITimerCallback() {

											@Override
											public void onTimePassed(
													TimerHandler pTimerHandler) {

												Log.d(TAG, "Come Here");
												FlagAnimatedSprite.this
														.setPosition(
																((int[][]) (Vol3Koinobori.this.arrListOnTree
																		.get(FlagAnimatedSprite.this
																				.getIndex())
																		.get(FlagAnimatedSprite.this
																				.getIndexOnTree())))[0][2] * 2,
																((int[][]) (Vol3Koinobori.this.arrListOnTree
																		.get(FlagAnimatedSprite.this
																				.getIndex())
																		.get(FlagAnimatedSprite.this
																				.getIndexOnTree())))[1][2] * 2);
												if (FlagAnimatedSprite.this
														.getIndexOnTree() >= 1
														&& animalSprite
																.isOnTree()) {
													animalSprite
															.setPosition(
																	((int[][]) (Vol3Koinobori.this.listAnimalOnTree
																			.get(FlagAnimatedSprite.this
																					.getIndexOnTree() - 1)
																			.get(FlagAnimatedSprite.this
																					.getIndex())))[0][2] * 2,
																	((int[][]) (Vol3Koinobori.this.listAnimalOnTree
																			.get(FlagAnimatedSprite.this
																					.getIndexOnTree() - 1)
																			.get(FlagAnimatedSprite.this
																					.getIndex())))[1][2] * 2);
												}
											}
										}));
						Vol3Koinobori.this.mEngine
								.registerUpdateHandler(new TimerHandler(1.0f,
										false, new ITimerCallback() {

											@Override
											public void onTimePassed(
													TimerHandler pTimerHandler) {
												Log.d(TAG, "Come Here 2");
												FlagAnimatedSprite.this
														.setPosition(
																((int[][]) (Vol3Koinobori.this.arrListOnTree
																		.get(FlagAnimatedSprite.this
																				.getIndex())
																		.get(FlagAnimatedSprite.this
																				.getIndexOnTree())))[0][0] * 2,
																((int[][]) (Vol3Koinobori.this.arrListOnTree
																		.get(FlagAnimatedSprite.this
																				.getIndex())
																		.get(FlagAnimatedSprite.this
																				.getIndexOnTree())))[1][0] * 2);
												if (FlagAnimatedSprite.this
														.getIndexOnTree() >= 1
														&& animalSprite
																.isOnTree()) {
													animalSprite
															.setPosition(
																	((int[][]) (Vol3Koinobori.this.listAnimalOnTree
																			.get(FlagAnimatedSprite.this
																					.getIndexOnTree() - 1)
																			.get(FlagAnimatedSprite.this
																					.getIndex())))[0][0] * 2,
																	((int[][]) (Vol3Koinobori.this.listAnimalOnTree
																			.get(FlagAnimatedSprite.this
																					.getIndexOnTree() - 1)
																			.get(FlagAnimatedSprite.this
																					.getIndex())))[1][0] * 2);
												}
											}
										}));
					}
				} else {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Instance Of Aimal
	 * 
	 * @Index: Index Of Object
	 * @hasHat: Object Has Hat
	 * @onTree: Drag To Tree
	 * @isTouch: Check If Object Touched
	 */
	class AnimalSprite extends AnimatedSprite {
		private int index;
		private boolean hasHat;
		private boolean onTree;
		private boolean isTouch = false;
		private SequenceEntityModifier modifier;
		@SuppressWarnings("unused")
		private VertexBufferObjectManager mVertexBufferObjectManager;

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}

		public boolean isHasHat() {
			return hasHat;
		}

		public void setHasHat(boolean hasHat) {
			this.hasHat = hasHat;
		}

		public boolean isOnTree() {
			return onTree;
		}

		public void setOnTree(boolean onTree) {
			this.onTree = onTree;
		}

		// ===========================================================
		// Contructor
		// ===========================================================
		public AnimalSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.mVertexBufferObjectManager = pVertexBufferObjectManager;
			this.onTree = false;
		}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			int action = pSceneTouchEvent.getAction();
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			int pX1 = 0, pY1 = 0, pX2 = (int) this.getWidth(), pY2 = (int) this
					.getHeight();
			if (this.getCurrentTileIndex() == 3
					|| this.getCurrentTileIndex() == 6) {
				// Check If Object drag To Tree, Get pY Touch
				pY1 = (int) this.getHeight() / 2;
			}

			switch (action) {
			case TouchEvent.ACTION_DOWN:

				// Check If Object Touched
				if (!this.isAnimationRunning()) {
					if (checkContains(this, pX1, pY1, pX2, pY2, (int) pX,
							(int) pY)) {
						// Check if Red Boy not on Flag
						if (this.index == 0 && indexFirst == -1 && !isResetTree) {
							Log.d(TAG, this.index + "");

							if (this.modifier == null
									|| this.modifier.isFinished()) {
								if (!this.isOnTree()) {
									mp3Pomi.play();
								}
								this.modifier = new SequenceEntityModifier(
										new MoveYModifier(0.5f, this.getY(),
												this.getY() - 40),
										new MoveYModifier(0.5f,
												this.getY() - 40, this.getY()));
								this.registerEntityModifier(this.modifier);
							}
							return false;
						}

						// Check if Yellow Boy not on Flag
						if (this.index == 1 && indexSecond == -1
								&& !isResetTree) {
							Log.d(TAG, this.index + "");

							if (this.modifier == null
									|| this.modifier.isFinished()) {
								if (!this.isOnTree()) {
									mp3Pomi.play();
								}
								this.modifier = new SequenceEntityModifier(
										new MoveYModifier(0.5f, this.getY(),
												this.getY() - 40),
										new MoveYModifier(0.5f,
												this.getY() - 40, this.getY()));
								this.registerEntityModifier(this.modifier);
							}
							return false;
						}

						// Check if Bear not on Flag
						if (this.index == 2 && indexThrew == -1 && !isResetTree) {
							Log.d(TAG, this.index + "");

							if (this.modifier == null
									|| this.modifier.isFinished()) {
								if (!this.isOnTree()) {
									mp3Pomi.play();
								}
								this.modifier = new SequenceEntityModifier(
										new MoveYModifier(0.5f, this.getY(),
												this.getY() - 40),
										new MoveYModifier(0.5f,
												this.getY() - 40, this.getY()));
								this.registerEntityModifier(this.modifier);
							}
							return false;
						}

						// Check if Cat not on Flag
						if (this.index == 3 && indexFour == -1 && !isResetTree) {
							Log.d(TAG, this.index + "");

							if (this.modifier == null
									|| this.modifier.isFinished()) {
								if (!this.isOnTree()) {
									mp3Pomi.play();
								}
								this.modifier = new SequenceEntityModifier(
										new MoveYModifier(0.5f, this.getY(),
												this.getY() - 40),
										new MoveYModifier(0.5f,
												this.getY() - 40, this.getY()));
								this.registerEntityModifier(this.modifier);
							}
							return false;
						}

						isTouch = true;
					}
				}
				if (isTouch) {
					// Set Tile For Object
					if (!this.isOnTree()) {
						mp3Boyon.play();
					}
					if (!hasHat) {
						this.setCurrentTileIndex(6);
					} else {
						this.setCurrentTileIndex(3);
					}
				}
				break;
			case TouchEvent.ACTION_MOVE:
				if (isTouch) {
					// Move Object
					this.setPosition(pX - this.getWidth() * 2 / 3,
							pY - this.getHeight() / 2);
					this.setZIndex(sprBoundFlags.getZIndex() + 10);
					if (!anmSprBear.isOnTree() && this.index != 2) {
						anmSprBear.setZIndex(sprBoundFlags.getZIndex() - 1);
					}
					Vol3Koinobori.this.mScene.sortChildren();
					setGimmicBringToFront();
				}
				break;
			case TouchEvent.ACTION_UP:

				if (isTouch) {
					if (countFlagOnTree > 1
							&& arrAnmSprFlag[this.index + 1] != null
							&& arrAnmSprFlag[this.index + 1].isVisible()) {
						// Reset Position When Object Drag To Tree
						if (Polygon.contains(
								(int) (this.getX() + this.getWidth() / 2),
								(int) (this.getY() + this.getHeight() / 2),
								pxPolygonAnimal, pyPolygonAnimal, 4)) {
							// Call Audience Method
							if (!this.isOnTree()) {
								animateAudience();
								mp3Oho.play();
							}
							switch (this.index) {
							case 0:
								this.setPosition(
										(arrListRedBoyOnTree.get(indexFirst))[0][0] * 2,
										(arrListRedBoyOnTree.get(indexFirst))[1][0] * 2);
								break;
							case 1:
								this.setPosition((arrListYellowBoyOnTree
										.get(indexSecond))[0][0] * 2,
										(arrListYellowBoyOnTree
												.get(indexSecond))[1][0] * 2);
								break;
							case 2:
								this.setPosition(
										(arrListBearOnTree.get(indexThrew))[0][0] * 2,
										(arrListBearOnTree.get(indexThrew))[1][0] * 2);
								break;
							case 3:
								this.setPosition(
										(arrListCatOnTree.get(indexFour))[0][0] * 2,
										(arrListCatOnTree.get(indexFour))[1][0] * 2);
								break;
							default:
								break;
							}
							this.onTree = true;

							// Set Tile For Object
							if (hasHat) {
								this.setCurrentTileIndex(3);
							} else {
								this.setCurrentTileIndex(6);
							}
						} else {
							// Reset Position And zIndex For Object
							this.onTree = false;
							if (!anmSprBear.isOnTree()) {
								anmSprBear
										.setZIndex(sprBoundFlags.getZIndex() - 1);
							}
							this.setZIndex(sprBoundFlags.getZIndex() - 10);
							// Vol3Koinobori.this.sprBoundFlags.setZIndex(999);
							Vol3Koinobori.this.mScene.sortChildren();
							setGimmicBringToFront();
							this.setPosition(arrPointerAnimals[0][this.index],
									arrPointerAnimals[1][this.index]);

							// Set Tile For Object
							if (hasHat) {
								this.setCurrentTileIndex(8);
							} else {
								this.setCurrentTileIndex(0);
							}
						}
					} else {
						// Reset Position And zIndex For Object
						this.onTree = false;
						if (!anmSprBear.isOnTree()) {
							anmSprBear.setZIndex(sprBoundFlags.getZIndex() - 1);
						}
						this.setZIndex(sprBoundFlags.getZIndex() - 10);
						// Vol3Koinobori.this.sprBoundFlags.setZIndex(999);
						Vol3Koinobori.this.mScene.sortChildren();
						setGimmicBringToFront();
						this.setPosition(arrPointerAnimals[0][this.index],
								arrPointerAnimals[1][this.index]);

						// Set Tile For Object
						if (hasHat) {
							this.setCurrentTileIndex(8);
						} else {
							this.setCurrentTileIndex(0);
						}
					}
				}
				isTouch = false;
				break;
			default:
				break;
			}
			return true;
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
								/ (polyY[j] - polyY[i]) + polyX[i]))
					c = !c;
			}
			return c;
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		// TODO Auto-generated method stub

		if (pModifier.equals(kumoAlphaModifier)) {
			anmSprKumo.setCurrentTileIndex(0);
			anmSprKumo.setAlpha(1.0f);
		}
		if (pModifier.equals(cycleModifier)) {
			Log.d(TAG, "Cycle Modifier End");
			this.sprCycle.setRotation(0.0f);
			this.sprCycle.setPosition(670, 6);

		}
		if (pModifier.equals(tanpopoOneModifier)) {
			Log.d(TAG, "END ONE");
			this.sprTanpopoOne.setAlpha(1.0f);
			this.sprTanpopoOne.setVisible(false);
			this.anmSprTanpopoOne.setCurrentTileIndex(0);
		}
		if (pModifier.equals(tanpopoTwoModifier)) {
			Log.d(TAG, "END TWO");
			this.sprTanpopoTwo.setAlpha(1.0f);
			this.sprTanpopoTwo.setVisible(false);
			this.anmSprTanpopoTwo.setCurrentTileIndex(0);
		}
		if (pModifier.equals(tanpopoThreeModifier)) {
			Log.d(TAG, "END THREE");
			this.sprTanpopoThree.setAlpha(1.0f);
			this.sprTanpopoThree.setVisible(false);
			this.anmSprTanpopoThree.setCurrentTileIndex(0);
		}
		if (pModifier.equals(tanpopoFourModifier)) {
			Log.d(TAG, "END FOUR");
			this.sprTanpopoFour.setAlpha(1.0f);
			this.sprTanpopoFour.setVisible(false);
			this.anmSprTanpopoFour.setCurrentTileIndex(0);
		}
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(anmSprAudience)) {
			this.anmSprAudience.clearEntityModifiers();
			this.anmSprAudience.setPosition(97, 24);
		}

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

}
