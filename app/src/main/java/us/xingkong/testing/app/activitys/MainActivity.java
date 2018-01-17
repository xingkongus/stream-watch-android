package us.xingkong.testing.app.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class MainActivity extends BaseActivity {

    protected RecyclerView recyclerView;
    protected ItemAdapter adapter;
    protected List<App> apps;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init(boolean bindSuccess) {
        apps = new ArrayList<>();

        findViewById();
        adapter = new ItemAdapter(apps);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        //获取直播信息
        client.getApps(new ResultListener<AppsResult>() {
            @Override
            public void onDone(AppsResult result, Exception e) {

                if (result != null) {
                    apps.clear();
                    //System.out.println(app.getAppname());
                    //if(app.isAlive())
                    apps.addAll(result.getApps());
                    adapter.notifyDataSetChanged();
                }

                if (e != null)
                    e.printStackTrace(System.err);

            }
        });
    }

    protected void findViewById() {
        recyclerView = findViewById(R.id.appsList);
    }
}
