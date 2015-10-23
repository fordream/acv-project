package jp.co.xing.utaehon03.songs;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3HinamatsuriResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class Vol3Hinamatsuri extends BaseGameActivity implements
		IOnSceneTouchListener, IAnimationListener {

	private String TAG = Vol3Hinamatsuri.this.getClass().toString();
        private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private static boolean DEBUG = false;

	/**
	 * Texture, TextureRegion, TiledTextureRegion, Sprite, AnimatedSprite
	 */
	private ITextureRegion ttrRBgr;
	private ITextureRegion ttrRZabtn;
	private ITextureRegion ttrRZabtnFix;
	private ITextureRegion ttrRZabtnFix2;
	private ITextureRegion ttrRCarpet;
	private ITextureRegion ttrRSakurain;

	private TiledTextureRegion tlTtrRTable;
	private TiledTextureRegion tlTtrRScroll;
	private TiledTextureRegion tlTtrRScreen;
	private TiledTextureRegion tlTtrRKnQ;
	private TiledTextureRegion tlTtrSakura;

	private Sprite sprBgr;
	private Sprite sprZabtn;
	private Sprite sprZabtnFix;
	private Sprite sprZabtnFix2;
	private Sprite sprCarpet;
	private Sprite sprSakurain;

	private ITextureRegion[] zxRegion = new ITextureRegion[15];
	private DraggableSprite[] zxSprite = new DraggableSprite[15];
	private TiledTextureRegion[] zxTiledRight = new TiledTextureRegion[15];
	private TiledTextureRegion[] zxTiledWrong = new TiledTextureRegion[15];
	private AnimatedSprite[] zxSpriteRight = new AnimatedSprite[15];
	private AnimatedSprite[] zxSpriteWrong = new AnimatedSprite[15];
	private ITextureRegion[] zxZaRegion = new ITextureRegion[15];
	private Sprite[] zxZaSprite = new Sprite[15];

	private AnimatedSprite anmSprTable;
	private AnimatedSprite anmSprScroll;
	private AnimatedSprite anmSprScreen;
	private AnimatedSprite anmSprKnQ;
	private AnimatedSprite anmSprSakura;

	/**
	 * All Sound
	 */

	private Sound oggAnimTable;
	private Sound oggAnimFoldingScreenOpen;
	private Sound oggAnimFoldingScreenClose;
	private Sound oggAllSet;
	private Sound oggAnimScroll;
	private Sound oggAnimSakurain;
	private Sound oggAnimKnQ;

	private Sound[] oggAnimDragRight = new Sound[15];
	private Sound[] oggAnimDragWrong = new Sound[15];
	private Sound[] oggAnimDragWrong2 = new Sound[15];
	private Sound[] oggAnimOnlyRight = new Sound[15];
	private Sound[] oggAnimOnlyRight2 = new Sound[15];

	/**
	 * Positions in Scroll (Y-axis only)
	 */

	private float scrollYPos[] = new float[] { 87, 178, 269, 360, 451 };

	/**
	 * Array Drag Status
	 */

	private int[][] dragBillBoard = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
			{ 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
			{ 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, };

	/**
	 * isDragMode Flags
	 */
	private enum DragState {
		UNDRAGGED, DRAGGED_WRONG, DRAGGED_RIGHT
	};

	private enum Type {
		HUMAN, GOOD
	};

	private boolean isFoldingScreenClickable = true;
	private boolean isTableClickable = true;
	private boolean isScrollClickable = false;
	private int gimmicsDisabledTimeCount = 0;
	private boolean[] isZxSpriteRightClickable = { false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false };
	private int dragCount = 0;
	private boolean isTouchAreas = false;
	private TexturePack Vol3HinamatsuriPacker_1, Vol3HinamatsuriPacker_2, Vol3HinamatsuriPacker_3, Vol3HinamatsuriPacker_4;
        private TexturePackTextureRegionLibrary Vol3HinamatsuriPackerLibrary1, Vol3HinamatsuriPackerLibrary2, Vol3HinamatsuriPackerLibrary3, Vol3HinamatsuriPackerLibrary4;
        @Override
            public void onCreateResources() {
                Log.d(TAG,"Create Resource");
                SoundFactory.setAssetBasePath("hinamatsuri/mfx/");
                BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("hinamatsuri/gfx/");
                mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "hinamatsuri/gfx/");
                super.onCreateResources();
            }
	@Override
	protected void loadKaraokeResources() {
		Log.i(TAG, "loadKaraokeResources");
		Vol3HinamatsuriPacker_1 = mTexturePackLoaderFromSource.load("Vol3HinamatsuriPacker1.xml");
		Vol3HinamatsuriPacker_1.loadTexture();  
		Vol3HinamatsuriPackerLibrary1 = Vol3HinamatsuriPacker_1.getTexturePackTextureRegionLibrary();
		
		Vol3HinamatsuriPacker_2 = mTexturePackLoaderFromSource.load("Vol3HinamatsuriPacker2.xml");
                Vol3HinamatsuriPacker_2.loadTexture();  
                Vol3HinamatsuriPackerLibrary2 = Vol3HinamatsuriPacker_2.getTexturePackTextureRegionLibrary();
                
                Vol3HinamatsuriPacker_3 = mTexturePackLoaderFromSource.load("Vol3HinamatsuriPacker3.xml");
                Vol3HinamatsuriPacker_3.loadTexture();  
                Vol3HinamatsuriPackerLibrary3 = Vol3HinamatsuriPacker_3.getTexturePackTextureRegionLibrary();
                
                Vol3HinamatsuriPacker_4 = mTexturePackLoaderFromSource.load("Vol3HinamatsuriPacker4.xml");
                Vol3HinamatsuriPacker_4.loadTexture();  
                Vol3HinamatsuriPackerLibrary4 = Vol3HinamatsuriPacker_4.getTexturePackTextureRegionLibrary();
                
		/** Load Background */
		this.ttrRBgr = Vol3HinamatsuriPackerLibrary3.get(
				Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_19_IPHONE_HAIKEI);
		/** Load Table */
		this.tlTtrRTable = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
		        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_14_1_IPHONE_SIKIMONO,
		        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_14_2_IPHONE_SIKIMONO,
		        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_14_3_IPHONE_SIKIMONO,
		        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_14_4_IPHONE_SIKIMONO);
		
		/** Load left Carpet */
		this.ttrRCarpet = Vol3HinamatsuriPackerLibrary1.get(
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_2_IPHONE_DAIRI);
		/** Load Zabuton */
		this.ttrRZabtn = Vol3HinamatsuriPackerLibrary4.get(
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker4.A3_18_IPHONE_ZABUTON);
		
		this.ttrRZabtnFix = Vol3HinamatsuriPackerLibrary4.get(
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker4.A3_18_IPHONE_ZABUTON_FIX);
		this.ttrRZabtnFix2 = Vol3HinamatsuriPackerLibrary4.get(
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker4.A3_18_IPHONE_ZABUTON_FIX2);
		/** Load Folding Screen */
		this.tlTtrRScreen = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_17_1_IPHONE_BYOUBU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_17_2_IPHONE_BYOUBU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_17_3_IPHONE_BYOUBU);
		/** Load Scroll */
		this.tlTtrRScroll = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_1_IPHONE_KAKEJIKU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_2_IPHONE_KAKEJIKU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_3_IPHONE_KAKEJIKU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_4_IPHONE_KAKEJIKU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_5_IPHONE_KAKEJIKU,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_15_6_IPHONE_KAKEJIKU);
		/**
		 * Load 15 Draggable Sprites (include Right and Wrong state Sprites) and
		 * 15 Zabuton
		 */
		for (int i = 0; i < 15; i++) {
			if (i < 13) {
				this.zxZaRegion[i] = Vol3HinamatsuriPackerLibrary4.get(Vol3HinamatsuriResource.ARR_RESOURCE_ZABUTON_INT[i]); 
				if(i < 8){
				    this.zxRegion[i] = Vol3HinamatsuriPackerLibrary1.get(Vol3HinamatsuriResource.ARR_RESOURCE_SPRITE_INT[i]);
				}else{
				    this.zxRegion[i] = Vol3HinamatsuriPackerLibrary2.get(Vol3HinamatsuriResource.ARR_RESOURCE_SPRITE_INT[i]);
				}
			} else {
				this.zxZaRegion[i] =Vol3HinamatsuriPackerLibrary4.get(Vol3HinamatsuriResource.ARR_RESOURCE_ZABUTON_INT[i - 2]); 
				this.zxRegion[i] = Vol3HinamatsuriPackerLibrary2.get(Vol3HinamatsuriResource.ARR_RESOURCE_SPRITE_INT[i - 2]);
			}
			switch(i){
			case 0:
			    this.zxTiledRight[i] = 
	                            getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_1_1_IPHONE_DAIRI,
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_1_2_IPHONE_DAIRI,
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_1_3_IPHONE_DAIRI,
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_1_4_IPHONE_DAIRI,
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_1_IPHONE_DAIRI,
	                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_3_5_IPHONE_DAIRI);
			    this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
			            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_2_1_IPHONE_DAIRI,
			            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_2_2_IPHONE_DAIRI
			        );
			    break;
			case 1:
			    this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_1_1_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_1_2_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_1_3_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_1_4_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_1_1_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_3_1_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_3_2_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_3_3_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_3_4_IPHONE_HINA,
                                            Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_3_5_IPHONE_HINA);
			    this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_2_1_IPHONE_HINA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_2_2_2_IPHONE_HINA
                                );
			    break;
			case 2:
			    this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_1_1_IPHONE_CYOUSHI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_1_2_IPHONE_CYOUSHI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_1_IPHONE_CYOUSHI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_3_1_IPHONE_CYOUSHI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_3_2_IPHONE_CYOUSHI
                                        );
			    this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_2_1_IPHONE_CYOUSHI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_3_2_2_IPHONE_CYOUSHI
                                );
			    break;
			case 3:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_1_1_IPHONE_CYUOU,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_1_2_IPHONE_CYUOU,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_1_IPHONE_CYUOU,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_3_1_IPHONE_CYUOU,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_3_2_IPHONE_CYUOU,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_3_3_IPHONE_CYUOU
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_2_1_IPHONE_CYUOU,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_4_2_2_IPHONE_CYUOU
                                );
                            break;
			case 4:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_1_1_IPHONE_NAGAE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_1_IPHONE_NAGAE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_3_1_IPHONE_NAGAE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_3_2_IPHONE_NAGAE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_3_3_IPHONE_NAGAE
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_2_1_IPHONE_NAGAE,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_5_2_2_IPHONE_NAGAE
                                );
                            break;
			case 5:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_1_1_IPHONE_TAIKO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_1_2_IPHONE_TAIKO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_1_IPHONE_TAIKO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_3_1_IPHONE_TAIKO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_3_2_IPHONE_TAIKO
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_2_1_IPHONE_TAIKO,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_2_2_IPHONE_TAIKO,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_6_2_3_IPHONE_TAIKO
                                );
                            break;
			case 6:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_1_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_2_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_3_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_4_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_5_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_1_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_3_1_IPHONE_OKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_3_2_IPHONE_OKAWA
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_2_1_IPHONE_OKAWA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_2_2_IPHONE_OKAWA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_7_2_3_IPHONE_OKAWA
                                );
                            break;
			case 7:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_1_1_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_1_2_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_1_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_3_1_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_3_2_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_3_3_IPHONE_KOKAWA,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_3_4_IPHONE_KOKAWA
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_2_1_IPHONE_KOKAWA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_2_2_IPHONE_KOKAWA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_2_3_IPHONE_KOKAWA,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_8_2_4_IPHONE_KOKAWA
                                );
                            break;
			case 8:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_1_1_IPHONE_FUE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_1_2_IPHONE_FUE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_1_IPHONE_FUE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_3_1_IPHONE_FUE,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_3_2_IPHONE_FUE
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_2_1_IPHONE_FUE,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_9_2_2_IPHONE_FUE
                                );
                            break;
			case 9:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_1_1_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_1_2_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_1_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_3_1_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_3_2_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_3_3_IPHONE_UTAI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_3_4_IPHONE_UTAI
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_2_1_IPHONE_UTAI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_10_2_2_IPHONE_UTAI
                                );
                            break;
			case 10:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_1_IPHONE_MOMO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_2_1_IPHONE_MOMO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_2_2_IPHONE_MOMO,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_2_3_IPHONE_MOMO
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_1_1_IPHONE_MOMO,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_11_1_2_IPHONE_MOMO
                                );
                            break;
			case 11:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_IPHONE_BONBORI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_2_IPHONE_BONBORI
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_1_IPHONE_BONBORI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_2_IPHONE_BONBORI
                                );
                            break;
			case 12:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_1_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_2_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_3_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_4_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_5_IPHONE_TAKATSUKI
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_1_IPHONE_TAKATSUKI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_2_IPHONE_TAKATSUKI
                                );
                            break;
			case 13:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_IPHONE_BONBORI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_2_IPHONE_BONBORI
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_1_IPHONE_BONBORI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_12_1_2_IPHONE_BONBORI
                                );
                            break;
                        case 14:
                            this.zxTiledRight[i] = 
                                getTiledTextureFromPacker(Vol3HinamatsuriPacker_2, 
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_1_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_2_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_3_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_4_IPHONE_TAKATSUKI,
                                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_2_5_IPHONE_TAKATSUKI
                                        );
                            this.zxTiledWrong[i] = getTiledTextureFromPacker(Vol3HinamatsuriPacker_2,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_1_IPHONE_TAKATSUKI,
                                    Vol3HinamatsuriResource.Vol3HinamatsuriPacker2.A3_13_1_2_IPHONE_TAKATSUKI
                                );
                            break;
			}
		}

		/** Load King and Queen */

		this.tlTtrRKnQ = getTiledTextureFromPacker(Vol3HinamatsuriPacker_1, 
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_3_1_IPHONE_DAIRI,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_3_2_IPHONE_DAIRI,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_3_3_IPHONE_DAIRI,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker1.A3_1_3_4_IPHONE_DAIRI);
		/** Load Sakurain */

		this.ttrRSakurain =Vol3HinamatsuriPackerLibrary3.get(Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_16_5_IPHONE_SAKURA);
		this.tlTtrSakura = getTiledTextureFromPacker(Vol3HinamatsuriPacker_3, 
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_16_1_IPHONE_SAKURA,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_16_2_IPHONE_SAKURA,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_16_3_IPHONE_SAKURA,
                        Vol3HinamatsuriResource.Vol3HinamatsuriPacker3.A3_16_4_IPHONE_SAKURA);

	}

	@Override
	protected void loadKaraokeSound() {
		Log.i(TAG, "loadKaraokeSound");
		/** Load Sounds */
		oggAnimTable = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_14_WADAIKO);
		oggAnimFoldingScreenOpen = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_17_MOKIN2);
		oggAnimFoldingScreenClose = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_17_MOKIN2_5);
		oggAllSet = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_MATIGAI_FAFAFAN);
		oggAnimScroll = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_15_HAJIMENIMODORU);
		oggAnimSakurain = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_16_SAKURA45);
		oggAnimKnQ = loadSoundResourceFromSD(Vol3HinamatsuriResource.A3_1_CHU7);

		for (int i = 0; i < 15; i++) {
			if (i < 13) {
				oggAnimDragRight[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][0]);
				oggAnimDragWrong[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][1]);
				oggAnimOnlyRight[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][3]);
				if (Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][2] != "") {
					oggAnimDragWrong2[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][2]);
				}
				if (Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][4] != "") {
					oggAnimOnlyRight2[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i][4]);
				}
			} else {
				oggAnimDragRight[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][0]);
				oggAnimDragWrong[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][1]);
				oggAnimOnlyRight[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][3]);
				if (Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][2] != "") {
					oggAnimDragWrong2[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][2]);
				}
				if (Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][4] != "") {
					oggAnimOnlyRight2[i] = loadSoundResourceFromSD(Vol3HinamatsuriResource.ARR_SOUND_DRAG[i - 2][4]);
				}
			}
		}
	}

	@Override
	protected void loadKaraokeScene() {
		Log.i(TAG, "loadKaraokeScene");

		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();
		this.mScene.setOnAreaTouchTraversalFrontToBack();

		/** Set Background */
		this.sprBgr = new Sprite(0, 0, ttrRBgr,this.getVertexBufferObjectManager());
		this.mScene.setBackground(new SpriteBackground(sprBgr));

		/** Add Folding Screen */
		this.anmSprScreen = new AnimatedSprite(252, 30, this.tlTtrRScreen,this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprScreen);

		/** Add Table */
		this.anmSprTable = new AnimatedSprite(55, 142, this.tlTtrRTable,this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprTable);

		/** Add left Carpet */
		this.sprCarpet = new Sprite(
				Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[0][0],
				Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[0][1], ttrRCarpet,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprCarpet);

		/** Add King and Queen */
		this.anmSprKnQ = new AnimatedSprite(330, 11, this.tlTtrRKnQ,this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprKnQ);

		/** Add Zabuton */
		this.sprZabtn = new Sprite(55, 142, ttrRZabtn,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprZabtn);
		this.sprZabtnFix = new Sprite(55, 142, ttrRZabtnFix,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprZabtnFix);
		this.sprZabtnFix2 = new Sprite(55, 142, ttrRZabtnFix2,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprZabtnFix2);

		/** Add Sakurain */
		this.sprSakurain = new Sprite(31, 5, this.ttrRSakurain,this.getVertexBufferObjectManager());
		this.mScene.attachChild(sprSakurain);
		this.anmSprSakura = new AnimatedSprite(31, 5, this.tlTtrSakura,this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprSakura);

		/** Add Scroll */
		this.anmSprScroll = new AnimatedSprite(801, 55, this.tlTtrRScroll,this.getVertexBufferObjectManager());
		this.mScene.attachChild(anmSprScroll);

		/** Add 15 Draggable Sprites */
		for (int i = 0; i < 15; i++) {

			/** Add 15 Zabuton Sprites */
			this.zxZaSprite[i] = new Sprite(
					Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[i][0],
					Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[i][1],
					zxZaRegion[i],this.getVertexBufferObjectManager());
			this.zxSpriteRight[i] = new AnimatedSprite(
					Vol3HinamatsuriResource.ARR_RESULT_POSITION[i][0],
					Vol3HinamatsuriResource.ARR_RESULT_POSITION[i][1],
					zxTiledRight[i],this.getVertexBufferObjectManager());
			this.zxSpriteWrong[i] = new AnimatedSprite(0, 0, zxTiledWrong[i],this.getVertexBufferObjectManager());

			if (i < 5) {
				this.zxSprite[i] = new DraggableSprite(827, scrollYPos[i],
						this.zxRegion[i], i, Type.HUMAN);
			} else {
				if (i < 10) {
					this.zxSprite[i] = new DraggableSprite(827, 0,
							this.zxRegion[i], i, Type.HUMAN);
				} else {
					this.zxSprite[i] = new DraggableSprite(827, 0,
							this.zxRegion[i], i, Type.GOOD);
				}
			}
			this.mScene.attachChild(zxZaSprite[i]);
			this.mScene.attachChild(zxSprite[i]);
			this.mScene.attachChild(zxSpriteRight[i]);
			this.mScene.attachChild(zxSpriteWrong[i]);
			this.mScene.registerTouchArea(zxSprite[i]);
			zxSpriteRight[i].setVisible(false);
			zxSpriteWrong[i].setVisible(false);
		}

		/** Add Three Gimmick Buttons */
		addGimmicsButton(mScene, Vol3HinamatsuriResource.SOUND_GIMMIC,
				Vol3HinamatsuriResource.IMAGE_GIMMIC,Vol3HinamatsuriPackerLibrary2);
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.mScene.setOnSceneTouchListener(this);
	}

	@Override
	protected void loadKaraokeComplete() {
		Log.i(TAG, "loadKaraokeComplete");
		super.loadKaraokeComplete();
	}
	@Override
	public synchronized void onResumeGame() {
	           sprSakurain.setVisible(false);
	                anmSprSakura.setVisible(false);
	                anmSprKnQ.setVisible(false);
	                for (int j = 0; j < 5; j++) {
	                        zxZaSprite[j].setVisible(false);
	                }
	                for (int i = 5; i < 15; i++) {
	                        zxSprite[i].setVisible(false);
	                        zxZaSprite[i].setVisible(false);
	                }
	                refreshZIndex();
	      super.onResumeGame();
	}
	private void scrollInit() {
		anmSprScroll.setCurrentTileIndex(0);
		for (int i = 0; i < 5; i++) {
			zxSprite[i].setVisible(true);
			zxSprite[i].setPosition(827, scrollYPos[i]);
			zxSprite[i].dragState = DragState.UNDRAGGED;
			zxSprite[i].draggable = true;
			zxSprite[i].setScale(1f);
			zxZaSprite[i].setVisible(false);
			zxSpriteRight[i].setVisible(false);
			zxSpriteWrong[i].setVisible(false);
			dragBillBoardUpdate(i, 0, 0);
		}
		for (int j = 5; j < 15; j++) {
			zxSprite[j].setVisible(false);
			zxSprite[j].setPosition(827, 0);
			zxSprite[j].dragState = DragState.UNDRAGGED;
			zxSprite[j].draggable = true;
			zxSprite[j].setScale(1f);
			zxZaSprite[j].setVisible(false);
			zxSpriteRight[j].setVisible(false);
			zxSpriteWrong[j].setVisible(false);
			dragBillBoardUpdate(j, 0, 0);
		}
		zxSpriteRight[10].setCurrentTileIndex(0);
		zxSpriteRight[11].setCurrentTileIndex(0);
		zxSpriteRight[12].setCurrentTileIndex(0);
		zxSpriteRight[13].setCurrentTileIndex(0);
		zxSpriteRight[14].setCurrentTileIndex(0);
		dragCount = 0;
		refreshZIndex();
		return;
	}

	private void scrollRefresh() {
		if (anmSprScroll.getCurrentTileIndex() != 5) {
			int count = 0;
			int dragCount = 0;
			if (DEBUG) {
				Log.d(TAG, "Scroll Refresh");
			}
			for (int i = 0; i < 15; i++) {
				if (zxSprite[i].dragState == DragState.UNDRAGGED && count < 5) {
					zxSprite[i].setVisible(true);
					zxSprite[i].setPosition(zxSprite[i].getX(),
							scrollYPos[count]);
					count++;
				} else if (zxSprite[i].dragState == DragState.UNDRAGGED) {
					zxSprite[i].setVisible(false);
					zxSprite[i].setPosition(zxSprite[i].getX(), 0);
				}
				if (dragBillBoard[i][0] != 0) {
					dragCount++;
				}
				if (DEBUG) {
					Log.d(TAG, "# = " + i + " & status = "
							+ zxSprite[i].dragState + " & posY = "
							+ zxSprite[i].getY() + " & visible = "
							+ zxSprite[i].isVisible());
				}
			}
			if (DEBUG) {
				Log.d(TAG,
						"---------------------------------------------------------------");
			}
			scrollSetLength(count);
			if (dragCount == 15) {
				animScrollEnd();
			}
		}
		return;
	}

	private void scrollSetLength(int length) {
		switch (length) {
		case 0:
			anmSprScroll.setCurrentTileIndex(3);
			break;
		case 1:
		case 2:
			anmSprScroll.setCurrentTileIndex(2);
			break;
		case 3:
		case 4:
			anmSprScroll.setCurrentTileIndex(1);
			break;
		default:
			anmSprScroll.setCurrentTileIndex(0);
			break;
		}
		return;
	}

	private void animScrollEnd() {
		isScrollClickable = false;
		long[] pDuration = { 4000, 400 };
		int[] pFrames = { 3, 4 };
		anmSprScroll.registerEntityModifier(new DelayModifier(4.0f, new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                    }
                    
                    @Override
                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                        oggAnimScroll.play();
                    }
                }));
		setEnabledGimmics(false);
		anmSprScroll.animate(pDuration, pFrames, 0, this);
		return;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			int pX = (int) pSceneTouchEvent.getX();
			int pY = (int) pSceneTouchEvent.getY();
			if (zxSpriteRight[10].contains(pX, pY)
					&& zxSpriteRight[10].isVisible()
					&& zxSprite[10].dragState == DragState.DRAGGED_RIGHT) {
			    Log.d(TAG, "touch Bong hoa:" + 10);
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 10);
				}
				animOnlyRight(10);
				return true;
			} else if (zxSpriteRight[1].contains(pX, pY)
					&& zxSpriteRight[1].isVisible()
					&& zxSprite[1].dragState == DragState.DRAGGED_RIGHT
					&& (pX > zxSpriteRight[1].getmXFirst() + 30)
					&& (pX < (zxSpriteRight[1].getmXFirst()
							+ zxSpriteRight[1].getWidth() - 80))) {
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 1);
				}
				animOnlyRight(1);
				return true;
			} else if (zxSpriteRight[11].contains(pX, pY)
					&& zxSpriteRight[11].isVisible()) {
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 11);
				}
				animOnlyRight(11);
				return true;
			} else if (zxSpriteRight[12].contains(pX, pY)
					&& zxSpriteRight[12].isVisible()) {
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 12);
				}
				animOnlyRight(12);
				return true;
			} else if (zxSpriteRight[13].contains(pX, pY)
					&& zxSpriteRight[13].isVisible()) {
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 13);
				}
				animOnlyRight(13);
				return true;
			} else if (zxSpriteRight[14].contains(pX, pY)
					&& zxSpriteRight[14].isVisible()) {
				if (DEBUG) {
					Log.d(TAG, "touch animOnlyRight:" + 14);
				}
				animOnlyRight(14);
				return true;
			}
			for (int i = 0; i < 15; i++) {
				if (i != 10 && i != 1 && i != 11 && i != 13) {
					if (zxSpriteRight[i].contains(pX, pY)
							&& zxSpriteRight[i].isVisible()) {
						if (zxSprite[i].dragState == DragState.DRAGGED_RIGHT) {
							if (i == 0
									&& zxSprite[1].dragState == DragState.DRAGGED_RIGHT) {
								if (DEBUG) {
									Log.d(TAG, "touch animOnlyRight:KnQ");
								}
								animKnQ();
							} else {
								if (pX > zxSpriteRight[i].getmXFirst() + 30
										&& pX < (zxSpriteRight[i].getmXFirst()
												+ zxSpriteRight[i].getWidth() - 30)
										&& pY > zxSpriteRight[i].getmYFirst() + 50) {
									if ((zxSpriteRight[5].contains(pX, pY)
											|| zxSpriteRight[6]
													.contains(pX, pY)
											|| zxSpriteRight[7]
													.contains(pX, pY)
											|| zxSpriteRight[8]
													.contains(pX, pY) || zxSpriteRight[9]
											.contains(pX, pY))
											&& (sprGimmic[0].contains(pX, pY)
													|| sprGimmic[1].contains(
															pX, pY) || sprGimmic[2]
													.contains(pX, pY))) {
										if (DEBUG) {
											Log.d(TAG,
													"touch double Gimmic error");
										}
									} else {
										if (DEBUG) {
											Log.d(TAG, "touch animOnlyRight:"
													+ i);
										}
										animOnlyRight(i);
									}
								} else {
									if (DEBUG) {
										Log.d(TAG, "animTable Fix");
									}
									if (isTableClickable) {
										animTable();
									}
								}
							}
						}
						return true;
					} else if (zxSpriteWrong[i].contains(pX, pY)
							&& zxSpriteWrong[i].isVisible()) {
						return true;
					} else if (zxZaSprite[i].contains(pX, pY)) {
						return true;
					}
				} else if (zxZaSprite[i].contains(pX, pY)) {
					return true;
				}
			}
			if (anmSprScreen.contains(pX, pY) && isFoldingScreenClickable) {
				animFoldingScreen();
			} else if (anmSprTable.contains(pX, pY)
					&& !sprGimmic[0].contains(pX, pY)
					&& !sprGimmic[1].contains(pX, pY)
					&& !sprGimmic[2].contains(pX, pY)) {
				if ((pX > 105 && pX < 763 && pY < 352)
						|| (pX > 50 && pX < 818 && pY > 352)) {
					if (isTableClickable) {
						animTable();
					}
				}
			} else if (anmSprScroll.contains(pX, pY)) {
				if (anmSprScroll.getCurrentTileIndex() == 5
						&& isScrollClickable) {
					if (isScrollClickable) {
						isScrollClickable = false;
						resetGameStatus();
					} else {
						if (DEBUG) {
							Log.d(TAG, "invalid click on scroll");
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onPauseGame() {
		resetGameStatus();
		super.onPauseGame();
	}

	private void resetGameStatus() {
		if (!this.isFinishing()) {
			try {
				anmSprTable.setCurrentTileIndex(0);
				anmSprScreen.setCurrentTileIndex(0);
				sprSakurain.setVisible(false);
				anmSprSakura.stopAnimation();
				anmSprSakura.setCurrentTileIndex(0);
				anmSprSakura.setVisible(false);
				anmSprKnQ.stopAnimation();
				anmSprKnQ.setVisible(false);
				anmSprKnQ.setCurrentTileIndex(0);
				sprCarpet.setVisible(true);
				zxSpriteRight[0].setVisible(false);
				zxSpriteRight[1].setVisible(false);
				anmSprScroll.stopAnimation();
				oggAllSet.pause();
				oggAnimSakurain.pause();
				oggAnimScroll.pause();
				gimmicsDisabledTimeCount = 0;
				setEnabledGimmics(true);
				isTouchAreas = false;
				scrollInit();
				Log.i(TAG, "resetGameStatus");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return;
	}

	@Override
	public void combineGimmic3WithAction() {
		animTable();
	}

	private void animTable() {
		oggAnimTable.play();
		int ct = anmSprTable.getCurrentTileIndex();
		if (ct < 3) {
			anmSprTable.setCurrentTileIndex(ct + 1);
		} else {
			anmSprTable.setCurrentTileIndex(0);
		}
		return;
	}

	private void animFoldingScreen() {
		long pFrameDurations[] = new long[] { 200, 200, 200, 200, 200 };
		int pFrames[] = new int[] { 0, 1, 2, 1, 0 };
		isFoldingScreenClickable = false;
		oggAnimFoldingScreenOpen.play();
		anmSprScreen.registerEntityModifier(new DelayModifier(0.6f, new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                        oggAnimFoldingScreenClose.play();
                    }
                }));
		setEnabledGimmics(false);
		anmSprScreen.animate(pFrameDurations, pFrames, 0, this);
		return;
	}

	private void animDragRight(final int index) {
		long pDuration[] = null;
		int pFrames[] = null;
		int loopCount = 1;
		refreshZIndex();
		zxSpriteRight[index].setVisible(true);
		switch (index) {
		case 0:
			pDuration = new long[] { 200, 200, 200, 200 };
			pFrames = new int[] { 0, 1, 2, 3 };
			break;
		case 1:
			pDuration = new long[] { 200, 200, 200, 200 };
			pFrames = new int[] { 0, 1, 2, 3 };
			break;
		case 2:
			pDuration = new long[] { 350, 350 };
			pFrames = new int[] { 0, 1 };
			break;
		case 3:
			pDuration = new long[] { 500, 500 };
			pFrames = new int[] { 0, 1 };
			loopCount = 0;
			break;
		case 4:
			pDuration = new long[] { 350, 350 };
			pFrames = new int[] { 0, 1 };
			break;
		case 5:
			pDuration = new long[] { 300, 1000 };
			pFrames = new int[] { 0, 1 };
			loopCount = 0;
			break;
		case 6:
			pDuration = new long[] { 150, 150, 150, 150, 150 };
			pFrames = new int[] { 0, 1, 2, 3, 4 };
			loopCount = 0;
			break;
		case 7:
			pDuration = new long[] { 200, 200 };
			pFrames = new int[] { 0, 1 };
			break;
		case 8:
			pDuration = new long[] { 300, 300 };
			pFrames = new int[] { 0, 1 };
			loopCount = 0;
			break;
		case 9:
			pDuration = new long[] { 300, 300 };
			pFrames = new int[] { 0, 1 };
			break;
		default:
			pDuration = new long[] { 100 };
			pFrames = new int[] { 0 };
			break;
		}
		if (index == 7) {
		    zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
	                    @Override
	                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                    }
	                    @Override
	                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                        oggAnimDragRight[index].play();
	                        zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
	                            @Override
	                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                            }
	                            @Override
	                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                                oggAnimDragRight[index].play();
	                            }
	                        }));
	                    }
	                }));
		} else if (index == 5) {
		    zxSpriteRight[index].registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                        @Override
                        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                        }
                        @Override
                        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                            oggAnimDragRight[index].play();
                        }
                    }));
		} else {
			if (index == 4) {
				oggAnimDragRight[index].setLoopCount(1);
			}
			oggAnimDragRight[index].play();
		}
		if (index != 11 && index != 13) {
			setEnabledGimmics(false);
			zxSpriteRight[index].animate(pDuration, pFrames, loopCount, this);
		}
		return;
	}

	private void animDragWrong(final int index, int position) {
		zxSprite[index].draggable = false;
		putPositionWrong(zxSpriteWrong[index], position, index);
		long pDuration[] = null;
		int pFrames[] = null;
		int loopCount = 0;
		refreshZIndex();
		zxSpriteWrong[index].setVisible(true);
		switch (index) {
		case 0:
			pDuration = new long[] { 300, 300, 100 };
			pFrames = new int[] { 0, 1, 0 };
			break;
		case 2:
		case 3:
		case 4:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			pDuration = new long[] { 300, 100 };
			pFrames = new int[] { 0, 1 };
			break;
		case 1:
			pDuration = new long[] { 300, 300 };
			pFrames = new int[] { 0, 1 };
			break;
		case 5:
			pDuration = new long[] { 300, 500, 100 };
			pFrames = new int[] { 0, 1, 2 };
			break;
		case 6:
			pDuration = new long[] { 300, 300, 300 };
			pFrames = new int[] { 0, 1, 2 };
			break;
		case 7:
			pDuration = new long[] { 300, 300, 300, 300 };
			pFrames = new int[] { 0, 1, 2, 3 };
			break;
		default:
			break;
		}
		if (index != 9) {
			oggAnimDragWrong[index].play();
		} else {
		    zxSpriteWrong[index].registerEntityModifier(new DelayModifier(0.25f, new IEntityModifierListener() {
                        @Override
                        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                        }
                        @Override
                        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                            oggAnimDragWrong[index].play();
                        }
                    }));
		}
		setEnabledGimmics(false);
		zxSpriteWrong[index].animate(pDuration, pFrames, loopCount, this);
		return;
	}

	private void putPositionWrong(AnimatedSprite spr, int pos, int index) {
		float x = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[pos][0]
				- ((spr.getWidth() - zxZaSprite[pos].getWidth()) / 2);
		float y = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[pos][1]
				- Vol3HinamatsuriResource.ARR_WRONG_OFFSET[index];
		if (index == 0) {
			x += 10;
		} else if (index == 9) {
			x += 15;
		} else if (index == 10) {
			x += 10;
		} else if (index == 12 || index == 14) {
			x += 9;
		}
		spr.setPosition(x, y);
		return;
	}

	private void putPositionSprite(DraggableSprite spr, int pos, int index) {
		float x = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[pos][0]
				- ((spr.getWidth() - zxZaSprite[pos].getWidth()) / 2);
		float y = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[pos][1]
				- (zxZaSprite[pos].getHeight() + 30);
		if (index == 0) {
			x += 10;
		} else if (index == 9) {
			x += 15;
		} else if (index == 10) {
			x += 10;
		} else if (index == 12 || index == 14) {
			x += 9;
		}
		spr.setPosition(x, y);
		return;
	}

	private void dragBillBoardUpdate(int index, int status, int spr) {
		if (DEBUG) {
			Log.d(TAG, "update pos = " + index + " & status = " + status
					+ " & spr = " + spr);
		}
		dragBillBoard[index][0] = status;
		dragBillBoard[index][1] = spr;
		return;
	}

	private int checkDragBillBoard(int pos) {
		return dragBillBoard[pos][0];
	}

	private void setEnabledGimmics(boolean bl) {
		if (bl == false) {
			gimmicsDisabledTimeCount++;
		}
		for (int i = 0; i < 3; i++) {
			isTouchGimmic[i] = bl;
		}
		isTableClickable = bl;
		return;
	}
	private Sound snds;
	private void animCommon(final int index) {
		if (isZxSpriteRightClickable[index]) {
			int max = 0;
			snds = oggAnimOnlyRight[index];
			int ct = zxSpriteRight[index].getCurrentTileIndex();
			Log.d("Index","Index of flower"+ ct);
			switch (index) {
			case 10:
				max = 3;
				if (ct == 3) {
				    snds = oggAnimOnlyRight2[index];
				}
				break;
			case 12:
			case 14:
				max = 5;
				break;
			}
			if (ct == (max - 1)) {
				zxSpriteRight[index].setCurrentTileIndex(ct + 1);
				isZxSpriteRightClickable[index] = false;
				zxSpriteRight[index].registerEntityModifier(new DelayModifier(1.2f, new IEntityModifierListener() {
                                    @Override
                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                    }
                                    @Override
                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                        if(index==10){
                                            oggAnimOnlyRight2[10].play();
                                        }
                                        zxSpriteRight[index].setCurrentTileIndex(0);
                                        isZxSpriteRightClickable[index] = true;
                                    }
                                }));
			} else {
				zxSpriteRight[index].setCurrentTileIndex(ct + 1);
			}
			snds.play();
		}
		return;
	}

	private void animOnlyRight(final int index) {
		if (index == 11 || index == 13) {
			if (zxSpriteRight[index].getCurrentTileIndex() != 0) {
				zxSpriteRight[index].setCurrentTileIndex(0);
			} else {
				zxSpriteRight[index].setCurrentTileIndex(1);
			}
			oggAnimOnlyRight[index].play();
			return;
		} else if (index == 10 || index == 12 || index == 14) {
			animCommon(index);
			return;
		}
		if (isZxSpriteRightClickable[index]) {
			isZxSpriteRightClickable[index] = false;
			long pDuration[] = null;
			int pFrames[] = null;
			int loopCount = 1;
			zxSpriteRight[index].setVisible(true);
			switch (index) {
			case 0:
				pDuration = new long[] { 300, 300 };
				pFrames = new int[] { 4, 5 };
				break;
			case 1:
				pDuration = new long[] { 250, 250, 250, 250, 250, 250 };
				pFrames = new int[] { 4, 5, 6, 7, 8, 9 };
				loopCount = 0;
				zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.25f, new IEntityModifierListener() {
		                        @Override
		                        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		                        }
		                        @Override
		                        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
		                            zxSpriteRight[1].setPosition(
                                                    zxSpriteRight[1].getmXFirst() - 10,
                                                    zxSpriteRight[1].getmYFirst() - 15);
		                        }
		                    }));
				break;
			case 2:
				pDuration = new long[] { 200, 200, 400 };
				pFrames = new int[] { 2, 3, 4 };
				loopCount = 0;
				break;
			case 3:
				pDuration = new long[] { 300, 300, 300, 300 };
				pFrames = new int[] { 2, 3, 4, 5 };
				loopCount = 0;
				break;
			case 4:
				pDuration = new long[] { 300, 300, 500, 500, 300, 300, 500, 500 };
				pFrames = new int[] { 0, 2, 3, 4, 0, 2, 3, 4 };
				loopCount = 0;
				break;
			case 5:
				pDuration = new long[] { 150, 150, 150, 150, 150, 150 };
				pFrames = new int[] { 2, 3, 4, 2, 3, 4 };
				loopCount = 0;
				break;
			case 6:
				pDuration = new long[] { 300, 300, 300 };
				pFrames = new int[] { 5, 6, 7 };
				loopCount = 0;
				break;
			case 7:
				pDuration = new long[] { 200, 200, 200, 200, 200, };
				pFrames = new int[] { 2, 3, 4, 5, 6 };
				loopCount = 0;
				break;
			case 8:
				pDuration = new long[] { 200, 1000, 1000 };
				pFrames = new int[] { 2, 3, 4 };
				loopCount = 0;
				break;
			case 9:
				pDuration = new long[] { 200, 200, 200, 200, 200, 200, 200,
						200, 200 };
				pFrames = new int[] { 2, 3, 4, 5, 6, 3, 4, 5, 6 };
				loopCount = 0;
				break;
			case 10:
			case 12:
			case 14:
				pDuration = new long[] { 1000, 1000, 1000, 1000 };
				pFrames = new int[] { 0, 1, 2, 3 };
				loopCount = 0;
				break;
			}
			if (index == 2) {
			    zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.4f, new IEntityModifierListener() {
                                @Override
                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                }
                                @Override
                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                    oggAnimOnlyRight[index].play();
                                }
                            }));
			} else if (index == 3) {
			    zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.6f, new IEntityModifierListener() {
                                @Override
                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                }
                                @Override
                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                    oggAnimOnlyRight[index].play();
                                }
                            }));
			} else if (index == 4) {
			    zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
                                @Override
                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                }
                                @Override
                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                    oggAnimOnlyRight[index].play();
                                }
                            }));
			} else if (index == 6) {
				oggAnimOnlyRight[index].play();
				 zxSpriteRight[index].registerEntityModifier(new DelayModifier(0.6f, new IEntityModifierListener() {
	                                @Override
	                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
	                                }
	                                @Override
	                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
	                                    oggAnimOnlyRight2[index].play();
	                                }
	                            }));
			} else {
				oggAnimOnlyRight[index].play();
			}
			setEnabledGimmics(false);
			zxSpriteRight[index].animate(pDuration, pFrames, loopCount, this);
		} else {
			if (DEBUG) {
				Log.d(TAG, "invalid click on " + index);
			}
		}
		return;
	}

	private void refreshZIndex() {
		for (int i = 0; i < 15; i++) {
			if (i == 0 || i == 1) {
				zxZaSprite[i].setZIndex(5);
			} else if (i == 10) {
				zxZaSprite[i].setZIndex(8);
			} else {
				zxZaSprite[i].setZIndex(2);
			}
			if ((i >= 0 && i <= 1) || (i == 10) || (i == 11) || (i == 13)) {
				if (i == 13 || i == 11) {
					zxSpriteRight[i].setZIndex(3);
					zxSpriteWrong[dragBillBoard[i][1]].setZIndex(3);
				} else if (i == 10) {
					zxSpriteRight[i].setZIndex(9);
					zxSpriteWrong[dragBillBoard[i][1]].setZIndex(9);
				} else {
					zxSpriteRight[i].setZIndex(6);
					zxSpriteWrong[dragBillBoard[i][1]].setZIndex(6);
				}
			} else if ((i >= 2 && i <= 4) || (i == 12) || (i == 14)) {
				if (i == 12 || i == 14) {
					zxSpriteRight[i].setZIndex(10);
					zxSpriteWrong[dragBillBoard[i][1]].setZIndex(10);
				} else {
					zxSpriteRight[i].setZIndex(11);
					zxSpriteWrong[dragBillBoard[i][1]].setZIndex(11);
				}
			} else if (i >= 5 && i <= 9) {
				zxSpriteRight[i].setZIndex(12);
				zxSpriteWrong[dragBillBoard[i][1]].setZIndex(12);
			}
			zxSprite[i].setZIndex(14);
		}
		sprZabtn.setZIndex(1);
		sprZabtnFix.setZIndex(7);
		sprZabtnFix2.setZIndex(4);
		sprCarpet.setZIndex(4);
		anmSprKnQ.setZIndex(6);
		sprGimmic[0].setZIndex(16);
		sprGimmic[1].setZIndex(16);
		sprGimmic[2].setZIndex(16);
		anmSprScroll.setZIndex(13);
		this.mScene.sortChildren();
		return;
	}

	private void setEnabled(final int i) {
	    anmSprScroll.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                }
                @Override
                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    isZxSpriteRightClickable[i] = true;
                }
            }));
		return;
	}
	private void animKnQ() {
		if (isZxSpriteRightClickable[0] && isZxSpriteRightClickable[1]) {
			setEnabledGimmics(false);
			isZxSpriteRightClickable[0] = false;
			isZxSpriteRightClickable[1] = false;
			anmSprScreen.registerEntityModifier(new DelayModifier(0.7f, new IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                            }
                            @Override
                            public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                zxSpriteRight[0].setVisible(false);
                                zxSpriteRight[1].setVisible(false);
                                sprCarpet.setVisible(false);
                                anmSprKnQ.setVisible(true);
                                long pFrameDurations[] = new long[] { 700, 700, 700, 1000 };
                                int pFrames[] = new int[] { 0, 1, 2, 3 };
                                anmSprKnQ.animate(pFrameDurations, pFrames, 0,
                                                Vol3Hinamatsuri.this);
                                anmSprKnQ.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifierListener() {
                                    @Override
                                    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                    }
                                    @Override
                                    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                        oggAnimKnQ.play();
                                    }
                                }));
                            }
                        }));
		}
		return;
	}

	private void animSakurain() {
		oggAnimSakurain.play();
		setEnabledGimmics(false);
		anmSprSakura.setVisible(true);
		long pFrameDurations[] = { 700, 700, 700, 700 };
		int pFrames[] = { 0, 1, 2, 3 };
		anmSprSakura.animate(pFrameDurations, pFrames, 0, this);
		return;
	}

	private void checkAllSet() {
		int dragCount = 0;
		int dragFail = 0;
		for (int i = 0; i < 15; i++) {
			if (dragBillBoard[i][0] != 0) {
				dragCount++;
				if (dragBillBoard[i][0] == 2) {
					dragFail++;
				}
			}
		}
		if (dragCount == 15) {
			if (dragFail > 0 && anmSprScroll.getCurrentTileIndex() != 5) {
			    anmSprScroll.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
                                @Override
                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                }
                                @Override
                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                    oggAllSet.play();
                                }
                            }));
			} else if (dragFail == 0) {
			    anmSprScroll.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
                                @Override
                                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                                }
                                @Override
                                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                                    animSakurain();
                                }
                            }));
			}
		}
		return;
	}
	private class DraggableSprite extends Sprite {
		float posY;
		int index;
		Type type;
		int wrongPos;
		boolean draggable = true;
		DragState dragState = DragState.UNDRAGGED;

		public DraggableSprite(float pX, float pY,
				ITextureRegion pTextureRegion, int ps, Type t) {
			super(pX, pY, pTextureRegion, Vol3Hinamatsuri.this.getVertexBufferObjectManager());
			this.posY = pY;
			this.index = ps;
			this.type = t;
		}

		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			dragCount = 0;
			for (int i = 0; i < 15; i++) {
				if (dragBillBoard[i][0] != 0) {
					dragCount++;
				}
			}
			if ((((this.dragState == DragState.UNDRAGGED && this.isVisible()) || (this.dragState == DragState.DRAGGED_WRONG && this.draggable)))
					&& dragCount != 15) {
				int action = pSceneTouchEvent.getAction();
				float pX = pSceneTouchEvent.getX();
				float pY = pSceneTouchEvent.getY();
				switch (action) {
				//TODO
				case TouchEvent.ACTION_DOWN:
					if(!isTouchAreas){
	                                        Log.d(TAG, "ACTION_DOWN on DeBug" + this.index);
        					this.setScale(2.1f);
        					this.setZIndex(15);
        					mScene.sortChildren();
        					if (this.dragState == DragState.DRAGGED_WRONG) {
        						zxSpriteWrong[this.index].setVisible(false);
        					}
        					if (!this.isVisible()) {
        						this.setVisible(true);
        					}
        					this.setPosition(pX - this.getWidth() / 2,
        							pY - this.getHeight() / 2);
        					isTouchAreas = true;
    					}
					break;

				case TouchEvent.ACTION_MOVE:
				    if(isTouchAreas){
					if (this.index == 11 || this.index == 13) {
						zxZaSprite[11].setVisible(true);
						zxZaSprite[13].setVisible(true);
					} else if (this.index == 12 || this.index == 14) {
						zxZaSprite[12].setVisible(true);
						zxZaSprite[14].setVisible(true);
					} else {
						zxZaSprite[this.index].setVisible(true);
					}
					this.setPosition(pX - this.getWidth() / 2,
							pY - this.getHeight() / 2);
				    }
					break;

				case TouchEvent.ACTION_UP:
					Log.d(TAG, "ACTION_UP on " + this.index);
					if(isTouchAreas){
        					if (this.index == 11 || this.index == 13) {
        						zxZaSprite[11].setVisible(false);
        						zxZaSprite[13].setVisible(false);
        					} else if (this.index == 12 || this.index == 14) {
        						zxZaSprite[12].setVisible(false);
        						zxZaSprite[14].setVisible(false);
        					} else {
        						zxZaSprite[this.index].setVisible(false);
        					}
        					// Check position
        					boolean trueOrFalse = false;
        					int tmp = this.index;
        					if (this.index == 11 || this.index == 13) {
        						if (checkDrag(pX, pY, 11)) {
        							trueOrFalse = checkDrag(pX, pY, 11);
        							tmp = 11;
        						} else if (checkDrag(pX, pY, 13)) {
        							trueOrFalse = checkDrag(pX, pY, 13);
        							tmp = 13;
        						}
        					} else if (this.index == 12 || this.index == 14) {
        						if (checkDrag(pX, pY, 12)) {
        							trueOrFalse = checkDrag(pX, pY, 12);
        							tmp = 12;
        						} else if (checkDrag(pX, pY, 14)) {
        							trueOrFalse = checkDrag(pX, pY, 14);
        							tmp = 14;
        						}
        					} else {
        						trueOrFalse = checkDrag(pX, pY, this.index);
        					}
        					if (trueOrFalse) {
        						if (checkDragBillBoard(tmp) == 1) {
        							if (DEBUG) {
        								Log.d(TAG, "checkDragBillBoard=1");
        							}
        						} else {
        							if (DEBUG) {
        								Log.d(TAG, "GOAL! & the finisher is "
        										+ this.index);
        							}
        							if (this.dragState == DragState.DRAGGED_WRONG) {
        								dragBillBoardUpdate(this.wrongPos, 0, 0);
        							}
        							this.setVisible(false);
        							this.setPosition(827, 0);
        							this.setScale(1f);
        						}
        						if (checkDragBillBoard(tmp) == 0) {
        							this.dragState = DragState.DRAGGED_RIGHT;
        							this.setVisible(false);
        							dragBillBoardUpdate(tmp, 1, this.index);
        							animDragRight(tmp);
        							checkAllSet();
        						} else if (checkDragBillBoard(tmp) == 2) {
        							zxSpriteWrong[dragBillBoard[tmp][1]]
        									.setVisible(false);
        							zxSprite[dragBillBoard[tmp][1]].renew();
        							this.dragState = DragState.DRAGGED_RIGHT;
        							this.setVisible(false);
        							dragBillBoardUpdate(tmp, 1, this.index);
        							animDragRight(tmp);
        							checkAllSet();
        						}
        					} else {
        						if (this.type == Type.HUMAN) {
        							for (int zx = 0; zx < 10; zx++) {
        								if (checkDrag(pX, pY, zx)
        										&& checkDragBillBoard(zx) != 1
        										&& checkDragBillBoard(zx) != 2) {
        									if (DEBUG) {
        										Log.d(TAG,
        												"HIT THE BAR! & the finisher is "
        														+ this.index);
        									}
        									this.setVisible(false);
        									if (checkDragBillBoard(zx) == 0
        											&& this.dragState != DragState.UNDRAGGED) {
        										dragBillBoardUpdate(this.wrongPos, 0, 0);
        									}
        									this.dragState = DragState.DRAGGED_WRONG;
        									dragBillBoardUpdate(zx, 2, this.index);
        									this.wrongPos = zx;
        									putPositionSprite(zxSprite[this.index], zx,
        											this.index);
        									animDragWrong(this.index, zx);
        									checkAllSet();
        									break;
        								}
        							}
        						} else if (this.type == Type.GOOD) {
        							for (int nova = 10; nova < 15; nova++) {
        								if (checkDrag(pX, pY, nova)
        										&& checkDragBillBoard(nova) != 1
        										&& checkDragBillBoard(nova) != 2) {
        									if (DEBUG) {
        										Log.d(TAG,
        												"HIT THE BAR! & the finisher is "
        														+ this.index);
        									}
        									this.setVisible(false);
        									if (checkDragBillBoard(nova) == 0
        											&& this.dragState != DragState.UNDRAGGED) {
        										dragBillBoardUpdate(this.wrongPos, 0, 0);
        									}
        									this.dragState = DragState.DRAGGED_WRONG;
        									dragBillBoardUpdate(nova, 2, this.index);
        									putPositionSprite(zxSprite[this.index],
        											nova, this.index);
        									animDragWrong(this.index, nova);
        									this.wrongPos = nova;
        									checkAllSet();
        									break;
        								}
        							}
        						}
        					}
        					if (this.dragState == DragState.UNDRAGGED) {
        						if (DEBUG) {
        							Log.d(TAG, "WAY OFF TARGET! & the finisher is "
        									+ this.index);
        						}
        						this.setScale(1f);
        						this.setPosition(827, this.posY);
        					} else if (this.dragState == DragState.DRAGGED_WRONG
        							&& this.isVisible()) {
        						this.setVisible(false);
        						this.rollbackPosition();
        						zxSpriteWrong[this.index].setVisible(true);
        					}
        					if (DEBUG) {
        						Log.d(TAG,
        								"---------------------------------------------------------------");
        						Log.d(TAG, "DRAG BILLBOARD :D");
        						for (int i = 0; i < 15; i++) {
        							Log.d(TAG, "pos = " + i + " & value = "
        									+ dragBillBoard[i][0] + " & spr = "
        									+ dragBillBoard[i][1]);
        						}
        						Log.d(TAG,
        								"---------------------------------------------------------------");
        					}
        					this.setZIndex(14);
        					mScene.sortChildren();
        					scrollRefresh();
        					isTouchAreas = false;
					}
					break;
				}
				return true;
			}
			return false;
		}

		private boolean checkDrag(float pX, float pY, int positionIndex) {
			Log.d("POSITION", "" + positionIndex);
			float x = pX;
			float y = (float) (pY + (3 * (2.1 * this.getHeight()) / 8));
			float y2 = (float) (pY + (2.1 * this.getHeight()) / 2);
			Log.d("GSDGSG", y+"/"+y2);
			if (x > Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][0]
					&& x < Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][0]
							+ Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][2]
					&& ((y > Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][1] && y < Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][1]
							+ Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][3]) || (y2 > Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][1] && y2 < Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][1]
							+ Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[positionIndex][3]))) {
				return true;
			}
			return false;
		}

		private boolean renew() {
			if (DEBUG) {
				Log.d(TAG, "Renew: " + this.index);
			}
			this.dragState = DragState.UNDRAGGED;
			this.setScale(1f);
			this.setVisible(false);
			this.setPosition(827, 0);
			return true;
		}

		private void rollbackPosition() {
			float x = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[this.wrongPos][0];
			float y = Vol3HinamatsuriResource.ARR_POSITION_ZABUTON[this.wrongPos][1] - 70;
			this.setPosition(x, y);
			return;
		}
	}

    @Override
    public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
        if (pAnimatedSprite == zxSpriteRight[0]) {
            zxSpriteRight[0].setCurrentTileIndex(4);
            setEnabled(0);
    } else if (pAnimatedSprite == zxSpriteRight[1]) {
            //Replace getInitialX() -> getX();
            zxSpriteRight[1].setPosition(zxSpriteRight[1].getmXFirst(),
                            zxSpriteRight[1].getmYFirst());
            zxSpriteRight[1].setCurrentTileIndex(4);
            setEnabled(1);
    } else if (pAnimatedSprite == zxSpriteRight[2]) {
            zxSpriteRight[2].setCurrentTileIndex(2);
            setEnabled(2);
    } else if (pAnimatedSprite == zxSpriteRight[3]) {
            zxSpriteRight[3].setCurrentTileIndex(2);
            setEnabled(3);
    } else if (pAnimatedSprite == zxSpriteRight[4]) {
            zxSpriteRight[4].setCurrentTileIndex(0);
            setEnabled(4);
    } else if (pAnimatedSprite == zxSpriteRight[5]) {
            zxSpriteRight[5].setCurrentTileIndex(2);
            setEnabled(5);
    } else if (pAnimatedSprite == zxSpriteRight[6]) {
            zxSpriteRight[6].setCurrentTileIndex(5);
            setEnabled(6);
    } else if (pAnimatedSprite == zxSpriteRight[7]) {
            zxSpriteRight[7].setCurrentTileIndex(2);
            setEnabled(7);
    } else if (pAnimatedSprite == zxSpriteRight[8]) {
            zxSpriteRight[8].setCurrentTileIndex(2);
            setEnabled(8);
    } else if (pAnimatedSprite == zxSpriteRight[9]) {
            zxSpriteRight[9].setCurrentTileIndex(2);
            setEnabled(9);
    } else if (pAnimatedSprite == zxSpriteRight[10]) {
            setEnabled(10);
    } else if (pAnimatedSprite == zxSpriteRight[12]) {
            zxSpriteRight[12].setCurrentTileIndex(0);
            setEnabled(12);
    } else if (pAnimatedSprite == zxSpriteRight[14]) {
            zxSpriteRight[14].setCurrentTileIndex(0);
            setEnabled(14);
    } else if (pAnimatedSprite == zxSpriteWrong[1]) {
            zxSpriteWrong[1].setCurrentTileIndex(0);
            zxSprite[1].draggable = true;
    } else if (pAnimatedSprite == zxSpriteWrong[6]) {
            zxSpriteWrong[6].setCurrentTileIndex(1);
            zxSprite[6].draggable = true;
    } else if (pAnimatedSprite == zxSpriteWrong[7]) {
            zxSpriteWrong[7].setCurrentTileIndex(1);
            zxSprite[7].draggable = true;
    } else if (pAnimatedSprite == anmSprScreen) {
            isFoldingScreenClickable = true;
    } else if (pAnimatedSprite == anmSprScroll) {
            anmSprScroll.setCurrentTileIndex(5);
            anmSprScroll.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                }
                @Override
                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    isScrollClickable = true;
                }
            }));
    } else if (pAnimatedSprite == anmSprSakura) {
            anmSprSakura.setVisible(false);
            anmSprSakura.setCurrentTileIndex(0);
            sprSakurain.setVisible(true);
            sprSakurain.registerEntityModifier(new DelayModifier(0.3f, new IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                }
                @Override
                public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
                    sprSakurain.setVisible(false);
                }
            }));
    } else if (pAnimatedSprite == anmSprKnQ) {
            sprCarpet.setVisible(true);
            zxSpriteRight[0].setVisible(true);
            zxSpriteRight[1].setVisible(true);
            anmSprKnQ.setVisible(false);
            anmSprKnQ.setCurrentTileIndex(0);
            isZxSpriteRightClickable[0] = true;
            isZxSpriteRightClickable[1] = true;
    } else {
            for (int i = 0; i < 15; i++) {
                    if (pAnimatedSprite == zxSpriteWrong[i]) {
                            zxSprite[i].draggable = true;
                    }
            }
    }
    if (DEBUG) {
            Log.d(TAG, "gimmicsDisabledTimeCount = " + gimmicsDisabledTimeCount);
    }
    if (isTouchGimmic[0] == false) {
            gimmicsDisabledTimeCount -= 1;
    }
    if (gimmicsDisabledTimeCount == 0) {
            setEnabledGimmics(true);
    }
    return;
        
    }

    @Override
    public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }
}
