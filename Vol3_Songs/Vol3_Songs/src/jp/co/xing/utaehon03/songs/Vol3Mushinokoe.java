package jp.co.xing.utaehon03.songs;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MushinokoeResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Mushinokoe extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {
	private static final String TAG = "LOG_MUSHINOKOE";
	RectangularShape shapeAmi, shapeKuwagata, shapeKabuto, shapeKirigirisu,
			shapeTentoumushi, shapeKamakiri, shapeTonbo, shapeCyou1,
			shapeCyou2, shapeHachi1, shapeHachi2, shapeHachi3, shapeSemi1,
			shapeSemi2, shapeAri, shapeDango, shapeKamikiri, shapeKogane,
			shapeYoutyu, shapeHotaru, shapeMatsumushi, shapeSuzumushi;
	private TimerHandler tTimer;

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpMushinokoe1, ttpMushinokoe2, ttpMushinokoe3,
			ttpMushinokoe4, ttpMushinokoe5;
	private TexturePackTextureRegionLibrary ttpLibMushinokoe1,
			ttpLibMushinokoe2, ttpLibMushinokoe3, ttpLibMushinokoe4,
			ttpLibMushinokoe5;

	private TextureRegion ttrBackGround1, ttrBackGround2, ttrBackGround3,
			ttrBackGround4, ttrAmi, ttrMushikago, ttrnameOneKuwagata,
			ttrOneKuwagata4, ttrOneKuwagata5, ttrnameOneKabuto, ttrOneKabuto4,
			ttrOneKabuto5,

			ttrKirigirisu, ttrnameKirigirisu, ttrKirigirisu6,
			ttrnameTentoumushi, ttrTentoumushi4, ttrKamakiri1, ttrKamakiri2,
			ttrKamakiri3, ttrnameKamakiri, ttrKamakiri5, ttrTonbo3,
			ttrnameTonbo, ttrTonbo5, ttrCyou1, ttrnameCyou, ttrCyou5, ttrCyou6,
			ttrHachi1, ttrHachi2, ttrHachi3, ttrHachi4, ttrHachi5,
			ttrnameHachi, ttrHachi7, ttrSemi1, ttrSemi2, ttrSemi3, ttrnameSemi,
			ttrSemi5, ttrSemi6, ttrnameAri, ttrAri5, ttrnameDango, ttrDango5,
			ttrnameKamikiri, ttrKamikiri4, ttrnameKogane, ttrKogane5,
			ttrnameYoutyu, ttrYoutyu6,

			ttrHotaru1, ttrHotaru2, ttrHotaru3, ttrnameHotaru, ttrHotaru5,
			ttrMatsumushi1, ttrnameMatsumushi, ttrMatsumuchi5, ttrSuzumushi1,
			ttrnameSuzumushi, ttrSuzumushi5, ttrHake1, ttrHake2, ttrHake3;

	private Sprite sBackGround1, sBackGround2, sBackGround3, sBackGround4,
			sAmi, sMushikago, snameOneKugawata, sOneKugawata4, sOneKugawata5,
			snameOneKabuto, sOneKabuto4, sOneKabuto5,

			sKirigirisu, snameKirigirisu, sKirigirisu6, snameTentoumushi,
			sTentoumushi4, sKamakiri1, sKamakiri2, sKamakiri3, snameKamakiri,
			sKamakiri5, sTonbo3, snameTonbo, sTonbo5, sCyou1, snameCyou,
			sCyou5, sCyou6, sHachi1, sHachi2, sHachi3, sHachi4, sHachi5,
			snameHachi, sHachi7, sSemi1, sSemi2, sSemi3, snameSemi, sSemi5,
			sSemi6, snameAri, sAri5, snameDango, sDango5, snameKamikiri,
			sKamikiri4, snameKogane, sKogane5, snameYoutyu, sYoutyu6,

			sHotaru1, sHotaru2, sHotaru3, snameHotaru, sHotaru5, sMatsumushi1,
			snameMatsumushi, sMatsumushi5, sSuzumushi1, snameSuzumushi,
			sSuzumushi5, sHake1, sHake2, sHake3;

	private TiledTextureRegion tttrKuwagata, tttrKabuto, tttrKirigirisu,
			tttrTentoumushi, tttrTonbo, tttrCyou, tttrAri, tttrDango,
			tttrKamikiri, tttrKogane, tttrYoutyu, tttrMatsumushi,
			tttrSuzumushi;

	private AnimatedSprite animKuwagata, animKabuto, animKirigirisu,
			animTentoumushi, animTonbo, animCyou, animAri, animDango,
			animKamikiri, animKogane, animYoutyu, animMatsumushi,
			animSuzumushi;

	private Calendar mCalendar;

	private int cases = 0, current, pTentoumushi = 0, checkCyouFly,
			checkKamakiri, checkSemi, checkTentoumushi, checkAri, checkDango,
			checkKamikiri, checkKogane, checkYoutyu, pAri = 0, pDango = 0,
			pKamikiri = 0, pKogane, pYoutyu = 0;

	private boolean isAmiMove, enableCatch, isCatch, isTouchKuwagata,
			isTouchKabuto, isTouchKirigirisu, isTouchKamakiri, isTouchTonbo,
			isTouchCyou, isTouchHachi, isTouchSemi, isTouchHotaru,
			isTouchMatsumushi, isTouchSuzumushi, isTouchHake, isFaceKabuto,
			isFaceKuwagata, isTouchKamikiri, checkNight;

	private boolean notCatchKuwagata, notCatchKabuto, notCatchKirigirisu,
			notCatchTentoumushi, notCatchKamakiri, notCatchTonbo, notCatchCyou,
			notCatchHachi, notCatchSemi, notCatchAri, notCatchDango,
			notCatchKamikiri, notCatchKogane, notCatchYoutyu, notCatchHotaru,
			notCatchMatsumushi, notCatchSuzumushi;

	private boolean isKuwagataJump, isKabutoJump, isKirigirisuJump,
			isTentoumushiJump, isKamakiriJump, isTonboJump, isCyouJump,
			isHachiJump, isSemiJump, isAriJump, isDangoJump, isKamikiriJump,
			isKoganeJump, isYoutyuJump, isHotaruJump, isMatsumushiJump,
			isSuzumushiJump;

	private Sound A1_04_harp, A1_05_7_kirigilis_long, A1_05_7_kirigilis_voice,
			A1_05_8_tentoumushi_voice, A1_05_9_kamakiri_voice,
			A1_05_10_tonbo_voice, A1_05_11_cyou_voice, A1_05_12_hachi_voice,
			A1_05_13_semi, A1_05_13_semi_voice, A1_05_14_ari_voice,
			A1_05_15_dangomushi_voice, A1_05_16_kamikirimushi,
			A1_05_16_kamikirimushi_voice, A1_05_17_koganemushi_voice,
			A1_05_18_youtyu_voice, A1_05_19_hotaru_voice, A1_05_20_matsumushi,
			A1_05_20_matsumushi_voice, A1_05_21_suzumushi,
			A1_05_21_suzumushi_voice, A1_05_22_kuwagatamushi_voice,
			A1_05_23_kabutomushi_voice, A1_05_za, A1_06_syu2,
			A1_07_kirigi_byon, A1_08_pomi, A1_09_kamakiri,
			A1_medamaya1_10_powanaki, A1_11_basa, A1_12_bun, A1_13_kasakasa,
			A1_15_dangomushi, A1_17_18_kasa2, A1_19_kira5, A1_20_matsumushi,
			A1_21_suzumushi, A1_22_23_kuwagata;

	private float arrPointtentoumushi[][] = { { 10, 80, 80, 10, 10 },
			{ 10, 100, 360, 360, 10 } };

	private float arrPointTree[][] = {
			{ 620, 960, 960, 900, 900, 800, 810, 665, 620 },
			{ 0, 0, 135, 120, 520, 520, 145, 80, 0 } };

	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("mushinokoe/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("mushinokoe/gfx/");

		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "mushinokoe/gfx/");

		Log.d(TAG, "onCreateResources : end");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources: ");

		// Lib
		ttpMushinokoe1 = mTexturePackLoaderFromSource.load("mushinokoe1.xml");
		ttpMushinokoe1.loadTexture();
		ttpLibMushinokoe1 = ttpMushinokoe1.getTexturePackTextureRegionLibrary();

		ttpMushinokoe2 = mTexturePackLoaderFromSource.load("mushinokoe2.xml");
		ttpMushinokoe2.loadTexture();
		ttpLibMushinokoe2 = ttpMushinokoe2.getTexturePackTextureRegionLibrary();

		ttpMushinokoe3 = mTexturePackLoaderFromSource.load("mushinokoe3.xml");
		ttpMushinokoe3.loadTexture();
		ttpLibMushinokoe3 = ttpMushinokoe3.getTexturePackTextureRegionLibrary();

		ttpMushinokoe4 = mTexturePackLoaderFromSource.load("mushinokoe4.xml");
		ttpMushinokoe4.loadTexture();
		ttpLibMushinokoe4 = ttpMushinokoe4.getTexturePackTextureRegionLibrary();

		ttpMushinokoe5 = mTexturePackLoaderFromSource.load("mushinokoe5.xml");
		ttpMushinokoe5.loadTexture();
		ttpLibMushinokoe5 = ttpMushinokoe5.getTexturePackTextureRegionLibrary();

		// BackGround
		// Trua
		this.ttrBackGround1 = ttpLibMushinokoe1
				.get(Vol3MushinokoeResource.A1_04_1_1_IPHONE_HAIKEI_HIRU_ID);

		// Toi
		this.ttrBackGround2 = ttpLibMushinokoe1
				.get(Vol3MushinokoeResource.A1_04_2_IPHONE_HAIKEI_YORU_ID);

		// Sang
		this.ttrBackGround3 = ttpLibMushinokoe1
				.get(Vol3MushinokoeResource.A1_04_3_IPHONE_HAIKEI_ASA_ID);

		// back ground
		this.ttrBackGround4 = ttpLibMushinokoe5
				.get(Vol3MushinokoeResource.A1_04_1_2_IPHONE_HAIKEI_HIRU_ID);

		// Mushikago
		this.ttrMushikago = ttpLibMushinokoe5
				.get(Vol3MushinokoeResource.A1_05_1_IPHONE_MUSHIKAGO_ID);

		// Sau buoi sang
		// Kuwagata
		this.tttrKuwagata = getTiledTextureFromPacker(ttpMushinokoe4,
				Vol3MushinokoeResource.A1_22_1_IPHONE_KUWAGATA_ID,
				Vol3MushinokoeResource.A1_22_2_IPHONE_KUWAGATA_ID);

		this.ttrnameOneKuwagata = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_22_3_IPHONE_KUWAGATA_ID);
		this.ttrOneKuwagata4 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_22_4_IPHONE_KUWAGATA_ID);
		this.ttrOneKuwagata5 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_22_5_IPHONE_KUWAGATA_ID);
		// Kabuto
		this.tttrKabuto = getTiledTextureFromPacker(ttpMushinokoe4,
				Vol3MushinokoeResource.A1_23_1_IPHONE_KABUTO_ID,
				Vol3MushinokoeResource.A1_23_2_IPHONE_KABUTO_ID);

		this.ttrnameOneKabuto = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_23_3_IPHONE_KABUTO_ID);
		this.ttrOneKabuto4 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_23_4_IPHONE_KABUTO_ID);
		this.ttrOneKabuto5 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_23_5_IPHONE_KABUTO_ID);
		// Sau buoi trua
		// Kamakiri
		this.ttrKamakiri1 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_09_1_IPHONE_KAMAKIRI_ID);
		this.ttrKamakiri2 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_09_A_IPHONE_KAMAKIRI_ID);
		this.ttrKamakiri3 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_09_B_IPHONE_KAMAKIRI_ID);

		this.ttrnameKamakiri = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_09_4_IPHONE_KAMAKIRI_ID);

		this.ttrKamakiri5 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_09_5_IPHONE_KAMAKIRI_ID);

		// Kirigirisu
		this.tttrKirigirisu = getTiledTextureFromPacker(ttpMushinokoe2,
				Vol3MushinokoeResource.A1_07_1_IPHONE_KIRIGIRISU_ID,
				Vol3MushinokoeResource.A1_07_2_IPHONE_KIRIGIRISU_ID,
				Vol3MushinokoeResource.A1_07_3_IPHONE_KIRIGIRISU_ID);
		this.ttrKirigirisu = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_07_4_IPHONE_KIRIGIRISU_ID);

		this.ttrnameKirigirisu = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_07_5_IPHONE_KIRIGIRISU_ID);
		this.ttrKirigirisu6 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_07_6_IPHONE_KIRIGIRISU_ID);

		// Tentoumushi
		this.tttrTentoumushi = getTiledTextureFromPacker(ttpMushinokoe2,
				Vol3MushinokoeResource.A1_08_1_IPHONE_TENTOUMUSHI_ID,
				Vol3MushinokoeResource.A1_08_2_IPHONE_TENTOUMUSHI_ID);
		this.ttrnameTentoumushi = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_08_3_IPHONE_TENTOUMUSHI_ID);

		this.ttrTentoumushi4 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_08_4_IPHONE_TENTOUMUSHI_ID);

		// Tonbo
		this.tttrTonbo = getTiledTextureFromPacker(ttpMushinokoe2,
				Vol3MushinokoeResource.A1_10_1_IPHONE_TONBO_ID,
				Vol3MushinokoeResource.A1_10_2_IPHONE_TONBO_ID);

		this.ttrTonbo3 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_10_3_IPHONE_TONBO_ID);
		this.ttrnameTonbo = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_10_4_IPHONE_TONBO_ID);
		this.ttrTonbo5 = ttpLibMushinokoe2
				.get(Vol3MushinokoeResource.A1_10_5_IPHONE_TONBO_ID);

		// Cyou
		this.ttrCyou1 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_11_1_IPHONE_CYOU_ID);

		this.tttrCyou = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_11_2_IPHONE_CYOU_ID,
				Vol3MushinokoeResource.A1_11_3_IPHONE_CYOU_ID);

		this.ttrnameCyou = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_11_4_IPHONE_CYOU_ID);
		this.ttrCyou5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_11_5_IPHONE_CYOU_ID);
		this.ttrCyou6 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_11_6_IPHONE_CYOU_ID);

		// Hachi
		this.ttrHachi1 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_1_IPHONE_HACHI_ID);
		this.ttrHachi2 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_2_IPHONE_HACHI_ID);
		this.ttrHachi3 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_2_1_IPHONE_HACHI_ID);
		this.ttrHachi4 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_3_IPHONE_HACHI_ID);
		this.ttrHachi5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_4_IPHONE_HACHI_ID);
		this.ttrnameHachi = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_6_IPHONE_HACHI_ID);
		this.ttrHachi7 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_12_7_IPHONE_HACHI_ID);

		// Semi
		this.ttrSemi1 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_1_IPHONE_SEMI_ID);
		this.ttrSemi2 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_2_IPHONE_SEMI_ID);
		this.ttrSemi3 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_3_IPHONE_SEMI_ID);
		this.ttrnameSemi = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_4_IPHONE_SEMI_ID);
		this.ttrSemi5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_5_IPHONE_SEMI_ID);
		this.ttrSemi6 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_13_6_IPHONE_SEMI_ID);

		// Ari
		this.tttrAri = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_14_1_IPHONE_ARI_ID,
				Vol3MushinokoeResource.A1_14_2_IPHONE_ARI_ID,
				Vol3MushinokoeResource.A1_14_3_IPHONE_ARI_ID);

		this.ttrnameAri = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_14_4_IPHONE_ARI_ID);

		this.ttrAri5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_14_5_IPHONE_ARI_ID);

		// Dango
		this.tttrDango = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_15_1_IPHONE_DANGO_ID,
				Vol3MushinokoeResource.A1_15_2_IPHONE_DANGO_ID,
				Vol3MushinokoeResource.A1_15_3_IPHONE_DANGO_ID);
		this.ttrnameDango = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_15_4_IPHONE_DANGO_ID);
		this.ttrDango5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_15_5_IPHONE_DANGO_ID);

		// Kamikiri
		this.tttrKamikiri = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_16_1_IPHONE_KAMIKIRI_ID,
				Vol3MushinokoeResource.A1_16_2_IPHONE_KAMIKIRI_ID);
		this.ttrnameKamikiri = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_16_3_IPHONE_KAMIKIRI_ID);
		this.ttrKamikiri4 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_16_4_IPHONE_KAMIKIRI_ID);

		// Kogane
		this.tttrKogane = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_17_1_IPHONE_KOGANE_ID,
				Vol3MushinokoeResource.A1_17_2_IPHONE_KOGANE_ID,
				Vol3MushinokoeResource.A1_17_3_IPHONE_KOGANE_ID);

		this.ttrnameKogane = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_17_4_IPHONE_KOGANE_ID);
		this.ttrKogane5 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_17_5_IPHONE_KOGANE_ID);

		// Youtyu
		this.tttrYoutyu = getTiledTextureFromPacker(ttpMushinokoe3,
				Vol3MushinokoeResource.A1_18_1_IPHONE_YOUTYU_ID,
				Vol3MushinokoeResource.A1_18_2_IPHONE_YOUTYU_ID,
				Vol3MushinokoeResource.A1_18_3_IPHONE_YOUTYU_ID,
				Vol3MushinokoeResource.A1_18_4_IPHONE_YOUTYU_ID);
		this.ttrnameYoutyu = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_18_5_IPHONE_YOUTYU_ID);
		this.ttrYoutyu6 = ttpLibMushinokoe3
				.get(Vol3MushinokoeResource.A1_18_6_IPHONE_YOUTYU_ID);

		// Sau buoi toi
		// Hotaru
		this.ttrHotaru1 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_19_1_IPHONE_HOTARU_ID);
		this.ttrHotaru2 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_19_2_IPHONE_HOTARU_ID);
		this.ttrHotaru3 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_19_3_IPHONE_HOT_ID);
		this.ttrnameHotaru = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_19_4_IPHONE_HOTARU_ID);
		this.ttrHotaru5 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_19_5_IPHONE_HOTARU_ID);

		// Matsumushi
		this.ttrMatsumushi1 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_20_1_IPHONE_MATSUMUSHI_ID);
		this.tttrMatsumushi = getTiledTextureFromPacker(ttpMushinokoe4,
				Vol3MushinokoeResource.A1_20_2_IPHONE_MATSUMUSHI_ID,
				Vol3MushinokoeResource.A1_20_3_IPHONE_MATSUMUSHI_ID);
		this.ttrnameMatsumushi = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_20_4_IPHONE_MATSUMUSHI_ID);
		this.ttrMatsumuchi5 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_20_5_IPHONE_MATSUMUSHI_ID);

		// Suzumushi
		this.ttrSuzumushi1 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_21_1_IPHONE_SUZUMUSHI_ID);
		this.tttrSuzumushi = getTiledTextureFromPacker(ttpMushinokoe4,
				Vol3MushinokoeResource.A1_21_3_IPHONE_SUZUMUSHI_ID,
				Vol3MushinokoeResource.A1_21_2_IPHONE_SUZUMUSHI_ID);
		this.ttrnameSuzumushi = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_21_4_IPHONE_SUZUMUSHI_ID);
		this.ttrSuzumushi5 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_21_5_IPHONE_SUZUMUSHI_ID);

		// Hake
		this.ttrHake1 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_06_1_IPHONE_HAKE_ID);
		this.ttrHake2 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_06_2_IPHONE_HAKE_ID);
		this.ttrHake3 = ttpLibMushinokoe4
				.get(Vol3MushinokoeResource.A1_06_3_IPHONE_HAKE_ID);

		// Ami
		this.ttrAmi = ttpLibMushinokoe5
				.get(Vol3MushinokoeResource.A1_05_2_IPHONE_AMI_ID);
		Log.d(TAG, "loadKaraokeResources: end");
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		A1_04_harp = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_04_harp);
		A1_05_7_kirigilis_long = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_7_kirigilis_long);
		A1_05_7_kirigilis_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_7_kirigilis_voice);
		A1_05_8_tentoumushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_8_tentoumushi_voice);
		A1_05_9_kamakiri_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_9_kamakiri_voice);
		A1_05_10_tonbo_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_10_tonbo_voice);
		A1_05_11_cyou_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_11_cyou_voice);
		A1_05_12_hachi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_12_hachi_voice);
		A1_05_13_semi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_13_semi);
		A1_05_13_semi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_13_semi_voice);
		A1_05_14_ari_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_14_ari_voice);
		A1_05_15_dangomushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_15_dangomushi_voice);
		A1_05_16_kamikirimushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_16_kamikirimushi);
		A1_05_16_kamikirimushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_16_kamikirimushi_voice);
		A1_05_17_koganemushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_17_koganemushi_voice);
		A1_05_18_youtyu_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_18_youtyu_voice);
		A1_05_19_hotaru_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_19_hotaru_voice);
		A1_05_20_matsumushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_20_matsumushi);
		A1_05_20_matsumushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_20_matsumushi_voice);
		A1_05_21_suzumushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_21_suzumushi);
		A1_05_21_suzumushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_21_suzumushi_voice);
		A1_05_22_kuwagatamushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_22_kuwagatamushi_voice);
		A1_05_23_kabutomushi_voice = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_23_kabutomushi_voice);
		A1_05_za = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_05_za);
		A1_06_syu2 = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_06_syu2);
		A1_07_kirigi_byon = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_07_kirigi_byon);
		A1_08_pomi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_08_pomi);
		A1_09_kamakiri = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_09_kamakiri);
		A1_medamaya1_10_powanaki = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_medamaya1_10_powanaki);
		A1_11_basa = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_11_basa);
		A1_12_bun = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_12_bun);
		A1_13_kasakasa = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_13_kasakasa);
		A1_15_dangomushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_15_dangomushi);
		A1_17_18_kasa2 = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_17_18_kasa2);
		A1_19_kira5 = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_19_kira5);
		A1_20_matsumushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_20_matsumushi);
		A1_21_suzumushi = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_21_suzumushi);
		A1_22_23_kuwagata = loadSoundResourceFromSD(Vol3MushinokoeResource.A1_22_23_kuwagata);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();
		// Trua
		sBackGround1 = new Sprite(0, 0, ttrBackGround1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackGround1);
		sBackGround1.setVisible(false);

		// Toi
		sBackGround2 = new Sprite(-999, -999, ttrBackGround2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackGround2);
		sBackGround2.setVisible(false);

		// Sang
		sBackGround3 = new Sprite(0, 0, ttrBackGround3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackGround3);
		sBackGround3.setVisible(false);

		// Kamariki
		this.sKamakiri1 = new Sprite(-999, -999, ttrKamakiri1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamakiri1);

		this.sKamakiri2 = new Sprite(155, 82, ttrKamakiri2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamakiri2);
		sKamakiri2.setVisible(false);

		this.sKamakiri3 = new Sprite(155, 82, ttrKamakiri3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamakiri3);
		sKamakiri3.setVisible(false);

		sBackGround4 = new Sprite(0, 0, ttrBackGround4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackGround4);
		sBackGround4.setVisible(true);

		// Ari
		this.animAri = new AnimatedSprite(-999, -999, tttrAri,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animAri);

		// Dango
		this.animDango = new AnimatedSprite(-999, -999, tttrDango,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animDango);

		// Mushikago
		sMushikago = new Sprite(222, 250, ttrMushikago,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMushikago);
		sMushikago.setVisible(true);

		// Hake3
		sHake3 = new Sprite(761, 193, ttrHake3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHake3);
		sHake3.setVisible(false);

		// Sau buoi sang
		// anim Kawagata
		this.animKuwagata = new AnimatedSprite(-999, -999, tttrKuwagata,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animKuwagata);

		this.sOneKugawata5 = new Sprite(325, 371, ttrOneKuwagata5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOneKugawata5);
		sOneKugawata5.setVisible(false);

		this.sOneKugawata4 = new Sprite(-999, -999, ttrOneKuwagata4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOneKugawata4);

		// anim Kabuto
		this.animKabuto = new AnimatedSprite(-999, -999, tttrKabuto,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animKabuto);

		this.sOneKabuto5 = new Sprite(325, 371, ttrOneKabuto5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOneKabuto5);
		sOneKabuto5.setVisible(false);

		this.sOneKabuto4 = new Sprite(-999, -999, ttrOneKabuto4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sOneKabuto4);

		// Sau buoi trua
		// Kamakiri name

		this.sKamakiri5 = new Sprite(-999, -999, ttrKamakiri5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamakiri5);

		// sKirigirisu
		this.sKirigirisu6 = new Sprite(-999, -999, ttrKirigirisu6,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKirigirisu6);

		this.animKirigirisu = new AnimatedSprite(-999, -999, tttrKirigirisu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animKirigirisu);

		this.sKirigirisu = new Sprite(-59, 428, ttrKirigirisu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKirigirisu);
		sKirigirisu.setVisible(false);

		// Tentoumushi
		this.animTentoumushi = new AnimatedSprite(-999, -999, tttrTentoumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animTentoumushi);

		this.sTentoumushi4 = new Sprite(-999, -999, ttrTentoumushi4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTentoumushi4);

		// Tonbo
		this.animTonbo = new AnimatedSprite(-999, -999, tttrTonbo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animTonbo);

		this.sTonbo3 = new Sprite(162, 0, ttrTonbo3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTonbo3);
		sTonbo3.setVisible(false);

		this.sTonbo5 = new Sprite(-999, -999, ttrTonbo5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTonbo5);

		// Semi
		this.sSemi1 = new Sprite(-999, -999, ttrSemi1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSemi1);

		this.sSemi2 = new Sprite(649, 74, ttrSemi2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSemi2);
		sSemi2.setVisible(false);

		this.sSemi6 = new Sprite(325, 371, ttrSemi6,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSemi6);
		sSemi6.setVisible(false);

		this.sSemi5 = new Sprite(-999, -999, ttrSemi5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSemi5);

		// Cyou
		this.sCyou1 = new Sprite(-999, -999, ttrCyou1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCyou1);

		this.sCyou6 = new Sprite(325, 371, ttrCyou6,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCyou6);
		sCyou6.setVisible(false);

		this.sCyou5 = new Sprite(-999, -999, ttrCyou5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sCyou5);

		// Ari name
		this.sAri5 = new Sprite(-999, -999, ttrAri5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sAri5);

		// Hachi
		this.sHachi1 = new Sprite(-999, -999, ttrHachi1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi1);

		this.sHachi2 = new Sprite(580, 17, ttrHachi2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi2);
		sHachi2.setVisible(false);

		this.sHachi3 = new Sprite(580, 17, ttrHachi3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi3);
		sHachi3.setVisible(false);

		this.sHachi7 = new Sprite(-999, -999, ttrHachi7,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi7);

		// Dango name

		this.sDango5 = new Sprite(-999, -999, ttrDango5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sDango5);

		// Kamikiri
		this.animKamikiri = new AnimatedSprite(-999, -999, tttrKamikiri,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animKamikiri);

		this.sKamikiri4 = new Sprite(-999, -999, ttrKamikiri4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKamikiri4);

		// Kogane
		this.animKogane = new AnimatedSprite(-999, -999, tttrKogane,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animKogane);

		this.sKogane5 = new Sprite(-999, -999, ttrKogane5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKogane5);

		// Youtyu
		this.animYoutyu = new AnimatedSprite(-999, -999, tttrYoutyu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animYoutyu);

		this.sYoutyu6 = new Sprite(-999, -999, ttrYoutyu6,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sYoutyu6);

		// Sau buoi toi
		// Hotaru
		sHotaru1 = new Sprite(-999, -999, ttrHotaru1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHotaru1);

		sHotaru2 = new Sprite(87, 507, ttrHotaru2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHotaru2);
		sHotaru2.setVisible(false);

		sHotaru3 = new Sprite(0, 0, ttrHotaru3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHotaru3);
		sHotaru3.setVisible(false);

		sHotaru5 = new Sprite(-999, -999, ttrHotaru5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHotaru5);

		// Matsumushi

		sMatsumushi1 = new Sprite(-999, -999, ttrMatsumushi1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMatsumushi1);

		animMatsumushi = new AnimatedSprite(141, 391, tttrMatsumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animMatsumushi);
		animMatsumushi.setVisible(false);

		sMatsumushi5 = new Sprite(-999, -999, ttrMatsumuchi5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sMatsumushi5);

		// Suzumushi
		sSuzumushi1 = new Sprite(-999, -999, ttrSuzumushi1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSuzumushi1);

		animSuzumushi = new AnimatedSprite(54, 361, tttrSuzumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animSuzumushi);
		animSuzumushi.setVisible(false);

		sSuzumushi5 = new Sprite(-999, -999, ttrSuzumushi5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSuzumushi5);

		// Hake
		sHake1 = new Sprite(-999, -999, ttrHake1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHake1);
		sHake2 = new Sprite(761, 193, ttrHake2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHake2);
		sHake2.setVisible(false);

		// Name
		this.snameOneKugawata = new Sprite(344, 300, ttrnameOneKuwagata,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameOneKugawata);
		snameOneKugawata.setVisible(false);

		this.snameKamakiri = new Sprite(344, 300, ttrnameKamakiri,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameKamakiri);
		snameKamakiri.setVisible(false);

		this.snameOneKabuto = new Sprite(344, 300, ttrnameOneKabuto,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameOneKabuto);
		snameOneKabuto.setVisible(false);

		this.snameKirigirisu = new Sprite(344, 300, ttrnameKirigirisu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameKirigirisu);
		snameKirigirisu.setVisible(false);

		this.snameTentoumushi = new Sprite(344, 300, ttrnameTentoumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameTentoumushi);
		snameTentoumushi.setVisible(false);

		this.snameTonbo = new Sprite(344, 300, ttrnameTonbo,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameTonbo);
		snameTonbo.setVisible(false);

		this.snameSemi = new Sprite(344, 300, ttrnameSemi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameSemi);
		snameSemi.setVisible(false);

		this.snameCyou = new Sprite(344, 300, ttrnameCyou,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameCyou);
		snameCyou.setVisible(false);

		this.snameAri = new Sprite(344, 300, ttrnameAri,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameAri);
		snameAri.setVisible(false);

		this.snameHachi = new Sprite(344, 300, ttrnameHachi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameHachi);
		snameHachi.setVisible(false);

		this.snameDango = new Sprite(344, 300, ttrnameDango,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameDango);
		snameDango.setVisible(false);

		this.snameKamikiri = new Sprite(344, 300, ttrnameKamikiri,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameKamikiri);
		snameKamikiri.setVisible(false);

		this.snameKogane = new Sprite(344, 300, ttrnameKogane,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameKogane);
		snameKogane.setVisible(false);

		this.snameYoutyu = new Sprite(344, 300, ttrnameYoutyu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameYoutyu);
		snameYoutyu.setVisible(false);

		snameHotaru = new Sprite(344, 300, ttrnameHotaru,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameHotaru);
		snameHotaru.setVisible(false);

		snameMatsumushi = new Sprite(344, 300, ttrnameMatsumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameMatsumushi);
		snameMatsumushi.setVisible(false);

		snameSuzumushi = new Sprite(344, 300, ttrnameSuzumushi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(snameSuzumushi);
		snameSuzumushi.setVisible(false);

		// Ami
		sAmi = new Sprite(589, 173, ttrAmi, this.getVertexBufferObjectManager());
		mScene.attachChild(sAmi);
		sAmi.setVisible(true);

		// Hachi Fly
		this.sHachi4 = new Sprite(-999, -999, ttrHachi4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi4);

		this.sHachi5 = new Sprite(-999, -999, ttrHachi5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHachi5);

		this.sSemi3 = new Sprite(-999, -999, ttrSemi3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sSemi3);

		// Cyou fly
		this.animCyou = new AnimatedSprite(-999, -999, tttrCyou,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(animCyou);

		// Shape Ami
		shapeAmi = new Rectangle(40, 40, 1, 1,
				this.getVertexBufferObjectManager());
		shapeAmi.setAlpha((float) 0.0);
		sAmi.attachChild(shapeAmi);

		// Shape Kuwagata
		shapeKuwagata = new Rectangle(20, 20, 50, 60,
				this.getVertexBufferObjectManager());
		shapeKuwagata.setAlpha((float) 0.0);
		animKuwagata.attachChild(shapeKuwagata);

		// Shape Kabuto
		shapeKabuto = new Rectangle(25, 40, 35, 50,
				this.getVertexBufferObjectManager());
		shapeKabuto.setAlpha((float) 0.0);
		animKabuto.attachChild(shapeKabuto);

		// Shape Kirigirisu
		shapeKirigirisu = new Rectangle(155, 80, 75, 85,
				this.getVertexBufferObjectManager());
		shapeKirigirisu.setAlpha((float) 0.0);
		animKirigirisu.attachChild(shapeKirigirisu);

		// //Shape Tentoumushi
		shapeTentoumushi = new Rectangle(15, 65, 75, 235,
				this.getVertexBufferObjectManager());
		shapeTentoumushi.setAlpha((float) 0.0);
		animTentoumushi.attachChild(shapeTentoumushi);

		// Shape Kamakiri
		shapeKamakiri = new Rectangle(60, 55, 60, 160,
				this.getVertexBufferObjectManager());
		shapeKamakiri.setAlpha((float) 0.0);
		sKamakiri1.attachChild(shapeKamakiri);

		// Shape Tonbo
		shapeTonbo = new Rectangle(50, 30, 90, 60,
				this.getVertexBufferObjectManager());
		shapeTonbo.setAlpha((float) 0.0);
		animTonbo.attachChild(shapeTonbo);

		// Shape Cyou
		shapeCyou1 = new Rectangle(25, 25, 35, 65,
				this.getVertexBufferObjectManager());
		shapeCyou1.setAlpha((float) 0.0);
		sCyou1.attachChild(shapeCyou1);

		shapeCyou2 = new Rectangle(25, 30, 35, 45,
				this.getVertexBufferObjectManager());
		shapeCyou2.setAlpha((float) 0.0);
		animCyou.attachChild(shapeCyou2);

		// Shape Hachi
		shapeHachi1 = new Rectangle(110, 30, 40, 40,
				this.getVertexBufferObjectManager());
		shapeHachi1.setAlpha((float) 0.0);
		sHachi1.attachChild(shapeHachi1);

		shapeHachi2 = new Rectangle(25, 25, 40, 25,
				this.getVertexBufferObjectManager());
		shapeHachi2.setAlpha((float) 0.0);
		sHachi4.attachChild(shapeHachi2);

		shapeHachi3 = new Rectangle(20, 20, 40, 30,
				this.getVertexBufferObjectManager());
		shapeHachi3.setAlpha((float) 0.0);
		sHachi5.attachChild(shapeHachi3);

		// Shape Semi
		shapeSemi1 = new Rectangle(145, 50, 35, 100,
				this.getVertexBufferObjectManager());
		shapeSemi1.setAlpha((float) 0.0);
		sSemi1.attachChild(shapeSemi1);

		shapeSemi2 = new Rectangle(25, 55, 60, 45,
				this.getVertexBufferObjectManager());
		shapeSemi2.setAlpha((float) 0.0);
		sSemi3.attachChild(shapeSemi2);

		// Shape Ari
		shapeAri = new Rectangle(42, 42, 80, 60,
				this.getVertexBufferObjectManager());
		shapeAri.setAlpha((float) 0.0);
		animAri.attachChild(shapeAri);

		// Shape Dango
		shapeDango = new Rectangle(40, 50, 73, 47,
				this.getVertexBufferObjectManager());
		shapeDango.setAlpha((float) 0.0);
		animDango.attachChild(shapeDango);

		// Shape Kamikiri
		shapeKamikiri = new Rectangle(20, 60, 35, 60,
				this.getVertexBufferObjectManager());
		shapeKamikiri.setAlpha((float) 0.0);
		animKamikiri.attachChild(shapeKamikiri);

		// Kogane
		shapeKogane = new Rectangle(40, 55, 50, 40,
				this.getVertexBufferObjectManager());
		shapeKogane.setAlpha((float) 0.0);
		animKogane.attachChild(shapeKogane);

		// Youtyu
		shapeYoutyu = new Rectangle(60, 35, 70, 45,
				this.getVertexBufferObjectManager());
		shapeYoutyu.setAlpha((float) 0.0);
		animYoutyu.attachChild(shapeYoutyu);

		// Hotaru
		shapeHotaru = new Rectangle(40, 40, 65, 40,
				this.getVertexBufferObjectManager());
		shapeHotaru.setAlpha((float) 0.0);
		sHotaru1.attachChild(shapeHotaru);

		// Matsumushi
		shapeMatsumushi = new Rectangle(30, 60, 70, 35,
				this.getVertexBufferObjectManager());
		shapeMatsumushi.setAlpha((float) 0.0);
		sMatsumushi1.attachChild(shapeMatsumushi);

		// Suzumushi
		shapeSuzumushi = new Rectangle(19, 57, 61, 43,
				this.getVertexBufferObjectManager());
		shapeSuzumushi.setAlpha((float) 0.0);
		sSuzumushi1.attachChild(shapeSuzumushi);

		addGimmicsButton(mScene,
				Vol3MushinokoeResource.SOUND_GIMMIC_MUSHINOKOE,
				Vol3MushinokoeResource.IMAGE_GIMMIC_MUSHINOKOE,
				ttpLibMushinokoe5);
		mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene: end ");
	}

	float hours;

	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame: ");
		initial();
		// mCalendar.setToNow();
		vibrateAmi();
		// hours = mCalendar.get(Calendar.HOUR);
		// Log.d(TAG, "hours : " + hours);

		mCalendar = new GregorianCalendar();

		if (mCalendar.get(Calendar.AM_PM) == 0) {
			hours = mCalendar.get(Calendar.HOUR);
			Log.d(TAG, "hours IF : " + hours);
		} else {
			hours = mCalendar.get(Calendar.HOUR) + 12;
			Log.d(TAG, "hours ELSE : " + hours);
		}

		// Sang
		if (3 <= hours && hours <= 7)
			cases = 3;
		// Trua
		if (8 <= hours && hours <= 17)
			cases = 1;
		// Toi
		if (18 <= hours && hours <= 23)
			cases = 2;

		if (0 <= hours && hours <= 2)
			cases = 2;

		/*
		 * // Sang if (3 <= hours && hours < 8) cases = 3; // Trua if (8 <=
		 * hours && hours < 18) cases = 1; // Toi if (18 <= hours && hours <=
		 * 23) cases = 2;
		 * 
		 * if (23 <= hours && hours < 3) cases = 2;
		 */
		Log.d(TAG, "case co loi hay khong " + cases);
		switch (cases) {
		case 1:
			initialNoon();
			visibleTrueNoon();
			current = 1;
			break;
		case 2:
			initialNight();
			visibleTrueNight();
			sBackGround4.setVisible(false);
			current = 2;
			break;
		case 3:
			initialMorning();
			visibleTrueMorning();
			sBackGround4.setVisible(false);
			current = 3;
			break;
		default:
			break;
		}

		Log.d(TAG, "onResumeGame: end ");
		super.onResumeGame();
	}

	private void initial() {
		isAmiMove = false;
		isCatch = false;
		checkCyouFly = 0;
		checkKamakiri = 0;
		checkSemi = 0;
		checkAri = 0;
		checkDango = 0;
		checkKamikiri = 0;
		checkTentoumushi = 0;
		checkKogane = 0;
		checkYoutyu = 0;

		enableCatch = true;

		notCatchKuwagata = true;
		notCatchKabuto = true;
		notCatchKirigirisu = true;
		notCatchTentoumushi = true;
		notCatchKamakiri = true;
		notCatchTonbo = true;
		notCatchCyou = true;
		notCatchHachi = true;
		notCatchSemi = true;
		notCatchAri = true;
		notCatchDango = true;
		notCatchKamikiri = true;
		notCatchKogane = true;
		notCatchYoutyu = true;
		notCatchHotaru = true;
		notCatchMatsumushi = true;
		notCatchSuzumushi = true;

		isKuwagataJump = true;
		isKabutoJump = true;
		isKirigirisuJump = true;
		isTentoumushiJump = true;
		isKamakiriJump = true;
		isTonboJump = true;
		isCyouJump = true;
		isHachiJump = true;
		isSemiJump = true;
		isAriJump = true;
		isDangoJump = true;
		isKamikiriJump = true;
		isKoganeJump = true;
		isYoutyuJump = true;
		isHotaruJump = true;
		isMatsumushiJump = true;
		isSuzumushiJump = true;

	}

	private void initialMorning() {
		isTouchKuwagata = true;
		isTouchKabuto = true;
	}

	private void initialNoon() {
		isTouchKirigirisu = true;
		isTouchKamakiri = true;
		isTouchTonbo = true;
		isTouchCyou = true;
		isTouchHachi = true;
		isTouchSemi = true;
		isTouchKamikiri = true;

		isFaceKabuto = false;
		isFaceKuwagata = false;
	}

	private void initialNight() {
		isTouchHotaru = true;
		isTouchMatsumushi = true;
		isTouchSuzumushi = true;
		isTouchHake = true;
		checkNight = true;
	}

	@Override
	public void combineGimmic3WithAction() {
		touchBackGround();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		// Action Down
		if (pSceneTouchEvent.isActionDown()) {
			// touch Background
			if (checkContains(sBackGround4, 360, 0, 630, 125, pX, pY)) {
				touchBackGround();
			}

			// touch tentoumushi
			if (checkContainsPolygon(animTentoumushi, arrPointtentoumushi, 5,
					pX, pY)) {
				touchTentoumushi(pTentoumushi);
				pTentoumushi++;
				if (pTentoumushi > 2) {
					animTentoumushi.setVisible(false);
					pTentoumushi = 0;
				}
			}

			// touch Ami
			if (sAmi.contains(pX, pY) && enableCatch) {
				mScene.unregisterUpdateHandler(tTimer);
				isAmiMove = true;
				isCatch = true;
			}

			// touch Kuwagata
			if (animKuwagata.contains(pX, pY) && isTouchKuwagata
					&& !isFaceKuwagata) {
				Log.d(TAG, "Touch Kuwagata");
				isTouchKuwagata = false;
				touchKuwagata(0);
			}

			if (animKuwagata.contains(pX, pY) && isTouchKuwagata
					&& isFaceKuwagata) {
				Log.d(TAG, "Touch Kuwagata");
				isTouchKuwagata = false;
				touchKuwagata(1);
			}

			// touch Kabuto
			if (animKabuto.contains(pX, pY) && isTouchKabuto && !isFaceKabuto) {
				Log.d(TAG, "Touch Kabuto");
				isTouchKabuto = false;
				touchKabuto(0);
			}
			if (animKabuto.contains(pX, pY) && isTouchKabuto && isFaceKabuto) {
				Log.d(TAG, "Touch Kabuto");
				isTouchKabuto = false;
				touchKabuto(1);
			}

			// touch Kirigirisu
			if (checkContains(animKirigirisu, 130, 70, 250, 185, pX, pY)
					&& isTouchKirigirisu) {
				enableCatch = false;
				Log.d(TAG, "Touch Kirigirisu");
				isTouchKirigirisu = false;
				touchKirigirisu(animKirigirisu, sKirigirisu);
			}

			// touch Kamakiri
			if (checkContains(sKamakiri1, 70, 30, 160, 215, pX, pY)
					&& isTouchKamakiri) {
				Log.d(TAG, "Touch Kamakiri");
				isTouchKamakiri = false;
				touchKamakiri(sKamakiri1, sKamakiri2);
			}

			// touchKamakiri
			if (checkContains(sKamakiri1, 0, 145, 60, 290, pX, pY)
					&& isTouchKamakiri) {
				Log.d(TAG, "Touch Kamakiri");
				isTouchKamakiri = false;
				touchKamakiri(sKamakiri1, sKamakiri3);
			}

			// touch Tonbo
			if (checkContains(animTonbo, 22, 3, 193, 102, pX, pY)
					&& isTouchTonbo) {
				Log.d(TAG, "Touch Tonbo");
				isTouchTonbo = false;
				touchTonbo();
			}

			// touch Cyou
			if (checkContains(sCyou1, 17, 12, 96, 100, pX, pY) && isTouchCyou) {
				Log.d(TAG, "Touch Cyou");
				isTouchCyou = false;
				touchCyou();
			}

			// touch Hachi
			if (checkContains(sHachi1, 91, 11, 166, 81, pX, pY) && isTouchHachi) {
				Log.d(TAG, "Touch Hachi");
				isTouchHachi = false;
				touchHachi();
			}

			// touch Semi
			if (checkContains(sSemi1, 135, 44, 198, 163, pX, pY) && isTouchSemi) {
				Log.d(TAG, "Touch Semi");
				isTouchSemi = false;
				touchSemi();
			}

			// touch Ari
			if (animAri.contains(pX, pY)) {
				pAri++;
				touchAri(pAri);
				if (pAri >= 2) {
					pAri = -1;
				}
			}

			// touch Dango
			if (checkContains(animDango, 17, 34, 131, 107, pX, pY)) {
				pDango++;
				touchDango(pDango);
				if (pDango >= 2) {
					pDango = -1;
				}
			}

			// touch Kamikiri
			if (checkContains(animKamikiri, 4, 49, 64, 125, pX, pY)
					&& isTouchKamikiri) {
				enableCatch = false;
				isTouchKamikiri = false;
				pKamikiri++;
				touchKamikiri(pKamikiri);
				if (pKamikiri >= 1) {
					pKamikiri = -1;
				}
			}

			// touch Kogane
			if (checkContains(animKogane, 20, 47, 106, 108, pX, pY)) {
				pKogane++;
				touchKogane(pKogane);
				if (pKogane >= 2) {
					pKogane = -1;
				}
			}

			// touch Youtyu
			if (checkContains(animYoutyu, 20, 21, 169, 97, pX, pY)) {
				pYoutyu++;
				touchYoutyu(pYoutyu);
				if (pYoutyu >= 3) {
					pYoutyu = -1;
				}
			}

			// touchHotaru
			if (checkContains(sHotaru1, 44, 16, 124, 82, pX, pY)
					&& isTouchHotaru) {
				Log.d(TAG, "Touch Hotaru");
				enableCatch = false;
				isTouchHotaru = false;
				touchHotaru();
			}

			// touchMatsumushi
			if (checkContains(sMatsumushi1, 10, 28, 134, 98, pX, pY)
					&& isTouchMatsumushi) {
				Log.d(TAG, "Touch Matsumushi");
				enableCatch = false;
				isTouchMatsumushi = false;
				touchMatsumushi();
			}

			// touchSuzumushi
			if (checkContains(sSuzumushi1, 4, 38, 114, 107, pX, pY)
					&& isTouchSuzumushi) {
				Log.d(TAG, "Touch Suzumushi");
				enableCatch = false;
				isTouchSuzumushi = false;
				touchSuzumushi();
			}

			// touchHake
			if (checkContains(sHake1, 16, 95, 67, 164, pX, pY)
					|| checkContainsPolygon(sBackGround2, arrPointTree, 9, pX,
							pY) && isTouchHake) {
				Log.d(TAG, "Touch Hake");
				isTouchHake = false;
				touchHake();
			}

			// Jump

			// Kabuto
			if (checkContains(sOneKabuto4, 39, 35, 244, 179, pX, pY)) {
				if (isKabutoJump) {
					A1_05_23_kabutomushi_voice.play();
					isKabutoJump = false;
					actionJump(sOneKabuto4);
					sOneKabuto4.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_23_kabutomushi_voice.stop();
									isKabutoJump = true;
								}
							}));
				}
			}

			// Kugawata
			if (checkContains(sOneKugawata4, 36, 48, 244, 175, pX, pY)) {
				// TODO TruongVuong
				if (isKuwagataJump) {
					A1_05_22_kuwagatamushi_voice.play();
					isKuwagataJump = false;
					actionJump(sOneKugawata4);
					sOneKugawata4.registerEntityModifier(new DelayModifier(
							1.1f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_22_kuwagatamushi_voice.stop();
									isKuwagataJump = true;
								}
							}));
				}
			}

			// Kirigirisu
			if (checkContains(sKirigirisu6, 37, 45, 279, 188, pX, pY)) {
				if (isKirigirisuJump) {
					A1_05_7_kirigilis_voice.play();
					isKirigirisuJump = false;
					actionJump(sKirigirisu6);
					sKirigirisu6.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_7_kirigilis_voice.stop();
									isKirigirisuJump = true;
								}
							}));
				}
			}

			// Tentoumushi
			if (checkContains(sTentoumushi4, 44, 99, 251, 180, pX, pY)) {
				if (isTentoumushiJump) {
					A1_05_8_tentoumushi_voice.play();
					isTentoumushiJump = false;
					actionJump(sTentoumushi4);

					sTentoumushi4.registerEntityModifier(new DelayModifier(
							1.1f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_8_tentoumushi_voice.stop();
									isTentoumushiJump = true;
								}
							}));
				}
			}

			// Kamakiri
			if (checkContains(sKamakiri5, 3, 61, 296, 195, pX, pY)) {
				if (isKamakiriJump) {
					A1_05_9_kamakiri_voice.play();
					isKamakiriJump = false;
					actionJump(sKamakiri5);
					sKamakiri5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_9_kamakiri_voice.stop();
									isKamakiriJump = true;
								}
							}));
				}
			}

			// Tonbo
			if (checkContains(sTonbo5, 33, 57, 261, 194, pX, pY)) {
				if (isTonboJump) {
					A1_05_10_tonbo_voice.play();
					isTonboJump = false;
					actionJump(sTonbo5);
					sTonbo5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_10_tonbo_voice.stop();
									isTonboJump = true;
								}
							}));
				}
			}

			// Cyou
			if (checkContains(sCyou5, 73, 13, 263, 194, pX, pY)) {
				if (isCyouJump) {
					A1_05_11_cyou_voice.play();
					isCyouJump = false;
					actionJump(sCyou5);
					sCyou5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {

								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_11_cyou_voice.stop();
									isCyouJump = true;
								}
							}));
				}
			}

			// Hachi
			if (checkContains(sHachi7, 47, 62, 264, 183, pX, pY)) {
				if (isHachiJump) {
					isHachiJump = false;
					A1_05_12_hachi_voice.play();
					actionJump(sHachi7);
					sHachi7.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_12_hachi_voice.stop();
									isHachiJump = true;
								}
							}));
				}
			}

			// Semi
			if (checkContains(sSemi5, 75, 31, 201, 168, pX, pY)) {
				if (isSemiJump) {
					A1_05_13_semi_voice.play();
					isSemiJump = false;
					actionJump(sSemi5);
					sSemi5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_13_semi_voice.stop();
									isSemiJump = true;
								}
							}));
				}
			}

			// Ari
			if (checkContains(sAri5, 39, 95, 252, 186, pX, pY)) {
				if (isAriJump) {
					A1_05_14_ari_voice.play();
					isAriJump = false;
					actionJump(sAri5);
					sAri5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_14_ari_voice.stop();
									isAriJump = true;
								}
							}));
				}
			}

			// Dango
			if (checkContains(sDango5, 73, 100, 243, 179, pX, pY)) {
				if (isDangoJump) {
					A1_05_15_dangomushi_voice.play();
					isDangoJump = false;
					actionJump(sDango5);
					sDango5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_15_dangomushi_voice.stop();
									isDangoJump = true;
								}
							}));
				}
			}

			// Kamikiri
			if (checkContains(sKamikiri4, 38, 114, 269, 196, pX, pY)) {
				if (isKamikiriJump) {
					A1_05_16_kamikirimushi_voice.play();
					isKamikiriJump = false;
					actionJump(sKamikiri4);
					sKamikiri4.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_16_kamikirimushi_voice.stop();
									isKamikiriJump = true;
								}
							}));
				}
			}

			// Kogane
			if (checkContains(sKogane5, 64, 90, 259, 192, pX, pY)) {
				if (isKoganeJump) {
					A1_05_17_koganemushi_voice.play();
					isKoganeJump = false;
					actionJump(sKogane5);
					sKogane5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_17_koganemushi_voice.stop();
									isKoganeJump = true;
								}
							}));
				}
			}

			// Youtyu
			if (checkContains(sYoutyu6, 40, 100, 255, 193, pX, pY)) {
				if (isYoutyuJump) {
					A1_05_18_youtyu_voice.play();
					isYoutyuJump = false;
					actionJump(sYoutyu6);
					sYoutyu6.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_18_youtyu_voice.stop();
									isYoutyuJump = true;
								}
							}));
				}
			}

			// Hotaru
			if (checkContains(sHotaru5, 27, 70, 259, 194, pX, pY)) {
				if (isHotaruJump) {
					A1_05_19_hotaru_voice.play();
					isHotaruJump = false;
					actionJump(sHotaru5);
					sHotaru5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_19_hotaru_voice.stop();
									isHotaruJump = true;
								}
							}));
				}
			}

			// Matsumushi
			if (checkContains(sMatsumushi5, 19, 83, 284, 196, pX, pY)) {
				if (isMatsumushiJump) {
					A1_05_20_matsumushi_voice.play();
					isMatsumushiJump = false;
					actionJump(sMatsumushi5);
					sMatsumushi5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_20_matsumushi_voice.stop();
									isMatsumushiJump = true;
								}
							}));
				}
			}

			// Suzumushi
			if (checkContains(sSuzumushi5, 58, 71, 247, 198, pX, pY)) {
				if (isSuzumushiJump) {
					A1_05_21_suzumushi_voice.play();
					isSuzumushiJump = false;
					actionJump(sSuzumushi5);
					sSuzumushi5.registerEntityModifier(new DelayModifier(1.1f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									A1_05_21_suzumushi_voice.stop();
									isSuzumushiJump = true;
								}
							}));
				}
			}

		}

		// Action up
		if (pSceneTouchEvent.isActionUp()) {
			// enableCatch = true;
			isCatch = false;
			isAmiMove = false;
			sAmi.setPosition(sAmi.getmXFirst(), sAmi.getmYFirst());
			vibrateAmi();
		}

		// Action Move

		if (pSceneTouchEvent.isActionMove()) {
			if (isAmiMove) {
				sAmi.setPosition(pX, pY);

				// Kabuto

				if (shapeAmi.collidesWith(shapeKabuto) && isCatch) {
					delayAmi();

					notCatchKabuto = false;

					notCatchKuwagata = true;
					notCatchKirigirisu = true;
					notCatchTentoumushi = true;
					notCatchKamakiri = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;
					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animKabuto.setPosition(-999, -999);

					animKuwagata.setPosition(801, 320);

					visibleTrueWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);

					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					// TODO TRUONG VUONG
					Log.d(TAG, "Da bat duoc Kabuto");

					showNight();
				}

				// Kuwagata

				if (shapeAmi.collidesWith(shapeKuwagata) && isCatch) {
					delayAmi();

					notCatchKuwagata = false;

					notCatchKabuto = true;
					notCatchKirigirisu = true;
					notCatchTentoumushi = true;
					notCatchKamakiri = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animKuwagata.setPosition(-999, -999);

					animKabuto.setPosition(801, 205);

					visibleTrueWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc Kuwagata");
					showNight();
				}

				// Kirigirisu

				if (shapeAmi.collidesWith(shapeKirigirisu) && isCatch) {
					delayAmi();
					A1_05_7_kirigilis_long.play();
					enableCatch = false;

					isKirigirisuJump = false;

					notCatchKirigirisu = false;

					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchTentoumushi = true;
					notCatchKamakiri = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animKirigirisu.setPosition(-999, -999);
					sKirigirisu.setVisible(false);

					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameKirigirisu, sKirigirisu6);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc Kirigirisu");

					// TODO
					sKirigirisu6.registerEntityModifier(new DelayModifier(2.0f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									Log.d(TAG, "Bat tiep di");
									A1_05_7_kirigilis_long.stop();
									isCatch = true;
									isKirigirisuJump = true;
									enableCatch = true;
								}
							}));
				}

				// Tentoumushi

				if (shapeAmi.collidesWith(shapeTentoumushi) && isCatch) {
					delayAmi();
					checkTentoumushi = 0;

					notCatchTentoumushi = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animTentoumushi.setPosition(-999, -999);

					delayKirigirisu();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameTentoumushi, sTentoumushi4);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc Tentoumushi");
				}

				// Kamakiri

				if (shapeAmi.collidesWith(shapeKamakiri) && isCatch) {
					delayAmi();
					checkKamakiri = 0;

					notCatchKamakiri = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					sKamakiri1.setPosition(-999, -999);
					sKamakiri1.setVisible(true);
					sKamakiri2.setVisible(false);
					sKamakiri3.setVisible(false);

					delayKirigirisu();
					delayTentoumushi();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameKamakiri, sKamakiri5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc Kamakiri");
				}

				// Tonbo
				if (shapeAmi.collidesWith(shapeTonbo) && isCatch) {
					delayAmi();

					notCatchTonbo = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animTonbo.setPosition(-999, -999);
					sTonbo3.setVisible(false);
					isTouchTonbo = true;

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameTonbo, sTonbo5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc Tonbo");
				}

				// Cyou

				if (shapeAmi.collidesWith(shapeCyou1)
						|| shapeAmi.collidesWith(shapeCyou2) && isCatch) {
					delayAmi();
					checkCyouFly = 0;

					notCatchCyou = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;

					notCatchYoutyu = true;
					notCatchHotaru = true;
					notCatchMatsumushi = true;

					animCyou.clearEntityModifiers();
					sCyou1.setPosition(-999, -999);
					animCyou.setPosition(-999, -999);
					isTouchCyou = true;
					notCatchYoutyu = true;
					notCatchSuzumushi = true;

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm3(snameCyou, sCyou5, sCyou6);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					Log.d(TAG, "Da bat duoc cyou");
				}

				// Hachi

				if (shapeAmi.collidesWith(shapeHachi1)
						|| shapeAmi.collidesWith(shapeHachi2)
						|| shapeAmi.collidesWith(shapeHachi3) && isCatch) {
					delayAmi();

					notCatchHachi = false;
					A1_12_bun.stop();

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					sHachi4.clearEntityModifiers();
					sHachi5.clearEntityModifiers();
					sHachi1.clearEntityModifiers();
					sHachi2.clearEntityModifiers();
					sHachi3.clearEntityModifiers();

					sHachi1.setPosition(-999, -999);
					sHachi1.setVisible(true);
					sHachi2.setVisible(false);
					sHachi3.setVisible(false);
					sHachi4.setPosition(-999, -999);
					sHachi5.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameHachi, sHachi7);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);
				}

				// Semi
				if (shapeAmi.collidesWith(shapeSemi1)
						|| shapeAmi.collidesWith(shapeSemi2) && isCatch) {
					delayAmi();
					enableCatch = false;
					isSemiJump = false;
					A1_05_13_semi.play();
					checkSemi = 0;

					notCatchSemi = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					sSemi1.setPosition(-999, -999);
					sSemi1.setVisible(true);
					sSemi1.clearEntityModifiers();
					sSemi2.clearEntityModifiers();
					sSemi3.clearEntityModifiers();

					sSemi2.setVisible(false);
					sSemi3.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm3(snameSemi, sSemi5, sSemi6);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					// TODO
					sSemi5.registerEntityModifier(new DelayModifier(3.5f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									Log.d(TAG, "Bat tiep di");
									A1_05_13_semi.stop();
									isSemiJump = true;
									isCatch = true;
									enableCatch = true;
								}
							}));
				}

				// Ari
				if (shapeAmi.collidesWith(shapeAri) && isCatch) {
					delayAmi();
					checkAri = 0;

					notCatchAri = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animAri.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayDango();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameAri, sAri5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

				}

				// Dango
				if (shapeAmi.collidesWith(shapeDango) && isCatch) {
					delayAmi();
					checkDango = 0;

					notCatchDango = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animDango.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayKamikiri();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameDango, sDango5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);
				}

				// Kamikiri
				if (shapeAmi.collidesWith(shapeKamikiri) && isCatch) {
					delayAmi();
					checkKamikiri = 0;
					isKamikiriJump = false;
					enableCatch = false;
					A1_05_16_kamikirimushi.play();

					notCatchKamikiri = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animKamikiri.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKogane();
					delayYoutyu();

					visibleTrueWorm2(snameKamikiri, sKamikiri4);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					sKamikiri4.registerEntityModifier(new DelayModifier(2.0f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									Log.d(TAG, "Bat tiep di");
									isCatch = true;
									enableCatch = true;
									isKamikiriJump = true;
									A1_05_16_kamikirimushi.stop();
								}
							}));
				}

				// Kogane
				if (shapeAmi.collidesWith(shapeKogane) && isCatch) {
					delayAmi();
					checkKogane = 0;

					notCatchKogane = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animKogane.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayYoutyu();

					visibleTrueWorm2(snameKogane, sKogane5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);
				}

				// Youtyu
				if (shapeAmi.collidesWith(shapeYoutyu) && isCatch) {
					delayAmi();
					checkYoutyu = 0;

					notCatchYoutyu = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					animYoutyu.setPosition(-999, -999);

					delayKirigirisu();
					delayTentoumushi();
					delayKamakiri();
					delayTonbo();
					delayCyou();
					delayHachi();
					delaySemi();
					delayAri();
					delayDango();
					delayKamikiri();
					delayKogane();

					visibleTrueWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);
				}

				// Hotaru
				if (shapeAmi.collidesWith(shapeHotaru) && isCatch) {
					delayAmi();

					notCatchHotaru = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchMatsumushi = true;
					notCatchSuzumushi = true;

					sHotaru3.clearEntityModifiers();
					sHotaru1.setPosition(-999, -999);
					sHotaru2.setVisible(false);
					sHotaru3.setVisible(false);

					delayMatsumushi();
					delaySuzumushi();
					if (notCatchKabuto && !checkNight) {
						animKabuto.setPosition(801, 205);
					}
					if (notCatchKuwagata && !checkNight) {
						animKuwagata.setPosition(801, 320);
					}
					visibleTrueWorm2(snameHotaru, sHotaru5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);
				}

				// Matsumushi
				if (shapeAmi.collidesWith(shapeMatsumushi) && isCatch) {
					delayAmi();
					isMatsumushiJump = false;
					enableCatch = false;
					A1_05_20_matsumushi.play();

					notCatchMatsumushi = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchSuzumushi = true;

					animMatsumushi.clearEntityModifiers();
					animMatsumushi.stopAnimation();
					animMatsumushi.setVisible(false);
					sMatsumushi1.setPosition(-999, -999);

					delayHotaru();
					delaySuzumushi();
					if (notCatchKabuto && !checkNight) {
						animKabuto.setPosition(801, 205);
					}
					if (notCatchKuwagata && !checkNight) {
						animKuwagata.setPosition(801, 320);
					}

					visibleTrueWorm2(snameMatsumushi, sMatsumushi5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameSuzumushi, sSuzumushi5);

					// TODO Conbo vang
					sMatsumushi5.registerEntityModifier(new DelayModifier(2.0f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									Log.d(TAG, "Bat tiep di");
									isCatch = true;
									enableCatch = true;
									isMatsumushiJump = true;
									A1_05_20_matsumushi.stop();
								}
							}));
				}

				// Suzumushi
				if (shapeAmi.collidesWith(shapeSuzumushi) && isCatch) {
					delayAmi();
					isSuzumushiJump = false;
					enableCatch = false;
					A1_05_21_suzumushi.play();

					notCatchSuzumushi = false;

					notCatchKirigirisu = true;
					notCatchKabuto = true;
					notCatchKuwagata = true;
					notCatchKamakiri = true;
					notCatchTentoumushi = true;
					notCatchTonbo = true;
					notCatchCyou = true;
					notCatchHachi = true;
					notCatchSemi = true;
					notCatchAri = true;
					notCatchDango = true;
					notCatchKamikiri = true;
					notCatchKogane = true;
					notCatchYoutyu = true;

					notCatchHotaru = true;
					notCatchMatsumushi = true;

					animSuzumushi.clearEntityModifiers();
					animSuzumushi.stopAnimation();
					animSuzumushi.setVisible(false);
					sSuzumushi1.setPosition(-999, -999);

					delayHotaru();
					delayMatsumushi();
					if (notCatchKabuto && !checkNight) {
						animKabuto.setPosition(801, 205);
					}
					if (notCatchKuwagata && !checkNight) {
						animKuwagata.setPosition(801, 320);
					}

					visibleTrueWorm2(snameSuzumushi, sSuzumushi5);

					visibleFalseWorm3(snameOneKabuto, sOneKabuto4, sOneKabuto5);
					visibleFalseWorm3(snameOneKugawata, sOneKugawata4,
							sOneKugawata5);

					visibleFalseWorm2(snameKirigirisu, sKirigirisu6);
					visibleFalseWorm2(snameTentoumushi, sTentoumushi4);
					visibleFalseWorm2(snameKamakiri, sKamakiri5);
					visibleFalseWorm2(snameTonbo, sTonbo5);
					visibleFalseWorm3(snameCyou, sCyou5, sCyou6);
					visibleFalseWorm2(snameHachi, sHachi7);
					visibleFalseWorm3(snameSemi, sSemi5, sSemi6);
					visibleFalseWorm2(snameAri, sAri5);
					visibleFalseWorm2(snameDango, sDango5);
					visibleFalseWorm2(snameKamikiri, sKamikiri4);
					visibleFalseWorm2(snameKogane, sKogane5);
					visibleFalseWorm2(snameYoutyu, sYoutyu6);

					visibleFalseWorm2(snameHotaru, sHotaru5);
					visibleFalseWorm2(snameMatsumushi, sMatsumushi5);

					// con re men TODO
					sSuzumushi5.registerEntityModifier(new DelayModifier(3.0f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									Log.d(TAG, "Bat tiep di");
									isCatch = true;
									enableCatch = true;
									isSuzumushiJump = true;
									A1_05_21_suzumushi.stop();
								}
							}));
				}
			}
		}
		return false;
	}

	/**
	 * Truong Vuong add
	 */

	private void showNight() {
		// TODO TRUONG VUONG

		notCatchSuzumushi = true;
		notCatchMatsumushi = true;
		notCatchSuzumushi = true;
		
		sHotaru1.setPosition(87, 507);
		sMatsumushi1.setPosition(141, 391);
		sSuzumushi1.setPosition(54, 361);
	}

	// Function touchBackground
	private void touchBackGround() {
		A1_04_harp.play();
		enableCatch = true;
		if (current >= 3)
			current = 0;
		current++;
		if (current == 1) {
			visibleTrueNoon();
			visibleFalseNight();
			visibleFalseMorning();

		} else if (current == 2) {
			visibleFalseNoon();
			visibleTrueNight();
			visibleFalseMorning();

		} else if (current == 3) {
			visibleFalseNoon();
			visibleFalseNight();
			visibleTrueMorning();
		}
	}

	// Function Kabuto
	public void touchKabuto(int x) {
		A1_22_23_kuwagata.play();
		final long pDurations[] = new long[] { 290, 290 };
		final int[] pFrame = new int[] { 0, 1 };
		animKabuto.animate(pDurations, pFrame, x, this);
	}

	// Function Kuwagata
	public void touchKuwagata(int x) {
		A1_22_23_kuwagata.play();
		final long pDurations[] = new long[] { 290, 290 };
		final int[] pFrame = new int[] { 0, 1 };
		animKuwagata.animate(pDurations, pFrame, x, this);
	}

	// Function Kirigirisu
	public void touchKirigirisu(final AnimatedSprite animSprite,
			final Sprite sSprite) {
		A1_07_kirigi_byon.play();
		animSprite.setCurrentTileIndex(0);
		animSprite.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						animSprite.setCurrentTileIndex(1);
						animSprite.registerEntityModifier(new DelayModifier(
								0.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {

									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										animSprite.setCurrentTileIndex(2);
										sSprite.setVisible(true);
										sSprite.registerEntityModifier(new SequenceEntityModifier(
												new ParallelEntityModifier(
														new MoveXModifier(0.3f,
																-59, -180),
														new MoveYModifier(0.2f,
																428, 435)),
												new DelayModifier(
														2.0f,
														new IEntityModifierListener() {
															@Override
															public void onModifierStarted(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																enableCatch = true;
															}

															@Override
															public void onModifierFinished(
																	IModifier<IEntity> arg0,
																	IEntity arg1) {
																sSprite.setPosition(
																		-59,
																		428);
																sSprite.setVisible(false);
																animKirigirisu
																		.setCurrentTileIndex(0);
																isTouchKirigirisu = true;
																A1_07_kirigi_byon
																		.stop();
															}
														})));
									}
								}));
					}
				}));
	}

	// Function touchtentoumushi
	private void touchTentoumushi(int tentoumushiPoint) {
		A1_08_pomi.play();
		checkTentoumushi = 1;
		if (tentoumushiPoint == 0) {
			animTentoumushi.setCurrentTileIndex(0);
			animTentoumushi.setVisible(true);
		} else if (tentoumushiPoint == 1) {
			animTentoumushi.setCurrentTileIndex(1);
			animTentoumushi.setVisible(true);
		}
	}

	// Function touchKamakiri
	private void touchKamakiri(final Sprite s1, final Sprite s2) {
		A1_09_kamakiri.play();
		checkKamakiri = 1;
		s1.setVisible(false);
		s2.setVisible(true);
		s2.registerEntityModifier(new DelayModifier(1.5f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						s2.setVisible(false);
						s1.setVisible(true);
						isTouchKamakiri = true;
						checkKamakiri = 0;
						A1_09_kamakiri.stop();
					}
				}));
	}

	// Function touchTonbo
	private void touchTonbo() {
		A1_medamaya1_10_powanaki.play();
		animTonbo.setVisible(false);
		sTonbo3.setVisible(true);
		sTonbo3.registerEntityModifier(new DelayModifier(2.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						A1_medamaya1_10_powanaki.stop();
						animTonbo.setVisible(true);
						sTonbo3.setVisible(false);
						isTouchTonbo = true;
					}
				}));
	}

	// Function touchCyou
	private void touchCyou() {
		A1_11_basa.play();
		checkCyouFly = 1;
		sCyou1.setPosition(-999, -999);
		animCyou.setPosition(185, 100);
		animationCyou(animCyou);
		animCyou.setVisible(true);
		animCyou.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(2.5f, 185, 960), new DelayModifier(0.1f,
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								animCyou.setPosition(-96, 100);
							}
						}), new MoveXModifier(1.0f, -96, 135),
				new DelayModifier(0.1f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						A1_11_basa.stop();
						animCyou.setPosition(-999, -999);
						sCyou1.setPosition(144, 90);
						isTouchCyou = true;
						checkCyouFly = 0;
					}
				})));
	}

	// Function touchHachi
	private void touchHachi() {

		sHachi1.registerEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.5f, 0, 8, 147, 14),
				new RotationAtModifier(0.5f, 8, -8, 147, 14),
				new RotationAtModifier(0.5f, -8, 0, 147, 14),
				new DelayModifier(0.3f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sHachi1.setVisible(false);
						sHachi2.setVisible(true);
						sHachi2.registerEntityModifier(new DelayModifier(0.5f,
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
										sHachi2.setVisible(false);
										sHachi3.setVisible(true);
										A1_12_bun.play();
										sHachi4.setPosition(600, 90);
										sHachi5.setPosition(670, 95);
										sHachi3.registerEntityModifier(new DelayModifier(
												0.1f,
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
														flyHachi1(sHachi4);
														flyHachi2(sHachi5);
													}
												}));
									}
								}));
					}
				})));
	}

	// Function touchSemi
	private void touchSemi() {
		checkSemi = 1;
		sSemi1.registerEntityModifier(new DelayModifier(1.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						A1_13_kasakasa.play();
						sSemi1.setPosition(-999, -999);
						sSemi2.setVisible(true);
						sSemi2.registerEntityModifier(new DelayModifier(1.0f,
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
										sSemi2.setVisible(false);
										sSemi3.setPosition(649, 74);
										sSemi3.registerEntityModifier(new SequenceEntityModifier(
												new MoveModifier(2.5f, 649,
														-208, 74, -150),
												new DelayModifier(
														0.3f,
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
																A1_13_kasakasa
																		.stop();
																checkSemi = 0;
																sSemi1.setPosition(
																		649, 74);
																isTouchSemi = true;

															}
														})));
									}
								}));
					}
				}));
	}

	// Function touchAri
	private void touchAri(int ariPoint) {
		A1_15_dangomushi.play();
		checkAri = 1;
		if (ariPoint == 0) {
			animAri.setCurrentTileIndex(0);
		}
		if (ariPoint == 1) {
			animAri.setCurrentTileIndex(1);
		}
		if (ariPoint == 2) {
			animAri.setCurrentTileIndex(2);
		}
	}

	// Function touchDango
	private void touchDango(int dangoPoint) {
		A1_15_dangomushi.play();
		checkDango = 1;
		if (dangoPoint == 0) {
			animDango.setCurrentTileIndex(0);
		}
		if (dangoPoint == 1) {
			animDango.setCurrentTileIndex(1);
		}
		if (dangoPoint == 2) {
			animDango.setCurrentTileIndex(2);
		}
	}

	// Function touchKamikiri
	private void touchKamikiri(int kamikiriPoint) {
		checkKamikiri = 1;
		if (kamikiriPoint == 0) {
			animKamikiri.setCurrentTileIndex(0);
			A1_05_16_kamikirimushi.play();
			animKamikiri.registerEntityModifier(new DelayModifier(3.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							enableCatch = true;
							isTouchKamikiri = true;
						}
					}));
		}
		if (kamikiriPoint == 1) {
			animKamikiri.setCurrentTileIndex(1);
			A1_05_16_kamikirimushi.play();
			animKamikiri.registerEntityModifier(new DelayModifier(3.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							enableCatch = true;
							isTouchKamikiri = true;
						}
					}));
		}
	}

	// Function touchKogane
	public void touchKogane(int koganePoint) {
		A1_17_18_kasa2.play();
		checkKogane = 1;
		if (koganePoint == 0) {
			animKogane.setCurrentTileIndex(0);
		}
		if (koganePoint == 1) {
			animKogane.setCurrentTileIndex(1);
		}
		if (koganePoint == 2) {
			animKogane.setCurrentTileIndex(2);
		}
	}

	// Function touchYoutyu
	public void touchYoutyu(int youtyuPoint) {
		A1_17_18_kasa2.play();
		checkYoutyu = 1;
		if (youtyuPoint == 0) {
			animYoutyu.setCurrentTileIndex(0);
		}
		if (youtyuPoint == 1) {
			animYoutyu.setCurrentTileIndex(1);
		}
		if (youtyuPoint == 2) {
			animYoutyu.setCurrentTileIndex(2);
		}
		if (youtyuPoint == 3) {
			animYoutyu.setCurrentTileIndex(3);
		}
	}

	// Function touchHotaru
	public void touchHotaru() {
		A1_19_kira5.play();
		sHotaru1.setVisible(false);
		sHotaru2.setVisible(true);
		sHotaru3.setVisible(true);
		sHotaru3.registerEntityModifier(new SequenceEntityModifier(
				new AlphaModifier(1.3f, 0.0f, 1.0f), new AlphaModifier(1.3f,
						1.0f, 0.0f), new DelayModifier(0.2f,
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								A1_19_kira5.stop();
								sHotaru2.setVisible(false);
								sHotaru3.setVisible(false);
								sHotaru1.setVisible(true);
								enableCatch = true;
								isTouchHotaru = true;
							}
						})));
	}

	// Function touchMatsumushi
	public void touchMatsumushi() {
		final long pDurationsMat[] = new long[] { 130, 130 };
		final int[] pFrameMat = new int[] { 0, 1 };

		A1_20_matsumushi.play();
		sMatsumushi1.setVisible(false);
		animMatsumushi.setVisible(true);
		animMatsumushi.animate(pDurationsMat, pFrameMat, 5,
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
						A1_20_matsumushi.stop();
						enableCatch = true;
						animMatsumushi.setVisible(false);
						sMatsumushi1.setVisible(true);
						isTouchMatsumushi = true;
					}
				});
	}

	// Function touchSuzumushi
	public void touchSuzumushi() {
		final long pDurationsSu[] = new long[] { 150, 150 };
		final int[] pFrameSu = new int[] { 0, 1 };

		A1_21_suzumushi.play();
		sSuzumushi1.setVisible(false);
		animSuzumushi.setVisible(true);
		animSuzumushi.animate(pDurationsSu, pFrameSu, 5,
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
						A1_21_suzumushi.stop();
						enableCatch = true;
						animSuzumushi.setVisible(false);
						sSuzumushi1.setVisible(true);
						isTouchSuzumushi = true;
					}
				});
	}

	// Function Touch Hake
	public void touchHake() {
		A1_06_syu2.play();
		sHake1.setVisible(false);
		sHake1.setPosition(-999, -999);
		sHake2.setVisible(true);
		sHake2.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sHake2.setVisible(false);
						sHake3.setVisible(true);
						sHake3.registerEntityModifier(new DelayModifier(3.0f,
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
										A1_06_syu2.stop();
										checkNight = false;
										if (notCatchKabuto) {
											animKabuto.setPosition(801, 205);
										}
										if (notCatchKuwagata) {
											animKuwagata.setPosition(801, 320);
										}
									}
								}));
					}
				}));
	}

	// Function Jump
	public void actionJump(Sprite sJump) {
		sJump.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(0.3f, 371, 351), new MoveYModifier(0.3f, 351,
						371)));
	}

	// Function False Morning
	public void visibleFalseMorning() {
		sBackGround3.setVisible(false);

		mp3Stop();

		animKuwagata.clearEntityModifiers();
		animKabuto.clearEntityModifiers();

		animKuwagata.setPosition(-999, -999);
		animKabuto.setPosition(-999, -999);

		sHake3.setVisible(false);
	}

	// Function True Morning
	public void visibleTrueMorning() {
		initialMorning();
		sBackGround3.setVisible(true);

		if (notCatchKuwagata) {
			animKuwagata.setPosition(801, 320);
		} else
			animKuwagata.setPosition(-999, -999);

		if (notCatchKabuto) {
			animKabuto.setPosition(801, 205);
		} else
			animKabuto.setPosition(-999, -999);

	}

	// Function False Noon
	public void visibleFalseNoon() {
		sBackGround1.setVisible(false);
		sBackGround4.setVisible(false);

		mp3Stop();

		animKirigirisu.clearEntityModifiers();
		animKirigirisu.stopAnimation();
		sKirigirisu.clearEntityModifiers();
		sKamakiri2.clearEntityModifiers();
		sKamakiri3.clearEntityModifiers();
		sTonbo3.clearEntityModifiers();
		animCyou.clearEntityModifiers();
		animCyou.stopAnimation();
		sHachi4.clearEntityModifiers();
		sHachi5.clearEntityModifiers();
		sHachi1.clearEntityModifiers();
		sHachi2.clearEntityModifiers();
		sHachi3.clearEntityModifiers();

		animKirigirisu.setPosition(-999, -999);
		sKirigirisu.setPosition(-59, -404);
		sKirigirisu.setVisible(false);

		animTentoumushi.setPosition(-999, -999);
		pTentoumushi = 0;

		sKamakiri1.setPosition(-999, -999);
		sKamakiri1.setVisible(true);
		sKamakiri2.setVisible(false);
		sKamakiri3.setVisible(false);

		animTonbo.setPosition(-999, -999);
		animTonbo.setVisible(true);
		sTonbo3.setVisible(false);

		animCyou.setPosition(-999, -999);
		sCyou1.setPosition(-999, -999);

		sHachi1.setPosition(-999, -999);
		sHachi1.setVisible(true);
		sHachi2.setVisible(false);
		sHachi3.setVisible(false);
		sHachi4.setPosition(-999, -999);
		sHachi5.setPosition(-999, -999);

		sSemi1.setPosition(-999, -999);
		sSemi1.setVisible(true);
		sSemi1.clearEntityModifiers();
		sSemi2.clearEntityModifiers();
		sSemi3.clearEntityModifiers();

		sSemi2.setVisible(false);
		sSemi3.setPosition(-999, -999);

		animAri.setPosition(-999, -999);
		pAri = 0;

		animDango.setPosition(-999, -999);
		pDango = 0;

		animKamikiri.setPosition(-999, -999);
		pKamikiri = 0;

		animKogane.setPosition(-999, -999);
		pKogane = 0;

		animYoutyu.setPosition(-999, -999);
		pYoutyu = 0;

	}

	// Function True Noon
	public void visibleTrueNoon() {
		initialNoon();
		sBackGround1.setVisible(true);
		sBackGround4.setVisible(true);

		if (notCatchKirigirisu) {
			animKirigirisu.setPosition(-59, 404);
			animKirigirisu.setCurrentTileIndex(0);
			sKirigirisu.setVisible(false);
		} else {
			animKirigirisu.setPosition(-999, -999);
			sKirigirisu.setVisible(false);
		}

		if (notCatchTentoumushi) {
			animTentoumushi.setPosition(76, 78);
			animTentoumushi.setVisible(false);
		} else
			animTentoumushi.setPosition(-999, -999);

		if (notCatchKamakiri) {
			sKamakiri1.setPosition(155, 82);
			sKamakiri2.setVisible(false);
			sKamakiri3.setVisible(false);
		} else
			sKamakiri1.setPosition(-999, -999);

		if (notCatchTonbo) {
			animTonbo.setPosition(162, 0);
			animationTonbo(animTonbo);
		} else
			animTonbo.setPosition(-999, -999);

		if (notCatchCyou) {
			sCyou1.setPosition(144, 90);
		} else {
			sCyou1.setPosition(-999, -999);
		}

		if (notCatchHachi) {
			sHachi1.setPosition(580, 17);
		} else
			sHachi1.setPosition(-999, -999);

		if (notCatchSemi) {
			sSemi1.setPosition(649, 74);
		} else
			sSemi1.setPosition(-999, -999);

		if (notCatchAri) {
			animAri.setPosition(400, 180);
			animAri.setRotation(25);
			animAri.setCurrentTileIndex(0);
		} else
			animAri.setPosition(-999, -999);

		if (notCatchDango) {
			animDango.setPosition(665, 270);
			animDango.setCurrentTileIndex(0);
		} else
			animDango.setPosition(-999, -999);

		if (notCatchKamikiri) {
			animKamikiri.setPosition(810, 300);
			animKamikiri.setCurrentTileIndex(0);
		} else
			animKamikiri.setPosition(-999, -999);

		if (notCatchKogane) {
			animKogane.setPosition(674, 387);
			animKogane.setCurrentTileIndex(0);
		} else
			animKogane.setPosition(-999, -999);

		if (notCatchYoutyu) {
			animYoutyu.setPosition(754, 530);
			animYoutyu.setCurrentTileIndex(0);
		} else
			animYoutyu.setPosition(-999, -999);

	}

	// Function Visible False Night
	private void visibleFalseNight() {
		mp3Stop();
		sBackGround2.setPosition(-999, -999);
		sBackGround2.setVisible(false);
		sHotaru3.clearEntityModifiers();
		animMatsumushi.clearEntityModifiers();
		animMatsumushi.stopAnimation();
		animSuzumushi.clearEntityModifiers();
		animSuzumushi.stopAnimation();
		sHake2.clearEntityModifiers();
		sHake3.clearEntityModifiers();
		animKuwagata.clearEntityModifiers();
		animKuwagata.stopAnimation();
		animKabuto.clearEntityModifiers();
		animKabuto.stopAnimation();

		sHotaru1.setPosition(-999, -999);
		sHotaru1.setVisible(true);
		sHotaru2.setVisible(false);
		sHotaru3.setVisible(false);

		sMatsumushi1.setPosition(-999, -999);
		sMatsumushi1.setVisible(true);
		animMatsumushi.setVisible(false);

		sSuzumushi1.setPosition(-999, -999);
		sSuzumushi1.setVisible(true);
		animSuzumushi.setVisible(false);

		sHake1.setPosition(-999, -999);
		sHake2.setVisible(false);
		// sHake3.setVisible(false);
		isTouchHake = true;

		isFaceKabuto = false;
		isFaceKuwagata = false;

	}

	// Function Visible True Night
	private void visibleTrueNight() {
		initialNight();
		sBackGround2.setPosition(0, 0);
		sBackGround2.setVisible(true);
		
		isTouchKabuto = true;
		isTouchKuwagata = true;
		isFaceKabuto = true;
		isFaceKuwagata = true;

		if (notCatchHotaru) {
			sHotaru1.setPosition(87, 507);
		} else
			sHotaru1.setPosition(-999, -999);

		if (notCatchMatsumushi) {
			sMatsumushi1.setPosition(141, 391);
		} else
			sMatsumushi1.setPosition(-999, -999);

		if (notCatchSuzumushi) {
			sSuzumushi1.setPosition(54, 361);
		} else
			sSuzumushi1.setPosition(-999, -999);

		sHake1.setPosition(761, 193);
		sHake1.setVisible(true);

	}

	// Function Animated Tonbo
	public void animationTonbo(AnimatedSprite animaTonbo) {
		long pDurations[] = new long[] { 300, 300 };
		animaTonbo.animate(pDurations);
	}

	// Function Animated Cyou
	public void animationCyou(AnimatedSprite animaCyou) {
		long pDurations[] = new long[] { 300, 300 };
		animaCyou.animate(pDurations);
	}

	// Function True Worm 3 Sprite
	public void visibleTrueWorm3(Sprite worm1, Sprite worm2, Sprite worm3) {
		worm1.setVisible(true);
		worm2.setPosition(325, 371);
		worm2.setVisible(true);
		worm3.setVisible(true);
	}

	// Function True Worm 2 Sprite
	public void visibleTrueWorm2(Sprite worm1, Sprite worm2) {
		worm1.setVisible(true);
		worm2.setVisible(true);
		worm2.setPosition(325, 371);
	}

	// Function False Worm 3 Sprite
	public void visibleFalseWorm3(Sprite worm1, Sprite worm2, Sprite worm3) {
		worm1.setVisible(false);
		worm2.setPosition(-999, -999);
		worm3.setVisible(false);
	}

	// Function False Worm 2 Sprite
	public void visibleFalseWorm2(Sprite worm1, Sprite worm2) {
		worm1.setVisible(false);
		worm2.setPosition(-999, -999);
	}

	// Function Fly Hachi
	private int vectors11[][] = { { 353, 297 }, { 379, 366 } };
	private int vectors12[][] = { { -88, 405 }, { -88, 600 } };

	private void flyHachi1(Sprite flyHachi1) {
		Random random = new Random();
		int rand1 = random.nextInt(vectors11.length);
		int rand2 = random.nextInt(vectors12.length);
		int pointOne11 = 0;
		int pointTwo11 = 0;
		int pointOne12 = 0;
		int pointTwo12 = 0;
		switch (rand1) {
		case 0:
			pointOne11 = vectors11[0][0];
			pointTwo11 = vectors11[0][1];
			break;
		case 1:
			pointOne11 = vectors11[1][0];
			pointTwo11 = vectors11[1][1];
			break;
		}

		switch (rand2) {
		case 0:
			pointOne12 = vectors12[0][0];
			pointTwo12 = vectors12[0][1];
			break;
		case 1:
			pointOne12 = vectors12[1][0];
			pointTwo12 = vectors12[1][1];
			break;
		}
		flyHachi1.clearEntityModifiers();
		flyHachi1.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(2.0f, 600, pointOne11, 90, pointTwo11),
				new MoveModifier(1.0f, pointOne11, pointOne12, pointTwo11,
						pointTwo12), new DelayModifier(0.5f,
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								A1_12_bun.stop();
								isTouchHachi = true;
								sHachi1.setVisible(true);
								sHachi3.setVisible(false);
							}
						})));
	}

	private int vectors21[][] = { { 415, 403 }, { 533, 445 } };
	private int vectors22[][] = { { -88, 600 }, { 66, 640 } };

	private void flyHachi2(Sprite flyHachi2) {
		Random random = new Random();
		int rand1 = random.nextInt(vectors21.length);
		int rand2 = random.nextInt(vectors22.length);
		int pointOne21 = 0;
		int pointTwo21 = 0;
		int pointOne22 = 0;
		int pointTwo22 = 0;

		switch (rand1) {
		case 0:
			pointOne21 = vectors21[0][0];
			pointTwo21 = vectors21[0][1];
			break;
		case 1:
			pointOne21 = vectors21[1][0];
			pointTwo21 = vectors21[1][1];
			break;
		}

		switch (rand2) {
		case 0:
			pointOne22 = vectors22[0][0];
			pointTwo22 = vectors22[0][1];
			break;
		case 1:
			pointOne22 = vectors22[1][0];
			pointTwo22 = vectors22[1][1];
			break;
		}
		flyHachi2.clearEntityModifiers();
		flyHachi2.registerEntityModifier(new SequenceEntityModifier(
				new MoveModifier(2.0f, 670, pointOne21, 95, pointTwo21),
				new MoveModifier(1.0f, pointOne21, pointOne22, pointTwo21,
						pointTwo22), new DelayModifier(0.5f,
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								A1_12_bun.stop();
								isTouchHachi = true;
								sHachi1.setVisible(true);
								sHachi3.setVisible(false);
							}
						})));
	}

	// Function Fly Hachi //

	// onAnimation
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(animKabuto)) {
			animKabuto.setCurrentTileIndex(0);
			isTouchKabuto = true;
		}
		if (pAnimatedSprite.equals(animKuwagata)) {
			animKuwagata.setCurrentTileIndex(0);
			isTouchKuwagata = true;
		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int p1,
			int p2) {

	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int p1,
			int p2) {

	}

	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int p1) {

	}

	// Delay Worm
	// Kirigirisu
	public void delayKirigirisu() {
		animKirigirisu.setPosition(-59, 404);
	}

	// Tentoumushi
	public void delayTentoumushi() {
		if (checkTentoumushi != 1) {
			animTentoumushi.setPosition(76, 78);
			animTentoumushi.setCurrentTileIndex(0);
			animTentoumushi.setVisible(false);
			pTentoumushi = 0;
		}
	}

	// Kamakiri
	public void delayKamakiri() {
		if (checkKamakiri != 1) {
			sKamakiri1.setPosition(155, 82);
			sKamakiri1.setVisible(true);
			isTouchKamakiri = true;
			sKamakiri2.setVisible(false);
			sKamakiri3.setVisible(false);
		}
	}

	// Tonbo
	public void delayTonbo() {
		animTonbo.setPosition(162, 0);
		isTouchTonbo = true;
	}

	// Cyou
	public void delayCyou() {
		if (checkCyouFly != 1) {
			sCyou1.setPosition(144, 90);
			isTouchCyou = true;
		}
	}

	// Hachi
	public void delayHachi() {
		sHachi1.setPosition(580, 17);
		isTouchHachi = true;
	}

	// Semi
	public void delaySemi() {
		if (checkSemi != 1) {
			sSemi1.setPosition(649, 74);
			isTouchSemi = true;
		}
	}

	// Ari
	public void delayAri() {
		if (checkAri != 1) {
			animAri.setPosition(400, 180);
			animAri.setRotation(25);
			animAri.setCurrentTileIndex(0);
			pAri = 0;
		}
	}

	// Dango
	public void delayDango() {
		if (checkDango != 1) {
			animDango.setPosition(665, 270);
			animDango.setCurrentTileIndex(0);
			pDango = 0;
		}
	}

	// Kamikiri
	public void delayKamikiri() {
		if (checkKamikiri != 1) {
			animKamikiri.setPosition(810, 300);
			animKamikiri.setCurrentTileIndex(0);
			pKamikiri = 0;
		}
	}

	// Kogane
	public void delayKogane() {
		if (checkKogane != 1) {
			animKogane.setPosition(674, 387);
			animKogane.setCurrentTileIndex(0);
			pKogane = 0;
		}
	}

	// Youtyu
	public void delayYoutyu() {
		if (checkYoutyu != 1) {
			animYoutyu.setPosition(754, 530);
			animYoutyu.setCurrentTileIndex(0);
			pYoutyu = 0;
		}
	}

	// Hotaru
	public void delayHotaru() {
		sHotaru1.setPosition(87, 507);
		sHotaru1.setVisible(true);
		isTouchHotaru = true;
	}

	// Matsumushi
	public void delayMatsumushi() {
		sMatsumushi1.setPosition(141, 391);
		sMatsumushi1.setVisible(true);
		isTouchMatsumushi = true;
	}

	// Suzumushi
	public void delaySuzumushi() {
		sSuzumushi1.setPosition(54, 361);
		sSuzumushi1.setVisible(true);
		isTouchSuzumushi = true;
	}

	public void visibleFalseAll() {
		snameOneKabuto.setVisible(false);
		snameOneKugawata.setVisible(false);

		snameKirigirisu.setVisible(false);
		snameTentoumushi.setVisible(false);
		snameKamakiri.setVisible(false);
		snameTonbo.setVisible(false);
		snameCyou.setVisible(false);
		snameHachi.setVisible(false);
		snameSemi.setVisible(false);
		snameAri.setVisible(false);
		snameDango.setVisible(false);
		snameKamikiri.setVisible(false);
		snameKogane.setVisible(false);
		snameYoutyu.setVisible(false);

		snameHotaru.setVisible(false);
		snameMatsumushi.setVisible(false);
		snameSuzumushi.setVisible(false);

		sOneKabuto4.clearEntityModifiers();
		sOneKabuto4.setPosition(-999, -999);
		sOneKabuto4.setVisible(false);
		sOneKabuto5.setVisible(false);
		sOneKugawata4.clearEntityModifiers();
		sOneKugawata4.setPosition(-999, -999);
		sOneKugawata4.setVisible(false);
		sOneKugawata5.setVisible(false);

		sKirigirisu6.clearEntityModifiers();
		sKirigirisu6.setVisible(false);
		sKirigirisu6.setPosition(-999, -999);
		sTentoumushi4.clearEntityModifiers();
		sTentoumushi4.setVisible(false);
		sTentoumushi4.setPosition(-999, -999);
		sKamakiri5.clearEntityModifiers();
		sKamakiri5.setVisible(false);
		sKamakiri5.setPosition(-999, -999);
		sTonbo5.clearEntityModifiers();
		sTonbo5.setVisible(false);
		sTonbo5.setPosition(-999, -999);
		sCyou5.clearEntityModifiers();
		sCyou5.setVisible(false);
		sCyou5.setPosition(-999, -999);
		sCyou6.setVisible(false);
		sHachi7.clearEntityModifiers();
		sHachi7.setVisible(false);
		sHachi7.setPosition(-999, -999);
		sSemi5.clearEntityModifiers();
		sSemi5.setVisible(false);
		sSemi5.setPosition(-999, -999);
		sSemi6.setVisible(false);
		sAri5.clearEntityModifiers();
		sAri5.setVisible(false);
		sAri5.setPosition(-999, -999);
		sDango5.clearEntityModifiers();
		sDango5.setVisible(false);
		sDango5.setPosition(-999, -999);
		sKamikiri4.clearEntityModifiers();
		sKamikiri4.setVisible(false);
		sKamikiri4.setPosition(-999, -999);
		sKogane5.clearEntityModifiers();
		sKogane5.setVisible(false);
		sKogane5.setPosition(-999, -999);
		sYoutyu6.clearEntityModifiers();
		sYoutyu6.setVisible(false);
		sYoutyu6.setPosition(-999, -999);

		sHotaru5.clearEntityModifiers();
		sHotaru5.setVisible(false);
		sHotaru5.setPosition(-999, -999);
		sMatsumushi5.clearEntityModifiers();
		sMatsumushi5.setVisible(false);
		sMatsumushi5.setPosition(-999, -999);
		sSuzumushi5.clearEntityModifiers();
		sSuzumushi5.setVisible(false);
		sSuzumushi5.setPosition(-999, -999);
	}

	// OnPause
	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		mp3Stop();
		A1_04_harp.stop();
		visibleFalseAll();
		visibleFalseMorning();
		visibleFalseNoon();
		visibleFalseNight();
		super.onPauseGame();
	}

	// Vibrate
	private void vibrateAmi() {
		mScene.unregisterUpdateHandler(tTimer);
		tTimer = new TimerHandler(3.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sAmi.registerEntityModifier(new SequenceEntityModifier(
						new RotationAtModifier(0.1f, 0, 7, 176, 35),
						new RotationAtModifier(0.1f, 7, -7, 176, 35),
						new RotationAtModifier(0.1f, -7, 0, 176, 35)));
			}
		});
		mScene.registerUpdateHandler(tTimer);
	}

	// Delay Ami
	public void delayAmi() {
		A1_05_za.play();
		isCatch = false;
		isAmiMove = false;
		sAmi.setPosition(sAmi.getmXFirst(), sAmi.getmYFirst());
		vibrateAmi();
	}

	// Mp3 Stop
	public void mp3Stop() {
		A1_05_7_kirigilis_long.stop();
		A1_05_7_kirigilis_voice.stop();
		A1_05_8_tentoumushi_voice.stop();
		A1_05_9_kamakiri_voice.stop();
		A1_05_10_tonbo_voice.stop();
		A1_05_11_cyou_voice.stop();
		A1_05_12_hachi_voice.stop();
		A1_05_13_semi.stop();
		A1_05_13_semi_voice.stop();
		A1_05_14_ari_voice.stop();
		A1_05_15_dangomushi_voice.stop();
		A1_05_16_kamikirimushi.stop();
		A1_05_16_kamikirimushi_voice.stop();
		A1_05_17_koganemushi_voice.stop();
		A1_05_18_youtyu_voice.stop();
		A1_05_19_hotaru_voice.stop();
		A1_05_20_matsumushi.stop();
		A1_05_20_matsumushi_voice.stop();
		A1_05_21_suzumushi.stop();
		A1_05_21_suzumushi_voice.stop();
		A1_05_22_kuwagatamushi_voice.stop();
		A1_05_23_kabutomushi_voice.stop();
		A1_05_za.stop();
		A1_06_syu2.stop();
		A1_07_kirigi_byon.stop();
		A1_08_pomi.stop();
		A1_09_kamakiri.stop();
		A1_medamaya1_10_powanaki.stop();
		A1_11_basa.stop();
		A1_12_bun.stop();
		A1_13_kasakasa.stop();
		A1_15_dangomushi.stop();
		A1_17_18_kasa2.stop();
		A1_19_kira5.stop();
		A1_20_matsumushi.stop();
		A1_21_suzumushi.stop();
		A1_22_23_kuwagata.stop();
	}
}