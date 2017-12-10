package com.sun.webview_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.sun.webview_gesture.R;

/**
 * Created by sun on 2017/11/10.
 */

public class MxBrowserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();// 隐藏ActionBar

        setContentView(R.layout.test_main_view);




        MainFragmentTest mainFragmentTest =new MainFragmentTest();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainFragmentTest).commit();


    }


}
