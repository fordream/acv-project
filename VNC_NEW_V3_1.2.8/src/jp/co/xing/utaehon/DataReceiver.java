package jp.co.xing.utaehon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataReceiver extends BroadcastReceiver {
	public static final String ACTION = "jp.co.xing.utaehon.DataReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		String category = intent.getStringExtra("category");
		if (category != null && !"".equals(category.trim())) {
			// gauTils.sendEvent(category, category, category);
			// gauTils.sendTiming(1l, category, category, category);
			// gauTils.sendView(category);
			// Log.e("LOG SEND GAME END", category);
		}
	}
}
