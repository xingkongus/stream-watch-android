package us.xingkong.testing;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by SeaLynn0 on 2018/1/30.
 */

public class MyApplication extends Application {

    /**
     * 一个MyApplication的静态对象
     * 用于Fragment获取上下文练习Context
     */
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;   //在MyApplication 启动时获取本对象

        /*
        利用 Application 中声明的一个内部接口 ActivityLifecycleCallbacks 以及 registerActivityLifecycleCallbacks() 的方法
        判断ToolBar是否存在，存在即实例化此对象
        （感觉这个方法和接口很牛逼，可以减少BaseActivity的封装
        https://www.jianshu.com/p/75a5c24174b2）
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity.findViewById(R.id.toolbar)!=null){
                    ((BaseActivity)activity).setSupportActionBar((Toolbar)activity.findViewById(R.id.toolbar));
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 获取context
     *
     * @return mInstance
     */
    public static Context getInstance() {
        return mInstance;
    }
}
