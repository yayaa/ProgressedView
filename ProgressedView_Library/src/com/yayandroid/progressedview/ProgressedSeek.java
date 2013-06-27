package com.yayandroid.progressedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ProgressedSeek extends View {

	private Paint bgPaint, pbPaint;
	private int bgColor = Color.DKGRAY;
	private int pbColor = Color.GRAY;
	private Rect bgRect;
	private Rect pbRect;
	private int progress = 0;
	private final int MAX_VALUE = 100;
	private final int MIN_VALUE = 0;

	public ProgressedSeek(Context context) {
		super(context);
		Init();
	}

	public ProgressedSeek(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init();
	}

	public ProgressedSeek(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init();
	}

	private void Init() {
		this.bgRect = new Rect(0, 0, 0, 0);
		this.pbRect = new Rect(0, 0, 0, 0);
		this.bgPaint = new Paint();
		this.pbPaint = new Paint();
	}

	public void setBackgroundColor(int color) {
		this.bgColor = color;
	}

	public void setProgressColor(int color) {
		this.pbColor = color;
	}

	public void setProgress(int value) {
		this.progress = value;
		postInvalidate();
	}

	private int getProgressWidth() {
		int width = getMeasuredWidth();
		if (progress >= MAX_VALUE)
			return width;

		if (progress <= MIN_VALUE)
			return MIN_VALUE;

		return ((width * progress) / MAX_VALUE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		bgRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
		bgPaint.setColor(bgColor);
		canvas.drawRect(bgRect, bgPaint);

		pbRect.set(0, 0, getProgressWidth(), getMeasuredHeight());
		pbPaint.setColor(pbColor);
		canvas.drawRect(pbRect, pbPaint);
	}

}
