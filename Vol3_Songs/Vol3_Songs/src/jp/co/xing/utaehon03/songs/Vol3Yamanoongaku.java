package jp.co.xing.utaehon03.songs;

import java.util.HashMap;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3YamanoongakuResouce;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class Vol3Yamanoongaku extends BaseGameActivity implements
		IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final String TAG = "LOG_VOL3YAMANOONGAKU";
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mTexturePack;
	private TexturePackTextureRegionLibrary mTexturePackLib;

	private static boolean isKemushi = true;
	private static int ANIID[][] = new int[][] {
			{ 2048, 512, 381, 148, 5, 1 }, // risu
			{ 2048, 512, 629, 365, 5, 1 }, { 1024, 256, 66, 411, 5, 1 },
			{ 1024, 256, 226, 269, 5, 1 }, { 1024, 1024, 602, 115, 3, 3 },
			{ 1024, 1024, 68, 100, 3, 2 }, { 2048, 256, 227, 9, 5, 1 },
			{ 2048, 256, 490, 14, 5, 1 }, };

	private static int INFOFLOWER[][] = new int[][] { { 128, 64, 835, 148 },
			{ 128, 64, 80, 146 }, { 128, 64, 211, 283 },
			{ 128, 128, 277, 467 }, { 128, 64, 827, 317 }, };

	private HashMap<Integer, Sound> hashSound = new HashMap<Integer, Sound>();

	/** Texture **/


	/** TextureRegion **/
	private ITextureRegion mHaikeiTextureRegion;

	private ITextureRegion mNodeTextureRegion[] = new ITextureRegion[8];

	/** TiledTextureRegion **/
	private TiledTextureRegion mCyouTiledTextureRegion;
	private TiledTextureRegion mKemushiTiledTextureRegion;

	private TiledTextureRegion[] mAnimalsTextureRegion = new TiledTextureRegion[ANIID.length];
	private TiledTextureRegion mFlowerTextureRegion[] = new TiledTextureRegion[INFOFLOWER.length];

	/** Sprite **/
	private Sprite mHaikeiSprite;

	/** AnimatedSprite **/
	private AnimatedSprite mCyouAnimatedSprite;
	private AnimatedSprite mKemushiAnimatedSprite;

	private AniAnimatedSprite mAnimalsAniSprite[] = new AniAnimatedSprite[ANIID.length];
	private FlowerAnimatedSprite mFlowerAniSprite[] = new FlowerAnimatedSprite[INFOFLOWER.length];

	/** Sound **/
	private Sound oggPomi, oggKira;

	@Override
	protected void loadKaraokeScene() {
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		mHaikeiSprite = new Sprite(0, 0, mHaikeiTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mHaikeiSprite);

		// mNodeSprite = new Sprite(0, 0, pTextureRegion)

		for (int i = 0; i < ANIID.length; i++) {

			mAnimalsAniSprite[i] = new AniAnimatedSprite(ANIID[i][2],
					ANIID[i][3], mAnimalsTextureRegion[i], i, this.getVertexBufferObjectManager());
			this.mScene.attachChild(mAnimalsAniSprite[i]);
			this.mScene.registerTouchArea(mAnimalsAniSprite[i]);

		}
		for (int i = 0; i < INFOFLOWER.length; i++) {
			mFlowerAniSprite[i] = new FlowerAnimatedSprite(INFOFLOWER[i][2],
					INFOFLOWER[i][3], mFlowerTextureRegion[i], this.getVertexBufferObjectManager());
			this.mScene.attachChild(mFlowerAniSprite[i]);
			this.mScene.registerTouchArea(mFlowerAniSprite[i]);
		}
		Log.d(TAG,"INFOFLOWER END");
		mCyouAnimatedSprite = new AnimatedSprite(821, 214,
				mCyouTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mCyouAnimatedSprite);
		mCyouAnimatedSprite.animate(new long[] { 250, 250 },
				new int[] { 0, 1 }, -1);

		mKemushiAnimatedSprite = new AnimatedSprite(526, 394,
				mKemushiTiledTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(mKemushiAnimatedSprite);

		addGimmicsButton(mScene, Vol3YamanoongakuResouce.SOUND_GIMMIC, new int[]{
				Vol3YamanoongakuResouce.A11_YAMANOONGAKUKA_1_IPHONE_ID, Vol3YamanoongakuResouce.A11_YAMANOONGAKUKA_2_IPHONE_ID,
				Vol3YamanoongakuResouce.A11_YAMANOONGAKUKA_3_IPHONE_ID
		}, mTexturePackLib
		);
	}

	@Override
	protected void loadKaraokeResources() {
		
		mTexturePack = mTexturePackLoaderFromSource.load("AllItem.xml");
		mTexturePack.loadTexture();
		mTexturePackLib = mTexturePack.getTexturePackTextureRegionLibrary();
		
		this.mHaikeiTextureRegion = mTexturePackLib.get(Vol3YamanoongakuResouce.A11_20_IPHONE_HAIKEI_ID);

		for (int i = 0; i < ANIID.length; i++) {
			this.mAnimalsTextureRegion[i] = getTiledTextureFromPacker(mTexturePack, Vol3YamanoongakuResouce.IArrAni[i]);
		}

		for (int i = 0; i < Vol3YamanoongakuResouce.IArrNod.length; i++) {
			this.mNodeTextureRegion[i] = mTexturePackLib.get(Vol3YamanoongakuResouce.IArrNod[i]); 
		}

		for (int i = 0; i < INFOFLOWER.length; i++) {
			this.mFlowerTextureRegion[i] =  getTiledTextureFromPacker(mTexturePack, Vol3YamanoongakuResouce.IArrFlower[i]);
		}

		this.mCyouTiledTextureRegion = getTiledTextureFromPacker(mTexturePack,
				Vol3YamanoongakuResouce.A11_18_1_IPHONE_CYOU_03_ID,
				Vol3YamanoongakuResouce.A11_18_2_IPHONE_CYOU_03_ID);
		this.mKemushiTiledTextureRegion =  getTiledTextureFromPacker(mTexturePack,
				Vol3YamanoongakuResouce.A11_12_1_IPHONE_KEMUSHI_03_ID,
				Vol3YamanoongakuResouce.A11_12_2_IPHONE_KEMUSHI_03_ID,
				Vol3YamanoongakuResouce.A11_12_3_IPHONE_KEMUSHI_03_ID
				);
		Log.d(TAG,"loadKaraokeResources END");
	}

	@Override
	protected void loadKaraokeSound() {
		hashSound.put(0,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_04_VIOLIN));
		hashSound.put(1,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_05_PIANO));
		hashSound.put(2,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_06_DRUM));
		hashSound.put(3,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_07_FULUT));
		hashSound.put(4,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_08_BASE));
		hashSound
				.put(5,
						loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_09_HAMONIKA));
		hashSound.put(6,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_10_GUITER));
		hashSound.put(7,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_11_CYMBAL));

		hashSound.put(8,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_04_KIRA10));
		hashSound
				.put(9,
						loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_05_MISHIN2));
		hashSound.put(10,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_06_SOFT));
		hashSound.put(11,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_07_NEGI));
		hashSound.put(12,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_08_PITI2));
		hashSound.put(13,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_09_TABERU));
		hashSound
				.put(14,
						loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_10_HOUKIGUITER));
		hashSound.put(15,
				loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_11_SOFT));

		oggPomi = loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_12_POMI);
		oggKira = loadSoundResourceFromSD(Vol3YamanoongakuResouce.A11_13_17_KIRA5);
	}
	
	@Override
	public synchronized void onResumeGame() {
		for (int i = 0; i < ANIID.length; i++) {
			mAnimalsAniSprite[i].defaultAction();
			mAnimalsAniSprite[i].isTouched = true;
		}

		for (int i = 0; i < INFOFLOWER.length; i++) {
			mFlowerAniSprite[i].setCurrentTileIndex(0);
		}
		super.onResumeGame();
	}
	
	
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("yamanoongaku/mfx/"); // Sound Resources
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("yamanoongaku/gfx/"); // Image
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager);
		super.onCreateResources();
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isKemushi) {
			actionKemushi();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() != TouchEvent.ACTION_DOWN) {
			return true;
		}

		float pX = pSceneTouchEvent.getX();
		float pY = pSceneTouchEvent.getY();

		if (mKemushiAnimatedSprite.contains(pX, pY)) {
			if (isKemushi) {
				actionKemushi();
			}
		}

		return true;
	}

	private void actionKemushi() {
		isKemushi = false;
		oggPomi.play();
		mKemushiAnimatedSprite.animate(new long[] { 200, 200 }, new int[] { 1,
				2 }, 2, new IAnimationListener() {

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				mKemushiAnimatedSprite.setCurrentTileIndex(0);
				isKemushi = true;
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1,
					int arg2) {}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1,
					int arg2) {	}

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
		});
	}

	// Ramdom array
	private int rand(int length) {
		return new Random().nextInt(length);
	}

	// Class flower
	private class FlowerAnimatedSprite extends AnimatedSprite {

		public FlowerAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertext) {
			super(pX, pY, pTiledTextureRegion, pVertext);

		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
				oggKira.play();
				if (this.getCurrentTileIndex() == 0) {
					this.setCurrentTileIndex(1);
				} else {
					this.setCurrentTileIndex(0);
				}
			}
			return true;
		}

	}

	// Class Animals
	private class AniAnimatedSprite extends AnimatedSprite {

		private int Id = 0;
		private boolean isTouched = true;

		public AniAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, int idlist, VertexBufferObjectManager pVertext) {
			super(pX, pY, pTiledTextureRegion, pVertext);

			this.Id = idlist;

		}

		public void defaultAction() {
			final long duration[] = { 250, 250 };
			final int frame[] = { 0, 1 };
			this.animate(duration, frame, -1);
		}

		private void touchAction() {
			this.isTouched = false;
			long duration[] = { 1000 };
			int frame[] = { 4 };
			if (this.Id == 1) {
				duration = new long[] { 2900 };
			} else if (this.Id == 4) {
				frame = new int[] { 6 };
			} else if (this.Id == 5) {
				frame = new int[] { 5 };
			} else if (this.Id == 7) {
				duration = new long[] { 1300 };
			}
			hashSound.get(this.Id + mNodeTextureRegion.length).play();
			this.animate(duration, frame, 0, new IAnimationListener() {


				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					AniAnimatedSprite.this.defaultAction();
					AniAnimatedSprite.this.isTouched = true;
				}

				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			});
		}

		private void touchInstruments() {
			this.isTouched = false;
			long duration[] = { 375, 375 };
			int frame[] = { 2, 3 };
			int loop = 1;
			if (this.Id == 0) {
				duration = new long[] { 250, 250 };
				loop = 3;
			} else if (this.Id == 2) {
				duration = new long[] { 180, 180 };
			} else if (this.Id == 3) {
				duration = new long[] { 300, 300 };
			} else if (this.Id == 4) {
				duration = new long[] { 180, 180, 180, 180 };
				frame = new int[] { 2, 3, 4, 5 };
			} else if (this.Id == 5) {
				duration = new long[] { 285, 285, 285 };
				frame = new int[] { 2, 3, 4 };
			} else if (this.Id == 6) {
				duration = new long[] { 262, 262 };
				loop = 3;
			}

			hashSound.get(this.Id).play();
			this.animate(duration, frame, loop, new IAnimationListener() {


				@Override
				public void onAnimationFinished(AnimatedSprite arg0) {
					AniAnimatedSprite.this.defaultAction();
				}

				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}
			});

			float centerX = this.getX() + this.getWidth() / 2;
			float centerY = this.getY() + this.getHeight() / 2;

			int nod1 = rand(mNodeTextureRegion.length);
			int nod2 = rand(mNodeTextureRegion.length);
			int nod3 = rand(mNodeTextureRegion.length);

			// new NODE
			final Sprite nod1Sprite = new Sprite(centerX
					- mNodeTextureRegion[nod1].getWidth() / 2, centerY
					- mNodeTextureRegion[nod1].getHeight() / 2,
					mNodeTextureRegion[nod1], this.getVertexBufferObjectManager());

			final Sprite nod2Sprite = new Sprite(centerX
					- mNodeTextureRegion[nod2].getWidth() / 2, centerY
					- mNodeTextureRegion[nod2].getHeight() / 2,
					mNodeTextureRegion[nod2], this.getVertexBufferObjectManager());

			final Sprite nod3Sprite = new Sprite(centerX
					- mNodeTextureRegion[nod3].getWidth() / 2, centerY
					- mNodeTextureRegion[nod3].getHeight() / 2,
					mNodeTextureRegion[nod3], this.getVertexBufferObjectManager());

			Vol3Yamanoongaku.this.mHaikeiSprite.attachChild(nod1Sprite);
			Vol3Yamanoongaku.this.mHaikeiSprite.attachChild(nod2Sprite);
			Vol3Yamanoongaku.this.mHaikeiSprite.attachChild(nod3Sprite);

			nod1Sprite.registerEntityModifier(new SequenceEntityModifier(

			new MoveModifier(1.0f, nod1Sprite.getX(), nod1Sprite.getX() - 85,
					nod1Sprite.getY(), nod1Sprite.getY() - 70),
					new AlphaModifier(0.5f, 1.0f, 0.5f)

			));

			nod2Sprite.registerEntityModifier(new SequenceEntityModifier(

			new MoveModifier(1.0f, nod1Sprite.getX(), nod1Sprite.getX(),
					nod1Sprite.getY(), nod1Sprite.getY() - 100),
					new AlphaModifier(0.5f, 1.0f, 0.5f)

			));

			nod3Sprite.registerEntityModifier(new SequenceEntityModifier(

			new MoveModifier(1.0f, nod1Sprite.getX(), nod1Sprite.getX() + 85,
					nod1Sprite.getY(), nod1Sprite.getY() - 70),
					new AlphaModifier(0.5f, 1.0f, 0.5f)

			));
			
			mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					runOnUpdateThread(new Runnable() {

						@Override
						public void run() {
							nod1Sprite.detachSelf();
							nod2Sprite.detachSelf();
							nod3Sprite.detachSelf();
							AniAnimatedSprite.this.isTouched = true;
						}
					});
				}
			}));


		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pLX,
				float pLY) {

			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN
					&& isTouched) {
				Log.d(TAG, " It's me X:" + pLX + " Y" + pLY);
				switch (this.Id) {
				case 0:

					if (pLX >= 75 && pLY >= 100 && pLX <= 180 && pLY <= 180) {
						this.touchAction();
						break;
					}

					if (pLX >= 20 && pLY >= 0 && pLX <= 230 && pLY <= 250) {
						touchInstruments();
						break;
					}
					break;

				case 1:

					if (pLX >= 10 && pLY >= 95 && pLX <= 135 && pLY <= 205) {
						this.touchAction();
						break;
					}

					if (pLX >= 0 && pLY >= 50 && pLX <= 250 && pLY <= 250) {
						touchInstruments();
						break;
					}
					break;

				case 2:

					if (pLX >= 15 && pLY >= 18 && pLX <= 65 && pLY <= 120) {
						this.touchAction();
						break;
					}

					if (pLX >= 150 && pLY >= 10 && pLX <= 189 && pLY <= 110) {
						this.touchAction();
						break;
					}

					if (pLX >= 0 && pLY >= 10 && pLX <= 185 && pLY <= 200) {
						touchInstruments();
						break;
					}
					break;

				case 3:

					if (pLX >= 10 && pLY >= 79 && pLX <= 135 && pLY <= 131) {
						this.touchAction();
						break;
					}

					if (pLX >= 0 && pLY >= 0 && pLX <= 135 && pLY <= 170) {
						touchInstruments();
						break;
					}
					break;
				case 4:

					if (pLX >= 105 && pLY >= 125 && pLX <= 200 && pLY <= 265) {
						this.touchAction();
						break;
					}

					if (pLX >= 145 && pLY >= 0 && pLX <= 190 && pLY <= 125) {
						this.touchAction();
						break;
					}

					if (pLX >= 20 && pLY >= 45 && pLX <= 150 && pLY <= 280) {
						touchInstruments();
						break;
					}

					break;

				case 5:

					if (pLX >= 90 && pLY >= 130 && pLX <= 180 && pLY <= 175) {
						this.touchAction();
						break;
					}

					if (pLX >= 10 && pLY >= 0 && pLX <= 160 && pLY <= 260) {
						touchInstruments();
						break;
					}
					break;

				case 6:

					if (pLX >= 30 && pLY >= 120 && pLX <= 140 && pLY <= 195) {
						this.touchAction();
						break;
					}

					if (pLX >= 140 && pLY >= 88 && pLX <= 240 && pLY <= 131) {
						this.touchAction();
						break;
					}

					if (pLX >= 30 && pLY >= 0 && pLX <= 160 && pLY <= 230) {
						touchInstruments();
						break;
					}
					break;

				case 7:

					if (pLX >= 10 && pLY >= 40 && pLX <= 55 && pLY <= 140) {
						this.touchAction();
						break;
					}

					if (pLX >= 112 && pLY >= 52 && pLX <= 165 && pLY <= 155) {
						this.touchAction();
						break;
					}

					if (pLX >= 35 && pLY >= 0 && pLX <= 165 && pLY <= 165) {
						touchInstruments();
						break;
					}
					break;

				}
			}

			return true;
		}

	}
}
