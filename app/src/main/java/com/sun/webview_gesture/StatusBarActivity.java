package com.sun.webview_gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sun.utils.StatusBarUtil;

/**
 * Created by sun on 2018/1/25.
 */

public class StatusBarActivity extends AppCompatActivity implements View.OnClickListener {

    Button first_bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status_bar_main_layout);
        first_bt=findViewById(R.id.first_button);
        first_bt.setOnClickListener(this);
        findViewById(R.id.second_button).setOnClickListener(this);
        findViewById(R.id.third_button).setOnClickListener(this);
        findViewById(R.id.four_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_button:
                StatusBarUtil.hideSystemUI(this.getWindow());
                break;
            case R.id.second_button:
                StatusBarUtil.showSystemUI(this.getWindow());
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
