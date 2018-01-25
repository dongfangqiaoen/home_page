package com.sun.webview_gesture;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.webview_test.MxBrowserActivity;

import java.util.ArrayList;

/**
 * Created by sun on 2017/11/10.
 */

public class ActivityAdaper extends RecyclerView.Adapter <ActivityAdaper.ActivityViewHolder>{

    Context mContext;
    ArrayList<String> mList;

    public ActivityAdaper() {
    }

    public ActivityAdaper(Context context, ArrayList<String> list) {
        mList=list;
        mContext=context;
    }


    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityViewHolder holder = new ActivityViewHolder(new TextView(mContext));
        return holder;

    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ActivityViewHolder(View view) {
            super(view);

            mTextView= (TextView) view;
            mTextView.setTextSize(20);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if("DrawerLayoutActivity".equals(mTextView.getText())){
                       Intent intent =new Intent(v.getContext(),DrawerLayoutActivity.class);
                       v.getContext().startActivity(intent);
                   }else if("DrawerlayoutFragmentActivity".equals(mTextView.getText())){

                       Intent intent =new Intent(v.getContext(),DrawerlayoutFragmentActivity.class);
                       v.getContext().startActivity(intent);
                   }else if("MxBrowserActivity".equals(mTextView.getText())){

                       Intent intent =new Intent(v.getContext(),MxBrowserActivity.class);
                       v.getContext().startActivity(intent);
                   }else if("JsActivity".equals(mTextView.getText())){
                       Intent intent =new Intent(v.getContext(),JsActivity.class);
                       v.getContext().startActivity(intent);
                   }else if("StatusBarActivity".equals(mTextView.getText())){
                       Intent intent =new Intent(v.getContext(),StatusBarActivity.class);
                       v.getContext().startActivity(intent);
                   }


                }
            });
        }
    }
}
