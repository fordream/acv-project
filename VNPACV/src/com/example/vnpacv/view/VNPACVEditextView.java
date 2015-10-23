package com.example.vnpacv.view;

import android.content.Context;
import android.util.AttributeSet;

import com.ict.library.common.CommonResize;
import com.ict.library.view.ResizeEditextTextView;

public class VNPACVEditextView extends ResizeEditextTextView {

	public VNPACVEditextView(Context context) {
		super(context);
	}

	public VNPACVEditextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VNPACVEditextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void _onDraw() {
		super._onDraw();
		// changle size
		CommonResize._20130408_resizeLandW960H640(this, 600, 200);
	}
}