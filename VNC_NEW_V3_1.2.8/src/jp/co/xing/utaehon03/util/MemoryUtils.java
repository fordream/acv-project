package jp.co.xing.utaehon03.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * This class for process with SDCard.
 * */
public class MemoryUtils {

	private String pathFileExternalMemory = "";
	private String pathCacheExternalMemory = "";
	private String pathFileInternalMemory = "";

	/** 1MB block. */
	private static final int MB = 1048576;

	public MemoryUtils(Context context) {
		super();

		// if (checkSDAvaiable(Environment.getExternalStorageDirectory()
		// + "/Android/data/" + context.getPackageName() + "/cache/")) {
		// pathFileExternalMemory = Environment.getExternalStorageDirectory()
		// + "/Android/data/" + context.getPackageName() + "/files/";
		// pathCacheExternalMemory = Environment.getExternalStorageDirectory()
		// + "/Android/data/" + context.getPackageName() + "/cache/";
		// } else {
		// pathFileExternalMemory = "/mnt/emmc/Android/data/"
		// + context.getPackageName() + "/files/";
		// pathCacheExternalMemory = "/mnt/emmc/Android/data/"
		// + context.getPackageName() + "/cache/";
		// }
		pathFileExternalMemory = Environment.getExternalStorageDirectory()
				+ "/Android/data/" + context.getPackageName() + "/files/";
		pathCacheExternalMemory = Environment.getExternalStorageDirectory()
				+ "/Android/data/" + context.getPackageName() + "/cache/";
		pathFileInternalMemory = context.getFilesDir().getPath() + "/";
	}

	public String getPathFileExternalMemory() {
		return pathFileExternalMemory;
	}

	public String getPathFileExternalMemory(String packageName) {
		if (packageName.endsWith(".ini")) {
			return pathFileExternalMemory + packageName;
		}

		return pathFileExternalMemory + packageName + ".ini";
	}

	public String getPathCacheExternalMemory() {
		return pathCacheExternalMemory;
	}

	public String getPathFileInternalMemory() {
		return pathFileInternalMemory;
	}

	public boolean checkSDAvaiable(String path) {
		boolean isSDCardExisted = false;
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}

		File file = new File(path, "test" + System.currentTimeMillis() + ".txt");

		try {
			isSDCardExisted = file.createNewFile();
		} catch (IOException e1) {
			isSDCardExisted = false;
		}
		file.delete();
		return isSDCardExisted;
	}

	public double checkSDFreeMB() {

		if (!checkSDAvaiable(getPathCacheExternalMemory())) {
			return 0;
		} else {
			StatFs stat = new StatFs(getPathCacheExternalMemory());
			double sdAvailSize = (double) stat.getAvailableBlocks()
					* (double) stat.getBlockSize();

			return sdAvailSize / MB;
		}
	}

	public long avaiableInternalStoreMemorySize() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long) stat.getFreeBlocks()
				* (long) stat.getBlockSize();
		return (bytesAvailable / 1048576);
	}

}
