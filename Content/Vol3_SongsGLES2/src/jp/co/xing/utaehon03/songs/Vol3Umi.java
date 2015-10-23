/* Umi.java
 * Create on Feb 13, 2012
 */
package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3UmiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Umi extends BaseGameActivity implements IOnSceneTouchListener,
		IAnimationListener, IModifierListener<IEntity> {
	
	private TexturePack ttpUmi1, ttpUmi2, ttpUmi3;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary ttpUmiLibrary1, ttpUmiLibrary2,
			ttpUmiLibrary3;

	private ITextureRegion tttrBackground_1, ttrLightBackground,
			ttrBackground_2, ttrBackground_3, ttrFrontRock, ttrBackRock,
			ttrFish1, ttrMiniFish1, ttrMiniFish2, ttrMiniFish3, ttrMiniFish4,
			ttrFish2, ttrFish3, ttrFish4, ttrFish5, ttrFish6, ttrShark,
			ttrWhale1, ttrWhale2, ttrUrchin;

	private Sprite sBackground, sLightBackground, sBackground_2, sBackground_3,
			sBackRock, sFrontRock, sFish1, sFish2, sFish3, sFish4, sFish5,
			sFish6, sMiniFish1, sMiniFish2, sMiniFish3, sMiniFish4, sShark,
			sWhale1, sWhale2, sUrchin;

	private TiledTextureRegion tttrMiniWhale, tttrShip, tttrMedusa, tttrShell,
			tttrOctopus, tttrBox, tttrHermitcrab, tttrStarFish, tttrSeaanemone,
			tttrPrawn, tttrManta, tttrSeaHorse, tttrSquid, tttrGlobeFish,
			tttrLamp, tttrAnago, tttrKarei, tttrTsunodashi, tttrFrontWeed,
			tttrMiddleWeed, tttrBigWeed, tttrTobiuo;

	private AnimatedSprite aniMiniWhale, aniShip, aniMedusa, aniShell,
			aniOctopus, aniBox, aniHermitcrab, aniStarFish, aniSeaanemone,
			aniPrawn, aniManta, aniSeaHorse, aniSquid, aniGlobeFish, aniLamp,
			aniAnago, aniKarei, aniTsunodashi, aniFrontWeed, aniMiddleWeed,
			aniBigWeed, aniTobiuo;

	boolean istouchShark, istouchTobiuo, istouchWhale, istouchShip,
			istouchUrchin, istouchShell, istouchPrawn, istouchSquid,
			istouchMedusa, istouchTsunodashi, istouchSeaanemone, istouchManta,
			istouchSeaHorse, istouchAnago, istouchGlobeFish, istouchLamp,
			istouchOctopus, istouchHermitcrab, istouchKarei;

	private SequenceEntityModifier sharkModifier, tobiuoModifier,
			medusaModifier, shipModifier, prawnModifier, squidModifier,
			tsunodashiModifier;
	private ParallelEntityModifier urchinModifier;
	private int currentBg = 0, currentBox = 0, currentStarFish = 0,
			currentHermitcrab = 0, currentKarei = 0;

	private Sound A3_04_DEDEN2, A3_05_PAKAKIRA, A3_06_TAKARABAKO, A3_07_SYU2,
			A3_08_KASA, A3_09_DUL, A3_10_BOKO, A3_11_POWA, A3_12_POMI,
			A3_13_JU, A3_14_KORO, A3_15_POWA3, A3_16_POWA2, A3_17_PYONPASYA,
			A3_18_PYU2, A3_19_POWA4, A3_20_BO, A3_21_ZABA, A3_22_BO,
			A3_23_HARP, A3_24_KASA, A3_25_NEWN;

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(sBackground = new Sprite(0,
				0, CAMERA_WIDTH, CAMERA_HEIGHT, tttrBackground_1, this
						.getVertexBufferObjectManager())));
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// display back ground
		sBackground_2 = new Sprite(0, 0, ttrBackground_2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground_2);
		sBackground_2.setVisible(false);
		sBackground_3 = new Sprite(0, 0, ttrBackground_3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground_3);
		sBackground_3.setVisible(false);
		// display light background
		sLightBackground = new Sprite(0, 0, ttrLightBackground,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLightBackground);
		// display back rock
		sBackRock = new Sprite(0, 460, ttrBackRock,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackRock);
		// display big weed
		aniBigWeed = new AnimatedSprite(6, 261, tttrBigWeed,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniBigWeed);
		// display middle weed
		aniMiddleWeed = new AnimatedSprite(0, 226, tttrMiddleWeed,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniMiddleWeed);
		// display octopus
		aniOctopus = new AnimatedSprite(154, 419, tttrOctopus,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniOctopus);
		// display box
		aniBox = new AnimatedSprite(29, 422, tttrBox,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniBox);
		// display hermitcrab
		aniHermitcrab = new AnimatedSprite(313, 416, tttrHermitcrab,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniHermitcrab);
		// display lamp
		aniLamp = new AnimatedSprite(194, 303, tttrLamp,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniLamp);
		// display manta
		aniManta = new AnimatedSprite(620, 212, tttrManta,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniManta);
		// display shell
		aniShell = new AnimatedSprite(30, 550, tttrShell,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniShell);
		// display urchin
		sUrchin = new Sprite(527, 446, ttrUrchin,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sUrchin);
		// display front rock
		sFrontRock = new Sprite(149, 452, ttrFrontRock,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFrontRock);
		// display anago
		aniAnago = new AnimatedSprite(597, 410, tttrAnago,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniAnago);
		// display seaanemone
		aniSeaanemone = new AnimatedSprite(658, 364, tttrSeaanemone,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniSeaanemone);
		// display star fish
		aniStarFish = new AnimatedSprite(519, 487, tttrStarFish,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniStarFish);
		// display karei
		aniKarei = new AnimatedSprite(700, 528, tttrKarei,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniKarei);
		// display ship
		aniShip = new AnimatedSprite(154, 13, tttrShip,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniShip);
		// display whale
		sWhale1 = new Sprite(489, 180, ttrWhale1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sWhale1);
		sWhale2 = new Sprite(252, 21, ttrWhale2,
				this.getVertexBufferObjectManager());
		sWhale2.setVisible(false);
		mScene.attachChild(sWhale2);
		aniMiniWhale = new AnimatedSprite(285, 300, tttrMiniWhale,
				this.getVertexBufferObjectManager());
		aniMiniWhale.setVisible(false);
		mScene.attachChild(aniMiniWhale);
		// dis medusa
		aniMedusa = new AnimatedSprite(69, 152, tttrMedusa,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniMedusa);
		// display prawn
		aniPrawn = new AnimatedSprite(492, 366, tttrPrawn,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniPrawn);
		// display globe fish
		aniGlobeFish = new AnimatedSprite(200, 175, tttrGlobeFish,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniGlobeFish);
		// display squid
		aniSquid = new AnimatedSprite(148, 257, tttrSquid,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniSquid);
		// display tsunodashi
		aniTsunodashi = new AnimatedSprite(872, 479, tttrTsunodashi,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniTsunodashi);
		// display front weed
		aniFrontWeed = new AnimatedSprite(820, 300, tttrFrontWeed,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniFrontWeed);
		// display tobiuo
		aniTobiuo = new AnimatedSprite(768, 110, tttrTobiuo,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniTobiuo);
		// display mini fish1
		sMiniFish1 = new Sprite(754, 346, ttrMiniFish1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMiniFish1);
		// display mini fish2
		sMiniFish2 = new Sprite(572, 346, ttrMiniFish2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMiniFish2);
		// display mini fish3
		sMiniFish3 = new Sprite(313, 346, ttrMiniFish3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMiniFish3);
		// display mini fish4
		sMiniFish4 = new Sprite(52, 346, ttrMiniFish4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMiniFish4);
		// display shark
		sShark = new Sprite(845, 208, ttrShark,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sShark);
		// display sea horse
		aniSeaHorse = new AnimatedSprite(838, 232, tttrSeaHorse,
				this.getVertexBufferObjectManager());
		mScene.attachChild(aniSeaHorse);
		// display fish 1
		sFish1 = new Sprite(177, 160, ttrFish1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish1);
		// display fish 2
		sFish2 = new Sprite(316, 164, ttrFish2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish2);
		// display fish 3
		sFish3 = new Sprite(689, 162, ttrFish3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish3);
		// display fish 4
		sFish4 = new Sprite(390, 599, ttrFish4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish4);
		// display fish 5
		sFish5 = new Sprite(486, 528, ttrFish5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish5);
		// display fish 6
		sFish6 = new Sprite(505, 601, ttrFish6,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFish6);
		addGimmicsButton(mScene, Vol3UmiResource.SOUND_GIMMIC,
				Vol3UmiResource.IMAGE_GIMMIC, ttpUmiLibrary1);
		mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("umi/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("umi/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "umi/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		try {
			ttpUmi1 = mTexturePackLoaderFromSource.load("umi1.xml");
			ttpUmi1.loadTexture();
			ttpUmi2 = mTexturePackLoaderFromSource.load("umi2.xml");
			ttpUmi2.loadTexture();
			ttpUmi3 = mTexturePackLoaderFromSource.load("umi3.xml");
			ttpUmi3.loadTexture();

		} catch (Exception e) {
			e.printStackTrace();
		}
		ttpUmiLibrary1 = ttpUmi1.getTexturePackTextureRegionLibrary();
		ttpUmiLibrary2 = ttpUmi2.getTexturePackTextureRegionLibrary();
		ttpUmiLibrary3 = ttpUmi3.getTexturePackTextureRegionLibrary();

		this.tttrBackground_1 = ttpUmiLibrary2
				.get(Vol3UmiResource.A3_23_1_IPHONE_HAIKEI_ID);
		this.ttrBackground_2 = ttpUmiLibrary2
				.get(Vol3UmiResource.A3_23_2_IPHONE_HAIKEI_ID);

		this.ttrBackground_3 = ttpUmiLibrary2
				.get(Vol3UmiResource.A3_23_3_IPHONE_HAIKEI_ID);
		// light background
		this.ttrLightBackground = ttpUmiLibrary3
				.get(Vol3UmiResource.A3_31_IPHONE_LIGHT_ID);
		// back rock
		this.ttrBackRock = ttpUmiLibrary3
				.get(Vol3UmiResource.A3_29_IPHONE_BACKROCK_ID);
		// front rock
		this.ttrFrontRock = ttpUmiLibrary3
				.get(Vol3UmiResource.A3_30_IPHONE_FRONTROCK_ID);
		// fish 1
		this.ttrFish1 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_1_IPHONE_FISH_ID);
		// fish 2
		this.ttrFish2 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_2_IPHONE_FISH_ID);
		// fish 3
		this.ttrFish3 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_3_IPHONE_FISH_ID);
		// fish 4
		this.ttrFish4 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_4_IPHONE_FISH_ID);
		// fish 5
		this.ttrFish5 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_5_IPHONE_FISH_ID);
		// fish 6
		this.ttrFish6 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_32_6_IPHONE_FISH_ID);
		// mini fish 1
		this.ttrMiniFish1 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_04_2_IPHONE_MINIFISH_ID);
		// mini fish 2
		this.ttrMiniFish2 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_04_3_IPHONE_MINIFISH_ID);
		// mini fish 3
		this.ttrMiniFish3 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_04_4_IPHONE_MINIFISH_ID);
		// mini fish 4
		this.ttrMiniFish4 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_04_5_IPHONE_MINIFISH_ID);
		// Shark
		this.ttrShark = ttpUmiLibrary3
				.get(Vol3UmiResource.A3_04_1_IPHONE_SHARK_ID);
		// tobiuo
		this.tttrTobiuo = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_TOBIUO);
		// whale 1
		this.ttrWhale1 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_21_1_IPHONE_WHALE_ID);
		// whale 2
		this.ttrWhale2 = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_21_2_IPHONE_WHALE_ID);
		// mini Whale
		this.tttrMiniWhale = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_WHALE);
		// ship
		this.tttrShip = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_22_1_IPHONE_SHIP_ID,
				Vol3UmiResource.A3_22_2_IPHONE_SHIP_ID);
		// urchin
		this.ttrUrchin = ttpUmiLibrary1
				.get(Vol3UmiResource.A3_14_IPHONE_URCHIN_ID);
		// medusa
		this.tttrMedusa = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_19_1_IPHONE_MEDUSA_ID,
				Vol3UmiResource.A3_19_2_IPHONE_MEDUSA_ID);
		// shell
		this.tttrShell = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_05_1_IPHONE_SHELL_ID,
				Vol3UmiResource.A3_05_2_IPHONE_SHELL_ID);
		// Octopus
		this.tttrOctopus = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_07_1_IPHONE_OCTOPUS_ID,
				Vol3UmiResource.A3_07_2_IPHONE_OCTOPUS_ID);
		// box
		this.tttrBox = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_06_1_IPHONE_BOX_ID,
				Vol3UmiResource.A3_06_2_IPHONE_BOX_ID);
		// hermitcrab
		this.tttrHermitcrab = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_HERMITCRAB);
		// star fish
		this.tttrStarFish = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_STARFISH);
		// seaanemone
		this.tttrSeaanemone = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_SEAANEMONE);
		// lamp
		this.tttrLamp = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_12_1_IPHONE_LAMP_ID,
				Vol3UmiResource.A3_12_2_IPHONE_LAMP_ID);
		// prawn
		tttrPrawn = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_13_1_IPHONE_PRAWN_ID,
				Vol3UmiResource.A3_13_2_IPHONE_PRAWN_ID);
		// Manta
		this.tttrManta = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_15_1_IPHONE_MANTA_ID,
				Vol3UmiResource.A3_15_2_IPHONE_MANTA_ID);
		// squid
		this.tttrSquid = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_18_1_IPHONE_SQUID_ID,
				Vol3UmiResource.A3_18_2_IPHONE_SQUID_ID);
		// globe fish
		this.tttrGlobeFish = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_20_1_IPHONE_GLOBEFISH_ID,
				Vol3UmiResource.A3_20_2_IPHONE_GLOBEFISH_ID);
		// sea horse
		this.tttrSeaHorse = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_SEAHORSE);
		// anago
		this.tttrAnago = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_25_1_IPHONE_ANAGO_ID,
				Vol3UmiResource.A3_25_2_IPHONE_ANAGO_ID);
		// karei
		this.tttrKarei = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.PACK_KAREI);
		// tsunodashi
		this.tttrTsunodashi = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_11_IPHONE_TSUNODASHI_ID,
				Vol3UmiResource.A3_11_IPHONE_TSUNODASHI_FLIP_ID);
		// FrontWeed
		this.tttrFrontWeed = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_28_1_IPHONE_FRONTWEED_ID,
				Vol3UmiResource.A3_28_2_IPHONE_FRONTWEED_ID);
		// middle weed
		this.tttrMiddleWeed = getTiledTextureFromPacker(ttpUmi1,
				Vol3UmiResource.A3_27_1_IPHONE_MIDDLEWEED_ID,
				Vol3UmiResource.A3_27_2_IPHONE_MIDDLEWEED_ID);
		// big weed
		this.tttrBigWeed = getTiledTextureFromPacker(ttpUmi3,
				Vol3UmiResource.A3_26_1_IPHONE_BIGWEED_ID,
				Vol3UmiResource.A3_26_2_IPHONE_BIGWEED_ID);
	}

	@Override
	public synchronized void onResumeGame() {
		loadDefault();
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.d("Vol3Umi: ", "loadKaraokeComplete");
		super.loadKaraokeComplete();
	}

	@Override
	protected void loadKaraokeSound() {
		A3_04_DEDEN2 = loadSoundResourceFromSD(Vol3UmiResource.A3_04_DEDEN2);
		A3_05_PAKAKIRA = loadSoundResourceFromSD(Vol3UmiResource.A3_05_PAKAKIRA);
		A3_06_TAKARABAKO = loadSoundResourceFromSD(Vol3UmiResource.A3_06_TAKARABAKO);
		A3_07_SYU2 = loadSoundResourceFromSD(Vol3UmiResource.A3_07_SYU2);
		A3_08_KASA = loadSoundResourceFromSD(Vol3UmiResource.A3_08_KASA);
		A3_09_DUL = loadSoundResourceFromSD(Vol3UmiResource.A3_09_DUL);
		A3_10_BOKO = loadSoundResourceFromSD(Vol3UmiResource.A3_10_BOKO);
		A3_11_POWA = loadSoundResourceFromSD(Vol3UmiResource.A3_11_POWA);
		A3_12_POMI = loadSoundResourceFromSD(Vol3UmiResource.A3_12_POMI);
		A3_13_JU = loadSoundResourceFromSD(Vol3UmiResource.A3_13_JU);
		A3_14_KORO = loadSoundResourceFromSD(Vol3UmiResource.A3_14_KORO);
		A3_15_POWA3 = loadSoundResourceFromSD(Vol3UmiResource.A3_15_POWA3);
		A3_16_POWA2 = loadSoundResourceFromSD(Vol3UmiResource.A3_16_POWA2);
		A3_17_PYONPASYA = loadSoundResourceFromSD(Vol3UmiResource.A3_17_PYONPASYA);
		A3_18_PYU2 = loadSoundResourceFromSD(Vol3UmiResource.A3_18_PYU2);
		A3_19_POWA4 = loadSoundResourceFromSD(Vol3UmiResource.A3_19_POWA4);
		A3_20_BO = loadSoundResourceFromSD(Vol3UmiResource.A3_20_BO);
		A3_21_ZABA = loadSoundResourceFromSD(Vol3UmiResource.A3_21_ZABA);
		A3_22_BO = loadSoundResourceFromSD(Vol3UmiResource.A3_22_BO);
		A3_23_HARP = loadSoundResourceFromSD(Vol3UmiResource.A3_23_HARP);
		A3_24_KASA = loadSoundResourceFromSD(Vol3UmiResource.A3_24_KASA);
		A3_25_NEWN = loadSoundResourceFromSD(Vol3UmiResource.A3_25_NEWN);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sShark, 0, (int) sShark.getHeight() / 2 - 65,
					(int) sShark.getWidth(), (int) sShark.getHeight() * 2 / 3,
					pX, pY)) {
				moveShark();
			} else if (checkContains(sBackground, 354, 0, 960, 140, pX, pY)) {
				currentBg++;
				touchBackground(currentBg);
				if (currentBg >= 2) {
					currentBg = -1;
				}
			} else if (checkContains(aniTobiuo, 2, 50, 142, 90, pX, pY)) {
				touchTobiuo();
			} else if (checkContains(sWhale1, 10, 10, 115, 50, pX, pY)) {
				touchWhale();
			} else if (checkContains(aniShip, 15, 15, 180, 135, pX, pY)) {
				touchShip();
			} else if (checkContains(sUrchin, 10, 8, 82, 50, pX, pY)) {
				touchUrchin();
			} else if (checkContains(aniMedusa, 10, 56, 100, 140, pX, pY)) {
				touchMedusa();
			} else if (checkContains(aniShell, 18, 30, 72, 79, pX, pY)) {
				touchShell();
			} else if (checkContains(aniOctopus, 55, 30, 145, 100, pX, pY)) {
				touchOctopus();
			} else if (checkContains(aniBox, 42, 42, 178, 168, pX, pY)) {
				currentBox++;
				touchBox(currentBox);
				if (currentBox >= 1) {
					currentBox = -1;
				}
			} else if (checkContains(aniLamp, 20, 70, 120, 120, pX, pY)) {
				touchLamp();
			} else if (checkContains(aniGlobeFish, 0, 30, 120, 85, pX, pY)) {
				touchGlobeFish();
			} else if (checkContains(aniAnago, 2, 45, 55, 96, pX, pY)) {
				touchAnago();
			} else if (checkContains(aniStarFish, 14, 14, 72, 72, pX, pY)) {
				currentStarFish++;
				touchStarFish(currentStarFish);
				if (currentStarFish >= 2) {
					currentStarFish = -1;
				}
			} else if (checkContains(aniSeaHorse, 5, 0, 55, 100, pX, pY)) {
				touchSeaHorse();
			} else if (checkContains(aniManta, 50, 50, 200, 140, pX, pY)) {
				touchManta();
			} else if (checkContains(aniSeaanemone, 10, 20, 210, 150, pX, pY)) {
				touchSeaanemone();
			} else if (checkContains(aniHermitcrab, 0, 10, 154, 115, pX, pY)) {
				if (istouchHermitcrab) {
					istouchHermitcrab = false;
					currentHermitcrab++;
					touchHermitcrab(currentHermitcrab);
					if (currentHermitcrab >= 1) {
						currentHermitcrab = -1;
					}
				}
			} else if (checkContains(aniKarei, 10, 20, 160, 85, pX, pY)) {
				currentKarei++;
				touchKarei(currentKarei);
				if (currentKarei >= 1) {
					currentKarei = -1;
				}
			} else if (checkContains(aniPrawn, 0, 20, 80, 90, pX, pY)) {
				touchPrawn();
			} else if (checkContains(aniSquid, 5, 5, 56, 112, pX, pY)) {
				touchSquid();
			} else if (checkContains(aniTsunodashi, 0, 10, 90, 120, pX, pY)) {
				touchTsunodashi();
			}
		}
		return false;
	}

	private void touchTsunodashi() {
		if (istouchTsunodashi) {
			A3_11_POWA.play();
			istouchTsunodashi = false;
			aniTsunodashi
					.registerEntityModifier(tsunodashiModifier = new SequenceEntityModifier(
							new MoveXModifier(2.5f, aniTsunodashi.getX(),
									aniTsunodashi.getX() - 150,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											aniTsunodashi
													.setCurrentTileIndex(1);
										}
									}), new MoveXModifier(2.5f, aniTsunodashi
									.getX() - 150, aniTsunodashi.getX())));

			tsunodashiModifier
					.addModifierListener(new IModifierListener<IEntity>() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							aniTsunodashi.setCurrentTileIndex(0);
							istouchTsunodashi = true;
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					});
		}
	}

	private void touchSquid() {
		if (istouchSquid) {
			A3_18_PYU2.play();
			istouchSquid = false;
			aniSquid.setCurrentTileIndex(1);
			aniSquid.registerEntityModifier(squidModifier = new SequenceEntityModifier(
					new MoveModifier(1.5f, aniSquid.getX(),
							aniSquid.getX() + 65, aniSquid.getY(), -(aniSquid
									.getHeight() + 10)),
					new DelayModifier(0.5f)));
			squidModifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					istouchSquid = true;
					aniSquid.setCurrentTileIndex(0);
					aniSquid.setPosition(148, 257);
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		}
	}

	private void touchPrawn() {
		if (istouchPrawn) {
			A3_13_JU.play();
			istouchPrawn = false;
			aniPrawn.setCurrentTileIndex(1);
			aniPrawn.registerEntityModifier(prawnModifier = new SequenceEntityModifier(
					new RotationModifier(0.8f, 0.0f, 30.0f),
					new RotationModifier(0.8f, 30.0f, 0.0f),
					new RotationModifier(0.8f, 0.0f, 30.0f),
					new RotationModifier(0.8f, 30.0f, 0.0f)));
			prawnModifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					aniPrawn.setCurrentTileIndex(0);
					istouchPrawn = true;
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
					
				}
			});
		}
	}

	private void touchKarei(int position) {
		if (istouchKarei) {
			A3_24_KASA.play();
			istouchKarei = false;
			long pDuration[] = new long[] { 500, 700, 500 };
			if (position == 1) {
				aniKarei.registerEntityModifier(new FadeInModifier(2.0f));
				int pFrame[] = new int[] { 0, 1, 2 };
				aniKarei.animate(pDuration, pFrame, 0, this);

				Log.d("Vol3Umi", "touchKarei: " + aniKarei.isScaled());
			} else if (position == 0) {
				int pFrame[] = new int[] { 2, 1, 0};
				aniKarei.registerEntityModifier(new FadeOutModifier(1.0f));
				aniKarei.animate(pDuration, pFrame, 0, this);
			}
		}
	}

	private void touchHermitcrab(int position) {
		A3_08_KASA.play();
		long pDuration[] = new long[] { 400, 400 };
		if (position == 1) {
			int pFrame[] = new int[] { 1, 2 };
			aniHermitcrab.animate(pDuration, pFrame, 0, this);
		} else if (position == 0) {
			int pFrame[] = new int[] { 1, 0 };
			aniHermitcrab.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchSeaanemone() {
		if (istouchSeaanemone) {
			A3_10_BOKO.play();
			istouchSeaanemone = false;
			long pDuration[] = new long[] { 1000, 1000, 1000 };
			int pFrame[] = new int[] { 1, 2, 0 };
			aniSeaanemone.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchManta() {
		if (istouchManta) {
			A3_15_POWA3.play();
			istouchManta = false;
			long pDuration[] = new long[] { 800, 300 };
			int pFrame[] = new int[] { 1, 0 };
			aniManta.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchSeaHorse() {
		if (istouchSeaHorse) {
			A3_16_POWA2.play();
			istouchSeaHorse = false;
			long pDuration[] = new long[] { 300, 300, 300 };
			int pFrame[] = new int[] { 1, 2, 0 };
			aniSeaHorse.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchStarFish(int position) {
		A3_09_DUL.play();
		aniStarFish.setCurrentTileIndex(position);
	}

	private void touchAnago() {
		if (istouchAnago) {
			A3_25_NEWN.play();
			istouchAnago = false;
			long pDuration[] = new long[] { 600, 400 };
			int pFrame[] = new int[] { 1, 0 };
			aniAnago.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchGlobeFish() {
		if (istouchGlobeFish) {
			A3_20_BO.play();
			istouchGlobeFish = false;
			long pDuration[] = new long[] { 600, 400 };
			int pFrame[] = new int[] { 1, 0 };
			aniGlobeFish.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchLamp() {
		if (istouchLamp) {
			A3_12_POMI.play();
			istouchLamp = false;
			long pDuration[] = new long[] { 600, 400 };
			int pFrame[] = new int[] { 1, 0 };
			aniLamp.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchBox(int position) {
		A3_06_TAKARABAKO.play();
		aniBox.setCurrentTileIndex(position);
	}

	private void touchOctopus() {
		if (istouchOctopus) {
			A3_07_SYU2.play();
			istouchOctopus = false;
			long pDuration[] = new long[] { 600, 400 };
			int pFrame[] = new int[] { 1, 0 };
			aniOctopus.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchShell() {
		if (istouchShell) {
			A3_05_PAKAKIRA.play();
			istouchShell = false;
			aniShell.clearEntityModifiers();
			long pDuration[] = new long[] { 600, 300 };
			int pFrame[] = new int[] { 1, 0 };
			aniShell.animate(pDuration, pFrame, 0, this);
		}
	}

	private void touchMedusa() {
		if (istouchMedusa) {
			A3_19_POWA4.play();
			istouchMedusa = false;
			aniMedusa.setCurrentTileIndex(1);
			aniMedusa
					.registerEntityModifier(medusaModifier = new SequenceEntityModifier(
							new MoveYModifier(1.0f, aniMedusa.getY(), aniMedusa
									.getY() - 30,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											aniMedusa.setCurrentTileIndex(0);
										}
									}), new MoveYModifier(1.0f, aniMedusa
									.getY() - 30, aniMedusa.getY())));
			medusaModifier
					.addModifierListener(new IModifierListener<IEntity>() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							istouchMedusa = true;
						}
					});
		}
	}

	private void touchUrchin() {
		if (istouchUrchin) {
			A3_14_KORO.play();
			istouchUrchin = false;
			sUrchin.registerEntityModifier(urchinModifier = new ParallelEntityModifier(
					new RotationModifier(1.5f, 0.0f, -360.0f),
					new MoveModifier(2.0f, sUrchin.getX(),
							sUrchin.getX() - 600, sUrchin.getY(), sUrchin
									.getY() + 200)));
			urchinModifier
					.addModifierListener(new IModifierListener<IEntity>() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							sUrchin.setPosition(527, 446);
							istouchUrchin = true;
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					});
		}
	}

	private void touchShip() {
		if (istouchShip) {
			A3_22_BO.play();
			istouchShip = false;
			long pDuration[] = new long[] { 200, 200 };
			int pFrame[] = new int[] { 1, 0 };
			aniShip.animate(pDuration, pFrame, 3);
			aniShip.registerEntityModifier(shipModifier = new SequenceEntityModifier(
					new DelayModifier(1.4f),
					new MoveXModifier(6.0f, 154, 1144), new MoveXModifier(3.0f,
							-184, 154)));
			shipModifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					istouchShip = true;
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		}
	}

	private void touchWhale() {
		if (istouchWhale) {
			A3_21_ZABA.play();
			istouchWhale = false;
			long pDuration[] = new long[] { 100, 100, 100, 100, 100, 100, 100,
					100, 100, 100, 100, 100 };
			int pFrame[] = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
			sWhale2.setVisible(true);
			aniMiniWhale.setVisible(true);
			aniMiniWhale.animate(pDuration, pFrame, 0);
			sWhale1.setVisible(false);
			mEngine.registerUpdateHandler(new TimerHandler(1.4f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							sWhale2.setVisible(false);
							aniMiniWhale.setVisible(false);
							sWhale1.setVisible(true);
							istouchWhale = true;

						}
					}));
		}
	}

	private void moveFish() {
		// fish 1
		sFish1.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(6.0f, sFish1
						.getX(), sFish1.getX() + 783), new MoveXModifier(2.0f,
						0, sFish1.getX()))));
		// fish 2
		sFish2.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(5.0f, sFish2
						.getX(), sFish2.getX() + 644), new MoveXModifier(4.0f,
						0, sFish2.getX()))));
		// fish 3
		sFish3.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(6.0f, sFish3
						.getX(), 0),
						new MoveXModifier(2.0f, 960, sFish3.getX()))));
		// fish 4
		sFish4.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(5.0f, sFish4
						.getX(), 0),
						new MoveXModifier(6.0f, 960, sFish4.getX()))));
		// fish 5
		sFish5.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(5.0f, sFish5
						.getX(), sFish5.getX() + 474), new MoveXModifier(6.0f,
						0, sFish5.getX()))));
		// fish 6
		sFish6.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(4.0f, sFish6
						.getX(), -30), new MoveXModifier(6.0f, 980, sFish6
						.getX()))));
	}

	private void touchBackground(int position) {
		A3_23_HARP.play();
		if (position == 1) {
			sBackground.setVisible(false);
			sBackground_3.setVisible(false);
			sBackground_2.setVisible(true);

		} else if (position == 2) {
			sBackground_2.setVisible(false);
			sBackground.setVisible(false);
			sBackground_3.setVisible(true);
		} else if (position == 0) {
			sBackground.setVisible(true);
			sBackground_2.setVisible(false);
			sBackground_3.setVisible(false);
		}
	}

	private void moveShark() {
		if (istouchShark) {
			A3_04_DEDEN2.play();
			istouchShark = false;
			sShark.registerEntityModifier(sharkModifier = new SequenceEntityModifier(
					new MoveXModifier(4.0f, sShark.getX(), -sShark.getWidth()),
					new MoveXModifier(0.7f, 960, sShark.getX())));
			sharkModifier.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					istouchShark = true;
					sMiniFish1.setVisible(true);
					sMiniFish2.setVisible(true);
					sMiniFish3.setVisible(true);
					sMiniFish4.setVisible(true);
					sShark.clearUpdateHandlers();
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
			});
		}
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (sShark.getX() <= sMiniFish1.getX()) {
					sMiniFish1.setVisible(false);
				}
				if (sShark.getX() <= sMiniFish2.getX()) {
					sMiniFish2.setVisible(false);
				}
				if (sShark.getX() <= sMiniFish3.getX()) {
					sMiniFish3.setVisible(false);
				}
				if (sShark.getX() <= sMiniFish4.getX()) {
					sMiniFish4.setVisible(false);
				}
			}
		});
	}

	private void touchTobiuo() {
		if (istouchTobiuo) {
			A3_17_PYONPASYA.play();
			istouchTobiuo = false;
			aniTobiuo
					.registerEntityModifier(tobiuoModifier = new SequenceEntityModifier(
							new MoveModifier(0.3f, 768, 588, 110, 8,
									new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											long pDuration[] = new long[] {
													200, 300 };
											int pFrame[] = new int[] { 1, 0 };
											aniTobiuo.animate(pDuration,
													pFrame, 0);
										}
									}), new MoveModifier(0.3f, 588, 315, 8,
									110, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											long pDuration[] = new long[] {
													200, 300 };
											int pFrame[] = new int[] { 2, 0 };
											aniTobiuo.animate(pDuration,
													pFrame, 0);
										}
									}),

							new MoveXModifier(0.8f, 315, -139),
							new MoveXModifier(0.4f, 1099, 768)));
			tobiuoModifier
					.addModifierListener(new IModifierListener<IEntity>() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							aniTobiuo.setCurrentTileIndex(0);
							istouchTobiuo = true;
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					});
		}
	}

	private void moveShell() {
		aniShell.clearEntityModifiers();
		aniShell.registerEntityModifier(new LoopEntityModifier(

		new SequenceEntityModifier(new DelayModifier(5.0f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						istouchShell = false;
					}
				}), new MoveYModifier(0.6f, 550, 530), new MoveYModifier(0.6f,
				530, 550, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						istouchShell = true;
					}
				}))));

	}

	private void moveSeaWeed() {
		aniFrontWeed.animate(600, true);
		aniBigWeed.animate(600, true);
		aniMiddleWeed.animate(600, true);
	}

	@Override
	public void onPauseGame() {
		loadDefault();
		clearData();
		super.onPauseGame();
	}

	private void clearData() {
		currentBg = 0;
		currentBox = 0;
		currentStarFish = 0;
		currentHermitcrab = 0;
		currentKarei = 0;
		// back ground
		if (sBackground != null) {
			sBackground.setVisible(true);
			sBackground_2.setVisible(false);
			sBackground_3.setVisible(false);
		}
		// ship
		if (aniShip != null) {
			aniShip.stopAnimation();
		}
		if (shipModifier != null) {
			aniShip.clearEntityModifiers();
			aniShip.setPosition(154, 13);
		}
		// tobiuo
		if (tobiuoModifier != null) {
			aniTobiuo.clearEntityModifiers();
			aniTobiuo.stopAnimation();
			aniTobiuo.setCurrentTileIndex(0);
			aniTobiuo.setPosition(768, 110);
		}
		// shark
		if (sharkModifier != null) {
			sShark.clearEntityModifiers();
			sMiniFish1.setVisible(true);
			sMiniFish2.setVisible(true);
			sMiniFish3.setVisible(true);
			sMiniFish4.setVisible(true);
			sShark.setPosition(845, 208);
		}
		// box
		if (aniBox != null) {
			aniBox.setCurrentTileIndex(0);
		}
		if (aniStarFish != null) {
			aniStarFish.setCurrentTileIndex(0);
		}
		// seaanemone
		if (aniSeaanemone != null) {
			aniSeaanemone.stopAnimation();
		}
		// prawn
		if (prawnModifier != null) {
			aniPrawn.clearEntityModifiers();
			aniPrawn.setCurrentTileIndex(0);
		}
		if (squidModifier != null) {
			aniSquid.clearEntityModifiers();
			aniSquid.setCurrentTileIndex(0);
			aniSquid.setPosition(148, 257);
		}
		if (urchinModifier != null) {
			sUrchin.clearEntityModifiers();
			sUrchin.setPosition(527, 446);
		}
		if (tsunodashiModifier != null) {
			aniTsunodashi.clearEntityModifiers();
			aniTsunodashi.setCurrentTileIndex(0);
			aniTsunodashi.setPosition(872, 479);
		}
		if (aniHermitcrab != null) {
			aniHermitcrab.setCurrentTileIndex(0);
		}
		if (aniKarei != null) {
			aniKarei.setCurrentTileIndex(0);
		}
		// medusa
		if (medusaModifier != null) {
			aniMedusa.clearEntityModifiers();
			aniMedusa.setCurrentTileIndex(0);
			aniMedusa.setPosition(69, 152);
		}
	}

	private void loadDefault() {
		istouchShark = true;
		istouchTobiuo = true;
		istouchWhale = true;
		istouchShip = true;
		istouchUrchin = true;
		istouchShell = true;
		istouchPrawn = true;
		istouchSquid = true;
		istouchMedusa = true;
		istouchTsunodashi = true;
		istouchSeaanemone = true;
		istouchManta = true;
		istouchSeaHorse = true;
		istouchAnago = true;
		istouchLamp = true;
		istouchGlobeFish = true;
		istouchOctopus = true;
		istouchHermitcrab = true;
		istouchKarei = true;
		try {
			moveFish();
			moveShell();
			moveSeaWeed();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Override
	public void combineGimmic3WithAction() {
		moveShark();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(aniShell)) {
			istouchShell = true;
			moveShell();
		}
		if (pAnimatedSprite.equals(aniSeaanemone)) {
			istouchSeaanemone = true;
		}
		if (pAnimatedSprite.equals(aniManta)) {
			istouchManta = true;
		}
		if (pAnimatedSprite.equals(aniSeaHorse)) {
			istouchSeaHorse = true;
		}
		if (pAnimatedSprite.equals(aniAnago)) {
			istouchAnago = true;
		}
		if (pAnimatedSprite.equals(aniLamp)) {
			istouchLamp = true;
		}
		if (pAnimatedSprite.equals(aniGlobeFish)) {
			istouchGlobeFish = true;
		}
		if (pAnimatedSprite.equals(aniOctopus)) {
			istouchOctopus = true;
		}
		if (pAnimatedSprite.equals(aniHermitcrab)) {
			istouchHermitcrab = true;
		}
		if (pAnimatedSprite.equals(aniKarei)) {
			aniKarei.setAlpha(1.0f);
			istouchKarei = true;
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
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> pModifier, IEntity arg1) {

	}

}
