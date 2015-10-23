package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3KaeruResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

public class Vol3Kaeru extends BaseGameActivity implements
		IOnSceneTouchListener {

	private TexturePack packBackground = null, packFrog = null,
			packOmedeto = null;
	private TexturePackTextureRegionLibrary packLibBackground = null,
			packLibFrog = null, packLibOmedeto = null;

	// Random coordinate
	RandomCoordinate rcCrocodiles, rcWaters, rcTadpoles, rcFrog08, rcFrog12;

	private TiledTextureRegion txtTadpoles[] = new TiledTextureRegion[4];
	private AnimatedSprite asTadpoles[] = new AnimatedSprite[4];
	private int ranTadpoles = 0;
	private int[][] tadpolesId = {
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6A_1_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6A_2_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6A_3_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6A_4_IPHONE_BOY_ID },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6B_1_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6B_2_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6B_3_IPHONE_BOY_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6B_4_IPHONE_BOY_ID },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6C_1_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6C_2_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6C_3_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6C_4_IPHONE_GIRL_ID },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6D_1_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6D_2_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6D_3_IPHONE_GIRL_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_6D_4_IPHONE_GIRL_ID }, };

	// CROCODILE
	private TiledTextureRegion txtCrocodile = null;
	private AnimatedSprite asCrocodile = null;
	private int ranCorocodile = 0;

	private ITextureRegion txtLotusNumber = null, txtStar = null;
	private Sprite sLotusNumber = null;

	// SCORE
	private TiledTextureRegion txtScore = null;
	private AnimatedSprite asScore = null;
	private int[] scoreId = {
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_00_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_01_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_02_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_03_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_04_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_05_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_06_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_07_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_08_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_09_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_10_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_11_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_12_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_13_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_14_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_15_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_16_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_17_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_18_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_19_IPHONE_NO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_20_IPHONE_NO_ID };
	private int score = 0;

	// OMEDETO
	private ITextureRegion txtOmedetoBackground = null, txtOmedetoLotus = null;
	private Sprite sOmedetoBackground = null, sOmedetoLotus = null;
	private TiledTextureRegion ttxOmedeto = null;
	private AnimatedSprite asOmedeto = null;
	private boolean isTouchOmedetoLotus = false, isTouchCrocodile = true,
			isTouchTadpoles = true;

	// LOTUS
	private ITextureRegion[] txtLotus = new ITextureRegion[12];
	private Sprite[] sLotus = new Sprite[12];
	private int[] intLotusId = {
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_1_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_2_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_3_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_4_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_5_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_6_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_7_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_8_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_9_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_10_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_11_IPHONE_HASU_ID,
			Vol3KaeruResource.kaeru_background_crocodile.A2_20_12_IPHONE_HASU_ID, };
	private int[] intLotusLeft = { 15, 280, 487, -27, 272, 710, -38, 105, 265,
			614, 791, 788 };
	private int[] intLotusTop = { -147, -100, -97, 81, 132, 116, 258, 248, 377,
			307, 261, 511 };
	private boolean[] lotusIsAvaiable = { false, false, false, true, true,
			true, true, true, true, true, true, true };
	private boolean[] isTouchLotus = { true, true, true, true, true, true,
			true, true, true, true, true, true };

	// WAVES
	private ITextureRegion[] txtWaves = new ITextureRegion[12];
	private Sprite[] sWaves = new Sprite[6];
	private int[] intWavesId = {
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_1_IPHONE_MINAMO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_2_IPHONE_MINAMO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_3_IPHONE_MINAMO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_4_IPHONE_MINAMO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_5_IPHONE_MINAMO_ID,
			Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_19_6_IPHONE_MINAMO_ID, };
	private int[] intWavesLeft = { 206, 433, 634, 832, 439, 730 };
	private int[] intWavesTop = { 60, 21, 85, -37, 314, 487 };

	// FROG
	private int currentFrogAppear = 0;
	private ArrayList<String> frogAvaiable = new ArrayList<String>();
	HashMap<String, FrogAnimatedSprite> frog = new HashMap<String, FrogAnimatedSprite>();
	TiledTextureRegion[] ttxFrog = new TiledTextureRegion[12];
	private int[][] frogId = {
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_7_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_7_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_7_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_8_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_8_2_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_9_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_9_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_9_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_10_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_10_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_10_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_11_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_11_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_11_3_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_11_4_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_12_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_12_2_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_13_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_13_2_IPHONE_KAERU_ID },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_14_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_14_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_14_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_15_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_15_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_15_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_16_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_16_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_16_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_17_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_17_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_17_3_IPHONE_KAERU_ID, },
			{
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_18_1_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_18_2_IPHONE_KAERU_ID,
					Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_18_3_IPHONE_KAERU_ID, } };

	private Sound a2_08_kaeru3, a2_09_kaeru5, a2_10_13_kaeru2, a2_11_17_kaeru8,
			a2_04_omedeto2, a2_12_15_kaeru7, a2_04_pinpon2, a2_14_16_kaeru6,
			a2_06_bokotyapo, a2_07_kaeru4, a2_05_gao;
	private Sound[] a2_04 = new Sound[20];
	private String[] a2_04_files = { "a2_04_1.ogg", "a2_04_2.ogg",
			"a2_04_3.ogg", "a2_04_4.ogg", "a2_04_5.ogg", "a2_04_6.ogg",
			"a2_04_7.ogg", "a2_04_8.ogg", "a2_04_9.ogg", "a2_04_10.ogg",
			"a2_04_11.ogg", "a2_04_12.ogg", "a2_04_13.ogg", "a2_04_14.ogg",
			"a2_04_15.ogg", "a2_04_16.ogg", "a2_04_17.ogg", "a2_04_18.ogg",
			"a2_04_19.ogg", "a2_04_20.ogg",

	};

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("kaeru/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("kaeru/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		packBackground = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "kaeru/gfx/")
				.load("kaeru_background_crocodile.xml");
		packBackground.loadTexture();
		packLibBackground = packBackground.getTexturePackTextureRegionLibrary();

		packFrog = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "kaeru/gfx/")
				.load("kaeru_frogs_numbers_tadpoles.xml");
		packFrog.loadTexture();
		packLibFrog = packFrog.getTexturePackTextureRegionLibrary();

		// Load crocodile, lotus
		for (int i = 0; i < txtLotus.length; i++) {
			txtLotus[i] = packLibBackground.get(intLotusId[i]);
		}

		ranCorocodile = new Random().nextInt(3);
		switch (ranCorocodile) {
		case 0:
			txtCrocodile = new TiledTextureRegion(
					packBackground.getTexture(),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5A_1_IPHONE_WANI_ID),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5A_2_IPHONE_WANI_ID),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5A_3_IPHONE_WANI_ID));
			break;
		case 1:
			txtCrocodile = new TiledTextureRegion(
					packBackground.getTexture(),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5B_1_IPHONE_WANI_ID),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5B_2_IPHONE_WANI_ID),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5B_3_IPHONE_WANI_ID));
			break;
		case 2:
			txtCrocodile = new TiledTextureRegion(
					packBackground.getTexture(),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5C_1_IPHONE_WANI_ID),
					packLibBackground
							.get(Vol3KaeruResource.kaeru_background_crocodile.A2_5C_2_IPHONE_WANI_ID));
			break;
		}

		// Load frog, lotus, number
		for (int i = 0; i < sWaves.length; i++) {
			txtWaves[i] = packLibFrog.get(intWavesId[i]);
		}

		for (int i = 0; i < txtTadpoles.length; i++) {
			ITextureRegion[] pTextureTadpoles = new ITextureRegion[tadpolesId[i].length];
			for (int j = 0; j < pTextureTadpoles.length; j++) {
				pTextureTadpoles[j] = packLibFrog.get(tadpolesId[i][j]);
			}
			txtTadpoles[i] = new TiledTextureRegion(packFrog.getTexture(),
					pTextureTadpoles);
		}

		txtLotusNumber = packLibFrog
				.get(Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_04_IPHONE_NO_ID);

		ITextureRegion[] pTextureScore = new ITextureRegion[scoreId.length];
		for (int i = 0; i < scoreId.length; i++) {
			pTextureScore[i] = packLibFrog.get(scoreId[i]);
		}
		txtScore = new TiledTextureRegion(packFrog.getTexture(), pTextureScore);

		// Frog
		for (int i = 0; i < frogId.length; i++) {
			ITextureRegion[] pTextureFrog = new ITextureRegion[frogId[i].length];
			for (int j = 0; j < frogId[i].length; j++) {
				pTextureFrog[j] = packLibFrog.get(frogId[i][j]);
			}
			ttxFrog[i] = new TiledTextureRegion(packFrog.getTexture(),
					pTextureFrog);
		}

		// star
		txtStar = packLibFrog
				.get(Vol3KaeruResource.kaeru_frogs_numbers_tadpoles.A2_22_IPHONE_KIRAKIRA_ID);

		// omedeto
		packOmedeto = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "kaeru/gfx/").load("kaeru_omedeto.xml");
		packOmedeto.loadTexture();
		packLibOmedeto = packOmedeto.getTexturePackTextureRegionLibrary();

		txtOmedetoBackground = packLibOmedeto
				.get(Vol3KaeruResource.kaeru_omedeto.A2_04_1_2_IPHONE_OMEDETOU_ID);
		txtOmedetoLotus = packLibBackground
				.get(Vol3KaeruResource.kaeru_background_crocodile.A2_04_1_1_IPHONE_OMEDETOU_ID);
		ttxOmedeto = new TiledTextureRegion(
				packOmedeto.getTexture(),
				packLibOmedeto
						.get(Vol3KaeruResource.kaeru_omedeto.A2_04A_2_IPHONE_OMEDETOU_ID),
				packLibOmedeto
						.get(Vol3KaeruResource.kaeru_omedeto.A2_04A_3_IPHONE_OMEDETOU_ID));

	}

	@Override
	protected void loadKaraokeSound() {
		a2_08_kaeru3 = loadSoundResourceFromSD("a2_08_kaeru3.ogg");
		a2_09_kaeru5 = loadSoundResourceFromSD("a2_09_kaeru5.ogg");
		a2_10_13_kaeru2 = loadSoundResourceFromSD("a2_10_13_kaeru2.ogg");
		a2_11_17_kaeru8 = loadSoundResourceFromSD("a2_11_17_kaeru8.ogg");
		a2_04_omedeto2 = loadSoundResourceFromSD("a2_04_omedeto2.ogg");
		a2_12_15_kaeru7 = loadSoundResourceFromSD("a2_12_15_kaeru7.ogg");
		a2_04_pinpon2 = loadSoundResourceFromSD("a2_04_pinpon2.ogg");
		a2_14_16_kaeru6 = loadSoundResourceFromSD("a2_14_16_kaeru6.ogg");
		a2_06_bokotyapo = loadSoundResourceFromSD("a2_06_bokotyapo.ogg");
		a2_07_kaeru4 = loadSoundResourceFromSD("a2_07_kaeru4.ogg");
		a2_05_gao = loadSoundResourceFromSD("a2_05_gao.ogg");
		for (int i = 0; i < a2_04.length; i++) {
			a2_04[i] = loadSoundResourceFromSD(a2_04_files[i]);
		}
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();

		mScene.setBackground(new SpriteBackground(
				new Sprite(
						0,
						0,
						packLibBackground
								.get(Vol3KaeruResource.kaeru_background_crocodile.A2_21_IPHONE_HAIKEI_ID),
						this.getVertexBufferObjectManager())));

		rcWaters = new RandomCoordinate();
		rcWaters.add(110, 100);
		rcWaters.add(720, -20);
		rcWaters.add(500, 180);
		rcWaters.add(300, 300);
		rcWaters.add(500, 500);
		rcWaters.add(810, 130);
		rcWaters.add(320, 480);

		rcFrog08 = new RandomCoordinate();
		rcFrog08.add(570, 280);
		rcFrog08.add(750, 230);
		rcFrog08.add(260, 310);
		rcFrog08.add(60, 220);
		rcFrog08.add(810, 440);

		rcFrog12 = new RandomCoordinate();
		rcFrog12.add(30, -45);
		rcFrog12.add(500, -50);
		rcFrog12.add(300, -60);

		ranTadpoles = new Random().nextInt(4);
		rcTadpoles = new RandomCoordinate();
		rcTadpoles.add(246, 186);
		rcTadpoles.add(70, 248);
		rcTadpoles.add(420, 430);
		rcTadpoles.add(550, 440);
		rcTadpoles.add(460, 240);
		rcTadpoles.add(570, 130);
		rcTadpoles.add(630, 228);
		rcTadpoles.add(849, 120);

		for (int i = 0; i < sWaves.length; i++) {
			sWaves[i] = new Sprite(intWavesLeft[i], intWavesTop[i],
					txtWaves[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(sWaves[i]);
		}

		for (int i = 0; i < asTadpoles.length; i++) {
			asTadpoles[i] = new AnimatedSprite(rcTadpoles.getX(),
					rcTadpoles.getY(), txtTadpoles[i],
					this.getVertexBufferObjectManager());
			asTadpoles[i].setVisible(false);
			this.mScene.attachChild(asTadpoles[i]);
		}
		asTadpoles[ranTadpoles].setVisible(true);

		for (int i = 0; i < sLotus.length; i++) {
			sLotus[i] = new Sprite(intLotusLeft[i], intLotusTop[i],
					txtLotus[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(sLotus[i]);
			sLotus[i].setZIndex(200 + i);
		}

		sLotusNumber = new Sprite(55, 484, txtLotusNumber,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLotusNumber);

		asScore = new AnimatedSprite(55, 470, txtScore,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(asScore);
		asScore.stopAnimation(score);

		switch (ranCorocodile) {
		case 0:
			rcCrocodiles = new RandomCoordinate();
			rcCrocodiles.add(new Random().nextInt(800),
					new Random().nextInt(500));
			asCrocodile = new AnimatedSprite(rcCrocodiles.getX(),
					rcCrocodiles.getY(), txtCrocodile,
					this.getVertexBufferObjectManager());
			asCrocodile.stopAnimation(0);
			asCrocodile.setZIndex(250);
			break;
		case 1:
			rcCrocodiles = new RandomCoordinate();
			rcCrocodiles.add(new Random().nextInt(800),
					new Random().nextInt(500));
			asCrocodile = new AnimatedSprite(rcCrocodiles.getX(),
					rcCrocodiles.getY(), txtCrocodile,
					this.getVertexBufferObjectManager());
			asCrocodile.stopAnimation(0);
			asCrocodile.setZIndex(250);
			break;
		case 2:
			asCrocodile = new AnimatedSprite(rcWaters.getX(), rcWaters.getY(),
					txtCrocodile, this.getVertexBufferObjectManager());
			asCrocodile.stopAnimation(0);
			rcWaters.remove_current();
			asCrocodile.setZIndex(199);
			break;
		}
		this.mScene.attachChild(asCrocodile);

		// Frog
		for (int i = 0; i < ttxFrog.length; i++) {
			frog.put("number_" + (i + 7), new FrogAnimatedSprite(100, 100,
					ttxFrog[i], this.getVertexBufferObjectManager()));
			frog.get("number_" + (i + 7)).setVisible(false);
			frogAvaiable.add("number_" + (i + 7));
			this.mScene.attachChild(frog.get("number_" + (i + 7)));
			frog.get("number_" + (i + 7)).setZIndex(300 + i);
			if (i == 1 || i == 4 || i == 8) {
				frog.get("number_" + (i + 7)).setZIndex(200 - i);
			}
		}

		// omedeto
		sOmedetoBackground = new Sprite(0, 0, txtOmedetoBackground,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOmedetoBackground);
		sOmedetoBackground.setVisible(false);
		sOmedetoBackground.setZIndex(400);
		sOmedetoLotus = new Sprite(349, 157, txtOmedetoLotus,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOmedetoLotus);
		sOmedetoLotus.setVisible(false);
		sOmedetoLotus.setZIndex(401);
		asOmedeto = new AnimatedSprite(0, 0, ttxOmedeto,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asOmedeto);
		asOmedeto.setVisible(false);
		asOmedeto.setZIndex(402);

		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3KaeruResource.SOUND_GIMMIC,
				Vol3KaeruResource.IMAGE_GIMMIC_ID, packLibFrog);
		for (int i = 0; i < sprGimmic.length; i++) {
			sprGimmic[i].setZIndex(10000 + i);
		}
		this.mScene.sortChildren();
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
		long[] pFrameDurations = { 400, 400 };
		asTadpoles[ranTadpoles].animate(pFrameDurations, 0, 1, true);
		mEngine.registerUpdateHandler(new TimerHandler(1f, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						// start random frogs appeared
						if (!sOmedetoLotus.isVisible()) {
							Collections.shuffle(frogAvaiable);
							for (int i = 0; i < 4 - currentFrogAppear; i++) {
								try {
									animationForFrogAppear(frogAvaiable.get(i));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}));
	}

	@Override
	public void combineGimmic3WithAction() {
		try {
			asTadpoles[ranTadpoles].clearEntityModifiers();
			asTadpoles[ranTadpoles].clearUpdateHandlers();
			asTadpoles[ranTadpoles].setPosition(
					asTadpoles[ranTadpoles].getmXFirst(),
					asTadpoles[ranTadpoles].getmYFirst());
			onTouchTadpoles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		try {
			resetGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetGame() throws Exception {
		newSessionGame();
		asCrocodile.setVisible(true);
		isTouchCrocodile = true;
		asTadpoles[ranTadpoles].clearEntityModifiers();
		asTadpoles[ranTadpoles].clearUpdateHandlers();
		asTadpoles[ranTadpoles].setPosition(
				asTadpoles[ranTadpoles].getmXFirst(),
				asTadpoles[ranTadpoles].getmYFirst());
		isTouchTadpoles = true;
	}

	public void animationForFrogAppear(final String frogNumber)
			throws Exception {
		int no = Integer.parseInt(frogNumber.replace("number_", ""));
		frogAvaiable.remove("number_" + no);
		switch (no) {
		case 7:
		case 9:
		case 10:
		case 14:
		case 16:
		case 17:
		case 18:
			int randomLotus = 0;
			Random random = new Random();
			while (true) {
				randomLotus = random.nextInt(lotusIsAvaiable.length);
				if (lotusIsAvaiable[randomLotus]) {
					break;
				}
			}
			lotusIsAvaiable[randomLotus] = false;
			isTouchLotus[randomLotus] = false;
			frogAppearAndMoveOverLotus(no, randomLotus,
					random.nextInt(sWaves.length));
			break;
		case 8:
			frog8Animation();
			break;
		case 11:
			frog11Animation();
			break;
		case 12:
			frog12Animation();
			break;
		case 13:
			int randomWave = 0;
			Random random2 = new Random();
			while (true) {
				randomWave = random2.nextInt(sWaves.length);
				if (randomWave != 3) {
					break;
				}
			}
			frog13Animation(randomWave);
			break;
		case 15:
			frog15Animation();
			break;
		}
		currentFrogAppear++;
	}

	/**
	 * function for frogs: 7, 9, 10, 14, 16, 17, 18
	 */
	public void frogAppearAndMoveOverLotus(final int no, final int randomLotus,
			final int randomWave) throws Exception {
		frog.get("number_" + no).setVisible(true);
		frog.get("number_" + no).stopAnimation(0);
		frog.get("number_" + no).setPosition(sWaves[randomWave]);
		frog.get("number_" + no).setOnLotus(randomLotus);
		float pX = (sLotus[randomLotus].getWidth() - frog.get("number_" + no)
				.getWidth()) / 2 + sLotus[randomLotus].getX();
		float pY = (sLotus[randomLotus].getHeight() - frog.get("number_" + no)
				.getHeight()) / 2 + sLotus[randomLotus].getY() - 20;

		float dX = pX - frog.get("number_" + no).getX();
		float dY = pY - frog.get("number_" + no).getY();
		float time = (float) (Math.sqrt(dX * dX + dY * dY) * 0.002);

		sWaves[randomWave].clearEntityModifiers();
		sWaves[randomWave].registerEntityModifier(new ScaleModifier(1f, 1.0f,
				1.2f));
		resetLotus(randomLotus);
		frog.get("number_" + no).registerEntityModifier(
				new SequenceEntityModifier(new MoveModifier(time, frog.get(
						"number_" + no).getX(), pX, frog.get("number_" + no)
						.getY(), pY), new DelayModifier(3f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> paramIModifier,
									IEntity paramT) {
								sWaves[randomWave].clearEntityModifiers();
								sWaves[randomWave].setScale(1.0f);
								frog.get("number_" + no).stopAnimation(1);
								frog.get("number_" + no).setTouch(true);
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> paramIModifier,
									IEntity paramT) {
								lotusIsAvaiable[randomLotus] = true;
								isTouchLotus[randomLotus] = true;
								frog.get("number_" + no).setOnLotus(-1);
								addFrogAvaiable(no);
							}
						})));
	}

	public void frog8Animation() throws Exception {
		frog.get("number_8").setVisible(true);
		rcFrog08.refresh();
		frog.get("number_8").setPosition(rcFrog08.getX(), rcFrog08.getY());
		frog.get("number_8").setTouch(true);
		frog.get("number_8").registerUpdateHandler(
				new TimerHandler(3f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						addFrogAvaiable(8);
					}
				}));
	}

	public void frog11Animation() throws Exception {
		rcWaters.refresh();
		frog.get("number_11").setPosition(rcWaters.getX(), rcWaters.getY());
		rcWaters.remove_current();
		long pDuration[] = new long[] { 300, 300, 300 };
		int pFrame[] = new int[] { 0, 1, 2 };
		frog.get("number_11").animate(pDuration, pFrame, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						frog.get("number_11").setTouch(true);
					}
				});
		frog.get("number_11").setVisible(true);
		frog.get("number_11").registerUpdateHandler(
				new TimerHandler(3.9f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						rcWaters.add((int) frog.get("number_11").getX(),
								(int) frog.get("number_11").getY());
						addFrogAvaiable(11);
					}
				}));
	}

	public void frog12Animation() throws Exception {
		rcFrog12.refresh();
		frog.get("number_12").setPosition(rcFrog12.getX(), rcFrog12.getY());
		frog.get("number_12").setVisible(true);
		frog.get("number_12").setTouch(true);
		resetLotus(rcFrog12.getIRandom());
		isTouchLotus[rcFrog12.getIRandom()] = false;
		frog.get("number_12").registerUpdateHandler(
				new TimerHandler(3f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						addFrogAvaiable(12);
						isTouchLotus[rcFrog12.getIRandom()] = true;
					}
				}));
	}

	public void frog13Animation(final int randomWave) throws Exception {
		rcWaters.refresh();
		frog.get("number_13").setPosition(sWaves[randomWave].getX(),
				sWaves[randomWave].getY());
		frog.get("number_13").setVisible(true);
		frog.get("number_13").setTouch(true);
		sWaves[randomWave].clearEntityModifiers();
		sWaves[randomWave].registerEntityModifier(new ScaleModifier(1f, 1.0f,
				1.2f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								sWaves[randomWave].clearEntityModifiers();
								sWaves[randomWave].setScale(1f);
							}
						});
					}
				}));
		frog.get("number_13").registerEntityModifier(
				new MoveModifier(3f, sWaves[randomWave].getX(), 960,
						sWaves[randomWave].getY(), 0,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								frog.get("number_13").stopAnimation(0);
								frog.get("number_13").detachChildren();
								addFrogAvaiable(13);
							}
						}));
	}

	public void frog15Animation() throws Exception {
		rcWaters.refresh();
		frog.get("number_15").setPosition(rcWaters.getX(), rcWaters.getY());
		rcWaters.remove_current();
		long pDuration[] = new long[] { 300, 300 };
		int pFrame[] = new int[] { 0, 1 };
		frog.get("number_15").animate(pDuration, pFrame, 0,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						frog.get("number_15").setTouch(true);
					}
				});
		frog.get("number_15").setVisible(true);
		frog.get("number_15").registerUpdateHandler(
				new TimerHandler(3.9f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						rcWaters.add((int) frog.get("number_15").getX(),
								(int) frog.get("number_15").getY());
						addFrogAvaiable(15);
					}
				}));
	}

	public void addFrogAvaiable(final int no) {
		try {
			if (!frogAvaiable.contains("number_" + no)) {
				frogAvaiable.add("number_" + no);
			}
			currentFrogAppear--;
			frog.get("number_" + no).setVisible(false);
			frog.get("number_" + no).setTouch(false);
			if (no == 12) {
				isTouchLotus[rcFrog12.getIRandom()] = true;
			}
		} catch (Exception e) {
		}
	}

	public void omedetoLotus() throws Exception {
		sOmedetoBackground.setVisible(true);
		sOmedetoLotus.registerEntityModifier(new MoveYModifier(3f, 640,
				sOmedetoLotus.getmYFirst(), new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						sOmedetoLotus.setVisible(true);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						isTouchOmedetoLotus = true;
					}
				}));
	}

	public void omedeto() throws Exception {
		a2_04_omedeto2.play();
		asOmedeto.setVisible(true);
		asOmedeto.animate(250, 5, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				try {
					newSessionGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void newSessionGame() throws Exception {
		sOmedetoBackground.setVisible(false);
		sOmedetoLotus.setVisible(false);
		asOmedeto.setVisible(false);
		isTouchOmedetoLotus = false;
		if (asCrocodile.isVisible()) {
			isTouchCrocodile = true;
		}
		score = 0;
		asScore.stopAnimation(score);
	}

	public void resetLotus(final int index) throws Exception {
		sLotus[index].clearEntityModifiers();
		sLotus[index].clearUpdateHandlers();
		sLotus[index].setRotation(0f);
		sLotus[index].setPosition(sLotus[index].getmXFirst(),
				sLotus[index].getmYFirst());
	}

	public void onTouchFrog(final int number) throws Exception {
		score++;
		a2_04[score - 1].play();
		if (score == 20) {
			isTouchCrocodile = false;
			omedetoLotus();
		}
		a2_04_pinpon2.play();
		playSoundOnTouchFrog(number);
		asScore.stopAnimation(score);
		frog.get("number_" + number).setTouch(false);
		frog.get("number_" + number).stopAnimation(
				frog.get("number_" + number).getTileCount() - 1);
		Sprite sStar = new Sprite(0, 0, txtStar,
				this.getVertexBufferObjectManager());
		sStar.setSize(frog.get("number_" + number).getWidth(),
				frog.get("number_" + number).getHeight());
		frog.get("number_" + number).attachChild(sStar);
		sStar.registerEntityModifier(new ScaleModifier(1f, 1.0f, 1.3f));
		if (number != 13) {
			frog.get("number_" + number).clearEntityModifiers();
			frog.get("number_" + number).clearUpdateHandlers();
			frog.get("number_" + number).registerUpdateHandler(
					new TimerHandler(1f, new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							frog.get("number_" + number).detachChildren();
							frog.get("number_" + number).stopAnimation(0);
							if (frog.get("number_" + number).getOnLotus() != -1) {
								lotusIsAvaiable[frog.get("number_" + number)
										.getOnLotus()] = true;
								isTouchLotus[frog.get("number_" + number)
										.getOnLotus()] = true;
							}
							addFrogAvaiable(number);
						}
					}));
		}
		if (number == 11 || number == 15) {
			rcWaters.add((int) frog.get("number_" + number).getX(), (int) frog
					.get("number_" + number).getY());
		}
	}

	public void playSoundOnTouchFrog(final int number) {
		switch (number) {
		case 7:
			a2_07_kaeru4.play();
			break;
		case 8:
			a2_08_kaeru3.play();
			break;
		case 9:
			a2_09_kaeru5.play();
			break;
		case 10:
		case 13:
			a2_10_13_kaeru2.play();
			break;
		case 11:
		case 17:
			a2_11_17_kaeru8.play();
			break;
		case 12:
		case 15:
			a2_12_15_kaeru7.play();
			break;
		case 14:
		case 16:
			a2_14_16_kaeru6.play();
			break;
		}
	}

	public void onTouchCrocodile() throws Exception {
		long pDuration[] = new long[] { 300, 300, 300, 300, 300, 300, 800 };
		int pFrame[] = null;
		if (ranCorocodile == 2) {
			pFrame = new int[] { 0, 1, 0, 1, 0, 1, 0 };
		} else {
			pFrame = new int[] { 1, 2, 1, 2, 1, 2, 0 };
		}
		a2_05_gao.play();
		asCrocodile.animate(pDuration, pFrame, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
					int paramInt) {
			}

			@Override
			public void onAnimationLoopFinished(
					AnimatedSprite paramAnimatedSprite, int paramInt1,
					int paramInt2) {
			}

			@Override
			public void onAnimationFrameChanged(
					AnimatedSprite paramAnimatedSprite, int paramInt1,
					int paramInt2) {
			}

			@Override
			public void onAnimationFinished(AnimatedSprite paramAnimatedSprite) {
				runOnUpdateThread(new Runnable() {
					public void run() {

						asCrocodile.setVisible(false);
						if (ranCorocodile == 2) {
							rcWaters.add((int) asCrocodile.getX(),
									(int) asCrocodile.getY());
						}
					}
				});
			}
		});
		asCrocodile.registerEntityModifier(new ScaleModifier(2.4f, 1.0f, 1.1f));
	}

	public void onTouchTadpoles() throws Exception {
		long[] pFrameDurations = { 200, 200 };
		int toX = 0;
		int toY = 640;
		switch (ranTadpoles) {
		case 0:
		case 2:
			break;
		case 1:
		case 3:
			toX = 960;
			break;
		}
		a2_06_bokotyapo.play();
		asTadpoles[ranTadpoles].animate(pFrameDurations, 2, 3, true);
		asTadpoles[ranTadpoles].registerEntityModifier(new MoveModifier(5.5f,
				asTadpoles[ranTadpoles].getX(), toX, asTadpoles[ranTadpoles]
						.getY(), toY, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {

					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
						asTadpoles[ranTadpoles].setVisible(false);
						ranTadpoles = new Random().nextInt(4);
						rcTadpoles.refresh();
						asTadpoles[ranTadpoles].setPosition(rcTadpoles.getX(),
								rcTadpoles.getY());
						asTadpoles[ranTadpoles].setmXFirst(rcTadpoles.getX());
						asTadpoles[ranTadpoles].setmYFirst(rcTadpoles.getY());
						asTadpoles[ranTadpoles].setVisible(true);
						isTouchTadpoles = true;
					}
				}));
	}

	public void ontouchLotus(final int i) throws Exception {
		sLotus[i].registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
						isTouchLotus[i] = true;
					}
				}, new ParallelEntityModifier(new RotationModifier(1f, 0, 20),
						new MoveXModifier(1f, sLotus[i].getX(), sLotus[i]
								.getX() + 20)), new ParallelEntityModifier(
						new RotationModifier(1f, 20, 0), new MoveXModifier(1f,
								sLotus[i].getX() + 20, sLotus[i].getX()))));
	}

	@Override
	public boolean onSceneTouchEvent(Scene paramScene,
			TouchEvent paramTouchEvent) {
		int pX = (int) paramTouchEvent.getX();
		int pY = (int) paramTouchEvent.getY();
		if (paramTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			try {
				if (isTouchCrocodile
						&& checkContains(asCrocodile, (int) 0, 0,
								(int) asCrocodile.getWidth(),
								(int) asCrocodile.getHeight(), pX, pY)) {
					isTouchCrocodile = false;
					onTouchCrocodile();
					return true;
				}
				if (isTouchTadpoles
						&& checkContains(asTadpoles[ranTadpoles], (int) 0, 0,
								(int) asTadpoles[ranTadpoles].getWidth(),
								(int) asTadpoles[ranTadpoles].getHeight(), pX,
								pY)) {
					isTouchTadpoles = false;
					onTouchTadpoles();
					return true;
				}
				for (int i = 0; i < 12; i++) {
					if (!sOmedetoBackground.isVisible()
							&& frog.get("number_" + (i + 7)).isTouch()
							&& checkContains(frog.get("number_" + (i + 7)),
									(int) 0, 0,
									(int) frog.get("number_" + (i + 7))
											.getWidth(),
									(int) frog.get("number_" + (i + 7))
											.getHeight(), pX, pY)) {
						onTouchFrog(i + 7);
						return true;
					}
				}
				for (int i = 0; i < sLotus.length; i++) {
					if (isTouchLotus[i]
							&& checkContains(sLotus[i], (int) 0, 0,
									(int) sLotus[i].getWidth(),
									(int) sLotus[i].getHeight(), pX, pY)) {
						isTouchLotus[i] = false;
						ontouchLotus(i);
						return true;
					}
				}
				if (isTouchOmedetoLotus
						&& checkContains(sOmedetoLotus, (int) 0, 0,
								(int) asCrocodile.getWidth(),
								(int) asCrocodile.getHeight(), pX, pY)) {
					isTouchOmedetoLotus = false;
					omedeto();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public class FrogAnimatedSprite extends AnimatedSprite {

		private boolean isTouch = false;
		private int onLotus = -1;

		public FrogAnimatedSprite(int i, int j,
				TiledTextureRegion tiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(i, j, tiledTextureRegion, vertexBufferObjectManager);
		}

		public boolean isTouch() {
			return isTouch;
		}

		public void setTouch(boolean isTouch) {
			this.isTouch = isTouch;
		}

		public int getOnLotus() {
			return onLotus;
		}

		public void setOnLotus(int onLotus) {
			this.onLotus = onLotus;
		}

		public void addStar() {

		}

	}

	public class RandomCoordinate {

		private int x;
		private int y;
		private int iRandom;
		private ArrayList<Integer> x_collection;
		private ArrayList<Integer> y_collection;

		public RandomCoordinate() {
			x_collection = new ArrayList<Integer>();
			y_collection = new ArrayList<Integer>();
			iRandom = 0;
		}

		public void add(int x, int y) {
			x_collection.add(x);
			y_collection.add(y);
			if (x_collection.size() == 1) {
				this.iRandom = 0;
			} else {
				this.iRandom = getRandom();
			}
			this.x = x_collection.get(iRandom);
			this.y = y_collection.get(iRandom);
		}

		public void remove_current() {
			x_collection.remove(iRandom);
			y_collection.remove(iRandom);
			if (x_collection.size() == 1) {
				this.iRandom = 0;
			} else {
				this.iRandom = getRandom();
			}
			this.x = x_collection.get(iRandom);
			this.y = y_collection.get(iRandom);
		}

		public void refresh() {
			if (x_collection.size() == 1) {
				this.iRandom = 0;
			} else {
				this.iRandom = getRandom();
			}
			this.x = x_collection.get(iRandom);
			this.y = y_collection.get(iRandom);
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getIRandom() {
			return this.iRandom;
		}

		private int getRandom() {
			int tmp = -1;
			do {
				tmp = new Random().nextInt(x_collection.size());
				if (tmp != iRandom) {
					break;
				}
			} while (true);
			return tmp;
		}

	}
}
