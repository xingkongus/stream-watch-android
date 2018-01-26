package us.xingkong.testing.serveice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import us.xingkong.streamsdk.network.Client;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class LiveService extends Service{

    private Client live;

    @Override
    public void onCreate() {
        super.onCreate();
        live = new Client(this);

    }

    public Client getLive() {
        return live;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LiveBinder();
    }

    public class LiveBinder extends Binder {

        public LiveService getService(){
            return LiveService.this;
        }
    }
}
