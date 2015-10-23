package jp.co.xing.utaehon03.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class LogUtils {

	public static void eInapp(Throwable e) {
		if (ConfigApp.CANLOGINAPP) {
			Log.e("INAPP", "INAPP", e);
		}
	}

	public static void eOutOfMemoryError(Context context) {
		if (ConfigApp.CANLOGOUTOFMEMORYERROR) {
			Log.e("eOutOfMemoryError", "eOutOfMemoryError : " + Runtime.getRuntime().totalMemory());
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			int memoryClass = am.getMemoryClass();
			Log.e("eOutOfMemoryError", "eOutOfMemoryError : " + memoryClass);
			Log.e("eOutOfMemoryError", "------------------------------------------------------------");
		}
	}

	public static void eCommon(String str) {
		if (ConfigApp.CANCOMMOM) {
			Log.e("eCommon", str);
			Log.e("eCommon", "------------------------------------------------------------");
		}
	}
}