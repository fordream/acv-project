package com.example.googleanalyticssample;

public interface IGATracker {
	public void sendTiming(long loadTime, String category, String name, String label);

	public void sendEvent(String category, String action, String label);

	public void sendEvent(String category, String action, String label, Long optionValue);

	public void sendView(String view);
}