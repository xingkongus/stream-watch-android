package us.xingkong.testing.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import us.xingkong.testing.MyApplication;

/**
 * Created by SeaLynn0 on 2018/1/30.
 */

public class BaseFragment extends Fragment {

    /**
     * 一个Activity对象作为Context
     */
    private Activity activity;

    /**
     * 子Fragment获取Context的方法
     * @return activity
     */
    public Context getInstantContext() {
        if (activity == null) {
            return MyApplication.getInstance();
        }
        return activity;
    }

    /**
     * 用Fragment的onAttach(Context context);方法来绑定Context
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
}
