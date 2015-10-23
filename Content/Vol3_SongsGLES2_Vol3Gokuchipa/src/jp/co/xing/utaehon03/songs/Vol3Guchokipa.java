package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3GuchokipaResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.R.style;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;

public class Vol3Guchokipa extends BaseGameActivity implements IOnSceneTouchListener {
	private static final String TAG = "LOG_GUCHOKIPA";

	private int p__x = 0, p__y = 0, current = 1, case1 = 0, case2 = 0, case4 = 0, case5 = 0, case6 = 0;

	private boolean isTouch = true, IsOTT = false, checkgm = true, checkmg = true, isgimmic = true;

	private boolean isTouchStart, isTouchSecond, isCheckOtt;

	private boolean finishNeko, finishKani, finishUsagi, finishNail, finishPasta, finishKuma, finishJisan, finishKurage, finishEgg, finishHeri, finishOhana, finishHanabi, finishCyou;

	private TexturePack ttpGuchokipa1, ttpGuchokipa2, ttpGuchokipa3, ttpGuchokipa4, ttpGuchokipa5, ttpGuchokipa6, ttpGuchokipa7, ttpGuchokipa8, ttpGuchokipa9, ttpGuchokipa10, ttpGuchokipa11, ttpGuchokipa12, ttpGuchokipa13, ttpGuchokipa14,
			ttpGuchokipa15, ttpGuchokipa16, ttpGuchokipa17, ttpGuchokipa18, ttpGuchokipa19, ttpGuchokipa20, ttpGuchokipa21, ttpGuchokipa22, ttpGuchokipa23, ttpGuchokipa24, ttpGuchokipa25, ttpGuchokipa26, ttpGuchokipa27, ttpGuchokipa28,
			ttpGuchokipa29, ttpGuchokipa30, ttpGuchokipa31;

	private TexturePackTextureRegionLibrary ttpLibGuchokipa26, ttpLibGuchokipa27, ttpLibGuchokipa30, ttpLibGuchokipa31;

	private AnimatedSprite animOnePiero, animSmoke, animOnePieroSize, animhoneneko4112, animhoneneko4134, animhonekani4212, animhonekani4234, anim4and51123, anim4and5145, anim4and5212, anim4and5265, anim4and5243, anim4and6112, anim4and61345,
			animhonekuma12, animhonekuma34, animhonejisan12, animhonejisan34, anim5and6112, anim5and6134, anim5and6212, anim5and63123, anim5and63456, animhonehana1123, animhonehana145, animhonehana212, animhonehana2345, animhonecyou112,
			animhonecyou1345;

	private TiledTextureRegion tttrOnePiero, tttrSmoke, tttrOnePieroSize, tttrhoneneko4112, tttrhoneneko4134, tttrhonekani4212, tttrhonekani4234, tttr4and51123, tttr4and5145, tttr4and5212, tttr4and5265, tttr4and5243, tttr4and6112, tttr4and61345,
			tttrhonekuma12, tttrhonekuma34, tttrhonejisan12, tttrhonejisan34, tttr5and6112, tttr5and6134, tttr5and6212, tttr5and63123, tttr5and63456, tttrhonehana1123, tttrhonehana145, tttrhonehana212, tttrhonehana2345, tttrhonecyou112,
			tttrhonecyou1345;

	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;

	private TextureRegion ttrBackground, ttrOne, ttrTwo, ttrThree, ttrOnePiOne, ttrTwoPiOne, ttrThreePiOne, ttr4and527, ttr5and623, ttr5and624, ttr5and625;

	private Sprite sOne, sTwo, sThree, sOnePiOne, sTwoPiOne, sThreePiOne, sONE1, sONE2, sTWO1, sTWO2, sTHREE1, sTHREE2, s4and527, s5and623, s5and624, s5and625;

	private Sound mp3chyu7, mp3katatsumuri, mp3kui, mp3nyu, mp3usagi2, mp3supa3, mp3gu, mp3kani2, mp3kasa, mp3neko, mp3neko_voice, mp3bomi, mp3cyoki, mp3kobujisan, mp3kuma, mp3ohana, mp3pa, mp3arere, mp3boyon, mp3oyaoya, mp3chou, mp3hanabi,
			mp3kemuri, mp3kurage2, mp3awaboko, mp3heri, mp3heri_voice, mp3kira5, mp3medamayaki, mp3medamayaki3, mp3pachidone, mp3syararan, mp3syusyu;

	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "loadKaraokeResources : ");
		// Lib
		ttpGuchokipa1 = mTexturePackLoaderFromSource.load("guchokipa1.xml");
		ttpGuchokipa1.loadTexture();

		ttpGuchokipa2 = mTexturePackLoaderFromSource.load("guchokipa2.xml");
		ttpGuchokipa2.loadTexture();

		ttpGuchokipa3 = mTexturePackLoaderFromSource.load("guchokipa3.xml");
		ttpGuchokipa3.loadTexture();

		ttpGuchokipa4 = mTexturePackLoaderFromSource.load("guchokipa4.xml");
		ttpGuchokipa4.loadTexture();

		ttpGuchokipa5 = mTexturePackLoaderFromSource.load("guchokipa5.xml");
		ttpGuchokipa5.loadTexture();

		ttpGuchokipa6 = mTexturePackLoaderFromSource.load("guchokipa6.xml");
		ttpGuchokipa6.loadTexture();

		ttpGuchokipa7 = mTexturePackLoaderFromSource.load("guchokipa7.xml");
		ttpGuchokipa7.loadTexture();

		ttpGuchokipa8 = mTexturePackLoaderFromSource.load("guchokipa8.xml");
		ttpGuchokipa8.loadTexture();

		ttpGuchokipa9 = mTexturePackLoaderFromSource.load("guchokipa9.xml");
		ttpGuchokipa9.loadTexture();

		ttpGuchokipa10 = mTexturePackLoaderFromSource.load("guchokipa10.xml");
		ttpGuchokipa10.loadTexture();

		ttpGuchokipa11 = mTexturePackLoaderFromSource.load("guchokipa11.xml");
		ttpGuchokipa11.loadTexture();

		ttpGuchokipa12 = mTexturePackLoaderFromSource.load("guchokipa12.xml");
		ttpGuchokipa12.loadTexture();

		ttpGuchokipa13 = mTexturePackLoaderFromSource.load("guchokipa13.xml");
		ttpGuchokipa13.loadTexture();

		ttpGuchokipa14 = mTexturePackLoaderFromSource.load("guchokipa14.xml");
		ttpGuchokipa14.loadTexture();

		ttpGuchokipa15 = mTexturePackLoaderFromSource.load("guchokipa15.xml");
		ttpGuchokipa15.loadTexture();

		ttpGuchokipa16 = mTexturePackLoaderFromSource.load("guchokipa16.xml");
		ttpGuchokipa16.loadTexture();

		ttpGuchokipa17 = mTexturePackLoaderFromSource.load("guchokipa17.xml");
		ttpGuchokipa17.loadTexture();

		ttpGuchokipa18 = mTexturePackLoaderFromSource.load("guchokipa18.xml");
		ttpGuchokipa18.loadTexture();

		ttpGuchokipa19 = mTexturePackLoaderFromSource.load("guchokipa19.xml");
		ttpGuchokipa19.loadTexture();

		ttpGuchokipa20 = mTexturePackLoaderFromSource.load("guchokipa20.xml");
		ttpGuchokipa20.loadTexture();

		ttpGuchokipa21 = mTexturePackLoaderFromSource.load("guchokipa21.xml");
		ttpGuchokipa21.loadTexture();

		ttpGuchokipa22 = mTexturePackLoaderFromSource.load("guchokipa22.xml");
		ttpGuchokipa22.loadTexture();

		ttpGuchokipa23 = mTexturePackLoaderFromSource.load("guchokipa23.xml");
		ttpGuchokipa23.loadTexture();

		ttpGuchokipa24 = mTexturePackLoaderFromSource.load("guchokipa24.xml");
		ttpGuchokipa24.loadTexture();

		ttpGuchokipa25 = mTexturePackLoaderFromSource.load("guchokipa25.xml");
		ttpGuchokipa25.loadTexture();

		ttpGuchokipa28 = mTexturePackLoaderFromSource.load("guchokipa28.xml");
		ttpGuchokipa28.loadTexture();

		ttpGuchokipa31 = mTexturePackLoaderFromSource.load("guchokipa31.xml");
		ttpGuchokipa31.loadTexture();
		ttpLibGuchokipa31 = ttpGuchokipa31.getTexturePackTextureRegionLibrary();

		// Background
		ttpGuchokipa30 = mTexturePackLoaderFromSource.load("guchokipa30.xml");
		ttpGuchokipa30.loadTexture();
		ttpLibGuchokipa30 = ttpGuchokipa30.getTexturePackTextureRegionLibrary();

		ttpGuchokipa26 = mTexturePackLoaderFromSource.load("guchokipa26.xml");
		ttpGuchokipa26.loadTexture();
		ttpLibGuchokipa26 = ttpGuchokipa26.getTexturePackTextureRegionLibrary();

		ttpGuchokipa27 = mTexturePackLoaderFromSource.load("guchokipa27.xml");
		ttpGuchokipa27.loadTexture();
		ttpLibGuchokipa27 = ttpGuchokipa27.getTexturePackTextureRegionLibrary();

		ttpGuchokipa29 = mTexturePackLoaderFromSource.load("guchokipa29.xml");
		ttpGuchokipa29.loadTexture();

		// Texture
		// Background
		this.ttrBackground = ttpLibGuchokipa30.get(Vol3GuchokipaResource.A1_9_IPHONE_HAIKEI_ID);

		// OnePiero
		this.tttrOnePiero = getTiledTextureFromPacker(ttpGuchokipa26, Vol3GuchokipaResource.A1_7_4_IPHONE_PIERO_ID, Vol3GuchokipaResource.A1_7_5_IPHONE_PIERO_ID);
		this.ttrOnePiOne = ttpLibGuchokipa26.get(Vol3GuchokipaResource.A1_7_2_IPHONE_PIERO_ID);

		this.ttrTwoPiOne = ttpLibGuchokipa26.get(Vol3GuchokipaResource.A1_7_1_IPHONE_PIERO_ID);

		this.ttrThreePiOne = ttpLibGuchokipa26.get(Vol3GuchokipaResource.A1_7_3_IPHONE_PIERO_ID);

		Log.d(TAG, "loadKaraokeResources: end");

		// One - Two - Three
		this.ttrOne = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_4_IPHONE_K_ID);
		this.ttrTwo = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_5_IPHONE_G_ID);
		this.ttrThree = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_6_IPHONE_P_ID);

		// OnePiero Size
		this.tttrOnePieroSize = getTiledTextureFromPacker(ttpGuchokipa28, Vol3GuchokipaResource.A1_7_6_IPHONE_PIERO_ID, Vol3GuchokipaResource.A1_7_7_IPHONE_PIERO_ID);

		// Smoke
		this.tttrSmoke = getTiledTextureFromPacker(ttpGuchokipa29, Vol3GuchokipaResource.A1_8_1_IPHONE_KEMURI_ID, Vol3GuchokipaResource.A1_8_2_IPHONE_KEMURI_ID, Vol3GuchokipaResource.A1_8_3_IPHONE_KEMURI_ID);

		// Honeneko4112
		this.tttrhoneneko4112 = getTiledTextureFromPacker(ttpGuchokipa1, Vol3GuchokipaResource.A1_4_1_1_IPHONE_NEKO_ID, Vol3GuchokipaResource.A1_4_1_2_IPHONE_NEKO_ID);

		// Honeneko4134
		this.tttrhoneneko4134 = getTiledTextureFromPacker(ttpGuchokipa2, Vol3GuchokipaResource.A1_4_1_3_IPHONE_NEKO_ID, Vol3GuchokipaResource.A1_4_1_4_IPHONE_NEKO_ID);

		// Honekani 4212
		this.tttrhonekani4212 = getTiledTextureFromPacker(ttpGuchokipa3, Vol3GuchokipaResource.A1_4_2_1_IPHONE_KANI_ID, Vol3GuchokipaResource.A1_4_2_2_IPHONE_KANI_ID);

		// Honekani 4234
		this.tttrhonekani4234 = getTiledTextureFromPacker(ttpGuchokipa4, Vol3GuchokipaResource.A1_4_2_3_IPHONE_KANI_ID, Vol3GuchokipaResource.A1_4_2_4_IPHONE_KANI_ID);

		// 4and51123
		this.tttr4and51123 = getTiledTextureFromPacker(ttpGuchokipa5, Vol3GuchokipaResource.A1_4AND5_1_1_IPHONE_USAGI_ID, Vol3GuchokipaResource.A1_4AND5_1_2_IPHONE_USAGI_ID, Vol3GuchokipaResource.A1_4AND5_1_3_IPHONE_USAGI_ID);

		// 4and5145
		this.tttr4and5145 = getTiledTextureFromPacker(ttpGuchokipa6, Vol3GuchokipaResource.A1_4AND5_1_4_IPHONE_USAGI_ID, Vol3GuchokipaResource.A1_4AND5_1_5_IPHONE_USAGI_ID);

		// 4and5212
		this.tttr4and5212 = getTiledTextureFromPacker(ttpGuchokipa7, Vol3GuchokipaResource.A1_4AND5_2_1_IPHONE_SNAIL_ID, Vol3GuchokipaResource.A1_4AND5_2_2_IPHONE_SNAIL_ID);

		// 4and5265
		this.tttr4and5265 = getTiledTextureFromPacker(ttpGuchokipa9, Vol3GuchokipaResource.A1_4AND5_2_6_IPHONE_SNAIL_ID, Vol3GuchokipaResource.A1_4AND5_2_5_IPHONE_SNAIL_ID);

		// 4and5243
		this.tttr4and5243 = getTiledTextureFromPacker(ttpGuchokipa8, Vol3GuchokipaResource.A1_4AND5_2_4_IPHONE_SNAIL_ID, Vol3GuchokipaResource.A1_4AND5_2_3_IPHONE_SNAIL_ID);

		// 4and5227
		this.ttr4and527 = ttpLibGuchokipa31.get(Vol3GuchokipaResource.A1_4AND5_2_7_IPHONE_SNAIL_ID);

		// 4and6112
		this.tttr4and6112 = getTiledTextureFromPacker(ttpGuchokipa10, Vol3GuchokipaResource.A1_4AND6_1_1_IPHONE_PASTA_ID, Vol3GuchokipaResource.A1_4AND6_1_2_IPHONE_PASTA_ID);

		// 4and61345
		this.tttr4and61345 = getTiledTextureFromPacker(ttpGuchokipa11, Vol3GuchokipaResource.A1_4AND6_1_3_IPHONE_PASTA_ID, Vol3GuchokipaResource.A1_4AND6_1_4_IPHONE_PASTA_ID, Vol3GuchokipaResource.A1_4AND6_1_5_IPHONE_PASTA_ID);

		// Honakuma12
		this.tttrhonekuma12 = getTiledTextureFromPacker(ttpGuchokipa12, Vol3GuchokipaResource.A1_5_1_1_IPHONE_KUMA_ID, Vol3GuchokipaResource.A1_5_1_2_IPHONE_KUMA_ID);

		// Honekuma34
		this.tttrhonekuma34 = getTiledTextureFromPacker(ttpGuchokipa13, Vol3GuchokipaResource.A1_5_1_3_IPHONE_KUMA_ID, Vol3GuchokipaResource.A1_5_1_4_IPHONE_KUMA_ID);

		// Honejisan12
		this.tttrhonejisan12 = getTiledTextureFromPacker(ttpGuchokipa14, Vol3GuchokipaResource.A1_5_2_1_IPHONE_JISAN_ID, Vol3GuchokipaResource.A1_5_2_2_IPHONE_JISAN_ID);

		// Honejisan34
		this.tttrhonejisan34 = getTiledTextureFromPacker(ttpGuchokipa15, Vol3GuchokipaResource.A1_5_2_3_IPHONE_JISAN_ID, Vol3GuchokipaResource.A1_5_2_4_IPHONE_JISAN_ID);

		// 5and6112
		this.tttr5and6112 = getTiledTextureFromPacker(ttpGuchokipa16, Vol3GuchokipaResource.A1_5AND6_1_1_IPHONE_KURAGE_ID, Vol3GuchokipaResource.A1_5AND6_1_2_IPHONE_KURAGE_ID);

		// 5and6134
		this.tttr5and6134 = getTiledTextureFromPacker(ttpGuchokipa17, Vol3GuchokipaResource.A1_5AND6_1_3_IPHONE_KURAGE_ID, Vol3GuchokipaResource.A1_5AND6_1_4_IPHONE_KURAGE_ID);

		// 5and6212
		this.tttr5and6212 = getTiledTextureFromPacker(ttpGuchokipa18, Vol3GuchokipaResource.A1_5AND6_2_1_IPHONE_MEDAMA_ID, Vol3GuchokipaResource.A1_5AND6_2_2_IPHONE_MEDAMA_ID);

		// 5and623
		this.ttr5and623 = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_5AND6_2_3_IPHONE_MEDAMA_ID);

		// 5and624
		this.ttr5and624 = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_5AND6_2_4_IPHONE_MEDAMA_ID);

		// 5and625
		this.ttr5and625 = ttpLibGuchokipa27.get(Vol3GuchokipaResource.A1_5AND6_2_5_IPHONE_MEDAMA_ID);

		// 5and63123
		this.tttr5and63123 = getTiledTextureFromPacker(ttpGuchokipa19, Vol3GuchokipaResource.A1_5AND6_3_1_IPHONE_HERI_ID, Vol3GuchokipaResource.A1_5AND6_3_2_IPHONE_HERI_ID, Vol3GuchokipaResource.A1_5AND6_3_3_IPHONE_HERI_ID);

		// 5and63456
		this.tttr5and63456 = getTiledTextureFromPacker(ttpGuchokipa20, Vol3GuchokipaResource.A1_5AND6_3_4_IPHONE_HERI_ID, Vol3GuchokipaResource.A1_5AND6_3_5_IPHONE_HERI_ID, Vol3GuchokipaResource.A1_5AND6_3_6_IPHONE_HERI_ID);

		// honahana1123
		this.tttrhonehana1123 = getTiledTextureFromPacker(ttpGuchokipa21, Vol3GuchokipaResource.A1_6_1_1_IPHONE_HANA_ID, Vol3GuchokipaResource.A1_6_1_2_IPHONE_HANA_ID, Vol3GuchokipaResource.A1_6_1_3_IPHONE_HANA_ID);

		// honehana145
		this.tttrhonehana145 = getTiledTextureFromPacker(ttpGuchokipa22, Vol3GuchokipaResource.A1_6_1_4_IPHONE_HANA_ID, Vol3GuchokipaResource.A1_6_1_5_IPHONE_HANA_ID);

		// honehana212
		this.tttrhonehana212 = getTiledTextureFromPacker(ttpGuchokipa31, Vol3GuchokipaResource.A1_6_2_1_IPHONE_HANABI_ID, Vol3GuchokipaResource.A1_6_2_2_IPHONE_HANABI_ID);

		// honehana2345
		this.tttrhonehana2345 = getTiledTextureFromPacker(ttpGuchokipa23, Vol3GuchokipaResource.A1_6_2_3_IPHONE_HANABI_ID, Vol3GuchokipaResource.A1_6_2_4_IPHONE_HANABI_ID, Vol3GuchokipaResource.A1_6_2_5_IPHONE_HANABI_ID);

		// honecyou112
		this.tttrhonecyou112 = getTiledTextureFromPacker(ttpGuchokipa24, Vol3GuchokipaResource.A1_6_3_1_IPHONE_CYOU_ID, Vol3GuchokipaResource.A1_6_3_2_IPHONE_CYOU_ID);

		// honecyou1345
		this.tttrhonecyou1345 = getTiledTextureFromPacker(ttpGuchokipa25, Vol3GuchokipaResource.A1_6_3_3_IPHONE_CYOU_ID, Vol3GuchokipaResource.A1_6_3_4_IPHONE_CYOU_ID, Vol3GuchokipaResource.A1_6_3_5_IPHONE_CYOU_ID);

	}

	@Override
	protected void loadKaraokeSound() {
		mp3chyu7 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_5_chyu7);
		mp3katatsumuri = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_5_katatsumuri);
		mp3kui = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_5_kui);
		mp3nyu = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_5_nyu);
		mp3usagi2 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_5_usagi2);
		mp3supa3 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_6_supa3);
		mp3gu = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_gu);
		mp3kani2 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_kani2);

		mp3kasa = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_kasa);
		mp3neko = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_neko);
		mp3neko_voice = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_4_neko_voice);
		mp3bomi = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_5_bomi);
		mp3cyoki = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_5_cyoki);
		mp3kobujisan = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_5_kobujisan);
		mp3kuma = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_5_kuma);
		mp3ohana = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_6_ohana);
		mp3pa = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_6_pa);
		mp3arere = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_7_arere);

		mp3boyon = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_7_boyon);
		mp3oyaoya = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_7_oyaoya);
		mp3chou = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_8_chou);
		mp3hanabi = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_8_hanabi);
		mp3kemuri = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_8_kemuri);
		mp3kurage2 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_8_kurage2);
		mp3awaboko = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_awaboko);
		mp3heri = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_heri);
		mp3heri_voice = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_heri_voice);
		mp3kira5 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_kira5);

		mp3medamayaki = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_medamayaki);
		mp3medamayaki3 = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_medamayaki3);
		mp3pachidone = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_pachidone);
		mp3syararan = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_syararan);
		mp3syusyu = loadSoundResourceFromSD(Vol3GuchokipaResource.A1_syusyu);

	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene: ");
		mScene = new Scene();

		// Background
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.ttrBackground, this.getVertexBufferObjectManager())));
		// One Piero
		this.animOnePiero = new AnimatedSprite(680, 36, tttrOnePiero, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animOnePiero);

		// One Two Three
		this.sOne = new Sprite(68, 41, ttrOne, this.getVertexBufferObjectManager());
		this.sTwo = new Sprite(66, 261, ttrTwo, this.getVertexBufferObjectManager());
		this.sThree = new Sprite(64, 408, ttrThree, this.getVertexBufferObjectManager());

		this.mScene.attachChild(sOne);
		this.mScene.attachChild(sTwo);
		this.mScene.attachChild(sThree);

		// // ONE1,2 - TWO1,2 - THREE1,2
		this.sONE1 = new Sprite(280, 196, ttrOne, this.getVertexBufferObjectManager());
		this.sONE2 = new Sprite(505, 196, ttrOne, this.getVertexBufferObjectManager());
		this.sTWO1 = new Sprite(270, 261, ttrTwo, this.getVertexBufferObjectManager());
		this.sTWO2 = new Sprite(500, 261, ttrTwo, this.getVertexBufferObjectManager());
		this.sTHREE1 = new Sprite(270, 224, ttrThree, this.getVertexBufferObjectManager());
		this.sTHREE2 = new Sprite(500, 224, ttrThree, this.getVertexBufferObjectManager());

		this.mScene.attachChild(sONE1);
		this.mScene.attachChild(sONE2);
		sONE1.setVisible(false);
		sONE2.setVisible(false);
		this.mScene.attachChild(sTWO1);
		this.mScene.attachChild(sTWO2);
		sTWO1.setVisible(false);
		sTWO2.setVisible(false);
		this.mScene.attachChild(sTHREE1);
		this.mScene.attachChild(sTHREE2);
		sTHREE1.setVisible(false);
		sTHREE2.setVisible(false);

		// sOne - sTwo - sThree
		this.sOnePiOne = new Sprite(680, 36, ttrOnePiOne, this.getVertexBufferObjectManager());
		this.sTwoPiOne = new Sprite(680, 36, ttrTwoPiOne, this.getVertexBufferObjectManager());
		this.sThreePiOne = new Sprite(680, 36, ttrThreePiOne, this.getVertexBufferObjectManager());

		this.mScene.attachChild(sOnePiOne);
		this.mScene.attachChild(sTwoPiOne);
		this.mScene.attachChild(sThreePiOne);

		sOnePiOne.setVisible(false);
		sTwoPiOne.setVisible(false);
		sThreePiOne.setVisible(false);

		// Smoke
		this.animSmoke = new AnimatedSprite(50, 10, tttrSmoke, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animSmoke);
		animSmoke.setVisible(false);

		// Honeneko12
		this.animhoneneko4112 = new AnimatedSprite(204, 19, tttrhoneneko4112, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhoneneko4112);
		animhoneneko4112.setVisible(false);

		// Honeneko34
		this.animhoneneko4134 = new AnimatedSprite(-700, -700, tttrhoneneko4134, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhoneneko4134);
		animhoneneko4134.setVisible(false);

		// Honekani4212
		this.animhonekani4212 = new AnimatedSprite(204, 19, tttrhonekani4212, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonekani4212);
		animhonekani4212.setVisible(false);

		// Honekani4234
		this.animhonekani4234 = new AnimatedSprite(-700, -700, tttrhonekani4234, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonekani4234);
		animhonekani4234.setVisible(false);

		// 4and51123
		this.anim4and51123 = new AnimatedSprite(204, 19, tttr4and51123, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and51123);
		anim4and51123.setVisible(false);

		// 4and5145
		this.anim4and5145 = new AnimatedSprite(-700, -700, tttr4and5145, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and5145);
		anim4and5145.setVisible(false);

		// 4and5212
		this.anim4and5212 = new AnimatedSprite(204, 19, tttr4and5212, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and5212);
		anim4and5212.setVisible(false);

		// 4and5227
		this.s4and527 = new Sprite(370, 123, ttr4and527, this.getVertexBufferObjectManager());
		this.mScene.attachChild(s4and527);
		s4and527.setVisible(false);

		// 4and5265
		this.anim4and5265 = new AnimatedSprite(204, 19, tttr4and5265, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and5265);
		anim4and5265.setVisible(false);

		// 4and5243
		this.anim4and5243 = new AnimatedSprite(-700, -700, tttr4and5243, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and5243);
		anim4and5243.setVisible(false);

		// 4and6112
		this.anim4and6112 = new AnimatedSprite(204, 19, tttr4and6112, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and6112);
		anim4and6112.setVisible(false);

		// 4and61345
		this.anim4and61345 = new AnimatedSprite(-700, -700, tttr4and61345, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim4and61345);
		anim4and61345.setVisible(false);

		// Honekuma12
		this.animhonekuma12 = new AnimatedSprite(204, 19, tttrhonekuma12, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonekuma12);
		animhonekuma12.setVisible(false);

		// Honekuma34
		this.animhonekuma34 = new AnimatedSprite(-700, -700, tttrhonekuma34, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonekuma34);
		animhonekuma34.setVisible(false);

		// Honejisan12
		this.animhonejisan12 = new AnimatedSprite(204, 19, tttrhonejisan12, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonejisan12);
		animhonejisan12.setVisible(false);

		// Honejisan34
		this.animhonejisan34 = new AnimatedSprite(-700, -700, tttrhonejisan34, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonejisan34);
		animhonejisan34.setVisible(false);

		// 5and6112
		this.anim5and6112 = new AnimatedSprite(204, 19, tttr5and6112, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim5and6112);
		anim5and6112.setVisible(false);

		// 5and6134

		this.anim5and6134 = new AnimatedSprite(-700, -700, tttr5and6134, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim5and6134);
		anim5and6134.setVisible(false);

		// 5and6212
		this.anim5and6212 = new AnimatedSprite(204, 19, tttr5and6212, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim5and6212);
		anim5and6212.setVisible(false);

		// 5and623
		this.s5and623 = new Sprite(240, 291, ttr5and623, this.getVertexBufferObjectManager());
		this.mScene.attachChild(s5and623);
		s5and623.setVisible(false);

		// 5and624
		this.s5and624 = new Sprite(215, 146, ttr5and624, this.getVertexBufferObjectManager());
		this.mScene.attachChild(s5and624);
		s5and624.setVisible(false);

		// 5and625
		this.s5and625 = new Sprite(204, 19, ttr5and625, this.getVertexBufferObjectManager());
		this.mScene.attachChild(s5and625);
		s5and625.setVisible(false);

		// 5and63123
		this.anim5and63123 = new AnimatedSprite(204, 19, tttr5and63123, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim5and63123);
		anim5and63123.setVisible(false);

		// 5and63456
		this.anim5and63456 = new AnimatedSprite(-700, -700, tttr5and63456, this.getVertexBufferObjectManager());
		this.mScene.attachChild(anim5and63456);
		anim5and63456.setVisible(false);

		// Honehana1123
		this.animhonehana1123 = new AnimatedSprite(204, 19, tttrhonehana1123, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonehana1123);
		animhonehana1123.setVisible(false);

		// honehana145
		this.animhonehana145 = new AnimatedSprite(-700, -700, tttrhonehana145, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonehana145);
		animhonehana145.setVisible(false);

		// honehana212
		this.animhonehana212 = new AnimatedSprite(204, 19, tttrhonehana212, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonehana212);
		animhonehana212.setVisible(false);

		// honehana2345
		this.animhonehana2345 = new AnimatedSprite(-700, -700, tttrhonehana2345, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonehana2345);
		animhonehana2345.setVisible(false);

		// honecyou112
		this.animhonecyou112 = new AnimatedSprite(204, 19, tttrhonecyou112, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonecyou112);
		animhonecyou112.setVisible(false);

		// honecyou1345
		this.animhonecyou1345 = new AnimatedSprite(-700, -700, tttrhonecyou1345, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animhonecyou1345);
		animhonecyou1345.setVisible(false);

		// OnePieroSize
		this.animOnePieroSize = new AnimatedSprite(246, 0, tttrOnePieroSize, this.getVertexBufferObjectManager());
		this.mScene.attachChild(animOnePieroSize);
		animOnePieroSize.setVisible(false);

		addGimmicsButton(mScene, Vol3GuchokipaResource.SOUND_GIMMIC_GUCHOKIPA, Vol3GuchokipaResource.IMAGE_GIMMIC_GUCHOKIPA, ttpLibGuchokipa27);

		mScene.setOnSceneTouchListener(this);
		Log.d(TAG, "loadKaraokeScene: end ");
	}

	@Override
	public void combineGimmic3WithAction() {
		Log.d(TAG, "Gimmic");
		Gimmic();
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResources : ");
		SoundFactory.setAssetBasePath("guchokipa/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("guchokipa/gfx/");

		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(), pAssetManager, "guchokipa/gfx/");
		super.onCreateResources();
		Log.d(TAG, "onCreateResources : end");
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// --------------------------------------------------------------
		// 20130326
		// -------------------------------------------------------------
		if (isPause()) {
			return false;
		}
		// --------------------------------------------------------------
		// 20130326 END
		// -------------------------------------------------------------

		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();

		if (pSceneTouchEvent.isActionDown()) {
			Log.d(TAG, "Touch Down : ");
			// ONE
			if (checkContains(sOne, 10, 10, 110, 170, pX, pY) && isCheckOtt && isTouch && isTouchStart) {
				Log.d(TAG, " x = 1 : ");
				isTouchStart = false;
				sONE1.setVisible(true);
				isTouchSecond = true;
				p__x = 1;
				IsOTT = true;
				isgimmic = false;
				checkmg = false;
				TouchsOne();
			}

			if (checkContains(sTwo, 10, 10, 125, 100, pX, pY) && isCheckOtt && isTouch && isTouchStart) {
				Log.d(TAG, " x = 2 : ");
				isTouchStart = false;
				sTWO1.setVisible(true);
				isTouchSecond = true;
				p__x = 2;
				IsOTT = true;
				isgimmic = false;
				checkmg = false;
				TouchsTwo();
			}

			if (checkContains(sThree, 10, 10, 130, 140, pX, pY) && isCheckOtt && isTouch && isTouchStart) {
				Log.d(TAG, " x = 3 : ");
				isTouchStart = false;
				sTHREE1.setVisible(true);
				isTouchSecond = true;
				p__x = 3;
				IsOTT = true;
				isgimmic = false;
				checkmg = false;
				TouchsThree();
			}

			// TWO
			if (checkContains(sOne, 10, 10, 110, 170, pX, pY) && !isCheckOtt && isTouch && isTouchSecond) {
				Log.d(TAG, " y = 1 : ");
				isTouchSecond = false;
				sONE2.setVisible(true);
				p__y = 1;
				checkmg = false;
				TouchsOne();
				sONE2.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						VisibleParent();
						isTouchStart = true;
						isCheckOtt = true;
						SwitchCase(checkOTT(p__x, p__y));
					}
				}));
			}

			if (checkContains(sTwo, 10, 10, 125, 100, pX, pY) && !isCheckOtt && isTouch && isTouchSecond) {
				Log.d(TAG, " y = 2 : ");
				isTouchSecond = false;
				sTWO2.setVisible(true);
				p__y = 2;
				checkmg = false;
				TouchsTwo();
				sTWO2.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						VisibleParent();
						isTouchStart = true;
						isCheckOtt = true;
						SwitchCase(checkOTT(p__x, p__y));
					}
				}));
			}

			if (checkContains(sThree, 10, 10, 130, 140, pX, pY) && !isCheckOtt && isTouch && isTouchSecond) {
				Log.d(TAG, " y = 3 : ");
				isTouchSecond = false;
				sTHREE2.setVisible(true);
				p__y = 3;
				checkmg = false;
				TouchsThree();
				sTHREE2.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						VisibleParent();
						isTouchStart = true;
						isCheckOtt = true;
						SwitchCase(checkOTT(p__x, p__y));
					}
				}));
			}

			if (animhoneneko4134.contains(pX, pY) && finishNeko) {
				Log.d(TAG, "neko");
				animhoneneko4134.stopAnimation();
				animhoneneko4134.clearEntityModifiers();
				animhoneneko4134.setPosition(-700, -700);
				animhoneneko4134.setVisible(false);
				isgimmic = true;
				finishNeko = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);

			}

			if (animhonekani4234.contains(pX, pY) && finishKani) {
				Log.d(TAG, "kani");
				animhonekani4234.stopAnimation();
				animhonekani4234.clearEntityModifiers();
				animhonekani4234.setPosition(-700, -700);
				animhonekani4234.setVisible(false);
				isgimmic = true;
				finishKani = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (anim4and5145.contains(pX, pY) && finishUsagi) {
				Log.d(TAG, "usagi");
				anim4and5145.stopAnimation();
				anim4and5145.clearEntityModifiers();
				anim4and5145.setPosition(-700, -700);
				anim4and5145.setVisible(false);
				isgimmic = true;
				finishUsagi = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (anim4and5243.contains(pX, pY) && finishNail) {
				Log.d(TAG, "nail");
				anim4and5243.stopAnimation();
				anim4and5243.clearEntityModifiers();
				s4and527.setVisible(false);
				anim4and5243.setPosition(-700, -700);
				anim4and5243.setVisible(false);
				isgimmic = true;
				finishNail = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (anim4and61345.contains(pX, pY) && finishPasta) {
				Log.d(TAG, "pasta");
				anim4and61345.stopAnimation();
				anim4and61345.clearEntityModifiers();
				anim4and61345.setPosition(-700, -700);
				anim4and61345.setVisible(false);
				isgimmic = true;
				finishPasta = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animhonekuma34.contains(pX, pY) && finishKuma) {
				Log.d(TAG, "kuma");
				animhonekuma34.stopAnimation();
				animhonekuma34.clearEntityModifiers();
				animhonekuma34.setPosition(-700, -700);
				animhonekuma34.setVisible(false);
				isgimmic = true;
				finishKuma = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animhonejisan34.contains(pX, pY) && finishJisan) {
				Log.d(TAG, "Jisan");
				animhonejisan34.stopAnimation();
				animhonejisan34.clearEntityModifiers();
				animhonejisan34.setPosition(-700, -700);
				animhonejisan34.setVisible(false);
				isgimmic = true;
				finishJisan = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (anim5and6134.contains(pX, pY) && finishKurage) {
				Log.d(TAG, "Kurage");
				anim5and6134.stopAnimation();
				anim5and6134.clearEntityModifiers();
				anim5and6134.setPosition(-700, -700);
				anim5and6134.setVisible(false);
				isgimmic = true;
				finishKurage = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (s5and625.contains(pX, pY) && finishEgg) {
				Log.d(TAG, "finishEgg");
				s5and625.clearEntityModifiers();
				s5and625.reset();
				s5and625.setPosition(-700, -700);
				s5and625.setVisible(false);
				isgimmic = true;
				finishEgg = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (anim5and63456.contains(pX, pY) && finishHeri) {
				Log.d(TAG, "Heri");
				anim5and63456.stopAnimation();
				anim5and63456.clearEntityModifiers();
				anim5and63456.setPosition(-700, -700);
				anim5and63456.setVisible(false);
				isgimmic = true;
				finishHeri = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animhonehana145.contains(pX, pY) && finishOhana) {
				Log.d(TAG, "Hana");
				animhonehana145.stopAnimation();
				animhonehana145.clearEntityModifiers();
				animhonehana145.setPosition(-700, -700);
				animhonehana145.setVisible(false);
				isgimmic = true;
				finishOhana = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animhonehana2345.contains(pX, pY) && finishHanabi) {
				Log.d(TAG, "hanabi");
				mp3pachidone.stop();
				animhonehana2345.stopAnimation();
				animhonehana2345.clearEntityModifiers();
				animhonehana2345.setPosition(-700, -700);
				animhonehana2345.setVisible(false);
				isgimmic = true;
				finishHanabi = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animhonecyou1345.contains(pX, pY) && finishCyou) {
				Log.d(TAG, "cyou");
				animhonecyou1345.stopAnimation();
				animhonecyou1345.clearEntityModifiers();
				animhonecyou1345.setPosition(-700, -700);
				animhonecyou1345.setVisible(false);
				isgimmic = true;
				finishCyou = false;
				sOne.setPosition(68, 41);
				sTwo.setPosition(64, 261);
				sThree.setPosition(64, 408);
			}

			if (animOnePiero.contains(pX, pY)) {
				Log.d(TAG, "Gimmic");
				Gimmic();
			}
		}
		if (pSceneTouchEvent.isActionUp()) {
			if (IsOTT) {
				isCheckOtt = false;
				IsOTT = false;
			}
		}
		return false;
	}

	public void LoopOnePiero(AnimatedSprite Animonepiero) {
		long pDurations[] = new long[] { 550, 550 };
		Animonepiero.animate(pDurations);
	}

	public void ScaleSprite() {
		LoopEntityModifier LoopScale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.8f, 1.0f, 1.1f), new ScaleModifier(0.2f, 1.1f, 1.0f), new DelayModifier(2.0f)), -1);
		sOne.registerEntityModifier(LoopScale);
		sTwo.registerEntityModifier(LoopScale);
		sThree.registerEntityModifier(LoopScale);
	}

	public void TouchsOne() {
		isTouch = false;
		mp3cyoki.play();
		animOnePiero.setVisible(false);
		sOnePiOne.setVisible(true);
		sOnePiOne.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sOnePiOne.setVisible(false);
				animOnePiero.setVisible(true);
				isTouch = true;
				checkmg = true;
			}
		}));
	}

	public void TouchsTwo() {
		isTouch = false;
		mp3gu.play();
		animOnePiero.setVisible(false);
		sTwoPiOne.setVisible(true);
		sTwoPiOne.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sTwoPiOne.setVisible(false);
				animOnePiero.setVisible(true);
				isTouch = true;
				checkmg = true;
			}
		}));
	}

	public void TouchsThree() {
		isTouch = false;
		mp3pa.play();
		animOnePiero.setVisible(false);
		sThreePiOne.setVisible(true);
		sThreePiOne.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				sThreePiOne.setVisible(false);
				animOnePiero.setVisible(true);
				isTouch = true;
				checkmg = true;
			}
		}));
	}

	//
	public void SwitchCase(int c) {
		switch (c) {
		case 1:
			VisibleChild();
			if (case1 == 0) {
				actionNeko(animhoneneko4112, animSmoke, animhoneneko4134);
			}
			if (case1 == 1) {
				actionKani(animhonekani4212, animSmoke, animhonekani4234);
			}
			case1++;
			if (case1 > 1)
				case1 = 0;
			break;

		case 2:
			VisibleChild();
			if (case2 == 0) {
				actionUsagi(anim4and51123, animSmoke, anim4and5145);
			}
			if (case2 == 1) {
				actionKatatsu(anim4and5212, animSmoke, anim4and5265, anim4and5243, s4and527);
			}
			case2++;
			if (case2 > 1)
				case2 = 0;
			break;

		case 3:
			VisibleChild();
			actionSupa(anim4and6112, animSmoke, anim4and61345);
			break;

		case 4:
			VisibleChild();
			if (case4 == 0) {
				actionKuma(animhonekuma12, animSmoke, animhonekuma34);
			}
			if (case4 == 1) {
				actionKubo(animhonejisan12, animSmoke, animhonejisan34);
			}
			case4++;
			if (case4 > 1)
				case4 = 0;
			break;

		case 5:
			VisibleChild();
			if (case5 == 0) {
				actionKurage(anim5and6112, animSmoke, anim5and6134);
			}

			if (case5 == 1) {
				actionEgg(anim5and6212, animSmoke, s5and623, s5and624, s5and625);

			}
			if (case5 == 2) {
				actionHeri(anim5and63123, animSmoke, anim5and63456);
			}

			case5++;
			if (case5 > 2)
				case5 = 0;
			break;
		case 6:
			VisibleChild();

			if (case6 == 0) {
				actionOhana(animhonehana1123, animSmoke, animhonehana145);
				Log.d(TAG, "Dang bi loi o day ");
			}
			if (case6 == 1) {
				actionHanabi(animhonehana212, animSmoke, animhonehana2345);
			}
			if (case6 == 2) {
				actionChou(animhonecyou112, animSmoke, animhonecyou1345);
			}
			case6++;
			if (case6 > 2)
				case6 = 0;
			break;

		default:
			Log.d(TAG, "ERROR");
			// --------------------------------------------------------------
			// 20130326
			// -------------------------------------------------------------
			error();
			// --------------------------------------------------------------
			// 20130326 END
			// -------------------------------------------------------------
			break;
		}
	}

	public void VisibleChild() {
		sONE1.setVisible(false);
		sONE2.setVisible(false);
		sTWO1.setVisible(false);
		sTWO2.setVisible(false);
		sTHREE1.setVisible(false);
		sTHREE2.setVisible(false);
	}

	public void VisibleParent() {
		sOne.setPosition(-200, -200);
		sTwo.setPosition(-200, -200);
		sThree.setPosition(-200, -200);
	}

	// Gimmic
	public void Gimmic() {
		if (isgimmic) {
			isTouchStart = false;
			if (checkgm) {
				checkgm = false;
				checkmg = false;

				// --------------------------------------------------------------
				// 20130326
				// -------------------------------------------------------------
				if (isPause()) {
					checkgm = true;
					checkmg = true;
					isTouchStart = true;
					return;
				}
				// --------------------------------------------------------------
				// 20130326END
				// -------------------------------------------------------------
				if (current % 2 == 0) {
					mp3arere.play();
				} else {
					mp3oyaoya.play();
				}
				current++;
				if (current > 2)
					current = 1;

				animOnePiero.setVisible(false);
				animOnePieroSize.setVisible(true);
				AnimOnePieroSize(animOnePieroSize);
				animOnePieroSize.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						animOnePieroSize.setPosition(246, 0);
						animOnePieroSize.setVisible(false);
						animOnePiero.setVisible(true);
						checkgm = true;
						checkmg = true;
						isTouchStart = true;
					}
				})));
			}
		} else {
			isTouchStart = false;
			isTouchSecond = false;
			if (checkmg) {
				checkmg = false;
				checkgm = false;
				mp3boyon.play();
				animOnePiero.setVisible(false);
				sTwoPiOne.setVisible(true);

				sTwoPiOne.registerEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.5f, 36.0f, 10.0f), new DelayModifier(0.1f, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						sTwoPiOne.setVisible(false);
						sTwoPiOne.setPosition(680, 36);
						animOnePiero.setPosition(animOnePiero.getmXFirst(), 10);
						animOnePiero.setVisible(true);
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						animOnePiero.registerEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.5f, 10.0f, 36.0f), new DelayModifier(0.1f, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								checkmg = true;
								checkgm = true;
								isTouchStart = true;
								isTouchSecond = true;
								animOnePiero.setPosition(680, 36);
							}
						})));
					}
				})));
			}
		}
	}

	public void AnimOnePieroSize(AnimatedSprite OnePieroSize) {
		long pDurations[] = new long[] { 600, 600 };
		OnePieroSize.animate(pDurations);
	}

	// Case
	public int checkOTT(int x, int y) {
		int c = 0;
		if (x == 1 && y == 1) {
			c = 1;
		}

		if (x == 1 && y == 2) {
			c = 2;
		}

		if (x == 1 && y == 3) {
			c = 3;
		}

		if (x == 2 && y == 1) {
			c = 2;
		}

		if (x == 2 && y == 2) {
			c = 4;
		}

		if (x == 2 && y == 3) {
			c = 5;
		}

		if (x == 3 && y == 1) {
			c = 3;
		}

		if (x == 3 && y == 2) {
			c = 5;
		}

		if (x == 3 && y == 3) {
			c = 6;
		}
		return c;
	}

	// ActionNeko
	public void actionNeko(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {
		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 350, 350 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3neko_voice.play();
								mp3neko.play();

							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3neko.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3neko.stop();
										mp3neko_voice.stop();
										finishNeko = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, " neko no touch : ");

										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.3f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishNeko = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Actionkani
	public void actionKani(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 450, 450 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);

						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3kani2.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3kasa.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new MoveXModifier(0.3f, 204.0f, 170.0f), new MoveXModifier(0.3f, 170.0f, 204.0f)));

								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3kasa.stop();
										mp3kani2.stop();
										finishKani = true;

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, " kani no touch : ");
										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.3f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishKani = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Action Katatsu
	public void actionKatatsu(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird, final AnimatedSprite AnimFour, final Sprite sEye) {
		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 450, 450 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 0, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3katatsumuri.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								mp3nyu.play();
								animThird.setVisible(false);
								AnimFour.setPosition(204, 19);
								AnimFour.setVisible(true);
								AnimFour.setCurrentTileIndex(0);
								AnimFour.registerEntityModifier(new DelayModifier(0.45f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sEye.setVisible(true);
										mp3kui.play();
										sEye.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.8f, 1.0f, 1.3f), new LoopEntityModifier(new SequenceEntityModifier(new RotationAtModifier(0.4f, 0, 12, 60.5f, 106.0f),
												new RotationAtModifier(0.4f, 12, -12, 60.5f, 106.0f), new RotationAtModifier(0.2f, -12, 0, 60.5f, 106.0f)), 2)));
										AnimFour.setCurrentTileIndex(1);
										AnimFour.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												AnimFour.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

													@Override
													public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
														finishNail = true;
													}

													@Override
													public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
														AnimFour.setPosition(-700, -700);
														AnimFour.setVisible(false);
														sEye.setVisible(false);
													}
												}), new DelayModifier(0.2f, new IEntityModifierListener() {

													@Override
													public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

													}

													@Override
													public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
														isgimmic = true;
													}
												}), new DelayModifier(0.1f, new IEntityModifierListener() {

													@Override
													public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

													}

													@Override
													public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
														sOne.setPosition(68, 41);
														sTwo.setPosition(64, 261);
														sThree.setPosition(64, 408);
														finishNail = false;
													}
												})));
											}
										}));
									}
								}));
							}
						});
					}
				});
			}
		});
	}

	// Action Usagi
	public void actionUsagi(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		final long pDurationsf[] = new long[] { 300, 300, 300 };
		final int[] pFramef = new int[] { 0, 1, 2 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 350, 350 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.setVisible(true);
		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3kui.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3kui.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);

						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3usagi2.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3chyu7.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3chyu7.stop();
										mp3usagi2.stop();
										finishUsagi = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "usagi no touch");
										animThird.setPosition(-700, -700);
										animThird.setVisible(false);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishUsagi = false;
									}
								})));
							}
						});
					}
				});
			}
		});

	}

	// Action Supa
	public void actionSupa(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 350, 350, 350 };
		final int[] pFramet = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);

				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 0, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3supa3.play();
								mp3kira5.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										finishPasta = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "Supa no touch");
										mp3kira5.stop();
										mp3supa3.stop();
										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishPasta = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Action Kuma
	public void actionKuma(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {
		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 350, 350 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.setVisible(true);
		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3bomi.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3bomi.stop();
				animSecond.setVisible(true);

				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3kuma.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3kira5.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3kira5.stop();
										mp3kuma.stop();
										finishKuma = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "Kuma no touch");
										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishKuma = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Action Kubo
	public void actionKubo(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {
		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 600, 600 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3kobujisan.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										finishJisan = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "Jisan no touch");
										mp3kobujisan.stop();
										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishJisan = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Action Kurage
	public void actionKurage(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {
		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {
					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.setCurrentTileIndex(0);
						animThird.registerEntityModifier(new SequenceEntityModifier(

						new MoveYModifier(0.3f, animThird.getY(), animThird.getY() - 50, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								mp3kurage2.play();
								mp3awaboko.play();
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								animThird.setCurrentTileIndex(1);
							}
						}),

						new MoveYModifier(0.3f, animThird.getY() - 50, animThird.getY(), new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								mp3awaboko.play();
								animThird.setCurrentTileIndex(0);
								animThird.registerEntityModifier(new SequenceEntityModifier(new MoveYModifier(0.3f, animThird.getY(), animThird.getY() - 50, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										animThird.setCurrentTileIndex(1);
									}
								}), new MoveYModifier(0.3f, animThird.getY() - 50, animThird.getY(), new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												mp3kurage2.stop();
												mp3awaboko.stop();
												finishKurage = true;
											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												Log.d(TAG, "Kurage no touch");
												animThird.setPosition(-999, -999);
												animThird.setVisible(false);
											}
										}), new DelayModifier(0.2f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												isgimmic = true;
											}
										}), new DelayModifier(0.1f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												sOne.setPosition(68, 41);
												sTwo.setPosition(64, 261);
												sThree.setPosition(64, 408);
												finishKurage = false;
											}
										})));
									}
								})));
							}
						})));
					}
				});
			}
		});
	}

	// Action Heri
	public void actionHeri(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300, 300 };
		final int[] pFramef = new int[] { 0, 1, 2 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 350, 350, 350 };
		final int[] pFramet = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3heri_voice.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3heri.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								mp3heri_voice.stop();
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										finishHeri = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "Heri no touch");
										mp3heri.stop();
										animThird.setPosition(-700, -700);
										animThird.setVisible(false);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishHeri = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	// Action Egg
	public void actionEgg(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final Sprite chef, final Sprite egg, final Sprite finish) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						chef.setVisible(true);
						egg.setVisible(true);
						mp3medamayaki3.play();
						mp3medamayaki.play();
						egg.registerEntityModifier(new SequenceEntityModifier(

						new ParallelEntityModifier(new MoveYModifier(0.75f, 146.0f, -50.0f), new RotationAtModifier(0.3f, 0, 16, 170.0f, 120.0f)),

						new ParallelEntityModifier(new MoveYModifier(0.75f, -50.0f, 145.0f), new DelayModifier(0.2f, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								egg.setVisible(false);
								chef.setVisible(false);
								finish.setPosition(204, 19);
								finish.setVisible(true);
								finish.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.8f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										finishEgg = true;
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										mp3medamayaki.stop();
										mp3medamayaki3.stop();
										finish.setVisible(false);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishEgg = false;
										egg.setRotation(0);
									}
								})));
							}
						}))));
					}
				});
			}
		});
	}

	// Action Ohana
	public void actionOhana(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {
		final long pDurationsf[] = new long[] { 300, 300, 300 };
		final int[] pFramef = new int[] { 0, 1, 2 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 400, 400 };
		final int[] pFramet = new int[] { 0, 1 };

		animFirst.setVisible(true);
		animFirst.animate(pDurationsf, pFramef, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syararan.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syararan.stop();
				animSecond.setVisible(true);

				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3ohana.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3syararan.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3ohana.stop();
										mp3syararan.stop();
										finishOhana = true;
										Log.d("TEST", "Xem no chay khong" + finishOhana);
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "ohana no touch");
										animThird.setPosition(-700, -700);
										animThird.setVisible(false);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishOhana = false;
									}
								})));
							}
						});
					}
				});
			}
		});

	}

	// Action Hanabi
	public void actionHanabi(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 400, 450, 500 };
		final int[] pFramet = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 0, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3hanabi.play();
								mp3pachidone.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								mp3hanabi.stop();
								animThird.setCurrentTileIndex(2);
								animThird.registerEntityModifier(new SequenceEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.35f, 1.0f, 1.3f), new ScaleModifier(0.35f, 1.3f, 0.8f), new ScaleModifier(0.35f, 0.8f, 1.0f)),
										new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {
											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
												finishHanabi = true;
											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												Log.d(TAG, "Hanabi no touch");
												mp3pachidone.stop();
												animThird.setPosition(-700, -700);
												animThird.setVisible(false);
											}
										}),

										new DelayModifier(0.1f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												isgimmic = true;
											}
										}), new DelayModifier(0.2f, new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
												sOne.setPosition(68, 41);
												sTwo.setPosition(64, 261);
												sThree.setPosition(64, 408);
												finishHanabi = false;
											}
										})));
							}
						});
					}
				});
			}
		});
	}

	// Action Chou
	public void actionChou(final AnimatedSprite animFirst, final AnimatedSprite animSecond, final AnimatedSprite animThird) {

		animFirst.setVisible(true);

		final long pDurationsf[] = new long[] { 300, 300 };
		final int[] pFramef = new int[] { 0, 1 };

		final long pDurationss[] = new long[] { 550, 600, 750 };
		final int[] pFrames = new int[] { 0, 1, 2 };

		final long pDurationst[] = new long[] { 450, 450, 450 };
		final int[] pFramet = new int[] { 0, 1, 2 };

		animFirst.animate(pDurationsf, pFramef, 1, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
				mp3syusyu.play();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

			}

			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				animFirst.setVisible(false);
				mp3syusyu.stop();
				animSecond.setVisible(true);
				animSecond.animate(pDurationss, pFrames, 0, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
						mp3kemuri.play();
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {

					}

					@Override
					public void onAnimationFinished(AnimatedSprite arg0) {
						animSecond.setVisible(false);
						mp3kemuri.stop();
						animThird.setPosition(204, 19);
						animThird.setVisible(true);
						animThird.animate(pDurationst, pFramet, 1, new IAnimationListener() {

							@Override
							public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
								mp3chou.play();
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
								mp3syararan.play();
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
							}

							@Override
							public void onAnimationFinished(AnimatedSprite arg0) {
								animThird.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f), new DelayModifier(1.5f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3syararan.stop();
										finishCyou = true;
										mp3chou.stop();
									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										Log.d(TAG, "Chou no touch");
										animThird.setVisible(false);
										animThird.setPosition(-999, -999);
									}
								}), new DelayModifier(0.2f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										isgimmic = true;
									}
								}), new DelayModifier(0.1f, new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sOne.setPosition(68, 41);
										sTwo.setPosition(64, 261);
										sThree.setPosition(64, 408);
										finishCyou = false;
									}
								})));
							}
						});
					}
				});
			}
		});
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.d(TAG, "loadKaraokeComplete");
		super.loadKaraokeComplete();
	}

	@Override
	public synchronized void onResumeGame() {
		_onResume();
		Log.d(TAG, "onResumeGame");
		LoopOnePiero(animOnePiero);
		ScaleSprite();
		setBoolean(false, finishNeko, finishKani, finishUsagi, finishNail, finishPasta, finishKuma, finishJisan, finishKurage, finishEgg, finishHeri, finishOhana, finishHanabi, finishCyou);

		isgimmic = true;

		isTouchStart = true;
		isTouchSecond = false;
		isCheckOtt = true;
		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		_onPause();
		p__x = 0;
		p__y = 0;
		Log.d(TAG, "On Pause Game");
		resetData();
		super.onPauseGame();

	}

	public void resetData() {
		mp3kemuri.stop();
		mp3arere.stop();
		mp3oyaoya.stop();
		mp3syusyu.stop();
		mp3neko.stop();
		mp3neko_voice.stop();
		mp3kasa.stop();
		mp3kani2.stop();
		mp3kui.stop();
		mp3chyu7.stop();
		mp3usagi2.stop();
		mp3nyu.stop();
		mp3katatsumuri.stop();
		mp3kira5.stop();
		mp3supa3.stop();
		mp3bomi.stop();
		mp3kira5.stop();
		mp3kuma.stop();
		mp3kobujisan.stop();
		mp3awaboko.stop();
		mp3kurage2.stop();
		mp3heri.stop();
		mp3heri_voice.stop();
		mp3chou.stop();
		mp3hanabi.stop();
		mp3pachidone.stop();
		mp3syararan.stop();
		mp3ohana.stop();

		if (animSmoke != null) {
			animSmoke.clearEntityModifiers();
			animSmoke.stopAnimation();
			animSmoke.setVisible(false);
		}

		if (animOnePieroSize != null) {
			animOnePieroSize.clearEntityModifiers();
			animOnePieroSize.stopAnimation();
			animOnePieroSize.setPosition(246, -29);
			animOnePieroSize.setVisible(false);
			animOnePiero.setVisible(true);
		}
		if (animhoneneko4112 != null) {
			animhoneneko4112.clearEntityModifiers();
			animhoneneko4112.stopAnimation();
			animhoneneko4112.setVisible(false);
		}
		if (animhoneneko4134 != null) {
			animhoneneko4134.clearEntityModifiers();
			animhoneneko4134.stopAnimation();
			animhoneneko4134.setPosition(-700, -700);
			animhoneneko4134.setVisible(false);
		}
		if (animhonekani4212 != null) {
			animhonekani4212.clearEntityModifiers();
			animhonekani4212.stopAnimation();
			animhonekani4212.setVisible(false);
		}
		if (animhonekani4234 != null) {
			animhonekani4234.clearEntityModifiers();
			animhonekani4234.stopAnimation();
			animhonekani4234.setVisible(false);
			animhonekani4234.setPosition(-700, -700);

		}

		if (anim4and51123 != null) {
			anim4and51123.clearEntityModifiers();
			anim4and51123.stopAnimation();
			anim4and51123.setVisible(false);

		}
		if (anim4and5145 != null) {
			anim4and5145.clearEntityModifiers();
			anim4and5145.stopAnimation();
			anim4and5145.setVisible(false);
			anim4and5145.setPosition(-700, -700);

		}
		if (anim4and5212 != null) {
			anim4and5212.clearEntityModifiers();
			anim4and5212.stopAnimation();
			anim4and5212.setVisible(false);

		}
		if (anim4and5265 != null) {
			anim4and5265.clearEntityModifiers();
			anim4and5265.stopAnimation();
			anim4and5265.setVisible(false);

		}
		if (anim4and5243 != null) {
			anim4and5243.clearEntityModifiers();
			anim4and5243.setVisible(false);
			anim4and5243.setPosition(-700, -700);
			s4and527.setVisible(false);
		}

		if (anim4and6112 != null) {
			anim4and6112.clearEntityModifiers();
			anim4and6112.stopAnimation();
			anim4and6112.setVisible(false);
		}
		if (anim4and61345 != null) {
			anim4and61345.clearEntityModifiers();
			anim4and61345.stopAnimation();
			anim4and61345.setVisible(false);
			anim4and61345.setPosition(-700, -700);

		}
		if (animhonekuma12 != null) {
			animhonekuma12.clearEntityModifiers();
			animhonekuma12.stopAnimation();
			animhonekuma12.setVisible(false);

		}
		if (animhonekuma34 != null) {
			animhonekuma34.clearEntityModifiers();
			animhonekuma34.stopAnimation();
			animhonekuma34.setPosition(-700, -700);
			animhonekuma34.setVisible(false);

		}
		if (animhonejisan12 != null) {
			animhonejisan12.clearEntityModifiers();
			animhonejisan12.stopAnimation();
			animhonejisan12.setVisible(false);
		}
		if (animhonejisan34 != null) {
			animhonejisan34.clearEntityModifiers();
			animhonejisan34.setVisible(false);
			animhonejisan34.stopAnimation();
			animhonejisan34.setPosition(-700, -700);

		}
		if (anim5and6112 != null) {
			anim5and6112.clearEntityModifiers();
			anim5and6112.setVisible(false);
			anim5and6112.stopAnimation();
		}

		if (anim5and6134 != null) {
			anim5and6134.clearEntityModifiers();
			anim5and6134.setVisible(false);
			anim5and6134.stopAnimation();
			anim5and6134.setPosition(-700, -700);

		}
		if (anim5and6212 != null) {
			anim5and6212.clearEntityModifiers();
			anim5and6212.stopAnimation();
			anim5and6212.setVisible(false);
		}
		if (anim5and63123 != null) {
			anim5and63123.clearEntityModifiers();
			anim5and63123.stopAnimation();
			anim5and63123.setVisible(false);
		}
		if (anim5and63456 != null) {
			anim5and63456.clearEntityModifiers();
			anim5and63456.setVisible(false);
			anim5and63456.stopAnimation();
			anim5and63456.setPosition(-700, -700);

		}
		if (animhonehana1123 != null) {
			animhonehana1123.clearEntityModifiers();
			animhonehana1123.stopAnimation();
			animhonehana1123.setVisible(false);

		}
		if (animhonehana145 != null) {
			animhonehana145.clearEntityModifiers();
			animhonehana145.setPosition(-700, -700);
			animhonehana145.stopAnimation();
			animhonehana145.setVisible(false);

		}
		if (animhonehana212 != null) {
			animhonehana212.clearEntityModifiers();
			animhonehana212.stopAnimation();
			animhonehana212.setVisible(false);

		}
		if (animhonehana2345 != null) {
			animhonehana2345.clearEntityModifiers();
			animhonehana2345.setVisible(false);
			animhonehana2345.stopAnimation();
			animhonehana2345.setPosition(-700, -700);

		}
		if (animhonecyou112 != null) {
			animhonecyou112.clearEntityModifiers();
			animhonecyou112.stopAnimation();
			animhonecyou112.setVisible(false);
		}
		if (animhonecyou1345 != null) {
			animhonecyou1345.clearEntityModifiers();
			animhonecyou1345.stopAnimation();
			animhonecyou1345.setPosition(-700, -700);
			animhonecyou1345.setVisible(false);
		}
		if (s5and625 != null) {
			s5and625.clearEntityModifiers();
			s5and625.reset();
			s5and625.setPosition(-700, -700);
			s5and625.setVisible(false);
		}
		if (s5and623 != null) {
			s5and623.clearEntityModifiers();
			s5and623.reset();
			s5and623.setVisible(false);
		}
		if (s5and624 != null) {
			s5and624.clearEntityModifiers();
			s5and624.reset();
			s5and624.setVisible(false);

			sOne.clearEntityModifiers();
			sTwo.clearEntityModifiers();
			sThree.clearEntityModifiers();

			sOne.setVisible(true);
			sTwo.setVisible(true);
			sThree.setVisible(true);
			sOne.setPosition(68, 41);
			sTwo.setPosition(68, 261);
			sThree.setPosition(68, 408);

			sONE1.setVisible(false);
			sONE2.setVisible(false);
			sTWO1.setVisible(false);
			sTHREE1.setVisible(false);
			sTWO2.setVisible(false);
			sTHREE2.setVisible(false);

			isCheckOtt = true;
			isTouchStart = true;
			isTouchSecond = false;

			setBoolean(false, finishNeko, finishKani, finishUsagi, finishNail, finishPasta, finishKuma, finishJisan, finishKurage, finishEgg, finishHeri, finishOhana, finishHanabi, finishCyou);

			isgimmic = true;
			checkgm = true;
			checkmg = true;
		}
	}

	public void setBoolean(boolean value, boolean... bs) {
		for (int i = 0; i < bs.length; i++) {
			bs[i] = value;
		}
	}

	// --------------------------------------------------------------
	// 20130326
	// -------------------------------------------------------------
	private void error() {
		// vi tri cua
		sOne.setPosition(sOne.getmXFirst(), sOne.getmYFirst());
		sTwo.setPosition(sTwo.getmXFirst(), sTwo.getmYFirst());
		sThree.setPosition(sThree.getmXFirst(), sThree.getmYFirst());
	}

	private Dialog dialog;

	protected void lock(boolean isLog) {
		if (isLog) {
			if (dialog != null) {
				dialog.dismiss();
			}

			dialog = new Dialog(this, style.Theme_Translucent_NoTitleBar_Fullscreen);
			dialog.setCancelable(false);
			dialog.show();
		} else if (dialog != null) {
			dialog.dismiss();
		}
	}

	private boolean isPause = false;
	private Handler handler = new Handler();

	private void _onPause() {
		isPause = true;
		lock(true);
	}

	private void _onResume() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isRestricted()) {
					isPause = false;
					lock(false);
				}
			}
		}, 1500);
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

}