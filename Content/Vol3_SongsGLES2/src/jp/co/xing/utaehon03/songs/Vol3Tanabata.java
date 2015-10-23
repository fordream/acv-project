/* Vol3Tanabata.java
* Create on Jun 1, 2012
*/
package jp.co.xing.utaehon03.songs;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationAtModifier;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.SpritePool;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TanabataResource;

public class Vol3Tanabata extends BaseGameActivity implements 
IEntityModifierListener, IOnSceneTouchListener, IAnimationListener{
	
	public final static String TAG = " Vol3Tanabata "; 
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack ttpTanabata1, ttpTanabata2, ttpTanabata3, ttpTanabata4;
	private TexturePackTextureRegionLibrary ttpLibTanabata1, ttpLibTanabata2, ttpLibTanabata4;
	
	private TextureRegion trHaikeiue, trSasaTree, trSasaLeaf1, trSasaLeaf2, trSasaLeaf3, trSasaLeaf4, trHashi,
			trHashiLight, trGirlHand, trHashiFront, trHikari, trSasaTreeMove;
	private TextureRegion trTanzaku[] = new TextureRegion[12];
	private TextureRegion trStar[] = new TextureRegion[8];
	private Sprite sHaikeiue, sSasaTree, sSasaLeaf1, sSasaLeaf2, sSasaLeaf3, sSasaLeaf4, sHashi,
			sHashiLight, sGirlHand, sHashiFront, sSasaTreeMove;
	private Sprite sTanzaku[] = new Sprite[12];
	private Sprite sTanzakuTree[] = new Sprite[12];
	private Sprite sHikari[] = new Sprite[12];
	private SpritePool sStarPool[] = new SpritePool[8];
	private TiledTextureRegion ttrBackground, ttrTown,  ttrSasa, ttrBoy, ttrGirl,ttrOrihime, ttrHikoboshi, 
			ttrMeet, ttrMeetStat, ttrStar;
	private AnimatedSprite aniBackground, aniTown, aniSasa, aniStar, aniBoy, aniGirl,
			aniOrihime, aniHikoboshi, aniMeet, aniMeetStat;
	
	private boolean istouchTown, istouchHashi, touchBoy, touchGirl, istouchSasa, istouchTree, 
			isTouchStar, isCheckMeet, istouchHikoboshi;
	private int currentTanzaku;
	private float arrPointStar[][]= {
			{0, -60, -60, -60, 0, 60, 60, 60},
			{-60, -60, 0, 60, 60, 60, 0, -60}
	};
	
	private float arrPointTanzaku[][]= {
			{125, 0, 718, 56, 838, 218, 24, 101, 810, 147, 80, 68},
			{54, 22, -80, 30, -11, 128, 104, 19, -17, 48, 21, 40}
	};
	private float arrPointHikari[][]= {
			{700, 588, 672, 645, 792, 791, 865, 692, 764, 732, 662, 640},
			{95, 178, -40, 280, 20, 164, 105, 284, 20, 196, 166, 72}
	};
	
	private int  pLastX = 0;
	private int  pLastY = 0;
	
	private Sound A1_10_SAWA, A1_11_OTOHIMEHENKA, A1_13_FUTARI, A1_15_SASAHENKA, A1_16_HOSHI,
			A1_4_HOSHITACHI, A1_5_GIRLTANZAKU, A1_6_MACHIAKARI, A1_7_HASHITACHI, A1_8_BOYJUMP, A1_8_EI; 
	
	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateResources: ");
		SoundFactory.setAssetBasePath("tanabata/mfx/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tanabata/gfx/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(this.getTextureManager(),
				pAssetManager, "tanabata/gfx/");
		super.onCreateResources();
		
	}
	
	@Override
	protected void loadKaraokeResources() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeResources ");
		ttpTanabata1 = mTexturePackLoaderFromSource.load("tanabata1.xml");
		ttpTanabata1.loadTexture();
		ttpLibTanabata1 = ttpTanabata1.getTexturePackTextureRegionLibrary();
		ttpTanabata2 = mTexturePackLoaderFromSource.load("tanabata2.xml");
		ttpTanabata2.loadTexture();
		ttpLibTanabata2 = ttpTanabata2.getTexturePackTextureRegionLibrary();
		ttpTanabata3 = mTexturePackLoaderFromSource.load("tanabata3.xml");
		ttpTanabata3.loadTexture();
//		ttpLibTanabata3 = ttpTanabata3.getTexturePackTextureRegionLibrary();
		
		ttpTanabata4 = mTexturePackLoaderFromSource.load("tanabata4.xml");
		ttpTanabata4.loadTexture();
		ttpLibTanabata4 = ttpTanabata4.getTexturePackTextureRegionLibrary();
		ttrBackground= getTiledTextureFromPacker(ttpTanabata3,
				Vol3TanabataResource.A1_17_1_IPHONE_HAIKEI_ID,
				Vol3TanabataResource.A1_17_2_IPHONE_HAIKEI_ID,
				Vol3TanabataResource.A1_17_3_IPHONE_HAIKEI_ID);
		ttrTown = getTiledTextureFromPacker(ttpTanabata2, 
				Vol3TanabataResource.A1_6_1_IPHONE_TOWN_ID,
				Vol3TanabataResource.A1_6_2_IPHONE_TOWN_ID,
				Vol3TanabataResource.A1_6_3_IPHONE_TOWN_ID,
				Vol3TanabataResource.A1_6_4_IPHONE_TOWN_ID);
		
		trHashi = ttpLibTanabata1.get(Vol3TanabataResource.A1_7_1_IPHONE_HASHI_BACK_ID);
		trHashiLight = ttpLibTanabata1.get(Vol3TanabataResource.A1_7_3_IPHONE_HASHI_ID);
		ttrSasa = getTiledTextureFromPacker(ttpTanabata1, 
				Vol3TanabataResource.A1_15_1_IPHONE_SASA_ID,
				Vol3TanabataResource.A1_15_2_IPHONE_SASA_ID,
				Vol3TanabataResource.A1_15_3_IPHONE_SASA_ID);
		ttrStar = getTiledTextureFromPacker(ttpTanabata1, 
				Vol3TanabataResource.A1_4_1_1_IPHONE_STAR_ID,
				Vol3TanabataResource.A1_4_1_2_IPHONE_STAR_ID,
				Vol3TanabataResource.A1_4_2_1_IPHONE_STAR_ID,
				Vol3TanabataResource.A1_4_2_2_IPHONE_STAR_ID,
				Vol3TanabataResource.A1_4_3_1_IPHONE_STAR_ID,
				Vol3TanabataResource.A1_4_3_2_IPHONE_STAR_ID);
		
		ttrBoy = getTiledTextureFromPacker(ttpTanabata2, 
				Vol3TanabataResource.A1_8_1_IPHONE_BOY_ID,
				Vol3TanabataResource.A1_8_2_IPHONE_BOY_ID);
		ttrGirl = getTiledTextureFromPacker(ttpTanabata2,
				Vol3TanabataResource.A1_9_1_IPHONE_GIRL_ID,
				Vol3TanabataResource.A1_9_2_IPHONE_GIRL_ID,
				Vol3TanabataResource.A1_9_3_IPHONE_GIRL_ID,
				Vol3TanabataResource.A1_9_4_IPHONE_GIRL_ID);
		ttrOrihime = getTiledTextureFromPacker(ttpTanabata1,
				Vol3TanabataResource.A1_11_1_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_11_2_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_11_3_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_11_4_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_12_1_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_12_2_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_12_3_IPHONE_ORIHIME_ID,
				Vol3TanabataResource.A1_12_4_IPHONE_ORIHIME_ID);
		ttrHikoboshi = getTiledTextureFromPacker(ttpTanabata1,
				Vol3TanabataResource.A1_13_1_IPHONE_HIKOBOSHI_ID,
				Vol3TanabataResource.A1_13_2_IPHONE_HIKOBOSHI_ID);
		
		ttrMeet = getTiledTextureFromPacker(ttpTanabata1,
				Vol3TanabataResource.A1_14_1_IPHONE_MEET_ID,
				Vol3TanabataResource.A1_14_2_IPHONE_MEET_ID,
				Vol3TanabataResource.A1_14_3_IPHONE_MEET_ID,
				Vol3TanabataResource.A1_14_4_IPHONE_MEET_ID);
		
		ttrMeetStat = getTiledTextureFromPacker(ttpTanabata1,
				Vol3TanabataResource.A1_14_5_IPHONE_MEET_ID,
				Vol3TanabataResource.A1_14_6_IPHONE_MEET_ID);
		
		trGirlHand = ttpLibTanabata2.get(Vol3TanabataResource.A1_9_5_IPHONE_GIRL_HAND_ID);
		
		trSasaTree = ttpLibTanabata1.get(Vol3TanabataResource.A1_10_1_IPHONE_SASA_ID);
		trSasaLeaf1 = ttpLibTanabata1.get(Vol3TanabataResource.A1_10_2_IPHONE_SASA_ID);
		trSasaLeaf2 = ttpLibTanabata1.get(Vol3TanabataResource.A1_10_3_IPHONE_SASA_ID);
		trSasaLeaf3 = ttpLibTanabata1.get(Vol3TanabataResource.A1_10_4_IPHONE_SASA_ID);
		trSasaLeaf4 = ttpLibTanabata1.get(Vol3TanabataResource.A1_10_5_IPHONE_SASA_ID);
		
		trHaikeiue = ttpLibTanabata2.get(Vol3TanabataResource.A1_18_IPHONE_HAIKEI_UE_ID);
		trHashiFront = ttpLibTanabata1.get(Vol3TanabataResource.A1_7_2_IPHONE_HASHI_ID);
		trTanzaku[0] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_1_IPHONE_TANZAKU_ID);
		trTanzaku[1] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_2_IPHONE_TANZAKU_ID);
		trTanzaku[2] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_3_IPHONE_TANZAKU_ID);
		trTanzaku[3] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_4_IPHONE_TANZAKU_ID);
		trTanzaku[4] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_5_IPHONE_TANZAKU_ID);
		trTanzaku[5] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_6_IPHONE_TANZAKU_ID);
		trTanzaku[6] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_7_IPHONE_TANZAKU_ID);
		trTanzaku[7] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_8_IPHONE_TANZAKU_ID);
		trTanzaku[8] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_9_IPHONE_TANZAKU_ID);
		trTanzaku[9] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_10_IPHONE_TANZAKU_ID);
		trTanzaku[10] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_11_IPHONE_TANZAKU_ID);
		trTanzaku[11] = ttpLibTanabata1.get(Vol3TanabataResource.A1_5_12_IPHONE_TANZAKU_ID);
		trHikari = ttpLibTanabata1.get(Vol3TanabataResource.A1_19_IPHONE_HIKARI_ID);
		trSasaTreeMove = ttpLibTanabata4.get(Vol3TanabataResource.A1_10_IPHONE_SASA_TREE_MOVE_ID);
		for (int i = 0; i < trStar.length; i++) {
			trStar[i]= ttpLibTanabata1.get(Vol3TanabataResource.PACK_STAR[i]);
		}
		
	}

	@Override
	protected void loadKaraokeScene() {
		// TODO Auto-generated method stub
		Log.d(TAG, "loadKaraokeScene ");
		mScene = new Scene();
		aniBackground = new AnimatedSprite(0, 0, ttrBackground, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBackground);
		aniTown = new AnimatedSprite(-8, 342, ttrTown, this.getVertexBufferObjectManager());
		mScene.attachChild(aniTown);
		sHaikeiue = new Sprite(0, 360, trHaikeiue, this.getVertexBufferObjectManager());
		mScene.attachChild(sHaikeiue);
		
		sHashiLight = new Sprite(332, 97, trHashiLight, this.getVertexBufferObjectManager());
		mScene.attachChild(sHashiLight);
		sHashiLight.setVisible(false);
		sHashi = new Sprite(332, 97, trHashi, this.getVertexBufferObjectManager());
		mScene.attachChild(sHashi);
		sSasaTreeMove = new Sprite(0, 0, trSasaTreeMove, this.getVertexBufferObjectManager());
		mScene.attachChild(sSasaTreeMove);
		
		//-----------------------------------
		//add sasa leaf and sasa tree in sasa tree move
		
		sSasaLeaf1 = new Sprite(619, 10, trSasaLeaf1, this.getVertexBufferObjectManager());
		sSasaTreeMove.attachChild(sSasaLeaf1);
		
		sSasaLeaf2 = new Sprite(890, -46, trSasaLeaf2, this.getVertexBufferObjectManager());
		sSasaTreeMove.attachChild(sSasaLeaf2);
		
		sSasaLeaf3 = new Sprite(630, 125, trSasaLeaf3, this.getVertexBufferObjectManager());
		sSasaTreeMove.attachChild(sSasaLeaf3);
		
		sSasaLeaf4 = new Sprite(644, 241, trSasaLeaf4, this.getVertexBufferObjectManager());
		sSasaTreeMove.attachChild(sSasaLeaf4);
		
		sSasaTree = new Sprite(813, -50, trSasaTree, this.getVertexBufferObjectManager());
		sSasaTreeMove.attachChild(sSasaTree);
		
		//----------------------------------
		aniOrihime = new AnimatedSprite(505, 7, ttrOrihime, this.getVertexBufferObjectManager());
		mScene.attachChild(aniOrihime);
		
		aniHikoboshi = new AnimatedSprite(242, 20, ttrHikoboshi, this.getVertexBufferObjectManager());
		mScene.attachChild(aniHikoboshi);
		
		aniMeetStat = new AnimatedSprite(310, 0, ttrMeetStat, this.getVertexBufferObjectManager());
		mScene.attachChild(aniMeetStat);
		aniMeetStat.setVisible(false);
		aniMeet = new AnimatedSprite(310, -5, ttrMeet, this.getVertexBufferObjectManager());
		mScene.attachChild(aniMeet);
		aniMeet.setVisible(false);
		sHashiFront = new Sprite(332, 97, trHashiFront, this.getVertexBufferObjectManager());
		mScene.attachChild(sHashiFront);
		
		
		//-------------------------------
		for (int i = 0; i < sTanzakuTree.length; i++) {
			 sTanzakuTree[i]= new Sprite(arrPointTanzaku[0][i],arrPointTanzaku[1][i] , 
					 trTanzaku[i], this.getVertexBufferObjectManager());
		}
		sSasaLeaf1.attachChild(sTanzakuTree[0]);
		sSasaLeaf1.attachChild(sTanzakuTree[5]);
		sSasaLeaf1.attachChild(sTanzakuTree[11]);
		sSasaLeaf2.attachChild(sTanzakuTree[6]);
		sSasaLeaf3.attachChild(sTanzakuTree[1]);
		sSasaLeaf3.attachChild(sTanzakuTree[10]);
		sSasaLeaf3.attachChild(sTanzakuTree[9]);
		sSasaLeaf4.attachChild(sTanzakuTree[3]);
		sSasaLeaf4.attachChild(sTanzakuTree[7]);
		sSasaTreeMove.attachChild(sTanzakuTree[2]);
		sSasaTreeMove.attachChild(sTanzakuTree[4]);
		sSasaTreeMove.attachChild(sTanzakuTree[8]);
		
		for (int i = 0; i < sTanzakuTree.length; i++) {
			sTanzakuTree[i].setVisible(false);
		}
		
		for (int i = 0; i < sHikari.length; i++) {
			sHikari[i]= new Sprite(arrPointHikari[0][i],arrPointHikari[1][i], trHikari,
					this.getVertexBufferObjectManager());
			mScene.attachChild(sHikari[i]);
			sHikari[i].setVisible(false);
		}
		//--------------------------------
		aniStar = new AnimatedSprite(457, 193, ttrStar, this.getVertexBufferObjectManager());
		mScene.attachChild(aniStar);
		
		aniBoy = new AnimatedSprite(476, 227, ttrBoy, this.getVertexBufferObjectManager());
		mScene.attachChild(aniBoy);
		
		aniGirl = new AnimatedSprite(706, 206, ttrGirl, this.getVertexBufferObjectManager());
		mScene.attachChild(aniGirl);
		
		sTanzaku[0]= new Sprite(7, 212, trTanzaku[0], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[0]);
		
		sTanzaku[1]= new Sprite(0, 221, trTanzaku[1], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[1]);
		sTanzaku[1].setVisible(false);
		
		sTanzaku[2]= new Sprite(0, 221, trTanzaku[2], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[2]);
		sTanzaku[2].setVisible(false);
		sTanzaku[3]= new Sprite(7, 214, trTanzaku[3], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[3]);
		sTanzaku[3].setVisible(false);
		sTanzaku[4]= new Sprite(7, 212, trTanzaku[4], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[4]);
		sTanzaku[4].setVisible(false);
		sTanzaku[5]= new Sprite(7, 218, trTanzaku[5], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[5]);
		sTanzaku[5].setVisible(false);
		sTanzaku[6]= new Sprite(7, 214, trTanzaku[6], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[6]);
		sTanzaku[6].setVisible(false);
		
		sTanzaku[7]= new Sprite(0, 226, trTanzaku[7], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[7]);
		sTanzaku[7].setVisible(false);
		sTanzaku[8]= new Sprite(7, 225, trTanzaku[8], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[8]);
		sTanzaku[8].setVisible(false);
		sTanzaku[9]= new Sprite(0, 240, trTanzaku[9], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[9]);
		sTanzaku[9].setVisible(false);
		sTanzaku[10]= new Sprite(15, 225, trTanzaku[10], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[10]);
		sTanzaku[10].setVisible(false);
		sTanzaku[11]= new Sprite(12, 220, trTanzaku[11], this.getVertexBufferObjectManager());
		aniGirl.attachChild(sTanzaku[11]);
		sTanzaku[11].setVisible(false);
		sGirlHand = new Sprite(0, 0, trGirlHand, this.getVertexBufferObjectManager());
		aniGirl.attachChild(sGirlHand);
		
		aniSasa = new AnimatedSprite(-10, 128, ttrSasa, this.getVertexBufferObjectManager());
		mScene.attachChild(aniSasa);
		
		for (int i = 0; i < sStarPool.length; i++) {
			 sStarPool[i] = new SpritePool(trStar[i], this.getVertexBufferObjectManager());
			
		}
		addGimmicsButton(mScene, Vol3TanabataResource.SOUND_GIMMIC, 
				Vol3TanabataResource.IMAGE_GIMMIC, ttpLibTanabata1);
		mScene.setOnSceneTouchListener(this);
		mScene.registerTouchArea(aniBackground);
		Log.d(TAG, "loadKaraokeScene end ");
	}
	
	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		A1_10_SAWA = loadSoundResourceFromSD(Vol3TanabataResource.A1_10_SAWA);
		A1_11_OTOHIMEHENKA = loadSoundResourceFromSD(Vol3TanabataResource.A1_11_OTOHIMEHENKA);
		A1_13_FUTARI = loadSoundResourceFromSD(Vol3TanabataResource.A1_13_FUTARI);
		A1_15_SASAHENKA = loadSoundResourceFromSD(Vol3TanabataResource.A1_15_SASAHENKA);
		A1_16_HOSHI = loadSoundResourceFromSD(Vol3TanabataResource.A1_16_HOSHI);
		A1_4_HOSHITACHI = loadSoundResourceFromSD(Vol3TanabataResource.A1_4_HOSHITACHI);
		A1_5_GIRLTANZAKU = loadSoundResourceFromSD(Vol3TanabataResource.A1_5_GIRLTANZAKU);
		A1_6_MACHIAKARI = loadSoundResourceFromSD(Vol3TanabataResource.A1_6_MACHIAKARI);
		A1_7_HASHITACHI = loadSoundResourceFromSD(Vol3TanabataResource.A1_7_HASHITACHI);
		A1_8_BOYJUMP = loadSoundResourceFromSD(Vol3TanabataResource.A1_8_BOYJUMP);
		A1_8_EI = loadSoundResourceFromSD(Vol3TanabataResource.A1_8_EI);
		
	}
	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		if (isTouchGimmic[2]) {
			isTouchGimmic[2] = false;
			touchTown();
		}
	}
	@Override
	public synchronized void onPauseGame() {
		Log.d(TAG, "onPauseGame ");
		resetData();
		super.onPauseGame();
	}
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame ");
		moveBackground();
		initial();
		loadRandomStar();
		super.onResumeGame();
	}
	private void resetData() {
		if (aniHikoboshi != null) {
		setUnVisibleAS(aniHikoboshi);
		setUnVisibleAS(aniOrihime);
		setUnVisibleAS(aniSasa);
		aniBoy.clearEntityModifiers();
		aniBoy.setPosition(476, 227);
		aniBoy.setCurrentTileIndex(0);
		setUnVisibleAS(aniTown);
		setUnVisibleAS(aniGirl);
		}
		if (sTanzakuTree[0] != null) {
			for (int i = 0; i < sTanzakuTree.length; i++) {
				setUnVisibleS(sTanzakuTree[i]);
				sTanzakuTree[i].setVisible(false);
				sTanzakuTree[i].setRotation(0);
				sTanzaku[i].setVisible(false);
			}
			sTanzaku[0].setVisible(true);
		}
		setTouchGimmic(1,true);
		setTouchGimmic(0,true);
		initial();
	}
	private void setUnVisibleAS(final AnimatedSprite anis){
		if(anis!=null ){
			if(anis.isAnimationRunning()){
				anis.stopAnimation();
			}
			anis.clearEntityModifiers();
			anis.clearUpdateHandlers();
			anis.setPosition(anis.getmXFirst(), anis.getmYFirst());
			anis.setCurrentTileIndex(0);
		}
	}
	private void setUnVisibleS(final Sprite s){
		if( s!=null ){
			s.clearEntityModifiers();
			s.clearUpdateHandlers();
			s.setPosition(s.getmXFirst(), s.getmYFirst());
		}
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		
		int pX = (int) pTouchEvent.getX();
		int pY = (int) pTouchEvent.getY();
		if (pTouchEvent.isActionMove()) {
			Log.d(TAG, "ACTION_MOVE:");
			if (checkContains(sHashi, 48, 20, 160, 135, pX, pY)) {
				
			} else if (checkContains(aniStar, 23, 8, 140, 111, pX, pY)){
				
			}else if (checkContains(aniHikoboshi, 15, 7, 111, 168, pX, pY)) {
				
			}else if (checkContains(aniOrihime, 1, 1, 122, 187, pX, pY)){
				
			} else if (checkContains(sSasaTreeMove, 650, 24, 950, 362, pX, pY)){
				
			}
			else
			if (checkContains(aniBackground, 24, 22, 876, 280, pX, pY)) {
				Log.d(TAG, "value pX: " + pX);
				Log.d(TAG, "value pY: " + pY);
				touchSky(pX,pY);
			}
		} else
		if (pTouchEvent.isActionDown()) {
			Log.d(TAG, "ACTION_DOWN");
			if (checkContains(aniSasa, 46, 120, 248, 520, pX, pY)) {
				touchSasa();
			} else if (checkContains(aniTown, 15, 16, 480, 162, pX, pY)) {
				touchTown();
				
			}
			
			if (checkContains(aniBoy, 56, 64, 208, 416, pX, pY)) {
				touchBoy();
			} else
			if (checkContains(aniGirl, 40, 65, 202, 440, pX, pY)) {
				
				if (currentTanzaku < 12) {
					touchGirl();
				} else if (currentTanzaku == 12) {
					for (int i = 0; i < sTanzakuTree.length; i++) {
						 sTanzakuTree[i].setVisible(false);
						 sHikari[i].setVisible(false);
					}
					currentTanzaku = 0;
					sTanzaku[0].setVisible(true);
				}
			} else if (checkContains(sSasaTreeMove, 650, 24, 950, 362, pX, pY)) {
				touchSasaTree();
			} else if (checkContains(aniOrihime, 1, 1, 122, 187, pX, pY)) {
				touchOrihime();
			} else if (checkContains(sHashi, 48, 20, 160, 135, pX, pY)) {
				touchHashi();
			} else
			if (checkContains(aniStar, 23, 8, 140, 111, pX, pY)) {
				touchStar();
			} else 
			if (checkContains(aniHikoboshi, 15, 7, 111, 168, pX, pY)) {
				touchHikoboshi();
			}
		} 
		return false;
	}
	
	private void touchSky(int pX,int pY) {
		
		if(Math.abs((pX + pY) - (pLastX+pLastY)) >= 100){
			
			A1_16_HOSHI.play();
			Sprite sStar[] = new Sprite[8];
			for (int i = 0; i < sStar.length; i++) {
				 sStar[i]= sStarPool[i].obtainPoolItem();
				 sStar[i].setPosition(pX, pY);
				 sStar[i].setAlpha(0.7f);
				 mScene.attachChild(sStar[i]);
				 starMoveSky(sStar[i], pX, (int)arrPointStar[0][i], pY, (int)arrPointStar[1][i]);
			}
			pLastX = pX;
			pLastY = pY;
		}
		
	}
	private void starMoveSky(final Sprite starMove, int fromX, int toX,  int fromY, int toY) {
		starMove.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(5.0f, 0.7f, 0.0f),
				new SequenceEntityModifier(
				new MoveModifier(2.5f, fromX, fromX+toX, fromY, fromY+toY),
				new MoveModifier(2.5f, fromX+toX, fromX, fromY+toY, fromY, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						runOnUpdateThread(new  Runnable() {
							public void run() {
								for (int i = 0; i < sStarPool.length; i++) {
									sStarPool[i].onHandleRecycleItem(starMove);
									
								}
							}
						}); 
						
					}
				}))));
	}
	private void touchHikoboshi(){
		if (istouchHikoboshi) {
			istouchHikoboshi = false;
			isCheckMeet = false;
			A1_13_FUTARI.play();
			aniHikoboshi.setCurrentTileIndex(1);
			if (aniOrihime.getCurrentTileIndex()==0){
				aniOrihime.setCurrentTileIndex(4);
			} else if (aniOrihime.getCurrentTileIndex()==1) {
				aniOrihime.setCurrentTileIndex(5);
				
			} else if (aniOrihime.getCurrentTileIndex()==2) {
				aniOrihime.setCurrentTileIndex(6);
				
			} else if (aniOrihime.getCurrentTileIndex()==3){
				aniOrihime.setCurrentTileIndex(7);
			}
			aniHikoboshi.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f),
					new MoveModifier(1.0f,aniHikoboshi.getX(),aniHikoboshi.getX()+110, 
							aniHikoboshi.getY(), aniHikoboshi.getY()-20)));
			aniOrihime.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f),
					new MoveModifier(1.0f, aniOrihime.getX(), aniOrihime.getX()-110,
							aniHikoboshi.getY(), aniHikoboshi.getY()-20,
							new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							if (aniOrihime.getCurrentTileIndex()==4) {
								aniMeet.setVisible(true);
								aniMeet.setCurrentTileIndex(0);
							} else if (aniOrihime.getCurrentTileIndex()==5){
								aniMeet.setVisible(true);
								aniMeet.setCurrentTileIndex(1);
							} else if (aniOrihime.getCurrentTileIndex()==6) {
								aniMeet.setVisible(true);
								aniMeet.setCurrentTileIndex(2);
							} else if (aniOrihime.getCurrentTileIndex()==7) {
								aniMeet.setVisible(true);
								aniMeet.setCurrentTileIndex(3);
							}
							aniHikoboshi.setVisible(false);
							aniOrihime.setVisible(false);
							aniHikoboshi.setPosition(242, 20);
							aniOrihime.setPosition(505, 7);
							aniMeetStat.setVisible(true);
							long pDuration[] = new long[]{300,300};
							int pFrame[] = new int[]{1,0};
							aniMeetStat.animate(pDuration,pFrame,3);
						}
					})));

			TimerHandler timeHikoboshi = new TimerHandler(3.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					// TODO Auto-generated method stub
					aniHikoboshi.setCurrentTileIndex(0);
					aniHikoboshi.setPosition(242, 20);
					aniHikoboshi.setVisible(true);
					if (aniOrihime.getCurrentTileIndex()==4){
						aniOrihime.setCurrentTileIndex(0);
					} else if (aniOrihime.getCurrentTileIndex()==5) {
						aniOrihime.setCurrentTileIndex(1);
						
					} else if (aniOrihime.getCurrentTileIndex()==6) {
						aniOrihime.setCurrentTileIndex(2);
						
					} else if (aniOrihime.getCurrentTileIndex()==7){
						aniOrihime.setCurrentTileIndex(3);
					}
					aniOrihime.setVisible(true);
					aniMeetStat.setVisible(false);
					aniMeet.setVisible(false);
					istouchHikoboshi = true;
					isCheckMeet = true;
				}
			});
			mEngine.registerUpdateHandler(timeHikoboshi);
			
		}
	}
	private void touchOrihime() {
		if (aniOrihime.getCurrentTileIndex()==0){
			A1_11_OTOHIMEHENKA.play();
			aniOrihime.setCurrentTileIndex(1);
		}else if (aniOrihime.getCurrentTileIndex()==1) {
			A1_11_OTOHIMEHENKA.play();
			aniOrihime.setCurrentTileIndex(2);
			
		} else if (aniOrihime.getCurrentTileIndex()==2) {
			A1_11_OTOHIMEHENKA.play();
			aniOrihime.setCurrentTileIndex(3);
			
		}else if (aniOrihime.getCurrentTileIndex()==3){
			A1_11_OTOHIMEHENKA.play();
			aniOrihime.setCurrentTileIndex(0);
		}
	}
	private void touchStar() {
		if (isTouchStar && isCheckMeet) {
			isTouchStar = false;
			A1_4_HOSHITACHI.play();
			if (aniStar.getCurrentTileIndex() == 0){
				aniStar.setCurrentTileIndex(1);
			} else if (aniStar.getCurrentTileIndex() == 2) {
				aniStar.setCurrentTileIndex(3);
			} else if (aniStar.getCurrentTileIndex() == 4) {
				aniStar.setCurrentTileIndex(5);
			}
			aniStar.registerEntityModifier(new SequenceEntityModifier(
					new MoveModifier(2.0f, aniStar.getX(), aniStar.getX()-800, aniStar.getY(), aniStar.getY()+500),
					new DelayModifier(0.4f, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							isTouchStar = true;
							loadRandomStar();
						}
					})));
		}
	}
	private void loadRandomStar() {
		try {
			int random = new Random().nextInt(3);
			Log.d(TAG, "value random star:" + random);
			aniStar.setPosition(457, 193);
			switch (random) {
			case 0:
				aniStar.setCurrentTileIndex(0);
				break;
			case 1:
				aniStar.setCurrentTileIndex(2);
				break;
			case 2:
				aniStar.setCurrentTileIndex(4);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return;
	}
	private void touchSasaTree() {
		if (istouchTree) {
			istouchTree = false;
			A1_10_SAWA.play();
		
			sSasaLeaf1.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.3f,0,5,263,159),
					new RotationAtModifier(0.6f,5,-5,263,159),
					new RotationAtModifier(0.3f,-5,0,263,159, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							// TODO Auto-generated method stub
							istouchTree = true;
						}
					})));
			
			sSasaLeaf2.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.25f,0,5,41,273),
					new RotationAtModifier(0.5f,5,-5,41,273),
					new RotationAtModifier(0.25f,-5,0,41,273)));
			sSasaLeaf3.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.25f,0,5,273,111),
					new RotationAtModifier(0.5f,5,-5,273,111),
					new RotationAtModifier(0.25f,-5,0,273,111)));
			sSasaLeaf4.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.25f,0,5,286,63),
					new RotationAtModifier(0.5f,5,-5,286,63),
					new RotationAtModifier(0.25f,-5,0,286,63)));
			if (sTanzakuTree[0].isVisible()){
				Log.d(TAG, "manh giay thu nhat: ");
				moveTanzakuTree(sTanzakuTree[0],22,2);
			}
			if (sTanzakuTree[1].isVisible()){
				moveTanzakuTree(sTanzakuTree[1],33,2);
			}
			if (sTanzakuTree[2].isVisible()){
				moveTanzakuTree(sTanzakuTree[2],33,2);		
			}
			if (sTanzakuTree[3].isVisible()){
				moveTanzakuTree(sTanzakuTree[3],26,2);
			}
			if (sTanzakuTree[4].isVisible()){
				moveTanzakuTree(sTanzakuTree[4],18,2);
			}
			if (sTanzakuTree[5].isVisible()){
				moveTanzakuTree(sTanzakuTree[5],22,1);
			}
			if (sTanzakuTree[6].isVisible()){
				moveTanzakuTree(sTanzakuTree[6],20,2);
			}
			if (sTanzakuTree[7].isVisible()){
				moveTanzakuTree(sTanzakuTree[7],19,2);
			}
			if (sTanzakuTree[8].isVisible()){
				moveTanzakuTree(sTanzakuTree[8],14,1);
			}
			if (sTanzakuTree[9].isVisible()){
				moveTanzakuTree(sTanzakuTree[9],48,1);
			}
	
			if (sTanzakuTree[10].isVisible()){
				moveTanzakuTree(sTanzakuTree[10],16,2);
			}
			if (sTanzakuTree[11].isVisible()){
				moveTanzakuTree(sTanzakuTree[11],9,1);
			}
		
		}
	}
	private void moveTanzakuTree(Sprite sTanzaku, float pRotationCenterX, float pRotationCenterY) {
		sTanzaku.registerEntityModifier(new SequenceEntityModifier(
				new RotationAtModifier(0.4f,0,10,pRotationCenterX, pRotationCenterY),
				new RotationAtModifier(0.6f,10,-10,pRotationCenterX, pRotationCenterY),
				new RotationAtModifier(0.6f,-10,10,pRotationCenterX, pRotationCenterY),
				new RotationAtModifier(0.4f,10,0,pRotationCenterX, pRotationCenterY)));
	}
	private void touchSasa() {
		if (istouchSasa){
			istouchSasa = false;
			A1_15_SASAHENKA.play();
			aniSasa.registerEntityModifier(new SequenceEntityModifier(
					new RotationAtModifier(0.3f, 0, 5, 35, 499),
					new RotationAtModifier(0.3f, 5, 0, 35, 499, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
							nextTileAnimatedSprite(aniSasa);
							istouchSasa=true;
						}
					})));
		}
	}
	private void touchGirl(){
		if (touchGirl) {
			touchGirl=false;
			setTouchGimmic(1,false);
			setTouchGimmic(0,false);
			long pDuration[] = new long[]{100,800,600,300,100};
			int pFrame[] = new int[]{1,0,2,3,0};
			aniGirl.animate(pDuration,pFrame,0,this);
			A1_5_GIRLTANZAKU.play();
			sTanzaku[currentTanzaku].setVisible(false);
			switch (currentTanzaku) {
			case 0:
				moveGirl(0.45f, 200);
				break;
			case 1:
				moveGirl(0.4f, 180);
				break;
			case 2:
				moveGirl(0.5f, 250);
				break;
			case 3:
				moveGirl(0.2f, 100);
				break;
			case 4:
				moveGirl(0.5f, 240);
				break;
			case 5:
				moveGirl(0.4f, 190);
				break;
			case 6:
				moveGirl(0.45f, 200);
				break;
			case 7:
				moveGirl(0.2f, 100);
				break;
			case 8:
				moveGirl(0.5f, 240);
				break;
			case 9:
				moveGirl(0.3f, 175);
				break;
			case 10:
				moveGirl(0.4f, 180);
				break;
			case 11:
				moveGirl(0.45f, 200);
				break;

			default:
				break;
			}
		}
		
	}
	private void moveGirl(float pDuration,float toY) {
		aniGirl.registerEntityModifier(new SequenceEntityModifier(
				new MoveYModifier(pDuration, aniGirl.getY(), aniGirl.getY() - toY, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						sTanzakuTree[currentTanzaku].setVisible(true);
						moveTanzaku(currentTanzaku);
						sHikari[currentTanzaku].setVisible(true);
						sHikari[currentTanzaku].registerEntityModifier(new SequenceEntityModifier(
								new AlphaModifier(0.2f, 1.0f, 0.0f),
								new AlphaModifier(0.2f, 0.0f, 1.0f),
								new AlphaModifier(0.2f, 1.0f, 0.0f),
								new AlphaModifier(0.2f, 0.0f, 1.0f, new IEntityModifierListener() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
										sHikari[currentTanzaku].setVisible(false);
									}
								})));
					}
				}),
				new MoveYModifier(pDuration, aniGirl.getY()-toY, aniGirl.getY())));
	}
	private void moveTanzaku(int index){
		switch (index) {
		case 0:
			moveTanzakuTree(sTanzakuTree[0],22,2);
			break;
		case 1:
			moveTanzakuTree(sTanzakuTree[1],33,2);
			break;
		case 2:
			moveTanzakuTree(sTanzakuTree[2],33,2);		
			break;
		case 3:
			moveTanzakuTree(sTanzakuTree[3],26,2);
			break;
		case 4:
			moveTanzakuTree(sTanzakuTree[4],18,2);
			break;
		case 5:
			moveTanzakuTree(sTanzakuTree[5],22,1);
			break;
		case 6:
			moveTanzakuTree(sTanzakuTree[6],20,2);
			break;
		case 7:
			moveTanzakuTree(sTanzakuTree[7],19,2);
			break;
		case 8:
			moveTanzakuTree(sTanzakuTree[8],14,1);
			break;
		case 9:
			moveTanzakuTree(sTanzakuTree[9],48,1);
			break;
		case 10:
			moveTanzakuTree(sTanzakuTree[10],16,2);
			break;
		case 11:	
			moveTanzakuTree(sTanzakuTree[11],9,1);
			break;

		default:
			break;
		}
		
	}
	
	private void touchHashi(){
		if (istouchHashi) {
			istouchHashi = false;
			sHashiLight.setVisible(true);
			A1_7_HASHITACHI.play();
			TimerHandler timerHash = new TimerHandler(2.0f, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler arg0) {
					istouchHashi=true;
					sHashiLight.setVisible(false);
				}
			});
			mEngine.registerUpdateHandler(timerHash);
		}
	}
	private void touchBoy() {
		if (touchBoy) {
			touchBoy = false;
			A1_8_BOYJUMP.play();
			setTouchGimmic(1,false);
			setTouchGimmic(0,false);
			long pDuration[] = new long[]{100,600,500};
			int pFrame[] = new int[]{0,1,0};
			aniBoy.animate(pDuration,pFrame,0,this);
			Timer timerBoy = new Timer();
			timerBoy.schedule(new TimerTask() {
				
				@Override
				public void run() {
					A1_8_EI.play();
				}
			}, 400);
			
		}
	}
	private void touchTown() {
		if (istouchTown) {
			isTouchGimmic[2] = false;
			istouchTown = false;
			A1_6_MACHIAKARI.play();
			long pDuration[] = new long[]{200,200,200};
			int pFrame[] = new int[]{1,2,3};
			aniTown.animate(pDuration,pFrame,2,this);
		}
		return;
	}
	private void moveBackground() {
		aniBackground.animate(2000,-1);
		return;
	}
	
	private void initial(){
		istouchTown = true;
		istouchHashi = true;
		touchBoy = true;
		currentTanzaku = 0;
		touchGirl = true;
		istouchSasa = true;
		istouchTree = true;
		isTouchStar = true;
		isCheckMeet = true; 
		istouchHikoboshi = true;
		
	}
	@Override
	public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
		
	}
	@Override
	public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		
	}
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimateSprite) {
		if (pAnimateSprite.equals(aniTown)) {
			aniTown.setCurrentTileIndex(0);
			istouchTown = true;
			isTouchGimmic[2] = true;
			
		}
		if (pAnimateSprite.equals(aniGirl)) {
			if (currentTanzaku!=11) {
				sTanzaku[currentTanzaku+1].setVisible(true);
				
			} 
			setTouchGimmic(1,true);
			setTouchGimmic(0,true);
			touchGirl = true;
			currentTanzaku++;
		}
		if (pAnimateSprite.equals(aniBoy)) {
			touchBoy = true;
			setTouchGimmic(1,true);
			setTouchGimmic(0,true);
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
