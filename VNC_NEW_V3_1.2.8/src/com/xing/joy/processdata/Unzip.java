package com.xing.joy.processdata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.co.xing.utaehon03.util.LogUtils;
import jp.co.xing.utaehon03.util.MemoryUtils;
import jp.co.xing.utaehon03.util.ZDialogUtils;
import android.content.Context;
import android.os.AsyncTask;

public abstract class Unzip extends AsyncTask<FilesModel, Integer, Boolean> {
	private static int STATUS_NORMAL = 0;
	private static int STATUS_CHECK_STORE_FAIL = 1;
	private static int STATUS_CHECK_MEMORY = 2;
	private int status = STATUS_NORMAL;
	protected long total = 0;
	private Context context;

	public Unzip(Context context) {
		this.context = context;
	}

	/** BUFFER read file. */
	private static final int BUFFER = 2048;

	@Override
	protected Boolean doInBackground(FilesModel... params) {
		File mDirectory = new File(params[0].getPathDir());
		File tmpFileConfig = null;
		if (!mDirectory.exists()) {
			mDirectory.mkdirs();
		}

		for (FilesModel filesModel : params) {
			// UNZIP file.
			File file = new File(filesModel.getUrl());
			try {
				// 20130814
				MemoryUtils memoryUtils = new MemoryUtils(context);

				LogUtils.eCommon(filesModel.getPathDir() + "\n"
						+ memoryUtils.getPathFileInternalMemory());

				String pathDir = filesModel.getPathDir();
				if (pathDir.contains(memoryUtils.getPathFileInternalMemory())) {
					// Get length of file in bytes
					long fileSizeInBytes = file.length();
					// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
					long fileSizeInKB = fileSizeInBytes / 1024;
					// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
					long fileSizeInMB = fileSizeInKB / 1024;
					if (memoryUtils.avaiableInternalStoreMemorySize() < fileSizeInMB - 1) {
						status = STATUS_CHECK_MEMORY;
						throw new MemoryLowException();
					}
				}

				ZipFile zip = new ZipFile(file);

				Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

				// Process each entry.
				while (zipFileEntries.hasMoreElements()) {
					// grab a ZIP file entry.
					ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
					String currentEntry = entry.getName();
					if (currentEntry.contains(".ini")) {
						currentEntry = currentEntry.replace(".ini", ".tmp");
					}

					File destFile = new File(filesModel.getPathDir(),
							currentEntry);
					if (currentEntry.contains(".tmp")) {
						tmpFileConfig = destFile;
					}
					File destinationParent = destFile.getParentFile();

					// create the parent directory structure if needed.
					destinationParent.mkdirs();

					if (!entry.isDirectory()) {
						BufferedInputStream is = new BufferedInputStream(
								zip.getInputStream(entry));
						int currentByte;
						// establish buffer for writing file
						byte data[] = new byte[BUFFER];

						// write the current file to disk.
						FileOutputStream fos = new FileOutputStream(destFile);
						BufferedOutputStream dest = new BufferedOutputStream(
								fos, BUFFER);
						// read and write until last byte is encountered.
						while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
							total += currentByte;
							publishProgress(1000);
							dest.write(data, 0, currentByte);
						}
						dest.flush();
						dest.close();
						is.close();
					}
				}

				if (tmpFileConfig != null) {
					tmpFileConfig.renameTo(new File(tmpFileConfig
							.getAbsolutePath().replace(".tmp", ".ini")));
				}
				// Delete ZIP file.
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (!result) {
			if (STATUS_CHECK_MEMORY == status) {
				new ZDialogUtils(context)
						.onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_003);
			} else
				new ZDialogUtils(context)
						.onCreateDialog(ZDialogUtils.DIALOG_ERROR_001);
		}
	}

	private class MemoryLowException extends Exception {
		public MemoryLowException() {
			super("MemoryLowException");
		}
	}
}