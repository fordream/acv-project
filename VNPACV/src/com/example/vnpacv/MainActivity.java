package com.example.vnpacv;

import java.net.URISyntaxException;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.database.CommonDataStore;

public class MainActivity extends CommonBaseActivity {
	private EditText editText;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// init
		webView = getView(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		editText = getView(R.id.vNPACVEditextView1);

		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
					login();
					return true;
				}
				return false;
			}
		});
	}

	private void login() {
		String passwordInput = editText.getText().toString().trim();
		// check input
		if (passwordInput.equals("")) {
			showDialog(DIALOG_LOGIN_FAIL);
			return;
		}

		// compare and save password
		CommonDataStore dataStore = CommonDataStore.getInstance();
		dataStore.init(this);

		String passwordSave = dataStore.get("password", "");

		if (passwordSave.equals("")) {
			dataStore.save("password", passwordInput);
			loginSucess();
		} else if (passwordSave.equals(passwordInput)) {
			loginSucess();
		} else {
			showDialog(DIALOG_LOGIN_FAIL);
		}
	}

	private void loginSucess() {
		// hidden editext
		editText.setVisibility(View.GONE);

		// show webView
		// webView.setVisibility(View.VISIBLE);
		// webView.loadUrl("http://www.acvdev.com/redmine");
		try {
			Intent intent = Intent.parseUri("http://www.acvdev.com/redmine/",
					Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		} catch (URISyntaxException e) {
		}
	}
}