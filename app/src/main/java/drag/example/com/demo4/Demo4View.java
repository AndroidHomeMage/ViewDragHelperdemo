package drag.example.com.demo4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author  :mayong
 * function:
 * date    :2019-10-25
 **/
public class Demo4View extends LinearLayout {

    private ViewDragHelper dragHelper;
    private View child0;
    private boolean isEdgeTouched;
    private boolean isOpen;//抽屉是否是打开状态

    public Demo4View(Context context) {
        super(context);
        init(context);
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        dragHelper = ViewDragHelper.create(this, new CustomViewDragCallback());
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child0 = getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    class CustomViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (xvel > 0) {
                openDrawer();
            } else if (xvel < 0) {
                close();
            } else if (child0.getLeft() > getWidth() / 3) {
                openDrawer();
            } else {
                close();
            }
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (isEdgeTouched||isOpen) {
                if (left < 0) {
                    left = 0;
                }
                if (left > getWidth() * 2 / 3) {
                    left = getWidth() * 2 / 3;
                }
                return left;
            } else {
                return super.clampViewPositionHorizontal(child, left, dx);
            }
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return getWidth() * 2 / 3;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                isEdgeTouched = false;
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            isEdgeTouched = true;

        }
    }

    private void openDrawer() {
        if (dragHelper.smoothSlideViewTo(child0, getWidth() * 2 / 3, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
            isOpen=true;
        }
    }

    private void close() {
        if (dragHelper.smoothSlideViewTo(child0, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
            isOpen=false;
        }
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }
}
