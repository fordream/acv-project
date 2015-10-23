package jp.co.xing.utaehon03.basegame;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

// TODO: Auto-generated Javadoc
/**
 * The Class SpritePool.
 *
 * @author Pham Quy Hai
 * @date 10/02/2012
 * Example:
 * private SpritePool _ppritePool;
 * private TextureRegion  _textureRegion;
 * private Sprite sprite;
 * .   .   .   .    .   .   .    .   .   .    .   .   .    .   .   .    .   .   .
 * .   .   .    .   .   .    .   .   .    .   .   .    .   .   .    .   .   .
 * 
 * _ppritePool = new  SpritePool(_textureRegion);
 * 
 * Khi can doi duong sprite moi
 * 
 * sprite = _ppritePool.onHandleObtainItem();
 * 
 * Khi xoa doi tuong  sprite
 * 
 * _ppritePool.onHandleRecycleItem(sprite);
 */


public class SpritePool extends GenericPool<Sprite> {
		
		/** The m texture region. */
		private ITextureRegion mTextureRegion;
		private VertexBufferObjectManager mVertexBufferObjectManager;
		/**
		 * Instantiates a new sprite pool.
		 *
		 * @param pTextureRegion the texture region
		 */
		public SpritePool(ITextureRegion pTextureRegion, VertexBufferObjectManager pVertext) {
			
			  if (pTextureRegion == null) {
			   throw new IllegalArgumentException("The texture region must not be NULL");
			  }
			  this.mTextureRegion = pTextureRegion;
			  this.mVertexBufferObjectManager = pVertext;
		}
		
		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.pool.GenericPool#onAllocatePoolItem()
		 */
		@Override
		protected Sprite onAllocatePoolItem() {
			return new Sprite(0, 0, mTextureRegion, mVertexBufferObjectManager);
		}
		
		
		// Xoa doi tuong
		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.pool.GenericPool#onHandleRecycleItem(java.lang.Object)
		 */
		@Override
		public void onHandleRecycleItem(final Sprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.setVisible(false);
			pItem.detachSelf();
			pItem.reset();
		}
		
		// Thay doi TextureRegion moi --  chua test ^^
		/**
		 * Se texture regiont.
		 *
		 * @param nTextureRegion the n texture region
		 */
		protected void setTextureRegion(final TextureRegion nTextureRegion) {
			this.mTextureRegion = nTextureRegion;
		}
		
}
