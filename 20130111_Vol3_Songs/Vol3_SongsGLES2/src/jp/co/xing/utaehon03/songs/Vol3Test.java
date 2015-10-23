package jp.co.xing.utaehon03.songs;

import java.util.Random;

import jp.co.xing.utaehon03.basegame.BaseGameActivity;
import jp.co.xing.utaehon03.basegame.TexturePackLoaderFromSource;
import jp.co.xing.utaehon03.resources.Vol3TestResource;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class Vol3Test extends BaseGameActivity implements IOnSceneTouchListener{
	private static final String TAG = "LOG_VOL3TEST";
	
	private TexturePackLoaderFromSource mTexturePackLoaderFromSource;
	private TexturePack mTexturePack;
	
	private Sprite mTableSprite;
	private Sprite mRegiSprite;
	private Rectangle  mNumberBox;
	private Entity mColum1Entity;
	private SpriteNumber mColum1Sprite[] = new SpriteNumber[10];
	
	private SequenceEntityModifier mMove;
	
	private float  numberHeight ;
	
	@Override
	public void onCreateResources() {
		Log.d(TAG, "----------onCreateResources------------------------------");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("test/");
		mTexturePackLoaderFromSource = new TexturePackLoaderFromSource(getTextureManager(), getAssets(), "test/");
		Log.d(TAG, "onCreateResources------------------------------");
		super.onCreateResources();
	}
	
	@Override
	protected void loadKaraokeResources() {
		Log.d(TAG, "--------------loadKaraokeResources------------------------------");
		mTexturePack = mTexturePackLoaderFromSource.load("test.xml");
		mTexturePack.loadTexture();	
		Log.d(TAG, "loadKaraokeResources------------------------------");
	}

	@Override
	protected void loadKaraokeSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loadKaraokeScene() {
		mScene = new Scene();
		mScene.setOnSceneTouchListener(this);
		mNumberBox = new Rectangle(737, 461, 139, 33, getVertexBufferObjectManager());
		mNumberBox.setColor(255, 255, 255);
		mScene.attachChild(mNumberBox);
		mColum1Entity = new Entity(0, 0);
		mScene.attachChild(mColum1Entity);
		mTableSprite = new Sprite(0, 150, mTexturePack.getTexturePackTextureRegionLibrary().get(
				Vol3TestResource.A2_8_IPHONE_TABLE_ID), getVertexBufferObjectManager());
		mScene.attachChild(mTableSprite);
		mRegiSprite = new Sprite(670, 449, mTexturePack.getTexturePackTextureRegionLibrary().get(
				Vol3TestResource.A2_2_5_IPHONE_REGI_ID), getVertexBufferObjectManager());
		mScene.attachChild(mRegiSprite);
		ITextureRegion textureNumber = mTexturePack.getTexturePackTextureRegionLibrary().get(Vol3TestResource.A2_2_1_0_IPHONE_NO_ID);
		numberHeight  = textureNumber.getHeight();
		float x = mNumberBox.getX();
		float y = mNumberBox.getY() + 1;
		for(int i = 0; i < mColum1Sprite.length;i++){
			Log.i(TAG, "Y: " + y);
			mColum1Sprite[i] = new SpriteNumber(x, y, textureNumber.deepCopy(), getVertexBufferObjectManager());
			mColum1Entity.attachChild(mColum1Sprite[i]);
			y =  y + numberHeight +1;
		}
		
	}

	@Override
	public void combineGimmic3WithAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void onResumeGame() {
		Log.d(TAG, "onResumeGame------------------------------");
		super.onResumeGame();
	}
	
	class SpriteNumber extends Sprite{
		
		public SpriteNumber(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		}
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			if(this.collidesWith(mNumberBox)){
				this.setAlpha(1.0f);
			}else{
				this.setAlpha(0.0f);
			}
			super.onManagedUpdate(pSecondsElapsed);
		}
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
				if(arg1.isActionDown()){
					if(mMove != null){
						if(mMove.isFinished()){
							mColum1Entity.unregisterEntityModifier(mMove);
							int id = new Random().nextInt(10);
							float pY = id * numberHeight + id;
							float duration = id * 0.1f;
							float pointY = mColum1Entity.getY() - pY;
							mMove = null;
							mMove = new SequenceEntityModifier(
									new MoveYModifier(duration, mColum1Entity.getY(), pointY),
									new MoveYModifier(duration, pointY, mColum1Entity.getY()));
							mColum1Entity.registerEntityModifier(mMove);
						}
					}else{
						int id = new Random().nextInt(10);
						float pY = id * numberHeight + id;
						float duration = id * 0.1f;
						mMove = null;
						float pointY = mColum1Entity.getY() - pY;
						mMove = new SequenceEntityModifier(
								new MoveYModifier(duration, mColum1Entity.getY(), pointY),
								new MoveYModifier(duration, pointY , mColum1Entity.getY()));
						mColum1Entity.registerEntityModifier(mMove);
					}
				
				}
		return false;
	}

}
