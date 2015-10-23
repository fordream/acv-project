package com.xing.joy.processdata;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DownloadHistory {
	/** Share preferences store data. */
	protected SharedPreferences shareReportHistory;
	protected Editor editHistoryDownload;

	public DownloadHistory(Context context) {
		// init SharePreference for read/save data
		shareReportHistory = context.getSharedPreferences("history_download",
				Context.MODE_PRIVATE);
		editHistoryDownload = shareReportHistory.edit();
	}

	public void setStringData(String key, String val) {
		editHistoryDownload.putString(key, val);
		editHistoryDownload.commit();
	}

	public void setIntData(String key, int val) {
		editHistoryDownload.putInt(key, val);
		editHistoryDownload.commit();
	}

	public String getStringData(String key) {
		return shareReportHistory.getString(key, "");
	}

	public int getIntData(String key) {
		return shareReportHistory.getInt(key, 0);
	}

	public void removeHistory(String key) {
		editHistoryDownload.remove(key);
		editHistoryDownload.commit();
	}

	public Map<String, ?> getAll() {
		return shareReportHistory.getAll();
	}
}
