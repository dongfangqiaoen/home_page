package com.sun.webview_test;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sun on 2017/11/21.
 */

public class MainVeiwPager extends ViewPager {
    int mPreX ;

    public MainVeiwPager(Context context) {
        super(context);
    }

    public MainVeiwPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPreX = (int) event.getX();
        } else {
            if (Math.abs((int) event.getX() - mPreX) > 20) {
                return true;
            } else {
                mPreX = (int) event.getX();
            }
        }
        boolean returnBoolean=super.onInterceptTouchEvent(event);
        return returnBoolean;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
