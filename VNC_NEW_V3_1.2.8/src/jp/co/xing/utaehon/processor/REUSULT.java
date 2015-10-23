package jp.co.xing.utaehon.processor;

public enum REUSULT {
	PACKAGEISNULLORBLANK, // when package is null
	FREE, // when is free song and haven't download
	ISFREE_AND_NOT_DOWNLOAD, // when is free song and haven't download
	ISFREE_AND_DOWNLOADED, // when free and downloaded
	FREEDOWNLOADED, // when free and downloaded
	ISPURCHARSE, // when purchase
	ISPURCHARSE_AND_DOWNLOADEDFREE, // when purchase and downloaded free
	ISPURCHARSE_AND_DOWNLOADPURCHARSE, // when purchase and downloaded purchase
	ISPURCHARSE_AND_NOTDOWNLOAD, // when purchase and haven't download
	PURCHARSEDOWNLOAED, // when purchase and downloaded purchase
	ISFREE_AND_DOWNLOADED_OVERTIME // when free is overtime
}