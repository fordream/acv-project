package jp.co.xing.utaehon03.basegame;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

/**
 * The Class AnimatedSpritePool.
 *
 * @author Pham Quy Hai
 * @date 13/02/2012
 * 
 * Example:
 * private AnimatedSpritePool _AnispritePool;
 * private TiledTextureRegion  _tiledTextureRegion;
 * private AnimatedSprite _animatedSprite;
 * .   .   .   .    .   .   .    .   .   .    .   .   .    .   .   .    .   .   .
 * .   .   .    .   .   .    .   .   .    .   .   .    .   .   .    .   .   .
 * 
 * _AnispritePool = new  AnimatedSpritePool(mTiledTextureRegion);
 * 
 * Khi can doi duong  AnimatedSprite moi
 * 
 * _animatedSprite = _AnispritePool.onHandleObtainItem();
 * 
 * Khi xoa doi tuong  _animatedSprite
 * 
 * _AnispritePool.onHandleRecycleItem(_animatedSprite);
 */


public class AnimatedSpritePool extends GenericPool<AnimatedSprite> {
		
		/** The m tiled texture region. */
		private TiledTextureRegion mTiledTextureRegion;
		private VertexBufferObjectManager mVertexBufferObjectManager;

		/**
		 * Instantiates a new animated sprite pool.
		 *
		 * @param pTiledTextureRegion the tiled texture region
		 */
		public AnimatedSpritePool(TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertext) {
			
			  if (pTiledTextureRegion == null) {
			   throw new IllegalArgumentException("The texture region must not be NULL");
			  }
			  this.mTiledTextureRegion = pTiledTextureRegion;
			  this.mVertexBufferObjectManager = pVertext;
		}
		

		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.pool.GenericPool#onAllocatePoolItem()
		 */
		@Override
		protected AnimatedSprite onAllocatePoolItem() {
			return new AnimatedSprite(0, 0, mTiledTextureRegion, mVertexBufferObjectManager);
		}
		
		
		// Xoa doi tuong
		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.pool.GenericPool#onHandleRecycleItem(java.lang.Object)
		 */
		@Override
		protected void onHandleRecycleItem(final AnimatedSprite pItem) {
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.setVisible(false);
			pItem.detachSelf();
			pItem.reset();
		}
		

		/**
		 * Sets the tiled texture region.
		 *
		 * @param pTiledTextureRegion the new tiled texture region
		 */
		protected void setTiledTextureRegion(final TiledTextureRegion pTiledTextureRegion) {
			this.mTiledTextureRegion = pTiledTextureRegion;
		}
		
}
