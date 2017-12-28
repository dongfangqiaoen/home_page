package com.sun.webview_test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.sun.webview_gesture.R;

/**
 * Created by sun on 2017/11/24.
 */

public class MyScrollView extends ScrollView {

    private String TAG = "MyScrollView";

    public MyScrollView(Context context) {
        super(context);
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    double upThreshold= ViewUtils.getCurScreenHeight(getContext())  * 0.25;
    int fixY;
    TextView  upText;
    boolean close=false;
    Scroller mScroller;
    ObjectAnimator mObjectAnimator;
    private void init(){
        int contentHeight=ViewUtils.getCurScreenHeight(getContext())-ViewUtils.getStatusBarHeight(getContext());

        LinearLayout scrollContainer= (LinearLayout) inflate(getContext(),R.layout.activity_scroll_view,null);
        upText=scrollContainer.findViewById(R.id.up_text);
        BottomLayout bottomLayout=scrollContainer.findViewById(R.id.bottom_view);
        bottomLayout.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,contentHeight));
        fixY= (int) (upText.getHeight()

        );
        addView(scrollContainer);
        mScroller=new Scroller(getContext());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        fixY=upText.getHeight();

    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: " + ev);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: " + ev+ "close="+close) ;
        if(close){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent: " + ev);

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "getScrollY=: " + getScrollY() + "  upThreshold=" + upThreshold);
                if (getScrollY() > upThreshold) {
                    Log.i(TAG, "fixY: " + fixY);
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollTo(0, fixY);

                        }
                    });
                }/*else{
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollTo(0, 0);
                        }
                    });
                }*/


                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            default:


        }

        return super.onTouchEvent(ev);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i(TAG, "onScrollChanged: " + "oldt=" + oldt + " t=" + t);
        if(t==0){
            close=false;
        }else if(t==fixY){
            close=true;
        }
        Log.i(TAG, "onScrollChanged: " + "close=" + close);
        super.onScrollChanged(l, t, oldl, oldt);
    }



    private void starCloseAnimation() {
        Log.i(TAG,"onAnimationStart");
        if (mObjectAnimator != null && mObjectAnimator.isRunning())
            return;

        mObjectAnimator = ObjectAnimator.ofInt(this, "scrollY", getScrollY(), fixY);
        mObjectAnimator.setInterpolator(new DecelerateInterpolator());
        mObjectAnimator.setDuration(300);
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG,"onAnimationUpdate");
            }
        });

        mObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG,"onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG,"onAnimationEnd");

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG,"onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG,"onAnimationRepeat");
            }
        });

        mObjectAnimator.start();
    }


    public void performOpen() {
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollToSlow(0, 0,2000);

            }
        });

    }



    private void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    private void smoothScrollBySlow(int dx, int dy,int duration) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 滑动事件，这是控制手指滑动的惯性速度
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 4);
    }

}
