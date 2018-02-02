package us.xingkong.testing.app.activities.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


import butterknife.BindView;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class LiveActivity extends BaseActivity {

    @BindView(R.id.video)
    protected VideoView videoView;

    @Override
    public int getLayout() {
        return R.layout.acticity_live;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {
        String app = getIntent().getStringExtra("app");
        videoView.setVideoURI(Uri.parse("http://live.xingkong.us/hls/" + app + ".m3u8"));
        videoView.setMediaController(new MediaController(this));
        videoView.start();

    }
}
