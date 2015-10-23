package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ShiawaseResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
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
import org.andengine.util.modifier.ease.EaseQuadOut;

public class Vol3Shiawase extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {

	private ITextureRegion ttrBackground, ttrBackground2, ttrBird6, ttrBall,
			ttrBird1, ttrBee, ttrHeart, ttrHeartlisu, ttrKomo1, ttrKomo2,
			ttrKomo3, ttrBridchild1, ttrBridchild2, ttrBirdchildrento,
			ttrBirdchildrenaway, ttrBrid5;

	private TiledTextureRegion ttrCat, ttrFaceboy, ttrFacegirl, ttrHousefather,
			ttrHousemother, ttrClover, ttrLisu, ttrKodomo1, ttrBodygirl,
			ttrBodyboy, ttrClapboy, ttrClapgirl, ttrHappy123, ttrHappy45;

	private Sprite sBackground2, sBird6, sBall, sBird1, sKomo1, sKomo2, sKomo3,
			sBirdchild1, sBirdchild2, sBee, sHeart, sHeartlisu, sBirdParentto,
			sBirdParentaway, sBird5;

	private AnimatedSprite anmCat, anmHousefather, anmHousemother, anmFaceboy,
			anmFacegirl, anmClover, anmLisu, anmKodomo1, anmBodyboy,
			anmBodygirl, anmClapboy, anmClapgirl, sHappy123, sHappy45;

	private LoopEntityModifier komo1Modifier, komo2Modifier, komo3Modifier;

	private SequenceEntityModifier beeModifier, heartModifier,
			heartlisuModifier, birdparentawayModifier, birdparenttoModifier;

	private boolean isTouchCat, isTouchHouse, isTouchclover, isTouchlisu,
			isTouchkodomo, isTouchbodygirl, isTouchbodyboy, isTouchGimic,
			isTouchhappy, isTouchbirdchild;

	private int touchHouse = 0, posBodyboy = 0, posBodygirl = 0;
	private static final float FROMXBEE = 1000;
	private static final float FROMYBEE = 411;
	private static final float FROMYHEART = 249;
	private static final float FROMYHEARTLISU = 295;
	private static final float XBRIDC1 = 657;
	private static final float YBRIDC1 = 55;

	private float[] arrayXbirdchildren = new float[] { 657, 683 };
	private float[] arrayYbirdchildren = new float[] { 57, 50 };

	private static final float XBRID_PARENT_START = 320;
	private static final float YBRID_PARENT_START = -100;

	private long timeBeefly = 0;
	private boolean norMal = true; // flag to refer to that state of body, true:
									// normal state, false: clap state;

	public Sound mp3Cat, mp3Personl_change, mp3Personl_clap, mp3Clover,
			mp3House, mp3Kodomo, mp3Lisu, mp3Bird, mp3Happy1, mp3Happy2;

	private TexturePack texturePackAnimation, texturePackBackground,
			texturePackHappy123, texturePackHappy45;

	private TexturePackTextureRegionLibrary mTextPackLibAnimation,
			mTextPackLibBackground, mTextPackLibHappy123, mTextPackLibHappy45;

	@Override
	protected void loadKaraokeResources() {

		texturePackBackground = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "shiawase/gfx/")
				.load("shiawase_background.xml");
		texturePackBackground.loadTexture();
		mTextPackLibBackground = texturePackBackground
				.getTexturePackTextureRegionLibrary();

		ttrBackground = mTextPackLibBackground
				.get(Vol3ShiawaseResource.shiawase_background.A16_15_1_IPHONE_HAIKEI_ID);

		ttrBackground2 = mTextPackLibBackground
				.get(Vol3ShiawaseResource.shiawase_background.A16_15_2_IPHONE_HAIKEI_ID);

		texturePackAnimation = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "shiawase/gfx/")
				.load("shiawase_animation.xml");
		texturePackAnimation.loadTexture();
		mTextPackLibAnimation = texturePackAnimation
				.getTexturePackTextureRegionLibrary();

		ttrKomo1 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_13_1_IPHONE_KUMO_ID);
		ttrKomo2 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_13_2_IPHONE_KUMO_ID);
		ttrKomo3 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_13_3_IPHONE_KUMO_ID);

		ttrBridchild1 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_2_IPHONE_BIRD_ID);
		ttrBridchild2 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_3_IPHONE_BIRD_ID);

		ttrBrid5 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_5_IPHONE_BIRD_ID);
		ttrBird6 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_6_IPHONE_BIRD_ID);

		ttrBird1 = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_1_IPHONE_BIRD_ID);

		ttrBall = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_10_1_IPHONE_BALL_ID);

		ttrBall = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_10_1_IPHONE_BALL_ID);

		ttrFacegirl = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_1_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_2_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_3_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_4_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_5_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_4_IPHONE_CLAP_ID));

		ttrFaceboy = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_1_1_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_1_2_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_1_3_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_1_4_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_1_5_IPHONE_FACE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_4_IPHONE_CLAP_ID));

		ttrCat = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_07_1_IPHONE_CAT_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_07_2_IPHONE_CAT_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_07_3_IPHONE_CAT_ID));
		ttrClover = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_06_1_IPHONE_CLOVER_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_06_2_IPHONE_CLOVER_ID));

		ttrBee = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_06_3_IPHONE_BEE_ID);
		ttrHeart = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_04_1_IPHONE_HEART_ID);
		ttrLisu = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_11_1_IPHONE_LISU_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_11_2_IPHONE_LISU_ID));

		ttrHeartlisu = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_11_3_IPHONE_LISU_ID);

		ttrKodomo1 = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_1_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_2_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_3_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_4_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_5_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_6_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_7_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_8_IPHONE_KODOMO_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_09_9_IPHONE_KODOMO_ID));

		ttrHousefather = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_1_IPHONE_HOUSE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_2_IPHONE_HOUSE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_3_IPHONE_HOUSE_ID));

		ttrHousemother = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_1_IPHONE_HOUSE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_4_IPHONE_HOUSE_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_08_5_IPHONE_HOUSE_ID));

		ttrBodyboy = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_2_1_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_2_2_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_2_3_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_1_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_2_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_3_IPHONE_CLAP_ID));

		ttrClapboy = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_5_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_05_3_5_IPHONE_CLAP_2_ID));

		ttrBodygirl = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_2_1_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_2_2_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_2_3_IPHONE_BODY_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_1_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_2_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_3_IPHONE_CLAP_ID));

		ttrClapgirl = new TiledTextureRegion(
				texturePackAnimation.getTexture(),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_5_IPHONE_CLAP_ID),
				mTextPackLibAnimation
						.get(Vol3ShiawaseResource.shiawase_animation.A16_04_3_5_IPHONE_CLAP_2_ID));

		ttrBirdchildrento = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_4_IPHONE_BIRD_ID);

		ttrBirdchildrenaway = mTextPackLibAnimation
				.get(Vol3ShiawaseResource.shiawase_animation.A16_12_7_IPHONE_BIRD_ID);

		texturePackHappy123 = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "shiawase/gfx/")
				.load("shiawase_happy123.xml");
		texturePackHappy123.loadTexture();
		mTextPackLibHappy123 = texturePackHappy123
				.getTexturePackTextureRegionLibrary();

		ttrHappy123 = new TiledTextureRegion(
				texturePackHappy123.getTexture(),
				mTextPackLibHappy123
						.get(Vol3ShiawaseResource.shiawase_happy123.A16_14_1_IPHONE_HAPPY_ID),
				mTextPackLibHappy123
						.get(Vol3ShiawaseResource.shiawase_happy123.A16_14_2_IPHONE_HAPPY_ID),
				mTextPackLibHappy123
						.get(Vol3ShiawaseResource.shiawase_happy123.A16_14_3_IPHONE_HAPPY_ID));

		texturePackHappy45 = new TexturePackLoaderFromSource(
				this.getTextureManager(), pAssetManager, "shiawase/gfx/")
				.load("shiawase_happy45.xml");
		texturePackHappy45.loadTexture();
		mTextPackLibHappy45 = texturePackHappy45
				.getTexturePackTextureRegionLibrary();

		ttrHappy45 = new TiledTextureRegion(
				texturePackHappy45.getTexture(),
				mTextPackLibHappy45
						.get(Vol3ShiawaseResource.shiawase_happy45.A16_14_4_IPHONE_HAPPY_ID),
				mTextPackLibHappy45
						.get(Vol3ShiawaseResource.shiawase_happy45.A16_14_5_IPHONE_HAPPY_ID));
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();

		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground, this.getVertexBufferObjectManager())));

		mScene.setOnAreaTouchTraversalFrontToBack();

		sBackground2 = new Sprite(0, 0, ttrBackground2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);

		sKomo1 = new Sprite(-66, 34, ttrKomo1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKomo1);

		sKomo2 = new Sprite(374, 21, ttrKomo2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKomo2);

		sHeartlisu = new Sprite(745, 295, ttrHeartlisu,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sHeartlisu);
		sHeartlisu.setVisible(false);

		sKomo3 = new Sprite(746, 53, ttrKomo3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sKomo3);

		anmLisu = new AnimatedSprite(664, 295, ttrLisu,
				this.getVertexBufferObjectManager());
		mScene.attachChild(anmLisu);

		sBird6 = new Sprite(695, 26, ttrBird6,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBird6);

		sBirdParentto = new Sprite(XBRID_PARENT_START, YBRID_PARENT_START,
				ttrBirdchildrento, this.getVertexBufferObjectManager());
		sBirdParentto.setVisible(false);
		this.mScene.attachChild(sBirdParentto);

		sBirdParentaway = new Sprite(XBRIDC1, YBRIDC1, ttrBirdchildrenaway,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBirdParentaway);
		sBirdParentaway.setVisible(false);

		sBirdchild1 = new Sprite(757, 147, ttrBridchild1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBirdchild1);

		sBirdchild2 = new Sprite(782, 139, ttrBridchild2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBirdchild2);

		sBird5 = new Sprite(758, 113, ttrBrid5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBird5);
		sBird5.setVisible(false);

		sBird1 = new Sprite(753, 139, ttrBird1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBird1);

		anmKodomo1 = new AnimatedSprite(379, 133, ttrKodomo1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(anmKodomo1);

		anmHousefather = new AnimatedSprite(28, 29, ttrHousefather,
				this.getVertexBufferObjectManager());
		mScene.attachChild(anmHousefather);

		anmHousemother = new AnimatedSprite(28, 29, ttrHousemother,
				this.getVertexBufferObjectManager());
		mScene.attachChild(anmHousemother);

		sBall = new Sprite(507, 215, ttrBall,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBall);

		anmClapboy = new AnimatedSprite(489, 255, ttrClapboy,
				this.getVertexBufferObjectManager());
		anmClapboy.setVisible(false);
		this.mScene.attachChild(anmClapboy);

		anmBodyboy = new AnimatedSprite(489, 255, ttrBodyboy,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmBodyboy);

		anmClapgirl = new AnimatedSprite(267, 229, ttrClapgirl,
				this.getVertexBufferObjectManager());
		anmClapgirl.setVisible(false);
		this.mScene.attachChild(anmClapgirl);

		anmBodygirl = new AnimatedSprite(267, 229, ttrBodygirl,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmBodygirl);

		anmFaceboy = new AnimatedSprite(489, 255, ttrFaceboy,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmFaceboy);

		anmFacegirl = new AnimatedSprite(267, 229, ttrFacegirl,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmFacegirl);

		anmCat = new AnimatedSprite(26, 377, ttrCat,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmCat);

		anmClover = new AnimatedSprite(651, 406, ttrClover,
				this.getVertexBufferObjectManager());
		mScene.attachChild(anmClover);

		sBee = new Sprite(1000, 411, ttrBee,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBee);

		sHeart = new Sprite(450, 249, ttrHeart,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHeart);
		sHeart.setVisible(false);

		sHappy123 = new AnimatedSprite(-17, -45, ttrHappy123,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHappy123);

		sHappy45 = new AnimatedSprite(-17, -45, ttrHappy45,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHappy45);
		sHappy45.setVisible(false);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3ShiawaseResource.SOUND_GIMMIC,
				Vol3ShiawaseResource.IMAGE_GIMMIC_ID, mTextPackLibAnimation);
	}

	@Override
	protected void loadKaraokeSound() {
		mp3Personl_change = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_4_5_BYON);
		mp3Personl_clap = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_4_5_CRAP);
		mp3Clover = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_06_KIRA3);
		mp3Cat = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_4_5_KIRA5);
		mp3House = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_08_KIRA7);
		mp3Kodomo = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_9_WARAIGOE);
		mp3Lisu = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_11_CHU7);
		mp3Bird = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_12_KIRA8);
		mp3Happy1 = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_14_POWA4);
		mp3Happy2 = loadSoundResourceFromSD(Vol3ShiawaseResource.A16_BANDLUN);
	}

	@Override
	protected void loadKaraokeComplete() {
		isTouchCat = true;
		isTouchHouse = true;
		isTouchclover = true;
		isTouchlisu = true;
		isTouchkodomo = true;
		isTouchGimic = true;
		isTouchbirdchild = true;
		isTouchbodyboy = true;
		isTouchbodygirl = true;
		isTouchhappy = true;

		norMal = true;

		komoModifierFunction(sKomo1, komo1Modifier);
		komoModifierFunction(sKomo2, komo2Modifier);
		komoModifierFunction(sKomo3, komo3Modifier);

		ballModifier();

		birdchild1Modifier();
		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						birdchild2Modifier();
					}
				}));
		super.loadKaraokeComplete();
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("shiawase/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("shiawase/gfx/");
		super.onCreateResources();
	}

	@Override
	public void onResume() {
		setEnable(true);
		if (anmBodyboy != null) {
			anmBodyboy.setCurrentTileIndex(0);
		}
		if (anmBodygirl != null) {
			anmBodygirl.setCurrentTileIndex(0);
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (isLoadingComplete) {
			mEngine.clearUpdateHandlers();
			mp3Personl_change.stop();
			mp3Personl_clap.stop();
			mp3Clover.stop();
			mp3Cat.stop();
			mp3House.stop();
			mp3Kodomo.stop();
			mp3Lisu.stop();
			mp3Bird.stop();
			mp3Happy1.stop();
			mp3Happy2.stop();
			anmBodyboy.setCurrentTileIndex(0);
			anmBodygirl.setCurrentTileIndex(0);
		}
		super.onPause();
	}

	private void happyChangeAnimation() {
		if (isTouchhappy) {
			if (sHappy123.getCurrentTileIndex() < 2) {
				mp3Happy1.play();
				nextTileAnimatedSprite(sHappy123);
			} else {
				isTouchhappy = false;
				sHappy123.setVisible(false);
				sHappy45.setVisible(true);
				mp3Happy1.play();
				long pDurationCat[] = new long[] { 500, 700, 0 };
				int pFramsCat[] = new int[] { 0, 1, 0 };
				sHappy45.animate(pDurationCat, pFramsCat, 0);
				mEngine.registerUpdateHandler(new TimerHandler(0.4f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(
									TimerHandler paramTimerHandler) {
								mp3Happy2.play();
							}
						}));
				mEngine.registerUpdateHandler(new TimerHandler(1.6f,
						new ITimerCallback() {

							@Override
							public void onTimePassed(
									TimerHandler paramTimerHandler) {
								sHappy123.setVisible(true);
								sHappy123.setCurrentTileIndex(0);
								sHappy45.setVisible(false);
								isTouchhappy = true;
							}
						}));
			}
		}
	}

	private void beeModifier() {

		sBee.setVisible(true);
		sBee.setAlpha(1.0f);
		sBee.registerEntityModifier(beeModifier = new SequenceEntityModifier(
				(new MoveXModifier(0.6f, FROMXBEE, 700)), (new MoveYModifier(
						0.2f, FROMYBEE, FROMYBEE - 30)), (new MoveYModifier(
						0.4f, FROMYBEE - 30, FROMYBEE + 30)),
				(new MoveYModifier(0.2f, FROMYBEE + 30, FROMYBEE)),
				(new AlphaModifier(0.2f, 1.0f, 0.8f))));
		if (beeModifier != null) {
			beeModifier.addModifierListener(this);
		}
	}

	private void birdParenttoModifier() {

		mp3Bird.play();

		sBirdParentto.setVisible(true);
		sBird5.setVisible(true);

		Random ran = new Random();
		final int chosebird = ran.nextInt(2);

		sBirdchild1.clearEntityModifiers();
		sBirdchild1.setPosition(757, 147);
		sBirdchild2.clearEntityModifiers();
		sBirdchild2.setPosition(782, 139);

		long pDurationCat[] = new long[] { 100, 3800, 1000, 200 };
		int pFramsCat[] = new int[] { 0, 4, 5, 0 };

		anmFaceboy.animate(pDurationCat, pFramsCat, 0, this);
		anmFacegirl.animate(pDurationCat, pFramsCat, 0, this);

		mEngine.registerUpdateHandler(new TimerHandler(3.5f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						anmClapboy.setVisible(true);
						anmClapgirl.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								50 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						norMal = false;
						bodygirlChangeResourcewhenclap(norMal);
						bodyboyChangeResourcewhenclap(norMal);
						mp3Personl_clap.play();
						mEngine.registerUpdateHandler(new TimerHandler(1.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapgirl.setVisible(false);
										anmClapboy.setVisible(false);
										norMal = true;
										bodygirlChangeResourcewhenclap(norMal);
										bodyboyChangeResourcewhenclap(norMal);
									}
								}));
						heartModifier(0.7f, 0.5f);
					}
				}));

		sBirdParentto
				.registerEntityModifier(birdparenttoModifier = new SequenceEntityModifier(

				new MoveModifier(2.4f, sBirdParentto.getX(),
						arrayXbirdchildren[chosebird], sBirdParentto.getY(),
						arrayYbirdchildren[chosebird]), new DelayModifier(1.0f)));
		birdparenttoModifier
				.addModifierListener(new IModifierListener<IEntity>() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sBirdParentto.setVisible(false);
						sBirdParentaway.setVisible(true);

						sBirdParentaway
								.registerEntityModifier(birdparentawayModifier = new SequenceEntityModifier(

								new MoveModifier(1.4f,
										arrayXbirdchildren[chosebird],
										sBirdParentaway.getX() + 200,
										arrayYbirdchildren[chosebird],
										sBirdParentaway.getY() - 300)));
						birdparentawayModifier
								.addModifierListener(new IModifierListener<IEntity>() {

									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										sBirdParentaway.setVisible(false);
										birdparentawayModifier = null;
										sBirdParentto.setVisible(false);

										sBird5.setVisible(false);
										sBirdParentto.setPosition(
												XBRID_PARENT_START,
												YBRID_PARENT_START);
										sBirdParentaway.setPosition(XBRIDC1,
												YBRIDC1);

										// nen set enable ko
										setEnable(true);
										// ////////////
										birdchild1Modifier();
										mEngine.registerUpdateHandler(new TimerHandler(
												3f, new ITimerCallback() {

													@Override
													public void onTimePassed(
															TimerHandler paramTimerHandler) {
														birdchild2Modifier();
													}
												}));
									}

									@Override
									public void onModifierStarted(
											IModifier<IEntity> paramIModifier,
											IEntity paramT) {

									}
								});
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {

					}
				});

	}

	private void heartModifier(float durationMo, float duarationA) {
		sHeart.setVisible(true);
		sHeart.setAlpha(1.0f);
		sHeart.registerEntityModifier(heartModifier = new SequenceEntityModifier(
				(new MoveYModifier(durationMo, FROMYHEART, FROMYHEART - 50)),
				(new AlphaModifier(duarationA, 1.0f, 0.5f))));
		if (heartModifier != null) {
			heartModifier.addModifierListener(this);
		}
	}

	private void heartlisuModifier() {

		sHeartlisu.setVisible(true);
		sHeartlisu.setAlpha(1.0f);
		sHeartlisu
				.registerEntityModifier(heartlisuModifier = new SequenceEntityModifier(
						(new MoveYModifier(0.7f, FROMYHEARTLISU,
								FROMYHEARTLISU - 30)), (new AlphaModifier(0.5f,
								1.0f, 0.5f))));

		if (heartlisuModifier != null) {
			heartlisuModifier.addModifierListener(this);
		}
	}

	// animation of kodomo
	private void kodomo1ChangeResource() {
		sBall.setVisible(false);
		mp3Kodomo.play();
		anmKodomo1.setVisible(true);
		anmKodomo1.animate(410, false, this);
		// faceboykodomoChangeResource();
		// facegirlkodomoChangeResource();
		long pDurationCat[] = new long[] { 100, 500, 3000, 200 };
		int pFramsCat[] = new int[] { 0, 4, 5, 0 };

		anmFacegirl.animate(pDurationCat, pFramsCat, 0);
		anmFaceboy.animate(pDurationCat, pFramsCat, 0);
		mEngine.registerUpdateHandler(new TimerHandler(0.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						mp3Personl_clap.play();
						norMal = false;
						bodyboyChangeResourcewhenclap(norMal);
						bodygirlChangeResourcewhenclap(norMal);
						anmClapgirl.setVisible(true);
						anmClapboy.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								200 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						mEngine.registerUpdateHandler(new TimerHandler(1f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mp3Personl_clap.play();
										long pDurationCat[] = new long[] { 200,
												200, 200, 200, 200 };
										int pFramsCat[] = new int[] { 0, 1, 0,
												1, 0 };
										anmClapgirl.animate(pDurationCat,
												pFramsCat, 0);
										anmClapboy.animate(pDurationCat,
												pFramsCat, 0);
									}
								}));
						mEngine.registerUpdateHandler(new TimerHandler(2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										mp3Personl_clap.play();
										long pDurationCat[] = new long[] { 200,
												200, 200, 200, 200 };
										int pFramsCat[] = new int[] { 0, 1, 0,
												1, 0 };
										anmClapgirl.animate(pDurationCat,
												pFramsCat, 0);
										anmClapboy.animate(pDurationCat,
												pFramsCat, 0);
									}
								}));
						mEngine.registerUpdateHandler(new TimerHandler(3.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapboy.setVisible(false);
										anmClapgirl.setVisible(false);
										norMal = true;
										bodyboyChangeResourcewhenclap(norMal);
										bodygirlChangeResourcewhenclap(norMal);
										sBall.setVisible(true);
									}
								}));
						heartModifier(1.6f, 1.4f);
					}
				}));

		mEngine.registerUpdateHandler(new TimerHandler(4.51f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						sBall.setVisible(true);
						anmKodomo1.setVisible(true);
						setEnable(true);
					}
				}));
	}

	// animation of cat
	private void catChangeResource() {
		mp3Cat.play();
		long pDurationCat[] = new long[] { 200, 200, 200, 200, 200, 200, 200,
				400, 200 };
		int pFramsCat[] = new int[] { 0, 1, 2, 1, 2, 1, 2, 1, 0 };
		anmCat.animate(pDurationCat, pFramsCat, 0, this);

		long pDurationCat1[] = new long[] { 100, 500, 1000, 200 };
		int pFramsCat1[] = new int[] { 0, 1, 5, 0 };
		anmFaceboy.animate(pDurationCat1, pFramsCat1, 0, this);
		anmFacegirl.animate(pDurationCat1, pFramsCat1, 0, this);

		mEngine.registerUpdateHandler(new TimerHandler(0.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						anmClapboy.setVisible(true);
						anmClapgirl.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								50 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						norMal = false;
						bodygirlChangeResourcewhenclap(norMal);
						bodyboyChangeResourcewhenclap(norMal);
						mp3Personl_clap.play();
						mEngine.registerUpdateHandler(new TimerHandler(1.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapgirl.setVisible(false);
										anmClapboy.setVisible(false);
										norMal = true;
										bodygirlChangeResourcewhenclap(norMal);
										bodyboyChangeResourcewhenclap(norMal);
									}
								}));
						heartModifier(0.7f, 0.5f);
					}
				}));
	}

	// animation of bodyboy when clap
	private void bodyboyChangeResourcewhenclap(boolean flag) {

		if (flag) {
			anmBodyboy.setCurrentTileIndex(posBodyboy);
		} else {
			anmBodyboy.setCurrentTileIndex(posBodyboy + 3);
		}
	}

	// animation of bodyboy
	private void bodyboyChangeResource(boolean flag) {
		if (isTouchbodyboy) {
			mp3Personl_change.play();
			isTouchbodyboy = false;

			posBodyboy++;
			if (posBodyboy > 2) {
				posBodyboy = 0;
			}

			if (flag) {
				anmBodyboy.setCurrentTileIndex(posBodyboy);
			} else {
				anmBodyboy.setCurrentTileIndex(posBodyboy + 3);
			}
			mEngine.registerUpdateHandler(new TimerHandler(0.3f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							isTouchbodyboy = true;
						}
					}));
		}
	}

	// animation of bodygirl when clap
	private void bodygirlChangeResourcewhenclap(boolean flag) {

		if (flag) {
			anmBodygirl.setCurrentTileIndex(posBodygirl);
		} else {
			anmBodygirl.setCurrentTileIndex(posBodygirl + 3);
		}
	}

	// animation of bodygirl
	private void bodygirlChangeResource(boolean flag) {
		if (isTouchbodygirl) {
			posBodygirl++;
			if (posBodygirl > 2) {
				posBodygirl = 0;
			}
			mp3Personl_change.play();

			isTouchbodygirl = false;

			if (flag) {
				anmBodygirl.setCurrentTileIndex(posBodygirl);
			} else {
				anmBodygirl.setCurrentTileIndex(posBodygirl + 3);
			}
			mEngine.registerUpdateHandler(new TimerHandler(0.3f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							isTouchbodygirl = true;
						}
					}));
		}
	}

	// animation of clover
	private void cloverChangeResource() {
		mp3Clover.play();
		long pDurationCat[] = new long[] { 1800 + timeBeefly, 200 };
		int pFramsCat[] = new int[] { 1, 0 };
		anmClover.animate(pDurationCat, pFramsCat, 0, this);

		long pDurationFace[] = new long[] { 100, 500, 1000, 200 };
		int pFramsFace[] = new int[] { 0, 2, 5, 0 };

		anmFacegirl.animate(pDurationFace, pFramsFace, 0, this);
		anmFaceboy.animate(pDurationFace, pFramsFace, 0, this);

		mEngine.registerUpdateHandler(new TimerHandler(0.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						anmClapboy.setVisible(true);
						anmClapgirl.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								50 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						norMal = false;
						bodygirlChangeResourcewhenclap(norMal);
						bodyboyChangeResourcewhenclap(norMal);
						mp3Personl_clap.play();
						mEngine.registerUpdateHandler(new TimerHandler(1.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapgirl.setVisible(false);
										anmClapboy.setVisible(false);
										norMal = true;
										bodygirlChangeResourcewhenclap(norMal);
										bodyboyChangeResourcewhenclap(norMal);
									}
								}));
						heartModifier(0.7f, 0.5f);
					}
				}));
		beeModifier();

	}

	// animation of lisu
	private void lisuChangeResource() {
		mp3Lisu.play();
		long pDurationCat[] = new long[] { 1800, 200 };
		int pFramsCat[] = new int[] { 1, 0 };
		anmLisu.animate(pDurationCat, pFramsCat, 0, this);

		long pDurationFace[] = new long[] { 100, 500, 1000, 200 };
		int pFramsFace[] = new int[] { 0, 2, 5, 0 };

		anmFacegirl.animate(pDurationFace, pFramsFace, 0, this);
		anmFaceboy.animate(pDurationFace, pFramsFace, 0, this);

		mEngine.registerUpdateHandler(new TimerHandler(0.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						anmClapboy.setVisible(true);
						anmClapgirl.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								50 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						norMal = false;
						bodygirlChangeResourcewhenclap(norMal);
						bodyboyChangeResourcewhenclap(norMal);
						mp3Personl_clap.play();
						mEngine.registerUpdateHandler(new TimerHandler(1.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapgirl.setVisible(false);
										anmClapboy.setVisible(false);
										norMal = true;
										bodygirlChangeResourcewhenclap(norMal);
										bodyboyChangeResourcewhenclap(norMal);
									}
								}));
						heartModifier(0.7f, 0.5f);
					}
				}));

		heartlisuModifier();

	}

	public void houseTouchChange(int touch) {
		mp3House.play();

		long pDurationHouse[] = new long[] { 200, 300, 1300, 200 };
		int pFrameHouse[] = new int[] { 0, 1, 2, 0 };
		switch (touch) {
		case 0:
			anmHousefather.setVisible(true);
			anmHousemother.setVisible(false);
			anmHousefather.animate(pDurationHouse, pFrameHouse, 0, this);
			break;
		case 1:
			anmHousemother.setVisible(true);
			anmHousefather.setVisible(false);
			anmHousemother.animate(pDurationHouse, pFrameHouse, 0, this);
			break;
		}
		long pDurationCat[] = new long[] { 100, 500, 1000, 200 };
		int pFramsCat[] = new int[] { 0, 3, 5, 0 };

		anmFaceboy.animate(pDurationCat, pFramsCat, 0, this);
		anmFacegirl.animate(pDurationCat, pFramsCat, 0, this);
		mEngine.registerUpdateHandler(new TimerHandler(0.6f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler paramTimerHandler) {
						anmClapboy.setVisible(true);
						anmClapgirl.setVisible(true);
						long pDurationCat[] = new long[] { 200, 200, 200, 200,
								50 };
						int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
						anmClapgirl.animate(pDurationCat, pFramsCat, 0);
						anmClapboy.animate(pDurationCat, pFramsCat, 0);
						norMal = false;
						bodygirlChangeResourcewhenclap(norMal);
						bodyboyChangeResourcewhenclap(norMal);
						mp3Personl_clap.play();
						mEngine.registerUpdateHandler(new TimerHandler(1.2f,
								new ITimerCallback() {

									@Override
									public void onTimePassed(
											TimerHandler paramTimerHandler) {
										anmClapgirl.setVisible(false);
										anmClapboy.setVisible(false);
										norMal = true;
										bodygirlChangeResourcewhenclap(norMal);
										bodyboyChangeResourcewhenclap(norMal);
									}
								}));
						heartModifier(0.7f, 0.5f);
					}
				}));
	}

	private void komoModifierFunction(Sprite skomo,
			LoopEntityModifier komoModifier) {
		skomo.registerEntityModifier((IEntityModifier) (komoModifier = new LoopEntityModifier(
				new SequenceEntityModifier(new MoveXModifier(2.5f,
						skomo.getX(), skomo.getX() + 60, EaseQuadOut
								.getInstance()), new MoveXModifier(2.5f, skomo
						.getX() + 60, skomo.getX(), EaseQuadOut.getInstance())))));
		return;
	}

	private void ballModifier() {
		if (sBall != null) {
			sBall.registerEntityModifier((IEntityModifier) (new LoopEntityModifier(
					new SequenceEntityModifier(new MoveXModifier(1.5f, sBall
							.getX(), sBall.getX() + 50, EaseQuadOut
							.getInstance()), new MoveXModifier(1.5f, sBall
							.getX() + 50, sBall.getX(), EaseQuadOut
							.getInstance())))));
		}
		return;
	}

	private void birdchild1Modifier() {
		if (sBirdchild1 != null) {
			sBirdchild1
					.registerEntityModifier((IEntityModifier) (new LoopEntityModifier(
							new SequenceEntityModifier(new MoveYModifier(2.5f,
									147, 137, EaseQuadOut.getInstance()),
									new MoveYModifier(2.5f, 137, 147,
											EaseQuadOut.getInstance())))));
		}
		return;
	}

	private void birdchild2Modifier() {
		if (sBirdchild2 != null) {
			sBirdchild2
					.registerEntityModifier((IEntityModifier) (new LoopEntityModifier(
							new SequenceEntityModifier(new MoveYModifier(2.5f,
									141, 133, EaseQuadOut.getInstance()),
									new MoveYModifier(2.5f, 133, 141,
											EaseQuadOut.getInstance())))));
		}
		return;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(anmCat, (int) 0, 0, (int) anmCat.getWidth(),
					(int) anmCat.getHeight() - 30, pX, pY)) {
				if (!anmCat.isAnimationRunning() && isTouchCat) {
					setEnable(false);
					catChangeResource();
				}

			} else if (checkContains(anmHousefather,
					(int) anmHousefather.getWidth() / 4, 0,
					(int) anmHousefather.getWidth() * 3 / 4,
					(int) anmHousefather.getHeight() * 3 / 4, pX, pY)) {
				if (!anmHousefather.isAnimationRunning()
						&& !anmHousemother.isAnimationRunning() && isTouchHouse) {
					setEnable(false);
					touchHouse++;
					houseTouchChange(touchHouse % 2);
				}

			} else if (checkContains(anmClover, (int) anmClover.getWidth() / 4,
					0, (int) anmClover.getWidth() * 3 / 4,
					(int) anmClover.getHeight() * 3 / 4, pX, pY)) {
				if ((beeModifier == null || beeModifier.isFinished())
						&& isTouchclover) {
					setEnable(false);
					cloverChangeResource();
				}

			} else if (checkContains(anmLisu, (int) anmLisu.getWidth() / 4, 0,
					(int) anmLisu.getWidth() * 3 / 4,
					(int) anmLisu.getHeight() * 3 / 4, pX, pY)) {
				if (!anmLisu.isAnimationRunning() && isTouchlisu) {
					setEnable(false);
					lisuChangeResource();
				}

			} else if (checkContains(anmKodomo1,
					(int) anmKodomo1.getWidth() / 2, 0,
					(int) anmKodomo1.getWidth(),
					(int) anmKodomo1.getHeight() * 3 / 4, pX, pY)) {
				if (!anmKodomo1.isAnimationRunning() && isTouchkodomo) {
					setEnable(false);
					kodomo1ChangeResource();
				}

			} else if (checkContains(anmBodyboy,
					(int) anmBodyboy.getWidth() / 4, 0,
					(int) anmBodyboy.getWidth() * 3 / 4,
					(int) anmBodyboy.getHeight() * 3 / 4, pX, pY)) {

				bodyboyChangeResource(norMal);
			} else if (checkContains(anmBodygirl,
					(int) anmBodygirl.getWidth() / 4, 0,
					(int) anmBodygirl.getWidth() * 3 / 4,
					(int) anmBodygirl.getHeight() * 3 / 4, pX, pY)) {

				bodygirlChangeResource(norMal);
			} else if (checkContains(sHappy123,
					(int) sHappy123.getWidth() * 5 / 12,
					(int) sHappy123.getHeight() * 1 / 8,
					(int) sHappy123.getWidth() * 7 / 12,
					(int) sHappy123.getHeight() / 4, pX, pY)) {
				happyChangeAnimation();
			} else if (checkContains(sBirdchild1, 0, 0,
					4 * (int) sBirdchild1.getWidth(),
					3 * (int) sBirdchild1.getHeight(), pX, pY)) {
				if (!sBirdParentto.isVisible()
						&& (birdparentawayModifier == null || birdparentawayModifier
								.isFinished()) && isTouchbirdchild) {
					setEnable(false);
					birdParenttoModifier();
				}
			}
			return true;
		}

		return false;
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimic) {
			setEnable(false);
			long pDurationCat[] = new long[] { 100, 500, 1000, 200 };
			int pFramsCat[] = new int[] { 0, 0, 5, 0 };
			anmFaceboy.animate(pDurationCat, pFramsCat, 0, this);
			anmFacegirl.animate(pDurationCat, pFramsCat, 0, this);

			mEngine.registerUpdateHandler(new TimerHandler(0.6f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							anmClapboy.setVisible(true);
							anmClapgirl.setVisible(true);
							long pDurationCat[] = new long[] { 200, 200, 200,
									200, 50 };
							int pFramsCat[] = new int[] { 0, 1, 0, 1, 0 };
							anmClapgirl.animate(pDurationCat, pFramsCat, 0);
							anmClapboy.animate(pDurationCat, pFramsCat, 0);
							norMal = false;
							bodygirlChangeResourcewhenclap(norMal);
							bodyboyChangeResourcewhenclap(norMal);
							mp3Personl_clap.play();
							mEngine.registerUpdateHandler(new TimerHandler(
									1.2f, new ITimerCallback() {

										@Override
										public void onTimePassed(
												TimerHandler paramTimerHandler) {
											anmClapgirl.setVisible(false);
											anmClapboy.setVisible(false);
											norMal = true;
											bodygirlChangeResourcewhenclap(norMal);
											bodyboyChangeResourcewhenclap(norMal);
										}
									}));
							heartModifier(0.7f, 0.5f);
						}
					}));
			mEngine.registerUpdateHandler(new TimerHandler(2.1f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							setEnable(true);
						}
					}));
		}
	}

	// setvisibale
	public void setEnable(boolean state) {
		isTouchbirdchild = state;
		isTouchCat = state;
		isTouchHouse = state;
		isTouchclover = state;
		isTouchlisu = state;
		isTouchkodomo = state;
		isTouchGimic = state;

	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

		if (pModifier.equals(beeModifier)) {
			sBee.setPosition(1000, 411);
		} else if (pModifier.equals(heartModifier)) {
			sHeart.setVisible(false);
			sHeart.setPosition(450, 249);
		} else if (pModifier.equals(heartlisuModifier)) {
			sHeartlisu.setVisible(false);
			sHeartlisu.setPosition(745, 295);
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(anmLisu)) {
			anmLisu.setCurrentTileIndex(0);
			setEnable(true);
		} else if (pAnimatedSprite.equals(anmCat)) {
			anmCat.setCurrentTileIndex(0);
			setEnable(true);
		} else if (pAnimatedSprite.equals(anmHousefather)) {
			anmHousefather.setCurrentTileIndex(0);
			setEnable(true);

		} else if (pAnimatedSprite.equals(anmHousemother)) {
			anmHousemother.setCurrentTileIndex(0);
			setEnable(true);
		} else if (pAnimatedSprite.equals(anmKodomo1)) {
			anmKodomo1.setCurrentTileIndex(0);
			sBall.setVisible(true);
		} else if (pAnimatedSprite.equals(anmClover)) {
			anmClover.setCurrentTileIndex(0);
			setEnable(true);
		} else if (pAnimatedSprite.equals(anmFaceboy)) {
			anmFaceboy.setCurrentTileIndex(0);

		} else if (pAnimatedSprite.equals(anmFacegirl)) {
			anmFacegirl.setCurrentTileIndex(0);

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
