package jp.co.xing.utaehon03.songs;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YagiyubinResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseSineInOut;

import android.util.Log;

public class Vol3Yagiyubin extends BaseGameFragment implements
		IOnSceneTouchListener, IEntityModifierListener {

	private String TAG_LOG = Vol3Yagiyubin.this.getClass().toString();
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion txrBackground, txrBackground2, txrLeftHouse,
			txrRightHouse, txrTree1, txrTree2, txrTree3, txrTree4, txrTree5,
			txrBoxMailHouseLeft, txrBoxMailHouseRight, txrLetterAreaTouched,
			txrLeftLetterDogFrontEnd, txrRightLetterDogFrontEnd;
	private ITextureRegion[] txrLeaf = new ITextureRegion[3];
	private ITextureRegion[] txrLeftDogFrontend = new ITextureRegion[5];
	private int[] resourceLeftDogFrontend = {
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_1_IPHONE_LEFTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_2_IPHONE_LEFTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_3_IPHONE_LEFTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_4_IPHONE_LEFTBODY_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_5_IPHONE_LEFTBODY_ID };

	private ITextureRegion[] txrRightDogFrontend = new ITextureRegion[5];
	private int[] resourceRightDogFrontend = {
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_1_IPHONE_RIGHTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_2_IPHONE_RIGHTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_3_IPHONE_RIGHTFACE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_4_IPHONE_RIGHTBODY_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_5_IPHONE_RIGHTBODY_ID };

	private ITextureRegion[] txrLeftDog = new ITextureRegion[6];
	private int[] resourceLeftDog = {
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_1_IPHONE_LEFTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_2_IPHONE_LEFTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_3_IPHONE_LEFTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_4_IPHONE_LEFTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_5_IPHONE_LEFTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_6_IPHONE_LEFTHOUSE_ID };

	private ITextureRegion[] txrRightDog = new ITextureRegion[6];
	private int[] resourceRightDog = {
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_1_IPHONE_RIGHTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_2_IPHONE_RIGHTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_3_IPHONE_RIGHTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_4_IPHONE_RIGHTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_5_IPHONE_RIGHTHOUSE_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_6_IPHONE_RIGHTHOUSE_ID };

	private ITextureRegion[] txrLeftPost = new ITextureRegion[3];
	private int[] resourceLeftPost = {
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_1_IPHONE_LEFTPOST_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_3_IPHONE_LEFTPOST_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_4_IPHONE_LEFTPOST_ID };

	private ITextureRegion[] txrRightPost = new ITextureRegion[3];
	private int[] resourceRightPost = {
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_1_IPHONE_RIGHTPOST_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_3_IPHONE_RIGHTPOST_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_4_IPHONE_RIGHTPOST_ID };
	private ITextureRegion[] txrLetter = new ITextureRegion[2];
	private int[] resourceLetter = {
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_05_1_IPHONE_LETTER_ID,
			Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_05_2_IPHONE_LETTER_ID };

	private Sprite sBackground, sBackground2, sLeafOne, sLeafTwo, sLeafThree,
			sLeftHouse, sRightHouse, sTree1, sTree2, sTree3, sTree4, sTree5,
			sLeftDogWrite, sLeftDogWater, sRightDogWrite, sRightDogWater,
			sBoxMailHouseLeft, sBoxMailHouseRight, sLeftPost, sRightPost,
			sLetterAreaTouched, sLeftLetterDogFrontend,
			sRightLetterDogFrontend, sUniFrontLeft, sUniFrontRight;
	private AnimatedSprite aSLeftDog, aSRightDog, aSLeftPost, aSRightPost,
			aSUni, aSLeftDogFrontendFace, aSRightDogFrontendFace,
			aSLeftDogFrontendBody, aSRightDogFrontendBody, aSLetter;
	private TiledTextureRegion ttrLeftDog, ttrRightDog, ttrLeftPost,
			ttrRightPost, ttrLeftDogFrontend, ttrRightDogFrontend, ttrUni,
			ttrLetter;
	private int touchLeftDog = 0, touchRightDog = 0, tempTouchLeftDog = 0,
			tempTouchRightDog = 0;
	private boolean touchLeftPostRunUni = true, touchRightPostRunUni = true,
			isTouchUni = false;
	private boolean isTouchTrees = true;
	private boolean isTouchBoxMailHouse = false;
	private int indexBoxMailHouse = 0;
	private boolean isTouchLetter = false;
	private boolean isLeftDogEatLetter = false;
	private boolean isRightDogEatLetter = false;
	private boolean isTouchGimic = false;
	private boolean isOtegami2Play = false;
	private boolean isMoveLetter = false;
	private Sound mp3Inug, mp3Otegami2, mp3Syu, mp3Taberu2, mp3Watashimo,
			mp3Bokunimo, mp3Kakon2, mp3Paka, mp3Kaki, mp3Mogu, mp3Kasa2;
	private boolean isTouchAction = false;

	private TexturePack Vol3YagiubinPacker_1, Vol3YagiubinPacker_2;
	private TexturePackTextureRegionLibrary Vol3YagiyubinLibrary1,
			Vol3YagiyubinLibrary2;

	@Override
	public void onCreateResources() {
		Log.d(TAG_LOG, "Create Resource");
		SoundFactory.setAssetBasePath("yagiyubin/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("yagiyubin/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "yagiyubin/gfx/");
		super.onCreateResources();
	}

	@Override
	protected void loadKaraokeSound() {
		mp3Inug = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_04_INUG);
		mp3Otegami2 = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_04_OTEGAMI2);
		mp3Syu = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_05_SYU);
		mp3Taberu2 = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_06_07_TABERU2);
		mp3Watashimo = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_06_WATASHIMO);
		mp3Bokunimo = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_07_BOKUNIMO);
		mp3Kakon2 = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_08_09_KAKON2);
		mp3Paka = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_08_09_PAKA);
		mp3Kaki = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_10_11_KAKI);
		mp3Mogu = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_10_11_MOGU);
		mp3Kasa2 = loadSoundResourceFromSD(Vol3YagiyubinResource.A10_12_KASA2);
	}

	@Override
	public void loadKaraokeResources() {
		Vol3YagiubinPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3YagiubinPacker1.xml");
		Vol3YagiubinPacker_1.loadTexture();
		Vol3YagiyubinLibrary1 = Vol3YagiubinPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3YagiubinPacker_2 = mTexturePackLoaderFromSource
				.load("Vol3YagiubinPacker2.xml");
		Vol3YagiubinPacker_2.loadTexture();
		Vol3YagiyubinLibrary2 = Vol3YagiubinPacker_2
				.getTexturePackTextureRegionLibrary();

		txrBackground = Vol3YagiyubinLibrary2
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_13_1_IPHONE_HAIKEI_ID);
		txrBackground2 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_13_2_IPHONE_HAIKEI_ID);

		txrLeftHouse = Vol3YagiyubinLibrary2
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_7_IPHONE_LEFTHOUSE_ID);
		txrRightHouse = Vol3YagiyubinLibrary2
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_7_IPHONE_RIGHTHOUSE_ID);
		txrTree1 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_1_IPHONE_TREE_ID);
		txrTree2 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_2_IPHONE_TREE_ID);
		txrTree3 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_3_IPHONE_TREE_ID);
		txrTree4 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_4_IPHONE_TREE_ID);
		txrTree5 = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_5_IPHONE_TREE_ID);
		// Tree leaf
		txrLeaf[0] = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_6_IPHONE_LEAF_ID);
		txrLeaf[1] = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_7_IPHONE_LEAF_ID);
		txrLeaf[2] = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_12_8_IPHONE_LEAF_ID);
		// leftDog
		for (int i = 0; i < txrLeftDog.length; i++) {
			txrLeftDog[i] = Vol3YagiyubinLibrary2.get(resourceLeftDog[i]);
		}
		// Create a TitledTextureRegion for LeftDog
		ttrLeftDog = getTiledTextureFromPacker(
				Vol3YagiubinPacker_2,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_1_IPHONE_LEFTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_2_IPHONE_LEFTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_3_IPHONE_LEFTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_4_IPHONE_LEFTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_5_IPHONE_LEFTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_6_IPHONE_LEFTHOUSE_ID);
		for (int i = 0; i < txrLeftDog.length; i++) {
			txrRightDog[i] = Vol3YagiyubinLibrary2.get(resourceRightDog[i]);
		}
		// Create a TitledTextureRegion for RightDog
		ttrRightDog = getTiledTextureFromPacker(
				Vol3YagiubinPacker_2,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_1_IPHONE_RIGHTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_2_IPHONE_RIGHTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_3_IPHONE_RIGHTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_4_IPHONE_RIGHTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_5_IPHONE_RIGHTHOUSE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_6_IPHONE_RIGHTHOUSE_ID);
		txrBoxMailHouseLeft = Vol3YagiyubinLibrary2
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_10_8_IPHONE_LEFTHOUSE_ID);
		txrBoxMailHouseRight = Vol3YagiyubinLibrary2
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_11_8_IPHONE_RIGHTHOUSE_ID);
		// Uni
		// Su dung chia theo hang cot de tiet kiem bo nho
		ttrUni = getTiledTextureFromPacker(
				Vol3YagiubinPacker_1,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_1_IPHONE_LEFTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_2_IPHONE_LEFTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_3_IPHONE_LEFTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_3_IPHONE_LEFTLETTERINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_4_IPHONE_LEFTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_4_IPHONE_LEFTLETTERINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_5_IPHONE_RIGHTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_6_IPHONE_RIGHTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_7_IPHONE_RIGHTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_7_IPHONE_RIGHTLETTERINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_8_IPHONE_RIGHTINU_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_8_IPHONE_RIGHTLETTERINU_ID);
		// End Uni
		// LeftPost
		// Create a Texture
		for (int i = 0; i < txrLeftPost.length; i++) {
			txrLeftPost[i] = Vol3YagiyubinLibrary2.get(resourceLeftPost[i]);
		}
		// Create a TitledTextureRegion for LeftPost
		ttrLeftPost = getTiledTextureFromPacker(
				Vol3YagiubinPacker_2,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_1_IPHONE_LEFTPOST_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_3_IPHONE_LEFTPOST_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_08_4_IPHONE_LEFTPOST_ID);
		// End LeftPost
		// RightPost
		// Create a Texture
		for (int i = 0; i < txrRightPost.length; i++) {
			txrRightPost[i] = Vol3YagiyubinLibrary2.get(resourceRightPost[i]);
		}
		// Create a TitledTextureRegion for LeftPost
		ttrRightPost = getTiledTextureFromPacker(
				Vol3YagiubinPacker_2,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_1_IPHONE_RIGHTPOST_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_3_IPHONE_RIGHTPOST_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker2.A10_09_4_IPHONE_RIGHTPOST_ID);
		// End RightPost
		for (int i = 0; i < txrLeftDogFrontend.length; i++) {
			txrLeftDogFrontend[i] = Vol3YagiyubinLibrary1
					.get(resourceLeftDogFrontend[i]);
		}
		// Create a TitledTextureRegion for LeftDogFrontend
		ttrLeftDogFrontend = getTiledTextureFromPacker(
				Vol3YagiubinPacker_1,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_1_IPHONE_LEFTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_2_IPHONE_LEFTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_3_IPHONE_LEFTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_4_IPHONE_LEFTBODY_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_5_IPHONE_LEFTBODY_ID);
		for (int i = 0; i < txrRightDogFrontend.length; i++) {
			txrRightDogFrontend[i] = Vol3YagiyubinLibrary1
					.get(resourceRightDogFrontend[i]);
		}
		// Create a TitledTextureRegion for RightDogFrontend
		ttrRightDogFrontend = getTiledTextureFromPacker(
				Vol3YagiubinPacker_1,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_1_IPHONE_RIGHTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_2_IPHONE_RIGHTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_3_IPHONE_RIGHTFACE_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_4_IPHONE_RIGHTBODY_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_07_5_IPHONE_RIGHTBODY_ID);
		// Letter
		txrLetterAreaTouched = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_9_IPHONE_LETTER_ID);
		for (int i = 0; i < txrLetter.length; i++) {
			txrLetter[i] = Vol3YagiyubinLibrary1.get(resourceLetter[i]);
		}
		// Create a TitledTextureRegion for Letter
		ttrLetter = getTiledTextureFromPacker(
				Vol3YagiubinPacker_1,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_05_1_IPHONE_LETTER_ID,
				Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_05_2_IPHONE_LETTER_ID);

		txrLeftLetterDogFrontEnd = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_6_IPHONE_LETTER_ID);
		txrRightLetterDogFrontEnd = Vol3YagiyubinLibrary1
				.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_06_6_IPHONE_LETTER_ID);
	}

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		sBackground = new Sprite(0, 0, txrBackground,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground);

		sBackground2 = new Sprite(405, 359, txrBackground2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBackground2);

		sLeftHouse = new Sprite(-62, -82, txrLeftHouse,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeftHouse);

		sRightHouse = new Sprite(643, -81, txrRightHouse,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sRightHouse);

		sTree2 = new Sprite(386, 0, txrTree2,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree2);

		sTree1 = new Sprite(322, -149, txrTree1,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree1);

		// Tree leaf
		sLeafThree = new Sprite(606, 40, txrLeaf[2],
				this.getVertexBufferObjectManager());
		sLeafThree.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mScene.attachChild(sLeafThree);
		sTree5 = new Sprite(521, -10, txrTree5,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree5);

		sLeafTwo = new Sprite(500, 22, txrLeaf[1],
				this.getVertexBufferObjectManager());
		sLeafTwo.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mScene.attachChild(sLeafTwo);
		sTree4 = new Sprite(408, -78, txrTree4,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree4);

		sLeafOne = new Sprite(325, 27, txrLeaf[0],
				this.getVertexBufferObjectManager());
		sLeafThree.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mScene.attachChild(sLeafOne);
		sTree3 = new Sprite(318, -63, txrTree3,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sTree3);
		// Left Dog
		sLeftDogWrite = new Sprite(102, 107, txrLeftDog[0],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeftDogWrite);

		sLeftDogWater = new Sprite(102, 107, txrLeftDog[1],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeftDogWater);
		sLeftDogWater.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// SetAnimatedSprite fof Left Dog
		aSLeftDog = new AnimatedSprite(102, 107, ttrLeftDog,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSLeftDog);

		// Right Dog
		sRightDogWrite = new Sprite(708, 107, txrRightDog[0],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sRightDogWrite);

		sRightDogWater = new Sprite(708, 107, txrRightDog[1],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sRightDogWater);
		sRightDogWater.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		// SetAnimatedSprite for Left Dog
		aSRightDog = new AnimatedSprite(708, 107, ttrRightDog,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSRightDog);

		// Box Mail House Left
		sBoxMailHouseLeft = new Sprite(260, 198, txrBoxMailHouseLeft,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBoxMailHouseLeft);
		// Box Mail House Right

		sBoxMailHouseRight = new Sprite(580, 199, txrBoxMailHouseRight,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sBoxMailHouseRight);
		// Uni
		aSUni = new AnimatedSprite(321, 143, ttrUni,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSUni);
		// End Uni
		// Left Dog Frontend
		aSLeftDogFrontendBody = new AnimatedSprite(179, 316,
				ttrLeftDogFrontend, this.getVertexBufferObjectManager());
		aSLeftDogFrontendBody.setCurrentTileIndex(3);
		this.mScene.attachChild(aSLeftDogFrontendBody);
		// Set Sprire for Dog keep letter Left
		sLeftLetterDogFrontend = new Sprite(271, 468, txrLeftLetterDogFrontEnd,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeftLetterDogFrontend);

		// SetAnimatedSprite for LeftDogFrontend
		aSLeftDogFrontendFace = new AnimatedSprite(179, 316,
				ttrLeftDogFrontend.deepCopy(),
				this.getVertexBufferObjectManager());
		aSLeftDogFrontendFace.setCurrentTileIndex(0);
		this.mScene.attachChild(aSLeftDogFrontendFace);

		// Left Post
		sLeftPost = new Sprite(45, 298, txrLeftPost[0],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sLeftPost);
		// SetAnimatedSprite for LeftPost
		aSLeftPost = new AnimatedSprite(45, 298, ttrLeftPost,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSLeftPost);
		// Lay 1 cai TextureRegion tu mot Texture
		sUniFrontLeft = new Sprite(
				60,
				440,
				Vol3YagiyubinLibrary1
						.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_4_IPHONE_LEFTLETTERINU_ID),
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sUniFrontLeft);
		// Right Dog Frontend
		aSRightDogFrontendBody = new AnimatedSprite(547, 323,
				ttrRightDogFrontend, this.getVertexBufferObjectManager());
		aSRightDogFrontendBody.setCurrentTileIndex(3);
		this.mScene.attachChild(aSRightDogFrontendBody);
		// Set Sprire for Dog keep letter Right
		sRightLetterDogFrontend = new Sprite(628, 464,
				txrRightLetterDogFrontEnd, this.getVertexBufferObjectManager());
		this.mScene.attachChild(sRightLetterDogFrontend);
		// SetAnimatedSprite for LeftDogFrontend
		aSRightDogFrontendFace = new AnimatedSprite(547, 323,
				ttrRightDogFrontend.deepCopy(),
				this.getVertexBufferObjectManager());
		aSRightDogFrontendFace.setCurrentTileIndex(0);
		this.mScene.attachChild(aSRightDogFrontendFace);

		// Right Post
		sRightPost = new Sprite(718, 257, txrRightPost[0],
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sRightPost);
		// SetAnimatedSprite for RightPost
		aSRightPost = new AnimatedSprite(718, 257, ttrRightPost,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSRightPost);
		// Lay 1 cai TextureRegion tu mot Texture
		sUniFrontRight = new Sprite(
				640,
				440,
				Vol3YagiyubinLibrary1
						.get(Vol3YagiyubinResource.Vol3YagiubinPacker1.A10_04_7_IPHONE_RIGHTLETTERINU_ID),
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(sUniFrontRight);
		// Letter
		aSLetter = new AnimatedSprite(348, 424, ttrLetter,
				this.getVertexBufferObjectManager());
		this.mScene.attachChild(aSLetter);
		sLetterAreaTouched = new Sprite(0, 0, txrLetterAreaTouched,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int x = (int) pSceneTouchEvent.getX();
				int y = (int) pSceneTouchEvent.getY();
				if (pSceneTouchEvent.isActionMove()) {
					if (!isMoveLetter) {
						this.setPosition(pSceneTouchEvent.getX(),
								pSceneTouchEvent.getY());
					}
				}
				if (pSceneTouchEvent.isActionUp()) {
					if (isTouchLetter) {
						if ((x >= 89 && x <= 178) && (y >= 330 && y <= 560)) {
							if (aSLetter.getCurrentTileIndex() == 1
									&& touchLeftPostRunUni && !isMoveLetter) {
								// Xu ly thung thu trai
								isMoveLetter = true;
								mp3Kakon2.play();
								aSLeftPost.setCurrentTileIndex(2);
								aSLetter.registerEntityModifier(new DelayModifier(
										1.0f, new IEntityModifierListener() {
											@Override
											public void onModifierFinished(
													IModifier<IEntity> pModifier,
													IEntity pItem) {
												isMoveLetter = false;
												aSLetter.setCurrentTileIndex(0);
												sLetterAreaTouched
														.setVisible(false);
												aSLeftPost
														.setCurrentTileIndex(0);
												isTouchLetter = false;
											}

											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												// TODO Auto-generated method
												// stub

											}
										}));
							} else {
								sLetterAreaTouched.setVisible(false);
								aSLetter.setCurrentTileIndex(0);
								isTouchLetter = false;
							}
						} else if ((x >= 789 && x <= 896)
								&& (y >= 310 && y <= 632)) {
							if (aSLetter.getCurrentTileIndex() == 1
									&& touchRightPostRunUni && !isMoveLetter) {
								// Xu ly thung thu phai
								isMoveLetter = true;
								mp3Kakon2.play();
								aSRightPost.setCurrentTileIndex(2);
								aSLetter.registerEntityModifier(new DelayModifier(
										1.0f, new IEntityModifierListener() {
											@Override
											public void onModifierFinished(
													IModifier<IEntity> pModifier,
													IEntity pItem) {
												isMoveLetter = false;
												aSLetter.setCurrentTileIndex(0);
												sLetterAreaTouched
														.setVisible(false);
												aSRightPost
														.setCurrentTileIndex(0);
												isTouchLetter = false;
											}

											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												// TODO Auto-generated method
												// stub

											}
										}));
							} else {
								sLetterAreaTouched.setVisible(false);
								aSLetter.setCurrentTileIndex(0);
								isTouchLetter = false;
							}
						} else if ((x >= 230 && x <= 360)
								&& (y >= 345 && y <= 460)) {
							// Xu ly con cho phia ngoai ben trai
							isTouchLetter = false;
							if (!isLeftDogEatLetter
									&& !isRightDogEatLetter
									&& aSLeftDogFrontendFace
											.getCurrentTileIndex() == 0) {
								mp3Taberu2.play();
								isLeftDogEatLetter = true;
								isRightDogEatLetter = true;
								aSLetter.setCurrentTileIndex(0);
								sLetterAreaTouched.setVisible(false);
								aSLeftDogFrontendBody.setCurrentTileIndex(4);
								sLeftLetterDogFrontend.setVisible(true);
								aSRightDogFrontendFace.setCurrentTileIndex(2);
								float CoordirateX1[] = new float[] {
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX(),
										aSLeftDogFrontendFace.getX() };
								float CoordirateY1[] = new float[] {
										aSLeftDogFrontendFace.getY(),
										aSLeftDogFrontendFace.getY() - 10,
										aSLeftDogFrontendFace.getY() + 10,
										aSLeftDogFrontendFace.getY() - 10,
										aSLeftDogFrontendFace.getY(),
										aSLeftDogFrontendFace.getY() + 10,
										aSLeftDogFrontendFace.getY() };
								final Path path1 = new Path(CoordirateX1,
										CoordirateY1);
								aSLeftDogFrontendFace
										.registerEntityModifier(new PathModifier(
												1.5f, path1, null,
												new IPathModifierListener() {

													@Override
													public void onPathFinished(
															PathModifier arg0,
															IEntity arg1) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onPathStarted(
															PathModifier arg0,
															IEntity arg1) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onPathWaypointFinished(
															PathModifier arg0,
															IEntity arg1,
															int arg2) {
														switch (arg2) {
														case 3:
															aSLeftDogFrontendFace
																	.setCurrentTileIndex(0);
															aSRightDogFrontendFace
																	.setCurrentTileIndex(0);
															aSLeftDogFrontendBody
																	.setCurrentTileIndex(3);
															sLeftLetterDogFrontend
																	.setVisible(false);
															break;
														case 5:
															isLeftDogEatLetter = false;
															isRightDogEatLetter = false;
															break;
														default:
															break;
														}

													}

													@Override
													public void onPathWaypointStarted(
															PathModifier arg0,
															IEntity arg1,
															int arg2) {
														// TODO Auto-generated
														// method stub

													}
												}, EaseLinear.getInstance()));
							} else {
								sLetterAreaTouched.setVisible(false);
								aSLetter.setCurrentTileIndex(0);
								isTouchLetter = false;
							}
						} else if ((x >= 570 && x <= 700)
								&& (y >= 323 && y <= 460)) {
							// Xu ly con cho phia ngoai ben phai
							isTouchLetter = false;
							if (!isRightDogEatLetter
									&& !isLeftDogEatLetter
									&& aSRightDogFrontendFace
											.getCurrentTileIndex() == 0) {
								mp3Taberu2.play();
								isLeftDogEatLetter = true;
								isRightDogEatLetter = true;
								aSLetter.setCurrentTileIndex(0);
								sLetterAreaTouched.setVisible(false);
								aSRightDogFrontendBody.setCurrentTileIndex(4);
								sRightLetterDogFrontend.setVisible(true);
								aSLeftDogFrontendFace.setCurrentTileIndex(2);
								float CoordirateX1[] = new float[] {
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX(),
										aSRightDogFrontendFace.getX() };
								float CoordirateY1[] = new float[] {
										aSRightDogFrontendFace.getY(),
										aSRightDogFrontendFace.getY() - 10,
										aSRightDogFrontendFace.getY() + 10,
										aSRightDogFrontendFace.getY() - 10,
										aSRightDogFrontendFace.getY(),
										aSRightDogFrontendFace.getY() + 10,
										aSRightDogFrontendFace.getY() };
								final Path path1 = new Path(CoordirateX1,
										CoordirateY1);
								aSRightDogFrontendFace
										.registerEntityModifier(new PathModifier(
												1.5f, path1, null,
												new IPathModifierListener() {

													@Override
													public void onPathFinished(
															PathModifier arg0,
															IEntity arg1) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onPathStarted(
															PathModifier arg0,
															IEntity arg1) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onPathWaypointFinished(
															PathModifier arg0,
															IEntity arg1,
															int arg2) {
														switch (arg2) {
														case 3:
															aSRightDogFrontendFace
																	.setCurrentTileIndex(0);
															aSLeftDogFrontendFace
																	.setCurrentTileIndex(0);
															aSRightDogFrontendBody
																	.setCurrentTileIndex(3);
															sRightLetterDogFrontend
																	.setVisible(false);
															break;
														case 5:
															isRightDogEatLetter = false;
															isLeftDogEatLetter = false;
															break;
														default:
															break;
														}

													}

													@Override
													public void onPathWaypointStarted(
															PathModifier arg0,
															IEntity arg1,
															int arg2) {
														// TODO Auto-generated
														// method stub

													}
												}, EaseLinear.getInstance()));
							} else {
								sLetterAreaTouched.setVisible(false);
								aSLetter.setCurrentTileIndex(0);
								isTouchLetter = false;
							}
						} else {
							sLetterAreaTouched.setVisible(false);
							aSLetter.setCurrentTileIndex(0);
							isTouchLetter = false;
						}
					}
					isTouchAction = true;
				}
				return true;
			}
		};
		sLetterAreaTouched.setScale(2.0f);
		this.mScene.registerTouchArea(sLetterAreaTouched);
		this.mScene.attachChild(sLetterAreaTouched);
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(this.mScene, Vol3YagiyubinResource.SOUND_GIMMIC,
				Vol3YagiyubinResource.IMAGE_GIMMIC, Vol3YagiyubinLibrary1);
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		sLeafOne.setVisible(false);
		sLeafTwo.setVisible(false);
		sLeafThree.setVisible(false);
		sUniFrontRight.setVisible(false);
		sUniFrontLeft.setVisible(false);
		sLetterAreaTouched.setVisible(false);
		sLeftLetterDogFrontend.setVisible(false);
		sRightLetterDogFrontend.setVisible(false);
		leftDogWriting();
		rightDogWriting();
		// Default Dog run from left to Right
		UniLeftToRightLineNotLeterModifier();
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		touchLeftDog = 0;
		touchRightDog = 0;
		tempTouchLeftDog = 0;
		tempTouchRightDog = 0;
		touchLeftPostRunUni = true;
		touchRightPostRunUni = true;
		isTouchUni = false;
		isTouchTrees = true;
		isTouchBoxMailHouse = false;
		indexBoxMailHouse = 0;
		isTouchLetter = false;
		isLeftDogEatLetter = false;
		isRightDogEatLetter = false;
		isTouchGimic = false;
		isOtegami2Play = false;
		isMoveLetter = false;
		isTouchAction = false;
		if (aSUni != null) {
			aSUni.clearEntityModifiers();
			aSUni.setVisible(true);
			sUniFrontLeft.setVisible(false);
			sUniFrontRight.setVisible(false);
			aSUni.setPosition(321, 143);
		}
		if (aSLetter != null) {
			aSLetter.clearEntityModifiers();
			aSLetter.setCurrentTileIndex(0);
			aSLetter.setPosition(348, 424);
		}
		if (aSLeftPost != null) {
			aSLeftPost.setCurrentTileIndex(0);
		}
		if (aSRightPost != null) {
			aSRightPost.setCurrentTileIndex(0);
		}
		// Dog front end
		if (aSLeftDogFrontendBody != null) {
			aSLeftDogFrontendBody.setCurrentTileIndex(3);
			aSLeftDogFrontendBody.setPosition(179, 316);
		}
		if (sLeftLetterDogFrontend != null) {
			sLeftLetterDogFrontend.setVisible(false);
			sLeftLetterDogFrontend.setPosition(271, 468);
		}
		if (aSLeftDogFrontendFace != null) {
			aSLeftDogFrontendFace.clearEntityModifiers();
			aSLeftDogFrontendFace.setCurrentTileIndex(0);
			aSLeftDogFrontendFace.setPosition(179, 316);
		}
		if (aSRightDogFrontendBody != null) {
			aSRightDogFrontendBody.setCurrentTileIndex(3);
			aSRightDogFrontendBody.setPosition(547, 323);
		}
		if (sRightLetterDogFrontend != null) {
			sRightLetterDogFrontend.setVisible(false);
			sRightLetterDogFrontend.setPosition(628, 464);
		}
		if (aSRightDogFrontendFace != null) {
			aSRightDogFrontendFace.clearEntityModifiers();
			aSRightDogFrontendFace.setCurrentTileIndex(0);
			aSRightDogFrontendFace.setPosition(547, 323);
		}
		if (sTree2 != null) {
			sTree2.clearEntityModifiers();
			sTree2.setPosition(386, 0);
		}
		if (sTree3 != null) {
			sTree3.clearEntityModifiers();
			sTree3.setPosition(318, -63);
		}
		if (sTree4 != null) {
			sTree4.clearEntityModifiers();
			sTree4.setPosition(408, -78);
		}
		if (sTree5 != null) {
			sTree5.clearEntityModifiers();
			sTree5.setPosition(521, -10);
		}

		if (sLeafOne != null) {
			sLeafOne.clearEntityModifiers();
			sLeafOne.setPosition(325, 27);
			sLeafOne.setVisible(false);
		}
		if (sLeafTwo != null) {
			sLeafTwo.clearEntityModifiers();
			sLeafTwo.setPosition(500, 22);
			sLeafTwo.setVisible(false);
		}
		if (sLeafThree != null) {
			sLeafThree.setPosition(604, 40);
			sLeafThree.clearEntityModifiers();
			sLeafThree.setVisible(false);
		}

	}

	// Process Tree
	// + Process foliage of tree
	public void foliageTrees() {
		isTouchTrees = false;
		mp3Kasa2.play();
		setTouchGimmic3(false);
		final Path path2 = new Path(4).to(sTree2.getX(), sTree2.getY())
				.to(sTree2.getX() - 10, sTree2.getY())
				.to(sTree2.getX() + 10, sTree2.getY())
				.to(sTree2.getX(), sTree2.getY());
		PathModifier pathTree2Modifier = new PathModifier(1.04f, path2, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						if (arg2 == 2) {
							treeLeafTree();
						}

					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance());
		sTree2.registerEntityModifier(pathTree2Modifier);
		final Path path3 = new Path(4).to(sTree3.getX(), sTree3.getY())
				.to(sTree3.getX() - 10, sTree3.getY())
				.to(sTree3.getX() + 10, sTree3.getY())
				.to(sTree3.getX(), sTree3.getY());
		PathModifier pathTree3Modifier = new PathModifier(1.0f, path3,
				EaseSineInOut.getInstance());
		sTree3.registerEntityModifier(pathTree3Modifier);

		final Path path4 = new Path(4).to(sTree4.getX(), sTree4.getY())
				.to(sTree4.getX() - 10, sTree4.getY())
				.to(sTree4.getX() + 10, sTree4.getY())
				.to(sTree4.getX(), sTree4.getY());
		PathModifier pathTree4Modifier = new PathModifier(1.0f, path4,
				EaseLinear.getInstance());
		sTree4.registerEntityModifier(pathTree4Modifier);
		final Path path5 = new Path(4).to(sTree5.getX(), sTree5.getY())
				.to(sTree5.getX() - 10, sTree5.getY())
				.to(sTree5.getX() + 10, sTree5.getY())
				.to(sTree5.getX(), sTree5.getY());
		PathModifier pathTree5Modifier = new PathModifier(1.0f, path5,
				EaseLinear.getInstance());
		sTree5.registerEntityModifier(pathTree5Modifier);
	}

	// + Process tree leaf
	public void treeLeafTree() {
		sLeafOne.setVisible(true);
		sLeafTwo.setVisible(true);
		sLeafThree.setVisible(true);
		ParallelEntityModifier leafOneModifier = new ParallelEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sLeafOne.setPosition(325, 27);
						sLeafOne.setBlendFunction(GL10.GL_SRC_ALPHA,
								GL10.GL_ONE_MINUS_SRC_ALPHA);
						sLeafOne.setAlpha(1.0f);
						sLeafOne.setVisible(false);
						isTouchTrees = true;
						isTouchGimic = false;
						setTouchGimmic3(true);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub

					}
				}, new SequenceEntityModifier(new DelayModifier(1.9f),
						new AlphaModifier(1, 0.5f, 0.5f)),
				new SequenceEntityModifier(new DelayModifier(0.4f),
						new MoveModifier(1.5f, sLeafOne.getX(),
								sLeafOne.getX(), sLeafOne.getY(), 300)));
		sLeafOne.registerEntityModifier(leafOneModifier);
		ParallelEntityModifier leafTwoModifier = new ParallelEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sLeafTwo.setPosition(505, 22);
						sLeafTwo.setBlendFunction(GL10.GL_SRC_ALPHA,
								GL10.GL_ONE_MINUS_SRC_ALPHA);
						sLeafTwo.setAlpha(1.0f);
						sLeafTwo.setVisible(false);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub

					}
				}, new SequenceEntityModifier(new DelayModifier(1.7f),
						new AlphaModifier(1, 0.5f, 0.5f)),
				new SequenceEntityModifier(new DelayModifier(0.2f),
						new MoveModifier(1.5f, sLeafTwo.getX(),
								sLeafTwo.getX(), sLeafTwo.getY(), 300)));
		sLeafTwo.registerEntityModifier(leafTwoModifier);
		ParallelEntityModifier leafThreeModifier = new ParallelEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						sLeafThree.setPosition(604, 40);
						sLeafThree.setBlendFunction(GL10.GL_SRC_ALPHA,
								GL10.GL_ONE_MINUS_SRC_ALPHA);
						sLeafThree.setAlpha(1.0f);
						sLeafThree.setVisible(false);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub

					}
				}, new SequenceEntityModifier(new DelayModifier(1.5f),
						new AlphaModifier(1, 0.5f, 0.5f)),
				new SequenceEntityModifier(new MoveModifier(1.5f, sLeafThree
						.getX(), sLeafThree.getX(), sLeafThree.getY(), 300)));
		sLeafThree.registerEntityModifier(leafThreeModifier);
	}

	// Processing LeftDog
	public void leftDogWriting() {
		aSLeftDog.stopAnimation();
		aSLeftDog.setCurrentTileIndex(0);
		sLeftDogWrite.setVisible(true);
		sLeftDogWater.setVisible(true);
		sLeftDogWater.clearEntityModifiers();
		sLeftDogWater.registerEntityModifier(new LoopEntityModifier(
				new AlphaModifier(0.6f, 1.0f, 0.0f), 4,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						leftDogSleep();
					}
				}));
	}

	public void leftDogSleep() {
		touchLeftDog = 0;
		tempTouchLeftDog = 1;
		sLeftDogWrite.setVisible(false);
		sLeftDogWater.setVisible(false);
		if (!aSLeftDog.isAnimationRunning()) {
			long pDurationleftDogSleep[] = new long[] { 400, 400 };
			int pFramsleftDogSleep[] = new int[] { 2, 3 };
			aSLeftDog.animate(pDurationleftDogSleep, pFramsleftDogSleep, -1);
		}
	}

	public void leftDogEat() {
		sLeftDogWrite.setVisible(false);
		sLeftDogWater.setVisible(false);
		if (!aSLeftDog.isAnimationRunning()) {
			long pDurationleftDogEat[] = new long[] { 450, 450 };
			int pFramsleftDogEat[] = new int[] { 4, 5 };
			aSLeftDog.animate(pDurationleftDogEat, pFramsleftDogEat, 2,
					new IAnimationListener() {
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							touchLeftDog = 1;
							leftDogWriting();
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {
						}
					});
		}
	}

	// Processing RightDog
	public void rightDogWriting() {
		aSRightDog.stopAnimation();
		aSRightDog.setCurrentTileIndex(0);
		sRightDogWrite.setVisible(true);
		sRightDogWater.setVisible(true);
		sRightDogWater.clearEntityModifiers();
		sRightDogWater.registerEntityModifier(new LoopEntityModifier(
				new AlphaModifier(0.6f, 1.0f, 0.0f), 4,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						rightDogSleep();
					}
				}));
	}

	public void rightDogSleep() {
		touchRightDog = 0;
		tempTouchRightDog = 1;
		sRightDogWrite.setVisible(false);
		sRightDogWater.setVisible(false);
		if (!aSRightDog.isAnimationRunning()) {
			long pDurationrightDogSleep[] = new long[] { 400, 400 };
			int pFramsrightDogSleep[] = new int[] { 2, 3 };
			aSRightDog.animate(pDurationrightDogSleep, pFramsrightDogSleep, -1);
		}
	}

	public void rightDogEat() {
		sRightDogWrite.setVisible(false);
		sRightDogWater.setVisible(false);
		if (!aSRightDog.isAnimationRunning()) {
			long pDurationrightDogEat[] = new long[] { 450, 450 };
			int pFramsrightDogEat[] = new int[] { 4, 5 };
			aSRightDog.animate(pDurationrightDogEat, pFramsrightDogEat, 2,
					new IAnimationListener() {
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							touchRightDog = 1;
							rightDogWriting();
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {
						}
					});
		}
	}

	// Processing Post
	private void leftPostChangeResource() {
		aSLeftPost.setCurrentTileIndex(1);
	}

	private void rightPostChangeResource() {
		aSRightPost.setCurrentTileIndex(1);
	}

	// Processing Dog FrontEnd
	private void leftDogFrontEndChangeResource() {
		isLeftDogEatLetter = true;
		if (!aSLeftDogFrontendFace.isAnimationRunning()) {
			mp3Watashimo.play();
			long pDurationRightPost[] = new long[] { 1200, 100 };
			int pFramsRightPost[] = new int[] { 1, 0 };
			aSLeftDogFrontendFace.animate(pDurationRightPost, pFramsRightPost,
					0, new IAnimationListener() {
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							isLeftDogEatLetter = false;
							aSLeftDogFrontendFace.setCurrentTileIndex(0);
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {
						}
					});
		}
	}

	private void rightDogFrontEndChangeResource() {
		isRightDogEatLetter = true;
		if (!aSRightDogFrontendFace.isAnimationRunning()) {
			mp3Bokunimo.play();
			long pDurationRightPost[] = new long[] { 1200, 100 };
			int pFramsRightPost[] = new int[] { 1, 0 };
			aSRightDogFrontendFace.animate(pDurationRightPost, pFramsRightPost,
					0, new IAnimationListener() {
						@Override
						public void onAnimationFinished(AnimatedSprite arg0) {
							isRightDogEatLetter = false;
							aSRightDogFrontendFace.setCurrentTileIndex(0);
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite arg0, int arg1, int arg2) {
						}

						@Override
						public void onAnimationStarted(AnimatedSprite arg0,
								int arg1) {
						}
					});
		}
	}

	// Change Resource Go on foot Uni
	private void leftUniNotLeterChangeResouce() {
		aSUni.stopAnimation();
		isTouchUni = true;
		long pDurationRightPost[] = new long[] { 350, 350 };
		int pFramsRightPost[] = new int[] { 6, 7 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void leftUniLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 350, 350 };
		int pFramsRightPost[] = new int[] { 9, 11 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void rightUniNotLeterChangeResouce() {
		aSUni.stopAnimation();
		isTouchUni = true;
		long pDurationRightPost[] = new long[] { 350, 350 };
		int pFramsRightPost[] = new int[] { 0, 1 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void rightUniLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 350, 350 };
		int pFramsRightPost[] = new int[] { 3, 5 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	// ChangeResource Run Uni
	private void rightRunUniNotLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 250, 250 };
		int pFramsRightPost[] = new int[] { 0, 1 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void rightRunUniLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 250, 250 };
		int pFramsRightPost[] = new int[] { 3, 5 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void leftRunUniNotLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 250, 250 };
		int pFramsRightPost[] = new int[] { 6, 7 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	private void leftRunUniLeterChangeResouce() {
		aSUni.stopAnimation();
		long pDurationRightPost[] = new long[] { 250, 250 };
		int pFramsRightPost[] = new int[] { 9, 11 };
		aSUni.animate(pDurationRightPost, pFramsRightPost, -1);
	}

	// Mac dinh di tu trai sang phai khong co thu
	private void UniLeftToRightLineNotLeterModifier() {
		touchLeftPostRunUni = true;
		touchRightPostRunUni = true;

		final Path path = new Path(2).to(aSUni.getX(), 143).to(450, 143);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						UniRightToLeftLineNotLeterModifier();

					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						leftUniNotLeterChangeResouce();
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance()));
	}

	// di tu phai sang trai khong co thu
	private void UniRightToLeftLineNotLeterModifier() {
		touchLeftPostRunUni = true;
		touchRightPostRunUni = true;
		final Path path = new Path(2).to(aSUni.getX(), 143).to(321, 143);
		aSUni.registerEntityModifier((new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						UniLeftToRightLineNotLeterModifier();
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						rightUniNotLeterChangeResouce();
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
	}

	// Di tu trai sang phai co cam thu
	private void UniLeftToRightLineLeterModifier() {
		touchLeftPostRunUni = true;
		touchRightPostRunUni = true;
		final Path path = new Path(2).to(aSUni.getX(), 143).to(450, 143);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						if (isTouchUni && isOtegami2Play
								&& indexBoxMailHouse == 0) {
							isTouchUni = false;
							isOtegami2Play = false;
							mp3Otegami2.play();
							rightDogWriting();
							aSUni.stopAnimation();
							aSUni.stopAnimation();
							aSUni.setCurrentTileIndex(7);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											UniRightToLeftLineNotLeterModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}
									}));
						} else if (isTouchBoxMailHouse && isOtegami2Play
								&& indexBoxMailHouse == 2) {
							isOtegami2Play = false;
							isTouchBoxMailHouse = false;
							indexBoxMailHouse = 0;
							mp3Otegami2.play();
							rightDogWriting();
							aSUni.stopAnimation();
							aSUni.stopAnimation();
							aSUni.setCurrentTileIndex(7);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											UniRightToLeftLineNotLeterModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											// TODO Auto-generated method stub

										}
									}));
						} else {
							UniRightToLeftLineLetterModifier();
						}
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						leftUniLeterChangeResouce();
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance()));
	}

	// Di tu phai sang trai co cam thu
	private void UniRightToLeftLineLetterModifier() {
		touchLeftPostRunUni = true;
		touchRightPostRunUni = true;
		final Path path = new Path(2).to(aSUni.getX(), 143).to(321, 143);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
						if (isTouchUni && isOtegami2Play
								&& indexBoxMailHouse == 0) {
							isTouchUni = false;
							isOtegami2Play = false;
							mp3Otegami2.play();
							leftDogWriting();
							aSUni.stopAnimation();
							aSUni.setCurrentTileIndex(0);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											UniLeftToRightLineNotLeterModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											// TODO Auto-generated method stub

										}
									}));

						} else if (isTouchBoxMailHouse && isOtegami2Play
								&& indexBoxMailHouse == 1) {
							isOtegami2Play = false;
							isTouchBoxMailHouse = false;
							indexBoxMailHouse = 0;
							mp3Otegami2.play();
							leftDogWriting();
							aSUni.stopAnimation();
							aSUni.setCurrentTileIndex(0);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											UniLeftToRightLineNotLeterModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}
									}));
						} else {
							UniLeftToRightLineLeterModifier();
						}
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
						rightUniLeterChangeResouce();
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance()));
	}

	// ++di chuyen khi cham vao thung thu ben trai
	// run from Right to left down
	private void UniRightToLeftDownModifier() {
		aSUni.clearEntityModifiers();
		touchRightPostRunUni = false;
		if (aSUni.getCurrentTileIndex() == 3
				|| aSUni.getCurrentTileIndex() == 5
				|| aSUni.getCurrentTileIndex() == 9
				|| aSUni.getCurrentTileIndex() == 11) {
			rightRunUniLeterChangeResouce();
		} else {
			rightRunUniNotLeterChangeResouce();
		}
		float CoordirateX[] = new float[] { aSUni.getX(), 280, 260, 240, 220,
				200, 180, 170, 160, 150, 145, 130, 120, 70, 50, 20,
		// 60, 80, 130, 140, 155, 160, 170, 180, 190, 200, 210, 240, 260, 280,
		// aSUni.getX()
		};
		float CoordirateY[] = new float[] { 143, 143, 150, 160, 165, 175, 180,
				185, 190, 195, 205, 215, 220, 310, 360, 440,
		// 360, 310, 220, 205, 200, 195, 190, 185, 180, 175, 170, 160, 150, 143,
		// 143
		};
		final Path path = new Path(CoordirateX, CoordirateY);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 14:
							aSUni.stopAnimation();
							aSUni.setVisible(false);
							sUniFrontLeft.setVisible(true);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											mp3Paka.play();
											aSUni.setVisible(true);
											aSLeftPost.setCurrentTileIndex(0);
											sUniFrontLeft.setVisible(false);
											UniLeftToRightUpModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}
									}));
							break;
						default:
							break;
						}

					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub

					}
				}, EaseLinear.getInstance()));
	}

	// run from Right to left down
	private void UniLeftToRightUpModifier() {
		leftRunUniLeterChangeResouce();
		touchRightPostRunUni = false;
		float CoordirateX[] = new float[] { aSUni.getX(), 60, 80, 130, 140,
				155, 160, 170, 180, 190, 200, 210, 240, 260, 280, 321 };
		float CoordirateY[] = new float[] { aSUni.getY(), 360, 310, 220, 205,
				200, 195, 190, 185, 180, 175, 170, 160, 150, 143, 143 };
		final Path path = new Path(CoordirateX, CoordirateY);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 14:
							UniLeftToRightLineLeterModifier();
							break;
						default:
							break;
						}
					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance()));
	}

	// ######################################################
	// ++di chuyen khi cham vao thung thu ben phai
	// run from Left to right down and from right to left up
	private void UniLeftToRightDownModifier() {
		aSUni.clearEntityModifiers();
		touchLeftPostRunUni = false;
		if (aSUni.getCurrentTileIndex() == 3
				|| aSUni.getCurrentTileIndex() == 5
				|| aSUni.getCurrentTileIndex() == 9
				|| aSUni.getCurrentTileIndex() == 11) {
			leftRunUniLeterChangeResouce();
		} else {
			leftRunUniNotLeterChangeResouce();
		}
		float CoordirateX1[] = new float[] { aSUni.getX(), 440, 460, 480, 510,
				520, 530, 540, 550, 560, 565, 570, 590, 640, 660, 680, };
		float CoordirateY1[] = new float[] { 143, 143, 150, 160, 170, 175, 180,
				185, 190, 195, 200, 205, 220, 310, 360, 440, };
		final Path path1 = new Path(CoordirateX1, CoordirateY1);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path1, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 14:
							aSUni.stopAnimation();
							aSUni.setVisible(false);
							sUniFrontRight.setVisible(true);
							aSUni.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											aSUni.setVisible(true);
											mp3Paka.play();
											aSRightPost.setCurrentTileIndex(0);
											sUniFrontRight.setVisible(false);
											UniRightToLeftUpModifier();
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
										}
									}));
							break;
						default:
							break;
						}

					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
						// TODO Auto-generated method stub

					}
				}, EaseLinear.getInstance()));
	}

	private void UniRightToLeftUpModifier() {
		rightRunUniLeterChangeResouce();
		touchLeftPostRunUni = false;
		float CoordirateX1[] = new float[] { aSUni.getX(), 660, 640, 590, 570,
				565, 560, 550, 540, 530, 520, 510, 480, 460, 440 };
		float CoordirateY1[] = new float[] { aSUni.getY(), 360, 310, 220, 205,
				200, 195, 190, 185, 180, 175, 170, 160, 150, 143 };
		final Path path1 = new Path(CoordirateX1, CoordirateY1);
		aSUni.registerEntityModifier(new PathModifier(1.5f, path1, null,
				new IPathModifierListener() {
					@Override
					public void onPathFinished(PathModifier arg0, IEntity arg1) {

					}

					@Override
					public void onPathStarted(PathModifier arg0, IEntity arg1) {
					}

					@Override
					public void onPathWaypointFinished(PathModifier arg0,
							IEntity arg1, int arg2) {
						switch (arg2) {
						case 13:
							UniRightToLeftLineLetterModifier();
							break;
						default:
							break;
						}

					}

					@Override
					public void onPathWaypointStarted(PathModifier arg0,
							IEntity arg1, int arg2) {
					}
				}, EaseLinear.getInstance()));
	}

	@Override
	public void combineGimmic3WithAction() {
		if (!isTouchGimic) {
			isTouchGimic = true;
			setTouchGimmic3(false);
			foliageTrees();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		int x = pX;
		int y = pY;
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

			// Touch Tree
			if (sTree3.contains(pX, pY) || sTree4.contains(pX, pY)
					|| sTree5.contains(pX, pY)) {
				if (isTouchTrees) {
					foliageTrees();
				}
			}
			// Touch LeftDog
			if (aSLeftDog.contains(pX, pY)) {
				if (touchLeftDog == 0) {
					if (aSLeftDog.getCurrentTileIndex() == 2
							|| aSLeftDog.getCurrentTileIndex() == 3) {
						mp3Kaki.play();
					}
					leftDogWriting();
				}
				if (touchLeftDog == 1 || tempTouchLeftDog == 0) {
					if (aSLeftDog.getCurrentTileIndex() == 0) {
						mp3Mogu.play();
					}
					leftDogEat();
				}
				if (touchLeftDog == 1) {
					touchLeftDog = 1;
				}
				touchLeftDog++;
				tempTouchLeftDog = 1;
			}
			// Touch RightDog
			if (aSRightDog.contains(pX, pY)) {
				if (touchRightDog == 0) {
					if (aSRightDog.getCurrentTileIndex() == 2
							|| aSRightDog.getCurrentTileIndex() == 3) {
						mp3Kaki.play();
					}
					rightDogWriting();
				}
				if (touchRightDog == 1 || tempTouchRightDog == 0) {
					if (aSRightDog.getCurrentTileIndex() == 0) {
						mp3Mogu.play();
					}
					rightDogEat();
				}
				if (touchRightDog == 1) {
					touchRightDog = 1;
				}
				touchRightDog++;
				tempTouchRightDog = 1;
			}
			// Touch LeftPost
			if (checkContains(sLeftPost, 6, 6, 176, 290, pX, pY)) {
				if (touchRightPostRunUni && !isMoveLetter) {
					mp3Paka.play();
					touchLeftPostRunUni = false;
					leftPostChangeResource();
					UniRightToLeftDownModifier();
					isTouchUni = false;
				}
			}
			// Touch RightPost
			if (checkContains(sRightPost, 65, 57, 180, 380, pX, pY)) {
				if (touchLeftPostRunUni && !isMoveLetter) {
					mp3Paka.play();
					touchRightPostRunUni = false;
					rightPostChangeResource();
					UniLeftToRightDownModifier();
					isTouchUni = false;
				}
			}
			// Touch DogLeftFrontEnd
			if (checkContains(sBackground, 230, 345, 360, 600, pX, pY)
					&& !isLeftDogEatLetter) {
				leftDogFrontEndChangeResource();
			}
			// Touch DogRightFrontEnd
			if (checkContains(sBackground, 570, 323, 700, 600, pX, pY)
					&& !isRightDogEatLetter) {
				rightDogFrontEndChangeResource();
			}
			// Touch Uni
			if (checkContains(aSUni, 72, 9, 160, 195, pX, pY)) {
				if ((aSUni.getCurrentTileIndex() == 0
						|| aSUni.getCurrentTileIndex() == 1
						|| aSUni.getCurrentTileIndex() == 6 || aSUni
						.getCurrentTileIndex() == 7)
						&& (touchRightPostRunUni || touchLeftPostRunUni)) {
					isTouchUni = true;
					mp3Inug.play();
				}
				if ((aSUni.getCurrentTileIndex() == 3
						|| aSUni.getCurrentTileIndex() == 5
						|| aSUni.getCurrentTileIndex() == 9 || aSUni
						.getCurrentTileIndex() == 11)
						&& (touchRightPostRunUni || touchLeftPostRunUni)) {
					isTouchUni = true;
					isOtegami2Play = true;
				}
			}
			// Box Mail right
			if (sBoxMailHouseLeft.contains(pX, pY)) {
				if (!isTouchBoxMailHouse) {
					isOtegami2Play = true;
					indexBoxMailHouse = 1;
					isTouchBoxMailHouse = true;
				}
			}
			// Box Mail left

			if (sBoxMailHouseRight.contains(pX, pY)) {
				if (!isTouchBoxMailHouse) {
					isOtegami2Play = true;
					indexBoxMailHouse = 2;
					isTouchBoxMailHouse = true;

				}
			}
			// Touch Letter
			if (aSLetter.contains(pX, pY)) {
				if (!isTouchLetter && !isMoveLetter) {
					mp3Syu.play();
					sLetterAreaTouched.setPosition(pX, pY);
					sLetterAreaTouched.setVisible(true);
					aSLetter.setCurrentTileIndex(1);
					isTouchLetter = true;
				}
			}

		}

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if (isTouchLetter && aSLetter.getCurrentTileIndex() == 1
					&& !isMoveLetter) {
				sLetterAreaTouched.setPosition(pX, pY);
			}
		}
		if (pSceneTouchEvent.isActionUp()) {
			if (!isTouchAction) {
				if (isTouchLetter) {
					if ((x >= 89 && x <= 178) && (y >= 330 && y <= 560)) {
						if (aSLetter.getCurrentTileIndex() == 1
								&& touchLeftPostRunUni && !isMoveLetter) {
							// Xu ly thung thu trai
							isMoveLetter = true;
							mp3Kakon2.play();
							aSLeftPost.setCurrentTileIndex(2);
							aSLetter.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											isMoveLetter = false;
											aSLetter.setCurrentTileIndex(0);
											sLetterAreaTouched
													.setVisible(false);
											aSLeftPost.setCurrentTileIndex(0);
											isTouchLetter = false;
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											// TODO Auto-generated method stub

										}
									}));
						} else {
							sLetterAreaTouched.setVisible(false);
							aSLetter.setCurrentTileIndex(0);
							isTouchLetter = false;
						}
					} else if ((x >= 789 && x <= 896) && (y >= 310 && y <= 632)) {
						if (aSLetter.getCurrentTileIndex() == 1
								&& touchRightPostRunUni && !isMoveLetter) {
							// Xu ly thung thu phai
							isMoveLetter = true;
							mp3Kakon2.play();
							aSRightPost.setCurrentTileIndex(2);
							aSLetter.registerEntityModifier(new DelayModifier(
									1.0f, new IEntityModifierListener() {
										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											isMoveLetter = false;
											aSLetter.setCurrentTileIndex(0);
											sLetterAreaTouched
													.setVisible(false);
											aSRightPost.setCurrentTileIndex(0);
											isTouchLetter = false;
										}

										@Override
										public void onModifierStarted(
												IModifier<IEntity> arg0,
												IEntity arg1) {
											// TODO Auto-generated method stub

										}
									}));
						} else {
							sLetterAreaTouched.setVisible(false);
							aSLetter.setCurrentTileIndex(0);
							isTouchLetter = false;
						}
					} else if ((x >= 230 && x <= 360) && (y >= 345 && y <= 460)) {
						// Xu ly con cho phia ngoai ben trai
						isTouchLetter = false;
						if (!isLeftDogEatLetter
								&& !isRightDogEatLetter
								&& aSLeftDogFrontendFace.getCurrentTileIndex() == 0) {
							mp3Taberu2.play();
							isLeftDogEatLetter = true;
							isRightDogEatLetter = true;
							aSLetter.setCurrentTileIndex(0);
							sLetterAreaTouched.setVisible(false);
							aSLeftDogFrontendBody.setCurrentTileIndex(4);
							sLeftLetterDogFrontend.setVisible(true);
							aSRightDogFrontendFace.setCurrentTileIndex(2);
							float CoordirateX1[] = new float[] {
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX(),
									aSLeftDogFrontendFace.getX() };
							float CoordirateY1[] = new float[] {
									aSLeftDogFrontendFace.getY(),
									aSLeftDogFrontendFace.getY() - 10,
									aSLeftDogFrontendFace.getY() + 10,
									aSLeftDogFrontendFace.getY() - 10,
									aSLeftDogFrontendFace.getY(),
									aSLeftDogFrontendFace.getY() + 10,
									aSLeftDogFrontendFace.getY() };
							final Path path1 = new Path(CoordirateX1,
									CoordirateY1);
							aSLeftDogFrontendFace
									.registerEntityModifier(new PathModifier(
											1.5f, path1, null,
											new IPathModifierListener() {
												@Override
												public void onPathFinished(
														PathModifier arg0,
														IEntity arg1) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onPathStarted(
														PathModifier arg0,
														IEntity arg1) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onPathWaypointFinished(
														PathModifier arg0,
														IEntity arg1, int arg2) {
													switch (arg2) {
													case 3:
														aSLeftDogFrontendFace
																.setCurrentTileIndex(0);
														aSRightDogFrontendFace
																.setCurrentTileIndex(0);
														aSLeftDogFrontendBody
																.setCurrentTileIndex(3);
														sLeftLetterDogFrontend
																.setVisible(false);
														break;
													case 5:
														isLeftDogEatLetter = false;
														isRightDogEatLetter = false;
														break;
													default:
														break;
													}

												}

												@Override
												public void onPathWaypointStarted(
														PathModifier arg0,
														IEntity arg1, int arg2) {
													// TODO Auto-generated
													// method stub

												}
											}, EaseLinear.getInstance()));
						} else {
							sLetterAreaTouched.setVisible(false);
							aSLetter.setCurrentTileIndex(0);
							isTouchLetter = false;
						}
					} else if ((x >= 570 && x <= 700) && (y >= 323 && y <= 460)) {
						// Xu ly con cho phia ngoai ben phai
						isTouchLetter = false;
						if (!isRightDogEatLetter
								&& !isLeftDogEatLetter
								&& aSRightDogFrontendFace.getCurrentTileIndex() == 0) {
							mp3Taberu2.play();
							isLeftDogEatLetter = true;
							isRightDogEatLetter = true;
							aSLetter.setCurrentTileIndex(0);
							sLetterAreaTouched.setVisible(false);
							aSRightDogFrontendBody.setCurrentTileIndex(4);
							sRightLetterDogFrontend.setVisible(true);
							aSLeftDogFrontendFace.setCurrentTileIndex(2);
							float CoordirateX1[] = new float[] {
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX(),
									aSRightDogFrontendFace.getX() };
							float CoordirateY1[] = new float[] {
									aSRightDogFrontendFace.getY(),
									aSRightDogFrontendFace.getY() - 10,
									aSRightDogFrontendFace.getY() + 10,
									aSRightDogFrontendFace.getY() - 10,
									aSRightDogFrontendFace.getY(),
									aSRightDogFrontendFace.getY() + 10,
									aSRightDogFrontendFace.getY() };
							final Path path1 = new Path(CoordirateX1,
									CoordirateY1);
							aSRightDogFrontendFace
									.registerEntityModifier(new PathModifier(
											1.5f, path1, null,
											new IPathModifierListener() {
												@Override
												public void onPathFinished(
														PathModifier arg0,
														IEntity arg1) {
												}

												@Override
												public void onPathStarted(
														PathModifier arg0,
														IEntity arg1) {
												}

												@Override
												public void onPathWaypointFinished(
														PathModifier arg0,
														IEntity arg1, int arg2) {
													switch (arg2) {
													case 3:
														aSRightDogFrontendFace
																.setCurrentTileIndex(0);
														aSLeftDogFrontendFace
																.setCurrentTileIndex(0);
														aSRightDogFrontendBody
																.setCurrentTileIndex(3);
														sRightLetterDogFrontend
																.setVisible(false);
														break;
													case 5:
														isRightDogEatLetter = false;
														isLeftDogEatLetter = false;
														break;
													default:
														break;
													}

												}

												@Override
												public void onPathWaypointStarted(
														PathModifier arg0,
														IEntity arg1, int arg2) {

												}
											}, EaseLinear.getInstance()));
						} else {
							sLetterAreaTouched.setVisible(false);
							aSLetter.setCurrentTileIndex(0);
							isTouchLetter = false;
						}
					} else {
						sLetterAreaTouched.setVisible(false);
						aSLetter.setCurrentTileIndex(0);
						isTouchLetter = false;
					}
				}
				isTouchAction = false;
			}
		}
		return true;
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		Log.d("END", "OK");

	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

}