package jp.co.xing.utaehon03.songs;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3SakanaResource;

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
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseQuartOut;

import android.util.Log;

public class Vol3Sakana extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity>, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_SAKANA";

	// ===========================================================
	// Fields
	// ===========================================================

	private TexturePack mBackgroundTexturePack;
	private TexturePack mItemSmallTexturePack;
	private TexturePack mKawaTexturePack;
	private TexturePack mAmiBaketShuTexturePack;
	private TexturePack mBoushiMeganeTexturePack;
	private TexturePack mMaskKinnikuTexturePack;
	private TexturePack mOpaiShippoHiepitaTexturePack;
	private TexturePack mTeKaoKyamiTexturePack;

	private TexturePackTextureRegionLibrary mBackgroundTexturePackTextureRegionLibrary;
	private TexturePackTextureRegionLibrary mItemSmallTexturePackTextureRegionLibrary;
	private TexturePackTextureRegionLibrary mOpaiShippoHiepitaTexturePackTexturePackRegionLibrary;
	private TexturePackTextureRegionLibrary mTeKaoKyamiTexturePackTextureRegionLibrary;

	private TextureRegion ttrRBackground, ttrRBackground2, ttrRBackground3,
			ttrRUfo, ttrRHati3, ttrRKumo, ttrRNiji, ttrRBodyGirl,
			ttrRKiamyGirl, ttrRHieptita;

	private TiledTextureRegion tiledTtrKawa, tiledTtrAri1, tiledTtrAri2,
			tiledTtrHati, tiledTtrSun, tiledTtrKappa, tiledTtrTako,
			tiledTtrAhuri, tiledTtrHandGirl, tiledTtrHeaderGirl,
			tiledTtrBaketsu, tiledTtrAmi, tiledTtrBoushi, tiledTtrMegane,
			tiledTtrMask, tiledTtrKiniku, tiledTtrOpa, tiledTtrShippo,
			tiledTtrSakanaRed, tiledTtrMizuFix, tiledSakanaYellow,
			tiledSakanaGreen, tiledSakanaPurple, tiledSakanaBlue,
			tiledSakanaYellowInBaketsu, tiledSakanaGreenInBaketsu,
			tiledSakanaPurpleInBaketsu, tiledSakanaBlueInBaketsu, tiledMizushi;

	private AnimatedSprite anmSprKawa, anmSprAri1, anmSprAri2, anmSprHati,
			anmSprSun, anmSprKappa, anmSprTako, anmSprAhuri, anmSprHandGirl,
			anmSprHeaderGirl, anmSprBaketsu, anmSprAmi, anmSprBoushi,
			anmSprMegane, anmSprMask, anmSprKiniku, anmSprOpa, anmSprShippo,
			anmSprSakanaRed, anmSprMizuFix, anmSprMizushi,
			arrAnmSprSakanaInBaketsu[] = new AnimatedSprite[11];
	private SakanaSprite anmSprSakana;
	private Sprite sprBackground2, sprBackground3, sprUfo, sprHati3, sprKumo,
			sprNiji, sprBodyGirl, sprKiamyGirl, sprHieptita;

	/**
	 * Handler, Runnable
	 */
	private TimerHandler handlerSakanaRed, handlerSakana, handlerAri;

	/**
	 * Modifier
	 */
	private MoveModifier hatiModifier, takoModifier, ahuriModifier,
			amiModifier;
	private AlphaModifier nijiModifier;
	private SequenceEntityModifier sakanaRedModifier, sakanaModifier;
	private ParallelEntityModifier ufoModifier, ari1Modifier;
	@SuppressWarnings("unused")
	private IModifierListener<IEntity> sakanaModifierListener;
	@SuppressWarnings("unused")
	private IAnimationListener sakanaAnimateListener;
	private Random random, randomAllSakana, randomPositionSakana;

	private int pointerForGirlTouch[][] = {
			{ 284, 284, 284, 284, 169, 399, 274, 289 },
			{ 86, 106, 162, 204, 276, 256, 275, 360 } };
	private boolean arrVisibleForGirl[] = { false, false, false, false };

	private int arrPoiterSakana[][] = {
			{ 160, 395, 394, 220, 326, 497, 331, 512, 288 },
			{ 73, 303, 337, 248, 92, 173, 167, 125, 217, 227 } };

	private int arrPoiterSakanaInBaketsu[][] = {
			{ 52, 121, 64, 119, 116, 176, 57, 125, 173, 160 },
			{ 146, 224, 222, 209, 153, 150, 187, 184, 188, 224 } };
	private float arrPointerKawa[][] = { { 0, 596, 960, 960, 501, 374, 0, 0 },
			{ 0, 154, 183, 440, 438, 302, 272, 0 } };
	private int countInBaketsu = 0;
	private boolean isInBaketSu = false, isTouchGirl, isStart = false,
			hasInGirl = false, isDrag = false;
	private float pXBaketsuTemp = 0;

	/**
	 * All Sound
	 */
	private Sound mp3Kasa2;
	private Sound mp3Powa2;
	private Sound mp3Kira5;
	private Sound mp3Ufo;
	private Sound mp3Kira6;
	private Sound mp3Powan;
	private Sound mp3Chapa;
	//private Sound mp3Wa3;
	private Sound mp3Botyan;
	private Sound mp3Namikarui;
	private Sound mp3Powa3;
	private Sound mp3Arere;
	private Sound mp3Piti2;
	private Sound mp3Reset;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("sakana/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sakana/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		// //////////////Load Background ////////////////////
		mBackgroundTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("background.xml");
		mBackgroundTexturePack.loadTexture();
		mBackgroundTexturePackTextureRegionLibrary = mBackgroundTexturePack
				.getTexturePackTextureRegionLibrary();

		mItemSmallTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("itemsmall.xml");
		mItemSmallTexturePack.loadTexture();
		mItemSmallTexturePackTextureRegionLibrary = mItemSmallTexturePack
				.getTexturePackTextureRegionLibrary();

		mKawaTexturePack = new TexturePackLoaderFromSource(getTextureManager(),
				pAssetManager, "sakana/gfx/").load("kawa.xml");
		mKawaTexturePack.loadTexture();

		mAmiBaketShuTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("ami_baketshu.xml");
		mAmiBaketShuTexturePack.loadTexture();

		mBoushiMeganeTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("boushi_megane.xml");
		mBoushiMeganeTexturePack.loadTexture();

		mMaskKinnikuTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("mask_kinniku.xml");
		mMaskKinnikuTexturePack.loadTexture();

		mOpaiShippoHiepitaTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("opai_shippo_hiepita.xml");
		mOpaiShippoHiepitaTexturePack.loadTexture();
		mOpaiShippoHiepitaTexturePackTexturePackRegionLibrary = mOpaiShippoHiepitaTexturePack
				.getTexturePackTextureRegionLibrary();

		mTeKaoKyamiTexturePack = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "sakana/gfx/")
				.load("te_kao_kyami.xml");
		mTeKaoKyamiTexturePack.loadTexture();
		mTeKaoKyamiTexturePackTextureRegionLibrary = mTeKaoKyamiTexturePack
				.getTexturePackTextureRegionLibrary();

		this.ttrRBackground = mBackgroundTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_16_1_IPHONE_HAIKEI_ID);
		this.ttrRBackground2 = mBackgroundTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_16_2_IPHONE_HAIKEI_ID);

		this.ttrRBackground3 = mBackgroundTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_16_3_IPHONE_HAIKEI_ID);

		// //////////////// Load Kawa ////////////////////////
		this.tiledTtrKawa = getTiledTextureFromPacker(mKawaTexturePack,
				Vol3SakanaResource.A15_04_1_IPHONE_KAWA_ID,
				Vol3SakanaResource.A15_04_2_IPHONE_KAWA_ID);

		// /////////////// Load Ufo /////////////////////////
		this.ttrRUfo = mItemSmallTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_08_IPHONE_UFO_ID);

		// ////////////// Load Ari //////////////////////////
		this.tiledTtrAri1 = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_05_1_IPHONE_ARI_ID,
				Vol3SakanaResource.A15_05_2_IPHONE_ARI_ID,
				Vol3SakanaResource.A15_05_3_IPHONE_ARI_ID,
				Vol3SakanaResource.A15_05_4_IPHONE_ARI_ID);

		this.tiledTtrAri2 = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_05_5_IPHONE_ARI_ID,
				Vol3SakanaResource.A15_05_6_IPHONE_ARI_ID);

		// /////////////////// Load Hati /////////////////////////
		this.tiledTtrHati = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_06_1_IPHONE_DEFAULT_ID,
				Vol3SakanaResource.A15_06_2_IPHONE_USAGI_ID);

		// Load Hati 3
		this.ttrRHati3 = mItemSmallTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_06_3_IPHONE_HATI_ID);

		// Load Sun
		this.tiledTtrSun = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_07_1_IPHONE_SUN_ID,
				Vol3SakanaResource.A15_07_2_IPHONE_SUN_ID);

		// Load Kumo
		this.ttrRKumo = mItemSmallTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_09_1_IPHONE_KUMO_ID);

		// Load Niji
		this.ttrRNiji = mItemSmallTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_09_2_IPHONE_NIJI_ID);

		// Load Kappa
		this.tiledTtrKappa = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_10_1_IPHONE_KAPPA_ID,
				Vol3SakanaResource.A15_10_2_IPHONE_KAPPA_ID);

		// Load Tako
		this.tiledTtrTako = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_11_1_IPHONE_TAKO_ID,
				Vol3SakanaResource.A15_11_2_IPHONE_TAKO_ID);

		// Load Kappa
		this.tiledTtrAhuri = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_12_1_IPHONE_AHIRU_ID,
				Vol3SakanaResource.A15_12_2_IPHONE_AHIRU_ID);

		// Load Girl
		this.ttrRBodyGirl = mTeKaoKyamiTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_15_3_IPHONE_TE_ID);

		this.ttrRKiamyGirl = mTeKaoKyamiTexturePackTextureRegionLibrary
				.get(Vol3SakanaResource.A15_15_8_IPHONE_KYAMI_ID);

		this.tiledTtrHandGirl = getTiledTextureFromPacker(
				mTeKaoKyamiTexturePack,
				Vol3SakanaResource.A15_15_1_IPHONE_TE_ID,
				Vol3SakanaResource.A15_15_2_IPHONE_TE_ID);

		this.tiledTtrHeaderGirl = getTiledTextureFromPacker(
				mTeKaoKyamiTexturePack,
				Vol3SakanaResource.A15_15_4_IPHONE_KAO_ID,
				Vol3SakanaResource.A15_15_5_IPHONE_KAO_ID,
				Vol3SakanaResource.A15_15_6_IPHONE_KAO_ID);
		// Load Baketsu
		this.tiledTtrBaketsu = getTiledTextureFromPacker(
				mAmiBaketShuTexturePack,
				Vol3SakanaResource.A15_14_7_1_IPHONE_BAKETSU_ID,
				Vol3SakanaResource.A15_14_7_2_IPHONE_BAKETSU_ID,
				Vol3SakanaResource.A15_14_7_3_IPHONE_BAKETSU_ID);

		// Load Ami
		this.tiledTtrAmi = getTiledTextureFromPacker(mAmiBaketShuTexturePack,
				Vol3SakanaResource.A15_14_6_1_IPHONE_AMI_ID,
				Vol3SakanaResource.A15_14_6_2_IPHONE_AMI_ID);

		// Load Boushi
		this.tiledTtrBoushi = getTiledTextureFromPacker(
				mBoushiMeganeTexturePack,
				Vol3SakanaResource.A15_15A_1_IPHONE_BOUSHI_ID,
				Vol3SakanaResource.A15_15A_2_IPHONE_BOUSHI_ID,
				Vol3SakanaResource.A15_15A_3_IPHONE_BOUSHI_ID);

		// Load Megane
		this.tiledTtrMegane = getTiledTextureFromPacker(
				mBoushiMeganeTexturePack,
				Vol3SakanaResource.A15_15B_1_IPHONE_MEGANE_ID,
				Vol3SakanaResource.A15_15B_2_IPHONE_MEGANE_ID,
				Vol3SakanaResource.A15_15B_3_IPHONE_MEGANE_ID);
		// Load Mask
		this.tiledTtrMask = getTiledTextureFromPacker(mMaskKinnikuTexturePack,
				Vol3SakanaResource.A15_15C_1_IPHONE_MASK_ID,
				Vol3SakanaResource.A15_15C_2_IPHONE_MASK_ID,
				Vol3SakanaResource.A15_15C_3_IPHONE_MASK_ID);

		// Load Kiniku
		this.tiledTtrKiniku = getTiledTextureFromPacker(
				mMaskKinnikuTexturePack,
				Vol3SakanaResource.A15_15D_1_IPHONE_KINNIKU_ID,
				Vol3SakanaResource.A15_15D_2_IPHONE_KINNIKU_ID);

		// Load Opa
		this.tiledTtrOpa = getTiledTextureFromPacker(
				mOpaiShippoHiepitaTexturePack,
				Vol3SakanaResource.A15_15E_1_IPHONE_OPAI_ID,
				Vol3SakanaResource.A15_15E_2_IPHONE_OPAI_ID,
				Vol3SakanaResource.A15_15E_3_IPHONE_OPAI_ID);

		// Load Shippo
		this.tiledTtrShippo = getTiledTextureFromPacker(
				mOpaiShippoHiepitaTexturePack,
				Vol3SakanaResource.A15_15F_1_IPHONE_SHIPPO_ID,
				Vol3SakanaResource.A15_15F_2_IPHONE_SHIPPO_ID,
				Vol3SakanaResource.A15_15F_3_IPHONE_SHIPPO_ID);

		// Load Hiepkita
		this.ttrRHieptita = mOpaiShippoHiepitaTexturePackTexturePackRegionLibrary
				.get(Vol3SakanaResource.A15_15G_IPHONE_HIEPITA_ID);

		// Load Sakana Red
		this.tiledTtrSakanaRed = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_4_1_IPHONE_SAKANA_ID,
				Vol3SakanaResource.A15_14_4_2_IPHONE_SAKANA_ID);

		// Load Mizu Fix
		this.tiledTtrMizuFix = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_13_1_IPHONE_MIZUSHIBUKI_ID,
				Vol3SakanaResource.A15_13_2_IPHONE_MIZUSHIBUKI_ID,
				Vol3SakanaResource.A15_13_3_IPHONE_MIZUSHIBUKI_ID);

		// Load Sakana all
		this.tiledSakanaYellow = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_1_1_IPHONE_SAKANA_ID,
				Vol3SakanaResource.A15_14_1_2_IPHONE_SAKANA_ID);

		this.tiledSakanaGreen = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_2_1_IPHONE_SAKANA_ID,
				Vol3SakanaResource.A15_14_2_2_IPHONE_SAKANA_ID);

		this.tiledSakanaPurple = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_3_1_IPHONE_SAKANA_ID,
				Vol3SakanaResource.A15_14_3_2_IPHONE_SAKANA_ID);
		this.tiledSakanaBlue = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_5_1_IPHONE_SAKANA_ID,
				Vol3SakanaResource.A15_14_5_2_IPHONE_SAKANA_ID);

		// Load Sakana all In Baketsu
		this.tiledSakanaYellowInBaketsu = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_8_1_IPHONE_MINI_ID,
				Vol3SakanaResource.A15_14_8_2_IPHONE_MINI_ID);

		this.tiledSakanaGreenInBaketsu = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_9_1_IPHONE_MINI_ID,
				Vol3SakanaResource.A15_14_9_2_IPHONE_MINI_ID);

		this.tiledSakanaPurpleInBaketsu = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_10_1_IPHONE_MINI_ID,
				Vol3SakanaResource.A15_14_10_2_IPHONE_MINI_ID);

		this.tiledSakanaBlueInBaketsu = getTiledTextureFromPacker(
				mItemSmallTexturePack,
				Vol3SakanaResource.A15_14_12_1_IPHONE_MINI_ID,
				Vol3SakanaResource.A15_14_12_2_IPHONE_MINI_ID

		);
		// Add Mizu
		this.tiledMizushi = getTiledTextureFromPacker(mItemSmallTexturePack,
				Vol3SakanaResource.A15_13_1_IPHONE_MIZUSHIBUKI_ID,
				Vol3SakanaResource.A15_13_2_IPHONE_MIZUSHIBUKI_ID,
				Vol3SakanaResource.A15_13_3_IPHONE_MIZUSHIBUKI_ID);
	}

	@Override
	protected void loadKaraokeScene() {

		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// Set Backgroud For Scene
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground, this
						.getVertexBufferObjectManager())));

		// Attach Background 2
		this.sprBackground2 = new Sprite(-1, 20, this.ttrRBackground2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBackground2);

		// Attach Background 3
		this.sprBackground3 = new Sprite(0, 0, this.ttrRBackground3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBackground3);

		// Add Sun
		this.anmSprSun = new AnimatedSprite(339, 2, this.tiledTtrSun,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprSun);

		// Add Niji
		this.sprNiji = new Sprite(495, 15, this.ttrRNiji,
				this.getVertexBufferObjectManager());
		this.sprNiji.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.sprNiji.setVisible(false);
		this.sprNiji.setAlpha(0.0f);
		this.mScene.attachChild(this.sprNiji);
		// Add Kumo
		this.sprKumo = new Sprite(495, 15, this.ttrRKumo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKumo);

		// Add Ufo
		this.sprUfo = new Sprite(10, 10, this.ttrRUfo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprUfo);
		sprUfo.setVisible(false);

		// Add Ari
		this.anmSprAri1 = new AnimatedSprite(370, 189, this.tiledTtrAri1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprAri1);

		this.anmSprAri2 = new AnimatedSprite(370, 189, this.tiledTtrAri2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprAri2);
		this.anmSprAri2.setVisible(false);

		anmSprAri2.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// //////// Add Hati //////////////////////
		this.anmSprHati = new AnimatedSprite(153, 117, this.tiledTtrHati,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprHati);

		this.sprHati3 = new Sprite(153, 117, this.ttrRHati3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprHati3);
		this.sprHati3.setVisible(false);

		// Add Kawa
		this.anmSprKawa = new AnimatedSprite(0, 201, this.tiledTtrKawa,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprKawa);

		// Add Kappa
		this.anmSprKappa = new AnimatedSprite(719, 491, this.tiledTtrKappa,
				this.getVertexBufferObjectManager());
		this.anmSprKappa.setVisible(false);
		this.mScene.attachChild(this.anmSprKappa);

		// Add Tako
		this.anmSprTako = new AnimatedSprite(439, 541, this.tiledTtrTako,
				this.getVertexBufferObjectManager());
		this.anmSprTako.setVisible(false);
		this.mScene.attachChild(this.anmSprTako);

		// Add Kappa
		this.anmSprAhuri = new AnimatedSprite(236, 208, this.tiledTtrAhuri,
				this.getVertexBufferObjectManager());
		this.anmSprAhuri.setVisible(false);
		this.mScene.attachChild(this.anmSprAhuri);

		// Add Shippo
		this.anmSprShippo = new AnimatedSprite(363, 64, this.tiledTtrShippo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprShippo);

		// Add Girl
		this.sprBodyGirl = new Sprite(363, 64, this.ttrRBodyGirl,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprBodyGirl);

		this.anmSprHandGirl = new AnimatedSprite(363, 64,
				this.tiledTtrHandGirl, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprHandGirl);

		// Add Kiniku
		this.anmSprKiniku = new AnimatedSprite(363, 64, this.tiledTtrKiniku,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprKiniku);

		this.sprKiamyGirl = new Sprite(363, 64, this.ttrRKiamyGirl,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprKiamyGirl);

		this.anmSprHeaderGirl = new AnimatedSprite(363, 64,
				this.tiledTtrHeaderGirl, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprHeaderGirl);

		// Add Hieptia
		this.sprHieptita = new Sprite(363, 64, this.ttrRHieptita,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprHieptita);

		// Add Boushi
		this.anmSprBoushi = new AnimatedSprite(363, 64, this.tiledTtrBoushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprBoushi);

		// Add Megane
		this.anmSprMegane = new AnimatedSprite(363, 64, this.tiledTtrMegane,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprMegane);

		// Add Mask
		this.anmSprMask = new AnimatedSprite(363, 64, this.tiledTtrMask,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprMask);

		// Add Opa
		this.anmSprOpa = new AnimatedSprite(363, 64, this.tiledTtrOpa,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprOpa);

		// Add Mizu Fix
		this.anmSprMizuFix = new AnimatedSprite(720, 527, this.tiledTtrMizuFix,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprMizuFix);
		this.anmSprMizuFix.setVisible(false);

		// Add Sakana Red
		this.anmSprSakanaRed = new AnimatedSprite(750, 527,
				this.tiledTtrSakanaRed, this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprSakanaRed);

		this.anmSprSakana = new SakanaSprite(0, 0, this.tiledSakanaYellow, 0,
				getVertexBufferObjectManager());
		this.anmSprKawa.attachChild(this.anmSprSakana);
		this.anmSprSakana.setVisible(false);

		this.anmSprMizushi = new AnimatedSprite(0, 0, this.tiledMizushi,
				this.getVertexBufferObjectManager());
		this.anmSprKawa.attachChild(this.anmSprMizushi);
		this.anmSprMizushi.setVisible(false);

		// Add Ami
		this.anmSprAmi = new MySprite(-198, 227, this.tiledTtrAmi,
				getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprAmi);
		this.anmSprAmi.setScale(0.9f);

		// Add Baketsu
		this.anmSprBaketsu = new AnimatedSprite(57, 362, this.tiledTtrBaketsu,
				this.getVertexBufferObjectManager())

		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pTouchAreaLocalX >= 250 || pTouchAreaLocalY <= 80) {
					return false;
				} else {
					int action = pSceneTouchEvent.getAction();
					if (!this.isAnimationRunning() && !isInBaketSu) {

						switch (action) {

						case TouchEvent.ACTION_DOWN:
							pXBaketsuTemp = pTouchAreaLocalX;
							break;
						case TouchEvent.ACTION_MOVE:

							if (pTouchAreaLocalX <= 300
									&& (pTouchAreaLocalX - pXBaketsuTemp >= 8)) {
								mp3Namikarui.play();
								runOnUpdateThread(new Runnable() {

									@Override
									public void run() {

										for (int i = 0; i < 10; i++) {
											if (arrAnmSprSakanaInBaketsu[i] != null) {
												arrAnmSprSakanaInBaketsu[i]
														.detachSelf();
												arrAnmSprSakanaInBaketsu[i]
														.setVisible(false);
											}
										}
									}
								});
								long durations[] = { 600, 600, 700 };
								int frames[] = { 1, 2, 0 };
								this.animate(durations, frames, 0,
										Vol3Sakana.this);
								countInBaketsu = 0;
							}
							break;
						case TouchEvent.ACTION_UP:

							break;
						default:
							break;
						}

					}
				}
				return true;
			}

		};
		this.mScene.attachChild(this.anmSprBaketsu);
		this.mScene.registerTouchArea(this.anmSprBaketsu);

		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3SakanaResource.SOUND_GIMMIC_SAKANA,
				Vol3SakanaResource.IMAGE_GIMMIC_SAKANA,
				mItemSmallTexturePackTextureRegionLibrary);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		this.mScene.registerTouchArea(this.anmSprAmi);
	}

	@Override
	protected void loadKaraokeSound() {

		mp3Powa2 = loadSoundResourceFromSD(Vol3SakanaResource.A15_06_POWA2);
		mp3Kira5 = loadSoundResourceFromSD(Vol3SakanaResource.A15_07_KIRA5);
		mp3Ufo = loadSoundResourceFromSD(Vol3SakanaResource.A15_08_UFO);
		mp3Kira6 = loadSoundResourceFromSD(Vol3SakanaResource.A15_09_KIRA6);
		mp3Powan = loadSoundResourceFromSD(Vol3SakanaResource.A15_10_11_12_POWAN);
		mp3Chapa = loadSoundResourceFromSD(Vol3SakanaResource.A15_13_14_CHAPA);
		//mp3Wa3 = loadSoundResourceFromSD(Vol3SakanaResource.A15_13_15_WA3);
		mp3Botyan = loadSoundResourceFromSD(Vol3SakanaResource.A15_14_BOTYAN);
		mp3Namikarui = loadSoundResourceFromSD(Vol3SakanaResource.A15_14_NAMIKARUI);
		mp3Powa3 = loadSoundResourceFromSD(Vol3SakanaResource.A15_14_POWA2);
		mp3Arere = loadSoundResourceFromSD(Vol3SakanaResource.A15_15_ARERE);
		mp3Piti2 = loadSoundResourceFromSD(Vol3SakanaResource.A15_15_PITI2);
		mp3Reset = loadSoundResourceFromSD(Vol3SakanaResource.A15_15_RESET);
		mp3Kasa2 = loadSoundResourceFromSD(Vol3SakanaResource.A15_05_KASA2);
	}

	@Override
	public synchronized void onResumeGame() {
		this.anmSprKawa.animate(500);
		this.anmSprBoushi.setVisible(false);
		this.anmSprMegane.setVisible(false);
		this.anmSprMask.setVisible(false);
		this.anmSprKiniku.setVisible(false);
		this.anmSprOpa.setVisible(false);
		this.anmSprShippo.setVisible(false);
		this.sprHieptita.setVisible(false);
		this.anmSprSakanaRed.setVisible(false);
		random = new Random();
		randomAllSakana = new Random();
		randomPositionSakana = new Random();
		isTouchGirl = false;
		displaySakana();
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
			for (int i = 0; i < 4; i++) {
				arrVisibleForGirl[i] = false;
			}
			if (this.anmSprHeaderGirl != null) {
				this.anmSprHeaderGirl.setCurrentTileIndex(0);
			}

			if (this.anmSprBoushi != null) {
				this.anmSprBoushi.setVisible(false);
			}
			if (this.sprHieptita != null) {
				this.sprHieptita.setVisible(false);
			}
			if (this.anmSprMegane != null) {
				this.anmSprMegane.setVisible(false);
			}
			if (this.anmSprMask != null) {
				this.anmSprMask.setVisible(false);
			}
			if (this.anmSprKiniku != null) {
				this.anmSprKiniku.setVisible(false);
			}

			if (this.anmSprOpa != null) {
				this.anmSprOpa.setVisible(false);
			}
			if (this.anmSprShippo != null) {
				this.anmSprShippo.setVisible(false);
			}
			if (this.anmSprHandGirl != null) {
				this.anmSprHandGirl.setVisible(true);
			}
			if (this.sprKiamyGirl != null) {
				this.sprKiamyGirl.setVisible(true);
			}
			if (this.anmSprTako != null) {
				anmSprTako.clearEntityModifiers();
				anmSprTako.setVisible(false);
			}
			if (this.anmSprKappa != null) {
				anmSprKappa.clearEntityModifiers();
				anmSprKappa.setVisible(false);
			}
			if (this.anmSprAhuri != null) {
				anmSprAhuri.clearEntityModifiers();
				anmSprAhuri.setVisible(false);
			}

			mScene.unregisterUpdateHandler(handlerSakana);
			mScene.unregisterUpdateHandler(handlerSakanaRed);

			if (this.anmSprSakana != null) {
				anmSprSakana.clearEntityModifiers();
				anmSprSakana.stopAnimation();
				anmSprSakana.setVisible(false);
			}
			if (this.anmSprKiniku != null) {
				anmSprSakanaRed.clearEntityModifiers();
				anmSprSakanaRed.stopAnimation();
				anmSprSakanaRed.setVisible(false);
				anmSprSakanaRed.setPosition(750, 527);
			}
			isTouchGirl = false;
			isStart = false;
			isDrag = false;
			ari1Modifier = null;
			if (this.sprNiji != null) {
				sprNiji.setVisible(false);
			}
			if (this.anmSprSun != null) {
				anmSprSun.setCurrentTileIndex(0);
			}
			if (this.anmSprAri1 != null) {
				anmSprAri1.stopAnimation();
				anmSprAri1.setCurrentTileIndex(0);
			}
			if (this.anmSprAri2 != null) {
				anmSprAri2.clearEntityModifiers();
				anmSprAri2.setVisible(false);
				anmSprAri2.setPosition(370, 189);
			}

			mScene.unregisterUpdateHandler(handlerAri);
			if (this.anmSprHati != null) {
				anmSprHati.setCurrentTileIndex(0);
			}
			if (this.sprHati3 != null) {
				sprHati3.setVisible(false);
				sprHati3.setPosition(153, 117);
			}
			if (this.sprUfo != null) {
				sprUfo.clearEntityModifiers();
				sprUfo.setPosition(10, 10);
				sprUfo.setVisible(false);
			}
			if (this.ufoModifier != null) {
				ufoModifier = null;
			}
			if (this.anmSprKiniku != null) {
				anmSprAmi.setScale(0.9f);
				anmSprAmi.setPosition(-198, 227);
				anmSprAmi.setCurrentTileIndex(0);
			}

			for (int i = 0; i < 11; i++) {
				if (arrAnmSprSakanaInBaketsu[i] != null) {
					arrAnmSprSakanaInBaketsu[i].setVisible(false);
				}
			}
			countInBaketsu = 0;
			mScene.clearUpdateHandlers();
		super.onPauseGame();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(this.sprBackground2, 0,
					(int) this.sprBackground2.getHeight() / 3,
					(int) this.sprBackground2.getWidth(),
					(int) this.sprBackground2.getHeight() * 2 / 3, pX, pY)) {
				modifiUfo();
			} else if (checkContains(this.anmSprAri1, 0, 0,
					(int) this.anmSprAri1.getWidth(),
					(int) this.anmSprAri1.getHeight() * 2 / 3, pX, pY)) {
				animateAri1();
			} else if (checkContains(this.anmSprHati, 0, 0,
					(int) this.anmSprHati.getWidth(),
					(int) this.anmSprHati.getHeight() * 2 / 3, pX, pY)) {
				touchHati();
			} else if (checkContains(this.sprKumo, 0,
					(int) this.sprKumo.getHeight() / 3,
					(int) this.sprKumo.getWidth() / 3,
					(int) this.sprKumo.getHeight() * 2 / 3, pX, pY)
					|| checkContains(this.sprKumo,
							(int) this.sprKumo.getWidth() * 3 / 4,
							(int) this.sprKumo.getHeight() * 3 / 4,
							(int) this.sprKumo.getWidth(),
							(int) this.sprKumo.getHeight(), pX, pY)) {
				touchKumo();
			}

			else if (checkContains(this.anmSprSun, 0, 0,
					(int) this.anmSprSun.getWidth(),
					(int) this.anmSprSun.getHeight(), pX, pY)) {
				touchSun();
			} else if (checkContains(this.sprBodyGirl, 199, 94, 199 + 264,
					94 + 56, pX, pY)) {
				modifiSakanaRed(0);
			} else if (checkContains(this.sprBodyGirl, 199, 150, 199 + 264,
					150 + 42, pX, pY)) {
				modifiSakanaRed(1);
			} else if (checkContains(this.sprBodyGirl, 199, 192, 199 + 264,
					192 + 42, pX, pY)) {
				modifiSakanaRed(2);
			} else if (checkContains(this.sprBodyGirl, 199, 234, 199 + 264,
					234 + 42, pX, pY)) {
				modifiSakanaRed(3);
			} else if (checkContains(this.sprBodyGirl, 199, 276, 199 + 90,
					276 + 110, pX, pY)) {
				modifiSakanaRed(4);
			} else if (checkContains(this.sprBodyGirl, 379, 276, 379 + 120,
					276 + 84, pX, pY)) {
				modifiSakanaRed(5);
			} else if (checkContains(this.sprBodyGirl, 289, 276, 289 + 90,
					276 + 84, pX, pY)) {
				modifiSakanaRed(6);
			} else if (checkContains(this.sprBodyGirl, 289, 360, 289 + 110,
					360 + 120, pX, pY)) {
				modifiSakanaRed(7);
			} else if (checkContains(this.sprBodyGirl, 0, 269, 276, 366, pX, pY)) {
				if (this.anmSprHandGirl.isVisible()) {
					resetAllForGirl();
				}
			} else if (checkContains(this.sprBodyGirl, 62, 62, 165, 285, pX, pY)) {
				if (!this.anmSprHandGirl.isVisible()) {
					resetAllForGirl();
				}
			} else if (checkContainsPolygon(this.anmSprKawa, arrPointerKawa, 7,
					pX, pY)) {
				touchKawa(pX, pY);
			}

		}
		return false;
	}

	@Override
	public void combineGimmic3WithAction() {
		modifiUfo();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(this.anmSprAri1)) {
			// modifiAri2();
		}
		if (pAnimatedSprite.equals(this.anmSprKappa)) {
			this.anmSprKappa.setVisible(false);
		}
		if (pAnimatedSprite.equals(this.anmSprTako)) {
			float duration = (960 - anmSprTako.getX()) / (960 - 439) * 2;
			if (anmSprTako.getY() <= 320) {
				this.anmSprTako
						.registerEntityModifier(takoModifier = new MoveModifier(
								duration, this.anmSprTako.getX(), 960,
								this.anmSprTako.getY(),
								this.anmSprTako.getY() + 150));
			} else {
				this.anmSprTako
						.registerEntityModifier(takoModifier = new MoveModifier(
								duration, this.anmSprTako.getX(), 960,
								this.anmSprTako.getY(), this.anmSprTako.getY()));
			}

			if (takoModifier != null) {
				takoModifier.addModifierListener(this);
			}
		}
		if (pAnimatedSprite.equals(this.anmSprAhuri)) {
			this.anmSprAhuri.setFlippedVertical(true);
			float duration = (960 - anmSprAhuri.getX()) / (960 - 236) * 2;
			if (anmSprAhuri.getY() <= 320) {
				this.anmSprAhuri
						.registerEntityModifier(ahuriModifier = new MoveModifier(
								duration, this.anmSprAhuri.getX(), 960,
								this.anmSprAhuri.getY(), this.anmSprAhuri
										.getY() + 150));
			} else {
				this.anmSprAhuri
						.registerEntityModifier(ahuriModifier = new MoveModifier(
								duration, this.anmSprAhuri.getX(), 960,
								this.anmSprAhuri.getY(), this.anmSprAhuri
										.getY()));
			}
			if (ahuriModifier != null) {
				ahuriModifier.addModifierListener(this);
			}
		}

		if (pAnimatedSprite.equals(anmSprMizushi)) {

			if (!isInBaketSu) {
				isDrag = true;
				this.anmSprMizushi.setVisible(false);
				this.anmSprSakana
						.registerEntityModifier(sakanaModifier = new SequenceEntityModifier(
								new MoveYModifier(1.8f, this.anmSprSakana
										.getY(), this.anmSprSakana.getY() - 170),
								new MoveYModifier(0.1f, this.anmSprSakana
										.getY() - 170,
										this.anmSprSakana.getY() - 170),
								new MoveYModifier(1.8f, this.anmSprSakana
										.getY() - 170, this.anmSprSakana.getY())));
				if (sakanaModifier != null) {
					sakanaModifier.addModifierListener(this);
				}
			}
		}

	}

	// //////////////////////////////////////////
	// Ufo Modifier
	// /////////////////////////////////////////
	private void modifiUfo() {

		if (ufoModifier == null) {

			ufoModifier = new ParallelEntityModifier(new LoopEntityModifier(
					new SequenceEntityModifier(
							new RotationModifier(0.5f, 0, 15),
							new RotationModifier(0.5f, 15, -15),
							new RotationModifier(0.5f, -15, 0))),
					new SequenceEntityModifier(new MoveModifier(2.5f, sprUfo
							.getX(), 368, sprUfo.getY(), 74, EaseQuartOut
							.getInstance()), new MoveModifier(1.0f, 368, 454,
							74, -42, EaseQuartOut.getInstance()),
							new MoveModifier(1.0f, 454, 534, -12, 86,
									EaseQuartOut.getInstance()),
							new MoveModifier(0.8f, 534, 986, 86, -10,
									EaseQuartOut.getInstance())));
			mp3Ufo.play();
			sprUfo.setVisible(true);
			sprUfo.registerEntityModifier(ufoModifier);
			sprUfo.registerUpdateHandler(new IUpdateHandler() {

				@Override
				public void reset() {

				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					if (sprUfo.getX() == 986) {
						sprUfo.clearEntityModifiers();
						sprUfo.setVisible(false);
						sprUfo.setPosition(10, 10);
						ufoModifier = null;
					}
				}
			});
			if (ufoModifier != null) {
				ufoModifier.addModifierListener(this);
			}
		}
		return;
	}

	// /////////////////////////////////////////////
	// Ari 1 Animation
	// ////////////////////////////////////////////
	private void animateAri1() {
		if (!anmSprAri1.isAnimationRunning()
				&& (ari1Modifier == null || ari1Modifier.isFinished())) {
			this.anmSprAri1.animate(600, false, this);
			mp3Kasa2.play();
			mScene.unregisterUpdateHandler(handlerAri);
			mScene.registerUpdateHandler(handlerAri = new TimerHandler(1.8f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							modifiAri2();
						}
					}));
		}
		return;
	}

	// /////////////////////////////////////////////
	// Ari 1 Modifier
	// ////////////////////////////////////////////
	private void modifiAri2() {

		this.anmSprAri2.setVisible(true);
		this.anmSprAri2.setAlpha(1.0f);
		if (ari1Modifier == null || ari1Modifier.isFinished()) {
			this.anmSprAri2
					.registerEntityModifier(ari1Modifier = new ParallelEntityModifier(
							new SequenceEntityModifier(new MoveModifier(2.5f,
									this.anmSprAri2.getX(), this.anmSprAri2
											.getX() - 230, this.anmSprAri2
											.getY(),
									this.anmSprAri2.getY() - 100),
									new AlphaModifier(1.5f, 1.0f, 0.2f)),
							new ScaleModifier(2.5f, 1.0f, 0.5f)));
			this.anmSprAri2.animate(400);
		}
		if (ari1Modifier != null) {
			ari1Modifier.addModifierListener(this);
		}
		return;
	}

	// ///////////////////////////////////////////////
	// Ari 2 Animate
	// ///////////////////////////////////////////////
	private void touchHati() {
		if (!this.anmSprHati.isAnimationRunning()
				&& (hatiModifier == null || hatiModifier.isFinished())) {
			mp3Powa2.play();
			int rdAri2 = new Random().nextInt(2);
			if (rdAri2 == 0) {
				if (!this.anmSprHati.isAnimationRunning()) {
					long durations[] = { 1000, 50 };
					int frames[] = { 1, 0 };
					this.anmSprHati.animate(durations, frames, 0);
				}
			} else {
				if (hatiModifier == null || hatiModifier.isFinished()) {
					this.sprHati3.setVisible(true);
					this.sprHati3
							.registerEntityModifier(hatiModifier = new MoveModifier(
									2.5f, this.sprHati3.getX(), 970,
									this.sprHati3.getY(), -this.sprHati3
											.getHeight()));
				}
				if (hatiModifier != null) {
					hatiModifier.addModifierListener(this);
				}
			}
		}
		return;
	}

	private void touchSun() {
		if (!this.anmSprSun.isAnimationRunning()) {
			if (!this.anmSprSun.isAnimationRunning()) {
				mp3Kira5.play();
				int frames[] = { 0, 1, 0 };
				long durations[] = { 300, 1500, 300 };
				this.anmSprSun.animate(durations, frames, 0, this);
			}
		}
		return;
	}

	private void touchKumo() {
		this.sprNiji.setVisible(true);
		if (nijiModifier == null || nijiModifier.isFinished()) {
			mp3Kira6.play();
			if (this.sprNiji.getAlpha() == 0.0f) {
				this.sprNiji
						.registerEntityModifier(nijiModifier = new AlphaModifier(
								1.6f, 0.0f, 1.0f));
			} else {
				this.sprNiji
						.registerEntityModifier(nijiModifier = new AlphaModifier(
								1.6f, 1.0f, 0.0f));
			}
			if (nijiModifier != null) {
				nijiModifier.addModifierListener(this);
			}
		}
	}

	private void touchKawa(float pX, float pY) {

		int random = new Random().nextInt(3);
		if (random == 0) {
			if (!this.anmSprKappa.isAnimationRunning()) {
				mp3Powan.play();
				this.anmSprKappa.setPosition(pX - this.anmSprKappa.getWidth()
						/ 2, pY - this.anmSprKappa.getHeight() / 2);
				this.anmSprKappa.setVisible(true);
				int frames[] = { 0, 1, 0 };
				long durations[] = { 500, 500, 500 };
				this.anmSprKappa.animate(durations, frames, 0, this);
			}
		} else if (random == 1) {
			if (!this.anmSprTako.isAnimationRunning()
					&& (takoModifier == null || takoModifier.isFinished())) {
				mp3Powan.play();
				this.anmSprTako.setPosition(
						pX - this.anmSprTako.getWidth() / 2, pY
								- this.anmSprTako.getHeight() / 2);
				this.anmSprTako.setVisible(true);
				this.anmSprTako.animate(300, false, this);
			}
		} else if (random == 2) {
			if (!this.anmSprAhuri.isAnimationRunning()
					&& (ahuriModifier == null || ahuriModifier.isFinished())) {
				mp3Powan.play();
				this.anmSprAhuri.setFlippedVertical(true);
				this.anmSprAhuri.setPosition(pX - this.anmSprAhuri.getWidth()
						/ 2, pY - this.anmSprAhuri.getHeight() / 2);
				this.anmSprAhuri.setVisible(true);
				this.anmSprAhuri.animate(300, false, this);
			}
		}
	}

	// //////////////////////////////////////////////////////////
	// Change All For Girl
	// /////////////////////////////////////////////////////////
	private void changeForGirl(int index) {
		if (index < 4) {
			arrVisibleForGirl[index] = true;
		}
		if (index == 0) {
			int rd = random.nextInt(3);
			if (rd == anmSprBoushi.getCurrentTileIndex()) {
				if (rd == 2) {
					rd = 0;
				} else {
					rd += 1;
				}
			}
			this.anmSprBoushi.setCurrentTileIndex(rd);
		} else if (index == 1) {

		} else if (index == 2) {
			int rd = random.nextInt(3);
			if (rd == anmSprMegane.getCurrentTileIndex()) {
				if (rd == 2) {
					rd = 0;
				} else {
					rd += 1;
				}
			}
			this.anmSprMegane.setCurrentTileIndex(rd);
		} else if (index == 3) {
			int rd = random.nextInt(3);
			if (rd == anmSprMask.getCurrentTileIndex()) {
				if (rd == 2) {
					rd = 0;
				} else {
					rd += 1;
				}
			}
			this.anmSprMask.setCurrentTileIndex(rd);
		}

		else if (index == 4 || index == 5) {
			this.anmSprHandGirl.setVisible(false);
			this.anmSprKiniku.setVisible(true);
			this.anmSprKiniku.setCurrentTileIndex(this.anmSprKiniku
					.getCurrentTileIndex() == 0 ? 1 : 0);
		} else if (index == 6) {
			this.sprKiamyGirl.setVisible(false);
			int rd = random.nextInt(3);
			if (rd == anmSprOpa.getCurrentTileIndex()) {
				if (rd == 2) {
					rd = 0;
				} else {
					rd += 1;
				}
			}
			this.anmSprOpa.setVisible(true);
			this.anmSprOpa.setCurrentTileIndex(rd);
		} else if (index == 7) {
			int rd = random.nextInt(3);
			if (rd == anmSprShippo.getCurrentTileIndex()) {
				if (rd == 2) {
					rd = 0;
				} else {
					rd += 1;
				}
			}
			this.anmSprShippo.setVisible(true);
			this.anmSprShippo.setCurrentTileIndex(rd);
		}
		return;
	}

	// ///////////////////////////////////////////////////////
	// Sakana Red Modifier
	// ///////////////////////////////////////////////////////
	private void modifiSakanaRed(final int index) {
		if (!isTouchGirl) {

			isTouchGirl = true;
			anmSprSakanaRed.setVisible(false);
			anmSprMizuFix.setVisible(true);
			// anmSprMizuFix.animate(200, false);
			final long durations[] = { 300, 300, 300 };
			final int frames[] = { 0, 1, 2 };
			final int frames2[] = { 1, 0, 1 };
			mScene.unregisterUpdateHandler(handlerSakanaRed);
			mScene.registerUpdateHandler(handlerSakanaRed = new TimerHandler(
					0.3f, new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							anmSprSakanaRed.setVisible(true);
						}
					}));
			anmSprSakanaRed.animate(durations, frames2, -1);
			this.anmSprMizuFix.animate(durations, frames, 0,
					sakanaAnimateListener = new IAnimationListener() {

						@Override
						public void onAnimationFinished(
								AnimatedSprite pAnimatedSprite) {
							mp3Arere.play();
							anmSprBoushi.setVisible(false);
							anmSprMegane.setVisible(false);
							anmSprMask.setVisible(false);
							sprHieptita.setVisible(false);
							anmSprMizuFix.setVisible(false);
							anmSprHeaderGirl.setCurrentTileIndex(1);

							anmSprSakanaRed.setCurrentTileIndex(1);
							float duration = ((527 - (pointerForGirlTouch[1][index] + 64)) * 3.2f)
									/ (527 - (pointerForGirlTouch[1][0] + 64));
							anmSprSakanaRed
									.registerEntityModifier(sakanaRedModifier = new SequenceEntityModifier(
											new MoveModifier(
													duration,
													anmSprSakanaRed.getX(),
													pointerForGirlTouch[0][index] + 363,
													anmSprSakanaRed.getY(),
													pointerForGirlTouch[1][index] + 64),
											new DelayModifier(0.3f)));
							if (sakanaRedModifier != null) {
								sakanaRedModifier
										.addModifierListener(sakanaModifierListener = new IModifierListener<IEntity>() {

											@Override
											public void onModifierFinished(
													IModifier<IEntity> pModifier,
													IEntity pItem) {

												mp3Piti2.play();
												anmSprSakanaRed
														.setCurrentTileIndex(0);
												// anmSprHeaderGirl.setCurrentTileIndex(2);

												mScene.unregisterUpdateHandler(handlerSakanaRed);
												mScene.registerUpdateHandler(handlerSakanaRed = new TimerHandler(
														0.5f,
														new ITimerCallback() {

															@Override
															public void onTimePassed(
																	TimerHandler arg0) {
																hasInGirl = true;
																isTouchGirl = false;
																anmSprSakanaRed
																		.setVisible(false);
																anmSprSakanaRed
																		.setPosition(
																				750,
																				527);
																changeForGirl(index);
																reDisplay();
															}
														}));
											}

											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {

											}
										});
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

		return;
	}

	// /////////////////////////////////////////////////////////
	// ReDisplay For Girl
	// /////////////////////////////////////////////////////////
	private void reDisplay() {
		if (!isTouchGirl) {
			this.anmSprHeaderGirl.setCurrentTileIndex(0);
			for (int i = 0; i < 4; i++) {
				switch (i) {
				case 0:
					this.anmSprBoushi.setVisible(arrVisibleForGirl[i]);
					break;
				case 1:
					this.sprHieptita.setVisible(arrVisibleForGirl[i]);
					break;
				case 2:
					this.anmSprMegane.setVisible(arrVisibleForGirl[i]);
					break;
				case 3:
					this.anmSprMask.setVisible(arrVisibleForGirl[i]);
					break;
				default:
					break;
				}
			}
		} else {
			this.anmSprHeaderGirl.setCurrentTileIndex(1);
			for (int i = 0; i < 4; i++) {
				switch (i) {
				case 0:
					this.anmSprBoushi.setVisible(false);
					break;
				case 1:
					this.sprHieptita.setVisible(false);
					break;
				case 2:
					this.anmSprMegane.setVisible(false);
					break;
				case 3:
					this.anmSprMask.setVisible(false);
					break;
				default:
					break;
				}
			}
		}

		return;
	}

	private void resetAllForGirl() {
		if (!anmSprSakanaRed.isVisible() && hasInGirl) {
			hasInGirl = false;
			mp3Reset.play();
			for (int i = 0; i < 4; i++) {
				arrVisibleForGirl[i] = false;
			}
			this.anmSprBoushi.setVisible(false);
			this.sprHieptita.setVisible(false);
			this.anmSprMegane.setVisible(false);
			this.anmSprMask.setVisible(false);
			this.anmSprKiniku.setVisible(false);
			this.anmSprOpa.setVisible(false);
			this.anmSprShippo.setVisible(false);
			this.anmSprHandGirl.setVisible(true);
			this.sprKiamyGirl.setVisible(true);
		}
		return;
	}

	private void displaySakana() {
		if (isStart) {
			isInBaketSu = false;
			int rd = randomAllSakana.nextInt(4);
			int rdPosition = randomPositionSakana.nextInt(9);
			if (anmSprSakana != null) {
				anmSprSakana.die();
			}

			switch (rd) {
			case 0:
				this.anmSprSakana = new SakanaSprite(
						arrPoiterSakana[0][rdPosition],
						arrPoiterSakana[1][rdPosition], tiledSakanaYellow, 0,
						getVertexBufferObjectManager());

				break;
			case 1:
				this.anmSprSakana = new SakanaSprite(
						arrPoiterSakana[0][rdPosition],
						arrPoiterSakana[1][rdPosition], tiledSakanaGreen, 1,
						getVertexBufferObjectManager());
				break;
			case 2:
				this.anmSprSakana = new SakanaSprite(
						arrPoiterSakana[0][rdPosition],
						arrPoiterSakana[1][rdPosition], tiledSakanaPurple, 2,
						getVertexBufferObjectManager());
				break;
			case 3:
				this.anmSprSakana = new SakanaSprite(
						arrPoiterSakana[0][rdPosition],
						arrPoiterSakana[1][rdPosition], tiledSakanaBlue, 3,
						getVertexBufferObjectManager());
				break;
			default:
				break;
			}
			mp3Chapa.play();

			this.anmSprSakana.setVisible(true);
			this.anmSprMizushi.setPosition(this.anmSprSakana.getX() - 20,
					this.anmSprSakana.getY() + 10);
			this.anmSprMizushi.setVisible(true);
			this.anmSprMizushi.setCurrentTileIndex(0);
			long durations[] = { 500, 500, 500 };
			int frames[] = { 0, 1, 2 };
			this.anmSprMizushi.animate(durations, frames, 0, this);
			int frames2[] = { 1, 0, 1 };
			this.anmSprSakana.animate(durations, frames2, 0, this);
			this.anmSprKawa.attachChild(this.anmSprSakana);

		}
		mScene.unregisterUpdateHandler(handlerSakana);
		mScene.registerUpdateHandler(handlerSakana = new TimerHandler(5.8f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						isStart = true;
						displaySakana();
					}
				}));
		return;
	}

	// //////////////////////////////////////////////////////////////
	// Inner Class
	// //////////////////////////////////////////////////////////////

	private class SakanaSprite extends AnimatedSprite {
		private int index;

		public int getIndex() {
			return this.index;
		}

		public SakanaSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int index,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
			this.index = index;
		}

		public void die() {
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {

					SakanaSprite.this.clearEntityModifiers();
					SakanaSprite.this.detachSelf();
					SakanaSprite.this.setVisible(false);
				}
			});

		}
	}

	private class MySprite extends AnimatedSprite {

		public MySprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			int action = pSceneTouchEvent.getAction();
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			if (!anmSprBaketsu.isAnimationRunning()) {
				switch (action) {
				case TouchEvent.ACTION_DOWN:
					this.setScale(1.1f);
					break;
				case TouchEvent.ACTION_MOVE:

					this.setPosition(pX - this.getWidth() * 2 / 3,
							pY - this.getHeight() / 2);

					if (Vol3Sakana.this.anmSprSakana.isVisible()
							&& !isInBaketSu && isDrag) {

						if (this.getX() + this.getWidth() >= Vol3Sakana.this.anmSprSakana
								.getX()
								+ Vol3Sakana.this.anmSprSakana.getWidth() / 2
								&& this.getX() + this.getWidth() <= Vol3Sakana.this.anmSprSakana
										.getX()
										+ Vol3Sakana.this.anmSprSakana
												.getWidth()
								&& this.getY() <= Vol3Sakana.this.anmSprSakana
										.getY() + 201
								&& (this.getY() + this.getHeight()) >= (Vol3Sakana.this.anmSprSakana
										.getY() + 201)) {
							anmSprBoushi.setVisible(false);
							anmSprMegane.setVisible(false);
							anmSprMask.setVisible(false);
							sprHieptita.setVisible(false);
							anmSprMizuFix.setVisible(false);
							anmSprHeaderGirl.setCurrentTileIndex(2);

							mp3Botyan.play();
							mScene.unregisterUpdateHandler(handlerSakana);
							this.setCurrentTileIndex(1);
							Vol3Sakana.this.anmSprSakana.setCurrentTileIndex(1);
							isInBaketSu = true;
							this.stopAnimation();
							Vol3Sakana.this.anmSprMizushi.stopAnimation();
							Vol3Sakana.this.anmSprMizushi.setVisible(false);
							Vol3Sakana.this.anmSprSakana.stopAnimation();
							Vol3Sakana.this.anmSprSakana.clearEntityModifiers();
							Vol3Sakana.this.anmSprKawa
									.detachChild(Vol3Sakana.this.anmSprSakana);
							Vol3Sakana.this.anmSprSakana.setScale(0.9f);
							Vol3Sakana.this.anmSprAmi
									.attachChild(Vol3Sakana.this.anmSprSakana);
							Vol3Sakana.this.anmSprSakana.setPosition(
									this.getWidth() * 2 / 3 - 20, 60);
							//
						}
					}
					break;
				case TouchEvent.ACTION_UP:
					this.setScale(0.9f);
					Vol3Sakana.this.anmSprSakana.setScale(1.0f);
					if (!isInBaketSu) {
						amiModifier = new MoveModifier(0.2f, this.getX(), -198,
								this.getY(), 207);
						this.registerEntityModifier(amiModifier);
						if (amiModifier != null) {
							amiModifier.addModifierListener(Vol3Sakana.this);
						}
					} else {
						amiModifier = new MoveModifier(0.2f, this.getX(), -128,
								this.getY(), 207);
						this.clearEntityModifiers();
						this.registerEntityModifier(amiModifier);
						if (amiModifier != null) {
							amiModifier.addModifierListener(Vol3Sakana.this);
						}
						if (!isInBaketSu) {
							Vol3Sakana.this.anmSprSakana.setPosition(
									this.getWidth() * 2 / 3 - 20, 60);
						}
					}
					break;
				default:
					break;
				}
			}
			return true;
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
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity arg1) {
		if (pModifier.equals(ufoModifier)) {
			sprUfo.setVisible(false);
			sprUfo.setPosition(10, 10);

		}
		if (pModifier.equals(ari1Modifier)) {
			this.anmSprAri2.stopAnimation();
			this.anmSprAri2.setCurrentTileIndex(0);
			this.anmSprAri2.setPosition(370, 189);
			this.anmSprAri2.setVisible(false);
		}
		if (pModifier.equals(hatiModifier)) {
			this.sprHati3.setVisible(false);
			this.sprHati3.setPosition(153, 117);
		}
		if (pModifier.equals(nijiModifier)) {
			if (this.sprNiji.getAlpha() == 0.0f) {
				this.sprNiji.setVisible(false);
			}
		}
		if (pModifier.equals(takoModifier)) {
			this.anmSprTako.setVisible(false);
			this.anmSprTako.setPosition(439, 541);
			this.anmSprTako.setCurrentTileIndex(0);
		}
		if (pModifier.equals(ahuriModifier)) {
			this.anmSprAhuri.setFlippedVertical(false);
			this.anmSprAhuri.setVisible(false);
			this.anmSprAhuri.setPosition(236, 208);
			this.anmSprAhuri.setCurrentTileIndex(0);
		}
		if (pModifier.equals(amiModifier)) {

			if (!isInBaketSu) {
				this.anmSprAmi.setScale(0.9f);
			} else {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						anmSprSakana.detachSelf();
						mScene.attachChild(anmSprSakana);
						anmSprSakana.setPosition(123, 341);

						anmSprSakana
								.registerEntityModifier(sakanaModifier = new SequenceEntityModifier(new IEntityModifierListener() {
		                                                    @Override
		                                                    public void onModifierStarted(IModifier<IEntity> paramIModifier,
		                                                            IEntity paramT) {
		                                                    }
		                                                    @Override
		                                                    public void onModifierFinished(IModifier<IEntity> paramIModifier,
		                                                            IEntity paramT) {

		                                                        if (Vol3Sakana.this.anmSprSakana.isVisible()) {
		                                                                Vol3Sakana.this.anmSprMizushi.setVisible(false);
		                                                                if (Vol3Sakana.this.countInBaketsu < 10) {
		                                                                    Vol3Sakana.this.mp3Powa3.play();
		                                                                        switch (anmSprSakana.getIndex()) {
		                                                                        case 0:
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu] = new AnimatedSprite(
		                                                                                                arrPoiterSakanaInBaketsu[0][countInBaketsu],
		                                                                                                arrPoiterSakanaInBaketsu[1][countInBaketsu],
		                                                                                                tiledSakanaYellowInBaketsu,
		                                                                                                getVertexBufferObjectManager());
		                                                                            Vol3Sakana.this.anmSprBaketsu
		                                                                                                .attachChild(Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]);
		                                                                                break;
		                                                                        case 1:
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu] = new AnimatedSprite(
		                                                                                                arrPoiterSakanaInBaketsu[0][countInBaketsu],
		                                                                                                arrPoiterSakanaInBaketsu[1][countInBaketsu],
		                                                                                                tiledSakanaGreenInBaketsu,
		                                                                                                getVertexBufferObjectManager());
		                                                                            Vol3Sakana.this.anmSprBaketsu
		                                                                                                .attachChild(Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]);
		                                                                                break;
		                                                                        case 2:
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu] = new AnimatedSprite(
		                                                                                                arrPoiterSakanaInBaketsu[0][countInBaketsu],
		                                                                                                arrPoiterSakanaInBaketsu[1][countInBaketsu],
		                                                                                                tiledSakanaPurpleInBaketsu,
		                                                                                                getVertexBufferObjectManager());
		                                                                            Vol3Sakana.this.anmSprBaketsu
		                                                                                                .attachChild(Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]);
		                                                                                break;
		                                                                        case 3:
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu] = new AnimatedSprite(
		                                                                                                arrPoiterSakanaInBaketsu[0][countInBaketsu],
		                                                                                                arrPoiterSakanaInBaketsu[1][countInBaketsu],
		                                                                                                tiledSakanaBlueInBaketsu,
		                                                                                                getVertexBufferObjectManager());
		                                                                            Vol3Sakana.this.anmSprBaketsu
		                                                                                                .attachChild(Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]);
		                                                                                break;

		                                                                        default:
		                                                                                break;
		                                                                        }
		                                                                        if (Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu] != null) {
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]
		                                                                                                .setVisible(true);
		                                                                            Vol3Sakana.this.arrAnmSprSakanaInBaketsu[countInBaketsu]
		                                                                                                .animate(500);
		                                                                        }
		                                                                        Vol3Sakana.this.countInBaketsu++;
		                                                                        Log.d(TAG, countInBaketsu + "");
		                                                                }
		                                                                reDisplay();
		                                                                Vol3Sakana.this. anmSprSakana.setVisible(false);
		                                                                Vol3Sakana.this.anmSprSakana.die();
		                                                                Vol3Sakana.this.mScene.registerUpdateHandler(handlerSakana = new TimerHandler(
		                                                                                4.5f, new ITimerCallback() {

		                                                                                        @Override
		                                                                                        public void onTimePassed(TimerHandler arg0) {
		                                                                                                displaySakana();
		                                                                                        }
		                                                                                }));
		                                                        }
		                                                    }
		                                                },
										new MoveModifier(0.1f, Vol3Sakana.this.anmSprSakana
												.getX(), Vol3Sakana.this.anmSprSakana.getX(),
												Vol3Sakana.this.anmSprSakana.getY(),
												Vol3Sakana.this.anmSprSakana.getY() + 30),
										new DelayModifier(1.0f)));
						if (sakanaModifier != null) {
						    Vol3Sakana.this.sakanaModifier.addModifierListener(Vol3Sakana.this);
						}

						Vol3Sakana.this.anmSprAmi.setCurrentTileIndex(0);
						Vol3Sakana.this.anmSprAmi.setPosition(-198, 207);
						Vol3Sakana.this.anmSprAmi.setScale(0.9f);
					}
				});

			}

		}
		if (pModifier.equals(sakanaModifier)) {
			Log.d(TAG, "--------------Modifier Finished--------------");
			isDrag = false;
			if (!isInBaketSu) {
				this.anmSprSakana.setVisible(false);
				this.anmSprMizushi.stopAnimation();
				this.anmSprMizushi.setVisible(true);
				this.anmSprMizushi.setCurrentTileIndex(2);
			}
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		// TODO Auto-generated method stub

	}
}
