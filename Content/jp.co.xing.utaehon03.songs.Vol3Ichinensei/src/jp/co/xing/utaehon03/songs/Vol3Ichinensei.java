package jp.co.xing.utaehon03.songs;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3IchinenseiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;

import android.os.Handler;
import android.util.Log;

public class Vol3Ichinensei extends BaseGameActivity implements IOnSceneTouchListener {

	private static final String TAG = "LOG_ICHINENSEI";
	// private BitmapTextureAtlas mLayoutTexture = null;
	// private ITextureRegion mLayoutTextureRegion = null;
	// private Sprite mSpriteAttach;
	private TexturePack ttpIchinensei1, ttpIchinensei2, ttpIchinensei3;
	private TexturePackTextureRegionLibrary ttpLibIchinensei1, ttpLibIchinensei2, ttpLibIchinensei3;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private TextureRegion ttrBackground1, ttrBackground2, ttrBackground3, ttrSita, ttrSeeSaw61, ttrSeeSaw62, ttrSeeSaw63, ttrSeeSaw64, ttrSeeSaw65, ttrSakuraLeft, ttrSakuraRight, ttrChildrenBefore, ttrChildRun1, ttrChildRun2, ttrChildRun3,
			ttrChildAfter11, ttrChildAfter12, ttrChildAfter21, ttrChildAfter22, ttrChildAfter31;
	private TextureRegion ttrSakura51, ttrSakura52, ttrSakura53, ttrSakura54, ttrSakura55, ttrSakura56, ttrSakura57, ttrKusa1, ttrKusa2, ttrKusa3, ttrKusa4;

	private Sprite sBackground1, sBackground2, sBackground3, sSita, sSeeSaw61, sSeeSaw62, sSeeSaw63Left, sSeeSaw63Right, sSeeSaw64, sSeeSaw65, sSakuraLeft, sSakuraRight, sChildrenBefore, sChildRun1, sChildRun2, sChildRun3, sChildAfter11,
			sChildAfter12, sChildAfter21, sChildAfter22, sChildAfter31;
	private Sprite sSakura52, sSakura53, sSakura54, sSakura55, sSakura56, sSakura57, sKusa1, sKusa2, sKusa3, sKusa4;
	private Sprite sSakura51[] = new Sprite[9];

	private Sprite sAnimals;

	private TextureRegion ttrChildren1, ttrChildren2, ttrChildren3, ttrChildren4;
	private Sprite sChildren1, sChildren2, sChildren3, sChildren4;

	private TextureRegion ttrPengin, ttrAkashia, ttrArikui, ttrMandoriru, ttrKapibara, ttrAmuruhyou, ttrOtosay, ttrIruka, ttrKuma;
	private Sprite sPengin, sAkashia, sArikui, sMandoriru, sKapibara, sAmuruhyou, sOtosay, sIruka, sKuma;
	private Sprite sPenginLast, sAkashiaLast, sArikuiLast, sMandoriruLast, sKapibaraLast, sAmuruhyouLast, sOtosayLast, sIrukaLast, sKumaLast;

	private TiledTextureRegion tttrChildren1, tttrChildren2, tttrChildren3, tttrChildren4, tttrPengin, tttrAkashia, tttrArikui, tttrMandoriru, tttrKapibara, tttrAmuruhyou, tttrOtosay, tttrIruka, tttrKuma;
	private AnimatedSprite asChildren1, asChildren2, asChildren3, asChildren4, asPengin, asAkashia, asArikui, asMandoriru, asKapibara, asAmuruhyou, asOtosay, asIruka, asKuma;
	private TiledTextureRegion tttrChildLast1, tttrChildLast2, tttrChildLast3, tttrChildLast4;
	private AnimatedSprite asChildLast1, asChildLast2, asChildLast3, asChildLast4;

	private boolean isTouchChildren1, isTouchChildren2, isTouchChildren3, isTouchChildren4, isTouchChildAfter;
	private boolean isTouchPengin, isTouchAkashia, isTouchArikui, isTouchMandoriru, isTouchKapibara, isTouchAmuruhyou, isTouchOtosay, isTouchIkura, isTouchKuma, isTouchHaikei, isTouchChilds, isTouchSakura, isTouch;
	private boolean isMp3Pengin, isMp3Akashia, isMp3Arikui, isMp3Mandoriru, isMp3Kapibara, isMp3Amuruhyou, isMp3Otosay, isMp3Ikura, isMp3Kuma;

	private boolean isMoving = false, isMoveAnimal = false, isTouchAction;

	private Hashtable<AnimatedSprite, Integer> htListChild = new Hashtable<AnimatedSprite, Integer>();
	private int iAnimal = 0, iResult = 0;

	private int iChild1[][] = { { -17, -164 }, { 65, -164 }, { 134, -164 }, { 199, -164 } };
	private int iChild2[][] = { { -29, -129 }, { 52, -129 }, { 126, -129 }, { 190, -129 } };
	private int iChild3[][] = { { -29, -129 }, { 46, -129 }, { 114, -129 }, { 182, -129 } };
	private int iChild4[][] = { { -10, -134 }, { 59, -134 }, { 129, -134 }, { 195, -134 } };

	private int positions[][] = { { 58, 179 }, { 184, 140 }, { 294, 142 }, { 368, 209 }, { 466, 216 }, { 546, 183 }, { 671, 147 }, { 780, 151 }, { 850, 176 } };
	private int moveArray[][] = { { 81, 350 }, { 183, 304 }, { 271, 310 }, { 339, 368 }, { 404, 440 }, { 555, 379 }, { 694, 352 }, { 780, 362 }, { 888, 414 } };

	private IchinenseiSprite pSpriteIchinensei;
	private LinkedList<Sprite> listIchinensei = new LinkedList<Sprite>();

	private TimerHandler timerChild, timerHandler;

	private Sound mp3Sisonosetene, mp3Pengin, mp3Pengin_vo, mp3Akashia, mp3Akashia_vo, mp3Arikui, mp3Arikui_vo, mp3Mandoriru, mp3Mandoriru_vo, mp3Kapibara, mp3Kapibara_vo, mp3Amuruhyou, mp3Amuruhyou_vo, mp3Otosay, mp3Otosay_vo, mp3Iruka,
			mp3Iruka_vo, mp3Kuma, mp3Kuma_vo, mp3Kodomo, mp3Onigirikorogaru, mp3Sakura, mp3Wai, mp3Siso, mp3Pakun, mp3Musya, mp3Yoisyo1, mp3Yoisyo2, mp3Hakusyu, mp3Onajiomosa, mp3Yata, mp3Gogogo, mp3Siso_noseru;

	private int lastX = 0;
	private int lastY = 0;
	final Sprite pFlower[] = new Sprite[9];
	private boolean isTouchAni[] = new boolean[] { true, true, true, true, true, true, true, true, true, };

	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources");

		ttpIchinensei1 = mTexturePackLoaderFromSource.load("ichinensei1.xml");
		ttpIchinensei1.loadTexture();
		ttpLibIchinensei1 = ttpIchinensei1.getTexturePackTextureRegionLibrary();

		ttpIchinensei2 = mTexturePackLoaderFromSource.load("ichinensei2.xml");
		ttpIchinensei2.loadTexture();
		ttpLibIchinensei2 = ttpIchinensei2.getTexturePackTextureRegionLibrary();

		ttpIchinensei3 = mTexturePackLoaderFromSource.load("ichinensei3.xml");
		ttpIchinensei3.loadTexture();
		ttpLibIchinensei3 = ttpIchinensei3.getTexturePackTextureRegionLibrary();

		this.ttrBackground1 = ttpLibIchinensei3.get(Vol3IchinenseiResource.A2_25_1_IPHONE_HAIKEI_ID);
		this.ttrBackground2 = ttpLibIchinensei3.get(Vol3IchinenseiResource.A2_25_2_IPHONE_HAIKEI_ID);
		this.ttrBackground3 = ttpLibIchinensei3.get(Vol3IchinenseiResource.A2_25_3_IPHONE_HAIKEI_ID);

		this.ttrSita = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_4_IPHONE_SITA_ID);

		this.ttrSakura51 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_1_IPHONE_SAKURA_ID);
		this.ttrSakura52 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_2_IPHONE_SAKURA_ID);
		this.ttrSakura53 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_3_IPHONE_SAKURA_ID);
		this.ttrSakura54 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_4_IPHONE_SAKURA_ID);
		this.ttrSakura55 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_5_IPHONE_SAKURA_ID);
		this.ttrSakura56 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_6_IPHONE_SAKURA_ID);
		this.ttrSakura57 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_7_IPHONE_SAKURA_ID);
		this.ttrSakuraLeft = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_8_IPHONE_SAKURA_LEFT_ID);
		this.ttrSakuraRight = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_5_9_IPHONE_SAKURA_RIGHT_ID);

		this.ttrSeeSaw61 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_6_1_IPHONE_SEESAW_ID);
		this.ttrSeeSaw62 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_6_2_IPHONE_SEESAW_ID);
		this.ttrSeeSaw63 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_6_3_IPHONE_SEESAW_ID);
		this.ttrSeeSaw64 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_6_4_IPHONE_SEESAW_ID);
		this.ttrSeeSaw65 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_6_5_IPHONE_SEESAW_ID);

		this.ttrChildren1 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_7_1_IPHONE_KODOMO_ID);
		this.tttrChildren1 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_7_2_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_7_3_IPHONE_KODOMO_ID);
		this.tttrChildLast1 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_7_4_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_7_5_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_7_6_IPHONE_KODOMO_ID);

		this.ttrChildren2 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_8_1_IPHONE_KODOMO_ID);
		this.tttrChildren2 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_8_2_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_8_3_IPHONE_KODOMO_ID);
		this.tttrChildLast2 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_8_4_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_8_5_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_8_6_IPHONE_KODOMO_ID);

		this.ttrChildren3 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_9_1_IPHONE_KODOMO_ID);
		this.tttrChildren3 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_9_2_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_9_3_IPHONE_KODOMO_ID);
		this.tttrChildLast3 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_9_4_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_9_5_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_9_6_IPHONE_KODOMO_ID);

		this.ttrChildren4 = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_10_1_IPHONE_KODOMO_ID);
		this.tttrChildren4 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_10_2_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_10_3_IPHONE_KODOMO_ID);
		this.tttrChildLast4 = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_10_4_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_10_5_IPHONE_KODOMO_ID, Vol3IchinenseiResource.A2_10_6_IPHONE_KODOMO_ID);

		this.ttrChildrenBefore = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_21_IPHONE_BEFORE_ID);
		this.ttrChildRun1 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_22_1_IPHONE_RUN_ID);
		this.ttrChildRun2 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_22_2_IPHONE_RUN_ID);
		this.ttrChildRun3 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_22_3_IPHONE_RUN_ID);
		this.ttrChildAfter11 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_23_1_IPHONE_AFTER_ID);
		this.ttrChildAfter12 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_23_2_IPHONE_AFTER_ID);
		this.ttrChildAfter21 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_24_1_IPHONE_AFTER_ID);
		this.ttrChildAfter22 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_24_2_IPHONE_AFTER_ID);
		this.ttrChildAfter31 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_24_3_IPHONE_AFTER_ID);

		this.ttrPengin = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_11_1_IPHONE_PENGIN_ID);
		this.tttrPengin = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_11_2_IPHONE_PENGIN_ID, Vol3IchinenseiResource.A2_11_3_IPHONE_PENGIN_ID);

		this.ttrAkashia = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_12_1_IPHONE_AKASHIA_ID);
		this.tttrAkashia = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_12_2_IPHONE_AKASHIA_ID, Vol3IchinenseiResource.A2_12_3_IPHONE_AKASHIA_ID);

		this.ttrArikui = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_13_1_IPHONE_ARIKUI_ID);
		this.tttrArikui = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_13_2_IPHONE_ARIKUI_ID, Vol3IchinenseiResource.A2_13_3_IPHONE_ARIKUI_ID);

		this.ttrMandoriru = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_14_1_IPHONE_MANDORIRU_ID);
		this.tttrMandoriru = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_14_2_IPHONE_MANDORIRU_ID, Vol3IchinenseiResource.A2_14_3_IPHONE_MANDORIRU_ID);

		this.ttrKapibara = ttpLibIchinensei1.get(Vol3IchinenseiResource.A2_15_1_IPHONE_KAPIBARA_ID);
		this.tttrKapibara = getTiledTextureFromPacker(ttpIchinensei1, Vol3IchinenseiResource.A2_15_2_IPHONE_KAPIBARA_ID, Vol3IchinenseiResource.A2_15_3_IPHONE_KAPIBARA_ID);

		this.ttrAmuruhyou = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_16_1_IPHONE_AMURUHYOU_ID);
		this.tttrAmuruhyou = getTiledTextureFromPacker(ttpIchinensei2, Vol3IchinenseiResource.A2_16_2_IPHONE_AMURUHYOU_ID, Vol3IchinenseiResource.A2_16_3_IPHONE_AMURUHYOU_ID);

		this.ttrOtosay = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_17_1_IPHONE_OTOSAY_ID);
		this.tttrOtosay = getTiledTextureFromPacker(ttpIchinensei2, Vol3IchinenseiResource.A2_17_2_IPHONE_OTOSAY_ID, Vol3IchinenseiResource.A2_17_3_IPHONE_OTOSAY_ID);

		this.ttrIruka = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_18_1_IPHONE_IRUKA_ID);
		this.tttrIruka = getTiledTextureFromPacker(ttpIchinensei2, Vol3IchinenseiResource.A2_18_2_IPHONE_IRUKA_ID, Vol3IchinenseiResource.A2_18_3_IPHONE_IRUKA_ID);

		this.ttrKuma = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_19_1_IPHONE_KUMA_ID);
		this.tttrKuma = getTiledTextureFromPacker(ttpIchinensei2, Vol3IchinenseiResource.A2_19_2_IPHONE_KUMA_ID, Vol3IchinenseiResource.A2_19_3_IPHONE_KUMA_ID);

		this.ttrKusa1 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_20_1_IPHONE_KUSA_ID);
		this.ttrKusa2 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_20_2_IPHONE_KUSA_ID);
		this.ttrKusa3 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_20_3_IPHONE_KUSA_ID);
		this.ttrKusa4 = ttpLibIchinensei2.get(Vol3IchinenseiResource.A2_20_4_IPHONE_KUSA_ID);
		// mLayoutTexture = new BitmapTextureAtlas(this.getTextureManager(), 1,
		// 1,
		// TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// mLayoutTextureRegion = new TextureRegion(mLayoutTexture, 0, 0, 0, 0);
	}

	@Override
	protected void loadKaraokeSound() {
		mp3Sisonosetene = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_6_SISONOSETENE);
		mp3Pengin = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_11_PENGIN);
		mp3Pengin_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_11_PENGIN_VO);
		mp3Arikui = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_13_OARIKUI);
		mp3Arikui_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_13_OARIKUI_VO);
		mp3Akashia = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_12_AKASHIA);
		mp3Akashia_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_12_AKASHIA_VO);
		mp3Mandoriru = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_14_MANDORIRU);
		mp3Mandoriru_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_14_MANDORIRU_VO);
		mp3Kapibara = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_15_KAPIBARA);
		mp3Kapibara_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_15_KAPIBARA_VO);
		mp3Amuruhyou = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_16_AMURUHYOU);
		mp3Amuruhyou_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_16_AMURUHYOU_VO);
		mp3Otosay = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_17_OTOSAY);
		mp3Otosay_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_17_OTOSAY_VO);
		mp3Iruka = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_18_IROWAKE);
		mp3Iruka_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_18_IROWAKE_VO);
		mp3Kuma = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_19_KUMA);
		mp3Kuma_vo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_19_KUMA_VO);
		mp3Kodomo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_22_KODOMO);
		mp3Onigirikorogaru = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_24_ONIGIRIKOROGARU);
		mp3Sakura = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_5_SAKURA);
		mp3Wai = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_23_WAI);
		mp3Siso = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_6_SISO);
		mp3Yoisyo1 = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_5_YOISYO1);
		mp3Yoisyo2 = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_5_YOISYO2);
		mp3Hakusyu = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_4_HAKUSYU);
		mp3Onajiomosa = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_4_ONAJIOMOSA);
		mp3Yata = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_4_YATA);
		mp3Gogogo = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_GOGOGO);
		mp3Pakun = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_23_PAKUN);
		mp3Musya = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_23_MUSYA);
		mp3Siso_noseru = loadSoundResourceFromSD(Vol3IchinenseiResource.A2_SISO_NOSERU);
	}

	@Override
	protected void loadKaraokeScene() {
		if (mScene == null)
			mScene = new Scene();
		/** setBackground Scene */

		sBackground2 = new Sprite(0, 0, ttrBackground2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);

		sBackground3 = new Sprite(45, 75, ttrBackground3, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground3);

		sBackground1 = new Sprite(0, 0, ttrBackground1, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground1);

		/** Create Sprite **/
		// /** Kusa **/
		// ---
		// sKusa1 = new Sprite(17, 249, ttrKusa1,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sKusa1);
		//
		// sKusa2 = new Sprite(18, 412, ttrKusa2,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sKusa2);
		//
		// sKusa3 = new Sprite(137, 411, ttrKusa3,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sKusa3);
		//
		// sKusa4 = new Sprite(480, 411, ttrKusa4,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sKusa4);

		/** SAkura **/
		sSakuraLeft = new Sprite(-37, -43, ttrSakuraLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakuraLeft);

		sSakuraRight = new Sprite(621, -29, ttrSakuraRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakuraRight);

		sSakura52 = new Sprite(198, 71, ttrSakura52, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura52);

		sSakura53 = new Sprite(72, 5, ttrSakura53, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura53);

		sSakura54 = new Sprite(205, -23, ttrSakura54, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura54);

		sSakura55 = new Sprite(642, -12, ttrSakura55, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura55);

		sSakura56 = new Sprite(737, -38, ttrSakura56, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura56);

		sSakura57 = new Sprite(782, -11, ttrSakura57, this.getVertexBufferObjectManager());
		mScene.attachChild(sSakura57);

		/** child run **/
		sChildrenBefore = new Sprite(258, 171, ttrChildrenBefore, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildrenBefore);

		sChildRun2 = new Sprite(-999, -999, ttrChildRun2, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildRun2);
		sChildRun2.setVisible(false);

		sChildRun3 = new Sprite(0, 0, ttrChildRun3, this.getVertexBufferObjectManager());
		sChildRun2.attachChild(sChildRun3);
		sChildRun3.setVisible(true);

		sChildRun1 = new Sprite(0, 0, ttrChildRun1, this.getVertexBufferObjectManager());
		sChildRun2.attachChild(sChildRun1);
		sChildRun1.setVisible(true);

		sChildAfter12 = new Sprite(245, -50, ttrChildAfter12, this.getVertexBufferObjectManager());
		sBackground3.attachChild(sChildAfter12);
		sChildAfter12.setVisible(false);

		sChildAfter11 = new Sprite(245, -50, ttrChildAfter11, this.getVertexBufferObjectManager());
		sBackground3.attachChild(sChildAfter11);
		sChildAfter11.setVisible(false);

		sChildAfter22 = new Sprite(245, -50, ttrChildAfter22, this.getVertexBufferObjectManager());
		sBackground3.attachChild(sChildAfter22);
		sChildAfter22.setVisible(false);

		sChildAfter21 = new Sprite(245, -50, ttrChildAfter21, this.getVertexBufferObjectManager());
		sBackground3.attachChild(sChildAfter21);
		sChildAfter21.setVisible(false);

		sChildAfter31 = new Sprite(430, 76, ttrChildAfter31, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildAfter31);
		sChildAfter31.setVisible(false);

		/** end child */

		/** Children **/
		sChildren4 = new Sprite(162, 151, ttrChildren4, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildren4);

		sChildren3 = new Sprite(109, 198, ttrChildren3, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildren3);

		sChildren2 = new Sprite(77, 273, ttrChildren2, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildren2);

		sChildren1 = new Sprite(56, 345, ttrChildren1, this.getVertexBufferObjectManager());
		mScene.attachChild(sChildren1);
		//
		asChildren4 = new AnimatedSprite(162, 151, tttrChildren4, this.getVertexBufferObjectManager());
		mScene.attachChild(asChildren4);
		asChildren4.setVisible(false);

		asChildren3 = new AnimatedSprite(109, 198, tttrChildren3, this.getVertexBufferObjectManager());
		mScene.attachChild(asChildren3);
		asChildren3.setVisible(false);

		asChildren2 = new AnimatedSprite(77, 273, tttrChildren2, this.getVertexBufferObjectManager());
		mScene.attachChild(asChildren2);
		asChildren2.setVisible(false);

		asChildren1 = new AnimatedSprite(56, 345, tttrChildren1, this.getVertexBufferObjectManager());
		mScene.attachChild(asChildren1);
		asChildren1.setVisible(false);
		//

		/** Animals **/
		sKuma = new Sprite(569, 102, ttrKuma, this.getVertexBufferObjectManager());
		mScene.attachChild(sKuma);
		sKuma.setVisible(false);

		sIruka = new Sprite(577, 127, ttrIruka, this.getVertexBufferObjectManager());
		mScene.attachChild(sIruka);
		sIruka.setVisible(false);

		sOtosay = new Sprite(609, 94, ttrOtosay, this.getVertexBufferObjectManager());
		mScene.attachChild(sOtosay);
		sOtosay.setVisible(false);

		sAmuruhyou = new Sprite(679, 225, ttrAmuruhyou, this.getVertexBufferObjectManager());
		mScene.attachChild(sAmuruhyou);
		sAmuruhyou.setVisible(false);

		sKapibara = new Sprite(713, 223, ttrKapibara, this.getVertexBufferObjectManager());
		mScene.attachChild(sKapibara);
		sKapibara.setVisible(false);

		sMandoriru = new Sprite(769, 297, ttrMandoriru, this.getVertexBufferObjectManager());
		mScene.attachChild(sMandoriru);
		sMandoriru.setVisible(false);

		sArikui = new Sprite(775, 384, ttrArikui, this.getVertexBufferObjectManager());
		mScene.attachChild(sArikui);
		sArikui.setVisible(false);

		sAkashia = new Sprite(831, 414, ttrAkashia, this.getVertexBufferObjectManager());
		mScene.attachChild(sAkashia);
		sAkashia.setVisible(false);

		sPengin = new Sprite(852, 420, ttrPengin, this.getVertexBufferObjectManager());
		mScene.attachChild(sPengin);
		sPengin.setVisible(false);

		asKuma = new AnimatedSprite(-999, -999, tttrKuma, this.getVertexBufferObjectManager());
		mScene.attachChild(asKuma);
		asKuma.setVisible(false);

		asIruka = new AnimatedSprite(-999, -999, tttrIruka, this.getVertexBufferObjectManager());
		mScene.attachChild(asIruka);
		asIruka.setVisible(false);

		asOtosay = new AnimatedSprite(-999, -999, tttrOtosay, this.getVertexBufferObjectManager());
		mScene.attachChild(asOtosay);
		asOtosay.setVisible(false);

		asAmuruhyou = new AnimatedSprite(-999, -999, tttrAmuruhyou, this.getVertexBufferObjectManager());
		mScene.attachChild(asAmuruhyou);
		asAmuruhyou.setVisible(false);

		asKapibara = new AnimatedSprite(-999, -999, tttrKapibara, this.getVertexBufferObjectManager());
		mScene.attachChild(asKapibara);
		asKapibara.setVisible(false);

		asMandoriru = new AnimatedSprite(-999, -999, tttrMandoriru, this.getVertexBufferObjectManager());
		mScene.attachChild(asMandoriru);
		asMandoriru.setVisible(false);

		asArikui = new AnimatedSprite(-999, -999, tttrArikui, this.getVertexBufferObjectManager());
		mScene.attachChild(asArikui);
		asArikui.setVisible(false);

		asAkashia = new AnimatedSprite(-999, -999, tttrAkashia, this.getVertexBufferObjectManager());
		mScene.attachChild(asAkashia);
		asAkashia.setVisible(false);

		asPengin = new AnimatedSprite(-999, -999, tttrPengin, this.getVertexBufferObjectManager());
		mScene.attachChild(asPengin);
		asPengin.setVisible(false);

		/** SeeSaw **/
		sSeeSaw62 = new Sprite(171, 352, ttrSeeSaw62, this.getVertexBufferObjectManager());
		mScene.attachChild(sSeeSaw62);

		// tam giac
		sSeeSaw61 = new Sprite(425, 373, ttrSeeSaw61, this.getVertexBufferObjectManager());
		mScene.attachChild(sSeeSaw61);

		// vong tron mot nua
		sSeeSaw63Left = new Sprite(164, 453, ttrSeeSaw63, this.getVertexBufferObjectManager());
		mScene.attachChild(sSeeSaw63Left);

		sSeeSaw63Right = new Sprite(721, 453, ttrSeeSaw63, this.getVertexBufferObjectManager());
		mScene.attachChild(sSeeSaw63Right);

		/** Kusa **/
		sKusa1 = new Sprite(17, 249, ttrKusa1, this.getVertexBufferObjectManager());
		mScene.attachChild(sKusa1);

		sKusa2 = new Sprite(18, 412, ttrKusa2, this.getVertexBufferObjectManager());
		mScene.attachChild(sKusa2);

		sKusa3 = new Sprite(137, 411, ttrKusa3, this.getVertexBufferObjectManager());
		mScene.attachChild(sKusa3);

		sKusa4 = new Sprite(480, 411, ttrKusa4, this.getVertexBufferObjectManager());
		mScene.attachChild(sKusa4);
		// het co

		// sSeeSaw63Left = new Sprite(164, 453, ttrSeeSaw63,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sSeeSaw63Left);
		//
		// sSeeSaw63Right = new Sprite(721, 453, ttrSeeSaw63,
		// this.getVertexBufferObjectManager());
		// mScene.attachChild(sSeeSaw63Right);

		sSeeSaw64 = new Sprite(0, 0, ttrSeeSaw64, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sSeeSaw64);
		sSeeSaw64.setVisible(false);

		sSeeSaw65 = new Sprite(0, 0, ttrSeeSaw65, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sSeeSaw65);
		sSeeSaw65.setVisible(false);

		// last Animals
		sPenginLast = new Sprite(418, -80, ttrPengin, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sPenginLast);
		sPenginLast.setVisible(false);

		sAkashiaLast = new Sprite(399, -90, ttrAkashia, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sAkashiaLast);
		sAkashiaLast.setVisible(false);

		sArikuiLast = new Sprite(334, -46, ttrArikui, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sArikuiLast);
		sArikuiLast.setVisible(false);

		sMandoriruLast = new Sprite(383, -130, ttrMandoriru, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sMandoriruLast);
		sMandoriruLast.setVisible(false);

		sKapibaraLast = new Sprite(282, -170, ttrKapibara, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sKapibaraLast);
		sKapibaraLast.setVisible(false);

		sAmuruhyouLast = new Sprite(308, -150, ttrAmuruhyou, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sAmuruhyouLast);
		sAmuruhyouLast.setVisible(false);

		sOtosayLast = new Sprite(320, -238, ttrOtosay, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sOtosayLast);
		sOtosayLast.setVisible(false);

		sIrukaLast = new Sprite(330, -90, ttrIruka, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sIrukaLast);
		sIrukaLast.setVisible(false);

		sKumaLast = new Sprite(327, -157, ttrKuma, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(sKumaLast);
		sKumaLast.setVisible(false);

		// last childs
		asChildLast1 = new AnimatedSprite(-999, -999, tttrChildLast1, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(asChildLast1);
		asChildLast1.setVisible(false);

		asChildLast2 = new AnimatedSprite(-999, -999, tttrChildLast2, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(asChildLast2);
		asChildLast2.setVisible(false);

		asChildLast3 = new AnimatedSprite(-999, -999, tttrChildLast3, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(asChildLast3);
		asChildLast3.setVisible(false);

		asChildLast4 = new AnimatedSprite(-999, -999, tttrChildLast4, this.getVertexBufferObjectManager());
		sSeeSaw62.attachChild(asChildLast4);
		asChildLast4.setVisible(false);

		/** Create sita **/
		sSita = new Sprite(-37, 500, ttrSita, this.getVertexBufferObjectManager());
		mScene.attachChild(sSita);
		sSita.setVisible(false);

		for (int i = 0; i < sSakura51.length; i++) {
			sSakura51[i] = new Sprite(-999, -999, ttrSakura51, this.getVertexBufferObjectManager());
			mScene.attachChild(sSakura51[i]);
			sSakura51[i].setVisible(false);
		}

		// mSpriteAttach = new Sprite(0, 0, this.mLayoutTextureRegion,
		// this.getVertexBufferObjectManager()){
		// @Override
		// public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		// float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// Log.d("Day ne","Ngon roi");
		// return true;
		// }
		// };
		// mSpriteAttach.setSize(960, 640);
		// mSpriteAttach.setAlpha(0.0f);
		// this.mScene.attachChild(mSpriteAttach);
		// this.mScene.unregisterTouchArea(mSpriteAttach);
		addGimmicsButton(mScene, Vol3IchinenseiResource.SOUND_GIMMIC, Vol3IchinenseiResource.IMAGE_GIMMIC, ttpLibIchinensei3);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("ichinensei/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("ichinensei/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "ichinensei/gfx/");
		super.onCreateResources();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		if (listIchinensei.size() > 0) {
			for (int i = 0; i < listIchinensei.size(); i++) {
				listIchinensei.get(i).clearEntityModifiers();
				listIchinensei.get(i).clearUpdateHandlers();
				pSpriteIchinensei.onHandleRecycleItem(listIchinensei.get(i));

			}
		}
		listIchinensei.clear();
		if (pSpriteIchinensei != null) {
			pSpriteIchinensei = null;
		}

		restartGame();

		sChildAfter11.setVisible(false);
		sChildAfter11.clearEntityModifiers();
		sChildAfter12.setVisible(false);
		sChildAfter12.clearEntityModifiers();
		sChildAfter21.setVisible(false);
		sChildAfter21.clearEntityModifiers();
		sChildAfter31.setVisible(false);
		sChildAfter31.clearEntityModifiers();

		sChildRun2.clearEntityModifiers();
		sChildRun2.setRotation(0);
		sChildRun2.setScale(1);
		sChildRun2.setVisible(false);

		sChildrenBefore.setPosition(sChildrenBefore.getmXFirst(), sChildrenBefore.getmYFirst());
		sChildrenBefore.setVisible(true);
		sChildrenBefore.clearEntityModifiers();
		isTouchChilds = true;
		isTouchChildAfter = true;

		sSita.setVisible(false);
		mp3Hakusyu.stop();
		mp3Onajiomosa.stop();
		mp3Yata.stop();
		mp3Sisonosetene.stop();
	}

	@Override
	public synchronized void onResumeGame() {
		// sAnimals = null;
		// --------------------------------------------------------------
		// 20130326
		// -------------------------------------------------------------
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isRestricted())
					isPause = false;
			}
		}, 1000);
		// --------------------------------------------------------------
		// 20130326 END
		// -------------------------------------------------------------
		mScene.detachChildren();
		loadKaraokeScene();

		pSpriteIchinensei = new IchinenseiSprite(ttrSakura51);
		// sPengin
		initial();
		randomVisibleAnimals();
		childSleep();
		super.onResumeGame();
		setGimmicBringToFront();
	}

	private void initial() {
		isTouch = true;

		isTouchChildren1 = false;
		isTouchChildren2 = false;
		isTouchChildren3 = false;
		isTouchChildren4 = false;

		isTouchPengin = false;
		isTouchAkashia = false;

		isTouchArikui = false;
		isTouchMandoriru = false;

		isTouchKapibara = false;
		isTouchAmuruhyou = false;

		isTouchOtosay = false;
		isTouchIkura = false;
		isTouchKuma = false;

		isTouchHaikei = true;
		isTouchChilds = true;
		isTouchChildAfter = false;

		isTouchSakura = true;

		isMp3Pengin = false;
		isMp3Akashia = false;
		isMp3Arikui = false;
		isMp3Mandoriru = false;
		isMp3Kapibara = false;
		isMp3Amuruhyou = false;
		isMp3Otosay = false;
		isMp3Ikura = false;
		isMp3Kuma = false;

		isTouchAction = true;
		// if (sAnimals != null) {
		// sAnimals.setPosition(-1000, -1000);
		// Log.e("NULLLLLLLLLLLLLLLLLLLLLLL",
		// "bnulllllllllllllllllllllllllllll");
		//
		// }

		sAnimals = null;

		for (int i = 0; i < isTouchAni.length; i++) {
			isTouchAni[i] = true;
		}
	}

	private void randomVisibleAnimals() {
		Random random1 = new Random();
		int iRandom1 = random1.nextInt(2);
		if (iRandom1 == 0) {
			sPengin.setVisible(true);
			sAkashia.setVisible(false);
		} else {
			sPengin.setVisible(false);
			sAkashia.setVisible(true);
		}

		Random random2 = new Random();
		int iRandom2 = random2.nextInt(2);
		if (iRandom2 == 0) {
			sArikui.setVisible(false);
			sMandoriru.setVisible(true);
		} else {
			sArikui.setVisible(true);
			sMandoriru.setVisible(false);
		}

		Random random3 = new Random();
		int iRandom3 = random3.nextInt(2);
		if (iRandom3 == 0) {
			sKapibara.setVisible(true);
			sAmuruhyou.setVisible(false);
		} else {
			sKapibara.setVisible(false);
			sAmuruhyou.setVisible(true);
		}

		Random random4 = new Random();
		int iRandom4 = random4.nextInt(3);
		if (iRandom4 == 0) {
			sKuma.setVisible(true);
			sIruka.setVisible(false);
			sOtosay.setVisible(false);
		} else if (iRandom4 == 1) {
			sKuma.setVisible(false);
			sIruka.setVisible(true);
			sOtosay.setVisible(false);
		} else {
			sKuma.setVisible(false);
			sIruka.setVisible(false);
			sOtosay.setVisible(true);
		}
		setZIndexs(asOtosay, asIruka, asKuma, asKapibara, asAmuruhyou, asArikui, asMandoriru, asPengin, asAkashia, sSeeSaw62, sSeeSaw61);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// --------------------------------------------------------------
		// 20130326
		// -------------------------------------------------------------
		if (isPause) {
			return false;
		}
		// --------------------------------------------------------------
		// 20130326 END
		// -------------------------------------------------------------

		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (!isTouch) {
			return true;
		}

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			lastX = pX;
			lastY = pY;
			Log.d(TAG, "DOWN............");
			if (asChildLast1.contains(pX, pY) && asChildLast1.isVisible()) {
				isTouchChildren1 = true;
			} else if (asChildLast2.contains(pX, pY) && asChildLast2.isVisible()) {
				isTouchChildren2 = true;
			} else if (asChildLast3.contains(pX, pY) && asChildLast3.isVisible()) {
				isTouchChildren3 = true;
			} else if (asChildLast4.contains(pX, pY) && asChildLast4.isVisible()) {
				isTouchChildren4 = true;
			} else if (sChildren1.contains(pX, pY) && sChildren1.isVisible()) {
				_touchChild(sChildren1);
				isTouchChildren1 = true;
			} else if (sChildren2.contains(pX, pY) && sChildren2.isVisible()) {
				_touchChild(sChildren2);
				isTouchChildren2 = true;
			} else if (sChildren3.contains(pX, pY) && sChildren3.isVisible()) {
				_touchChild(sChildren3);
				isTouchChildren3 = true;
			} else if (sChildren4.contains(pX, pY) && sChildren4.isVisible()) {
				_touchChild(sChildren4);
				isTouchChildren4 = true;
			} else if (isTouchAni[0] && (sPengin.contains(pX, pY) && sPengin.isVisible())) {
				_touchChild(sPengin);
				mp3Pengin.play();
				isTouchPengin = true;
				isMp3Pengin = true;
			} else if (sPenginLast.contains(pX, pY) && sPenginLast.isVisible()) {
				isTouchPengin = true;
				isMp3Pengin = true;
			} else if (isTouchAni[1] && (sAkashia.contains(pX, pY) && sAkashia.isVisible())) {
				_touchChild(sAkashia);
				mp3Akashia.play();
				isTouchAkashia = true;
				isMp3Akashia = true;
			} else if (sAkashiaLast.contains(pX, pY) && sAkashiaLast.isVisible()) {
				isTouchAkashia = true;
				isMp3Akashia = true;
			} else if (isTouchAni[2] && (sArikui.contains(pX, pY) && sArikui.isVisible())) {
				_touchChild(sArikui);
				mp3Arikui.play();
				isTouchArikui = true;
				isMp3Arikui = true;
			} else if (sArikuiLast.contains(pX, pY) && sArikuiLast.isVisible()) {
				isTouchArikui = true;
				isMp3Arikui = true;
			} else if (isTouchAni[3] && (sMandoriru.contains(pX, pY) && sMandoriru.isVisible())) {
				_touchChild(sMandoriru);
				mp3Mandoriru.play();
				isTouchMandoriru = true;
				isMp3Mandoriru = true;
			} else if (sMandoriruLast.contains(pX, pY) && sMandoriruLast.isVisible()) {
				isTouchMandoriru = true;
				isMp3Mandoriru = true;
			} else if (isTouchAni[4] && (sKapibara.contains(pX, pY) && sKapibara.isVisible())) {
				_touchChild(sKapibara);
				mp3Kapibara.play();
				isTouchKapibara = true;
				isMp3Kapibara = true;
			} else if (sKapibaraLast.contains(pX, pY) && sKapibaraLast.isVisible()) {
				isTouchKapibara = true;
				isMp3Kapibara = true;
			} else if (isTouchAni[5] && (sAmuruhyou.contains(pX, pY) && sAmuruhyou.isVisible())) {
				_touchChild(sAmuruhyou);
				mp3Amuruhyou.play();
				isTouchAmuruhyou = true;
				isMp3Amuruhyou = true;
			} else if (sAmuruhyouLast.contains(pX, pY) && sAmuruhyouLast.isVisible()) {
				isTouchAmuruhyou = true;
				isMp3Amuruhyou = true;
			} else if (isTouchAni[6] && (sOtosay.contains(pX, pY) && sOtosay.isVisible())) {
				_touchChild(sOtosay);
				mp3Otosay.play();
				isTouchOtosay = true;
				isMp3Otosay = true;
			} else if (sOtosayLast.contains(pX, pY) && sOtosayLast.isVisible()) {
				isTouchOtosay = true;
				isMp3Otosay = true;
			} else if (isTouchAni[7] && (sIruka.contains(pX, pY) && sIruka.isVisible())) {
				_touchChild(sIruka);
				mp3Iruka.play();
				isTouchIkura = true;
				isMp3Ikura = true;
			} else if (sIrukaLast.contains(pX, pY) && sIrukaLast.isVisible()) {
				isTouchIkura = true;
				isMp3Ikura = true;
			} else if (isTouchAni[8] && (sKuma.contains(pX, pY) && sKuma.isVisible())) {
				_touchChild(sKuma);
				mp3Kuma.play();
				isTouchKuma = true;
				isMp3Kuma = true;
			} else if (sKumaLast.contains(pX, pY) && sKumaLast.isVisible()) {
				isTouchKuma = true;
				isMp3Kuma = true;
			} else if (sChildrenBefore.contains(pX, pY)) {
				Log.d(TAG, "tang vao em be'");
				touchChilds();
			} else if (checkContains(sBackground3, 200, 0, 413, 222, pX, pY)) {
				Log.d(TAG, "tang vao dinh nui");
				touchHaikei();
			} else if (sChildAfter11.contains(pX, pY)) {
				Log.d(TAG, "touch after children");
				touchChildAfter();
			} else if (sSakuraLeft.contains(pX, pY) || sSakuraRight.contains(pX, pY)) {
				touchSakura();
			}
		}

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE && isTouch && Math.abs((lastX + lastY) - (pX + pY)) >= 20) {
			Log.e(TAG, "MOVE............");
			if (isTouchChildren1) {
				_moveChilds(sChildren1, asChildren1, asChildLast1, pX, pY);
			} else if (isTouchChildren2) {
				_moveChilds(sChildren2, asChildren2, asChildLast2, pX, pY);
			} else if (isTouchChildren3) {
				_moveChilds(sChildren3, asChildren3, asChildLast3, pX, pY);
			} else if (isTouchChildren4) {
				_moveChilds(sChildren4, asChildren4, asChildLast4, pX, pY);
			} else if (isTouchPengin) {
				if (isMp3Pengin) {
					isMp3Pengin = false;
					mp3Pengin.stop();
					mp3Pengin_vo.play();
				}

				_moveAnimal(sPengin, asPengin, sPenginLast, pX, pY);
			} else if (isTouchAkashia) {
				if (isMp3Akashia) {
					isMp3Akashia = false;
					mp3Akashia.stop();
					mp3Akashia_vo.play();
				}
				_moveAnimal(sAkashia, asAkashia, sAkashiaLast, pX, pY);
			} else if (isTouchArikui) {
				if (isMp3Arikui) {
					isMp3Arikui = false;
					mp3Arikui.stop();
					mp3Arikui_vo.play();
				}
				_moveAnimal(sArikui, asArikui, sArikuiLast, pX, pY);
			} else if (isTouchMandoriru) {
				if (isMp3Mandoriru) {
					isMp3Mandoriru = false;
					mp3Mandoriru.stop();
					mp3Mandoriru_vo.play();
				}

				_moveAnimal(sMandoriru, asMandoriru, sMandoriruLast, pX, pY);
			} else if (isTouchKapibara) {
				if (isMp3Kapibara) {
					isMp3Kapibara = false;
					mp3Kapibara.stop();
					mp3Kapibara_vo.play();
				}
				_moveAnimal(sKapibara, asKapibara, sKapibaraLast, pX, pY);
			} else if (isTouchAmuruhyou) {
				if (isMp3Amuruhyou) {
					isMp3Amuruhyou = false;
					mp3Amuruhyou.stop();
					mp3Amuruhyou_vo.play();
				}
				_moveAnimal(sAmuruhyou, asAmuruhyou, sAmuruhyouLast, pX, pY);
			} else if (isTouchOtosay) {
				if (isMp3Otosay) {
					isMp3Otosay = false;
					mp3Otosay.stop();
					mp3Otosay_vo.play();
				}

				_moveAnimal(sOtosay, asOtosay, sOtosayLast, pX, pY);
			} else if (isTouchIkura) {
				if (isMp3Ikura) {
					isMp3Ikura = false;
					mp3Iruka.stop();
					mp3Iruka_vo.play();
				}
				_moveAnimal(sIruka, asIruka, sIrukaLast, pX, pY);
			} else if (isTouchKuma) {
				if (isMp3Kuma) {
					isMp3Kuma = false;
					mp3Kuma.stop();
					mp3Kuma_vo.play();
				}
				_moveAnimal(sKuma, asKuma, sKumaLast, pX, pY);
			}
		}

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP && isTouch) {
			Log.e(TAG, "Up................");
			if (isTouchChildren1) {
				Log.d(TAG, "ACTION_UP: Children1");
				isTouchChildren1 = false;
				collidesChildWithRectangle(sChildren1, asChildren1, asChildLast1, pX, pY);
			} else if (isTouchChildren2) {
				isTouchChildren2 = false;
				collidesChildWithRectangle(sChildren2, asChildren2, asChildLast2, pX, pY);
			} else if (isTouchChildren3) {
				isTouchChildren3 = false;
				collidesChildWithRectangle(sChildren3, asChildren3, asChildLast3, pX, pY);
			} else if (isTouchChildren4) {
				isTouchChildren4 = false;
				collidesChildWithRectangle(sChildren4, asChildren4, asChildLast4, pX, pY);
			} else {
				update(true, pX, pY);
			}
		} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_CANCEL && isTouch) {
			try {
				if (isTouchChildren1) {
					pX = -1000;
					pY = -1000;
					Log.d(TAG, "ACTION_UP: Children1");
					isTouchChildren1 = false;
					collidesChildWithRectangle(sChildren1, asChildren1, asChildLast1, pX, pY);
				} else if (isTouchChildren2) {
					isTouchChildren2 = false;
					collidesChildWithRectangle(sChildren2, asChildren2, asChildLast2, pX, pY);
				} else if (isTouchChildren3) {
					isTouchChildren3 = false;
					collidesChildWithRectangle(sChildren3, asChildren3, asChildLast3, pX, pY);
				} else if (isTouchChildren4) {
					isTouchChildren4 = false;
					collidesChildWithRectangle(sChildren4, asChildren4, asChildLast4, pX, pY);
				}

			} catch (Exception e) {

			}
			try {

				update(false, -1000, -1000);
			} catch (Exception e) {

			}
			cancel = true;
		}

		return true;
	}

	boolean cancel = false;

	int index = -1;

	private void update(boolean isUp, int pX, int pY) {
		index = -1;
		if (!isUp) {
			pX = -1000;
			pY = -1000;
		}
		if (isTouchPengin) {
			isTouchPengin = false;
			isTouchAni[0] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[0] = true;
				}
			}));
			collidesAnimalWithRectangle(sPengin, asPengin, pX, pY);
		} else if (isTouchAkashia) {
			isTouchAkashia = false;
			isTouchAni[1] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[1] = true;
				}
			}));
			collidesAnimalWithRectangle(sAkashia, asAkashia, pX, pY);
		} else if (isTouchArikui) {
			isTouchArikui = false;
			isTouchAni[2] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[2] = true;
				}
			}));
			collidesAnimalWithRectangle(sArikui, asArikui, pX, pY);
		} else if (isTouchMandoriru) {
			isTouchMandoriru = false;
			isTouchAni[3] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[3] = true;
				}
			}));
			collidesAnimalWithRectangle(sMandoriru, asMandoriru, pX, pY);
		} else if (isTouchKapibara) {
			isTouchKapibara = false;
			isTouchAni[4] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[4] = true;
				}
			}));
			collidesAnimalWithRectangle(sKapibara, asKapibara, pX, pY);
		} else if (isTouchAmuruhyou) {
			isTouchAmuruhyou = false;
			isTouchAni[5] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[5] = true;
				}
			}));
			collidesAnimalWithRectangle(sAmuruhyou, asAmuruhyou, pX, pY);
		} else if (isTouchOtosay) {
			isTouchOtosay = false;
			isTouchAni[6] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[6] = true;
				}
			}));
			collidesAnimalWithRectangle(sOtosay, asOtosay, pX, pY);
		} else if (isTouchIkura) {
			isTouchIkura = false;
			isTouchAni[7] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[7] = true;
				}
			}));
			collidesAnimalWithRectangle(sIruka, asIruka, pX, pY);
		} else if (isTouchKuma) {
			isTouchKuma = false;
			isTouchAni[8] = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAni[8] = true;
				}
			}));
			collidesAnimalWithRectangle(sKuma, asKuma, pX, pY);
		}
	}

	private void _touchChild(final Sprite sChildren) {
		if (isTouchAction) {
			isTouchAction = false;
			mp3Sisonosetene.stop();
			sChildren.registerEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.1f, sChildren.getmYFirst(), sChildren.getmYFirst() - 15), new MoveYModifier(0.1f, sChildren.getmYFirst() - 15, sChildren.getmYFirst(),
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							isTouchAction = true;
							sChildren.setPosition(sChildren.getmXFirst(), sChildren.getmYFirst());
						}
					})));
		}
	}

	private void _moveChilds(Sprite sChildren, AnimatedSprite asChildren, AnimatedSprite asLastChild, int pX, int pY) {
		if (!isMoving) {
			isMoving = true;
			// flashSprite(sSeeSaw64);
			sSeeSaw64.setVisible(true);
			if (htListChild.containsKey(asLastChild)) {
				htListChild.remove(asLastChild);
				compareChildWithAnimal();
			}
			sChildren.setVisible(false);
			asLastChild.setVisible(false);
			asChildren.setVisible(true);
			if (!asChildren.isAnimationRunning()) {
				long pDurations[] = new long[] { 400, 400 };
				int pFrames[] = new int[] { 0, 1 };
				asChildren.animate(pDurations, pFrames, -1);
			}
			asChildren.setZIndex(500);
			this.mScene.sortChildren();
			mScene.unregisterUpdateHandler(timerChild);
			mEngine.unregisterUpdateHandler(timerHandler);
		}
		asChildren.setPosition(pX - asChildren.getWidth() / 2, pY - asChildren.getHeight() / 2);
	}

	private void _moveAnimal(Sprite sAnimal, AnimatedSprite asAnimal, Sprite sAnimalLast, int pX, int pY) {
		Log.d(TAG, "move animals.................................");
		if (!isMoveAnimal) {
			mScene.unregisterUpdateHandler(timerChild);
			mEngine.unregisterUpdateHandler(timerHandler);
			iAnimal = 0;
			isMoveAnimal = true;
			// flashSprite(sSeeSaw65);
			sSeeSaw65.setVisible(true);
			sAnimal.setVisible(false);
			asAnimal.setVisible(true);
			if (!asAnimal.isAnimationRunning()) {
				long pDurations[] = new long[] { 400, 400 };
				int pFrames[] = new int[] { 0, 1 };
				asAnimal.animate(pDurations, pFrames, -1);
			}
			asAnimal.setZIndex(500);
			this.mScene.sortChildren();
			sAnimalLast.setVisible(false);
			if (this.sAnimals != null) {
				if (!sAnimal.equals(this.sAnimals)) {
					if (sAnimals.equals(sPengin)) {
						sPenginLast.setVisible(false);
					} else if (sAnimals.equals(sAkashia)) {
						sAkashiaLast.setVisible(false);
					} else if (sAnimals.equals(sArikui)) {
						sArikuiLast.setVisible(false);
					} else if (sAnimals.equals(sMandoriru)) {
						sMandoriruLast.setVisible(false);
					} else if (sAnimals.equals(sKapibara)) {
						sKapibaraLast.setVisible(false);
					} else if (sAnimals.equals(sAmuruhyou)) {
						sAmuruhyouLast.setVisible(false);
					} else if (sAnimals.equals(sOtosay)) {
						sOtosayLast.setVisible(false);
					} else if (sAnimals.equals(sIruka)) {
						sIrukaLast.setVisible(false);
					} else if (sAnimals.equals(sKuma)) {
						sKumaLast.setVisible(false);
					}
					sAnimals.setVisible(true);
					sAnimals = null;
				}
				compareChildWithAnimal();
				setZIndexs(sOtosay, sIruka, sKuma, sKapibara, sAmuruhyou, sArikui, sMandoriru, sPengin, sAkashia, sSeeSaw62, sSeeSaw61);
			}

		}
		asAnimal.setPosition(pX - asAnimal.getWidth() / 2, pY - asAnimal.getHeight() / 2);
	}

	private void setZIndexs(final Sprite... asChild) {
		int zIndex = 100;
		// zIndex++;
		for (int i = 0; i < asChild.length; i++) {
			asChild[i].setZIndex(zIndex++);
		}
		// theem
		sSeeSaw61.setZIndex(zIndex + 1);
		sSeeSaw63Right.setZIndex(zIndex + 1);
		sSeeSaw63Left.setZIndex(zIndex + 1);
		sKusa1.setZIndex(zIndex + 1);
		sKusa2.setZIndex(zIndex + 1);
		sKusa3.setZIndex(zIndex + 1);

		setGimmicBringToFront();
	}

	private void collidesChildWithRectangle(Sprite sChild, AnimatedSprite asChild, AnimatedSprite asChildLast, int pX, int pY) {
		if (!isMoving)
			return;
		if (checkContains(sSeeSaw62, 0, -70, 100, 200, pX, pY)) {
			asChild.setVisible(false);
			asChildLast.setVisible(true);
			if (!htListChild.containsValue(1)) {
				asChildLast.setCurrentTileIndex(2);
				// setPosition for Children
				if (asChildLast.equals(asChildLast1))
					asChildLast.setPosition(iChild1[0][0], iChild1[0][1]);
				else if (asChildLast.equals(asChildLast2))
					asChildLast.setPosition(iChild2[0][0], iChild2[0][1]);
				else if (asChildLast.equals(asChildLast3))
					asChildLast.setPosition(iChild3[0][0], iChild3[0][1]);
				else
					asChildLast.setPosition(iChild4[0][0], iChild4[0][1]);
				asChildLast.setVisible(true);
				asChildLast.setZIndex(389);
				sSeeSaw62.sortChildren();
				htListChild.put(asChildLast, 1);
				compareChildWithAnimal();
				Log.d(TAG, "Size: " + htListChild.size());
			} else {
				asChildLast.setPosition(asChildLast.getmXFirst(), asChildLast.getmYFirst());
				asChildLast.setVisible(false);
				asChild.setPosition(asChild.getmXFirst(), asChild.getmYFirst());
				asChild.setVisible(false);
				sChild.setVisible(true);
			}
		} else if (checkContains(sSeeSaw62, 70, -70, 170, 200, pX, pY)) {
			asChild.setVisible(false);
			asChildLast.setVisible(true);
			if (!htListChild.containsValue(2)) {
				asChildLast.setCurrentTileIndex(2);
				// setPosition for Children
				if (asChildLast.equals(asChildLast1))
					asChildLast.setPosition(iChild1[1][0], iChild1[1][1]);
				else if (asChildLast.equals(asChildLast2))
					asChildLast.setPosition(iChild2[1][0], iChild2[1][1]);
				else if (asChildLast.equals(asChildLast3))
					asChildLast.setPosition(iChild3[1][0], iChild3[1][1]);
				else
					asChildLast.setPosition(iChild4[1][0], iChild4[1][1]);
				asChildLast.setVisible(true);
				asChildLast.setZIndex(388);
				sSeeSaw62.sortChildren();
				htListChild.put(asChildLast, 2);
				compareChildWithAnimal();
				Log.d(TAG, "Size: " + htListChild.size());
			} else {
				asChildLast.setPosition(asChildLast.getmXFirst(), asChildLast.getmYFirst());
				asChildLast.setVisible(false);
				asChild.setPosition(asChild.getmXFirst(), asChild.getmYFirst());
				asChild.setVisible(false);
				sChild.setVisible(true);
			}
		} else if (checkContains(sSeeSaw62, 140, -70, 240, 200, pX, pY)) {
			asChild.setVisible(false);
			asChildLast.setVisible(true);
			if (!htListChild.containsValue(3)) {
				asChildLast.setCurrentTileIndex(2);
				// setPosition for Children
				if (asChildLast.equals(asChildLast1))
					asChildLast.setPosition(iChild1[2][0], iChild1[2][1]);
				else if (asChildLast.equals(asChildLast2))
					asChildLast.setPosition(iChild2[2][0], iChild2[2][1]);
				else if (asChildLast.equals(asChildLast3))
					asChildLast.setPosition(iChild3[2][0], iChild3[2][1]);
				else
					asChildLast.setPosition(iChild4[2][0], iChild4[2][1]);
				asChildLast.setVisible(true);
				asChildLast.setZIndex(387);
				sSeeSaw62.sortChildren();
				htListChild.put(asChildLast, 3);
				compareChildWithAnimal();
				Log.d(TAG, "Size: " + htListChild.size());
			} else {
				asChildLast.setPosition(asChildLast.getmXFirst(), asChildLast.getmYFirst());
				asChildLast.setVisible(false);
				asChild.setPosition(asChild.getmXFirst(), asChild.getmYFirst());
				asChild.setVisible(false);
				sChild.setVisible(true);
			}
		} else if (checkContains(sSeeSaw62, 210, -70, 310, 200, pX, pY)) {
			Log.d(TAG, "O so 4...........................................");
			asChild.setVisible(false);
			asChildLast.setVisible(true);
			if (!htListChild.containsValue(4)) {
				asChildLast.setCurrentTileIndex(2);
				// setPosition for Children
				if (asChildLast.equals(asChildLast1))
					asChildLast.setPosition(iChild1[3][0], iChild1[3][1]);
				else if (asChildLast.equals(asChildLast2))
					asChildLast.setPosition(iChild2[3][0], iChild2[3][1]);
				else if (asChildLast.equals(asChildLast3))
					asChildLast.setPosition(iChild3[3][0], iChild3[3][1]);
				else
					asChildLast.setPosition(iChild4[3][0], iChild4[3][1]);
				asChildLast.setVisible(true);
				asChildLast.setZIndex(386);
				sSeeSaw62.sortChildren();
				htListChild.put(asChildLast, 4);
				compareChildWithAnimal();
				Log.d(TAG, "Size: " + htListChild.size());
			} else {
				asChildLast.setPosition(asChildLast.getmXFirst(), asChildLast.getmYFirst());
				asChildLast.setVisible(false);
				asChild.setPosition(asChild.getmXFirst(), asChild.getmYFirst());
				asChild.setVisible(false);
				sChild.setVisible(true);
			}
		} else {
			asChildLast.setPosition(asChildLast.getmXFirst(), asChildLast.getmYFirst());
			asChildLast.setVisible(false);
			asChild.setPosition(asChild.getmXFirst(), asChild.getmYFirst());
			asChild.setVisible(false);
			sChild.setVisible(true);
			Log.d(TAG, "Size: " + htListChild.size());
			if (iResult == 0 && sAnimals != null) {
				compareChildWithAnimal();
			}
			childSleep();
		}
		isMoving = false;
		sSeeSaw64.setVisible(false);
	}

	private void clearAni() {
		if (!htListChild.containsKey(asChildLast1)) {
			asChildren1.setVisible(false);
			asChildren1.resetLocalToFirst();
			sChildren1.setVisible(true);
		}
		if (!htListChild.containsKey(asChildLast2)) {
			asChildren2.setVisible(false);
			asChildren2.resetLocalToFirst();
			sChildren2.setVisible(true);
		}
		if (!htListChild.containsKey(asChildLast3)) {
			asChildren3.setVisible(false);
			asChildren3.resetLocalToFirst();
			sChildren3.setVisible(true);
		}
		if (!htListChild.containsKey(asChildLast4)) {
			asChildren4.setVisible(false);
			asChildren4.resetLocalToFirst();
			sChildren4.setVisible(true);
		}

	}

	private void collidesAnimalWithRectangle(Sprite sAnimal, AnimatedSprite asAnimal, int pX, int pY) {
		if (!isMoveAnimal) {
			setZIndexs(asOtosay, asIruka, asKuma, asKapibara, asAmuruhyou, asArikui, asMandoriru, asPengin, asAkashia, sSeeSaw62, sSeeSaw61);
			return;
		}

		asAnimal.stopAnimation();
		asAnimal.setPosition(asAnimal.getmXFirst(), asAnimal.getmYFirst());
		asAnimal.setVisible(false);
		if (checkContains(sSeeSaw62, 324, -100, 537, 200, pX, pY)) {
			if (cancel) {
				// return;
			}
			Log.d(TAG, "Collides Ractangle5............................................");
			iAnimal = 0;
			Sprite[] sprites = new Sprite[] { sPengin, sAkashia, sArikui, sMandoriru, sKapibara, sAmuruhyou, sOtosay, sIruka, sKuma };
			Sprite[] showsprites = new Sprite[] { sPenginLast, sAkashiaLast, sArikuiLast, sMandoriruLast, sKapibaraLast, sAmuruhyouLast, sOtosayLast, sIrukaLast, sKumaLast };

			for (int i = 0; i < sprites.length; i++) {
				if (sAnimal.equals(sprites[i])) {
					showsprites[i].setVisible(true);
					sAnimals = sprites[i];
				}
			}

			if (sAnimal.equals(sPengin) || sAnimal.equals(sAkashia)) {
				iAnimal = 1;
			} else if (sAnimal.equals(sArikui) || sAnimal.equals(sMandoriru)) {
				iAnimal = 2;
			} else if (sAnimal.equals(sKapibara) || sAnimal.equals(sAmuruhyou)) {
				iAnimal = 3;
			} else if (sAnimal.equals(sOtosay) | sAnimal.equals(sIruka) || sAnimal.equals(sKuma)) {
				iAnimal = 4;
			}

			compareChildWithAnimal();
		} else {
			sAnimals = null;
			if (sAnimal.equals(sPengin)) {
				sPengin.setVisible(true);
			} else if (sAnimal.equals(sAkashia)) {
				sAkashia.setVisible(true);
			} else if (sAnimal.equals(sArikui)) {
				sArikui.setVisible(true);
			} else if (sAnimal.equals(sMandoriru)) {
				sMandoriru.setVisible(true);
			} else if (sAnimal.equals(sKapibara)) {
				sKapibara.setVisible(true);
			} else if (sAnimal.equals(sAmuruhyou)) {
				sAmuruhyou.setVisible(true);
			} else if (sAnimal.equals(sOtosay)) {
				sOtosay.setVisible(true);
			} else if (sAnimal.equals(sIruka)) {
				sIruka.setVisible(true);
			} else if (sAnimal.equals(sKuma)) {
				sKuma.setVisible(true);
			}
			childSleep();
		}
		isMoveAnimal = false;
		setZIndexs(sOtosay, sIruka, sKuma, sKapibara, sAmuruhyou, sArikui, sMandoriru, sPengin, sAkashia, sSeeSaw62, sSeeSaw61);
		sSeeSaw65.setVisible(false);
	}

	private void compareChildWithAnimal() {
		Log.d(TAG, "Dang kiem tra" + "// " + htListChild.size() + "//" + iAnimal);
		final Enumeration<AnimatedSprite> asChilds;
		asChilds = htListChild.keys();
		if (iAnimal == 0 && htListChild.size() == 0) {
			if (htListChild.size() == 0) {
				iResult = 0;
			} else {
				iResult = -1;
				while (asChilds.hasMoreElements()) {
					asChilds.nextElement().setCurrentTileIndex(1);
				}
			}
			resultCompare(iResult);
		} else {

			if (htListChild.size() == iAnimal) {
				iResult = 0;
				mEngine.registerUpdateHandler(timerHandler = new TimerHandler(0.5f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						isTouch = false;
						iResult = 5;
						mScene.registerUpdateHandler(new TimerHandler(5.0f, new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler arg0) {
								clearAni();
								Log.d(TAG, "xxxxxxxxxxxxxxxxxxx");
							}
						}));
						resultCompare(iResult);
						Log.d(TAG, "Bang nhau roi hahahahaha");
					}
				}));
			} else {
				iResult = iAnimal - htListChild.size();
				while (asChilds.hasMoreElements()) {
					asChilds.nextElement().setCurrentTileIndex(1);
				}

				if (htListChild.containsKey(asChildLast1) || htListChild.containsKey(asChildLast2)) {
					mp3Yoisyo2.stop();
					mp3Yoisyo2.pause();
					mp3Yoisyo2.play();
				}
				if (htListChild.containsKey(asChildLast3) || htListChild.containsKey(asChildLast4)) {
					mp3Yoisyo1.stop();
					mp3Yoisyo1.pause();
					mp3Yoisyo1.play();
				}
				resultCompare(iResult);
			}
		}

	}

	private void resultCompare(int iResult) {
		mp3Siso.stop();
		mp3Siso.pause();
		mp3Siso.play();
		mp3Siso_noseru.stop();
		mp3Siso_noseru.pause();
		mp3Siso_noseru.play();
		Log.d(TAG, "iResult: " + iResult);
		if (iResult == 5) {
			resultCompare(0);
			isTouchSakura = false;
			mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {

				@Override
				public void onTimePassed(TimerHandler arg0) {
					// mScene.registerTouchArea(mSpriteAttach);
					sSita.setZIndex(mScene.getLastChild().getZIndex() + 3);
					mScene.sortChildren();
					setGimmicBringToFront();
					sSita.setVisible(true);
					sSita.registerEntityModifier(new MoveYModifier(0.2f, 600, 500));

					mp3Yata.play();
					Log.d(TAG, "Co chay am nay");

					final Enumeration<AnimatedSprite> asChilds;
					asChilds = htListChild.keys();
					while (asChilds.hasMoreElements()) {
						asChilds.nextElement().setCurrentTileIndex(0);
					}

					mScene.registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler arg0) {
							mp3Onajiomosa.play();
							mp3Hakusyu.play();

							_moveSakuraWin();
							mScene.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {

								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									// TODO
									// Auto-generated
									// method stub

								}

								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									// TODO
									// Auto-generated
									// method stub
									_moveSakuraWin();
								}
							}));

							mScene.registerEntityModifier(new DelayModifier(5.0f, new IEntityModifierListener() {
								@Override
								public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								}

								@Override
								public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									restartGame();
								}
							}));
						}
					}));

				}
			}));

			return;
		}

		if (iResult <= 4 || iResult >= -4 && iResult != 0) {
			Log.d(TAG, "iResult 4 - 4");

			if (iResult == 1) {
				roateSeeSaw(getFloatX(3), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else if (iResult == 2) {
				roateSeeSaw(getFloatX(5), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else if (iResult == 3) {
				roateSeeSaw(getFloatX(7), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else if (iResult == 4) {
				roateSeeSaw(new float[] { 0, 0, 0, 0, 0, 0, 0, 2.5f * iResult }, new float[] { 0.0f, 0.0f, 0.0f, 1.0f });
			} else if (iResult == -4) {
				Log.d(TAG, "----------------------------4");
				sSeeSaw62.clearEntityModifiers();
				float isRotate = sSeeSaw62.getRotation();
				sSeeSaw62.registerEntityModifier(new RotationModifier(1.0f, isRotate, -10, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						childSleep();
					}
				}));
			} else if (iResult == -1) {
				roateSeeSaw(getFloatXF(-3), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else if (iResult == -2) {
				roateSeeSaw(getFloatXF(-5), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else if (iResult == -3) {
				roateSeeSaw(getFloatXF(-7), new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			}

		}

		if (iResult == 0) {
			if (sSeeSaw62.getRotation() > 0) {
				roateSeeSaw(new float[] { sSeeSaw62.getRotation(), -3, -3, 3, 3, -2, -2, 0 }, new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			} else {
				roateSeeSaw(new float[] { sSeeSaw62.getRotation(), 3, 3, -3, -3, 2, 2, 0 }, new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
			}
		}
	}

	// private void resultCompare1(int iResult) {
	// if (iResult == 5) {
	// resultCompare1(0);
	// isTouchSakura = false;
	// mScene.registerUpdateHandler(new TimerHandler(0.1f,
	// new ITimerCallback() {
	//
	// @Override
	// public void onTimePassed(TimerHandler arg0) {
	// // sSita.setZIndex(mScene.getLastChild().getZIndex()
	// // + 3);
	// mScene.sortChildren();
	// setGimmicBringToFront();
	// // sSita.setVisible(true);
	// // sSita.registerEntityModifier(new MoveYModifier(
	// // 0.2f, 600, 500));
	//
	// // mp3Yata.play();
	// Log.d(TAG, "Co chay am nay");
	//
	// final Enumeration<AnimatedSprite> asChilds;
	// asChilds = htListChild.keys();
	// while (asChilds.hasMoreElements()) {
	// asChilds.nextElement().setCurrentTileIndex(0);
	// }
	//
	// mScene.registerUpdateHandler(new TimerHandler(0.1f,
	// new ITimerCallback() {
	//
	// @Override
	// public void onTimePassed(
	// TimerHandler arg0) {
	//
	// _moveSakuraWin();
	// mScene.registerEntityModifier(new DelayModifier(
	// 0.1f,
	// new IEntityModifierListener() {
	//
	// @Override
	// public void onModifierStarted(
	// IModifier<IEntity> arg0,
	// IEntity arg1) {
	// // TODO
	// // Auto-generated
	// // method stub
	//
	// }
	//
	// @Override
	// public void onModifierFinished(
	// IModifier<IEntity> arg0,
	// IEntity arg1) {
	// // TODO
	// // Auto-generated
	// // method stub
	// _moveSakuraWin();
	// }
	// }));
	//
	// mScene.registerEntityModifier(new DelayModifier(
	// 0.1f,
	// new IEntityModifierListener() {
	// @Override
	// public void onModifierStarted(
	// IModifier<IEntity> arg0,
	// IEntity arg1) {
	// }
	//
	// @Override
	// public void onModifierFinished(
	// IModifier<IEntity> arg0,
	// IEntity arg1) {
	// restartGame();
	// }
	// }));
	// }
	// }));
	//
	// }
	// }));
	//
	// return;
	// }
	//
	// if (iResult <= 4 || iResult >= -4 && iResult != 0) {
	// Log.d(TAG, "iResult 4 - 4");
	//
	// if (iResult == 1) {
	// roateSeeSaw(getFloatX(3),
	// new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
	// } else if (iResult == 2) {
	// roateSeeSaw(getFloatX(5),
	// new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
	// } else if (iResult == 3) {
	// roateSeeSaw(getFloatX(7),
	// new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
	// } else if (iResult == 4) {
	// roateSeeSaw(
	// new float[] { 0, 0, 0, 0, 0, 0, 0, 2.5f * iResult },
	// new float[] { 0.0f, 0.0f, 0.0f, 1.0f });
	// } else if (iResult == -4) {
	// Log.d(TAG, "----------------------------4");
	// sSeeSaw62.clearEntityModifiers();
	// float isRotate = sSeeSaw62.getRotation();
	// sSeeSaw62.registerEntityModifier(new RotationModifier(1.0f,
	// isRotate, -10, new IEntityModifierListener() {
	//
	// @Override
	// public void onModifierStarted(
	// IModifier<IEntity> arg0, IEntity arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onModifierFinished(
	// IModifier<IEntity> arg0, IEntity arg1) {
	// childSleep();
	// }
	// }));
	// } else if (iResult == -1) {
	// roateSeeSaw(getFloatXF(-3), new float[] { 0.6f, 0.6f, 0.6f,
	// 0.6f });
	// } else if (iResult == -2) {
	// roateSeeSaw(getFloatXF(-5), new float[] { 0.6f, 0.6f, 0.6f,
	// 0.6f });
	// } else if (iResult == -3) {
	// roateSeeSaw(getFloatXF(-7), new float[] { 0.6f, 0.6f, 0.6f,
	// 0.6f });
	// }
	//
	// }
	//
	// if (iResult == 0) {
	// if (sSeeSaw62.getRotation() > 0) {
	// roateSeeSaw(new float[] { sSeeSaw62.getRotation(), -3, -3, 3,
	// 3, -2, -2, 0 }, new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
	// } else {
	// roateSeeSaw(new float[] { sSeeSaw62.getRotation(), 3, 3, -3,
	// -3, 2, 2, 0 }, new float[] { 0.6f, 0.6f, 0.6f, 0.6f });
	// }
	// }
	//
	// }

	private float[] getFloatX(float pLeg) {
		final float[] f = new float[8];
		float isRotate = sSeeSaw62.getRotation();
		f[0] = isRotate;
		f[1] = pLeg - 3;

		f[2] = pLeg - 3;
		f[3] = pLeg + 3;

		f[4] = pLeg + 3;
		f[5] = pLeg - 2;

		f[6] = pLeg - 2;
		f[7] = pLeg;

		return f;
	}

	private float[] getFloatXF(float pLeg) {
		final float[] f = new float[8];
		float isRotate = sSeeSaw62.getRotation();
		f[0] = isRotate;
		f[1] = pLeg + 3;

		f[2] = pLeg + 3;
		f[3] = pLeg - 3;

		f[4] = pLeg - 3;
		f[5] = pLeg + 2;

		f[6] = pLeg + 2;
		f[7] = pLeg;

		return f;
	}

	private void roateSeeSaw(final float pPoint[], float ptime[]) {
		Log.d(TAG, "XXXXXXXXXXXXX");
		sSeeSaw62.clearEntityModifiers();
		float isRotate = sSeeSaw62.getRotation();
		sSeeSaw62.setRotationCenter(sSeeSaw62.getWidth() / 2, sSeeSaw62.getHeight() / 2);
		sSeeSaw62.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				childSleep();
			}
		}, new RotationModifier(ptime[0], isRotate, pPoint[1]), new RotationModifier(ptime[1], pPoint[2], pPoint[3]), new RotationModifier(ptime[2], pPoint[4], pPoint[5]), new RotationModifier(ptime[3], pPoint[6], pPoint[7])));
	}

	private void _moveSakuraWin() {
		mp3Sakura.play();
		mScene.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				int zIndex = 400;
				for (int i = 0; i < sSakura51.length; i++) {
					sSakura51[i].setPosition(positions[i][0], positions[i][1]);
					sSakura51[i].setVisible(true);
					sSakura51[i].setZIndex(zIndex++);
				}
				mScene.sortChildren();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				for (int i = 0; i < sSakura51.length; i++) {
					sSakura51[i].setPosition(moveArray[i][0], moveArray[i][1]);
				}
				mScene.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						for (int i = 0; i < sSakura51.length; i++) {
							sSakura51[i].setPosition(sSakura51[i].getmXFirst(), sSakura51[i].getmYFirst());
							sSakura51[i].setVisible(false);
						}
					}
				}));
			}
		}));
	}

	private void touchSakura() {

		if (isTouchSakura) {
			isTouchSakura = false;
			mScene.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					_moveSakuraWin();
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					isTouchSakura = true;
				}
			}));
		}
	}

	private void touchHaikei() {
		if (isTouchHaikei) {
			isTouchHaikei = false;
			mp3Gogogo.play();
			sBackground3.registerEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.2f, 45, 55), new MoveXModifier(0.2f, 55, 35), new MoveXModifier(0.2f, 35, 55, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					mp3Gogogo.play();
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				}
			}), new MoveXModifier(0.2f, 55, 35), new MoveXModifier(0.2f, 35, 45, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					isTouchHaikei = true;
				}
			})));
		}
	}

	private void touchChilds() {
		if (isTouchChilds) {
			isTouchChilds = false;
			sChildrenBefore.setPosition(-999, -999);
			sChildrenBefore.setVisible(false);
			sChildRun2.setPosition(258, 171);
			sChildRun2.setVisible(true);
			sChildRun2.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(1.0f, 1.0f, 0.5f), new MoveXModifier(1.0f, 258, 200, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					Path pPath = new Path(3).to(160, 170).to(331, 131).to(102, 63);
					sChildRun2.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(3.0f, 0.5f, 0.2f), new PathModifier(3.0f, pPath, new IPathModifierListener() {

						@Override
						public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
							mp3Kodomo.play();
							switch (arg2) {
							case 0:
								Log.d("Day no chay vao diem thu 0", "D:" + arg2);
								sChildRun2.registerEntityModifier(new RotationModifier(0.1f, 0, -15));
								break;
							case 1:
								Log.d("Day no chay vao diem thu 1", "D:" + arg2);
								sChildRun2.registerEntityModifier(new RotationModifier(0.1f, 0, 15));
								break;
							case 2:
								Log.d("Day no chay vao diem thu 2", "D:" + arg2);
								break;
							default:
								break;
							}
						}

						@Override
						public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
						}

						@Override
						public void onPathStarted(PathModifier arg0, IEntity arg1) {
						}

						@Override
						public void onPathFinished(PathModifier arg0, IEntity arg1) {
							sChildRun2.setRotation(0);
							sChildRun2.setScale(1);
							sChildRun2.setPosition(sChildRun2.getmXFirst(), sChildRun2.getmYFirst());
							sChildRun2.setVisible(false);
							sChildAfter11.setVisible(true);
							sChildAfter12.setVisible(true);
							actionChilds(sChildAfter12, sChildAfter11);
							isTouchChildAfter = true;
							mp3Wai.play();
						}
					}))));
				}
			}))));
		}
	}

	private void touchChildAfter() {
		if (isTouchChildAfter) {
			Path pPath = new Path(3).to(sChildAfter31.getmXFirst(), sChildAfter31.getmYFirst()).to(500, 187).to(592, 260);
			isTouchChildAfter = false;
			sChildAfter11.setVisible(false);
			sChildAfter12.setVisible(false);
			sChildAfter12.clearEntityModifiers();

			sChildAfter22.setVisible(true);
			sChildAfter21.setVisible(true);
			actionChilds(sChildAfter22, sChildAfter21);

			sChildAfter31.setVisible(true);
			sChildAfter31.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new RotationAtModifier(3.0f, 0, 720, sChildAfter31.getWidth() / 2, sChildAfter31.getHeight() / 2), new ScaleModifier(3.0f, 1.0f, 1.5f),
					new PathModifier(3.0f, pPath, new IPathModifierListener() {

						@Override
						public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
							switch (arg2) {
							case 0:
								Log.d("Day no chay vao diem thu 0", "D:" + arg2);
								mp3Pakun.play();
								mp3Musya.play();
								break;
							case 1:
								Log.d("Day no chay vao diem thu 1", "D:" + arg2);
								mp3Onigirikorogaru.play();
								break;
							case 2:
								Log.d("Day no chay vao diem thu 2", "D:" + arg2);
								break;
							default:
								break;
							}
						}

						@Override
						public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
						}

						@Override
						public void onPathStarted(PathModifier arg0, IEntity arg1) {
						}

						@Override
						public void onPathFinished(PathModifier arg0, IEntity arg1) {
							sChildAfter21.setVisible(false);
							sChildAfter22.setVisible(false);
							sChildAfter31.setVisible(false);
							sChildrenBefore.setPosition(sChildrenBefore.getmXFirst(), sChildrenBefore.getmYFirst());
							sChildrenBefore.setVisible(true);
							sChildAfter22.clearEntityModifiers();
							isTouchChilds = true;
						}
					}))));
		}

	}

	public void actionChilds(Sprite sprite1, Sprite sprite2) {
		sprite1.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.5f, sprite1.getmXFirst(), sprite1.getmXFirst() + 2), new MoveXModifier(0.5f, sprite1.getX() + 2, sprite1.getmXFirst() - 2),
				new MoveXModifier(0.5f, sprite1.getmXFirst() - 2, sprite1.getmXFirst()))));

		sprite2.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.5f, sprite2.getmXFirst(), sprite2.getmXFirst() - 2), new MoveXModifier(0.5f, sprite2.getX() - 2, sprite2.getmXFirst() + 2),
				new MoveXModifier(0.5f, sprite2.getmXFirst() + 2, sprite2.getmXFirst()))));
	}

	private void childSleep() {
		mScene.unregisterUpdateHandler(timerChild);
		timerChild = new TimerHandler(10.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				mp3Sisonosetene.play();
			}
		});
		mScene.registerUpdateHandler(timerChild);
	}

	private void restartGame() {
		iAnimal = 0;
		// this.mScene.clearEntityModifiers();
		if (this.sAnimals != null) {
			if (sAnimals.equals(sPengin)) {
				sPenginLast.setVisible(false);
			} else if (sAnimals.equals(sAkashia)) {
				sAkashiaLast.setVisible(false);
			} else if (sAnimals.equals(sArikui)) {
				sArikuiLast.setVisible(false);
			} else if (sAnimals.equals(sMandoriru)) {
				sMandoriruLast.setVisible(false);
			} else if (sAnimals.equals(sKapibara)) {
				sKapibaraLast.setVisible(false);
			} else if (sAnimals.equals(sAmuruhyou)) {
				sAmuruhyouLast.setVisible(false);
			} else if (sAnimals.equals(sOtosay)) {
				sOtosayLast.setVisible(false);
			} else if (sAnimals.equals(sIruka)) {
				sIrukaLast.setVisible(false);
			} else if (sAnimals.equals(sKuma)) {
				sKumaLast.setVisible(false);
			}
			sAnimals = null;
		}
		isTouchSakura = true;
		randomVisibleAnimals();
		sSita.setVisible(false);

		// sSeeSaw62.clearEntityModifiers();

		sChildren1.setVisible(true);
		sChildren2.setVisible(true);
		sChildren3.setVisible(true);
		sChildren4.setVisible(true);

		final Enumeration<AnimatedSprite> asChilds;
		asChilds = htListChild.keys();
		while (asChilds.hasMoreElements()) {
			asChilds.nextElement().setVisible(false);
		}
		htListChild.clear();

		sSeeSaw62.clearEntityModifiers();
		sSeeSaw62.setRotation(0);
		// setZIndexs(asChildren4, asChildren3, asChildren2, asChildren1);
		setZIndexs(asChildren4, asChildren3, asChildren2, asChildren1, sSeeSaw62, sSeeSaw61);
		childSleep();
		mScene.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				isTouch = true;
			}
		}));

		isTouchPengin = false;
		isTouchAkashia = false;
		isTouchArikui = false;
		isTouchMandoriru = false;
		isTouchKapibara = false;
		isTouchAmuruhyou = false;
		isTouchOtosay = false;
		isTouchIkura = false;
		isTouchKuma = false;

		isTouchChildren1 = false;
		isTouchChildren2 = false;
		isTouchChildren3 = false;
		isTouchChildren4 = false;
	}

	@Override
	public void combineGimmic3WithAction() {
		touchSakura();
	}

	// Create object GenericPool
	class IchinenseiSprite extends GenericPool<Sprite> {
		private ITextureRegion mTextureRegion;

		// Constructor
		public IchinenseiSprite(ITextureRegion pITextureRegion) {
			if (pITextureRegion == null) {
				// Need to be able to create a Sprite so the Pool needs to have
				// a TextureRegion
				throw new IllegalArgumentException("The texture region must not be NULL");
			}
			this.mTextureRegion = pITextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			final Sprite mSprites = new Sprite(480, -67, this.mTextureRegion, Vol3Ichinensei.this.getVertexBufferObjectManager());
			return mSprites;
		}

		@Override
		protected void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.resetLocalToFirst();
			pItem.setIgnoreUpdate(true);
		}

		@Override
		protected void onHandleObtainItem(final Sprite pItem) {
			pItem.reset();
		}
	}

	// --------------------------------------------------------------
	// 20130326
	// -------------------------------------------------------------
	private boolean isPause = false;
	private Handler handler = new Handler();

	@Override
	public void onPause() {
		isPause = true;
		super.onPause();
	}
}
