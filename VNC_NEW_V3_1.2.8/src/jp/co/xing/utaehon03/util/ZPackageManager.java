package jp.co.xing.utaehon03.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class ZPackageManager {

	public static final String TAG = ZPackageManager.class.getName();
	private PackageManager packageManager;

	public ZPackageManager(Context context) {
		packageManager = context.getPackageManager();
	}

	public List<ResolveInfo> getListIntentFiler(Intent intent) {
		List<ResolveInfo> resolves = packageManager.queryIntentActivities(
				intent, 0);
		return resolves;
	}

	public ResolveInfo getListIntentFiler(Intent intent, String mainPackage) {
		List<ResolveInfo> resolves = getListIntentFiler(intent);

		ResolveInfo resolveInfo = null;
		for (ResolveInfo res : resolves) {
			if (res.activityInfo.packageName.equals(mainPackage)) {
				resolveInfo = res;
				return resolveInfo;
			}
		}
		return resolveInfo;
	}

	public void getListIntentFilerAll() {

		List<ApplicationInfo> list = packageManager.getInstalledApplications(0);
		for (ApplicationInfo info : list) {
			if (info.packageName.contains("google")) {
			}
		}
	}
}