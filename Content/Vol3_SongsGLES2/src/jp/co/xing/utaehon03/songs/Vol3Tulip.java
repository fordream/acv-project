package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TulipResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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

public class Vol3Tulip extends BaseGameActivity implements IOnSceneTouchListener, IAnimationListener, IModifierListener<IEntity>{
        private String TAG_LOG = Vol3Tulip.this.getClass().toString();
        private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private ITextureRegion ttrBackground, ttrIe, ttrKadan, ttrSuberidai, ttrCyo,ttrBirdMother,ttrSuidou1, ttrSuidou11,ttrNezumi1,ttrNezumi2;
	private TiledTextureRegion ttrDango,ttrKaeru, ttrTori, ttrMogura,ttrRed, ttrWhite, ttrYellow, ttrMegane,
							   ttrPink, ttrBoy, ttrGirl,ttrLeft, ttrCenter, ttrRight,ttrSuidou2, ttrSuidou3, ttrSuidou4,ttrSuberidai2;
	private Sprite sIe, sKadan, sSuberidai, sCyo, sBirdMother,sSuidou1, sSuidou11,sNezumi,sNezumi2;
	private AnimatedSprite sDango, sKaeru, sTori, sMogura,  sRed, sWhite, sYellow, sPink, sBoy, sGirl,
							  sLeft, sCenter, sRight,sSuidou2, sSuidou3, sSuidou4, sSuberidai2, sMegane;
	
	private boolean isDango, isKaeru, isTori, isMogura, isNezumi, isBoy, isGirl, isLeft, isCenter, isRight, isSuidou2, isSuidou3, 
					isSuidou4, isSuberidai2, isRed, isWhite, isYellow, isPink, isMegane, isGimmic;
	
	private int choseSuberidai=0, levelRed = 0, levelWhite = 0, levelYellow = 0, levelPink = 0, classMegane =1, atMegane = 0, positionCurrent =-1;
	private boolean boyleft, girlleft, stateMoveMegane, isHasMeganeRed, isHasMeganeWhite,isHasMeganeYellow,isHasMeganePink;
	private int flowerX[]={192, 326, 459, 594};
	
	@SuppressWarnings("unused")
	private LoopEntityModifier loopCyo;
	private SequenceEntityModifier sqBirdMother, sqSuberidai2, sqMegane, sqRed, sqWhite, sqYellow, sqPink, sqNezumi;
	private Sound mp3_3_Suberidai1,mp3_3_Suberidai2,mp3_3_Suberidai3, mp3Megane, mp3_BoyGirl, mp3Red, mp3White, mp3Yellow, mp3Pink,
				  mp3Kaure1, mp3Kaure2, mp3Nezumi, mp3Suidou,mp3Tori,
				  mp3_17_18_19_1, mp3_17Left, mp3_17Center, mp3_17Right, mp3Dango1,mp3Dango2, mp3Mogura1,mp3Mogura2;
	
	
	private TexturePack Vol3TulipPacker_1;
        private TexturePackTextureRegionLibrary Vol3TulipLibrary1;
        @Override
            public void onCreateResources() {
                SoundFactory.setAssetBasePath("tulip/mfx/");
                BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tulip/gfx/");
                mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "tulip/gfx/");
                super.onCreateResources();
            }

	@Override
	protected void loadKaraokeResources() {
                Vol3TulipPacker_1 = mTexturePackLoaderFromSource.load("Vol3TulipPacker1.xml");
                Vol3TulipPacker_1.loadTexture();  
                Vol3TulipLibrary1 = Vol3TulipPacker_1.getTexturePackTextureRegionLibrary();
            
		ttrBackground = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_26_IPHONE_HAIKEI);
		
		ttrSuidou1 = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_14_1_IPHONE_SUIDOU);
		
		ttrSuidou11 = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_14_1_1_IPHONE_SUIDOU);
	
		ttrIe = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_24_1_IPHONE_IE);
		
		ttrKadan = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_25_IPHONE_KADAN);
		
		ttrDango = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_20_1_IPHONE_DANGO,
		        Vol3TulipResource.Vol3TulipPacker1.A1_20_2_IPHONE_DANGO,
		        Vol3TulipResource.Vol3TulipPacker1.A1_20_3_IPHONE_DANGO);
		ttrKaeru = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_11_1_IPHONE_KAERU,
                        Vol3TulipResource.Vol3TulipPacker1.A1_11_2_IPHONE_KAERU);
		
		ttrTori =getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_12_1_IPHONE_TORI,
                        Vol3TulipResource.Vol3TulipPacker1.A1_12_2_IPHONE_TORI);
		
		ttrSuberidai =  Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_22_1_IPHONE_SUBERIDAI);
		
		ttrCyo = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_23_IPHONE_CYO);
		
		ttrMogura = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_13_1_IPHONE_MOGURA,
                        Vol3TulipResource.Vol3TulipPacker1.A1_13_2_IPHONE_MOGURA);
		
		ttrNezumi1 = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_21_1_IPHONE_NEZUMI);
		
		ttrNezumi2 = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_21_2_IPHONE_NEZUMI);
		
		ttrRed = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_7_1_IPHONE_RED,
                        Vol3TulipResource.Vol3TulipPacker1.A1_7_2_IPHONE_RED,
                        Vol3TulipResource.Vol3TulipPacker1.A1_7_3_IPHONE_RED,
                        Vol3TulipResource.Vol3TulipPacker1.A1_7_4_IPHONE_RED);
		
		ttrWhite = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_8_1_IPHONE_WHITE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_8_2_IPHONE_WHITE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_8_3_IPHONE_WHITE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_8_4_IPHONE_WHITE);
		
		ttrYellow = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_9_1_IPHONE_YELLOW,
                        Vol3TulipResource.Vol3TulipPacker1.A1_9_2_IPHONE_YELLOW,
                        Vol3TulipResource.Vol3TulipPacker1.A1_9_3_IPHONE_YELLOW,
                        Vol3TulipResource.Vol3TulipPacker1.A1_9_4_IPHONE_YELLOW);
		
		ttrPink = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_10_1_IPHONE_PINK,
                        Vol3TulipResource.Vol3TulipPacker1.A1_10_2_IPHONE_PINK,
                        Vol3TulipResource.Vol3TulipPacker1.A1_10_3_IPHONE_PINK,
                        Vol3TulipResource.Vol3TulipPacker1.A1_10_4_IPHONE_PINK);
		
		ttrBoy = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_6_1_IPHONE_BOY,
                        Vol3TulipResource.Vol3TulipPacker1.A1_6_2_IPHONE_BOY,
                        Vol3TulipResource.Vol3TulipPacker1.A1_6_3_IPHONE_BOY);
		
		ttrGirl = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_5_1_IPHONE_GIRL,
                        Vol3TulipResource.Vol3TulipPacker1.A1_5_2_IPHONE_GIRL,
                        Vol3TulipResource.Vol3TulipPacker1.A1_5_3_IPHONE_GIRL);
		
		ttrRight =getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_19_1_IPHONE_RIGHT,
                        Vol3TulipResource.Vol3TulipPacker1.A1_19_1_1_IPHONE_RIGHT,
                        Vol3TulipResource.Vol3TulipPacker1.A1_19_2_IPHONE_RIGHT);
		
		ttrCenter = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_18_1_IPHONE_CENTER,
                        Vol3TulipResource.Vol3TulipPacker1.A1_18_1_1_IPHONE_CENTER,
                        Vol3TulipResource.Vol3TulipPacker1.A1_18_2_IPHONE_CENTER);
		
		ttrLeft = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_17_1_IPHONE_LEFT,
                        Vol3TulipResource.Vol3TulipPacker1.A1_17_1_1_IPHONE_LEFT,
                        Vol3TulipResource.Vol3TulipPacker1.A1_17_2_IPHONE_LEFT);
		
		ttrBirdMother = Vol3TulipLibrary1.get(Vol3TulipResource.Vol3TulipPacker1.A1_12_3_IPHONE_TORI_PNG);
		
		ttrSuidou2 = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_14_2_IPHONE_SUIDOU,
                        Vol3TulipResource.Vol3TulipPacker1.A1_14_2_1_IPHONE_SUIDOU);
		
		ttrSuidou3 = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_14_3_IPHONE_SUIDOU,
                        Vol3TulipResource.Vol3TulipPacker1.A1_14_3_1_IPHONE_SUIDOU);
		
		ttrSuidou4 = getTiledTextureFromPacker(Vol3TulipPacker_1, 
		        Vol3TulipResource.Vol3TulipPacker1.A1_14_4_IPHONE_SUIDOU,
                        Vol3TulipResource.Vol3TulipPacker1.A1_14_4_1_IPHONE_SUIDOU);
		
		ttrSuberidai2 = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_22_2_IPHONE_SUBERIDAI,
                        Vol3TulipResource.Vol3TulipPacker1.A1_22_3_IPHONE_SUBERIDAI,
                        Vol3TulipResource.Vol3TulipPacker1.A1_22_4_IPHONE_SUBERIDAI);
		
		ttrMegane = getTiledTextureFromPacker(Vol3TulipPacker_1, 
                        Vol3TulipResource.Vol3TulipPacker1.A1_4_10_IPHONE_MEGANE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_4_1_IPHONE_MEGANE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_4_7_IPHONE_MEGANE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_4_8_IPHONE_MEGANE,
                        Vol3TulipResource.Vol3TulipPacker1.A1_4_9_IPHONE_MEGANE
                        );
		
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0, ttrBackground, this.getVertexBufferObjectManager())));
		mScene.setOnAreaTouchTraversalFrontToBack();
		
		sIe = new Sprite(0, 0, ttrIe, this.getVertexBufferObjectManager());
		mScene.attachChild(sIe);
		
		sSuidou1 = new Sprite(22, 195, ttrSuidou1, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuidou1);
		
		sSuidou2 = new AnimatedSprite(60, 218, ttrSuidou2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuidou2);
		
		sSuidou3 = new AnimatedSprite(134, 219, ttrSuidou3, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuidou3);
		
		sSuidou4 = new AnimatedSprite(215, 218, ttrSuidou4, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuidou4);
		
		sSuidou11 = new Sprite(22, 195, ttrSuidou11, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuidou11);
		
		sDango = new AnimatedSprite(66, 365, ttrDango, this.getVertexBufferObjectManager());
		mScene.attachChild(sDango);
		
		sSuberidai2 = new AnimatedSprite(614, -15, ttrSuberidai2, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuberidai2);
		
		sSuberidai = new Sprite(568, 45, ttrSuberidai, this.getVertexBufferObjectManager());
		mScene.attachChild(sSuberidai);
		
		sCyo = new Sprite(754, 245, ttrCyo, this.getVertexBufferObjectManager());
		mScene.attachChild(sCyo);
		
		sKaeru = new AnimatedSprite(791, 281, ttrKaeru, this.getVertexBufferObjectManager());
		mScene.attachChild(sKaeru);
		
		sMogura = new AnimatedSprite(58, 508, ttrMogura, this.getVertexBufferObjectManager());
		mScene.attachChild(sMogura);
		
		sNezumi = new Sprite(481, 210, ttrNezumi1, this.getVertexBufferObjectManager());
		mScene.attachChild(sNezumi);
		
		sNezumi2 = new Sprite(481, 210, ttrNezumi2, this.getVertexBufferObjectManager());
		mScene.attachChild(sNezumi2);
		sNezumi2.setVisible(false);
		sNezumi2.setScale(0.4f);
		
		sKadan = new Sprite(157, 395, ttrKadan, this.getVertexBufferObjectManager());
		mScene.attachChild(sKadan);
		
		sLeft = new AnimatedSprite(70, 45, ttrLeft, this.getVertexBufferObjectManager());
		mScene.attachChild(sLeft);
		
		sCenter = new AnimatedSprite(238, 48, ttrCenter, this.getVertexBufferObjectManager());
		mScene.attachChild(sCenter);
		
		sRight = new AnimatedSprite(432, 48, ttrRight, this.getVertexBufferObjectManager());
		mScene.attachChild(sRight);
		
		sBoy = new AnimatedSprite(477, 139, ttrBoy, this.getVertexBufferObjectManager());
		mScene.attachChild(sBoy);
		
		sGirl = new AnimatedSprite(256, 162, ttrGirl, this.getVertexBufferObjectManager());
		mScene.attachChild(sGirl);
		
		sRed = new AnimatedSprite(258, 394, ttrRed, this.getVertexBufferObjectManager());
		mScene.attachChild(sRed);
		
		sWhite = new AnimatedSprite(388, 395, ttrWhite, this.getVertexBufferObjectManager());
		mScene.attachChild(sWhite);
		
		sYellow = new AnimatedSprite(520, 395, ttrYellow, this.getVertexBufferObjectManager());
		mScene.attachChild(sYellow);
		
		sPink = new AnimatedSprite(651, 395, ttrPink, this.getVertexBufferObjectManager());
		mScene.attachChild(sPink);
		
		sTori = new AnimatedSprite(142, -6, ttrTori, this.getVertexBufferObjectManager());
		mScene.attachChild(sTori);
		
		sMegane = new AnimatedSprite(970, 373, ttrMegane, this.getVertexBufferObjectManager());
		mScene.attachChild(sMegane);
		sMegane.setCurrentTileIndex(1);
		
		sBirdMother = new Sprite(580, -70, ttrBirdMother, this.getVertexBufferObjectManager());
		mScene.attachChild(sBirdMother);
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
     	        addGimmicsButton(mScene, Vol3TulipResource.SOUND_GIMMIC, Vol3TulipResource.IMAGE_GIMMIC, Vol3TulipLibrary1);
		
	}


	@Override
	protected void loadKaraokeComplete() {
	        Log.d(TAG_LOG,"Load Karaoke complete");
		super.loadKaraokeComplete();
	}
	@Override
	public synchronized void onResumeGame() {
            inital();
            toriChange();
            loopCyo();
	super.onResumeGame();
	}
	private void meganeMove(int i){
		stateMoveMegane = true;
		sMegane.setCurrentTileIndex(1);
		float pDuration = Math.abs(sMegane.getX()-flowerX[i])*6.0f/960;
		sMegane.registerEntityModifier(sqMegane = new SequenceEntityModifier(
				new MoveXModifier(pDuration, sMegane.getX(), flowerX[i])));
		if(sqMegane !=null){
			sqMegane.addModifierListener(this);
		}
	}
	private void suberidaiMove(){
		sSuberidai2.registerEntityModifier(sqSuberidai2 = new SequenceEntityModifier(
				new MoveModifier(1.0f, sSuberidai2.getX(), 800, sSuberidai2.getY(), 115),
				new RotationModifier(0.2f, 0.0f, -30.0f),
				new MoveXModifier(0.2f, 800, 820)
				));
		
		if(sqSuberidai2!=null){
			sqSuberidai2.addModifierListener(this);
		}
	}
	
	private void birdMother(){
		sBirdMother.registerEntityModifier(sqBirdMother= new SequenceEntityModifier(
				new MoveModifier( 1.8f, sBirdMother.getX(), 240, sBirdMother.getY(), 5),
				new DelayModifier(0.3f),
				new MoveYModifier(0.2f, 5, 20),
				new MoveYModifier(0.4f, 20, -15),
				new MoveYModifier(0.2f, -15, 5),
				new DelayModifier(0.3f),
				new MoveModifier( 1.4f, 240, -70, 20, -50)
				));
		if(sqBirdMother != null){
			sqBirdMother.addModifierListener(this);
		}
	}
	private void redMove(){
		sRed.registerEntityModifier(sqRed = new SequenceEntityModifier(
				new MoveXModifier(0.2f, sRed.getX(), sRed.getX()+10),
				new MoveXModifier(0.4f, sRed.getX()+10, sRed.getX()-10),
				new MoveXModifier(0.4f, sRed.getX()-10, sRed.getX()+10),
				new MoveXModifier(0.2f, sRed.getX()+10, sRed.getX())
				));
		if(sqRed!=null){
			sqRed.addModifierListener(this);
		}
	}
	private void whiteMove(){
		sWhite.registerEntityModifier(sqWhite = new SequenceEntityModifier(
				new MoveXModifier(0.2f, sWhite.getX(), sWhite.getX()+10),
				new MoveXModifier(0.4f, sWhite.getX()+10, sWhite.getX()-10),
				new MoveXModifier(0.4f, sWhite.getX()-10, sWhite.getX()+10),
				new MoveXModifier(0.2f, sWhite.getX()+10, sWhite.getX())
				));
		if(sqWhite!=null){
			sqWhite.addModifierListener(this);
		}
	}
	private void yellowMove(){
		sYellow.registerEntityModifier(sqYellow = new SequenceEntityModifier(
				new MoveXModifier(0.2f, sYellow.getX(), sYellow.getX()+10),
				new MoveXModifier(0.4f, sYellow.getX()+10, sYellow.getX()-10),
				new MoveXModifier(0.4f, sYellow.getX()-10, sYellow.getX()+10),
				new MoveXModifier(0.2f, sYellow.getX()+10, sYellow.getX())
				));
		if(sqYellow!=null){
			sqYellow.addModifierListener(this);
		}
	}
	private void pinkMove(){
		sPink.registerEntityModifier(sqPink = new SequenceEntityModifier(
				new MoveXModifier(0.2f, sPink.getX(), sPink.getX()+10),
				new MoveXModifier(0.4f, sPink.getX()+10, sPink.getX()-10),
				new MoveXModifier(0.4f, sPink.getX()-10, sPink.getX()+10),
				new MoveXModifier(0.2f, sPink.getX()+10, sPink.getX())
				));
		if(sqPink!=null){
			sqPink.addModifierListener(this);
		}
	}
	
 	private void loopCyo(){
		sCyo.registerEntityModifier(loopCyo= new LoopEntityModifier(
				new SequenceEntityModifier(
					new RotationModifier(0.1f, 0.0f, 10.0f),
					new DelayModifier(0.2f),
					new RotationModifier(0.2f, 10.0f, -10.0f),
					new DelayModifier(0.2f),
					new RotationModifier(0.1f, -10.0f, 0.0f)
					)
				));
	}

	private void suidou2Change(){
		long pDuration[] = new long[] { 100, 600, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sSuidou2.animate(pDuration, pFrams, 0, this);
	}
	
	private void suidou3Change(){
		long pDuration[] = new long[] { 100, 600, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sSuidou3.animate(pDuration, pFrams, 0, this);
	}
	
	private void suidou4Change(){
		long pDuration[] = new long[] { 100, 600, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sSuidou4.animate(pDuration, pFrams, 0, this);
	}
	
	private void leftChange(){
		long pDuration[] = new long[] { 100, 400, 700,400, 300 };
		int pFrams[] = new int[] { 0,1,2,1,0 };
		sLeft.animate(pDuration, pFrams, 0, this);
	}
	
	private void centerChange(){
		long pDuration[] = new long[] { 100, 400, 700,400, 300 };
		int pFrams[] = new int[] { 0,1,2,1,0 };
		sCenter.animate(pDuration, pFrams, 0, this);
	}
	
	private void rightChange(){
		long pDuration[] = new long[] { 100, 400, 700,400, 300 };
		int pFrams[] = new int[] { 0,1,2,1,0 };
		sRight.animate(pDuration, pFrams, 0, this);
	}
	
	private void boyChange(){
		if(boyleft){
			long pDuration[] = new long[] { 100, 1200, 300 };
			int pFrams[] = new int[] { 0,1,0 };
			sBoy.animate(pDuration, pFrams, 0, this);
		}else {
			long pDuration[] = new long[] { 100, 1200, 300 };
			int pFrams[] = new int[] { 0,2,0 };
			sBoy.animate(pDuration, pFrams, 0, this);
		}
	}
	
	private void girlChange(){
		if(girlleft){
			long pDuration[] = new long[] { 100, 1200, 300 };
			int pFrams[] = new int[] { 0,1,0 };
			sGirl.animate(pDuration, pFrams, 0, this);
		}else {
			long pDuration[] = new long[] { 100, 1200, 300 };
			int pFrams[] = new int[] { 0,2,0 };
			sGirl.animate(pDuration, pFrams, 0, this);
		}
	}
	
	private void dangoChange(){
		long pDuration[] = new long[] { 100, 400, 400, 300 };
		int pFrams[] = new int[] { 0,1,2,0 };
		sDango.animate(pDuration, pFrams, 0, this);
	}
	
	private void kaeruChange(){
		long pDuration[] = new long[] { 300, 1000, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sKaeru.animate(pDuration, pFrams, 0, this);
	}
	
	private void toriChange(){
		long pDuration[] = new long[] { 100, 400, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sTori.animate(pDuration, pFrams, -1, this);
	}
	
	private void moguraChange(){
		long pDuration[] = new long[] { 300, 1000, 300 };
		int pFrams[] = new int[] { 0,1,0 };
		sMogura.animate(pDuration, pFrams, 0, this);
	}

	private void nezumiScale(){
		sNezumi2.setVisible(true);
		sNezumi2.registerEntityModifier(sqNezumi = new SequenceEntityModifier( 
				new ScaleModifier(0.3f, 0.4f, 1.0f),
				new DelayModifier(2.0f),
				new ScaleModifier(0.3f, 1.0f, 0.4f)
		));
		
		if(sqNezumi!=null){
			sqNezumi.addModifierListener(this);
		}
	}
	
	@Override
	public void combineGimmic3WithAction() {
		if(isSuberidai2 && isGimmic){
			isSuberidai2 = false;
			isGimmic = false;
			switch (choseSuberidai) {
			case 0:
				mp3_3_Suberidai1.play();
				break;
			case 1:
				mp3_3_Suberidai2.play();
				break;
			case 2:
				mp3_3_Suberidai3.play();
				break;

			default:
				break;
			}
			suberidaiMove();
		}
	}
	public void delaySound(final AnimatedSprite anis, float durations, final Sound sounds){
	    anis.registerEntityModifier(new DelayModifier(durations, new IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                }
                @Override
                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    sounds.play();
                }
            }));
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int pX = (int)pSceneTouchEvent.getX();
		int pY = (int)pSceneTouchEvent.getY();
		if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_DOWN){
			if(checkContains(sDango, 0, 0, (int)sDango.getWidth(), (int)sDango.getHeight(), pX, pY)){
				if(isDango){
					mp3Dango1.play();
					isDango = false;
					dangoChange();
					delaySound(sDango, 0.3f, mp3Dango2);
				}
			}else if(checkContains(sKaeru, 0, 0, (int)sKaeru.getWidth(), (int)sKaeru.getHeight(), pX, pY)){
				if(isKaeru){
					mp3Kaure1.play();
					isKaeru = false;
					kaeruChange();
					delaySound(sKaeru, 0.3f, mp3Kaure2);
				}
			}else if(checkContains(sTori, 0, 0, (int)sTori.getWidth(), (int)sTori.getHeight(), pX, pY)){
				if(isTori){
					isTori = false;
					birdMother();
					delaySound(sTori, 1.0f, mp3Tori);
				}
			}else if(checkContains(sMogura, 0, 0, (int)sMogura.getWidth(), (int)sMogura.getHeight(), pX, pY)){
				if(isMogura){
					mp3Mogura1.play();
					isMogura = false;
					moguraChange();
					delaySound(sMogura, 0.3f, mp3Mogura2);
				}
			}else if(checkContains(sNezumi, 0, 0, (int)sNezumi.getWidth(), (int)sNezumi.getHeight(), pX, pY)){
				if(isNezumi){
					mp3Nezumi.play();
					isNezumi = false;
					nezumiScale();
				}
			}else if(checkContains(sBoy, (int)sBoy.getWidth()/4, 0, (int)sBoy.getWidth()*3/4, (int)sBoy.getHeight()*3/4, pX, pY)){
				if(isBoy){
					mp3_BoyGirl.play();
					isBoy = false;
					if(positionCurrent==2){
						boyleft = false;
					}
					if(positionCurrent==3){
						boyleft = true;
					}
					if(boyleft&&positionCurrent!=2){
						boyChange();
						//////////////////
						if(levelYellow<=3){
							levelYellow++;
						}
						if((levelYellow==4)&&!stateMoveMegane){
							isYellow = false;
							stateMoveMegane = true;
//							meganeMove(2);
							atMegane = 2;
							classMegane = 4;
							positionCurrent =2;
							sBoy.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                                                            @Override
                                                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                            }
                                                            @Override
                                                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                                meganeMove(2);
                                                            }
                                                        }));
						}
						if(levelYellow>=5&& isHasMeganeYellow){
							levelYellow = 0;
							isHasMeganeYellow = false;
						}
						// cho hoa lon cham lai de kip tuoi
						sBoy.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(levelYellow<=3&&levelYellow>0){
                                                            mp3Yellow.play();
                                                        }
                                                        if(levelYellow<=3){
                                                            sYellow.setCurrentTileIndex(levelYellow);
                                                        }
                                                        yellowMove();
                                                    }
                                                }));
						/////////////////
						sBoy.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(positionCurrent==3){
                                                            boyleft = true;
                                                        }
                                                        boyleft = false;
                                                    }
                                                }));
					}
					if((!boyleft)&&(positionCurrent!=3)){
						boyChange();
						///////////////////
						if(levelPink<=3){
							levelPink++;
						}
						if((levelPink==4)&!stateMoveMegane){
							
							isPink = false;
							stateMoveMegane = true;
//							meganeMove(3);
							atMegane = 3;
							classMegane = 0;
							positionCurrent =3;
							sBoy.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
	                                                    @Override
	                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                                                    }
	                                                    @Override
	                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                                                        meganeMove(3);
	                                                    }
	                                                }));
						}
						if(levelPink>=5&& isHasMeganePink){
							levelPink = 0;
							isHasMeganePink = false;
						}
						sBoy.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(levelPink<=3&&levelPink>0){
                                                            mp3Pink.play();
                                                        }if(levelPink<=3){
                                                            sPink.setCurrentTileIndex(levelPink);
                                                        }
                                                        pinkMove();
                                                    }
                                                }));
						sBoy.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(positionCurrent==2){
                                                            boyleft = false;
                                                        }
                                                        boyleft = true;
                                                    }
                                                }));
					}
						// lam sao de biet kinh nup dang o bong hoa nao
				}
			}else if(checkContains(sGirl, (int)sGirl.getWidth()/4, 0, (int)sGirl.getWidth()*3/4, (int)sGirl.getHeight()*3/4, pX, pY)){
				if(isGirl){
					mp3_BoyGirl.play();
					isGirl = false;
					if(positionCurrent==0){
						girlleft = false;
					}
					if(positionCurrent==1){
						girlleft = true;
					}
					if(girlleft&&positionCurrent!=0){
						girlChange();
						////////////////
						if(levelRed<=3){
							levelRed++;
						}
						
						if((levelRed==4)&&!stateMoveMegane){
							isRed = false;
							stateMoveMegane = true;
//							meganeMove(0);
							atMegane = 0;
							classMegane = 2;
							positionCurrent = 0;
							sGirl.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
	                                                    @Override
	                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                                                    }
	                                                    @Override
	                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                                                        meganeMove(0);
	                                                    }
	                                                }));
						}
						if(levelRed>=5&& isHasMeganeRed){
							isHasMeganeRed =false;
							levelRed = 0;
						}
						sGirl.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(levelRed<=3&&levelRed>0){
                                                            mp3Red.play();
                                                        }
                                                        if(levelRed<=3){
                                                            sRed.setCurrentTileIndex(levelRed);
                                                        }
                                                        redMove();
                                                    }
                                                }));
						////////////////
						sGirl.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(positionCurrent==1){
                                                            girlleft =true;
                                                        }else{
                                                            girlleft = false;
                                                        }
                                                    }
                                                }));
					}
					if(!girlleft&&positionCurrent!=1){
						girlChange();
						
						if(levelWhite<=3){
							levelWhite++;
						}
						if((levelWhite==4)&&!stateMoveMegane){
							isWhite = false;
							stateMoveMegane = true;
//							meganeMove(1);
							atMegane = 1;
							classMegane = 3;
							positionCurrent =1;
							sGirl.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
	                                                    @Override
	                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                                                    }
	                                                    @Override
	                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                                                        meganeMove(1);
	                                                    }
	                                                }));
						}
						if(levelWhite>=5&&isHasMeganeWhite){
							levelWhite = 0;
							isHasMeganeWhite=false;
						}
						sGirl.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(levelWhite<=3&&levelWhite>0){
                                                            mp3White.play();
                                                        }if(levelWhite<=3){
                                                            sWhite.setCurrentTileIndex(levelWhite);
                                                        }
                                                        whiteMove();
                                                    }
                                                }));
						//////////////
						sGirl.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                                    }
                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                                        if(positionCurrent==0){
                                                            girlleft =false;
                                                        }else{
                                                            girlleft = true;
                                                        }
                                                    }
                                                }));
					}
					
				}
			}else if(checkContains(sLeft, 0, 0, (int)sLeft.getWidth(), (int)sLeft.getHeight(), pX, pY)){
				if(isLeft){
					mp3_17_18_19_1.play();
					isLeft = false;
					leftChange();
					delaySound(sLeft, 0.3f, mp3_17Left);
				}
			}else if(checkContains(sCenter, 0, 0, (int)sCenter.getWidth(), (int)sCenter.getHeight(), pX, pY)){
				if(isCenter){
					mp3_17_18_19_1.play();
					isCenter = false;
					centerChange();
					delaySound(sCenter, 0.3f, mp3_17Center);
				}
			}else if(checkContains(sRight, 0, 0, (int)sRight.getWidth(), (int)sRight.getHeight(), pX, pY)){
				if(isRight){
					mp3_17_18_19_1.play();
					isRight = false;
					rightChange();
					delaySound(sRight, 0.3f, mp3_17Right);
				}
			}else if(checkContains(sSuidou2, 0, 0, (int)sSuidou2.getWidth(), (int)sSuidou2.getHeight(), pX, pY)){
				if(isSuidou2){
					mp3Suidou.play();
					isSuidou2 = false;
					suidou2Change();
				}
			}else if(checkContains(sSuidou3, 0, 0, (int)sSuidou3.getWidth(), (int)sSuidou3.getHeight(), pX, pY)){
				if(isSuidou3){
					mp3Suidou.play();
					isSuidou3 = false;
					suidou3Change();
				}
			}else if(checkContains(sSuidou4, 0, 0, (int)sSuidou4.getWidth(), (int)sSuidou4.getHeight(), pX, pY)){
				if(isSuidou4){
					mp3Suidou.play();
					isSuidou4 = false;
					suidou4Change();
				}
			}else if(checkContains(sSuberidai2, -200, 0, (int)sSuberidai2.getWidth(), (int)sSuberidai2.getHeight(), pX, pY)){
				if(isSuberidai2 && isGimmic){
					isSuberidai2 = false;
					isGimmic = false;
					switch (choseSuberidai) {
					case 0:
						mp3_3_Suberidai1.play();
						break;
					case 1:
						mp3_3_Suberidai2.play();
						break;
					case 2:
						mp3_3_Suberidai3.play();
						break;

					default:
						break;
					}
					suberidaiMove();
				}
			}else if(checkContains(sMegane, 0, 0, (int)sMegane.getWidth(), (int)sMegane.getHeight(), pX, pY)){
				if(isMegane && sqMegane.isFinished()){
					mp3Megane.play();
					positionCurrent = -1;
					isMegane = false;
					sMegane.setPosition(970, 373);
					sMegane.setCurrentTileIndex(1);

				}
			}else if(checkContains(sRed, 0, 0, (int)sRed.getWidth(), (int)sRed.getHeight(), pX, pY)){
				if(isRed){
					if(levelRed<=2){
						mp3Red.play();
					}
					if(levelRed<=3){
						levelRed++;
					}
					
					if((levelRed==4)&&!stateMoveMegane){
//						mp3Red.play();
						isRed = false;
						stateMoveMegane = true;
						meganeMove(0);
						atMegane = 0;
						classMegane = 2;
						positionCurrent = 0;
					}
					if(levelRed>=5&& isHasMeganeRed){
//						mp3Red.play();
						isHasMeganeRed =false;
						levelRed = 0;
					}
					Log.d(TAG_LOG,"LEVEL TEST" +levelRed);
					if(levelRed<=3){
					    sRed.setCurrentTileIndex(levelRed);
					}
					Log.d(TAG_LOG,"LEVEL  CHECK" +levelRed);
				}
			}else if(checkContains(sWhite, 0, 0, (int)sWhite.getWidth(), (int)sWhite.getHeight(), pX, pY)){
				if(isWhite){
					if(levelWhite<=2){
						mp3White.play();
						
					}
					if(levelWhite<=3){
						levelWhite++;
					}
					if((levelWhite==4)&&!stateMoveMegane){
//						mp3White.play();
						isWhite = false;
						stateMoveMegane = true;
						meganeMove(1);
						atMegane = 1;
						classMegane = 3;
						positionCurrent =1;
					}
					if(levelWhite>=5&&isHasMeganeWhite){
//						mp3White.play();
						levelWhite = 0;
						isHasMeganeWhite=false;
					}
					if(levelWhite<=3){
					    sWhite.setCurrentTileIndex(levelWhite);
					}
				}
			}else if(checkContains(sYellow, 0, 0, (int)sYellow.getWidth(), (int)sYellow.getHeight(), pX, pY)){
				if(isYellow){
					if(levelYellow<=2){
						mp3Yellow.play();
					}
					if(levelYellow<=3){
						levelYellow++;
					}
					if((levelYellow==4)&&!stateMoveMegane){
//						mp3Yellow.play();
						isYellow = false;
						stateMoveMegane = true;
						meganeMove(2);
						atMegane = 2;
						classMegane = 4;
						positionCurrent =2;
					}
					if(levelYellow>=5&& isHasMeganeYellow){
//						mp3Yellow.play();
						levelYellow = 0;
						isHasMeganeYellow = false;
					}
					if(levelYellow<=3){
					    sYellow.setCurrentTileIndex(levelYellow);
					}
				}
			}else if(checkContains(sPink, 0, 0, (int)sPink.getWidth(), (int)sPink.getHeight(), pX, pY)){
				if(isPink){
					if(levelPink<=2){
						mp3Pink.play();
					}
					
					if(levelPink<=3){
						levelPink++;
					}
					if((levelPink==4)&!stateMoveMegane){
//						mp3Pink.play();
						isPink = false;
						stateMoveMegane = true;
						meganeMove(3);
						atMegane = 3;
						classMegane = 0;
						positionCurrent =3;
					}
					if(levelPink>=5&& isHasMeganePink){
//						mp3Pink.play();
						levelPink = 0;
						isHasMeganePink = false;
					}
					if(levelPink<=3){
					    sPink.setCurrentTileIndex(levelPink);
					}
				}
			}
			return true;
		}
		return false;
	}
	private void inital() {
		choseSuberidai = 0;
		positionCurrent = -1;
		levelRed = 0;
		levelWhite = 0;
		levelYellow = 0;
		levelPink = 0;
		classMegane =1;
		atMegane = 0;
		isDango = true;
		isKaeru = true;
		isTori = true;
		isMogura = true;
		isNezumi = true;
		isBoy = true;
		isGirl = true;
		isLeft = true;
		isCenter = true;
		isRight = true;
		isSuidou2 = true;
		isSuidou3 = true;
		isSuidou4 = true;
		isSuberidai2 = true;
		boyleft = true;
		girlleft = true;
		isRed = true;
		isWhite = true;
		isYellow = true;
		isPink = true;
		isGimmic = true;
		isMegane = false;
		stateMoveMegane = false;
		isHasMeganeRed = false;
		isHasMeganeWhite = false;
		isHasMeganeYellow = false;
		isHasMeganePink = false;
	}
	@Override
	public void onPause() {
		if(sMegane!=null){
			sMegane.clearEntityModifiers();
			sMegane.setPosition(970, 373);
			sMegane.setCurrentTileIndex(1);
			sqMegane = null;
		}
		if(sBoy!=null){
		        sBoy.clearEntityModifiers();
			sBoy.stopAnimation();
			sBoy.setCurrentTileIndex(0);
		}
		if(sGirl!=null){
		        sGirl.clearEntityModifiers();
			sGirl.stopAnimation();
			sGirl.setCurrentTileIndex(0);
		}
		if(sRed!=null){
			sRed.stopAnimation();
			sRed.clearEntityModifiers();
			sRed.setCurrentTileIndex(0);
			sRed.setPosition(258, 394);
			sqRed = null;
		}
		if(sWhite!=null){
			sWhite.stopAnimation();
			sWhite.clearEntityModifiers();
			sWhite.setCurrentTileIndex(0);
			sWhite.setPosition(388, 395);
			sqWhite = null;
		}
		if(sYellow!=null){
			sYellow.stopAnimation();
			sYellow.clearEntityModifiers();
			sYellow.setCurrentTileIndex(0);
			sYellow.setPosition(520, 395);
			sqYellow = null;
		}
		if(sPink!=null){
			sPink.stopAnimation();
			sPink.clearEntityModifiers();
			sPink.setCurrentTileIndex(0);
			sPink.setPosition(651, 395);
			sqPink = null;
		}
		if(sKaeru!=null){
		        sKaeru.clearEntityModifiers();
			sKaeru.stopAnimation();
			sKaeru.setCurrentTileIndex(0);
		}
		if(sBirdMother!=null){
			sBirdMother.clearEntityModifiers();
			sBirdMother.setPosition(580, -70);
			sqBirdMother = null;
		}
		if(sTori!=null){
		        sTori.clearEntityModifiers();
			sTori.stopAnimation();
			sTori.setCurrentTileIndex(0);
		}
		if(sMogura!=null){
		        sMogura.clearEntityModifiers();
			sMogura.stopAnimation();
			sMogura.setCurrentTileIndex(0);
		}
		if(sLeft!=null){
		        sLeft.clearEntityModifiers();
			sLeft.stopAnimation();
			sLeft.setCurrentTileIndex(0);
		}
		if(sCenter!=null){
		        sCenter.clearEntityModifiers();
			sCenter.stopAnimation();
			sCenter.setCurrentTileIndex(0);
		}
		if(sRight!=null){
		        sRight.clearEntityModifiers();
			sRight.stopAnimation();
			sRight.setCurrentTileIndex(0);
		}
		if(sSuidou2!=null){
		        sSuidou2.clearEntityModifiers();
			sSuidou2.stopAnimation();
			sSuidou2.setCurrentTileIndex(0);
		}
		if(sSuidou3!=null){
		        sSuidou3.clearEntityModifiers();
			sSuidou3.stopAnimation();
			sSuidou3.setCurrentTileIndex(0);
		}
		if(sSuidou4!=null){
		        sSuidou4.clearEntityModifiers();
			sSuidou4.stopAnimation();
			sSuidou4.setCurrentTileIndex(0);
		}
		if(sDango!=null){
		        sDango.clearEntityModifiers();
			sDango.stopAnimation();
			sDango.setCurrentTileIndex(0);
		}
		if(sNezumi2!=null){
			sNezumi2.clearEntityModifiers();
			sNezumi2.setScale(0.4f);
			sNezumi2.setVisible(false);
			sqNezumi = null;
		}
		if(sSuberidai2!=null){
			sSuberidai2.clearEntityModifiers();
			sSuberidai2.setRotation(0.0f);
			sSuberidai2.setCurrentTileIndex(0);
			sSuberidai2.setPosition(614,-15);
			sqSuberidai2 = null;
		}
		if(sSuberidai!=null){
                    sSuberidai.clearEntityModifiers();
            }
		
		if(sCyo!=null){
			sCyo.clearEntityModifiers();
			sCyo.setRotation(0);
			loopCyo = null;
		}
		super.onPause();
	}
	@Override
	protected void loadKaraokeSound() {
		mp3_3_Suberidai1 = loadSoundResourceFromSD(Vol3TulipResource.A1_22_2_WARAI1);
		mp3_3_Suberidai2 = loadSoundResourceFromSD(Vol3TulipResource.A1_22_3_ONIPANTSUOCHI);
		mp3_3_Suberidai3 = loadSoundResourceFromSD(Vol3TulipResource.A1_22_4_SHUN2);
		mp3_BoyGirl = loadSoundResourceFromSD(Vol3TulipResource.A1_5_6_JOURO);
		mp3Red = loadSoundResourceFromSD(Vol3TulipResource.A1_7_MOKIN1);
		mp3White = loadSoundResourceFromSD(Vol3TulipResource.A1_8_MOKIN2);
		mp3Yellow = loadSoundResourceFromSD(Vol3TulipResource.A1_9_MOKIN3);
		mp3Pink = loadSoundResourceFromSD(Vol3TulipResource.A1_10_MOKIN4);
		mp3Kaure1 = loadSoundResourceFromSD(Vol3TulipResource.A1_11_13_MONI);
		mp3Kaure2 = loadSoundResourceFromSD(Vol3TulipResource.A1_11_GERO);
		mp3Mogura1 = loadSoundResourceFromSD(Vol3TulipResource.A1_11_13_MONI);
		mp3Mogura2 = loadSoundResourceFromSD(Vol3TulipResource.A1_13_PYOI);
		mp3Nezumi = loadSoundResourceFromSD(Vol3TulipResource.A1_21_CHU);
		mp3Suidou = loadSoundResourceFromSD(Vol3TulipResource.A1_14_SUIDOU);
		mp3_17_18_19_1 = loadSoundResourceFromSD(Vol3TulipResource.A1_17_18_19_GARA);
		mp3_17Left = loadSoundResourceFromSD(Vol3TulipResource.A1_17_KONICHIWA1);
		mp3_17Center = loadSoundResourceFromSD(Vol3TulipResource.A1_18_ASOBO1);
		mp3_17Right = loadSoundResourceFromSD(Vol3TulipResource.A1_19_OHAYO2);
		mp3Dango1 = loadSoundResourceFromSD(Vol3TulipResource.A1_20_TALKINGDRUM);
		mp3Dango2 = loadSoundResourceFromSD(Vol3TulipResource.A1_20_BO);
		mp3Tori = loadSoundResourceFromSD(Vol3TulipResource.A1_12_TSUBAME2);
		mp3Megane = loadSoundResourceFromSD(Vol3TulipResource.A1_4_PYOI);
	}

	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		if(pModifier.equals(sqBirdMother)){
			isTori = true;
			sBirdMother.setPosition(580, -70);
		}
		if(pModifier.equals(sqSuberidai2)){
			sSuberidai2.setVisible(false);
			sSuberidai.registerEntityModifier(new DelayModifier(0.7f, new IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                            }
                            @Override
                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                sSuberidai2.setVisible(true);
                                choseSuberidai++;
                                if(choseSuberidai==3){
                                        choseSuberidai=0;
                                }
                                sSuberidai2.setCurrentTileIndex(choseSuberidai);
                                isSuberidai2 = true;
                                isGimmic = true;
                                sSuberidai2.setRotation(0.0f);
                                sSuberidai2.clearEntityModifiers();
                                sSuberidai2.setPosition(614, -15);
                            }
                        }));
		}
		if(pModifier.equals(sqNezumi)){
			isNezumi=true;
			sNezumi2.setVisible(false);
		}
		if(pModifier.equals(sqMegane)){
			sMegane.setCurrentTileIndex(classMegane);
			stateMoveMegane = false;
			switch (atMegane) {
			case 0:
				isHasMeganeRed = true;
				levelRed =5;
				break;
			case 1:
				isHasMeganeWhite = true;
				levelWhite =5;
				break;
			case 2:
				isHasMeganeYellow = true;
				levelYellow =5;
				break;
			case 3:
				isHasMeganePink = true;
				levelPink = 5;
				break;
			default:
				break;
			}
			sMegane.registerEntityModifier(new DelayModifier(2.0f, new IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                            }
                            @Override
                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                isMegane = true;
                                isRed = true;
                                isWhite = true;
                                isYellow = true;
                                isPink = true;
                            }
                        }));
		}
	}

    @Override
    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
    }
    @Override
    public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
        if(pAnimatedSprite == sDango){
            isDango = true;
            sDango.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sKaeru){
            isKaeru = true;
            sKaeru.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sTori){
            isTori = true;
            sTori.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sMogura){
            isMogura = true;
            sMogura.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sBoy){
            isBoy = true;
            sBoy.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sGirl){
            isGirl = true;
            sGirl.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sLeft){
            isLeft = true;
            sLeft.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sCenter){
            isCenter = true;
            sCenter.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sRight){
            isRight = true;
            sRight.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sSuidou2){
            isSuidou2 = true;
            sSuidou2.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sSuidou3){
            isSuidou3 = true;
            sSuidou3.setCurrentTileIndex(0);
    }else if(pAnimatedSprite == sSuidou4){
            isSuidou4 = true;
            sSuidou4.setCurrentTileIndex(0);
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
