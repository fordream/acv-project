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
package jp.co.xing.utaehon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.gcm.CommonUtilities;
import com.android.gcm.ServerUtilities;
import com.google.android.gcm.GCMBaseIntentService;
import com.xing.joy.others.PushDialog;
import com.xing.joy.processdata.ApplicationData;

/**
 * {@link IntentService} responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(CommonUtilities.SENDER_ID);
		Log.i(TAG, "start");
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		ServerUtilities.unregister(context);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		ApplicationData appData = new ApplicationData(context);
		if (appData.getStringData("AllowReceivePush").equalsIgnoreCase("no")) {
			return;
		}
		final String message = intent.getStringExtra("message");
		
		
		if (!CommonUtilities.checkApplicationRunning(context)) {
			Intent trIntent = new Intent(context, PushDialog.class);
			trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			trIntent.putExtra("message", message);
			context.startActivity(trIntent);
		} else {
			Intent broadcast = new Intent();
			// Bundle the counter value with Intent
			broadcast.putExtra("message",
					CommonUtilities.parseTextMessage(message));
			broadcast.putExtra("type",
					CommonUtilities.parseTypeMessage(message));
			broadcast.setAction("com.xing.joy.common.activity.displayevent"); // Define
																				// intent-filter
			context.sendBroadcast(broadcast);
		}
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

}
