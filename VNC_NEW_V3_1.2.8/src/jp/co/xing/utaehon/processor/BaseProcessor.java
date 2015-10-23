package jp.co.xing.utaehon.processor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public abstract class BaseProcessor {
	protected Context mContext;

	public BaseProcessor(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public final void execute() {
		onResult(onProcessor());
	}

	public final void executeAsyntask() {
		new AsyncTask<String, String, String>() {
			Intent intent;
			@Override
			protected String doInBackground(String... params) {
				intent = onProcessor();
				return null;
			}

			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				onResult(intent);
			};
		}.execute("");
	}

	public abstract Intent onProcessor();

	public abstract void onResult(Intent intent);
}