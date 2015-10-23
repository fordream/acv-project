package jp.co.xing.utaehon;

import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import com.xing.joy.others.SelectSong;

public class WaitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait);

		AlertDialog.Builder builder = new Builder(this);
		builder.setCancelable(false);
		builder.setMessage(R.string.outOfMemory);
		builder.setNegativeButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						CommonUtils.startNewActivity(WaitActivity.this,
								SelectSong.class, "");
						finish();
					}
				});
		builder.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}