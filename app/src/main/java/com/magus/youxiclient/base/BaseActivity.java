package com.magus.youxiclient.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.magus.youxiclient.R;
import com.magus.youxiclient.util.SystemBarTintManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseActivity extends FragmentActivity implements View.OnClickListener{

    private FrameLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        content = (FrameLayout) findViewById(R.id.base_content);
    }

    private void fillLayoutView() {
        String layoutName = getLayoutName();
        int layoutId = getFieldValue("layout", layoutName, this);
        if (layoutId != -1) {
            setContentLayout(layoutId);
        }

    }

    private String getLayoutName() {
        String className = this.getClass().getSimpleName();
        className = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
        Pattern p = Pattern.compile("\\p{Upper}");
        Matcher m = p.matcher(className);

        ArrayList<String> names = new ArrayList<String>();

        int index = 0;
        int lastIndex = index;
        while (m.find()) {
            index = className.indexOf(m.group());
            names.add(className.substring(lastIndex, index));
            lastIndex = index;
        }
        names.add(className.substring(lastIndex, className.length()));

        //        Collections.reverse(names);
        className = names.get(names.size() - 1);
        for (int i = 0; i < names.size() - 1; i++) {
            className += "_" + names.get(i);
        }
        return className.toLowerCase();
    }

    /**
     * 根据给定的类型名和字段名，返回R文件中的字段的值
     *
     * @param typeName  属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
     * @param fieldName 字段名
     * @return 字段的值
     * @throws Exception
     */
    public static int getFieldValue(String typeName, String fieldName, Context context) {
        int i = -1;
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".R$" + typeName);
            i = clazz.getField(fieldName).getInt(null);
        } catch (Exception e) {
            Log.d("" + context.getClass(), "没有找到" + context.getPackageName() + ".R$" + typeName + "类型资源 " + fieldName + "请copy相应文件到对应的目录.");
            return -1;
        }
        return i;
    }

    protected void setContentLayout(int layoutId) {
        View sonView = LayoutInflater.from(this).inflate(layoutId, null);
        content.addView(sonView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        getChildViewForm(getWindow().getDecorView());
    }

    protected void setContentLayout(View view) {
        content.addView(view);
        getChildViewForm(getWindow().getDecorView());
    }

    private void getChildViewForm(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup g = (ViewGroup) view;
                for (int i = 0; i < g.getChildCount(); i++) {
                    getChildViewForm(g.getChildAt(i));
                }
            } else if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected FrameLayout getModuleContentLayout() {
        return content;
    }

    //双击退出
    private long exitTime = -1;

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.click_again_to_exit, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    //双击返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((this instanceof MainActivity) && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Toast公共方法
    private Toast toast = null;

    protected void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /* 沉浸式状态栏 */
    protected void setImmersiveStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(false);
            //		    // 激活导航栏设置
            tintManager.setNavigationBarTintEnabled(false);
            // 设置一个颜色给系统栏  #ff4444红色
            tintManager.setTintColor(Color.parseColor("#ff4444"));
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
