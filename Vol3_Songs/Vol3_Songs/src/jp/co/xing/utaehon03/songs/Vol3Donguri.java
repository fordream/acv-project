package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3DonguriResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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

public class Vol3Donguri extends BaseGameActivity implements
		IOnSceneTouchListener, IModifierListener<IEntity>, IAnimationListener {

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mTexturePack;
	private TexturePackTextureRegionLibrary mTexturePacklib;

	private ITextureRegion ttrBackground, ttrBackground2, ttrBrid, ttrAngler,
			ttrHatAngler, ttrFish, ttrWolf, ttrKareha, ttrKinoko1, ttrKinoko2,
			ttrRipple1;
	private TiledTextureRegion ttrMinomushi, ttrSmoke, ttrTree, ttrDonguri1,
			ttrDonguri2, ttrDonguri3, ttrDonguri4;

	private Sprite sBackground2, sBrid, sAngler, sHatAngler, sFish, sWolf,
			sKareha, sKinoko1, sKinoko2, sRipple1, sRipple2, sRipple3,
			sRipple4;
	private AnimatedSprite sMinomushi, sSmoke, sTree, sDonguri1, sDonguri2,
			sDonguri3, sDonguri4;
	private SequenceEntityModifier sqMinomushi, sqHatAngler, sqFishModifier,
			sqwolfModifier, sqDonguri1Modifier, sqDonguri2Modifier,
			sqDonguri3Modifier, sqDonguri4Modifier, sqRipple1Modifier,
			sqRipple2Modifier, sqRipple3Modifier, sqRipple4Modifier;

	@SuppressWarnings("unused")
	private LoopEntityModifier birdLoopEntityModifier;
	private boolean isTouchMinimushi, isTouchTree, isTouchHatAngler,
			isTouchFish, isTouchGimmic, isTouchWolf, isTouchDonguri,
			isTouchRipple1, isTouchRipple2;

	private Sound mp3Surface, mp3Minomushi, mp3Tree, mp3HatAngler, mp3Donguri,
			mp3Wolf, mp3Fish;

	private TimerHandler handlerTree, handlerDonguri;

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();

		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();

		sMinomushi = new AnimatedSprite(172, 30, ttrMinomushi,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMinomushi);

		sWolf = new Sprite(752, 288, ttrWolf,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sWolf);

		sTree = new AnimatedSprite(308, -6, ttrTree,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sTree);

		sAngler = new Sprite(-4, 216, ttrAngler,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sAngler);

		sFish = new Sprite(2, 462, ttrFish, this.getVertexBufferObjectManager());
		mScene.attachChild(sFish);

		sKareha = new Sprite(496, 404, ttrKareha,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKareha);

		sKinoko1 = new Sprite(612, 528, ttrKinoko1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKinoko1);

		sKinoko2 = new Sprite(634, 520, ttrKinoko2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKinoko2);

		sBackground2 = new Sprite(0, 0, ttrBackground2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);

		sDonguri4 = new AnimatedSprite(644, 120, ttrDonguri4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sDonguri4);

		sDonguri3 = new AnimatedSprite(540, 136, ttrDonguri3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sDonguri3);

		sDonguri2 = new AnimatedSprite(400, 194, ttrDonguri2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sDonguri2);

		sDonguri1 = new AnimatedSprite(304, 272, ttrDonguri1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sDonguri1);

		sSmoke = new AnimatedSprite(310, 80, ttrSmoke,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSmoke);
		sSmoke.setVisible(false);

		sHatAngler = new Sprite(74, 220, ttrHatAngler,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sHatAngler);

		sBrid = new Sprite(28, 6, ttrBrid, this.getVertexBufferObjectManager());
		mScene.attachChild(sBrid);

		sRipple1 = new Sprite(150, 400, ttrRipple1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRipple1);
		sRipple1.setVisible(false);
		sRipple1.setScale(0.0f);

		sRipple2 = new Sprite(150, 400, ttrRipple1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRipple2);
		sRipple2.setVisible(false);

		sRipple3 = new Sprite(290, 550, ttrRipple1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRipple3);
		sRipple3.setVisible(false);
		sRipple3.setScale(0.0f);

		sRipple4 = new Sprite(290, 550, ttrRipple1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sRipple4);
		sRipple4.setVisible(false);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(
				mScene,
				Vol3DonguriResource.SOUND_GIMMIC,
				new int[] {
						Vol3DonguriResource.allitem.A3_13_IPHONE3G_E00073_PAF_ID,
						Vol3DonguriResource.allitem.A3_14_IPHONE3G_E00074_TAMBOURINE_ID,
						Vol3DonguriResource.allitem.A3_03_IPHONE3G_QUESTION_ID },
				mTexturePacklib);
	}

	@Override
	public synchronized void onResumeGame() {
		birdModifierFunction();
		isTouchMinimushi = true;
		isTouchTree = true;
		isTouchHatAngler = true;
		isTouchFish = true;
		isTouchGimmic = true;
		isTouchWolf = true;
		isTouchDonguri = true;
		isTouchRipple1 = true;
		isTouchRipple2 = true;

		if (sRipple1 != null) {
			sRipple1.setScale(0.0f);
		}
		if (sRipple2 != null) {
			sRipple2.setScale(1.0f);
		}
		if (sRipple3 != null) {
			sRipple3.setScale(0.0f);
		}
		if (sRipple4 != null) {
			sRipple4.setScale(1.0f);
		}
		super.onResumeGame();
	}

	@Override
	protected void loadKaraokeResources() {

		mTexturePack = mTexturePackLoaderFromSource.load("allitem.xml");
		mTexturePack.loadTexture();
		mTexturePacklib = mTexturePack.getTexturePackTextureRegionLibrary();

		ttrBackground = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_01_1_IPHONE3G_HAIKEI_DONGURI_ID);
		ttrBackground2 = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_01_2_IPHONE3G_HAIKEI_DONGURI_ID);
		ttrBrid = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_02_IPHONE3G_BIRD_ID);
		ttrMinomushi = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_05_IPHONE3G_MINOMUSHI_ID);
		ttrSmoke = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_06_1_IPHONE3G_SMOKE_ID,
				Vol3DonguriResource.allitem.A3_06_2_IPHONE3G_SMOKE_ID,
				Vol3DonguriResource.allitem.A3_06_3_IPHONE3G_SMOKE_ID);
		ttrTree = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_07_2_IPHONE3G_TREE_BEFORE_ID,
				Vol3DonguriResource.allitem.A3_07_3_IPHONE3G_TREE_AFTER_ID);
		ttrAngler = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_10_1_IPHONE3G_ANGLER_ID);
		ttrHatAngler = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_10_2_IPHONE3G_ANGLER_HAT_ID);
		ttrFish = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_04_IPHONE3G_FISH_ID);
		ttrWolf = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_08_IPHONE3G_WOLF_ID);
		ttrKareha = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_11_IPHONE3G_KAREHA_ID);
		ttrKinoko1 = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_12_1_IPHONE3G_KINOKO_ID);
		ttrKinoko2 = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_12_2_IPHONE3G_KINOKO_ID);
		ttrDonguri1 = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_03_1_1_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_1_2_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_1_3_IPHONE3G_DONGURI_ID);
		ttrDonguri2 = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_03_2_1_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_2_2_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_2_3_IPHONE3G_DONGURI_ID);
		ttrDonguri3 = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_03_3_1_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_3_2_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_3_3_IPHONE3G_DONGURI_ID);
		ttrDonguri4 = getTiledTextureFromPacker(mTexturePack,
				Vol3DonguriResource.allitem.A3_03_4_1_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_4_2_IPHONE3G_DONGURI_ID,
				Vol3DonguriResource.allitem.A3_03_4_3_IPHONE3G_DONGURI_ID);
		ttrRipple1 = mTexturePacklib
				.get(Vol3DonguriResource.allitem.A3_09_IPHONE3G_RIPPLE_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		mp3Minomushi = loadSoundResourceFromSD(Vol3DonguriResource.E00076_PAN);
		mp3Tree = loadSoundResourceFromSD(Vol3DonguriResource.E00075_NANDAI);
		mp3HatAngler = loadSoundResourceFromSD(Vol3DonguriResource.E00081_FLY);
		mp3Donguri = loadSoundResourceFromSD(Vol3DonguriResource.E00078_DOWN);
		mp3Wolf = loadSoundResourceFromSD(Vol3DonguriResource.E00079_WOLF);
		mp3Fish = loadSoundResourceFromSD(Vol3DonguriResource.E00080_BOMI);
		mp3Surface = loadSoundResourceFromSD(Vol3DonguriResource.E00077_POWA);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("donguri/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("donguri/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	public void onPause() {
		if (isLoadingComplete) {
			if (sMinomushi != null) {
				sMinomushi.clearEntityModifiers();
				sqMinomushi = null;
			}
			if (sSmoke != null) {
				sSmoke.stopAnimation();
				sSmoke.setCurrentTileIndex(0);
				sSmoke.setVisible(false);
			}
			if (sTree != null) {
				sTree.stopAnimation();
				sTree.setCurrentTileIndex(0);
			}
			if (sRipple1 != null) {
				sRipple1.clearEntityModifiers();
				sqRipple1Modifier = null;
				sRipple1.setVisible(false);
			}
			if (sRipple2 != null) {
				sRipple2.clearEntityModifiers();
				sqRipple2Modifier = null;
				sRipple2.setVisible(false);
			}
			if (sRipple3 != null) {
				sRipple3.clearEntityModifiers();
				sqRipple3Modifier = null;
				sRipple3.setVisible(false);
			}
			if (sRipple4 != null) {
				sRipple4.clearEntityModifiers();
				sqRipple4Modifier = null;
				sRipple4.setVisible(false);
			}
			if (sDonguri1 != null) {
				sDonguri1.clearEntityModifiers();
				sDonguri1.stopAnimation();
				sqDonguri1Modifier = null;
				sDonguri1.setCurrentTileIndex(0);
				sDonguri1.setPosition(304, 272);
			}
			if (sDonguri2 != null) {
				sDonguri2.clearEntityModifiers();
				sDonguri2.stopAnimation();
				sqDonguri2Modifier = null;
				sDonguri2.setCurrentTileIndex(0);
				sDonguri2.setPosition(400, 194);
			}
			if (sDonguri3 != null) {
				sDonguri3.clearEntityModifiers();
				sDonguri3.stopAnimation();
				sqDonguri3Modifier = null;
				sDonguri3.setCurrentTileIndex(0);
				sDonguri3.setPosition(540, 136);
			}
			if (sDonguri4 != null) {

				sDonguri4.clearEntityModifiers();
				sDonguri4.stopAnimation();
				sqDonguri4Modifier = null;
				sDonguri4.setCurrentTileIndex(0);
				sDonguri4.setPosition(644, 120);
			}
			if (sWolf != null) {
				sWolf.clearEntityModifiers();
				sqwolfModifier = null;
			}
			if (sFish != null) {
				sFish.clearEntityModifiers();
				sqFishModifier = null;
				sFish.setPosition(2, 462);
			}
			if (sHatAngler != null) {
				sHatAngler.clearEntityModifiers();
				sqHatAngler = null;
				sHatAngler.setPosition(74, 220);
			}
			mScene.unregisterUpdateHandler(handlerDonguri);
			mScene.unregisterUpdateHandler(handlerTree);
			mScene.clearUpdateHandlers();
		}
		super.onPause();
	}

	private void ripple1ModifierFunction() {
		sRipple1.setVisible(true);
		sRipple1.registerEntityModifier((IEntityModifier) (sqRipple1Modifier = new SequenceEntityModifier(
				new ScaleModifier(1.0f, 0.1f, 0.6f))));
		if (sqRipple1Modifier != null) {
			sqRipple1Modifier.addModifierListener(this);
		}
	}

	private void ripple2ModifierFunction() {
		sRipple2.setVisible(true);
		sRipple2.registerEntityModifier((IEntityModifier) (sqRipple2Modifier = new SequenceEntityModifier(

		new ScaleModifier(0.7f, 1f, 1.3f))));
		if (sqRipple2Modifier != null) {
			sqRipple2Modifier.addModifierListener(this);
		}
	}

	private void ripple3ModifierFunction() {
		sRipple3.setVisible(true);
		sRipple3.registerEntityModifier((IEntityModifier) (sqRipple3Modifier = new SequenceEntityModifier(
				new ScaleModifier(1.0f, 0f, 0.6f))));
		if (sqRipple3Modifier != null) {
			sqRipple3Modifier.addModifierListener(this);
		}
	}

	private void ripple4ModifierFunction() {
		sRipple4.setVisible(true);
		sRipple4.registerEntityModifier((IEntityModifier) (sqRipple4Modifier = new SequenceEntityModifier(

		new ScaleModifier(0.7f, 1f, 1.3f))));
		if (sqRipple4Modifier != null) {
			sqRipple4Modifier.addModifierListener(this);
		}
	}

	private void birdModifierFunction() {
		sBrid.registerEntityModifier((IEntityModifier) (birdLoopEntityModifier = new LoopEntityModifier(
				new SequenceEntityModifier(new MoveYModifier(1.6f,
						sBrid.getY(), sBrid.getY() - 25), new MoveYModifier(
						1.6f, sBrid.getY() - 25, sBrid.getY())))));
		return;
	}

	private void MinomushiModifier() {
		isTouchMinimushi = false;

		sMinomushi.setRotationCenter(15f, 3f);
		sMinomushi
				.registerEntityModifier(sqMinomushi = new SequenceEntityModifier(
						(new RotationModifier(0.7f, 0f, 10f)),
						(new RotationModifier(0.7f, 10f, -10f)),
						(new RotationModifier(0.7f, -10f, 0))));
		if (sqMinomushi != null) {
			sqMinomushi.addModifierListener(this);
		}
	}

	private void sqHatAnglerModifier() {
		isTouchHatAngler = false;
		sHatAngler
				.registerEntityModifier(sqHatAngler = new SequenceEntityModifier(
						new ParallelEntityModifier((new MoveXModifier(0.6f,
								sHatAngler.getX(), sHatAngler.getX() - 150)),
								(new MoveYModifier(0.6f, sHatAngler.getY(),
										sHatAngler.getY() - 100)))));
		if (sqHatAngler != null) {
			sqHatAngler.addModifierListener(this);
		}
	}

	private void sqFishModifier() {
		isTouchFish = false;
		sFish.registerEntityModifier(sqFishModifier = new SequenceEntityModifier(

		(new MoveXModifier(0.4f, sFish.getX(), sFish.getX() + 60)),
				(new MoveXModifier(0.4f, sFish.getX() + 60, sFish.getX()))));
		if (sqFishModifier != null) {
			sqFishModifier.addModifierListener(this);
		}
	}

	private void smokeChangeResource() {
		sSmoke.setVisible(true);
		sSmoke.animate(680, false, this);
	}

	private void treeChangeResource() {
		int Pfame[] = { 1 };
		long durationFrame[] = { 1300 };
		sTree.animate(durationFrame, Pfame, 0, this);
	}

	private void donguriChangeResource1() {
		int Pfame[] = { 2, 1, 2, 0 };
		long durationFrame[] = { 400, 100, 600, 150 };
		sDonguri1.animate(durationFrame, Pfame, 0, this);
	}

	private void donguri1ModifierFunction() {
		sDonguri1
				.registerEntityModifier((IEntityModifier) (sqDonguri1Modifier = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveXModifier(0.4f,
								sDonguri1.getX(), sDonguri1.getX() - 60),
								new MoveYModifier(0.4f, sDonguri1.getY(),
										sDonguri1.getY() + 40)),
						new RotationModifier(0.10f, 0f, -9f),
						new ParallelEntityModifier(new MoveXModifier(0.4f,
								sDonguri1.getX() - 60, sDonguri1.getX()),
								new MoveYModifier(0.4f, sDonguri1.getY() + 40,
										sDonguri1.getY())),
						new RotationModifier(0.2f, -1f, 0f)

				)));
		if (sqDonguri1Modifier != null) {
			sqDonguri1Modifier.addModifierListener(this);
		}
	}

	private void donguriChangeResource2() {
		int Pfame[] = { 2, 1, 2, 0 };
		long durationFrame[] = { 400, 100, 600, 150 };
		sDonguri2.animate(durationFrame, Pfame, 0, this);
	}

	private void donguri2ModifierFunction() {
		sDonguri2
				.registerEntityModifier((IEntityModifier) (sqDonguri2Modifier = new SequenceEntityModifier(
						new ParallelEntityModifier(new MoveXModifier(0.4f,
								sDonguri2.getX(), sDonguri2.getX() - 70),
								new MoveYModifier(0.4f, sDonguri2.getY(),
										sDonguri2.getY() - 90)),
						new RotationModifier(0.10f, 0f, -9f),
						new ParallelEntityModifier(new MoveXModifier(0.4f,
								sDonguri2.getX() - 70, sDonguri2.getX()),
								new MoveYModifier(0.4f, sDonguri2.getY() - 90,
										sDonguri2.getY())),
						new RotationModifier(0.2f, -1f, 0f)

				)));
		if (sqDonguri2Modifier != null) {
			sqDonguri2Modifier.addModifierListener(this);
		}
	}

	private void donguriChangeResource3() {
		int Pfame[] = { 2, 1, 2, 0 };
		long durationFrame[] = { 400, 100, 600, 150 };
		sDonguri3.animate(durationFrame, Pfame, 0, this);
	}

	private void donguri3ModifierFunction() {
		sDonguri3
				.registerEntityModifier((IEntityModifier) (sqDonguri3Modifier = new SequenceEntityModifier(
						new MoveXModifier(0.4f, sDonguri3.getX(), sDonguri3
								.getX() + 60), new RotationModifier(0.10f, 0f,
								-9f), new MoveXModifier(0.4f,
								sDonguri3.getX() + 60, sDonguri3.getX()),
						new RotationModifier(0.2f, -1f, 0f))));
		if (sqDonguri3Modifier != null) {
			sqDonguri3Modifier.addModifierListener(this);
		}
	}

	private void donguriChangeResource4() {
		int Pfame[] = { 2, 1, 2, 0 };
		long durationFrame[] = { 400, 100, 600, 150 };
		sDonguri4.animate(durationFrame, Pfame, 0, this);
	}

	private void donguri4ModifierFunction() {
		sDonguri4
				.registerEntityModifier((IEntityModifier) (sqDonguri4Modifier = new SequenceEntityModifier(
						new MoveYModifier(0.4f, sDonguri4.getY(), sDonguri4
								.getY() - 100), new RotationModifier(0.1f, 0f,
								-9f), new MoveYModifier(0.4f,
								sDonguri4.getY() - 100, sDonguri4.getY()),
						new RotationModifier(0.2f, -1f, 0f))));
		if (sqDonguri4Modifier != null) {
			sqDonguri4Modifier.addModifierListener(this);
		}
	}

	private void sqwolfModifier() {
		isTouchWolf = false;

		sWolf.setRotationCenter(0, 150f);
		sWolf.registerEntityModifier(sqwolfModifier = new SequenceEntityModifier(
				(new RotationModifier(0.3f, 0f, -20f)), (new RotationModifier(
						0.3f, -20f, 0))));
		if (sqwolfModifier != null) {
			sqwolfModifier.addModifierListener(this);
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isTouchGimmic && isTouchTree) {
			mp3Tree.play();
			isTouchGimmic = false;
			isTouchTree = false;
			treeChangeResource();
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sMinomushi, (int) sMinomushi.getWidth() / 3,
					(int) sMinomushi.getHeight() / 2,
					(int) sMinomushi.getWidth(), (int) sMinomushi.getHeight(),
					pX, pY)) {
				if (isTouchMinimushi) {
					mp3Minomushi.play();
					MinomushiModifier();
					smokeChangeResource();
				}
			} else if (checkContains(sTree, (int) sTree.getWidth() / 2,
					(int) sTree.getHeight() / 2,
					(int) sTree.getWidth() * 3 / 4, (int) sTree.getHeight(),
					pX, pY)) {
				if (isTouchTree) {
					mp3Tree.play();
					isTouchTree = false;
					isTouchGimmic = false;
					treeChangeResource();
				}
			} else if (checkContains(sHatAngler, 0, 0,
					2 * (int) sHatAngler.getWidth(),
					2 * (int) sHatAngler.getHeight(), pX, pY)) {
				if (isTouchHatAngler) {
					mp3HatAngler.play();
					isTouchHatAngler = false;
					sqHatAnglerModifier();
				}
			} else if (checkContains(sFish, 0, 0, (int) sFish.getWidth(),
					(int) sFish.getHeight(), pX, pY)) {
				if (isTouchFish) {
					mp3Fish.play();
					isTouchFish = false;
					sqFishModifier();
				}
			} else if (checkContains(sWolf, (int) sWolf.getWidth() / 4,
					(int) sWolf.getHeight() / 4, (int) sWolf.getWidth(),
					(int) sWolf.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchWolf) {
					mp3Wolf.play();
					isTouchWolf = false;
					sqwolfModifier();
				}
			} else if (checkContains(sDonguri1, (int) sDonguri1.getWidth() / 4,
					(int) sDonguri1.getHeight() / 4,
					(int) sDonguri1.getWidth(),
					(int) sDonguri1.getHeight() * 3 / 4, pX, pY)
					|| checkContains(sDonguri2, (int) sDonguri2.getWidth() / 4,
							(int) sDonguri2.getHeight() / 4,
							(int) sDonguri2.getWidth(),
							(int) sDonguri2.getHeight() * 3 / 4, pX, pY)
					|| checkContains(sDonguri3, (int) sDonguri3.getWidth() / 4,
							(int) sDonguri3.getHeight() / 4,
							(int) sDonguri3.getWidth(),
							(int) sDonguri3.getHeight() * 3 / 4, pX, pY)
					|| checkContains(sDonguri4, (int) sDonguri4.getWidth() / 4,
							(int) sDonguri4.getHeight() / 4,
							(int) sDonguri4.getWidth(),
							(int) sDonguri4.getHeight() * 3 / 4, pX, pY)) {
				if (isTouchDonguri) {
					mp3Donguri.play();
					isTouchDonguri = false;
					donguriChangeResource1();
					donguriChangeResource2();
					donguriChangeResource3();
					donguriChangeResource4();

					donguri4ModifierFunction();
					donguri3ModifierFunction();
					donguri2ModifierFunction();
					donguri1ModifierFunction();
				}
			} else if (checkContains(sRipple1, 0, 0, (int) sRipple1.getWidth(),
					(int) sRipple1.getHeight(), pX, pY)) {
				if (isTouchRipple1) {
					mp3Surface.play();
					isTouchRipple1 = false;
					ripple1ModifierFunction();
				}
			} else if (checkContains(sRipple3, 0, 0,
					(int) sRipple3.getWidth() + 100,
					(int) sRipple3.getHeight(), pX, pY)) {
				if (isTouchRipple2) {
					mp3Surface.play();
					isTouchRipple2 = false;
					ripple3ModifierFunction();
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

		if (pModifier.equals(sqMinomushi)) {
			isTouchMinimushi = true;
		} else if (pModifier.equals(sqHatAngler)) {
			isTouchHatAngler = true;
			sHatAngler.setPosition(74, 220);
		} else if (pModifier.equals(sqFishModifier)) {
			isTouchFish = true;
			sFish.setPosition(2, 462);
		} else if (pModifier.equals(sqwolfModifier)) {
			isTouchWolf = true;
		} else if (pModifier.equals(sqRipple1Modifier)) {
			ripple2ModifierFunction();
		} else if (pModifier.equals(sqRipple2Modifier)) {
			isTouchRipple1 = true;
			sRipple1.setVisible(false);
			sRipple2.setVisible(false);
			sRipple1.setScale(0.0f);
			sRipple2.setScale(1.0f);
		} else if (pModifier.equals(sqRipple3Modifier)) {
			ripple4ModifierFunction();
		} else if (pModifier.equals(sqRipple4Modifier)) {
			isTouchRipple2 = true;
			sRipple3.setVisible(false);
			sRipple4.setVisible(false);
			sRipple3.setScale(0.0f);
			sRipple4.setScale(1.0f);
		} else if (pModifier.equals(sqDonguri1Modifier)) {
			sDonguri1.setPosition(304, 272);
		} else if (pModifier.equals(sqDonguri2Modifier)) {
			sDonguri2.setPosition(400, 194);
		} else if (pModifier.equals(sqDonguri3Modifier)) {
			sDonguri3.setPosition(540, 136);
		} else if (pModifier.equals(sqDonguri4Modifier)) {
			sDonguri4.setPosition(644, 120);
		}

	}

	@Override
	public void onAnimationStarted(AnimatedSprite paramAnimatedSprite,
			int paramInt) {
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite paramAnimatedSprite,
			int paramInt1, int paramInt2) {

	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite paramAnimatedSprite,
			int paramInt1, int paramInt2) {

	}

	@Override
	public void onModifierStarted(IModifier<IEntity> paramIModifier,
			IEntity paramT) {

	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite == sSmoke) {
			sSmoke.setVisible(false);
		} else if (pAnimatedSprite == sTree) {
			sTree.setCurrentTileIndex(0);
			mScene.unregisterUpdateHandler(handlerTree);
			mScene.registerUpdateHandler(handlerTree = new TimerHandler(0.6f,
					new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							isTouchTree = true;
							isTouchGimmic = true;
						}
					}));
		} else if (pAnimatedSprite == sDonguri1) {
			sDonguri1.setCurrentTileIndex(0);
			sDonguri2.setCurrentTileIndex(0);
			sDonguri3.setCurrentTileIndex(0);
			sDonguri4.setCurrentTileIndex(0);

			mScene.unregisterUpdateHandler(handlerDonguri);
			mScene.registerUpdateHandler(handlerDonguri = new TimerHandler(
					0.6f, new ITimerCallback() {

						@Override
						public void onTimePassed(TimerHandler paramTimerHandler) {
							isTouchDonguri = true;
						}
					}));
		}
	}

}
