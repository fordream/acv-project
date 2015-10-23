package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ShabondamaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

public class Vol3Shabondama extends BaseGameActivity implements IOnSceneTouchListener {
	private static final String TAG = "LOG_VOL3SHABONDAMA";

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private ITextureRegion trBackground, textureFloewerBlue1,
			textureFloewerWhite1, textureFloewerYellow2, textureFloewerYellow3,
			textureFloewerRed1, textureTree1, textureTree2, textureTree3,
			textureTree4, textureTree5, textureTree6, textureTree7,
			textureTree8;
	int check_bubbles1 = 1, check_bubbles2 = 8, check_bubbles3 = 8,
			check_bubbles4 = 8, check_bubbles5 = 8, check_bubbles6 = 8,
			check_bubbles7 = 8, check_bubbles8 = 8, rand;
	Random randomBubbles = new Random();
	private ITiledTextureRegion[] textureITextureRegionsPubles = new ITiledTextureRegion[8];
	int publesIndex[] = { Vol3ShabondamaResource.A3_01_1_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_2_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_3_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_4_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_5_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_6_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_7_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_8_IPHONE3G_BUBBLES_ID,
			Vol3ShabondamaResource.A3_01_9_IPHONE3G_BUBBLES_ID };
	private Sprite sBackground, sFloewerYellow1, sFloewerBlue2,
			sFloewerYellow2, sFloewerRed1, sFloewerWhite1, sFloewerWhite2,
			sFloewerYellow3, sFloewerRed2, sTree1, sTree2, sTree3, sTree4,
			sTree5, sTree6, sTree7, sTree8;

	private TexturePackTextureRegionLibrary vol3ShabondamaRegionLibrary1;

	private TexturePack Vol3ShabondamaPacker_1;

	// change image of same object
	private ITiledTextureRegion pITiledTextureRegionGirl,
			pTiledTextureRegionRapit, pITeITiledTextureRegionPalda_Bye,pITiledTextureRegionPandaScence,
			pITiledTextureRegionsSquirrel, pItITiledTextureRegionRightDuck,
			pITiledTextureRegionDuckLeft;

	private AnimatedSprite pAnimatedSpriteGirl, pAnimatedSpriteRapit,
			pAnimatedSpritePalda_Bye, pAnimatedSpriteSquirrel,pAnimatedSpritePandaScence,
			pAnimatedSpriteRightDuck, pAnimatedSpriteDuckLeft;
	final Bubled b[] = new Bubled[8];
	private Sound mp3PandaAction, mp3Knock,
			mp3Pan, mp3Powa;

	private boolean isTouchGirl = true;
	private boolean isTouchPalda = true;
	private boolean isTouchSquirel = true;
	private boolean isTouchGemic = false;
	private boolean isTouchBubleds = true;;
	private int countTouchGirl = 0;

	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Loadding Resource");
		SoundFactory.setAssetBasePath("shabondama/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("shabondama/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "shabondama/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Vol3ShabondamaPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3ShabondamaPacker1.xml");
		Vol3ShabondamaPacker_1.loadTexture();

		vol3ShabondamaRegionLibrary1 = Vol3ShabondamaPacker_1
				.getTexturePackTextureRegionLibrary();

		trBackground = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_00_IPHONE3G_HAIKEI_SHABONDAMA_ID);
		Log.d(TAG, "Loadding background...");
		// tree
		textureTree1 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree2 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree3 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree4 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree5 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree6 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree7 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);
		textureTree8 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_12_IPHONE3G_KUKI_ID);

		// Flower//
		textureFloewerBlue1 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_15_IPHONE3G_FLOWER_BLUE_ID);

		textureFloewerWhite1 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_13_IPHONE3G_FLOWER_WHITE_ID);
		textureFloewerYellow3 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_14_IPHONE3G_FLOWER_YELLOW_ID);
		textureFloewerYellow2 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_14_IPHONE3G_FLOWER_YELLOW_ID);

		textureFloewerRed1 = vol3ShabondamaRegionLibrary1
				.get(Vol3ShabondamaResource.A3_16_IPHONE3G_FLOWER_RED_ID);

		for (int i = 0; i < textureITextureRegionsPubles.length; i++) {
			textureITextureRegionsPubles[i] = getTiledTextureFromPacker(
					Vol3ShabondamaPacker_1, publesIndex[i], publesIndex[8]);
		}
		// //
		pITiledTextureRegionGirl = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_02_1_IPHONE3G_GIRL_ID,
				Vol3ShabondamaResource.A3_02_2_IPHONE3G_GIRL_ID);

		pTiledTextureRegionRapit = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_03_1_IPHONE3G_RABBIT_ID,
				Vol3ShabondamaResource.A3_03_2_IPHONE3G_RABBIT_ID);
		
		pITeITiledTextureRegionPalda_Bye = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_04_1_IPHONE3G_PANDA_BYE_ID,
				Vol3ShabondamaResource.A3_04_2_IPHONE3G_PANDA_BYE_ID,
				Vol3ShabondamaResource.A3_05_1_IPHONE3G_E0008_PANDA_ACTION_ID
				);
		pITiledTextureRegionPandaScence = getTiledTextureFromPacker(Vol3ShabondamaPacker_1, Vol3ShabondamaResource.A3_05_2_IPHONE3G_E0008_PANDA_ACTION_ID,
				Vol3ShabondamaResource.A3_05_3_IPHONE3G_E0008_PANDA_ACTION_ID,
				Vol3ShabondamaResource.A3_05_4_IPHONE3G_E0008_PANDA_ACTION_ID,
				Vol3ShabondamaResource.A3_05_5_IPHONE3G_E0008_PANDA_ACTION_ID);

		pITiledTextureRegionsSquirrel = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_06_1_IPHONE3G_SQUIRREL_ID,
				Vol3ShabondamaResource.A3_06_2_IPHONE3G_SQUIRREL_ID,
				Vol3ShabondamaResource.A3_06_3_IPHONE3G_SQUIRREL_ID,
				Vol3ShabondamaResource.A3_06_4_IPHONE3G_SQUIRREL_ID,
				Vol3ShabondamaResource.A3_06_5_IPHONE3G_SQUIRREL_ID);

		// duck left
		pITiledTextureRegionDuckLeft = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_08_IPHONE3G_DUCK_LEFT_ID,
				Vol3ShabondamaResource.A3_08_IPHONE3G_DUCK_LEFT_REVERT_ID);
		// duck right
		pItITiledTextureRegionRightDuck = getTiledTextureFromPacker(
				Vol3ShabondamaPacker_1,
				Vol3ShabondamaResource.A3_07_IPHONE3G_DUCK_RIGHT_ID,
				Vol3ShabondamaResource.A3_07_IPHONE3G_DUCK_RIGHT_REVERT_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3PandaAction = loadSoundResourceFromSD(Vol3ShabondamaResource.E0008_PANDA_ACTION);
		mp3Pan = loadSoundResourceFromSD(Vol3ShabondamaResource.E0055_PAN);
		mp3Knock = loadSoundResourceFromSD(Vol3ShabondamaResource.E0054_KNOCK);
		mp3Powa = loadSoundResourceFromSD(Vol3ShabondamaResource.E0056_POWA);
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		// background
		sBackground = new Sprite(0, 0, trBackground,
				this.getVertexBufferObjectManager());
		Log.d(TAG, "Loadding screen");
		this.mScene.attachChild(sBackground);
		// tree
		sTree1 = new Sprite(24, 520, textureTree1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree1);

		sTree2 = new Sprite(58, 554, textureTree2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree2);

		sTree3 = new Sprite(70, 470, textureTree3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree3);

		sTree4 = new Sprite(98, 506, textureTree4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree4);

		sTree5 = new Sprite(138, 544, textureTree5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree5);

		sTree6 = new Sprite(564, 446, textureTree6,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree6);

		sTree7 = new Sprite(608, 466, textureTree7,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree7);

		sTree8 = new Sprite(644, 480, textureTree8,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree8);

		/*
		 * flower
		 */
		sFloewerBlue2 = new Sprite(20, 494, textureFloewerBlue1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerBlue2);

		//
		sFloewerYellow2 = new Sprite(68, 448, textureFloewerYellow2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerYellow2);
		sFloewerYellow3 = new Sprite(135, 520, textureFloewerYellow3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerYellow3);
		sFloewerYellow1 = new Sprite(558, 428, textureFloewerYellow3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerYellow1);
		// //
		sFloewerRed1 = new Sprite(94, 490, textureFloewerRed1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerRed1);
		sFloewerRed2 = new Sprite(608, 446, textureFloewerRed1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerRed2);
		//

		//
		sFloewerWhite1 = new Sprite(55, 532, textureFloewerWhite1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerWhite1);
		sFloewerWhite2 = new Sprite(638, 458, textureFloewerWhite1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sFloewerWhite2);

		// the girl
		pAnimatedSpriteGirl = new AnimatedSprite(64, 260,
				pITiledTextureRegionGirl, this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedSpriteGirl);
		this.mScene.registerTouchArea(pAnimatedSpriteGirl);

		// Rapit
				pAnimatedSpriteRapit = new AnimatedSprite(500, 310,
						pTiledTextureRegionRapit, this.getVertexBufferObjectManager());
				this.mScene.attachChild(pAnimatedSpriteRapit);
		// Panda
		pAnimatedSpritePandaScence = new AnimatedSprite(344, 372,
				pITiledTextureRegionPandaScence,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedSpritePandaScence);
		pAnimatedSpritePandaScence.setVisible(false);
		
		pAnimatedSpritePalda_Bye = new AnimatedSprite(344, 372,
				pITeITiledTextureRegionPalda_Bye,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedSpritePalda_Bye);
		this.mScene.registerTouchArea(pAnimatedSpritePalda_Bye);

		

		// SQUIRREL

		pAnimatedSpriteSquirrel = new AnimatedSprite(736, 158,
				pITiledTextureRegionsSquirrel,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchSquirel) {
						mp3Knock.play();
						pAnimatedSpriteSquirrel.registerEntityModifier(new DelayModifier(1.5f));
						animateSquirrel();
						
						return true;
					}

				}

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		this.mScene.attachChild(pAnimatedSpriteSquirrel);
		this.mScene.registerTouchArea(pAnimatedSpriteSquirrel);

		// duck
				pAnimatedSpriteDuckLeft = new AnimatedSprite(698, 400,
						pITiledTextureRegionDuckLeft,
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(pAnimatedSpriteDuckLeft);
		// Duck
		pAnimatedSpriteRightDuck = new AnimatedSprite(736, 400,
				pItITiledTextureRegionRightDuck,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(pAnimatedSpriteRightDuck);
		

		// publes

		for (int i = 0; i < b.length; i++) {
			b[i] = new Bubled(300, 320, textureITextureRegionsPubles[i],
					this.getVertexBufferObjectManager(), i);
			b[i].setPosition(300, 320);
			this.mScene.attachChild(b[i]);
			b[i].setVisible(false);
		}

		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onUpdate(float arg0) {
				if (check_bubbles1 == 0 && !b[0].isVisible() && b[0] != null) {
					check_bubbles1 = 1;
					countTouchGirl--;
					b[0].clearEntityModifiers();
					Log.d("VE CHUA", "A:" + check_bubbles1);
				}
				if (check_bubbles2 == 1 && !b[1].isVisible() && b[1] != null) {
					check_bubbles2 = 8;
					countTouchGirl--;
					b[1].clearEntityModifiers();
					Log.d("VE CHUA", "B:" + check_bubbles2);
				}
				if (check_bubbles3 == 2 && !b[2].isVisible() && b[2] != null) {
					check_bubbles3 = 8;
					countTouchGirl--;
					b[2].clearEntityModifiers();
					Log.d("VE CHUA", "C:" + check_bubbles3);
				}
				if (check_bubbles4 == 3 && !b[3].isVisible() && b[3] != null) {
					check_bubbles4 = 8;
					countTouchGirl--;
					b[3].clearEntityModifiers();
					Log.d("VE CHUA", "D:" + check_bubbles4);
				}
				if (check_bubbles5 == 4 && !b[4].isVisible() && b[4] != null) {
					check_bubbles5 = 8;
					countTouchGirl--;
					b[4].clearEntityModifiers();
					Log.d("VE CHUA", "E:" + check_bubbles5);
				}
				if (check_bubbles6 == 5 && !b[5].isVisible() && b[5] != null) {
					check_bubbles6 = 8;
					countTouchGirl--;
					b[5].clearEntityModifiers();
					Log.d("VE CHUA", "F:" + check_bubbles6);
				}
				if (check_bubbles7 == 6 && !b[6].isVisible() && b[6] != null) {
					check_bubbles7 = 8;
					countTouchGirl--;
					b[6].clearEntityModifiers();
					Log.e("VE CHUA", "G:" + check_bubbles7);
				}
				if (check_bubbles8 == 7 && !b[7].isVisible() && b[7] != null) {
					check_bubbles8 = 8;
					countTouchGirl--;
					b[7].clearEntityModifiers();
					Log.e("VE CHUA", "H:" + check_bubbles8);
				}
			}
		});
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(this.mScene, Vol3ShabondamaResource.SOUND_GIMMIC,
				Vol3ShabondamaResource.IMAGE_GIMMIC,
				vol3ShabondamaRegionLibrary1);

	}

	// duck move right direction
	private void duckRightMove(final AnimatedSprite anisSprite, int index) {
		final Path path;
		if (index == 0) {
			path = new Path(3)
					.to(pAnimatedSpriteRightDuck.getX(),
							pAnimatedSpriteRightDuck.getY())
					.to(670, pAnimatedSpriteRightDuck.getY())
					.to(pAnimatedSpriteRightDuck.getX(),
							pAnimatedSpriteRightDuck.getY());
		} else {
			path = new Path(3).to(632, 400).to(698, 400).to(632, 400);
		}
		anisSprite.registerEntityModifier(new LoopEntityModifier(
				(new PathModifier(10, path, null, new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 0:
							anisSprite.setCurrentTileIndex(0);
							break;
						case 1:
							anisSprite.setCurrentTileIndex(1);
							break;
						}
					}
				}, EaseLinear.getInstance())), -1));

	}

	private void duckLeftMove(final AnimatedSprite aniSprite, final int index) {
		final Path path;
		if (index == 0) {
			path = new Path(3).to(aniSprite.getX(), aniSprite.getY())
					.to(632, 400).to(aniSprite.getX(), aniSprite.getY());
		} else {
			path = new Path(3).to(632, 400)
					.to(aniSprite.getX(), aniSprite.getY()).to(632, 400);
		}
		aniSprite.registerEntityModifier(new LoopEntityModifier(
				(new PathModifier(10, path, null, new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 0:
							aniSprite.setCurrentTileIndex(0);
							break;
						case 1:
							aniSprite.setCurrentTileIndex(1);
							break;
						}
					}
				}, EaseLinear.getInstance())), -1));

	}

	@Override
	public synchronized void onResumeGame() {
		isTouchGirl = true;
		isTouchPalda = true;
		isTouchSquirel = true;
		isTouchGemic = false;
		isTouchBubleds = true;
		countTouchGirl = 0;
		animateRapit();
		rotateFlower();
		this.mScene.registerTouchArea(pAnimatedSpriteGirl);
		pAnimatedSpriteDuckLeft.setPosition(698, 400);
		pAnimatedSpriteDuckLeft.setCurrentTileIndex(0);
		pAnimatedSpriteRightDuck.setPosition(736, 400);
		pAnimatedSpriteRightDuck.setCurrentTileIndex(0);
		animatePanda_Bye();
		duckLeftMove(pAnimatedSpriteDuckLeft,
				pAnimatedSpriteDuckLeft.getCurrentTileIndex());
		duckRightMove(pAnimatedSpriteRightDuck,
				pAnimatedSpriteRightDuck.getCurrentTileIndex());
		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		check_bubbles1 = 1;
		check_bubbles2 = 8;
		check_bubbles3 = 8;
		check_bubbles4 = 8;
		check_bubbles5 = 8;
		check_bubbles6 = 8;
		check_bubbles7 = 8;
		check_bubbles8 = 8;
		countTouchGirl = 0;
		isTouchPalda = true;
		isTouchSquirel = true;
		isTouchGirl = true;
		isTouchBubleds = true;
		isTouchGemic = false;
		setTouchGimmic3(true);
		for (int i = 0; i < b.length; i++) {
			if (b[i].isVisible() == true && b[i] != null) {
				b[i].setVisible(false);
				b[i].setPosition(b[i].getmXFirst(), b[i].getmYFirst());
				b[i].clearEntityModifiers();
			}
		}
		/*
		 * mp3PandaAction, mp3PandaFootstep, mp3PandaSlip, mp3Knock, mp3Pan,
		 * mp3Powa;
		 */
		mp3Knock.stop();
		mp3PandaAction.stop();
		mp3Pan.stop();
		mp3Powa.stop();
		if (pAnimatedSpriteDuckLeft != null) {
			pAnimatedSpriteDuckLeft.setPosition(698, 400);
			pAnimatedSpriteDuckLeft.clearEntityModifiers();
			pAnimatedSpriteDuckLeft.setVisible(true);
		}
		if(pAnimatedSpritePandaScence.isVisible()==true&&pAnimatedSpritePandaScence!=null){
			pAnimatedSpritePandaScence.setVisible(false);
			pAnimatedSpritePandaScence.setPosition(pAnimatedSpritePandaScence.getX(), pAnimatedSpritePandaScence.getY());
			pAnimatedSpritePandaScence.clearEntityModifiers();
		}
		if (pAnimatedSpriteRightDuck != null) {
			pAnimatedSpriteRightDuck.setPosition(736, 400);
			pAnimatedSpriteRightDuck.clearEntityModifiers();
			pAnimatedSpriteRightDuck.setVisible(true);
		}
		if (pAnimatedSpriteGirl != null) {
			pAnimatedSpriteGirl.clearEntityModifiers();
			pAnimatedSpriteGirl.setCurrentTileIndex(0);
			pAnimatedSpriteGirl.setPosition(64, 260);
		}
		if (pAnimatedSpritePalda_Bye != null) {
			pAnimatedSpritePalda_Bye.clearEntityModifiers();
			pAnimatedSpritePalda_Bye.setCurrentTileIndex(0);
			pAnimatedSpritePalda_Bye.setPosition(344, 372);
		}
		if (pAnimatedSpriteSquirrel != null) {
			pAnimatedSpriteSquirrel.clearEntityModifiers();
			pAnimatedSpriteSquirrel.setCurrentTileIndex(0);
			pAnimatedSpriteSquirrel.setPosition(736, 158);
		}

	}

	// panda action
	public void touchPandaAction() {
		mp3PandaAction.play();
		final Path path = new Path(3)
				.to(pAnimatedSpritePalda_Bye.getX(),
						pAnimatedSpritePalda_Bye.getY())
				.to(pAnimatedSpritePalda_Bye.getX() + 100,
						pAnimatedSpritePalda_Bye.getY() + 60)
				.to(pAnimatedSpritePalda_Bye.getX() + 150,
						CAMERA_HEIGHT - pAnimatedSpritePalda_Bye.getHeight());
		pAnimatedSpritePalda_Bye.registerEntityModifier((new PathModifier(2.2f,
				path, null, new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						pAnimatedSpritePalda_Bye.setVisible(false);
						pAnimatedSpritePalda_Bye.setPosition(344, 372);
						pAnimatedSpritePandaScence.setVisible(true);
						pAnimatedSpritePandaScence.setPosition(pAnimatedSpritePalda_Bye.getX() + 135,
								CAMERA_HEIGHT - pAnimatedSpritePalda_Bye.getHeight());
						pandaTouchedScreen();
						
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						animatePanda_Move();
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {

					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance())));

//		isTouchPalda = true;
	}

	// Bunleds
	class Bubled extends AnimatedSprite {
		private int indexs;

		public int getIndexs() {
			return indexs;
		}

		public Bubled(float pX, float pY,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pTiledSpriteVertexBufferObject,
				int index) {
			super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
			this.indexs = index;
		}

		public void setVisibleAndPosition(final AnimatedSprite anis) {
			mp3Pan.play();
			anis.registerEntityModifier(new DelayModifier(0.15f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTouchBubleds = false;
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							anis.setVisible(false);
							isTouchBubleds = true;
						}
					}));
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			// TODO Auto-generated method stub
			if (pSceneTouchEvent.isActionDown()) {
				pSceneTouchEvent.getX();
				if (isTouchBubleds && this.isVisible() == true) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							switch (indexs) {
							case 0:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[0]);
								b[0].setCurrentTileIndex(1);
								setVisibleAndPosition(b[0]);
								break;
							case 1:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[1]);
								b[1].setCurrentTileIndex(1);
								setVisibleAndPosition(b[1]);
								break;
							case 2:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[2]);
								b[2].setCurrentTileIndex(1);
								setVisibleAndPosition(b[2]);
								break;
							case 3:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[3]);
								b[3].setCurrentTileIndex(1);
								setVisibleAndPosition(b[3]);

								break;
							case 4:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[4]);
								b[4].setCurrentTileIndex(1);
								setVisibleAndPosition(b[4]);
								break;
							case 5:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[5]);
								b[5].setCurrentTileIndex(1);
								setVisibleAndPosition(b[5]);
								break;
							case 6:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[6]);
								b[6].setCurrentTileIndex(1);
								setVisibleAndPosition(b[6]);
								break;
							case 7:
								Vol3Shabondama.this.mScene
										.unregisterTouchArea(b[7]);
								b[7].setCurrentTileIndex(1);
								setVisibleAndPosition(b[7]);
								break;
							}
						}
					});
				}

				return true;
			}
			if (pSceneTouchEvent.isActionUp()) {
				return true;
			}
			return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);
		}

	}

	private int vectors[][] = { { 960, 20 }, { 950, 0 }, { 670, 652 },
			{ 991, 367 }, { 945, 532 }, { -100, 28 }, { -100, 114 },
			{ 512, -100 }, { 936, 129 }, { 384, 630 } };
	
	public void bubledsMove(final Bubled bubled){
		Random random = new Random();
		int rand = random.nextInt(vectors.length);
		int pointOne = 0;
		int pointTwo = 0;
		switch (rand) {
		case 0:
			pointOne = vectors[0][0];
			pointTwo = vectors[0][1];
			// Randome 10 huong
			break;
		case 1:
			pointOne = vectors[1][0];
			pointTwo = vectors[1][1];
			break;
		case 2:
			pointOne = vectors[2][0];
			pointTwo = vectors[2][1];
			break;
		case 3:
			pointOne = vectors[3][0];
			pointTwo = vectors[3][1];
			break;
		case 4:
			pointOne = vectors[4][0];
			pointTwo = vectors[4][1];
			break;
		case 5:
			pointOne = vectors[5][0];
			pointTwo = vectors[5][1];
			break;
		case 6:
			pointOne = vectors[6][0];
			pointTwo = vectors[6][1];
			break;
		case 7:
			pointOne = vectors[7][0];
			pointTwo = vectors[7][1];
			break;
		case 8:
			pointOne = vectors[8][0];
			pointTwo = vectors[8][1];
			break;
		case 9:
			pointOne = vectors[9][0];
			pointTwo = vectors[9][1];
			break;
		}
		bubled.clearEntityModifiers();
		bubled.registerEntityModifier(new MoveModifier(6.0f, bubled
				.getmXFirst(), pointOne, bubled.getmYFirst(), pointTwo,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						bubled.setVisible(false);
						isTouchBubleds = true;
						Log.d(TAG, "location  x :" + bubled.getX() + "y : "
								+ bubled.getY());
					}
				}));
	}

	public void touchGirl() {
		int pFrame[] = { 0, 1 };
		long pdurationFrame[] = { 85, 85 };
		pAnimatedSpriteGirl.animate(pdurationFrame, pFrame, 0,new IAnimationListener() {
			
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				// TODO Auto-generated method stub
				isTouchGirl = false;
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				// TODO Auto-generated method stub
				isTouchGirl = true;
				pAnimatedSpriteGirl.registerEntityModifier(new DelayModifier(0.15f,new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						pAnimatedSpriteGirl.setCurrentTileIndex(0);
					}
				}));
			}
		});
	}

	/*
	 * Rotation Flower
	 */
	public void rotateFlower() {
		//TODO
		LoopEntityModifier loopMod = new LoopEntityModifier(
				new RotationModifier(50.0f, 0, 360, EaseLinear.getInstance()));
		sFloewerBlue2.registerEntityModifier(loopMod);
		sFloewerRed1.registerEntityModifier(loopMod);
		sFloewerRed2.registerEntityModifier(loopMod);
		sFloewerWhite1.registerEntityModifier(loopMod);
		sFloewerWhite2.registerEntityModifier(loopMod);
		sFloewerYellow1.registerEntityModifier(loopMod);
		sFloewerYellow2.registerEntityModifier(loopMod);
		sFloewerYellow3.registerEntityModifier(loopMod);
	}

	// End rotation
	public void animateSquirrel() {
		// isTouchGemic = false;

		setTouchGimmic3(false);
		int frameSquirrel[] = { 0, 1, 2, 3, 4 };
		long[] durationFrameSquirrel = { 200, 175, 175, 175, 175 };
		pAnimatedSpriteSquirrel.animate(durationFrameSquirrel, frameSquirrel,
				0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						// TODO Auto-generated method stub
						isTouchSquirel = false;
					//	pAnimatedSpriteSquirrel.registerEntityModifier(new DelayModifier(0.7f));
						
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						// TODO Auto-generated method stub
						setTouchGimmic3(true);
						isTouchGemic = false;
						isTouchSquirel = true;
						pAnimatedSpriteSquirrel.setCurrentTileIndex(0);
					}
				});

	}

	public void animateRapit() {
		int frameRapit[] = { 0, 1 };
		long[] durationFrameRapit = { 300, 300 };
		pAnimatedSpriteRapit.animate(durationFrameRapit, frameRapit, -1);
	}

	public void animatePanda_Bye() {
		int framePanda[] = { 0, 1 };
		long[] durationFramePanda = { 300, 300 };
		pAnimatedSpritePalda_Bye.animate(durationFramePanda, framePanda, -1);
	}

	public void animatePanda_Move() {
		int framePandaMove[] = { 1, 2 };
		long[] durationFramePandaMove = { 300, 300 };
		pAnimatedSpritePalda_Bye.animate(durationFramePandaMove,
				framePandaMove, -1);
	}

	public void pandaTouchedScreen() {
		int framePandaMove_1[] = { 0, 1, 2,3 };
		long[] durationFramePandaMove_1 = { 300, 300, 300, 300 };
		pAnimatedSpritePandaScence.animate(durationFramePandaMove_1,
				framePandaMove_1, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						// TODO Auto-generated method stub
						pAnimatedSpritePalda_Bye.setPosition(344, 372);
						pAnimatedSpritePalda_Bye.setVisible(true);
						pAnimatedSpritePalda_Bye.setCurrentTileIndex(0);
						
						pAnimatedSpritePandaScence.setCurrentTileIndex(0);
						pAnimatedSpritePandaScence.setVisible(false);
						pAnimatedSpritePandaScence.setPosition(344, 372);
						isTouchPalda = true;
						animatePanda_Bye();
					}
				});
	}

	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		if (!isTouchGemic) {
			isTouchGemic = true;
			mp3Knock.play();
			setTouchGimmic3(false);
			animateSquirrel();
		}

	}

	@Override
	protected void loadKaraokeComplete() {
		// TODO Auto-generated method stub
		super.loadKaraokeComplete();
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pScenceTouch) {
		// TODO Auto-generated method stub
		int pX = (int) pScenceTouch.getX();
		int pY = (int) pScenceTouch.getY();
		if(checkContains(pAnimatedSpritePalda_Bye, 43, 17, 118, 100, pX, pY)){
			if(isTouchPalda){
				isTouchPalda=false;
				touchPandaAction();
			}
		}
		if(checkContains(pAnimatedSpriteGirl, 7, 46, 179, 259, pX, pY)){
			if(isTouchGirl){
			if(countTouchGirl<7){
				touchGirl();
				pAnimatedSpriteGirl.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0,
						IEntity arg1) {
					mp3Powa.play();
					if (countTouchGirl <= 6) {
						rand = randomBubbles.nextInt(8);
						while ((rand == check_bubbles1)
								|| (rand == check_bubbles2)
								|| (rand == check_bubbles3)
								|| (rand == check_bubbles4)
								|| (rand == check_bubbles5)
								|| (rand == check_bubbles6)
								|| (rand == check_bubbles7)
								|| (rand == check_bubbles8)) {
							rand = randomBubbles.nextInt(8);
						}
						Log.d("TEST TOUCH", "RANDOme" + rand);
						switch (rand) {
						case 0:
							if (check_bubbles1 == 1) {
								countTouchGirl++;
								b[0].setVisible(true);
								b[0].setPosition(b[0].getmXFirst(),
										b[0].getmYFirst());
								b[0].setCurrentTileIndex(0);
								bubledsMove(b[0]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[0]);
								check_bubbles1 = 0;
								break;
							}
						case 1:
							if (check_bubbles2 == 8) {
								countTouchGirl++;
								b[1].setVisible(true);
								b[1].setPosition(b[1].getmXFirst(),
										b[1].getmYFirst());
								b[1].setCurrentTileIndex(0);
								bubledsMove(b[1]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[1]);
								check_bubbles2 = 1;
								break;
							}
						case 2:
							if (check_bubbles3 == 8) {
								countTouchGirl++;
								b[2].setVisible(true);
								b[2].setPosition(b[2].getmXFirst(),
										b[2].getmYFirst());
								b[2].setCurrentTileIndex(0);
								bubledsMove(b[2]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[2]);
								check_bubbles3 = 2;
								break;
							}
						case 3:
							if (check_bubbles4 == 8) {
								countTouchGirl++;
								b[3].setVisible(true);
								b[3].setPosition(b[3].getmXFirst(),
										b[3].getmYFirst());
								b[3].setCurrentTileIndex(0);
								bubledsMove(b[3]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[3]);
								check_bubbles4 = 3;
								break;
							}
						case 4:
							if (check_bubbles5 == 8) {
								countTouchGirl++;
								b[4].setVisible(true);
								b[4].setPosition(b[4].getmXFirst(),
										b[4].getmYFirst());
								b[4].setCurrentTileIndex(0);
								bubledsMove(b[4]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[4]);
								check_bubbles5 = 4;
								break;
							}
						case 5:
							if (check_bubbles6 == 8) {
								countTouchGirl++;
								b[5].setVisible(true);
								b[5].setPosition(b[5].getmXFirst(),
										b[5].getmYFirst());
								b[5].setCurrentTileIndex(0);
								bubledsMove(b[5]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[5]);
								check_bubbles6 = 5;
								break;
							}
						case 6:
							if (check_bubbles7 == 8) {
								countTouchGirl++;
								b[6].setVisible(true);
								b[6].setPosition(b[6].getmXFirst(),
										b[6].getmYFirst());
								b[6].setCurrentTileIndex(0);
								bubledsMove(b[6]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[6]);
								check_bubbles7 = 6;
								break;
							}
						case 7:
							if (check_bubbles8 == 8) {
								countTouchGirl++;
								b[7].setVisible(true);
								b[7].setPosition(b[7].getmXFirst(),
										b[7].getmYFirst());
								b[7].setCurrentTileIndex(0);
								bubledsMove(b[7]);
								Vol3Shabondama.this.mScene
										.registerTouchArea(b[7]);
								check_bubbles8 = 7;
								break;
							}
						default:
							break;
						}
					}
				}
			}));

				return true;
			}
		}
		}
		
		return false;
	}

}
