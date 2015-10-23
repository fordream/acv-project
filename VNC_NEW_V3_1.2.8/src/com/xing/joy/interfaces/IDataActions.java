package com.xing.joy.interfaces;

public interface IDataActions {
	
	/** Define Shared Preferences and Key *********/
	public static final String PREFS_INFO_APP = "application_data";
	public static final String TYPE_NAME = "type_name";
	public static final String TYPE_ID = "type_id";
	public static final String CLASS_NAME = "class_name";
	public static final String CLASS_STATUS = "class_status";
	public static final String VERSION = "version";
	public static final String PACKAGE_NAME = "package_name";
	public static final String SONG_NAME = "song_name";
	public static final String LANGUAGE = "language";
	public static final String HEIGHT_BAR = "height_bar";
	public static final String IS_SHORTCUT = "is_shortcut";
	public static final String PROCESS_ID = "process_id";
	
	/** Shared preference file name to store purchase result. */
	public static final String PURCHASED_DB = "purchased_db";
	public static final String SONG_NAME_JAPAN = "_SONG_NAME_JAPAN";
	
	/**
	 * When application is paused, save information to memory,
	 * Class name, song code & type
	 */
    void updateInfosOnPause();

    /**
     * When quits normal, clear data saved about class
     */
    void deleteInfosOnBack();

    /**
     * Read information to start application
     */
    void readInfosOnCreate();

}
