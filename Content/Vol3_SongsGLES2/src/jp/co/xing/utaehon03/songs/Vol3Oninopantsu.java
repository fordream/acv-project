package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3OninopantsuResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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

import android.util.Log;

public class Vol3Oninopantsu extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {

	
	private TexturePack oninopantsuPack;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePackTextureRegionLibrary libOninopantsuPack;
	private ITextureRegion ttrBackground1, ttrBackground2, ttrMizutama,
			ttrCenterpants, ttrSutoraipupantu, ttrAooni_pantu, ttrAkaoni_pantu,
			ttrTori;
	private TiledTextureRegion ttrAkaoni, ttrAooni, ttrKumo, ttrKaminarigumo,
			ttrUsagi, ttrKuma, ttrTora;
	private Sprite sBackground2, sMizutama, sCenterpants, sSutoraipupantu,
			sAooni_pantu, sAkaoni_pantu, sTori;
	private AnimatedSprite sAkaoni, sAooni, sKumo, sKaminarigumo, sUsagi,
			sKuma, sTora;                            

	private SequenceEntityModifier sqAooni_pantuDownModifier,
			sqAooni_pantuUpModifier, sqAkoni_pantudownModifier,
			sqAkoni_pantuUpModifier, sqMizutamaModifier, sqCenterpantsModifier,
			sqSutoraipupantuModifier, sqToriModifier, sqKumoToModifier,
			sqKumoAwayModifier, sqKaminarigumoToModifier,
			sqKaminarigumoAwayModifier, sqToraModifier, sqToraTouchModifier,
			sqUsagiModifier, sqUsagiTouchModifier, sqKumaModifier,
			sqKumaTouchModifier;

	private boolean isGimmic, isTouchAooni, isTouchAkaoni, isTouchAkaoni_pantu,
			isTouchAooni_pantu, isTouchMizutama, isTouchCenterpants,
			isTouchSutoraipupantu, isTouchTori,isTouchKumo,
			isTouchKaminarigumo, isTouchTora, isTouchUsagi, isTouchKuma;

	private Sound mp3Gimmic, mp3Kaminarigumo2, mp3Kumo, mp3Kaminarigumo,
			mp3Aooni_pantu, mp3AooniUp_pantu, mp3Akaoni_pantu,
			mp3Usagi_Tora_Kuma, mp3Aooni, mp3Akaoni, mp3Tori,
			mp3Mizu_Center_Sutor, mp3AkaoniUp_pantu;

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		mScene = new Scene();

		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground1, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();

		sKumo = new AnimatedSprite(338, 12, ttrKumo, this.getVertexBufferObjectManager());
		mScene.attachChild(sKumo);

		sKaminarigumo = new AnimatedSprite(-300, 202, ttrKaminarigumo, this.getVertexBufferObjectManager());
		mScene.attachChild(sKaminarigumo);

		sTora = new AnimatedSprite(-300, 280, ttrTora, this.getVertexBufferObjectManager());
		mScene.attachChild(sTora);

		sUsagi = new AnimatedSprite(-300, 260, ttrUsagi, this.getVertexBufferObjectManager());
		mScene.attachChild(sUsagi);

		sKuma = new AnimatedSprite(-300, 280, ttrKuma, this.getVertexBufferObjectManager());
		mScene.attachChild(sKuma);

		sBackground2 = new Sprite(0, 0, ttrBackground2, this.getVertexBufferObjectManager());
		mScene.attachChild(sBackground2);

		sMizutama = new Sprite(274, 94, ttrMizutama, this.getVertexBufferObjectManager());
		mScene.attachChild(sMizutama);

		sCenterpants = new Sprite(406, 108, ttrCenterpants, this.getVertexBufferObjectManager());
		mScene.attachChild(sCenterpants);

		sSutoraipupantu = new Sprite(554, 102, ttrSutoraipupantu, this.getVertexBufferObjectManager());
		mScene.attachChild(sSutoraipupantu);

		sAkaoni = new AnimatedSprite(628, 188, ttrAkaoni, this.getVertexBufferObjectManager());
		mScene.attachChild(sAkaoni);

		sAooni = new AnimatedSprite(26, 188, ttrAooni, this.getVertexBufferObjectManager());
		mScene.attachChild(sAooni);

		sAooni_pantu = new Sprite(90, 466, ttrAooni_pantu, this.getVertexBufferObjectManager());
		mScene.attachChild(sAooni_pantu);

		sAkaoni_pantu = new Sprite(686, 466, ttrAkaoni_pantu, this.getVertexBufferObjectManager());
		mScene.attachChild(sAkaoni_pantu);

		sTori = new Sprite(658, 16, ttrTori, this.getVertexBufferObjectManager());
		mScene.attachChild(sTori);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3OninopantsuResource.SOUND_GIMMIC,
				Vol3OninopantsuResource.IMAGE_GIMMIC, libOninopantsuPack);
	}
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("oninopantsu/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("oninopantsu/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), getAssets(), "oninopantsu/gfx/");
		super.onCreateResources();
	}
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		oninopantsuPack = mTexturePackLoaderFromSource.load("oninopantsu.xml");
		oninopantsuPack.loadTexture();
		libOninopantsuPack = oninopantsuPack.getTexturePackTextureRegionLibrary();
		
		ttrBackground1 = libOninopantsuPack.get(Vol3OninopantsuResource.A3_16_IPHONE3G_HAIKEI_ONINOPANTU_ID);

		ttrBackground2 = libOninopantsuPack.get(Vol3OninopantsuResource.A3_17_IPHONE3G_YAMA_ID);
		ttrMizutama = libOninopantsuPack.get(Vol3OninopantsuResource.A3_07_IPHONE3G_MIZUTAMA_ID);
		ttrCenterpants = libOninopantsuPack.get(Vol3OninopantsuResource.A3_06_IPHONE3G_CENTERPANTS_ID);
		ttrSutoraipupantu = libOninopantsuPack.get(Vol3OninopantsuResource.A3_05_IPHONE3G_SUTORAIPUPANTU_ID);
		ttrAkaoni = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_10_IPHONE3G_AKAONI_ID,
				Vol3OninopantsuResource.A3_08_1_IPHONE3G_AKAONI_ACTIN_ID);
		ttrAooni = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_12_IPHONE3G_AOONI_ID,
				Vol3OninopantsuResource.A3_08_2_IPHONE3G_AOONI_ACTION_ID);
		
		ttrAooni_pantu = libOninopantsuPack.get(Vol3OninopantsuResource.A3_11_IPHONE3G_E00011_AOONI_PANTU_ID);
		ttrAkaoni_pantu = libOninopantsuPack.get(Vol3OninopantsuResource.A3_09_IPHONE3G_E00011_AKAONI_PANTU_ID);
		ttrTori =  libOninopantsuPack.get(Vol3OninopantsuResource.A3_15_IPHONE3G_TORI_ID);

		ttrKumo = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_14_IPHONE3G_KUMO_ID);
		ttrKaminarigumo = getTiledTextureFromPacker(oninopantsuPack,
				Vol3OninopantsuResource.A3_13_IPHONE3G_KAMINARIGUMO_ID,
				Vol3OninopantsuResource.A3_13_1_IPHONE3G_KAMINARIGUMO1_ID);

		ttrUsagi = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_02_IPHONE3G_USAGI_ID);
		ttrKuma = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_03_IPHONE3G_KUMA_ID);

		ttrTora = getTiledTextureFromPacker(oninopantsuPack, Vol3OninopantsuResource.A3_04_IPHONE3G_TORA_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		mp3Aooni_pantu = loadSoundResourceFromSD(Vol3OninopantsuResource.E00011_PANTU_DOWN);
		mp3AooniUp_pantu = loadSoundResourceFromSD(Vol3OninopantsuResource.E00011_PANTU_UP);
		mp3AkaoniUp_pantu = loadSoundResourceFromSD(Vol3OninopantsuResource.E00011_PANTU_UP);
		mp3Akaoni_pantu = loadSoundResourceFromSD(Vol3OninopantsuResource.E00011_PANTU_DOWN);
		mp3Mizu_Center_Sutor = loadSoundResourceFromSD(Vol3OninopantsuResource.E00050_PANTU_FLY);
		mp3Gimmic = loadSoundResourceFromSD(Vol3OninopantsuResource.E00011_PANTU_DOWN);

		mp3Kumo = loadSoundResourceFromSD(Vol3OninopantsuResource.E00051_CROWD);
		mp3Kaminarigumo = loadSoundResourceFromSD(Vol3OninopantsuResource.E00027_1_KAMINARI);
		mp3Kaminarigumo2 = loadSoundResourceFromSD(Vol3OninopantsuResource.E00027_2_KAMINARI);
		mp3Usagi_Tora_Kuma = loadSoundResourceFromSD(Vol3OninopantsuResource.E00053_PYON);
		mp3Aooni = loadSoundResourceFromSD(Vol3OninopantsuResource.E00058_BLUE_VOICE);

		mp3Akaoni = loadSoundResourceFromSD(Vol3OninopantsuResource.E00057_RED_VOICE);
		mp3Tori = loadSoundResourceFromSD(Vol3OninopantsuResource.E00053_PYON);
	}
	@Override
	public synchronized void onResumeGame() {
		mEngine.clearUpdateHandlers();
		loadDefault();
		super.onResumeGame();
	}
	@Override
	protected void loadKaraokeComplete() {
		Log.e("vol3Oninopantsu", "loadKaraokeComplete:");
		super.loadKaraokeComplete();
	}
	public void loadDefault() {
		Log.e("vol3Oninopantsu", "onResumeGame:");
		isTouchAooni = true;
		isTouchAkaoni = true;
		isTouchAooni_pantu = true;
		isTouchAkaoni_pantu = true;

		isTouchMizutama = true;
		isTouchCenterpants = true;
		isTouchSutoraipupantu = true;
		isTouchKaminarigumo = false;

		isTouchTori = true;
		isTouchKumo = true;

		isTouchTora = true;
		isTouchUsagi = true;
		isTouchKuma = true;
		isGimmic = true;


		if (sKaminarigumo != null) {
			sKaminarigumo.setCurrentTileIndex(1);
		}
		sTora.setPosition(-300, 280);
		sUsagi.setPosition(-300, 260);
		sKuma.setPosition(-300, 280);
		kaminarigumoToModifier();
		toraModifier();
		
		mEngine.registerUpdateHandler(new TimerHandler(5.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				usagiModifier();
				
			}
		}));
		
		mEngine.registerUpdateHandler(new TimerHandler(10.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				kumaModifier();
				
			}
		}));
		
	}

	@Override
	public void onPauseGame() {
		Log.e("vol3Oninopantsu", "onPauseGame:");
		mEngine.clearUpdateHandlers();
			if (sAkaoni_pantu != null) {
				sAkaoni_pantu.clearEntityModifiers();
				sAkaoni_pantu.setPosition(686, 466);
			}
			if (sAooni_pantu != null) {
				sAooni_pantu.clearEntityModifiers();
				sAooni_pantu.setPosition(90, 466);
			}
			if (sMizutama != null) {
				sMizutama.clearEntityModifiers();
				sMizutama.setPosition(274, 94);
			}
			if (sCenterpants != null) {
				sCenterpants.clearEntityModifiers();
				sCenterpants.setPosition(406, 108);
			}
			if (sSutoraipupantu != null) {
				sSutoraipupantu.clearEntityModifiers();
				sSutoraipupantu.setPosition(554, 102);
			}
			if (sKumo != null) {
				sKumo.clearEntityModifiers();
				sqKumoToModifier = null;
				sKumo.setPosition(338, 12);
			}
			if (sKaminarigumo != null) {
				sKaminarigumo.clearEntityModifiers();
				sKaminarigumo.stopAnimation();
				sqKaminarigumoToModifier = null;
				sKaminarigumo.setPosition(-300, 202);
				sKaminarigumo.setCurrentTileIndex(1);

			}
			if (sTora != null) {
				sTora.clearEntityModifiers();
				sqToraModifier = null;
				sqToraTouchModifier = null;
				sTora.setPosition(-300, 280);
			}
			if (sUsagi != null) {
				sUsagi.clearEntityModifiers();
				sqUsagiModifier = null;
				sqUsagiTouchModifier = null;
				sUsagi.setPosition(-300, 260);
			}
			if (sKuma != null) {
				sKuma.clearEntityModifiers();
				sqKumaModifier = null;
				sqKumaTouchModifier = null;
				sKuma.setPosition(-300, 280);
			}
			if (sAkaoni != null) {
				sAkaoni.setCurrentTileIndex(0);
			}
			if (sAooni != null) {
				sAooni.setCurrentTileIndex(0);
			}
		super.onPauseGame();
	}

	private void anoniChangeResource() {
		if (isTouchAooni) {
			mEngine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					sAooni_pantu.setVisible(false);
				}
			}));
			// sAooni_pantu.setVisible(false);
			isTouchAooni = false;
			isTouchAooni_pantu = false;
			long pDuration[] = new long[] { 800 };
			int pFrams[] = new int[] { 1 };
			sAooni.animate(pDuration, pFrams, 0, this);

		}
	}

	private void akaoniChangeResource() {
		if (isTouchAkaoni) {
			mEngine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					sAkaoni_pantu.setVisible(false);
				}
			}));
			isTouchAkaoni = false;
			isTouchAkaoni_pantu = false;
			long pDuration[] = new long[] { 800 };
			int pFrams[] = new int[] { 1 };
			sAkaoni.animate(pDuration, pFrams, 0, this);

		}
	}

	private void toraModifier() {
		float duration = ((this.mEngine.getCamera().getWidth() - sTora.getX()) * 15)
				/ (this.mEngine.getCamera().getWidth() + 300);

		sTora.registerEntityModifier(sqToraModifier = new SequenceEntityModifier(

		new MoveXModifier(duration, sTora.getX(), this.mEngine.getCamera()
				.getWidth())));
		sqToraModifier.addModifierListener(this);
		

		
	}

	private void toraTouchModifier() {
		if (isTouchTora) {
			Log.e("usagiModifier", "Tap vao Tora:");
			isTouchTora = false;
			sTora.registerEntityModifier(sqToraTouchModifier = new SequenceEntityModifier(

			(new MoveYModifier(0.1f, sTora.getY(), sTora.getY() - 110)), new DelayModifier(0.9f),
					(new MoveYModifier(0.1f, sTora.getY() - 110, sTora.getY()))));
			
			sqToraTouchModifier.addModifierListener(this);
			
		}
	}

	// /////////////////
	private void usagiModifier() {
		
		float duration = ((this.mEngine.getCamera().getWidth() - sUsagi.getX()) * 15)
				/ (this.mEngine.getCamera().getWidth() + 300);
		sUsagi.registerEntityModifier(sqUsagiModifier = new SequenceEntityModifier(

		(new MoveXModifier(duration, sUsagi.getX(), this.mEngine.getCamera()
				.getWidth()))));

		if (sqUsagiModifier != null) {
			sqUsagiModifier.addModifierListener(this);
		}

	}

	private void usagiTouchModifier() {
		if (isTouchUsagi) {
			isTouchUsagi = false;
			sUsagi.registerEntityModifier(sqUsagiTouchModifier = new SequenceEntityModifier(
					new MoveYModifier(0.1f, sUsagi.getY(), sUsagi.getY() - 120),new DelayModifier(0.9f),
					new MoveYModifier(0.1f, sUsagi.getY() - 120, sUsagi.getY())));
			if (sqUsagiTouchModifier != null) {
				sqUsagiTouchModifier.addModifierListener(this);
			}

		}
	}

	private void kumaModifier() {
		float duration = ((this.mEngine.getCamera().getWidth() - sKuma.getX()) * 15)
				/ (this.mEngine.getCamera().getWidth() + 300);
		sKuma.registerEntityModifier(sqKumaModifier = new SequenceEntityModifier(

		(new MoveXModifier(duration, sKuma.getX(), this.mEngine.getCamera()
				.getWidth()))));
		if (sqKumaModifier != null) {
			sqKumaModifier.addModifierListener(this);
		}

	}

	private void kumaTouchModifier() {
		if (isTouchKuma) {
			isTouchKuma = false;
			Log.e("kumaTouchModifier", "Tap vao gau:");
			sKuma.registerEntityModifier(sqKumaTouchModifier = new SequenceEntityModifier(

			(new MoveYModifier(0.1f, sKuma.getY(), sKuma.getY() - 110)), new DelayModifier(0.9f),
					(new MoveYModifier(0.1f, sKuma.getY() - 110, sKuma.getY()))));
			if (sqKumaTouchModifier != null) {
				sqKumaTouchModifier.addModifierListener(this);
			}

		}
	}

	private void kaminarigumoToModifier() {

		sKaminarigumo
				.registerEntityModifier(sqKaminarigumoToModifier = new SequenceEntityModifier(

				(new MoveXModifier(1.3f, sKaminarigumo.getX(), 378))));
		if (sqKaminarigumoToModifier != null) {
			sqKaminarigumoToModifier.addModifierListener(this);
		}
	}

	private void kaminarigumoAwayModifier() {

		sKaminarigumo
				.registerEntityModifier(sqKaminarigumoAwayModifier = new SequenceEntityModifier(
				new MoveXModifier(1.0f, 378, 1000)));
		if (sqKaminarigumoAwayModifier != null) {
			sqKaminarigumoAwayModifier.addModifierListener(this);
		}
	}

	private void KaminarigumoChangeResource() {
		if (isTouchKaminarigumo) {
			// mp3Cat.play();
			isTouchKaminarigumo = false;
			long pDuration[] = new long[] { 300, 600, 200 };
			int pFrams[] = new int[] { 1, 0, 1 };
			sKaminarigumo.animate(pDuration, pFrams, 0, this);
			mEngine.registerUpdateHandler(new TimerHandler(0.7f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					mp3Kaminarigumo2.play();
				}
			}));

		}
	}


	private void sqKumoAwayModifier() {
		sKumo.registerEntityModifier(sqKumoAwayModifier = new SequenceEntityModifier(

		new MoveXModifier(1.0f, 338, 1000),
		new MoveXModifier(1.5f, -250, 338)));
		if (sqKumoAwayModifier != null) {
			sqKumoAwayModifier.addModifierListener(this);
		}
	}

	private void sqAooni_pantuDownModifier() {
		isTouchAooni_pantu = false;
		isTouchAooni = false;
		sAooni_pantu
				.registerEntityModifier(sqAooni_pantuDownModifier = new SequenceEntityModifier(

				(new MoveYModifier(1.0f, sAooni_pantu.getY(), sAooni_pantu
						.getY() + 30))));
		if (sqAooni_pantuDownModifier != null) {
			sqAooni_pantuDownModifier.addModifierListener(this);
		}
	}

	private void sqAooni_pantuUpModifier() {
		mp3AooniUp_pantu.play();
		sAooni_pantu
				.registerEntityModifier(sqAooni_pantuUpModifier = new SequenceEntityModifier(

				(new MoveYModifier(1.0f, sAooni_pantu.getY(), sAooni_pantu
						.getY() - 30))));
		if (sqAooni_pantuUpModifier != null) {
			sqAooni_pantuUpModifier.addModifierListener(this);
		}
	}

	private void sqToriModifier() {
		isTouchTori = false;
		sTori.registerEntityModifier(sqToriModifier = new SequenceEntityModifier(

		new ParallelEntityModifier((new MoveYModifier(0.4f, sTori.getY(), sTori
				.getY() - 30)), (new RotationModifier(0.4f, 0f, 0.001f))),
				new SequenceEntityModifier((new MoveYModifier(0.4f, sTori
						.getY() - 30, sTori.getY())), (new RotationModifier(
						0.4f, 0f, -8f)))));
		if (sqToriModifier != null) {
			sqToriModifier.addModifierListener(this);
		}
	}

	private void sqAkaoni_pantudownModifier() {
		isTouchAkaoni_pantu = false;
		isTouchAkaoni = false;
		sAkaoni_pantu
				.registerEntityModifier(sqAkoni_pantudownModifier = new SequenceEntityModifier(

				(new MoveYModifier(1.0f, sAkaoni_pantu.getY(), sAkaoni_pantu
						.getY() + 30))));

		if (sqAkoni_pantudownModifier != null) {
			sqAkoni_pantudownModifier.addModifierListener(this);
		}
	}

	private void sqAkaoni_pantuUpModifier() {
		if (isGimmic) {
			mp3AkaoniUp_pantu.play();
		}
		sAkaoni_pantu
				.registerEntityModifier(sqAkoni_pantuUpModifier = new SequenceEntityModifier(

				(new MoveYModifier(1.0f, sAkaoni_pantu.getY(), sAkaoni_pantu
						.getY() - 30))));

		if (sqAkoni_pantuUpModifier != null) {
			sqAkoni_pantuUpModifier.addModifierListener(this);
		}
	}

	private void sqMizutamaModifier() {
		isTouchMizutama = false;
		sMizutama
				.registerEntityModifier(sqMizutamaModifier = new SequenceEntityModifier(
						new ParallelEntityModifier((new MoveXModifier(0.7f,
								sMizutama.getX(), sMizutama.getX() - 400)),
								(new MoveYModifier(0.7f, sMizutama.getY(),
										sMizutama.getY() + 300)))));
		if (sqMizutamaModifier != null) {
			sqMizutamaModifier.addModifierListener(this);
		}
	}

	private void sqCenterpantsModifier() {
		isTouchCenterpants = false;
		sCenterpants
				.registerEntityModifier(sqCenterpantsModifier = new SequenceEntityModifier(
						new ParallelEntityModifier(
								(new MoveXModifier(0.9f, sCenterpants.getX(),
										sCenterpants.getX() - 600)),
								(new MoveYModifier(0.9f, sCenterpants.getY(),
										sCenterpants.getY() + 300)))));
		if (sqCenterpantsModifier != null) {
			sqCenterpantsModifier.addModifierListener(this);
		}
	}

	private void sqSutoraipupantuModifier() {
		isTouchSutoraipupantu = false;
		sSutoraipupantu
				.registerEntityModifier(sqSutoraipupantuModifier = new SequenceEntityModifier(
						new ParallelEntityModifier((new MoveXModifier(1.1f,
								sSutoraipupantu.getX(),
								sSutoraipupantu.getX() - 800)),
								(new MoveYModifier(1.1f,
										sSutoraipupantu.getY(), sSutoraipupantu
												.getY() + 300)))));
		if (sqSutoraipupantuModifier != null) {
			sqSutoraipupantuModifier.addModifierListener(this);
		}
	}

	@Override
	public void combineGimmic3WithAction() {

		if (isTouchAooni_pantu && isTouchAkaoni_pantu && isGimmic) {
			mp3Gimmic.play();
			isGimmic = false;
			sqAooni_pantuDownModifier();
			sqAkaoni_pantudownModifier();
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sAooni, (int) sAooni.getWidth() / 8,
					(int) sAooni.getHeight() / 8,
					(int) sAooni.getWidth() * 7 / 8,
					(int) sAooni.getHeight() / 2, pX, pY)) {
				if (isTouchAooni && isTouchAooni_pantu) {
					mp3Aooni.play();
					anoniChangeResource();
				}
			} else if (checkContains(sAkaoni, (int) sAkaoni.getWidth() / 8,
					(int) sAkaoni.getHeight() / 8,
					(int) sAkaoni.getWidth() * 7 / 8,
					(int) sAkaoni.getHeight() / 2, pX, pY)) {
				if (isTouchAkaoni && isTouchAkaoni_pantu) {
					mp3Akaoni.play();
					akaoniChangeResource();
				}
			} else if (checkContains(sAooni_pantu, 0, 0,
					(int) sAooni_pantu.getWidth(),
					(int) sAooni_pantu.getHeight(), pX, pY)) {
				if (isTouchAooni_pantu && isTouchAooni) {
					mp3Aooni_pantu.play();
					sqAooni_pantuDownModifier();
				}
			} else if (checkContains(sAkaoni_pantu,
					(int) sAkaoni_pantu.getWidth() / 2, 0,
					(int) sAkaoni_pantu.getWidth(),
					(int) sAkaoni_pantu.getHeight(), pX, pY)) {
				if (isTouchAkaoni_pantu && isTouchAkaoni) {
					mp3Akaoni_pantu.play();
					sqAkaoni_pantudownModifier();
				}
			} else if (checkContains(sMizutama, (int) sMizutama.getWidth() / 3,
					0, (int) sMizutama.getWidth(), (int) sMizutama.getHeight(),
					pX, pY)) {
				if (isTouchMizutama) {
					mp3Mizu_Center_Sutor.play();
					sqMizutamaModifier();
				}
			} else if (checkContains(sCenterpants,
					(int) sCenterpants.getWidth() / 3, 0,
					(int) sCenterpants.getWidth(),
					(int) sCenterpants.getHeight(), pX, pY)) {
				if (isTouchCenterpants) {
					mp3Mizu_Center_Sutor.play();
					sqCenterpantsModifier();
				}
			} else if (checkContains(sSutoraipupantu,
					(int) sSutoraipupantu.getWidth() / 3, 0,
					(int) sSutoraipupantu.getWidth(),
					(int) sSutoraipupantu.getHeight(), pX, pY)) {
				if (isTouchSutoraipupantu) {
					mp3Mizu_Center_Sutor.play();
					sqSutoraipupantuModifier();
				}
			} else if (checkContains(sTori, 0, 0, (int) sTori.getWidth(),
					(int) sTori.getHeight(), pX, pY)) {
				if (isTouchTori) {
					mp3Tori.play();
					sqToriModifier();
				}
			} else if (checkContains(sKumo, 0, 0, (int) sKumo.getWidth(),
					(int) sKumo.getHeight(), pX, pY)) {
				 if (isTouchKumo){
					isTouchKumo = false;
					mp3Kumo.play();
					sqKumoAwayModifier();
				 }
				
			} else if (checkContains(sKaminarigumo, 0, 0,
					(int) sKaminarigumo.getWidth() * 3 / 4,
					(int) sKaminarigumo.getHeight() / 2, pX, pY)) {
				if (isTouchKaminarigumo) {
					mp3Kaminarigumo.play();
					KaminarigumoChangeResource();
				}
			} else if (checkContains(sTora, 0, 0, (int) sTora.getWidth(),
					(int) sTora.getHeight() / 2, pX, pY)) {
				if (isTouchTora) {
					mp3Usagi_Tora_Kuma.play();
					toraTouchModifier();
				}
			} else if (checkContains(sUsagi, 0, 0, (int) sUsagi.getWidth(),
					(int) sUsagi.getHeight() / 2, pX, pY)) {
				if (isTouchUsagi) {
					mp3Usagi_Tora_Kuma.play();
					usagiTouchModifier();
				}
			} else if (checkContains(sKuma, 0, 0, (int) sKuma.getWidth(),
					(int) sKuma.getHeight() / 2, pX, pY)) {
				if (isTouchKuma) {
					mp3Usagi_Tora_Kuma.play();
					kumaTouchModifier();
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite.equals(sAooni)) {
			sAooni.setCurrentTileIndex(0);
			sAooni_pantu.setVisible(true);
			mEngine.registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAooni = true;
					isTouchAooni_pantu = true;
				}
			}));

		} else if (pAnimatedSprite.equals(sAkaoni)) {
			sAkaoni.setCurrentTileIndex(0);
			sAkaoni_pantu.setVisible(true);
			mEngine.registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					isTouchAkaoni = true;
					isTouchAkaoni_pantu = true;
				}
			}));

		} else if (pAnimatedSprite.equals(sKaminarigumo)) {

			sKaminarigumo.setCurrentTileIndex(1);
			isTouchKaminarigumo = false;
			kaminarigumoAwayModifier();
		}

	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(sqAooni_pantuDownModifier)) {
			sqAooni_pantuUpModifier();
		} else if (pModifier.equals(sqAooni_pantuUpModifier)) {
			isTouchAooni_pantu = true;
			isTouchAooni = true;
		} else if (pModifier.equals(sqAkoni_pantudownModifier)) {
			sAkaoni.reset();
			sqAkaoni_pantuUpModifier();
		} else if (pModifier.equals(sqAkoni_pantuUpModifier)) {
			isTouchAkaoni_pantu = true;
			isTouchAkaoni = true;
			isGimmic = true;
		} else if (pModifier.equals(sqMizutamaModifier)) {
			sMizutama.setPosition(274, 94);
			isTouchMizutama = true;
		} else if (pModifier.equals(sqCenterpantsModifier)) {
			sCenterpants.setPosition(406, 108);
			isTouchCenterpants = true;
		} else if (pModifier.equals(sqSutoraipupantuModifier)) {
			sSutoraipupantu.setPosition(554, 102);
			isTouchSutoraipupantu = true;
		} else if (pModifier.equals(sqToriModifier)) {
			isTouchTori = true;
		} else if (pModifier.equals(sqKumoToModifier)) {
//			isTouchKumo = true;
		} else if (pModifier.equals(sqKumoAwayModifier)) {
//			sKumo.setPosition(338, 12);
			isTouchKumo= true;
//			sqKumoToModifier();
		} else if (pModifier.equals(sqKaminarigumoToModifier)) {
			isTouchKaminarigumo = true;
		} else if (pModifier.equals(sqKaminarigumoAwayModifier)) {
			sKaminarigumo.setPosition(-300, 202);
			kaminarigumoToModifier();
		} 
		if (pModifier.equals(sqToraTouchModifier)) {
			isTouchTora = true;
			toraModifier();
		} 
		if (pModifier.equals(sqUsagiTouchModifier)) {
			isTouchUsagi = true;
			usagiModifier();
		} 
		if (pModifier.equals(sqKumaTouchModifier)) {
			isTouchKuma = true;
			kumaModifier();
		} 
		if (pModifier.equals(sqToraModifier)) {
			sTora.setPosition(-300, 280);
			toraModifier();
		}
		if (pModifier.equals(sqUsagiModifier)) {
			sUsagi.setPosition(-300, 260);
			usagiModifier();
		} 
		if (pModifier.equals(sqKumaModifier)) {
			sKuma.setPosition(-300, 280);
			kumaModifier();
		}
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
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
