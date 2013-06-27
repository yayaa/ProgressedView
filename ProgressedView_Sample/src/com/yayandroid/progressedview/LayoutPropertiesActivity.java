package com.yayandroid.progressedview;

import android.os.Bundle;

public class LayoutPropertiesActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_properties);
		
		pv = (ProgressedView) findViewById(R.id.progressed);
	}

}
