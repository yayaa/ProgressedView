package com.yayandroid.progressedview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.SeekBar;

public class CustomLayoutActivity extends BaseActivity {
	
	View progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_layout);
		
		pv = (ProgressedView) findViewById(R.id.progressed);
		
		pv.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				
				pv.getViewTreeObserver().removeOnPreDrawListener(this);
				progress = pv.getProgress();
				
				return false;
			}
			
		});
		
		pv.setProgressListener(new ProgressedViewListener() {
			
			SeekBar mySeek;
			
			@Override
			public void onTaskFinished(View view) {
				mySeek.setProgress(0);
				mySeek = null;
			}
			
			@Override
			public void doBackgroundTask(View view) {
				
				for(int i = 0; i < 100; i += 10) {
					if(progress != null) {
						final int value = i;
						runOnUiThread(new Runnable() {
							@Override
							public void run() { 
								if(mySeek == null)
									mySeek = (SeekBar) progress.findViewById(R.id.myseek);
								mySeek.setProgress(value);
							} 
						});
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) { 
							e.printStackTrace();
						}
					}
				}
				
			}
		});
	}

}
