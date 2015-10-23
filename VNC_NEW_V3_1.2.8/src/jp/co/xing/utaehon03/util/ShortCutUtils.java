package jp.co.xing.utaehon03.util;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon.VNCStartUpActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ShortCutUtils {
	private Context context;

	public ShortCutUtils(Context context) {
		this.context = context;
	}

	public void autoCreateShortCut() {
		// Intent intentShortcut = createIntent(VNCStartUpActivity.class, null);
		// intentShortcut.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		// context.sendBroadcast(intentShortcut);

		autoCreateShortCut(VNCStartUpActivity.class, R.string.app_name,
				R.drawable.icon);
	}

	// private Intent createIntent(Class<?> cls, String action) {
	//
	// Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
	// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// shortcutIntent.setClass(context, cls);
	//
	// Intent intentShortcut = new Intent();
	// if (action != null) {
	// intentShortcut = new Intent(action);
	// }
	// intentShortcut.putExtra("android.intent.extra.shortcut.INTENT",
	// shortcutIntent);
	// String title = context.getResources().getString(R.string.app_name);
	// intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
	// intentShortcut.putExtra("duplicate", false);
	//
	// final int icon = R.drawable.icon;
	//
	// intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	// Intent.ShortcutIconResource.fromContext(context, icon));
	// return intentShortcut;
	// }

	public void autoCreateShortCut(Class<?> clss, int resstrName, int resIcon) {
		Intent intentShortcut = createIntent(clss, null, resstrName, resIcon);
		intentShortcut
				.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		context.sendBroadcast(intentShortcut);
	}

	private Intent createIntent(Class<?> cls, String action, int resstrName,
			int resIcon) {

		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// -------------------------------------------------------------
		// sam sung 2x
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				&& Build.BRAND.equals("samsung")) {
			// sam sung 2.x
			// shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
				&& Build.BRAND.equals("samsung")) {
			// sam sung 4.x
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		} else {
			// orther
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
		}
		// -------------------------------------------------------

		shortcutIntent.setClass(context, cls);

		Intent intentShortcut = new Intent();
		if (action != null) {
			intentShortcut = new Intent(action);
		}
		intentShortcut.putExtra("android.intent.extra.shortcut.INTENT",
				shortcutIntent);
		String title = context.getResources().getString(resstrName);
		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// intentShortcut
		// .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intentShortcut.putExtra("duplicate", false);

		final int icon = resIcon;

		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context, icon));
		return intentShortcut;
	}

}