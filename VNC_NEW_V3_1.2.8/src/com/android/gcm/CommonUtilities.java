/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {
	/**
	 * Google API project id registered to use GCM.
	 */
	public static final String SENDER_ID = "27284071298";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCMDemo";

	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "com.android.gcm.DISPLAY_MESSAGE";

	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static int parseTypeMessage(String message) {
		char typeMessage = message.charAt(1);
		if (typeMessage == '0') {
			return 0;
		}
		return 1;
	}

	public static String parseTextMessage(String message) {
		return android.text.Html.fromHtml(message.substring(3)).toString();
	}

	public static Activity ACTIVITY_TOP;

	public static boolean checkApplicationRunning(Context context) {
		if (ACTIVITY_TOP != null) {
			if (ACTIVITY_TOP.isTaskRoot()) {
				return true;
			}
		}

		return false;
		// ActivityManager am = (ActivityManager)
		// context.getSystemService(Activity.ACTIVITY_SERVICE);
		// String packageName =
		// am.getRunningTasks(1).get(0).topActivity.getPackageName();
		// if (packageName.equalsIgnoreCase(context.getPackageName())) {
		// return true;
		// }
		// return false;
	}
}
