package com.sun.webview_test;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.webview_gesture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/11/10.
 */

public class MainFragmentTest extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layout, null);
        ViewPager mViewPager = view.findViewById(R.id.view_pager);

        List<View> viewList = new ArrayList<>();
        List<View> textList=new ArrayList<>();

        final MyScrollView firstScrollview= new MyScrollView(getContext());

        StateListDrawable selector=new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.BLACK));

        GradientDrawable shape=new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setAlpha(50);
        shape.setColor(Color.GRAY);
        shape.setStroke(3,Color.YELLOW);
        shape.setCornerRadius(3);

        selector.addState(new int[]{},shape);



        ViewPager mViewPagerInScroll=firstScrollview.findViewById(R.id.view_pager_in_scroll);
        TextView t11=new TextView(getContext());
        t11.setText("inner first");
        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstScrollview.performOpen();
            }
        });
        t11.setBackground(selector);

        TextView t22=new TextView(getContext());
        t22.setText("inner second");
        textList.add(t11);
        textList.add(t22);
        mViewPagerInScroll.setAdapter(new MainPagerAdapter(textList));

        TextView t2 = new TextView(getContext());
        t2.setText("Second");
        viewList.add(firstScrollview);
        viewList.add(t2);
        mViewPager.setAdapter(new MainPagerAdapter(viewList));
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Browser", "mainfragment onresume");
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i("MainFragmentTest", "onPageScrolled:"+position+" "+positionOffset+" "+positionOffsetPixels);

        }

        @Override
        public void onPageSelected(int position) {
            Log.i("MainFragmentTest", "onPageSelected:"+position);



        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i("MainFragmentTest", "onPageScrollStateChanged:"+state);

        }
    };


    class MainPagerAdapter extends PagerAdapter {
        List<View> mList;

        public MainPagerAdapter(List<View> list) {
            mList = list;
        }


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return  view==object;
        }

        @Override
        public Object instantiateItem(View collection, int position) {
            ((ViewPager) collection).addView(mList.get(position), 0);
            return mList.get(position);
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView(mList.get(position));
        }

    }

}
