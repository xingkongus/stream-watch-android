package us.xingkong.testing.app.activitys;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import us.xingkong.streamsdk.network.Client;
import us.xingkong.testing.R;
import us.xingkong.testing.serveice.LiveService;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * API接口对象
     */
    protected Client client;
    protected ServiceConnection sc;
    protected LiveService serivce;

    private Toolbar toolbar;
    private ContentFrameLayout viewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        findViewById();
        setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                if (!showToolbar())
                    getSupportActionBar().hide();
            }

        LayoutInflater.from(BaseActivity.this).inflate(getLayout(), viewContent);

        Intent intent = new Intent(this, LiveService.class);
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                serivce = ((LiveService.LiveBinder) iBinder).getService();
                //获取接口对象
                client = serivce.getLive();
                init(true);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                init(false);
            }
        };
        bindService(intent, sc, Service.BIND_AUTO_CREATE);

    }

    private void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        viewContent = findViewById(R.id.view_content);
    }

    protected abstract boolean showToolbar();

    public abstract int getLayout();

    public abstract void init(boolean bindSuccess);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sc);
    }
}
