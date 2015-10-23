package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3AburahamuResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseQuadOut;

import android.util.Log;

public class Vol3Aburahamu extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity> {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_ABURAHAMU";

	/**
	 * Food Status
	 */
	private final int FOOD_APPLE = 0, FOOD_NIWATORI = 2, FOOD_MUGI = 1,
			FOOD_COW = 3;

	// ===========================================================
	// Fields
	// ===========================================================

	private TextureRegion ttrRBackground, ttrRTree, ttrRLeaf, ttrRSmoke,
			ttrRTableMugi, ttrRTableNiwatori, ttrRTableCow, ttrRSaku;

	private Sprite sTree, sLeaf, sSmoke, sTableMugi, sTableNiwatori, sTableCow,
			sSaku;

	private TextureRegion arrAppleTtrR[] = new TextureRegion[7],
			arrMugiTtrR[] = new TextureRegion[7],
			arrNiwatoriTtrR[] = new TextureRegion[7],
			arrCowTtrR[] = new TextureRegion[7],
			arrKumoTtrR[] = new TextureRegion[8];

	private TiledTextureRegion arrHmanTiledTtrR[] = new TiledTextureRegion[7],
			ttrRMugi, ttrRNiwatori, ttrRCow, ttrRUsagi;

	private Sprite arrSApple[] = new Sprite[7], arrSMugi[] = new Sprite[7],
			arrSNiwatori[] = new Sprite[7], arrSCow[] = new Sprite[7],
			arrSKumo[] = new Sprite[8];

	private AnimatedSprite arrAnmSHuman[] = new AnimatedSprite[7], anmSMugi,
			anmSNiwatori, anmSCow, anmSUsagi;

	/**
	 * All status food and give food
	 */
	private boolean isReset = false, isTouchLeaf, isSleep, isTouchFood,
			isTouchHuman, arrIsHumanGiveFood[] = { false, false, false, false,
					false, false, false }, arrIsTouchFood[] = { false, false,
					false, false, false, false, false }, arrIsTouchHuman[] = {
					true, true, true, true, true, true, true };

	/**
	 * Food Modifier
	 */
	private ParallelEntityModifier foodModifier[] = new ParallelEntityModifier[7],
			smokeModifier;

	private SequenceEntityModifier leafModifer;

	/**
	 * Count Human Give Food
	 */
	private int countHumanGiveFood, statusFood;

	/**
	 * Frame Give Food
	 */
	private int arrFrameGiveFood[][] = { { 5, 6, 7, 8, 9 }, { 5, 6, 7, 8, 9 },
			{ 5, 6, 7, 8, 9 }, { 5, 6, 7, 8, 9 }, { 6, 7, 8, 9, 10 },
			{ 6, 7, 8, 9, 10 }, { 8, 9, 10, 11, 12 } };

	/**
	 * Frame Human Touch
	 */
	private int arrFrameHumanTouch[][] = { { 6, 7, 8, 9, 1 },
			{ 6, 7, 8, 9, 1 }, { 6, 7, 8, 9, 1 }, { 6, 7, 8, 9, 1 },
			{ 7, 8, 9, 10, 2 }, { 7, 8, 9, 10, 2 }, { 9, 10, 11, 12, 4 } };

	/**
	 * Frame Human Sleep
	 */
	private int arrFrameHumanSleep[][] = { { 2, 3, 4 }, { 2, 3, 4 },
			{ 2, 3, 4 }, { 2, 3, 4 }, { 3, 4, 5 }, { 3, 4, 5 }, { 5, 6, 7 } };

	/**
	 * Array contains Smoke Point
	 */
	private int arrPointSmoke[][] = { { 704, 83, 708, 0 }, { 12, 442, 423, 0 } };

	/**
	 * Array contains Kumo Point
	 */
	private int arrPointKumo[][] = { { 37, 89, 227, 351, 439, 576, 812, 889 },
			{ 40, 58, 13, 30, 14, 31, 41, 18 } };

	/**
	 * Array contains Apple Point
	 */
	private float arrPointApple[][] = { { 82, 56, 87, 134, 173, 209, 182 },
			{ 104, 159, 238, 133, 61, 155, 226 } };

	/**
	 * Array contains Mugi Point
	 */
	private float arrPointMugi[][] = { { 687, 731, 776, 820, 709, 753, 798 },
			{ 32, 32, 32, 32, 75, 75, 75 } };

	/**
	 * Array contains Niwatori Point
	 */
	private float arrPointNiwatori[][] = { { 83, 129, 174, 57, 102, 147, 193 },
			{ 442, 442, 442, 491, 489, 489, 491 } };

	/**
	 * Array contains Cow Point
	 */
	private float arrPointCow[][] = { { 746, 792, 838, 720, 769, 818, 867 },
			{ 437, 437, 437, 478, 490, 490, 478 } };

	/**
	 * Array contains Human Point
	 */
	private float arrPointHuman[][] = { { 189, 263, 349, 422, 502, 578, 676 },
			{ 252, 70, 275, 63, 294, 69, 138 } };

	/**
	 * All Sound
	 */
	private Sound mp3Kasa2, mp3Bowan2, mp3Pomi, mp3Sawa, mp3Niwatori3, mp3Ushi,
			mp3Byon, mp3Migite, mp3Hidadrite, mp3Migiashi, mp3Hidariashi,
			mp3Atama, mp3Oshiri, mp3Mawaru, mp3Neiki, mp3Pan, mp3Ringo,
			mp3Donatu, mp3Chikin, mp3Ice;

	private TexturePack mBackgroundItemsTexturePack;
	private TexturePack mKodomo_9_10TexturePack;
	private TexturePack mKodomo_11_12TexturePack;
	private TexturePack mKodomo_13_14_15TexturePack;

	private TexturePackTextureRegionLibrary mBackgroundItemsTexturePackTextureRegionLibrary;
	private TimerHandler timeUpdate;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("aburahamu/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("aburahamu/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		mBackgroundItemsTexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "aburahamu/gfx/")
				.load("background_items.xml");
		mBackgroundItemsTexturePack.loadTexture();
		mBackgroundItemsTexturePackTextureRegionLibrary = mBackgroundItemsTexturePack
				.getTexturePackTextureRegionLibrary();

		mKodomo_9_10TexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "aburahamu/gfx/")
				.load("kodomo_9_10.xml");
		mKodomo_9_10TexturePack.loadTexture();

		mKodomo_11_12TexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "aburahamu/gfx/")
				.load("kodomo_11_12.xml");
		mKodomo_11_12TexturePack.loadTexture();

		mKodomo_13_14_15TexturePack = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "aburahamu/gfx/")
				.load("kodomo_13_14_15.xml");
		mKodomo_13_14_15TexturePack.loadTexture();
		this.ttrRBackground = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_18_IPHONE_HAIKEI_ID);

		// ///////////// Load Tree /////////////////////
		this.ttrRTree = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_04_1_IPHONE_APPLE_ID);

		// ///////////// Load Leaf /////////////////////
		this.ttrRLeaf = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_04_2_IPHONE_APPLE_ID);

		// //////////// Load Saku ///////////////////////
		this.ttrRSaku = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_16_IPHONE_SAKU_ID);

		// Load Smoke
		this.ttrRSmoke = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_05_4_IPHONE_MUGI_ID);

		// //////////// Load Kumo /////////////////////
		for (int i = 0; i < 8; i++) {
			this.arrKumoTtrR[i] = mBackgroundItemsTexturePackTextureRegionLibrary
					.get(Vol3AburahamuResource.ARR_KUMO[i]);
		}

		// //////////// Load Apple /////////////////////
		for (int i = 0; i < 7; i++) {

			this.arrAppleTtrR[i] = mBackgroundItemsTexturePackTextureRegionLibrary
					.get(Vol3AburahamuResource.ARR_APPLE[i]);

		}
		// //////////// Load Usagi ////////////////////
		this.ttrRUsagi = getTiledTextureFromPacker(mBackgroundItemsTexturePack,
				Vol3AburahamuResource.A14_08_1_IPHONE_USAGI_ID,
				Vol3AburahamuResource.A14_08_2_IPHONE_USAGI_ID);

		// //////////// Load Mugi ///////////////////
		this.ttrRMugi = getTiledTextureFromPacker(mBackgroundItemsTexturePack,
				Vol3AburahamuResource.A14_05_1_IPHONE_MUGI_ID,
				Vol3AburahamuResource.A14_05_2_IPHONE_MUGI_ID,
				Vol3AburahamuResource.A14_05_3_IPHONE_MUGI_ID,
				Vol3AburahamuResource.A14_05_4_IPHONE_MUGI_ID);

		ttrRTableMugi = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_05_5_IPHONE_MUGI_ID);

		for (int i = 0; i < 7; i++) {

			this.arrMugiTtrR[i] = mBackgroundItemsTexturePackTextureRegionLibrary
					.get(Vol3AburahamuResource.A14_05_6_IPHONE_MUGI_ID);
		}
		// Load Niwatori
		this.ttrRNiwatori = getTiledTextureFromPacker(
				mBackgroundItemsTexturePack,
				Vol3AburahamuResource.A14_06_1_IPHONE_NIWATORI_ID,
				Vol3AburahamuResource.A14_06_2_IPHONE_NIWATORI_ID);

		ttrRTableNiwatori = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_06_3_IPHONE_NIWATORI_ID);
		for (int i = 0; i < 7; i++) {

			this.arrNiwatoriTtrR[i] = mBackgroundItemsTexturePackTextureRegionLibrary
					.get(Vol3AburahamuResource.A14_06_4_IPHONE_NIWATORI_ID);

		}

		// Load Cow
		this.ttrRCow = getTiledTextureFromPacker(mBackgroundItemsTexturePack,
				Vol3AburahamuResource.A14_07_1_IPHONE_COW_ID,
				Vol3AburahamuResource.A14_07_2_IPHONE_COW_ID,
				Vol3AburahamuResource.A14_07_3_IPHONE_COW_ID);
		ttrRTableCow = mBackgroundItemsTexturePackTextureRegionLibrary
				.get(Vol3AburahamuResource.A14_07_4_IPHONE_COW_ID);

		for (int i = 0; i < 7; i++) {

			this.arrCowTtrR[i] = mBackgroundItemsTexturePackTextureRegionLibrary
					.get(Vol3AburahamuResource.A14_07_5_IPHONE_COW_ID);
		}

		// /////////// Load Human /////////////////////
		for (int i = 0; i < 7; i++) {

			switch (i) {
			case 0:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_9_10TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_9);
				break;
			case 1:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_9_10TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_10);
				break;
			case 2:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_11_12TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_11);
				break;
			case 3:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_11_12TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_12);
				break;
			case 4:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_13_14_15TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_13);
				break;

			case 5:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_13_14_15TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_14);
				break;
			case 6:
				this.arrHmanTiledTtrR[i] = getTiledTextureFromPacker(
						mKodomo_13_14_15TexturePack,
						Vol3AburahamuResource.ARR_HUMAN_15);
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// Set Backgroud For Scene
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground,
				getVertexBufferObjectManager())));

		// Add Kumo
		for (int i = 0; i < 8; i++) {
			this.arrSKumo[i] = new Sprite(arrPointKumo[0][i],
					arrPointKumo[1][i], this.arrKumoTtrR[i],
					getVertexBufferObjectManager());
			this.mScene.attachChild(arrSKumo[i]);
		}

		// Add Tree
		this.sTree = new Sprite(-20, 12, ttrRTree,
				getVertexBufferObjectManager());
		this.mScene.attachChild(sTree);

		// Add Leaf
		this.sLeaf = new Sprite(-20, 12, ttrRLeaf,
				getVertexBufferObjectManager());
		this.mScene.attachChild(sLeaf);

		// Add Mugi
		this.anmSMugi = new AnimatedSprite(704, 12, this.ttrRMugi,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSMugi);

		// Add Niwatori
		this.anmSNiwatori = new AnimatedSprite(28, 413, this.ttrRNiwatori,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSNiwatori);

		// Add Cow
		this.anmSCow = new AnimatedSprite(708, 423, this.ttrRCow,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSCow);

		// Add Smoke
		this.sSmoke = new Sprite(0, 0, this.ttrRSmoke,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.sSmoke);
		this.sSmoke.setVisible(false);

		// Add Mugi Table
		this.sTableMugi = new Sprite(664, 12, ttrRTableMugi,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.sTableMugi);
		this.sTableMugi.setVisible(false);

		// Add Saku
		this.sSaku = new Sprite(378, 160, ttrRSaku,
				getVertexBufferObjectManager());
		this.mScene.attachChild(sSaku);
		// Add Niwatori Table
		this.sTableNiwatori = new Sprite(48, 413, ttrRTableNiwatori,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.sTableNiwatori);
		this.sTableNiwatori.setVisible(false);

		// Add Cow Table
		this.sTableCow = new Sprite(708, 423, ttrRTableCow,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.sTableCow);
		this.sTableCow.setVisible(false);

		// Add Usagi
		this.anmSUsagi = new AnimatedSprite(790, 238, ttrRUsagi,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSUsagi);

		// Add Human
		for (int i = 0; i < 7; i++) {
			this.arrAnmSHuman[i] = new AnimatedSprite(arrPointHuman[0][i],
					arrPointHuman[1][i], this.arrHmanTiledTtrR[i],
					getVertexBufferObjectManager());
			this.mScene.attachChild(arrAnmSHuman[i]);
		}
		// Add Apple
		for (int i = 0; i < 7; i++) {
			this.arrSApple[i] = new Sprite(arrPointApple[0][i],
					arrPointApple[1][i], this.arrAppleTtrR[i],
					getVertexBufferObjectManager());
			this.mScene.attachChild(arrSApple[i]);
		}
		// Add Mugi
		for (int i = 0; i < 7; i++) {
			this.arrSMugi[i] = new Sprite(arrPointMugi[0][i],
					arrPointMugi[1][i], this.arrMugiTtrR[i],
					getVertexBufferObjectManager());
			this.mScene.attachChild(arrSMugi[i]);
			this.arrSMugi[i].setVisible(false);
		}
		// Add Niwatori
		for (int i = 0; i < 7; i++) {
			this.arrSNiwatori[i] = new Sprite(arrPointNiwatori[0][i],
					arrPointNiwatori[1][i], this.arrNiwatoriTtrR[i],
					getVertexBufferObjectManager());
			this.mScene.attachChild(arrSNiwatori[i]);
			this.arrSNiwatori[i].setVisible(false);
		}
		// Add Cow
		for (int i = 0; i < 7; i++) {
			this.arrSCow[i] = new Sprite(arrPointCow[0][i], arrPointCow[1][i],
					this.arrCowTtrR[i], getVertexBufferObjectManager());
			this.mScene.attachChild(arrSCow[i]);
			this.arrSCow[i].setVisible(false);
		}
		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3AburahamuResource.SOUND_GIMMIC_ABURAHAMU,
				Vol3AburahamuResource.IMAGE_GIMMIC_ABURAHAMU,
				mBackgroundItemsTexturePackTextureRegionLibrary);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		//this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}
	
	@Override
	public synchronized void onResumeGame() {

		Log.d(TAG, "onResumeGame");
		TimerHandler timer = new TimerHandler(3.0f, false,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						humansSleep();
					}
				});
		this.mScene.registerUpdateHandler(timer);
		isTouchLeaf = true;
		isTouchHuman = true;
		isSleep = false;
		isTouchFood = false;
		countHumanGiveFood = 0;
		statusFood = this.FOOD_APPLE;
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeSound() {
		
		mp3Kasa2 = loadSoundResourceFromSD(Vol3AburahamuResource.A14_04_KASA2);
		mp3Bowan2 = loadSoundResourceFromSD(Vol3AburahamuResource.A14_05_07_BOWAN2);
		mp3Pomi = loadSoundResourceFromSD(Vol3AburahamuResource.A14_05_POMI);
		mp3Sawa = loadSoundResourceFromSD(Vol3AburahamuResource.A14_05_SAWA);
		mp3Niwatori3 = loadSoundResourceFromSD(Vol3AburahamuResource.A14_06_NIWATORI3);
		mp3Ushi = loadSoundResourceFromSD(Vol3AburahamuResource.A14_07_USHI);
		mp3Byon = loadSoundResourceFromSD(Vol3AburahamuResource.A14_08_BYON);
		mp3Migite = loadSoundResourceFromSD(Vol3AburahamuResource.A14_9B_MIGITE);
		mp3Hidadrite = loadSoundResourceFromSD(Vol3AburahamuResource.A14_10B_HIDADRITE);
		mp3Migiashi = loadSoundResourceFromSD(Vol3AburahamuResource.A14_11B_MIGIASHI);
		mp3Hidariashi = loadSoundResourceFromSD(Vol3AburahamuResource.A14_12B_HIDARIASHI);
		mp3Atama = loadSoundResourceFromSD(Vol3AburahamuResource.A14_13B_ATAMA);
		mp3Oshiri = loadSoundResourceFromSD(Vol3AburahamuResource.A14_14B_OSHIRI);
		mp3Mawaru = loadSoundResourceFromSD(Vol3AburahamuResource.A14_15B_MAWARU);
		mp3Neiki = loadSoundResourceFromSD(Vol3AburahamuResource.A14_C_NEIKI);
		mp3Pan = loadSoundResourceFromSD(Vol3AburahamuResource.A14_D_PAN);
		mp3Ringo = loadSoundResourceFromSD(Vol3AburahamuResource.A14_F_RINGO);
		mp3Donatu = loadSoundResourceFromSD(Vol3AburahamuResource.A14_G_DONATU);
		mp3Chikin = loadSoundResourceFromSD(Vol3AburahamuResource.A14_H_CHIKIN);
		mp3Ice = loadSoundResourceFromSD(Vol3AburahamuResource.A14_I_ICE);
	}

	@Override
	public void onPauseGame() {
		this.mScene.clearUpdateHandlers();
			mEngine.clearUpdateHandlers();
			for (int i = 0; i < 7; i++) {
				if (arrSApple[i] != null) {
					arrSApple[i].clearEntityModifiers();
					arrSApple[i].setPosition(arrPointApple[0][i],
							arrPointApple[1][i]);
					arrSApple[i].setVisible(true);
				}

				if (arrSMugi[i] != null) {
					arrSMugi[i].clearEntityModifiers();
					arrSMugi[i].setPosition(arrPointMugi[0][i],
							arrPointMugi[1][i]);
					arrSMugi[i].setVisible(false);
				}
				if (arrSNiwatori[i] != null) {
					arrSNiwatori[i].clearEntityModifiers();
					arrSNiwatori[i].setPosition(arrPointNiwatori[0][i],
							arrPointNiwatori[1][i]);
					arrSNiwatori[i].setVisible(false);
				}
				if (arrSCow[i] != null) {
					arrSCow[i].clearEntityModifiers();
					arrSCow[i]
							.setPosition(arrPointCow[0][i], arrPointCow[1][i]);
					arrSCow[i].setVisible(false);
				}
				if (arrAnmSHuman[i] != null) {
					Log.d(TAG, "arrAnmSHuman[i].stopAnimation()");
					arrAnmSHuman[i].stopAnimation();
					arrAnmSHuman[i].setCurrentTileIndex(0);
				}
				arrIsHumanGiveFood[i] = false;
				arrIsTouchFood[i] = false;
				arrIsTouchHuman[i] = true;
			countHumanGiveFood = 0;
			isSleep = false;
			isTouchLeaf = true;
			statusFood = FOOD_APPLE;
			if (anmSMugi != null) {
				anmSMugi.setVisible(true);
			}
			if (anmSNiwatori != null) {
				anmSNiwatori.setVisible(true);
			}
			if (anmSCow != null) {
				anmSCow.setVisible(true);
			}
			if (sTableMugi != null) {
				sTableMugi.setVisible(false);
			}
			if (sTableNiwatori != null) {
				sTableNiwatori.setVisible(false);
			}
			if (sTableCow != null) {
				sTableCow.setVisible(false);
			}
			if (anmSMugi != null) {
				anmSMugi.setCurrentTileIndex(0);
			}
			if (anmSNiwatori != null) {
				anmSNiwatori.setCurrentTileIndex(0);
			}
			if (anmSCow != null) {
				anmSCow.setCurrentTileIndex(0);
			}
		}
		super.onPauseGame();

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		// pSceneTouchEvent.

		isTouchHuman = false;
		isTouchFood = false;
		Log.d(TAG, "ACTION DOWN - ");

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (statusFood == FOOD_APPLE) {
				for (int i = 0; i < 7; i++) {
					if (!arrIsTouchFood[i]
							&& (arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == 0
									|| arrAnmSHuman[countHumanGiveFood]
											.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][0] || arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][1])
							&& arrSApple[i].isVisible()
							&& checkContains(arrSApple[i], 0, 0,
									(int) arrSApple[i].getWidth(),
									(int) arrSApple[i].getHeight(), pX, pY)) {
						isTouchFood = true;
						foodModifier(arrSApple[i],
								arrAnmSHuman[countHumanGiveFood],
								countHumanGiveFood, i);
						// arrIsHumanGiveFood[countHumanGiveFood] = true;
						arrIsTouchFood[i] = true;
						countHumanGiveFood++;

						Log.d(TAG, i + "");
					}
				}
			} else if (statusFood == FOOD_MUGI) {
				for (int i = 0; i < 7; i++) {
					if (!arrIsTouchFood[i]
							&& (arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == 0
									|| arrAnmSHuman[countHumanGiveFood]
											.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][0] || arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][1])
							&& arrSMugi[i].isVisible()
							&& checkContains(arrSMugi[i], 0, 0,
									(int) arrSMugi[i].getWidth(),
									(int) arrSMugi[i].getHeight(), pX, pY)) {
						isTouchFood = true;
						Log.d(TAG, "ACTION DOWN FOOD_MUGI - " + i);
						foodModifier(arrSMugi[i],
								arrAnmSHuman[countHumanGiveFood],
								countHumanGiveFood, i);
						// arrIsHumanGiveFood[countHumanGiveFood] = true;
						arrIsTouchFood[i] = true;
						countHumanGiveFood++;
					}
				}
			} else if (statusFood == FOOD_NIWATORI) {
				for (int i = 0; i < 7; i++) {
					if (!arrIsTouchFood[i]
							&& (arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == 0
									|| arrAnmSHuman[countHumanGiveFood]
											.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][0] || arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][1])
							&& arrSNiwatori[i].isVisible()
							&& checkContains(arrSNiwatori[i], 0, 0,
									(int) arrSNiwatori[i].getWidth(),
									(int) arrSNiwatori[i].getHeight(), pX, pY)) {
						isTouchFood = true;
						Log.d(TAG, "ACTION DOWN FOOD_NIWATORI - " + i);
						foodModifier(arrSNiwatori[i],
								arrAnmSHuman[countHumanGiveFood],
								countHumanGiveFood, i);
						// arrIsHumanGiveFood[countHumanGiveFood] = true;
						arrIsTouchFood[i] = true;
						countHumanGiveFood++;
					}
				}
			} else if (statusFood == FOOD_COW) {
				for (int i = 0; i < 7; i++) {
					if (!arrIsTouchFood[i]
							&& (arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == 0
									|| arrAnmSHuman[countHumanGiveFood]
											.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][0] || arrAnmSHuman[countHumanGiveFood]
									.getCurrentTileIndex() == arrFrameHumanSleep[countHumanGiveFood][1])
							&& arrSCow[i].isVisible()
							&& checkContains(arrSCow[i], 0, 0,
									(int) arrSCow[i].getWidth(),
									(int) arrSCow[i].getHeight(), pX, pY)) {
						isTouchFood = true;
						Log.d(TAG, "ACTION DOWN - " + i);
						foodModifier(arrSCow[i],
								arrAnmSHuman[countHumanGiveFood],
								countHumanGiveFood, i);
						// arrIsHumanGiveFood[countHumanGiveFood] = true;
						arrIsTouchFood[i] = true;
						countHumanGiveFood++;
					}
				}
			}

			for (int i = 0; i < 7; i++) {
				if (arrIsTouchHuman[i]
						&& checkContains(arrAnmSHuman[i],
								(int) arrAnmSHuman[i].getWidth() / 3,
								(int) arrAnmSHuman[i].getHeight() / 5,
								(int) arrAnmSHuman[i].getWidth() * 2 / 3,
								(int) arrAnmSHuman[i].getHeight() * 4 / 5, pX,
								pY)) {

					// Human Touch
					isTouchHuman = true;
					humanTouch(i);
				}
			}

			if (anmSMugi.isVisible()
					&& checkContains(anmSMugi, 0,
							(int) anmSMugi.getHeight() / 2,
							(int) anmSMugi.getWidth(),
							(int) anmSMugi.getHeight(), pX, pY)) {
				mugiAnimate();
			} else if (anmSNiwatori.isVisible()
					&& checkContains(anmSNiwatori,
							(int) anmSNiwatori.getWidth() / 3,
							(int) anmSNiwatori.getHeight() / 2,
							(int) anmSNiwatori.getWidth() * 2 / 3,
							(int) anmSNiwatori.getHeight(), pX, pY)) {
				niwatoriAnimate();
			} else if (anmSCow.isVisible()
					&& checkContains(anmSCow, 0, 0, (int) anmSCow.getWidth(),
							(int) anmSCow.getHeight(), pX, pY)) {
				cowAnimate();
			} else if (checkContains(sLeaf, 0, 0, (int) sLeaf.getWidth(),
					(int) sLeaf.getHeight() / 2, pX, pY)
					|| checkContains(sTree, (int) sTree.getWidth() / 3, 0,
							(int) sTree.getWidth() * 2 / 3,
							(int) sTree.getHeight() * 6 / 7, pX, pY)) {
				leafModifier();
			} else if (checkContains(anmSUsagi, 0,
					(int) anmSUsagi.getHeight() / 2,
					(int) anmSUsagi.getWidth(),
					(int) anmSUsagi.getHeight() * 3 / 4, pX, pY)) {
				usagiAnimate();
			}

			humansSleep();
			if (isSleep && !isTouchHuman) {
				humanGetup(-1);
			}
		}

		return false;
	}

	@Override
	public void combineGimmic3WithAction() {
		humansSleep();
		if (isSleep) {
			humanGetup(-1);
		}
		usagiAnimate();

	}

	private void foodModifier(Sprite sFrom, AnimatedSprite sTo, int indexHuman,
			int indexFood) {
		mp3Pomi.play();

		for (int i = 0; i < 7; i++) {
			arrIsTouchHuman[i] = false;
		}

		float fromX = sFrom.getX();
		float toX = sTo.getX() + sTo.getWidth() / 2 - sFrom.getWidth() / 2;

		float fromY = sFrom.getY();
		float toY = sTo.getY() + sTo.getHeight() / 2;
		sFrom.registerEntityModifier(foodModifier[indexFood] = new ParallelEntityModifier(
				new MoveXModifier(1, fromX, toX, EaseQuadOut.getInstance()),
				new MoveYModifier(1, fromY, toY, EaseQuadOut.getInstance()))

		);
		if (foodModifier[indexFood] != null) {
			foodModifier[indexFood].addModifierListener(new MyModifierListener(
					indexHuman, indexFood));
		}
	}

	private void humanEat() {
		countHumanGiveFood = 0;
		isReset = false;

		long durationEat = 1200;
		// Reset check human have food
		for (int i = 0; i < arrIsHumanGiveFood.length; i++) {
			arrIsHumanGiveFood[i] = false;
		}
		for (int i = 0; i < arrIsTouchFood.length; i++) {
			arrIsTouchFood[i] = false;
		}

		if (statusFood == FOOD_APPLE) {
			mp3Ringo.play();
		} else if (statusFood == FOOD_MUGI) {
			mp3Donatu.play();
		} else if (statusFood == FOOD_NIWATORI) {
			mp3Chikin.play();
		}

		else if (statusFood == FOOD_COW) {
			mp3Ice.play();
			durationEat = 1500;
		}

		for (int i = 0; i < 7; i++) {
			long durations[] = new long[] { durationEat, 100 };
			int frames[] = new int[] { arrFrameGiveFood[i][statusFood + 1], 0 };
			arrAnmSHuman[i].animate(durations, frames, 0);
		}
		mEngine.registerUpdateHandler(new TimerHandler(1.1f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						for (int i = 0; i < 7; i++) {
							arrIsTouchHuman[i] = true;
						}
					}
				}));

		// Display Smoke And Fade Out it
		if (statusFood < FOOD_COW) {
			mp3Bowan2.play();

			this.sSmoke.setPosition(arrPointSmoke[0][statusFood],
					arrPointSmoke[1][statusFood]);
			this.sSmoke.setVisible(true);
			this.sSmoke
					.registerEntityModifier(smokeModifier = new ParallelEntityModifier(
							new MoveYModifier(1.0f, sSmoke.getY(), sSmoke
									.getY() - 80), new AlphaModifier(1.0f,
									1.0f, 0.0f)));

			if (smokeModifier != null) {
				smokeModifier.addModifierListener(this);
			}
		}

		if (statusFood == FOOD_APPLE) {
			anmSMugi.setVisible(false);
		} else if (statusFood == FOOD_MUGI) {
			anmSMugi.clearEntityModifiers();
			anmSMugi.setVisible(true);
			sTableMugi.setVisible(false);
			anmSNiwatori.setVisible(false);
		} else if (statusFood == FOOD_NIWATORI) {
			sTableNiwatori.setVisible(false);
			anmSNiwatori.clearEntityModifiers();
			anmSNiwatori.setVisible(true);
			anmSCow.setVisible(false);
		}

		else if (statusFood == FOOD_COW) {
			sTableCow.setVisible(false);
			anmSCow.clearEntityModifiers();
			anmSCow.setVisible(true);
			isReset = true;
		}
		if (statusFood != FOOD_COW) {
			statusFood++;
		}

		// Reset All To Begin
		if (isReset) {
			statusFood = FOOD_APPLE;
			for (int i = 0; i < 7; i++) {
				arrSApple[i].setPosition(arrPointApple[0][i],
						arrPointApple[1][i]);
				arrSApple[i].setVisible(true);
			}

			// Reset check human have food
			for (int i = 0; i < arrIsHumanGiveFood.length; i++) {
				arrIsHumanGiveFood[i] = false;
			}

			for (int i = 0; i < arrIsTouchFood.length; i++) {
				arrIsTouchFood[i] = false;
			}
		}

	}

	private void humanTouch(int index) {
		Log.d(TAG, arrIsHumanGiveFood[index] + "");

		if (!arrAnmSHuman[index].isAnimationRunning()) {
			switch (index) {
			case 0:
				mp3Migite.play();
				break;
			case 1:
				mp3Hidadrite.play();
				break;
			case 2:
				mp3Migiashi.play();
				break;
			case 3:
				mp3Hidariashi.play();
				break;
			case 4:
				mp3Atama.play();
				break;
			case 5:
				mp3Oshiri.play();
				break;
			case 6:
				mp3Mawaru.play();
				break;
			default:
				break;
			}
		}

		if (!isSleep) {

			if (!arrAnmSHuman[index].isAnimationRunning()) {
				int frames[] = new int[arrFrameHumanTouch[index][4] + 1];
				long durations[] = new long[arrFrameHumanTouch[index][4] + 1];
				for (int i = 0; i <= arrFrameHumanTouch[index][4]; i++) {
					if (i != arrFrameHumanTouch[index][4]) {
						frames[i] = i + 1;
						durations[i] = 800 / (frames.length - 1);
					} else {

						if (!arrIsHumanGiveFood[index]) {
							frames[i] = 0;
							durations[i] = 100;
						} else {
							frames[i] = arrFrameHumanTouch[index][statusFood];
							durations[i] = 100;
						}
					}
				}
				arrAnmSHuman[index].animate(durations, frames, 0);
			}
		} else {
			humanGetup(index);
		}
		return;
	}

	private void humanGetup(int index) {
		if (isTouchHuman) {
			mp3Pan.play();

			switch (index) {
			case 0:
				mp3Migite.play();
				break;
			case 1:
				mp3Hidadrite.play();
				break;
			case 2:
				mp3Migiashi.play();
				break;
			case 3:
				mp3Hidariashi.play();
				break;
			case 4:
				mp3Atama.play();
				break;
			case 5:
				mp3Oshiri.play();
				break;
			case 6:
				mp3Mawaru.play();
				break;
			default:
				break;
			}
		}
		if (!isTouchFood) {

			for (int j = 0; j < 7; j++) {
				if (j != index) {
					int frames[] = new int[2];
					long durations[] = new long[2];
					for (int i = 0; i <= 1; i++) {
						if (i == 0) {
							frames[i] = arrFrameHumanSleep[j][2];
							durations[i] = 500;
						} else {

							if (!arrIsHumanGiveFood[j]) {
								frames[i] = 0;
								durations[i] = 100;
							} else {
								frames[i] = arrFrameHumanTouch[j][statusFood];
								durations[i] = 100;
							}
						}
					}
					arrAnmSHuman[j].animate(durations, frames, 0);
				} else {
					int frames[] = new int[arrFrameHumanTouch[index][4] + 1];
					long durations[] = new long[arrFrameHumanTouch[index][4] + 1];
					for (int i = 0; i <= arrFrameHumanTouch[index][4]; i++) {
						if (i != arrFrameHumanTouch[index][4]) {
							frames[i] = i + 1;
							durations[i] = 800 / (frames.length - 1);
						} else {

							if (!arrIsHumanGiveFood[index]) {
								frames[i] = 0;
								durations[i] = 100;
							} else {
								frames[i] = arrFrameHumanTouch[index][statusFood];
								durations[i] = 100;
							}
						}
					}
					arrAnmSHuman[index].animate(durations, frames, 0);
				}
			}
		} else {
			for (int j = 0; j < 7; j++) {

				int frames[] = new int[1];
				long durations[] = new long[1];
				for (int i = 0; i < 1; i++) {

					if (!arrIsHumanGiveFood[j]) {
						frames[i] = 0;
						durations[i] = 100;
					} else {
						frames[i] = arrFrameHumanTouch[j][statusFood];
						durations[i] = 100;
					}
				}
				arrAnmSHuman[j].animate(durations, frames, 0);
			}
		}
		isSleep = false;
	}

	private void usagiAnimate() {

		if (!anmSUsagi.isAnimationRunning()) {
			mp3Byon.play();

			long durations[] = new long[] { 300, 100 };
			int frames[] = new int[] { 1, 0 };
			anmSUsagi.animate(durations, frames, 0);
		}
		return;
	}

	private void mugiAnimate() {
		if (!anmSMugi.isAnimationRunning()) {
			mp3Sawa.play();

			long durations[] = new long[] { 400, 400, 400, 400, 400, 400, 50 };
			int frames[] = new int[] { 1, 2, 1, 2, 1, 2, 0 };
			anmSMugi.animate(durations, frames, 0);
		}
	}

	private void niwatoriAnimate() {
		if (!anmSNiwatori.isAnimationRunning()) {
			mp3Niwatori3.play();
			long durations[] = new long[] { 1500, 100 };
			int frames[] = new int[] { 1, 0 };
			anmSNiwatori.animate(durations, frames, 0);
		}
	}

	private void cowAnimate() {
		if (!anmSCow.isAnimationRunning()) {
			mp3Ushi.play();

			long durations[] = new long[] { 250, 250, 250, 250, 50 };
			int frames[] = new int[] { 1, 2, 1, 2, 0 };
			anmSCow.animate(durations, frames, 0);
		}
	}

	private void leafModifier() {
		if (isTouchLeaf && statusFood != FOOD_APPLE) {
			mp3Kasa2.play();

			isTouchLeaf = false;
			sLeaf.registerEntityModifier(leafModifer = new SequenceEntityModifier(
					new MoveXModifier(0.2f, sLeaf.getX(), sLeaf.getX() + 20),
					new MoveXModifier(0.4f, sLeaf.getX() + 20,
							sLeaf.getX() - 20), new MoveXModifier(0.2f, sLeaf
							.getX() - 20, sLeaf.getX())));
			if (leafModifer != null) {
				leafModifer.addModifierListener(this);
			}
		}
		return;
	}

	private void humansSleep() {
		mScene.unregisterUpdateHandler(timeUpdate);
		mScene.registerUpdateHandler(timeUpdate =new TimerHandler(10f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						mp3Neiki.play();
						isSleep = true;
						long durations[] = { 500, 500 };

						for (int i = 0; i < 7; i++) {
							int frames[] = { arrFrameHumanSleep[i][0],
									arrFrameHumanSleep[i][1] };
							arrAnmSHuman[i].animate(durations, frames, -1);
						}
					}
				}));
	}

	class MyModifierListener implements IModifierListener<IEntity> {
		private int indexHuman, indexFood;

		public MyModifierListener(int indexHuman, int indexFood) {
			this.indexHuman = indexHuman;
			this.indexFood = indexFood;
		}

		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

			for (int i = 0; i < 7; i++) {
				arrIsTouchHuman[i] = true;
			}
			arrIsHumanGiveFood[indexHuman] = true;
			long durations[] = new long[] { 200, 200 };
			int frames[] = new int[] { arrFrameGiveFood[indexHuman][0],
					arrFrameGiveFood[indexHuman][statusFood + 1] };
			arrAnmSHuman[indexHuman].animate(durations, frames, 0);
			if (statusFood == FOOD_APPLE) {
				arrSApple[indexFood].setVisible(false);
			} else if (statusFood == FOOD_MUGI) {
				arrSMugi[indexFood].setVisible(false);
			} else if (statusFood == FOOD_NIWATORI) {
				arrSNiwatori[indexFood].setVisible(false);
			} else if (statusFood == FOOD_COW) {
				arrSCow[indexFood].setVisible(false);
			}
			if (indexHuman == 6) {
				for (int i = 0; i < 7; i++) {
					arrIsTouchHuman[i] = false;
				}
				mEngine.registerUpdateHandler(new TimerHandler(0.5f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler arg0) {
								humanEat();
							}
						}));
			}
		}

		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			

		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		if (pModifier.equals(smokeModifier)) {
			sSmoke.setVisible(false);
			if (statusFood == FOOD_MUGI) {
				sTableMugi.setVisible(true);
				for (int i = 0; i < 7; i++) {
					arrSMugi[i].setPosition(arrPointMugi[0][i],
							arrPointMugi[1][i]);
					arrSMugi[i].setVisible(true);
				}
			} else if (statusFood == FOOD_NIWATORI) {
				sTableNiwatori.setVisible(true);
				for (int i = 0; i < 7; i++) {
					arrSNiwatori[i].setPosition(arrPointNiwatori[0][i],
							arrPointNiwatori[1][i]);
					arrSNiwatori[i].setVisible(true);
				}
			} else if (statusFood == FOOD_COW) {
				sTableCow.setVisible(true);
				for (int i = 0; i < 7; i++) {
					arrSCow[i]
							.setPosition(arrPointCow[0][i], arrPointCow[1][i]);
					arrSCow[i].setVisible(true);
				}
			}

			for (int i = 0; i < 7; i++) {
				this.arrAnmSHuman[i].setCurrentTileIndex(0);
			}
		}
		if (pModifier.equals(leafModifer)) {
			isTouchLeaf = true;
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

	}

}
