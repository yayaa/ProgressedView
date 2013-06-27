package com.yayandroid.progressedview;
 
import com.yayandroid.progressedview.ProgressedView.ProgressType;

import android.os.Bundle;
import android.view.View;

public class CustomSeekActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_seek);
		
		pv = (ProgressedView) findViewById(R.id.progressed);
		pv.setProgressType(ProgressType.SEEK_AND_INDETERMINATE);
		pv.setProgressListener(pv.new ProgressSeekListener() {

			@Override
			public void doBackgroundTask(View view) {

				for (int i = 0; i < 100; i += 10) {
					publishProgressToSeek(i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onTaskFinished(View view) {
				
			}

		});
	}

}
