package com.xing.joy.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Logtime {
	public static boolean isEnable = false;
	public long timeTotal = 0;
	public long LogtimeStart = 0;
	public String LogPageStart = "";

	public static String deviceId = "";
	public static String urlLogPassword = "";
	public static String urlLogDevice = "";

	// second time
	public int TimeLimitCheck = 1;

	// data base
	public final static String SAMPLE_DB_NAME = "purchased_db_log_time";
	public final static String SAMPLE_TABLE_NAME = "logtime";

	// name page
	public final static String PAGE_TOP = "top";
	public final static String PAGE_WEBVIEW = "webview";
	public final static String PAGE_LISTSONG1 = "listsong1";
	public final static String PAGE_LISTSONG2 = "listsong2";
	public final static String PAGE_LISTSONG3 = "listsong3";
	public final static String PAGE_CREDIT = "credit";
	public final static String PAGE_HELP = "help";
	public final static String PAGE_BUYPACKAGE = "buypackage";
	public final static String PAGE_BUYVOL1 = "buyvol1";
	public final static String PAGE_BUYVOL2 = "buyvol2";
	public final static String PAGE_BUYVOL3 = "buyvol3";
	public final static String PAGE_PLAYKARAOKE = "playkaraoke";
	public final static String PAGE_PLAYMOVIE = "playmovie";

	public static SQLiteDatabase sampleDB = null;

	public Logtime(Context coreActivity) {
		// ==========================
		try {
			if (Logtime.sampleDB == null) {
				Logtime.sampleDB = coreActivity.openOrCreateDatabase(Logtime.SAMPLE_DB_NAME, Context.MODE_PRIVATE, null);
				Logtime.sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + Logtime.SAMPLE_TABLE_NAME + " (page_id VARCHAR, date VARCHAR," + " count INT(3), playtime INT(10));");
				// Log.e("========", "openOrCreateDatabase");
			}
		} catch (SQLiteException se) {
			// Log.e("========", "Could not create or Open the database");
		}
	}

	public void Logstart(String page) {
		long start = (long) System.currentTimeMillis();
		LogtimeStart = start;
	}

	public void Logend(String page) {
		if (page.equals("")) {
			return;
		}

		// LOG("E",
		// "----------------------Start -----------------------------");
		// LOG("E", page + "   " + LogPageStart);
		if (LogtimeStart > 0 && LogPageStart.endsWith(page)) {
			int total = 0;
			long end = (long) System.currentTimeMillis();
			if (end - LogtimeStart >= TimeLimitCheck * 1000) {
				total = (int) ((end - LogtimeStart) / 1000);
			}

			timeTotal = (end - LogtimeStart) - time * 1000;

			// LOG("E======:" + page, "total: " + total + " TIME : " + time);

			if (time > 0) {
				total = total - (int) time;
			}

			if (total > 0) {

				LogTotalPageUpdate(page, total);
			} else {
				reset();
			}
		}

		// LOG("E", "----------------------END -----------------------------");
	}

	public void reset() {
		LogtimeStart = 0;
		LogPageStart = "";
	}

	private long timePause = 0;

	public void onResume() {
		// reset
		if (timePause != 0) {
			time += ((System.currentTimeMillis() - timePause) / 1000);
			timePause = 0;
		}

		if (time > 0) {
			// Zlog.e("PAUSE " + LogPageStart + " : ", time + "");
		}
	}

	private long time = 0;

	public void onPause() {
		timePause = System.currentTimeMillis();
	}

	// TODO: update log time
	public void LogTotalPageUpdate(String page, int total) {
		try {
			// LOG("UPDATE======PAGE:" + page, "time:" + total);
			page = GetInfoPage(page);
			if (sampleDB != null) {
				// get currentDateandTime
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateandTime = sdf.format(new Date());
				// LOG("day==",currentDateandTime);

				// date test--> comment here
				// if (ConfigApp.DEBUG_LOG_LOGTIME) {
				// currentDateandTime = "2013-01-20";
				// }

				sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + SAMPLE_TABLE_NAME + " (page_id VARCHAR, date VARCHAR," + " count INT(3), playtime INT(10));");

				// check update
				Cursor c = sampleDB.rawQuery("SELECT page_id, date, count, playtime  FROM " + SAMPLE_TABLE_NAME + " where page_id = '" + page + "' and date = '" + currentDateandTime + "' ", null);// LIMIT
																																																	// 5

				if (c != null) {
					int count = 0;
					int playtime = 0;
					if (c.moveToFirst()) {
						do {
							count = c.getInt(c.getColumnIndex("count"));
							playtime = c.getInt(c.getColumnIndex("playtime"));
							LOG("Getall:" + page + ":===", "count=" + count + ",playtime: " + playtime);
						} while (c.moveToNext());
					}
					int count_update = count + 1;
					long playtime_update = playtime + total; /**/
					if (count >= 1) {
						sampleDB.execSQL("UPDATE " + SAMPLE_TABLE_NAME + " SET count = " + count_update + " , playtime = " + playtime_update + " where page_id = '" + page + "' and date = '" + currentDateandTime + "' ; ");
						reset();
						LOG("update======", "LogTotalPageUpdate=" + currentDateandTime);
					} else {
						sampleDB.execSQL("INSERT INTO " + SAMPLE_TABLE_NAME + " Values ('" + page + "','" + currentDateandTime + "',1 ," + total + ");");
						reset();
						LOG("add======", "LogTotalPageUpdate=" + currentDateandTime);
					}

				} else {
					sampleDB.execSQL("INSERT INTO " + SAMPLE_TABLE_NAME + " Values ('" + page + "','" + currentDateandTime + "',1 ," + total + ");");
					reset();
					LOG("add======", "LogTotalPageUpdate=" + currentDateandTime);
				}

			}

		} catch (SQLiteException se) {
			reset();
			LOG("err======", "LogTotalPageUpdate");
		} finally {
			// if (sampleDB != null)
			// // sampleDB.execSQL("DELETE FROM " + SAMPLE_TABLE_NAME);
			// // sampleDB.close();
		}
	}

	public String LogTotalPageGetall() {
		String data_all = "";
		try {
			if (sampleDB != null) {
				// get currentDateandTime
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateandTime = sdf.format(new Date());
				Cursor c = sampleDB.rawQuery("SELECT page_id, date, count, playtime  FROM " + SAMPLE_TABLE_NAME + " where count > 0 and date != '" + currentDateandTime + "'", null);// LIMIT
																																														// 5

				if (c != null) {
					if (c.moveToFirst()) {
						int indexFirst = 0;
						do {
							String page_id = c.getString(c.getColumnIndex("page_id"));
							String date = c.getString(c.getColumnIndex("date"));
							int count = c.getInt(c.getColumnIndex("count"));
							int playtime = c.getInt(c.getColumnIndex("playtime"));
							if (indexFirst == 1) {
								data_all += ",";
							}
							data_all += "{ \"page_id\": \"" + page_id + "\" ,\"date\":\"" + date + "\" , \"count\": " + count + "  , \"playtime\": " + playtime + "}";
							indexFirst = 1;
						} while (c.moveToNext());
					}
				} else {
					LOG("LogTotalPageGetall==", "null");
					return "";
				}
			} else {
				LOG("LogTotalPageGetall==", "sampleDB null");
				return "";
			}
		} catch (SQLiteException se) {
			LOG("LogTotalPageGetall==", "err");
			return "";
		} finally {
		}
		if (!data_all.equalsIgnoreCase("")) {
			String data_all2 = "";
			data_all2 = "{";
			data_all2 += "\"pass\":\"" + urlLogPassword + "\",";
			data_all2 += "\"device\":\"" + urlLogDevice + "\",";
			data_all2 += "\"user_id\":\"" + deviceId + "\",";
			data_all2 += "\"data\":[" + data_all + "]";
			data_all2 += "}";
			data_all = data_all2;
		}

		return data_all;
	}

	// TODO: get log time
	public void LogPageDeleteAll() {
		try {
			if (sampleDB != null) {
				// get currentDateandTime
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateandTime = sdf.format(new Date());
				sampleDB.execSQL("delete from " + SAMPLE_TABLE_NAME + " where count > 0 and date != '" + currentDateandTime + "'" + "; ");
			}
		} catch (SQLiteException se) {
			LOG("LogPageDeleteAll==", "err");
		} finally {
		}
	}

	public String GetInfoPage(String page) {
		if (page.endsWith(PAGE_TOP)) {
			return "top";// "10";
		}
		if (page.endsWith(PAGE_WEBVIEW)) {
			return "web_view";// "11";
		}
		if (page.endsWith(PAGE_LISTSONG1)) {
			return "tab1_list_song";// "12";
		}
		if (page.endsWith(PAGE_LISTSONG2)) {
			return "tab2_list_song";// "13";
		}
		if (page.endsWith(PAGE_LISTSONG3)) {
			return "tab3_list_song";// "14";
		}
		if (page.endsWith(PAGE_CREDIT)) {
			return "credit";// "15";
		}
		if (page.endsWith(PAGE_HELP)) {
			return "help";// "16";
		}
		if (page.endsWith(PAGE_BUYPACKAGE)) {
			return "list_buy_package";// "20";
		}
		if (page.endsWith(PAGE_BUYVOL1)) {
			return "list_buy_singlevol1";// "21";
		}
		if (page.endsWith(PAGE_BUYVOL2)) {
			return "list_buy_singlevol2";// "22";
		}
		if (page.endsWith(PAGE_BUYVOL3)) {
			return "list_buy_singlevol3";// "23";
		}
		if (page.endsWith(PAGE_PLAYKARAOKE)) {
			return "play_karaoke";// "30";
		}
		if (page.endsWith(PAGE_PLAYMOVIE)) {
			return "play_movie";// "31";
		}
		return page;
	}

	public void LOG(String TAG, String MESS) {
		// Log.e(TAG, MESS);
	}

	public long getTimeTotal() {
		return timeTotal;
	}

}