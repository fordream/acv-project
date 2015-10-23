package com.xing.joy.processdata;

public class FilesModel {

	private String fileName;
	private String pathDir;
	private String url;
	private String checksum;
	private long fileSize;
	private String pathNewDir;

	public FilesModel(String fileName, String url) {
		super();
		this.fileName = fileName;
		this.url = url;
	}

	public FilesModel(String fileName, String url, long fileSize) {
		super();
		this.fileName = fileName;
		this.url = url;
		this.fileSize = fileSize;
	}

	public FilesModel(String fileName, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	public FilesModel(String fileName, String url, String pathDir) {
		super();
		this.fileName = fileName;
		this.url = url;
		this.pathDir = pathDir;
	}

	public FilesModel(String fileName, String url, String pathDir, long fileSize) {
		super();
		this.fileName = fileName;
		this.url = url;
		this.pathDir = pathDir;
		this.fileSize = fileSize;
	}
	
	public FilesModel(String fileName, String url, String pathDir, long fileSize, String pathNewDir) {
		super();
		this.fileName = fileName;
		this.url = url;
		this.pathDir = pathDir;
		this.fileSize = fileSize;
		this.pathNewDir = pathNewDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPathDir() {
		return pathDir;
	}

	public void setPathDir(String pathDir) {
		this.pathDir = pathDir;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getPathNewDir() {
		return pathNewDir;
	}

	public void setPathNewDir(String pathNewDir) {
		this.pathNewDir = pathNewDir;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
