package jp.co.xing.utaehon03.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ZNetworkUtils {

	public static boolean haveConnectTed(Context context) {
		Object service = context.getSystemService(Context.CONNECTIVITY_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) service;
		NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].isConnected()) {
				return true;
			}
		}

		return false;
	}

	// public static void opennetworkSim(Context context, int requestCode) {
	// Intent intent = new Intent(Intent.ACTION_MAIN);
	// intent.setClassName("com.android.phone",
	// "com.android.phone.NetworkSetting");
	// ((Activity) context).startActivityForResult(intent, requestCode);
	// }
	//
	// public static void openWIFISetting(Context context, int requestCode) {
	// Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
	// ((Activity) context).startActivityForResult(intent, requestCode);
	// }
	//
	// public static void openNetWorkSetting(Context context, int requestCode) {
	// Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
	// ((Activity) context).startActivityForResult(intent, requestCode);
	// }
}