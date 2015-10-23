package jp.co.xing.utaehon;

import jp.co.xing.utaehon03.util.LogUtils;
import android.content.Intent;
import android.os.Bundle;

import com.xing.joy.common.CoreActivity;

public class Startup extends CoreActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent() != null)
			LogUtils.eCommon("StartUp : " + getIntent().toString());
		startActivity(new Intent(this, VNCStartUpActivity.class));
		finish();
	}

	@Override
	public String getNameCount() {
		// TODO Auto-generated method stub
		return null;
	}
}