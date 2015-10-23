package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YukirinrinResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
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
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Yukirinrin extends BaseGameActivity implements
		IOnSceneTouchListener {

	private TexturePack packBackground = null, packOther = null;
	private TexturePackTextureRegionLibrary packLibBackground = null,
			packLibOther = null;

	private ITextureRegion txtTable = null, txtBarrierLeft = null,
			txtBarrierRight = null, txtNefuda = null, txtRegi = null,
			txtInStack = null, txtAngel = null, txtLeftSide1 = null,
			txtLeftSide2 = null, txtLeftSide3 = null, txtLeftSide4 = null,
			txtOrderTable = null, txtRecept = null, txtSuccessText = null,
			txtSuccessLeft1 = null, txtSuccessLeft2 = null,
			txtSuccessRight1 = null, txtSuccessRight2 = null;
	private Sprite sTable = null, sBarrierLeft = null, sBarrierRight = null,
			sNefuda = null, sRegi = null, sInStack = null, sAngel = null,
			sLeftSide1 = null, sLeftSide2 = null, sLeftSide3 = null,
			sLeftSide4 = null, sOrderTable = null, sRecept = null,
			sSuccessText = null, sSuccessLeft1 = null, sSuccessLeft2 = null,
			sSuccessRight1 = null, sSuccessRight2 = null;

	private TiledTextureRegion ttxStack = null, ttxKuma = null,
			ttxMoneyA = null, ttxMoneyB = null, ttxTrayOrder = null;
	private AnimatedSprite asStack = null, asKuma = null, asMoneyA = null,
			asMoneyB = null, asTrayOrder = null;

	private ITextureRegion[] txtSuccess = new ITextureRegion[5];
	private Sprite[] sSuccess = new Sprite[5];
	private int[] intSuccessId = {
			Vol3YukirinrinResource.yukirinrin_usagi.A2_13_1_1_IPHONE_SUCCESS_ID,
			Vol3YukirinrinResource.yukirinrin_okami.A2_13_1_3_IPHONE_SUCCESS_ID,
			Vol3YukirinrinResource.yukirinrin_inu.A2_13_1_2_IPHONE_SUCCESS_ID,
			Vol3YukirinrinResource.yukirinrin_neko.A2_13_1_4_IPHONE_SUCCESS_ID,
			Vol3YukirinrinResource.yukirinrin_background_success.A2_9_IPHONE_SUCCESS_ID, };

	private int[][] intSuccessCoor = { { -36, 304 }, { 671, 472 }, { 23, 452 },
			{ 736, 311 }, { 51, 0 }, };

	private Entity[] mColumEntity = new Entity[6];
	private SpriteNumber[][] mColumSprite = new SpriteNumber[6][10];
	private ITextureRegion[][] mColumRegion = new ITextureRegion[6][10];
	private int[] intNumberId = {
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_0_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_1_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_2_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_3_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_4_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_5_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_6_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_7_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_8_IPHONE_NO_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_2_1_9_IPHONE_NO_ID };
	private boolean[] isColumnMove = { false, false, false, false, false, false };
	private Rectangle mNumberBox = null;

	int touchRegiRandrom = 0;
	int qualityCakeRandrom = 0;
	int countCakeMoveOK = 0;
	int[] typeCakeRandom = { 0, 0, 0, 0, 0, 0 };
	int[] typeCakeChoice = { 0, 0, 0, 0, 0, 0 };
	int[][] cakeAppearCoorX = { { 90, 0, 0 }, { 40, 130, 0 }, { 90, 40, 130 }, };
	int[][] cakeAppearCoorY = { { 35, 0, 0 }, { 35, 35, 0 }, { 20, 70, 70 }, };
	int[][] cakeMoveCoorX = { { 115, 0, 0 }, { 65, 150, 0 }, { 110, 65, 150 }, };
	int[][] cakeMoveCoorY = { { 45, 0, 0 }, { 45, 45, 0 }, { 20, 70, 70 }, };
	private ArrayList<Integer> cakeAppear = new ArrayList<Integer>();
	private Cake[] cakeManager = new Cake[6];
	private ITextureRegion[] ttxCake = new ITextureRegion[6];
	private int[] intCakeId = {
			Vol3YukirinrinResource.yukirinrin_other.A2_5_1_IPHONE_PAN_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_5_2_IPHONE_PAN_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_5_3_IPHONE_PAN_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_5_4_IPHONE_PAN_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_5_5_IPHONE_PAN_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_5_6_IPHONE_PAN_ID, };

	private ITextureRegion[] ttxCakeBought = new ITextureRegion[6];
	private Sprite[] sCakeBought = new Sprite[6];
	private int[] intCakeBoughtId = {
			Vol3YukirinrinResource.yukirinrin_other.A2_7_1_IPHONE__UKIDASHI_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_7_2_IPHONE__UKIDASHI_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_7_3_IPHONE__UKIDASHI_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_7_4_IPHONE__UKIDASHI_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_7_5_IPHONE__KIDASHI_ID,
			Vol3YukirinrinResource.yukirinrin_other.A2_7_6_IPHONE__UKIDASHI_ID, };

	private TiledTextureRegion[] ttxBuyer = new TiledTextureRegion[10];
	private AnimatedSprite[] asBuyer = new AnimatedSprite[10];
	private String[] pathPacker = { "yukirinrin_usagi.xml",
			"yukirinrin_okami.xml", "yukirinrin_inu.xml",
			"yukirinrin_neko.xml", "yukirinrin_boya.xml",
			"yukirinrin_boyb.xml", "yukirinrin_boyc.xml",
			"yukirinrin_girla.xml", "yukirinrin_girlb.xml",
			"yukirinrin_girlc.xml", };
	private TexturePack[] packBuyer = new TexturePack[10];
	private TexturePackTextureRegionLibrary[] packLibBuyer = new TexturePackTextureRegionLibrary[10];
	private ArrayList<Integer> buyerOrder = new ArrayList<Integer>();
	private int buyerNumber = 0;
	private int[][] buyerId = {
			{
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_1_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_2_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_3_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_4_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_5_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_6_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_7_IPHONE_USAGI_ID,
					Vol3YukirinrinResource.yukirinrin_usagi.A2_11_1_8_IPHONE_USAGI_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_1_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_2_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_3_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_4_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_5_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_6_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_7_IPHONE_OKAMI_ID,
					Vol3YukirinrinResource.yukirinrin_okami.A2_11_2_8_IPHONE_OKAMI_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_1_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_2_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_3_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_4_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_5_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_6_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_7_IPHONE_INU_ID,
					Vol3YukirinrinResource.yukirinrin_inu.A2_11_3_8_IPHONE_INU_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_1_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_2_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_3_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_4_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_5_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_6_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_7_IPHONE_NEKO_ID,
					Vol3YukirinrinResource.yukirinrin_neko.A2_11_4_8_IPHONE_NEKO_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_1_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_2_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_3_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_4_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_5_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_6_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_7_IPHONE_BOYA_ID,
					Vol3YukirinrinResource.yukirinrin_boya.A2_11_5_8_IPHONE_BOYA_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_1_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_2_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_3_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_4_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_5_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_6_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_7_IPHONE_BOYB_ID,
					Vol3YukirinrinResource.yukirinrin_boyb.A2_11_6_8_IPHONE_BOYB_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_1_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_2_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_3_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_4_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_5_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_6_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_7_IPHONE_BOYC_ID,
					Vol3YukirinrinResource.yukirinrin_boyc.A2_11_7_8_IPHONE_BOYC_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_1_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_2_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_3_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_4_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_5_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_6_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_7_IPHONE_GIRLA_ID,
					Vol3YukirinrinResource.yukirinrin_girla.A2_11_8_8_IPHONE_GIRLA_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_1_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_2_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_3_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_4_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_5_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_6_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_7_IPHONE_GIRLB_ID,
					Vol3YukirinrinResource.yukirinrin_girlb.A2_11_9_8_IPHONE_GIRLB_ID, },
			{
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_1_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_2_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_3_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_4_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_5_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_6_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_7_IPHONE_GIRLC_ID,
					Vol3YukirinrinResource.yukirinrin_girlc.A2_11_10_8_IPHONE_GIRLC_ID, } };
	private boolean isTouchRegi = true, isTouchKuma = true, isTouchBag = false,
			isActionRegi = false, isYukirinrinRunning = false;

	private TimerHandler randomTimer = null;

	private Sound a2_11_doubutsuaruku = null, a2_kawaipan = null,
			a2_ookami = null, a2_11_hitoaruku = null,
			a2_korekudasai_boy = null, a2_pan_tap = null, a2_arigatou = null,
			a2_korekudasai_girl = null, a2_pinpon = null, a2_inu = null,
			a2_kuraka = null, a2_rezi = null, a2_irasyai = null,
			a2_neko = null, a2_syu = null, a2_kamibukuro = null,
			a2_oishiso_boy = null, a2_usagihaneru = null,
			a2_kamibukuro_tap = null, a2_oishiso_girl = null, a2_yatane = null,
			a2_kansei_yubibue = null, a2_okane_ido = null, a2_yousei = null;

	private int numberSoundBoyAppear = 1, numberSoundGirlAppear = 1;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("yukirinrin/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("yukirinrin/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {

		// Load buyer packer
		for (int i = 0; i < packBuyer.length; i++) {
			packBuyer[i] = new TexturePackLoaderFromSource(
					this.getTextureManager(), pAssetManager, "yukirinrin/gfx/")
					.load(pathPacker[i]);
			Log.d("TEST", pathPacker[i]);
			packBuyer[i].loadTexture();
			packLibBuyer[i] = packBuyer[i].getTexturePackTextureRegionLibrary();
		}
		
		// Load background parker
		packBackground = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "yukirinrin/gfx/")
				.load("yukirinrin_background_success.xml");
		packBackground.loadTexture();
		packLibBackground = packBackground.getTexturePackTextureRegionLibrary();

		// Load other parker
		packOther = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "yukirinrin/gfx/").load("yukirinrin_other.xml");
		packOther.loadTexture();
		packLibOther = packOther.getTexturePackTextureRegionLibrary();
		
		for (int i = 0; i < ttxBuyer.length; i++) {
			ITextureRegion[] tmpRegion = new ITextureRegion[8];
			for (int j = 0; j < tmpRegion.length; j++) {
				tmpRegion[j] = packLibBuyer[i].get(buyerId[i][j]);
			}
			ttxBuyer[i] = new TiledTextureRegion(packBuyer[i].getTexture(),
					tmpRegion);
		}		

		txtTable = packLibBackground
				.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_8_IPHONE_TABLE_ID);
		txtBarrierLeft = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_10_1_IPHONE_LEFT_ID);
		txtBarrierRight = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_10_2_IPHONE_RIGHT_ID);
		txtNefuda = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_4_IPHONE_NEFUDA_ID);
		txtRegi = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_5_IPHONE_REGI_ID);

		for (int i = 0; i < mColumRegion.length; i++) {
			for (int j = 0; j < mColumRegion[i].length; j++) {
				mColumRegion[i][j] = packLibOther.get(intNumberId[j]);
			}
		}

		txtRecept = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_2_IPHONE_RECEPT_ID);
		txtInStack = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_3_3_IPHONE_OPEN_ID);
		txtAngel = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_3_2_IPHONE_OPEN_ID);
		ttxStack = new TiledTextureRegion(
				packOther.getTexture(),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_4_IPHONE_CLOSE_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_3_1_IPHONE_OPEN_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_3_4_IPHONE_OPEN_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_2_3_5_IPHONE_OPEN_ID));

		ttxKuma = new TiledTextureRegion(
				packOther.getTexture(),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_3_1_IPHONE_KUMA_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_3_2_IPHONE_KUMA_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_3_3_IPHONE_KUMA_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_3_4_IPHONE_KUMA_ID));
		txtLeftSide1 = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_6_IPHONE_LEFT_SIDE_ID);
		txtLeftSide2 = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_7_IPHONE_LEFT_SIDE_ID);

		txtLeftSide3 = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_4_IPHONE_LEFT_SIDE_ID);
		txtLeftSide4 = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_5_IPHONE_LEFT_SIDE_ID);

		for (int i = 0; i < ttxCake.length; i++) {
			ttxCake[i] = packLibOther.get(intCakeId[i]);
			ttxCakeBought[i] = packLibOther.get(intCakeBoughtId[i]);
		}

		ttxMoneyA = new TiledTextureRegion(
				packOther.getTexture(),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11A_1_IPHONE_MONEY_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11A_2_IPHONE_MONEY_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11A_3_IPHONE_MONEY_ID));

		ttxMoneyB = new TiledTextureRegion(
				packOther.getTexture(),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11B_1_IPHONE_MONEY_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11B_2_IPHONE_MONEY_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_11_11B_3_IPHONE_MONEY_ID));

		txtOrderTable = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_7_7_IPHONE__UKIDASHI_ID);

		ttxTrayOrder = new TiledTextureRegion(
				packOther.getTexture(),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_1_IPHONE_LEFT_SIDE_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_2_IPHONE_LEFT_SIDE_ID),
				packLibOther
						.get(Vol3YukirinrinResource.yukirinrin_other.A2_1_3_IPHONE_LEFT_SIDE_ID));

		for (int i = 0; i < txtSuccess.length; i++) {
			if (i == 4) {
				txtSuccess[i] = packLibBackground.get(intSuccessId[i]);
			} else {
				txtSuccess[i] = packLibBuyer[i].get(intSuccessId[i]);
			}
		}
		txtSuccessText = packLibOther
				.get(Vol3YukirinrinResource.yukirinrin_other.A2_6_IPHONE_SUCCESS_ID);
		txtSuccessLeft1 = packLibBackground
				.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_13_2_1_IPHONE_LEFT_ID);
		txtSuccessLeft2 = packLibBackground
				.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_13_2_2_IPHONE_LEFT_ID);
		txtSuccessRight1 = packLibBackground
				.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_13_3_1_IPHONE_RIGHT_ID);
		txtSuccessRight2 = packLibBackground
				.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_13_3_2_IPHONE_RIGHT_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		a2_11_doubutsuaruku = loadSoundResourceFromSD("a2_11_doubutsuaruku.ogg");
		a2_kawaipan = loadSoundResourceFromSD("a2_kawaipan.ogg");
		a2_ookami = loadSoundResourceFromSD("a2_ookami.ogg");
		a2_11_hitoaruku = loadSoundResourceFromSD("a2_11_hitoaruku.ogg");
		a2_korekudasai_boy = loadSoundResourceFromSD("a2_korekudasai_boy.ogg");
		a2_pan_tap = loadSoundResourceFromSD("a2_pan_tap.ogg");
		a2_arigatou = loadSoundResourceFromSD("a2_arigatou.ogg");
		a2_korekudasai_girl = loadSoundResourceFromSD("a2_korekudasai_girl.ogg");
		a2_pinpon = loadSoundResourceFromSD("a2_pinpon.ogg");
		a2_inu = loadSoundResourceFromSD("a2_inu.ogg");
		a2_kuraka = loadSoundResourceFromSD("a2_kuraka.ogg");
		a2_rezi = loadSoundResourceFromSD("a2_rezi.ogg");
		a2_irasyai = loadSoundResourceFromSD("a2_irasyai.ogg");
		a2_neko = loadSoundResourceFromSD("a2_neko.ogg");
		a2_syu = loadSoundResourceFromSD("a2_syu.ogg");
		a2_kamibukuro = loadSoundResourceFromSD("a2_kamibukuro.ogg");
		a2_oishiso_boy = loadSoundResourceFromSD("a2_oishiso_boy.ogg");
		a2_usagihaneru = loadSoundResourceFromSD("a2_usagihaneru.ogg");
		a2_kamibukuro_tap = loadSoundResourceFromSD("a2_kamibukuro_tap.ogg");
		a2_oishiso_girl = loadSoundResourceFromSD("a2_oishiso_girl.ogg");
		a2_yatane = loadSoundResourceFromSD("a2_yatane.ogg");
		a2_kansei_yubibue = loadSoundResourceFromSD("a2_kansei_yubibue.ogg");
		a2_okane_ido = loadSoundResourceFromSD("a2_okane_ido.ogg");
		a2_yousei = loadSoundResourceFromSD("a2_yousei.ogg");

	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(
				new Sprite(
						0,
						0,
						packLibBackground
								.get(Vol3YukirinrinResource.yukirinrin_background_success.A2_12_IPHONE_HAIKEI_ID),
						this.getVertexBufferObjectManager())));

		sBarrierLeft = new Sprite(0, 104, txtBarrierLeft,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBarrierLeft);

		sBarrierRight = new Sprite(641, 104, txtBarrierRight,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBarrierRight);

		for (int i = 0; i < asBuyer.length; i++) {
			asBuyer[i] = new AnimatedSprite(1000, -37, ttxBuyer[i],
					this.getVertexBufferObjectManager());
			mScene.attachChild(asBuyer[i]);
		}

		sSuccessText = new Sprite(53, 248, txtSuccessText,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSuccessText);
		sSuccessText.setZIndex(210);
		sSuccessText.setVisible(false);
		sSuccessLeft1 = new Sprite(-32, 144, txtSuccessLeft1,
				this.getVertexBufferObjectManager());
		sSuccessLeft1.setZIndex(211);
		sSuccessLeft1.setVisible(false);
		mScene.attachChild(sSuccessLeft1);
		sSuccessLeft2 = new Sprite(-32, 144, txtSuccessLeft2,
				this.getVertexBufferObjectManager());
		sSuccessLeft2.setZIndex(212);
		sSuccessLeft2.setVisible(false);
		mScene.attachChild(sSuccessLeft2);
		sSuccessRight1 = new Sprite(609, 144, txtSuccessRight1,
				this.getVertexBufferObjectManager());
		sSuccessRight1.setZIndex(213);
		sSuccessRight1.setVisible(false);
		mScene.attachChild(sSuccessRight1);
		sSuccessRight2 = new Sprite(609, 144, txtSuccessRight2,
				this.getVertexBufferObjectManager());
		sSuccessRight2.setZIndex(214);
		sSuccessRight2.setVisible(false);
		mScene.attachChild(sSuccessRight2);
		for (int k = 0; k < sSuccess.length; k++) {
			sSuccess[k] = new Sprite(intSuccessCoor[k][0],
					intSuccessCoor[k][1], txtSuccess[k],
					this.getVertexBufferObjectManager());
			mScene.attachChild(sSuccess[k]);
			if (k < 4) {
				sSuccess[k].setZIndex(220 + k);
			}
			sSuccess[k].setVisible(false);
		}
		sSuccess[3].setZIndex(220);

		mNumberBox = new Rectangle(737, 461, 139, 33,
				getVertexBufferObjectManager());
		mNumberBox.setColor(1.0f, 1.0f, 1.0f);
		mScene.attachChild(mNumberBox);

		float x = mNumberBox.getX();
		float y = mNumberBox.getY() + 1;
		for (int i = 0; i < mColumEntity.length; i++) {
			mColumEntity[i] = new Entity(0, 0);
			mScene.attachChild(mColumEntity[i]);

			for (int j = 0; j < mColumSprite[i].length; j++) {
				mColumSprite[i][j] = new SpriteNumber(x, y, mColumRegion[i][j],
						this.getVertexBufferObjectManager());
				y = y + mColumRegion[i][j].getHeight() + 1;
				mColumEntity[i].attachChild(mColumSprite[i][j]);
			}
			x += 23;
			y = mNumberBox.getY() + 1;
		}

		sTable = new Sprite(0, 150, txtTable,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sTable);

		sNefuda = new Sprite(91, 402, txtNefuda,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sNefuda);

		sRegi = new Sprite(670, 449, txtRegi,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRegi);

		sInStack = new Sprite(700, 471, txtInStack,
				this.getVertexBufferObjectManager());
		sInStack.setVisible(false);
		mScene.attachChild(sInStack);
		sInStack.setZIndex(99);

		sRecept = new Sprite(736, 498, txtRecept,
				this.getVertexBufferObjectManager());
		sRecept.setVisible(false);
		mScene.attachChild(sRecept);

		asMoneyA = new AnimatedSprite(535, 40, ttxMoneyA,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asMoneyA);
		asMoneyA.setVisible(false);
		asMoneyA.setZIndex(100);

		asMoneyB = new AnimatedSprite(520, 55, ttxMoneyB,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asMoneyB);
		asMoneyB.setVisible(false);
		asMoneyA.setZIndex(101);

		sAngel = new Sprite(813, 494, txtAngel,
				this.getVertexBufferObjectManager());
		sAngel.setVisible(false);
		mScene.attachChild(sAngel);
		sAngel.setZIndex(102);

		asStack = new AnimatedSprite(700, 471, ttxStack,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asStack);
		asStack.setZIndex(103);

		asKuma = new AnimatedSprite(291, 406, ttxKuma,
				this.getVertexBufferObjectManager());
		mScene.attachChild(asKuma);

		sLeftSide1 = new Sprite(0, 478, txtLeftSide1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeftSide1);

		sLeftSide2 = new Sprite(0, 478, txtLeftSide2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeftSide2);

		sLeftSide3 = new Sprite(0, 478, txtLeftSide3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeftSide3);
		sLeftSide3.setVisible(false);

		sLeftSide4 = new Sprite(0, 478, txtLeftSide4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeftSide4);
		sLeftSide4.setVisible(false);

		asTrayOrder = new AnimatedSprite(0, -10, ttxTrayOrder,
				this.getVertexBufferObjectManager());
		sLeftSide2.attachChild(asTrayOrder);
		asTrayOrder.setVisible(false);

		float tmpX = 85;
		for (int i = 0; i < cakeManager.length; i++) {
			float tmpY = 180;
			cakeManager[i] = new Cake();
			for (int j = 0; j < 4; j++) {
				cakeManager[i]
						.addCake(new SpriteCake(tmpX, tmpY, ttxCake[i], this
								.getVertexBufferObjectManager(), i, j,
								(i * 10 + j)) {
							@Override
							public boolean onAreaTouched(
									final TouchEvent pSceneTouchEvent,
									final float pTouchAreaLocalX,
									final float pTouchAreaLocalY) {
								if (!isTouchAllowAllSprite) {
									return true;
								} else {
									if (!this.isTouchOnThis()) {
										return true;
									}
								}
								switch (pSceneTouchEvent.getAction()) {
								case TouchEvent.ACTION_DOWN:
									a2_pan_tap.play();
									this.setZIndex(1000);
									this.setScale(1.3f);
									mScene.sortChildren();
									if (!checkDraftCake(this, false,
											pSceneTouchEvent)) {
										mEngine.registerUpdateHandler(new TimerHandler(
												0.2f, new ITimerCallback() {

													@Override
													public void onTimePassed(
															TimerHandler paramTimerHandler) {
														a2_syu.play();
													}
												}));
										asBuyer[buyerOrder.get(buyerNumber)]
												.stopAnimation(2);
										asBuyer[buyerOrder.get(buyerNumber)]
												.registerEntityModifier(new DelayModifier(
														1f,
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
																asBuyer[buyerOrder
																		.get(buyerNumber)]
																		.stopAnimation(1);
															}
														}));
									}
									break;
								case TouchEvent.ACTION_MOVE:
									randomNumber(0);
									this.setPosition(
											pSceneTouchEvent.getX()
													- this.getWidth() / 2,
											pSceneTouchEvent.getY()
													- this.getHeight() / 2);
									break;
								case TouchEvent.ACTION_UP:
									this.setZIndex(this.getIntZOder());
									resetNumber();
									if (!checkDraftCake(this, true,
											pSceneTouchEvent)) {
										if (pSceneTouchEvent.getX() > sLeftSide2
												.getX()
												&& this.getX() < sLeftSide2
														.getX()
														+ sLeftSide2
																.getWidthScaled()
												&& pSceneTouchEvent.getY() > sLeftSide2
														.getY()
												&& this.getY() < sLeftSide2
														.getY()
														+ sLeftSide2
																.getWidthScaled()) {
											final SpriteCake tmpCake = this;
											mEngine.registerUpdateHandler(new TimerHandler(
													0.2f, new ITimerCallback() {

														@Override
														public void onTimePassed(
																TimerHandler paramTimerHandler) {
															tmpCake.setScale(1.0f);
															tmpCake.setPosition(
																	tmpCake.getmXFirst(),
																	tmpCake.getmYFirst());
															mScene.sortChildren();
														}
													}));
										} else {
											this.setScale(1.0f);
											this.setPosition(this.getmXFirst(),
													this.getmYFirst());
											mScene.sortChildren();
										}

									} else {
										this.setScale(1.0f);
										this.setMovedStatus(true);
										this.setTouchOnThis(false);
										this.detachSelf();
										sLeftSide2.attachChild(this);
										this.setPosition(
												cakeMoveCoorX[qualityCakeRandrom - 1][countCakeMoveOK],
												cakeMoveCoorY[qualityCakeRandrom - 1][countCakeMoveOK]);
										typeCakeChoice[this.getTypeCake()]++;
										countCakeMoveOK++;
										setNumber(3, countCakeMoveOK, 0.1f);
										try {
											orderCake(this.getTypeCake(),
													this.getNumberCake());
											if (countCakeMoveOK == qualityCakeRandrom) {
												a2_pinpon.play();
												isTouchAllowAllSprite = false;
												asTrayOrder.setVisible(false);
												transactionSuccess();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									break;
								}
								return true;
							}
						});
				cakeManager[i].getCake(j).setZIndex(
						cakeManager[i].getCake(j).getIntZOder());
				mScene.attachChild(cakeManager[i].getCake(j));
				this.mScene.registerTouchArea(cakeManager[i].getCake(j));
				tmpY += 54;
			}
			tmpX += 141;
		}

		sOrderTable = new Sprite(80, 30, txtOrderTable,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOrderTable);
		sOrderTable.setVisible(false);

		mScene.sortChildren();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void combineGimmic3WithAction() {

	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
		long[] pFrameDurations = { 800, 800 };
		asKuma.animate(pFrameDurations, 0, 1, true);
		try {
			createRandromBuyer();
			startTransaction(buyerOrder.get(buyerNumber));
		} catch (Exception e) {
			e.printStackTrace();
		}
		touchRegiRandrom = new Random().nextInt(3);
		isYukirinrinRunning = true;
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		try {
			isYukirinrinRunning = false;
			resetGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		if (!isYukirinrinRunning) {
			mEngine.registerUpdateHandler(new TimerHandler(5f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							runOnUpdateThread(new Runnable() {

								@Override
								public void run() {
									try {
										createRandromBuyer();
										startTransaction(buyerOrder
												.get(buyerNumber));
										isYukirinrinRunning = true;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					}));
		}
	}

	public void playSoundWhenCustomerAppear(int index) {
		switch (index) {
		case 0:
			a2_usagihaneru.play();
			break;
		case 1:
			a2_ookami.play();
			break;
		case 2:
			a2_inu.play();
			break;
		case 3:
			a2_neko.play();
			break;
		case 4:
		case 5:
		case 6:
			if (numberSoundBoyAppear == 1) {
				a2_oishiso_boy.play();
				numberSoundBoyAppear = 2;
			} else {
				a2_korekudasai_boy.play();
				numberSoundBoyAppear = 1;
			}
			break;
		case 7:
		case 8:
		case 9:
			switch (numberSoundGirlAppear) {
			case 1:
				a2_oishiso_girl.play();
				numberSoundGirlAppear++;
				break;
			case 2:
				a2_korekudasai_girl.play();
				numberSoundGirlAppear++;
				break;
			case 3:
				a2_kawaipan.play();
				numberSoundGirlAppear = 1;
				break;
			}
			break;
		}
	}

	public boolean checkDraftCake(SpriteCake cakeCheck, boolean checkLocation,
			TouchEvent pSceneTouchEvent) {
		boolean checkCake = false;
		try {
			for (int k = 0; k < typeCakeRandom.length; k++) {
				if (typeCakeChoice[cakeCheck.getTypeCake()] < typeCakeRandom[cakeCheck
						.getTypeCake()]) {
					if (checkLocation) {
						if (pSceneTouchEvent.getX() > sLeftSide2.getX()
								&& cakeCheck.getX() < sLeftSide2.getX()
										+ sLeftSide2.getWidthScaled()
								&& pSceneTouchEvent.getY() > sLeftSide2.getY()
								&& cakeCheck.getY() < sLeftSide2.getY()
										+ sLeftSide2.getWidthScaled()) {
							checkCake = true;
						} else {
							checkCake = false;
						}
						break;
					} else {
						checkCake = true;
					}
				}
			}
		} catch (Exception e) {
		}
		return checkCake;
	}

	public void setNumber(final int index, final int toNumber, float timeDefault) {
		int currentNumber = (int) Math.abs(mColumEntity[index].getY()
				/ mColumRegion[0][0].getHeight());
		if (currentNumber == toNumber) {
			return;
		}
		final float pY = toNumber * mColumRegion[0][0].getHeight() + toNumber;
		int pointY = (int) (mColumEntity[index].getY()
				- (toNumber - currentNumber) * mColumRegion[0][0].getHeight() + toNumber);
		float duration = Math.abs(toNumber - currentNumber)
				* (timeDefault + index * 0.01f);
		mColumEntity[index].clearEntityModifiers();
		mColumEntity[index].registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(duration, mColumEntity[index].getY(), pointY,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								isColumnMove[index] = true;

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								mColumEntity[index].setPosition(
										mColumEntity[index].getX(), -pY);

							}
						}), new DelayModifier(0.5f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								isColumnMove[index] = false;
							}
						})));
	}

	public void randomNumber(final int currentStart) {
		if (randomTimer == null) {
			mEngine.registerUpdateHandler(randomTimer = new TimerHandler(0.1f,
					true, new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							for (int i = currentStart; i < mColumEntity.length; i++) {
								if (isColumnMove[i] == false) {
									setNumber(i, new Random().nextInt(10), 0.1f);
								}
							}
						}
					}));
		}
	}

	public void resetNumber() {
		mEngine.unregisterUpdateHandler(randomTimer);
		randomTimer = null;
		mEngine.registerUpdateHandler(new TimerHandler(0.2f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						try {
							for (int i = 0; i < mColumEntity.length; i++) {
								if (isActionRegi) {
									if (i >= 3) {
										setNumber(i, 9, 0f);
									} else {
										setNumber(i, 0, 0f);
									}
								} else {
									if (i != 3) {
										setNumber(i, 0, 0f);
									} else {
										setNumber(i, countCakeMoveOK, 0f);
									}
								}
							}
						} catch (Exception e) {
						}
					}
				}));

	}

	public void orderCake(int typeCake, int orderMove) throws Exception {
		float tmpY = cakeManager[typeCake].getCake(orderMove).getmYFirst();
		for (int i = orderMove - 1; i >= 0; i--) {
			if (cakeManager[typeCake].getCake(i).isMovedStatus()) {
				continue;
			}
			cakeManager[typeCake].getCake(i).registerEntityModifier(
					new MoveYModifier(0.5f, cakeManager[typeCake].getCake(i)
							.getY(), tmpY));
			float newY = tmpY;
			tmpY = cakeManager[typeCake].getCake(i).getmYFirst();
			cakeManager[typeCake].getCake(i).setmYFirst(newY);
		}
	}

	public void transactionSuccess() throws Exception {
		asBuyer[buyerOrder.get(buyerNumber)].stopAnimation(7);
		if (buyerOrder.get(buyerNumber) < 4) {
			asMoneyA.setVisible(true);
			asMoneyB.setVisible(false);
		} else {
			asMoneyB.setVisible(true);
			asMoneyA.setVisible(false);
		}
		asBuyer[buyerOrder.get(buyerNumber)]
				.registerEntityModifier(new DelayModifier(1f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								asBuyer[buyerOrder.get(buyerNumber)]
										.stopAnimation(1);
								if (buyerOrder.get(buyerNumber) < 4) {
									asMoneyA.setVisible(true);
									asMoneyB.setVisible(false);
								} else {
									asMoneyB.setVisible(true);
									asMoneyA.setVisible(false);
								}
							}
						}));
		IEntityModifier side2Modifier = new ParallelEntityModifier(
				new MoveModifier(1f, sLeftSide2.getX(), 0, sLeftSide2.getY(),
						480), new ScaleAtModifier(1f, 1.3f, 1.0f, 0.5f, 0.5f));
		sLeftSide2.registerEntityModifier(side2Modifier);
		sLeftSide3.setZIndex(1000);
		sLeftSide4.setZIndex(1001);
		mScene.sortChildren();
		sLeftSide3.setVisible(true);
		sLeftSide3.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(1f, 640, 478), new DelayModifier(1f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								try {
									sLeftSide2.detachChildren();
									sLeftSide2.attachChild(asTrayOrder);
									packageCakeProcess();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						})));
	}

	public void packageCakeProcess() throws Exception {
		sLeftSide4.setVisible(true);
		a2_kamibukuro.play();
		sLeftSide4.registerEntityModifier(new ParallelEntityModifier(
				new AlphaModifier(1f, 0.2f, 1f), new MoveYModifier(1f, 420,
						480, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								try {
									asTrayOrder.detachChildren();
									sLeftSide3.setVisible(false);
									asTrayOrder.setVisible(false);
									sLeftSide4
											.registerEntityModifier(bagVibration());
									isTouchBag = true;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						})));
	}

	public void paymentStarting() throws Exception {
		if (asMoneyB.isVisible()) {
			asMoneyB.setVisible(false);
			asMoneyA.setVisible(true);
		}
		a2_okane_ido.play();
		asMoneyA.registerEntityModifier(new MoveModifier(0.5f, asMoneyA.getX(),
				730, asMoneyA.getY(), 500, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							asBuyer[buyerOrder.get(buyerNumber)]
									.stopAnimation(0);
							openStack(0.2f);
							sendCakeBagForCustomer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							closeStack();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void openStack(float timeDelay) throws Exception {
		sInStack.registerEntityModifier(new DelayModifier(timeDelay,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						asStack.stopAnimation(1);
						sInStack.setVisible(true);
					}
				}));

	}

	public void closeStack() throws Exception {
		sInStack.registerEntityModifier(new DelayModifier(0.1f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							asMoneyA.setVisible(false);
							asMoneyA.setPosition(asMoneyA.getmXFirst(),
									asMoneyA.getmYFirst());
							sInStack.setVisible(false);
							asStack.stopAnimation(0);
							countCakeMoveOK = 0;
							setNumber(3, 0, 0.01f);
							sendReceptForCustomer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void sendCakeBagForCustomer() throws Exception {

		sLeftSide4.clearEntityModifiers();
		sLeftSide4.registerEntityModifier(new MoveModifier(0.9f, sLeftSide4
				.getX(), 330, sLeftSide4.getY(), 120,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void sendReceptForCustomer() throws Exception {
		a2_okane_ido.play();
		sRecept.setZIndex(1002);
		mScene.sortChildren();
		sRecept.setVisible(true);
		sRecept.registerEntityModifier(new MoveModifier(0.9f, sRecept.getX(),
				460, sRecept.getmYFirst(), 140, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							goodbyeCustomer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void goodbyeCustomer() throws Exception {
		if (!isActionRegi && !isTouchRegi) {
			isTouchRegi = true;
		}
		sRecept.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sRecept.setVisible(false);
						sRecept.setPosition(sRecept.getmXFirst(),
								sRecept.getmYFirst());
						sLeftSide4.setVisible(false);
						sLeftSide4.setPosition(sLeftSide4.getmXFirst(),
								sLeftSide4.getmYFirst());
						long[] pFrameDurations = { 600, 600 };
						a2_arigatou.play();
						asBuyer[buyerOrder.get(buyerNumber)].animate(
								pFrameDurations, 5, 6, true);
						asBuyer[buyerOrder.get(buyerNumber)]
								.registerEntityModifier(new MoveXModifier(3f,
										asBuyer[buyerOrder.get(buyerNumber)]
												.getX(), -asBuyer[buyerOrder
												.get(buyerNumber)].getWidth(),
										new IEntityModifierListener() {

											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												sOrderTable.detachChildren();
												sOrderTable.setVisible(false);
											}

											@Override
											public void onModifierFinished(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												countCakeMoveOK = 0;
												buyerNumber++;
												try {
													if (buyerNumber == 10) {
														omedeto();
													} else {
														startTransaction(buyerOrder
																.get(buyerNumber));
													}
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}));
					}
				}));
	}

	public void omedeto() throws Exception {
		a2_yatane.play();
		a2_kuraka.play();
		a2_kansei_yubibue.play();
		for (int i = 0; i < sSuccess.length; i++) {
			sSuccess[i].setVisible(true);
			switch (i) {
			case 0:
				a2_usagihaneru.play();
				sSuccess[i].registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(new DelayModifier(0.15f),
								new MoveModifier(0.75f, sSuccess[i]
										.getmXFirst(),
										sSuccess[i].getmXFirst() + 25,
										sSuccess[i].getmYFirst(), sSuccess[i]
												.getmYFirst() - 25),
								new DelayModifier(0.15f), new MoveModifier(
										0.75f, sSuccess[i].getmXFirst() + 25,
										sSuccess[i].getmXFirst(), sSuccess[i]
												.getmYFirst() - 25, sSuccess[i]
												.getmYFirst())), 2));
				break;
			case 1:
				a2_inu.play();
				sSuccess[i].registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(new DelayModifier(0.15f),
								new MoveYModifier(0.75f, sSuccess[i]
										.getmYFirst(),
										sSuccess[i].getmYFirst() - 25),
								new DelayModifier(0.15f), new MoveYModifier(
										0.75f, sSuccess[i].getmYFirst() - 25,
										sSuccess[i].getmYFirst())), 2));
				break;
			case 2:
				a2_ookami.play();
				sSuccess[i].registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(new DelayModifier(0.15f),
								new MoveYModifier(0.75f, sSuccess[i]
										.getmYFirst(),
										sSuccess[i].getmYFirst() - 25),
								new DelayModifier(0.15f), new MoveYModifier(
										0.75f, sSuccess[i].getmYFirst() - 25,
										sSuccess[i].getmYFirst())), 2));
				break;
			case 3:
				a2_neko.play();
				sSuccess[i].registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(new DelayModifier(0.15f),
								new MoveModifier(0.75f, sSuccess[i]
										.getmXFirst(),
										sSuccess[i].getmXFirst() - 25,
										sSuccess[i].getmYFirst(), sSuccess[i]
												.getmYFirst() - 25),
								new DelayModifier(0.15f), new MoveModifier(
										0.75f, sSuccess[i].getmXFirst() - 25,
										sSuccess[i].getmXFirst(), sSuccess[i]
												.getmYFirst() - 25, sSuccess[i]
												.getmYFirst())), 2));
				break;
			case 4:
				sSuccess[i].registerEntityModifier(new LoopEntityModifier(
						new SequenceEntityModifier(new MoveXModifier(0.25f,
								sSuccess[i].getX(), sSuccess[i].getX() - 20),
								new DelayModifier(0.25f), new MoveXModifier(
										0.5f, sSuccess[i].getX() - 20,
										sSuccess[i].getX())), 3));
				break;
			}
		}
		sSuccessText.setVisible(true);
		sSuccessLeft1.setVisible(true);
		sSuccessLeft2.setVisible(true);
		sSuccessRight1.setVisible(true);
		sSuccessRight2.setVisible(true);
		IEntityModifier fireworks = new LoopEntityModifier(
				new ParallelEntityModifier(new AlphaModifier(2.5f, 0f, 1f),
						new ScaleModifier(2.5f, 1f, 1.05f), new DelayModifier(
								1f)), 2);

		sSuccessLeft1.registerEntityModifier(fireworks);
		sSuccessRight1.registerEntityModifier(fireworks);
		asKuma.clearEntityModifiers();
		asKuma.stopAnimation(3);
		isTouchKuma = false;
		isTouchRegi = false;
		mEngine.registerUpdateHandler(new TimerHandler(5f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {

						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								try {
									resetGame();
									createRandromBuyer();
									startTransaction(buyerOrder
											.get(buyerNumber));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				}));
	}

	public void resetGame() throws Exception {
		mEngine.clearUpdateHandlers();
		for (int i = 0; i < sSuccess.length; i++) {
			sSuccess[i].setVisible(false);
			sSuccess[i].clearEntityModifiers();
			sSuccess[i].clearUpdateHandlers();
		}
		sSuccessText.setVisible(false);
		sSuccessLeft1.setVisible(false);
		sSuccessLeft1.clearEntityModifiers();
		sSuccessLeft1.clearUpdateHandlers();
		sSuccessLeft2.setVisible(false);
		sSuccessRight1.setVisible(false);
		sSuccessRight1.clearEntityModifiers();
		sSuccessRight1.clearUpdateHandlers();
		sSuccessRight2.setVisible(false);
		sLeftSide2.clearEntityModifiers();
		sLeftSide2.setScale(1f);
		sLeftSide2
				.setPosition(sLeftSide2.getmXFirst(), sLeftSide2.getmYFirst());
		sLeftSide3.clearEntityModifiers();
		sLeftSide3.setVisible(false);
		sLeftSide4.clearEntityModifiers();
		sLeftSide4.clearUpdateHandlers();
		sLeftSide4
				.setPosition(sLeftSide4.getmXFirst(), sLeftSide4.getmYFirst());
		sLeftSide4.setVisible(false);
		sAngel.clearEntityModifiers();
		sAngel.clearUpdateHandlers();
		sAngel.setVisible(false);
		sAngel.setPosition(sAngel.getmXFirst(), sAngel.getmYFirst());
		sInStack.setVisible(false);
		sInStack.clearEntityModifiers();
		sInStack.clearUpdateHandlers();
		asStack.stopAnimation(0);
		asStack.clearEntityModifiers();
		asStack.clearUpdateHandlers();
		sRecept.clearUpdateHandlers();
		sRecept.clearEntityModifiers();
		sRecept.setPosition(sRecept.getmXFirst(), sRecept.getmYFirst());
		sRecept.setVisible(false);
		for (int i = 0; i < asBuyer.length; i++) {
			asBuyer[i].clearEntityModifiers();
			asBuyer[i].clearUpdateHandlers();
			asBuyer[i].setPosition(1000, -37);
			//asBuyer[i].setVisible(false);
		}
		asKuma.clearEntityModifiers();
		asKuma.clearUpdateHandlers();
		long[] pFrameDurations = { 800, 800 };
		asKuma.animate(pFrameDurations, 0, 1, true);
		asTrayOrder.setScale(1f);
		asTrayOrder.setPosition(asTrayOrder.getmXFirst(),
				asTrayOrder.getmYFirst());
		asTrayOrder.setVisible(false);
		asTrayOrder.clearEntityModifiers();
		asTrayOrder.clearUpdateHandlers();
		sOrderTable.detachChildren();
		sOrderTable.setVisible(false);
		asMoneyA.setVisible(false);
		asMoneyB.setVisible(false);
		asMoneyA.clearEntityModifiers();
		asMoneyA.clearUpdateHandlers();
		asMoneyB.clearEntityModifiers();
		asMoneyB.clearUpdateHandlers();
		SpriteCake.isTouchAllowAllSprite = false;
		for (int i = 0; i < cakeManager.length; i++) {
			cakeManager[i].setQuality(4);
			for (Iterator<SpriteCake> iterator = cakeManager[i].getAllCake()
					.iterator(); iterator.hasNext();) {
				SpriteCake cake = (SpriteCake) iterator.next();
				cake.detachSelf();
				cake.setPosition(cake.getFloatX(), cake.getFloatY());
				cake.setmXFirst(cake.getFloatX());
				cake.setmYFirst(cake.getFloatY());
				cake.setTouchOnThis(true);
				cake.setMovedStatus(false);
				cake.setZIndex(cake.getIntZOder());
				cake.clearEntityModifiers();
				cake.clearUpdateHandlers();
				mScene.attachChild(cake);
			}
		}
		mScene.sortChildren();
		resetNumber();
		isTouchRegi = true;
		isTouchKuma = true;
		isTouchBag = false;
		isActionRegi = false;
		qualityCakeRandrom = 0;
		countCakeMoveOK = 0;
		for (int i = 0; i < 6; i++) {
			typeCakeRandom[i] = 0;
			typeCakeChoice[i] = 0;
		}
		buyerNumber = 0;
		buyerOrder.clear();
		cakeAppear.clear();
	}

	public IEntityModifier bagVibration() throws Exception {
		return new SequenceEntityModifier(new DelayModifier(1f),
				new MoveXModifier(0.35f, 0, 12), new MoveXModifier(0.35f, 12,
						-12), new MoveXModifier(0.3f, -12, 10),
				new MoveXModifier(0.3f, 10, -10), new MoveXModifier(0.25f, -10,
						8), new MoveXModifier(0.25f, 8, -8), new MoveXModifier(
						0.2f, -8, 6), new MoveXModifier(0.2f, 6, 0),
				new DelayModifier(1f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							sLeftSide4.registerEntityModifier(bagVibration());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void startTransaction(final int index) throws Exception {
		Log.d("TEST", index+"/"+asBuyer[index].isVisible());
		if (index < 4) {
			a2_11_doubutsuaruku.play();
		} else {
			a2_11_hitoaruku.play();
		}
		long[] pFrameDurations = { 600, 600 };
		asBuyer[index].animate(pFrameDurations, 3, 4, true);
		asBuyer[index].registerEntityModifier(new MoveXModifier(3f,
				asBuyer[index].getX(), 364, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						try {
							playSoundWhenCustomerAppear(index);
							createRandomCakeBought();
							asBuyer[index].stopAnimation(1);
							if (index < 4) {
								asMoneyA.setVisible(true);
							} else {
								asMoneyB.setVisible(true);
							}
							asMoneyA.stopAnimation(qualityCakeRandrom - 1);
							asMoneyB.stopAnimation(qualityCakeRandrom - 1);
							asTrayOrder.stopAnimation(qualityCakeRandrom - 1);
							sOrderTable.setVisible(true);
							asTrayOrder.setVisible(true);
							IEntityModifier side2Modifier = new ParallelEntityModifier(
									new MoveModifier(1f, 0, 60, 478, 420),
									new ScaleAtModifier(1f, 1.0f, 1.3f, 0.5f,
											0.5f));
							sLeftSide2.registerEntityModifier(side2Modifier);
							SpriteCake.isTouchAllowAllSprite = true;
							int count = 1;
							for (int i = 0; i < typeCakeRandom.length; i++) {
								if (typeCakeRandom[i] != 0) {
									for (int j = 0; j < typeCakeRandom[i]; j++) {
										sCakeBought[i] = new Sprite(
												cakeAppearCoorX[qualityCakeRandrom - 1][count - 1],
												cakeAppearCoorY[qualityCakeRandrom - 1][count - 1],
												ttxCakeBought[i],
												getVertexBufferObjectManager());
										sOrderTable.attachChild(sCakeBought[i]);
										count++;
									}
								}

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
	}

	public void onTouchKuma() throws Exception {
		a2_irasyai.play();
		asKuma.stopAnimation(2);
		asKuma.registerEntityModifier(new DelayModifier(1.0f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						long[] pFrameDurations = { 500, 500 };
						asKuma.animate(pFrameDurations, 0, 1, true);
						isTouchKuma = true;
					}
				}));
	}

	public void onTouchRegi() throws Exception {
		a2_rezi.play();
		resetNumber();
		for (int i = 3; i < mColumEntity.length; i++) {
			if (isColumnMove[i] == false) {
				setNumber(i, 9, 0.01f);
			}
		}
		switch (touchRegiRandrom) {
		case 0:
			// angel
			openStack(0);
			Path path = new Path(5).to(sAngel.getX(), sAngel.getY())
					.to(600, 440).to(550, 430).to(600, 300).to(-200, -200);
			sAngel.setVisible(true);
			sAngel.registerEntityModifier(new SequenceEntityModifier(
					new DelayModifier(0.5f, new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> paramIModifier,
								IEntity paramT) {

						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> paramIModifier,
								IEntity paramT) {

						}
					}), new PathModifier(3f, path,
							new IEntityModifierListener() {

								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									a2_yousei.play();
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									sInStack.setVisible(false);
									asStack.stopAnimation(0);
									resetNumber();
									allowTouchOnRegi();
									isActionRegi = false;
									sAngel.setVisible(false);
									sAngel.setPosition(sAngel.getmXFirst(),
											sAngel.getmYFirst());
								}
							})));
			break;
		case 1:
		case 2:
			asStack.stopAnimation(touchRegiRandrom + 1);
			mEngine.registerUpdateHandler(new TimerHandler(1.5f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							asStack.stopAnimation(0);
							resetNumber();
							allowTouchOnRegi();
							isActionRegi = false;
						}
					}));
			break;
		}
		touchRegiRandrom++;
		if (touchRegiRandrom == 3) {
			touchRegiRandrom = 0;
		}
	}

	public void allowTouchOnRegi() {
		mEngine.registerUpdateHandler(new TimerHandler(1.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						isTouchRegi = true;
					}
				}));
	}

	public void createRandromBuyer() throws Exception {
		if (buyerOrder.size() == 0) {
			for (int i = 0; i < 10; i++) {
				buyerOrder.add(i);
			}
		}
		Collections.shuffle(buyerOrder);
	}

	public void createRandomCakeBought() throws Exception {
		if (cakeAppear.size() == 0) {
			cakeAppear.add(3);
			for (int i = 0; i < 4; i++) {
				cakeAppear.add(2);
				cakeAppear.add(3);
			}
			Collections.shuffle(cakeAppear);
			cakeAppear.add(0, 1);
		}
		for (int i = 0; i < typeCakeRandom.length; i++) {
			typeCakeRandom[i] = 0;
			typeCakeChoice[i] = 0;
		}
		qualityCakeRandrom = cakeAppear.get(buyerNumber);
		ArrayList<Integer> cakeGet = getCakeRandom(qualityCakeRandrom);
		for (int i = 0; i < cakeGet.size(); i++) {
			typeCakeRandom[cakeGet.get(i)]++;
			cakeManager[cakeGet.get(i)].setQuality(cakeManager[cakeGet.get(i)]
					.getQuality() - 1);
		}
	}

	public ArrayList<Integer> getCakeRandom(int quality) {
		ArrayList<Integer> origin = new ArrayList<Integer>();
		ArrayList<Integer> target = new ArrayList<Integer>();
		for (int i = 0; i < cakeManager.length; i++) {
			origin.add(cakeManager[i].getQuality() * 10 + i);
		}
		Collections.sort(origin, Collections.reverseOrder());
		int standart = origin.get(0);
		target.add(standart % 10);
		for (int i = 1; i < origin.size(); i++) {
			if (origin.get(i) >= standart - 5) {
				target.add(origin.get(i) % 10);
			}
		}
		if (target.size() < quality) {
			for (int i = target.size(); i < origin.size(); i++) {
				target.add(origin.get(i) % 10);
				if (target.size() == quality) {
					break;
				}
			}
		}
		int size = target.size();
		for (int i = 0; i < size - quality; i++) {
			target.remove(new Random().nextInt(target.size()));
		}
		return target;
	}

	@Override
	public boolean onSceneTouchEvent(Scene paramScene,
			TouchEvent paramTouchEvent) {
		int pX = (int) paramTouchEvent.getX();
		int pY = (int) paramTouchEvent.getY();
		if (paramTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			try {
				if (!isActionRegi
						&& isTouchBag
						&& checkContains(sLeftSide4, 0, 0,
								(int) sLeftSide4.getWidth(),
								(int) sLeftSide4.getHeight(), pX, pY)) {
					isTouchBag = false;
					isTouchRegi = false;
					a2_kamibukuro_tap.play();
					paymentStarting();
				}
				if (isTouchKuma
						&& checkContains(asKuma, 0, 0, (int) asKuma.getWidth(),
								(int) asKuma.getHeight(), pX, pY)) {
					isTouchKuma = false;
					onTouchKuma();
				}
				if (!isActionRegi
						&& isTouchRegi
						&& checkContains(sRegi, 0, 0, (int) sRegi.getWidth(),
								(int) sRegi.getHeight(), pX, pY)) {
					isTouchRegi = false;
					isActionRegi = true;
					onTouchRegi();
				}
			} catch (Exception e) {
			}
		}
		return true;
	}

	private class Cake {

		ArrayList<SpriteCake> sCake;
		int quality = 4;

		public Cake() {
			super();
			sCake = new ArrayList<SpriteCake>();
		}

		public void addCake(SpriteCake cake) {
			sCake.add(cake);
		}

		public SpriteCake getCake(int index) {
			return sCake.get(index);
		}

		public ArrayList<SpriteCake> getAllCake() {
			return sCake;
		}

		public int getQuality() {
			return quality;
		}

		public void setQuality(int quality) {
			this.quality = quality;
		}

	}

	class SpriteNumber extends Sprite {

		public SpriteNumber(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		}

		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			if (this.collidesWith(mNumberBox)) {
				this.setAlpha(1.0f);
			} else {
				this.setAlpha(0.0f);
			}
			super.onManagedUpdate(pSecondsElapsed);
		}

	}

	private static class SpriteCake extends Sprite {

		private int typeCake;
		private int numberCake;
		private int intZOder = 0;
		public static boolean isTouchAllowAllSprite = false;
		private float floatX, floatY;
		private boolean isTouchOnThis = true;
		private boolean movedStatus = false;

		public SpriteCake(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		}

		public SpriteCake(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager,
				int typeCake, int numberCake, int intZOder) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
			this.typeCake = typeCake;
			this.numberCake = numberCake;
			this.floatX = pX;
			this.floatY = pY;
			this.intZOder = intZOder;
		}

		public int getTypeCake() {
			return typeCake;
		}

		public int getNumberCake() {
			return numberCake;
		}

		public float getFloatX() {
			return floatX;
		}

		public float getFloatY() {
			return floatY;
		}

		public int getIntZOder() {
			return intZOder;
		}

		public boolean isTouchOnThis() {
			return isTouchOnThis;
		}

		public void setTouchOnThis(boolean isTouchOnThis) {
			this.isTouchOnThis = isTouchOnThis;
		}

		public boolean isMovedStatus() {
			return movedStatus;
		}

		public void setMovedStatus(boolean movedStatus) {
			this.movedStatus = movedStatus;
		}

	}
}
