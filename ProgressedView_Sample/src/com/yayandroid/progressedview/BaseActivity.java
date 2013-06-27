package com.yayandroid.progressedview;

import com.yayandroid.progressedview.ProgressedView; 
import com.yayandroid.progressedview.R;
import com.yayandroid.progressedview.ProgressedView.AnimationType;
import com.yayandroid.progressedview.ProgressedView.ProgressType;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {

	protected ProgressedView pv;

	protected final String[] anims = new String[] { "Swipe - LeftToRight",
			"Swipe - RightToLeft", "Swipe - TopToBottom",
			"Swipe - BottomToTop", "ScaleIn", "ScaleOut", "Alpha" };
	protected final String[] interpolations = new String[] { "Accelerate",
			"Decelerate", "Accelerate-Decelerate", "Anticipate", "Overshoot",
			"Anticipate-Overshoot", "Cycle", "Bounce" };
	protected final String[] progressTypes = new String[] {
		"Indeterminate", "Seek", "Both"	
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sample, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.animationType:
			ShowPopupMenu(anims, 0);
			return true;
		case R.id.reversingType:
			ShowPopupMenu(anims, 1);
			return true;
		case R.id.interpolationType:
			ShowPopupMenu(interpolations, 2);
			return true;
		case R.id.progressType:
			ShowPopupMenu(progressTypes, 3);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void ShowPopupMenu(String[] items, final int type) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int position) {
		    	Refresh(position, type);
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}

	public void Refresh(int position, int type) {
		if (pv != null) {
			switch (type) {
			case 0:
				pv.setAnimationType(AnimationType.values()[position]);
				break;
			case 1:
				pv.setReverseAnimationType(AnimationType.values()[position]);
				break;
			case 2:
				pv.setInterpolation(position);
				break;
			case 3:
				pv.setProgressType(ProgressType.values()[position]);
				break;
			}
		}
	}

}
