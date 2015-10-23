package com.xing.joy.processdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ReportHistory {
	/** Share preferences store data. */
	protected SharedPreferences shareReportHistory;

	/** Editor access share data. */
	private Editor editReportHistory;

	public ReportHistory(Context context) {
		// init SharePreference for read/save data
		shareReportHistory = context.getSharedPreferences("history_report",
				Context.MODE_PRIVATE);
		editReportHistory = shareReportHistory.edit();
	}

	public int increaseReportCount(String songName) {
		int countReport = shareReportHistory.getInt(songName, 0);
		countReport = countReport + 1;
		editReportHistory.putInt(songName, countReport);
		editReportHistory.commit();
		return countReport;
	}
	
	public void increaseReportCountByPackage(String packageName) {
		int countReport = shareReportHistory.getInt(packageName, 0);
		countReport = countReport + 1;
		editReportHistory.putInt(packageName, countReport);
		editReportHistory.commit();
	}
	
	public int getReportCount(String songName){
		return shareReportHistory.getInt(songName, 0);
	}
	
	public int getReportCountByPackage(String packageName){
		return shareReportHistory.getInt(packageName, 0);
	}

	public void resetReportCount(String songName) {
		editReportHistory.putInt(songName, 0);
		editReportHistory.commit();
	}
	
	public void resetReportCountByPackage(String packageName) {
		editReportHistory.putInt(packageName, 0);
		editReportHistory.commit();
	}
}
