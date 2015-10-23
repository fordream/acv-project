package jp.co.xing.utaehon03.util;

import jp.co.xing.utaehon.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 
 * @author ACV 20130814
 * 
 */
public class ZDialogUtils implements OnClickListener {
	public static final int DIALOG_ERROR_001 = 1001;
	public static final int DIALOG_ERROR_002 = 1002;
	public static final int DIALOG_ERROR_004 = 1004;
	public static final int DIALOG_ERROR_005 = 1005;
	public static final int DIALOG_ERROR_007 = 1007;// chua dung

	public static final int DIALOG_ERROR_ANDROID_001 = 2001;
	public static final int DIALOG_ERROR_ANDROID_002 = 2002;
	public static final int DIALOG_ERROR_ANDROID_003 = 2003;
	public static final int DIALOG_ERROR_ANDROID_004 = 2004;// chua dung
	private Context context;

	public ZDialogUtils(Context context) {
		this.context = context;
	}

	public void onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);

		String message = null;
		String title = context.getString(R.string.app_name);
		// builder.setIcon(R.drawable.icon);
		if (id == DIALOG_ERROR_001) {
			message = context.getString(R.string.error001);
		} else if (id == DIALOG_ERROR_002) {
			// || id == MAIN_FIRST_DOWNLOAD
			message = context.getString(R.string.error002);
		} else if (id == DIALOG_ERROR_004) {
			message = context.getString(R.string.error004);
		} else if (id == DIALOG_ERROR_005) {
			message = context.getString(R.string.error005);
		} else if (id == DIALOG_ERROR_007) {
			message = context.getString(R.string.error007);
		} else if (id == DIALOG_ERROR_ANDROID_001) {
			message = context.getString(R.string.error_android_001);
		} else if (id == DIALOG_ERROR_ANDROID_002) {
			message = context.getString(R.string.error_android_002);
		} else if (id == DIALOG_ERROR_ANDROID_003) {
			message = context.getString(R.string.error_android_003);
		} else if (id == DIALOG_ERROR_ANDROID_004) {
			message = context.getString(R.string.error_android_004);
		}

		builder.setNegativeButton(context.getString(R.string.btn_yes), this);
		// builder.setTitle(title);
		builder.setMessage(message);
		builder.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
