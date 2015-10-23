package jp.co.xing.utaehon03.songs;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3MomotaroResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public class Vol3Momotaro extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity> {

	// Define tag log for this class
	private String TAG_LOG = Vol3Momotaro.this.getClass().toString();
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack Vol3MomotaroPacker_1, Vol3MomotaroPacker_2;
	private TexturePackTextureRegionLibrary Vol3MomotaroLibrary1,
			Vol3MomotaroLibrary2;
	private BitmapTextureAtlas ttKumo;
	private ITextureRegion ttrBackground, ttrNami1, ttrNami2, ttrNami3,
			ttrMomo, ttrMomotarohear, ttrKumo1, ttrKumo2, ttrKumo3, ttrKumo4,
			ttrKumo5, ttrKumo, ttrKiji1, ttrKiji2, ttrKingyo, ttrKemuri1,
			ttrKemuri2, ttrKemuri3, ttrKemuri4, ttrKemuri5, ttrKusa1, ttrKusa2,
			ttrKaminarikumo;
	private TiledTextureRegion ttrMomotaro, ttrFutari, ttrAhuro, ttrAkaoni,
			ttrOjisan, ttrUmibouzu, ttrInu, ttrSaru, ttrOnigashima;
	private AnimatedSprite sMomotaro, sFutari, sAhuro, sAkaoni, sOjisan,
			sUmibouzu, sInu, sSaru, sSaru_Virtual, sOnigashima;
	private Sprite sNami1, sNami2, sNami3, sMomo, sMomotarohear, sKumo1,
			sKumo2, sKumo3, sKumo4, sKumo5, sKumoall1, sKumoall2, sKiji1,
			sKiji2, sKingyo, sKemuri1, sKemuri2, sKemuri3, sKemuri4, sKemuri5,
			sKusa1, sKusa2, sKaminarikumo, sKumo11, sKumo22, sKumo33, sKumo44,
			sKumo55;
	private SequenceEntityModifier sqMomo, sqMomotaroHear, sqKiji, sqKijiDown,
			sqKingyo1, sqKingyo2, sqKemuri1, sqKemuri2, sqKemuri3, sqKemuri4,
			sqKemuri5, sqKusa11, sqKusa12, sqKusa21, sqKusa22, sqKaminarikumo,
			sqKaminarikumohome;
	@SuppressWarnings("unused")
	private LoopEntityModifier loopSura;
	private boolean isTouchFutari, isTouchMomo, isTouchAhuro, isTouchAkaoni,
			isTouchOjisan, isTouchUmibouzu, isTouchMomotaro, isTouchKiji,
			isTouchInu, isTouchSaru, isTouchKingyo, isTouchKusa1, isTouchKusa2,
			isTouchGimmic, isTouchOnigashima;

	private Sound mp3Kusa, mp3Kiji, mp3Saru1, mp3Saru2, mp3Inu, mp3Momotaro,
			mp3Futari, mp3Ahuro_Umibouzu_Akaoni, mp3Ojisan, mp3Kaminarikumo,
			mp3Onigashima, mp3Momo, mp3Kingyo;

	private IUpdateHandler RunCloud;

	@Override
	public void onCreateResources() {
		Log.d(TAG_LOG, "Create Resource");
		SoundFactory.setAssetBasePath("momotaro/mfx/");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("momotaro/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(
				getTextureManager(), pAssetManager, "momotaro/gfx/");
		super.onCreateResources();
	}

	@Override
	public void onResume() {
		innital();
		if (sKusa1 != null) {
			sKusa1.setVisible(true);
			sKusa1.setAlpha(1.0f);
			sKusa1.setPosition(0, 540);
		}
		if (sKusa2 != null) {
			sKusa2.setVisible(false);
			sKusa2.setAlpha(1.0f);
			sKusa2.setPosition(0, 490);
		}
		if (sKiji1 != null) {
			sqKijiModifier();
		}
		if (sSaru != null) {
			saruRotation();
		}
		if (sInu != null) {
			InuClapandChangeResource();
		}
		if (sMomotaro != null) {
			momotaroClapandChangeResource();
		}
		if (sKemuri1 != null) {
			sKemuri1.registerEntityModifier(new DelayModifier(3.5f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sqKemuri1Modifier();
						}
					}));
		}

		if (sKemuri2 != null) {
			sKemuri2.registerEntityModifier(new DelayModifier(7.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sqKemuri2Modifier();
						}
					}));
		}
		if (sKemuri3 != null) {
			sKemuri3.registerEntityModifier(new DelayModifier(10.05f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sqKemuri3Modifier();
						}
					}));
		}
		if (sKemuri4 != null) {
			sKemuri4.registerEntityModifier(new DelayModifier(14.0f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sqKemuri4Modifier();
						}
					}));
		}

		if (sKemuri5 != null) {
			sKemuri4.registerEntityModifier(new DelayModifier(17.5f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							sqKemuri5Modifier();
						}
					}));
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (mScene != null) {
			mScene.unregisterUpdateHandler(RunCloud);
		}
		if (sKusa1 != null) {
			sKusa1.clearEntityModifiers();
			sqKusa11 = null;
			sqKusa12 = null;
		}
		if (sKusa2 != null) {
			sKusa2.clearEntityModifiers();
			sqKusa21 = null;
			sqKusa22 = null;
		}
		if (sKiji1 != null) {
			sKiji1.clearEntityModifiers();
			sqKiji = null;
			sKiji1.setPosition(678, -19);
			sKiji2.setVisible(false);
		}
		if (sSaru != null) {
			sSaru.clearEntityModifiers();
			sSaru_Virtual.stopAnimation();
			sSaru_Virtual.setCurrentTileIndex(0);
			sSaru_Virtual.setVisible(false);
			sSaru.setVisible(true);
			sMomotaro.setVisible(true);
			loopSura = null;
		}
		if (sInu != null) {
			sInu.stopAnimation();
			sInu.setCurrentTileIndex(0);
			sMomotaro.setVisible(true);
		}
		if (sSaru_Virtual != null) {
			sSaru_Virtual.clearEntityModifiers();
			sSaru_Virtual.stopAnimation();
			sSaru_Virtual.setCurrentTileIndex(0);
			sSaru_Virtual.setVisible(false);
		}
		if (sMomotarohear != null) {
			sMomotarohear.clearEntityModifiers();
			sqMomotaroHear = null;
			sMomotarohear.setVisible(false);

		}
		if (sFutari != null) {
			sFutari.stopAnimation();
			sFutari.setCurrentTileIndex(0);
		}
		if (sAhuro != null) {
			sAhuro.stopAnimation();
			sAhuro.setCurrentTileIndex(0);
		}
		if (sUmibouzu != null) {
			sUmibouzu.stopAnimation();
			sUmibouzu.setCurrentTileIndex(0);
		}
		if (sOjisan != null) {
			sOjisan.stopAnimation();
			sOjisan.setCurrentTileIndex(0);
		}
		if (sAkaoni != null) {
			sAkaoni.stopAnimation();
			sAkaoni.setCurrentTileIndex(0);
		}
		if (sKaminarikumo != null) {
			sKaminarikumo.clearEntityModifiers();
			sqKaminarikumo = null;
			sKaminarikumo.setPosition(73, -208);
		}
		if (sKemuri1 != null) {
			sKemuri1.clearEntityModifiers();
			sqKemuri1 = null;
			sKemuri1.setPosition(310, 80);
		}
		if (sKemuri2 != null) {
			sKemuri2.clearEntityModifiers();
			sqKemuri2 = null;
			sKemuri2.setPosition(310, 80);
		}
		if (sKemuri3 != null) {
			sKemuri3.clearEntityModifiers();
			sqKemuri3 = null;
			sKemuri3.setPosition(310, 80);
		}
		if (sKemuri4 != null) {
			sKemuri4.clearEntityModifiers();
			sqKemuri4 = null;
			sKemuri4.setPosition(310, 80);
		}
		if (sKemuri5 != null) {
			sKemuri5.clearEntityModifiers();
			sqKemuri5 = null;
			sKemuri5.setPosition(310, 80);
		}
		if (sKumoall1 != null) {
			sKumoall1.setPosition(0, 0);
		}
		if (sKumoall2 != null) {
			sKumoall2.setPosition(1110, 0);
		}
		if (sMomo != null) {
			sMomo.clearEntityModifiers();
			sqMomo = null;
			sMomo.setPosition(645, 169);
		}
		if (sKingyo != null) {
			sKingyo.setPosition(52f, 345f);
			sKingyo.setRotation(0);
			sKingyo.clearEntityModifiers();
			sqKingyo1 = null;
			sqKingyo2 = null;
			sKingyo.setVisible(true);
		}
		if (sMomotaro != null) {
			sMomotaro.clearEntityModifiers();
			sMomotaro.stopAnimation();
			sMomotaro.setCurrentTileIndex(0);
		}
		if (sOnigashima != null) {
			sOnigashima.clearEntityModifiers();
			sOnigashima.stopAnimation();
			sOnigashima.setCurrentTileIndex(0);
		}
		super.onPause();
	}

	private void changeOnigashima() {
		long pDurationSardine[] = new long[] { 1000, 1000 };
		int pFramsSardine[] = new int[] { 1, 2 };
		sOnigashima.setCurrentTileIndex(1);
		sOnigashima.animate(pDurationSardine, pFramsSardine, 1,
				new IAnimationListener() {
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
						if (arg2 == 1 || arg2 == 3) {
							mp3Onigashima.play();
						}
					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						sOnigashima.registerEntityModifier(new DelayModifier(
								0.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0,
											IEntity arg1) {
									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> arg0,
											IEntity arg1) {
										sOnigashima.setCurrentTileIndex(0);
									}
								}));

					}
				});

	}

	private void momotaroClapandChangeResource() {
		if (isTouchMomotaro) {
			long pDurationSardine[] = new long[] { 500, 500 };
			int pFramsSardine[] = new int[] { 0, 1 };
			sMomotaro.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			long pDurationSardine[] = new long[] { 1500 };
			int pFramsSardine[] = new int[] { 2 };
			sMomotaro.animate(pDurationSardine, pFramsSardine, 0, this);
		}
	}

	private void InuClapandChangeResource() {
		if (isTouchInu) {
			long pDurationSardine[] = new long[] { 500, 500 };
			int pFramsSardine[] = new int[] { 0, 1 };
			sInu.animate(pDurationSardine, pFramsSardine, -1);
		} else {
			sMomotaro.setVisible(false);
			long pDurationSardine[] = new long[] { 400, 400 };
			int pFramsSardine[] = new int[] { 2, 3 };
			sInu.animate(pDurationSardine, pFramsSardine, 1, this);
		}
	}

	private void SaruChangeResource() {
		mp3Saru1.play();
		long pDurationSardine[] = new long[] { 500, 500, 600 };
		int pFramsSardine[] = new int[] { 1, 2, 3 };
		sSaru_Virtual.animate(pDurationSardine, pFramsSardine, 0, this);
		sSaru_Virtual.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						mp3Saru2.play();
					}
				}));
	}

	private void saruRotation() {
		sSaru.setRotationCenter(10.0f, 0.0f);
		sSaru.registerEntityModifier(loopSura = new LoopEntityModifier(
				new SequenceEntityModifier(new RotationModifier(1.0f, 0.0f,
						10.0f), new RotationModifier(1.0f, 10.0f, 0.0f))));
	}

	private void KingyoRotation() {
		mp3Kingyo.play();
		sKingyo.setRotationCenter(172.0f, 85.0f);
		sKingyo.registerEntityModifier(sqKingyo1 = new SequenceEntityModifier(
				new RotationModifier(0.4f, 0.0f, 140.0f)));
		if (sqKingyo1 != null) {
			sqKingyo1.addModifierListener(this);
		}
	}

	// virtual Rotation to move image to old position;
	private void KingyoVirtualHomeRotation() {
		sKingyo.setRotationCenter(172.0f, 85.0f);
		sKingyo.registerEntityModifier(sqKingyo2 = new SequenceEntityModifier(
				new RotationModifier(1.0f, 140.0f, 0.0f)));
		if (sqKingyo2 != null) {
			sqKingyo2.addModifierListener(this);
		}
	}

	private void momotarohearRotation() {
		sMomotarohear.setVisible(true);
		sMomotarohear
				.registerEntityModifier(sqMomotaroHear = new SequenceEntityModifier(
						new RotationModifier(1.0f, 0.0f, 360f)));
		if (sqMomotaroHear != null) {
			sqMomotaroHear.addModifierListener(this);
		}
	}

	private void futariChangeResource() {
		if (isTouchFutari) {
			mp3Futari.play();
			isTouchFutari = false;
			long pDuration[] = new long[] { 300, 300, 300, 300, 300 };
			int pFrams[] = new int[] { 0, 1, 0, 1, 0 };
			sFutari.animate(pDuration, pFrams, 0, this);
		}
	}

	private void ahuroChangeResource() {
		if (isTouchAhuro) {
			mp3Ahuro_Umibouzu_Akaoni.play();
			isTouchAhuro = false;
			long pDuration[] = new long[] { 500, 1800, 400 };
			int pFrams[] = new int[] { 0, 1, 0 };
			sAhuro.animate(pDuration, pFrams, 0, this);
		}
	}

	private void akaoniChangeResource() {
		if (isTouchAkaoni) {
			mp3Ahuro_Umibouzu_Akaoni.play();
			isTouchAkaoni = false;
			long pDuration[] = new long[] { 500, 1800, 400 };
			int pFrams[] = new int[] { 0, 1, 0 };
			sAkaoni.animate(pDuration, pFrams, 0, this);
		}
	}

	private void ojisanChangeResource() {
		if (isTouchOjisan) {
			mp3Ojisan.play();
			isTouchOjisan = false;
			long pDuration[] = new long[] { 200, 600, 200 };
			int pFrams[] = new int[] { 0, 1, 0 };
			sOjisan.animate(pDuration, pFrams, 0, this);
		}
	}

	private void umibouzuChangeResource() {
		if (isTouchUmibouzu) {
			mp3Ahuro_Umibouzu_Akaoni.play();
			isTouchUmibouzu = false;
			long pDuration[] = new long[] { 500, 1800, 400 };
			int pFrams[] = new int[] { 0, 1, 0 };
			sUmibouzu.animate(pDuration, pFrams, 0, this);
		}
	}

	private void sqMomoModifier() {
		sMomo.registerEntityModifier(sqMomo = new SequenceEntityModifier(
				new MoveModifier(0.6f, 645f, 500f, 169f, 170f),
				new MoveModifier(0.6f, 500f, 430f, 170f, 190f),
				new MoveModifier(0.6f, 430f, 420f, 190f, 250f),
				new MoveModifier(1.3f, 420f, 520f, 250f, 320f),
				new MoveModifier(3.9f, 520f, -550f, 320f, 370f)));
		if (sqMomo != null) {
			sqMomo.addModifierListener(this);
		}
	}

	private void sqKusa11() {
		mp3Kusa.play();
		sKusa1.registerEntityModifier(sqKusa11 = new SequenceEntityModifier(
				new ParallelEntityModifier(new MoveYModifier(1.0f, 540.0f,
						470.0f), new AlphaModifier(1.0f, 1.0f, 0.1f)),
				new DelayModifier(0.6f)));
		if (sqKusa11 != null) {
			sqKusa11.addModifierListener(this);
		}
	}

	private void sqKusa12() {
		mp3Kusa.play();
		sKusa1.setPosition(0, 540);
		sKusa1.registerEntityModifier(sqKusa12 = new SequenceEntityModifier(
				new ParallelEntityModifier(
				// new MoveYModifier(1.0f, 640.0f, 540.0f),
						new AlphaModifier(1.0f, 0.1f, 1.0f)),
				new DelayModifier(0.6f)));
		if (sqKusa12 != null) {
			sqKusa12.addModifierListener(this);
		}
	}

	private void sqKusa21() {
		sKusa2.setPosition(0.0f, 490.0f);
		sKusa2.registerEntityModifier(sqKusa21 = new SequenceEntityModifier(
				new ParallelEntityModifier(
						new MoveYModifier(1.0f, 490f, 470.0f),
						new AlphaModifier(1.0f, 0.1f, 1.0f))));
		if (sqKusa21 != null) {
			sqKusa21.addModifierListener(this);
		}
	}

	private void sqKusa22() {
		sKusa2.registerEntityModifier(sqKusa22 = new SequenceEntityModifier(
				new ParallelEntityModifier(
						new MoveYModifier(1.0f, 470f, 540.0f),
						new AlphaModifier(1.0f, 1.0f, 0.1f))));
		if (sqKusa22 != null) {
			sqKusa22.addModifierListener(this);
		}
	}

	private void sqKijiModifier() {
		sKiji1.registerEntityModifier(sqKiji = new SequenceEntityModifier(
				new MoveYModifier(1.0f, sKiji1.getY(), sKiji1.getY() + 60f),
				new MoveYModifier(1.0f, sKiji1.getY() + 60f, sKiji1.getY())));
		if (sqKiji != null) {
			sqKiji.addModifierListener(this);
		}
	}

	private void sqKijiDownModifier() {
		float y = sKiji1.getY();
		Log.d("TEST", "Toa do y la: " + y);
		sKiji1.clearEntityModifiers();
		sKiji1.registerEntityModifier(sqKijiDown = new SequenceEntityModifier(
				new MoveModifier(1.0f, sKiji1.getX(), sKiji1.getX() - 110f, y,
						135, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> arg0, IEntity arg1) {
								sMomotaro.setVisible(false);
								sKiji2.setVisible(true);
								sKiji1.registerEntityModifier(new DelayModifier(
										0.6f, new IEntityModifierListener() {
											@Override
											public void onModifierStarted(
													IModifier<IEntity> arg0,
													IEntity arg1) {
											}

											@Override
											public void onModifierFinished(
													IModifier<IEntity> arg0,
													IEntity arg1) {
												sMomotaro.setVisible(true);
												sKiji2.setVisible(false);
											}
										}));
							}
						}), new DelayModifier(0.6f), new MoveModifier(1.0f,
						sKiji1.getX() - 110f, sKiji1.getX(), 135, -19)));
		if (sqKijiDown != null) {
			sqKijiDown.addModifierListener(this);
		}
	}

	private void sqKaminarikumo() {
		sKaminarikumo
				.registerEntityModifier(sqKaminarikumo = new SequenceEntityModifier(
						new MoveYModifier(3.5f, sKaminarikumo.getY(),
								sKaminarikumo.getY() + 130), new DelayModifier(
								1.0f)));
		if (sqKaminarikumo != null) {
			sqKaminarikumo.addModifierListener(this);
		}
	}

	private void sqKaminarikumohome() {
		sKaminarikumo
				.registerEntityModifier(sqKaminarikumohome = new SequenceEntityModifier(
						new MoveYModifier(1.0f, sKaminarikumo.getY(),
								sKaminarikumo.getY() - 130)));
		if (sqKaminarikumohome != null) {
			sqKaminarikumohome.addModifierListener(this);
		}
	}

	private void sqKemuri1Modifier() {
		sKemuri1.setVisible(true);
		sKemuri1.registerEntityModifier(sqKemuri1 = new SequenceEntityModifier(
				new MoveModifier(21.0f, 310, 200, 77, -88)));
		if (sqKemuri1 != null) {
			sqKemuri1.addModifierListener(this);
		}
	}

	private void sqKemuri2Modifier() {
		sKemuri2.setVisible(true);
		sKemuri2.registerEntityModifier(sqKemuri2 = new SequenceEntityModifier(
				new MoveModifier(21.80f, 310, 200, 80, -100)));
		if (sqKemuri2 != null) {
			sqKemuri2.addModifierListener(this);
		}
	}

	private void sqKemuri3Modifier() {
		sKemuri3.setVisible(true);
		sKemuri3.registerEntityModifier(sqKemuri3 = new SequenceEntityModifier(
				new MoveModifier(21.0f, 310, 200, 80, -100)));
		if (sqKemuri3 != null) {
			sqKemuri3.addModifierListener(this);
		}
	}

	private void sqKemuri4Modifier() {
		sKemuri4.setVisible(true);
		sKemuri4.registerEntityModifier(sqKemuri4 = new SequenceEntityModifier(
				new MoveModifier(21.0f, 310, 200, 80, -100)));
		if (sqKemuri4 != null) {
			sqKemuri4.addModifierListener(this);
		}
	}

	private void sqKemuri5Modifier() {
		sKemuri5.setVisible(true);
		sKemuri5.registerEntityModifier(sqKemuri5 = new SequenceEntityModifier(
				new MoveModifier(21.0f, 310, 200, 80, -100)));
		if (sqKemuri5 != null) {
			sqKemuri5.addModifierListener(this);
		}
	}

	@Override
	public void combineGimmic3WithAction() {
		if (isTouchKusa1 & isTouchGimmic) {
			isTouchGimmic = false;
			isTouchKusa1 = false;
			isTouchKusa2 = false;
			sKusa2.setVisible(true);
			sqKusa11();
			sqKusa21();
		}
		if (isTouchKusa2 && isTouchGimmic) {
			isTouchKusa1 = false;
			isTouchKusa2 = false;
			isTouchGimmic = false;
			sKusa1.setVisible(true);
			Log.d("TEST", "Touch on Kusa12 tai gimmic");
			sqKusa12();
			sqKusa22();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (checkContains(sFutari, 0, 0, (int) sFutari.getWidth(),
					(int) sFutari.getHeight(), pX, pY)) {
				futariChangeResource();
			} else if (checkContains(sMomo, 0, 0, (int) sMomo.getWidth(),
					(int) sMomo.getHeight(), pX, pY)) {
				if (isTouchMomo) {
					isTouchMomo = false;
					mp3Momo.play();
					sqMomoModifier();
				}
			} else if (checkContains(sKusa1, 0, 0, (int) sKusa1.getWidth(),
					(int) sKusa1.getHeight(), pX, pY)) {
				if (isTouchKusa1 && isTouchGimmic) {
					isTouchKusa1 = false;
					isTouchKusa2 = false;
					isTouchGimmic = false;
					sKusa2.setVisible(true);
					sqKusa11();
					sqKusa21();
				}
			} else if (checkContains(sKusa2, 0, 0, (int) sKusa2.getWidth(),
					(int) sKusa2.getHeight(), pX, pY)) {
				if (isTouchKusa2 && isTouchGimmic) {
					isTouchKusa1 = false;
					isTouchKusa2 = false;
					isTouchGimmic = false;
					sKusa1.setVisible(true);
					sqKusa12();
					sqKusa22();
				}
			} else if (checkContains(sAhuro, 0,
					(int) sAhuro.getHeight() * 2 / 3, (int) sAhuro.getWidth(),
					(int) sAhuro.getHeight(), pX, pY)) {
				ahuroChangeResource();
			} else if (checkContains(sAkaoni, 0,
					(int) sAkaoni.getHeight() * 2 / 3,
					(int) sAkaoni.getWidth() / 2, (int) sAkaoni.getHeight(),
					pX, pY)) {
				akaoniChangeResource();
			} else if (checkContains(sOjisan, 0,
					(int) sOjisan.getHeight() * 2 / 3,
					(int) sOjisan.getWidth(), (int) sOjisan.getHeight(), pX, pY)) {
				ojisanChangeResource();
			} else if (checkContains(sUmibouzu, 0,
					(int) sUmibouzu.getHeight() * 2 / 3,
					(int) sUmibouzu.getWidth(), (int) sUmibouzu.getHeight(),
					pX, pY)) {
				umibouzuChangeResource();

			} else if (checkContains(sMomotaro, (int) sMomotaro.getWidth() / 2,
					(int) sMomotaro.getHeight() / 3,
					(int) sMomotaro.getWidth() * 2 / 3,
					(int) sMomotaro.getHeight(), pX, pY)) {
				if (isTouchMomotaro && isTouchInu && isTouchSaru && isTouchKiji) {
					isTouchMomotaro = false;
					isTouchSaru = false;
					isTouchInu = false;
					isTouchKiji = false;
					mp3Momotaro.play();
					momotaroClapandChangeResource();
					momotarohearRotation();
				}
			} else if (checkContains(sKiji1, 0, 0, (int) sKiji1.getWidth(),
					(int) sKiji1.getHeight(), pX, pY)) {
				if (isTouchInu && isTouchSaru && isTouchKiji && isTouchMomotaro) {
					isTouchSaru = false;
					isTouchInu = false;
					isTouchKiji = false;
					isTouchMomotaro = false;
					mp3Kiji.play();
					// sKiji1.reset();
					sqKijiDownModifier();
				}
			} else if (checkContains(sInu, (int) sInu.getWidth() / 2,
					(int) sInu.getHeight() / 2, (int) sInu.getWidth(),
					(int) sInu.getHeight() * 2 / 3, pX, pY)) {
				if (isTouchInu && isTouchSaru && isTouchKiji && isTouchMomotaro) {
					isTouchSaru = false;
					isTouchInu = false;
					isTouchKiji = false;
					isTouchMomotaro = false;
					// sMomotaro.setVisible(false);
					mp3Inu.play();
					InuClapandChangeResource();
				}
			} else if (checkContains(sKingyo, 0, 0, (int) sKingyo.getWidth(),
					(int) sKingyo.getHeight(), pX, pY)) {
				if (isTouchKingyo) {
					isTouchKingyo = false;
					KingyoRotation();
				}
			} else if (checkContains(sSaru, 0, (int) sSaru.getHeight() * 2 / 3,
					(int) sSaru.getWidth() / 4, (int) sSaru.getHeight(), pX, pY)) {
				if (isTouchSaru && isTouchInu && isTouchKiji && isTouchMomotaro) {
					isTouchSaru = false;
					isTouchInu = false;
					isTouchKiji = false;
					isTouchMomotaro = false;

					sSaru_Virtual.setVisible(true);
					sMomotaro.setVisible(false);
					sSaru.setVisible(false);
					SaruChangeResource();
				}
			} else if (checkContains(sOnigashima, 0, 0,
					(int) sOnigashima.getWidth() * 2 / 5,
					(int) sOnigashima.getHeight() * 2 / 5, pX, pY)) {
				if (isTouchOnigashima) {
					isTouchOnigashima = false;
					mp3Kaminarikumo.play();
					sqKaminarikumo();
					changeOnigashima();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if (pModifier.equals(sqMomo)) {
			isTouchMomo = true;
			sMomo.setPosition(645f, 169f);
		}
		if (pModifier.equals(sqKiji)) {
			sqKijiModifier();
		}
		if (pModifier.equals(sqKijiDown)) {
			isTouchKiji = true;
			isTouchSaru = true;
			isTouchInu = true;
			isTouchMomotaro = true;
			sMomotaro.setVisible(true);
			sKiji2.setVisible(false);
			sqKijiModifier();
		}
		if (pModifier.equals(sqKingyo1)) {
			sKingyo.setVisible(false);
			KingyoVirtualHomeRotation();
		} else if (pModifier.equals(sqKingyo2)) {
			isTouchKingyo = true;
			sKingyo.setPosition(52f, 345f);
			sKingyo.setVisible(true);
		} else if (pModifier.equals(sqKemuri1)) {
			sqKemuri1Modifier();
		} else if (pModifier.equals(sqKemuri2)) {
			sqKemuri2Modifier();
		} else if (pModifier.equals(sqKemuri3)) {
			sqKemuri3Modifier();
		} else if (pModifier.equals(sqKemuri4)) {
			sqKemuri4Modifier();
		} else if (pModifier.equals(sqKemuri5)) {
			sqKemuri5Modifier();
		} else if (pModifier.equals(sqKusa11)) {
			sKusa1.setPosition(0, 640);
			sKusa1.setVisible(false);
			isTouchKusa2 = true;
			isTouchKusa1 = false;
			isTouchGimmic = true;
		} else if (pModifier.equals(sqKusa12)) {
			sKusa2.setVisible(false);
			isTouchKusa1 = true;
			isTouchKusa2 = false;
			isTouchGimmic = true;
		} else if (pModifier.equals(sqKaminarikumo)) {
			sqKaminarikumohome();
		} else if (pModifier.equals(sqKaminarikumohome)) {
			isTouchOnigashima = true;
		}
	}

	private void innital() {
		isTouchFutari = true;
		isTouchMomo = true;
		isTouchAhuro = true;
		isTouchAkaoni = true;
		isTouchOjisan = true;
		isTouchUmibouzu = true;
		isTouchMomotaro = true;
		isTouchKiji = true;
		isTouchInu = true;
		isTouchSaru = true;
		isTouchKingyo = true;
		isTouchKusa1 = true;
		isTouchKusa2 = false;
		isTouchGimmic = true;
		isTouchOnigashima = true;

		if (sKemuri1 != null) {
			sKemuri1.setVisible(false);
		}
		if (sKemuri2 != null) {
			sKemuri2.setVisible(false);
		}
		if (sKemuri3 != null) {
			sKemuri3.setVisible(false);
		}
		if (sKemuri4 != null) {
			sKemuri4.setVisible(false);
		}
		if (sKemuri5 != null) {
			sKemuri5.setVisible(false);
		}

	}

	@Override
	protected void loadKaraokeResources() {
		Vol3MomotaroPacker_1 = mTexturePackLoaderFromSource
				.load("Vol3MomotaroPacker1.xml");
		Vol3MomotaroPacker_1.loadTexture();
		Vol3MomotaroLibrary1 = Vol3MomotaroPacker_1
				.getTexturePackTextureRegionLibrary();

		Vol3MomotaroPacker_2 = mTexturePackLoaderFromSource
				.load("Vol3MomotaroPacker2.xml");
		Vol3MomotaroPacker_2.loadTexture();
		Vol3MomotaroLibrary2 = Vol3MomotaroPacker_2
				.getTexturePackTextureRegionLibrary();

		ttrBackground = Vol3MomotaroLibrary2
				.get(Vol3MomotaroResource.Vol3MomotaroPacker2.A21_19_IPHONE_HAIKEI_ID);

		ttrOnigashima = getTiledTextureFromPacker(
				Vol3MomotaroPacker_2,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_14_2_IPHONE_ONIGASHIMA_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_14_3_IPHONE_ONIGASHIMA_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_14_4_IPHONE_ONIGASHIMA_ID);

		ttrNami1 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_18_1_IPHONE_NAMI_ID);

		ttrNami2 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_18_2_IPHONE_NAMI_ID);

		ttrNami3 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_18_3_IPHONE_NAMI_ID);

		ttrMomotaro = getTiledTextureFromPacker(
				Vol3MomotaroPacker_2,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_08_1_IPHONE_MOMOTARO_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_08_2_IPHONE_MOMOTARO_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker2.A21_08_3_IPHONE_MOMOTARO_ID);

		ttrFutari = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_09_1_IPHONE_FUTARI_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_09_2_IPHONE_FUTARI_ID);

		ttrMomo = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_16_IPHONE_MOMO_ID);

		ttrAhuro = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_10_1_IPHONE_AHURO_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_10_2_IPHONE_AHURO_ID);

		ttrAkaoni = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_13_1_IPHONE_AKAONI_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_13_2_IPHONE_AKAONI_ID);

		ttrOjisan = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_12_1_IPHONE_OJISAN_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_12_2_IPHONE_OJISAN_ID);

		ttrUmibouzu = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_11_1_IPHONE_UMIBOUZU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_11_2_IPHONE_UMIBOUZU_ID);

		ttrInu = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_07_1_IPHONE_INU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_07_2_IPHONE_INU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_07_3_IPHONE_INU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_07_4_IPHONE_INU_ID);

		ttrMomotarohear = Vol3MomotaroLibrary2
				.get(Vol3MomotaroResource.Vol3MomotaroPacker2.A21_08_4_IPHONE_MOMOTARO_ID);

		ttrKumo1 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_15_1_IPHONE_KUMO_ID);

		ttrKumo2 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_15_2_IPHONE_KUMO_ID);

		ttrKumo3 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_15_3_IPHONE_KUMO_ID);

		ttrKumo4 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_15_4_IPHONE_KUMO_ID);

		ttrKumo5 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_15_5_IPHONE_KUMO_ID);

		ttrKemuri1 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_5_IPHONE_KEMURI_ID);

		ttrKemuri2 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_6_IPHONE_KEMURI_ID);

		ttrKemuri3 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_7_IPHONE_KEMURI_ID);

		ttrKemuri4 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_8_IPHONE_KEMURI_ID);

		ttrKemuri5 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_9_IPHONE_KEMURI_ID);

		ttKumo = new BitmapTextureAtlas(this.getTextureManager(), 2, 2,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrKumo = new TextureRegion(ttKumo, 0, 0, 0, 0);
		this.mEngine.getTextureManager().loadTexture(ttKumo);

		ttrKiji1 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_05_1_IPHONE_KIJI_ID);

		ttrKiji2 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_05_2_IPHONE_KIJI_ID);

		ttrKingyo = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_17_IPHONE_KINGYO_ID);

		ttrSaru = getTiledTextureFromPacker(
				Vol3MomotaroPacker_1,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_06_1_IPHONE_SARU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_06_2_IPHONE_SARU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_06_3_IPHONE_SARU_ID,
				Vol3MomotaroResource.Vol3MomotaroPacker1.A21_06_4_IPHONE_SARU_ID);

		ttrKusa1 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_04_1_IPHONE_KUSA_ID);

		ttrKusa2 = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_04_2_IPHONE_KUSA_ID);

		ttrKaminarikumo = Vol3MomotaroLibrary1
				.get(Vol3MomotaroResource.Vol3MomotaroPacker1.A21_14_1_IPHONE_KAMINARIKUMO_ID);

	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();

		sOnigashima = new AnimatedSprite(0, 0, ttrOnigashima,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOnigashima);

		sNami1 = new Sprite(48, 274, ttrNami1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sNami1);

		sNami2 = new Sprite(194, 305, ttrNami2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sNami2);

		sNami3 = new Sprite(126, 349, ttrNami3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sNami3);

		sOjisan = new AnimatedSprite(419, 79, ttrOjisan,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sOjisan);

		sKumo1 = new Sprite(19, 43, ttrKumo1,
				this.getVertexBufferObjectManager());
		sKumo2 = new Sprite(133, -35, ttrKumo2,
				this.getVertexBufferObjectManager());
		sKumo3 = new Sprite(408, 8, ttrKumo3,
				this.getVertexBufferObjectManager());
		sKumo4 = new Sprite(517, -42, ttrKumo4,
				this.getVertexBufferObjectManager());
		sKumo5 = new Sprite(690, -21, ttrKumo5,
				this.getVertexBufferObjectManager());

		sKumoall1 = new Sprite(0, 0, 960, 640, ttrKumo,
				this.getVertexBufferObjectManager());
		sKumoall1.setAlpha(0.0f);
		sKumoall1.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		sKumoall1.attachChild(sKumo1);
		sKumoall1.attachChild(sKumo2);
		sKumoall1.attachChild(sKumo3);
		sKumoall1.attachChild(sKumo4);
		sKumoall1.attachChild(sKumo5);

		mScene.attachChild(sKumoall1);
		// ========================================================//
		sKumoall2 = new Sprite(1110, 0, 960, 640, ttrKumo,
				this.getVertexBufferObjectManager());
		sKumoall2.setAlpha(0.0f);
		sKumoall2.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		sKumo11 = new Sprite(19, 43, ttrKumo1,
				this.getVertexBufferObjectManager());
		sKumo22 = new Sprite(133, -35, ttrKumo2,
				this.getVertexBufferObjectManager());
		sKumo33 = new Sprite(408, 8, ttrKumo3,
				this.getVertexBufferObjectManager());
		sKumo44 = new Sprite(517, -42, ttrKumo4,
				this.getVertexBufferObjectManager());
		sKumo55 = new Sprite(690, -21, ttrKumo5,
				this.getVertexBufferObjectManager());
		sKumoall2.attachChild(sKumo11);
		sKumoall2.attachChild(sKumo22);
		sKumoall2.attachChild(sKumo33);
		sKumoall2.attachChild(sKumo44);
		sKumoall2.attachChild(sKumo55);
		mScene.attachChild(sKumoall2);

		sKaminarikumo = new Sprite(73, -208, ttrKaminarikumo,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKaminarikumo);

		sKemuri1 = new Sprite(310, 80, ttrKemuri1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKemuri1);
		sKemuri1.setVisible(false);

		sKemuri2 = new Sprite(310, 80, ttrKemuri2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKemuri2);
		sKemuri2.setVisible(false);

		sKemuri3 = new Sprite(310, 80, ttrKemuri3,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKemuri3);
		sKemuri3.setVisible(false);

		sKemuri4 = new Sprite(310, 80, ttrKemuri4,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKemuri4);
		sKemuri4.setVisible(false);

		sKemuri5 = new Sprite(310, 80, ttrKemuri5,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKemuri5);
		sKemuri5.setVisible(false);

		sFutari = new AnimatedSprite(545, 129, ttrFutari,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sFutari);

		sAhuro = new AnimatedSprite(312, 184, ttrAhuro,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sAhuro);

		sAkaoni = new AnimatedSprite(66, 114, ttrAkaoni,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sAkaoni);

		sUmibouzu = new AnimatedSprite(172, 166, ttrUmibouzu,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sUmibouzu);

		sMomo = new Sprite(645, 169, ttrMomo,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMomo);

		sKiji1 = new Sprite(678, -19, ttrKiji1,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKiji1);

		sKiji2 = new Sprite(478, 141, ttrKiji2,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKiji2);
		sKiji2.setVisible(false);

		sKingyo = new Sprite(53, 345, ttrKingyo,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sKingyo);

		sSaru = new AnimatedSprite(35, -60, ttrSaru,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSaru);

		sSaru_Virtual = new AnimatedSprite(35, -60, ttrSaru,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sSaru_Virtual);
		sSaru_Virtual.setVisible(false);

		sMomotaro = new AnimatedSprite(297, 109, ttrMomotaro,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMomotaro);

		sMomotarohear = new Sprite(517, 216, ttrMomotarohear,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sMomotarohear);
		sMomotarohear.setVisible(false);

		sInu = new AnimatedSprite(459, 246, ttrInu,
				this.getVertexBufferObjectManager());
		mScene.attachChild(sInu);

		sKusa2 = new Sprite(0, 490, ttrKusa2,
				this.getVertexBufferObjectManager());
		sKusa2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sKusa2);
		sKusa2.setVisible(false);

		sKusa1 = new Sprite(0, 540, ttrKusa1,
				this.getVertexBufferObjectManager());
		sKusa1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sKusa1);

		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
		addGimmicsButton(mScene, Vol3MomotaroResource.SOUND_GIMMIC,
				Vol3MomotaroResource.IMAGE_GIMMIC, Vol3MomotaroLibrary1);

	}

	@Override
	protected void loadKaraokeSound() {
		mp3Kusa = loadSoundResourceFromSD(Vol3MomotaroResource.A21_03_04_GAO2);
		mp3Kiji = loadSoundResourceFromSD(Vol3MomotaroResource.A21_05_KIJI);
		mp3Saru1 = loadSoundResourceFromSD(Vol3MomotaroResource.A21_06_HOI);
		mp3Saru2 = loadSoundResourceFromSD(Vol3MomotaroResource.A21_06_SARU);
		mp3Inu = loadSoundResourceFromSD(Vol3MomotaroResource.A21_07_INUA);
		mp3Momotaro = loadSoundResourceFromSD(Vol3MomotaroResource.A21_08_IKUZO);
		mp3Futari = loadSoundResourceFromSD(Vol3MomotaroResource.A21_09_GANBARE);
		mp3Ahuro_Umibouzu_Akaoni = loadSoundResourceFromSD(Vol3MomotaroResource.A21_10_11_13_BOKOZABA);
		mp3Ojisan = loadSoundResourceFromSD(Vol3MomotaroResource.A21_12_SUPON);
		mp3Kaminarikumo = loadSoundResourceFromSD(Vol3MomotaroResource.A21_14_GORO);
		mp3Onigashima = loadSoundResourceFromSD(Vol3MomotaroResource.A21_14_GAO2);
		mp3Momo = loadSoundResourceFromSD(Vol3MomotaroResource.A21_16_POWA4);
		mp3Kingyo = loadSoundResourceFromSD(Vol3MomotaroResource.A21_17_HYU_POTYAN);
	}

	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}

	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	}

	@Override
	public synchronized void onResumeGame() {
		innital();
		momotaroClapandChangeResource();
		InuClapandChangeResource();
		// ////////////////////
		RunCloud = new IUpdateHandler() {
			final float step = -2;

			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				float pFirst = sKumoall1.getX();
				sKumoall1.setPosition(pFirst += step, 0);
				float pSeconds = sKumoall2.getX();
				sKumoall2.setPosition(pSeconds += step, 0);
				if (sKumoall1.getX() == 0) {
					sKumoall2.setPosition(1110, 0);
				} else if (sKumoall2.getX() == 0) {
					sKumoall1.setPosition(1110, 0);
				}
			}
		};
		mScene.registerUpdateHandler(RunCloud);
		// ///////////////////////
		sqKijiModifier();
		saruRotation();
		sKemuri1.registerEntityModifier(new DelayModifier(3.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sqKemuri1Modifier();
					}
				}));
		sKemuri2.registerEntityModifier(new DelayModifier(7.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sqKemuri2Modifier();
					}
				}));
		sKemuri3.registerEntityModifier(new DelayModifier(10.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sqKemuri3Modifier();
					}
				}));
		sKemuri4.registerEntityModifier(new DelayModifier(14.0f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sqKemuri4Modifier();
					}
				}));
		sKemuri5.registerEntityModifier(new DelayModifier(17.5f,
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						sqKemuri5Modifier();
					}
				}));
		super.onResumeGame();
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		if (pAnimatedSprite == sFutari) {
			sFutari.setCurrentTileIndex(0);
			sFutari.registerEntityModifier(new DelayModifier(0.6f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTouchFutari = true;
						}
					}));
		} else if (pAnimatedSprite == sAhuro) {
			isTouchAhuro = true;
			sAhuro.setCurrentTileIndex(0);
		} else if (pAnimatedSprite == sAkaoni) {
			isTouchAkaoni = true;
			sAkaoni.setCurrentTileIndex(0);
		} else if (pAnimatedSprite == sOjisan) {
			isTouchOjisan = true;
			sOjisan.setCurrentTileIndex(0);
		} else if (pAnimatedSprite == sUmibouzu) {
			isTouchUmibouzu = true;
			sUmibouzu.setCurrentTileIndex(0);
		} else if (pAnimatedSprite == sMomotaro) {

			sMomotaro.setCurrentTileIndex(0);
			sMomotarohear.setVisible(false);
			sMomotaro.registerEntityModifier(new DelayModifier(0.4f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTouchMomotaro = true;
							isTouchKiji = true;
							isTouchSaru = true;
							isTouchInu = true;

							momotaroClapandChangeResource();
						}
					}));
		} else if (pAnimatedSprite == sInu) {
			sMomotaro.setVisible(true);
			sInu.setCurrentTileIndex(0);
			sInu.registerEntityModifier(new DelayModifier(0.4f,
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}

						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
							isTouchKiji = true;
							isTouchSaru = true;
							isTouchInu = true;
							isTouchMomotaro = true;
							InuClapandChangeResource();
							momotaroClapandChangeResource();
						}
					}));
		} else if (pAnimatedSprite == sSaru_Virtual) {
			isTouchSaru = true;
			isTouchKiji = true;
			isTouchInu = true;
			isTouchMomotaro = true;

			sMomotaro.setVisible(true);
			sSaru.setVisible(true);
			sSaru_Virtual.setVisible(false);
			sSaru_Virtual.setCurrentTileIndex(0);
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
