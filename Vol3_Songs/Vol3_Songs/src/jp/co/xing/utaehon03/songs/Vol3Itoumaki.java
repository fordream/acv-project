package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.RanDomNoRepeat;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3ItoumakiResource;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

public class Vol3Itoumaki extends BaseGameActivity{
    //Define tag log for this class
    private String TAG_LOG = Vol3Itoumaki.this.getClass().toString();
    private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
    //Define Texture
    //Define TextureRegion
    private ITextureRegion curtainTrBackground1,curtainTrBackground2,curtainTrBackground3,curtainTrBackground4,
                                            haikeiTrBackground;
    //Define Sprite
    private Sprite curtainSpBackground1,curtainSpBackground2,curtainSpBackground3,curtainSpBackground4,
                               haikeiSpBackground;
    private TiledTextureRegion kamihubukiTl;
    private AnimatedSprite kamihubukiSp;
    private boolean butaActionUp = false, zouActionUp = false, girlActionUp = false, kobitoActionUp = false,
                                    kameActionUp = false, niwatoriActionUp = false, kaeruActionUp = false,
                                    umaActionUp = false, penginActionUp = false, nekoActionUp = false;
    private TiledTextureRegion butaTlObject, zouTlObject, girlTlObject, 
    kobitoTlObject, kameTlObject, niwatoriTlObject, kaeruTlObject, 
    umaTlObject, penginTlObject, nekoTlObject;
    private AnimatedSprite butaAsObject, zouAsObject, girlAsObject, kobitoAsObject, kameAsObject,
                    niwatoriAsObject, kaeruAsObject, umaAsObject, penginAsObject, nekoAsObject;
    private RanDomNoRepeat ran;
    private TiledTextureRegion basanTlObject;
    private AnimatedSprite basanAsObject;
    
    private boolean touchShoes = false;
    //Define  object resource include 8 items;
  //Define object include 2 items;
    private TiledTextureRegion butaTlShoesObject, zouTlShoesObject, girlTlShoesObject, 
    kobitoTlShoesObject, kameTlShoesObject, niwatoriTlShoesObject, kaeruTlShoesObject, 
    umaTlShoesObject, penginTlShoesObject, nekoTlShoesObject;
    private AnimatedSprite butaAsShoesObject, zouAsShoesObject, girlAsShoesObject, 
    kobitoAsShoesObject, kameAsShoesObject, niwatoriAsShoesObject, kaeruAsShoesObject, 
    umaAsShoesObject, penginAsShoesObject, nekoAsShoesObject;
    private TiledTextureRegion boxTlContainObject;
    private AnimatedSprite boxAsContainObject;
    private ITextureRegion basanCicleTl;
    private Sprite basanCicleSp;
    private boolean basanGrabbed = false;
    private int randomeAction = 0;
    private int countdown = 10;
    private boolean touchContainObject = false;
    private TexturePack Vol3ItoumakiPacker_1, Vol3ItoumakiPacker_2, Vol3ItoumakiPacker_3;
    private TexturePackTextureRegionLibrary Vol3ItoumakiLibrary2, Vol3ItoumakiLibrary3;
    //Define array random for shoes object
    private int [][] randomShoes1 = new int[][] {
            {180, 420},
            {460, 300},
            {335, 300},
            {99, 478},
            {60, 324},
            {475, 440},
            {210, 300},
            {73, 536},
            {310, 408},
            {330, 510}
    };
    private int [][] randomShoes2 = new int[][] {
            {93, 311},
            {70, 405},
            {222, 317},
            {198, 393},
            {168, 468},
            {306, 353},
            {332, 465},
            {402, 310},
            {405, 387},
            {489, 479}
        };
    private int [][] randomShoes3 = new int[][] {
            {475, 430},
            {77, 314},
            {330, 510},
            {99, 478},
            {440, 310},
            {175, 410},
            {216, 314},
            {73, 536},
            {315, 408},
            {340, 310}
        };
    private int [][] randomeShoesTemp;
    private Random randomeShoes;
    private int ranShoes;
    private boolean isTouchGimic = false;
    private int zIndex;
    //Load resource factory include sound and images
    @Override
    public void onCreateResources() {
        Log.d(TAG_LOG,"on Create Rerource");
        SoundFactory.setAssetBasePath("itoumaki/mfx/");
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("itoumaki/gfx/");
        mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), pAssetManager, "itoumaki/gfx/");
        super.onCreateResources();
    }
    //Load sound
    private Sound A2_10_POYON, A2_6_7_WAI1, A2_15_SYU2, A2_KIRA8,
    A2_12_POKA, A2_PYOI, A2_5_ZANNENPAON, A2_8_KAME2, A2_4_BUTA2, A2_7_13_TOKO,
    A2_15_HAKUSYU, A2_FAFAFAN, A2_12_SYU2 , A2_KUTU_POMI, A2_9_NIWATRI1_2, 
    A2_7_EHE, A2_5_ZOU2 ,A2_11_UMA2, A2_6_TIGAU1, A2_8_SUPON,
    A2_12_GIRL1, A2_6_12_GIRL2, A2_10_BYON, A2_13_NEKO6, A2_8_KAME1, A2_4_BUJOBUHI,
    
    A2_9_KOKE_2, A2_13_NEKO5, A2_4_BUHI, A2_5_PAON, A2_9_KOKE2, A2_14_ITOGURUMA,
    A2_6_AHAHA, A2_10_KERO, A2_11_INANAKI, A2_11_UMA1, A2_7_TIGAU2, A2_5_ZOU1, A2_14_YATAWANE,
    A2_6_GIRL1, A2_9_NIWATORI3 , A2_4_7_KATAI;
    @Override
    protected void loadKaraokeSound() {
        A2_10_POYON = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_10_POYON);
        A2_6_7_WAI1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_6_7_WAI1);
        A2_15_SYU2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_15_SYU2);
        A2_KIRA8 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_KIRA8);
        A2_12_POKA = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_12_POKA);
        A2_PYOI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_PYOI);
        A2_5_ZANNENPAON = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_5_ZANNENPAON);
        A2_8_KAME2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_8_KAME2);
        A2_4_BUTA2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_4_BUTA2);
        A2_7_13_TOKO = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_7_13_TOKO);
        A2_15_HAKUSYU = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_15_HAKUSYU);
        A2_FAFAFAN = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_FAFAFAN);
        A2_12_SYU2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_12_SYU2);
        A2_KUTU_POMI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_KUTU_POMI);
        A2_9_NIWATRI1_2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_9_NIWATRI1_2);
        A2_7_EHE = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_7_EHE);
        A2_5_ZOU2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_5_ZOU2);
        A2_11_UMA2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_11_UMA2);
        A2_6_TIGAU1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_6_TIGAU1);
        A2_8_SUPON = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_8_SUPON);
        A2_12_GIRL1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_12_GIRL1);
        A2_6_12_GIRL2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_6_12_GIRL2);
        A2_10_BYON = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_10_BYON);
        A2_13_NEKO6 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_13_NEKO6);
        A2_8_KAME1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_8_KAME1);
        A2_4_BUJOBUHI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_4_BUJOBUHI);
        A2_9_KOKE_2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_9_KOKE_2); 
        A2_13_NEKO5 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_13_NEKO5);
        A2_4_BUHI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_4_BUHI);
        A2_5_PAON = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_5_PAON);
        A2_9_KOKE2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_9_KOKE2);
        A2_14_ITOGURUMA = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_14_ITOGURUMA);
        A2_6_AHAHA = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_6_AHAHA);
        A2_10_KERO = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_10_KERO);
        A2_11_INANAKI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_11_INANAKI);
        A2_11_UMA1= loadSoundResourceFromSD(Vol3ItoumakiResource.A2_11_UMA1);
        A2_7_TIGAU2 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_7_TIGAU2);
        A2_5_ZOU1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_5_ZOU1);
        A2_14_YATAWANE = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_14_YATAWANE);
        A2_6_GIRL1 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_6_GIRL1);
        A2_9_NIWATORI3 = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_9_NIWATORI3);
        A2_4_7_KATAI = loadSoundResourceFromSD(Vol3ItoumakiResource.A2_4_7_KATAI);
    }
    //Load Karaoke Resource
    @Override
    protected void loadKaraokeResources() {
            Vol3ItoumakiPacker_1 = mTexturePackLoaderFromSource.load("Vol3ItoumakiPacker1.xml");
            Vol3ItoumakiPacker_1.loadTexture();      
            Vol3ItoumakiPacker_2 = mTexturePackLoaderFromSource.load("Vol3ItoumakiPacker2.xml");
            Vol3ItoumakiPacker_2.loadTexture();
            Vol3ItoumakiLibrary2 = Vol3ItoumakiPacker_2.getTexturePackTextureRegionLibrary();
            
            Vol3ItoumakiPacker_3 = mTexturePackLoaderFromSource.load("Vol3ItoumakiPacker3.xml");
            Vol3ItoumakiPacker_3.loadTexture();
            Vol3ItoumakiLibrary3 = Vol3ItoumakiPacker_3.getTexturePackTextureRegionLibrary();
            
        curtainTrBackground1 = Vol3ItoumakiLibrary3.get(Vol3ItoumakiResource.Vol3ItoumakiPacker3.A2_15_1_IPHONE_CURTAIN_ID);
        curtainTrBackground2 = Vol3ItoumakiLibrary3.get(Vol3ItoumakiResource.Vol3ItoumakiPacker3.A2_15_2_IPHONE_CURTAIN_ID);
        curtainTrBackground3 =  Vol3ItoumakiLibrary3.get(Vol3ItoumakiResource.Vol3ItoumakiPacker3.A2_15_3_IPHONE_CURTAIN_ID);
        haikeiTrBackground = Vol3ItoumakiLibrary3.get(Vol3ItoumakiResource.Vol3ItoumakiPacker3.A2_16_2_IPHONE_HAIKEI_ID);
        
        curtainTrBackground4 = Vol3ItoumakiLibrary2.get(Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_15_4_IPHONE_CURTAIN_ID);
        
        
        kamihubukiTl = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_17_1_IPHONE_KAMIHUBUKI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_17_2_IPHONE_KAMIHUBUKI_ID
                );
        //Box Contain Object
        boxTlContainObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                    Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_16_1_1_IPHONE_HAIKEI_ID,
                    Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_16_1_IPHONE_HAIKEI_ID
                    );
      //##############################################################
        //##############################################################
        //Load texture Object include 7, 8, 9 items
      //Texture Buta
        butaTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_1_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_2_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_3_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_4_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_5_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_6_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_7_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_8_IPHONE_BUTA_ID
                );
      //Texture Zou
        zouTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_1_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_2_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_3_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_4_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_5_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_6_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_7_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_8_IPHONE_ZOU_ID);
      //Texture Girl
        girlTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_1_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_2_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_3_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_4_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_5_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_6_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_7_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_8_IPHONE_GIRL_ID);
      //Texture Kobito
        kobitoTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_1_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_2_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_3_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_4_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_5_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_6_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_7_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_8_IPHONE_KOBITO_ID);
      //Texture Kame
        kameTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_1_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_2_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_3_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_4_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_5_IPHONE_KAME_ID, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_6_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_7_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_8_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_9_IPHONE_KAME_ID);
        //Texture Niwatori
        niwatoriTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_1_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_2_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_3_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_4_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_5_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_6_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_7_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_8_IPHONE_NIWATORI_ID
                );
        
      //Texture Kaeru
        kaeruTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_1_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_2_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_3_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_4_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_5_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_6_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_7_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_8_IPHONE_KAERU_ID);
      //Texture Uma
        umaTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_1_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_2_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_3_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_4_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_5_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_6_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_7_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_8_IPHONE_UMA_ID
                );
      //Texture Pengin
        penginTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_1_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_2_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_3_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_4_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_5_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_6_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_7_IPHONE_PENGIN_ID);
      //Texture Neko
        nekoTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_1_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_2_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_3_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_4_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_5_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_6_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_7_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_8_IPHONE_NEKO_ID);
        //Texture Basan
        basanTlObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_1_IPHONE_BASAN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_2_IPHONE_BASAN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_3_IPHONE_BASAN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_4_IPHONE_BASAN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_5_IPHONE_BASAN_ID);

        //##############################################################
        //##############################################################
      //Load texture clude 2 items(Shoes)
        //Texture Buta Shoes
        butaTlShoesObject = getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_9_IPHONE_BUTA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_4_10_IPHONE_BUTA_ID
        );
      //Texture Zou
        zouTlShoesObject =
        getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_9_IPHONE_ZOU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_5_10_IPHONE_ZOU_ID
        );
      //Texture Girl
        girlTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_9_IPHONE_GIRL_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_6_10_IPHONE_GIRL_ID
        );
      //Texture Kobito
        kobitoTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_9_IPHONE_KOBITO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_7_10_IPHONE_KOBITO_ID);
      //Texture Kame
        kameTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_10_IPHONE_KAME_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_8_11_IPHONE_KAME_ID);
        //Texture Niwatori
        niwatoriTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_9_IPHONE_NIWATORI_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_9_10_IPHONE_NIWATORI_ID);
      //Texture Kaeru
        kaeruTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_1, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_9_IPHONE_KAERU_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker1.A2_10_10_IPHONE_KAERU_ID);
      //Texture Uma
        umaTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_9_IPHONE_UMA_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_11_10_IPHONE_UMA_ID);
      //Texture Pengin
        penginTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_8_IPHONE_PENGIN_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_12_9_IPHONE_PENGIN_ID);
      //Texture Neko
        nekoTlShoesObject =  getTiledTextureFromPacker(Vol3ItoumakiPacker_2, 
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_9_IPHONE_NEKO_ID,
                Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_13_10_IPHONE_NEKO_ID);
        
        basanCicleTl = Vol3ItoumakiLibrary2.get(Vol3ItoumakiResource.Vol3ItoumakiPacker2.A2_14_IPHONE_BASAN_ID);
    }
    //Attach into Karaoke Scene
    @Override
    protected void loadKaraokeScene() {
        this.mScene = new Scene();
        //Create a random
        ran = new RanDomNoRepeat();
        ran.setLenghNoRepeat(10);
        randomeShoes = new Random();
        //Create a array position for shoes object
        randomShoes();
        //Background bound in
        haikeiSpBackground = new Sprite(0, 0, haikeiTrBackground, this.getVertexBufferObjectManager());
        this.mScene.attachChild(haikeiSpBackground);
        //#################################################################
        //#################################################################
        //SPRITE OBJECT
        //AnimatedSprite Buta
        butaAsObject = addAnimatedSpriteItomaki(butaAsObject, this.mScene, 960, 103, butaTlObject, 0);
        //AnimatedSprite Zou
        zouAsObject =  addAnimatedSpriteItomaki(zouAsObject, this.mScene, 960, 50,zouTlObject,1);
        //AnimatedSprite Girl
        girlAsObject =   addAnimatedSpriteItomaki(girlAsObject, this.mScene, 960, 10, girlTlObject,2);
        //AnimatedSprite Kobito
        kobitoAsObject=  addAnimatedSpriteItomaki(kobitoAsObject, this.mScene, 960, 123, kobitoTlObject,3);
        //AnimatedSprite Kame
        kameAsObject =  addAnimatedSpriteItomaki(kameAsObject, this.mScene, 960, 80, kameTlObject,4);
        //AnimatedSprite Niwatori
        niwatoriAsObject =   addAnimatedSpriteItomaki(niwatoriAsObject, this.mScene, 960, 60, niwatoriTlObject,5);
        //AnimatedSprite Kaeru
        kaeruAsObject =   addAnimatedSpriteItomaki(kaeruAsObject, this.mScene, 960, 90, kaeruTlObject,6);
        //AnimatedSprite Uma
        umaAsObject =   addAnimatedSpriteItomaki(umaAsObject, this.mScene, 960, 70, umaTlObject,7);
        //AnimatedSprite Pengin
        penginAsObject =  addAnimatedSpriteItomaki(penginAsObject, this.mScene, 960, 33, penginTlObject,8);
        //AnimatedSprite Neko
        nekoAsObject =  addAnimatedSpriteItomaki(nekoAsObject, this.mScene, 960, 123, nekoTlObject,9);
        //#############################################################
        //#############################################################
      //Box contain object
        boxAsContainObject = new AnimatedSprite(61,253,boxTlContainObject, this.getVertexBufferObjectManager());
        this.mScene.attachChild(boxAsContainObject);
        boxAsContainObject.setCurrentTileIndex(1);
        boxAsContainObject.setVisible(true);
        //SPRITE SHOES
        //End shoes
        //Shoes Buta
        butaAsShoesObject = addSpriteShoes(butaAsShoesObject, this.mScene, randomeShoesTemp[0][0], randomeShoesTemp[0][1], butaTlShoesObject, 0);
        //Shoes Zou
        zouAsShoesObject = addSpriteShoes(zouAsShoesObject, this.mScene, randomeShoesTemp[1][0], randomeShoesTemp[1][1], zouTlShoesObject, 1);
        //Shoes Girl
        girlAsShoesObject = addSpriteShoes(girlAsShoesObject, this.mScene, randomeShoesTemp[2][0], randomeShoesTemp[2][1], girlTlShoesObject, 2);
        //Shoes Kobito
        kobitoAsShoesObject = addSpriteShoes(kobitoAsShoesObject, this.mScene, randomeShoesTemp[3][0], randomeShoesTemp[3][1], kobitoTlShoesObject, 3);
        //Shoes Kame
        kameAsShoesObject = addSpriteShoes(kameAsShoesObject, this.mScene, randomeShoesTemp[4][0], randomeShoesTemp[4][1], kameTlShoesObject, 4);
        //Shoes Niwatori
        niwatoriAsShoesObject = addSpriteShoes(niwatoriAsShoesObject, this.mScene, randomeShoesTemp[5][0], randomeShoesTemp[5][1], niwatoriTlShoesObject, 5);
        //Shoes Kaeru
        kaeruAsShoesObject = addSpriteShoes(kaeruAsShoesObject, this.mScene, randomeShoesTemp[6][0], randomeShoesTemp[6][1], kaeruTlShoesObject, 6);
        //Shoes Uma
        umaAsShoesObject = addSpriteShoes(umaAsShoesObject, this.mScene, randomeShoesTemp[7][0], randomeShoesTemp[7][1], umaTlShoesObject, 7);
        //Shoes Pengin
        penginAsShoesObject = addSpriteShoes(penginAsShoesObject, this.mScene, randomeShoesTemp[8][0], randomeShoesTemp[8][1], penginTlShoesObject, 8);
        //Shoes Neko
        nekoAsShoesObject = addSpriteShoes(nekoAsShoesObject, this.mScene, randomeShoesTemp[9][0], randomeShoesTemp[9][1], nekoTlShoesObject, 9);
        //####################################################
        //####################################################
        //Object Basan
        //Sprite Basan Cicle
        basanCicleSp = new Sprite(558, 342, basanCicleTl, this.getVertexBufferObjectManager());
        this.mScene.attachChild(basanCicleSp);
        //AnimatedSprite Basan Object
        basanAsObject= new AnimatedSprite(555, 315, basanTlObject, this.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                    float pTouchAreaLocalX, float pTouchAreaLocalY) {
                int px = (int)pSceneTouchEvent.getX();
                int py = (int)pSceneTouchEvent.getY();
                if(pSceneTouchEvent.isActionDown()){
                    if(checkContains(this, 242, 21, 333, 300, px, py) || checkContains(this, 180, 70, 240, 300, px, py)){
                        if(!basanGrabbed){
                            basanGrabbed = true;
                            isTouchGimic = true;
                            setTouchGimmic3(false);
                            changeBasanObject();
                        }
                    }
                }
                return super
                        .onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.mScene.attachChild(basanAsObject);
        this.mScene.registerTouchArea(basanAsObject);
        
        kamihubukiSp = new AnimatedSprite(96, 0, kamihubukiTl, this.getVertexBufferObjectManager());
        this.mScene.attachChild(kamihubukiSp);
        kamihubukiSp.setVisible(false);
        
        curtainSpBackground4 = new Sprite(59, 115, curtainTrBackground4, this.getVertexBufferObjectManager());
        this.mScene.attachChild(curtainSpBackground4);
        curtainSpBackground4.setVisible(false);
        
        curtainSpBackground2 = new Sprite(0, 0, curtainTrBackground2, this.getVertexBufferObjectManager());
        this.mScene.attachChild(curtainSpBackground2);
        curtainSpBackground2.setVisible(false);
        
        curtainSpBackground3 = new Sprite(0, 0, curtainTrBackground3, this.getVertexBufferObjectManager());
        this.mScene.attachChild(curtainSpBackground3);
        curtainSpBackground3.setVisible(false);
        //Background haikei bound out
        curtainSpBackground1 = new Sprite(0, 0, curtainTrBackground1, this.getVertexBufferObjectManager());
        this.mScene.attachChild(curtainSpBackground1);
        //Sound Gimic
        addGimmicsButton(this.mScene, Vol3ItoumakiResource.SOUND_GIMMIC,  Vol3ItoumakiResource.IMAGE_GIMMIC, Vol3ItoumakiLibrary2);
    }
    //return random temp for shoes object
    public void randomShoes(){
        ranShoes = randomeShoes.nextInt(3);
        if(ranShoes == 0){
            randomeShoesTemp = randomShoes1;
        }else if(ranShoes==1){
            randomeShoesTemp = randomShoes2;
        }else{
            randomeShoesTemp = randomShoes3;
        }
    }
    //return default first value
    @Override
    public void onPauseGame() {
        butaActionUp = false;
        zouActionUp = false;
        girlActionUp = false;
        kobitoActionUp = false;
        kameActionUp = false;
        niwatoriActionUp = false;
        kaeruActionUp = false;
        umaActionUp = false;
        penginActionUp = false;
        nekoActionUp = false;
        basanGrabbed = false;
        isTouchGimic = false;
        touchShoes = false;
        randomeAction = 0;
        countdown = 10;
        touchContainObject = false;
        if(butaAsObject!=null){
            butaAsObject.clearEntityModifiers();
            butaAsObject.stopAnimation();
            butaAsObject.setPosition(960, 103);
            butaAsObject.setVisible(false);
        }
        //AnimatedSprite Zou
        if(zouAsObject!=null){
            zouAsObject.clearEntityModifiers();
            zouAsObject.stopAnimation();
            zouAsObject.setPosition(960, 50);
            zouAsObject.setVisible(false);
        }
        //AnimatedSprite Girl
        if(girlAsObject!=null){
            girlAsObject.clearEntityModifiers();
            girlAsObject.stopAnimation();
            girlAsObject.setPosition(960, 10);
            girlAsObject.setVisible(false);
        }
        //AnimatedSprite Kobito
        if(kobitoAsObject!=null){
            kobitoAsObject.clearEntityModifiers();
            kobitoAsObject.stopAnimation();
            kobitoAsObject.setPosition(960, 123);
            kobitoAsObject.setVisible(false);
        }
        //AnimatedSprite Kame
        if(kameAsObject!=null){
            kameAsObject.clearEntityModifiers();
            kameAsObject.stopAnimation();
            kameAsObject.setPosition(960, 80);
            kameAsObject.setVisible(false);
        }
        //AnimatedSprite Niwatori
        if(niwatoriAsObject!=null){
            niwatoriAsObject.clearEntityModifiers();
            niwatoriAsObject.stopAnimation();
            niwatoriAsObject.setPosition(960, 60);
            niwatoriAsObject.setVisible(false);
        }
        //AnimatedSprite Kaeru
        if(kaeruAsObject!=null){
            kaeruAsObject.clearEntityModifiers();
            kaeruAsObject.stopAnimation();
            kaeruAsObject.setPosition(960, 90);
            kaeruAsObject.setVisible(false);
        }
        //AnimatedSprite Uma
        if(umaAsObject!=null){
            umaAsObject.clearEntityModifiers();
            umaAsObject.setPosition(960, 70);
            umaAsObject.setVisible(false);
        }
        //AnimatedSprite Pengin
        if(penginAsObject!=null){
            penginAsObject.clearEntityModifiers();
            penginAsObject.stopAnimation();
            penginAsObject.setPosition(960, 33);
            penginAsObject.setVisible(false);
        }
        //AnimatedSprite Neko
        if(nekoAsObject!=null){
            nekoAsObject.clearEntityModifiers();
            nekoAsObject.stopAnimation();
            nekoAsObject.setPosition(960, 123);
            nekoAsObject.setVisible(false);
        }
        if(basanAsObject!=null){
            basanAsObject.clearEntityModifiers();
            basanAsObject.stopAnimation();
            basanAsObject.setCurrentTileIndex(0);
        }
        if(basanCicleSp!=null){
            basanCicleSp.clearEntityModifiers();
            basanCicleSp.setVisible(true);
            basanCicleSp.setPosition(558, 342);
        }
        if(boxAsContainObject!=null){
            boxAsContainObject.clearEntityModifiers();
            boxAsContainObject.clearUpdateHandlers();
            boxAsContainObject.setCurrentTileIndex(1);
            boxAsContainObject.setVisible(true);
        }
        //Shoes
        //Create random for shoes object
        randomShoes();
        if(butaAsShoesObject!=null){
            butaAsShoesObject.clearEntityModifiers();
            butaAsShoesObject.setCurrentTileIndex(0);
            butaAsShoesObject.setPosition(randomeShoesTemp[0][0], randomeShoesTemp[0][1]);
            butaAsShoesObject.setVisible(true);
        }
        //Shoes Zou
        if(zouAsShoesObject!=null){
            zouAsShoesObject.clearEntityModifiers();
            zouAsShoesObject.setCurrentTileIndex(0);
            zouAsShoesObject.setPosition(randomeShoesTemp[1][0], randomeShoesTemp[1][1]);
            zouAsShoesObject.setVisible(true);
        }
        //Shoes Girl
        if(girlAsShoesObject!=null){
            girlAsShoesObject.clearEntityModifiers();
            girlAsShoesObject.setCurrentTileIndex(0);
            girlAsShoesObject.setPosition(randomeShoesTemp[2][0], randomeShoesTemp[2][1]);
            girlAsShoesObject.setVisible(true);
        }
        //Shoes Kobito
        if(kobitoAsShoesObject!=null){
            kobitoAsShoesObject.clearEntityModifiers();
            kobitoAsShoesObject.setCurrentTileIndex(0);
            kobitoAsShoesObject.setPosition(randomeShoesTemp[3][0], randomeShoesTemp[3][1]);
            kobitoAsShoesObject.setVisible(true);
        }
        //Shoes Kame
        if(kameAsShoesObject!=null){
            kameAsShoesObject.clearEntityModifiers();
            kameAsShoesObject.setCurrentTileIndex(0);
            kameAsShoesObject.setPosition(randomeShoesTemp[4][0], randomeShoesTemp[4][1]);
            kameAsShoesObject.setVisible(true);
        }
        //Shoes Niwatori
        if(niwatoriAsShoesObject!=null){
            niwatoriAsShoesObject.clearEntityModifiers();
            niwatoriAsShoesObject.setCurrentTileIndex(0);
            niwatoriAsShoesObject.setPosition(randomeShoesTemp[5][0], randomeShoesTemp[5][1]);
            niwatoriAsShoesObject.setVisible(true);
        }
        //Shoes Kaeru
        if(kaeruAsShoesObject!=null){
            kaeruAsShoesObject.clearEntityModifiers();
            kaeruAsShoesObject.setCurrentTileIndex(0);
            kaeruAsShoesObject.setPosition(randomeShoesTemp[6][0], randomeShoesTemp[6][1]);
            kaeruAsShoesObject.setVisible(true);
        }
        //Shoes Uma
        if(umaAsShoesObject!=null){
            umaAsShoesObject.clearEntityModifiers();
            umaAsShoesObject.setCurrentTileIndex(0);
            umaAsShoesObject.setPosition(randomeShoesTemp[7][0], randomeShoesTemp[7][1]);
            umaAsShoesObject.setVisible(true);
        }
        //Shoes Pengin
        if(penginAsShoesObject!=null){
            penginAsShoesObject.clearEntityModifiers();
            penginAsShoesObject.setCurrentTileIndex(0);
            penginAsShoesObject.setPosition(randomeShoesTemp[8][0], randomeShoesTemp[8][1]);
            penginAsShoesObject.setVisible(true);
        }
        //Shoes Neko
        if(nekoAsShoesObject!=null){
            nekoAsShoesObject.clearEntityModifiers();
            nekoAsShoesObject.setCurrentTileIndex(0);
            nekoAsShoesObject.setPosition(randomeShoesTemp[9][0], randomeShoesTemp[9][1]);
            nekoAsShoesObject.setVisible(true);
        }
        
        if(curtainSpBackground1!=null){
            curtainSpBackground1.clearEntityModifiers();
            curtainSpBackground1.setVisible(true);
        }
        
        if(curtainSpBackground2!=null){
            curtainSpBackground2.clearEntityModifiers();
            curtainSpBackground2.setVisible(false);
        }
        if(curtainSpBackground3!=null){
            curtainSpBackground3.clearEntityModifiers();
            curtainSpBackground3.setVisible(false);
        }
        if(curtainSpBackground4!=null){
            curtainSpBackground4.clearEntityModifiers();
            curtainSpBackground4.setVisible(false);
        }
        if(kamihubukiSp!=null){
            kamihubukiSp.clearEntityModifiers();
            kamihubukiSp.setVisible(false);
        }
        if(this.mScene!=null){
            this.mScene.clearEntityModifiers();
        }
        ran.clearTmp();
        ran.setLenghNoRepeat(10);
        super.onPauseGame();
    }
    //Show screen when load karaoke complete
    @Override
    protected void loadKaraokeComplete() {
        super.loadKaraokeComplete();
    }
    @Override
    public synchronized void onResumeGame() {
        randomItemsObject();
        changeBoxContainObject();
        super.onResumeGame();
    }
    //using for contain shoes object
    private void changeBoxContainObject(){
        boxAsContainObject.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void reset() {
            }
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if((butaAsObject.getCurrentTileIndex()==2 || zouAsObject.getCurrentTileIndex()==2
                        || girlAsObject.getCurrentTileIndex()==2 || kobitoAsObject.getCurrentTileIndex()==2
                        || kameAsObject.getCurrentTileIndex()==2 || niwatoriAsObject.getCurrentTileIndex()==2
                        || kaeruAsObject.getCurrentTileIndex()==2 || umaAsObject.getCurrentTileIndex()==2
                        || penginAsObject.getCurrentTileIndex()==2 || nekoAsObject.getCurrentTileIndex()==2
                        ) && !touchShoes){ 
                        if(!boxAsContainObject.isAnimationRunning()){
                                changeContainObject();
                        }
                }else{
                    if(boxAsContainObject.isAnimationRunning()){
                        boxAsContainObject.stopAnimation();
                        boxAsContainObject.clearEntityModifiers();
                        boxAsContainObject.setCurrentTileIndex(1);
                    }
                }
            }
        });
    }
    //return when shoes and object same
    private void winCongratulations(){
        //basanCongratulations();
        curtainSpBackground3.setVisible(true);
        curtainSpBackground1.setVisible(false);
        boxAsContainObject.setVisible(false);
        butaAsShoesObject.setVisible(false);
        zouAsShoesObject.setVisible(false);
        girlAsShoesObject.setVisible(false);
        kobitoAsShoesObject.setVisible(false);
        kameAsShoesObject.setVisible(false);
        niwatoriAsShoesObject.setVisible(false);
        kaeruAsShoesObject.setVisible(false);
        umaAsShoesObject.setVisible(false);
        penginAsShoesObject.setVisible(false);
        nekoAsShoesObject.setVisible(false);
        curtainSpBackground3.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                A2_15_SYU2.play();
                curtainSpBackground3.setVisible(false);
                curtainSpBackground2.setVisible(true);
                curtainSpBackground4.setVisible(true);
                curtainSpBackground4.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener() {
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        curtainSpBackground2.setVisible(false);
                        curtainSpBackground1.setVisible(true);
                        A2_15_HAKUSYU.play();
                        kamihubukiCongratulations();
                        basanCongratulations();
                    }

                    @Override
                    public void onModifierStarted(IModifier<IEntity> arg0,
                            IEntity arg1) {
                        // TODO Auto-generated method stub
                        
                    }
                }));
            }

            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                
            }
        }));
    }
    //Change image kamihubuki when win
    private void kamihubukiCongratulations(){
        kamihubukiSp.setVisible(true);
        if(!kamihubukiSp.isAnimationRunning()){
            long pDuration[] = new long[] {1000, 1000};
            int pFram[] = new int[] { 0, 1};
            kamihubukiSp.animate(pDuration, pFram, 1, new IAnimationListener() {
                @Override
                public void onAnimationFinished(AnimatedSprite arg0) {
                    kamihubukiSp.setCurrentTileIndex(0);
                    kamihubukiSp.setVisible(false);
                    kamihubukiSp.stopAnimation();
                }
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
            });
        }
    }
    //Change image basan when win and replay
    private void basanCongratulations(){
        A2_14_YATAWANE.play();
        if(!basanAsObject.isAnimationRunning()){
            basanCicleSp.setVisible(false);
            long pDuration[] = new long[] {2000, 2000};
            int pFram[] = new int[] { 3, 4};
            basanAsObject.animate(pDuration, pFram, 0, new IAnimationListener() {
                @Override
                public void onAnimationFinished(AnimatedSprite arg0) {
                    randomShoes();
                    butaAsShoesObject.setPosition(randomeShoesTemp[0][0], randomeShoesTemp[0][1]);
                    zouAsShoesObject.setPosition(randomeShoesTemp[1][0], randomeShoesTemp[1][1]);
                    girlAsShoesObject.setPosition(randomeShoesTemp[2][0], randomeShoesTemp[2][1]);
                    kobitoAsShoesObject.setPosition(randomeShoesTemp[3][0], randomeShoesTemp[3][1]);
                    kameAsShoesObject.setPosition(randomeShoesTemp[4][0], randomeShoesTemp[4][1]);
                    niwatoriAsShoesObject.setPosition(randomeShoesTemp[5][0], randomeShoesTemp[5][1]);
                    kaeruAsShoesObject.setPosition(randomeShoesTemp[6][0], randomeShoesTemp[6][1]);
                    umaAsShoesObject.setPosition(randomeShoesTemp[7][0], randomeShoesTemp[7][1]);
                    penginAsShoesObject.setPosition(randomeShoesTemp[8][0], randomeShoesTemp[8][1]);
                    nekoAsShoesObject.setPosition(randomeShoesTemp[9][0], randomeShoesTemp[9][1]);
                    basanAsObject.setCurrentTileIndex(0);
                    basanCicleSp.setVisible(true);
                    curtainSpBackground4.setVisible(false);
                    boxAsContainObject.setVisible(true);
                    butaAsShoesObject.setCurrentTileIndex(0);
                    butaAsShoesObject.setVisible(true);
                    zouAsShoesObject.setCurrentTileIndex(0);
                    zouAsShoesObject.setVisible(true);
                    girlAsShoesObject.setCurrentTileIndex(0);
                    girlAsShoesObject.setVisible(true);
                    kobitoAsShoesObject.setCurrentTileIndex(0);
                    kobitoAsShoesObject.setVisible(true);
                    kameAsShoesObject.setCurrentTileIndex(0);
                    kameAsShoesObject.setVisible(true);
                    niwatoriAsShoesObject.setCurrentTileIndex(0);
                    niwatoriAsShoesObject.setVisible(true);
                    kaeruAsShoesObject.setCurrentTileIndex(0);
                    kaeruAsShoesObject.setVisible(true);
                    umaAsShoesObject.setCurrentTileIndex(0);
                    umaAsShoesObject.setVisible(true);
                    penginAsShoesObject.setCurrentTileIndex(0);
                    penginAsShoesObject.setVisible(true);
                    nekoAsShoesObject.setCurrentTileIndex(0);
                    nekoAsShoesObject.setVisible(true);
                    countdown = 10;
                    randomItemsObject();
                    basanGrabbed = false;
                    isTouchGimic = false;
                    setTouchGimmic3(true);
                }

                @Override
                public void onAnimationFrameChanged(AnimatedSprite arg0,
                        int arg1, int arg2) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onAnimationLoopFinished(AnimatedSprite arg0,
                        int arg1, int arg2) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
                    // TODO Auto-generated method stub
                    
                }
            });
        }
    }
    //register Path Modifier when start objects buta, zou, ....
    private void registerFirstPathAniItomaki(final AnimatedSprite ani, final int indexAction){
        float CoordirateX[] = new  float[]{
                ani.getX(), CAMERA_WIDTH/2 - ani.getWidth()/2
        };
        float CoordirateY[] = new  float[]{
                ani.getY(),ani.getY()
        };
        final Path path = new Path(CoordirateX, CoordirateY);
        ani.registerEntityModifier(new PathModifier(2.5f, path, null, new IPathModifierListener() {

            @Override
            public void onPathFinished(PathModifier arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                if(ani.isAnimationRunning()){
                    ani.stopAnimation();
                }
                switch (indexAction) {
              case 0:
                      butaActionUp = true;
                      butaAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 1:
                      zouActionUp = true;
                      zouAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 2:
                      girlActionUp = true;
                      girlAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 3:
                      kobitoActionUp = true;
                      kobitoAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 4:
                      kameActionUp = true;
                      kameAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 5:
                      niwatoriActionUp = true;
                      niwatoriAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 6:
                      kaeruActionUp = true;
                      kaeruAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 7:
                      umaActionUp = true;
                      umaAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 8:
                      penginActionUp = true;
                      penginAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              case 9:
                      nekoActionUp = true;
                      nekoAsObject.setCurrentTileIndex(2);
                      touchContainObject = true;
                  break;
              }
            }

            @Override
            public void onPathStarted(PathModifier arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onPathWaypointFinished(PathModifier arg0, IEntity arg1,
                    int arg2) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onPathWaypointStarted(PathModifier arg0, IEntity arg1,
                    int arg2) {
                // TODO Auto-generated method stub
                
            }
          
          }, EaseLinear.getInstance()));
    }
  //register Path Modifier when choose shoes contain object buta, zou....
    private void registerAfterPathAniItomaki(final AnimatedSprite ani){
        float CoordirateX[] = new  float[]{
                ani.getX(),  - ani.getWidth()
        };
        float CoordirateY[] = new  float[]{
                ani.getY(),ani.getY()
        };
        final Path path = new Path(CoordirateX, CoordirateY);
        ani.registerEntityModifier(new PathModifier(2.5f, path, null, new IPathModifierListener() {
            @Override
            public void onPathFinished(PathModifier arg0, IEntity arg1) {
                ani.stopAnimation();
                ani.setVisible(false);
                ani.setPosition(960, ani.getY());
                if(countdown!=0){
                    Vol3Itoumaki.this.mScene.registerEntityModifier(new DelayModifier(1.0f, new IEntityModifierListener() {
                        @Override
                        public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                            Log.d(TAG_LOG, "This random next Object");
                            randomItemsObject();
                            Log.d(TAG_LOG,"Count down"+countdown);
                        }
                      @Override
                      public void onModifierStarted(IModifier<IEntity> arg0,
                              IEntity arg1) {
                      }
                    }));
                }
                else{
                    basanGrabbed = true;
                    isTouchGimic = true;
                    setTouchGimmic3(false);
                    if(basanAsObject.isAnimationRunning()){
                        basanAsObject.clearEntityModifiers();
                        basanAsObject.stopAnimation();
                        basanCicleSp.clearEntityModifiers();
                    }
                    winCongratulations();
                }
            }
            @Override
            public void onPathStarted(PathModifier arg0, IEntity arg1) {
                
            }

            @Override
            public void onPathWaypointFinished(PathModifier arg0, IEntity arg1,
                    int arg2) {
            }

            @Override
            public void onPathWaypointStarted(PathModifier arg0, IEntity arg1,
                    int arg2) {
            }

          }, EaseLinear.getInstance()));
    }
  //retuan AnimatedSprite for all items 7,8,9(buta, zou...)
    private AnimatedSprite addAnimatedSpriteItomaki(AnimatedSprite ani ,final Scene scene, final int x, final int y, final TiledTextureRegion region, final int index){
            ani = new AnimatedSprite(x,y,region, this.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                            switch (randomeAction) {
                            case 0:
                                if(index ==0 && butaActionUp){
                                    A2_PYOI.play();
                                    touchAnimatedSpriteItomaki(butaAsObject);
                                    butaActionUp = false;
                                }
                                break;
                            case 1:
                                if(index ==1 && zouActionUp){
                                    A2_PYOI.play();
                                    touchAnimatedSpriteItomaki( zouAsObject);
                                    zouActionUp = false;
                                }
                                break;
                            case 2:
                                if(index ==2 && girlActionUp){
                                    A2_6_AHAHA.play();
                                    touchAnimatedSpriteItomaki(girlAsObject);
                                    girlActionUp = false;
                                }
                                break;
                            case 3:
                                if(index ==3 && kobitoActionUp){
                                    A2_7_EHE.play();
                                    touchAnimatedSpriteItomaki(kobitoAsObject);
                                    kobitoActionUp = false;
                                }
                                break;
                            case 4:
                                if(index ==4 && kameActionUp){
                                    A2_PYOI.play();
                                    touchAnimatedSpriteItomaki(kameAsObject);
                                    kameActionUp = false;
                                }
                                break;
                            case 5:
                                if(index ==5 && niwatoriActionUp){
                                    A2_9_KOKE2.play();
                                    touchAnimatedSpriteItomaki(niwatoriAsObject);
                                    niwatoriActionUp = false;
                                }
                                break;
                            case 6:
                                if(index ==6 && kaeruActionUp){
                                    A2_10_KERO.play();
                                    touchAnimatedSpriteItomaki(kaeruAsObject);
                                    kaeruActionUp = false;
                                }
                                break;
                            case 7:
                                if(index ==7 && umaActionUp){
                                    A2_PYOI.play();
                                    touchAnimatedSpriteItomaki(umaAsObject);
                                    umaActionUp = false;
                                }
                                break;
                            case 8:
                                if(index ==8 && penginActionUp){
                                    A2_PYOI.play();
                                    touchAnimatedSpriteItomaki(penginAsObject);
                                    penginActionUp = false;
                                }
                                break;
                            case 9:
                                if(index ==9 && nekoActionUp){
                                    A2_13_NEKO6.play();
                                    touchAnimatedSpriteItomaki(nekoAsObject);
                                    nekoActionUp = false;
                                }
                                break;
                            }
                        break;
                }
                return true;
            }
        };
        scene.attachChild(ani);
        scene.registerTouchArea(ani);
        ani.setVisible(false);
        return ani;
    }
    //Touch Animated Sprite Object Itomaki(buta, zou, ...)
    private void touchAnimatedSpriteItomaki(final AnimatedSprite ani){
        touchContainObject = false;
        ani.setCurrentTileIndex(3);
        ani.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                    ani.setCurrentTileIndex(2);
                    if(!butaActionUp && randomeAction == 0){
                        touchContainObject = true;
                        butaActionUp = true;
                    }
                    if(!zouActionUp && randomeAction == 1){
                        touchContainObject = true;
                        zouActionUp = true;
                    }
                    if(!girlActionUp && randomeAction == 2){
                        touchContainObject = true;
                        girlActionUp = true;
                    }
                    if(!kobitoActionUp && randomeAction == 3){
                        touchContainObject = true;
                        kobitoActionUp =  true;
                    }
                    if(!kameActionUp && randomeAction == 4){
                        touchContainObject = true;
                        kameActionUp = true;
                    }
                    if(!niwatoriActionUp && randomeAction == 5){
                        touchContainObject = true;
                        niwatoriActionUp = true;
                    }
                    if(!kaeruActionUp && randomeAction == 6){
                        touchContainObject = true;
                        kaeruActionUp = true;
                    }
                    if(!umaActionUp && randomeAction == 7){
                        touchContainObject = true;
                        umaActionUp = true;
                    }
                    if(!penginActionUp && randomeAction == 8){
                        touchContainObject = true;
                        penginActionUp = true;
                    }
                    if(!nekoActionUp && randomeAction == 9){
                        touchContainObject = true;
                        nekoActionUp = true;
                    }
            }

            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                
            }
        }));
    }
  //return AnimatedSprite for all Shoes
    private AnimatedSprite addSpriteShoes(AnimatedSprite ani ,final Scene scene, final int x, final int y, final TiledTextureRegion region, final int touch){
            ani = new AnimatedSprite(x,y,region, this.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                int pX = (int)pSceneTouchEvent.getX();
                int pY = (int)pSceneTouchEvent.getY();
                if(this.getCurrentTileIndex() == 1){
                    return false;
                }
                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        if(touchContainObject){
                                //Set touchShoes to Front end
                                touchShoes = true;
                                A2_KUTU_POMI.play();
                                zIndex = Vol3Itoumaki.this.mScene.getChild(mScene.getLastChild().getTag()).getZIndex();
                                zIndex ++;
                                this.setZIndex(zIndex++);
                                Vol3Itoumaki.this.mScene.sortChildren();
                        }
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(touchContainObject) {
                            this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if(touchContainObject) {
                            touchShoes = false;
                            //Buta Object
                            if(this.collidesWith(butaAsObject) && checkContains(butaAsObject, 56, 63, 229, 137, pX, pY)){
                                if(randomeAction == 0){
                                    if(touch ==0){
                                      //Xu ly truong hop giay dung
                                        A2_4_BUJOBUHI.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        butaActionUp = false;
                                        touchContainObject = false;
                                        butaAsObject.setCurrentTileIndex(5);
                                        butaAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_4_7_KATAI.play();
                                                butaChangeSourceAfter();
                                                registerAfterPathAniItomaki(butaAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                        //Xu ly truong hop sai giay
                                        A2_4_BUHI.play();
                                        butaActionUp = false;
                                        touchContainObject = false;
                                        butaAsObject.setCurrentTileIndex(4);
                                        delaySound(butaAsObject, A2_FAFAFAN, 0.5f);
                                        butaAsObject.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                butaAsObject.setCurrentTileIndex(2);
                                                butaActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Zou Object
                            if(this.collidesWith(zouAsObject) && checkContains(zouAsObject, 59, 95, 266, 181, pX, pY)){
                                if(randomeAction == 1){
                                    if(touch ==1){
                                      //Xu ly truong hop giay dung
                                        A2_5_PAON.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        zouActionUp = false;
                                        touchContainObject = false;
                                        zouAsObject.setCurrentTileIndex(5);
                                        zouAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_5_ZOU2.play();
                                                zouChangeSourceAfter();
                                                registerAfterPathAniItomaki(zouAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_5_ZANNENPAON.play();
                                        zouActionUp = false;
                                        touchContainObject = false;
                                        zouAsObject.setCurrentTileIndex(4);
                                        delaySound(zouAsObject, A2_FAFAFAN, 1.2f);
                                        zouAsObject.registerEntityModifier(new DelayModifier(3.2f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                zouAsObject.setCurrentTileIndex(2);
                                                zouActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Girld Object
                            if(this.collidesWith(girlAsObject) && checkContains(girlAsObject, 59, 28, 165, 222, pX, pY)){
                                if(randomeAction == 2){
                                    if(touch ==2){
                                      //Xu ly truong hop giay dung
                                        A2_6_7_WAI1.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        girlActionUp = false;
                                        touchContainObject = false;
                                        girlAsObject.setCurrentTileIndex(5);
                                        girlAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_6_12_GIRL2.play();
                                                girlChangeSourceAfter();
                                                registerAfterPathAniItomaki(girlAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_6_TIGAU1.play();
                                        girlActionUp = false;
                                        touchContainObject = false;
                                        girlAsObject.setCurrentTileIndex(4);
                                        delaySound(girlAsObject, A2_FAFAFAN, 1.0f);
                                        girlAsObject.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                girlAsObject.setCurrentTileIndex(2);
                                                girlActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //KobitoObject
                            if(this.collidesWith(kobitoAsObject)){
                                if(randomeAction == 3){
                                    if(touch ==3){
                                      //Xu ly truong hop giay dung
                                        A2_6_7_WAI1.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        kobitoActionUp = false;
                                        touchContainObject = false;
                                        kobitoAsObject.setCurrentTileIndex(5);
                                        kobitoAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_4_7_KATAI.play();
                                                kobitoChangeSourceAfter();
                                                registerAfterPathAniItomaki(kobitoAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_7_TIGAU2.play();
                                        kobitoActionUp = false;
                                        touchContainObject = false;
                                        kobitoAsObject.setCurrentTileIndex(4);
                                        delaySound(kobitoAsObject, A2_FAFAFAN, 0.7f);
                                        kobitoAsObject.registerEntityModifier(new DelayModifier(2.7f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                kobitoAsObject.setCurrentTileIndex(2);
                                                kobitoActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //KameObject
                            if(this.collidesWith(kameAsObject)  && checkContains(kameAsObject, 30, 70, 268, 160, pX, pY)){
                                if(randomeAction == 4){
                                    if(touch ==4){
                                      //Xu ly truong hop giay dung
                                        A2_KIRA8.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        kameActionUp = false;
                                        touchContainObject = false;
                                        kameAsObject.setCurrentTileIndex(6);
                                        kameAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_8_KAME1.play();
                                                kameChangeSourceAfter();
                                                registerAfterPathAniItomaki(kameAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_8_SUPON.play();
                                        kameActionUp = false;
                                        touchContainObject = false;
                                        delaySound(kameAsObject, A2_FAFAFAN, 0.5f);
                                        if(!kameAsObject.isAnimationRunning()){
                                            long pDuration[] = new long[] {750, 750, 750};
                                            int pFram[] = new int[] { 4, 5, 5};
                                            kameAsObject.animate(pDuration, pFram, 0, new IAnimationListener() {
                                                @Override
                                                public void onAnimationFinished(
                                                        AnimatedSprite arg0) {
                                                    kameAsObject.setCurrentTileIndex(2);
                                                    kameActionUp = true;
                                                    touchContainObject = true;
                                                }

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
                                            });
                                        }
                                    }
                                }
                            }
                            //Niwatori Object
                            if(this.collidesWith(niwatoriAsObject) && checkContains(niwatoriAsObject, 60, 21, 196, 196, pX, pY)){
                                if(randomeAction == 5){
                                    if(touch ==5){
                                      //Xu ly truong hop giay dung
                                        A2_9_NIWATORI3.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        niwatoriActionUp = false;
                                        touchContainObject = false;
                                        niwatoriAsObject.setCurrentTileIndex(5);
                                        niwatoriAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_7_13_TOKO.play();
                                                niwatoriChangeSourceAfter();
                                                registerAfterPathAniItomaki(niwatoriAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_9_KOKE_2.play();
                                        niwatoriActionUp = false;
                                        touchContainObject = false;
                                        niwatoriAsObject.setCurrentTileIndex(4);
                                        delaySound(niwatoriAsObject, A2_FAFAFAN, 0.5f);
                                        niwatoriAsObject.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                niwatoriAsObject.setCurrentTileIndex(2);
                                                niwatoriActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Kaeru Object
                            if(this.collidesWith(kaeruAsObject) && checkContains(kaeruAsObject, 45, 68, 180, 165, pX, pY)){
                                if(randomeAction == 6){
                                    if(touch ==6){
                                      //Xu ly truong hop giay dung
                                        A2_KIRA8.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        kaeruActionUp = false;
                                        touchContainObject = false;
                                        kaeruAsObject.setCurrentTileIndex(5);
                                        kaeruAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_10_POYON.play();
                                                kaeruChangeSourceAfter();
                                                registerAfterPathAniItomaki(kaeruAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_10_BYON.play();
                                        kaeruActionUp = false;
                                        touchContainObject = false;
                                        kaeruAsObject.setCurrentTileIndex(4);
                                        delaySound(kaeruAsObject, A2_FAFAFAN, 0.7f);
                                        kaeruAsObject.registerEntityModifier(new DelayModifier(2.7f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                kaeruAsObject.setCurrentTileIndex(2);
                                                kaeruActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Uma Object
                            if(this.collidesWith(umaAsObject) && checkContains(umaAsObject, 45, 27, 268, 140, pX, pY)){
                                if(randomeAction == 7){
                                    if(touch ==7){
                                      //Xu ly truong hop giay dung
                                        A2_KIRA8.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        umaActionUp = false;
                                        touchContainObject = false;
                                        umaAsObject.setCurrentTileIndex(5);
                                        umaAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_11_UMA2.play();
                                                umaChangeSourceAfter();
                                                registerAfterPathAniItomaki(umaAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_11_INANAKI.play();
                                        umaActionUp = false;
                                        touchContainObject = false;
                                        umaAsObject.setCurrentTileIndex(4);
                                        delaySound(umaAsObject, A2_FAFAFAN, 1.5f);
                                        umaAsObject.registerEntityModifier(new DelayModifier(3.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                umaAsObject.setCurrentTileIndex(2);
                                                umaActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Pengin Object
                            if(this.collidesWith(penginAsObject) && checkContains(penginAsObject, 60, 45, 139, 226, pX, pY)){
                                if(randomeAction == 8){
                                    if(touch ==8){
                                      //Xu ly truong hop giay dung
                                        A2_KIRA8.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        penginActionUp = false;
                                        touchContainObject = false;
                                        penginAsObject.setCurrentTileIndex(5);
                                        penginAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_12_SYU2.play();
                                                penginChangeSourceAfter();
                                                registerAfterPathAniItomaki(penginAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_12_POKA.play();
                                        penginActionUp = false;
                                        touchContainObject = false;
                                        penginAsObject.setCurrentTileIndex(4);
                                        delaySound(penginAsObject, A2_FAFAFAN, 1.0f);
                                        penginAsObject.registerEntityModifier(new DelayModifier(3.0f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                penginAsObject.setCurrentTileIndex(2);
                                                penginActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            //Neko Object
                            if(this.collidesWith(nekoAsObject) && checkContains(nekoAsObject, 53, 37, 195, 124, pX, pY)){
                                if(randomeAction == 9){
                                    if(touch ==9){
                                      //Xu ly truong hop giay dung
                                        A2_KIRA8.play();
                                        this.setCurrentTileIndex(1);
                                        countdown -- ;
                                        nekoActionUp = false;
                                        touchContainObject = false;
                                        nekoAsObject.setCurrentTileIndex(5);
                                        nekoAsObject.registerEntityModifier(new DelayModifier(1.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                A2_7_13_TOKO.play();
                                                nekoChangeSourceAfter();
                                                registerAfterPathAniItomaki(nekoAsObject);
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }else{
                                      //Xu ly truong hop sai giay
                                        A2_13_NEKO5.play();
                                        nekoActionUp = false;
                                        touchContainObject = false;
                                        nekoAsObject.setCurrentTileIndex(4);
                                        delaySound(nekoAsObject, A2_FAFAFAN, 0.5f);
                                        nekoAsObject.registerEntityModifier(new DelayModifier(2.5f, new IEntityModifierListener() {
                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                                nekoAsObject.setCurrentTileIndex(2);
                                                nekoActionUp = true;
                                                touchContainObject = true;
                                            }

                                            @Override
                                            public void onModifierStarted(
                                                    IModifier<IEntity> arg0,
                                                    IEntity arg1) {
                                                // TODO Auto-generated method stub
                                                
                                            }
                                        }));
                                    }
                                }
                            }
                            switch (touch) {
                            case 0:
                                this.setPosition(randomeShoesTemp[0][0],randomeShoesTemp[0][1]);
                                break;
                            case 1:
                                this.setPosition(randomeShoesTemp[1][0],randomeShoesTemp[1][1]);
                                break;
                            case 2:
                                this.setPosition(randomeShoesTemp[2][0],randomeShoesTemp[2][1]);
                                break;
                            case 3:
                                this.setPosition(randomeShoesTemp[3][0],randomeShoesTemp[3][1]);
                                break;
                            case 4:
                                this.setPosition(randomeShoesTemp[4][0],randomeShoesTemp[4][1]);
                                break;
                            case 5:
                                this.setPosition(randomeShoesTemp[5][0],randomeShoesTemp[5][1]);
                                break;
                            case 6:
                                this.setPosition(randomeShoesTemp[6][0],randomeShoesTemp[6][1]);
                                break;
                            case 7:
                                this.setPosition(randomeShoesTemp[7][0],randomeShoesTemp[7][1]);
                                break;
                            case 8:
                                this.setPosition(randomeShoesTemp[8][0],randomeShoesTemp[8][1]);
                                break;
                            case 9:
                                this.setPosition(randomeShoesTemp[9][0],randomeShoesTemp[9][1]);
                                break;
                            default:
                                break;
                            }
                            //Set basanCiclesp
                            zIndex ++;
                            basanCicleSp.setZIndex(zIndex++);
                            basanAsObject.setZIndex(zIndex++);
                            Vol3Itoumaki.this.mScene.sortChildren();
                            //Set button Gimic to Front end
                            setGimmicBringToFront();
                        }
                        break;
                }
                return true;
            }
        };
        scene.attachChild(ani);
        scene.registerTouchArea(ani);
        return ani;
    }
    private void delaySound(AnimatedSprite ani, final Sound sound, float delay){
        ani.registerEntityModifier(new DelayModifier(delay, new IEntityModifierListener() {
            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                sound.play();
            }

            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                
            }
        }));
    }
    //Call random Items Object (buta, zou, ...)
    private void randomItemsObject(){
        randomeAction = ran.getNextIntNoRepeat(true);
        switch(randomeAction){
        case 0:
                //AnimatedSprite Buta
                A2_4_BUTA2.play();
                butaChangeSourceBefore();
                registerFirstPathAniItomaki(butaAsObject, 0);
                butaAsObject.setVisible(true);
                butaAsShoesObject.setVisible(true);
            break;
        case 1:
                //AnimatedSprite Zou
                A2_5_ZOU1.play();
                zouChangeSourceBefore();
                registerFirstPathAniItomaki(zouAsObject, 1);
                zouAsObject.setVisible(true);
                zouAsShoesObject.setVisible(true);
            break;
        case 2: 
                //AnimatedSprite Girl
                A2_6_GIRL1.play();
                girlChangeSourceBefore();
                registerFirstPathAniItomaki(girlAsObject, 2);
                girlAsObject.setVisible(true);
                girlAsShoesObject.setVisible(true);
            break;
        case 3:
                //AnimatedSprite Kobito
                A2_7_13_TOKO.play();
                kobitoChangeSourceBefore();
                registerFirstPathAniItomaki(kobitoAsObject, 3);
                kobitoAsObject.setVisible(true);
                kobitoAsShoesObject.setVisible(true);
            break;
        case 4:
                //AnimatedSprite Kame
                A2_8_KAME2.play();
                kameChangeSourceBefore();
                registerFirstPathAniItomaki(kameAsObject, 4);
                kameAsObject.setVisible(true);
                kameAsShoesObject.setVisible(true);
            break;
        case 5:
                //AnimatedSprite Niwatori
                A2_9_NIWATRI1_2.play();
                niwatoriChangeSourceBefore();
                registerFirstPathAniItomaki(niwatoriAsObject, 5);
                niwatoriAsObject.setVisible(true);
                niwatoriAsShoesObject.setVisible(true);
            break;
        case 6:
                //AnimatedSprite Kaeru
                A2_10_POYON.play();
                kaeruChangeSourceBefore();
                registerFirstPathAniItomaki(kaeruAsObject, 6);
                kaeruAsObject.setVisible(true);
                kaeruAsShoesObject.setVisible(true);
            break;
        case 7:
                //AnimatedSprite Uma
                A2_11_UMA1.play();
                umaChangeSourceBefore();
                registerFirstPathAniItomaki(umaAsObject, 7);
                umaAsObject.setVisible(true);
                umaAsShoesObject.setVisible(true);
            break;
        case 8:
                //AnimatedSprite Pengin
                A2_12_GIRL1.play();
                penginChangeSourceBefore();
                registerFirstPathAniItomaki(penginAsObject, 8);
                penginAsObject.setVisible(true);
                penginAsShoesObject.setVisible(true);
            break;
        case 9:
                //AnimatedSprite Neko
                A2_7_13_TOKO.play();
                nekoChangeSourceBefore();
                registerFirstPathAniItomaki(nekoAsObject, 9);
                nekoAsObject.setVisible(true);
                nekoAsShoesObject.setVisible(true);
            break;
        }
    }
    //Function change image of contain object
    private void changeContainObject(){
        boxAsContainObject.registerEntityModifier(new DelayModifier(5.0f, new IEntityModifierListener() {
            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                if(!boxAsContainObject.isAnimationRunning()){
                    long pDuration[] = new long[] {1500,1500};
                    int pFram[] = new int[] { 1,0};
                    boxAsContainObject.animate(pDuration, pFram, -1);
                }
            }

            @Override
            public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
                // TODO Auto-generated method stub
                
            }
        }));
    }
    //Function Rotate Basan cicle
    private void rotateBasanCicle(){
        ParallelEntityModifier effect = new ParallelEntityModifier(
                new LoopEntityModifier(new RotationModifier(1.0f,0, 360, EaseLinear.getInstance())));
        basanCicleSp.registerEntityModifier(effect);
    }
    //Function change Basan Object
    private void changeBasanObject(){
        A2_14_ITOGURUMA.play();
        rotateBasanCicle();
        if(!basanAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {350, 350, 350};
            int pFram[] = new int[] { 2, 1, 0};
            basanAsObject.animate(pDuration, pFram, 0, new IAnimationListener() {

                @Override
                public void onAnimationFinished(AnimatedSprite arg0) {
                    if(basanCicleSp!=null){
                        basanCicleSp.clearEntityModifiers();
                    }
                    basanAsObject.setCurrentTileIndex(0);
                    basanGrabbed = false;
                    isTouchGimic = false;
                    setTouchGimmic3(true);
                }

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

            });
        }
    }
    //Function change image for all object 7,8, 9 items
    //Change Resource Before and After of Buta
    private void butaChangeSourceBefore() {
        if(!butaAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            butaAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void butaChangeSourceAfter() {
        if(!butaAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            butaAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Zou
    private void zouChangeSourceBefore() {
        if(!zouAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            zouAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void zouChangeSourceAfter() {
        if(!zouAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            zouAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Girl
    private void girlChangeSourceBefore() {
        if(!girlAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            girlAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void girlChangeSourceAfter() {
        if(!girlAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            girlAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Kobito
    private void kobitoChangeSourceBefore() {
        if(!kobitoAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            kobitoAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void kobitoChangeSourceAfter() {
        if(!kobitoAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            kobitoAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Kame
    private void kameChangeSourceBefore() {
        if(!kameAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            kameAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void kameChangeSourceAfter() {
        if(!kameAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 7, 8};
            kameAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Niwatori
    private void niwatoriChangeSourceBefore() {
        if(!niwatoriAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            niwatoriAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void niwatoriChangeSourceAfter() {
        if(!niwatoriAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            niwatoriAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Kaeru
    private void kaeruChangeSourceBefore() {
        if(!kaeruAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            kaeruAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void kaeruChangeSourceAfter() {
        if(!kaeruAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            kaeruAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Uma
    private void umaChangeSourceBefore() {
        if(!umaAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            umaAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void umaChangeSourceAfter() {
        if(!umaAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            umaAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Pengin
    private void penginChangeSourceBefore() {
        if(!penginAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            penginAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void penginChangeSourceAfter() {
        if(!penginAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300};
            int pFram[] = new int[] { 6};
            penginAsObject.animate(pDuration, pFram, -1);
        }
    }
  //Change Resource Before and After of Neko
    private void nekoChangeSourceBefore() {
        if(!nekoAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 0, 1};
            nekoAsObject.animate(pDuration, pFram, -1);
        }
    }
    private void nekoChangeSourceAfter() {
        if(!nekoAsObject.isAnimationRunning()){
            long pDuration[] = new long[] {300, 300};
            int pFram[] = new int[] { 6, 7};
            nekoAsObject.animate(pDuration, pFram, -1);
        }
    }
    @Override
    public void combineGimmic3WithAction() {
        if (!isTouchGimic) {
            basanGrabbed = true;
            isTouchGimic = true;
            setTouchGimmic3(false);
            changeBasanObject();
        }
    }
}