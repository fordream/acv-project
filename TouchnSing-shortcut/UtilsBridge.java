package jp.acv;

import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.apportable.activity.VerdeActivity;
import jp.co.xing.picturebook.R;


public class UtilsBridge {
    public static void createShortcut(Activity activity, boolean hasShortcut) {
        Context context = (Context)activity;
        Log.d("UtilsBridge.java", "createShortcut");
        if (!hasShortcut) {
            Log.d("UtilsBridge.java", "hasShorcut=false -> create shortcut");
            final String title = context.getResources().getString(R.string.app_name);
            final int icon = R.drawable.icon;
            final Class<?> cls = VerdeActivity.class;
            
            final Intent shortcutIntent = new Intent();
            shortcutIntent.setClass(context, cls);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            final Intent putShortCutIntent = new Intent();
            putShortCutIntent.putExtra("duplicate", false);
            putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
            putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, icon));
            putShortCutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            context.sendBroadcast(putShortCutIntent);
        }
    }
}