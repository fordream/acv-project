package jp.co.xing.utaehon03.songs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3SanPoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Sanpo extends BaseGameActivity implements IOnSceneTouchListener {
	
	private final static String TAG = "LOG_VOL3SANPO";
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mAnhNenTexturePack;
	private TexturePack mHienDaiTexturePack;
	private TexturePack mCoDaiATexturePack;
	private TexturePack mCoDaiBTexturePack;
	private TexturePack mCoDaiCTexturePack;
	private TexturePack mKhungLongTexturePack;
	private TexturePackTextureRegionLibrary mAnhNenTexturePackLib;
	private TexturePackTextureRegionLibrary mHienDaiTexturePackLib;
	private TexturePackTextureRegionLibrary mCoDaiATexturePackLib;
	private TexturePackTextureRegionLibrary mCoDaiBTexturePackLib;
	private TexturePackTextureRegionLibrary mCoDaiCTexturePackLib;

	

	private ITextureRegion ttrBackground1, ttrBackground2, ttrBackground3,
	ttrBackground4, ttrBG1, mJakkalTextureRegion;;
	private Sprite sBackground1, sBackground2, sBackground3, sBackground4,
	sBG1, sBG2, sBG3, sBG4, sJakkalSprite;
	private Sprite sTBG1, sTBG2, sTBG3, sTBG4;

	// belong BG1
	private ITextureRegion mWaterTextureRegion;
	private TiledTextureRegion ttrKirin, ttrKirinKids, ttrZouKids, ttrZou,
		ttrLion, ttrBoyGirl;
	private AnimatedSprite sKirin, sZou, sLion, sBoyGirl;
	private Sprite mWaterASprite, mWaterBSprite;

// belong BG2
	private ITextureRegion ttrPrakiozaulKidA, ttrPrakiozaulKidB;
	private TiledTextureRegion ttrLionKids, ttrDacho, ttrDachoKids, ttrGazel,
	ttrGazelKids, ttrSimauma2, ttrSimaumaKids, ttrJakkal, ttrJakkalKid,
	ttrPrakiozaul, ttrPurakiozaulKid, ttrSimauma1;
	private AnimatedSprite  sDacho, sGazel,
	sSimauma2, sJakkal, sPrakiozaul,
	sSimauma1;
	private Sprite sPrakiozaulKidA, sPrakiozaulKidB;

// belong BG4
	private TiledTextureRegion ttrTiranozaul, ttrTiranozaulKid, ttrOviraputoru,
	ttrOviraputoruKid, ttrCenter, ttrPteranodon;
	private AnimatedSprite sTiranozaul, sOviraputoru,
	 sCenter, sPteranodon;
	private ITextureRegion ttrTiranozaulsNiku;
	private Sprite sTiranozaulsNiku;

// belong BG3
	private TiledTextureRegion ttrAnkirosaur, ttrAnkirosaurKid,
	ttrTorikeratps_kids, ttrSutegozauls_kids, ttrKi_temae, ttrTamago1,
	ttrTamago2, ttrTamago3, ttrTamago4, ttrTamago5, ttrTamago6,
	ttrSutegozauls, ttrTrikeratpsA, ttrTrikeratpsB, ttrTrikeratpsAB;
	private AnimatedSprite sAnkirosaur,
	sKi_temae, sTamago1, sTamago2, sTamago3,
	sTamago4, sTamago5, sTamago6, sSutegozauls, sTrikeratpsA,
	sTrikeratpsB, sTrikeratpsAB;

// Portal
private TiledTextureRegion mPortalTopTiledTextureRegion;
private TiledTextureRegion mPortalBackTiledTextureRegion;

// private Sprite mPortalLeftTopSprite, mPortalRightTopSprite,
// mPortalOneSprite, mPortalTwoSprite;
private AnimatedSprite mPortalATopAniSprite, mPortalABackAniSprite,
	mPortalBTopAniSprite, mPortalBBackAniSprite;

private KidAnimatedSprite sTorikeratps_kids, sPrakiozaulKid, sTiranozaulKid, sSutegozauls_kids,
		sOviraputoruKid,sAnkirosaurKid, sSimaumaKids, sJakkalKid, sGazelKids, sDachoKids,sLionKids,
		sKirinKids, sZouKids;

private Rectangle mVaCham;

// Sound

private Sound A3_11_BABY, A3_11_TAMAGO, A3_12_OVIRA_ASHI, A3_12_OVIRA_NAKU,
	A3_12_OVIRA_VO, A3_13_BOY, A3_14_GIRL, A3_15_ANKI_NAKU,
	A3_15_ANKI_SHIPO, A3_15_ANKI_VO, A3_21_JAKARU_EAT,
	A3_21_JAKARU_VO, A3_22_KIRIN, A3_22_KIRIN_VO, A3_23_SHIMAUMA_RUN,
	A3_23_SHIMAUMA_VO, A3_24_RION_NAKU, A3_24_RION_VO,
	A3_25_GAZERU_FLY, A3_25_GAZERU_VO, A3_26_DACYO_ASHI,
	A3_26_DACYO_NAKU, A3_26_DACYO_VO, A3_27_ZOU_NAKU, A3_27_ZOU_VO,
	A3_29_SUTEGO_EAT, A3_29_SUTEGO_NAKU, A3_29_SUTEGO_VO,
	A3_30_TIRANO_EAT, A3_30_TIRANO_NAKU,
	A3_30_TIRANO_VO, A3_31_TORIKERATSUNO, A3_31_TORIKERAZA,
	A3_31_TORIKERAZA3, A3_31_TORIKERA_VO, A3_32_BURAKIO_OCHIRU,
	A3_32_BURAKIO_VO, A3_34_PUTERA, A3_34_PUTERA_VO, A3_NIKUOCHI2,
	A3_WARP, A3__30_NIKUOCHI2, A3_CHYAPA, A3_BUSUPYU, A3_CHU;

private boolean isGirlBoy = true;
private boolean isSwitch = false;
private static int STATUS = 1;
private boolean isWarptouch = false;

protected LinkedList<KidAnimatedSprite> KidLinkedRoot;
protected HashMap<Integer, KidAnimatedSprite> AllKidMap = new HashMap<Integer, Vol3Sanpo.KidAnimatedSprite>();
	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("sanpo/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sanpo/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets(), "sanpo/gfx/");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		mAnhNenTexturePack = mTexturePackLoaderFromSource.load("anhnen.xml");
		mHienDaiTexturePack = mTexturePackLoaderFromSource.load("hiendai.xml");
		mCoDaiATexturePack = mTexturePackLoaderFromSource.load("codaiA.xml"); 
		mCoDaiBTexturePack = mTexturePackLoaderFromSource.load("codaiB.xml");
		mCoDaiCTexturePack = mTexturePackLoaderFromSource.load("codaiC.xml");
		mKhungLongTexturePack = mTexturePackLoaderFromSource.load("khunglong.xml");
		
		mAnhNenTexturePack.loadTexture();
		mHienDaiTexturePack.loadTexture();
		mCoDaiATexturePack.loadTexture();
		mCoDaiBTexturePack.loadTexture();
		mCoDaiCTexturePack.loadTexture();
		mKhungLongTexturePack.loadTexture();
		
		mAnhNenTexturePackLib = mAnhNenTexturePack.getTexturePackTextureRegionLibrary();
		mHienDaiTexturePackLib = mHienDaiTexturePack.getTexturePackTextureRegionLibrary();
		mCoDaiATexturePackLib = mCoDaiATexturePack.getTexturePackTextureRegionLibrary();
		mCoDaiBTexturePackLib = mCoDaiBTexturePack.getTexturePackTextureRegionLibrary();
		mCoDaiCTexturePackLib = mCoDaiCTexturePack.getTexturePackTextureRegionLibrary();
		//mKhungLongTexturePackLib = mKhungLongTexturePack.getTexturePackTextureRegionLibrary();
		
		mWaterTextureRegion = mHienDaiTexturePackLib.get(Vol3SanPoResource.HienDai.A3_22_2_2_IPHONE_KIRIN_ID);
		ttrBackground1 = mCoDaiBTexturePackLib.get(Vol3SanPoResource.CoDaiB.A3_37_1_IPHONE_HAIKEI_ID);
		ttrBackground2 = mAnhNenTexturePackLib.get(Vol3SanPoResource.AnhNen.A3_37_2_IPHONE_HAIKEI_ID);
		ttrBackground3 = mAnhNenTexturePackLib.get(Vol3SanPoResource.AnhNen.A3_37_3_IPHONE_HAIKEI_ID);
		ttrBackground4 = mAnhNenTexturePackLib.get(Vol3SanPoResource.AnhNen.A3_37_4_IPHONE_HAIKEI_ID);
		
		ttrBoyGirl = getTiledTextureFromPacker(mCoDaiBTexturePack,
				Vol3SanPoResource.CoDaiB.A3_13AND14_1_IPHONE_KIDS_ID, Vol3SanPoResource.CoDaiB.A3_13AND14_2_IPHONE_KIDS_ID,
				Vol3SanPoResource.CoDaiB.A3_13AND14_3_IPHONE_KIDS_ID, Vol3SanPoResource.CoDaiB.A3_13AND14_4_IPHONE_KIDS_ID,
				Vol3SanPoResource.CoDaiB.A3_13AND14_5_IPHONE_KIDS_ID, Vol3SanPoResource.CoDaiB.A3_13AND14_6_IPHONE_KIDS_ID,
				Vol3SanPoResource.CoDaiB.A3_13AND14_7_IPHONE_KIDS_ID, Vol3SanPoResource.CoDaiB.A3_13AND14_8_IPHONE_KIDS_ID,
				Vol3SanPoResource.CoDaiB.A3_13_1_IPHONE_BOY_ID, Vol3SanPoResource.CoDaiB.A3_14_1_IPHONE_GIRL_ID
				);


		BitmapTextureAtlas ttBG1 = new BitmapTextureAtlas(getTextureManager(),2, 2, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ttrBG1 = new TextureRegion(ttBG1, 0, 0, 0, 0);
		ttBG1.load();
		
		ttrKirin = getTiledTextureFromPacker(mHienDaiTexturePack,
				Vol3SanPoResource.HienDai.A3_22_1_1_IPHONE_KIRIN_ID,
				Vol3SanPoResource.HienDai.A3_22_1_2_IPHONE_KIRIN_ID,
				Vol3SanPoResource.HienDai.A3_22_2_1_IPHONE_KIRIN_ID);
		ttrKirinKids = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_3_1_IPHONE_KIRIN_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_3_2_IPHONE_KIRIN_KIDS_ID);
		ttrZou = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_27_1_1_IPHONE_ZOU_ID,
				Vol3SanPoResource.HienDai.A3_27_1_2_IPHONE_ZOU_ID, 
				Vol3SanPoResource.HienDai.A3_27_2_1_IPHONE_ZOU_ID);
		ttrZouKids = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_2_1_IPHONE_ZOU_KIDS_ID, 
				Vol3SanPoResource.HienDai.A3_2_2_IPHONE_ZOU_KIDS_ID);
		ttrLion = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_24_1_1_IPHONE_LION_ID,Vol3SanPoResource.HienDai.A3_24_1_2_IPHONE_LION_ID,
				Vol3SanPoResource.HienDai.A3_24_2_1_IPHONE_LION_ID,Vol3SanPoResource.HienDai.A3_24_2_2_IPHONE_LION_ID,
				Vol3SanPoResource.HienDai.A3_24_2_3_IPHONE_LION_ID,Vol3SanPoResource.HienDai.A3_24_2_4_IPHONE_LION_ID
				);
		ttrLionKids = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_7_1_IPHONE_LION_KIDS_ID, 
				Vol3SanPoResource.HienDai.A3_7_2_IPHONE_LION_KIDS_ID);
		ttrDacho = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_26_1_1_IPHONE_GACHO_ID, Vol3SanPoResource.HienDai.A3_26_1_2_IPHONE_GACHO_ID,
				Vol3SanPoResource.HienDai.A3_26_2_1_IPHONE_GACHO_ID, Vol3SanPoResource.HienDai.A3_26_2_2_IPHONE_GACHO_ID,
				Vol3SanPoResource.HienDai.A3_26_2_3_IPHONE_GACHO_ID, Vol3SanPoResource.HienDai.A3_26_2_4_IPHONE_GACHO_ID,
				Vol3SanPoResource.HienDai.A3_26_2_5_IPHONE_GACHO_ID, Vol3SanPoResource.HienDai.A3_26_2_6_IPHONE_GACHO_ID,
				Vol3SanPoResource.HienDai.A3_26_2_7_IPHONE_GACHO_ID, Vol3SanPoResource.HienDai.A3_26_2_8_IPHONE_GACHO_ID
				);
		ttrDachoKids = getTiledTextureFromPacker(mHienDaiTexturePack,
				Vol3SanPoResource.HienDai.A3_1_1_1_IPHONE_DACHO_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_1_1_2_IPHONE_DACHO_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_1_1_IPHONE_DACHO_KIDS_ID
				);
		ttrGazel  =  getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_25_1_1_IPHONE_GAZEL_ID, 
				Vol3SanPoResource.HienDai.A3_25_1_2_IPHONE_GAZEL_ID,
				Vol3SanPoResource.HienDai.A3_25_2_1_IPHONE_GAZEL_ID);
		ttrGazelKids = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_6_1_IPHONE_GAZEL_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_6_2_IPHONE_GAZEL_KIDS_ID);
		ttrSimaumaKids = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_4_1_IPHONE_SIMAUMA_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_4_2_IPHONE_SIMAUMA_KIDS_ID);
		ttrJakkal = getTiledTextureFromPacker(mHienDaiTexturePack,
				Vol3SanPoResource.HienDai.A3_21_1_1_IPHONE_JAKKAL_ID, Vol3SanPoResource.HienDai.A3_21_1_2_IPHONE_JAKKAL_ID,
				Vol3SanPoResource.HienDai.A3_21_2_1_IPHONE_JAKKAL_ID, Vol3SanPoResource.HienDai.A3_21_2_2_IPHONE_JAKKAL_ID,
				Vol3SanPoResource.HienDai.A3_21_2_3_IPHONE_JAKKAL_ID
				);
		ttrJakkalKid = getTiledTextureFromPacker(mHienDaiTexturePack, 
				Vol3SanPoResource.HienDai.A3_5_1_IPHONE_JAKKAL_KIDS_ID,
				Vol3SanPoResource.HienDai.A3_5_2_IPHONE_JAKKAL_KIDS_ID);
		ttrPrakiozaul = getTiledTextureFromPacker(mKhungLongTexturePack, 
				Vol3SanPoResource.KhungLong.A3_32_1_1_IPHONE_PRAKIOZAULS_ID,
				Vol3SanPoResource.KhungLong.A3_32_1_2_IPHONE_PRAKIOZAULS_ID,
				Vol3SanPoResource.KhungLong.A3_32_2_1_IPHONE_PRAKIOZAULS_ID
				);
		ttrPurakiozaulKid = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_9_1_IPHONE_PURAKIOZAURS_KIDS_ID,
				Vol3SanPoResource.CoDaiA.A3_9_2_IPHONE_PURAKIOZAURS_KIDS_ID);
		ttrTiranozaul = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_30_1_1_IPHONE_TIRANOZAULS_ID, Vol3SanPoResource.CoDaiA.A3_30_1_2_IPHONE_TIRANOZAULS_ID,
				Vol3SanPoResource.CoDaiA.A3_30_2_1_IPHONE_TIRANOZAULS_ID, Vol3SanPoResource.CoDaiA.A3_30_2_2_IPHONE_TIRANOZAULS_ID,
				Vol3SanPoResource.CoDaiA.A3_30_2_3_IPHONE_TIRANOZAULS_ID);
		ttrTiranozaulKid = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_17_1_IPHONE_TIRANOSAURS_KIDS_ID,
				Vol3SanPoResource.CoDaiA.A3_17_2_IPHONE_TIRANOSAURS_KIDS_ID);
		ttrAnkirosaur = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_15_1_1_IPHONE_ANKIROSAURS_ID, Vol3SanPoResource.CoDaiA.A3_15_1_2_IPHONE_ANKIROSAURS_ID,
				Vol3SanPoResource.CoDaiA.A3_15_2_1_IPHONE_ANKIROSAURS_ID, Vol3SanPoResource.CoDaiA.A3_15_2_2_IPHONE_ANKIROSAURS_ID,
				Vol3SanPoResource.CoDaiA.A3_15_2_3_IPHONE_ANKIROSAURS_ID, Vol3SanPoResource.CoDaiA.A3_15_2_4_IPHONE_ANKIROSAURS_ID,
				Vol3SanPoResource.CoDaiA.A3_15_2_5_IPHONE_ANKIROSAURS_ID, Vol3SanPoResource.CoDaiA.A3_15_2_6_IPHONE_ANKIROSAURS_ID
				);
		ttrAnkirosaurKid = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_20_1_IPHONE_ANKIROSAULS_KIDS_ID, 
				Vol3SanPoResource.CoDaiA.A3_20_2_IPHONE_ANKIROSAULS_KIDS_ID);
		ttrOviraputoru = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_12_1_1_IPHONE_OVIRAPUTORU_ID, Vol3SanPoResource.CoDaiA.A3_12_1_2_IPHONE_OVIRAPUTORU_ID,
				Vol3SanPoResource.CoDaiA.A3_12_1_3_IPHONE_OVIRAPUTORU_ID, Vol3SanPoResource.CoDaiA.A3_12_1_4_IPHONE_OVIRAPUTORU_ID,
				Vol3SanPoResource.CoDaiA.A3_12_1_5_IPHONE_OVIRAPUTORU_ID, Vol3SanPoResource.CoDaiA.A3_12_1_6_IPHONE_OVIRAPUTORU_ID,
				Vol3SanPoResource.CoDaiA.A3_12_1_7_IPHONE_OVIRAPUTORU_ID, Vol3SanPoResource.CoDaiA.A3_12_1_8_IPHONE_OVIRAPUTORU_ID
				);
		ttrOviraputoruKid = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_16_1_IPHONE_OVIRAPUTORU_KIDS_ID, 
				Vol3SanPoResource.CoDaiA.A3_16_2_IPHONE_OVIRAPUTORU_KIDS_ID);
		ttrTorikeratps_kids = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_18_1_IPHONE_TORIKERATPS_KIDS_ID, 
				Vol3SanPoResource.CoDaiA.A3_18_2_IPHONE_TORIKERATPS_KIDS_ID);
		ttrSutegozauls_kids = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_19_1_IPHONE_SUTEGOZAULS_KIDS_ID, 
				Vol3SanPoResource.CoDaiA.A3_19_2_IPHONE_SUTEGOZAULS_KIDS_ID);
		ttrKi_temae =  getTiledTextureFromPacker(mKhungLongTexturePack, Vol3SanPoResource.KhungLong.A3_10_1_IPHONE_KI_TEMAE_ID);
		ttrTamago1 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_1_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_1_2_IPHONE_TAMAGO_ID);
		ttrTamago2 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_2_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_2_2_IPHONE_TAMAGO_ID);
		ttrTamago3 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_3_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_3_2_IPHONE_TAMAGO_ID);
		ttrTamago4 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_4_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_4_2_IPHONE_TAMAGO_ID);
		ttrTamago5 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_5_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_5_2_IPHONE_TAMAGO_ID);
		ttrTamago6 =  getTiledTextureFromPacker(mCoDaiATexturePack,
				Vol3SanPoResource.CoDaiA.A3_11_6_1_IPHONE_TAMAGO_ID, Vol3SanPoResource.CoDaiA.A3_11_6_2_IPHONE_TAMAGO_ID);
		ttrCenter =  getTiledTextureFromPacker(mKhungLongTexturePack, Vol3SanPoResource.KhungLong.A3_28_1_IPHONE_KI_CENTER_ID);
		ttrSutegozauls = getTiledTextureFromPacker(mCoDaiATexturePack, 
				Vol3SanPoResource.CoDaiA.A3_29_1_1_IPHONE_SUTEGOZAULS_ID, 
				Vol3SanPoResource.CoDaiA.A3_29_1_2_IPHONE_SUTEGOZAULS_ID,
				Vol3SanPoResource.CoDaiA.A3_29_2_1_IPHONE_SUTEGOZAULS_ID
				);
		ttrPteranodon = getTiledTextureFromPacker(mCoDaiCTexturePack, 
				Vol3SanPoResource.CoDaiC.A3_34_1_1_IPHONE_PTERANODON_ID, 
				Vol3SanPoResource.CoDaiC.A3_34_1_2_IPHONE_PTERANODON_ID,
				Vol3SanPoResource.CoDaiC.A3_34_2_1_IPHONE_PTERANODON_ID,
				Vol3SanPoResource.CoDaiC.A3_34_2_2_IPHONE_PTERANODON_ID
				);
		ttrSimauma1 = getTiledTextureFromPacker(mHienDaiTexturePack,
				Vol3SanPoResource.HienDai.A3_23_1_1_IPHONE_SIMAUMA_ID,
				Vol3SanPoResource.HienDai.A3_23_1_2_IPHONE_SIMAUMA_ID
				);
		mPortalTopTiledTextureRegion = getTiledTextureFromPacker(mCoDaiBTexturePack,
				Vol3SanPoResource.CoDaiB.A3_35_1_IPHONE_TEMAE_ID,
				Vol3SanPoResource.CoDaiB.A3_35_2_IPHONE_TEMAE_ID
				);
		mPortalBackTiledTextureRegion =  getTiledTextureFromPacker(mCoDaiBTexturePack,
				Vol3SanPoResource.CoDaiB.A3_36_1_IPHONE_OKU_ID,
				Vol3SanPoResource.CoDaiB.A3_36_2_IPHONE_OKU_ID
				);
		ttrPrakiozaulKidA = mCoDaiCTexturePackLib.get(Vol3SanPoResource.CoDaiC.A3_32_2_2_IPHONE_PRAKIOZAULS_KIDSA_ID);
		ttrPrakiozaulKidB = mCoDaiCTexturePackLib.get(Vol3SanPoResource.CoDaiC.A3_32_2_3_IPHONE_PRAKIOZAULS_KIDSB_ID);
		ttrTiranozaulsNiku 	= mCoDaiATexturePackLib.get(Vol3SanPoResource.CoDaiA.A3_30_2_4_IPHONE_TIRANOZAULS_NIKU_ID);
		ttrTrikeratpsA =  getTiledTextureFromPacker(mCoDaiCTexturePack,
				Vol3SanPoResource.CoDaiC.A3_31A_1_1_IPHONE_TRIKERATPS_ID,Vol3SanPoResource.CoDaiC.A3_31A_1_2_IPHONE_TRIKERATPS_ID,
				Vol3SanPoResource.CoDaiC.A3_31A_1_3_IPHONE_TRIKERATPS_ID,Vol3SanPoResource.CoDaiC.A3_31A_1_4_IPHONE_TRIKERATPS_ID
				);
		ttrTrikeratpsB = getTiledTextureFromPacker(mCoDaiCTexturePack,
				Vol3SanPoResource.CoDaiC.A3_31B_1_1_IPHONE_TRIKERATPS_ID,Vol3SanPoResource.CoDaiC.A3_31B_1_2_IPHONE_TRIKERATPS_ID
				);
		ttrTrikeratpsAB = getTiledTextureFromPacker(mCoDaiCTexturePack,
				Vol3SanPoResource.CoDaiC.A3_31_2_1_IPHONE_TRIKERATPS_ID,Vol3SanPoResource.CoDaiC.A3_31_2_2_IPHONE_TRIKERATPS_ID,
				Vol3SanPoResource.CoDaiC.A3_31_2_3_IPHONE_TRIKERATPS_ID
				);
		ttrSimauma2 = getTiledTextureFromPacker(mHienDaiTexturePack,
				Vol3SanPoResource.HienDai.A3_23_2_1_IPHONE_SIMAUMA_ID,Vol3SanPoResource.HienDai.A3_23_2_2_IPHONE_SIMAUMA_ID
				);
		mJakkalTextureRegion = mHienDaiTexturePackLib.get(Vol3SanPoResource.HienDai.A3_21_2_4_IPHONE_JAKKAL_ID);
	}

	@Override
	protected void loadKaraokeSound() {
		A3_11_BABY = loadSoundResourceFromSD(Vol3SanPoResource.A3_11_BABY);
		A3_11_TAMAGO = loadSoundResourceFromSD(Vol3SanPoResource.A3_11_TAMAGO);
		A3_12_OVIRA_ASHI = loadSoundResourceFromSD(Vol3SanPoResource.A3_12_OVIRA_ASHI);
		A3_12_OVIRA_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_12_OVIRA_NAKU);
		A3_12_OVIRA_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_12_OVIRA_VO);
		A3_13_BOY = loadSoundResourceFromSD(Vol3SanPoResource.A3_13_BOY);
		A3_14_GIRL = loadSoundResourceFromSD(Vol3SanPoResource.A3_14_GIRL);
		A3_15_ANKI_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_15_ANKI_NAKU);
		A3_15_ANKI_SHIPO = loadSoundResourceFromSD(Vol3SanPoResource.A3_15_ANKI_SHIPO);
		A3_15_ANKI_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_15_ANKI_VO);
		A3_21_JAKARU_EAT = loadSoundResourceFromSD(Vol3SanPoResource.A3_21_JAKARU_EAT);
		A3_21_JAKARU_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_21_JAKARU_VO);
		A3_22_KIRIN = loadSoundResourceFromSD(Vol3SanPoResource.A3_22_KIRIN);
		A3_22_KIRIN_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_22_KIRIN_VO);
		A3_23_SHIMAUMA_RUN = loadSoundResourceFromSD(Vol3SanPoResource.A3_23_SHIMAUMA_RUN);
		A3_23_SHIMAUMA_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_23_SHIMAUMA_VO);
		A3_24_RION_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_24_RION_NAKU);
		A3_24_RION_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_24_RION_VO);
		A3_25_GAZERU_FLY = loadSoundResourceFromSD(Vol3SanPoResource.A3_25_GAZERU_FLY);
		A3_25_GAZERU_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_25_GAZERU_VO);
		A3_26_DACYO_ASHI = loadSoundResourceFromSD(Vol3SanPoResource.A3_26_DACYO_ASHI);
		A3_26_DACYO_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_26_DACYO_NAKU);
		A3_26_DACYO_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_26_DACYO_VO);
		A3_27_ZOU_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_27_ZOU_NAKU);
		A3_27_ZOU_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_27_ZOU_VO);
		A3_29_SUTEGO_EAT = loadSoundResourceFromSD(Vol3SanPoResource.A3_29_SUTEGO_EAT);
		A3_29_SUTEGO_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_29_SUTEGO_NAKU);
		A3_29_SUTEGO_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_29_SUTEGO_VO);
		A3_30_TIRANO_EAT = loadSoundResourceFromSD(Vol3SanPoResource.A3_30_TIRANO_EAT);
		A3_30_TIRANO_NAKU = loadSoundResourceFromSD(Vol3SanPoResource.A3_30_TIRANO_NAKU);
		A3_30_TIRANO_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_30_TIRANO_VO);
		A3_31_TORIKERATSUNO = loadSoundResourceFromSD(Vol3SanPoResource.A3_31_TORIKERATSUNO);
		A3_31_TORIKERAZA = loadSoundResourceFromSD(Vol3SanPoResource.A3_31_TORIKERAZA);
		A3_31_TORIKERAZA3 = loadSoundResourceFromSD(Vol3SanPoResource.A3_31_TORIKERAZA3);
		A3_31_TORIKERA_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_31_TORIKERA_VO);
		A3_32_BURAKIO_OCHIRU = loadSoundResourceFromSD(Vol3SanPoResource.A3_32_BURAKIO_OCHIRU);
		A3_32_BURAKIO_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_32_BURAKIO_VO);
		A3_34_PUTERA = loadSoundResourceFromSD(Vol3SanPoResource.A3_34_PUTERA);
		A3_34_PUTERA_VO = loadSoundResourceFromSD(Vol3SanPoResource.A3_34_PUTERA_VO);
		A3_NIKUOCHI2 = loadSoundResourceFromSD(Vol3SanPoResource.A3_NIKUOCHI2);
		A3_WARP = loadSoundResourceFromSD(Vol3SanPoResource.A3_WARP);
		A3__30_NIKUOCHI2 = loadSoundResourceFromSD(Vol3SanPoResource.A3__30_NIKUOCHI2);
		A3_CHYAPA = loadSoundResourceFromSD(Vol3SanPoResource.A3_CHYAPA);
		A3_BUSUPYU = loadSoundResourceFromSD(Vol3SanPoResource.A3_BUSUPYU);
		A3_CHU = loadSoundResourceFromSD(Vol3SanPoResource.A3_CHU);
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				ttrBackground1, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();
		
		sBoyGirl = new AnimatedSprite(480, 299, ttrBoyGirl, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoyGirl);
		// =============== belong BG2===================//
		//AnimatedSprite(599, 324, ttrJakkal);
		sJakkalSprite = new Sprite(599, 0, mJakkalTextureRegion, this.getVertexBufferObjectManager());
		sJakkalSprite.setVisible(false);
		
		sBackground2 = new Sprite(0, 0, ttrBackground2, this.getVertexBufferObjectManager());
		sLion = new AnimatedSprite(-17, 423, ttrLion, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sLion);
		sLionKids = new KidAnimatedSprite(264, 534, ttrLionKids, this.getVertexBufferObjectManager());
		sLionKids.isFuture = true;
		sLionKids.setpY(-50);
		AllKidMap.put(AllKidMap.size(), sLionKids);
		this.mScene.registerTouchArea(sLionKids);
		
		sDacho = new AnimatedSprite(274, 221, ttrDacho, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sDacho);
		sDachoKids = new KidAnimatedSprite(456, 325, ttrDachoKids, this.getVertexBufferObjectManager());
		sDachoKids.isFuture = true;
		sDachoKids.setpY(50);
		AllKidMap.put(AllKidMap.size(), sDachoKids);
		this.mScene.registerTouchArea(sDachoKids);
		
		sGazel = new AnimatedSprite(480, 478, ttrGazel, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sGazel);
		sGazelKids = new KidAnimatedSprite(626, 549, ttrGazelKids, this.getVertexBufferObjectManager());
		sGazelKids.isFuture = true;
		sGazelKids.setpY(-90);
		AllKidMap.put(AllKidMap.size(), sGazelKids);
		this.mScene.registerTouchArea(sGazelKids);
		
		sJakkal = new AnimatedSprite(599, 324, ttrJakkal, this.getVertexBufferObjectManager());
		sJakkalKid = new KidAnimatedSprite(741, 370, ttrJakkalKid, this.getVertexBufferObjectManager());
		sJakkalKid.isFuture = true;
		sJakkalKid.setpY(110);
		AllKidMap.put(AllKidMap.size(), sJakkalKid);
		this.mScene.registerTouchArea(sJakkalKid);

		sZou = new AnimatedSprite(-117, 108, ttrZou, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sZou);
		sZouKids = new KidAnimatedSprite(-12, 267, ttrZouKids, this.getVertexBufferObjectManager());
		sZouKids.isFuture = true;
		sZouKids.setpY(50);
		AllKidMap.put(AllKidMap.size(), sZouKids);
		this.mScene.registerTouchArea(sZouKids);

		sSimauma2 = new AnimatedSprite(589, 185, ttrSimauma2, this.getVertexBufferObjectManager());
		sSimauma2.setVisible(false);
		AnimatedDefaultAction(sSimauma2);
		sSimauma1 = new AnimatedSprite(699, 182, ttrSimauma1, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sSimauma1);
		
		sSimaumaKids = new KidAnimatedSprite(791, 256, ttrSimaumaKids, this.getVertexBufferObjectManager());
		sSimaumaKids.isFuture = true;
		sSimaumaKids.setpY(120);
		AllKidMap.put(AllKidMap.size(), sSimaumaKids);
		this.mScene.registerTouchArea(sSimaumaKids);

		// =============== belong BG1===================//
		sBackground1 = new Sprite(0, 0, ttrBackground1, this.getVertexBufferObjectManager());
		sKirin = new AnimatedSprite(518, 1, ttrKirin, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sKirin);
		sKirinKids = new KidAnimatedSprite(-246, 195, ttrKirinKids, this.getVertexBufferObjectManager());
		sKirinKids.isFuture = true;
		sKirinKids.setpY(120);
		AllKidMap.put(AllKidMap.size(), sKirinKids);
		this.mScene.registerTouchArea(sKirinKids);

		// =============== belong BG3===================//
		sBackground3 = new Sprite(0, 0, ttrBackground3, this.getVertexBufferObjectManager());
		sAnkirosaur = new AnimatedSprite(-870, 484, ttrAnkirosaur, this.getVertexBufferObjectManager());
		AnimatedDefaultAction(sAnkirosaur);

		sTamago1 = new AnimatedSprite(710, 545, ttrTamago1, this.getVertexBufferObjectManager());
		sTamago2 = new AnimatedSprite(-682, 349, ttrTamago2, this.getVertexBufferObjectManager());

		sTorikeratps_kids = new KidAnimatedSprite(-289, 547, ttrTorikeratps_kids, this.getVertexBufferObjectManager());
		sTorikeratps_kids.setpY(-100);
		sTorikeratps_kids.setSprite(sTamago1);
		AllKidMap.put(AllKidMap.size(), sTorikeratps_kids);
		this.mScene.registerTouchArea(sTorikeratps_kids);
		//sTorikeratps_kids.set

		// =============== belong BG4===================//
		sBackground4 = new Sprite(0, 0, ttrBackground4, this.getVertexBufferObjectManager());
		sPrakiozaul = new AnimatedSprite(-824, -10, ttrPrakiozaul, this.getVertexBufferObjectManager());
		sPrakiozaul.animate(new long[] { 400, 400 }, new int[] { 0, 1 }, -1);
		sPrakiozaulKidA = new Sprite(-250, -12, ttrPrakiozaulKidA, this.getVertexBufferObjectManager());
		sPrakiozaulKidB = new Sprite(-250, -12, ttrPrakiozaulKidB, this.getVertexBufferObjectManager());
		sPrakiozaulKidA.setVisible(false);
		sPrakiozaulKidB.setVisible(false);

		sSutegozauls_kids = new KidAnimatedSprite(-686, 382, ttrSutegozauls_kids, this.getVertexBufferObjectManager());
		sSutegozauls_kids.setpY(50);
		sSutegozauls_kids.setSprite(sTamago2);
		AllKidMap.put(AllKidMap.size(), sSutegozauls_kids);
		this.mScene.registerTouchArea(sSutegozauls_kids);
		
		sTrikeratpsB = new AnimatedSprite(-920, 224, ttrTrikeratpsB, this.getVertexBufferObjectManager()); // -805
		sTrikeratpsB.setVisible(false);

		sTrikeratpsA = new AnimatedSprite(-505, 207, ttrTrikeratpsA, this.getVertexBufferObjectManager());
		sTrikeratpsA.animate(new long[] { 300, 300 }, new int[] { 0, 1 }, -1);

		sTrikeratpsAB = new AnimatedSprite(-757, 204, ttrTrikeratpsAB, this.getVertexBufferObjectManager());
		sTrikeratpsAB.setVisible(false);

		sTamago3 = new AnimatedSprite(190, 554, ttrTamago3, this.getVertexBufferObjectManager());
		sTamago5 = new AnimatedSprite(420, 350, ttrTamago5, this.getVertexBufferObjectManager());
		sTamago6 = new AnimatedSprite(600, 269, ttrTamago6, this.getVertexBufferObjectManager());
		sPrakiozaulKid = new KidAnimatedSprite(86, 547, ttrPurakiozaulKid, this.getVertexBufferObjectManager());
		sPrakiozaulKid.setpY(-100);
		sPrakiozaulKid.setSprite(sTamago3);
		AllKidMap.put(AllKidMap.size(), sPrakiozaulKid);
		this.mScene.registerTouchArea(sPrakiozaulKid);
		sTiranozaul = new AnimatedSprite(-204, 81, ttrTiranozaul, this.getVertexBufferObjectManager());
		sTiranozaul.animate(new long[] { 400, 400 }, new int[] { 0, 1 }, -1);
		sTiranozaulKid = new KidAnimatedSprite(420, 367, ttrTiranozaulKid, this.getVertexBufferObjectManager());
			sTiranozaulKid.setpY(50);
			sTiranozaulKid.setSprite(sTamago5);
			this.mScene.registerTouchArea(sTiranozaulKid);
			AllKidMap.put(AllKidMap.size(), sTiranozaulKid);
		sTiranozaulsNiku = new Sprite(-204, 0, ttrTiranozaulsNiku, this.getVertexBufferObjectManager());
		sTiranozaulsNiku.setVisible(false);

		sOviraputoru = new AnimatedSprite(435, 403, ttrOviraputoru, this.getVertexBufferObjectManager());
		sOviraputoru.animate(new long[] { 300, 300 }, new int[] { 0, 1 }, -1);

		sTamago4 = new AnimatedSprite(-107, 355, ttrTamago4, this.getVertexBufferObjectManager());
		sOviraputoruKid = new KidAnimatedSprite(-106, 375, ttrOviraputoruKid, this.getVertexBufferObjectManager());
		sOviraputoruKid.setpY(60);
		sOviraputoruKid.setSprite(sTamago4);
		this.mScene.registerTouchArea(sOviraputoruKid);
		AllKidMap.put(AllKidMap.size(), sOviraputoruKid);
		
		sKi_temae = new AnimatedSprite(-460, 121, ttrKi_temae, this.getVertexBufferObjectManager());
		sAnkirosaurKid = new KidAnimatedSprite(569, 286, ttrAnkirosaurKid, this.getVertexBufferObjectManager());
		sAnkirosaurKid.setpY(110);
		sAnkirosaurKid.setSprite(sTamago6);
		this.mScene.registerTouchArea(sAnkirosaurKid);
		AllKidMap.put(AllKidMap.size(), sAnkirosaurKid);
		
		sCenter = new AnimatedSprite(150, 97, ttrCenter, this.getVertexBufferObjectManager());
		sSutegozauls = new AnimatedSprite(359, 164, ttrSutegozauls, this.getVertexBufferObjectManager());
		sSutegozauls.animate(new long[] { 300, 300 }, new int[] { 0, 1 }, -1);
		sPteranodon = new AnimatedSprite(50, 28, ttrPteranodon, this.getVertexBufferObjectManager());
		pteranodonBase();

		mPortalATopAniSprite = new AnimatedSprite(-91, 0,
				mPortalTopTiledTextureRegion, this.getVertexBufferObjectManager());
		mPortalABackAniSprite = new AnimatedSprite(-91, 0,
				mPortalBackTiledTextureRegion, this.getVertexBufferObjectManager());
		mPortalBTopAniSprite = new AnimatedSprite(-91, 0,
				mPortalTopTiledTextureRegion.deepCopy(), this.getVertexBufferObjectManager());
		mPortalBBackAniSprite = new AnimatedSprite(869, 0,
				mPortalBackTiledTextureRegion.deepCopy(), this.getVertexBufferObjectManager());
		
		mWaterASprite = new Sprite(0, 0, mWaterTextureRegion, this.getVertexBufferObjectManager());
		mWaterBSprite = new Sprite(0, 0, mWaterTextureRegion.deepCopy(), this.getVertexBufferObjectManager());

		// /////////////////////////////////////////////////////////////////////////////////////////

		sBG1 = new Sprite(960, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sBG1.setAlpha(0.0f);
		sBG1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		sBG1.attachChild(sBackground1);
		sBG1.attachChild(sKirin);
		mVaCham = new Rectangle(750, 325, 2, 320, this.getVertexBufferObjectManager());
		mVaCham.setAlpha(0.0f);
		sBG1.attachChild(mVaCham);
		sBG1.attachChild(mWaterASprite);
		sBG1.attachChild(mWaterBSprite);
		mWaterASprite.setVisible(false);
		mWaterBSprite.setVisible(false);
		/* sBG1.attachChild(mPortalOneSprite); */
		// sBG1.attachChild(mPortalBBackAniSprite);

		mScene.attachChild(sBG1);

		// =============================================//
		sBG2 = new Sprite(-1920, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sBG2.setAlpha(0.0f);
		sBG2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		sBG2.attachChild(sBackground2);
		
		sBG2.attachChild(sSimauma2);
		sBG2.attachChild(sSimauma1);	
		sBG2.attachChild(sJakkal);	
		sBG2.attachChild(sJakkalSprite);	
		sBG2.attachChild(sZou);

		sBG2.attachChild(sDacho);
		sBG2.attachChild(sJakkalKid);
		sBG2.attachChild(sZouKids);
		sBG2.attachChild(sSimaumaKids);
		sBG2.attachChild(sDachoKids);
		sBG2.attachChild(sKirinKids);
		mScene.attachChild(sBG2);
		// ===========================================//

		// ===========================================//
		sBG3 = new Sprite(-960, 0, 1156, 640, ttrBG1, this.getVertexBufferObjectManager());
		sBG3.setAlpha(0.0f);
		sBG3.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		sBG3.attachChild(sBackground3);
		
	

		sBG3.attachChild(sTamago1);

		sBG3.attachChild(mPortalABackAniSprite);
		/* sBG3.attachChild(mPortalTopAniSprite); */

		mScene.attachChild(sBG3);
		// ===========================================//

		sBG4 = new Sprite(0, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sBG4.setAlpha(0.0f);
		sBG4.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		sBG4.attachChild(sBackground4);
		sBG4.attachChild(sTamago3);
		

		sBG4.attachChild(sPrakiozaul);
		sBG4.attachChild(sPrakiozaulKidA);
		sBG4.attachChild(sPrakiozaulKidB);

		sBG4.attachChild(sTrikeratpsB);
		sBG4.attachChild(sTrikeratpsA);
		sBG4.attachChild(sTrikeratpsAB);

		sBG4.attachChild(sTiranozaul);
		sBG4.attachChild(sSutegozauls);
		sBG4.attachChild(sCenter);
		sBG4.attachChild(sTamago5);
		sBG4.attachChild(sTamago4);
		sBG4.attachChild(sTamago2);
		sBG4.attachChild(sOviraputoruKid);
		sBG4.attachChild(sSutegozauls_kids);
		sBG4.attachChild(sTamago6);
		sBG4.attachChild(sAnkirosaurKid);
		sBG4.attachChild(sTiranozaulKid);
		sBG4.attachChild(sTiranozaulsNiku);
		sBG4.attachChild(sPteranodon);

		// AnimatedSprite mPortal1 = new AnimatedSprite(869, 0,
		// mPortalBackTiledTextureRegion.clone());
		sBG4.attachChild(mPortalBBackAniSprite);

		/* sBG4.attachChild(mPortalBBackAniSprite); */

		mScene.attachChild(sBG4);

		// ==================================================//
		sBoyGirl = new AnimatedSprite(480, 299, ttrBoyGirl, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoyGirl);
		sBoyGirl.animate(new long[] { 200, 200, 200, 200, 200, 200, 200, 200 },
				new int[] { 0, 1, 2, 3, 4, 5, 6, 7 }, -1);
		// ============= TOP Layout =====================

		// T1
		sTBG1 = new Sprite(960, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sTBG1.setAlpha(0.0f);
		sTBG1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sTBG1);
		
		// sTBG1.attachChild(mPortalOneSprite);
		// sTBG1.attachChild(mPortalTwoSprite);
		/*
		 * sTBG1.attachChild(mPortalLeftTopSprite);
		 * sTBG1.attachChild(mPortalRightTopSprite);
		 */
		sTBG1.attachChild(mPortalBTopAniSprite);

		// T2
		sTBG2 = new Sprite(960, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sTBG2.setAlpha(0.0f);
		sTBG2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		mScene.attachChild(sTBG2);
		sTBG2.attachChild(sGazelKids);
		sTBG2.attachChild(sLionKids);
		sTBG2.attachChild(sLion);
		sTBG2.attachChild(sGazel);

		// T3
		sTBG3 = new Sprite(960, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sTBG3.setAlpha(0.0f);
		sTBG3.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sTBG3);
		

		/* sTBG3.attachChild(mPortalLeftTopSprite); */
		sTBG3.attachChild(mPortalATopAniSprite);

		// T4
		sTBG4 = new Sprite(960, 0, 960, 640, ttrBG1, this.getVertexBufferObjectManager());
		sTBG4.setAlpha(0.0f);
		sTBG4.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mScene.attachChild(sTBG4);

		
		sTBG4.attachChild(sOviraputoru);
		sTBG4.attachChild(sPrakiozaulKid);
		sTBG4.attachChild(sTorikeratps_kids);
		sTBG4.attachChild(sAnkirosaur);
		sTBG4.attachChild(sKi_temae);

		mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		addGimmicsButton(mScene, Vol3SanPoResource.SOUND_GIMMIC, new int[]{
				Vol3SanPoResource.CoDaiC.A3_WARP_BT_IPHONE_ID, Vol3SanPoResource.CoDaiC.A3_WARP_BT_IPHONE_ID, Vol3SanPoResource.CoDaiC.A3_WARP_BT_IPHONE_ID
		},mCoDaiCTexturePackLib);
		
sprGimmic[0].setVisible(false);
sprGimmic[1].setVisible(false);
setTouchGimmic(0, false);
setTouchGimmic(1, false);
		
		KidLinkedRoot = new LinkedList<Vol3Sanpo.KidAnimatedSprite>();
		STATUS = 1;
	}
	
	IUpdateHandler updaterHandler = new IUpdateHandler() {
		final float step = 1;

		@Override
		public void reset() {

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {

			// =============================
			
		/*	if(isWarptouch){
				mPortalATopAniSprite.stopAnimation(0);
				mPortalABackAniSprite.stopAnimation(0);
				mPortalBTopAniSprite.stopAnimation(0);
				mPortalBBackAniSprite.stopAnimation(0);
				isWarptouch = false;
			}*/
			
			if (checkBoyGirlinWarp(mPortalATopAniSprite)) {
				if (!mPortalATopAniSprite.isAnimationRunning()) {
					A3_WARP.play();
					AnimatedDefaultAction(mPortalATopAniSprite);
					AnimatedDefaultAction(mPortalABackAniSprite);
					STATUS = 0;
				}
			} else {
				if (mPortalATopAniSprite.isAnimationRunning()) {
					if(!isWarptouch){
					mPortalATopAniSprite.stopAnimation(0);
					mPortalABackAniSprite.stopAnimation(0);
					}
				}
			}
			// ==================================
			if (checkBoyGirlinWarp(mPortalBTopAniSprite)) {
				if (!mPortalBTopAniSprite.isAnimationRunning()) {
					A3_WARP.play();
					AnimatedDefaultAction(mPortalBTopAniSprite);
					AnimatedDefaultAction(mPortalBBackAniSprite);
					STATUS = 1;
				}
			} else {
				if (mPortalBTopAniSprite.isAnimationRunning()) {
					if(!isWarptouch){
						mPortalBTopAniSprite.stopAnimation(0);
						mPortalBBackAniSprite.stopAnimation(0);
					}
		
				}
			}
			
			if(mVaCham.collidesWith(sBoyGirl)){
				STATUS = 2;
			}
			
			/*Log.d(TAG,"BG1     " + sBG1.getX());
			Log.d(TAG,"BG2     " + sBG2.getX());
			Log.d(TAG,"BG3     " + sBG3.getX());
			Log.d(TAG,"BG4     " + sBG4.getX());
			
			Log.d(TAG,"sTBG1     " + sTBG1.getX());
			Log.d(TAG,"sTBG2     " + sTBG2.getX());
			Log.d(TAG,"sTBG3     " + sTBG3.getX());
			Log.d(TAG,"sTBG4     " + sTBG4.getX());*/
			
			//
			Iterator<KidAnimatedSprite> KidLinkedRoottIterator = KidLinkedRoot
					.iterator();
			KidAnimatedSprite _KidSprite;

			while (KidLinkedRoottIterator.hasNext()) {
				_KidSprite = KidLinkedRoottIterator.next();
				
				if(_KidSprite.isMove){
					float pX = _KidSprite.getX();
					_KidSprite.setPosition(pX -= step, _KidSprite.mPy);
				}
				
				if(mVaCham.collidesWith(_KidSprite)){
					_KidSprite.setDisMove();
				}

				
				if(mPortalATopAniSprite.collidesWith(_KidSprite)){
					_KidSprite.setDisMove();
				}
			}
			

			float pFirst = sBG1.getX();
			sBG1.setPosition(pFirst += step, 0);
			sTBG1.setPosition(pFirst += step, 0);

			float pFouths = sBG4.getX();
			sBG4.setPosition(pFouths += step, 0);
			sTBG4.setPosition(pFouths += step, 0);

			float pThirds = sBG3.getX();
			sBG3.setPosition(pThirds += step, 0);
			sTBG3.setPosition(pThirds += step, 0);

			float pSeconds = sBG2.getX();
			sBG2.setPosition(pSeconds += step, 0);
			sTBG2.setPosition(pSeconds += step, 0);

			if (sBG1.getX() == 960) {
				sBG4.setPosition(0, 0);
				sTBG4.setPosition(0, 0);
			}
			if ((int) sBG4.getX() == 0) {
				sBG3.setPosition(-960, 0);
				sTBG3.setPosition(-960, 0);
			}
			if ((int) sBG3.getX() == 0) {
				sBG2.setPosition(-960, 0);
				sTBG2.setPosition(-960, 0);
			}
			if ((int) sBG2.getX() == 0) {
				sBG1.setPosition(-960, 0);
				sTBG1.setPosition(-960, 0);
			}
			if ((int) sBG1.getX() == 0) {
				sBG4.setPosition(-960, 0);
				sTBG4.setPosition(-960, 0);
			}
		}
	};
	
	@Override
	protected void loadKaraokeComplete() {
		super.loadKaraokeComplete();
	}
	
	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		mScene.registerUpdateHandler(updaterHandler);
		isSwitch = false;
		Log.d(TAG,"onResumeGame");
	}

	@Override
	public void onPauseGame() {
		Log.d(TAG,"onPauseGame");
		mScene.unregisterUpdateHandler(updaterHandler);
		this.mScene.clearUpdateHandlers();
		resetGame();
		super.onPauseGame();
	}
	
	private void resetGame(){
		sBG1.setPosition(960, 0);
		sBG2.setPosition(-1920, 0);
		sBG3.setPosition(-960, 0);
		sBG4.setPosition(0, 0);
		sTBG1.setPosition(960, 0);
		sTBG2.setPosition(960, 0);
		sTBG3.setPosition(960, 0);
		sTBG4.setPosition(960, 0);
		STATUS = 1;
		sTamago1.setCurrentTileIndex(0);
		sTamago2.setCurrentTileIndex(0);
		sTamago3.setCurrentTileIndex(0);
		sTamago4.setCurrentTileIndex(0);
		sTamago5.setCurrentTileIndex(0);
		sTamago6.setCurrentTileIndex(0);
		
		for(int i =0; i< KidLinkedRoot.size(); i++){
			KidLinkedRoot.get(i).reload();
			Log.d("TAG", "reload" + i);
		}	
		KidLinkedRoot.clear();
		
		reloadAllBaby();
		
	}
	
	private void reloadAllBaby(){
		for(int z= 0; z< AllKidMap.size(); z++){
			AllKidMap.get(z).reload();
		}
	}
	
	@Override
	public void combineGimmic3WithAction() {
		if(!isSwitch){
			isSwitch = true;
				this.mScene.clearUpdateHandlers();
				A3_BUSUPYU.play();
				this.mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						//============================
						for(int z =0; z< KidLinkedRoot.size(); z++){
							KidLinkedRoot.get(z).reload();
							Log.d("TAG", "reload" + z);
						}	
						KidLinkedRoot.clear();
						reloadAllBaby();
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								if(STATUS == 1){
									sBG1.setPosition(2876, 0);
									sBG2.setPosition(-4, 0);
									sBG3.setPosition(956, 0);
									sBG4.setPosition(1916, 0);
									sTBG1.setPosition(2876, 0);
									sTBG2.setPosition(-4, 0);
									sTBG3.setPosition(956, 0);
									sTBG4.setPosition(1916, 0);
									STATUS =0;
									Vol3Sanpo.this.mScene.registerUpdateHandler(updaterHandler);
								}else{
									sBG1.setPosition(960, 0);
									sBG2.setPosition(-1920, 0);
									sBG3.setPosition(-960, 0);
									sBG4.setPosition(0, 0);
									sTBG1.setPosition(960, 0);
									sTBG2.setPosition(960, 0);
									sTBG3.setPosition(960, 0);
									sTBG4.setPosition(960, 0);
									Vol3Sanpo.this.mScene.registerUpdateHandler(updaterHandler);
									STATUS = 1;
								}
								isSwitch = false;
							}
						});
						//===================
					}
				}));
				
		}			
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();

		if (pSceneTouchEvent.isActionDown()) {
	
			int pXX = (pX - (int)sBG1.getX());
			
			if(pXX >= 435 && pXX <=611 && pY >= 390 && pY <= 447 && !mWaterBSprite.isVisible()){
				waterAction(pX - (int)sBG1.getX(),pY);
				return true;
			}else
			if(pXX >= 500 && pXX <=639 && pY >= 563 && pY <= 603 && !mWaterBSprite.isVisible()){
				waterAction(pX - (int)sBG1.getX(),pY);
				return true;
			}else
			if (sSimauma1.contains(pX, pY) && sSimauma1.isVisible()) {
				
				A3_23_SHIMAUMA_VO.play(); 
				A3_23_SHIMAUMA_RUN.play();
				sSimauma1.setVisible(false);
				sSimauma2.registerEntityModifier(new MoveXModifier(3.0f,
						sSimauma2.getX(), -960 - sSimauma2.getWidth(),
						new IEntityModifierListener() {

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								sSimauma1
										.registerEntityModifier(new MoveXModifier(
												0.7f, 960, sSimauma1.getX()));
								sSimauma1.setVisible(true);

								runOnUpdateThread(new Runnable() {

									@Override
									public void run() {
										sSimauma2.clearEntityModifiers();
										sSimauma2.resetLocalToFirst();
										sSimauma2.reset();
										sSimauma2.setVisible(false);
									}
								});
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
							}
						}));
				sSimauma2.setVisible(true);
			} else if (sDacho.contains(pX, pY) && sDacho.getCurrentTileIndex() <2) {

				A3_26_DACYO_VO.play();
				A3_26_DACYO_NAKU.play();
				A3_26_DACYO_ASHI.play();

				sDacho.stopAnimation();
				sDacho.animate(new long[] { 200, 200, 200, 200, 200, 200, 200,
						200 }, new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, -1);
				sDacho.registerEntityModifier(new SequenceEntityModifier(
						new MoveXModifier(1.5f, sDacho.getX(), - 300),
						new DelayModifier(0.5f, new IEntityModifierListener() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								sDacho.stopAnimation(0);
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								runOnUpdateThread(new Runnable() {

									@Override
									public void run() {
										sDacho.clearEntityModifiers();
										sDacho.stopAnimation();
										sDacho.resetLocalToFirst();
										sDacho.reset();
										AnimatedDefaultAction(sDacho);
									}
								});
							}
						})
						));
			} else if (sLion.contains(pX, pY)
					&& sLion.getCurrentTileIndex() < 2) {

				sLion.stopAnimation();
				A3_24_RION_VO.play();
				A3_24_RION_NAKU.play();
				sLion.animate(new long[] { 150, 150, 250, 250, 250, 250 },
						new int[] { 2, 3, 4, 5, 4, 5 }, 0,
						new IAnimationListener() {

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								AnimatedDefaultAction(sLion);
							}

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
									int paramInt1, int paramInt2) {}
						});

			}
			if (sGazel.contains(pX, pY) && sGazel.getCurrentTileIndex() < 2) {
				sGazel.stopAnimation();
				A3_25_GAZERU_VO.play();
				A3_25_GAZERU_FLY.play();
				sGazel.registerEntityModifier(new MoveYModifier(0.2f, sGazel.getY(),  sGazel.getY() - 50));
				sGazel.animate(new long[] { 1000 }, new int[] { 2 }, 0,
						new IAnimationListener() {

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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
										runOnUpdateThread(new Runnable() {
												
												@Override
												public void run() {
													sGazel.clearEntityModifiers();
													sGazel.resetLocalToFirst();
													sGazel.reset();
													AnimatedDefaultAction(sGazel);
												}
											});
							}
						});
			} else if (sZou.contains(pX, pY) && sZou.getCurrentTileIndex() < 2) {

				A3_27_ZOU_VO.play();
				A3_27_ZOU_NAKU.play();
				sZou.stopAnimation();
				sZou.animate(new long[] { 1000 }, new int[] { 2 }, 0,
						new IAnimationListener() {

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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								AnimatedDefaultAction(sZou);
							}
						});
			} else if (sKirin.contains(pX, pY)
					&& sKirin.getCurrentTileIndex() < 2) {

				A3_22_KIRIN_VO.play();
				A3_22_KIRIN.play();

				sKirin.animate(new long[] { 1000 }, new int[] { 2 }, 0,
						new IAnimationListener() {

							@Override
							public void onAnimationFinished(
									AnimatedSprite pAnimatedSprite) {
								AnimatedDefaultAction(sKirin);
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
			} else if (sTrikeratpsA.contains(pX, pY)  && !sTrikeratpsB.isVisible() && !sTrikeratpsAB.isVisible()) {
				
				A3_31_TORIKERA_VO.play();
				A3_31_TORIKERAZA.play();
				A3_31_TORIKERAZA3.play();
				sTrikeratpsA.animate(new long[] { 250, 250 },
						new int[] { 2, 3 }, -1);
				sTrikeratpsB.animate(new long[] { 300, 300 },
						new int[] { 0, 1 }, -1);
				sTrikeratpsB.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.75f),
						new MoveXModifier(0.3f,
								-920, -805, new IEntityModifierListener() {

									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier, IEntity pItem) {
										runOnUpdateThread(new Runnable() {

											@Override
											public void run() {
												sTrikeratpsB.stopAnimation();
												sTrikeratpsB.clearEntityModifiers();
												sTrikeratpsB.resetLocalToFirst();
												sTrikeratpsB.reset();
												sTrikeratpsB.setVisible(false);
												sTrikeratpsA.setVisible(false);
												sTrikeratpsAB.setVisible(true);
												A3_31_TORIKERATSUNO.play();
												sTrikeratpsAB.animate(new long[] { 300,
														300, 500 },
														new int[] { 0, 1, 2 }, 0,
														new IAnimationListener() {

															@Override
															public void onAnimationFrameChanged(
																	AnimatedSprite arg0,
																	int arg1, int arg2) {
																
																
															}

															@Override
															public void onAnimationLoopFinished(
																	AnimatedSprite arg0,
																	int arg1, int arg2) {
																
																
															}

															@Override
															public void onAnimationStarted(
																	AnimatedSprite arg0,
																	int arg1) {
																
																
															}

															@Override
															public void onAnimationFinished(
																	AnimatedSprite paramAnimatedSprite) {
																sTrikeratpsAB
																.setVisible(false);
														sTrikeratpsA
																.animate(
																		new long[] {
																				300,
																				300 },
																		new int[] {
																				0,
																				1 },
																		-1);
														sTrikeratpsA
																.setVisible(true);
															}
														});

											}
										});

									}

									@Override
									public void onModifierStarted(
											IModifier<IEntity> arg0, IEntity arg1) {
										
										
									}
								})
						));
				//sTrikeratpsB.registerEntityModifier();
				sTrikeratpsB.setVisible(true);

			} else if (checkContains(sBoyGirl, 72, 12, 162, 185, pX, pY)
					&& isGirlBoy) {
				A3_13_BOY.play();
				isGirlBoy = false;
				sBoyGirl.animate(new long[] { 1250 }, new int[] { 8 }, 0,
						new IAnimationListener() {

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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								isGirlBoy = true;
								sBoyGirl.animate(new long[] { 200, 200, 200,
										200, 200, 200, 200, 200 }, new int[] {
										0, 1, 2, 3, 4, 5, 6, 7 }, -1);
							}
						});
			} else if (checkContains(sBoyGirl, 188, 18, 276, 185, pX, pY)
					&& isGirlBoy) {
				A3_14_GIRL.play();
				isGirlBoy = false;
				sBoyGirl.animate(new long[] { 750 }, new int[] { 9 }, 0,
						new IAnimationListener() {

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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								isGirlBoy = true;
								sBoyGirl.animate(new long[] { 200, 200, 200,
										200, 200, 200, 200, 200 }, new int[] {
										0, 1, 2, 3, 4, 5, 6, 7 }, -1);
							}
						});
			} else if (sOviraputoru.contains(pX, pY) && sOviraputoru.getCurrentTileIndex() < 2) {

				A3_12_OVIRA_VO.play();
				A3_12_OVIRA_NAKU.play();
				A3_12_OVIRA_ASHI.play();

				sOviraputoru.animate(
						new long[] { 250, 250, 250, 250, 250, 250 }, new int[] {
								2, 3, 4, 5, 6, 7 }, -1);
				sOviraputoru.registerEntityModifier(new MoveXModifier(3.0f,
						sOviraputoru.getX(), -sOviraputoru.getWidth() * 4,
						new IEntityModifierListener() {

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								runOnUpdateThread(new Runnable() {

									@Override
									public void run() {
										sOviraputoru.clearEntityModifiers();
										sOviraputoru.resetLocalToFirst();
										sOviraputoru.reset();
										sOviraputoru.animate(new long[] { 300,
												300 }, new int[] { 0, 1 }, -1);
									}
								});

							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								
								
							}
						}));
			} else if (sAnkirosaur.contains(pX, pY) && sAnkirosaur.getCurrentTileIndex() <2 ) {

				A3_15_ANKI_VO.play();
				A3_15_ANKI_SHIPO.play();
				A3_15_ANKI_NAKU.play();

				sAnkirosaur.animate(
						new long[] { 200, 200, 200, 200, 200, 200 }, new int[] {
								2, 3, 4, 5, 6, 7 }, 0,
						new IAnimationListener() {

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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								sAnkirosaur.animate(new long[] { 300, 300 },
										new int[] { 0, 1 }, -1);
							}
						});
			} else if (sSutegozauls.contains(pX, pY) && sSutegozauls.getCurrentTileIndex() <2) {

				A3_29_SUTEGO_VO.play();
				A3_29_SUTEGO_EAT.play();
				A3_29_SUTEGO_NAKU.play();

				sSutegozauls.animate(new long[] { 1500 }, new int[] { 2 }, 0,
						new IAnimationListener() {


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

							@Override
							public void onAnimationFinished(
									AnimatedSprite paramAnimatedSprite) {
								sSutegozauls.animate(new long[] { 300, 300 },
										new int[] { 0, 1 }, -1);
							}
						});
			} else if (checkContains(sTiranozaul, 26, 80, 408, 299, pX - (int)sBG4.getX(), pY) && sTiranozaul.getCurrentTileIndex() <2 
					&& !sTiranozaulsNiku.isVisible()) {

				sTiranozaul.setCurrentTileIndex(2);
				A3_30_TIRANO_VO.play();
				A3_NIKUOCHI2.play();
				A3__30_NIKUOCHI2.play();

				sTiranozaulsNiku.registerEntityModifier(new MoveYModifier(0.7f,
						0, 330, new IEntityModifierListener() {

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								sTiranozaulsNiku.setVisible(false);
								A3_30_TIRANO_EAT.play();
								sTiranozaul.animate(new long[] { 300, 300 },
										new int[] { 3, 4 }, 2,
										new IAnimationListener() {

											@Override
											public void onAnimationFrameChanged(
													AnimatedSprite arg0,
													int arg1, int arg2) {
												
												
											}

											@Override
											public void onAnimationLoopFinished(
													AnimatedSprite arg0,
													int arg1, int arg2) {
												
												
											}

											@Override
											public void onAnimationStarted(
													AnimatedSprite arg0,
													int arg1) {
												
												
											}

											@Override
											public void onAnimationFinished(
													AnimatedSprite paramAnimatedSprite) {
												A3_30_TIRANO_NAKU.play();
												sTiranozaul
														.animate(
																new long[] { 750 },
																new int[] { 2 },
																0,
																new IAnimationListener() {

																
																	@Override
																	public void onAnimationFrameChanged(
																			AnimatedSprite arg0,
																			int arg1,
																			int arg2) {
																		
																		
																	}

																	@Override
																	public void onAnimationLoopFinished(
																			AnimatedSprite arg0,
																			int arg1,
																			int arg2) {
																		
																		
																	}

																	@Override
																	public void onAnimationStarted(
																			AnimatedSprite arg0,
																			int arg1) {
																		
																		
																	}

																	@Override
																	public void onAnimationFinished(
																			AnimatedSprite paramAnimatedSprite) {
																		AnimatedDefaultAction(sTiranozaul);
																	}
																});
											}
										});
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
								
								
							}
						}));
				sTiranozaulsNiku.setVisible(true);

			} else if (sPteranodon.contains(pX, pY) && sPteranodon.getCurrentTileIndex() <2) {
				sPteranodon.stopAnimation();
				sPteranodon.setScale(1.4f);
				sPteranodon.animate(new long[] { 300, 300 },
						new int[] { 2, 3 }, -1);
				A3_34_PUTERA_VO.play();
				A3_34_PUTERA.play();

				sPteranodon.registerEntityModifier(new MoveXModifier(2.3f,
						sPteranodon.getX(), sPteranodon.getX() - 960
								- sPteranodon.getWidth() * 2,
						new IEntityModifierListener() {

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {
								runOnUpdateThread(new Runnable() {

									@Override
									public void run() {
										sPteranodon.clearEntityModifiers();
										sPteranodon.stopAnimation();
										sPteranodon.setScale(1.0f);
										sPteranodon.resetLocalToFirst();
										sPteranodon.reset();
										pteranodonBase();
									}
								});
							}

							@Override
							public void onModifierStarted(
									IModifier<IEntity> arg0, IEntity arg1) {
		
							}
						}));
			} else if (checkContains(sPrakiozaul, 26, 36, 898, 167,pX - (int) sBG4.getX(), pY)
					&& sPrakiozaul.isAnimationRunning()) {
				Log.d(TAG,"xxxxxxxxxx");
				sPrakiozaul.stopAnimation(2);
				sPrakiozaulKidA.setVisible(true);
				sPrakiozaulKidB.setVisible(true);

				A3_32_BURAKIO_VO.play();
				A3_32_BURAKIO_OCHIRU.play();

				sPrakiozaulKidA.registerEntityModifier(new MoveModifier(1.5f,
						sPrakiozaulKidA.getX(), 300, sPrakiozaulKidA.getY(),
						200));
				sPrakiozaulKidA.registerEntityModifier(new SequenceEntityModifier(
						new MoveModifier(1.5f,sPrakiozaulKidA.getX(), 300, sPrakiozaulKidA.getY(),200),
						new RotationModifier(0.2f, 0, -30)
						));
				sPrakiozaulKidB.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								sPrakiozaulKidA.clearEntityModifiers();
								sPrakiozaulKidB.clearEntityModifiers();
								sPrakiozaulKidA.resetLocalToFirst();
								sPrakiozaulKidB.resetLocalToFirst();
								sPrakiozaulKidA.reset();
								sPrakiozaulKidB.reset();
								sPrakiozaulKidA.setVisible(false);
								sPrakiozaulKidB.setVisible(false);
								sPrakiozaul.animate(new long[] { 400,
										400 }, new int[] { 0, 1 }, -1);
							}
						});
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						
						
					}
				}, new MoveModifier(1.5f,sPrakiozaulKidB.getX(), -800, sPrakiozaulKidB.getY(),180),
				  new RotationModifier(0.2f, 0, 30)
				));
				
			}else if(sJakkal.contains(pX, pY) && sJakkal.getCurrentTileIndex() < 2 && !sJakkalSprite.isVisible()){
				final float y = sJakkal.getY() + sJakkal.getHeight() - sJakkalSprite.getHeight();
				sJakkalSprite.setVisible(true);
				sJakkalSprite.registerEntityModifier(new MoveYModifier(1.0f, 0, y, new IEntityModifierListener() {
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								sJakkalSprite.setVisible(false);
								sJakkalSprite.resetLocalToFirst();
								sJakkalSprite.reset();
								sJakkalSprite.setVisible(false);
							}
						});
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}
				}));
				
				A3_21_JAKARU_VO.play();
				A3_NIKUOCHI2.play();
				playSoundDelay(A3_21_JAKARU_EAT, 1.0f);
				sJakkal.stopAnimation();
				sJakkal.animate( new long[]{250,250,300,200,250,250,250,250,250,250}, new int[]{0,1,0,2,3,4,3,4,3,4},0, new IAnimationListener() {
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite arg0,
							int arg1, int arg2) {
		
					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite arg0,
							int arg1, int arg2) {
			
					}

					@Override
					public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
	
					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite paramAnimatedSprite) {
						AnimatedDefaultAction(sJakkal);
					}
				});
			}else if(mPortalATopAniSprite.contains(pX, pY)){
				warptouch(0);
			}else if(mPortalBTopAniSprite.contains(pX, pY)){
				warptouch(1);
			}
		}
		return true;
	}
	
	private void waterAction(float pX, float pY){
			mWaterASprite.setPosition(pX - mWaterASprite.getWidth()/2, pY - mWaterASprite.getHeight()/2);
			A3_CHYAPA.play();
			mWaterASprite.registerEntityModifier(new ScaleModifier(0.5f, 0.3f, 0.5f, new IEntityModifierListener() {
			
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
						}
					});
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
					mWaterASprite.setVisible(true);
				}
			}));
			
			mWaterBSprite.setPosition(pX - mWaterBSprite.getWidth()/2, pY - mWaterBSprite.getHeight()/2);
			
			mWaterBSprite.registerEntityModifier(
					new DelayModifier(0.5f, new IEntityModifierListener() {
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							mWaterBSprite.registerEntityModifier(new ScaleModifier(0.8f, 0.5f, 1.0f, new IEntityModifierListener() {
								
								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
									runOnUpdateThread(new Runnable() {
										  
										@Override
										public void run() {
											mWaterBSprite.setVisible(false);
											mWaterBSprite.clearEntityModifiers();
											mWaterBSprite.resetLocalToFirst();
											mWaterBSprite.reset();
											mWaterBSprite.setVisible(false);
											mWaterASprite.setVisible(false);
											mWaterASprite.clearEntityModifiers();
											mWaterASprite.resetLocalToFirst();
											mWaterASprite.reset();
											mWaterASprite.setVisible(false);
										}
									});
								}

								@Override
								public void onModifierStarted(
										IModifier<IEntity> arg0, IEntity arg1) {
									mWaterBSprite.setVisible(true);
								}
							}));
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
						}
					})
					);
	}

	private void AnimatedDefaultAction(AnimatedSprite pAni) {
		pAni.animate(new long[] { 650, 650 }, new int[] { 0, 1 }, -1);
	}

	protected float getXFromScene(final AnimatedSprite pAnimatedSprite) {
		return pAnimatedSprite.getX() + pAnimatedSprite.getParent().getX();
	}
	
	private void warptouch(int type){
		if(isWarptouch){
			return;
		}
		isWarptouch = true;
		A3_WARP.play();
		long duration[] = new long[]{300,300};
		int frame[] = new int[]{0,1};
		if(type == 0){
			Log.d(TAG,"warptouch000");
			mPortalATopAniSprite.animate(duration, frame, 2);
			mPortalABackAniSprite.animate(duration, frame, 2, new IAnimationListener() {


				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
					Log.d(TAG,"warptouch onAnimationStarted");
				}

				@Override
				public void onAnimationFinished(
						AnimatedSprite paramAnimatedSprite) {
					isWarptouch = false;
					mPortalATopAniSprite.setCurrentTileIndex(0);
					mPortalABackAniSprite.setCurrentTileIndex(0);
				}
			});
		
		}else if(type == 1){
			mPortalBTopAniSprite.animate(duration, frame, 2);
			mPortalBBackAniSprite.animate(duration, frame, 2, new IAnimationListener() {
			

				@Override
				public void onAnimationFrameChanged(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite arg0,
						int arg1, int arg2) {
				}

				@Override
				public void onAnimationStarted(AnimatedSprite arg0, int arg1) {}

				@Override
				public void onAnimationFinished(
						AnimatedSprite paramAnimatedSprite) {
					isWarptouch = false;
					mPortalBTopAniSprite.setCurrentTileIndex(0);
					mPortalBBackAniSprite.setCurrentTileIndex(0);
				}
			});
		}
		
	}

	protected float getXBoyGirlToSeft(final AnimatedSprite pAnimatedSprite) {
		return sBoyGirl.getX() - pAnimatedSprite.getParent().getX();
	}

	class KidAnimatedSprite extends AnimatedSprite {
		public boolean isFuture = false;
		private boolean isTouch = true;
		private boolean isMove = true;
		private float mPointerX = 0;
		private float mPointerY = 0;
		private float mPy = 0;
		private AnimatedSprite tmpSprite;

		public KidAnimatedSprite(float pX, float pY,
				TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBuffer) {
			super(pX, pY, pTiledTextureRegion, pVertexBuffer);
			this.mPointerX = pX;
			this.mPointerY = pY;
		}
		
		private void setSprite(AnimatedSprite pSprite){
			this.tmpSprite = pSprite;
			this.setVisible(false);
		}
		
		private void runWidthBodyGirl(float pickX){
			this.isTouch = false;
			AnimatedDefaultAction(this);
			float x = pickX - 50;
			if(this.equals(sSutegozauls_kids)){
				if( (this.getX() - x )>= 50){
					x += 50;
					Log.i(TAG, "okokokokokokok");
				}
			
			}
			this.registerEntityModifier(new MoveModifier(0.1f, mPointerX,
					 x, this.getY(), mPy, new IEntityModifierListener() {
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							KidLinkedRoot.add(KidAnimatedSprite.this);
							Log.d(TAG,"KidLinkedRoot" + KidLinkedRoot.size());
							KidAnimatedSprite.this.isMove = true;
							A3_11_BABY.stop();
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							
							
						}
					}));
		}
		
		private void futureAction(){
			Log.d(TAG,"Hien dai xx");
				this.isTouch = false;
				this.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								KidAnimatedSprite.this.resetLocalToFirst();
								KidAnimatedSprite.this.reset();
								KidAnimatedSprite.this.isTouch = true;
							}
						});
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						
						
					}
				}, new MoveYModifier(0.2f,this.getY(), this.getY() - 20),
				new MoveYModifier(0.2f,this.getY() - 20, this.getY())));
		}
		private void ancientAction(){
			tmpSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							tmpSprite.clearEntityModifiers();
							KidAnimatedSprite.this.tmpSprite.resetLocalToFirst();
							KidAnimatedSprite.this.tmpSprite.reset();
							KidAnimatedSprite.this.isTouch = true;
						}
					});
				}

				@Override
				public void onModifierStarted(IModifier<IEntity> arg0,
						IEntity arg1) {
					
					
				}
			}, new ScaleModifier(0.2f, 1.0f, 1.5f),
			new ScaleModifier(0.2f, 1.5f, 1.0f)));
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(pSceneTouchEvent.isActionDown() && this.isTouch){
				Log.d(TAG,"TAG: " + STATUS);
				final float pXX = getXBoyGirlToSeft(this);
				switch (STATUS) {
				case 0: // Hien dai
						if(this.isFuture){
								if(!getBoyGirlInGate()){
									A3_11_BABY.play();
									runWidthBodyGirl(pXX);
								}else{
									futureAction();
								}
						}else{
								A3_11_TAMAGO.play();
								ancientAction();
						}
					break;
				case 1: // Co dai
						if(!this.isFuture){
								if(!sBoyGirl.collidesWith(mPortalBTopAniSprite) && !checkBoyGirlinWarp(mPortalATopAniSprite)){
									this.isTouch = false;
									//Log.d(TAG,"xx " + tmpSprite.toString());
									A3_11_TAMAGO.play();
									tmpSprite.animate(new long[]{200}, new int[]{1},0, new IAnimationListener() {

										@Override
										public void onAnimationFinished(
												AnimatedSprite arg0) {
											KidAnimatedSprite.this.setVisible(true);

											Vol3Sanpo.this.mScene.registerUpdateHandler(new TimerHandler(0.7f, new ITimerCallback() {
												
												@Override
												public void onTimePassed(TimerHandler pTimerHandler) {
													
													A3_CHU.play();
													runWidthBodyGirl(pXX);
												}
											}));
										}

										@Override
										public void onAnimationFrameChanged(
												AnimatedSprite arg0, int arg1,
												int arg2) {
											
											
										}

										@Override
										public void onAnimationLoopFinished(
												AnimatedSprite arg0, int arg1,
												int arg2) {
											
											
										}

										@Override
										public void onAnimationStarted(
												AnimatedSprite arg0, int arg1) {
											
											
										}
									});
									
								}else{
									ancientAction();
								}
						}else{
							   futureAction();
						}
					break;	
				case 2:
						if(this.isFuture){
								futureAction();
						}else{
								ancientAction();
						}
					break;
				}	
			}
			return true;
		}
		
		
		
		public void setpY(float pPy){
			this.mPy = mPointerY + pPy;
		}
		
		public void setDisMove(){
			this.isMove = false;
			this.stopAnimation(0);
		}
		
		public void move(float pX){
			if(this.isMove)
				this.setPosition(pX, this.getY());
		}
		
		public void reload(){

			this.isTouch = true;
			KidLinkedRoot.remove(this);
			this.isMove = true;
			Log.d(TAG, this.getTag() + "x A reload x" + this.getX());
			this.resetLocalToFirst();
			Log.d(TAG, this.getTag() + "x B reload Fx" +this.getmXFirst() );
			Log.d(TAG, this.getTag() + "x C reload x" + getX());
			if(!this.isFuture){
				this.setVisible(false);
				tmpSprite.setCurrentTileIndex(0);
			}
			runOnUpdateThread(new Runnable() {
				
				@Override
				public void run() {
					KidAnimatedSprite.this.clearEntityModifiers();
					KidAnimatedSprite.this.resetLocalToFirst();
				}
			});
		
		}


		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
		
			if (getXFromScene(this) > 950 && !isTouch) {
				this.reload();
			}
			super.onManagedUpdate(pSecondsElapsed);
		}

	}
	
	private boolean getBoyGirlInGate(){
		if (!checkBoyGirlinWarp(mPortalATopAniSprite) && ! checkBoyGirlinWarp(mPortalBTopAniSprite))
				return false;
		return true;
	}
	
	private void pteranodonBase(){
		AnimatedDefaultAction(sPteranodon);
		sPteranodon.registerEntityModifier(
				new LoopEntityModifier(
						new SequenceEntityModifier(
								new ScaleModifier(0.5f, 1.0f, 1.3f),
								new ScaleModifier(0.5f, 1.3f, 1.0f),
								new DelayModifier(0.5f)
								)
						)
				);
	}
	
	private boolean checkBoyGirlinWarp(AnimatedSprite pAnimatedSprite){
			if(sBoyGirl.collidesWith(pAnimatedSprite)){
					if( (sBoyGirl.getX() + 70) <= (getXFromScene(pAnimatedSprite) + pAnimatedSprite.getWidth())
							&& getXFromScene(pAnimatedSprite) <= (sBoyGirl.getX() + 70)
							){
								return true;
					}
			}
			return false;
	}

}
