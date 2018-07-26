package drag.example.com.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by mayong on 2018/7/25.
 * https://www.jianshu.com/p/e4d1f88ca922
 */

public class SimpleDragDemoView extends LinearLayout {
    private ViewDragHelper helper;
    private View child0;
    private int currentX;
    private int currentY;

    public SimpleDragDemoView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SimpleDragDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SimpleDragDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        helper = ViewDragHelper.create(this, new SimpleDragHelperCallback());
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_TOP);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        helper.shouldInterceptTouchEvent(ev);
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child0 = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        child0.layout(currentX, currentY, currentX + child0.getWidth(), currentY + child0.getHeight());
    }

    class SimpleDragHelperCallback extends ViewDragHelper.Callback {
        /**
         * 确定要滑动的view
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == child0;//滑动child0 ，只有这里返回true下面的其他方法才会被回调
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**
         * 子view位置发生变化的时候回调这个方法
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
//            super.onViewPositionChanged(changedView, left, top, dx, dy);
            currentX += dx;
            currentY += dy;
            if (currentX < 0) {//修正子view互动的x坐标，避免划出父布局
                currentX = 0;
            } else if (currentX > getWidth() - changedView.getWidth()) {
                currentX = getWidth() - changedView.getWidth();
            }

            if (currentY < 0) {//修正子view互动的y坐标，避免划出父布局
                currentY = 0;
            } else if (currentY > getHeight() - changedView.getHeight()) {
                currentY = getHeight() - changedView.getHeight();
            }
            requestLayout();//重新布局，改变子child0位置
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {

            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
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

        /**
         *
         * @param child
         * @param left 本次移动事件中横坐标可以到达的距离
         * @param dx  水平方向的偏移变化量
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        /**
         *
         * @param child
         * @param top 本次移动事件中竖直方向上可以到达的距离
         * @param dy 竖直方向上的变化量
         * @return
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//            return Math.max(Math.min(top, child0.getHeight()), 0);
            return top;
        }
    }
}
