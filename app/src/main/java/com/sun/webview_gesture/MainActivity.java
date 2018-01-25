package com.sun.webview_gesture;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        RecyclerView activityList=findViewById(R.id.activity_list);

        activityList.setLayoutManager(new LinearLayoutManager(this));

        activityList.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.set(10,10,0,0);
            }
        });

        ArrayList list=new ArrayList();
        list.add("DrawerLayoutActivity");
        list.add("DrawerlayoutFragmentActivity");
        list.add("MxBrowserActivity");
        list.add("JsActivity");
        list.add("StatusBarActivity");

        ActivityAdaper activityAdaper=new ActivityAdaper(this,list);

        activityList.setAdapter(activityAdaper);
    }
}
