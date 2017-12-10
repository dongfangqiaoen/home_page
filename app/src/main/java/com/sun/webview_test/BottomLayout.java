package com.sun.webview_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by sun on 2017/11/21.
 */

public class BottomLayout extends FrameLayout {

    public BottomLayout(@NonNull Context context) {
        super(context);
    }

    public BottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        Log.d("Event", "dispatchGenericFocusedEvent="+event);
        return super.dispatchGenericFocusedEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("Event", "onInterceptTouchEvent="+ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Event", "onTouchEvent="+event);
        return super.onTouchEvent(event);
    }
}
