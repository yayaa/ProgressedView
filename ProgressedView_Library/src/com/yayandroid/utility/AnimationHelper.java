package com.yayandroid.utility;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * @author Yahya BAYRAMOGLU
 * 
 */
public class AnimationHelper {

	public static final int SHORT = 300;
	public static final int LONG = 500;

	/**
	 * Enumeration to determine direction of animation
	 * 
	 * @author Yahya BAYRAMOGLU
	 * 
	 */
	public enum AnimDirection {
		X, Y, BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN, RIGHT_OUT, RIGHT_IN
	}

	/**
	 * Returns translate target fully from given direction
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int duration) {
		return Translate(target, direction, duration, null, null);
	}

	/**
	 * Translate target fully from given direction
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int duration) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, duration));
	}

	/**
	 * Returns translate target fully from given direction with interpolation
	 * 
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int duration, Interpolator interpolation) {
		return Translate(target, direction, duration, interpolation, null);
	}

	/**
	 * Translate target fully from given direction with interpolation
	 * 
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int duration, Interpolator interpolation) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, duration,
				interpolation));
	}

	/**
	 * Returns translate target fully from given direction with interpolation
	 * and by sending callback to listener
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int duration, Interpolator interpolation, AnimationListener listener) {

		Animation animation = null;
		switch (direction) {
		case BOTTOM_OUT:
			animation = Translate(target, 0, 0, 0, target.getMeasuredHeight(),
					duration, interpolation, listener);
			break;
		case TOP_OUT:
			animation = Translate(target, 0, 0, 0, -target.getMeasuredHeight(),
					duration, interpolation, listener);
			break;
		case BOTTOM_IN:
			animation = Translate(target, 0, 0, target.getMeasuredHeight(), 0,
					duration, interpolation, listener);
			break;
		case TOP_IN:
			animation = Translate(target, 0, 0, -target.getMeasuredHeight(), 0,
					duration, interpolation, listener);
			break;
		case LEFT_OUT:
			animation = Translate(target, 0, -target.getMeasuredWidth(), 0, 0,
					duration, interpolation, listener);
			break;
		case RIGHT_OUT:
			animation = Translate(target, 0, target.getMeasuredWidth(), 0, 0,
					duration, interpolation, listener);
			break;
		case LEFT_IN:
			animation = Translate(target, -target.getMeasuredWidth(), 0, 0, 0,
					duration, interpolation, listener);
			break;
		case RIGHT_IN:
			animation = Translate(target, target.getMeasuredWidth(), 0, 0, 0,
					duration, interpolation, listener);
			break;
		default:
			break;
		}
		return animation;
	}

	/**
	 * Translate target fully from given direction with interpolation and by
	 * sending callback to listener
	 * 
	 * IMPORTANT!: target view needs to be attached to window first, otherwise
	 * there will no be any animation. (e.g.: Target's visibility should be
	 * invisible instead of gone)
	 * 
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : Direction to translate target from side to side (e.g.:
	 *            BOTTOM_OUT, BOTTOM_IN, TOP_OUT, TOP_IN, LEFT_OUT, LEFT_IN,
	 *            RIGHT_OUT, RIGHT_IN)
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int duration, Interpolator interpolation, AnimationListener listener) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, duration,
				interpolation, listener));
	}

	/**
	 * Returns translate target from given position to given one in direction (X
	 * or Y)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int from, int to, int duration) {
		return Translate(target, direction, from, to, duration, null, null);
	}

	/**
	 * Translate target from given position to given one in direction (X or Y)
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int from, int to, int duration) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, from, to, duration));
	}

	/**
	 * Returns translate target from given position to given one in direction (X
	 * or Y) with interpolation
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int from, int to, int duration, Interpolator interpolation) {
		return Translate(target, direction, from, to, duration, interpolation,
				null);
	}

	/**
	 * Translate target from given position to given one in direction (X or Y)
	 * with interpolation
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int from, int to, int duration, Interpolator interpolation) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, from, to, duration,
				interpolation));
	}

	/**
	 * Returns translate target from given position to given one in direction (X
	 * or Y) with interpolation and by sending callback to listener
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Translate(View target, AnimDirection direction,
			int from, int to, int duration, Interpolator interpolation,
			AnimationListener listener) {
		Animation animation = null;
		switch (direction) {
		case X:
			animation = Translate(target, from, to, 0, 0, duration,
					interpolation, listener);
			break;
		case Y:
			animation = Translate(target, 0, 0, from, to, duration,
					interpolation, listener);
			break;
		default:
			break;
		}
		return animation;
	}

	/**
	 * Translate target from given position to given one in direction (X or Y)
	 * with interpolation and by sending callback to listener
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param direction
	 *            : AnimDirection value to determine the target should animate
	 *            in a direction of X or Y
	 * @param from
	 *            : Integer value to determine where target should begin to
	 *            translate for given direction
	 * @param to
	 *            :Integer value to determine where target should finish
	 *            translating for given direction
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartTranslation(View target, AnimDirection direction,
			int from, int to, int duration, Interpolator interpolation,
			AnimationListener listener) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, direction, from, to, duration,
				interpolation, listener));
	}

	/**
	 * Returns translate target from given (X, Y) to given (X, Y) with
	 * interpolation and by sending callback to given listener
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param fromX
	 *            : Integer value to determine where target should begin to
	 *            translate for X coordinate
	 * @param toX
	 *            : Integer value to determine where target should finish
	 *            translating for X coordinate
	 * @param fromY
	 *            : Integer value to determine where target should begin to
	 *            translate for Y coordinate
	 * @param toY
	 *            : Integer value to determine where target should finish
	 *            translating for Y coordinate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Translate(View target, int fromX, int toX,
			int fromY, int toY, int duration, Interpolator interpolation,
			AnimationListener listener) {
		TranslateAnimation animation = new TranslateAnimation(fromX, toX,
				fromY, toY);
		animation.setDuration(duration);

		if (interpolation != null)
			animation.setInterpolator(interpolation);

		if (listener != null) {
			animation.setAnimationListener(listener);
		}

		return animation;
	}

	/**
	 * Translate target from given (X, Y) to given (X, Y) with interpolation and
	 * by sending callback to given listener
	 * 
	 * @param target
	 *            : Target object to apply translate animation
	 * @param fromX
	 *            : Integer value to determine where target should begin to
	 *            translate for X coordinate
	 * @param toX
	 *            : Integer value to determine where target should finish
	 *            translating for X coordinate
	 * @param fromY
	 *            : Integer value to determine where target should begin to
	 *            translate for Y coordinate
	 * @param toY
	 *            : Integer value to determine where target should finish
	 *            translating for Y coordinate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartTranslation(View target, int fromX, int toX,
			int fromY, int toY, int duration, Interpolator interpolation,
			AnimationListener listener) {
		target.setVisibility(View.VISIBLE);
		target.clearAnimation();
		target.startAnimation(Translate(target, fromX, toX, fromY, toY,
				duration, interpolation, listener));
	}

	/**
	 * Returns an alpha animation from given opacity to given one
	 * 
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 */
	public static Animation Alpha(float from, float to, int duration) {
		return Alpha(from, to, duration, null);
	}

	/**
	 * Start an alpha animation from given opacity to given one
	 * 
	 * @param target
	 *            : Target object to apply alpha animation
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 */
	public static void StartAlpha(View target, float from, float to,
			int duration) {
		target.clearAnimation();
		target.startAnimation(Alpha(from, to, duration));
	}

	/**
	 * Returns an alpha animation from given opacity to given one with
	 * interpolation, but without callback
	 * 
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static Animation Alpha(float from, float to, int duration,
			Interpolator interpolation) {
		return Alpha(from, to, duration, interpolation, null);
	}

	/**
	 * Start an alpha animation from given opacity to given one with
	 * interpolation, but without callback
	 * 
	 * @param target
	 *            : Target object to apply alpha animation
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static void StartAlpha(View target, float from, float to,
			int duration, Interpolator interpolation) {
		target.clearAnimation();
		target.startAnimation(Alpha(from, to, duration, interpolation));
	}

	/**
	 * Returns an alpha animation on target object from given opacity to given
	 * one with interpolation and send callback to listener
	 * 
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Alpha(float from, float to, int duration,
			Interpolator interpolation, AnimationListener listener) {
		AlphaAnimation animation = new AlphaAnimation(from, to);
		animation.setDuration(duration);

		if (interpolation != null)
			animation.setInterpolator(interpolation);

		if (listener != null) {
			animation.setAnimationListener(listener);
		}

		return animation;
	}

	/**
	 * Start an alpha animation on target object from given opacity to given one
	 * with interpolation and send callback to listener
	 * 
	 * @param target
	 *            : Target object to apply alpha animation
	 * @param from
	 *            : Float value to determine opacity at the beginning
	 * @param to
	 *            : Float value to determine opacity at the end
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartAlpha(View target, float from, float to,
			int duration, Interpolator interpolation, AnimationListener listener) {
		target.clearAnimation();
		target.startAnimation(Alpha(from, to, duration, interpolation, listener));
	}

	/**
	 * Returns scale target object from its center to both way, until it reaches
	 * to given value from given one
	 * 
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 */
	public static Animation Scale(float from, float to, int duration) {
		return Scale(from, to, duration, null);
	}

	/**
	 * Scale target object from its center to both way, until it reaches to
	 * given value from given one
	 * 
	 * @param target
	 *            : Target object to apply scale animation
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 */
	public static void StartScale(View target, float from, float to,
			int duration) {
		target.clearAnimation();
		target.startAnimation(Scale(from, to, duration));
	}

	/**
	 * Returns scale target object from its center to both way with
	 * interpolation, until it reaches to given value from given one
	 * 
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static Animation Scale(float from, float to, int duration,
			Interpolator interpolation) {
		return Scale(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f, duration, interpolation, null);
	}

	/**
	 * Scale target object from its center to both way with interpolation, until
	 * it reaches to given value from given one
	 * 
	 * @param target
	 *            : Target object to apply scale animation
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 */
	public static void StartScale(View target, float from, float to,
			int duration, Interpolator interpolation) {
		target.clearAnimation();
		target.startAnimation(Scale(from, to, duration, interpolation));
	}

	/**
	 * Returns scale target object from its center to both way with
	 * interpolation, until it reaches to given value from given one
	 * 
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Scale(float from, float to, int duration,
			Interpolator interpolation, AnimationListener listener) {
		return Scale(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f, duration, interpolation,
				listener);
	}

	/**
	 * Scale target object from its center to both way with interpolation, until
	 * it reaches to given value from given one
	 * 
	 * @param target
	 *            : Target object to apply scale animation
	 * @param from
	 *            : Float value to determine animation initial scale for both
	 *            coordinates
	 * @param to
	 *            : Float value to determine animation scale for both
	 *            coordinates
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartScale(View target, float from, float to,
			int duration, Interpolator interpolation, AnimationListener listener) {
		target.clearAnimation();
		target.startAnimation(Scale(from, to, duration, interpolation, listener));
	}

	/**
	 * Returns scale target object with interpolation up to given X-Y pairs and
	 * dispatch callback with AnimationListener
	 * 
	 * @param fromX
	 *            : Float value to determine animation initial scale for X
	 *            coordinate
	 * @param toX
	 *            : Float value to determine animation scale for X coordinate
	 * @param fromY
	 *            : Float value to determine animation initial scale for Y
	 *            coordinate
	 * @param toY
	 *            : Float value to determine animation scale for Y coordinate
	 * @param pivotXType
	 *            : Type to determine whether target should scale up to itself
	 *            or parent and so on
	 * @param pivotXValue
	 *            : Float value to determine target needs to start scaling from
	 *            X coordinate
	 * @param pivotYType
	 *            : Type to determine whether target should scale up to itself
	 *            or parent and so on
	 * @param pivotYValue
	 *            : Float value to determine target needs to start scaling from
	 *            Y coordinate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static Animation Scale(float fromX, float toX, float fromY,
			float toY, int pivotXType, float pivotXValue, int pivotYType,
			float pivotYValue, int duration, Interpolator interpolation,
			AnimationListener listener) {
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY,
				pivotXType, pivotXValue, pivotYType, pivotYValue);
		animation.setDuration(duration);

		if (interpolation != null)
			animation.setInterpolator(interpolation);

		if (listener != null) {
			animation.setAnimationListener(listener);
		}

		return animation;
	}

	/**
	 * Scale target object with interpolation up to given X-Y pairs and dispatch
	 * callback with AnimationListener
	 * 
	 * @param target
	 *            : Target object to apply scale animation
	 * @param fromX
	 *            : Float value to determine animation initial scale for X
	 *            coordinate
	 * @param toX
	 *            : Float value to determine animation scale for X coordinate
	 * @param fromY
	 *            : Float value to determine animation initial scale for Y
	 *            coordinate
	 * @param toY
	 *            : Float value to determine animation scale for Y coordinate
	 * @param pivotXType
	 *            : Type to determine whether target should scale up to itself
	 *            or parent and so on
	 * @param pivotXValue
	 *            : Float value to determine target needs to start scaling from
	 *            X coordinate
	 * @param pivotYType
	 *            : Type to determine whether target should scale up to itself
	 *            or parent and so on
	 * @param pivotYValue
	 *            : Float value to determine target needs to start scaling from
	 *            Y coordinate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Animation interpolation
	 * @param listener
	 *            : Animation listener to send callback
	 */
	public static void StartScale(View target, float fromX, float toX,
			float fromY, float toY, int pivotXType, float pivotXValue,
			int pivotYType, float pivotYValue, int duration,
			Interpolator interpolation, AnimationListener listener) {
		target.clearAnimation();
		target.startAnimation(Scale(fromX, toX, fromY, toY, pivotXType,
				pivotXValue, pivotYType, pivotYValue, duration, interpolation,
				listener));
	}

	/**
	 * Starts given animations all together
	 * 
	 * @param target
	 *            : Source view to animate 
	 * @param anims
	 *            : Animations to run together
	 */
	public static void StartTogether(View target, Animation... anims) {
		StartTogether(target, -1, null, null, anims);
	}

	/**
	 * Starts given animations all together
	 * 
	 * @param target
	 *            : Source view to animate
	 * @param duration
	 *            : Animation duration 
	 * @param anims
	 *            : Animations to run together
	 */
	public static void StartTogether(View target, int duration,
			Animation... anims) {
		StartTogether(target, duration, null, null, anims);
	}

	/**
	 * Starts given animations all together
	 * 
	 * @param target
	 *            : Source view to animate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Interpolation to apply all animations 
	 * @param anims
	 *            : Animations to run together
	 */
	public static void StartTogether(View target, int duration,
			Interpolator interpolation, Animation... anims) {
		StartTogether(target, duration, interpolation, null, anims);
	}

	/**
	 * Starts given animations all together
	 * 
	 * @param target
	 *            : Source view to animate
	 * @param duration
	 *            : Animation duration
	 * @param interpolation
	 *            : Interpolation to apply all animations
	 * @param listener
	 *            : Animation listener to get notified on necessary
	 * @param anims
	 *            : Animations to run together
	 */
	public static void StartTogether(View target, int duration,
			Interpolator interpolation, AnimationListener listener,
			Animation... anims) {
		AnimationSet set = new AnimationSet(true);
		for (int i = 0; i < anims.length; i++)
			set.addAnimation(anims[i]);

		if (duration != -1)
			set.setDuration(duration);
		if (interpolation != null)
			set.setInterpolator(interpolation);
		if (listener != null)
			set.setAnimationListener(listener);

		target.clearAnimation();
		target.startAnimation(set);
	}

}
