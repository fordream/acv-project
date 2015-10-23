package jp.co.xing.utaehon03.songs;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3UsagikameResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3UsagiKame extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<Object>, IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "LOG_USAGIKAME";
	private static final int RIGHT_TO_LEFT = 0, BOTTOM_TO_TOP = 1,
			LEFT_TO_RIGHT = 2, TOP_TO_BOTTOM = 3;
	private int DISTANCE_AUDIENCE_MOVE_5, DISTANCE_AUDIENCE_MOVE_6,
			DISTANCE_AUDIENCE_MOVE_7, DISTANCE_AUDIENCE_MOVE_8 = 25;
	@SuppressWarnings("unused")
	private static final int RABBIT_WIN = 0, TURTLE_WIN = 1;

	// ===========================================================
	// Fields
	// ===========================================================
	private TexturePack Vol3UsagiKamePacker_1, Vol3UsagiKamePacker_2;
        private TexturePackTextureRegionLibrary Vol3UsagiKameLibrary1,
                        Vol3UsagiKameLibrary2;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion ttrRBackground, ttrRAudienceBack05,
			ttrRAudienceFront05, ttrRAudienceBack06, ttrRAudienceFront06,
			ttrRAudienceBack07, ttrRAudienceFront07, ttrRAudienceBack08,
			ttrRAudienceFront08, ttrRDesk,
			arrTtrRFood[] = new TextureRegion[10];

	private TiledTextureRegion tiledTtrRabbit, tiledTtrTurtle, tiledTtrPanda,
			tiledTtrUsagi, tiledTtrKame;

	private Sprite sprAudienceBack05, sprAudienceFront05, sprAudienceBack06,
			sprAudienceFront06, sprAudienceBack07, sprAudienceFront07,
			sprAudienceBack08, sprAudienceFront08, sprDesk,
			arrSprFood[] = new MySprite[10];

	private AnimatedSprite anmSprRabbit, anmSprTurtle, anmSprPanda,
			anmSprUsagi, anmSprKame;

	private float rootXRabbit, rootYRabbit, rootXTurtle, rootYTurtle;
	private boolean isRabbitSleep = true, isTurtleSleep = true, isRabbitTired,
			isTurtleTired, isChangePanda, isReset = false, isWin = false,
			isStart = false, isRabbitEating = false, isTurtleEating = false,
			isRabbitOffer = false, isTurtleOffer = false,
			isAudienceTouch = true, isTouchGimic = false;
	private boolean isBooleanA = false;
	private int statusRabbit, statusTurtle, countDistanceAxisRabbit,
			countDistanceAxisTurtle, statusRabbitTemp, statusTurtleTemp;
	private int countRabbitWin = 3, countTurtleWin = 3;

	private int speedAudience05, speedAudience06, speedAudience07,
			speedAudience08 = 6;
	private int distanceMove05 = 0, distanceMove06, distanceMove07,
			distanceMove08;
	private int wayPointRabbit = -1;
	private int wayPointTurtle = -1;
	/**
	 * Handler, Runnable
	 */
	private Timer myTimer;

	@SuppressWarnings("unused")
	private LoopEntityModifier audienceBack05Modifier, audienceBack06Modifier,
			audienceBack07Modifier, audienceBack08Modifier,
			audienceFront05Modifier, audienceFront06Modifier,
			audienceFront07Modifier, audienceFront08Modifier;

	private PathModifier pathModifierRabbit, pathModifierTurtle;
	@SuppressWarnings("unused")
	private SequenceEntityModifier seqModifierRabbit;

	private float durationAudience[] = { 0.5f, 0.5f, 0.5f, 0.5f };

	/**
	 * Point Of All Food
	 */
	private float arrPointFood[][] = {
			{ 298.0f, 370.0f, 466.0f, 522.0f, 620.0f, 247.0f, 320.0f, 383.0f,
					450.0f, 594.0f },
			{ 177.0f, 195.0f, 216.0f, 242.0f, 197.0f, 279.0f, 304.0f, 283.0f,
					320.0f, 318.0f } };

	/**
	 * Array Of Bound All Food
	 */
	private float FOUR_POITER_USAGIKAME[][] = { { 96, 96, 720, 720 },
			{ 408, 8, 8, 408 } };

	private float FOUR_POITER_TURTLE[][] = { { 96, 96, 720, 720 },
			{ 404, 8, 8, 404 } };
	private boolean isFinishRabit = false;
	private boolean isFinishTurtle = false;
	/**
	 * All Sound
	 */
	private Sound oggKansei, oggStart, oggUsagiWin, oggKameWin, oggBatahah,
			oggMakenaizo, oggGanbaruzo, oggChu, oggHagu, A2_1_YANKIHON4;

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("usagikame/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("usagikame/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
                        getTextureManager(), pAssetManager, "usagikame/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
        	Vol3UsagiKamePacker_1 = mTexturePackLoaderFromSource
                            .load("Vol3UsagiKamePacker1.xml");
        	Vol3UsagiKamePacker_1.loadTexture();
                Vol3UsagiKameLibrary1 = Vol3UsagiKamePacker_1
                            .getTexturePackTextureRegionLibrary();
                
                Vol3UsagiKamePacker_2 = mTexturePackLoaderFromSource
                .load("Vol3UsagiKamePacker2.xml");
                Vol3UsagiKamePacker_2.loadTexture();
                Vol3UsagiKameLibrary2 = Vol3UsagiKamePacker_2
                .getTexturePackTextureRegionLibrary();
                
		this.ttrRBackground = Vol3UsagiKameLibrary2
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_22_IPHONE_HAIKEI_ID);

		// ////////////////////////////////////////////////////
		// Load Audience
		// ////////////////////////////////////////////////////
		this.ttrRAudienceBack05 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_05_BACK_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceFront05 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_05_FRONT_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceBack06 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_06_BACK_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceFront06 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_06_FRONT_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceBack07 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_07_BACK_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceFront07 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_07_FRONT_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceBack08 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_08_BACK_IPHONE_AUDIENCE_ID);

		this.ttrRAudienceFront08 = Vol3UsagiKameLibrary1
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker1.A2_08_FRONT_IPHONE_AUDIENCE_ID);

		// //////////////////////////////////////////////////////
		// Load Desk
		// /////////////////////////////////////////////////////
		this.ttrRDesk = Vol3UsagiKameLibrary2
				.get(Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_21_IPHONE_DESK_ID);

		// //////////////////////////////////////////////////////
		// Load Food
		// /////////////////////////////////////////////////////
		for (int i = 0; i < 10; i++) {
			this.arrTtrRFood[i] = Vol3UsagiKameLibrary1
					.get(Vol3UsagikameResource.ARR_RESOURCE_FOOD[i]);
		}
		// //////////////////////////////////////////////////////
		// Load Rabbit
		// /////////////////////////////////////////////////////
		this.tiledTtrRabbit = getTiledTextureFromPacker(Vol3UsagiKamePacker_1,
				Vol3UsagikameResource.ARR_RESOURCE_RABBIT

		);

		// //////////////////////////////////////////////////////
		// Load Turtle
		// /////////////////////////////////////////////////////
		this.tiledTtrTurtle = getTiledTextureFromPacker(Vol3UsagiKamePacker_1,
				Vol3UsagikameResource.ARR_RESOURCE_TURTLE

		);

		// //////////////////////////////////////////////////////
		// Load Panda
		// /////////////////////////////////////////////////////
		this.tiledTtrPanda = getTiledTextureFromPacker(Vol3UsagiKamePacker_1,
				Vol3UsagikameResource.ARR_RESOURCE_PANDA);

		// Load Usagi
		this.tiledTtrUsagi = getTiledTextureFromPacker(Vol3UsagiKamePacker_2,
				Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_23_1_IPHONE_USAGI_ID,
				Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_23_2_IPHONE_USAGI_ID);

		// Load Kame
		this.tiledTtrKame = getTiledTextureFromPacker(Vol3UsagiKamePacker_2,
				Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_24_1_IPHONE_KAME_ID,
				Vol3UsagikameResource.Vol3UsagiKamePacker2.A2_24_2_IPHONE_KAME_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		// Set Backgroud For Scene
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrRBackground, this
						.getVertexBufferObjectManager())));

		// ////////////////////////////////////////////////////
		// Add Desk
		// ///////////////////////////////////////////////////
		this.sprDesk = new Sprite(261, 224, this.ttrRDesk,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.sprDesk);
		this.sprAudienceBack08 = new Sprite(-23f, -126f,
				this.ttrRAudienceBack08, this.getVertexBufferObjectManager());
		this.sprAudienceFront08 = new Sprite(-23f, -126f,
				this.ttrRAudienceFront08, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprAudienceBack08);
		this.mScene.attachChild(sprAudienceFront08);
		// ////////////////////////////////////////////////////
		// Add Rabbit
		// ///////////////////////////////////////////////////
		this.anmSprRabbit = new AnimatedSprite(420, 408, this.tiledTtrRabbit,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprRabbit);

		// ////////////////////////////////////////////////////
		// Add Turtle
		// ///////////////////////////////////////////////////
		this.anmSprTurtle = new AnimatedSprite(485, 404, this.tiledTtrTurtle,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprTurtle);

		// ////////////////////////////////////////////////////
		// Add Audience
		// ////////////////////////////////////////////////////
		this.sprAudienceBack05 = new Sprite(-36f, 496.5f,
				this.ttrRAudienceBack05, this.getVertexBufferObjectManager());
		this.sprAudienceFront05 = new Sprite(-36f, 496.5f,
				this.ttrRAudienceFront05, this.getVertexBufferObjectManager());
		this.sprAudienceBack06 = new Sprite(794f, 39f, this.ttrRAudienceBack06,
				this.getVertexBufferObjectManager());
		this.sprAudienceFront06 = new Sprite(794f, 39f,
				this.ttrRAudienceFront06, this.getVertexBufferObjectManager());
		this.sprAudienceBack07 = new Sprite(-71f, 46.5f,
				this.ttrRAudienceBack07, this.getVertexBufferObjectManager());
		this.sprAudienceFront07 = new Sprite(-71f, 46.5f,
				this.ttrRAudienceFront07, this.getVertexBufferObjectManager());

		this.mScene.attachChild(sprAudienceBack05);
		this.mScene.attachChild(sprAudienceFront05);
		this.mScene.attachChild(sprAudienceBack06);
		this.mScene.attachChild(sprAudienceFront06);
		this.mScene.attachChild(sprAudienceBack07);
		this.mScene.attachChild(sprAudienceFront07);

		// ////////////////////////////////////////////////////
		// Add Food
		// ///////////////////////////////////////////////////
		for (int i = 0; i < 10; i++) {
			this.arrSprFood[i] = new MySprite(arrPointFood[0][i],
					arrPointFood[1][i], this.arrTtrRFood[i], i,
					getVertexBufferObjectManager());
			this.mScene.attachChild(this.arrSprFood[i]);
			this.mScene.registerTouchArea(arrSprFood[i]);
		}

		// ////////////////////////////////////////////////////
		// Add Panda
		// ///////////////////////////////////////////////////
		this.anmSprPanda = new AnimatedSprite(398, 488, this.tiledTtrPanda,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprPanda);

		// ////////////////////////////////////////////////////
		// Add Usagi
		// ///////////////////////////////////////////////////
		this.anmSprUsagi = new AnimatedSprite(227, 60, this.tiledTtrUsagi,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprUsagi);
		this.anmSprUsagi.setVisible(false);

		// ////////////////////////////////////////////////////
		// Add Kame
		// ///////////////////////////////////////////////////
		this.anmSprKame = new AnimatedSprite(227, 60, this.tiledTtrKame,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(this.anmSprKame);
		this.anmSprKame.setVisible(false);

		// Add Three Button Gimmic
		addGimmicsButton(mScene, Vol3UsagikameResource.SOUND_GIMMIC_KIRATO,
				Vol3UsagikameResource.IMAGE_GIMMIC_KIRANTO,
				Vol3UsagiKameLibrary2);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		oggKansei = loadSoundResourceFromSD(Vol3UsagikameResource.A2_05_08_KANSEI);
		oggStart = loadSoundResourceFromSD(Vol3UsagikameResource.A2_09_10_START);
		oggUsagiWin = loadSoundResourceFromSD(Vol3UsagikameResource.A2_23_USAGIWIN);
		oggKameWin = loadSoundResourceFromSD(Vol3UsagikameResource.A2_24_KAMEWIN);
		oggBatahah = loadSoundResourceFromSD(Vol3UsagikameResource.A2_09_10_BATAHAH);
		oggMakenaizo = loadSoundResourceFromSD(Vol3UsagikameResource.A2_09_MAKENAIZO);
		oggGanbaruzo = loadSoundResourceFromSD(Vol3UsagikameResource.A2_10_GANBARUZO);
		oggChu = loadSoundResourceFromSD(Vol3UsagikameResource.A2_11_15_CHU);
		oggHagu = loadSoundResourceFromSD(Vol3UsagikameResource.A2_12_14_16_20_HAGU);
		A2_1_YANKIHON4 = loadSoundResourceFromSD(Vol3UsagikameResource.A2_1_YANKIHON4);
	}

	@Override
	protected void loadKaraokeComplete() {
		// TODO Auto-generated method stub

		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		rootXRabbit = anmSprRabbit.getX();
		rootYRabbit = anmSprRabbit.getY();
		rootXTurtle = anmSprTurtle.getX();
		rootYTurtle = anmSprTurtle.getY();
		statusRabbit = RIGHT_TO_LEFT;
		statusTurtle = RIGHT_TO_LEFT;
		countDistanceAxisRabbit = 0;
		countDistanceAxisTurtle = 0;
		countRabbitWin = 3;
		countTurtleWin = 3;
		isChangePanda = false;
		isRabbitTired = false;
		isTurtleTired = false;
		isReset = false;
		isWin = false;
		isStart = false;
		isBooleanA = false;
		isRabbitEating = false;
		isTurtleEating = false;
		statusRabbitTemp = statusRabbit;
		statusTurtleTemp = statusTurtle;
		speedAudience05 = 6;
		speedAudience08 = 3;
		distanceMove05 = 0;
		distanceMove08 = 0;
		speedAudience06 = 3;
		speedAudience07 = 3;
		distanceMove06 = 0;
		distanceMove07 = 0;

		DISTANCE_AUDIENCE_MOVE_5 = 25;
		DISTANCE_AUDIENCE_MOVE_6 = 20;
		DISTANCE_AUDIENCE_MOVE_7 = 20;
		DISTANCE_AUDIENCE_MOVE_8 = 25;
		myTimer = new Timer();
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (!isFinishRabit && !isFinishTurtle) {
					if (!isWin) {
						if (anmSprRabbit.getX() == FOUR_POITER_USAGIKAME[0][0]
						/* && anmSprRabbit.getY() == FOUR_POITER_USAGIKAME[1][0] */) {
							// Log.d(TAG,
							// "________________COME HERE__________________" +
							// anmSprRabbit.getY());
							anmSprRabbit.setFlippedHorizontal(true);
						}
						if (anmSprRabbit.getX() == FOUR_POITER_USAGIKAME[0][2]
						/* && anmSprRabbit.getY() == FOUR_POITER_USAGIKAME[1][2] */) {
							anmSprRabbit.setFlippedHorizontal(false);
						}

						if (anmSprTurtle.getX() == FOUR_POITER_USAGIKAME[0][0]
						/* && anmSprTurtle.getY() == FOUR_POITER_USAGIKAME[1][0] */) {
							anmSprTurtle.setFlippedHorizontal(true);
						}
						if (anmSprTurtle.getX() == FOUR_POITER_USAGIKAME[0][2]
						/* && anmSprTurtle.getY() == FOUR_POITER_USAGIKAME[1][2] */) {
							anmSprTurtle.setFlippedHorizontal(false);
						}

						// Log.d(TAG,
						// "_________________COME HERE________________" +
						// anmSprRabbit.getX());
						// if (anmSprRabbit.getX() == 421
						// && anmSprRabbit.getY() == 408) {
						// countRabbitWin -= 1;
						// isChangePanda = false;
						// }
						// if (anmSprTurtle.getX() == 486
						// && anmSprTurtle.getY() == 404) {
						// countTurtleWin -= 1;
						// isChangePanda = false;
						// }
						if (!isReset) {
							if (pathModifierRabbit != null
									&& pathModifierRabbit.isFinished()) {
								anmSprRabbit.clearEntityModifiers();
								if (isRabbitSleep) {
									isRabbitSleep = false;
									oggBatahah.play();
									long durations[] = { 300, 300 };
									int frames[] = { 7, 8 };
									anmSprRabbit.animate(durations, frames, -1);
								}
							}

							if (pathModifierTurtle != null
									&& pathModifierTurtle.isFinished()) {
								anmSprTurtle.clearEntityModifiers();
								if (isTurtleSleep) {
									isTurtleSleep = false;
									anmSprTurtle.stopAnimation();
									oggBatahah.play();
									long durations[] = { 300, 300 };
									int frames[] = { 7, 8 };
									anmSprTurtle.animate(durations, frames, -1);
								}
							}

							if (statusRabbit == RIGHT_TO_LEFT
									&& anmSprRabbit.getX() == FOUR_POITER_USAGIKAME[0][0]) {
								Log.d(TAG,
										"_________________COME HERE RABBIT__________________"
												+ BOTTOM_TO_TOP);
								statusRabbit = BOTTOM_TO_TOP;
								countDistanceAxisRabbit += 1;
							}
							if (statusRabbit == BOTTOM_TO_TOP
									&& anmSprRabbit.getY() == FOUR_POITER_USAGIKAME[1][1]) {
								Log.d(TAG,
										"_________________COME HERE RABBIT__________________"
												+ LEFT_TO_RIGHT);
								statusRabbit = LEFT_TO_RIGHT;
								countDistanceAxisRabbit += 1;
							}
							if (statusRabbit == LEFT_TO_RIGHT
									&& anmSprRabbit.getX() == FOUR_POITER_USAGIKAME[0][2]) {
								Log.d(TAG,
										"_________________COME HERE RABBIT__________________"
												+ TOP_TO_BOTTOM);
								statusRabbit = TOP_TO_BOTTOM;
								countDistanceAxisRabbit += 1;
							}
							if (statusRabbit == TOP_TO_BOTTOM
									&& anmSprRabbit.getY() == FOUR_POITER_USAGIKAME[1][0]) {
								Log.d(TAG,
										"_________________COME HERE RABBIT__________________"
												+ RIGHT_TO_LEFT);
								statusRabbit = RIGHT_TO_LEFT;
								countDistanceAxisRabbit += 1;
							}

							if (statusTurtle == RIGHT_TO_LEFT
									&& anmSprTurtle.getX() == FOUR_POITER_TURTLE[0][0]) {
								Log.d(TAG,
										"_________________COME HERE TURTLE__________________"
												+ BOTTOM_TO_TOP);
								statusTurtle = BOTTOM_TO_TOP;
								countDistanceAxisTurtle += 1;
							}
							if (statusTurtle == BOTTOM_TO_TOP
									&& anmSprTurtle.getY() == FOUR_POITER_TURTLE[1][1]) {
								Log.d(TAG,
										"_________________COME HERE TURTLE__________________"
												+ LEFT_TO_RIGHT);
								statusTurtle = LEFT_TO_RIGHT;
								countDistanceAxisTurtle += 1;
							}
							if (statusTurtle == LEFT_TO_RIGHT
									&& anmSprTurtle.getX() == FOUR_POITER_TURTLE[0][2]) {
								Log.d(TAG,
										"_________________COME HERE TURTLE__________________"
												+ TOP_TO_BOTTOM);
								statusTurtle = TOP_TO_BOTTOM;
								countDistanceAxisTurtle += 1;
							}
							if (statusTurtle == TOP_TO_BOTTOM
									&& anmSprTurtle.getY() == FOUR_POITER_TURTLE[1][0]) {
								Log.d(TAG,
										"_________________COME HERE TURTLE__________________"
												+ RIGHT_TO_LEFT);
								statusTurtle = RIGHT_TO_LEFT;
								countDistanceAxisTurtle += 1;
							}

							if (countDistanceAxisRabbit == 3) {
								if (!isRabbitTired) {
									isRabbitTired = true;
									long durations[] = { 300, 300 };
									int frames[] = { 2, 3 };
									anmSprRabbit.animate(durations, frames, -1);
								}
							}

							if (countDistanceAxisTurtle == 3) {
								if (!isTurtleTired) {
									isTurtleTired = true;
									long durations[] = { 300, 300 };
									int frames[] = { 2, 3 };
									anmSprTurtle.animate(durations, frames, -1);
								}
							}
						}
					}
					if (statusRabbit == RIGHT_TO_LEFT
							|| statusTurtle == RIGHT_TO_LEFT) {
						speedAudience05 = (speedAudience05 / Math
								.abs(speedAudience05)) * 6;

					} else {
						speedAudience05 = (speedAudience05 / Math
								.abs(speedAudience05)) * 3;
					}
					if (statusRabbit == TOP_TO_BOTTOM
							|| statusTurtle == TOP_TO_BOTTOM) {
						speedAudience06 = (speedAudience06 / Math
								.abs(speedAudience06)) * 6;

					} else {
						speedAudience06 = (speedAudience06 / Math
								.abs(speedAudience06)) * 3;
					}
					if (statusRabbit == LEFT_TO_RIGHT
							|| statusTurtle == LEFT_TO_RIGHT) {
						speedAudience08 = (speedAudience08 / Math
								.abs(speedAudience08)) * 6;

					} else {
						speedAudience08 = (speedAudience08 / Math
								.abs(speedAudience08)) * 3;
					}
					if (statusRabbit == BOTTOM_TO_TOP
							|| statusTurtle == BOTTOM_TO_TOP) {
						speedAudience07 = (speedAudience07 / Math
								.abs(speedAudience07)) * 6;

					} else {
						speedAudience07 = (speedAudience07 / Math
								.abs(speedAudience07)) * 3;
					}
				}
			}
		});
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				modifiAudience(durationAudience);
			}
		}, 0, 50);
		sprAudienceFront07.clearEntityModifiers();
		sprAudienceFront07.registerEntityModifier(new DelayModifier(3.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						start();
					}
				}));
		// TODO Auto-generated method stub
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		if (anmSprRabbit != null) {
			anmSprRabbit.stopAnimation();
			anmSprRabbit.setCurrentTileIndex(0);
			anmSprRabbit.clearEntityModifiers();
			anmSprRabbit.setPosition(420, 408);
			rootXRabbit = anmSprRabbit.getX();
			rootYRabbit = anmSprRabbit.getY();
			anmSprRabbit.setFlippedHorizontal(false);
			anmSprRabbit.clearUpdateHandlers();
		}
		if (anmSprTurtle != null) {
			anmSprTurtle.stopAnimation();
			anmSprTurtle.setCurrentTileIndex(0);
			anmSprTurtle.clearEntityModifiers();
			anmSprTurtle.setPosition(485, 404);
			rootXTurtle = anmSprTurtle.getX();
			rootYTurtle = anmSprTurtle.getY();
			anmSprTurtle.setFlippedHorizontal(false);
		}
		if (anmSprUsagi != null) {
			anmSprUsagi.setVisible(false);
		}
		if (anmSprKame != null) {
			anmSprKame.setVisible(false);
		}

		if (myTimer != null) {
			myTimer.cancel();
			myTimer = null;
		}

		if (this.sprAudienceBack05 != null) {
			this.sprAudienceBack05.clearEntityModifiers();
			sprAudienceBack05.setPosition(-36f, 496.5f);
		}
		if (this.sprAudienceFront05 != null) {
			this.sprAudienceFront05.clearEntityModifiers();
			sprAudienceFront05.setPosition(-36f, 496.5f);
		}
		if (this.sprAudienceBack06 != null) {
			this.sprAudienceBack06.clearEntityModifiers();
			this.sprAudienceBack06.setPosition(794f, 39f);
		}
		if (this.sprAudienceFront06 != null) {
			this.sprAudienceFront06.clearEntityModifiers();
			this.sprAudienceFront06.setPosition(794f, 39f);
		}
		if (this.sprAudienceBack07 != null) {
			this.sprAudienceBack07.clearEntityModifiers();
			this.sprAudienceBack07.setPosition(-71f, 46.5f);
		}
		if (this.sprAudienceFront07 != null) {
			this.sprAudienceFront07.clearEntityModifiers();
			this.sprAudienceFront07.setPosition(-71f, 46.5f);
		}
		if (this.sprAudienceBack08 != null) {
			this.sprAudienceBack08.clearEntityModifiers();
			this.sprAudienceBack08.setPosition(-23f, -126f);
		}
		if (this.sprAudienceFront08 != null) {
			this.sprAudienceFront08.clearEntityModifiers();
			this.sprAudienceFront08.setPosition(-23f, -126f);
		}
		if(this.mScene!=null){
		    this.mScene.clearEntityModifiers();
		}
		isBooleanA = false;
		isWin = true;
		isReset = false;
		statusRabbit = RIGHT_TO_LEFT;
		statusTurtle = RIGHT_TO_LEFT;
		statusRabbitTemp = statusRabbit;
		statusTurtleTemp = statusTurtle;
		countDistanceAxisRabbit = 0;
		countDistanceAxisTurtle = 0;
		isRabbitSleep = false;
		isTurtleSleep = false;
		isRabbitTired = false;
		isTurtleTired = false;
		isTurtleOffer = false;
		isRabbitOffer = false;
		isRabbitEating = false;
		isTurtleEating = false;
		countRabbitWin = 3;
		countTurtleWin = 3;
		isTouchGimic = false; 
		isFinishRabit = false;
		isFinishTurtle = false;
		isAudienceTouch = true;
		super.onPauseGame();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (anmSprRabbit.contains(pX, pY)) {

				rabbitTouch();
			} else if (anmSprTurtle.contains(pX, pY)) {

				turtleTouch();
			} else if (sprAudienceBack05.contains(pX, pY)) {
				audienceTouch();
			} else if (sprAudienceBack06.contains(pX, pY)) {
				audienceTouch();
			} else if (sprAudienceBack07.contains(pX, pY)) {
				audienceTouch();
			} else if (sprAudienceBack08.contains(pX, pY)) {
				audienceTouch();
			}

		}
		return false;
	}

	private void audienceTouch() {
		if (isAudienceTouch) {
			oggKansei.play();
			isAudienceTouch = false;
			rabbitTouch();
			turtleTouch();
			isRabbitOffer = true;
			isTurtleOffer = true;
			sprAudienceBack05.clearEntityModifiers();
			sprAudienceBack05.registerEntityModifier(new DelayModifier(3.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isAudienceTouch = true;
							isRabbitOffer = false;
							isTurtleOffer = false;
						}
					}));
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		 	A2_1_YANKIHON4.play();
	        if(!isTouchGimic && (!isTurtleEating && isTurtleSleep && !isTurtleOffer
                        && anmSprTurtle.getCurrentTileIndex() != 2
                        && anmSprTurtle.getCurrentTileIndex() != 3 || !isRabbitEating && isRabbitSleep && !isRabbitOffer
                        && anmSprRabbit.getCurrentTileIndex() != 2
                        && anmSprRabbit.getCurrentTileIndex() != 3)){
        	        isTouchGimic = true;
        	       
        		rabbitTouch();
        		turtleTouch();
        		this.mScene.registerEntityModifier(new DelayModifier(0.1f, new IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                            }
                            @Override
                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                isTouchGimic = false;
                            }
                        }));
	        }
	}

	@Override
	public void onModifierFinished(
			@SuppressWarnings("rawtypes") IModifier pModifier, Object pItem) {

	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(anmSprUsagi)
				|| pAnimatedSprite.equals(anmSprKame)) {
			anmSprUsagi.setVisible(false);
			anmSprKame.setVisible(false);
			start();
		}
	}

	// ////////////////////////////////////////////////////////////
	// Audience Modifier
	// ///////////////////////////////////////////////////////////
	private void modifiAudience(float durations[]) {

		this.sprAudienceBack05.setPosition(this.sprAudienceBack05.getX()
				+ speedAudience05, this.sprAudienceBack05.getY());
		this.sprAudienceFront05.setPosition(this.sprAudienceFront05.getX()
				- speedAudience05, this.sprAudienceFront05.getY());
		distanceMove05 += speedAudience05;
		if (distanceMove05 >= DISTANCE_AUDIENCE_MOVE_5
				|| distanceMove05 <= -DISTANCE_AUDIENCE_MOVE_5) {
			distanceMove05 = 0;
			DISTANCE_AUDIENCE_MOVE_5 = 50;
			speedAudience05 *= -1;
		}

		this.sprAudienceBack08.setPosition(this.sprAudienceBack08.getX()
				- speedAudience08, this.sprAudienceBack08.getY());
		this.sprAudienceFront08.setPosition(this.sprAudienceFront08.getX()
				+ speedAudience08, this.sprAudienceFront08.getY());
		distanceMove08 += speedAudience08;
		if (distanceMove08 >= DISTANCE_AUDIENCE_MOVE_8
				|| distanceMove08 <= -DISTANCE_AUDIENCE_MOVE_8) {
			distanceMove08 = 0;
			DISTANCE_AUDIENCE_MOVE_8 = 50;
			speedAudience08 *= -1;
		}

		this.sprAudienceBack06.setPosition(this.sprAudienceBack06.getX(),
				this.sprAudienceBack06.getY() - speedAudience06);
		this.sprAudienceFront06.setPosition(this.sprAudienceFront06.getX(),
				this.sprAudienceBack06.getY() + speedAudience06);
		distanceMove06 += speedAudience06;
		if (distanceMove06 >= DISTANCE_AUDIENCE_MOVE_6
				|| distanceMove06 <= -DISTANCE_AUDIENCE_MOVE_6) {
			distanceMove06 = 0;
			DISTANCE_AUDIENCE_MOVE_6 = 40;
			speedAudience06 *= -1;
		}

		this.sprAudienceBack07.setPosition(this.sprAudienceBack07.getX(),
				this.sprAudienceBack07.getY() + speedAudience07);
		this.sprAudienceFront07.setPosition(this.sprAudienceFront07.getX(),
				this.sprAudienceFront07.getY() - speedAudience07);
		distanceMove07 += speedAudience07;
		if (distanceMove07 >= DISTANCE_AUDIENCE_MOVE_7
				|| distanceMove07 <= -DISTANCE_AUDIENCE_MOVE_7) {
			distanceMove07 = 0;
			DISTANCE_AUDIENCE_MOVE_7 = 40;
			speedAudience07 *= -1;
		}

		// sprAudienceBack05.registerUpdateHandler()
	}

	private void modifiRabbit(float duration) {
		isRabbitSleep = true;
		anmSprRabbit.clearEntityModifiers();
		long durations[] = { 200, 200 };
		int frames[] = { 0, 1 };
		anmSprRabbit.stopAnimation();
		anmSprRabbit.animate(durations, frames, -1);
		Path pathRabbit = new Path(0);
		Log.d(TAG, statusRabbit + "");

		if (anmSprRabbit.getX() == 421) {
			anmSprRabbit.setPosition(422, 408);
		}
		if (countRabbitWin != 0 && !isFinishRabit && !isFinishTurtle) {
			if (statusRabbit == statusRabbitTemp) {

				if (statusRabbit == RIGHT_TO_LEFT) {

					if (anmSprRabbit.getX() <= 420) {

						if (rootXRabbit <= 420) {
							wayPointRabbit = 4;
							pathRabbit = new Path(7)
									.to(anmSprRabbit.getX(),
											anmSprRabbit.getY())
									.to(FOUR_POITER_USAGIKAME[0][0],
											FOUR_POITER_USAGIKAME[1][0])
									.to(FOUR_POITER_USAGIKAME[0][1],
											FOUR_POITER_USAGIKAME[1][1])
									.to(FOUR_POITER_USAGIKAME[0][2],
											FOUR_POITER_USAGIKAME[1][2])
									.to(FOUR_POITER_USAGIKAME[0][3],
											FOUR_POITER_USAGIKAME[1][3])
									.to(421, 408).to(rootXRabbit, rootYRabbit);
						} else {
							wayPointRabbit = -1;
							pathRabbit = new Path(6)
									.to(anmSprRabbit.getX(),
											anmSprRabbit.getY())
									.to(FOUR_POITER_USAGIKAME[0][0],
											FOUR_POITER_USAGIKAME[1][0])
									.to(FOUR_POITER_USAGIKAME[0][1],
											FOUR_POITER_USAGIKAME[1][1])
									.to(FOUR_POITER_USAGIKAME[0][2],
											FOUR_POITER_USAGIKAME[1][2])
									.to(FOUR_POITER_USAGIKAME[0][3],
											FOUR_POITER_USAGIKAME[1][3])
									.to(rootXRabbit, rootYRabbit);
						}
					} else {
						Log.d(TAG, "Vao day roi");
						wayPointRabbit = 0;
						pathRabbit = new Path(7)
								.to(anmSprRabbit.getX(), anmSprRabbit.getY())
								.to(421, 408)
								.to(FOUR_POITER_USAGIKAME[0][0],
										FOUR_POITER_USAGIKAME[1][0])
								.to(FOUR_POITER_USAGIKAME[0][1],
										FOUR_POITER_USAGIKAME[1][1])
								.to(FOUR_POITER_USAGIKAME[0][2],
										FOUR_POITER_USAGIKAME[1][2])
								.to(FOUR_POITER_USAGIKAME[0][3],
										FOUR_POITER_USAGIKAME[1][3])
								.to(rootXRabbit, rootYRabbit);
					}
				} else if (statusRabbit == BOTTOM_TO_TOP) {
					wayPointRabbit = 3;
					pathRabbit = new Path(7)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][1],
									FOUR_POITER_USAGIKAME[1][1])
							.to(FOUR_POITER_USAGIKAME[0][2],
									FOUR_POITER_USAGIKAME[1][2])
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3])
							.to(421, 408)
							.to(FOUR_POITER_USAGIKAME[0][0],
									FOUR_POITER_USAGIKAME[1][0])
							.to(rootXRabbit, rootYRabbit);
				} else if (statusRabbit == LEFT_TO_RIGHT) {
					wayPointRabbit = 2;
					pathRabbit = new Path(7)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][2],
									FOUR_POITER_USAGIKAME[1][2])
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3])
							.to(421, 408)
							.to(FOUR_POITER_USAGIKAME[0][0],
									FOUR_POITER_USAGIKAME[1][0])
							.to(FOUR_POITER_USAGIKAME[0][1],
									FOUR_POITER_USAGIKAME[1][1])
							.to(rootXRabbit, rootYRabbit);
				} else if (statusRabbit == TOP_TO_BOTTOM) {
					wayPointRabbit = 1;
					pathRabbit = new Path(7)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3])
							.to(421, 408)
							.to(FOUR_POITER_USAGIKAME[0][0],
									FOUR_POITER_USAGIKAME[1][0])
							.to(FOUR_POITER_USAGIKAME[0][1],
									FOUR_POITER_USAGIKAME[1][1])
							.to(FOUR_POITER_USAGIKAME[0][2],
									FOUR_POITER_USAGIKAME[1][2])
							.to(rootXRabbit, rootYRabbit);
				}
			} else {
				if (statusRabbit == RIGHT_TO_LEFT) {
					if (anmSprRabbit.getX() <= 420) {
						wayPointRabbit = -1;
						pathRabbit = new Path(5)
								.to(anmSprRabbit.getX(), anmSprRabbit.getY())
								.to(FOUR_POITER_USAGIKAME[0][0],
										FOUR_POITER_USAGIKAME[1][0])
								.to(FOUR_POITER_USAGIKAME[0][1],
										FOUR_POITER_USAGIKAME[1][1])
								.to(FOUR_POITER_USAGIKAME[0][2],
										FOUR_POITER_USAGIKAME[1][2])
								.to(rootXRabbit, rootYRabbit);
					} else {
						wayPointRabbit = 1;
						pathRabbit = new Path(6)
								.to(anmSprRabbit.getX(), anmSprRabbit.getY())
								.to(421, 408)
								.to(FOUR_POITER_USAGIKAME[0][0],
										FOUR_POITER_USAGIKAME[1][0])
								.to(FOUR_POITER_USAGIKAME[0][1],
										FOUR_POITER_USAGIKAME[1][1])
								.to(FOUR_POITER_USAGIKAME[0][2],
										FOUR_POITER_USAGIKAME[1][2])
								.to(rootXRabbit, rootYRabbit);
					}
				} else if (statusRabbit == BOTTOM_TO_TOP) {
					wayPointRabbit = 3;
					pathRabbit = new Path(6)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][1],
									FOUR_POITER_USAGIKAME[1][1])
							.to(FOUR_POITER_USAGIKAME[0][2],
									FOUR_POITER_USAGIKAME[1][2])
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3]).to(421, 408)
							.to(rootXRabbit, rootYRabbit);
				} else if (statusRabbit == LEFT_TO_RIGHT) {
					wayPointRabbit = 2;
					pathRabbit = new Path(6)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][2],
									FOUR_POITER_USAGIKAME[1][2])
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3])
							.to(421, 408)
							.to(FOUR_POITER_USAGIKAME[0][0],
									FOUR_POITER_USAGIKAME[1][0])
							.to(rootXRabbit, rootYRabbit);
				} else if (statusRabbit == TOP_TO_BOTTOM) {
					wayPointRabbit = 1;
					pathRabbit = new Path(6)
							.to(anmSprRabbit.getX(), anmSprRabbit.getY())
							.to(FOUR_POITER_USAGIKAME[0][3],
									FOUR_POITER_USAGIKAME[1][3])
							.to(421, 408)
							.to(FOUR_POITER_USAGIKAME[0][0],
									FOUR_POITER_USAGIKAME[1][0])
							.to(FOUR_POITER_USAGIKAME[0][1],
									FOUR_POITER_USAGIKAME[1][1])
							.to(rootXRabbit, rootYRabbit);
				}
			}
		}
		pathModifierRabbit = new PathModifier(duration, pathRabbit,
				new IPathModifierListener() {

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {

					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						Log.d("Xem xem thenao Rabbit", "Day roi" + arg2);
						if (arg2 == wayPointRabbit && !isFinishTurtle
								&& !isFinishTurtle) {
							Log.d(TAG, "_____________LOG WAY POINT____________"
									+ wayPointRabbit);
							countRabbitWin--;
							isChangePanda = false;
							if (!isChangePanda) {
								if (!isFinishRabit && !isFinishTurtle) {
									if (countRabbitWin < countTurtleWin) {
										animatePanda(countRabbitWin);
									}
								}
							}
							if (countRabbitWin == 0 && !isFinishTurtle) {
                                isFinishRabit = true;
                                isReset = true;
								runOnUpdateThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        animateWin(0);
                                                                    }
                                                                });
								
							}
						}
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {

					}

					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {

					}
				});
		anmSprRabbit.registerEntityModifier(pathModifierRabbit);
	}

	// //////////////////////////////////////////////////////////////
	// Rabbit Eat
	// //////////////////////////////////////////////////////////////
	private void rabbitEat(final int indexFood) {

		if (!isRabbitEating) {
			countDistanceAxisRabbit = 0;
			statusRabbitTemp = statusRabbit;

			rootXRabbit = anmSprRabbit.getX();
			rootYRabbit = anmSprRabbit.getY();

			isRabbitEating = true;
			long durations[] = { 200, 200 };
			if (!isRabbitSleep) {

				int frames[] = { 9, 10 };
				anmSprRabbit.stopAnimation();
				anmSprRabbit.animate(durations, frames, 1, this);
			} else {
				if (pathModifierRabbit != null) {
					modifiRabbit(pathModifierRabbit.getDuration());
				}
				int frames[] = { 4, 5 };
				anmSprRabbit.stopAnimation();
				anmSprRabbit.animate(durations, frames, 1, this);
			}
			isRabbitSleep = false;
			sprAudienceFront05.clearEntityModifiers();
			sprAudienceFront05.registerEntityModifier(new DelayModifier(0.75f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isRabbitTired = false;
							if (indexFood == 0 || indexFood == 2
									|| indexFood == 3 || indexFood == 4
									|| indexFood == 5) {
								modifiRabbit(6);
							} else {
								modifiRabbit(9);
							}
							isRabbitEating = false;
						}
					}));
		}
		return;
	}

	private void modifiTurtle(float duration) {
		isTurtleSleep = true;
		anmSprTurtle.clearEntityModifiers();
		long durations[] = { 200, 200 };
		int frames[] = { 0, 1 };
		anmSprTurtle.stopAnimation();
		anmSprTurtle.animate(durations, frames, -1);
		Path pathTurtle = new Path(0);
		Log.d(TAG, statusTurtle + "");

		if (anmSprTurtle.getX() == 486) {
			anmSprTurtle.setPosition(487, 404);
		}
		if (countTurtleWin != 0 && !isFinishRabit && !isFinishTurtle) {
			if (statusTurtle == statusTurtleTemp) {

				if (statusTurtle == RIGHT_TO_LEFT) {
					if (anmSprTurtle.getX() <= 485) {

						if (rootXTurtle <= 485) {
							wayPointTurtle = 4;
							pathTurtle = new Path(7)
									.to(anmSprTurtle.getX(),
											anmSprTurtle.getY())
									.to(FOUR_POITER_TURTLE[0][0],
											FOUR_POITER_TURTLE[1][0])
									.to(FOUR_POITER_TURTLE[0][1],
											FOUR_POITER_TURTLE[1][1])
									.to(FOUR_POITER_TURTLE[0][2],
											FOUR_POITER_TURTLE[1][2])
									.to(FOUR_POITER_TURTLE[0][3],
											FOUR_POITER_TURTLE[1][3])
									.to(486, 404).to(rootXTurtle, rootYTurtle);
						} else {
							wayPointTurtle = -1;
							pathTurtle = new Path(6)
									.to(anmSprTurtle.getX(),
											anmSprTurtle.getY())
									.to(FOUR_POITER_TURTLE[0][0],
											FOUR_POITER_TURTLE[1][0])
									.to(FOUR_POITER_TURTLE[0][1],
											FOUR_POITER_TURTLE[1][1])
									.to(FOUR_POITER_TURTLE[0][2],
											FOUR_POITER_TURTLE[1][2])
									.to(FOUR_POITER_TURTLE[0][3],
											FOUR_POITER_TURTLE[1][3])
									.to(rootXTurtle, rootYTurtle);
						}

					} else {
						Log.d(TAG, "Vao day roi");
						wayPointTurtle = 0;
						pathTurtle = new Path(7)
								.to(anmSprTurtle.getX(), anmSprTurtle.getY())
								.to(486, 404)
								.to(FOUR_POITER_TURTLE[0][0],
										FOUR_POITER_TURTLE[1][0])
								.to(FOUR_POITER_TURTLE[0][1],
										FOUR_POITER_TURTLE[1][1])
								.to(FOUR_POITER_TURTLE[0][2],
										FOUR_POITER_TURTLE[1][2])
								.to(FOUR_POITER_TURTLE[0][3],
										FOUR_POITER_TURTLE[1][3])
								.to(rootXTurtle, rootYTurtle);
					}
				} else if (statusTurtle == BOTTOM_TO_TOP) {
					wayPointTurtle = 3;
					pathTurtle = new Path(7)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][1],
									FOUR_POITER_TURTLE[1][1])
							.to(FOUR_POITER_TURTLE[0][2],
									FOUR_POITER_TURTLE[1][2])
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3])
							.to(486, 404)
							.to(FOUR_POITER_TURTLE[0][0],
									FOUR_POITER_TURTLE[1][0])
							.to(rootXTurtle, rootYTurtle);
				} else if (statusTurtle == LEFT_TO_RIGHT) {
					wayPointTurtle = 2;
					pathTurtle = new Path(7)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][2],
									FOUR_POITER_TURTLE[1][2])
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3])
							.to(486, 404)
							.to(FOUR_POITER_TURTLE[0][0],
									FOUR_POITER_TURTLE[1][0])
							.to(FOUR_POITER_TURTLE[0][1],
									FOUR_POITER_TURTLE[1][1])
							.to(rootXTurtle, rootYTurtle);
				} else if (statusTurtle == TOP_TO_BOTTOM) {
					wayPointTurtle = 1;
					pathTurtle = new Path(7)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3])
							.to(486, 404)
							.to(FOUR_POITER_TURTLE[0][0],
									FOUR_POITER_TURTLE[1][0])
							.to(FOUR_POITER_TURTLE[0][1],
									FOUR_POITER_TURTLE[1][1])
							.to(FOUR_POITER_TURTLE[0][2],
									FOUR_POITER_TURTLE[1][2])
							.to(rootXTurtle, rootYTurtle);
				}
			} else {
				if (statusTurtle == RIGHT_TO_LEFT) {
					if (anmSprTurtle.getX() <= 485) {
						wayPointTurtle = -1;
						pathTurtle = new Path(5)
								.to(anmSprTurtle.getX(), anmSprTurtle.getY())
								.to(FOUR_POITER_TURTLE[0][0],
										FOUR_POITER_TURTLE[1][0])
								.to(FOUR_POITER_TURTLE[0][1],
										FOUR_POITER_TURTLE[1][1])
								.to(FOUR_POITER_TURTLE[0][2],
										FOUR_POITER_TURTLE[1][2])
								.to(rootXTurtle, rootYTurtle);
					} else {
						wayPointTurtle = 0;
						pathTurtle = new Path(6)
								.to(anmSprTurtle.getX(), anmSprTurtle.getY())
								.to(486, 404)
								.to(FOUR_POITER_TURTLE[0][0],
										FOUR_POITER_TURTLE[1][0])
								.to(FOUR_POITER_TURTLE[0][1],
										FOUR_POITER_TURTLE[1][1])
								.to(FOUR_POITER_TURTLE[0][2],
										FOUR_POITER_TURTLE[1][2])
								.to(rootXTurtle, rootYTurtle);
					}
				} else if (statusTurtle == BOTTOM_TO_TOP) {
					wayPointTurtle = 3;
					pathTurtle = new Path(6)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][1],
									FOUR_POITER_TURTLE[1][1])
							.to(FOUR_POITER_TURTLE[0][2],
									FOUR_POITER_TURTLE[1][2])
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3]).to(486, 404)
							.to(rootXTurtle, rootYTurtle);
				} else if (statusTurtle == LEFT_TO_RIGHT) {
					wayPointTurtle = 2;
					pathTurtle = new Path(6)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][2],
									FOUR_POITER_TURTLE[1][2])
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3])
							.to(486, 404)
							.to(FOUR_POITER_TURTLE[0][0],
									FOUR_POITER_TURTLE[1][0])
							.to(rootXTurtle, rootYTurtle);
				} else if (statusTurtle == TOP_TO_BOTTOM) {
					wayPointTurtle = 1;
					pathTurtle = new Path(6)
							.to(anmSprTurtle.getX(), anmSprTurtle.getY())
							.to(FOUR_POITER_TURTLE[0][3],
									FOUR_POITER_TURTLE[1][3])
							.to(486, 404)
							.to(FOUR_POITER_TURTLE[0][0],
									FOUR_POITER_TURTLE[1][0])
							.to(FOUR_POITER_TURTLE[0][1],
									FOUR_POITER_TURTLE[1][1])
							.to(rootXTurtle, rootYTurtle);
				}
			}
		}
		pathModifierTurtle = new PathModifier(duration, pathTurtle,
				new IPathModifierListener() {

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub
						Log.d("Xem xem the nao Turtle", "Day roi" + arg2);
						if (arg2 == wayPointTurtle && !isFinishTurtle
								&& !isFinishTurtle) {
							Log.d(TAG, "wayPointTurtle:" + wayPointTurtle);
							countTurtleWin--;
							isChangePanda = false;
							if (!isChangePanda) {
								if (!isFinishRabit && !isFinishTurtle) {
									if (countTurtleWin < countRabbitWin) {
										animatePanda(countTurtleWin);
									}
								}
							}

							if (countTurtleWin == 0 && !isFinishRabit) {
								isFinishTurtle = true;
                                isReset = true;
								runOnUpdateThread(new Runnable() {
                                                                    
                                                                    @Override
                                                                    public void run() {
                                                                        animateWin(1);
                                                                    }
                                                                });
							}
						}
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}
				});
		anmSprTurtle.registerEntityModifier(pathModifierTurtle);
	}

	// //////////////////////////////////////////////////////////////
	// Turtle Eat
	// //////////////////////////////////////////////////////////////
	private void turtleEat(final int indexFood) {

		if (!isTurtleEating) {
			countDistanceAxisTurtle = 0;
			statusTurtleTemp = statusTurtle;

			rootXTurtle = anmSprTurtle.getX();
			rootYTurtle = anmSprTurtle.getY();
			isTurtleEating = true;
			long durations[] = { 200, 200 };
			if (!isTurtleSleep) {
				int frames[] = { 9, 10 };
				anmSprTurtle.stopAnimation();
				anmSprTurtle.animate(durations, frames, 1, this);
			} else {
				if (pathModifierTurtle != null) {
					modifiTurtle(pathModifierTurtle.getDuration());
				}
				int frames[] = { 4, 5 };
				anmSprTurtle.stopAnimation();
				anmSprTurtle.animate(durations, frames, 1, this);
			}
			isTurtleSleep = false;
			sprAudienceBack06.clearEntityModifiers();
			sprAudienceBack06.registerEntityModifier(new DelayModifier(0.5f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTurtleTired = false;

							if (indexFood == 0 || indexFood == 2
									|| indexFood == 3 || indexFood == 4
									|| indexFood == 5) {
								modifiTurtle(6);
							} else {
								modifiTurtle(9);
							}
							isTurtleEating = false;

						}
					}));
		}
		return;
	}

	private void animatePanda(int frame) {
        	    if (anmSprPanda.isAnimationRunning() && anmSprPanda.isVisible()) {
        	        anmSprPanda.stopAnimation();
        	    }
		isChangePanda = true;
		long durations[] = { 200, 200 };
		int frames[] = { frame * 2, frame * 2 + 1 };
		anmSprPanda.animate(durations, frames, -1);
	}

	@SuppressWarnings("static-access")
	private void animateWin(int winner) {
		Log.d(TAG, "----------animateWin------------");
		isWin = true;
		isStart = false;
		anmSprRabbit.stopAnimation();
		anmSprRabbit.setCurrentTileIndex(0);
		anmSprTurtle.stopAnimation();
		anmSprTurtle.setCurrentTileIndex(0);
		anmSprRabbit.clearEntityModifiers();
		anmSprTurtle.clearEntityModifiers();
		anmSprRabbit.setPosition(420, 408);
		anmSprTurtle.setPosition(485, 404);
		rootXRabbit = anmSprRabbit.getX();
		rootYRabbit = anmSprRabbit.getY();
		rootXTurtle = anmSprTurtle.getX();
		rootYTurtle = anmSprTurtle.getY();
		statusRabbit = RIGHT_TO_LEFT;
		statusTurtle = RIGHT_TO_LEFT;
		statusRabbitTemp = statusRabbit;
		statusTurtleTemp = statusTurtle;
		anmSprRabbit.setVisible(false);
		anmSprTurtle.setVisible(false);
		countDistanceAxisRabbit = 0;
		countDistanceAxisTurtle = 0;
		isAudienceTouch = true;
		isRabbitSleep = false;
		isTurtleSleep = false;
		isRabbitTired = false;
		isTurtleTired = false;
		isTurtleOffer = false;
		isRabbitOffer = false;
		isRabbitEating = false;
		isTurtleEating = false;
		isTouchGimic = false;
		countRabbitWin = 3;
		countTurtleWin = 3;
		animatePanda(0);
		anmSprRabbit.setFlippedHorizontal(false);
		anmSprTurtle.setFlippedHorizontal(false);
		sprAudienceFront07.clearEntityModifiers();
		sprAudienceBack05.clearEntityModifiers();
		sprAudienceFront05.clearEntityModifiers();
		sprAudienceBack06.clearEntityModifiers();
		sprDesk.clearEntityModifiers();
		sprAudienceFront06.clearEntityModifiers();
		sprAudienceBack07.clearEntityModifiers();
		if (winner == this.RABBIT_WIN) {
			oggUsagiWin.play();
			this.anmSprUsagi.setVisible(true);
			long durations[] = { 400, 400 };
			int frames[] = { 0, 1 };
			this.anmSprUsagi.animate(durations, frames, 3, this);
		} else {
			oggKameWin.play();
			this.anmSprKame.setVisible(true);
			long durations[] = { 400, 400 };
			int frames[] = { 0, 1 };
			this.anmSprKame.animate(durations, frames, 3, this);
		}
		isFinishRabit = false;
		isFinishTurtle = false;
	}

	private void start() {
		anmSprRabbit.setVisible(true);
		anmSprTurtle.setVisible(true);
		animatePanda(3);
		isReset = false;
		long durations[] = { 200, 200 };
		int frames[] = { 0, 1 };
		anmSprRabbit.stopAnimation();
		anmSprRabbit.animate(durations, frames, -1);
		anmSprTurtle.stopAnimation();
		anmSprTurtle.animate(durations, frames, -1);
		sprDesk.clearEntityModifiers();
		sprDesk.registerEntityModifier(new DelayModifier(1.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						isStart = true;
						isWin = false;
						oggStart.play();
						modifiRabbit(12);
						modifiTurtle(13);
					}
				}));
	}

	private void rabbitTouch() {

		if (!isRabbitEating && isRabbitSleep && !isRabbitOffer
				&& anmSprRabbit.getCurrentTileIndex() != 2
				&& anmSprRabbit.getCurrentTileIndex() != 3) {
		        if(!isTouchGimic){
		            oggMakenaizo.play();
		        }
			isRabbitOffer = true;
			anmSprRabbit.stopAnimation();
			anmSprRabbit.setCurrentTileIndex(6);
			sprAudienceFront06.clearEntityModifiers();
			sprAudienceFront06.registerEntityModifier(new DelayModifier(0.8f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isRabbitOffer = false;
							anmSprRabbit.stopAnimation();
							if (isRabbitTired) {
								long durations[] = { 200, 200 };
								int frames[] = { 2, 3 };
								anmSprRabbit.animate(durations, frames, -1);
							} else {
								long durations[] = { 200, 200 };
								int frames[] = { 0, 1 };
								anmSprRabbit.animate(durations, frames, -1);
							}
						}
					}));
		}
		return;

	}

	private void turtleTouch() {
		if (!isTurtleEating && isTurtleSleep && !isTurtleOffer
				&& anmSprTurtle.getCurrentTileIndex() != 2
				&& anmSprTurtle.getCurrentTileIndex() != 3) {
		        if(!isTouchGimic){
		            oggGanbaruzo.play();
		        }
			isTurtleOffer = true;
			anmSprTurtle.stopAnimation();
			anmSprTurtle.setCurrentTileIndex(6);
			sprAudienceBack07.clearEntityModifiers();
			sprAudienceBack07.registerEntityModifier(new DelayModifier(0.6f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTurtleOffer = false;
							anmSprTurtle.stopAnimation();
							if (isTurtleTired) {
								long durations[] = { 200, 200 };
								int frames[] = { 2, 3 };
								anmSprTurtle.animate(durations, frames, -1);
							} else {
								long durations[] = { 200, 200 };
								int frames[] = { 0, 1 };
								anmSprTurtle.animate(durations, frames, -1);
							}
						}
					}));
		}
		return;
	}

	// //////////////////////////////////////////////////////////////
	// Inner Class
	// //////////////////////////////////////////////////////////////
	private class MySprite extends Sprite {
		private int index;
		private boolean isEated;

		public MySprite(float pX, float pY, TextureRegion pTextureRegion,
				int index, VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
			this.index = index;
			this.isEated = false;
		}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			int action = pSceneTouchEvent.getAction();
			float pX = pSceneTouchEvent.getX();
			float pY = pSceneTouchEvent.getY();

			switch (action) {
			case TouchEvent.ACTION_DOWN:
				if ((countRabbitWin != 0 && countTurtleWin != 0)
						&& !isFinishRabit && !isFinishTurtle) {
					isBooleanA = true;
				}
				break;
			case TouchEvent.ACTION_MOVE:
			    try{
				if (isStart) {
					Log.d(TAG, pSceneTouchEvent.getMotionEvent()
							.getPointerCount() + "");
					this.setPosition(pX - this.getWidth() / 2,
							pY - this.getHeight() / 2);
					if (this.contains(Vol3UsagiKame.this.anmSprRabbit.getX()
							+ anmSprRabbit.getWidth() / 2,
							Vol3UsagiKame.this.anmSprRabbit.getY()
									+ anmSprRabbit.getHeight() / 2)
							&& (countRabbitWin != 0 && countTurtleWin != 0)
							&& !isFinishRabit && !isFinishTurtle) {
						if (!this.isEated && isBooleanA) {
							this.isEated = true;
							if (this.index == 0 || this.index == 4) {
								oggChu.play();
							} else {
								oggHagu.play();
							}
							rabbitEat(this.index);
						}
					}
					if (this.contains(Vol3UsagiKame.this.anmSprTurtle.getX()
							+ anmSprTurtle.getWidth() / 2,
							Vol3UsagiKame.this.anmSprTurtle.getY()
									+ anmSprTurtle.getHeight() / 2)
							&& (countRabbitWin != 0 && countTurtleWin != 0)
							&& !isFinishRabit && !isFinishTurtle) {
						if (!this.isEated && isBooleanA) {
							this.isEated = true;
							if (this.index == 0 || this.index == 4) {
								oggChu.play();
							} else {
								oggHagu.play();
							}
							turtleEat(this.index);
						}
					}
				}
			    }catch (Exception e) {
                                // TODO: handle exception
                            }
				break;
			case TouchEvent.ACTION_UP:
			    try{
				if (this.isEated
						&& (countRabbitWin != 0 && countTurtleWin != 0)
						&& !isFinishRabit && !isFinishTurtle && isBooleanA) {
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MySprite.this.clearEntityModifiers();
							MySprite.this.registerEntityModifier(new DelayModifier(
									0.6f, new IEntityModifierListener() {
										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0, IEntity arg1) {
											// TODO Auto-generated method stub
										}
										@Override
										public void onModifierFinished(
												IModifier<IEntity> arg0, IEntity arg1) {
											MySprite.this
													.setPosition(
															arrPointFood[0][MySprite.this.index],
															arrPointFood[1][MySprite.this.index]);

										}
									}));
						}
					});

				} else {
					this.setPosition(arrPointFood[0][this.index],
							arrPointFood[1][this.index]);
				}
				this.isEated = false;
				isBooleanA = false;
				break;
			    }catch (Exception e) {
                                // TODO: handle exception
                            }
			default:
				break;
			}
			return true;

		}
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onModifierStarted(IModifier<Object> arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
