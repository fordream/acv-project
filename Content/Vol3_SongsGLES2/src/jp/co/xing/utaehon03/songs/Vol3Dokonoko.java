package jp.co.xing.utaehon03.songs;

import java.util.ArrayList;
import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameFragment;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3DokonokoResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Dokonoko extends BaseGameFragment implements IOnSceneTouchListener{
	
	private static final String TAG = "LOG_KURONEKO";
	
	private TexturePack tpDokonoko1, tpDokonoko2, tpDokonoko3, tpDokonoko4, tpDokonoko5, tpDokonoko6, tpDokonoko7, tpDokonoko8, tpDokonoko9;
	private TexturePackTextureRegionLibrary ttpLibDokonoko1, ttpLibDokonoko2, ttpLibDokonoko3, ttpLibDokonoko4, ttpLibDokonoko5,
											ttpLibDokonoko6, ttpLibDokonoko7, ttpLibDokonoko8;
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	
	private TextureRegion trBackground;
	private TextureRegion trMushroomCenter1, trMushroomCenter2, trCurtain, trCry;
	private Sprite sMushroomCenter1, sMushroomCenter2, sCurtain, sCryLeft, sCryBetween, sCryRight;
	
	private ITiledTextureRegion ttrWinner;
	private AnimatedSprite asWinner;
	
	private ITextureRegion[] txrMushroomCenter = new ITextureRegion[10];
	private Sprite[] sMushroomCenter = new Sprite[10];
	private int[] iMushroomCenterId = {
			Vol3DokonokoResource.A74_2_1_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_3_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_4_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_5_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_6_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_7_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_8_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_9_IPHONE_ID,
			Vol3DokonokoResource.A74_2_1_10_IPHONE_ID,
	};
	
	private ITextureRegion[] txrMushroomRight = new ITextureRegion[10];
	private Sprite[] sMushroomRight = new Sprite[10];
	private int[] iMushroomRightId = {
			Vol3DokonokoResource.A74_4_1_IPHONE_ID,
			Vol3DokonokoResource.A74_4_2_IPHONE_ID,
			Vol3DokonokoResource.A74_4_3_IPHONE_ID,
			Vol3DokonokoResource.A74_4_4_IPHONE_ID,
			Vol3DokonokoResource.A74_4_5_IPHONE_ID,
			Vol3DokonokoResource.A74_4_6_IPHONE_ID,
			Vol3DokonokoResource.A74_4_7_IPHONE_ID,
			Vol3DokonokoResource.A74_4_8_IPHONE_ID,
			Vol3DokonokoResource.A74_4_9_IPHONE_ID,
			Vol3DokonokoResource.A74_4_10_IPHONE_ID,
	};
	private int[][] iMushroomRightPosition = {
			{615, 115}, {759, 120}, {590, 300}, {731, 266}, {759, 120}, {615, 115}, {590, 300},{760, 420}, {760, 420}, {731, 266}
	};
	private int[] iIndexMushroomRight = {0, 5, 1, 4, 2, 6, 3, 9, 7, 8};
	
	private ITextureRegion[] txrMushroomTop = new ITextureRegion[10];
	private Sprite[] sMushroomTop = new Sprite[10];
	private int[] iMushroomTopId = {
			Vol3DokonokoResource.A74_3_1_IPHONE_ID,
			Vol3DokonokoResource.A74_3_2_IPHONE_ID,
			Vol3DokonokoResource.A74_3_3_IPHONE_ID,
			Vol3DokonokoResource.A74_3_4_IPHONE_ID,
			Vol3DokonokoResource.A74_3_5_IPHONE_ID,
			Vol3DokonokoResource.A74_3_6_IPHONE_ID,
			Vol3DokonokoResource.A74_3_7_IPHONE_ID,
			Vol3DokonokoResource.A74_3_8_IPHONE_ID,
			Vol3DokonokoResource.A74_3_9_IPHONE_ID,
			Vol3DokonokoResource.A74_3_10_IPHONE_ID
	};
	private int[][] iMushroomTopPosition = {
			{166, 13}, {225, 16}, {286, 18}, {345, 14}, {407, 16},
			{468, 24}, {530, 23}, {590, 12}, {651, 24}, {710, 17}};
	
	private Random rdMushroom = new Random();
	private ArrayList<Integer>rdIdMushroomRight = new ArrayList<Integer>();
	private int idCenter;
	
	private ITextureRegion[] txrMushroomNoFace = new ITextureRegion[10];
	private Sprite[] sMushroomNoFace = new Sprite[10];
	private int[] iMushroomNoFaceId = {
			Vol3DokonokoResource.A74_2_2_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_3_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_4_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_5_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_6_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_7_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_8_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_9_IPHONE_ID,
			Vol3DokonokoResource.A74_2_2_10_IPHONE_ID,
	};
	private int[][] iMushroomNoFacePosition = {{91, 105}, {91, 105}, {91, 105}, {91, 105}, {91, 105},
			{91, 105}, {91, 105}, {91, 105}, {91, 105}, {91, 120}};
	
	private ITextureRegion[] txrMushroomNormal = new ITextureRegion[10];
	private Sprite[] sMushroomNormal = new Sprite[10];
	private int[] iMushroomNormalId = {
			Vol3DokonokoResource.A74_2_3_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_3_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_4_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_5_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_6_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_7_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_8_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_9_IPHONE_ID,
			Vol3DokonokoResource.A74_2_3_10_IPHONE_ID,
	};
	
	private ITextureRegion[] txrMushroomFun = new ITextureRegion[10];
	private Sprite[] sMushroomFun = new Sprite[10];
	private int[] iMushroomFunId = {
			Vol3DokonokoResource.A74_2_4_1_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_2_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_3_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_4_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_5_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_6_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_7_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_8_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_9_1_IPHONE_ID,
			Vol3DokonokoResource.A74_2_4_10_1_IPHONE_ID,
	};
	
	private ITextureRegion[] txrMushroomBad = new ITextureRegion[10];
	private Sprite[] sMushroomBad = new Sprite[10];
	private int[] iMushroomBadId = {
			Vol3DokonokoResource.A74_2_5_1_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_2_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_3_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_4_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_5_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_6_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_7_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_8_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_9_2_IPHONE_ID,
			Vol3DokonokoResource.A74_2_5_10_2_IPHONE_ID,
	};
	
	private ITextureRegion[] txrMushroomLabel = new ITextureRegion[10];
	private Sprite[] sMushroomLabel = new Sprite[10];
	private int[] iMushroomLabelId = {
		Vol3DokonokoResource.A71_5_1_IPHONE_ID,
		Vol3DokonokoResource.A71_5_2_IPHONE_ID,
		Vol3DokonokoResource.A71_5_3_IPHONE_ID,
		Vol3DokonokoResource.A71_5_4_IPHONE_ID,
		Vol3DokonokoResource.A71_5_5_IPHONE_ID,
		Vol3DokonokoResource.A71_5_6_IPHONE_ID,
		Vol3DokonokoResource.A71_5_7_IPHONE_ID,
		Vol3DokonokoResource.A71_5_8_IPHONE_ID,
		Vol3DokonokoResource.A71_5_9_IPHONE_ID,
		Vol3DokonokoResource.A71_5_10_IPHONE_ID,
	};
	private int[][] iMushroomLabelPosition = {
			{133, 168}, {105, 195}, {133, 168}, {133, 168}, {133, 168},
			{133, 168}, {133, 175}, {170, 245}, {133, 168}, {133, 168}};
	
	private int[][] iPositionTopTrue = {
			{-20, -170}, {35, -170}, {85, -170}, {150, -170}, {213, -170},
			{273, -170}, {335, -170}, {380, -170}, {447, -170}, {510, -170},
	};
	private int indexPositionTop;
	private ArrayList<Integer>idMushRoom = new ArrayList<Integer>();
	
	private boolean isWinner, isTouchScene, isTouchSound, isBengin;
	
	private Sound[] mp3MushroomCenterTouch = new Sound[10];
	private String[] idMushroomCenterTouch = {
			Vol3DokonokoResource.A74_2_1_1,
			Vol3DokonokoResource.A74_2_1_2,
			Vol3DokonokoResource.A74_2_1_3,
			Vol3DokonokoResource.A74_2_1_4,
			Vol3DokonokoResource.A74_2_1_5,
			Vol3DokonokoResource.A74_2_1_6,
			Vol3DokonokoResource.A74_2_1_7,
			Vol3DokonokoResource.A74_2_1_8,
			Vol3DokonokoResource.A74_2_1_9,
			Vol3DokonokoResource.A74_2_1_10,
	};
	
	private Sound[] mp3MushroomTrue = new Sound[10];
	private String[] idMushroomTrue = {
			Vol3DokonokoResource.A74_2_2_1,
			Vol3DokonokoResource.A74_2_2_2,
			Vol3DokonokoResource.A74_2_2_3,
			Vol3DokonokoResource.A74_2_2_4,
			Vol3DokonokoResource.A74_2_2_5,
			Vol3DokonokoResource.A74_2_2_6,
			Vol3DokonokoResource.A74_2_2_7,
			Vol3DokonokoResource.A74_2_2_8,
			Vol3DokonokoResource.A74_2_2_9,
			Vol3DokonokoResource.A74_2_2_10,
	};
	
	private Sound[] mp3Cry = new Sound[8];
	private String[] idCry = {
			Vol3DokonokoResource.A74_4_2_5_1,
			Vol3DokonokoResource.A74_4_2_5_2,
			Vol3DokonokoResource.A74_4_2_5_3,
			Vol3DokonokoResource.A74_4_2_5_4,
			Vol3DokonokoResource.A74_4_2_5_5,
			Vol3DokonokoResource.A74_4_2_5_6,
			Vol3DokonokoResource.A74_4_2_5_7,
			Vol3DokonokoResource.A74_4_2_5_8,
	};
	
	private Sound mp3CurtainShow, mp3CurtainStopMove, mp3Pinpon, mp3Teko, mp3Hazure, mp3Omedeto, mp3Ato, mp3Deden, mp3Se3;
	private Rectangle rtCry;
	
	private int[][] idFromYCry = {{232, 394}, {249, 335}, {138, 447}, {248, 389}, {269, 379},
			{174, 248}, {248, 404}, {267, 292}, {199, 262}, {258, 356}};
	
	private int[][] idFirst = {
			{139, 100}, {119, 153}, {135, 110}, {102, 133}, {96, 133},
			{174, 99}, {167, 113}, {165, 98}, {165, 94}, {133, 131}
	};
	private int[][] idLast = {
			{358, 408}, {402, 383}, {385, 391}, {388, 376}, {404, 385},
			{338, 397}, {352, 410}, {360, 415}, {366, 413}, {371, 366}
	};
	
	
	@Override
	protected void loadKaraokeResources() {
		tpDokonoko1 = mTexturePackLoaderFromSource.load("dokonoko1.xml");
		tpDokonoko1.loadTexture();
		ttpLibDokonoko1 = tpDokonoko1.getTexturePackTextureRegionLibrary();
		
		tpDokonoko2 = mTexturePackLoaderFromSource.load("dokonoko2.xml");
		tpDokonoko2.loadTexture();
		ttpLibDokonoko2 = tpDokonoko2.getTexturePackTextureRegionLibrary();
		
		tpDokonoko3 = mTexturePackLoaderFromSource.load("dokonoko3.xml");
		tpDokonoko3.loadTexture();
		ttpLibDokonoko3 = tpDokonoko3.getTexturePackTextureRegionLibrary();
		
		tpDokonoko4 = mTexturePackLoaderFromSource.load("dokonoko4.xml");
		tpDokonoko4.loadTexture();
		ttpLibDokonoko4 = tpDokonoko4.getTexturePackTextureRegionLibrary();
		
		tpDokonoko5 = mTexturePackLoaderFromSource.load("dokonoko5.xml");
		tpDokonoko5.loadTexture();
		ttpLibDokonoko5 = tpDokonoko5.getTexturePackTextureRegionLibrary();
		
		tpDokonoko6 = mTexturePackLoaderFromSource.load("dokonoko6.xml");
		tpDokonoko6.loadTexture();
		ttpLibDokonoko6 = tpDokonoko6.getTexturePackTextureRegionLibrary();
		
		tpDokonoko7 = mTexturePackLoaderFromSource.load("dokonoko7.xml");
		tpDokonoko7.loadTexture();
		ttpLibDokonoko7 = tpDokonoko7.getTexturePackTextureRegionLibrary();
		
		tpDokonoko8 = mTexturePackLoaderFromSource.load("dokonoko8.xml");
		tpDokonoko8.loadTexture();
		ttpLibDokonoko8 = tpDokonoko8.getTexturePackTextureRegionLibrary();
		
		tpDokonoko9 = mTexturePackLoaderFromSource.load("dokonoko9.xml");
		tpDokonoko9.loadTexture();
		//ttpLibDokonoko9 = tpDokonoko9.getTexturePackTextureRegionLibrary();
		
		this.trBackground = ttpLibDokonoko2.get(Vol3DokonokoResource.A74_1_1_IPHONE_ID);
		this.trMushroomCenter1 = ttpLibDokonoko2.get(Vol3DokonokoResource.A74_1_2_1_IPHONE_ID);
		this.trMushroomCenter2 = ttpLibDokonoko2.get(Vol3DokonokoResource.A74_1_2_2_IPHONE_ID);
		
		this.trCurtain = ttpLibDokonoko2.get(Vol3DokonokoResource.A74_1_3_IPHONE_ID);
		this.trCry = ttpLibDokonoko8.get(Vol3DokonokoResource.A74_2_5_IPHONE_ID);
		
		//Winner
		ttrWinner = getTiledTextureFromPacker(tpDokonoko9, 
				Vol3DokonokoResource.A74_6_1_IPHONE_ID,
				Vol3DokonokoResource.A74_6_2_IPHONE_ID);
		
		//Mushrooms Center
		for (int i = 0; i < txrMushroomCenter.length; i++) {
			if(i < 5) {
				this.txrMushroomCenter[i] = ttpLibDokonoko2.get(iMushroomCenterId[i]);
			}
			if(i >= 5) {
				this.txrMushroomCenter[i] = ttpLibDokonoko3.get(iMushroomCenterId[i]);
			}
		}
		
		//Mushrooms Top
		for (int i = 0; i < txrMushroomTop.length; i++) {
			this.txrMushroomTop[i] = ttpLibDokonoko8.get(iMushroomTopId[i]);
		}
		
		//Mushrooms Right
		for (int i = 0; i < txrMushroomRight.length; i++) {
			this.txrMushroomRight[i] = ttpLibDokonoko8.get(iMushroomRightId[i]);
		}
		
		//Mushrooms NoFace
		for (int i = 0; i < txrMushroomNoFace.length; i++) {
			if(i < 3) {
				this.txrMushroomNoFace[i] = ttpLibDokonoko3.get(iMushroomNoFaceId[i]);
			} else {
				this.txrMushroomNoFace[i] = ttpLibDokonoko4.get(iMushroomNoFaceId[i]);
			}
		}
		
		//Mushrooms Normal
		this.txrMushroomNormal[0] = ttpLibDokonoko4.get(iMushroomNormalId[0]);
		for (int i = 1; i < txrMushroomNormal.length - 1; i++) {
			this.txrMushroomNormal[i] = ttpLibDokonoko5.get(iMushroomNormalId[i]);
		}
		this.txrMushroomNormal[9] = ttpLibDokonoko6.get(iMushroomNormalId[9]);
		
		//Mushrooms Fun
		for (int i = 0; i < txrMushroomFun.length; i++) {
			if(i < 7) {
				this.txrMushroomFun[i] = ttpLibDokonoko6.get(iMushroomFunId[i]);
			} else {
				this.txrMushroomFun[i] = ttpLibDokonoko7.get(iMushroomFunId[i]);
			}
		}
		
		//Mushrooms Bad
		for (int i = 0; i < txrMushroomBad.length; i++) {
			if(i < 5) {
				this.txrMushroomBad[i] = ttpLibDokonoko7.get(iMushroomBadId[i]);
			} else {
				this.txrMushroomBad[i] = ttpLibDokonoko8.get(iMushroomBadId[i]);
			}
		}
		
		//Label mushrooms
		for (int i = 0; i < txrMushroomLabel.length; i++) {
			this.txrMushroomLabel[i] = ttpLibDokonoko1.get(iMushroomLabelId[i]);
		}
		
	}

	@Override
	protected void loadKaraokeSound() {
		for (int i = 0; i < mp3MushroomCenterTouch.length; i++) {
			mp3MushroomCenterTouch[i] = loadSoundResourceFromSD(idMushroomCenterTouch[i]);
		}
		for (int i = 0; i < mp3MushroomTrue.length; i++) {
			mp3MushroomTrue[i] = loadSoundResourceFromSD(idMushroomTrue[i]);
		}
		for (int i = 0; i < mp3Cry.length; i++) {
			mp3Cry[i] = loadSoundResourceFromSD(idCry[i]);
		}
		
		mp3CurtainShow = loadSoundResourceFromSD(Vol3DokonokoResource.A74_1_3_SYU);
		mp3CurtainStopMove = loadSoundResourceFromSD(Vol3DokonokoResource.A74_1_3_KASYA);
		mp3Pinpon = loadSoundResourceFromSD(Vol3DokonokoResource.A74_2_1_PINPON);
		mp3Teko = loadSoundResourceFromSD(Vol3DokonokoResource.A74_2_1_TEKO);
		mp3Hazure = loadSoundResourceFromSD(Vol3DokonokoResource.A74_4_2_5_HAZURE);
		mp3Omedeto = loadSoundResourceFromSD(Vol3DokonokoResource.A74_6_1_OMEDETO2);
		mp3Ato = loadSoundResourceFromSD(Vol3DokonokoResource.A74_1_2_3_ATO5);
		mp3Deden = loadSoundResourceFromSD(Vol3DokonokoResource.A74_2_1_DEDEN);
		mp3Se3 = loadSoundResourceFromSD(Vol3DokonokoResource.A74_SE3);
	}

	@Override
	protected void loadKaraokeScene() {
		Log.d(TAG, "loadKaraokeScene");
		mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, this.trBackground, this.getVertexBufferObjectManager())));
		
		//Mushrooms center
		sMushroomCenter1 = new Sprite(248, 469, trMushroomCenter1, this.getVertexBufferObjectManager());
		mScene.attachChild(sMushroomCenter1);
		
		sMushroomCenter2 = new Sprite(237, 402, trMushroomCenter2, this.getVertexBufferObjectManager());
		mScene.attachChild(sMushroomCenter2);
		sMushroomCenter2.setVisible(false);
		
		//Mushrooms Top
		for (int i = 0; i < sMushroomTop.length; i++) {
			sMushroomTop[i] = new Sprite(iMushroomTopPosition[i][0], iMushroomTopPosition[i][1],
					txrMushroomTop[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomTop[i]);
			sMushroomTop[i].setVisible(false);
		}
		
		//Mushrooms Right
		for (int i = 0; i < sMushroomRight.length; i++) {
			sMushroomRight[i] = new Sprite(iMushroomRightPosition[i][0], iMushroomRightPosition[i][1], 
					txrMushroomRight[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomRight[i]);
			sMushroomRight[i].setVisible(false);
		}
		
		//Mushrooms Fun
		for (int i = 0; i < sMushroomFun.length; i++) {
			sMushroomFun[i] = new Sprite(91, 105, txrMushroomFun[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomFun[i]);
			sMushroomFun[i].setVisible(false);
		}
		
		//Mushrooms NoFace
		for (int i = 0; i < sMushroomNoFace.length; i++) {
			sMushroomNoFace[i] = new Sprite(iMushroomNoFacePosition[i][0], iMushroomNoFacePosition[i][1],
					txrMushroomNoFace[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomNoFace[i]);
			sMushroomNoFace[i].setVisible(false);
		}
		
		//Mushrooms Label
		for (int i = 0; i < sMushroomLabel.length; i++) {
			sMushroomLabel[i] = new Sprite(iMushroomLabelPosition[i][0], iMushroomLabelPosition[i][1], txrMushroomLabel[i], 
					this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomLabel[i]);
			sMushroomLabel[i].setVisible(false);
		}
		
		//REM
		sCurtain = new Sprite(179, 102, trCurtain, this.getVertexBufferObjectManager());
		mScene.attachChild(sCurtain);
		sCurtain.setVisible(false);
		
		//Mushrooms Center
		for (int i = 0; i < sMushroomCenter.length; i++) {
			sMushroomCenter[i] = new Sprite(91, 105, txrMushroomCenter[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomCenter[i]);
			sMushroomCenter[i].setVisible(false);
		}
		
		//Mushrooms Normal
		for (int i = 0; i < sMushroomNormal.length; i++) {
			sMushroomNormal[i] = new Sprite(91, 105, txrMushroomNormal[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomNormal[i]);
			sMushroomNormal[i].setVisible(false);
		}
		
		//Mushrooms Bad
		for (int i = 0; i < sMushroomBad.length; i++) {
			sMushroomBad[i] = new Sprite(91, 105, txrMushroomBad[i], this.getVertexBufferObjectManager());
			mScene.attachChild(sMushroomBad[i]);
			sMushroomBad[i].setVisible(false);
		}
		
		//Winner
		asWinner = new AnimatedSprite(0, 0, ttrWinner, this.getVertexBufferObjectManager());
		mScene.attachChild(asWinner);
		asWinner.setVisible(false);
				
		//Cry
		rtCry = new Rectangle(400, 400, 50, 50, this.getVertexBufferObjectManager());
		rtCry.setColor(Color.TRANSPARENT);
		mScene.attachChild(rtCry);
		rtCry.setVisible(false);
		
		sCryLeft = new Sprite(-10, 0, trCry, this.getVertexBufferObjectManager());
		rtCry.attachChild(sCryLeft);
		
		sCryBetween = new Sprite(20, 5, trCry, this.getVertexBufferObjectManager());
		rtCry.attachChild(sCryBetween);
		
		sCryRight = new Sprite(50, -5, trCry, this.getVertexBufferObjectManager());
		rtCry.attachChild(sCryRight);
		
		addGimmicsButton(mScene, Vol3DokonokoResource.SOUND_GIMMIC, Vol3DokonokoResource.IMAGE_GIMMIC, ttpLibDokonoko8);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	public void onCreateResources() {
		SoundFactory.setAssetBasePath("dokonoko/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("dokonoko/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "dokonoko/gfx/");
		super.onCreateResources();
	}

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		isBengin = hasFocus;
//	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		initial();
		mScene.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
				
			}
			
			@Override
			public void onUpdate(float arg0) {
				try {
					isBengin = WINDOW_FOCUS_CHANGE;
				} catch (Exception e) {
					// TODO: handle exception
					isBengin = true;
				}
				if(isBengin) {
					mScene.clearUpdateHandlers();
					mScene.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							// TODO Auto-generated method stub
							actionCurtain();
						}
					}));
				}
			}
		});
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		mScene.clearEntityModifiers();
		mScene.clearUpdateHandlers();
		if(!rdIdMushroomRight.isEmpty()) {
			rdIdMushroomRight.clear();
		}
		
		if(!idMushRoom.isEmpty()) {
			idMushRoom.clear();
		}
		
		if(sMushroomFun[idCenter] != null) {
			sMushroomFun[idCenter].clearEntityModifiers();
			sMushroomFun[idCenter].setScale(1);
			sMushroomFun[idCenter].setPosition(sMushroomFun[idCenter].getmXFirst(), sMushroomFun[idCenter].getmYFirst());
		}
		
		if(!isTouchScene) {
			isTouchScene = true;
		}
		if(isWinner) {
			isWinner = false;
		}
		if(!isTouchSound) {
			isTouchSound = true;
		}
		idCenter = - 1;
		sCurtain.clearEntityModifiers();
		sMushroomCenter1.setVisible(true);
		sMushroomCenter2.setVisible(false);
		rtCry.clearEntityModifiers();
		rtCry.setVisible(false);
		resetData();
	}

	private void initial() {
		isWinner = false;
		isTouchScene = true;
		isTouchSound = true;
		idCenter = rdMushroom.nextInt(10);
		indexPositionTop = 0;
		if(!idMushRoom.isEmpty()) {
			idMushRoom.clear();
		}
		for (int i = 0; i < 10; i++) {
			idMushRoom.add(i);
		}
	}
	
	private void resetData() {
		initial();
		if(asWinner != null) {
			asWinner.stopAnimation();
			asWinner.setVisible(false);
		}
		for (int i = 0; i < sMushroomTop.length; i++) {
			sMushroomTop[i].setVisible(false);
		}
		
		if(isWinner) {
			isWinner = false;
		}
		
		if(!isTouchScene) {
			isTouchScene = true;
		}
		
		for (int i = 0; i < sMushroomCenter.length; i++) {
			if(sMushroomCenter[i].isVisible()) {
				sMushroomCenter[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < sMushroomBad.length; i++) {
			if(sMushroomBad[i].isVisible()) {
				sMushroomBad[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < sMushroomLabel.length; i++) {
			if(sMushroomLabel[i].isVisible()) {
				sMushroomLabel[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < sMushroomNoFace.length; i++) {
			if(sMushroomNoFace[i].isVisible()) {
				sMushroomNoFace[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < sMushroomNormal.length; i++) {
			if(sMushroomNormal[i].isVisible()) {
				sMushroomNormal[i].setVisible(false);
			}
		}
		
		for (int i = 0; i < sMushroomFun.length; i++) {
			if(sMushroomFun[i].isVisible()) {
				sMushroomFun[i].setVisible(false);
			}
		}
		
		if(sCurtain.isVisible()) {
			sCurtain.setPosition(sCurtain.getmXFirst(), sCurtain.getmYFirst());
			sCurtain.setVisible(false);
		}
		
		if(indexPositionTop != 0) {
			indexPositionTop = 0;
		}
		
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		int pX = (int) pSceneTouchEvent.getX();
		int pY = (int) pSceneTouchEvent.getY();
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			for (int i = 0; i < rdIdMushroomRight.size(); i++) {
				if(sMushroomRight[rdIdMushroomRight.get(i)].contains(pX, pY) && isTouchScene) {
					Log.d(TAG, "Id: " + rdIdMushroomRight.get(i));
					isTouchScene = false;
					moveCurtainLeft(i);
				}
			}
			for (int i = 0; i < sMushroomCenter.length; i++) {
				if(sMushroomCenter[i].isVisible() && isTouchSound && 
						checkContains(sMushroomCenter[i], idFirst[i][0], idFirst[i][1], idLast[i][0], idLast[i][1], pX, pY)) {
					isTouchSound = false;
					mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler arg0) {
							isTouchSound = true;
						}
					}));
					mp3MushroomCenterTouch[i].play();
				}
			}
		}
		
		return false;
	}
	
	private void actionCurtain() {
		mp3CurtainShow.play();
		sCurtain.setVisible(true);
		sCurtain.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					}
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						randomMushroomRight();
						isTouchScene = true;
						Log.d(TAG, "ket thuc chuyen dong");
					}
				},
				new MoveXModifier(0.7f, -sCurtain.getWidth(), sCurtain.getmXFirst()),
				new RotationAtModifier(0.2f, 0, -20, sCurtain.getWidth() / 2, 0, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						mp3CurtainStopMove.play();
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					}
				}),
				new RotationAtModifier(0.2f, -20, 0, sCurtain.getWidth() / 2, 0)));
	}
	
	private void randomMushroomBlack(int iMushroomBlack) {
		mp3Deden.play();
		sMushroomCenter[iMushroomBlack].setVisible(true);
	}
	
	private void randomMushroomRight() {
		int i = 0;
		rdSwap();
		if(isWinner) {
			isWinner = false;
			idCenter = idMushRoom.get(rdMushroom.nextInt(idMushRoom.size()));
		}
		while(i <= 8) {
			
			if(idCenter == iIndexMushroomRight[i] || idCenter == iIndexMushroomRight[i + 1]) {
				sMushroomRight[idCenter].setVisible(true);
				if(idCenter == iIndexMushroomRight[i]) {
					sMushroomRight[iIndexMushroomRight[i+1]].setVisible(false);
				} else {
					sMushroomRight[iIndexMushroomRight[i]].setVisible(false);
				}
				rdIdMushroomRight.add(idCenter);
			} else {
				int index = iIndexMushroomRight[i];
				sMushroomRight[index].setVisible(true);
				sMushroomRight[iIndexMushroomRight[i+1]].setVisible(false);
				rdIdMushroomRight.add(index);
			}
			i = i + 2;
			
		}
		Log.d(TAG, "id random mushroom right: " + idCenter);
		randomMushroomBlack(idCenter);
	}
	
	private void rdSwap() {
		int i = 0;
		while(i <= 8) {
			int tmp = iIndexMushroomRight[i];
			iIndexMushroomRight[i] = iIndexMushroomRight[i+1];
			iIndexMushroomRight[i + 1] = tmp;
			i = i + 2;
		}
		for (int j = 0; j < iIndexMushroomRight.length; j++) {
			Log.i(TAG, "ID: " +iIndexMushroomRight[j]);
		}
	}
	
	private void moveCurtainLeft(final int iIndex) {
		sCurtain.registerEntityModifier(new SequenceEntityModifier(
				new MoveXModifier(0.7f, sCurtain.getmXFirst(), -sCurtain.getWidth()-100, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						sMushroomCenter[idCenter].setVisible(false);
						sMushroomNoFace[idCenter].setVisible(true);
						if(rdIdMushroomRight.get(iIndex) == idCenter) {
							mp3Pinpon.play();
							sMushroomLabel[idCenter].setVisible(true);
						} else {
							mp3Hazure.play();
						}
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						if(rdIdMushroomRight.get(iIndex) == idCenter) {
							Log.d(TAG, "Bang nhau");
							mp3MushroomTrue[idCenter].play();
							isWinner = true;
							actionWin(idCenter, iPositionTopTrue[indexPositionTop][0], iPositionTopTrue[indexPositionTop][1]);
							
						} else {
							actionLoser(idCenter, 91, -450);
						}
						rdIdMushroomRight.clear();
					}
				})));
	}
	
	private void actionWin(final int idMushroom, final int pToX, final int pToY) {
		
		mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sMushroomFun[idMushroom].setVisible(true);
				sMushroomNoFace[idMushroom].setVisible(false);
				mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						// TODO Auto-generated method stub
						sMushroomFun[idMushroom].registerEntityModifier(new ParallelEntityModifier(new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								if(idMushRoom.size() == 6) {
									mp3Ato.play();
								}
							}
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								if(idMushRoom.size() > 0) {
									if(idMushRoom.size() > 1) {
										mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
											
											@Override
											public void onTimePassed(TimerHandler arg0) {
												// TODO Auto-generated method stub
												actionCurtain();
											}
										}));
									}
									sMushroomLabel[idMushroom].setVisible(false);
									idMushRoom.remove((Integer)idCenter);
									sMushroomFun[idMushroom].setVisible(false);
									sMushroomFun[idMushroom].setScale(1);
									sMushroomFun[idMushroom].setPosition(sMushroomFun[idMushroom].getmXFirst(), 
											sMushroomFun[idMushroom].getmYFirst());
									sMushroomTop[idMushroom].setPosition(iMushroomTopPosition[indexPositionTop][0],
											iMushroomTopPosition[indexPositionTop][1]);
									sMushroomTop[idMushroom].setVisible(true);
									indexPositionTop++;
									Log.i(TAG, "idMushroom: " + idMushRoom.size());
								}
								
								if(idMushRoom.size() == 0) {
									Log.d(TAG, "Win....................");
									mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
										
										@Override
										public void onTimePassed(TimerHandler arg0) {
											winner();
										}
									}));
								}
							}
						}, new ScaleModifier(1.5f, 1f, 0.2f),
						   new MoveModifier(1.5f, sMushroomFun[idMushroom].getmXFirst(), pToX, sMushroomFun[idMushroom].getmYFirst(), pToY,
								   new IEntityModifierListener() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
										mp3Teko.play();
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
									}
								})));
					}
				}));
			}//
		}));
	}
	
	private void winner() {
		mp3Omedeto.play();
		long pDurations[] = new long[] {400, 400};
		asWinner.setVisible(true);
		asWinner.animate(pDurations);
		mScene.registerUpdateHandler(new TimerHandler(6.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				resetData();
				actionCurtain();
			}
		}));
	}
	
	private void actionLoser(final int idMushroom, final int pFromX, final int pToX) {
		Log.d(TAG, "Loser...................");
		sMushroomNoFace[idMushroom].setVisible(false);
		sMushroomNormal[idMushroom].setVisible(true);
		mScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler arg0) {
				sMushroomBad[idMushroom].setVisible(true);
				sMushroomNormal[idMushroom].setVisible(false);
				sMushroomCenter1.setVisible(false);
				sMushroomCenter2.setVisible(true);
				cry(idFromYCry[idMushroom][0], idFromYCry[idMushroom][1]);
				int idMusic = rdMushroom.nextInt(8);
				rdMusic(idMusic);
				mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler arg0) {
						sMushroomBad[idMushroom].registerEntityModifier(new MoveXModifier(1.5f, pFromX, pToX, new IEntityModifierListener() {
							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								sMushroomBad[idMushroom].setPosition(
										sMushroomBad[idMushroom].getmXFirst(), sMushroomBad[idMushroom].getmYFirst());
								sMushroomBad[idMushroom].setVisible(false);
								sMushroomCenter1.setVisible(true);
								sMushroomCenter2.setVisible(false);
								actionCurtain();
							}
						}));
					}
				}));
			}
		}));
	}
	
	private void rdMusic(int idMusic) {
		switch (idMusic) {
		case 0:
			mp3Cry[0].play();
			break;
		case 1:
			mp3Cry[1].play();
			break;
		case 2:
			mp3Cry[2].play();
			break;
		case 3:
			mp3Cry[3].play();
			break;
		case 4:
			mp3Cry[4].play();
			break;
		case 5:
			mp3Cry[5].play();
			break;
		case 6:
			mp3Cry[6].play();
			break;
		case 7:
			mp3Cry[7].play();
			break;

		default:
			break;
		}
	}
	
	private void cry(int pFromX, int pFromY ) {
		rtCry.registerEntityModifier(new MoveModifier(1.0f, pFromX, pFromX, pFromY, pFromY + 50, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				rtCry.setVisible(true);
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rtCry.setVisible(false);
			}
		}));
	}
	
	@Override
	public void combineGimmic3WithAction() {
		mp3Se3.play();
	}
}