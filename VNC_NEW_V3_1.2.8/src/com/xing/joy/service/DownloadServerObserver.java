package com.xing.joy.service;

import java.util.HashMap;

import android.content.Context;

public class DownloadServerObserver {
	private static DownloadServerObserver downloadServerObserver = new DownloadServerObserver();
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();

	public static DownloadServerObserver getInstance() {
		return downloadServerObserver;
	}

	private DownloadServerObserver() {

	}

	public Object get(String id) {
		return hashMap.get(id);
	}

	public boolean push(String id, Object object) {
		if (get(id) == null) {
			hashMap.put(id, object);
			return true;
		}

		return false;
	}

	public boolean remove(String id) {
		if (get(id) != null) {
			hashMap.remove(id);
			return true;
		}

		return false;
	}
	
	private Context mContext;
	public void init(Context context) {
		if(mContext == null){
			mContext = context;
//			mContext.bindService(service, conn, flags);
		}
	}
}