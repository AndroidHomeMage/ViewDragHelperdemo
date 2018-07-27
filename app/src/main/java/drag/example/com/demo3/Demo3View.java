package drag.example.com.demo3;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by mayong on 2018/7/27.
 */

public class Demo3View extends RelativeLayout {


    private View child1;//真正滑动的view
    private Scroller scroller0;
    private View child0;
    private Scroller scroller1;

    public Demo3View(Context context) {
        super(context);
        init(context, null, 0);
    }

    public Demo3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public Demo3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        scroller0 = new Scroller(getContext(), new AccelerateInterpolator());
        scroller1 = new Scroller(getContext(),new DecelerateInterpolator());
    }

    /**
     * 互换两个子view的位置
     */
    public void startScroll() {
        int dx = child1.getLeft() - child0.getLeft();
        int dy = child1.getTop() - child0.getTop();
        scroller0.startScroll( child0.getLeft(), child0.getTop(), dx,dy, (int) (1000*1.5));// TODO: 2018/7/27治理调用stratScroll开始滑动，滑动的位置会在computeScroll里面回调
        scroller1.startScroll(child1.getLeft(),child1.getTop(),-dx,-dy,1000*1);
        invalidate();//必须调用，不然不会回调computerScroll方法
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child1 = getChildAt(1);
        child0 = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (scroller0.computeScrollOffset()) {
            child0.offsetLeftAndRight(scroller0.getCurrX()-child0.getLeft());
            child0.offsetTopAndBottom(scroller0.getCurrY()-child0.getTop());
            ViewCompat.postInvalidateOnAnimation(this);// TODO: 2018/7/27 这里也必须调用否则不会再次调用computerScroll
        }else {
            scroller0.forceFinished(true);
        }
        if (scroller1.computeScrollOffset()) {
            child1.offsetLeftAndRight(scroller1.getCurrX()-child1.getLeft());
            child1.offsetTopAndBottom(scroller1.getCurrY()-child1.getTop());
            ViewCompat.postInvalidateOnAnimation(this);// TODO: 2018/7/27 这里也必须调用否则不会再次调用computerScroll
        }else {
            scroller1.forceFinished(true);
        }
    }
}
