package com.xing.joy.others;

import jp.co.xing.utaehon.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xing.joy.processdata.ApplicationData;

public class PushDialog extends Activity {

	ApplicationData data;
	String message;
	int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.push_dialog);
		Bundle extras = getIntent().getExtras();
		// Load button
		Button button1 = (Button) findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (type == 1) {
					data = new ApplicationData(getApplicationContext());
					data.setStringData("message", message);
					data.setIntData("type", type);
				}
				PushDialog.this.finish();
			}
		});
		Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setData(Uri
						.parse("market://details?id=jp.co.xing.utaehon"));
				getApplicationContext().startActivity(intent);
				PushDialog.this.finish();
			}
		});
		if (extras != null) {
			try {
				parseMessage(extras.getString("message"));
				if (message != null && message.length() > 0) {
					TextView view = (TextView) findViewById(R.id.message);
					view.setText(message);
				}
				if (type == 0) {
					button2.setVisibility(View.GONE);
					button1.setText("OK");
				} else {
					button1.setText(button1.getContext().getResources()
							.getString(R.string.button1));
					button2.setText(button1.getContext().getResources()
							.getString(R.string.button2));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.onCreate(savedInstanceState);
	}

	public void parseMessage(String message) throws Exception {
		this.message = android.text.Html.fromHtml(message.substring(3))
				.toString();
		char typeMessage = message.charAt(1);
		if (typeMessage == '0') {
			this.type = 0;
		} else {
			this.type = 1;
		}
	}
}
