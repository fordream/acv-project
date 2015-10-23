package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3PandaResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Panda extends BaseGameActivity implements
		IOnSceneTouchListener {

	private static final String TAG = "LOG_VOL3SHABONDAMA";

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack Vol2PandaPacker_1, Vol2PandaPacker_2;
	private TexturePackTextureRegionLibrary vol2PandaTexturePackTextureRegionLibrary_1,
			vol2PandaTexturePackTextureRegionLibrary_2;
	private Sprite spBackground, spHanabiPanda, spHanabiKoala, spHanabiRapit,
			spHanabiLine1, spHanabiLine2, spHanabiLine3;
	private Sprite spriteHanabiLine[] = { spHanabiLine1, spHanabiLine2,
			spHanabiLine3 };
	private Sprite spritePandaFace[] = new Sprite[7];
	private Sprite[] spriteKoalaFace = new Sprite[7];
	private Sprite[] spriteRapitFace = new Sprite[7];
	private Sprite[] sprGimmic = new Sprite[3];
	//int gimmicIndex = {};
	private ITextureRegion[] textGimmic = new ITextureRegion[3];
	private ITextureRegion[] textITextureRegionsPandaFace = new ITextureRegion[7];
	private ITextureRegion[] textITextureRegionsRapitFace = new ITextureRegion[7];
	private ITextureRegion[] textITextureRegionsKoalaFace = new ITextureRegion[7];

	private int[] textPandaFace = {
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_1_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_2_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_3_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_4_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_5_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_6_IPHONE4_PANDA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_1_7_IPHONE4_PANDA_ID };
	private int[] textKoalaFace = {
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_1_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_2_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_3_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_4_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_5_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_6_IPHONE4_KOALA_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_3_7_IPHONE4_KOALA_ID };
	private int[] textRapitface = {
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_1_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_2_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_3_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_4_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_5_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_6_IPHONE4_RABBIT_ID,
			Vol3PandaResouce.Vol2Panda_Packer1.A8_07_2_7_IPHONE4_RABBIT_ID };
	private ITextureRegion textureBackground, textHanabiPanda, textHanabiKoala,
			textHanabiRapit, textHanabiLine;
	private AnimatedSprite asAnimatedSpritePanda, asAnimatedSpriteRabit,
			asAnimatedSpriteKoala, asAnimatedSpriteWindow1,
			asAnimatedSpriteWindow2, asAnimatedSpriteWindow3,
			asAnimatedSpriteWindow4, spHouse, asAnimatedSpriteSmoke;
	private ITiledTextureRegion tileTiledTextureRegionPanda,
			tileITiledTextureRegionRabit, tileITiledTextureRegionKoala,
			tileITiledTextureRegionWindow1, tileITiledTextureRegionWindow2,
			tileITiledTextureRegionWindow3, tileITiledTextureRegionWindow4,
			textureHouse, tileITiledTextureRegionSmoke;

	private Sound mp3Bomi, mp3Gogogo, mp3Gogogo2, mp3Bobobo,
			mp3Kiran;
	private Sound[] soundGimmic = new Sound[3];

	private boolean isTouchWindow1 = true;
	private boolean isTouchWindow2 = true;
	private boolean isTouchWindow3 = true;
	private boolean isTouchWindow4 = true;
	private boolean isTouchHouse = true;
	private boolean isTouchKoala = true;
	private boolean isHouseMoving = false;
	private boolean isTouchGimmic_1 = true;
	private boolean isTouchGimmic_2 = true;
	private boolean isTouchGimmic_3 = true;

	RanDomNoRepeat randomNoRepeat, randomActionFace;
	private int countTouchhouse = 0;
	private int countTouchPanda = 0;
	private int countTouchRapit = 0;
	private int countTouchKoala = 0;
	private int imgSlideOrders = 0;
	private int rand = 0;

	private float lastX = 0;
	private float spase = 100.0f;
	private int[] start = { 244, 436, 612 };

	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Loadding Resource");
		SoundFactory.setAssetBasePath("panda/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("panda/gfx/");

		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "panda/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Vol2PandaPacker_1 = mTexturePackLoaderFromSource
				.load("Vol2PandaPacker1.xml");
		Vol2PandaPacker_1.loadTexture();
		vol2PandaTexturePackTextureRegionLibrary_1 = Vol2PandaPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol2PandaPacker_2 = mTexturePackLoaderFromSource
				.load("Vol2PandaPacker2.xml");
		Vol2PandaPacker_2.loadTexture();
		vol2PandaTexturePackTextureRegionLibrary_2 = Vol2PandaPacker_2
				.getTexturePackTextureRegionLibrary();

		// Background
		textureBackground = vol2PandaTexturePackTextureRegionLibrary_2
				.get(Vol3PandaResouce.Vol2Panda_Packer2.A8_08_IPHONE4_HAIKEI_ID);
		// House
		textureHouse = getTiledTextureFromPacker(Vol2PandaPacker_2,
				Vol3PandaResouce.Vol2Panda_Packer2.A8_06_1_IPHONE4_HOUSE_ID);
		// Panda
		tileTiledTextureRegionPanda = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_1_1_IPHONE4_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_1_2_IPHONE4_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_1_3_IPHONE4_PANDA_ID);
		// Rabit
		tileITiledTextureRegionRabit = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_2_1_IPHONE4_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_2_2_IPHONE4_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_2_3_IPHONE4_RABBIT_ID);
		// Koala
		tileITiledTextureRegionKoala = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_3_1_IPHONE4_KOALA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_3_2_IPHONE4_KOALA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_04_3_3_IPHONE4_KOALA_ID);
		// Window 1
		tileITiledTextureRegionWindow1 = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_1_1_IPHONE4_WINDOW_DEFAULT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_1_2_IPHONE4_WINDOW_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_1_3_IPHONE4_WINDOW_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_1_4_IPHONE4_WINDOW_COALA_ID);
		// Window 2
		tileITiledTextureRegionWindow2 = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_2_1_IPHONE4_WINDOW_DEFAULT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_2_2_IPHONE4_WINDOW_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_2_3_IPHONE4_WINDOW_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_2_4_IPHONE4_WINDOW_COALA_ID);
		// Window 3
		tileITiledTextureRegionWindow3 = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_3_1_IPHONE4_WINDOW_DEFAULT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_3_2_IPHONE4_WINDOW_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_3_3_IPHONE4_WINDOW_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_3_4_IPHONE4_WINDOW_COALA_ID);
		// Window 4
		tileITiledTextureRegionWindow4 = getTiledTextureFromPacker(
				Vol2PandaPacker_1,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_4_1_IPHONE4_WINDOW_DEFAULT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_4_2_IPHONE4_WINDOW_PANDA_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_4_3_IPHONE4_WINDOW_RABBIT_ID,
				Vol3PandaResouce.Vol2Panda_Packer1.A8_05_4_4_IPHONE4_WINDOW_COALA_ID);

		for (int i = 0; i < textITextureRegionsPandaFace.length; i++) {
			textITextureRegionsPandaFace[i] = vol2PandaTexturePackTextureRegionLibrary_1
					.get(textPandaFace[i]);
		}
		for (int i = 0; i < textITextureRegionsKoalaFace.length; i++) {
			textITextureRegionsKoalaFace[i] = vol2PandaTexturePackTextureRegionLibrary_1
					.get(textKoalaFace[i]);
		}
		for (int i = 0; i < textITextureRegionsRapitFace.length; i++) {
			textITextureRegionsRapitFace[i] = vol2PandaTexturePackTextureRegionLibrary_1
					.get(textRapitface[i]);
		}
		// Hanabi
		textHanabiKoala = vol2PandaTexturePackTextureRegionLibrary_1
				.get(Vol3PandaResouce.Vol2Panda_Packer1.A8_03_1_IPHONE4_HANABI_KOALA_ID);
		textHanabiLine = vol2PandaTexturePackTextureRegionLibrary_1
				.get(Vol3PandaResouce.Vol2Panda_Packer1.A8_03_1_IPHONE4_HANABI_LINE_ID);
		textHanabiPanda = vol2PandaTexturePackTextureRegionLibrary_1
				.get(Vol3PandaResouce.Vol2Panda_Packer1.A8_03_2_IPHONE4_HANABI_PANDA_ID);
		textHanabiRapit = vol2PandaTexturePackTextureRegionLibrary_1
				.get(Vol3PandaResouce.Vol2Panda_Packer1.A8_03_3_IPHONE4_HANABI_RABBIT_ID);
		// Smoke
		tileITiledTextureRegionSmoke = getTiledTextureFromPacker(
				Vol2PandaPacker_2,
				Vol3PandaResouce.Vol2Panda_Packer2.A8_06_2_IPHONE4_SMOKE1_ID,
				Vol3PandaResouce.Vol2Panda_Packer2.A8_06_3_IPHONE4_SMOKE1_ID);
		
		for(int i=0;i<sprGimmic.length;i++){
			textGimmic[i] = vol2PandaTexturePackTextureRegionLibrary_1.get(Vol3PandaResouce.Vol2Panda_Packer1.IMAGE_GIMMIC[i]);
		}
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3Bobobo = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.E00171_BOBOBO);
		mp3Bomi = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.E00080_BOMI);
		mp3Gogogo = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.E00169_GOGOGO);
		mp3Gogogo2 = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.E00170_GOGOGO2);
		mp3Kiran = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.E00172_KIRAN);
		for(int i=0;i<soundGimmic.length;i++){
			soundGimmic[i] = loadSoundResourceFromSD(Vol3PandaResouce.Vol2Panda_Packer1.SOUND_MUSIC[i]);
		}
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		// Background
		spBackground = new Sprite(0, 0, textureBackground,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(spBackground);
		float[] hanabiWidth = { 194.5f, 475f, 756.167f };
		// Hanabi
		for (int i = 0; i < spriteHanabiLine.length; i++) {
			spriteHanabiLine[i] = new Sprite(hanabiWidth[i], 144.056f,
					textHanabiLine, this.getVertexBufferObjectManager());
			this.mScene.attachChild(spriteHanabiLine[i]);
			spriteHanabiLine[i].setVisible(false);
		}
		
		spHanabiRapit = new Sprite(342.500f, 3.556f, textHanabiRapit,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(spHanabiRapit);
		spHanabiRapit.setVisible(false);
		spHanabiKoala = new Sprite(619.167f, 7.500f, textHanabiKoala,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(spHanabiKoala);
		spHanabiKoala.setVisible(false);

		spHanabiPanda = new Sprite(55.000f, 7.500f, textHanabiPanda,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(spHanabiPanda);
		spHanabiPanda.setVisible(false);
		// end hanabi

		// Window
		asAnimatedSpriteWindow1 = new AnimatedSprite(81, 170,
				tileITiledTextureRegionWindow1,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchWindow1) {
						mp3Bomi.play();
						isTouchWindow1=false;
						randomWindow(asAnimatedSpriteWindow1);
						asAnimatedSpriteWindow1.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								isTouchWindow1=true;
							}
						}));
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		this.mScene.registerTouchArea(asAnimatedSpriteWindow1);

		asAnimatedSpriteWindow2 = new AnimatedSprite(206, 170,
				tileITiledTextureRegionWindow2,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchWindow2) {
						mp3Bomi.play();
						isTouchWindow2=false;
						randomWindow(asAnimatedSpriteWindow2);
						asAnimatedSpriteWindow2.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								isTouchWindow2=true;
							}
						}));
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		this.mScene.registerTouchArea(asAnimatedSpriteWindow2);

		asAnimatedSpriteWindow3 = new AnimatedSprite(81, 266,
				tileITiledTextureRegionWindow3,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchWindow3) {
						mp3Bomi.play();
						isTouchWindow3 = false;
						randomWindow(asAnimatedSpriteWindow3);
						asAnimatedSpriteWindow3.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								isTouchWindow3=true;
							}
						}));
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		this.mScene.registerTouchArea(asAnimatedSpriteWindow3);
		asAnimatedSpriteWindow4 = new AnimatedSprite(206, 266,
				tileITiledTextureRegionWindow4,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (isTouchWindow4) {
						mp3Bomi.play();
						isTouchWindow4 = false;
						randomWindow(asAnimatedSpriteWindow4);
						asAnimatedSpriteWindow4.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								isTouchWindow4=true;
							}
						}));
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		this.mScene.registerTouchArea(asAnimatedSpriteWindow4);
		// end window
		// Smoke
		asAnimatedSpriteSmoke = new AnimatedSprite(647.464f, 297.87f,
				tileITiledTextureRegionSmoke,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(asAnimatedSpriteSmoke);
		asAnimatedSpriteSmoke.setVisible(false);
		// House 610.464f
		spHouse = new AnimatedSprite(610.464f, 174.87f, textureHouse,
				this.getVertexBufferObjectManager());
		this.spHouse.attachChild(asAnimatedSpriteWindow1);
		this.spHouse.attachChild(asAnimatedSpriteWindow2);
		this.spHouse.attachChild(asAnimatedSpriteWindow3);
		this.spHouse.attachChild(asAnimatedSpriteWindow4);
		this.mScene.attachChild(spHouse);

		// Face
		for (int i = 0; i < textPandaFace.length; i++) {
			spritePandaFace[i] = new Sprite(0, 0,
					textITextureRegionsPandaFace[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(spritePandaFace[i]);
			spritePandaFace[i].setVisible(false);
		}
		for (int i = 0; i < textKoalaFace.length; i++) {
			spriteKoalaFace[i] = new Sprite(0, 0,
					textITextureRegionsKoalaFace[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(spriteKoalaFace[i]);
			spriteKoalaFace[i].setVisible(false);
		}
		for (int i = 0; i < textRapitface.length; i++) {
			spriteRapitFace[i] = new Sprite(0, 0,
					textITextureRegionsRapitFace[i],
					this.getVertexBufferObjectManager());
			this.mScene.attachChild(spriteRapitFace[i]);
			spriteRapitFace[i].setVisible(false);
		}
		// end face
		// Panda
		asAnimatedSpritePanda = new AnimatedSprite(49.464f, 264.870f,
				tileTiledTextureRegionPanda,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(asAnimatedSpritePanda);
		// Rapit
		asAnimatedSpriteRabit = new AnimatedSprite(257.500f, 232.500f,
				tileITiledTextureRegionRabit,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(asAnimatedSpriteRabit);
		
				// Koala
				asAnimatedSpriteKoala = new AnimatedSprite(449.167f, 305,
						tileITiledTextureRegionKoala,
						this.getVertexBufferObjectManager());
				this.mScene.attachChild(asAnimatedSpriteKoala);
		// Gimmic
//		addGimmicsButton(this.mScene,
//				Vol3PandaResouce.Vol2Panda_Packer1.SOUND_MUSIC,
//				Vol3PandaResouce.Vol2Panda_Packer1.IMAGE_GIMMIC,
//				vol2PandaTexturePackTextureRegionLibrary_1);
				
					sprGimmic[0] = new Sprite(start[0], 496, textGimmic[0], this.getVertexBufferObjectManager()){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
								final float pTouchAreaLocalX,
								final float pTouchAreaLocalY) {
							if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
								if(isTouchGimmic_1){
									isTouchGimmic_1=false;
									soundGimmic[0].play();
									sprGimmic[0]
											.registerEntityModifier(new SequenceEntityModifier(
													new ScaleModifier(0.3f, 1, 1.3f),
													new ScaleModifier(0.3f, 1.3f, 1f,new IEntityModifierListener() {
														
														@Override
														public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
															// TODO Auto-generated method stub
															
														}
														
														@Override
														public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
															// TODO Auto-generated method stub
															isTouchGimmic_1=true;
														}
													})));
								return true;
								}
								
							}
							return super.onAreaTouched(pSceneTouchEvent,
									pTouchAreaLocalX, pTouchAreaLocalY);
						}
					};
					this.mScene.attachChild(sprGimmic[0]);
					this.mScene.registerTouchArea(sprGimmic[0]);
					
					sprGimmic[1] = new Sprite(start[1], 496, textGimmic[1], this.getVertexBufferObjectManager()){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
								final float pTouchAreaLocalX,
								final float pTouchAreaLocalY) {
							if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
								
								if(isTouchGimmic_2){
									isTouchGimmic_2=false;
									soundGimmic[1].play();
									sprGimmic[1]
											.registerEntityModifier(new SequenceEntityModifier(
													new ScaleModifier(0.3f, 1, 1.3f),
													new ScaleModifier(0.3f, 1.3f,1,new IEntityModifierListener() {
														
														@Override
														public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
															// TODO Auto-generated method stub
															
														}
														
														@Override
														public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
															// TODO Auto-generated method stub
															isTouchGimmic_2=true;
														}
													})));
								
								return true;
								}

							}
							return super.onAreaTouched(pSceneTouchEvent,
									pTouchAreaLocalX, pTouchAreaLocalY);
						}
					};
					this.mScene.attachChild(sprGimmic[1]);
					this.mScene.registerTouchArea(sprGimmic[1]);
					
					sprGimmic[2] = new Sprite(start[2], 496, textGimmic[2], this.getVertexBufferObjectManager()){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
								final float pTouchAreaLocalX,
								final float pTouchAreaLocalY) {
							if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
								
									if(isTouchGimmic_3){
										isTouchGimmic_3 = false;
										sprGimmic[2]
												.registerEntityModifier(new SequenceEntityModifier(
														new ScaleModifier(0.3f, 1, 1.3f),
														new ScaleModifier(0.3f, 1.3f,1, new IEntityModifierListener() {
															
															@Override
															public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
																// TODO Auto-generated method stub
																
															}
															
															@Override
															public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
																// TODO Auto-generated method stub
																
															}
														})));
											
											Sprite[] sp = { spHanabiPanda, spHanabiRapit, spHanabiKoala };
								
											randomNoRepeat.setLenghNoRepeat(3);
											// Random random = new Random();
											int index = randomNoRepeat.getNextIntNoRepeat(true);
											soundGimmic[2].play();
											showHanabiLine(spriteHanabiLine[index], sp[index]);
									return true;
									}

							}
							return super.onAreaTouched(pSceneTouchEvent,
									pTouchAreaLocalX, pTouchAreaLocalY);
						}
					};
					this.mScene.attachChild(sprGimmic[2]);
					this.mScene.registerTouchArea(sprGimmic[2]);
		

		this.mScene.setOnSceneTouchListener(this);
		
	}
	

	// animate random wimdow
	private void randomWindow(final AnimatedSprite windows) {
		Random random = new Random();
		int index = 0;
		index = random.nextInt(4);
		if (index == windows.getCurrentTileIndex()) {
			index = random.nextInt(4);
		}
		windows.setCurrentTileIndex(index);
	}

	// modifier when touch the house
	private void houseTouchAction(final AnimatedSprite anisHouse, float duration ,Path path) {
		anisHouse.registerEntityModifier(new PathModifier(duration, path, null,
				new IPathModifierListener() {

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub
						isTouchHouse = false;
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						isTouchHouse = true;
					}
				}));
	}

	private void smoke(){
		asAnimatedSpriteSmoke.setCurrentTileIndex(0);
		final Path path = new Path(2).to(asAnimatedSpriteSmoke.getmXFirst(),asAnimatedSpriteSmoke.getmYFirst()).
				to(asAnimatedSpriteSmoke.getmXFirst(),-CAMERA_HEIGHT);
		asAnimatedSpriteSmoke.registerEntityModifier(new PathModifier(3.50f, path, new IPathModifierListener() {
			
			@Override
			public void onPathWaypointStarted(PathModifier arg0, IEntity arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPathStarted(PathModifier arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPathFinished(PathModifier arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				asAnimatedSpriteSmoke.setVisible(false);
				asAnimatedSpriteSmoke.setPosition(asAnimatedSpriteSmoke.getmXFirst(), asAnimatedSpriteSmoke.getmYFirst());
			}
		}));
	}
	
	
	// modifier when thirt touch the house
	private void houseTouchThreeAction(final AnimatedSprite house) {
		isTouchWindow1 = false;
		isTouchWindow2 = false;
		isTouchWindow3 = false;
		isTouchWindow4 = false;
		final Path path = new Path(3)
				.to(house.getmXFirst(), house.getmYFirst())
				.to(house.getmXFirst(), house.getmYFirst() - 250)
				.to(house.getmXFirst(), -CAMERA_HEIGHT);
		
		isHouseMoving = true;
		if(isHouseMoving==true){
			mp3Bobobo.play();
		}
		house.registerEntityModifier(new PathModifier(3.35f,
				path, null, new IPathModifierListener() {
					@Override
					public void onPathWaypointStarted(
							PathModifier arg0, IEntity arg1,
							int arg2) {
						// TODO Auto-generated method stub
						isTouchHouse = false;
					}

					@Override
					public void onPathWaypointFinished(
							PathModifier arg0, IEntity arg1,
							int arg2) {
						// TODO Auto-generated method stub
						switch (arg2) {
						case 0:
							smoke();
							break;

						default:
							break;
						}
					}

					@Override
					public void onPathStarted(
							PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPathFinished(
							PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						house.registerEntityModifier(new DelayModifier(0.15f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
								asAnimatedSpriteSmoke
								.setVisible(false);
						isTouchHouse = true;
						house.setPosition(
								house.getmXFirst(),
								house.getmYFirst());
						countTouchhouse = 0;
						isTouchWindow1 = true;
						isTouchWindow2 = true;
						isTouchWindow3 = true;
						isTouchWindow4 = true;
						isHouseMoving =false;
							}
						}));
						
					}
				}));
	}

	private void showHanabiLine(final Sprite sprite, final Sprite animatedSprite) {

		setTouchGimmic3(false);
//		isTouchGimmic_1=false;
//		isTouchGimmic_2 =false;
		Path path = new Path(2).to(sprite.getmXFirst(),
				sprite.getmYFirst() + 120).to(sprite.getmXFirst(),
				sprite.getmYFirst());
		sprite.setPosition(sprite.getmXFirst(), sprite.getmYFirst() + 120);
		sprite.setVisible(true);
		sprite.registerEntityModifier(new PathModifier(1.5f, path, null,
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
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						sprite.setVisible(false);
						showHanabi(animatedSprite);
						isTouchGimmic_3=true;

					}
				}));
	}

	private void showHanabi(final Sprite sprite) {
		sprite.clearEntityModifiers();
		sprite.setVisible(true);
		sprite.registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				sprite.setVisible(false);
				
			}
		}));
	}


	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		isTouchWindow1 = true;
		isTouchWindow2 = true;
		isTouchWindow3 = true;
		isTouchWindow4 = true;
		isTouchHouse = true;
		countTouchhouse = 0;
		randomNoRepeat = new RanDomNoRepeat();
		randomActionFace = new RanDomNoRepeat();
	}

	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		super.onPauseGame();
		isTouchWindow1 = true;
		isTouchWindow2 = true;
		isTouchWindow3 = true;
		isTouchWindow4 = true;
		isTouchHouse = true;
		isTouchGimmic_1=true;
		isTouchGimmic_2=true;
		isTouchGimmic_3=true;
		countTouchhouse = 0;
		countTouchKoala = 0;
		countTouchPanda = 0;
		countTouchRapit = 0;
		imgSlideOrders = 0;

		lastX = 0;
		spase = 100.0f;
		setTouchGimmic3(true);
		mp3Bobobo.stop();
		mp3Bomi.stop();
		mp3Gogogo.stop();
		mp3Gogogo2.stop();
		mp3Kiran.stop();
		for (int i = 0; i < spriteHanabiLine.length; i++) {
			if (spriteHanabiLine[i].isVisible() == true
					&& spriteHanabiLine[i] != null) {
				spriteHanabiLine[i].clearEntityModifiers();
				spriteHanabiLine[i].setVisible(false);
				spriteHanabiLine[i].setPosition(
						spriteHanabiLine[i].getmXFirst(),
						spriteHanabiLine[i].getmYFirst());
			}
		}
		if (asAnimatedSpriteKoala != null) {
			asAnimatedSpriteKoala.clearEntityModifiers();
			asAnimatedSpriteKoala.setCurrentTileIndex(0);
			asAnimatedSpriteKoala.setPosition(
					asAnimatedSpriteKoala.getmXFirst(),
					asAnimatedSpriteKoala.getmYFirst());
		}
		if (asAnimatedSpriteRabit != null) {
			asAnimatedSpriteRabit.clearEntityModifiers();
			asAnimatedSpriteRabit.setCurrentTileIndex(0);
			asAnimatedSpriteRabit.setPosition(
					asAnimatedSpriteRabit.getmXFirst(),
					asAnimatedSpriteRabit.getmYFirst());
		}
		if (asAnimatedSpritePanda != null) {
			asAnimatedSpritePanda.clearEntityModifiers();
			asAnimatedSpritePanda.setCurrentTileIndex(0);
			asAnimatedSpritePanda.setPosition(
					asAnimatedSpritePanda.getmXFirst(),
					asAnimatedSpritePanda.getmYFirst());
		}
		for (int i = 0; i < textPandaFace.length; i++) {
			if (spritePandaFace[i].isVisible() && spritePandaFace[i] != null) {
				spritePandaFace[i].setVisible(false);
				spritePandaFace[i].setPosition(spritePandaFace[i].getmXFirst(),
						spriteKoalaFace[i].getmYFirst());
			}
		}
		for (int i = 0; i < textKoalaFace.length; i++) {
			if (spriteKoalaFace[i].isVisible() && spriteKoalaFace[i] != null) {
				spriteKoalaFace[i].setVisible(false);
				spriteKoalaFace[i].setPosition(spriteKoalaFace[i].getmXFirst(),
						spriteKoalaFace[i].getmYFirst());
			}
		}
		for (int i = 0; i < textRapitface.length; i++) {
			if (spriteRapitFace[i].isVisible() && spriteRapitFace[i] != null) {
				spriteRapitFace[i].setVisible(false);
				spriteRapitFace[i].setPosition(spriteRapitFace[i].getmXFirst(),
						spriteKoalaFace[i].getmYFirst());
			}
		}
		if(spHanabiPanda.isVisible()&&spHanabiPanda!=null){
			spHanabiPanda.clearEntityModifiers();
			spHanabiPanda.setVisible(false);
			spHanabiPanda.setPosition(spHanabiPanda.getmXFirst(), spHanabiPanda.getmYFirst());
		}
		if(spHanabiKoala.isVisible()&&spHanabiKoala!=null){
			spHanabiKoala.clearEntityModifiers();
			spHanabiKoala.setVisible(false);
			spHanabiKoala.setPosition(spHanabiKoala.getmXFirst(), spHanabiKoala.getmYFirst());
		}
		if(spHanabiRapit.isVisible()&&spHanabiRapit!=null){
			spHanabiRapit.clearEntityModifiers();
			spHanabiRapit.setVisible(false);
			spHanabiRapit.setPosition(spHanabiRapit.getmXFirst(), spHanabiRapit.getmYFirst());
		}
		if (asAnimatedSpriteWindow1 != null) {
			asAnimatedSpriteWindow1.clearEntityModifiers();
			asAnimatedSpriteWindow1.setCurrentTileIndex(0);
			asAnimatedSpriteWindow1.setPosition(
					asAnimatedSpriteWindow1.getmXFirst(),
					asAnimatedSpriteWindow1.getmYFirst());
		}
		if (asAnimatedSpriteWindow2 != null) {
			asAnimatedSpriteWindow2.clearEntityModifiers();
			asAnimatedSpriteWindow2.setCurrentTileIndex(0);
			asAnimatedSpriteWindow2.setPosition(
					asAnimatedSpriteWindow2.getmXFirst(),
					asAnimatedSpriteWindow2.getmYFirst());
		}
		if (asAnimatedSpriteWindow3 != null) {
			asAnimatedSpriteWindow3.clearEntityModifiers();
			asAnimatedSpriteWindow3.setCurrentTileIndex(0);
			asAnimatedSpriteWindow3.setPosition(
					asAnimatedSpriteWindow3.getmXFirst(),
					asAnimatedSpriteWindow3.getmYFirst());
		}
		if (asAnimatedSpriteWindow4 != null) {
			asAnimatedSpriteWindow4.clearEntityModifiers();
			asAnimatedSpriteWindow4.setCurrentTileIndex(0);
			asAnimatedSpriteWindow4.setPosition(
					asAnimatedSpriteWindow4.getmXFirst(),
					asAnimatedSpriteWindow4.getmYFirst());
		}
		if (spHouse != null) {
			spHouse.clearEntityModifiers();
			spHouse.setPosition(spHouse.getmXFirst(), spHouse.getmYFirst());
		}
		if (asAnimatedSpriteSmoke != null
				&& asAnimatedSpriteSmoke.isVisible() == true) {
			asAnimatedSpriteSmoke.setVisible(false);
			asAnimatedSpriteSmoke.clearEntityModifiers();
			asAnimatedSpriteSmoke.setPosition(
					asAnimatedSpriteSmoke.getmXFirst(),
					asAnimatedSpriteSmoke.getmYFirst());
		}
	}

	private void create_Img(int x, int y, final Sprite sprite) {
		float XY = Math.round((x+sprite.getWidth()) + (y+sprite.getHeight()));
		if (Math.abs(XY - lastX) >= spase) {
			Log.d(TAG, "lastX : "+lastX);
			if (y > 175) {
				return;
			}
			sprite.setPosition(x, y);
			sprite.setVisible(true);
			if (imgSlideOrders == 0) {
				mp3Kiran.play();
			}
			imgSlideOrders++;
			lastX = XY;
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pScenceTouch) {
		// TODO Auto-generated method stub
		int pX = (int) pScenceTouch.getX();
		int pY = (int) pScenceTouch.getY();
		if (pScenceTouch.getAction() == TouchEvent.ACTION_MOVE) {
			if (imgSlideOrders <= 6) {
				switch (rand) {
				case 0:
					create_Img(pX, pY, spritePandaFace[imgSlideOrders]);
					break;
				case 1:
					create_Img(pX, pY, spriteRapitFace[imgSlideOrders]);
					break;
				case 2:
					create_Img(pX, pY, spriteKoalaFace[imgSlideOrders]);
					break;
				default:
					break;
				}
			}
		}
		if (pScenceTouch.getAction() == TouchEvent.ACTION_UP) {
			imgSlideOrders = 0;
			randomActionFace.setLenghNoRepeat(3);
			rand = randomActionFace.getNextIntNoRepeat(true);
			for (int i = 0; i < spritePandaFace.length; i++) {
				if (spritePandaFace[i].isVisible()) {
					spritePandaFace[i].setVisible(false);
					spritePandaFace[i].setPosition(
							spritePandaFace[i].getmXFirst(),
							spritePandaFace[i].getmYFirst());
				}
			}
			for (int i = 0; i < spriteRapitFace.length; i++) {
				if (spriteRapitFace[i].isVisible()) {
					spriteRapitFace[i].setVisible(false);
					spriteRapitFace[i].setPosition(
							spriteRapitFace[i].getmXFirst(),
							spriteRapitFace[i].getmYFirst());
				}
			}
			for (int i = 0; i < spriteKoalaFace.length; i++) {
				if (spriteKoalaFace[i].isVisible()) {
					spriteKoalaFace[i].setVisible(false);
					spriteKoalaFace[i].setPosition(
							spriteKoalaFace[i].getmXFirst(),
							spriteKoalaFace[i].getmYFirst());
				}
			}
		}
		if (asAnimatedSpritePanda.contains(pX, pY)) {
			if (pScenceTouch.getAction() == TouchEvent.ACTION_DOWN) {
				if (isTouchKoala) {
					mp3Bomi.play();
					isTouchKoala = false;
					countTouchPanda++;
					if (countTouchPanda == 3) {
						countTouchPanda = 0;
					}
					asAnimatedSpritePanda.setCurrentTileIndex(countTouchPanda);
					asAnimatedSpritePanda.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							isTouchKoala = true;
						}
					}));
				}
			}
		}
		if (checkContains(asAnimatedSpriteKoala, 27, 23, 153, 200, pX, pY)) {
			if (pScenceTouch.getAction() == TouchEvent.ACTION_DOWN) {
				if (isTouchKoala) {
					countTouchKoala++;
					if (countTouchKoala == 3) {
						countTouchKoala = 0;
					}
					mp3Bomi.play();
					isTouchKoala = false;
					asAnimatedSpriteKoala.setCurrentTileIndex(countTouchKoala);
					asAnimatedSpriteKoala.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							isTouchKoala=true;
						}
					}));
				}
			}
		}
		if (checkContains(asAnimatedSpriteRabit, 40, 26, 160, 270, pX, pY)) {
			if (pScenceTouch.getAction() == TouchEvent.ACTION_DOWN) {
				if (isTouchKoala) {
						mp3Bomi.play();
					
						isTouchKoala = false;
					countTouchRapit++;
					if (countTouchRapit == 3) {
						countTouchRapit = 0;
					}
					asAnimatedSpriteRabit.setCurrentTileIndex(countTouchRapit);
					asAnimatedSpriteRabit.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							isTouchKoala = true;
						}
					}));
				}
			}
		}
		if (checkContains(spHouse, 46, 44, 344, 164, pX, pY)
				) {
			if (pScenceTouch.getAction() == TouchEvent.ACTION_DOWN) {
				if (isTouchHouse) {
				Path	path;

					countTouchhouse++;
					float duration = 0.75f;
					if (countTouchhouse == 1) {
						path = new Path(5).to(spHouse.getmXFirst(), spHouse.getmYFirst())
								.to(spHouse.getmXFirst() + 20, spHouse.getmYFirst())
								.to(spHouse.getmXFirst(), spHouse.getmYFirst())
								.to(spHouse.getmXFirst() + 20, spHouse.getmYFirst())
								.to(spHouse.getmXFirst(), spHouse.getmYFirst());
						mp3Gogogo.play();
						houseTouchAction(spHouse, duration,path);
					}
					if (countTouchhouse == 2) {
						path = new Path(7).to(spHouse.getmXFirst(), spHouse.getmYFirst())
								.to(spHouse.getmXFirst() + 35, spHouse.getmYFirst())
								.to(spHouse.getmXFirst(), spHouse.getmYFirst())
								.to(spHouse.getmXFirst() + 35, spHouse.getmYFirst())
								.to(spHouse.getmXFirst(), spHouse.getmYFirst())
								.to(spHouse.getmXFirst() + 35, spHouse.getmYFirst())
								.to(spHouse.getmXFirst(), spHouse.getmYFirst());
						duration = 2.55f;
						mp3Gogogo2.play();
						houseTouchAction(spHouse, duration,path);
					}
					if (countTouchhouse == 3) {
						asAnimatedSpriteSmoke.setVisible(true);
						asAnimatedSpriteSmoke.setCurrentTileIndex(1);
						isTouchHouse = false;
						
						houseTouchThreeAction(spHouse);
					}
				}
			}
		}
		return false;
	}

	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		
	}
	
}
