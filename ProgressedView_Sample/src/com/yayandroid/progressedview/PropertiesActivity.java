package com.yayandroid.progressedview;

import com.yayandroid.progressedview.ProgressedView;
import com.yayandroid.progressedview.R;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PropertiesActivity extends BaseActivity {

	TextView animTimeText;
	CheckBox remainSteady, bringFront;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_properties);

		pv = (ProgressedView) findViewById(R.id.progressed);
		pv.setProgressListener(new ProgressedViewListener() {

			@Override
			public void onClick(View view) {
				super.onClick(view);
				Toast.makeText(getApplicationContext(),
						getString(R.string.error), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onTaskFinished(View view) {
				Toast.makeText(getApplicationContext(), "DONE!",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void doBackgroundTask(View view) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		animTimeText = (TextView) findViewById(R.id.animTimeText);
		animTimeText.setText(String.valueOf(pv.getAnimationTime()));

		remainSteady = (CheckBox) findViewById(R.id.remainSteadyCheck);
		remainSteady.setOnCheckedChangeListener(checkChangedListener);
		bringFront = (CheckBox) findViewById(R.id.bringFrontCheck);
		bringFront.setOnCheckedChangeListener(checkChangedListener);

	}

	private CompoundButton.OnCheckedChangeListener checkChangedListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (buttonView.getId() == R.id.bringFrontCheck) {
				pv.setBringTargetFront(isChecked);
			} else if (buttonView.getId() == R.id.remainSteadyCheck) {
				pv.setRemainSteady(isChecked);
				if (isChecked) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.advice), Toast.LENGTH_LONG)
							.show();
				}
			}

		}
	};

	public void AnimTimeClick(View v) {
		int animationTime = FindNewAnimTime(animTimeText.getText().toString(),
				(String) v.getTag());
		animTimeText.setText(String.valueOf(animationTime));
		pv.setAnimationTime(animationTime);
	}

	public int FindNewAnimTime(String before, String addition) {
		int beforeInt = Integer.parseInt(before);
		int addInt = Integer.parseInt(addition);

		if (addInt < 0 && beforeInt < Math.abs(addInt))
			return beforeInt;

		return beforeInt + addInt;
	}

}
