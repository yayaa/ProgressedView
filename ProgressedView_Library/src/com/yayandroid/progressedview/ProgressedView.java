package com.yayandroid.progressedview;

import com.yayandroid.utility.AnimationHelper;
import com.yayandroid.utility.AnimationHelper.AnimDirection;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * 
 * @author Yahya BAYRAMOGLU
 * 
 */

public class ProgressedView extends RelativeLayout {

	private View child;
	private View progress;
	private ProgressedSeek seek;
	private ProgressBar pb;
	private View animatingSource, animatingTarget;
	private Interpolator interpolation = new LinearInterpolator();
	private LayoutInflater inflater;
	private ProgressedViewListener progressListener;
	private int backgroundColor = Color.DKGRAY;
	private int seekBackgroundColor = Color.DKGRAY;
	private int seekProgressColor = Color.GRAY;
	private boolean isTaskRunning = false;
	private boolean hasAttachedSuccessfully = false;
	private boolean isReversingAnimation = false;
	private boolean isAnimating = false;
	private boolean isOccupied = false;
	private boolean shouldReverseAnimation = true;
	private boolean shouldSourceRemainSteady = false;
	private boolean shouldBringTargetFront = false;
	private boolean enabled = true;

	private final String LOG = "ProgressedView";
	private final int DEFAULT_PROGRESS_LAYOUT = R.layout.default_progress;
	private final int DEFAULT_ANIMATION_DURATION = 300;
	private final AnimationType DEFAULT_ANIMATION_TYPE = AnimationType.SCALE_IN;
	private final AnimationType DEFAULT_REVERSE_ANIMATION_TYPE = AnimationType.SCALE_OUT;
	private int LIMIT_FOR_SMALL_PROGRESS = 100;
	private int LIMIT_FOR_BIG_PROGRESS = 250;
	private int progressLayoutId = DEFAULT_PROGRESS_LAYOUT;
	private int animationTime = DEFAULT_ANIMATION_DURATION;
	private ProgressType progressType = ProgressType.JUST_INDETERMINATE;
	private AnimationType animationType = DEFAULT_ANIMATION_TYPE;
	private AnimationType reversingAnimationType = DEFAULT_REVERSE_ANIMATION_TYPE;

	/**
	 * Enumeration to determine which progress style should perform on click
	 * 
	 * @author Yahya BAYRAMOGLU
	 * 
	 */
	public enum ProgressType {
		JUST_INDETERMINATE, JUST_SEEK, SEEK_AND_INDETERMINATE
	}

	/**
	 * Enumeration to determine which animation should perform on click
	 * 
	 * @author Yahya BAYRAMOGLU
	 * 
	 */
	public enum AnimationType {
		SWIPE_LEFT_TO_RIGHT, SWIPE_RIGHT_TO_LEFT, SWIPE_TOP_TO_BOTTOM, SWIPE_BOTTOM_TO_TOP, SCALE_IN, SCALE_OUT, ALPHA
	}

	/**
	 * Abstract class to notify ui when user clicks to childView, do task
	 * onBackground and then notify back ui onFinish. If loading progress has
	 * failed, then it will perform normal click event
	 * 
	 * @author Yahya BAYRAMOGLU
	 * 
	 */
	public abstract class ProgressSeekListener extends ProgressedViewListener {

		/**
		 * This method is to update seekBar on default progressLayout. It can be
		 * used just like in AsyncTask. If you are using default layout, it will
		 * automatically update seekBar
		 * 
		 * @param value
		 *            : integer value, which should be between 0 - 100
		 */
		public void publishProgressToSeek(int value) {
			SetProgress(value);
		}
	}

	public ProgressedView(Context context) {
		super(context);
		Initialize();
	}

	public ProgressedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Initialize();

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ProgressedView, 0, 0);
		this.progressLayoutId = ta.getResourceId(
				R.styleable.ProgressedView_progressLayout,
				DEFAULT_PROGRESS_LAYOUT);
		this.animationTime = ta.getInteger(
				R.styleable.ProgressedView_animationTime,
				DEFAULT_ANIMATION_DURATION);
		this.shouldSourceRemainSteady = ta.getBoolean(
				R.styleable.ProgressedView_remainSteady, false);
		this.shouldBringTargetFront = ta.getBoolean(
				R.styleable.ProgressedView_bringTargetFront, false);
		this.backgroundColor = ta
				.getColor(R.styleable.ProgressedView_defaultBackgroundColor,
						Color.DKGRAY);
		this.seekBackgroundColor = ta.getColor(
				R.styleable.ProgressedView_defaultSeekBackgroundColor,
				Color.DKGRAY);
		this.seekProgressColor = ta
				.getColor(R.styleable.ProgressedView_defaultSeekProgressColor,
						Color.GRAY);
		this.enabled = ta.getBoolean(R.styleable.ProgressedView_progressEnable,
				true);

		AnimationType[] anims = AnimationType.values();

		int animType = ta.getInteger(R.styleable.ProgressedView_animationType,
				-1);
		if (animType != -1) {
			this.animationType = anims[animType];
		}

		int reverseType = ta.getInteger(
				R.styleable.ProgressedView_reversingAnimationType, -1);
		if (reverseType != -1) {
			this.reversingAnimationType = anims[reverseType];
			this.shouldReverseAnimation = false;
		}

		ProgressType[] progTypes = ProgressType.values();
		int pType = ta.getInteger(R.styleable.ProgressedView_progressType, -1);
		if (pType != -1) {
			this.progressType = progTypes[pType];
		}

		int interpolationType = ta.getInteger(
				R.styleable.ProgressedView_interpolation, -1);
		GetInterpolation(interpolationType);

		ta.recycle();
	}

	public ProgressedView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Initialize();
	}

	private void Initialize() {
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		setClipChildren(true);

		float density = GetScreenDensity();
		LIMIT_FOR_SMALL_PROGRESS *= density;
		LIMIT_FOR_BIG_PROGRESS *= density;
	}

	private float GetScreenDensity() {
		Display display = ((WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.density;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if ((!isOccupied && getChildCount() > 1)
				|| (isOccupied && getChildCount() > 2))
			throw new IllegalStateException(
					"ProgressedView can host only one child");

		if (getChildCount() == 1)
			applyProgress(getChildAt(0), animationType, progressLayoutId);

	}

	/**
	 * 
	 * Can put a view inside progressedView if there was no other view yet
	 * 
	 * @param view
	 *            : View to show progress when clicked
	 * @return True if there was no error and progress view was successfully
	 *         applied, false otherwise
	 */
	public boolean applyProgress(View view) {
		return this.isOccupied = apply(view, null, progressLayoutId);
	}

	/**
	 * 
	 * Can put a view inside progressedView if there was no other view yet
	 * 
	 * @param view
	 *            : View to show progress when clicked
	 * @param animationType
	 *            : AnimationType to determine how to appear progress
	 * @return True if there was no error and progress view was successfully
	 *         applied, false otherwise
	 */
	public boolean applyProgress(View view, AnimationType animationType) {
		return this.isOccupied = apply(view, animationType, progressLayoutId);
	}

	/**
	 * 
	 * Can put a view inside progressedView if there was no other view yet
	 * 
	 * @param view
	 *            : View to show progress when clicked
	 * @param animationType
	 *            : AnimationType to determine how to appear progress
	 * @param progressLayout
	 *            : Custom progress layout to display when user clicks the
	 *            source view
	 * @return True if there was no error and progress view was successfully
	 *         applied, false otherwise
	 */
	public boolean applyProgress(View view, AnimationType animationType,
			int progressLayout) {
		return this.isOccupied = apply(view, animationType, progressLayout);
	}

	/** Call to remove progress by reversing animation */
	public void removeProgress() {
		isReversingAnimation = true;
		switchViewsWithAnimation(progress, child, reversingAnimationType);
	}

	/**
	 * 
	 * Can put a view inside progressedView if there was no other view yet
	 * 
	 * @param view
	 *            : View to show progress when clicked
	 * @param animationType
	 *            : AnimationType to determine how to appear progress
	 * @param progressLayout
	 *            : Custom progress layout to display when user clicks the
	 *            source view
	 * @return True if there was no error and progress view was successfully
	 *         applied, false otherwise
	 */
	private boolean apply(final View view, final AnimationType animationType,
			int progressLayout) {

		// If it is already occupied by another view, then return false to
		// notify user that progress didn't get applied
		if (isOccupied) {
			return false;
			// throw new
			// IllegalStateException("ProgressedView already has a child");
		}

		// Store values
		this.child = view;
		this.progressLayoutId = progressLayout;
		this.progress = inflater.inflate(progressLayout, this, false);
		if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT) {
			progress.setBackgroundColor(backgroundColor);
		}
		this.animationType = animationType;
		if (shouldReverseAnimation)
			this.reversingAnimationType = getReverseAnimationType(animationType);
		UpdateSeekBar();

		// Set sourceView's clickListener
		view.setOnClickListener(progressedClickListener);

		// Add a onPreDrawListener to get view's sizes whenever attach to window
		ViewTreeObserver observer = view.getViewTreeObserver();
		if (observer != null && observer.isAlive()) {
			observer.addOnPreDrawListener(new OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {

					// Remove onPreDrawListener
					view.getViewTreeObserver().removeOnPreDrawListener(this);

					// Get source view's layoutParams set its width and height
					// to progress view, so progress will place in same area
					// with source view and it'll have same size of source view
					LayoutParams lp = (LayoutParams) view.getLayoutParams();
					lp.width = view.getWidth();
					lp.height = view.getHeight();

					// If progressLayout is not custom, then calculate and
					// determine progressBar size up to view's width and height
					if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT)
						CalculateProgressSize(view.getWidth(), view.getHeight());

					// Finally add progressView also to parent of sourceView,
					// and set progressView invisible by default
					addView(progress, lp);
					progress.setVisibility(View.GONE);
					UpdateSeekBar();

					hasAttachedSuccessfully = true;
					return true;
				}

			});

		} else {
			Log.e(LOG,
					"Error! Progress couldn't get attached to view because of ViewTreeObserver was not alive.");
			return false;
		}

		return true;
	}

	/**
	 * Decides which progressBar should be visible
	 * 
	 * @param parentWidth
	 *            : width of parent which holds progressBar
	 * @param parentHeight
	 *            : height of parent which holds progressBar
	 */
	private void CalculateProgressSize(int parentWidth, int parentHeight) {
		ProgressBar pbSmall = (ProgressBar) progress.findViewById(R.id.pbSmall);
		ProgressBar pb = (ProgressBar) progress.findViewById(R.id.pb);
		ProgressBar pbBig = (ProgressBar) progress.findViewById(R.id.pbBig);

		if (parentWidth <= LIMIT_FOR_SMALL_PROGRESS
				|| parentHeight <= LIMIT_FOR_SMALL_PROGRESS)
			this.pb = pbSmall;
		else if (parentWidth <= LIMIT_FOR_BIG_PROGRESS
				|| parentHeight <= LIMIT_FOR_BIG_PROGRESS)
			this.pb = pb;
		else
			this.pb = pbBig;

		if (progressType != ProgressType.JUST_SEEK)
			this.pb.setVisibility(View.VISIBLE);
	}

	/** Returns reversing form of given animationType */
	private AnimationType getReverseAnimationType(AnimationType animType) {
		if (animType == null)
			return null;

		AnimationType anim = null;
		switch (animType) {
		case ALPHA:
			anim = AnimationType.ALPHA;
			break;
		case SCALE_IN:
			anim = AnimationType.SCALE_OUT;
			break;
		case SCALE_OUT:
			anim = AnimationType.SCALE_IN;
			break;
		case SWIPE_BOTTOM_TO_TOP:
			anim = AnimationType.SWIPE_TOP_TO_BOTTOM;
			break;
		case SWIPE_TOP_TO_BOTTOM:
			anim = AnimationType.SWIPE_BOTTOM_TO_TOP;
			break;
		case SWIPE_LEFT_TO_RIGHT:
			anim = AnimationType.SWIPE_RIGHT_TO_LEFT;
			break;
		case SWIPE_RIGHT_TO_LEFT:
			anim = AnimationType.SWIPE_LEFT_TO_RIGHT;
			break;
		}
		return anim;
	}

	/**
	 * Returns interpolation up to given type id
	 */
	private void GetInterpolation(int type) {
		switch (type) {
		case 0:
			interpolation = new AccelerateInterpolator();
			break;
		case 1:
			interpolation = new DecelerateInterpolator();
			break;
		case 2:
			interpolation = new AccelerateDecelerateInterpolator();
			break;
		case 3:
			interpolation = new AnticipateInterpolator();
			break;
		case 4:
			interpolation = new OvershootInterpolator();
			break;
		case 5:
			interpolation = new AnticipateOvershootInterpolator();
			break;
		case 6:
			interpolation = new CycleInterpolator(1);
			break;
		case 7:
			interpolation = new BounceInterpolator();
			break;
		default:
			interpolation = new LinearInterpolator();
			break;
		}
	}

	/**
	 * Set seekBar's progress if this view currently is used with default
	 * progress layout
	 * 
	 * @param value
	 *            : integer value to set progress
	 */
	private void SetProgress(int value) {
		if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT) {
			if (seek == null)
				this.seek = (ProgressedSeek) progress.findViewById(R.id.seek);
			seek.setProgress(value);
		}
	}

	/**
	 * Update default progress layout according to progressType
	 */
	private void UpdateProgressView() {
		if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT && progress != null) {
			if (seek == null)
				this.seek = (ProgressedSeek) progress.findViewById(R.id.seek);

			switch (progressType) {
			case JUST_INDETERMINATE: {
				if (this.pb != null)
					this.pb.setVisibility(View.VISIBLE);
				if (this.seek != null)
					this.seek.setVisibility(View.GONE);
				break;
			}
			case JUST_SEEK: {
				if (this.pb != null)
					this.pb.setVisibility(View.GONE);
				if (this.seek != null)
					this.seek.setVisibility(View.VISIBLE);
				break;
			}
			case SEEK_AND_INDETERMINATE: {
				if (this.pb != null)
					this.pb.setVisibility(View.VISIBLE);
				if (this.seek != null)
					this.seek.setVisibility(View.VISIBLE);
				break;
			}
			}
		}
	}

	/**
	 * To update default seekBar's properties
	 */
	private void UpdateSeekBar() {
		if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT && progress != null) {
			if (seek == null)
				this.seek = (ProgressedSeek) progress.findViewById(R.id.seek);
			if (seek != null) {
				if (this.progressType != ProgressType.JUST_INDETERMINATE) {
					seek.setBackgroundColor(seekBackgroundColor);
					seek.setProgressColor(seekProgressColor);
					seek.setVisibility(View.VISIBLE);
				} else {
					seek.setVisibility(View.GONE);
				}
			}
		}
	}

	/** Click listener to handle click event on given view */
	private OnClickListener progressedClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			if (enabled) {
				// If currently there is no task running and no animation
				// performing, only then view can be clicked!
				if (!isTaskRunning && !isAnimating) {
					if (progress != null && hasAttachedSuccessfully) {
						SetProgress(0);
						switchViewsWithAnimation(view, progress, animationType);
					} else {
						if (getProgressListener() != null)
							getProgressListener().onClick(child);
					}
				}
			} else {
				// ProgressedView is not enabled, so send click action directly
				// to listener and do nothing
				if (getProgressListener() != null)
					getProgressListener().onClick(child);
			}
		}

	};

	/** Animation listener to notify ui whenever needed */
	private AnimationListener progressedAnimListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			if (isAnimating) {
				animatingTarget.setVisibility(View.VISIBLE);
				animatingTarget.clearAnimation();

				animatingSource.setVisibility(View.GONE);
				animatingSource.clearAnimation();

				if (isReversingAnimation) {
					if (getProgressListener() != null)
						getProgressListener().onTaskFinished(child);
					isReversingAnimation = false;
				} else {
					new Task().execute();
				}

				isAnimating = false;
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationStart(Animation animation) {
			isAnimating = true;
		}

	};

	/**
	 * Switch source and target view according to given animation type. If
	 * animation type is null, then just switch views' visibilities
	 * 
	 * @param source
	 *            : Currently visible view, this will be invisible after
	 *            animating
	 * @param target
	 *            : Currently invisible view, this will be visible after
	 *            animating
	 * @param anim
	 *            : Animation type to determine which animation should perform
	 */
	private void switchViewsWithAnimation(View source, View target,
			AnimationType anim) {
		this.animatingSource = source;
		this.animatingTarget = target;

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		if (anim != null) {

			animatingTarget.setVisibility(View.VISIBLE);
			animatingSource.setVisibility(View.VISIBLE);

			if (shouldBringTargetFront)
				bringChildToFront(animatingTarget);

			switch (anim) {
			case SWIPE_LEFT_TO_RIGHT: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTranslation(source, AnimDirection.X,
							0, width, animationTime, interpolation,
							progressedAnimListener);
				AnimationHelper.StartTranslation(target, AnimDirection.X,
						-width, 0, animationTime, interpolation,
						progressedAnimListener);
				break;
			}
			case SWIPE_RIGHT_TO_LEFT: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTranslation(source, AnimDirection.X,
							0, -width, animationTime, interpolation,
							progressedAnimListener);
				AnimationHelper.StartTranslation(target, AnimDirection.X,
						width, 0, animationTime, interpolation,
						progressedAnimListener);
				break;
			}
			case SWIPE_BOTTOM_TO_TOP: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTranslation(source, AnimDirection.Y,
							0, -height, animationTime, interpolation,
							progressedAnimListener);
				AnimationHelper.StartTranslation(target, AnimDirection.Y,
						height, 0, animationTime, interpolation,
						progressedAnimListener);
				break;
			}
			case SWIPE_TOP_TO_BOTTOM: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTranslation(source, AnimDirection.Y,
							0, height, animationTime, interpolation,
							progressedAnimListener);
				AnimationHelper.StartTranslation(target, AnimDirection.Y,
						-height, 0, animationTime, interpolation,
						progressedAnimListener);
				break;
			}
			case SCALE_IN: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTogether(source, AnimationHelper
							.Scale(1, 0.5f, animationTime, interpolation,
									progressedAnimListener), AnimationHelper
							.Alpha(1.0f, 0, animationTime));
				AnimationHelper.StartTogether(target, AnimationHelper.Scale(
						1.5f, 1, animationTime, interpolation), AnimationHelper
						.Alpha(0, 1.0f, animationTime));
				break;
			}
			case SCALE_OUT: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartTogether(source, AnimationHelper
							.Scale(1, 1.5f, animationTime, interpolation,
									progressedAnimListener), AnimationHelper
							.Alpha(1.0f, 0, animationTime));
				AnimationHelper.StartTogether(target, AnimationHelper.Scale(
						0.5f, 1, animationTime, interpolation), AnimationHelper
						.Alpha(0, 1.0f, animationTime));
				break;
			}
			case ALPHA: {
				if (shouldSourceRemainSteady)
					StartSteadyAnimation(source);
				else
					AnimationHelper.StartAlpha(source, 1.0f, 0, animationTime,
							interpolation, progressedAnimListener);
				AnimationHelper.StartAlpha(target, 0, 1.0f, animationTime,
						interpolation);
				break;
			}
			}

		} else {
			// If there is no animation willing, then just switch views
			animatingTarget.setVisibility(View.VISIBLE);
			animatingSource.setVisibility(View.GONE);
		}
	}

	/**
	 * Animation to make given view remain steady
	 * 
	 * @param view
	 *            : view to apply steadyAnimation
	 */
	private void StartSteadyAnimation(View view) {
		AnimationHelper.StartAlpha(view, 1.0f, 1.0f, animationTime,
				interpolation, progressedAnimListener);
	}

	/**
	 * 
	 * AsyncTask to do stuff on background and then remove progress on given
	 * child
	 * 
	 * @author Yahya BAYRAMOGLU
	 * 
	 */
	private class Task extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isTaskRunning = true;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			if (getProgressListener() != null)
				getProgressListener().doBackgroundTask(child);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			isTaskRunning = false;
			removeProgress();
		}

	}

	/**
	 * @category Getter And Setter
	 */

	public ProgressedViewListener getProgressListener() {
		return progressListener;
	}

	public void setProgressListener(ProgressedViewListener progressListener) {
		this.progressListener = progressListener;
	}

	public View getChild() {
		return child;
	}

	public View getProgress() {
		return progress;
	}

	public boolean isTaskRunning() {
		return isTaskRunning;
	}

	public boolean hasAttachedSuccessfully() {
		return hasAttachedSuccessfully;
	}

	public boolean isReversingAnimation() {
		return isReversingAnimation;
	}

	public boolean isAnimating() {
		return isAnimating;
	}

	public int getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(int animationTime) {
		this.animationTime = animationTime;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}

	public void setReverseAnimationType(AnimationType reversingAnimationType) {
		this.reversingAnimationType = reversingAnimationType;
		this.shouldReverseAnimation = false;
	}

	public void setInterpolation(Interpolator interpolation) {
		this.interpolation = interpolation;
	}

	public void setInterpolation(int interpolationReference) {
		GetInterpolation(interpolationReference);
	}

	public void setRemainSteady(boolean shouldSourceRemainSteady) {
		this.shouldSourceRemainSteady = shouldSourceRemainSteady;
	}

	public void setBringTargetFront(boolean shouldBringTargetFront) {
		this.shouldBringTargetFront = shouldBringTargetFront;
	}

	public ProgressType getProgressType() {
		return progressType;
	}

	public void setProgressType(ProgressType progressType) {
		this.progressType = progressType;

		UpdateProgressView();
	}

	public void setProgressLayoutId(int progressLayoutId) {
		this.progressLayoutId = progressLayoutId;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;

		if (progressLayoutId == DEFAULT_PROGRESS_LAYOUT && progress != null)
			progress.setBackgroundColor(backgroundColor);
	}

	public void setSeekBackgroundColor(int seekBackgroundColor) {
		this.seekBackgroundColor = seekBackgroundColor;
		UpdateSeekBar();
	}

	public void setSeekProgressColor(int seekProgressColor) {
		this.seekProgressColor = seekProgressColor;
		UpdateSeekBar();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
