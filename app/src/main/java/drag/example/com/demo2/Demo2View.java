package drag.example.com.demo2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import drag.example.com.demo1.SimpleDragDemoView;

/**
 * Created by mayong on 2018/7/26.
 */

public class Demo2View extends RelativeLayout {
    private ViewDragHelper helper;
    private View child0;
    private View child1;
    private int currentX;

    public Demo2View(Context context) {
        super(context);
        init(context, null, 0);
    }

    public Demo2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public Demo2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        helper = ViewDragHelper.create(this, new DragCallback2());
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_TOP);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child0 = getChildAt(0);
        child1 = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        child1.layout(currentX, 0, getWidth(), child1.getHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        // TODO: 2018/7/26 note 这里是重点
        if (helper.continueSettling(true)) {//判断一下是否滑动完成，没完成就继续滑动
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
        }
    }

    class DragCallback2 extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == child1;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            currentX = left;
            if (currentX > 0.7 * getWidth()) {
                currentX = (int) (0.7 * getWidth());
            } else if (currentX < 0) {
                currentX = 0;
            }
            requestLayout();
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            // TODO: 2018/7/26 note 这里是重点
//           //手指松开的时候在这里完成惯性滑动
            int pWidth = getWidth();
            if (currentX > 0.3 * pWidth) {
                helper.settleCapturedViewAt((int) (0.7 * pWidth), 0);
            } else {

                helper.settleCapturedViewAt(0, 0);//调用这个方法，需要在父控件的computerScroll方法里面完成剩余滚动逻辑
            }
            requestLayout();//这个方法必须，否则可能不会继续完成惯性滑动

        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}
