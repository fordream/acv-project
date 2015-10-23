package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YukiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
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

import android.util.Log;

public class Vol3Yuki extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity> {
	private static final String TAG = "LOG_YUKI";

	private float arrPointIce[][] = {
			{ 0, 105, 206, 290, 352, 474, 561, 354, 170, 4, 0 },
			{ 0, 10, 43, 90, 170, 191, 257, 274, 277, 270, 0 } };

	private float arrPointSnowman[][] = {
			{ 129, 181, 214, 185, 127, 61, 41, 57, 129 },
			{ 113, 121, 186, 261, 272, 250, 194, 137, 113 } };

	private float arrPointSnowHouse[][] = { { 29, 147, 269, 220, 117, 24, 29 },
			{ 8, 87, 189, 228, 144, 61, 8 } };

	private SequenceEntityModifier DogModifier;

	private boolean istouchGirl, istouchSnowHouse, istouchTreeRYuki,
			istouchTreeLYuki, istouchTreeCYuki, istouchFoHana, istouchDog,
			isTouchGlass = true, IsglovesR = false, checkR = true,
			IsglovesL = false, checkL = true, IsNinjin = false, checkN = true,
			IsBaketsu = false, checkB = true;

	boolean checkglass1 = true, checkglass2 = true, checkglass3 = true,
			checkglass4 = true, checkglass5 = true, checkglass6 = true,
			checkglass7 = true, checkglass8 = true, checkglass9 = true,
			checkglass10 = true, checkglass11 = true, checkglass12 = true,
			checkglass13 = true, checkglass14 = true, checkglass15 = true,
			checkglass16 = true;
	private int currentIce = 0, RandomPosition;

	private TexturePack ttpYuki1, ttpYuki2;
	private TexturePackTextureRegionLibrary ttpLibYuki1, ttpLibYuki2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private AnimatedSprite aniDog, aniSnow, aniIce, aniFoHana;

	private TiledTextureRegion tttrDog, tttrSnow, tttrIce, tttrFoHana;

	private TextureRegion ttrHaikei, ttrHaikeiB, ttrGlovesRight, ttrGlovesLeft,
			ttrNinjin, ttrBaketsu, ttrSnowman, ttrIke, ttrHouse, ttrHouseRight,
			ttrHouseLeft, ttrTreeLeft, ttrTreeLeftYuki, ttrTreeCenter,
			ttrTreeCenterYuki, ttrTreeRight, ttrTreeRightYuki, ttrGirl,
			ttrGirlT, ttrGirlFoot, ttrCat, ttrCatBig, ttrGlass1, ttrGlass2,
			ttrGlass3, ttrGlass6, ttrGlass8, ttrGlass10, ttrGlass12,
			ttrGlass14, ttrGlass4, ttrGlass7, ttrGlass9, ttrGlass11,
			ttrGlass13, ttrGlass15, ttrGlass5, ttrGlass16;

	private Sprite sbackground, sNinjin, sBaketsu, sGlovesRight, sGlovesLeft,
			sSnowman, sIke, sHouse, sHouseLeft, sHouseRight, sTreeLeft,
			sTreeRight, sTreeRightYuki, sTreeLeftYuki, sTreeCenterYuki,
			sTreeCenter, sGirl, sGirlT, sGirlFoot, sCat, sCatBig, sGlass1,
			sGlass2, sGlass3, sGlass4, sGlass6, sGlass8, sGlass10, sGlass12,
			sGlass14, sGlass15, sGlass5, sGlass7, sGlass9, sGlass11, sGlass13,
			sGlass16;

	private Sound mp3Dog, mp3koori, mp3pomi, mp3gyugyu, mp3yukiza, mp3saku,
			mp3mado, mp3cat;

	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources: ");
		ttpYuki1 = mTexturePackLoaderFromSource.load("yuki1.xml");
		ttpYuki1.loadTexture();
		ttpLibYuki1 = ttpYuki1.getTexturePackTextureRegionLibrary();

		ttpYuki2 = mTexturePackLoaderFromSource.load("yuki2.xml");
		ttpYuki2.loadTexture();
		ttpLibYuki2 = ttpYuki2.getTexturePackTextureRegionLibrary();

		// background
		this.ttrHaikei = ttpLibYuki2.get(Vol3YukiResource.A10_12_1_IPHONE4_HAIKEI_ID);
		this.ttrHaikeiB = ttpLibYuki2.get(Vol3YukiResource.A10_12_2_IPHONE4_HAIKEI_ID);
		
		// House
		this.ttrHouse = ttpLibYuki1.get(Vol3YukiResource.A10_08_1_IPHONE4_HOUSE_ID);

		this.ttrHouseRight = ttpLibYuki1.get(Vol3YukiResource.A10_08_2_IPHONE4_HOUSE_YUKI_RIGHT_ID);

		// Tree
		this.ttrTreeLeft = ttpLibYuki1.get(Vol3YukiResource.A10_08_4_IPHONE4_TREE_LEFT_ID);
		this.ttrTreeRight = ttpLibYuki1.get(Vol3YukiResource.A10_08_8_IPHONE4_TREE_RIGHT_ID);
		this.ttrTreeCenter = ttpLibYuki1.get(Vol3YukiResource.A10_08_6_IPHONE4_TREE_CENTER_ID);

		// snow tree
		this.ttrTreeRightYuki = ttpLibYuki1.get(Vol3YukiResource.A10_08_9_IPHONE4_TREE_RIGHT_YUKI_ID);
		this.ttrTreeLeftYuki = ttpLibYuki1.get(Vol3YukiResource.A10_08_5_IPHONE4_TREE_LEFT_YUKI_ID);
		this.ttrTreeCenterYuki = ttpLibYuki1.get(Vol3YukiResource.A10_08_7_IPHONE4_TREE_CENTER_YUKI_ID);
		this.ttrHouseLeft = ttpLibYuki1.get(Vol3YukiResource.A10_08_3_IPHONE4_HOUSE_YUKI_LEFT_ID);

		// Snowman
		this.ttrSnowman = ttpLibYuki1.get(Vol3YukiResource.A10_05_5_IPHONE4_SNOWMAN_ID);

		// Dog
		this.tttrDog = getTiledTextureFromPacker(ttpYuki1,
				Vol3YukiResource.A10_04_1_IPHONE4_DOG_ID,
				Vol3YukiResource.A10_04_2_IPHONE4_DOG_ID);

		// Girl
		this.ttrGirl = ttpLibYuki1.get(Vol3YukiResource.A10_10_1_IPHONE4_GIRL_ID);
		this.ttrGirlT = ttpLibYuki1.get(Vol3YukiResource.A10_10_2_IPHONE4_GIRL_ID);
		this.ttrGirlFoot = ttpLibYuki1.get(Vol3YukiResource.A10_10_3_IPHONE4_GIRL_ID);

		// Glovers
		this.ttrGlovesRight = ttpLibYuki1.get(Vol3YukiResource.A10_05_1_IPHONE4_GLOVES_RIGHT_ID);
		this.ttrGlovesLeft = ttpLibYuki1.get(Vol3YukiResource.A10_05_2_IPHONE4_GLOVES_LEFT_ID);

		// Ninjin
		this.ttrNinjin = ttpLibYuki1.get(Vol3YukiResource.A10_05_3_IPHONE4_NINJIN_ID);

		// Baketsu
		this.ttrBaketsu = ttpLibYuki1.get(Vol3YukiResource.A10_05_4_IPHONE4_BAKETSU_ID);

		// Ike
		this.ttrIke = ttpLibYuki1.get(Vol3YukiResource.A10_06_1_IPHONE4_IKE_ID);

		// Snow
		this.tttrSnow = getTiledTextureFromPacker(ttpYuki1,
				Vol3YukiResource.A10_09_1_IPHONE4_SNOW_ID,
				Vol3YukiResource.A10_09_2_IPHONE4_SNOW_ID,
				Vol3YukiResource.A10_09_3_IPHONE4_SNOW_ID,
				Vol3YukiResource.A10_09_4_IPHONE4_SNOW_ID);
		// Ice
		this.tttrIce = getTiledTextureFromPacker(ttpYuki1,
				Vol3YukiResource.A10_06_2_IPHONE4_ICE_ID,
				Vol3YukiResource.A10_06_3_IPHONE4_ICE_ID,
				Vol3YukiResource.A10_06_4_IPHONE4_ICE_ID,
				Vol3YukiResource.A10_06_5_IPHONE4_ICE_ID);

		// Fokinotou & Hana
		this.tttrFoHana = getTiledTextureFromPacker(ttpYuki1,
				Vol3YukiResource.A10_07_1_IPHONE4_FUKINOTOU_ID,
				Vol3YukiResource.A10_07_2_IPHONE4_HANA_ID);

		// Cat
		this.ttrCat = ttpLibYuki2.get(Vol3YukiResource.A10_11_2_IPHONE4_CAT_ID);
		this.ttrCatBig = ttpLibYuki2.get(Vol3YukiResource.A10_11_3_IPHONE4_CAT_ID);

		// Glass

		this.ttrGlass1 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_01_IPHONE4_ID);
		this.ttrGlass2 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_02_IPHONE4_ID);
		this.ttrGlass3 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_03_IPHONE4_ID);
		this.ttrGlass4 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_04_IPHONE4_ID);
		this.ttrGlass5 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_05_IPHONE4_ID);
		this.ttrGlass6 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_06_IPHONE4_ID);
		this.ttrGlass7 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_07_IPHONE4_ID);
		this.ttrGlass8 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_08_IPHONE4_ID);
		this.ttrGlass9 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_09_IPHONE4_ID);
		this.ttrGlass10 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_10_IPHONE4_ID);
		this.ttrGlass11 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_11_IPHONE4_ID);
		this.ttrGlass12 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_12_IPHONE4_ID);
		this.ttrGlass13 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_13_IPHONE4_ID);
		this.ttrGlass14 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_14_IPHONE4_ID);
		this.ttrGlass15 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_15_IPHONE4_ID);
		this.ttrGlass16 = ttpLibYuki2.get(Vol3YukiResource.A10_11_1_16_IPHONE4_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		Log.d(TAG, "loadKaraokeSound: ");
		mp3koori = loadSoundResourceFromSD(Vol3YukiResource.E00087_koori);
		mp3Dog = loadSoundResourceFromSD(Vol3YukiResource.E00088_dog);
		mp3pomi = loadSoundResourceFromSD(Vol3YukiResource.E00089_pomi);
		mp3gyugyu = loadSoundResourceFromSD(Vol3YukiResource.E00090_gyugyu);
		mp3yukiza = loadSoundResourceFromSD(Vol3YukiResource.E00091_yukiza);
		mp3saku = loadSoundResourceFromSD(Vol3YukiResource.E00092_saku);
		mp3mado = loadSoundResourceFromSD(Vol3YukiResource.E00093_mado);
		mp3cat = loadSoundResourceFromSD(Vol3YukiResource.E00094_cat);
		Log.d(TAG, "loadKaraokeSound: end ");

	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();

		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrHaikei, this
						.getVertexBufferObjectManager())));
		// House

		this.sHouse = new Sprite(0, 0, ttrHouse,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHouse);

		this.sHouseLeft = new Sprite(0, 0, ttrHouseLeft,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHouseLeft);

		// Tree
		this.sTreeRight = new Sprite(826, 56, ttrTreeRight,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeRight);

		this.sTreeCenter = new Sprite(630, 128, ttrTreeCenter,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeCenter);

		this.sTreeLeft = new Sprite(412, 56, ttrTreeLeft,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeLeft);

		this.sTreeCenter = new Sprite(631, 128, ttrTreeCenter,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeCenter);

		this.sTreeRight = new Sprite(826, 56, ttrTreeRight,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeRight);

		// Cat
		this.sCat = new Sprite(79, 99, ttrCat,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCat);

		// CatBig
		this.sCatBig = new Sprite((float) 204.7, 175, ttrCatBig, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCatBig);
		sCatBig.setVisible(false);

		// Glass
		this.sGlass1 = new Sprite(0, 0, ttrGlass1,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass1);
		//(float) 57.8
		this.sGlass2 = new Sprite(58, 0, ttrGlass2,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass2);

		this.sGlass3 = new Sprite(115, 0, ttrGlass3,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass3);

		this.sGlass4 = new Sprite(172, 0, ttrGlass4,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass4);
		
		this.sGlass5 = new Sprite(0, 45, ttrGlass5,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass5);

		this.sGlass6 = new Sprite(58, 45, ttrGlass6,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass6);

		this.sGlass7 = new Sprite(115, 45, ttrGlass7,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass7);

		this.sGlass8 = new Sprite(172, 45, ttrGlass8,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass8);

		this.sGlass9 = new Sprite(0, 87, ttrGlass9,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass9);

		this.sGlass10 = new Sprite(58, 87, ttrGlass10,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass10);

		this.sGlass11 = new Sprite(115, 87, ttrGlass11,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass11);

		this.sGlass12 = new Sprite(172, 87, ttrGlass12,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass12);

		this.sGlass13 = new Sprite(0, 125, ttrGlass13,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass13);

		this.sGlass14 = new Sprite(58, 125, ttrGlass14,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass14);

		this.sGlass15 = new Sprite(115, 125, ttrGlass15,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass15);

		this.sGlass16 = new Sprite(172, 125, ttrGlass16,this.getVertexBufferObjectManager());
		this.sCat.attachChild(sGlass16);

		// background
		sbackground = new Sprite(0, 0, ttrHaikeiB,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sbackground);

		// Snow Tree
		this.sTreeLeftYuki = new Sprite(420, 41, ttrTreeLeftYuki,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeLeftYuki);

		this.sTreeRightYuki = new Sprite(830, 39, ttrTreeRightYuki,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeRightYuki);

		this.sTreeCenterYuki = new Sprite(635, 118, ttrTreeCenterYuki,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTreeCenterYuki);

		this.sHouseRight = new Sprite(166, -130, ttrHouseRight,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHouseRight);

		// sIke
		this.sIke = new Sprite(0, 361, ttrIke,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sIke);

		// sIce
		this.aniIce = new AnimatedSprite(59, 471, this.tttrIce,this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniIce);
		aniIce.setVisible(false);

		// Snow
		this.aniSnow = new AnimatedSprite(0, 0, tttrSnow,this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniSnow);

		// Dog
		this.aniDog = new AnimatedSprite(250, 245, tttrDog,this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniDog);

		// Snowman
		this.sSnowman = new Sprite(640, 155, ttrSnowman,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSnowman);

		// Girl

		this.sGirlFoot = new Sprite(390, 120, ttrGirlFoot,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGirlFoot);
		sGirlFoot.setVisible(false);

		this.sGirl = new Sprite(390, 120, ttrGirl,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGirl);

		this.sGirlT = new Sprite(390, 120, ttrGirlT,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGirlT);
		sGirlT.setVisible(false);

		// Gloves
		this.sGlovesRight = new Sprite(590, 430, ttrGlovesRight,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGlovesRight);

		this.sGlovesLeft = new Sprite(700, 480, ttrGlovesLeft,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sGlovesLeft);

		// Ninjin
		this.sNinjin = new Sprite(770, 435, ttrNinjin,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sNinjin);

		// Baketsu
		this.sBaketsu = new Sprite(845, 390, ttrBaketsu,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBaketsu);

		// Fokinotou & Hana
		this.aniFoHana = new AnimatedSprite(780, 530, tttrFoHana,this.getVertexBufferObjectManager());
		this.mScene.attachChild(aniFoHana);
		aniFoHana.setVisible(false);

		addGimmicsButton(mScene, Vol3YukiResource.SOUND_GIMMIC_YUKI,
				Vol3YukiResource.IMAGE_GIMMIC_YUKI, ttpLibYuki1);
		this.mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene: end ");

	}

	@Override
	public void combineGimmic3WithAction() {
		touchIce();
	}

	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		super.onResumeGame();
		frameAnimationSnow();
		initial();
	}

	/**
	 * Initial All Variable
	 */
	private void initial() {
		istouchDog = true;
		istouchGirl = true;
		istouchSnowHouse = true;
		istouchTreeLYuki = true;
		istouchTreeRYuki = true;
		istouchTreeCYuki = true;
		istouchFoHana = true;
		return;
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "loadKaraokeSound");
		SoundFactory.setAssetBasePath("yuki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("yuki/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "yuki/gfx/");
		super.onCreateResources();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			if (checkContains(aniDog, 0, 30, 140, 140, pX, pY)) {
				touchDog();
			}

			if (checkContainsPolygon(sIke, arrPointIce, 10, pX, pY)) {
				touchIce();
			}
			if (checkContains(sGirl, 0, 60, 200, 300, pX, pY)) {
				touchGirl();
			}

			if (checkContainsPolygon(sHouseRight, arrPointSnowHouse, 6, pX, pY)) {
				TouchSnowHouse();
			}

			if (sTreeRightYuki.contains(pX, pY)) {
				TouchTreeRYuki();
			}
			if (sTreeLeftYuki.contains(pX, pY)) {
				TouchTreeLYuki();
			}
			if (sTreeCenterYuki.contains(pX, pY)) {
				TouchCenterYuki();
			}

			if (checkContains(sbackground, 770, 575, 860, 630, pX, pY)) {
				touchFoHana();
			}

			// Right
			if (checkContains(sGlovesRight, 4, 8, 59, 62, pX, pY)) {
				if (checkR) {
					mp3pomi.play();
					IsglovesR = true;
				}
			}

			// Left
			if (checkContains(sGlovesLeft, 6, 8, 62, 63, pX, pY)) {
				if (checkL) {
					mp3pomi.play();
					IsglovesL = true;
				}
			}

			// Ninjin
			if (sNinjin.contains(pX, pY)) {
				if (checkN) {
					mp3pomi.play();
					IsNinjin = true;
				}
			}

			// Baketsu
			if (checkContains(sBaketsu, 10, 10, 75, 75, pX, pY)) {
				if (checkB) {
					mp3pomi.play();
					IsBaketsu = true;
				}
			}

			// Snowman
			if (checkContainsPolygon(sSnowman, arrPointSnowman, 8, pX, pY)) {
				if (!checkL && !checkR && !checkN && !checkB) {
					sGlovesRight.setPosition(sGlovesRight.getmXFirst(),
							sGlovesRight.getmYFirst());
					sGlovesLeft.setPosition(sGlovesLeft.getmXFirst(),
							sGlovesLeft.getmYFirst());
					sNinjin.setPosition(sNinjin.getmXFirst(),
							sNinjin.getmYFirst());
					sBaketsu.setPosition(sBaketsu.getmXFirst(),
							sBaketsu.getmYFirst());

					checkL = true;
					checkR = true;
					checkB = true;
					checkN = true;
				}
			}

			return true;
		}
		if (pSceneTouchEvent.isActionUp()) {
			// Right
			if (sGlovesRight.getX() > 610 && sGlovesRight.getX() < 760
					&& sGlovesRight.getY() > 190 && sGlovesRight.getY() < 330) {

				sGlovesRight.setPosition(621, 210);

				checkR = false;
				IsglovesR = false;

			} else {
				sGlovesRight.setPosition(sGlovesRight.getmXFirst(),
						sGlovesRight.getmYFirst());
				IsglovesR = false;
			}

			// Left
			if (sGlovesLeft.getX() > 795 && sGlovesLeft.getX() < 920
					&& sGlovesLeft.getY() > 200 && sGlovesLeft.getY() < 330) {

				sGlovesLeft.setPosition(845, 210);

				checkL = false;
				IsglovesL = false;

			} else {
				sGlovesLeft.setPosition(sGlovesLeft.getmXFirst(),
						sGlovesLeft.getmYFirst());
				IsglovesL = false;
			}

			// Ninjin
			if (sNinjin.getX() > 700 && sNinjin.getX() < 830
					&& sNinjin.getY() > 155 && sNinjin.getY() < 285) {

				sNinjin.setPosition(720, 220);

				checkN = false;
				IsNinjin = false;

			} else {
				sNinjin.setPosition(sNinjin.getmXFirst(), sNinjin.getmYFirst());
				IsNinjin = false;
			}

			// Baketsu
			if (sBaketsu.getX() > 690 && sBaketsu.getX() < 850
					&& sBaketsu.getY() > 100 && sBaketsu.getY() < 245) {

				sBaketsu.setPosition(750, 115);

				checkB = false;
				IsBaketsu = false;

			} else {
				sBaketsu.setPosition(sBaketsu.getmXFirst(),
						sBaketsu.getmYFirst());
				IsBaketsu = false;
			}

		}
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if (sCat.contains(pX, pY)) {
				if (isTouchGlass) {
					TouchGlass(pX, pY);
				}
			}

			// Right
			if (IsglovesR) {
				sGlovesRight.setPosition(pX, pY);
			}

			// Left
			if (IsglovesL) {
				sGlovesLeft.setPosition(pX, pY);
			}

			// Ninjin
			if (IsNinjin) {
				sNinjin.setPosition(pX, pY);
			}

			// Baketsu
			if (IsBaketsu) {
				sBaketsu.setPosition(pX, pY);
			}

		}
		return false;
	}

	private void touchFoHana() {
		if (istouchFoHana) {
			mp3gyugyu.play();
			Random Position = new Random();
			RandomPosition = Position.nextInt(2);

			aniFoHana.setCurrentTileIndex(RandomPosition);
			aniFoHana.setVisible(true);
			istouchFoHana = false;

			aniFoHana.registerEntityModifier(new DelayModifier(4.0f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
						aniFoHana.setVisible(false);
						istouchFoHana = true;
					}
				}));
		}
	}

	private void TouchTreeLYuki() {
		if (istouchTreeLYuki) {
			istouchTreeLYuki = false;
			mp3yukiza.play();

			sTreeLeftYuki.registerEntityModifier(new ParallelEntityModifier(
				new SequenceEntityModifier(
					new MoveYModifier(0.6f, sTreeLeftYuki.getY(), sTreeLeftYuki.getY() + 260),
					new DelayModifier(0.3f,new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
							sTreeLeftYuki.setVisible(false);
						}
					}), 
					new DelayModifier(0.2f,new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
							sTreeLeftYuki.setRotation(0);
							sTreeLeftYuki.setPosition(420, 41);
							sTreeLeftYuki.setVisible(true);
							istouchTreeLYuki = true;
						}
					})), 
				new MoveXModifier(0.5f, sTreeLeftYuki.getX(), sTreeLeftYuki.getX() + 80),
				new RotationModifier(0.4f, 0.0f, 60.5f)));
		}
	}

	private void TouchTreeRYuki() {
		if (istouchTreeRYuki) {
			istouchTreeRYuki = false;
			mp3yukiza.play();

			sTreeRightYuki.registerEntityModifier(new ParallelEntityModifier(
					new SequenceEntityModifier(
						new MoveYModifier(0.6f, sTreeRightYuki.getY(),sTreeRightYuki.getY() + 273),
						new DelayModifier(0.3f,new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
								sTreeRightYuki.setVisible(false);
							}
						}), 
						new DelayModifier(0.2f,new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
								sTreeRightYuki.setRotation(0);
								sTreeRightYuki.setPosition(830, 39);
								sTreeRightYuki.setVisible(true);
								istouchTreeRYuki = true;
							}
						})), 
					new MoveXModifier(0.5f, sTreeRightYuki.getX(), sTreeRightYuki.getX() - 60),
					new RotationModifier(0.4f, 0.0f, -60.5f)));

		}
	}

	private void TouchCenterYuki() {
		if (istouchTreeCYuki) {
			istouchTreeCYuki = false;
			mp3yukiza.play();

			sTreeCenterYuki.registerEntityModifier(new ParallelEntityModifier(
					new SequenceEntityModifier(
						new MoveYModifier(0.6f,sTreeCenterYuki.getY(),sTreeCenterYuki.getY() + 215), 
						new DelayModifier(0.3f, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sTreeCenterYuki.setVisible(false);
							}
						}), 
						new DelayModifier(0.2f,new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								sTreeCenterYuki.setRotation(0);
								sTreeCenterYuki.setPosition(630, 128);
								sTreeCenterYuki.setVisible(true);
								istouchTreeCYuki = true;

							}
						})),
					new MoveXModifier(0.5f, sTreeCenterYuki.getX(),sTreeCenterYuki.getX() + 80), 
					new RotationModifier(0.4f, 0.0f, 60.5f)));
		}
	}

	private void TouchSnowHouse() {
		if (istouchSnowHouse) {
			istouchSnowHouse = false;
			mp3yukiza.play();

			sHouseRight.registerEntityModifier(new ParallelEntityModifier(

				new SequenceEntityModifier(
					new MoveYModifier(0.6f, sHouseRight.getY(), sHouseRight.getY() + 295), 
					new DelayModifier(0.4f,new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sHouseRight.setVisible(false);
						}
					}), 
					new DelayModifier(0.3f, new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
		
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
		
							sHouseRight.setRotation(0);
							sHouseRight.setPosition(160, -130);
							sHouseRight.setVisible(true);
							istouchSnowHouse = true;
						}
					})), 
				new MoveXModifier(0.6f, sHouseRight.getX(),sHouseRight.getX() + 110), 
				new RotationModifier(0.4f, 0.0f,63.0f)));

		}
	}

	private void touchGirl() {
		if (istouchGirl) {
			mp3saku.play();
			istouchGirl = false;
			sGirl.setVisible(false);
			sGirlT.setVisible(true);

			sGirlT.registerEntityModifier(new DelayModifier(1.4f,new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
					sGirlT.setVisible(false);
					sGirlFoot.setVisible(true);
					sGirl.setVisible(true);
					sGirl.registerEntityModifier(new DelayModifier(0.7f, new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
							istouchGirl = true;
						}
					}));

				}
			}));

			sGirlFoot.registerEntityModifier(new DelayModifier(2.4f,new IEntityModifierListener() {
						
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
					sGirlFoot.setVisible(false);
				}
			}));

		}
	}

	private void touchIce() {
		aniIce.setVisible(true);
		mp3koori.play();
		if (currentIce >= 0) {
			aniIce.setCurrentTileIndex(currentIce);
		}
		currentIce++;
		if (currentIce > 4) {
			aniIce.setVisible(false);
			currentIce = 0;
		}

	}

	private void touchDog() {
		if (istouchDog) {
			long[] pDuration = new long[] { 1300, 600 };
			int[] pFrame = new int[] { 1, 0 };
			istouchDog = false;
			mp3Dog.play();

			aniDog.setRotationCenter(162, 149);
			aniDog.registerEntityModifier(DogModifier = new SequenceEntityModifier(
				new RotationModifier(0.6f, 0.0f, 45.0f),
				new RotationModifier(0.7f, 45.0f, -5.2f)));
			DogModifier.addModifierListener(this);
			aniDog.animate(pDuration, pFrame, 0);
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(DogModifier)) {
			istouchDog = true;
			aniDog.setRotation(0);
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, " onpauseGame ");
		super.onPauseGame();
		resetData();
	}

	private void frameAnimationSnow() {
		aniSnow.animate(500, -1);
	}

	private void TouchGlass(int x, int y) {

		if (x > 79 && x < 137 && y > 99 && y < 144 && checkglass1) {
			mp3mado.play();
			checkglass1 = false;
			sGlass1.setVisible(false);
		}
		if (x > 137 && x < 194 && y > 99 && y < 144 && checkglass2) {
			mp3mado.play();
			checkglass2 = false;
			sGlass2.setVisible(false);
		}
		if (x > 194 && x < 251 && y > 99 && y < 144 && checkglass3) {
			mp3mado.play();
			checkglass3 = false;
			sGlass3.setVisible(false);
		}
		if (x > 251 && x < 309 && y > 99 && y < 144 && checkglass4) {
			mp3mado.play();
			checkglass4 = false;
			sGlass4.setVisible(false);
		}

		if (x > 79 && x < 137 && y > 144 && y < 187 && checkglass5) {
			mp3mado.play();
			checkglass5 = false;
			sGlass5.setVisible(false);
		}
		if (x > 137 && x < 194 && y > 144 && y < 187 && checkglass6) {
			mp3mado.play();
			checkglass6 = false;
			sGlass6.setVisible(false);
		}
		if (x > 194 && x < 251 && y > 144 && y < 187 && checkglass7) {
			mp3mado.play();
			checkglass7 = false;
			sGlass7.setVisible(false);
		}
		if (x > 251 && x < 309 && y > 144 && y < 187 && checkglass8) {
			mp3mado.play();
			checkglass8 = false;
			sGlass8.setVisible(false);
		}

		if (x > 79 && x < 137 && y > 187 && y < 225 && checkglass9) {
			mp3mado.play();
			checkglass9 = false;
			sGlass9.setVisible(false);
		}
		if (x > 137 && x < 194 && y > 187 && y < 225 && checkglass10) {
			mp3mado.play();
			checkglass10 = false;
			sGlass10.setVisible(false);
		}
		if (x > 194 && x < 251 && y > 187 && y < 225 && checkglass11) {
			mp3mado.play();
			checkglass11 = false;
			sGlass11.setVisible(false);
		}
		if (x > 251 && x < 309 && y > 187 && y < 225 && checkglass12) {
			mp3mado.play();
			checkglass12 = false;
			sGlass12.setVisible(false);
		}

		if (x > 79 && x < 137 && y > 225 && y < 268 && checkglass13) {
			mp3mado.play();
			checkglass13 = false;
			sGlass13.setVisible(false);
		}
		if (x > 137 && x < 194 && y > 225 && y < 268 && checkglass14) {
			mp3mado.play();
			checkglass14 = false;
			sGlass14.setVisible(false);
		}
		if (x > 194 && x < 251 && y > 225 && y < 268 && checkglass15) {
			mp3mado.play();
			checkglass15 = false;
			sGlass15.setVisible(false);
		}
		if (x > 251 && x < 309 && y > 225 && y < 268 && checkglass16) {
			mp3mado.play();
			checkglass16 = false;
			sGlass16.setVisible(false);
		}

		if (!checkglass1 && !checkglass2 && !checkglass3 && !checkglass4
				&& !checkglass5 && !checkglass6 && !checkglass7 && !checkglass8
				&& !checkglass9 && !checkglass10 && !checkglass11
				&& !checkglass12 && !checkglass13 && !checkglass14
				&& !checkglass15 && !checkglass16) {
			isTouchGlass = false;
			mp3mado.pause();
			sCatBig.setVisible(true);
			sCatBig.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.3f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,IEntity arg1) {
						mp3cat.play();
					}
				}), 
				new ScaleModifier(1.0f, 1.0f, 1.3f), 
				new ScaleModifier(1.0f, 1.3f, 1.0f), 
				new DelayModifier(2.0f,new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						isTouchGlass = true;
						sCatBig.setVisible(false);

						sGlass1.setVisible(true);
						sGlass2.setVisible(true);
						sGlass3.setVisible(true);
						sGlass4.setVisible(true);
						sGlass5.setVisible(true);
						sGlass6.setVisible(true);
						sGlass7.setVisible(true);
						sGlass8.setVisible(true);
						sGlass9.setVisible(true);
						sGlass10.setVisible(true);
						sGlass11.setVisible(true);
						sGlass12.setVisible(true);
						sGlass13.setVisible(true);
						sGlass14.setVisible(true);
						sGlass15.setVisible(true);
						sGlass16.setVisible(true);

						checkglass1 = true;
						checkglass2 = true;
						checkglass3 = true;
						checkglass4 = true;
						checkglass5 = true;
						checkglass6 = true;
						checkglass7 = true;
						checkglass8 = true;
						checkglass9 = true;
						checkglass10 = true;
						checkglass11 = true;
						checkglass12 = true;
						checkglass13 = true;
						checkglass14 = true;
						checkglass15 = true;
						checkglass16 = true;
					}
				})));
		}
	}

	private void resetData() {
		if (aniDog != null) {
			istouchDog = true;
			aniDog.clearEntityModifiers();
			aniDog.setRotation(0);
			aniDog.setCurrentTileIndex(0);
			aniDog.setPosition(250, 245);
		}
		if (sGirl != null) {
			istouchGirl = true;
			sGirlT.clearEntityModifiers();
			sGirl.setVisible(true);
			sGirlT.setVisible(false);
			sGirlFoot.setVisible(false);
			sGirlFoot.clearEntityModifiers();

		}

		if (sTreeCenterYuki != null) {
			istouchTreeCYuki = true;
			sTreeCenterYuki.clearEntityModifiers();
			sTreeCenterYuki.setVisible(true);
			sTreeCenterYuki.setRotation(0);
			sTreeCenterYuki.setPosition(630, 128);
		}

		if (sTreeLeftYuki != null) {
			istouchTreeLYuki = true;
			sTreeLeftYuki.clearEntityModifiers();
			sTreeLeftYuki.setVisible(true);
			sTreeLeftYuki.setRotation(0);
			sTreeLeftYuki.setPosition(420, 41);
		}

		if (sTreeRightYuki != null) {
			istouchTreeRYuki = true;
			sTreeRightYuki.clearEntityModifiers();
			sTreeRightYuki.setVisible(true);
			sTreeRightYuki.setRotation(0);
			sTreeRightYuki.setPosition(830, 39);
		}

		if (sHouseRight != null) {
			istouchSnowHouse = true;
			sHouseRight.clearEntityModifiers();
			sHouseRight.setVisible(true);
			sHouseRight.setRotation(0);
			sHouseRight.setPosition(160, -130);
		}

		if (aniFoHana != null) {
			aniFoHana.clearEntityModifiers();
			istouchFoHana = false;
			aniFoHana.setVisible(false);
		}

		if (aniIce != null) {
			aniIce.setVisible(false);
			currentIce = 0;
		}

		if (sCat != null) {
			isTouchGlass = true;

			sGlass1.setVisible(true);
			sGlass2.setVisible(true);
			sGlass3.setVisible(true);
			sGlass4.setVisible(true);
			sGlass5.setVisible(true);
			sGlass6.setVisible(true);
			sGlass7.setVisible(true);
			sGlass8.setVisible(true);
			sGlass9.setVisible(true);
			sGlass10.setVisible(true);
			sGlass11.setVisible(true);
			sGlass12.setVisible(true);
			sGlass13.setVisible(true);
			sGlass14.setVisible(true);
			sGlass15.setVisible(true);
			sGlass16.setVisible(true);

			checkglass1 = true;
			checkglass2 = true;
			checkglass3 = true;
			checkglass4 = true;
			checkglass5 = true;
			checkglass6 = true;
			checkglass7 = true;
			checkglass8 = true;
			checkglass9 = true;
			checkglass10 = true;
			checkglass11 = true;
			checkglass12 = true;
			checkglass13 = true;
			checkglass14 = true;
			checkglass15 = true;
			checkglass16 = true;
		}

		if (sGlovesLeft != null) {
			checkL = true;
			IsglovesL = false;
			sGlovesLeft.setPosition(700, 480);
		}

		if (sGlovesRight != null) {
			checkR = true;
			IsglovesR = false;
			sGlovesRight.setPosition(590, 430);
		}

		if (sNinjin != null) {
			checkN = true;
			IsNinjin = false;
			sNinjin.setPosition(770, 435);
		}

		if (sBaketsu != null) {
			checkB = true;
			IsBaketsu = false;
			sBaketsu.setPosition(845, 390);
		}

	}

}
