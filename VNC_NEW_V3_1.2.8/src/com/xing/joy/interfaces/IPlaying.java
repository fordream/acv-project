package com.xing.joy.interfaces;

import android.content.Intent;

public interface IPlaying {
	
	/**
	 * Create text view for play lyric
	 * @throws Exception 
	 */
	void createLyric() throws Exception;
	
	/**
	 * init service for lyric
	 */
	void initializationLyric();
	
	/**
	 * Add button for dislay lyric
	 */
	void addLyricControl();
	
	/**
	 * init media player
	 * @param pathMusic
	 * @throws Exception 
	 */
	void initializationMediaPlayer(String pathMusic) throws Exception;
	
	/**
	 * Update lyric from service
	 * @param intent
	 */
	void lyricPlaying(Intent intent);
}
