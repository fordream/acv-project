package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OfurosukiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;

import android.os.Handler;
import android.util.Log;

public class Vol3Ofurosuki extends BaseGameActivity implements IOnSceneTouchListener, IAnimationListener {

	private TexturePack packBackground, packOther, packChildren, packAnimals;
	private TexturePackTextureRegionLibrary packLibBackground, packLibOther, packLibChildren, packLibAnimals;
	private TexturePackerTextureRegion txrBackground1, txrBackground2, txrBackground3, txrShower, txrMountain, txrSky, txrCloud1, txrCloud2, txrLargeDuck, txrSquirrel, txrTowel, txrShowerWater, txrStar;
	private Sprite sBackground1, sBackground2, sBackground3, sShower, sMountain, sSky, sCloud1, sCloud2, sLargeDuck, sSquirrel, sTowel, sShowerWater, sStar;
	private TiledTextureRegion ttrRiver, ttrSmallDuck, ttrBird, ttrShampoo;
	private AnimatedSprite asRiver, asSmallDuck, asBird, asShampoo;

	private TexturePackerTextureRegion[] txrSteam = new TexturePackerTextureRegion[4];
	private int[] intSteam = { Vol3OfurosukiResource.others.A6_9_1_IPHONE_ID, Vol3OfurosukiResource.others.A6_9_2_IPHONE_ID, Vol3OfurosukiResource.others.A6_9_3_IPHONE_ID, Vol3OfurosukiResource.others.A6_9_4_IPHONE_ID, };
	private ITextureRegion[] txrCostumer = new ITextureRegion[5];
	private Sprite[] sCostume = new Sprite[5];
	private int[] yCostumeWithBody = { 100, 185, 203, 158, 158 };
	private int[] intCostumerId = { Vol3OfurosukiResource.children_clother.A6_12_9_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_7_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_5_IPHONE_ID,
			Vol3OfurosukiResource.children_clother.A6_12_3_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_1_IPHONE_ID, };
	private ITextureRegion[] txrHook = new ITextureRegion[5];
	private Sprite[] sHook = new Sprite[5];
	private ITextureRegion[] txrHookCostumer = new ITextureRegion[5];
	private Sprite[] sHookCostumer = new Sprite[5];
	private int[] intHookCostumerId = { Vol3OfurosukiResource.children_clother.A6_12_10_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_8_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_6_IPHONE_ID,
			Vol3OfurosukiResource.children_clother.A6_12_4_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_12_2_IPHONE_ID, };
	private TiledTextureRegion[] ttrSwimmer = new TiledTextureRegion[5];
	private SwimmerAnimatedSprite[] asSwimmer = new SwimmerAnimatedSprite[5];
	private int[] imageSwimmer = new int[5];
	private int[][] intSwimmerId = {
			{ Vol3OfurosukiResource.animals.A6_4_1_1_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_2_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_3_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_4_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_1_5_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_6_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_7_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_8_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_1_9_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_10_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_1_11_IPHONE_ID, Vol3OfurosukiResource.animals.A6_X_1_12_IPHONE_ID },
			{ Vol3OfurosukiResource.animals.A6_4_2_1_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_2_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_3_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_4_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_2_5_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_6_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_7_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_8_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_2_9_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_10_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_2_11_IPHONE_ID, Vol3OfurosukiResource.animals.A6_X_1_12_IPHONE_ID },
			{ Vol3OfurosukiResource.animals.A6_4_3_1_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_2_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_3_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_4_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_3_5_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_6_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_7_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_8_IPHONE_ID,
					Vol3OfurosukiResource.animals.A6_4_3_9_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_10_IPHONE_ID, Vol3OfurosukiResource.animals.A6_4_3_11_IPHONE_ID, Vol3OfurosukiResource.animals.A6_X_1_12_IPHONE_ID },
			{ Vol3OfurosukiResource.children_clother.A6_4_4_1_IPHONE__ID, Vol3OfurosukiResource.children_clother.A6_4_4_2_IPHONE__ID, Vol3OfurosukiResource.children_clother.A6_4_4_3_IPHONE__ID,
					Vol3OfurosukiResource.children_clother.A6_4_4_4_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_4_5_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_4_6_IPHONE_ID,
					Vol3OfurosukiResource.children_clother.A6_4_4_7_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_4_8_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_4_9_IPHONE_ID,
					Vol3OfurosukiResource.children_clother.A6_4_4_10_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_4_11_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_X_1_12_IPHONE_ID },
			{ Vol3OfurosukiResource.children_clother.A6_4_5_1_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_2_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_3_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_4_IPHONE_ID,
					Vol3OfurosukiResource.children_clother.A6_4_5_5_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_6_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_7_IPHONE_ID,
					Vol3OfurosukiResource.children_clother.A6_4_5_8_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_9_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_4_5_10_IPHONE_ID,
					Vol3OfurosukiResource.children_clother.A6_4_5_11_IPHONE_ID, Vol3OfurosukiResource.children_clother.A6_X_1_12_IPHONE_ID } };

	private boolean isTouchSmallDuck = true, isTouchSquirrel = true, checkTouchTowel = true, checkCostume = true, isTouchBird = true, isTouchLargeDuck = true, istouchSwimmer = false, checkMoveTowel = false, checkSound = true, checkSound1 = true,
			touchBubble = true, checkMove = false, checkMoveSwimmer = true;

	private boolean[] isTouchCostume = { false, false, false, false, false };

	private static AnimatedSpritePool sasSteam, spBubble;
	private TiledTextureRegion ttrSteam;

	private static SpritePool spDroplet;
	private ITextureRegion txrDroplet;
	private TiledTextureRegion txrBubble;
	Rectangle leftLake, rightLake, localLake;

	private int[][][] intLake = { { { 305, 230 }, { 594, 232 } }, { { 300, 165 }, { 600, 165 } }, { { 300, 160 }, { 596, 160 } }, { { 300, 195 }, { 596, 195 } }, { { 300, 195 }, { 596, 195 } } };

	private int[][] intTouchSwimmer = { { 93, 46, 192, 230 }, { 96, 130, 168, 282 }, { 109, 148, 159, 294 }, { 99, 96, 156, 231 }, { 100, 90, 154, 228 } };
	private boolean[] statusLocalLake = { false, false };

	private int[] intXHook = { 450, 320 };
	private boolean[] boolXHook = { false, false };
	private TiledTextureRegion ttrWater;
	private AnimatedSpritePool asWater;
	private int intLocationStart = -1;
	private int lastX = 0, lastY = 0;
	private int checkX = 0, checkY = 0;
	private int checkTowelX = 0, checkTowelY = 0;
	private ArrayList<AnimatedSprite> lsSteam = new ArrayList<AnimatedSprite>();
	private ArrayList<AnimatedSprite> lsWater = new ArrayList<AnimatedSprite>();
	@SuppressWarnings("unchecked")
	private ArrayList<Sprite>[] lsDroplet = new ArrayList[5];
	private ArrayList<AnimatedSprite> lsBubbleLeft = new ArrayList<AnimatedSprite>();
	private ArrayList<AnimatedSprite> lsBubbleRight = new ArrayList<AnimatedSprite>();
	private ArrayList<Integer> lsRandomSwimmer = new ArrayList<Integer>();
	private Sound A6_20_FUITAATO, A6_2_AHIRU, A6_3_KARADAFUKU, A6_3_TAWL, A6_4_1_2, A6_4_1_KUMA, A6_4_2_2, A6_4_2_KITSUNE, A6_4_3_2, A6_4_3_USAGI, A6_4_4_2, A6_4_4_BOY, A6_4_5_2, A6_4_5_GIRL, A6_4_FURODERU_KARUI, A6_4_FURODERU_OMOI,
			A6_4_FUROHAIRU_KARUI, A6_4_FUROHAIRU_OMOI, A6_4_FUROSHIZIUMU_KARUI, A6_4_FUROSHIZIUMU_OMOI, A6_4_KYARA, A6_4_SUITEKI, A6_4A_1, A6_4A_2, A6_4A_3, A6_4A_4, A6_4B_1, A6_4B_2, A6_4B_3, A6_6_BASA, A6_7_RISU, A6_8_AWA_SYAKA, A6_8_PONPU_AWA,
			A6_10_PONPU_MODORU, A6_11_2_S1, A6_11_2_S2, A6_12_FUKUIDOU, A6_12_FUKUYURERU, A6_12_HANGA_KAKARU, A6_15_FUNE, A6_8_AWA4, A6_8_AWA1, A6_8_AWA2, A6_8_AWA3;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("ofurosuki/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("ofurosuki/gfx/");
		super.onCreateResources();
	}

	private void initial() {

		asWater = new AnimatedSpritePool(ttrWater);
		sasSteam = new AnimatedSpritePool(ttrSteam);
		spDroplet = new SpritePool(txrDroplet);
		spBubble = new AnimatedSpritePool(txrBubble);
		isTouchSmallDuck = true;
		isTouchSquirrel = true;
		checkCostume = true;
		isTouchBird = true;
		isTouchLargeDuck = true;
		checkSound = true;
		checkSound1 = true;
		touchBubble = true;
		checkTouchTowel = true;
		checkMove = false;
		istouchSwimmer = false;
		checkMoveTowel = false;
		checkMoveSwimmer = true;
		for (int i = 0; i < isTouchCostume.length; i++) {
			isTouchCostume[i] = false;
		}
		for (int i = 0; i < statusLocalLake.length; i++) {
			statusLocalLake[i] = false;
		}
		for (int i = 0; i < boolXHook.length; i++) {
			boolXHook[i] = false;
		}
	}

	@Override
	protected void loadKaraokeResources() {

		/************* Load Background *****************/
		packBackground = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "ofurosuki/gfx/").load("background.xml");
		packBackground.loadTexture();
		packLibBackground = packBackground.getTexturePackTextureRegionLibrary();

		txrBackground1 = packLibBackground.get(Vol3OfurosukiResource.background.A6_11_1_IPHONE_ID);

		/************* Load Other *****************/
		packOther = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "ofurosuki/gfx/").load("others.xml");
		packOther.loadTexture();
		packLibOther = packOther.getTexturePackTextureRegionLibrary();

		txrBackground2 = packLibOther.get(Vol3OfurosukiResource.others.A6_14_1_IPHONE_ID);

		txrBackground3 = packLibOther.get(Vol3OfurosukiResource.others.A6_13_1_IPHONE_ID);

		txrShower = packLibOther.get(Vol3OfurosukiResource.others.A6_11_2_IPHONE_ID);

		ttrRiver = new TiledTextureRegion(packOther.getTexture(), packLibOther.get(Vol3OfurosukiResource.others.A6_16_1_IPHONE_ID), packLibOther.get(Vol3OfurosukiResource.others.A6_16_2_IPHONE_ID));

		txrMountain = packLibOther.get(Vol3OfurosukiResource.others.A6_17_1_IPHONE_ID);

		txrSky = packLibOther.get(Vol3OfurosukiResource.others.A6_19_1_IPHONE_ID);

		txrCloud1 = packLibOther.get(Vol3OfurosukiResource.others.A6_18_1_IPHONE_ID);

		txrCloud2 = packLibOther.get(Vol3OfurosukiResource.others.A6_18_2_IPHONE_ID);

		for (int i = 0; i < txrSteam.length; i++) {
			txrSteam[i] = packLibOther.get(intSteam[i]);
		}

		ttrSteam = new TiledTextureRegion(packOther.getTexture(), txrSteam);
		txrDroplet = packLibOther.get(Vol3OfurosukiResource.others.A6_5_2_IPHONE_ID);

		/************* Load Childrens *****************/
		packChildren = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "ofurosuki/gfx/").load("children_clother.xml");
		packChildren.loadTexture();
		packLibChildren = packChildren.getTexturePackTextureRegionLibrary();

		txrShowerWater = packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_5_1_IPHONE_ID);

		/************* Load Animals *****************/
		packAnimals = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "ofurosuki/gfx/").load("animals.xml");
		packAnimals.loadTexture();
		packLibAnimals = packAnimals.getTexturePackTextureRegionLibrary();

		txrLargeDuck = packLibAnimals.get(Vol3OfurosukiResource.animals.A6_15_1_IPHONE_ID);
		ttrSmallDuck = new TiledTextureRegion(packAnimals.getTexture(), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_1_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_2_IPHONE_ID),
				packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_3_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_4_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_5_IPHONE_ID),
				packLibAnimals.get(Vol3OfurosukiResource.animals.A6_2_6_IPHONE_ID));

		txrSquirrel = packLibAnimals.get(Vol3OfurosukiResource.animals.A6_7_1_IPHONE_ID);

		ttrBird = new TiledTextureRegion(packAnimals.getTexture(), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_6_1_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_6_2_IPHONE_ID),
				packLibAnimals.get(Vol3OfurosukiResource.animals.A6_6_3_IPHONE_ID));

		ttrShampoo = new TiledTextureRegion(packAnimals.getTexture(), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_10_2_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_10_1_IPHONE_ID));

		// txrBubble = packLibChildren
		// .get(Vol3OfurosukiResource.children_clother.A6_8_1_IPHONE_ID);
		txrBubble = new TiledTextureRegion(packChildren.getTexture(),
		// packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_8_1_IPHONE_ID),
		// packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_8_2_IPHONE_ID),
				packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_8_3_IPHONE_ID), packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_8_4_IPHONE_ID));
		txrTowel = packLibAnimals.get(Vol3OfurosukiResource.animals.A6_3_1_IPHONE_ID);

		txrStar = packLibAnimals.get(Vol3OfurosukiResource.animals.A6_20_IPHONE_ID);

		for (int i = 0; i < ttrSwimmer.length; i++) {
			ITextureRegion[] tmp = new ITextureRegion[intSwimmerId[i].length];
			for (int j = 0; j < intSwimmerId[i].length; j++) {
				if (i < 3) {
					tmp[j] = packLibAnimals.get(intSwimmerId[i][j]);
				} else {
					tmp[j] = packLibChildren.get(intSwimmerId[i][j]);
				}
			}
			if (i < 3) {
				ttrSwimmer[i] = new TiledTextureRegion(packAnimals.getTexture(), tmp);
			} else {
				ttrSwimmer[i] = new TiledTextureRegion(packChildren.getTexture(), tmp);
			}
		}

		for (int i = 0; i < txrCostumer.length; i++) {
			txrHook[i] = packLibChildren.get(Vol3OfurosukiResource.children_clother.A6_12_11_IPHONE_ID);
			txrCostumer[i] = packLibChildren.get(intCostumerId[i]);
			txrHookCostumer[i] = packLibChildren.get(intHookCostumerId[i]);
		}

		ttrWater = new TiledTextureRegion(packAnimals.getTexture(), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_1_1_IPHONE_ID), packLibAnimals.get(Vol3OfurosukiResource.animals.A6_1_2_IPHONE_ID));

	}

	@Override
	protected void loadKaraokeSound() {
		A6_20_FUITAATO = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_20_FUITAATO);
		A6_2_AHIRU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_2_AHIRU);
		A6_3_KARADAFUKU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_3_KARADAFUKU);
		A6_3_TAWL = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_3_TAWL);
		A6_4_1_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_1_2);
		A6_4_1_KUMA = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_1_KUMA);
		A6_4_2_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_2_2);
		A6_4_2_KITSUNE = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_2_KITSUNE);
		A6_4_3_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_3_2);
		A6_4_3_USAGI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_3_USAGI);
		A6_4_4_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_4_2);
		A6_4_4_BOY = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_4_BOY);
		A6_4_5_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_5_2);
		A6_4_5_GIRL = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_5_GIRL);
		A6_4_FURODERU_KARUI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FURODERU_KARUI);
		A6_4_FURODERU_OMOI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FURODERU_OMOI);
		A6_4_FUROHAIRU_KARUI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FUROHAIRU_KARUI);
		A6_4_FUROHAIRU_OMOI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FUROHAIRU_OMOI);
		A6_4_FUROSHIZIUMU_KARUI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FUROSHIZIUMU_KARUI);
		A6_4_FUROSHIZIUMU_OMOI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_FUROSHIZIUMU_OMOI);
		A6_4_KYARA = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_KYARA);
		A6_4_SUITEKI = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4_SUITEKI);
		A6_4A_1 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4A_1);
		A6_4A_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4A_2);
		A6_4A_3 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4A_3);
		A6_4A_4 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4A_4);
		A6_4B_1 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4B_1);
		A6_4B_2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4B_2);
		A6_4B_3 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_4B_3);
		A6_6_BASA = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_6_BASA);
		A6_7_RISU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_7_RISU);
		A6_8_AWA_SYAKA = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_AWA_SYAKA);
		A6_8_PONPU_AWA = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_PONPU_AWA);
		A6_10_PONPU_MODORU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_10_PONPU_MODORU);
		A6_11_2_S1 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_11_2_S1);
		A6_11_2_S2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_11_2_S2);
		A6_12_FUKUIDOU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_12_FUKUIDOU);
		A6_12_FUKUYURERU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_12_FUKUYURERU);
		A6_12_HANGA_KAKARU = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_12_HANGA_KAKARU);
		A6_15_FUNE = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_15_FUNE);
		A6_8_AWA4 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_AWA4);
		A6_8_AWA1 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_AWA1);
		A6_8_AWA2 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_AWA2);
		A6_8_AWA3 = loadSoundResourceFromSD(Vol3OfurosukiResource.A6_8_AWA3);

	}

	@Override
	protected void loadKaraokeScene() {
		if (mScene == null)
			this.mScene = new Scene();

		sSky = new Sprite(-4, -35, txrSky, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSky);
		sSky.setZIndex(1);

		sCloud1 = new Sprite(396, 92, txrCloud1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCloud1);
		sCloud1.setZIndex(2);

		sCloud2 = new Sprite(571, 79, txrCloud2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCloud2);
		sCloud2.setZIndex(3);

		sMountain = new Sprite(-84, 110, txrMountain, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMountain);
		sMountain.setZIndex(4);

		asRiver = new AnimatedSprite(-122, 51, ttrRiver, this.getVertexBufferObjectManager());
		this.mScene.attachChild(asRiver);
		asRiver.setZIndex(5);

		sLargeDuck = new Sprite(415, 133, txrLargeDuck, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLargeDuck);
		sLargeDuck.setZIndex(6);

		sBackground3 = new Sprite(-40, 97, txrBackground3, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground3);
		sBackground3.setZIndex(7);

		sBackground2 = new Sprite(-237, -1, txrBackground2, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground2);
		sBackground2.setZIndex(8);

		sBackground1 = new Sprite(-160, -263, txrBackground1, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground1);
		sBackground1.setZIndex(9);

		sShower = new Sprite(716, 94, txrShower, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sShower);
		sShower.setZIndex(10);

		sShowerWater = new Sprite(265, 116, txrShowerWater, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sShowerWater);
		sShowerWater.setScaleCenter(485, 0);
		sShowerWater.setVisible(false);
		sShowerWater.setZIndex(411);

		asSmallDuck = new AnimatedSprite(275, 398, ttrSmallDuck, this.getVertexBufferObjectManager());
		this.mScene.attachChild(asSmallDuck);
		asSmallDuck.setZIndex(60);

		asShampoo = new AnimatedSprite(860, 300, ttrShampoo, this.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pX, final float pY) {
				// --------------------------------------------------------------
				// 20130326
				// binh xit sua loi
				// -------------------------------------------------------------
				if (isPause) {
					return false;
				}
				// --------------------------------------------------------------
				// 20130326 END
				// binh xit sua loi
				// -------------------------------------------------------------

				int px = (int) pSceneTouchEvent.getX();
				int py = (int) pSceneTouchEvent.getY();
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					checkSound = false;
					checkX = px;
					checkY = py;
					break;
				case TouchEvent.ACTION_MOVE:
					Log.e("TEST_TEST", "ABC");
					if (px < checkX - this.getWidth() / 4 || py < checkY - this.getHeight() / 6 || px > checkX + this.getWidth() / 4 || py > checkY + this.getHeight() / 6) {

						checkSound = true;

						if (!asShampoo.isAnimationRunning()) {
							asShampoo.clearEntityModifiers();
							if (checkSound) {
								checkSound = false;
								checkSound1 = false;
								A6_10_PONPU_MODORU.play();
							}
							asShampoo.animate(400, true, Vol3Ofurosuki.this);
							asShampoo.setRotation(-20);
							asShampoo.setZIndex(301);
							mScene.sortChildren();

						}
						this.setPosition(px - this.getWidth() * 3 / 4, py - this.getHeight() * 3 / 4);
					}
					break;
				case TouchEvent.ACTION_UP:
					this.setPosition(this.getmXFirst(), this.getmYFirst());
					if (!checkSound1) {
						this.setZIndex(13);
						mScene.sortChildren();
						A6_10_PONPU_MODORU.play();
						checkSound1 = true;
					}
					asShampoo.stopAnimation(1);
					asShampoo.setRotation(0);
					asShampoo.clearUpdateHandlers();
					asShampoo.registerEntityModifier(rotationShampoo());
					if (lsBubbleLeft.size() > 0 || lsBubbleRight.size() > 0) {
						sShower.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.5f, 1.0f, 1.3f), new ScaleModifier(1.5f, 1.30f, 1.0f))));
					}
					break;
				}
				return true;
			};
		};
		this.mScene.attachChild(asShampoo);
		this.mScene.registerTouchArea(asShampoo);
		asShampoo.stopAnimation(1);
		asShampoo.setZIndex(13);
		asShampoo.setFlippedHorizontal(true);

		sSquirrel = new Sprite(100, 486, txrSquirrel, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSquirrel);
		sSquirrel.setZIndex(14);

		asBird = new AnimatedSprite(626, 5, ttrBird, this.getVertexBufferObjectManager());
		this.mScene.attachChild(asBird);
		asBird.setZIndex(15);

		for (int i = 0; i < asSwimmer.length; i++) {
			final int index = i;
			asSwimmer[i] = new SwimmerAnimatedSprite(-1 * ttrSwimmer[i].getWidth(), 0, ttrSwimmer[i], this.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pX, final float pY) {
					if (!this.isTouchSwimmer()) {
						return false;
					}
					int px = (int) pSceneTouchEvent.getX();
					int py = (int) pSceneTouchEvent.getY();
					switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:

						if (checkContains(asSwimmer[index], intTouchSwimmer[index][0], intTouchSwimmer[index][1], intTouchSwimmer[index][2], intTouchSwimmer[index][3], px, py)) {
							lastX = px;
							lastY = py;

							this.setMoving(false);
							istouchSwimmer = true;
							Log.e("*********", "action_Down in asSwimmer");
							this.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {

								@Override
								public void onTimePassed(TimerHandler arg0) {
									if (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status() || imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status()) {
										swimmerIsDiving(index);
									}
								}
							}));
						}
						break;
					case TouchEvent.ACTION_MOVE:

						if (Math.abs((lastX + lastY) - (px + py)) >= 25 && istouchSwimmer && asSwimmer[index].getStatusSwimmer() == StatusSwimmer.SWIMMING_NOT_SHAMPOO.status()
								&& (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status() || imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status())) {
							this.setMoving(true);
							if (checkMoveSwimmer) {
								this.setZIndex(200);
								checkMoveSwimmer = false;
								mScene.sortChildren();
							}
							this.clearEntityModifiers();
							this.clearUpdateHandlers();
							this.stopAnimation(0);
							this.setPosition(px - this.getWidth() / 2, py - this.getHeight() / 2);

						}

						break;
					case TouchEvent.ACTION_UP:
						istouchSwimmer = false;
						if (!checkMoveSwimmer) {
							this.setZIndex(16 + index);
							mScene.sortChildren();
							checkMoveSwimmer = true;
						}
						if (this.isMoving() && (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status() || imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status())) {
							this.setMoving(false);
							Log.e(" ACTION_UP ", "@@@@@@@@@@@@@@@@@@@@2");
							if (localLake.contains(px, py)) {
								if (index == 0 || index == 3 || index == 4) {
									A6_4_FURODERU_OMOI.play();
								}
								if (index == 1 || index == 2) {
									A6_4_FURODERU_KARUI.play();
								}
							}
							checkDraftSwimerOutLake(index, px, py);
						}

						break;
					}
					return true;
				}
			};
			this.mScene.registerTouchArea(asSwimmer[i]);
			this.mScene.attachChild(asSwimmer[i]);
			asSwimmer[i].setVisible(false);
			asSwimmer[i].setZIndex(16 + i);
		}
		sTowel = new Sprite(-206, 195, txrTowel, this.getVertexBufferObjectManager()) {

			int count = 0;
			int lastX = 0;
			int lastY = 0;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pX, final float pY) {
				if (!checkTouchTowel) {
					return false;
				}
				int px = (int) pSceneTouchEvent.getX();
				int py = (int) pSceneTouchEvent.getY();
				if (sTowel.isVisible()) {
					switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						count = 0;
						checkTowelX = px;
						checkTowelY = py;
						break;
					case TouchEvent.ACTION_MOVE:
						if (px < checkTowelX - sTowel.getWidth() / 6 || py < checkTowelY - sTowel.getHeight() / 6 || px > checkTowelX + sTowel.getWidth() / 6 || py > checkTowelY + sTowel.getHeight() / 6) {
							sTowel.setPosition(px - sTowel.getWidth() / 2, py - sTowel.getHeight() / 2);
							if (!checkMoveTowel) {
								checkMoveTowel = true;
								sTowel.setScale(1.0f);
								A6_3_KARADAFUKU.play();
								mScene.clearUpdateHandlers();
								mScene.registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback() {
									@Override
									public void onTimePassed(TimerHandler arg0) {
										checkMoveTowel = false;
									}
								}));
							}
							if (intLocationStart != -1 && px != lastX && py != lastY && asSwimmer[intLocationStart].contains(px, py)) {
								count++;
								sTowel.clearEntityModifiers();
							}
							if (count == 30) {
								sTowel.setVisible(false);
								mScene.clearUpdateHandlers();
								checkMoveTowel = false;
								A6_3_KARADAFUKU.stop();
								sTowel.setPosition(sTowel.getmXFirst(), sTowel.getmYFirst());
								wasDriedSuccessfull();
								count = 0;
							}
							lastX = px;
							lastY = py;
						}
						break;
					case TouchEvent.ACTION_UP:
						if (count < 30) {
							sTowel.setScale(0.8f);
							sTowel.setZIndex(390);
							mScene.sortChildren();
							sTowel.setPosition(80, 195);
							count = 0;
							sTowel.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.5f, sTowel.getY(), sTowel.getY() - 12), new MoveYModifier(0.5f, sTowel.getY() - 12, sTowel.getY()))));
						}
						break;
					}
				}
				return true;
			}
		};
		mScene.registerTouchArea(sTowel);
		mScene.attachChild(sTowel);
		sTowel.setZIndex(390);
		sTowel.setVisible(false);

		for (int i = 0; i < sCostume.length; i++) {
			final int index = i;
			sHook[i] = new Sprite(-180, 90, txrHook[i], this.getVertexBufferObjectManager());
			sHook[i].setVisible(false);
			this.mScene.attachChild(sHook[i]);
			sHook[i].setZIndex(12 + i);
			sCostume[i] = new Sprite(-176, 90, txrCostumer[i], this.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pX, final float pY) {
					Log.e("*******************", "isTouchCostume[" + index + "]= " + isTouchCostume[index]);
					if (!isTouchCostume[index]) {
						return false;
					}
					int px = (int) pSceneTouchEvent.getX();
					int py = (int) pSceneTouchEvent.getY();

					switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						checkX = px;
						checkY = py;
						Log.e("*********", "action_Down in sCostume");
						if (asSwimmer[index].getStatusSwimmer() == StatusSwimmer.DRESSING.status() || (intLocationStart != -1 && asSwimmer[intLocationStart].getStatusSwimmer() == StatusSwimmer.WAS_DRIED.status())) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									sCostume[index].detachSelf();
									sCostume[index].setPosition(asSwimmer[intLocationStart].getX() + 48, yCostumeWithBody[intLocationStart]);
									mScene.attachChild(sCostume[index]);
									sCostume[index].setZIndex(250);
									mScene.sortChildren();
								}
							});
						}

						if (intLocationStart != -1 && asSwimmer[intLocationStart].getStatusSwimmer() == StatusSwimmer.WAS_DRIED.status()) {
							Log.e("*********", "action_Down in sCostume + moveCostumerFromHook" + index);
							moveCostumerFromHook(index);
						}
						break;
					case TouchEvent.ACTION_MOVE:
						if (asSwimmer[index].getStatusSwimmer() == StatusSwimmer.DRESSING.status()) {
							Log.d("$$$$$$$$$$$$$$$$$", "you dang move: " + px);
							if (px < checkX - this.getWidth() / 4 || py < checkY - this.getHeight() / 4 || px > checkX + this.getWidth() / 4 || py > checkY + this.getHeight() / 4) {
								checkCostume = false;
								this.setPosition(px - this.getWidth() / 2, py - this.getHeight() / 2);
							}
						}
						break;
					case TouchEvent.ACTION_UP:
						if (asSwimmer[index].getStatusSwimmer() == StatusSwimmer.DRESSING.status() && !checkCostume) {
							checkCostume = true;
							Log.e("*********", "ACTION_UP in sCostume + moveCostumerFromHook");
							moveCostumerToHook(index, px, py);
							sCostume[index].setZIndex(40);
							mScene.sortChildren();
						}
						break;
					}
					return true;
				}
			};
			this.mScene.registerTouchArea(sCostume[i]);
			sHookCostumer[i] = new Sprite(-176, 90, txrHookCostumer[i], this.getVertexBufferObjectManager());
			sHook[i].attachChild(sHookCostumer[i]);
			sHookCostumer[i].setPosition(0, 0);
		}

		leftLake = new Rectangle(398, 280, 80, 150, this.getVertexBufferObjectManager());
		leftLake.setAlpha(0.0f);
		// leftLake.setColor(1f, 0.4f, 0.2f);
		mScene.attachChild(leftLake);
		leftLake.setZIndex(27);

		rightLake = new Rectangle(692, 280, 80, 150, this.getVertexBufferObjectManager());
		rightLake.setAlpha(0.0f);
		// rightLake.setColor(1f, 0.4f, 0.2f);
		mScene.attachChild(rightLake);
		rightLake.setZIndex(28);

		localLake = new Rectangle(300, 360, 550, 100, this.getVertexBufferObjectManager());
		localLake.setAlpha(0.0f);
		mScene.attachChild(localLake);
		localLake.setZIndex(29);

		for (int i = 0; i < lsDroplet.length; i++) {
			lsDroplet[i] = new ArrayList<Sprite>();
		}
		sStar = new Sprite(0, 0, txrStar, this.getVertexBufferObjectManager());
		sStar.setVisible(false);

		this.mScene.sortChildren();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);

	}

	@Override
	public void combineGimmic3WithAction() {

	}

	@Override
	public synchronized void onPauseGame() {
		// --------------------------------------------------------------
		// 20130326
		// binh xit sua loi
		// -------------------------------------------------------------
		if (asShampoo != null) {
			asShampoo.setPosition(-1000, -1000);
		}

		if (asBird != null) {
			asBird.setPosition(-1000, -1000);
		}

		// --------------------------------------------------------------
		// 20130326 END
		// binh xit sua loi
		// ------------------------------------------------------------
		// TODO Auto-generated method stub
		stopSound();
		resetData();
		super.onPauseGame();
	}

	private void resetData() {
		if (sLargeDuck != null) {
			sLargeDuck.clearEntityModifiers();
			sLargeDuck.setPosition(sLargeDuck.getmXFirst(), sLargeDuck.getmYFirst());
		}
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {

				for (Iterator<AnimatedSprite> iterator = lsSteam.iterator(); iterator.hasNext();) {
					AnimatedSprite type = iterator.next();
					sasSteam.onHandleRecycleItem(type);
				}
				lsSteam.clear();

				for (Iterator<AnimatedSprite> iterator = lsBubbleLeft.iterator(); iterator.hasNext();) {
					AnimatedSprite type = iterator.next();
					spBubble.onHandleRecycleItem(type);
				}
				lsBubbleLeft.clear();

				for (Iterator<AnimatedSprite> iterator = lsBubbleRight.iterator(); iterator.hasNext();) {
					AnimatedSprite type = iterator.next();
					spBubble.onHandleRecycleItem(type);
				}
				lsBubbleRight.clear();

				for (Iterator<AnimatedSprite> iterator = lsWater.iterator(); iterator.hasNext();) {
					AnimatedSprite type = iterator.next();
					asWater.onHandleRecycleItem(type);
				}
				lsWater.clear();
				lsRandomSwimmer.clear();

				for (int i = 0; i < lsDroplet.length; i++) {
					for (Iterator<Sprite> iterator = lsDroplet[i].iterator(); iterator.hasNext();) {
						Sprite type = iterator.next();
						spDroplet.onHandleRecycleItem(type);
					}
					lsDroplet[i].clear();
				}
			}
		});
		if (asSwimmer != null) {
			for (int i = 0; i < asSwimmer.length; i++) {
				asSwimmer[i].clearEntityModifiers();
				asSwimmer[i].clearUpdateHandlers();
				asSwimmer[i].setPosition(asSwimmer[i].getmXFirst(), asSwimmer[i].getmYFirst());
				asSwimmer[i].stopAnimation(ImageSwimmer.DEFAULT.status());
				asSwimmer[i].setStatusSwimmer(-1);
				asSwimmer[i].setCurrentAppear(14);
				asSwimmer[i].setCurrentLocation(-1);
			}
		}
		if (sHook != null) {
			for (int j = 0; j < sHook.length; j++) {

				sHook[j].clearEntityModifiers();
				sHook[j].setPosition(sHook[j].getmXFirst(), sHook[j].getmYFirst());
				sHook[j].setVisible(false);
				sHookCostumer[j].clearEntityModifiers();

			}
		}

		if (asBird != null) {
			asBird.clearEntityModifiers();
			asBird.setCurrentTileIndex(0);
			asBird.setPosition(asBird.getmXFirst(), asBird.getmYFirst());
		}
		if (asSmallDuck != null) {
			asSmallDuck.clearEntityModifiers();
			asSmallDuck.setCurrentTileIndex(0);
			asSmallDuck.setPosition(asSmallDuck.getmXFirst(), asSmallDuck.getmYFirst());

		}
		if (sSquirrel != null) {
			sSquirrel.clearEntityModifiers();
			sSquirrel.setPosition(sSquirrel.getmXFirst(), sSquirrel.getmYFirst());
		}

		if (sShower != null) {
			sShower.clearEntityModifiers();
			sShower.setScale(1);
		}
		if (sTowel != null) {
			sTowel.clearEntityModifiers();
			sTowel.setVisible(false);
			sTowel.setPosition(sTowel.getmXFirst(), sTowel.getmYFirst());
		}
		intLocationStart = -1;
		lastX = 0;
		lastY = 0;
		checkX = 0;
		checkY = 0;
		checkTowelX = 0;
		checkTowelY = 0;
		mEngine.clearUpdateHandlers();

	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();

		mScene.detachChildren();
		loadKaraokeScene();
		initial();
		largeDuckSwimmingToLeft();
		engineControlSwimmerAndDroplet();

		// --------------------------------------------------------------
		// 20130326
		// binh xit sua loi
		// -------------------------------------------------------------
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isPause && !isRestricted()) {
					isPause = false;
					// asShampoo.setVisible(true);
					// mScene.registerTouchArea(asShampoo);
				}
			}
		}, 2000);
		// --------------------------------------------------------------
		// 20130326 END
		// binh xit sua loi
		// -------------------------------------------------------------

	}

	public void engineControlSwimmerAndDroplet() {
		Log.d("TEST", "engineControlSwimmerAndDroplet");
		// TODO
		mEngine.registerUpdateHandler(new TimerHandler(0.8f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler arg0) {

				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < asSwimmer.length; i++) {
							addDropletForSwimmer(i);
							if (intLocationStart == -1 && asSwimmer[i].getCurrentAppear() == 14 && asSwimmer[i].getStatusSwimmer() == StatusSwimmer.NOT_APPEAR.status() && (!statusLocalLake[0] || !statusLocalLake[1])) {
								Log.e("$$$$$$$$", "value i = " + i);
								asSwimmer[i].stopAnimation(ImageSwimmer.DEFAULT.status());
								startMemberGoSwimming(i);
								asSwimmer[i].setCurrentAppear(15);
							}
						}
						steamOnLake();
					}
				});
			}
		}));
	}

	public void startMemberGoSwimming(final int index) {
		Log.d("TEST", "startMemberGoSwimming");
		intLocationStart = index;
		imageSwimmer[index] = ImageSwimmer.IN_COSTUMER.status();
		asSwimmer[index].setStatusSwimmer(StatusSwimmer.DRESSING.status());
		asSwimmer[index].setTouchSwimmer(false);
		if (sCostume[index].hasParent()) {
			sCostume[index].detachSelf();
		}
		isTouchCostume[index] = false;
		Log.d("TEST", "index: " + index);
		Log.d("TEST", "intLocationStart: " + intLocationStart);
		asSwimmer[index].attachChild(sCostume[index]);
		sCostume[index].setPosition(48, yCostumeWithBody[index]);

		moveHook(index);
		asSwimmer[index].clearEntityModifiers();
		asSwimmer[index].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				A6_4_KYARA.play();

			}
		}), new MoveXModifier(1.3f, -1 * asSwimmer[index].getWidth(), 73, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				sCostume[index].setVisible(true);
				asSwimmer[index].setVisible(true);
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				Log.d("TEST", "startMemberGoSwimming$$$$$$: " + index);
				isTouchCostume[index] = true;
				asSwimmer[index].setCurrentLocation(0);
			}
		})));
	}

	public void addDropletForSwimmer(final int index) {
		Log.d("TEST", "addDropletForSwimmer");
		Log.d("TEST", "index:  " + index);
		if (asSwimmer[index].getStatusSwimmer() == StatusSwimmer.SWIMMING_NOT_SHAMPOO.status() || asSwimmer[index].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()
				|| asSwimmer[index].getStatusSwimmer() == StatusSwimmer.NOT_DRY.status()) {
			Random rd = new Random();
			final Sprite droplet = spDroplet.obtainPoolItem();
			lsDroplet[index].add(droplet);
			droplet.detachSelf();
			asSwimmer[index].attachChild(droplet);
			// TODO
			int[][] xInTubDischarge = { { 100, 70 }, { 120, 30 }, { 120, 30 }, { 120, 30 }, { 120, 30 } };
			int[][] yInTubDischarge = { { 130, 30 }, { 205, 20 }, { 225, 12 }, { 180, 15 }, { 180, 18 } };
			int[][] xInTubCharge = { { 100, 70 }, { 100, 70 }, { 100, 70 }, { 100, 70 }, { 100, 70 } };
			int[][] yInTubCharge = { { 40, 140 }, { 140, 90 }, { 140, 100 }, { 140, 80 }, { 140, 80 } };
			int[][] xOutTubDischarge = { { 100, 70 }, { 120, 30 }, { 120, 30 }, { 120, 30 }, { 120, 30 } };
			int[][] yOutTubDischarge = { { 130, 140 }, { 140, 160 }, { 215, 25 }, { 215, 25 }, { 215, 25 } };
			int[][] xOutTubcharge = { { 100, 70 }, { 100, 70 }, { 120, 30 }, { 120, 30 }, { 120, 30 } };
			int[][] yOutTubcharge = { { 40, 230 }, { 140, 160 }, { 215, 25 }, { 215, 25 }, { 215, 25 } };
			// default for INTUB_DISCHARGE
			float pX = 0;
			float pY = 0;
			int distance = 0;
			if (imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status()) {
				pX = rd.nextInt(xInTubDischarge[index][1]) + xInTubDischarge[index][0];
				pY = rd.nextInt(yInTubDischarge[index][1]) + yInTubDischarge[index][0];
				if (index == 0) {
					distance = 210;
				} else if (index == 1) {
					distance = 255;
				} else if (index == 2) {
					distance = 260;
				} else if (index == 3) {
					distance = 230;
				} else if (index == 4) {
					distance = 230;
				}
			}
			if (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status()) {
				pX = rd.nextInt(xInTubCharge[index][1]) + xInTubCharge[index][0];
				pY = rd.nextInt(yInTubCharge[index][1]) + yInTubCharge[index][0];
				if (index == 0) {
					distance = 210;
				} else if (index == 1) {
					distance = 250;
				} else if (index == 2) {
					distance = 260;
				} else if (index == 3) {
					distance = 220;
				} else if (index == 4) {
					distance = 220;
				}

			}
			if (imageSwimmer[index] == ImageSwimmer.OUTTUB_DISCHARGE.status()) {
				pX = rd.nextInt(xOutTubDischarge[index][1]) + xOutTubDischarge[index][0];
				pY = rd.nextInt(yOutTubDischarge[index][1]) + yOutTubDischarge[index][0];
				distance = 320;
			}
			if (imageSwimmer[index] == ImageSwimmer.OUTTUB_CHARGE.status()) {
				pX = rd.nextInt(xOutTubcharge[index][1]) + xOutTubcharge[index][0];
				pY = rd.nextInt(yOutTubcharge[index][1]) + yOutTubcharge[index][0];
				distance = 320;
			}
			droplet.setPosition(pX, pY);
			animationForDroplet(droplet, index, rd.nextFloat() * 5 + 0.1f, distance);
		}

	}

	public void animationForDroplet(final Sprite droplet, final int index, final float time, final float distance) {
		Log.d("TEST", "animationForDroplet");
		Log.d("TEST", "index: " + index);
		droplet.clearEntityModifiers();
		droplet.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.6f, 0f, 1f), new MoveYModifier(time, droplet.getY(), distance), new AlphaModifier(0.3f, 1f, 0f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						Log.d("TEST", "animationForDroplet----" + index);
						lsDroplet[index].remove(droplet);
						spDroplet.recyclePoolItem(droplet);
					}
				});
			}
		})));

	}

	public void largeDuckSwimmingToLeft() {
		Log.d("TEST", "largeDuckSwimmingToLeft");
		sLargeDuck.setFlippedHorizontal(false);
		float time = Math.abs(sLargeDuck.getX() - 20) / 80 + 1.8f;

		sLargeDuck.registerEntityModifier(new MoveXModifier(time, sLargeDuck.getX(), 20, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				Log.d("TEST", "onModifierFinished: largeDuckSwimmingToLeft");
				largeDuckSwimmingToRight();
			}

		}));
	}

	public void largeDuckSwimmingToRight() {
		Log.d("TEST", "largeDuckSwimmingToRight");
		sLargeDuck.setFlippedHorizontal(true);
		float time = Math.abs(sLargeDuck.getX() - 500) / 80 + 1.8f;

		sLargeDuck.registerEntityModifier(new MoveXModifier(time, sLargeDuck.getX(), 500, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				Log.d("TEST", "onModifierFinished: largeDuckSwimmingToRight");
				largeDuckSwimmingToLeft();
			}

		}));
	}

	public void draftSwimmerOutLake(final int index, int px, int py) {
		Log.d("TEST", "draftSwimmerOutLake");
		Log.d("TEST", "index: " + index);

		if (!localLake.contains(px, py)) {
			asSwimmer[index].setPosition(73, asSwimmer[index].getmYFirst());
			if (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status()) {
				imageSwimmer[index] = ImageSwimmer.OUTTUB_CHARGE.status();
			}
			if (imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status()) {
				imageSwimmer[index] = ImageSwimmer.OUTTUB_DISCHARGE.status();
			}
			Log.d("TEST", "draftSwimmerOutLake-- " + imageSwimmer[index]);
			asSwimmer[index].stopAnimation(imageSwimmer[index]);
			Log.d("TEST", "draftSwimmerOutLake 2");
			statusLocalLake[asSwimmer[index].getCurrentLocation()] = false;
			intLocationStart = index;
			sTowel.setPosition(-206, 195);
			sTowel.clearEntityModifiers();
			sTowel.setScale(0.8f);
			sTowel.setVisible(true);
			checkTouchTowel = false;
			sTowel.registerEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.8f, sTowel.getX(), 80, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					A6_3_TAWL.play();
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					checkTouchTowel = true;
					asSwimmer[intLocationStart].setTouchSwimmer(false);
				}
			}), new LoopEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.5f, sTowel.getY(), sTowel.getY() - 12), new MoveYModifier(0.5f, sTowel.getY() - 12, sTowel.getY())))));
			asSwimmer[index].setStatusSwimmer(StatusSwimmer.NOT_DRY.status());
			Log.d("TEST", "draftSwimmerOutLake 4");
			if (statusLocalLake[0] == false && statusLocalLake[1] == false) {
				if (asShampoo.isRotated()) {
					asShampoo.clearEntityModifiers();
				}
			}
			asSwimmer[index].clearEntityModifiers();
			asSwimmer[index].registerEntityModifier(new LoopEntityModifier(new DelayModifier(5.0f, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					Log.d("TEST", "draftSwimmerOutLake 5");
					switch (index) {
					case 0:
						A6_4_1_KUMA.play();
						break;
					case 1:
						A6_4_2_KITSUNE.play();
						break;

					case 2:
						A6_4_3_USAGI.play();
						break;

					case 3:
						A6_4_4_BOY.play();
						break;

					case 4:
						A6_4_5_GIRL.play();
						break;

					default:
						break;
					}
					long[] duration = new long[] { 1000, 200 };
					int[] frame = new int[] { 10, 0 };
					asSwimmer[index].animate(duration, frame, 0);

				}
			})));

		} else {
			asSwimmer[index].setPosition(intLake[index][asSwimmer[index].getCurrentLocation()][0], intLake[index][asSwimmer[index].getCurrentLocation()][1]);
			Log.d("TEST:", "asSwimmer[index] " + asSwimmer[index]);
			Log.d("TEST:", "imageSwimmer[index]] " + imageSwimmer[index]);
			asSwimmer[index].stopAnimation(imageSwimmer[index]);
		}
	}

	public void wasDriedSuccessfull() {
		Log.d("TEST", "wasDriedSuccessfull:");
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				asSwimmer[intLocationStart].clearEntityModifiers();

				asSwimmer[intLocationStart].setTouchSwimmer(false);
				asSwimmer[intLocationStart].stopAnimation(ImageSwimmer.DEFAULT.status());
				asSwimmer[intLocationStart].setStatusSwimmer(StatusSwimmer.WAS_DRIED.status());
				asSwimmer[intLocationStart].detachChildren();

				if (sStar.hasParent()) {
					sStar.detachSelf();
				}
				asSwimmer[intLocationStart].attachChild(sStar);
				sStar.setPosition(0, 0);
				sStar.setVisible(true);
				A6_20_FUITAATO.play();
				sStar.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sStar.setVisible(false);
					}
				}));
				for (Iterator<Sprite> iterator = lsDroplet[intLocationStart].iterator(); iterator.hasNext();) {
					Sprite type = iterator.next();
					spDroplet.recyclePoolItem(type);
				}
				lsDroplet[intLocationStart].clear();
				for (int j = 0; j < sHook.length; j++) {
					if (sHook[j].isVisible()) {
						isTouchCostume[j] = true;
						sHook[j].registerEntityModifier(new LoopEntityModifier(rotationHook(j)));
					}
				}

			}
		});

	}

	public void checkDraftSwimerOutLake(int index, int px, int py) {
		Log.d("TEST", "checkDraftSwimerOutLake");
		Log.d("TEST", "index: " + index);
		if (!localLake.contains(px, py)) {
			if (intLocationStart == -1) {
				draftSwimmerOutLake(index, px, py);
			} else {
				if (asSwimmer[intLocationStart].getStatusSwimmer() == StatusSwimmer.DRESSING.status() || asSwimmer[intLocationStart].getStatusSwimmer() == StatusSwimmer.NOT_APPEAR.status()) {
					Log.d("Vol3Ofurosuki ", "intLocationStart: " + intLocationStart);

					endMemberGoNotSwimming(intLocationStart);
					draftSwimmerOutLake(index, px, py);

				} else {
					asSwimmer[index].setPosition(intLake[index][asSwimmer[index].getCurrentLocation()][0], intLake[index][asSwimmer[index].getCurrentLocation()][1]);
					asSwimmer[index].stopAnimation(imageSwimmer[index]);
				}
			}
		} else {
			asSwimmer[index].setPosition(intLake[index][asSwimmer[index].getCurrentLocation()][0], intLake[index][asSwimmer[index].getCurrentLocation()][1]);
			asSwimmer[index].stopAnimation(imageSwimmer[index]);
		}
	}

	public void moveCostumerFromHook(final int index) {
		Log.d("TEST", "moveCostumerFromHook");
		Log.d("TEST", "index: " + index);
		for (int i = 0; i < isTouchCostume.length; i++) {
			if (sCostume[i].isVisible()) {
				isTouchCostume[i] = false;
				asSwimmer[i].setTouchSwimmer(false);
			}
		}

		sCostume[index].registerEntityModifier(new MoveModifier(0.8f, sHook[index].getX(), asSwimmer[intLocationStart].getX() + 48, sHook[index].getY(), yCostumeWithBody[intLocationStart], new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				checkCostumeAvaiable(index);
			}
		}));
	}

	public void checkCostumeAvaiable(final int index) {
		Log.d("TEST", "checkCostumeAvaiable");
		Log.d("TEST", "index:  " + index);
		if (index == intLocationStart) {
			imageSwimmer[index] = ImageSwimmer.RIGHT_COSTUME.status();
			asSwimmer[index].setStatusSwimmer(StatusSwimmer.SWIMMING_DRESSING.status());
			if (index == 0 || index == 1 || index == 3) {
				int rdSound = new Random().nextInt(4);
				switch (rdSound) {
				case 0:
					A6_4A_1.play();
					break;

				case 1:
					A6_4A_2.play();
					break;
				case 2:
					A6_4A_3.play();
					break;
				case 3:
					A6_4A_4.play();
					break;
				default:
					break;
				}
			} else if (index == 2 || index == 4) {
				int rdSound = new Random().nextInt(3);
				switch (rdSound) {
				case 0:
					A6_4B_1.play();
					break;

				case 1:
					A6_4B_2.play();
					break;
				case 2:
					A6_4B_3.play();
					break;

				default:
					break;
				}
			}
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					asSwimmer[index].stopAnimation(imageSwimmer[index]);
					sCostume[index].detachSelf();
					asSwimmer[index].attachChild(sCostume[index]);
					sCostume[index].setPosition(48, yCostumeWithBody[index]);
					sStar.clearEntityModifiers();
					sStar.setVisible(true);
					A6_20_FUITAATO.play();
					endMemberGoSwimming(index);
				}
			});

		} else {
			imageSwimmer[intLocationStart] = ImageSwimmer.WRONG_COSTUME.status();
			asSwimmer[intLocationStart].stopAnimation(imageSwimmer[intLocationStart]);

			moveCostumeReturnHook(index);
		}
	}

	public void endMemberGoSwimming(final int index) {
		Log.d("TEST", "endMemberGoSwimming");
		intLocationStart = -1;
		isTouchCostume[index] = false;
		changeHookLocation(index);
		Log.e("TEST", "statusLocalLake " + statusLocalLake);
		asSwimmer[index].clearEntityModifiers();
		asSwimmer[index].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.8f), new MoveXModifier(1.0f, asSwimmer[index].getX(), -1 * asSwimmer[index].getWidth(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				for (int i = 0; i < asSwimmer.length; i++) {
					if (sCostume[i].isVisible()) {
						asSwimmer[i].setTouchSwimmer(true);
					}
				}
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						Log.d("TEST", "endMemberGoSwimming---");
						sCostume[index].setVisible(false);
						asSwimmer[index].detachChildren();
						imageSwimmer[index] = ImageSwimmer.DEFAULT.status();
						for (int i = 0; i < asSwimmer.length; i++) {

							if (asSwimmer[i].getStatusSwimmer() == -1) {
								lsRandomSwimmer.add(i);
								Log.d("nhung con chua xuat hien ", "chua xuat hien i= " + i);
								asSwimmer[i].setCurrentAppear(15);

							}
						}

						Collections.shuffle(lsRandomSwimmer);
						Log.d("kich thuoc lsRandomSwimmer ", " lsRandomSwimmer.size()= " + lsRandomSwimmer.size());
						for (int j = 0; j < lsRandomSwimmer.size() - 1; j++) {
							asSwimmer[lsRandomSwimmer.get(j)].setCurrentAppear(14);
						}
						lsRandomSwimmer.clear();
						asSwimmer[index].setCurrentLocation(-1);

						asSwimmer[index].setStatusSwimmer(StatusSwimmer.NOT_APPEAR.status());
					}
				});
			}
		})));
	}

	public void endMemberGoNotSwimming(final int index) {
		Log.d("TEST", "endMemberGoSwimming");
		intLocationStart = -1;
		isTouchCostume[index] = false;
		changeHookLocation(index);
		Log.d("TEST", "endMemberGoSwimming " + index);
		asSwimmer[index].clearEntityModifiers();
		asSwimmer[index].registerEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.6f, asSwimmer[index].getX(), -1 * asSwimmer[index].getWidth(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						Log.d("TEST", "endMemberGoSwimming---");
						sCostume[index].setVisible(false);
						asSwimmer[index].detachChildren();
						imageSwimmer[index] = ImageSwimmer.DEFAULT.status();
						asSwimmer[index].setCurrentLocation(-1);

						asSwimmer[index].setStatusSwimmer(StatusSwimmer.NOT_APPEAR.status());
					}
				});
			}
		})));
	}

	public void changeHookLocation(int index) {
		Log.d("TEST", "changeHookLocation");
		Log.d("TEST", " index: " + index);
		for (int i = 0; i < sHook.length; i++) {
			if (sHook[i].isVisible()) {
				sHook[i].clearEntityModifiers();
				sHook[i].setRotation(0);
			}
		}
		for (int i = 0; i < intXHook.length; i++) {
			if (sHook[index].getX() == intXHook[i]) {
				boolXHook[i] = false;
				break;
			}
		}
		sHook[index].setVisible(false);
		sHook[index].setPosition(sHook[index].getmXFirst(), sHook[index].getmYFirst());
		if (!boolXHook[0] && boolXHook[1]) {
			for (int i = 0; i < sHook.length; i++) {
				if (sHook[i].isVisible()) {
					boolXHook[0] = true;
					boolXHook[1] = false;
					sHook[i].registerEntityModifier(new MoveXModifier(1f, sHook[i].getX(), intXHook[0]));
					break;
				}
			}
		}
	}

	public void steamOnLake() {
		final AnimatedSprite steam = sasSteam.obtainPoolItem();
		Random rd = new Random();
		steam.stopAnimation(rd.nextInt(4));
		mScene.attachChild(steam);
		lsSteam.add(steam);
		steam.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new MoveYModifier(1.8f, steam.getmYFirst(), steam.getmYFirst() - (rd.nextInt(4) * 30 + 80)), new AlphaModifier(2.0f, 1f, 0.0f)), new DelayModifier(3f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								sasSteam.recyclePoolItem(steam);
								lsSteam.remove(steam);
							}
						});
					}
				})));
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		// --------------------------------------------------------------
		// 20130326
		// binh xit sua loi
		// -------------------------------------------------------------
		if (isPause) {
			return isPause;
		}
		// --------------------------------------------------------------
		// 20130326 END
		// binh xit sua loi
		// -------------------------------------------------------------

		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		/***************** DOWN *********************/
		if (pSceneTouchEvent.isActionDown()) {
			lastX = pX;
			lastY = pY;
			if (asSmallDuck.contains(pX, pY) && isTouchSmallDuck) {
				isTouchSmallDuck = false;
				onTouchSmallDuck();
				// return true;
			}
			if (sSquirrel.contains(pX, pY) && isTouchSquirrel) {
				isTouchSquirrel = false;
				onTouchSquirrel();
				// return true;
			}
			if (asBird.contains(pX, pY) && isTouchBird) {
				isTouchBird = false;
				onTouchBird();
				// return true;
			}
			if (sLargeDuck.contains(pX, pY) && isTouchLargeDuck) {
				isTouchLargeDuck = false;
				onTouchLargeDuck();
				// return true;
			}
			if (sShower.contains(pX, pY)) {
				onTouchShower();
			}
			if (localLake.contains(pX, pY)) {
				final AnimatedSprite water = asWater.obtainPoolItem();
				water.setPosition(pX - water.getWidth() / 2, 394);
				mScene.attachChild(water);
				A6_4_FURODERU_KARUI.play();
				water.animate(300, false, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						water.setVisible(false);
						lsWater.remove(water);
					}
				});
				lsWater.add(water);
			}
		}
		/***************** MOVE *********************/
		if (pSceneTouchEvent.isActionMove() && Math.abs((lastX + lastY) - (pX + pY)) >= 20) {
		}
		return true;
	}

	public void clearBubble(ArrayList<AnimatedSprite> bubble) {
		Log.d("TEST", "clearBubble");
		final int max = bubble.size() - 1;
		for (int i = max; i > max - new Random().nextInt(2) - 5; i--) {
			if (i < 0) {
				bubble.clear();
				break;
			}
			final AnimatedSprite tmp = bubble.get(i);
			tmp.registerEntityModifier(new ParallelEntityModifier(new MoveYModifier(1.6f, tmp.getY(), 420), new AlphaModifier(1.6f, 1f, 0f, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> paramIModifier, IEntity paramT) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> paramIModifier, IEntity paramT) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							tmp.setAlpha(1f);
							tmp.detachSelf();
							spBubble.recyclePoolItem(tmp);
						}
					});
				}
			})));
			bubble.remove(bubble.get(i));
		}
	}

	public void onTouchShower() {
		Log.d("TEST", "onTouchShower");
		sShower.clearEntityModifiers();
		sShower.setScale(1.0f);
		sShowerWater.setVisible(true);
		touchBubble = false;
		for (int i = 0; i < asSwimmer.length; i++) {
			if (imageSwimmer[i] == ImageSwimmer.INTUB_DISCHARGE.status() || imageSwimmer[i] == ImageSwimmer.INTUB_CHARGE.status()) {
				asSwimmer[i].stopAnimation(9);
				imageSwimmer[i] = ImageSwimmer.INTUB_CHARGE.status();
			}
		}
		A6_11_2_S1.play();
		sShowerWater.clearEntityModifiers();
		sShowerWater.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.8f, 0.4f, 1.0f), new ScaleModifier(0.6f, 0.9f, 0.95f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> paramIModifier, IEntity paramT) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> paramIModifier, IEntity paramT) {
				sShowerWater.setVisible(false);
				touchBubble = true;
				for (int i = 0; i < asSwimmer.length; i++) {
					if (imageSwimmer[i] == ImageSwimmer.INTUB_CHARGE.status()) {
						asSwimmer[i].stopAnimation(6);
						if ((statusLocalLake[0] == true && asSwimmer[i].getCurrentTileIndex() == 6) || (statusLocalLake[1] == true && asSwimmer[i].getCurrentTileIndex() == 6)) {
							asSwimmer[i].setTouchSwimmer(true);
						}
					}
				}

				if (lsBubbleLeft.size() > 0 || lsBubbleRight.size() > 0) {
					sShower.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.5f, 1.0f, 1.3f), new ScaleModifier(1.5f, 1.30f, 1.0f))));
				}

			}
		})));
		if (lsBubbleLeft.size() > 0) {
			clearBubble(lsBubbleLeft);
		}
		if (lsBubbleRight.size() > 0) {
			clearBubble(lsBubbleRight);
		}
	}

	public void swimmerIsDiving(final int index) {
		Log.d("TEST", "swimmerIsDiving");
		Log.d("TEST", "index:  " + index);
		long[] duration = new long[] { 400, 400, 800, 400, 400 };
		int[] frame = null;
		if (imageSwimmer[index] == ImageSwimmer.INTUB_DISCHARGE.status()) {
			frame = new int[] { 4, 7, 11, 8, 6 };
		}
		if (imageSwimmer[index] == ImageSwimmer.INTUB_CHARGE.status()) {
			frame = new int[] { 6, 8, 11, 8, 6 };
		}
		if (index == 0 || index == 3 || index == 4) {
			A6_4_FUROSHIZIUMU_OMOI.play();
		}
		if (index == 1 || index == 2) {
			A6_4_FUROSHIZIUMU_KARUI.play();
		}

		asSwimmer[index].setTouchSwimmer(false);
		asSwimmer[index].animate(duration, frame, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				asSwimmer[index].setTouchSwimmer(true);
				imageSwimmer[index] = ImageSwimmer.INTUB_CHARGE.status();
			}
		});
		asSwimmer[index].registerEntityModifier(new DelayModifier(0.6f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						asSwimmer[index].detachChildren();
						asSwimmer[index].setStatusSwimmer(StatusSwimmer.SWIMMING_NOT_SHAMPOO.status());
						for (Iterator<Sprite> iterator = lsDroplet[index].iterator(); iterator.hasNext();) {
							Sprite type = iterator.next();
							spDroplet.recyclePoolItem(type);
						}
						lsDroplet[index].clear();

						if (asSwimmer[index].getCurrentLocation() == 0) {
							for (Iterator<AnimatedSprite> iterator = lsBubbleLeft.iterator(); iterator.hasNext();) {
								AnimatedSprite type = iterator.next();
								spBubble.onHandleRecycleItem(type);
							}
							lsBubbleLeft.clear();
						}
						if (asSwimmer[index].getCurrentLocation() == 1) {
							for (Iterator<AnimatedSprite> iterator = lsBubbleRight.iterator(); iterator.hasNext();) {
								AnimatedSprite type = iterator.next();
								spBubble.onHandleRecycleItem(type);
							}
							lsBubbleRight.clear();
						}
						if (lsBubbleRight.size() == 0 && lsBubbleLeft.size() == 0) {
							sShower.clearEntityModifiers();
							sShower.setScale(1.0f);
						}
					}
				});

			}
		}));

	}

	public void onTouchLargeDuck() {
		Log.d("TEST", "onTouchLargeDuck");
		sLargeDuck.clearEntityModifiers();
		A6_15_FUNE.play();
		sLargeDuck.setScale(1.3f);
		sLargeDuck.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sLargeDuck.setScale(1f);
				if (!sLargeDuck.isFlippedHorizontal()) {
					largeDuckSwimmingToLeft();
				} else {
					largeDuckSwimmingToRight();
				}
				isTouchLargeDuck = true;
			}
		}));
	}

	public void moveCostumeReturnHook(final int index) {
		Log.d("TEST", "moveCostumerReturnHook");
		Log.d("TEST", "index: " + index);
		// chon sai quan ao
		switch (index) {
		case 0:
			A6_4_1_2.play();
			break;
		case 1:
			A6_4_2_2.play();
			break;
		case 2:
			A6_4_3_2.play();
			break;
		case 3:
			A6_4_4_2.play();
			break;
		case 4:
			A6_4_5_2.play();
			break;

		default:
			break;
		}
		sCostume[index].registerUpdateHandler(new TimerHandler(1.2f, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler arg0) {

				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						sCostume[index].detachSelf();
						sHook[index].attachChild(sCostume[index]);
						sCostume[index].setPosition(0, 0);
						asSwimmer[intLocationStart].stopAnimation(ImageSwimmer.DEFAULT.status());
						for (int i = 0; i < isTouchCostume.length; i++) {
							if (sCostume[i].isVisible()) {
								isTouchCostume[i] = true;
								asSwimmer[i].setTouchSwimmer(true);
							}
						}

					}
				});
			}
		}));

	}

	public void moveCostumerToHook(final int index, int pX, int pY) {
		Log.d("TEST", "moveCostumerToHook");
		Log.d("TEST", "index: " + index);
		isTouchCostume[index] = false;
		A6_12_FUKUIDOU.play();
		float durationCostume = Math.abs(((pX - sHook[index].getX()) * 0.6f / ((asSwimmer[intLocationStart].getX() + 48) - sHook[index].getX())));
		// Log.e("TEST ", "duration: " + durationCostume);
		sCostume[index].registerEntityModifier(new MoveModifier(durationCostume, sCostume[index].getX(), sHook[index].getX(), sCostume[index].getY(), sHook[index].getY(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				moveSwimmerToLake(index);
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						A6_12_HANGA_KAKARU.play();
						sCostume[index].detachSelf();
						sHook[index].attachChild(sCostume[index]);
						sCostume[index].setPosition(0, 0);
						asSwimmer[index].setTouchSwimmer(true);
						sHook[index].registerEntityModifier(rotationHook(index));
					}
				});
			}
		}));
	}

	public IEntityModifier rotationHook(int index) {
		Log.d("TEST", "rotationHook");
		Log.d("TEST", "index: " + index);

		return new SequenceEntityModifier(new RotationAtModifier(0.25f, 0, -15, sHook[index].getWidth() / 2, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				A6_12_FUKUYURERU.play();

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {

			}
		}), new RotationAtModifier(0.5f, -15, 15, sHook[index].getWidth() / 2, 0), new RotationAtModifier(0.25f, 15, 0, sHook[index].getWidth() / 2, 0));

	}

	public void moveHook(final int index) {
		Log.d("TEST", "moveHook");
		Log.d("TEST", "index: " + index);
		sHook[index].setVisible(true);
		for (int i = 0; i < boolXHook.length; i++) {
			if (!boolXHook[i]) {
				final int j = i;
				sHook[index].registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.8f), new MoveXModifier(0.8f, sHook[index].getX(), intXHook[i], new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						boolXHook[j] = true;
						sHook[index].registerEntityModifier(rotationHook(index));
					}
				})));
				break;
			}
		}
	}

	public void moveSwimmerToLake(int index) {
		Log.d("TEST", "moveSwimmerToLake");
		Log.d("TEST", "index :" + index);
		for (int i = statusLocalLake.length - 1; i >= 0; i--) {
			if (!statusLocalLake[i]) {
				if (index == 0 || index == 3 || index == 4) {
					A6_4_FUROHAIRU_OMOI.play();
				}
				if (index == 1 || index == 2) {
					A6_4_FUROHAIRU_KARUI.play();
				}
				asSwimmer[index].setPosition(intLake[index][i][0], intLake[index][i][1]);
				asSwimmer[index].setCurrentLocation(i);
				asSwimmer[index].setStatusSwimmer(StatusSwimmer.SWIMMING_NOT_SHAMPOO.status());
				intLocationStart = -1;
				imageSwimmer[index] = ImageSwimmer.INTUB_DISCHARGE.status();
				asSwimmer[index].stopAnimation(imageSwimmer[index]);
				asSwimmer[index].setTouchSwimmer(true);
				statusLocalLake[i] = true;
				final AnimatedSprite water = asWater.obtainPoolItem();
				float x = asSwimmer[index].getX() + (asSwimmer[index].getWidth() - water.getWidth()) / 2;
				water.setPosition(x, 394);
				mScene.attachChild(water);
				water.animate(300, false, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						water.setVisible(false);
						lsWater.remove(water);
					}
				});
				lsWater.add(water);
				asShampoo.registerEntityModifier(rotationShampoo());
				break;
			}
		}
	}

	public IEntityModifier rotationShampoo() {
		return new LoopEntityModifier(new SequenceEntityModifier(new RotationAtModifier(0.5f, 0, -7, asShampoo.getWidth() / 2, asShampoo.getHeight()), new RotationAtModifier(1f, -7, 7, asShampoo.getWidth() / 2, asShampoo.getHeight()),
				new RotationAtModifier(0.5f, 7, 0, asShampoo.getWidth() / 2, asShampoo.getHeight(), new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								if (!statusLocalLake[0] && !statusLocalLake[1]) {
									asShampoo.clearEntityModifiers();
								}
							}
						});

					}
				})));

	}

	public void onTouchBird() {
		// --------------------------------------------------------------
		// 20130326
		// binh xit sua loi
		// -------------------------------------------------------------
		if (isPause) {
			return;
		}
		Log.d("TEST_TEST", "onTouchBird");
		long time[] = new long[] { 300, 300 };
		int frame[] = new int[] { 1, 2 };
		A6_6_BASA.play();
		asBird.animate(time, frame, -1);

		asBird.registerEntityModifier(new MoveModifier(2.5f, asBird.getX(), 50, asBird.getY(), -asBird.getHeight(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				asBird.stopAnimation(0);
				asBird.setPosition(asBird.getmXFirst(), asBird.getmYFirst());
				isTouchBird = true;
			}
		}));

	}

	public void onTouchSquirrel() {
		Log.d("TEST", "onTouchSquirrel");
		A6_7_RISU.play();
		sSquirrel.registerEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.6f, sSquirrel.getY(), sSquirrel.getY() - 80), new MoveYModifier(0.5f, sSquirrel.getY() - 80, sSquirrel.getY(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> paramIModifier, IEntity paramT) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> paramIModifier, IEntity paramT) {
				isTouchSquirrel = true;
			}
		})));
	}

	public void onTouchSmallDuck() {
		Log.d("TEST", "onTouchSmallDuck");
		A6_2_AHIRU.play();
		asSmallDuck.animate(300, false, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				asSmallDuck.stopAnimation(0);
				isTouchSmallDuck = true;
			}
		});

	}

	public enum ImageSwimmer {

		DEFAULT(0), // 4_■_1
		WRONG_COSTUME(1), // 4_■_2
		RIGHT_COSTUME(2), // 4_■_3
		OUTTUB_DISCHARGE(3), // 4_■_4
		INTUB_DISCHARGE(4), // 4_■_5
		OUTTUB_CHARGE(5), // 4_■_6
		INTUB_CHARGE(6), // 4_■_7
		TOUCH_INTUB_DISCHARGE(7), // 4_■_8
		TOUCH_INTUB_CHARGE(8), // 4_■_9
		CHARGING(9), // 4_■_10
		SNEEZING(10), // 4_■_11
		IN_COSTUMER(11); // 4_■_12

		private final int status;

		ImageSwimmer(int aStatus) {
			this.status = aStatus;
		}

		public int status() {
			return this.status;
		}
	}

	public enum StatusSwimmer {
		NOT_APPEAR(-1), // not appear
		DRESSING(0), // not swimming and is dressing
		SWIMMING_NOT_SHAMPOO(1), // is swimming and not shampoo
		SWIMMING_WITH_SHAMPOO(2), // is swimming with shampoo
		NOT_DRY(3), // is not dried
		WAS_DRIED(4), // dried
		SWIMMING_DRESSING(5); // swimmed and is dressing

		private final int status;

		StatusSwimmer(int aStatus) {
			this.status = aStatus;
		}

		public int status() {
			return this.status;
		}
	}

	/**
	 * @author kiemhm
	 * 
	 */
	class SwimmerAnimatedSprite extends AnimatedSprite {

		private boolean isTouchSwimmer = false;
		private int statusSwimmer = StatusSwimmer.NOT_APPEAR.status();
		private int currentLocation = -1;
		private boolean isMoving = false;
		private int currentAppear = 14;

		public SwimmerAnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
			super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
		}

		public boolean isTouchSwimmer() {
			return isTouchSwimmer;
		}

		public void setTouchSwimmer(boolean isTouchSwimmer) {
			this.isTouchSwimmer = isTouchSwimmer;
		}

		public int getStatusSwimmer() {
			return statusSwimmer;
		}

		public void setStatusSwimmer(int statusSwimmer) {
			this.statusSwimmer = statusSwimmer;
		}

		public int getCurrentLocation() {
			return currentLocation;
		}

		public void setCurrentLocation(int currentLocation) {
			this.currentLocation = currentLocation;
		}

		public boolean isMoving() {
			return isMoving;
		}

		public void setMoving(boolean isMoving) {
			this.isMoving = isMoving;
		}

		/**
		 * @param currentAppear
		 *            the currentAppear to set
		 */
		public void setCurrentAppear(int currentAppear) {
			this.currentAppear = currentAppear;
		}

		/**
		 * @return the currentAppear
		 */
		public int getCurrentAppear() {
			return currentAppear;
		}

	}

	class AnimatedSpritePool extends GenericPool<AnimatedSprite> {

		private ITiledTextureRegion mTiledTextureRegion;

		public AnimatedSpritePool(ITiledTextureRegion pTextureRegion) {
			if (pTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have
				// a TextureRegion
				throw new IllegalArgumentException("The texture region must not be NULL");
			}
			mTiledTextureRegion = pTextureRegion;
		}

		@Override
		protected AnimatedSprite onAllocatePoolItem() {
			return new AnimatedSprite(246, 293, mTiledTextureRegion, Vol3Ofurosuki.this.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pX, float pY) {

					// TODO Auto-generated method stub
					if (!touchBubble) {
						return true;
					}
					switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_MOVE:
						if (lsBubbleLeft.size() > 0 || lsBubbleRight.size() > 0) {
							if (!checkMove) {
								checkMove = true;
								Log.e("%%%%%%%%%%%%%%%%", "tap vao bot xa phong");
								A6_8_AWA_SYAKA.play();
								this.setScale(1.5f);
								mScene.clearUpdateHandlers();
								mScene.registerUpdateHandler(new TimerHandler(0.7f, new ITimerCallback() {
									@Override
									public void onTimePassed(TimerHandler arg0) {
										checkMove = false;
									}
								}));
							}
						}
						break;
					}

					return true;
				}
			};
		}

		@Override
		protected void onHandleRecycleItem(final AnimatedSprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.detachSelf();
		}

		@Override
		protected void onHandleObtainItem(final AnimatedSprite pItem) {
			pItem.reset();
		}

	}

	class SpritePool extends GenericPool<Sprite> {

		private ITextureRegion pTextureRegion;

		public SpritePool(ITextureRegion pTextureRegion) {
			if (pTextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have
				// a TextureRegion
				throw new IllegalArgumentException("The texture region must not be NULL");
			}
			this.pTextureRegion = pTextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			return new Sprite(0, 0, pTextureRegion, Vol3Ofurosuki.this.getVertexBufferObjectManager());
		}

		@Override
		protected void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.detachSelf();
		}

		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
			pItem.reset();
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
		if (arg0.equals(asShampoo)) {
			final AnimatedSprite bubble = spBubble.obtainPoolItem();
			// Random rd = new Random();
			// bubble.stopAnimation(rd.nextInt(4));
			bubble.setPosition(asShampoo.getX() - bubble.getWidth() - 20, asShampoo.getY() + 20);
			A6_8_PONPU_AWA.play();
			bubble.setZIndex(220);
			mScene.attachChild(bubble);
			mScene.sortChildren();
			mScene.registerTouchArea(bubble);
			Log.d("TEST", statusLocalLake[0] + "/" + statusLocalLake[1]);
			if (bubble.collidesWith(leftLake) && statusLocalLake[0]) {
				lsBubbleLeft.add(bubble);
				for (int i = 0; i < asSwimmer.length; i++) {
					if (asSwimmer[i].getCurrentLocation() == 0) {
						asSwimmer[i].setStatusSwimmer(StatusSwimmer.SWIMMING_WITH_SHAMPOO.status());
						break;
					}

				}
				if (lsBubbleLeft.size() % 5 == 0) {
					int rdSoundLeft = new Random().nextInt(2);
					if (asSwimmer[0].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status() || asSwimmer[1].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()
							|| asSwimmer[3].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()) {
						switch (rdSoundLeft) {
						case 0:
							A6_8_AWA1.play();
							break;
						case 1:
							A6_8_AWA3.play();
							break;
						default:
							break;

						}
					} else if (asSwimmer[2].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status() || asSwimmer[4].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()) {
						switch (rdSoundLeft) {
						case 0:
							A6_8_AWA2.play();
							break;
						case 1:
							A6_8_AWA4.play();
							break;
						default:
							break;

						}
					}

				}
			} else if (bubble.collidesWith(rightLake) && statusLocalLake[1]) {
				lsBubbleRight.add(bubble);
				for (int i = 0; i < asSwimmer.length; i++) {
					if (asSwimmer[i].getCurrentLocation() == 1) {
						asSwimmer[i].setStatusSwimmer(StatusSwimmer.SWIMMING_WITH_SHAMPOO.status());
						break;
					}

				}
				if (lsBubbleRight.size() % 5 == 0) {

					int rdSoundRight = new Random().nextInt(2);
					if (asSwimmer[0].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status() || asSwimmer[1].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()
							|| asSwimmer[3].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()) {
						switch (rdSoundRight) {
						case 0:
							A6_8_AWA1.play();
							break;
						case 1:
							A6_8_AWA3.play();
							break;
						default:
							break;

						}
					} else if (asSwimmer[2].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status() || asSwimmer[4].getStatusSwimmer() == StatusSwimmer.SWIMMING_WITH_SHAMPOO.status()) {
						switch (rdSoundRight) {
						case 0:
							A6_8_AWA2.play();
							break;
						case 1:
							A6_8_AWA4.play();
							break;
						default:
							break;

						}
					}

				}
			} else {
				bubble.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.2f), new AlphaModifier(0.4f, 1f, 0f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								spBubble.recyclePoolItem(bubble);
							}
						});
					}
				})));
			}

		}

	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {

	}

	private void stopSound() {
		A6_20_FUITAATO.stop();
		A6_2_AHIRU.stop();
		A6_3_KARADAFUKU.stop();
		A6_3_TAWL.stop();
		A6_4_1_2.stop();
		A6_4_1_KUMA.stop();
		A6_4_2_2.stop();
		A6_4_2_KITSUNE.stop();
		A6_4_3_2.stop();
		A6_4_3_USAGI.stop();
		A6_4_4_2.stop();
		A6_4_4_BOY.stop();
		A6_4_5_2.stop();
		A6_4_5_GIRL.stop();
		A6_4_FURODERU_KARUI.stop();
		A6_4_FURODERU_OMOI.stop();
		A6_4_FUROHAIRU_KARUI.stop();
		A6_4_FUROHAIRU_OMOI.stop();
		A6_4_FUROSHIZIUMU_KARUI.stop();
		A6_4_FUROSHIZIUMU_OMOI.stop();
		A6_4_KYARA.stop();
		A6_4_SUITEKI.stop();
		A6_4A_1.stop();
		A6_4A_2.stop();
		A6_4A_3.stop();
		A6_4A_4.stop();
		A6_4B_1.stop();
		A6_4B_2.stop();
		A6_4B_3.stop();
		A6_6_BASA.stop();
		A6_7_RISU.stop();
		A6_8_AWA_SYAKA.stop();
		A6_8_PONPU_AWA.stop();
		A6_10_PONPU_MODORU.stop();
		A6_11_2_S1.stop();
		A6_11_2_S2.stop();
		A6_12_FUKUIDOU.stop();
		A6_12_FUKUYURERU.stop();
		A6_12_HANGA_KAKARU.stop();
		A6_15_FUNE.stop();
		A6_8_AWA4.stop();
		A6_8_AWA1.stop();
		A6_8_AWA2.stop();
		A6_8_AWA3.stop();
	}

	// --------------------------------------------------------------
	// 20130326
	// binh xit sua loi
	// -------------------------------------------------------------
	private boolean isPause = false;
	private Handler handler = new Handler();

	@Override
	public void onPause() {
		isPause = true;
		super.onPause();
	}

}