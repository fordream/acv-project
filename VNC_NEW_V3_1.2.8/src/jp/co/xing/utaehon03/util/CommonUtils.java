package jp.co.xing.utaehon03.util;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon.VNCStartUpActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.gcm.CommonUtilities;
import com.android.gcm.ServerUtilities;
import com.google.android.gcm.GCMRegistrar;
import com.xing.joy.common.CoreActivity;
import com.xing.joy.common.CoreGameActivity;
import com.xing.joy.others.Top;
import com.xing.joy.processdata.ApplicationData;

public final class CommonUtils {

	

	public static String findDeviceID(Context context) {
		String deviceID = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return deviceID;
	}

	public static void popupAlert(Context context, String message) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setButton(context.getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				return;
			}
		});
		alertDialog.show();
	}

	public static void alertNotificationPush(final Context context, String message, final int type, final boolean hasCheckbox) {
		final Dialog dialog = new Dialog(context);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (hasCheckbox) {
			dialog.setContentView(R.layout.new_version_dialog);
		} else {
			dialog.setContentView(R.layout.push_dialog);
		}
		final ApplicationData appData = new ApplicationData(context);
		final CheckBox chooseNewVesion = (CheckBox) dialog.findViewById(R.id.checkbox_1);
		Button button1 = (Button) dialog.findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (hasCheckbox) {
					try {
						if (chooseNewVesion.isChecked()) {
							appData.setBoolData("NotShowPopupNewVersion", true);
						} else {
							appData.setBoolData("NotShowPopupNewVersion", false);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (type == 2) {
					Top.callback.callback();
				}
				dialog.dismiss();
			}
		});
		Button button2 = (Button) dialog.findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (hasCheckbox) {
						if (chooseNewVesion.isChecked()) {
							appData.setBoolData("NotShowPopupNewVersion", true);
						} else {
							appData.setBoolData("NotShowPopupNewVersion", false);
						}
					}
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("market://details?id=jp.co.xing.utaehon"));
					context.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					popupAlert(context, "Google Play Client Not Found!");
					if (type == 2) {
						Top.callback.callback();
					}
				} catch (Exception e) {
				}
				dialog.dismiss();
			}
		});
		if (message != null && message.length() > 0) {
			TextView view = (TextView) dialog.findViewById(R.id.message);
			view.setText(message);
		}
		if (type == 0) {
			button2.setVisibility(View.GONE);
			button1.setText("OK");
		} else {
			button1.setText(button1.getContext().getResources().getString(R.string.button1));
			button2.setText(button1.getContext().getResources().getString(R.string.button2));
		}
		dialog.show();
	}

	public static void registerGCM(boolean isOK, final Context context) {
		try {
			if (isOK) {
				// Log.e("aaaaaaaa", "aaaaaaaaaaa1");
				GCMRegistrar.checkDevice(context);
				GCMRegistrar.checkManifest(context);
				final String regId = GCMRegistrar.getRegistrationId(context);
				// Log.e("aaaaaaaa", "aaaaaaaaaaa1==" + regId);
				if (regId.equals("")) {
					GCMRegistrar.register(context, CommonUtilities.SENDER_ID);
				} else {
					// Log.e("aaaaaaaa", "aaaaaaaaaaa2");
					if (!GCMRegistrar.isRegisteredOnServer(context)) {
						// Log.e("aaaaaaaa", "aaaaaaaaaaa3");
						final AsyncTask<Void, Void, Void> mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								// Log.e("aaaaaaaa", "aaaaaaaaaaa4");
								boolean registered = ServerUtilities.register(context, regId);
								// Log.e("aaaaaaaa", "aaaaaaaaaaa5" +
								// registered);
								if (!registered) {
									GCMRegistrar.unregister(context);
								}
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
							}
						};
						mRegisterTask.execute(null, null, null);
					}
				}
			} else {
				GCMRegistrar.unregister(context);
			}
		} catch (Exception ex) {
		} catch (Error error) {
		}
	}

	public static void autoCreateShortCut(final boolean hasShorcut, final Context context) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!hasShorcut) {
					// Create Shortcut

					final String title = context.getResources().getString(R.string.app_name);
					final int icon = R.drawable.icon;
					final Class<?> cls = VNCStartUpActivity.class;

					final Intent shortcutIntent = new Intent();
					shortcutIntent.setClass(context, cls);
					shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					final Intent putShortCutIntent = new Intent();
					putShortCutIntent.putExtra("duplicate", false);

					putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
					putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
					putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, icon));

					// putShortCutIntent
					// .setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
					//
					// context.sendBroadcast(putShortCutIntent);

					putShortCutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
					context.sendBroadcast(putShortCutIntent);
				}
			}
		}).start();

	}

	private static void delShortcut(Context cls, Class<?> cls1) {
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

		// Shortcut name
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, cls.getString(R.string.app_name));

		// ComponentName comp = new ComponentName(cls.getPackageName(),
		// appClass);
		// shortcut.setClass(cls, cls1);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setClass(cls, cls1));
		cls.sendBroadcast(shortcut);
	}

	public static void startNewActivity(Context context, Class<?> cls, String backLink) {
		Intent intent = new Intent(context, cls);

		if (!backLink.equalsIgnoreCase("")) {
			intent.putExtra("Back", backLink);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

		if (context instanceof CoreActivity) {
			((CoreActivity) context).logEnd();
		} else if (context instanceof CoreGameActivity) {
			((CoreGameActivity) context).logEnd();
		}

		if (Build.MODEL.equalsIgnoreCase("IS03") || Build.MODEL.equalsIgnoreCase("IS06")) {
			((Activity) context).finish();
			context.startActivity(intent);
		} else {
			context.startActivity(intent);
			((Activity) context).finish();
		}
		if (!(context instanceof VNCStartUpActivity))
			System.exit(0);
	}
}