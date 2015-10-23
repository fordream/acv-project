package jp.co.xing.utaehon03.basegame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.StreamUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.res.AssetManager;

public class TexturePackLoaderFromSource extends TexturePackLoader {

	public static String pathSourceSD = "";
	public static boolean isAsset = true;

	private final TextureManager mTextureManager;
	private AssetManager mAssetManager = null;	

	@SuppressWarnings("unused")
	private final String pathSource;

	public TexturePackLoaderFromSource(TextureManager pTextureManager,
			AssetManager pAssetManager, String pathSource) {
		super(pTextureManager, pathSource);
		this.mTextureManager = pTextureManager;
		this.mAssetManager = pAssetManager;
		this.pathSource = pathSource;

	}

	public TexturePackLoaderFromSource(TextureManager pTextureManager,
			AssetManager pAssetManager) {
		super(pTextureManager, BitmapTextureAtlasTextureRegionFactory
				.getAssetBasePath());
		this.mTextureManager = pTextureManager;
		this.mAssetManager = pAssetManager;
		this.pathSource = BitmapTextureAtlasTextureRegionFactory
				.getAssetBasePath();
	}

	public TexturePack load(String fileName){
		TexturePack rTexturePack = null;
		if (isAsset) {
			try {
				rTexturePack = this.loadFromAsset(this.mAssetManager, fileName);
			} catch (TexturePackParseException e) {
				e.printStackTrace();
			}
		} else {
			final File file = new File(pathSourceSD + fileName);
			try {
				rTexturePack = this.loadFromSource(new BufferedInputStream(
						new FileInputStream(file)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rTexturePack;
	}

	public TexturePack loadFromSource(final InputStream pInputStream)
			throws TexturePackParseException, FileNotFoundException {
		try {
			final SAXParserFactory spf = SAXParserFactory.newInstance();
			final SAXParser sp = spf.newSAXParser();

			final XMLReader xr = sp.getXMLReader();
			final SourceTexturePackParser texturePackerParser = new SourceTexturePackParser(
					this.mTextureManager, pathSourceSD);
			xr.setContentHandler(texturePackerParser);

			xr.parse(new InputSource(new BufferedInputStream(pInputStream)));

			return texturePackerParser.getTexturePack();
		} catch (final SAXException e) {
			throw new FileNotFoundException();
		} catch (final ParserConfigurationException pe) {
			/* Doesn't happen. */
			return null;
		} catch (final IOException e) {
			throw new FileNotFoundException();
		} finally {
			StreamUtils.close(pInputStream);
		}
	}

}
