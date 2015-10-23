package com.xing.joy.common;

import android.widget.ImageView;

public class PackageSongs {

	private int packageID;
	private int packageNumberSongs;
	private int packageVol;
	private String packageName;
	private String packagesRelation;
	private String packageChecksum;
	private String iconImage;
	private ImageView image;
	private String imageInfo;

	// Count number of songs
	public static int singleV1Songs = 0;
	public static int singleV2Songs = 0;
	public static int singleV1_2Songs = 0;
	public static int packageSongs = 0;

	public PackageSongs(int packageID, int packageNumberSongs,
			String packageName, String packageChecksum, String iconImage,
			String iconInfo, int packageVol, String packagesRelation) {
		super();
		this.packageID = packageID;
		this.packageNumberSongs = packageNumberSongs;
		this.packageName = packageName;
		this.packagesRelation = packagesRelation;
		this.packageChecksum = packageChecksum;
		this.iconImage = iconImage;
		this.imageInfo = iconInfo;
		this.packageVol = packageVol;
		switch (packageVol) {
		case 1:
			singleV1Songs++;
			singleV1_2Songs++;
			break;
		case 2:
			singleV2Songs++;
			singleV1_2Songs++;
			break;
		case 3:
			packageSongs++;
			break;
		}
	}

	public static void resetCountPackages() {
		singleV1Songs = 0;
		singleV2Songs = 0;
		packageSongs = 0;
		singleV1_2Songs = 0;
	}

	public int getPackageID() {
		return packageID;
	}

	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}

	public int getPackageNumberSongs() {
		return packageNumberSongs;
	}

	public void setPackageNumberSongs(int packageNumberSongs) {
		this.packageNumberSongs = packageNumberSongs;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageChecksum() {
		return packageChecksum;
	}

	public void setPackageChecksum(String packageChecksum) {
		this.packageChecksum = packageChecksum;
	}

	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public int getPackageVol() {
		return packageVol;
	}

	public void setPackageVol(int packageVol) {
		this.packageVol = packageVol;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public void removeImage() {
		this.image = null;
	}

	public String getPackagesRelation() {
		return packagesRelation;
	}

	public void setPackagesRelation(String packagesRelation) {
		this.packagesRelation = packagesRelation;
	}

}
