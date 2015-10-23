package jp.co.xing.utaehon03.util;

import jp.co.xing.utaehon.R;
import android.content.Context;

public class UrlUtils {
	private Context context;

	public UrlUtils(Context context) {
		this.context = context;
	}

	private String getServer() {
		return context.getResources().getString(R.string.server_real);
	}

	public String getUrl(int res) {

		String folderServer = context.getResources().getString(res);

		if (folderServer.startsWith("http://")) {
			return folderServer;
		}

		return getServer() + context.getResources().getString(res);
	}
}
