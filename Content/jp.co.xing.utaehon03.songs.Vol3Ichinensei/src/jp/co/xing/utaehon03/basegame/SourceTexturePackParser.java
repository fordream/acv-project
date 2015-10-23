package jp.co.xing.utaehon03.basegame;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackParser;
import org.andengine.opengl.texture.TextureManager;

public class SourceTexturePackParser extends TexturePackParser{
	
	private final String mBasePath;
	
	public SourceTexturePackParser(TextureManager pTextureManager, String mBasePath) {
		super(pTextureManager);
		this.mBasePath = mBasePath;
	}

	@Override
	protected InputStream onGetInputStream(String filename)
			throws IOException {
		// TODO Auto-generated method stub
		File file = new File(mBasePath, filename);
		return new BufferedInputStream(new FileInputStream(file));
		
		
	}

}
