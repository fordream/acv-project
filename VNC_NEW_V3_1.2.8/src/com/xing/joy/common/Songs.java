package com.xing.joy.common;

import java.util.Comparator;

import android.util.Log;

public class Songs implements Comparable<Songs> {

	private String songNameEnglish;
	private String songNameJapanese;
	private int idInPackage;
	private String packageName;
	private long datePurchase;
	private String pathImage;
	private String pathImageMovie;
	public static boolean sortASC = true;

	public Songs(String songNameEnglish, String songNameJapanese,
			int idInPackage, String packageName, long datePurchase,
			String pathImage, String pathImageMovie) {
		super();
		this.songNameEnglish = songNameEnglish;
		this.songNameJapanese = songNameJapanese;
		this.idInPackage = idInPackage;
		this.packageName = packageName;
		this.datePurchase = datePurchase;
		this.pathImage = pathImage;
		this.pathImageMovie = pathImageMovie;
	}

	public String getSongNameEnglish() {
		return songNameEnglish;
	}

	public void setSongNameEnglish(String songNameEnglish) {
		this.songNameEnglish = songNameEnglish;
	}

	public String getSongNameJapanese() {
		return songNameJapanese;
	}

	public void setSongNameJapanese(String songNameJapanese) {
		this.songNameJapanese = songNameJapanese;
	}

	public int getIdInPackage() {
		return idInPackage;
	}

	public void setIdInPackage(int idInPackage) {
		this.idInPackage = idInPackage;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String inPackage) {
		this.packageName = inPackage;
	}

	public long getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(int datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public String getPathImageMovie() {
		return pathImageMovie;
	}

	public void setPathImageMovie(String pathImageMovie) {
		this.pathImageMovie = pathImageMovie;
	}

	@Override
	public int compareTo(Songs another) {
		try {
			long compareDate = ((Songs) another).getDatePurchase();
			int compareID = ((Songs) another).getIdInPackage();
			// ascending order
			//Log.e("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA",getPackageName() + " : "  + datePurchase + "   " + compareDate);
			if (sortASC) {
				if (this.datePurchase == compareDate) {
					return (int) (this.idInPackage - compareID);
				} else {
					if (this.datePurchase > compareDate) {
						return 1;
					} else {
						return -1;
					}
				}
			} else {
				if (this.datePurchase == compareDate) {
					return (int) (this.idInPackage - compareID);
				} else {
					if (compareDate > this.datePurchase) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		} catch (Exception e) {
			Log.e("SSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSS", e);
			return 0;
		}
	}

	public static Comparator<Songs> SongNameComparator = new Comparator<Songs>() {

		public int compare(Songs song1, Songs song2) {

			String songName1 = song1.getSongNameJapanese().toUpperCase();
			String songName2 = song2.getSongNameJapanese().toUpperCase();

			// ascending order
			return songName1.compareTo(songName2);
		}

	};
}