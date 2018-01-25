package com.sun.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StatusBarUtil {

    private static String TAG = "StatusBarUtil";

    public static final int DEFAULT_STATUS_BAR_ALPHA = 0;
    public static final int LOLLIPOP_STATUS_BAR_ALPHA = 80;


    /**
     * 设置状态栏颜色
     *
     * @param activity         需要设置的 activity
     * @param color            状态栏颜色值
     * @param isLightStatusBar
     */
    public static void setColor(Activity activity, @ColorInt int color, boolean isLightStatusBar) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA, isLightStatusBar);
    }

    public static void setColor(Activity activity, @ColorInt int color, int statusBarAlpha, boolean isLightStatusBar) {
        Window window = activity.getWindow();
        setColor(window, color, statusBarAlpha, isLightStatusBar);

    }

    public static void setColor(Window window, int color, int statusBarAlpha, boolean isLightStatusBar) {
        setColor(window, color, isLightStatusBar);
    }

    public static void setColor(Window window, @ColorInt int color, boolean isLightStatusBar) {

        int ui = window.getDecorView().getSystemUiVisibility();
        if (isLightStatusBar) {
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
//        window.getDecorView().setSystemUiVisibility(ui);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA));
//            window.getDecorView().setSystemUiVisibility(ui);
//
//            //对小米兼容
//            if (com.mx.push.utils.PushUtils.isMiUi()) {
//                MIUISetStatusBarLightMode(window, isLightStatusBar);
//            }
//        }
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 隐藏SystemUi
     */
    public static void hideSystemUI(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 显示SystemUi
     *
     * @param window
     */
    public static void showSystemUI(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 显示SystemUiNavigation  主要处理PopupWindow
     *
     * @param view
     */
    public static void hideSystemUI(View view) {
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    /**
     * 显示SystemUiNavigation  主要处理PopupWindow
     *
     * @param view
     */
    public static void showSystemUI(View view) {
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY & ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN & ~View.SYSTEM_UI_FLAG_FULLSCREEN & ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }


    /**
     * 判断当前版本是否可以支持透明状态栏，系统版本和是否是平板
     * @return
     */
    public static boolean canTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            return true;
        }
        return false;
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     */
    public static void setTranslucent(Activity activity) {
        Window window = activity.getWindow();
        setTranslucent(window);
    }

    public static void setTranslucent(Window window) {


        //对小米兼容
        if (isMiUi()) {
            MIUISetStatusBarLightMode(window, true);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int ui = window.getDecorView().getSystemUiVisibility();
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(ui);
            return;
        }

        //对华为兼容
        if(isEMUI()){
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int ui = window.getDecorView().getSystemUiVisibility();
                if (true) {
                    ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                window.getDecorView().setSystemUiVisibility(ui);
//            }
            return;
        }

        //对魅族兼容
        if(isMeizuFlymeOS()){

        }

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int ui = window.getDecorView().getSystemUiVisibility();
            if (true) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(ui);
        }
    }

   public static void  setNoTranslucent(Activity activity){
       Window window = activity.getWindow();
       setNoTranslucent(window);
   }

   public static void setNoTranslucent(Window window){

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           // 设置状态栏透明
           window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           int ui = window.getDecorView().getSystemUiVisibility();
           if (false) {
               ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
           } else {
               ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
           }
           window.getDecorView().setSystemUiVisibility(ui);
       }
   }


    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    /**
     *
     * 实现判断当前系统是否是miui
     * @return
     */

    public static final boolean isMiUi() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }


    /**
     *   判断是华为操作系统
     * @return true 为华为系统 否则不是
     */
    public static boolean isEMUI() {
        String emuiOsFlag=getSystemProperty("ro.build.version.emui","");
        Log.e(TAG,"SystemUtil=================>"+emuiOsFlag);
        if (TextUtils.isEmpty(emuiOsFlag)) {
            return false;
        }else if(emuiOsFlag.contains("EmotionUI")){
            return true;
        }
        else{
            return false;
        }

    }


    /**
     *   判断是魅族操作系统
     * @return true 为魅族系统 否则不是
     */
    public static boolean isMeizuFlymeOS() {
/* 获取魅族系统操作版本标识*/
        String meizuFlymeOSFlag  = getSystemProperty("ro.build.display.id","");
        Log.e(TAG,"SystemUtil=================>"+meizuFlymeOSFlag);
        if (TextUtils.isEmpty(meizuFlymeOSFlag)){
            return false;
        }else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")){
            return  true;
        }else {
            return false;
        }
    }


    /**
     *   获取系统属性
     * @param key  ro.build.display.id
     * @param defaultValue 默认值
     * @return 系统操作版本标识
     */
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (ClassNotFoundException e) {
            Log.e(TAG,"SystemUtil=================>"+e.getMessage());
            return null;
        } catch (NoSuchMethodException e) {
            Log.e(TAG,"SystemUtil=================>"+e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            Log.e(TAG,"SystemUtil=================>"+e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG,"SystemUtil=================>"+e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            Log.e(TAG,"SystemUtil=================>"+e.getMessage());
            return null;
        }
    }


    public static class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            FileInputStream is = null;
            try {
                is = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(is);
            }catch (IOException e){
                throw e;
            }finally {
                closeQuietly(is);
            }
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }


    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }


}
