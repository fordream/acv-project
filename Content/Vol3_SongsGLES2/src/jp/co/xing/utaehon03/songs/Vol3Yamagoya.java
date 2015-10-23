package jp.co.xing.utaehon03.songs;

import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YamagoyaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class Vol3Yamagoya extends BaseGameFragment implements
		IAccelerationListener, IOnAreaTouchListener, IOnSceneTouchListener,
		IEntityModifierListener {

	private String TAG_LOG = Vol3Yamagoya.this.getClass().toString();
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion TextureRegionBackground1, TextureRegionBackground2,
			TextureRegionBackground3A, TextureRegionBackground3B,
			TextureRegionKemuriA, TextureRegionKemuriB,
			TextureRegionObjectHouse, TextureRegionObjectUsagiA,
			TextureRegionObjectUsagiB, TextureRegionObjectUsagiC,
			TextureRegionObjectUsagiD, TextureRegionObjectUsagiE,
			TextureRegionObjectUsagiF, TextureRegionObjectSnorkel,
			TextureRegionObjectDoor1, TextureRegionObjectDoor2,
			TextureRegionObjectDoor3, TextureRegionCircles;

	private Sprite SpriteBackground1, SpriteBackground2, SpriteBackground3A,
			SpriteBackground3B, SpriteHouse, SpriteSnorkel, SpriteDoor1,
			SpriteDoor2, SpriteDoor3, SpriteUsagiA, SpriteUsagiB, SpriteUsagiC,
			SpriteUsagiD, SpriteUsagiE, SpriteUsagiF, SpriteKemuriA,
			SpriteKemuriB;
	private AnimatedSprite AnimatedJisan, AnimatedJisanCopy,
			AnimatedYoushiGreen, AnimatedYoushiBrown, AnimatedUsagiA,
			AnimatedUsagiB, AnimatedUsagiC, AnimatedUsagiD, AnimatedUsagiE,
			AnimatedUsagiF, AnimatedKamihubuki;
	private TiledTextureRegion TiledTextureRegionJisan,
			TiledTextureRegionYoushiGreen, TiledTextureRegionYoushiBrown,
			TiledTextureRegionKamihubuki, TiledTextureRegionUsagiA,
			TiledTextureRegionUsagiB, TiledTextureRegionUsagiC,
			TiledTextureRegionUsagiD, TiledTextureRegionUsagiE,
			TiledTextureRegionUsagiF;
	private int IndexYoushiGreen = 0, IndexYoushiBrown = 0;
	private boolean TouchYoushiGreen = false, TouchYoushiBrown = false;
	private float tempSpeedYoushi = 20f;
	private float speedOneYoushiGreen = 0, speedTwoYoushiGreen = 0,
			speedThreeYoushiGreen = 0, speedFourYoushiGreen = 0;
	private float speedOneYoushiBrown = 0, speedTwoYoushiBrown = 0,
			speedThreeYoushiBrown = 0, speedFourYoushiBrown = 0;
	private Sprite circleObstacleA, circleObstacleB, circleObstacleC,
			circleObstacleD, circleObstacleE, circleObstacleF,
			circleObstacleAll;
	// Array TeturePixelPerfect
	private BitmapTextureAtlas mLayoutTexture = null;
	private ITextureRegion mLayoutTextureRegion = null;
	// private Sprite [] mLayoutSprite;
	private MapSprite MapSpritePool = null;
	private LinkedList<Sprite> mapLink = null;

	private Body postBodiesA = null, postBodiesB = null, postBodiesC = null,
			postBodiesD = null, postBodiesE = null, postBodiesF = null;
	private Body groundBodiesA = null, groundBodiesB = null,
			groundBodiesC = null, groundBodiesD = null, groundBodiesE = null,
			groundBodiesF = null;
	private Vector2 initLocsA = null, initLocsB = null, initLocsC = null,
			initLocsD = null, initLocsE = null, initLocsF = null;
	private MouseJoint mouseJointsA, mouseJointsB = null, mouseJointsC = null,
			mouseJointsD = null, mouseJointsE = null, mouseJointsF = null;
	private BodyDef bDef = null;
	private String TAG = "YAMAGOYA";
	private boolean isTouchCicleA = false, isTouchCicleB = false,
			isTouchCicleC = false, isTouchCicleD = false,
			isTouchCicleE = false, isTouchCicleF = false;
	private short iMAP_G = 1;
	private int iA_G = 2, iB_G = 4, iC_G = 8, iD_G = 16, iE_G = 32, iF_G = 64;
	private int iMAP_B = iMAP_G + iA_G + iB_G + iC_G + iD_G + iE_G + iF_G;
	private int iA_B = iMAP_G + iA_G;
	private int iB_B = iMAP_G + iB_G;
	private int iC_B = iMAP_G + iC_G;
	private int iD_B = iMAP_G + iD_G;
	private int iE_B = iMAP_G + iE_G;
	private int iF_B = iMAP_G + iF_G;

	private Vector2 localPoint = null;
	private Vector2 touchVecDown = null;
	private Vector2 touchVecMove = null;
	private int beginOne = 1;
	private int beginTwo = 1;
	private FixtureDef fixA, fixB, fixC, fixD, fixE, fixF, wallFixtureDef;
	private int[][] randomMapPoint;
	private int[][] randomPositionPoint;
	private Random randomMap, randomPosition;
	private int rMapPoint = 0, rPosition = 0;

	private PhysicsWorld mPhysicsWorld;
	private PhysicsConnector phyC_A = null, phyC_B = null, phyC_C = null,
			phyC_D = null, phyC_E = null, phyC_F = null;
	private int countdownObject = 6;
	private boolean isTouchJisanCopy = false;
	private boolean isMoveA = false, isMoveB = false, isMoveC = false,
			isMoveD = false, isMoveE = false, isMoveF = false;
	private boolean isTouchSnorkel = false;
	private boolean isTouchGimic = false;
	private TimerHandler tHandlerA, tHandlerB, tHandlerC, tHandlerD, tHandlerE,
			tHandlerF;

	private TexturePack Vol3YamagoyaPacker_1, Vol3YamagoyaPacker_2,
			Vol3YamagoyaPacker_3;
	private TexturePackTextureRegionLibrary Vol3YamagoyaLibrary1,
			Vol3YamagoyaLibrary2, Vol3YamagoyaLibrary3;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("yamagoya/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("yamagoya/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "yamagoya/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		Vol3YamagoyaPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3YamagoyaPacker1.xml");
		Vol3YamagoyaPacker_1.loadTexture();
		Vol3YamagoyaLibrary1 = Vol3YamagoyaPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3YamagoyaPacker_2 = mTexturePackLoaderFromSource
				.load("Vol3YamagoyaPacker2.xml");
		Vol3YamagoyaPacker_2.loadTexture();
		Vol3YamagoyaLibrary2 = Vol3YamagoyaPacker_2
				.getTexturePackTextureRegionLibrary();

		Vol3YamagoyaPacker_3 = mTexturePackLoaderFromSource
				.load("Vol3YamagoyaPacker3.xml");
		Vol3YamagoyaPacker_3.loadTexture();
		Vol3YamagoyaLibrary3 = Vol3YamagoyaPacker_3
				.getTexturePackTextureRegionLibrary();

		TextureRegionBackground1 = Vol3YamagoyaLibrary3
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker3.A18_10_1_IPHONE_HAIKEI_ID);
		TextureRegionBackground2 = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_10_2_IPHONE_HAIKEI_ID);
		TextureRegionBackground3A = Vol3YamagoyaLibrary3
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker3.A18_09_1_IPHONE_MEIRO_ID);
		TextureRegionBackground3B = Vol3YamagoyaLibrary3
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker3.A18_09_2_IPHONE_MEIRO_ID);

		TextureRegionKemuriA = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_12_IPHONE_KEMURI_ID);

		TextureRegionKemuriB = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_13_IPHONE_KEMURI_ID);
		// Kamihubuki
		TiledTextureRegionKamihubuki = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_1,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_19_IPHONE_KAMIHUBUKI_ID);
		// Object House
		TextureRegionObjectHouse = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_2_IPHONE_YAMAGOYA_ID);
		TextureRegionObjectSnorkel = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_1_IPHONE_YAMAGOYA_ID);
		TextureRegionObjectDoor1 = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_3_IPHONE_YAMAGOYA_ID);
		TextureRegionObjectDoor2 = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_4_IPHONE_YAMAGOYA_ID);
		TextureRegionObjectDoor3 = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_5_IPHONE_YAMAGOYA_ID);
		// Object Usagi Hidden A, B, C, D, E, F
		TextureRegionObjectUsagiA = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_6_IPHONE_USAGIA_ID);
		TextureRegionObjectUsagiB = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_7_IPHONE_USAGIB_ID);
		TextureRegionObjectUsagiC = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_8_IPHONE_USAGIC_ID);
		TextureRegionObjectUsagiD = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_9_IPHONE_USAGID_ID);
		TextureRegionObjectUsagiE = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_10_IPHONE_USAGIE_ID);
		TextureRegionObjectUsagiF = Vol3YamagoyaLibrary2
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_07_11_IPHONE_USAGEF_ID);
		// Usagi A,B,C,D,E,F
		TiledTextureRegionUsagiA = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8A_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8A_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8A_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8A_4_IPHONE_USAGI_ID);

		TiledTextureRegionUsagiB = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8B_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8B_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8B_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8B_4_IPHONE_USAGI_ID);

		TiledTextureRegionUsagiC = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8C_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8C_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8C_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8C_4_IPHONE_USAGI_ID);

		TiledTextureRegionUsagiD = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8D_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8D_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8D_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8D_4_IPHONE_USAGI_ID);

		TiledTextureRegionUsagiE = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8E_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8E_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8E_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8E_4_IPHONE_USAGI_ID);

		TiledTextureRegionUsagiF = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_2,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8F_1_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8F_2_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8F_3_IPHONE_USAGI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker2.A18_8F_4_IPHONE_USAGI_ID);
		// Jisan
		TiledTextureRegionJisan = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_1,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_1_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_2_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_3_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_4_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_5_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_6_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_7_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_8_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_9_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_10_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_11_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_12_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_13_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_14_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_15_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_16_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_17_IPHONE_JISAN_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_04_18_IPHONE_JISAN_ID);
		// Youshi Green
		TiledTextureRegionYoushiGreen = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_1,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_06_1_IPHONE_RYOUSHI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_06_2_IPHONE_RYOUSHI_ID);
		// Youshi Brown
		TiledTextureRegionYoushiBrown = getTiledTextureFromPacker(
				Vol3YamagoyaPacker_1,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_05_1_IPHONE_RYOUSHI_ID,
				Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_05_2_IPHONE_RYOUSHI_ID);
		TextureRegionCircles = Vol3YamagoyaLibrary1
				.get(Vol3YamagoyaResource.Vol3YamagoyaPacker1.A18_CIRLE_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		randomMap = new Random();
		randomPosition = new Random();
		mapLink = new LinkedList<Sprite>();
		// Set Map Point
		randomMapPointObject();
		// Set Position Point
		randomPositions();
		SpriteBackground1 = new Sprite(0, 0, TextureRegionBackground1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteBackground1);
		SpriteBackground3A = new Sprite(0, 0, TextureRegionBackground3A,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteBackground3A);
		SpriteBackground3A.setVisible(false);
		SpriteBackground3B = new Sprite(0, 0, TextureRegionBackground3B,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteBackground3B);
		SpriteBackground3B.setVisible(false);
		// Set show map random
		setRandomMap();
		SpriteBackground2 = new Sprite(0, 0, TextureRegionBackground2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteBackground2);
		// Youshi
		AnimatedYoushiBrown = new AnimatedSprite(710f, 501f,
				TiledTextureRegionYoushiBrown,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(AnimatedYoushiBrown);
		AnimatedYoushiGreen = new AnimatedSprite(168f, 53f,
				TiledTextureRegionYoushiGreen,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(AnimatedYoushiGreen);
		// KamihubukiObject
		AnimatedKamihubuki = new AnimatedSprite(310, 1,
				TiledTextureRegionKamihubuki,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(AnimatedKamihubuki);
		AnimatedKamihubuki.setVisible(false);
		// Kemuri
		SpriteKemuriA = new Sprite(541, 30, TextureRegionKemuriA,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteKemuriA);
		SpriteKemuriA.setVisible(false);
		SpriteKemuriB = new Sprite(600, 6, TextureRegionKemuriB,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteKemuriB);
		SpriteKemuriB.setVisible(false);
		// UsagiObject
		SpriteUsagiA = new Sprite(349, 1, TextureRegionObjectUsagiA,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiA);
		SpriteUsagiA.setVisible(false);
		SpriteUsagiD = new Sprite(349, 1, TextureRegionObjectUsagiD,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiD);
		SpriteUsagiD.setVisible(false);
		SpriteUsagiF = new Sprite(349, 1, TextureRegionObjectUsagiF,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiF);
		SpriteUsagiF.setVisible(false);
		// Object House
		SpriteSnorkel = new Sprite(349, 1, TextureRegionObjectSnorkel,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteSnorkel);
		SpriteUsagiB = new Sprite(349, 1, TextureRegionObjectUsagiB,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiB);
		SpriteUsagiB.setVisible(false);
		SpriteUsagiC = new Sprite(349, 1, TextureRegionObjectUsagiC,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiC);
		SpriteUsagiC.setVisible(false);
		SpriteHouse = new Sprite(349, 1, TextureRegionObjectHouse,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteHouse);
		SpriteDoor1 = new Sprite(349, 1, TextureRegionObjectDoor1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteDoor1);
		SpriteDoor1.setVisible(false);
		SpriteDoor2 = new Sprite(349, 1, TextureRegionObjectDoor2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteDoor2);
		// SpriteDoor2.setVisible(false);
		SpriteDoor3 = new Sprite(349, 1, TextureRegionObjectDoor3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteDoor3);
		SpriteDoor3.setVisible(false);
		SpriteUsagiE = new Sprite(349, 1, TextureRegionObjectUsagiE,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(SpriteUsagiE);
		SpriteUsagiE.setVisible(false);
		// Jisan
		AnimatedJisan = new AnimatedSprite(349, 1, TiledTextureRegionJisan,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(AnimatedJisan);
		AnimatedJisan.setVisible(false);
		AnimatedJisanCopy = new AnimatedSprite(349, 1, TiledTextureRegionJisan,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(AnimatedJisanCopy);
		AnimatedJisanCopy.setVisible(false);
		AnimatedJisanCopy.setCurrentTileIndex(0);
		// Add Gimmic buttons
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		this.mScene.setOnAreaTouchListener(this);
		// Create physics
		mPhysicsWorld = new PhysicsWorld(new Vector2(0,
				SensorManager.GRAVITY_EARTH), false);
		// Create sprites
		bDef = new BodyDef();
		circleObstacleAll = circlesObject(circleObstacleAll,
				CAMERA_WIDTH / 2 - 40, CAMERA_HEIGHT / 2 - 100);
		circleObstacleAll.setScale(3);
		mScene.attachChild(circleObstacleAll);
		// Animation UsagiA
		circleObstacleA = circlesObject(circleObstacleA,
				randomPositionPoint[0][0], randomPositionPoint[0][1]);
		AnimatedUsagiA = new AnimatedSprite(850, 240, TiledTextureRegionUsagiA,
				this.getVertexBufferObjectManager());
		// Animation UsagiB
		circleObstacleB = circlesObject(circleObstacleB,
				randomPositionPoint[1][0], randomPositionPoint[1][1]);
		AnimatedUsagiB = new AnimatedSprite(850, 515, TiledTextureRegionUsagiB,
				this.getVertexBufferObjectManager());
		// Animation UsagiC
		circleObstacleC = circlesObject(circleObstacleC,
				randomPositionPoint[2][0], randomPositionPoint[2][1]);
		AnimatedUsagiC = new AnimatedSprite(880, 365, TiledTextureRegionUsagiC,
				this.getVertexBufferObjectManager());
		// Animation UsagiD
		circleObstacleD = circlesObject(circleObstacleD,
				randomPositionPoint[3][0], randomPositionPoint[3][1]);
		AnimatedUsagiD = new AnimatedSprite(140, 480, TiledTextureRegionUsagiD,
				this.getVertexBufferObjectManager());
		// Animation UsagiE
		circleObstacleE = circlesObject(circleObstacleE,
				randomPositionPoint[4][0], randomPositionPoint[4][1]);
		AnimatedUsagiE = new AnimatedSprite(125, 360, TiledTextureRegionUsagiE,
				this.getVertexBufferObjectManager());
		// Animation UsagiF
		circleObstacleF = circlesObject(circleObstacleF,
				randomPositionPoint[5][0], randomPositionPoint[5][1]);
		AnimatedUsagiF = new AnimatedSprite(105, 225, TiledTextureRegionUsagiF,
				this.getVertexBufferObjectManager());
		// Create bodies
		fixA = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iA_G,
				(short) iA_B, (short) 0);
		fixB = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iB_G,
				(short) iB_B, (short) 0);
		fixC = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iC_G,
				(short) iC_B, (short) 0);
		fixD = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iD_G,
				(short) iD_B, (short) 0);
		fixE = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iE_G,
				(short) iE_B, (short) 0);
		fixF = PhysicsFactory.createFixtureDef(0, 0, 0, false, (short) iF_G,
				(short) iF_B, (short) 0);
		// Body UsagiA
		postBodiesA = createUsagiBody(mPhysicsWorld, circleObstacleA,
				BodyType.DynamicBody, fixA);
		circleObstacleA.setUserData(postBodiesA);
		groundBodiesA = mPhysicsWorld.createBody(bDef);
		// Body UsagiB
		postBodiesB = createUsagiBody(mPhysicsWorld, circleObstacleB,
				BodyType.DynamicBody, fixB);
		circleObstacleB.setUserData(postBodiesB);
		groundBodiesB = mPhysicsWorld.createBody(bDef);
		// Body UsagiC
		postBodiesC = createUsagiBody(mPhysicsWorld, circleObstacleC,
				BodyType.DynamicBody, fixC);
		circleObstacleC.setUserData(postBodiesC);
		groundBodiesC = mPhysicsWorld.createBody(bDef);
		// Body UsagiD
		postBodiesD = createUsagiBody(mPhysicsWorld, circleObstacleD,
				BodyType.DynamicBody, fixD);
		circleObstacleD.setUserData(postBodiesD);
		groundBodiesD = mPhysicsWorld.createBody(bDef);
		// Body UsagiE
		postBodiesE = createUsagiBody(mPhysicsWorld, circleObstacleE,
				BodyType.DynamicBody, fixE);
		circleObstacleE.setUserData(postBodiesE);
		groundBodiesE = mPhysicsWorld.createBody(bDef);
		// Body UsagiF
		postBodiesF = createUsagiBody(mPhysicsWorld, circleObstacleF,
				BodyType.DynamicBody, fixF);
		circleObstacleF.setUserData(postBodiesF);
		groundBodiesF = mPhysicsWorld.createBody(bDef);
		mLayoutTexture = new BitmapTextureAtlas(this.getTextureManager(), 2, 2,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mLayoutTextureRegion = new TextureRegion(mLayoutTexture, 0, 0, 0, 0);
		MapSpritePool = new MapSprite(mLayoutTextureRegion);
		// Create walls
		wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, false,
				(short) iMAP_G, (short) iMAP_B, (short) 0);
		// Register Physic UsagiA
		this.mScene.attachChild(AnimatedUsagiA);
		mScene.attachChild(circleObstacleA);
		phyC_A = new PhysicsConnector(circleObstacleA, postBodiesA, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_A);
		// Register Physic UsagiB
		this.mScene.attachChild(AnimatedUsagiB);
		mScene.attachChild(circleObstacleB);
		phyC_B = new PhysicsConnector(circleObstacleB, postBodiesB, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_B);
		// Register Physic UsagiC
		this.mScene.attachChild(AnimatedUsagiC);
		mScene.attachChild(circleObstacleC);
		phyC_C = new PhysicsConnector(circleObstacleC, postBodiesC, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_C);
		// Register Physic UsagiD
		this.mScene.attachChild(AnimatedUsagiD);
		mScene.attachChild(circleObstacleD);
		phyC_D = new PhysicsConnector(circleObstacleD, postBodiesD, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_D);
		// Register Physic UsagiE
		this.mScene.attachChild(AnimatedUsagiE);
		mScene.attachChild(circleObstacleE);
		phyC_E = new PhysicsConnector(circleObstacleE, postBodiesE, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_E);
		// Register Physic UsagiF
		this.mScene.attachChild(AnimatedUsagiF);
		mScene.attachChild(circleObstacleF);
		phyC_F = new PhysicsConnector(circleObstacleF, postBodiesF, true, false);
		mPhysicsWorld.registerPhysicsConnector(phyC_F);
		// Register Update Handler for PhysicsWorld
		addGimmicsButton(this.mScene, Vol3YamagoyaResource.SOUND_GIMMIC,
				Vol3YamagoyaResource.IMAGE_GIMMIC, Vol3YamagoyaLibrary2);
		mScene.registerUpdateHandler(mPhysicsWorld);
	}

	private Sprite circlesObject(Sprite ellipses, final float pX, final float pY) {
		ellipses = new Sprite(pX, pY, TextureRegionCircles,
				this.getVertexBufferObjectManager());
		ellipses.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		ellipses.setScale(1.5f);
		ellipses.setAlpha(0);
		return ellipses;
	}

	private void randomMapPointObject() {
		try {
			rMapPoint = randomMap.nextInt(2);
		} catch (Exception e) {
			rMapPoint = 0;
		}

		if (rMapPoint == 0) {
			randomMapPoint = Vol3YamagoyaResource.MapOne.MapPoint1;
		} else {
			randomMapPoint = Vol3YamagoyaResource.MapTwo.MapPoint2;
		}
	}

	private void randomPositions() {
		try {
			rPosition = randomPosition.nextInt(6);
		} catch (Exception e) {
			rPosition = 0;
		}
		switch (rPosition) {
		case 0:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint1;
			break;
		case 1:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint2;
			break;
		case 2:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint3;
			break;
		case 3:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint4;
			break;
		case 4:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint5;
			break;
		case 5:
			randomPositionPoint = Vol3YamagoyaResource.positionPoint6;
			break;
		}
	}

	private void setUpUpdateHandler(final IAreaShape circleObstacles,
			final AnimatedSprite anis) {
		// RegisterUpdate Handler for Animated UsagiA
		anis.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				anis.setPosition(
						(circleObstacles.getX() - anis.getWidth() / 2) + 35,
						(circleObstacles.getY() - anis.getHeight() / 2) - 15);
				if (isMoveA) {
					AnimatedUsagiA.setPosition(400, 200);
				}
				if (isMoveB) {
					AnimatedUsagiB.setPosition(400, 200);
				}
				if (isMoveF) {
					AnimatedUsagiF.setPosition(400, 200);
				}
				if (isMoveC) {
					AnimatedUsagiC.setPosition(400, 200);
				}
				if (isMoveD) {
					AnimatedUsagiD.setPosition(400, 200);
				}
				if (isMoveE) {
					AnimatedUsagiE.setPosition(400, 200);
				}
			}
		});
	}

	private void globalMouseJoin() {
		// Set up global mouse joints Usagi A
		if (initLocsA == null) {
			initLocsA = new Vector2(postBodiesA.getWorldPoint(postBodiesA
					.getLocalCenter()));
		} else {
			initLocsA.set(postBodiesA.getWorldPoint(postBodiesA
					.getLocalCenter()));
		}
		mouseJointsA = createMouseJoint(circleObstacleA, initLocsA.x,
				initLocsA.y, 1);
		if (mouseJointsA == null) {
			this.mEngine.vibrate(100);
		}
		// RegisterUpdate Handler for Animated UsagiA
		setUpUpdateHandler(circleObstacleA, AnimatedUsagiA);
		// Set up global mouse joints Usagi B
		if (initLocsB == null) {
			initLocsB = new Vector2(postBodiesB.getWorldPoint(postBodiesB
					.getLocalCenter()));
		} else {
			initLocsB.set(postBodiesB.getWorldPoint(postBodiesB
					.getLocalCenter()));
		}
		mouseJointsB = createMouseJoint(circleObstacleB, initLocsB.x,
				initLocsB.y, 2);
		// RegisterUpdate Handler for Animated UsagiB
		setUpUpdateHandler(circleObstacleB, AnimatedUsagiB);
		// Set up global mouse joints Usagi C
		if (initLocsC == null) {
			initLocsC = new Vector2(postBodiesC.getWorldPoint(postBodiesC
					.getLocalCenter()));
		} else {
			initLocsC.set(postBodiesC.getWorldPoint(postBodiesC
					.getLocalCenter()));
		}
		mouseJointsC = createMouseJoint(circleObstacleC, initLocsC.x,
				initLocsC.y, 3);
		// RegisterUpdate Handler for Animated UsagiB
		setUpUpdateHandler(circleObstacleC, AnimatedUsagiC);
		// Set up global mouse joints Usagi D
		if (initLocsD == null) {
			initLocsD = new Vector2(postBodiesD.getWorldPoint(postBodiesD
					.getLocalCenter()));
		} else {
			initLocsD.set(postBodiesD.getWorldPoint(postBodiesD
					.getLocalCenter()));
		}
		mouseJointsD = createMouseJoint(circleObstacleD, initLocsD.x,
				initLocsD.y, 4);
		// RegisterUpdate Handler for Animated UsagiB
		setUpUpdateHandler(circleObstacleD, AnimatedUsagiD);
		// Set up global mouse joints Usagi E
		if (initLocsE == null) {
			initLocsE = new Vector2(postBodiesE.getWorldPoint(postBodiesE
					.getLocalCenter()));
		} else {
			initLocsE.set(postBodiesE.getWorldPoint(postBodiesE
					.getLocalCenter()));
		}
		mouseJointsE = createMouseJoint(circleObstacleE, initLocsE.x,
				initLocsE.y, 5);
		// RegisterUpdate Handler for Animated UsagiB
		setUpUpdateHandler(circleObstacleE, AnimatedUsagiE);
		// Set up global mouse joints Usagi F
		if (initLocsF == null) {
			initLocsF = new Vector2(postBodiesF.getWorldPoint(postBodiesF
					.getLocalCenter()));
		} else {
			initLocsF.set(postBodiesF.getWorldPoint(postBodiesF
					.getLocalCenter()));
		}
		mouseJointsF = createMouseJoint(circleObstacleF, initLocsF.x,
				initLocsF.y, 6);
		// RegisterUpdate Handler for Animated UsagiB
		setUpUpdateHandler(circleObstacleF, AnimatedUsagiF);
	}

	private void setRandomMap() {
		if (rMapPoint == 0) {
			if (SpriteBackground3A != null) {
				SpriteBackground3A.setVisible(true);
			}
			if (SpriteBackground3B != null) {
				SpriteBackground3B.setVisible(false);
			}
		} else {
			if (SpriteBackground3A != null) {
				SpriteBackground3A.setVisible(false);
			}
			if (SpriteBackground3B != null) {
				SpriteBackground3B.setVisible(true);
			}
		}
	}

	private void removeWalkMap() {
		try {
			for (int i = 0; i < mapLink.size(); i++) {
				final Body body = (Body) mapLink.get(i).getUserData();
				mPhysicsWorld.destroyBody(body);
				MapSpritePool.recyclePoolItem(mapLink.get(i));
			}
		} catch (Exception e) {
		}
	}

	private void destroyBody() {
		if (circleObstacleA != null) {
			circleObstacleA.setPosition(randomPositionPoint[0][0],
					randomPositionPoint[0][1]);
		}
		if (postBodiesA != null) {
			mPhysicsWorld.destroyBody(postBodiesA);
			postBodiesA = createUsagiBody(mPhysicsWorld, circleObstacleA,
					BodyType.DynamicBody, fixA);
			circleObstacleA.setUserData(postBodiesA);
		}
		if (groundBodiesA != null) {
			mPhysicsWorld.destroyBody(groundBodiesA);
			groundBodiesA = mPhysicsWorld.createBody(bDef);
		}
		// ####################circleObstacleB#######################
		if (circleObstacleB != null) {
			circleObstacleB.setPosition(randomPositionPoint[1][0],
					randomPositionPoint[1][1]);
		}
		if (postBodiesB != null) {
			mPhysicsWorld.destroyBody(postBodiesB);
			postBodiesB = createUsagiBody(mPhysicsWorld, circleObstacleB,
					BodyType.DynamicBody, fixB);
			circleObstacleB.setUserData(postBodiesB);
		}
		if (groundBodiesB != null) {
			mPhysicsWorld.destroyBody(groundBodiesB);
			groundBodiesB = mPhysicsWorld.createBody(bDef);
		}
		// #####################circleObstacleC######################
		if (circleObstacleC != null) {
			circleObstacleC.setPosition(randomPositionPoint[2][0],
					randomPositionPoint[2][1]);
		}
		if (postBodiesC != null) {
			mPhysicsWorld.destroyBody(postBodiesC);
			postBodiesC = createUsagiBody(mPhysicsWorld, circleObstacleC,
					BodyType.DynamicBody, fixC);
			circleObstacleC.setUserData(postBodiesC);
		}
		if (groundBodiesC != null) {
			mPhysicsWorld.destroyBody(groundBodiesC);
			groundBodiesC = mPhysicsWorld.createBody(bDef);
		}
		// ###################circleObstacleD########################
		if (circleObstacleD != null) {
			circleObstacleD.setPosition(randomPositionPoint[3][0],
					randomPositionPoint[3][1]);
		}
		if (postBodiesD != null) {
			mPhysicsWorld.destroyBody(postBodiesD);
			postBodiesD = createUsagiBody(mPhysicsWorld, circleObstacleD,
					BodyType.DynamicBody, fixD);
			circleObstacleD.setUserData(postBodiesD);
		}
		if (groundBodiesD != null) {
			mPhysicsWorld.destroyBody(groundBodiesD);
			groundBodiesD = mPhysicsWorld.createBody(bDef);
		}
		// ##################circleObstacleE#########################
		if (circleObstacleE != null) {
			circleObstacleE.setPosition(randomPositionPoint[4][0],
					randomPositionPoint[4][1]);
		}
		if (postBodiesE != null) {
			mPhysicsWorld.destroyBody(postBodiesE);
			postBodiesE = createUsagiBody(mPhysicsWorld, circleObstacleE,
					BodyType.DynamicBody, fixE);
			circleObstacleE.setUserData(postBodiesE);
		}
		if (groundBodiesE != null) {
			mPhysicsWorld.destroyBody(groundBodiesE);
			groundBodiesE = mPhysicsWorld.createBody(bDef);
		}
		// ##################circleObstacleF#########################
		if (circleObstacleF != null) {
			circleObstacleF.setPosition(randomPositionPoint[5][0],
					randomPositionPoint[5][1]);
		}
		if (postBodiesF != null) {
			mPhysicsWorld.destroyBody(postBodiesF);
			postBodiesF = createUsagiBody(mPhysicsWorld, circleObstacleF,
					BodyType.DynamicBody, fixF);
			circleObstacleF.setUserData(postBodiesF);
		}
		if (groundBodiesF != null) {
			mPhysicsWorld.destroyBody(groundBodiesF);
			groundBodiesF = mPhysicsWorld.createBody(bDef);
		}
	}

	private void unRegisterPhysic() {
		if (phyC_A != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_A);
			phyC_A = new PhysicsConnector(circleObstacleA, postBodiesA, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_A);
		}
		if (phyC_B != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_B);
			phyC_B = new PhysicsConnector(circleObstacleB, postBodiesB, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_B);
		}
		if (phyC_C != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_C);
			phyC_C = new PhysicsConnector(circleObstacleC, postBodiesC, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_C);
		}
		if (phyC_D != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_D);
			phyC_D = new PhysicsConnector(circleObstacleD, postBodiesD, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_D);
		}
		if (phyC_E != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_E);
			phyC_E = new PhysicsConnector(circleObstacleE, postBodiesE, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_E);
		}
		if (phyC_F != null) {
			mPhysicsWorld.unregisterPhysicsConnector(phyC_F);
			phyC_F = new PhysicsConnector(circleObstacleF, postBodiesF, true,
					false);
			mPhysicsWorld.registerPhysicsConnector(phyC_F);
		}
	}

	private static Body createUsagiBody(final PhysicsWorld pPhysicsWorld,
			final IAreaShape pAreaShape, final BodyType pBodyType,
			final FixtureDef pFixtureDef) {
		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;
		final float[] sceneCenterCoordinates = pAreaShape
				.getSceneCenterCoordinates();
		final float pCenterX = sceneCenterCoordinates[0];
		final float pCenterY = sceneCenterCoordinates[1];
		circleBodyDef.position.x = (pCenterX / 32.0F);
		circleBodyDef.position.y = (pCenterY / 32.0F);
		final float pRadius = pAreaShape.getWidthScaled() * 0.07F;
		Log.d("Day Pradius", "" + pRadius);
		final float pRotation = pAreaShape.getRotation();

		circleBodyDef.angle = MathUtils.degToRad(pRotation);
		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);
		final CircleShape circlePoly = new CircleShape();
		pFixtureDef.shape = circlePoly;
		final float radius = pRadius / 32.0f;
		Log.d("Day radius", "" + radius);
		circlePoly.setRadius(radius);
		circleBody.createFixture(pFixtureDef);
		circlePoly.dispose();
		return circleBody;
	}

	private void resetAnimatedSprite(AnimatedSprite anis) {
		if (anis != null) {
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			if (anis.isAnimationRunning()) {
				anis.stopAnimation();
			}
			anis.setCurrentTileIndex(0);
			anis.setVisible(false);
		}
	}

	private void resetSprite(Sprite anis) {
		if (anis != null) {
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			anis.setVisible(false);
		}
	}

	private void resetEllipse(IAreaShape ellipses) {
		if (ellipses != null) {
			ellipses.clearEntityModifiers();
			ellipses.clearUpdateHandlers();
			ellipses.setVisible(true);
		}
	}

	@Override
	public void onPauseGame() {
		Log.d("On Pause", "On pause" + TAG_LOG);
		this.disableAccelerationSensor();
		IndexYoushiGreen = 0;
		IndexYoushiBrown = 0;
		TouchYoushiGreen = false;
		TouchYoushiBrown = false;
		tempSpeedYoushi = 20f;
		speedOneYoushiGreen = 0;
		speedTwoYoushiGreen = 0;
		speedThreeYoushiGreen = 0;
		speedFourYoushiGreen = 0;
		speedOneYoushiBrown = 0;
		speedTwoYoushiBrown = 0;
		speedThreeYoushiBrown = 0;
		speedFourYoushiBrown = 0;
		countdownObject = 6;
		isTouchJisanCopy = false;
		isMoveA = false;
		isMoveB = false;
		isMoveC = false;
		isMoveD = false;
		isMoveE = false;
		isMoveF = false;
		isTouchCicleA = false;
		isTouchCicleB = false;
		isTouchCicleC = false;
		isTouchCicleD = false;
		isTouchCicleE = false;
		isTouchCicleF = false;
		beginOne = 1;
		beginTwo = 1;
		isTouchSnorkel = false;
		isTouchGimic = false;
		if (SpriteSnorkel != null) {
			SpriteSnorkel.clearUpdateHandlers();
		}
		if (SpriteKemuriA != null) {
			SpriteKemuriA.setVisible(false);
		}
		if (SpriteKemuriB != null) {
			SpriteKemuriB.setVisible(false);
		}
		if (AnimatedKamihubuki != null) {
			AnimatedKamihubuki.setVisible(false);
		}
		if (SpriteHouse != null) {
			SpriteHouse.clearEntityModifiers();
		}
		if (tHandlerA != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerA);
		}
		if (tHandlerB != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerB);
		}
		if (tHandlerC != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerC);
		}
		if (tHandlerD != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerD);
		}
		if (tHandlerE != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerE);
		}
		if (tHandlerF != null) {
			SpriteSnorkel.unregisterUpdateHandler(tHandlerF);
		}
		if (localPoint != null) {
			localPoint = null;
		}
		if (initLocsA != null) {
			initLocsA = null;
		}
		if (initLocsB != null) {
			initLocsB = null;
		}
		if (initLocsC != null) {
			initLocsC = null;
		}
		if (initLocsD != null) {
			initLocsD = null;
		}
		if (initLocsE != null) {
			initLocsE = null;
		}
		if (initLocsF != null) {
			initLocsF = null;
		}
		if (touchVecMove != null) {
			touchVecMove = null;
		}
		if (touchVecDown == null) {
			touchVecDown = null;
		}
		// ####################circleObstacleA#######################
		try {
			
			removeWalkMap();
			randomPositions();
			// Destroy Body and setPosition again
			destroyBody();
			randomMapPointObject();
			setRandomMap();
			// again register PhysicConnector
			unRegisterPhysic();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (AnimatedYoushiGreen != null) {
			AnimatedYoushiGreen.clearEntityModifiers();
			AnimatedYoushiGreen.setCurrentTileIndex(0);
			AnimatedYoushiGreen.setFlippedHorizontal(false);
			AnimatedYoushiBrown.setPosition(710f,501f);
                
		}
		if (AnimatedYoushiBrown != null) {
			AnimatedYoushiBrown.clearEntityModifiers();
			AnimatedYoushiBrown.setCurrentTileIndex(0);
			AnimatedYoushiBrown.setFlippedHorizontal(false);
			AnimatedYoushiGreen.setPosition(168f,53f);
		}
		if (AnimatedJisan != null) {
			AnimatedJisan.clearEntityModifiers();
			AnimatedJisan.setCurrentTileIndex(0);
			AnimatedJisan.setVisible(false);
			AnimatedJisan.setPosition(349, 1);
			if (AnimatedJisan.isAnimationRunning()) {
				AnimatedJisan.stopAnimation();
			}
		}
		if (AnimatedJisanCopy != null) {
			AnimatedJisanCopy.clearEntityModifiers();
			AnimatedJisanCopy.setCurrentTileIndex(0);
			AnimatedJisanCopy.setVisible(false);
			AnimatedJisanCopy.setPosition(349, 1);
		}
		resetAnimatedSprite(AnimatedUsagiA);
		resetAnimatedSprite(AnimatedUsagiB);
		resetAnimatedSprite(AnimatedUsagiC);
		resetAnimatedSprite(AnimatedUsagiD);
		resetAnimatedSprite(AnimatedUsagiE);
		resetAnimatedSprite(AnimatedUsagiF);
		resetSprite(SpriteUsagiA);
		resetSprite(SpriteUsagiB);
		resetSprite(SpriteUsagiC);
		resetSprite(SpriteUsagiD);
		resetSprite(SpriteUsagiE);
		resetSprite(SpriteUsagiF);
		resetEllipse(circleObstacleA);
		resetEllipse(circleObstacleB);
		resetEllipse(circleObstacleC);
		resetEllipse(circleObstacleD);
		resetEllipse(circleObstacleE);
		resetEllipse(circleObstacleF);
		super.onPauseGame();
	}

	private void createWalkMap() {
		if (mapLink != null) {
			mapLink.clear();
		}
		if (randomMapPoint.length > 0) {
			int tempX = 0;
			int tempY = 0;
			int tempWith = 0;
			int tempHeight = 0;
			for (int i = 0; i < randomMapPoint.length; i++) {
				for (int j = 0; j < 4; j++) {
					if (j == 0) {
						tempX = randomMapPoint[i][0];
					}
					if (j == 1) {
						tempY = randomMapPoint[i][1];
					}
					if (j == 2) {
						tempWith = randomMapPoint[i][2];
					}
					if (j == 3) {
						tempHeight = randomMapPoint[i][3];
					}
				}
				final Sprite sprite = MapSpritePool.obtainPoolItem();
				sprite.setPosition(tempX, tempY);
				sprite.setSize(tempWith, tempHeight);
				final Body body = PhysicsFactory.createBoxBody(mPhysicsWorld,
						sprite, BodyType.KinematicBody, wallFixtureDef);
				sprite.setUserData(body);
				mapLink.add(sprite);
				mScene.attachChild(sprite);
			}
		}
	}

	@Override
	protected void loadKaraokeComplete() {
		this.mEngine.enableVibrator(getActivity());
		Log.d("Load karaoke Complete", "Load karaoke Complete");
		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		this.enableAccelerationSensor(this);
		if (SpriteKemuriA != null) {
			SpriteKemuriA.setVisible(false);
		}
		if (SpriteKemuriB != null) {
			SpriteKemuriB.setVisible(false);
		}
		if (AnimatedUsagiA != null) {
			AnimatedUsagiA.setVisible(true);
		}
		if (AnimatedUsagiB != null) {
			AnimatedUsagiB.setVisible(true);
		}
		if (AnimatedUsagiC != null) {
			AnimatedUsagiC.setVisible(true);
		}
		if (AnimatedUsagiD != null) {
			AnimatedUsagiD.setVisible(true);
		}
		if (AnimatedUsagiE != null) {
			AnimatedUsagiE.setVisible(true);
		}
		if (AnimatedUsagiF != null) {
			AnimatedUsagiF.setVisible(true);
		}
		if (SpriteUsagiA != null) {
			SpriteUsagiA.setVisible(false);
		}
		if (SpriteUsagiB != null) {
			SpriteUsagiB.setVisible(false);
		}
		if (SpriteUsagiC != null) {
			SpriteUsagiC.setVisible(false);
		}
		if (SpriteKemuriA != null) {
			SpriteKemuriA.setVisible(false);
		}
		if (SpriteUsagiE != null) {
			SpriteUsagiE.setVisible(false);
		}
		if (SpriteUsagiF != null) {
			SpriteUsagiF.setVisible(false);
		}
		if (SpriteDoor1 != null) {
			SpriteDoor1.setVisible(false);
		}
		if (SpriteDoor2 != null) {
			SpriteDoor2.setVisible(true);
		}
		if (SpriteDoor3 != null) {
			SpriteDoor3.setVisible(false);
		}
		createWalkMap();
		runAnimatedJisanLeftDefault();
		runDrawableYoushiGreen();
		JisanGreenWalkAround();
		runDrawableYoushiBrown();
		JisanBrownWalkAround();
		mScene.registerTouchArea(circleObstacleA);
		mScene.registerTouchArea(circleObstacleB);
		mScene.registerTouchArea(circleObstacleC);
		mScene.registerTouchArea(circleObstacleD);
		mScene.registerTouchArea(circleObstacleE);
		mScene.registerTouchArea(circleObstacleF);
		globalMouseJoin();
		Log.d("onResumeGame", "onResumeGame");
		super.onResumeGame();
	}

	private void assignBooleanIsTouch(int objects) {
		switch (objects) {
		case 1:
			isTouchCicleA = false;
			changUsagiImages(AnimatedUsagiA);
			break;
		case 2:
			isTouchCicleB = false;
			changUsagiImages(AnimatedUsagiB);
			break;
		case 3:
			isTouchCicleC = false;
			changUsagiImages(AnimatedUsagiC);
			break;
		case 4:
			isTouchCicleD = false;
			changUsagiImages(AnimatedUsagiD);
			break;
		case 5:
			isTouchCicleE = false;
			changUsagiImages(AnimatedUsagiE);
			break;
		case 6:
			isTouchCicleF = false;
			changUsagiImages(AnimatedUsagiF);
			break;
		}
	}

	private int moveCircleAcions(TouchEvent pSceneTouchEvent,
			IAreaShape ellipses, final int objects) {
		int check = 0;
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pX > (int) ellipses.getX()) {
			if ((pX - (int) ellipses.getX()) <= 62) {
				check = 1;
			} else {
				assignBooleanIsTouch(objects);
			}
		}
		if (pY > (int) ellipses.getY()) {
			if ((pY - (int) ellipses.getY()) <= 62) {
				check = 2;
			} else {
				assignBooleanIsTouch(objects);
			}
		}
		if (((int) ellipses.getX() > pX)) {
			if (((int) ellipses.getX() - pX) <= 62) {
				check = 3;
			} else {
				assignBooleanIsTouch(objects);
			}
		}
		if (((int) ellipses.getY() > pY)) {
			if (((int) ellipses.getY() - pY) <= 62) {
				check = 4;
			} else {
				assignBooleanIsTouch(objects);
			}
		}
		return check;
	}

	private void unRegisterTouchArea() {
		mScene.unregisterTouchArea(circleObstacleF);
		mScene.unregisterTouchArea(circleObstacleC);
		mScene.unregisterTouchArea(circleObstacleA);
		mScene.unregisterTouchArea(circleObstacleB);
		mScene.unregisterTouchArea(circleObstacleD);
		mScene.unregisterTouchArea(circleObstacleE);
	}

	private void changUsagiImages(final AnimatedSprite anmts1) {
		if (anmts1.isAnimationRunning()) {
			anmts1.stopAnimation();
		}
		anmts1.setCurrentTileIndex(3);
	}

	private final TimerHandler soundUsagiWalkHandler(TimerHandler times,
			final int registerHanders) {
		A18_08_TOKO.play();
		times = new TimerHandler(0.3f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				switch (registerHanders) {
				case 1:
					unRegisterSound(tHandlerA, isTouchCicleA, isMoveA);
					break;
				case 2:
					unRegisterSound(tHandlerB, isTouchCicleB, isMoveB);
					break;
				case 3:
					unRegisterSound(tHandlerC, isTouchCicleC, isMoveC);
					break;
				case 4:
					unRegisterSound(tHandlerD, isTouchCicleD, isMoveD);
					break;
				case 5:
					unRegisterSound(tHandlerE, isTouchCicleE, isMoveE);
					break;
				case 6:
					unRegisterSound(tHandlerF, isTouchCicleF, isMoveF);
					break;
				}
			}
		});
		SpriteSnorkel.registerUpdateHandler(times);
		return times;
	}

	private void unRegisterSound(TimerHandler times, boolean isTouchCircles,
			boolean isMoves) {
		if (!isTouchCircles || isMoves) {
			SpriteSnorkel.unregisterUpdateHandler(times);
		} else {
			A18_08_TOKO.play();
		}
	}

	private void setVisibleCircle(int anmts2) {
		switch (anmts2) {
		case 1:
			circleObstacleA.setVisible(false);
			circleObstacleA.setPosition(0, 0);
			if (tHandlerA != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerA);
			}
			break;
		case 2:
			circleObstacleB.setVisible(false);
			circleObstacleB.setPosition(0, 0);
			if (tHandlerB != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerB);
			}
			break;
		case 3:
			circleObstacleC.setVisible(false);
			circleObstacleC.setPosition(0, 0);
			if (tHandlerC != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerC);
			}
			break;
		case 4:
			circleObstacleD.setVisible(false);
			circleObstacleD.setPosition(0, 0);
			if (tHandlerD != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerD);
			}
			break;
		case 5:
			circleObstacleE.setVisible(false);
			circleObstacleE.setPosition(0, 0);
			if (tHandlerE != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerE);
			}
			break;
		case 6:
			circleObstacleF.setVisible(false);
			circleObstacleF.setPosition(0, 0);
			if (tHandlerF != null) {
				SpriteSnorkel.unregisterUpdateHandler(tHandlerF);
			}
			break;
		}
	}

	private void moveToHouse(final IAreaShape cicles,
			final AnimatedSprite anmts1, final int anmts2, final Sprite sp1s,
			final Sprite sp2s, final Sprite sp3s) {
		setVisibleCircle(anmts2);
		A18_07_PYON.play();
		isTouchJisanCopy = true;
		// remove touch area with circle
		// Open the door
		sp1s.setVisible(true);
		if (anmts2 == 1 || anmts2 == 2 || anmts2 == 3) {
			if (AnimatedJisan.isAnimationRunning()) {
				AnimatedJisan.stopAnimation();
			}
			runAnimatedJisanRightFunny();
		}
		if (anmts2 == 4 || anmts2 == 5 || anmts2 == 6) {
			if (AnimatedJisan.isAnimationRunning()) {
				AnimatedJisan.stopAnimation();
			}
			runAnimatedJisanLeftFunny();
		}
		SpriteHouse.registerEntityModifier(new DelayModifier(1.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// Set visible Animated Usagi A, B, C, D, E, F
						anmts1.setVisible(false);
						anmts1.setCurrentTileIndex(0);
						if (anmts1.isAnimationRunning()) {
							anmts1.stopAnimation();
						}
						SpriteHouse.registerEntityModifier(new DelayModifier(
								0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										// Close the Door
										sp1s.setVisible(false);
										SpriteHouse
												.registerEntityModifier(new DelayModifier(
														0.5f,
														new IEntityModifierListener() {
															@Override
															public void onModifierFinished(
																	IModifier<IEntity> pModifier,
																	IEntity pItem) {
																// Set Visible
																// for
																// SpriteUsagi
																// A, B, C, D,
																// E, F
																sp2s.setVisible(true);
																// Set visible
																// for sprite
																// door of
																// SpriteUsagi E
																if (sp3s != null) {
																	sp3s.setVisible(true);
																}
																if (anmts1 != null) {
																	anmts1.clearEntityModifiers();
																	anmts1.clearUpdateHandlers();
																}
																if (circleObstacleA
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleA);
																}
																if (circleObstacleB
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleB);
																}
																if (circleObstacleC
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleC);
																}
																if (circleObstacleD
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleD);
																}
																if (circleObstacleE
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleE);
																}
																if (circleObstacleF
																		.isVisible()) {
																	mScene.registerTouchArea(circleObstacleF);
																}
																isTouchJisanCopy = false;
																if (SpriteSnorkel != null) {
																	SpriteSnorkel
																			.clearUpdateHandlers();
																}
																countdownObject--;
																Log.d("Test count down",
																		"Test count down"
																				+ countdownObject);
																if (countdownObject == 0) {
																	changeMapWhenWin();
																} else {
																	if (AnimatedJisan
																			.isAnimationRunning()) {
																		AnimatedJisan
																				.stopAnimation();
																	}
																	runAnimatedJisanLeftDefault();
																}
															}

															@Override
															public void onModifierStarted(
																	IModifier<IEntity> paramIModifier,
																	IEntity paramT) {
															}
														}));
									}

									@Override
									public void onModifierStarted(
											IModifier<IEntity> paramIModifier,
											IEntity paramT) {
									}
								}));
					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}
				}));
	}

	private void changeMapWhenWin() {
		if (SpriteUsagiA.isVisible() && SpriteUsagiB.isVisible()
				&& SpriteUsagiC.isVisible() && SpriteUsagiD.isVisible()
				&& SpriteUsagiE.isVisible() && SpriteUsagiF.isVisible()) {
			A18_07_YOKATANE.play();
			isTouchSnorkel = true;
			isTouchJisanCopy = true;
			AnimatedKamihubuki.setVisible(true);
			SpriteHouse.registerEntityModifier(new DelayModifier(2.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							beginOne = 1;
							beginTwo = 1;
							isTouchSnorkel = false;
							isTouchJisanCopy = false;
							SpriteKemuriA.setVisible(false);
							SpriteKemuriB.setVisible(false);
							if (AnimatedJisan.isAnimationRunning()) {
								AnimatedJisan.stopAnimation();
								runAnimatedJisanLeftDefault();
							}
							AnimatedKamihubuki.setVisible(false);
							removeWalkMap();
							randomMapPointObject();
							createWalkMap();
							if (localPoint != null) {
								localPoint = null;
							}
							if (initLocsA != null) {
								initLocsA = null;
							}
							if (initLocsB != null) {
								initLocsB = null;
							}
							if (initLocsC != null) {
								initLocsC = null;
							}
							if (initLocsD != null) {
								initLocsD = null;
							}
							if (initLocsE != null) {
								initLocsE = null;
							}
							if (initLocsF != null) {
								initLocsF = null;
							}
							if (touchVecMove != null) {
								touchVecMove = null;
							}
							if (touchVecDown == null) {
								touchVecDown = null;
							}
							// ####################circleObstacleA#######################
							randomPositions();
							// Destroy body and set position again
							destroyBody();
							setRandomMap();
							// again register PhysicConnector
							unRegisterPhysic();
							circleObstacleA.setVisible(true);
							circleObstacleB.setVisible(true);
							circleObstacleC.setVisible(true);
							circleObstacleD.setVisible(true);
							circleObstacleE.setVisible(true);
							circleObstacleF.setVisible(true);
							AnimatedUsagiA.setVisible(true);
							AnimatedUsagiB.setVisible(true);
							AnimatedUsagiC.setVisible(true);
							AnimatedUsagiD.setVisible(true);
							AnimatedUsagiE.setVisible(true);
							AnimatedUsagiF.setVisible(true);
							SpriteUsagiA.setVisible(false);
							SpriteUsagiB.setVisible(false);
							SpriteUsagiC.setVisible(false);
							SpriteUsagiD.setVisible(false);
							SpriteUsagiE.setVisible(false);
							SpriteUsagiF.setVisible(false);
							SpriteDoor1.setVisible(false);
							SpriteDoor2.setVisible(true);
							SpriteDoor3.setVisible(false);
							mScene.registerTouchArea(circleObstacleA);
							mScene.registerTouchArea(circleObstacleB);
							mScene.registerTouchArea(circleObstacleC);
							mScene.registerTouchArea(circleObstacleD);
							mScene.registerTouchArea(circleObstacleE);
							mScene.registerTouchArea(circleObstacleF);
							globalMouseJoin();
							countdownObject = 6;
							isMoveA = false;
							isMoveB = false;
							isMoveC = false;
							isMoveD = false;
							isMoveE = false;
							isMoveF = false;
						}

						@Override
						public void onModifierStarted(
								IModifier<IEntity> paramIModifier,
								IEntity paramT) {
						}
					}));
		}
	}

	private void changeImageJisanMove(TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		if (pX > CAMERA_WIDTH / 2) {
			if (beginOne == 1) {
				beginTwo = 1;
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				long pDuration[] = new long[] { 500, 500 };
				int pFram[] = new int[] { 8, 9 };
				AnimatedJisan.animate(pDuration, pFram, -1);
			}
			beginOne = 2;
		} else {
			if (beginTwo == 1) {
				beginOne = 1;
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				long pDuration[] = new long[] { 500, 500 };
				int pFram[] = new int[] { 2, 3 };
				AnimatedJisan.animate(pDuration, pFram, -1);
			}
			beginTwo = 2;
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.isActionDown()) {
			// Touch AnimatedYoushiGreen
			if (AnimatedYoushiGreen.contains(pX, pY)) {
				if (!TouchYoushiGreen) {
					isTouchGimic = true;
					setTouchGimmic3(false);
					A18_06_PAN2.play();
					TouchYoushiGreen = true;
					touchYoushiGreen();
				}
			}
			if (AnimatedYoushiBrown.contains(pX, pY)) {
				if (!TouchYoushiBrown) {
					setTouchGimmic3(false);
					isTouchGimic = true;
					A18_06_PAN2.play();
					TouchYoushiBrown = true;
					touchYoushiBrown();
				}

			}
			if (checkContains(SpriteSnorkel, 158, 73, 201, 165, pX, pY)) {
				if (isTouchSnorkel) {
					SpriteKemuriA.setVisible(true);
					SpriteKemuriB.setVisible(true);
				}
			}
			if (checkContains(AnimatedJisan, 92, 93, 157, 187, pX, pY)) {
				if (!isTouchJisanCopy) {
					A18_04_KOCHI.play();
					isTouchJisanCopy = true;
					if (AnimatedJisan.getCurrentTileIndex() == 2
							|| AnimatedJisan.getCurrentTileIndex() == 3
							|| AnimatedJisan.getCurrentTileIndex() == 4
							|| AnimatedJisan.getCurrentTileIndex() == 5
							|| AnimatedJisan.getCurrentTileIndex() == 6
							|| AnimatedJisan.getCurrentTileIndex() == 7) {
						runAnimatedJisanCopyLeft();
					}
					if (AnimatedJisan.getCurrentTileIndex() == 8
							|| AnimatedJisan.getCurrentTileIndex() == 9
							|| AnimatedJisan.getCurrentTileIndex() == 10
							|| AnimatedJisan.getCurrentTileIndex() == 11
							|| AnimatedJisan.getCurrentTileIndex() == 12
							|| AnimatedJisan.getCurrentTileIndex() == 13) {
						runAnimatedJisanCopyRight();
					}
				}
			}
			return true;
		}
		if (touchVecMove == null) {
			touchVecMove = new Vector2(pSceneTouchEvent.getX()
					/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
					pSceneTouchEvent.getY()
							/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		} else {
			touchVecMove.set(pSceneTouchEvent.getX()
					/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
					pSceneTouchEvent.getY()
							/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		}
		if (mPhysicsWorld != null) {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_MOVE:
				touchVecMove
						.set(pSceneTouchEvent.getX()
								/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
								pSceneTouchEvent.getY()
										/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
				if (isTouchCicleA) {
					if (circleObstacleAll.contains(circleObstacleA.getX(),
							circleObstacleA.getY())) {
						unRegisterTouchArea();
						isMoveA = true;
					}
					if (!isMoveA) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleA,
								1) != 0) {
							AnimatedUsagiA.setPosition(
									(circleObstacleA.getX() - AnimatedUsagiA
											.getWidth() / 2) + 35,
									(circleObstacleA.getY() - AnimatedUsagiA
											.getHeight() / 2) - 15);
							mouseJointsA.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				if (isTouchCicleB) {
					if (circleObstacleAll.contains(circleObstacleB.getX(),
							circleObstacleB.getY())) {
						unRegisterTouchArea();
						isMoveB = true;
					}
					if (!isMoveB) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleB,
								2) != 0) {
							AnimatedUsagiB.setPosition(
									(circleObstacleB.getX() - AnimatedUsagiB
											.getWidth() / 2) + 35,
									(circleObstacleB.getY() - AnimatedUsagiB
											.getHeight() / 2) - 15);
							mouseJointsB.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				if (isTouchCicleC) {
					if (circleObstacleAll.contains(circleObstacleC.getX(),
							circleObstacleC.getY())) {
						unRegisterTouchArea();
						isMoveC = true;
					}
					if (!isMoveC) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleC,
								3) != 0) {
							AnimatedUsagiC.setPosition(
									(circleObstacleC.getX() - AnimatedUsagiC
											.getWidth() / 2) + 35,
									(circleObstacleC.getY() - AnimatedUsagiC
											.getHeight() / 2) - 15);
							mouseJointsC.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				if (isTouchCicleD) {
					if (circleObstacleAll.contains(circleObstacleD.getX(),
							circleObstacleD.getY())) {
						unRegisterTouchArea();
						isMoveD = true;
					}
					if (!isMoveD) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleD,
								4) != 0) {
							AnimatedUsagiD.setPosition(
									(circleObstacleD.getX() - AnimatedUsagiD
											.getWidth() / 2) + 35,
									(circleObstacleD.getY() - AnimatedUsagiD
											.getHeight() / 2) - 15);
							mouseJointsD.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				if (isTouchCicleE) {
					if (circleObstacleAll.contains(circleObstacleE.getX(),
							circleObstacleE.getY())) {
						unRegisterTouchArea();
						isMoveE = true;
					}
					if (!isMoveE) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleE,
								5) != 0) {
							AnimatedUsagiE.setPosition(
									(circleObstacleE.getX() - AnimatedUsagiE
											.getWidth() / 2) + 35,
									(circleObstacleE.getY() - AnimatedUsagiE
											.getHeight() / 2) - 15);
							mouseJointsE.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				if (isTouchCicleF) {
					if (circleObstacleAll.contains(circleObstacleF.getX(),
							circleObstacleF.getY())) {
						isMoveF = true;
						unRegisterTouchArea();
					}
					if (!isMoveF) {
						if (moveCircleAcions(pSceneTouchEvent, circleObstacleF,
								6) != 0) {
							AnimatedUsagiF.setPosition(
									(circleObstacleF.getX() - AnimatedUsagiF
											.getWidth() / 2) + 35,
									(circleObstacleF.getY() - AnimatedUsagiF
											.getHeight() / 2) - 15);
							mouseJointsF.setTarget(touchVecMove);
							changeImageJisanMove(pSceneTouchEvent);
						}
					}
				}
				return true;
			case TouchEvent.ACTION_UP:
				beginOne = 1;
				beginTwo = 1;
				Log.d(TAG, "UPONTOUCH AREA" + "GET boolean touch A"
						+ isTouchCicleA + isMoveA);
				if (isTouchCicleA) {
					Log.d(TAG, "UPONTOUCH AREA" + "GET boolean touch A"
							+ isTouchCicleA);
					isTouchCicleA = false;
					if (isMoveA) {
						moveToHouse(circleObstacleA, AnimatedUsagiA, 1,
								SpriteDoor1, SpriteUsagiA, null);
					} else {
						changUsagiImages(AnimatedUsagiA);
					}
					Log.d(TAG, "UP Usagi A");
				}
				if (isTouchCicleB) {
					isTouchCicleB = false;
					if (isMoveB) {
						moveToHouse(circleObstacleB, AnimatedUsagiB, 2,
								SpriteDoor1, SpriteUsagiB, null);
					} else {
						changUsagiImages(AnimatedUsagiB);
					}
					Log.d(TAG, "UP Usagi B");
				}
				if (isTouchCicleC) {
					isTouchCicleC = false;
					if (isMoveC) {
						moveToHouse(circleObstacleC, AnimatedUsagiC, 3,
								SpriteDoor1, SpriteUsagiC, null);
					} else {
						changUsagiImages(AnimatedUsagiC);
					}
					Log.d(TAG, "UP Usagi C");
				}
				if (isTouchCicleD) {
					isTouchCicleD = false;
					if (isMoveD) {
						moveToHouse(circleObstacleD, AnimatedUsagiD, 4,
								SpriteDoor1, SpriteUsagiD, null);
					} else {
						changUsagiImages(AnimatedUsagiD);
					}
					Log.d(TAG, "UP Usagi D");
				}
				if (isTouchCicleE) {
					isTouchCicleE = false;
					if (isMoveE) {
						moveToHouse(circleObstacleE, AnimatedUsagiE, 5,
								SpriteDoor1, SpriteUsagiE, SpriteDoor3);
					} else {
						changUsagiImages(AnimatedUsagiE);
					}
					Log.d(TAG, "UP Usagi E");
				}
				if (isTouchCicleF) {
					isTouchCicleF = false;
					if (isMoveF) {
						moveToHouse(circleObstacleF, AnimatedUsagiF, 6,
								SpriteDoor1, SpriteUsagiF, null);
					} else {
						changUsagiImages(AnimatedUsagiF);
					}
					Log.d(TAG, "UP Usagi F");
				}
				return true;
			}
			return false;
		}

		return false;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (touchVecDown == null) {
				touchVecDown = new Vector2(pTouchAreaLocalX
						/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
						pTouchAreaLocalY
								/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			} else {
				touchVecDown
						.set(pTouchAreaLocalX
								/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
								pTouchAreaLocalY
										/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			}
			if (circleObstacleA.contains(pX, pY) && circleObstacleA.isVisible()) {
				isTouchCicleA = true;
				if (tHandlerA != null) {
					mScene.unregisterUpdateHandler(tHandlerA);
				}
				tHandlerA = soundUsagiWalkHandler(tHandlerA, 1);
				Log.d(TAG, "DOWN Usagi A");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanRightAbove();
					break;
				case 1:
					runAnimatedJisanRightAbove();
					break;
				case 2:
					runAnimatedJisanRightMidle();
					break;
				case 3:
					runAnimatedJisanRightMidle();
					break;
				case 4:
					runAnimatedJisanRightBelow();
					break;
				case 5:
					runAnimatedJisanRightBelow();
					break;
				}
				runAnimatedUsagiA();
				AnimatedUsagiA
						.setPosition((circleObstacleA.getX() - AnimatedUsagiA
								.getWidth() / 2) + 35,
								(circleObstacleA.getY() - AnimatedUsagiA
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			if (circleObstacleB.contains(pX, pY) && circleObstacleB.isVisible()) {
				isTouchCicleB = true;
				if (tHandlerB != null) {
					mScene.unregisterUpdateHandler(tHandlerB);
				}
				tHandlerB = soundUsagiWalkHandler(tHandlerB, 2);
				Log.d(TAG, "DOWN Usagi B");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanRightMidle();
					break;
				case 1:
					runAnimatedJisanRightBelow();
					break;
				case 2:
					runAnimatedJisanRightAbove();
					break;
				case 3:
					runAnimatedJisanRightAbove();
					break;
				case 4:
					runAnimatedJisanRightMidle();
					break;
				case 5:
					runAnimatedJisanRightAbove();
					break;
				}
				runAnimatedUsagiB();
				AnimatedUsagiB
						.setPosition((circleObstacleB.getX() - AnimatedUsagiB
								.getWidth() / 2) + 35,
								(circleObstacleB.getY() - AnimatedUsagiB
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			if (circleObstacleC.contains(pX, pY) && circleObstacleC.isVisible()) {
				isTouchCicleC = true;
				if (tHandlerC != null) {
					mScene.unregisterUpdateHandler(tHandlerC);
				}
				tHandlerC = soundUsagiWalkHandler(tHandlerC, 3);
				Log.d(TAG, "DOWN Usagi C");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanRightBelow();
					break;
				case 1:
					runAnimatedJisanRightMidle();
					break;
				case 2:
					runAnimatedJisanRightMidle();
					break;
				case 3:
					runAnimatedJisanRightBelow();
					break;
				case 4:
					runAnimatedJisanRightAbove();
					break;
				case 5:
					runAnimatedJisanRightAbove();
					break;
				}
				runAnimatedUsagiC();
				AnimatedUsagiC
						.setPosition((circleObstacleC.getX() - AnimatedUsagiC
								.getWidth() / 2) + 35,
								(circleObstacleC.getY() - AnimatedUsagiC
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			if (circleObstacleD.contains(pX, pY) && circleObstacleD.isVisible()) {
				isTouchCicleD = true;
				if (tHandlerD != null) {
					mScene.unregisterUpdateHandler(tHandlerD);
				}
				tHandlerD = soundUsagiWalkHandler(tHandlerD, 4);
				Log.d(TAG, "DOWN Usagi D");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanLeftDefault();
					break;
				case 1:
					runAnimatedJisanLeftDefault();
					break;
				case 2:
					runAnimatedJisanLeftMidle();
					break;
				case 3:
					runAnimatedJisanLeftMidle();
					break;
				case 4:
					runAnimatedJisanLeftAbove();
					break;
				case 5:
					runAnimatedJisanLeftAbove();
					break;
				}
				runAnimatedUsagiD();
				AnimatedUsagiD
						.setPosition((circleObstacleD.getX() - AnimatedUsagiD
								.getWidth() / 2) + 35,
								(circleObstacleD.getY() - AnimatedUsagiD
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			if (circleObstacleE.contains(pX, pY) && circleObstacleE.isVisible()) {
				isTouchCicleE = true;
				if (tHandlerE != null) {
					mScene.unregisterUpdateHandler(tHandlerE);
				}
				tHandlerE = soundUsagiWalkHandler(tHandlerE, 5);
				Log.d(TAG, "DOWN Usagi E");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanLeftMidle();
					break;
				case 1:
					runAnimatedJisanLeftDefault();
					break;
				case 2:
					runAnimatedJisanLeftAbove();
					break;
				case 3:
					runAnimatedJisanLeftAbove();
					break;
				case 4:
					runAnimatedJisanLeftMidle();
					break;
				case 5:
					runAnimatedJisanLeftAbove();
					break;
				}
				runAnimatedUsagiE();
				AnimatedUsagiE
						.setPosition((circleObstacleE.getX() - AnimatedUsagiE
								.getWidth() / 2) + 35,
								(circleObstacleE.getY() - AnimatedUsagiE
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			if (circleObstacleF.contains(pX, pY) && circleObstacleF.isVisible()) {
				isTouchCicleF = true;
				if (tHandlerF != null) {
					mScene.unregisterUpdateHandler(tHandlerF);
				}
				tHandlerF = soundUsagiWalkHandler(tHandlerF, 6);
				Log.d(TAG, "DOWN Usagi F");
				if (AnimatedJisan.isAnimationRunning()) {
					AnimatedJisan.stopAnimation();
				}
				switch (rPosition) {
				case 0:
					runAnimatedJisanLeftAbove();
					break;
				case 1:
					runAnimatedJisanLeftMidle();
					break;
				case 2:
					runAnimatedJisanLeftMidle();
					break;
				case 3:
					runAnimatedJisanLeftDefault();
					break;
				case 4:
					runAnimatedJisanLeftAbove();
					break;
				case 5:
					runAnimatedJisanLeftAbove();
					break;
				}
				runAnimatedUsagiF();
				AnimatedUsagiF
						.setPosition((circleObstacleF.getX() - AnimatedUsagiF
								.getWidth() / 2) + 35,
								(circleObstacleF.getY() - AnimatedUsagiF
										.getHeight() / 2) - 15);
				bDef.position.set(touchVecDown);
			}
			return true;
		}
		return false;
	}

	private Sound A18_04_KOCHI, A18_08_TOKO, A18_07_PYON, A18_06_PAN2,
			A18_07_YOKATANE;

	@Override
	protected void loadKaraokeSound() {
		A18_04_KOCHI = loadSoundResourceFromSD(Vol3YamagoyaResource.A18_04_KOCHI);
		A18_08_TOKO = loadSoundResourceFromSD(Vol3YamagoyaResource.A18_08_TOKO);
		A18_07_PYON = loadSoundResourceFromSD(Vol3YamagoyaResource.A18_07_PYON);
		A18_06_PAN2 = loadSoundResourceFromSD(Vol3YamagoyaResource.A18_06_PAN2);
		A18_07_YOKATANE = loadSoundResourceFromSD(Vol3YamagoyaResource.A18_07_YOKATANE);
	}

	@Override
	public void combineGimmic3WithAction() {
		if (!isTouchGimic) {
			isTouchGimic = true;
			A18_06_PAN2.play();
			TouchYoushiBrown = true;
			TouchYoushiGreen = true;
			setTouchGimmic3(false);
			touchYoushiGreen();
			touchYoushiBrown();
		}

	}

	public MouseJoint createMouseJoint(final IAreaShape pFace,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY,
			final int object) {
		final Body body = (Body) pFace.getUserData();
		final MouseJointDef mouseJointDef = new MouseJointDef();
		if (localPoint == null) {
			localPoint = new Vector2(
					(pTouchAreaLocalX - pFace.getWidth() * 0.5f)
							/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
					(pTouchAreaLocalY - pFace.getHeight() * 0.5f)
							/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		} else {
			localPoint.set((pTouchAreaLocalX - pFace.getWidth() * 0.5f)
					/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
					(pTouchAreaLocalY - pFace.getHeight() * 0.5f)
							/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		}
		switch (object) {
		case 1:
			groundBodiesA.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesA;
			break;
		case 2:
			groundBodiesB.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesB;
			break;
		case 3:
			groundBodiesC.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesC;
			break;
		case 4:
			groundBodiesD.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesD;
			break;
		case 5:
			groundBodiesE.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesE;
			break;
		case 6:
			groundBodiesF.setTransform(localPoint, 0);
			mouseJointDef.bodyA = groundBodiesF;
			break;
		}
		mouseJointDef.bodyB = body;
		mouseJointDef.dampingRatio = 0.95f;
		mouseJointDef.frequencyHz = 30f;
		mouseJointDef.maxForce = (3000.0f * body.getMass());
		mouseJointDef.collideConnected = true;
		mouseJointDef.target.set(body.getWorldPoint(localPoint));
		return (MouseJoint) mPhysicsWorld.createJoint(mouseJointDef);
	}

	// Jisan
	private void runAnimatedJisanCopyLeft() {
		AnimatedJisanCopy.setCurrentTileIndex(0);
		AnimatedJisanCopy.setVisible(true);
		if (!AnimatedJisanCopy.isAnimationRunning()) {
			long pDuration[] = new long[] { 1000, 1000 };
			int pFram[] = new int[] { 0, 1 };
			AnimatedJisanCopy.animate(pDuration, pFram, 0,
					new IAnimationListener() {
						@Override
						public void onAnimationStarted(
								AnimatedSprite paramAnimatedSprite, int paramInt) {
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							AnimatedJisanCopy.setVisible(false);
							isTouchJisanCopy = false;
						}
					});
		}
		if (AnimatedJisan.isAnimationRunning()) {
			AnimatedJisan.stopAnimation();
			if (!AnimatedJisan.isAnimationRunning()) {
				long pDuration[] = new long[] { 500, 500, 500, 500 };
				int pFram[] = new int[] { 2, 3, 8, 9 };
				AnimatedJisan.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {
							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								A18_04_KOCHI.play();
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> paramIModifier,
									IEntity paramT) {
							}
						}));
				AnimatedJisan.animate(pDuration, pFram, 0,
						new IAnimationListener() {
							@Override
							public void onAnimationStarted(
									AnimatedSprite paramAnimatedSprite,
									int paramInt) {
							}

							@Override
							public void onAnimationFrameChanged(
									AnimatedSprite paramAnimatedSprite,
									int paramInt1, int paramInt2) {
							}

							@Override
							public void onAnimationLoopFinished(
									AnimatedSprite paramAnimatedSprite,
									int paramInt1, int paramInt2) {
							}

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								if (AnimatedJisan.isAnimationRunning()) {
									AnimatedJisan.stopAnimation();
								}
								runAnimatedJisanLeftDefault();
							}
						});
			}
		}
	}

	private void runAnimatedJisanCopyRight() {
		AnimatedJisanCopy.setCurrentTileIndex(1);
		AnimatedJisanCopy.setVisible(true);
		if (!AnimatedJisanCopy.isAnimationRunning()) {
			long pDuration[] = new long[] { 1000, 1000 };
			int pFram[] = new int[] { 1, 0 };
			AnimatedJisanCopy.animate(pDuration, pFram, 0,
					new IAnimationListener() {
						@Override
						public void onAnimationStarted(
								AnimatedSprite paramAnimatedSprite, int paramInt) {
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite paramAnimatedSprite,
								int paramInt1, int paramInt2) {
						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite paramAnimatedSprite) {
							AnimatedJisanCopy.setVisible(false);
							isTouchJisanCopy = false;
						}
					});
		}
		if (AnimatedJisan.isAnimationRunning()) {
			AnimatedJisan.stopAnimation();
			if (!AnimatedJisan.isAnimationRunning()) {
				long pDuration[] = new long[] { 500, 500, 500, 500 };
				int pFram[] = new int[] { 8, 9, 2, 3 };
				AnimatedJisan.registerEntityModifier(new DelayModifier(1.0f,
						new IEntityModifierListener() {
							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								A18_04_KOCHI.play();
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> paramIModifier,
									IEntity paramT) {
							}
						}));
				AnimatedJisan.animate(pDuration, pFram, 0,
						new IAnimationListener() {
							@Override
							public void onAnimationStarted(
									AnimatedSprite paramAnimatedSprite,
									int paramInt) {
							}

							@Override
							public void onAnimationFrameChanged(
									AnimatedSprite paramAnimatedSprite,
									int paramInt1, int paramInt2) {
							}

							@Override
							public void onAnimationLoopFinished(
									AnimatedSprite paramAnimatedSprite,
									int paramInt1, int paramInt2) {
							}

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								if (AnimatedJisan.isAnimationRunning()) {
									AnimatedJisan.stopAnimation();
								}
								runAnimatedJisanRightMidle();
							}
						});
			}
		}
	}

	// Xu ly con
	private void runAnimatedJisanLeftAbove() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 2, 3 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanLeftDefault() {
		AnimatedJisan.setVisible(true);
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 6, 7 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanLeftMidle() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 4, 5 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanRightAbove() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 8, 9 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanRightMidle() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 10, 11 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanRightFunny() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 16, 17 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanLeftFunny() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 14, 15 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runAnimatedJisanRightBelow() {
		if (!AnimatedJisan.isAnimationRunning()) {
			long pDuration[] = new long[] { 500, 500 };
			int pFram[] = new int[] { 12, 13 };
			AnimatedJisan.animate(pDuration, pFram, -1);
		}
	}

	private void runDrawableYoushiBrown() {
		if (!AnimatedYoushiBrown.isAnimationRunning()) {
			long pDuration[] = new long[] { 250, 250 };
			int pFram[] = new int[] { 0, 1 };
			AnimatedYoushiBrown.animate(pDuration, pFram, -1);
		}
	}

	private void runDrawableYoushiGreen() {
		if (!AnimatedYoushiGreen.isAnimationRunning()) {
			long pDuration[] = new long[] { 250, 250 };
			int pFram[] = new int[] { 0, 1 };
			AnimatedYoushiGreen.animate(pDuration, pFram, -1);
		}
	}

	// Run Jisan Green
	private void pathOneJisanGreenWalk() {
		IndexYoushiGreen = 0;
		speedOneYoushiGreen = ((710f - AnimatedYoushiGreen.getX()) * tempSpeedYoushi) / 548f;
		final Path path = new Path(2).to(AnimatedYoushiGreen.getX(),
				AnimatedYoushiGreen.getY())
				.to(710f, AnimatedYoushiGreen.getY());
		AnimatedYoushiGreen.registerEntityModifier((new PathModifier(
				speedOneYoushiGreen, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						Log.d("TEST", "TT" + paramInt);

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							AnimatedYoushiGreen.setFlippedHorizontal(true);
							pathTwoJisanGreenWalk();
						}
					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance())));

	}

	private void pathTwoJisanGreenWalk() {
		speedTwoYoushiGreen = ((510f - AnimatedYoushiGreen.getY()) * tempSpeedYoushi) / 457f;
		IndexYoushiGreen = 1;
		final Path path = new Path(2).to(AnimatedYoushiGreen.getX(),
				AnimatedYoushiGreen.getY())
				.to(AnimatedYoushiGreen.getX(), 501f);
		AnimatedYoushiGreen.registerEntityModifier((new PathModifier(
				speedTwoYoushiGreen, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							pathThreeJisanGreenWalk();
						}
					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance())));

	}

	private void pathThreeJisanGreenWalk() {
		speedThreeYoushiGreen = ((AnimatedYoushiGreen.getX() - 168f) * tempSpeedYoushi) / 542f;
		IndexYoushiGreen = 2;
		final Path path = new Path(2).to(AnimatedYoushiGreen.getX(),
				AnimatedYoushiGreen.getY())
				.to(168f, AnimatedYoushiGreen.getY());
		AnimatedYoushiGreen.registerEntityModifier((new PathModifier(
				speedThreeYoushiGreen, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							AnimatedYoushiGreen.setFlippedHorizontal(false);
							pathFourJisanGreenWalk();
						}
					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {

					}
				}, EaseLinear.getInstance())));
	}

	private void pathFourJisanGreenWalk() {
		speedFourYoushiGreen = ((AnimatedYoushiGreen.getY() - 53f) * tempSpeedYoushi) / 457f;
		IndexYoushiGreen = 3;
		final Path path = new Path(2).to(AnimatedYoushiGreen.getX(),
				AnimatedYoushiGreen.getY()).to(AnimatedYoushiGreen.getX(), 53f);
		AnimatedYoushiGreen.registerEntityModifier((new PathModifier(
				speedFourYoushiGreen, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							pathOneJisanGreenWalk();
						}
					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance())));

	}

	private void JisanGreenWalkAround() {
		pathOneJisanGreenWalk();
	}

	// Run Jisan Brown
	private void pathOneJisanBrownWalk() {
		IndexYoushiBrown = 0;
		speedOneYoushiBrown = ((AnimatedYoushiBrown.getX() - 168f) * tempSpeedYoushi) / 542f;
		final Path path = new Path(2).to(AnimatedYoushiBrown.getX(),
				AnimatedYoushiBrown.getY())
				.to(168f, AnimatedYoushiBrown.getY());
		AnimatedYoushiBrown.registerEntityModifier(new PathModifier(
				speedOneYoushiBrown, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							AnimatedYoushiBrown.setFlippedHorizontal(true);
							pathTwoJisanBrownWalk();
						}

					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance()));
	}

	private void pathTwoJisanBrownWalk() {
		IndexYoushiBrown = 1;
		speedTwoYoushiBrown = ((AnimatedYoushiBrown.getY() - 53f) * tempSpeedYoushi) / 457f;
		final Path path = new Path(2).to(AnimatedYoushiBrown.getX(),
				AnimatedYoushiBrown.getY()).to(AnimatedYoushiBrown.getX(), 53f);
		AnimatedYoushiBrown.registerEntityModifier(new PathModifier(
				speedTwoYoushiBrown, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							pathThreeJisanBrownWalk();
						}

					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance()));
	}

	private void pathThreeJisanBrownWalk() {
		IndexYoushiBrown = 2;
		speedThreeYoushiBrown = ((710f - AnimatedYoushiBrown.getX()) * tempSpeedYoushi) / 548f;
		final Path path = new Path(2).to(AnimatedYoushiBrown.getX(),
				AnimatedYoushiBrown.getY())
				.to(710f, AnimatedYoushiBrown.getY());
		AnimatedYoushiBrown.registerEntityModifier(new PathModifier(
				speedThreeYoushiBrown, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							AnimatedYoushiBrown.setFlippedHorizontal(false);
							pathFourJisanBrownWalk();
						}

					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance()));
	}

	private void pathFourJisanBrownWalk() {
		IndexYoushiBrown = 3;
		speedFourYoushiBrown = ((510f - AnimatedYoushiBrown.getY()) * tempSpeedYoushi) / 457f;
		final Path path = new Path(2).to(AnimatedYoushiBrown.getX(),
				AnimatedYoushiBrown.getY())
				.to(AnimatedYoushiBrown.getX(), 501f);
		AnimatedYoushiBrown.registerEntityModifier(new PathModifier(
				speedFourYoushiBrown, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}

					@Override
					public void onPathWaypointStarted(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {

					}

					@Override
					public void onPathWaypointFinished(
							PathModifier paramPathModifier,
							IEntity paramIEntity, int paramInt) {
						if (paramInt == 0) {
							pathOneJisanBrownWalk();
						}
					}

					@Override
					public void onPathFinished(PathModifier paramPathModifier,
							IEntity paramIEntity) {
					}
				}, EaseLinear.getInstance()));
	}

	private void JisanBrownWalkAround() {
		pathOneJisanBrownWalk();
	}

	private void touchYoushiGreen() {
		AnimatedYoushiGreen.stopAnimation();
		AnimatedYoushiGreen.setCurrentTileIndex(0);
		AnimatedYoushiGreen.clearEntityModifiers();
		SequenceEntityModifier moveYoushiGreen = new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						runDrawableYoushiGreen();
						switch (IndexYoushiGreen) {
						case 0:
							pathOneJisanGreenWalk();
							break;
						case 1:
							pathTwoJisanGreenWalk();
							break;
						case 2:
							pathThreeJisanGreenWalk();
							break;
						case 3:
							pathFourJisanGreenWalk();
							break;
						}
						AnimatedYoushiGreen.registerEntityModifier(new DelayModifier(
								0.3f, new IEntityModifierListener() {
									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										TouchYoushiGreen = false;
										isTouchGimic = false;
										setTouchGimmic3(true);
									}

									@Override
									public void onModifierStarted(
											IModifier<IEntity> paramIModifier,
											IEntity paramT) {
									}
								}));

					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}
				}, new MoveModifier(0.2f, AnimatedYoushiGreen.getX(),
						AnimatedYoushiGreen.getX(), AnimatedYoushiGreen.getY(),
						AnimatedYoushiGreen.getY() - 20), new MoveModifier(
						0.2f, AnimatedYoushiGreen.getX(),
						AnimatedYoushiGreen.getX(),
						AnimatedYoushiGreen.getY() - 20,
						AnimatedYoushiGreen.getY()));
		AnimatedYoushiGreen.registerEntityModifier(moveYoushiGreen);
	}

	private void touchYoushiBrown() {
		AnimatedYoushiBrown.stopAnimation();
		AnimatedYoushiBrown.setCurrentTileIndex(0);
		AnimatedYoushiBrown.clearEntityModifiers();
		SequenceEntityModifier moveYoushiBrown = new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						runDrawableYoushiBrown();
						switch (IndexYoushiBrown) {
						case 0:
							pathOneJisanBrownWalk();
							break;
						case 1:
							pathTwoJisanBrownWalk();
							break;
						case 2:
							pathThreeJisanBrownWalk();
							break;
						case 3:
							pathFourJisanBrownWalk();
							break;
						}
						AnimatedYoushiBrown.registerEntityModifier(new DelayModifier(
								0.3f, new IEntityModifierListener() {
									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										TouchYoushiBrown = false;
										isTouchGimic = false;
										setTouchGimmic3(true);
									}

									@Override
									public void onModifierStarted(
											IModifier<IEntity> paramIModifier,
											IEntity paramT) {
									}
								}));

					}

					@Override
					public void onModifierStarted(
							IModifier<IEntity> paramIModifier, IEntity paramT) {
					}
				}, new MoveModifier(0.2f, AnimatedYoushiBrown.getX(),
						AnimatedYoushiBrown.getX(), AnimatedYoushiBrown.getY(),
						AnimatedYoushiBrown.getY() - 20), new MoveModifier(
						0.2f, AnimatedYoushiBrown.getX(),
						AnimatedYoushiBrown.getX(),
						AnimatedYoushiBrown.getY() - 20,
						AnimatedYoushiBrown.getY()));
		AnimatedYoushiBrown.registerEntityModifier(moveYoushiBrown);
	}

	// Animated Usagi A,B,C,D,E,F
	private void runAnimatedUsagiA() {
		if (!AnimatedUsagiA.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiA.animate(pDuration, pFramsleft, -1);
		}
	}

	private void runAnimatedUsagiB() {
		if (!AnimatedUsagiB.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiB.animate(pDuration, pFramsleft, -1);
		}
	}

	private void runAnimatedUsagiC() {
		if (!AnimatedUsagiC.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiC.animate(pDuration, pFramsleft, -1);
		}
	}

	private void runAnimatedUsagiD() {
		if (!AnimatedUsagiD.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiD.animate(pDuration, pFramsleft, -1);
		}
	}

	private void runAnimatedUsagiE() {
		if (!AnimatedUsagiE.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiE.animate(pDuration, pFramsleft, -1);
		}
	}

	private void runAnimatedUsagiF() {
		if (!AnimatedUsagiF.isAnimationRunning()) {
			long pDuration[] = new long[] { 200, 200 };
			int pFramsleft[] = new int[] { 1, 2 };
			AnimatedUsagiF.animate(pDuration, pFramsleft, -1);
		}
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
	}

	class MapSprite extends GenericPool<Sprite> {
		private ITextureRegion mTextureRegion;

		public MapSprite(ITextureRegion pTextureRegion) {
			this.mTextureRegion = pTextureRegion;
		}

		@Override
		protected Sprite onAllocatePoolItem() {
			final Sprite sprite = new Sprite(0, 0, this.mTextureRegion,
					Vol3Yamagoya.this.getVertexBufferObjectManager());
			sprite.setAlpha(0.0f);
			sprite.setColor(1, 0, 0);
			sprite.setBlendFunction(GL10.GL_SRC_ALPHA,
					GL10.GL_ONE_MINUS_SRC_ALPHA);
			return sprite;
		}

		@Override
		protected void onHandleRecycleItem(Sprite pItem) {
			pItem.detachSelf();
		}

	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData arg0) {
	}

	@Override
	public void onAccelerationChanged(AccelerationData arg0) {
		final Vector2 gravity = Vector2Pool.obtain(arg0.getX(), arg0.getY());
		this.mPhysicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);

	}
}