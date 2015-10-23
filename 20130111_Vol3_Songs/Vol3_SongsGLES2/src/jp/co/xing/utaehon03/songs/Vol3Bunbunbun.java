package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3BunbunbunResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
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

public class Vol3Bunbunbun extends BaseGameFragment implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion ttrBackground, ttrTree, ttrLeaf1, ttrLeaf2,
			ttrLeaf3, ttrLeaf4, ttrLeaf5, ttrKumo1, ttrKumo2, ttrHana1,
			ttrHana2, ttrHana3, ttrHana4, ttrHana5, ttrHana6, ttrHana7,
			ttrHana8, ttrHana9, ttrJar;

	private TiledTextureRegion ttrKaeru, ttrBoy, ttrKuma, ttrBeehive,
			ttrBeeAngry, ttrBees, ttrBee, ttrBeehoney;

	private Sprite sTree, sLeaf11, sLeaf12, sLeaf13, sLeaf14, sLeaf15, sLeaf2,
			sLeaf3, sLeaf4, sLeaf5, sKumo1, sKumo2, sHana1, sHana2, sHana3,
			sHana4, sHana5, sHana32, sHana6, sHana52, sHana7, sHana8, sHana9,
			sHana21, sJar;

	private AnimatedSprite sKaeru, sBoy, sKuma, sBeehive, sBeeAngry, sBees,
			sBee, sBeehoney;

	private Sound mp3Kaeru, mp3Beehive1, mp3Beehive2, mp3Kuma1, mp3Kuma2,
			mp3Boy1, mp3Boy2, mp3Boy3, mp3Boy4, mp3TreeLeaf, mp3_4_9_10_13_1,
			mp3_4_9_10_13_2, mp3_4_9_10_13_3, mp3_4_9_10_13_4, mp3_4_9_10_13_5;

	private boolean isTouchKaure, isTouchKuma, isTouchHana1, isTouchHana2,
			isTouchHana3, isTouchHana4, isTouchHana5, isTouchHana32,
			isTouchHana6, isTouchHana52, isTouchHana7, isTouchHana8,
			isTouchHana9, isTouchHana21, isTouchLeaf, isTouchTree, isTouchBoy,
			isTouchBehive, isTouchBee;

	private SequenceEntityModifier sqBees, sqBeehive, sqBeeAngryto,
			sqBeeAngryhome, sqLeaf11, sqLeaf12, sqLeaf13, sqLeaf14, sqLeaf15,
			sqBeeGiveHoney, sqBeeBringHoney, sqKumaGotoJar, sqKumagotoAway;
	private LoopEntityModifier sqBeeTo;
	private int StateKuma = 0;
	private boolean flagBeeAngryTo = true;
	private int flagBeeTo = 0, amountHoney = 0;
	private float xHana[] = { 20, 100, 174, 230, 340, 397, 492, 216, 318, 379,
			468, 529 };
	private float xHanave[] = { -20, 50, 124, 180, 270, 330, 400, 140, 230,
			280, 368, 419 };

	private float yHana[] = { 155, 145, 160, 175, 160, 150, 160, 380, 370, 350,
			360, 350 };
	private float yHanave[] = { 155, 145, 160, 175, 160, 150, 160, 360, 360,
			350, 360, 340 };
	private Timer timer = new Timer();

	private TexturePack Vol3BunbunbunPacker_1, Vol3BunbunbunPacker_2;
	private TexturePackTextureRegionLibrary Vol3BunbunbunLibrary1,
			Vol3BunbunbunLibrary2;

	// Load resource factory include sound and images
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("bunbunbun/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("bunbunbun/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "bunbunbun/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		Vol3BunbunbunPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3BunbunbunPacker1.xml");
		Vol3BunbunbunPacker_1.loadTexture();
		Vol3BunbunbunLibrary1 = Vol3BunbunbunPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3BunbunbunPacker_2 = mTexturePackLoaderFromSource
				.load("Vol3BunbunbunPacker2.xml");
		Vol3BunbunbunPacker_2.loadTexture();
		Vol3BunbunbunLibrary2 = Vol3BunbunbunPacker_2
				.getTexturePackTextureRegionLibrary();

		ttrBackground = Vol3BunbunbunLibrary2
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_15_IPHONE_HAIKEI_ID);
		ttrKumo1 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_14_1_IPHONE_KUMO_ID);
		ttrKumo2 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_14_2_IPHONE_KUMO_ID);

		ttrHana1 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_1_IPHONE_HANA_ID);

		ttrHana2 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_2_IPHONE_HANA_ID);

		ttrHana3 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_3_IPHONE_HANA_ID);

		ttrHana4 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_4_IPHONE_HANA_ID);

		ttrHana5 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_5_IPHONE_HANA_ID);

		ttrHana6 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_6_IPHONE_HANA_ID);

		ttrHana7 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_7_IPHONE_HANA_ID);

		ttrHana8 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_13_8_IPHONE_HANA_ID);

		ttrHana9 = Vol3BunbunbunLibrary2
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_13_9_IPHONE_HANA_ID);

		ttrTree = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_11_IPHONE_TREE_ID);

		ttrLeaf1 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_12_1_IPHONE_LEAF_ID);

		ttrLeaf2 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_12_2_IPHONE_LEAF_ID);

		ttrLeaf3 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_12_3_IPHONE_LEAF_ID);

		ttrLeaf4 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_12_4_IPHONE_LEAF_ID);

		ttrLeaf5 = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_12_5_IPHONE_LEAF_ID);

		ttrKaeru = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_06_1_IPHONE_KAERU_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_06_2_IPHONE_KAERU_ID);

		ttrBeehive = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_05_1_IPHONE_BEEHIVE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_1_IPHONE_BEEHIVE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_2_IPHONE_BEEHIVE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_3_IPHONE_BEEHIVE_ID);
		ttrBoy = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_1_IPHONE_BOY_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_2_IPHONE_BOY_ID);

		ttrKuma = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_1_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_2_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_3_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_4_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_5_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_6_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_7_IPHONE_KUMA_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_07_8_IPHONE_KUMA_ID);
		ttrBeeAngry = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_3_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_4_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_3_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_08_4_IPHONE_BEE_ID);

		ttrBees = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_05_2_IPHONE_BEES_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_05_3_IPHONE_BEES_ID);

		ttrBee = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_2,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_2_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_3_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_4_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_1_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_2_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_3_1_IPHONE_BEE_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker2.A20_04_4_1_IPHONE_BEE_ID);

		ttrBeehoney = getTiledTextureFromPacker(
				Vol3BunbunbunPacker_1,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_4_IPHONE_BEEHONEY_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_5_IPHONE_BEEHONEY_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_6_IPHONE_BEEHONEY_ID,
				Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_09_7_IPHONE_BEEHONEY_ID);

		ttrJar = Vol3BunbunbunLibrary1
				.get(Vol3BunbunbunResource.Vol3BunbunbunPacker1.A20_10_IPHONE_JAR_ID);

	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();

		sTree = new Sprite(780, 117, ttrTree,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sTree);

		sKumo1 = new Sprite(173, 77, ttrKumo1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKumo1);

		sKumo2 = new Sprite(310, 49, ttrKumo2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKumo2);

		sHana1 = new Sprite(41, 210, ttrHana1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana1);

		sHana2 = new Sprite(116, 197, ttrHana2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana2);

		sHana3 = new Sprite(174, 230, ttrHana3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana3);

		sHana4 = new Sprite(270, 229, ttrHana4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana4);

		sHana5 = new Sprite(356, 207, ttrHana5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana5);

		sHana32 = new Sprite(397, 214, ttrHana3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana32);

		sHana6 = new Sprite(492, 219, ttrHana6,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana6);

		sHana52 = new Sprite(226, 429, ttrHana5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana52);

		sHana7 = new Sprite(318, 439, ttrHana7,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana7);

		sHana8 = new Sprite(379, 430, ttrHana8,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana8);

		sHana9 = new Sprite(468, 419, ttrHana9,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana9);

		sHana21 = new Sprite(529, 407, ttrHana2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHana21);

		sLeaf2 = new Sprite(471, 40, ttrLeaf2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeaf2);

		sLeaf3 = new Sprite(646, 95, ttrLeaf3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeaf3);

		sLeaf4 = new Sprite(864, 17, ttrLeaf4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeaf4);

		sLeaf5 = new Sprite(453, -139, ttrLeaf5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sLeaf5);

		sKuma = new AnimatedSprite(745, 277, ttrKuma,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKuma);

		sJar = new Sprite(680, 430, ttrJar, this.getVertexBufferObjectManager());
		sJar.setVisible(false);
		mScene.attachChild(sJar);

		sBeehoney = new AnimatedSprite(710, 140, ttrBeehoney,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBeehoney);
		sBeehoney.setVisible(false);

		sBeehive = new AnimatedSprite(640, 17, ttrBeehive,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBeehive);

		// ===========================================//
		sLeaf11 = new Sprite(620, 50, ttrLeaf1,
				this.getVertexBufferObjectManager());
		sLeaf11.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sLeaf11);
		sLeaf11.setVisible(false);

		sLeaf12 = new Sprite(783, 47, ttrLeaf1,
				this.getVertexBufferObjectManager());
		sLeaf12.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sLeaf12);
		sLeaf12.setVisible(false);

		sLeaf13 = new Sprite(680, 85, ttrLeaf1,
				this.getVertexBufferObjectManager());
		sLeaf13.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sLeaf13);
		sLeaf13.setVisible(false);

		sLeaf14 = new Sprite(800, 100, ttrLeaf1,
				this.getVertexBufferObjectManager());
		sLeaf14.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sLeaf14);
		sLeaf14.setVisible(false);

		sLeaf15 = new Sprite(650, 62, ttrLeaf1,
				this.getVertexBufferObjectManager());
		sLeaf15.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sLeaf15);
		sLeaf15.setVisible(false);
		// ===========================================//

		sKaeru = new AnimatedSprite(318, 346, ttrKaeru,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKaeru);

		sBeeAngry = new AnimatedSprite(1000, 140, ttrBeeAngry,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBeeAngry);

		sBees = new AnimatedSprite(480, 130, ttrBees,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBees);
		sBees.setVisible(false);

		sBee = new AnimatedSprite(665, 100, ttrBee,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBee);

		sBoy = new AnimatedSprite(-32, 323, ttrBoy,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBoy);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3BunbunbunResource.SOUND_GIMMIC,
				Vol3BunbunbunResource.IMAGE_GIMMIC, Vol3BunbunbunLibrary1);

	}

	private void beeTo() {
		Path path = new Path(11).to(665, 100).to(510, 230).to(410, 100)
				.to(330, 220).to(130, 80).to(-30, 200).to(130, 80).to(330, 220)
				.to(410, 100).to(510, 230).to(665, 100);
		sBee.registerEntityModifier(sqBeeTo = new LoopEntityModifier(
				new PathModifier(12.0f, path, null,
						new IPathModifierListener() {

							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									flagBeeTo = 0;
									beeChange();
									break;
								case 5:
									flagBeeTo = 1;
									beeChange();
									break;
								default:
									break;
								}
							}
						})));
		if (sqBeeTo != null) {
			sqBeeTo.addModifierListener(this);
		}
	}

	private void beeTogiveHoney(float xHana, float yHana) {
		float duration;
		double deltaDistace = calcuDistance(xHana, yHana, sBee.getX(),
				sBee.getY());
		if (deltaDistace <= 50) {
			duration = 0.6f;
		} else {
			duration = (float) (calcuDistance(xHana, yHana, sBee.getX(),
					sBee.getY()) * 6.0f / 960.0f);
		}

		sBee.clearEntityModifiers();
		sBee.registerEntityModifier(sqBeeGiveHoney = new SequenceEntityModifier(
				new MoveModifier(duration, sBee.getX(), xHana, sBee.getY(),
						yHana)));
		if (sqBeeGiveHoney != null) {
			sqBeeGiveHoney.addModifierListener(this);
		}
	}

	private void beeToBringHoney() {
		float duration;
		double deltaDistace = calcuDistance(sBee.getX(), sBee.getY(), 665, 100);
		if (deltaDistace <= 50) {
			duration = 0.6f;
		} else {
			duration = (float) (deltaDistace * 6.0f / 960.0f);
		}

		sBee.registerEntityModifier(sqBeeBringHoney = new SequenceEntityModifier(
				new MoveModifier(duration, sBee.getX(), 665, sBee.getY(), 100)));
		if (sqBeeBringHoney != null) {
			sqBeeBringHoney.addModifierListener(this);
		}
	}

	private void leaf1Fly() {
		Path path = new Path(5).to(620, 50).to(800, 200).to(600, 300)
				.to(730, 350).to(580, 400);
		sLeaf11.registerEntityModifier(sqLeaf11 = new SequenceEntityModifier(
				new ParallelEntityModifier(new PathModifier(2.5f, path, null,
						new IPathModifierListener() {
							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									sLeaf11.setAlpha(1.0f);
									sLeaf11.setVisible(true);

									break;
								case 4:
									sLeaf11.setVisible(false);
									sLeaf11.setPosition(620, 50);
									break;
								default:
									break;
								}

							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
							}
						}), new AlphaModifier(2.7f, 1.0f, 0.6f))));
		if (sqLeaf11 != null) {
			sqLeaf11.addModifierListener(this);
		}
	}

	private void leaf2Fly() {
		Path path = new Path(5).to(783, 47).to(900, 100).to(650, 230)
				.to(700, 250).to(600, 300);
		sLeaf12.registerEntityModifier(sqLeaf12 = new SequenceEntityModifier(
				new ParallelEntityModifier(new PathModifier(2.50f, path, null,
						new IPathModifierListener() {
							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									sLeaf12.setAlpha(1.0f);
									sLeaf12.setVisible(true);

									break;
								case 4:
									sLeaf12.setVisible(false);
									sLeaf12.setPosition(783, 47);
									break;
								default:
									break;
								}

							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
							}
						}), new AlphaModifier(2.7f, 1.0f, 0.6f))));
		if (sqLeaf12 != null) {
			sqLeaf12.addModifierListener(this);
		}
	}

	private void leaf3Fly() {

		Path path = new Path(5).to(680, 85).to(900, 120).to(600, 180)
				.to(830, 220).to(550, 280);
		sLeaf13.registerEntityModifier(sqLeaf13 = new SequenceEntityModifier(
				new ParallelEntityModifier(new PathModifier(2.50f, path, null,
						new IPathModifierListener() {
							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									sLeaf13.setAlpha(1.0f);
									sLeaf13.setVisible(true);

									break;
								case 4:
									sLeaf13.setVisible(false);
									sLeaf13.setPosition(680, 85);
									break;
								default:
									break;
								}

							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
							}
						}), new AlphaModifier(2.7f, 1.0f, 0.6f))));
		if (sqLeaf13 != null) {
			sqLeaf13.addModifierListener(this);
		}
	}

	private void leaf4Fly() {

		Path path = new Path(5).to(800, 100).to(880, 120).to(600, 150)
				.to(650, 250).to(610, 400);
		sLeaf14.registerEntityModifier(sqLeaf14 = new SequenceEntityModifier(
				new ParallelEntityModifier(new PathModifier(2.50f, path, null,
						new IPathModifierListener() {
							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									sLeaf14.setAlpha(1.0f);
									sLeaf14.setVisible(true);

									break;
								case 4:
									sLeaf14.setVisible(false);
									sLeaf14.setPosition(800, 100);
									break;
								default:
									break;
								}

							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
							}
						}), new AlphaModifier(2.70f, 1.0f, 0.6f))));
		if (sqLeaf14 != null) {
			sqLeaf14.addModifierListener(this);
		}
	}

	private void leaf5Fly() {

		Path path = new Path(5).to(650, 62).to(680, 220).to(600, 270)
				.to(650, 300).to(620, 470);
		sLeaf15.registerEntityModifier(sqLeaf15 = new SequenceEntityModifier(
				new ParallelEntityModifier(new PathModifier(2.50f, path, null,
						new IPathModifierListener() {
							@Override
							public void onPathFinished(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathStarted(PathModifier arg0,
									IEntity arg1) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier arg0, IEntity arg1, int arg2) {
								switch (arg2) {
								case 0:
									sLeaf15.setAlpha(1.0f);
									sLeaf15.setVisible(true);

									break;
								case 4:
									sLeaf15.setVisible(false);
									sLeaf15.setPosition(650, 62);
									break;
								default:
									break;
								}

							}

							@Override
							public void onPathWaypointStarted(
									PathModifier arg0, IEntity arg1, int arg2) {
							}
						}), new AlphaModifier(2.70f, 1.0f, 0.6f))));
		if (sqLeaf15 != null) {
			sqLeaf15.addModifierListener(this);
		}
	}

	private void beeChange() {
		if (sBee.isAnimationRunning()) {
			sBee.stopAnimation();
		}
		switch (flagBeeTo) {
		case 0:
			long pDuration1[] = new long[] { 300, 300 };
			int pFrams1[] = new int[] { 0, 1 };
			sBee.animate(pDuration1, pFrams1, -1);
			break;
		case 1:
			long pDuration2[] = new long[] { 300, 300 };
			int pFrams2[] = new int[] { 4, 5 };
			sBee.animate(pDuration2, pFrams2, -1);
			break;
		case 2:
			long pDuration3[] = new long[] { 300, 300 };
			int pFrams3[] = new int[] { 2, 3 };
			sBee.animate(pDuration3, pFrams3, -1);
			break;
		case 3:
			long pDuration4[] = new long[] { 300, 300 };
			int pFrams4[] = new int[] { 6, 7 };
			sBee.animate(pDuration4, pFrams4, -1);
			break;
		default:
			break;
		}

	}

	private void boyChange() {
		long pDuration[] = new long[] { 400, 200 };
		int pFrams[] = new int[] { 1, 0 };
		sBoy.animate(pDuration, pFrams, 0);
	}

	private void beeHoneyChange() {
		sBeehoney.setVisible(true);
		long pDuration[] = new long[] { 400, 400, 700, 700 };
		int pFrams[] = new int[] { 0, 1, 2, 3 };
		sBeehoney.animate(pDuration, pFrams, 0, this);
	}

	private void beeAngryChange() {
		if (flagBeeAngryTo) {
			long pDuration1[] = new long[] { 400, 400 };
			int pFrams1[] = new int[] { 0, 1 };
			sBeeAngry.animate(pDuration1, pFrams1, -1);
		} else {
			long pDuration1[] = new long[] { 400, 400 };
			int pFrams1[] = new int[] { 2, 3 };
			sBeeAngry.animate(pDuration1, pFrams1, -1);
		}
	}

	private void beeAngryTo() {
		sBeeAngry
				.registerEntityModifier(sqBeeAngryto = new SequenceEntityModifier(
						new MoveModifier(3.0f, sBeeAngry.getX(), 158, sBeeAngry
								.getY(), 500), new DelayModifier(0.7f)));
		if (sqBeeAngryto != null) {
			sqBeeAngryto.addModifierListener(this);
		}
	}

	private void beeAngryHome() {
		sBeeAngry
				.registerEntityModifier(sqBeeAngryhome = new SequenceEntityModifier(
						new MoveModifier(2.0f, sBeeAngry.getX(), 1000,
								sBeeAngry.getY(), 140)

				));
		if (sqBeeAngryhome != null) {
			sqBeeAngryhome.addModifierListener(this);
		}
	}

	private void beesFlyChange() {
		sBees.setVisible(true);
		long pDuration[] = new long[] { 400, 400 };
		int pFrams[] = new int[] { 0, 1 };
		sBees.animate(pDuration, pFrams, -1);
	}

	private void beesFly() {
		sBees.registerEntityModifier(sqBees = new SequenceEntityModifier(
				new MoveXModifier(4.0f, sBees.getX(), -300)));
		if (sqBees != null) {
			sqBees.addModifierListener(this);
		}
	}

	private void beehiveRotation() {
		sBeehive.setRotationCenter(250, 0);
		sBeehive.registerEntityModifier(sqBeehive = new SequenceEntityModifier(
				new RotationModifier(0.6f, 1.0f, -3.0f), new RotationModifier(
						0.6f, -3.0f, 1.0f)));
		if (sqBeehive != null) {
			sqBeehive.addModifierListener(this);
		}
	}

	private void kumaChange(int chose) {
		switch (chose) {
		case 0:
			mp3Kuma1.play();
			long pDuration1[] = new long[] { 300, 300, 300, 300, 300 };
			int pFrams1[] = new int[] { 1, 2, 1, 2, 0 };
			sKuma.animate(pDuration1, pFrams1, 0, this);
			break;
		case 1:
			long pDuration2[] = new long[] { 400, 400 };
			int pFrams2[] = new int[] { 3, 4 };
			sKuma.animate(pDuration2, pFrams2, -1);
			break;
		case 2:
			mp3Kuma2.play();
			long pDuration3[] = new long[] { 1000 };
			int pFrams3[] = new int[] { 5 };
			sKuma.animate(pDuration3, pFrams3, 0);
			break;
		case 3:
			long pDuration4[] = new long[] { 400, 400 };
			int pFrams4[] = new int[] { 6, 7 };
			sKuma.animate(pDuration4, pFrams4, -1);
			break;
		default:
			break;
		}

	}

	private void kumaGotoJar() {
		sKuma.registerEntityModifier(sqKumaGotoJar = new SequenceEntityModifier(
				new MoveModifier(1.2f, sKuma.getX(), 630, sKuma.getY(), sKuma
						.getY() - 10))); // 680, 430
		if (sqKumaGotoJar != null) {
			sqKumaGotoJar.addModifierListener(this);
		}
	}

	private void kumaGotoAway() {
		sKuma.registerEntityModifier(sqKumagotoAway = new SequenceEntityModifier(
				new MoveModifier(2.5f, sKuma.getX(), 980, sKuma.getY(), sKuma
						.getY()))); // 680, 430
		if (sqKumagotoAway != null) {
			sqKumagotoAway.addModifierListener(this);
		}
	}

	private void kaureChange() {
		mp3Kaeru.play();
		long pDuration[] = new long[] { 500, 800, 300 };
		int pFrams[] = new int[] { 0, 1, 0 };
		sKaeru.animate(pDuration, pFrams, 0, this);
	}

	private void hanaModifier(Sprite sprite) {
		sprite.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.3f, sprite.getX(), sprite.getX() + 15.0f),
				new MoveXModifier(0.6f, sprite.getX() + 15,
						sprite.getX() - 15.0f), new MoveXModifier(0.3f, sprite
						.getX() - 15, sprite.getX())));
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isTouchKaure && isTouchGimmic[2]) {
			isTouchGimmic[2] = false;
			isTouchKaure = false;
			kaureChange();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sKaeru, 0, 0, (int) sKaeru.getWidth() + 10,
					(int) sKaeru.getHeight() + 10, pX, pY)) {
				if (isTouchKaure && isTouchGimmic[2]) {
					isTouchGimmic[2] = false;
					isTouchKaure = false;
					kaureChange();
				}
			} else if (checkContains(sKuma, (int) sKuma.getWidth() / 6, 0,
					(int) sKuma.getWidth() * 2 / 3, (int) sKuma.getHeight(),
					pX, pY)) {
				if (isTouchKuma) {
					isTouchKuma = false;

					kumaChange(StateKuma);
					if (StateKuma == 1 || sJar.isVisible()) {
						StateKuma = 1;
						kumaChange(StateKuma);
						kumaGotoJar();
						mp3Kuma1.play();
					}
				}
			} else if (checkContains(sHana1, (int) sHana1.getWidth() / 4, 0,
					(int) sHana1.getWidth() * 3 / 4, (int) sHana1.getHeight(),
					pX, pY)) {
				if (isTouchHana1) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana1);
					if (sBee.getX() <= xHana[0]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[0], yHanave[0]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[0], yHana[0]);
					}
					beeChange();
				}
			} else if (checkContains(sHana2, (int) sHana2.getWidth() / 4, 0,
					(int) sHana2.getWidth() * 3 / 4, (int) sHana2.getHeight(),
					pX, pY)) {
				if (isTouchHana2) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana2);
					if (sBee.getX() <= xHana[1]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[1], yHanave[1]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[1], yHana[1]);
					}
					beeChange();
				}
			} else if (checkContains(sHana3, (int) sHana3.getWidth() / 4, 0,
					(int) sHana3.getWidth() * 3 / 4, (int) sHana3.getHeight(),
					pX, pY)) {
				if (isTouchHana3) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana3);
					if (sBee.getX() <= xHana[2]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[2], yHanave[2]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[2], yHana[2]);
					}
					beeChange();
				}
			} else if (checkContains(sHana4, (int) sHana4.getWidth() / 4, 0,
					(int) sHana4.getWidth() * 3 / 4, (int) sHana4.getHeight(),
					pX, pY)) {
				if (isTouchHana4) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana4);
					if (sBee.getX() <= xHana[3]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[3], yHanave[3]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[3], yHana[3]);
					}
					beeChange();
				}
			} else if (checkContains(sHana5, (int) sHana5.getWidth() / 4, 0,
					(int) sHana5.getWidth() * 3 / 4, (int) sHana5.getHeight(),
					pX, pY)) {
				if (isTouchHana5) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana5);
					if (sBee.getX() <= xHana[4]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[4], yHanave[4]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[4], yHana[4]);
					}
					beeChange();
				}
			} else if (checkContains(sHana32, (int) sHana32.getWidth() / 4, 0,
					(int) sHana32.getWidth() * 3 / 4,
					(int) sHana32.getHeight(), pX, pY)) {
				if (isTouchHana32) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana32);
					if (sBee.getX() <= xHana[5]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[5], yHanave[5]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[5], yHana[5]);
					}
					beeChange();
				}
			} else if (checkContains(sHana6, (int) sHana6.getWidth() / 4, 0,
					(int) sHana6.getWidth() * 3 / 4, (int) sHana6.getHeight(),
					pX, pY)) {
				if (isTouchHana6) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana6);
					if (sBee.getX() <= xHana[6]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[6], yHanave[6]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[6], yHana[6]);
					}
					beeChange();
				}
			} else if (checkContains(sHana52, (int) sHana52.getWidth() / 4, 0,
					(int) sHana52.getWidth() * 3 / 4,
					(int) sHana52.getHeight(), pX, pY)) {
				if (isTouchHana52) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana52);
					if (sBee.getX() <= xHana[7]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[7], yHanave[7]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[7], yHana[7]);
					}
					beeChange();
				}
			} else if (checkContains(sHana7, (int) sHana7.getWidth() / 4, 0,
					(int) sHana7.getWidth() * 3 / 4, (int) sHana7.getHeight(),
					pX, pY)) {
				if (isTouchHana7) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana7);
					if (sBee.getX() <= xHana[8]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[8], yHanave[8]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[8], yHana[8]);
					}
					beeChange();
				}
			} else if (checkContains(sHana8, (int) sHana8.getWidth() / 4, 0,
					(int) sHana8.getWidth() * 3 / 4, (int) sHana8.getHeight(),
					pX, pY)) {
				if (isTouchHana8) {
					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana8);
					if (sBee.getX() <= xHana[9]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[9], yHanave[9]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[9], yHana[9]);
					}
					beeChange();
				}
			} else if (checkContains(sHana9, (int) sHana9.getWidth() / 4, 0,
					(int) sHana9.getWidth() * 3 / 4, (int) sHana9.getHeight(),
					pX, pY)) {
				if (isTouchHana9) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana9);
					if (sBee.getX() <= xHana[10]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[10], yHanave[10]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[10], yHana[10]);
					}
					beeChange();
				}
			} else if (checkContains(sHana21, (int) sHana21.getWidth() / 4, 0,
					(int) sHana21.getWidth() * 3 / 4,
					(int) sHana21.getHeight(), pX, pY)) {
				if (isTouchHana21) {

					setEnable(false);
					mp3_4_9_10_13_3.play();
					hanaModifier(sHana21);
					if (sBee.getX() <= xHana[11]) {
						flagBeeTo = 1;
						beeTogiveHoney(xHanave[11], yHanave[11]);
					} else {
						flagBeeTo = 0;
						beeTogiveHoney(xHana[11], yHana[11]);
					}
					beeChange();
				}
			} else if (checkContains(sLeaf5, 0,
					(int) sLeaf5.getHeight() * 2 / 5,
					(int) sLeaf5.getWidth() * 2 / 5, (int) sLeaf5.getHeight(),
					pX, pY)) {
				if (isTouchLeaf && isTouchTree) {
					mp3TreeLeaf.play();
					leaf1Fly();
					leaf2Fly();
					leaf3Fly();
					leaf4Fly();
					leaf5Fly();
					isTouchLeaf = false;
					isTouchTree = false;
					hanaModifier(sLeaf2);
					hanaModifier(sLeaf3);
					hanaModifier(sLeaf4);
					hanaModifier(sLeaf5);
					delayTree();
				}
			} else if (checkContains(sTree, (int) sTree.getWidth() / 3,
					(int) sTree.getHeight() * 1 / 7, (int) sTree.getWidth(),
					(int) sTree.getHeight() * 6 / 7, pX, pY)) {
				if (isTouchLeaf && isTouchTree) {
					mp3TreeLeaf.play();
					leaf1Fly();
					leaf2Fly();
					leaf3Fly();
					leaf4Fly();
					leaf5Fly();
					isTouchLeaf = false;
					isTouchTree = false;
					hanaModifier(sLeaf2);
					hanaModifier(sLeaf3);
					hanaModifier(sLeaf4);
					hanaModifier(sLeaf5);
					delayTree();
				}
			} else if (checkContains(sBoy, (int) sBoy.getWidth() / 4, 0,
					(int) sBoy.getWidth() * 3 / 4, (int) sBoy.getHeight(), pX,
					pY)) {
				if (isTouchBoy) {
					beeAngryChange();
					isTouchBoy = false;
					flagBeeAngryTo = false;

					mp3Boy1.play();
					mp3Boy2.play();
					beeAngryTo();
				}
			} else if (checkContains(sBeehive, (int) sBeehive.getWidth() / 4,
					0, (int) sBeehive.getWidth() * 3 / 4,
					(int) sBeehive.getHeight(), pX, pY)) {
				if (isTouchBehive) {
					mp3Beehive1.play();
					isTouchBehive = false;
					beehiveRotation();
				}
			} else if (checkContains(sBee, (int) sBee.getWidth() / 4, 0,
					(int) sBee.getWidth() * 3 / 4, (int) sBee.getHeight(), pX,
					pY)) {
				if (isTouchBee) {
					mp3_4_9_10_13_1.play();
					isTouchBee = false;
					sBee.registerEntityModifier(new DelayModifier(1.3f,
							new IEntityModifierListener() {
								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> arg0, IEntity arg1) {
									isTouchBee = true;
								}
							}));
				}
			}
			return true;
		}
		return false;
	}

	private void delayTree() {
		sLeaf5.registerEntityModifier(new DelayModifier(3.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						isTouchLeaf = true;
						isTouchTree = true;
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}
				}));
	}

	private void setEnable(boolean b) {
		isTouchHana1 = b;
		isTouchHana2 = b;
		isTouchHana3 = b;
		isTouchHana4 = b;
		isTouchHana5 = b;
		isTouchHana32 = b;
		isTouchHana6 = b;
		isTouchHana52 = b;
		isTouchHana7 = b;
		isTouchHana8 = b;
		isTouchHana9 = b;
		isTouchHana21 = b;
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(sqBees)) {
			isTouchBehive = true;
			sBees.setVisible(false);
			sBees.setPosition(480, 130);
		} else if (pModifier.equals(sqBeehive)) {
			mp3Beehive2.play();
			beesFly();
			beesFlyChange();
		} else if (pModifier.equals(sqBeeAngryto)) {
			mp3Boy3.play();
			mp3Boy4.play();
			beeAngryChange();
			beeAngryHome();
			boyChange();
		} else if (pModifier.equals(sqBeeAngryhome)) {
			isTouchBoy = true;
			flagBeeAngryTo = true;
		} else if (pModifier.equals(sqLeaf11)) {
			sLeaf11.setVisible(false);
			sLeaf11.setPosition(620, 50);
		} else if (pModifier.equals(sqLeaf12)) {
			sLeaf12.setVisible(false);
			sLeaf12.setPosition(783, 47);
		} else if (pModifier.equals(sqLeaf13)) {
			sLeaf13.setVisible(false);
			sLeaf13.setPosition(680, 85);
		} else if (pModifier.equals(sqLeaf14)) {
			sLeaf14.setVisible(false);
			sLeaf14.setPosition(800, 100);
		} else if (pModifier.equals(sqLeaf15)) {
			sLeaf15.setVisible(false);
			sLeaf15.setPosition(650, 62);
		} else if (pModifier.equals(sqBeeGiveHoney)) {
			mp3_4_9_10_13_2.play();
			if (flagBeeTo == 1) {
				sBee.stopAnimation();
				sBee.setCurrentTileIndex(6);
				sBee.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								flagBeeTo = 3;
								beeChange();
								isTouchBehive = false;
								beeToBringHoney();
							}
						}));
			} else {
				flagBeeTo = 3;
				sBee.stopAnimation();
				sBee.setCurrentTileIndex(2);
				sBee.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								beeChange();
								isTouchBehive = false;
								beeToBringHoney();
							}
						}));
			}

		} else if (pModifier.equals(sqBeeBringHoney)) {
			amountHoney++;
			mp3_4_9_10_13_4.play();
			sBeehive.setCurrentTileIndex(amountHoney);

			if (amountHoney == 3) {
				amountHoney = 0;
				beeHoneyChange();
			}
			setEnable(true);
			flagBeeTo = 0;
			beeChange();
			beeTo();
			isTouchBehive = true;
		} else if (pModifier.equals(sqKumaGotoJar)) {
			StateKuma = 2;
			sJar.setVisible(false);
			kumaChange(StateKuma);
			sJar.registerEntityModifier(new DelayModifier(1.0f,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							StateKuma = 3;
							kumaChange(StateKuma);
							kumaGotoAway();
						}
					}));
		} else if (pModifier.equals(sqKumagotoAway)) {
			StateKuma = 0;
			isTouchKuma = true;
			sKuma.stopAnimation();
			sKuma.setCurrentTileIndex(0);
			sKuma.setPosition(745, 277);
		}

	}

	@Override
	protected void loadKaraokeSound() {
		mp3Kaeru = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_03_06_GEROGEROPOTYAN);
		mp3Beehive1 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_05_KASA);
		mp3Beehive2 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_05_BUN);
		mp3Kuma1 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_07_PYON2);
		mp3Kuma2 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_07_OISHI);
		mp3Boy1 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_05_HACHIDA);
		mp3Boy2 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_08_BUN);
		mp3Boy3 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_08_CHIC);
		mp3Boy4 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_08_ITAI);
		mp3TreeLeaf = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_12_KASA2);
		mp3_4_9_10_13_1 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_04_HACHIMITSU);
		mp3_4_9_10_13_2 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_04_POYO);
		mp3_4_9_10_13_3 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_04_SYU2);
		mp3_4_9_10_13_4 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_09_MOKIN2);
		mp3_4_9_10_13_5 = loadSoundResourceFromSD(Vol3BunbunbunResource.A20_10_KIRA10);

	}

	private double calcuDistance(float x1, float y1, float x2, float y2) {
		return Math
				.sqrt((double) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
	}

	private void inital() {
		isTouchKaure = true;
		isTouchGimmic[2] = true;
		isTouchKuma = true;
		isTouchLeaf = true;
		isTouchTree = true;
		isTouchBoy = true;
		isTouchBehive = true;
		isTouchBee = true;
		flagBeeAngryTo = true;
		flagBeeTo = 0;
		amountHoney = 0;
		StateKuma = 0;
	}

	@Override
	public void onPauseGame() {
		resetData();
		super.onPauseGame();
	}

	public void resetData() {
		if (sKaeru != null) {
			sKaeru.stopAnimation();
			sKaeru.clearEntityModifiers();
			sKaeru.setCurrentTileIndex(0);
		}
		if (sBoy != null) {
			sBoy.stopAnimation();
			sBoy.setCurrentTileIndex(0);
		}
		if (sBees != null) {
			sBees.clearEntityModifiers();
			sBees.stopAnimation();
			sBees.setVisible(false);
			sBees.setPosition(480, 130);
			sBee.setCurrentTileIndex(0);
		}
		if (sBeehive != null) {
			sBeehive.clearEntityModifiers();
			sBeehive.setCurrentTileIndex(0);
		}
		if (sBeehoney != null) {
			sBeehoney.stopAnimation();
			sBeehoney.setVisible(false);
			sBeehoney.setCurrentTileIndex(0);

		}
		if (sBeeAngry != null) {
			sBeeAngry.clearEntityModifiers();
			sBeeAngry.stopAnimation();
			sBeeAngry.setPosition(1000, 140);
			sBeeAngry.setCurrentTileIndex(0);
		}
		if (sBee != null) {
			sBee.clearEntityModifiers();
			sBee.stopAnimation();
			sBee.setPosition(665, 100);
			sBee.setCurrentTileIndex(0);
		}
		if (sLeaf11 != null) {
			sLeaf11.clearEntityModifiers();
			sLeaf11.setVisible(false);
			sLeaf11.setPosition(620, 50);
		}
		if (sLeaf15 != null) {
			sLeaf15.clearEntityModifiers();
			sLeaf15.setVisible(false);
			sLeaf15.setPosition(650, 62);
		}
		if (sLeaf12 != null) {
			sLeaf12.clearEntityModifiers();
			sLeaf12.setVisible(false);
			sLeaf12.setPosition(783, 47);
		}
		if (sLeaf13 != null) {
			sLeaf13.clearEntityModifiers();
			sLeaf13.setVisible(false);
			sLeaf13.setPosition(680, 85);
		}
		if (sLeaf14 != null) {
			sLeaf14.clearEntityModifiers();
			sLeaf14.setVisible(false);
			sLeaf14.setPosition(800, 100);
		}
		if (sKuma != null) {
			sKuma.stopAnimation();
			sKuma.clearEntityModifiers();
			sKuma.setCurrentTileIndex(0);
			sKuma.setPosition(745, 277);
		}
		if (sLeaf2 != null) {
			sLeaf2.clearEntityModifiers();
			sLeaf2.setPosition(471, 40);
		}
		if (sLeaf3 != null) {
			sLeaf3.clearEntityModifiers();
			sLeaf3.setPosition(646, 95);
		}
		if (sLeaf4 != null) {
			sLeaf4.clearEntityModifiers();
			sLeaf4.setPosition(864, 17);
		}
		if (sLeaf5 != null) {
			sLeaf5.clearEntityModifiers();
			sLeaf5.setPosition(453, -139);
		}
		if (sHana1 != null) {
			sHana1.clearEntityModifiers();
			sHana1.setPosition(41, 210);
		}
		if (sHana2 != null) {
			sHana2.clearEntityModifiers();
			sHana2.setPosition(116, 197);
		}
		if (sHana3 != null) {
			sHana3.clearEntityModifiers();
			sHana3.setPosition(174, 230);
		}
		if (sHana4 != null) {
			sHana4.clearEntityModifiers();
			sHana4.setPosition(270, 229);
		}
		if (sHana5 != null) {
			sHana5.clearEntityModifiers();
			sHana5.setPosition(356, 207);
		}
		if (sHana6 != null) {
			sHana6.clearEntityModifiers();
			sHana6.setPosition(492, 219);
		}
		if (sHana7 != null) {
			sHana7.clearEntityModifiers();
			sHana7.setPosition(318, 439);
		}
		if (sHana8 != null) {
			sHana8.clearEntityModifiers();
			sHana8.setPosition(379, 430);
		}
		if (sHana9 != null) {
			sHana9.clearEntityModifiers();
			sHana9.setPosition(468, 419);
		}
		if (sHana32 != null) {
			sHana32.clearEntityModifiers();
			sHana32.setPosition(397, 214);
		}
		if (sHana52 != null) {
			sHana52.clearEntityModifiers();
			sHana52.setPosition(226, 429);
		}
		if (sHana21 != null) {
			sHana21.clearEntityModifiers();
			sHana21.setPosition(529, 407);
		}
		if (sJar != null) {
			sJar.clearEntityModifiers();
		}
		if (sLeaf5 != null) {
			sLeaf5.clearEntityModifiers();
		}
		return;
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		inital();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				beeTo();
				setEnable(true);
			}
		}, 500);

		if (sJar != null) {
			sJar.setVisible(false);
		}
		if (sBeehoney != null) {
			sBeehoney.setCurrentTileIndex(0);
			sBeehoney.setVisible(false);
		}
		if (sBeehive != null) {
			sBeehive.setCurrentTileIndex(0);
		}
		super.onResumeGame();
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite == sKaeru) {
			sKaeru.registerEntityModifier(new DelayModifier(0.3f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTouchGimmic[2] = true;
							isTouchKaure = true;
						}
					}));
		} else if (pAnimatedSprite == sKuma) {
			isTouchKuma = true;
		} else if (pAnimatedSprite == sBeehoney) {
			StateKuma = 1;
			mp3_4_9_10_13_5.play();
			sJar.setVisible(true);
			sBeehoney.setVisible(false);
			sBeehive.setCurrentTileIndex(0);
			sBeehoney.setCurrentTileIndex(0);
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
