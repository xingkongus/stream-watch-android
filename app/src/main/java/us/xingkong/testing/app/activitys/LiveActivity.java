package us.xingkong.testing.app.activitys;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;


import us.xingkong.testing.R;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class LiveActivity extends BaseActivity {

    protected VideoView videoView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public int getLayout() {
        return R.layout.acticity_live;
    }

    @Override
    public void init(boolean bindSuccess) {
        findViewById();
        String app = getIntent().getStringExtra("app");
        videoView.setVideoURI(Uri.parse("http://live.xingkong.us/hls/" + app + ".m3u8"));
        videoView.setMediaController(new MediaController(this));
        videoView.start();

    }

    public void findViewById() {
        videoView = findViewById(R.id.video);
    }
}
