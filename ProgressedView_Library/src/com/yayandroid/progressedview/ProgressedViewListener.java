package com.yayandroid.progressedview;

import android.view.View;

public abstract class ProgressedViewListener {

	/**
	 * This method is not supposed to get called unless progressedView has been
	 * disabled or any error has occurred. it will return as a normal click
	 * event.
	 * 
	 */
	public void onClick(View view) {

	}

	/**
	 * This method is called from background thread, so there should not be
	 * touching views. Do whatever needs to do when user clicks child and when
	 * this task is completed it will automatically remove progress.
	 */
	public abstract void doBackgroundTask(View view);

	/**
	 * This method is just to notify user when everything has completed. It is
	 * called just onAnimationEnd not to interrupt animating. And it runs on uiThread.
	 */
	public abstract void onTaskFinished(View view);

}
