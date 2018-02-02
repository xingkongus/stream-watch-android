package us.xingkong.testing.app.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.testing.serveice.LiveService;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * API接口对象
     */
    protected Client client;
    protected ServiceConnection sc;
    protected LiveService serivce;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setContentView(getLayout());
        /*
        由于 ActivityLifecycleCallbacks 中所有方法的调用时机都是在 Activity 对应生命周期的 Super 方法中进行的,
        所以在 Activity 的 onCreate 方法中使用 setContentView 必须在 super.onCreate(savedInstanceState); 之前,
        不然在 onActivityCreated 方法中 findViewById 会发现找不到
         */
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this); //引入ButterKnife，在这里绑定ButterKnife子类无需绑定

        Intent intent = new Intent(this, LiveService.class);
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                serivce = ((LiveService.LiveBinder) iBinder).getService();
                //获取接口对象
                client = serivce.getLive();
                init(savedInstanceState,true);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                init(savedInstanceState,false);
            }
        };
        bindService(intent, sc, Service.BIND_AUTO_CREATE);

    }

    public abstract int getLayout();

    public abstract void init(Bundle savedInstanceState, boolean bindSuccess);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sc);
    }
}
