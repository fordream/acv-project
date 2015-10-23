package jp.co.xing.utaehon.processor;

import java.io.File;

import jp.co.xing.utaehon03.util.MemoryUtils;
import android.content.Context;
import android.content.Intent;

import com.ict.library.database.DataStore;

public class IDFreePurchaseComparseProcessor extends BaseProcessor {
	public final static String PUCHARSEID = "jp.co.xing.utaehon";// jp.co.xing.utaehon.purikyua
	public final static String FREEID = "free.song.relation";// free.song.relation.purikyua

	public static final String STR_RESULT = "result";

	private String packageName;

	public IDFreePurchaseComparseProcessor(Context mContext, String packageName) {
		super(mContext);
		this.packageName = packageName;
	}

	@Override
	public Intent onProcessor() {
		Intent intent = new Intent();
		MemoryUtils memoryUtils = new MemoryUtils(mContext);

		if (packageName == null
				|| (packageName != null && packageName.trim().equals(""))) {
			// khi package la null hoac blank
			intent.putExtra(STR_RESULT, REUSULT.PACKAGEISNULLORBLANK);
		} else if (packageName.contains(FREEID)) {
			// bai free
			String path = memoryUtils.getPathFileExternalMemory(packageName);
			// bai free da download
			if (new File(path).exists()) {
				intent.putExtra(STR_RESULT,
						REUSULT.ISFREE_AND_DOWNLOADED_OVERTIME);
			} else {
				// free chua download
				intent.putExtra(STR_RESULT, REUSULT.ISFREE_AND_NOT_DOWNLOAD);
			}
		} else {
			String packageFree = packageName.replace(PUCHARSEID, FREEID);
			String pathFree = memoryUtils
					.getPathFileExternalMemory(packageFree);
			String pathPurchase = memoryUtils
					.getPathFileExternalMemory(packageName);

			if (new File(pathFree).exists()) {
				// ourrchase nhung download free roi
				intent.putExtra(STR_RESULT,
						REUSULT.ISPURCHARSE_AND_DOWNLOADEDFREE);
			} else {

				if (new File(pathPurchase).exists()) {
					// purchase va da download purchase
					intent.putExtra(STR_RESULT,
							REUSULT.ISPURCHARSE_AND_DOWNLOADPURCHARSE);
				} else {
					// purchase va chua download
					intent.putExtra(STR_RESULT,
							REUSULT.ISPURCHARSE_AND_NOTDOWNLOAD);
				}
			}
		}

		return intent;
	}

	@Override
	public void onResult(Intent intent) {
	}

	private boolean canRedownload(String packageName) {
		DataStore dataStore = DataStore.getInstance();
		dataStore.init(mContext);

		String dateOfServer = dataStore.get("DATE" + packageName, "");
		String dateOfClient = dataStore.get(packageName + "DATE", "");
		MemoryUtils memoryUtils = new MemoryUtils(mContext);
		String pathFree = memoryUtils.getPathFileExternalMemory(packageName);

		if (new File(pathFree).exists()) {
			if (dateOfClient != null && dateOfClient.equals(dateOfServer)) {
				return true;
			}
		}

		return false;
	}

}