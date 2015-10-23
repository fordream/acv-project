package jp.co.xing.utaehon03.songs;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class Vol3OsyougatsuScene extends Scene {
  
  public abstract void loadResource();
  protected abstract void drawScene();
  public abstract void preView();
  public abstract void onResumeGame();
  public abstract void onPauseGame();

  public TiledTextureRegion getTiledTextureFromPacker(TexturePack pTexturePack,
      int... pSpriteSheets) {
    final TexturePackTextureRegionLibrary mTexturePackTextureRegionLibrary =
        pTexturePack.getTexturePackTextureRegionLibrary();
    final int sheetcount = pSpriteSheets.length;
    TexturePackerTextureRegion[] objTextureRegion = new TexturePackerTextureRegion[sheetcount];
    for (int i = 0; i < sheetcount; i++) {
      objTextureRegion[i] = mTexturePackTextureRegionLibrary.get(pSpriteSheets[i]);
    }
    TiledTextureRegion mTiledTextureRegion =
        new TiledTextureRegion(pTexturePack.getTexture(), objTextureRegion);
    return mTiledTextureRegion;
  }


  public boolean checkContains(RectangularShape myShape, int x1, int y1, int x2, int y2, int pX,
      int pY) {
    boolean c = false;
    if (myShape != null) {
      int myShapeX = (int) myShape.getX();
      int myShapeY = (int) myShape.getY();

      int polyX[] = new int[] {myShapeX + x1, myShapeX + x1, myShapeX + x2, myShapeX + x2};
      int polyY[] = new int[] {myShapeY + y1, myShapeY + y2, myShapeY + y2, myShapeY + y1};

      int i, j = 0;
      for (i = 0, j = 3; i < 4; j = i++) {
        if (((polyY[i] > pY) != (polyY[j] > pY))
            && (pX < (polyX[j] - polyX[i]) * (pY - polyY[i]) / (polyY[j] - polyY[i]) + polyX[i]))
          c = !c;
      }
    }
    return c;
  }

  public boolean checkContainsPolygon(RectangularShape myShape, float[][] arrPointer,
      int numberPointer, float pX, float pY) {

    float myShapeX = myShape.getX();
    float myShapeY = myShape.getY();
    float arrPoiterTemp[][] = new float[2][numberPointer + 1];

    for (int j = 0; j <= 1; j++) {
      for (int i = 0; i < numberPointer; i++) {
        if (j == 0) {
          arrPoiterTemp[j][i] = arrPointer[j][i] + myShapeX;
        }
        if (j == 1) {
          arrPoiterTemp[j][i] = arrPointer[j][i] + myShapeY;
        }
      }
    }

    boolean c = false;
    int i, j = 0;
    for (i = 0, j = numberPointer - 1; i < numberPointer; j = i++) {
      if (((arrPoiterTemp[1][i] > pY) != (arrPoiterTemp[1][j] > pY))
          && (pX < (arrPoiterTemp[0][j] - arrPoiterTemp[0][i]) * (pY - arrPoiterTemp[1][i])
              / (arrPoiterTemp[1][j] - arrPoiterTemp[1][i]) + arrPoiterTemp[0][i])) c = !c;
    }

    return c;
  }

}
