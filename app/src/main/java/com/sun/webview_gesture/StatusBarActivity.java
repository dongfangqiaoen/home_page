package com.sun.webview_gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sun.utils.StatusBarUtil;

/**
 * Created by sun on 2018/1/25.
 */

public class StatusBarActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status_bar_main_layout);
        findViewById(R.id.first_button).setOnClickListener(this);
        findViewById(R.id.second_button).setOnClickListener(this);
        findViewById(R.id.third_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_button:
                StatusBarUtil.hideSystemUI(getWindow());
                break;
            case R.id.second_button:
                StatusBarUtil.showSystemUI(getWindow());
                break;
            case R.id.third_button:
                StatusBarUtil.setTranslucent(this);
                break;
            case R.id.four_button:
                StatusBarUtil.setNoTranslucent(this);
                break;
            default:

        }

    }
}
