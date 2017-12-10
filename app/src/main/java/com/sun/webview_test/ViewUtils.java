package com.sun.webview_test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;



/**
 * Created by wks on 17/4/20.
 * 视图工具类，包括屏幕，屏幕适配等等。
 */

public class ViewUtils {
    private static final String LOG_TAG  = "ViewUtils";
    private static long VALID_INTERVAL = 350;
    private static long mLastClickMilli;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;

    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context
     * @param pxValue
     */
    public static float px2dip(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2sp(Context context, float pxValue) {


        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {

        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * 获得当前屏幕的宽度， 不包含导航栏
     *
     * @param cx Context
     * @return int
     */
    public static float getCurScreenWidth(Context cx) {
        WindowManager manage = (WindowManager) cx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manage.getDefaultDisplay();
        return display.getWidth();
    }


    /**
     * 获得当前屏幕的高度，不包含导航栏
     * @param cx Context
     * @return int
     */
    public static int getCurScreenHeight(Context cx) {
        WindowManager manage = (WindowManager) cx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manage.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;

    }


    /**
     * 获取屏幕较大的边
     * @param context
     * @return
     */
    public static int compareBigScreenLength(Context context) {
        WindowManager manage = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manage.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        return Math.max(width, height);
    }

    /**
     * 获取屏幕较小的边
     * @param context
     * @return
     */
    public static int compareSmallScreenLength(Context context) {
        WindowManager manage = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manage.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        return Math.min(width, height);
    }

    /**
     * 获取屏幕的真实宽高，包含导航栏
     * @param context
     * @return
     */
//    public static Point getScreenRealResolution(Context context) {
//        Point size = new Point();
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        display.getMetrics(metrics);
//        int widthPixels = metrics.widthPixels;
//        int heightPixels = metrics.heightPixels;
//        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
//            try {
//                widthPixels = Reflect.on(display).call("getRawWidth").get();
//                heightPixels = Reflect.on(display).call("getRawHeight").get();
//                Log.i(LOG_TAG, "widthPixels = " + widthPixels);
//                Log.i(LOG_TAG, "heightPixels = " + heightPixels);
//            } catch (Exception ignored) {
//                ignored.printStackTrace();
//            }
//        }else if (Build.VERSION.SDK_INT >= 17) {
//            try {
//                Point realSize = new Point();
//                Reflect.on(display).call("getRealSize", realSize);
//                widthPixels = realSize.x;
//                heightPixels = realSize.y;
//                Log.i(LOG_TAG, "widthPixels = " + widthPixels);
//                Log.i(LOG_TAG, "heightPixels = " + heightPixels);
//            } catch (Exception ignored) {
//                ignored.printStackTrace();
//            }
//        }
//        size.x = widthPixels;
//        size.y = heightPixels;
//        return size;
//    }

    /**
     * 获取状态栏高度——方法1
     **/
    public static int getStatusHeight(Context context) {
        int statusBarHeight1 = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 获取状态栏高度——方法2
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 判断是否存在导航栏
     * @param context
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager manage = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = manage.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }

    /**
     * 获取导航栏的高度
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Context activity) {
        if (!hasNavigationBar(activity)){
            return 0;
        }

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 主要判断两次按键是否是一个双击事件，或者是单击事件小于有效的时间间隔，导致了无效操作
     * @return
     */
    public static boolean isDoubleClick() {
        long curMilli = System.currentTimeMillis();
        long interval = curMilli - mLastClickMilli;
        if (interval > 0 && interval < VALID_INTERVAL) {
            return true;
        }
        mLastClickMilli = curMilli;
        return false;
    }

    /**
     * 按屏幕大小渲染一个view
     * @param view
     */
    public static void layoutViewAllDisplay(View view){
        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        layoutView(view, metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 重新布局一个View
     * @param v view
     * @param width 宽
     * @param height 高
     */
    public static void layoutView(View v, int width, int height) {
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    /**
     * 生成高亮文字
     *
     * @param src
     *            包含高亮字符串的整个字符串
     * @param highlight
     *            高亮文字
     * @param highlightColor
     *            高亮文字的颜色
     * @return
     */
    public static CharSequence highlightText(String src, String highlight,
                                             int highlightColor) {

        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(highlight))
            return src;

        SpannableStringBuilder style = new SpannableStringBuilder(src);
        String tmpSrc = src.toLowerCase();
        String tmpHl = highlight.toLowerCase();
        int start = tmpSrc.indexOf(tmpHl);
        int end = start + highlight.length();


        while(start >= 0){
            try {
                style.setSpan(new ForegroundColorSpan(highlightColor), start,
                        end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                Log.e("StringUtils", "hightlightText.", e.fillInStackTrace());
            }
            start = tmpSrc.indexOf(tmpHl, end);
            end = start + highlight.length();
        }

        return style;
    }

    public static final CharSequence highlight(Context context, CharSequence target, CharSequence highlight, int styleResId){
        if(TextUtils.isEmpty(target) || TextUtils.isEmpty(highlight)){
            return target;
        }

        String tmpSrc = target.toString().toLowerCase();
        String tmpHl = highlight.toString().toLowerCase();
        int start = tmpSrc.indexOf(tmpHl);
        int end = start + highlight.length();

        SpannableStringBuilder style = new SpannableStringBuilder(target);
        while(start > -1){
            //if(start > -1){
            style.setSpan(new TextAppearanceSpan(context, styleResId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = tmpSrc.indexOf(tmpHl, end);
            end = start + highlight.length();
        }
        return style;
    }

    /**
     * 获取适配指定TextView的字符串
     *
     * @return
     */
    public static String getEllipsisStr(String source, TextView tv) {
        if (!TextUtils.isEmpty(source)) {
            return TextUtils.ellipsize(source,
                    tv.getPaint(),
                    tv.getWidth() - tv.getPaint().measureText("...") * 3 / 2,
                    TextUtils.TruncateAt.END).toString();
        }
        return "";
    }
}
