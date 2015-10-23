package jp.co.xing.picturebook;

import vnc.com.android.gms.vnc.plus.p.p.R;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.vnp.core.common.CommonAndroid;

public class VerdeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PackageManager manager = getPackageManager();
		Intent intent = manager.getLaunchIntentForPackage(getPackageName());

		if (getIntent() != null) {
			Builder builder = new Builder(this);
			builder.setMessage(getIntent().toString() + "\nLAUNCHER--------\n"
					+ intent.toString());
			builder.show();
		}

		CommonAndroid.SHORTCUT commonShortCutUtils = new CommonAndroid.SHORTCUT(
				this);
//		commonShortCutUtils.createShortCutLauncher(R.string.app_name,
//				R.drawable.icon);

		// autoCreateShortCut(this);
		createIntent1(VerdeActivity.class,this);
	}

	private void autoCreateShortCut1(Context context) {
		Intent intentShortcut = createIntent1(VerdeActivity.class, context);
		intentShortcut
				.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		sendBroadcast(intentShortcut);
	}

	private Intent createIntent1(Class<?> cls, Context context) {
		PackageManager manager = getPackageManager();
		Intent shortcutIntent = manager
				.getLaunchIntentForPackage(getPackageName());

		// Intent shortcutIntent = new Intent(Intent.ACTION_MAIN); //
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				&& Build.BRAND.equals("samsung")) { // sam sung 2.x 
			//shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
				&& Build.BRAND.equals("samsung")) { // sam sung 4.x //
			shortcutIntent.addCategory("android.intent.category.LAUNCHER"); //
			shortcutIntent.setPackage(context.getPackageName());
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		} else { // orther //
			shortcutIntent.addCategory("android.intent.category.LAUNCHER"); //
			shortcutIntent.setPackage(context.getPackageName());
		} //
		shortcutIntent.setClass(context, cls);

		Intent intentShortcut = new Intent();
		intentShortcut.putExtra("android.intent.extra.shortcut.INTENT",
				shortcutIntent);
		String title = context.getResources().getString(R.string.app_name);
		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		intentShortcut.putExtra("duplicate", false);

		final int icon = R.drawable.icon;

		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context, icon));
		return intentShortcut;
	}

	public void autoCreateShortCut(Context context) {
		Intent intentShortcut = createIntent(VerdeActivity.class, context);
		intentShortcut
				.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		sendBroadcast(intentShortcut);
	}

	private Intent createIntent(Class<?> cls, Context context) {

		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				&& Build.BRAND.equals("samsung")) { // sam sung 2.x //
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
				&& Build.BRAND.equals("samsung")) { // sam sung 4.x
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		} else { // orther
			shortcutIntent.addCategory("android.intent.category.LAUNCHER");
			shortcutIntent.setPackage(context.getPackageName());
		} //
		shortcutIntent.setClass(context, cls);
		Intent intentShortcut = new Intent();
		intentShortcut.putExtra("android.intent.extra.shortcut.INTENT",
				shortcutIntent);
		String title = context.getResources().getString(R.string.app_name);
		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		intentShortcut.putExtra("duplicate", false);

		final int icon = R.drawable.icon;

		intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context, icon));
		return intentShortcut;
	}

}
