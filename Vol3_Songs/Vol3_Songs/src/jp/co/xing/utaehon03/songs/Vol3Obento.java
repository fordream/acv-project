package jp.co.xing.utaehon03.songs;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ObentoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Obento extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {
	
	private static final String TAG = " Vol3Obento";
	private TexturePack obentoPack;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary libObentoPack;

	private BitmapTextureAtlas ttClapFood1, ttClapFood2;
	private ITextureRegion ttrBackground, ttrBackground2, ttrBoyhand,
			ttrSaltup, ttrSaltbody, ttrPeperup, ttrPeperBody,
			ttrMayonnaisebody, ttrMayonnaiseup, ttrKetchupup, ttrKetchupbody,
			ttrLuchboxmini, ttrLuchbox_buck, ttrLuchbox_partition1,
			ttrLuchbox_partition2, ttrLuchbox_front, ttrGomashio, ttrButterbur,
			ttrGinger, ttrLotusroot, ttrCarrot, ttrShiitake, ttrCherry,
			ttrRiceball, ttrBurdock, ttrGomashio_box, ttrButterbur_box,
			ttrGinger_box, ttrLotusroot_box, ttrCarrot_box, ttrShiitake_box,
			ttrCherry_box, ttrRiceball1_box, ttrRiceball2_box, ttrBurdock_box,
			ttrClapFood1, ttrClapFood2;
	private TiledTextureRegion ttrBoy;
	private Sprite sBackgound2, sBoyhand, sSaltup, sSaltbody, sPeperup,
			sPeperbody, sMayonnaisebody, sMayonnaiseup, sClapFood1, sClapFood2,
			sKetchupup, sKetchupbody, sLuchboxmini, sLuchbox_buck,
			sLuchbox_front, sLuchbox_partition1, sLuchbox_partition2,
			sGomashio, sButterbur, sGinger, sLotusroot, sCarrot, sShiitake,
			sCherry, sRiceball, sBurdock, sGomashio_box, sButterbur_box,
			sGinger_box, sLotusroot_box, sCarrot_box, sShiitake_box,
			sCherry_box, sRiceball1_box, sRiceball2_box, sBurdock_box;
	private AnimatedSprite sBoy;

	private boolean isTouchGimmic, isTouchBoy, isTouchSalt, isTouchPeper,
			isTouchMayonnaise, isTouchKetchup, isTouchLuchbox, isTouchGomashio,
			isTouchButterbur, isTouchGinger, isTouchLotusroot, isTouchCarrot,
			isTouchShiitake, isTouchCherry, isTouchRiceball, isTouchBurdock,
			isTouchGomashiobox, isTouchButterburbox, isTouchGingerbox,
			isTouchLotusrootbox, isTouchCarrotbox, isTouchShiitakebox,
			isTouchCherrybox, isTouchRiceball1box, isTouchRiceball2box,
			isTouchBurdockbox;

	private SequenceEntityModifier sqClapFood, sqClapFood2, sqSaltModifier,
			sqPeperModifier, sqMayonnaise, sqKetchup, sqLuchboxmini,
			sqLuchbox_partition1, sqLuchbox_partition2, sqLuchbox_front,
			sqLuchbox_buck, sqGomashio, sqButterbur, sqGinger, sqLotusroot,
			sqCarrot, sqShiitake, sqCherry, sqRiceball, sqBurdock,
			sqGomashiobox, sqButterburbox, sqGingerbox, sqLotusrootbox,
			sqCarrotbox, sqShiitakebox, sqCherrybox, sqRiceball2box,
			sqRiceball1box, sqBurdockbox, sqGomashiocome, sqButterburcome,
			sqGingercome, sqLotusrootcome, sqCarrotcome, sqShiitakecome,
			sqCherrycome, sqRiceball2come, sqRiceball1come, sqBurdockcome,
			sqGomashioaway, sqButterburaway, sqGingeraway, sqLotusrootaway,
			sqCarrotaway, sqShiitakeaway, sqCherryaway, sqRiceball2away,
			sqRiceball1away, sqBurdockaway;
	private boolean istouchBox1 = true, istouchBox2 = true, istouchBox3 = true,
			istouchBox4 = true, istouchBoxMini=true;
	private Sound mp3Salt_Pepper, mp3MayKet, mp3Luchbox1,
			mp3Luchbox3, mp310_1, mp310_2, mp310_3, mp311, mp3Boy;

	private boolean inBox1, inBox2, inBox3, inBox4, inBox5, inBox6, inBox7,
			inBox8, inBox9;

	@Override
	public void onCreateResources() {
		Log.e(TAG, " onCreateResources:");
		SoundFactory.setAssetBasePath("obento/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("obento/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "obento/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		obentoPack = mTexturePackLoaderFromSource.load("obento.xml");
		obentoPack.loadTexture();
		libObentoPack = obentoPack.getTexturePackTextureRegionLibrary();
		ttrBackground = libObentoPack
				.get(Vol3ObentoResource.A3_12_3_IPHONE4_HAIKEI_ID);
		ttrBackground2 = libObentoPack
				.get(Vol3ObentoResource.A3_12_1_IPHONE4_TABLE_ID);
		ttrBoy = getTiledTextureFromPacker(obentoPack,
				Vol3ObentoResource.A3_03_1_IPHONE4_BOY_ID,
				Vol3ObentoResource.A3_03_2_IPHONE4_BOY_ID);
		ttrBoyhand = libObentoPack
				.get(Vol3ObentoResource.A3_12_2_IPHONE4_BOY_HAND_ID);

		ttrSaltup = libObentoPack
				.get(Vol3ObentoResource.A3_05_2_IPHONE4_SALT_ID);
		ttrSaltbody = libObentoPack
				.get(Vol3ObentoResource.A3_05_1_IPHONE4_SALT_ID);

		ttrPeperBody = libObentoPack
				.get(Vol3ObentoResource.A3_06_1_IPHONE4_PEPPER_ID);

		ttrPeperup = libObentoPack
				.get(Vol3ObentoResource.A3_06_2_IPHONE4_PEPPER_ID);
		ttrMayonnaisebody = libObentoPack
				.get(Vol3ObentoResource.A3_07_1_IPHONE4_MAYONNAISE_ID);
		ttrMayonnaiseup = libObentoPack
				.get(Vol3ObentoResource.A3_07_2_IPHONE4_MAYONNAISE_ID);

		ttrKetchupup = libObentoPack
				.get(Vol3ObentoResource.A3_08_2_IPHONE4_KETCHUP_ID);

		ttrKetchupbody = libObentoPack
				.get(Vol3ObentoResource.A3_08_1_IPHONE4_KETCHUP_ID);

		ttrLuchboxmini = libObentoPack
				.get(Vol3ObentoResource.A3_09_5_IPHONE4_LUNCHBOX_MINI_ID);

		ttrLuchbox_buck = libObentoPack
				.get(Vol3ObentoResource.A3_09_1_IPHONE4_LUNCHBOX_BUCK_ID);

		ttrLuchbox_partition1 = libObentoPack
				.get(Vol3ObentoResource.A3_09_2_IPHONE4_LUNCHBOX_PARTITION1_ID);
		ttrLuchbox_partition2 = libObentoPack
				.get(Vol3ObentoResource.A3_09_3_IPHONE4_LUNCHBOX_PARTITION2_ID);
		ttrLuchbox_front = libObentoPack
				.get(Vol3ObentoResource.A3_09_4_IPHONE4_LUNCHBOX_FRONT_ID);

		// ====================================================================================
		ttClapFood1 = new BitmapTextureAtlas(this.getTextureManager(), 2, 2,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrClapFood1 = new TextureRegion(ttClapFood1, 0, 0, 0, 0);
		this.mEngine.getTextureManager().loadTexture(ttClapFood1);

		ttClapFood2 = new BitmapTextureAtlas(this.getTextureManager(), 2, 2,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrClapFood2 = new TextureRegion(ttClapFood2, 0, 0, 0, 0);
		this.mEngine.getTextureManager().loadTexture(ttClapFood2);
		// ====================================================================================

		ttrGomashio = libObentoPack
				.get(Vol3ObentoResource.A3_10_1_IPHONE4_GOMASHIO_ID);
		ttrButterbur = libObentoPack
				.get(Vol3ObentoResource.A3_10_2_IPHONE4_BUTTERBUR_ID);
		ttrGinger = libObentoPack
				.get(Vol3ObentoResource.A3_10_3_IPHONE4_GINGER_ID);
		ttrLotusroot = libObentoPack
				.get(Vol3ObentoResource.A3_10_4_IPHONE4_LOTUSROOT_ID);
		ttrCarrot = libObentoPack
				.get(Vol3ObentoResource.A3_10_5_IPHONE4_CARROT_ID);
		ttrShiitake = libObentoPack
				.get(Vol3ObentoResource.A3_10_6_IPHONE4_SHIITAKE_ID);
		ttrCherry = libObentoPack
				.get(Vol3ObentoResource.A3_10_7_IPHONE4_CHERRY_ID);
		ttrRiceball = libObentoPack
				.get(Vol3ObentoResource.A3_10_8_IPHONE4_RICEBALL_ID);

		ttrBurdock = libObentoPack
				.get(Vol3ObentoResource.A3_10_9_IPHONE4_BURDOCK_ID);
		// ===================================================================================================

		ttrGomashio_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_1_IPHONE4_GOMASHIO_ID);
		ttrButterbur_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_2_IPHONE4_BUTTERBUR_ID);
		ttrGinger_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_3_IPHONE4_GINGER_ID);

		ttrLotusroot_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_4_IPHONE4_LOTUSROOT_ID);
		ttrCarrot_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_5_IPHONE4_CARROT_ID);
		ttrShiitake_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_6_IPHONE4_SHIITAKE_ID);
		ttrCherry_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_7_IPHONE4_CHERRY_ID);
		ttrRiceball1_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_8_1_IPHONE4_RICEBALL_ID);
		ttrRiceball2_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_8_2_IPHONE4_RICEBALL_ID);
		ttrBurdock_box = libObentoPack
				.get(Vol3ObentoResource.A3_11_9_IPHONE4_BURDOCK_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();

		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();

		sBackgound2 = new Sprite(0, 0, ttrBackground2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackgound2);

		sBoy = new AnimatedSprite(185, 0, ttrBoy,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBoy);

		sBoyhand = new Sprite(160.868f, 134.418f, ttrBoyhand,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBoyhand);

		sSaltbody = new Sprite(288.167f, 194.087f, ttrSaltbody,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSaltbody);

		sSaltup = new Sprite(288.167f, 173.333f, ttrSaltup,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSaltup);

		sPeperbody = new Sprite(383.333f, 196.667f, ttrPeperBody,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sPeperbody);

		sPeperup = new Sprite(383.333f, 173.333f, ttrPeperup,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sPeperup);

		sMayonnaiseup = new Sprite(479.167f, 53.333f, ttrMayonnaiseup,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMayonnaiseup);
		sMayonnaiseup.setVisible(false);

		sMayonnaisebody = new Sprite(479.167f, 111.667f, ttrMayonnaisebody,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMayonnaisebody);

		sKetchupup = new Sprite(593.334f, 53.333f, ttrKetchupup,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKetchupup);
		sKetchupup.setVisible(false);

		sKetchupbody = new Sprite(593.334f, 111.667f, ttrKetchupbody,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKetchupbody);

		// ====================================================================================//
		sRiceball = new Sprite(39.667f, 121.667f, ttrRiceball,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRiceball);

		sGomashio = new Sprite(60f, 265.833f, ttrGomashio,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sGomashio);

		sCherry = new Sprite(161.667f, 146.667f, ttrCherry,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sCherry);

		sLotusroot = new Sprite(161.667f, 272.500f, ttrLotusroot,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLotusroot);

		// ////////////////
		sBurdock = new Sprite(811.667f, 0, ttrBurdock,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBurdock);

		sCarrot = new Sprite(733.334f, 80.833f, ttrCarrot,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sCarrot);

		sShiitake = new Sprite(685f, 210f, ttrShiitake,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sShiitake);

		sGinger = new Sprite(733.334f, 292.500f, ttrGinger,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sGinger);

		sButterbur = new Sprite(774.167f, 271.667f, ttrButterbur,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sButterbur);
		// ====================================================================================
		sLuchbox_buck = new Sprite(242.500f, 292.500f, ttrLuchbox_buck,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLuchbox_buck);

		sLuchbox_partition1 = new Sprite(440.833f, 328.333f,
				ttrLuchbox_partition1, this.getVertexBufferObjectManager());
		mScene.attachChild(sLuchbox_partition1);
		// ==============================================================================
		sGinger_box = new Sprite(-150f, 0f, ttrGinger_box,
				this.getVertexBufferObjectManager());
		sGomashio_box = new Sprite(-100f, -200f, ttrGomashio_box,
				this.getVertexBufferObjectManager());
		sCherry_box = new Sprite(-100f, -300f, ttrCherry_box,
				this.getVertexBufferObjectManager());
		sButterbur_box = new Sprite(-100f, -200f, ttrButterbur_box,
				this.getVertexBufferObjectManager());
		// ==================================================================================

		sLotusroot_box = new Sprite(-150f, 0f, ttrLotusroot_box,
				this.getVertexBufferObjectManager());
		sRiceball2_box = new Sprite(1000f, 0f, ttrRiceball2_box,
				this.getVertexBufferObjectManager());
		sRiceball1_box = new Sprite(1000f, 0f, ttrRiceball1_box,
				this.getVertexBufferObjectManager());
		sShiitake_box = new Sprite(1000f, 250f, ttrShiitake_box,
				this.getVertexBufferObjectManager());
		sCarrot_box = new Sprite(1000f, 200f, ttrCarrot_box,
				this.getVertexBufferObjectManager());
		sBurdock_box = new Sprite(-150f, 0f, ttrBurdock_box,
				this.getVertexBufferObjectManager());
		// ==================================================================

		sClapFood2 = new Sprite(0, 0, 960, 640, ttrClapFood2,
				this.getVertexBufferObjectManager());
		sClapFood2.setAlpha(0.0f);
		sClapFood2.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		sClapFood2.attachChild(sGinger_box);
		sClapFood2.attachChild(sGomashio_box);
		sClapFood2.attachChild(sCherry_box);
		sClapFood2.attachChild(sButterbur_box);
		mScene.attachChild(sClapFood2);

		sLuchbox_partition2 = new Sprite(450f, 402.500f, ttrLuchbox_partition2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLuchbox_partition2);

		sClapFood1 = new Sprite(0, 0, 960, 640, ttrClapFood1,
				this.getVertexBufferObjectManager());
		sClapFood1.setAlpha(0);
		sClapFood1.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		sClapFood1.attachChild(sLotusroot_box);
		sClapFood1.attachChild(sRiceball2_box);
		sClapFood1.attachChild(sRiceball1_box);
		sClapFood1.attachChild(sShiitake_box);
		sClapFood1.attachChild(sCarrot_box);
		sClapFood1.attachChild(sBurdock_box);

		mScene.attachChild(sClapFood1);
		sClapFood1.setVisible(true);
		// ====================================================================================
		sLuchbox_front = new Sprite(242.2f, 421.667f, ttrLuchbox_front,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLuchbox_front);

		sLuchboxmini = new Sprite(23.172f, 382.717f, ttrLuchboxmini,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLuchboxmini);
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3ObentoResource.SOUND_GIMMIC,
				Vol3ObentoResource.IMAGE_GIMMIC, libObentoPack);
	}

	@Override
	protected void loadKaraokeSound() {
		mp3Boy = loadSoundResourceFromSD(Vol3ObentoResource.E00186_DEKITA);
		mp3Salt_Pepper = loadSoundResourceFromSD(Vol3ObentoResource.E00129_SUPON);
		mp3MayKet = loadSoundResourceFromSD(Vol3ObentoResource.E00187_PUJU);
		mp3Luchbox1 = loadSoundResourceFromSD(Vol3ObentoResource.E00193_SYU2);
		mp3Luchbox3 = loadSoundResourceFromSD(Vol3ObentoResource.E00194_AA2);
		mp310_1 = loadSoundResourceFromSD(Vol3ObentoResource.E00130_BYON);
		mp310_2 = loadSoundResourceFromSD(Vol3ObentoResource.E00131_SYUTA);
		mp310_3 = loadSoundResourceFromSD(Vol3ObentoResource.E00161_SYARAN);
		mp311 = loadSoundResourceFromSD(Vol3ObentoResource.E00132_ARUMI);
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public void onResumeGame() {
		Log.e(TAG, " onResumeGame:");
		inital();
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		Log.e(TAG, " onPause:");
		if (sBoy != null) {
			sBoy.stopAnimation();
			sBoy.setCurrentTileIndex(0);
		}
		if (sSaltup != null) {
			sSaltup.setPosition(288.167f, 173.333f);
			sSaltup.clearEntityModifiers();
			sqSaltModifier = null;
		}
		if (sPeperup != null) {
			sPeperup.setPosition(383.333f, 173.333f);
			sPeperup.clearEntityModifiers();
			sqPeperModifier = null;
		}
		if (sMayonnaiseup != null) {
			sMayonnaiseup.setVisible(false);
			sMayonnaiseup.setPosition(479.167f, 53.333f);
			sMayonnaiseup.clearEntityModifiers();
			sqMayonnaise = null;
		}
		if (sKetchupup != null) {
			sKetchupup.setVisible(false);
			sKetchupup.setPosition(593.334f, 53.333f);
			sKetchupup.clearEntityModifiers();
			sqKetchup = null;
		}
		// ============================================================
		if (sLuchboxmini != null) {
			sLuchboxmini.setPosition(23.172f, 382.717f);
			sLuchboxmini.clearEntityModifiers();
			sqLuchboxmini = null;
		}
		if (sLuchbox_partition1 != null) {
			sLuchbox_partition1.setPosition(440.833f, 328.333f);
			sLuchbox_partition1.clearEntityModifiers();
			sqLuchbox_partition1 = null;
		}
		if (sLuchbox_partition2 != null) {
			sLuchbox_partition2.setPosition(450f, 402.500f);
			sLuchbox_partition2.clearEntityModifiers();
			sqLuchbox_partition2 = null;
		}
		if (sLuchbox_buck != null) {
			sLuchbox_buck.setPosition(242.500f, 292.500f);
			sLuchbox_buck.clearEntityModifiers();
			sqLuchbox_buck = null;
		}
		if (sLuchbox_front != null) {
			sLuchbox_front.setPosition(242.2f, 421.667f);
			sLuchbox_front.clearEntityModifiers();
			sqLuchbox_front = null;
		}
		// ============================================================
		if (sGomashio != null) {
			sGomashio.setPosition(60f, 265.833f);
			sGomashio.clearEntityModifiers();
			sqGomashio = null;
		}
		if (sButterbur != null) {
			sButterbur.setPosition(774.167f, 271.667f);
			sButterbur.clearEntityModifiers();
			sqButterbur = null;
		}
		if (sGinger != null) {
			sGinger.setPosition(733.334f, 292.500f);
			sGinger.clearEntityModifiers();
			sqGinger = null;
		}
		if (sLotusroot != null) {
			sLotusroot.setPosition(161.667f, 272.500f);
			sLotusroot.clearEntityModifiers();
			sqLotusroot = null;
		}
		if (sCarrot != null) {
			sCarrot.setPosition(733.334f, 80.833f);
			sCarrot.clearEntityModifiers();
			sqCarrot = null;
		}
		if (sShiitake != null) {
			sShiitake.setPosition(685f, 210f);
			sShiitake.clearEntityModifiers();
			sqShiitake = null;
		}
		if (sCherry != null) {
			sCherry.setPosition(161.667f, 146.667f);
			sCherry.clearEntityModifiers();
			sqCherry = null;
		}
		if (sRiceball != null) {
			sRiceball.setPosition(39.667f, 121.667f);
			sRiceball.clearEntityModifiers();
			sqRiceball = null;
		}
		if (sBurdock != null) {
			sBurdock.setPosition(811.667f, 0);
			sBurdock.clearEntityModifiers();
			sqBurdock = null;
		}
		// ===============================================================
		if (sGomashio_box != null) {
			sGomashio_box.setPosition(-100f, -200f);
			sGomashio_box.clearEntityModifiers();
			sqGomashiobox = null;
			sqGomashiocome = null;
			sqGomashioaway = null;
		}
		if (sButterbur_box != null) {
			sButterbur_box.setPosition(-100f, -200f);
			sButterbur_box.clearEntityModifiers();
			sqButterburbox = null;
			sqButterburcome = null;
			sqButterburaway = null;
		}
		if (sGinger_box != null) {
			sGinger_box.setPosition(-150f, 0f);
			sGinger_box.clearEntityModifiers();
			sqGingeraway = null;
			sqGingerbox = null;
			sqGingercome = null;
		}
		if (sLotusroot_box != null) {
			sLotusroot_box.setPosition(-150f, 0);
			sLotusroot_box.clearEntityModifiers();
			sqLotusrootaway = null;
			sqLotusrootbox = null;
			sqLotusrootcome = null;
		}
		if (sCarrot_box != null) {
			sCarrot_box.setPosition(1000f, 200f);
			sCarrot_box.clearEntityModifiers();
			sqCarrotaway = null;
			sqCarrotbox = null;
			sqCarrotcome = null;
		}
		if (sShiitake_box != null) {
			sShiitake_box.setPosition(1000f, 250);
			sShiitake_box.clearEntityModifiers();
			sqShiitakeaway = null;
			sqShiitakebox = null;
			sqShiitakecome = null;
		}
		if (sCherry_box != null) {
			sCherry_box.setPosition(-100f, -300f);
			sCherry_box.clearEntityModifiers();
			sqCherryaway = null;
			sqCherrybox = null;
			sqCherrycome = null;
		}
		if (sRiceball1_box != null) {
			sRiceball1_box.setPosition(1000f, 0f);
			sRiceball1_box.clearEntityModifiers();
			sqRiceball1away = null;
			sqRiceball1box = null;
			sqRiceball1come = null;
		}
		if (sRiceball2_box != null) {
			sRiceball2_box.setPosition(1000f, 0f);
			sRiceball2_box.clearEntityModifiers();
			sqRiceball2away = null;
			sqRiceball2box = null;
			sqRiceball2come = null;
		}
		if (sBurdock_box != null) {
			sBurdock_box.setPosition(-150f, 0f);
			sBurdock_box.clearEntityModifiers();
			sqBurdockaway = null;
			sqBurdockbox = null;
			sqBurdockcome = null;
		}
		istouchBox1 = true;
		istouchBox2 = true;
		istouchBox3 = true;
		istouchBox4 = true;
		istouchBoxMini= true;
		super.onPauseGame();
	}

	private void boyChange() {
		int Pfame[] = { 1 };
		long durationFrame[] = { 400 };
		sBoy.animate(durationFrame, Pfame, 0, this);
	}

	private void saltModifier() {
		sSaltup.registerEntityModifier((sqSaltModifier = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 173.333f, 173.333f - 70),
				new MoveYModifier(0.4f, 173.333f - 70, 173.333f))));
		if (sqSaltModifier != null) {
			sqSaltModifier.addModifierListener(this);
		}
	}

	private void peperModifier() {
		sPeperup.registerEntityModifier((sqPeperModifier = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 173.333f, 173.333f - 70),
				new MoveYModifier(0.4f, 173.333f - 70, 173.333f))));
		if (sqPeperModifier != null) {
			sqPeperModifier.addModifierListener(this);
		}
	}

	private void mayonaiseModifier() {
		sMayonnaiseup
				.registerEntityModifier((sqMayonnaise = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 53.333f,
								53.333f - 70), new MoveYModifier(
								0.4f, 53.333f - 70, 53.333f))));
		if (sqMayonnaise != null) {
			sqMayonnaise.addModifierListener(this);
		}
	}

	private void ketchupModifier() {
		sKetchupup
				.registerEntityModifier((sqKetchup = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 53.333f, 53.333f - 70),
						new MoveYModifier(0.4f, 53.333f - 70, 53.333f))));
		if (sqKetchup != null) {
			sqKetchup.addModifierListener(this);
		}
	}

	private void luchBoxduckModifier() {
		if (istouchBox1) {
			istouchBox1 = false;
			sLuchbox_buck
					.registerEntityModifier((sqLuchbox_buck = new SequenceEntityModifier(
							new MoveYModifier(0.4f, 292.500f, 242.500f),
							new MoveYModifier(0.4f, 242.500f, 292.500f))));
			if (sqLuchbox_buck != null) {
				sqLuchbox_buck.addModifierListener(this);
			}
		}
	}

	private void luchBoxminiModifier() {
		if (istouchBoxMini) {
			istouchBoxMini = false;
			mp3Luchbox3.play();
			sLuchboxmini
					.registerEntityModifier((sqLuchboxmini = new SequenceEntityModifier(
							new MoveYModifier(0.4f, 382.717f, 382.717f - 50),
							new MoveYModifier(0.4f, 382.717f - 50, 382.717f))));
			if (sqLuchboxmini != null) {
				sqLuchboxmini.addModifierListener(this);
			}
		}
		
	}

	private void luchBoxpartition1Modifier() {
		if (istouchBox2) {
			istouchBox2 = false;
			sLuchbox_partition1
					.registerEntityModifier((sqLuchbox_partition1 = new SequenceEntityModifier(
							new MoveYModifier(0.4f, 328.333f, 328.333f - 50),
							new MoveYModifier(0.4f, 328.333f - 50, 328.333f))));
			if (sqLuchbox_partition1 != null) {
				sqLuchbox_partition1.addModifierListener(this);
			}
		}
	}

	private void luchBoxpartition2Modifier() {
		if (istouchBox3) {
			istouchBox3 = false;
			sLuchbox_partition2
					.registerEntityModifier((sqLuchbox_partition2 = new SequenceEntityModifier(
							new MoveYModifier(0.4f, 402.500f, 402.500f - 50),
							new MoveYModifier(0.4f, 402.500f - 50, 402.500f))));
			if (sqLuchbox_partition2 != null) {
				sqLuchbox_partition2.addModifierListener(this);
			}
		}
	}

	private void luchBoxfrontModifier() {
		if (istouchBox4) {
			istouchBox4 = false;
			sLuchbox_front
					.registerEntityModifier((sqLuchbox_front = new SequenceEntityModifier(
							new MoveYModifier(0.4f, 421.667f, 421.667f - 50),
							new MoveYModifier(0.4f, 421.667f - 50, 421.667f))));
			if (sqLuchbox_front != null) {
				sqLuchbox_front.addModifierListener(this);
			}
		}
	}

	// ================================================================================
	private void GomashioModifier() {
		sGomashio
				.registerEntityModifier((sqGomashio = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 265.833f, 265.833f - 70), new MoveYModifier(0.4f,
								265.833f - 70, 265.833f))));
		if (sqGomashio != null) {
			sqGomashio.addModifierListener(this);
		}
	}

	private void butterburModifier() {
		sButterbur
				.registerEntityModifier((sqButterbur = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 271.667f, 271.667f - 70),
						new MoveYModifier(0.4f, 271.667f - 70, 271.667f))));
		if (sqButterbur != null) {
			sqButterbur.addModifierListener(this);
		}
	}

	private void gingerModifier() {
		sGinger.registerEntityModifier((sqGinger = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 292.500f, 292.500f - 70),
				new MoveYModifier(0.4f, 292.500f - 70, 292.500f))));
		if (sqGinger != null) {
			sqGinger.addModifierListener(this);
		}
	}

	private void LotusrootModifier() {
		sLotusroot
				.registerEntityModifier((sqLotusroot = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 272.500f, 272.500f - 70), 
						new MoveYModifier(0.4f, 272.500f - 70, 272.500f))));
		if (sqLotusroot != null) {
			sqLotusroot.addModifierListener(this);
		}
	}

	private void carrotModifier() {
		sCarrot.registerEntityModifier((sqCarrot = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 80.833f, 80.833f - 70),
				new MoveYModifier(0.4f, 80.833f - 70, 80.833f))));
		if (sqCarrot != null) {
			sqCarrot.addModifierListener(this);
		}
	}

	private void shiitakeModifier() {
		sShiitake
				.registerEntityModifier((sqShiitake = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 210f, 210f - 70), 
						new MoveYModifier(0.4f, 210f - 70, 210f))));
		if (sqShiitake != null) {
			sqShiitake.addModifierListener(this);
		}
	}

	private void cherryModifier() {
		sCherry.registerEntityModifier((sqCherry = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 146.667f, 146.667f - 70),
				new MoveYModifier(0.4f, 146.667f - 70, 146.667f))));
		if (sqCherry != null) {
			sqCherry.addModifierListener(this);
		}
	}

	private void riceballModifier() {
		sRiceball
				.registerEntityModifier((sqRiceball = new SequenceEntityModifier(
						new MoveYModifier(0.4f, 121.667f, 121.667f - 70), 
						new MoveYModifier(0.4f, 121.667f - 70, 121.667f))));
		if (sqRiceball != null) {
			sqRiceball.addModifierListener(this);
		}
	}

	private void burdockModifier() {
		sBurdock.registerEntityModifier((sqBurdock = new SequenceEntityModifier(
				new MoveYModifier(0.4f, 0, - 70),
				new MoveYModifier(0.4f, - 70, 0))));
		if (sqBurdock != null) {
			sqBurdock.addModifierListener(this);
		}
	}

	// ================================================================================================//
	private void GomashioboxModifier() {
		sGomashio_box
				.registerEntityModifier((sqGomashiobox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sGomashio_box.getY(),
								sGomashio_box.getY() - 70), 
						new MoveYModifier(
								0.4f, sGomashio_box.getY() - 70, sGomashio_box
										.getY()))));
		if (sqGomashiobox != null) {
			sqGomashiobox.addModifierListener(this);
		}
	}

	private void butterburboxModifier() {
		sButterbur_box
				.registerEntityModifier((sqButterburbox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sButterbur_box.getY(),
								sButterbur_box.getY() - 70), new MoveYModifier(
								0.4f, sButterbur_box.getY() - 70,
								sButterbur_box.getY()))));
		if (sqButterburbox != null) {
			sqButterburbox.addModifierListener(this);
		}
	}

	private void gingerboxModifier() {
		sGinger_box
				.registerEntityModifier((sqGingerbox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sGinger_box.getY(), sGinger_box
								.getY() - 70), new MoveYModifier(0.4f,
								sGinger_box.getY() - 70, sGinger_box.getY()))));
		if (sqGingerbox != null) {
			sqGingerbox.addModifierListener(this);
		}
	}

	private void LotusrootboxModifier() {
		sLotusroot_box
				.registerEntityModifier((sqLotusrootbox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sLotusroot_box.getY(),
								sLotusroot_box.getY() - 70), new MoveYModifier(
								0.4f, sLotusroot_box.getY() - 70,
								sLotusroot_box.getY()))));
		if (sqLotusrootbox != null) {
			sqLotusrootbox.addModifierListener(this);
		}
	}

	private void carrotboxModifier() {
		sCarrot_box
				.registerEntityModifier((sqCarrotbox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sCarrot_box.getY(), sCarrot_box
								.getY() - 70), new MoveYModifier(0.4f,
								sCarrot_box.getY() - 70, sCarrot_box.getY()))));
		if (sqCarrotbox != null) {
			sqCarrotbox.addModifierListener(this);
		}
	}

	private void shiitakeboxModifier() {
		sShiitake_box
				.registerEntityModifier((sqShiitakebox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sShiitake_box.getY(),
								sShiitake_box.getY() - 70), new MoveYModifier(
								0.4f, sShiitake_box.getY() - 70, sShiitake_box
										.getY()))));
		if (sqShiitakebox != null) {
			sqShiitakebox.addModifierListener(this);
		}
	}

	private void cherryboxModifier() {
		sCherry_box
				.registerEntityModifier((sqCherrybox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sCherry_box.getY(), sCherry_box
								.getY() - 70), new MoveYModifier(0.4f,
								sCherry_box.getY() - 70, sCherry_box.getY()))));
		if (sqCherrybox != null) {
			sqCherrybox.addModifierListener(this);
		}
	}

	private void riceball1boxModifier() {
		sRiceball1_box
				.registerEntityModifier((sqRiceball1box = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sRiceball1_box.getY(),
								 sRiceball1_box.getY()- 70), new MoveYModifier(
								0.4f, sRiceball1_box.getY() - 70,  sRiceball1_box.getY()))));
		if (sqRiceball1box != null) {
			sqRiceball1box.addModifierListener(this);
		}
	}

	private void riceball2boxModifier() {
		sRiceball2_box
				.registerEntityModifier((sqRiceball2box = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sRiceball2_box.getY(),
								sRiceball2_box.getY() - 70), new MoveYModifier(
								0.4f, sRiceball2_box.getY() - 70,
								sRiceball2_box.getY()))));
		if (sqRiceball2box != null) {
			sqRiceball2box.addModifierListener(this);
		}
	}

	private void burdockboxModifier() {
		sBurdock_box
				.registerEntityModifier((sqBurdockbox = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sBurdock_box.getY(),
								sBurdock_box.getY() - 70), new MoveYModifier(
								0.4f, sBurdock_box.getY() - 70, sBurdock_box
										.getY()))));
		if (sqBurdockbox != null) {
			sqBurdockbox.addModifierListener(this);
		}
	}

	// ===============================================================================================
	// foot fly
	private void GomashiocomeModifier() {
		sGomashio_box
				.registerEntityModifier((sqGomashiocome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.6f,
								-150f, 469.167f, -200f, 330f)))));
		if (sqGomashiocome != null) {
			sqGomashiocome.addModifierListener(this);
		}
	}

	private void butterburcomeModifier() {
		sButterbur_box
				.registerEntityModifier((sqButterburcome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.5f,
								-100f, 535f, -200f, 290.5f)))));
		if (sqButterburcome != null) {
			sqButterburcome.addModifierListener(this);
		}
	}

	private void gingercomeModifier() {
		sGinger_box
				.registerEntityModifier((sqGingercome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.4f,
								-150f, 440.833f, 0f, 300.5f)))));
		if (sqGingercome != null) {
			sqGingercome.addModifierListener(this);
		}
	}

	private void LotusrootcomeModifier() {
		sLotusroot_box
				.registerEntityModifier((sqLotusrootcome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.6f,
								-150f, 610.167f, 0f, 392.5f)))));
		if (sqLotusrootcome != null) {
			sqLotusrootcome.addModifierListener(this);
		}
	}

	private void carrotcomeModifier() {
		sCarrot_box
				.registerEntityModifier((sqCarrotcome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.4f,
								1000f, 440.833f, 200f, 421.667f)))));
		if (sqCarrotcome != null) {
			sqCarrotcome.addModifierListener(this);
		}
	}

	private void shiitakecomeModifier() {
		sShiitake_box
				.registerEntityModifier((sqShiitakecome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.5f,
								1000f, 499.167f, 250f, 421.667f)

						))));
		if (sqShiitakecome != null) {
			sqShiitakecome.addModifierListener(this);
		}
	}

	private void cherrycomeModifier() {
		sCherry_box
				.registerEntityModifier((sqCherrycome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.7f,
								-150f, 635.297f, -300f, 324.079f)))));
		if (sqCherrycome != null) {
			sqCherrycome.addModifierListener(this);
		}
	}

	private void riceball1comeModifier() {
		sRiceball1_box
				.registerEntityModifier((sqRiceball1come = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.4f,
								1000f, 255.833f, 0f, 422.0f)))));
		if (sqRiceball1come != null) {
			sqRiceball1come.addModifierListener(this);
		}
	}

	private void riceball2comeModifier() {
		sRiceball2_box
				.registerEntityModifier((sqRiceball2come = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.4f,
								1000f, 257.5f, 0f, 319.167f)))));
		if (sqRiceball2come != null) {
			sqRiceball2come.addModifierListener(this);

		}
	}

	private void burdockcomeModifier() {
		sBurdock_box
				.registerEntityModifier((sqBurdockcome = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveModifier(0.6f,
								-150f, 585f, 0f, 385.5f)))));
		if (sqBurdockcome != null) {
			sqBurdockcome.addModifierListener(this);
		}
	}

	// ================================================================================================//
	// foot away
	private void GomashioawayModifier() {
		sGomashio_box
				.registerEntityModifier((sqGomashioaway = new SequenceEntityModifier(
//						new MoveYModifier(0.4f, (int) sGomashio_box.getY(),
//								(int) sGomashio_box.getY() - 100),
						new MoveModifier(0.8f, 469.167f, -150f, 230f, -200)

				)));
		if (sqGomashioaway != null) {
			sqGomashioaway.addModifierListener(this);
		}
	}

	private void butterburawayModifier() {
		sButterbur_box
				.registerEntityModifier((sqButterburaway = new SequenceEntityModifier(
//				new MoveYModifier(0.4f, (int) sButterbur_box.getY(),
//						(int) sButterbur_box.getY() - 100),
						new MoveModifier(
						0.8f, 535f, -100f, 190.5f, -200f))));
		if (sqButterburaway != null) {
			sqButterburaway.addModifierListener(this);
		}
	}

	private void gingerawayModifier() {
		sGinger_box
				.registerEntityModifier((sqGingeraway = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, (int) sGinger_box.getY(),
//						(int) sGinger_box.getY() - 100),
						new MoveModifier(0.8f,
						440.833f, -150f, 200.5f, 0f))));
		if (sqGingeraway != null) {
			sqGingeraway.addModifierListener(this);
		}
	}

	private void LotusrootawayModifier() {
		sLotusroot_box
				.registerEntityModifier((sqLotusrootaway = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, (int) sLotusroot_box.getY(),
//						(int) sLotusroot_box.getY() - 100), 
						new MoveModifier(
						0.8f, 610.167f, -150f, 292.5f, 0f))));
		if (sqLotusrootaway != null) {
			sqLotusrootaway.addModifierListener(this);
		}
	}

	private void carrotawayModifier() {
		sCarrot_box
				.registerEntityModifier((sqCarrotaway = new SequenceEntityModifier(
//						new MoveYModifier(0.4f, (int) sCarrot_box.getY(),
//								(int) sCarrot_box.getY() - 100),
						new MoveModifier(0.8f, 440.833f, 1000f, 321.667f, 200f))));
		if (sqCarrotaway != null) {
			sqCarrotaway.addModifierListener(this);
		}
	}

	private void shiitakeawayModifier() {
		sShiitake_box
				.registerEntityModifier((sqShiitakeaway = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, (int) sShiitake_box.getY(),
//						(int) sShiitake_box.getY() - 100), 
						new MoveModifier(
						0.8f, 499.167f, 1000f, 321.667f, 250f)

				)));
		if (sqShiitakeaway != null) {
			sqShiitakeaway.addModifierListener(this);
		}
	}

	private void cherryawayModifier() {
		sCherry_box
				.registerEntityModifier((sqCherryaway = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, (int) sCherry_box.getY(),
//						(int) sCherry_box.getY() - 100),
						new MoveModifier(0.8f,
						635.297f, -150f, 224.079f, -300f))));
		if (sqCherryaway != null) {
			sqCherryaway.addModifierListener(this);
		}
	}

	private void riceball1awayModifier() {
		sRiceball1_box
				.registerEntityModifier((sqRiceball1away = new SequenceEntityModifier(
//				new MoveYModifier(0.4f, sRiceball1_box.getY(),  sRiceball1_box.getY() - 100),
				new MoveModifier(
						0.8f, 255.833f, 1000f, 322.0f, 0f))));
		if (sqRiceball1away != null) {
			sqRiceball1away.addModifierListener(this);
		}
	}

	private void riceball2awayModifier() {
		sRiceball2_box
				.registerEntityModifier((sqRiceball2away = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, sRiceball2_box.getY(),sRiceball2_box.getY() - 100),
				new MoveModifier(
						0.8f, 257.5f, 1000f, 219.167f, 0f))));
		if (sqRiceball2away != null) {
			sqRiceball2away.addModifierListener(this);

		}
	}

	private void burdockawayModifier() {
		sBurdock_box
				.registerEntityModifier((sqBurdockaway = new SequenceEntityModifier(

//				new MoveYModifier(0.4f, (int) sBurdock_box.getY(),
//						(int) sBurdock_box.getY() - 100),
						new MoveModifier(
						0.8f, 585f, -150f, 285.5f, 0f))));
		if (sqBurdockaway != null) {
			sqBurdockaway.addModifierListener(this);
		}
	}

	// ================================================================================================//

	private void clapFoodModifier() {
//		setEnable(false);
		sClapFood1
				.registerEntityModifier((sqClapFood = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sClapFood1.getY(), sClapFood1
								.getY() - 50), new MoveYModifier(0.4f,
								sClapFood1.getY() - 50, sClapFood1.getY()))));
		if (sqClapFood != null) {
			sqClapFood.addModifierListener(this);
		}
	}

	private void clapFood2Modifier() {
		sClapFood2
				.registerEntityModifier((sqClapFood2 = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sClapFood2.getY(), sClapFood2
								.getY() - 50), new MoveYModifier(0.4f,
								sClapFood2.getY() - 50, sClapFood2.getY()))));
		if (sqClapFood2 != null) {
			sqClapFood2.addModifierListener(this);
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isTouchBoy && isTouchGimmic) {
			mp3Boy.play();
			isTouchBoy = false;
			isTouchGimmic = false;
			boyChange();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sBoy, (int) sBoy.getWidth() / 3,
					(int) sBoy.getHeight() / 3, (int) sBoy.getWidth(),
					(int) sBoy.getHeight(), pX, pY)) {
				if (isTouchBoy && isTouchGimmic) {
					mp3Boy.play();
					isTouchBoy = false;
					isTouchGimmic = false;
					boyChange();
				}
			} else if (checkContains(sSaltbody, 0, 0,
					(int) sSaltbody.getWidth(), (int) sSaltbody.getHeight(),
					pX, pY)) {
				if (isTouchSalt) {
					mp3Salt_Pepper.play();
					isTouchSalt = false;
					saltModifier();
				}
			} else if (checkContains(sPeperbody, 0, 0,
					(int) sPeperbody.getWidth(), (int) sPeperbody.getHeight(),
					pX, pY)) {
				if (isTouchPeper) {
					mp3Salt_Pepper.play();
					isTouchPeper = false;
					peperModifier();
				}
			} else if (checkContains(sMayonnaisebody, 0, 0,
					(int) sMayonnaisebody.getWidth(),
					(int) sMayonnaisebody.getHeight(), pX, pY)) {
				if (isTouchMayonnaise) {
					mp3MayKet.play();
					isTouchMayonnaise = false;
					sMayonnaiseup.setVisible(true);
					mayonaiseModifier();
				}
			} else if (checkContains(sKetchupbody, 0, 0,
					(int) sKetchupbody.getWidth(),
					(int) sKetchupbody.getHeight(), pX, pY)) {
				if (isTouchKetchup) {
					mp3MayKet.play();
					isTouchKetchup = false;
					sKetchupup.setVisible(true);
					ketchupModifier();
				}
			} else if (checkContains(sGomashio, 0, 0,
					(int) sGomashio.getWidth(), (int) sGomashio.getHeight(),
					pX, pY)) {
				if (isTouchGomashio && isTouchGomashiobox) {
					isTouchGomashio = false;
					isTouchGomashiobox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					GomashioModifier();
					if (!inBox1) {
						GomashioboxModifier();
					} else {
						GomashiocomeModifier();
					}

				}
			} else if (checkContains(sButterbur,46,41,82, 216, pX, pY))
//					(int) sButterbur.getWidth() / 10,
//					(int) sButterbur.getHeight() / 3,
//					(int) sButterbur.getWidth() * 3 / 4,
//					(int) sButterbur.getHeight() * 3 / 4, pX, pY)) 
			{
				if (isTouchButterbur && isTouchButterburbox) {
					isTouchButterbur = false;
					isTouchButterburbox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					butterburModifier();
					if (!inBox2) {
						butterburboxModifier();
					} else {
						butterburcomeModifier();
					}
				}
			} else if (checkContains(sGinger, 0, (int) sGinger.getHeight() / 3,
					(int) sGinger.getWidth(), (int) sGinger.getHeight(), pX, pY)) {
				if (isTouchGinger && isTouchGingerbox) {
					isTouchGinger = false;
					isTouchGingerbox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					gingerModifier();
					if (!inBox3) {
						gingerboxModifier();
					} else {
						gingercomeModifier();
					}
				}
			} else if (checkContains(sLotusroot, 0, 0,
					(int) sLotusroot.getWidth(), (int) sLotusroot.getHeight(),
					pX, pY)) {
				if (isTouchLotusroot && isTouchLotusrootbox) {
					isTouchLotusroot = false;
					isTouchLotusrootbox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					LotusrootModifier();
					if (!inBox4) {
						LotusrootboxModifier();
					} else {
						LotusrootcomeModifier();
					}
				}
			} else if (checkContains(sCarrot, 0, 0, (int) sCarrot.getWidth(),
					(int) sCarrot.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchCarrot && isTouchCarrotbox) {
					isTouchCarrot = false;
					isTouchCarrotbox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					carrotModifier();
					if (!inBox5) {
						carrotboxModifier();
					} else {
						carrotcomeModifier();
					}

				}
			} else if (checkContains(sShiitake, 0, 0,
					(int) sShiitake.getWidth(), (int) sShiitake.getHeight(),
					pX, pY)) {
				if (isTouchShiitake && isTouchShiitakebox) {
					isTouchShiitake = false;
					isTouchShiitakebox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					shiitakeModifier();
					if (!inBox6) {
						shiitakeboxModifier();
					} else {
						shiitakecomeModifier();
					}
				}
			} else if (checkContains(sCherry, 0, 0, (int) sCherry.getWidth(),
					(int) sCherry.getHeight(), pX, pY)) {
				if (isTouchCherry && isTouchCherrybox) {
					isTouchCherry = false;
					isTouchCherrybox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					cherryModifier();
					if (!inBox7) {
						cherryboxModifier();
					} else {
						cherrycomeModifier();
					}
				}
			} else if (checkContains(sRiceball, 0, 0,
					(int) sRiceball.getWidth(), (int) sRiceball.getHeight(),
					pX, pY)) {
				if (isTouchRiceball && isTouchRiceball1box
						&& isTouchRiceball2box) {
					isTouchRiceball = false;
					isTouchRiceball1box = false;
					isTouchRiceball2box = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					riceballModifier();
					if (!inBox8) {
						riceball1boxModifier();
						riceball2boxModifier();
					} else {
						riceball1comeModifier();
						riceball2comeModifier();
					}
				}
			} else if (checkContains(sBurdock, 0, 0, (int) sBurdock.getWidth(),
					(int) sBurdock.getHeight() / 2, pX, pY)) {
				if (isTouchBurdock && isTouchBurdockbox) {
					isTouchBurdock = false;
					isTouchBurdockbox = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp310_1.play();
					burdockModifier();
					if (!inBox9) {
						burdockboxModifier();
					} else {
						burdockcomeModifier();
					}
				}
				// ==============================================================================
			} else if (checkContains(sGomashio_box, 0, 0,
					(int) sGomashio_box.getWidth(),
					(int) sGomashio_box.getHeight(), pX, pY)) {
				if (isTouchGomashiobox && isTouchGomashio) {
					isTouchGomashiobox = false;
					isTouchGomashio = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					GomashioboxModifier();
				}
			} else if (checkContains(sBurdock_box, 0, 0,
					(int) sBurdock_box.getWidth(),
					(int) sBurdock_box.getHeight(), pX, pY)) {
				if (isTouchBurdockbox && isTouchBurdock) {
					isTouchBurdockbox = false;
					isTouchBurdock = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					burdockboxModifier();
				}
			} else if (checkContains(sButterbur_box, 0, 0,
					(int) sButterbur_box.getWidth(),
					(int) sButterbur_box.getHeight(), pX, pY)) {
				if (isTouchButterburbox && isTouchButterbur) {
					isTouchButterburbox = false;
					isTouchButterbur = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					butterburboxModifier();
				}
			} else if (checkContains(sGinger_box, 0, 0,
					(int) sGinger_box.getWidth(),
					(int) sGinger_box.getHeight(), pX, pY)) {
				if (isTouchGingerbox && isTouchGinger) {
					isTouchGingerbox = false;
					isTouchGinger = false;
					mp311.play();
					Log.i(TAG, "tap vao thuc an: ");
					gingerboxModifier();
				}
			} else if (checkContains(sLotusroot_box, 0, 0,
					(int) sLotusroot_box.getWidth(),
					(int) sLotusroot_box.getHeight(), pX, pY)) {
				if (isTouchLotusrootbox && isTouchLotusroot) {
					isTouchLotusrootbox = false;
					isTouchLotusroot = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					LotusrootboxModifier();
				}
			} else if (checkContains(sCarrot_box, 0, 0,
					(int) sCarrot_box.getWidth(),
					(int) sCarrot_box.getHeight(), pX, pY)) {
				if (isTouchCarrotbox && isTouchCarrot) {
					isTouchCarrotbox = false;
					isTouchCarrot = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					carrotboxModifier();
				}
			} else if (checkContains(sShiitake_box, 0, 0,
					(int) sShiitake_box.getWidth(),
					(int) sShiitake_box.getHeight(), pX, pY)) {
				if (isTouchShiitakebox && isTouchShiitake) {
					isTouchShiitakebox = false;
					isTouchShiitake = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					shiitakeboxModifier();
				}
			} else if (checkContains(sCherry_box, 0, 0,
					(int) sCherry_box.getWidth(),
					(int) sCherry_box.getHeight(), pX, pY)) {
				if (isTouchCherrybox && isTouchCherry) {
					isTouchCherrybox = false;
					isTouchCherry = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					cherryboxModifier();
				}
			} else if (checkContains(sRiceball1_box, 0, 0,
					(int) sRiceball1_box.getWidth(),
					(int) sRiceball1_box.getHeight(), pX, pY)
					|| checkContains(sRiceball2_box, 0, 0,
							(int) sRiceball2_box.getWidth(),
							(int) sRiceball2_box.getHeight(), pX, pY)) {
				if (isTouchRiceball1box && isTouchRiceball2box
						&& isTouchRiceball) {
					isTouchRiceball1box = false;
					isTouchRiceball2box = false;
					isTouchRiceball = false;
					Log.i(TAG, "tap vao thuc an: ");
					mp311.play();
					riceball1boxModifier();
					riceball2boxModifier();
				}
			} else if (checkContains(sLuchboxmini, 80, 3,
					201,120, pX, pY)) {
				if (isTouchLuchbox && isTouchGomashiobox && isTouchGomashio
						&& isTouchButterburbox && isTouchButterbur
						&& isTouchGingerbox && isTouchGinger
						&& isTouchLotusrootbox && isTouchLotusroot
						&& isTouchCarrotbox && isTouchCarrot
						&& isTouchShiitakebox && isTouchShiitake
						&& isTouchCherrybox && isTouchCherry
						&& isTouchRiceball1box && isTouchRiceball2box
						&& isTouchRiceball && isTouchBurdockbox
						&& isTouchBurdock&& isTouchLuchbox) {
					setEnable(false);
					Log.i(TAG, "Tap vao hop com be: ");
					luchBoxduckModifier();
					luchBoxminiModifier();
					luchBoxpartition1Modifier();
					luchBoxpartition2Modifier();
					luchBoxfrontModifier();

					if (inBox1) {
						GomashioawayModifier();
					}
					if (inBox2) {
						butterburawayModifier();
					}
					if (inBox3) {
						gingerawayModifier();
					}
					if (inBox4) {
						LotusrootawayModifier();
					}
					if (inBox5) {
						carrotawayModifier();
					}
					if (inBox6) {
						shiitakeawayModifier();
					}
					if (inBox7) {
						cherryawayModifier();
					}
					if (inBox8) {
						riceball1awayModifier();
						riceball2awayModifier();
					}
					if (inBox9) {
						burdockawayModifier();
					}
					if (inBox1 || inBox2 || inBox3 || inBox4 || inBox5
							|| inBox6 || inBox7 || inBox8 || inBox9) {
						mp3Luchbox1.play();
//						mEngine.registerUpdateHandler(new TimerHandler(0.4f,
//								new ITimerCallback() {
//									@Override
//									public void onTimePassed(TimerHandler arg0) {
//										mp3Luchbox3.play();
//
//									}
//								}));
						mEngine.registerUpdateHandler(new TimerHandler(1.5f,
								new ITimerCallback() {
									@Override
									public void onTimePassed(TimerHandler arg0) {
										setEnable(true);

									}
								}));
					}
//					if (!(inBox1 || inBox2 || inBox3 || inBox4 || inBox5
//							|| inBox6 || inBox7 || inBox8 || inBox9)) {
//						mp3Luchbox3.play();
//					}
				}
			} else if (checkContains(sLuchbox_buck, 96, 10,
					404,249,pX, pY) || checkContains(sLuchbox_front, 11, 110,
							407, 193, pX, pY)) {
				if (isTouchLuchbox && isTouchGomashiobox && isTouchGomashio
						&& isTouchButterburbox && isTouchButterbur
						&& isTouchGingerbox && isTouchGinger
						&& isTouchLotusrootbox && isTouchLotusroot
						&& isTouchCarrotbox && isTouchCarrot
						&& isTouchShiitakebox && isTouchShiitake
						&& isTouchCherrybox && isTouchCherry
						&& isTouchRiceball1box && isTouchRiceball2box
						&& isTouchRiceball && isTouchBurdockbox
						&& isTouchBurdock && istouchBoxMini) {
					Log.i(TAG, "Tap vao hop com to: ");
					if (!(inBox1 || inBox2 || inBox3 || inBox4 || inBox5
							|| inBox6 || inBox7 || inBox8 || inBox9)) {
//						mp3Luchbox3.play();
						setEnable(false);

						luchBoxduckModifier();
						luchBoxminiModifier();
						luchBoxpartition1Modifier();
						luchBoxpartition2Modifier();
						luchBoxfrontModifier();
					}
				}
			}

			return true;
		}
		return false;
	}

	

	private void clapFood() {
		sClapFood1.setVisible(true);
		setEnable(false);
		mp310_3.play();
		clapFoodModifier();
		clapFood2Modifier();
//		mEngine.registerUpdateHandler(new TimerHandler(1.0f,
//				new ITimerCallback() {
//					@Override
//					public void onTimePassed(TimerHandler arg0) {
//						setEnable(true);
//					}
//				}));
	}

	public void inital() {
		isTouchBoy = true;
		isTouchSalt = true;
		isTouchPeper = true;
		isTouchGimmic = true;
		isTouchMayonnaise = true;
		isTouchKetchup = true;
		isTouchLuchbox = true;
		isTouchGomashio = true;
		isTouchButterbur = true;
		isTouchGinger = true;
		isTouchLotusroot = true;
		isTouchCarrot = true;
		isTouchShiitake = true;
		isTouchCherry = true;
		isTouchRiceball = true;
		isTouchBurdock = true;
		isTouchGomashiobox = true;
		isTouchButterburbox = true;
		isTouchGingerbox = true;
		isTouchLotusrootbox = true;
		isTouchCarrotbox = true;
		isTouchShiitakebox = true;
		isTouchCherrybox = true;
		isTouchRiceball1box = true;
		isTouchRiceball2box = true;
		isTouchBurdockbox = true;
		istouchBoxMini = true;
		istouchBox1 = true;
		istouchBox2 = true;
		istouchBox3 = true;
		istouchBox4 = true;
		inBox1 = false;
		inBox2 = false;
		inBox3 = false;
		inBox4 = false;
		inBox5 = false;
		inBox6 = false;
		inBox7 = false;
		inBox8 = false;
		inBox9 = false;
	}

	private void setEnable(boolean state) {
		isTouchGomashiobox = state;
		isTouchGomashio = state;
		isTouchButterburbox = state;
		isTouchButterbur = state;
		isTouchGingerbox = state;
		isTouchGinger = state;
		isTouchLotusrootbox = state;
		isTouchLotusroot = state;
		isTouchCarrotbox = state;
		isTouchCarrot = state;
		isTouchShiitakebox = state;
		isTouchShiitake = state;
		isTouchCherrybox = state;
		isTouchCherry = state;
		isTouchRiceball1box = state;
		isTouchRiceball2box = state;
		isTouchRiceball = state;
		isTouchBurdockbox = state;
		isTouchBurdock = state;
		isTouchLuchbox = state;
		isTouchLuchbox = state;
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> pModifier, IEntity arg1) {

		if (pModifier.equals(sqGomashioaway)) {
			inBox1 = false;

		}
		if (pModifier.equals(sqButterburaway)) {
			inBox2 = false;

		}
		if (pModifier.equals(sqGingeraway)) {
			inBox3 = false;

		}
		if (pModifier.equals(sqLotusrootaway)) {
			inBox4 = false;

		}
		if (pModifier.equals(sqCarrotaway)) {
			inBox5 = false;

		}
		if (pModifier.equals(sqShiitakeaway)) {
			inBox6 = false;

		}
		if (pModifier.equals(sqCherryaway)) {
			inBox7 = false;

		}
		if (pModifier.equals(sqRiceball1away)) {
			inBox8 = false;

		}
		if (pModifier.equals(sqBurdockaway)) {
			inBox9 = false;
		}
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(sqLuchbox_buck)) {
			istouchBox1 = true;
		}
		if (pModifier.equals(sqLuchbox_partition1)) {
			istouchBox2 = true;
		}
		if (pModifier.equals(sqLuchbox_partition2)) {
			istouchBox3 = true;
		}
		if (pModifier.equals(sqLuchbox_front)) {
			istouchBox4 = true;
		}
		if (pModifier.equals(sqSaltModifier)) {
			isTouchSalt = true;
		}
		if (pModifier.equals(sqPeperModifier)) {
			isTouchPeper = true;
		}
		if (pModifier.equals(sqMayonnaise)) {
			sMayonnaiseup.setVisible(false);
			isTouchMayonnaise = true;

		}
		if (pModifier.equals(sqKetchup)) {
			sKetchupup.setVisible(false);
			isTouchKetchup = true;
			// =============================================================//
		}
		if (pModifier.equals(sqGomashio)) {
			isTouchGomashio = true;
			isTouchGomashiobox = true;

		} else if (pModifier.equals(sqButterbur)) {
			isTouchButterbur = true;
			isTouchButterburbox = true;

		}
		if (pModifier.equals(sqGinger)) {
			isTouchGinger = true;
			isTouchGingerbox = true;

		}
		if (pModifier.equals(sqLotusroot)) {
			isTouchLotusroot = true;
			isTouchLotusrootbox = true;

		}
		if (pModifier.equals(sqCarrot)) {
			isTouchCarrot = true;
			isTouchCarrotbox = true;

		}
		if (pModifier.equals(sqShiitake)) {
			isTouchShiitake = true;
			isTouchShiitakebox = true;

		}
		if (pModifier.equals(sqCherry)) {
			isTouchCherry = true;
			isTouchCherrybox = true;

		}
		if (pModifier.equals(sqRiceball)) {
			isTouchRiceball = true;
			isTouchRiceball1box = true;
			isTouchRiceball2box = true;

		}
		if (pModifier.equals(sqBurdock)) {
			isTouchBurdock = true;
			isTouchBurdockbox = true;

			// ==========================================================//
		}
		if (pModifier.equals(sqGomashiobox)) {
			sGomashio_box.setPosition(469.167f, 330f);
			isTouchGomashiobox = true;
			isTouchGomashio = true;

		}
		if (pModifier.equals(sqButterburbox)) {
			sButterbur_box.setPosition(535f, 290.5f);
			isTouchButterburbox = true;
			isTouchButterbur = true;

		}
		if (pModifier.equals(sqGingerbox)) {
			sGinger_box.setPosition(440.833f, 300.5f);
			isTouchGingerbox = true;
			isTouchGinger = true;

		}
		if (pModifier.equals(sqLotusrootbox)) {
			sLotusroot_box.setPosition(610.167f, 392.5f);
			isTouchLotusrootbox = true;
			isTouchLotusroot = true;

		}
		if (pModifier.equals(sqCarrotbox)) {
			sCarrot_box.setPosition(440.833f, 421.667f);
			isTouchCarrotbox = true;
			isTouchCarrot = true;

		}
		if (pModifier.equals(sqShiitakebox)) {
			sShiitake_box.setPosition(499.167f, 421.667f);
			isTouchShiitakebox = true;
			isTouchShiitake = true;

		}
		if (pModifier.equals(sqCherrybox)) {
			sCherry_box.setPosition(635.297f, 324.079f);
			isTouchCherrybox = true;
			isTouchCherry = true;

		}
		if (pModifier.equals(sqRiceball1box)) {
			sRiceball1_box.setPosition(255.833f, 422.0f);
			isTouchRiceball1box = true;
			isTouchRiceball2box = true;
			isTouchRiceball = true;

		}
		if (pModifier.equals(sqRiceball2box)) {
			sRiceball2_box.setPosition(257.5f, 319.167f);
			isTouchRiceball1box = true;
			isTouchRiceball2box = true;
			isTouchRiceball = true;

		}
		if (pModifier.equals(sqBurdockbox)) {
			sBurdock_box.setPosition(585f, 385.5f);
			isTouchBurdockbox = true;
			isTouchBurdock = true;

		}
		if (pModifier.equals(sqLuchboxmini)) {
			istouchBoxMini=true;
			if (!(inBox1 || inBox2 || inBox3 || inBox4 || inBox5 || inBox6
					|| inBox7 || inBox8 || inBox9)) {
				setEnable(true);
			}

		}
		// =============================================================//
		if (pModifier.equals(sqGomashiocome)) {
			inBox1 = true;
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
			mp310_2.play();

		}
		if (pModifier.equals(sqButterburcome)) {
			inBox2 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqGingercome)) {
			inBox3 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqLotusrootcome)) {
			inBox4 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqCarrotcome)) {
			inBox5 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqShiitakecome)) {
			inBox6 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqCherrycome)) {
			inBox7 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqRiceball1come)) {
			inBox8 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		if (pModifier.equals(sqBurdockcome)) {
			inBox9 = true;
			mp310_2.play();
			if (inBox1 && inBox2 && inBox3 && inBox4 && inBox5 && inBox6
					&& inBox7 && inBox8 && inBox9) {
				clapFood();
			}
		}
		
		if (pModifier.equals(sqClapFood)||pModifier.equals(sqClapFood2)) {
			setEnable(true);
		}
//		if (pModifier.equals(sqGomashioaway)) {
//			inBox1 = false;
//
//		}
//		if (pModifier.equals(sqButterburaway)) {
//			inBox2 = false;
//
//		}
//		if (pModifier.equals(sqGingeraway)) {
//			inBox3 = false;
//
//		}
//		if (pModifier.equals(sqLotusrootaway)) {
//			inBox4 = false;
//
//		}
//		if (pModifier.equals(sqCarrotaway)) {
//			inBox5 = false;
//
//		}
//		if (pModifier.equals(sqShiitakeaway)) {
//			inBox6 = false;
//
//		}
//		if (pModifier.equals(sqCherryaway)) {
//			inBox7 = false;
//
//		}
//		if (pModifier.equals(sqRiceball1away)) {
//			inBox8 = false;
//
//		}
//		if (pModifier.equals(sqBurdockaway)) {
//			inBox9 = false;
//		}
	}
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite == sBoy) {
			sBoy.setCurrentTileIndex(0);
			mEngine.registerUpdateHandler(new TimerHandler(0.5f,
					new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler arg0) {
							isTouchBoy = true;
							isTouchGimmic = true;

						}
					}));
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
