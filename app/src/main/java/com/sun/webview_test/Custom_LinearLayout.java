package com.sun.webview_test;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sun.webview_gesture.R;

/**
 * Created by sun on 2017/12/9.
 */

public class Custom_LinearLayout extends LinearLayout {
    public Custom_LinearLayout(Context context) {
        super(context);
        initView();
    }

    public Custom_LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Custom_LinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Custom_LinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

       View root= View.inflate(getContext(), R.layout.custom_linearlayout,this);
//       addView(root);
    }


}
