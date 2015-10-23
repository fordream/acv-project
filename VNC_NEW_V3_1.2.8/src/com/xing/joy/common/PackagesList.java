package com.xing.joy.common;

import java.util.ArrayList;

public class PackagesList {

	private ArrayList<Integer> id = new ArrayList<Integer>();
	private ArrayList<Integer> songNumber = new ArrayList<Integer>();
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> packageRelation = new ArrayList<String>();
	private ArrayList<String> checksum = new ArrayList<String>();
	private ArrayList<String> icon = new ArrayList<String>();
	private ArrayList<String> imageIntro = new ArrayList<String>();
	private ArrayList<Integer> vol = new ArrayList<Integer>();

	public ArrayList<Integer> getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id.add(id);
	}

	public ArrayList<String> getName() {
		return name;
	}

	public ArrayList<Integer> getSongNumber() {
		return songNumber;
	}

	public void setSongNumber(Integer songNumber) {
		this.songNumber.add(songNumber);
	}
	
	public ArrayList<String> getPackageRelation() {
		return packageRelation;
	}

	public void setPackageRelation(String packageRelation) {
		this.packageRelation.add(packageRelation);
	}

	public void setName(String name) {
		this.name.add(name);
	}

	public ArrayList<String> getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon.add(icon);
	}
	
	public ArrayList<String> getChecksum() {
		return this.checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum.add(checksum);
	}

	public ArrayList<String> getImageIntro() {
		return imageIntro;
	}

	public void setImageIntro(String imageIntro) {
		this.imageIntro.add(imageIntro);
	}
	
	public ArrayList<Integer> getVol() {
		return vol;
	}

	public void setVol(Integer id) {
		this.vol.add(id);
	}

}
