package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3UrashimataroResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
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

public class Vol3Urashimataro extends BaseGameFragment implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {
	// Define tag log for this class
	private String TAG_LOG = Vol3Urashimataro.this.getClass().toString();
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion ttrBackground1, ttrBackground2, ttrBackground3;
	private Sprite sBackground1, sBackground2, sBackground3;

	private TiledTextureRegion ttrSardine, ttrSurface, ttrWeed, ttrFlatfish,
			ttrPrincess, ttrLeftgirl, ttrRightgirl, ttrSunfish,
			ttrTropicalfish, ttrTurtle, ttrSquid, ttrOctopus, ttrMedusa,
			ttrBream, ttrBoy, ttrHouse;
	private AnimatedSprite sSardine, sSurface, sWeed, sFlatfish, sPrincess,
			sLeftgirl, sRightgirl, sSunfish, sTropicalfish, sTurtle, sSquid,
			sOctopus, sMedusa, sBream, sBoy, sHouse;
	private SequenceEntityModifier sqSardineModifier, sqSquidModifier,
			sqOctopusModifier, sqMedusaModifier, sqBreamModifier;

	private float angle = 0;
	private boolean flagLeft = true, isTouchBackground = true,
			isTouchPrincess = true, isTouchLeftgirl, isTouchRightgirl,
			isTouchSunfish, isTouchTropicalfish, isTouchTurtle,
			isTouchFlatfish, isTouchSquid, isTouchOctopus, isTouchMedusa,
			isTouchBream, isTouchHouse, isTouchGimmic;
	private int numberTouchBoy = 2, numberTouchhouse = 0;

	private Sound mp3House, mp3Flatfish, mp3Bream, mp3Turtle,
			mp3Squid_Medusa_Octopus, mp3Tropicalfish, mp3Boy, mp3Sunfish,
			mp3Leftgirl, mp3Rightgirl, mp3Princess;

	private TexturePack Vol3UrashimataroPacker_1, Vol3UrashimataroPacker_2;
	private TexturePackTextureRegionLibrary Vol3UrashimataroLibrary1,
			Vol3UrashimataroLibrary2;

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG_LOG, "Load Karaoke Scene");
		mScene = new Scene();

		sBackground1 = new Sprite(0, 0, ttrBackground1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground1);

		sBackground2 = new Sprite(0, 340, ttrBackground2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);

		sBackground3 = new Sprite(67, 9, ttrBackground3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground3);

		sSardine = new AnimatedSprite(501, 0, ttrSardine,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSardine);

		sSurface = new AnimatedSprite(-54, -23, ttrSurface,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSurface);

		sHouse = new AnimatedSprite(-74, 150, ttrHouse,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHouse);

		sWeed = new AnimatedSprite(-45, 336, ttrWeed,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sWeed);

		sRightgirl = new AnimatedSprite(364, 237, ttrRightgirl,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRightgirl);

		sLeftgirl = new AnimatedSprite(117, 256, ttrLeftgirl,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeftgirl);

		sPrincess = new AnimatedSprite(238, 146, ttrPrincess,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sPrincess);

		sSunfish = new AnimatedSprite(209, 3, ttrSunfish,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSunfish);

		sTropicalfish = new AnimatedSprite(783, 157, ttrTropicalfish,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sTropicalfish);

		sTurtle = new AnimatedSprite(501, 321, ttrTurtle,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sTurtle);

		sFlatfish = new AnimatedSprite(61, 526, ttrFlatfish,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFlatfish);

		sSquid = new AnimatedSprite(814, 269, ttrSquid,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSquid);

		sOctopus = new AnimatedSprite(356, 15, ttrOctopus,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOctopus);

		sMedusa = new AnimatedSprite(470, 147, ttrMedusa,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMedusa);

		sBream = new AnimatedSprite(715, 540, ttrBream,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBream);

		sBoy = new AnimatedSprite(503, 82, ttrBoy,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBoy);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3UrashimataroResource.SOUND_GIMMIC,
				Vol3UrashimataroResource.IMAGE_GIMMIC, Vol3UrashimataroLibrary2);
	}

	@Override
	protected void loadKaraokeResources() {
		Vol3UrashimataroPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3UrashimataroPacker1.xml");
		Vol3UrashimataroPacker_1.loadTexture();
		Vol3UrashimataroLibrary1 = Vol3UrashimataroPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3UrashimataroPacker_2 = mTexturePackLoaderFromSource
				.load("Vol3UrashimataroPacker2.xml");
		Vol3UrashimataroPacker_2.loadTexture();
		Vol3UrashimataroLibrary2 = Vol3UrashimataroPacker_2
				.getTexturePackTextureRegionLibrary();

		ttrBackground1 = Vol3UrashimataroLibrary2
				.get(Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_20_IPHONE_HAIKEI);

		ttrBackground2 = Vol3UrashimataroLibrary2
				.get(Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_21_IPHONE_CORAL);

		ttrBackground3 = Vol3UrashimataroLibrary1
				.get(Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_03_IPHONE_BUBBLE);

		ttrSardine = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_2,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_17_1_IPHONE_SARDINE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_17_2_IPHONE_SARDINE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_17_1_IPHONE_SARDINE_FLIP,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_17_2_IPHONE_SARDINE_FLIP);

		ttrSurface = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_2,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_18_1_IPHONE_SURFACE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_18_2_IPHONE_SURFACE);

		ttrWeed = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_2,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_19_1_IPHONE_WEED,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_19_2_IPHONE_WEED,
				Vol3UrashimataroResource.Vol3UrashimataroPacker2.A4_19_3_IPHONE_WEED);

		ttrPrincess = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_15_1_IPHONE_PRINCESS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_15_2_IPHONE_PRINCESS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_15_3_IPHONE_PRINCESS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_15_4_IPHONE_PRINCESS);

		ttrRightgirl = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_16_1_IPHONE_RIGHTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_16_2_IPHONE_RIGHTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_16_3_IPHONE_RIGHTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_16_4_IPHONE_RIGHTGIRL);

		ttrLeftgirl = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_14_1_IPHONE_LEFTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_14_2_IPHONE_LEFTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_14_3_IPHONE_LEFTGIRL,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_14_4_IPHONE_LEFTGIRL);

		ttrSunfish = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_13_1_IPHONE_SUNFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_13_2_IPHONE_SUNFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_13_3_IPHONE_SUNFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_13_4_IPHONE_SUNFISH);

		ttrTropicalfish = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_09_1_IPHONE_TROPICALFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_09_2_IPHONE_TROPICALFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_09_3_IPHONE_TROPICALFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_09_4_IPHONE_TROPICALFISH);

		ttrTurtle = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_07_1_IPHONE_TURTLE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_07_2_IPHONE_TURTLE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_07_3_IPHONE_TURTLE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_07_4_IPHONE_TURTLE);

		ttrFlatfish = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_05_1_IPHONE_FLATFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_05_2_IPHONE_FLATFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_05_3_IPHONE_FLATFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_05_4_IPHONE_FLATFISH,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_05_5_IPHONE_FLATFISH);

		ttrSquid = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_08_1_IPHONE_SQUID,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_08_2_IPHONE_SQUID,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_08_3_IPHONE_SQUID,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_08_4_IPHONE_SQUID);

		ttrOctopus = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_12_1_IPHONE_OCTOPUS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_12_2_IPHONE_OCTOPUS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_12_3_IPHONE_OCTOPUS,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_12_4_IPHONE_OCTOPUS);

		ttrMedusa = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_11_1_IPHONE_MEDUSA,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_11_2_IPHONE_MEDUSA,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_11_3_IPHONE_MEDUSA,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_11_4_IPHONE_MEDUSA,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_11_5_IPHONE_MEDUSA);

		ttrBream = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_06_1_IPHONE_BREAM,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_06_2_IPHONE_BREAM,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_06_3_IPHONE_BREAM);

		ttrBoy = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_10_1_IPHONE_BOY,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_10_2_IPHONE_BOY,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_10_3_IPHONE_BOY,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_10_4_IPHONE_BOY);

		ttrHouse = getTiledTextureFromPacker(
				Vol3UrashimataroPacker_1,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_04_1_IPHONE_HOUSE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_04_2_IPHONE_HOUSE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_04_3_IPHONE_HOUSE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_04_4_IPHONE_HOUSE,
				Vol3UrashimataroResource.Vol3UrashimataroPacker1.A4_04_5_IPHONE_HOUSE);
	}

	@Override
	protected void loadKaraokeSound() {
		mp3House = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_04_POWA);
		mp3Flatfish = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_05_PISYA);
		mp3Bream = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_06_07_BOCO);
		mp3Turtle = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_06_07_BOCO);
		mp3Squid_Medusa_Octopus = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_08_11_12PYU2);
		mp3Tropicalfish = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_09_CHU7);
		mp3Boy = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_10_BOWAN);
		mp3Sunfish = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_13_BUJU3);
		mp3Leftgirl = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_14_KASA);
		mp3Rightgirl = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_16_NEIKI2);
		mp3Princess = loadSoundResourceFromSD(Vol3UrashimataroResource.A4_15_KUSYUN);
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG_LOG, "Load KaraokeComplete");
		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG_LOG, "Load onResumeGame");
		angle = 0;
		flagLeft = true;
		isTouchBackground = true;
		isTouchPrincess = true;
		isTouchLeftgirl = true;
		isTouchRightgirl = true;
		isTouchSunfish = true;
		isTouchTropicalfish = true;
		isTouchTurtle = true;
		isTouchFlatfish = true;
		isTouchSquid = true;
		isTouchOctopus = true;
		isTouchMedusa = true;
		isTouchBream = true;
		isTouchHouse = true;
		isTouchGimmic = true;
		sardineChangeResource(flagLeft);
		ChangeResource(sWeed);
		ChangeResource(sSurface);
		ClapandChangeResource(isTouchPrincess, sPrincess, 0);
		ClapandChangeResource(isTouchRightgirl, sRightgirl, 1);
		ClapandChangeResource(isTouchLeftgirl, sLeftgirl, 0);
		ClapandChangeResource(isTouchSunfish, sSunfish, 1);
		ClapandChangeResource(isTouchTropicalfish, sTropicalfish, 0);

		ClapandChangeResource(isTouchTurtle, sTurtle, 2);
		flatFishClapandChangeResource(isTouchFlatfish, sFlatfish, 0);
		ClapandChangeResource(isTouchOctopus, sOctopus, 0);
		numberTouchBoy = 2;
		boyClapandChangeResource(numberTouchBoy);
		breamClapandChangeResource(isTouchBream, sBream);
		ClapandChangeResource(isTouchSquid, sSquid, 0);
		ClapandChangeResource(isTouchTurtle, sTurtle, 2);
		boyClapandChangeResource(numberTouchBoy);
		ClapandChangeResource(isTouchMedusa, sMedusa, 0);
		super.onResumeGame();
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG_LOG, "Create Resource");
		SoundFactory.setAssetBasePath("urashimataro/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("urashimataro/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "urashimataro/gfx/");
		super.onCreateResources();
	}

	private void ChangeResource(AnimatedSprite anmSp) {
		anmSp.animate(600, true, this);
	}

	@Override
	public void onPause() {
		if (isLoadingComplete) {
			numberTouchBoy = 2;
			angle = 0;
			flagLeft = true;
			isTouchBackground = true;
			isTouchPrincess = true;
			isTouchLeftgirl = true;
			isTouchRightgirl = true;
			isTouchSunfish = true;
			isTouchTropicalfish = true;
			isTouchTurtle = true;
			isTouchFlatfish = true;
			isTouchSquid = true;
			isTouchOctopus = true;
			isTouchMedusa = true;
			isTouchBream = true;
			isTouchHouse = true;
			isTouchGimmic = true;
			if (sBream != null) {
				sBream.clearEntityModifiers();
				sBream.setPosition(715, 540);
				sBream.setCurrentTileIndex(0);
			}
			sqBreamModifier = null;
			if (sSquid != null) {
				sSquid.clearEntityModifiers();
				sSquid.setPosition(814, 269);
				sSquid.setCurrentTileIndex(0);
			}
			sqSquidModifier = null;

			if (sMedusa != null) {
				sMedusa.clearEntityModifiers();
				sMedusa.setPosition(470, 147);
				sMedusa.setCurrentTileIndex(0);
			}

			sqMedusaModifier = null;
			if (sSardine != null) {
				sSardine.clearEntityModifiers();
				sSardine.setPosition(501, 0);
				sSardine.setCurrentTileIndex(0);
			}
			sqSardineModifier = null;
			if (sHouse != null) {
				sHouse.clearEntityModifiers();
				sHouse.setCurrentTileIndex(0);
			}
			if (sOctopus != null) {
				sOctopus.clearEntityModifiers();
				sOctopus.setCurrentTileIndex(0);
			}
		}
		super.onPause();
	}

	private void sardineChangeResource(boolean flag) {
		if (flag) {
			sSardine.stopAnimation(2);
			sSardine.stopAnimation(3);
			long pDurationSardine[] = new long[] { 200, 200 };
			int pFramsSardine[] = new int[] { 0, 1 };
			sSardine.animate(pDurationSardine, pFramsSardine, 0);
		} else {
			sSardine.stopAnimation(0);
			sSardine.stopAnimation(1);
			long pDurationSardine[] = new long[] { 200, 200 };
			int pFramsSardine[] = new int[] { 2, 3 };
			sSardine.animate(pDurationSardine, pFramsSardine, 0);
		}
		sSardine.registerEntityModifier(new DelayModifier(0.4f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sardineChangeResource(flagLeft);
					}
				}));
	}

	public void houseChange(int number) {
		if (isTouchHouse && isTouchGimmic) {
			isTouchHouse = false;
			isTouchGimmic = false;
			numberTouchhouse++;
			mp3House.play();
			// ///////
			sHouse.setCurrentTileIndex(numberTouchhouse);
			sHouse.registerEntityModifier(new DelayModifier(1.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sHouse.setCurrentTileIndex(0);
							isTouchHouse = true;
							isTouchGimmic = true;
						}
					}));
			// ////////////
			if (numberTouchhouse == 4) {
				numberTouchhouse = 0;
			}
		}

	}

	private void breamClapandChangeResource(boolean istouch,
			AnimatedSprite anmSp) {
		if (istouch) {
			long pDurationSardine[] = new long[] { 400, 400 };
			int pFramsSardine[] = new int[] { 0, 1 };
			anmSp.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			mp3Bream.play();
			sBream.setCurrentTileIndex(2);
			breamModifier();
		}
	}

	private void ClapandChangeResource(boolean istouch, AnimatedSprite anmSp,
			int loop) {
		if (istouch) {
			long pDurationSardine[] = new long[] { 400, 400 };
			int pFramsSardine[] = new int[] { 0, 1 };
			anmSp.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			long pDurationSardine[] = new long[] { 200, 200 };
			int pFramsSardine[] = new int[] { 2, 3 };
			anmSp.animate(pDurationSardine, pFramsSardine, loop, this);
		}
	}

	private void boyClapandChangeResource(int numbertouch) {

		if (numbertouch % 2 == 0) {
			long pDurationSardine[] = new long[] { 400, 400 };
			int pFramsSardine[] = new int[] { 0, 1 };
			sBoy.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			long pDurationSardine[] = new long[] { 400, 400 };
			int pFramsSardine[] = new int[] { 2, 3 };
			sBoy.animate(pDurationSardine, pFramsSardine, -1);
		}
	}

	// flatfish
	private void flatFishClapandChangeResource(boolean istouch,
			AnimatedSprite anmSp, int loop) {
		if (istouch) {
			long pDurationSardine[] = new long[] { 200, 200, 200 };
			int pFramsSardine[] = new int[] { 0, 1, 2 };
			anmSp.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			long pDurationSardine[] = new long[] { 100, 100 };
			int pFramsSardine[] = new int[] { 3, 4 };
			anmSp.animate(pDurationSardine, pFramsSardine, loop, this);
		}
	}

	private void breamModifier() {

		if (sqBreamModifier == null || sqBreamModifier.isFinished()) {

			sBream.registerEntityModifier(sqBreamModifier = new SequenceEntityModifier(
					new MoveXModifier(3.0f, sBream.getX(), -300),

					new MoveXModifier(2.4f, this.mEngine.getCamera().getWidth()
							+ sBream.getWidth(), 715)));
		}
		if (sqBreamModifier != null) {
			sqBreamModifier.addModifierListener(this);
		}

	}

	// squid move
	private void squidModifier() {
		mp3Squid_Medusa_Octopus.play();
		sSquid.registerEntityModifier(sqSquidModifier = new SequenceEntityModifier(
				new ParallelEntityModifier((new MoveXModifier(1.5f, sSquid
						.getX(), sSquid.getX() - 80)), (new MoveYModifier(1.5f,
						sSquid.getY(), -250)))));
		if (sqSquidModifier != null) {
			sqSquidModifier.addModifierListener(this);
		}
	}

	// Octopus
	private void octopusModifier() {
		mp3Squid_Medusa_Octopus.play();
		sOctopus.registerEntityModifier(sqOctopusModifier = new SequenceEntityModifier(
				new ParallelEntityModifier((new MoveXModifier(1.5f, sOctopus
						.getX(), sOctopus.getX() - 250)), (new MoveYModifier(
						1.5f, sOctopus.getY(), -450)))));
		if (sqOctopusModifier != null) {
			sqOctopusModifier.addModifierListener(this);
		}
	}

	// Medusa
	private void medusaModifier() {
		mp3Squid_Medusa_Octopus.play();
		sMedusa.registerEntityModifier(sqMedusaModifier = new SequenceEntityModifier(
				new ParallelEntityModifier((new MoveXModifier(1.5f, sMedusa
						.getX(), sMedusa.getX() + 80)), (new MoveYModifier(
						1.5f, sMedusa.getY(), -250)))));
		if (sqMedusaModifier != null) {
			sqMedusaModifier.addModifierListener(this);
		}
	}

	private void translateSardine(float x, float y, float angle, boolean orient) {
		if (isTouchBackground) {
			isTouchBackground = false;
			if (sqSardineModifier == null || sqSardineModifier.isFinished()) {

				sSardine.registerEntityModifier(sqSardineModifier = new SequenceEntityModifier(
						new RotationModifier(1.0f, 0f, angle),
						new ParallelEntityModifier(new MoveXModifier(2.0f,
								sSardine.getX(), x), new MoveYModifier(2.0f,
								sSardine.getY(), y)), new RotationModifier(
								1.0f, angle, 0f)));

			}

			if (sqSardineModifier != null) {
				sqSardineModifier.addModifierListener(this);
			}
		}

	}

	@Override
	public void combineGimmic3WithAction() {
		houseChange(numberTouchBoy);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sPrincess, (int) sPrincess.getWidth() / 4,
					(int) sPrincess.getHeight() / 4,
					(int) sPrincess.getWidth() * 3 / 4,
					(int) sPrincess.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchPrincess) {
					isTouchPrincess = false;
					mp3Princess.play();
					ClapandChangeResource(isTouchPrincess, sPrincess, 0);
				}
			} else if (checkContains(sRightgirl,
					(int) sRightgirl.getWidth() / 4,
					(int) sRightgirl.getHeight() / 4,
					(int) sRightgirl.getWidth() * 3 / 4,
					(int) sRightgirl.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchRightgirl) {
					isTouchRightgirl = false;
					mp3Rightgirl.play();
					ClapandChangeResource(isTouchRightgirl, sRightgirl, 1);
				}
			} else if (checkContains(sLeftgirl, (int) sLeftgirl.getWidth() / 4,
					(int) sLeftgirl.getHeight() / 4,
					(int) sLeftgirl.getWidth() * 3 / 4,
					(int) sLeftgirl.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchLeftgirl) {
					isTouchLeftgirl = false;
					mp3Leftgirl.play();
					ClapandChangeResource(isTouchLeftgirl, sLeftgirl, 0);
				}
			} else if (checkContains(sSunfish, (int) sSunfish.getWidth() / 4,
					(int) sSunfish.getHeight() / 4,
					(int) sSunfish.getWidth() * 3 / 4,
					(int) sSunfish.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchSunfish) {
					isTouchSunfish = false;
					mp3Sunfish.play();
					ClapandChangeResource(isTouchSunfish, sSunfish, 1);
				}

			} else if (checkContains(sTropicalfish,
					(int) sTropicalfish.getWidth() / 4,
					(int) sTropicalfish.getHeight() / 4,
					(int) sTropicalfish.getWidth() * 3 / 4,
					(int) sTropicalfish.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchTropicalfish) {
					isTouchTropicalfish = false;
					mp3Tropicalfish.play();
					ClapandChangeResource(isTouchTropicalfish, sTropicalfish, 0);
				}
			} else if (checkContains(sTurtle, (int) sTurtle.getWidth() / 4,
					(int) sTurtle.getHeight() / 4,
					(int) sTurtle.getWidth() * 3 / 4,
					(int) sTurtle.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchTurtle) {
					isTouchTurtle = false;
					mp3Turtle.play();
					ClapandChangeResource(isTouchTurtle, sTurtle, 2);
				}
			} else if (checkContains(sFlatfish, (int) sFlatfish.getWidth() / 4,
					(int) sFlatfish.getHeight() / 4,
					(int) sFlatfish.getWidth() * 3 / 4,
					(int) sFlatfish.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchFlatfish) {
					isTouchFlatfish = false;
					// xu ly thoi gian
					mp3Flatfish.play();
					// /ClapandChangeResource(isTouchFlatfish, sFlatfish, 2);
					flatFishClapandChangeResource(isTouchFlatfish, sFlatfish, 2);
				}
			} else if (checkContains(sSquid, (int) sSquid.getWidth() / 4,
					(int) sSquid.getHeight() / 4,
					(int) sSquid.getWidth() * 3 / 4,
					(int) sSquid.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchSquid) {
					isTouchSquid = false;

					ClapandChangeResource(isTouchSquid, sSquid, 0);
				}
			} else if (checkContains(sOctopus, (int) sOctopus.getWidth() / 4,
					(int) sOctopus.getHeight() / 4,
					(int) sOctopus.getWidth() * 3 / 4,
					(int) sOctopus.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchOctopus) {
					isTouchOctopus = false;

					ClapandChangeResource(isTouchOctopus, sOctopus, 0);
				}

			} else if (sMedusa.contains(pX, pY)) {
				if (isTouchMedusa) {
					isTouchMedusa = false;
					Log.d(TAG_LOG, "sMedusa touch");
					if (sMedusa.isAnimationRunning()) {
						sMedusa.stopAnimation();
					}
					long pDurationSardine[] = new long[] { 250, 250, 250 };
					int pFramsSardine[] = new int[] { 2, 3, 4 };
					sMedusa.animate(pDurationSardine, pFramsSardine, 0, this);
					// ClapandChangeResource(isTouchMedusa, sMedusa, 0);
				}

			} else if (checkContains(sBream, (int) sBream.getWidth() / 4,
					(int) sBream.getHeight() / 4,
					(int) sBream.getWidth() * 3 / 4,
					(int) sBream.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchBream) {
					isTouchBream = false;
					sBream.stopAnimation();
					sBream.setCurrentTileIndex(2);
					breamClapandChangeResource(isTouchBream, sBream);
				}
			} else if (checkContains(sBoy, (int) sBoy.getWidth() / 4,
					(int) sBoy.getHeight() / 4, (int) sBoy.getWidth() * 3 / 4,
					(int) sBoy.getHeight() * 3 / 4, pX, pY)) {
				mp3Boy.play();
				numberTouchBoy++;
				sBoy.stopAnimation();
				sBoy.setCurrentTileIndex(2);
				boyClapandChangeResource(numberTouchBoy);
			} else if (checkContains(sHouse, (int) sHouse.getWidth() / 4,
					(int) sHouse.getHeight() / 4,
					(int) sHouse.getWidth() * 3 / 4,
					(int) sHouse.getHeight() * 3 / 4, pX, pY)) {
				houseChange(numberTouchhouse);
			} else if (checkContains(sBackground1, 0, 0,
					(int) sBackground1.getWidth(),
					(int) sBackground1.getHeight(), pX, pY)) {
				if (isTouchBackground) {

					// calculate angle
					angle = (float) ((Math
							.atan((double) ((pY - sSardine.getY()) / (pX - sSardine
									.getX())))) * 180 / Math.PI);
					if (pX >= sSardine.getX()) {
						flagLeft = false;
					} else {
						flagLeft = true;
					}
					translateSardine(pX - sSardine.getWidth() / 2, pY
							- sSardine.getHeight() / 2, angle, flagLeft);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(sqSardineModifier)) {
			isTouchBackground = true;
		} else if (pModifier.equals(sqSquidModifier)) {
			sSquid.setPosition(814, 269);
			isTouchSquid = true;
			ClapandChangeResource(isTouchSquid, sSquid, 0);
		} else if (pModifier.equals(sqOctopusModifier)) {
			sOctopus.setPosition(356, 15);
			isTouchOctopus = true;
			ClapandChangeResource(isTouchOctopus, sOctopus, 0);
		} else if (pModifier.equals(sqMedusaModifier)) {
			sMedusa.setPosition(470, 147);
			isTouchMedusa = true;
			ClapandChangeResource(isTouchMedusa, sMedusa, 0);
		} else if (pModifier.equals(sqBreamModifier)) {
			isTouchBream = true;
			breamClapandChangeResource(isTouchBream, sBream);
		}

	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(sPrincess)) {
			isTouchPrincess = true;
			ClapandChangeResource(isTouchPrincess, sPrincess, 1);
		} else if (pAnimatedSprite.equals(sRightgirl)) {
			isTouchRightgirl = true;
			ClapandChangeResource(isTouchRightgirl, sRightgirl, 1);
		} else if (pAnimatedSprite.equals(sLeftgirl)) {
			isTouchLeftgirl = true;
			ClapandChangeResource(isTouchLeftgirl, sLeftgirl, 1);
		} else if (pAnimatedSprite.equals(sSunfish)) {
			isTouchSunfish = true;
			ClapandChangeResource(isTouchSunfish, sSunfish, 1);
		} else if (pAnimatedSprite.equals(sTropicalfish)) {
			isTouchTropicalfish = true;
			ClapandChangeResource(isTouchTropicalfish, sTropicalfish, 0);
		} else if (pAnimatedSprite.equals(sTurtle)) {
			isTouchTurtle = true;
			ClapandChangeResource(isTouchTurtle, sTurtle, 2);
		} else if (pAnimatedSprite.equals(sFlatfish)) {
			isTouchFlatfish = true;
			flatFishClapandChangeResource(isTouchFlatfish, sFlatfish, 0);
		} else if (pAnimatedSprite.equals(sSquid)) {
			squidModifier();
		} else if (pAnimatedSprite.equals(sOctopus)) {
			octopusModifier();
		} else if (pAnimatedSprite.equals(sMedusa)) {
			medusaModifier();
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
}
