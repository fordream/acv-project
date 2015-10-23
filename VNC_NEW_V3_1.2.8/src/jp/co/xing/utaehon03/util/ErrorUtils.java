package jp.co.xing.utaehon03.util;

import java.io.File;

import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.xing.joy.others.Buy;
import com.xing.joy.others.SelectSong;
import com.xing.joy.processdata.ReportHistory;

public final class ErrorUtils {

	static AlertDialog alertDialog;

	public static void errorLoadResource(final Context context,
			final String songName, final String packageName) {

		// in case package name is null, song is free.
		// Delete file jar to user redownload
		if (packageName.equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle(context
					.getString(R.string.error_load_resource_title));
			alertDialog.setMessage(context
					.getString(R.string.error_load_resource_content));
			alertDialog.setButton(context.getString(R.string.btn_yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Set intent
							MemoryUtils memory = new MemoryUtils(context);
							String pathJarFile = memory
									.getPathFileInternalMemory();
							new File(pathJarFile + songName.toLowerCase() + "/"
									+ songName.toLowerCase() + ".jar").delete();
							alertDialog.dismiss();
							CommonUtils.startNewActivity(context,
									SelectSong.class, "");
							return;
						}
					});
			alertDialog.show();
		} else {
			ReportHistory report = new ReportHistory(context);
			report.increaseReportCountByPackage(packageName);
			if (report.increaseReportCount(songName) >= 4) {
				report.resetReportCount(songName);
				alertDialog = new AlertDialog.Builder(context).create();
				alertDialog.setTitle(context
						.getString(R.string.error_load_resource_title));
				alertDialog.setMessage(context
						.getString(R.string.error_load_resource_helper));
				alertDialog.setButton(context.getString(R.string.btn_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
								Intent browserIntent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse(context
												.getString(R.string.error_load_resource_url_helper)));
								((Activity) context)
										.startActivity(browserIntent);
								return;
							}
						});
				alertDialog.setButton2(context.getString(R.string.btn_no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Set intent
								alertDialog.dismiss();
								CommonUtils.startNewActivity(context,
										SelectSong.class, "");
								return;
							}
						});
				alertDialog.show();
			} else {
				alertDialog = new AlertDialog.Builder(context).create();
				alertDialog.setTitle(context
						.getString(R.string.error_load_resource_title));
				alertDialog.setMessage(context
						.getString(R.string.error_load_resource_content));
				alertDialog.setButton(context.getString(R.string.btn_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
								// Set intent
								CommonUtils.startNewActivity(context,
										Buy.class, "SelectSong");
								return;
							}
						});
				alertDialog.setButton2(context.getString(R.string.btn_no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
								// Set intent
								CommonUtils.startNewActivity(context,
										SelectSong.class, "");
								return;
							}
						});
				alertDialog.show();
			}
		}
	}
}
