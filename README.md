ProgressedView
==============

ProgressedView provides you to show progress to user, when clicks any view. After done your task at background reverse it back to view again. This view extends RelativeLayout, so it easily implement in xml or code. 

ProgressedView can host only one child. Becuase it adds another view, which contains progressbar and seekbar, just with exact layoutparams with source view. 

You can easily set animation types for changing view to progress, and reversing back to view. The animations don't have to be same, you can choose them seperately. And if you choose, only view to progress animation then library will decide what reversing animation should be.

Supported animation types:

```java
public enum AnimationType {
  	SWIPE_LEFT_TO_RIGHT, SWIPE_RIGHT_TO_LEFT, SWIPE_TOP_TO_BOTTOM, SWIPE_BOTTOM_TO_TOP, SCALE_IN, SCALE_OUT, ALPHA
}
```

And also possible to change progress style, which is more limited for now:

```java
public enum ProgressType {
  	JUST_INDETERMINATE, JUST_SEEK, SEEK_AND_INDETERMINATE
}
```

You can also specify interpolations which they are defined as attributes so you can simply implement them from xml, or you can set them from code as well. Used interpolatins:

AccelerateInterpolator, 
DecelerateInterpolator, 
AccelerateDecelerateInterpolator, 
AnticipateInterpolator, 
OvershootInterpolator, 
AnticipateOvershootInterpolator, 
BounceInterpolator, 
LinearInterpolator (As default), 
CycleInterpolator (Not so effective for this library)

Implementation
--------------


Demo is available on [GooglePlayStore] [GooglePlayStoreUrl]
[GooglePlayStoreUrl]: https://play.google.com/store/apps/details?id=com.yayandroid.progressedview
