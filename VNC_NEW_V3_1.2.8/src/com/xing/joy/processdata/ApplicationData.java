package com.xing.joy.processdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xing.joy.interfaces.IDataActions;

public class ApplicationData {
	/** Share preferences store data. */
	protected SharedPreferences shareApplicationData;

	/** Editor access share data. */
	private Editor editApplicationData;

	public ApplicationData(Context context) {
		// init SharePreference for read/save data
		shareApplicationData = context.getSharedPreferences(
				IDataActions.PREFS_INFO_APP, Context.MODE_PRIVATE);
		editApplicationData = shareApplicationData.edit();
	}

	public void setStringData(String key, String val) {
		editApplicationData.putString(key, val);
		editApplicationData.commit();
	}

	public String getStringData(String key) {
		return shareApplicationData.getString(key, "");
	}

	public void setIntData(String key, int val) {
		editApplicationData.putInt(key, val);
		editApplicationData.commit();
	}

	public int getIntData(String key) {
		return shareApplicationData.getInt(key, 0);
	}
	
	public void setBoolData(String key, boolean boolValue){
		editApplicationData.putBoolean(key, boolValue);
		editApplicationData.commit();
	}
	
	public boolean getBoolData(String key){
		return shareApplicationData.getBoolean(key, false);
	}
}
